# 13. Batch Processing in Java — ONEPAGE

Batch Processing is a JDBC technique used to **send multiple SQL operations together** rather than executing every operation separately.

It is especially useful when inserting, updating, or deleting **large numbers of records**.

---

# 1. `addBatch()`

## What is `addBatch()`?

`addBatch()` adds an SQL command or a prepared statement's parameter set to a **batch** instead of immediately executing it.

There are two commonly used forms.

### With `Statement`

```java
Statement st = con.createStatement();

st.addBatch("INSERT INTO student VALUES (1, 'A')");
st.addBatch("INSERT INTO student VALUES (2, 'B')");
st.addBatch("INSERT INTO student VALUES (3, 'C')");
```

The SQL statements are collected:

```text
Batch
│
├── INSERT student 1
├── INSERT student 2
└── INSERT student 3
```

They have been **added**, not yet batch-executed.

---

### With `PreparedStatement`

With `PreparedStatement`, you usually set parameter values and then call:

```java
ps.addBatch();
```

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "INSERT INTO student(id, name) VALUES (?, ?)"
    );

ps.setInt(1, 1);
ps.setString(2, "A");
ps.addBatch();

ps.setInt(1, 2);
ps.setString(2, "B");
ps.addBatch();

ps.setInt(1, 3);
ps.setString(2, "C");
ps.addBatch();
```

Conceptually:

```text
Parameters
   ↓
addBatch()
   ↓
Batch entry

Parameters
   ↓
addBatch()
   ↓
Batch entry

Parameters
   ↓
addBatch()
   ↓
Batch entry
```

---

## Important doubt: Does `addBatch()` execute SQL?

**No.**

```java
ps.addBatch();
```

means:

> Add the current parameter values to the batch.

It does **not** mean:

> Execute the SQL immediately.

Execution happens with:

```java
ps.executeBatch();
```

---

# 2. `executeBatch()`

## What is `executeBatch()`?

`executeBatch()` executes the commands that have previously been added to the batch.

Example:

```java
Statement st = con.createStatement();

st.addBatch("INSERT INTO student VALUES (1, 'A')");
st.addBatch("INSERT INTO student VALUES (2, 'B')");
st.addBatch("INSERT INTO student VALUES (3, 'C')");

int[] result = st.executeBatch();
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

## Return value

`executeBatch()` returns:

```java
int[]
```

Example:

```java
int[] result = ps.executeBatch();
```

The returned array contains update counts or JDBC-defined special values for individual batch commands.

For example, conceptually:

```text
result
│
├── 1
├── 1
└── 1
```

`1` generally means one row was affected for that command.

JDBC can also use special values such as:

```java
Statement.SUCCESS_NO_INFO
Statement.EXECUTE_FAILED
```

so don't assume every returned value must be exactly `1`.

---

# 3. Batch vs Individual Execution

## Individual execution

Suppose we need to insert 1,000 students.

Without batching:

```java
for (...) {
    ps.setInt(...);
    ps.setString(...);
    ps.executeUpdate();
}
```

Conceptually:

```text
Prepare
 ↓
Execute #1
 ↓
Execute #2
 ↓
Execute #3
 ↓
...
 ↓
Execute #1000
```

Each operation is executed individually.

---

## Batch execution

With batching:

```java
for (...) {
    ps.setInt(...);
    ps.setString(...);
    ps.addBatch();
}

ps.executeBatch();
```

Conceptually:

```text
Prepare
 ↓
Add #1
Add #2
Add #3
...
Add #1000
 ↓
executeBatch()
 ↓
Execute batch
```

---

# 4. Why use Batch Processing?

### 1. Better performance

Batching can reduce communication overhead between the Java application and database.

```text
Individual:
Java → DB
Java → DB
Java → DB
Java → DB
...

Batch:
Java → DB
   [many operations]
```

The exact performance improvement depends on the JDBC driver, database, network, SQL, batch size, and configuration.

---

### 2. Useful for large amounts of data

Common examples:

```text
Bulk INSERT
Bulk UPDATE
Bulk DELETE
Data migration
Importing CSV data
ETL operations
```

---

### 3. Cleaner bulk-operation code

Instead of:

```java
executeUpdate();
executeUpdate();
executeUpdate();
executeUpdate();
```

you can build a batch and execute it:

```java
addBatch();
addBatch();
addBatch();
addBatch();

executeBatch();
```

---

# 5. Complete `PreparedStatement` Example

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

Remember:

```text
setInt()
setString()
     ↓
addBatch()
     ↓
setInt()
setString()
     ↓
addBatch()
     ↓
...
     ↓
executeBatch()
```

---

# 6. Batch + Transaction

Batch processing and transactions are **different concepts**, but they can be used together.

Example:

```java
con.setAutoCommit(false);

try {

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

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

Here:

```text
Batch Processing
       +
Transaction Management
```

are working together.

### Important distinction

```text
addBatch()/executeBatch()
        ↓
HOW multiple SQL commands are submitted/executed

commit()/rollback()
        ↓
HOW transaction completion is controlled
```

**Batch ≠ Transaction.**

A batch can be executed with or without an explicitly managed transaction, depending on the connection's transaction mode and application requirements.

---

# 7. `Statement` vs `PreparedStatement` Batch

| Feature                     | Statement                     | PreparedStatement                             |
| --------------------------- | ----------------------------- | --------------------------------------------- |
| Add method                  | `addBatch(sql)`               | `addBatch()`                                  |
| SQL                         | Can add different SQL strings | SQL structure is prepared once                |
| Parameters                  | No parameter binding          | Supports `?` parameters                       |
| Repeated similar operations | Less convenient               | Excellent                                     |
| SQL injection protection    | Not inherently provided       | Parameter binding helps prevent SQL injection |
| Typical bulk INSERT         | Possible                      | Usually preferred                             |

---

# 8. ONEPAGE Memory Map

```text
                 JDBC BATCH PROCESSING
                         │
             ┌───────────┴───────────┐
             ↓                       ↓
         addBatch()             executeBatch()
             │                       │
             ↓                       ↓
      Add operation(s)         Execute batch
             │                       │
             └───────────┬───────────┘
                         ↓
                   int[] result
```

### Individual execution

```text
executeUpdate()
executeUpdate()
executeUpdate()
executeUpdate()
```

### Batch execution

```text
addBatch()
addBatch()
addBatch()
addBatch()
     ↓
executeBatch()
```

---

# 🔥 Final ONEPAGE Rule

> **`addBatch()` collects operations; `executeBatch()` executes the collected operations; batching can improve bulk-operation performance by reducing execution/communication overhead compared with issuing each operation individually.**

And the most important distinction:

```text
BATCH
↓
Group multiple SQL operations for execution

TRANSACTION
↓
Group database changes into one unit of commit/rollback
```

They are **not the same thing**.
