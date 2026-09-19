# Inheritance in Java — TEACHME

Let's learn **Inheritance from zero**, using your **Robo example** exactly in the way you are studying it.

---

# 1. First Understand the Problem

Imagine we have three robots:

```text
Fighter Robo
Player Robo
Teacher Robo
```

All three robots have some **common activities**:

```text
move()
learn()
recharge()
interact()
```

But each robot also has its **own special activity**:

```text
Fighter Robo → fight()
Player Robo  → play()
Teacher Robo → teach()
```

So instead of writing the common methods three times, we create one common class:

```text
                         Robo
                          |
             ┌────────────┼────────────┐
             ↓            ↓            ↓
       FighterRobo    PlayerRobo   TeacherRobo
             |            |            |
          fight()       play()       teach()
```

This is the basic idea of **Inheritance**.

---

# 2. What Is Inheritance?

### Simple Definition

> **Inheritance is the process by which one class acquires the accessible properties and methods of another class.**

In our example:

```text
Robo → Parent class
FighterRobo → Child class
PlayerRobo → Child class
TeacherRobo → Child class
```

The child classes reuse the common functionality of `Robo`.

---

# 3. Remember This Real-Life Example

Think about a **family**.

A child can receive certain characteristics from a parent.

Similarly, in Java:

```text
Parent Class
     ↓
Child Class
```

The child can acquire accessible members from the parent.

That's why we call it:

> **Inheritance**

---

# 4. Parent and Child Class

There are different names for the same relationship.

### Parent class

Also called:

```text
Parent Class
Super Class
Base Class
```

### Child class

Also called:

```text
Child Class
Sub Class
Derived Class
```

For our example:

```text
Robo
 ↓
Parent / Super / Base Class
```

and:

```text
FighterRobo
PlayerRobo
TeacherRobo
 ↓
Child / Sub / Derived Classes
```

---

# 5. How Do We Create Inheritance?

Java provides the keyword:

```java
extends
```

Example:

```java
class FighterRobo extends Robo
{
}
```

Read this as:

> **FighterRobo is a Robo.**

This is called an **IS-A relationship**.

```text
FighterRobo IS-A Robo
```

Similarly:

```text
PlayerRobo IS-A Robo

TeacherRobo IS-A Robo
```

---

# 6. Let's Build the Parent Class First

Our parent class is:

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
```

It has four methods:

```text
move()
learn()
recharge()
interact()
```

These are common to all robots.

---

# 7. Now Create FighterRobo

```java
class FighterRobo extends Robo
{
    public void fight()
    {
        System.out.println("Robo fights");
    }
}
```

Notice something important.

We did **not** write:

```text
move()
learn()
recharge()
interact()
```

inside `FighterRobo`.

Why?

Because `FighterRobo` inherits them from `Robo`.

So FighterRobo can use:

```text
move()       ← inherited
learn()      ← inherited
recharge()   ← inherited
interact()   ← inherited
fight()      ← FighterRobo's own method
```

---

# 8. Let's Create PlayerRobo

```java
class PlayerRobo extends Robo
{
    public void play()
    {
        System.out.println("Robo plays games");
    }
}
```

PlayerRobo can use:

```text
move()       ← inherited
learn()      ← inherited
recharge()   ← inherited
interact()   ← inherited
play()       ← own method
```

---

# 9. Let's Create TeacherRobo

```java
class TeacherRobo extends Robo
{
    public void teach()
    {
        System.out.println("Robo teaches");
    }
}
```

TeacherRobo can use:

```text
move()       ← inherited
learn()      ← inherited
recharge()   ← inherited
interact()   ← inherited
teach()      ← own method
```

---

# 10. Complete Program

Now combine everything:

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

# 11. Understand the First Object

Look at:

```java
FighterRobo fr = new FighterRobo();
```

Now:

```java
fr.move();
```

Where is `move()`?

Not inside `FighterRobo`.

It is inside:

```text
Robo
```

But because:

```java
class FighterRobo extends Robo
```

`FighterRobo` can access the inherited method.

So:

```java
fr.move();
```

works.

---

# 12. What Can `fr` Access?

The object:

```java
FighterRobo fr = new FighterRobo();
```

can access:

```text
                 FighterRobo object
                         |
          ┌──────────────┼──────────────┐
          ↓              ↓              ↓
       Robo methods   own method      inherited
          |               |
     move()             fight()
     learn()
     recharge()
     interact()
```

Therefore:

```java
fr.move();
fr.learn();
fr.recharge();
fr.interact();
fr.fight();
```

all work.

---

# 13. Why Do We Use Inheritance?

Suppose there was **no inheritance**.

We would have to write:

```java
class FighterRobo
{
    public void move() { }
    public void learn() { }
    public void recharge() { }
    public void interact() { }
    public void fight() { }
}
```

Then again:

```java
class PlayerRobo
{
    public void move() { }
    public void learn() { }
    public void recharge() { }
    public void interact() { }
    public void play() { }
}
```

And again:

```java
class TeacherRobo
{
    public void move() { }
    public void learn() { }
    public void recharge() { }
    public void interact() { }
    public void teach() { }
}
```

The common code is repeated.

### With inheritance:

```text
Robo
 |
 ├── move()
 ├── learn()
 ├── recharge()
 └── interact()
