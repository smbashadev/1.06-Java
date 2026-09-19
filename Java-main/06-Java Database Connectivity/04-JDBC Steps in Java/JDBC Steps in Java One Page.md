# 4. JDBC Steps in Java — ONEPAGE

JDBC programming is commonly understood as a sequence of steps used to communicate between a Java application and a database.

## 🔥 The 6 JDBC Steps

```text
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

> **Memory:** **Load → Connect → Create → Execute → Process → Close**

---

# 1. Load/Register Driver

## What is it?

The JDBC driver is the software that allows Java/JDBC to communicate with a particular database.

Historically, Java programs explicitly loaded the driver:

```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

### Modern JDBC

With JDBC 4+ and a correctly configured driver JAR, explicit `Class.forName()` is normally **not required** because drivers can be discovered automatically.

So modern code usually goes directly to:

```java
Connection con =
    DriverManager.getConnection(url, user, password);
```

### Important

```text
JDBC API
   ↓
needs a suitable
   ↓
JDBC Driver
   ↓
communicates with Database
```

---

# 2. Establish Connection

## What is it?

A connection establishes communication between the Java application and the database.

Use:

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

Example:

```java
Connection con =
    DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/college",
        "root",
        "password"
    );
```

### Result

`getConnection()` returns:

```java
Connection
```

So:

```text
DriverManager
      ↓
getConnection()
      ↓
Connection object
```

The `Connection` object represents the JDBC connection/session used for database operations.

---

# 3. Create Statement

After obtaining the connection, create an object for sending SQL to the database.

There are three commonly encountered statement types:

```text
Statement
PreparedStatement
CallableStatement
```

### Statement

```java
Statement st = con.createStatement();
```

### PreparedStatement

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

### CallableStatement

Used to call stored procedures:

```java
CallableStatement cs =
    con.prepareCall("{call getStudents()}");
```

### Which should you generally prefer?

For parameterized SQL, prefer:

```java
PreparedStatement
```

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );

ps.setInt(1, 101);
```

It is safer and generally preferable to constructing SQL by string concatenation.

---

# 4. Execute SQL

Once the statement is created, execute the SQL.

The method depends on the operation.

### `executeQuery()`

Used for queries that return a result set, typically `SELECT`.

```java
ResultSet rs = ps.executeQuery();
```

### `executeUpdate()`

Used for `INSERT`, `UPDATE`, `DELETE`, and SQL statements that return an update count.

```java
int count = ps.executeUpdate();
```

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "UPDATE student SET name=? WHERE id=?"
    );

ps.setString(1, "Rahul");
ps.setInt(2, 101);

int count = ps.executeUpdate();
```

### `execute()`

Can be used when the result may be either a `ResultSet` or an update count.

```java
boolean result = ps.execute();
```

---

# 5. Process Result

If the SQL produces rows, JDBC gives you a `ResultSet`.

Example:

```java
ResultSet rs = ps.executeQuery();
```

Then process the rows:

```java
while (rs.next()) {

    int id = rs.getInt("id");

    String name = rs.getString("name");

    System.out.println(id + " " + name);
}
```

### Important

`ResultSet` represents the result of a query.

```text
Database
   ↓
ResultSet
   ↓
Java Application
```

The `next()` method moves the cursor to the next row.

```java
while (rs.next()) {
    // process current row
}
```

---

# 6. Close Resources

After completing the database operation, close resources.

Typical resources:

```text
ResultSet
PreparedStatement / Statement
Connection
```

Traditional explicit closing:

```java
rs.close();
ps.close();
con.close();
```

### Better approach: try-with-resources

Modern JDBC code should generally use **try-with-resources**:

```java
try (
    Connection con =
        DriverManager.getConnection(url, user, password);

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

The resources are automatically closed when the `try` block finishes.

---

# 🔥 Complete JDBC Program

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

                int id = rs.getInt("id");
                String name = rs.getString("name");

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

---

# 🧠 Program → 6 Steps Mapping

Look at the program and identify each step:

| Step                        | Code                             | Purpose                         |
| --------------------------- | -------------------------------- | ------------------------------- |
| **1. Load/Register Driver** | Usually automatic in modern JDBC | Makes suitable driver available |
| **2. Establish Connection** | `DriverManager.getConnection()`  | Connects to database            |
| **3. Create Statement**     | `con.prepareStatement()`         | Creates SQL execution object    |
| **4. Execute SQL**          | `ps.executeQuery()`              | Sends SQL for execution         |
| **5. Process Result**       | `while(rs.next())`               | Reads returned rows             |
| **6. Close Resources**      | try-with-resources               | Releases JDBC resources         |

---

# ⚠️ Important Modern JDBC Correction

Many older textbooks teach:

```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

as **Step 1**.

That is historically valid, but with modern JDBC 4+ drivers, explicit loading is normally unnecessary when the driver JAR is properly configured.

Therefore, modern JDBC is often effectively:

```text
1. Ensure driver is available/configured
2. Get Connection
3. Create Statement
4. Execute SQL
5. Process Result
6. Close Resources
```

---

# 🎯 Final Memory Diagram

```text
        JDBC PROGRAM
             │
             ▼
   ┌─────────────────────┐
   │ 1. Load/Register    │
   │    Driver            │
   └──────────┬──────────┘
              ▼
   ┌─────────────────────┐
   │ 2. Establish        │
   │    Connection        │
   └──────────┬──────────┘
              ▼
   ┌─────────────────────┐
   │ 3. Create Statement │
   │    /PreparedStmt     │
   └──────────┬──────────┘
              ▼
   ┌─────────────────────┐
   │ 4. Execute SQL      │
   └──────────┬──────────┘
              ▼
   ┌─────────────────────┐
   │ 5. Process Result   │
   │    ResultSet        │
   └──────────┬──────────┘
              ▼
   ┌─────────────────────┐
   │ 6. Close Resources  │
   └─────────────────────┘
```

## ⭐ One-line formula

> **Driver → Connection → Statement → Execute → Result → Close**

And the most important distinction:

```text
Connection  → connects
Statement   → sends SQL
execute     → runs SQL
ResultSet   → reads query results
close       → releases resources
```
