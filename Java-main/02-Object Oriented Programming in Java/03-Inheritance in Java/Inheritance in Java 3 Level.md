# Inheritance in Java — 3LEVEL

We will learn the same concept in **3 levels**:

```text
LEVEL 1 → Basic Understanding
LEVEL 2 → Programming Understanding
LEVEL 3 → Deep / Interview Understanding
```

Using your **Robo example** throughout.

---

# 🟢 LEVEL 1 — BASIC UNDERSTANDING

## 1. What is Inheritance?

> **Inheritance is the process by which one class acquires the accessible properties and methods of another class.**

In simple words:

> **Child class can reuse the functionality of Parent class.**

Example:

```text
             Robo
              |
      ┌───────┼────────┐
      ↓       ↓        ↓
   Fighter  Player   Teacher
```

`Robo` is the **Parent/Super class**.

`FighterRobo`, `PlayerRobo`, and `TeacherRobo` are **Child/Sub classes**.

---

## 2. Why Do We Need Inheritance?

Suppose every Robo has:

```text
move()
learn()
recharge()
interact()
```

Instead of writing these methods separately in every child class, we put them inside `Robo`.

Then:

```text
Robo
 ├── move()
 ├── learn()
 ├── recharge()
 └── interact()
```

Child classes can reuse them.

This gives:

> **Code Reusability**

---

# 3. Your Robo Example

```java
class Robo
{
    public void move()
    {
        System.out.println("Robo moves fast");
    }

    public void learn()
    {
        System.out.println("Robo self learns");
    }

    public void recharge()
    {
        System.out.println("Plug in to recharge");
    }

    public void interact()
    {
        System.out.println("Robo interacts");
    }
}

class FighterRobo extends Robo
{
    public void fight()
    {
        System.out.println("Robo fights");
    }
}

class PlayerRobo extends Robo
{
    public void play()
    {
        System.out.println("Robo plays games");
    }
}

class TeacherRobo extends Robo
{
    public void teach()
    {
        System.out.println("Robo teaches");
    }
}
```

Diagram:

```text
                         Robo
                          |
             ┌────────────┼────────────┐
             ↓            ↓            ↓
       FighterRobo    PlayerRobo   TeacherRobo
             |            |            |
          fight()       play()       teach()
```

---

# 4. What Does FighterRobo Get?

```java
class FighterRobo extends Robo
```

`FighterRobo` can use:

```text
move()       → inherited
learn()      → inherited
recharge()   → inherited
interact()   → inherited
fight()      → own method
```

So:

```java
FighterRobo fr = new FighterRobo();

fr.move();
fr.learn();
fr.recharge();
fr.interact();
fr.fight();
```

All are valid.

---

# 5. What Is the Relationship?

Because:

```java
class FighterRobo extends Robo
```

we can say:

```text
FighterRobo IS-A Robo
```

Similarly:

```text
PlayerRobo IS-A Robo
TeacherRobo IS-A Robo
```

This is called an:

> **IS-A relationship**

---

# 6. Main Benefits

Inheritance provides:

```text
        INHERITANCE
             |
     ┌───────┼────────┐
     ↓       ↓        ↓
 Reusability IS-A  Specialization
```

---

# 🟡 LEVEL 2 — PROGRAMMING UNDERSTANDING

Now let's understand how inheritance behaves in actual Java programs.

---

# 7. Complete Program

