# 8. PreparedStatement in Java — TEACHME

Let's learn `PreparedStatement` **from zero**, as if this is your first time seeing it.

The goal is not just to memorize methods. By the end, you should understand:

```text
What is PreparedStatement?
        ↓
Why do we need it?
        ↓
How does ? work?
        ↓
How do we put values into ?
        ↓
How do we execute SQL?
        ↓
Why is it safer than Statement?
```

---

# 1. First: What Problem Are We Trying to Solve?

Suppose we have a database table:

```text
student
--------------------------------
id     name       marks
--------------------------------
101    Ravi       90
102    Raj        85
103    Kumar      95
```

Our Java program wants to find a student by ID.

Suppose the ID comes from a variable:

```java
int id = 101;
```

We want to execute:

```sql
SELECT * FROM student WHERE id = 101;
```

One way is to build the SQL ourselves.

```java
String sql =
    "SELECT * FROM student WHERE id = " + id;
```

Then:

```java
Statement st = con.createStatement();

ResultSet rs =
    st.executeQuery(sql);
```

This works.

But there is a **better way**, especially when values come from users or variables:

```java
PreparedStatement
```

---

# 2. What Is PreparedStatement?

`PreparedStatement` is a JDBC interface used to execute **parameterized SQL statements**.

It belongs to:

```java
java.sql.PreparedStatement
```

The relationship is:

```text
Statement
    ↑
PreparedStatement
```

So `PreparedStatement` is a more specialized form of `Statement`.

---

# 3. The Big Idea Behind PreparedStatement

Instead of putting the value directly into SQL:

```sql
SELECT * FROM student WHERE id = 101
```

we write:

```sql
SELECT * FROM student WHERE id = ?
```

The `?` means:

> "I will provide this value later."

So:

```text
SQL:
SELECT * FROM student WHERE id = ?
                              ↑
                         placeholder
```

Then Java supplies:

```java
ps.setInt(1, 101);
```

Think of it like filling a blank:

```text
SELECT * FROM student WHERE id = ___
                              ↑
                              101
```

That's the central idea of `PreparedStatement`.

---

# 4. Why Is It Called "Prepared"?

Because we first give JDBC the **structure of the SQL statement**:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

Then we provide the actual values:

```java
ps.setInt(1, 101);
```

Then we execute:

```java
ResultSet rs =
    ps.executeQuery();
```

So remember this three-step pattern:

```text
1. Prepare
      ↓
2. Set parameters
      ↓
3. Execute
```

---

# 5. PreparedStatement Interface

Let's look at the interface itself.

```java
PreparedStatement ps;
```

We normally obtain an object from `Connection`:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

For example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);
```

Here:

```text
con
 ↓
prepareStatement()
 ↓
PreparedStatement object
```

---

# 6. Let's Understand the Whole Flow First

Before studying each method individually, see the complete picture:

```text
                 Java Program
                      |
                      ▼
                Connection
                      |
                      ▼
          prepareStatement(SQL)
                      |
                      ▼
             PreparedStatement
                      |
                      ▼
               ? parameters
                      |
             ┌────────┼────────┐
             ▼        ▼        ▼
          setInt   setString setDouble
             |        |        |
             └────────┼────────┘
                      ▼
                executeQuery()
                      |
                      ▼
                  ResultSet
```

For an update:

```text
PreparedStatement
       ↓
set parameters
       ↓
executeUpdate()
       ↓
int
```

Now let's learn each part.

---

# 7. Parameters

## What is a parameter?

A parameter is a value represented by `?` in SQL.

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";
```

The `?` is a parameter placeholder.

Then:

```java
ps.setInt(1, 101);
```

means:

```text
First ? = 101
```

---

# 8. Why Do We Use `?`?

Suppose we want to search for different students.

Without `PreparedStatement`, we might create different SQL strings:

```text
SELECT ... WHERE id = 101
SELECT ... WHERE id = 102
SELECT ... WHERE id = 103
```

With `PreparedStatement`, the SQL structure stays the same:

```text
SELECT ... WHERE id = ?
```

Only the value changes:

```text
? → 101
? → 102
? → 103
```

That's very useful.

---

# 9. Parameter Index Starts at 1

