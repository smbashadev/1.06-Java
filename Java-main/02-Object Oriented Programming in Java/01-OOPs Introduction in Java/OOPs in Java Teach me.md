👨‍🏫 OOPs Introduction in Java — TEACH ME

Let's learn OOPs from zero, as if you're seeing it for the first time. We'll build the idea step by step.


---

1. First: What Problem Does OOP Solve?

Imagine your teacher says:

> "Write a Java program to store details of 1,000 students."



Each student has:

Roll Number
Name
Age
Marks

and can perform actions such as:

Study
Write Exam
Display Details

If we simply keep creating unrelated variables, the program becomes difficult to manage.

Instead, we say:

> A student is an entity. Let's represent that entity as an object.



That's where OOP comes in.


---

2. What Is OOP?

OOP = Object-Oriented Programming.

It is a programming approach where we design programs using:

Classes
   ↓
Objects
   ↓
Data + Behavior

The basic idea is:

> Keep related data and the operations that work on that data together.




---

3. First Learn "Object"

Think about a real-world student.

A student has:

Data

Name = Ravi
Roll No = 101
Age = 20

Actions

Study
Write Exam
Display Details

In Java:

Data     → Variables
Actions  → Methods

So an object can be thought of as:

Object
  |
  ├── Data
  |
  └── Behavior


---

4. Real-World Example

Think about a Car.

A car has:

Color
Brand
Price
Speed

These are its properties/state.

A car can:

Start
Stop
Accelerate
Brake

These are its behaviors.

In Java:

class Car {

    String color;
    String brand;
    double price;

    void start() {
        System.out.println("Car started");
    }

    void stop() {
        System.out.println("Car stopped");
    }
}

Now we have described what a car object should contain and do.

But we haven't created an actual car object yet.


---

5. Then What Is a Class?

A class is a blueprint/template.

Think about a building blueprint.

The blueprint describes:

Rooms
Doors
Windows
Dimensions

But the blueprint itself isn't the actual building.

Similarly:

Class → Blueprint
Object → Actual instance

Example:

class Student {

    int rollNo;
    String name;

    void display() {
        System.out.println(rollNo);
        System.out.println(name);
    }
}

Here:

Student → Class
rollNo  → Data
name    → Data
display → Behavior


---

6. 🔥 Class vs Object

This is VERY important.

Suppose:

class Student {
    int rollNo;
    String name;
}

Student is the class.

Now:

Student s1 = new Student();

s1 refers to a Student object.

So:

Student
   ↓
Class / Blueprint

s1
   ↓
Reference
   ↓
Student Object


---

7. Let's Create Our First Object

Start with:

class Student {

    int rollNo;
    String name;
}

Now create an object:

Student s1 = new Student();

The keyword:

new

is used here to create an object.

Conceptually:

s1 ─────────→ Student Object


---

8. Put Data Into the Object

We can now write:

s1.rollNo = 101;
s1.name = "Ravi";

Think:

s1
 |
 ├── rollNo = 101
 └── name = Ravi

Now s1 represents a student with those values.


---

9. Add Behavior

Let's add a method:

class Student {

    int rollNo;
    String name;

    void display() {
        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
    }
}

Now:

Student s1 = new Student();

s1.rollNo = 101;
s1.name = "Ravi";

s1.display();

Output:

Roll No : 101
Name    : Ravi

Notice what happened:

Data:
rollNo
name

Behavior:
display()

Both are grouped inside the Student class.


---

10. Complete Single-Object Program

class Student {

    int rollNo;
    String name;

    void display() {

        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
    }

    public static void main(String[] args) {

        Student s1 = new Student();

        s1.rollNo = 101;
        s1.name = "Ravi";

        s1.display();
    }
}

Output:

Roll No : 101
Name    : Ravi

This program has:

1 Class
1 Object
2 Instance variables
1 Method


---

11. 🔥 Now the Most Important Part — Multiple Objects

Suppose we have three students.

Do we create three classes?

❌ No!

We create one class:

class Student {
}

and three objects:

Student s1 = new Student();
Student s2 = new Student();
Student s3 = new Student();

Think:

Student Class
                  |
       ┌──────────┼──────────┐
       ↓          ↓          ↓
      s1         s2         s3
       ↓          ↓          ↓
    Object      Object      Object


---

12. Complete Multiple-Object Program

class Student {

    int rollNo;
    String name;

    void display() {

        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
    }

    public static void main(String[] args) {

        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();

        s1.rollNo = 101;
        s1.name = "Ravi";

        s2.rollNo = 102;
        s2.name = "Kiran";

        s3.rollNo = 103;
        s3.name = "Arjun";

        s1.display();
        s2.display();
        s3.display();
    }
}

Output:

Roll No : 101
Name    : Ravi

Roll No : 102
Name    : Kiran

