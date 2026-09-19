# 17. JDBC Best Practices in Java — DOUBTKILLER

This section is designed to eliminate the **common doubts, traps, interview confusions, and “why?” questions** around JDBC best practices.

We will handle each concept **individually and completely**.

---

# 1. try-with-resources

## 1.1 What exactly is try-with-resources?

It is a Java language feature that automatically closes resources after the `try` block finishes.

Example:

```java
try (Connection con =
         DriverManager.getConnection(url, user, password)) {

    System.out.println("Connected");

}
```

You don't explicitly write:

```java
con.close();
```

Java handles the closing.

---

## 1.2 Why was try-with-resources introduced?

Before try-with-resources, programmers commonly wrote:

```java
Connection con = null;

try {
    con = DriverManager.getConnection(
        url, user, password
    );

    // database work

} finally {
    if (con != null) {
        con.close();
    }
}
```

This works, but becomes cumbersome when multiple resources are involved.

```text
Connection
PreparedStatement
ResultSet
```

Try-with-resources makes this cleaner.

---

## 1.3 Can I use multiple JDBC resources?

Yes.

```java
try (
    Connection con =
        DriverManager.getConnection(
            url, user, password
        );

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

---

## 1.4 Which resource closes first?

Resources are closed in **reverse order**.

Creation:

```text
Connection
    ↓
PreparedStatement
    ↓
ResultSet
```

Closing:

```text
ResultSet
    ↓
PreparedStatement
    ↓
Connection
```

### Doubt

**Why reverse order?**

Because later-created resources often depend on earlier-created resources.

---

## 1.5 Does try-with-resources work only with JDBC?

No.

It works with objects implementing `AutoCloseable`.

JDBC resources support this mechanism.

Examples include:

```text
Connection
Statement
PreparedStatement
CallableStatement
ResultSet
```

---

## 1.6 What if an exception occurs?

This is one of its biggest advantages.

```java
try (Connection con =
         DriverManager.getConnection(
             url, user, password)) {

    // exception occurs here

}
```

Even if an exception occurs, Java still attempts to close the resource.

Conceptually:

```text
Open resource
     ↓
Use resource
     ↓
Exception
     ↓
Close resource
     ↓
Exception handling
```

---

## 1.7 Does `close()` mean a pooled connection is destroyed?

**Not necessarily.**

With a connection pool:

```java
Connection con =
    dataSource.getConnection();
```

then:

```java
con.close();
```

normally means:

```text
Application
    ↓
close()
    ↓
logical connection returned to pool
```

The physical database connection may remain in the pool.

### Golden rule

> **Always close a connection, even when using connection pooling.**

---

# 2. PreparedStatement

## 2.1 What is PreparedStatement?

`PreparedStatement` is a JDBC interface used for executing parameterized SQL.

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

---

## 2.2 What does `?` mean?

It represents a **parameter placeholder**.

```sql
SELECT * FROM student WHERE id = ?
```

The `?` isn't the value itself.

The value is supplied separately:

```java
ps.setInt(1, 101);
```

---

## 2.3 Why does parameter numbering start at 1?

JDBC parameter indexes are **1-based**.

Correct:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

Not:

```java
ps.setInt(0, 101);  // Wrong
```

---

## 2.4 What happens with multiple parameters?

SQL:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ?";
```

Values:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

Mapping:

```text
? #1 → 101
? #2 → Ravi
```

---

## 2.5 What if I set parameters in the wrong order?

Suppose:

```sql
WHERE id = ? AND name = ?
```

but:

```java
ps.setString(1, "Ravi");
ps.setInt(2, 101);
```

The types/order don't match the SQL parameters.

This can produce an error or incorrect behavior.

### Rule

> Parameter position must correspond to the `?` position.

---

## 2.6 What happens if I don't set a parameter?

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.executeQuery();
```

You haven't supplied parameter 1.

This normally results in a `SQLException`.

---

## 2.7 What if I set a parameter but there is no `?`?

Example:

```java
String sql =
    "SELECT * FROM student";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);
