# 17. JDBC Best Practices in Java / DEEPDIVE

JDBC code can work correctly and still be **bad JDBC code**.

A production-quality JDBC program should answer six questions:

1. **How are resources closed?**
2. **How are SQL parameters supplied safely?**
3. **How are connections obtained and released?**
4. **How are transactions controlled?**
5. **How is SQL injection prevented?**
6. **How is database performance maintained?**

We will examine each individually and then combine them into a complete JDBC design.

---

# 1. try-with-resources

## 1.1 What is try-with-resources?

**Try-with-resources** is a Java language feature that automatically closes resources when execution leaves the `try` block.

The resource must implement `AutoCloseable` (or `Closeable`).

JDBC provides several resources that support this mechanism:

```text
Connection
Statement
PreparedStatement
CallableStatement
ResultSet
```

So instead of manually calling `close()`, we can write:

```java
try (Connection con = dataSource.getConnection()) {

    // database work

}
```

Java automatically performs the required cleanup.

---

# 1.2 Why is resource closing necessary?

When JDBC creates resources, there can be resources on both the Java side and database/driver side.

For example:

```text
Java Application
       ↓
Connection
       ↓
Database session
       ↓
Database resources
```

If a connection isn't released, repeated requests can eventually exhaust available connections.

With a connection pool:

```text
Pool size = 10

Connection 1 → leaked
Connection 2 → leaked
...
Connection 10 → leaked
```

Eventually:

```text
New request
    ↓
getConnection()
    ↓
No available connection
    ↓
Wait / timeout / failure
```

So resource management directly affects **reliability and scalability**.

---

# 1.3 Traditional resource management

Before try-with-resources, code often looked like:

```java
Connection con = null;
PreparedStatement ps = null;
ResultSet rs = null;

try {
    con = dataSource.getConnection();

    ps = con.prepareStatement(
        "SELECT id, name FROM student"
    );

    rs = ps.executeQuery();

    while (rs.next()) {
        System.out.println(
            rs.getInt("id") + " " +
            rs.getString("name")
        );
    }

} finally {
    if (rs != null) {
        rs.close();
    }

    if (ps != null) {
        ps.close();
    }

    if (con != null) {
        con.close();
    }
}
```

It works, but there is a lot of cleanup code.

---

# 1.4 Try-with-resources version

```java
try (
    Connection con = dataSource.getConnection();

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

Much cleaner.

---

# 1.5 How does it work conceptually?

Suppose:

```java
try (Connection con = ...) {
    // work
}
```

Conceptually Java guarantees cleanup similar to:

```java
Connection con = ...;

try {
    // work
}
finally {
    con.close();
}
```

The actual language semantics are more sophisticated, particularly when exceptions occur, but this is the correct mental model.

---

# 1.6 Multiple resources

You can declare multiple resources:

```java
try (
    Connection con = ...;
    PreparedStatement ps = ...;
    ResultSet rs = ...
) {
    // work
}
```

They are closed in **reverse order**.

Therefore:

```text
Created:
Connection
    ↓
PreparedStatement
    ↓
ResultSet

Closed:
ResultSet
    ↓
PreparedStatement
    ↓
Connection
```

This makes sense because the resources form a dependency chain.

---

# 1.7 Nested try-with-resources

You can also separate resource scopes:

```java
try (
    Connection con = dataSource.getConnection();
    PreparedStatement ps =
        con.prepareStatement(
            "SELECT id, name FROM student WHERE id = ?"
        )
) {

    ps.setInt(1, 101);

    try (ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            System.out.println(
                rs.getString("name")
            );
        }
    }
}
```

This can sometimes make the lifetime of a `ResultSet` particularly clear.

---

# 1.8 Suppressed exceptions

An advanced point:

Suppose the SQL operation throws exception A, and then closing a resource throws exception B.

Java preserves the primary exception and can attach the cleanup exception as a **suppressed exception**.

You can inspect them:

```java
catch (SQLException e) {

    for (Throwable t : e.getSuppressed()) {
        t.printStackTrace();
    }
}
```

This is one reason manually implementing complicated cleanup logic is error-prone.

---

# 1.9 Does `close()` happen if an exception occurs?

Yes.

That's one of the primary benefits.

```text
try block
   │
   ├── successful
   │      ↓
   │    close
   │
   └── exception
          ↓
        close
          ↓
      exception handling
