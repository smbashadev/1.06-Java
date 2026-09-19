# 14. JDBC Exceptions in Java — 3LEVEL

The **3LEVEL method** means we learn every concept at three depths:

```text
LEVEL 1 → Basic understanding
LEVEL 2 → Internal / practical understanding
LEVEL 3 → Interview + coding + doubt-clearing understanding
```

We will cover:

```text
14. JDBC Exceptions
│
├── 1. SQLException
├── 2. SQLState
├── 3. Error Codes
└── 4. Exception Handling
```

---

# 1. SQLException

## LEVEL 1 — Basic

### What is `SQLException`?

`SQLException` is a Java exception used by JDBC to represent problems that occur while performing database-related operations.

It belongs to:

```java
java.sql.SQLException
```

For example:

```java
try {
    Connection con =
        DriverManager.getConnection(url, user, password);
}
catch (SQLException e) {
    System.out.println("Database error");
}
```

If the connection fails, JDBC can throw an `SQLException`.

### Simple meaning

```text
JDBC operation
      ↓
Something goes wrong
      ↓
SQLException
```

---

## LEVEL 2 — Practical

`SQLException` can occur during many JDBC operations:

```text
DriverManager.getConnection()
        ↓
Connection problem

createStatement()
        ↓
Statement creation problem

executeQuery()
        ↓
SELECT/SQL problem

executeUpdate()
        ↓
INSERT/UPDATE/DELETE problem

commit()
        ↓
Transaction problem
```

For example:

```java
try {
    Statement st = con.createStatement();

    ResultSet rs =
        st.executeQuery("SELECT * FROM student");
}
catch (SQLException e) {
    System.out.println(e.getMessage());
}
```

Possible reasons include:

* invalid SQL
* table doesn't exist
* database unavailable
* invalid credentials
* insufficient permissions
* constraint violation
* connection failure
* timeout

---

## LEVEL 3 — Advanced

`SQLException` is a **checked exception**.

Therefore, Java requires you to either:

### Handle it

```java
try {
    // JDBC code
}
catch (SQLException e) {
    // handle
}
```

### Or declare it

```java
void display() throws SQLException {
    // JDBC code
}
```

---

### Important methods of `SQLException`

```java
e.getMessage();
e.getSQLState();
e.getErrorCode();
e.getCause();
e.getNextException();
```

Think:

```text
SQLException
     │
     ├── getMessage()
     ├── getSQLState()
     ├── getErrorCode()
     ├── getCause()
     └── getNextException()
```

---

### Important subclasses

JDBC also defines more specific SQL exception types, for example:

```text
SQLException
    │
    ├── SQLTimeoutException
    ├── SQLSyntaxErrorException
    ├── SQLDataException
    ├── SQLIntegrityConstraintViolationException
    └── BatchUpdateException
```

The exact exception class reported can depend on the JDBC driver and database.

### Key point

> `SQLException` is the general JDBC exception; specialized subclasses can provide more specific information about the failure.

---

# 2. SQLState

## LEVEL 1 — Basic

### What is SQLState?

**SQLState** is a code used to identify the general SQL condition associated with a database error or warning.

You obtain it using:

```java
e.getSQLState();
```

Example:

```java
catch (SQLException e) {

    System.out.println(
        e.getSQLState()
    );
}
```

A SQLState normally contains **five characters**.

Example:

```text
23000
```

---

## LEVEL 2 — Practical

Suppose the database reports an integrity constraint violation.

The SQLState may belong to the:

```text
23xxx
```

class.

So SQLState helps your application understand the **category** of the database condition.

Think:

```text
SQLState
   ↓
General classification
```

Whereas:

```text
getMessage()
   ↓
Human-readable explanation
```

---

## LEVEL 3 — Advanced

A SQLState consists of:

```text
23000
│││││
││└┴┴── subclass
└┴───── class
```

The first two characters identify the **class**.

