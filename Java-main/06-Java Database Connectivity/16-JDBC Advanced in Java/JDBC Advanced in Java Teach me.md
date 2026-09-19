# 16. JDBC Advanced in Java / TEACHME

Let's learn this as if we're building a **real JDBC application step by step**.

The seven concepts are:

```text
JDBC ADVANCED
│
├── 1. Connection Pooling
├── 2. DataSource
├── 3. RowSet
├── 4. Transactions
├── 5. Isolation Levels
├── 6. Batch Processing
└── 7. Resource Management
```

The most important idea is:

> **Basic JDBC teaches you how to connect to a database and execute SQL. Advanced JDBC teaches you how to do that efficiently, safely, and reliably in a real application.**

---

# 1. Connection Pooling

## 1.1 First understand the problem

Suppose a Java web application receives a request.

The application needs data from a database.

It does:

```java
Connection con =
    DriverManager.getConnection(url, user, password);
```

Then:

```text
Java Application
      ↓
Create Connection
      ↓
Database
      ↓
Execute SQL
      ↓
Close Connection
```

Now imagine **10,000 requests**.

If every request creates a new database connection:

```text
Request 1 → Create → Use → Close
Request 2 → Create → Use → Close
Request 3 → Create → Use → Close
...
Request 10000 → Create → Use → Close
```

That's inefficient.

---

## 1.2 Why is creating a connection expensive?

Creating a connection can involve:

```text
Java Application
       ↓
JDBC Driver
       ↓
Network
       ↓
Database
       ↓
Authentication
       ↓
Database Session
       ↓
Connection
```

So connection creation isn't just:

```java
new Connection();
```

It involves communication with the database.

---

# 1.3 Solution: Connection Pooling

Instead of creating a new connection for every request, create a **pool of reusable connections**.

Think of a swimming pool.

Instead of building a new swimming pool every time someone wants to swim:

```text
Person → use pool → leave
Person → use pool → leave
```

Similarly:

```text
Application
     ↓
Connection Pool
 ┌────┬────┬────┬────┐
 │ C1 │ C2 │ C3 │ C4 │
 └────┴────┴────┴────┘
     ↓
 Database
```

The application **borrows** a connection.

After using it, it **returns** the connection to the pool.

---

## 1.4 Pooling lifecycle

```text
Application
    ↓
Request connection
    ↓
Connection Pool
    ↓
Give C2
    ↓
Execute SQL
    ↓
con.close()
    ↓
Return C2 to pool
```

Very important:

### Does `con.close()` destroy the physical connection?

In a typical connection pool:

**No.**

It generally means:

> "I'm finished using this logical connection; return it to the pool."

So:

```text
con.close()
     ↓
Return to pool
```

not necessarily:

```text
con.close()
     ↓
Destroy physical database connection
```

---

# 1.5 Physical vs logical connection

This is a very important concept.

```text
Java Application
       ↓
Logical Connection
       ↓
Connection Pool
       ↓
Physical Connection
       ↓
Database
```

The application works with the logical connection.

The pool manages the physical connection.

---

# 1.6 Why pooling improves performance

Without pooling:

```text
Create connection
       ↓
Authentication
       ↓
Network setup
       ↓
SQL
       ↓
Close
```

With pooling:

```text
Borrow connection
       ↓
SQL
       ↓
Return connection
```

So repeated connection creation can be avoided.

---

# 1.7 What happens if all connections are busy?

Suppose the pool has:

```text
Maximum = 5 connections
```

And:

```text
C1 → busy
C2 → busy
C3 → busy
C4 → busy
C5 → busy
```

A new request cannot immediately borrow a connection.

Depending on the pool configuration, it may:

```text
Wait
 ↓
Connection becomes available
 ↓
Request gets connection
```

or eventually fail after a timeout.

---

# 1.8 Is a bigger pool always better?

**No.**

Suppose:

```text
1000 requests
1000 database connections
```

That doesn't automatically make the application faster.

The database itself has limited:

* CPU
* memory
* network capacity
* concurrent session capacity
* disk/I/O resources

A huge connection pool can overwhelm the database.

So connection pool size must be configured according to the application's and database's workload.

---

# 1.9 What problem does pooling solve?

Remember:

> **Connection Pooling = Reuse database connections.**

Main benefits:

