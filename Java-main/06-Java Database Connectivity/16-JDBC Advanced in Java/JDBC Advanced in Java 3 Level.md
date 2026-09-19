# 16. JDBC Advanced in Java / 3LEVEL

We will learn **every sub-concept individually in 3 levels**:

* 🟢 **LEVEL 1 — Basic:** What is it?
* 🟡 **LEVEL 2 — Intermediate:** How does it work?
* 🔴 **LEVEL 3 — Advanced:** Internal behavior, differences, examples, and interview points.

---

# 1. Connection Pooling

## 🟢 LEVEL 1 — Basic

### What is Connection Pooling?

**Connection Pooling** is a technique where database connections are created in advance and **reused** instead of creating a new connection for every database operation.

Without pooling:

```text
Request
   ↓
Create Connection
   ↓
Use Connection
   ↓
Close Connection
```

With pooling:

```text
Application
     ↓
Connection Pool
 ┌────┬────┬────┐
 │ C1 │ C2 │ C3 │
 └────┴────┴────┘
     ↓
Borrow → Use → Return
```

### Simple definition

> **Connection Pooling = Reusing a collection of database connections.**

---

## 🟡 LEVEL 2 — Intermediate

Creating a database connection can be relatively expensive because it may involve:

```text
Java Application
      ↓
JDBC Driver
      ↓
Network
      ↓
Database
      ↓
Authentication/session setup
```

Therefore, instead of repeatedly creating connections, a pool maintains reusable connections.

Example:

```text
Pool:
C1
C2
C3
C4
C5
```

Application asks:

```java
Connection con = dataSource.getConnection();
```

The pool gives an available connection.

When finished:

```java
con.close();
```

With a pooled DataSource, `close()` normally returns the logical connection to the pool rather than physically destroying the underlying connection.

---

## 🔴 LEVEL 3 — Advanced

A pool normally manages:

* minimum/initial connections
* maximum connections
* idle connections
* connection acquisition
* connection return
* timeout behavior
* validation
* abandoned/leaked connection handling

Suppose maximum pool size is 5:

```text
C1 → busy
C2 → busy
C3 → busy
C4 → busy
C5 → busy
```

A sixth request may have to wait for a connection to become available or eventually time out, depending on pool configuration.

### Important

A larger pool is **not automatically better**.

Too many connections can overload the database.

### Interview point

**Why is connection pooling used?**

> To reduce the overhead of repeatedly creating database connections and improve application scalability and throughput.

---

# 2. DataSource

## 🟢 LEVEL 1 — Basic

`DataSource` is a JDBC interface used to obtain database connections.

Instead of:

```java
Connection con =
    DriverManager.getConnection(url, user, password);
```

we can use:

```java
Connection con =
    dataSource.getConnection();
```

The central method is:

```java
getConnection()
```

### Definition

> **DataSource is a JDBC abstraction for obtaining database connections.**

---

## 🟡 LEVEL 2 — Intermediate

Why is `DataSource` useful?

With `DriverManager`, application code often directly deals with:

```text
URL
Username
Password
Driver
```

With a configured `DataSource`:

```text
Application
     ↓
DataSource
     ↓
Connection
```

The DataSource can be configured externally or by the application's environment.

---

## 🔴 LEVEL 3 — Advanced

A crucial distinction:

> **DataSource does NOT inherently mean connection pooling.**

`DataSource` is an interface.

Implementations can provide different behavior.

Conceptually:

```text
DataSource
   │
   ├── Basic connection provider
   │
   ├── Pooled DataSource
   │
   └── Managed DataSource
```

A pooled DataSource may internally use:

```text
DataSource
     ↓
Connection Pool
     ↓
Physical DB connections
```

### DataSource vs DriverManager

| DriverManager                             | DataSource                        |
| ----------------------------------------- | --------------------------------- |
| Static connection-management API          | Connection-provider abstraction   |
| Common in simple programs                 | Common in managed applications    |
| URL/user/password often supplied directly | Configuration can be externalized |
| Pooling isn't its primary abstraction     | Can be backed by a pool           |

### Memory trick

> **DataSource tells you where/how to obtain a Connection.**

> **Connection Pool tells you how connections are reused.**

---

# 3. RowSet

