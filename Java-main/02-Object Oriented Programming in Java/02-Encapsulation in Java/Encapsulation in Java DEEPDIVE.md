# 🔐 Encapsulation in Java — DEEPDIVE

Encapsulation is one of the **four major pillars of OOP**:

```text
OOP
│
├── Encapsulation
├── Inheritance
├── Polymorphism
└── Abstraction
```

The easiest way to understand encapsulation is through a **Bank Account**, because a bank account contains sensitive data such as **amount and PIN**.

---

# 1. What Is Encapsulation?

### Definition

> **Encapsulation is the process of binding data and the methods that operate on that data into a single unit (class), while restricting direct access to the internal data and providing controlled access through methods.**

In Java, encapsulation is commonly implemented using:

```text
private variables
      +
public methods
      +
validation / business rules
```

For example:

```java
class BankAccount {

    private double amt;
    private int pin;

    public void deposit(double amount) {
        // controlled operation
    }

    public void withdraw(double amount, int enteredPin) {
        // controlled operation
    }

    public void checkBalance(int enteredPin) {
        // controlled operation
    }
}
```

The important idea is:

```text
Outside World
      |
      ↓
Public Method
      |
      ↓
Validation / Security
      |
      ↓
Private Data
```

---

# 2. Why Do We Need Encapsulation?

Imagine a bank account containing:

```text
Amount = ₹50,000
PIN = 1234
```

We don't want arbitrary code to do this:

```java
account.amt = -500000;
account.pin = 9999;
```

That would allow uncontrolled modification.

So we protect the data:

```java
private double amt;
private int pin;
```

Then provide controlled operations:

```java
deposit()
withdraw()
checkBalance()
```

This allows us to say:

> **The data is hidden, but legitimate operations are still available.**

---

# 3. Security Problem — Without Encapsulation ❌

Consider:

```java
class BankAccount {

    double amt;
    int pin;
}
```

Now:

```java
public class Test {

    public static void main(String[] args) {

        BankAccount account = new BankAccount();

        account.amt = 50000;
        account.pin = 1234;

        System.out.println(account.amt);
        System.out.println(account.pin);
    }
}
```

This compiles if these classes are accessible in the same package/context.

The problem is that outside code has direct access to the internal state.

For example:

```java
account.amt = -100000;
```

There is no validation.

Or:

```java
account.pin = 1111;
```

The class has no control over how its data is modified.

### Problem:

```text
Data
 ↓
Direct access
 ↓
No validation
 ↓
No control
```

---

# 4. Over-Security Problem ❌

Now we try to protect everything:

```java
class BankAccount {

    private double amt;
    private int pin;
}
```

This prevents direct access:

```java
BankAccount account = new BankAccount();

account.amt = 50000;  // ERROR
account.pin = 1234;  // ERROR
```

That's good from a protection perspective.

But now legitimate code cannot perform operations either.

For example:

```java
account.withdraw(5000, 1234);
```

doesn't exist.

So we have created another problem:

```text
Data protected
      ↓
No controlled interface
      ↓
Legitimate operations impossible
```

This is the **over-security problem** in the teaching model.

---

# 5. 🔥 Correct Solution — Encapsulation

The solution is **controlled access**.

```java
class BankAccount {

    private double amt;
    private int pin;

    public void deposit(double amount) {
        // controlled access
    }

    public void withdraw(double amount, int enteredPin) {
        // controlled access
    }

    public void checkBalance(int enteredPin) {
        // controlled access
    }
}
```

Now:

```text
                 BankAccount
                      |
          ┌───────────┴───────────┐
          ↓                       ↓
    private data            public methods
     amt + pin               deposit()
                             withdraw()
                             checkBalance()
```

The outside world cannot directly manipulate the fields.

Instead:

```text
User
 ↓
deposit()
withdraw()
checkBalance()
 ↓
Validation
 ↓
Private data
```

That is the essence of encapsulation.

---

# 6. Complete Bank Account Program

Let's build it step by step.

