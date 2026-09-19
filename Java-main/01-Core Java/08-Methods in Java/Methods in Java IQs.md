Methods in Java — DOUBTKILLER 🔥

This is the confusion-destroying version.
I’ll focus on the questions that usually make students stop and think: method vs method call, parameters vs arguments, void vs return, the 4 types, overloading, and main() overloading.


---

1. What exactly is a Method?

A method is a named block of code that performs a particular task.

static void add() {
    System.out.println(10 + 20);
}

Here:

add() → method

But be careful:

This:

static void add() {
    System.out.println(10 + 20);
}

is method definition/declaration.

This:

add();

is a method call.

Killer point 🔥

> Writing a method does not execute it. Calling the method executes it.




---

2. Why do we need Methods?

Without methods:

int a = 10;
int b = 20;
System.out.println(a + b);

int x = 30;
int y = 40;
System.out.println(x + y);

int p = 50;
int q = 60;
System.out.println(p + q);

The same logic is repeatedly written.

With a method:

static void add(int a, int b) {
    System.out.println(a + b);
}

We can reuse it:

add(10, 20);
add(30, 40);
add(50, 60);

Therefore:

> The main purpose of a method is reusable and organized code.




---

3. Predefined vs User-defined — Don't confuse them

Predefined

Already provided by Java.

Math.max(10, 20);

max() is predefined.

"Java".length();

length() is predefined.


---

User-defined

Created by the programmer.

static void greet() {
    System.out.println("Hello");
}

greet() is user-defined.

Killer point 🔥

> main() is a method, but it is written by the programmer.




---

4. What does void actually mean?

Consider:

static void display() {
    System.out.println("Hello");
}

void means:

> This method does not return a value to its caller.



It does not mean:

> "The method does nothing."



The method can absolutely perform work:

static void display() {
    System.out.println("Hello");
}

It simply doesn't return a value.


---

5. Does every method need return?

No.

If return type is void:

static void display() {
    System.out.println("Hello");
}

No return value is required.

But if:

static int add() {
    return 10 + 20;
}

then an int value must be returned.

Killer rule 🔥

void
 ↓
No value returned

int
 ↓
int value must be returned

double
 ↓
double-compatible value must be returned

String
 ↓
String value must be returned


---

6. return vs System.out.println() — HUGE CONFUSION

These are not the same.

Example 1

static void add() {
    System.out.println(10 + 20);
}

This prints:

30

It doesn't return 30.


---

Example 2

static int add() {
    return 10 + 20;
}

This returns:

30

It doesn't automatically print it.

You need:

System.out.println(add());

Killer difference 🔥

System.out.println()
        ↓
Displays value

return
        ↓
Sends value back to caller


---

7. Parameters vs Arguments — NEVER MIX THEM

Look at:

static int add(int a, int b) {
    return a + b;
}

a and b are:

> Parameters



Now:

add(10, 20);

10 and 20 are:

> Arguments



Easy trick:

Parameters → Placeholders
Arguments  → Actual values

Think:

add(int a, int b)
    ↑       ↑
 placeholders


add(10, 20)
    ↑    ↑
 actual values


---

8. Why do we need arguments?

Consider:

static void add() {
    int a = 10;
    int b = 20;
    System.out.println(a + b);
}

This always works with 10 and 20.

But:

add(100, 200);

is impossible because the method doesn't accept arguments.

Instead:

static void add(int a, int b) {
    System.out.println(a + b);
}

Now:

add(10, 20);
add(100, 200);
add(500, 600);

The method becomes reusable with different data.


---

9. The Four Types — THE BIG DOUBT

Students often memorize them without understanding.

Don't memorize. Ask two questions:

Question 1:

Does the method receive input?

Question 2:

Does the method send output back?


---

Type 1

void add()

Input?

❌ No arguments.

Output?

❌ No return.

Therefore:

> No Return + No Arguments




---

Type 2

void add(int a, int b)

Input?

✅ Arguments.

Output?

❌ No return.

Therefore:

> No Return + Arguments




---

Type 3

int add()

Input?

❌ No arguments.

Output?

✅ Returns int.

Therefore:

> Return + No Arguments




---

Type 4

int add(int a, int b)

Input?

✅ Arguments.

Output?

✅ Returns int.

Therefore:

> Return + Arguments




---

10. The Ultimate Four-Type Trick 🧠

Remember:

METHOD
                  |
           ┌──────┴──────┐
           │             │
        INPUT?        OUTPUT?
           │             │
       Arguments       Return

