# 13. Batch Processing in Java — TEACHME

Let's learn **JDBC Batch Processing from zero**, as if we are sitting together in a classroom.

The goal is not just to memorize `addBatch()` and `executeBatch()`. You should understand **why they exist, what exactly happens, and when to use them**.

We will learn:

1. `addBatch()`
2. `executeBatch()`
3. Batch vs individual execution

---

# Part 1 — First understand the problem

Suppose your database has this table:

```sql
CREATE TABLE student (
    id INT,
    name VARCHAR(50),
    marks DOUBLE
);
```

Now imagine you have **1,000 students** to insert.

You could do this:

```java
ps.setInt(1, 101);
ps.setString(2, "A");
ps.setDouble(3, 80);
ps.executeUpdate();

ps.setInt(1, 102);
ps.setString(2, "B");
ps.setDouble(3, 85);
ps.executeUpdate();

ps.setInt(1, 103);
ps.setString(2, "C");
ps.setDouble(3, 90);
ps.executeUpdate();
```

This works.

But notice what we're doing:

```text
Student 101 → execute
Student 102 → execute
Student 103 → execute
Student 104 → execute
...
Student 1000 → execute
```

We're repeatedly asking JDBC/database to execute individual operations.

For large amounts of data, this can create unnecessary overhead.

That's where **Batch Processing** comes in.

---

# Part 2 — What is Batch Processing?

## Simple definition

> **Batch processing in JDBC means collecting multiple SQL operations and executing them together as a batch.**

Instead of:

```text
Execute
Execute
Execute
Execute
Execute
```

we can do:

```text
Collect
Collect
Collect
Collect
Collect
   ↓
Execute Batch
```

The two most important methods are:

```java
addBatch();
executeBatch();
```

Think of them like this:

```text
addBatch()
    ↓
"Put this operation into my basket."

executeBatch()
    ↓
"Now process everything in the basket."
```

---

# Part 3 — `addBatch()`

## 3.1 What does `addBatch()` mean?

`addBatch()` means:

> **Add an operation to the current batch without executing it immediately.**

This distinction is extremely important.

When you write:

```java
ps.addBatch();
```

you are **not executing the SQL**.

You're only collecting the operation.

---

# 3.2 `addBatch()` with `PreparedStatement`

This is the form you will commonly use for repeated operations.

Suppose we have:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name, marks) VALUES (?, ?, ?)"
    );
```

Now:

```java
ps.setInt(1, 101);
ps.setString(2, "A");
ps.setDouble(3, 80);
ps.addBatch();
```

What happened?

The parameter values:

```text
101
A
80
```

were added as **one batch entry**.

Now:

```java
ps.setInt(1, 102);
ps.setString(2, "B");
ps.setDouble(3, 85);
ps.addBatch();
```

Another entry is added.

Now the batch conceptually contains:

```text
BATCH
│
├── 101, A, 80
└── 102, B, 85
```

Then:

```java
ps.setInt(1, 103);
ps.setString(2, "C");
ps.setDouble(3, 90);
ps.addBatch();
```

Now:

```text
BATCH
│
├── 101, A, 80
├── 102, B, 85
└── 103, C, 90
```

Still, nothing has been batch-executed yet.

---

# Part 4 — The most important `addBatch()` doubt

You might ask:

> "If I change the parameters after calling `addBatch()`, will the previous batch entry also change?"

**No.**

Example:

```java
ps.setInt(1, 101);
ps.setString(2, "A");
ps.addBatch();

ps.setInt(1, 102);
ps.setString(2, "B");
ps.addBatch();
```

Think of it as:

```text
First addBatch()
        ↓
Save entry:
101, A

Change parameters

Second addBatch()
        ↓
Save entry:
102, B
```

So the batch is:

```text
┌───────────────┐
│ 101, A        │
├───────────────┤
│ 102, B        │
└───────────────┘
```

Each call to `addBatch()` creates another batch entry using the current parameter values.

---

# Part 5 — `addBatch()` with `Statement`

`Statement` works slightly differently.

You provide the SQL itself:

```java
Statement st = con.createStatement();

