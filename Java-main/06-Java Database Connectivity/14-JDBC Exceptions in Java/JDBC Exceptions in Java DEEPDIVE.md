# 14. JDBC Exceptions in Java — DEEPDIVE

JDBC programs sit between a Java application and a database:

```text
Java Application
       ↓
    JDBC API
       ↓
 JDBC Driver
       ↓
    Database
```

At **any layer**, something can go wrong.

For example:

```text
Java Application
      ↓
Wrong URL
      ↓
JDBC Driver
      ↓
Authentication failure
      ↓
Database
```

JDBC provides exception mechanisms so that your Java program can detect, diagnose, and handle database-related failures.

We will study:

```text
14. JDBC Exceptions
│
├── SQLException
├── SQLState
├── Error Codes
└── Exception Handling
```

---

# 1. `SQLException`

## 1.1 What is `SQLException`?

`SQLException` is the primary exception class used by JDBC to indicate that something went wrong while accessing or processing data in a database.

It belongs to:

```java
java.sql.SQLException
```

Example:

```java
try {
    Connection con =
        DriverManager.getConnection(
            url,
            username,
            password
        );
}
catch (SQLException e) {
    e.printStackTrace();
}
```

If the connection cannot be established, the JDBC operation can throw `SQLException`.

---

# 1.2 Why does JDBC use an exception?

Suppose Java executes:

```java
ResultSet rs =
    st.executeQuery("SELECT * FROM student");
```

Many things could go wrong:

```text
SQL syntax error
        ↓
Table doesn't exist
        ↓
Column doesn't exist
        ↓
Database unavailable
        ↓
Permission denied
        ↓
Connection lost
        ↓
Constraint violation
```

The database/driver needs a mechanism to communicate these failures back to Java.

That mechanism is primarily:

```text
SQLException
```

---

# 1.3 `SQLException` hierarchy

Conceptually:

```text
Throwable
   │
   └── Exception
         │
         └── SQLException
               │
               ├── SQLWarning
               ├── SQLTimeoutException
               ├── SQLSyntaxErrorException
               ├── SQLIntegrityConstraintViolationException
               ├── SQLDataException
               └── other JDBC subclasses
```

There are many specialized JDBC exceptions.

For example:

### `SQLSyntaxErrorException`

Can indicate an SQL syntax-related problem.

### `SQLIntegrityConstraintViolationException`

Can indicate a constraint violation such as a duplicate key or foreign-key violation.

### `SQLTimeoutException`

Can indicate that a JDBC operation timed out.

The exact exception subclass thrown depends on the JDBC driver/database and circumstances.

---

# 1.4 Is `SQLException` a checked exception?

Yes.

`SQLException` extends `Exception`, not `RuntimeException`.

Therefore Java requires you to either:

### Handle it

```java
try {
    // JDBC code
}
catch (SQLException e) {
    // handle
}
```

or:

### Declare it

```java
void getData() throws SQLException {
    // JDBC code
}
```

This is an important Java language rule.

---

# 1.5 Example using `throws`

```java
public static void connect() throws SQLException {

    Connection con =
        DriverManager.getConnection(
            url,
            user,
            password
        );
}
```

Here the method does not handle the exception itself.

It says:

> "This method may throw `SQLException`; the caller must deal with it."

---

# 1.6 What information does `SQLException` contain?

An `SQLException` is more than simply:

```text
"Something went wrong."
```

It can provide diagnostic information.

Important methods include:

```java
e.getMessage();
e.getSQLState();
e.getErrorCode();
e.getCause();
e.getNextException();
```

Conceptually:

```text
SQLException
│
├── Message
├── SQLState
├── Error Code
├── Cause
└── Next Exception
```

---

# 1.7 `getMessage()`

```java
e.getMessage()
```

returns a descriptive message associated with the exception.

Example:

```java
catch (SQLException e) {

    System.out.println(
        e.getMessage()
    );
}
```

You might receive a message such as:

```text
Table 'college.student' doesn't exist
```

The exact message depends on the database and JDBC driver.

---

# 1.8 `getCause()`

`SQLException` inherits Java's exception-cause mechanism.

```java
Throwable cause = e.getCause();
```

This represents another exception that caused the current exception, when a cause has been provided.

Don't confuse:

```java
getCause()
```

with:

```java
getNextException()
```

They represent different exception relationships.

---

# 1.9 `getNextException()`

