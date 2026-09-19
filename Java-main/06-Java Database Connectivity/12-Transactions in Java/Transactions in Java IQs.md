# 12. Transactions in Java — DOUBTKILLER

This is the **confusion-removal version**.
For every sub-concept, we'll answer:

* **What is it?**
* **Why do we need it?**
* **How does it work?**
* **What exactly happens?**
* **What is it NOT?**
* **Common doubts**
* **Interview traps**
* **Example**

---

# 1. TRANSACTION

## 1.1 What is a Transaction?

A **transaction** is a group of related database operations that should be treated as **one logical unit of work**.

Example: transferring ₹1,000.

```text
Account A → -₹1,000
Account B → +₹1,000
```

There are two SQL operations:

```sql
UPDATE account
SET balance = balance - 1000
WHERE id = 1;
```

and:

```sql
UPDATE account
SET balance = balance + 1000
WHERE id = 2;
```

These two operations belong to one logical transaction.

---

## 1.2 Why do we need a transaction?

Imagine:

```text
Debit Account A     → SUCCESS
Credit Account B    → FAILURE
```

Without proper transaction handling:

```text
A → ₹1,000 lost
B → ₹1,000 not received
```

The database is left with a partial operation.

We want:

```text
Both operations SUCCESS
          ↓
       COMMIT
```

or:

```text
Any operation FAILURE
          ↓
      ROLLBACK
```

Therefore:

> **Transaction protects a group of related database operations from being partially completed.**

---

## 1.3 Transaction ≠ SQL statement

This is a very common doubt.

### SQL statement

```sql
UPDATE account SET balance = balance - 1000;
```

One SQL operation.

### Transaction

```text
Transaction
│
├── UPDATE
├── UPDATE
├── INSERT
└── UPDATE
```

One transaction can contain many SQL statements.

Therefore:

```text
SQL Statement ≠ Transaction
```

---

## 1.4 Transaction ≠ Connection

Another common doubt.

```java
Connection con;
```

is a JDBC connection to the database.

A transaction is a unit of database work controlled through that connection.

Think:

```text
Connection
    │
    └── Transaction
          │
          ├── SQL 1
          ├── SQL 2
          └── SQL 3
```

One connection can have transaction state.

---

## 1.5 Who controls JDBC transactions?

The main JDBC object is:

```java
Connection
```

Important methods:

```java
con.setAutoCommit(false);

con.commit();

con.rollback();

con.setSavepoint();
```

So remember:

> **JDBC transaction control happens primarily through the `Connection` interface.**

---

## 1.6 Basic transaction code

```java
con.setAutoCommit(false);

try {

    statement1.executeUpdate();
    statement2.executeUpdate();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

Meaning:

```text
Disable automatic completion
          ↓
Perform operations
          ↓
Everything successful?
       /       \
     YES        NO
      ↓          ↓
   COMMIT     ROLLBACK
```

---

# 2. AUTO-COMMIT

This is one of the biggest JDBC transaction doubts.

---

## 2.1 What is auto-commit?

JDBC `Connection` normally starts with:

```java
autoCommit = true
```

You can check:

```java
boolean value = con.getAutoCommit();
```

You can set it:

```java
con.setAutoCommit(true);
```

or:

```java
con.setAutoCommit(false);
```

---

# 2.2 What happens when auto-commit is TRUE?

Suppose:

```java
con.setAutoCommit(true);
```

Then you execute:

```java
statement.executeUpdate(
    "UPDATE student SET marks = 90 WHERE id = 1"
);
```

The statement is automatically committed according to JDBC/database transaction semantics.

Conceptually:

```text
execute SQL
    ↓
statement completes
    ↓
automatic commit
```

You don't normally need:

```java
con.commit();
```

for each individual operation.

---

# 2.3 What happens when auto-commit is FALSE?

```java
con.setAutoCommit(false);
```

Now you have manual transaction control.

Example:

```java
statement1.executeUpdate();
statement2.executeUpdate();
statement3.executeUpdate();

con.commit();
```

Or:

```java
statement1.executeUpdate();
statement2.executeUpdate();

con.rollback();
```

Flow:

```text
setAutoCommit(false)
        ↓
SQL 1
        ↓
