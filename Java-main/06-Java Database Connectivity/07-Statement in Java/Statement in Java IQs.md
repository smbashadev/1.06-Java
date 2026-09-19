# 7. Statement in Java — DOUBTKILLER

This section is designed to eliminate the **common doubts, traps, confusions, and interview questions** around:

```text
Statement
│
├── Statement Interface
├── execute()
├── executeQuery()
├── executeUpdate()
└── executeBatch()
```

---

# 1. Statement Interface — DOUBTS

## ❓ Doubt 1: What exactly is `Statement`?

`Statement` is a JDBC **interface** used to send SQL statements to a database.

```java
import java.sql.Statement;
```

It belongs to:

```text
java.sql
   ↓
Statement
```

Example:

```java
Statement st = con.createStatement();
```

Think:

```text
Connection
    ↓
creates
    ↓
Statement
    ↓
sends SQL
    ↓
Database
```

---

## ❓ Doubt 2: Is `Statement` a class?

**No.**

It is an interface.

Therefore:

```java
Statement st = new Statement();
```

❌ Invalid.

Instead:

```java
Statement st = con.createStatement();
```

The JDBC driver supplies the implementation.

---

## ❓ Doubt 3: Why do we need `Statement` if we already have `Connection`?

Because they have different responsibilities.

### `Connection`

Represents the database connection/session.

### `Statement`

Represents an object through which SQL can be executed.

```text
Connection
   │
   │ createStatement()
   ▼
Statement
   │
   │ execute SQL
   ▼
Database
```

So:

> **Connection connects; Statement executes SQL.**

---

## ❓ Doubt 4: Does `Statement` itself connect to the database?

**No.**

The connection is established by:

```java
Connection con =
    DriverManager.getConnection(...);
```

Then:

```java
Statement st =
    con.createStatement();
```

So:

```text
DriverManager
      ↓
Connection
      ↓
Statement
      ↓
SQL
```

---

# 2. `execute()` — DOUBTS

## ❓ Doubt 5: What does `execute()` actually return?

It returns:

```java
boolean
```

Example:

```java
boolean result =
    st.execute(sql);
```

But this boolean does **not** mean:

> "SQL succeeded = true, SQL failed = false."

⚠️ This is one of the biggest JDBC misunderstandings.

Instead, it indicates whether the **first result is a `ResultSet`**.

---

## ❓ Doubt 6: Does `true` mean SQL executed successfully?

**Not exactly.**

For `execute()`:

```text
true
 ↓
The first result is a ResultSet
```

And:

```text
false
 ↓
The first result is an update count
or there is no result
```

So don't interpret it as:

```text
true  = success
false = failure
```

❌ Wrong mental model.

Use:

```text
true  = ResultSet
false = update count/no result
```

---

## ❓ Doubt 7: If `execute()` returns true, where do I get the ResultSet?

Use:

```java
ResultSet rs =
    st.getResultSet();
```

Example:

```java
boolean result =
    st.execute(
        "SELECT * FROM student"
    );

if (result) {

    ResultSet rs =
        st.getResultSet();

    while (rs.next()) {
        System.out.println(
            rs.getString("name")
        );
    }
}
```

Flow:

```text
execute()
   ↓
true
   ↓
getResultSet()
   ↓
ResultSet
```

---

## ❓ Doubt 8: If `execute()` returns false, what do I do?

Check the update count:

```java
int count =
    st.getUpdateCount();
```

Example:

```java
boolean result =
    st.execute(
        "UPDATE student " +
        "SET marks = 90"
    );

if (!result) {

    int count =
        st.getUpdateCount();

    System.out.println(count);
}
```

---

## ❓ Doubt 9: Why does `execute()` return boolean instead of `ResultSet`?

Because `execute()` is designed to handle different kinds of results.

The first result might be:

```text
ResultSet
```

or:

```text
Update count
```

Therefore it can't simply promise:

```java
ResultSet
```

Instead:

```text
execute()
   ↓
boolean
   ↓
"What type of first result did I get?"
```

---

