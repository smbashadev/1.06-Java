# 8. PreparedStatement in Java — 3LEVEL

We will learn **every sub-concept at 3 levels**:

* 🟢 **Level 1 — Beginner:** What is it?
* 🟡 **Level 2 — Intermediate:** How does it work?
* 🔴 **Level 3 — Expert:** What are the important rules, traps, and interview points?

---

# 1. PreparedStatement Interface

## 🟢 Level 1 — Beginner

`PreparedStatement` is a JDBC interface used to execute **parameterized SQL statements**.

Package:

```java
import java.sql.PreparedStatement;
```

Instead of writing:

```sql
SELECT * FROM student WHERE id = 101
```

we write:

```sql
SELECT * FROM student WHERE id = ?
```

The `?` is filled later.

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);
```

Then:

```java
ps.setInt(1, 101);
```

Then:

```java
ResultSet rs =
    ps.executeQuery();
```

### Basic formula

```text
SQL with ?
    ↓
prepareStatement()
    ↓
PreparedStatement
    ↓
set value
    ↓
execute
```

---

## 🟡 Level 2 — Intermediate

`PreparedStatement` is a child interface of `Statement`.

Conceptually:

```text
Statement
    ↑
PreparedStatement
```

It is designed especially for SQL statements containing **parameters**.

Example:

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
```

There are two parameters:

```text
WHERE id = ? AND name = ?
          ↑              ↑
          1              2
```

---

## 🔴 Level 3 — Expert

The important distinction is:

```text
prepareStatement()
       ↓
creates a PreparedStatement
```

It does **not mean the SQL has been executed**.

Similarly:

```java
ps.setInt(1, 101);
```

does **not execute** the SQL.

It binds a value to parameter 1.

Actual execution occurs through methods such as:

```java
executeQuery()
executeUpdate()
execute()
```

Also, `PreparedStatement` is particularly appropriate when the same SQL structure is executed repeatedly with different parameter values.

---

# 2. Parameters

## 🟢 Level 1 — Beginner

A parameter is represented by:

```text
?
```

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";
```

The `?` is a placeholder.

Then:

```java
ps.setInt(1, 101);
```

means:

```text
first ? = 101
```

---

## 🟡 Level 2 — Intermediate

Multiple parameters are numbered from **1**.

Example:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ?";
```

Parameter positions:

```text
        ?             ?
        ↓             ↓
        1             2
```

Therefore:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

Important:

> JDBC parameter indexes start at **1**, not 0.

---

## 🔴 Level 3 — Expert

Suppose:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND marks > ?";
```

Then:

```java
ps.setInt(1, 101);
ps.setDouble(2, 80.0);
```

The parameter positions correspond to the order of `?` placeholders.

```text
? #1 → id
? #2 → marks
```

A parameter represents a **value**, not arbitrary SQL syntax.

You cannot generally use:

```sql
SELECT * FROM ?
```

to substitute a table name, or use:

```sql
ORDER BY ?
```

expecting it to become an arbitrary column name.

Think:

```text
? = DATA VALUE
```

not:

```text
? = SQL CODE
```

---

# 3. `setInt()`

## 🟢 Level 1 — Beginner

`setInt()` is used to place an `int` value into a parameter.

Syntax:

```java
ps.setInt(parameterIndex, value);
```

Example:

```java
ps.setInt(1, 101);
```

Meaning:

```text
Parameter 1 → 101
```

---

## 🟡 Level 2 — Intermediate

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

Flow:

```text
id = ?
     ↓
setInt(1, 101)
     ↓
id parameter receives 101
```

---

## 🔴 Level 3 — Expert

Remember the parameter index and Java type are separate concepts.

```java
ps.setInt(1, 101);
```

means:

```text
1   → parameter position
101 → integer value
```

It does not mean:

```text
1 → column number
```

It identifies the **parameter placeholder position**.

And parameter positions start at **1**.

❌

```java
ps.setInt(0, 101);
```

✅

```java
ps.setInt(1, 101);
```

---

# 4. `setString()`

## 🟢 Level 1 — Beginner

`setString()` puts a Java `String` into a parameter.

Syntax:

```java
ps.setString(parameterIndex, value);
```

Example:

```java
ps.setString(1, "Ravi");
```

---

## 🟡 Level 2 — Intermediate

Example:

```java
String sql =
    "SELECT * FROM student WHERE name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, "Ravi");

ResultSet rs =
    ps.executeQuery();
```

Notice that we **don't** write:

```java
"WHERE name = 'Ravi'"
```

ourselves.

We write:

```java
"WHERE name = ?"
```

and use:

```java
ps.setString(1, "Ravi");
```

---

## 🔴 Level 3 — Expert

Do not manually add SQL quotes around the placeholder.

❌ Wrong:

```java
String sql =
    "SELECT * FROM student WHERE name = '?'";
