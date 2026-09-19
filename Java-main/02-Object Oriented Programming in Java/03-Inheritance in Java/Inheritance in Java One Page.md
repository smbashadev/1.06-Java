# Inheritance in Java — ONEPAGE

## 1. Definition of Inheritance

> **Inheritance is an OOPs concept in Java in which one class acquires the properties and methods of another class.**

Inheritance is mainly used for:

* **Code reusability**
* **Method overriding**
* **Achieving IS-A relationship**
* **Runtime polymorphism**

The existing class is called:

```text
Parent Class / Super Class / Base Class
```

The new class is called:

```text
Child Class / Sub Class / Derived Class
```

### Syntax

```java
class Child extends Parent
{
    // child members
}
```

---

# 2. 🤖 Robot Example — Complete Inheritance

Here `Robo` is the **Parent/Super class**.

It contains common methods:

```text
move()
learn()
recharge()
interact()
```

`FighterRobo`, `PlayerRobo`, and `TeacherRobo` are child classes.

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

### Diagram

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

# 3. Program Execution

```java
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

### Important observation

`FighterRobo` object can access:

```text
move()       → inherited
learn()      → inherited
recharge()   → inherited
interact()   → inherited
fight()      → own method
```

Similarly:

```text
PlayerRobo
 ├── move()       inherited
 ├── learn()      inherited
 ├── recharge()   inherited
 ├── interact()   inherited
 └── play()       own method
```

```text
TeacherRobo
 ├── move()       inherited
 ├── learn()      inherited
 ├── recharge()   inherited
 ├── interact()   inherited
 └── teach()      own method
```

---

# 4. Types of Inheritance

## 1️⃣ Single Inheritance

> One parent class → one child class.

```text
Animal
   ↓
 Dog
```

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

---

# 5. 2️⃣ Hierarchical Inheritance

> **One parent class is extended by multiple child classes.**

```text
             Animal
           /   |   \
          ↓    ↓    ↓
        Dog   Cat   Cow
```

Your Robot program is an example of **Hierarchical Inheritance**:

```text
                 Robo
          /        |        \
         ↓         ↓         ↓
   FighterRobo PlayerRobo TeacherRobo
```

---

# 6. 3️⃣ Multilevel Inheritance

> A class inherits from a parent class, and another class inherits from that child.

Exactly three levels:

```text
Grand Parent
     ↓
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

class Puppy extends Dog
{
    public void play()
    {
        System.out.println("Puppy plays");
    }
}
```

Structure:

```text
Animal
  ↓
Dog
  ↓
Puppy
```

`Puppy` can access accessible methods from both `Dog` and `Animal`.

---

# 7. 4️⃣ Hybrid Inheritance

> **Hybrid inheritance is a combination of two or more types of inheritance.**

Example:

```text
                         Animal
                    /       |       \
                   ↓        ↓        ↓
             Herbivores Carnivores Omnivores
                  ↓         ↓          ↓
                 Cow       Tiger       Dog
```

Here:

```text
Animal
   ↓
Herbivores
   ↓
Cow
```

and:

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

The structure combines **Hierarchical + Multilevel/Single relationships**, so it is considered a hybrid structure.

---

# 8. 💥 Diamond Problem

The diamond structure is:

```text
        A
       / \
      B   C
       \ /
        D
```

Suppose `A` contains:

```java
void show()
```

and both `B` and `C` provide their own `show()`.

Then `D` could receive two versions:

```text
B → show()
C → show()
```

Now:

```text
D.show()
```

creates ambiguity:

```text
Which show() should be executed?
B's show() ?
        OR
C's show() ?
```

This is called the **Diamond Problem**.

### Does Java support multiple inheritance using classes?

**No.**

Java does not allow one class to directly inherit from multiple classes, which avoids this classic ambiguity.

Java can achieve multiple inheritance of type using **interfaces**, subject to Java's conflict-resolution rules.

---

# 9. 🔄 Why Cyclic Inheritance Is Not Permitted?

Cyclic inheritance means inheritance forms a circle.

Example:

```text
A
↓
B
↓
C
↓
A
```

Or:

```text
A → B
↑   ↓
└───┘
```

This is not permitted because there is **no proper starting/root class**.

Inheritance must form a hierarchy:

```text
Parent
   ↓
Child
   ↓
Grandchild
```

not a cycle.

---

# 10. Three Types of Methods During Inheritance

During inheritance, we commonly discuss:

### 1. Inherited Method

Parent method is used by the child without changing its implementation.

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

`move()` is an **inherited method**.

---

### 2. Overridden Method

Parent has a method and child provides its own implementation with the same method signature.

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

`fight()` is **overridden**.

---

### 3. Specialized Method

A method that exists only in the child class.

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

`specialAttack()` is a **specialized method**.

---

# 11. All Three in One Program

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
    // Inherited: move()
    // Inherited: recharge()

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
```

### Classification

```text
Robo
 |
 ├── move()        → Inherited
 |
 ├── recharge()    → Inherited
 |
 └── fight()
       ↓
    Overridden
       
FighterRobo
 |
 └── specialAttack()
          ↓
      Specialized
```

---

# 12. Important Inheritance Terms

| Term               | Meaning                                     |
| ------------------ | ------------------------------------------- |
| Parent/Super class | Class whose members are inherited           |
| Child/Sub class    | Class that inherits from another class      |
| `extends`          | Keyword used to establish class inheritance |
| IS-A relationship  | Relationship represented by inheritance     |
| Inherited method   | Parent method reused by child               |
| Overridden method  | Parent method reimplemented by child        |
| Specialized method | New method introduced by child              |
| Single             | One parent → one child                      |
| Hierarchical       | One parent → multiple children              |
| Multilevel         | Grandparent → Parent → Child                |
| Hybrid             | Combination of inheritance structures       |
| Diamond problem    | Ambiguity from multiple inheritance paths   |
| Cyclic inheritance | Circular inheritance; not permitted         |

---

# 🔥 FINAL REVISION

```text
                    INHERITANCE
                         |
              Parent → Child
                         |
                    IS-A Relationship
                         |
          ┌──────────────┼──────────────┐
          ↓              ↓              ↓
        Reuse       Overriding     Polymorphism
```

### Types:

```text
1. Single

     A
     ↓
     B


2. Hierarchical

       A
     / | \
    B  C  D


3. Multilevel

     A
     ↓
     B
     ↓
     C


4. Hybrid

Combination of different inheritance structures
```

### Robot example:

```text
                         Robo
                          |
             ┌────────────┼────────────┐
             ↓            ↓            ↓
       FighterRobo    PlayerRobo   TeacherRobo
             |            |            |
          fight()       play()       teach()
```

### Three methods:

```text
Inherited  → Parent method used by child

Overridden → Child provides its own implementation

Specialized → Child creates a new method
```

### Remember:

> **Inheritance = Reusability + IS-A Relationship + Specialization + Polymorphism**
