# Abstraction in Java — TEACHME 🎓

Let's learn **Abstraction from zero**, as if we are sitting in a classroom. I’ll first make the idea clear with a real-life example, then connect it to Java programs.

---

# 1. First Understand the Problem

Imagine you are using a **TV remote**.

You press:

```text
POWER
VOLUME +
CHANNEL +
```

Do you know what happens internally when you press `VOLUME +`?

No.

You don't need to know:

```text
Button
  ↓
Remote circuit
  ↓
Signal generation
  ↓
TV receiver
  ↓
Audio processing
  ↓
Speaker
  ↓
Increased volume
```

You only need to know:

```text
Press VOLUME +
      ↓
Volume increases
```

This is the basic idea of **abstraction**.

---

# 2. Simple Definition

> **Abstraction means hiding unnecessary implementation details and exposing only the essential functionality.**

The easiest way to remember it:

```text
ABSTRACTION

WHAT?
  ↓
Show what an object can do

HOW?
  ↓
Hide how it does it
```

For example:

```text
ATM

withdraw()
checkBalance()
deposit()

        ↓

User doesn't need to know
the internal banking implementation.
```

---

# 3. Now Connect This Idea to Java

Suppose we have different robots:

```text
                 ROBO
                  |
       ┌──────────┼──────────┐
       ↓          ↓          ↓
    Fighter     Player     Teacher
     Robo        Robo        Robo
```

Every robot should be able to:

```text
move()
```

But each robot may move differently.

So we can tell the parent:

> "Every Robo must have a `move()` operation."

But we don't provide the implementation there.

```java
abstract class Robo
{
    abstract void move();
}
```

This is abstraction.

---

# 4. What Did We Just Do?

Look carefully:

```java
abstract void move();
```

We told Java:

> "A Robo must have a `move()` method."

But we didn't tell Java **how** Robo should move.

So:

```text
WHAT
 ↓
move()

HOW
 ↓
Not specified here
```

The child class can decide the implementation.

---

# 5. Child Provides the Implementation

```java
class FighterRobo extends Robo
{
    void move()
    {
        System.out.println("Fighter Robo moves");
    }
}
```

Now the child says:

> "I know how a Fighter Robo should move."

So we have:

```text
Robo
 ↓
WHAT → move()

FighterRobo
 ↓
HOW → Fighter Robo moves
```

---

# 6. Complete First Program

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

---

# 7. Let's Understand Every Line

### Step 1

```java
abstract class Robo
```

We created an **abstract class**.

It represents a general concept of Robo.

---

### Step 2

```java
abstract void move();
```

This is an **abstract method**.

It says:

> Every concrete Robo must provide a `move()` implementation.

There is no method body.

---

### Step 3

```java
class FighterRobo extends Robo
```

`FighterRobo` inherits from `Robo`.

---

### Step 4

```java
void move()
{
    System.out.println("Fighter Robo moves");
}
```

The child provides the implementation.

---

### Step 5

```java
FighterRobo f = new FighterRobo();
```

We create a `FighterRobo` object.

---

### Step 6

```java
f.move();
```

The Fighter Robo's implementation executes.

---

# 8. Now Add More Robots

Suppose we have:

```text
                 Robo
                  |
       ┌──────────┼──────────┐
       ↓          ↓          ↓
   Fighter      Player     Teacher
     Robo         Robo        Robo
```

Every Robo has:

```text
move()
```

But each one moves differently.

---

## Complete Program

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

class PlayerRobo extends Robo
{
    void move()
    {
        System.out.println("Player Robo moves");
    }
}

class TeacherRobo extends Robo
{
    void move()
    {
        System.out.println("Teacher Robo moves");
    }
}

