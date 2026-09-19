# 13. Batch Processing in Java — DEEPDIVE

Batch processing is a JDBC technique used when we need to execute **many SQL operations of the same or different forms efficiently**.

The three concepts we need to master are:

1. `addBatch()`
2. `executeBatch()`
3. Batch execution vs individual execution

---

# 1. `addBatch()`

## 1.1 What is `addBatch()`?

`addBatch()` is used to **add a SQL command or a set of parameter values to a batch**.

It does **not execute the operation immediately**.

Think:

```text
addBatch()
    ↓
"Keep this operation for later"
```

Then eventually:

```text
executeBatch()
    ↓
"Execute the collected operations"
```

---

# 1.2 `addBatch()` with `Statement`

With `Statement`, the SQL string itself is supplied to `addBatch()`.

### Syntax

```java
statement.addBatch(sql);
```

Example:

```java
Statement st = con.createStatement();

st.addBatch("INSERT INTO student VALUES (101, 'A')");
st.addBatch("INSERT INTO student VALUES (102, 'B')");
st.addBatch("INSERT INTO student VALUES (103, 'C')");
```

At this point:

```text
Database
   ↑
   │  Nothing has been batch-executed yet
   │
Java
   │
   └── Batch
       ├── INSERT 101
       ├── INSERT 102
       └── INSERT 103
```

The operations have been **added to the batch**.

---

# 1.3 `addBatch()` with `PreparedStatement`

This is extremely important.

With `PreparedStatement`, you normally don't pass SQL to `addBatch()`.

Instead:

1. Set parameters.
2. Call `addBatch()`.
3. Change parameters.
4. Call `addBatch()` again.
5. Finally call `executeBatch()`.

Example:

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
┌────────────────────┐
│ 101, "A"            │
├────────────────────┤
│ 102, "B"            │
├────────────────────┤
│ 103, "C"            │
└────────────────────┘
```

The SQL structure is reused, while different parameter values are added to the batch.

---

# 1.4 Does `addBatch()` execute SQL?

**No.**

This:

```java
ps.addBatch();
```

does not mean:

```text
Execute SQL now
```

It means:

```text
Add current parameter values to the batch
```

Actual batch execution happens with:

```java
ps.executeBatch();
```

---

# 1.5 What happens when parameters change?

Consider:

```java
ps.setInt(1, 101);
ps.setString(2, "A");
ps.addBatch();

ps.setInt(1, 102);
ps.setString(2, "B");
ps.addBatch();
```

A common beginner doubt is:

> "When I changed `101` to `102`, did the first batch entry also change?"

**No.**

Each call to `addBatch()` adds the current parameter values as a batch entry.

Conceptually:

```text
addBatch()
    ↓
101, A → stored as batch entry

change parameters

addBatch()
    ↓
102, B → another batch entry
```

So:

```text
Batch
├── 101, A
└── 102, B
```

---

# 1.6 Can `Statement` and `PreparedStatement` both use batching?

**Yes.**

### Statement

```java
st.addBatch("INSERT ...");
```

### PreparedStatement

```java
ps.setInt(...);
ps.setString(...);
ps.addBatch();
```

The major difference is how the batch entries are supplied.

---

# 1.7 `clearBatch()`

JDBC also provides:

```java
st.clearBatch();
```

This removes commands that were added to a `Statement`'s current batch.

It is useful when you have constructed a batch but decide not to execute those commands.

Conceptually:

```text
addBatch()
addBatch()
addBatch()
     ↓
clearBatch()
     ↓
Batch cleared
```

For `PreparedStatement`, you typically use:

```java
ps.clearBatch();
```

to clear accumulated batch entries.

---

# 1.8 Important distinction

Don't confuse:

```java
clearBatch()
```

with:

```java
rollback()
```

They do completely different things.

```text
clearBatch()
    ↓
Remove commands from JDBC batch

rollback()
    ↓
Roll back database transaction changes
```

---

# 2. `executeBatch()`

# 2.1 What is `executeBatch()`?

`executeBatch()` executes the commands that have been added to the batch.

### Syntax

```java
int[] result = statement.executeBatch();
```

or:

```java
int[] result = preparedStatement.executeBatch();
```

---

# 2.2 Complete Statement example

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

int[] counts = st.executeBatch();
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
Database executes batch commands
     ↓
int[] returned
```

