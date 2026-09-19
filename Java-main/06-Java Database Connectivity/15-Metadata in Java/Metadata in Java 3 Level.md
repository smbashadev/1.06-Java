# 15. Metadata in Java / 3LEVEL

We will learn the three JDBC metadata concepts in **3 levels**:

* **LEVEL 1 — Beginner:** What is it?
* **LEVEL 2 — Intermediate:** How does it work and what methods are important?
* **LEVEL 3 — Advanced:** Deep understanding, differences, limitations, and practical use.

---

# 0. First Understand: What is Metadata?

**Metadata = data about data.**

Suppose a query produces:

```text
ID     NAME     MARKS
101    Ravi     85
102    John     90
```

The actual data is:

```text
101 Ravi 85
102 John 90
```

Information such as:

```text
There are 3 columns.
Column 1 is ID.
Column 2 is NAME.
Column 3 is MARKS.
ID is an integer.
NAME is a string.
```

is **metadata**.

JDBC provides three important metadata interfaces:

```text
                         JDBC METADATA
                              |
             +----------------+----------------+
             |                |                |
             ↓                ↓                ↓
      DatabaseMetaData  ResultSetMetaData  ParameterMetaData
             |                |                |
             ↓                ↓                ↓
        DATABASE          RESULT SET       PARAMETERS
       INFORMATION       INFORMATION       INFORMATION
```

The easiest memory trick:

> **DatabaseMetaData → Database**
> **ResultSetMetaData → ResultSet**
> **ParameterMetaData → Parameters**

---

# LEVEL 1 — BEGINNER

# 1. DatabaseMetaData

## What is DatabaseMetaData?

`DatabaseMetaData` provides information **about the database and JDBC environment** to which the Java application is connected.

It can tell us things such as:

* database name
* database version
* JDBC driver name
* JDBC driver version
* database URL
* username
* available tables
* available columns
* supported features

### How do we obtain it?

From `Connection`:

```java
DatabaseMetaData dbmd = con.getMetaData();
```

Relationship:

```text
Connection
    ↓
getMetaData()
    ↓
DatabaseMetaData
    ↓
Information about database
```

### Example

```java
DatabaseMetaData dbmd = con.getMetaData();

System.out.println(
    dbmd.getDatabaseProductName()
);
```

Possible output:

```text
MySQL
```

The actual result depends on the database.

---

# 2. ResultSetMetaData

## What is ResultSetMetaData?

`ResultSetMetaData` provides information **about the columns contained in a `ResultSet`**.

Suppose:

```java
ResultSet rs =
    stmt.executeQuery(
        "SELECT id, name, marks FROM student"
    );
```

We can ask:

* How many columns are there?
* What is the column name?
* What is the column type?
* What is the column label?
* What Java class corresponds to the column?

### How do we obtain it?

From `ResultSet`:

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
    ↓
Information about result columns
```

### Example

```java
ResultSetMetaData rsmd =
    rs.getMetaData();

System.out.println(
    rsmd.getColumnCount()
);
```

If the query returns:

```text
id
name
marks
```

output:

```text
3
```

---

# 3. ParameterMetaData

## What is ParameterMetaData?

`ParameterMetaData` provides information about the **parameters (`?`) of a `PreparedStatement`**.

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

There is one parameter:

```text
?
```

We can obtain information about it.

### How do we obtain it?

From `PreparedStatement`:

```java
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
       ↓
Information about ? parameters
```

We can ask:

```java
pmd.getParameterCount();
```

Output:

```text
1
```

---

# LEVEL 1 — MASTER TABLE

| Metadata            | Obtained from       | Describes            |
| ------------------- | ------------------- | -------------------- |
| `DatabaseMetaData`  | `Connection`        | Database/environment |
| `ResultSetMetaData` | `ResultSet`         | Result columns       |
| `ParameterMetaData` | `PreparedStatement` | `?` parameters       |

### Three lines to memorize

```java
con.getMetaData();
rs.getMetaData();
ps.getParameterMetaData();
```

---

# LEVEL 2 — INTERMEDIATE

Now let's understand each one more deeply.

---

# 4. DatabaseMetaData — Level 2

## 4.1 Database information

```java
dbmd.getDatabaseProductName();
```

Returns the database product name.

Example:

```text
MySQL
```

---

### Database version

```java
dbmd.getDatabaseProductVersion();
```

Returns the database version.

---

### Major/minor versions

```java
dbmd.getDatabaseMajorVersion();
dbmd.getDatabaseMinorVersion();
```

---

# 5. Driver information

`DatabaseMetaData` can also provide JDBC driver information.

### Driver name

```java
dbmd.getDriverName();
```

### Driver version

```java
dbmd.getDriverVersion();
```

Therefore:

```text
DatabaseMetaData
      |
      +---- Database information
      |
      +---- Driver information
