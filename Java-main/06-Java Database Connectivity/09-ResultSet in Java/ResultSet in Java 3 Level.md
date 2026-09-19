# 9. ResultSet in Java — 3LEVEL

The **3LEVEL method** means we learn every concept in three stages:

* 🟢 **LEVEL 1 — Basic:** What it is
* 🟡 **LEVEL 2 — Understanding:** How and why it works
* 🔴 **LEVEL 3 — Deep/Interview:** Important rules, examples, and traps

---

# 1. ResultSet Interface

## 🟢 LEVEL 1 — Basic

`ResultSet` is an **interface** in `java.sql` used to store and read the data returned by a SQL query.

```java
import java.sql.ResultSet;
```

Usually, we get a `ResultSet` from:

```java
ResultSet rs = statement.executeQuery();
```

Example:

```java
ResultSet rs =
    ps.executeQuery("SELECT * FROM student");
```

Conceptually:

```text
Java Application
       ↓
SQL SELECT
       ↓
Database
       ↓
ResultSet
       ↓
Java reads rows
```

---

## 🟡 LEVEL 2 — Understanding

Suppose the database contains:

```text
id    name     marks
--------------------
101   Ravi     85.5
102   Kumar    90.0
103   Ali      78.5
```

After:

```java
ResultSet rs = ps.executeQuery();
```

the ResultSet represents the returned rows.

But the cursor initially starts **before the first row**:

```text
Cursor
   ↓
101 Ravi 85.5
102 Kumar 90.0
103 Ali 78.5
```

You use:

```java
rs.next();
```

to move to the first row.

---

## 🔴 LEVEL 3 — Deep/Interview

`ResultSet` is an interface whose implementation is supplied by the JDBC driver.

You normally don't create it yourself:

```java
new ResultSet();       // ❌
```

Instead:

```java
ResultSet rs = ps.executeQuery();  // ✅
```

A ResultSet can have two independent characteristics:

```text
ResultSet
   │
   ├── TYPE
   │    └── Controls cursor movement
   │
   └── CONCURRENCY
        └── Controls read/update capability
```

Remember:

> **TYPE = How can I move?**
> **CONCURRENCY = Can I update?**

---

# 2. `next()`

## 🟢 LEVEL 1 — Basic

`next()` moves the cursor to the next row.

```java
rs.next();
```

It returns:

```java
boolean
```

Meaning:

```text
true  → row exists
false → no more rows
```

---

## 🟡 LEVEL 2 — Understanding

Initially:

```text
BEFORE FIRST
```

After:

```java
rs.next();
```

the cursor moves to row 1.

```text
101 Ravi
 ↑
Cursor
```

Another:

```java
rs.next();
```

moves to row 2:

```text
102 Kumar
 ↑
Cursor
```

When there are no more rows:

```java
rs.next()
```

returns:

```text
false
```

That's why we commonly write:

```java
while (rs.next()) {
    // process current row
}
```

---

## 🔴 LEVEL 3 — Deep/Interview

`next()` does **not retrieve column values**.

It only moves the cursor.

```text
next()
  ↓
MOVE

getInt()
getString()
getDouble()
  ↓
READ
```

For example:

```java
while (rs.next()) {

    int id = rs.getInt("id");

    String name =
        rs.getString("name");

    System.out.println(id + " " + name);
}
```

### Common mistake

❌ Wrong:

```java
int id = rs.getInt("id");
rs.next();
```

You should normally position the cursor first:

```java
rs.next();

int id = rs.getInt("id");
```

---

# 3. `getInt()`

## 🟢 LEVEL 1 — Basic

`getInt()` reads a column value as a Java `int`.

```java
int id = rs.getInt("id");
```

Example:

```text
Database:
id = 101

Java:
int id = 101
```

---

## 🟡 LEVEL 2 — Understanding

You can identify the column by **name**:

```java
rs.getInt("id");
```

or by **column index**:

```java
rs.getInt(1);
```

Important:

> JDBC column indexes start at **1**, not 0.

For:

```text
id | name | marks
```

the indexes are:

```text
1 → id
2 → name
3 → marks
```

---

## 🔴 LEVEL 3 — Deep/Interview

Consider SQL `NULL`.

```java
int value = rs.getInt("age");
```

If the database value is SQL `NULL`, the primitive `int` cannot contain `null`.

You need:

```java
rs.wasNull();
```

Example:

```java
int age = rs.getInt("age");

if (rs.wasNull()) {
    System.out.println("Age is NULL");
}
```

Remember:

```text
SQL NULL
   ↓
getInt()
   ↓
primitive int
   ↓
cannot represent null
   ↓
use wasNull()
```

