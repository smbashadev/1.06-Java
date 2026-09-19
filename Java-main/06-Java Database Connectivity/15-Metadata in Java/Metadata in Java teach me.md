# 15. Metadata in Java / TEACHME

Let's learn **JDBC Metadata** from the beginning, as if we are seeing it for the first time.

The goal is not just to memorize methods. By the end, you should understand **why metadata exists, where each metadata object comes from, what information it gives, and when to use each one.**

---

# 1. First: What does "Metadata" mean?

The word **metadata** means:

> **Data about data.**

Consider a student table:

```text
STUDENT
--------------------------------
ID       NAME       MARKS
--------------------------------
101      Ravi       85
102      John       90
103      Mary       78
```

The actual data is:

```text
101 Ravi 85
102 John 90
103 Mary 78
```

But information such as:

```text
Table name = STUDENT
Number of columns = 3
Column 1 = ID
Column 2 = NAME
Column 3 = MARKS
ID type = INTEGER
NAME type = VARCHAR
MARKS type = INTEGER
```

is **metadata**.

So:

```text
DATA
↓
101 Ravi 85

METADATA
↓
ID is an INTEGER column
NAME is a VARCHAR column
There are 3 columns
```

---

# 2. Why do we need metadata in JDBC?

Suppose Java executes:

```sql
SELECT * FROM student;
```

What if Java doesn't know:

* how many columns exist?
* what the columns are called?
* what types they have?
* what database is being used?
* what JDBC driver is being used?
* how many `?` parameters exist in a prepared statement?

We don't want to hard-code all this information.

JDBC therefore provides **metadata interfaces**.

There are three major ones:

```text
                    JDBC METADATA
                         |
          +--------------+--------------+
          |              |              |
          ↓              ↓              ↓
 DatabaseMetaData ResultSetMetaData ParameterMetaData
          |              |              |
          ↓              ↓              ↓
      Database        ResultSet      Parameters
       information     information    information
```

The easiest way to remember them:

> **DatabaseMetaData → Database**
> **ResultSetMetaData → ResultSet**
> **ParameterMetaData → Parameters**

---

# 3. First important question: Where does each one come from?

This is the most important thing to understand.

## `DatabaseMetaData`

Comes from:

```java
Connection
```

using:

```java
con.getMetaData();
```

---

## `ResultSetMetaData`

Comes from:

```java
ResultSet
```

using:

```java
rs.getMetaData();
```

---

## `ParameterMetaData`

Comes from:

```java
PreparedStatement
```

using:

```java
ps.getParameterMetaData();
```

Therefore:

```text
Connection
    ↓
DatabaseMetaData


ResultSet
    ↓
ResultSetMetaData


PreparedStatement
    ↓
ParameterMetaData
```

Keep this picture in your mind throughout the lesson.

---

# PART A — DatabaseMetaData

# 4. What is DatabaseMetaData?

Let's imagine you successfully connected Java to a database.

```java
Connection con =
    DriverManager.getConnection(
        url,
        user,
        password
    );
```

Now you want to ask:

> "What database am I connected to?"

> "What version is it?"

> "Which JDBC driver am I using?"

> "What tables exist?"

> "Does this database support transactions?"

Who answers these questions?

**`DatabaseMetaData`.**

---

# 5. Getting DatabaseMetaData

We use:

```java
DatabaseMetaData dbmd =
    con.getMetaData();
```

Think of it like this:

```text
Connection
    |
    | "Tell me about the database."
    ↓
DatabaseMetaData
```

The `Connection` represents the actual connection.

`DatabaseMetaData` represents **information about that connection's database environment**.

---

# 6. Simple example

```java
Connection con =
    DriverManager.getConnection(
        url,
        user,
        password
    );

DatabaseMetaData dbmd =
    con.getMetaData();

System.out.println(
    dbmd.getDatabaseProductName()
);
```

The result might be:

```text
MySQL
```

The exact result depends on the database you're connected to.

---

# 7. Database product information

We can ask:

## Database name

```java
dbmd.getDatabaseProductName();
```

For example:

```text
MySQL
```

---

## Database version

```java
dbmd.getDatabaseProductVersion();
```

---

## Major version

```java
dbmd.getDatabaseMajorVersion();
```

---

## Minor version

```java
dbmd.getDatabaseMinorVersion();
```

So:

```text
DatabaseMetaData
      |
      +-- Database name
      +-- Database version
      +-- Major version
      +-- Minor version
```

---

# 8. JDBC Driver information

Remember that JDBC uses a driver to communicate with the particular database.

