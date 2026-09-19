# 6. Connection in Java / ONEPAGE

`Connection` is one of the **most important interfaces in JDBC**. It represents the communication/session between a Java application and a database.

We will cover every sub-concept individually:

```text
Connection
│
├── 1. Connection Interface
├── 2. createStatement()
├── 3. prepareStatement()
├── 4. prepareCall()
├── 5. commit()
├── 6. rollback()
├── 7. setAutoCommit()
└── 8. close()
```

---

# 1. Connection Interface

## What is `Connection`?

`Connection` is an interface from:

```java
java.sql.Connection
```

It represents an active connection/session between a Java application and a database.

Example:

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

Flow:

```text
Java Application
       ↓
DriverManager
       ↓
getConnection()
       ↓
Connection
       ↓
Database
```

### Important

`Connection` is **not the database**.

```text
Database  → actual database
Connection → communication/session with database
```

---

## Who creates the Connection object?

The application normally doesn't directly do:

```java
new Connection();
```

because `Connection` is an interface.

Instead:

```java
Connection con =
    DriverManager.getConnection(...);
```

The JDBC driver provides the implementation.

---

# 2. `createStatement()`

## What is it?

`createStatement()` creates a `Statement` object that can be used to execute SQL statements.

```java
Statement st =
    con.createStatement();
```

Import:

```java
import java.sql.Statement;
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

Example:

```java
Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

### Best suited for

SQL that doesn't require parameter values.

Example:

```java
SELECT * FROM student
```

### Important warning

Avoid constructing SQL by concatenating untrusted user input:

```java
String sql =
    "SELECT * FROM student WHERE name='"
    + userInput + "'";
```

This can lead to SQL injection.

For parameterized SQL, prefer:

```java
prepareStatement()
```

---

# 3. `prepareStatement()`

## What is it?

`prepareStatement()` creates a `PreparedStatement` object for executing **parameterized SQL**.

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

Import:

```java
import java.sql.PreparedStatement;
```

Then supply the value:

```java
ps.setInt(1, 101);
```

Execute:

```java
ResultSet rs =
    ps.executeQuery();
```

Flow:

```text
Connection
     ↓
prepareStatement()
     ↓
PreparedStatement
     ↓
set values
     ↓
execute SQL
```

---

## Why use `PreparedStatement`?

### 1. Parameters

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);
```

### 2. Better protection against SQL injection

The parameter is treated as a value rather than being directly concatenated into the SQL text.

### 3. Repeated execution

The same prepared SQL structure can often be reused with different parameter values.

---

## Example

```java
String sql =
    "INSERT INTO student(id, name) VALUES (?, ?)";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);
ps.setString(2, "Ravi");

ps.executeUpdate();
```

---

# 4. `prepareCall()`

## What is it?

`prepareCall()` creates a `CallableStatement` used to invoke a **stored procedure or stored function**, depending on the database and JDBC driver.

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

Import:

```java
import java.sql.CallableStatement;
```

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

Suppose the database has a stored procedure:

```sql
CALL getStudent(101);
```

JDBC might use:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );

cs.setInt(1, 101);

ResultSet rs =
    cs.executeQuery();
```

The exact SQL call syntax can vary by database/driver.

---

# 5. `commit()`

## What is `commit()`?

`commit()` permanently commits the changes made in the current transaction.

Example:

```java
con.commit();
```

Suppose:

```java
con.setAutoCommit(false);

PreparedStatement ps =
    con.prepareStatement(
        "UPDATE account SET balance = balance - ? WHERE id = ?"
    );

ps.setDouble(1, 500);
ps.setInt(2, 101);

ps.executeUpdate();

con.commit();
```

Conceptually:

```text
Transaction
     ↓
Changes
     ↓
commit()
     ↓
Transaction committed
```

---

## Why do we need `commit()`?

When auto-commit is disabled:

```java
con.setAutoCommit(false);
```

changes aren't automatically committed after each statement.

You explicitly decide:

```java
con.commit();
```

or:

```java
con.rollback();
```

---

# 6. `rollback()`

## What is `rollback()`?

`rollback()` undoes the uncommitted changes in the current transaction, subject to the database's transaction semantics.

Example:

```java
try {

    con.setAutoCommit(false);

    // SQL operation 1
    // SQL operation 2

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

Flow:

```text
Transaction
     ↓
SQL changes
     ↓
Something goes wrong
     ↓
rollback()
     ↓
Uncommitted changes undone
```

---

# 7. `setAutoCommit()`

## What is it?

`setAutoCommit()` controls whether each statement is automatically committed as a transaction.

```java
con.setAutoCommit(true);
```

or:

```java
con.setAutoCommit(false);
```

---

## `setAutoCommit(true)`

This is the normal default for a newly created JDBC connection.

Conceptually:

```text
SQL statement
     ↓
Execute
     ↓
Automatically commit
```

Example:

```java
con.setAutoCommit(true);

