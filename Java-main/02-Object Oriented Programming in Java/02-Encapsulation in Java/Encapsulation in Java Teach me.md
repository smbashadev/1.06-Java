# 🔐 Encapsulation in Java — TEACHME

Let's learn **Encapsulation from zero**, using one simple example throughout: a **Bank Account**.

By the end, you should be able to answer:

* What is encapsulation?
* Why do we need it?
* What is the security problem?
* What is the over-security problem?
* How does `private` help?
* Why do we need methods?
* How does a constructor initialize private data?
* Why do we use `this`?
* What is a default constructor?
* How are encapsulation, data hiding, and abstraction different?

---

# 1. First Imagine a Bank Account 🏦

Suppose your bank account has:

```text
Amount = ₹10,000
PIN    = 1234
```

In Java, we could represent it as:

```java
class BankAccount {

    double amt;
    int pin;
}
```

Now imagine another class can do:

```java
account.amt = -50000;
account.pin = 9999;
```

Would a real bank allow anybody to directly change your balance or PIN?

**No!**

This is exactly the kind of problem encapsulation helps us solve.

---

# 2. The Basic Idea

We want:

```text
                    Bank Account
                         |
              ┌──────────┴──────────┐
              ↓                     ↓
        Private Data          Public Methods
              ↓                     ↓
          amt + pin       deposit()
                          withdraw()
                          checkBalance()
```

The outside world doesn't directly touch the data.

Instead:

```text
User
 ↓
Method
 ↓
Validation
 ↓
Private Data
```

That is **encapsulation**.

---

# 3. What Is Encapsulation?

### Simple definition

> **Encapsulation means keeping an object's data and related methods together in a class and controlling how outside code accesses the object's internal data.**

In Java, we commonly achieve this using:

```java
private
```

for data and appropriate methods for controlled access.

---

# 4. 🔥 First Understand the Security Problem

Let's write a bad program.

```java
class BankAccount {

    double amt;
    int pin;
}
```

Then:

```java
public class Test {

    public static void main(String[] args) {

        BankAccount account = new BankAccount();

        account.amt = 10000;
        account.pin = 1234;

        System.out.println(account.amt);
        System.out.println(account.pin);
    }
}
```

The outside code can directly access the variables.

Even this is possible:

```java
account.amt = -500000;
```

There is no rule preventing it.

So:

```text
Public data
    ↓
Direct access
    ↓
No control
```

❌ **Security problem**

---

# 5. Let's Protect the Data

We change:

```java
double amt;
int pin;
```

to:

```java
private double amt;
private int pin;
```

Now:

```java
class BankAccount {

    private double amt;
    private int pin;
}
```

Try:

```java
BankAccount account = new BankAccount();

account.amt = 10000;
```

❌ Compilation error.

Why?

Because:

```text
private
  ↓
Direct access restricted outside the class
```

Excellent!

But now we have another problem.

---

# 6. 🔥 Over-Security Problem

Suppose the bank account contains:

```java
private double amt;
private int pin;
```

But we don't provide any methods.

Then how can someone legitimately:

```text
Deposit money?
Withdraw money?
Check balance?
```

They can't directly access the fields.

So:

```text
No security
    ↓
Anyone can modify data ❌

Too much restriction
    ↓
Nobody can use the data ❌
```

We need the **middle path**.

---

# 7. The Middle Path = Encapsulation ✅

We keep the variables private:

```java
private double amt;
private int pin;
```

but provide controlled methods:

```java
public void deposit()
public void withdraw()
public void checkBalance()
```

Now:

```text
               BankAccount
                    |
       ┌────────────┴────────────┐
       ↓                         ↓
  private data              public methods
       ↓                         ↓
  amt + pin              deposit()
                         withdraw()
                         checkBalance()
```

This is the basic idea of encapsulation.

---

# 8. Let's Build It Step by Step

Start with:

```java
class BankAccount {

    private double amt;
    private int pin;
}
```

Now add a deposit method:

```java
public void deposit(double amount) {

    amt = amt + amount;
}
```

Now add withdrawal:

```java
public void withdraw(double amount, int enteredPin) {

    if (enteredPin == pin) {
        amt = amt - amount;
    }
}
```

And balance:

```java
public void checkBalance(int enteredPin) {

    if (enteredPin == pin) {
        System.out.println(amt);
    }
}
```

Now the class controls how its data is used.

---

# 9. Complete Beginner Program

```java
class BankAccount {

    private double amt;
    private int pin;

    public void deposit(double amount) {

        if (amount > 0) {
            amt = amt + amount;
            System.out.println("Amount deposited");
        }
    }

    public void withdraw(double amount, int enteredPin) {

        if (enteredPin != pin) {
            System.out.println("Invalid PIN");
            return;
        }

        if (amount > amt) {
            System.out.println("Insufficient balance");
            return;
        }

        amt = amt - amount;

        System.out.println("Amount withdrawn");
    }

    public void checkBalance(int enteredPin) {

        if (enteredPin == pin) {
            System.out.println("Balance = " + amt);
        } else {
            System.out.println("Invalid PIN");
        }
    }
}
```

But there is one problem:

**How do we initially give `amt` and `pin` their values?**

That's where the **constructor** comes in.

---

# 10. Constructor Enters the Picture

We'll create:

```java
BankAccount(double amt, int pin)
```

Full constructor:

```java
BankAccount(double amt, int pin) {

    this.amt = amt;
    this.pin = pin;
}
```

Now we can create:

```java
BankAccount account =
        new BankAccount(10000, 1234);
```

Meaning:

```text
Initial amount = 10000
Initial PIN    = 1234
```

---

# 11. 🔥 Why Do We Need `this`?

This is one of the most important points.

We have:

```java
private double amt;
```

and:

```java
BankAccount(double amt, int pin)
```

There are two `amt`s.

```text
this.amt
   ↓
Object's instance variable

amt
 ↓
Constructor parameter
```

Therefore:

```java
this.amt = amt;
```

means:

> Put the constructor parameter `amt` into the current object's `amt`.

Similarly:

```java
this.pin = pin;
```

means:

> Put the constructor parameter `pin` into the current object's `pin`.

---

# 12. Think of `this` Like "My"

When we say:

```java
this.amt
```

you can mentally read it as:

> **my amount**

So:

```java
this.amt = amt;
```

can be mentally understood as:

> My amount = the amount supplied by the caller.

This makes `this` much easier to understand.

---

# 13. Complete Encapsulation Program

Now let's put everything together.

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

Output:

```text
Balance = 10000.0
Amount deposited
Amount withdrawn
Balance = 12000.0
Invalid PIN
```

---

# 14. Let's Act Like the Java Program

Initially:

```text
amt = 10000
pin = 1234
```

Then:

```java
account.deposit(5000);
```

becomes:

```text
10000 + 5000
     ↓
15000
```

Then:

```java
account.withdraw(3000, 1234);
```

PIN is correct:

```text
15000 - 3000
     ↓
12000
```

Then:

```java
account.checkBalance(1234);
```

prints:

```text
Balance = 12000
```

---

# 15. What Can the Outside World Do?

It **cannot** do:

```java
account.amt = 999999;
```

❌ Not allowed.

It **can** do:

```java
account.deposit(5000);
```

✅ Allowed.

It **can** do:

```java
account.withdraw(2000, 1234);
```

✅ Allowed.

It **can** do:

```java
account.checkBalance(1234);
```

✅ Allowed.

So the class says:

> "You cannot directly touch my internal data, but you can ask me to perform operations that I allow."

That's encapsulation.

---

# 16. Why Is This Better?

Suppose someone asks:

> Why can't I directly change `amt`?

