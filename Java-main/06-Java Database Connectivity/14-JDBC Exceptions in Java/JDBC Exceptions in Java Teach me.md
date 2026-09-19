# 14. JDBC Exceptions in Java — TEACHME

Let's learn **JDBC Exceptions** from zero, as if you're seeing the concept for the first time.

The goal is not just to memorize `SQLException`. You should understand:

```text
What failed?
      ↓
Which exception represents it?
      ↓
What does SQLState tell me?
      ↓
What does Error Code tell me?
      ↓
How should I handle it?
```

---

# 1. First Understand the Problem

Imagine a Java program wants to retrieve students:

```java
Connection con =
    DriverManager.getConnection(url, user, password);

Statement st = con.createStatement();

ResultSet rs =
    st.executeQuery("SELECT * FROM student");
```

Normally:

```text
Java Program
     ↓
JDBC
     ↓
Driver
     ↓
Database
     ↓
Result
     ↓
Java Program
```

But what if:

* the database is not running?
* username/password is wrong?
* the URL is wrong?
* the table doesn't exist?
* the SQL contains a mistake?
* a duplicate key is inserted?
* the connection times out?

Something has to tell Java:

> "The database operation failed."

That something is primarily **`SQLException`**.

---

# 2. `SQLException`

## 2.1 What is `SQLException`?

`SQLException` is a Java exception used by JDBC to represent errors that occur during database operations.

It belongs to:

```java
java.sql.SQLException
```

Think of it simply as:

```text
Database/JDBC operation failed
            ↓
       SQLException
```

---

# 2.2 Simple Example

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

    System.out.println("Database error!");

}
```

If the connection fails, JDBC can throw an `SQLException`.

---

# 2.3 Why does Java need `SQLException`?

Suppose you execute:

```java
st.executeQuery(
    "SELECT * FROM student"
);
```

The database could respond:

```text
Table doesn't exist
```

or:

```text
Connection lost
```

or:

```text
Permission denied
```

or:

```text
SQL syntax error
```

Java needs a standard way to receive these failures.

Therefore JDBC uses:

```text
SQLException
```

---

# 2.4 Is `SQLException` a checked exception?

**Yes.**

This is important.

Because `SQLException` is a checked exception, Java requires you to either:

### Handle it

```java
try {
    // JDBC operation
}
catch (SQLException e) {
    // handling
}
```

or declare it:

```java
void getData() throws SQLException {
    // JDBC operation
}
```

---

# 2.5 `SQLException` hierarchy

At a simplified level:

```text
Throwable
   ↓
Exception
   ↓
SQLException
   ↓
Specific JDBC exceptions
```

Examples include:

```text
SQLException
   │
   ├── SQLTimeoutException
   ├── SQLSyntaxErrorException
   ├── SQLDataException
   └── SQLIntegrityConstraintViolationException
```

The exact exception subclass reported depends on the JDBC driver and database.

---

# 2.6 Example: Constraint Violation

Suppose:

```sql
id = 101
```

is already present in a column having a unique constraint.

You try:

```sql
INSERT INTO student VALUES (101, 'John');
```

The database rejects it.

A JDBC driver may report:

```java
SQLIntegrityConstraintViolationException
```

which is a specialized JDBC exception.

So instead of always thinking:

```text
Everything = SQLException
```

think:

```text
SQLException
     ↓
General JDBC SQL problem

Specific subclasses
     ↓
More specific categories
```

---

# 3. What Information Does `SQLException` Give Us?

This is where JDBC exceptions become really useful.

Suppose:

```java
catch (SQLException e) {
}
```

The object `e` contains diagnostic information.

We can ask it:

```java
e.getMessage();
e.getSQLState();
e.getErrorCode();
```

So:

```text
                 SQLException
                      │
          ┌───────────┼───────────┐
          ↓           ↓           ↓
       Message     SQLState    Error Code
```

Let's understand each individually.

---

# 4. `getMessage()`

Suppose:

```java
catch (SQLException e) {

    System.out.println(
        e.getMessage()
    );
}
```

`getMessage()` gives a human-readable description of the problem.

For example, conceptually:

```text
Table 'college.student' doesn't exist
```

So remember:

```text
getMessage()
     ↓
