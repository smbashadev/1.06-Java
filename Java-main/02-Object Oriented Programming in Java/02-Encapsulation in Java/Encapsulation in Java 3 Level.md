# 🔐 Encapsulation in Java — 3LEVEL

We'll understand **Encapsulation in 3 levels**:

```text
LEVEL 1 → Beginner
LEVEL 2 → Intermediate
LEVEL 3 → Interview / Deep Understanding
```

The same **Bank Account** example will be used throughout.

---

# 🟢 LEVEL 1 — BEGINNER

## 1. What is Encapsulation?

> **Encapsulation means protecting the data of an object and providing controlled access to that data through methods.**

In Java, we commonly use:

```java
private
```

for sensitive data.

Example:

```java
class BankAccount {

    private double amt;
    private int pin;
}
```

Here:

```text
amt → private
pin → private
```

Outside code cannot directly access them.

---

# 2. Why Do We Need It?

Imagine:

```java
class BankAccount {

    double amt;
    int pin;
}
```

Then anyone could do:

```java
account.amt = -50000;
account.pin = 9999;
```

❌ No control.

This is the **security problem**.

---

# 3. What If We Make Everything Private?

```java
class BankAccount {

    private double amt;
    private int pin;
}
```

Now:

```java
account.amt = 50000;
```

❌ Not allowed.

But if we provide **no methods**, legitimate operations also become impossible.

This is the **over-security problem** in our teaching example.

---

# 4. The Solution

Use:

```text
Private Data
     +
Public Methods
     =
Controlled Access
```

Example:

```java
class BankAccount {

    private double amt;
    private int pin;

    public void deposit(double amount) {
        amt += amount;
    }

    public void withdraw(double amount, int enteredPin) {

        if (enteredPin == pin) {
            amt -= amount;
        }
    }

    public void checkBalance(int enteredPin) {

        if (enteredPin == pin) {
            System.out.println(amt);
        }
    }
}
```

Now the outside world interacts through methods.

---

# 5. Basic Flow

```text
Outside Code
     ↓
public method
     ↓
validation
     ↓
private data
```

For example:

```java
account.withdraw(5000, 1234);
```

instead of:

```java
account.amt = account.amt - 5000;
```

---

# 6. LEVEL 1 Memory Trick 🧠

> **Encapsulation = Private Data + Controlled Methods**

---

# 🟡 LEVEL 2 — INTERMEDIATE

Now let's understand **how** encapsulation works.

---

# 7. Complete Encapsulation Program

```java
class BankAccount {

    private double amt;
    private int pin;

    // Constructor
    BankAccount(double amt, int pin) {

        this.amt = amt;
        this.pin = pin;
    }

    // Deposit
    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        amt = amt + amount;

        System.out.println("Amount deposited");
    }

    // Withdraw
    public void withdraw(double amount, int enteredPin) {

        if (enteredPin != pin) {
            System.out.println("Invalid PIN");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        if (amount > amt) {
            System.out.println("Insufficient balance");
            return;
        }

        amt = amt - amount;

        System.out.println("Amount withdrawn");
    }

    // Check balance
    public void checkBalance(int enteredPin) {

        if (enteredPin == pin) {
            System.out.println(
                "Balance = " + amt
            );
        } else {
            System.out.println("Invalid PIN");
        }
    }
}

public class Test {

    public static void main(String[] args) {

        BankAccount account =
                new BankAccount(10000, 1234);

        account.checkBalance(1234);

        account.deposit(5000);

        account.withdraw(3000, 1234);

        account.checkBalance(1234);

        account.withdraw(2000, 9999);
    }
}
```

---

# 8. Understand the Variables

```java
private double amt;
private int pin;
```

These are **instance variables**.

They represent the internal state of the bank account.

Because they're private:

```java
account.amt
```

from outside the class is not allowed.

---

# 9. Understand the Constructor

```java
BankAccount(double amt, int pin) {

    this.amt = amt;
    this.pin = pin;
}
```

When we write:

```java
BankAccount account =
        new BankAccount(10000, 1234);
```

the constructor initializes:

```text
amt = 10000
pin = 1234
```

---

# 10. Why `this`?

We have:

```java
private double amt;
```

and:

```java
BankAccount(double amt, int pin)
```

There are two `amt`s.

Therefore:

```java
this.amt = amt;
```

means:

```text
object's amt ← parameter amt
```

Similarly:

```java
this.pin = pin;
```

means:

```text
object's pin ← parameter pin
```

### Easy trick:

Read:

```java
this.amt
```

as:

> **My object's `amt`.**

---

# 11. Deposit

The caller writes:

```java
account.deposit(5000);
```

The method:

```java
public void deposit(double amount) {

    if (amount > 0) {
        amt += amount;
    }
}
```

controls the modification.

The caller cannot directly change `amt`.

---

