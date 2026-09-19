# 12. Transactions in Java — DEEPDIVE

Transactions are one of the most important JDBC concepts because real applications rarely perform only one database operation. Often, **multiple SQL operations must succeed together**.

We will study each concept individually:

```text
12. Transactions
│
├── Transaction
├── Auto-commit
├── commit()
├── rollback()
├── Savepoint
└── ACID basics
```

---

# 1. Transaction

## 1.1 What is a Transaction?

A **transaction** is a logical unit of database work consisting of one or more operations that should be treated together.

For example, transferring ₹1,000 from Account A to Account B requires at least two operations:

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

These are logically one operation from the application's point of view:

```text
Transfer ₹1000
     │
     ├── Debit A
     │
     └── Credit B
```

We don't want this:

```text
A → -1000
B → operation failed
```

We want either:

```text
A → -1000
B → +1000
```

or:

```text
A → unchanged
B → unchanged
```

That's the purpose of a transaction.

---

# 1.2 Why do we need transactions?

Suppose a bank transfer contains:

```text
1. Deduct money from A
2. Add money to B
```

Imagine step 1 succeeds:

```text
A = ₹9,000
B = ₹5,000
```

but step 2 fails because of some database error.

Without proper transaction handling:

```text
A = ₹9,000
B = ₹5,000
```

₹1,000 has effectively disappeared.

With a transaction:

```text
Step 1 → SUCCESS
Step 2 → FAILURE
             ↓
          ROLLBACK
             ↓
Original state restored
```

---

# 1.3 Transaction boundary

A transaction has a beginning and an ending.

Conceptually:

```text
Transaction begins
       ↓
Operation 1
       ↓
Operation 2
       ↓
Operation 3
       ↓
COMMIT / ROLLBACK
       ↓
Transaction ends
```

The exact transaction-boundary behavior depends on the database and JDBC connection state, but from the application's perspective, `commit()` and `rollback()` are the key completion controls when auto-commit is disabled.

---

# 1.4 Transaction in JDBC

A typical manual transaction looks like:

```java
con.setAutoCommit(false);

try {

    // SQL operation 1
    ps1.executeUpdate();

    // SQL operation 2
    ps2.executeUpdate();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

The important idea is:

```text
setAutoCommit(false)
        ↓
multiple SQL operations
        ↓
      commit
```

or:

```text
setAutoCommit(false)
        ↓
multiple SQL operations
        ↓
     error
        ↓
    rollback
```

---

# 1.5 Transaction is not the same as SQL statement

This is a very important distinction.

A **SQL statement** might be:

```sql
UPDATE account SET balance = balance - 1000 WHERE id = 1;
```

A **transaction** might contain:

```text
UPDATE A
UPDATE B
INSERT transaction_history
```

Therefore:

```text
Statement ≠ Transaction
```

A transaction can contain multiple statements.

---

# 1.6 Transaction and JDBC Connection

In JDBC, transaction control is primarily associated with the:

```java
Connection
```

For example:

```java
con.setAutoCommit(false);
con.commit();
con.rollback();
```

So remember:

```text
Connection
   │
   ├── setAutoCommit()
   ├── commit()
   ├── rollback()
   └── setSavepoint()
```

---

# 2. Auto-commit

## 2.1 What is auto-commit?

A JDBC `Connection` normally starts with **auto-commit enabled**.

You can check it:

```java
boolean value = con.getAutoCommit();

System.out.println(value);
```

You can explicitly enable it:

```java
con.setAutoCommit(true);
```

or disable it:

```java
con.setAutoCommit(false);
```

---

# 2.2 Auto-commit = true

Conceptually:

```text
SQL statement
     ↓
execute
     ↓
automatically committed
```

For example:

```java
con.setAutoCommit(true);

