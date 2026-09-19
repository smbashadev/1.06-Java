# 7. Statement in Java — DEEPDIVE

`Statement` is a core JDBC interface used to **send SQL statements from a Java application to a database and process the result returned by the database**.

We will study each sub-concept separately:

```text
7. Statement
│
├── Statement Interface
├── execute()
├── executeQuery()
├── executeUpdate()
└── executeBatch()
```

---

# 1. `Statement` Interface

## 1.1 What is `Statement`?

`Statement` is an interface from the JDBC API:

```java
java.sql.Statement
```

It provides methods for sending SQL commands to a database.

Example:

```java
Statement st = con.createStatement();
```

Here:

```text
con
 │
 │ createStatement()
 ▼
Statement object
 │
 ▼
SQL execution
 │
 ▼
Database
```

---

## 1.2 Why do we need `Statement`?

A `Connection` represents the connection/session with the database.

But `Connection` itself is not the normal object through which we send ordinary SQL commands.

We create a `Statement` from the connection:

```java
Connection con = ...;

Statement st =
    con.createStatement();
```

Then use `st`:

```java
st.executeQuery("SELECT * FROM student");
```

So:

```text
Connection
    ↓
creates
    ↓
Statement
    ↓
executes SQL
```

---

# 1.3 Is `Statement` a class or interface?

It is an **interface**.

```java
public interface Statement
```

Therefore:

```java
Statement st = new Statement();
```

❌ Invalid.

Instead:

```java
Statement st =
    con.createStatement();
```

The JDBC driver supplies the implementation.

---

# 1.4 Who creates the actual Statement object?

You call:

```java
con.createStatement();
```

But you don't directly create the implementation.

Conceptually:

```text
Your Java code
      ↓
Connection interface
      ↓
JDBC Driver implementation
      ↓
Statement implementation
```

This is one of the benefits of JDBC's abstraction.

Your code works against the JDBC interfaces rather than a particular database's internal implementation.

---

# 1.5 What can `Statement` do?

The `Statement` interface provides many methods, but the important execution methods in this topic are:

```text
Statement
│
├── execute()
├── executeQuery()
├── executeUpdate()
└── executeBatch()
```

There are also methods such as:

```text
addBatch()
getResultSet()
getUpdateCount()
close()
cancel()
setQueryTimeout()
```

and others.

---

# 1.6 Basic Statement program

```java
import java.sql.*;

public class Demo {
    public static void main(String[] args)
            throws SQLException {

        Connection con =
            DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/college",
                "root",
                "password"
            );

        Statement st =
            con.createStatement();

        ResultSet rs =
            st.executeQuery(
                "SELECT * FROM student"
            );

        while (rs.next()) {
            System.out.println(
                rs.getInt("id")
            );
        }

        rs.close();
        st.close();
        con.close();
    }
}
```

The important flow is:

```text
DriverManager
     ↓
Connection
     ↓
createStatement()
     ↓
Statement
     ↓
executeQuery()
     ↓
ResultSet
```

Modern code should generally use **try-with-resources** so JDBC resources are closed automatically.

---

# 2. `execute()`

## 2.1 What is `execute()`?

`execute()` is a **general-purpose SQL execution method**.

Basic form:

```java
boolean result =
    st.execute(sql);
```

Its special characteristic is that it returns:

```java
boolean
```

rather than directly returning a `ResultSet` or update count.

---

# 2.2 Why does `execute()` return boolean?

Because the SQL being executed might produce different kinds of results.

The important distinction is:

```text
execute()
    │
    ├── true
    │    ↓
    │  ResultSet
    │
    └── false
         ↓
       update count
       or no result
```

If it returns `true`, the first result is a `ResultSet`.

You can retrieve it using:

```java
ResultSet rs =
    st.getResultSet();
```

If it returns `false`, you can inspect:

```java
int count =
    st.getUpdateCount();
```

---

# 2.3 Example with SELECT