---

# 2.3 Complete PreparedStatement example

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

int[] counts = ps.executeBatch();
```

---

# 2.4 What does `executeBatch()` return?

It returns:

```java
int[]
```

For example:

```java
int[] counts = ps.executeBatch();
```

Conceptually:

```text
counts
│
├── 1
├── 1
└── 1
```

A returned update count generally describes how many rows were affected by a batch command.

But JDBC also defines special values.

---

# 2.5 `Statement.SUCCESS_NO_INFO`

JDBC provides:

```java
Statement.SUCCESS_NO_INFO
```

which has the value:

```text
-2
```

It means:

> The command succeeded, but the number of affected rows is not available.

Therefore:

```java
int[] counts = ps.executeBatch();
```

doesn't necessarily produce only:

```text
1, 1, 1
```

You may encounter:

```text
1
1
-2
```

where `-2` represents `SUCCESS_NO_INFO`.

---

# 2.6 `Statement.EXECUTE_FAILED`

JDBC defines:

```java
Statement.EXECUTE_FAILED
```

with value:

```text
-3
```

It indicates that execution of a batch command failed in situations where the driver reports the failure using this value.

So the important constants are:

```java
Statement.SUCCESS_NO_INFO
Statement.EXECUTE_FAILED
```

Don't blindly assume:

```java
count == 1
```

for every successful batch entry.

---

# 2.7 Does `executeBatch()` mean `commit()`?

**Absolutely not.**

This is one of the most important JDBC distinctions.

```java
ps.executeBatch();
```

means:

> Execute the batch.

Whereas:

```java
con.commit();
```

means:

> Complete the current transaction.

For example:

```java
con.setAutoCommit(false);

ps.executeBatch();

con.commit();
```

Here:

```text
executeBatch()
    ↓
Execute SQL commands

commit()
    ↓
Complete transaction
```

Therefore:

```text
executeBatch() ≠ commit()
```

---

# 2.8 Batch processing and transactions

Batch processing and transaction management are separate concepts.

You can combine them:

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
                │
       ┌────────┼────────┐
       ↓        ↓        ↓
      SQL      SQL      SQL
       └────────┼────────┘
                ↓
         executeBatch()
                ↓
          Transaction
                ↓
          ┌─────┴─────┐
          ↓           ↓
       Success      Failure
          ↓           ↓
       commit()   rollback()
```

---

# 3. Batch vs Individual Execution

Now let's understand **why batching exists**.

Suppose we need to insert 5,000 records.

---

# 3.1 Individual execution

We can write:

```java
for (Student s : students) {

    ps.setInt(1, s.getId());
    ps.setString(2, s.getName());

    ps.executeUpdate();
}
```

Every iteration calls:

```java
executeUpdate()
```

Conceptually:

```text
Java
 │
 ├── executeUpdate() → Database
 │
 ├── executeUpdate() → Database
 │
 ├── executeUpdate() → Database
 │
 ├── executeUpdate() → Database
 │
 └── ...
```

There may be significant application/driver/database/network overhead repeated for each execution.

---

# 3.2 Batch execution

Instead:

```java
for (Student s : students) {

    ps.setInt(1, s.getId());
    ps.setString(2, s.getName());

    ps.addBatch();
}

ps.executeBatch();
```

Conceptually:

```text
Java
 │
 ├── Add entry
 ├── Add entry
 ├── Add entry
 ├── Add entry
 ├── ...
 │
 └── executeBatch()
           ↓
        Database
```

This can reduce communication and execution overhead.

---

# 3.3 Why can batching be faster?

Suppose individual execution requires repeated communication:

```text
Java → Driver → Network → Database
```

If you repeat that many times:

```text
Java → DB
Java → DB
Java → DB
Java → DB
Java → DB
...
```

there can be significant overhead.

Batching allows the driver/database to process multiple commands as a batch, potentially reducing that overhead:

```text
Java → DB
       ├── command 1
       ├── command 2
       ├── command 3
       ├── command 4
       └── ...
```

**Important:** The exact performance improvement depends on the JDBC driver, database, network, SQL statements, batch size, and configuration.

Batching is an optimization, not a guarantee of a specific speedup.

---

# 3.4 Does batching always mean one network packet?

**No.**

This is a common misconception.

Don't memorize:

> "Batch = exactly one network packet."

That's not guaranteed.

The JDBC driver decides how it communicates with the database, and the database/driver may internally split or optimize the batch.

The safe statement is:

> **Batching allows multiple operations to be submitted as a batch and can reduce communication/execution overhead.**

---

# 3.5 Does batching combine all SQL into one SQL statement?

Not necessarily.

For example:

```java
st.addBatch("INSERT ...");
st.addBatch("UPDATE ...");
st.addBatch("DELETE ...");
```

These remain separate commands in the batch.

```text
Batch
├── INSERT
├── UPDATE
└── DELETE
```

A JDBC batch is not automatically transformed into:

```sql
INSERT ...;
UPDATE ...;
DELETE ...;
```

as one SQL statement.

The driver/database determines how the batch is actually sent and processed.

---

# 3.6 Does batch mean all operations succeed?

**No.**

Batching itself does not automatically mean:

```text
All succeed
OR
All rollback
```

That is **transaction behavior**, not the definition of batching.

If you need all operations to succeed or all database changes to be rolled back, you generally combine batching with explicit transaction management:

```java
con.setAutoCommit(false);
```

then:

```java
executeBatch();
```

and:

```java
commit();
```

or:

```java
rollback();
```

---

# 3.7 Batch ≠ Transaction

This deserves special attention.

### Batch

Answers:

> **How are multiple commands grouped for execution?**

```text
addBatch()
     ↓
executeBatch()
```

### Transaction

Answers:

> **How are database changes grouped for commit/rollback?**

```text
setAutoCommit(false)
     ↓
commit() / rollback()
```

Therefore:

```text
BATCH ≠ TRANSACTION
```

But they can work together.

---

# 3.8 Batch + Transaction Example

```java
con.setAutoCommit(false);

try {

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

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

The responsibilities are:

```text
addBatch()
    ↓
Collect operations

executeBatch()
    ↓
Execute collected operations

commit()
    ↓
Complete transaction

rollback()
    ↓
Abandon uncommitted transaction changes
```

---

# 4. Individual Execution vs Batch Execution

| Feature                          | Individual execution               | Batch execution       |
| -------------------------------- | ---------------------------------- | --------------------- |
| Main method                      | `executeUpdate()`                  | `executeBatch()`      |
| Collection step                  | Not required                       | `addBatch()`          |
| Multiple operations              | Executed separately                | Submitted as a batch  |
| Communication overhead           | Potentially higher                 | Can be lower          |
| Bulk operations                  | Less efficient in many cases       | Usually better suited |
| Return value                     | Individual update count            | `int[]`               |
| Automatically transactional?     | ❌                                  | ❌                     |
| Can use transactions?            | ✅                                  | ✅                     |
| Can use `PreparedStatement`?     | ✅                                  | ✅                     |
| Useful for thousands of records? | Possible, but often less efficient | ✅                     |

---

# 5. `Statement` Batch vs `PreparedStatement` Batch

This is another important distinction.

## Statement

```java
Statement st = con.createStatement();

st.addBatch(
    "INSERT INTO student VALUES (101, 'A')"
);

st.addBatch(
    "INSERT INTO student VALUES (102, 'B')"
);

st.executeBatch();
```

Here each batch entry is an SQL string.

---

## PreparedStatement

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

ps.executeBatch();
```

Here:

```text
SQL template
     +
different parameter values
     ↓
batch entries
```

For repeated operations with changing values, `PreparedStatement` is generally the preferred approach.

---

# 6. Batch Processing and SQL Injection

Batch processing itself does **not** automatically prevent SQL injection.

Consider:

```java
st.addBatch(
    "INSERT INTO student VALUES (" +
    id + ", '" + name + "')"
);
```

Building SQL through string concatenation can still create SQL injection risks.

Prefer:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name) VALUES (?, ?)"
    );
```

Then:

```java
ps.setInt(1, id);
ps.setString(2, name);
ps.addBatch();
```

So:

```text
Batching → performance/execution technique
PreparedStatement → parameter binding / safer SQL construction
```

They solve different problems.

---

# 7. Batch Size

Suppose you have:

```text
1,000,000 records
```

You don't necessarily want to place all one million entries into a single batch.

You can process chunks:

```text
Records
│
├── Batch 1 → 1–1,000
├── Batch 2 → 1,001–2,000
├── Batch 3 → 2,001–3,000
└── ...
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

