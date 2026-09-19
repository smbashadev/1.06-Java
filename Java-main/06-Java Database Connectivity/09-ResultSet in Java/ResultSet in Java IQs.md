# 9. ResultSet in Java — DOUBTKILLER

This version is designed specifically to eliminate the **confusing points, traps, interview questions, and “why does this happen?” doubts** around `ResultSet`.

---

# 1. ResultSet Interface

## 1.1 What exactly is ResultSet?

`ResultSet` is an interface from:

```java
java.sql
```

It represents the **tabular data returned by a SQL query**, usually a `SELECT` query.

Example:

```java
ResultSet rs = ps.executeQuery();
```

Suppose SQL is:

```sql
SELECT id, name, marks FROM student;
```

Database returns:

```text
+-----+-------+-------+
| id  | name  | marks |
+-----+-------+-------+
| 101 | Ravi  | 85.5  |
| 102 | Kumar | 90.0  |
| 103 | Ali   | 78.5  |
+-----+-------+-------+
```

That returned result is accessed through `ResultSet`.

---

# 1.2 Is ResultSet a class?

❌ No.

It is an **interface**.

```java
ResultSet
```

belongs to:

```java
java.sql
```

You don't normally do:

```java
ResultSet rs = new ResultSet(); // ❌
```

Instead, JDBC's driver provides an implementation:

```java
ResultSet rs =
    ps.executeQuery();
```

### Doubt Killer

> **Who creates the ResultSet object?**

The JDBC driver/JDBC implementation creates and returns it.

You only hold the reference:

```java
ResultSet rs
```

---

# 1.3 Is ResultSet the database table?

❌ No.

This distinction is extremely important.

```text
Database table
      ↓
Permanent/stored data
```

while:

```text
ResultSet
      ↓
Result returned by a query
```

For example:

```sql
SELECT * FROM student;
```

doesn't turn the database table into a ResultSet.

Instead:

```text
student table
     ↓
SELECT
     ↓
database processes query
     ↓
returned result
     ↓
ResultSet
```

---

# 1.4 Is ResultSet a collection?

Not in the normal Java Collections Framework sense.

It is **not**:

```java
List
Set
Map
```

It is a JDBC interface representing a database query result and providing cursor-based access.

---

# 1.5 What does ResultSet actually contain?

Think of:

```text
ResultSet
    ↓
Rows + Columns
```

For example:

```text
Row 1 → 101 Ravi  85.5
Row 2 → 102 Kumar 90.0
Row 3 → 103 Ali   78.5
```

But there is another concept:

> **ResultSet has a cursor.**

---

# 1.6 The cursor — the biggest ResultSet concept

Initially:

```text
Cursor
  ↓
BEFORE FIRST

101 Ravi
102 Kumar
103 Ali
```

The cursor isn't initially sitting on row 101.

It is **before the first row**.

You must move it:

```java
rs.next();
```

Then:

```text
101 Ravi
 ↑
Cursor
```

---

# 1.7 ResultSet's biggest mental model

Remember:

```text
ResultSet
   ↓
Cursor
   ↓
next()
   ↓
Current row
   ↓
getXXX()
   ↓
Column value
```

This one model explains most of `ResultSet`.

---

# 2. `next()`

# 2.1 What does `next()` do?

It moves the cursor to the next row.

```java
rs.next();
```

Return type:

```java
boolean
```

Possible results:

```text
true
false
```

---

# 2.2 What does `true` mean?

It means:

> The cursor successfully moved to a valid row.

Example:

```java
if (rs.next()) {
    System.out.println("A row exists");
}
```

---

# 2.3 What does `false` mean?

It means:

> There is no next row.

For example, after the final row:

```java
rs.next()
```

returns:

```text
false
```

---

# 2.4 Why do we write `while(rs.next())`?

Because we want to process every row.

```java
while (rs.next()) {

    System.out.println(
        rs.getString("name")
    );
}
```

The process is:

