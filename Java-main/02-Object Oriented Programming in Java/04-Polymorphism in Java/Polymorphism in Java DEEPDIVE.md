# Polymorphism in Java — DEEPDIVE

Polymorphism is one of the **four major OOP concepts** in Java:

```text
OOP
│
├── Encapsulation
├── Inheritance
├── Polymorphism
└── Abstraction
```

The word **Polymorphism** comes from:

```text
Poly       = Many
Morphism   = Forms
```

So:

> **Polymorphism means one entity can take many forms.**

In Java, polymorphism mainly allows the **same method name or reference to behave differently depending on the context**.

---

# 1. Basic Example of Polymorphism

Consider a `Robo`.

Different types of Robos can perform movement differently:

```text
                         Robo
                           |
             ┌─────────────┼─────────────┐
             ↓             ↓             ↓
       FighterRobo    PlayerRobo    TeacherRobo
             |             |             |
          move()         move()        move()
             |             |             |
        Fight-style     Game-style    Teaching-style
          movement       movement       movement
```

All three have:

```java
move()
```

but the behavior can be different.

Therefore:

> **Same method name + different behavior = Polymorphism.**

---

# 2. Types of Polymorphism

Java commonly discusses two major types:

```text
                    POLYMORPHISM
                         |
             ┌───────────┴───────────┐
             ↓                       ↓
      Compile-Time                Runtime
      Polymorphism               Polymorphism
             |                       |
        Overloading              Overriding
             |                       |
       Early Binding             Dynamic Binding
       Static Binding            Late Binding
```

Let's understand both deeply.

---

# 3. Compile-Time Polymorphism

Compile-time polymorphism is commonly achieved through:

> **Method Overloading**

Here, multiple methods have the same name but different parameter lists.

---

## Example

```java
class Calculator
{
    public void add()
    {
        System.out.println("No arguments");
    }

    public void add(int a)
    {
        System.out.println("One argument");
    }

    public void add(int a, int b)
    {
        System.out.println("Two arguments");
    }
}
```

Now:

```java
class Test
{
    public static void main(String[] args)
    {
        Calculator c = new Calculator();

        c.add();
        c.add(10);
        c.add(10, 20);
    }
}
```

Output:

```text
No arguments
One argument
Two arguments
```

The method name is always:

```text
add()
```

but its parameter list changes.

---

# 4. Why Is It Called Compile-Time Polymorphism?

Consider:

```java
c.add(10, 20);
```

The compiler sees:

```text
add()
add(int)
add(int,int)
```

The compiler determines that:

```text
add(int,int)
```

is the appropriate method.

The decision is made during compilation.

Therefore:

```text
Method Overloading
        ↓
Compile-Time Polymorphism
        ↓
Compiler decides
```

---

# 5. How Does Compiler Resolve Overloading?

When overloaded methods are present, Java uses the method invocation and the argument types to determine the applicable method.

For example:

```java
void show(int x)
{
}

void show(double x)
{
}
```

Calling:

```java
show(10);
```

selects:

```text
show(int)
```

because `10` is an `int` literal.

Calling:

```java
show(10.5);
```

selects:

```text
show(double)
```

because `10.5` is a `double` literal.

---

# 6. Important Rule

For method overloading:

> **Return type alone cannot distinguish overloaded methods.**

This is invalid:

```java
class Demo
{
    int show()
    {
        return 10;
    }

    double show()
    {
        return 20.5;
    }
}
```

Why?

Both methods have:

```text
show()
```

The parameter list is identical.

Changing only the return type does not create a valid overload.

---

# 7. What Can Change During Overloading?

You can change:

```text
Number of parameters
Type of parameters
Order of parameters
```

Example:

```java
void show(int a)
{
}

void show(int a, int b)
{
}

void show(double a)
{
}

void show(int a, double b)
{
}
```

These can coexist because their parameter lists differ.

---

# 8. Runtime Polymorphism

Now comes the more important form of polymorphism.

> **Runtime polymorphism is achieved through method overriding and dynamic method dispatch.**

Example:

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

# 9. Why Did the Child Method Execute?

This is the most important point.

Look carefully:

```java
Robo r = new FighterRobo();
```

There are two different things:

```text
Robo r
   ↑
Reference type


new FighterRobo()
       ↑
Actual object type
```

So:

```text
Reference → Robo
Object    → FighterRobo
```

When we call:

```java
r.move();
```

Java determines the overridden instance method based on the **actual object at runtime**.

