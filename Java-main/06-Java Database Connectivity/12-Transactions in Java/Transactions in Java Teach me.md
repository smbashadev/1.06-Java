# 12. Transactions in Java — TEACHME

Let's learn **JDBC Transactions** from the ground up, as if this is your first time seeing the concept.

The goal is not just to memorize `commit()` and `rollback()`, but to understand **why transactions exist, how JDBC controls them, and when each concept is used**.

---

# 1. Transaction

## 1.1 First understand the problem

Imagine you are transferring ₹1,000 from one bank account to another.

There are two database operations:

```text
Account A → subtract ₹1,000
Account B → add ₹1,000
```

In SQL:

```sql
UPDATE account
SET balance = balance - 1000
WHERE id = 1;
```

Then:

```sql
UPDATE account
SET balance = balance + 1000
WHERE id = 2;
```

Now imagine this happens:

```text
Step 1 → SUCCESS
Step 2 → FAILURE
```

Then:

```text
Account A → ₹1,000 deducted
Account B → ₹1,000 NOT added
```

That's a serious problem.

We therefore want:

```text
Both operations succeed
        OR
Neither operation succeeds
```

This is where a **transaction** comes in.

---

## 1.2 Definition

A **transaction** is a group of one or more related database operations that are treated as **one logical unit of work**.

For example:

```text
Transaction
│
├── Debit Account A
└── Credit Account B
```

The entire transfer is considered one transaction.

---

## 1.3 Simple real-life example

Think about buying something online.

```text
1. Deduct money from customer
2. Create order
3. Reduce product stock
```

These operations are related.

You don't want:

```text
Money deducted ✓
Order created ✓
Stock update failed ✗
```

The application may need to treat them as one transaction.

Conceptually:

```text
              TRANSACTION
                   │
        ┌──────────┼──────────┐
        ↓          ↓          ↓
      Money      Order      Stock
      Deduct     Create     Update
        │          │          │
        └──────────┼──────────┘
                   ↓
              All successful?
                /       \
              YES        NO
               ↓          ↓
            COMMIT     ROLLBACK
```

---

# 1.4 Transaction vs SQL statement

Don't confuse these two.

A SQL statement might be:

```sql
UPDATE account
SET balance = balance - 1000
WHERE id = 1;
```

A transaction can contain several SQL statements:

```text
Transaction
│
├── UPDATE
├── UPDATE
├── INSERT
└── UPDATE
```

Therefore:

> **A statement is one database operation; a transaction can contain multiple operations.**

---

# 1.5 Transaction in JDBC

Transactions are controlled primarily through the JDBC `Connection`.

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

# 1.6 Basic transaction structure

The normal pattern is:

```java
con.setAutoCommit(false);

try {

    // SQL operation 1
    // SQL operation 2
    // SQL operation 3

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

Read it as:

> "Don't automatically finish each operation. I'll decide whether the whole group succeeds or fails."

---

# 2. Auto-commit

Now we need to understand something called **auto-commit**.

---

## 2.1 What is auto-commit?

A JDBC `Connection` normally starts with auto-commit enabled.

You can check it:

```java
boolean value = con.getAutoCommit();

System.out.println(value);
```

You can explicitly enable it:

```java
con.setAutoCommit(true);
```

Or disable it:

```java
con.setAutoCommit(false);
```

---

# 2.2 Auto-commit ON

Suppose:

```java
con.setAutoCommit(true);
```

Then you execute:

```java
ps.executeUpdate();
```

The operation is automatically committed according to JDBC/database transaction semantics.

Conceptually:

```text
SQL operation
     ↓
Execute
     ↓
Automatically commit
```

So you generally don't need to manually write:

```java
con.commit();
```

for every individual operation.

---

# 2.3 Auto-commit OFF

Now:

```java
con.setAutoCommit(false);
```

means:

> "Don't automatically complete each transaction at the statement boundary. I will control when the transaction is committed or rolled back."

Then:

```java
ps1.executeUpdate();
ps2.executeUpdate();
ps3.executeUpdate();
```

can be treated as part of one transaction.

Finally:

```java
con.commit();
```

or:

```java
con.rollback();
```

---

# 2.4 Easy comparison

### Auto-commit ON

```text
Operation 1
    ↓
