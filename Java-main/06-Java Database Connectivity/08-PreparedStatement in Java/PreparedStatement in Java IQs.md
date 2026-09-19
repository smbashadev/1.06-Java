# 8. PreparedStatement in Java — DOUBTKILLER

This version is designed to **remove the common confusions one by one**.

The central idea is:

```text
Statement
   ↓
SQL is generally built as text

PreparedStatement
   ↓
SQL structure + ? placeholders
   ↓
setXXX() binds values
   ↓
execute
```

---

# 1. PreparedStatement Interface

## What exactly is PreparedStatement?

`PreparedStatement` is a JDBC interface used to execute **parameterized SQL statements**.

Package:

```java
java.sql.PreparedStatement
```

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);
```

Here:

```text
? = parameter placeholder
```

Then:

```java
ps.setInt(1, 101);
```

Then:

```java
ResultSet rs = ps.executeQuery();
```

So the complete process is:

```text
prepareStatement()
       ↓
PreparedStatement object
       ↓
setXXX()
       ↓
executeXXX()
```

---

## DOUBTKILLER: Does `prepareStatement()` execute SQL?

**No.**

This:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

does not mean:

```text
"Execute SQL now"
```

It means:

```text
"Create a PreparedStatement based on this SQL"
```

Execution happens later:

```java
ps.executeQuery();
```

or:

```java
ps.executeUpdate();
```

---

## DOUBTKILLER: Is PreparedStatement a class?

No.

It is an **interface**:

```java
PreparedStatement
```

JDBC provides the interface, while the JDBC driver supplies the implementation.

You normally don't do:

```java
new PreparedStatement();
```

Instead:

```java
con.prepareStatement(sql);
```

returns a suitable implementation.

---

# 2. Parameters

A parameter is represented using:

```text
?
```

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";
```

Think:

```text
SELECT * FROM student WHERE id = ___
                                    ↑
                                  blank
```

Later:

```java
ps.setInt(1, 101);
```

fills that parameter.

---

## Multiple Parameters

Suppose:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ?";
```

There are two parameters:

```text
WHERE id = ? AND name = ?
          ↑              ↑
          1              2
```

Therefore:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

---

## 🔥 DOUBTKILLER: Does parameter numbering start at 0?

**NO.**

This is one of the most common JDBC mistakes.

Java arrays:

```text
0  1  2  3
```

But JDBC parameters:

```text
1  2  3  4
```

Therefore:

```java
ps.setInt(1, 101);  // Correct
```

Not:

```java
ps.setInt(0, 101);  // Wrong
```

---

## 🔥 DOUBTKILLER: Is `?` a variable?

Not exactly.

It is a **parameter marker/place-holder** in the SQL statement.

```sql
SELECT * FROM student WHERE id = ?
```

It does not mean Java variable syntax.

It means:

> "A value will be supplied for this position."

---

## 🔥 DOUBTKILLER: Can `?` replace anything in SQL?

**No.**

`?` is primarily for **values**.

Good:

```sql
SELECT * FROM student WHERE id = ?
```

Good:

```sql
SELECT * FROM student WHERE name = ?
```

But don't expect this to substitute a table name:

```sql
SELECT * FROM ?
```

or arbitrary SQL syntax.

Remember:

```text
? = VALUE
```

not:

```text
? = arbitrary SQL code
```

---

# 3. `setInt()`

## What is setInt()?

`setInt()` binds a Java `int` value to a parameter.

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
parameter #1
      ↓
     101
```

---

## Complete example

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

## 🔥 DOUBTKILLER: Does setInt() execute the query?

**NO.**

This:

```java
ps.setInt(1, 101);
```

only binds the value.

It does NOT execute the SQL.

Execution requires:

```java
ps.executeQuery();
```

or:

```java
ps.executeUpdate();
```

So:

```text
setInt()
   ↓
PUT VALUE

executeQuery()
   ↓
EXECUTE SQL
```

---

# 4. `setString()`

## What is setString()?

It binds a Java `String` to a parameter.

Syntax:

```java
ps.setString(parameterIndex, value);
```

Example:

```java
ps.setString(1, "Ravi");
```

---

## Complete example

```java
String sql =
    "SELECT * FROM student WHERE name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, "Ravi");

ResultSet rs =
    ps.executeQuery();
```

---

## 🔥 DOUBTKILLER: Should we put quotes around `?`?

**NO.**

Correct:

```java
String sql =
    "SELECT * FROM student WHERE name = ?";
```