Therefore:

```text
FighterRobo.move()
```

executes.

---

# 10. Dynamic Method Dispatch

The process by which Java selects an overridden instance method at runtime is called:

> **Dynamic Method Dispatch**

Example:

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
    public void move()
    {
        System.out.println("Fighter Robo moves");
    }
}

class PlayerRobo extends Robo
{
    public void move()
    {
        System.out.println("Player Robo moves");
    }
}
```

Now:

```java
class Test
{
    public static void main(String[] args)
    {
        Robo r;

        r = new FighterRobo();
        r.move();

        r = new PlayerRobo();
        r.move();
    }
}
```

Output:

```text
Fighter Robo moves
Player Robo moves
```

Notice that the statement:

```java
r.move();
```

is the same.

But the actual object changes.

Therefore the behavior changes.

---

# 11. The Most Important Diagram

```text
                    Robo
                      |
            move()    |
                      |
           ┌──────────┴──────────┐
           ↓                     ↓
     FighterRobo            PlayerRobo
       move()                  move()
           |                     |
           ↓                     ↓
 "Fighter Robo moves"   "Player Robo moves"
```

Now:

```java
Robo r;

r = new FighterRobo();
r.move();
```

→ Fighter behavior

Then:

```java
r = new PlayerRobo();
r.move();
```

→ Player behavior

This is runtime polymorphism.

---

# 12. Runtime Polymorphism Requires Inheritance?

For the traditional class-based form of runtime polymorphism through overriding:

**Yes, a parent-child relationship is involved.**

Example:

```text
Robo
 ↓
FighterRobo
```

The child overrides the parent's method.

---

# 13. Upcasting

This statement:

```java
Robo r = new FighterRobo();
```

is called **upcasting**.

Why?

Because:

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

# 14. Why Is Upcasting Important?

Because runtime polymorphism commonly uses:

```java
Parent reference = new Child();
```

Example:

```java
Robo r = new FighterRobo();
```

Then:

```java
r.move();
```

executes the overridden method of the actual object.

So:

```text
Upcasting
    ↓
Parent reference
    ↓
Child object
    ↓
Overridden method
    ↓
Runtime polymorphism
```

---

# 15. Reference Type vs Object Type

This is a **major interview concept**.

Consider:

```java
Robo r = new FighterRobo();
```

### Reference type

```text
Robo
```

The reference type determines which members are available through the reference at compile time.

### Object type

```text
FighterRobo
```

The actual object determines which overridden instance implementation executes at runtime.

So remember:

> **Reference type controls accessibility; actual object controls overridden instance-method behavior.**

---

# 16. Parent Reference Cannot Directly Access Child-Specific Methods

Suppose:

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
    public void fight()
    {
        System.out.println("Fighter Robo fights");
    }
}
```

Now:

```java
Robo r = new FighterRobo();
```

This is valid:

```java
r.move();
```

But this is not:

```java
r.fight();
```

Why?

Because `fight()` is not declared in `Robo`.

The reference is:

```text
Robo
```

If you need the child-specific functionality and the actual object really is a `FighterRobo`, you can downcast:

```java
FighterRobo fr = (FighterRobo) r;

fr.fight();
```

---

# 17. Downcasting

Downcasting means converting a parent reference to a child reference.

```java
Robo r = new FighterRobo();

FighterRobo fr = (FighterRobo) r;
```

Diagram:

```text
Robo reference
     ↓
FighterRobo reference
```

But this must be done carefully.

For example:

```java
Robo r = new PlayerRobo();

FighterRobo fr = (FighterRobo) r;
```

This compiles with the explicit cast but fails at runtime because the actual object is a `PlayerRobo`.

Result:

```text
ClassCastException
```

---

# 18. Method Overloading vs Method Overriding

This distinction is extremely important.

| Feature              | Overloading                          | Overriding                                       |
| -------------------- | ------------------------------------ | ------------------------------------------------ |
| Polymorphism         | Compile-time                         | Runtime                                          |
| Classes required     | Not necessarily                      | Parent-child relationship                        |
| Method name          | Same                                 | Same                                             |
| Parameters           | Must differ                          | Same signature                                   |
| Return type          | Cannot be the only difference        | Must be compatible according to overriding rules |
| Decision             | Compiler                             | Runtime                                          |
| Binding              | Static/Early                         | Dynamic/Late                                     |
| Inheritance required | No                                   | Yes                                              |
| Main purpose         | Multiple ways to call same operation | Different child implementation                   |

