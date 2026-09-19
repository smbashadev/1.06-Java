# 15. Metadata in Java — ONEPAGE

## What is Metadata?

**Metadata means “data about data.”**

In JDBC, metadata means information **about the database, query result, or parameters**, rather than the actual data stored in the rows.

JDBC provides three important metadata interfaces:

```text
Metadata
   │
   ├── DatabaseMetaData
   │      ↓
   │   Information about Database
   │
   ├── ResultSetMetaData
   │      ↓
   │   Information about ResultSet/columns
   │
   └── ParameterMetaData
          ↓
      Information about ? parameters
```

---

# 1. DatabaseMetaData

## What is DatabaseMetaData?

`DatabaseMetaData` provides information **about the database and JDBC driver** to which the application is connected.

Package:

```java
java.sql.DatabaseMetaData
```

You obtain it from a `Connection`:

```java
DatabaseMetaData metaData =
    con.getMetaData();
```

Think:

```text
Connection
    ↓
getMetaData()
    ↓
DatabaseMetaData
    ↓
Information about database
```

---

## What information can it provide?

For example:

```java
metaData.getDatabaseProductName();
metaData.getDatabaseProductVersion();
metaData.getDriverName();
metaData.getDriverVersion();
metaData.getURL();
metaData.getUserName();
```

Example:

```java
DatabaseMetaData md =
    con.getMetaData();

System.out.println(
    md.getDatabaseProductName()
);

System.out.println(
    md.getDatabaseProductVersion()
);

System.out.println(
    md.getDriverName()
);

System.out.println(
    md.getDriverVersion()
);
```

Possible conceptual output:

```text
MySQL
8.x.x
MySQL Connector/J
8.x.x
```

The exact output depends on the database and JDBC driver.

---

## DatabaseMetaData can also describe database capabilities

For example, it provides methods for determining whether the database/driver supports particular features.

Conceptually:

```text
DatabaseMetaData
     │
     ├── Database information
     ├── Driver information
     ├── Supported features
     ├── Transaction capabilities
     ├── SQL capabilities
     └── Schema/table information
```

### Important methods

| Method                        | Gives information about            |
| ----------------------------- | ---------------------------------- |
| `getDatabaseProductName()`    | Database name                      |
| `getDatabaseProductVersion()` | Database version                   |
| `getDriverName()`             | JDBC driver name                   |
| `getDriverVersion()`          | JDBC driver version                |
| `getURL()`                    | Database connection URL            |
| `getUserName()`               | Connected database user            |
| `getTables()`                 | Tables matching specified criteria |
| `supportsTransactions()`      | Transaction support                |
| `getColumns()`                | Column metadata                    |

---

# 2. ResultSetMetaData

## What is ResultSetMetaData?

`ResultSetMetaData` provides information **about the columns contained in a `ResultSet`**.

Package:

```java
java.sql.ResultSetMetaData
```

You obtain it from a `ResultSet`:

```java
ResultSetMetaData metaData =
    rs.getMetaData();
```

Think:

```text
ResultSet
    ↓
getMetaData()
    ↓
ResultSetMetaData
    ↓
Information about result columns
```

---

## Example

Suppose:

```sql
SELECT id, name, salary FROM employee;
```

The `ResultSet` contains rows:

```text
1   John   50000
2   Mary   60000
```

`ResultSetMetaData` can tell you:

```text
Number of columns → 3

Column 1 → id
Column 2 → name
Column 3 → salary
```

---

## Important methods

```java
ResultSetMetaData md =
    rs.getMetaData();

int count =
    md.getColumnCount();

for (int i = 1; i <= count; i++) {

    System.out.println(
        md.getColumnName(i)
    );

    System.out.println(
        md.getColumnTypeName(i)
    );
}
```

### Important methods

| Method                  | Information                             |
| ----------------------- | --------------------------------------- |
| `getColumnCount()`      | Number of columns                       |
| `getColumnName(i)`      | Column name                             |
| `getColumnLabel(i)`     | Column label/alias                      |
| `getColumnType(i)`      | JDBC SQL type                           |
| `getColumnTypeName(i)`  | Database type name                      |
| `getColumnClassName(i)` | Java class normally used for the column |
| `isNullable(i)`         | Whether column can contain NULL         |
| `getPrecision(i)`       | Precision                               |
| `getScale(i)`           | Scale                                   |

### Very important:

Column indexes in `ResultSetMetaData` are **1-based**:

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

# 3. ParameterMetaData