Then:

```java
ps.setString(1, "Ravi");
```

Don't write:

```java
String sql =
    "SELECT * FROM student WHERE name = '?'";
```

The placeholder itself should not be manually wrapped in SQL string quotes.

---

## Why?

Because the JDBC driver handles the parameter value.

You supply:

```java
"Ravi"
```

through:

```java
setString()
```

You don't manually construct:

```text
'Ravi'
```

inside the SQL.

---

# 5. `setDouble()`

## What is setDouble()?

It binds a Java `double` to a parameter.

Syntax:

```java
ps.setDouble(parameterIndex, value);
```

Example:

```java
ps.setDouble(1, 95.5);
```

---

## Complete example

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

## 🔥 DOUBTKILLER: Why setDouble instead of putting 80.5 into SQL?

Instead of:

```java
String sql =
    "SELECT * FROM student WHERE marks > " + marks;
```

use:

```java
String sql =
    "SELECT * FROM student WHERE marks > ?";

ps.setDouble(1, marks);
```

This keeps the SQL structure separate from the value.

---

# 6. `executeQuery()`

## What does executeQuery() do?

It **executes the prepared SQL** and returns a:

```java
ResultSet
```

Example:

```java
ResultSet rs =
    ps.executeQuery();
```

Typical use:

```sql
SELECT
```

---

## Complete example

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
        rs.getInt("id")
    );

    System.out.println(
        rs.getString("name")
    );
}
```

Flow:

```text
SQL
 ↓
prepareStatement()
 ↓
PreparedStatement
 ↓
setInt()
 ↓
executeQuery()
 ↓
ResultSet
 ↓
rs.next()
 ↓
read data
```

---

## 🔥 DOUBTKILLER: Does executeQuery() return an int?

**NO.**

It returns:

```java
ResultSet
```

Remember:

```text
executeQuery()
      ↓
ResultSet
```

---

# 7. `executeUpdate()`

## What does executeUpdate() do?

It executes SQL that modifies database data.

Common examples:

```text
INSERT
UPDATE
DELETE
```

It normally returns:

```java
int
```

representing the number of affected rows for those DML operations.

---

## UPDATE example

```java
String sql =
    "UPDATE student " +
    "SET marks = ? " +
    "WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setDouble(1, 95.5);
ps.setInt(2, 101);

int count =
    ps.executeUpdate();
```

If one row was updated:

```text
count = 1
```

---

## INSERT example

```java
String sql =
    "INSERT INTO student " +
    "(id, name, marks) " +
    "VALUES (?, ?, ?)";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 105);
ps.setString(2, "Kumar");
ps.setDouble(3, 90.5);

int count =
    ps.executeUpdate();
```

---

## DELETE example

```java
String sql =
    "DELETE FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 105);

int count =
    ps.executeUpdate();
```

---

## 🔥 DOUBTKILLER: executeQuery vs executeUpdate

Memorize this:

```text
SELECT
  ↓
executeQuery()
  ↓
ResultSet
```

```text
INSERT
UPDATE
DELETE
  ↓
executeUpdate()
  ↓
int
```

Therefore:

| Method            | Typical SQL                  | Return      |
| ----------------- | ---------------------------- | ----------- |
| `executeQuery()`  | `SELECT`                     | `ResultSet` |
| `executeUpdate()` | `INSERT`, `UPDATE`, `DELETE` | `int`       |

---

# 8. SQL Injection

Now the most important security concept.

## What is SQL Injection?

SQL injection happens when untrusted input is improperly incorporated into SQL and can alter the intended SQL command.

Dangerous approach:

```java
String sql =
    "SELECT * FROM users " +
    "WHERE username = '" +
    username +
    "'";
```

Here:

```text
user input
    ↓
string concatenation
    ↓
SQL text
```

That's dangerous.

---

# How PreparedStatement helps

Instead:

```java
String sql =
    "SELECT * FROM users " +
    "WHERE username = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, username);
```

Now:

```text
SQL structure
      +
parameter value
      ↓
PreparedStatement
```

The value is bound as data rather than being concatenated into the SQL syntax.

---

## 🔥 DOUBTKILLER: Is PreparedStatement "magic security"?

No.

The important security practice is:

> **Use parameter binding instead of concatenating untrusted values into SQL.**

PreparedStatement is the JDBC mechanism designed for this.

But remember:

```text
? → values
```

It does not automatically make every dynamically constructed SQL fragment safe.

For example, table/column identifiers usually need a controlled allow-list or another safe design.

---

# 9. Statement vs PreparedStatement

Now let's eliminate the confusion completely.

---

## Statement

With `Statement`, you often construct the complete SQL yourself:

```java
int id = 101;

