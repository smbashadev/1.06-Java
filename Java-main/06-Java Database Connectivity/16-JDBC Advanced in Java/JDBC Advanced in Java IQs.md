# 16. JDBC Advanced in Java / DOUBTKILLER

This is the **confusion-destroying version**.

The goal of `DOUBTKILLER` is not just to tell you *what* each concept is, but to eliminate the questions that usually appear in exams, interviews, debugging, and real JDBC code.

---

# 1. CONNECTION POOLING

## 1.1 What exactly is a database connection?

A `Connection` represents a communication/session relationship between Java and a database.

```java
Connection con = dataSource.getConnection();
```

Conceptually:

```text
Java Application
       ↓
   JDBC Driver
       ↓
    Network
       ↓
    Database
```

A connection isn't merely a Java object. Establishing it can involve network communication, authentication, session initialization, and database-side resources.

---

# 1.2 Why do we need Connection Pooling?

Suppose 1,000 requests arrive.

### Without pooling

```text
Request 1 → create connection → use → close
Request 2 → create connection → use → close
Request 3 → create connection → use → close
...
Request 1000 → create connection → use → close
```

Repeated connection creation is expensive.

### With pooling

```text
Connection Pool
┌────┬────┬────┬────┬────┐
│ C1 │ C2 │ C3 │ C4 │ C5 │
└────┴────┴────┴────┴────┘
```

Requests borrow connections:

```text
Request
   ↓
Borrow C2
   ↓
Use C2
   ↓
close()
   ↓
Return C2 to pool
```

---

# 1.3 The biggest doubt: Does `close()` destroy the connection?

### Without pooling

Usually:

```text
con.close()
     ↓
Connection is closed
```

### With pooling

Typically:

```text
con.close()
     ↓
Logical connection released
     ↓
Returned to pool
     ↓
Physical connection may remain available
```

So:

> **Never stop calling `close()` just because you're using a connection pool.**

In fact, failing to close pooled connections can exhaust the pool.

---

# 1.4 Physical vs logical connection

This distinction is important.

```text
Application
     ↓
Logical Connection
     ↓
Connection Pool
     ↓
Physical Connection
     ↓
Database
```

Your application sees a connection object.

The pool can wrap/manage the underlying physical database connection.

---

# 1.5 What happens when all connections are busy?

Suppose:

```text
Maximum pool size = 5
```

Currently:

```text
C1 → busy
C2 → busy
C3 → busy
C4 → busy
C5 → busy
```

Another request asks for a connection.

The pool may:

```text
wait
  ↓
connection becomes available
  ↓
give it to requester
```

or eventually:

```text
timeout
  ↓
exception/failure
```

depending on configuration.

---

# 1.6 Is a bigger pool better?

**No.**

Suppose:

```text
Pool = 500 connections
Database = limited resources
```

Five hundred simultaneous connections may put enormous pressure on the database.

Therefore:

> **Connection pool size is a tuning decision, not a "bigger is always better" setting.**

---

# 1.7 What does a connection pool manage?

Typical pooling implementations manage things such as:

* maximum pool size
* minimum/initial pool size
* idle connections
* connection acquisition
* connection return
* acquisition timeout
* validation
* connection lifetime
* leaked/abandoned connections

Exact features depend on the pool implementation.

---

# 1.8 Connection Pooling — DOUBTKILLER

### ❓ Why not simply create a new connection every time?

Because connection creation can be expensive.

### ❓ Is pooling a JDBC interface?

Pooling itself is a mechanism, not a core JDBC interface.

### ❓ Does `DataSource` automatically mean pooling?

**No.**

### ❓ Should pooled connections be closed?

**Yes.**

### ❓ What does closing a pooled connection usually do?

Returns/releases it to the pool.

### ❓ Is 1,000 connections better than 10?

Not necessarily. Too many connections can hurt performance.

---

# 2. DATASOURCE

## 2.1 What is `DataSource`?

`DataSource` is a JDBC interface for obtaining database connections.

Conceptually:

```java
Connection con =
    dataSource.getConnection();
```

Its central method is:

```java
getConnection()
```

---

# 2.2 DriverManager vs DataSource

### DriverManager

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

### DataSource

```java
Connection con =
    dataSource.getConnection();
```

---

# 2.3 The biggest doubt: Is DataSource a replacement for Connection?

**No.**

Think:

```text
DataSource
    ↓
provides
    ↓
Connection
```

`DataSource` is not the database connection itself.

---

# 2.4 Is DataSource a class?

`DataSource` is an **interface**:

```java
public interface DataSource
```

Implementations provide the actual behavior.

---

# 2.5 Does DataSource mean connection pooling?

**No.**

This is one of the most common JDBC misconceptions.

```text
DataSource
   ↓
Connection provider abstraction
```

A particular DataSource implementation might provide:

```text
Simple connections
```

or:

```text
Connection pooling
```

or:

```text
Container/application-managed connections
```

---

# 2.6 DataSource and pooling relationship

A common real-world architecture is:

```text
Application
     ↓
Pooled DataSource
     ↓
Connection Pool
     ↓
Database Connections
     ↓
Database
```

Therefore:

> A **pooled DataSource** is a common way to expose connection pooling to application code.

---

# 2.7 Why is DataSource preferred in larger applications?

It allows connection configuration to be separated from application logic.

Instead of every class knowing:

```text
URL
Username
Password
Driver
Pool configuration
```

application code can simply do:

```java
Connection con =
    dataSource.getConnection();
```

---

# 2.8 DataSource — DOUBTKILLER

### ❓ DataSource = Connection?

**No.**

### ❓ DataSource = Connection Pool?

**No.**

### ❓ What does DataSource provide?

Connections.

### ❓ Main method?

```java
getConnection()
```

### ❓ Can DataSource use pooling?

**Yes.** A DataSource implementation can be backed by a pool.

### ❓ Which is an abstraction: Connection or DataSource?

Both are interfaces in JDBC, but they represent different things:

```text
DataSource → source/provider of connections
Connection → database session/connection
```

---

# 3. ROWSET

## 3.1 What is RowSet?

`RowSet` is a JDBC interface that extends `ResultSet`.

```text
ResultSet
    ↑
  RowSet
```

It provides a more flexible way of working with tabular data.

---

# 3.2 Biggest doubt: Is RowSet a replacement for ResultSet?

Not exactly.

A `RowSet` is a type of JDBC result/data representation with additional capabilities.

Think:

```text
ResultSet
    ↓
standard query-result abstraction

RowSet
    ↓
ResultSet + additional RowSet capabilities
```

---

# 3.3 Why was RowSet introduced?

Traditional JDBC often looks like:

```text
Connection
    ↓
Statement
    ↓
ResultSet
```

The result set is closely associated with the database interaction.

Some applications need a more flexible object that can:

* be JavaBeans-oriented
* support disconnected operation
* be serializable, depending on implementation
* be passed between components
* work with data after the connection is released

---

# 3.4 RowSet types

Important standard implementations/interfaces include:

```text
RowSet
│
├── JdbcRowSet
├── CachedRowSet
├── WebRowSet
├── JoinRowSet
└── FilteredRowSet
```

---

# 3.5 JdbcRowSet

`JdbcRowSet` generally remains connected.

```text
Database
   ↕
JdbcRowSet
```

Think:

> Connected RowSet.

---

# 3.6 CachedRowSet

This is the important disconnected implementation.

Conceptually:

```text
Database
   ↓
Retrieve data
   ↓
CachedRowSet
   ↓
Connection released
   ↓
Work with cached data
```

Therefore:

> **CachedRowSet can allow application code to work with data without continuously holding a database connection.**

---

# 3.7 Biggest doubt: Does every RowSet work disconnected?

**No.**

Don't memorize:

```text
RowSet = disconnected
```

Instead:

```text
RowSet
   ├── connected implementations
   └── disconnected implementations
```

`CachedRowSet` is the classic disconnected example.

---

# 3.8 WebRowSet