```

---

# 1.10 Important pooled-connection point

Suppose:

```java
Connection con =
    dataSource.getConnection();
```

and `dataSource` uses a connection pool.

Calling:

```java
con.close();
```

usually means:

```text
release logical connection
        ↓
return to pool
```

rather than necessarily destroying the physical database connection.

Therefore:

> **Connection pooling does not eliminate the need for `close()`.**

---

# 1.11 Best-practice rule

> **Use try-with-resources for JDBC resources whenever possible.**

---

# 2. PreparedStatement

# 2.1 What is PreparedStatement?

`PreparedStatement` is a JDBC interface used for executing parameterized SQL statements.

Example:

```java
String sql =
    "SELECT id, name FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();
```

The:

```text
?
```

is a parameter placeholder.

---

# 2.2 Why not concatenate values?

Suppose:

```java
int id = 101;
```

You could write:

```java
String sql =
    "SELECT * FROM student WHERE id = " + id;
```

But this becomes dangerous when values originate from external/untrusted input, especially strings.

For example:

```java
String username = userInput;

String sql =
    "SELECT * FROM users WHERE username = '"
    + username
    + "'";
```

The input becomes part of SQL syntax.

That is precisely what we want to avoid.

---

# 2.3 Correct approach

```java
String sql =
    "SELECT * FROM users WHERE username = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, username);
```

The SQL structure and parameter value are kept conceptually separate.

```text
SQL structure
     +
parameter value
     ↓
PreparedStatement
     ↓
Database
```

---

# 2.4 Parameter indexes start at 1

This is a classic JDBC question.

Given:

```java
String sql =
    "SELECT * FROM student WHERE id = ? AND name = ?";
```

Use:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

**Not:**

```java
ps.setInt(0, 101);   // Wrong
```

JDBC parameter indexes are **1-based**.

---

# 2.5 Common setter methods

### Integer

```java
ps.setInt(1, 101);
```

### String

```java
ps.setString(2, "Ravi");
```

### Double

```java
ps.setDouble(3, 45000.50);
```

### Long

```java
ps.setLong(1, 100000L);
```

### Boolean

```java
ps.setBoolean(1, true);
```

There are many other setter methods.

---

# 2.6 `executeQuery()` vs `executeUpdate()`

For a SELECT:

```java
ResultSet rs =
    ps.executeQuery();
```

For INSERT, UPDATE, or DELETE:

```java
int count =
    ps.executeUpdate();
```

Conceptually:

```text
SELECT
  ↓
executeQuery()
  ↓
ResultSet
```

and:

```text
INSERT/UPDATE/DELETE
  ↓
executeUpdate()
  ↓
int update count
```

---

# 2.7 Reusing PreparedStatement

Suppose we want to insert many students:

```java
String sql =
    "INSERT INTO student(id, name) VALUES (?, ?)";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);
ps.setString(2, "Ravi");
ps.executeUpdate();

ps.setInt(1, 102);
ps.setString(2, "John");
ps.executeUpdate();
```

The same statement object can be reused with different parameter values.

For many operations, batching can be even more appropriate.

---

# 2.8 Does PreparedStatement always mean "faster"?

Be careful.

A common statement is:

> "PreparedStatement is always faster."

That is too absolute.

Performance depends on:

* database
* JDBC driver
* statement reuse
* server-side preparation
* configuration
* query complexity
* workload

The strongest general reason to use PreparedStatement is **safe parameterization and clean SQL**, with performance benefits possible in repeated execution scenarios.

---

# 2.9 PreparedStatement and SQL injection

The correct relationship is:

```text
PreparedStatement
       ↓
Parameterized SQL
       ↓
Values aren't concatenated into SQL syntax
       ↓
Strong protection against SQL injection
```

But PreparedStatement does not make unrelated application logic automatically secure.

---

# 2.10 Important limitation: SQL identifiers

This does **not** work as ordinary parameterization:

```java
String sql =
    "SELECT * FROM ?";
```

A parameter represents a value, not normally a table/column identifier.

If dynamic table/column selection is genuinely required, use a controlled allowlist.

Example:

```java
Map<String, String> allowedTables = Map.of(
    "students", "student",
    "employees", "employee"
);

