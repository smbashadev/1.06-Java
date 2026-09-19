📌 STATIC IN JAVA — ONEPAGE

1. What is static in Java?

static is a keyword in Java used to make a member belong to the class rather than to individual objects.

It can be used with:

1. Variables


2. Methods


3. Blocks


4. Nested classes



For beginners, remember:

> Object members → separate copy for every object
Static members → one class-level copy shared by objects




---

2. Static Variable

A variable declared with static is called a static variable or class variable.

Syntax

static dataType variableName;

Example:

class Student {

    int rollNo;              // instance variable
    static String college;   // static variable
}

Here:

rollNo  → separate for each object
college → one shared class-level variable


---

3. Java Program WITHOUT Static Variable

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

Output

1
1
1

Why?

Every object gets its own copy of count.

s1 → count = 1
s2 → count = 1
s3 → count = 1


---

4. Java Program WITH Static Variable

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

Output

1
2
3

Why?

There is only one class-level count shared by all objects.

Student.class
                  |
             static count
                  |
        ┌─────────┼─────────┐
        ↓         ↓         ↓
       s1        s2        s3


---

5. Instance Variable vs Static Variable

Instance Variable	Static Variable

Belongs to object	Belongs to class
Each object gets separate copy	One shared copy
Declared without static	Declared with static
Access through object/reference normally	Prefer class name
Created as part of each object's state	Class-level state


Example:

class Student {

    int rollNo;              // instance
    static String college;   // static
}

Preferred access:

Student s = new Student();

s.rollNo = 10;
Student.college = "ABC College";


---

6. Why Do We Use Static Variables?

Use a static variable when a value should be common/shared among all objects.

Example:

class Student {

    int rollNo;
    String name;

    static String college = "ABC College";
}

All students can share the same college value.


---

7. Static Method

A method declared with static is called a static method.

Syntax

static returnType methodName() {
    // statements
}

Example:

class Demo {

    static void display() {
        System.out.println("Hello Java");
    }

    public static void main(String[] args) {

        Demo.display();
    }
}

Output:

Hello Java

Notice:

Demo.display();

We don't need to create an object.


---

8. Java Program Having Static Method

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

9. Static Method Important Rule ⭐

A static method can directly access static members.

class Demo {

    static int x = 10;

    static void display() {
        System.out.println(x);
    }

    public static void main(String[] args) {
        display();
    }
}

Output:

10


---

10. Can Static Method Directly Access Instance Variable?

Normally, no.

Example:

class Demo {

    int x = 10;

    static void display() {
        System.out.println(x); // ERROR
    }
}

Why?

A static method belongs to the class and can execute without an object.

But x belongs to an object.

Correct approach:

class Demo {

    int x = 10;

    static void display() {

        Demo d = new Demo();

        System.out.println(d.x);
    }

    public static void main(String[] args) {
        display();
    }
}


---

11. Can Static Method Access Instance Method Directly?

No.

class Demo {

    void test() {
        System.out.println("Test");
    }

    static void display() {
        test(); // ERROR
    }
}

Because test() belongs to an object.

You can use:

Demo d = new Demo();
d.test();


---

12. Static Block

A static block is a block declared using static.

Syntax

static {
    // statements
}

Example:

class Demo {

    static {
        System.out.println("Static Block");
    }

    public static void main(String[] args) {
        System.out.println("Main Method");
    }
}

Output:

Static Block
Main Method


---

13. When Does Static Block Execute?

A static block executes when the class is initialized, generally before main() when that class is launched through main.

Example:

class Demo {

    static {
        System.out.println("1");
    }

    static {
        System.out.println("2");
    }

    public static void main(String[] args) {
        System.out.println("3");
    }
}

Output:

1
2
3

Static blocks execute in their source-code order.


---

14. Why Do We Use Static Blocks?

Static blocks are commonly used for class-level initialization.

Example:

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

15. Static Variable + Static Block

class Student {

    static String college;

