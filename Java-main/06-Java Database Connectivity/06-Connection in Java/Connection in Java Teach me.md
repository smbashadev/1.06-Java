# 6. Connection in Java / TEACHME

Let's learn `Connection` **from zero**, as if we're building our understanding step by step.

The most important idea is this:

> **`Connection` is the object that represents the communication/session between our Java program and the database.**

Once we have a `Connection`, we can:

* create objects to execute SQL,
* manage transactions,
* commit or undo changes,
* and finally close the database connection.

---

# 1. Connection Interface

## First: What problem does JDBC solve?

Suppose we have:

```text
Java Program
     ↓
Database
```

Our Java program needs some way to communicate with the database.

JDBC provides that communication mechanism.

The basic flow is:

```text
Java Application
       ↓
   JDBC API
       ↓
 JDBC Driver
       ↓
   Database
```

After asking `DriverManager` for a connection:

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

we get a `Connection`.

---

## What is `Connection`?

`Connection` is an interface from:

```java
java.sql.Connection
```

It represents a **database connection/session**.

Think of it like opening a communication channel:

```text
Java Application
       │
       │ Connection
       ▼
   Database
```

Once the connection exists, we can ask it to create objects that execute SQL.

---

## Why is it called an interface?

Because JDBC defines the standard behavior.

We write:

```java
Connection con;
```

rather than depending directly on a particular database implementation.

The JDBC driver provides the actual implementation.

Conceptually:

```text
             JDBC API
                │
                ▼
          Connection
           interface
                ▲
                │
        JDBC Driver
        implementation
                │
                ▼
            Database
```

---

## Can we do this?

```java
Connection con = new Connection();
```

❌ No.

`Connection` is an interface.

Instead:

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

---

# 2. What Can We Do With Connection?

Imagine that we have:

```java
Connection con;
```

Now `Connection` gives us several important capabilities:

```text
Connection
│
├── Create SQL execution objects
│   ├── createStatement()
│   ├── prepareStatement()
│   └── prepareCall()
│
├── Manage transactions
│   ├── setAutoCommit()
│   ├── commit()
│   └── rollback()
│
└── Release resources
    └── close()
```

Let's learn each one.

---

# 3. `createStatement()`

## What is a Statement?

A `Statement` is an object used to execute SQL.

First:

```java
Statement st =
    con.createStatement();
```

Now:

```text
Connection
     ↓
createStatement()
     ↓
Statement
     ↓
SQL
```

---

## Example

Suppose we want to retrieve all students.

SQL:

```sql
SELECT * FROM student;
```

Java:

```java
Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

---

## Understand the roles

Don't confuse these:

```text
Connection
    ↓
creates
    ↓
Statement
    ↓
executes
    ↓
SQL
```

So:

> `Connection` creates the `Statement`; `Statement` executes the SQL.

---

## When is `Statement` useful?

For SQL that doesn't need parameters.

Example:

```java
Statement st =
    con.createStatement();

st.executeUpdate(
    "DELETE FROM student WHERE id = 101"
);
```

---

## But what if the value comes from a user?

Suppose:

```java
int id = 101;
```

We could construct SQL, but for parameterized SQL we should normally use `PreparedStatement`.

That's where the next method becomes important.

---

# 4. `prepareStatement()`

## What is the problem with dynamic SQL?

Suppose we want:

```sql
SELECT * FROM student WHERE id = 101
```

Instead of putting `101` directly into the SQL string, we can use:

```sql
SELECT * FROM student WHERE id = ?
```

The `?` is a **parameter placeholder**.

Java:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

Then:

```java
ps.setInt(1, 101);
```

Finally:

```java
ResultSet rs =
    ps.executeQuery();
```

---

# 5. Let's Understand `PreparedStatement` Slowly

We have:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";
```

Then:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

At this point, we have created a prepared SQL statement.

Then:

```java
ps.setInt(1, 101);
```

means:

```text
First ? → 101
```

Then:

```java
ps.executeQuery();
```

executes it.

Complete:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();
```

---

## Why is this better?

### 1. Parameterization

You don't have to construct SQL by concatenating values.

### 2. SQL injection protection

Parameters are handled separately from the SQL command text.

### 3. Reuse

The same SQL structure can be executed with different parameter values.

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );

ps.setInt(1, 101);
ps.executeQuery();

ps.setInt(1, 102);
ps.executeQuery();
```

---

# 6. Very Important: Parameter Index Starts at 1

This is a common JDBC question.