COMMIT

Operation 2
    ↓
COMMIT

Operation 3
    ↓
COMMIT
```

### Auto-commit OFF

```text
Operation 1
    ↓
Operation 2
    ↓
Operation 3
    ↓
COMMIT
```

Or, if something fails:

```text
Operation 1
    ↓
Operation 2
    ↓
ERROR
    ↓
ROLLBACK
```

---

# 2.5 Why disable auto-commit?

Suppose:

```text
Operation 1 → Success
Operation 2 → Success
Operation 3 → Failure
```

If each operation was automatically committed:

```text
Operation 1 → COMMIT ✓
Operation 2 → COMMIT ✓
Operation 3 → FAIL ✗
```

You have a partial result.

Instead:

```java
con.setAutoCommit(false);
```

allows:

```text
Operation 1
     ↓
Operation 2
     ↓
Operation 3
     ↓
    FAIL
     ↓
 rollback()
```

The uncommitted transaction changes can then be abandoned.

---

# 2.6 Important point

This:

```java
con.setAutoCommit(false);
```

does **not** execute SQL.

It changes the **transaction mode of the connection**.

Remember:

```text
executeUpdate()
     ↓
Executes SQL

setAutoCommit()
     ↓
Controls transaction mode
```

---

# 3. `commit()`

Now suppose all our operations succeeded.

What do we do?

We call:

```java
con.commit();
```

---

## 3.1 What does `commit()` mean?

`commit()` tells the database:

> "The current transaction has successfully completed; accept its changes."

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
commit()
  ↓
Transaction successfully completed
```

---

# 3.2 Why do we need commit?

Consider:

```java
con.setAutoCommit(false);

ps1.executeUpdate();
ps2.executeUpdate();
```

We haven't said:

```java
con.commit();
```

yet.

So we haven't explicitly completed the transaction.

The normal success path is:

```java
con.commit();
```

---

# 3.3 `commit()` is not SQL execution

This is a common beginner confusion.

Consider:

```java
ps.executeUpdate();
```

This means:

> Execute the SQL.

But:

```java
con.commit();
```

means:

> Complete the current transaction successfully.

So:

```text
executeUpdate()
       ↓
SQL execution

commit()
       ↓
Transaction completion
```

---

# 3.4 Can we rollback after commit?

No—not to undo the transaction that was already committed.

Example:

```java
con.commit();

con.rollback();
```

The rollback does not undo the already completed transaction.

Think:

```text
BEFORE COMMIT
      ↓
uncommitted changes
      ↓
rollback possible

COMMIT
      ↓
transaction completed
```

---

# 4. `rollback()`

Now let's learn the failure path.

---

## 4.1 What is rollback?

```java
con.rollback();
```

abandons the current transaction's uncommitted database changes, subject to the database's transaction behavior.

Suppose:

```text
Operation 1 → SUCCESS
Operation 2 → SUCCESS
Operation 3 → FAILURE
```

We can do:

```java
con.rollback();
```

Conceptually:

```text
Operation 1 ✓
Operation 2 ✓
Operation 3 ✗
       ↓
   ROLLBACK
       ↓
Abandon uncommitted changes
```

---

# 4.2 Real-life example

Bank transfer:

```text
A → -₹1,000
B → +₹1,000
```

Suppose:

```text
A deduction → SUCCESS
B credit → FAILURE
```

Then:

```java
con.rollback();
```

The transaction's uncommitted changes are abandoned.

Conceptually:

```text
A → original balance
B → original balance
```

---

# 4.3 Standard JDBC pattern

This is extremely important:

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

Read it like this:

```text
TRY
 ↓
Perform all operations
 ↓
Everything successful?
 ↓
COMMIT

If exception:
 ↓
ROLLBACK
```

---

# 4.4 Rollback does not undo Java code

This is very important.

Suppose:

```java
int x = 10;

x = 20;

con.rollback();
```

