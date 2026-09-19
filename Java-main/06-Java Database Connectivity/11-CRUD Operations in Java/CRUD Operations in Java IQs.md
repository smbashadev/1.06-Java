# 11. CRUD Operations in Java — DOUBTKILLER

This version is designed to eliminate the **common doubts, traps, interview questions, and confusing points** around:

```text
INSERT
SELECT
UPDATE
DELETE
```

We'll use:

```sql
CREATE TABLE student (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    marks DOUBLE
);
```

Example data:

```text
101  Ravi   85
102  Kumar  90
103  John   75
```

---

# 1. INSERT — DOUBTKILLER

## 1.1 What exactly does INSERT do?

`INSERT` adds a **new row** to a table.

```sql
INSERT INTO student(id, name, marks)
VALUES (104, 'Basha', 88);
```

Result:

```text
101 Ravi   85
102 Kumar  90
103 John   75
104 Basha  88   ← NEW ROW
```

### Don't confuse:

```text
INSERT ≠ UPDATE
```

`INSERT` creates a new row.

`UPDATE` changes an existing row.

---

## 1.2 Does INSERT create a table?

**No.**

This:

```sql
INSERT INTO student ...
```

adds data to an **already existing table**.

Creating a table is:

```sql
CREATE TABLE student (...);
```

So:

```text
CREATE TABLE → creates table structure

INSERT       → adds rows
```

---

## 1.3 JDBC INSERT

```java
String sql =
    "INSERT INTO student(id, name, marks) VALUES (?, ?, ?)";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 104);
ps.setString(2, "Basha");
ps.setDouble(3, 88.0);

int rows = ps.executeUpdate();
```

---

## 1.4 Why `executeUpdate()`?

Because INSERT changes the database.

```text
INSERT
  ↓
Database changes
  ↓
executeUpdate()
  ↓
int
```

So:

```java
int rows = ps.executeUpdate();
```

might produce:

```text
1
```

meaning one row was affected.

---

## 1.5 Does `executeUpdate()` mean only UPDATE?

**Absolutely not.**

This is one of the biggest JDBC beginner doubts.

```text
executeUpdate()
```

is commonly used for:

```text
INSERT
UPDATE
DELETE
```

It means, conceptually:

> Execute an operation that modifies database state and return the affected-row count.

Therefore:

```text
INSERT  → executeUpdate()
UPDATE  → executeUpdate()
DELETE  → executeUpdate()
```

---

## 1.6 Why not `executeQuery()` for INSERT?

Because:

```java
executeQuery()
```

is used for statements that return a `ResultSet`, especially `SELECT`.

INSERT normally gives:

```text
affected row count
```

not a `ResultSet`.

Therefore:

```text
INSERT
 ↓
executeUpdate()
 ↓
int
```

---

## 1.7 Why are there `?` symbols?

```sql
INSERT INTO student(id, name, marks)
VALUES (?, ?, ?);
```

The `?` symbols are **parameter placeholders**.

Then:

```java
ps.setInt(1, 104);
ps.setString(2, "Basha");
ps.setDouble(3, 88);
```

maps:

```text
?     ?       ?
↓     ↓       ↓
1     2       3
↓     ↓       ↓
104  Basha    88
```

---

## 1.8 Does parameter numbering start at 0?

**No.**

JDBC parameter indexes start at **1**.

Correct:

```java
ps.setInt(1, 104);
```

Incorrect:

```java
ps.setInt(0, 104);
```

Think:

```text
JDBC parameter numbering → 1, 2, 3, ...
```

---

## 1.9 What if the primary key already exists?

Suppose:

```text
101 Ravi 85
```

already exists.

Then:

```sql
INSERT INTO student
VALUES (101, 'Basha', 88);
```

may fail because `id` is a primary key.

JDBC will generally report the database error through:

```java
SQLException
```

Example:

```java
try {
    ps.executeUpdate();
} catch (SQLException e) {
    e.printStackTrace();
}
```

---

## 1.10 Does successful INSERT mean the transaction is permanently saved?

**Not necessarily.**

It depends on transaction handling and auto-commit.

With normal auto-commit behavior, each statement is generally committed automatically.

But if you do:

```java
con.setAutoCommit(false);
```

then:

```java
ps.executeUpdate();
```

doesn't by itself mean the transaction is permanently committed.

You need:

```java
con.commit();
```

or:

```java
con.rollback();
```

---

# 2. SELECT — DOUBTKILLER

## 2.1 What exactly does SELECT do?

