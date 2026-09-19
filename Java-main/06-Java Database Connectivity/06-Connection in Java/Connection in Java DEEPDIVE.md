# 6. Connection in Java — DEEPDIVE

`Connection` is the **central JDBC interface used after a database connection has been established**.

If `DriverManager` gets us into the database:

```text
DriverManager
      ↓
getConnection()
      ↓
Connection
```

then `Connection` gives us the tools to actually work with the database:

```text
Connection
│
├── createStatement()
├── prepareStatement()
├── prepareCall()
│
├── setAutoCommit()
├── commit()
├── rollback()
│
└── close()
```

The key idea is:

> **`Connection` represents a session/connection between a Java application and a database and provides methods for executing SQL through statement objects and controlling transactions.**

---

# 1. Connection Interface

## 1.1 What is `Connection`?

`Connection` is an interface in:

```java
java.sql.Connection
```

It represents a connection/session with a specific database.

Example:

```java
Connection con =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

The flow is:

```text
Java Application
       ↓
DriverManager
       ↓
getConnection()
       ↓
Connection
       ↓
Database
```

---

## 1.2 Why is `Connection` an interface?

The JDBC API defines standard interfaces so that Java code does not have to depend directly on a particular database vendor's implementation.

For example:

```java
Connection con;
```

is JDBC-level code.

The actual implementation is supplied by the JDBC driver.

Conceptually:

```text
                 JDBC API
                    │
                    ▼
             Connection
              interface
                    ▲
                    │
          JDBC Driver implementation
                    │
                    ▼
                Database
```

Therefore, your application can use:

```java
Connection
```

without directly creating a database-vendor-specific implementation.

---

# 1.3 Can we create `Connection` using `new`?

Normally, no.

This is invalid:

```java
Connection con = new Connection();
```

because `Connection` is an interface.

Instead:

```java
Connection con =
    DriverManager.getConnection(
        url,
        user,
        password
    );
```

The JDBC driver supplies the implementation.

---

# 1.4 What can a Connection do?

A `Connection` gives access to several important categories of JDBC functionality.

### Statement creation

```text
createStatement()
prepareStatement()
prepareCall()
```

### Transaction control

```text
setAutoCommit()
commit()
rollback()
```

### Resource management

```text
close()
```

So:

```text
Connection
│
├── SQL execution setup
│   ├── Statement
│   ├── PreparedStatement
│   └── CallableStatement
│
├── Transaction management
│   ├── setAutoCommit()
│   ├── commit()
│   └── rollback()
│
└── Resource management
    └── close()
```

---

# 2. `createStatement()`

## 2.1 What is `createStatement()`?

`createStatement()` is a `Connection` method that creates a `Statement` object.

```java
Statement st =
    con.createStatement();
```

The flow:

```text
Connection
    ↓
createStatement()
    ↓
Statement
    ↓
execute SQL
```

---

# 2.2 What is `Statement`?

`Statement` is another JDBC interface:

```java
java.sql.Statement
```

It provides methods for executing SQL statements.

For example:

```java
Statement st =
    con.createStatement();

ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

---

# 2.3 When should `Statement` be used?

It is suitable when the SQL is already completely formed and does not need parameter placeholders.

Example:

```java
Statement st =
    con.createStatement();

st.executeUpdate(
    "DELETE FROM student WHERE id = 101"
);
```

There is no parameter:

```text
?
```

in this SQL.

---

# 2.4 Why shouldn't we concatenate user input?

Suppose:

```java
String name = getUserInput();

String sql =
    "SELECT * FROM student WHERE name = '"
    + name + "'";
```

Then:

```java
Statement st =
    con.createStatement();

st.executeQuery(sql);
```

is dangerous if `name` is untrusted.

The input becomes part of the SQL syntax itself.

This can create SQL injection vulnerabilities.

For values supplied by users, prefer:

```java
PreparedStatement
```

---

# 2.5 Important methods of `Statement`

After creating it:

```java
Statement st =
    con.createStatement();
```

you commonly use:

### `executeQuery()`

For queries that return a `ResultSet`.

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

### `executeUpdate()`

For SQL statements that modify data or database structure and return an update count.

```java
int count =
    st.executeUpdate(
        "UPDATE student SET marks = 90 WHERE id = 101"
    );
```

### `execute()`

A general-purpose execution method that can handle SQL where the result may be either a `ResultSet` or an update count.

```java
boolean result =
    st.execute(sql);
```

---

# 3. `prepareStatement()`

## 3.1 What is `prepareStatement()`?

