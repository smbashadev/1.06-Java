# 7. Statement in Java — TEACHME

Let's learn `Statement` **from zero**, as if you're seeing JDBC for the first time.

The goal is not just to memorize four methods. By the end, you should understand **why each method exists, what it sends to the database, what it returns, and when to use it.**

```text
Statement
│
├── Statement Interface
├── execute()
├── executeQuery()
├── executeUpdate()
└── executeBatch()
```

---

# Part 1 — First Understand the Situation

Suppose Java wants to communicate with a database.

Java has this:

```java
Connection con;
```

The `Connection` represents the communication/session with the database.

But now Java wants to say:

> "Database, give me all students."

or:

> "Database, insert this student."

or:

> "Database, update this student's marks."

How does Java send that SQL?

That's where **`Statement`** comes in.

```text
Java Program
     │
     ▼
 Connection
     │
     ▼
 Statement
     │
     ▼
    SQL
     │
     ▼
 Database
```

So remember:

> **`Connection` connects Java to the database; `Statement` is used to send SQL through that connection.**

---

# Part 2 — Statement Interface

## What is `Statement`?

`Statement` is a JDBC **interface** from:

```java
java.sql.Statement
```

It provides methods for executing SQL statements.

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
Statement
```

Then:

```java
st.executeQuery("SELECT * FROM student");
```

---

# Why is Statement an interface?

You might wonder:

> "If it's an interface, how can I use it?"

You don't directly create it.

This is invalid:

```java
Statement st = new Statement();
```

❌ You cannot instantiate an interface.

Instead:

```java
Statement st = con.createStatement();
```

The JDBC driver provides the actual implementation.

Conceptually:

```text
Your Program
     │
     ▼
Statement interface
     │
     ▼
JDBC Driver implementation
     │
     ▼
Database
```

This is part of JDBC's abstraction.

Your Java code doesn't need to know the driver's internal implementation.

---

# Part 3 — Create a Statement

Suppose we already have:

```java
Connection con;
```

We create a statement:

```java
Statement st =
    con.createStatement();
```

Now:

```text
Connection
     │
     │ createStatement()
     ▼
Statement
```

Now we can execute SQL using `st`.

---

# Part 4 — `executeQuery()`

Let's start with the easiest method.

## What does "query" mean?

A query generally means:

> "Ask the database for data."

For example:

```sql
SELECT * FROM student;
```

We expect the database to give us rows.

Therefore we use:

```java
executeQuery()
```

---

## Syntax

```java
ResultSet rs =
    st.executeQuery(sql);
```

Notice the return type:

```text
ResultSet
```

That's very important.

---

# Why ResultSet?

Suppose the database contains:

```text
student
--------------------------------
id     name      marks
101    Ravi      90
102    Raj       85
103    Amit      95
```

We execute:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

The database returns the rows through the `ResultSet`.

Think:

```text
SELECT
  │
  ▼
Database
  │
  ▼
Rows
  │
  ▼
ResultSet
```

---

# Reading the ResultSet

We normally use:

```java
while (rs.next()) {
    ...
}
```

Why `next()`?

Because the `ResultSet` cursor initially starts before the first row.

```text
Before Row 1
     │
     │ rs.next()
     ▼
   Row 1
     │
     │ rs.next()
     ▼
   Row 2
     │
     │ rs.next()
     ▼
   Row 3
```

Example:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );

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

# When should I use `executeQuery()`?

When the SQL is expected to return a `ResultSet`.

Most commonly:

```sql
SELECT
```

Memory:

```text
executeQuery()
       ↓
     SELECT
       ↓
   ResultSet
```

---

# Part 5 — `executeUpdate()`

Now suppose we don't want to retrieve rows.

Suppose we want to insert:

```sql
INSERT INTO student
VALUES (101, 'Ravi', 90);
```

The database isn't giving us a table of rows to read.

Instead, we want to know:

> "How many rows were affected?"

That's where:

```java
executeUpdate()
```

comes in.

---

# What does executeUpdate() return?

It returns:

```java
int
```

Example:

```java
int count =
    st.executeUpdate(
        "UPDATE student " +
        "SET marks = 95 " +
        "WHERE id = 101"
    );
```

If one row was updated:

```text
count = 1
```

---

# Example: INSERT

```java
int count =
    st.executeUpdate(
        "INSERT INTO student " +
        "(id, name, marks) " +
        "VALUES (101, 'Ravi', 90)"
    );

System.out.println(count);
```

If one row was inserted:

```text
1
```

---

# Example: UPDATE

```java
int count =
    st.executeUpdate(
        "UPDATE student " +
        "SET marks = 95 " +
        "WHERE id = 101"
    );
```

If one row was affected:

```text
count = 1
```

---

# Example: DELETE

```java
int count =
    st.executeUpdate(
        "DELETE FROM student " +
        "WHERE id = 101"
    );
