# 6. Connection in Java — 3LEVEL

We will study **each sub-concept individually in 3 levels**:

* 🟢 **Level 1 — Beginner:** What it is
* 🟡 **Level 2 — Intermediate:** How it works
* 🔴 **Level 3 — Advanced:** Important rules, internal relationships, and interview-level understanding

---

# 1. `Connection` Interface

## 🟢 LEVEL 1 — Beginner

`Connection` is an interface in:

```java
java.sql.Connection
```

It represents a **connection/session between a Java application and a database**.

We normally obtain it using:

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

Simple picture:

```text
Java Application
       ↓
   Connection
       ↓
   Database
```

### Important

`Connection` itself is an interface.

We don't normally create it using:

```java
new Connection();   // ❌
```

Instead, the JDBC driver provides the implementation.

---

## 🟡 LEVEL 2 — Intermediate

A `Connection` allows us to do several important things:

```text
Connection
│
├── Create Statement
│   ├── createStatement()
│   ├── prepareStatement()
│   └── prepareCall()
│
├── Transaction Management
│   ├── setAutoCommit()
│   ├── commit()
│   └── rollback()
│
└── Resource Management
    └── close()
```

So `Connection` is not merely "a link."

It is also the **context in which SQL execution and transaction management happen**.

---

## 🔴 LEVEL 3 — Advanced

The actual `Connection` object is supplied by the JDBC driver.

Conceptually:

```text
             JDBC API
                │
                ▼
        Connection interface
                ▲
                │
        Driver implementation
                │
                ▼
             Database
```

Your Java code can therefore remain largely database-independent.

For example:

```java
Connection con =
    DriverManager.getConnection(...);
```

The exact implementation depends on the installed JDBC driver.

### Important distinction

```text
Connection ≠ Database
Connection ≠ Driver
Connection ≠ Statement
```

* **Database** → stores data
* **Driver** → knows how to communicate with a particular database
* **Connection** → represents the application's database session
* **Statement** → executes SQL

---

# 2. `createStatement()`

## 🟢 LEVEL 1 — Beginner

`createStatement()` creates a `Statement` object.

Syntax:

```java
Statement st =
    con.createStatement();
```

Then we can execute SQL:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

Flow:

```text
Connection
    ↓
createStatement()
    ↓
Statement
    ↓
SQL
```

---

## 🟡 LEVEL 2 — Intermediate

`Statement` is suitable when SQL doesn't require parameter placeholders.

Example:

```java
Statement st =
    con.createStatement();

int rows =
    st.executeUpdate(
        "UPDATE student SET marks = 90 WHERE id = 101"
    );
```

`createStatement()` has multiple overloads that allow you to specify things such as:

* result-set type
* concurrency
* holdability

For example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY
    );
```

---

## 🔴 LEVEL 3 — Advanced

Do **not** choose `Statement` simply because it is shorter.

If values come from users or variables, `PreparedStatement` is generally preferred.

For example, avoid constructing SQL like:

```java
String sql =
    "SELECT * FROM student WHERE name = '" + name + "'";
```

Instead:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE name = ?"
    );

ps.setString(1, name);
```

### Interview point

`createStatement()`:

> Creates a `Statement` object for sending SQL statements to the database.

---

# 3. `prepareStatement()`

## 🟢 LEVEL 1 — Beginner

`prepareStatement()` creates a `PreparedStatement`.

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

Here:

```text
?
```

is a parameter placeholder.

Then:

```java
ps.setInt(1, 101);
```

and:

```java
ResultSet rs =
    ps.executeQuery();
```

---

## 🟡 LEVEL 2 — Intermediate

Suppose SQL is:

```sql
SELECT * FROM student
WHERE id = ? AND name = ?
```

Java:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student " +
        "WHERE id = ? AND name = ?"
    );

ps.setInt(1, 101);
ps.setString(2, "Ravi");

ResultSet rs =
    ps.executeQuery();
```

Remember:

> JDBC parameter indexes start at **1**, not 0.

```text
? #1 → ps.setInt(1, ...)
? #2 → ps.setString(2, ...)
```

---

## 🔴 LEVEL 3 — Advanced

`PreparedStatement` is important for:

### 1. Parameterized SQL

```java
"SELECT * FROM student WHERE id = ?"
```

### 2. Safer handling of external values

Values are supplied through setter methods rather than concatenated into SQL.

### 3. Repeated execution

For example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "UPDATE student SET marks = ? WHERE id = ?"
    );

ps.setInt(1, 90);
ps.setInt(2, 101);
ps.executeUpdate();

ps.setInt(1, 95);
ps.setInt(2, 102);
ps.executeUpdate();
```

The same prepared SQL structure can be reused.

### Critical distinction

```text
Statement
    ↓
SQL text directly

PreparedStatement
    ↓
SQL template + parameters
```

---

# 4. `prepareCall()`