```java
boolean result =
    st.execute(
        "SELECT * FROM student"
    );

if (result) {

    ResultSet rs =
        st.getResultSet();

    while (rs.next()) {
        System.out.println(
            rs.getString("name")
        );
    }
}
```

Because the SQL produces rows:

```text
execute()
   ↓
true
   ↓
getResultSet()
   ↓
ResultSet
```

---

# 2.4 Example with UPDATE

```java
boolean result =
    st.execute(
        "UPDATE student " +
        "SET marks = 90 " +
        "WHERE id = 101"
    );

if (!result) {

    int count =
        st.getUpdateCount();

    System.out.println(
        "Rows affected: " + count
    );
}
```

Conceptually:

```text
UPDATE
  ↓
execute()
  ↓
false
  ↓
getUpdateCount()
  ↓
number of affected rows
```

---

# 2.5 `execute()` does NOT mean "execute SELECT only"

This is a common misunderstanding.

`execute()` is general-purpose.

It can execute SQL that produces:

```text
ResultSet
```

or:

```text
Update count
```

or potentially other results depending on the statement and database.

---

# 2.6 Why would we use `execute()` instead of the other methods?

Use it when:

* the type of result isn't known beforehand;
* the SQL can produce different result types;
* you need to handle multiple results;
* you're writing generic SQL execution code.

For a simple `SELECT`, however:

```java
executeQuery()
```

is usually clearer.

For a simple update:

```java
executeUpdate()
```

is usually clearer.

---

# 2.7 `execute()` and multiple results

This is an advanced reason for using `execute()`.

A statement can potentially produce multiple results.

Conceptually:

```text
execute()
   ↓
Result 1
   ↓
getMoreResults()
   ↓
Result 2
   ↓
getMoreResults()
   ↓
Result 3
```

For example, stored routines or database-specific operations may produce multiple results.

The `Statement` API provides methods such as:

```java
getMoreResults()
```

to move through them.

Therefore:

> `execute()` is more general than `executeQuery()` and `executeUpdate()`.

---

# 3. `executeQuery()`

## 3.1 What is `executeQuery()`?

`executeQuery()` is used to execute SQL that is expected to produce a **single `ResultSet`**.

Syntax:

```java
ResultSet rs =
    st.executeQuery(sql);
```

The classic use is:

```sql
SELECT
```

---

# 3.2 Example

```java
Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

Then:

```java
while (rs.next()) {

    int id =
        rs.getInt("id");

    String name =
        rs.getString("name");

    System.out.println(
        id + " " + name
    );
}
```

---

# 3.3 What is a `ResultSet`?

A `ResultSet` represents the tabular data returned by a query.

Suppose the database contains:

```text
id    name      marks
---------------------
101   Ravi       90
102   Raj        85
103   Amit       95
```

After:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

the `ResultSet` allows Java to traverse those rows.

---

# 3.4 Why do we use `rs.next()`?

Initially, the `ResultSet` cursor is positioned before the first row.

```text
Before first row
       ↓
     rs.next()
       ↓
     Row 1
       ↓
     rs.next()
       ↓
     Row 2
```

Example:

```java
while (rs.next()) {

    System.out.println(
        rs.getInt("id")
    );
}
```

---

# 3.5 Can `executeQuery()` be used for UPDATE?

It should not be used for statements that don't produce a `ResultSet`.

For example:

```java
st.executeQuery(
    "UPDATE student SET marks = 90"
);
```

❌ Incorrect usage.

For an update:

```java
st.executeUpdate(
    "UPDATE student SET marks = 90"
);
```

---

# 3.6 What does `executeQuery()` return?

Exactly:

```java
ResultSet
```

Therefore:

```java
ResultSet rs =
    st.executeQuery(sql);
```

---

# 3.7 Important distinction

```text
executeQuery()
       ↓
expects a ResultSet
       ↓
returns ResultSet
```

Whereas:

```text
execute()
       ↓
determines result type
       ↓
