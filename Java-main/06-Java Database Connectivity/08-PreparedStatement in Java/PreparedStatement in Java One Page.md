# 8. PreparedStatement in Java — ONEPAGE

`PreparedStatement` is one of the **most important JDBC interfaces** because it allows us to execute **parameterized SQL**, improves safety against SQL injection, and can be efficient when the same SQL structure is executed repeatedly.

---

# 1. PreparedStatement Interface

`PreparedStatement` is an interface in:

```java
java.sql.PreparedStatement
```

It extends `Statement`.

```text
Statement
    ↑
PreparedStatement
```

We obtain it from a `Connection`:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

The `?` is called a **parameter placeholder**.

### Basic flow

```text
Connection
    ↓
prepareStatement(SQL)
    ↓
PreparedStatement
    ↓
set parameter values
    ↓
execute
    ↓
Database
```

---

# 2. Parameters

A parameter is a value that we supply to a `?` placeholder in SQL.

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);
```

Here:

```text
? → parameter
```

We supply its value using:

```java
ps.setInt(1, 101);
```

The number `1` means:

> Set the **first `?`**.

### Multiple parameters

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ?";
```

Then:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

Important:

> JDBC parameter indexes start from **1**, not 0.

```text
?       ?
↑       ↑
1       2
```

---

# 3. `setInt()`

`setInt()` assigns an integer value to a parameter.

Syntax:

```java
ps.setInt(parameterIndex, value);
```

Example:

```java
ps.setInt(1, 101);
```

SQL:

```java
"SELECT * FROM student WHERE id = ?"
```

becomes logically:

```text
id = 101
```

without manually concatenating the value into the SQL string.

---

# 4. `setString()`

`setString()` assigns a `String` value to a parameter.

Syntax:

```java
ps.setString(parameterIndex, value);
```

Example:

```java
ps.setString(1, "Ravi");
```

SQL:

```java
"SELECT * FROM student WHERE name = ?"
```

### Important

Don't manually add quotes around the `?`.

❌ Wrong:

```java
"WHERE name = '?'"
```

✅ Correct:

```java
"WHERE name = ?"
```

Then:

```java
ps.setString(1, "Ravi");
```

JDBC handles the appropriate parameter representation.

---

# 5. `setDouble()`

`setDouble()` assigns a `double` value to a parameter.

Syntax:

```java
ps.setDouble(parameterIndex, value);
```

Example:

```java
String sql =
    "SELECT * FROM product WHERE price > ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setDouble(1, 500.50);
```

The first `?` receives:

```text
500.50
```

---

# 6. `executeQuery()`

`PreparedStatement` also has:

```java
executeQuery()
```

It is used when the SQL produces a `ResultSet`.

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();
```

Notice something important:

With `PreparedStatement`, we normally **don't pass the SQL again** to `executeQuery()`.

We already supplied it here:

```java
con.prepareStatement(sql);
```

Then:

```java
ps.executeQuery();
```

### Flow

```text
SQL + ?
   ↓
prepareStatement()
   ↓
setInt()
   ↓
executeQuery()
   ↓
ResultSet
```

---

# 7. `executeUpdate()`

Use `executeUpdate()` when the prepared SQL produces an update count.

Common examples:

```text
INSERT
UPDATE
DELETE
```

Example:

```java
String sql =
    "UPDATE student SET marks = ? WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 95);
ps.setInt(2, 101);

int count =
    ps.executeUpdate();
```

If one row was updated:

```text
count = 1
```

### Remember

```text
executeQuery()
       ↓
   ResultSet

executeUpdate()
       ↓
      int
```

---

# 8. SQL Injection

## What is SQL Injection?

SQL injection is a security vulnerability where malicious input changes the intended meaning of dynamically constructed SQL.

### Vulnerable approach

Suppose:

```java
String name = userInput;

String sql =
    "SELECT * FROM student " +
    "WHERE name = '" + name + "'";

Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(sql);
```

The user's input becomes part of the SQL **syntax itself**.

That is dangerous.

---

## PreparedStatement solution

Instead:

```java
String sql =
    "SELECT * FROM student WHERE name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, userInput);

ResultSet rs =
    ps.executeQuery();
```

The SQL structure and parameter value are handled separately.

Conceptually:

```text
SQL structure
     +
parameter value
     ↓
PreparedStatement
     ↓
Database
```

This is the standard JDBC approach for preventing SQL injection through parameter values.

---

# 9. Statement vs PreparedStatement

| Feature                    | `Statement`          | `PreparedStatement`                           |
| -------------------------- | -------------------- | --------------------------------------------- |
| Interface                  | Yes                  | Yes                                           |
| Extends                    | —                    | `Statement`                                   |
| SQL parameters (`?`)       | No parameter binding | Yes                                           |
| `setInt()`                 | ❌                    | ✅                                             |
| `setString()`              | ❌                    | ✅                                             |
| `setDouble()`              | ❌                    | ✅                                             |
| SQL injection protection   | Not inherently       | Protects parameter values when used correctly |
| Repeated parameterized SQL | Less suitable        | Well suited                                   |
| SQL supplied at execution  | Yes                  | SQL structure supplied when prepared          |
| Typical use                | Simple/static SQL    | Parameterized SQL                             |

---

# 🔥 Most Important Difference

### `Statement`

```java
String sql =
    "SELECT * FROM student WHERE id = " + id;

Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(sql);
```

SQL and data are manually combined.

### `PreparedStatement`

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, id);

ResultSet rs =
    ps.executeQuery();
```

SQL structure and parameter value are kept separate.

---

# 🧠 Complete Example

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);
ps.setString(2, "Ravi");

ResultSet rs =
    ps.executeQuery();

while (rs.next()) {
    System.out.println(
        rs.getInt("id") + " " +
        rs.getString("name")
    );
}
```

For an update:

```java
String sql =
    "UPDATE student SET marks = ? WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setDouble(1, 95.5);
ps.setInt(2, 101);

int count =
    ps.executeUpdate();
```

---

# 🏆 ONEPAGE Memory Map

```text
                 PreparedStatement
                         │
             prepareStatement(sql)
                         │
                         ▼
                       SQL
                         │
                    ?   ?   ?
                    │   │   │
                    ▼   ▼   ▼
                 setInt()
              setString()
                setDouble()
                         │
             ┌───────────┴───────────┐
             │                       │
             ▼                       ▼
      executeQuery()          executeUpdate()
             │                       │
             ▼                       ▼
         ResultSet                  int
```

### Golden rules

```text
?              → Parameter placeholder
setInt()       → int parameter
setString()    → String parameter
setDouble()    → double parameter

executeQuery() → ResultSet
executeUpdate()→ int

PreparedStatement
        ↓
Parameterized SQL
        ↓
Safer against SQL injection
```

> **Best practical rule:** Whenever SQL contains values coming from users, applications, or variables, prefer `PreparedStatement` with parameter binding instead of building SQL through string concatenation.