```java
class Robo
{
    public void move()
    {
        System.out.println("Robo moves fast");
    }

    public void learn()
    {
        System.out.println("Robo self learns");
    }

    public void recharge()
    {
        System.out.println("Plug in to recharge");
    }

    public void interact()
    {
        System.out.println("Robo interacts");
    }
}

class FighterRobo extends Robo
{
    public void fight()
    {
        System.out.println("Robo fights");
    }
}

class PlayerRobo extends Robo
{
    public void play()
    {
        System.out.println("Robo plays games");
    }
}

class TeacherRobo extends Robo
{
    public void teach()
    {
        System.out.println("Robo teaches");
    }
}

class RoboApp
{
    public static void main(String[] args)
    {
        FighterRobo fr = new FighterRobo();

        System.out.println("Fighter Robo");

        fr.move();
        fr.learn();
        fr.recharge();
        fr.interact();
        fr.fight();

        System.out.println("----------------");

        PlayerRobo pr = new PlayerRobo();

        System.out.println("Player Robo");

        pr.move();
        pr.learn();
        pr.recharge();
        pr.interact();
        pr.play();

        System.out.println("----------------");

        TeacherRobo tr = new TeacherRobo();

        System.out.println("Teacher Robo");

        tr.move();
        tr.learn();
        tr.recharge();
        tr.interact();
        tr.teach();
    }
}
```

---

# 8. Understanding the FighterRobo Object

Consider:

```java
FighterRobo fr = new FighterRobo();
```

The object can access:

```text
FighterRobo
     |
     ├── move()
     ├── learn()
     ├── recharge()
     ├── interact()
     └── fight()
```

The first four methods come from `Robo`.

The last method comes from `FighterRobo`.

Therefore:

```java
fr.move();
```

calls the inherited `Robo` method.

And:

```java
fr.fight();
```

calls the `FighterRobo` method.

---

# 9. Types of Inheritance

There are four important structures you need to remember.

---

## 1️⃣ Single Inheritance

One parent → One child.

```text
       Animal
          ↓
         Dog
```

Program:

```java
class Animal
{
    public void eat()
    {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal
{
    public void bark()
    {
        System.out.println("Dog barks");
    }
}
```

This is **Single Inheritance**.

---

# 10. 2️⃣ Hierarchical Inheritance

One parent → Multiple children.

```text
                 Robo
              /    |    \
             ↓     ↓     ↓
        Fighter  Player  Teacher
```

Your complete Robo example is **Hierarchical Inheritance**.

Why?

Because:

```text
Robo → FighterRobo
Robo → PlayerRobo
Robo → TeacherRobo
```

There is **one parent and multiple child classes**.

---

# 11. 3️⃣ Multilevel Inheritance

Grandparent → Parent → Child.

```text
Animal
  ↓
Dog
  ↓
Puppy
```

Program:

```java
class Animal
{
    public void eat()
    {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal
{
    public void bark()
    {
        System.out.println("Dog barks");
    }
}

class Puppy extends Dog
{
    public void play()
    {
        System.out.println("Puppy plays");
    }
}
```

Now:

```java
Puppy p = new Puppy();

p.eat();
p.bark();
p.play();
```

The `Puppy` object can access functionality from all three levels.

```text
Puppy
 ↓
Dog
 ↓
Animal
```

---

# 12. 4️⃣ Hybrid Inheritance

Hybrid inheritance is a combination of different inheritance structures.

Your example:

```text
                         Animal
                    /       |       \
                   ↓        ↓        ↓
             Herbivores Carnivores Omnivores
                  ↓         ↓          ↓
                 Cow       Tiger       Dog
```

There is a hierarchical structure:

```text
             Animal
           /   |   \
          ↓    ↓    ↓
     Herbivores Carnivores Omnivores
```

And multilevel paths:

```text
Animal
  ↓
Herbivores
  ↓
Cow
```

Therefore the overall structure is hybrid.

---

# 13. Multiple Inheritance

Multiple inheritance means:

```text
       A       B
        \     /
         \   /
          C
```

One child has multiple parent classes.

Java does **not support multiple inheritance through classes**.

The main reason is ambiguity such as the Diamond Problem.

---

# 14. Diamond Problem

Consider:

```text
             A
            / \
           B   C
            \ /
             D
```

Suppose `A` has:

```java
show()
```

and `B` and `C` both provide their own versions.

Then:

```java
D d = new D();

d.show();
```

creates the question:

```text
Should Java execute:

B's show()

OR

C's show()?
```

This is the **Diamond Problem**.

Java avoids this type of ambiguity by not supporting multiple inheritance through classes.

Java can support multiple inheritance of type through interfaces, with rules for resolving conflicting default methods.

---

