# 17. JDBC Best Practices in Java / TEACHME

Let's learn this as if we are building a **real JDBC application from scratch**.

The six best practices are:

```text
JDBC Best Practices
│
├── 1. try-with-resources
├── 2. PreparedStatement
├── 3. Connection management
├── 4. Transaction management
├── 5. SQL Injection prevention
└── 6. Performance
```

The easiest way to understand them is to first see **what problem each one solves**.

---

# 1. try-with-resources

## 1.1 First understand the problem

Whenever we work with JDBC, we create resources.

For example:

```java
Connection con;
PreparedStatement ps;
ResultSet rs;
```

Think of them as things that must eventually be released.

```text
Connection      → must be closed
PreparedStatement → must be closed
ResultSet       → must be closed
```

If we forget to close them repeatedly, resources can accumulate.

Eventually:

```text
Application
    ↓
Connection 1 → not closed
Connection 2 → not closed
Connection 3 → not closed
...
Connection N → not closed
    ↓
Resources exhausted
```

This can cause connection-pool exhaustion, timeouts, or other failures.

---

# 1.2 Traditional solution

We could manually close everything:

```java
Connection con = null;
PreparedStatement ps = null;
ResultSet rs = null;

try {

    con = DriverManager.getConnection(
        url, user, password
    );

    ps = con.prepareStatement(
        "SELECT * FROM student"
    );

    rs = ps.executeQuery();

    while (rs.next()) {
        System.out.println(
            rs.getString("name")
        );
    }

} finally {

    if (rs != null)
        rs.close();

    if (ps != null)
        ps.close();

    if (con != null)
        con.close();
}
```

This works, but there is a lot of cleanup code.

---

# 1.3 What is try-with-resources?

Java provides a better mechanism:

```java
try (Connection con =
         DriverManager.getConnection(
             url, user, password
         )) {

    // JDBC work

}
```

Java automatically closes the resource when the `try` block finishes.

This is called:

> **try-with-resources**

---

# 1.4 Simple mental model

Think:

```text
try
 ↓
Use resource
 ↓
Work completed
 ↓
Java automatically closes resource
```

Even if an exception occurs:

```text
try
 ↓
Database operation
 ↓
Exception
 ↓
Resource still gets closed
 ↓
catch
```

That's the important benefit.

---

# 1.5 Multiple JDBC resources

We can write:

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

Three resources are being managed.

```text
Connection
    ↓
PreparedStatement
    ↓
ResultSet
```

They are closed in reverse order:

```text
ResultSet
    ↓
PreparedStatement
    ↓
Connection
```

---

# 1.6 Why reverse order?

Because resources often depend on other resources.

For example:

```text
Connection
   ↓
PreparedStatement
   ↓
ResultSet
```

The ResultSet was created from the PreparedStatement, which was created from the Connection.

So it makes sense to close:

```text
ResultSet first
PreparedStatement second
Connection last
```

---

# 1.7 What does `close()` mean with connection pooling?

This is important.

Suppose:

```java
Connection con =
    dataSource.getConnection();
```

If `DataSource` uses a connection pool, then:

```java
con.close();
```

usually means:

```text
Application
    ↓
close()
    ↓
Return logical connection
    ↓
Connection goes back to pool
```

It doesn't necessarily mean the physical database connection is destroyed.

Therefore:

> **Even with connection pooling, always close the Connection.**

---

# 1.8 Teach-me rule

Remember:

> **If JDBC gives you a resource, give it back.**

And the easiest way is:

```java
try (...) {
    // use it
}
```

---

# 2. PreparedStatement

Now let's solve the second major problem.

## 2.1 The problem

Suppose we want:

```text
Find student whose ID = 101
```

We might write:

```java
int id = 101;

String sql =
    "SELECT * FROM student WHERE id = "
    + id;
```

This produces:

```sql
SELECT * FROM student WHERE id = 101
```

It works.

But imagine the value is supplied by an external user, especially a String.

Then directly concatenating that input into SQL can allow the input to become part of the SQL syntax.