We can ask:

> "Which JDBC driver am I using?"

Use:

```java
dbmd.getDriverName();
```

We can also ask for the driver version:

```java
dbmd.getDriverVersion();
```

And:

```java
dbmd.getDriverMajorVersion();
dbmd.getDriverMinorVersion();
```

So:

```text
DatabaseMetaData
      |
      +-- Database information
      |
      +-- JDBC Driver information
```

---

# 9. Connection information

We can also obtain information about the connection.

### URL

```java
dbmd.getURL();
```

For example:

```text
jdbc:mysql://localhost:3306/college
```

### Username

```java
dbmd.getUserName();
```

This tells us which database user is associated with the connection.

---

# 10. Discovering tables

Here's where `DatabaseMetaData` becomes really useful.

Suppose we don't know what tables exist in the database.

We can ask the metadata:

```java
ResultSet rs =
    dbmd.getTables(
        null,
        null,
        "%",
        new String[]{"TABLE"}
    );
```

Then:

```java
while (rs.next()) {

    System.out.println(
        rs.getString("TABLE_NAME")
    );
}
```

The database might contain:

```text
student
employee
department
course
```

and our program can discover those names dynamically.

---

# 11. Why does getTables() return ResultSet?

This sometimes confuses beginners.

You might expect:

```java
getTables()
```

to return some special table object.

But JDBC represents the metadata result as a `ResultSet`.

Conceptually:

```text
DatabaseMetaData
       |
       | getTables()
       ↓
    ResultSet
       |
       ↓
Table information
```

So you can use:

```java
while (rs.next()) {
    ...
}
```

just as you do with an ordinary query result.

---

# 12. Discovering columns

`DatabaseMetaData` can also help discover information about columns.

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

This can tell us about the columns belonging to the specified table.

---

# 13. Database capabilities

`DatabaseMetaData` isn't only about names and versions.

It can also answer capability questions.

For example:

```java
dbmd.supportsTransactions();
```

This asks:

> "Does this database/driver combination support transactions?"

The result is:

```text
true
```

or:

```text
false
```

There are many similar `supports...()` methods.

So think:

```text
DatabaseMetaData
      |
      +-- What database?
      +-- What version?
      +-- What driver?
      +-- What tables?
      +-- What columns?
      +-- What features are supported?
```

---

# 14. Real-world use of DatabaseMetaData

Imagine you're building a database management application.

You don't know beforehand whether the user connects to:

```text
MySQL
PostgreSQL
Oracle
SQL Server
```

Your application can inspect:

```java
DatabaseMetaData dbmd =
    con.getMetaData();
```

and discover information about the environment.

This is why metadata is useful for:

* database tools,
* administration tools,
* frameworks,
* debugging,
* database discovery,
* portable applications.

---

# PART B — ResultSetMetaData

Now let's move to the second type.

# 15. What is ResultSetMetaData?

`ResultSetMetaData` tells us:

> **"What columns are present in this ResultSet?"**

Suppose we execute:

```sql
SELECT id, name, salary
FROM employee;
```

The result might be:

```text
ID     NAME      SALARY
-----------------------
101    Ravi      50000
102    John      60000
103    Mary      70000
```

The rows are **data**.

But information such as:

```text
There are 3 columns.
Column 1 is ID.
Column 2 is NAME.
Column 3 is SALARY.
```

is **metadata**.

---

# 16. Getting ResultSetMetaData

Suppose:

```java
ResultSet rs =
    statement.executeQuery(
        "SELECT id, name, salary FROM employee"
    );
```

Now:

```java
ResultSetMetaData rsmd =
    rs.getMetaData();
```

Think:

```text
ResultSet
   |
   | "Tell me about your columns."
   ↓
ResultSetMetaData
```

---

# 17. Getting number of columns

Use:

```java
int count =
    rsmd.getColumnCount();
```

For:

```sql
SELECT id, name, salary
```

the result is:

```text
3
```

---

# 18. Important: Column indexes start at 1

This is extremely important.

In normal Java arrays:

```text
0 → first
1 → second
2 → third
```

But JDBC metadata uses:

```text
1 → first column
2 → second column
3 → third column
```

So:

```java
rsmd.getColumnName(1);
```

means:

> Give me information about the first column.

---

# 19. Getting column names

Use:

```java
rsmd.getColumnName(1);
```

For our example:

```text
1 → id
2 → name
3 → salary
```

You can loop:

```java
for (int i = 1;
     i <= rsmd.getColumnCount();
     i++) {

    System.out.println(
        rsmd.getColumnName(i)
    );
}
```