```

---

# 6. URL and user information

### Database URL

```java
dbmd.getURL();
```

For example:

```text
jdbc:mysql://localhost:3306/college
```

### User

```java
dbmd.getUserName();
```

---

# 7. Finding tables

One powerful feature is discovering database tables.

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
DatabaseMetaData
       ↓
    getTables()
       ↓
    ResultSet
       ↓
Table information
```

---

# 8. Finding columns

We can also obtain information about columns:

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

So `DatabaseMetaData` can help discover the structure of the database.

---

# 9. Database capabilities

`DatabaseMetaData` can also tell us what features are supported.

Example:

```java
dbmd.supportsTransactions();
```

This asks:

> Does this database/driver combination support transactions?

The result is a boolean:

```text
true
```

or:

```text
false
```

There are many other `supports...()` methods.

---

# 10. ResultSetMetaData — Level 2

Suppose:

```java
ResultSet rs =
    stmt.executeQuery(
        "SELECT id, name, marks FROM student"
    );
```

Get metadata:

```java
ResultSetMetaData rsmd =
    rs.getMetaData();
```

---

# 11. Number of columns

```java
int count =
    rsmd.getColumnCount();
```

For:

```text
ID NAME MARKS
```

result:

```text
3
```

---

# 12. Column name

```java
rsmd.getColumnName(1);
```

The important point:

> JDBC column indexes are generally **1-based**.

Therefore:

```text
1 → first column
2 → second column
3 → third column
```

Not:

```text
0 → first column
```

---

# 13. Column label

```java
rsmd.getColumnLabel(1);
```

This is especially useful when SQL uses aliases.

Example:

```sql
SELECT name AS student_name
FROM student;
```

The result column can have the label:

```text
student_name
```

So:

```java
rsmd.getColumnLabel(1);
```

can give:

```text
student_name
```

---

# 14. Column type

```java
rsmd.getColumnType(1);
```

This returns the JDBC SQL type represented by an integer constant from:

```java
java.sql.Types
```

Examples include:

```java
Types.INTEGER
Types.VARCHAR
Types.DOUBLE
```

---

# 15. Column type name

```java
rsmd.getColumnTypeName(1);
```

This gives the database type name, such as:

```text
INTEGER
VARCHAR
DECIMAL
```

Remember:

```text
getColumnType()
       ↓
JDBC SQL type

getColumnTypeName()
       ↓
Database type name
```

---

# 16. Java class associated with column

```java
rsmd.getColumnClassName(1);
```

This gives the Java class name that the driver associates with the column.

For example, an integer column might be associated with:

```text
java.lang.Integer
```

Exact results can depend on the JDBC driver.

---

# 17. NULL information

```java
rsmd.isNullable(1);
```

Possible values include:

```java
ResultSetMetaData.columnNoNulls
ResultSetMetaData.columnNullable
ResultSetMetaData.columnNullableUnknown
```

This provides metadata about whether the column can contain SQL `NULL`.

---

# 18. Precision and scale

For numeric columns:

```java
rsmd.getPrecision(1);
rsmd.getScale(1);
```

For example:

```sql
salary DECIMAL(10,2)
```

conceptually:

```text
Precision = 10
Scale     = 2
```

---

# 19. ParameterMetaData — Level 2

Suppose:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student " +
        "WHERE id = ? AND marks > ?"
    );