That's dangerous.

---

# 2.2 PreparedStatement solves this

Instead:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";
```

Then:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Now we provide the value separately:

```java
ps.setInt(1, 101);
```

Then:

```java
ResultSet rs =
    ps.executeQuery();
```

---

# 2.3 What is `?`?

The `?` is a:

> **parameter placeholder**

Example:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ?";
```

We provide:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

Remember:

> JDBC parameter indexes start at **1**, not 0.

---

# 2.4 Common setter methods

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
ps.setLong(4, 100000L);
```

### Boolean

```java
ps.setBoolean(5, true);
```

---

# 2.5 `executeQuery()`

Use:

```java
executeQuery()
```

when the SQL produces a result set, normally a `SELECT`.

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

The return type is:

```text
ResultSet
```

---

# 2.6 `executeUpdate()`

Use:

```java
executeUpdate()
```

for DML operations such as:

```text
INSERT
UPDATE
DELETE
```

Example:

```java
String sql =
    "UPDATE student SET name = ? WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, "Rahul");
ps.setInt(2, 101);

int count =
    ps.executeUpdate();
```

`count` generally represents the number of affected rows.

---

# 2.7 Why PreparedStatement is preferred

It provides:

### 1. Parameterization

```text
SQL structure
     +
values
```

are handled separately.

### 2. Security

It strongly protects parameter values against SQL injection when used correctly.

### 3. Readability

Compare:

```java
String sql =
    "SELECT * FROM student WHERE id = " + id;
```

with:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";
```

The second clearly tells us:

> "This SQL expects a value."

---

# 2.8 Reusing PreparedStatement

Suppose we need to insert several students.

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

Same SQL structure, different values.

For very large numbers of operations, batch processing can be more efficient.

---

# 2.9 Teach-me rule

Remember:

> **SQL with values → use PreparedStatement.**

Think:

```text
SQL
 ↓
?
 ↓
setXXX()
 ↓
execute
```

---

# 3. Connection Management

Now let's understand the `Connection`.

## 3.1 What is a Connection?

A JDBC Connection represents a session through which Java communicates with a database.

Think of it like opening a communication channel:

```text
Java Application
       ↓
   Connection
       ↓
 JDBC Driver
       ↓
    Database
```

---

# 3.2 Creating a connection

Traditional approach:

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

In many real applications, however, we use:

```java
Connection con =
    dataSource.getConnection();
```

A `DataSource` can be configured to use connection pooling.

---

# 3.3 What is connection pooling?

Suppose every request creates a brand-new physical database connection:

```text
Request 1 → Create connection
Request 2 → Create connection
Request 3 → Create connection
Request 4 → Create connection
```

Creating database connections repeatedly can be expensive.

A connection pool keeps reusable connections:

```text
             Connection Pool
        ┌────┬────┬────┬────┐
        │ C1 │ C2 │ C3 │ C4 │
        └────┴────┴────┴────┘
             ↑
             │
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

# 3.4 Important: don't forget close()

Some beginners think:

> "If I'm using a pool, I don't need to close the connection."

Wrong.

You still do:

```java
try (Connection con =
         dataSource.getConnection()) {

    // work
}
```

Closing the logical connection usually releases it back to the pool.

---

# 3.5 Don't hold a connection unnecessarily

Bad design:

```text
Get connection
     ↓
Do unrelated Java processing
     ↓
Call another service
     ↓
Wait
     ↓
Finally execute SQL
     ↓
Close connection
```

The connection was sitting unused.

Better:

```text
Prepare what you need
       ↓
Get connection
       ↓
Perform DB work
       ↓