# 15. Cyclic Inheritance

Cyclic inheritance means a class hierarchy forms a circle.

```text
A
↓
B
↓
C
↓
A
```

This is not permitted.

Why?

Because there is no proper root parent.

Inheritance should form a hierarchy:

```text
A
↓
B
↓
C
```

not:

```text
A → B → C → A
```

---

# 16. Three Types of Methods During Inheritance

Remember:

```text
1. Inherited
2. Overridden
3. Specialized
```

---

## Inherited Method

Parent method is used by child without changing it.

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
}
```

Here:

```text
move() → Inherited
```

---

## Overridden Method

Child provides its own implementation of a parent method with the same signature.

```java
class Robo
{
    public void fight()
    {
        System.out.println("Robo fights");
    }
}

class FighterRobo extends Robo
{
    public void fight()
    {
        System.out.println("Fighter Robo fights strongly");
    }
}
```

Here:

```text
fight() → Overridden
```

---

## Specialized Method

Child introduces a new method that the parent does not have.

```java
class FighterRobo extends Robo
{
    public void specialAttack()
    {
        System.out.println("Fighter Robo performs special attack");
    }
}
```

Here:

```text
specialAttack() → Specialized
```

---

# 17. All Three in One Program

```java
class Robo
{
    public void move()
    {
        System.out.println("Robo moves");
    }

    public void fight()
    {
        System.out.println("Robo fights");
    }

    public void recharge()
    {
        System.out.println("Robo recharges");
    }
}

class FighterRobo extends Robo
{
    // Overridden method
    public void fight()
    {
        System.out.println("Fighter Robo fights strongly");
    }

    // Specialized method
    public void specialAttack()
    {
        System.out.println("Fighter Robo performs special attack");
    }
}

class RoboApp
{
    public static void main(String[] args)
    {
        FighterRobo fr = new FighterRobo();

        fr.move();
        fr.recharge();
        fr.fight();
        fr.specialAttack();
    }
}
```

Classification:

```text
move()
     ↓
Inherited

recharge()
     ↓
Inherited

fight()
     ↓
Overridden

specialAttack()
     ↓
Specialized
```

---

# 🔴 LEVEL 3 — DEEP / INTERVIEW UNDERSTANDING

Now let's handle the doubts that usually cause confusion.

---

# 18. Are Constructors Inherited?

### No.

Constructors are **not inherited**.

But when a child object is created, the parent constructor participates in object initialization.

Example:

```java
class Robo
{
    Robo()
    {
        System.out.println("Robo constructor");
    }
}

class FighterRobo extends Robo
{
    FighterRobo()
    {
        System.out.println("Fighter Robo constructor");
    }
}
```

When:

```java
FighterRobo fr = new FighterRobo();
```

output is:

```text
Robo constructor
Fighter Robo constructor
```

Remember:

```text
Parent constructor
       ↓
Child constructor
```

But:

```text
Constructors are NOT inherited.
```

---

# 19. What About Private Members?

Consider:

```java
class Robo
{
    private int battery = 100;
}
```

The child cannot directly access:

```java
battery
```

because it is private.

But the parent can provide a public method:

```java
class Robo
{
    private int battery = 100;

    public void showBattery()
    {
        System.out.println(battery);
    }
}
```

Then:

```java
class FighterRobo extends Robo
{
}
```

can call:

```java
FighterRobo fr = new FighterRobo();

fr.showBattery();
```

So:

> **Private members are not directly accessible inside the child class.**

---

# 20. Access Modifiers in Inheritance

| Modifier    | Child access                            |
| ----------- | --------------------------------------- |
| `private`   | ❌ Direct access not allowed             |
| default     | ✅ Same package                          |
| `protected` | ✅ Accessible subject to protected rules |
| `public`    | ✅ Accessible                            |

---

# 21. `super` Keyword

`super` is used to refer to the immediate parent class.

It can be used to:

```text
1. Access parent variable
2. Call parent method
3. Call parent constructor
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
    public void move()
    {
        super.move();

        System.out.println("Fighter Robo moves");
    }
}
```

Output:

```text
Robo moves
Fighter Robo moves
```

---

# 22. Inheritance and Runtime Polymorphism

This is extremely important.

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

Because:

```text
Reference type → Robo
Actual object  → FighterRobo
```

The overridden method belonging to the actual object is selected at runtime.

This is **runtime polymorphism**.

---

# 23. Upcasting

```java
Robo r = new FighterRobo();
```

This is called **upcasting**.

```text
FighterRobo
     ↓
    Robo
