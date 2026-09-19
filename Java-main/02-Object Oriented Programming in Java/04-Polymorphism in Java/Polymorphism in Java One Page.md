# Polymorphism in Java — ONEPAGE

## 1. Definition

> **Polymorphism means "one name, many forms."**

In Java, polymorphism means the **same method name or reference can represent different behaviors depending on the situation**.

```text
Poly  = Many
Morphism = Forms

Polymorphism = Many Forms
```

Example:

```text
Robo
  |
  ├── FighterRobo → fight differently
  ├── PlayerRobo  → play differently
  └── TeacherRobo → teach differently
```

---

# 2. Types of Polymorphism in Java

Java mainly has **two types**:

```text
                 POLYMORPHISM
                      |
             ┌────────┴────────┐
             ↓                 ↓
       Compile-Time        Runtime
       Polymorphism        Polymorphism
             |                 |
       Method Overloading  Method Overriding
```

---

# 3. Compile-Time Polymorphism

> When the method to be executed is determined by the compiler, it is called **Compile-Time Polymorphism**.

It is achieved through:

### Method Overloading

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

Same method name:

```text
add()
add(int)
add(int,int)
```

but different parameter lists.

Therefore:

> **Method Overloading = Compile-Time Polymorphism**

---

# 4. Runtime Polymorphism

> When the method implementation to be executed is determined at runtime based on the actual object, it is called **Runtime Polymorphism**.

It is achieved through:

### Method Overriding

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

Why?

```text
Reference type → Robo
Actual object  → FighterRobo
```

The actual object determines the overridden instance method that executes.

---

# 5. Runtime Polymorphism Flow

```text
Robo r = new FighterRobo();
       ↓
Reference → Robo
       ↓
Object → FighterRobo
       ↓
r.move()
       ↓
FighterRobo's move()
```

This is the most important example of **runtime polymorphism**.

---

# 6. Compile-Time vs Runtime Polymorphism

| Feature               | Compile-Time               | Runtime               |
| --------------------- | -------------------------- | --------------------- |
| Also called           | Static/Early Binding       | Dynamic/Late Binding  |
| Main mechanism        | Method Overloading         | Method Overriding     |
| Decision              | Compiler                   | Runtime               |
| Inheritance required? | ❌ No                       | ✅ Normally yes        |
| Same method name?     | ✅ Yes                      | ✅ Yes                 |
| Parameters            | Must differ                | Same signature        |
| Example               | `add(int)`, `add(int,int)` | Parent/child `move()` |

---

# 7. Complete Runtime Polymorphism Program

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

class TeacherRobo extends Robo
{
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

Notice:

```java
r.move();
```

is the **same statement**, but it produces different behavior because `r` refers to different objects.

That is polymorphism.

---

# 8. Upcasting and Runtime Polymorphism

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

It is possible because:

> `FighterRobo IS-A Robo`

Upcasting is commonly used with runtime polymorphism.

---

# 9. Very Important: Reference vs Object

For:

```java
Robo r = new FighterRobo();
```

remember:

```text
Robo r
  ↑
Reference type

new FighterRobo()
      ↑
Actual object
```

The reference type determines **what members are accessible through the reference**, while for an overridden instance method, the actual object's implementation is selected at runtime.

---

# 10. Is Method Overloading Runtime Polymorphism?

❌ **No.**

```java
add();
add(10);
add(10, 20);
```

The compiler determines which overloaded method matches the arguments.

Therefore:

```text
Overloading → Compile Time
Overriding  → Runtime
```

---

# 11. Can Static Methods Provide Runtime Polymorphism?

❌ No.

Static methods are associated with the class, and a same-signature static method in a child is **method hiding**, not overriding.

```text
Instance method → Overriding → Runtime polymorphism
Static method   → Hiding       → Not runtime overriding
```

---

# 12. Can Constructors Be Polymorphic?

❌ Constructors are not overridden.

Therefore constructors are **not used for runtime polymorphism**.

---

# 13. Real-Life Example

Think of:

```text
         Animal
        /      \
       Dog     Cat
```

Both can have:

```java
sound()
```

But:

```text
Dog → Bark
Cat → Meow
```

Same operation:

```text
sound()
```

Different behavior:

```text
Dog object → Bark
Cat object → Meow
```

That is **polymorphism**.

---

# 🔥 FINAL REVISION

```text
                    POLYMORPHISM
                         |
             ┌───────────┴───────────┐
             ↓                       ↓
        Compile-Time              Runtime
             |                       |
       Method Overloading      Method Overriding
             |                       |
       Compiler decides          Object decides
```

### Remember these 5 points:

1. **Polymorphism = One name, many forms.**
2. **Overloading → Compile-Time Polymorphism.**
3. **Overriding → Runtime Polymorphism.**
4. `Parent ref = new Child();` is **upcasting** and is commonly used for runtime polymorphism.
5. For an overridden instance method, **the actual object determines the implementation executed at runtime.**

> **One method call + different actual objects + different overridden behavior = Runtime Polymorphism.**