```text
next()
 ↓
true → process row

next()
 ↓
true → process row

next()
 ↓
true → process row

next()
 ↓
false → stop
```

---

# 2.5 Doubt: Does `next()` retrieve the data?

❌ No.

This is one of the most common mistakes.

```java
rs.next();
```

means:

> **Move the cursor.**

It doesn't mean:

> Give me the name.

For the name:

```java
rs.getString("name");
```

Therefore:

```text
next()      → MOVE
getXXX()    → READ
```

🔥 Memorize this.

---

# 2.6 Doubt: Can I call getString() before next()?

Normally, no.

This is wrong:

```java
ResultSet rs = ps.executeQuery();

String name =
    rs.getString("name"); // ❌
```

The cursor is still:

```text
BEFORE FIRST
```

You need:

```java
rs.next();

String name =
    rs.getString("name"); // ✅
```

---

# 2.7 Doubt: What happens if I call next() twice?

Suppose:

```text
101 Ravi
102 Kumar
103 Ali
```

Initially:

```text
BEFORE FIRST
```

First:

```java
rs.next();
```

Cursor → Ravi.

Second:

```java
rs.next();
```

Cursor → Kumar.

It doesn't skip a row.

Each successful call advances **one row**.

---

# 2.8 Doubt: What happens after the last row?

Suppose cursor is at:

```text
103 Ali
```

Then:

```java
rs.next();
```

returns:

```text
false
```

The cursor moves beyond the last row.

Conceptually:

```text
101
 ↓
102
 ↓
103
 ↓
AFTER LAST
```

---

# 2.9 Can `next()` move backward?

For a default forward-only ResultSet:

❌ No.

For a scrollable ResultSet, other methods such as:

```java
previous()
first()
last()
absolute()
relative()
```

may be available.

That depends on the ResultSet type.

---

# 3. `getInt()`

# 3.1 What does getInt() do?

It reads a column as a Java `int`.

```java
int id =
    rs.getInt("id");
```

Example:

```text
Database:

id = 101

Java:

int id = 101;
```

---

# 3.2 Column name vs column index

You can write:

```java
rs.getInt("id");
```

or:

```java
rs.getInt(1);
```

If:

```text
1 → id
2 → name
3 → marks
```

then:

```java
rs.getInt(1);
```

means:

> Read the first column.

---

# 3.3 Doubt: Does JDBC column numbering start from 0?

❌ No.

JDBC column indexes start at:

```text
1
```

not:

```text
0
```

So:

```java
rs.getInt(1); // first column
```

not:

```java
rs.getInt(0); // ❌ invalid column index
```

🔥 This is a classic JDBC interview trap.

---

# 3.4 What if the database value is NULL?

Suppose:

```text
age = NULL
```

and:

```java
int age =
    rs.getInt("age");
```

`int` is a primitive and cannot contain `null`.

Therefore, you must use:

```java
rs.wasNull();
```

Example:

```java
int age =
    rs.getInt("age");

if (rs.wasNull()) {
    System.out.println("Age is SQL NULL");
}
```

---

# 3.5 Doubt: Is `0` the same as SQL NULL?

❌ Not necessarily.

Suppose database contains:

```text
age = 0
```

Then:

```java
int age = rs.getInt("age");
```

gives:

```text
0
```

Now suppose database contains:

```text
age = NULL
```

`getInt()` also gives the primitive default-like value `0`.

So:

```text
SQL 0
 ↓
getInt()
 ↓
0

SQL NULL
 ↓
getInt()
 ↓
0
```

How do you distinguish them?

```java
rs.wasNull();
```

That's why `wasNull()` exists.

---

# 3.6 Important `wasNull()` rule

It applies to the **most recently read column value**.

Example:

```java
int age = rs.getInt("age");

if (rs.wasNull()) {
    ...
}
```

Don't read another column first:

```java
int age = rs.getInt("age");

String name =
    rs.getString("name");

if (rs.wasNull()) {
    ...
}
```

Now `wasNull()` relates to the most recently retrieved value, not necessarily `age`.

---