`WebRowSet` provides XML-oriented representation of RowSet data.

Think:

```text
RowSet
  ↓
WebRowSet
  ↓
XML representation
```

---

# 3.9 JoinRowSet

Used for joining data from RowSets.

Conceptually:

```text
Student RowSet
      +
Course RowSet
      ↓
JoinRowSet
```

---

# 3.10 FilteredRowSet

Provides filtering capabilities.

```text
RowSet
   ↓
Filter
   ↓
Selected rows
```

---

# 3.11 RowSet — DOUBTKILLER

### ❓ RowSet is a class?

**No. It is an interface.**

### ❓ RowSet extends what?

`ResultSet`.

### ❓ Is every RowSet disconnected?

**No.**

### ❓ Which famous RowSet is disconnected?

`CachedRowSet`.

### ❓ Is RowSet the same as ResultSet?

**No.**

### ❓ Why use RowSet?

For additional flexibility in handling JDBC tabular data.

---

# 4. TRANSACTIONS

## 4.1 What is a transaction?

A transaction is a logical unit of database work.

Example:

```text
Transfer ₹1,000
       │
       ├── Debit Account A
       │
       └── Credit Account B
```

We don't want:

```text
Debit succeeds
Credit fails
```

leaving the operation partially completed.

---

# 4.2 Auto-commit

By default, JDBC connections normally start with:

```java
con.getAutoCommit()
```

returning:

```text
true
```

assuming the driver/database supports the normal JDBC behavior.

---

# 4.3 Auto-commit = true

Conceptually:

```text
SQL 1
 ↓
COMMIT

SQL 2
 ↓
COMMIT

SQL 3
 ↓
COMMIT
```

Each successfully completed statement is normally committed automatically.

---

# 4.4 Auto-commit = false

```java
con.setAutoCommit(false);
```

Now:

```text
SQL 1
SQL 2
SQL 3
    ↓
commit()
```

The application explicitly controls the transaction boundary.

---

# 4.5 `commit()`

```java
con.commit();
```

Means:

> Finish the current transaction and commit its changes.

---

# 4.6 `rollback()`

```java
con.rollback();
```

Means:

> Roll back uncommitted changes in the current transaction.

---

# 4.7 Classic transaction example

```java
try {
    con.setAutoCommit(false);

    debitAccount();
    creditAccount();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

Conceptually:

```text
             BEGIN WORK
                 ↓
             Debit A
                 ↓
             Credit B
                 ↓
            Everything OK?
             /       \
           YES       NO
            ↓         ↓
         COMMIT    ROLLBACK
```

---

# 4.8 Savepoint

Suppose:

```text
Operation 1
Operation 2
SAVEPOINT
Operation 3
Operation 4
```

Create:

```java
Savepoint sp =
    con.setSavepoint();
```

Then:

```java
con.rollback(sp);
```

means:

```text
Operation 1
Operation 2
     ↓
 SAVEPOINT
     ↓
Operation 3
Operation 4
     ↓
Failure
     ↓
ROLLBACK TO SAVEPOINT
```

The work before the savepoint remains part of the transaction.

---

# 4.9 Biggest doubt: Does rollback mean database goes back to the state before the application started?

Not necessarily.

It means the **uncommitted work in the current transaction** is rolled back according to transaction semantics.

If you already committed:

```text
SQL
 ↓
COMMIT
```

then later:

```java
rollback();
```

doesn't normally undo that earlier committed transaction.

---

# 4.10 Another huge doubt: What happens when a pooled connection is returned?

This is why transaction state must be managed carefully.

Suppose:

```java
con.setAutoCommit(false);
```

and then the connection is returned incorrectly without finishing the transaction.

A good pool/provider will typically reset or clean connection state, but application code should not rely on sloppy transaction handling.

Best practice:

```text
Begin transaction
     ↓
Commit OR rollback
     ↓
Close/release connection
```

---

# 4.11 Transactions — DOUBTKILLER

### ❓ Transaction = one SQL statement?

**No.**

A transaction can contain multiple SQL statements.

### ❓ `commit()` and `executeUpdate()` are the same?

No.

```text
executeUpdate()
→ execute DML and obtain update count

