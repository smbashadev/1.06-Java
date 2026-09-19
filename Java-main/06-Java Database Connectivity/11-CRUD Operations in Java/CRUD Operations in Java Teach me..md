# 11. CRUD Operations in Java — TEACHME

Let's learn **CRUD in JDBC from zero**, as if we're building a small student-management application.

CRUD is not a separate JDBC API. It is the name we give to the **four basic operations we perform on database data**.

```text
C → Create → INSERT
R → Read   → SELECT
U → Update → UPDATE
D → Delete → DELETE
```

We'll use one table throughout:

```text
student
---------------------------
id    name       marks
---------------------------
101   Ravi       85
102   Kumar      90
103   John       75
```

---

# 1. First Understand the Big Picture

Imagine your Java program wants to work with the database.

It doesn't directly jump into the database.

The basic JDBC flow is:

```text
Java Program
     ↓
Connection
     ↓
PreparedStatement
     ↓
SQL
     ↓
Database
```

For `SELECT`, there is one additional important object:

```text
Database
    ↓
ResultSet
    ↓
Java Program
```

So CRUD becomes:

```text
INSERT  → Send new data
SELECT  → Receive existing data
UPDATE  → Change existing data
DELETE  → Remove existing data
```

---

# 2. CREATE → INSERT

## 🟢 First: What does CREATE mean?

In CRUD, **Create** means:

> Add new data to the database.

The SQL command used is:

```sql
INSERT
```

For example, we want to add:

```text
104  Basha  88
```

SQL:

```sql
INSERT INTO student(id, name, marks)
VALUES (104, 'Basha', 88);
```

After executing it:

```text
student
---------------------------
101   Ravi       85
102   Kumar      90
103   John       75
104   Basha      88
```

A new row has been created.

---

# 3. INSERT Using JDBC

Instead of putting values directly into SQL, we'll use `PreparedStatement`.

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

Let's understand this **line by line**.

---

## Step 1 — Write SQL

```java
String sql =
    "INSERT INTO student(id, name, marks) VALUES (?, ?, ?)";
```

The `?` symbols are placeholders.

```text
INSERT INTO student
       (id,   name,  marks)
        ↓      ↓      ↓
       (?)    (?)    (?)
```

---

## Step 2 — Create PreparedStatement

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Remember:

```text
Connection
    ↓
prepareStatement()
    ↓
PreparedStatement
```

---

## Step 3 — Put values into `?`

```java
ps.setInt(1, 104);
ps.setString(2, "Basha");
ps.setDouble(3, 88.0);
```

The indexes start at **1**, not 0.

```text
?       ?       ?
↓       ↓       ↓
1       2       3
↓       ↓       ↓
104   Basha    88
```

So:

```java
setInt(1, 104)
```

means:

> Put `104` into parameter 1.

---

## Step 4 — Execute INSERT

```java
int rows = ps.executeUpdate();
```

Why `executeUpdate()`?

Because `INSERT` **changes the database**.

```text
INSERT
  ↓
Database changes
  ↓
executeUpdate()
```

The method returns an `int`.

For one inserted row:

```text
1
```

---

# 4. Why Not `executeQuery()` for INSERT?

This is a very common beginner doubt.

`executeQuery()` is normally used when you want a **result set**, especially with `SELECT`.

```text
SELECT
  ↓
executeQuery()
  ↓
ResultSet
```

But `INSERT` doesn't normally give you a `ResultSet` containing the inserted table rows.

It gives you an affected-row count:

```text
INSERT
  ↓
executeUpdate()
  ↓
int
```

So remember:

```text
INSERT → executeUpdate()
```

---

# 5. READ → SELECT

Now suppose we want to **see the students**.

CRUD calls this **Read**.

The SQL command is:

```sql
SELECT
```

Example:

```sql
SELECT * FROM student;
```

The database might return:

```text
101 Ravi  85
102 Kumar 90
103 John  75
```

---

# 6. SELECT Using JDBC

```java
String sql =
    "SELECT * FROM student";

PreparedStatement ps =
    con.prepareStatement(sql);

ResultSet rs =
    ps.executeQuery();
```

Now something new appears:

```text
ResultSet
```

---

# 7. What Is ResultSet?

Think of `ResultSet` as a **Java-side representation of the rows returned by a SELECT query**.

Imagine the database gives Java:

```text
101 Ravi  85
102 Kumar 90
103 John  75
```

JDBC puts those results into:

```text
ResultSet
```

Conceptually:

```text
Database
   ↓
SELECT
   ↓
ResultSet
   ↓
Java
```

---

# 8. How Do We Read ResultSet?

We use:

```java
rs.next()
```

and:

```java
rs.getXXX()
```

Example:

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

# 9. Why `rs.next()`?

This is extremely important.

When a `ResultSet` is created, its cursor starts **before the first row**.

Think of it like:

```text
          Cursor
             ↓
        ┌─────────┐
        │ Row 101 │
        ├─────────┤
        │ Row 102 │
        ├─────────┤
        │ Row 103 │
        └─────────┘
```

Initially:

```text
BEFORE FIRST ROW
```

Calling:

```java
rs.next()
```

moves it to the first row.

Another:

```java
rs.next()
```

moves to the second row.

And so on.

That's why we commonly write:

```java
while (rs.next()) {
    ...
}
```

---

# 10. Understanding `getInt()`, `getString()`, `getDouble()`

Suppose our columns are:

```text
id     → INT
name   → VARCHAR
marks  → DOUBLE
```

We use:

```java
rs.getInt("id");
rs.getString("name");
rs.getDouble("marks");
```

Think:

```text
Database column       Java getter
----------------------------------
INT                →  getInt()
VARCHAR            →  getString()
DOUBLE             →  getDouble()
```

---

# 11. SELECT With a Condition

We don't always want every student.

Suppose we want student `101`.

SQL:

```sql
SELECT *
FROM student
WHERE id = ?;
```

Java:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();

if (rs.next()) {

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

Notice something important:

For potentially multiple rows:

```java
while (rs.next())
```

For a query where you expect at most one matching row:

```java
if (rs.next())
```

can be appropriate.

---

# 12. Why Does SELECT Use `executeQuery()`?

Because SELECT retrieves data.

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

The key point is:

> `executeQuery()` returns a `ResultSet`.

---

# 13. UPDATE

Now imagine Ravi's marks change from:

```text
85
```

to:

```text
95
```

We don't want to create another row.

We want to **modify the existing row**.

That's CRUD's **Update** operation.

SQL:

```sql
UPDATE student
SET marks = 95
WHERE id = 101;
```

---

# 14. UPDATE Using JDBC

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

---

# 15. Understand the UPDATE Parameters

SQL:

```sql
UPDATE student
SET marks = ?
WHERE id = ?;
```

There are two `?` parameters:

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
marks = 95
WHERE id = 101
```

---

# 16. Why `executeUpdate()` for UPDATE?

Because the database is being changed.

```text
UPDATE
  ↓
Existing row modified
  ↓
executeUpdate()
  ↓
int
```

If one row was changed:

```text
1
```

If no row matched:

```text
0
```

If multiple rows matched:

```text
multiple affected rows
```

---

# 17. The Most Important UPDATE Danger ⚠️

Look at this:

```sql
UPDATE student
SET marks = 95;
```

Where is `WHERE`?

There isn't one.

That can update **every row**.

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

Now only rows matching `id = 101` are targeted.

### Teach-yourself rule:

> **Before executing UPDATE, always ask: "Which rows will my WHERE condition affect?"**

---

# 18. UPDATE Multiple Columns

You can change several columns at once.

```sql
UPDATE student
SET name = ?, marks = ?
WHERE id = ?;
```

Java:

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

Parameter positions:

```text
?       ?       ?
↓       ↓       ↓
1       2       3
↓       ↓       ↓
name   marks    id
```

---

# 19. DELETE

Now suppose we no longer need John (`id = 103`).

CRUD calls this **Delete**.

SQL:

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

The row has been removed.

---

# 20. DELETE Using JDBC

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

---

# 21. Why `executeUpdate()` for DELETE?

Because the database changes.

```text
DELETE
  ↓
Rows removed
  ↓
executeUpdate()
  ↓
int
```

Again, the returned integer is the number of affected rows.

---

# 22. The Most Important DELETE Danger ⚠️

Look at:

```sql
DELETE FROM student;
```

There is no `WHERE`.

That can delete **all rows** in the table.

Compare:

```sql
DELETE FROM student
WHERE id = ?;
```

This targets rows matching the condition.

So before executing DELETE:

> **Always check the WHERE condition.**

---

# 23. CRUD — Put Everything Together

Now let's see the complete picture.

## CREATE

```text
INSERT
  ↓
PreparedStatement
  ↓
setXXX()
  ↓
executeUpdate()
  ↓
int
```

---

## READ

```text
SELECT
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
UPDATE
  ↓
PreparedStatement
  ↓
setXXX()
  ↓
executeUpdate()
  ↓
int
```

---

## DELETE

```text
DELETE
  ↓
PreparedStatement
  ↓
setXXX()
  ↓
executeUpdate()
  ↓
int
```

---

# 24. The Most Important JDBC Rule

Memorize this:

```text
INSERT  → executeUpdate()
SELECT  → executeQuery()
UPDATE  → executeUpdate()
DELETE  → executeUpdate()
```

Or even shorter:

```text
I U D → executeUpdate()
S     → executeQuery()
```

---

# 25. Why Is `executeUpdate()` Used for Three Operations?

Don't let the name confuse you.

`executeUpdate()` does **not** mean:

> "Execute only an UPDATE SQL command."

It is used for statements that modify database state, such as:

```text
INSERT
UPDATE
DELETE
```

It returns:

```text
affected row count
```

For example:

```java
int count = ps.executeUpdate();
```

If:

```text
1 row affected
```

then:

```text
count = 1
```

If:

```text
0 rows affected
```

then:

```text
count = 0
```

---

# 26. Why Is SELECT Different?

SELECT returns **data**, not simply an affected-row count.

So:

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

Think:

```text
executeUpdate()
       ↓
"How many rows changed?"

executeQuery()
       ↓
"Give me the rows."
```

That's an excellent way to remember the difference.

---

# 27. Why PreparedStatement Instead of Statement?

Suppose the user gives you:

```text
student ID = 101
```

You could construct SQL through string concatenation, but that's not the preferred approach.

Instead:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);
```

The SQL structure and the parameter value are handled separately.

This improves safety and is the standard approach for parameterized CRUD operations.

It also helps prevent **SQL injection** when values originate from users or other untrusted sources.

---

# 28. CRUD in a Real Student Application

Imagine you build:

```text
Student Management System
```

The user chooses:

```text
1. Add Student
2. View Students
3. Update Student
4. Delete Student
```

Your Java application might map these options like this:

```text
User selects 1
      ↓
INSERT

User selects 2
      ↓
SELECT

User selects 3
      ↓
UPDATE

User selects 4
      ↓
DELETE
```

That's CRUD.

---

# 29. A Complete Mental Example

Imagine the table initially contains:

```text
101 Ravi   85
102 Kumar  90
103 John   75
```

### Step 1 — CREATE

Add:

```text
104 Basha 88
```

Using:

```sql
INSERT
```

Now:

```text
101 Ravi   85
102 Kumar  90
103 John   75
104 Basha  88
```

---

### Step 2 — READ

Execute:

```sql
SELECT * FROM student;
```

Java receives the rows through:

```text
ResultSet
```

---

### Step 3 — UPDATE

Change Basha's marks:

```text
88 → 95
```

Using:

```sql
UPDATE
```

Now:

```text
104 Basha 95
```

---

### Step 4 — DELETE

Remove John:

```sql
DELETE FROM student
WHERE id = 103;
```

Now:

```text
101 Ravi   85
102 Kumar  90
104 Basha  95
```

You've just performed the complete CRUD cycle.

---

# 30. CRUD and Transactions

CRUD operations can also participate in transactions.

Suppose you want:

```text
INSERT student
+
UPDATE account
```

to succeed as one unit.

You can use:

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

If something fails:

```java
con.rollback();
```

Conceptually:

```text
Start Transaction
       ↓
    INSERT
       ↓
    UPDATE
       ↓
  Everything OK?
    ↙       ↘
  YES        NO
   ↓          ↓
COMMIT     ROLLBACK
```

This is especially important when several CRUD operations must succeed or fail together.

---

# 31. CRUD Resource Management

A proper JDBC program should close resources.

For example:

```java
String sql =
    "SELECT * FROM student";

try (PreparedStatement ps =
         con.prepareStatement(sql);
     ResultSet rs =
         ps.executeQuery()) {

    while (rs.next()) {
        System.out.println(
            rs.getInt("id") + " " +
            rs.getString("name")
        );
    }
}
```

Try-with-resources automatically closes the `PreparedStatement` and `ResultSet`.

The `Connection` should also be managed appropriately, usually by the surrounding application/service layer.

---

# 32. Final TEACHME Picture

Imagine a **student notebook**.

### INSERT

You write a **new student** into the notebook.

```text
INSERT = ADD
```

### SELECT

You **read students** from the notebook.

```text
SELECT = READ
```

### UPDATE

You **correct/change information** already written.

```text
UPDATE = CHANGE
```

### DELETE

You **remove a student's record**.

```text
DELETE = REMOVE
```

That's CRUD.

---

# 🧠 Final Memory Map

```text
                  CRUD
                    │
       ┌────────────┼────────────┐
       ↓            ↓            ↓
    CREATE        READ         UPDATE
       ↓            ↓            ↓
    INSERT        SELECT       UPDATE
       ↓            ↓            ↓
executeUpdate  executeQuery  executeUpdate
       ↓            ↓            ↓
      int       ResultSet        int


                 DELETE
                    ↓
                  DELETE
                    ↓
             executeUpdate()
                    ↓
                   int
```

## 🔥 The four sentences you should be able to say without thinking

> **INSERT adds new rows.**

> **SELECT retrieves existing rows.**

> **UPDATE modifies existing rows.**

> **DELETE removes existing rows.**

And in JDBC:

```text
INSERT  → executeUpdate()
SELECT  → executeQuery() → ResultSet
UPDATE  → executeUpdate()
DELETE  → executeUpdate()
```

Once this becomes automatic, **CRUD becomes the foundation on which JDBC DAO/repository programs are built.**