---

# 19. Example Comparing Both

```java
class Robo
{
    // Overloading
    public void move()
    {
        System.out.println("Normal movement");
    }

    public void move(int speed)
    {
        System.out.println("Movement speed: " + speed);
    }
}
```

Here:

```text
move()
move(int)
```

→ **Overloading**

Now:

```java
class FighterRobo extends Robo
{
    // Overriding
    public void move()
    {
        System.out.println("Fighter movement");
    }
}
```

Here:

```text
Robo.move()
      ↓
FighterRobo.move()
```

→ **Overriding**

---

# 20. Can Static Methods Be Overridden?

### No.

A static method belongs to the class rather than participating in runtime overriding based on the object.

Example:

```java
class Robo
{
    public static void show()
    {
        System.out.println("Robo");
    }
}

class FighterRobo extends Robo
{
    public static void show()
    {
        System.out.println("Fighter Robo");
    }
}
```

This is called:

> **Method Hiding**

not overriding.

Therefore:

```text
Instance method → Overriding
Static method   → Hiding
```

Static methods do not provide runtime polymorphism through overriding.

---

# 21. Can Private Methods Be Overridden?

No.

Example:

```java
class Robo
{
    private void show()
    {
        System.out.println("Robo");
    }
}

class FighterRobo extends Robo
{
    private void show()
    {
        System.out.println("Fighter");
    }
}
```

The child method is not an override of the parent's private method.

Why?

Because the parent private method is not accessible to the child.

---

# 22. Can Final Methods Be Overridden?

No.

```java
class Robo
{
    public final void move()
    {
        System.out.println("Robo moves");
    }
}
```

The child cannot override:

```java
class FighterRobo extends Robo
{
    public void move()
    {
        // Compilation error
    }
}
```

Therefore:

```text
final method
     ↓
Cannot be overridden
     ↓
Cannot participate in overriding-based runtime polymorphism
```

---

# 23. Can Constructors Be Overridden?

No.

Constructors:

* are not inherited
* cannot be overridden
* are not polymorphic

You can overload constructors:

```java
Robo()
{
}

Robo(int x)
{
}
```

but that is **constructor overloading**, not constructor overriding.

---

# 24. Does Return Type Matter in Overriding?

Yes.

The overriding method cannot arbitrarily change the return type.

For example:

```java
class Robo
{
    public Number getValue()
    {
        return 10;
    }
}

class FighterRobo extends Robo
{
    public Integer getValue()
    {
        return 20;
    }
}
```

This is allowed because `Integer` is a subtype of `Number`.

This is called a **covariant return type**.

But an unrelated return type would not be allowed.

---

# 25. What About Access Modifiers During Overriding?

A child cannot reduce the accessibility of an overridden method.

Example:

```java
class Robo
{
    public void move()
    {
    }
}
```

This is invalid:

```java
class FighterRobo extends Robo
{
    protected void move()
    {
    }
}
```

because:

```text
public → protected
```

reduces accessibility.

A child can maintain or increase accessibility, subject to Java's overriding rules.

---

# 26. `@Override`

Java provides the annotation:

```java
@Override
```

Example:

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
```

It tells the compiler:

> "I intend this method to override a superclass method."

It is highly recommended because the compiler can catch mistakes in the intended override.

---

# 27. Polymorphism with the Robo Example

Let's create a complete example.

```java
class Robo
{
    public void move()
    {
        System.out.println("Robo moves");
    }

    public void interact()
    {
        System.out.println("Robo interacts");
    }
}

class FighterRobo extends Robo
{
    @Override
    public void move()
    {
        System.out.println("Fighter Robo moves");
    }

    public void fight()
    {
        System.out.println("Fighter Robo fights");
    }
}

class PlayerRobo extends Robo
{
    @Override
    public void move()
    {
        System.out.println("Player Robo moves");
    }

    public void play()
    {
        System.out.println("Player Robo plays");
    }
}

class TeacherRobo extends Robo
{
    @Override
    public void move()
    {
        System.out.println("Teacher Robo moves");
    }

    public void teach()
    {
        System.out.println("Teacher Robo teaches");
    }
}
```

Now:

```java
class RoboApp
{
    public static void main(String[] args)
    {
        Robo r;

        r = new FighterRobo();
        r.move();
        r.interact();

        r = new PlayerRobo();
        r.move();
        r.interact();

        r = new TeacherRobo();
        r.move();
        r.interact();
    }
}
```

Output:

```text
Fighter Robo moves
Robo interacts

