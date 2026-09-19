# 8. PreparedStatement in Java — DEEPDIVE

`PreparedStatement` is one of the most important JDBC topics because it combines **parameterized SQL, type-safe parameter binding, reusable SQL structure, and protection against SQL injection**.

We will study every sub-concept individually:

```text
8. PreparedStatement
│
├── PreparedStatement Interface
├── Parameters
├── setInt()
├── setString()
├── setDouble()
├── executeQuery()
├── executeUpdate()
├── SQL Injection
└── Statement vs PreparedStatement
```

---

# 1. PreparedStatement Interface

## 1.1 What is PreparedStatement?

`PreparedStatement` is a JDBC interface used to execute **parameterized SQL statements**.

It belongs to:

```java
java.sql.PreparedStatement
```

It is a subinterface of `Statement`:

```text
Statement
    ↑
PreparedStatement
```

Therefore, `PreparedStatement` inherits many capabilities from `Statement` while adding **parameter binding**.

---

## 1.2 Why was PreparedStatement introduced?

Suppose we want to find a student by ID.

With `Statement`:

```java
int id = 101;

String sql =
    "SELECT * FROM student WHERE id = " + id;

Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(sql);
```

The Java program constructs a complete SQL string every time.

With `PreparedStatement`:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();
```

Here:

```text
SQL structure
     ↓
SELECT * FROM student WHERE id = ?
                         ↑
                    placeholder
```

The actual value is supplied separately.

---

# 2. How PreparedStatement Works

There are two important stages.

## Stage 1 — Prepare SQL

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

At this point, the SQL structure is supplied to the JDBC driver.

## Stage 2 — Bind the parameter

```java
ps.setInt(1, 101);
```

Then execute:

```java
ResultSet rs =
    ps.executeQuery();
```

Conceptually:

```text
             SQL
              │
              ▼
    prepareStatement()
              │
              ▼
      PreparedStatement
              │
       ┌──────┴──────┐
       │             │
    setInt()      setString()
       │             │
       └──────┬──────┘
              ▼
           Execute
              │
              ▼
           Database
```

---

# 3. Parameters

## 3.1 What is a parameter?

A parameter is a value represented by a `?` placeholder in a prepared SQL statement.

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";
```

The `?` is a parameter placeholder.

Then:

```java
ps.setInt(1, 101);
```

sets the first parameter to `101`.

---

# 4. Parameter Indexing

This is extremely important.

JDBC parameter indexes start at **1**.

They do **not** start at 0.

For:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ?";
```

we have:

```text
SQL:

WHERE id = ? AND name = ?
          ↑              ↑
          1              2
```

Therefore:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

Correct.

---

## ❌ Common mistake

```java
ps.setInt(0, 101);
```

This is incorrect because JDBC parameter numbering starts at 1.

---

# 5. Multiple Parameters

Example:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND marks > ?";
```

Create:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Set parameters:

```java
ps.setInt(1, 101);
ps.setDouble(2, 80.0);
```

Execute:

```java
ResultSet rs =
    ps.executeQuery();
```

Conceptually:

```text
? #1 → 101
? #2 → 80.0
```

---

# 6. Parameter Type Matters

The setter method should normally correspond to the Java type you are binding.

For example:

```java
int       → setInt()
String    → setString()
double    → setDouble()
```

There are many other setter methods:

```text
setLong()
setFloat()
setBoolean()
setDate()
setTime()
setTimestamp()
setBigDecimal()
setBytes()
setObject()
...
```

The three important ones in this topic are:

```text
setInt()
setString()
setDouble()
```

---

# 7. `setInt()`

## 7.1 What is setInt()?

`setInt()` sets an `int` value for a parameter.

Conceptually:

```java
ps.setInt(parameterIndex, value);
```

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);
```

Here:

```text
parameter #1
     ↓
   integer
     ↓
    101
```

---

## 7.2 Example with UPDATE

```java
String sql =
    "UPDATE student " +
    "SET marks = ? " +
    "WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 95);
ps.setInt(2, 101);

int count =
    ps.executeUpdate();
```

Here:

```text
? #1 → 95
? #2 → 101
```

---

## 7.3 Common mistake

Don't write:

```java
ps.setInt(0, 101);
```

Remember:

```text
First parameter → 1
Second parameter → 2
Third parameter → 3
```

---

# 8. `setString()`

## 8.1 What is setString()?

`setString()` binds a Java `String` to a parameter.

Example:

```java
String sql =
    "SELECT * FROM student WHERE name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, "Ravi");
