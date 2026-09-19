# 11. CRUD Operations in Java — DEEPDIVE

**CRUD** stands for:

```text
C → Create → INSERT
R → Read   → SELECT
U → Update → UPDATE
D → Delete → DELETE
```

CRUD represents the four fundamental operations performed on data stored in a database.

In JDBC, these operations are normally performed through:

```text
Java Application
      ↓
Connection
      ↓
PreparedStatement
      ↓
SQL
      ↓
Database
```

We will use this table throughout:

```sql
CREATE TABLE student (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    marks DOUBLE
);
```

Example data:

```text
+-----+-------+-------+
| id  | name  | marks |
+-----+-------+-------+
| 101 | Ravi  | 85.0  |
| 102 | Kumar | 90.0  |
| 103 | John  | 75.0  |
+-----+-------+-------+
```

---

# 1. INSERT — CREATE

## 1.1 What is INSERT?

`INSERT` is an SQL command used to **add a new row to a table**.

For example:

```sql
INSERT INTO student(id, name, marks)
VALUES (104, 'Basha', 88.0);
```

Before:

```text
101 Ravi  85
102 Kumar 90
103 John  75
```

After:

```text
101 Ravi  85
102 Kumar 90
103 John  75
104 Basha 88
```

So:

```text
INSERT
   ↓
New row
   ↓
Database
```

---

# 1.2 INSERT using JDBC

The preferred JDBC approach is `PreparedStatement`.

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

Output:

```text
1
```

---

# 1.3 Understanding every line

### Step 1 — SQL

```java
String sql =
    "INSERT INTO student(id, name, marks) VALUES (?, ?, ?)";
```

The three `?` characters are parameter placeholders.

```text
?       ?       ?
↓       ↓       ↓
id     name    marks
```

---

### Step 2 — Prepare SQL

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

The `Connection` creates a `PreparedStatement`.

```text
Connection
     ↓
prepareStatement()
     ↓
PreparedStatement
```

---

### Step 3 — Supply values

```java
ps.setInt(1, 104);
ps.setString(2, "Basha");
ps.setDouble(3, 88.0);
```

JDBC parameter indexes start at **1**:

```text
?       ?       ?
↓       ↓       ↓
1       2       3
```

Therefore:

```java
setInt(1, 104)
```

means:

```text
parameter 1 = 104
```

---

### Step 4 — Execute

```java
int rows = ps.executeUpdate();
```

`INSERT` modifies the database, so we use:

```java
executeUpdate()
```

The returned `int` represents the number of affected rows.

For one successfully inserted row:

```text
1
```

---

# 1.4 Why `executeUpdate()` instead of `executeQuery()`?

Because `INSERT` changes the database.

```text
INSERT
   ↓
Database modification
   ↓
executeUpdate()
```

`executeQuery()` is intended for retrieving a result set, typically with `SELECT`.

---

# 1.5 What if the INSERT fails?

Suppose `id` is a primary key and you try:

```java
ps.setInt(1, 101);
```

when `101` already exists.

The database can reject the operation, and JDBC generally reports this through:

```java
SQLException
```

Example:

```java
try {
    int rows = ps.executeUpdate();
} catch (SQLException e) {
    e.printStackTrace();
}
```

---

# 1.6 INSERT with generated IDs

Many database tables use automatically generated IDs.

For example, if the database generates the ID, your SQL may be:

```sql
INSERT INTO student(name, marks)
VALUES (?, ?);
```

JDBC:

```java
String sql =
    "INSERT INTO student(name, marks) VALUES (?, ?)";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, "Basha");
ps.setDouble(2, 88.0);

int rows = ps.executeUpdate();
```

The database generates the ID.

If your application needs the generated key, JDBC provides generated-key support, for example:

```java
PreparedStatement ps =
    con.prepareStatement(
        sql,
        Statement.RETURN_GENERATED_KEYS
    );
```

Then the generated keys can be obtained through:

```java
ResultSet keys =
    ps.getGeneratedKeys();
```

---

# 2. SELECT — READ

## 2.1 What is SELECT?

`SELECT` retrieves data from a database.

Example:

```sql
SELECT * FROM student;
```

Unlike `INSERT`, `SELECT` normally doesn't modify the table.

It **returns data**.

```text
Database
    ↓
SELECT
    ↓
Rows
    ↓
Java
```

---

# 2.2 SELECT using JDBC

```java
String sql =
    "SELECT * FROM student";

PreparedStatement ps =
    con.prepareStatement(sql);

ResultSet rs =
    ps.executeQuery();
```

The important point:

```java
executeQuery()
```

returns:

```java
ResultSet
```

---

# 2.3 What is ResultSet?

`ResultSet` represents the data returned by a query.

Imagine the database returns:

```text
101 Ravi  85
102 Kumar 90
103 John  75
```

