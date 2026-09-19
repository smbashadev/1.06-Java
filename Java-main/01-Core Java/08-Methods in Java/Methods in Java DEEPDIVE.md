Methods in Java — DEEP DIVE 🔥

Let's build the topic from the foundation to the exam/interview level.


---

1. What is a Method?

A method is a named block of code designed to perform a particular task.

Example:

static void display() {
    System.out.println("Hello Java");
}

Calling the method:

display();

produces:

Hello Java

Why use methods?

Without methods:

System.out.println("Hello");
System.out.println("Welcome");
System.out.println("Hello");
System.out.println("Welcome");

With methods:

static void message() {
    System.out.println("Hello");
    System.out.println("Welcome");
}

Then:

message();
message();

So methods provide:

Reusability

Modularity

Readability

Maintainability

Abstraction



---

2. Basic Structure of a Method

General syntax:

accessModifier static returnType methodName(parameters) {
    // method body
}

For example:

public static int add(int a, int b) {
    return a + b;
}

Break it down:

public       → access modifier
static       → method belongs to the class
int          → return type
add          → method name
(int a,b)    → parameters
{ ... }      → method body
return       → sends result back

Not every method needs all these parts.

For example:

void display() {
}


---

3. Method Declaration vs Method Call

This distinction is important.

Method definition/declaration

static void display() {
    System.out.println("Hello");
}

You are creating the method.

Method invocation/call

display();

You are executing/invoking the method.

Think:

Definition → "What should the method do?"
Call       → "Execute that method."


---

4. Two Major Types of Methods

As you requested, methods can broadly be divided into:

METHODS
                        │
             ┌──────────┴──────────┐
             ▼                     ▼
        PREDEFINED             USER-DEFINED
         METHODS                 METHODS


---

5. Predefined Methods

These are methods already provided by Java's classes/libraries.

Examples:

Math.sqrt(25);
Math.pow(2, 3);

String methods:

String s = "Java";

s.length();
s.toUpperCase();
s.charAt(0);

Output examples:

sqrt(25)      → 5.0
length()      → 4
charAt(0)     → 'J'

You don't write the implementation of these methods yourself.


---

6. User-Defined Methods

These are methods created by the programmer.

Example:

class Demo {

    static void display() {
        System.out.println("Hello Java");
    }

    public static void main(String[] args) {
        display();
    }
}

Here:

display()

is a user-defined method.


---

7. Four Types Based on Return Type and Arguments

This is a common introductory classification.

METHODS
                            │
       ┌────────────────────┼────────────────────┐
       │                    │                    │
       ▼                    ▼                    ▼
 No Return + No Args   No Return + Args   Return + No Args
                                               │
                                               ▼
                                      Return + Arguments

Let's understand each carefully.


---

8. Type 1 — No Return Type, No Arguments

Syntax

void methodName() {
    // statements
}

Example

class Demo {

    static void display() {
        System.out.println("Hello Java");
    }

    public static void main(String[] args) {
        display();
    }
}

Here:

void → returns nothing
()   → accepts no arguments


---

9. What Does void Mean?

void means:

> This method does not return a value to its caller.



Example:

static void display() {
    System.out.println("Hello");
}

You cannot write:

int x = display();

because display() doesn't produce an int.


---

10. Type 2 — No Return Type, With Arguments

Syntax

void methodName(dataType parameter) {
    // statements
}

Example:

class Demo {

    static void square(int n) {
        System.out.println(n * n);
    }

    public static void main(String[] args) {
        square(5);
    }
}

Output:

25

Here:

void → no return
int n → parameter
5 → argument


---

11. Parameter vs Argument

These two words are often confused.

Consider:

static void square(int n) {
    System.out.println(n * n);
}

n is a parameter.

When we call:

square(5);

5 is an argument.

So:

Method definition:
int n       → parameter

Method call:
5           → argument


---

12. Multiple Parameters

static void add(int a, int b) {
    System.out.println(a + b);
}

Call:

add(10, 20);

Here:

a ← 10
b ← 20

Output:

30


---

