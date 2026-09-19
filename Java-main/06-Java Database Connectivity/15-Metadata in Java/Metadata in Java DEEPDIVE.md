# 15. Metadata in Java — DEEPDIVE

**Metadata = information about data.**

In JDBC, metadata allows a Java program to discover information about the database, query results, columns, and parameters **at runtime**, instead of having to know everything in advance.

The three major JDBC metadata interfaces are:

```text
JDBC Metadata
│
├── DatabaseMetaData
│   └── Information about database + JDBC driver
│
├── ResultSetMetaData
│   └── Information about ResultSet columns
│
└── ParameterMetaData
    └── Information about PreparedStatement parameters
```

---

# 1. Why do we need Metadata?

Imagine a Java program executes:

```sql
SELECT * FROM student;
```

Suppose you don't know beforehand:

* How many columns are returned?
* What are their names?
* What SQL types do they have?
* Which Java classes correspond to them?
* Can they contain `NULL`?
* What database are you connected to?
* What JDBC driver is being used?
* How many `?` parameters does a prepared statement have?

JDBC metadata lets your program discover such information.

Without metadata, you might need to hard-code everything:

```java
System.out.println(rs.getInt("id"));
System.out.println(rs.getString("name"));
System.out.println(rs.getDouble("salary"));
```

With metadata, a program can inspect the structure dynamically.

---

# 2. The Three Metadata Interfaces

| Interface           | Obtained from       | Describes                          |
| ------------------- | ------------------- | ---------------------------------- |
| `DatabaseMetaData`  | `Connection`        | Database and JDBC driver           |
| `ResultSetMetaData` | `ResultSet`         | Columns returned by a query        |
| `ParameterMetaData` | `PreparedStatement` | Parameters of a prepared statement |

The most important relationship to memorize is:

```text
Connection
    ↓
DatabaseMetaData
    ↓
DATABASE information


ResultSet
    ↓
ResultSetMetaData
    ↓
COLUMN information


PreparedStatement
    ↓
ParameterMetaData
    ↓
PARAMETER information
```

---

# PART 1 — DatabaseMetaData

# 3. What is `DatabaseMetaData`?

`DatabaseMetaData` is a JDBC interface that provides information about:

1. the database,
2. the database version,
3. the JDBC driver,
4. driver version,
5. supported features,
6. tables,
7. columns,
8. schemas/catalogs,
9. transaction capabilities,
10. SQL capabilities.

It belongs to:

```java
java.sql.DatabaseMetaData
```

---

# 4. How do we obtain DatabaseMetaData?

You obtain it from a `Connection`.

```java
DatabaseMetaData dbmd =
    con.getMetaData();
```

Relationship:

```text
Connection object
       ↓
getMetaData()
       ↓
DatabaseMetaData object
```

Example:

```java
Connection con =
    DriverManager.getConnection(
        url,
        user,
        password
    );

DatabaseMetaData dbmd =
    con.getMetaData();
```

---

# 5. Does DatabaseMetaData contain actual database records?

**No.**

This is important.

Suppose your table contains:

```text
101  John
102  Mary
103  David
```

`DatabaseMetaData` does not represent those rows.

Instead, it describes the **database environment and structure/capabilities**.

Think:

```text
Actual data
    ↓
101 John
102 Mary

Metadata
    ↓
Database name
Database version
Driver
Tables
Columns
Capabilities
```

---

# 6. Database information

## `getDatabaseProductName()`

Returns the database product name.

```java
String name =
    dbmd.getDatabaseProductName();

System.out.println(name);
```

Conceptual output:

```text
MySQL
```

Could instead be another database product depending on your connection.

---

## `getDatabaseProductVersion()`

Returns the database product version.

```java
System.out.println(
    dbmd.getDatabaseProductVersion()
);
```

---

## `getDatabaseMajorVersion()`

Returns the major database version.

```java
int version =
    dbmd.getDatabaseMajorVersion();
```

