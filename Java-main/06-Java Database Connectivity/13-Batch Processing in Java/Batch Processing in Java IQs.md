# 13. Batch Processing in Java — DOUBTKILLER

This section is designed specifically to eliminate the **confusing points, common mistakes, interview traps, and "why does this work?" questions** around JDBC batch processing.

We will completely understand:

```text
13. Batch Processing
│
├── addBatch()
├── executeBatch()
└── Batch vs Individual Execution
```

---

# PART 1 — `addBatch()`

## Doubt 1: What exactly is `addBatch()`?

`addBatch()` means:

> **Add an SQL operation to a batch for later execution.**

It does **not** execute the SQL immediately.

Think of it like collecting items into a box:

```text
addBatch()
    ↓
Put operation into box

addBatch()
    ↓
Put another operation into box

addBatch()
    ↓
Put another operation into box

executeBatch()
    ↓
Process the box
```

So:

```java
ps.addBatch();
```

means:

> "Remember this operation."

It does **not** mean:

> "Execute this operation now."

---

# Doubt 2: Does `addBatch()` send SQL to the database?

### Not as an execution request.

When you call:

```java
ps.addBatch();
```

you are adding the current operation to the JDBC batch.

Actual execution happens when:

```java
ps.executeBatch();
```

is called.

Therefore:

```text
addBatch()
    ↓
Collect

executeBatch()
    ↓
Execute
```

---

# Doubt 3: What is a "batch"?

A batch is simply a **group of commands/parameter sets that are intended to be executed together as a batch**.

For example:

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
```

Conceptually:

```text
BATCH
│
├── (101, A)
├── (102, B)
└── (103, C)
```

Nothing about the word "batch" means "transaction."

That's a separate concept.

---

# Doubt 4: How does `addBatch()` work with `Statement`?

With `Statement`, the SQL itself is supplied.

```java
Statement st = con.createStatement();

st.addBatch(
    "INSERT INTO student VALUES (101, 'A')"
);

st.addBatch(
    "INSERT INTO student VALUES (102, 'B')"
);

st.addBatch(
    "INSERT INTO student VALUES (103, 'C')"
);
```

Here:

```java
addBatch(String sql)
```

is used.

Conceptually:

```text
Batch
│
├── SQL #1
├── SQL #2
└── SQL #3
```

---

# Doubt 5: How does `addBatch()` work with `PreparedStatement`?

This is the more important pattern.

Suppose:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name) VALUES (?, ?)"
    );
```

Now:

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
```

The SQL template is:

```sql
INSERT INTO student(id, name) VALUES (?, ?)
```

The batch conceptually becomes:

```text
SQL template
        +
parameter values

┌───────────────┐
│ 101, A        │
├───────────────┤
│ 102, B        │
├───────────────┤
│ 103, C        │
└───────────────┘
```

---

# Doubt 6: Why don't I pass SQL to `PreparedStatement.addBatch()`?

Because the SQL was already supplied when the `PreparedStatement` was created:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name) VALUES (?, ?)"
    );
```

Therefore, with `PreparedStatement`, you normally do:

```java
ps.setInt(...);
ps.setString(...);
ps.addBatch();
```

while `Statement` uses:

```java
st.addBatch(sql);
```

Remember:

```text
Statement
    ↓
addBatch(sql)

PreparedStatement
    ↓
set parameters
    ↓
addBatch()
```

---

# Doubt 7: If I change the parameters, will the previous batch entry change?

### No.

Example:

```java
ps.setInt(1, 101);
ps.setString(2, "A");
ps.addBatch();

ps.setInt(1, 102);
ps.setString(2, "B");
ps.addBatch();
```

You should think of it as:

```text
First addBatch()
    ↓
Entry #1 = 101, A

Second addBatch()
    ↓
Entry #2 = 102, B
```

So:

```text
Batch
├── 101, A
└── 102, B
```

The second parameter assignment is used for the next batch entry.

---

# Doubt 8: What happens if I change parameters but DON'T call `addBatch()`?

Example:

```java
ps.setInt(1, 101);
ps.setString(2, "A");
ps.addBatch();

ps.setInt(1, 102);
ps.setString(2, "B");

// No addBatch()
```

The second set of values has **not been added as another batch entry**.

The batch still conceptually contains:

```text
101, A
```

not:

```text
101, A
102, B
```

This is why the pattern is:

```java
set parameters
addBatch
```

repeated for each record.

---