13. Type 3 — Return Type, No Arguments

Syntax

returnType methodName() {
    return value;
}

Example:

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

int → return type
()  → no parameters
return 100 → sends value back


---

14. Why Do We Need return?

Suppose:

static int add() {
    return 10 + 20;
}

Calling:

int result = add();

The method produces:

30

and sends it back to the caller.

Think:

add()
               │
               │ return 30
               ▼
        int result = 30


---

15. Type 4 — Return Type With Arguments

This is probably the most useful form.

Syntax

returnType methodName(dataType parameter) {
    return value;
}

Example:

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

Flow:

add(10, 20)
    ↓
a = 10
b = 20
    ↓
a + b
    ↓
30
    ↓
return 30
    ↓
result = 30


---

16. Complete Four-Type Table

Type	Return	Arguments	Example

1	No	No	void display()
2	No	Yes	void display(int x)
3	Yes	No	int getNumber()
4	Yes	Yes	int add(int a, int b)


Memory trick

ARGUMENTS?
                /          \
              NO            YES
              │              │
          RETURN?          RETURN?
          /     \          /     \
        NO       YES      NO       YES
        │         │        │         │
     Type 1    Type 3   Type 2    Type 4


---

17. Does Every Method Need return?

No.

If return type is:

void

a value is not required.

static void display() {
    System.out.println("Hello");
}

But if return type is:

int

the method must return a compatible value on every normal execution path.

static int getNumber() {
    return 10;
}


---

18. Can a void Method Have return?

Yes, but without a value.

static void check(int age) {

    if (age < 18) {
        return;
    }

    System.out.println("Adult");
}

This simply exits the method.

You cannot:

return 10;

from a void method.


---

19. Can a Method Return Another Method's Result?

Yes.

static int square(int x) {
    return x * x;
}

static int calculate(int x) {
    return square(x) + 10;
}

Calling:

calculate(5);

gives:

square(5) = 25
25 + 10 = 35


---

20. Method Calling Another Method

Methods can call other methods.

class Demo {

    static void first() {
        second();
    }

    static void second() {
        System.out.println("Second method");
    }

    public static void main(String[] args) {
        first();
    }
}

Flow:

main()
 ↓
first()
 ↓
second()
 ↓
print


---

21. Static Methods

A static method belongs to the class rather than to an individual object.

Example:

class Demo {

    static void display() {
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        display();
    }
}

Because both methods are static, main() can directly call display().


---

22. Instance Methods

A non-static method belongs to an object.

class Demo {

    void display() {
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        Demo obj = new Demo();

        obj.display();
    }
}

Here:

Demo obj = new Demo();

creates an object.

Then:

obj.display();

calls the instance method.


---

23. Static vs Instance Method

Static	Instance

Belongs to class	Belongs to object
Can be called using class name	Usually called using object
Math.sqrt()	obj.display()
No object required for invocation	Object generally required



---

24. Method Overloading

Now the important advanced topic.

Definition

> Method overloading is defining multiple methods with the same name in the same class, where their parameter lists are different.



Example:

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


---

25. Why Do We Need Overloading?

Without overloading:

addTwo(10, 20);
addThree(10, 20, 30);

With overloading:

add(10, 20);
add(10, 20, 30);

The same conceptual operation gets the same method name.

This improves readability.


---

26. How Can Parameters Differ?

There are three common ways.

A. Different Number of Parameters

void show(int a) {}

void show(int a, int b) {}

Valid overloading.


---

B. Different Parameter Types

void show(int a) {}

void show(double a) {}

Valid overloading.


---

C. Different Order of Parameter Types

void show(int a, double b) {}

void show(double a, int b) {}

Valid overloading.


---

27. What Does NOT Create Overloading?

Changing only the return type does not work.

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

Java cannot select based only on return type.


---

28. Why Can't Java Use Return Type?

Consider:

int x = add(10, 20);

and:

double x = add(10, 20);

If Java allowed overloading only by return type, the method call:

add(10, 20)

would not itself identify which method to use.

Therefore Java does not use return type alone to distinguish overloaded methods.


---