JDBC supports **exception chaining**.

You can have:

```text
SQLException #1
       ↓
SQLException #2
       ↓
SQLException #3
```

You can retrieve the next SQL exception using:

```java
e.getNextException();
```

Example:

```java
SQLException current = e;

while (current != null) {

    System.out.println(
        current.getMessage()
    );

    current = current.getNextException();
}
```

This can be useful when a JDBC driver supplies multiple related database diagnostics.

---

# 2. SQLState

Now let's understand one of the most frequently misunderstood JDBC concepts.

# 2.1 What is SQLState?

**SQLState** is a five-character code associated with the SQL condition reported by the JDBC driver.

You obtain it using:

```java
e.getSQLState();
```

Example:

```java
catch (SQLException e) {

    System.out.println(
        "SQLState = " + e.getSQLState()
    );
}
```

It generally has the form:

```text
XXXXX
```

where each character is part of the SQLState classification.

---

# 2.2 Why does SQLState exist?

Imagine two different database vendors report an error.

Their internal numeric error codes may be completely different.

For example, conceptually:

```text
Database A
Error code → 1234

Database B
Error code → 5678
```

Those numbers aren't necessarily portable.

SQLState provides a standardized classification mechanism.

So:

```text
Vendor-specific
       ↓
Error Code

More standardized
       ↓
SQLState
```

This makes SQLState particularly useful when writing code that should recognize broad categories of database errors.

---

# 2.3 SQLState structure

SQLState is generally five characters.

The first two characters represent the **class**.

The last three represent a more specific condition within that class.

Conceptually:

```text
SQLState
  ↓
┌─────┐
│ 23  │  ← class
│ 000 │  ← subclass
└─────┘
```

For example:

```text
23000
```

is associated with integrity-constraint violations in the SQLState classification.

---

# 2.4 SQLState `23xxx`

A commonly encountered class is:

```text
23
```

which represents **integrity constraint violations**.

For example:

```text
Duplicate key
Foreign-key violation
Constraint violation
```

The exact SQLState depends on the specific condition and database/driver.

---

# 2.5 SQLState is not an exception class

This distinction is important.

These are different:

```java
SQLIntegrityConstraintViolationException
```

and:

```java
e.getSQLState()
```

The first is a **Java exception type**.

The second is a **five-character SQL condition code**.

Conceptually:

```text
Java exception type
       ↓
SQLIntegrityConstraintViolationException

SQLState
       ↓
23000
```

They can describe related aspects of the same failure.

---

# 3. Error Codes

# 3.1 What is an error code?

An error code is a numeric code supplied by the JDBC driver/database to provide additional diagnostic information.

Retrieve it with:

```java
e.getErrorCode();
```

Example:

```java
catch (SQLException e) {

    System.out.println(
        "Error Code = " +
        e.getErrorCode()
    );
}
```

---

# 3.2 Are JDBC error codes universal?

### No.

This is extremely important.

The numeric error code is generally specific to the:

```text
Database
     +
Driver
```

Therefore:

```text
Error Code
     ↓
Vendor-specific diagnostic information
```

You should not assume:

```text
Error code 123
```

has the same meaning across MySQL, PostgreSQL, Oracle, SQL Server, etc.

---

# 3.3 SQLState vs Error Code

Suppose:

```java
catch (SQLException e) {

    System.out.println(
        "SQLState: " +
        e.getSQLState()
    );

    System.out.println(
        "Error Code: " +
        e.getErrorCode()
    );
}
```

You might conceptually get:

```text
SQLState: 23000
Error Code: 1062
```

The important point isn't the specific numeric value.

It's:

```text
23000
 ↓
SQLState classification

1062
 ↓
Vendor-specific error information
```

---

# 3.4 Why use both?

Because they answer different questions.

### SQLState

> What general SQL condition occurred?

### Error code

> What specific vendor/database error was reported?

So:

```text
              SQLException
                    │
          ┌─────────┴─────────┐
          ↓                   ↓
      SQLState            Error Code
          ↓                   ↓
 General classification   Vendor-specific
                          information
```

---

# 3.5 Portability consideration

Suppose you're building an application that supports several database vendors.

Avoid designing your entire error-handling strategy around vendor-specific error numbers.

Instead, use:

* standard JDBC exception types where appropriate
* SQLState for portable classification where appropriate
* vendor error codes when database-specific handling is genuinely required

For example:

```java
catch (SQLIntegrityConstraintViolationException e) {
    // Handle integrity violation
}
```

is often more portable than:

```java
if (e.getErrorCode() == SOME_VENDOR_SPECIFIC_NUMBER) {
    ...
}
```

---

# 4. Exception Handling

Now let's understand how JDBC exceptions should actually be handled.

---

# 4.1 Basic `try-catch`

The simplest pattern:

```java
try {

    Connection con =
        DriverManager.getConnection(
            url,
            user,
            password
        );

}
catch (SQLException e) {

    System.out.println(
        e.getMessage()
    );
}
```

Flow:

```text
try
 ↓
JDBC operation
 ↓
Success?
 ├── YES → continue
 └── NO
      ↓
SQLException
      ↓
catch
```

---

# 4.2 Handling diagnostic information

A more useful development/debugging pattern:

```java
try {

    Connection con =
        DriverManager.getConnection(
            url,
            user,
            password
        );

}
catch (SQLException e) {

    System.out.println(
        "Message: " + e.getMessage()
    );

    System.out.println(
        "SQLState: " + e.getSQLState()
    );

    System.out.println(
        "Error Code: " + e.getErrorCode()
    );
}
```

This gives three different levels of information.

---

# 4.3 `printStackTrace()`

During development:

```java
catch (SQLException e) {
    e.printStackTrace();
}
```

is useful because it shows the stack trace.

However, production applications generally shouldn't rely on raw console stack traces.

Instead, use an appropriate logging framework and provide suitable error handling to the application/user.

---

# 4.4 Catching specific exceptions

Suppose you want to distinguish constraint violations from other SQL errors.

You can write:

```java
try {

    // JDBC operation

}
catch (SQLIntegrityConstraintViolationException e) {

    System.out.println(
        "Constraint violation"
    );

}
catch (SQLException e) {

    System.out.println(
        "Other database error"
    );
}
```

The order matters.

---

# 4.5 Why must the specific exception come first?

Because:

```text
SQLIntegrityConstraintViolationException
             ↓
        SQLException
```

The subclass is also an `SQLException`.

If you write:

```java
catch (SQLException e) {
    ...
}
catch (SQLIntegrityConstraintViolationException e) {
    ...
}
```

the second catch is unreachable because the first catch would already catch it.

Therefore:

```text
Specific
   ↓
General
```

---

# 4.6 `throws SQLException`

Instead of catching an exception:

```java
public void insertStudent()
        throws SQLException {

    // JDBC operation
}
```

This means:

> The current method doesn't handle the exception; it propagates responsibility to its caller.

Example:

```java
public void saveStudent()
        throws SQLException {

    PreparedStatement ps =
        con.prepareStatement(
            "INSERT INTO student VALUES (?, ?)"
        );

    ps.setInt(1, 101);
    ps.setString(2, "A");

    ps.executeUpdate();
}
```

The caller can handle it:

```java
try {
    saveStudent();
}
catch (SQLException e) {
    // handle
}
```

---

# 4.7 `try-with-resources`

JDBC uses resources that should be closed:

```text
Connection
Statement / PreparedStatement
ResultSet
```

A modern approach is:

```java
try (
    Connection con =
        DriverManager.getConnection(
            url, user, password
        );

    PreparedStatement ps =
        con.prepareStatement(
            "SELECT id, name FROM student"
        );

    ResultSet rs =
        ps.executeQuery()
) {

    while (rs.next()) {

        System.out.println(
            rs.getInt("id")
        );

    }

}
catch (SQLException e) {

    e.printStackTrace();
}
```

When the try block finishes, the resources are automatically closed.

---

# 4.8 Why is resource closing important?

Suppose you repeatedly create:

```text
Connection
Connection
Connection
Connection
...
```

and never close them.

Eventually you can exhaust database connections or other resources.

Therefore:

```text
Use resource
    ↓
Finish operation
    ↓
Close resource
```

Try-with-resources makes this much safer.

---

# 4.9 Exception handling with transactions

Transactions make exception handling even more important.

Suppose:

```java
con.setAutoCommit(false);
```

Then:

```java
try {

    // Operation 1
    ps1.executeUpdate();

    // Operation 2
    ps2.executeUpdate();

    con.commit();

}
catch (SQLException e) {

    con.rollback();

}
```

Conceptually:

```text
Transaction begins
       ↓
Operation 1
       ↓
Operation 2
       ↓
   Everything OK?
      /     \
    YES      NO
     ↓        ↓
 commit()  rollback()
```

This is a crucial JDBC pattern.

---

# 4.10 Why rollback belongs in the catch?

Suppose:

```text
Operation 1 → SUCCESS
Operation 2 → SUCCESS
Operation 3 → FAILURE
```

If the transaction is manually controlled, you may want to undo the earlier uncommitted changes:

```text
Operation 1
    ↓
Operation 2
    ↓
Operation 3 ❌
    ↓
rollback()
```

Without an appropriate rollback strategy, you may leave the transaction in an undesirable state.

---

# 4.11 `SQLException` in batch processing

Batch operations can throw:

```java
BatchUpdateException
```

which is a subclass of `SQLException`.

Example:

```java
try {

    ps.executeBatch();

}
catch (BatchUpdateException e) {

    int[] counts =
        e.getUpdateCounts();

    e.printStackTrace();
}
```

This is useful because batch execution has multiple update results.

---

# 4.12 `BatchUpdateException`

Conceptually:

```text
SQLException
     │
     └── BatchUpdateException
```

It can provide update counts associated with batch processing.

For example:

```text
Batch
│
├── Operation 1 → success
├── Operation 2 → success
└── Operation 3 → failure
```

The driver reports update-count information according to JDBC/driver behavior.

Don't assume every driver behaves identically in every failure scenario.

---

# 5. Exception Chaining in Detail

JDBC has two concepts that students frequently mix up:

```java
getCause()
```

and:

```java
getNextException()
```

---

## `getCause()`

This is Java's general exception-cause mechanism.

```java
Throwable cause = e.getCause();
```

It answers:

> What exception caused this exception?

---

## `getNextException()`

This is JDBC's SQL exception chain.

```java
SQLException next =
    e.getNextException();
```

It answers:

> Is there another related `SQLException` in the JDBC exception chain?

So:

```text
getCause()
     ↓
General Java exception cause

getNextException()
     ↓
JDBC SQLException chain
```

Do not treat them as synonyms.

---

# 6. Complete Diagnostic Program

Here's a useful JDBC exception-handling template:

```java
import java.sql.*;

public class JdbcExceptionDemo {

    public static void main(String[] args) {

        String url =
            "jdbc:mysql://localhost:3306/college";

        String user = "root";
        String password = "password";

        try (
            Connection con =
                DriverManager.getConnection(
                    url, user, password
                );

            PreparedStatement ps =
                con.prepareStatement(
                    "SELECT * FROM student"
                );

            ResultSet rs =
                ps.executeQuery()
        ) {

            while (rs.next()) {

                System.out.println(
                    rs.getInt("id") +
                    " " +
                    rs.getString("name")
                );
            }

        }
        catch (SQLException e) {

            System.out.println(
                "Message: " +
                e.getMessage()
            );

            System.out.println(
                "SQLState: " +
                e.getSQLState()
            );

            System.out.println(
                "Error Code: " +
                e.getErrorCode()
            );

            SQLException next =
                e.getNextException();

            while (next != null) {

                System.out.println(
                    "Next SQL Exception: " +
                    next.getMessage()
                );

                next =
                    next.getNextException();
            }
        }
    }
}
```

This demonstrates:

```text
SQLException
├── getMessage()
├── getSQLState()
├── getErrorCode()
└── getNextException()
```

and also:

```text
try-with-resources
        ↓
automatic resource closing
```

---

# 7. `SQLException` vs `SQLState` vs Error Code

This distinction must be crystal clear.

| Concept        | What is it?                           | Example API              |
| -------------- | ------------------------------------- | ------------------------ |
| `SQLException` | Java exception object                 | `catch (SQLException e)` |
| SQLState       | Five-character SQL condition code     | `e.getSQLState()`        |
| Error code     | Numeric vendor/driver diagnostic code | `e.getErrorCode()`       |
| Message        | Human-readable diagnostic text        | `e.getMessage()`         |

Think:

```text
SQLException
     │
     ├── "What Java exception occurred?"
     │
     ├── Message
     │      ↓
     │   What happened?
     │
     ├── SQLState
     │      ↓
     │   What general SQL condition?
     │
     └── Error Code
            ↓
         What vendor-specific diagnostic?
```

---

# 8. Common JDBC Failure Scenarios

## Scenario 1 — Wrong database URL