```

✅ Correct:

```java
String sql =
    "SELECT * FROM student WHERE name = ?";
```

Then:

```java
ps.setString(1, "Ravi");
```

The JDBC driver handles the parameter value appropriately.

---

# 5. `setDouble()`

## 🟢 Level 1 — Beginner

`setDouble()` puts a Java `double` into a parameter.

Syntax:

```java
ps.setDouble(parameterIndex, value);
```

Example:

```java
ps.setDouble(1, 90.5);
```

---

## 🟡 Level 2 — Intermediate

Example:

```java
String sql =
    "SELECT * FROM student WHERE marks > ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setDouble(1, 80.5);

ResultSet rs =
    ps.executeQuery();
```

Conceptually:

```text
marks > ?
        ↓
marks > 80.5
```

---

## 🔴 Level 3 — Expert

The important concept is **type-specific parameter binding**.

```text
Java value       Method

int              setInt()
String           setString()
double           setDouble()
```

There are many more:

```java
setLong()
setFloat()
setBoolean()
setDate()
setTimestamp()
setBigDecimal()
setBytes()
setObject()
```

The correct setter should generally match the Java value and the intended SQL type.

---

# 6. `executeQuery()`

## 🟢 Level 1 — Beginner

`executeQuery()` executes a SQL query that returns a result set.

It returns:

```java
ResultSet
```

Example:

```java
ResultSet rs =
    ps.executeQuery();
```

Usually used with:

```sql
SELECT
```

---

## 🟡 Level 2 — Intermediate

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();

while (rs.next()) {
    System.out.println(
        rs.getString("name")
    );
}
```

Flow:

```text
SELECT
  ↓
executeQuery()
  ↓
ResultSet
  ↓
rs.next()
  ↓
read rows
```

---

## 🔴 Level 3 — Expert

`executeQuery()` is intended for statements that produce a tabular result set.

Typical example:

```sql
SELECT ...
```

The return type is:

```java
ResultSet
```

Also remember:

```java
ps.executeQuery();
```

is different from the `Statement` style where SQL can be supplied directly.

With an already-prepared SQL statement, you normally use:

```java
ps.executeQuery();
```

not:

```java
ps.executeQuery(sql);
```

---

# 7. `executeUpdate()`

## 🟢 Level 1 — Beginner

`executeUpdate()` is generally used for:

```text
INSERT
UPDATE
DELETE
```

It returns:

```java
int
```

The `int` normally represents the number of affected rows for those DML operations.

Example:

```java
int count =
    ps.executeUpdate();
```

---

## 🟡 Level 2 — Intermediate

### UPDATE

```java
String sql =
    "UPDATE student " +
    "SET marks = ? " +
    "WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setDouble(1, 95.0);
ps.setInt(2, 101);

int count =
    ps.executeUpdate();
```

If one row was updated:

```text
count = 1
```

### INSERT

```java
String sql =
    "INSERT INTO student " +
    "(id, name, marks) " +
    "VALUES (?, ?, ?)";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 104);
ps.setString(2, "Kumar");
ps.setDouble(3, 90.0);

int count =
    ps.executeUpdate();
```

---

## 🔴 Level 3 — Expert

The most important distinction:

```text
executeQuery()
       ↓
ResultSet

executeUpdate()
       ↓
int
```

Memory:

```text
SELECT
  ↓
executeQuery()
  ↓
ResultSet
```

and:

```text
INSERT / UPDATE / DELETE
  ↓
executeUpdate()
  ↓
int
```

Don't choose based simply on "Does the SQL contain the word query?" Choose based on the operation's expected JDBC result.

---

# 8. SQL Injection

## 🟢 Level 1 — Beginner

SQL injection is a security problem where specially crafted input can interfere with dynamically constructed SQL.

Dangerous style:

```java
String sql =
    "SELECT * FROM users " +
    "WHERE username = '" + username + "'";
```

Here user input is directly concatenated into SQL.

---

## 🟡 Level 2 — Intermediate

Suppose:

```java
String username = userInput;
```

and we create:

```java
String sql =
    "SELECT * FROM users " +
    "WHERE username = '" +
    username +
    "'";
```

The user's input becomes part of the SQL text.

That's the problem.

Instead:

```java
String sql =
    "SELECT * FROM users WHERE username = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, username);
```

Now the SQL structure is separated from the supplied value.

---

## 🔴 Level 3 — Expert

The fundamental security principle is:

```text
Don't construct SQL syntax by concatenating untrusted values.
```

Prefer:

```text
SQL template
     +
parameter binding
     ↓
PreparedStatement
```