String sql =
    "SELECT * FROM student WHERE id = " + id;

Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(sql);
```

The SQL string itself contains the value.

---

## PreparedStatement

With `PreparedStatement`:

```java
int id = 101;

String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, id);

ResultSet rs =
    ps.executeQuery();
```

The SQL structure contains:

```text
?
```

The value is supplied separately.

---

# 🔥 DOUBTKILLER: Which is faster?

Don't memorize:

> "PreparedStatement is always faster."

That's too simplistic.

For repeated execution of the same parameterized SQL, prepared statements can provide performance benefits because preparation/reuse can be leveraged by the JDBC driver and/or database.

But actual behavior depends on:

* JDBC driver
* database
* configuration
* statement usage
* server-side preparation behavior

The **strongest reason to prefer PreparedStatement for variable values is safety and proper parameter binding**, not an absolute claim that it is always faster.

---

# 🔥 DOUBTKILLER: Can Statement prevent SQL injection?

`Statement` itself doesn't provide parameter binding.

You could theoretically build SQL safely in special situations, but direct concatenation of untrusted values is dangerous.

So don't do:

```java
"SELECT ... WHERE name = '" + userInput + "'"
```

Prefer:

```java
"SELECT ... WHERE name = ?"
```

with:

```java
ps.setString(1, userInput);
```

---

# 🔥 DOUBTKILLER: Is PreparedStatement only for SELECT?

**NO.**

It can be used for:

```text
SELECT
INSERT
UPDATE
DELETE
```

Examples:

```java
ps.executeQuery();
```

for a result-producing query, and:

```java
ps.executeUpdate();
```

for typical DML.

---

# 🔥 DOUBTKILLER: Can PreparedStatement have multiple `?`?

**Yes.**

Example:

```java
String sql =
    "UPDATE student " +
    "SET name = ?, marks = ? " +
    "WHERE id = ?";
```

Parameters:

```text
? #1 → name
? #2 → marks
? #3 → id
```

Then:

```java
ps.setString(1, "Ravi");
ps.setDouble(2, 95.5);
ps.setInt(3, 101);
```

---

# 🔥 DOUBTKILLER: Can I change a parameter after setting it?

**Yes.**

Example:

```java
ps.setInt(1, 101);

ResultSet rs1 =
    ps.executeQuery();
```

After processing/closing `rs1`, you can bind another value:

```java
ps.setInt(1, 102);

ResultSet rs2 =
    ps.executeQuery();
```

The SQL structure remains:

```sql
SELECT * FROM student WHERE id = ?
```

Only the parameter value changes.

---

# 🔥 DOUBTKILLER: Do I need to create a new PreparedStatement every time the value changes?

Not necessarily.

For repeated use of the same SQL structure, you can reuse the same `PreparedStatement` and change its parameter values.

Conceptually:

```text
Same SQL
   ↓
PreparedStatement
   ↓
101 → execute
   ↓
102 → execute
   ↓
103 → execute
```

This is one of the useful features of parameterized statements.

---

# 🔥 DOUBTKILLER: What happens if I forget to set a parameter?

Suppose:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ?";
```

Then:

```java
ps.setInt(1, 101);
```

but you forget parameter 2.

You haven't supplied all required parameter values.

When you execute, the driver will report an error because the parameter has not been properly set.

So:

```text
Number of required ?
        ↓
must be properly handled
```

---

# 🔥 DOUBTKILLER: Does parameter order matter?

**Absolutely.**

Given:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ?";
```

Correct:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

If you accidentally reverse the values:

```java
ps.setString(1, "Ravi");
ps.setInt(2, 101);
```

the parameter types/order don't match the intended SQL.

Remember:

```text
1st ? → parameter 1
2nd ? → parameter 2
3rd ? → parameter 3
```

---

# 🔥 DOUBTKILLER: Is `?` replaced by Java string replacement?

No.

It isn't equivalent to:

```java
sql.replace("?", "101");
```

That is **not** how `PreparedStatement` works.

The parameter is bound through the JDBC API:

```java
ps.setInt(1, 101);
```

The JDBC driver handles the parameter binding.

---

# 🔥 DOUBTKILLER: Does PreparedStatement eliminate all SQL injection?

Be precise:

**It protects parameter values when you use parameter binding correctly.**

It doesn't mean:

```text
Any SQL you construct dynamically = automatically safe
```

For example, if you concatenate an untrusted value into a table name or SQL fragment, you're back to constructing SQL dynamically.

Safe design:

```text
Controlled SQL structure
        +