class Test
{
    public static void main(String[] args)
    {
        FighterRobo f = new FighterRobo();
        PlayerRobo p = new PlayerRobo();
        TeacherRobo t = new TeacherRobo();

        f.move();
        p.move();
        t.move();
    }
}
```

Output:

```text
Fighter Robo moves
Player Robo moves
Teacher Robo moves
```

The parent defines the common requirement:

```java
abstract void move();
```

The children decide their own implementation.

---

# 9. 🔥 Now the Most Important Question

### Why didn't we simply write this?

```java
class Robo
{
    void move()
    {
        System.out.println("Robo moves");
    }
}
```

Because perhaps there is **no single implementation** that is appropriate for every type of Robo.

For example:

```text
Fighter Robo → moves for fighting
Player Robo  → moves for playing
Teacher Robo → moves for teaching
```

So the parent says:

> "Every Robo must move."

But each child decides:

> "This is how I move."

That's where abstraction becomes useful.

---

# 10. What Is an Abstract Class?

A class declared with `abstract` is an **abstract class**.

```java
abstract class Robo
{
}
```

It is generally used as a base class for defining common behavior and/or abstract requirements.

---

# 11. What Is an Abstract Method?

A method declared with `abstract` and without a body is an **abstract method**.

```java
abstract void move();
```

Remember:

```text
abstract void move();
                ↑
             semicolon
```

There is no:

```java
{
}
```

---

# 12. Very Important: Abstract Class Can Have Normal Methods

Many beginners think:

> "Abstract class can contain only abstract methods."

❌ Wrong.

It can contain both:

```text
Abstract methods
+
Concrete methods
```

Example:

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

---

# 13. Complete Example

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

Notice:

```text
move()
    ↓
Child provides implementation

recharge()
    ↓
Parent already provides implementation
```

---

# 14. Can We Create an Object of Abstract Class?

### ❌ No.

Suppose:

```java
abstract class Robo
{
    abstract void move();
}
```

We cannot write:

```java
Robo r = new Robo();
```

This is invalid.

Why?

Because `Robo` contains an incomplete method:

```java
abstract void move();
```

There is no implementation for that method in `Robo`.

---

# 15. But Can We Create an Abstract Class Reference?

### ✅ Yes!

This is one of the most important points.

```java
Robo r = new FighterRobo();
```

This is perfectly valid.

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

# 16. Why Is This Useful?

Now we can write:

```java
Robo r;

r = new FighterRobo();
r.move();

r = new PlayerRobo();
r.move();

r = new TeacherRobo();
r.move();
```

The parent abstraction lets us work with different implementations through one common type.

This also demonstrates **runtime polymorphism**.

---

# 17. Abstraction + Polymorphism

Look at:

```java
Robo r = new FighterRobo();
r.move();
```

### Abstraction

The parent says:

```java
abstract void move();
```

It defines **what must exist**.

### Polymorphism

The actual object is:

```text
FighterRobo
```

and its implementation executes.

So:

```text
ABSTRACTION
     ↓
Defines WHAT

POLYMORPHISM
     ↓
Allows DIFFERENT IMPLEMENTATIONS
```

---

# 18. Does Abstract Class Always Need Abstract Methods?

### ❌ No.

This is valid:

```java
abstract class Robo
{
    void move()
    {
        System.out.println("Robo moves");
    }
}
```

There is no abstract method, but the class itself is abstract.

Why would we do that?

We may want to prevent direct instantiation while still providing a common base implementation.

---

# 19. Can an Abstract Class Have Variables?

### Yes.

```java
abstract class Robo
{
    int battery = 100;

    abstract void move();
}
```

So an abstract class can contain instance variables.

It can also contain other normal class members.

---

# 20. Can an Abstract Class Have a Constructor?

### Yes! 🔥

This is a very common doubt.

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

We cannot do:

```java
new Robo();
```

But when we create:

```java
new FighterRobo();
```

the `Robo` constructor participates in initialization.

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

class FighterRobo extends Robo
{
    FighterRobo()
    {
        System.out.println("Fighter Robo constructor");
    }

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
    }
}
```

Output:

```text
Robo constructor
Fighter Robo constructor
```

---

# 21. Why Does the Parent Constructor Execute?

Because creating the child object also initializes the inherited parent portion of the object.

Think:

```text
new FighterRobo()
       ↓
Robo constructor
       ↓
FighterRobo constructor
       ↓
Object ready
```

---

# 22. What If the Child Doesn't Implement the Abstract Method?