`prepareStatement()` creates a `PreparedStatement` for executing parameterized SQL.

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

Flow:

```text
Connection
     ↓
prepareStatement()
     ↓
PreparedStatement
     ↓
set parameter values
     ↓
execute
```

---

# 3.2 What is the `?`

Consider:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";
```

The `?` is a **parameter placeholder**.

Then:

```java
ps.setInt(1, 101);
```

means:

```text
First ? → 101
```

Then:

```java
ps.executeQuery();
```

executes the prepared statement.

---

# 3.3 Parameter indexes start at 1

This is a classic JDBC point.

Given:

```java
String sql =
    "SELECT * FROM student WHERE id = ? AND name = ?";
```

we use:

```java
ps.setInt(1, 101);
ps.setString(2, "Ravi");
```

Not:

```java
ps.setInt(0, 101);       // wrong
```

JDBC parameter indexes are **1-based**.

---

# 3.4 Example: INSERT

```java
String sql =
    "INSERT INTO student(id, name, marks) " +
    "VALUES (?, ?, ?)";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);
ps.setString(2, "Ravi");
ps.setInt(3, 90);

int rows =
    ps.executeUpdate();
```

Flow:

```text
SQL template
    ↓
?
?
?
    ↓
Set values
    ↓
Execute
```

---

# 3.5 Why is `PreparedStatement` preferred?

## Reason 1 — Parameterized SQL

Instead of:

```java
"SELECT ... WHERE id = " + id
```

use:

```java
"SELECT ... WHERE id = ?"
```

---

## Reason 2 — SQL injection protection

The parameter is bound as a value rather than being directly concatenated into the SQL text.

---

## Reason 3 — Reuse

You can execute the same prepared SQL structure with different values.

Example:

```java
String sql =
    "SELECT * FROM student WHERE id = ?";

PreparedStatement ps =
    con.prepareStatement(sql);

ps.setInt(1, 101);
ResultSet rs1 =
    ps.executeQuery();

ps.setInt(1, 102);
ResultSet rs2 =
    ps.executeQuery();
```

---

## Important nuance

Don't oversimplify this as:

> "PreparedStatement is always faster."

Performance depends on the database, JDBC driver, workload, and how statements are used.

Its biggest application-level advantages are **parameterization, safety, and convenient repeated execution**.

---

# 4. `prepareCall()`

## 4.1 What is `prepareCall()`?

`prepareCall()` creates a `CallableStatement`.

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

Flow:

```text
Connection
     ↓
prepareCall()
     ↓
CallableStatement
     ↓
Stored procedure/function
     ↓
Database
```

---

# 4.2 What is `CallableStatement`?

It is a JDBC interface used to call stored procedures and stored functions supported by the database/driver.

```java
java.sql.CallableStatement
```

---

# 4.3 Example

Suppose the database provides a procedure conceptually like:

```sql
getStudent(101)
```

JDBC may call it using:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );

cs.setInt(1, 101);

ResultSet rs =
    cs.executeQuery();
```

The exact procedure/function syntax and whether a `ResultSet` is returned depend on the database and stored routine.

---

# 4.4 IN parameters

An input parameter can be supplied using:

```java
cs.setInt(1, 101);
```

For example:

```text
{call getStudent(?)}
              ↑
              IN parameter
```

---

# 4.5 OUT parameters

Stored procedures can also have output parameters.

For example, conceptually:

```text
procedure getStudentName(
    IN id,
    OUT name
)
```

JDBC can register an OUT parameter:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

Then after execution:

```java
cs.execute();

String name =
    cs.getString(2);
```

So:

```text
IN parameter
    ↓
setXxx()

OUT parameter
    ↓
registerOutParameter()
    ↓
getXxx()
```

---

# 5. `commit()`

Now we move from **SQL execution setup** to **transaction management**.

## 5.1 What is a transaction?

A transaction is a logical unit of work consisting of one or more database operations.

For example, transferring ₹1,000:

```text
Account A
   ↓
- ₹1,000

Account B
   ↓
+ ₹1,000
```

These operations logically belong together.

We don't want:

```text
A → money removed
B → money not added
```

because that leaves inconsistent data.

---

# 5.2 What does `commit()` do?

```java
con.commit();
```

It commits the current transaction.

Conceptually:

```text
Transaction
    ↓
SQL operations
    ↓
commit()
    ↓
Changes committed
```

---

# 5.3 When is explicit `commit()` needed?

Typically when:

```java
con.setAutoCommit(false);
```

has been set.

Example:

```java
con.setAutoCommit(false);

statement1.executeUpdate();
statement2.executeUpdate();

con.commit();
```

