# Interface in Java — TEACH ME

Let's learn **Interface in Java from zero**, as if we are sitting in a classroom. I’ll build the concept step by step and use the **Robo example** so the idea stays connected.

---

# 1. First: What Problem Does an Interface Solve?

Imagine we have different robots:

```text
              ROBOT
                |
       ┌────────┼────────┐
       ↓        ↓        ↓
    Fighter   Player   Teacher
     Robo      Robo      Robo
```

All robots can do some common activities:

```text
move()
learn()
recharge()
interact()
fight()
```

But every robot may perform these activities differently.

For example:

```text
Fighter Robo → fights enemies
Player Robo  → plays games
Teacher Robo → teaches students
```

So we need a way to say:

> "Every robot must provide this particular behavior."

That's where an **interface** comes in.

---

# 2. Imagine an Interface as a RULE BOOK 📖

Suppose I create:

```java
interface Robo
{
    void move();
    void recharge();
}
```

Think of this as a **rule book**.

It says:

```text
Any class that follows Robo
        ↓
must provide
        ↓
move()
recharge()
```

The interface tells us **WHAT** must be done.

The implementing class tells us **HOW** it is done.

---

# 3. Your First Interface Program

```java
interface Robo
{
    void move();
}

class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter Robo is moving");
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
Fighter Robo is moving
```

Now let's understand every line.

---

# 4. What Does `interface Robo` Mean?

```java
interface Robo
```

This creates an interface named `Robo`.

Inside it:

```java
void move();
```

We haven't written the body.

We have only said:

> Robo requires a `move()` operation.

---

# 5. What Does `implements` Mean?

Look at:

```java
class FighterRobo implements Robo
```

Read it like English:

> FighterRobo **implements** the Robo interface.

In other words:

> FighterRobo agrees to follow the rules defined by Robo.

So if `Robo` says:

```java
void move();
```

then `FighterRobo` must provide:

```java
public void move()
{
    System.out.println("Fighter Robo is moving");
}
```

---

# 6. Why Is `public` Used?

This is a very important point.

In:

```java
interface Robo
{
    void move();
}
```

the method is automatically:

```java
public abstract void move();
```

Therefore the implementing method must be `public`.

Correct:

```java
public void move()
{
}
```

Not:

```java
void move()
{
}
```

because that would reduce the accessibility.

---

# 7. Interface = WHAT, Class = HOW

This is one of the best ways to remember interfaces.

### Interface

```java
interface Robo
{
    void move();
}
```

It says:

```text
WHAT?
↓
Robot must move.
```

### Class

```java
class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter moves using weapons");
    }
}
```

It says:

```text
HOW?
↓
This is how Fighter Robo moves.
```

So:

```text
INTERFACE
    ↓
WHAT

CLASS
    ↓
HOW
```

---

# 8. Can We Create an Object of an Interface?

Look at:

```java
Robo r = new Robo();
```

❌ **No.**

You cannot directly create an object of an interface.

Why?

Because the interface is a contract, not a complete concrete implementation.

But this is valid:

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
Actual object
```

---

# 9. Interface Reference — Very Important

Let's use:

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
```

Now:

```java
Robo r = new FighterRobo();
```

This is perfectly valid.

Then:

```java
r.move();
```

Output:

```text
Fighter moves
```

This is one of the most important uses of interfaces.

---

# 10. Why Do We Use Interface References?

Suppose we have:

```java
class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter moves");
    }
}
```

and:

```java
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

The same reference:

```java
Robo r;
```

can refer to different objects.

This is **runtime polymorphism**.

---

# 11. Interface + Polymorphism

This is the connection:

```text
Interface
    ↓
Common contract
    ↓
Multiple classes
    ↓
Different implementations
    ↓
Interface reference
    ↓
Runtime polymorphism
```

For example:

```java
Robo r;

r = new FighterRobo();
r.move();