The `ResultSet` lets Java navigate through these rows.

```text
ResultSet
   ↓
Row 1
   ↓
Row 2
   ↓
Row 3
```

---

# 2.4 Processing ResultSet

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

---

# 2.5 Why do we call `next()`?

A newly obtained `ResultSet` cursor is initially positioned before the first row.

Conceptually:

```text
        Cursor
          ↓
        BEFORE
          │
          ↓ next()
       Row 1
          │
          ↓ next()
       Row 2
          │
          ↓ next()
       Row 3
          │
          ↓ next()
        AFTER
```

Therefore:

```java
while (rs.next())
```

means:

> Move to the next row and continue while a row exists.

---

# 2.6 `getInt()`, `getString()`, `getDouble()`

Suppose the table has:

```text
id      → INT
name    → VARCHAR
marks   → DOUBLE
```

Then:

```java
rs.getInt("id");
rs.getString("name");
rs.getDouble("marks");
```

Conceptually:

```text
SQL type       JDBC getter
---------------------------
INT       →    getInt()
VARCHAR   →    getString()
DOUBLE    →    getDouble()
```

---

# 2.7 SELECT with WHERE

You don't have to retrieve every row.

Example:

```sql
SELECT *
FROM student
WHERE id = ?;
```

JDBC:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();

while (rs.next()) {
    System.out.println(
        rs.getInt("id") + " " +
        rs.getString("name") + " " +
        rs.getDouble("marks")
    );
}
```

The flow is:

```text
Java supplies 101
        ↓
WHERE id = 101
        ↓
Database searches
        ↓
Matching rows
        ↓
ResultSet
```

---

# 2.8 SELECT one row vs multiple rows

A `SELECT` can return:

### Zero rows

```text
ResultSet
   ↓
no matching row
```

### One row

```text
ResultSet
   ↓
one matching row
```

### Many rows

```text
ResultSet
   ↓
row 1
row 2
row 3
...
```

That's why:

```java
while (rs.next())
```

is commonly used.

If you expect at most one row, you can still use `if`:

```java
if (rs.next()) {
    // process the row
}
```

---

# 2.9 SELECT and `executeQuery()`

The normal pattern is:

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

Remember:

> `executeQuery()` returns a `ResultSet`.

---

# 3. UPDATE — UPDATE

## 3.1 What is UPDATE?

`UPDATE` modifies existing rows.

Example:

```sql
UPDATE student
SET marks = 95
WHERE id = 101;
```

Before:

```text
101 Ravi 85
```

After:

```text
101 Ravi 95
```

---

# 3.2 UPDATE using JDBC

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

Output:

```text
1
```

---

# 3.3 Understand the parameter positions

SQL:

```sql
UPDATE student
SET marks = ?
WHERE id = ?;
```

Parameter map:

```text
?             ?
↓             ↓
1             2
marks         id
```

Therefore:

```java
ps.setDouble(1, 95.0);
ps.setInt(2, 101);
```

means:

```text
marks = 95
id = 101
```

---

# 3.4 Why does UPDATE use `executeUpdate()`?

Because `UPDATE` modifies database data.

```text
UPDATE
   ↓
Modify existing row
   ↓
executeUpdate()
```

The return value is the number of rows affected.

---

# 3.5 The most dangerous UPDATE mistake

Consider:

```sql
UPDATE student
SET marks = 95;
```

There is **no `WHERE` condition**.

That can update every row:

```text
101 → 95
102 → 95
103 → 95
104 → 95
...
```

Compare:

```sql
UPDATE student
SET marks = 95
WHERE id = 101;
```

Only the matching row is targeted.

### Golden rule

> Always inspect the `WHERE` condition before executing an UPDATE.

---

# 3.6 Updating multiple columns

You can update more than one column:

```sql
UPDATE student
SET name = ?, marks = ?
WHERE id = ?;
```

JDBC:

```java
PreparedStatement ps =
    con.prepareStatement(
        "UPDATE student " +
        "SET name = ?, marks = ? " +
        "WHERE id = ?"
    );

ps.setString(1, "Rahul");
ps.setDouble(2, 92.0);
ps.setInt(3, 101);

int rows =
    ps.executeUpdate();
```

Parameter mapping:

```text
1 → name
2 → marks
3 → id
```

---

# 3.7 UPDATE and affected rows

Suppose:

```java
int rows =
    ps.executeUpdate();
