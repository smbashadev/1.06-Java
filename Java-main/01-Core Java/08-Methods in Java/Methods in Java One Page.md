Methods in Java — ONE PAGE

1. What is a Method?

A method is a block of code that performs a particular task.

General Syntax

returnType methodName(parameters) {
    // statements
}

Example:

void display() {
    System.out.println("Hello Java");
}

A method is executed when it is called.


---

2. Types of Methods in Java

There are two broad types:

METHODS
                    │
          ┌─────────┴─────────┐
          ▼                   ▼
    PREDEFINED            USER-DEFINED
     METHODS                METHODS

Predefined Methods

Methods already provided by Java libraries/classes.

Examples:

System.out.println();
Math.sqrt(25);
String.length();

User-Defined Methods

Methods created by the programmer.

void display() {
    System.out.println("Hello");
}


---

3. Four Types Based on Syntax

Depending on return type and arguments, a method can be classified into four types:

METHODS
                            │
          ┌─────────────────┼─────────────────┐
          │                 │                 │
          ▼                 ▼                 ▼
No Return + No Args    No Return + Args    Return + No Args
                                              │
                                              ▼
                                      Return + Arguments

More simply:

Type	Return Type	Arguments

1	No	No
2	No	Yes
3	Yes	No
4	Yes	Yes



---

4. Type 1 — No Return Type, No Arguments

Syntax

void methodName() {
    // statements
}

Program

class Demo {
    static void display() {
        System.out.println("Hello Java");
    }

    public static void main(String[] args) {
        display();
    }
}

Output:

Hello Java

Here:

void     → no return value
()       → no arguments
display  → method name


---

5. Type 2 — No Return Type, Arguments

Syntax

void methodName(dataType parameter) {
    // statements
}

Program

class Demo {
    static void display(int n) {
        System.out.println("Number = " + n);
    }

    public static void main(String[] args) {
        display(10);
    }
}

Output:

Number = 10

Here:

void     → no return value
int n    → argument/parameter


---

6. Type 3 — Return Type, No Arguments

Syntax

returnType methodName() {
    return value;
}

Program

class Demo {
    static int getNumber() {
        return 100;
    }

    public static void main(String[] args) {
        int x = getNumber();

        System.out.println(x);
    }
}

Output:

100

Here:

int      → return type
()       → no arguments
return   → sends value back to caller


---

7. Type 4 — Return Type, Arguments

Syntax

returnType methodName(dataType parameter) {
    return value;
}

Program

class Demo {
    static int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        int result = add(10, 20);

        System.out.println(result);
    }
}

Output:

30


---

8. Method Overloading

Definition

> Method overloading is the process of defining multiple methods with the same name but different parameter lists in the same class.



The parameter list can differ in:

Number of parameters

Type of parameters

Order of parameters


Example

class Demo {

    static void add(int a, int b) {
        System.out.println(a + b);
    }

    static void add(int a, int b, int c) {
        System.out.println(a + b + c);
    }

    public static void main(String[] args) {
        add(10, 20);
        add(10, 20, 30);
    }
}

Output:

30
60

Same method name:

add()

Different parameter lists:

add(int, int)
add(int, int, int)


---

9. What Is NOT Method Overloading?

Changing only the return type is not overloading.

❌ Invalid:

int add(int a, int b) {
    return a + b;
}

double add(int a, int b) {
    return a + b;
}

Why?

Both have the same parameter list:

add(int, int)

Java cannot distinguish them based only on return type.


---

10. Program Without Method Overloading

Suppose we want to add two numbers and three numbers.

Without overloading, we need different method names:

class Demo {

    static void addTwo(int a, int b) {
        System.out.println(a + b);
    }

    static void addThree(int a, int b, int c) {
        System.out.println(a + b + c);
    }

    public static void main(String[] args) {
        addTwo(10, 20);
        addThree(10, 20, 30);
    }
}

Methods have different names:

addTwo()
addThree()


---

11. Program With Method Overloading

With overloading, we can use the same meaningful method name:

class Demo {

    static void add(int a, int b) {
        System.out.println(a + b);
    }

    static void add(int a, int b, int c) {
        System.out.println(a + b + c);
    }

    public static void main(String[] args) {
        add(10, 20);
        add(10, 20, 30);
    }
}

Now:

add(int, int)
add(int, int, int)

are overloaded methods.


---

12. How Does Java Decide Which Overloaded Method to Call?

Suppose:

add(10, 20);

and we have:

add(int, int)
add(int, int, int)

Java needs to determine which method matches the call.

3 Steps to Resolve Method Overloading

Step 1 — Number of Arguments

Check how many arguments are supplied.

add(10, 20);

There are:

2 arguments

So Java looks for methods that can accept 2 arguments.


---

Step 2 — Data Types of Arguments

Then Java checks the argument types.

add(10, 20);

10 and 20 are int values.

So:

add(int, int)

is an exact match.


---

Step 3 — Choose the Best Matching Method

Java selects the most specific/best applicable method according to its overload-resolution rules.

For example:

void show(int x)
void show(double x)

Calling:

show(10);

selects:

show(int)

because int is an exact match.

Memory:

1️⃣ Number of arguments
        ↓
2️⃣ Types of arguments
        ↓
3️⃣ Best matching method


---

13. Is main() Method Overloaded?

✅ Yes!

You can overload main() like any other method.

Example:

class Demo {

    public static void main(String[] args) {
        System.out.println("Original main()");
        main(10);
    }

    static void main(int x) {
        System.out.println("Overloaded main()");
    }
}

Output:

Original main()
Overloaded main()

However, the JVM specifically starts the program using the standard entry-point signature:

public static void main(String[] args)

The other overloaded main() methods are ordinary overloaded methods; they are not automatically used as the program entry point.


---

🧠 FINAL ONE-PAGE MAP

METHODS
                            │
              ┌─────────────┴─────────────┐
              ▼                           ▼
         PREDEFINED                 USER-DEFINED
       Java-provided               Programmer-created
              │
              │
       Based on Syntax
              │
    ┌─────────┼──────────┬──────────┐
    ▼         ▼          ▼          ▼
 No Return  No Return  Return     Return
 No Args    Arguments  No Args    Arguments
    │         │          │          │
    ▼         ▼          ▼          ▼
  void f()  void f(int) int f()   int f(int)

Method Overloading

Same method name
       +
Different parameter list
       =
Method Overloading

Overload Resolution

Number of arguments
        ↓
Data types of arguments
        ↓
Best applicable match

main() Overloading

main(String[] args)   ← JVM entry point
main(int x)           ← overloaded method
main(int, int)        ← overloaded method

> Golden Rule: A method is overloaded when methods have the same name but different parameter lists. Return type alone cannot create method overloading.
