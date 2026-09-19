# 4. JDBC Steps in Java — 3LEVEL

The **3LEVEL method** means we will understand every JDBC step at **three depths**:

* 🟢 **LEVEL 1 — Basic:** What is it?
* 🟡 **LEVEL 2 — Working:** How does it work?
* 🔴 **LEVEL 3 — Deep/Technical:** What should you really understand?

---

# Complete JDBC Flow

```text
Java Application
       ↓
1. Load/Register Driver
       ↓
2. Establish Connection
       ↓
3. Create Statement
       ↓
4. Execute SQL
       ↓
5. Process Result
       ↓
6. Close Resources
```

### Memory formula

> **Driver → Connection → Statement → Execute → Result → Close**

---

# 1. Load / Register Driver

## 🟢 LEVEL 1 — Basic

A **JDBC Driver** is software that allows Java/JDBC to communicate with a particular database.

```text
Java
  ↓
JDBC
  ↓
JDBC Driver
  ↓
Database
```

For example:

```text
Java → JDBC → MySQL Driver → MySQL
```

The driver is normally provided as a **JAR dependency** in the project.

---

## 🟡 LEVEL 2 — Working

Traditionally, JDBC programs explicitly loaded the driver:

```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

Conceptually:

```text
Class.forName()
      ↓
Load driver class
      ↓
Driver becomes registered
      ↓
DriverManager can use it
```

---

## 🔴 LEVEL 3 — Technical

With **JDBC 4+**, explicit:

```java
Class.forName(...)
```

is normally unnecessary if the JDBC driver JAR is correctly configured.

Modern JDBC drivers use automatic driver discovery through Java's service-provider mechanism.

Therefore, this:

```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

may appear in older tutorials, but modern code commonly begins with:

```java
Connection con =
    DriverManager.getConnection(
        url, username, password
    );
```

### Important distinction

```text
JDBC API
   ↓
standard interfaces/classes

JDBC Driver
   ↓
database-specific implementation
```

---

# 2. Establish Connection

## 🟢 LEVEL 1 — Basic

After the driver is available, Java establishes a connection with the database.

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

The result is a:

```java
Connection
```

object.

---

## 🟡 LEVEL 2 — Working

Example:

```java
String url =
    "jdbc:mysql://localhost:3306/college";

Connection con =
    DriverManager.getConnection(
        url,
        "root",
        "password"
    );
```

Conceptually:

```text
Java Application
       ↓
DriverManager
       ↓
Suitable JDBC Driver
       ↓
Database
       ↓
Connection
```

### JDBC URL

```text
jdbc:mysql://localhost:3306/college
```

Conceptually:

```text
jdbc:       → JDBC
mysql:      → database/driver subprotocol
localhost   → database server
3306        → port
college     → target database/schema
```

The exact URL format depends on the JDBC driver/database.

---

## 🔴 LEVEL 3 — Technical

Don't confuse these three:

### `Driver`

Database-specific JDBC implementation.

### `DriverManager`

JDBC class that manages JDBC drivers and helps obtain connections.

### `Connection`

JDBC interface representing the database connection/session.

So:

```text
DriverManager
      ↓
getConnection()
      ↓
Connection
```

The `Connection` can subsequently create statement objects and also participate in transaction management.

For example:

```java
con.setAutoCommit(false);
con.commit();
con.rollback();
```

---

# 3. Create Statement

## 🟢 LEVEL 1 — Basic

After connecting to the database, Java needs an object through which SQL can be executed.

There are three important JDBC statement types:

```text
Statement
PreparedStatement
CallableStatement
```

---

## 🟡 LEVEL 2 — Working

### Statement

```java
Statement st =
    con.createStatement();
```

Example:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

### PreparedStatement

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );

ps.setInt(1, 101);
```

### CallableStatement

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

---

## 🔴 LEVEL 3 — Technical

Understand the purpose of each:

| Type                | Main purpose      |
| ------------------- | ----------------- |
| `Statement`         | Simple fixed SQL  |
| `PreparedStatement` | Parameterized SQL |
| `CallableStatement` | Stored procedures |

### Why `PreparedStatement`?

Instead of:

```java
String sql =
    "SELECT * FROM student WHERE id = "
    + id;
```

use:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );

ps.setInt(1, id);
```

The parameter is bound separately from the SQL text.

This is safer than constructing SQL by concatenating untrusted input and is the normal approach for parameterized SQL.

