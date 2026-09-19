# 9. ResultSet in Java — DEEPDIVE

`ResultSet` is the JDBC object through which Java reads the rows returned by a SQL query.

The complete mental model is:

```text
Java Application
      ↓
PreparedStatement / Statement
      ↓
executeQuery()
      ↓
ResultSet
      ↓
Cursor moves through rows
      ↓
getInt(), getString(), getDouble(), getObject()
      ↓
Java variables
```

We will study every sub-concept individually, including **cursor behavior, column indexes, type conversion, ResultSet types, concurrency, limitations, and common mistakes**.

---

# 1. ResultSet Interface

## 1.1 What is ResultSet?

`ResultSet` is an interface in the `java.sql` package.

```java
import java.sql.ResultSet;
```

It represents the result of executing a SQL statement that produces rows.

For example:

```sql
SELECT id, name, marks
FROM student;
```

Suppose the database returns:

```text
+-----+-------+-------+
| id  | name  | marks |
+-----+-------+-------+
| 101 | Ravi  | 85.5  |
| 102 | Kumar | 90.0  |
| 103 | Ali   | 78.5  |
+-----+-------+-------+
```

JDBC gives Java access to these rows through:

```java
ResultSet rs;
```

---

## 1.2 How do we obtain a ResultSet?

Usually through:

```java
ResultSet rs = statement.executeQuery();
```

For `PreparedStatement`:

```java
String sql =
    "SELECT id, name, marks FROM student";

PreparedStatement ps =
    con.prepareStatement(sql);

ResultSet rs =
    ps.executeQuery();
```

For `Statement`:

```java
Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(
        "SELECT id, name, marks FROM student"
    );
```

The important relationship is:

```text
executeQuery()
       ↓
   ResultSet
```

---

# 1.3 Is ResultSet the database table?

**No.**

This distinction is extremely important.

A table exists permanently in the database:

```text
student table
```

A `ResultSet` represents the **result of a query**.

For example:

```sql
SELECT * FROM student;
```

produces a result.

```text
student table
      ↓
    SELECT
      ↓
   ResultSet
```

A different query produces a different ResultSet:

```sql
SELECT name FROM student;
```

The ResultSet now contains only the selected column.

---

# 1.4 Is ResultSet an interface or class?

It is an **interface**.

```java
public interface ResultSet
```

You normally don't create it using:

```java
new ResultSet(); // ❌
```

Instead, the JDBC driver provides an implementation.

You obtain it through:

```java
ps.executeQuery();
```

---

# 1.5 What does ResultSet contain?

A ResultSet conceptually contains:

```text
Rows
 +
Columns
 +
Cursor position
 +
Metadata
```

Example:

```text
          Columns
       ↓      ↓       ↓
       id    name    marks

row → 101    Ravi    85.5
row → 102    Kumar   90.0
row → 103    Ali     78.5
```

The cursor determines **which row you're currently working with**.

---

# 1.6 ResultSet cursor

The cursor is one of the most important ResultSet concepts.

When a ResultSet is initially created, its cursor is conceptually:

```text
       cursor
          ↓
----------------
101 Ravi  85.5
102 Kumar 90.0
103 Ali   78.5
```

It is positioned **before the first row**.

Then:

```java
rs.next();
```

moves it to:

```text
       cursor
          ↓
101 Ravi 85.5
102 Kumar 90.0
103 Ali   78.5
```

Another:

```java
rs.next();
```

moves it to:

```text
101 Ravi 85.5
       cursor
          ↓
102 Kumar 90.0
103 Ali 78.5
```

---

# 2. `next()`

## 2.1 What is next()?

`next()` advances the cursor to the next row.

Syntax:

```java
boolean result = rs.next();
```

It returns:

```text
true
```

if the cursor successfully moves to a row.

It returns:

```text
false
```

if there is no next row.

---

# 2.2 Why is next() necessary?

Because a newly created ResultSet starts **before the first row**.

Suppose:

```text
101 Ravi
102 Kumar
103 Ali
```

Initial position:

```text
BEFORE FIRST
     ↓
101 Ravi
102 Kumar
103 Ali
```

After:

```java
rs.next();
```

position becomes:

```text
101 Ravi
↑
CURRENT ROW
```

Therefore, this is the standard pattern:

```java
while (rs.next()) {
    // read current row
}
```

---

# 2.3 How does while(rs.next()) work?

Consider:

```java
while (rs.next()) {
    System.out.println(
        rs.getInt("id")
    );
}
```

Execution:

```text
Initial:
BEFORE FIRST

next() → true
       ↓
row 1
       ↓
print row 1

next() → true
       ↓
row 2
       ↓
print row 2

next() → true
       ↓
row 3
       ↓
print row 3

next() → false
       ↓
loop ends
```

This is why:

```java
while (rs.next())
```

is so common in JDBC.

---

# 2.4 What happens when next() reaches the end?

Suppose there are three rows.

After processing row 3:

```java
rs.next();
```

returns:

```java
false
```

The cursor has moved beyond the last row.

Conceptually:

```text
101 Ravi
102 Kumar
103 Ali
         ↑
     AFTER LAST
```

You cannot then retrieve normal column values from that position.

---

# 2.5 Can next() move backward?

For a normal:

```java
TYPE_FORWARD_ONLY
```

ResultSet, you should not expect backward movement.

For scrollable ResultSets, methods such as:

```java
rs.previous();
```

can be available.

Therefore:

```text
TYPE_FORWARD_ONLY
        ↓
mostly forward navigation

Scrollable ResultSet
        ↓
forward + backward/positioning methods
```

---

# 2.6 Other cursor-navigation methods

With a scrollable ResultSet, you may use:

```java
rs.next();
rs.previous();
rs.first();
rs.last();
rs.beforeFirst();
rs.afterLast();
rs.absolute(3);
rs.relative(1);
```

These are not equally useful with every ResultSet type.

---

## `first()`

Moves to first row:

```java
rs.first();
```

---

## `last()`

Moves to last row:

```java
rs.last();
```

---

## `previous()`

Moves backward:

```java
rs.previous();
```

---

## `absolute(3)`

Moves to row 3:

```java
rs.absolute(3);
```

---

## `relative(1)`

Moves relative to the current position:

```java
rs.relative(1);
```

These navigation methods are mainly relevant to **scrollable ResultSets**.

---

# 3. `getInt()`

## 3.1 What is getInt()?

`getInt()` retrieves the value of a column as a Java `int`.

Two common forms:

```java
rs.getInt("id");
```

and:

```java
rs.getInt(1);
```

---

# 3.2 Using a column name

Suppose:

```text
id = 101
```

Use:

```java
int id = rs.getInt("id");
```

This is often easier to understand than using a numeric index.

---

# 3.3 Using column index

Suppose:

```text
Column 1 → id
Column 2 → name
Column 3 → marks
```

Then:

```java
int id = rs.getInt(1);
```

Important:

> JDBC ResultSet column indexes start at **1**.

Not zero.

```text
1 → id
2 → name
3 → marks
```

---

# 3.4 Example

```java
while (rs.next()) {

    int id =
        rs.getInt("id");

    System.out.println(id);
}
```

Output:

```text
101
102
103
```

---

# 3.5 What if the database value is NULL?

This is an important JDBC detail.

For primitive getters such as:

```java
rs.getInt("id");
```

a SQL `NULL` is returned as the Java primitive's default value, typically:

```text
0
```

So you cannot distinguish:

```text
SQL NULL
```

from:

```text
actual SQL value 0
```

using the returned `int` alone.

JDBC provides:

```java
rs.wasNull();
```

Example:

```java
int value =
    rs.getInt("age");

if (rs.wasNull()) {
    System.out.println("Database value was NULL");
}
```

This is an important interview point.

---

# 3.6 `getInt()` and type conversion

The JDBC driver may perform appropriate conversions between SQL types and Java types.

But don't assume every SQL type can meaningfully be retrieved as `int`.

Choose the getter appropriate to the data you expect.

---

# 4. `getString()`

## 4.1 What is getString()?

`getString()` retrieves a column value as a Java `String`.

Example:

```java
String name =
    rs.getString("name");
```

or:

```java
String name =
    rs.getString(2);
```

---

# 4.2 Example

```java
while (rs.next()) {

    String name =
        rs.getString("name");

    System.out.println(name);
}
```

Output:

```text
Ravi
Kumar
Ali
```

---

# 4.3 Does getString() work only with VARCHAR?

Not strictly.

JDBC drivers can perform certain conversions to String.

For example, depending on the database/driver:

```java
String value =
    rs.getString("someNumericColumn");
```

may return a textual representation.

However, when you know the intended Java type, using the matching getter is clearer.

---

# 4.4 What happens if String column is NULL?