This is one of the most important JDBC rules.

Suppose:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";
```

There is one `?`.

Its index is:

```text
? → 1
```

So:

```java
ps.setInt(1, 101);
```

### NOT:

```java
ps.setInt(0, 101);   // Wrong
```

Java arrays normally start at 0.

But JDBC parameter indexes start at **1**.

---

# 10. Multiple Parameters

Suppose:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ?";
```

Visualize it:

```text
WHERE id = ? AND name = ?
          ↑              ↑
          1              2
```

Therefore:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

So:

```text
? #1 → 101
? #2 → Ravi
```

---

# 11. `setInt()`

Now let's study our first parameter-setting method.

## What does setInt() do?

`setInt()` puts an integer value into a parameter.

Example:

```java
ps.setInt(1, 101);
```

Think:

```text
setInt(parameter number, value)
```

So:

```java
ps.setInt(1, 101);
       │      │
       │      └── value
       └───────── parameter number
```

---

# 12. Example of setInt()

SQL:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";
```

Prepare:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Set:

```java
ps.setInt(1, 101);
```

Execute:

```java
ResultSet rs =
    ps.executeQuery();
```

The idea is:

```text
SELECT * FROM student WHERE id = ?
                                  ↓
                                101
```

---

# 13. `setString()`

Now suppose the parameter is a name.

SQL:

```java
String sql =
    "SELECT * FROM student WHERE name = ?";
```

Prepare:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Set the string:

```java
ps.setString(1, "Ravi");
```

Execute:

```java
ResultSet rs =
    ps.executeQuery();
```

---

# 14. Why Don't We Write Quotes Around `?`?

This is important.

### Correct:

```java
String sql =
    "SELECT * FROM student WHERE name = ?";
```

Then:

```java
ps.setString(1, "Ravi");
```

### Don't write:

```java
String sql =
    "SELECT * FROM student WHERE name = '?'";
```

The `?` is the parameter placeholder itself.

You don't manually put SQL string quotes around it.

---

# 15. `setDouble()`

Now suppose we want to search using marks.

```java
String sql =
    "SELECT * FROM student WHERE marks > ?";
```

Prepare:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Set the double:

```java
ps.setDouble(1, 80.5);
```

Execute:

```java
ResultSet rs =
    ps.executeQuery();
```

Conceptually:

```text
marks > ?
        ↓
marks > 80.5
```

---

# 16. Three Important Setter Methods

Remember:

```text
Java type        PreparedStatement method

int       →      setInt()
String    →      setString()
double    →      setDouble()
```

Example:

```java
ps.setInt(1, 101);

ps.setString(2, "Ravi");

ps.setDouble(3, 95.5);
```

There are many other setter methods too:

```text
setLong()
setFloat()
setBoolean()
setDate()
setTime()
setTimestamp()
setBigDecimal()
setBytes()
setObject()
```

But the principle is the same:

```text
parameter index + value
```

---

# 17. `executeQuery()`

Now we've prepared SQL and supplied the parameters.

How do we actually execute it?

For a query that returns rows, use:

```java
executeQuery()
```

Example:

```java
ResultSet rs =
    ps.executeQuery();
```

It returns:

```text
ResultSet
```

---

# 18. Why Does executeQuery() Return ResultSet?

Suppose we execute:

```sql
SELECT * FROM student;
```

The database may return:

```text
101  Ravi   90
102  Raj    85
103  Kumar  95
```

These rows need to be represented in Java.

JDBC uses:

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
read rows
```

---

# 19. Complete SELECT Example

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
        rs.getInt("id")
        + " "
        + rs.getString("name")
    );
}
```

Let's read this slowly.

### Step 1

```java
String sql =
    "SELECT * FROM student WHERE id = ?";
```

Create SQL with a placeholder.

### Step 2

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Prepare the SQL.

### Step 3

```java
ps.setInt(1, 101);
```

Put `101` into the first parameter.

### Step 4

```java
ResultSet rs =
    ps.executeQuery();