"What happened?"
```

---

# 5. SQLState

Now we reach the second major concept.

## 5.1 What is SQLState?

**SQLState** is a five-character code representing the general SQL condition associated with an error or warning.

You obtain it using:

```java
e.getSQLState();
```

Example:

```java
catch (SQLException e) {

    System.out.println(
        "SQLState = " +
        e.getSQLState()
    );
}
```

It generally looks like:

```text
XXXXX
```

For example:

```text
23000
```

---

# 5.2 Why do we need SQLState?

Imagine two database vendors.

They may use different internal error numbers:

```text
Database A
Error Code → 1234

Database B
Error Code → 5678
```

If your Java program depends heavily on those numbers, it becomes database-specific.

SQLState provides a more standardized classification.

Think:

```text
SQLState
   ↓
"What general kind of SQL condition occurred?"
```

---

# 5.3 SQLState has classes

The first two characters generally represent the **class** of the condition.

The remaining three provide a more specific subclass/condition.

Conceptually:

```text
23000
││
│└──────── condition/subclass
└───────── class
```

For example:

```text
23xxx
```

represents the class associated with **integrity constraint violations**.

---

# 5.4 Don't confuse SQLState with SQLException

This is a very common beginner mistake.

These are NOT the same:

```java
SQLException
```

and:

```java
e.getSQLState()
```

The first is a **Java exception object/type**.

The second gives a **code describing the SQL condition**.

Think:

```text
SQLException
     ↓
The exception

SQLState
     ↓
Information about the SQL condition
```

---

# 6. Error Codes

Now let's understand the third concept.

## 6.1 What is an Error Code?

A JDBC `SQLException` can contain a numeric error code supplied by the database/driver.

Retrieve it using:

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

# 6.2 Is an Error Code universal?

**No.**

This is extremely important.

Error codes are generally **database/vendor-specific**.

For example, one database might use:

```text
Error Code → 123
```

for a particular condition, while another database might use a completely different number.

Therefore:

```text
Error Code
    ↓
Vendor-specific information
```

---

# 6.3 SQLState vs Error Code

Let's make this crystal clear.

| SQLState                   | Error Code                           |
| -------------------------- | ------------------------------------ |
| Usually five characters    | Numeric                              |
| More standardized          | Usually vendor-specific              |
| General SQL classification | Specific database/driver information |
| `getSQLState()`            | `getErrorCode()`                     |

Remember:

```text
SQLState
   ↓
"What category of SQL problem?"

Error Code
   ↓