String table = allowedTables.get(userChoice);

if (table == null) {
    throw new IllegalArgumentException(
        "Invalid table"
    );
}
```

Then construct SQL only from trusted/allowlisted identifiers.

---

# 2.11 Best-practice rule

> **Use PreparedStatement for SQL containing external or variable values.**

---

# 3. Connection Management

## 3.1 What is a Connection?

A JDBC `Connection` represents a database connection/session through which SQL operations are performed.

```text
Java
 ↓
Connection
 ↓
JDBC Driver
 ↓
Database
```

---

# 3.2 Don't create connections unnecessarily

Bad architecture:

```text
Method A → new database connection
Method B → new database connection
Method C → new database connection
```

Repeated connection creation can be expensive.

---

# 3.3 Use DataSource

In many real applications:

```java
Connection con =
    dataSource.getConnection();
```

instead of directly using:

```java
DriverManager.getConnection(...);
```

A DataSource can be configured to use a connection pool.

---

# 3.4 Connection pooling

Typical production architecture:

```text
Application
     ↓
DataSource
     ↓
Connection Pool
     ↓
Physical Connections
     ↓
Database
```

Example:

```text
Pool
┌────┬────┬────┬────┐
│ C1 │ C2 │ C3 │ C4 │
└────┴────┴────┴────┘
```

Application borrows:

```text
borrow → use → close/release → pool
```

---

# 3.5 Don't hold connections too long

Consider:

```java
Connection con =
    dataSource.getConnection();

// 20 seconds of unrelated processing

// finally execute SQL
```

This is poor resource utilization.

Better:

```text
Perform non-DB work
       ↓
Acquire connection
       ↓
Perform DB work
       ↓
Release connection
```

---

# 3.6 Connection scope

The correct scope depends on the application architecture.

In general, avoid:

```text
one Connection for the entire application
```

Connections should generally have a controlled lifecycle around database work.

For pooled applications, connections are typically borrowed for a unit of work and returned promptly.

---

# 3.7 Don't share a Connection indiscriminately between concurrent requests

A connection has mutable state, including things such as:

* transaction state
* auto-commit
* isolation level
* warnings
* session configuration

Sharing one connection carelessly among unrelated concurrent operations can produce incorrect behavior.

A pool typically gives independent logical connections to concurrent requests.

---

# 3.8 Connection state

A connection can have state:

```java
con.setAutoCommit(false);
```

or:

```java
con.setTransactionIsolation(
    Connection.TRANSACTION_READ_COMMITTED
);
```

Therefore, when using pools, connection state should be correctly managed/reset by the pooling infrastructure and application.

---

# 3.9 Always release connections

Best:

```java
try (Connection con =
         dataSource.getConnection()) {

    // DB work
}
```

---

# 3.10 Best-practice rules

> **Acquire late.**

> **Use quickly.**

> **Release promptly.**

> **Use pooling where appropriate.**

> **Don't leak or unnecessarily share connections.**

---

# 4. Transaction Management

## 4.1 What is a transaction?

A transaction is a logical unit of database work.

Consider a bank transfer:

```text
Account A
   ↓
-₹1,000

Account B
   ↓
+₹1,000
```

These operations belong together.

---

# 4.2 The problem without transaction control

Suppose:

```text
Debit A → SUCCESS
Credit B → FAILURE
```

Now the database may be left in an incorrect intermediate state.

Transactions provide a mechanism to commit or roll back a unit of work.

---

# 4.3 Auto-commit

JDBC connections normally begin with auto-commit enabled.

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

To explicitly control a multi-operation transaction:

```java
con.setAutoCommit(false);
```

---

# 4.4 Complete transaction example

```java
try (Connection con =
         dataSource.getConnection()) {

    con.setAutoCommit(false);

    try {
        debit(con, 101, 1000);
        credit(con, 102, 1000);

        con.commit();

    } catch (SQLException e) {

        try {
            con.rollback();
        } catch (SQLException rollbackError) {
            e.addSuppressed(rollbackError);
        }

        throw e;
    }

} catch (SQLException e) {
    e.printStackTrace();
}
```

Conceptually:

```text
Get Connection
      ↓