Does:

```text
x = 20
```

become:

```text
x = 10
```

?

**No.**

`rollback()` concerns the database transaction.

It doesn't undo arbitrary Java memory operations.

```text
rollback()
    ↓
Database transaction changes
```

not:

```text
rollback()
    ↓
Undo Java program execution
```

---

# 4.5 Rollback does not undo everything outside the database

Suppose your program does:

```text
1. Update database
2. Send email
3. Update database
4. Failure
5. rollback()
```

The rollback can affect the database transaction, but it cannot magically unsend an email that was already delivered.

This distinction becomes very important in real applications.

---

# 5. Savepoint

Now let's learn a slightly more advanced transaction concept.

---

## 5.1 What is a Savepoint?

A **Savepoint** is a marker created inside a transaction.

It allows you to roll back to that particular point instead of necessarily rolling back the entire transaction.

Example:

```java
Savepoint sp = con.setSavepoint();
```

Then:

```java
con.rollback(sp);
```

---

# 5.2 Why do we need a Savepoint?

Imagine:

```text
Transaction
│
├── Operation 1
├── Operation 2
├── Operation 3
└── Operation 4
```

Suppose Operation 3 and 4 fail.

But you want to keep Operations 1 and 2 as part of the transaction.

A complete:

```java
con.rollback();
```

would roll back the entire transaction.

Instead:

```java
Savepoint sp = con.setSavepoint();
```

can create a point from which you can partially roll back.

---

# 5.3 Savepoint flow

```text
START TRANSACTION
       ↓
Operation 1
       ↓
Operation 2
       ↓
SAVEPOINT
       ↓
Operation 3
       ↓
Operation 4
       ↓
Problem
       ↓
ROLLBACK TO SAVEPOINT
       ↓
Operation 3 & 4 abandoned
       ↓
Continue
       ↓
COMMIT
```

---

# 5.4 Creating a Savepoint

First:

```java
Savepoint sp = con.setSavepoint();
```

You can also name it:

```java
Savepoint sp =
    con.setSavepoint("beforeProductUpdate");
```

---

# 5.5 Rollback to Savepoint

```java
con.rollback(sp);
```

This means:

> "Return the transaction to the state represented by this savepoint."

It does **not** necessarily mean the whole transaction is finished.

You can continue:

```java
con.rollback(sp);

operation5();

con.commit();
```

---

# 5.6 Savepoint vs rollback

### Complete rollback

```java
con.rollback();
```

Conceptually:

```text
START
 ↓
A
 ↓
B
 ↓
C
 ↓
ROLLBACK
 ↓
Undo current transaction changes
```

### Rollback to savepoint

```java
con.rollback(sp);
```

Conceptually:

```text
START
 ↓
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
C and D abandoned
 ↓
A and B remain part of transaction
```

---

# 5.7 Savepoint is NOT commit

This is one of the most important things to remember.

```text
Savepoint
    ↓
Marker inside a transaction
```

Whereas:

```text
commit()
    ↓
Successfully complete transaction
```

Therefore:

```text
SAVEPOINT ≠ COMMIT
```

A savepoint doesn't permanently save the transaction.

---

# 6. ACID Basics

Now let's learn the four famous properties of transactions.

```text
A → Atomicity
C → Consistency
I → Isolation
D → Durability
```

These four letters are extremely important for interviews and exams.

---

# 6.1 A — Atomicity

## Easy meaning

**All or Nothing.**

A transaction should be treated as one logical unit.

Example:

```text
Bank Transfer

Debit A  ✓
Credit B ✗
```

The transaction should not simply remain half completed.

Conceptually:

```text
Both succeed
     OR
Neither transaction change remains
```

That's **Atomicity**.

---

## Easy memory trick

```text
A = All or Nothing
```

---

# 6.2 C — Consistency

## Easy meaning

The database should move from one valid state to another valid state after a successful transaction, respecting its rules and constraints.

Suppose:

```text
Student ID must be unique.
```

The database has:

```sql
PRIMARY KEY
```

Trying to insert a duplicate ID can violate a database constraint and cause the operation/transaction to fail.