Suppose:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student " +
        "WHERE id = ? AND name = ?"
    );
```

Then:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

The indexes are:

```text
? #1 → 101
? #2 → Ravi
```

Not:

```java
ps.setInt(0, 101);  // ❌
```

JDBC parameter positions start at **1**.

---

# 7. `prepareCall()`

Now imagine the database itself contains a stored procedure.

For example, conceptually:

```text
getStudent(101)
```

Java can call it using:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

---

## What is `CallableStatement`?

`CallableStatement` is used to call:

* stored procedures
* stored functions

supported by the database and JDBC driver.

Flow:

```text
Connection
     ↓
prepareCall()
     ↓
CallableStatement
     ↓
Stored procedure/function
     ↓
Database
```

---

## Example

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );

cs.setInt(1, 101);

ResultSet rs =
    cs.executeQuery();
```

The exact procedure/function syntax depends on the database.

---

# 8. Compare the Three

This is extremely important.

```text
Connection
│
├── createStatement()
│       ↓
│   Statement
│       ↓
│   Simple SQL
│
├── prepareStatement()
│       ↓
│   PreparedStatement
│       ↓
│   Parameterized SQL
│
└── prepareCall()
        ↓
    CallableStatement
        ↓
    Stored procedure/function
```

### Memory trick

> **Statement → SQL**
> **PreparedStatement → Parameters**
> **CallableStatement → Call**

---

# 9. Now Let's Learn Transactions

The next three methods are related:

```text
setAutoCommit()
       ↓
   SQL operations
       ↓
 ┌─────┴─────┐
 ↓           ↓
commit()   rollback()
```

First, understand what a **transaction** is.

---

# 10. What Is a Transaction?

Suppose we transfer ₹1,000 from Account A to Account B.

We need two operations:

```text
Account A
   ↓
- ₹1,000

Account B
   ↓
+ ₹1,000
```

These two operations logically belong together.

We don't want:

```text
A → ₹1,000 removed
B → ₹1,000 NOT added
```

That would create an incorrect state.

So we group operations into a **transaction**.

```text
Transaction
│
├── Remove ₹1,000 from A
└── Add ₹1,000 to B
```

Then:

```text
Everything successful
        ↓
     commit()
```

If something fails:

```text
Something failed
        ↓
    rollback()
```

---

# 11. `setAutoCommit()`

## What is auto-commit?

A JDBC connection normally starts with auto-commit enabled.

Conceptually:

```java
con.setAutoCommit(true);
```

With auto-commit enabled, successful statements are automatically committed.

Think:

```text
SQL 1
 ↓
Execute
 ↓
Commit

SQL 2
 ↓
Execute
 ↓
Commit
```

---

# 12. `setAutoCommit(false)`

Now suppose we want several operations to belong to one transaction.

We say:

```java
con.setAutoCommit(false);
```

Now we control when the transaction is completed.

Example:

```java
con.setAutoCommit(false);

operation1();
operation2();
operation3();

con.commit();
```

Think:

```text
setAutoCommit(false)
        ↓
     SQL 1
        ↓
     SQL 2
        ↓
     SQL 3
        ↓
     commit()
```

---

# 13. Why Do We Need `setAutoCommit(false)`?

Because sometimes several database operations must succeed together.

Example:

```java
con.setAutoCommit(false);

debitAccount();
creditAccount();

con.commit();
```

The idea is:

```text
Debit
  +
Credit
  ↓
One transaction
```

If the credit operation fails:

```java
con.rollback();
```

---

# 14. `commit()`

Now we reach the actual transaction confirmation.

```java
con.commit();
```

means:

> **Commit the current transaction.**

Example:

```java
con.setAutoCommit(false);

updateStudent1();
updateStudent2();

con.commit();
```

Conceptually:

```text
Changes
   ↓
commit()
   ↓
Transaction confirmed
```

---

# 15. `rollback()`

Suppose:

```java
con.setAutoCommit(false);

operation1();
operation2();
operation3();
```

and operation 3 fails.

We can do:

```java
con.rollback();
```

Conceptually:

```text
Operation 1
Operation 2
Operation 3 ❌
     ↓
 rollback()
     ↓
Uncommitted transaction changes undone
```

---

# 16. Complete Transaction Example

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );

try {

    con.setAutoCommit(false);

    // Operation 1
    // Operation 2

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

Think:

```text
             Connection
                  ↓
       setAutoCommit(false)
                  ↓
          ┌───────┴───────┐
          ↓               ↓
       Operation 1    Operation 2
          └───────┬───────┘
                  ↓
             Everything OK?
               /       \
             YES        NO
              ↓          ↓
           commit()   rollback()
```

---

# 17. Important Difference: `commit()` vs `rollback()`

| `commit()`                                   | `rollback()`                                 |
| -------------------------------------------- | -------------------------------------------- |
| Confirms transaction changes                 | Undoes uncommitted transaction changes       |
| Used when operations succeed                 | Used when something fails                    |
| Makes the transaction complete               | Returns to the transaction's previous state  |
| Usually used with manual transaction control | Usually used with manual transaction control |

Memory trick:

> **Commit = Confirm**
> **Rollback = Undo uncommitted work**

---

# 18. `close()`

We've finished using the database.

What should we do?

```java
con.close();
```

This closes the JDBC connection and releases associated resources.

---

## Why close the connection?

A database connection consumes resources.

Imagine:

```text
Connection 1 → open
Connection 2 → open
Connection 3 → open
Connection 4 → open
...
```

If applications never close connections, eventually available connections/resources can be exhausted.

So:

> **Open what you need, close what you no longer need.**

---

# 19. Does `close()` Shut Down the Database?

❌ No.

This:

```java
con.close();
```

doesn't mean:

```text
Shutdown MySQL
```

It means:

```text
Close my application's JDBC connection
```

Think:

```text
Database
   ↑
   │
Connection ← closed
```

The database continues running.

---

# 20. Modern Way to Close Connection

Instead of manually doing:

```java
Connection con = null;

try {
    con = DriverManager.getConnection(...);

    // work

} finally {
    if (con != null) {
        con.close();
    }
}
```

Java provides **try-with-resources**.

```java
try (
    Connection con =
        DriverManager.getConnection(
            url,
            username,
            password
        )
) {

    // JDBC work

}
```

When the try block ends, the connection is automatically closed.

---

# 21. Complete Beginner Example

Let's put everything together.

```java
import java.sql.*;

public class Demo {

    public static void main(String[] args) {

        String url =
            "jdbc:mysql://localhost:3306/college";

        String username = "root";
        String password = "password";

        try (
            Connection con =
                DriverManager.getConnection(
                    url,
                    username,
                    password
                )
        ) {

            String sql =
                "SELECT * FROM student WHERE id = ?";

            PreparedStatement ps =
                con.prepareStatement(sql);

            ps.setInt(1, 101);

            ResultSet rs =
                ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                    rs.getInt("id")
                );

                System.out.println(
                    rs.getString("name")
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
```

Now look at the flow:

```text
DriverManager
     ↓
getConnection()
     ↓
Connection
     ↓
prepareStatement()
     ↓
PreparedStatement
     ↓
setInt()
     ↓
executeQuery()
     ↓
ResultSet
     ↓
Read data
     ↓
Connection automatically closed
```

---

# 22. Complete Transaction Example

Now let's understand why `Connection` is also called the **transaction control center**.

```java
try (
    Connection con =
        DriverManager.getConnection(
            url,
            username,
            password
        )
) {

    try {

        con.setAutoCommit(false);

        PreparedStatement debit =
            con.prepareStatement(
                "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE id = ?"
            );

        PreparedStatement credit =
            con.prepareStatement(
                "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE id = ?"
            );

        debit.setDouble(1, 1000);
        debit.setInt(2, 101);

        credit.setDouble(1, 1000);
        credit.setInt(2, 102);

        debit.executeUpdate();
        credit.executeUpdate();

        con.commit();

    } catch (SQLException e) {

        con.rollback();
        throw e;
    }
}
```

The important part is:

```text
setAutoCommit(false)
        ↓
Debit
        ↓
Credit
        ↓
   ┌────┴────┐
   ↓         ↓
commit    rollback
```

---

# 23. One Very Important Concept

You might think:

> "If `Connection` is responsible for database communication, why don't I execute SQL directly on `Connection`?"

Because JDBC separates responsibilities.

```text
Connection
     ↓
Creates statement objects
```

Then:

```text
Statement
PreparedStatement
CallableStatement
     ↓
Execute SQL
```

So:

```text
Connection
    │
    ├── creates Statement
    ├── creates PreparedStatement
    ├── creates CallableStatement
    │
    ├── controls transaction
    │     ├── setAutoCommit()
    │     ├── commit()
    │     └── rollback()
    │
    └── closes connection
```

---

# 24. The Three Statement Types — Teacher's Explanation

Imagine you're giving instructions to the database.

### `Statement`

You already know exactly what the SQL should look like:

```text
"Give me all students"
```

```java
Statement st =
    con.createStatement();
```

---

### `PreparedStatement`

You have a fixed SQL template but some values change:

```text
"Give me student whose ID is ___"
```

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

Then:

```java
ps.setInt(1, 101);
```

---

### `CallableStatement`

You want to call a stored routine in the database:

```text
"Database, please execute this stored procedure."
```

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

---

# 25. The Three Transaction Methods — Teacher's Explanation

Imagine a group project.

You have three tasks:

```text
Task A
Task B
Task C
```

You want all three to be considered one unit.

### Step 1

```java
con.setAutoCommit(false);
```

Translation:

> "Don't automatically finalize each operation."

### Step 2

Perform operations.

### Step 3 — Everything worked

```java
con.commit();
```

Translation:

> "Everything is correct. Confirm the transaction."

### Step 3 — Something failed

```java
con.rollback();
```

Translation:

> "Cancel the uncommitted work in this transaction."

---

# 26. Common Student Confusions

## Confusion 1

### Is `Connection` the database?

❌ No.

```text
Database = actual database
Connection = session/communication channel
```

---

## Confusion 2

### Is `Connection` a class?

❌ No.

```java
java.sql.Connection
```

is an interface.

---

## Confusion 3

### Does `Connection` execute SQL?

Not directly.

It creates statement objects that execute SQL.

---

## Confusion 4

### Is `PreparedStatement` only for SELECT?

❌ No.

It can execute parameterized:

```text
SELECT
INSERT
UPDATE
DELETE
```

and other supported SQL operations.

---

## Confusion 5

### Does `commit()` mean saving Java code?

❌ No.

It commits the current database transaction.

---

## Confusion 6

### Does `rollback()` delete the database?

❌ No.

It rolls back uncommitted changes in the current transaction.

---

## Confusion 7

### Does `close()` shut down the database?

❌ No.

It closes the application's JDBC connection.

---

# 27. The Entire Topic in One Picture

```text
                         JAVA APPLICATION
                                │
                                ▼
                       DriverManager
                                │
                         getConnection()
                                │
                                ▼
                         ┌─────────────┐
                         │ Connection  │
                         └─────────────┘
                                │
             ┌──────────────────┼──────────────────┐
             │                  │                  │
             ▼                  ▼                  ▼
      createStatement()  prepareStatement()  prepareCall()
             │                  │                  │
             ▼                  ▼                  ▼
         Statement       PreparedStatement   CallableStatement
             │                  │                  │
             └──────────────────┼──────────────────┘
                                ▼
                           Execute SQL
                                │
                                ▼
                            DATABASE


                  TRANSACTION CONTROL
                         │
                         ▼
                 setAutoCommit(false)
                         │
                         ▼
                   SQL operations
                         │
                    ┌────┴────┐
                    ▼         ▼
                 commit    rollback
                    │         │
                    └────┬────┘
                         ▼
                       close()
```

---

# 28. Final TEACHME Summary

Remember `Connection` using **three jobs**:

## Job 1 — Create SQL execution objects

```java
con.createStatement();
con.prepareStatement(...);
con.prepareCall(...);
```

```text
Connection
    ↓
Statement / PreparedStatement / CallableStatement
```

---

## Job 2 — Control transactions

```java
con.setAutoCommit(false);

con.commit();

con.rollback();
```

```text
Connection
    ↓
Transaction control
```

---

## Job 3 — Manage the connection's lifetime

```java
con.close();
```

```text
Connection
    ↓
close()
    ↓
Resources released
```

---

# 🧠 The Ultimate Memory Trick

Think of a **restaurant**:

```text
Connection = Your table/session
```

At the table:

```text
createStatement()
       ↓
Ask a simple question/order

prepareStatement()
       ↓
Use a fixed order form with blanks

prepareCall()
       ↓
Ask the restaurant to execute a special predefined procedure
```

For the bill/transaction:

```text
setAutoCommit(false)
       ↓
"Wait, don't finalize yet."

commit()
       ↓
"Everything is correct. Finalize it."

rollback()
       ↓
"Something went wrong. Undo the uncommitted work."
```

Finally:

```text
close()
   ↓
Leave the table
```

So the easiest sentence to remember is:

> **Connection creates statement objects, controls transactions, and closes the database session.**
