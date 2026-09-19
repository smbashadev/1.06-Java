# 13. Batch Processing in Java — 3LEVEL

We will learn each concept at **3 levels**:

* 🟢 **LEVEL 1 — Beginner:** What is it?
* 🟡 **LEVEL 2 — Intermediate:** How does it work?
* 🔴 **LEVEL 3 — Advanced:** Important internal concepts, differences, traps, and interview points.

The three topics are:

```text
13. JDBC Batch Processing
│
├── addBatch()
├── executeBatch()
└── Batch vs Individual Execution
```

---

# 1. `addBatch()`

---

## 🟢 LEVEL 1 — Beginner

### What is `addBatch()`?

`addBatch()` is a JDBC method used to **add an SQL operation to a batch without executing it immediately**.

Think of a batch as a basket.

```text
Basket
│
├── Operation 1
├── Operation 2
├── Operation 3
└── Operation 4
```

`addBatch()` means:

> **Put one operation into the basket.**

Example:

```java
Statement st = con.createStatement();

st.addBatch("INSERT INTO student VALUES (101, 'A')");
st.addBatch("INSERT INTO student VALUES (102, 'B')");
st.addBatch("INSERT INTO student VALUES (103, 'C')");
```

At this point, the SQL operations have been **collected**.

They haven't been batch-executed yet.

---

## 🟢 Simple flow

```text
addBatch()
    ↓
Add operation
    ↓
addBatch()
    ↓
Add operation
    ↓
addBatch()
    ↓
Add operation
```

Eventually:

```java
st.executeBatch();
```

executes them.

---

# 🟡 LEVEL 2 — Intermediate

There are two common forms of `addBatch()`.

---

## A. `Statement.addBatch(String sql)`

With `Statement`, you provide the SQL string.

```java
Statement st = con.createStatement();

st.addBatch(
    "INSERT INTO student VALUES (101, 'A')"
);

st.addBatch(
    "INSERT INTO student VALUES (102, 'B')"
);

st.addBatch(
    "UPDATE student SET name='C' WHERE id=103"
);
```

Conceptually:

```text
Batch
│
├── INSERT 101
├── INSERT 102
└── UPDATE 103
```

---

## B. `PreparedStatement.addBatch()`

With `PreparedStatement`, you first set parameter values and then call `addBatch()`.

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name) VALUES (?, ?)"
    );

ps.setInt(1, 101);
ps.setString(2, "A");
ps.addBatch();

ps.setInt(1, 102);
ps.setString(2, "B");
ps.addBatch();

ps.setInt(1, 103);
ps.setString(2, "C");
ps.addBatch();
```

Conceptually:

```text
SQL template:
INSERT INTO student(id, name) VALUES (?, ?)

Batch:
│
├── 101, A
├── 102, B
└── 103, C
```

This is an extremely common JDBC batch pattern.

---

## 🟡 Important difference

### Statement

```java
st.addBatch(sql);
```

### PreparedStatement

```java
ps.setInt(...);
ps.setString(...);
ps.addBatch();
```

Remember:

```text
Statement
    ↓
SQL supplied to addBatch()

PreparedStatement
    ↓
Parameters supplied
    ↓
addBatch()
```

---

# 🔴 LEVEL 3 — Advanced

## Does `addBatch()` execute SQL?

**No.**

This:

```java
ps.addBatch();
```

means:

```text
Collect current parameter values
```

not:

```text
Execute SQL
```

Actual execution is performed using:

```java
ps.executeBatch();
```

Therefore:

```text
addBatch()       → COLLECT
executeBatch()   → EXECUTE
```

---

## What happens when parameters change?

Consider:

```java
ps.setInt(1, 101);
ps.setString(2, "A");
ps.addBatch();

ps.setInt(1, 102);
ps.setString(2, "B");
ps.addBatch();
```

The second parameter assignment does not mean:

```text
101 → 102
```

inside the first batch entry.

Instead, you conceptually have:

```text
Batch
├── 101, A
└── 102, B
```

Each `addBatch()` contributes another parameterized operation.

---

## `clearBatch()`

JDBC also provides:

```java
ps.clearBatch();
```

It clears the commands accumulated in the batch.

Don't confuse:

```java
clearBatch();
```

with:

```java
rollback();
```

They solve different problems.

```text
clearBatch()
    ↓
Remove accumulated batch commands

rollback()
    ↓
Roll back uncommitted database changes
```

---

# 2. `executeBatch()`

---

## 🟢 LEVEL 1 — Beginner

### What is `executeBatch()`?

`executeBatch()` executes the operations that have previously been added to the batch.

Example:

```java
ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();
```

Think:

```text
addBatch()
    ↓
addBatch()
    ↓
addBatch()
    ↓
executeBatch()
    ↓
Execute all collected operations
```

---

# 🟡 LEVEL 2 — Intermediate

`executeBatch()` returns an array:

```java
int[] result = ps.executeBatch();
```

Why an array?

Because multiple operations were executed.

For example:

```text
Batch
│
├── INSERT → 1 row affected
├── INSERT → 1 row affected
└── UPDATE → 2 rows affected
```

The result could conceptually be:

```text
result
│
├── 1
├── 1
└── 2
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

