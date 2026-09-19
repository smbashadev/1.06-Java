# Interface in Java — 3 LEVEL

The **3 LEVEL method** means we learn the same concept at three depths:

```text
LEVEL 1 → Basic understanding
LEVEL 2 → Programming + internal understanding
LEVEL 3 → Advanced + interview understanding
```

---

# 🟢 LEVEL 1 — BASIC

## 1. What Is an Interface?

> **An interface is a reference type in Java that defines a contract that implementing classes must fulfill.**

Example:

```java
interface Robo
{
    void move();
}
```

Here `Robo` is an interface.

It says:

> Any class implementing `Robo` must provide `move()`.

---

## 2. Implementing an Interface

```java
interface Robo
{
    void move();
}

class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter Robo moves");
    }
}
```

The important keyword is:

```java
implements
```

So:

```text
Class → implements → Interface
```

---

## 3. Complete Basic Program

```java
interface Robo
{
    void move();
}

class FighterRobo implements Robo
{
    public void move()
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

---

## 4. Interface = Contract

Think of:

```java
interface Robo
{
    void move();
}
```

as a rule:

```text
Robo says:
"Every Robo must have move()."
```

The interface tells:

```text
WHAT to do
```

The implementing class tells:

```text
HOW to do it
```

---

## 5. Can We Create an Interface Object?

❌ No.

```java
Robo r = new Robo();
```

Invalid.

But we can create an interface reference:

```java
Robo r = new FighterRobo();
```

Here:

```text
Robo
 ↓
Reference

FighterRobo
 ↓
Object
```

---

## 6. Interface Reference

```java
interface Robo
{
    void move();
}

class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter moves");
    }
}