ps.executeUpdate();
```

The statement is automatically committed according to JDBC/database transaction semantics.

You don't normally need:

```java
con.commit();
```

for each individual statement when auto-commit is enabled.

---

# 2.3 Auto-commit = false

Now the application controls transaction completion:

```java
con.setAutoCommit(false);
```

Then:

```java
ps1.executeUpdate();
ps2.executeUpdate();
ps3.executeUpdate();
```

These operations can belong to the same transaction.

Finally:

```java
con.commit();
```

or:

```java
con.rollback();
```

---

# 2.4 Auto-commit comparison

| Auto-commit | Behavior                                                     |
| ----------- | ------------------------------------------------------------ |
| `true`      | JDBC automatically commits according to statement boundaries |
| `false`     | Application explicitly controls `commit()` / `rollback()`    |

Conceptually:

```text
AUTO-COMMIT TRUE

Operation 1 → commit
Operation 2 → commit
Operation 3 → commit
```

versus:

```text
AUTO-COMMIT FALSE

Operation 1
     ↓
Operation 2
     ↓
Operation 3
     ↓
commit()
```

---

# 2.5 Why disable auto-commit?

Suppose:

```text
Operation A
Operation B
Operation C
```

must all succeed together.

If each operation is automatically committed separately:

```text
A → COMMIT ✓
B → COMMIT ✓
C → FAIL ✗
```

You can end up with a partially completed business operation.

With:

```java
con.setAutoCommit(false);
```

you can do:

```text
A
 ↓
B
 ↓
C
 ↓
commit()
```

If C fails:

```text
rollback()
```

and the transaction's uncommitted changes can be abandoned.

---

# 2.6 Important `setAutoCommit(false)` point

This:

```java
con.setAutoCommit(false);
```

does **not** mean:

> "Start executing SQL."

It means:

> "Disable automatic transaction completion so the application can control the transaction."

---

# 2.7 What happens when `setAutoCommit(true)` is called?

This has an important JDBC behavior.

If auto-commit is disabled and there are pending changes, changing auto-commit back to `true` can cause those pending changes to be committed.

Therefore, don't casually switch:

```java
con.setAutoCommit(false);
...
con.setAutoCommit(true);
```

without understanding the transaction state.

A safe mental model is:

```text
Before changing transaction mode:
        ↓
Know whether there are uncommitted changes.
```

---

# 3. `commit()`

## 3.1 What is `commit()`?

```java
con.commit();
```

successfully completes the current transaction and makes its changes durable according to the database's transaction guarantees.

Example:

```java
con.setAutoCommit(false);

ps1.executeUpdate();
ps2.executeUpdate();

con.commit();
```

Flow:

```text
START
  ↓
Operation 1
  ↓
Operation 2
  ↓
COMMIT
  ↓
Transaction successfully completed
```

---

# 3.2 Why is commit important?

Consider:

```java
con.setAutoCommit(false);

ps1.executeUpdate();
ps2.executeUpdate();
```

If you don't commit, the changes remain uncommitted.

You have not told the database:

> "Successfully finish this transaction."

Therefore:

```java
con.commit();
```

is the success path.

---

# 3.3 What happens after commit?

The current transaction is completed.

You can then start performing further operations, which belong to a subsequent transaction boundary as appropriate.

Conceptually:

```text
Transaction 1
    ↓
commit()
    ↓
Transaction 1 finished

Operation
    ↓
Transaction 2
```

---

# 3.4 Can rollback undo a committed transaction?

Generally, **no**.

Example:

```java
con.commit();

con.rollback();
```

The rollback does not undo the transaction that was already committed.

Think:

```text
Before commit:
    changes can potentially be rolled back

After commit:
    transaction completed
```

---

# 3.5 `commit()` does not mean "execute SQL"

This is another common misunderstanding.

SQL execution happens here:

```java
ps.executeUpdate();
```

Transaction completion happens here:

```java
con.commit();
```

So:

```text
executeUpdate()
    ↓
Execute SQL

commit()
    ↓
