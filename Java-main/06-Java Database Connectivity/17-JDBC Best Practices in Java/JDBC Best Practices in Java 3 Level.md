# 17. JDBC Best Practices in Java — 3LEVEL

We will study every sub-concept at **three levels**:

* 🟢 **LEVEL 1 — Beginner:** What is it?
* 🟡 **LEVEL 2 — Intermediate:** How does it work and why is it needed?
* 🔴 **LEVEL 3 — Advanced:** Important rules, edge cases, and professional usage.

---

# 1. try-with-resources

## 🟢 LEVEL 1 — Beginner

### What is try-with-resources?

`try-with-resources` is a Java feature used to **automatically close resources** after they are no longer needed.

JDBC resources include:

```text
Connection
PreparedStatement
Statement
ResultSet
```

Example:

```java
try (Connection con =
         DriverManager.getConnection(url, user, password)) {

    System.out.println("Connected");

}
```

When the `try` block finishes, Java automatically closes `con`.

### Why is it needed?

Without it, we have to manually write:

```java
con.close();
```

If an exception occurs before `close()`, cleanup can become difficult.

---

## 🟡 LEVEL 2 — Intermediate

Multiple resources can be declared:

```java
try (
    Connection con =
        DriverManager.getConnection(url, user, password);

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
```

Java closes them automatically.

The conceptual closing order is:

```text
ResultSet
    ↓
PreparedStatement
    ↓
Connection
```

That is, resources are closed in **reverse order of creation**.

---

## 🔴 LEVEL 3 — Advanced

A resource used in try-with-resources must implement `AutoCloseable` (or `Closeable`, which extends it).

JDBC resources such as `Connection`, `Statement`, and `ResultSet` support this mechanism.

Important:

```java
try (Connection con = dataSource.getConnection()) {
    ...
}
```

If a connection comes from a connection pool, `close()` normally means:

```text
logical connection
      ↓
returned to pool
```

rather than necessarily physically destroying the underlying database connection.

### Exception during close

If the main operation throws an exception and closing the resource also throws one, Java can preserve the close exception as a **suppressed exception**.

You can inspect suppressed exceptions with:

```java
e.getSuppressed();
```

### Golden rule

> **Every JDBC resource that your code acquires should have a clear ownership and cleanup strategy.**

---

# 2. PreparedStatement

## 🟢 LEVEL 1 — Beginner

`PreparedStatement` is a JDBC interface used to execute SQL containing parameters.

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();
```

Here:

```text
? → parameter placeholder
101 → value supplied for parameter 1
```

Parameter indexes start from **1**, not 0.

---

## 🟡 LEVEL 2 — Intermediate

Suppose:

```java
String sql =
    "INSERT INTO student(id, name) " +
    "VALUES (?, ?)";
```

Then:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

The SQL structure and values are handled separately.

Common methods:

```java
setInt()
setString()
setDouble()
setLong()
setBoolean()
setDate()
```

For queries returning rows:

```java
ps.executeQuery();
```

For operations such as INSERT, UPDATE, and DELETE:

```java
ps.executeUpdate();
```

---

## 🔴 LEVEL 3 — Advanced

The major advantages are:

### 1. Parameterization

Values are supplied separately from SQL syntax.

### 2. Security

Proper parameter binding prevents ordinary user-supplied values from being interpreted as SQL syntax.

### 3. Reusability

The same statement structure can be executed with different values.

```java
ps.setInt(1, 101);
ps.executeUpdate();

ps.setInt(1, 102);
ps.executeUpdate();
```

### 4. Potential performance benefits

Drivers/databases may reuse prepared execution information, but exact behavior is driver- and database-dependent.

Therefore don't memorize:

> "PreparedStatement is always faster."

Its strongest general reasons are:

```text
Security
+
Parameterization
+
Maintainability
+
Potential reuse
```

---

# 3. Connection Management

## 🟢 LEVEL 1 — Beginner

A JDBC `Connection` represents a communication/session relationship between the Java application and database.

Conceptually:

```text
Java Application
      ↓
Connection
      ↓
JDBC Driver
      ↓
Database
```

A connection can be obtained using:

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

or commonly in applications:

```java
Connection con =
    dataSource.getConnection();
```

---

## 🟡 LEVEL 2 — Intermediate

A connection should not be held longer than necessary.

Good:

```text
Get connection
     ↓
Perform database work
     ↓
Close/release connection
```

Bad:

```text
Get connection
     ↓
Do unrelated processing
     ↓
Wait for another service
     ↓
Do more unrelated work
     ↓
Finally use database
     ↓
Close
```

The second approach unnecessarily occupies a connection.

### Connection pooling

Instead of creating a physical connection for every request:

```text
Request
  ↓
Create connection
  ↓
Use
  ↓
