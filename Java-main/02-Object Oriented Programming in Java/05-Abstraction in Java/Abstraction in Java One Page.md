# Abstraction in Java — ONEPAGE

## 1. What is Abstraction?

> **Abstraction is the process of hiding implementation details and showing only the essential functionality to the user.**

### Real-life example

When you use an ATM:

```text
You know:
→ Insert card
→ Enter PIN
→ Select amount
→ Withdraw money

You don't need to know:
→ How the bank server processes the request
→ How the ATM communicates with the bank
→ How the cash mechanism works
```

So:

```text
User
 ↓
Essential functionality
 ↓
Hidden implementation
```

That's **Abstraction**.

---

# 2. Abstraction in Java

Java mainly provides **two ways to achieve abstraction**:

```text
                 ABSTRACTION
                      |
             ┌────────┴────────┐
             ↓                 ↓
      Abstract Class        Interface
```

---

# 3. Abstract Class

A class declared using the `abstract` keyword is called an **abstract class**.

```java
abstract class Robo
{
    abstract void move();

    void recharge()
    {
        System.out.println("Robo is recharging");
    }
}
```

Here:

```text
move()
   ↓
Abstract method
   ↓
No implementation
```

while:

```text
recharge()
   ↓
Concrete method
   ↓
Has implementation
```

An abstract class can therefore contain:

* Abstract methods
* Concrete methods
* Variables
* Constructors
* Static methods
* Other members allowed by normal class rules

---

# 4. Abstract Method

An abstract method is a method declared without a body.

```java
abstract void move();
```

Notice:

```text
❌ No method body
❌ No { }
✅ Ends with ;
```

The child class generally provides the implementation.

---

# 5. Complete Abstract Class Program

```java
abstract class Robo
{
    abstract void move();

    void recharge()
    {
        System.out.println("Robo is recharging");
    }
}

class FighterRobo extends Robo
{
    void move()
    {
        System.out.println("Fighter Robo moves");
    }
}

class Test
{
    public static void main(String[] args)
    {
        FighterRobo f = new FighterRobo();

        f.move();
        f.recharge();
    }
}
```

Output:

```text
Fighter Robo moves
Robo is recharging
```

The parent defines **what operation is required**:

```java
abstract void move();
```

The child defines **how the operation is performed**:

```java
void move()
{
    System.out.println("Fighter Robo moves");
}
```

---

# 6. Can We Create an Object of an Abstract Class?

### ❌ No.

This is invalid:

```java
Robo r = new Robo();
```

because `Robo` is abstract.

But we can create a child object:

```java
Robo r = new FighterRobo();
```

This is valid.

It also demonstrates the combination of:

```text
Abstraction
+
Inheritance
+
Polymorphism
```

---

# 7. Abstract Class Reference

We can write:

```java
Robo r = new FighterRobo();

r.move();
r.recharge();
```

Here:

```text
Reference type → Robo
Actual object  → FighterRobo
```

The abstract class provides the common contract, while the concrete child provides the implementation of the abstract method.

---

# 8. Can Abstract Class Have Constructors?

### ✅ Yes.

Example:

```java
abstract class Robo
{
    Robo()
    {
        System.out.println("Robo constructor");
    }

    abstract void move();
}
```

Even though we cannot directly create:

```java
new Robo();
```

the constructor can execute when a child object is created:

```java
new FighterRobo();
```

---

# 9. Can Abstract Class Have Concrete Methods?

### ✅ Yes.

```java
abstract class Robo
{
    abstract void move();

    void recharge()
    {
        System.out.println("Recharge");
    }
}
```

Therefore:

```text
Abstract class
│
├── Abstract methods
├── Concrete methods
├── Variables
├── Constructors
└── Other class members
```

---

# 10. Interface

An interface is another major mechanism for abstraction.

Example:

```java
interface Robo
{
    void move();
}
```

A class can implement it:

```java
class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter Robo moves");
    }
}
```

Now:

```java
Robo r = new FighterRobo();

r.move();
```

Output:

```text
Fighter Robo moves
```

---

# 11. Abstract Class vs Interface

| Abstract Class                  | Interface                                    |
| ------------------------------- | -------------------------------------------- |
| Declared using `abstract class` | Declared using `interface`                   |
| Class uses `extends`            | Class uses `implements`                      |
| Can have abstract methods       | Can declare abstract methods                 |
| Can have concrete methods       | Can have concrete/default/static methods     |
| Can have instance variables     | Fields are constants (`public static final`) |
| Can have constructors           | Cannot have constructors                     |
| A class can extend one class    | A class can implement multiple interfaces    |

---

# 12. Abstraction vs Encapsulation

These are frequently confused.

### Abstraction

> **Hides implementation details and exposes essential functionality.**

Example:

```text
ATM
 ↓
Withdraw money()
 ↓
Bank processing hidden
```

### Encapsulation

> **Bundles data and methods together and controls access to the data.**

Example:

```java
class Bank
{
    private int balance;

    public int getBalance()
    {
        return balance;
    }
}
```

Memory trick:

```text
Abstraction
→ WHAT should be done?

Encapsulation
→ HOW do we protect/control the data?
```

---

# 13. Abstraction vs Polymorphism

### Abstraction

Focuses on:

```text
What should be exposed?
What should be hidden?
```

### Polymorphism

Focuses on:

```text
How can the same operation behave differently?
```

Example:

```java
Robo r = new FighterRobo();
r.move();
```

The abstract `move()` requirement represents abstraction.

The child's different `move()` implementation and runtime selection demonstrate polymorphism.

---

# 14. Why Do We Need Abstraction?

Abstraction helps us:

* Hide unnecessary implementation details
* Expose only essential operations
* Reduce complexity
* Define common contracts
* Improve maintainability
* Allow different implementations
* Support loose coupling

---

# 🧠 ONEPAGE MEMORY MAP

```text
                         ABSTRACTION
                              |
              Hide implementation details
                              |
                  Show essential features
                              |
               ┌──────────────┴──────────────┐
               ↓                             ↓
        ABSTRACT CLASS                  INTERFACE
               |                             |
        abstract method               method declaration
               |                             |
        Child implements              Class implements
               |                             |
               └──────────────┬──────────────┘
                              ↓
                     Concrete implementation
```

### ⭐ Final definition

> **Abstraction in Java is the process of hiding implementation details and exposing only the essential operations, mainly achieved using abstract classes and interfaces.**

### ⭐ Remember these four points

```text
1. Abstract class → cannot be directly instantiated.

2. Abstract method → declared without a body.

3. Child/concrete class → provides implementation
   of inherited abstract methods.

4. Interface → another major mechanism for abstraction.
```