Complete transaction
```

---

# 4. `rollback()`

## 4.1 What is rollback?

```java
con.rollback();
```

abandons the uncommitted changes in the current transaction, subject to the database's transaction behavior.

Example:

```java
con.setAutoCommit(false);

try {

    ps1.executeUpdate();
    ps2.executeUpdate();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

---

# 4.2 Why rollback?

Suppose:

```text
Operation 1 → SUCCESS
Operation 2 → SUCCESS
Operation 3 → FAILURE
```

Without rollback:

```text
Operation 1 ✓
Operation 2 ✓
Operation 3 ✗
```

The application may be left with a partial result.

With rollback:

```text
Operation 1 ✓
Operation 2 ✓
Operation 3 ✗
       ↓
   rollback()
       ↓
Uncommitted changes abandoned
```

---

# 4.3 Rollback doesn't mean "reverse every Java operation"

This is important.

Suppose you do:

```java
int x = 10;
x = 20;

con.rollback();
```

Rollback does **not** change:

```text
x = 20
```

back to:

```text
x = 10
```

JDBC rollback applies to the **database transaction**, not arbitrary Java memory operations.

So:

```text
rollback()
    ↓
Database transaction changes
```

not:

```text
rollback()
    ↓
Undo Java variables
```

---

# 4.4 Rollback doesn't undo external side effects automatically

Suppose inside a transaction you:

```text
1. Update database
2. Send an email
3. Update another database row
4. Failure
5. rollback()
```

Rollback can affect the database transaction, but it does not magically unsend an email.

This is why transaction design becomes important in real-world systems.

---

# 4.5 `rollback()` vs `rollback(savepoint)`

There are two important forms:

```java
con.rollback();
```

and:

```java
con.rollback(savepoint);
```

### `rollback()`

Rolls back the current transaction's uncommitted work.

### `rollback(savepoint)`

Rolls back to a particular savepoint.

```text
rollback()
       ↓
transaction-level rollback

rollback(savepoint)
       ↓
partial rollback
```

---

# 5. Savepoint

## 5.1 What is a Savepoint?

A **Savepoint** is a named or unnamed marker inside a transaction.

It allows you to return to that point later.

Example:

```java
con.setAutoCommit(false);

operation1();

Savepoint sp =
    con.setSavepoint();

operation2();
operation3();

con.rollback(sp);
```

---

# 5.2 Why use a Savepoint?

Suppose a transaction contains:

```text
Operation 1
Operation 2
Operation 3
Operation 4
```

You discover that operations 3 and 4 should be abandoned, but operation 1 and 2 should remain part of the transaction.

A complete:

```java
con.rollback();
```

would roll back the whole transaction.

Instead:

```java
con.rollback(sp);
```

can roll back to the savepoint.

---

# 5.3 Savepoint flow

```text
START
  ↓
Operation 1
  ↓
Operation 2
  ↓
SAVEPOINT A
  ↓
Operation 3
  ↓
Operation 4
  ↓
ROLLBACK TO A
  ↓
Operation 3 & 4 abandoned
  ↓
Continue transaction
  ↓
COMMIT
```

---

# 5.4 Creating a Savepoint

```java
Savepoint sp =
    con.setSavepoint();
```

You can also give it a name:

```java
Savepoint sp =
    con.setSavepoint("beforeAddressUpdate");
```

---

# 5.5 Rolling back to a Savepoint

```java
con.rollback(sp);
```

This doesn't necessarily finish the entire transaction.

You can continue:

```java
con.rollback(sp);

operation5();

con.commit();
```

---

# 5.6 Savepoint does not equal commit

This is a major distinction.

```text
Savepoint
    ↓
Marker inside transaction
```

while:

```text
Commit
    ↓
Successfully complete transaction
```

Therefore:

```text
Savepoint ≠ Commit
```

---

# 5.7 Savepoint example

Imagine an order transaction:

```text
Create order
    ↓
Savepoint
    ↓
Add product A
    ↓
Add product B
    ↓
Problem with product B
    ↓
Rollback to Savepoint
    ↓
Continue with another operation
    ↓
Commit
```

The savepoint gives you a **partial recovery point** inside the transaction.

---

# 5.8 Savepoint limitations

Savepoint behavior is subject to the JDBC driver and database capabilities.

Also, savepoints belong to a particular transaction/connection context.

You shouldn't think of a savepoint as something that survives independently of its transaction.

---

# 6. ACID Basics

ACID is the classic model for understanding transaction guarantees:

```text
A → Atomicity
C → Consistency
I → Isolation
D → Durability
```

Let's understand each individually.

---

# 6.1 A — Atomicity

## Meaning

**Atomicity = All or Nothing.**

A transaction is treated as one logical unit.

Example:

```text
Bank Transfer

Debit A       ✓
Credit B      ✗
```

Atomicity aims to prevent the transaction from being left partially applied.

Instead:

```text
Both succeed
     OR
Both are rolled back
```

### Important distinction

Atomicity doesn't mean:

> "Every SQL statement is physically indivisible."

It means:

> "The transaction's changes are treated as one unit of work from the transaction's perspective."

---

# 6.2 C — Consistency

## Meaning

**Consistency means a successful transaction should preserve the database's defined rules and constraints, moving it from one valid state to another valid state.**

Suppose:

```text
id must be unique
```

and:

```sql
PRIMARY KEY (id)
```

A transaction that attempts to create duplicate primary-key values can fail.

Other examples include:

```text
NOT NULL
PRIMARY KEY
FOREIGN KEY
UNIQUE
CHECK
```

These constraints help maintain database consistency.

### Important

Consistency is not simply:

> "The data looks correct."

It means the database's defined integrity rules are maintained after a successful transaction.

---

# 6.3 I — Isolation

## Meaning

**Isolation controls how concurrent transactions interact with one another and what intermediate changes they can see.**

Imagine:

```text
Transaction A
      ↕
Transaction B
      ↕
Transaction C
```

All are executing at approximately the same time.

Without appropriate isolation, concurrent transactions can produce problematic effects.

Examples include:

```text
Dirty Read
Non-repeatable Read
Phantom Read
```

Different isolation levels provide different guarantees.

---

# 6.4 JDBC Isolation Levels

JDBC exposes standard transaction isolation constants such as:

```java
Connection.TRANSACTION_READ_UNCOMMITTED
Connection.TRANSACTION_READ_COMMITTED
Connection.TRANSACTION_REPEATABLE_READ
Connection.TRANSACTION_SERIALIZABLE
```

You can inspect the current isolation level:

```java
int level =
    con.getTransactionIsolation();
```

And request a level:

```java
con.setTransactionIsolation(
    Connection.TRANSACTION_READ_COMMITTED
);
```

However, the exact supported behavior depends on the database and JDBC driver.

---

# 6.5 Dirty Read

Suppose Transaction A changes:

```text
Balance = 10,000
```

but hasn't committed.

Transaction B reads:

```text
Balance = 10,000
```

Then Transaction A rolls back.

The value Transaction B read was never committed.

This is a **dirty read**.

Conceptually:

```text
A: UPDATE
     ↓
uncommitted = 10000
     ↓
B: READ → 10000
     ↓
A: ROLLBACK
```

B read data that ultimately did not become part of the committed database state.

---

# 6.6 Non-repeatable Read

Transaction A reads a row:

```text
marks = 80
```

Then Transaction B changes and commits it:

```text
marks = 90
```

Transaction A reads the same row again and gets:

```text
marks = 90
```

The same row produced different values during the same transaction.

That's a **non-repeatable read**.

---

# 6.7 Phantom Read

Transaction A executes:

```sql
SELECT *
FROM student
WHERE marks >= 80;
```

It gets:

```text
101
102
```

Transaction B inserts another matching row and commits.

Transaction A executes the same query again:

```sql
SELECT *
FROM student
WHERE marks >= 80;
```

Now it gets:

```text
101
102
103
```

The newly appearing row is a **phantom row**.

---

# 6.8 D — Durability

## Meaning

After a successful commit, the database is expected to preserve the committed changes even if a subsequent failure occurs, according to the database's durability guarantees.

Conceptually:

```text
Transaction
    ↓
COMMIT
    ↓
Database records successful transaction
    ↓
System failure
    ↓
Restart
    ↓
Committed changes survive
```

Durability is primarily a property provided by the database system and its storage/recovery mechanisms; JDBC requests the commit but does not itself implement the database's disk-recovery system.

---

# 7. ACID in One Example

Consider:

```text
Bank transfer ₹1,000
```

### Atomicity

```text
Debit + Credit
     ↓
Both or neither
```

### Consistency

```text
Database constraints/rules remain valid
```

### Isolation

```text
Other concurrent transactions
don't improperly observe/interfere with
intermediate transaction state
```

### Durability

```text
After successful COMMIT
     ↓
Committed transfer should survive failures
according to DB guarantees
```

---

# 8. Complete JDBC Transaction Example

Let's combine everything.

```java
import java.sql.*;

public class BankTransfer {

    public static void main(String[] args) {

        Connection con = null;

        try {

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bank",
                "root",
                "password"
            );

            con.setAutoCommit(false);

            String debitSql =
                "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE id = ?";

            String creditSql =
                "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE id = ?";

            try (PreparedStatement debit =
                     con.prepareStatement(debitSql);
                 PreparedStatement credit =
                     con.prepareStatement(creditSql)) {

                debit.setDouble(1, 1000);
                debit.setInt(2, 1);

                int debitRows =
                    debit.executeUpdate();

                if (debitRows != 1) {
                    throw new SQLException(
                        "Debit failed"
                    );
                }

                credit.setDouble(1, 1000);
                credit.setInt(2, 2);

                int creditRows =
                    credit.executeUpdate();

                if (creditRows != 1) {
                    throw new SQLException(
                        "Credit failed"
                    );
                }

                con.commit();

                System.out.println(
                    "Transfer successful"
                );
            }

        } catch (SQLException e) {

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

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
    }
}
```

### Transaction flow

```text
                Connection
                    ↓
          setAutoCommit(false)
                    ↓
             Debit Account
                    ↓
            debit successful?
                 ↙     ↘
               NO       YES
                ↓         ↓
            rollback   Credit Account
                            ↓
                     credit successful?
                        ↙       ↘
                      NO         YES
                       ↓           ↓
                   rollback     commit
```

---

# 9. Transaction vs Auto-commit vs Commit vs Rollback

This is a very important comparison.

| Concept                    | What it does                                                                      |
| -------------------------- | --------------------------------------------------------------------------------- |
| **Transaction**            | Group of related database operations                                              |
| **Auto-commit**            | Controls whether transactions are automatically completed at statement boundaries |
| **`setAutoCommit(false)`** | Lets application control transaction completion                                   |
| **`commit()`**             | Successfully completes current transaction                                        |
| **`rollback()`**           | Abandons current transaction's uncommitted changes                                |
| **Savepoint**              | Marker for partial rollback                                                       |

---

# 10. Complete Transaction Lifecycle

```text
                  CONNECTION
                      │
                      ↓
             Auto-commit = false
                      │
                      ↓
                TRANSACTION
                      │
          ┌───────────┼───────────┐
          ↓           ↓           ↓
       INSERT      UPDATE      DELETE
          │           │           │
          └───────────┼───────────┘
                      ↓
                  Everything
                   successful?
                   /       \
                 YES        NO
                  ↓          ↓
               COMMIT     ROLLBACK
                  ↓          ↓
             Transaction  Transaction
               done         undone
```

---

# 11. Savepoint Lifecycle

```text
START TRANSACTION
       ↓
Operation A
       ↓
Operation B
       ↓
SAVEPOINT S1
       ↓
Operation C
       ↓
Operation D
       ↓
Problem
       ↓
ROLLBACK TO S1
       ↓
A and B remain part of transaction
C and D are rolled back
       ↓
Operation E
       ↓
COMMIT
```

---

# 12. 🔥 Most Important Deep-Dive Doubts

### Doubt 1: Does `executeUpdate()` commit automatically?

**It depends on auto-commit mode.**

With auto-commit enabled, statement execution is normally automatically committed according to JDBC/database transaction semantics.

With:

```java
con.setAutoCommit(false);
```

you need:

```java
con.commit();
```

to successfully complete the transaction.

---

### Doubt 2: Is `commit()` a SQL command?

In JDBC:

```java
con.commit();
```

is a method of the `Connection` interface.

It controls the transaction; it is not a normal SQL statement sent through `Statement`.

---

### Doubt 3: Is rollback the opposite of commit?

Conceptually, yes:

```text
commit   → accept transaction
rollback → abandon uncommitted transaction changes
```

But rollback is not a universal "undo button" for everything your Java program did.

---

### Doubt 4: Can I rollback after commit?

Not to undo the transaction that has already been committed.

```text
commit()
   ↓
transaction completed
   ↓
rollback()
   ↓
cannot undo that completed transaction
```

---

### Doubt 5: Is Savepoint a separate transaction?

**No.**

A savepoint exists **inside a transaction**.

```text
Transaction
   ├── Operation
   ├── Savepoint
   ├── Operation
   └── Operation
```

---

### Doubt 6: Does rollback(savepoint) commit anything?

**No.**

It rolls the transaction back to the savepoint. The transaction can continue afterward.

You still need:

```java
con.commit();
```

when the overall transaction is successfully finished.

---

### Doubt 7: Does rollback undo Java variables?

**No.**

It concerns database transaction changes, not arbitrary Java memory state.

---

### Doubt 8: Does rollback undo an email sent by Java?

**No.**

Database rollback cannot automatically undo external side effects such as an already-sent email.

---

### Doubt 9: Is ACID implemented by JDBC?

Not exactly.

JDBC provides APIs for transaction control and isolation settings, while the **database management system** provides the underlying transaction mechanisms and guarantees.

Think:

```text
Java Application
       ↓
JDBC
       ↓
JDBC Driver
       ↓
Database
       ↓
Transaction engine
```

---

### Doubt 10: Is auto-commit always bad?

**No.**

For simple independent database operations, auto-commit can be perfectly reasonable.

You disable it when multiple operations need to participate in one logical transaction.

---

# 13. Final Deep-Dive Mental Model

```text
                       TRANSACTION
                            │
          ┌─────────────────┼─────────────────┐
          │                 │                 │
          ↓                 ↓                 ↓
      Auto-commit         Commit           Rollback
          │                 │                 │
          │                 │                 │
     true / false       Success path       Failure path
          │
          ↓
 setAutoCommit(false)
          │
          ↓
   Multiple operations
          │
          ├───────────────┐
          ↓               ↓
      Savepoint       ACID
          │               │
          │        ┌──────┼──────┐
          │        ↓      ↓      ↓
          │     Atomic  Consistent Isolation
          │                         │
          │                         ↓
          │                    Concurrent work
          │
          ↓
   Partial rollback
```

## The deepest memory rule

> **A transaction groups related database operations. `setAutoCommit(false)` gives you control. `commit()` is the success path. `rollback()` is the failure path. A savepoint provides a partial rollback point. ACID describes the fundamental guarantees expected from transaction processing.**

```text
setAutoCommit(false)
        ↓
   SQL operations
        ↓
   ┌────┴────┐
   ↓         ↓
SUCCESS    FAILURE
   ↓         ↓
commit()  rollback()
```

That is the complete JDBC transaction foundation.