Other examples:

```text
PRIMARY KEY
FOREIGN KEY
UNIQUE
NOT NULL
CHECK
```

These help maintain data integrity.

### Memory trick

```text
C = Correct / Consistent database state
```

---

# 6.3 I — Isolation

This one is slightly harder.

Imagine two transactions are executing at the same time:

```text
Transaction A
       ↕
Transaction B
```

Isolation controls how their operations interact and what changes they can see while they are running.

Without suitable isolation, concurrent transactions can encounter problems such as:

```text
Dirty Read
Non-repeatable Read
Phantom Read
```

---

# 6.4 Dirty Read

Imagine Transaction A changes:

```text
Balance = ₹10,000
```

but hasn't committed.

Transaction B reads:

```text
₹10,000
```

Then Transaction A rolls back.

So B read a value that never became committed.

```text
A → UPDATE
     ↓
uncommitted ₹10,000
     ↓
B → READ ₹10,000
     ↓
A → ROLLBACK
```

This is a **dirty read**.

---

# 6.5 Non-repeatable Read

Transaction A reads:

```text
Marks = 80
```

Transaction B changes it:

```text
Marks = 90
```

and commits.

Transaction A reads the same row again:

```text
Marks = 90
```

First read:

```text
80
```

Second read:

```text
90
```

The same row changed during Transaction A.

That's a **non-repeatable read**.

---

# 6.6 Phantom Read

Suppose Transaction A runs:

```sql
SELECT *
FROM student
WHERE marks >= 80;
```

It gets:

```text
Student 101
Student 102
```

Transaction B inserts another student with marks ≥ 80 and commits.

Transaction A runs the same query again:

```sql
SELECT *
FROM student
WHERE marks >= 80;
```

Now:

```text
Student 101
Student 102
Student 103
```

A new matching row has appeared.

That's a **phantom read**.

---

# 6.7 JDBC Isolation Levels

JDBC provides standard constants:

```java
Connection.TRANSACTION_READ_UNCOMMITTED
Connection.TRANSACTION_READ_COMMITTED
Connection.TRANSACTION_REPEATABLE_READ
Connection.TRANSACTION_SERIALIZABLE
```

For example:

```java
con.setTransactionIsolation(
    Connection.TRANSACTION_READ_COMMITTED
);
```

The actual behavior and support depend on the database and JDBC driver.

For now, remember:

```text
Isolation level
     ↓
Controls concurrency behavior
```

---

# 6.8 D — Durability

Once a transaction has successfully committed, its changes are intended to survive later failures according to the database's durability guarantees.

Example:

```text
Transaction
    ↓
commit()
    ↓
Database records transaction
    ↓
System crashes
    ↓
Database restarts
    ↓
Committed data should remain
```

That's **Durability**.

The database's storage and recovery mechanisms provide this guarantee; JDBC itself doesn't physically store the committed data.

---

# 6.9 ACID memory trick

Remember:

```text
A → All or Nothing
C → Consistent State
I → Isolation from concurrent transactions
D → Data survives successful commit
```

Or simply:

```text
ACID
│
├── A → Atomicity
├── C → Consistency
├── I → Isolation
└── D → Durability
```

---

# 7. Complete Example — Bank Transfer

Let's put everything together.

Suppose:

```text
Account 1 → Send ₹1,000
Account 2 → Receive ₹1,000
```