The last three identify the **subclass**.

Some commonly encountered SQLState classes include:

| Class | General meaning                  |
| ----- | -------------------------------- |
| `00`  | Successful completion            |
| `01`  | Warning                          |
| `02`  | No data                          |
| `08`  | Connection exception             |
| `22`  | Data exception                   |
| `23`  | Integrity constraint violation   |
| `42`  | Syntax/access-rule-related error |

The exact conditions and support can depend on the database and driver.

### SQLState vs `SQLException`

Don't confuse:

```java
SQLException
```

with:

```java
e.getSQLState()
```

They are completely different things.

```text
SQLException
     ↓
Java exception object/type

SQLState
     ↓
Code describing SQL condition
```

---

# 3. Error Codes

## LEVEL 1 — Basic

An **error code** is a numeric code supplied by the database or JDBC driver to provide diagnostic information about an error.

Retrieve it using:

```java
e.getErrorCode();
```

Example:

```java
catch (SQLException e) {

    System.out.println(
        e.getErrorCode()
    );
}
```

---

## LEVEL 2 — Practical

Suppose:

```java
catch (SQLException e) {

    System.out.println(
        "Message = " + e.getMessage()
    );

    System.out.println(
        "SQLState = " + e.getSQLState()
    );

    System.out.println(
        "Error Code = " + e.getErrorCode()
    );
}
```

Now we have three different pieces of information:

```text
Message
   ↓
Human-readable explanation

SQLState
   ↓
General SQL classification

Error Code
   ↓
Database/driver-specific diagnostic
```

---

## LEVEL 3 — Advanced

The major difference is **portability**.

SQLState is designed around a standardized SQL condition classification.

Error codes are generally **vendor-specific**.

For example, conceptually:

```text
Database A
    SQLState → 23000
    ErrorCode → 1234

Database B
    SQLState → 23000
    ErrorCode → 5678
```

The same broad SQL condition can therefore have different vendor-specific error numbers.

### Therefore:

If you write:

```java
if (e.getErrorCode() == 1234) {
    // ...
}
```

your code may become dependent on a particular database/driver.

---

# 4. Exception Handling

## LEVEL 1 — Basic

### What is exception handling?

Exception handling means handling an error instead of allowing it to unexpectedly terminate the application.

JDBC commonly uses:

```java
try {
    // JDBC code
}
catch (SQLException e) {
    // handle database error
}
```

Basic structure:

```text
try
 ↓
JDBC operation
 ↓
Exception?
 ↓
catch
 ↓
Handle exception
```

---

# LEVEL 2 — Practical

Consider:

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
        "Unable to connect to database."
    );
}
```

If the connection succeeds:

```text
try executes normally
```

If it fails:

```text
SQLException
      ↓
catch executes
```

---

## Using exception information

A useful handler is:

```java
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

This is especially useful while debugging JDBC applications.

---

# LEVEL 3 — Advanced

## `try-catch` vs `throws`

There are two common approaches.

### Approach 1 — Handle here

```java
void display() {

    try {
        // JDBC code
    }
    catch (SQLException e) {
        // handle
    }
}
```

### Approach 2 — Pass responsibility to caller

```java
void display() throws SQLException {

    // JDBC code

}
```

Then the caller handles it:

```java
try {
    display();
}
catch (SQLException e) {
    // handle
}
```

Remember:

```text
try-catch
   ↓
I handle the exception here

throws SQLException
   ↓
I tell the caller that this method may throw it
```

---

# 5. Specific Exception Handling

Instead of always catching only:

```java
SQLException
```

you can sometimes catch a more specific JDBC exception.

Example:

```java
try {

    // JDBC operation

}
catch (SQLIntegrityConstraintViolationException e) {

    System.out.println(
        "Constraint violation."
    );

}
catch (SQLException e) {

    System.out.println(
        "Other JDBC error."
    );
}
```

Why this order?

Because:

```text
SQLIntegrityConstraintViolationException
              ↓
         SQLException
```

The specific exception must come first.

### Wrong:

```java
catch (SQLException e) {
}

catch (SQLIntegrityConstraintViolationException e) {
}
```

The second catch becomes unreachable.

### Rule:

> **Specific exception first → general exception later.**

---

# 6. `getMessage()`, `getSQLState()`, `getErrorCode()`

This is one of the most important JDBC concepts.

Suppose:

```java
catch (SQLException e) {

    System.out.println(e.getMessage());
    System.out.println(e.getSQLState());
    System.out.println(e.getErrorCode());

}
```

Understand them like this:

| Method           | Question it answers                                |
| ---------------- | -------------------------------------------------- |
| `getMessage()`   | What happened?                                     |
| `getSQLState()`  | What general SQL condition is this?                |
| `getErrorCode()` | What vendor-specific diagnostic code was reported? |

### Memory trick

```text
MESSAGE
   ↓
What happened?

SQLSTATE
   ↓
What category?

ERROR CODE
   ↓
Which vendor-specific code?
```

---

# 7. `printStackTrace()`

During development/debugging:

```java
catch (SQLException e) {

    e.printStackTrace();

}
```

This prints the exception's stack trace.

It helps identify:

```text
What exception?
     ↓
Where did it occur?
     ↓
Which method?
     ↓
Which line?
```

For production systems, applications normally use a proper logging framework rather than relying on `printStackTrace()`.

---

# 8. `getNextException()`

JDBC can maintain a chain of related `SQLException` objects.

You can access the next exception with:

```java
e.getNextException();
```

Example:

```java
catch (SQLException e) {

    SQLException current = e;

    while (current != null) {

        System.out.println(
            current.getMessage()
        );

        current =
            current.getNextException();
    }
}
```

Conceptually:

```text
SQLException #1
      ↓
SQLException #2
      ↓
SQLException #3
```

This can be useful when the driver/database reports multiple related SQL errors.

---

# 9. `getCause()` vs `getNextException()`

Another common interview doubt.

### `getCause()`

```java
e.getCause();
```

is part of Java's general exception mechanism.

It asks:

```text
What caused this exception?
```

### `getNextException()`

```java
e.getNextException();
```

is JDBC-specific.

It asks:

```text
Is there another SQLException
in this JDBC exception chain?
```

Therefore:

```text
getCause()
    ↓
General Java exception cause

getNextException()
    ↓
JDBC SQLException chain
```

---

# 10. Exception Handling + Resource Management

JDBC uses resources such as:

```text
Connection
Statement
PreparedStatement
ResultSet
```

These should be closed.

The preferred modern approach is **try-with-resources**.

```java
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
            rs.getString("name")
        );
    }

}
catch (SQLException e) {

    System.out.println(
        "Database error: " +
        e.getMessage()
    );
}
```

The resources are automatically closed when the try block finishes.

---

# 11. Exception Handling + Transactions

This is another important JDBC connection.

Suppose:

```java
con.setAutoCommit(false);
```

You perform multiple operations:

```text
Transaction begins
       ↓
INSERT
       ↓
UPDATE
       ↓
DELETE
       ↓
Something fails
```

You can handle the failure with rollback:

```java
try {

    con.setAutoCommit(false);

    ps1.executeUpdate();
    ps2.executeUpdate();
    ps3.executeUpdate();

    con.commit();

}
catch (SQLException e) {

    con.rollback();

}
```

Conceptually:

```text
                    Transaction
                        │
              ┌─────────┴─────────┐
              ↓                   ↓
          Everything            Failure
          succeeds                ↓
              ↓                rollback()
          commit()
```

In production-quality code, rollback itself can also throw `SQLException`, so transaction cleanup should be designed carefully.

---

# 12. Complete Example

