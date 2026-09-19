# Interface in Java — DEEPDIVE

An **interface** is one of the most important concepts in Java OOP. It is closely connected with **abstraction, inheritance, polymorphism, multiple inheritance, loose coupling, and default/static methods**.

---

# 1. What Is an Interface?

> **An interface is a reference type in Java that defines a contract that implementing classes must fulfill.**

The simplest way to understand it:

```text
INTERFACE
    ↓
WHAT should be done?
    ↓
IMPLEMENTING CLASS
    ↓
HOW should it be done?
```

Example:

```java
interface Robo
{
    void move();
}
```

The interface says:

> Every class implementing `Robo` must provide `move()`.

But it doesn't specify the implementation of `move()` here.

---

# 2. Why Do We Need Interfaces?

Suppose we have different robots:

```text
                         Robo
                           |
              ┌────────────┼────────────┐
              ↓            ↓            ↓
        FighterRobo   PlayerRobo   TeacherRobo
```

All robots may need to perform:

```text
move()
```

But each robot may perform the operation differently.

So we define a common contract:

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

Another:

```java
class PlayerRobo implements Robo
{
    public void move()
    {
        System.out.println("Player Robo moves");
    }
}
```

The interface defines the common requirement, while each class supplies its own implementation.

---

# 3. Basic Syntax

```java
interface InterfaceName
{
    // members
}
```

A class implements an interface using:

```java
class ClassName implements InterfaceName
{
    // implementation
}
```

Example:

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

---

# 4. `implements` — Very Important

When a **class uses an interface**, we use:

```java
implements
```

Example:

```java
class FighterRobo implements Robo
```

Not:

```java
class FighterRobo extends Robo
```

for an interface.

Remember:

```text
Class → extends → Class

Class → implements → Interface

Interface → extends → Interface
```

---

# 5. First Complete Program

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

# 6. Understand the Program Step by Step

### Step 1

```java
interface Robo
```

Creates an interface called `Robo`.

---

### Step 2

```java
void move();
```

Defines the required operation.

---

### Step 3

```java
class FighterRobo implements Robo
```

This means:

> FighterRobo agrees to follow the Robo interface contract.

---

### Step 4

```java
public void move()
```

FighterRobo provides the implementation.

---

### Step 5

```java
FighterRobo f = new FighterRobo();
```

Creates the actual object.

---

### Step 6

```java
f.move();
```

Calls the implementation.

---

# 7. Why Is the Method `public`?

Consider:

```java
interface Robo
{
    void move();
}
```

An interface method declared this way is implicitly:

```java
public abstract void move();
```

Therefore the implementing method must not reduce its visibility.

Correct:

```java
public void move()
{
}
```

Incorrect:

```java
void move()
{
}
```

because that would provide weaker access than the interface contract requires.

---

# 8. Interface Reference

We can create an interface reference:

```java
Robo r = new FighterRobo();
```

But we cannot create an interface object directly:

```java
Robo r = new Robo();   // ❌
```

Think:

```text
Robo
 ↓
Interface reference

FighterRobo
 ↓
Actual object
```

This is extremely important for polymorphism.

---

# 9. Interface Reference + Runtime Polymorphism

Consider:

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

The same reference type:

```java
Robo r;
```

can refer to different implementations.

This is **runtime polymorphism**.

---

# 10. Interface Is a Contract

Think of an interface as a contract.

```java
interface Payment
{
    void pay();
}
```

This means:

> Any class that implements `Payment` must provide the required `pay()` behavior.

For example:

```java
class UPI implements Payment
{
    public void pay()
    {
        System.out.println("Payment through UPI");
    }
}
```

and:

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

# 11. Interface Can Contain Fields

Consider:

```java
interface Robo
{
    int MAX_BATTERY = 100;
}
```

The field is automatically:

```java
public static final
```

So it is equivalent to:

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

# 12. Important Interface Field Rule

Interface fields are:

```text
public
static
final
```

automatically.

So:

```java
interface Test
{
    int x = 10;
}
```

means:

```java
interface Test
{
    public static final int x = 10;
}
```

You don't have to explicitly write all three modifiers.

---

# 13. Can an Interface Have Instance Variables?

### ❌ No

For example:

```java
interface Robo
{
    int battery = 100;
}
```

