# Abstraction in Java — 3LEVEL

The **3LEVEL method** means learning the same concept at three depths:

```text
LEVEL 1 → Basic understanding
LEVEL 2 → Programming understanding
LEVEL 3 → Interview + deep understanding
```

---

# 🟢 LEVEL 1 — BASIC

## 1. What is Abstraction?

> **Abstraction means hiding implementation details and showing only the essential functionality.**

### Real-life example

When you use an ATM:

```text
You see:
    ↓
Withdraw
Deposit
Check Balance

You don't see:
    ↓
Bank-server processing
Transaction verification
Internal calculations
```

You know **WHAT** the ATM does, but you don't need to know **HOW** it does it.

That is abstraction.

---

## 2. Abstraction in Java

Java mainly achieves abstraction using:

```text
                 ABSTRACTION
                      |
             ┌────────┴────────┐
             ↓                 ↓
      Abstract Class       Interface
```

---

## 3. Abstract Class

A class declared using `abstract` is called an abstract class.

```java
abstract class Robo
{
}
```

An abstract class is generally used as a base class.

---

## 4. Abstract Method

A method declared using `abstract` without a body is an abstract method.

```java
abstract void move();
```

It tells the child:

> "You must provide an implementation for `move()`."

---

## 5. Simple Program

```java
abstract class Robo
{
    abstract void move();
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
    }
}
```

Output:

```text
Fighter Robo moves
```

### What happened?

```text
Robo
 ↓
WHAT → move()

FighterRobo
 ↓
HOW → Fighter Robo moves
```

---

# 🟡 LEVEL 2 — PROGRAMMING UNDERSTANDING

## 1. Abstract Class Can Have Abstract + Concrete Methods

An abstract class does **not** contain only abstract methods.

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

recharge()
   ↓
Concrete method
```

Child:

```java
class FighterRobo extends Robo
{
    void move()
    {
        System.out.println("Fighter Robo moves");
    }
}
```

Complete program:

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

---

# 2. Can We Create an Abstract Class Object?

### ❌ No

```java
Robo r = new Robo();
```

Invalid.

But we can create an abstract-class reference:

### ✅ Yes

```java
Robo r = new FighterRobo();
```

Here:

```text
Robo
 ↓
Reference type

FighterRobo
 ↓
Actual object
```

Then:

```java
r.move();
```

works.

---

# 3. Why Use an Abstract Reference?

Suppose we have:

```text
                 Robo
                  |
       ┌──────────┼──────────┐
       ↓          ↓          ↓
   Fighter      Player     Teacher
     Robo         Robo        Robo
```

Each Robo can have a different implementation of `move()`.

```java
abstract class Robo
{
    abstract void move();
}

class FighterRobo extends Robo
{
    void move()
    {
        System.out.println("Fighter moves");
    }
}

class PlayerRobo extends Robo
{
    void move()
    {
        System.out.println("Player moves");
    }
}
```

Now:

```java
Robo r;

r = new FighterRobo();
r.move();

r = new PlayerRobo();
r.move();
```

Output:

```text
Fighter moves
Player moves
```

This combines:

```text
Abstraction
     +
Inheritance
     +
Polymorphism
```

---

# 4. Abstract Class Can Have Constructor

### Yes.

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

We cannot create:

```java
new Robo();
```

But the constructor executes while creating a child object.

```java
class FighterRobo extends Robo
{
    FighterRobo()
    {
        System.out.println("Fighter constructor");
    }

    void move()
    {
        System.out.println("Moving");
    }
}
```

```java
class Test
{
    public static void main(String[] args)
    {
        FighterRobo f = new FighterRobo();
    }
}
```

Output:

```text
Robo constructor
Fighter constructor
```

---

# 5. Abstract Class Can Have Variables

```java
abstract class Robo
{
    int battery = 100;

    abstract void move();
}
```

So an abstract class can contain:

```text
✓ Variables
✓ Constructors
✓ Abstract methods
✓ Concrete methods
✓ Static methods
✓ Other valid class members
```

---

# 6. Interface

An interface is another major mechanism for abstraction.

```java
interface Robo
{
    void move();
}
```

Implementation:

```java
class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter Robo moves");
    }
}
```

Usage:

```java
Robo r = new FighterRobo();

r.move();
```

Output:

```text
Fighter Robo moves
```

---

# 🔴 LEVEL 3 — INTERVIEW + DEEP UNDERSTANDING

## 1. The Exact Meaning of Abstraction

Don't simply memorize:

> "Abstraction hides data."

That is incomplete.

A better definition is:

> **Abstraction hides unnecessary implementation details and exposes essential behavior through a suitable abstraction such as an abstract class or interface.**

---

# 2. WHAT vs HOW

This is the most important way to understand abstraction.

```text
                 ABSTRACTION
                     |
             ┌───────┴───────┐
             ↓               ↓
            WHAT             HOW
             |               |
       Exposed/defined     Hidden
             |               |
       move() required    Implementation