```java
class BankAccount {

    private double amt;
    private int pin;

    // Parameterized constructor
    BankAccount(double amt, int pin) {
        this.amt = amt;
        this.pin = pin;
    }

    // Deposit
    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }

        amt = amt + amount;

        System.out.println("Amount deposited successfully.");
    }

    // Withdraw
    public void withdraw(double amount, int enteredPin) {

        if (enteredPin != pin) {
            System.out.println("Invalid PIN.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }

        if (amount > amt) {
            System.out.println("Insufficient balance.");
            return;
        }

        amt = amt - amount;

        System.out.println("Amount withdrawn successfully.");
    }

    // Check balance
    public void checkBalance(int enteredPin) {

        if (enteredPin == pin) {
            System.out.println("Current Balance = " + amt);
        } else {
            System.out.println("Invalid PIN.");
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

        account.withdraw(50000, 1234);
    }
}
```

Possible output:

```text
Current Balance = 10000.0
Amount deposited successfully.
Amount withdrawn successfully.
Current Balance = 12000.0
Invalid PIN.
Insufficient balance.
```

---

# 7. Let's Understand Every Part

## Step 1 — Private Variables

```java
private double amt;
private int pin;
```

These are **instance variables**.

Because they are `private`, code outside `BankAccount` cannot directly access them.

For example:

```java
account.amt
```

from `Test` is not allowed.

---

# 8. Why `private`?

`private` is an access modifier.

It means the member is directly accessible only within the **same top-level class that declares it**.

So:

```java
class BankAccount {

    private double amt;

    void test() {

        amt = 5000;  // allowed
    }
}
```

But outside:

```java
class Test {

    public static void main(String[] args) {

        BankAccount b = new BankAccount();

        // b.amt = 5000;   // ERROR
    }
}
```

This gives the class control over its internal state.

---

# 9. Why Public Methods?

We need a way for outside code to request legitimate operations.

So we expose:

```java
public void deposit(...)
public void withdraw(...)
public void checkBalance(...)
```

These methods become the **controlled interface** of the object.

Notice something important:

We are **not** saying:

> "The user can access `amt`."

We are saying:

> "The user can request the account to perform an operation involving the amount."

That's a much better design.

---

# 10. Deposit Operation

```java
public void deposit(double amount) {

    if (amount <= 0) {
        System.out.println("Invalid deposit amount.");
        return;
    }

    amt = amt + amount;
}
```

The caller doesn't directly modify:

```java
amt
```

Instead:

```java
account.deposit(5000);
```

The method decides whether the operation is valid.

---

# 11. Withdraw Operation

```java
public void withdraw(double amount, int enteredPin) {

    if (enteredPin != pin) {
        System.out.println("Invalid PIN.");
        return;
    }

    if (amount <= 0) {
        System.out.println("Invalid withdrawal amount.");
        return;
    }

    if (amount > amt) {
        System.out.println("Insufficient balance.");
        return;
    }

    amt = amt - amount;
}
```

There are multiple rules:

```text
PIN correct?
     ↓
Amount valid?
     ↓
Sufficient balance?
     ↓
Withdraw
```

This is one of the biggest advantages of encapsulation:

> **The class can enforce rules around its own state.**

---

# 12. Checking Balance

We don't write:

```java
System.out.println(account.amt);
```

Instead:

```java
account.checkBalance(1234);
```

The method verifies the PIN:

```java
public void checkBalance(int enteredPin) {

    if (enteredPin == pin) {
        System.out.println("Current Balance = " + amt);
    } else {
        System.out.println("Invalid PIN.");
    }
}
```

Again:

```text
Private data
     ↑
     |
checkBalance()
     ↑
     |
Outside code
```

---

# 13. 🔥 Why Not Make `amt` Public?

Suppose:

```java
public double amt;
```

Then anyone could do:

```java
account.amt = -50000;
```

The class loses control.

With:

```java
private double amt;
```

the class can decide exactly how `amt` changes.

For example:

```java
public void deposit(double amount) {

    if (amount > 0) {
        amt += amount;
    }
}
```

Now invalid values can be rejected.

---

# 14. Constructor and Encapsulation

Our constructor is:

```java
BankAccount(double amt, int pin) {

    this.amt = amt;
    this.pin = pin;
}
```

When we create:

```java
BankAccount account =
        new BankAccount(10000, 1234);
```

the constructor initializes the object's private state.

Conceptually:

```text
new BankAccount(10000, 1234)
            ↓
       Constructor
            ↓
      amt = 10000
      pin = 1234
```

---

# 15. Why `this.amt = amt`?

There are two variables named `amt`:

```java
private double amt;
```

and:

```java
BankAccount(double amt, int pin)
```

The parameter `amt` **shadows** the instance variable `amt` within the constructor.

So:

```java
this.amt
```

means:

> The current object's instance variable.

And:

```java
amt
```

means:

> The constructor parameter.

Therefore:

```java
this.amt = amt;
```

means:

```text
object's amt = supplied amt
```

Likewise:

```java
this.pin = pin;
```

means:

```text
object's pin = supplied pin
```

---

# 16. What If We Don't Use `this`?

Suppose:

```java
BankAccount(double amt, int pin) {

    amt = amt;
    pin = pin;
}
```

This doesn't initialize the instance variables as intended.

Both sides refer to the parameters in this context.

So:

```text
this.amt = amt;
```

is the clear way to distinguish:

```text
instance variable ← parameter
```

---

# 17. 🔥 Constructor Is Not Encapsulation

Another important distinction:

```text
Constructor ≠ Encapsulation
```

A constructor is used to initialize objects.

Encapsulation is about:

```text
bundling
+
access control
+
controlled interaction
```

A constructor can be **used as part of an encapsulated class design**, but the constructor itself is not encapsulation.

---

# 18. Default Constructor — Important Connection

Consider:

```java
class BankAccount {

    private double amt;
    private int pin;
}
```

No constructor is declared.

Java provides a **default no-argument constructor**.

Conceptually:

```java
BankAccount() {
}
```

Therefore:

```java
BankAccount account = new BankAccount();
```

is valid.

The private variables receive their normal default values:

```text
double → 0.0
int    → 0
```

---

# 19. Program Showing Default Constructor

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

        BankAccount account = new BankAccount();

        account.display();
    }
}
```

Output:

```text
Amount = 0.0
PIN = 0
```

Why?

Because the instance variables were not explicitly initialized.

Java supplies default values to instance fields.

---

# 20. 🔥 Very Important: When Does the Compiler Provide the Default Constructor?

Suppose:

```java
class BankAccount {
}
```

You don't write any constructor.

Java provides a no-argument default constructor.

But now:

```java
class BankAccount {

    BankAccount(double amt, int pin) {
        // ...
    }
}
```

You have explicitly declared a constructor.

Java **does not additionally provide**:

```java
BankAccount()
```

automatically.

Therefore:

```java
new BankAccount();
```

causes a compilation error.

---

# 21. Want Both Constructors?

Write both explicitly:

```java
class BankAccount {

    private double amt;
    private int pin;

    BankAccount() {
        amt = 0;
        pin = 0;
    }

    BankAccount(double amt, int pin) {
        this.amt = amt;
        this.pin = pin;
    }
}
```

Now both work:

```java
BankAccount b1 = new BankAccount();

BankAccount b2 =
        new BankAccount(10000, 1234);
```

---

# 22. Default Class vs Default Constructor

Don't confuse these terms.

### Default/package-private class

```java
class BankAccount {
}
```

Because there is no `public`, the top-level class has **package-private access**.

### Default constructor

A no-argument constructor supplied by the compiler when **no constructor is declared**.

These are completely different concepts:

```text
default class
      ≠
default constructor
```

---

# 23. Is Encapsulation Just `private`?

**No.**

This is a common misconception.

Suppose:

```java
class Student {

    private int marks;
}
```

You've restricted direct access, but that's only part of the design.

A properly encapsulated design generally provides an appropriate interface:

```java
public void setMarks(int marks) {
    // validation
}

public int getMarks() {
    return marks;
}
```

or, depending on the design, domain-specific methods:

```java
public void addMarks(int value) {
    // rules
}
```

The key is **controlled access**, not simply generating getters and setters for everything.

---

# 24. Getter and Setter Approach

A traditional encapsulation example is:

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

Use:

```java
Student s = new Student();