rather than:

```text
SQL template
     +
string concatenation
     ↓
SQL command
```

`PreparedStatement` parameter binding is the standard JDBC mechanism for protecting parameter values against SQL injection.

However, remember the limitation:

```text
PreparedStatement protects parameter VALUES.
```

It does not automatically make arbitrary dynamically constructed SQL safe.

For example, table names and column names generally cannot simply be supplied as `?` parameters. If identifiers must be dynamic, they need a different safe design, such as selecting from a controlled allow-list.

---

# 9. Statement vs PreparedStatement

## 🟢 Level 1 — Beginner

### Statement

You create the complete SQL:

```java
String sql =
    "SELECT * FROM student WHERE id = " + id;

Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(sql);
```

### PreparedStatement

You create SQL with `?`:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, id);

ResultSet rs =
    ps.executeQuery();
```

---

# 🟡 Level 2 — Intermediate

| Feature            | Statement                                 | PreparedStatement                        |
| ------------------ | ----------------------------------------- | ---------------------------------------- |
| Interface          | `Statement`                               | `PreparedStatement`                      |
| Parameters         | No parameter placeholders in the same way | Supports `?` parameters                  |
| Values             | Often concatenated into SQL               | Bound using `setXXX()`                   |
| SQL injection risk | Higher if concatenating input             | Much safer with parameter binding        |
| Reuse              | Less convenient for parameterized SQL     | Excellent for repeated parameterized SQL |
| Type binding       | Not parameter-based                       | `setInt()`, `setString()`, etc.          |

---

# 🔴 Level 3 — Expert

The key conceptual difference is **how SQL and values are represented**.

### Statement

```text
Java
 ↓
build complete SQL text
 ↓
Statement
 ↓
Database
```

### PreparedStatement

```text
Java
 ↓
SQL structure + placeholders
 ↓
PreparedStatement
 ↓
bind parameter values
 ↓
execute
 ↓
Database
```

For example:

### Statement

```java
String sql =
    "SELECT * FROM student WHERE id = " + id;
```

The SQL itself changes when `id` changes.

### PreparedStatement

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, id);
```

The SQL structure stays the same.

Only the parameter value changes.

---

# 🔥 Complete 3-Level Map

```text
PREPAREDSTATEMENT
│
├── 1. PreparedStatement Interface
│   ├── 🟢 JDBC interface for parameterized SQL
│   ├── 🟡 Obtained using Connection.prepareStatement()
│   └── 🔴 Prepare ≠ execute; values are bound separately
│
├── 2. Parameters
│   ├── 🟢 Represented by ?
│   ├── 🟡 Indexed from 1
│   └── 🔴 ? represents a value, not arbitrary SQL syntax
│
├── 3. setInt()
│   ├── 🟢 Binds an int
│   ├── 🟡 setInt(index, value)
│   └── 🔴 Index identifies parameter position, starting at 1
│
├── 4. setString()
│   ├── 🟢 Binds a String
│   ├── 🟡 No manual SQL quotes around ?
│   └── 🔴 Driver handles parameter representation
│
├── 5. setDouble()
│   ├── 🟢 Binds a double
│   ├── 🟡 Used for numeric parameters
│   └── 🔴 Type-specific binding is part of JDBC's parameter API
│
├── 6. executeQuery()
│   ├── 🟢 Executes SELECT-like queries
│   ├── 🟡 Returns ResultSet
│   └── 🔴 Used when the operation produces a result set
│
├── 7. executeUpdate()
│   ├── 🟢 INSERT / UPDATE / DELETE
│   ├── 🟡 Returns int
│   └── 🔴 Usually represents affected-row count for DML
│
├── 8. SQL Injection
│   ├── 🟢 Malicious input can alter dynamically built SQL
│   ├── 🟡 Avoid concatenating untrusted values into SQL
│   └── 🔴 Use parameter binding; identifiers require separate safe handling
│
└── 9. Statement vs PreparedStatement
    ├── 🟢 Complete SQL vs parameterized SQL
    ├── 🟡 Concatenation vs setXXX()
    └── 🔴 PreparedStatement separates SQL structure from parameter values
```

---

# 🧠 3-Level Final Memory

If you remember only this, you can reconstruct the whole topic:

### Level 1

```text
PreparedStatement = SQL + ?
```

### Level 2

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();
```

### Level 3

```text
prepareStatement()
       ↓
SQL structure + placeholders
       ↓
setXXX()
       ↓
parameter binding
       ↓
executeQuery()/executeUpdate()
       ↓
database
```

And the golden rule:

> **Never build SQL by concatenating untrusted values when a parameter can be used. Use `PreparedStatement` and `setXXX()` for parameter values.**