---

## `getDatabaseMinorVersion()`

Returns the minor database version.

```java
int version =
    dbmd.getDatabaseMinorVersion();
```

---

# 7. JDBC Driver information

`DatabaseMetaData` can also tell you about the JDBC driver.

### `getDriverName()`

```java
System.out.println(
    dbmd.getDriverName()
);
```

### `getDriverVersion()`

```java
System.out.println(
    dbmd.getDriverVersion()
);
```

### `getDriverMajorVersion()`

```java
System.out.println(
    dbmd.getDriverMajorVersion()
);
```

### `getDriverMinorVersion()`

```java
System.out.println(
    dbmd.getDriverMinorVersion()
);
```

So:

```text
DatabaseMetaData
       │
       ├── Database information
       │
       └── Driver information
```

---

# 8. Connection information

### `getURL()`

Returns the URL associated with the connection.

```java
System.out.println(
    dbmd.getURL()
);
```

For example, conceptually:

```text
jdbc:mysql://localhost:3306/college
```

### `getUserName()`

Returns the user name associated with the connection.

```java
System.out.println(
    dbmd.getUserName()
);
```

---

# 9. Discovering tables

One of the powerful features of `DatabaseMetaData` is discovering database objects.

```java
ResultSet tables =
    dbmd.getTables(
        null,
        null,
        "%",
        new String[]{"TABLE"}
    );
```

Then:

```java
while (tables.next()) {

    System.out.println(
        tables.getString("TABLE_NAME")
    );
}
```

Conceptually:

```text
Database
│
├── student
├── employee
├── department
└── course
```

The metadata API can help discover such objects.

---

# 10. Why does `getTables()` return a ResultSet?

This is a subtle but important point.

You might think:

```java
getTables()
```

should return a `List<Table>`.

But JDBC uses a `ResultSet` to represent tabular metadata results.

Therefore:

```text
DatabaseMetaData
      ↓
getTables()
      ↓
ResultSet
      ↓
Rows describing tables
```

You can then use:

```java
while (tables.next()) {
    ...
}
```

---

# 11. Discovering columns

`DatabaseMetaData` can also provide information about columns.

For example:

```java
ResultSet columns =
    dbmd.getColumns(
        null,
        null,
        "student",
        "%"
    );
```

Then:

```java
while (columns.next()) {

    System.out.println(
        columns.getString("COLUMN_NAME")
    );
}
```

So:

```text
DatabaseMetaData
      ↓
getColumns()
      ↓
ResultSet
      ↓
Column metadata
```

---

# 12. Database capabilities

`DatabaseMetaData` can also answer questions such as:

```java
dbmd.supportsTransactions();
```

This returns whether transactions are supported.

There are many other `supports...()` methods.

Conceptually:

```text
Does database support X?
        ↓
DatabaseMetaData
        ↓
supportsX()
        ↓
true / false
```

This is particularly useful for applications that need to adapt to different database environments.

---

# 13. Why is DatabaseMetaData useful?

It is useful in:

### Database administration tools

A tool can discover:

```text
Database
Tables
Columns
Indexes
Keys
```

### Frameworks

Frameworks can inspect database capabilities and structure.

### Development tools

IDE/database tools can dynamically display schemas.

### Portable applications

Applications can determine database capabilities rather than blindly assuming them.

### Diagnostics

You can print:

```text
Database
Version
Driver
Driver Version
URL
```

to diagnose environment problems.

---

# PART 2 — ResultSetMetaData

# 14. What is `ResultSetMetaData`?

`ResultSetMetaData` provides information about the **columns in a `ResultSet`**.

Package:

```java
java.sql.ResultSetMetaData
```

You obtain it using:

```java
ResultSetMetaData rsmd =
    rs.getMetaData();
```

Relationship:

```text
ResultSet
    ↓
getMetaData()
    ↓
ResultSetMetaData
```

---

# 15. Example ResultSet