```

Example:

```java
abstract void move();
```

The parent says:

```text
WHAT?
→ Robo must move.
```

The child says:

```text
HOW?
→ This is how Fighter Robo moves.
```

---

# 3. Can Abstract Class Have Zero Abstract Methods?

### ✅ Yes.

```java
abstract class Robo
{
    void move()
    {
        System.out.println("Moving");
    }
}
```

The class is still abstract.

Therefore:

> **An abstract class does not necessarily need an abstract method.**

---

# 4. Can Abstract Class Have Static Methods?

### ✅ Yes.

```java
abstract class Robo
{
    static void show()
    {
        System.out.println("Static method");
    }
}
```

But:

```java
abstract static void move();
```

### ❌ Invalid

An abstract method cannot be static.

---

# 5. Can Abstract Method Be `final`?

### ❌ No.

```java
abstract final void move();
```

Why?

```text
abstract → implementation must come from subclass
final    → cannot be overridden
```

These requirements conflict.

---

# 6. Can Abstract Method Be `private`?

### ❌ No.

```java
private abstract void move();
```

A private method isn't available to subclasses for implementation/overriding.

---

# 7. Can Abstract Class Be `final`?

### ❌ No.

```java
abstract final class Robo
{
}
```

Why?

```text
abstract → intended for inheritance
final    → cannot be inherited
```

Contradiction.

---

# 8. Can Abstract Class Extend Another Abstract Class?

### ✅ Yes.

```java
abstract class Robo
{
    abstract void move();
}

abstract class BattleRobo extends Robo
{
    abstract void fight();
}

class FighterRobo extends BattleRobo
{
    void move()
    {
        System.out.println("Moving");
    }

    void fight()
    {
        System.out.println("Fighting");
    }
}
```

Hierarchy:

```text
Robo
 ↓
BattleRobo
 ↓
FighterRobo
```

The final concrete class implements all required abstract methods.

---

# 9. Can Abstract Class Implement an Interface?

### ✅ Yes.

```java
interface Robo
{
    void move();
    void recharge();
}

abstract class BattleRobo implements Robo
{
}
```

Because `BattleRobo` is abstract, it can leave the methods unimplemented.

A concrete subclass must eventually provide the required implementations.

---

# 10. Abstract Class vs Interface

| Feature                 | Abstract Class   | Interface                              |
| ----------------------- | ---------------- | -------------------------------------- |
| Declaration             | `abstract class` | `interface`                            |
| Direct object           | ❌                | ❌                                      |
| Abstract methods        | ✅                | ✅                                      |
| Concrete methods        | ✅                | ✅ through allowed method forms         |
| Instance variables      | ✅                | ❌                                      |
| Constants               | Can have them    | Fields are `public static final`       |
| Constructor             | ✅                | ❌                                      |
| Static methods          | ✅                | ✅                                      |
| Multiple implementation | One superclass   | Multiple interfaces can be implemented |

---

# 11. Abstraction vs Encapsulation

### Abstraction

```text
Hide implementation complexity
        ↓
Expose essential behavior
```

### Encapsulation

```text
Bundle data + methods
        ↓
Control access to data
```

Memory trick:

```text
ABSTRACTION
→ WHAT?

ENCAPSULATION
→ HOW IS DATA PROTECTED?
```

---

# 12. Abstraction vs Inheritance

### Inheritance

```text
FighterRobo
     ↓
    Robo
```

It establishes an inheritance relationship.

### Abstraction

```text
Robo
 ↓
abstract void move();
```

It defines required behavior without providing that implementation there.

They are different concepts, but abstract classes commonly use inheritance.

---

# 13. Abstraction vs Polymorphism

### Abstraction

Defines:

```text
WHAT should be done?
```

Example:

```java
abstract void move();
```

### Polymorphism

Allows:

```text
Different objects
+
Same reference/interface
+
Different behavior
```

Example:

```java
Robo r = new FighterRobo();
r.move();
```

---

# 14. The Complete Relationship

```text
                    OOP
                     |
       ┌─────────────┼─────────────┐
       ↓             ↓             ↓
 Abstraction     Inheritance   Polymorphism
       |
       ↓
Hide implementation
       |
       ↓
Abstract class / Interface
       |
       ↓
Common contract
       |
       ↓
Concrete implementations
```

---

# 🧠 3LEVEL FINAL REVISION

## 🟢 Level 1 — Remember

```text
Abstraction
→ Hide implementation details
→ Show essential functionality

Main Java mechanisms:
→ Abstract class
→ Interface
```

---

## 🟡 Level 2 — Understand

```text
abstract class Robo
{
    abstract void move();
}
```

means:

```text
Robo says:
"Every concrete Robo must have move()."

FighterRobo says:
"Here is how I implement move()."
```

Also remember:

```text
Abstract class object      ❌
Abstract class reference   ✅
Abstract class constructor ✅
Concrete methods           ✅
Variables                  ✅
```

---

## 🔴 Level 3 — Master

Remember these interview rules:

```text
abstract class + abstract method     ✅
abstract class + concrete method    ✅
abstract class + constructor        ✅
abstract class + variables          ✅
abstract class + static method      ✅
abstract class + zero abstract methods ✅

abstract method + final             ❌
abstract method + static            ❌
abstract method + private           ❌
abstract class + final              ❌
```

### ⭐ Golden Rule

> **Abstraction is about WHAT, not HOW.**

```text
abstract void move();
        ↓
       WHAT

FighterRobo's move() body
        ↓
       HOW
```

That single distinction is the key to understanding **Abstraction in Java**.
