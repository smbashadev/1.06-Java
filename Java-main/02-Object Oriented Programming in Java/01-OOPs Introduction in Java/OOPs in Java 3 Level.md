OOPs in Java — 3LEVEL 🧠🔥

We’ll learn OOPs in 3 levels:

🟢 LEVEL 1 — Foundation: Understand the concepts.

🟡 LEVEL 2 — Programming: Understand how Java implements them.

🔴 LEVEL 3 — Interview/Deep Understanding: Remove common confusion.



---

🟢 LEVEL 1 — FOUNDATION

1. What is OOP?

OOP = Object-Oriented Programming

It is a programming approach where we organize programs around objects and classes.

Instead of thinking only:

> "What instructions should I execute?"



we think:

> "What objects exist, what data do they have, and what can they do?"




---

2. Real-Life Example

Imagine a Car.

A car has:

Data / Properties

color
brand
speed
model

Behaviors / Actions

start()
stop()
accelerate()
brake()

In Java, we can represent this using a class:

class Car {

    String color;
    String brand;
    int speed;

    void start() {
        System.out.println("Car started");
    }

    void stop() {
        System.out.println("Car stopped");
    }
}

Here:

Variables → Data / Properties
Methods   → Behaviors / Actions


---

3. What is a Class?

A class is a blueprint/template for creating objects.

Example:

class Student {

    String name;
    int age;

    void study() {
        System.out.println("Student is studying");
    }
}

Student is a class.

It describes:

Student
 ├── name
 ├── age
 └── study()

But the class itself is not necessarily an individual student.


---

4. What is an Object?

An object is an instance of a class.

Example:

Student s1 = new Student();

Here:

Student → Class
s1      → Reference variable
new Student() → Object

Think:

Class
  ↓
Blueprint

Object
  ↓
Actual thing created from blueprint


---

5. Class vs Object

Class	Object

Blueprint/template	Instance of class
Logical definition	Actual runtime entity
Describes properties/behavior	Has actual state
Doesn't represent one specific instance	Represents a particular instance


Example:

class Student {
    String name;
}

Then:

Student s1 = new Student();
Student s2 = new Student();

Both s1 and s2 are different objects created from the same class.


---

6. Four Main Pillars of OOP

This is the most important part.

Java OOP is commonly explained using four pillars:

OOP
              |
    ┌─────────┼─────────┐
    │         │         │
Encapsulation Inheritance Polymorphism
                         |
                    Abstraction

More commonly arranged as:

1. Encapsulation
2. Inheritance
3. Polymorphism
4. Abstraction


---

7. Encapsulation

Encapsulation = bundling data and methods together and controlling access to the data.

Example:

class BankAccount {

    private double balance;

    public void deposit(double amount) {
        balance = balance + amount;
    }

    public double getBalance() {
        return balance;
    }
}

Here:

private double balance;

prevents direct access from outside the class.

The user interacts through methods:

deposit()
getBalance()

Simple idea:

Data
  +
Methods
  +
Access control
  ↓
Encapsulation


---

8. Inheritance

Inheritance allows one class to acquire accessible properties and behaviors from another class.

Example:

class Animal {

    void eat() {
        System.out.println("Eating");
    }
}

class Dog extends Animal {

    void bark() {
        System.out.println("Barking");
    }
}

Now:

Dog d = new Dog();

d.eat();
d.bark();

Dog gets the inherited eat() behavior from Animal.

Animal
  │
  │ extends
  ↓
Dog

Important terms

Animal → Parent / Superclass
Dog    → Child / Subclass


---

9. Polymorphism

Polymorphism = one interface/name, multiple forms.

The word comes from:

Poly  → Many
Morph → Forms

Java commonly demonstrates polymorphism through:

Compile-time polymorphism

Usually method overloading.

add(int a, int b)
add(double a, double b)

Runtime polymorphism

Method overriding.

class Animal {
    void sound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Bark");
    }
}

Then:

Animal a = new Dog();
a.sound();

Output:

Bark

The actual object's overridden method is selected at runtime.


---

10. Abstraction

Abstraction = exposing essential functionality while hiding implementation details.

Real-life example:

When you use an ATM:

Insert card
Enter PIN
Choose withdrawal
Receive money

You don't need to know the internal banking implementation.

Java provides abstraction mainly through:

abstract classes

interfaces


Example:

abstract class Animal {

    abstract void sound();

    void eat() {
        System.out.println("Eating");
    }
}

The abstract method:

abstract void sound();

specifies what must be provided by subclasses, while the implementation is left to them.


---

🟡 LEVEL 2 — PROGRAMMING

11. A Complete Basic OOP Example

class Student {

    String name;
    int age;

    void study() {
        System.out.println(name + " is studying");
    }

    void display() {
        System.out.println(name);
        System.out.println(age);
    }

    public static void main(String[] args) {

        Student s1 = new Student();

        s1.name = "Rahul";
        s1.age = 20;

        s1.display();
        s1.study();
    }
}