For object-returning methods such as:

```java
String name =
    rs.getString("name");
```

SQL `NULL` is represented as:

```java
null
```

So:

```java
if (name == null) {
    System.out.println("Name is NULL");
}
```

---

# 5. `getDouble()`

## 5.1 What is getDouble()?

`getDouble()` retrieves a column as a Java `double`.

Example:

```java
double marks =
    rs.getDouble("marks");
```

or:

```java
double marks =
    rs.getDouble(3);
```

---

# 5.2 Example

```java
while (rs.next()) {

    double marks =
        rs.getDouble("marks");

    System.out.println(marks);
}
```

Output:

```text
85.5
90.0
78.5
```

---

# 5.3 Important: Money and precision

A common mistake is thinking:

```text
DECIMAL → double
```

is always the best choice.

For financial values, `double` can introduce binary floating-point representation issues.

For exact decimal values, JDBC applications commonly use:

```java
BigDecimal
```

with:

```java
rs.getBigDecimal("amount");
```

So:

```text
Approximate floating-point value → double
Exact decimal/financial value → BigDecimal
```

This is an important real-world distinction.

---

# 5.4 NULL and getDouble()

Like `getInt()`, `getDouble()` returns a primitive.

If the database value is SQL `NULL`, the primitive getter returns the corresponding default:

```text
0.0
```

Use:

```java
rs.wasNull();
```

to determine whether the SQL value was actually `NULL`.

Example:

```java
double marks =
    rs.getDouble("marks");

if (rs.wasNull()) {
    System.out.println("marks is NULL");
}
```

---

# 6. `getObject()`

## 6.1 What is getObject()?

`getObject()` retrieves a column as a Java `Object`.

Example:

```java
Object value =
    rs.getObject("name");
```

The actual Java object depends on the SQL type and JDBC driver.

Conceptually:

```text
SQL data
   ↓
JDBC Driver
   ↓
Java object
```

---

# 6.2 Why use getObject()?

Suppose you don't know the exact SQL type beforehand.

Instead of:

```java
getInt()
getString()
getDouble()
getDate()
...
```

you can use:

```java
Object value =
    rs.getObject(column);
```

This is especially useful for generic database tools and metadata-driven applications.

---

# 6.3 Example

```java
while (rs.next()) {

    Object value =
        rs.getObject(1);

    System.out.println(value);
}
```

The returned object could conceptually be:

```text
INTEGER → Integer
VARCHAR → String
DECIMAL → BigDecimal
DATE → java.sql.Date / compatible representation
...
```

The exact mapping depends on JDBC/database/driver behavior.

---

# 6.4 Typed getObject()

JDBC also provides a typed form:

```java
String name =
    rs.getObject(
        "name",
        String.class
    );
```

Another example:

```java
Integer id =
    rs.getObject(
        "id",
        Integer.class
    );
```

This allows you to specify the Java type you want.

---

# 6.5 getObject() and NULL

Unlike primitive getters:

```java
rs.getObject(...)
```

can naturally return:

```java
null
```

for SQL `NULL`.

Therefore:

```java
Object value =
    rs.getObject("someColumn");

if (value == null) {
    System.out.println("NULL");
}
```

---

# 7. ResultSet Types

Now we reach an area where many students confuse **type** with **concurrency**.

ResultSet type answers:

> **How can the cursor move through the result?**

There are three standard ResultSet types:

```java
ResultSet.TYPE_FORWARD_ONLY
ResultSet.TYPE_SCROLL_INSENSITIVE
ResultSet.TYPE_SCROLL_SENSITIVE
```

---

# 7.1 TYPE_FORWARD_ONLY

This is the simplest type.

```java
ResultSet.TYPE_FORWARD_ONLY
```

The cursor moves forward.

Typical usage:

```java
while (rs.next()) {
    // process row
}
```

Conceptually:

```text
BEFORE FIRST
     ↓
ROW 1
     ↓
ROW 2
     ↓
ROW 3
     ↓
AFTER LAST
```

You should not design your code expecting arbitrary backward navigation.

---

## When should you use it?

Use forward-only processing when you simply need:

```text
Read rows
   ↓
Process rows sequentially
   ↓
Finish
```

It is often the natural choice for ordinary queries.

---

# 7.2 TYPE_SCROLL_INSENSITIVE

```java
ResultSet.TYPE_SCROLL_INSENSITIVE
```

This allows cursor movement in multiple directions.