s.setMarks(85);

System.out.println(s.getMarks());
```

Output:

```text
85
```

---

# 25. But Bank Accounts Don't Always Need a Setter

This is an important design insight.

You might think:

```java
public void setAmt(double amt)
```

But consider what that would allow:

```java
account.setAmt(1000000);
```

That may bypass important banking rules.

Instead, domain-specific methods are better:

```java
deposit()
withdraw()
transfer()
```

For example:

```java
account.deposit(5000);
```

This expresses **what the account is allowed to do**, rather than exposing a generic way to replace its internal state.

---

# 26. 🔥 Encapsulation vs Data Hiding

These terms are related but not identical.

### Data hiding

Restricting direct access to internal data.

Example:

```java
private double amt;
```

### Encapsulation

A broader design principle involving:

```text
Data
+
Behavior
+
Access control
+
Controlled interface
```

So:

> **Data hiding is an important technique used in encapsulation.**

---

# 27. Encapsulation vs Abstraction

This is one of the most frequently confused pairs.

| Encapsulation                  | Abstraction                               |
| ------------------------------ | ----------------------------------------- |
| Bundles data and behavior      | Hides unnecessary implementation details  |
| Controls access to state       | Focuses on essential functionality        |
| Commonly uses access modifiers | Commonly uses abstract classes/interfaces |
| "How is access controlled?"    | "What should be exposed?"                 |

### Memory trick:

```text
Encapsulation → CONTROL
Abstraction   → HIDE
```

---

# 28. Encapsulation vs Inheritance

### Encapsulation

```text
Protect/control internal state
```

### Inheritance

```text
Create a class relationship
```

Example:

```java
class Animal {
}

class Dog extends Animal {
}
```

Inheritance doesn't automatically mean encapsulation.

They solve different problems.

---

# 29. 🔥 What Is the Main Benefit?

The biggest practical benefit is:

> **A class can change its internal implementation without forcing every caller to know or modify that implementation.**

Suppose today:

```java
deposit()
```

updates a simple field:

```java
amt += amount;
```

Later you might add:

```text
transaction logging
fraud checks
daily limits
notifications
database updates
```

The caller can still write:

```java
account.deposit(5000);
```

The implementation can evolve behind the interface.

This is an important reason encapsulation improves maintainability.

---

# 30. Real-World Banking Design

A simplified model:

```text
                 BankAccount
                      |
       ┌──────────────┴──────────────┐
       ↓                             ↓
   PRIVATE STATE                PUBLIC API
       |                             |
   amt + pin                  deposit()
                              withdraw()
                              checkBalance()
```

Outside code:

```text
                    Customer
                       |
                       ↓
              Public operations
                       |
                       ↓
                 BankAccount
                       |
                 Validation
                       |
                       ↓
                  Private state
```

The customer doesn't directly manipulate the account's internal fields.

---

# 31. Complete Improved Program

Here's a clean version suitable for learning and exams:

```java
class BankAccount {

    private double amt;
    private int pin;

    // Parameterized constructor
    BankAccount(double amt, int pin) {

        if (amt >= 0) {
            this.amt = amt;
        }

        this.pin = pin;
    }