Here:

Student
   ↓
Class

s1
   ↓
Reference variable

new Student()
   ↓
Object

The object contains its own state:

s1
 ├── name = Rahul
 └── age = 20


---

12. Multiple Objects

class Student {

    String name;
    int age;
}

class Demo {

    public static void main(String[] args) {

        Student s1 = new Student();
        Student s2 = new Student();

        s1.name = "Rahul";
        s1.age = 20;

        s2.name = "Priya";
        s2.age = 21;
    }
}

There is one class:

Student

but two objects:

s1 → Student object
s2 → Student object

Each object can have different state.


---

13. Reference Variable vs Object 🔥

This is a major beginner confusion.

Student s1 = new Student();

There are three things here:

Student
   ↓
Reference type

s1
   ↓
Reference variable

new Student()
   ↓
Object

Killer point

> s1 is not the object itself. It is a reference variable that refers to the object.



Conceptually:

s1 ─────────→ Student Object


---

14. Encapsulation in Java

A common implementation is:

class Student {

    private int age;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}

Usage:

Student s = new Student();

s.setAge(20);

System.out.println(s.getAge());

Direct access:

s.age = 20;

is not allowed because age is private.


---

15. Why private?

Suppose:

class BankAccount {

    double balance;
}

Anyone who can access the field might assign inappropriate values.

With:

private double balance;

we control how the data is changed.

For example:

public void deposit(double amount) {

    if (amount > 0) {
        balance += amount;
    }
}

This gives the class control over its internal state.


---

16. Inheritance Example

class Animal {

    void eat() {
        System.out.println("Eating");
    }
}

class Dog extends Animal {

    void bark() {
        System.out.println("Barking");
    }
}

Usage:

Dog d = new Dog();

d.eat();
d.bark();

The Dog object can use the inherited accessible eat() method.


---

17. Types of Inheritance in Java

Conceptually:

1. Single
2. Multilevel
3. Hierarchical
4. Multiple
5. Hybrid

But Java classes do not support multiple inheritance of classes.

Single inheritance

A
|
B

Multilevel inheritance

A
|
B
|
C

Hierarchical inheritance

A
   / \
  B   C

Multiple inheritance

A     B
  \   /
    C

Java does not support this with classes.

However, Java supports multiple inheritance of type through interfaces.


---

18. Method Overriding

Parent:

class Animal {

    void sound() {
        System.out.println("Animal sound");
    }
}

Child:

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Bark");
    }
}

The child provides its own implementation of the inherited method.

This is:

> Method overriding




---

19. Overloading vs Overriding

🔥 Extremely important.

Overloading	Overriding

Same class commonly	Parent-child relationship
Same method name	Same method signature
Different parameter list	Same parameter list
Compile-time polymorphism	Runtime polymorphism
Inheritance not required	Inheritance required


Example overloading:

add(int, int)
add(int, int, int)

Example overriding:

Parent:
sound()

Child:
sound()


---

20. Runtime Polymorphism

Consider:

class Animal {

    void sound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Bark");
    }
}

Now:

Animal a = new Dog();

a.sound();

Question:

Reference type?

Animal

Object type?

Dog

At runtime, Java invokes:

Dog.sound()

Output:

Bark

Killer idea:

> For overridden instance methods, the actual object's implementation is selected at runtime.




---

21. Abstraction Using Abstract Class

abstract class Animal {

    abstract void sound();

    void eat() {
        System.out.println("Eating");
    }
}

Child:

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Bark");
    }
}

You cannot directly instantiate the abstract class:

Animal a = new Animal(); // ❌

But you can:

Animal a = new Dog(); // ✅


---

22. Interface and Abstraction

Example:

interface Vehicle {

    void start();
}

Implementation:

class Car implements Vehicle {

    public void start() {
        System.out.println("Car started");
    }
}

Usage:

Vehicle v = new Car();

v.start();

The interface defines a contract.

The implementing class supplies the implementation.


---

🔴 LEVEL 3 — ADVANCED / INTERVIEW

23. Is Java a Pure OOP Language?

No.

Java is not considered a purely object-oriented language.

Why?

Because Java has primitive data types:

int
char
double
boolean
byte
short
long
float

These are not objects.

Java provides wrapper classes such as:

int     → Integer
char    → Character
double  → Double
boolean → Boolean

and supports autoboxing/unboxing.


---

24. Is everything in Java inside a class?

For ordinary Java programs, methods and fields are declared within classes/interfaces/etc., but not every value is an object because Java has primitives.

For example:

int x = 10;

x is a primitive variable.

Whereas:

String name = "Java";

name refers to a String object.


---

25. Does a Class Create an Object?

Not exactly.

The class is the definition/blueprint.

The new expression creates an object:

Student s = new Student();

Conceptually:

class Student
     ↓
defines structure/behavior

new Student()
     ↓
creates object


---

26. Does Every Class Need an Object?

No.