```java
DriverManager.getConnection(
    wrongUrl,
    user,
    password
);
```

Possible result:

```text
SQLException
```

---

## Scenario 2 — Wrong credentials

```java
DriverManager.getConnection(
    url,
    "wrongUser",
    "wrongPassword"
);
```

Possible result:

```text
SQLException
```

---

## Scenario 3 — Invalid SQL

```java
st.executeQuery(
    "SELCT * FROM student"
);
```

The database may report a syntax error.

A driver may throw a specialized SQL exception such as:

```java
SQLSyntaxErrorException
```

or another `SQLException` depending on the database/driver.

---

## Scenario 4 — Constraint violation

```sql
INSERT INTO student(id, name)
VALUES (101, 'A');
```

If `101` already violates a unique constraint, a constraint-related exception may be reported.

For example:

```java
SQLIntegrityConstraintViolationException
```

may be used by the driver.

---

## Scenario 5 — Timeout

A JDBC operation may exceed its configured timeout.

A driver may report:

```java
SQLTimeoutException
```

---

# 9. What Should Good Exception Handling Do?

Good exception handling should answer three questions:

### 1. What happened?

Use:

```java
e.getMessage()
```

### 2. What category of SQL problem is it?

Use:

```java
e.getSQLState()
```

and/or an appropriate JDBC exception subclass.

### 3. What database-specific information is available?

Use:

```java
e.getErrorCode()
```

So:

```text
             JDBC FAILURE
                  │
       ┌──────────┼──────────┐
       ↓          ↓          ↓
    Message    SQLState   Error Code
       ↓          ↓          ↓
   Description Classification Vendor detail
```

---

# 10. What NOT to Do

## ❌ Don't silently ignore `SQLException`

Bad:

```java
catch (SQLException e) {
}
```

This makes diagnosing database failures extremely difficult.

---

## ❌ Don't rely only on the error code

```java
if (e.getErrorCode() == 1234) {
    ...
}
```

This can make code tightly coupled to one database vendor.

Use vendor-specific codes only when that dependency is intentional.

---

## ❌ Don't assume all `SQLException`s mean the same thing

There are many different failure categories.

Use specific exception subclasses when useful.

---

## ❌ Don't forget transactions

If you manually disable auto-commit:

```java
con.setAutoCommit(false);
```

make sure your error path handles the transaction appropriately.

---

## ❌ Don't forget resources

Use try-with-resources whenever practical.

---

# 11. Deep Conceptual Difference: Exception vs Error Information

This is a subtle but important distinction.

Consider:

```java
catch (SQLException e) {

    e.getSQLState();
    e.getErrorCode();
}
```

Here:

```text
SQLException
```

is the **exception object**.

But:

```text
SQLState
Error Code
Message
```

are **information contained in / associated with that exception**.

Therefore don't say:

> "SQLState is another exception."

It isn't.

Don't say:

> "Error code is an exception."

It isn't.

Correct:

```text
SQLException
     │
     ├── Message
     ├── SQLState
     ├── Error Code
     └── Exception chain
```

---

# 12. Deep Conceptual Difference: SQLState vs Java Exception Type

Suppose a constraint violation occurs.

You might have:

```java
SQLIntegrityConstraintViolationException
```

and:

```java
e.getSQLState()
```

returning a code in the integrity-constraint class, such as:

```text
23xxx
```

These describe the problem at different levels:

```text
Java level
    ↓
SQLIntegrityConstraintViolationException

SQL classification level
    ↓
SQLState
```

So they complement each other.

---

# 13. Deep Conceptual Difference: Error Code vs SQLState

Imagine:

```text
Database Vendor A
    ↓
Error Code: 1234

Database Vendor B
    ↓
Error Code: 5678
```

Those numbers may mean different things.

But the SQLState classification provides a more standardized way to categorize the condition.

Therefore:

```text
Portability
    ↑
SQLState / standard JDBC exception types

Vendor-specific diagnostics
    ↑
Error codes
```

This is why application code should generally avoid unnecessarily depending on vendor error numbers.

---

# 14. Complete Exception Flow

A JDBC failure can conceptually travel like this:

```text
             Java Application
                    │
                    ↓
                JDBC API
                    │
                    ↓
              JDBC Driver
                    │
                    ↓
                 Database
                    │
              Something fails
                    │
                    ↓
              Database reports
                    │
                    ↓
              JDBC Driver maps/
              reports condition
                    │
                    ↓
              SQLException
                    │
        ┌───────────┼───────────┐
        ↓           ↓           ↓
     Message     SQLState    Error Code
        │           │           │
        └───────────┼───────────┘
                    ↓
              Java application
                    │
                    ↓
              catch / throws
```