st.addBatch(
    "INSERT INTO student VALUES (101, 'A', 80)"
);

st.addBatch(
    "INSERT INTO student VALUES (102, 'B', 85)"
);

st.addBatch(
    "INSERT INTO student VALUES (103, 'C', 90)"
);
```

Conceptually:

```text
BATCH
│
├── INSERT 101
├── INSERT 102
└── INSERT 103
```

With `Statement`:

```java
addBatch(sql);
```

With `PreparedStatement`:

```java
setXXX(...);
addBatch();
```

That's a very important difference.

---

# Part 6 — `addBatch()` does NOT execute

Let's make this crystal clear.

### This:

```java
ps.addBatch();
```

means:

```text
Add operation to batch
```

### This:

```java
ps.executeBatch();
```

means:

```text
Execute collected operations
```

Therefore:

```text
addBatch() ≠ executeBatch()
```

---

# Part 7 — `executeBatch()`

Now we have several operations inside our batch.

How do we execute them?

With:

```java
executeBatch();
```

Example:

```java
ps.setInt(1, 101);
ps.setString(2, "A");
ps.setDouble(3, 80);
ps.addBatch();

ps.setInt(1, 102);
ps.setString(2, "B");
ps.setDouble(3, 85);
ps.addBatch();

ps.setInt(1, 103);
ps.setString(2, "C");
ps.setDouble(3, 90);
ps.addBatch();

int[] result = ps.executeBatch();
```

The flow is:

```text
Set parameters
      ↓
addBatch()
      ↓
Set parameters
      ↓
addBatch()
      ↓
Set parameters
      ↓
addBatch()
      ↓
executeBatch()
      ↓
Execute batch
```

---

# Part 8 — What does `executeBatch()` return?

`executeBatch()` returns:

```java
int[]
```

Example:

```java
int[] result = ps.executeBatch();
```

Why an array?

Because we executed **multiple operations**.

For example:

```text
Batch:

INSERT 101 → affected 1 row
INSERT 102 → affected 1 row
INSERT 103 → affected 1 row
```

The result can conceptually be:

```text
result
│
├── 1
├── 1
└── 1
```

So:

```java
result[0]
```

corresponds to the first batch command,

```java
result[1]
```

to the second,

and so on.

---

# Part 9 — What do the returned numbers mean?

For a normal update operation:

```text
1
```

usually means:

> One row was affected.

For example:

```sql
UPDATE student SET marks = 90 WHERE id = 101;
```

might return:

```text
1
```

because one row was updated.

But JDBC also defines special values.

### `Statement.SUCCESS_NO_INFO`

This means:

> The command succeeded, but the number of affected rows is not known.

### `Statement.EXECUTE_FAILED`

This indicates that execution of that batch command failed in cases where the driver reports the failure this way.

So don't assume:

```java
result[i] == 1
```

for every successful batch operation.

---

# Part 10 — `executeBatch()` does NOT mean `commit()`

This is one of the biggest JDBC doubts.

Suppose:

```java
ps.executeBatch();
```

Does this mean the changes are committed?

**No.**

`executeBatch()` means:

> Execute the batch.

`commit()` means:

> Commit the current transaction.

They are different operations.

```text
executeBatch()
      ↓
Execute SQL operations

commit()
      ↓
Commit transaction
```

For example:

```java
con.setAutoCommit(false);

ps.executeBatch();

con.commit();
```

Here both are being used.

---

# Part 11 — Batch and Transaction are different

This is worth memorizing:

> **Batch = grouping operations for execution.**

> **Transaction = grouping database changes for commit/rollback.**

Therefore:

```text
BATCH ≠ TRANSACTION
```

But they can be combined.

Example:

```java
con.setAutoCommit(false);