Because the bank account needs to enforce rules.

For example:

```text
Deposit must be positive
Withdrawal must be positive
PIN must match
Withdrawal cannot exceed balance
```

If `amt` were public, outside code could bypass all these rules.

With encapsulation:

```text
                   BankAccount
                        |
                ┌───────┴───────┐
                ↓               ↓
             Private          Rules
              data              |
                ↓               ↓
               amt       deposit()
                         withdraw()
                         checkBalance()
```

The object protects its own state.

---

# 17. 🔥 What Does "Data Hiding" Mean?

When we write:

```java
private double amt;
private int pin;
```

the internal fields are hidden from direct access outside the class.

This is called **data hiding**.

But:

```text
Data hiding ≠ complete definition of encapsulation
```

Encapsulation is broader.

```text
Encapsulation
    ↓
Bundle state + behavior
    +
Control access to internal state
```

---

# 18. Encapsulation vs Data Hiding

| Encapsulation                             | Data Hiding                              |
| ----------------------------------------- | ---------------------------------------- |
| Broader concept                           | More specific concept                    |
| Bundles state and behavior                | Restricts direct access to internal data |
| Controls how object is interacted with    | Protects implementation/state            |
| Commonly implemented using access control | `private` is a common mechanism          |

Simple memory:

> **Data hiding is one important part of encapsulation.**

---

# 19. Encapsulation vs Abstraction

Don't confuse these.

### Encapsulation

Question:

> **How do I control access to the object's internal state?**

Example:

```java
private double amt;
```

### Abstraction

Question:

> **What details should I expose and what implementation details can I hide?**

Example:

```java
withdraw()
```

The caller can use `withdraw()` without needing to know every internal implementation detail.

### Memory trick:

```text
Encapsulation → CONTROL
Abstraction   → HIDE
```

---

# 20. Do We Always Need Getters and Setters?

No.

Beginners often learn:

```java
private int x;

public int getX() {
    return x;
}

public void setX(int x) {
    this.x = x;
}
```

This is a common encapsulation pattern.

But you shouldn't automatically create unrestricted setters for every field.

For a bank account, this would be questionable:

```java
public void setAmt(double amt) {
    this.amt = amt;
}
```

Why?

Because then someone could do:

```java
account.setAmt(-500000);
```

Instead, domain-specific methods are better:

```java
deposit()
withdraw()
```

These express valid operations.

---

# 21. 🔥 Think "Ask, Don't Grab"

Bad design:

```java
account.amt = account.amt - 5000;
```

The outside code is grabbing and manipulating internal state.

Better:

```java
account.withdraw(5000, 1234);
```

The outside code **asks the object** to perform an operation.

That's a powerful object-oriented design idea:

> **Don't expose your internal state unnecessarily; expose meaningful behavior.**

---

# 22. Now Understand the Constructor

Consider:

```java
BankAccount account =
        new BankAccount(10000, 1234);
```

Three important things happen conceptually:

```text
new
 ↓
creates object
 ↓
constructor initializes object
 ↓
reference stored in account
```

So:

```text
account
   |
   ↓
BankAccount Object
   |
   ├── amt = 10000
   └── pin = 1234
```

---

# 23. What If We Don't Write a Constructor?

Consider:

```java
class BankAccount {

    private double amt;
    private int pin;
}
```

There is no constructor written.

Java provides a **default no-argument constructor**.

So:

```java
BankAccount account =
        new BankAccount();
```

is valid.

The fields get their default values:

```text
double amt → 0.0
int pin    → 0
```

---

# 24. Default Constructor Program

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

Why?

Because Java initializes instance variables with their default values when no explicit initialization has occurred.

---

# 25. 🔥 Very Important Constructor Rule

Suppose:

```java
class BankAccount {

    BankAccount(double amt, int pin) {
    }
}
```

Now try:

```java
BankAccount b =
        new BankAccount();
```

❌ Compilation error.

