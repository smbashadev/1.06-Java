OOPs Introduction in Java — DEEPDIVE

1. What is OOPs?

OOPs = Object-Oriented Programming System.

OOP is a programming approach in which we organize a program around objects, rather than organizing everything merely as a sequence of functions or instructions.

Java strongly supports object-oriented programming through:

Classes

Objects

Encapsulation

Inheritance

Polymorphism

Abstraction

Interfaces

Composition and aggregation


The central idea is:

> Class defines what an object has and can do; an object represents an actual instance of that class.




---

2. Why Was OOP Introduced?

Imagine a large application for a college.

It may need to manage:

Students
Teachers
Courses
Departments
Exams
Fees
Attendance
Results

If we keep everything as unrelated variables and functions, the program can become difficult to maintain.

OOP allows us to group related data + behavior together.

For example:

Student
 ├── rollNo
 ├── name
 ├── marks
 ├── display()
 └── calculateResult()

Similarly:

Teacher
 ├── id
 ├── name
 ├── subject
 └── teach()

This makes the program easier to model and organize.


---

3. Real-World Thinking

Suppose we want to represent a student.

A real student has:

Properties

Name
Roll Number
Age
Marks

Behaviors

Study
Attend class
Write exam
Display details

In OOP:

Properties → Variables/Fields
Behaviors  → Methods

So:

class Student {

    int rollNo;
    String name;

    void study() {
        System.out.println("Student is studying");
    }
}


---

4. What is a Class?

A class is a blueprint or template that describes the properties and behavior of objects.

Example:

class Student {

    int rollNo;
    String name;

    void display() {
        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
    }
}

Here:

Student → Class
rollNo  → Field / Instance variable
name    → Field / Instance variable
display → Method

The class describes what a Student object will contain and what it can do.


---

5. Class Is Not the Object

This is an important distinction.

class Student {
    int rollNo;
    String name;
}

Student is the class.

It is not:

Ravi
Kiran
Arjun

Those can be represented by individual objects.

Think:

Class
 ↓
Blueprint

Object
 ↓
Actual instance

Like:

House blueprint → Class
Actual house     → Object


---

6. What Is an Object?

An object is an instance of a class.

Syntax:

ClassName reference = new ClassName();

Example:

Student s1 = new Student();

Break it down:

Student
   ↓
Data type / class type

s1
   ↓
Reference variable

new
   ↓
Object creation

Student()
   ↓
Constructor invocation

Conceptually:

s1 ─────────→ Student Object


---

7. Object Has State, Behavior and Identity

A useful way to understand an object is through three characteristics.

State

The current values of its data.

rollNo = 101
name = "Ravi"

Behavior

Operations the object can perform.

display()
study()
writeExam()

Identity

The particular object distinguishes it from another object.

s1 ≠ s2

Two students can have the same name, but they are still separate objects.


---

8. Java Program With One Object

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

Student → class
s1      → reference variable
new Student() → object

There is one object.


---

9. Java Program With Multiple Objects

One class can create many objects.

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

Conceptually:

Student Class
                       |
          ┌────────────┼────────────┐
          ↓            ↓            ↓
         s1           s2           s3
          |            |            |
      101/Ravi     102/Kiran    103/Arjun

Each object has its own instance state.


---

10. Why Multiple Objects?

Suppose there are 10,000 students.

We don't create:

StudentClass1
StudentClass2
StudentClass3
...

Instead:

Student s1 = new Student();
Student s2 = new Student();
Student s3 = new Student();

One class can be used to create many objects.

This is one of the fundamental benefits of OOP.


---

11. Instance Variables Belong to Objects

Consider:

class Student {

    int rollNo;
}

Create:

Student s1 = new Student();
Student s2 = new Student();

Then:

s1.rollNo = 101;
s2.rollNo = 102;

Conceptually:

s1
 └── rollNo = 101

s2
 └── rollNo = 102

The two objects have independent rollNo values.


---

12. Static Data Is Different

Suppose the college is common to all students:

class Student {

    int rollNo;
    String name;