Output:

```text
id
name
salary
```

---

# 20. Column labels

There is another method:

```java
getColumnLabel()
```

Why do we need this?

Consider:

```sql
SELECT name AS student_name
FROM student;
```

The query gives a label:

```text
student_name
```

You can obtain the result column label using:

```java
rsmd.getColumnLabel(1);
```

This is especially useful when queries use aliases.

---

# 21. `getColumnName()` vs `getColumnLabel()`

Remember:

```text
getColumnName()
    ↓
Column name

getColumnLabel()
    ↓
Column label presented by the result
```

For example:

```sql
SELECT name AS student_name
FROM student;
```

Conceptually:

```text
Column name  → name
Column label → student_name
```

This distinction is particularly useful when building dynamic reports or database tools.

---

# 22. Getting SQL type

We can ask:

> "What JDBC SQL type does this column have?"

Use:

```java
rsmd.getColumnType(1);
```

It returns an integer corresponding to a constant in:

```java
java.sql.Types
```

For example:

```java
Types.INTEGER
Types.VARCHAR
Types.DOUBLE
```

---

# 23. Getting database type name

We can also use:

```java
rsmd.getColumnTypeName(1);
```

This gives the database type name.

For example:

```text
INTEGER
VARCHAR
DECIMAL
```

So remember:

```text
getColumnType()
       ↓
JDBC SQL type

getColumnTypeName()
       ↓
Database type name
```

---

# 24. Getting Java class

We can ask:

> "What Java class is normally associated with this column?"

Use:

```java
rsmd.getColumnClassName(1);
```

For example, an integer column might correspond to:

```text
java.lang.Integer
```

The exact result is driver-dependent.

---

# 25. Checking NULL information

Use:

```java
rsmd.isNullable(1);
```

It can return constants such as:

```java
ResultSetMetaData.columnNoNulls
ResultSetMetaData.columnNullable
ResultSetMetaData.columnNullableUnknown
```

This tells us what the metadata says about whether the result column can contain SQL `NULL`.

---

# 26. Precision and scale

For numeric columns we can use:

```java
rsmd.getPrecision(1);
rsmd.getScale(1);
```

Suppose:

```sql
salary DECIMAL(10,2)
```

Conceptually:

```text
Precision = 10
Scale = 2
```

So:

```text
DECIMAL(10,2)
       |
       +-- 10 total digits
       |
       +-- 2 digits after decimal point
```

Exact metadata values can depend on the database and JDBC driver.

---

# 27. Complete ResultSetMetaData example

```java
ResultSet rs =
    statement.executeQuery(
        "SELECT id, name, salary FROM employee"
    );

ResultSetMetaData md =
    rs.getMetaData();

int count =
    md.getColumnCount();

System.out.println(
    "Number of columns: " + count
);

for (int i = 1; i <= count; i++) {

    System.out.println(
        "Column Name: " +
        md.getColumnName(i)
    );

    System.out.println(
        "Column Label: " +
        md.getColumnLabel(i)
    );

    System.out.println(
        "Column Type: " +
        md.getColumnTypeName(i)
    );

    System.out.println(
        "Java Class: " +
        md.getColumnClassName(i)
    );
}
```

This program doesn't need to hard-code the number of columns.

---

# 28. Why is ResultSetMetaData powerful?

Suppose your program receives any SQL query.

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

You don't know the columns in advance.

Metadata allows the program to ask:

```text
How many columns?
What are their names?
What are their types?
```

This is called **runtime discovery**.

---

# PART C — ParameterMetaData

Now we come to the third type.

# 29. What is ParameterMetaData?

`ParameterMetaData` tells us information about the parameters of a `PreparedStatement`.

Consider:

```sql
SELECT *
FROM student
WHERE id = ?
AND name = ?;
```

There are two parameters:

```text
? → parameter 1
? → parameter 2
```

Java supplies them:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

`ParameterMetaData` lets us inspect information about those parameters.

---

# 30. Getting ParameterMetaData

First:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student " +
        "WHERE id = ? AND name = ?"
    );
```

Then:

```java
ParameterMetaData pmd =
    ps.getParameterMetaData();
```

Think:

```text
PreparedStatement
       |
       | "Tell me about your ? parameters."
       ↓
ParameterMetaData
```

---

# 31. Getting parameter count

Use:

```java
int count =
    pmd.getParameterCount();