# Doubt 9: Can I call `addBatch()` without changing parameters?

Yes.

For example:

```java
ps.setInt(1, 101);
ps.setString(2, "A");

ps.addBatch();
ps.addBatch();
ps.addBatch();
```

You have asked JDBC to add the current parameter set repeatedly.

Whether doing this makes sense depends on what you intend to insert/update.

---

# Doubt 10: What does `clearBatch()` do?

It removes the accumulated commands from the batch.

```java
ps.clearBatch();
```

Think:

```text
Batch
├── Operation 1
├── Operation 2
└── Operation 3

clearBatch()
      ↓

Batch
└── Empty
```

Do not confuse this with:

```java
con.rollback();
```

because:

```text
clearBatch()
    ↓
Clear pending JDBC batch entries

rollback()
    ↓
Undo uncommitted database transaction changes
```

---

# PART 2 — `executeBatch()`

# Doubt 11: What exactly is `executeBatch()`?

`executeBatch()` means:

> **Execute the commands currently collected in the batch.**

Example:

```java
ps.addBatch();
ps.addBatch();
ps.addBatch();

ps.executeBatch();
```

Flow:

```text
addBatch()
     ↓
addBatch()
     ↓
addBatch()
     ↓
executeBatch()
     ↓
Execute batch
```

---

# Doubt 12: Does `executeBatch()` execute one SQL statement?

Not necessarily.

Suppose:

```java
st.addBatch("INSERT ...");
st.addBatch("UPDATE ...");
st.addBatch("DELETE ...");
```

The batch contains three commands:

```text
Batch
├── INSERT
├── UPDATE
└── DELETE
```

A JDBC batch is **not simply "one SQL statement."**

The driver/database determine how the batch is actually communicated and processed.

---

# Doubt 13: Does `executeBatch()` mean "execute everything in one network packet"?

### No.

This is a common misconception.

Don't memorize:

> Batch = exactly one network packet.

The actual communication is driver- and database-dependent.

The correct concept is:

> **Batching allows multiple operations to be submitted/processed as a batch and can reduce communication and execution overhead.**

---

# Doubt 14: What does `executeBatch()` return?

It returns:

```java
int[]
```

Example:

```java
int[] result = ps.executeBatch();
```

Why an array?

Because there can be multiple batch commands.

For example:

```text
Batch:
├── INSERT #1 → 1 row
├── INSERT #2 → 1 row
└── UPDATE #3 → 2 rows
```

The returned array could conceptually be:

```text
result
├── 1
├── 1
└── 2
```

The entries correspond to the batch commands.

---

# Doubt 15: Does every result value have to be `1`?

### No.

For an update, `1` may mean one row was affected.

But you could have:

```text
0
```

meaning no rows were affected by a particular update.

Or:

```text
2
```

meaning two rows were affected.

JDBC also defines special status values, including:

```java
Statement.SUCCESS_NO_INFO
```

and:

```java
Statement.EXECUTE_FAILED
```

So don't blindly write logic assuming:

```java
result[i] == 1
```

always means success.

---

# Doubt 16: What is `SUCCESS_NO_INFO`?

`Statement.SUCCESS_NO_INFO` indicates:

> The command succeeded, but the affected-row count is not available.

Its JDBC-defined value is:

```text
-2
```

So you might conceptually encounter:

```text
result:
1
1
-2
```

The `-2` does **not** mean failure.

It means:

```text
SUCCESS
but
NO UPDATE COUNT INFORMATION
```

---

# Doubt 17: What is `EXECUTE_FAILED`?

JDBC defines:

```java
Statement.EXECUTE_FAILED
```

with value:

```text
-3
```

It indicates that a batch command failed when the driver/API reports the failure using this status.

So:

```text
-2 → SUCCESS_NO_INFO
-3 → EXECUTE_FAILED
```

These are special JDBC constants.

---

# Doubt 18: Does `executeBatch()` automatically call `commit()`?

### No.

This is probably the **#1 batch-processing confusion**.

These are different:

```java
ps.executeBatch();
```

and:

```java
con.commit();
```

Think:

```text
executeBatch()
    ↓
Execute SQL operations

commit()
    ↓
Commit transaction
```

Therefore:

```text
executeBatch() ≠ commit()
```

---

# Doubt 19: If auto-commit is true, what happens?

A JDBC `Connection` normally starts with auto-commit enabled unless changed.

If:

```java
con.getAutoCommit()
```