Suppose:

```sql
SELECT id, name, salary
FROM employee;
```

The result might look like:

```text
id    name       salary
-----------------------
101   John       50000
102   Mary       60000
103   David      70000
```

The actual data is:

```text
101 John 50000
102 Mary 60000
103 David 70000
```

The metadata is:

```text
3 columns
id
name
salary
```

plus information such as types, precision, nullability, etc.

---

# 16. How to obtain ResultSetMetaData

```java
ResultSet rs =
    ps.executeQuery();

ResultSetMetaData rsmd =
    rs.getMetaData();
```

Now:

```java
rsmd
```

describes the columns of that result.

---

# 17. `getColumnCount()`

Returns the number of columns in the `ResultSet`.

```java
int count =
    rsmd.getColumnCount();

System.out.println(count);
```

If the query returns:

```sql
SELECT id, name, salary FROM employee;
```

then:

```text
count = 3
```

---

# 18. Column indexes are 1-based

This is an extremely important JDBC rule.

```java
rsmd.getColumnName(1);
```

means first column.

```java
rsmd.getColumnName(2);
```

means second column.

```java
rsmd.getColumnName(3);
```

means third column.

Not:

```text
0 → first column
```

JDBC uses:

```text
1 → first
2 → second
3 → third
```

This differs from normal Java array indexing.

---

# 19. `getColumnName()`

Returns the column name.

```java
String name =
    rsmd.getColumnName(1);
```

Example:

```text
id
```

---

# 20. `getColumnLabel()`

This is very important when SQL aliases are involved.

Suppose:

```sql
SELECT
    name AS student_name
FROM student;
```

The column's underlying name may be:

```text
name
```

while its result label can be:

```text
student_name
```

You can retrieve the label using:

```java
rsmd.getColumnLabel(1);
```

### Why does this matter?

For dynamically generated reports, UI tables, and query tools, the **label presented by the query** may be more useful than the underlying column name.

---

# 21. `getColumnName()` vs `getColumnLabel()`

This is a common interview question.

| Method             | Purpose                                     |
| ------------------ | ------------------------------------------- |
| `getColumnName()`  | Column name                                 |
| `getColumnLabel()` | Column label, often reflecting an SQL alias |

Example:

```sql
SELECT name AS student_name
FROM student;
```

Conceptually:

```text
getColumnName()
      ↓
name

getColumnLabel()
      ↓
student_name
```

Exact behavior can depend on driver details and JDBC specification behavior, but this is the key distinction to understand.

---

# 22. `getColumnType()`

Returns the JDBC SQL type as an integer constant from `java.sql.Types`.

```java
int type =
    rsmd.getColumnType(1);
```

For example, it could correspond to:

```java
Types.INTEGER
```

or:

```java
Types.VARCHAR
```

---

# 23. `getColumnTypeName()`

Returns the database-specific type name.

```java
String typeName =
    rsmd.getColumnTypeName(1);
```

For example, depending on the database:

```text
INT
VARCHAR
DECIMAL
```

So:

```text
getColumnType()
      ↓
JDBC type code

getColumnTypeName()
      ↓
Database type name
```

---

# 24. `getColumnClassName()`

Returns the fully qualified Java class name that the JDBC driver recommends for retrieving the column.

```java
String className =
    rsmd.getColumnClassName(1);
```

For example, an integer column may correspond to:

```text
java.lang.Integer
```

The exact result depends on the driver and SQL type.

---

# 25. `isNullable()`

Checks whether the column may contain SQL `NULL`.

```java
int nullable =
    rsmd.isNullable(1);
```

The return value uses constants such as:

```java
ResultSetMetaData.columnNoNulls
ResultSetMetaData.columnNullable
ResultSetMetaData.columnNullableUnknown
```

Important:

```text
columnNullable
        ≠
Java null automatically
```

It describes the metadata about whether the database result column may contain SQL `NULL`.

---

