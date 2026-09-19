# Polymorphism in Java / 3LEVEL

Think of **Polymorphism** in 3 levels:

```text
LEVEL 1 → BASIC
LEVEL 2 → PROGRAMMING
LEVEL 3 → DEEP / INTERVIEW
```

---

# 🟢 LEVEL 1 — BASIC UNDERSTANDING

## 1. What is Polymorphism?

**Polymorphism = Many Forms**

```text
Poly      → Many
Morphism  → Forms
```

In Java:

> **Polymorphism means the same method name, operation, or reference can be associated with different forms/behaviors.**

Example:

```text
              Robo
                |
       ┌────────┼────────┐
       ↓        ↓        ↓
   Fighter    Player   Teacher
     Robo       Robo      Robo
       |         |         |
     move()    move()    move()
       |         |         |
    Fight      Play      Teach
```

All have:

```java
move()
```

but their behavior can be different.

---

# 2. Two Main Types

```text
                 POLYMORPHISM
                       |
              ┌────────┴────────┐
              ↓                 ↓
       Compile-Time          Runtime
       Polymorphism          Polymorphism
              |                 |
        Method Overloading  Method Overriding
```

---

# 3. Compile-Time Polymorphism

Achieved mainly through:

> **Method Overloading**

Example:

```java
class Calculator
{
    void add()
    {
        System.out.println("No arguments");
    }

    void add(int a)
    {
        System.out.println("One argument");
    }

    void add(int a, int b)
    {
        System.out.println("Two arguments");
    }
}
```

Calling:

```java
Calculator c = new Calculator();

c.add();
c.add(10);
c.add(10, 20);
```

The method name is the same:

```text
add()
add(int)
add(int,int)
```

but parameters differ.

### Remember:

```text
Overloading
     ↓
Different parameter list
     ↓
Compiler selects method
     ↓
Compile-Time Polymorphism
```

---

# 4. Runtime Polymorphism

Achieved through:

> **Method Overriding**

Example:

```java
class Robo
{
    void move()
    {
        System.out.println("Robo moves");
    }
}

class FighterRobo extends Robo
{
    void move()
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

Why?

```text
Reference type → Robo
Actual object  → FighterRobo
```

The actual object is `FighterRobo`, so its overridden `move()` executes.

---

# 🟡 LEVEL 2 — PROGRAMMING UNDERSTANDING

## 5. Complete Runtime Polymorphism Program

```java
class Robo
{
    public void move()
    {
        System.out.println("Robo moves");
    }
}

class FighterRobo extends Robo
{
    @Override
    public void move()
    {
        System.out.println("Fighter Robo moves");
    }
}

class PlayerRobo extends Robo
{
    @Override
    public void move()
    {
        System.out.println("Player Robo moves");
    }
}

class TeacherRobo extends Robo
{
    @Override
    public void move()
    {
        System.out.println("Teacher Robo moves");
    }
}

class RoboApp
{
    public static void main(String[] args)
    {
        Robo r;

        r = new FighterRobo();
        r.move();

        r = new PlayerRobo();
        r.move();

        r = new TeacherRobo();
        r.move();
    }
}
```

Output:

```text
Fighter Robo moves
Player Robo moves
Teacher Robo moves
```

---

## 6. Why Is This Polymorphism?

Look at:

```java
Robo r;
```

Only **one reference** exists:

```text
r
```

But it can refer to different objects:

```java
r = new FighterRobo();
r = new PlayerRobo();
r = new TeacherRobo();
```

And we use the same method call:

```java
r.move();
```

Yet the result changes.

```text
One reference
     ↓
Different objects
     ↓
Same method call
     ↓
Different behavior
```

That's runtime polymorphism.

---

# 7. Upcasting

This:

```java
Robo r = new FighterRobo();
```

is **upcasting**.

```text
FighterRobo
     ↓
    Robo