## 🟢 LEVEL 1 — Basic

`RowSet` is a JDBC interface that extends `ResultSet`.

Conceptually:

```text
ResultSet
    ↑
  RowSet
```

It represents tabular data but provides additional capabilities.

### Definition

> **RowSet is a JDBC interface designed to provide a more flexible representation and handling of tabular data.**

---

## 🟡 LEVEL 2 — Intermediate

A normal ResultSet is commonly associated with:

```text
Connection
   ↓
Statement
   ↓
ResultSet
```

Some RowSet implementations can work in disconnected mode.

For example:

```text
Database
   ↓
Fetch data
   ↓
CachedRowSet
   ↓
Connection released
   ↓
Continue processing data
```

This can be useful when data needs to be processed without keeping a database connection occupied.

---

## 🔴 LEVEL 3 — Advanced

Important standard RowSet types include:

```text
RowSet
│
├── JdbcRowSet
├── CachedRowSet
├── WebRowSet
├── JoinRowSet
└── FilteredRowSet
```

### JdbcRowSet

Generally connected to the database.

```text
Database
   ↕
JdbcRowSet
```

### CachedRowSet

Can operate in a disconnected fashion.

```text
Database
   ↓
CachedRowSet
   ↓
Disconnect
   ↓
Work with cached data
```

### WebRowSet

Provides an XML-oriented representation of RowSet data.

### JoinRowSet

Provides functionality for joining related RowSets.

### FilteredRowSet

Supports filtering RowSet data.

### Important distinction

Don't memorize:

```text
RowSet = disconnected ResultSet
```

That's too broad.

Instead remember:

> **RowSet is a JDBC interface; some implementations, especially CachedRowSet, support disconnected operation.**

---

# 4. Transactions

## 🟢 LEVEL 1 — Basic

A **transaction** is a logical unit of database operations.

Example: transferring ₹1,000.

Two operations:

```sql
UPDATE account
SET balance = balance - 1000
WHERE id = 101;
```

and:

```sql
UPDATE account
SET balance = balance + 1000
WHERE id = 102;
```

Both operations should belong to one logical transaction.

```text
Transaction
   ├── Debit A
   └── Credit B
```

---

## 🟡 LEVEL 2 — Intermediate

Normally JDBC connections begin with auto-commit enabled.

```java
con.setAutoCommit(false);
```

Now multiple operations can be performed before committing.

```java
con.setAutoCommit(false);

// SQL 1
// SQL 2
// SQL 3

con.commit();
```

If something goes wrong:

```java
con.rollback();
```

Conceptually:

```text
              Transaction
                   │
        ┌──────────┴──────────┐
        ↓                     ↓
   Everything OK          Something fails
        ↓                     ↓
     commit()             rollback()
```

---

## 🔴 LEVEL 3 — Advanced

### Auto-commit

With:

```java
con.setAutoCommit(true);
```

each successfully completed statement is normally committed automatically.

With:

```java
con.setAutoCommit(false);
```

the application controls transaction boundaries.

### Savepoint

A transaction can have a checkpoint:

```java
Savepoint sp = con.setSavepoint();
```

Then:

```java
con.rollback(sp);
```

Conceptually:

```text
Operation 1
    ↓
Operation 2
    ↓
SAVEPOINT
    ↓
Operation 3
    ↓
Operation 4 fails
    ↓
ROLLBACK TO SAVEPOINT
```

### Important distinction

```text
commit()
```

finishes the current transaction.

```text
rollback()
```

undoes uncommitted work in the current transaction.

```text
rollback(savepoint)
```

rolls back to a particular checkpoint.

---

# 5. Isolation Levels

## 🟢 LEVEL 1 — Basic

Isolation levels control how concurrent transactions interact.

Suppose:

```text
Transaction A
      ↕
   Database
      ↕
Transaction B
```

The question is:

> What changes made by B can A see?

JDBC provides these standard levels:

```java
TRANSACTION_READ_UNCOMMITTED
TRANSACTION_READ_COMMITTED
TRANSACTION_REPEATABLE_READ
TRANSACTION_SERIALIZABLE
```

---

# 🟡 LEVEL 2 — Intermediate

There are three classic problems.

## 1. Dirty Read

Transaction A reads data that transaction B changed but has not committed.