commit()
→ commit transaction changes
```

### ❓ `rollback()` commits anything?

No.

### ❓ Can a transaction contain 10 SQL statements?

Yes.

### ❓ Can a transaction have savepoints?

Yes.

### ❓ Does `commit()` mean "execute SQL"?

No.

It finalizes the transaction's pending changes.

---

# 5. ISOLATION LEVELS

This is where many students become confused.

---

# 5.1 Why do we need isolation?

Imagine:

```text
Transaction A          Transaction B
      │                      │
      └────── Database ──────┘
```

Both transactions may run concurrently.

We need rules about what one transaction can observe from another.

That's isolation.

---

# 5.2 Four standard JDBC isolation levels

```java
Connection.TRANSACTION_READ_UNCOMMITTED

Connection.TRANSACTION_READ_COMMITTED

Connection.TRANSACTION_REPEATABLE_READ

Connection.TRANSACTION_SERIALIZABLE
```

There is also:

```java
Connection.TRANSACTION_NONE
```

which indicates that transactions are not supported.

---

# 5.3 READ_UNCOMMITTED

Weakest standard transaction isolation level.

Transaction A may read changes made by B before B commits.

Example:

```text
B:
Balance = 5000
     ↓
not committed

A:
reads 5000
```

Then:

```text
B → ROLLBACK
```

The value A read may never have been committed.

This is:

> **Dirty Read**

---

# 5.4 READ_COMMITTED

A transaction generally sees only committed data from other transactions.

Therefore:

```text
Dirty Read → prevented
```

But:

```text
Non-repeatable Read → possible
Phantom Read → possible
```

---

# 5.5 REPEATABLE_READ

A row read by a transaction is protected from changing underneath it in the classic non-repeatable-read sense.

Generally:

```text
Dirty Read → prevented
Non-repeatable Read → prevented
```

Phantom behavior depends on the database's implementation and concurrency mechanism; JDBC's isolation definition allows phantom reads at this level.

---

# 5.6 SERIALIZABLE

Strongest standard JDBC isolation level.

It provides the strongest isolation guarantee among the standard levels.

Conceptually, conflicting transactions behave as though they were serialized.

```text
Transaction A
     ↓
complete
     ↓
Transaction B
```

But actual database implementations may use different locking/MVCC techniques.

---

# 5.7 Dirty vs Non-repeatable vs Phantom

This is a **must-know distinction**.

## Dirty Read

> Reading **uncommitted data**.

```text
B changes
   ↓
A reads
   ↓
B rollback
```

---

## Non-repeatable Read

> Reading the **same row twice** and getting different committed values.

```text
A reads → 100

B updates → 200 + COMMIT

A reads → 200
```

---

## Phantom Read

> Repeating a query and finding a **different set of matching rows**.

```text
A:
SELECT ... WHERE salary > 50000
→ 5 rows

B:
INSERT matching row
→ COMMIT

A:
same query
→ 6 rows
```

---

# 5.8 Isolation table

| Level            | Dirty     | Non-repeatable | Phantom         |
| ---------------- | --------- | -------------- | --------------- |
| READ_UNCOMMITTED | Possible  | Possible       | Possible        |
| READ_COMMITTED   | Prevented | Possible       | Possible        |
| REPEATABLE_READ  | Prevented | Prevented      | May be possible |
| SERIALIZABLE     | Prevented | Prevented      | Prevented       |

---

# 5.9 Setting isolation

```java
con.setTransactionIsolation(
    Connection.TRANSACTION_SERIALIZABLE
);
```

Reading it:

```java
int level =
    con.getTransactionIsolation();
```

---

# 5.10 Biggest doubt: Should I always use SERIALIZABLE?

**No.**

Higher isolation can mean:

```text
More consistency
      ↓
More locking/contention or other concurrency costs
      ↓