```

A child object is being referred to using a parent-class reference.

This is valid because:

```text
FighterRobo IS-A Robo
```

---

# 8. Reference Type vs Object Type

For:

```java
Robo r = new FighterRobo();
```

remember:

| Part          | Type          |
| ------------- | ------------- |
| Reference     | `Robo`        |
| Actual object | `FighterRobo` |

The **reference type** controls what members are accessible through `r`.

The **actual object** determines which overridden instance-method implementation executes.

Example:

```java
r.move();
```

If `FighterRobo` overrides `move()`, then:

```text
FighterRobo.move()
```

executes.

---

# 9. Parent Reference and Child-Specific Method

Suppose:

```java
class FighterRobo extends Robo
{
    public void fight()
    {
        System.out.println("Fighting");
    }
}
```

Then:

```java
Robo r = new FighterRobo();
```

This is valid:

```java
r.move();
```

But:

```java
r.fight();
```

is not valid if `fight()` is declared only in `FighterRobo`.

Why?

Because the reference type is:

```text
Robo
```

and `Robo` doesn't declare `fight()`.

---

# 10. Downcasting

If the actual object is really a `FighterRobo`:

```java
Robo r = new FighterRobo();
```

you can explicitly downcast:

```java
FighterRobo f = (FighterRobo) r;

f.fight();
```

But incorrect downcasting can cause:

```text
ClassCastException
```

For example:

```java
Robo r = new PlayerRobo();

FighterRobo f = (FighterRobo) r;
```

The actual object is `PlayerRobo`, not `FighterRobo`.

---

# 11. Overloading vs Overriding

| Feature                   | Overloading                 | Overriding                    |
| ------------------------- | --------------------------- | ----------------------------- |
| Type                      | Compile-time polymorphism   | Runtime polymorphism          |
| Method name               | Same                        | Same                          |
| Parameters                | Different                   | Same signature                |
| Parent-child relationship | Not required                | Required                      |
| Decision                  | Compiler                    | Runtime                       |
| Main concept              | Multiple versions of method | Child-specific implementation |

### Easy memory:

```text
OVERLOADING
Same name
Different parameters
Compiler decides

OVERRIDING
Same signature
Different implementation
Runtime dispatch
```

---

# 🔴 LEVEL 3 — DEEP / INTERVIEW UNDERSTANDING

## 12. Dynamic Method Dispatch

When Java determines an overridden instance method at runtime based on the actual object, it is called:

> **Dynamic Method Dispatch**

Example:

```java
Robo r = new FighterRobo();

r.move();
```

Flow:

```text
r.move()
   ↓
Reference type = Robo
   ↓
Actual object = FighterRobo
   ↓
Check overridden move()
   ↓
FighterRobo.move()
   ↓
Execute
```

---

# 13. Why Can't Static Methods Be Overridden?

Static methods belong to the class rather than participating in instance-method overriding.

Example:

```java
class Robo
{
    static void show()
    {
        System.out.println("Robo");
    }
}

class FighterRobo extends Robo
{
    static void show()
    {
        System.out.println("Fighter Robo");
    }
}
```

This is:

> **Method hiding**, not method overriding.

Therefore:

```text
Instance method → Overriding
Static method   → Hiding
```

---

# 14. Can Constructors Be Overridden?

❌ No.

Constructors:

```text
Cannot be inherited
Cannot be overridden
```

But constructors can be **overloaded**.

```java
Robo()
{
}

Robo(int x)
{
}
```

That's constructor overloading.

---

# 15. Can Private Methods Be Overridden?

❌ No.

A private method is not accessible to the child class for overriding.

A child method with the same signature is not an override of the parent's private method.

---

# 16. Can Final Methods Be Overridden?

❌ No.

```java
class Robo
{
    final void move()
    {
        System.out.println("Robo moves");
    }
}
```

A child cannot override `move()`.

```text
final
 ↓