r = new PlayerRobo();
r.move();
```

The JVM determines which implementation should execute based on the actual object.

---

# 12. Interface Can Have Multiple Methods

We can write:

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

Now any concrete class implementing `Robo` has to provide implementations for those abstract methods.

For example:

```java
class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter moves");
    }

    public void learn()
    {
        System.out.println("Fighter learns combat");
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

# 13. What Happens If We Don't Implement a Method?

Suppose:

```java
interface Robo
{
    void move();
    void fight();
}
```

But:

```java
class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Moving");
    }
}
```

We didn't implement:

```java
fight()
```

So a normal concrete class cannot compile successfully.

We have two choices:

### Choice 1 — Implement the missing method

```java
public void fight()
{
    System.out.println("Fighting");
}
```

### Choice 2 — Make the class abstract

```java
abstract class FighterRobo implements Robo
{
}
```

An abstract class is allowed to leave inherited abstract methods unimplemented.

---

# 14. Interface Variables

An interface can contain variables.

Example:

```java
interface Robo
{
    int MAX_BATTERY = 100;
}
```

Java automatically treats it as:

```java
public static final int MAX_BATTERY = 100;
```

So:

```java
System.out.println(Robo.MAX_BATTERY);
```

Output:

```text
100
```

---

# 15. Why Can't We Change the Variable?

Because it is automatically `final`.

This is invalid:

```java
Robo.MAX_BATTERY = 200;
```

The interface variable is a constant.

Remember:

```text
Interface variable
       ↓
public
static
final
```

---

# 16. Does an Interface Have a Constructor?

### No ❌

For example:

```java
interface Robo
{
    Robo()
    {
    }
}
```

is invalid.

An interface cannot be directly instantiated, so it doesn't have a constructor.

---

# 17. Interface and Multiple Inheritance

Now we reach one of the biggest reasons interfaces are important.

Java does not allow a class to inherit from two classes:

```java
class C extends A, B
```

❌ Invalid.

But a class can implement multiple interfaces:

```java
class C implements A, B
```

✅ Valid.

For example:

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
             /     \
            ↓       ↓
        Movable   Fightable
```

This is a major advantage of interfaces.

---

# 18. Interface Can Extend Another Interface

Suppose:

```java
interface Robo
{
    void move();
}
```

Then:

```java
interface Fighter extends Robo
{
    void fight();
}
```

Now `Fighter` inherits the contract of `Robo`.

So a class implementing `Fighter` must provide both:

```text
move()
fight()
```

Example:

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

# 19. Interface Can Extend Multiple Interfaces

Yes.

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

# 20. Class Can Extend a Class and Implement Interfaces

This is also very important.

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

So:

```text
FighterRobo
    |
    ├── extends Machine
    |
    ├── implements Movable
    |
    └── implements Fightable
```

---

# 21. Modern Interfaces

At this point, don't make the mistake of thinking:

> "An interface can contain only abstract methods."

Modern Java interfaces can contain:

```text
1. Abstract methods
2. Default methods
3. Static methods
4. Private methods
5. Constants
```

Let's understand them.

---

# 22. Abstract Method

Traditional interface method:

```java
interface Robo
{
    void move();
}
```

It is effectively:

```java
public abstract void move();
```

No body.

---

# 23. Default Method

A default method has a body.

```java
interface Robo
{
    default void recharge()
    {
        System.out.println("Recharging");
    }
}
```

A class implementing `Robo` can use it directly.

```java
class FighterRobo implements Robo
{
}
```

Then:

```java
FighterRobo f = new FighterRobo();

f.recharge();
```

Output:

```text
Recharging
```

The class didn't write `recharge()`, but it inherited the default implementation.

---

# 24. Can We Override a Default Method?

Yes.

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

Now:

```java
new FighterRobo().recharge();
```

Output:

```text
Fast recharge
```

The class's implementation overrides the default implementation.

---

# 25. Static Method in Interface

Example:

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

The static method belongs to the interface itself.

---

# 26. Private Method in Interface

Modern Java also allows private methods.

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

The private method is used internally by the interface.

It is not directly accessible from the implementing class.

---

# 27. Complete Interface Example

Let's combine the concepts:

```java
interface Robo
{
    int MAX_BATTERY = 100;

    void move();

    default void recharge()
    {
        System.out.println("Robo is recharging");
    }

    static void show()
    {
        System.out.println("This is Robo interface");
    }
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
        f.recharge();

        System.out.println(Robo.MAX_BATTERY);

        Robo.show();
    }
}
```

Output:

```text
Fighter Robo moves
Robo is recharging
100
This is Robo interface
```

---

# 28. Interface and Abstraction

Let's connect this to the previous topic: **Abstraction**.

Suppose:

```java
interface Payment
{
    void pay();
}
```

We don't care how payment happens.

We only know:

```text
Payment
   ↓