# 12. Withdraw

Caller:

```java
account.withdraw(3000, 1234);
```

The method checks:

```text
PIN correct?
     ↓
Amount valid?
     ↓
Enough balance?
     ↓
Withdraw
```

This is much better than allowing:

```java
account.amt -= 3000;
```

---

# 13. Check Balance

Instead of:

```java
System.out.println(account.amt);
```

we use:

```java
account.checkBalance(1234);
```

The class controls whether the balance can be revealed.

---

# 14. 🔥 Getter and Setter

Another common encapsulation pattern is:

```java
class Student {

    private int marks;

    public void setMarks(int marks) {

        if (marks >= 0 && marks <= 100) {
            this.marks = marks;
        }
    }

    public int getMarks() {
        return marks;
    }
}
```

Usage:

```java
Student s = new Student();

s.setMarks(90);

System.out.println(s.getMarks());
```

Here:

```text
setMarks()
   ↓
Modify

getMarks()
   ↓
Read
```

But **not every private field needs a setter**.

For a bank account, methods such as:

```text
deposit()
withdraw()
```

are often better than:

```text
setAmt()
```

because they enforce meaningful business operations.

---

# 15. Data Hiding vs Encapsulation

### Data hiding

```java
private double amt;
```

prevents direct access.

### Encapsulation

```text
private data
     +
methods
     +
controlled access
```

is the broader concept.

So:

> **Data hiding is an important part of encapsulation, but encapsulation is broader than simply using `private`.**

---

# 16. Default Constructor

Consider:

```java
class BankAccount {

    private double amt;
    private int pin;
}
```

No constructor has been written.

Java provides a **default no-argument constructor**.

Conceptually:

```java
BankAccount() {
}
```

Therefore:

```java
BankAccount account =
        new BankAccount();
```

is valid.

The instance variables initially have:

```text
double → 0.0
int    → 0
```

---

# 17. Default Constructor Program

```java
class BankAccount {

    private double amt;
    private int pin;

    void display() {

        System.out.println("Amount = " + amt);
        System.out.println("PIN = " + pin);
    }
}

public class Test {

    public static void main(String[] args) {

        BankAccount account =
                new BankAccount();

        account.display();
    }
}
```

Output:

```text
Amount = 0.0
PIN = 0
```

---

# 18. Important Constructor Rule

If you write:

```java
class BankAccount {

    BankAccount(double amt, int pin) {
    }
}
```

Java does **not** automatically provide:

```java
BankAccount()
```

Therefore:

```java
new BankAccount();
```

❌ Compilation error.

If you want both, explicitly write:

```java
class BankAccount {

    BankAccount() {
    }

    BankAccount(double amt, int pin) {
    }
}
```

---

# 🔴 LEVEL 3 — INTERVIEW / DEEP UNDERSTANDING

Now let's remove the deeper doubts.

---

# 19. Is `private` Equal to Encapsulation?

### No.

`private` is an **access modifier**.

Encapsulation is a **design principle**.

For example:

```java
private int balance;
```

provides restricted access.

But a well-designed encapsulated class also provides an appropriate interface:

```java
deposit()
withdraw()
checkBalance()
```

So:

```text
private
   ↓
Access-control mechanism

Encapsulation
   ↓
Overall design principle
```

---

# 20. Is Encapsulation Only About Security?

### No.

Security is one benefit, but encapsulation also provides:

### 1. Controlled access

The class controls modifications.

### 2. Validation

```java
if (amount > 0)
```

### 3. Maintainability

Internal implementation can change without necessarily changing callers.

### 4. Reduced coupling

Other classes don't need to depend directly on internal fields.

### 5. Protection of invariants

The class can prevent invalid states.

---

# 21. What Is an Invariant?

An **invariant** is a condition that should remain true for a valid object.

For a simplified bank account:

```text
balance >= 0
```

We can protect this using:

```java
if (amount <= amt) {
    amt -= amount;
}
```

Without encapsulation:

```java
account.amt = -500000;
```

could violate the rule.

With encapsulation, the class can enforce it.

---

# 22. 🔥 Why Is `setAmt()` Often Bad for a Bank Account?

Suppose:

```java
public void setAmt(double amt) {
    this.amt = amt;
}
```

Then:

```java
account.setAmt(-50000);
```

may bypass banking rules.

Instead:

```java
account.deposit(5000);
```

or:

```java
account.withdraw(2000, 1234);
```

expresses a meaningful operation.

This is stronger encapsulation because the class controls **behavior**, not just field assignment.

---

# 23. Encapsulation vs Abstraction

| Encapsulation                         | Abstraction                                                |
| ------------------------------------- | ---------------------------------------------------------- |
| Controls access to internal state     | Hides unnecessary implementation details                   |
| Focuses on how state is protected     | Focuses on what functionality is exposed                   |
| Uses access control such as `private` | Often implemented with interfaces/abstract classes         |
| Example: private `amt`                | Example: `withdraw()` without exposing internal processing |