SQL 2
        ↓
SQL 3
        ↓
   ┌────┴────┐
   ↓         ↓
Success    Failure
   ↓         ↓
commit()  rollback()
```

---

# 2.4 Does `setAutoCommit(false)` start a transaction?

### Beginner answer

It enables manual transaction control.

### More precise answer

It disables automatic transaction completion. The transaction boundary behavior is then controlled explicitly through commit/rollback, subject to JDBC/database semantics.

For learning purposes:

```text
setAutoCommit(false)
       ↓
Manual transaction management
```

is the correct mental model.

---

# 2.5 Is `setAutoCommit(false)` SQL?

**No.**

This:

```java
con.setAutoCommit(false);
```

is a JDBC API method call.

It does not mean:

```sql
SET AUTOCOMMIT = ...
```

from the perspective of your Java source code.

It changes the connection's transaction mode.

---

# 2.6 Does `setAutoCommit(false)` execute a database operation?

It changes connection transaction behavior; it isn't your business SQL such as `INSERT`, `UPDATE`, or `DELETE`.

Compare:

```java
ps.executeUpdate();
```

→ executes SQL.

```java
con.setAutoCommit(false);
```

→ changes transaction handling.

---

# 2.7 Why disable auto-commit?

Suppose:

```text
Operation 1 → SUCCESS
Operation 2 → SUCCESS
Operation 3 → FAILURE
```

With automatic completion, earlier operations may already have been committed.

With manual transaction control:

```java
con.setAutoCommit(false);
```

you can do:

```java
con.rollback();
```

and abandon the transaction's uncommitted changes.

---

# 2.8 Major doubt: Does every SQL statement create a transaction?

Don't memorize this as:

> "Every SQL statement is a transaction."

That's misleading.

A transaction is a logical unit of work.

When auto-commit is enabled, JDBC treats each completed statement as a transaction boundary in the normal case.

When auto-commit is disabled, multiple statements can belong to one transaction.

---

# 2.9 Auto-commit comparison

| Auto-commit                   | Meaning                                              |
| ----------------------------- | ---------------------------------------------------- |
| `true`                        | Automatic transaction completion                     |
| `false`                       | Application manually controls transaction completion |
| Default JDBC connection state | Normally `true`                                      |
| Manual success control        | `commit()`                                           |
| Manual failure control        | `rollback()`                                         |

---

# 3. `commit()`

---

## 3.1 What is `commit()`?

```java
con.commit();
```

means:

> **Successfully complete the current transaction.**

Example:

```java
con.setAutoCommit(false);

operation1();
operation2();

con.commit();
```

---

## 3.2 Why do we need `commit()`?

Because after:

```java
con.setAutoCommit(false);
```

you are manually controlling the transaction.

Suppose:

```java
operation1();
operation2();
```

Both succeed.

You then say:

```java
con.commit();
```

Meaning:

> "I approve the transaction. Complete it."

---

# 3.3 Does `commit()` execute SQL?

**No.**

This is a critical distinction.

```java
ps.executeUpdate();
```

means:

> Execute SQL.

Whereas:

```java
con.commit();
```

means:

> Complete the current transaction.

Therefore:

```text
executeUpdate() → SQL execution
commit()        → Transaction completion
```

---

# 3.4 Does commit make data permanent?

In ordinary database terminology, a successful commit makes the transaction's changes committed.

The **database** provides the actual persistence/durability guarantees.

So the accurate statement is:

> `commit()` completes the transaction successfully, and the database's durability mechanisms ensure committed data survives according to its guarantees.

---

# 3.5 Can rollback undo a committed transaction?

Normally:

**No.**

Example:

```java
con.commit();

con.rollback();
```

The later rollback cannot be used to undo the already completed transaction.

Think:

```text
BEFORE COMMIT
     ↓
Uncommitted changes
     ↓
rollback() possible

COMMIT
     ↓
Transaction completed

After COMMIT
     ↓