## ❓ Doubt 10: When should I use `execute()`?

Use it when you need **general-purpose execution**, particularly when:

* the result type isn't known in advance;
* the SQL can produce different result types;
* multiple results may need to be processed.

For ordinary SQL, prefer the more specific methods when possible:

```text
SELECT          → executeQuery()
INSERT/UPDATE/
DELETE          → executeUpdate()
```

---

# 3. `executeQuery()` — DOUBTS

## ❓ Doubt 11: What does `executeQuery()` return?

It returns:

```java
ResultSet
```

Example:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

Memory:

```text
executeQuery()
      ↓
 ResultSet
```

---

## ❓ Doubt 12: Why is it called `executeQuery()`?

Because it is intended for executing a query that returns a result set.

The classic example:

```sql
SELECT * FROM student;
```

Think:

```text
Query
 ↓
Rows
 ↓
ResultSet
```

---

## ❓ Doubt 13: Can I use `executeQuery()` for UPDATE?

Normally, **no**.

This is wrong usage:

```java
ResultSet rs =
    st.executeQuery(
        "UPDATE student SET marks = 90"
    );
```

An update does not normally produce the `ResultSet` expected by `executeQuery()`.

Use:

```java
int count =
    st.executeUpdate(
        "UPDATE student SET marks = 90"
    );
```

---

## ❓ Doubt 14: Is `executeQuery()` only for SELECT?

For beginner-level understanding:

```text
SELECT → executeQuery()
```

is the rule you should remember.

More precisely, `executeQuery()` is for SQL that produces a single `ResultSet`; `SELECT` is the standard use case.

---

## ❓ Doubt 15: Why do I need `ResultSet`?

Because the database may return many rows.

For example:

```text
101 Ravi 90
102 Raj  85
103 Amit 95
```

`ResultSet` provides a cursor-based way to read those rows.

```java
while (rs.next()) {
    System.out.println(
        rs.getString("name")
    );
}
```

---

## ❓ Doubt 16: Why do we call `rs.next()`?

Initially, the cursor is positioned **before the first row**.

```text
Before Row 1
     ↓
 rs.next()
     ↓
 Row 1
     ↓
 rs.next()
     ↓
 Row 2
```

Therefore:

```java
while (rs.next()) {
    ...
}
```

moves through the returned rows.

---

# 4. `executeUpdate()` — DOUBTS

## ❓ Doubt 17: What does `executeUpdate()` return?

It returns:

```java
int
```

The integer represents the applicable **update count**.

Example:

```java
int count =
    st.executeUpdate(
        "UPDATE student " +
        "SET marks = 90 " +
        "WHERE id = 101"
    );
```

If one row is affected:

```text
count = 1
```

---

## ❓ Doubt 18: Is the returned `int` the new value?

No.

For:

```sql
UPDATE student SET marks = 90;
```

the returned `int` does **not** mean:

```text
90
```

It represents the number of affected rows, subject to JDBC/database semantics.

Example:

```text
100 rows updated
     ↓
executeUpdate()
     ↓
100
```

---

## ❓ Doubt 19: Which SQL commands commonly use `executeUpdate()`?

Commonly:

```text
INSERT
UPDATE
DELETE
```

For example:

```java
int count =
    st.executeUpdate(
        "DELETE FROM student " +
        "WHERE id = 101"
    );
```

---

## ❓ Doubt 20: Can DDL use `executeUpdate()`?

Yes, applicable DDL statements can be executed with `executeUpdate()`, such as:

```sql
CREATE TABLE
ALTER TABLE
DROP TABLE
```

The exact update-count behavior for DDL can depend on the database/driver.

So the better rule is:

> **`executeUpdate()` is for SQL expected to produce an update count rather than a `ResultSet`.**

---

## ❓ Doubt 21: Does `executeUpdate()` return `ResultSet`?

No.

```text
executeUpdate()
      ↓
     int
```

While:

```text
executeQuery()
      ↓
  ResultSet
```

---

# 5. `executeBatch()` — DOUBTS