Java:

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

            // Disable auto-commit
            con.setAutoCommit(false);

            String debitSQL =
                "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE id = ?";

            String creditSQL =
                "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE id = ?";

            try (PreparedStatement debit =
                     con.prepareStatement(debitSQL);
                 PreparedStatement credit =
                     con.prepareStatement(creditSQL)) {

                // Debit
                debit.setDouble(1, 1000);
                debit.setInt(2, 1);

                int debitRows =
                    debit.executeUpdate();

                if (debitRows != 1) {
                    throw new SQLException(
                        "Debit failed"
                    );
                }

                // Credit
                credit.setDouble(1, 1000);
                credit.setInt(2, 2);

                int creditRows =
                    credit.executeUpdate();

                if (creditRows != 1) {
                    throw new SQLException(
                        "Credit failed"
                    );
                }

                // Success
                con.commit();

                System.out.println(
                    "Transfer successful"
                );
            }

        } catch (SQLException e) {

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
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

---

# 8. Understand the program like a teacher

Look only at these lines:

```java
con.setAutoCommit(false);
```

Means:

> "I want manual transaction control."

Then:

```java
debit.executeUpdate();
```

Means:

> "Execute debit SQL."

Then:

```java
credit.executeUpdate();
```

Means:

> "Execute credit SQL."

Then:

```java
con.commit();
```

Means:

> "Both operations succeeded. Complete the transaction."

If something fails:

```java
con.rollback();
```

Means:

> "The transaction failed. Abandon its uncommitted database changes."

So the complete idea is:

```text
setAutoCommit(false)
        ↓
     Debit
        ↓
     Credit
        ↓
   ┌────┴────┐
   ↓         ↓
SUCCESS    FAILURE
   ↓         ↓
commit()  rollback()
```

---

# 9. Savepoint Example

Let's see a simple example.

```java
con.setAutoCommit(false);

statement.executeUpdate(
    "INSERT INTO student VALUES (1, 'A')"
);

Savepoint sp = con.setSavepoint();

statement.executeUpdate(
    "INSERT INTO student VALUES (2, 'B')"
);

statement.executeUpdate(
    "INSERT INTO student VALUES (3, 'C')"
);

// Something went wrong with later operations
con.rollback(sp);

// Continue transaction
statement.executeUpdate(
    "INSERT INTO student VALUES (4, 'D')"
);

con.commit();
```

Conceptually:

```text
INSERT A
   ↓
SAVEPOINT
   ↓
INSERT B
   ↓
INSERT C
   ↓
ROLLBACK TO SAVEPOINT
   ↓
B and C abandoned
   ↓
INSERT D
   ↓
COMMIT
```

---

# 10. Transaction Lifecycle — Learn This

This is the most useful mental picture:

```text
                CONNECTION
                    │
                    ↓
          setAutoCommit(false)
                    │
                    ↓
              TRANSACTION
                    │
       ┌────────────┼────────────┐
       ↓            ↓            ↓
    INSERT        UPDATE       DELETE
       │            │            │
       └────────────┼────────────┘
                    ↓
               Everything OK?
                 /       \
               YES        NO
                ↓          ↓
             commit()   rollback()
                ↓          ↓
             SUCCESS     ABORT
```

---

# 11. Transaction vs Savepoint

This is a common interview question.

| Transaction                          | Savepoint                                |
| ------------------------------------ | ---------------------------------------- |
| Represents a unit of database work   | Represents a marker inside a transaction |
| Can be committed                     | Cannot itself be committed               |
| Can be rolled back                   | Can be rolled back to                    |
| Usually contains multiple operations | Exists within a transaction              |
| `commit()` / `rollback()`            | `setSavepoint()` / `rollback(savepoint)` |

Remember:

```text
Transaction
   │
   ├── Operation
   ├── Operation
   ├── Savepoint
   ├── Operation
   └── Operation
```

---

# 12. Transaction vs Auto-commit

| Transaction                                                     | Auto-commit                                                           |
| --------------------------------------------------------------- | --------------------------------------------------------------------- |
| Logical group of database operations                            | Connection transaction mode                                           |
| Represents work                                                 | Controls automatic completion                                         |
| Can contain multiple SQL operations                             | Determines whether each statement boundary is automatically committed |
| Uses `commit()` / `rollback()` for manual control when disabled | Controlled using `setAutoCommit()`                                    |

---

# 13. `commit()` vs `rollback()`

| `commit()`                                                 | `rollback()`                             |
| ---------------------------------------------------------- | ---------------------------------------- |
| Success path                                               | Failure/recovery path                    |
| Completes current transaction                              | Abandons uncommitted transaction changes |
| Makes committed changes durable according to DB guarantees | Reverts uncommitted database changes     |
| Transaction is completed                                   | Transaction is rolled back               |
| Used when everything succeeds                              | Used when something fails                |

Easy memory:

```text
SUCCESS → COMMIT
FAILURE → ROLLBACK
```

---

# 14. `rollback()` vs `rollback(savepoint)`

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

### Partial rollback

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
C & D abandoned
↓
Continue
```

---

# 15. The Most Important Beginner Doubts

### ❓ Does `executeUpdate()` automatically commit?

**Depends on auto-commit mode.**

With auto-commit enabled, the statement is normally committed automatically according to JDBC/database semantics.

With:

```java
con.setAutoCommit(false);
```

you control completion with:

```java
con.commit();
```

or:

```java
con.rollback();
```

---

### ❓ Does `commit()` execute SQL?

No.

```text
executeUpdate() → execute SQL
commit()        → complete transaction
```

---

### ❓ Does rollback undo Java code?

No.

```text
rollback()
    ↓
Database transaction
```

It doesn't undo:

```java
x++;
```

or:

```java
System.out.println();
```

---

### ❓ Is a Savepoint a backup?

Not exactly.

A savepoint is a **transactional rollback marker**, not a database backup.

```text
Savepoint → partial transaction recovery point
Backup    → database/data recovery mechanism
```

---

### ❓ Does Savepoint permanently save data?

No.

```text
SAVEPOINT
    ↓
temporary marker inside transaction
```

Only successful transaction completion through `commit()` completes the transaction.

---

### ❓ Can I continue after `rollback(savepoint)`?

Yes, normally.

For example:

```java
con.rollback(sp);

operation4();

con.commit();
```

---

### ❓ Is ACID a JDBC API?

No.

ACID describes fundamental transaction properties.

JDBC provides APIs for transaction control, while the database/driver combination implements the underlying transaction behavior.

---

# 16. Final Story — Understand Transactions Forever

Imagine a restaurant order.

```text
Customer places order
       ↓
Transaction starts
       ↓
Insert Order
       ↓
Update Inventory
       ↓
Insert Payment
       ↓
Everything successful?
```

If YES:

```text
       ↓
    COMMIT
       ↓
Order completed
```

If NO:

```text
       ↓
   ROLLBACK
       ↓
Uncommitted database changes abandoned
```

If you need a recovery point in the middle:

```text
Operation 1
    ↓
Operation 2
    ↓
SAVEPOINT
    ↓
Operation 3
    ↓
Operation 4
    ↓
Problem
    ↓
ROLLBACK TO SAVEPOINT
    ↓
Continue
    ↓
COMMIT
```

And ACID tells us what good transaction processing aims to provide:

```text
A → Atomicity   → All or Nothing
C → Consistency → Valid State → Valid State
I → Isolation   → Safe Concurrent Interaction
D → Durability  → Committed Changes Persist
```

---

# 🧠 MASTER MEMORY MAP

```text
                    TRANSACTIONS
                         │
        ┌────────────────┼────────────────┐
        │                │                │
        ↓                ↓                ↓
    AUTO-COMMIT       COMMIT          ROLLBACK
        │                │                │
        ↓                ↓                ↓
 setAutoCommit()      SUCCESS          FAILURE
        │
        ↓
 false
        │
        ↓
 Multiple SQL operations
        │
        ↓
   ┌────┴────┐
   ↓         ↓
 SUCCESS    ERROR
   ↓         ↓
 COMMIT    ROLLBACK
   │
   │
   └──────────────┐
                  ↓
              SAVEPOINT
                  │
                  ↓
          Partial rollback
                  │
                  ↓
                ACID
                  │
        ┌─────────┼─────────┐
        ↓         ↓         ↓
       A          C         I          D
       │          │         │          │
    Atomic     Consistent Isolation Durability
```

## ⭐ One sentence to remember

> **A JDBC transaction groups related database operations; disable auto-commit when you need manual control, call `commit()` when the complete unit succeeds, call `rollback()` when it fails, use a Savepoint for partial rollback, and remember ACID as Atomicity, Consistency, Isolation, and Durability.**
