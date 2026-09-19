# 6. Connection in Java — DOUBTKILLER

This version is designed to **kill the common doubts, traps, contradictions, and interview questions** around `Connection`.

---

# 1. `Connection` Interface

## What exactly is `Connection`?

`Connection` is an interface in:

```java
java.sql.Connection
```

It represents a **session/connection between a Java application and a database**.

Example:

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

Think:

```text
Java Program
     │
     │ Connection
     ▼
 JDBC Driver
     │
     ▼
 Database
```

---

## ❓ DOUBT: Is `Connection` a class?

**No.**

```java
Connection
```

is an interface.

Therefore:

```java
Connection con = new Connection();
```

❌ Invalid.

The JDBC driver provides an implementation of the interface.

---

## ❓ DOUBT: Is `Connection` the database?

**No.**

| Object       | Meaning                                 |
| ------------ | --------------------------------------- |
| Database     | Where data is stored                    |
| JDBC Driver  | Communicates with a particular database |
| `Connection` | Represents a database session           |
| `Statement`  | Executes SQL                            |

So:

```text
Connection ≠ Database
Connection ≠ Driver
Connection ≠ Statement
```

---

## ❓ DOUBT: What is the main purpose of `Connection`?

It has three major responsibilities:

```text
Connection
│
├── Create SQL execution objects
│   ├── createStatement()
│   ├── prepareStatement()
│   └── prepareCall()
│
├── Transaction management
│   ├── setAutoCommit()
│   ├── commit()
│   └── rollback()
│
└── Resource management
    └── close()
```

### 🔥 Memory line

> **Connection creates, controls, and closes.**

It **creates** statement objects, **controls** transactions, and **closes** the database session.

---

# 2. `createStatement()`

## What does it do?

```java
Statement st =
    con.createStatement();
```

It creates a `Statement` object.

Then:

```java
st.executeQuery(
    "SELECT * FROM student"
);
```

Flow:

```text
Connection
    ↓
createStatement()
    ↓
Statement
    ↓
execute SQL
```

---

## ❓ DOUBT: Does `createStatement()` execute SQL?

**No.**

This:

```java
con.createStatement();
```

only creates the `Statement`.

This:

```java
st.executeQuery("SELECT * FROM student");
```

executes the SQL.

### Remember:

```text
createStatement() → creates
executeQuery()    → executes
```

---

## ❓ DOUBT: Can `Statement` execute SELECT?

Yes.

```java
Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

---

## ❓ DOUBT: Can `Statement` execute INSERT/UPDATE/DELETE?

Yes.

Usually:

```java
st.executeUpdate(...);
```

Example:

```java
int count =
    st.executeUpdate(
        "UPDATE student SET marks = 90"
    );
```

---

## ❓ DOUBT: Should I always use `Statement`?

**No.**

If SQL contains values coming from variables/users, `PreparedStatement` is generally preferable.

Don't build SQL by concatenating untrusted input:

```java
String sql =
    "SELECT * FROM student WHERE name = '"
    + name + "'";
```

Prefer:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE name = ?"
    );

ps.setString(1, name);
```

---

# 3. `prepareStatement()`

## What does it do?

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

It creates a `PreparedStatement`.

The `?` is a **parameter placeholder**.

Then:

```java
ps.setInt(1, 101);
```

Then:

```java
ResultSet rs =
    ps.executeQuery();
```

---

# ❓ DOUBT: Why is it called "Prepared"?

Because you provide a SQL statement template and parameters separately.

```text
SQL:
SELECT * FROM student WHERE id = ?

Parameter:
101
```

Conceptually:

```text
SQL template + parameter
          ↓
PreparedStatement
          ↓
Execution
```

---

## ❓ DOUBT: Does `?` mean any SQL?

No.

`?` is for **parameter values**, not arbitrary SQL syntax.

Good:

```sql
SELECT * FROM student WHERE id = ?
```

Then:

```java
ps.setInt(1, 101);
```

You cannot safely use:

```sql
SELECT * FROM ? 
```

to replace a table name with a normal parameter.

Parameters represent values, not arbitrary SQL identifiers or SQL fragments.

---

## ❓ DOUBT: Does parameter indexing start from 0?

**No.**

It starts from **1**.

```java
ps.setInt(1, 101);
```

Correct.

```java
ps.setInt(0, 101);
```

❌ Wrong.

If there are three placeholders:

```sql
WHERE id = ? AND age = ? AND name = ?
```

then:

```text
? #1 → index 1
? #2 → index 2
? #3 → index 3
```

---

## ❓ DOUBT: Is `PreparedStatement` only for SELECT?

**No.**

It can be used for:

```text
SELECT
INSERT
UPDATE
DELETE
```

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name) VALUES (?, ?)"
    );

ps.setInt(1, 101);
ps.setString(2, "Ravi");

ps.executeUpdate();
```

---

## ❓ DOUBT: Why prefer `PreparedStatement`?

Three major reasons:

### 1. Parameterization

```java
WHERE id = ?
```

### 2. Safer handling of untrusted input

It helps prevent SQL injection when used correctly.

### 3. Reuse

```java
PreparedStatement ps =
    con.prepareStatement(
        "UPDATE student SET marks = ? WHERE id = ?"
    );

ps.setInt(1, 90);
ps.setInt(2, 101);
ps.executeUpdate();

ps.setInt(1, 95);
ps.setInt(2, 102);
ps.executeUpdate();
```

---

# 4. `prepareCall()`

## What does it do?

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

It creates a `CallableStatement`.

It is used to call **stored procedures/functions**, according to the database and JDBC driver's supported syntax.

---

## ❓ DOUBT: Is `prepareCall()` used for normal SQL?

Generally, **no**.

Think:

```text
createStatement()
      ↓
ordinary SQL

prepareStatement()
      ↓
parameterized SQL

prepareCall()
      ↓
stored procedure/function
```

---

## ❓ DOUBT: What is a stored procedure?

A stored procedure is a routine stored inside the database.

Conceptually:

```text
Database
│
└── getStudent(id)
```

Java can call it:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

---

## ❓ DOUBT: What are IN and OUT parameters?

A stored routine can have:

```text
IN
OUT
INOUT
```

### IN

Java sends a value to the database.

```java
cs.setInt(1, 101);
```

### OUT

Database sends a value back to Java.

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

Then:

```java
String result =
    cs.getString(2);
```

### INOUT

A value goes in and can come back modified.

---

# 5. `commit()`

## What does `commit()` mean?

```java
con.commit();
```

It confirms the changes made in the **current transaction**.

Typical pattern:

```java
con.setAutoCommit(false);

operation1();
operation2();

con.commit();
```

---

## ❓ DOUBT: Does `commit()` save a Java file?

**No.**

It has nothing to do with saving Java source code.

It commits a **database transaction**.

---

## ❓ DOUBT: Is `commit()` required after every SQL statement?

**No.**

It depends on transaction mode.

With auto-commit enabled, successful statements are generally committed automatically.

With:

```java
con.setAutoCommit(false);
```

you normally explicitly commit:

```java
con.commit();
```

---

## Example

```java
con.setAutoCommit(false);

PreparedStatement ps =
    con.prepareStatement(
        "UPDATE account " +
        "SET balance = balance - ? " +
        "WHERE id = ?"
    );

ps.setDouble(1, 1000);
ps.setInt(2, 101);

ps.executeUpdate();

con.commit();
```

---

# 6. `rollback()`

## What does `rollback()` mean?

```java
con.rollback();
```

It rolls back **uncommitted changes in the current transaction**.

Example:

```java
con.setAutoCommit(false);