Destroy
```

a pool can maintain reusable connections:

```text
        Connection Pool
     ┌────┬────┬────┬────┐
     │ C1 │ C2 │ C3 │ C4 │
     └────┴────┴────┴────┘
             ↑
         Application
```

Application:

```text
Borrow
  ↓
Use
  ↓
Close
  ↓
Return to pool
```

---

## 🔴 LEVEL 3 — Advanced

### Never assume `close()` is unnecessary with pooling

Even when using a pool:

```java
try (Connection con =
         dataSource.getConnection()) {

    ...
}
```

is still correct.

`close()` generally releases the logical connection back to the pool.

### Connection state matters

A connection can have state such as:

```text
auto-commit
transaction state
isolation level
read-only setting
warnings
session configuration
```

Therefore sharing a single mutable `Connection` indiscriminately across unrelated concurrent operations is dangerous.

### Professional rule

> **Acquire late, use efficiently, release early.**

---

# 4. Transaction Management

## 🟢 LEVEL 1 — Beginner

A transaction is a group of database operations treated as one logical unit.

Example:

```text
Transfer ₹1,000

Account A
   ↓
- ₹1,000

Account B
   ↓
+ ₹1,000
```

Both operations should succeed together.

If one fails:

```text
rollback
```

If everything succeeds:

```text
commit
```

---

## 🟡 LEVEL 2 — Intermediate

JDBC connections normally begin with auto-commit enabled.

With auto-commit:

```text
SQL 1 → commit
SQL 2 → commit
SQL 3 → commit
```

For a multi-step transaction:

```java
con.setAutoCommit(false);
```

Now we control the transaction manually.

If successful:

```java
con.commit();
```

If something fails:

```java
con.rollback();
```

Example:

```java
try {
    con.setAutoCommit(false);

    updateAccountA(con);
    updateAccountB(con);

    con.commit();

} catch (SQLException e) {

    con.rollback();

    throw e;
}
```

---

## 🔴 LEVEL 3 — Advanced

A proper transaction flow is:

```text
Acquire Connection
       ↓
Disable auto-commit
       ↓
Operation 1
       ↓
Operation 2
       ↓
Operation 3
       ↓
     Success?
     /      \
   YES       NO
    ↓         ↓
 commit    rollback
    ↓         ↓
 Release connection
```

### Savepoint

For partial rollback:

```java
Savepoint sp =
    con.setSavepoint();
```

Then:

```java
con.rollback(sp);
```

This allows rollback to that savepoint instead of necessarily rolling back the entire transaction.

### Important

When using pooled connections, make sure the transaction is properly completed:

```text
commit OR rollback
        ↓
close/release
```

Never return a connection to the pool with an unintended unfinished transaction.

---

# 5. SQL Injection Prevention

## 🟢 LEVEL 1 — Beginner

SQL injection is a security problem where untrusted input can alter the intended SQL command.

Dangerous pattern:

```java
String sql =
    "SELECT * FROM users WHERE name = '"
    + username
    + "'";
```

The application is combining:

```text
SQL syntax
+
user input
```

into one SQL string.

---

## 🟡 LEVEL 2 — Intermediate

Use `PreparedStatement`.

Instead of:

```java
String sql =
    "SELECT * FROM users WHERE name = '"
    + username
    + "'";
```

use:

```java
String sql =
    "SELECT * FROM users WHERE name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, username);

ResultSet rs =
    ps.executeQuery();
```

Now:

```text
SQL structure
      ↓
PreparedStatement

User value
      ↓
Parameter binding
```

The value is treated as data rather than simply being concatenated into the SQL command.

---

## 🔴 LEVEL 3 — Advanced

### Parameterization is the primary defense

Use:

```java
ps.setString(...)
ps.setInt(...)
ps.setDouble(...)
```

rather than:

```java
"..." + userInput
```

### But parameters don't represent arbitrary SQL syntax

For example, this generally doesn't work as a way to parameterize an identifier:

```sql
SELECT * FROM ?
```

A table name is SQL syntax/an identifier, not a normal value.

If the application needs dynamically selected tables, use a strict allowlist.

Example:

```java
String table;

if (choice.equals("students")) {
    table = "student";
} else if (choice.equals("employees")) {
    table = "employee";
} else {
    throw new IllegalArgumentException(
        "Invalid table"
    );
}
```

Then construct SQL only from trusted, predefined identifiers.

### Defense in depth

A professional application can combine:

```text
PreparedStatement
       +
Input validation
       +
Allowlisting identifiers
       +
Least-privilege DB account
       +
Secure application design
```

---

# 6. Performance

## 🟢 LEVEL 1 — Beginner

JDBC performance means making database operations efficient.

Important techniques include:

```text
Connection pooling
PreparedStatement
Batch processing
Efficient SQL
Indexes
Reasonable result sets
```

Don't retrieve more data than you need.

Instead of:

```sql
SELECT * FROM student;
```

if you only need two columns:

```sql
SELECT id, name
FROM student;
```

---

## 🟡 LEVEL 2 — Intermediate

### 1. Connection pooling

Avoid repeatedly establishing physical connections.

```text
Pool
 ↓