    // Deposit
    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }

        amt += amount;

        System.out.println(
            "Deposited: " + amount
        );
    }

    // Withdraw
    public void withdraw(double amount, int enteredPin) {

        if (enteredPin != pin) {
            System.out.println("Invalid PIN.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }

        if (amount > amt) {
            System.out.println("Insufficient balance.");
            return;
        }

        amt -= amount;

        System.out.println(
            "Withdrawn: " + amount
        );
    }

    // Check balance
    public void checkBalance(int enteredPin) {

        if (enteredPin != pin) {
            System.out.println("Invalid PIN.");
            return;
        }

        System.out.println(
            "Balance: " + amt
        );
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

        account.withdraw(1000, 9999);

        account.withdraw(50000, 1234);
    }
}
```

---

# 32. Trace the Program

Initially:

```text
amt = 10000
pin = 1234
```

### Step 1

```java
account.checkBalance(1234);
```

PIN matches:

```text
Balance = 10000
```

### Step 2

```java
account.deposit(5000);
```

Now:

```text
amt = 15000
```

### Step 3

```java
account.withdraw(3000, 1234);
```

PIN correct and balance sufficient:

```text
amt = 12000
```

### Step 4

```java
account.checkBalance(1234);
```

Output:

```text
Balance = 12000
```

### Step 5

```java
account.withdraw(1000, 9999);
```

Wrong PIN:

```text
Invalid PIN
```

The balance remains:

```text
12000
```

---

# 33. What Would Happen Without Encapsulation?

Imagine:

```java
class BankAccount {

    double amt;
    int pin;
}
```

Then:

```java
BankAccount account = new BankAccount();

account.amt = -999999;
account.pin = 1111;
```

The class cannot enforce its own rules.

That's the fundamental problem encapsulation addresses.

---

# 34. Important Exam Question

### Q: How do you achieve encapsulation in Java?

A good answer:

> Encapsulation is achieved by keeping the internal state of a class private and providing appropriate public or otherwise controlled methods through which the state can be accessed or modified according to the class's rules.

Example:

```java
class BankAccount {

    private double amt;
    private int pin;

    public void deposit(double amount) {
        // controlled operation
    }

    public void withdraw(double amount, int pin) {
        // controlled operation
    }
}
```

---

# 35. Important Interview Question

### Q: Why should fields generally be private?

Because it:

* prevents uncontrolled direct modification,
* allows validation,
* preserves invariants,
* hides implementation details,
* gives the class control over its state,
* makes future implementation changes easier.

---

# 36. Important Interview Question

### Q: Does encapsulation mean hiding all data completely?

**No.**

It means controlling access.

Some information may intentionally be exposed through methods.

For example:

```java
account.checkBalance(1234);
```

The balance is not directly exposed as a field, but the class provides a controlled operation to retrieve/display it.

---

# 37. Important Interview Question

### Q: Why not simply make everything public?

Because public fields allow outside code to modify state without the class being able to enforce its rules.

Bad:

```java
account.amt = -5000;
```

Better:

```java
account.withdraw(5000, 1234);
```

The second approach lets the object decide whether the operation is valid.

---

# 38. Important Interview Question

### Q: Why not make everything private and provide no methods?

Because then external code cannot meaningfully interact with the object's state or behavior.

That is the **over-security** problem in our teaching example.

Good encapsulation is:

```text
Private internal state
        +
Appropriate public interface
        +
Validation/business rules
```

---

# 39. 🔥 One Very Important Correction

Don't define encapsulation only as:

> "Wrapping data and methods into a class."

That's incomplete.

A stronger definition is:

> **Encapsulation is the bundling of state and behavior within a class together with controlled access to the object's internal state.**

Why is this better?

Because simply putting variables and methods inside a class isn't enough to demonstrate meaningful access control.

---

# 40. FINAL DEEP-DIVE MAP

```text
                       ENCAPSULATION
                             |
              ┌──────────────┴──────────────┐
              ↓                             ↓
        INTERNAL STATE                PUBLIC INTERFACE
              ↓                             ↓
       private amt                    deposit()
       private pin                    withdraw()
                                      checkBalance()
              |                             |
              └──────────────┬──────────────┘
                             ↓
                       CONTROLLED ACCESS
                             |
                    ┌────────┴────────┐
                    ↓                 ↓
                Validation        Business Rules
                    ↓                 ↓
                    └────────┬────────┘
                             ↓
                     Protected State
```

## 🧠 Remember the Bank Account

### ❌ No security

```java
double amt;
int pin;
```

Anyone can directly manipulate them.

### ❌ Over-security

```java
private double amt;
private int pin;
```

with no useful interface.

### ✅ Encapsulation

```java
private double amt;
private int pin;

public void deposit(...)
public void withdraw(...)
public void checkBalance(...)
```

The golden idea is:

> **Don't give the outside world direct control over the data. Give it controlled operations instead.**

And the constructor:

```java
BankAccount(double amt, int pin) {
    this.amt = amt;
    this.pin = pin;
}
```

initializes the object's private state, while `this` distinguishes the **instance variables** from the **constructor parameters**.