```text
B → changes data
      ↓
A → reads it
      ↓
B → rollback
```

A read data that was never committed.

---

## 2. Non-repeatable Read

A reads:

```text
100
```

B changes and commits:

```text
200
```

A reads the same row again:

```text
200
```

The same row produced different values.

---

## 3. Phantom Read

A executes:

```sql
SELECT * FROM employee
WHERE salary > 50000;
```

Gets:

```text
5 rows
```

B inserts another matching row and commits.

A runs the same query:

```text
6 rows
```

The new row is a phantom.

---

## 🔴 LEVEL 3 — Advanced

### Isolation matrix

| Isolation Level  | Dirty Read | Non-repeatable Read | Phantom Read    |
| ---------------- | ---------- | ------------------- | --------------- |
| READ_UNCOMMITTED | Possible   | Possible            | Possible        |
| READ_COMMITTED   | Prevented  | Possible            | Possible        |
| REPEATABLE_READ  | Prevented  | Prevented           | May be possible |
| SERIALIZABLE     | Prevented  | Prevented           | Prevented       |

The exact implementation can vary by database.

### Setting isolation

```java
con.setTransactionIsolation(
    Connection.TRANSACTION_READ_COMMITTED
);
```

Reading it:

```java
int level =
    con.getTransactionIsolation();
```

### Trade-off

Generally:

```text
Higher isolation
      ↓
Stronger consistency
      ↓
Potentially less concurrency
      ↓
Potentially more locking/contention
```

So:

> **Highest isolation is not automatically the best choice.**

Choose according to the application's consistency requirements.

---

# 6. Batch Processing

## 🟢 LEVEL 1 — Basic

Batch processing means grouping multiple SQL operations and executing them as a batch.

Without batch:

```text
SQL 1 → Database
SQL 2 → Database
SQL 3 → Database
SQL 4 → Database
```

With batch:

```text
SQL 1
SQL 2
SQL 3
SQL 4
 ↓
Batch
 ↓
Database
```

---

# 🟡 LEVEL 2 — Intermediate

Use:

```java
addBatch();
```

to add an operation.

Then:

```java
executeBatch();
```

to execute the accumulated operations.

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student VALUES (?, ?)"
    );

ps.setInt(1, 101);
ps.setString(2, "Ravi");
ps.addBatch();

ps.setInt(1, 102);
ps.setString(2, "John");
ps.addBatch();

int[] result =
    ps.executeBatch();
```

The returned `int[]` contains update counts or driver-defined batch result indicators for the individual commands, subject to JDBC batch semantics.

---

## 🔴 LEVEL 3 — Advanced

Batch processing is especially useful when the SQL structure is repeated.

For example:

```text
INSERT (?, ?)

101, Ravi
102, John
103, Alice
104, David
```

PreparedStatement is therefore commonly combined with batching.

### Batch ≠ Transaction

This is extremely important.

```text
Batch
  ↓
How to execute many operations efficiently

Transaction
  ↓
How to group operations into one logical unit
```

They can be combined:

```java
con.setAutoCommit(false);

ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();

con.commit();
```

If appropriate:

```java
con.rollback();
```

### Performance

Batching can reduce overhead, but actual performance depends on:

* JDBC driver
* database engine
* network
* batch size
* SQL type
* database configuration

---

# 7. Resource Management

## 🟢 LEVEL 1 — Basic

JDBC uses resources such as:

```text
Connection
Statement
PreparedStatement
CallableStatement
ResultSet
```

These should be properly closed.

Otherwise resources can leak.

---

## 🟡 LEVEL 2 — Intermediate

A common JDBC dependency chain is:

```text
Connection
    ↓
PreparedStatement
    ↓