class PlayerRobo implements Robo
{
    public void move()
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

This demonstrates **runtime polymorphism**.

---

## 7. Interface Variables

```java
interface Robo
{
    int MAX_BATTERY = 100;
}
```

The variable is automatically:

```text
public static final
```

So:

```java
System.out.println(Robo.MAX_BATTERY);
```

works.

But:

```java
Robo.MAX_BATTERY = 200;
```

❌ Invalid because it is `final`.

---

## 8. Constructor in Interface

❌ An interface does not have a constructor.

```text
Interface
   ↓
Cannot directly create object
   ↓
No constructor
```

---

# 🟡 LEVEL 2 — PROGRAMMING + CONCEPTUAL DEPTH

## 9. Interface With Multiple Methods

Using our Robo example:

```java
interface Robo
{
    void move();
    void learn();
    void recharge();
    void interact();
    void fight();
}
```

A class implementing it must implement all abstract methods:

```java
class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter moves");
    }

    public void learn()
    {
        System.out.println("Fighter learns");
    }

    public void recharge()
    {
        System.out.println("Fighter recharges");
    }

    public void interact()
    {
        System.out.println("Fighter interacts");
    }

    public void fight()
    {
        System.out.println("Fighter fights");
    }
}
```

---

# 10. Why `public`?

This:

```java
interface Robo
{
    void move();
}
```

is effectively:

```java
public abstract void move();
```

Therefore:

```java
class FighterRobo implements Robo
{
    public void move()
    {
    }
}
```

must use `public`.

---

# 11. Multiple Interfaces

A class can implement multiple interfaces.

```java
interface Movable
{
    void move();
}

interface Fightable
{
    void fight();
}

class FighterRobo implements Movable, Fightable
{
    public void move()
    {
        System.out.println("Moving");
    }

    public void fight()
    {
        System.out.println("Fighting");
    }
}
```

Diagram:

```text
          FighterRobo
           /       \
          ↓         ↓
      Movable    Fightable
```

This is one of the major advantages of interfaces.

---

# 12. Why Multiple Interfaces?

Java does not permit a class to directly inherit from multiple classes:

```java
class C extends A, B
```

❌ Invalid.

But:

```java
class C implements A, B
```

✅ Valid when `A` and `B` are interfaces.

---

# 13. Interface Extending Interface

An interface can extend another interface.

```java
interface Robo
{
    void move();
}

interface Fighter extends Robo
{
    void fight();
}
```

Now:

```java
class FighterRobo implements Fighter
{
    public void move()
    {
        System.out.println("Moving");
    }

    public void fight()
    {
        System.out.println("Fighting");
    }
}
```

Remember:

```text
Interface → extends → Interface
```

---

# 14. Multiple Interface Inheritance

An interface can extend multiple interfaces:

```java
interface Movable
{
    void move();
}

interface Fightable
{
    void fight();
}

interface BattleRobo extends Movable, Fightable
{
}
```

Then:

```java
class FighterRobo implements BattleRobo
{
    public void move()
    {
        System.out.println("Moving");
    }

    public void fight()
    {
        System.out.println("Fighting");
    }
}
```

---

# 15. Class + Interface Together

A class can extend one class and implement multiple interfaces:

```java
class Machine
{
    void recharge()
    {
        System.out.println("Recharging");
    }
}

interface Movable
{
    void move();
}

interface Fightable
{
    void fight();
}

class FighterRobo extends Machine implements Movable, Fightable
{
    public void move()
    {
        System.out.println("Moving");
    }

    public void fight()
    {
        System.out.println("Fighting");
    }
}
```

Remember:

```text
class → extends → one class

class → implements → multiple interfaces
```

---

# 16. Default Method

Modern Java interfaces can contain a `default` method.

```java
interface Robo
{
    void move();

    default void recharge()
    {
        System.out.println("Robo is recharging");
    }
}
```

Now:

```java
class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter moves");
    }
}
```

The class automatically gets the default implementation of `recharge()`.

```java
FighterRobo f = new FighterRobo();

f.move();
f.recharge();
```

Output:

```text
Fighter moves
Robo is recharging
```

---

# 17. Can Default Method Be Overridden?

✅ Yes.

```java
interface Robo
{
    default void recharge()
    {
        System.out.println("Normal recharge");
    }
}

class FighterRobo implements Robo
{
    public void recharge()
    {
        System.out.println("Fast recharge");
    }
}
```

Output:

```text
Fast recharge
```

---

# 18. Static Method in Interface

An interface can contain a static method.

```java
interface Robo
{
    static void show()
    {
        System.out.println("Robo interface");
    }
}
```

Call it using:

```java
Robo.show();
```

The static method belongs to the **interface itself**.

---

# 19. Private Method in Interface

Modern Java also supports private interface methods.

```java
interface Robo
{
    default void recharge()
    {
        log();
        System.out.println("Recharging");
    }

    private void log()
    {
        System.out.println("Recharge started");
    }
}
```

The private method is for internal use inside the interface.

---

# 20. Interface and Abstraction

Interface provides a strong way to achieve abstraction.

Example:

```java
interface Payment
{
    void pay();
}
```

The interface doesn't tell us how payment happens.

Different classes can implement it:

```java
class UPI implements Payment
{
    public void pay()
    {
        System.out.println("UPI payment");
    }
}
```

```java
class Card implements Payment
{
    public void pay()
    {
        System.out.println("Card payment");
    }
}
```

The interface gives:

```text
WHAT → pay()
```

Classes provide:

```text
HOW → UPI / Card implementation
```

---

# 21. Interface and Loose Coupling

Consider:

```java
void makePayment(Payment p)
{
    p.pay();
}
```

We can use:

```java
makePayment(new UPI());
```

or:

```java
makePayment(new Card());
```

The method depends on:

```text
Payment
```

rather than a specific class.

This is **loose coupling**.

---

# 🔴 LEVEL 3 — ADVANCED + INTERVIEW

## 22. Diamond Problem

Consider:

```text
          A
         / \
        B   C
         \ /
          D
```

Suppose:

```java
interface A
{
    default void show()
    {
        System.out.println("A");
    }
}
```

Then:

```java
interface B extends A
{
    default void show()
    {
        System.out.println("B");
    }
}
```

and:

```java
interface C extends A
{
    default void show()
    {
        System.out.println("C");
    }
}
```

Now:

```java
class D implements B, C
{
}
```

Which `show()` should D use?

```text
B.show() ?
C.show() ?
```

This creates a conflict.

---

# 23. Solving the Diamond Problem

The class can resolve the conflict:

```java
class D implements B, C
{
    public void show()
    {
        System.out.println("D");
    }
}
```

Now there is no ambiguity.

The class's implementation wins.

We can also explicitly choose an inherited default:

```java
class D implements B, C
{
    public void show()
    {
        B.super.show();
    }
}
```

---

# 24. Functional Interface

A functional interface has exactly **one abstract method**.

```java
@FunctionalInterface
interface Calculator
{
    int add(int a, int b);
}
```

Then:

```java
Calculator c = (a, b) -> a + b;

System.out.println(c.add(10, 20));
```

Output:

```text
30
```

Important:

```text
Exactly one abstract method
```

But it can still contain default, static, and private methods.

---

# 25. Marker Interface

A marker interface contains no methods.

```java
interface MyMarker
{
}
```

It provides type-level information.

A famous Java example is:

```text
Serializable
```

Conceptually:

```text
Marker Interface
      ↓
No methods
      ↓
Marks a class for a particular purpose
```

---

# 26. Interface vs Abstract Class

| Interface                               | Abstract Class                  |
| --------------------------------------- | ------------------------------- |
| `interface`                             | `abstract class`                |
| No constructor                          | Can have constructor            |
| Cannot directly create object           | Cannot directly create object   |
| Fields are `public static final`        | Can have instance variables     |
| Abstract methods                        | Abstract methods                |
| Default/static/private methods          | Normal concrete methods         |
| Class can implement multiple interfaces | Class can extend only one class |
| Mainly contract/abstraction             | Common state + behavior         |

---

# 27. Interface Member Summary

Modern interface:

```text
                    INTERFACE
                        |
        ┌───────────────┼────────────────┐
        ↓               ↓                ↓
     Fields          Methods          Nested Types
        |               |
        ↓               |
public static final     |
                        |
          ┌─────────────┼──────────────┐
          ↓             ↓              ↓
      abstract       default        static/private
```

---

# 28. Three Golden Relationships

This should be permanently memorized:

```text
1. Class → extends → Class

2. Class → implements → Interface

3. Interface → extends → Interface
```

Examples:

```java
class Child extends Parent
```

```java
class Fighter implements Robo
```

```java
interface Fighter extends Robo
```

---

# 29. Most Important Rules

### Rule 1

```java
Robo r = new Robo();
```

❌ No interface object.

---

### Rule 2

```java
Robo r = new FighterRobo();
```

✅ Interface reference.

---

### Rule 3

```java
class Fighter implements Robo
```

✅ Class implements interface.

---

### Rule 4

```java
interface Fighter extends Robo
```

✅ Interface extends interface.

---

### Rule 5

```java
class Fighter implements A, B, C
```

✅ Multiple interfaces.

---

### Rule 6

```java
interface C extends A, B
```

✅ Multiple interface inheritance.

---

### Rule 7

Interface variables are:

```text
public static final
```

---

### Rule 8

Interface has no constructor.

---

### Rule 9

Default methods can be overridden.

---

### Rule 10

Interface static methods belong to the interface.

---

# 🧠 FINAL 3-LEVEL MEMORY MAP

```text
LEVEL 1
│
├── Interface = Contract
├── Cannot create interface object
├── Class implements interface
└── Interface reference can refer to implementing object


LEVEL 2
│
├── Multiple interfaces
├── Interface inheritance
├── Interface fields = public static final
├── Abstract methods
├── Default methods
├── Static methods
└── Private methods


LEVEL 3
│
├── Multiple inheritance of type
├── Diamond problem
├── Default-method conflict resolution
├── Functional interface
├── Marker interface
├── Runtime polymorphism
└── Loose coupling
```

## 🔥 One sentence to remember

> **An interface defines a contract; classes implement that contract, allowing abstraction, runtime polymorphism, multiple inheritance of type, and loose coupling.**