* reduced connection creation overhead
* better throughput
* controlled connection usage
* improved scalability

---

# 2. DataSource

Now ask:

> Where does my application get these pooled connections from?

This is where `DataSource` becomes important.

---

# 2.1 What is DataSource?

`DataSource` is a JDBC interface used to obtain database connections.

```java
DataSource ds = ...;

Connection con =
    ds.getConnection();
```

The important method is:

```java
getConnection()
```

---

# 2.2 DriverManager vs DataSource

Traditional JDBC:

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

With DataSource:

```java
Connection con =
    dataSource.getConnection();
```

The application doesn't necessarily have to manage the connection details itself.

---

# 2.3 Why is DataSource useful?

Imagine a large application.

You don't want every class to know:

```text
Database URL
Username
Password
Driver configuration
Pool configuration
```

Instead:

```text
Application
     ↓
DataSource
     ↓
Connection
```

The environment can manage how the connection is obtained.

---

# 2.4 Does DataSource always mean connection pooling?

**No.**

This is an important interview question.

`DataSource` is an **interface**.

Different implementations can provide different behavior.

Conceptually:

```text
DataSource
   │
   ├── Simple DataSource
   │
   ├── Pooled DataSource
   │
   └── Managed DataSource
```

So:

> **DataSource ≠ automatically connection pool.**

But pooled DataSource implementations are commonly used in real applications.

---

# 2.5 The relationship

Think:

```text
DataSource
    ↓
provides Connection
```

And often:

```text
DataSource
    ↓
Connection Pool
    ↓
Connection
```

---

# 2.6 Easy memory trick

> **DataSource = Where/how I obtain a connection.**

> **Connection Pool = How connections are efficiently reused.**

---

# 3. RowSet

Now let's move to another concept.

Suppose you execute:

```sql
SELECT * FROM student;
```

JDBC normally gives you:

```java
ResultSet
```

But sometimes applications need a more flexible representation.

That's where `RowSet` comes in.

---

# 3.1 What is RowSet?

`RowSet` is a JDBC interface that extends `ResultSet`.

Conceptually:

```text
ResultSet
    ↑
  RowSet
```

It represents tabular data but provides additional flexibility.

---

# 3.2 Why do we need RowSet?

A normal `ResultSet` is commonly associated with:

```text
Connection
   ↓
Statement
   ↓
ResultSet
```

The result is usually processed while the database resources remain available.

But sometimes we want:

```text
Database
   ↓
Retrieve data
   ↓
Disconnect
   ↓
Continue working with data
```

A disconnected RowSet can help with this.

---

# 3.3 Types of RowSet

Important standard RowSet types:

```text
RowSet
│
├── JdbcRowSet
├── CachedRowSet
├── WebRowSet
├── JoinRowSet
└── FilteredRowSet
```

Let's understand them.

---

# 3.4 JdbcRowSet

`JdbcRowSet` is generally connected to the database.

Think:

```text
Database
   ↕
JdbcRowSet
```

It provides RowSet behavior while maintaining the connection.

---

# 3.5 CachedRowSet

This is especially important.

`CachedRowSet` can work in a **disconnected** manner.

Imagine:

```text
Database
   ↓
Fetch data
   ↓
CachedRowSet
   ↓
Close database connection
   ↓
Continue working
```

Example idea:

```text
100 student records
       ↓
CachedRowSet
       ↓
Connection released
       ↓
Process records
```

This can be useful when you don't want to keep a database connection occupied while processing data.

---

# 3.6 WebRowSet

`WebRowSet` extends the disconnected RowSet concept and provides an XML-oriented representation of RowSet data.

Think:

```text
RowSet
  ↓
WebRowSet
  ↓
XML representation
```

---

# 3.7 JoinRowSet

Used to combine related RowSet data.

Conceptually:

```text
Student RowSet
      +
Course RowSet
      ↓
JoinRowSet
```

---

# 3.8 FilteredRowSet

Allows filtering of RowSet data.

```text
RowSet
  ↓
Filter
  ↓
Matching rows
```

---

# 3.9 Easy memory trick

> **ResultSet = normal JDBC query result.**

> **RowSet = more flexible ResultSet-style tabular data.**

> **CachedRowSet = especially useful for disconnected work.**

---

# 4. Transactions

