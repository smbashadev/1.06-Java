# 7. Statement in Java — ONEPAGE

`Statement` is one of the core JDBC interfaces used to **send SQL statements to a database and obtain the result**.

Package:

```java
java.sql.Statement
```

---

# 1. Statement Interface

## Definition

`Statement` is an interface that represents a SQL statement.

It is normally obtained from a `Connection`:

```java
Statement st = con.createStatement();
```

Relationship:

```text
Connection
     │
     │ createStatement()
     ▼
 Statement
     │
     ├── execute()
     ├── executeQuery()
     ├── executeUpdate()
     └── executeBatch()
```

### Important

`Statement` itself is an **interface**, not a class.

Therefore:

```java
Statement st = new Statement();
```

❌ Invalid.

The JDBC driver supplies the implementation.

---

# 2. `execute()`

## Purpose

`execute()` is a **general-purpose method** for executing SQL when you may not know beforehand whether the result will be a `ResultSet` or an update count.

Syntax:

```java
boolean result = st.execute(sql);
```

Example:

```java
boolean result =
    st.execute("SELECT * FROM student");
```

The returned `boolean` tells you what kind of result was produced.

### If `true`

The SQL produced a `ResultSet`.

```java
ResultSet rs = st.getResultSet();
```

### If `false`

The SQL produced an update count or no result.

```java
int count = st.getUpdateCount();
```

Conceptually:

```text
execute()
    │
    ├── true  → ResultSet
    │
    └── false → update count / no result
```

### Example

```java
boolean result =
    st.execute("SELECT * FROM student");

if (result) {

    ResultSet rs =
        st.getResultSet();

} else {

    int count =
        st.getUpdateCount();
}
```

### When to use?

Use `execute()` when the type of result is not known in advance or when handling multiple results.

---

# 3. `executeQuery()`

## Purpose

`executeQuery()` is primarily used for SQL statements that **return a `ResultSet`**, especially `SELECT`.

Syntax:

```java
ResultSet rs =
    st.executeQuery(sql);
```

Example:

```java
Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

Then process the result:

```java
while (rs.next()) {

    System.out.println(
        rs.getInt("id")
    );

    System.out.println(
        rs.getString("name")
    );
}
```

Flow:

```text
executeQuery()
      ↓
   SELECT
      ↓
 ResultSet
      ↓
   rs.next()
      ↓
 Read rows
```

---

## ❓ What does `executeQuery()` return?

```text
ResultSet
```

So:

```java
ResultSet rs = st.executeQuery(...);
```

is correct.

---

## ❓ Can we use it for INSERT?

No.

This is incorrect usage:

```java
st.executeQuery(
    "INSERT INTO student ..."
);
```

For an INSERT/UPDATE/DELETE, use `executeUpdate()`.

---

# 4. `executeUpdate()`

## Purpose

`executeUpdate()` is primarily used for SQL statements that **modify data or database structure**, such as:

```text
INSERT
UPDATE
DELETE
```

It returns an `int` representing the **update count** for applicable statements.

---

## INSERT

```java
int count =
    st.executeUpdate(
        "INSERT INTO student " +
        "VALUES (101, 'Ravi', 90)"
    );
```

---

## UPDATE

```java
int count =
    st.executeUpdate(
        "UPDATE student " +
        "SET marks = 95 " +
        "WHERE id = 101"
    );
```

If one row is updated:

```text
count = 1
```

---

## DELETE

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

## Flow

```text
executeUpdate()
       ↓
INSERT / UPDATE / DELETE
       ↓
   update count
       ↓
       int
```

---

# 5. `executeBatch()`

## Purpose

`executeBatch()` executes a group of SQL commands as a **batch**.

Instead of sending/executing every statement separately:

```text
SQL 1 → execute
SQL 2 → execute
SQL 3 → execute
SQL 4 → execute
```

we can collect them:

```text
SQL 1 ┐
SQL 2 │
SQL 3 ├── Batch
SQL 4 ┘
       ↓
executeBatch()
```

---

## Step 1 — Create Statement

```java
Statement st =
    con.createStatement();
```

## Step 2 — Add commands

```java
st.addBatch(
    "INSERT INTO student VALUES (101, 'Ravi', 90)"
);

st.addBatch(
    "INSERT INTO student VALUES (102, 'Raj', 85)"
);

st.addBatch(
    "INSERT INTO student VALUES (103, 'Amit', 95)"
);
```

## Step 3 — Execute batch

```java
int[] results =
    st.executeBatch();
```

The returned array contains update counts/status information for the batch commands, subject to JDBC/driver behavior.

---

# `addBatch()` vs `executeBatch()`

This is an important distinction.

### `addBatch()`

Adds a command to the batch.

```java
st.addBatch(sql);
```

### `executeBatch()`

Executes the accumulated batch.

```java
st.executeBatch();
```

Memory:

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

# 🔥 The Four Methods Compared

| Method            | Main purpose                 | Typical SQL                       | Return      |
| ----------------- | ---------------------------- | --------------------------------- | ----------- |
| `execute()`       | General-purpose execution    | Any SQL/result type               | `boolean`   |
| `executeQuery()`  | Retrieve data                | `SELECT`                          | `ResultSet` |
| `executeUpdate()` | Modify data/schema           | `INSERT`, `UPDATE`, `DELETE`, DDL | `int`       |
| `executeBatch()`  | Execute accumulated commands | Multiple commands                 | `int[]`     |

---

# 🧠 Easy Memory Trick

```text
execute()
   ↓
"I don't know the result type."

executeQuery()
   ↓
"I expect rows."

executeUpdate()
   ↓
"I expect an update count."

executeBatch()
   ↓
"I have multiple commands."
```

---

# Important JDBC Flow

```text
DriverManager
      ↓
getConnection()
      ↓
Connection
      ↓
createStatement()
      ↓
Statement
      │
      ├── executeQuery()
      │       ↓
      │   ResultSet
      │
      ├── executeUpdate()
      │       ↓
      │   int
      │
      ├── execute()
      │       ↓
      │   boolean
      │       ↓
      │   ResultSet / update count
      │
      └── executeBatch()
              ↓
             int[]
```

---

# ⚠️ Important: `Statement` vs `PreparedStatement`

Do not confuse:

```java
Statement
```

with:

```java
PreparedStatement
```

`Statement`:

```java
Statement st =
    con.createStatement();

st.executeQuery(
    "SELECT * FROM student"
);
```

`PreparedStatement`:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );

ps.setInt(1, 101);

ps.executeQuery();
```

For SQL containing external/user-provided values, `PreparedStatement` is generally preferred because it supports parameterized SQL and helps avoid SQL-injection vulnerabilities when used correctly.

---

# ⭐ ONEPAGE FINAL MAP

```text
                    Statement
                        │
            ┌───────────┼────────────┐
            │           │            │
            ▼           ▼            ▼
        execute()  executeQuery()  executeUpdate()
            │           │            │
            │           ▼            ▼
            │       ResultSet       int
            │
            └── true → ResultSet
                false → update count/no result

                        │
                        ▼
                  executeBatch()
                        │
                        ▼
                       int[]
```

### Final rule to memorize

> **`execute()` → general execution, `executeQuery()` → retrieve a `ResultSet`, `executeUpdate()` → obtain an update count, and `executeBatch()` → execute multiple accumulated commands.**