Release connection
```

---

# 3.6 Why does this matter?

Suppose your pool contains 10 connections:

```text
Pool = 10 connections
```

If 10 requests each hold a connection while doing unrelated work:

```text
C1 → occupied
C2 → occupied
...
C10 → occupied
```

Request 11 cannot immediately obtain one.

So:

> **Acquire connections as late as practical and release them as soon as the database work is finished.**

---

# 3.7 Don't use one global Connection everywhere

A common beginner mistake is:

```java
static Connection con;
```

and then allowing the entire application to share it indiscriminately.

This can cause problems because a Connection has mutable state:

```text
auto-commit
transaction state
isolation level
warnings
session settings
```

Concurrent operations can interfere with each other.

Use an appropriate connection-management strategy, commonly a DataSource/pool in application environments.

---

# 3.8 Teach-me rule

Think:

```text
Connection
    ↓
Borrow
    ↓
Use quickly
    ↓
Close/release
```

---

# 4. Transaction Management

Now we come to one of the most important JDBC concepts.

## 4.1 What is a transaction?

A transaction is a group of database operations treated as one logical unit.

Imagine transferring ₹1,000:

```text
Account A
    ↓
- ₹1,000

Account B
    ↓
+ ₹1,000
```

We don't want this:

```text
Debit A → SUCCESS
Credit B → FAILURE
```

because money has disappeared.

We want:

```text
Both succeed
     ↓
COMMIT

Something fails
     ↓
ROLLBACK
```

---

# 4.2 Auto-commit

JDBC connections normally start with auto-commit enabled.

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

Each statement can be committed independently.

That's not always what we want.

---

# 4.3 Disable auto-commit

For a multi-operation transaction:

```java
con.setAutoCommit(false);
```

Now:

```text
SQL 1
 ↓
SQL 2
 ↓
SQL 3
 ↓
Commit manually
```

---

# 4.4 commit()

When everything succeeds:

```java
con.commit();
```

means:

> Make the transaction's changes permanent.

---

# 4.5 rollback()

If something fails:

```java
con.rollback();
```

means:

> Undo the transaction's uncommitted changes.

---

# 4.6 Complete example

```java
try (Connection con =
         dataSource.getConnection()) {

    con.setAutoCommit(false);

    try {

        // Operation 1
        debitAccount(con);

        // Operation 2
        creditAccount(con);

        // Everything succeeded
        con.commit();

    } catch (SQLException e) {

        // Something failed
        con.rollback();

        throw e;
    }

}
```

The flow is:

```text
Get Connection
      ↓
setAutoCommit(false)
      ↓
Operation 1
      ↓
Operation 2
      ↓
   SUCCESS?
   /      \
 YES       NO
 ↓          ↓
commit   rollback
```

---

# 4.7 Why rollback?

Suppose:

```text
Operation 1 → SUCCESS
Operation 2 → SUCCESS
Operation 3 → FAILURE
```

Without rollback, we might have partially applied the work.

With rollback:

```text
Operation 1 → undone
Operation 2 → undone
Operation 3 → failed
```

The transaction returns to its previous transactional state.

---

# 4.8 Savepoint

Sometimes we don't want to roll back everything.

We can create a savepoint:

```java
Savepoint sp =
    con.setSavepoint();
```

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
rollback(sp)
```

The rollback can return to the savepoint rather than necessarily abandoning the whole transaction.

---

# 4.9 Transaction and batch are different

This causes confusion.

### Batch

```text
addBatch()
executeBatch()
```

is about grouping SQL executions.

### Transaction

```text
commit()
rollback()
```

is about controlling transactional atomicity.

You can combine them:

```java
con.setAutoCommit(false);

ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();

con.commit();
```

So:

```text
Batch      → how operations are submitted
Transaction → when changes become permanent
```

---

# 4.10 Important pooled connection issue

Suppose:

```java
Connection con =
    dataSource.getConnection();

con.setAutoCommit(false);
```

You must not simply abandon the connection while an unfinished transaction remains.

The logical flow should be:

```text
Transaction starts
      ↓
Operations
      ↓
commit OR rollback
      ↓
close/release connection
```

---

# 4.11 Teach-me rule

Remember:

> **Success → commit. Failure → rollback.**

---

# 5. SQL Injection Prevention

Now let's understand the security problem more deeply.

## 5.1 What is SQL injection?

SQL injection occurs when untrusted input is allowed to become part of SQL syntax.