## ❓ Doubt 22: What is a batch?

A batch is a group of SQL commands accumulated for execution.

Example:

```java
st.addBatch(sql1);
st.addBatch(sql2);
st.addBatch(sql3);
```

Then:

```java
st.executeBatch();
```

---

## ❓ Doubt 23: Does `addBatch()` execute SQL?

**No.**

This:

```java
st.addBatch(sql);
```

means:

> Add SQL to the batch.

Execution happens when:

```java
st.executeBatch();
```

is called.

---

## ❓ Doubt 24: Why use batch processing?

Suppose you have:

```text
1,000 INSERT statements
```

Instead of treating every command as a separate JDBC execution operation, you can group them into a batch.

```text
SQL 1 ┐
SQL 2 │
SQL 3 │
SQL 4 ├── Batch
 ...  │
SQL N ┘
        ↓
executeBatch()
```

This can reduce communication overhead and improve performance, depending on the driver/database/workload.

---

## ❓ Doubt 25: What does `executeBatch()` return?

It returns:

```java
int[]
```

Example:

```java
int[] counts =
    st.executeBatch();
```

Why an array?

Because multiple commands were executed.

Conceptually:

```text
SQL 1 → 1
SQL 2 → 1
SQL 3 → 1
```

could result in:

```text
[1, 1, 1]
```

The exact entries can include JDBC-defined special values for some batch outcomes, depending on driver behavior.

---

## ❓ Doubt 26: Is `executeBatch()` the same as a transaction?

**No.**

This confusion is extremely common.

### Batch

```text
Groups commands
```

### Transaction

```text
Controls commit/rollback
```

They are independent concepts.

You can combine them:

```java
con.setAutoCommit(false);

st.addBatch(sql1);
st.addBatch(sql2);
st.addBatch(sql3);

st.executeBatch();

con.commit();
```

---

# 6. The Biggest Confusion — Four Methods

## ❓ Doubt 27: What's the easiest way to decide which method to use?

Ask:

### Question 1:

> "Do I want rows?"

Use:

```java
executeQuery()
```

Returns:

```text
ResultSet
```

---

### Question 2:

> "Do I want to modify data and know how many rows were affected?"

Use:

```java
executeUpdate()
```

Returns:

```text
int
```

---

### Question 3:

> "Do I need general execution because I don't know what kind of first result I'll get?"

Use:

```java
execute()
```

Returns:

```text
boolean
```

---

### Question 4:

> "Do I have multiple commands to execute as a batch?"

Use:

```java
executeBatch()
```

Returns:

```text
int[]
```

---

# 7. The Most Important Return-Type Trick

If you forget everything else, remember this:

```text
execute()
       ↓
   boolean

executeQuery()
       ↓
   ResultSet

executeUpdate()
       ↓
      int

executeBatch()
       ↓
      int[]
```

This single table solves many JDBC exam/interview questions.

---

# 8. `execute()` vs `executeQuery()` — DOUBT KILLER

### `executeQuery()`

You already know what you expect:

```text
ResultSet
```

Example:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

### `execute()`

You want general execution:

```java
boolean b =
    st.execute(sql);
```

Then determine the first result:

```java
if (b) {
    ResultSet rs =
        st.getResultSet();
} else {
    int count =
        st.getUpdateCount();
}
```

So:

> **`executeQuery()` directly gives the `ResultSet`; `execute()` first tells you whether the first result is a `ResultSet`.**

---

# 9. `execute()` — TRUE/FALSE DOUBT KILLER

This is worth memorizing.

### ❌ Wrong

```text
true  = SQL successful
false = SQL failed
```

### ✅ Correct

```text
true
 ↓
First result is ResultSet

false
 ↓
First result is update count
or no result
```

SQL failure is normally communicated through a JDBC `SQLException`, not by `execute()` returning `false`.

---

# 10. `executeQuery()` vs `executeUpdate()` — DOUBT KILLER