and so forth.

---

## 🟡 Example

```java
PreparedStatement ps =
    con.prepareStatement(
        "UPDATE student SET marks=? WHERE id=?"
    );

ps.setDouble(1, 90);
ps.setInt(2, 101);
ps.addBatch();

ps.setDouble(1, 85);
ps.setInt(2, 102);
ps.addBatch();

int[] result = ps.executeBatch();
```

Conceptually:

```text
Batch
│
├── id 101 → marks 90
└── id 102 → marks 85
```

Then:

```text
executeBatch()
      ↓
Database
      ↓
int[]
```

---

# 🔴 LEVEL 3 — Advanced

## `executeBatch()` does NOT mean `commit()`

This is a critical distinction.

```java
ps.executeBatch();
```

means:

> Execute the batch.

It does **not** necessarily mean:

> Commit the transaction.

If you are manually managing the transaction:

```java
con.setAutoCommit(false);

ps.executeBatch();

con.commit();
```

There are two different operations:

```text
executeBatch()
      ↓
Execute SQL

commit()
      ↓
Commit transaction
```

Therefore:

> **Batch ≠ Transaction**

---

## Batch + Transaction

They can be combined:

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
             Batch
               ↓
        executeBatch()
               ↓
          Transaction
          /         \
         ↓           ↓
      Success      Failure
         ↓           ↓
      commit()   rollback()
```

---

## What does `executeBatch()` return?

Usually you deal with:

```java
int[]
```

JDBC also defines special status values such as:

```java
Statement.SUCCESS_NO_INFO
Statement.EXECUTE_FAILED
```

`SUCCESS_NO_INFO` means the command succeeded but the affected-row count isn't available.

`EXECUTE_FAILED` represents a failed batch command when reported that way by the JDBC driver/API behavior.

Therefore, don't blindly assume:

```java
result[i] == 1
```

for every batch entry.

---

# 3. Batch vs Individual Execution

This is the most important practical comparison.

---

# 🟢 LEVEL 1 — Beginner

## Individual execution

Suppose we have three students:

```java
ps.setInt(1, 101);
ps.setString(2, "A");
ps.executeUpdate();

ps.setInt(1, 102);
ps.setString(2, "B");
ps.executeUpdate();

ps.setInt(1, 103);
ps.setString(2, "C");
ps.executeUpdate();
```

Every operation is executed immediately through `executeUpdate()`.

```text
Student 101
    ↓
executeUpdate()

Student 102
    ↓
executeUpdate()

Student 103
    ↓
executeUpdate()
```

---

## Batch execution

Instead:

```java
ps.setInt(1, 101);
ps.setString(2, "A");
ps.addBatch();

ps.setInt(1, 102);
ps.setString(2, "B");
ps.addBatch();

ps.setInt(1, 103);
ps.setString(2, "C");
ps.addBatch();

ps.executeBatch();
```

Now:

```text
Student 101 ──┐
Student 102 ──┤
Student 103 ──┘
       ↓
 executeBatch()
       ↓
   Database
```

---

# 🟡 LEVEL 2 — Intermediate

Why use batch processing?

Because when you have a large number of operations:

```text
10 operations
100 operations
1,000 operations
100,000 operations
```

executing every operation individually can involve repeated overhead.

Batch processing allows multiple operations to be submitted as a batch.

This can reduce communication and execution overhead, depending on:

* JDBC driver
* database
* network
* SQL statements
* batch size
* configuration

Therefore:

> **Batch processing is generally useful for bulk operations.**

---

# 🔴 LEVEL 3 — Advanced

## Individual execution

```java
for (...) {
    ps.executeUpdate();
}
```

Conceptually:

```text
Java
 ↓
JDBC Driver
 ↓
Database

Java
 ↓
JDBC Driver
 ↓
Database

Java
 ↓
JDBC Driver
 ↓
Database
```

Repeated operations can introduce significant overhead.

---

## Batch execution

```java
for (...) {
    ps.addBatch();
}

ps.executeBatch();
```

Conceptually:

```text
Java
 ↓
JDBC Driver
 ↓
Batch
 ↓
Database
```

The exact wire-level behavior is **driver-specific**.

Therefore, don't memorize:

> "A batch always means one network packet."

That's not guaranteed.

The safer statement is:

> **Batching allows multiple operations to be processed as a batch and can reduce overhead.**

---

# 4. Batch Does NOT Mean One SQL Statement

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

It does not necessarily become:

```sql
INSERT ...;
UPDATE ...;
DELETE ...;
```

as one SQL statement.

The JDBC driver and database determine how the batch is actually communicated and processed.

---

# 5. Batch Does NOT Automatically Mean All-or-Nothing

Another major doubt:

> "If I use `executeBatch()`, will everything automatically roll back if one command fails?"

**Not simply because you used batching.**

Batching and transactions are different.

If you want explicit transaction control:

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

Remember:

```text
Batch
    ↓
Groups operations for execution

Transaction
    ↓