is `true`, successful statement execution is generally committed automatically according to JDBC transaction semantics.

But don't conclude:

> "`executeBatch()` itself calls `commit()`."

That's incorrect.

The **transaction mode of the connection** determines when commits happen.

If you use:

```java
con.setAutoCommit(false);
```

then you explicitly control the transaction:

```java
con.setAutoCommit(false);

ps.executeBatch();

con.commit();
```

or:

```java
con.rollback();
```

---

# Doubt 20: Is Batch Processing the same as Transaction?

### Absolutely not.

This distinction should be permanently clear.

### Batch

Answers:

> **How can multiple operations be grouped for execution?**

```text
addBatch()
    ↓
executeBatch()
```

### Transaction

Answers:

> **How are database changes committed or rolled back?**

```text
setAutoCommit(false)
       ↓
commit() / rollback()
```

Therefore:

```text
BATCH ≠ TRANSACTION
```

---

# Doubt 21: Can batch and transaction be used together?

### Yes — very commonly.

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
        executeBatch()
               ↓
          TRANSACTION
          /         \
         ↓           ↓
      Success      Failure
         ↓           ↓
      commit()   rollback()
```

Batch controls the **execution grouping**.

Transaction controls the **commit/rollback boundary**.

---

# PART 3 — Batch vs Individual Execution

# Doubt 22: What is individual execution?

Individual execution means executing each operation separately.

Example:

```java
for (Student s : students) {

    ps.setInt(1, s.getId());
    ps.setString(2, s.getName());

    ps.executeUpdate();
}
```

If there are 1,000 students:

```text
executeUpdate()
executeUpdate()
executeUpdate()
...
1000 times
```

Each operation is individually executed.

---

# Doubt 23: What is batch execution?

Instead:

```java
for (Student s : students) {

    ps.setInt(1, s.getId());
    ps.setString(2, s.getName());

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
     ...    │
Student N ──┘
      ↓
executeBatch()
      ↓
Database
```

---

# Doubt 24: Why can batch be faster?

Individual execution can involve repeated overhead:

```text
Java
 ↓
JDBC Driver
 ↓
Database
 ↓
Response

Java
 ↓
JDBC Driver
 ↓
Database
 ↓
Response

Java
 ↓
JDBC Driver
 ↓
Database
 ↓
Response
```

With batching:

```text
Java
 ↓
JDBC Driver
 ↓
Batch of operations
 ↓
Database
```

The driver/database can optimize the handling of the batch, and repeated communication/execution overhead can be reduced.

But:

> **Batch processing does not guarantee a particular speed improvement.**

Actual performance depends on the JDBC driver, database, network, SQL, batch size, and configuration.

---

# Doubt 25: Does batch always use less memory?

### Not necessarily.

Batch entries have to be accumulated somewhere before execution.

A huge batch can consume more memory.

For example:

```text
10 records
    ↓
tiny batch

1,000 records
    ↓
larger batch

1,000,000 records
    ↓
potentially huge batch
```

Therefore, very large datasets are often processed in manageable chunks.

---

# Doubt 26: Should I put one million records into one batch?

Not necessarily.

A common approach is:

```text
1,000 records
    ↓
executeBatch()

next 1,000
    ↓
executeBatch()

next 1,000
    ↓
executeBatch()
```

Example:

```java
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
```

The ideal batch size is workload- and driver-dependent.

---

# Doubt 27: Does executing a batch automatically clear it?

The JDBC API defines batch execution behavior, but for portable, explicit code, don't build your design around assumptions about an implicit clearing lifecycle.

If you intentionally want to clear accumulated commands, use:

```java
ps.clearBatch();
```

This makes your intent explicit.

---

# Doubt 28: Can I use `PreparedStatement` with batch processing?

### Yes — and it is one of the most common approaches.

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name) VALUES (?, ?)"
    );

for (Student s : students) {

    ps.setInt(1, s.getId());
    ps.setString(2, s.getName());

    ps.addBatch();
}

ps.executeBatch();
```

This is excellent for repeated SQL operations with different values.

---

# Doubt 29: Can I use `Statement` with batch processing?

### Yes.

```java
Statement st = con.createStatement();

st.addBatch("INSERT ...");
st.addBatch("INSERT ...");
st.addBatch("UPDATE ...");

st.executeBatch();
```

So both support batch processing.

---

# Doubt 30: Which is usually preferable for repeated values?

For repeated operations where only parameter values change, `PreparedStatement` is generally preferable:

```java
PreparedStatement
       ↓
SQL template
       +
parameter values
       ↓
batch
```

rather than constructing SQL strings repeatedly.

It provides parameter binding and helps avoid SQL construction mistakes and SQL injection risks associated with concatenating untrusted values.

---

# Doubt 31: Does batch processing prevent SQL injection?

### No.

This:

```java
st.addBatch(
    "INSERT INTO student VALUES (" +
    id + ", '" + name + "')"
);
```

doesn't become safe merely because it uses `addBatch()`.

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

Here the values are parameterized.

Remember:

```text
Batch
   ↓
Execution technique

PreparedStatement
   ↓
Parameter binding
```

Different purposes.

---

# Doubt 32: Can one batch contain INSERT, UPDATE, and DELETE?

With `Statement`, yes.

For example:

```java
st.addBatch("INSERT ...");
st.addBatch("UPDATE ...");
st.addBatch("DELETE ...");
```

A batch can contain multiple commands.

However, when doing large-scale repeated operations of one SQL shape, `PreparedStatement` batching is often the clearer and more efficient design.

---

# Doubt 33: Is batch execution atomic?

### Don't equate batch with atomicity.

A batch is an execution mechanism, not a transaction definition.

If you need all operations to succeed or want explicit rollback behavior, use transaction management:

```java
con.setAutoCommit(false);

try {

    ps.executeBatch();
    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

Also remember that exact behavior when a batch command fails can depend on the JDBC driver/database and the transaction configuration.

---

# Doubt 34: What happens if one batch operation fails?

This is an advanced JDBC point.

A batch can fail with a:

```java
BatchUpdateException
```

Example:

```java
try {

    ps.executeBatch();

} catch (BatchUpdateException e) {

    int[] counts = e.getUpdateCounts();

    e.printStackTrace();
}
```

`BatchUpdateException` provides update counts for commands that the driver reports as having been processed before the failure, subject to JDBC driver behavior.

So don't assume:

> "If one command fails, every command definitely didn't execute."

Nor assume:

> "If one fails, all previous commands are automatically rolled back."

Those are different issues.

Transaction management determines rollback behavior.

---

# Doubt 35: What is `BatchUpdateException`?

It is a specialized `SQLException` used to report a failure during batch execution.

Example:

```java
try {
    ps.executeBatch();
} catch (BatchUpdateException e) {
    e.printStackTrace();
}
```

It can provide:

```java
e.getUpdateCounts()
```

which helps determine the update status reported for the batch commands.

---

# Doubt 36: What if I want all batch operations to succeed or none?

Use a transaction.

For example:

```java
con.setAutoCommit(false);

try {

    ps.executeBatch();

    con.commit();

} catch (BatchUpdateException e) {

    con.rollback();

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
Failure?
 ├── No → commit()
 └── Yes → rollback()
```

This gives you explicit transaction control.

---

# Doubt 37: Is `executeBatch()` equivalent to calling `executeUpdate()` many times?

### Conceptually, both ultimately cause multiple database operations to be executed.

But they are **not the same JDBC operation**.

Individual:

```java
ps.executeUpdate();
```

Batch:

```java
ps.addBatch();
...
ps.executeBatch();
```

Batching gives the JDBC driver/database an opportunity to optimize grouped execution and reduce overhead.

So:

```text
Same general goal:
Execute many database modifications

Different mechanism:
Individual execution ≠ batch execution
```

---

# Doubt 38: What is the biggest advantage of batch?

For many repeated database operations:

> **Potentially lower communication and execution overhead, making bulk processing more efficient.**

Especially:

```text
10,000 INSERTs
50,000 UPDATEs
100,000 DELETEs
```

Batch processing is often much more appropriate than executing every operation independently.

---

# Doubt 39: What is the biggest misconception about batch?

The biggest misconception is:

```text
Batch = Transaction
```

No.

Correct:

```text
Batch
    ↓
Groups operations for execution

Transaction
    ↓
Groups changes for commit/rollback
```

---

# PART 4 — Ultimate Comparison

| Question                          | `addBatch()`       | `executeBatch()` | Individual execution   |
| --------------------------------- | ------------------ | ---------------- | ---------------------- |
| What does it do?                  | Collects operation | Executes batch   | Executes one operation |
| Executes SQL immediately?         | ❌                  | ✅                | ✅                      |
| Used with `Statement`?            | ✅                  | ✅                | ✅                      |
| Used with `PreparedStatement`?    | ✅                  | ✅                | ✅                      |
| Returns update counts?            | ❌                  | `int[]`          | Individual count       |
| Main purpose                      | Build batch        | Run batch        | Run one operation      |
| Related to transaction?           | No                 | No directly      | No directly            |
| Can be combined with transaction? | ✅                  | ✅                | ✅                      |

---

# PART 5 — The Most Important Differences

## `addBatch()` vs `executeBatch()`

```text
addBatch()
    ↓
"Remember this."

executeBatch()
    ↓
"Execute what you remembered."
```

---

## `executeBatch()` vs `executeUpdate()`

```text
executeUpdate()
    ↓
One operation now

executeBatch()
    ↓
Multiple collected operations
```

---

## `executeBatch()` vs `commit()`

```text
executeBatch()
    ↓
Execute SQL

commit()
    ↓
Commit transaction
```

---

## `clearBatch()` vs `rollback()`

```text
clearBatch()
    ↓
Clear pending batch commands

rollback()
    ↓
Undo uncommitted transaction changes
```

---

# PART 6 — Interview Trap Questions

### Q1. Does `addBatch()` execute SQL?

**No.**

It adds an operation to the batch.

---

### Q2. Which method actually executes the batch?

```java
executeBatch()
```

---

### Q3. What is the return type?

```java
int[]
```

---

### Q4. Does `executeBatch()` automatically mean `commit()`?

**No.**

They are separate concepts.

---

### Q5. Is batch processing the same as transaction management?

**No.**

```text
Batch → execution grouping
Transaction → commit/rollback control
```

---

### Q6. Can `PreparedStatement` be used for batch processing?

**Yes.**

```java
ps.addBatch();
ps.executeBatch();
```

---

### Q7. Can `Statement` be used for batch processing?

**Yes.**

```java
st.addBatch(sql);
st.executeBatch();
```

---

### Q8. Does batch mean one network packet?

**No.**

The actual communication behavior is driver/database dependent.

---

### Q9. Does batch mean one SQL statement?

**No.**

A batch can contain multiple SQL commands.

---

### Q10. Does batch automatically provide rollback if one operation fails?

**No.**

Rollback is a transaction-management concept.

---

### Q11. What exception can indicate a batch execution failure?

```java
BatchUpdateException
```

---

### Q12. Does batching itself prevent SQL injection?

**No.**

Use parameterized `PreparedStatement`s.

---

# 🔥 DOUBTKILLER MASTER DIAGRAM

```text
                 JDBC BATCH PROCESSING
                         │
                         ↓
                  addBatch()
                         │
                "COLLECT THIS"
                         │
              ┌──────────┼──────────┐
              ↓          ↓          ↓
           Entry 1    Entry 2    Entry 3
              └──────────┼──────────┘
                         ↓
                  executeBatch()
                         │
                    "EXECUTE"
                         │
                         ↓
                       int[]
                         │
                         ↓
              Database operations
```

Transaction is separate:

```text
              TRANSACTION
                   │
          setAutoCommit(false)
                   │
            executeBatch()
                   │
             ┌─────┴─────┐
             ↓           ↓
          Success      Failure
             ↓           ↓
          commit()   rollback()
```

Individual execution:

```text
        INDIVIDUAL
             │
             ↓
     executeUpdate()
             │
             ↓
       Execute one
```

---

# 🧠 FINAL DOUBTKILLER RULES

Memorize these **10 rules**:

1. **`addBatch()` collects; it doesn't execute.**

2. **`executeBatch()` executes the collected batch.**

3. **`executeBatch()` returns `int[]`.**

4. **`executeBatch()` is not the same as `commit()`.**

5. **Batch processing is not transaction management.**

6. **`Statement` and `PreparedStatement` both support batching.**

7. **With `Statement`:**

   ```java
   st.addBatch(sql);
   ```

8. **With `PreparedStatement`:**

   ```java
   ps.setXXX(...);
   ps.addBatch();
   ```

9. **Batching can reduce repeated overhead, but the actual performance benefit depends on the driver, database, network, workload, and batch size.**

10. **For bulk operations with changing values, `PreparedStatement + addBatch() + executeBatch()` is the standard pattern.**

### The one sentence to remember forever:

> **`addBatch()` collects multiple operations, `executeBatch()` executes them as a batch, and `commit()` controls transaction completion — they are three different responsibilities.**