```

There is no parameter 1.

This is invalid and can result in an exception.

---

## 2.8 `executeQuery()` vs `executeUpdate()`

For a query returning rows:

```java
ResultSet rs =
    ps.executeQuery();
```

Typically:

```text
SELECT → executeQuery()
```

For:

```text
INSERT
UPDATE
DELETE
```

typically:

```java
int count =
    ps.executeUpdate();
```

---

## 2.9 Does PreparedStatement automatically prevent every SQL injection problem?

It protects parameter values when used correctly.

Correct:

```java
String sql =
    "SELECT * FROM user WHERE name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, input);
```

But don't do this:

```java
String sql =
    "SELECT * FROM user WHERE name = '"
    + input
    + "'";
```

and then claim:

> "I'm using JDBC, so SQL injection is impossible."

The problem is **how SQL is constructed**, not merely whether JDBC is being used.

---

## 2.10 Can `?` replace a table name?

Generally no.

This:

```sql
SELECT * FROM ?
```

is not how PreparedStatement parameters are intended to work.

Parameters represent **values**, not arbitrary SQL syntax such as:

```text
table names
column names
SQL keywords
ORDER BY identifiers
```

For dynamic identifiers, use trusted allowlists.

---

# 3. Connection Management

## 3.1 What is the main connection-management rule?

Think:

> **Acquire → Use → Release.**

```text
Get Connection
      ↓
Perform DB work
      ↓
Close Connection
```

---

## 3.2 Why shouldn't I keep a Connection forever?

A connection is a limited database resource.

Suppose your application has:

```text
10 available connections
```

If 10 requests keep connections unnecessarily:

```text
C1 → occupied
C2 → occupied
...
C10 → occupied
```

Another request may have to wait.

This can lead to:

```text
slow requests
timeouts
connection pool exhaustion
```

---

## 3.3 Should I create a new connection for every SQL statement?

Not necessarily.

With a connection pool, the usual pattern is:

```text
Borrow connection
      ↓
Perform related DB operations
      ↓
Close/release
```

You don't want:

```text
SQL 1 → borrow → close
SQL 2 → borrow → close
SQL 3 → borrow → close
```

when those operations logically belong together, particularly if they are part of one transaction.

---

## 3.4 What is connection pooling?

A pool maintains reusable connections.

```text
              CONNECTION POOL

       ┌──────┬──────┬──────┬──────┐
       │  C1  │  C2  │  C3  │  C4  │
       └──────┴──────┴──────┴──────┘
             ↑
          Application
```

Application requests:

```text
borrow
  ↓
use
  ↓
close
  ↓
return to pool
```

---

## 3.5 Why is pooling useful?

Creating a physical database connection can involve:

```text
network communication
authentication
database session setup
driver work
```

Doing that repeatedly can be expensive.

Pooling allows connections to be reused.

---

## 3.6 Should I manually create my own connection pool?

In real applications, usually use a mature connection-pooling implementation rather than writing one yourself.

The application commonly obtains connections through a `DataSource`.

```java
Connection con =
    dataSource.getConnection();
```

---

## 3.7 Is `DataSource` the same as `Connection`?

No.

Think:

```text
DataSource
    ↓
provides Connection
    ↓
Connection
    ↓
communicates with database
```

`DataSource` is a source/factory for database connections and may integrate with pooling and configuration.

---

## 3.8 Can multiple threads safely share one Connection?

You should not treat a single JDBC `Connection` as a general-purpose thread-safe shared object.

A connection has mutable state:

```text
transaction
auto-commit
isolation level
read-only state
```

Sharing it indiscriminately can cause operations to interfere with one another.

A typical application architecture obtains an appropriate connection for the unit of work/request/transaction.

---

# 4. Transaction Management

## 4.1 What exactly is a transaction?

A transaction is a logical unit of database work.

Example:

```text
Transfer ₹1,000