```

Execute the SELECT.

### Step 5

```java
while (rs.next())
```

Move through returned rows.

---

# 20. `executeUpdate()`

Now suppose we're not retrieving rows.

Suppose we want to change data.

Examples:

```text
INSERT
UPDATE
DELETE
```

We normally use:

```java
executeUpdate()
```

It returns:

```java
int
```

The `int` represents the number of affected rows for these typical DML operations.

---

# 21. UPDATE Example

Suppose we want to change Ravi's marks.

```java
String sql =
    "UPDATE student " +
    "SET marks = ? " +
    "WHERE id = ?";
```

Prepare:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Set parameters:

```java
ps.setDouble(1, 95.5);
ps.setInt(2, 101);
```

Execute:

```java
int count =
    ps.executeUpdate();
```

If one student was updated:

```text
count = 1
```

---

# 22. INSERT Example

```java
String sql =
    "INSERT INTO student " +
    "(id, name, marks) " +
    "VALUES (?, ?, ?)";
```

Prepare:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Set:

```java
ps.setInt(1, 104);
ps.setString(2, "Suresh");
ps.setDouble(3, 88.5);
```

Execute:

```java
int count =
    ps.executeUpdate();
```

---

# 23. DELETE Example

```java
String sql =
    "DELETE FROM student WHERE id = ?";
```

Prepare:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Set:

```java
ps.setInt(1, 104);
```

Execute:

```java
int count =
    ps.executeUpdate();
```

---

# 24. Remember executeQuery vs executeUpdate

This is an extremely important exam/interview point.

```text
                 PreparedStatement
                        |
              ┌─────────┴─────────┐
              |                   |
              ▼                   ▼
       executeQuery()      executeUpdate()
              |                   |
              ▼                   ▼
          ResultSet               int
```

Usually:

```text
SELECT
 ↓
executeQuery()
```

and:

```text
INSERT
UPDATE
DELETE
 ↓
executeUpdate()
```

---

# 25. SQL Injection

Now we reach one of the biggest reasons you should understand `PreparedStatement`.

Imagine we have a login system.

The user enters:

```text
username
password
```

A beginner might create SQL using string concatenation:

```java
String sql =
    "SELECT * FROM users " +
    "WHERE username = '" + username + "'" +
    " AND password = '" + password + "'";
```

This is dangerous because **user input becomes part of the SQL syntax**.

---

# 26. The Problem With Concatenation

The fundamental problem is:

```text
User input
    ↓
String concatenation
    ↓
SQL statement
```

The program is allowing external data to become part of the SQL command.

If specially crafted input changes the SQL structure, the database may execute something different from what the programmer intended.

That's **SQL injection**.

---

# 27. PreparedStatement Solution

Instead:

```java
String sql =
    "SELECT * FROM users " +
    "WHERE username = ? " +
    "AND password = ?";
```

Prepare:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Set values:

```java
ps.setString(1, username);
ps.setString(2, password);
```

Execute:

```java
ResultSet rs =
    ps.executeQuery();
```

The SQL structure remains separate from the supplied values.

---

# 28. Think of PreparedStatement Like a Form

This analogy is useful.

Imagine a form:

```text
Name:  __________
Age:   __________
City:  __________
```

The form defines the **structure**.

The person only fills in the **values**.

PreparedStatement works similarly:

```text
SQL:
SELECT * FROM student
WHERE id = ? AND name = ?
                 ↑        ↑
               blank    blank
```

Then:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

You're filling the blanks.

You aren't rewriting the SQL structure.

---

# 29. Why PreparedStatement Helps Prevent SQL Injection

With string concatenation:

```text
SQL + user input
      ↓
one combined SQL command
```

With parameter binding:

```text
SQL structure
      +
parameter values
      ↓
JDBC driver
      ↓
database
```

The values are handled as parameter data rather than being treated as arbitrary SQL syntax.

That is why parameterized `PreparedStatement` usage is the standard JDBC defense against SQL injection through values.

---

# 30. Important Limitation of `?`

This causes a lot of confusion.

Suppose you write:

```java
String sql =
    "SELECT * FROM student ORDER BY ?";
```

Then:

```java
ps.setString(1, "name");
```

You should **not** think that `?` can replace any piece of SQL.

The parameter placeholder represents a **value**, not arbitrary SQL syntax such as:

```text
table names
column names
SQL keywords
ORDER BY clauses
```

So remember:

```text
? = value
```

not:

```text
? = arbitrary SQL code
```

---

# 31. Statement vs PreparedStatement

Let's compare them using a real example.

## Statement

```java
int id = 101;