    static String college = "ABC College";
}

Now:

Student Class
     |
     └── static college = ABC College
               |
        ┌──────┼──────┐
        ↓      ↓      ↓
       s1     s2     s3

rollNo and name are object-specific.

college is class-level.


---

13. Object Creation and new

Consider:

Student s1 = new Student();

Don't treat the whole statement as one indivisible thing.

Conceptually:

Student s1
    ↓
Reference variable

new Student()
    ↓
Creates an object

Then:

s1 ─────────→ object

The reference allows us to access the object's members.


---

14. Reference Variable vs Object

This is a very common doubt.

Student s1 = new Student();

There are two distinct concepts:

s1

A reference variable.

new Student()

An expression that creates a new object.

So:

s1
 ↓
reference
 ↓
Student object


---

15. Can Two References Refer to the Same Object?

Yes.

Student s1 = new Student();

Student s2 = s1;

Now:

s1 ─────┐
        ├────→ Same Student Object
s2 ─────┘

So:

s1.rollNo = 101;

System.out.println(s2.rollNo);

prints:

101

because both references refer to the same object.


---

16. Can Two Objects Have the Same Values?

Yes.

Student s1 = new Student();
Student s2 = new Student();

s1.rollNo = 101;
s2.rollNo = 101;

They have the same value but are still separate objects.

s1 ─────→ Object A
          rollNo = 101

s2 ─────→ Object B
          rollNo = 101

Same data does not automatically mean same object.


---

17. The Four Main Pillars of OOP

The four concepts traditionally emphasized are:

1. Encapsulation
2. Inheritance
3. Polymorphism
4. Abstraction

Let's understand each.


---

18. Encapsulation

Encapsulation means bundling data and the methods that operate on that data together, while controlling how the data is accessed.

Example:

class BankAccount {

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

Here:

balance
   ↓
private

deposit()
getBalance()
   ↓
controlled access

Instead of allowing arbitrary direct modification:

account.balance = -5000;

we can control changes through methods.

Key idea:

> Encapsulation = data + behavior + controlled access.




---

19. Inheritance

Inheritance allows one class to derive from another class.

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

Output:

Eating
Barking

Conceptually:

Animal
          ↑
          |
         Dog

Dog inherits accessible members from Animal.


---

20. Why Use Inheritance?

Suppose several classes need common behavior:

Animal
 ├── eat()
 └── sleep()

Then:

Dog
Cat
Lion

can inherit common behavior instead of unnecessarily duplicating it.

Inheritance represents an is-a relationship.

Dog is an Animal.
Cat is an Animal.


---

21. Polymorphism

Polymorphism means:

> One interface/name can represent multiple forms of behavior.



In Java, two important forms are:

Compile-time polymorphism
        ↓
Method overloading

Runtime polymorphism
        ↓
Method overriding


---

22. Method Overloading

Same method name, different parameter list.

class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}

Both methods are named:

add()

but have different parameter lists.

add(int, int)
add(int, int, int)

This is method overloading.


---

23. Method Overriding

Suppose:

class Animal {

    void sound() {
        System.out.println("Animal sound");
    }
}

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

The actual object's overridden implementation is selected at runtime.

This is runtime polymorphism.


---

24. Abstraction

Abstraction means exposing the essential operation while hiding unnecessary implementation details.

Example:

abstract class Vehicle {

    abstract void start();
}

A subclass provides the implementation:

class Car extends Vehicle {

    @Override
    void start() {
        System.out.println("Car starts");
    }
}

The abstract class defines the required behavior:

start()

without providing its implementation there.


---

25. Abstraction Through Interfaces

Interfaces are another major Java mechanism for abstraction.

interface Payment {

    void pay();
}

Implementation:

class UPI implements Payment {

    public void pay() {
        System.out.println("Payment through UPI");
    }
}

The user of Payment can depend on the abstraction rather than the implementation details.


---

26. OOP Relationship Diagram

A useful high-level picture:

OOP
                        |
       ┌────────────────┼────────────────┐
       ↓                ↓                ↓
    Classes           Objects       Relationships
       |                |                |
       |                |        ┌───────┼────────┐
       |                |        ↓       ↓        ↓
       |                |     Inheritance  Association
       |                |                    |
       └────────────────┴──────────────→ Composition

The four traditional pillars sit on top:

Encapsulation
Inheritance
Polymorphism
Abstraction


---

27. Association

Association represents a relationship between objects.

Example:

Teacher ───── teaches ───── Student

A teacher can teach students, and a student can have teachers.


---

28. Aggregation

Aggregation is a weaker whole-part relationship.

Example:

Department
    |
    └── Professors

The parts can conceptually exist independently of the whole.


---

29. Composition

Composition represents a stronger whole-part relationship where the part's lifecycle is closely tied to the whole.

Example:

House
 |
 └── Room

Composition is often described as a has-a relationship.


---

30. Is Java 100% Object-Oriented?

Strictly speaking, no.

Why?

Java has primitive types:

int
char
boolean
double
float
long
short
byte

These are not objects.

Java also provides wrapper classes:

Integer
Character
Boolean
Double

which represent corresponding primitive values as objects.

So it is more accurate to say:

> Java is a strongly object-oriented language that supports both primitive types and objects.




---

31. OOP vs Procedural Programming

Procedural	OOP

Focuses on procedures/functions	Focuses on objects/classes
Data and functions may be separate	Data and behavior can be encapsulated
Often top-down	Often modeled around interacting entities
Reuse through functions/modules	Reuse through classes, composition, inheritance, etc.
Suitable for many algorithmic tasks	Particularly useful for large object-rich systems


Neither approach is universally superior for every problem.


---

32. Real-World Example — Banking

Suppose we design a banking application.

We can identify objects such as:

Bank
Customer
Account
Transaction
Employee
Loan

For example:

class BankAccount {

    private String accountNumber;
    private double balance;

    void deposit(double amount) {
        balance += amount;
    }

    void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        }
    }
}

The object combines:

Data
 ↓
accountNumber
balance

Behavior
 ↓
deposit()
withdraw()

That's OOP thinking.


---

33. Real-World Example — E-Commerce

An e-commerce system may contain:

Customer
Product
Cart
Order
Payment
Address

A Product might have:

id
name
price
quantity

and behavior such as:

updatePrice()
checkStock()

An Order might contain:

orderId
date
items
total

and behavior:

calculateTotal()
placeOrder()
cancelOrder()

The system becomes a collection of collaborating objects.


---

34. One Class → Many Objects

This is extremely important.

class Employee {

    int id;
    String name;
}

Then:

Employee e1 = new Employee();
Employee e2 = new Employee();
Employee e3 = new Employee();

Conceptually:

Employee Class
                    |
          ┌─────────┼─────────┐
          ↓         ↓         ↓
         e1        e2        e3
          ↓         ↓         ↓
        Object    Object    Object

Each object gets its own instance state.


---

35. Multiple Classes → Multiple Objects

A larger program may look like:

Application
                  |
       ┌──────────┼──────────┐
       ↓          ↓          ↓
    Customer    Product     Order
       ↓          ↓          ↓
     Objects    Objects    Objects

These objects communicate by invoking methods and maintaining relationships.


---

36. Object Communication

Objects can communicate through method calls.

Example:

class Printer {

    void print(String message) {
        System.out.println(message);
    }
}

class Computer {

    void sendToPrinter(Printer p) {
        p.print("Hello");
    }

    public static void main(String[] args) {

        Computer c = new Computer();
        Printer p = new Printer();

        c.sendToPrinter(p);
    }
}

Here the Computer interacts with the Printer object through a method call.


---

37. Why OOP Helps Large Programs

OOP can help with:

Modularity

Different responsibilities can be organized into classes.

Reusability

Classes and components can be reused.

Maintainability

Changes can be localized.

Extensibility

New classes and implementations can be added.

Encapsulation

Internal implementation can be hidden behind controlled interfaces.

Polymorphism

Code can work with abstractions rather than concrete implementations.