"What vendor-specific error was reported?"
```

---

# 7. Put Everything Together

Suppose:

```java
try {

    Statement st =
        con.createStatement();

    st.executeQuery(
        "SELECT * FROM student"
    );

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
```

Think of the output as:

```text
Message:
What happened?

SQLState:
What general SQL condition occurred?

Error Code:
What database/driver-specific diagnostic was reported?
```

---

# 8. Exception Handling

Now let's learn the fourth major topic.

## 8.1 What is Exception Handling?

Exception handling means writing code that deals with errors instead of allowing them to unexpectedly terminate the program.

Without handling:

```text
JDBC operation
      ↓
Exception
      ↓
Program may terminate
```

With handling:

```text
JDBC operation
      ↓
Exception
      ↓
catch
      ↓
Handle / log / recover / report
```

---

# 8.2 Basic JDBC Exception Handling

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
        "Database operation failed."
    );
}
```

The JDBC operation is inside `try`.

The exception is handled inside `catch`.

---

# 8.3 `try-catch` Structure

General Java syntax:

```java
try {

    // risky code

}
catch (ExceptionType e) {

    // handling code

}
```

JDBC:

```java
try {

    // JDBC operation

}
catch (SQLException e) {

    // handle database error

}
```

---

# 9. `throws SQLException`

There is another way.

Instead of handling the exception inside the method, you can pass responsibility to the caller.

```java
void insertStudent()
        throws SQLException {

    Connection con =
        DriverManager.getConnection(
            url,
            user,
            password
        );
}
```

This means:

> "This method may throw `SQLException`; whoever calls me must handle or further declare it."

So:

```text
try-catch
    ↓
Handle here

throws SQLException
    ↓
Let caller handle it
```

---

# 10. `catch(SQLException e)` Explained

Look at:

```java
catch (SQLException e)
```

There are two important parts:

```text
SQLException
     ↓
Type of exception

e
 ↓
Reference to exception object
```

So inside the catch:

```java
e.getMessage();
e.getSQLState();
e.getErrorCode();
```

are asking the exception object for information.

---

# 11. Specific Exception vs General Exception

Suppose a constraint violation occurs.

You can potentially catch:

```java
SQLIntegrityConstraintViolationException
```

instead of only:

```java
SQLException
```

Example:

```java
try {

    // JDBC operation

}
catch (SQLIntegrityConstraintViolationException e) {

    System.out.println(
        "Constraint violation!"
    );

}
catch (SQLException e) {

    System.out.println(
        "Other JDBC error!"
    );
}
```

---

# 12. Why Is the Order Important?

This is very important for Java exception handling.

A specialized exception is also an `SQLException`.

Therefore:

```text
SQLIntegrityConstraintViolationException
              ↓
         SQLException
```

So this is correct:

```java
catch (SpecificException e) {
}

catch (SQLException e) {
}
```

But this is wrong:

```java
catch (SQLException e) {
}

catch (SpecificException e) {
}
```

because the first catch would already catch the specific exception.

Remember:

> **Specific first, general later.**

---

# 13. `printStackTrace()`

For debugging, you can write:

```java
catch (SQLException e) {

    e.printStackTrace();

}
```

This prints information about where the exception occurred.

For learning and debugging, this is extremely useful.

In production applications, proper application logging is generally preferable.

---

# 14. `SQLException` Chaining

Sometimes JDBC can provide more than one related SQL exception.

Example:

```text
SQLException #1
      ↓
SQLException #2
      ↓
SQLException #3
```

You can access the next SQL exception using:

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

---

# 15. `getCause()` vs `getNextException()`

These are easy to confuse.

## `getCause()`

General Java exception mechanism:

```java
e.getCause();
```

means:

> What exception caused this exception?

## `getNextException()`

JDBC-specific exception chain:

```java
e.getNextException();
```

means:

> Is there another related `SQLException`?

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

# 16. Try-With-Resources

JDBC uses resources:

```text
Connection
Statement
PreparedStatement
ResultSet
```

These resources should be closed.

Instead of manually closing them, Java provides **try-with-resources**.

Example:

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

    e.printStackTrace();
}
```

When the try block finishes, the resources are automatically closed.

---

# 17. Why Should Resources Be Closed?

Imagine:

```text
Program
  ↓
Connection opened
  ↓
Connection not closed
  ↓
Another connection
  ↓
Another connection
  ↓
Another connection
  ↓
...
```

Eventually the application can run out of available database resources.

Therefore:

```text
Open
 ↓
Use
 ↓
Close
```

Try-with-resources helps automate the final step.

---

# 18. JDBC Exception Handling with Transactions

Suppose:

```java
con.setAutoCommit(false);
```

Now imagine:

```java
try {

    ps1.executeUpdate();

    ps2.executeUpdate();

    con.commit();

}
catch (SQLException e) {

    con.rollback();

}
```

The idea is:

```text
Start transaction
      ↓
Operation 1
      ↓
Operation 2
      ↓
Everything successful?
    ↙       ↘
  YES        NO
   ↓          ↓
commit()   rollback()
```

If something fails before the commit, rollback can undo the transaction's uncommitted changes, subject to the database/transaction semantics.

---

# 19. Example: Complete JDBC Exception Handling

```java
import java.sql.*;

public class Demo {

