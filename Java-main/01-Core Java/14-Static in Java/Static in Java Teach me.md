🎓 STATIC IN JAVA — TEACH ME

Let's learn static from zero → understanding → programs → interview/exam level.

The biggest question we want to answer is:

> Why does Java have static, and when should I use it?




---

1. First, Understand Objects

Suppose we create a Student class:

class Student {

    int rollNo;
    String name;
}

Now:

Student s1 = new Student();
Student s2 = new Student();

We have two objects.

Think of them as two students:

Student Class
     |
     ├── s1
     │    ├── rollNo
     │    └── name
     │
     └── s2
          ├── rollNo
          └── name

Each object gets its own copy of instance variables.

For example:

s1.rollNo = 101;
s2.rollNo = 102;

So:

s1 → rollNo = 101
s2 → rollNo = 102

That's exactly what we want.


---

2. But What If Something Is COMMON?

Imagine both students belong to:

ABC College

We could write:

class Student {

    int rollNo;
    String name;
    String college;
}

Then every object has its own college.

s1 → college = ABC College
s2 → college = ABC College
s3 → college = ABC College

But logically, the college is common.

This is where static becomes useful.


---

3. Introduce static

Write:

class Student {

    int rollNo;
    String name;

    static String college = "ABC College";
}

Now:

rollNo → individual
name   → individual
college → shared

Think:

Student Class
                      |
                static college
                      |
                 ABC College
                      |
          ┌───────────┼───────────┐
          ↓           ↓           ↓
         s1          s2          s3
      rollNo=101  rollNo=102  rollNo=103

That is the basic meaning of static.

> Static means the member belongs to the class rather than to each individual object.




---

4. Easy Real-Life Example 🏫

Imagine a school.

Every student has:

Name
Roll Number

These are different.

But all students may have:

School Name

which is common.

So:

class Student {

    String name;
    int rollNo;

    static String school = "ABC School";
}

Think:

Student 1 → name, rollNo
Student 2 → name, rollNo
Student 3 → name, rollNo

                  ↓
           ABC School
             STATIC


---

5. Static Variable

A variable declared using static is called a:

> Static variable / Class variable



Example:

static int count;

or:

static String college = "ABC";


---

6. Let's See the Difference

Without static

class Student {

    int count = 0;

    Student() {
        count++;
        System.out.println(count);
    }

    public static void main(String[] args) {

        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();
    }
}

Output:

1
1
1

Why?

Each object has its own count.

s1 → count = 1
s2 → count = 1
s3 → count = 1


---

7. Now Add static

class Student {

    static int count = 0;

    Student() {
        count++;
        System.out.println(count);
    }

    public static void main(String[] args) {

        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();
    }
}

Output:

1
2
3

Why?

Because there is one shared count.

Student Class
                  |
             static count
                  |
                  3
             /    |    \
           s1     s2    s3


---

8. ⭐ This Is the Main Idea

Remember this:

WITHOUT static
→ object-level
→ separate copy

WITH static
→ class-level
→ shared copy


---

9. What Are the Types of static We Learn?

For your Java syllabus, focus on:

1. Static variable

static int x;

2. Static method

static void display() {
}

3. Static block

static {
}

There is also a static nested class, which is an advanced topic.


---

10. Static Method

Now suppose we have:

class Calculator {

    static int add(int a, int b) {
        return a + b;
    }
}

Because add() is static, we can call it using the class:

Calculator.add(10, 20);

No object is required.


---

11. Why No Object?

Because:

static int add(...)

belongs to the class.

Think:

Calculator
    |
    └── static add()

Instead of:

Calculator
    |
    ├── object 1 → add()
    ├── object 2 → add()
    └── object 3 → add()


---

12. Complete Static Method Program

class Calculator {

    static int add(int a, int b) {

        return a + b;
    }

    public static void main(String[] args) {

        int result = Calculator.add(10, 20);

        System.out.println(result);
    }
}

Output:

30


---

13. Static Method — Easy Rule

If a method is static:

static void display()

you can call:

ClassName.display();

Example:

Calculator.add(10, 20);
Student.display();


---

14. Now Comes the Most Important Confusion 🚨

Can a static method directly access an instance variable?

Suppose:

class Demo {

    int x = 100;

    static void display() {

        System.out.println(x);
    }
}