```java
import java.sql.*;

public class JdbcExceptionDemo {

    public static void main(String[] args) {

        String url =
            "jdbc:mysql://localhost:3306/college";

        String user = "root";
        String password = "password";

        String sql =
            "SELECT * FROM student";

        try (
            Connection con =
                DriverManager.getConnection(
                    url,
                    user,
                    password
                );

            PreparedStatement ps =
                con.prepareStatement(sql);

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
        }
    }
}
```

The flow is:

```text
Java Program
     ↓
Connection
     ↓
PreparedStatement
     ↓
executeQuery()
     ↓
ResultSet
     ↓
If failure
     ↓
SQLException
     ↓
┌───────────────┬───────────────┬──────────────┐
│               │               │
Message       SQLState       Error Code
│               │               │
What happened? General       Vendor-specific
               category      diagnostic
```

---

# 13. Three-Level Final Comparison

| Concept            | LEVEL 1                   | LEVEL 2                           | LEVEL 3                                                  |
| ------------------ | ------------------------- | --------------------------------- | -------------------------------------------------------- |
| `SQLException`     | JDBC exception            | Represents database/JDBC failures | Checked exception with subclasses and diagnostic methods |
| SQLState           | SQL condition code        | General classification            | Five-character class/subclass scheme                     |
| Error Code         | Numeric error information | Driver/database diagnostic        | Usually vendor-specific; less portable                   |
| Exception Handling | Handle errors             | `try-catch` / `throws`            | Specific catches, logging, rollback, resource cleanup    |

---

# 14. Most Important Interview Doubts

### Q1. Is `SQLException` checked or unchecked?

**Checked exception.**

```java
SQLException extends Exception
```

Therefore it must be handled or declared.

---

### Q2. Is SQLState an exception?

**No.**

```text
SQLException → exception
SQLState     → information/code associated with the exception
```

---

### Q3. Is Error Code universal?

**No.**

It is generally database/driver-specific.

---

### Q4. Which is more portable: SQLState or Error Code?

Generally **SQLState**, because it follows standardized SQL condition classifications, whereas vendor error codes are database-specific.

---

### Q5. What does `getMessage()` give?

A human-readable description of the error.

---

### Q6. What does `getSQLState()` give?

The SQLState associated with the condition.

---

### Q7. What does `getErrorCode()` give?

The vendor/database-specific numeric error code.

---

### Q8. Can I use `throws SQLException` instead of `try-catch`?

Yes.

```java
void method() throws SQLException {
    // JDBC code
}
```

But eventually some caller must handle the checked exception or propagate it further.

---

### Q9. Why use try-with-resources?

To automatically close JDBC resources such as:

```text
Connection
Statement
PreparedStatement
ResultSet
```

---

### Q10. What is the difference between `getCause()` and `getNextException()`?

```text
getCause()
    → general Java exception cause

getNextException()
    → next SQLException in JDBC's exception chain
```

---

# 🧠 3LEVEL MASTER MEMORY MAP

```text
                    JDBC EXCEPTIONS
                          │
              ┌───────────┼───────────┐
              │           │           │
              ↓           ↓           ↓
        SQLException   SQLState   Error Code
              │           │           │
              │           │           ├── Numeric
              │           │           └── Usually vendor-specific
              │           │
              │           ├── SQL condition classification
              │           └── Usually 5 characters
              │
              ├── getMessage()
              ├── getSQLState()
              ├── getErrorCode()
              ├── getCause()
              └── getNextException()
                          │
                          ↓
                  EXCEPTION HANDLING
                          │
                ┌─────────┴─────────┐
                ↓                   ↓
             try-catch            throws
                │
                ↓
        Handle / log / recover
                │
          ┌─────┴─────┐
          ↓           ↓
      rollback    close resources
```

## One-line memory rule

> **`SQLException` = failure, `getMessage()` = explanation, `SQLState` = standardized condition category, `ErrorCode` = vendor-specific diagnostic, and exception handling = deciding what the Java program does after the failure.**
