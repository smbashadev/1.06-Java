# 11. CRUD Operations in Java — ONEPAGE

**CRUD** is one of the most important concepts in JDBC because almost every database application performs these four basic operations:

```text
C → Create  → INSERT
R → Read    → SELECT
U → Update  → UPDATE
D → Delete  → DELETE
```

We will use this example table throughout:

```sql
student
--------------------------------
id    name       marks
--------------------------------
101   Ravi       85
102   Kumar      90
103   John       75
```

---

# 1. INSERT — Create

## What is INSERT?

`INSERT` adds a **new row** to a database table.

SQL:

```sql
INSERT INTO student(id, name, marks)
VALUES (?, ?, ?);
```

In JDBC, `PreparedStatement` is normally preferred:

```java
String sql =
    "INSERT INTO student(id, name, marks) VALUES (?, ?, ?)";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 104);
ps.setString(2, "Basha");
ps.setInt(3, 88);

int rows = ps.executeUpdate();

System.out.println(rows);
```

Output:

```text
1
```

### What happened?

```text
Java
 ↓
PreparedStatement
 ↓
INSERT SQL
 ↓
Database
 ↓
New row added
```

### Important method

```java
executeUpdate()
```

is used because `INSERT` changes the database.

### Return value

```java
int rows = ps.executeUpdate();
```

returns the number of affected rows.

For a successful single-row insert, normally:

```text
1
```

---

# 2. SELECT — Read

## What is SELECT?

`SELECT` retrieves data from the database.

Example:

```sql
SELECT * FROM student;
```

JDBC:

```java
String sql =
    "SELECT * FROM student";

PreparedStatement ps =
    con.prepareStatement(sql);

ResultSet rs =
    ps.executeQuery();
```

Now process the result:

```java
while (rs.next()) {

    int id = rs.getInt("id");
    String name = rs.getString("name");
    int marks = rs.getInt("marks");

    System.out.println(
        id + " " + name + " " + marks
    );
}
```

Output:

```text
101 Ravi 85
102 Kumar 90
103 John 75
```

### Important method

For a `SELECT`:

```java
executeQuery()
```

returns:

```java
ResultSet
```

So:

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

# 3. UPDATE — Modify

## What is UPDATE?

`UPDATE` changes existing data.

Example:

```sql
UPDATE student
SET marks = ?
WHERE id = ?;
```

JDBC:

```java
String sql =
    "UPDATE student SET marks = ? WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 95);
ps.setInt(2, 101);

int rows =
    ps.executeUpdate();

System.out.println(rows);
```

Output:

```text
1
```

Student `101` changes from:

```text
101 Ravi 85
```

to:

```text
101 Ravi 95
```

### Important method

```java
executeUpdate()
```

because `UPDATE` changes database data.

---

# 4. DELETE — Remove

## What is DELETE?

`DELETE` removes existing rows from a table.

Example:

```sql
DELETE FROM student
WHERE id = ?;
```

JDBC:

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

Output:

```text
1
```

Student `103` is removed.

### Important method

```java
executeUpdate()
```

because `DELETE` changes the database.

---

# 5. CRUD — Complete Comparison

| Operation  | SQL      | Purpose       | JDBC method       | Typical return |
| ---------- | -------- | ------------- | ----------------- | -------------- |
| **Create** | `INSERT` | Add data      | `executeUpdate()` | `int`          |
| **Read**   | `SELECT` | Retrieve data | `executeQuery()`  | `ResultSet`    |
| **Update** | `UPDATE` | Modify data   | `executeUpdate()` | `int`          |
| **Delete** | `DELETE` | Remove data   | `executeUpdate()` | `int`          |

### The most important rule

```text
INSERT
   ↓
executeUpdate()

SELECT
   ↓
executeQuery()

UPDATE
   ↓
executeUpdate()

DELETE
   ↓
executeUpdate()
```

---

# 6. Why PreparedStatement?

For CRUD operations involving user/application values, prefer:

```java
PreparedStatement
```

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "DELETE FROM student WHERE id = ?"
    );

ps.setInt(1, 103);
```

Instead of constructing SQL through string concatenation:

```java
// Avoid this pattern for user-controlled values
String sql =
    "DELETE FROM student WHERE id = " + id;
```

`PreparedStatement` provides parameter binding and helps prevent SQL injection.

---

# 7. CRUD Flow in JDBC

The complete general pattern is:

```text
                JAVA
                  ↓
             Connection
                  ↓
         PreparedStatement
                  ↓
            SQL command
                  ↓
              Database
```

For `INSERT`, `UPDATE`, and `DELETE`:

```text
SQL
 ↓
setXXX()
 ↓
executeUpdate()
 ↓
affected row count
```

For `SELECT`:

```text
SQL
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

# 8. `executeUpdate()` vs `executeQuery()`

This is a major JDBC doubt.

### `executeQuery()`

Used primarily for queries that return a result set:

```java
ResultSet rs =
    ps.executeQuery();
```

Typical operation:

```text
SELECT
```

### `executeUpdate()`

Used for SQL statements that modify data:

```java
int count =
    ps.executeUpdate();
```

Typical operations:

```text
INSERT
UPDATE
DELETE
```

Memory:

```text
SELECT
   ↓
QUERY
   ↓
ResultSet

INSERT / UPDATE / DELETE
   ↓
UPDATE database
   ↓
int
```

---

# 9. Important Safety Rule: WHERE

Be extremely careful with:

```sql
UPDATE
```

and:

```sql
DELETE
```

### Safe targeted update

```sql
UPDATE student
SET marks = 95
WHERE id = 101;
```

### Dangerous update

```sql
UPDATE student
SET marks = 95;
```

This can modify **every row**.

Likewise:

```sql
DELETE FROM student
WHERE id = 101;
```

deletes one matching student, while:

```sql
DELETE FROM student;
```

can delete **all rows** from the table.

So always understand your `WHERE` condition before executing `UPDATE` or `DELETE`.

---

# 10. Final CRUD Memory Map

```text
             CRUD
              │
      ┌───────┼────────┐
      │       │        │
      ↓       ↓        ↓
   INSERT   SELECT   UPDATE   DELETE
      │       │        │        │
      ↓       ↓        ↓        ↓
 execute   execute   execute  execute
 Update    Query     Update   Update
      │       │        │        │
      ↓       ↓        ↓        ↓
     int   ResultSet   int      int
```

## 🔥 One-line formula

> **INSERT, UPDATE, DELETE → `executeUpdate()`; SELECT → `executeQuery()` → `ResultSet`.**

That is the core of **CRUD in JDBC**.