```

For:

```sql
WHERE id = ? AND name = ?
```

the count is:

```text
2
```

So:

```text
Parameter 1 → ?
Parameter 2 → ?
```

---

# 32. Parameter indexes are 1-based too

Just like JDBC column indexes:

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
1 → id parameter
2 → name parameter
```

Then:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

---

# 33. Parameter type

We can ask for the JDBC type:

```java
pmd.getParameterType(1);
```

It may correspond to:

```java
Types.INTEGER
Types.VARCHAR
Types.DOUBLE
```

depending on the parameter and driver.

---

# 34. Parameter type name

Use:

```java
pmd.getParameterTypeName(1);
```

It may give a database type name such as:

```text
INTEGER
VARCHAR
```

Again, exact results depend on the driver.

---

# 35. Parameter mode

This is especially important when dealing with callable statements.

A parameter can conceptually be:

```text
IN
OUT
INOUT
```

`ParameterMetaData` has:

```java
pmd.getParameterMode(1);
```

Possible values include:

```java
ParameterMetaData.parameterModeIn
ParameterMetaData.parameterModeOut
ParameterMetaData.parameterModeInOut
ParameterMetaData.parameterModeUnknown
```

### IN

Java sends a value to the database.

```text
Java
 ↓
Database
```

### OUT

The database returns a value.

```text
Database
 ↓
Java
```

### INOUT

Value goes in and comes back out.

```text
Java
 ↓
Database
 ↓
Java
```

---

# 36. Parameter nullability

We can use:

```java
pmd.isNullable(1);
```

Possible constants include:

```java
ParameterMetaData.parameterNoNulls
ParameterMetaData.parameterNullable
ParameterMetaData.parameterNullableUnknown
```

---

# 37. Parameter precision and scale

For numeric parameters:

```java
pmd.getPrecision(1);
pmd.getScale(1);
```

Again, actual availability and values can depend on the JDBC driver.

---

# 38. Important warning about ParameterMetaData

This is a common source of confusion.

You might think:

> "If I ask `ParameterMetaData` for a parameter type, JDBC must always know it."

Not necessarily.

Different JDBC drivers have different capabilities in how completely they can determine parameter metadata.

Therefore:

> **ParameterMetaData is useful, but you should not assume every driver provides every piece of parameter metadata perfectly.**

This is especially important when writing portable JDBC applications.

---

# 39. Complete ParameterMetaData example

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

---

# 40. The Biggest Difference — Learn This Carefully

Suppose we have:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT id, name " +
        "FROM student " +
        "WHERE age > ?"
    );
```

Look carefully.

The SQL contains:

```text
id
name
?
```

There are:

```text
2 result columns
1 parameter
```

After execution:

```java
ResultSet rs =
    ps.executeQuery();
```

we can ask two different questions.

---

## Question 1

> "Tell me about the `?`."

Use:

```java
ps.getParameterMetaData();
```

Result:

```text
ParameterMetaData
       ↓
1 parameter
```

---

## Question 2

> "Tell me about the result columns."

Use:

```java
rs.getMetaData();
```

Result:

```text
ResultSetMetaData
       ↓
2 columns
```

This distinction is extremely important.

---

# 41. Three-question technique

Whenever you see JDBC metadata, ask:

### Question 1:

> **Am I asking about the database?**

Use:

```java
con.getMetaData()
```

→ `DatabaseMetaData`

---

### Question 2:

> **Am I asking about the query result?**

Use:

```java
rs.getMetaData()
```

→ `ResultSetMetaData`

---

### Question 3:

> **Am I asking about the `?` parameters?**

Use:

```java
ps.getParameterMetaData()
```

→ `ParameterMetaData`

---

# 42. One Complete Example

Let's put all three together.

```java
Connection con =
    DriverManager.getConnection(
        url,
        user,
        password
    );

// DATABASE METADATA
DatabaseMetaData dbmd =
    con.getMetaData();

System.out.println(
    "Database: " +
    dbmd.getDatabaseProductName()
);

System.out.println(
    "Driver: " +
    dbmd.getDriverName()
);


// PREPARED STATEMENT
PreparedStatement ps =
    con.prepareStatement(
        "SELECT id, name " +
        "FROM student " +
        "WHERE age > ?"
    );


// PARAMETER METADATA
ParameterMetaData pmd =
    ps.getParameterMetaData();

System.out.println(
    "Parameters: " +
    pmd.getParameterCount()
);


// SET PARAMETER
ps.setInt(1, 18);


// EXECUTE
ResultSet rs =
    ps.executeQuery();


// RESULTSET METADATA
ResultSetMetaData rsmd =
    rs.getMetaData();

