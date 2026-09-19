# 11. CRUD Operations in Java — 3LEVEL

The **3LEVEL method** means we will understand every CRUD operation at three depths:

* 🟢 **LEVEL 1 — Basic:** What is it?
* 🟡 **LEVEL 2 — Working:** How does it work in JDBC?
* 🔴 **LEVEL 3 — Deep Understanding:** Why, when, and what can go wrong?

We will use this table:

```sql
CREATE TABLE student (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    marks DOUBLE
);
```

Example:

```text
101  Ravi   85
102  Kumar  90
103  John   75
```

---

# 1. INSERT — CREATE

## 🟢 LEVEL 1 — BASIC

### What is INSERT?

`INSERT` is used to **add new rows into a database table**.

Example:

```sql
INSERT INTO student(id, name, marks)
VALUES (104, 'Basha', 88);
```

Before:

```text
101 Ravi   85
102 Kumar  90
103 John   75
```

After:

```text
101 Ravi   85
102 Kumar  90
103 John   75
104 Basha  88
```

So:

```text
INSERT = ADD NEW DATA
```

---

## 🟡 LEVEL 2 — JDBC Working

In JDBC, normally use `PreparedStatement`.

```java
String sql =
    "INSERT INTO student(id, name, marks) VALUES (?, ?, ?)";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 104);
ps.setString(2, "Basha");
ps.setDouble(3, 88.0);

int rows = ps.executeUpdate();

System.out.println(rows);
```

### Flow

```text
SQL
 ↓
prepareStatement()
 ↓
setXXX()
 ↓
executeUpdate()
 ↓
affected rows
```

The `?` parameters are numbered starting from **1**:

```text
?       ?       ?
↓       ↓       ↓
1       2       3
↓       ↓       ↓
104    Basha    88
```

---

## 🔴 LEVEL 3 — DEEP UNDERSTANDING

### Why `executeUpdate()`?

Because INSERT changes database state.

```text
INSERT
  ↓
Database changes
  ↓
executeUpdate()
  ↓
int
```

If one row was inserted:

```text
rows = 1
```

If nothing was inserted:

```text
rows = 0
```

### Why PreparedStatement?

Instead of:

```java
"INSERT ... VALUES (" + id + ", '" + name + "', " + marks + ")"
```

use:

```java
"INSERT ... VALUES (?, ?, ?)"
```

and bind values:

```java
ps.setInt(...)
ps.setString(...)
ps.setDouble(...)
```

This is safer for external/user-provided values and helps prevent SQL injection.

### Remember

```text
INSERT
  ↓
Create
  ↓
executeUpdate()
  ↓
int
```

---

# 2. SELECT — READ

## 🟢 LEVEL 1 — BASIC

### What is SELECT?

`SELECT` is used to **retrieve data from the database**.

Example:

```sql
SELECT * FROM student;
```

Database:

```text
101 Ravi   85
102 Kumar  90
103 John   75
```

So:

```text
SELECT = READ DATA
```

---

## 🟡 LEVEL 2 — JDBC Working

```java
String sql =
    "SELECT * FROM student";

PreparedStatement ps =
    con.prepareStatement(sql);

ResultSet rs =
    ps.executeQuery();
```

Unlike INSERT, we don't get an `int`.

We get:

```text
ResultSet
```

Then:

```java
while (rs.next()) {

    int id =
        rs.getInt("id");

    String name =
        rs.getString("name");

    double marks =
        rs.getDouble("marks");

    System.out.println(
        id + " " + name + " " + marks
    );
}
```

Output:

```text
101 Ravi 85.0
102 Kumar 90.0
103 John 75.0
```

### Flow

```text
SELECT
  ↓
executeQuery()
  ↓
ResultSet
  ↓
next()
  ↓
getXXX()
```

---

## 🔴 LEVEL 3 — DEEP UNDERSTANDING

### Why ResultSet?

A SELECT can return:

```text
0 rows
1 row
many rows
```

Therefore JDBC needs an object capable of representing and navigating those returned rows.

That object is:

```java
ResultSet
```

### Why `next()`?

Initially, the cursor is before the first row.

```text
BEFORE
  ↓ next()
ROW 1
  ↓ next()
ROW 2
  ↓ next()
ROW 3
  ↓ next()
AFTER
```

That's why:

```java
while (rs.next()) {
    ...
}
```

is so common.

### `getXXX()`

The getter should correspond appropriately to the column's data type:

```text
INT       → getInt()
VARCHAR   → getString()
DOUBLE    → getDouble()
```

Example:

```java
rs.getInt("id");
rs.getString("name");
rs.getDouble("marks");
```

### SELECT with WHERE

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();

if (rs.next()) {
    System.out.println(
        rs.getString("name")
    );
}
```

### Remember

```text
SELECT
  ↓
Read
  ↓
executeQuery()
  ↓
ResultSet
  ↓
next()
  ↓
getXXX()
```

---

# 3. UPDATE — UPDATE

## 🟢 LEVEL 1 — BASIC

### What is UPDATE?

`UPDATE` changes existing data.

Suppose:

```text
101 Ravi 85
```

We want:

```text
101 Ravi 95
```

SQL:

```sql
UPDATE student
SET marks = 95
WHERE id = 101;
```

So:

```text
UPDATE = CHANGE EXISTING DATA
```

---

## 🟡 LEVEL 2 — JDBC Working

```java
String sql =
    "UPDATE student SET marks = ? WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setDouble(1, 95.0);
