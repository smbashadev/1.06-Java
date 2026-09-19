# 12. Transactions in Java — 3LEVEL

We will learn every concept at **3 levels**:

* 🟢 **Level 1 — Beginner:** What is it?
* 🟡 **Level 2 — Intermediate:** How does it work in JDBC?
* 🔴 **Level 3 — Advanced:** Important rules, behavior, and interview-level understanding.

---

# 1. Transaction

## 🟢 Level 1 — Beginner

A **transaction** is a group of related database operations treated as **one unit of work**.

Example: transferring ₹1,000:

```text
Account A → -₹1,000
Account B → +₹1,000
```

Both operations belong to one transaction.

The goal is:

```text
Both succeed
     OR
Both are cancelled
```

### Simple definition

> A transaction is a logical unit of one or more database operations that should be completed together.

---

## 🟡 Level 2 — JDBC

JDBC controls transactions through the `Connection`.

Important methods:

```java
con.setAutoCommit(false);

con.commit();

con.rollback();
```

Typical structure:

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
START
  ↓
Operation 1
  ↓
Operation 2
  ↓
Success?
 /    \
YES    NO
 ↓      ↓
COMMIT ROLLBACK
```

---

## 🔴 Level 3 — Advanced

A transaction is **not the same thing as a SQL statement**.

For example:

```text
Transaction
│
├── INSERT
├── UPDATE
├── UPDATE
└── DELETE
```

A transaction may contain many SQL statements.

The transaction boundary determines which database changes are committed or rolled back together.

Also remember:

> JDBC provides transaction-control APIs, but the database and JDBC driver implement the actual transaction behavior.

---

# 2. Auto-commit

## 🟢 Level 1 — Beginner

JDBC connections normally start with:

```java
autoCommit = true
```

You can explicitly set it:

```java
con.setAutoCommit(true);
```

or:

```java
con.setAutoCommit(false);
```

### Auto-commit ON

Conceptually:

```text
SQL operation
      ↓
Execute
      ↓
Automatically commit
```

---

## 🟡 Level 2 — JDBC

If you want to manually control a transaction:

```java
con.setAutoCommit(false);
```

Then:

```java
statement1.executeUpdate();
statement2.executeUpdate();
statement3.executeUpdate();

con.commit();
```

If something goes wrong:

```java
con.rollback();
```

So:

```text
setAutoCommit(false)
        ↓
Manual transaction control
        ↓
       COMMIT
        OR
      ROLLBACK
```

---

## 🔴 Level 3 — Advanced

`setAutoCommit(false)` does **not** execute SQL.

It changes the transaction mode of the connection.

Compare:

```java
ps.executeUpdate();
```

→ executes SQL.

Whereas:

```java
con.setAutoCommit(false);
```

→ changes transaction behavior.

### Important

When auto-commit is enabled, JDBC normally commits a statement when it completes, according to the JDBC/database semantics.

When auto-commit is disabled, the application controls transaction completion.

---

# 3. `commit()`

## 🟢 Level 1 — Beginner

`commit()` means:

> "Everything succeeded. Permanently complete this transaction."

Example:

```java
con.commit();
```

---

## 🟡 Level 2 — JDBC

Example:

```java
con.setAutoCommit(false);

ps1.executeUpdate();
ps2.executeUpdate();

con.commit();
```

Flow:

```text
Operation 1
    ↓
Operation 2
    ↓
commit()
    ↓
Transaction completed
```

---

## 🔴 Level 3 — Advanced

`commit()` is a **transaction boundary operation**.

It does not mean:

```text
"execute SQL"
```

Instead:

```text
executeUpdate()
      ↓
Execute SQL

commit()
      ↓
Complete current transaction
```

After a successful commit, those transaction changes are no longer uncommitted changes that can simply be undone using `rollback()`.

The database's durability mechanisms are responsible for preserving successfully committed data.

---

# 4. `rollback()`

## 🟢 Level 1 — Beginner

`rollback()` means:

> "Something went wrong. Abandon the current transaction's uncommitted database changes."

Example:

```java
con.rollback();
```

---

## 🟡 Level 2 — JDBC

Typical code:

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
Uncommitted transaction changes abandoned
```

