🚀 STATIC IN JAVA — 3 LEVEL

Learn static in 3 levels: Basic → Intermediate → Advanced.


---

🟢 LEVEL 1 — BASIC

1. What is static?

static is a Java keyword used to make a member belong to the class rather than to individual objects.

It can mainly be used with:

1. Variables
2. Methods
3. Blocks

Simple formula:

static → class level
non-static → object level


---

2. Static Variable

class Student {

    static String college = "ABC College";
}

college is shared by all objects.

Student
   |
   ↓
college = ABC College
   |
 ┌─┴─┐
s1  s2


---

3. Static Variable Example

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

There is only one shared count.


---

4. Without Static

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

Because each object has its own count.


---

5. Static Method

A method declared with static is called a static method.

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

No object is required.


---

6. Static Block

A static block is:

static {
    // statements
}

Example:

class Demo {

    static {
        System.out.println("Static Block");
    }

    public static void main(String[] args) {

        System.out.println("Main");
    }
}

Output:

Static Block
Main

The static block executes during class initialization.


---

🟡 LEVEL 2 — INTERMEDIATE

7. Static vs Instance

Static	Instance

Belongs to class	Belongs to object
Shared class-level member	Separate for each object
Object not required for access	Object normally required
Declared with static	No static
Example: Student.college	Example: s1.rollNo


Example:

class Student {

    int rollNo;              // Instance
    static String college;   // Static
}

Think:

Student Class
                  |
           static college
                  |
       ┌──────────┴──────────┐
       ↓                     ↓
      s1                    s2
   rollNo=101             rollNo=102


---

8. Static Method Can Access Static Variable

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

Both are class-level.


---

9. Static Method Cannot Directly Access Instance Variable

class Demo {

    int x = 100;

    static void display() {

        System.out.println(x); // ERROR
    }
}

Why?

x          → object-level
display()  → class-level

Java doesn't know which object's x you want.


---

10. Correct Way

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

11. Static Method Cannot Use this

this means:

> Current object.



A static method has no implicit current object.

Therefore:

static void display() {

    System.out.println(this.x); // ERROR
}

Remember:

static method → no this


---

12. Static Method Can Be Called Without Object

class Calculator {

    static int add(int a, int b) {

        return a + b;
    }

    public static void main(String[] args) {

        System.out.println(Calculator.add(10, 20));
    }
}

Output:

30

Preferred form:

ClassName.method();


---

13. Why Is main() Static?

Java programs commonly begin with:

public static void main(String[] args)

main() is static so the JVM can invoke the entry point without first creating an object of the class.

Conceptually:

JVM
 ↓
Class
 ↓
static main()


---

14. Static Block vs Constructor

class Demo {

    static {
        System.out.println("Static Block");
    }

    Demo() {
        System.out.println("Constructor");
    }

    public static void main(String[] args) {

        Demo d1 = new Demo();
        Demo d2 = new Demo();
    }
}

Output:

Static Block
Constructor
Constructor

Remember:

Static Block → class initialization
Constructor  → object creation

So generally:

Static block → once per class initialization
Constructor  → once per object


---

15. Multiple Static Blocks

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

🔴 LEVEL 3 — ADVANCED

16. Static Does NOT Mean Constant

This:

static int x = 10;

doesn't mean x is constant.

You can do:

x = 50;

If you want a class-level constant:

static final int MAX = 100;

Remember:

static → class-level
final  → cannot be reassigned


---

17. Static Methods Can Be Overloaded

Yes. ✅

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

So:

> static does not prevent method overloading.




---

18. Static Methods Are Hidden, Not Overridden

Consider:

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

Now:

Parent p = new Child();

p.display();

Output:

Parent

Static methods participate in method hiding, not runtime method overriding.

Compare:

Instance method → overriding
Static method   → hiding


---

19. Static Constructor?

❌ Not allowed.

static Demo() { } // ERROR

Constructors initialize objects, while static members belong to the class.


---

20. Static Local Variable?

❌ Java doesn't support static local variables.

This is invalid:

void display() {

    static int x = 10; // ERROR
}

Java's static is not used to create C/C++-style static local variables.


---

21. Static Nested Class

A nested class can be static:

class Outer {

    static class Inner {

        void display() {
            System.out.println("Inner");
        }
    }

    public static void main(String[] args) {

        Outer.Inner obj = new Outer.Inner();

        obj.display();
    }
}

Output:

Inner

But a top-level class cannot be declared static.


---

22. Complete Program — All Major Concepts

class Student {

    // Instance variables
    int rollNo;
    String name;

    // Static variable
    static String college;

    // Static block
    static {
        college = "ABC College";
        System.out.println("Static Block");
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

        System.out.println("Main Method");

        // Static method
        Student.displayCollege();

        // Objects
        Student s1 = new Student(101, "Ravi");
        Student s2 = new Student(102, "Kiran");

        // Instance methods
        s1.displayStudent();
        s2.displayStudent();
    }
}

Output:

Static Block
Main Method
College : ABC College
Roll No : 101
Name    : Ravi
College : ABC College
Roll No : 102
Name    : Kiran
College : ABC College


---

🧠 3-LEVEL FINAL REVISION

🟢 Level 1 — Remember

static variable → shared class-level data

static method → class-level method

static block → class-level initialization


---

🟡 Level 2 — Understand

Static
  ↓
Class

Instance
  ↓
Object

Therefore:

static method
→ no object required

instance method
→ object context

And:

static method
→ directly access static members
→ cannot directly access instance members
→ cannot use this


---

🔴 Level 3 — Interview/Exam

static ≠ final

static method can be overloaded

static method is hidden, not overridden

static constructor → impossible

static local variable → not supported in Java

static nested class → allowed

main() → static because JVM can invoke it without
          creating an application-class object

⭐ One Sentence to Remember

> static makes a member belong to the class rather than to individual objects.



That single idea explains static variables, static methods, static blocks, and most static-related questions in Java.