---

# 5.4 Example: money transfer

Conceptually:

```java
con.setAutoCommit(false);

debit.executeUpdate();
credit.executeUpdate();

con.commit();
```

The idea is:

```text
Debit
  +
Credit
  ↓
Commit together
```

---

# 5.5 Does `commit()` mean "save Java variables"?

❌ No.

It concerns the current database transaction.

```text
commit()
   ↓
Database transaction
```

It has nothing to do with:

```text
Java variable memory
```

---

# 6. `rollback()`

## 6.1 What is `rollback()`?

```java
con.rollback();
```

It rolls back uncommitted changes in the current transaction, subject to the database's transaction semantics.

Example:

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

# 6.2 Why do we need rollback?

Suppose:

```text
Operation 1 → successful
Operation 2 → successful
Operation 3 → failed
```

If these operations form one transaction, we may want to undo the uncommitted work rather than leave the database in a partially updated state.

```text
Operation 1
Operation 2
Operation 3 ✗
     ↓
rollback()
     ↓
Uncommitted transaction changes undone
```

---

# 6.3 `rollback()` doesn't mean "restore the whole database"

This is an important distinction.

It rolls back the current transaction's uncommitted changes.

It does **not** mean:

```text
Restore database to yesterday
```

or:

```text
Undo all historical changes
```

---

# 6.4 What if we already committed?

Once a transaction has been committed, a normal subsequent rollback does not simply undo that committed transaction.

```text
SQL
 ↓
commit()
 ↓
Committed
 ↓
rollback()
```

You cannot use the later rollback as a general "undo last commit" operation.

---

# 7. `setAutoCommit()`

## 7.1 What is auto-commit?

JDBC connections normally start with auto-commit enabled.

Conceptually:

```java
con.getAutoCommit();
```

normally returns:

```text
true
```

for a newly created connection, subject to the JDBC/driver behavior and connection configuration.

---

# 7.2 `setAutoCommit(true)`

```java
con.setAutoCommit(true);
```

When auto-commit is enabled, each statement is treated as a transaction and is committed automatically when execution completes successfully.

Conceptually:

```text
Statement 1
    ↓
Execute
    ↓
Commit

Statement 2
    ↓
Execute
    ↓
Commit
```

---

# 7.3 `setAutoCommit(false)`

```java
con.setAutoCommit(false);
```

Now the application controls transaction completion:

```text
Statement 1
     ↓
Statement 2
     ↓
Statement 3
     ↓
   ┌─┴─┐
   ↓   ↓
commit rollback
```

---

# 7.4 Why disable auto-commit?

Because multiple operations may need to succeed or fail as a unit.

Example:

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

This gives the application explicit transaction control.

---

# 7.5 Important behavior of `setAutoCommit()`

A subtle point:

If you call:

```java
con.setAutoCommit(false);
```

you enter manual transaction control.

If you later call:

```java
con.setAutoCommit(true);
```

JDBC's transaction semantics require that if there is an active transaction, changing auto-commit to `true` commits that transaction.

So don't casually switch auto-commit modes in the middle of transaction logic.

---

# 7.6 `getAutoCommit()`

Although not in your requested list, it is useful to know:

```java
boolean status =
    con.getAutoCommit();
```

It tells you whether auto-commit is currently enabled.

---

# 8. `close()`

## 8.1 What does `close()` do?

```java
con.close();
```

Closes the connection and releases JDBC/database resources associated with it.

---

# 8.2 What happens after `close()`?

The connection is closed.

You should not continue using it:

```java
con.close();

con.createStatement();  // invalid
```

Such operations normally result in a `SQLException`.

---

# 8.3 Why is closing important?

Database connections are limited resources.

If an application continuously opens connections and doesn't close them:

```text
Connection 1 → not closed
Connection 2 → not closed
Connection 3 → not closed
...
```

eventually it can exhaust the application's/database's available connections.

---

# 8.4 Does closing Connection close the database?

❌ Absolutely not.

This:

```java
con.close();
```

means:

```text
Close THIS application's JDBC connection
```

It does not mean:

```text
Shutdown MySQL
Delete database
Drop tables
```

---

# 8.5 Does `close()` commit or rollback?

You should **not use `close()` as your transaction decision mechanism**.

Before closing, transaction handling should be explicit.

For example:

```java
try {

    con.setAutoCommit(false);

    // operations

    con.commit();

} catch (SQLException e) {

    con.rollback();

} finally {

    con.close();
}
```