`SELECT` retrieves data.

```sql
SELECT * FROM student;
```

It does not normally modify the table.

Think:

```text
SELECT = READ
```

---

## 2.2 Why does SELECT use `executeQuery()`?

Because the result of a SELECT is normally a set of rows.

JDBC represents those rows using:

```java
ResultSet
```

Therefore:

```java
ResultSet rs =
    ps.executeQuery();
```

Flow:

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

## 2.3 Does SELECT return an ArrayList?

**No.**

JDBC directly returns:

```java
ResultSet
```

It does not automatically return:

```java
ArrayList<Student>
```

If you want an `ArrayList<Student>`, your application can manually convert the `ResultSet` into objects.

---

## 2.4 What exactly is ResultSet?

`ResultSet` represents the data returned by a query.

For:

```sql
SELECT * FROM student;
```

the database might return:

```text
101 Ravi  85
102 Kumar 90
103 John  75
```

JDBC makes those results available through:

```java
ResultSet rs
```

---

## 2.5 Why do we need `next()`?

A `ResultSet` has a cursor.

Initially:

```text
BEFORE FIRST ROW
```

Then:

```java
rs.next();
```

moves to the first row.

Another:

```java
rs.next();
```

moves to the second row.

Conceptually:

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

Therefore:

```java
while (rs.next()) {
    ...
}
```

is the standard pattern for processing multiple rows.

---

## 2.6 Why `while`, not `if`?

If your query can return multiple rows:

```java
while (rs.next()) {
    ...
}
```

is appropriate.

If you're expecting at most one row:

```java
if (rs.next()) {
    ...
}
```

can be appropriate.

Example:

```sql
SELECT * FROM student;
```

Potentially many rows:

```java
while (rs.next()) { }
```

Whereas:

```sql
SELECT * FROM student WHERE id = ?;
```

with a primary-key condition can return at most one row:

```java
if (rs.next()) { }
```

---

## 2.7 What happens if SELECT finds no rows?

Then:

```java
rs.next()
```

returns:

```text
false
```

So:

```java
if (rs.next()) {
    // found
} else {
    // not found
}
```

is a useful pattern for a single expected row.

---

## 2.8 What are `getInt()`, `getString()`, etc.?

They retrieve column values from the current row.

Example:

```java
int id =
    rs.getInt("id");

String name =
    rs.getString("name");

double marks =
    rs.getDouble("marks");
```

Think:

```text
Database type        Getter
----------------------------
INT                → getInt()
VARCHAR            → getString()
DOUBLE             → getDouble()
```

---

## 2.9 Can I use column indexes instead of names?

Yes.

For example:

```java
rs.getInt(1);
rs.getString(2);
rs.getDouble(3);
```

assuming the SELECT result columns are ordered appropriately.

You can also use labels:

```java
rs.getInt("id");
rs.getString("name");
```

Using column names is often easier to read and maintain.

---

## 2.10 What does `SELECT *` mean?

```sql
SELECT * FROM student;
```

means:

> Select all columns from `student`.

But in application code, it's often better to explicitly specify needed columns:

```sql
SELECT id, name, marks
FROM student;
```

Why?

Because explicit columns make the query's intent clearer and avoid unnecessarily retrieving columns you don't need.

---

# 3. UPDATE — DOUBTKILLER

## 3.1 What exactly does UPDATE do?

`UPDATE` changes values in **existing rows**.

Example:

```text
Before:
101 Ravi 85

After:
101 Ravi 95
```

SQL:

```sql
UPDATE student
SET marks = 95
WHERE id = 101;
```

---

## 3.2 Does UPDATE create a new row?

**No.**

This is important.

```text
INSERT → creates/adds row

UPDATE → changes existing row
```

---

## 3.3 JDBC UPDATE

```java
String sql =
    "UPDATE student SET marks = ? WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setDouble(1, 95);
ps.setInt(2, 101);

int rows =
    ps.executeUpdate();
```

---

## 3.4 What does the returned `int` mean?

Suppose:

```java
int rows = ps.executeUpdate();
```

If:

```text
rows = 1
```

one row was affected.

If:

```text
rows = 0
```

no row matched the statement's criteria.

If multiple rows match:

```text
rows = multiple
```

depending on the database and statement.

---

## 3.5 What happens if UPDATE has no WHERE?

Consider:

```sql
UPDATE student
SET marks = 95;
```

This can update **every row**.

For example:

```text
101 Ravi   85 → 95
102 Kumar  90 → 95
103 John   75 → 95
```