So:

void add()
    ↓
No Input + No Output


void add(int a)
    ↓
Input + No Output


int add()
    ↓
No Input + Output


int add(int a)
    ↓
Input + Output

That's the whole classification.


---

11. Is void an argument?

❌ No.

Example:

void display()

The empty parentheses mean:

> No parameters.



void describes the return type, not arguments.


---

12. Can a method have multiple parameters?

Yes.

static void add(int a, int b, int c) {
    System.out.println(a + b + c);
}

Call:

add(10, 20, 30);

Here:

Parameters → a, b, c
Arguments  → 10, 20, 30


---

13. Can a method return multiple values?

Not as multiple separate return values in one ordinary Java return statement.

For example, this is invalid:

return a, b;

A method has one return value from the method invocation.

However, that one value can be an object, array, collection, etc., containing multiple pieces of data.

Example:

static int[] getValues() {
    return new int[]{10, 20};
}

The method returns one array object, which contains two integers.

Killer point 🔥

> A method returns one value/reference from a return statement, but that value can represent multiple pieces of data.




---

14. Can a void method use return?

Yes — but only without returning a value.

static void display() {

    System.out.println("Hello");

    return;
}

This is valid.

But:

static void display() {
    return 10; // ❌
}

is invalid.

Remember:

void method
    ↓
return;       ✅
return value; ❌


---

15. Can a method return void?

Careful with the wording.

We say:

> A method has return type void.



We generally don't say that it "returns void" as a value.

void means no value is returned.


---

16. METHOD OVERLOADING — THE BIGGEST DOUBT 🔥

Suppose:

static void add(int a, int b) {
}

static void add(int a, int b, int c) {
}

Same method name:

add

Different parameter lists:

(int, int)
(int, int, int)

Therefore:

> Method overloading.




---

17. Is this overloading?

static void add(int a, int b) {
}

static void add(double a, double b) {
}

✅ Yes.

Parameter types differ.


---

18. Is this overloading?

static void add(int a, double b) {
}

static void add(double a, int b) {
}

✅ Yes.

The order of parameter types differs.


---

19. Is this overloading?

static int add(int a, int b) {
    return a + b;
}

static double add(int a, int b) {
    return a + b;
}

❌ No.

Why?

Because the parameter lists are exactly the same:

(int, int)
(int, int)

Only return type changed.

Killer rule 🔥

> Return type alone cannot create method overloading.




---

20. Why can't Java overload only by return type?

Suppose Java allowed:

int add(int a, int b)
double add(int a, int b)

Now imagine:

add(10, 20);

Which one should Java call?

There is nothing in the call that distinguishes them.

Therefore Java does not allow return type alone to determine overloaded methods.


---

21. Does changing parameter names create overloading?

❌ No.

These are identical signatures for overloading purposes:

add(int a, int b)
add(int x, int y)

Parameter names changed, but parameter types are the same.

So:

(int, int)
(int, int)

No overloading.


---

22. Does changing static create overloading?

No.

You cannot create two methods with the same signature merely by changing static.

The parameter list must differ for method overloading.


---

23. How does Java choose an overloaded method?

Example:

static void show(int x) {
    System.out.println("int");
}

static void show(double x) {
    System.out.println("double");
}

Call:

show(10);

Java sees:

10 → int

So the exact matching method is:

show(int x)


---

24. The 3-step overload confusion killer

For your notes:

METHOD CALL
    ↓
STEP 1
Check number of arguments
    ↓
STEP 2
Check argument types
    ↓
STEP 3
Select the best applicable match

In a simple exact-match example:

show(10);

Java finds:

show(int)

before needing a widening conversion to:

show(double)


---

25. Can overloaded methods have different return types?

Yes — if their parameter lists are also different.

Example:

static int show(int x) {
    return x;
}

static double show(double x) {
    return x;
}

This is valid overloading.

The important point is:

> The methods are overloaded because their parameter lists differ, not because their return types differ.




---

26. Can main() be overloaded?

YES. 🔥

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

So main() can be overloaded like any other method.


---

27. Then why doesn't JVM call main(int)?

Because the JVM looks for the standard entry-point signature:

public static void main(String[] args)

So:

main(String[])
      ↓
JVM recognizes as entry point

while:

main(int)
      ↓
ordinary overloaded method

Killer sentence 🔥

> main() can be overloaded, but JVM starts execution using the standard main(String[] args) entry point.