---

# 15. Best-Practice JDBC Exception Pattern

A robust general pattern is:

```java
try (
    Connection con =
        DriverManager.getConnection(
            url, user, password
        );

    PreparedStatement ps =
        con.prepareStatement(sql)
) {

    // JDBC operations

}
catch (SQLIntegrityConstraintViolationException e) {

    // Specific handling

}
catch (SQLTimeoutException e) {

    // Timeout handling

}
catch (SQLException e) {

    // General JDBC handling

    System.err.println(
        "Message: " + e.getMessage()
    );

    System.err.println(
        "SQLState: " + e.getSQLState()
    );

    System.err.println(
        "ErrorCode: " + e.getErrorCode()
    );
}
```

This combines:

```text
Specific exception handling
        +
General SQLException handling
        +
Diagnostic information
        +
Automatic resource management
```

---

# 16. Deep Dive Summary Table

| Concept                                    | Meaning                                   | Important method/class          |
| ------------------------------------------ | ----------------------------------------- | ------------------------------- |
| `SQLException`                             | Main JDBC SQL exception                   | `catch(SQLException e)`         |
| `getMessage()`                             | Error description                         | `e.getMessage()`                |
| SQLState                                   | Standardized SQL condition classification | `e.getSQLState()`               |
| Error Code                                 | Vendor/driver-specific numeric diagnostic | `e.getErrorCode()`              |
| `getCause()`                               | General Java exception cause              | `e.getCause()`                  |
| `getNextException()`                       | Next JDBC SQL exception in chain          | `e.getNextException()`          |
| `BatchUpdateException`                     | Batch execution failure                   | `catch(BatchUpdateException e)` |
| `SQLTimeoutException`                      | SQL/JDBC operation timeout                | Specific subclass               |
| `SQLSyntaxErrorException`                  | SQL syntax-related failure                | Specific subclass               |
| `SQLIntegrityConstraintViolationException` | Integrity constraint violation            | Specific subclass               |
| `try-with-resources`                       | Automatic resource closing                | `try (...)`                     |
| `commit()`                                 | Commit transaction                        | `con.commit()`                  |
| `rollback()`                               | Roll back transaction                     | `con.rollback()`                |

---

# 🔥 DEEPDIVE MASTER MAP

```text
                 JDBC EXCEPTION HANDLING
                         │
                         ↓
                   SQLException
                         │
          ┌──────────────┼──────────────┐
          ↓              ↓              ↓
       Message        SQLState       ErrorCode
          ↓              ↓              ↓
    Description     Standardized     Vendor/
                    classification    driver
                                      detail
          │              │              │
          └──────────────┼──────────────┘
                         ↓
                   Exception Handling
                         │
            ┌────────────┼────────────┐
            ↓            ↓            ↓
          catch        throws      try-with-
                                  resources
            │
            ↓
      Specific subclasses
            │
      ┌─────┼──────┐
      ↓     ↓      ↓
   Syntax  Timeout  Constraint
```

---

# 🧠 The 7 Rules You Must Never Forget

### Rule 1

```java
SQLException
```

is the primary JDBC exception type.

### Rule 2

```java
e.getMessage()
```

gives the descriptive message.

### Rule 3

```java
e.getSQLState()
```

gives the SQLState classification.

### Rule 4

```java
e.getErrorCode()
```

gives a database/driver-specific numeric diagnostic.

### Rule 5

```java
getCause()
```

and:

```java
getNextException()
```

are **not the same thing**.

### Rule 6

Use specific JDBC exception subclasses before the general:

```java
catch (SQLException e)
```

when specialized handling is useful.

### Rule 7

For JDBC resources, prefer:

```java
try (...) {
    // JDBC code
}
catch (SQLException e) {
    // handle
}
```

so resources are automatically closed.

---

## ⭐ One-line memory formula

```text
SQLException
     =
Exception
+
Message
+
SQLState
+
ErrorCode
+
Possible Exception Chain
```

And the most important distinction:

> **SQLState tells you the standardized category of the SQL condition, while the error code usually gives database/driver-specific diagnostic information. `SQLException` is the Java exception object that carries this information.**