Now let's learn one of the most important JDBC concepts.

Imagine a bank transfer.

You want:

```text
Account A → Account B
₹1000
```

Two operations are needed:

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

---

# 4.1 The problem

Suppose this happens:

```text
A: ₹5000
B: ₹2000
```

First operation succeeds:

```text
A: ₹4000
B: ₹2000
```

Then the second operation fails.

Now ₹1000 has disappeared from the intended transfer.

We need both operations to behave as one logical unit.

---

# 4.2 Transaction

A **transaction** is a logical unit of database work.

We want:

```text
Transaction
   │
   ├── Debit A
   │
   └── Credit B
```

Then:

```text
Everything successful
        ↓
     COMMIT
```

or:

```text
Something fails
        ↓
     ROLLBACK
```

---

# 4.3 Auto-commit

By default, JDBC connections normally start with auto-commit enabled.

Check:

```java
boolean value =
    con.getAutoCommit();
```

To disable:

```java
con.setAutoCommit(false);
```

---

# 4.4 What does auto-commit mean?

Suppose:

```java
con.setAutoCommit(true);
```

Then each successful SQL statement is normally committed automatically.

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

This is not suitable for every multi-step business operation.

---

# 4.5 Manual transaction

Disable auto-commit:

```java
con.setAutoCommit(false);
```

Then:

```text
SQL 1
SQL 2
SQL 3
   ↓
commit()
```

Now the application explicitly controls the transaction boundary.

---

# 4.6 commit()

```java
con.commit();
```

Means:

> Successfully finish the current transaction and make its changes committed according to the database's transaction semantics.

---

# 4.7 rollback()

```java
con.rollback();
```

Means:

> Discard the uncommitted changes in the current transaction.

---

# 4.8 Simple transaction example

```java
try {
    con.setAutoCommit(false);

    // Operation 1
    // Operation 2

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

Conceptually:

```text
setAutoCommit(false)
        ↓
Operation 1
        ↓
Operation 2
        ↓
   Everything OK?
     /       \
   YES       NO
    ↓         ↓
 COMMIT    ROLLBACK
```

---

# 4.9 Savepoint

Suppose you have:

```text
Transaction
   ↓
Operation 1
   ↓
Operation 2
   ↓
Operation 3
   ↓
Operation 4
```

You don't necessarily want to roll everything back if Operation 4 fails.

You can create a savepoint:

```java
Savepoint sp =
    con.setSavepoint();
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
Operation 4
    ↓
Failure
    ↓
ROLLBACK TO SAVEPOINT
```

So the transaction can continue from the savepoint, subject to the database/JDBC semantics.

---

# 4.10 Transaction vs Savepoint

### Transaction

```text
Entire logical unit
```

### Savepoint

```text
Checkpoint inside a transaction
```

Memory:

> **Transaction = whole journey.**

> **Savepoint = checkpoint during the journey.**

---

# 5. Isolation Levels

Now suppose multiple users access the database simultaneously.

For example:

```text
User A
   ↓
Database
   ↑
User B
```

Both transactions can execute at the same time.

The question becomes:

> How much should one transaction be able to see from another transaction?

That's the purpose of **isolation levels**.

---

# 5.1 The four common isolation levels

JDBC defines:

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

# 5.2 Dirty Read

Suppose B changes:

```text
Balance = 5000
```

but has not committed.

A reads:

```text
5000
```

Then B rolls back.

Now the actual committed value may still be:

```text
4000
```

A read something that was never committed.

That's a:

> **Dirty Read**

---

# 5.3 Non-repeatable Read

A reads:

```text
Balance = 4000
```

B changes it:

```text
Balance = 5000
```

B commits.

A reads the same row again:

```text
Balance = 5000
```

Same row, different value.

That's:

> **Non-repeatable Read**

---

# 5.4 Phantom Read

A executes:

```sql
SELECT * FROM employee
WHERE salary > 50000;
```

Gets:

```text
5 rows
```

B inserts another employee with salary > 50000 and commits.

A executes the same query again.

Gets:

```text
6 rows
```

The additional row is a:

> **Phantom row**

---

# 5.5 Isolation levels explained simply

## READ_UNCOMMITTED

Allows the weakest isolation.

Dirty reads may occur.

```text
Consistency: lower
Concurrency: higher
```

---

## READ_COMMITTED

Prevents dirty reads.

But:

```text
Non-repeatable read → possible
Phantom read → possible
```

---

## REPEATABLE_READ

Provides stronger protection.

Typically:

```text
Dirty read → prevented
Non-repeatable read → prevented
Phantom read → may still be possible
```

---

## SERIALIZABLE

Strongest standard JDBC isolation level.

Conceptually:

```text
Transaction A
     ↓