# 4. `getString()`

# 4.1 What does getString() do?

It retrieves a column as a Java `String`.

```java
String name =
    rs.getString("name");
```

---

# 4.2 Column name

```java
String name =
    rs.getString("name");
```

---

# 4.3 Column index

If `name` is column 2:

```java
String name =
    rs.getString(2);
```

Again:

> JDBC indexes start at **1**.

---

# 4.4 What happens with SQL NULL?

Suppose:

```text
name = NULL
```

Then:

```java
String name =
    rs.getString("name");
```

can give:

```java
null
```

because `String` is a reference type.

So:

```java
if (name == null) {
    System.out.println("Name is NULL");
}
```

---

# 4.5 Doubt: Does getString() only work on VARCHAR?

❌ No.

JDBC drivers can convert compatible database values to a String representation.

For example, depending on the driver and SQL type, `getString()` can be used for various textual/numeric/date-like values.

But don't blindly depend on arbitrary conversions. Prefer a getter matching the intended Java type.

---

# 5. `getDouble()`

# 5.1 What does getDouble() do?

It reads a column as Java `double`.

```java
double marks =
    rs.getDouble("marks");
```

---

# 5.2 Example

Database:

```text
marks = 85.5
```

Java:

```java
double marks = 85.5;
```

---

# 5.3 Column index

If marks is column 3:

```java
double marks =
    rs.getDouble(3);
```

---

# 5.4 NULL issue

Like `getInt()`:

```java
double marks =
    rs.getDouble("marks");

if (rs.wasNull()) {
    System.out.println("Marks is NULL");
}
```

Why?

Because:

```text
double
```

is primitive and cannot contain:

```text
null
```

---

# 5.5 Doubt: Should money always use getDouble()?

❌ No.

For monetary/exact decimal values, `BigDecimal` is generally safer:

```java
BigDecimal amount =
    rs.getBigDecimal("amount");
```

Why?

Floating-point types can introduce representation/rounding issues.

So:

```text
Marks / approximate numeric calculation
        ↓
double can be appropriate

Money / exact decimal
        ↓
BigDecimal
```

---

# 6. `getObject()`

# 6.1 What is getObject()?

It retrieves a column as a Java `Object`.

```java
Object value =
    rs.getObject("name");
```

---

# 6.2 Why do we need getObject()?

Suppose you know:

```text
id → Integer
name → String
marks → Double
```

You can use:

```java
getInt()
getString()
getDouble()
```

But suppose you're writing a **generic database tool** and don't know the column type in advance.

Then:

```java
getObject()
```

is useful.

---

# 6.3 Conceptual conversion

```text
SQL value
    ↓
JDBC Driver
    ↓
appropriate Java representation
    ↓
Object
```

For example:

```text
INTEGER
   ↓
Integer

VARCHAR
   ↓
String

DECIMAL
   ↓
BigDecimal
```

Exact mappings can depend on the JDBC driver/database.

---

# 6.4 Typed getObject()

Modern JDBC also allows:

```java
String name =
    rs.getObject(
        "name",
        String.class
    );
```

Or:

```java
Integer id =
    rs.getObject(
        "id",
        Integer.class
    );
```

This lets you request a particular Java type.

---

# 6.5 Doubt: Is getObject() always better than getInt/getString?

❌ No.

If you already know the expected type:

```java
int id =
    rs.getInt("id");
```

is clearer than:

```java
Object id =
    rs.getObject("id");
```

Use:

```text
getInt()
getString()
getDouble()
...
```

when you know the type.

Use:

```text
getObject()
```

when generic/dynamic handling is useful.

---

# 7. ResultSet Types

This is where many students get confused.

First understand:

> **ResultSet TYPE is about cursor movement.**

Three standard types:

```java
TYPE_FORWARD_ONLY
TYPE_SCROLL_INSENSITIVE
TYPE_SCROLL_SENSITIVE
```

---

# 7.1 TYPE_FORWARD_ONLY

### Meaning

The cursor moves forward.