Disable Auto-commit
      ↓
Operation 1
      ↓
Operation 2
      ↓
      ├── Success → COMMIT
      │
      └── Failure → ROLLBACK
```

---

# 4.5 Why catch rollback failure?

Because `rollback()` itself can fail.

For example:

```java
try {
    con.rollback();
} catch (SQLException rollbackError) {
    e.addSuppressed(rollbackError);
}
```

This preserves the original failure while recording the rollback failure.

---

# 4.6 Transaction boundaries

A transaction should have a clear beginning and end.

Good:

```text
BEGIN
 ↓
SQL 1
 ↓
SQL 2
 ↓
COMMIT
```

Bad:

```text
BEGIN
 ↓
SQL
 ↓
unrelated application work
 ↓
network call
 ↓
user interaction
 ↓
COMMIT
```

Long-running transactions can hold database resources and increase contention.

---

# 4.7 Savepoints

A savepoint provides a checkpoint inside a transaction.

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
Operation A
Operation B
     ↓
 SAVEPOINT
     ↓
Operation C
Operation D
     ↓
Problem
     ↓
ROLLBACK TO SAVEPOINT
```

The entire transaction does not necessarily need to be discarded.

---

# 4.8 Transaction + batch

They are independent concepts and can be combined:

```java
con.setAutoCommit(false);

ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();

con.commit();
```

Here:

```text
Batch
→ groups execution

Transaction
→ controls commit/rollback
```

---

# 4.9 Connection pool and transactions

This is an important production issue.

Suppose:

```java
Connection con =
    dataSource.getConnection();

con.setAutoCommit(false);

// transaction work
```

Before releasing the connection, the transaction should be completed appropriately:

```text
commit OR rollback
       ↓
close/release
```

Leaving unfinished transactional state is dangerous.

---

# 4.10 Best-practice rules

> **Use explicit transaction boundaries for related operations.**

> **Commit only when the logical unit of work succeeds.**

> **Rollback when it fails.**

> **Keep transactions as short as reasonably possible.**

> **Complete transaction handling before releasing a pooled connection.**

---

# 5. SQL Injection Prevention

# 5.1 What is SQL injection?

SQL injection occurs when untrusted input is allowed to alter SQL syntax.

Consider:

```java
String sql =
    "SELECT * FROM users WHERE username = '"
    + username
    + "'";
```

The user's input is being inserted directly into SQL text.

This creates the possibility that input can change the meaning of the SQL statement.

---

# 5.2 The fundamental rule

> **Data should be treated as data, not as SQL syntax.**

The primary JDBC mechanism for achieving this is:

```text
PreparedStatement
```

---

# 5.3 Safe example

```java
String sql =
    "SELECT * FROM users WHERE username = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, username);

ResultSet rs =
    ps.executeQuery();
```

Here the username is supplied as a parameter.

---

# 5.4 Why does this help?

Conceptually:

```text
SQL template
"SELECT ... WHERE username = ?"

          +

Value
"Ravi"

          ↓

PreparedStatement
```

The parameter isn't simply pasted into the SQL command as raw SQL syntax.

---

# 5.5 Dangerous vs safe

### ❌ Dangerous

```java
String sql =
    "SELECT * FROM employee WHERE name = '"
    + name
    + "'";
```

### ✅ Preferred

```java
String sql =
    "SELECT * FROM employee WHERE name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, name);
```

---

# 5.6 Why escaping manually isn't the preferred solution

You might see code attempting to manually escape quotes or special characters.

That is fragile because SQL syntax and escaping rules can vary by database and context.

Instead:

> Use parameter binding through PreparedStatement.

---

# 5.7 What about numbers?

Even though numeric input may look harmless:

```java
String sql =
    "SELECT * FROM student WHERE id = " + id;
```

prefer:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

ps.setInt(1, id);
```

This creates consistent parameterized SQL.

---

# 5.8 What about LIKE?

This is a common question.

You can parameterize the value:

```java
String sql =
    "SELECT * FROM student WHERE name LIKE ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, "%" + searchTerm + "%");
```

The pattern becomes the parameter value.

---

# 5.9 What about ORDER BY?

This is where parameterization has a limitation.

You generally cannot do:

```java
ORDER BY ?
```

to dynamically choose a column identifier.

Instead, use an allowlist:

```java
String sortColumn;