complete

Transaction B
     ↓
complete
```

rather than allowing conflicting concurrent effects.

It generally provides the strongest isolation but may reduce concurrency and performance.

---

# 5.6 Isolation table

| Isolation        | Dirty Read | Non-repeatable Read | Phantom Read    |
| ---------------- | ---------- | ------------------- | --------------- |
| READ_UNCOMMITTED | Possible   | Possible            | Possible        |
| READ_COMMITTED   | Prevented  | Possible            | Possible        |
| REPEATABLE_READ  | Prevented  | Prevented           | May be possible |
| SERIALIZABLE     | Prevented  | Prevented           | Prevented       |

Database implementations can have additional behavior and differences.

---

# 5.7 Setting isolation

```java
con.setTransactionIsolation(
    Connection.TRANSACTION_READ_COMMITTED
);
```

Check it:

```java
int level =
    con.getTransactionIsolation();
```

---

# 5.8 Easy memory

Think:

```text
READ_UNCOMMITTED
       ↓
READ_COMMITTED
       ↓
REPEATABLE_READ
       ↓
SERIALIZABLE
```

Generally:

```text
Isolation ↑
Consistency ↑
Potential concurrency ↓
```

---

# 6. Batch Processing

Now suppose we want to insert 10,000 students.

Without batch:

```text
INSERT 1 → Database
INSERT 2 → Database
INSERT 3 → Database
...
INSERT 10000 → Database
```

That's a lot of repeated execution/communication.

---

# 6.1 Batch processing

Instead:

```text
INSERT 1
INSERT 2
INSERT 3
...
INSERT 10000
      ↓
executeBatch()
```

The operations are accumulated and then executed as a batch.

---

# 6.2 addBatch()

```java
ps.addBatch();
```

means:

> Add the current configured operation to the batch.

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
```

At this point, the batch contains two operations.

---

# 6.3 executeBatch()

```java
int[] result =
    ps.executeBatch();
```

means:

> Execute the accumulated batch.

Conceptually:

```text
addBatch()
addBatch()
addBatch()
   ↓
executeBatch()
   ↓
Database
```

---

# 6.4 Why PreparedStatement + Batch is powerful

Suppose:

```sql
INSERT INTO student
VALUES (?, ?)
```

The SQL structure remains the same.

Only values change:

```text
101, Ravi
102, John
103, Alice
```

So:

```java
ps.setInt(...);
ps.setString(...);
ps.addBatch();
```

is ideal for repeated operations.

---

# 6.5 Batch vs individual execution

### Individual

```text
SQL 1 → DB
SQL 2 → DB
SQL 3 → DB
SQL 4 → DB
```

### Batch

```text
SQL 1
SQL 2
SQL 3
SQL 4
 ↓
Batch
 ↓
DB
```

Batching can reduce overhead and improve throughput.

But the actual performance depends on:

* JDBC driver
* database
* network
* batch size
* SQL operations
* database configuration

---

# 6.6 Batch and transaction are different

This is a very important interview point.

### Batch

Answers:

> How can I execute many operations efficiently?

### Transaction

Answers:

> How can I make multiple operations behave as one logical unit?

Therefore:

```text
Batch → Efficiency
Transaction → Atomicity/Consistency
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

If something goes wrong:

```java
con.rollback();
```

---

# 7. Resource Management

Now imagine this:

```java
Connection con =
    dataSource.getConnection();
```

Then:

```java
PreparedStatement ps =
    con.prepareStatement(...);
```

Then:

```java
ResultSet rs =
    ps.executeQuery();
```

You now have:

```text
Connection
PreparedStatement
ResultSet
```

All of these need proper lifecycle management.

---

# 7.1 Why must resources be closed?

If you don't close resources:

```text
Connection 1 → leaked
Connection 2 → leaked
Connection 3 → leaked
...
```

Eventually:

```text
Connection Pool
       ↓