returns boolean
```

---

# 4. `executeUpdate()`

## 4.1 What is `executeUpdate()`?

`executeUpdate()` executes SQL that produces an **update count** rather than a query result.

Typical examples:

```text
INSERT
UPDATE
DELETE
```

It returns:

```java
int
```

---

# 4.2 INSERT example

```java
int count =
    st.executeUpdate(
        "INSERT INTO student " +
        "(id, name, marks) " +
        "VALUES (101, 'Ravi', 90)"
    );
```

If one row is inserted:

```text
count = 1
```

---

# 4.3 UPDATE example

```java
int count =
    st.executeUpdate(
        "UPDATE student " +
        "SET marks = 95 " +
        "WHERE id = 101"
    );
```

If one row is affected:

```text
count = 1
```

---

# 4.4 DELETE example

```java
int count =
    st.executeUpdate(
        "DELETE FROM student " +
        "WHERE id = 101"
    );
```

If one row is deleted:

```text
count = 1
```

---

# 4.5 What exactly does the returned `int` mean?

Usually it is the **update count** — the number of rows affected by the operation.

Example:

```sql
UPDATE student
SET marks = 90;
```

Suppose 50 rows are affected.

Then:

```java
int count =
    st.executeUpdate(sql);
```

gives an update count corresponding to those affected rows.

---

# 4.6 Is `executeUpdate()` only for INSERT, UPDATE, DELETE?

This is an important nuance.

No.

JDBC also allows `executeUpdate()` for SQL statements that return no result set but have an update count, including applicable **DDL** statements such as:

```sql
CREATE TABLE
ALTER TABLE
DROP TABLE
```

For example:

```java
int result =
    st.executeUpdate(
        "CREATE TABLE test " +
        "(id INT)"
    );
```

The exact update-count behavior for DDL can be driver/database dependent.

So the better rule is:

> Use `executeUpdate()` for SQL that does not return a `ResultSet` and instead produces an update count, including common DML such as INSERT/UPDATE/DELETE and applicable DDL.

---

# 4.7 `executeUpdate()` vs `executeQuery()`

|              | `executeQuery()`        | `executeUpdate()`                 |
| ------------ | ----------------------- | --------------------------------- |
| Main purpose | Retrieve data           | Modify/define data                |
| Typical SQL  | `SELECT`                | `INSERT`, `UPDATE`, `DELETE`, DDL |
| Return type  | `ResultSet`             | `int`                             |
| Represents   | Returned rows           | Update count                      |
| Example      | `SELECT * FROM student` | `UPDATE student SET marks=90`     |

Memory:

```text
Query → ResultSet
Update → int
```

---

# 5. `executeBatch()`

## 5.1 What is a batch?

Suppose you need to execute 1,000 INSERT statements.

One approach:

```text
INSERT 1 → execute
INSERT 2 → execute
INSERT 3 → execute
...
INSERT 1000 → execute
```

Instead, JDBC allows you to build a batch:

```text
INSERT 1 ┐
INSERT 2 │
INSERT 3 ├── Batch
   ...   │
INSERT N ┘
     ↓
executeBatch()
```

---

# 5.2 `addBatch()`

Before `executeBatch()`, commands are added using:

```java
st.addBatch(sql);
```

Example:

```java
Statement st =
    con.createStatement();

st.addBatch(
    "INSERT INTO student " +
    "VALUES (101, 'Ravi', 90)"
);

st.addBatch(
    "INSERT INTO student " +
    "VALUES (102, 'Raj', 85)"
);

st.addBatch(
    "INSERT INTO student " +
    "VALUES (103, 'Amit', 95)"
);
```

Nothing has been executed merely because you called `addBatch()`.

The commands have been **added to the batch**.

---

# 5.3 `executeBatch()`

Now:

```java
int[] counts =
    st.executeBatch();
```

This executes the accumulated commands.

The return type is:

```java
int[]
```

because there can be multiple update counts.

Conceptually:

```text
SQL 1 → 1 row affected
SQL 2 → 1 row affected
SQL 3 → 1 row affected

       ↓