try {

    ps.executeBatch();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

Conceptually:

```text
              BATCH
                ↓
       Multiple SQL operations
                ↓
          executeBatch()
                ↓
           Transaction
             /     \
            /       \
       Success     Failure
          ↓           ↓
      commit()    rollback()
```

---

# Part 12 — Batch vs Individual Execution

Now let's compare the two approaches.

---

## Individual execution

Suppose we have 1,000 students.

```java
for (Student s : students) {

    ps.setInt(1, s.getId());
    ps.setString(2, s.getName());
    ps.setDouble(3, s.getMarks());

    ps.executeUpdate();
}
```

Notice:

```text
Student 1
   ↓
executeUpdate()

Student 2
   ↓
executeUpdate()

Student 3
   ↓
executeUpdate()

...
```

Every operation is executed individually.

---

# Part 13 — Batch execution

Instead:

```java
for (Student s : students) {

    ps.setInt(1, s.getId());
    ps.setString(2, s.getName());
    ps.setDouble(3, s.getMarks());

    ps.addBatch();
}

ps.executeBatch();
```

Now:

```text
Student 1 ──┐
Student 2 ──┤
Student 3 ──┤
Student 4 ──┤
Student 5 ──┤
             ↓
        executeBatch()
             ↓
          Database
```

This can reduce repeated communication and execution overhead.

---

# Part 14 — Why is batch processing generally faster?

Imagine you repeatedly communicate with the database.

Individual execution can involve repeated processing such as:

```text
Java
 ↓
JDBC Driver
 ↓
Database
 ↓
Response
```

Then again:

```text
Java
 ↓
JDBC Driver
 ↓
Database
 ↓
Response
```

And again.

For thousands of operations, this repeated overhead can become significant.

Batch processing allows multiple operations to be handled as a batch:

```text
Java
 ↓
JDBC Driver
 ↓
Batch of operations
 ↓
Database
```

The exact performance improvement depends on the JDBC driver, database, network, SQL, batch size, and configuration.

So remember:

> **Batch processing can improve performance; it does not guarantee a specific performance improvement.**

---

# Part 15 — Does batch mean "one SQL statement"?

**No.**

Suppose:

```java
st.addBatch("INSERT ...");
st.addBatch("UPDATE ...");
st.addBatch("DELETE ...");
```

The batch contains:

```text
Batch
│
├── INSERT
├── UPDATE
└── DELETE
```

These are still separate SQL commands.

JDBC batching does not simply mean:

> "Combine everything into one SQL string."

The driver and database determine how the batch is actually sent and processed.

---

# Part 16 — Does batch guarantee all-or-nothing?

**No.**

Another common misconception is:

> "If I use `executeBatch()`, either every command succeeds or everything is automatically rolled back."

That's not what batching means.

If you require all changes to be treated as one transaction, use transaction management:

```java
con.setAutoCommit(false);
```

Then:

```java
try {

    ps.executeBatch();
    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

So:

```text
Batch
    ↓
Controls grouped execution

Transaction
    ↓
Controls commit/rollback
```

---

# Part 17 — Complete Teacher Example

Let's build a complete example.

Suppose we want to insert three students.

### Step 1 — Prepare SQL

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name, marks) VALUES (?, ?, ?)"
    );
```

The SQL has three parameters:

```text
?
?
?
```

---

### Step 2 — First student

```java
ps.setInt(1, 101);
ps.setString(2, "A");
ps.setDouble(3, 80);

ps.addBatch();
```

Batch:

```text
101, A, 80
```

---

### Step 3 — Second student

```java
ps.setInt(1, 102);
ps.setString(2, "B");
ps.setDouble(3, 85);

ps.addBatch();
```

Batch:

```text
101, A, 80
102, B, 85
```

---

### Step 4 — Third student

```java
ps.setInt(1, 103);
ps.setString(2, "C");
ps.setDouble(3, 90);

ps.addBatch();
```

Batch:

```text
101, A, 80
102, B, 85
103, C, 90
```

---

### Step 5 — Execute

```java
int[] result = ps.executeBatch();
```

Now the batch is executed.

---

# Part 18 — Complete Program

```java
import java.sql.*;

public class BatchDemo {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/college";
        String username = "root";
        String password = "root";

        try (Connection con =
                 DriverManager.getConnection(
                     url, username, password);

             PreparedStatement ps =
                 con.prepareStatement(
                     "INSERT INTO student" +
                     "(id, name, marks) VALUES (?, ?, ?)")) {

            con.setAutoCommit(false);

            ps.setInt(1, 101);
            ps.setString(2, "A");
            ps.setDouble(3, 80);
            ps.addBatch();

            ps.setInt(1, 102);
            ps.setString(2, "B");
            ps.setDouble(3, 85);
            ps.addBatch();

            ps.setInt(1, 103);
            ps.setString(2, "C");
            ps.setDouble(3, 90);
            ps.addBatch();

            int[] result = ps.executeBatch();

            con.commit();

            System.out.println("Batch executed successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

The central part is:

```java
ps.setInt(...);
ps.setString(...);
ps.setDouble(...);
ps.addBatch();
```

repeated for every record, followed by:

```java
ps.executeBatch();
```

and, because manual transaction management is being used:

```java
con.commit();
```

---

# Part 19 — Individual vs Batch: Visual Comparison

## Individual

```text
Student 1
   ↓
executeUpdate()
   ↓
Database

Student 2
   ↓
executeUpdate()
   ↓
Database

Student 3
   ↓
executeUpdate()
   ↓
Database
```

---

## Batch

```text
Student 1 ──┐
Student 2 ──┤
Student 3 ──┤
Student 4 ──┤
Student 5 ──┘
      ↓
 addBatch()
      ↓
executeBatch()
      ↓
Database
```

---

# Part 20 — When should I use Batch Processing?

Batch processing is particularly useful when you have many operations such as:

### Bulk INSERT

```text
Import 100,000 students
```

### Bulk UPDATE

```text
Update marks for thousands of students
```

### Bulk DELETE

```text
Delete thousands of old records
```

### Data migration

```text
Database A
    ↓
Java
    ↓
Database B
```

### File/database import

```text
CSV
 ↓
Java
 ↓
JDBC Batch
 ↓
Database
```

---

# Part 21 — `Statement` vs `PreparedStatement` in Batch

## Statement

```java
Statement st = con.createStatement();

st.addBatch(
    "INSERT INTO student VALUES (101, 'A', 80)"
);

st.addBatch(
    "INSERT INTO student VALUES (102, 'B', 85)"
);

st.executeBatch();
```

Here:

```java
addBatch(sql);
```

is used.

---

## PreparedStatement

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student VALUES (?, ?, ?)"
    );

ps.setInt(1, 101);
ps.setString(2, "A");
ps.setDouble(3, 80);
ps.addBatch();

ps.setInt(1, 102);
ps.setString(2, "B");
ps.setDouble(3, 85);
ps.addBatch();

ps.executeBatch();
```

Here:

```java
addBatch();
```

is used after setting the parameters.

For repeated operations with changing values, `PreparedStatement` is generally preferable because it provides parameter binding and avoids constructing SQL by string concatenation.

---

# Part 22 — Very Important: Batch ≠ SQL Injection Protection

Suppose someone writes:

```java
st.addBatch(
    "INSERT INTO student VALUES (" +
    id + ", '" + name + "')"
);
```

The fact that this is a batch does **not** automatically make the SQL safe.

Instead:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name) VALUES (?, ?)"
    );