```

Possible results include:

```text
0
```

No rows matched the condition.

Or:

```text
1
```

One row was affected.

Or:

```text
5
```

Five rows were affected.

Therefore:

```java
if (rows == 0) {
    System.out.println("No student found");
}
```

can be useful.

---

# 4. DELETE — DELETE

## 4.1 What is DELETE?

`DELETE` removes rows from a table.

Example:

```sql
DELETE FROM student
WHERE id = 103;
```

Before:

```text
101 Ravi  85
102 Kumar 90
103 John  75
```

After:

```text
101 Ravi  85
102 Kumar 90
```

---

# 4.2 DELETE using JDBC

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

---

# 4.3 Why does DELETE use `executeUpdate()`?

Because `DELETE` modifies database contents.

```text
DELETE
   ↓
Remove rows
   ↓
executeUpdate()
   ↓
affected row count
```

---

# 4.4 DELETE without WHERE

This is extremely dangerous:

```sql
DELETE FROM student;
```

It can delete **all rows** in the table.

Compare:

```sql
DELETE FROM student
WHERE id = ?;
```

which targets rows matching the supplied condition.

### Golden rule

> Before executing DELETE, verify the `WHERE` condition.

---

# 4.5 DELETE and affected rows

```java
int rows =
    ps.executeUpdate();

if (rows == 0) {
    System.out.println(
        "No matching student found"
    );
} else {
    System.out.println(
        rows + " row(s) deleted"
    );
}
```

---

# 5. Complete CRUD Comparison

| CRUD           | SQL      | Purpose       | JDBC execution    | Return      |
| -------------- | -------- | ------------- | ----------------- | ----------- |
| **C — Create** | `INSERT` | Add rows      | `executeUpdate()` | `int`       |
| **R — Read**   | `SELECT` | Retrieve rows | `executeQuery()`  | `ResultSet` |
| **U — Update** | `UPDATE` | Modify rows   | `executeUpdate()` | `int`       |
| **D — Delete** | `DELETE` | Remove rows   | `executeUpdate()` | `int`       |

This is the most important table to remember.

---

# 6. Why PreparedStatement Is Preferred for CRUD

Consider user input:

```java
String name = ...;
```

Don't normally build SQL by concatenating that value:

```java
String sql =
    "SELECT * FROM student WHERE name = '"
    + name
    + "'";
```

Instead:

```java
String sql =
    "SELECT * FROM student WHERE name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, name);
```

The parameter is bound separately from the SQL structure.

This is one of the main defenses against **SQL injection**.

---

# 7. CRUD and Transactions

CRUD operations can also participate in a transaction.

Suppose you need:

```text
INSERT student
     +
UPDATE another table
```

and both operations must succeed together.

You can disable automatic committing:

```java
con.setAutoCommit(false);
```

Perform the operations:

```java
insertPs.executeUpdate();
updatePs.executeUpdate();
```

Then:

```java
con.commit();
```

If something goes wrong:

```java
con.rollback();
```

Conceptually:

```text
BEGIN
  ↓
INSERT
  ↓
UPDATE
  ↓
Everything successful?
  ├── YES → COMMIT
  └── NO  → ROLLBACK
```

This connects CRUD with the `Connection` transaction methods you studied earlier.

---

# 8. CRUD with try-with-resources

A good JDBC implementation should properly close resources.

Example:

```java
String sql =
    "INSERT INTO student(id, name, marks) " +
    "VALUES (?, ?, ?)";

try (PreparedStatement ps =
         con.prepareStatement(sql)) {

    ps.setInt(1, 104);
    ps.setString(2, "Basha");
    ps.setDouble(3, 88.0);

    int rows =
        ps.executeUpdate();

    System.out.println(
        rows + " row inserted"
    );
}
```

For a SELECT:

```java
String sql =
    "SELECT id, name, marks FROM student";

try (PreparedStatement ps =
         con.prepareStatement(sql);
     ResultSet rs =
         ps.executeQuery()) {

    while (rs.next()) {
        System.out.println(
            rs.getInt("id") + " " +
            rs.getString("name") + " " +
            rs.getDouble("marks")
        );
    }
}
```

Try-with-resources automatically closes the JDBC resources when the block finishes.

---

# 9. Complete CRUD Program Structure

A typical application may have methods such as:

```java
void insertStudent(...) { ... }

void selectStudents() { ... }

void updateStudent(...) { ... }

void deleteStudent(...) { ... }
```

Conceptually:

```text
Student Application
       │
       ├── createStudent()
       │       ↓
       │    INSERT
       │
       ├── getStudents()
       │       ↓
       │    SELECT
       │
       ├── updateStudent()
       │       ↓
       │    UPDATE
       │
       └── deleteStudent()
               ↓
             DELETE
```

This is the foundation of a typical JDBC DAO/repository layer.

---

# 10. CRUD Execution Flow

## INSERT

```text
Java
 ↓
Connection
 ↓
PreparedStatement
 ↓
setXXX()
 ↓
executeUpdate()
 ↓
Database
 ↓
affected rows
```

---

## SELECT

```text
Java
 ↓
Connection
 ↓
PreparedStatement
 ↓