int[] counts
```

---

# 5.4 Why `int[]`?

Suppose:

```text
Statement 1 → 2 rows affected
Statement 2 → 1 row affected
Statement 3 → 5 rows affected
```

Then conceptually:

```text
counts = [2, 1, 5]
```

Each element corresponds to a command/result in the batch, subject to JDBC's batch update semantics.

---

# 5.5 Complete batch example

```java
Statement st =
    con.createStatement();

st.addBatch(
    "INSERT INTO student " +
    "VALUES (101, 'Ravi', 90)"
);

st.addBatch(
    "INSERT INTO student " +
    "VALUES (102, 'Raj', 85)"
);

st.addBatch(
    "INSERT INTO student " +
    "VALUES (103, 'Amit', 95)"
);

int[] result =
    st.executeBatch();

for (int count : result) {
    System.out.println(count);
}
```

Flow:

```text
createStatement()
       ↓
addBatch()
       ↓
addBatch()
       ↓
addBatch()
       ↓
executeBatch()
       ↓
int[]
```

---

# 5.6 Does `addBatch()` execute SQL?

**No.**

This:

```java
st.addBatch(sql);
```

means:

> "Add this command to the batch."

This:

```java
st.executeBatch();
```

means:

> "Execute the accumulated batch."

---

# 5.7 Why use batching?

Batching can be useful when you need to perform many related SQL operations.

Instead of treating each operation as an isolated JDBC execution, you can submit a group as a batch.

This can reduce communication overhead and improve performance, although the actual performance benefit depends on the database, JDBC driver, network, batch size, and workload.

---

# 5.8 Does `executeBatch()` automatically mean transaction?

**No.**

This is a very important distinction.

Batching and transactions are separate concepts.

```text
Batching
    ↓
Groups SQL commands for execution
```

while:

```text
Transaction
    ↓
Controls commit/rollback behavior
```

You can combine them:

```java
con.setAutoCommit(false);

st.addBatch(sql1);
st.addBatch(sql2);
st.addBatch(sql3);

st.executeBatch();

con.commit();
```

If an appropriate failure occurs:

```java
con.rollback();
```

---

# 🔥 `execute()` vs `executeQuery()` vs `executeUpdate()` vs `executeBatch()`

This is the heart of the entire topic.

| Method            | What it answers                             | Return type |
| ----------------- | ------------------------------------------- | ----------- |
| `execute()`       | "What kind of result did this SQL produce?" | `boolean`   |
| `executeQuery()`  | "Give me the rows."                         | `ResultSet` |
| `executeUpdate()` | "How many rows/updates were affected?"      | `int`       |
| `executeBatch()`  | "Execute all accumulated commands."         | `int[]`     |

---

# 🔥 Think of Them as Four Different Questions

### `execute()`

> **"Execute this SQL; I'll determine what result came back."**

```java
boolean b = st.execute(sql);
```

---

### `executeQuery()`

> **"Execute this query and give me its rows."**

```java
ResultSet rs =
    st.executeQuery(sql);
```

---

### `executeUpdate()`

> **"Execute this update and tell me the update count."**

```java
int count =
    st.executeUpdate(sql);
```

---

### `executeBatch()`

> **"Execute all the commands I've accumulated."**

```java
int[] counts =
    st.executeBatch();
```

---

# 🔥 Why Doesn't `executeQuery()` Return `int`?

Because its job is to return rows.

```text
SELECT
  ↓
Rows
  ↓
ResultSet
```

Therefore:

```java
ResultSet rs =
    st.executeQuery(sql);
```

---

# 🔥 Why Doesn't `executeUpdate()` Return `ResultSet`?

Because its job is to return an update count.

```text
UPDATE
  ↓
Rows affected
  ↓
int
```

Therefore:

```java
int count =
    st.executeUpdate(sql);
```

---

# 🔥 Why Does `execute()` Return `boolean`?

Because it needs to tell you which kind of first result was produced.

```text
execute()
   │
   ├── true
   │     ↓
   │  ResultSet available
   │
   └── false
         ↓
      Update count/no result