A → -1000
B → +1000
```

We want:

```text
Both succeed
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

## 4.2 Why is auto-commit important?

By default, JDBC connections generally begin with auto-commit enabled.

That means each successful statement is normally committed automatically.

Conceptually:

```text
Statement 1 → commit
Statement 2 → commit
Statement 3 → commit
```

---

## 4.3 Why disable auto-commit?

Suppose:

```text
Debit A
Credit B
```

If auto-commit is enabled:

```text
Debit A → committed
Credit B → fails
```

Now the transaction isn't atomic.

Instead:

```java
con.setAutoCommit(false);
```

Then:

```text
Debit A
   ↓
Credit B
   ↓
commit
```

---

## 4.4 What does `commit()` do?

```java
con.commit();
```

It commits the current transaction's changes.

Conceptually:

```text
Temporary transactional changes
             ↓
          COMMIT
             ↓
       permanent changes
```

---

## 4.5 What does `rollback()` do?

```java
con.rollback();
```

It rolls back uncommitted changes in the current transaction.

Conceptually:

```text
Changes
  ↓
ROLLBACK
  ↓
discard uncommitted transaction changes
```

---

## 4.6 Complete transaction pattern

```java
try (Connection con =
         dataSource.getConnection()) {

    con.setAutoCommit(false);

    try {
        debit(con);
        credit(con);

        con.commit();

    } catch (SQLException e) {

        con.rollback();

        throw e;
    }
}
```

The critical pattern is:

```text
setAutoCommit(false)
        ↓
operations
        ↓
commit
```

and on failure:

```text
rollback
```

---

## 4.7 What if `commit()` itself fails?

This is an important advanced doubt.

`commit()` can itself throw `SQLException`.

So transaction code must treat commit as a database operation that can fail.

Don't assume:

```java
con.commit();  // must always succeed
```

Always handle `SQLException` appropriately.

---

## 4.8 What if rollback fails?

Even rollback can fail.

For robust error handling:

```java
catch (SQLException e) {

    try {
        con.rollback();
    } catch (SQLException rollbackError) {
        e.addSuppressed(rollbackError);
    }

    throw e;
}
```

This preserves the original failure while retaining rollback failure information.

---

## 4.9 What is a Savepoint?

A savepoint is a marker inside a transaction.

```java
Savepoint sp =
    con.setSavepoint();
```

Then:

```java
con.rollback(sp);
```

can roll the transaction back to that point.

Conceptually:

```text
Operation A
    ↓
Operation B
    ↓
SAVEPOINT
    ↓
Operation C
    ↓
Operation D
    ↓
Failure
    ↓
rollback(savepoint)
```

---

## 4.10 Is a transaction the same as a batch?

**No.**

This is a major JDBC interview question.

### Batch:

```text
addBatch()
executeBatch()
```

is about grouping/submitting multiple SQL commands.

### Transaction:

```text
commit()
rollback()
```

is about controlling transactional atomicity.

You can use both:

```java
con.setAutoCommit(false);

ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();

con.commit();
```

So:

> **Batch = execution grouping. Transaction = transactional boundary.**

---

# 5. SQL Injection Prevention

## 5.1 What is the central rule?

> **Never turn untrusted input into SQL syntax.**

Bad:

```java
String sql =
    "SELECT * FROM users WHERE name = '"
    + username
    + "'";
```

The user input becomes part of the SQL string.

---

## 5.2 Correct approach

```java
String sql =
    "SELECT * FROM users WHERE name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, username);
```

Now:

```text
SQL structure → SQL
User input    → parameter value
```

---

## 5.3 Why does PreparedStatement help?

Because the SQL structure is established separately from parameter values.

Instead of:

```text
String concatenation
       ↓
SQL command
```

we have:

```text
SQL template
      +
parameter values
      ↓
database execution
```