## What is ParameterMetaData?

`ParameterMetaData` provides information **about the parameters of a `PreparedStatement`**.

Package:

```java
java.sql.ParameterMetaData
```

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM employee WHERE id = ? AND name = ?"
    );

ParameterMetaData pmd =
    ps.getParameterMetaData();
```

Think:

```text
PreparedStatement
       ↓
getParameterMetaData()
       ↓
ParameterMetaData
       ↓
Information about ?
parameters
```

---

## What is a parameter?

In:

```sql
SELECT * FROM employee
WHERE id = ? AND name = ?
```

there are two parameters:

```text
? → parameter 1
? → parameter 2
```

Then:

```java
ps.setInt(1, 101);
ps.setString(2, "John");
```

---

## Important ParameterMetaData methods

```java
int count =
    pmd.getParameterCount();
```

Gets the number of parameters.

```java
int type =
    pmd.getParameterType(1);
```

Gets the JDBC type of parameter 1 when the driver supports providing that information.

Other methods include:

```java
pmd.getParameterTypeName(1);
pmd.getParameterMode(1);
pmd.getPrecision(1);
pmd.getScale(1);
pmd.isNullable(1);
```

---

# 4. The Most Important Difference

This is the easiest way to remember the three.

| Metadata            | Obtained from       | Tells you about |
| ------------------- | ------------------- | --------------- |
| `DatabaseMetaData`  | `Connection`        | Database/driver |
| `ResultSetMetaData` | `ResultSet`         | Result columns  |
| `ParameterMetaData` | `PreparedStatement` | `?` parameters  |

### Memory trick

```text
Connection
    ↓
DatabaseMetaData
    ↓
DATABASE

ResultSet
    ↓
ResultSetMetaData
    ↓
RESULT

PreparedStatement
    ↓
ParameterMetaData
    ↓
PARAMETERS
```

---

# 5. Complete Relationship

```text
                    JDBC
                     │
        ┌────────────┼────────────┐
        │            │            │
        ↓            ↓            ↓
   Connection    ResultSet   PreparedStatement
        │            │            │
        ↓            ↓            ↓
DatabaseMetaData ResultSetMetaData ParameterMetaData
        │            │            │
        ↓            ↓            ↓
   Database      Result Columns    ? Parameters
   information
```

---

# 6. One Complete Example

```java
Connection con =
    DriverManager.getConnection(
        url, user, password
    );

// 1. DatabaseMetaData
DatabaseMetaData dbmd =
    con.getMetaData();

System.out.println(
    dbmd.getDatabaseProductName()
);

System.out.println(
    dbmd.getDriverName()
);


// 2. PreparedStatement
PreparedStatement ps =
    con.prepareStatement(
        "SELECT id, name FROM student WHERE id = ?"
    );


// 3. ParameterMetaData
ParameterMetaData pmd =
    ps.getParameterMetaData();

System.out.println(
    "Parameters: " +
    pmd.getParameterCount()
);


// 4. Execute query
ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();


// 5. ResultSetMetaData
ResultSetMetaData rsmd =
    rs.getMetaData();

System.out.println(
    "Columns: " +
    rsmd.getColumnCount()
);

for (
    int i = 1;
    i <= rsmd.getColumnCount();
    i++
) {

    System.out.println(
        rsmd.getColumnName(i)
    );
}
```

The complete flow:

```text
Connection
    │
    └── getMetaData()
           ↓
    DatabaseMetaData
           ↓
    Database information


PreparedStatement
    │
    └── getParameterMetaData()
           ↓
    ParameterMetaData
           ↓
    ? parameter information


ResultSet
    │
    └── getMetaData()
           ↓
    ResultSetMetaData
           ↓
    Column information
```

---

# 🔥 ONEPAGE Final Memory

```text
METADATA = DATA ABOUT DATA
```

### `DatabaseMetaData`

> **Information about the database and JDBC driver.**

```java
con.getMetaData();
```

### `ResultSetMetaData`

> **Information about columns in a ResultSet.**

```java
rs.getMetaData();
```

### `ParameterMetaData`

> **Information about parameters in a PreparedStatement.**

```java
ps.getParameterMetaData();
```

## Ultimate memory formula

```text
Connection       → DatabaseMetaData
ResultSet        → ResultSetMetaData
PreparedStatement → ParameterMetaData
```

Or simply:

> **DatabaseMetaData = DATABASE information; ResultSetMetaData = RESULT information; ParameterMetaData = PARAMETER (`?`) information.**