Roll No : 103
Name    : Arjun


---

13. 🔥 Why Are the Values Different?

This is a very important OOP concept.

We created:

Student s1 = new Student();
Student s2 = new Student();

These are two different objects.

So:

s1.rollNo = 101;
s2.rollNo = 102;

means:

s1
 |
 └── rollNo = 101


s2
 |
 └── rollNo = 102

Each object has its own instance state.


---

14. Object Has Three Important Things

Remember:

1. State

What data does the object currently contain?

rollNo = 101
name = Ravi

2. Behavior

What can it do?

display()
study()

3. Identity

Which particular object is it?

s1
s2

So:

> Object = State + Behavior + Identity




---

15. Now Let's Learn the Four Pillars

OOP is commonly explained using four major pillars:

1. Encapsulation
2. Inheritance
3. Polymorphism
4. Abstraction

Let's learn them one by one.


---

16. Pillar 1 — Encapsulation

The word sounds difficult, but the basic idea is simple:

> Keep data and the methods that operate on that data together, and control access to the data.



Example:

class Student {

    private int marks;

    public void setMarks(int marks) {

        if (marks >= 0 && marks <= 100) {
            this.marks = marks;
        }
    }

    public int getMarks() {
        return marks;
    }
}

Why private?

Because we don't want outside code to freely modify:

marks

Instead, we provide controlled methods:

setMarks()
getMarks()

This is encapsulation.

Simple memory trick:

> Encapsulation = Protect and control data.




---

17. Pillar 2 — Inheritance

Suppose we have:

class Animal {

    void eat() {
        System.out.println("Eating");
    }
}

Now create:

class Dog extends Animal {

    void bark() {
        System.out.println("Barking");
    }
}

Now:

Dog d = new Dog();

d.eat();
d.bark();

Output:

Eating
Barking

Why can Dog use eat()?

Because:

Animal
   ↑
   |
  Dog

Dog inherits accessible members from Animal.

Memory trick:

> Inheritance = Getting features from another class.



It commonly represents an IS-A relationship:

Dog IS-A Animal


---

18. Pillar 3 — Polymorphism

Break the word:

Poly = Many
Morphism = Forms

So:

> Polymorphism = Many forms.



Two major forms are taught in Java:

Compile-time polymorphism
        ↓
Method Overloading

Runtime polymorphism
        ↓
Method Overriding


---

19. Method Overloading

Example:

class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}

Both methods have the same name:

add()

but different parameter lists:

add(int, int)
add(int, int, int)

This is method overloading.


---

20. Method Overriding

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
        System.out.println("Dog barks");
    }
}

Now:

Animal a = new Dog();

a.sound();

Output:

Dog barks

The child provides its own implementation of the inherited method.

This is method overriding and is associated with runtime polymorphism.


---

21. Pillar 4 — Abstraction

Abstraction means:

> Show the essential operation while hiding unnecessary implementation details.



Think about driving a car.

You use:

Steering
Brake
Accelerator

You don't need to know every internal engine operation to drive it.

In Java:

abstract class Vehicle {

    abstract void start();
}

The class says:

Every Vehicle must have start()

but leaves the implementation to subclasses.


---

22. Abstraction Using Interface

Another common Java example:

interface Payment {

    void pay();
}

Then:

class UPI implements Payment {

    public void pay() {
        System.out.println("Payment through UPI");
    }
}

The interface describes what must be done.

The implementing class describes how it is done.


---

23. 🔥 Don't Confuse Encapsulation and Abstraction

This is a very common interview/exam doubt.

Encapsulation

Focuses on:

> Protecting/bundling data and controlling access.



Example:

private int balance;

Abstraction

Focuses on:

> Hiding implementation details and exposing essential behavior.



Example:

abstract void start();

Easy memory:

Encapsulation → HOW data is protected
Abstraction   → WHAT is exposed / implementation hidden


---

24. Class vs Object — Again

Let's make this absolutely clear.

class Student {

    int rollNo;
}

This is a class.

Then:

Student s1 = new Student();

This creates an object.

Then:

s1.rollNo = 101;

This gives that object a value.

So:

Class
 ↓
Student
 ↓
Blueprint

Object
 ↓
s1
 ↓
Actual instance


---

25. One Class, Many Objects

Imagine a cookie cutter.

Cookie cutter → Class
Cookies       → Objects

One cutter can produce many cookies.

Similarly:

Student class
      ↓
 ┌────┼────┐
 ↓    ↓    ↓
s1   s2   s3

One class can create many objects.


---

26. Can Two References Point to One Object?

Yes.

Student s1 = new Student();

Student s2 = s1;

Now:

s1 ─────┐
        ├────→ Same Object
s2 ─────┘

If:

s1.rollNo = 101;

then:

System.out.println(s2.rollNo);

prints:

101

because both references point to the same object.


---