```

---

## 8.2 Do we put quotes around `?`?

No.

### ❌ Wrong

```java
String sql =
    "SELECT * FROM student " +
    "WHERE name = '?'";
```

### ✅ Correct

```java
String sql =
    "SELECT * FROM student " +
    "WHERE name = ?";
```

Then:

```java
ps.setString(1, "Ravi");
```

JDBC handles the parameter value appropriately.

---

## 8.3 Why is this safer?

Suppose the user enters:

```text
Ravi
```

The value is supplied as a parameter rather than becoming part of the SQL syntax.

This separation is a major reason `PreparedStatement` is preferred for user-supplied values.

---

# 9. `setDouble()`

## 9.1 What is setDouble()?

`setDouble()` binds a Java `double` value.

Example:

```java
String sql =
    "SELECT * FROM product WHERE price > ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setDouble(1, 500.50);
```

---

## 9.2 Example

Suppose the table contains:

```text
id    product    price
----------------------
1     Laptop     60000.50
2     Mouse        500.00
3     Keyboard    1500.00
```

SQL:

```java
String sql =
    "SELECT * FROM product WHERE price > ?";
```

Parameter:

```java
ps.setDouble(1, 1000.0);
```

Then:

```java
ResultSet rs =
    ps.executeQuery();
```

The database returns products whose price satisfies the condition.

---

# 10. `executeQuery()`

## 10.1 What is executeQuery()?

`executeQuery()` executes a prepared SQL statement that produces a **`ResultSet`**.

Return type:

```java
ResultSet
```

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

---

# 11. Why don't we pass SQL to executeQuery()?

With `Statement`:

```java
ResultSet rs =
    st.executeQuery(sql);
```

With `PreparedStatement`:

```java
ResultSet rs =
    ps.executeQuery();
```

Why?

Because the SQL was already supplied here:

```java
con.prepareStatement(sql);
```

So:

```text
Statement:

executeQuery(SQL)
       ↓
SQL supplied during execution
```

whereas:

```text
PreparedStatement:

prepareStatement(SQL)
       ↓
SQL prepared
       ↓
set parameters
       ↓
executeQuery()
```

---

# 12. Complete SELECT Example

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

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
```

Flow:

```text
SQL
 ↓
prepareStatement()
 ↓
?
 ↓
setInt()
 ↓
executeQuery()
 ↓
ResultSet
 ↓
rs.next()
 ↓
Read rows
```

---

# 13. `executeUpdate()`

## 13.1 What is executeUpdate()?

`executeUpdate()` is used when the prepared SQL produces an **update count**.

Typical operations:

```text
INSERT
UPDATE
DELETE
```

It returns:

```java
int
```

---

# 14. UPDATE Example

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

System.out.println(
    "Rows updated: " + count
);
```

Suppose one row is affected:

```text
Rows updated: 1
```

---

# 15. INSERT Example

```java
String sql =
    "INSERT INTO student " +
    "(id, name, marks) " +
    "VALUES (?, ?, ?)";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);
ps.setString(2, "Ravi");
ps.setDouble(3, 95.5);

int count =
    ps.executeUpdate();
```

Here:

```text
? #1 → 101
? #2 → Ravi
? #3 → 95.5
```

---

# 16. DELETE Example

```java
String sql =
    "DELETE FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

int count =
    ps.executeUpdate();
```

Again:

```text
executeUpdate()
      ↓
     int
      ↓
affected-row count
```

---

# 17. executeQuery() vs executeUpdate()

This is a critical distinction.

| Method            | Return type | Typical purpose                    |
| ----------------- | ----------- | ---------------------------------- |
| `executeQuery()`  | `ResultSet` | Retrieve rows                      |
| `executeUpdate()` | `int`       | Modify data / produce update count |

Memory:

```text
SELECT
  ↓
executeQuery()
  ↓
ResultSet
```

```text
INSERT / UPDATE / DELETE
  ↓
executeUpdate()
  ↓
int
```

---

# 18. SQL Injection

## 18.1 What is SQL Injection?

SQL injection is a vulnerability where untrusted input is allowed to alter the intended SQL command.

Consider:

```java
String username = userInput;

