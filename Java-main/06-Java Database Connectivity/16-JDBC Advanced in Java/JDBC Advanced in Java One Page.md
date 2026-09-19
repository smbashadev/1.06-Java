16. JDBC Advanced in Java / ONEPAGE

JDBC advanced concepts are mainly about making database applications faster, safer, scalable, transaction-safe, and resource-efficient.

The seven important concepts are:

JDBC ADVANCED
│
├── Connection Pooling
├── DataSource
├── RowSet
├── Transactions
├── Isolation Levels
├── Batch Processing
└── Resource Management


---

1. Connection Pooling

Definition

Connection pooling means maintaining a collection (pool) of already-created database connections and reusing them instead of creating a new physical database connection for every operation.

Without pooling

Application
   ↓
Create Connection
   ↓
Use Connection
   ↓
Close Connection
   ↓
Create another Connection
   ↓
Use
   ↓
Close

Creating a database connection repeatedly can be expensive.

With pooling

CONNECTION POOL
          +------+------+------+
          |      |      |      |
          C1     C2     C3     C4
          +------+------+------+
                 ↑
                 |
            Application

The application borrows a connection, uses it, and returns it to the pool.

Important point

When you call:

connection.close();

on a pooled connection, it commonly means:

> Return the connection to the pool.



It does not necessarily mean that the underlying physical database connection is destroyed.


---

Why use connection pooling?

Better performance

Less connection-creation overhead

Better scalability

Efficient resource utilization

Useful for applications handling many database requests


Typical applications:

Web applications

Enterprise applications

Application servers

High-traffic services



---

2. DataSource

Definition

DataSource is a JDBC interface used as an alternative to directly using DriverManager for obtaining database connections.

DataSource ds;
Connection con = ds.getConnection();

Basic relationship:

DataSource
    ↓
getConnection()
    ↓
Connection


---

Why is DataSource important?

DataSource is commonly used with:

connection pooling

application servers

JNDI

enterprise applications

centralized database configuration


Compare:

Connection con =
    DriverManager.getConnection(url, user, password);

with:

Connection con =
    dataSource.getConnection();

DataSource is generally the preferred abstraction for managed/pooling environments.


---

Important distinction

DataSource itself does not automatically mean connection pooling.

There can be different implementations:

DataSource
   |
   +-- Basic DataSource
   |
   +-- Pooled DataSource

A pooling implementation can manage reusable connections.


---

3. RowSet

Definition

RowSet is a JDBC interface that extends ResultSet and provides a more flexible representation of tabular data.

It can be:

connected or disconnected,

configurable through properties,

easier to use in certain application architectures,

and, depending on the implementation, serializable.


Hierarchy:

ResultSet
    ↑
  RowSet

More precisely, RowSet extends ResultSet.


---

Important RowSet types

Common standard RowSet interfaces include:

RowSet
│
├── JdbcRowSet
├── CachedRowSet
├── WebRowSet
├── JoinRowSet
└── FilteredRowSet

JdbcRowSet

Generally remains connected to the database.

Database
   ↕
JdbcRowSet

CachedRowSet

Can work in a disconnected manner.

Database
   ↓
CachedRowSet
   ↓
Disconnect
   ↓
Work with data

This can be useful when you don't want to keep a database connection open while manipulating the data.


---

4. Transactions

Definition

A transaction is a logical unit of database work consisting of one or more SQL operations that should be treated as a single unit.

Example: transferring money:

Account A
   ↓
Subtract ₹1000

Account B
   ↓
Add ₹1000

Both operations should succeed together.

If the second operation fails, we should undo the first.

Transaction
   |
   +-- UPDATE Account A
   |
   +-- UPDATE Account B
   |
   +-- COMMIT

If something goes wrong:

ROLLBACK


---

Auto-commit

By default, a JDBC connection commonly starts with auto-commit enabled.

con.getAutoCommit();

To disable it:

con.setAutoCommit(false);

Then:

// SQL 1
// SQL 2
// SQL 3

con.commit();

If something fails:

con.rollback();


---

Basic transaction pattern

try {
    con.setAutoCommit(false);

    // SQL operation 1
    // SQL operation 2

    con.commit();

} catch (SQLException e) {

    con.rollback();
}

The exact production pattern should also handle cleanup and rollback failures appropriately.


---

5. Isolation Levels

Definition

An isolation level controls how one transaction is allowed to see changes made by other concurrent transactions.

JDBC provides standard isolation constants through Connection.

TRANSACTION_NONE
TRANSACTION_READ_UNCOMMITTED
TRANSACTION_READ_COMMITTED
TRANSACTION_REPEATABLE_READ
TRANSACTION_SERIALIZABLE

Set it using:

con.setTransactionIsolation(
    Connection.TRANSACTION_READ_COMMITTED
);


---

The common problems

Dirty Read

Transaction A reads data that transaction B has changed but not committed.

B: UPDATE
A: READ uncommitted value
B: ROLLBACK

A read something that ultimately never existed as committed data.


---

Non-repeatable Read

A transaction reads the same row twice and gets different committed values because another transaction modified it between the reads.