Suppose:

```java
abstract class Robo
{
    abstract void move();
}

class FighterRobo extends Robo
{
}
```

This is not allowed if `FighterRobo` is intended to be concrete.

It must implement:

```java
void move()
{
    // implementation
}
```

Or `FighterRobo` itself must be declared abstract.

```java
abstract class FighterRobo extends Robo
{
}
```

---

# 23. Can One Abstract Class Extend Another?

### Yes.

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

# 24. Interface — Another Way of Abstraction

Java also uses **interfaces**.

Example:

```java
interface Robo
{
    void move();
}
```

Then:

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

# 25. Think of Interface as a Contract

Suppose you create:

```java
interface Payment
{
    void pay();
}
```

You're saying:

> "Any class that implements `Payment` must provide the required payment operation."

Then:

```java
class UPI implements Payment
{
    public void pay()
    {
        System.out.println("Payment through UPI");
    }
}
```

Another:

```java
class Card implements Payment
{
    public void pay()
    {
        System.out.println("Payment through Card");
    }
}
```

Both satisfy the same contract.

---

# 26. Abstract Class vs Interface — Easy Version

| Abstract Class               | Interface                                 |
| ---------------------------- | ----------------------------------------- |
| `abstract class`             | `interface`                               |
| Can have abstract methods    | Can declare abstract methods              |
| Can have concrete methods    | Can have concrete/default/static methods  |
| Can have instance variables  | Fields are constants                      |
| Can have constructors        | No constructors                           |
| A class can extend one class | A class can implement multiple interfaces |

---

# 27. 🔥 Don't Confuse Abstraction and Encapsulation

Suppose:

```java
class BankAccount
{
    private double balance;

    public double getBalance()
    {
        return balance;
    }
}
```

The `private` variable is about **access control/data hiding**.

That's related to encapsulation.

Abstraction is about:

```text
Hide unnecessary implementation
        ↓
Expose essential operation
```

### Easy trick:

```text
ABSTRACTION
→ What should the user see?

ENCAPSULATION
→ How do we protect/control the data?
```

---

# 28. Abstraction vs Inheritance

Don't say:

> "Inheritance is abstraction."

They are different concepts.

### Inheritance

```text
FighterRobo
     ↓
   Robo
```

Establishes an **IS-A relationship**.

### Abstraction

```text
Robo
 ↓
abstract move()
```

Hides implementation and defines required behavior.

They are often used together, but they are not the same thing.

---

# 29. Abstraction vs Polymorphism

### Abstraction

```text
WHAT should happen?
```

Example:

```java
abstract void move();
```

### Polymorphism

```text
WHICH implementation should execute?
```

Example:

```java
Robo r = new FighterRobo();

r.move();
```

So:

```text
Abstraction
    ↓
Defines the common requirement

Polymorphism
    ↓
Allows different implementations
```

---

# 30. Real-Life Example: Vehicle

Imagine:

```text
Vehicle
```

Every vehicle should:

```text
start()
stop()
```

But the implementation differs.

```text
                  Vehicle
                     |
            ┌────────┴────────┐
            ↓                 ↓
           Car              Bike
            |                 |
        start()            start()
            |                 |
     Engine starts       Engine starts
```

We can represent the common requirement:

```java
abstract class Vehicle
{
    abstract void start();
}
```

Then:

```java
class Car extends Vehicle
{
    void start()
    {
        System.out.println("Car starts");
    }
}
```

and:

```java
class Bike extends Vehicle
{
    void start()
    {
        System.out.println("Bike starts");
    }
}
```

The parent doesn't need to know the exact starting mechanism.

---

# 31. The Best Way to Remember Abstraction

Whenever you see:

```java
abstract void something();
```

think:

> **"I am telling the child WHAT it must do, but I am not telling it HOW to do it here."**

For example:

```java
abstract void move();
```

means:

```text
WHAT?
→ Robo must move.

HOW?
→ Child decides.
```

---

# 32. 🔥 Important Restrictions

### Abstract + final?

❌ Not allowed.

Why?