Cannot override
```

---

# 17. Does Return Type Matter?

Yes.

Return type alone cannot create overloading:

```java
int show()
{
    return 10;
}

double show()
{
    return 20.5;
}
```

❌ Invalid because the parameter list is identical.

For overriding, Java permits a **covariant return type**.

Example:

```java
class Robo
{
    Number getValue()
    {
        return 10;
    }
}

class FighterRobo extends Robo
{
    Integer getValue()
    {
        return 20;
    }
}
```

`Integer` is a subtype of `Number`, so this return type is compatible with overriding.

---

# 18. `@Override`

Use:

```java
@Override
```

when overriding an inherited instance method.

Example:

```java
class Robo
{
    public void move()
    {
        System.out.println("Robo");
    }
}

class FighterRobo extends Robo
{
    @Override
    public void move()
    {
        System.out.println("Fighter Robo");
    }
}
```

It helps the compiler detect mistakes in your intended override.

---

# 19. Interface-Based Polymorphism

Polymorphism also works with interfaces.

```java
interface Animal
{
    void sound();
}

class Dog implements Animal
{
    public void sound()
    {
        System.out.println("Dog barks");
    }
}

class Cat implements Animal
{
    public void sound()
    {
        System.out.println("Cat meows");
    }
}
```

Now:

```java
Animal a;

a = new Dog();
a.sound();

a = new Cat();
a.sound();
```

Output:

```text
Dog barks
Cat meows
```

Again:

```text
One reference
     ↓
Different implementations
     ↓
Same method call
     ↓
Different behavior
```

---

# 20. Polymorphism + Inheritance

Don't confuse these two concepts.

### Inheritance

```text
FighterRobo → Robo
```

establishes a relationship.

### Polymorphism

```java
Robo r = new FighterRobo();
r.move();
```

allows the parent reference to work with different child forms and invoke the appropriate overridden behavior.

So:

```text
Inheritance
     ↓
IS-A relationship

Polymorphism
     ↓
Many forms / behaviors
```

They are related but **not identical**.

---

# 21. The Most Important Exam Question

### Question:

```java
Robo r = new FighterRobo();
r.move();
```

What is happening?

### Answer:

```text
Robo
 ↓
Reference type

FighterRobo
 ↓
Actual object

move()
 ↓
Overridden instance method

FighterRobo.move()
 ↓
Runtime selection
```

Therefore:

> **Runtime polymorphism through method overriding and dynamic method dispatch is occurring.**

---

# 22. One-Page 3LEVEL Revision

```text
                    POLYMORPHISM
                         |
             ┌───────────┴───────────┐
             ↓                       ↓
       COMPILE-TIME              RUNTIME
             |                       |
       Method Overloading       Method Overriding
             |                       |
      Different parameters      Same signature
             |                       |
        Compiler decides        Runtime dispatch
                                     |
                                     ↓
                              Actual object decides
```

### Level 1 — Remember

> **Polymorphism = Many Forms**

### Level 2 — Understand

```java
Robo r = new FighterRobo();
r.move();
```

Parent reference + child object + overridden method = runtime polymorphism.

### Level 3 — Master

Know the difference between:

```text
Overloading
Overriding
Upcasting
Downcasting
Dynamic Method Dispatch
Method Hiding
Static methods
Private methods
Final methods
Constructors
Interface polymorphism
Reference type vs actual object
```

---

# 🧠 FINAL MEMORY TRICK

```text
OVERLOADING
     ↓
Same name
Different parameters
     ↓
Compiler
     ↓
COMPILE-TIME POLYMORPHISM


OVERRIDING
     ↓
Same signature
Child gives new implementation
     ↓
Actual object
     ↓
Runtime
     ↓
RUNTIME POLYMORPHISM
```

### ⭐ Golden statement

> **One parent reference can refer to different child objects, and when an overridden instance method is called through that reference, Java selects the appropriate implementation according to the actual object at runtime. This is runtime polymorphism.**
