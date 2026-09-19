# 12. Transactions in Java — ONEPAGE

A **transaction** is a group of database operations treated as **one logical unit of work**.

The core idea is:

```text
START TRANSACTION
      ↓
 Operation 1
      ↓
 Operation 2
      ↓
 Operation 3
      ↓
  ┌───┴────┐
  ↓        ↓
COMMIT   ROLLBACK
  ↓        ↓
SAVE     UNDO
```

---

# 1. Transaction

## Definition

A **transaction** is a sequence of one or more database operations that should be treated as a single unit.

### Example: Bank transfer

Suppose ₹1,000 is transferred from Account A to Account B.

Two operations are required:

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

These two operations belong to **one transaction**.

We don't want:

```text
A → -₹1000
B → ❌ money not added
```

We want either:

```text
A → -₹1000
B → +₹1000
```

or:

```text
A → unchanged
B → unchanged
```

This is why transactions are important.

---

# 2. Auto-commit

JDBC connections normally begin with **auto-commit enabled**.

Check it:

```java
boolean status = con.getAutoCommit();
```

To disable it:

```java
con.setAutoCommit(false);
```

Now you control when the transaction is committed.

```java
con.setAutoCommit(false);

operation1();
operation2();
operation3();

con.commit();
```

### Auto-commit ON

```text
Operation
   ↓
Automatically commit
   ↓
Next operation
```

Each statement generally becomes its own transaction.

### Auto-commit OFF

```text
Operation 1
    ↓
Operation 2
    ↓
Operation 3
    ↓
commit()
```

The application controls the transaction boundary.

### Remember

```java
con.setAutoCommit(false);
```

means:

> "Don't automatically commit each transaction; I will control commit/rollback."

---

# 3. `commit()`

## Definition

`commit()` permanently makes the changes of the current transaction visible according to the database's transaction semantics.

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
INSERT
  ↓
UPDATE
  ↓
COMMIT
  ↓
Changes accepted
```

### Important

`commit()` is associated with the current transaction.

Once committed, you generally cannot use `rollback()` to undo those already committed changes.

```text
Before commit:
    rollback possible

After commit:
    that transaction is finished
```

---

# 4. `rollback()`

## Definition

`rollback()` abandons the uncommitted changes of the current transaction, subject to the database's transaction behavior.

Example:

```java
try {
    con.setAutoCommit(false);

    ps1.executeUpdate();
    ps2.executeUpdate();

    con.commit();

} catch (SQLException e) {

    con.rollback();
}
```

Flow:

```text
START
  ↓
Operation 1 ✓
  ↓
Operation 2 ✓
  ↓
Operation 3 ✗
  ↓
ROLLBACK
  ↓
Undo uncommitted transaction changes
```

### Bank example

```text
Account A: -1000 ✓
Account B: +1000 ✗
        ↓
    ROLLBACK
        ↓
Both changes abandoned
```

This prevents a partially completed transaction.

---

# 5. Savepoint

A **Savepoint** is a marker inside a transaction.

It allows you to roll back **part of the transaction** rather than necessarily rolling back everything.

Example:

```java
con.setAutoCommit(false);

operation1();

Savepoint sp = con.setSavepoint();

operation2();
operation3();

con.rollback(sp);
```

Conceptually:

```text
START
  ↓
Operation 1
  ↓
SAVEPOINT
  ↓
Operation 2
  ↓
Operation 3
  ↓
ROLLBACK TO SAVEPOINT
  ↓
Operation 2 & 3 abandoned
  ↓
Operation 1 remains part of transaction
```

The transaction is **not necessarily completed** after rolling back to a savepoint.

You can continue:

```java
con.rollback(sp);

operation4();

con.commit();
```

### Creating a savepoint

```java
Savepoint sp = con.setSavepoint();
```

### Rolling back to it

```java
con.rollback(sp);
```

### Important distinction

```text
rollback()
      ↓
Rollback entire current transaction

rollback(savepoint)
      ↓
Rollback to specified savepoint
```

---

# 6. ACID Basics

Transactions are commonly explained using the **ACID** properties.

```text
A → Atomicity
C → Consistency
I → Isolation
D → Durability
```

---

## A — Atomicity

### Meaning

A transaction is treated as an **all-or-nothing** unit.

Example:

```text
Transfer ₹1000