Player Robo moves
Robo interacts

Teacher Robo moves
Robo interacts
```

---

# 28. What Is Actually Polymorphic Here?

Look at:

```java
r.interact();
```

`interact()` isn't overridden, so the inherited `Robo` implementation executes.

But:

```java
r.move();
```

is overridden by each child.

Therefore:

```text
interact()
    ↓
Same implementation

move()
    ↓
Different implementation depending on object
```

This is the essence of runtime polymorphism.

---

# 29. One Reference — Many Objects

This is one of the best ways to understand polymorphism.

```java
Robo r;

r = new FighterRobo();
r.move();

r = new PlayerRobo();
r.move();

r = new TeacherRobo();
r.move();
```

One reference:

```text
r
```

Different objects:

```text
FighterRobo
PlayerRobo
TeacherRobo
```

Same call:

```text
r.move()
```

Different behavior.

Therefore:

> **One reference, many possible object forms.**

---

# 30. Why Is This Powerful?

Imagine a method:

```java
void makeRoboMove(Robo r)
{
    r.move();
}
```

Now:

```java
makeRoboMove(new FighterRobo());
makeRoboMove(new PlayerRobo());
makeRoboMove(new TeacherRobo());
```

The same method can work with different Robo subclasses.

No need to write:

```text
makeFighterMove()
makePlayerMove()
makeTeacherMove()
```

This makes code more flexible and extensible.

---

# 31. Polymorphism and Loose Coupling

Consider:

```java
void operate(Robo r)
{
    r.move();
}
```

The method doesn't need to know exactly which Robo subclass it receives.

It only needs to know:

> "This object is a Robo."

This reduces dependency on specific implementations and is one reason polymorphism is fundamental to good object-oriented design.

---

# 32. Polymorphism with Interfaces

Runtime polymorphism isn't limited to class inheritance.

Interfaces are also heavily used.

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
     +
Different objects
     +
Same method call
     =
Different behavior
```

---

# 33. Polymorphism Does Not Mean Everything Is Dynamic

This is an important correction to a common misunderstanding.

Not every method call is resolved at runtime.

For example:

```java
c.add(10, 20);
```

with overloaded methods is resolved based on compile-time information.

Whereas:

```java
Robo r = new FighterRobo();
r.move();
```

with an overridden instance method uses runtime dispatch.

So:

```text
Overloading  → Compile-time selection
Overriding   → Runtime dispatch
```

---

# 34. Binding

The term **binding** refers to associating a method call with a method implementation.

### Early Binding

Usually associated with:

```text
Overloading
static methods
private methods
final methods
```

The exact rules are more nuanced, but these do not use virtual overriding dispatch.

### Late/Dynamic Binding

Associated with:

```text
Overridden instance methods
```

Example:

```java
Robo r = new FighterRobo();

r.move();
```

The actual implementation is selected dynamically.

---

# 35. Very Important: Overloading vs Overriding

Don't memorize only:

```text
Overloading = same name
Overriding = same name
```

That's incomplete.

Remember:

### Overloading

```text
Same class or related classes
+
Same method name
+
Different parameter list
```

### Overriding

```text
Parent-child relationship
+
Same method signature
+
Child provides new implementation
```

---

# 36. Does Inheritance Automatically Mean Polymorphism?

### No.

Inheritance provides the parent-child relationship.

Polymorphism occurs when that relationship is used to allow different forms/implementations, particularly through overriding or other polymorphic mechanisms.

Example:

```java
class Robo
{
    public void move()
    {
    }
}

class FighterRobo extends Robo
{
}
```

There is inheritance.

But if nothing is overridden or otherwise polymorphically dispatched, there isn't necessarily an interesting runtime-polymorphism example.

---

# 37. Does Polymorphism Require Inheritance?

For **method overloading**:

❌ No.

```java
class Calculator
{
    void add(int a)
    {
    }

    void add(int a, int b)
    {
    }
}
```

No inheritance is required.

For traditional **runtime method overriding**:

✅ A class inheritance relationship or interface implementation relationship is involved.

---

# 38. Four Important Terms

Memorize these:

```text
Polymorphism
     ↓
One entity → Many forms

Overloading
     ↓
Compile-time polymorphism

Overriding
     ↓
Runtime polymorphism

Dynamic Method Dispatch
     ↓
Runtime selection of overridden instance method
```