The parameter is not simply inserted into the SQL text as executable syntax.

---

## 5.4 Is escaping strings manually a good replacement?

Generally, no.

Don't build your primary defense around manually escaping user input.

Prefer:

```java
PreparedStatement
```

with parameters.

---

## 5.5 What about numeric input?

Some developers think:

> "It's an integer, so concatenating it is always safe."

Don't build security around assumptions about input sources or manual conversions.

Prefer:

```java
ps.setInt(1, id);
```

It is cleaner and maintains the parameterized design.

---

## 5.6 What about `ORDER BY`?

Suppose:

```text
User chooses:
name
salary
department
```

You generally cannot do:

```sql
ORDER BY ?
```

to dynamically substitute an arbitrary identifier.

Instead use an allowlist:

```java
String column;

switch (sortOption) {

    case "name":
        column = "name";
        break;

    case "salary":
        column = "salary";
        break;

    default:
        throw new IllegalArgumentException();
}
```

Then use only the trusted predefined identifier.

---

## 5.7 What about table names?

Same principle.

Don't allow arbitrary user input to become:

```sql
FROM <user input>
```

Use an allowlist of known table names.

---

## 5.8 Is PreparedStatement enough for all application security?

No.

It is a major SQL-injection defense, but complete security also involves:

```text
PreparedStatement
+
Authorization
+
Authentication
+
Input validation
+
Least-privilege database accounts
+
Secure configuration
```

PreparedStatement doesn't decide whether a user **should be allowed** to access a particular record.

That's an authorization issue.

---

# 6. Performance

## 6.1 Is JDBC itself slow?

Not necessarily.

JDBC is an API layer.

Performance depends on many components:

```text
Java application
      ↓
JDBC API
      ↓
JDBC Driver
      ↓
Network
      ↓
Database
      ↓
SQL execution
      ↓
Indexes / locks / storage
```

A slow query may have nothing to do with Java itself.

---

## 6.2 What is usually the biggest mistake?

Doing too much unnecessary database work.

For example:

```sql
SELECT *
FROM student;
```

when you need only:

```text
id
name
```

Prefer:

```sql
SELECT id, name
FROM student;
```

---

## 6.3 Is connection pooling always faster?

Pooling can reduce the cost of repeatedly establishing physical connections.

But pooling doesn't magically make every database operation faster.

Poorly configured pools can even cause problems.

For example:

```text
Too small
   ↓
requests wait for connections

Too large
   ↓
too much database concurrency
   ↓
database becomes overloaded
```

The pool must be sized appropriately for the application and database.

---

## 6.4 Is PreparedStatement always faster than Statement?

Don't memorize that.

The stronger reasons to prefer PreparedStatement are:

```text
parameterization
security
readability
maintainability
possible reuse
```

Actual performance depends on:

```text
JDBC driver
database
query
execution strategy
configuration
```

---

## 6.5 Is batch processing always faster?

Usually batching can reduce per-operation overhead, especially for many similar operations.

But don't assume:

```text
batch = automatically fastest
```

Performance depends on:

```text
database
driver
batch size
transaction design
network
statement complexity
generated keys
error handling
```

Very large batches may also create memory or transaction-size problems.

---

## 6.6 Why shouldn't I execute 10,000 individual INSERTs?

Because you may incur repeated overhead:

```text
Java
 ↓
Driver
 ↓
Network
 ↓
Database
```

repeated many times.

Batching can instead group operations:

```text
Java
 ↓
Driver
 ↓
Batch
 ↓
Database
```

Example:

```java
for (Student s : students) {

    ps.setInt(1, s.getId());
    ps.setString(2, s.getName());

    ps.addBatch();
}

ps.executeBatch();
```

---

## 6.7 What is the N+1 problem?

Suppose:

```text
1 query → retrieve 100 departments
```

Then:

```text
100 additional queries
```

to retrieve information for each department.

Total:

```text
101 queries
```