System.out.println(
    "Columns: " +
    rsmd.getColumnCount()
);

for (int i = 1;
     i <= rsmd.getColumnCount();
     i++) {

    System.out.println(
        rsmd.getColumnLabel(i)
    );
}
```

Now notice the complete flow:

```text
                    Connection
                       |
                       ↓
                DatabaseMetaData
                       |
                       ↓
              Database information


               PreparedStatement
                   /       \
                  /         \
                 ↓           ↓
      ParameterMetaData      executeQuery()
             |                    |
             ↓                    ↓
        ? information         ResultSet
                                  |
                                  ↓
                         ResultSetMetaData
                                  |
                                  ↓
                           Column information
```

---

# 43. Real-Life Analogy

Let's use a **restaurant analogy**.

Imagine you're working with a restaurant.

## DatabaseMetaData

You ask:

> "Tell me about the restaurant."

You learn:

```text
Restaurant name
Location
Operating information
Available facilities
```

This is like:

```text
DatabaseMetaData
```

It tells you about the **database environment**.

---

## ResultSetMetaData

You receive an order:

```text
Pizza
Burger
Juice
```

You ask:

> "Tell me about this result/order."

You learn:

```text
Number of items
Item names
Item types
```

This is like:

```text
ResultSetMetaData
```

It tells you about the **query result**.

---

## ParameterMetaData

Before placing an order, there are fields:

```text
Quantity: ?
Size: ?
Flavor: ?
```

You ask:

> "What parameters does this order require?"

That's like:

```text
ParameterMetaData
```

It tells you about the **parameters**.

---

# 44. Common confusion: DatabaseMetaData vs ResultSetMetaData

Suppose you have:

```java
Connection con;
ResultSet rs;
```

If you want:

> Database name

use:

```java
con.getMetaData()
```

If you want:

> Number of columns returned by the query

use:

```java
rs.getMetaData()
```

Therefore:

```text
Database
    ↓
DatabaseMetaData

Query Result
    ↓
ResultSetMetaData
```

---

# 45. Common confusion: ResultSetMetaData vs ParameterMetaData

Suppose:

```sql
SELECT id, name
FROM student
WHERE age > ?
```

Count them:

```text
Columns:
id
name

Parameters:
?
```

Therefore:

```text
ResultSetMetaData
    ↓
2 columns

ParameterMetaData
    ↓
1 parameter
```

Never confuse the two.

---

# 46. Common confusion: Metadata vs actual data

Suppose:

```text
Student
------------------
101 Ravi
102 John
```

Actual data:

```text
101 Ravi
102 John
```

Metadata:

```text
Student table
2 columns
ID
NAME
```

So:

> **Metadata describes data; it is not the actual row data itself.**

---

# 47. Common confusion: Metadata vs Schema

A schema describes the structure of database objects.

For example:

```text
Student
 ├── ID
 ├── NAME
 └── MARKS
```

Metadata is information **about** that database and its objects.

So don't say:

> Metadata and schema are exactly the same thing.

They aren't.

---

# 48. Most Important Methods

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

Remember:

> **DatabaseMetaData = Database + Driver + Capabilities + Structure information**

---

# 49. ResultSetMetaData

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

Remember:

> **ResultSetMetaData = Result column information**

---

# 50. ParameterMetaData

```java
pmd.getParameterCount();

pmd.getParameterType(i);
pmd.getParameterTypeName(i);

pmd.getParameterMode(i);

pmd.isNullable(i);

pmd.getPrecision(i);
pmd.getScale(i);
```

Remember:

> **ParameterMetaData = `?` parameter information**

---

# 51. Final Mental Picture

If you remember only one diagram from this entire topic, remember this:

```text
                         JDBC
                          |
                 +--------+--------+
                 |        |        |
                 ↓        ↓        ↓
            Connection ResultSet PreparedStatement
                 |        |        |
                 ↓        ↓        ↓
          DatabaseMetaData ResultSetMetaData ParameterMetaData
                 |        |        |
                 ↓        ↓        ↓
             DATABASE   COLUMNS   PARAMETERS
              INFO       INFO       INFO
```

And the three magic methods are:

```java
con.getMetaData();
rs.getMetaData();
ps.getParameterMetaData();
```

### Say it aloud:

> **Connection → DatabaseMetaData → Database information.**

> **ResultSet → ResultSetMetaData → Result information.**

> **PreparedStatement → ParameterMetaData → Parameter information.**

Once those three relationships are clear, the entire **JDBC Metadata** topic becomes much easier.