29. Overloading Resolution — The 3 Steps

Suppose we have:

static void show(int x) {
    System.out.println("int");
}

static void show(double x) {
    System.out.println("double");
}

And:

show(10);

How does Java decide?

Step 1 — Number of arguments

There is:

1 argument

So Java considers methods accepting one argument.

Step 2 — Argument types

10 is an int literal.

Java compares it with:

show(int)
show(double)

Step 3 — Choose the best applicable match

show(int) is an exact match.

Therefore:

int

is printed.


---

30. The 3-Step Memory Formula

1️⃣ Number of arguments
          ↓
2️⃣ Type of arguments
          ↓
3️⃣ Best applicable match

At a deeper level, Java's overload resolution considers applicable methods through its language rules, including conversions and specificity.


---

31. Example of Widening During Overloading

class Demo {

    static void show(long x) {
        System.out.println("long");
    }

    static void show(double x) {
        System.out.println("double");
    }

    public static void main(String[] args) {
        show(10);
    }
}

10 is an int.

There is no show(int), so Java considers applicable widening conversions.

int → long
int → double

long is the more specific applicable choice here, so:

long

is printed.


---

32. Overloading with byte

Consider:

static void show(int x) {
    System.out.println("int");
}

static void show(long x) {
    System.out.println("long");
}

Then:

byte b = 10;
show(b);

byte can widen to int, so:

show(int)

is selected.


---

33. Ambiguous Overloading

This can happen:

static void show(int x, double y) {
}

static void show(double x, int y) {
}

Now:

show(10, 10);

Both methods may require a widening conversion for one argument.

Neither is clearly the better match.

Result:

❌ reference to show is ambiguous

This is an important overload-resolution trap.


---

34. Can main() Be Overloaded?

✅ Yes.

main is just a method name and can be overloaded.

Example:

class Demo {

    public static void main(String[] args) {
        System.out.println("Standard main");
        main(10);
    }

    static void main(int x) {
        System.out.println("Overloaded main");
    }
}

Output:

Standard main
Overloaded main


---

35. But Which main() Does the JVM Start With?

The JVM recognizes the standard entry-point signature:

public static void main(String[] args)

An overloaded version such as:

static void main(int x)

is just an ordinary method.

The JVM does not automatically select it as the application entry point.

So:

main(String[] args)
       ↓
JVM entry point

while:

main(int)
main(double)
main(String)

are simply overloaded methods.


---

36. Is main() Overloading Useful?

Technically yes, but normally it is not necessary.

For example:

public static void main(String[] args) {
    main(100);
}

static void main(int x) {
    System.out.println(x);
}

The standard main() acts as the entry point and delegates to another overloaded method.


---

37. Method Overloading vs Method Overriding

These are frequently confused.

Overloading

Same class generally:

same method name
different parameter list

Example:

add(int, int)
add(int, int, int)

Overriding

A subclass provides its own implementation of an inherited instance method, subject to Java's overriding rules.

Example conceptually:

Parent
  ↓
Child

Same method signature, different implementation.

Memory:

OVERLOADING  → same name, different parameters
OVERRIDING   → inheritance, redefine inherited method


---

38. Methods and Pass-by-Value

Java is pass-by-value.

For primitives:

static void change(int x) {
    x = 100;
}

public static void main(String[] args) {
    int a = 10;

    change(a);

    System.out.println(a);
}

Output:

10

Why?

A copy of a is passed.


---

39. What About Objects?

Java still passes by value.

But when an object reference is passed, the value of the reference is copied.

Example:

class Demo {

    int value = 10;

    static void change(Demo d) {
        d.value = 100;
    }

    public static void main(String[] args) {
        Demo obj = new Demo();

        change(obj);

        System.out.println(obj.value);
    }
}

Output:

100

The reference value was copied, but both references refer to the same object.


---

40. Method Parameters Can Be Arrays

static int sum(int[] arr) {

    int total = 0;

    for (int x : arr) {
        total += x;
    }

    return total;
}

Call:

int[] numbers = {10, 20, 30};

int result = sum(numbers);