You can potentially use:

```java
rs.next();
rs.previous();
rs.first();
rs.last();
rs.absolute(3);
```

The important word is:

```text
INSENSITIVE
```

The ResultSet does not generally reflect changes made to the underlying database after the ResultSet was produced.

Example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY
    );
```

---

# 7.3 TYPE_SCROLL_SENSITIVE

```java
ResultSet.TYPE_SCROLL_SENSITIVE
```

This also supports scrolling.

```java
rs.next();
rs.previous();
rs.first();
rs.last();
rs.absolute(3);
```

The difference is that it is intended to be **sensitive to certain changes in the underlying data**.

However, this does **not** mean:

> "The ResultSet always automatically shows every database change."

The actual behavior depends on:

* JDBC driver
* database
* query
* transaction behavior
* ResultSet implementation

Therefore, don't overgeneralize `SCROLL_SENSITIVE`.

---

# 7.4 Comparing ResultSet types

| Feature                  | `FORWARD_ONLY`         | `SCROLL_INSENSITIVE` | `SCROLL_SENSITIVE`                  |
| ------------------------ | ---------------------- | -------------------- | ----------------------------------- |
| Move forward             | Yes                    | Yes                  | Yes                                 |
| Move backward            | Not intended           | Yes                  | Yes                                 |
| `first()`                | Not generally usable   | Yes                  | Yes                                 |
| `last()`                 | Not generally usable   | Yes                  | Yes                                 |
| `absolute()`             | Not generally usable   | Yes                  | Yes                                 |
| Reflect later DB changes | No scrolling semantics | Generally no         | Intended to detect certain changes  |
| Complexity               | Lowest                 | Higher               | Highest                             |
| Common use               | Sequential reading     | Scrollable results   | Special cases requiring sensitivity |

---

# 7.5 How do we request a ResultSet type?

Through `createStatement()`:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY
    );
```

Or with `PreparedStatement`:

```java
PreparedStatement ps =
    con.prepareStatement(
        sql,
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY
    );
```

---

# 7.6 Can the driver ignore our requested type?

This is important.

You request:

```java
TYPE_SCROLL_SENSITIVE
```

but the driver/database may not support that exact feature.

JDBC allows implementations to downgrade certain requested ResultSet capabilities.

Therefore, you should not blindly assume the requested capability was granted.

You can inspect:

```java
int type =
    rs.getType();

int concurrency =
    rs.getConcurrency();
```

---

# 8. ResultSet Concurrency

Concurrency answers a **different question**.

It asks:

> **Can the ResultSet be used to update rows?**

Two standard concurrency modes are:

```java
ResultSet.CONCUR_READ_ONLY
```

and:

```java
ResultSet.CONCUR_UPDATABLE
```

---

# 8.1 CONCUR_READ_ONLY

This is the normal read-only mode.

```java
ResultSet.CONCUR_READ_ONLY
```

You read:

```java
while (rs.next()) {
    System.out.println(
        rs.getString("name")
    );
}
```

The ResultSet itself isn't being used to update the database rows.

Example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.CONCUR_READ_ONLY
    );
```

---

# 8.2 CONCUR_UPDATABLE

This mode allows an eligible ResultSet to update database rows through ResultSet methods.

Example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_UPDATABLE
    );
```

Then:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT id, name FROM student"
    );
```

Move to a row:

```java
rs.next();
```

Change a column:

```java
rs.updateString(
    "name",
    "Ravi Kumar"
);
```

Then:

```java
rs.updateRow();
```

Conceptually:

```text
ResultSet current row
        ↓
updateString()
        ↓
updateRow()
        ↓
Database row
```

---

# 8.3 Does CONCUR_UPDATABLE guarantee that every query is updatable?

**No.**

This is a major misconception.

Even if you request:

```java
CONCUR_UPDATABLE
```

the database/driver may not be able to provide an updatable ResultSet for a particular query.

Complex queries involving things such as:

* joins
* aggregates
* calculated expressions
* certain views
* other database-specific constructs

may not be updatable.

The actual capabilities depend on the JDBC driver and database.

---

# 8.4 ResultSet type vs concurrency

Memorize this distinction:

### Type

```text
TYPE_...
```

answers:

> **How does the cursor move?**

### Concurrency

```text
CONCUR_...
```

answers:

> **Can the ResultSet be updated?**

Therefore:

```text
                 ResultSet
                     │
             ┌───────┴────────┐
             ↓                ↓
           TYPE          CONCURRENCY
             │                │
       cursor movement     updating
             │                │
     ┌───────┼──────┐     ┌───┴────┐
     ↓       ↓      ↓     ↓        ↓
 FORWARD  SCROLL  SCROLL READ     UPDATE
 ONLY     INSENS  SENS   ONLY     ABLE