`battery` is not an instance variable.

It is implicitly:

```java
public static final
```

Interfaces don't have ordinary per-object instance state.

---

# 14. Can We Change an Interface Variable?

### ❌ No

```java
interface Robo
{
    int battery = 100;
}
```

This is invalid:

```java
battery = 50;
```

because:

```text
battery
 ↓
final
 ↓
cannot be reassigned
```

---

# 15. Can an Interface Have a Constructor?

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

Because interfaces cannot be directly instantiated and therefore don't have constructors.

---

# 16. Can an Interface Have Abstract Methods?

### ✅ Yes

This is the traditional/basic form:

```java
interface Robo
{
    void move();

    void fight();
}
```

These methods are implicitly:

```java
public abstract
```

So this:

```java
void move();
```

means:

```java
public abstract void move();
```

---

# 17. Can an Interface Have Concrete Methods?

### ✅ Yes — in modern Java

This is an important historical point.

Older Java interfaces were commonly taught as containing only abstract methods.

Modern Java allows interfaces to contain:

```text
abstract methods
default methods
static methods
private methods
```

---

# 18. Default Method

A default method has a body.

```java
interface Robo
{
    default void recharge()
    {
        System.out.println("Robo is recharging");
    }
}
```

A class implementing the interface can use it without implementing it again.

Example:

```java
interface Robo
{
    void move();

    default void recharge()
    {
        System.out.println("Robo is recharging");
    }
}

class FighterRobo implements Robo
{
    public void move()
    {
        System.out.println("Fighter moves");
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
Fighter moves
Robo is recharging
```

---

# 19. Why Were Default Methods Introduced?

Imagine Java already has an interface:

```java
interface Robo
{
    void move();
}
```

Thousands of classes implement it.

Now suppose we want to add:

```java
void recharge();
```

Existing classes would be forced to implement the new method.

Default methods allow us to provide an implementation:

```java
default void recharge()
{
    System.out.println("Recharging");
}
```

This helps evolve interfaces while maintaining compatibility with existing implementations.

---

# 20. Can a Class Override a Default Method?

### ✅ Yes

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

The class's implementation takes precedence.

---

# 21. Static Methods in Interface

Interfaces can contain static methods.

```java
interface Robo
{
    static void show()
    {
        System.out.println("Robo interface");
    }
}
```

Call it using the interface name:

```java
Robo.show();
```

### Important

Don't use:

```java
FighterRobo.show();
```

as the normal way to access the interface's static method.

And:

```java
Robo r = new FighterRobo();
r.show();
```

does not work as an interface-instance call.

Interface static methods belong to the interface itself.

---

# 22. Private Methods in Interface

Modern Java also allows private methods inside interfaces.

Example:

```java
interface Robo
{
    default void move()
    {
        helper();
        System.out.println("Moving");
    }

    private void helper()
    {
        System.out.println("Internal helper");
    }
}
```

The private method is used internally by the interface.

It is not accessible from implementing classes.

---

# 23. Complete Modern Interface Example

```java
interface Robo
{
    int MAX_BATTERY = 100;

    void move();

    default void recharge()
    {
        log();
        System.out.println("Robo is recharging");
    }

    static void show()
    {
        System.out.println("This is Robo interface");
    }

    private void log()
    {
        System.out.println("Recharge operation started");
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
Recharge operation started
Robo is recharging
100
This is Robo interface
```

---

# 24. Multiple Interfaces

One of the strongest features of interfaces is that a class can implement multiple interfaces.

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

Hierarchy:

```text
             FighterRobo
              /       \
             ↓         ↓
        Movable      Fightable
```

---

# 25. Why Multiple Interfaces Are Important

Java does not allow:

```java
class C extends A, B
```

❌ A class cannot directly extend two classes.

But:

```java
class C implements A, B
```

✅ is allowed when `A` and `B` are interfaces.

This provides multiple inheritance of **type/contracts**.

---

# 26. Interface Extending Interface

An interface uses `extends` when inheriting from another interface.

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

Now `Fighter` contains the contract of:

```text
Robo
+
Fighter
```

A class can implement `Fighter`:

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

# 27. Multiple Interface Inheritance

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

# 28. Class + Multiple Interfaces

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