With an active transaction, the precise behavior associated with closing a connection can involve driver/database-specific details, so robust application code should explicitly `commit()` or `rollback()` rather than relying on `close()` to decide the transaction outcome.

---

# 9. Connection and Statement Hierarchy

This is one of the most important diagrams in JDBC:

```text
                    Connection
                         │
             ┌───────────┼───────────┐
             │           │           │
             ▼           ▼           ▼
      createStatement  prepare    prepareCall
             │         Statement       │
             ▼           │             ▼
         Statement        ▼       CallableStatement
                       Prepared
                       Statement
```

More accurately:

```text
Connection
│
├── createStatement()
│       ↓
│   Statement
│
├── prepareStatement()
│       ↓
│   PreparedStatement
│
└── prepareCall()
        ↓
    CallableStatement
```

---

# 10. Statement Selection

| Requirement                                 | Use                 |
| ------------------------------------------- | ------------------- |
| Simple SQL with no user-supplied parameters | `Statement`         |
| Parameterized SQL                           | `PreparedStatement` |
| Stored procedure/function                   | `CallableStatement` |

Example:

### Statement

```java
Statement st =
    con.createStatement();
```

### PreparedStatement

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

### CallableStatement

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

---

# 11. Complete Transaction Example

Consider a bank transfer.

```java
Connection con =
    DriverManager.getConnection(
        url,
        user,
        password
    );

try {

    con.setAutoCommit(false);

    PreparedStatement debit =
        con.prepareStatement(
            "UPDATE account " +
            "SET balance = balance - ? " +
            "WHERE id = ?"
        );

    PreparedStatement credit =
        con.prepareStatement(
            "UPDATE account " +
            "SET balance = balance + ? " +
            "WHERE id = ?"
        );

    debit.setDouble(1, 1000);
    debit.setInt(2, 101);

    credit.setDouble(1, 1000);
    credit.setInt(2, 102);

    debit.executeUpdate();
    credit.executeUpdate();

    con.commit();

} catch (SQLException e) {

    con.rollback();

} finally {

    con.close();
}
```

The conceptual flow:

```text
Connection
    ↓
setAutoCommit(false)
    ↓
Debit
    ↓
Credit
    ↓
Both successful?
   / \
 Yes  No
 ↓     ↓
commit rollback
   \   /
    close
```

---

# 12. Modern Resource Management

Instead of manually closing everything:

```java
Connection con = null;
PreparedStatement ps = null;

try {
    ...
}
finally {
    if (ps != null) ps.close();
    if (con != null) con.close();
}
```

prefer try-with-resources:

```java
try (
    Connection con =
        DriverManager.getConnection(
            url,
            user,
            password
        );

    PreparedStatement ps =
        con.prepareStatement(
            "SELECT * FROM student WHERE id = ?"
        )
) {

    ps.setInt(1, 101);

    try (ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            System.out.println(
                rs.getString("name")
            );
        }
    }
}
```

Resources are automatically closed.

---

# 13. `Connection` Doesn't Execute SQL Directly

This is a very important conceptual distinction.

You don't normally do:

```java
con.executeQuery(...);  // ❌
```

Instead:

```text
Connection
    ↓
Statement object
    ↓
execute SQL
```

For example:

```java
PreparedStatement ps =
    con.prepareStatement(sql);

ps.executeQuery();
```

Therefore:

> **Connection provides the environment/session from which statement objects are created; statement objects execute SQL.**

---

# 14. `Connection` Controls Transactions

This is the other major responsibility.

```text
Connection
│
├── setAutoCommit()
├── commit()
└── rollback()
```

Example:

```java
con.setAutoCommit(false);

try {

    // multiple SQL operations

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

---

# 15. The Complete JDBC Relationship

```text
                   DriverManager
                         │
                         │ getConnection()
                         ▼
                    Connection
                         │
        ┌────────────────┼────────────────┐
        │                │                │
        ▼                ▼                ▼
 createStatement   prepareStatement   prepareCall
        │                │                │
        ▼                ▼                ▼
    Statement      PreparedStatement   CallableStatement
        │                │                │
        └────────────────┼────────────────┘
                         ▼
                    Execute SQL
                         │
                         ▼
                     Database
```

And transaction management happens through the `Connection`:

```text
Connection
    │
    ▼
setAutoCommit(false)
    │
    ▼
SQL operations
    │
    ├───────────────┐
    ▼               ▼
 commit()       rollback()
```

Finally:

```text
Connection
    ↓
close()
    ↓