Borrow
 ↓
Use
 ↓
Return
```

### 2. Batch processing

Instead of executing thousands of operations individually:

```text
execute
execute
execute
execute
...
```

use:

```java
ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();
```

This can reduce overhead, although exact performance depends on the driver, database, SQL, network, and configuration.

### 3. Filter in the database

Bad:

```text
Database
 ↓
1,000,000 rows
 ↓
Java filters
 ↓
100 rows
```

Better:

```sql
SELECT id, name
FROM student
WHERE department = ?;
```

Now the database returns only relevant rows.

---

## 🔴 LEVEL 3 — Advanced

### 1. Avoid N+1 queries

Suppose:

```text
1 query → get 100 departments

then:

100 queries → get students for each department
```

That's:

```text
101 database calls
```

Depending on the requirement, joins, batching, or better query design may reduce unnecessary round trips.

---

### 2. Index appropriately

If we frequently search:

```sql
SELECT *
FROM student
WHERE roll_no = ?;
```

an appropriate index on `roll_no` may improve lookup performance.

But indexes have costs:

```text
Index
 ├── faster reads for suitable queries
 ├── storage cost
 └── additional write/maintenance cost
```

So indexes should be based on actual query patterns.

---

### 3. Control result size

Don't retrieve millions of rows when the application needs 20.

Use appropriate:

```text
WHERE
pagination
LIMIT/FETCH syntax where supported
```

depending on the database.

---

### 4. Fetch size

JDBC allows:

```java
ps.setFetchSize(500);
```

This gives the JDBC driver a fetch-size hint.

However:

> Its exact behavior is driver/database dependent.

Don't assume it universally means exactly 500 rows are transferred per network request.

---

### 5. Measure before optimizing

The most important professional performance rule:

```text
Measure
   ↓
Find bottleneck
   ↓
Optimize
   ↓
Measure again
```

Possible bottlenecks:

```text
Connection acquisition
SQL execution
Network latency
Database locks
Missing indexes
Large result sets
Result processing
```

Don't optimize Java code blindly when the real problem may be the SQL or database.

---

# 🔥 3LEVEL Master Comparison

| Best Practice                | 🟢 Level 1                     | 🟡 Level 2                             | 🔴 Level 3                                                         |
| ---------------------------- | ------------------------------ | -------------------------------------- | ------------------------------------------------------------------ |
| **try-with-resources**       | Automatically closes resources | Manages multiple JDBC resources        | Handles `AutoCloseable`, suppressed exceptions, pooled connections |
| **PreparedStatement**        | SQL with `?` parameters        | `setXXX()` + execute                   | Parameterization, reuse, security, driver-dependent optimization   |
| **Connection management**    | Create/use/close               | DataSource + pooling                   | Minimize hold time, manage connection state                        |
| **Transaction management**   | Commit or rollback             | Disable auto-commit                    | Savepoints, pooled-connection state, atomic workflows              |
| **SQL injection prevention** | Don't concatenate user input   | Use PreparedStatement                  | Parameterize values + allowlist identifiers + least privilege      |
| **Performance**              | Make DB operations efficient   | Pooling + batching + efficient queries | Measure bottlenecks, indexes, N+1, fetch behavior                  |

---

# 🧠 Final 3LEVEL Memory Map

```text
17. JDBC BEST PRACTICES
│
├── 1. try-with-resources
│      ├── L1 → Automatically close
│      ├── L2 → Manage multiple resources
│      └── L3 → AutoCloseable + suppressed exceptions
│
├── 2. PreparedStatement
│      ├── L1 → SQL + ?
│      ├── L2 → setXXX()
│      └── L3 → Parameterization + security + reuse
│
├── 3. Connection Management
│      ├── L1 → Connection = DB communication
│      ├── L2 → DataSource + pooling
│      └── L3 → Acquire late, release early
│
├── 4. Transaction Management
│      ├── L1 → Commit / Rollback
│      ├── L2 → setAutoCommit(false)
│      └── L3 → Savepoints + correct pooled state
│
├── 5. SQL Injection Prevention
│      ├── L1 → Don't concatenate untrusted input
│      ├── L2 → PreparedStatement
│      └── L3 → Parameters + allowlists + least privilege
│
└── 6. Performance
       ├── L1 → Avoid unnecessary work
       ├── L2 → Pool + batch + efficient SQL
       └── L3 → Measure + indexes + N+1 + result/fetch optimization
```

## ⭐ One-line exam memory

> **Close what you open, parameterize what you execute, release connections quickly, commit successful transactions, rollback failures, never trust SQL input, and optimize only after measuring.**