Suppose:

```java
String username =
    getUserInput();

String sql =
    "SELECT * FROM users " +
    "WHERE username = '" +
    username +
    "'";
```

The application has mixed:

```text
SQL syntax
+
User-controlled data
```

into one SQL string.

That's dangerous.

---

# 5.2 The fundamental security rule

Think:

> **Never allow untrusted data to become SQL syntax.**

Instead:

```text
SQL syntax
     ↓
PreparedStatement

User data
     ↓
Parameter
```

---

# 5.3 Safe version

```java
String sql =
    "SELECT * FROM users " +
    "WHERE username = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, username);

ResultSet rs =
    ps.executeQuery();
```

Now the username is a parameter.

---

# 5.4 Why does this protect us?

Compare the two approaches.

### Dangerous

```text
User input
    ↓
String concatenation
    ↓
SQL text
    ↓
Database
```

### Preferred

```text
SQL template
    ↓
PreparedStatement
    ↓
Parameter binding
    ↓
Database
```

The parameter isn't simply treated as SQL code.

---

# 5.5 Example with LIKE

Suppose we want names containing `"Raj"`.

Don't write:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE name LIKE '%" +
    search +
    "%'";
```

Instead:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE name LIKE ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, "%" + search + "%");
```

The pattern is still supplied as a parameter value.

---

# 5.6 What about dynamic table names?

This is an important limitation.

You can't generally use:

```sql
SELECT * FROM ?
```

with a parameter for the table identifier.

Why?

Because `?` is designed for **values**, not arbitrary SQL identifiers.

If the user chooses:

```text
students
employees
departments
```

use an allowlist.

```java
String table;

switch (choice) {

    case "students":
        table = "student";
        break;

    case "employees":
        table = "employee";
        break;

    default:
        throw new IllegalArgumentException(
            "Invalid table"
        );
}
```

Now only known identifiers are permitted.

---

# 5.7 Is validation enough?

Validation is useful, but don't replace parameterization with validation.

Best approach:

```text
Validate input
       +
PreparedStatement
       +
Least-privilege DB account
```

This is defense in depth.

---

# 5.8 Teach-me rule

Remember:

> **Values → PreparedStatement.**

For dynamic SQL identifiers:

> **Use a strict allowlist.**

---

# 6. Performance

Now let's make the JDBC program efficient.

The important idea is:

> **Don't optimize JDBC by guessing. Understand where the time is actually being spent.**

---

# 6.1 Connection pooling

Instead of repeatedly creating physical connections:

```text
Request
 ↓
Create connection
 ↓
Use
 ↓
Destroy
```

use:

```text
Request
 ↓
Borrow connection
 ↓
Use
 ↓
Return to pool
```

This can reduce connection-establishment overhead and improve scalability.

---

# 6.2 PreparedStatement

Suppose we repeatedly run:

```sql
INSERT INTO student(id, name)
VALUES (?, ?)
```

We can reuse the statement:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name) " +
        "VALUES (?, ?)"
    );

ps.setInt(1, 101);
ps.setString(2, "Ravi");
ps.executeUpdate();

ps.setInt(1, 102);
ps.setString(2, "John");
ps.executeUpdate();
```

This is cleaner and can be beneficial for repeated execution depending on the JDBC driver/database configuration.

Don't interpret this as:

> "PreparedStatement is always faster."

Its most important benefits are parameterization, safety, and maintainability.

---

# 6.3 Batch processing

Suppose you have 10,000 records.

Doing:

```text
INSERT 1 → execute
INSERT 2 → execute
INSERT 3 → execute
...
INSERT 10000 → execute
```

may involve substantial execution/communication overhead.

Batch:

```java
ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();
```

can group many operations.

---

# 6.4 Batch + transaction

For large data operations:

```java
con.setAutoCommit(false);

ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();

con.commit();
```

Now:

```text
Batch
 ↓
grouped execution

Transaction
 ↓