```text
abstract → must be implemented through inheritance
final    → cannot be inherited/overridden
```

Contradiction.

---

### Abstract + static method?

❌ Not allowed.

Static methods are not overridden as instance methods.

---

### Abstract + private?

❌ Not allowed.

A private method isn't available to subclasses for overriding.

---

### Abstract class + static method?

✅ Allowed.

```java
abstract class Robo
{
    static void show()
    {
        System.out.println("Hello");
    }
}
```

The restriction is on the **method being abstract**, not on the class being abstract.

---

# 33. Can Abstract Class Have `main()`?

### Yes.

An abstract class can contain a static `main()` method.

For example:

```java
abstract class Robo
{
    public static void main(String[] args)
    {
        System.out.println("Main method");
    }
}
```

The class being abstract does not automatically prevent the JVM from invoking a valid `main` method.

---

# 34. Can Abstract Class Have Zero Abstract Methods?

### Yes.

```java
abstract class Robo
{
    void move()
    {
        System.out.println("Moving");
    }
}
```

Still valid.

---

# 35. One Complete Teaching Example

Let's combine:

* Abstract class
* Abstract method
* Concrete method
* Constructor
* Variable
* Inheritance
* Method overriding
* Abstract reference
* Runtime polymorphism

```java
abstract class Robo
{
    String name;

    Robo(String name)
    {
        this.name = name;
    }

    abstract void move();

    void recharge()
    {
        System.out.println(name + " is recharging");
    }

    void showName()
    {
        System.out.println("Robo Name: " + name);
    }
}

class FighterRobo extends Robo
{
    FighterRobo(String name)
    {
        super(name);
    }

    void move()
    {
        System.out.println("Fighter Robo moves");
    }
}

class Test
{
    public static void main(String[] args)
    {
        Robo r = new FighterRobo("FR-101");

        r.showName();
        r.move();
        r.recharge();
    }
}
```

Output:

```text
Robo Name: FR-101
Fighter Robo moves
FR-101 is recharging
```

---

# 36. Let's Identify Every Concept

### `abstract class Robo`

```text
→ Abstract class
```

### `String name`

```text
→ Instance variable
```

### `Robo(String name)`

```text
→ Constructor
```

### `abstract void move();`

```text
→ Abstract method
```

### `recharge()`

```text
→ Concrete method
```

### `FighterRobo extends Robo`

```text
→ Inheritance
```

### `move()` in FighterRobo

```text
→ Method overriding
```

### `Robo r = new FighterRobo(...)`

```text
→ Parent/abstract reference
→ Child object
→ Upcasting
```

### `r.move()`

```text
→ Runtime polymorphism
→ Dynamic method dispatch
```

So one program can demonstrate several OOP concepts together.

---

# 🧠 TEACHME FINAL RECAP

Imagine your teacher says:

> "Every Robo must move."

You ask:

**How?**

Teacher says:

> "I don't care how. You just have to provide a `move()` implementation."

In Java:

```java
abstract class Robo
{
    abstract void move();
}
```

Then Fighter Robo says:

```java
class FighterRobo extends Robo
{
    void move()
    {
        System.out.println("Fighter Robo moves");
    }
}
```

That's abstraction.

---

## The complete mental picture

```text
                     ABSTRACTION
                          |
              Hide implementation details
                          |
                  Show essential features
                          |
             ┌────────────┴────────────┐
             ↓                         ↓
      ABSTRACT CLASS              INTERFACE
             |                         |
     abstract methods              Contract
     concrete methods
     variables
     constructors
             |
             ↓
       Concrete child
             |
             ↓
       Implementation
```

### ⭐ Remember these 7 points

```text
1. Abstraction = hiding implementation details.

2. Abstract class is one way to achieve abstraction.

3. Interface is another major way.

4. Abstract method has no body.

5. Abstract class cannot be directly instantiated.

6. Abstract class can have constructors,
   variables and concrete methods.

7. A concrete child must implement all inherited
   abstract methods.
```

### 🔥 One golden sentence

> **Abstraction tells us WHAT an object should do while hiding HOW that operation is implemented.**