String sql =
    "SELECT * FROM student WHERE id = " + id;

Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(sql);
```

Here the Java program creates the complete SQL string.

---

## PreparedStatement

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();
```

Here:

```text
SQL structure → fixed
value          → supplied separately
```

---

# 32. Easy Analogy: Letter vs Form

Think about sending information.

### Statement

You write a new complete letter every time:

```text
Dear Ravi, your ID is 101...
```

Then another:

```text
Dear Raj, your ID is 102...
```

The whole content changes.

### PreparedStatement

You create a form:

```text
Dear __________,
your ID is ________.
```

Then fill the blanks.

```text
Name → Ravi
ID   → 101
```

The structure stays the same.

That's the basic idea of parameterized SQL.

---

# 33. Why PreparedStatement Is Usually Preferred

When SQL contains variable values, `PreparedStatement` provides several advantages:

### 1. Security

Parameter binding helps prevent SQL injection.

### 2. Cleaner code

Instead of:

```java
"... WHERE id = " + id
```

you write:

```java
"... WHERE id = ?"
```

and:

```java
ps.setInt(1, id);
```

### 3. Type-aware binding

You explicitly say:

```java
setInt()
setString()
setDouble()
```

### 4. Reusability

The same prepared SQL structure can be executed with different values.

### 5. Potential performance benefits

Repeated execution of the same SQL structure can benefit from preparation/reuse depending on the JDBC driver and database.

Don't interpret this as "PreparedStatement is always faster."

---

# 34. Reusing a PreparedStatement

Suppose:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);
```

First student:

```java
ps.setInt(1, 101);

ResultSet rs1 =
    ps.executeQuery();
```

After processing/closing `rs1`, we can change the parameter:

```java
ps.setInt(1, 102);

ResultSet rs2 =
    ps.executeQuery();
```

Same SQL:

```text
SELECT * FROM student WHERE id = ?
```

Different values:

```text
101
102
```

This is very useful.

---

# 35. One Very Important Distinction

Students often think:

```java
ps.setInt(1, 101);
```

means:

> "Execute the SQL with 101."

No.

It only means:

> "Set parameter 1 to 101."

Execution happens separately.

```text
setInt()
   ↓
bind value

executeQuery()
   ↓
execute SELECT
```

Likewise:

```java
con.prepareStatement(sql);
```

does not mean:

> "Run the SQL."

It prepares/creates the statement object.

---

# 36. Complete Mental Model

Imagine this SQL:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ? " +
    "AND marks > ?";
```

There are three parameters:

```text
?       ?       ?
↑       ↑       ↑
1       2       3
```

Now:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Then:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
ps.setDouble(3, 80.0);
```

Now:

```text
Parameter 1 → 101
Parameter 2 → Ravi
Parameter 3 → 80.0
```

Then:

```java
ResultSet rs =
    ps.executeQuery();
```

The complete conceptual flow is:

```text
                    SQL
                     ↓
        SELECT ... WHERE id = ?
                     ↓
            prepareStatement()
                     ↓
             PreparedStatement
                     ↓
              Bind parameters
                     ↓
          ┌──────────┼──────────┐
          ▼          ▼          ▼
       setInt    setString   setDouble
          │          │          │
          └──────────┼──────────┘
                     ▼
              executeQuery()
                     ↓
                 ResultSet
```

---

# 37. Full Example From Beginning to End

Suppose we already have a `Connection` called `con`.

```java
String sql =
    "SELECT id, name, marks " +
    "FROM student " +
    "WHERE id = ? " +
    "AND name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);
ps.setString(2, "Ravi");

ResultSet rs =
    ps.executeQuery();

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

Let's translate this into English:

> "Prepare this SQL. It needs two values. Put 101 into the first parameter. Put Ravi into the second parameter. Execute the query. Give me the returned rows."

That's exactly what the code does.

---

# 38. Complete INSERT Example

```java
String sql =
    "INSERT INTO student " +
    "(id, name, marks) " +
    "VALUES (?, ?, ?)";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 105);
ps.setString(2, "Anil");
ps.setDouble(3, 91.5);

int count =
    ps.executeUpdate();

System.out.println(
    "Rows inserted: " + count
);
```