❌ Compilation error

Why?

Because:

x → belongs to object
display() → belongs to class

Java asks:

> "Which object's x do you want?"



Maybe:

object1.x
object2.x
object3.x

There is no object reference.

Therefore Java doesn't allow it directly.


---

15. How Can We Access It?

Create an object:

class Demo {

    int x = 100;

    static void display() {

        Demo d = new Demo();

        System.out.println(d.x);
    }

    public static void main(String[] args) {

        display();
    }
}

Output:

100

Now we explicitly told Java:

> Use d.x.




---

16. Static Method Can Access Static Variable

This is completely fine:

class Demo {

    static int x = 100;

    static void display() {

        System.out.println(x);
    }

    public static void main(String[] args) {

        display();
    }
}

Output:

100

Why?

Both are class-level.

Class
 ├── static x
 └── static display()


---

17. Remember This Table 🧠

From	Access static	Access instance directly

Static method	✅ Yes	❌ No
Instance method	✅ Yes	✅ Yes


This table is extremely important.


---

18. What About this?

You already learned this.

this means:

> Current object



Example:

class Student {

    int rollNo;

    void display() {
        System.out.println(this.rollNo);
    }
}

Here this works because display() is an instance method.

But:

static void display() {

    System.out.println(this.rollNo);
}

❌ Error

Why?

Because static method doesn't have a current object.

So:

static method → no this


---

19. Static Block

Now we have:

static {
    // code
}

This is called a:

> Static initialization block



Example:

class Demo {

    static {
        System.out.println("Hello");
    }

    public static void main(String[] args) {

        System.out.println("Main");
    }
}

Output:

Hello
Main


---

20. Why Did Static Block Run First?

Because the static block is part of class initialization.

A simplified way to remember it:

Class initialization
       ↓
Static initialization
       ↓
main()

So:

Static Block
     ↓
Main


---

21. Multiple Static Blocks

You can have multiple static blocks:

class Demo {

    static {
        System.out.println("A");
    }

    static {
        System.out.println("B");
    }

    static {
        System.out.println("C");
    }

    public static void main(String[] args) {

        System.out.println("Main");
    }
}

Output:

A
B
C
Main

They execute in source-code order.


---

22. Static Block vs Constructor

This is very important.

Static block

static {
}

is associated with class initialization.

Constructor

Demo() {
}

is associated with object creation.

Look:

class Demo {

    static {
        System.out.println("Static Block");
    }

    Demo() {
        System.out.println("Constructor");
    }

    public static void main(String[] args) {

        System.out.println("Main");

        Demo d1 = new Demo();
        Demo d2 = new Demo();
    }
}

Output:

Static Block
Main
Constructor
Constructor

Notice:

Static Block → once during class initialization
Constructor  → each time an object is created


---

23. Static Variable + Static Block

You can initialize a static variable inside a static block.

class Demo {

    static int x;

    static {
        x = 100;
    }

    public static void main(String[] args) {

        System.out.println(x);
    }
}

Output:

100


---

24. Why Use a Static Block?

Suppose initialization needs multiple statements:

class Demo {

    static int x;

    static {
        System.out.println("Initializing...");
        x = 10 * 20;
    }

    public static void main(String[] args) {

        System.out.println(x);
    }
}

Output:

Initializing...
200


---

25. Is static the Same as final?

❌ No.

This:

static int x = 10;

means:

> One class-level variable.



It can change:

x = 50;

But:

static final int x = 10;

means:

static → class-level
final  → cannot be reassigned

So:

static ≠ constant


---

26. Why Is main() Static?

You have seen:

public static void main(String[] args)

Why static?

Because the JVM needs to invoke the program's entry point without first creating an object of your application class.

If main() were an ordinary instance method, an object would be needed first.

So:

JVM
 ↓
class
 ↓
static main()

That's why main() is static.


---

27. Can We Overload Static Methods?

Yes. ✅

Example:

class Demo {

    static void display() {
        System.out.println("No argument");
    }

    static void display(int x) {
        System.out.println("Integer");
    }

    static void display(String x) {
        System.out.println("String");
    }

    public static void main(String[] args) {

        display();
        display(10);
        display("Java");
    }
}

Output:

No argument
Integer
String

Static methods can therefore participate in method overloading.


---

28. Can Static Methods Be Overridden?