rollback() does not undo that completed transaction
```

---

# 3.6 What happens if `commit()` itself fails?

`commit()` can throw:

```java
SQLException
```

Therefore robust JDBC code handles exceptions.

Example:

```java
try {
    con.commit();
} catch (SQLException e) {
    // Handle commit failure
}
```

This is important in production transaction handling.

---

# 4. `rollback()`

---

## 4.1 What is rollback?

```java
con.rollback();
```

means:

> **Abandon the current transaction's uncommitted database changes.**

It is normally used when something goes wrong.

---

## 4.2 Basic example

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

Flow:

```text
Operation 1 ✓
Operation 2 ✗
      ↓
 rollback()
      ↓
Abandon uncommitted changes
```

---

# 4.3 Does rollback mean "delete the rows"?

No.

This is a major misunderstanding.

Suppose:

```sql
INSERT INTO student VALUES (1, 'A');
```

was performed inside an uncommitted transaction.

Then:

```java
con.rollback();
```

does not mean:

```sql
DELETE FROM student WHERE id = 1;
```

Instead, the database transaction is rolled back.

It is **transaction reversal**, not Java code generating inverse SQL.

---

# 4.4 Does rollback undo Java code?

Absolutely **not**.

Example:

```java
int x = 10;

x = 20;

con.rollback();
```

After rollback:

```text
x = 20
```

Rollback doesn't restore:

```text
x = 10
```

because:

```text
rollback()
     ↓
Database transaction
```

not:

```text
rollback()
     ↓
Java memory/history
```

---

# 4.5 Does rollback undo an email?

No.

Suppose:

```text
1. INSERT order
2. Send email
3. UPDATE payment
4. Error
5. rollback()
```

The rollback can affect the database transaction.

It cannot unsend the email.

This is why external side effects require additional architectural patterns in real applications.

---

# 4.6 Does rollback undo committed data?

No.

Suppose:

```text
Transaction 1
    ↓
commit()
```

Later:

```text
Transaction 2
    ↓
rollback()
```

Transaction 2's rollback does not undo Transaction 1's committed work.

---

# 4.7 `rollback()` with auto-commit TRUE

If:

```java
con.setAutoCommit(true);
```

then each completed statement is normally automatically committed.

Therefore manually calling:

```java
con.rollback();
```

does not provide a way to undo already completed auto-committed statements.

This is exactly why multi-operation units often require:

```java
con.setAutoCommit(false);
```

---

# 5. SAVEPOINT

---

## 5.1 What is a Savepoint?

A **Savepoint** is a marker inside a transaction.

Example:

```java
Savepoint sp = con.setSavepoint();
```

Later:

```java
con.rollback(sp);
```

means:

> Roll back to that point rather than rolling back the entire transaction.

---

# 5.2 Why do we need Savepoint?

Suppose:

```text
Transaction
│
├── Operation A
├── Operation B
├── Operation C
└── Operation D
```

Suppose you want:

```text
A and B → keep
C and D → undo
```

A complete:

```java
con.rollback();
```

is too broad.

Instead:

```java
Savepoint sp = con.setSavepoint();
```

before C:

```text
A
↓
B
↓
SAVEPOINT
↓
C
↓
D
↓
ROLLBACK TO SAVEPOINT
```

Then the transaction can potentially continue and later commit.

---

# 5.3 Complete rollback vs Savepoint rollback

### Complete rollback

```java
con.rollback();
```

Conceptually:

```text
A
↓
B
↓
C
↓
ROLLBACK
↓
Abandon transaction's uncommitted changes
```

### Savepoint rollback

```java
con.rollback(sp);
```

Conceptually:

```text
A
↓
B
↓
SAVEPOINT
↓
C
↓
D
↓
ROLLBACK TO SAVEPOINT
↓
Continue transaction
```

---

# 5.4 Is Savepoint a backup?

**No.**

This is a very important distinction.

```text
Savepoint → transaction rollback marker
Backup    → persistent data recovery mechanism
```

A savepoint does not create a database backup.

---

# 5.5 Is Savepoint a commit?

**No.**

```text
Savepoint
    ↓
Marker

Commit
    ↓
Complete transaction
```

Therefore:

```text
SAVEPOINT ≠ COMMIT
```

---

# 5.6 Can we continue after rollback to Savepoint?

Yes, normally.

Example:

```java
con.setAutoCommit(false);

operation1();

Savepoint sp = con.setSavepoint();

operation2();

con.rollback(sp);

operation3();