ps.setInt(1, id);
ps.setString(2, name);

ps.addBatch();
```

Here parameter binding is being used.

So:

```text
Batch Processing
      ↓
How operations are grouped

PreparedStatement
      ↓
How values are parameterized
```

Different concepts.

---

# Part 23 — Batch Size

Suppose you have:

```text
1,000,000 records
```

You don't necessarily need to put all one million into one batch.

You can divide them:

```text
1,000 records
     ↓
Batch 1

1,000 records
     ↓
Batch 2

1,000 records
     ↓
Batch 3

...
```

For example:

```java
int batchSize = 1000;

for (int i = 0; i < students.size(); i++) {

    Student s = students.get(i);

    ps.setInt(1, s.getId());
    ps.setString(2, s.getName());
    ps.setDouble(3, s.getMarks());

    ps.addBatch();

    if ((i + 1) % batchSize == 0) {
        ps.executeBatch();
    }
}

ps.executeBatch();
```

Why do this?

Because very large batches can consume more resources, and practical limits depend on the driver and database.

There is no universal perfect batch size.

---

# Part 24 — Three Things You Must Never Confuse

### 1. `addBatch()`

```text
Collect
```

### 2. `executeBatch()`

```text
Execute collected operations
```

### 3. `commit()`

```text
Commit transaction
```

So:

```text
addBatch()
     ↓
