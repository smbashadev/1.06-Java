# 9. ResultSet in Java — ONEPAGE

`ResultSet` is one of the most important JDBC concepts because it represents the **data returned by a database query**.

The basic flow is:

```text
SQL SELECT
   ↓
executeQuery()
   ↓
ResultSet
   ↓
next()
   ↓
getXXX()
   ↓
Read column values
```

---

# 1. ResultSet Interface

## What is ResultSet?

`ResultSet` is a JDBC **interface** in `java.sql`.

```java
import java.sql.ResultSet;
```

It represents the **tabular data returned by a SQL query**.

Example:

```java
String sql = "SELECT id, name, marks FROM student";

Statement st = con.createStatement();

ResultSet rs = st.executeQuery(sql);
```

Suppose the database returns:

```text
id     name      marks
-----------------------
101    Ravi      85.5
102    Kumar     90.0
103    Ali       78.5
```

The `ResultSet` represents this returned result.

### Important

`ResultSet` does **not** represent the table itself.

It represents the **result produced by a query**.

```text
Database Table
      ↓
    SELECT
      ↓
   ResultSet
```

---

# 2. `next()`

## What is `next()`?

`next()` moves the `ResultSet` cursor to the **next row**.

Syntax:

```java
rs.next();
```

It returns:

```java
boolean
```

Meaning:

```text
true  → a row is available
false → no more rows
```

---

## Why do we need `next()`?

When a `ResultSet` is initially created, its cursor is positioned **before the first row**.

Conceptually:

```text
       cursor
         ↓
-------------------
101  Ravi   85.5
102  Kumar  90.0
103  Ali    78.5
```

Calling:

```java
rs.next();
```

moves it to the first row.

```text
              cursor
                ↓
101  Ravi   85.5
102  Kumar  90.0
103  Ali    78.5
```

Another:

```java
rs.next();
```

moves to the second row.

---

## Most common pattern

```java
while (rs.next()) {
    System.out.println(rs.getInt("id"));
    System.out.println(rs.getString("name"));
}
```

Meaning:

```text
next()
 ↓
Is there another row?
 ↓
YES → process row
 ↓
next()
 ↓
YES → process row
 ↓
next()
 ↓
NO → stop
```

### 🔥 Important

You normally must call `next()` before retrieving column values.

Don't normally do:

```java
rs.getInt("id");
```

immediately after obtaining a fresh `ResultSet`.

First:

```java
rs.next();
```

then:

```java
rs.getInt("id");
```

---

# 3. `getInt()`

## What is `getInt()`?

`getInt()` retrieves an integer-valued column from the current row.

Two common forms:

```java
rs.getInt("id");
```

or:

```java
rs.getInt(1);
```

Example:

```java
while (rs.next()) {
    int id = rs.getInt("id");
    System.out.println(id);
}
```

---

## Column name vs column index

### Column name

```java
rs.getInt("id");
```

### Column index

```java
rs.getInt(1);
```

JDBC column indexes start at **1**.

```text
Column:
1       2        3
id    name     marks
```

Not:

```text
0       1        2
```

---

## Example

Database:

```text
id     name
101    Ravi
102    Kumar
```

Code:

```java
while (rs.next()) {
    int id = rs.getInt("id");
    System.out.println(id);
}
```

Output:

```text
101
102
```

---

# 4. `getString()`

## What is `getString()`?

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

## Example

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

## 🔥 Important

`getString()` can be used for textual database columns, but JDBC drivers may also support converting certain other SQL values to strings.

For clarity and correctness, prefer the getter that matches the intended Java representation.

---

# 5. `getDouble()`

## What is `getDouble()`?

`getDouble()` retrieves a numeric column as a Java `double`.

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

## Example

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

## Common mapping

```text
Database value       Java getter

INTEGER              getInt()
VARCHAR              getString()
numeric/decimal      getDouble()
```

The exact SQL-to-Java type mapping depends on the database/driver and desired representation, so don't assume every numeric SQL type should always become `double`.

For exact decimal values such as money, `BigDecimal` is generally preferable to `double`.

---

# 6. `getObject()`

## What is `getObject()`?

`getObject()` retrieves a column value as a Java `Object`.

Example:

```java
Object value =
    rs.getObject("name");
```

There are common forms:

```java
rs.getObject("name");
```

and:

```java
rs.getObject(1);
```

---

## Why use getObject()?

Use it when you don't want to explicitly choose:

```text
getInt()
getString()
getDouble()
...
```

Example:

```java
Object value =
    rs.getObject(1);
```

The actual Java object returned depends on the SQL type and JDBC driver.

Conceptually:

```text
SQL value
   ↓
JDBC driver
   ↓
Java Object
```

---

## Typed getObject()

Modern JDBC also supports forms where you specify the desired Java type:

```java
String name =
    rs.getObject("name", String.class);
```

and:

```java
Integer id =
    rs.getObject("id", Integer.class);
```

This can be useful when you want explicit type conversion while using the general `getObject()` API.

---

# 7. ResultSet Types

A `ResultSet` has a **cursor type** that determines how its cursor behaves.

The major types are:

```java
ResultSet.TYPE_FORWARD_ONLY
ResultSet.TYPE_SCROLL_INSENSITIVE
ResultSet.TYPE_SCROLL_SENSITIVE
```

---

## 7.1 `TYPE_FORWARD_ONLY`

This is the simplest/common cursor type.

```java
ResultSet.TYPE_FORWARD_ONLY
```

The cursor normally moves forward:

```text
1 → 2 → 3 → 4
```

You typically use:

```java
rs.next();
```

You don't rely on moving backward.

Example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.CONCUR_READ_ONLY
    );
```

### Use when

You simply want to process rows sequentially.

---

# 7.2 `TYPE_SCROLL_INSENSITIVE`

This allows the cursor to move in different directions.

For example:

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

The `ResultSet` generally does not reflect certain subsequent changes made to the underlying database after the result was produced.

Example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY
    );
```

---

# 7.3 `TYPE_SCROLL_SENSITIVE`

This also allows scrolling:

```java
rs.next();
rs.previous();
rs.first();
rs.last();
rs.absolute(3);
```

But it is intended to be **sensitive to changes in the underlying data**.

```java
ResultSet.TYPE_SCROLL_SENSITIVE
```

### Important

Do **not** assume every database/JDBC driver supports every requested ResultSet type exactly as requested.

The driver/database may downgrade or otherwise handle unsupported features.

You can check what was actually created with:

```java
rs.getType();
```

---

# 8. ResultSet Concurrency

ResultSet concurrency determines whether the result set is intended to be **read-only** or **updatable**.

The two major constants are:

```java
ResultSet.CONCUR_READ_ONLY
```

and:

```java
ResultSet.CONCUR_UPDATABLE
```

---

# 8.1 `CONCUR_READ_ONLY`

The ResultSet is read-only.

```java
ResultSet.CONCUR_READ_ONLY
```

Typical usage:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.CONCUR_READ_ONLY
    );
```

You retrieve data:

```java
while (rs.next()) {
    System.out.println(
        rs.getString("name")
    );
}
```

You aren't using the ResultSet itself to update database rows.

---

# 8.2 `CONCUR_UPDATABLE`

An updatable ResultSet may allow you to modify rows through ResultSet methods.

For example:

```java
rs.updateString("name", "Ravi Kumar");
```

Then:

```java
rs.updateRow();
```

Conceptually:

```text
ResultSet row
     ↓
updateString()
     ↓
updateRow()
     ↓
Database row updated
```

Example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_SENSITIVE,
        ResultSet.CONCUR_UPDATABLE
    );
```

But support depends on the database, JDBC driver, SQL query, and ResultSet configuration. Not every query can produce an updatable ResultSet.

---

# 🔥 ResultSet Type vs Concurrency — Don't Mix Them Up

This is a very common confusion.

### ResultSet Type

Answers:

> **How can I move through the ResultSet?**

```text
FORWARD_ONLY
SCROLL_INSENSITIVE
SCROLL_SENSITIVE
```

### ResultSet Concurrency

Answers:

> **Can the ResultSet be updated?**

```text
READ_ONLY
UPDATABLE
```

Therefore:

```text
ResultSet
│
├── TYPE
│   ├── FORWARD_ONLY
│   ├── SCROLL_INSENSITIVE
│   └── SCROLL_SENSITIVE
│
└── CONCURRENCY
    ├── READ_ONLY
    └── UPDATABLE
```

---

# 🔥 Complete ResultSet Example

```java
String sql =
    "SELECT id, name, marks FROM student";

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
        id + " " + name + " " + marks
    );
}

rs.close();
ps.close();
```

Suppose database contains:

```text
101  Ravi   85.5
102  Kumar  90.0
103  Ali    78.5
```

Output:

```text
101 Ravi 85.5
102 Kumar 90.0
103 Ali 78.5
```

---

# 🧠 ResultSet ONEPAGE Map

```text
ResultSet
│
├── ResultSet Interface
│   └── Represents query result
│
├── next()
│   ├── Moves cursor to next row
│   ├── returns true/false
│   └── usually used in while(rs.next())
│
├── getInt()
│   └── Retrieves integer value
│
├── getString()
│   └── Retrieves String representation
│
├── getDouble()
│   └── Retrieves double value
│
├── getObject()
│   └── Retrieves value as Object
│
├── ResultSet Types
│   ├── TYPE_FORWARD_ONLY
│   ├── TYPE_SCROLL_INSENSITIVE
│   └── TYPE_SCROLL_SENSITIVE
│
└── ResultSet Concurrency
    ├── CONCUR_READ_ONLY
    └── CONCUR_UPDATABLE
```

---

# 🎯 Ultimate Memory

Remember these four relationships:

```text
executeQuery()
      ↓
 ResultSet
```

```text
ResultSet
   ↓
next()
   ↓
current row
```

```text
current row
   ↓
getInt()
getString()
getDouble()
getObject()
```

And:

```text
ResultSet TYPE
   ↓
controls cursor movement

ResultSet CONCURRENCY
   ↓
controls read-only/updatable behavior
```

### One-line exam definition

> **ResultSet is a JDBC interface that represents the tabular data returned by a database query and provides methods for navigating through rows and retrieving column values.**