---

## 🔴 Level 3 — Advanced

`rollback()` does **not** undo arbitrary Java operations.

For example:

```java
int x = 10;

x = 20;

con.rollback();
```

`rollback()` does not change:

```java
x
```

back to `10`.

It deals with the **database transaction**, not Java memory.

Also:

```text
COMMITTED
    ↓
Normally cannot be undone with rollback()
```

So:

```text
Before commit → rollback possible
After commit  → rollback cannot undo that completed transaction
```

---

# 5. Savepoint

## 🟢 Level 1 — Beginner

A **Savepoint** is a marker inside a transaction.

Example:

```java
Savepoint sp = con.setSavepoint();
```

You can later do:

```java
con.rollback(sp);
```

Meaning:

> "Go back to this point in the transaction."

---

## 🟡 Level 2 — JDBC

Example:

```java
con.setAutoCommit(false);

operation1();
operation2();

Savepoint sp = con.setSavepoint();

operation3();
operation4();

con.rollback(sp);

operation5();

con.commit();
```

Flow:

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
ROLLBACK TO SAVEPOINT
     ↓
Operation 5
     ↓
COMMIT
```

The idea is that operations after the savepoint can be rolled back while the transaction can continue.

---

## 🔴 Level 3 — Advanced

A savepoint is **not a backup**.

It is a marker within the current database transaction.

You can create one:

```java
Savepoint sp =
    con.setSavepoint("BeforeUpdate");
```

Then:

```java
con.rollback(sp);
```

This is different from:

```java
con.rollback();
```

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
Abandon current transaction's uncommitted changes
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

### Critical distinction

```text
Savepoint ≠ Commit
```

A savepoint doesn't permanently save the transaction.

---

# 6. ACID Basics

ACID represents four important transaction properties:

```text
A → Atomicity
C → Consistency
I → Isolation
D → Durability
```

---

## 🟢 Level 1 — Beginner

### A — Atomicity

**All or nothing.**

```text
Operation A ✓
Operation B ✓
Operation C ✗
       ↓
   ROLLBACK
```

The transaction should not simply remain half-completed.

Memory:

```text
A = All or Nothing
```

---

### C — Consistency

The database should remain in a **valid state** after a successful transaction.

Examples of database rules:

```text
PRIMARY KEY
FOREIGN KEY
UNIQUE
NOT NULL
CHECK
```

Memory:

```text
C = Correct / Consistent state
```

---

### I — Isolation

Multiple transactions can execute concurrently, and isolation controls how their changes interact and what they can observe.

Memory:

```text
I = Independent interaction
```

---

### D — Durability

Once a transaction has successfully committed, its changes should survive later failures according to the database's durability guarantees.

Memory:

```text
D = Data survives successful commit
```

---

# 🟡 Level 2 — Intermediate

## Atomicity

Consider:

```text
Bank Transfer

Debit A
Credit B
```

Without proper transaction handling:

```text
Debit A ✓
Credit B ✗
```

With transaction control:

```text
Debit A ✓
Credit B ✗
      ↓
ROLLBACK
```

---

## Consistency

Suppose:

```text
Account balance cannot be negative.
```

The database may enforce such business/data rules through constraints or application logic.

A successful transaction should leave the database in a state that satisfies its applicable integrity rules.

---

## Isolation

Consider two transactions:

```text
Transaction A
      ↕
Transaction B
```

Poorly controlled concurrency can lead to problems such as:

```text
Dirty Read
Non-repeatable Read
Phantom Read
```

---

## Durability

After:

```java
con.commit();
```

the successfully committed transaction is intended to survive system/database failures according to the database's recovery mechanisms.

---

# 🔴 Level 3 — Advanced

## Atomicity

Atomicity concerns the transaction as a whole:

```text
T = Operation 1 + Operation 2 + Operation 3
```

The transaction is treated as one logical unit.

---

## Consistency

Consistency means successful transactions preserve database integrity rules and move the database between valid states.

It does **not** simply mean:

> "The data never changes."

Data is expected to change.

The important point is:

```text
Valid State
    ↓
Transaction
    ↓
