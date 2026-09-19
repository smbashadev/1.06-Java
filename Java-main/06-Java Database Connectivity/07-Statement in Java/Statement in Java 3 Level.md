# 7. Statement in Java — 3LEVEL

We will learn each concept at **3 levels**:

* 🟢 **LEVEL 1 — Basic:** What it is
* 🟡 **LEVEL 2 — Understanding:** How and why it works
* 🔴 **LEVEL 3 — Deep/Interview:** Important rules, differences, and traps

---

# 1. Statement Interface

## 🟢 LEVEL 1 — Basic

`Statement` is a JDBC interface used to **send SQL statements from Java to the database**.

It belongs to:

```java
java.sql.Statement
```

We normally obtain a `Statement` from a `Connection`:

```java
Statement st = con.createStatement();
```

Then:

```java
st.executeQuery("SELECT * FROM student");
```

### Basic flow

```text
Java Application
      ↓
Connection
      ↓
Statement
      ↓
SQL
      ↓
Database
```

---

## 🟡 LEVEL 2 — Understanding

`Statement` is an **interface**, so we don't normally create it using `new`.

❌ Wrong:

```java
Statement st = new Statement();
```

✅ Correct:

```java
Statement st = con.createStatement();
```

The JDBC driver provides the actual implementation behind the interface.

The `Statement` object gives us methods such as:

```text
execute()
executeQuery()
executeUpdate()
executeBatch()
```

---

## 🔴 LEVEL 3 — Deep / Interview

The important idea is that `Statement` is an abstraction provided by JDBC.

Your application doesn't need to know how a particular database driver internally sends SQL.

```text
Your Java code
     ↓
JDBC Statement interface
     ↓
JDBC Driver implementation
     ↓
Database
```

Also remember:

> `Statement` is appropriate mainly for simple/static SQL. For SQL containing parameters or user-supplied values, `PreparedStatement` is generally preferred.

---

# 2. `execute()`

## 🟢 LEVEL 1 — Basic

`execute()` is a **general-purpose SQL execution method**.

Syntax:

```java
boolean result = st.execute(sql);
```

It returns:

```text
boolean
```

---

## 🟡 LEVEL 2 — Understanding

Why does it return `boolean`?

Because the SQL might produce different kinds of results.

If it returns:

```java
true
```

the first result is a `ResultSet`.

If it returns:

```java
false
```

the first result is an update count or there is no result.

Example:

```java
boolean result =
    st.execute("SELECT * FROM student");

if (result) {
    ResultSet rs = st.getResultSet();
}
```

For an update:

```java
boolean result =
    st.execute(
        "UPDATE student SET marks = 90"
    );

if (!result) {
    int count = st.getUpdateCount();
}
```

---

## 🔴 LEVEL 3 — Deep / Interview

Think of `execute()` as:

> **"Execute this SQL; I will determine what type of result came back."**

Conceptually:

```text
execute()
   │
   ├── true
   │     ↓
   │  ResultSet
   │
   └── false
         ↓
    update count
    or no result
```

It is useful when:

* the type of result isn't known in advance;
* you need generic SQL execution;
* multiple results may need to be processed.

For a straightforward `SELECT`, `executeQuery()` is normally clearer.

For a straightforward update, `executeUpdate()` is normally clearer.

---

# 3. `executeQuery()`

## 🟢 LEVEL 1 — Basic

`executeQuery()` is used when SQL is expected to return a **`ResultSet`**.

Syntax:

```java
ResultSet rs =
    st.executeQuery(sql);
```

Most commonly:

```sql
SELECT
```

Example:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

---

## 🟡 LEVEL 2 — Understanding

Suppose the database contains:

```text
id    name    marks
-------------------
101   Ravi     90
102   Raj      85
103   Amit     95
```

We execute:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

The returned rows are represented by the `ResultSet`.

We can traverse them:

```java
while (rs.next()) {

    System.out.println(
        rs.getString("name")
    );
}
```

The flow is:

```text
SELECT
  ↓
Database
  ↓
Rows
  ↓
ResultSet
```

---

## 🔴 LEVEL 3 — Deep / Interview

The key point is the **return type**:

```java
ResultSet
```

Therefore:

```text
executeQuery()
       ↓
   ResultSet
```

It is intended for statements that produce a result set.

Don't use it as the normal method for:

```sql
INSERT
UPDATE
DELETE
```

For those, use `executeUpdate()`.

### Interview memory:

> `executeQuery()` → query result → `ResultSet`

---

# 4. `executeUpdate()`

## 🟢 LEVEL 1 — Basic

`executeUpdate()` executes SQL that produces an **update count**.

It returns:

```java
int
```

Typical examples:

```sql
INSERT
UPDATE
DELETE
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

---

## 🟡 LEVEL 2 — Understanding

Suppose one student is updated.

Then:

```text
count = 1
```

Suppose ten students are updated:

```text
count = 10
```

So:

```text
UPDATE
   ↓