The appropriate batch size depends on:

* database
* JDBC driver
* network
* SQL complexity
* memory
* transaction size
* application requirements

There is no universal "best batch size."

---

# 8. Common Mistakes

## Mistake 1

```java
ps.addBatch();
ps.addBatch();
ps.addBatch();
```

and then expecting execution.

❌ Wrong.

Need:

```java
ps.executeBatch();
```

---

## Mistake 2

Thinking:

```java
executeBatch()
```

automatically commits.

❌ Wrong.

You may still need:

```java
con.commit();
```

when manual transaction management is being used.

---

## Mistake 3

Thinking:

```java
addBatch()
```

executes SQL.

❌ Wrong.

It collects the operation.

---

## Mistake 4

Thinking:

```text
Batch = Transaction
```

❌ Wrong.

They are separate concepts.

---

## Mistake 5

Thinking:

```text
Batch = one SQL statement
```

❌ Wrong.

A batch can contain multiple commands.

---

## Mistake 6

Thinking batching automatically prevents SQL injection.

❌ Wrong.

Use parameterized `PreparedStatement`s for SQL values.

---

# 9. Complete Realistic Example

```java
Connection con = null;
PreparedStatement ps = null;

try {

    con = DriverManager.getConnection(
        url,
        username,
        password
    );

    con.setAutoCommit(false);

    ps = con.prepareStatement(
        "INSERT INTO student(id, name, marks) " +
        "VALUES (?, ?, ?)"
    );

    for (int i = 1; i <= 1000; i++) {

        ps.setInt(1, i);
        ps.setString(2, "Student" + i);
        ps.setDouble(3, 80.0);

        ps.addBatch();
    }

    int[] counts = ps.executeBatch();

    con.commit();

} catch (SQLException e) {

    if (con != null) {
        try {
            con.rollback();
        } catch (SQLException rollbackException) {
            rollbackException.printStackTrace();
        }
    }

    e.printStackTrace();

} finally {

    if (ps != null) {
        try {
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    if (con != null) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

The important sequence is:

```text
Connection
    ↓
setAutoCommit(false)
    ↓
Prepare SQL
    ↓
Set parameters
    ↓
addBatch()
    ↓
Set parameters
    ↓
addBatch()
    ↓
...
    ↓
executeBatch()
    ↓
commit()
```

If something goes wrong:

```text
executeBatch()
     ↓
SQLException
     ↓
rollback()
```

---

# 10. Deep Conceptual Comparison

```text
                JDBC BULK PROCESSING
                        │
              ┌─────────┴─────────┐
              ↓                   ↓
        INDIVIDUAL             BATCH
        EXECUTION              EXECUTION
              │                   │
              ↓                   ↓
      executeUpdate()       addBatch()
                                  │
                                  ↓
                           executeBatch()
                                  │
                                  ↓
                               int[]
```

And transaction control is separate:

```text
                TRANSACTION
                     │
            setAutoCommit(false)
                     │
              ┌──────┴──────┐
              ↓             ↓
           SUCCESS       FAILURE
              ↓             ↓
           commit()     rollback()
```

Together:

```text
Batch Processing
      +
Transaction Management
      ↓
Efficient + Controlled bulk database work
```

---

# 🔥 DEEPDIVE MASTER RULE

### `addBatch()`

> **Collect an SQL command or the current `PreparedStatement` parameter values into the batch.**

### `executeBatch()`

> **Execute the commands currently in the batch and return an `int[]` containing update counts or JDBC-defined status values.**

### Individual execution

```java
ps.executeUpdate();
```

executes each operation separately.

### Batch execution

```java
ps.addBatch();
...
ps.executeBatch();
```

collects multiple operations and executes them as a batch, potentially reducing overhead.

### Most important distinction

```text
addBatch()
    ↓
WHAT TO EXECUTE

executeBatch()
    ↓
EXECUTE THE BATCH

commit()
    ↓
COMPLETE THE TRANSACTION

rollback()
    ↓
ABANDON UNCOMMITTED TRANSACTION CHANGES
```

And finally:

> **Batch processing is about efficiently executing multiple database commands; transactions are about controlling whether database changes are committed or rolled back. They are different concepts, but they are frequently used together.**