This can be much more expensive than designing a query that retrieves the required data more efficiently.

---

## 6.8 Should I always use JOINs to solve N+1?

Not blindly.

A join may be appropriate, but alternatives can include:

```text
JOIN
batch query
IN (...)
fetching strategy
caching
different data model/query
```

The correct choice depends on the use case and data volume.

---

## 6.9 What is `setFetchSize()`?

Example:

```java
ps.setFetchSize(500);
```

It provides the driver with a hint about the desired fetch size.

But:

> **It does not have identical behavior across every JDBC driver/database.**

Don't interpret it as a guaranteed network packet size or universal memory limit.

---

## 6.10 Does `SELECT *` always make the query slow?

No.

But retrieving unnecessary columns can increase:

```text
database I/O
network transfer
driver processing
Java memory usage
ResultSet processing
```

So selecting only required columns is generally a good practice.

---

## 6.11 Do indexes always improve performance?

No.

An index can improve suitable reads:

```text
SELECT ... WHERE id = ?
```

But indexes also require:

```text
storage
maintenance
write overhead
```

Too many indexes can hurt INSERT/UPDATE/DELETE performance.

---

## 6.12 How do I actually find a performance problem?

Don't guess.

Use:

```text
Measure
   ↓
Identify bottleneck
   ↓
Optimize
   ↓
Measure again
```

Look at:

```text
SQL execution time
connection acquisition time
database query plans
network latency
lock waits
result-set size
application processing
pool utilization
```

The database query plan is particularly important for understanding why a SQL query is slow.

---

# 🚨 DOUBTKILLER — Most Important Confusions

## Doubt 1: `close()` vs connection pool

**Question:** If I use a connection pool, should I call `close()`?

**YES.**

```java
try (Connection con =
         dataSource.getConnection()) {
}
```

Usually `close()` returns the logical connection to the pool.

---

## Doubt 2: `Statement` vs `PreparedStatement`

```text
Statement
    ↓
SQL often constructed as text

PreparedStatement
    ↓
SQL + parameters
```

For user/application values, prefer:

```text
PreparedStatement
```

---

## Doubt 3: `executeQuery()` vs `executeUpdate()`

```text
SELECT
   ↓
executeQuery()
   ↓
ResultSet
```

```text
INSERT / UPDATE / DELETE
   ↓
executeUpdate()
   ↓
int affected-row count
```

---

## Doubt 4: `commit()` vs `executeBatch()`

They are completely different.

```text
executeBatch()
    ↓
Execute grouped statements
```

```text
commit()
    ↓
Commit transaction
```

They can be used together.

---

## Doubt 5: `rollback()` vs `close()`

They are **not the same**.

```text
rollback()
    ↓
Undo uncommitted transaction changes
```

```text
close()
    ↓
Release the JDBC resource
```

---

## Doubt 6: PreparedStatement vs SQL injection

PreparedStatement helps prevent injection **when untrusted values are passed as parameters**.

Bad:

```java
String sql =
    "SELECT ... " + userInput;
```

Good:

```java
String sql =
    "SELECT ... WHERE name = ?";

ps.setString(1, userInput);
```

---

## Doubt 7: Can `?` represent a column name?

Normally **no**.

This:

```sql
SELECT ? FROM student
```

does not mean:

> "Dynamically choose a column."

Parameter markers are for values, not arbitrary SQL identifiers.

---

## Doubt 8: Can `?` represent a table name?

Normally **no**.

```sql
SELECT * FROM ?
```

is not the normal use of parameter binding.

Use trusted allowlists for dynamic identifiers.

---

## Doubt 9: Does `commit()` happen automatically after `executeUpdate()`?

If auto-commit is enabled, each statement is normally committed automatically.

If:

```java
con.setAutoCommit(false);
```

then you must explicitly commit:

```java
con.commit();
```

or roll back:

```java
con.rollback();
```

---