Database
   ↓
Rows affected
   ↓
int
```

Example:

```java
int count =
    st.executeUpdate(
        "DELETE FROM student " +
        "WHERE marks < 40"
    );

System.out.println(
    "Rows affected: " + count
);
```

---

## 🔴 LEVEL 3 — Deep / Interview

Don't memorize:

> "`executeUpdate()` is only for INSERT, UPDATE and DELETE."

A better rule is:

> **Use `executeUpdate()` when the SQL is expected to produce an update count rather than a `ResultSet`.**

This can include applicable DDL such as:

```sql
CREATE TABLE
ALTER TABLE
DROP TABLE
```

depending on the operation and driver's behavior.

### Return type is the key:

```text
executeUpdate()
      ↓
     int
      ↓
update count
```

---

# 5. `executeBatch()`

## 🟢 LEVEL 1 — Basic

Suppose we have many SQL commands:

```text
INSERT 1
INSERT 2
INSERT 3
INSERT 4
```

Instead of executing each command individually, we can add them to a batch.

```java
st.addBatch(sql1);
st.addBatch(sql2);
st.addBatch(sql3);
```

Then:

```java
int[] result =
    st.executeBatch();
```

---

## 🟡 LEVEL 2 — Understanding

There are two important operations.

### Step 1 — Add SQL to the batch

```java
st.addBatch(
    "INSERT INTO student " +
    "VALUES (101, 'Ravi', 90)"
);
```

Again:

```java
st.addBatch(sql);
```

does **not** mean "execute immediately."

It means:

> Add this command to the batch.

### Step 2 — Execute the batch

```java
int[] counts =
    st.executeBatch();
```

Now the accumulated commands are executed.

---

## 🔴 LEVEL 3 — Deep / Interview

Why does `executeBatch()` return an array?

Because there can be multiple commands:

```text
SQL 1 → result 1
SQL 2 → result 2
SQL 3 → result 3
```

Therefore:

```java
int[] counts =
    st.executeBatch();
```

Conceptually:

```text
counts = [1, 1, 1]
```

The exact returned values can depend on the database driver and JDBC batch-update semantics.

### Important:

Batching ≠ transaction.

They are different concepts.

```text
Batch
 ↓
Groups SQL commands for execution
```

while:

```text
Transaction
 ↓
Controls commit / rollback
```

They can be used together:

```java
con.setAutoCommit(false);

st.addBatch(sql1);
st.addBatch(sql2);
st.addBatch(sql3);

st.executeBatch();

con.commit();
```

---

# 🔥 All Four at One Glance

| Method            | Main purpose                 | Typical SQL                                  | Return      |
| ----------------- | ---------------------------- | -------------------------------------------- | ----------- |
| `execute()`       | General-purpose execution    | Any                                          | `boolean`   |
| `executeQuery()`  | Retrieve rows                | `SELECT`                                     | `ResultSet` |
| `executeUpdate()` | Modify/define data           | `INSERT`, `UPDATE`, `DELETE`, applicable DDL | `int`       |
| `executeBatch()`  | Execute accumulated commands | Multiple commands                            | `int[]`     |

---

# 🧠 LEVEL 1 — One-Line Memory

```text
execute()        → boolean
executeQuery()   → ResultSet
executeUpdate()  → int
executeBatch()   → int[]
```

---

# 🧠 LEVEL 2 — Purpose Memory

```text
Need rows?
    ↓
executeQuery()

Need update count?
    ↓
executeUpdate()

Need general execution?
    ↓
execute()

Have many commands?
    ↓
executeBatch()
```

---

# 🧠 LEVEL 3 — Interview Memory

```text
                         Statement
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
        ▼                    ▼                    ▼
 executeQuery()       executeUpdate()          execute()
        │                    │                    │
        ▼                    ▼                 boolean
   ResultSet                 int                 │
                                             ┌───┴───┐
                                           true    false
                                             │        │
                                             ▼        ▼
                                         ResultSet  Update
                                                    count
```

And:

```text
Statement
    │
    ├── addBatch(sql1)
    ├── addBatch(sql2)
    ├── addBatch(sql3)
    │
    ▼
executeBatch()
    │
    ▼
   int[]
```

---

# 🎯 Final 3-Level Revision

### LEVEL 1 — Know it

```text
Statement = JDBC interface for executing SQL.
```

### LEVEL 2 — Understand it

```text
Statement
   ↓
executeQuery()  → ResultSet
executeUpdate() → int
execute()       → boolean
executeBatch()  → int[]
```

### LEVEL 3 — Don't get confused

```text
executeQuery()
    = "Give me the rows."

executeUpdate()
    = "Perform the operation and give me the update count."

execute()
    = "Execute it; I'll inspect what kind of first result I got."

executeBatch()
    = "Execute all the commands I've accumulated."
```

> **Golden rule:** `Query → ResultSet`, `Update → int`, `General → boolean`, `Batch → int[]`.