---

# 4. Execute SQL

## 🟢 LEVEL 1 — Basic

Now send the SQL to the database.

JDBC commonly provides:

```text
executeQuery()
executeUpdate()
execute()
```

---

## 🟡 LEVEL 2 — Working

### `executeQuery()`

Typically used for `SELECT`.

```java
ResultSet rs =
    ps.executeQuery();
```

Result:

```text
ResultSet
```

---

### `executeUpdate()`

Typically used for:

```text
INSERT
UPDATE
DELETE
```

Example:

```java
int count =
    ps.executeUpdate();
```

Result:

```text
number of affected rows
```

---

### `execute()`

More general execution method.

```java
boolean result =
    ps.execute();
```

It can be useful when handling statements whose result may be a `ResultSet` or an update count.

---

## 🔴 LEVEL 3 — Technical

Remember the return types:

```text
executeQuery()
      ↓
ResultSet

executeUpdate()
      ↓
int

execute()
      ↓
boolean
```

### Practical mapping

```text
SELECT
  ↓
executeQuery()

INSERT / UPDATE / DELETE
  ↓
executeUpdate()

General/multiple-result handling
  ↓
execute()
```

---

# 5. Process Result

## 🟢 LEVEL 1 — Basic

When a `SELECT` query returns rows, JDBC gives us a:

```java
ResultSet
```

Example:

```java
ResultSet rs =
    ps.executeQuery();
```

Suppose the database returns:

```text
101  Ravi
102  Kumar
103  Anil
```

We read these rows through `ResultSet`.

---

## 🟡 LEVEL 2 — Working

Use:

```java
while (rs.next()) {

    int id =
        rs.getInt("id");

    String name =
        rs.getString("name");

    System.out.println(
        id + " " + name
    );
}
```

The important method is:

```java
rs.next()
```

It moves the cursor to the next row.

---

## 🔴 LEVEL 3 — Technical

When a `ResultSet` is created, its cursor initially sits **before the first row**.

Conceptually:

```text
Cursor
  ↓
101 Ravi
102 Kumar
103 Anil
```

Calling:

```java
rs.next();
```

moves it:

```text
101 Ravi ← cursor
102 Kumar
103 Anil
```

Calling it again:

```text
101 Ravi
102 Kumar ← cursor
103 Anil
```

When there is no next row:

```java
rs.next()
```

returns:

```text
false
```

That's why we normally write:

```java
while (rs.next()) {
    ...
}
```

---

## Reading columns

By column name:

```java
rs.getInt("id");
rs.getString("name");
```

By column index:

```java
rs.getInt(1);
rs.getString(2);
```

### Important:

JDBC column indexes are **1-based**.

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

# 6. Close Resources

## 🟢 LEVEL 1 — Basic

After finishing database operations, release the JDBC resources.

Common resources:

```text
ResultSet
Statement / PreparedStatement
Connection
```

---

## 🟡 LEVEL 2 — Working

Traditional code:

```java
rs.close();
ps.close();
con.close();
```

Conceptually:

```text
ResultSet
    ↓
Statement
    ↓
Connection
```

---

## 🔴 LEVEL 3 — Technical

Modern Java generally recommends **try-with-resources**.

Example:

```java
try (
    Connection con =
        DriverManager.getConnection(
            url, user, password
        );

    PreparedStatement ps =
        con.prepareStatement(
            "SELECT * FROM student"
        );

    ResultSet rs =
        ps.executeQuery()
) {

    while (rs.next()) {
        System.out.println(
            rs.getInt("id") + " " +
            rs.getString("name")
        );
    }

}
```

The JDBC resources are automatically closed when the try block ends.

This also works correctly when an exception occurs.

JDBC resources such as `Connection`, `Statement`, and `ResultSet` implement/support `AutoCloseable`.

---

# 🔥 3LEVEL — Complete Comparison