# 26. Precision and Scale

For numeric columns:

```java
rsmd.getPrecision(column);
rsmd.getScale(column);
```

Example:

```sql
salary DECIMAL(10,2)
```

Conceptually:

```text
Precision = 10
Scale     = 2
```

Meaning:

```text
DECIMAL(10,2)
       │
       ├── 10 → total significant decimal digits
       └── 2  → digits after decimal point
```

Exact metadata reporting can depend on the database/driver.

---

# 27. Complete ResultSetMetaData example

```java
ResultSet rs =
    ps.executeQuery();

ResultSetMetaData md =
    rs.getMetaData();

int count =
    md.getColumnCount();

for (int i = 1; i <= count; i++) {

    System.out.println(
        "Column: " +
        md.getColumnName(i)
    );

    System.out.println(
        "Label: " +
        md.getColumnLabel(i)
    );

    System.out.println(
        "Type: " +
        md.getColumnTypeName(i)
    );

    System.out.println(
        "Java Class: " +
        md.getColumnClassName(i)
    );
}
```

This allows the program to inspect the result structure dynamically.

---

# 28. Why is ResultSetMetaData useful?

Imagine a generic database tool.

You don't know whether the user will execute:

```sql
SELECT * FROM student;
```

or:

```sql
SELECT * FROM employee;
```

or:

```sql
SELECT * FROM department;
```

You can't hard-code:

```java
rs.getString("name");
```

for every possible query.

Instead:

```java
ResultSetMetaData md =
    rs.getMetaData();

int count =
    md.getColumnCount();

for (int i = 1; i <= count; i++) {

    System.out.println(
        md.getColumnLabel(i)
    );
}
```

Now the program can discover the result structure at runtime.

---

# PART 3 — ParameterMetaData

# 29. What is `ParameterMetaData`?

`ParameterMetaData` provides information about the **parameters of a `PreparedStatement`**.

Package:

```java
java.sql.ParameterMetaData
```

Consider:

```sql
SELECT *
FROM student
WHERE id = ?
AND name = ?;
```

There are two parameter markers:

```text
? → parameter 1
? → parameter 2
```

You can inspect them through `ParameterMetaData`.

---

# 30. How do we obtain ParameterMetaData?

From a `PreparedStatement`:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );

ParameterMetaData pmd =
    ps.getParameterMetaData();
```

Relationship:

```text
PreparedStatement
       ↓
getParameterMetaData()
       ↓
ParameterMetaData
```

---

# 31. What is a parameter?

In:

```sql
SELECT *
FROM student
WHERE id = ?
```

the `?` is a **parameter marker**.

Then Java supplies its value:

```java
ps.setInt(1, 101);
```

So:

```text
SQL:
id = ?

Java:
setInt(1, 101)
```

---

# 32. Parameter count

Use:

```java
int count =
    pmd.getParameterCount();
```

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student " +
        "WHERE id = ? AND name = ?"
    );

ParameterMetaData pmd =
    ps.getParameterMetaData();

System.out.println(
    pmd.getParameterCount()
);
```

Conceptual output:

```text
2
```

---

# 33. Parameter indexes are also 1-based

Again:

```text
1 → first parameter
2 → second parameter
3 → third parameter
```

For:

```sql
WHERE id = ? AND name = ?
```

we have:

```text
parameter 1 → id
parameter 2 → name
```

---

# 34. `getParameterType()`

You can ask for the JDBC type of a parameter:

```java
int type =
    pmd.getParameterType(1);
```

The result is a value from `java.sql.Types`, when the driver can provide the information.

For example:

```text
Types.INTEGER
Types.VARCHAR
Types.DOUBLE
```

---

# 35. Important Driver Caveat

This is a **major DOUBTKILLER point**.

Don't assume that every JDBC driver can always provide complete parameter metadata.

For example:

```java
pmd.getParameterType(1);
```

may have limitations depending on:

* JDBC driver
* database
* SQL statement
* driver configuration

Some drivers may not be able to determine certain parameter properties until additional information is available.

Therefore:

> **ParameterMetaData is useful, but its reported information can be more driver-dependent than beginners often expect.**

---

# 36. `getParameterTypeName()`

Returns the type name associated with a parameter when supported.

```java
String type =
    pmd.getParameterTypeName(1);
```

Conceptually:

```text
Parameter 1
     ↓
VARCHAR
```

or:

```text
INTEGER
```

depending on the database/driver.

---

# 37. `getParameterMode()`

This is particularly relevant to callable statements.

```java
pmd.getParameterMode(1);
```

Possible modes include:

```java
ParameterMetaData.parameterModeIn
ParameterMetaData.parameterModeOut
ParameterMetaData.parameterModeInOut
ParameterMetaData.parameterModeUnknown
```

Think:

```text
IN
 ↓
Java sends value to database

OUT
 ↓
Database returns value

INOUT
 ↓
Java sends value
AND
database returns value
```

For ordinary `PreparedStatement` parameters, `IN` is the usual mode.

---

# 38. `getPrecision()` and `getScale()`

For numeric parameters, metadata can also provide:

```java
pmd.getPrecision(1);
pmd.getScale(1);
```

Again, the exact availability and values can depend on the driver.

---

# 39. `isNullable()`

You can ask whether a parameter is allowed to be nullable:

```java
pmd.isNullable(1);
```

The result uses constants such as:

```java
ParameterMetaData.parameterNoNulls
ParameterMetaData.parameterNullable
ParameterMetaData.parameterNullableUnknown
```

---

# 40. Complete ParameterMetaData example

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student " +
        "WHERE id = ? AND name = ?"
    );

ParameterMetaData pmd =
    ps.getParameterMetaData();

int count =
    pmd.getParameterCount();

System.out.println(
    "Parameter count: " + count
);

for (int i = 1; i <= count; i++) {

    System.out.println(
        "Parameter: " + i
    );

    System.out.println(
        "Type: " +
        pmd.getParameterTypeName(i)
    );

    System.out.println(
        "Mode: " +
        pmd.getParameterMode(i)
    );
}
```

Again, exact metadata returned depends on the JDBC driver.

---

# 41. DatabaseMetaData vs ResultSetMetaData

This distinction is extremely important.

Suppose:

```java
Connection con = ...;
ResultSet rs = ...;
```

### DatabaseMetaData

```java
con.getMetaData();
```

asks:

> "Tell me about the database/driver."

### ResultSetMetaData

```java
rs.getMetaData();
```

asks:

> "Tell me about the columns in this query result."

Therefore:

```text
Connection
   ↓
DatabaseMetaData
   ↓
Database

ResultSet
   ↓
ResultSetMetaData
   ↓
Result columns
```

---

# 42. ResultSetMetaData vs ParameterMetaData

Suppose:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT id, name " +
        "FROM student " +
        "WHERE age > ?"
    );
```

After executing:

```java
ResultSet rs =
    ps.executeQuery();
```

Now you have two completely different things.

### ParameterMetaData

```java
ps.getParameterMetaData();
```

describes:

```text
?
```

### ResultSetMetaData

```java
rs.getMetaData();
```

describes:

```text
id
name
```

Therefore:

```text
PreparedStatement
       │
       └── ParameterMetaData
                 ↓
                ?

ResultSet
       │
       └── ResultSetMetaData
                 ↓
             id, name
```

---

# 43. Very Common Confusion

Suppose:

```sql
SELECT id, name
FROM student
WHERE age > ?
```

How many parameters?

```text
1
```

How many result columns?

```text
2
```

Therefore:

```text
ParameterMetaData
    → 1 parameter

ResultSetMetaData
    → 2 columns
```

Do **not** confuse:

```text
parameter count
```

with:

```text
column count
```

They are completely different.

---