Valid State
```

---

## Isolation

JDBC defines standard transaction isolation levels:

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

The exact behavior and supported levels depend on the database and driver.

Common phenomena:

### Dirty read

Reading data written by another transaction before that transaction commits.

```text
T1 → UPDATE
       ↓
   uncommitted
       ↓
T2 → READ
       ↓
T1 → ROLLBACK
```

T2 read data that did not ultimately become committed.

---

### Non-repeatable read

A transaction reads the same row twice and gets different committed values because another transaction changed it between the reads.

```text
T1 → READ → 80

T2 → UPDATE → 90
T2 → COMMIT

T1 → READ → 90
```

---

### Phantom read

A repeated query returns a different set of matching rows because another transaction inserted/deleted rows that satisfy the query condition.

```text
T1 → SELECT → 2 rows

T2 → INSERT matching row
T2 → COMMIT

T1 → SELECT → 3 rows
```

---

## Durability

Durability is primarily a property of the database's transaction and recovery system.

JDBC:

```text
con.commit()
```

requests successful transaction completion.

The database is responsible for ensuring the committed transaction is persisted according to its durability guarantees.

---

# 7. Complete 3-Level Comparison

| Concept         | 🟢 Level 1                              | 🟡 Level 2                                  | 🔴 Level 3                                                                    |
| --------------- | --------------------------------------- | ------------------------------------------- | ----------------------------------------------------------------------------- |
| **Transaction** | Group of related operations             | Controlled through `Connection`             | Defines transaction boundary                                                  |
| **Auto-commit** | Automatically completes transactions    | `setAutoCommit(false)` gives manual control | Connection transaction mode                                                   |
| **commit()**    | Confirm success                         | Completes current transaction               | Transaction boundary; committed changes get DB durability guarantees          |
| **rollback()**  | Cancel failed work                      | Abandons uncommitted changes                | Database transaction operation, not Java undo                                 |
| **Savepoint**   | Marker in transaction                   | `rollback(savepoint)`                       | Partial rollback point inside transaction                                     |
| **Atomicity**   | All or nothing                          | Avoids partial transaction results          | Transaction treated as one logical unit                                       |
| **Consistency** | Valid data                              | Database rules remain satisfied             | Valid state → transaction → valid state                                       |
| **Isolation**   | Transactions don't improperly interfere | Controls concurrent transaction behavior    | Isolation levels address phenomena such as dirty/non-repeatable/phantom reads |
| **Durability**  | Committed data survives                 | Database preserves committed changes        | Database recovery/storage mechanisms provide durability                       |

---

# 8. MASTER 3-LEVEL MEMORY MAP

```text
                         TRANSACTION
                              │
                  ┌───────────┴───────────┐
                  ↓                       ↓
             AUTO-COMMIT              MANUAL MODE
                  │                       │
                true                     false
                                          │
                                          ↓
                                  SQL Operations
                                          │
                              ┌───────────┴───────────┐
                              ↓                       ↓
                           SUCCESS                  FAILURE
                              ↓                       ↓
                           COMMIT                  ROLLBACK
                              │
                              ↓
                         Transaction
                          completed
                              │
                              ↓
                         SAVEPOINT
                              │
                              ↓
                    Partial rollback point
                              │
                              ↓
                            ACID
                              │
            ┌─────────────────┼─────────────────┐
            ↓                 ↓                 ↓
            A                 C                 I                 D
            ↓                 ↓                 ↓                 ↓
       Atomicity         Consistency        Isolation         Durability
       All/Nothing       Valid State        Concurrency       Survives
```

## ⭐ Final exam/interview memory

```text
Transaction
   ↓
Group of related SQL operations

setAutoCommit(false)
   ↓
Take manual transaction control

commit()
   ↓
SUCCESS

rollback()
   ↓
FAILURE

Savepoint
   ↓
Partial rollback point

ACID
   ↓
A = Atomicity
C = Consistency
I = Isolation
D = Durability
```

**One-line master rule:**

> **Disable auto-commit when you need multiple operations to behave as one transaction; `commit()` on success, `rollback()` on failure, use a Savepoint for partial rollback, and remember ACID as All-or-Nothing, Valid State, Controlled Concurrency, and Durable Committed Data.**