switch (userChoice) {
    case "name":
        sortColumn = "name";
        break;

    case "id":
        sortColumn = "id";
        break;

    default:
        throw new IllegalArgumentException(
            "Invalid sort column"
        );
}
```

Then use only the validated identifier in the SQL.

---

# 5.10 Defense in depth

PreparedStatement is the primary defense, but secure applications should also use:

* input validation
* least-privilege database accounts
* restricted database permissions
* secure error handling
* careful dynamic SQL construction

---

# 5.11 Best-practice rule

> **Never construct SQL by concatenating untrusted values into SQL syntax.**

---

# 6. Performance

Performance is not one single JDBC method.

It is the result of several decisions.

```text
JDBC Performance
       │
       ├── Connection management
       ├── SQL quality
       ├── PreparedStatement
       ├── Batching
       ├── Result processing
       ├── Fetching strategy
       ├── Transactions
       └── Resource management
```

---

# 6.1 Connection pooling

Repeatedly creating connections can be expensive.

Use an appropriate pool through a DataSource:

```text
Application
    ↓
DataSource
    ↓
Pool
    ↓
Reusable connections
```

This can significantly reduce connection-establishment overhead in high-throughput applications.

---

# 6.2 PreparedStatement reuse

Suppose you execute:

```text
INSERT 10,000 times
```

with the same SQL structure.

Use:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name) VALUES (?, ?)"
    );
```

Then change parameters:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

etc.

This avoids repeatedly constructing entirely new SQL strings.

---

# 6.3 Batch processing

For bulk operations:

```java
ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();
```

This can reduce execution/communication overhead.

---

# 6.4 Batch size matters

Don't assume:

```text
1,000,000 rows
→ one giant batch
```

is optimal.

Large batches can increase:

* memory usage
* transaction size
* rollback cost
* database pressure
* lock duration

A common strategy is chunking:

```text
Rows 1–1000
   ↓
batch

Rows 1001–2000
   ↓
batch

Rows 2001–3000
   ↓
batch
```

The appropriate size depends on the database, driver, workload, and environment.

---

# 6.5 Don't use SELECT *

If you only need:

```text
id
name
```

prefer:

```sql
SELECT id, name
FROM student
```

rather than:

```sql
SELECT *
FROM student
```

This can reduce:

* data transferred
* memory usage
* result processing
* unnecessary dependency on unrelated columns

---

# 6.6 Filter at the database

Bad:

```text
Database
   ↓
1,000,000 rows
   ↓
Java
   ↓
Filter 10 rows
```

Better:

```sql
SELECT id, name
FROM student
WHERE department = ?
```

Let the database perform appropriate filtering.

---

# 6.7 Use indexes appropriately

Suppose:

```sql
SELECT *
FROM employee
WHERE employee_id = ?
```

An appropriate index may make this query much faster.

But indexes aren't free.

They can:

* consume storage
* increase write cost
* increase maintenance overhead

Therefore indexes should be designed based on actual workload and query patterns.

---

# 6.8 Avoid N+1 query patterns

Suppose:

```text
SELECT all students
```

returns 1,000 students.

Then Java executes:

```text
1 query for students
+
1 query per student
=
1001 queries
```

This is commonly called the **N+1 query problem**.

Depending on the use case, it may be better to retrieve related data using appropriate joins, batching, or other query strategies.

---

# 6.9 Don't hold ResultSets unnecessarily

If you no longer need the result:

```text
Process
 ↓
Close
```

Don't keep database resources alive while performing unrelated work.

---

# 6.10 Fetch size

JDBC provides:

```java
ps.setFetchSize(...);
```

Fetch size can provide a hint to the driver about how many rows to fetch/manage at a time.

Example:

```java
ps.setFetchSize(500);
```

But:

> Fetch-size behavior is driver/database dependent.

It should not be treated as a universal guarantee that exactly 500 rows are transferred in one network request.

---

# 6.11 Pagination

Instead of retrieving millions of records at once, applications often retrieve smaller pages.

Conceptually:

```text
Page 1 → rows 1–100
Page 2 → rows 101–200
Page 3 → rows 201–300
```

The exact pagination technique should be chosen according to the database and query workload.