ps.setInt(2, 101);

int rows =
    ps.executeUpdate();

System.out.println(rows);
```

Parameter positions:

```text
?             ?
↓             ↓
1             2
↓             ↓
marks         id
```

Therefore:

```java
ps.setDouble(1, 95.0);
ps.setInt(2, 101);
```

means:

```text
SET marks = 95
WHERE id = 101
```

---

## 🔴 LEVEL 3 — DEEP UNDERSTANDING

### Why `executeUpdate()`?

UPDATE changes database state:

```text
UPDATE
  ↓
Database modification
  ↓
executeUpdate()
  ↓
int
```

The returned integer represents affected rows.

```text
1 → one row affected
0 → no matching row
many → multiple rows affected
```

### ⚠️ Why WHERE is important

This is dangerous:

```sql
UPDATE student
SET marks = 95;
```

Without `WHERE`, the statement can affect **every row**.

Correct targeted operation:

```sql
UPDATE student
SET marks = 95
WHERE id = 101;
```

Think:

```text
UPDATE + WHERE
       ↓
Which rows should change?
```

### Updating multiple columns

```sql
UPDATE student
SET name = ?, marks = ?
WHERE id = ?;
```

```java
ps.setString(1, "Rahul");
ps.setDouble(2, 92.0);
ps.setInt(3, 101);
```

### Remember

```text
UPDATE
  ↓
Modify
  ↓
executeUpdate()
  ↓
int
```

---

# 4. DELETE — DELETE

## 🟢 LEVEL 1 — BASIC

### What is DELETE?

`DELETE` removes existing rows from a table.

Example:

```sql
DELETE FROM student
WHERE id = 103;
```

Before:

```text
101 Ravi   85
102 Kumar  90
103 John   75
```

After:

```text
101 Ravi   85
102 Kumar  90
```

So:

```text
DELETE = REMOVE DATA
```

---

## 🟡 LEVEL 2 — JDBC Working

```java
String sql =
    "DELETE FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 103);

int rows =
    ps.executeUpdate();

System.out.println(rows);
```

Flow:

```text
DELETE
  ↓
PreparedStatement
  ↓
setInt()
  ↓
executeUpdate()
  ↓
affected rows
```

---

## 🔴 LEVEL 3 — DEEP UNDERSTANDING

### Why `executeUpdate()`?

DELETE changes database state:

```text
DELETE
  ↓
Rows removed
  ↓
executeUpdate()
  ↓
int
```

### ⚠️ DELETE without WHERE

This:

```sql
DELETE FROM student;
```

can remove **all rows** from the table.

This:

```sql
DELETE FROM student
WHERE id = ?;
```

targets the matching rows.

Always ask:

> **"Which rows will my DELETE affect?"**

before executing it.

### Remember

```text
DELETE
  ↓
Remove
  ↓
executeUpdate()
  ↓
int
```

---

# 5. The Most Important 3LEVEL Comparison

| Operation  | Meaning | SQL      | JDBC method       | Result      |
| ---------- | ------- | -------- | ----------------- | ----------- |
| **INSERT** | Add     | `INSERT` | `executeUpdate()` | `int`       |
| **SELECT** | Read    | `SELECT` | `executeQuery()`  | `ResultSet` |
| **UPDATE** | Change  | `UPDATE` | `executeUpdate()` | `int`       |
| **DELETE** | Remove  | `DELETE` | `executeUpdate()` | `int`       |

### The memory trick

```text
I U D → executeUpdate()
S     → executeQuery()
```

Or:

> **If you're changing rows → `executeUpdate()`**
> **If you're retrieving rows → `executeQuery()`**

---

# 6. CRUD — Complete 3LEVEL Mental Model

```text
                         CRUD
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ↓                ↓                ↓
       CREATE            READ            UPDATE
          │                │                │
       INSERT            SELECT          UPDATE
          │                │                │
          ↓                ↓                ↓
 executeUpdate()      executeQuery()  executeUpdate()
          │                │                │
          ↓                ↓                ↓
         int           ResultSet           int


                        DELETE
                           │
                        DELETE
                           │
                           ↓
                    executeUpdate()
                           │
                           ↓
                          int
```

---

# 7. 🔥 Final Exam-Level Understanding

### INSERT

**Question:** What does INSERT do?

**Answer:** Adds new rows.

```java
ps.executeUpdate();
```

---

### SELECT

**Question:** What does SELECT do?

**Answer:** Retrieves rows.

```java
ResultSet rs =
    ps.executeQuery();
```

Then:

```java
while (rs.next()) {
    ...
}
```

---

### UPDATE

**Question:** What does UPDATE do?

**Answer:** Modifies existing rows.

```java
ps.executeUpdate();
```

Be careful with:

```sql
WHERE
```

---

### DELETE

**Question:** What does DELETE do?

**Answer:** Removes existing rows.

```java
ps.executeUpdate();
```

Again, be careful with:

```sql
WHERE
```

---

# 🧠 One-Line Memory

```text
INSERT  = ADD
SELECT  = READ
UPDATE  = CHANGE
DELETE  = REMOVE
```

And JDBC:

```text
INSERT  ──┐
UPDATE  ──┼──→ executeUpdate() → int
DELETE  ──┘

SELECT ─────→ executeQuery() → ResultSet
                                  ↓
                                next()
                                  ↓
                                getXXX()
```

That is the **3LEVEL foundation of JDBC CRUD**.