commit/rollback control
```

---

# 6.5 Don't retrieve unnecessary data

Suppose you only need:

```text
id
name
```

Don't unnecessarily retrieve:

```sql
SELECT *
FROM student;
```

Prefer:

```sql
SELECT id, name
FROM student;
```

This can reduce data transfer and processing.

---

# 6.6 Filter data in the database

Suppose there are 1 million students but we only need students from:

```text
Computer Science
```

Bad:

```text
Database
 ↓
1,000,000 rows
 ↓
Java
 ↓
filter
 ↓
100 rows
```

Better:

```sql
SELECT id, name
FROM student
WHERE department = ?;
```

Now:

```text
Database
 ↓
100 relevant rows
 ↓
Java
```

---

# 6.7 Indexes

Suppose we frequently execute:

```sql
SELECT *
FROM student
WHERE roll_no = ?;
```

An appropriate index on `roll_no` can make lookups much faster.

But indexes also have costs:

```text
Index
 ├── improves some reads
 ├── consumes storage
 └── adds write/maintenance overhead
```

Therefore don't create indexes blindly.

---

# 6.8 Avoid N+1 queries

Imagine:

```text
SELECT all departments
```

returns 100 departments.

Then Java does:

```text
Query department 1
Query department 2
Query department 3
...
Query department 100
```

Total:

```text
1 + 100 = 101 queries
```

This is the classic **N+1 query problem**.

Depending on the use case, joins, batching, or other query designs may reduce unnecessary round trips.

---

# 6.9 Don't retrieve huge ResultSets unnecessarily

If the application needs only:

```text
first 20 records
```

don't blindly retrieve millions.

Use appropriate:

* filtering
* pagination
* limits where supported
* fetch strategies

---

# 6.10 Fetch size

JDBC provides:

```java
ps.setFetchSize(500);
```

This can give the driver a hint about fetching rows.

But remember:

> The exact behavior depends on the JDBC driver and database.

It does not universally mean:

```text
"Exactly 500 rows are transferred in one network operation."
```

---

# 6.11 Measure before optimizing

This is one of the most important professional habits.

Don't say:

> "This code must be slow."

Measure it.

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
Network
Result processing
Database locks
Missing indexes
Large result sets
```

---

# 6.12 Database performance matters more than tiny Java changes

Suppose:

```java
ps.executeQuery();
```

takes 5 seconds.

Changing a Java loop slightly may make almost no difference.

The actual problem might be:

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

> **JDBC performance means Java + JDBC driver + network + database.**

---

# 7. Let's Build a Good JDBC Program

Now put everything together.

Suppose we want:

> Find students belonging to a particular department.

```java
String sql =
    "SELECT id, name " +
    "FROM student " +
    "WHERE department = ?";

try (
    Connection con =
        dataSource.getConnection();

    PreparedStatement ps =
        con.prepareStatement(sql)
) {

    ps.setString(1, "Computer Science");

    try (ResultSet rs =
             ps.executeQuery()) {

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
    e.printStackTrace();
}
```

Now identify the best practices.

### `DataSource`

```java
dataSource.getConnection();
```

→ Good connection management.

### try-with-resources

```java
try (...) {
}
```

→ Automatic resource cleanup.

### PreparedStatement

```java
con.prepareStatement(sql);
```

→ Parameterized SQL.

### Parameter

```java
ps.setString(1, "Computer Science");
```

→ Avoids SQL concatenation.

### Specific columns

```sql
SELECT id, name
```

→ Don't retrieve unnecessary columns.

### ResultSet cleanup

```java
try (ResultSet rs = ...)
```

→ Automatically closes ResultSet.

---

# 8. Complete Transaction Example

Now consider a bank transfer.

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

The logic is:

```text
              Get Connection
                    ↓
          setAutoCommit(false)
                    ↓
             Debit Account
                    ↓
            Credit Account
                    ↓
              Everything OK?
                /       \
              YES        NO
               ↓          ↓
            commit     rollback
               ↓          ↓
            Release Connection
```

---

# 9. The Six Concepts as Six Problems