String sql =
    "SELECT * FROM users " +
    "WHERE username = '" +
    username +
    "'";

Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(sql);
```

The user input is directly incorporated into the SQL string.

This is dangerous.

---

# 19. Why String Concatenation Is Dangerous

Suppose the application expects:

```text
Ravi
```

The generated SQL might be:

```sql
SELECT * FROM users
WHERE username = 'Ravi'
```

But if malicious input is supplied, the input may contain SQL syntax that changes the meaning of the statement.

The core problem is:

```text
Untrusted data
      ↓
combined with
      ↓
SQL syntax
```

---

# 20. PreparedStatement Solution

Instead:

```java
String sql =
    "SELECT * FROM users " +
    "WHERE username = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setString(1, userInput);

ResultSet rs =
    ps.executeQuery();
```

Now the SQL structure is fixed:

```text
SELECT * FROM users WHERE username = ?
```

and the user value is bound separately.

Conceptually:

```text
SQL syntax
     │
     ├───────────────┐
     │               │
     ▼               ▼
Prepared SQL      Parameter value
     │               │
     └───────┬───────┘
             ▼
        JDBC Driver
             ▼
          Database
```

---

# 21. Does PreparedStatement "Automatically Escape Everything"?

Be precise here.

Don't think:

> "PreparedStatement is an escaping function."

The stronger and more useful concept is:

> **PreparedStatement uses parameter binding so parameter values are handled separately from the SQL statement structure.**

Therefore, when used correctly for parameter values, it protects against SQL injection.

---

# 22. Important SQL Injection Limitation

PreparedStatement parameters represent **values**, not arbitrary SQL syntax.

For example, this works:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

ps.setInt(1, 101);
```

But you cannot normally do:

```java
String sql =
    "SELECT * FROM student ORDER BY ?";
```

and expect:

```java
ps.setString(1, "name");
```

to turn the parameter into an SQL identifier.

Similarly, you cannot use `?` as a replacement for an entire SQL fragment such as:

```text
table name
column name
SQL keyword
ORDER BY direction
```

Dynamic SQL identifiers generally need a different, carefully validated approach.

This distinction is extremely important:

```text
? → value
```

not:

```text
? → arbitrary SQL syntax
```

---

# 23. Statement vs PreparedStatement

## 23.1 Statement

Example:

```java
int id = 101;

String sql =
    "SELECT * FROM student " +
    "WHERE id = " + id;

Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(sql);
```

The complete SQL string is constructed manually.

---

## 23.2 PreparedStatement

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs =
    ps.executeQuery();
```

The SQL structure is fixed and the value is bound separately.

---

# 24. Complete Comparison

| Feature                                   | Statement                               | PreparedStatement      |
| ----------------------------------------- | --------------------------------------- | ---------------------- |
| Type                                      | Interface                               | Interface              |
| Relationship                              | Base JDBC statement interface           | Extends `Statement`    |
| Parameter placeholders                    | ❌                                       | ✅                      |
| Parameter binding                         | ❌                                       | ✅                      |
| `setInt()`                                | ❌                                       | ✅                      |
| `setString()`                             | ❌                                       | ✅                      |
| `setDouble()`                             | ❌                                       | ✅                      |
| SQL injection protection for bound values | ❌ Not inherently                        | ✅                      |
| Repeated same SQL structure               | Less convenient                         | Well suited            |
| SQL structure                             | Usually supplied as complete SQL string | Supplied when prepared |
| Typical use                               | Simple/static SQL                       | Parameterized SQL      |
| Can execute query?                        | ✅                                       | ✅                      |
| Can execute update?                       | ✅                                       | ✅                      |

---

# 25. Is PreparedStatement Always Faster?

Be careful with this interview question.

A common statement is:

> "PreparedStatement is always faster than Statement."

That is **too absolute**.

Prepared statements can provide performance advantages, especially when the same SQL structure is executed repeatedly, because the driver/database may be able to reuse prepared execution information.

But actual behavior depends on:

* JDBC driver
* database
* configuration
* statement usage
* server-side preparation
* workload

Therefore the safest statement is:

> **PreparedStatement is designed for parameterized SQL and can provide performance benefits for repeated execution, but you should not claim that it is universally faster in every situation.**

---

# 26. Does PreparedStatement Prepare the SQL Only Once?

Not necessarily in the simplistic way beginners are often taught.

Consider:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

The driver/database handles preparation according to its implementation and configuration.

The important programming model is:

```text
SQL structure
    ↓
prepare
    ↓
bind parameters
    ↓