```

---

# 9. Complete ResultSet Program

Let's combine the concepts.

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

        String sql =
            "SELECT id, name, marks " +
            "FROM student";

        PreparedStatement ps =
            con.prepareStatement(sql);

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

Suppose:

```text
101 Ravi 85.5
102 Kumar 90.0
103 Ali 78.5
```

The execution is:

```text
executeQuery()
      ↓
ResultSet created
      ↓
cursor BEFORE FIRST
      ↓
next()
      ↓
row 101
      ↓
getInt()
getString()
getDouble()
      ↓
next()
      ↓
row 102
      ↓
...
      ↓
next() = false
      ↓
end
```

---

# 10. Column Name vs Column Index

This deserves special attention.

Suppose:

```sql
SELECT id, name, marks FROM student
```

The ResultSet columns are:

```text
1 → id
2 → name
3 → marks
```

You can write:

```java
rs.getInt(1);
rs.getString(2);
rs.getDouble(3);
```

Or:

```java
rs.getInt("id");
rs.getString("name");
rs.getDouble("marks");
```

---

## Which is better?

Generally, column labels/names are easier to understand:

```java
rs.getString("name");
```

rather than:

```java
rs.getString(2);
```

because if the SQL column order changes, index-based code can become confusing or incorrect.

---

# 11. Column Label vs Actual Column Name

Another subtle JDBC concept:

```java
rs.getString("name");
```

uses a **column label**.

For example:

```sql
SELECT name AS student_name
FROM student;
```

Then the result column's label is:

```text
student_name
```

So you can use:

```java
rs.getString("student_name");
```

This is particularly useful with aliases.

---

# 12. ResultSet Metadata

A ResultSet also provides information about its columns through:

```java
ResultSetMetaData
```

Example:

```java
ResultSetMetaData meta =
    rs.getMetaData();
```

Then:

```java
int count =
    meta.getColumnCount();
```

You can inspect:

```java
meta.getColumnName(1);
meta.getColumnLabel(1);
meta.getColumnType(1);
meta.getColumnTypeName(1);
```

This is especially useful for generic database applications.

Conceptually:

```text
ResultSet
   │
   ├── actual data
   │
   └── ResultSetMetaData
          ↓
      information about columns
```

---

# 13. ResultSet and Resource Management

A ResultSet is a JDBC resource.

You should close it when finished.

Traditional:

```java
rs.close();
```

Modern Java generally uses **try-with-resources**:

```java
try (PreparedStatement ps =
         con.prepareStatement(sql);
     ResultSet rs =
         ps.executeQuery()) {

    while (rs.next()) {
        System.out.println(
            rs.getString("name")
        );
    }
}
```

This automatically closes the resources when leaving the block.

---

# 14. Important ResultSet Rules

## Rule 1

`ResultSet` normally comes from:

```java
executeQuery()
```

---

## Rule 2

A newly created ResultSet starts before the first row.

Therefore:

```java
rs.next();
```

must normally happen before reading a row.

---

## Rule 3

Column indexes begin at **1**.

```java
rs.getInt(1);
```

not:

```java
rs.getInt(0);
```

---

## Rule 4

`getInt()` returns a Java primitive:

```java
int
```

SQL `NULL` can therefore require:

```java
rs.wasNull();
```

---

## Rule 5

`getString()` returns:

```java
String
```

and SQL `NULL` is represented as:

```java
null
```

---

## Rule 6

`getObject()` provides a general Java object representation of the SQL value.

---

## Rule 7

ResultSet **type** and **concurrency** are different.

```text
TYPE → cursor movement
CONCUR → updating capability
```

---

# 15. Common Mistakes

## Mistake 1

```java
ResultSet rs = ps.executeQuery();

System.out.println(
    rs.getString("name")
);
```

Potential problem: you haven't moved to a row.

Correct:

```java
ResultSet rs = ps.executeQuery();