con.commit();
```

Conceptually:

```text
operation1
    ↓
SAVEPOINT
    ↓
operation2
    ↓
ROLLBACK TO SAVEPOINT
    ↓
operation3
    ↓
COMMIT
```

---

# 5.7 Named Savepoint

You can create a named savepoint:

```java
Savepoint sp =
    con.setSavepoint("BeforePayment");
```

Then:

```java
con.rollback(sp);
```

This can make code easier to understand.

---

# 5.8 Savepoint inside transaction

Think of it this way:

```text
              TRANSACTION
                   │
        ┌──────────┼──────────┐
        ↓          ↓          ↓
      SQL A      SQL B      SQL C
                   │
                   ↓
              SAVEPOINT
                   │
                   ↓
                 SQL D
                   │
                   ↓
                 SQL E
```

The savepoint belongs to the transaction.

It doesn't exist independently as a separate transaction.

---

# 6. ACID BASICS

Now the biggest theory topic.

```text
A → Atomicity
C → Consistency
I → Isolation
D → Durability
```

---

# 6.1 ATOMICITY

## What is it?

**All or Nothing.**

Example:

```text
Transfer ₹1,000

Debit A ✓
Credit B ✗
```

The transaction shouldn't simply remain half-completed.

Conceptually:

```text
Both succeed
    OR
Transaction changes are rolled back
```

---

## Doubt: Does Atomicity mean every SQL statement individually succeeds?

No.

Atomicity is about the **transaction as a whole**.

Example:

```text
Transaction
│
├── INSERT ✓
├── UPDATE ✓
├── DELETE ✗
```

The transaction can be rolled back as a unit.

---

## Memory

```text
A = All or Nothing
```

---

# 6.2 CONSISTENCY

## What is it?

Consistency means that a successful transaction preserves the database's defined integrity rules and moves it from one valid state to another valid state.

Example:

Suppose:

```text
Student ID = PRIMARY KEY
```

Then duplicate IDs are not allowed.

```text
Before transaction → valid state
        ↓
Transaction
        ↓
After successful transaction → valid state
```

---

## Doubt: Does consistency mean the data cannot change?

**No.**

Data is allowed to change.

Example:

```text
Balance = ₹5,000
       ↓
Transaction
       ↓
Balance = ₹4,000
```

That's perfectly consistent if it satisfies the database/application's rules.

---

## Examples of integrity rules

```text
PRIMARY KEY
FOREIGN KEY
UNIQUE
NOT NULL
CHECK
```

---

## Memory

```text
C = Consistent / Correct database state
```

---

# 6.3 ISOLATION

## What is it?

Isolation concerns how concurrently executing transactions interact with one another and what intermediate changes they can see.

Imagine:

```text
Transaction A
      ↕
Transaction B
```

Both are running at the same time.

Isolation helps prevent problematic interference.

---

# 6.4 Dirty Read

Suppose:

```text
Transaction A
    ↓
UPDATE balance = 10000
    ↓
Not committed
```

Transaction B reads:

```text
10000
```

Then A does:

```text
ROLLBACK
```

The value B read was never committed.

That's a:

> **Dirty Read**

---

# 6.5 Non-repeatable Read

Transaction A:

```text
SELECT marks → 80
```

Transaction B:

```text
UPDATE marks → 90
COMMIT
```

Transaction A executes the same query again:

```text
SELECT marks → 90
```

Same row, different value.

That's:

> **Non-repeatable Read**

---

# 6.6 Phantom Read

Transaction A:

```sql
SELECT *
FROM student
WHERE marks >= 80;
```

Result:

```text
Student 1
Student 2
```

Transaction B inserts:

```text
Student 3 → marks = 90
```

and commits.

Transaction A repeats the query:

```text
Student 1
Student 2
Student 3
```

A new matching row appeared.

That's:

> **Phantom Read**

---

# 6.7 JDBC Isolation Levels

JDBC defines these standard isolation constants:

```java
Connection.TRANSACTION_READ_UNCOMMITTED
Connection.TRANSACTION_READ_COMMITTED
Connection.TRANSACTION_REPEATABLE_READ
Connection.TRANSACTION_SERIALIZABLE
```

You can request one using:

```java
con.setTransactionIsolation(
    Connection.TRANSACTION_READ_COMMITTED
);
```

But the actual supported behavior depends on the database and JDBC driver.

---

# 6.8 DURABILITY

## What is it?

Once a transaction successfully commits, its changes are intended to survive subsequent failures according to the database's durability guarantees.

Example:

```text
Transaction
    ↓