execute
```

Don't assume that every JDBC driver necessarily performs identical server-side preparation behavior.

---

# 27. Can We Reuse a PreparedStatement?

**Yes.**

This is one of its useful features.

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

ResultSet rs1 =
    ps.executeQuery();
```

After processing/closing `rs1`, the same prepared statement can be reused:

```java
ps.setInt(1, 102);

ResultSet rs2 =
    ps.executeQuery();
```

Conceptually:

```text
Prepared SQL
     │
     ├── id = 101 → execute
     │
     ├── id = 102 → execute
     │
     └── id = 103 → execute
```

The SQL structure remains:

```sql
SELECT * FROM student WHERE id = ?
```

---

# 28. Does setInt() Execute the SQL?

**No.**

This:

```java
ps.setInt(1, 101);
```

only binds the parameter.

It does not execute the SQL.

Execution happens with:

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
Set value

executeQuery()
   ↓
Execute SELECT

executeUpdate()
   ↓
Execute update
```

---

# 29. Does prepareStatement() Execute the SQL?

**No.**

This:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

creates/prepares the statement object.

It does not mean:

> "The SQL has already been executed."

Execution happens later:

```java
ps.executeQuery();
```

or:

```java
ps.executeUpdate();
```

---

# 30. Can We Call setString() on an Integer Parameter?

Technically, JDBC drivers may perform conversions in some circumstances, but don't use setter methods carelessly.

If the Java value is an integer:

```java
ps.setInt(1, 101);
```

If the value is a string:

```java
ps.setString(1, "Ravi");
```

Prefer matching the setter to the intended SQL/Java type.

---

# 31. What Happens If We Forget a Parameter?

Suppose:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE id = ? AND name = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);

// Forgot parameter 2

ps.executeQuery();
```

The statement is incomplete.

The JDBC driver will report an SQL/JDBC exception because not all required parameters have been supplied.

---

# 32. What Happens If Parameter Index Is Wrong?

Example:

```java
ps.setInt(3, 101);
```

when there are only two parameters.

That's invalid.

Similarly:

```java
ps.setInt(0, 101);
```

is invalid because JDBC parameters are 1-based.

---

# 33. Can We Change a Parameter After Setting It?

Yes.

Example:

```java
ps.setInt(1, 101);

ps.setInt(1, 102);
```

The second assignment replaces the value currently bound to parameter 1.

Then:

```java
ps.executeQuery();
```

uses the current parameter value.

---

# 34. Complete Realistic Example

```java
import java.sql.*;

public class PreparedStatementDemo {

    public static void main(String[] args)
            throws SQLException {

        String url =
            "jdbc:mysql://localhost:3306/college";

        String username = "root";
        String password = "password";

        String sql =
            "SELECT id, name, marks " +
            "FROM student " +
            "WHERE marks > ? " +
            "AND name = ?";

        try (
            Connection con =
                DriverManager.getConnection(
                    url,
                    username,
                    password
                );

            PreparedStatement ps =
                con.prepareStatement(sql)
        ) {

            ps.setDouble(1, 80.0);
            ps.setString(2, "Ravi");

            try (
                ResultSet rs =
                    ps.executeQuery()
            ) {

                while (rs.next()) {

                    System.out.println(
                        rs.getInt("id")
                        + " "
                        + rs.getString("name")
                        + " "
                        + rs.getDouble("marks")
                    );
                }
            }
        }
    }
}
```

Notice the complete lifecycle:

```text
Connection
    ↓
prepareStatement()
    ↓
PreparedStatement
    ↓
setDouble()
    ↓
setString()
    ↓
executeQuery()
    ↓
ResultSet
    ↓
process rows
    ↓
close resources
```

---

# 35. PreparedStatement with UPDATE

```java
String sql =
    "UPDATE student " +
    "SET marks = ? " +
    "WHERE id = ?";

try (PreparedStatement ps =
        con.prepareStatement(sql)) {

    ps.setDouble(1, 96.5);
    ps.setInt(2, 101);

    int count =
        ps.executeUpdate();

    System.out.println(
        "Rows affected: " + count
    );
}
```

Flow:

```text
UPDATE SQL
    ↓
? ← 96.5
? ← 101
    ↓
executeUpdate()
    ↓
int
```

---

# 36. PreparedStatement and Transactions

`PreparedStatement` does not automatically mean transaction management.

Transactions are controlled through `Connection`:

```java
con.setAutoCommit(false);
```

Then:

```java
ps.executeUpdate();
```

and:

```java
con.commit();
```

or:

```java
con.rollback();
```

So:

```text
PreparedStatement
     ↓
SQL execution

Connection
     ↓
Transaction management
```

Don't mix these responsibilities.

---

# 37. PreparedStatement and Batch

Prepared statements can also be used for batching.

Example:

```java
String sql =
    "INSERT INTO student " +
    "(id, name, marks) " +
    "VALUES (?, ?, ?)";

try (PreparedStatement ps =
        con.prepareStatement(sql)) {

    ps.setInt(1, 101);
    ps.setString(2, "Ravi");
    ps.setDouble(3, 90.0);
    ps.addBatch();

    ps.setInt(1, 102);
    ps.setString(2, "Raj");
    ps.setDouble(3, 85.0);
    ps.addBatch();

    ps.executeBatch();
}
```

This is especially useful when the **SQL structure remains the same but parameter values change**.

---

# 38. Deep Conceptual Difference

The biggest conceptual difference is this:

### Statement

```text
SQL + data
   ↓
one SQL string
```

### PreparedStatement

```text
SQL structure
     +
parameter values
     ↓
separate concepts
```

That separation provides:

* safer parameter handling;
* cleaner code;
* easier reuse;
* better support for repeated parameterized operations;
* protection against SQL injection through parameter values.

---

# 39. The Most Important DOUBTS — Answered Quickly

### ❓ `?` starts at 0?

**No.**

```text
First ? → 1
```

---

### ❓ `setInt()` executes SQL?

**No.**

It only binds a parameter.

---

### ❓ `prepareStatement()` executes SQL?

**No.**

It creates/prepares the statement.

---

### ❓ `executeQuery()` returns `int`?

**No.**

```text
ResultSet
```

---

### ❓ `executeUpdate()` returns `ResultSet`?

**No.**

```text
int
```

---

### ❓ Can `?` represent a table name?

Normally **no**.

`?` represents a parameter **value**, not arbitrary SQL syntax.

---

### ❓ Is PreparedStatement only for SELECT?

**No.**

It can be used for:

```text
SELECT
INSERT
UPDATE
DELETE
```

and other supported SQL operations.

---

### ❓ Does PreparedStatement eliminate every possible SQL injection?

It protects parameter values when used correctly, but it does **not** automatically make dynamically constructed SQL identifiers or arbitrary SQL fragments safe.

---

### ❓ Is PreparedStatement a class?

**No.**

It is an interface.

---

### ❓ Does PreparedStatement extend Statement?

**Yes.**

```text
Statement
    ↑
PreparedStatement
```

---

# 40. Final Deep-Dive Map

```text
                 PreparedStatement
                         │
                         ▼
               prepareStatement(SQL)
                         │
                         ▼
                  Parameterized SQL
                         │
                  ┌──────┴──────┐
                  │             │
                  ▼             ▼
               setInt()     setString()
                  │             │
                  └──────┬──────┘
                         │
                    setDouble()
                         │
              ┌──────────┴──────────┐
              │                     │
              ▼                     ▼
       executeQuery()        executeUpdate()
              │                     │
              ▼                     ▼
          ResultSet                 int
```

And the security concept:

```text
❌ Statement + String Concatenation

User Input
    ↓
SQL String
    ↓
Database
    ↓
SQL Injection Risk


✅ PreparedStatement + Parameter Binding

SQL Structure ─────────┐
                       ├──→ Database
Parameter Value ───────┘
                       ↓
                SQL Injection
                Protection*
```

`*` Protection here refers specifically to **values supplied through parameter binding**; dynamic SQL identifiers/fragments require separate validation/design.

---

# 🏆 DEEP-DIVE GOLDEN RULES

```text
1. PreparedStatement is an interface.

2. It extends Statement.

3. Use ? for parameter values.

4. JDBC parameter indexes start at 1.

5. setInt() → integer value.

6. setString() → String value.

7. setDouble() → double value.

8. setXXX() binds values; it does not execute SQL.

9. executeQuery() → ResultSet.

10. executeUpdate() → int.

11. PreparedStatement is preferred for parameterized SQL.

12. Parameter binding helps prevent SQL injection.

13. ? represents a value, not arbitrary SQL syntax.

14. PreparedStatement can be reused with different
    parameter values.

15. PreparedStatement and transactions are separate concepts.
```

### The one formula to remember:

> **Prepare the SQL → bind the parameters → execute the appropriate method → process the result.**