This is one of the most dangerous JDBC/SQL mistakes.

---

## 3.6 Is WHERE mandatory for UPDATE?

**SQL syntax does not require `WHERE`.**

But if your intention is to update only particular rows, you normally need an appropriate `WHERE` clause.

```sql
UPDATE student
SET marks = ?
WHERE id = ?;
```

The key question is:

> Which rows should be modified?

---

## 3.7 Can UPDATE modify multiple columns?

Yes.

```sql
UPDATE student
SET name = ?, marks = ?
WHERE id = ?;
```

JDBC:

```java
ps.setString(1, "Basha");
ps.setDouble(2, 92);
ps.setInt(3, 101);
```

---

## 3.8 Can UPDATE modify multiple rows?

Yes.

For example:

```sql
UPDATE student
SET marks = marks + 5
WHERE marks < 80;
```

Every matching row can be affected.

That's why checking the `WHERE` condition is critical.

---

# 4. DELETE — DOUBTKILLER

## 4.1 What exactly does DELETE do?

`DELETE` removes rows.

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

---

## 4.2 Does DELETE delete the table?

**No.**

This is an important distinction.

```sql
DELETE FROM student;
```

deletes rows but leaves the table structure.

It does **not** mean:

```text
DROP TABLE student
```

Compare:

```text
DELETE → removes rows

DROP   → removes the table object/structure
```

---

## 4.3 JDBC DELETE

```java
String sql =
    "DELETE FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 103);

int rows =
    ps.executeUpdate();
```

Again:

```text
DELETE
 ↓
executeUpdate()
 ↓
int
```

---

## 4.4 What happens with DELETE without WHERE?

```sql
DELETE FROM student;
```

This can delete **all rows**.

The table still exists, but its rows are removed.

```text
Before:
101 Ravi
102 Kumar
103 John

DELETE FROM student;

After:
empty table
```

---

## 4.5 Is WHERE mandatory for DELETE?

No, SQL allows:

```sql
DELETE FROM student;
```

But if you intend to delete a particular row, use an appropriate condition:

```sql
DELETE FROM student
WHERE id = ?;
```

---

# 5. The Biggest JDBC CRUD Confusion

## ❓ `executeUpdate()` vs `executeQuery()`

Memorize this:

```text
                  JDBC
                   │
        ┌──────────┴──────────┐
        ↓                     ↓
  Modifies database       Retrieves rows
        │                     │
        ↓                     ↓
INSERT / UPDATE / DELETE    SELECT
        │                     │
        ↓                     ↓
executeUpdate()          executeQuery()
        │                     │
        ↓                     ↓
       int                ResultSet
```

### Therefore:

```java
// INSERT
ps.executeUpdate();

// SELECT
ps.executeQuery();

// UPDATE
ps.executeUpdate();

// DELETE
ps.executeUpdate();
```

---

# 6. Why Isn't SELECT Called `executeUpdate()`?

Because SELECT isn't normally modifying rows.

Think of the return value:

### `executeUpdate()`

Asks:

> **How many rows were affected?**

Returns:

```java
int
```

### `executeQuery()`

Asks:

> **What rows did the database return?**

Returns:

```java
ResultSet
```

This mental distinction is more useful than memorizing method names blindly.

---

# 7. Another Big Confusion: SQL UPDATE vs `executeUpdate()`

These are different concepts.

### SQL command:

```sql
UPDATE student ...
```

### JDBC method:

```java
executeUpdate()
```

The JDBC method is **not named after SQL UPDATE**.

That's why all three commonly use it:

```text
INSERT
UPDATE
DELETE
   ↓
executeUpdate()
```

---

# 8. PreparedStatement and CRUD

For CRUD involving values, the preferred pattern is usually:

```text
SQL with ?
      ↓
PreparedStatement
      ↓
setXXX()
      ↓
execute...
```

Example:

```java
String sql =
    "UPDATE student SET marks = ? WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setDouble(1, 95);
ps.setInt(2, 101);

ps.executeUpdate();
```

---

# 9. SQL Injection Doubt

Suppose a user supplies:

```text
name = some user input
```

Avoid constructing SQL by blindly concatenating untrusted values:

```java
String sql =
    "SELECT * FROM student WHERE name = '" +
    name + "'";
```

Prefer:

```java
String sql =
    "SELECT * FROM student WHERE name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, name);
```

The parameter is bound separately from the SQL statement structure.

This is a major reason `PreparedStatement` is preferred for CRUD.

---