setXXX()
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

## UPDATE

```text
Java
 ↓
Connection
 ↓
PreparedStatement
 ↓
setXXX()
 ↓
executeUpdate()
 ↓
Database
 ↓
affected rows
```

---

## DELETE

```text
Java
 ↓
Connection
 ↓
PreparedStatement
 ↓
setXXX()
 ↓
executeUpdate()
 ↓
Database
 ↓
affected rows
```

---

# 11. The Difference Between Database State and Result

This distinction is important.

### INSERT

Changes database state:

```text
Database
   ↓
new row
```

### UPDATE

Changes database state:

```text
Database
   ↓
modified row
```

### DELETE

Changes database state:

```text
Database
   ↓
removed row
```

### SELECT

Doesn't normally modify the table:

```text
Database
   ↓
data copied/retrieved
   ↓
ResultSet
```

Therefore:

```text
INSERT / UPDATE / DELETE
        ↓
      MODIFY
        ↓
executeUpdate()

SELECT
        ↓
      RETRIEVE
        ↓
executeQuery()
```

---

# 12. `executeUpdate()` Does Not Mean "Only UPDATE"

This is a very common beginner misconception.

The method:

```java
executeUpdate()
```

is **not exclusively for SQL's `UPDATE` command**.

It is commonly used for:

```text
INSERT
UPDATE
DELETE
```

because these operations modify data and return an affected-row count.

So:

```text
executeUpdate()
        ↓
"Execute a statement that updates/modifies database state"
```

not:

```text
executeUpdate()
        ↓
"Execute only UPDATE SQL"
```

---

# 13. `executeQuery()` Does Not Mean "Execute Any Query"

For normal JDBC CRUD teaching, remember:

```java
executeQuery()
```

is used for a query that returns a `ResultSet`, especially:

```sql
SELECT
```

Example:

```java
ResultSet rs =
    ps.executeQuery();
```

Whereas:

```java
int count =
    ps.executeUpdate();
```

returns an affected-row count for update-type operations.

---

# 14. SQL `WHERE` Is Extremely Important

For CRUD:

### INSERT

`WHERE` normally isn't used.

```sql
INSERT INTO student ...
```

### SELECT

`WHERE` filters rows:

```sql
SELECT * FROM student
WHERE id = ?;
```

### UPDATE

`WHERE` determines which rows are modified:

```sql
UPDATE student
SET marks = ?
WHERE id = ?;
```

### DELETE

`WHERE` determines which rows are removed:

```sql
DELETE FROM student
WHERE id = ?;
```

So:

```text
SELECT → WHERE = filter

UPDATE → WHERE = target

DELETE → WHERE = target
```

---

# 15. CRUD + PreparedStatement + ResultSet

The three major JDBC objects fit together like this:

```text
             Connection
                  │
        ┌─────────┴─────────┐
        ↓                   ↓
PreparedStatement       PreparedStatement
        │                   │
     INSERT/UPDATE/      SELECT
       DELETE               │
        │                   ↓
 executeUpdate()        executeQuery()
        │                   │
        ↓                   ↓
 affected rows          ResultSet
                            │
                         next()
                            │
                         getXXX()
```

---

# 16. Final DEEPDIVE Mental Model

Think of CRUD as four questions:

### ① INSERT

> **"I want to add new data."**

```text
INSERT
 ↓
executeUpdate()
 ↓
int
```

### ② SELECT

> **"I want to read data."**

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

### ③ UPDATE

> **"I want to change existing data."**

```text
UPDATE
 ↓
executeUpdate()
 ↓
int
```

### ④ DELETE

> **"I want to remove existing data."**

```text
DELETE
 ↓
executeUpdate()
 ↓
int
```

---

# 🔥 CRUD MASTER FORMULA

```text
                    CRUD
                      │
       ┌──────────────┼──────────────┐
       │              │              │
       ↓              ↓              ↓
    INSERT          SELECT         UPDATE
       │              │              │
       │              │              │
       ↓              ↓              ↓
executeUpdate()  executeQuery()  executeUpdate()
       │              │              │
       ↓              ↓              ↓
      int          ResultSet         int
                      │
                    next()
                      │
                    getXXX()

                    DELETE
                       │
                       ↓
                executeUpdate()
                       │
                       ↓
                      int
```

## 🧠 Five rules to remember

1. **INSERT = add a row**
2. **SELECT = retrieve rows**
3. **UPDATE = modify rows**
4. **DELETE = remove rows**
5. **INSERT/UPDATE/DELETE → `executeUpdate()`; SELECT → `executeQuery()` → `ResultSet`**

And for real JDBC code, **prefer `PreparedStatement` for parameterized CRUD**, use `WHERE` carefully for `UPDATE`/`DELETE`, and close JDBC resources with try-with-resources.