```text
BEFORE FIRST
     ↓
Row 1
     ↓
Row 2
     ↓
Row 3
     ↓
AFTER LAST
```

Typical usage:

```java
while (rs.next()) {
    ...
}
```

This is the straightforward and commonly used mode.

---

# 7.2 Doubt: Does FORWARD_ONLY mean I can only call `next()`?

Conceptually, yes for normal cursor navigation.

You shouldn't expect:

```java
rs.previous();
```

to work on a forward-only ResultSet.

If you need backward/positioned navigation, request a scrollable type.

---

# 7.3 TYPE_SCROLL_INSENSITIVE

Now the cursor can scroll.

You can potentially use:

```java
rs.first();
rs.last();
rs.previous();
rs.absolute(3);
rs.relative(-1);
```

Think:

```text
Row 1 ↔ Row 2 ↔ Row 3 ↔ Row 4
```

### What does "insensitive" mean?

The ResultSet generally doesn't reflect certain subsequent changes made to the underlying database after the result was created.

It does **not** mean:

> The database cannot be changed.

It means:

> The ResultSet is generally insensitive to those later changes.

---

# 7.4 TYPE_SCROLL_SENSITIVE

This is also scrollable.

But it is intended to be sensitive to certain changes in the underlying data.

Conceptually:

```text
Database changes
       ↓
ResultSet may reflect
certain changes
```

However:

> **Actual support depends on the JDBC driver and database.**

This is a very important interview point.

---

# 7.5 Doubt: If I request TYPE_SCROLL_SENSITIVE, am I guaranteed sensitivity?

❌ No.

You are requesting a capability.

The actual driver/database implementation determines what is supported.

You can inspect:

```java
rs.getType();
```

and:

```java
rs.getConcurrency();
```

---

# 7.6 How do I request a ResultSet type?

When creating the statement:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY
    );
```

Then:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

---

# 8. ResultSet Concurrency

Now forget cursor movement for a moment.

Concurrency answers:

> **Can the ResultSet be used only for reading, or can it be used to update rows?**

Two main values:

```java
CONCUR_READ_ONLY
CONCUR_UPDATABLE
```

---

# 8.1 CONCUR_READ_ONLY

Means:

> The ResultSet is intended for reading only.

Example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.CONCUR_READ_ONLY
    );
```

Then:

```java
while (rs.next()) {

    System.out.println(
        rs.getString("name")
    );
}
```

This is the common situation.

---

# 8.2 CONCUR_UPDATABLE

Means:

> Request a ResultSet that can potentially update rows.

Example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_UPDATABLE
    );
```

If supported:

```java
rs.next();

rs.updateString(
    "name",
    "Ravi Kumar"
);

rs.updateRow();
```

---

# 8.3 What does `updateString()` do?

This is another common doubt.

```java
rs.updateString(
    "name",
    "Ravi Kumar"
);
```

This prepares/changes the value in the **current ResultSet row**.

It doesn't necessarily mean the database is immediately updated at that exact line.

Then:

```java
rs.updateRow();
```

tells JDBC to write the updated row back to the database.

Conceptually:

```text
Current ResultSet row
        ↓
updateString()
        ↓
change pending in current row
        ↓
updateRow()
        ↓