Why?

Because once you explicitly declare a constructor, Java does **not** automatically add the no-argument default constructor.

If you want it, write it:

```java
class BankAccount {

    BankAccount() {
    }

    BankAccount(double amt, int pin) {
    }
}
```

Now both are valid:

```java
new BankAccount();

new BankAccount(10000, 1234);
```

---

# 26. Default Class vs Default Constructor

Another common confusion.

### This:

```java
class BankAccount {
}
```

means the class has **package-private/default access** if no access modifier is specified.

### This:

```java
BankAccount() {
}
```

is a **no-argument constructor**.

They are not the same thing.

```text
Default/package-private class
            ≠
Default constructor
```

---

# 27. Quick Quiz 🧠

### Question 1

Which is better?

```java
account.amt = 5000;
```

or:

```java
account.deposit(5000);
```

### Answer:

```java
account.deposit(5000);
```

because the object controls the operation.

---

### Question 2

Why is:

```java
private double amt;
```

used?

### Answer:

To prevent direct access from outside the declaring class and allow the class to control how its state is accessed or modified.

---

### Question 3

Why:

```java
this.amt = amt;
```

?

### Answer:

Because `this.amt` refers to the object's instance variable, while `amt` refers to the constructor parameter.

---

### Question 4

Is `private` alone enough to demonstrate complete encapsulation?

### Answer:

**No.**

A useful encapsulated class normally provides an appropriate controlled interface.

---

### Question 5

Does a constructor itself mean encapsulation?

### Answer:

**No.**

The constructor initializes an object. Encapsulation is the broader concept of bundling state/behavior and controlling access.

---

# 28. 🎯 The Entire Concept in One Picture

```text
                    BANK ACCOUNT
                         |
          ┌──────────────┴──────────────┐
          ↓                             ↓
      PRIVATE DATA                 PUBLIC METHODS
          ↓                             ↓
     private amt                  deposit()
     private pin                  withdraw()
                                  checkBalance()
          |                             |
          |                      ┌──────┴──────┐
          |                      ↓             ↓
          |                 PIN validation   Amount validation
          |                      ↓             ↓
          └──────────────────────┴─────────────┘
                                 ↓
                         Controlled Access
```

---

# 29. 🧠 Remember It Like a Real Bank

Think about an ATM.

You don't walk into the bank's database and say:

```text
"Give me direct access to my balance variable."
```

Instead, you interact through an interface:

```text
Insert card
   ↓
Enter PIN
   ↓
Choose operation
   ↓
Withdraw / Check Balance
```

The bank's internal state remains controlled.

Java encapsulation follows the same fundamental idea:

```text
Object's internal data
        ↓
Protected
        ↓
Public interface
        ↓
Controlled operation
```

---

# 🔥 FINAL TEACHME SUMMARY

If you remember only this, remember:

### 1️⃣ Without security

```java
double amt;
int pin;
```

Anyone can directly manipulate the data.

### 2️⃣ Over-security

```java
private double amt;
private int pin;
```

with no useful methods means legitimate operations aren't available.

### 3️⃣ Encapsulation

```java
private double amt;
private int pin;

public void deposit(...)
public void withdraw(...)
public void checkBalance(...)
```

Now the data is protected **and** legitimate operations are available.

### 4️⃣ Constructor

```java
BankAccount(double amt, int pin) {
    this.amt = amt;
    this.pin = pin;
}
```

Initializes the object's state.

### 5️⃣ `this`

```java
this.amt
```

means:

> **the current object's `amt`.**

### 6️⃣ The golden formula

```text
       ENCAPSULATION
             =
   Private State
       +
 Controlled Interface
       +
 Validation/Rules
```

### 7️⃣ One-line exam answer

> **Encapsulation in Java is the process of bundling data and the methods that operate on that data inside a class while restricting direct access to the internal state and providing controlled access through appropriate methods.**