```

There are two parameters:

```text
? → parameter 1
? → parameter 2
```

Get metadata:

```java
ParameterMetaData pmd =
    ps.getParameterMetaData();
```

---

# 20. Parameter count

```java
pmd.getParameterCount();
```

Output:

```text
2
```

---

# 21. Parameter type

```java
pmd.getParameterType(1);
```

This returns the JDBC type corresponding to the parameter, subject to the capabilities of the JDBC driver.

---

# 22. Parameter type name

```java
pmd.getParameterTypeName(1);
```

This may return something like:

```text
INTEGER
```

or another database-specific type name.

---

# 23. Parameter mode

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

Conceptually:

```text
IN
 ↓
Java → Database

OUT
 ↓
Database → Java

INOUT
 ↓
Java → Database → Java
```

This is particularly relevant to callable statements and stored procedures.

---

# 24. Parameter NULL information

```java
pmd.isNullable(1);
```

Possible values:

```java
ParameterMetaData.parameterNoNulls
ParameterMetaData.parameterNullable
ParameterMetaData.parameterNullableUnknown
```

---

# 25. Precision and scale

For numeric parameters:

```java
pmd.getPrecision(1);
pmd.getScale(1);
```

Again, the exact information available depends on the JDBC driver and database.

---

# LEVEL 3 — ADVANCED

Now we connect everything together and remove the common doubts.

---

# 26. DatabaseMetaData — Level 3

`DatabaseMetaData` is not describing one particular query.

It describes the **database environment associated with a JDBC connection**.

Think:

```text
Connection
    |
    +---- Which database?
    +---- Which version?
    +---- Which driver?
    +---- Which URL?
    +---- Which user?
    +---- Which tables?
    +---- Which columns?
    +---- Which capabilities?
```

Therefore, if you want to build a database administration or discovery tool, `DatabaseMetaData` is extremely useful.

---

# 27. ResultSetMetaData — Level 3

`ResultSetMetaData` is tied to a **particular `ResultSet`**.

Suppose:

```sql
SELECT id, name FROM student;
```

The result has:

```text
id
name
```

But another query:

```sql
SELECT id, name, marks, address FROM student;
```

has:

```text
id
name
marks
address
```

Each `ResultSet` can therefore have different metadata.

```text
Query 1
   ↓
ResultSet 1
   ↓
ResultSetMetaData 1
   ↓
2 columns


Query 2
   ↓
ResultSet 2
   ↓
ResultSetMetaData 2
   ↓
4 columns
```

This is why `ResultSetMetaData` is useful for **dynamic result processing**.

---

# 28. ParameterMetaData — Level 3

`ParameterMetaData` is tied to a particular `PreparedStatement`.

For example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

There is:

```text
1 parameter
```

But:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student " +
        "WHERE id = ? AND marks > ?"
    );
```

has:

```text
2 parameters
```

So:

```text
PreparedStatement
       ↓
ParameterMetaData
       ↓
Information about its parameters
```

---

# 29. The most important comparison

Consider this SQL:

```sql
SELECT id, name
FROM student
WHERE age > ?;
```

There are two completely different concepts here.

### Result columns

```text
id
name
```

Therefore:

```java
ResultSetMetaData
```

describes:

```text
2 columns
```

### Parameter

```text
?
```

Therefore:

```java
ParameterMetaData
```

describes:

```text
1 parameter
```

So:

```text
SELECT id, name
       ↑       ↑
       |       |
       +-------+---- ResultSetMetaData


WHERE age > ?
            ↑
            |
            +--------- ParameterMetaData
```

This is one of the most important distinctions in JDBC.

---

# 30. DatabaseMetaData vs ResultSetMetaData

### Question:

> "What database am I connected to?"

```java
DatabaseMetaData dbmd =
    con.getMetaData();
```

### Question:

> "What columns did my query return?"

```java
ResultSetMetaData rsmd =
    rs.getMetaData();
```

So:

```text
DATABASE
   ↓
DatabaseMetaData


RESULT
   ↓
ResultSetMetaData
```

---

# 31. ResultSetMetaData vs ParameterMetaData

### Question:

> "What columns are in my result?"

```java
rs.getMetaData();
```

### Question:

> "What parameters does my PreparedStatement contain?"

```java
ps.getParameterMetaData();
```

So:

```text
ResultSet
   ↓
ResultSetMetaData
   ↓
Columns


PreparedStatement
   ↓
ParameterMetaData
   ↓
Parameters
```

---

# 32. Metadata does not mean actual data

This is another important distinction.

Suppose:

```text
ID     NAME
101    Ravi
102    John
```

`ResultSet` contains the actual rows:

```text
101 Ravi
102 John
```

`ResultSetMetaData` tells us about the columns:

```text
2 columns
ID
NAME
```

Therefore:

```text
ResultSet
    ↓
Actual result data


ResultSetMetaData
    ↓
Information about result structure
```

---

# 33. Metadata does not mean only table structure

Another common misconception:

> "Metadata only means column names."

No.

Metadata can contain much more information.

For example:

```text
DatabaseMetaData
    → database version
    → driver
    → tables
    → columns
    → capabilities

ResultSetMetaData
    → column count
    → names
    → labels
    → types
    → Java classes
    → nullability
    → precision
    → scale

ParameterMetaData
    → parameter count
    → parameter types
    → parameter modes
    → nullability
    → precision
    → scale
```

---

# 34. Complete 3-level mental model

## LEVEL 1

Just remember:

```text
DatabaseMetaData
       ↓
Database information


ResultSetMetaData
       ↓
Result information


ParameterMetaData
       ↓
Parameter information
```

---

## LEVEL 2

Remember where they come from:

```text
Connection
    ↓
con.getMetaData()
    ↓
DatabaseMetaData


ResultSet
    ↓
rs.getMetaData()
    ↓
ResultSetMetaData


PreparedStatement
    ↓
ps.getParameterMetaData()
    ↓
ParameterMetaData
```

---

## LEVEL 3

Understand **what problem each solves**:

```text
"Tell me about the database."
             ↓
      DatabaseMetaData


"Tell me about my query result."
             ↓
      ResultSetMetaData


"Tell me about my ? parameters."
             ↓
      ParameterMetaData
```

---

# 35. Final Revision Table

| Feature             | DatabaseMetaData     | ResultSetMetaData  | ParameterMetaData           |
| ------------------- | -------------------- | ------------------ | --------------------------- |
| Describes           | Database/environment | Result columns     | Statement parameters        |
| Obtained from       | `Connection`         | `ResultSet`        | `PreparedStatement`         |
| Main method         | `con.getMetaData()`  | `rs.getMetaData()` | `ps.getParameterMetaData()` |
| Database name       | ✅                    | ❌                  | ❌                           |
| Driver information  | ✅                    | ❌                  | ❌                           |
| Table information   | ✅                    | ❌                  | ❌                           |
| Column count        | ❌*                   | ✅                  | ❌                           |
| Result column names | ❌                    | ✅                  | ❌                           |
| Result column types | ❌                    | ✅                  | ❌                           |
| Parameter count     | ❌                    | ❌                  | ✅                           |
| Parameter types     | ❌                    | ❌                  | ✅                           |
| Parameter modes     | ❌                    | ❌                  | ✅                           |

* `DatabaseMetaData` can provide database/schema/table/column structure information, but it is not the metadata object for a particular query's result columns.

---

# 36. The One Diagram You Must Remember

```text
                         JDBC
                          |
             +------------+------------+
             |            |            |
             ↓            ↓            ↓
        Connection     ResultSet   PreparedStatement
             |            |            |
             ↓            ↓            ↓
   DatabaseMetaData  ResultSetMetaData ParameterMetaData
             |            |            |
             ↓            ↓            ↓
        DATABASE       RESULT SET      PARAMETERS
        INFORMATION    INFORMATION     INFORMATION
```

### Final memory sentence:

> **Connection gives DatabaseMetaData, ResultSet gives ResultSetMetaData, and PreparedStatement gives ParameterMetaData.**

And their jobs are:

> **DatabaseMetaData → "Tell me about the database."**

> **ResultSetMetaData → "Tell me about the result columns."**

> **ParameterMetaData → "Tell me about the `?` parameters."**