---

# 39. Common Mistakes

## Mistake 1

```java
Robo r = new FighterRobo();
r.fight();
```

❌ Not valid if `fight()` exists only in `FighterRobo`.

---

## Mistake 2

Thinking:

```text
Reference type = actual object
```

❌ Wrong.

```java
Robo r = new FighterRobo();
```

means:

```text
Reference type = Robo
Object type    = FighterRobo
```

---

## Mistake 3

Calling static methods overridden.

❌ Static methods are hidden, not overridden.

---

## Mistake 4

Calling constructor overriding.

❌ Constructors cannot be overridden.

---

## Mistake 5

Thinking overloading is runtime polymorphism.

❌ Overloading is resolved using compile-time method selection.

---

# 40. Complete Comparison

| Concept                 | Meaning                                                 | Example                            |
| ----------------------- | ------------------------------------------------------- | ---------------------------------- |
| Polymorphism            | Many forms                                              | Same operation, different behavior |
| Overloading             | Same method name, different parameters                  | `add()`, `add(int)`                |
| Overriding              | Child replaces inherited instance-method implementation | Child `move()`                     |
| Dynamic dispatch        | Runtime selection of overridden method                  | `r.move()`                         |
| Upcasting               | Child object referred to by parent type                 | `Robo r = new FighterRobo()`       |
| Downcasting             | Parent reference converted to child type                | `(FighterRobo) r`                  |
| Method hiding           | Child declares same static method                       | Static `show()`                    |
| Constructor overloading | Multiple constructors with different parameters         | `Robo()`, `Robo(int)`              |

---

# 41. Interview Question: What Is the Difference Between Inheritance and Polymorphism?

### Inheritance

> A mechanism for establishing a parent-child relationship and reusing accessible functionality.

```text
Robo
 ↓
FighterRobo
```

### Polymorphism

> The ability to use one interface/reference or operation with different forms or implementations.

```text
Robo r = new FighterRobo();
Robo r = new PlayerRobo();
Robo r = new TeacherRobo();
```

So:

```text
Inheritance → Relationship
Polymorphism → Different behavior/forms
```

They are closely related but **not the same concept**.

---

# 42. Real-Life Analogy

Think about a common operation:

```text
"Make payment"
```

Different payment types can implement it differently:

```text
              Payment
                 |
       ┌─────────┼─────────┐
       ↓         ↓         ↓
      Card      UPI      Cash
       |         |         |
    pay()      pay()     pay()
```

The operation is:

```text
pay()
```

but its implementation differs.

This is the basic idea behind polymorphism.

---

# 43. 🔥 Ultimate Polymorphism Flow

```text
                         POLYMORPHISM
                              |
               ┌──────────────┴──────────────┐
               ↓                             ↓
        Compile-Time                    Runtime
               |                             |
        Method Overloading             Method Overriding
               |                             |
        Different parameters           Same signature
               |                             |
           Compiler                    Parent → Child
           decides                         |
                                           ↓
                                  Dynamic Method Dispatch
                                           |
                                           ↓
                                    Actual object decides
```

---

# 44. Final Deep-Dive Summary

### Compile-Time Polymorphism

```java
Calculator c = new Calculator();

c.add();
c.add(10);
c.add(10, 20);
```

```text
Same name
+
Different parameter lists
+
Compiler selects method
=
Compile-Time Polymorphism
```

---

### Runtime Polymorphism

```java
Robo r = new FighterRobo();

r.move();
```

```text
Parent reference
+
Child object
+
Overridden instance method
+
Runtime selection
=
Runtime Polymorphism
```

---

# 🧠 The 10 Things You Must Never Forget

```text
1. Polymorphism = Many Forms.

2. Java commonly discusses:
   Compile-Time + Runtime polymorphism.

3. Overloading → Compile-Time polymorphism.

4. Overriding → Runtime polymorphism.

5. Overloading requires different parameter lists.

6. Return type alone cannot overload a method.

7. Overriding requires a parent-child/interface relationship.

8. Parent reference can refer to child object:
   Robo r = new FighterRobo();

9. For an overridden instance method,
   actual object determines the implementation at runtime.

10. Static methods are hidden, not overridden;
    constructors are not overridden.
```

### The most important statement:

> **When a parent-type reference refers to a child object and the child overrides an instance method, calling that method can execute the child's implementation at runtime — this is runtime polymorphism through dynamic method dispatch.**