pay()
```

Then:

```java
class UPI implements Payment
{
    public void pay()
    {
        System.out.println("UPI payment");
    }
}
```

and:

```java
class Card implements Payment
{
    public void pay()
    {
        System.out.println("Card payment");
    }
}
```

The user of the interface can simply say:

```java
Payment p = new UPI();
p.pay();
```

The implementation details are hidden behind the interface.

That's abstraction.

---

# 29. Interface + Loose Coupling

This is a very important real-world concept.

Suppose:

```java
void makePayment(Payment p)
{
    p.pay();
}
```

We can pass:

```java
makePayment(new UPI());
```

or:

```java
makePayment(new Card());
```

The `makePayment()` method doesn't need to know the exact class.

It only depends on:

```text
Payment interface
```

This creates **loose coupling**.

---

# 30. Functional Interface

An interface containing exactly **one abstract method** is called a functional interface.

Example:

```java
@FunctionalInterface
interface Calculator
{
    int add(int a, int b);
}
```

We can use a lambda:

```java
Calculator c = (a, b) -> a + b;

System.out.println(c.add(10, 20));
```

Output:

```text
30
```

The interface has one abstract method:

```java
add()
```

It can still have default/static/private methods.

---

# 31. Marker Interface

A marker interface doesn't contain abstract methods.

Example:

```java
interface MyMarker
{
}
```

It is used to mark a class with some special meaning recognized by Java APIs/frameworks.

A famous Java example is:

```text
Serializable
```

The important point:

```text
Marker Interface
      ↓
No methods
      ↓
Provides type-level information/meaning
```

---

# 32. Interface vs Abstract Class

This is frequently asked in interviews and exams.

| Interface                               | Abstract Class                        |
| --------------------------------------- | ------------------------------------- |
| Uses `interface`                        | Uses `abstract class`                 |
| No constructor                          | Can have constructor                  |
| Cannot directly create object           | Cannot directly create object         |
| Can have constants                      | Can have instance variables           |
| Fields are `public static final`        | Fields can have different modifiers   |
| Can have abstract methods               | Can have abstract methods             |
| Can have default/static/private methods | Can have normal methods               |
| Class can implement multiple interfaces | Class can extend only one class       |
| Mainly represents a contract            | Can represent common state + behavior |

---

# 33. The Three Relationships You MUST Remember

This is perhaps the most important memory trick.

```text
CLASS
  |
  | extends
  ↓
CLASS
```

Example:

```java
class Child extends Parent
```

---

```text
CLASS
  |
  | implements
  ↓
INTERFACE
```

Example:

```java
class Child implements ParentInterface
```

---

```text
INTERFACE
  |
  | extends
  ↓