Controls commit/rollback
```

---

# 6. Batch vs Individual — Complete Table

| Feature                            | Individual Execution         | Batch Execution     |
| ---------------------------------- | ---------------------------- | ------------------- |
| Main execution method              | `executeUpdate()`            | `executeBatch()`    |
| Collection required                | ❌                            | `addBatch()`        |
| Multiple operations grouped        | ❌                            | ✅                   |
| Return type                        | Individual count             | `int[]`             |
| Bulk operations                    | Possible                     | Well suited         |
| Repeated overhead                  | Potentially higher           | Can be reduced      |
| Transaction automatically created? | ❌                            | ❌                   |
| Can use transactions?              | ✅                            | ✅                   |
| Can use `PreparedStatement`?       | ✅                            | ✅                   |
| Useful for large data              | Less efficient in many cases | Generally preferred |

---

# 7. Complete 3-Level Example

Let's put everything together.

## 🟢 Level 1 — Basic idea

```java
ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();
```

Meaning:

```text
Collect
Collect
Collect
   ↓
Execute
```

---

## 🟡 Level 2 — Real code

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name) VALUES (?, ?)"
    );

ps.setInt(1, 101);
ps.setString(2, "A");
ps.addBatch();

ps.setInt(1, 102);
ps.setString(2, "B");
ps.addBatch();

ps.setInt(1, 103);
ps.setString(2, "C");
ps.addBatch();

int[] result = ps.executeBatch();
```

---

## 🔴 Level 3 — Production-style pattern

For large data, you may process the records in manageable batches:

```java
con.setAutoCommit(false);

try {

    int batchSize = 1000;

    for (int i = 0; i < students.size(); i++) {

        Student s = students.get(i);

        ps.setInt(1, s.getId());
        ps.setString(2, s.getName());

        ps.addBatch();

        if ((i + 1) % batchSize == 0) {
            ps.executeBatch();
        }
    }

    ps.executeBatch();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

The exact batch size should be chosen based on the database, JDBC driver, workload, memory, and transaction requirements.

---

# 8. `addBatch()` vs `executeBatch()` vs `executeUpdate()`

This comparison is extremely important.

| Method            | Meaning                                                |
| ----------------- | ------------------------------------------------------ |
| `addBatch()`      | Add an operation to the batch                          |
| `executeBatch()`  | Execute collected batch operations                     |
| `executeUpdate()` | Execute one update/insert/delete operation immediately |

Think:

```text
addBatch()
   ↓
WAIT

addBatch()
   ↓
WAIT

addBatch()
   ↓
WAIT

executeBatch()
   ↓
EXECUTE BATCH
```

Whereas:

```text
executeUpdate()
   ↓
EXECUTE NOW
```

---

# 9. Most Common Beginner Mistakes

### ❌ Mistake 1

```java
ps.addBatch();
```

and thinking the database has already been updated.

**Correction:**

```java
ps.executeBatch();
```

is needed to execute the batch.

---

### ❌ Mistake 2

Thinking:

```java
executeBatch()
```

means:

```java
commit()
```

Wrong.

```text
executeBatch() → execute
commit()       → commit
```

---

### ❌ Mistake 3

Thinking:

```text
Batch = Transaction
```

Wrong.

```text
Batch      → execution grouping
Transaction → commit/rollback grouping
```

---

### ❌ Mistake 4

Thinking:

```text
Batch = one SQL statement
```

Wrong.

A batch can contain multiple SQL commands.

---

### ❌ Mistake 5

Thinking batching itself prevents SQL injection.

Wrong.

For user-controlled values, use parameterized `PreparedStatement`s.

---

# 🔥 3LEVEL MASTER MAP

```text
                 JDBC BATCH PROCESSING
                         │
             ┌───────────┴───────────┐
             │                       │
             ↓                       ↓
        addBatch()             executeBatch()
             │                       │
             ↓                       ↓
          COLLECT                 EXECUTE
             │                       │
             └───────────┬───────────┘
                         ↓
                       int[]
```

And:

```text
          INDIVIDUAL
              │
              ↓
      executeUpdate()
              │
              ↓
         Execute now
```

versus:

```text
            BATCH
              │
       ┌──────┼──────┐
       ↓      ↓      ↓
     add    add     add
       └──────┼──────┘
              ↓
       executeBatch()
              ↓
        Execute batch
```

---

# 🧠 FINAL 3LEVEL MEMORY

### 🟢 Level 1 — Remember

```text
addBatch()       → Collect
executeBatch()   → Execute
```

### 🟡 Level 2 — Understand

```text
Individual:
executeUpdate()
executeUpdate()
executeUpdate()

Batch:
addBatch()
addBatch()
addBatch()
executeBatch()
```

Batching is useful for bulk operations and **can reduce repeated overhead**.

### 🔴 Level 3 — Never confuse

```text
addBatch()
     ↓
Collect operation

executeBatch()
     ↓
Execute batch

commit()
     ↓
Commit transaction

rollback()
     ↓
Rollback uncommitted transaction changes
```

**Golden rule:**

> **`addBatch()` collects, `executeBatch()` executes, and `commit()` commits. Batch processing and transaction management are separate concepts that can be used together.**