    public static void main(String[] args) {

        String url =
            "jdbc:mysql://localhost:3306/college";

        String user = "root";
        String password = "password";

        try (
            Connection con =
                DriverManager.getConnection(
                    url,
                    user,
                    password
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
        }
    }
}
```

This single example demonstrates:

```text
Connection
     ↓
PreparedStatement
     ↓
ResultSet
     ↓
SQLException
     ↓
Message
SQLState
Error Code
```

---

# 20. Let's Learn Through a Real-Life Analogy

Imagine you order food from a restaurant.

```text
You
 ↓
Restaurant system
 ↓
Kitchen
```

Something goes wrong.

### Message

The restaurant says:

> "Your order could not be prepared because the item is unavailable."

That's like:

```java
e.getMessage()
```

It tells you **what happened**.

---

### SQLState

The restaurant categorizes the problem:

```text
ITEM_UNAVAILABLE
```

That's conceptually like:

```java
e.getSQLState()
```

It gives a standardized/general classification.

---

### Error Code

The restaurant internally assigns:

```text
Error Code: 4582
```

That's like:

```java
e.getErrorCode()
```

It gives a more specific internal/vendor diagnostic.

---

### Exception

The entire failure notification is:

```text
SQLException
```

So:

```text
SQLException
    │
    ├── Message → explanation
    ├── SQLState → classification
    └── Error Code → specific diagnostic
```

---

# 21. One More Important Distinction

Don't say:

> "SQLState handles the exception."

Incorrect.

Don't say:

> "Error code is the exception."

Incorrect.

Correct:

```text
SQLException
     ↓
Exception object
     │
     ├── Message
     ├── SQLState
     └── Error Code
```

And your program handles the exception with:

```java
catch (SQLException e)
```

---

# 22. Common JDBC Problems and Their Handling

| Problem                     | Possible JDBC representation                                         |
| --------------------------- | -------------------------------------------------------------------- |
| Database connection failure | `SQLException` / specific subclass                                   |
| Invalid SQL                 | `SQLSyntaxErrorException` or another `SQLException`                  |
| Constraint violation        | `SQLIntegrityConstraintViolationException` or another `SQLException` |
| Timeout                     | `SQLTimeoutException` or another `SQLException`                      |
| Batch failure               | `BatchUpdateException`                                               |
| Other database error        | `SQLException`                                                       |

The exact exception type depends on the driver and database behavior.

---

# 23. The Complete Mental Model

Whenever you see:

```java
catch (SQLException e)
```

think:

```text
                    SQLException
                         │
             "A JDBC/database
               operation failed"
                         │
          ┌──────────────┼──────────────┐
          ↓              ↓              ↓
      getMessage()   getSQLState()  getErrorCode()
          ↓              ↓              ↓
      What happened?  General       Vendor-specific
                      category        information
```

Then ask:

```text
How should I handle it?
        ↓
catch?
        ↓
specific exception?
        ↓
rollback?
        ↓
logging?
        ↓
resource cleanup?
```

---

# 24. TEACHME Final Revision

## `SQLException`

```text
Main JDBC exception
        ↓
Represents database/JDBC failures
```

Example:

```java
catch (SQLException e) {
}
```

---

## SQLState

```text
Five-character SQL condition code
        ↓
General/standardized classification
```

Retrieve:

```java
e.getSQLState();
```

---

## Error Code

```text
Numeric diagnostic information
        ↓
Usually database/driver-specific
```

Retrieve:

```java
e.getErrorCode();
```

---

## Exception Handling

```text
JDBC operation
      ↓
try
      ↓
Exception?
      ↓
catch(SQLException)
      ↓
Handle / log / recover
```

---

# 🧠 Final Memory Trick

Remember these four questions:

### 1. **What failed?**

```java
SQLException
```

### 2. **What happened?**

```java
e.getMessage()
```

### 3. **What general SQL condition is it?**

```java
e.getSQLState()
```

### 4. **What database-specific diagnostic was reported?**

```java
e.getErrorCode()
```

Then:

```text
                 JDBC ERROR
                     ↓
              SQLException
                     ↓
        ┌────────────┼────────────┐
        ↓            ↓            ↓
     Message      SQLState     ErrorCode
        ↓            ↓            ↓
    What happened?  Category    Vendor detail
                     ↓
                Exception
                 Handling
                     ↓
              catch / throws
                     ↓
          log / recover / rollback
                     ↓
            close resources
```

> **The simplest way to remember JDBC exceptions is: `SQLException` is the failure object, `getMessage()` explains it, `getSQLState()` classifies it, `getErrorCode()` gives vendor-specific detail, and exception handling decides what your Java program should do about it.**