    static {
        college = "ABC College";
    }

    public static void main(String[] args) {

        System.out.println(Student.college);
    }
}

Output:

ABC College


---

16. Can We Have Multiple Static Blocks?

Yes.

class Demo {

    static {
        System.out.println("Block 1");
    }

    static {
        System.out.println("Block 2");
    }

    static {
        System.out.println("Block 3");
    }

    public static void main(String[] args) {
        System.out.println("Main");
    }
}

Output:

Block 1
Block 2
Block 3
Main


---

17. Static Method vs Instance Method

Static Method	Instance Method

Declared with static	No static
Belongs to class	Belongs to object
Can be called using class name	Normally called using object
Doesn't require an object	Requires an object
Directly accesses static members	Can access instance and static members



---

18. Why Is main() Static?

public static void main(String[] args)

main() is static because the JVM needs to invoke it without first creating an object of the class.

Therefore:

JVM
 ↓
class
 ↓
static main()

No application object is required just to start execution through main.


---

19. Can We Create an Object Inside a Static Method?

Yes.

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


---

20. Can We Use this in Static Method?

No.

this represents the current object.

A static method doesn't have a current object.

So:

static void display() {
    System.out.println(this.x); // ERROR
}


---

21. Can We Use super in Static Context?

You cannot use super as an object-instance reference from a static context.

super refers to the current object's superclass portion, so it requires an instance context.


---

22. Static Members and Objects

Consider:

class Student {

    int rollNo;
    static String college = "ABC";

    Student(int rollNo) {
        this.rollNo = rollNo;
    }
}

Create objects:

Student s1 = new Student(101);
Student s2 = new Student(102);

Conceptually:

Student Class
                      |
                 college = ABC
                      |
             ┌────────┴────────┐
             ↓                 ↓
           s1                s2
        rollNo=101         rollNo=102

The rollNo values differ.

The college value is shared.


---

23. 🔥 COMPLETE PROGRAM — ALL STATIC CONCEPTS

This program contains:

✅ Static variable
✅ Static method
✅ Static block
✅ Instance variable
✅ Instance method
✅ Constructor
✅ Object creation
✅ main()

class Student {

    // Instance variable
    int rollNo;

    // Static variable
    static String college;

    // Static block
    static {
        college = "ABC College";
        System.out.println("Static Block Executed");
    }

    // Constructor
    Student(int rollNo) {
        this.rollNo = rollNo;
    }

    // Instance method
    void displayStudent() {
        System.out.println("Roll No : " + rollNo);
        System.out.println("College : " + college);
    }

    // Static method
    static void displayCollege() {
        System.out.println("College : " + college);
    }

    public static void main(String[] args) {

        System.out.println("Main Method Started");

        // Calling static method
        Student.displayCollege();

        // Creating objects
        Student s1 = new Student(101);
        Student s2 = new Student(102);

        // Calling instance methods
        s1.displayStudent();
        s2.displayStudent();
    }
}

Output

Static Block Executed
Main Method Started
College : ABC College
Roll No : 101
College : ABC College
Roll No : 102
College : ABC College


---

🧠 STATIC — ONE-PAGE REVISION

STATIC
                        |
          ┌─────────────┼─────────────┐
          ↓             ↓             ↓
       Variable       Method         Block
          ↓             ↓             ↓
      Class-level    Class-level    Class
       storage       behavior      initialization
          |
          ↓
       One shared
         value

Remember these rules:

static variable
→ one shared class-level variable

static method
→ can be called without object

static block
→ executes when class is initialized

main()
→ static so JVM can invoke it without creating
  an object first

static method
→ directly accesses static members

static method
→ cannot directly access instance members

static context
→ no this
→ no super as an instance reference

instance variable
→ separate copy for each object

⭐ Final Exam Formula

> static means the member belongs to the class rather than to each individual object. A static variable has one shared class-level copy, a static method can be invoked without an object, and a static block is used for class-level initialization.