database update
```

---

# 8.4 Doubt: Does CONCUR_UPDATABLE guarantee updating?

❌ No.

This is probably the most important concurrency doubt.

```java
CONCUR_UPDATABLE
```

means:

> **Request an updatable ResultSet.**

It does not mean:

> Every query is automatically updatable.

The JDBC driver, database, and SQL query must support the operation.

---

# 8.5 Why might a ResultSet not be updatable?

Some complex queries can prevent updatability.

For example:

```sql
SELECT s.name, d.department_name
FROM student s
JOIN department d
ON s.department_id = d.id;
```

Or queries involving:

```text
JOIN
GROUP BY
DISTINCT
aggregate functions
calculated expressions
```

may not be suitable for direct ResultSet updates.

Exact rules depend on the database/driver.

---

# 9. TYPE vs CONCURRENCY — Biggest Doubt Killer

This distinction must become automatic in your mind.

### TYPE

```text
TYPE = How can I MOVE?
```

Examples:

```text
FORWARD_ONLY
SCROLL_INSENSITIVE
SCROLL_SENSITIVE
```

### CONCURRENCY

```text
CONCURRENCY = Can I MODIFY?
```

Examples:

```text
READ_ONLY
UPDATABLE
```

Therefore:

```text
                 ResultSet
                     │
          ┌──────────┴──────────┐
          ↓                     ↓
        TYPE                CONCURRENCY
          ↓                     ↓
   Cursor movement        Update capability
          ↓                     ↓
 FORWARD_ONLY           READ_ONLY
 SCROLL_INSENSITIVE     UPDATABLE
 SCROLL_SENSITIVE
```

🔥 **Never confuse these.**

---

# 10. Major Doubt: Statement vs ResultSet

A `Statement` and `ResultSet` are not the same thing.

```text
Statement
    ↓
Executes SQL

ResultSet
    ↓
Represents result returned by SQL
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

So:

```text
Statement → asks database to execute

ResultSet → lets Java read returned rows
```

---

# 11. Major Doubt: executeQuery() vs ResultSet

`executeQuery()` is a **method**.

```java
ResultSet rs =
    ps.executeQuery();
```

`ResultSet` is an **interface/type**.

```text
executeQuery()
     ↓
method
     ↓
returns
     ↓
ResultSet
```

So don't say:

> "ResultSet executes the query."

❌ Incorrect.

The statement/prepared statement executes the query.

The ResultSet represents the returned result.

---

# 12. Major Doubt: ResultSet for INSERT/UPDATE/DELETE?

Normally:

```text
SELECT
   ↓
executeQuery()
   ↓
ResultSet
```

For:

```text
INSERT
UPDATE
DELETE
```

you normally use:

```java
executeUpdate();
```

which returns an update count:

```java
int count =
    ps.executeUpdate();
```

So the basic distinction is:

```text
SELECT
 ↓
executeQuery()
 ↓
ResultSet

INSERT/UPDATE/DELETE
 ↓
executeUpdate()
 ↓
int
```

---

# 13. Major Doubt: Can ResultSet contain multiple rows?

Absolutely.

Example:

```text
ResultSet
│
├── Row 1
├── Row 2
├── Row 3
├── Row 4
└── ...
```

You normally process them:

```java
while (rs.next()) {
    ...
}
```

---

# 14. Major Doubt: Can ResultSet contain zero rows?

Yes.

Suppose:

```sql
SELECT *
FROM student
WHERE id = 9999;
```

No student exists.

Then:

```java
ResultSet rs =
    ps.executeQuery();
```

still returns a ResultSet.

But it contains zero rows.

Then:

```java
rs.next();
```

returns:

```text
false
```

This is important:

> **No rows does not mean `executeQuery()` returns `null`.**

You generally receive a ResultSet representing an empty result.

---

# 15. Major Doubt: Does ResultSet automatically load the entire table into Java?

Don't think of it as:

```text
Database table
       ↓
Entire table copied into Java List
```

A ResultSet is a JDBC cursor-based interface over the query result.

Its exact buffering/fetch behavior is driver/database dependent.

Therefore, don't assume:

> "ResultSet always stores the entire result in JVM memory."

That is an implementation detail and may vary.

---

# 16. Major Doubt: Does ResultSet automatically update when database changes?

Usually, you should **not assume this**.

The behavior depends on the ResultSet type, driver, database, and concurrency.

This is especially important with:

```java
TYPE_SCROLL_INSENSITIVE
```

versus:

```java
TYPE_SCROLL_SENSITIVE
```

Even `TYPE_SCROLL_SENSITIVE` is not a promise that every external database change will magically appear in your ResultSet.

---

# 17. Major Doubt: ResultSet and Connection

A ResultSet is associated with JDBC resources such as the statement and connection that produced it.