```

Then you use:

```java
st.getResultSet();
```

or:

```java
st.getUpdateCount();
```

---

# 🔥 Why Does `executeBatch()` Return `int[]`?

Because there are multiple commands.

```text
SQL 1 → count 1
SQL 2 → count 2
SQL 3 → count 3
             ↓
         int[] result
```

---

# Statement Execution Decision Tree

When writing JDBC code, think:

```text
What am I executing?
       │
       ├── SELECT / expected ResultSet
       │       ↓
       │   executeQuery()
       │
       ├── INSERT / UPDATE / DELETE / applicable DDL
       │       ↓
       │   executeUpdate()
       │
       ├── Multiple commands collected
       │       ↓
       │   executeBatch()
       │
       └── Result type not known /
           multiple-result handling
               ↓
           execute()
```

---

# ⚠️ Very Important: `Statement` and SQL Injection

Consider:

```java
String name =
    userInput;

String sql =
    "SELECT * FROM student " +
    "WHERE name = '" + name + "'";

st.executeQuery(sql);
```

If `userInput` is untrusted, constructing SQL this way can create SQL-injection vulnerabilities.

For values supplied externally, prefer:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student " +
        "WHERE name = ?"
    );

ps.setString(1, name);

ResultSet rs =
    ps.executeQuery();
```

Therefore:

```text
Statement
    ↓
Best for simple/static SQL

PreparedStatement
    ↓
Best for parameterized SQL
```

---

# Statement Lifecycle

A typical `Statement` lifecycle is:

```text
Connection
    ↓
createStatement()
    ↓
Statement created
    ↓
Add/execute SQL
    ↓
Process result
    ↓
Statement closed
```

Example:

```java
try (
    Connection con =
        DriverManager.getConnection(
            url, username, password
        );

    Statement st =
        con.createStatement();

    ResultSet rs =
        st.executeQuery(
            "SELECT * FROM student"
        )
) {

    while (rs.next()) {
        System.out.println(
            rs.getString("name")
        );
    }
}
```

Try-with-resources ensures the JDBC resources are closed automatically.

---

# Complete Conceptual Architecture

```text
                    Java Application
                           │
                           ▼
                       Connection
                           │
                   createStatement()
                           │
                           ▼
                       Statement
                           │
          ┌────────────────┼─────────────────┐
          │                │                 │
          ▼                ▼                 ▼
     executeQuery()   executeUpdate()     execute()
          │                │                 │
          ▼                ▼            ┌────┴────┐
      ResultSet           int           │         │
                                        ▼         ▼
                                   ResultSet   Update Count
```

And separately:

```text
Statement
    │
    ├── addBatch()
    │       ↓
    │   SQL commands
    │       ↓
    └── executeBatch()
            ↓
           int[]
```

---

# 🧠 Deep-Dive Summary

## `Statement`

> JDBC interface used to send SQL statements to the database.

## `execute()`

> General-purpose execution method that returns `true` when the first result is a `ResultSet` and `false` when it is an update count or there is no result.

## `executeQuery()`

> Executes a SQL statement expected to return a `ResultSet`, commonly `SELECT`.

## `executeUpdate()`

> Executes SQL that produces an update count, commonly `INSERT`, `UPDATE`, `DELETE`, and applicable DDL.

## `executeBatch()`

> Executes the commands accumulated using `addBatch()` and returns an array of update counts/status values.

---

# ⭐ Final Memory Formula

```text
                    STATEMENT
                        │
       ┌────────────────┼─────────────────┐
       │                │                 │
       ▼                ▼                 ▼
   execute()      executeQuery()    executeUpdate()
       │                │                 │
   boolean          ResultSet             int
       │
   ┌───┴────┐
   │        │
 true     false
   │        │
ResultSet  Update
           Count


                STATEMENT
                    │
               addBatch()
                    │
               addBatch()
                    │
               addBatch()
                    │
                    ▼
             executeBatch()
                    │
                    ▼
                   int[]
```

### The golden rule

> **`executeQuery()` → rows, `executeUpdate()` → update count, `execute()` → unknown/general result handling, `executeBatch()` → multiple accumulated commands.**