try {

    operation1();
    operation2();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

---

## ❓ DOUBT: Does rollback undo everything ever done?

**No.**

It does not mean:

```text
Delete database ❌
Delete table ❌
Delete all records ❌
Undo committed transactions ❌
```

It concerns the current transaction's uncommitted work.

---

## Example

Imagine:

```text
Transaction
│
├── UPDATE A ✓
├── UPDATE B ✓
└── UPDATE C ✗
```

Then:

```java
rollback();
```

Conceptually:

```text
UPDATE A
UPDATE B
    ↓
rollback()
    ↓
Uncommitted changes undone
```

---

## ❓ DOUBT: Can rollback undo a committed transaction?

Normally, **no**.

Once:

```java
con.commit();
```

has successfully committed the transaction, calling:

```java
con.rollback();
```

does not reverse that already-committed transaction.

If you need to reverse a committed business operation, you generally perform another database operation representing the reversal.

---

# 7. `setAutoCommit()`

This is one of the **most important JDBC concepts**.

---

## What is auto-commit?

A `Connection` normally starts with auto-commit enabled.

You can explicitly set:

```java
con.setAutoCommit(true);
```

or:

```java
con.setAutoCommit(false);
```

---

# `setAutoCommit(true)`

Conceptually:

```text
SQL 1 → automatically committed
SQL 2 → automatically committed
SQL 3 → automatically committed
```

So you don't normally call:

```java
con.commit();
```

after each individual operation.

---

# `setAutoCommit(false)`

Now you control transaction boundaries.

```java
con.setAutoCommit(false);

operation1();
operation2();
operation3();

con.commit();
```

or:

```java
con.rollback();
```

---

## ❓ DOUBT: Why turn auto-commit off?

Because multiple operations may need to succeed or fail **as one transaction**.

Classic example:

```text
Transfer ₹1,000

Account A
   ↓
- ₹1,000

Account B
   ↓
+ ₹1,000
```

We don't want:

```text
A = -₹1,000
B = FAILED
```

Instead:

```text
Debit
  +
Credit
  ↓
ONE TRANSACTION
```

Java:

```java
con.setAutoCommit(false);

try {

    debit();
    credit();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

---

# ❓ DOUBT: What happens when `setAutoCommit(false)` is called?

It switches the connection to manual transaction control.

You can then explicitly decide:

```text
Success → commit()
Failure → rollback()
```

---

# ❓ DOUBT: What happens if I call `setAutoCommit(true)` while a transaction is active?

This is an important JDBC detail.

Changing auto-commit from `false` to `true` **commits the current transaction**.

Therefore, don't casually toggle auto-commit in the middle of a transaction.

---

# 8. `close()`

## What does `close()` do?

```java
con.close();
```

It closes the JDBC connection and releases associated resources.

---

## ❓ DOUBT: Does it shut down MySQL/Oracle/PostgreSQL?

**No.**

It closes **your application's connection**.

```text
Database Server
      ↑
      │
      X ← your Connection closed
```

The database server continues running.

---

## ❓ DOUBT: Can I use the connection after `close()`?

No.

```java
con.close();

con.createStatement();
```

❌ The connection is closed, so using it afterward results in a `SQLException`.

---

## ❓ DOUBT: Should I always manually call `close()`?

You need to ensure resources are closed, but modern Java strongly favors **try-with-resources**.

Example:

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

At the end of the try block, the connection is automatically closed.

---

# 🔥 BIG DOUBTKILLER: `close()` and Transactions

Suppose:

```java
con.setAutoCommit(false);

update1();
update2();

con.close();
```

Did we explicitly commit?

**No.**

You should never depend on closing a connection as your transaction-management strategy.

If the transaction should succeed:

```java
con.commit();
```

If it should be discarded:

```java
con.rollback();
```

Then close.

Correct mental model:

```text
Work
 ↓
Commit / Rollback
 ↓
Close
```

---

# 🔥 BIG DOUBTKILLER: `Statement` vs `PreparedStatement` vs `CallableStatement`

|                | `Statement`                   | `PreparedStatement`         | `CallableStatement`             |
| -------------- | ----------------------------- | --------------------------- | ------------------------------- |
| Created by     | `createStatement()`           | `prepareStatement()`        | `prepareCall()`                 |
| Main use       | SQL                           | Parameterized SQL           | Stored routines                 |
| `?` parameters | Not the normal mechanism      | Yes                         | Yes                             |
| Parameters     | Manually embedded in SQL text | Setter methods              | IN/OUT/INOUT                    |
| Typical use    | Simple/static SQL             | Application SQL with values | Stored procedure/function calls |

### Ultimate memory:

```text
Statement
    ↓
"Execute this SQL."

PreparedStatement
    ↓
"Execute this SQL template with these values."

CallableStatement
    ↓
"Call this stored routine."
```

---

# 🔥 BIG DOUBTKILLER: `commit()` vs `rollback()`

Imagine:

```text
BEGIN TRANSACTION
      ↓
   Operation 1
      ↓
   Operation 2
      ↓
   Operation 3
      ↓
   Decision
    /     \
   /       \
Success    Failure
  ↓           ↓
commit()   rollback()
```

### `commit()`

```text
"Confirm the transaction."
```

### `rollback()`

```text
"Undo the uncommitted transaction changes."
```

---

# 🔥 BIG DOUBTKILLER: `setAutoCommit()` vs `commit()`

These are **not the same thing**.

### `setAutoCommit(false)`

Controls **how transactions are handled**.

```java
con.setAutoCommit(false);
```

### `commit()`

Actually **commits the current transaction**.

```java
con.commit();
```

So:

```text
setAutoCommit(false)
        ↓
"I will control the transaction."

commit()
        ↓
"I am confirming this transaction."
```

---

# 🔥 BIG DOUBTKILLER: `Connection` vs `Statement`

This is a very common interview question.

### `Connection`

Represents the database session and provides transaction management and statement creation.

### `Statement`

Represents an SQL execution object.

```text
Connection
     │
     ├── creates Statement
     │
     └── manages transaction
```

So don't say:

> "`Connection` executes SQL."

A better answer is:

> **`Connection` provides the database session and creates statement objects through which SQL is executed.**

---

# 🔥 BIG DOUBTKILLER: Who Does What?

```text
DriverManager
     │
     │ getConnection()
     ▼
Connection
     │
     ├───────────────┬──────────────────┐
     ▼               ▼                  ▼
Statement     PreparedStatement   CallableStatement
     │               │                  │
     └───────────────┴──────────────────┘
                     ↓
                  Execute
                     ↓
                  Database
```

And independently:

```text
Connection
     │
     ├── setAutoCommit()
     ├── commit()
     ├── rollback()
     └── close()
```

---

# 🔥 BIG DOUBTKILLER: Complete JDBC Example

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

            con.setAutoCommit(false);

            try {

                PreparedStatement ps =
                    con.prepareStatement(
                        "UPDATE student " +
                        "SET marks = ? " +
                        "WHERE id = ?"
                    );

                ps.setInt(1, 90);
                ps.setInt(2, 101);

                ps.executeUpdate();

                con.commit();

            } catch (SQLException e) {

                con.rollback();
                throw e;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
```

Understand the order:

```text
1. getConnection()
       ↓
2. Connection created
       ↓
3. setAutoCommit(false)
       ↓
4. prepareStatement()
       ↓
5. Set parameters
       ↓
6. Execute SQL
       ↓
7. commit()
       ↓
8. Connection automatically closed
```

If something fails:

```text
Execute SQL
    ↓
Exception
    ↓
rollback()
    ↓
Connection closes
```

---

# 🚨 15 Most Important Doubts — Rapid Fire

### 1. Is `Connection` a class?

**No. It is an interface.**

### 2. Can we use `new Connection()`?

**No.**

### 3. Does `Connection` equal the database?

**No. It represents a database session/connection.**

### 4. Does `createStatement()` execute SQL?

**No. It creates a `Statement`.**

### 5. Does `Statement` execute SQL?

**Yes.**

### 6. Why use `PreparedStatement`?

**For parameterized SQL and safer handling of values.**

### 7. Does `?` indexing start at 0?

**No. It starts at 1.**

### 8. Is `PreparedStatement` only for SELECT?

**No. It can execute parameterized INSERT, UPDATE, DELETE, SELECT, etc.**

### 9. What does `prepareCall()` do?

**Creates a `CallableStatement` for calling stored procedures/functions.**

### 10. Does `commit()` execute SQL?

**No. It commits the current transaction.**

### 11. Does `rollback()` delete the database?

**No. It rolls back uncommitted transaction changes.**

### 12. Does rollback undo a successfully committed transaction?

**Normally, no.**

### 13. What does `setAutoCommit(false)` mean?

**The application takes manual control of transaction completion.**

### 14. Does `close()` shut down the database server?

**No. It closes the application's connection.**

### 15. What is the preferred way to close JDBC resources?

**Try-with-resources.**

---

# 🧠 FINAL DOUBTKILLER MAP

```text
                         Connection
                              │
              ┌───────────────┼────────────────┐
              │               │                │
              ▼               ▼                ▼
        SQL Creation     Transactions       Lifetime
              │               │                │
      ┌───────┼───────┐       │                │
      │       │       │       │                │
      ▼       ▼       ▼       ▼                ▼
 Statement Prepared Callable  setAutoCommit()  close()
            Statement Statement     │
                                    │
                              ┌─────┴─────┐
                              ▼           ▼
                           commit()   rollback()
```

## The ultimate memory sentence:

> **`Connection` is the JDBC database session: `createStatement()` creates a `Statement`, `prepareStatement()` creates a parameterized `PreparedStatement`, `prepareCall()` creates a `CallableStatement`, `setAutoCommit()` controls transaction behavior, `commit()` confirms, `rollback()` undoes uncommitted work, and `close()` releases the connection.**

### One final distinction to never forget:

```text
createStatement()    → Statement
prepareStatement()   → PreparedStatement
prepareCall()        → CallableStatement

setAutoCommit()      → Transaction mode
commit()             → Confirm
rollback()           → Undo uncommitted work

close()              → Release connection
```