```

Again, `count` represents the applicable update count.

---

# What SQL is commonly used with executeUpdate()?

```text
INSERT
UPDATE
DELETE
```

It can also be used for applicable DDL such as:

```text
CREATE TABLE
ALTER TABLE
DROP TABLE
```

where the operation doesn't produce a `ResultSet`.

So don't memorize the rule as merely:

> "executeUpdate is only INSERT/UPDATE/DELETE."

The better rule is:

> **Use `executeUpdate()` when the SQL is expected to produce an update count rather than a `ResultSet`.**

---

# Memory Trick

```text
SELECT
  ↓
"Give me data"
  ↓
executeQuery()
  ↓
ResultSet
```

and:

```text
INSERT / UPDATE / DELETE
  ↓
"Change data"
  ↓
executeUpdate()
  ↓
int
```

---

# Part 6 — `execute()`

Now we reach the method that often confuses beginners.

You've learned:

```java
executeQuery()
```

and:

```java
executeUpdate()
```

So why do we need:

```java
execute()
```

?

Because sometimes we want a **general-purpose execution method**.

---

# What does execute() return?

It returns:

```java
boolean
```

Example:

```java
boolean result =
    st.execute(sql);
```

But what does `true` or `false` mean?

This is the important part.

---

# If execute() returns true

It means:

> The first result produced by the execution is a `ResultSet`.

So:

```java
boolean result =
    st.execute(
        "SELECT * FROM student"
    );

if (result) {

    ResultSet rs =
        st.getResultSet();

}
```

Think:

```text
execute()
   │
   ▼
 true
   │
   ▼
ResultSet available
```

---

# If execute() returns false

It means:

> The first result is not a `ResultSet`; it is an update count or there is no result.

You can obtain the update count using:

```java
int count =
    st.getUpdateCount();
```

Example:

```java
boolean result =
    st.execute(
        "UPDATE student " +
        "SET marks = 95 " +
        "WHERE id = 101"
    );

if (!result) {

    int count =
        st.getUpdateCount();

    System.out.println(count);
}
```

Think:

```text
execute()
   │
   ▼
 false
   │
   ▼
Update count / no result
```

---

# Why doesn't execute() simply return ResultSet?

Because `execute()` is designed to handle **different kinds of results**.

It doesn't know, from its return type alone, whether the first result is:

```text
ResultSet
```

or:

```text
Update count
```

So it first tells you:

```text
true / false
```

Then you inspect the appropriate result.

---

# execute() Mental Model

Imagine you ask a waiter:

> "I don't know what the kitchen is going to return. Tell me what type of result I got."

The waiter says:

```text
true
```

meaning:

> "You got a ResultSet."

Or:

```text
false
```

meaning:

> "You got an update count/no ResultSet."

That's essentially what `execute()` allows you to determine.

---

# `execute()` with SELECT

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

Flow:

```text
SELECT
 ↓
execute()
 ↓
true
 ↓
getResultSet()
 ↓
ResultSet
```

---

# `execute()` with UPDATE

```java
boolean result =
    st.execute(
        "UPDATE student " +
        "SET marks = 90"
    );

if (!result) {

    int count =
        st.getUpdateCount();

    System.out.println(count);
}
```

Flow:

```text
UPDATE
 ↓
execute()
 ↓
false
 ↓
getUpdateCount()
 ↓
int
```

---

# When should I use execute()?

For straightforward SQL, these are usually clearer:

```text
SELECT
   ↓
executeQuery()

UPDATE/INSERT/DELETE
   ↓
executeUpdate()
```

Use `execute()` when you need more general execution behavior, such as when the result type isn't known beforehand or when dealing with multiple results.

---

# Part 7 — `executeBatch()`

Now imagine this problem.

You need to insert 1,000 students.

You could execute each statement individually:

```text
INSERT 1
INSERT 2
INSERT 3
INSERT 4
...
INSERT 1000
```

That means many individual execution operations.

JDBC provides **batch processing**.

---

# What is a batch?

A batch is simply a collection of commands that you want to execute together.

```text
SQL 1
SQL 2
SQL 3
SQL 4
   │
   ▼
 Batch
   │
   ▼
executeBatch()
```

---

# Step 1 — Create Statement

```java
Statement st =
    con.createStatement();
```

---

# Step 2 — Add commands

Use:

```java
addBatch()
```

Example:

```java
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

At this point:

**The batch has been built.**

We have not called `executeBatch()` yet.

---

# Step 3 — Execute the batch

```java
int[] counts =
    st.executeBatch();
```

Notice the return type:

```text
int[]
```

Why an array?

Because multiple SQL commands were executed.

For example:

```text
SQL 1 → 1 row
SQL 2 → 1 row
SQL 3 → 1 row
```

Conceptually:

```text
counts = [1, 1, 1]
```

The exact values can depend on the database/driver and JDBC batch-update semantics.

---

# Complete Batch Example

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

int[] counts =
    st.executeBatch();

for (int count : counts) {
    System.out.println(count);
}
```

---

# `addBatch()` vs `executeBatch()`

This is extremely important.

### `addBatch()`

Means:

> "Put this SQL command into the batch."

```java
st.addBatch(sql);
```

### `executeBatch()`

Means:

> "Execute the commands currently in the batch."

```java
st.executeBatch();
```

So:

```text
addBatch()
    ↓
addBatch()
    ↓
addBatch()
    ↓