st.executeUpdate(
    "UPDATE student SET marks = 90 WHERE id = 101"
);
```

The statement's transaction is automatically committed when execution completes successfully.

---

## `setAutoCommit(false)`

Now the application controls transaction boundaries:

```java
con.setAutoCommit(false);
```

Then:

```java
// SQL 1
// SQL 2
// SQL 3

con.commit();
```

or:

```java
con.rollback();
```

Flow:

```text
setAutoCommit(false)
          ↓
      SQL 1
          ↓
      SQL 2
          ↓
      SQL 3
          ↓
    ┌─────┴─────┐
    ↓           ↓
 commit()   rollback()
```

---

# 8. `close()`

## What is `close()`?

`close()` closes the JDBC `Connection` and releases associated resources.

```java
con.close();
```

After closing:

```text
Connection
     ↓
closed
```

You should not continue using the connection as though it were open.

---

## Why should we close it?

Database connections are valuable resources.

If you continually create connections without closing them, you can eventually exhaust the available database connections/resources.

---

## Traditional approach

```java
Connection con = null;

try {

    con =
        DriverManager.getConnection(
            url,
            username,
            password
        );

    // JDBC work

} finally {

    if (con != null) {
        con.close();
    }
}
```

---

# 9. Modern Approach — try-with-resources

Prefer try-with-resources:

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

The connection is automatically closed when the try block finishes.

---

# 10. Complete Connection Example

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
                )
        ) {

            con.setAutoCommit(false);

            String sql =
                "UPDATE student SET marks = ? WHERE id = ?";

            try (
                PreparedStatement ps =
                    con.prepareStatement(sql)
            ) {

                ps.setInt(1, 90);
                ps.setInt(2, 101);

                ps.executeUpdate();

                con.commit();
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
```

---

# 11. Connection's Three Main Statement-Creation Methods

This is extremely important:

| Method               | Returns             | Main purpose                |
| -------------------- | ------------------- | --------------------------- |
| `createStatement()`  | `Statement`         | Simple/static SQL           |
| `prepareStatement()` | `PreparedStatement` | Parameterized SQL           |
| `prepareCall()`      | `CallableStatement` | Stored procedures/functions |

Remember:

```text
Connection
│
├── createStatement()
│       ↓
│   Statement
│
├── prepareStatement()
│       ↓
│   PreparedStatement
│
└── prepareCall()
        ↓
    CallableStatement
```

---

# 12. Transaction Methods

These three belong together:

```text
setAutoCommit(false)
        ↓
   SQL operations
        ↓
 ┌──────┴──────┐
 ↓             ↓
commit()    rollback()
```

### `setAutoCommit(false)`

> Take manual control of transaction boundaries.

### `commit()`

> Confirm/commit the transaction's changes.

### `rollback()`

> Undo uncommitted changes according to the database's transaction semantics.

---

# 13. Complete Connection Lifecycle

```text
DriverManager.getConnection()
             ↓
         Connection
             ↓
    ┌────────┼─────────┐
    ↓        ↓         ↓
create    prepare    prepare
Statement Statement   Call
    ↓        ↓         ↓
 Statement Prepared  Callable
            Statement Statement
                 │
                 ▼
             SQL work
                 │
                 ▼
        setAutoCommit(false)
                 │
          ┌──────┴──────┐
          ↓             ↓
       commit()     rollback()
          │             │
          └──────┬──────┘
                 ↓
              close()
```

---

# 14. ONEPAGE Quick Revision

### `Connection`

```text
java.sql.Connection
```

Represents a JDBC database connection/session.

### `createStatement()`

```java
Statement st = con.createStatement();
```

Creates a `Statement`.

### `prepareStatement()`

```java
PreparedStatement ps =
    con.prepareStatement("... ?");
```

Creates a parameterized `PreparedStatement`.

### `prepareCall()`

```java
CallableStatement cs =
    con.prepareCall("{call ...}");
```

Creates a `CallableStatement` for stored procedure/function calls.

### `setAutoCommit()`

```java
con.setAutoCommit(false);
```

Controls automatic transaction committing.

### `commit()`

```java
con.commit();
```

Commits the current transaction.

### `rollback()`

```java
con.rollback();
```

Rolls back uncommitted transaction changes.

### `close()`

```java
con.close();
```

Closes the connection and releases resources.

---

# 🔥 Most Important Distinctions

```text
createStatement()
        ↓
Statement
        ↓
Simple SQL
```

```text
prepareStatement()
        ↓
PreparedStatement
        ↓
Parameterized SQL
```

```text
prepareCall()
        ↓
CallableStatement
        ↓
Stored procedure/function calls
```

And:

```text
setAutoCommit(false)
        ↓
Manual transaction control
        ↓
   ┌────┴────┐
   ↓         ↓
commit    rollback
```

Finally:

```text
Connection
     ↓
close()
     ↓
Resources released
```

### 🧠 One-line memory trick

> **Connection creates statement objects, controls transactions, and finally closes the database connection.**