| Question                    | `executeQuery()` | `executeUpdate()`                        |
| --------------------------- | ---------------- | ---------------------------------------- |
| Main purpose                | Retrieve rows    | Perform operation producing update count |
| Common SQL                  | `SELECT`         | `INSERT`, `UPDATE`, `DELETE`             |
| Return type                 | `ResultSet`      | `int`                                    |
| Reads returned rows?        | Yes              | No                                       |
| Returns affected-row count? | No               | Yes                                      |

Memory:

```text
QUERY
 ↓
ResultSet

UPDATE
 ↓
int
```

---

# 11. `executeUpdate()` vs `executeBatch()` — DOUBT KILLER

Suppose you have:

```text
SQL1
SQL2
SQL3
```

If you execute one operation:

```java
int count =
    st.executeUpdate(sql1);
```

One command → one update count.

If you accumulate several:

```java
st.addBatch(sql1);
st.addBatch(sql2);
st.addBatch(sql3);

int[] counts =
    st.executeBatch();
```

Multiple commands → array of batch results/statuses.

So:

```text
executeUpdate()
      ↓
one SQL operation
      ↓
int
```

while:

```text
executeBatch()
      ↓
multiple accumulated operations
      ↓
int[]
```

---

# 12. Does Batch Automatically Roll Back Everything If One SQL Fails?

**Do not assume that.**

Batch execution and transaction management are separate.

If you need all-or-nothing behavior, explicitly manage the transaction:

```java
con.setAutoCommit(false);

try {
    st.addBatch(sql1);
    st.addBatch(sql2);
    st.addBatch(sql3);

    st.executeBatch();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

The exact behavior of a batch failure and the update counts returned is governed by JDBC and the database driver's behavior, so don't assume every driver behaves identically in every failure scenario.

---

# 13. Does `Statement` Prevent SQL Injection?

**No.**

Consider:

```java
String sql =
    "SELECT * FROM student " +
    "WHERE name = '" + name + "'";

st.executeQuery(sql);
```

If `name` comes from untrusted input, this can create SQL-injection vulnerabilities.

For parameterized SQL, prefer:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student " +
        "WHERE name = ?"
    );

ps.setString(1, name);

ResultSet rs =
    ps.executeQuery();
```

So:

```text
Static/simple SQL
      ↓
Statement can be appropriate

Parameterized SQL
      ↓
PreparedStatement is generally preferred
```

---

# 14. Can `Statement` Execute Multiple Results?

Yes.

This is another reason `execute()` exists.

A statement can potentially produce multiple results.

Conceptually:

```text
execute()
   ↓
Result 1
   ↓
getMoreResults()
   ↓
Result 2
   ↓
getMoreResults()
   ↓
Result 3
```

This is especially relevant with stored procedures or database-specific operations that can return multiple results.

---

# 15. Does `executeBatch()` Return `ResultSet`?

No.

Its return type is:

```java
int[]
```

So:

```java
ResultSet rs =
    st.executeBatch();
```

❌ Wrong.

Correct:

```java
int[] result =
    st.executeBatch();
```

---

# 16. Does `executeQuery()` Return `int`?

No.

```java
int result =
    st.executeQuery(sql);
```

❌ Wrong.

Correct:

```java
ResultSet rs =
    st.executeQuery(sql);
```

---

# 17. Does `executeUpdate()` Return `ResultSet`?

No.

```java
ResultSet rs =
    st.executeUpdate(sql);
```

❌ Wrong.

Correct:

```java
int count =
    st.executeUpdate(sql);
```

---

# 18. Can `execute()` Return `ResultSet` Directly?

No.

This is important.

```java
ResultSet rs =
    st.execute(sql);
```

❌ Wrong.

`execute()` returns:

```java
boolean
```

Then if the result is a `ResultSet`:

```java
boolean result =
    st.execute(sql);

if (result) {

    ResultSet rs =
        st.getResultSet();
}
```

---

# 19. Statement Complete Example