Potentially lower throughput
```

Choose the lowest level that correctly satisfies the application's consistency requirements.

---

# 5.11 Isolation — DOUBTKILLER

### ❓ Isolation controls what?

Concurrent transaction interaction/visibility.

### ❓ Does isolation replace transactions?

No.

They are related but different.

```text
Transaction
→ unit of work

Isolation
→ rules for concurrent transaction interaction
```

### ❓ Strongest standard JDBC isolation?

`TRANSACTION_SERIALIZABLE`.

### ❓ Weakest commonly used transactional isolation?

`TRANSACTION_READ_UNCOMMITTED`.

### ❓ Is `READ_COMMITTED` the same as `REPEATABLE_READ`?

No.

`REPEATABLE_READ` provides stronger guarantees regarding repeated reads of the same rows.

---

# 6. BATCH PROCESSING

## 6.1 What problem does batch solve?

Suppose:

```text
10,000 INSERT statements
```

Without batching:

```text
INSERT 1 → execute
INSERT 2 → execute
INSERT 3 → execute
...
INSERT 10000 → execute
```

With batching:

```text
INSERT 1
INSERT 2
INSERT 3
...
INSERT 10000
     ↓
executeBatch()
```

The driver/database can process the commands as a batch, potentially reducing overhead.

---

# 6.2 `addBatch()`

For `PreparedStatement`:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
ps.addBatch();
```

The configured operation is added to the batch.

---

# 6.3 `executeBatch()`

```java
int[] counts =
    ps.executeBatch();
```

Executes the accumulated commands.

The returned array contains per-command update counts or special batch-result values as defined by JDBC.

---

# 6.4 Why PreparedStatement + Batch?

Suppose:

```sql
INSERT INTO student VALUES (?, ?)
```

The SQL structure remains fixed.

Only values change:

```text
101, Ravi
102, John
103, Alice
```

Therefore:

```java
ps.setInt(...);
ps.setString(...);
ps.addBatch();
```

is a natural combination.

---

# 6.5 Biggest doubt: Is batch automatically transactional?

**No.**

Batching and transactions are different concepts.

```text
Batch
→ execution mechanism

Transaction
→ commit/rollback boundary
```

You can combine them:

```java
con.setAutoCommit(false);

ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();

con.commit();
```

If required:

```java
con.rollback();
```

---

# 6.6 Does batch guarantee one network request?

**No.**

Do not memorize:

> "Batch = exactly one network call."

Actual behavior depends on the JDBC driver and database.

The correct statement is:

> **Batch processing allows multiple commands to be submitted/executed as a batch, potentially reducing communication and execution overhead.**

---

# 6.7 Batch size

Don't blindly put millions of operations into one batch.

A huge batch can consume:

* application memory
* driver memory
* database resources
* transaction resources

Therefore applications often process large datasets in chunks.

```text
1000 rows
   ↓
batch

1000 rows
   ↓
batch

1000 rows
   ↓
batch
```

---

# 6.8 Batch — DOUBTKILLER

### ❓ Batch = transaction?

**No.**

### ❓ `addBatch()` executes SQL immediately?

Not normally. It adds the command to the batch.

### ❓ `executeBatch()` returns `ResultSet`?

No.

It returns:

```java
int[]
```

for update counts/batch result information.

### ❓ Does batching guarantee better performance?

Not universally, but it is commonly used to improve bulk-operation efficiency.

### ❓ Can PreparedStatement be batched?

Yes.

### ❓ Can Statement be batched?

Yes.

---

# 7. RESOURCE MANAGEMENT

This is one of the most important practical JDBC topics.

---

# 7.1 What are JDBC resources?

Common JDBC resources include:

```text
Connection
Statement
PreparedStatement
CallableStatement
ResultSet
```

---

# 7.2 Why must we close them?

Because resources may exist at both Java and database/driver levels.

For example:

```text
Connection
   ↓
Database session/resources
```

If connections are leaked:

```text
Connection 1 → leaked
Connection 2 → leaked
Connection 3 → leaked
...
```

Eventually:

```text
Pool
 ↓
No available connections
 ↓
Requests wait/fail
```

---

# 7.3 Correct approach

Use:

```java
try (
    Connection con = ...;
    PreparedStatement ps = ...;
    ResultSet rs = ...
) {
    ...
}
```

This is called:

> **try-with-resources**

---

# 7.4 Why try-with-resources?

Because resources implementing `AutoCloseable` are automatically closed.

JDBC resources such as:

* `Connection`
* `Statement`
* `PreparedStatement`
* `CallableStatement`
* `ResultSet`

support the necessary closeable contract.

---

# 7.5 Closing order

Suppose:

```java
try (
    Connection con = ...;
    PreparedStatement ps = ...;
    ResultSet rs = ...
) {
}
```

They are closed in reverse order:

```text
ResultSet
   ↓
PreparedStatement
   ↓
Connection
```

This is logical because:

```text
ResultSet depends on Statement
Statement depends on Connection
```

---

# 7.6 Biggest doubt: What if SQL throws an exception?

That's another reason try-with-resources is useful.

Example:

```java
try (
    Connection con = ...;
    PreparedStatement ps = ...;
    ResultSet rs = ...
) {
    // SQL processing

} catch (SQLException e) {
    e.printStackTrace();
}
```

Even if an exception occurs inside the `try` block, Java attempts to close the resources.

---

# 7.7 What if `close()` itself throws an exception?

Try-with-resources has special handling for this.

If the main operation throws one exception and closing a resource also throws another, the close exception can become a **suppressed exception** attached to the primary exception.

You can inspect suppressed exceptions:

```java
for (Throwable t : e.getSuppressed()) {
    t.printStackTrace();
}
```

This is an advanced interview point.

---

# 7.8 Resource Management + Pooling

Suppose:

```java
Connection con =
    dataSource.getConnection();
```

Then:

```java
try (con) {
    // work
}
```

At the end:

```text
close()
  ↓
Pooled DataSource
  ↓
Return connection to pool
```

Therefore:

> **Connection pooling does not remove the need for resource management.**

It makes proper resource release even more important.

---

# 7.9 Resource Management — DOUBTKILLER

### ❓ Should ResultSet be closed?

Yes.

### ❓ Should PreparedStatement be closed?

Yes.

### ❓ Should Connection be closed?

Yes.

### ❓ Does try-with-resources eliminate the need to call `close()` manually?

Inside the resource declaration, yes—the compiler/runtime-generated cleanup handles it.

### ❓ Does a pool mean connection doesn't need closing?

**Absolutely not.**

### ❓ What closes first?

The resource declared last is closed first.

---

# 8. THE SEVEN BIG CONFUSIONS

Now put all the confusing pairs side-by-side.

---

## Confusion 1: DataSource vs Connection Pool

```text
DataSource
   ↓
Provides connections

Connection Pool
   ↓
Reuses/manages connections
```

A DataSource **can be backed by** a connection pool.

---

## Confusion 2: DataSource vs Connection

```text
DataSource
   ↓
getConnection()
   ↓
Connection
```

DataSource **provides** Connection.

---

## Confusion 3: ResultSet vs RowSet

```text
ResultSet
   ↓
Standard JDBC result abstraction

RowSet
   ↓
JDBC interface extending ResultSet
   ↓
Additional capabilities
```

`CachedRowSet` supports disconnected use.

---

## Confusion 4: Transaction vs Isolation

```text
Transaction
→ What work belongs together?

Isolation
→ How do concurrent transactions interact?
```

---

## Confusion 5: Batch vs Transaction

```text
Batch
→ Efficiently submit/execute many commands

Transaction
→ Define commit/rollback boundary
```

They can be used together.

---

## Confusion 6: `commit()` vs `executeBatch()`

```text
executeBatch()
→ Execute accumulated batch commands

commit()
→ Commit the transaction
```

Different jobs.

---

## Confusion 7: `close()` vs Physical Connection Destruction

Without pooling:

```text
close()
→ close connection
```

With pooling:

```text
close()
→ release/return logical connection
→ physical connection may remain in pool
```

