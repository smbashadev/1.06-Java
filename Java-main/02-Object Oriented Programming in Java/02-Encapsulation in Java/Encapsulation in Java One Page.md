# Encapsulation in Java — ONEPAGE

## 1. What is Encapsulation?

**Encapsulation** means **binding data and the methods that operate on that data into a single class, while controlling access to the data**.

In Java, encapsulation is commonly achieved using:

```text
private data
   +
public/protected methods
```

The outside world should not directly manipulate sensitive data. Instead, it should use controlled methods.

### Real-world example: Bank Account

A bank account contains sensitive information such as:

```text
Amount
PIN
```

We don't want anyone to directly change or read these values.

So:

```java
private double amt;
private int pin;
```

and provide controlled methods such as:

```java
withdraw()
deposit()
checkBalance()
```

---

# 2. Bank Account — Security Problem ❌

Suppose we create a bank account like this:

```java
class BankAccount {

    double amt;
    int pin;
}
```

Now outside code can directly access the data:

```java
class Test {

    public static void main(String[] args) {

        BankAccount b = new BankAccount();

        b.amt = 100000;
        b.pin = 1234;

        System.out.println(b.amt);
        System.out.println(b.pin);
    }
}
```

### Problem

Anyone who has access to the object can directly do:

```java
b.amt = 1000000;
b.pin = 9999;
```

There is **no control** over how the data is accessed or modified.

This is a **security/design problem**.

---

# 3. Over-Security Problem ❌

Now imagine we make everything inaccessible:

```java
class BankAccount {

    private double amt;
    private int pin;
}
```

Outside the class:

```java
class Test {

    public static void main(String[] args) {

        BankAccount b = new BankAccount();

        b.amt = 10000;  // ERROR
        b.pin = 1234;   // ERROR
    }
}
```

Why?

Because:

```text
private
   ↓
accessible only inside the declaring class
```

Now the data is protected—but there is **no controlled way for legitimate users to access the required functionality**.

This is the **over-security problem**.

---

# 4. 🔥 Solution — Encapsulation

We want a balance between:

```text
No Security       ❌
       ↓
Over Security     ❌
       ↓
Controlled Access ✅
```

So we make the sensitive variables:

```java
private double amt;
private int pin;
```

and provide **public methods** that perform controlled operations.

```text
Outside
   ↓
Public Method
   ↓
Validation / Security Check
   ↓
Private Data
```

---

# 5. Complete Bank Account Encapsulation Program

```java
class BankAccount {

    private double amt;
    private int pin;

    // Constructor
    BankAccount(double amt, int pin) {
        this.amt = amt;
        this.pin = pin;
    }

    // Deposit money
    public void deposit(double amount) {

        if (amount > 0) {
            amt = amt + amount;
            System.out.println("Amount deposited successfully.");
        } else {
            System.out.println("Invalid amount.");
        }
    }

    // Withdraw money
    public void withdraw(double amount, int enteredPin) {

        if (enteredPin != pin) {
            System.out.println("Invalid PIN.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount.");
        }
        else if (amount > amt) {
            System.out.println("Insufficient balance.");
        }
        else {
            amt = amt - amount;
            System.out.println("Amount withdrawn successfully.");
        }
    }

    // Check balance
    public void checkBalance(int enteredPin) {

        if (enteredPin == pin) {
            System.out.println("Current Balance = " + amt);
        }
        else {
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
    }
}
```

### Output

```text
Current Balance = 10000.0
Amount deposited successfully.
Amount withdrawn successfully.
Current Balance = 12000.0
Invalid PIN.
```

---

# 6. 🔍 How Is Encapsulation Working Here?

Look at:

```java
private double amt;
private int pin;
```

These cannot be directly accessed from outside `BankAccount`.

For example, this is illegal:

```java
account.amt = 500000;
```

and:

```java
account.pin = 9999;
```

Instead, the user must go through:

```java
account.deposit(5000);
account.withdraw(3000, 1234);
account.checkBalance(1234);
```

The class controls what is allowed.

### Therefore:

```text
private variables
        +
controlled public methods
        =
Encapsulation
```

---

# 7. Why Use a Constructor?

Our constructor is:

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

the constructor initializes the private data.

Conceptually:

```text
new BankAccount(10000, 1234)
              ↓
       Constructor called
              ↓
       amt = 10000
       pin = 1234
```

---

# 8. Why Is `this` Used?

Look carefully:

```java
BankAccount(double amt, int pin) {

    this.amt = amt;
    this.pin = pin;
}
```

There are two `amt`s:

```text
this.amt
   ↓
instance variable

amt
 ↓
constructor parameter
```

Similarly:

```text
this.pin
   ↓
instance variable

pin
 ↓
constructor parameter
```

Therefore:

```java
this.amt = amt;
```

means:

> Store the constructor parameter `amt` into the object's instance variable `amt`.

This avoids **shadowing confusion**.

---

# 9. 🔥 Accessing Money Safely

We don't allow:

```java
account.amt = account.amt - 5000;
```

Instead:

```java
account.withdraw(5000, 1234);
```

The method can check:

```text
Is PIN correct?
        ↓
Is amount valid?
        ↓
Is sufficient balance available?
        ↓
Perform withdrawal
```

This is much safer and gives the class control over its own state.

---

# 10. Check Balance Safely

Instead of:

```java
System.out.println(account.amt);
```

we use:

```java
account.checkBalance(1234);
```

The method verifies the PIN first.

```java
public void checkBalance(int enteredPin) {

    if (enteredPin == pin) {
        System.out.println("Current Balance = " + amt);
    }
    else {
        System.out.println("Invalid PIN.");
    }
}
```

So the balance is **not directly exposed as a public variable**.

---

# 11. 🔥 Security vs Over-Security vs Encapsulation

| Approach      | Code                                  | Problem                                |
| ------------- | ------------------------------------- | -------------------------------------- |
| No security   | `double amt;`                         | Anyone can directly modify data        |
| Over-security | `private double amt;` with no methods | Even legitimate operations are blocked |
| Encapsulation | `private` + controlled public methods | Protected and controlled access        |

The goal is:

> **Don't expose sensitive data directly. Don't block legitimate operations. Provide controlled access.**

---

# 12. Default Class and Default Constructor

Now an important Java concept.

Consider:

```java
class BankAccount {

    private double amt;
    private int pin;
}
```

There is:

* No explicit constructor
* No parameterized constructor
* No constructor written by the programmer

Java automatically provides a **default constructor** in this situation.

Conceptually:

```java
BankAccount() {
}
```

So:

```java
BankAccount account = new BankAccount();
```

is valid.

---

# 13. Example — Default Constructor Creation

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

Because instance variables receive their default values:

```text
double → 0.0
int    → 0
```

---

# 14. 🔥 Important: Default Constructor vs Default Class

These are **different concepts**.

### Default constructor

A constructor with no parameters, often the **compiler-provided constructor** when you declare no constructor yourself.

Example conceptually:

```java
BankAccount() {
}
```

### Default/package-private class

If you write:

```java
class BankAccount {
}
```

without `public`, the class has **package-private (default) access**.

It can generally be accessed by classes in the same package.

So don't confuse:

```text
default class
     ≠
default constructor
```

---

# 15. ⚠️ Very Important Constructor Rule

Suppose you write:

```java
class BankAccount {

    private double amt;
    private int pin;

    BankAccount(double amt, int pin) {
        this.amt = amt;
        this.pin = pin;
    }
}
```

Now Java **does not automatically provide** a no-argument default constructor.

Therefore:

```java
BankAccount b = new BankAccount();
```

will produce a compilation error.

If you want both:

```java
BankAccount()
BankAccount(double, int)
```

you must explicitly define both.

Example:

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

---

# 🧠 ONE-PAGE FINAL MEMORY

```text
                 ENCAPSULATION
                       |
          ┌────────────┴────────────┐
          ↓                         ↓
     Private Data            Public Methods
          ↓                         ↓
      amt + pin          deposit/withdraw/
                         checkBalance
          └──────────────┬──────────┘
                         ↓
                  CONTROLLED ACCESS
```

### Bank example:

```java
private double amt;
private int pin;
```

❌ Don't allow:

```java
account.amt = 50000;
account.pin = 9999;
```

✅ Instead provide:

```java
account.deposit(5000);
account.withdraw(2000, 1234);
account.checkBalance(1234);
```

### Remember these 5 points:

1. **Encapsulation = bundling + controlled access.**
2. Use **`private`** for sensitive instance variables.
3. Use **public methods** to provide controlled operations.
4. A **constructor** can initialize the private state.
5. If no constructor is declared, Java provides a **no-argument default constructor**; once you declare any constructor, the compiler does not add that default constructor for you.