This is the easiest way to remember the entire topic.

## Problem 1: "Who closes my resources?"

Answer:

```text
try-with-resources
```

---

## Problem 2: "How do I safely insert values into SQL?"

Answer:

```text
PreparedStatement
```

---

## Problem 3: "How do I efficiently manage database connections?"

Answer:

```text
DataSource
+
Connection Pool
+
Prompt release
```

---

## Problem 4: "How do I make several operations succeed/fail together?"

Answer:

```text
Transaction
+
commit()
+
rollback()
```

---

## Problem 5: "How do I prevent SQL injection?"

Answer:

```text
PreparedStatement
+
parameter binding
+
allowlist dynamic identifiers
```

---

## Problem 6: "How do I make JDBC efficient?"

Answer:

```text
Connection pooling
+
PreparedStatement
+
Batch processing
+
Efficient SQL
+
Appropriate indexes
+
Reasonable result sizes
+
Measurement
```

---

# 10. One Complete Mental Picture

```text
                    JDBC APPLICATION
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
              │                         │
              ↓                         ↓
       Transaction               PreparedStatement
              │                         │
              │                    Parameter (?)
              │                         │
              ↓                         ↓
       commit/rollback              SQL execution
                                        │
                                        ↓
                                    ResultSet
                                        │
                                        ↓
                              try-with-resources
                                        │
                                        ↓
                                  Auto cleanup
```

Around all of this:

```text
                ┌───────────────────────────┐
                │      SQL INJECTION        │
                │      PREVENTION           │
                │                           │
                │  Parameterize values      │
                │  Validate identifiers     │
                └───────────────────────────┘

                ┌───────────────────────────┐
                │       PERFORMANCE         │
                │                           │
                │ Pooling                   │
                │ Batch processing          │
                │ Efficient SQL             │
                │ Indexes                   │
                │ Reasonable result sets    │
                │ Measure bottlenecks       │
                └───────────────────────────┘
```

---

# 11. TEACHME Final Revision

### 🟢 try-with-resources

**Question:** Who closes JDBC resources?

**Answer:**

```java
try (Connection con = ...) {
}
```

Java automatically closes the resource.

---

### 🟢 PreparedStatement

**Question:** How do I safely supply values to SQL?

**Answer:**

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

ps.setInt(1, id);
```

---

### 🟢 Connection Management

**Question:** How should connections be handled?

**Answer:**

```text
DataSource
 ↓
Pool
 ↓
Borrow
 ↓
Use
 ↓
Close/release
```

---

### 🟢 Transaction Management

**Question:** How do multiple operations succeed/fail together?

**Answer:**

```java
con.setAutoCommit(false);

con.commit();
```

or:

```java
con.rollback();
```

---

### 🟢 SQL Injection Prevention

**Question:** How do I stop user input from becoming SQL syntax?

**Answer:**

```text
Don't concatenate untrusted values.
Use PreparedStatement parameters.
```

---

### 🟢 Performance

**Question:** How do I make JDBC scalable?

**Answer:**

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
Reasonable ResultSets
      +
Measure bottlenecks
```

---

# ⭐ The Golden JDBC Rule

If you remember only this:

```text
                    JDBC BEST PRACTICES
                           │
         ┌─────────────────┼─────────────────┐
         ↓                 ↓                 ↓
       CLOSE           PARAMETERIZE        MANAGE
     Resources            SQL            Connections
         │                 │                 │
         ↓                 ↓                 ↓
try-with-resources   PreparedStatement    DataSource
                                             │
                                             ↓
                                      Connection Pool
                           │
         ┌─────────────────┴─────────────────┐
         ↓                                   ↓
   TRANSACTION                           PERFORMANCE
         │                                   │
 commit / rollback                    Batch / SQL / Index
         │                                   │
         └─────────────────┬─────────────────┘
                           ↓
                    PRODUCTION-QUALITY
                         JDBC
```

### Final memory sentence

> **Close resources, parameterize SQL, manage connections, control transactions, prevent injection, and optimize based on measurement.**