---

# 9. MASTER DOUBTKILLER TABLE

| Concept                 | What it does                                | What it does NOT mean                         |
| ----------------------- | ------------------------------------------- | --------------------------------------------- |
| **Connection Pooling**  | Reuses database connections                 | Doesn't mean unlimited connections            |
| **DataSource**          | Provides connections                        | Isn't itself a Connection                     |
| **RowSet**              | Flexible JDBC tabular-data abstraction      | Doesn't mean every RowSet is disconnected     |
| **Transaction**         | Groups logical database work                | Isn't the same as batch                       |
| **Isolation**           | Controls concurrent transaction interaction | Doesn't replace transactions                  |
| **Batch**               | Groups commands for efficient execution     | Isn't automatically a transaction             |
| **Resource Management** | Releases JDBC resources safely              | Doesn't mean "never close pooled connections" |

---

# 10. COMPLETE REAL-WORLD FLOW

Now let's combine all seven.

```text
                         APPLICATION
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
                 ┌────────────┴────────────┐
                 ↓                         ↓
            Transaction               Isolation
                 │                       Level
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
            ResultSet /
              RowSet
                 │
                 ↓
          Process Results
                 │
                 ↓
          commit / rollback
                 │
                 ↓
         try-with-resources
                 │
                 ↓
          close resources
                 │
                 ↓
        Return connection
           to pool
```

---

# 11. INTERVIEW TRAPS

### Q1. Is `DataSource` a connection pool?

**Answer:** No. `DataSource` is a JDBC interface for obtaining connections. A particular DataSource implementation may use connection pooling.

---

### Q2. Why call `close()` on a pooled connection?

**Answer:** To release the logical connection and return it to the pool.

---

### Q3. Which RowSet can work without continuously maintaining a connection?

**Answer:** `CachedRowSet` is the classic disconnected RowSet implementation.

---

### Q4. Which isolation level is strongest?

**Answer:**

```java
Connection.TRANSACTION_SERIALIZABLE
```

among the standard JDBC transaction isolation levels.

---

### Q5. Is `executeBatch()` the same as `commit()`?

**Answer:** No.

```text
executeBatch()
→ executes batch commands

commit()
→ commits transaction
```

---

### Q6. Can batch processing be used with transactions?

**Answer:** Yes.

```java
setAutoCommit(false)
       ↓
addBatch()
       ↓
executeBatch()
       ↓
commit()
```

---

### Q7. What is a dirty read?

> Reading another transaction's uncommitted changes.

---

### Q8. What is a non-repeatable read?

> Reading the same row twice and seeing different committed values.

---

### Q9. What is a phantom read?

> Repeating a query and seeing a different set of matching rows because another transaction inserted/deleted matching rows.

---

### Q10. Why use try-with-resources?

> To ensure JDBC resources are automatically closed, reducing resource leaks.

---

# 12. FINAL MEMORY FORMULA

Remember this sequence:

```text
DATA SOURCE
     ↓
GET CONNECTION
     ↓
POOL REUSES CONNECTION
     ↓
START TRANSACTION
     ↓
CHOOSE ISOLATION
     ↓
EXECUTE/BATCH SQL
     ↓
COMMIT OR ROLLBACK
     ↓
PROCESS RESULT / ROWSET
     ↓
CLOSE RESOURCES
     ↓
RETURN CONNECTION TO POOL
```

And remember the **one-word purpose** of each:

```text
Connection Pooling → REUSE
DataSource         → PROVIDE
RowSet             → FLEXIBILITY
Transaction        → ATOMIC UNIT
Isolation          → CONCURRENCY
Batch              → EFFICIENCY
Resource Management→ CLEANUP
```

### The ultimate distinction

> **DataSource gives you a Connection. Connection Pooling helps reuse that Connection. Transactions decide what work commits together. Isolation decides how concurrent transactions interact. Batch Processing makes multiple SQL operations efficient. RowSet provides flexible tabular-data handling. Resource Management ensures everything is released correctly.**