```

Write the common methods **only once**.

That is called:

> **Code Reusability**

---

# 14. The Most Important Formula

Remember:

```text
Parent
   +
Child's own methods
   =
Child's available functionality
```

For example:

```text
Robo
 ├── move()
 ├── learn()
 ├── recharge()
 └── interact()

        +

FighterRobo
 └── fight()

        ↓

FighterRobo can use
move()
learn()
recharge()
interact()
fight()
```

---

# 15. Type 1 — Single Inheritance

Now let's learn the types.

## Definition

> When one child class inherits from one parent class, it is called **Single Inheritance**.

Diagram:

```text
Parent
   ↓
Child
```

Example:

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

Here:

```text
Animal
  ↓
 Dog
```

This is **Single Inheritance**.

---

# 16. Type 2 — Hierarchical Inheritance

This one is very important because **your Robo example is Hierarchical Inheritance**.

### Definition

> **When one parent class is extended by multiple child classes, it is called Hierarchical Inheritance.**

Diagram:

```text
              Parent
            /   |   \
           ↓    ↓    ↓
        Child Child Child
```

Your example:

```text
                    Robo
                 /    |    \
                ↓     ↓     ↓
          Fighter   Player  Teacher
```

So:

```text
Robo
 ↓
FighterRobo

Robo
 ↓
PlayerRobo

Robo
 ↓
TeacherRobo
```

All three have the same parent.

Therefore:

> **Robo example = Hierarchical Inheritance.**

---

# 17. Type 3 — Multilevel Inheritance

Now imagine:

```text
Grand Parent
      ↓
    Parent
      ↓
    Child
```

Exactly three levels.

Example:

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

Diagram:

```text
Animal
  ↓
 Dog
  ↓
Puppy
```

Now:

```java
Puppy p = new Puppy();
```

`p` can use:

```java
p.eat();
p.bark();
p.play();
```

Why?

Because:

```text
Puppy
  ↓
Dog
  ↓
Animal
```

---

# 18. Think of Multilevel Like a Family

```text
Grandfather
     ↓
   Father
     ↓
    Son
```

Similarly:

```text
Animal
   ↓
 Dog
   ↓
Puppy
```

The Puppy belongs to the inheritance chain.

---

# 19. Type 4 — Hybrid Inheritance

Hybrid means:

> **Combination of different inheritance structures.**

Use your example:

```text
                         Animal
                    /       |       \
                   ↓        ↓        ↓
             Herbivores Carnivores Omnivores
                  ↓         ↓          ↓
                 Cow       Tiger       Dog
```

Look carefully.

First:

```text
Animal
 /   |   \
↓    ↓    ↓
H    C    O
```

This is hierarchical.

Then:

```text
Animal
 ↓
Herbivores
 ↓
Cow
```

This is multilevel.

Likewise:

```text
Animal
 ↓
Carnivores
 ↓
Tiger
```

and:

```text
Animal
 ↓
Omnivores
 ↓
Dog
```

Therefore the overall structure combines different inheritance forms.

---

# 20. Diamond Problem

Now comes one of the most important doubts.

Imagine:

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

Then `B` and `C` both have their own `show()`.

Now `D` receives:

```text
B → show()
C → show()
```

Suppose:

```java
D d = new D();

d.show();
```

Which `show()` should Java execute?

```text
B's show()
      OR
C's show()
```

There is ambiguity.

This is called the:

> **Diamond Problem**

---

# 21. Why Java Does Not Allow Multiple Inheritance Through Classes

Multiple inheritance would look like:

```text
       Parent1     Parent2
          \          /
           \        /
             Child
```

One child would have two parent classes.

Java does **not** support this kind of multiple inheritance through classes.

One important reason is avoiding ambiguity such as the Diamond Problem.

Java can support multiple inheritance of type through **interfaces**, with Java's rules for resolving default-method conflicts.

---

# 22. Cyclic Inheritance

Now imagine:

```text
A
↓
B
↓
C
↓
A
```

This creates a circle.

This is called:

> **Cyclic Inheritance**

Java does not allow it.

Why?

Because there is no proper starting parent.

Every class depends on another class in the same cycle.

Inheritance should be hierarchical:

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

# 23. Three Types of Methods During Inheritance

This is very important.

There are three things you should remember:

```text
1. Inherited Method
2. Overridden Method
3. Specialized Method
```

---

# 24. 1. Inherited Method

Parent has a method.

Child uses it without changing it.

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

Now:

```java
FighterRobo fr = new FighterRobo();

fr.move();
```

Output:

```text
Robo moves
```

`move()` is an:

> **Inherited Method**

---

# 25. 2. Overridden Method

Parent has a method.

Child provides its **own implementation** of that method with the same signature.

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

Now:

```java
FighterRobo fr = new FighterRobo();