This is valid.

Think:

```text
                FighterRobo
                /    |    \
               /     |     \
              ↓      ↓      ↓
            Robo   Movable Fightable
```

---

# 29. Diamond Problem with Interfaces

Consider:

```text
        A
       / \
      B   C
       \ /
        D
```

Suppose both `B` and `C` provide the same default method:

```java
interface A
{
    default void show()
    {
        System.out.println("A");
    }
}
```

```java
interface B extends A
{
    default void show()
    {
        System.out.println("B");
    }
}
```

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

Which `show()` should D inherit?

```text
B.show() ?
C.show() ?
```

This creates ambiguity.

---

# 30. How Does Java Solve the Interface Diamond Problem?

The implementing class can resolve the conflict explicitly.

```java
interface A
{
    default void show()
    {
        System.out.println("A");
    }
}

interface B extends A
{
    default void show()
    {
        System.out.println("B");
    }
}

interface C extends A
{
    default void show()
    {
        System.out.println("C");
    }
}

class D implements B, C
{
    public void show()
    {
        System.out.println("D");
    }
}
```

Now:

```java
D d = new D();
d.show();
```

Output:

```text
D
```

The class resolves the conflict.

---

# 31. Can We Explicitly Select a Parent Interface Default Method?

Yes.

Java provides:

```java
InterfaceName.super.methodName();
```

Example:

```java
class D implements B, C
{
    public void show()
    {
        B.super.show();
    }
}
```

Now `B`'s default implementation is selected.

This is especially useful when resolving default-method conflicts.

---

# 32. Interface vs Multiple Class Inheritance

### Classes

```java
class C extends A, B
```

❌ Not permitted.

### Interfaces

```java
class C implements A, B
```

✅ Permitted if `A` and `B` are interfaces.

This is a major difference between Java classes and interfaces.

---

# 33. Functional Interface

A **functional interface** is an interface with exactly **one abstract method**.

Example:

```java
@FunctionalInterface
interface Calculator
{
    int add(int a, int b);
}
```

It can be used with a lambda expression:

```java
Calculator c = (a, b) -> a + b;

System.out.println(c.add(10, 20));
```

Output:

```text
30
```

---

# 34. What Does `@FunctionalInterface` Do?

It tells the compiler:

> This interface is intended to have exactly one abstract method.

For example:

```java
@FunctionalInterface
interface Test
{
    void show();
}
```

If you add another abstract method:

```java
void display();
```

the compiler reports an error.

Important:

> A functional interface can still have multiple default/static/private methods; the restriction is on the number of **abstract methods**.

---

# 35. Marker Interface

A marker interface contains no methods.

Example:

```java
interface MyMarker
{
}
```

It is used to mark a class as having some property recognized by a framework/API.

A well-known Java example is:

```text
Serializable
```

The important idea is:

```text
Marker interface
→ No required methods
→ Provides type-level metadata/meaning
```

---

# 36. Can Interface Be `abstract`?

An interface is inherently an abstraction mechanism; explicitly writing:

```java
abstract interface Robo
{
}
```

is permitted but redundant.

Usually we simply write:

```java
interface Robo
{
}
```

---

# 37. Can Interface Be `final`?

### ❌ No.

An interface is designed to be implemented or extended.

Making an interface `final` would prevent that purpose.

---

# 38. Can Interface Be `private`?

A top-level interface cannot be declared `private`.

However, nested interfaces can have appropriate access modifiers depending on where they are declared.

For beginner-level Java, remember:

```text
Top-level interface
→ public or package-private
```

---

# 39. Can an Interface Contain a Class?

### ✅ Yes

An interface can contain nested types.

For example:

```java
interface Robo
{
    class Battery
    {
        void show()
        {
            System.out.println("Battery");
        }
    }
}
```

Then:

```java
Robo.Battery b = new Robo.Battery();

b.show();
```

---

# 40. Can an Interface Contain Another Interface?

### ✅ Yes

```java
interface Robo
{
    interface Battery
    {
        void charge();
    }
}
```

This is a nested interface.

---

# 41. Can a Class Implement an Interface and Also Extend a Class?

### ✅ Yes

```java
class Machine
{
}

interface Movable
{
    void move();
}

class Robo extends Machine implements Movable
{
    public void move()
    {
        System.out.println("Moving");
    }
}
```