bound parameters
```

---

# 🔥 The Biggest Confusion: Statement vs PreparedStatement

Imagine you need:

```text
id = 101
```

### Statement

```java
String sql =
    "SELECT * FROM student WHERE id = " + id;
```

The final SQL text contains:

```sql
WHERE id = 101
```

---

### PreparedStatement

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

ps.setInt(1, id);
```

The SQL template contains:

```sql
WHERE id = ?
```

and the value is bound separately.

### The fundamental difference:

```text
Statement
─────────
SQL + value
     ↓
complete SQL text


PreparedStatement
─────────────────
SQL structure
     +
parameter value
     ↓
parameterized execution
```

---

# 🧠 The Entire Topic in One Diagram

```text
                 PreparedStatement
                         │
                         ▼
               SQL with ? markers
                         │
                         ▼
              prepareStatement()
                         │
                         ▼
              PreparedStatement obj
                         │
                ┌────────┼────────┐
                │        │        │
                ▼        ▼        ▼
             setInt  setString setDouble
                │        │        │
                └────────┼────────┘
                         ▼
                  Bound parameters
                         │
                 ┌───────┴────────┐
                 │                │
                 ▼                ▼
          executeQuery()    executeUpdate()
                 │                │
                 ▼                ▼
             ResultSet            int
                 │                │
                 ▼                ▼
             SELECT       INSERT/UPDATE/DELETE
```

---

# 🧠 Ultimate DOUBTKILLER Table

| Doubt                                              | Correct Answer                                    |
| -------------------------------------------------- | ------------------------------------------------- |
| Is PreparedStatement a class?                      | **No, interface**                                 |
| Where is it?                                       | `java.sql`                                        |
| How do we obtain it?                               | `Connection.prepareStatement()`                   |
| Does `prepareStatement()` execute SQL?             | **No**                                            |
| What is `?`?                                       | Parameter placeholder                             |
| What does `?` represent?                           | A **value**                                       |
| Parameter numbering starts from?                   | **1**                                             |
| Does `setInt()` execute SQL?                       | **No**                                            |
| What does `setInt()` do?                           | Binds an `int`                                    |
| What does `setString()` do?                        | Binds a `String`                                  |
| What does `setDouble()` do?                        | Binds a `double`                                  |
| What does `executeQuery()` return?                 | `ResultSet`                                       |
| What is `executeQuery()` commonly used for?        | `SELECT`                                          |
| What does `executeUpdate()` return?                | `int`                                             |
| What is `executeUpdate()` commonly used for?       | `INSERT`, `UPDATE`, `DELETE`                      |
| Can PreparedStatement have multiple `?`?           | **Yes**                                           |
| Can parameter indexes start at 0?                  | **No**                                            |
| Should we write `'?'` for a String?                | **No**                                            |
| Can `?` represent a table name?                    | **Not as a normal value parameter**               |
| Does PreparedStatement help prevent SQL injection? | **Yes, when parameter binding is used correctly** |
| Is PreparedStatement always faster?                | **No absolute guarantee**                         |
| Can PreparedStatement be reused?                   | **Yes**, for the same SQL structure               |
| Is PreparedStatement only for SELECT?              | **No**                                            |

---

# 🎯 Final Memory Formula

Don't memorize dozens of disconnected facts.

Memorize this:

```text
               PREPARE
                  ↓
      SQL + ? + ? + ?
                  ↓
             SET VALUES
                  ↓
     setInt / setString /
          setDouble / ...
                  ↓
               EXECUTE
              ↙       ↘
     executeQuery   executeUpdate
          ↓               ↓
      ResultSet           int
```

And the **most important distinction in the entire topic**:

```text
Statement
    ↓
You construct the SQL text.

PreparedStatement
    ↓
You define SQL with placeholders.
    ↓
You bind values separately.
    ↓
You execute it.
```

### One-line exam definition

> **PreparedStatement is a JDBC interface used to execute parameterized SQL statements, where `?` placeholders are assigned values using `setXXX()` methods before execution.**

### One-line security rule

> **Never concatenate untrusted input into SQL when a parameter can be used; use `PreparedStatement` parameter binding instead.**
