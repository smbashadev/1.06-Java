# 17. JDBC Best Practices in Java / ONEPAGE

JDBC best practices are rules that help us write JDBC programs that are **safe, efficient, reliable, maintainable, and less error-prone**.

We will study each concept individually.

---

# 1. try-with-resources

## What is it?

**try-with-resources** is a Java feature used to automatically close resources that implement `AutoCloseable`.

JDBC resources such as:

* `Connection`
* `Statement`
* `PreparedStatement`
* `CallableStatement`
* `ResultSet`

can be used with try-with-resources.

### Traditional approach

```java
Connection con = null;
PreparedStatement ps = null;
ResultSet rs = null;

try {
    con = DriverManager.getConnection(url, user, password);

    ps = con.prepareStatement(
        "SELECT id, name FROM student"
    );

    rs = ps.executeQuery();

    while (rs.next()) {
        System.out.println(rs.getInt("id"));
    }

} finally {
    if (rs != null) rs.close();
    if (ps != null) ps.close();
    if (con != null) con.close();
}
```

Lots of cleanup code.

### Recommended approach

```java
try (
    Connection con = DriverManager.getConnection(url, user, password);

    PreparedStatement ps =
        con.prepareStatement(
            "SELECT id, name FROM student"
        );

    ResultSet rs = ps.executeQuery()
) {

    while (rs.next()) {
        System.out.println(
            rs.getInt("id") + " " +
            rs.getString("name")
        );
    }

} catch (SQLException e) {
    e.printStackTrace();
}
```

Resources are automatically closed.

### Closing order

If declared as:

```text
Connection
    ↓
PreparedStatement
    ↓
ResultSet
```

they are closed in reverse order:

```text
ResultSet
    ↓
PreparedStatement
    ↓
Connection
```

### Best practice

> **Use try-with-resources for JDBC resource management whenever practical.**

---

# 2. PreparedStatement

## What is it?

`PreparedStatement` is a JDBC interface used for executing **parameterized SQL statements**.

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs = ps.executeQuery();
```

Here:

```text
?
```

is a parameter placeholder.

---

## Why use PreparedStatement?

### 1. Security

It helps prevent SQL injection when values are supplied as parameters.

### 2. Readability

Instead of constructing SQL manually:

```java
String sql =
    "SELECT * FROM student WHERE id = " + id;
```

use:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

ps.setInt(1, id);
```

### 3. Repeated execution

The same SQL structure can be reused with different parameter values.

```java
ps.setInt(1, 101);
ps.executeQuery();

ps.setInt(1, 102);
ps.executeQuery();
```

### Best practice

> **Use PreparedStatement instead of concatenating untrusted/user-supplied values into SQL.**

---

# 3. Connection Management

A JDBC `Connection` represents a database connection/session.

Example:

```java
Connection con =
    dataSource.getConnection();
```

## Best practices

### 1. Don't create unnecessary connections

Avoid:

```text
Request 1 → create connection
Request 2 → create connection
Request 3 → create connection
...
```

Use connection pooling in applications where appropriate:

```text
Application
     ↓
DataSource
     ↓
Connection Pool
     ↓
Database
```

---

### 2. Close connections

Even pooled connections should be closed:

```java
try (Connection con =
         dataSource.getConnection()) {

    // database work
}
```

With a pool, `close()` typically returns/releases the logical connection to the pool.

---

### 3. Don't keep connections unnecessarily long

Bad idea:

```text
Get connection
      ↓
Do unrelated application work
      ↓
Wait
      ↓
Finally execute SQL
      ↓
Close
```

Better:

```text
Get connection
      ↓
Perform DB work
      ↓
Close/release
```

This is especially important with connection pools because an unnecessarily held connection cannot be used by another request.

---

### 4. Don't create a connection for every SQL statement

Prefer:

```text
Connection
   ↓
Statement 1
Statement 2
Statement 3
   ↓
Close
```

when the operations logically belong together and the connection remains appropriate.

---

# 4. Transaction Management

A **transaction** is a logical unit of database operations.

Example:

```text
Bank Transfer
    │
    ├── Debit Account A
    │
    └── Credit Account B
```

Both operations should succeed together.

---

## Disable auto-commit when necessary

```java
con.setAutoCommit(false);
```

Then:

```java
try {
    debitAccount();
    creditAccount();

    con.commit();

} catch (SQLException e) {
    con.rollback();
}
```

Conceptually:

```text
                Transaction
                    │
          ┌─────────┴─────────┐
          ↓                   ↓
      Everything OK        Failure
          ↓                   ↓
       commit()           rollback()
```

---

## Important distinction

```text
commit()
```

means:

> Make the transaction's changes permanent.

```text
rollback()
```

means:

> Undo the transaction's uncommitted changes.

---

## Best practice

Always make transaction boundaries explicit when multiple operations must succeed or fail together.

Also ensure that a transaction is completed appropriately before a connection is released.

---

# 5. SQL Injection Prevention

## What is SQL Injection?

SQL injection occurs when untrusted input changes the intended meaning of SQL.