INTERFACE
```

Example:

```java
interface Child extends ParentInterface
```

### Memorize:

> **Class extends class, class implements interface, interface extends interface.**

---

# 34. Let's Build a Complete Robo Example

Now let's put everything together.

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

Now Fighter Robo:

```java
class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter Robo moves");
    }

    public void learn()
    {
        System.out.println("Fighter Robo learns combat");
    }

    public void recharge()
    {
        System.out.println("Fighter Robo recharges");
    }

    public void interact()
    {
        System.out.println("Fighter Robo interacts");
    }

    public void fight()
    {
        System.out.println("Fighter Robo fights");
    }
}
```

Now:

```java
class Test
{
    public static void main(String[] args)
    {
        Robo r = new FighterRobo();

        r.move();
        r.learn();
        r.recharge();
        r.interact();
        r.fight();
    }
}
```

Output:

```text
Fighter Robo moves
Fighter Robo learns combat
Fighter Robo recharges
Fighter Robo interacts
Fighter Robo fights
```

Notice something very important:

```java
Robo r = new FighterRobo();
```

Reference is:

```text
Robo
```

Object is:

```text
FighterRobo
```

That's where interface-based polymorphism comes into the picture.

---

# 35. A Simple Real-Life Analogy

Imagine a **driving license**.

The license specifies that a driver must know certain things:

```text
steering
braking
accelerating
```

But different vehicles implement those operations differently.

Similarly:

```text
INTERFACE
   ↓
Rules / Contract

CLASS
   ↓
Implementation
```

Don't think:

> Interface = object.

Think:

> **Interface = contract/reference type.**

---

# 36. Common Doubts

### Q1. Can I create an interface object?

❌ No.

```java
Robo r = new Robo();
```

---

### Q2. Can I create an interface reference?

✅ Yes.

```java
Robo r = new FighterRobo();
```

---

### Q3. Can an interface have variables?

✅ Yes.

They are automatically:

```text
public static final
```

---

### Q4. Can an interface have a constructor?

❌ No.

---

### Q5. Can a class implement two interfaces?

✅ Yes.

```java
class Robo implements A, B
```

---

### Q6. Can an interface extend two interfaces?

✅ Yes.

```java
interface C extends A, B
```

---

### Q7. Can an interface extend a class?

❌ No.

---

### Q8. Can a class extend a class and implement interfaces?

✅ Yes.

```java
class Robo extends Machine implements A, B
```

---

### Q9. Can interface methods have bodies?

✅ Modern Java allows bodies for `default`, `static`, and `private` methods.

---

### Q10. Can a default method be overridden?

✅ Yes.

---

# 37. One Final Story to Remember Everything

Imagine:

```text
                    Robo Interface
                         |
             "Every Robo must move"
                         |
             ┌───────────┴───────────┐
             ↓                       ↓
       FighterRobo              PlayerRobo
             |                       |
       Fighter moves            Player moves
```

The interface says:

```text
move() is required.
```

The classes say:

```text
Here is MY implementation of move().
```

Then:

```java
Robo r;
```

can point to:

```java
r = new FighterRobo();
```

or:

```java
r = new PlayerRobo();
```

And:

```java
r.move();
```

executes the appropriate implementation.

That's:

```text
Interface
   ↓
Contract
   ↓
Implementation
   ↓
Polymorphism
```

---

# 🧠 INTERFACE MEMORY MAP

```text
                         INTERFACE
                             |
                 Defines a CONTRACT
                             |
          ┌──────────────────┼──────────────────┐
          ↓                  ↓                  ↓
    Abstract methods   Default methods    Static methods
          |                  |                  |
          └──────────────────┼──────────────────┘
                             ↓
                    Implementing class
                             |
                             ↓
                      Actual behavior
                             |
                             ↓
                      Polymorphism
```

### 🔥 Remember these 10 points

```text
1. Interface = contract.

2. Interface cannot be directly instantiated.

3. Interface can be used as a reference.

4. Class implements interface.

5. Interface extends interface.

6. Interface fields are public static final.

7. Interface has no constructor.

8. Class can implement multiple interfaces.

9. Modern interfaces can contain abstract,
   default, static and private methods.

10. Interface + implementation classes
    commonly provide abstraction, polymorphism
    and loose coupling.
```

## ⭐ One-line definition

> **An interface in Java is a reference type that defines a contract, which implementing classes fulfill, making interfaces a powerful mechanism for abstraction, polymorphism, multiple inheritance of type, and loose coupling.**