Here's a common interview trap.

Suppose:

class Parent {

    static void display() {
        System.out.println("Parent");
    }
}

class Child extends Parent {

    static void display() {
        System.out.println("Child");
    }
}

This is not runtime overriding.

It is called:

> Method hiding



For example:

Parent p = new Child();

p.display();

Output:

Parent

Static method selection is based on the reference/class context, not dynamic dispatch like an instance method.


---

29. Can We Create a Static Constructor?

❌ No.

This is invalid:

static Demo() {
}

Constructors initialize objects.

static members belong to the class.

Therefore Java doesn't have static constructors.


---

30. Can We Create Static Local Variables?

❌ No.

This is invalid:

void display() {

    static int x = 10;
}

Java does not support static local variables in methods.


---

31. Complete Program — Everything Together

Now let's create one program containing:

✅ Instance variables
✅ Static variable
✅ Static block
✅ Constructor
✅ Instance method
✅ Static method
✅ main()

class Student {

    // Instance variables
    int rollNo;
    String name;

    // Static variable
    static String college;

    // Static block
    static {
        college = "ABC College";
        System.out.println("Static Block Executed");
    }

    // Constructor
    Student(int rollNo, String name) {

        this.rollNo = rollNo;
        this.name = name;
    }

    // Instance method
    void displayStudent() {

        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
        System.out.println("College : " + college);
    }

    // Static method
    static void displayCollege() {

        System.out.println("College : " + college);
    }

    public static void main(String[] args) {

        System.out.println("Main Started");

        // Calling static method
        Student.displayCollege();

        // Creating objects
        Student s1 = new Student(101, "Ravi");
        Student s2 = new Student(102, "Kiran");

        // Calling instance methods
        s1.displayStudent();
        s2.displayStudent();
    }
}

Output

Static Block Executed
Main Started
College : ABC College
Roll No : 101
Name    : Ravi
College : ABC College
Roll No : 102
Name    : Kiran
College : ABC College


---

32. Let's Understand This Program Like a Story

First:

static String college;

Java has one class-level college.


---

Then:

static {
    college = "ABC College";
}

The class-level data is initialized during class initialization.


---

Then:

main()

starts execution.


---

Then:

Student.displayCollege();

Static method is called directly using the class.

No object needed.


---

Then:

Student s1 = new Student(...);
Student s2 = new Student(...);

Two objects are created.

Each gets:

rollNo
name

But they share:

college


---

33. Visualize the Complete Program

STUDENT CLASS
                       |
          ┌────────────┼────────────┐
          ↓            ↓            ↓
    static college   static       main()
                     method
                       |
                displayCollege()
                       |
          ┌────────────┴────────────┐
          ↓                         ↓
         s1                         s2
    rollNo = 101               rollNo = 102
    name = Ravi                name = Kiran
          \                         /
           \                       /
            └── college = ABC ───┘

This picture explains almost everything about static.


---

34. ⭐ One Very Important Mental Trick

Whenever you see:

static

ask yourself:

> "Does this need an individual object's identity?"



If NO, static may be appropriate.

Example:

static String company = "ABC";

Company can be common.

static int add(int a, int b)

Addition doesn't need a particular object.

If YES, use an instance member.

Example:

int rollNo;
String name;

These belong to individual students.


---

35. Static — Final Memory Trick 🧠

Imagine a school notice board.

Student's name:

Different for each student
→ instance

School name:

Same for all students
→ static

School-wide utility:

Calculate something without a particular student
→ static method

School initialization:

Set up common information when class initializes
→ static block


---

🎯 STATIC IN ONE PICTURE

STATIC
                          |
          "BELONGS TO THE CLASS"
                          |
        ┌─────────────────┼─────────────────┐
        ↓                 ↓                 ↓
    VARIABLE            METHOD             BLOCK
        ↓                 ↓                 ↓
    Shared data       No object needed   Class initialization
        ↓                 ↓                 ↓
   static int x     static void test()   static { ... }

🔥 Five rules to memorize

1. static variable
   → one shared class-level variable

2. static method
   → can be called without creating an object

3. static method
   → cannot directly access instance members

4. static block
   → used for class-level initialization

5. static ≠ final
   → static means class-level
   → final means cannot be reassigned

The single best definition:

> static makes a member associated with the class rather than with individual objects.