## Doubt 10: Does rollback undo committed data?

Normally, **no**.

Once a transaction has been committed, a later rollback does not undo that already-committed transaction.

Think:

```text
Before commit
     ↓
rollback possible
```

After:

```text
commit()
```

that transaction is completed.

---

## Doubt 11: Is transaction = batch?

**No.**

```text
Batch
→ grouping execution

Transaction
→ controlling commit/rollback
```

---

## Doubt 12: Is PreparedStatement only for SELECT?

No.

It can be used for:

```text
SELECT
INSERT
UPDATE
DELETE
```

and many other SQL statements.

---

## Doubt 13: Is try-with-resources the same as `finally`?

They solve the same general cleanup problem in different ways.

Traditional:

```java
try {
    ...
} finally {
    resource.close();
}
```

Modern preferred style:

```java
try (Resource resource = ...) {
    ...
}
```

Try-with-resources is designed specifically for automatic resource management.

---

## Doubt 14: Does try-with-resources eliminate SQLException?

**No.**

It handles resource cleanup.

You still need to handle database exceptions:

```java
try (Connection con = ...) {
    ...
} catch (SQLException e) {
    ...
}
```

So:

```text
try-with-resources
    ≠
exception prevention
```

It is primarily:

```text
resource management
```

---

## Doubt 15: Is connection pooling a replacement for try-with-resources?

**No.**

They solve different problems.

```text
Connection pooling
→ reuse physical connections

try-with-resources
→ reliably release resources
```

They are complementary.

---

## Doubt 16: Does more connection-pool size mean more performance?

**No.**

Too few:

```text
waiting
```

Too many:

```text
database overload
```

The appropriate size depends on workload and database capacity.

---

## Doubt 17: Does more batching always mean better performance?

**No.**

Huge batches can cause:

```text
large memory usage
long transactions
large rollback cost
database pressure
```

Batch size should be tested and tuned.

---

# 🏆 Final DOUBTKILLER Map

```text
                    JDBC BEST PRACTICES
                            │
       ┌────────────────────┼────────────────────┐
       ↓                    ↓                    ↓
 TRY-WITH-RESOURCES   PREPAREDSTATEMENT   CONNECTION MANAGEMENT
       │                    │                    │
       ↓                    ↓                    ↓
 Auto close           ? = parameter       Acquire
       │                    │              ↓
       ↓                    ↓             Use
 Reverse close         setXXX()            ↓
       │                    │             Close
       ↓                    ↓              ↓
 Resource safety       SQL injection      Pool release
                            │
                            ↓
                    TRANSACTION MANAGEMENT
                            │
                 ┌──────────┴──────────┐
                 ↓                     ↓
          setAutoCommit(false)       Auto-commit
                 ↓
          operations
             /      \
        success     failure
           ↓           ↓
        commit      rollback
                            │
                            ↓
                  SQL INJECTION PREVENTION
                            │
                 ┌──────────┴──────────┐
                 ↓                     ↓
          Parameter values       Dynamic identifiers
                 ↓                     ↓
        PreparedStatement          Allowlist
                            │
                            ↓
                       PERFORMANCE
                            │
          ┌─────────────────┼─────────────────┐
          ↓                 ↓                 ↓
       Pooling           Batching          Efficient SQL
          ↓                 ↓                 ↓
     Reuse connections  Fewer round trips   Less data
                                              │
                           ┌──────────────────┼──────────────┐
                           ↓                  ↓              ↓
                         Indexes            N+1          Result size
                           │
                           ↓
                      MEASURE FIRST
```

## 🔥 The six rules you should never forget

> **1. Open resources → close them with try-with-resources.**
> **2. SQL values → use PreparedStatement parameters.**
> **3. Connections → acquire late and release promptly.**
> **4. Multiple dependent operations → use transactions.**
> **5. Untrusted input → never concatenate it into SQL.**
> **6. Performance → measure the real bottleneck before optimizing.**