```java
import java.sql.*;

public class StatementDemo {

    public static void main(String[] args)
            throws SQLException {

        Connection con =
            DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/college",
                "root",
                "password"
            );

        Statement st =
            con.createStatement();

        // Query
        ResultSet rs =
            st.executeQuery(
                "SELECT * FROM student"
            );

        while (rs.next()) {
            System.out.println(
                rs.getString("name")
            );
        }

        // Update
        int count =
            st.executeUpdate(
                "UPDATE student " +
                "SET marks = 90 " +
                "WHERE id = 101"
            );

        System.out.println(
            "Rows updated: " + count
        );

        rs.close();
        st.close();
        con.close();
    }
}
```

Modern JDBC code should preferably use try-with-resources:

```java
try (
    Connection con =
        DriverManager.getConnection(
            url, username, password
        );

    Statement st =
        con.createStatement();

    ResultSet rs =
        st.executeQuery(
            "SELECT * FROM student"
        )
) {
    while (rs.next()) {
        System.out.println(
            rs.getString("name")
        );
    }
}
```

---

# 20. Ultimate DOUBTKILLER Table

| Method            | Returns     | Think                             | Common use                                   |
| ----------------- | ----------- | --------------------------------- | -------------------------------------------- |
| `execute()`       | `boolean`   | "What type of first result?"      | General/multiple-result handling             |
| `executeQuery()`  | `ResultSet` | "Give me rows"                    | `SELECT`                                     |
| `executeUpdate()` | `int`       | "How many were affected?"         | `INSERT`, `UPDATE`, `DELETE`, applicable DDL |
| `executeBatch()`  | `int[]`     | "Execute my accumulated commands" | Multiple SQL commands                        |

---

# 🧠 The Ultimate Memory Diagram

```text
                         STATEMENT
                             │
             "I want to execute SQL"
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
        ▼                    ▼                    ▼
   Need rows?          Need update count?    General result?
        │                    │                    │
        ▼                    ▼                    ▼
executeQuery()        executeUpdate()        execute()
        │                    │                    │
        ▼                    ▼                 boolean
   ResultSet                 int                  │
                                             ┌────┴────┐
                                             │         │
                                           true      false
                                             │         │
                                             ▼         ▼
                                         ResultSet  update count
```

And:

```text
                    MANY SQL COMMANDS
                           │
                           ▼
                     addBatch()
                           │
                     addBatch()
                           │
                     addBatch()
                           │
                           ▼
                    executeBatch()
                           │
                           ▼
                          int[]
```

---

# 🚨 10 Interview Traps to Never Forget

### 1.

`Statement` is:

**Interface**, not class.

### 2.

`execute()` returns:

**`boolean`**, not `ResultSet`.

### 3.

`execute()` returning `true` means:

**First result is a `ResultSet`**, not "SQL succeeded."

### 4.

`execute()` returning `false` means:

**First result is an update count or there is no result.**

### 5.

`executeQuery()` returns:

**`ResultSet`**

### 6.

`executeUpdate()` returns:

**`int`**

### 7.

`executeBatch()` returns:

**`int[]`**

### 8.

`addBatch()`:

**Doesn't execute immediately.**

### 9.

Batching:

**Is not the same thing as a transaction.**

### 10.

For user-supplied parameters:

**Prefer `PreparedStatement` rather than constructing SQL by string concatenation.**

---

# 🏆 Final DOUBTKILLER Formula

```text
┌─────────────────────────────────────────────┐
│               STATEMENT                     │
├─────────────────────────────────────────────┤
│                                             │
│ execute()       → boolean                   │
│                   true  → ResultSet         │
│                   false → update/no result  │
│                                             │
│ executeQuery()  → ResultSet                 │
│                   "Give me rows"            │
│                                             │
│ executeUpdate() → int                       │
│                   "Give me update count"    │
│                                             │
│ executeBatch()  → int[]                     │
│                   "Execute multiple cmds"   │
│                                             │
└─────────────────────────────────────────────┘
```

### 🔥 One sentence to lock it into memory:

> **`executeQuery()` gives rows, `executeUpdate()` gives an update count, `execute()` tells you whether the first result is rows or an update/no result, and `executeBatch()` executes accumulated commands and returns batch results.**