# 44. Complete Metadata Flow

Let's put everything together.

```java
Connection con = ...;

DatabaseMetaData dbmd =
    con.getMetaData();

PreparedStatement ps =
    con.prepareStatement(
        "SELECT id, name " +
        "FROM student " +
        "WHERE age > ?"
    );

ParameterMetaData pmd =
    ps.getParameterMetaData();

ps.setInt(1, 18);

ResultSet rs =
    ps.executeQuery();

ResultSetMetaData rsmd =
    rs.getMetaData();
```

Now:

```text
                         Connection
                             │
                             ↓
                    DatabaseMetaData
                             │
                             ↓
                  Database / Driver info


                    PreparedStatement
                       │          │
                       │          ↓
                       │    ParameterMetaData
                       │          │
                       │          ↓
                       │       ? info
                       │
                       ↓
                    executeQuery()
                       │
                       ↓
                    ResultSet
                       │
                       ↓
                ResultSetMetaData
                       │
                       ↓
                   Column info
```

---

# 45. Real-World Example: Generic Table Viewer

One of the best ways to understand `ResultSetMetaData` is to imagine a database viewer.

The program executes an unknown query:

```java
ResultSet rs =
    statement.executeQuery(sql);
```

It doesn't know:

```text
How many columns?
What are their names?
What types?
```

So:

```java
ResultSetMetaData md =
    rs.getMetaData();

int columns =
    md.getColumnCount();

for (int i = 1; i <= columns; i++) {

    System.out.print(
        md.getColumnLabel(i) + "\t"
    );
}

System.out.println();

while (rs.next()) {

    for (int i = 1; i <= columns; i++) {

        System.out.print(
            rs.getObject(i) + "\t"
        );
    }

    System.out.println();
}
```

This is powerful because the program doesn't have to know the result structure beforehand.

---

# 46. Why `getObject()` is useful with Metadata

Suppose metadata says:

```text
3 columns
```

but you don't know their types.

Instead of writing:

```java
rs.getInt(1);
rs.getString(2);
rs.getDouble(3);
```

you can dynamically retrieve:

```java
Object value =
    rs.getObject(i);
```

Combined:

```text
ResultSetMetaData
       ↓
Discover columns
       ↓
getObject()
       ↓
Retrieve values dynamically
```

This pattern is common in generic database tools.

---

# 47. Is Metadata the same as Schema?

**No.**

Another important distinction.

### Schema

The database structure itself:

```text
Tables
Columns
Constraints
Indexes
Relationships
```

### Metadata

Information describing data/database structure/capabilities.

So metadata can **describe the schema**, but metadata itself is not simply the schema.

Think:

```text
Schema
 ↓
Actual database structure

Metadata
 ↓
Information describing that structure
```

---

# 48. Is Metadata actual data?

No.

Suppose:

```text
Student table

101 John
102 Mary
```

Actual data:

```text
101 John
102 Mary
```

Metadata:

```text
Table name = Student
Column count = 2
Column 1 = id
Column 2 = name
```

---

# 49. Metadata is runtime information

One major advantage is that the Java application can inspect information dynamically.

For example:

```java
int count =
    rsmd.getColumnCount();
```

The value can change depending on the query.

So metadata supports:

```text
Runtime discovery
```

rather than requiring everything to be hard-coded.

---

# 50. Complete Comparison Table

| Feature               | DatabaseMetaData                        | ResultSetMetaData     | ParameterMetaData            |
| --------------------- | --------------------------------------- | --------------------- | ---------------------------- |
| Describes             | Database/driver                         | ResultSet             | PreparedStatement parameters |
| Obtained from         | `Connection`                            | `ResultSet`           | `PreparedStatement`          |
| Main method           | `con.getMetaData()`                     | `rs.getMetaData()`    | `ps.getParameterMetaData()`  |
| Describes rows?       | No                                      | No, describes columns | No                           |
| Describes columns?    | Can provide database column information | Yes, result columns   | No                           |
| Describes `?`         | No                                      | No                    | Yes                          |
| Database info         | Yes                                     | No                    | No                           |
| Driver info           | Yes                                     | No                    | No                           |
| Column count          | Not result-set count                    | Yes                   | No                           |
| Parameter count       | No                                      | No                    | Yes                          |
| Database capabilities | Yes                                     | No                    | No                           |