COLLECT

executeBatch()
     ↓
EXECUTE

commit()
     ↓
COMMIT
```

---

# Part 25 — What if I don't call `executeBatch()`?

Suppose:

```java
ps.addBatch();
ps.addBatch();
ps.addBatch();
```

and then:

```java
ps.close();
```

You never called:

```java
ps.executeBatch();
```

Therefore, you should **not expect those batch entries to have been executed**.

The important execution method is:

```java
executeBatch();
```

---

# Part 26 — What if I call `executeBatch()` twice?

Suppose:

```java
ps.addBatch();
ps.addBatch();

ps.executeBatch();
```

Then you add more:

```java
ps.addBatch();
ps.addBatch();

ps.executeBatch();
```

The exact lifecycle/clearing behavior is governed by the JDBC API and driver, but the practical pattern is to treat each `executeBatch()` as processing the currently accumulated batch and then explicitly build the next batch.

For very clear code, use:

```java
ps.executeBatch();
ps.clearBatch();
```

when you intentionally want to ensure the batch is cleared before building another one.

---

# Part 27 — Teacher's Mental Model

Imagine a restaurant.

### `addBatch()`

You tell the waiter:

> "Add this order to the list."

```text
Order 1 → list
Order 2 → list
Order 3 → list
```

### `executeBatch()`

You tell the waiter:

> "Process all the orders."

```text
List
 ↓
Process orders
```

### `commit()`

You say:

> "Everything is confirmed."

### `rollback()`

You say:

> "Cancel the uncommitted transaction changes."

So:

```text
addBatch()  → Add to list
executeBatch() → Process list
commit() → Confirm transaction
rollback() → Undo uncommitted transaction changes
```

This mental model makes JDBC Batch much easier.

---

# 🔥 FINAL TEACHME SUMMARY

## `addBatch()`

**Purpose:**

```text
Collect multiple SQL operations
```

### Statement:

```java
st.addBatch(sql);
```

### PreparedStatement:

```java
ps.setInt(...);
ps.setString(...);
ps.addBatch();
```

**Important:**

```text
addBatch() DOES NOT execute SQL.
```

---

## `executeBatch()`

**Purpose:**

```text
Execute the operations collected in the batch.
```

Example:

```java
int[] result = ps.executeBatch();
```

Returns:

```text
int[]
```

containing update counts or JDBC-defined status values.

---

## Batch vs Individual

### Individual:

```java
ps.executeUpdate();
```

repeated many times.

### Batch:

```java
ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();
```

Batching can improve performance by reducing repeated overhead and is particularly useful for bulk database operations.

---

# 🧠 One-minute revision

```text
                 JDBC BATCH
                     │
          ┌──────────┴──────────┐
          ↓                     ↓
      addBatch()          executeBatch()
          │                     │
          ↓                     ↓
       COLLECT               EXECUTE
          │                     │
          └──────────┬──────────┘
                     ↓
                   int[]
```

And:

```text
Individual:
executeUpdate()
executeUpdate()
executeUpdate()

Batch:
addBatch()
addBatch()
addBatch()
    ↓
executeBatch()
```

And the **golden rule**:

> **`addBatch()` collects, `executeBatch()` executes, and `commit()` commits the transaction.**

That single sentence will eliminate most beginner confusion around JDBC Batch Processing.