### Dangerous approach

```java
String sql =
    "SELECT * FROM users WHERE username = '"
    + username
    + "'";
```

If `username` contains specially crafted SQL syntax, the resulting SQL can have a different meaning than intended.

---

## Correct approach

Use `PreparedStatement`:

```java
String sql =
    "SELECT * FROM users WHERE username = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, username);

ResultSet rs =
    ps.executeQuery();
```

Now the value is supplied as a parameter rather than being directly concatenated into the SQL command text.

---

## Important distinction

PreparedStatement does **not** mean:

> "All SQL is automatically safe."

You must still construct SQL correctly.

For example, table/column names generally cannot be supplied as ordinary `?` parameter values:

```java
// Not a valid way to parameterize a table name
SELECT * FROM ?
```

For dynamic identifiers, use a controlled allowlist rather than blindly inserting user input.

---

## Best practice

> **Never concatenate untrusted user input directly into SQL syntax.**

Use:

```text
PreparedStatement
       ↓
Parameters
       ↓
setInt()
setString()
setDouble()
...
```

---

# 6. Performance

JDBC performance depends on much more than simply "use JDBC."

Important practices include:

---

## 6.1 Use PreparedStatement

For repeated parameterized operations:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student VALUES (?, ?)"
    );
```

This makes parameterized execution straightforward and can allow the driver/database to optimize repeated execution.

---

## 6.2 Use batch processing

Instead of:

```text
INSERT 1 → execute
INSERT 2 → execute
INSERT 3 → execute
INSERT 4 → execute
```

use:

```java
ps.addBatch();
ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();
```

Batch processing is particularly useful for large numbers of similar DML operations.

---

## 6.3 Don't retrieve unnecessary columns

Instead of:

```sql
SELECT * FROM student;
```

when only two columns are required:

```sql
SELECT id, name FROM student;
```

This can reduce the amount of data transferred and processed.

---

## 6.4 Use appropriate queries

Good database performance depends heavily on SQL itself.

Consider:

* appropriate indexes
* selective `WHERE` conditions
* avoiding unnecessary joins
* appropriate pagination
* avoiding unnecessary repeated queries

JDBC cannot compensate for inefficient SQL.

---

## 6.5 Don't retrieve millions of rows unnecessarily

Bad:

```java
SELECT * FROM huge_table;
```

when the application only needs a small subset.

Use appropriate filtering and pagination.

---

## 6.6 Use connection pooling

Creating connections repeatedly can be expensive.

A pool allows connections to be reused:

```text
Application
     ↓
DataSource
     ↓
Pool
 ┌───┬───┬───┐
 │ C1│ C2│ C3│
 └───┴───┴───┘
```

---

## 6.7 Close resources quickly

A leaked connection can eventually cause:

```text
Connection leak
      ↓
Pool exhausted
      ↓
Requests wait
      ↓
Timeouts
      ↓
Poor performance
```

So resource management is also a **performance practice**, not merely cleanup.

---

# 7. Complete Best-Practice Example

```java
String sql =
    "SELECT id, name FROM student WHERE id = ?";

try (
    Connection con = dataSource.getConnection();

    PreparedStatement ps =
        con.prepareStatement(sql)
) {

    ps.setInt(1, 101);

    try (ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");

            System.out.println(id + " " + name);
        }
    }

} catch (SQLException e) {
    e.printStackTrace();
}
```

This demonstrates:

```text
try-with-resources
        ↓
Connection management
        ↓
PreparedStatement
        ↓
Parameterized SQL
        ↓
SQL injection protection
        ↓
ResultSet processing
        ↓
Automatic cleanup
```

---

# 8. ONEPAGE Master Table

| Best Practice                | What to do                                            | Main Benefit               |
| ---------------------------- | ----------------------------------------------------- | -------------------------- |
| **try-with-resources**       | Automatically close JDBC resources                    | Prevent leaks              |
| **PreparedStatement**        | Use parameterized SQL                                 | Security + maintainability |
| **Connection management**    | Reuse/pool connections and release promptly           | Scalability                |
| **Transaction management**   | Use `commit()` / `rollback()` appropriately           | Data consistency           |
| **SQL injection prevention** | Never concatenate untrusted values into SQL           | Security                   |
| **Performance**              | Pooling, batching, efficient SQL, appropriate queries | Speed + scalability        |

---

# 9. Final Memory Formula

```text
JDBC BEST PRACTICES
        │
        ├── Resources
        │      ↓
        │  try-with-resources
        │
        ├── SQL
        │      ↓
        │  PreparedStatement
        │
        ├── Connections
        │      ↓
        │  Pool + release promptly
        │
        ├── Transactions
        │      ↓
        │  commit / rollback
        │
        ├── Security
        │      ↓
        │  Prevent SQL Injection
        │
        └── Performance
               ↓
        Pool + Batch + Efficient SQL
```

### Six words to remember

> **Resources → Close**
> **SQL → Parameterize**
> **Connections → Reuse**
> **Transactions → Control**
> **Input → Protect**
> **Performance → Optimize**