27. What Does new Actually Mean?

When you write:

Student s1 = new Student();

remember:

new
 ↓
request object creation

The result is an object reference, which is assigned to s1.

Don't say:

> "s1 is the object."



For beginner-level precision, say:

> s1 is a reference variable referring to a Student object.




---

28. OOP Is More Than Just Four Pillars

Don't think:

OOP = only four concepts

OOP also involves:

Classes
Objects
Constructors
Access modifiers
Interfaces
Inheritance
Composition
Association
Aggregation
Polymorphism
Abstraction
Encapsulation

The four pillars are the central conceptual categories, not the entire Java language.


---

29. Java Is Not 100% Pure OOP

You may hear:

> "Java is a 100% object-oriented language."



That's not technically precise.

Why?

Java has primitive data types:

int
char
boolean
double
float
byte
short
long

These are not objects.

Java also provides wrapper classes:

Integer
Character
Boolean
Double

So a better statement is:

> Java is a strongly object-oriented language that supports both primitive types and objects.




---

30. OOP in a Real Application

Imagine an online shopping application.

What objects might exist?

Customer
Product
Cart
Order
Payment
Address

A Product might have:

productId
name
price
quantity

and methods:

updatePrice()
checkStock()

An Order might have:

orderId
date
items
total

and methods:

calculateTotal()
placeOrder()
cancelOrder()

Now the application becomes a collection of objects interacting with one another.

That's the heart of OOP.


---

31. 🔥 Think Like an OOP Programmer

When given a problem, don't immediately start writing variables.

First ask:

Question 1:

What are the entities?

Example:

Student
Teacher
Course

Question 2:

What data does each entity have?

Student → rollNo, name
Teacher → id, name
Course  → code, title

Question 3:

What can each entity do?

Student → study()
Teacher → teach()
Course → display()

Question 4:

How are they related?

Teacher → teaches → Course
Student → attends → Course

Now you are thinking in OOP.


---

32. 🔥 Complete Beginner OOP Program

Let's put everything basic together:

class Student {

    // State
    int rollNo;
    String name;

    // Behavior
    void display() {

        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
    }

    public static void main(String[] args) {

        // First object
        Student s1 = new Student();

        s1.rollNo = 101;
        s1.name = "Ravi";

        // Second object
        Student s2 = new Student();

        s2.rollNo = 102;
        s2.name = "Kiran";

        // Calling behavior
        s1.display();
        s2.display();
    }
}

Think about the program as:

Student
               |
             CLASS
               |
       ┌───────┴───────┐
       ↓               ↓
      s1              s2
       ↓               ↓
  101 / Ravi      102 / Kiran


---

33. 🎯 Teacher's Quick Questions

Q: What is OOP?

Answer:

OOP is a programming paradigm that organizes software around objects containing state and behavior.


---

Q: What is a class?

Answer:

A class is a blueprint/template that defines the data and behavior of objects.


---

Q: What is an object?

Answer:

An object is an instance of a class.


---

Q: Can one class have many objects?

Answer:

Yes.

Student s1 = new Student();
Student s2 = new Student();
Student s3 = new Student();


---

Q: Does every object have separate instance variables?

Answer:

Yes. Each object has its own instance state.


---

Q: What are the four pillars?

Answer:

Encapsulation
Inheritance
Polymorphism
Abstraction


---

Q: What is inheritance?

Answer:

A mechanism through which a class derives accessible members from another class.


---

Q: What is polymorphism?

Answer:

The ability for one interface/name to represent multiple forms of behavior.


---

Q: What is encapsulation?

Answer:

Bundling data and behavior together while controlling access to internal data.


---

Q: What is abstraction?

Answer:

Exposing essential behavior while hiding unnecessary implementation details.


---

🧠 FINAL MEMORY MAP

OOP
                     |
        Object-Oriented Programming
                     |
          ┌──────────┴──────────┐
          ↓                     ↓
        CLASS                 OBJECT
          ↓                     ↓
     Blueprint              Instance
          |                     |
          └──────────┬──────────┘
                     ↓
              State + Behavior
                     |
        ┌────────────┼────────────┐
        ↓            ↓            ↓
   Variables       Methods      Identity

Then:

OOP
                     |
       ┌─────────────┼─────────────┐
       ↓             ↓             ↓
 Encapsulation   Inheritance   Polymorphism
                     |
                 Abstraction

🏆 Remember these five sentences:

> 1. Class is a blueprint.



> 2. Object is an instance of a class.



> 3. One class can create many objects.



> 4. An object combines state and behavior.



> 5. The four major OOP pillars are Encapsulation, Inheritance, Polymorphism and Abstraction.



Once these five ideas are completely clear, the rest of Java OOP—constructors, this, inheritance, method overriding, abstract classes, interfaces, encapsulation, and polymorphism—becomes much easier to understand.