Therefore, don't think of it as an independent object that can always live forever.

Typical lifecycle:

```text
Connection
   ↓
Statement / PreparedStatement
   ↓
ResultSet
   ↓
Read data
   ↓
Close ResultSet
   ↓
Close Statement
   ↓
Close Connection
```

Try-with-resources is preferred:

```java
try (
    PreparedStatement ps =
        con.prepareStatement(
            "SELECT id, name FROM student"
        );

    ResultSet rs =
        ps.executeQuery()
) {

    while (rs.next()) {
        System.out.println(
            rs.getString("name")
        );
    }
}
```

---

# 18. Complete Doubt-Killer Program

```java
import java.sql.*;

public class ResultSetDemo {

    public static void main(String[] args)
            throws Exception {

        Connection con =
            DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testdb",
                "root",
                "password"
            );

        PreparedStatement ps =
            con.prepareStatement(
                "SELECT id, name, marks " +
                "FROM student"
            );

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
                id + " " +
                name + " " +
                marks
            );
        }

        rs.close();
        ps.close();
        con.close();
    }
}
```

The mental execution:

```text
prepareStatement()
       ↓
PreparedStatement
       ↓
executeQuery()
       ↓
ResultSet
       ↓
cursor BEFORE FIRST
       ↓
next()
       ↓
Row 1
       ↓
getInt()
getString()
getDouble()
       ↓
next()
       ↓
Row 2
       ↓
getInt()
getString()
getDouble()
       ↓
...
       ↓
next() → false
       ↓
close()
```

---

# 19. All 8 Sub-concepts — Doubt Killer Summary

| Concept               | What it REALLY means                | Biggest trap                                |
| --------------------- | ----------------------------------- | ------------------------------------------- |
| `ResultSet`           | Represents rows returned by a query | It's an interface, not a table              |
| `next()`              | Moves cursor to next row            | It doesn't read column values               |
| `getInt()`            | Reads a value as `int`              | SQL `NULL` requires `wasNull()`             |
| `getString()`         | Reads a value as `String`           | SQL `NULL` can become Java `null`           |
| `getDouble()`         | Reads a value as `double`           | Don't blindly use for money                 |
| `getObject()`         | Retrieves value as Java object      | Not automatically better than typed getters |
| ResultSet Type        | Controls cursor movement            | Not the same as concurrency                 |
| ResultSet Concurrency | Controls update capability          | `CONCUR_UPDATABLE` isn't guaranteed         |

---

# 🔥 20. Ultimate Doubt-Killer Rules

### Rule 1

```java
rs.next();
```

means:

> **MOVE**

---

### Rule 2

```java
rs.getInt();
rs.getString();
rs.getDouble();
rs.getObject();
```

means:

> **READ**

---

### Rule 3

```text
TYPE
```

means:

> **CURSOR MOVEMENT**

---

### Rule 4

```text
CONCURRENCY
```

means:

> **UPDATE CAPABILITY**

---

### Rule 5

JDBC column indexes start at:

```text
1
```

not `0`.

---

### Rule 6

Primitive getters such as:

```java
getInt()
getDouble()
```

cannot directly represent Java `null`.

Use:

```java
wasNull()
```

when SQL `NULL` matters.

---

### Rule 7

```text
SELECT
 ↓
executeQuery()
 ↓
ResultSet
```

while:

```text
INSERT / UPDATE / DELETE
 ↓
executeUpdate()
 ↓
int
```

---

### Rule 8

An empty SELECT result is still represented by a ResultSet.

```java
rs.next() == false
```

means there is no row to process.

---

### Rule 9

`CONCUR_UPDATABLE` means:

> **Request an updatable ResultSet.**

It does **not** guarantee that every query can be updated.

---

### Rule 10 — The ultimate memory line

> **ResultSet = rows, `next()` = move, `getXXX()` = read, TYPE = movement, CONCURRENCY = modification.**

If these five relationships are clear, you have the core of `ResultSet` completely under control.