commit()
    ↓
Database records committed transaction
    ↓
System crashes
    ↓
Database restarts
    ↓
Committed data should remain
```

---

## Doubt: Does JDBC itself guarantee physical disk persistence?

Not by itself.

JDBC provides:

```java
con.commit();
```

The database engine and its recovery/storage mechanisms provide the underlying durability guarantees.

---

# 7. ACID — The Deepest Confusion

Don't confuse these four:

| Property        | Main question                                             |
| --------------- | --------------------------------------------------------- |
| **Atomicity**   | Is the transaction all-or-nothing?                        |
| **Consistency** | Does successful work preserve valid database rules/state? |
| **Isolation**   | How do concurrent transactions interact?                  |
| **Durability**  | Does committed work survive failures?                     |

Easy memory:

```text
A → All or Nothing
C → Correct/Valid State
I → Interaction between concurrent transactions
D → Data survives successful commit
```

---

# 8. COMPLETE TRANSACTION EXAMPLE

Let's put everything together.

```java
Connection con = null;

try {

    con = DriverManager.getConnection(
        url,
        username,
        password
    );

    con.setAutoCommit(false);

    try (PreparedStatement ps1 =
             con.prepareStatement(
                 "UPDATE account " +
                 "SET balance = balance - ? " +
                 "WHERE id = ?"
             );
         PreparedStatement ps2 =
             con.prepareStatement(
                 "UPDATE account " +
                 "SET balance = balance + ? " +
                 "WHERE id = ?"
             )) {

        ps1.setDouble(1, 1000);
        ps1.setInt(2, 1);

        int rows1 = ps1.executeUpdate();

        if (rows1 != 1) {
            throw new SQLException("Debit failed");
        }

        ps2.setDouble(1, 1000);
        ps2.setInt(2, 2);

        int rows2 = ps2.executeUpdate();

        if (rows2 != 1) {
            throw new SQLException("Credit failed");
        }

        con.commit();

    } catch (SQLException e) {

        con.rollback();
        throw e;
    }

} catch (SQLException e) {

    e.printStackTrace();

} finally {

    if (con != null) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

Understand the important part:

```java
con.setAutoCommit(false);
```

↓

```text
Manual transaction control
```

Then:

```java
ps1.executeUpdate();
ps2.executeUpdate();
```

↓

```text
Two operations belong to the transaction
```

Then:

```java
con.commit();
```

↓

```text
SUCCESS
```

If anything fails:

```java
con.rollback();
```

↓

```text
FAILURE → abandon uncommitted transaction changes
```

---

# 9. COMPLETE DOUBTKILLER TABLE

| Doubt                                                      | Correct answer                                |
| ---------------------------------------------------------- | --------------------------------------------- |
| Is a transaction the same as a SQL statement?              | ❌ No                                          |
| Can one transaction contain many SQL statements?           | ✅ Yes                                         |
| Is a transaction the same as a Connection?                 | ❌ No                                          |
| Which JDBC object controls transactions?                   | `Connection`                                  |
| Is auto-commit normally enabled?                           | ✅ Yes                                         |
| Does `setAutoCommit(false)` execute business SQL?          | ❌ No                                          |
| Does auto-commit mean every Java statement commits?        | ❌ No; it concerns JDBC transaction behavior   |
| Does `executeUpdate()` mean commit?                        | ❌ No                                          |
| Does `commit()` execute SQL?                               | ❌ No                                          |
| What does `commit()` do?                                   | Completes the current transaction             |
| What does `rollback()` do?                                 | Abandons uncommitted transaction changes      |
| Does rollback undo Java variables?                         | ❌ No                                          |
| Does rollback unsend an email?                             | ❌ No                                          |
| Can rollback undo already committed transaction work?      | ❌ No                                          |
| Is Savepoint a backup?                                     | ❌ No                                          |
| Is Savepoint a commit?                                     | ❌ No                                          |
| Can we continue after `rollback(savepoint)`?               | ✅ Normally yes                                |
| Is Savepoint inside a transaction?                         | ✅ Yes                                         |
| Does Atomicity mean all-or-nothing?                        | ✅ Yes                                         |
| Does Consistency mean data never changes?                  | ❌ No                                          |
| Does Isolation concern concurrent transactions?            | ✅ Yes                                         |
| Does Durability concern committed data surviving failures? | ✅ Yes                                         |
| Does JDBC itself physically guarantee durability?          | ❌ No; database/storage/recovery mechanisms do |
| Can isolation levels differ by database?                   | ✅ Yes                                         |
| Is ACID a JDBC class/interface?                            | ❌ No                                          |

---

# 10. The Most Important Differences

## `commit()` vs `rollback()`

```text
Everything successful
        ↓
     commit()

Something failed
        ↓
    rollback()
```

---

## `rollback()` vs `rollback(savepoint)`

```text
rollback()
    ↓
Whole current transaction's uncommitted changes

rollback(savepoint)
    ↓
Roll back to a specific point in current transaction
```

---

## Transaction vs Savepoint

```text
TRANSACTION
   ↓
Complete unit of database work

SAVEPOINT
   ↓
Marker inside that unit
```

---

## Auto-commit vs Manual transaction

```text
AUTO-COMMIT = TRUE
       ↓
Automatic transaction completion

AUTO-COMMIT = FALSE
       ↓
Application controls completion
       ↓
commit() / rollback()
```

---

## ACID vs JDBC methods

This distinction is important:

```text
ACID
 ↓
Properties of transaction processing

JDBC methods
 ↓
APIs used to control transactions
```

For example:

```text
Atomicity
   ↕
rollback()

Durability
   ↕
commit() + database recovery/storage

Isolation
   ↕
setTransactionIsolation()

Transaction control
   ↕
setAutoCommit()
commit()
rollback()
```

Don't say:

> "`commit()` is ACID."

Instead:

> "`commit()` is a JDBC transaction-control operation that contributes to the successful completion of a transaction; ACID describes the properties expected of transaction processing."

---

# 11. FINAL MASTER DIAGRAM

```text
                         JDBC TRANSACTION
                                │
                ┌───────────────┴───────────────┐
                ↓                               ↓
          Auto-commit TRUE              Auto-commit FALSE
                │                               │
                ↓                               ↓
       Automatic completion            Manual transaction
                                                │
                              ┌─────────────────┼─────────────────┐
                              ↓                 ↓                 ↓
                           SQL #1            SQL #2             SQL #3
                              │                 │                 │
                              └─────────────────┼─────────────────┘
                                                ↓
                                          Everything OK?
                                           /           \
                                         YES            NO
                                          ↓              ↓
                                      commit()       rollback()
                                          │              │
                                          ↓              ↓
                                     Transaction      Abandon
                                      completed       uncommitted
                                                       changes
                                          │
                                          ↓
                                      Savepoint
                                          │
                                          ↓
                                Partial rollback point
                                          │
                                          ↓
                                        ACID
                                          │
                   ┌──────────────────────┼──────────────────────┐
                   ↓                      ↓                      ↓
                  A                       C                      I                       D
                   ↓                      ↓                      ↓                      ↓
             Atomicity              Consistency              Isolation              Durability
             All/Nothing             Valid State             Concurrency            Survives
```

# 🔥 FINAL DOUBTKILLER RULE

If you remember only this, you can reconstruct the whole topic:

```text
Transaction
   ↓
Group related database operations

setAutoCommit(false)
   ↓
"I want manual transaction control"

commit()
   ↓
"Everything succeeded"

rollback()
   ↓
"Something failed"

Savepoint
   ↓
"Go back to this point inside the transaction"

ACID
   ↓
A → All or Nothing
C → Valid/Consistent State
I → Control concurrent interaction
D → Committed data survives failures
```

**The single most important JDBC pattern is:**

```java
con.setAutoCommit(false);

try {
    // related SQL operations

    con.commit();       // success
} catch (SQLException e) {
    con.rollback();     // failure
}
```

That is the foundation on which the more advanced JDBC transaction concepts are built.