if (rs.next()) {
    System.out.println(
        rs.getString("name")
    );
}
```

---

## Mistake 2

Using index `0`:

```java
rs.getInt(0);
```

❌ Wrong.

Use:

```java
rs.getInt(1);
```

---

## Mistake 3

Confusing `ResultSet` with a table.

```text
Table       → database object
ResultSet   → query result
```

---

## Mistake 4

Thinking:

```java
TYPE_SCROLL_INSENSITIVE
```

means "the database doesn't change."

It doesn't mean that.

It concerns whether the ResultSet reflects certain underlying changes while scrolling.

---

## Mistake 5

Thinking:

```java
CONCUR_UPDATABLE
```

means every query can be updated.

It doesn't.

The actual query and driver/database capabilities matter.

---

# 16. ResultSet Types + Concurrency Together

You can request both properties:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY
    );
```

Read it as:

```text
TYPE_SCROLL_INSENSITIVE
        ↓
I want a scrollable cursor.

CONCUR_READ_ONLY
        ↓
I don't want to update through the ResultSet.
```

Another combination:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_SENSITIVE,
        ResultSet.CONCUR_UPDATABLE
    );
```

Conceptually:

```text
scrollable
+
potentially sensitive
+
potentially updatable
```

But again, the driver/database may not support every requested combination.

---

# 17. Deep Comparison of the Getter Methods

| Method        | Java result | Typical use                   | SQL NULL concern                 |
| ------------- | ----------- | ----------------------------- | -------------------------------- |
| `getInt()`    | `int`       | Integer values                | returns `0`; check `wasNull()`   |
| `getString()` | `String`    | Text                          | returns `null`                   |
| `getDouble()` | `double`    | Floating-point numeric values | returns `0.0`; check `wasNull()` |
| `getObject()` | `Object`    | General/dynamic retrieval     | can return `null`                |

Remember:

```text
Primitive getter
    ↓
can't itself represent null
    ↓
use wasNull()
```

while:

```text
Object getter
    ↓
can represent null directly
```

---

# 18. The Complete Mental Model

Imagine this ResultSet:

```text
             COLUMN
       1       2       3
      id      name    marks
       ↓       ↓       ↓

ROW 1 101     Ravi    85.5
ROW 2 102     Kumar   90.0
ROW 3 103     Ali     78.5
```

Initially:

```text
BEFORE FIRST
```

Call:

```java
rs.next();
```

Now:

```text
ROW 1
```

Read:

```java
rs.getInt("id");
rs.getString("name");
rs.getDouble("marks");
```

Then:

```java
rs.next();
```

Now:

```text
ROW 2
```

Read again.

Eventually:

```java
rs.next();
```

returns:

```text
false
```

and iteration ends.

---

# 🔥 ResultSet Deep-Dive Summary

```text
ResultSet
│
├── Interface
│   └── Represents query result
│
├── Cursor
│   ├── BEFORE FIRST initially
│   ├── current row
│   └── AFTER LAST at the end
│
├── next()
│   ├── moves to next row
│   └── returns boolean
│
├── Data Retrieval
│   ├── getInt()
│   ├── getString()
│   ├── getDouble()
│   └── getObject()
│
├── Column Identification
│   ├── column label/name
│   └── column index starting at 1
│
├── ResultSet Type
│   ├── TYPE_FORWARD_ONLY
│   ├── TYPE_SCROLL_INSENSITIVE
│   └── TYPE_SCROLL_SENSITIVE
│
├── ResultSet Concurrency
│   ├── CONCUR_READ_ONLY
│   └── CONCUR_UPDATABLE
│
└── Metadata
    └── ResultSetMetaData
```

---

# 🎯 The 5 Things You Must Never Forget

### 1. `executeQuery()` → `ResultSet`

```text
SELECT
 ↓
executeQuery()
 ↓
ResultSet
```

### 2. `next()` → move to a row

```java
while (rs.next()) {
    ...
}
```

### 3. Getter → read the current row

```java
rs.getInt("id");
rs.getString("name");
rs.getDouble("marks");
```

### 4. Column indexes start at 1

```text
1, 2, 3...
```

not:

```text
0, 1, 2...
```

### 5. Type ≠ Concurrency

```text
ResultSet TYPE
       ↓
cursor movement

ResultSet CONCURRENCY
       ↓
read-only / updatable
```

> **ResultSet is the JDBC representation of a query's returned rows; `next()` navigates the cursor, `getXXX()` methods retrieve values from the current row, ResultSet types control cursor movement, and concurrency controls whether the ResultSet can be used for updates.**