---

# 4. `getString()`

## 🟢 LEVEL 1 — Basic

`getString()` reads a column as a Java `String`.

```java
String name =
    rs.getString("name");
```

Example:

```text
Database:
name = Ravi

Java:
String name = "Ravi";
```

---

## 🟡 LEVEL 2 — Understanding

You can use column name:

```java
rs.getString("name");
```

or column index:

```java
rs.getString(2);
```

Example:

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

## 🔴 LEVEL 3 — Deep/Interview

If the database column contains SQL `NULL`:

```java
String name =
    rs.getString("name");
```

the Java result can be:

```java
null
```

Unlike primitive `int`, `String` can represent `null`.

Therefore:

```java
if (name == null) {
    System.out.println("Name is NULL");
}
```

---

# 5. `getDouble()`

## 🟢 LEVEL 1 — Basic

`getDouble()` retrieves a value as Java `double`.

```java
double marks =
    rs.getDouble("marks");
```

Example:

```text
Database:
85.5

Java:
double marks = 85.5;
```

---

## 🟡 LEVEL 2 — Understanding

Using column name:

```java
rs.getDouble("marks");
```

Using column index:

```java
rs.getDouble(3);
```

Example:

```java
while (rs.next()) {

    double marks =
        rs.getDouble("marks");

    System.out.println(marks);
}
```

---

## 🔴 LEVEL 3 — Deep/Interview

Like `getInt()`, `double` is primitive.

Therefore SQL `NULL` cannot be represented directly.

```java
double marks =
    rs.getDouble("marks");

if (rs.wasNull()) {
    System.out.println("Marks is NULL");
}
```

Also remember:

> Don't automatically use `double` for monetary values.

For exact decimal/financial data, `BigDecimal` is generally preferable:

```java
BigDecimal salary =
    rs.getBigDecimal("salary");
```

---

# 6. `getObject()`

## 🟢 LEVEL 1 — Basic

`getObject()` retrieves a database value as a Java `Object`.

```java
Object value =
    rs.getObject("name");
```

---

## 🟡 LEVEL 2 — Understanding

Normally, we choose a specific getter:

```java
getInt()
getString()
getDouble()
```

But sometimes we don't know the database column's type beforehand.

Then:

```java
getObject()
```

is useful.

Conceptually:

```text
SQL value
   ↓
JDBC Driver
   ↓
Java Object
```

Example:

```java
Object value =
    rs.getObject("id");
```

The driver maps the SQL value to an appropriate Java representation.

---

## 🔴 LEVEL 3 — Deep/Interview

You can also request a specific Java type:

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

For SQL `NULL`, `getObject()` can return:

```java
null
```

This is one reason `getObject()` is useful when working with nullable values.

---

# 7. ResultSet Types

ResultSet type determines:

> **How the cursor can move through the ResultSet.**

There are three standard types.

```java
ResultSet.TYPE_FORWARD_ONLY
ResultSet.TYPE_SCROLL_INSENSITIVE
ResultSet.TYPE_SCROLL_SENSITIVE
```

---

## 🟢 LEVEL 1 — Basic

### 1. TYPE_FORWARD_ONLY

Only move forward.

```text
Row 1
 ↓
Row 2
 ↓
Row 3
 ↓
END
```

---

### 2. TYPE_SCROLL_INSENSITIVE

Cursor can move forward and backward.

```text
Row 1 ↔ Row 2 ↔ Row 3
```

---

### 3. TYPE_SCROLL_SENSITIVE

Also allows scrolling and is intended to reflect certain changes made to the underlying data.

---

## 🟡 LEVEL 2 — Understanding

### TYPE_FORWARD_ONLY

```java
ResultSet.TYPE_FORWARD_ONLY
```

Typical usage:

```java
while (rs.next()) {
    ...
}
```

This is the simple, common case.

---

### TYPE_SCROLL_INSENSITIVE

You can potentially use methods such as:

```java
rs.first();
rs.last();
rs.previous();
rs.absolute(3);
```

Example:

```java
rs.last();
```

moves to the last row.

---

### TYPE_SCROLL_SENSITIVE

Also supports scrolling, but is intended to be sensitive to changes in the underlying data.

However, the actual behavior depends on the JDBC driver/database support.

---

## 🔴 LEVEL 3 — Deep/Interview

You request a ResultSet type when creating the statement:

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

You can inspect the actual type:

```java
int type = rs.getType();
```

Important:

> Not every JDBC driver/database supports every ResultSet type equally.

So don't assume that requesting `TYPE_SCROLL_SENSITIVE` guarantees fully sensitive behavior.

