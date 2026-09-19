# Interface in Java — ONEPAGE

## 1. What is an Interface?

> **An interface in Java is a reference type that defines a contract that implementing classes must follow.**

It is one of the major ways of achieving **abstraction** in Java.

Think:

```text
INTERFACE
    ↓
WHAT should be done?
    ↓
IMPLEMENTING CLASS
    ↓
HOW should it be done?
```

### Real-life example

Consider a **USB port**.

The USB standard specifies requirements for communication, but different devices implement those requirements differently.

Similarly:

```text
                Payment
                   |
        ┌──────────┼──────────┐
        ↓          ↓          ↓
       UPI        Card       Cash
        |          |           |
      pay()       pay()       pay()
```

The interface defines the common contract:

```java
interface Payment
{
    void pay();
}
```

---

# 2. Basic Syntax

```java
interface InterfaceName
{
    // fields
    // methods
}
```

A class implements an interface using:

```java
class ClassName implements InterfaceName
{
    // implementation
}
```

⚠️ Remember:

```text
class → extends → class
class → implements → interface
interface → extends → interface
```

There is **no `@extends` keyword**. `extends` and `implements` are Java keywords, and `@` is not used with them.

---

# 3. Simple Interface Program

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

### What happened?

The interface says:

```java
void move();
```

It defines **what** is required.

The implementing class provides **how**:

```java
public void move()
{
    System.out.println("Fighter Robo moves");
}
```

---

# 4. Why `public` in the Implementation?

Interface methods declared without an explicit access modifier are public abstract methods.

Therefore, when implementing:

```java
interface Robo
{
    void move();
}
```

we write:

```java
public void move()
{
}
```

We cannot reduce the visibility to package-private.

---

# 5. Interface Reference

We can create a reference of the interface:

```java
Robo r = new FighterRobo();
```

But we cannot directly create an interface object:

```java
Robo r = new Robo();     // ❌
```

The relationship is:

```text
Robo
 ↓
Interface reference

FighterRobo
 ↓
Actual object
```

Then:

```java
r.move();
```

This also gives us **runtime polymorphism**.

---

# 6. Interface Can Contain Variables

Fields declared in an interface are automatically:

```text
public
static
final
```

For example:

```java
interface Robo
{
    int MAX_BATTERY = 100;
}
```

is effectively:

```java
public static final int MAX_BATTERY = 100;
```

Therefore:

```java
System.out.println(Robo.MAX_BATTERY);
```

works.

But:

```java
Robo.MAX_BATTERY = 200;
```

is invalid because the field is `final`.

---

# 7. Interface Methods

Modern Java interfaces can contain different kinds of methods.

### Abstract method

```java
interface Robo
{
    void move();
}
```

### Default method

```java
interface Robo
{
    default void recharge()
    {
        System.out.println("Recharging");
    }
}
```

### Static method

```java
interface Robo
{
    static void show()
    {
        System.out.println("Robo interface");
    }
}
```

### Private method

Modern Java interfaces can also contain private methods, which are used internally by the interface's default/static methods.

---

# 8. Complete Program with Abstract + Default + Static

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

# 9. Interface Cannot Have a Constructor

### ❌ No

```java
interface Robo
{
    Robo()
    {
    }
}
```

Invalid.

Why?

An interface cannot be directly instantiated, so it does not have constructors.

---

# 10. Multiple Interfaces — Very Important

A Java class can implement multiple interfaces.

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

This is one major reason interfaces are important in Java.

```text
             FighterRobo
              /       \
             ↓         ↓
        Movable      Fightable
```

---

# 11. Multiple Inheritance Through Interfaces

Java does not allow a class to extend multiple classes:

```java
class C extends A, B
```

❌ Invalid.

But Java allows a class to implement multiple interfaces:

```java
class C implements A, B
```

✅ Valid.

Therefore, interfaces provide a mechanism for achieving **multiple inheritance of type/contracts**.

---

# 12. Interface Extending Interface

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

Hierarchy:

```text
Robo
 ↓
Fighter
 ↓
FighterRobo
```

---

# 13. Multiple Interface Inheritance

An interface can extend multiple interfaces.

```java
interface Movable
{
    void move();
}

interface Fightable
{
    void fight();
}

interface Fighter extends Movable, Fightable
{
}
```

Then:

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

---

# 14. Interface + Class

A class can extend one class and implement multiple interfaces.

```java
class Robo
{
    void recharge()
    {
        System.out.println("Recharge");
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

class FighterRobo extends Robo implements Movable, Fightable
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

This is perfectly valid.

---

# 15. Interface vs Abstract Class

| Interface                                 | Abstract Class                     |
| ----------------------------------------- | ---------------------------------- |
| Declared using `interface`                | Declared using `abstract class`    |
| No constructor                            | Can have constructor               |
| Cannot be directly instantiated           | Cannot be directly instantiated    |
| Fields are `public static final`          | Can have instance/static variables |
| Can have abstract methods                 | Can have abstract methods          |
| Can have default/static/private methods   | Can have normal methods            |
| A class can implement multiple interfaces | A class can extend only one class  |
| Excellent for contracts/capabilities      | Useful for common state + behavior |

---

# 16. Interface and Abstraction

Interface:

```java
interface Payment
{
    void pay();
}
```

UPI:

```java
class UPI implements Payment
{
    public void pay()
    {
        System.out.println("Payment through UPI");
    }
}
```

Card:

```java
class Card implements Payment
{
    public void pay()
    {
        System.out.println("Payment through Card");
    }
}
```

Now:

```java
Payment p;

p = new UPI();
p.pay();

p = new Card();
p.pay();
```

The interface defines the common **WHAT**, while the classes provide different **HOWs**.

---

# 17. Interface and Polymorphism

```java
Payment p = new UPI();
p.pay();
```

Reference:

```text
Payment
```

Object:

```text
UPI
```

At runtime, the appropriate `pay()` implementation executes.

Therefore:

```text
Interface
    +
Inheritance/implementation
    +
Runtime polymorphism
```

work together very naturally.

---

# 18. 🔥 Important Rules

```text
1. Interface cannot be instantiated directly.

2. Interface can have references.

3. Class uses implements for an interface.

4. Interface extends another interface.

5. A class can implement multiple interfaces.

6. Interface fields are public static final.

7. Interface has no constructor.

8. Interface methods can include abstract,
   default, static and private methods.

9. An implementing class must implement
   inherited abstract methods unless the class itself is abstract.

10. Interface is commonly used to define a contract.
```

---

# 🧠 ONEPAGE MEMORY MAP

```text
                         INTERFACE
                             |
                Defines a CONTRACT
                             |
             ┌───────────────┴───────────────┐
             ↓                               ↓
       Abstract methods                Other allowed
                                       modern methods
             |
             ↓
      class implements
             |
             ↓
     Provides implementation
             |
             ↓
      Different classes
             |
             ↓
      Runtime polymorphism
```

### Golden syntax

```java
interface A
{
    void show();
}

class B implements A
{
    public void show()
    {
        System.out.println("Hello");
    }
}
```

### Golden relationship

```text
class → extends → class
class → implements → interface
interface → extends → interface
```

### Golden definition

> **An interface in Java defines a contract that implementing classes agree to fulfill, and it is one of the primary mechanisms used to achieve abstraction and support multiple inheritance of type.**