---

# 51. The Three Interfaces — Mental Model

Think of a JDBC application as asking three questions.

### Question 1

> **"Where am I connected?"**

```java
con.getMetaData()
```

Answer:

```text
DatabaseMetaData
```

---

### Question 2

> **"What did my query return?"**

```java
rs.getMetaData()
```

Answer:

```text
ResultSetMetaData
```

---

### Question 3

> **"What parameters does my prepared statement have?"**

```java
ps.getParameterMetaData()
```

Answer:

```text
ParameterMetaData
```

---

# 52. Important Methods at a Glance

## DatabaseMetaData

```java
dbmd.getDatabaseProductName();
dbmd.getDatabaseProductVersion();
dbmd.getDriverName();
dbmd.getDriverVersion();
dbmd.getURL();
dbmd.getUserName();
dbmd.getTables(...);
dbmd.getColumns(...);
dbmd.supportsTransactions();
```

---

## ResultSetMetaData

```java
rsmd.getColumnCount();
rsmd.getColumnName(i);
rsmd.getColumnLabel(i);
rsmd.getColumnType(i);
rsmd.getColumnTypeName(i);
rsmd.getColumnClassName(i);
rsmd.isNullable(i);
rsmd.getPrecision(i);
rsmd.getScale(i);
```

---

## ParameterMetaData

```java
pmd.getParameterCount();
pmd.getParameterType(i);
pmd.getParameterTypeName(i);
pmd.getParameterMode(i);
pmd.isNullable(i);
pmd.getPrecision(i);
pmd.getScale(i);
```

---

# 53. Major Interview Traps

### Trap 1

```java
con.getMetaData()
```

does **not** return `ResultSetMetaData`.

It returns:

```java
DatabaseMetaData
```

---

### Trap 2

```java
rs.getMetaData()
```

does **not** describe the database.

It describes the columns in that `ResultSet`.

---

### Trap 3

```java
ps.getParameterMetaData()
```

does **not** describe the result columns.

It describes the parameters.

---

### Trap 4

For:

```sql
SELECT id, name
FROM student
WHERE age > ?
```

there are:

```text
2 result columns
1 parameter
```

---

### Trap 5

JDBC column/parameter indexes are generally **1-based**, not 0-based.

```text
1 → first
2 → second
3 → third
```

---

### Trap 6

`ParameterMetaData` information is not guaranteed to be equally complete across all JDBC drivers.

Don't assume every driver can always infer every property.

---

### Trap 7

Metadata doesn't mean actual records.

```text
Data:
101 John

Metadata:
id is an INTEGER column
```

---

# 54. Final Deep-Dive Summary

```text
                         JDBC METADATA
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
          ↓                   ↓                   ↓
 DatabaseMetaData      ResultSetMetaData    ParameterMetaData
          │                   │                   │
          ↓                   ↓                   ↓
      Connection           ResultSet       PreparedStatement
          │                   │                   │
          ↓                   ↓                   ↓
    Database/Driver      Result columns       ? parameters
    information           information         information
```

### The deepest memory rule:

> **`DatabaseMetaData` tells you about the environment you are connected to. `ResultSetMetaData` tells you about the columns your query returned. `ParameterMetaData` tells you about the parameters your prepared statement accepts.**

And the three acquisition methods are:

```java
con.getMetaData();              // DatabaseMetaData

rs.getMetaData();               // ResultSetMetaData

ps.getParameterMetaData();      // ParameterMetaData
```

That relationship is the **core of JDBC Metadata**.