---

# 8. ResultSet Concurrency

## 🟢 LEVEL 1 — Basic

ResultSet concurrency answers:

> **Can the ResultSet be used only for reading, or can it be used to update rows?**

Two main constants:

```java
ResultSet.CONCUR_READ_ONLY
ResultSet.CONCUR_UPDATABLE
```

---

## 🟡 LEVEL 2 — Understanding

### CONCUR_READ_ONLY

```java
ResultSet.CONCUR_READ_ONLY
```

Means:

> Read the ResultSet, but don't update rows through it.

Example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.CONCUR_READ_ONLY
    );
```

This is very common.

---

### CONCUR_UPDATABLE

```java
ResultSet.CONCUR_UPDATABLE
```

Means the ResultSet is requested to allow row updates.

For example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_UPDATABLE
    );
```

If supported and the query is updatable:

```java
rs.next();

rs.updateString(
    "name",
    "Ravi Kumar"
);

rs.updateRow();
```

---

## 🔴 LEVEL 3 — Deep/Interview

`CONCUR_UPDATABLE` does **not** mean:

> "Every ResultSet can always be updated."

The driver/database and SQL query must support an updatable ResultSet.

For example, a simple table query may be eligible:

```sql
SELECT id, name, marks
FROM student;
```

But complex queries involving things such as:

```text
JOIN
GROUP BY
aggregate functions
calculated expressions
```

may not produce an updatable ResultSet.

Therefore:

```text
CONCUR_UPDATABLE
       ↓
Request update capability
       ↓
Driver + Database + Query
       ↓
Must support it
```

---

# 9. ResultSet Type vs Concurrency

This is one of the **most important JDBC distinctions**.

## ResultSet TYPE

asks:

> **How can I move the cursor?**

```text
TYPE_FORWARD_ONLY
TYPE_SCROLL_INSENSITIVE
TYPE_SCROLL_SENSITIVE
```

---

## ResultSet CONCURRENCY

asks:

> **Can I update through the ResultSet?**

```text
CONCUR_READ_ONLY
CONCUR_UPDATABLE
```

### Memory trick

```text
TYPE
 ↓
TRAVEL

CONCURRENCY
 ↓
CHANGE
```

Or:

> **TYPE = Movement**
> **CONCURRENCY = Modification**

---

# 10. Complete 3LEVEL Example

Suppose:

```text
student
-------------------------
id    name     marks
101   Ravi     85.5
102   Kumar    90.0
103   Ali      78.5
```

Code:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT id, name, marks FROM student"
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
```

Flow:

```text
executeQuery()
      ↓
   ResultSet
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
   next()
      ↓
   Row 3
      ↓
      ...
      ↓
 next() == false
      ↓
     END
```

---

# 11. Complete Concept Map

```text
                 RESULTSET
                     │
       ┌─────────────┼──────────────┐
       │             │              │
       ↓             ↓              ↓
    Cursor        Get values       Control
       │             │              │
       ↓             ↓              ↓
    next()       getInt()          TYPE
                  getString()         │
                  getDouble()         ├─ FORWARD_ONLY
                  getObject()         ├─ SCROLL_INSENSITIVE
                                      └─ SCROLL_SENSITIVE
                     
                                  CONCURRENCY
                                      │
                                      ├─ READ_ONLY
                                      └─ UPDATABLE
```

---

# 🔥 Final 3LEVEL Revision Table

| Concept        | 🟢 Basic                   | 🟡 Understand            | 🔴 Remember                        |
| -------------- | -------------------------- | ------------------------ | ---------------------------------- |
| `ResultSet`    | Holds query result         | Represents returned rows | Driver provides its implementation |
| `next()`       | Moves cursor               | Returns `true/false`     | `next()` = MOVE                    |
| `getInt()`     | Reads `int`                | Name or index            | SQL `NULL` → use `wasNull()`       |
| `getString()`  | Reads `String`             | Name or index            | SQL `NULL` can become `null`       |
| `getDouble()`  | Reads `double`             | Name or index            | SQL `NULL` → `wasNull()`           |
| `getObject()`  | Reads as `Object`          | Useful for generic data  | Can request a specific Java type   |
| ResultSet Type | Controls movement          | Forward/scrollable       | Driver support matters             |
| Concurrency    | Controls update capability | Read-only/updatable      | `UPDATABLE` isn't guaranteed       |

## 🧠 One-line memory

> **`ResultSet` represents database rows; `next()` moves to a row; `getXXX()` reads its columns; TYPE controls cursor movement; CONCURRENCY controls whether rows can be updated.**