Resources released
```

---

# 16. Common Deep-Dive Confusions

## Confusion 1: Is `Connection` a class?

❌ No.

It is an interface:

```java
java.sql.Connection
```

---

## Confusion 2: Does `DriverManager` return a database?

❌ No.

```text
getConnection()
       ↓
Connection
```

---

## Confusion 3: Does `Connection` execute SQL?

Not directly.

It creates statement objects that execute SQL.

```text
Connection
    ↓
PreparedStatement
    ↓
executeQuery()
```

---

## Confusion 4: Is `Statement` better than `PreparedStatement`?

Not generally.

For parameterized/user-supplied values, `PreparedStatement` is normally preferred.

---

## Confusion 5: Is `PreparedStatement` only for SELECT?

❌ No.

It can be used for:

```text
SELECT
INSERT
UPDATE
DELETE
and other supported SQL operations
```

Example:

```java
PreparedStatement ps =
    con.prepareStatement(
        "DELETE FROM student WHERE id = ?"
    );

ps.setInt(1, 101);

ps.executeUpdate();
```

---

## Confusion 6: Is `CallableStatement` the same as `PreparedStatement`?

❌ No.

```text
PreparedStatement
    ↓
Parameterized SQL

CallableStatement
    ↓
Stored procedure/function calls
```

---

## Confusion 7: Is `commit()` required after every SQL statement?

❌ Not necessarily.

With:

```java
con.setAutoCommit(true);
```

successful statements are automatically committed.

With:

```java
con.setAutoCommit(false);
```

you explicitly manage transaction completion.

---

## Confusion 8: Does `rollback()` undo committed changes?

❌ No.

Rollback applies to the current transaction's uncommitted changes.

---

## Confusion 9: Does `close()` mean database shutdown?

❌ No.

It closes the application's JDBC connection.

---

# 17. Important Transaction Example

Consider:

```java
con.setAutoCommit(false);

updateA();
updateB();
updateC();

con.commit();
```

Think of all three operations as one logical unit:

```text
┌───────────────────────────┐
│       Transaction         │
│                           │
│ updateA()                 │
│ updateB()                 │
│ updateC()                 │
│                           │
│       commit()            │
└───────────────────────────┘
```

If an error occurs:

```java
try {

    updateA();
    updateB();
    updateC();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

---

# 18. The 8 Concepts in One Table

| Concept              | Purpose                                              | Returns / Result    |
| -------------------- | ---------------------------------------------------- | ------------------- |
| `Connection`         | Represents database session/connection               | Interface           |
| `createStatement()`  | Creates simple SQL statement object                  | `Statement`         |
| `prepareStatement()` | Creates parameterized SQL statement                  | `PreparedStatement` |
| `prepareCall()`      | Creates stored routine call object                   | `CallableStatement` |
| `commit()`           | Commits current transaction                          | `void`              |
| `rollback()`         | Rolls back current transaction's uncommitted changes | `void`              |
| `setAutoCommit()`    | Enables/disables automatic transaction commit        | `void`              |
| `close()`            | Closes connection and releases resources             | `void`              |

---

# 19. Deep Mental Model

Think of `Connection` as the **control center of a JDBC database session**.

```text
                       CONNECTION
                            │
       ┌────────────────────┼────────────────────┐
       │                    │                    │
       ▼                    ▼                    ▼
  SQL Creation         Transactions          Resources
       │                    │                    │
       ├── Statement        ├── autoCommit       └── close()
       ├── PreparedStmt     ├── commit()
       └── CallableStmt     └── rollback()
```

That's why `Connection` is so important.

It sits between:

```text
Java Application
       ↓
Connection
       ↓
Database
```

and provides both **SQL execution setup** and **transaction/resource management**.

---

# 20. 🔥 DEEPDIVE MASTER FLOW

```text
DriverManager.getConnection()
              │
              ▼
         Connection
              │
       ┌──────┼──────┐
       │      │      │
       ▼      ▼      ▼
    Statement Prepared Callable
       │      │      │
       └──────┼──────┘
              ▼
          Execute SQL
              │
              ▼
           Database
```

Transaction side:

```text
Connection
     │
     ▼
setAutoCommit(false)
     │
     ▼
Multiple SQL operations
     │
     ├───────────────┐
     ▼               ▼
  commit()       rollback()
     │               │
     └───────┬───────┘
             ▼
          close()
```

## Final takeaway

> **`Connection` is the JDBC session interface through which a Java application creates `Statement`, `PreparedStatement`, and `CallableStatement` objects, manages transactions using `setAutoCommit()`, `commit()`, and `rollback()`, and finally releases the database connection using `close()`.**