ResultSet
```

Therefore, the resources should be released properly.

Modern Java uses **try-with-resources**:

```java
try (
    Connection con = dataSource.getConnection();

    PreparedStatement ps =
        con.prepareStatement(
            "SELECT id, name FROM student"
        );

    ResultSet rs =
        ps.executeQuery()
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

When control leaves the `try` block, the resources are automatically closed.

---

## 🔴 LEVEL 3 — Advanced

Resources are closed in **reverse order of initialization**.

Conceptually:

```text
ResultSet
    ↓ close
PreparedStatement
    ↓ close
Connection
    ↓ close
```

This makes sense because:

```text
ResultSet depends on Statement
Statement depends on Connection
```

---

## Important: Connection pooling

Suppose:

```java
Connection con =
    dataSource.getConnection();
```

and the DataSource is pooled.

You should **still close it**:

```java
con.close();
```

In a typical pool:

```text
con.close()
     ↓
Return logical connection
     ↓
Connection Pool
```

It doesn't necessarily mean:

```text
Destroy physical DB connection
```

Failing to close pooled connections can exhaust the pool.

---

# 8. All Seven Concepts Together

Now connect everything.

```text
                    JAVA APPLICATION
                           │
                           ↓
                      DataSource
                           │
                           ↓
                    Connection Pool
                           │
                           ↓
                       Connection
                           │
            ┌──────────────┼──────────────┐
            ↓              ↓              ↓
      Transaction    Isolation Level    Resource
            │              │           Management
            ↓              ↓
       SQL Operations   Concurrent
            │            Transactions
            ↓
       PreparedStatement
            │
            ↓
       Batch Processing
            │
            ↓
         Database
            │
            ↓
        ResultSet
        / RowSet
```

---

# 9. Three-Level Master Revision

| Concept                 | 🟢 Level 1                       | 🟡 Level 2                                | 🔴 Level 3                                            |
| ----------------------- | -------------------------------- | ----------------------------------------- | ----------------------------------------------------- |
| **Connection Pooling**  | Reuse connections                | Borrow/return connections                 | Pool sizing, timeout, validation, leaks               |
| **DataSource**          | Obtain connections               | Alternative to direct DriverManager usage | Interface; may be pooled/managed                      |
| **RowSet**              | Flexible ResultSet-style API     | Can support connected/disconnected use    | JdbcRowSet, CachedRowSet, WebRowSet, etc.             |
| **Transactions**        | Group operations                 | commit/rollback                           | Auto-commit, savepoints, transaction boundaries       |
| **Isolation**           | Control concurrent access        | Dirty/non-repeatable/phantom reads        | Four JDBC isolation levels and trade-offs             |
| **Batch**               | Execute many operations together | addBatch/executeBatch                     | Performance, batch size, driver behavior              |
| **Resource Management** | Close JDBC resources             | try-with-resources                        | Dependency order, pooled connections, leak prevention |

---

# 10. Final 3LEVEL Memory Map

```text
JDBC ADVANCED
│
├── 1. CONNECTION POOLING
│      ├── Create/reuse connections
│      ├── Borrow connection
│      └── Return connection
│
├── 2. DATASOURCE
│      ├── Connection provider
│      ├── getConnection()
│      └── Can be backed by a pool
│
├── 3. ROWSET
│      ├── RowSet interface
│      ├── JdbcRowSet
│      ├── CachedRowSet
│      ├── WebRowSet
│      ├── JoinRowSet
│      └── FilteredRowSet
│
├── 4. TRANSACTIONS
│      ├── Auto-commit
│      ├── setAutoCommit(false)
│      ├── commit()
│      ├── rollback()
│      └── Savepoint
│
├── 5. ISOLATION LEVELS
│      ├── READ_UNCOMMITTED
│      ├── READ_COMMITTED
│      ├── REPEATABLE_READ
│      └── SERIALIZABLE
│
├── 6. BATCH PROCESSING
│      ├── addBatch()
│      ├── executeBatch()
│      └── Efficient bulk execution
│
└── 7. RESOURCE MANAGEMENT
       ├── Connection
       ├── Statement
       ├── PreparedStatement
       ├── ResultSet
       ├── try-with-resources
       └── Prevent resource leaks
```

### The 7 questions to remember

1. **Connection Pooling:** How do I **reuse connections efficiently**?
2. **DataSource:** How do I **obtain/manage connections through an abstraction**?
3. **RowSet:** How can I **work with tabular JDBC data more flexibly**?
4. **Transactions:** How do I **group database operations into one logical unit**?
5. **Isolation:** How do I **control what concurrent transactions can see**?
6. **Batch:** How do I **execute many operations efficiently**?
7. **Resource Management:** How do I **release JDBC resources safely and prevent leaks**?