A class can contain static members:

class Demo {

    static void display() {
        System.out.println("Hello");
    }
}

You can call:

Demo.display();

without creating a Demo object.


---

27. Does Every Method Need an Object?

No.

A static method can be called through the class:

Demo.display();

An instance method normally requires an object:

Demo d = new Demo();
d.display();


---

28. What exactly is Encapsulation?

Don't reduce encapsulation to:

> "Making variables private."



That's incomplete.

A stronger definition is:

> Encapsulation is bundling state and behavior together and controlling access to the internal state through a defined interface.



private is one important Java mechanism used to achieve it.

Example:

class Account {

    private double balance;

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public double getBalance() {
        return balance;
    }
}

The class controls how balance changes.


---

29. Encapsulation vs Abstraction 🔥

This is a classic interview question.

Encapsulation

Focus:

> How do we protect/control the data and implementation details?



Example:

private int balance;

Abstraction

Focus:

> What should the outside world see/use while implementation details remain hidden?



Example:

interface Payment {
    void pay();
}

Memory trick:

ENCAPSULATION
     ↓
HOW to protect/control

ABSTRACTION
     ↓
WHAT to expose


---

30. Inheritance vs Composition

Inheritance:

class Dog extends Animal

means:

> Dog is an Animal.



Composition:

class Car {
    Engine engine;
}

means:

> Car has an Engine.



Memory trick:

IS-A  → Inheritance
HAS-A → Composition


---

31. Why is Composition Often Preferred?

Suppose:

class Car extends Engine

This would incorrectly claim:

> Car is an Engine.



Instead:

class Car {

    private Engine engine;
}

means:

> Car has an Engine.



This gives more flexible object relationships.


---

32. What is Dynamic Method Dispatch?

This is closely connected to runtime polymorphism.

Animal a = new Dog();
a.sound();

At compile time:

Reference type → Animal

At runtime:

Actual object → Dog

For an overridden instance method:

a.sound()
   ↓
actual object is Dog
   ↓
Dog.sound()

This runtime selection is commonly called dynamic method dispatch.


---

33. Important OOP Relationship Map

OOP
                          |
        ┌─────────────────┼─────────────────┐
        |                 |                 |
   Encapsulation      Inheritance      Polymorphism
        |                 |                 |
   Data + behavior    IS-A relation    Many forms
        |                 |                 |
     private        extends/implements  Overloading
     methods                              Overriding
        |
        |
    Abstraction
        |
  Hide unnecessary
  implementation
        |
  ┌─────┴──────┐
  |            |
Abstract      Interface
 class


---

34. The Most Important OOP Confusions

❓ Is class an object?

❌ No.

Class = blueprint/definition.

Object = instance created from the class.


---

❓ Is reference variable an object?

❌ No.

Student s = new Student();

s is a reference variable.

new Student() creates the object.


---

❓ Is private equal to encapsulation?

❌ Not exactly.

private is an access-control mechanism that can help implement encapsulation.


---

❓ Is inheritance the same as polymorphism?

❌ No.

Inheritance establishes a relationship.

Polymorphism allows one interface/reference to work with different implementations/forms.


---

❓ Is overloading runtime polymorphism?

❌ No.

Overloading is generally compile-time polymorphism.


---

❓ Is overriding compile-time polymorphism?

❌ No.

Overriding is associated with runtime polymorphism.


---

❓ Does Java support multiple inheritance?

Classes:

❌ No.

Interfaces:

✅ A class can implement multiple interfaces.

class C implements A, B {
}


---

🧠 FINAL 3LEVEL MASTER MAP

OOPs IN JAVA
                      |
        ┌─────────────┼─────────────┐
        |             |             |
      CLASS         OBJECT        METHOD
        |             |             |
   Blueprint       Instance       Behavior
                      |
                      |
              ┌───────┴───────┐
              |               |
          STATE/DATA       BEHAVIOR

Four pillars:

OOP
               |
      ┌────────┼────────┐
      |        |        |
ENCAPSULATION INHERITANCE POLYMORPHISM
                          |
                      Overloading
                      Overriding

               +
           ABSTRACTION
               |
       Abstract class
       Interface

🔥 Final interview memory

CLASS       → Blueprint
OBJECT      → Instance
ENCAPSULATION → Protect/control state
ABSTRACTION → Hide unnecessary implementation details
INHERITANCE → IS-A relationship
COMPOSITION → HAS-A relationship
POLYMORPHISM → Many forms
OVERLOADING  → Compile-time
OVERRIDING   → Runtime

The single most important picture:

CLASS
               |
          creates via new
               ↓
             OBJECT
               |
       ┌───────┴────────┐
       ↓                ↓
     STATE            BEHAVIOR
    fields            methods
       |
  Encapsulation
       |
  control access

Inheritance
     ↓
reuse/extend relationship

Abstraction
     ↓
expose essential contract

Polymorphism
     ↓
same interface/reference
        ↓
different implementations