| JDBC Step         | 🟢 Level 1                  | 🟡 Level 2                                              | 🔴 Level 3                                                                      |
| ----------------- | --------------------------- | ------------------------------------------------------- | ------------------------------------------------------------------------------- |
| **1. Driver**     | Make driver available       | Traditional `Class.forName()`                           | JDBC 4+ automatic discovery normally makes explicit loading unnecessary         |
| **2. Connection** | Connect Java to DB          | `DriverManager.getConnection()`                         | Returns a `Connection` representing a DB session                                |
| **3. Statement**  | Create SQL execution object | `Statement` / `PreparedStatement` / `CallableStatement` | Prefer parameter binding with `PreparedStatement` for runtime values            |
| **4. Execute**    | Run SQL                     | `executeQuery`, `executeUpdate`, `execute`              | Different methods produce `ResultSet`, update count, or general result handling |
| **5. Result**     | Read returned data          | `ResultSet` + `next()`                                  | Cursor starts before first row; column indexes are 1-based                      |
| **6. Close**      | Release resources           | `close()`                                               | Prefer try-with-resources for automatic cleanup                                 |

---

# 🧩 Complete Example

```java
import java.sql.*;

public class JdbcDemo {

    public static void main(String[] args) {

        String url =
            "jdbc:mysql://localhost:3306/college";

        String user = "root";
        String password = "password";

        String sql =
            "SELECT id, name FROM student";

        try (
            Connection con =
                DriverManager.getConnection(
                    url, user, password
                );

            PreparedStatement ps =
                con.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery()
        ) {

            while (rs.next()) {

                int id =
                    rs.getInt("id");

                String name =
                    rs.getString("name");

                System.out.println(
                    id + " " + name
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

### Identify the six steps:

```text
1. Driver
   ↓
   Modern automatic discovery

2. Connection
   ↓
   DriverManager.getConnection()

3. Statement
   ↓
   con.prepareStatement()

4. Execute
   ↓
   ps.executeQuery()

5. Result
   ↓
   while(rs.next())

6. Close
   ↓
   try-with-resources
```

---

# 🧠 Two Most Important Flows

## SELECT Flow

```text
Driver
  ↓
Connection
  ↓
PreparedStatement
  ↓
executeQuery()
  ↓
ResultSet
  ↓
rs.next()
  ↓
Read columns
  ↓
Close
```

---

## INSERT / UPDATE / DELETE Flow

```text
Driver
  ↓
Connection
  ↓
PreparedStatement
  ↓
executeUpdate()
  ↓
Update Count
  ↓
Close
```

---

# ⚠️ 3LEVEL Doubt Killer

### ❓ Is `DriverManager` the driver?

**No.**

```text
DriverManager ≠ JDBC Driver
```

---

### ❓ Is `Connection` the database?

**No.**

```text
Database ≠ Connection
```

`Connection` represents the JDBC application's connection/session with the database.

---

### ❓ Does `Connection` directly execute SQL?

Usually, you create a statement object from it:

```text
Connection
    ↓
PreparedStatement
    ↓
execute SQL
```

---

### ❓ Does every SQL operation return `ResultSet`?

**No.**

```text
SELECT
 ↓
ResultSet

INSERT / UPDATE / DELETE
 ↓
Update Count
```

---

### ❓ Why `rs.next()`?

Because the `ResultSet` cursor starts before the first row.

```text
Before first row
       ↓
   rs.next()
       ↓
First row
```

---

### ❓ Why `PreparedStatement`?

For parameterized SQL, it separates SQL structure from parameter values and is the standard safer approach compared with concatenating untrusted input into SQL.

---

### ❓ Is `Class.forName()` compulsory?

**Not normally with modern JDBC 4+ drivers.**

The driver JAR must still be correctly included.

---

# 🏆 Final 3LEVEL Memory Map

```text
             JDBC
              │
              ▼
      ┌───────────────┐
      │ 1. DRIVER     │
      │ Make driver   │
      │ available     │
      └───────┬───────┘
              ↓
      ┌───────────────┐
      │ 2. CONNECTION │
      │ Connect to DB │
      └───────┬───────┘
              ↓
      ┌───────────────┐
      │ 3. STATEMENT  │
      │ Prepare SQL   │
      └───────┬───────┘
              ↓
      ┌───────────────┐
      │ 4. EXECUTE    │
      │ Run SQL       │
      └───────┬───────┘
              ↓
       ┌──────┴──────┐
       ↓             ↓
    ResultSet     Update Count
       ↓
      5. PROCESS
       ↓
      6. CLOSE
```

## ⭐ One-line answer for exams

> **The basic JDBC workflow consists of making the JDBC driver available, establishing a database connection, creating a statement, executing SQL, processing the returned result or update count, and closing the JDBC resources.**

### Final formula:

```text
D → C → S → E → R → C

Driver
Connection
Statement
Execute
Result
Close
```

**That is the complete 3-level understanding of the six JDBC steps.**