---

# 6.12 Don't optimize blindly

This is a major professional principle.

Before optimizing:

```text
Measure
 ↓
Identify bottleneck
 ↓
Change
 ↓
Measure again
```

Useful things to investigate include:

* SQL execution plan
* database indexes
* query latency
* connection acquisition time
* pool utilization
* batch size
* result-set size
* network latency

---

# 6.13 Performance isn't just Java code

Suppose Java code is:

```java
ps.executeQuery();
```

but the SQL takes 5 seconds.

Changing a Java loop from:

```java
while (...) {
}
```

to something slightly different may accomplish almost nothing.

The actual bottleneck could be:

```text
SQL
 ↓
Missing index
 ↓
Full table scan
 ↓
5 seconds
```

Therefore:

> **JDBC performance requires looking at the complete Java → driver → network → database path.**

---

# 7. COMPLETE PRODUCTION-STYLE EXAMPLE

Here's a compact example combining the major practices:

```java
String sql = """
    SELECT id, name
    FROM student
    WHERE department = ?
    ORDER BY id
    """;

try (
    Connection con = dataSource.getConnection();

    PreparedStatement ps =
        con.prepareStatement(sql)
) {

    ps.setString(1, department);
    ps.setFetchSize(100);

    try (ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            int id =
                rs.getInt("id");

            String name =
                rs.getString("name");

            System.out.println(
                id + " " + name
            );
        }
    }

} catch (SQLException e) {

    // Log appropriately in a real application.
    e.printStackTrace();
}
```

This demonstrates:

```text
DataSource
    ↓
Connection management
    ↓
try-with-resources
    ↓
PreparedStatement
    ↓
Parameterized SQL
    ↓
SQL injection prevention
    ↓
Fetch configuration
    ↓
ResultSet
    ↓
Automatic cleanup
```

---

# 8. Transaction Example with Best Practices

For a transaction:

```java
try (Connection con =
         dataSource.getConnection()) {

    con.setAutoCommit(false);

    try {
        String debitSql =
            "UPDATE account " +
            "SET balance = balance - ? " +
            "WHERE id = ?";

        String creditSql =
            "UPDATE account " +
            "SET balance = balance + ? " +
            "WHERE id = ?";

        try (
            PreparedStatement debit =
                con.prepareStatement(debitSql);

            PreparedStatement credit =
                con.prepareStatement(creditSql)
        ) {

            debit.setDouble(1, 1000);
            debit.setInt(2, 101);
            debit.executeUpdate();

            credit.setDouble(1, 1000);
            credit.setInt(2, 102);
            credit.executeUpdate();
        }

        con.commit();

    } catch (SQLException e) {

        try {
            con.rollback();
        } catch (SQLException rollbackError) {
            e.addSuppressed(rollbackError);
        }

        throw e;
    }

} catch (SQLException e) {
    e.printStackTrace();
}
```

Notice how multiple best practices work together:

```text
DataSource
    ↓
Connection pooling
    ↓
try-with-resources
    ↓
Auto-commit disabled
    ↓
PreparedStatement
    ↓
Parameterized values
    ↓
Multiple operations
    ↓
commit()
    ↓
rollback() on failure
    ↓
Connection released
```

---

# 9. Bad JDBC vs Good JDBC

| Area                | ❌ Poor Practice                  | ✅ Best Practice                           |
| ------------------- | -------------------------------- | ----------------------------------------- |
| Resources           | Manually scattered cleanup       | try-with-resources                        |
| SQL values          | String concatenation             | PreparedStatement                         |
| Connections         | Create repeatedly                | DataSource/pooling where appropriate      |
| Connection lifetime | Hold unnecessarily               | Acquire late, release promptly            |
| Transactions        | Rely blindly on auto-commit      | Explicit boundaries when needed           |
| Failure             | Ignore rollback                  | Rollback on transaction failure           |
| User input          | Put directly into SQL            | Parameter binding                         |
| Bulk operations     | Execute one-by-one unnecessarily | Batch processing                          |
| Queries             | `SELECT *` unnecessarily         | Select required columns                   |
| Large results       | Load everything                  | Filtering/pagination/appropriate fetching |
| Database design     | Ignore indexes/query plans       | Optimize based on workload                |
| Optimization        | Guess                            | Measure and profile                       |