## 🟢 LEVEL 1 — Beginner

`prepareCall()` creates a `CallableStatement`.

It is used to call stored procedures/functions supported by the database.

Example:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

Then:

```java
cs.setInt(1, 101);

ResultSet rs =
    cs.executeQuery();
```

---

## 🟡 LEVEL 2 — Intermediate

The relationship is:

```text
Connection
    ↓
prepareCall()
    ↓
CallableStatement
    ↓
Stored Procedure / Function
    ↓
Database
```

For example, suppose the database contains a stored procedure conceptually like:

```text
getStudent(101)
```

Java can invoke it through JDBC.

---

## 🔴 LEVEL 3 — Advanced

Stored procedures can have:

```text
IN parameters
OUT parameters
INOUT parameters
```

For example:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudentName(?, ?)}"
    );

cs.setInt(1, 101);

cs.registerOutParameter(
    2,
    Types.VARCHAR
);

cs.execute();

String name =
    cs.getString(2);
```

The exact stored procedure syntax varies between database systems.

### Memory trick

```text
Statement
      ↓
Simple SQL

PreparedStatement
      ↓
Parameterized SQL

CallableStatement
      ↓
Stored procedure/function
```

---

# 5. `commit()`

## 🟢 LEVEL 1 — Beginner

`commit()` permanently applies the changes of the current transaction.

Example:

```java
con.commit();
```

Usually used after:

```java
con.setAutoCommit(false);
```

Example:

```java
con.setAutoCommit(false);

updateStudent1();
updateStudent2();

con.commit();
```

---

## 🟡 LEVEL 2 — Intermediate

Suppose we have:

```text
Transaction
│
├── UPDATE A
├── UPDATE B
└── UPDATE C
```

If everything succeeds:

```java
con.commit();
```

Conceptually:

```text
UPDATE A
UPDATE B
UPDATE C
      ↓
   commit()
      ↓
Transaction completed
```

---

## 🔴 LEVEL 3 — Advanced

`commit()` is meaningful in the context of transaction boundaries.

For example:

```java
con.setAutoCommit(false);