# 10. CRUD + Transactions

Suppose you perform:

```text
INSERT
UPDATE
DELETE
```

and all three operations must succeed together.

Use transaction control:

```java
con.setAutoCommit(false);
```

Then:

```java
insertPs.executeUpdate();
updatePs.executeUpdate();
deletePs.executeUpdate();
```

If everything succeeds:

```java
con.commit();
```

If something fails:

```java
con.rollback();
```

Conceptually:

```text
START
  ↓
INSERT
  ↓
UPDATE
  ↓
DELETE
  ↓
Everything successful?
   ↙             ↘
 YES             NO
  ↓               ↓
COMMIT         ROLLBACK
```

---

# 11. CRUD + try-with-resources

For JDBC resources:

```java
try (PreparedStatement ps =
         con.prepareStatement(sql)) {

    ...
}
```

For SELECT:

```java
try (PreparedStatement ps =
         con.prepareStatement(sql);
     ResultSet rs =
         ps.executeQuery()) {

    while (rs.next()) {
        ...
    }
}
```

This ensures the JDBC resources are closed automatically when the block exits.

---

# 12. INSERT vs UPDATE — Killer Difference

Suppose:

```text
Student 101 already exists.
```

You want to change the marks.

Should you use:

```sql
INSERT
```

or:

```sql
UPDATE
```

Answer:

```text
UPDATE
```

Because the row already exists.

---

### INSERT

```text
Does the new row need to be added?
         ↓
       YES
         ↓
      INSERT
```

### UPDATE

```text
Does an existing row need modification?
         ↓
       YES
         ↓
      UPDATE
```

---

# 13. UPDATE vs DELETE — Killer Difference

Suppose:

```text
101 Ravi 85
```

You want to change:

```text
85 → 95
```

Use:

```text
UPDATE
```

You want Ravi's row completely removed?

Use:

```text
DELETE
```

```text
Change data  → UPDATE

Remove row   → DELETE
```

---

# 14. DELETE vs DROP — Killer Difference

### DELETE

```sql
DELETE FROM student;
```

Removes rows.

The table remains.

### DROP

```sql
DROP TABLE student;
```

Removes the table itself.

Conceptually:

```text
DELETE
 ↓
Rows disappear
 ↓
Table remains


DROP
 ↓
Table itself disappears
```

---

# 15. DELETE vs TRUNCATE

Another common interview doubt.

### DELETE

```sql
DELETE FROM student;
```

Removes rows and can be used with a `WHERE` condition:

```sql
DELETE FROM student
WHERE id = 101;
```

### TRUNCATE

```sql
TRUNCATE TABLE student;
```

is generally used to remove all rows from a table as a bulk operation, with database-specific transactional/identity behavior.

Most importantly:

```text
DELETE → can target selected rows with WHERE

TRUNCATE → removes all rows; no WHERE
```

Don't treat `DELETE`, `TRUNCATE`, and `DROP` as the same thing.

---

# 16. What Does "Affected Rows" Mean?

Suppose:

```sql
UPDATE student
SET marks = 100
WHERE id = 101;
```

and student `101` exists.

Then:

```java
int count = ps.executeUpdate();
```

may give:

```text
1
```

Now suppose:

```sql
UPDATE student
SET marks = 100
WHERE id = 999;
```

and `999` doesn't match any row.

Then:

```text
count = 0
```

So the returned `int` is useful for checking whether your intended operation matched rows.

---

# 17. What If UPDATE Matches a Row but the Value Doesn't Change?

Suppose:

```text
101 Ravi 95
```

and you execute:

```sql
UPDATE student
SET marks = 95
WHERE id = 101;
```

The exact affected-row semantics can depend on the database and JDBC driver, particularly when the new value equals the old value.

Therefore, don't build application logic on an assumption that "affected row count always means the value definitely changed."

The exact semantics should be checked against your database/driver when it matters.

---

# 18. What If DELETE Finds Nothing?

```sql
DELETE FROM student
WHERE id = 999;
```

If no row matches:

```java
int rows = ps.executeUpdate();
```

will normally indicate:

```text
0 affected rows
```

Your application can use that to report:

```text
Student not found.
```

---

# 19. What If SELECT Finds Nothing?

For:

```java
ResultSet rs = ps.executeQuery();

if (rs.next()) {
    // found
} else {
    // not found
}
```

If there is no matching row:

```text
rs.next() → false
```

Notice the difference:

```text
UPDATE/DELETE → inspect returned int

SELECT        → inspect ResultSet using next()
```