---

# 10. The Six Best-Practice Principles

## Principle 1 — Resource Safety

```text
JDBC resource
     ↓
try-with-resources
     ↓
automatic cleanup
```

---

## Principle 2 — Parameter Safety

```text
Variable value
     ↓
?
     ↓
PreparedStatement
```

Never:

```text
untrusted input
      ↓
SQL string concatenation
```

---

## Principle 3 — Connection Safety

```text
DataSource
   ↓
Pool
   ↓
Borrow
   ↓
Use
   ↓
Release
```

---

## Principle 4 — Transaction Safety

```text
Related operations
       ↓
Transaction
       ↓
Success → commit
Failure → rollback
```

---

## Principle 5 — Security

```text
Untrusted input
      ↓
Validate where appropriate
      ↓
Parameterize
      ↓
Least-privilege DB account
```

---

## Principle 6 — Performance

```text
Connection Pool
      +
PreparedStatement
      +
Batch Processing
      +
Efficient SQL
      +
Appropriate Indexes
      +
Reasonable Result Sets
      ↓
Better scalability
```

---

# 11. DEEPDIVE DOUBT CLEARING

### ❓ Does try-with-resources prevent SQL exceptions?

**No.**

It manages resource cleanup. SQL operations can still throw `SQLException`.

---

### ❓ Does PreparedStatement automatically commit?

**No.**

Transaction behavior is controlled by the connection's transaction settings.

---

### ❓ Does PreparedStatement automatically prevent every type of SQL injection?

It strongly protects parameter values when used correctly, but dynamic SQL identifiers and other application-level issues still require careful handling.

---

### ❓ Does DataSource automatically mean connection pooling?

**No.**

A DataSource can be backed by a pool, but the interface itself doesn't guarantee pooling.

---

### ❓ Does connection pooling mean I shouldn't close Connection?

**No.**

Always release it.

---

### ❓ Does `commit()` execute SQL?

**No.**

SQL execution and transaction commit are separate operations.

---

### ❓ Does `rollback()` undo previously committed transactions?

Normally, **no**.

Rollback applies to the current transaction's uncommitted work.

---

### ❓ Is batch processing the same as a transaction?

**No.**

```text
Batch       → execution grouping
Transaction → commit/rollback grouping
```

---

### ❓ Is PreparedStatement always faster than Statement?

Don't make an absolute performance claim. PreparedStatement is preferred primarily for parameterization and safety; repeated execution may also provide performance advantages depending on driver/database behavior.

---

### ❓ Does `setFetchSize(500)` guarantee exactly 500 rows per network request?

**No.**

It's generally a driver/database hint, and behavior varies.

---

### ❓ Is `SELECT *` always slow?

Not necessarily.

The problem is unnecessary data retrieval and coupling. If all columns are genuinely needed, it can be reasonable. Otherwise, select only what you need.

---

# 12. FINAL DEEPDIVE MAP

```text
                    JDBC BEST PRACTICES
                            │
       ┌────────────────────┼────────────────────┐
       ↓                    ↓                    ↓
 RESOURCE SAFETY        SQL SAFETY         CONNECTION SAFETY
       │                    │                    │
try-with-resources    PreparedStatement     DataSource
       │                    │                    │
auto cleanup          parameters (?)        pooling
       │                    │                    │
close resources       no concatenation      acquire/release
       │                    │                    │
       └────────────────────┼────────────────────┘
                            ↓
                    TRANSACTION SAFETY
                            │
                    setAutoCommit(false)
                            │
                  ┌─────────┴─────────┐
                  ↓                   ↓
               commit              rollback
                            │
                            ↓
                     PERFORMANCE
                            │
       ┌────────────────────┼────────────────────┐
       ↓                    ↓                    ↓
   Connection            Batch              Efficient SQL
     Pooling            Processing           + Indexes
       │                    │                    │
       └────────────────────┼────────────────────┘
                            ↓
                     SCALABLE JDBC
```

## Ultimate formula

> **JDBC Best Practice = Close resources + Parameterize SQL + Reuse connections + Control transactions + Prevent injection + Optimize based on measurement.**

That combination turns JDBC from merely **working code** into **production-quality JDBC code**.