---

28. Is main() a special method?

It is a method with a special role as the conventional JVM entry point.

Its standard declaration is:

public static void main(String[] args)

Break it down:

public
  ↓
accessible to JVM

static
  ↓
can be invoked without creating an object

void
  ↓
returns no value

main
  ↓
method name

String[] args
  ↓
command-line arguments


---

29. Can we change the name main?

You can create another method with a different name, but it won't serve as the standard Java entry point.

For example:

public static void start(String[] args)

is just start().

The JVM does not treat it as the standard entry point.


---

30. Can we overload a method by changing only parameter names?

❌ No.

show(int x)
show(int y)

These have the same parameter type sequence:

(int)

Therefore, they cannot coexist as overloaded methods.


---

31. Can we overload a method by changing only access modifier?

❌ No.

For example, changing:

public

to:

private

does not create a different overloaded method if the signature remains the same.


---

32. Can one method call another method?

Absolutely.

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
"Second method"

This is one of the fundamental ideas behind breaking a program into smaller tasks.


---

33. Can a method call itself?

Yes.

That's called recursion.

Example:

static void count(int n) {

    if (n == 0)
        return;

    System.out.println(n);

    count(n - 1);
}

Call:

count(3);

Flow:

count(3)
 ↓
count(2)
 ↓
count(1)
 ↓
count(0)
 ↓
return

Recursion is a separate concept, but methods make recursion possible.


---

34. Can methods be written inside methods?

❌ No, not as ordinary Java method declarations.

This is invalid:

static void first() {

    static void second() {   // ❌
    }
}

Methods are members of classes/interfaces (or related constructs such as records), not ordinary nested method declarations.


---

35. Can two methods have the same name?

Yes, if they are overloaded.

add(int, int)
add(double, double)

No, if their signatures are the same within the same class.

add(int, int)
add(int, int)

That would be a duplicate method declaration.


---

36. Method signature — Important 🔥

For ordinary Java overloading, the method signature is based on:

Method name + parameter types

Example:

add(int, double)

The relevant signature is conceptually:

add(int, double)

Changing only:

return type ❌

parameter names ❌

access modifier ❌


doesn't create a different overload.


---

37. One final confusion: "Method has input and output"

This is a useful learning model, but don't misunderstand it.

Input

Parameters:

int add(int a, int b)

Processing

a + b

Output

return a + b;

So mentally:

INPUT
         ↓
     PARAMETERS
         ↓
      METHOD
      PROCESSING
         ↓
       RETURN
         ↓
      OUTPUT

But a method doesn't necessarily have both.

It can have:

No input + no output
No input + output
Input + no output
Input + output

That's exactly why we have the four types.


---

🔥 FINAL DOUBTKILLER — 15 QUESTIONS

1. What is a method?

A reusable block of code that performs a specific task.

2. Does defining a method execute it?

❌ No.

3. What executes a method?

A method call.

4. What are parameters?

Variables declared in the method parameter list.

5. What are arguments?

Actual values passed during a method call.

6. What does void mean?

The method does not return a value.

7. Does void mean the method does nothing?

❌ No.

8. Can a void method contain return;?

✅ Yes.

9. Can a void method contain return 10;?

❌ No.

10. Can return type alone overload a method?

❌ No.

11. Can parameter count create overloading?

✅ Yes.

12. Can parameter type create overloading?

✅ Yes.

13. Can parameter order create overloading?

✅ Yes.

14. Can main() be overloaded?

✅ Yes.

15. Which main() is the standard JVM entry point?

public static void main(String[] args)


---

🧠 ONE-MINUTE REVISION

METHOD
  ↓
Reusable block of code
  ↓
 ┌─────────────────────────────┐
 │                             │
Predefined              User-defined
 │                             │
Java provides           Programmer creates
                                │
                     Return + Arguments
                                │
                ┌───────────────┴───────────────┐
                │                               │
             No Return                       Return
                │                               │
          void method()                  int method()
                │                               │
          Arguments?                       Arguments?
                │                               │
        ┌───────┴───────┐               ┌───────┴───────┐
        │               │               │               │
       NO              YES             NO              YES
        │               │               │               │
   void m()      void m(int)       int m()       int m(int)

Then:

SAME NAME
   +
DIFFERENT PARAMETER LIST
   ↓
METHOD OVERLOADING

And the most important rule:

> 🔥 Overloading is decided by the method name + parameter list. Return type alone cannot distinguish overloaded methods.