---

38. Important OOP Terms

Term	Meaning

Class	Blueprint/template
Object	Instance of a class
Field	Data/member variable
Method	Behavior/operation
Constructor	Initializes an object during creation
Encapsulation	Bundling and controlled access
Inheritance	Deriving one class from another
Polymorphism	One interface/name, multiple forms
Abstraction	Exposing essential behavior while hiding implementation



---

39. Complete Basic OOP Program

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

        // Object 1
        Student s1 = new Student();

        s1.rollNo = 101;
        s1.name = "Ravi";

        // Object 2
        Student s2 = new Student();

        s2.rollNo = 102;
        s2.name = "Kiran";

        // Behavior
        s1.display();
        s2.display();
    }
}

Output

Roll No : 101
Name    : Ravi
Roll No : 102
Name    : Kiran

The important relationship is:

Student
   ↓
Class
   ↓
Creates
   ↓
Objects
   ↓
s1, s2
   ↓
Each has its own state


---

40. Class + Object + Method — Complete Picture

CLASS
                           |
                     Student
                           |
             ┌─────────────┴─────────────┐
             ↓                           ↓
          DATA                        BEHAVIOR
             ↓                           ↓
        rollNo, name                  display()
             |                           |
             └─────────────┬─────────────┘
                           ↓
                     OBJECT CREATION
                           ↓
              ┌────────────┴────────────┐
              ↓                         ↓
             s1                        s2
              ↓                         ↓
         101, Ravi                 102, Kiran


---

41. 🔥 Important Doubts

Q1. Is a class an object?

Generally, no.

A class is a type/blueprint; an object is an instance of a class.


---

Q2. Can one class create multiple objects?

Yes.

Student s1 = new Student();
Student s2 = new Student();


---

Q3. Does every object have separate instance variables?

Yes, each object has its own instance state.


---

Q4. Can objects of the same class have different values?

Yes.

s1 → rollNo = 101
s2 → rollNo = 102


---

Q5. Can two references point to the same object?

Yes.

Student s1 = new Student();
Student s2 = s1;


---

Q6. Does new create a class?

No.

It creates an object.


---

Q7. Is s1 the object?

Strictly speaking:

Student s1 = new Student();

s1 is the reference variable; new Student() creates the object.


---

Q8. Is Java purely object-oriented?

No, not strictly, because Java has primitive types.


---

42. 🧠 The OOP Mental Model

Whenever you see an OOP problem, ask:

Step 1 — What are the entities?

Student
Teacher
Course

Step 2 — What data does each entity have?

Student → name, rollNo
Teacher → name, subject
Course  → code, title

Step 3 — What can each entity do?

Student → study()
Teacher → teach()
Course  → displayDetails()

Step 4 — What relationships exist?

Student → enrolls in → Course
Teacher → teaches → Course

This is how you begin modeling a real system using OOP.


---

🏆 DEEPDIVE SUMMARY

OOP
                          |
                 Object-Oriented
                 Programming
                          |
              ┌───────────┴───────────┐
              ↓                       ↓
            CLASS                   OBJECT
              ↓                       ↓
          Blueprint                Instance
              |                       |
       ┌──────┴──────┐         ┌──────┴──────┐
       ↓             ↓         ↓             ↓
     State        Behavior    State       Behavior
       ↓             ↓         ↓             ↓
    Fields         Methods   Values        Methods

Four pillars:

Encapsulation → Bundle + control access
Inheritance   → Reuse/derive behavior
Polymorphism  → One interface/name, multiple forms
Abstraction   → Hide implementation details

Most important formulas:

> Class = Blueprint



> Object = Instance of Class



> One Class → Many Objects



> Object = State + Behavior + Identity



> Encapsulation = Data + Methods + Controlled Access



> Polymorphism = One interface/name + Multiple forms



> Inheritance = IS-A relationship



> Composition = HAS-A relationship



And the most important OOP idea:

> Don't think only in terms of "What steps should the program perform?" Think in terms of "What objects exist, what data do they own, what can they do, and how do they interact?"