fr.fight();
```

Output:

```text
Fighter Robo fights strongly
```

The child version is used.

This is:

> **Method Overriding**

---

# 26. 3. Specialized Method

Suppose the parent doesn't have a particular method.

The child creates a new method for its own special behavior.

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
    public void specialAttack()
    {
        System.out.println("Fighter Robo performs special attack");
    }
}
```

Here:

```text
move()
```

is inherited.

And:

```text
specialAttack()
```

is a **Specialized Method**.

---

# 27. All Three Together

Let's put all three into one program.

```java
class Robo
{
    // Inherited method
    public void move()
    {
        System.out.println("Robo moves");
    }

    // This method will be overridden
    public void fight()
    {
        System.out.println("Robo fights");
    }

    // Inherited method
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

Understand it like this:

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

# 28. One Very Important Question

### Does the child receive everything from the parent?

**No.**

Access modifiers matter.

For example:

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

because it is `private`.

But if the parent provides:

```java
public void showBattery()
{
    System.out.println(battery);
}
```

the child can call `showBattery()`.

So remember:

> **Private members are not directly accessible inside the child class.**

---

# 29. Constructors and Inheritance

Another common doubt:

### Are constructors inherited?

**No.**

Constructors are **not inherited**.

But when a child object is created, the parent constructor is executed as part of initialization.

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

Now:

```java
FighterRobo fr = new FighterRobo();
```

Output:

```text
Robo constructor
Fighter Robo constructor
```

Remember:

```text
Parent constructor executes first
             ↓
Child constructor executes next
```

But:

```text
Constructor ≠ inherited member
```

---

# 30. `super` Keyword

In inheritance, `super` is used to refer to the immediate parent class.

For example:

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

Here:

```java
super.move();
```

calls the parent version.

Output:

```text
Robo moves
Fighter Robo moves
```

---

# 31. Inheritance + Polymorphism

Inheritance is also important for polymorphism.

Consider:

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

Because the actual object is:

```text
FighterRobo
```

and its overridden `move()` executes at runtime.

This is one of the foundations of:

> **Runtime Polymorphism**

---

# 32. The Most Important Diagram to Remember

For your Robot example, remember this:

```text
                              Robo
                               |
             ┌─────────────────┼─────────────────┐
             ↓                 ↓                 ↓
       FighterRobo         PlayerRobo        TeacherRobo
             |                 |                 |
          fight()            play()            teach()
```

Parent methods:

```text
move()
learn()
recharge()
interact()
```

Fighter-specific:

```text
fight()
```

Player-specific:

```text
play()
```

Teacher-specific:

```text
teach()
```

---

# 33. Let's Trace One Object Like Your Notebook

Suppose:

```java
FighterRobo fr = new FighterRobo();
```

Think of the object as having access to:

```text
             fr
             ↓
     ┌─────────────────┐
     │ move()          │
     │ learn()         │
     │ recharge()      │
     │ interact()      │
     │ fight()         │
     └─────────────────┘
```

Then:

```java
fr.move();
```

→ `Robo.move()`

```java
fr.learn();
```

→ `Robo.learn()`

```java
fr.recharge();
```

→ `Robo.recharge()`

```java
fr.interact();
```

→ `Robo.interact()`

```java
fr.fight();
```

→ `FighterRobo.fight()`

This is exactly how to think about the program.

---

# 34. Why Your Robo Program Is Hierarchical

Look only at the class relationships:

```text
                 Robo
              /    |    \
             ↓     ↓     ↓
       Fighter   Player  Teacher
```

One parent:

```text
Robo
```

Multiple children:

```text
FighterRobo
PlayerRobo
TeacherRobo
```

Therefore:

> **This is Hierarchical Inheritance.**

---

# 35. Final Memory Trick 🧠

Remember the word:

## **I-R-S**

During inheritance:

```text
I → Inherited
R → Overridden
S → Specialized
```

### Inherited

```text
Parent method → Child uses it
```

### Overridden

```text
Parent method → Child changes implementation
```

### Specialized

```text
Child creates a new method
```

---

# 36. Final Exam Revision

### Inheritance

> Acquiring accessible properties and methods from another class.

### Parent

```text
Super / Base / Parent
```

### Child

```text
Sub / Derived / Child
```

### Keyword

```java
extends
```

### Relationship

```text
IS-A
```

### Main purpose

```text
Code Reusability
```

### Types

```text
1. Single
2. Hierarchical
3. Multilevel
4. Hybrid
```

### Not supported through classes

```text
Multiple Inheritance
```

### Not permitted

```text
Cyclic Inheritance
```

### Diamond Problem

```text
Ambiguity caused by multiple inheritance paths
```

### Three method categories

```text
Inherited
Overridden
Specialized
```

### Robot example

```text
                         Robo
                          |
             ┌────────────┼────────────┐
             ↓            ↓            ↓
       FighterRobo    PlayerRobo   TeacherRobo
             |            |            |
          fight()       play()       teach()
```

And the **golden rule**:

> **Put common functionality in the parent and specialized functionality in the child.**

That is the heart of **Inheritance in Java**.