Debit     ✓
Credit    ✗
```

Atomicity aims to prevent the transaction from being left partially completed.

Conceptually:

```text
ALL operations succeed
        OR
NONE of the transaction's changes remain
```

---

## C — Consistency

### Meaning

A successful transaction should move the database from one valid state to another valid state, respecting applicable constraints and rules.

Example:

Suppose:

```text
Account balance cannot be negative.
```

A transaction should not leave the database violating that rule.

Database constraints such as:

```text
PRIMARY KEY
FOREIGN KEY
UNIQUE
CHECK
```

can help enforce consistency.

---

## I — Isolation

### Meaning

When multiple transactions execute concurrently, the database controls how their intermediate changes interact and become visible to each other.

Example:

```text
Transaction A
      ↕
Transaction B
```

Isolation helps prevent one transaction from improperly interfering with another.

JDBC/database systems support different **isolation levels**, such as:

```text
READ_UNCOMMITTED
READ_COMMITTED
REPEATABLE_READ
SERIALIZABLE
```

The exact behavior depends on the database system and configuration.

---

## D — Durability

### Meaning

Once a transaction has successfully committed, its changes are intended to survive subsequent failures according to the database's durability guarantees.

Conceptually:

```text
COMMIT
  ↓
Changes permanently recorded
  ↓
Database failure/restart
  ↓
Committed changes should survive
```

---

# 7. Complete JDBC Transaction Pattern

A common pattern is:

```java
try {
    con.setAutoCommit(false);

    // Operation 1
    ps1.executeUpdate();

    // Operation 2
    ps2.executeUpdate();

    // Operation 3
    ps3.executeUpdate();

    con.commit();

} catch (SQLException e) {

    try {
        con.rollback();
    } catch (SQLException rollbackError) {
        rollbackError.printStackTrace();
    }

    e.printStackTrace();
}
```

Conceptually:

```text
              TRANSACTION
                   │
          setAutoCommit(false)
                   │
                   ↓
              Operation 1
                   │
                   ↓
              Operation 2
                   │
                   ↓
              Operation 3
                   │
             ┌─────┴─────┐
             ↓           ↓
          SUCCESS       ERROR
             ↓           ↓
          commit()    rollback()
             ↓           ↓
           SAVE        UNDO
```

---

# 8. 🔥 Quick Comparison

| Concept                    | Meaning                                                                 |
| -------------------------- | ----------------------------------------------------------------------- |
| **Transaction**            | Group of database operations treated as one logical unit                |
| **Auto-commit**            | Automatically commits transactions according to each statement boundary |
| **`setAutoCommit(false)`** | Gives application control over transaction completion                   |
| **`commit()`**             | Successfully completes the current transaction                          |
| **`rollback()`**           | Abandons uncommitted changes in the current transaction                 |
| **Savepoint**              | Marker allowing partial rollback within a transaction                   |
| **Atomicity**              | All-or-nothing                                                          |
| **Consistency**            | Valid state → valid state                                               |
| **Isolation**              | Controls interaction/visibility among concurrent transactions           |
| **Durability**             | Committed changes survive failures according to DB guarantees           |

---

# 🧠 ONEPAGE Memory

```text
TRANSACTION
    │
    ├── Auto-commit
    │      ├── true  → automatic transaction completion
    │      └── false → application controls it
    │
    ├── commit()
    │      └── accept transaction
    │
    ├── rollback()
    │      └── abandon uncommitted transaction changes
    │
    ├── Savepoint
    │      └── partial rollback marker
    │
    └── ACID
           ├── A → Atomicity   → All or Nothing
           ├── C → Consistency → Valid State
           ├── I → Isolation   → Concurrent Transactions
           └── D → Durability  → Committed Changes Persist
```

### The most important JDBC sequence:

```java
con.setAutoCommit(false);

try {
    // SQL operations

    con.commit();

} catch (SQLException e) {
    con.rollback();
}
```

**Core idea:**

> **Auto-commit OFF → perform related operations → `commit()` if everything succeeds → `rollback()` if something fails.**