The syntax order is:

```text
extends first
implements afterward
```

Example:

```java
class Robo extends Machine implements Movable
```

---

# 42. Can a Class Implement Multiple Interfaces?

### ✅ Yes

```java
class Robo implements Movable, Fightable, Rechargeable
{
}
```

All are separated by commas.

---

# 43. Can an Interface Extend Multiple Interfaces?

### ✅ Yes

```java
interface BattleRobo extends Movable, Fightable
{
}
```

---

# 44. Can an Interface Extend a Class?

### ❌ No

```java
interface Robo extends Machine
```

where `Machine` is a class is invalid.

Remember:

```text
Interface → extends → Interface(s)

Class → extends → Class

Class → implements → Interface(s)
```

---

# 45. Can a Class Implement Another Class?

### ❌ No

`implements` is used with interfaces.

```java
class Fighter implements Robo
```

where `Robo` is an interface.

---

# 46. Interface and Abstraction

Interface:

```java
interface Payment
{
    void pay();
}
```

Implementation:

```java
class UPI implements Payment
{
    public void pay()
    {
        System.out.println("UPI payment");
    }
}
```

The interface defines:

```text
WHAT?
→ pay()
```

The class defines:

```text
HOW?
→ UPI payment implementation
```

Therefore interface is strongly associated with **abstraction**.

---

# 47. Interface and Encapsulation

Don't confuse these.

### Interface

Focuses on:

```text
WHAT behavior is available?
```

### Encapsulation

Focuses on:

```text
How data/state is bundled and access is controlled
```

For example:

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

The `private` variable demonstrates access control/data hiding, not interface-based abstraction.

---

# 48. Interface and Inheritance

Interfaces also participate in inheritance.

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

`Fighter` inherits the contract of `Robo`.

Then:

```java
class FighterRobo implements Fighter
```

must satisfy the inherited abstract methods.

---

# 49. Interface and Polymorphism

This is one of the most important practical uses.

```java
interface Payment
{
    void pay();
}

class UPI implements Payment
{
    public void pay()
    {
        System.out.println("UPI");
    }
}

class Card implements Payment
{
    public void pay()
    {
        System.out.println("Card");
    }
}
```

Then:

```java
Payment p;

p = new UPI();
p.pay();

p = new Card();
p.pay();
```

Output:

```text
UPI
Card
```

Same interface reference:

```text
Payment p
```

Different objects:

```text
UPI
Card
```

Different implementations execute.

---

# 50. Interface Gives Loose Coupling

Suppose a method accepts:

```java
void makePayment(Payment p)
{
    p.pay();
}
```

Now we can pass:

```java
makePayment(new UPI());
makePayment(new Card());
```

The method doesn't need to know the concrete class.

It depends on the abstraction:

```text
Payment
```

rather than a specific implementation.

This is an important real-world reason interfaces are heavily used in Java applications.

---

# 51. Complete Real-World Style Program

```java
interface Payment
{
    void pay();
}

class UPI implements Payment
{
    public void pay()
    {
        System.out.println("Payment through UPI");
    }
}

class Card implements Payment
{
    public void pay()
    {
        System.out.println("Payment through Card");
    }
}

class PaymentService
{
    void makePayment(Payment p)
    {
        p.pay();
    }
}

class Test
{
    public static void main(String[] args)
    {
        PaymentService service = new PaymentService();

        service.makePayment(new UPI());
        service.makePayment(new Card());
    }
}
```

Output:

```text
Payment through UPI
Payment through Card
```

The important design idea is:

```text
PaymentService
       |
       ↓
Payment interface
       |
   ┌───┴───┐
   ↓       ↓
  UPI     Card
```

`PaymentService` doesn't depend on one concrete payment implementation.

---

# 52. Interface — Complete Member Summary

Modern Java interface can contain:

```text
┌──────────────────────────────────────┐
│            INTERFACE                 │
├──────────────────────────────────────┤
│ Constants / fields                   │
│ Abstract methods                     │
│ Default methods                      │
│ Static methods                       │
│ Private methods                      │
│ Nested classes/interfaces/enums      │
└──────────────────────────────────────┘
```

But remember:

```text
Interface fields
→ public static final

Traditional abstract interface methods
→ public abstract
```

---

# 53. Interface vs Abstract Class — Deep Comparison

| Feature               | Interface                                 | Abstract Class                    |
| --------------------- | ----------------------------------------- | --------------------------------- |
| Keyword               | `interface`                               | `abstract class`                  |
| Direct object         | ❌                                         | ❌                                 |
| Reference             | ✅                                         | ✅                                 |
| Constructor           | ❌                                         | ✅                                 |
| Instance variables    | ❌                                         | ✅                                 |
| Interface fields      | `public static final`                     | Normal variables possible         |
| Abstract methods      | ✅                                         | ✅                                 |
| Concrete methods      | Default/static/private methods            | Normal methods                    |
| Static methods        | ✅                                         | ✅                                 |
| Private methods       | ✅                                         | ✅                                 |
| Multiple inheritance  | A class can implement multiple interfaces | A class can extend only one class |
| Interface inheritance | `extends`                                 | Class inheritance via `extends`   |
| Best suited for       | Contract/capability                       | Shared state + common behavior    |

---

# 54. Most Important Interface Rules

Memorize these:

```text
1. Interface cannot be instantiated directly.

2. Interface can be used as a reference type.

3. Class uses implements to implement an interface.

4. Interface uses extends to inherit another interface.

5. A class can implement multiple interfaces.

6. An interface can extend multiple interfaces.

7. Interface fields are public static final.

8. Interface has no constructor.

9. Modern interfaces can have abstract, default,
   static and private methods.

10. Implementing classes must provide implementations
    for inherited abstract methods unless they remain abstract.

11. Interface static methods belong to the interface.

12. Default methods can be overridden.

13. Interfaces are heavily used for abstraction,
    polymorphism and loose coupling.
```

---

# 55. 🔥 Biggest Interview Traps

### Trap 1

**Can we create an interface object?**

```java
Robo r = new Robo();
```

❌ No.

---

### Trap 2

**Can we create an interface reference?**

```java
Robo r = new FighterRobo();
```

✅ Yes.

---

### Trap 3

**Can interface have variables?**

✅ Yes.

But they are automatically:

```text
public static final
```

---

### Trap 4

**Can interface have constructors?**

❌ No.

---

### Trap 5

**Can interface have concrete methods?**

✅ Yes, through modern interface method forms such as `default`, `static`, and `private`.

---

### Trap 6

**Can class implement multiple interfaces?**

✅ Yes.

```java
class Robo implements A, B, C
```

---

### Trap 7

**Can interface extend multiple interfaces?**

✅ Yes.

```java
interface C extends A, B
```

---

### Trap 8

**Can interface extend a class?**

❌ No.

---

### Trap 9

**Can class extend multiple classes?**

❌ No.

---

### Trap 10

**Can class extend one class and implement multiple interfaces?**

✅ Yes.

```java
class Robo extends Machine implements Movable, Fightable
```

---

# 56. Final Mental Diagram

```text
                              INTERFACE
                                  |
                     Defines a CONTRACT
                                  |
             ┌────────────────────┼────────────────────┐
             ↓                    ↓                    ↓
      Abstract methods      Default methods      Static methods
             |                    |                    |
             └────────────────────┼────────────────────┘
                                  ↓
                         Implementing class
                                  |
                                  ↓
                           Provides behavior
                                  |
                    ┌─────────────┴─────────────┐
                    ↓                           ↓
                  UPI                         Card
                    |                           |
                 pay()                        pay()
                    |                           |
                    └─────────────┬─────────────┘
                                  ↓
                           Polymorphism
```

## ⭐ Final Definition

> **An interface in Java is a reference type that defines a contract for implementing classes. It is primarily used to achieve abstraction, support polymorphism, enable multiple inheritance of type, and promote loose coupling between components.**

### Golden syntax to remember

```java
interface A
{
    void show();
}

class B implements A
{
    public void show()
    {
        System.out.println("B implementation");
    }
}
```

And the three relationships:

```text
Class      → extends    → Class
Class      → implements → Interface
Interface  → extends    → Interface
```

That is the foundation on which the more advanced interface topics—**default methods, functional interfaces, lambda expressions, multiple inheritance conflicts, and loose coupling**—are built.