---

Phantom Read

A transaction repeats a query and gets a different set of rows because another transaction inserted/deleted matching rows.


---

Isolation levels overview

Isolation Level	Dirty Read	Non-repeatable Read	Phantom Read

Read Uncommitted	Possible	Possible	Possible
Read Committed	Prevented	Possible	Possible
Repeatable Read	Prevented	Prevented	May be possible*
Serializable	Prevented	Prevented	Prevented


* Exact behavior can depend on the database implementation.

Easy memory

READ UNCOMMITTED
        ↓
     weakest

READ COMMITTED
        ↓

REPEATABLE READ
        ↓

SERIALIZABLE
        ↓
     strongest

Higher isolation generally provides stronger consistency but can reduce concurrency/performance.


---

6. Batch Processing

Definition

Batch processing means sending multiple SQL operations together instead of executing each operation separately.

Individual execution

INSERT 1 → Database
INSERT 2 → Database
INSERT 3 → Database
INSERT 4 → Database

Batch execution

INSERT 1
INSERT 2
INSERT 3
INSERT 4
      ↓
executeBatch()
      ↓
Database


---

Basic JDBC example

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

int[] results =
    ps.executeBatch();

Important methods:

addBatch()
    ↓
Adds operation to batch

executeBatch()
    ↓
Executes accumulated operations


---

Advantages

Fewer round trips

Better throughput

Efficient for large numbers of similar operations

Useful for bulk INSERT/UPDATE/DELETE operations


Batch execution should often be combined thoughtfully with transactions.


---

7. Resource Management

JDBC uses resources that must be released properly.

Important resources include:

Connection
Statement
PreparedStatement
CallableStatement
ResultSet

If resources aren't closed properly, applications can suffer from:

connection leaks

memory/resource consumption

exhausted connection pools

poor performance

eventual application failures



---

8. try-with-resources

Modern Java provides try-with-resources for automatic closing of AutoCloseable resources.

Example:

String sql =
    "SELECT id, name FROM student";

try (
    Connection con =
        DriverManager.getConnection(
            url, user, password
        );

    PreparedStatement ps =
        con.prepareStatement(sql);

    ResultSet rs =
        ps.executeQuery()
) {

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

When the try block finishes, the resources are automatically closed.


---

9. Resource Closing Order

Conceptually:

ResultSet
    ↓
Statement
    ↓
Connection

With nested try-with-resources, Java closes resources in reverse order of declaration.

For example:

try (
    Connection con = ...;
    PreparedStatement ps = ...;
    ResultSet rs = ...
) {
    ...
}

Closing happens approximately:

rs.close()
    ↓
ps.close()
    ↓
con.close()

This is one reason try-with-resources is preferred over manually managing finally blocks.


---

10. Complete Advanced JDBC Picture

JDBC ADVANCED
                           |
       +-------------------+-------------------+
       |                   |                   |
       ↓                   ↓                   ↓
Connection Pooling      DataSource         RowSet
       |
       ↓
Reuse connections


       +-------------------+-------------------+
       |                                       |
       ↓                                       ↓
   Transactions                         Isolation Levels
       |                                       |
   commit()                              concurrency
   rollback()                              control
       |
       ↓
 atomic database work


       +-------------------+-------------------+
       |                                       |
       ↓                                       ↓
 Batch Processing                      Resource Management
       |                                       |
 addBatch()                              try-with-resources
 executeBatch()                          close resources


---

11. ONEPAGE Final Revision Table

Concept	Main Purpose	Key API/Method

Connection Pooling	Reuse database connections	Pool implementation
DataSource	Obtain/manage connections through a higher-level abstraction	getConnection()
RowSet	Flexible ResultSet-like tabular data	RowSet
Transactions	Treat multiple operations as one logical unit	commit(), rollback()
Isolation Levels	Control concurrent transaction visibility	setTransactionIsolation()
Batch Processing	Execute many operations efficiently	addBatch(), executeBatch()
Resource Management	Prevent resource leaks	try-with-resources



---

12. Final Memory Map

CONNECTION POOLING
→ Reuse connections

DATASOURCE
→ Obtain/manage connections

ROWSET
→ Flexible ResultSet

TRANSACTION
→ Multiple operations as one unit

ISOLATION LEVEL
→ Control concurrent transaction visibility

BATCH
→ Many SQL operations together

RESOURCE MANAGEMENT
→ Close JDBC resources safely

One-line exam definitions

> Connection Pooling: Reuses database connections to reduce connection-creation overhead.



> DataSource: A JDBC abstraction for obtaining database connections, commonly used with managed and pooled connections.



> RowSet: A JDBC interface extending ResultSet that provides a more flexible representation of tabular data, including connected and disconnected implementations.



> Transaction: A logical unit of database operations that can be committed or rolled back as a unit.



> Isolation Level: Defines the degree to which concurrent transactions are isolated from one another.



> Batch Processing: Groups multiple SQL operations for execution together, improving efficiency for bulk work.



> Resource Management: Properly releases JDBC resources such as ResultSet, Statement, and Connection, preferably using try-with-resources.