executeBatch()
```

---

# Does executeBatch() mean transaction?

**No.**

Don't confuse:

### Batch

Grouping SQL commands for execution.

### Transaction

Controlling commit/rollback of database changes.

They are different concepts.

You can use them together:

```java
con.setAutoCommit(false);

st.addBatch(sql1);
st.addBatch(sql2);
st.addBatch(sql3);

st.executeBatch();

con.commit();
```

---

# Now Let's Compare All Four

This is the most important table in this topic.

| Method            | Think of it as                                | Typical SQL                                  | Return      |
| ----------------- | --------------------------------------------- | -------------------------------------------- | ----------- |
| `execute()`       | "Execute generally; I'll inspect the result." | Any                                          | `boolean`   |
| `executeQuery()`  | "Give me the rows."                           | `SELECT`                                     | `ResultSet` |
| `executeUpdate()` | "Change something and give me the count."     | `INSERT`, `UPDATE`, `DELETE`, applicable DDL | `int`       |
| `executeBatch()`  | "Execute all my accumulated commands."        | Multiple commands                            | `int[]`     |

---

# Let's Use One Database Example

Suppose:

```text
student
--------------------------------
id     name      marks
101    Ravi      90
102    Raj       85
103    Amit      95
```

## Situation 1 — I want to read students

```sql
SELECT * FROM student;
```

Use:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

Because:

```text
SELECT → ResultSet
```

---

## Situation 2 — I want to update Ravi's marks

```sql
UPDATE student
SET marks = 95
WHERE id = 101;
```

Use:

```java
int count =
    st.executeUpdate(
        "UPDATE student " +
        "SET marks = 95 " +
        "WHERE id = 101"
    );
```

Because:

```text
UPDATE → update count
```

---

## Situation 3 — I don't know what type of result the SQL will produce

Use:

```java
boolean result =
    st.execute(sql);
```

Then:

```java
if (result) {
    ResultSet rs =
        st.getResultSet();
} else {
    int count =
        st.getUpdateCount();
}
```

---

## Situation 4 — I have many SQL commands

Use:

```java
st.addBatch(sql1);
st.addBatch(sql2);
st.addBatch(sql3);

int[] counts =
    st.executeBatch();
```

---

# The Big Picture

Everything now connects:

```text
                    Connection
                         │
                         │
                createStatement()
                         │
                         ▼
                    Statement
                         │
       ┌─────────────────┼──────────────────┐
       │                 │                  │
       ▼                 ▼                  ▼
executeQuery()     executeUpdate()      execute()
       │                 │                  │
       ▼                 ▼             ┌────┴────┐
 ResultSet              int            │         │
                                     true      false
                                       │         │
                                       ▼         ▼
                                  ResultSet   Update count
```

And for batches:

```text
                    Statement
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

---

# 🔥 The Most Common Confusion

Students often memorize:

```text
executeQuery()  → SELECT
executeUpdate() → INSERT/UPDATE/DELETE
```

That is useful, but understand **why**.

The real distinction is based on the **kind of result expected**.

### `executeQuery()`

Expected result:

```text
ResultSet
```

### `executeUpdate()`

Expected result:

```text
update count
```

### `execute()`

Can produce either kind of first result:

```text
ResultSet
       OR
update count/no result
```

### `executeBatch()`

Executes multiple accumulated commands:

```text
multiple update results/statuses
```

---

# 🧠 One-Minute Revision

Imagine the database asks:

### "Do you want rows?"

You say:

```java
executeQuery()
```

Database:

```text
Here's your ResultSet.
```

---

### "Do you want to modify data?"

You say:

```java
executeUpdate()
```

Database:

```text
Here's the update count.
```

---

### "You don't know what kind of result you'll get?"

You say:

```java
execute()
```

Database:

```text
true  → ResultSet
false → update count/no result
```

---

### "Do you have many commands?"

You say:

```java
addBatch()
addBatch()
addBatch()
executeBatch()
```

Database:

```text
Here's the array of batch update results/statuses.
```

---

# ⭐ Final TEACHME Formula

```text
                 STATEMENT
                     │
       "How do I send SQL?"
                     │
       ┌─────────────┼──────────────┐
       │             │              │
       ▼             ▼              ▼
  Want rows?    Want update?    Don't know /
       │             │          multiple results?
       ▼             ▼              ▼
executeQuery   executeUpdate    execute
       │             │              │
       ▼             ▼           boolean
 ResultSet         int              │
                                ┌────┴────┐
                              true      false
                                │          │
                           ResultSet   update count


        Have many commands?
                │
                ▼
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

## 🎯 Three things to permanently remember

**1. `Statement` is an interface.**

```java
Statement st = con.createStatement();
```

**2. Return types tell you the purpose.**

```text
execute()        → boolean
executeQuery()   → ResultSet
executeUpdate()  → int
executeBatch()   → int[]
```

**3. The core decision is:**

```text
Need rows?              → executeQuery()
Need update count?      → executeUpdate()
Need general execution? → execute()
Need many commands?     → executeBatch()
```

And in real applications, when SQL contains variable/user-supplied values, prefer **`PreparedStatement`** over string-concatenated `Statement` SQL.