Think:

```text
INSERT
  ↓
3 parameters
  ↓
setInt()
setString()
setDouble()
  ↓
executeUpdate()
  ↓
affected row count
```

---

# 39. Complete UPDATE Example

```java
String sql =
    "UPDATE student " +
    "SET name = ?, marks = ? " +
    "WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, "Anil");
ps.setDouble(2, 96.0);
ps.setInt(3, 105);

int count =
    ps.executeUpdate();
```

Parameters:

```text
? #1 → Anil
? #2 → 96.0
? #3 → 105
```

---

# 40. Complete DELETE Example

```java
String sql =
    "DELETE FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 105);

int count =
    ps.executeUpdate();
```

---

# 41. Common Beginner Mistakes

## Mistake 1 — Starting parameter index at 0

❌

```java
ps.setInt(0, 101);
```

✅

```java
ps.setInt(1, 101);
```

---

## Mistake 2 — Putting quotes around `?`

❌

```java
"WHERE name = '?'"
```

✅

```java
"WHERE name = ?"
```

---

## Mistake 3 — Passing SQL again to executeQuery()

With `PreparedStatement`, normally:

❌

```java
ps.executeQuery(sql);
```

when you've already prepared that SQL.

✅

```java
ps.executeQuery();
```

---

## Mistake 4 — Thinking setInt() executes SQL

❌

```text
setInt() = execution
```

✅

```text
setInt() = parameter binding
executeQuery()/executeUpdate() = execution
```

---

## Mistake 5 — Using executeQuery() for UPDATE

❌

```java
ps.executeQuery();
```

for an ordinary `UPDATE`.

✅

```java
ps.executeUpdate();
```

---

# 42. Quick Comparison Table

| Concept              | Meaning                                                     |
| -------------------- | ----------------------------------------------------------- |
| `PreparedStatement`  | Interface for parameterized SQL                             |
| `?`                  | Parameter placeholder                                       |
| `prepareStatement()` | Creates/prepares the prepared statement                     |
| `setInt()`           | Binds an integer                                            |
| `setString()`        | Binds a String                                              |
| `setDouble()`        | Binds a double                                              |
| `executeQuery()`     | Executes a query and returns `ResultSet`                    |
| `executeUpdate()`    | Executes an update operation and returns affected-row count |
| Parameter index      | Starts at 1                                                 |
| SQL injection        | Attack involving unintended SQL through untrusted input     |
| PreparedStatement    | Standard JDBC approach for parameterized values             |

---

# 43. Statement vs PreparedStatement — Final Picture

```text
STATEMENT
─────────

Java variable
     ↓
String concatenation
     ↓
Complete SQL string
     ↓
Statement
     ↓
Database
```

Potential problem:

```text
User input
    ↓
String concatenation
    ↓
SQL syntax
    ↓
SQL Injection risk
```

---

```text
PREPAREDSTATEMENT
─────────────────

SQL structure
     ↓
   ?
   ?
   ?
     ↓
prepareStatement()
     ↓
PreparedStatement
     ↓
setInt()
setString()
setDouble()
     ↓
execute
     ↓
Database
```

The SQL structure and parameter values are handled separately.

---

# 🧠 Final Memory Trick

Remember **P-S-E**:

### **P → Prepare**

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

### **S → Set**

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
ps.setDouble(3, 90.5);
```

### **E → Execute**

For retrieving rows:

```java
ResultSet rs =
    ps.executeQuery();
```

For modifying rows:

```java
int count =
    ps.executeUpdate();
```

So the complete JDBC PreparedStatement formula is:

```text
             PREPARE
                ↓
          SQL + ? + ? + ?
                ↓
               SET
                ↓
      setInt / setString /
          setDouble / ...
                ↓
             EXECUTE
            ↙        ↘
   executeQuery   executeUpdate
        ↓               ↓
   ResultSet            int
```

And the **one sentence you should never forget**:

> **PreparedStatement lets us write SQL with `?` placeholders, bind actual values using `setXXX()` methods, and then execute the SQL safely without constructing SQL by concatenating parameter values.**