Memory:

```text
Encapsulation → CONTROL
Abstraction   → HIDE
```

---

# 24. Encapsulation vs Inheritance

They solve different problems.

### Encapsulation

```text
Protect/control state
```

### Inheritance

```text
Reuse/extend behavior through a class relationship
```

Example:

```java
class Animal {
}

class Dog extends Animal {
}
```

That's inheritance—not encapsulation.

---

# 25. Encapsulation vs Polymorphism

### Encapsulation

Controls access:

```java
private double amt;
```

### Polymorphism

Allows one interface/reference to represent different implementations.

For example:

```java
Animal a = new Dog();
a.sound();
```

Different concepts.

---

# 26. 🔥 Can Encapsulation Exist Without Getters and Setters?

**Absolutely.**

This is important.

Consider:

```java
class BankAccount {

    private double amt;

    public void deposit(double amount) {
        if (amount > 0) {
            amt += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= amt) {
            amt -= amount;
        }
    }
}
```

There is no:

```java
getAmt()
setAmt()
```

Yet this is still a good example of encapsulation.

Why?

Because the internal state is protected and accessed through controlled behavior.

---

# 27. Can Encapsulation Exist Without `private`?

The strongest beginner answer is:

> **Java encapsulation is commonly implemented using `private` fields and controlled methods.**

But technically, encapsulation is broader than one keyword.

Java has several access levels:

```text
private
default/package-private
protected
public
```

The choice depends on the API and design.

For sensitive object state, `private` is generally the strongest starting point.

---

# 28. 🔥 What Happens If Two Objects Exist?

```java
BankAccount a1 =
        new BankAccount(10000, 1234);

BankAccount a2 =
        new BankAccount(5000, 5678);
```

Each object has its own instance state:

```text
a1
 ├── amt = 10000
 └── pin = 1234

a2
 ├── amt = 5000
 └── pin = 5678
```

Encapsulation applies independently to each object's state.

---

# 29. Can Outside Code Access the Private Variables Through an Object?

No, not through normal Java source-level access.

This:

```java
account.amt
```

is not permitted outside the declaring class.

Instead:

```java
account.deposit(5000);
account.withdraw(2000, 1234);
```

is allowed if those methods are accessible.

---

# 30. Why Is This Called "Controlled Access"?

Because the class determines:

```text
WHO
 ↓
CAN DO WHAT
 ↓
UNDER WHICH CONDITIONS
```

For example:

```java
account.withdraw(5000, 1234);
```

The account can check:

```text
PIN?
 ↓
Amount?
 ↓
Balance?
 ↓
Allow / Reject
```

That's controlled access.

---

# 31. 🔥 Three-Level Comparison

| Level      | What you should understand                                                                                  |
| ---------- | ----------------------------------------------------------------------------------------------------------- |
| 🟢 Level 1 | `private` protects data and methods provide access                                                          |
| 🟡 Level 2 | Methods validate and control changes to state                                                               |
| 🔴 Level 3 | Encapsulation protects invariants, reduces coupling, and hides implementation behind a controlled interface |

---

# 32. The Three Bank Account Designs

### ❌ Level 0 — No Encapsulation

```java
class BankAccount {

    double amt;
    int pin;
}
```

Usage:

```java
account.amt = -50000;
```

---

### ❌ Level 0.5 — Over-Security

```java
class BankAccount {

    private double amt;
    private int pin;
}
```

No useful operations are exposed.

---

### ✅ Proper Encapsulation

```java
class BankAccount {

    private double amt;
    private int pin;

    public void deposit(double amount) {
        // validation
    }

    public void withdraw(double amount, int pin) {
        // validation
    }

    public void checkBalance(int pin) {
        // controlled access
    }
}
```

---

# 🧠 FINAL 3LEVEL MEMORY MAP

```text
                 ENCAPSULATION
                       |
            ┌──────────┴──────────┐
            ↓                     ↓
       PRIVATE STATE         CONTROLLED API
            ↓                     ↓
       amt + pin          deposit()
                          withdraw()
                          checkBalance()
            |                     |
            └──────────┬──────────┘
                       ↓
                VALIDATION
                       ↓
                OBJECT STATE
```

### 🟢 LEVEL 1

> **Protect data using `private`.**

### 🟡 LEVEL 2

> **Provide methods to access or modify the data in a controlled way.**

### 🔴 LEVEL 3

> **Encapsulation protects an object's internal state and invariants, reduces coupling, hides implementation details, and exposes a meaningful interface through which other objects interact with it.**

## 🔥 One final sentence to memorize

> **Encapsulation is not simply making variables private; it is designing a class so that its internal state is protected and all meaningful interaction happens through controlled, well-defined operations.**