No available connections
       ↓
Application requests wait/fail
```

This is called a:

> **Connection leak**

---

# 7.2 Resources that need attention

Important JDBC resources:

```text
Connection
Statement
PreparedStatement
CallableStatement
ResultSet
```

---

# 7.3 Old-style cleanup

You might see:

```java
Connection con = null;
PreparedStatement ps = null;
ResultSet rs = null;

try {
    con = ...;
    ps = con.prepareStatement(...);
    rs = ps.executeQuery();

} finally {

    if (rs != null)
        rs.close();

    if (ps != null)
        ps.close();

    if (con != null)
        con.close();
}
```

This works, but it is verbose.

---

# 7.4 Modern solution: try-with-resources

Java provides:

```java
try (
    Connection con = ...;
    PreparedStatement ps = ...;
    ResultSet rs = ...
) {
    // work
}
```

When execution leaves the block, Java automatically closes the resources.

---

# 7.5 Complete example

```java
try (
    Connection con =
        dataSource.getConnection();

    PreparedStatement ps =
        con.prepareStatement(
            "SELECT id, name FROM student"
        );

    ResultSet rs =
        ps.executeQuery()
) {

    while (rs.next()) {

        int id =
            rs.getInt("id");

        String name =
            rs.getString("name");

        System.out.println(
            id + " " + name
        );
    }

} catch (SQLException e) {

    e.printStackTrace();
}
```

Notice:

```text
Connection
PreparedStatement
ResultSet
```

are all automatically closed.

---

# 7.6 Closing order

Suppose:

```java
try (
    Connection con = ...;
    PreparedStatement ps = ...;
    ResultSet rs = ...
) {
    ...
}
```

Java closes them in reverse order:

```text
ResultSet
    ↓
PreparedStatement
    ↓
Connection
```

Why?

Because:

```text
ResultSet depends on Statement
Statement depends on Connection
```

So dependent resources are closed first.

---

# 7.7 try-with-resources and connection pools

This is another important point.

Suppose:

```java
Connection con =
    dataSource.getConnection();
```

Then:

```java
try (con) {
    ...
}
```

At the end:

```java
con.close();
```

In a pooled environment:

```text
close()
  ↓
Return logical connection
  ↓
Connection Pool
```

So **you should still close pooled connections**.

Never think:

> "It's pooled, so I don't need to close it."

The opposite is true:

> **Pooled connections must be returned to the pool promptly.**

---

# 8. Let's Put Everything Together

Imagine a real web application.

A request comes in:

```text
User Request
     ↓
Application
```

The application needs a database connection:

```text
Application
     ↓
DataSource
     ↓
Connection Pool
     ↓
Connection
```

Now it starts a transaction:

```text
Connection
     ↓
setAutoCommit(false)
```

It executes many operations:

```text
PreparedStatement
     ↓
addBatch()
     ↓
addBatch()
     ↓
addBatch()
     ↓
executeBatch()
```

Isolation controls how concurrent transactions interact:

```text
Connection
     ↓
Isolation Level
```

If everything succeeds:

```text
commit()
```

If something fails:

```text
rollback()
```

Finally:

```text
try-with-resources
     ↓
close()
     ↓
Return connection to pool
```

---

# 9. Complete Mental Picture

```text
                     USER REQUEST
                          │
                          ↓
                    JAVA APPLICATION
                          │
                          ↓
                      DataSource
                          │
                          ↓
                  ┌───────────────┐
                  │ Connection    │
                  │     Pool      │
                  └───────────────┘
                          │
                          ↓
                     Connection
                          │
             ┌────────────┴────────────┐
             ↓                         ↓
       Transaction               Isolation Level
             │                         │
             ↓                         ↓
      PreparedStatement          Concurrent access
             │
             ↓
       Batch Processing
             │
             ↓
          Database
             │
             ↓
        ResultSet/RowSet
```

And surrounding everything:

```text
       TRY-WITH-RESOURCES
              ↓
       Resource Management
              ↓
          close safely
```

---

# 10. The Biggest Confusions — Removed

## Confusion 1

### DataSource = Connection Pool?

**No.**

```text
DataSource = interface for obtaining connections
Connection Pool = connection reuse mechanism
```

A pooled `DataSource` is commonly used in real applications.

---

## Confusion 2

### `Connection.close()` with pooling = physical connection destroyed?

Usually **no**.

```text
Application
    ↓