```

It is valid because:

```text
FighterRobo IS-A Robo
```

---

# 24. Downcasting

```java
Robo r = new FighterRobo();

FighterRobo fr = (FighterRobo) r;
```

This is downcasting.

```text
Robo
 ↓
FighterRobo
```

After downcasting:

```java
fr.fight();
```

can be accessed through the `FighterRobo` reference.

But an invalid downcast can cause:

```text
ClassCastException
```

---

# 25. Final Class

A `final` class cannot be inherited.

```java
final class Robo
{
}
```

This is not allowed:

```java
class FighterRobo extends Robo
{
}
```

because `Robo` is final.

---

# 26. Final Method

A final method can be inherited but cannot be overridden.

```java
class Robo
{
    public final void move()
    {
        System.out.println("Robo moves");
    }
}
```

The child cannot redefine `move()`.

---

# 27. Static Method

Static methods belong to the class.

If a child declares a static method with the same signature as the parent, it is **method hiding**, not overriding.

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

Remember:

```text
Instance method → can be overridden
Static method   → hidden, not overridden
```

---

# 28. Inheritance vs Composition

Don't confuse:

```text
IS-A
```

with:

```text
HAS-A
```

### IS-A → Inheritance

```text
FighterRobo IS-A Robo
Dog IS-A Animal
```

### HAS-A → Composition/Association

```text
Car HAS-A Engine
House HAS-A Room
```

---

# 29. The Complete 3-Level Picture

## LEVEL 1 — Remember the Definition

```text
Inheritance
     ↓
Child acquires accessible functionality
     ↓
Parent
     ↓
Child
     ↓
Code Reusability
```

---

## LEVEL 2 — Understand the Structure

```text
                         Robo
                          |
             ┌────────────┼────────────┐
             ↓            ↓            ↓
       FighterRobo    PlayerRobo   TeacherRobo
             |            |            |
          fight()       play()       teach()
```

Common methods:

```text
move()
learn()
recharge()
interact()
```

Child-specific methods:

```text
fight()
play()
teach()
```

Therefore this is:

> **Hierarchical Inheritance**

---

## LEVEL 3 — Understand the Advanced Concepts

```text
Inheritance
    |
    ├── Code Reusability
    ├── IS-A Relationship
    ├── Method Overriding
    ├── Runtime Polymorphism
    ├── Upcasting
    ├── super
    ├── Constructor execution
    ├── Access modifiers
    ├── final restrictions
    ├── Static method hiding
    ├── Diamond Problem
    └── Cyclic Inheritance
```

---

# 🧠 3LEVEL FINAL REVISION

### Level 1

> **Inheritance means acquiring accessible functionality from a parent class.**

### Level 2

```text
Robo
 |
 ├── FighterRobo
 ├── PlayerRobo
 └── TeacherRobo
```

One parent + multiple children = **Hierarchical Inheritance**.

### Level 3

Remember these:

```text
Inherited       → Parent method reused
Overridden       → Child changes implementation
Specialized      → Child adds new method

Constructor      → Not inherited
Private member   → Not directly accessible in child
final class      → Cannot be inherited
final method     → Cannot be overridden
static method    → Hidden, not overridden

A → B → C        → Multilevel
A → B            → Single
A → B,C,D        → Hierarchical
Combination      → Hybrid

Multiple classes → ❌ Not supported
Cyclic hierarchy → ❌ Not permitted
```

## 🔥 One sentence to remember

> **Inheritance allows a child class to reuse the accessible functionality of its parent, add its own specialized functionality, and override inherited methods when required.**