Result:

60


---

41. Variable-Length Arguments — Varargs

Java allows:

static int sum(int... numbers) {

    int total = 0;

    for (int n : numbers) {
        total += n;
    }

    return total;
}

You can call:

sum(10, 20);
sum(10, 20, 30);
sum(1, 2, 3, 4, 5);

The syntax:

int... numbers

is called varargs.

Internally, the parameter is handled as an array.


---

42. Varargs and Overloading

Be careful:

void show(int... x) {}
void show(int[] x) {}

❌ Invalid as overloads.

Because varargs is represented as an array parameter for method signature purposes.

Conceptually:

int... → int[]


---

43. Can Methods Have the Same Name as the Class?

A method can technically have the same name as its class, but it is not a constructor if it has a return type.

Example:

class Demo {

    void Demo() {
        System.out.println("This is a method");
    }
}

This is a method because:

void Demo()

has a return type.

A constructor has:

Demo()

with no return type.


---

44. Method vs Constructor

Method	Constructor

Performs an operation	Initializes objects
Has a return type or void	Has no return type
Can have any name	Must match class name
Called explicitly/invoked	Called during object creation
Can be overloaded	Can be overloaded



---

45. Complete Example

Let's combine methods, arguments, return values, and overloading:

class Calculator {

    static void display() {
        System.out.println("Calculator");
    }

    static void display(int x) {
        System.out.println("Number: " + x);
    }

    static int add(int a, int b) {
        return a + b;
    }

    static int add(int a, int b, int c) {
        return a + b + c;
    }

    public static void main(String[] args) {

        display();

        display(10);

        int result1 = add(10, 20);
        int result2 = add(10, 20, 30);

        System.out.println(result1);
        System.out.println(result2);
    }
}

Output:

Calculator
Number: 10
30
60


---

🧠 COMPLETE CONCEPT MAP

METHODS
                                 │
                ┌────────────────┴────────────────┐
                ▼                                 ▼
           PREDEFINED                       USER-DEFINED
                │                                 │
          Java library                    Programmer creates
                                                  │
                                   ┌──────────────┴──────────────┐
                                   ▼                             ▼
                             Based on syntax               Overloading
                                   │                             │
                     ┌─────────────┼─────────────┐       Same name
                     │             │             │       +
                     ▼             ▼             ▼       Different
                 No Return      Return        Return     parameters
                 No Args        No Args       Arguments
                     │             │             │
                     └─────────────┴─────────────┘


---

🔥 FINAL DOUBT-KILLERS

1. Is a method executed when it is defined?

❌ No.

It executes when invoked, except for special execution contexts such as initialization mechanisms.


---

2. Does every method need arguments?

❌ No.

void display()

has no arguments.


---

3. Does every method need to return a value?

❌ No.

A void method doesn't return a value.


---

4. Can a method have multiple parameters?

✅ Yes.

int add(int a, int b)


---

5. Can methods have the same name?

✅ Yes, through method overloading, if their parameter lists differ.


---

6. Can overloading differ only by return type?

❌ No.


---

7. Can main() be overloaded?

✅ Yes.

But only:

public static void main(String[] args)

is the conventional JVM entry-point signature.


---

8. Can a method call another method?

✅ Yes.


---

9. Is Java pass-by-reference?

❌ No.

Java is always pass-by-value.


---

🏆 MASTER MEMORY

METHOD
  ↓
Named block of code
  ↓
Reusable task

Four basic forms:

1. void method()
2. void method(parameters)
3. returnType method()
4. returnType method(parameters)

Overloading:

SAME NAME
   +
DIFFERENT PARAMETER LIST
   =
METHOD OVERLOADING

Overload resolution:

1️⃣ Number of arguments
        ↓
2️⃣ Types of arguments
        ↓
3️⃣ Best applicable match

main():

main(String[] args) → JVM entry point
main(int)           → overloaded method

> The most important idea: A method is a reusable unit of behavior. Its parameters define what input it accepts, its return type defines what result it produces, and overloading allows the same method name to represent the same general operation for different parameter lists.