logical connection
    ↓
close()
    ↓
return to pool
```

---

## Confusion 3

### Batch = Transaction?

No.

```text
Batch
→ efficiency

Transaction
→ atomicity / consistency
```

They can work together.

---

## Confusion 4

### Higher isolation = always better?

No.

Higher isolation generally means stronger consistency but can reduce concurrency and increase contention.

---

## Confusion 5

### RowSet = ResultSet?

Not exactly.

`RowSet` extends `ResultSet` and provides additional flexibility, including disconnected implementations.

---

## Confusion 6

### Pooled connections don't need closing?

**Wrong.**

Always release them.

```java
try (Connection con =
         dataSource.getConnection()) {
    ...
}
```

---

# 11. Teach-Yourself Memory Story

Imagine a restaurant.

### DataSource

The restaurant's **reception desk** gives you a table.

```text
DataSource
→ "Here's your connection."
```

### Connection Pool

The restaurant has a fixed number of tables.

```text
Connection Pool
→ Reusable tables
```

When you're finished, you don't destroy the table.

```text
close()
→ Return table
```

### Transaction

Your order contains multiple things that must be handled together.

```text
Order
→ Main course
→ Side dish
→ Payment
```

Either the operation succeeds appropriately or you roll back.

### Isolation

Multiple customers are using the restaurant simultaneously.

Isolation determines how much one customer's in-progress activity should interfere with what another customer can observe.

### Batch

Instead of asking the waiter:

```text
"Bring item 1."
"Bring item 2."
"Bring item 3."
"Bring item 4."
```

you give one combined order.

```text
Batch
→ many operations together
```

### Resource Management

When you're done:

```text
Return table
Clean resources
```

That's `close()`.

### RowSet

Instead of staying connected to the restaurant while working with your copied order information, you can take a suitable copy with you and work with it separately.

That's the basic intuition behind disconnected RowSet usage.

---

# 12. One Concept → One Question

For exam/interview preparation, ask yourself:

### Connection Pooling

> **How can I avoid repeatedly creating expensive database connections?**

**Answer:** Connection pooling.

---

### DataSource

> **How can an application obtain database connections through a flexible JDBC abstraction?**

**Answer:** `DataSource`.

---

### RowSet

> **How can I work with JDBC tabular data in a more flexible way, including disconnected scenarios?**

**Answer:** `RowSet`.

---

### Transactions

> **How can multiple SQL operations behave as one logical unit?**

**Answer:** Transactions.

---

### Isolation Levels

> **How should concurrent transactions interact with each other's changes?**

**Answer:** Isolation levels.

---

### Batch Processing

> **How can I execute many similar SQL operations efficiently?**

**Answer:** Batch processing.

---

### Resource Management

> **How do I prevent JDBC resources from leaking?**

**Answer:** Proper closing, preferably try-with-resources.

---

# 13. Final TEACHME Summary

```text
1. CONNECTION POOLING
   ↓
   Reuse connections
   ↓
   Better performance/scalability


2. DATASOURCE
   ↓
   Obtain connections
   ↓
   Common abstraction for managed/pooling environments


3. ROWSET
   ↓
   Flexible ResultSet-style data
   ↓
   CachedRowSet can work disconnected


4. TRANSACTIONS
   ↓
   Group related SQL operations
   ↓
   commit() / rollback()


5. ISOLATION LEVELS
   ↓
   Control concurrent transaction visibility
   ↓
   READ_UNCOMMITTED
   READ_COMMITTED
   REPEATABLE_READ
   SERIALIZABLE


6. BATCH PROCESSING
   ↓
   addBatch()
   ↓
   executeBatch()
   ↓
   Efficient bulk operations


7. RESOURCE MANAGEMENT
   ↓
   Close JDBC resources
   ↓
   try-with-resources
   ↓
   Prevent leaks
```

## The one sentence that connects the entire topic

> **Advanced JDBC is about obtaining connections efficiently (`DataSource` + pooling), working with data flexibly (`RowSet`), controlling database work safely (`Transactions` + isolation), executing large workloads efficiently (`Batch Processing`), and releasing everything correctly (`Resource Management`).**