---

# 20. Complete CRUD Table

| Operation | Purpose     | SQL      | JDBC method       | Main result        |
| --------- | ----------- | -------- | ----------------- | ------------------ |
| INSERT    | Add row     | `INSERT` | `executeUpdate()` | affected-row count |
| SELECT    | Read rows   | `SELECT` | `executeQuery()`  | `ResultSet`        |
| UPDATE    | Modify rows | `UPDATE` | `executeUpdate()` | affected-row count |
| DELETE    | Remove rows | `DELETE` | `executeUpdate()` | affected-row count |

---

# 21. 🔥 DOUBTKILLER Rapid-Fire Questions

### Q1. Is CRUD a Java feature?

**No.**

CRUD is a general term for four fundamental data operations.

```text
Create
Read
Update
Delete
```

JDBC is one way Java applications perform these operations against relational databases.

---

### Q2. Is CRUD the same as JDBC?

**No.**

```text
CRUD = operations

JDBC = Java API/technology used to communicate with relational databases
```

JDBC can be used to implement CRUD.

---

### Q3. Which SQL performs Create?

```text
INSERT
```

---

### Q4. Which SQL performs Read?

```text
SELECT
```

---

### Q5. Which SQL performs Update?

```text
UPDATE
```

---

### Q6. Which SQL performs Delete?

```text
DELETE
```

---

### Q7. Which JDBC method is generally used for INSERT?

```java
executeUpdate()
```

---

### Q8. Which JDBC method is generally used for SELECT?

```java
executeQuery()
```

---

### Q9. Which JDBC method is generally used for UPDATE?

```java
executeUpdate()
```

---

### Q10. Which JDBC method is generally used for DELETE?

```java
executeUpdate()
```

---

### Q11. What does `executeUpdate()` return?

```java
int
```

The affected-row count, subject to JDBC/database semantics.

---

### Q12. What does `executeQuery()` return?

```java
ResultSet
```

---

### Q13. Does INSERT return a ResultSet?

Not normally. For ordinary INSERT execution, you use `executeUpdate()` and inspect the affected-row count. Generated keys are a separate JDBC feature.

---

### Q14. Does SELECT change the table?

Normally, no. It retrieves data.

---

### Q15. Does UPDATE create a new row?

No. It modifies matching existing rows.

---

### Q16. Does DELETE delete the table?

No. It deletes rows. `DROP TABLE` removes the table itself.

---

### Q17. Can UPDATE affect multiple rows?

Yes.

```sql
UPDATE student
SET marks = marks + 5
WHERE marks < 80;
```

Every matching row may be affected.

---

### Q18. Can DELETE affect multiple rows?

Yes.

```sql
DELETE FROM student
WHERE marks < 40;
```

Every matching row may be deleted.

---

### Q19. Can SELECT return multiple rows?

Yes.

That's why:

```java
while (rs.next())
```

is commonly used.

---

### Q20. Why is `PreparedStatement` preferred?

For parameterized SQL, it provides parameter binding and helps protect against SQL injection while making repeated parameterized statements easier to manage.

---

# 22. 🔥 FINAL DOUBTKILLER MEMORY MAP

```text
                         CRUD
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ↓                ↓                ↓
       CREATE             READ            UPDATE
          ↓                ↓                ↓
       INSERT            SELECT          UPDATE
          ↓                ↓                ↓
 executeUpdate()      executeQuery()  executeUpdate()
          ↓                ↓                ↓
         int           ResultSet           int


                       DELETE
                          ↓
                       DELETE
                          ↓
                   executeUpdate()
                          ↓
                         int
```

## The ultimate memory trick:

```text
╔════════════════════════════════════════════╗
║ INSERT  = ADD                             ║
║ SELECT  = READ                            ║
║ UPDATE  = CHANGE                          ║
║ DELETE  = REMOVE                          ║
╠════════════════════════════════════════════╣
║ INSERT  → executeUpdate() → int           ║
║ SELECT  → executeQuery()  → ResultSet     ║
║ UPDATE  → executeUpdate() → int           ║
║ DELETE  → executeUpdate() → int           ║
╚════════════════════════════════════════════╝
```

### And the 3 biggest traps:

```text
❌ executeUpdate() is NOT only for SQL UPDATE.

❌ DELETE FROM table does NOT drop the table.

❌ UPDATE/DELETE without a proper WHERE can affect many or all rows.
```

If these distinctions are crystal clear, you've got the core JDBC CRUD model solid.