try {
    // SQL operations

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

A successful commit ends the current transaction and starts the next transaction context for subsequent work when auto-commit remains disabled.

### Important

`commit()` does **not** mean:

> "Save my Java program."

It means:

> **Commit the current database transaction.**

---

# 6. `rollback()`

## 🟢 LEVEL 1 — Beginner

`rollback()` is used to undo changes that have not been committed in the current transaction.

Example:

```java
con.rollback();
```

Typical pattern:

```java
con.setAutoCommit(false);

try {

    operation1();
    operation2();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

---

## 🟡 LEVEL 2 — Intermediate

Suppose:

```text
Transaction
│
├── Operation A ✓
├── Operation B ✓
└── Operation C ✗
```

We don't want the transaction to remain partially applied.

So:

```java
con.rollback();
```

Conceptually:

```text
Operations
    ↓
rollback()
    ↓
Uncommitted transaction changes undone
```

---

## 🔴 LEVEL 3 — Advanced

A rollback affects the current transaction's uncommitted changes.

It does **not** mean:

```text
Delete database ❌
Delete table ❌
Delete all historical data ❌
```

It means:

```text
Undo uncommitted changes
```

### Important limitation

Exactly what can be rolled back depends on the database, transaction configuration, and the operations involved.

Also, once a transaction has been committed:

```java
con.commit();
```

you cannot normally use:

```java
con.rollback();
```

to undo that already-committed transaction.

---

# 7. `setAutoCommit()`

## 🟢 LEVEL 1 — Beginner

By default, JDBC connections generally start with auto-commit enabled.

You can explicitly set it:

```java
con.setAutoCommit(true);
```

or:

```java
con.setAutoCommit(false);
```

---

## 🟡 LEVEL 2 — Intermediate

### `true`

Each individual statement is generally treated as its own transaction and committed automatically when successful.

Conceptually:

```text
SQL 1 → Commit
SQL 2 → Commit
SQL 3 → Commit
```

### `false`

You control the transaction:

```java
con.setAutoCommit(false);

SQL 1
SQL 2
SQL 3

con.commit();
```

or:

```java
con.rollback();
```

---

## 🔴 LEVEL 3 — Advanced

This is extremely important for multi-step operations.

Suppose money transfer requires:

```text
Debit Account A
+
Credit Account B
```

We want both operations to belong to the same transaction.

```java
con.setAutoCommit(false);

try {

    debit();
    credit();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

Without transaction control, one operation could succeed while another fails, leaving the database in an unwanted intermediate state.

### Key relationship

```text
setAutoCommit(false)
        ↓
Manual transaction control
        ↓
 ┌──────┴──────┐
 ↓             ↓
commit()    rollback()
```

---

# 8. `close()`

## 🟢 LEVEL 1 — Beginner

`close()` closes the `Connection`.

```java
con.close();
```

It releases JDBC/database resources associated with the connection.

---

## 🟡 LEVEL 2 — Intermediate

After:

```java
con.close();
```

you should not continue using that connection.

For example:

```java
con.close();

con.createStatement();  // ❌
```

will result in an exception because the connection is closed.

---

## 🔴 LEVEL 3 — Advanced

Closing the connection also closes resources that are dependent on that connection, subject to JDBC/driver behavior.

This is why modern JDBC code commonly uses **try-with-resources**.

```java
try (
    Connection con =
        DriverManager.getConnection(
            url,
            username,
            password
        )
) {

    // JDBC operations

}
```

At the end of the try block, Java automatically closes the connection.

---

# 🔥 ALL 8 CONCEPTS TOGETHER

Now connect everything:

```text
                       Connection
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
        ▼                  ▼                  ▼
createStatement()  prepareStatement()  prepareCall()
        │                  │                  │
        ▼                  ▼                  ▼
   Statement        PreparedStatement   CallableStatement
        │                  │                  │
        └──────────────────┼──────────────────┘
                           ▼
                       Execute SQL
                           │
                           ▼
                       Transaction
                           │
                    setAutoCommit(false)
                           │
                     ┌─────┴─────┐
                     ▼           ▼
                  commit()   rollback()
                     │           │
                     └─────┬─────┘
                           ▼
                         close()
```

---

# 🧠 3-Level Comparison Table

| Concept              | 🟢 Level 1                  | 🟡 Level 2                                | 🔴 Level 3                                                   |
| -------------------- | --------------------------- | ----------------------------------------- | ------------------------------------------------------------ |
| `Connection`         | Database session            | Creates statements + manages transactions | JDBC driver supplies implementation                          |
| `createStatement()`  | Creates `Statement`         | Used for direct SQL                       | Prefer parameterized alternatives when values are external   |
| `prepareStatement()` | Creates `PreparedStatement` | Uses `?` parameters                       | Supports parameterization and repeated execution             |
| `prepareCall()`      | Creates `CallableStatement` | Calls stored procedures/functions         | Supports IN/OUT/INOUT parameters                             |
| `commit()`           | Confirms transaction        | Makes transaction changes durable         | Ends current transaction successfully                        |
| `rollback()`         | Undoes uncommitted changes  | Used when transaction fails               | Applies to current uncommitted transaction                   |
| `setAutoCommit()`    | Controls automatic commit   | `true` = automatic, `false` = manual      | Essential for multi-operation transaction boundaries         |
| `close()`            | Closes connection           | Releases resources                        | Connection becomes unusable; try-with-resources is preferred |

---

# 🎯 The Most Important Relationships

Memorize these five chains:

### Chain 1 — Connection

```text
DriverManager
     ↓
getConnection()
     ↓
Connection
```

### Chain 2 — SQL

```text
Connection
     ↓
createStatement()
     ↓
Statement
```

### Chain 3 — Parameterized SQL

```text
Connection
     ↓
prepareStatement()
     ↓
PreparedStatement
```

### Chain 4 — Stored procedure

```text
Connection
     ↓
prepareCall()
     ↓
CallableStatement
```

### Chain 5 — Transaction

```text
Connection
     ↓
setAutoCommit(false)
     ↓
SQL operations
     ↓
 ┌───┴───┐
 ↓       ↓
commit rollback
```

---

# ⭐ FINAL 3-LEVEL MEMORY MAP

```text
LEVEL 1 — WHAT?
────────────────────────────────

Connection
    ↓
Database session

createStatement()
    ↓
Statement

prepareStatement()
    ↓
PreparedStatement

prepareCall()
    ↓
CallableStatement

commit()
    ↓
Confirm

rollback()
    ↓
Undo uncommitted work

setAutoCommit()
    ↓
Automatic/manual transaction control

close()
    ↓
Release connection
```

```text
LEVEL 2 — HOW?
────────────────────────────────

Connection
    │
    ├── SQL execution
    │      ├── Statement
    │      ├── PreparedStatement
    │      └── CallableStatement
    │
    └── Transaction management
           ├── setAutoCommit()
           ├── commit()
           └── rollback()
```

```text
LEVEL 3 — WHY/IMPORTANT?
────────────────────────────────

Connection
    ↓
Driver-provided implementation
    ↓
Database communication

Statement
    ↓
Direct SQL

PreparedStatement
    ↓
Parameterized SQL
    ↓
Safer external-value handling

CallableStatement
    ↓
Stored routines
    ↓
IN / OUT / INOUT

setAutoCommit(false)
    ↓
Group multiple operations
    ↓
commit() OR rollback()

close()
    ↓
Release resources
```

## One sentence to remember

> **`Connection` is the JDBC database session through which we create SQL-execution objects, control transactions with `setAutoCommit()`, `commit()` and `rollback()`, and finally release resources with `close()`.**
