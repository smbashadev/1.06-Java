OOPs Introduction in Java — ONEPAGE

1. What is OOPs?

OOPs = Object-Oriented Programming System

It is a programming approach where a program is designed around objects and the classes that define them.

Java is primarily an object-oriented programming language.

Simple idea:

Class → Blueprint
Object → Real instance created from the blueprint

Example:

Class: Student
        ↓
 ┌──────┴──────┐
 ↓             ↓
Object 1      Object 2
Ravi          Kiran


---

2. What is a Class?

A class is a blueprint/template used to define the properties and behavior of objects.

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
rollNo  → Property / Data
name    → Property / Data
display → Behavior / Method

A class itself is not an individual student. It describes what a Student object will contain and do.


---

3. What is an Object?

An object is an instance of a class.

Syntax:

ClassName reference = new ClassName();

Example:

Student s1 = new Student();

Here:

Student → Class
s1      → Reference variable
new     → Creates an object
Student() → Constructor invocation


---

4. Object Has Three Important Characteristics

An object can be understood through:

1. State

Data/properties of the object.

rollNo = 101
name = "Ravi"

2. Behavior

What the object can do.

display()
study()
writeExam()

3. Identity

The particular object that distinguishes it from another object.

s1 ≠ s2


---

5. Java Program Having a Single Object

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

Output

Roll No : 101
Name    : Ravi

Here:

Student
   ↓
  s1
   ↓
One Student object

This program contains one object.


---

6. Java Program Having Multiple Objects

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

Output

Roll No : 101
Name    : Ravi

Roll No : 102
Name    : Kiran

Roll No : 103
Name    : Arjun

Important:

All three objects belong to the same class, but each object has its own instance data.

Student Class
                   |
        ┌──────────┼──────────┐
        ↓          ↓          ↓
       s1         s2         s3
       ↓          ↓          ↓
    Ravi/101   Kiran/102   Arjun/103


---

7. Why Do We Need Multiple Objects?

Suppose we need to represent 1,000 students.

Instead of creating 1,000 separate classes:

Student1
Student2
Student3
...

we create one Student class and many objects:

Student s1 = new Student();
Student s2 = new Student();
Student s3 = new Student();

This is one of the major benefits of OOP.


---

8. Four Main Pillars of OOP

Java OOP is commonly explained using four major concepts:

1. Encapsulation

Wrapping data and methods together and controlling access to the data.

class Student {

    private int rollNo;

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public int getRollNo() {
        return rollNo;
    }
}


---

2. Inheritance

One class acquiring properties/behavior from another class.

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

Animal
   ↑
   |
 Dog


---

3. Polymorphism

One name, many forms.

Two major forms commonly taught in Java:

Compile-time polymorphism
        ↓
Method Overloading

Runtime polymorphism
        ↓
Method Overriding

Example of overloading:

class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}


---

4. Abstraction

Showing essential details while hiding unnecessary implementation details.

Example:

abstract class Vehicle {

    abstract void start();
}

A user knows what operation is available without necessarily seeing its complete implementation.


---

9. Class vs Object

Class	Object

Blueprint/template	Instance of class
Logical definition	Runtime entity
Defines properties and behavior	Contains actual values/state
Used to create objects	Created from a class
Example: Student	Example: s1



---

10. new Keyword

The new keyword is commonly used to create an object:

Student s1 = new Student();

Think:

new Student()
     ↓
creates Student object
     ↓
reference stored in s1


---

11. Reference Variable vs Object

This is a common beginner doubt.

Student s1 = new Student();

There are two different things here:

s1             → reference variable
new Student()  → object creation expression

Conceptually:

s1 ───────────→ Student Object

The variable s1 refers to the object.


---

12. OOP vs Procedural Approach

Procedural approach

Focuses primarily on:

Functions + sequence of operations

OOP approach

Focuses primarily on:

Classes + Objects + their relationships

For large programs, OOP provides useful mechanisms for organizing related data and behavior.


---

13. Java's OOP Nature

Java supports major object-oriented features such as:

Class
Object
Encapsulation
Inheritance
Polymorphism
Abstraction

However, Java is not "100% pure OOP" in the strict textbook sense because it has primitive data types such as:

int
char
boolean
double

which are not objects.


---

🧠 ONE-PAGE REVISION

OOPs
                     |
              Object-Oriented
              Programming
                     |
          ┌──────────┴──────────┐
          ↓                     ↓
        CLASS                 OBJECT
     Blueprint              Instance
          |                     |
          └─────── creates ─────┘
                     |
        ┌────────────┼────────────┐
        ↓            ↓            ↓
      State       Behavior      Identity
        |
   Data/Fields

Four Pillars

Encapsulation
      ↓
Inheritance
      ↓
Polymorphism
      ↓
Abstraction

Most Important Formula

> Class = Blueprint
Object = Instance of a Class



Single Object

Student s1 = new Student();

Multiple Objects

Student s1 = new Student();
Student s2 = new Student();
Student s3 = new Student();

One class can create many objects, and each object can maintain its own instance state.
