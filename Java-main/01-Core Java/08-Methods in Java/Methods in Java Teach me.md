# Methods in Java — TEACHME 🧑‍🏫

Think of a **method as a small machine inside a class**.

You give the machine some input → it performs some work → sometimes it gives you an output.

For example:

> **Input:** 10 and 20
> **Method:** adds them
> **Output:** 30

---

# 1. First understand: Why do we need methods?

Imagine you write this program:

```java
class Demo {
    public static void main(String[] args) {

        int a = 10;
        int b = 20;
        int c = a + b;
        System.out.println(c);

        int x = 30;
        int y = 40;
        int z = x + y;
        System.out.println(z);

        int p = 50;
        int q = 60;
        int r = p + q;
        System.out.println(r);
    }
}
```

Here, the same **addition logic** is repeated.

Instead, we can create a method:

```java
class Demo {

    static void add(int a, int b) {
        int c = a + b;
        System.out.println(c);
    }

    public static void main(String[] args) {

        add(10, 20);
        add(30, 40);
        add(50, 60);
    }
}
```

Now the addition logic is written **once** and reused many times.

### So remember:

> **Method = reusable block of code designed to perform a particular task.**

---

# 2. Real-life example 🧠

Think about a **washing machine**.

You don't need to know every internal operation.

You simply press:

**START**

The machine performs many operations internally.

Similarly:

```java
washClothes();
```

The method contains the instructions.

You simply **call the method**.

---

# 3. Basic structure of a method

Look at:

```java
static void add() {
    System.out.println("Addition");
}
```

Break it down:

```text
static       → modifier
void         → return type
add          → method name
()           → parameter list
{ }          → method body
```

General syntax:

```java
modifier returnType methodName(parameters) {
    // statements
}
```

Example:

```java
static int add(int a, int b) {
    return a + b;
}
```

Here:

| Part           | Meaning                   |
| -------------- | ------------------------- |
| `static`       | method belongs to class   |
| `int`          | method returns an integer |
| `add`          | method name               |
| `int a, int b` | parameters                |
| `return a + b` | returned result           |

---

# 4. Two types of methods

In Java, methods are broadly classified into:

### 1. Predefined methods

### 2. User-defined methods

---

## 4.1 Predefined methods

These are methods already provided by Java libraries/classes.

Example:

```java
System.out.println("Hello");
```

`println()` is a predefined method.

Another example:

```java
Math.max(10, 20);
```

`max()` is a predefined method.

Another:

```java
String name = "Java";
System.out.println(name.length());
```

`length()` is a predefined method.

### Remember:

> **Predefined method = method already provided by Java.**

---

# 5. User-defined methods

Methods created by the programmer are called **user-defined methods**.

Example:

```java
class Demo {

    static void greet() {
        System.out.println("Hello Java");
    }

    public static void main(String[] args) {
        greet();
    }
}
```

We created:

```java
greet()
```

Therefore, `greet()` is a **user-defined method**.

---

# 6. The BIG classification of methods

This is extremely important for exams and interviews.

Depending upon:

* **Return type**
* **Arguments/parameters**

we have four types.

```text
                    METHODS
                       |
          ---------------------------
          |                         |
     No Return                  Return
          |                         |
     -----------               -----------
     |         |               |         |
   No Arg    Arg             No Arg     Arg
```

So:

### Type 1

**No Return Type + No Arguments**

### Type 2

**No Return Type + Arguments**

### Type 3

**Return Type + No Arguments**

### Type 4

**Return Type + Arguments**

Let's learn each one.

---

# 7. TYPE 1 — No Return Type + No Arguments

Example:

```java
class Demo {

    static void add() {
        int a = 10;
        int b = 20;
        int c = a + b;

        System.out.println(c);
    }

    public static void main(String[] args) {
        add();
    }
}
```

Look at:

```java
void add()
```

### `void`

Means:

> This method does not return a value.

### `()`

Means:

> This method does not receive arguments.

Therefore:

```text
void add()
     ↓    ↓
   no     no
 return  arguments
```

### Flow

```text
main()
   ↓
add()
   ↓
10 + 20
   ↓
30
```

---

# 8. TYPE 2 — No Return Type + Arguments

Now suppose we want to give values to the method.

```java
class Demo {

    static void add(int a, int b) {
        int c = a + b;
        System.out.println(c);
    }

    public static void main(String[] args) {

        add(10, 20);
    }
}
```

Here:

```java
add(int a, int b)
```

The method accepts two parameters.

When we call:

```java
add(10, 20);
```

the values are passed.

```text
10 → a
20 → b
```

Then:

```java
a + b
```

becomes:

```java
10 + 20
```

Output:

```text
30
```

But the method doesn't return anything.

Therefore:

> **No Return Type + Arguments**

---

# 9. TYPE 3 — Return Type + No Arguments

Now we want the method to **give a value back**.

```java
class Demo {

    static int add() {

        int a = 10;
        int b = 20;

        return a + b;
    }

    public static void main(String[] args) {

        int result = add();

        System.out.println(result);
    }
}
```

Here:

```java
int add()
```

means:

> This method returns an `int`.

The method executes:

```java
return a + b;
```

which gives:

```text
30
```

back to the caller.

So:

```java
int result = add();
```

means:

```text
add()
 ↓
30
 ↓
result
```

Output:

```text
30
```

---

# 10. TYPE 4 — Return Type + Arguments

This is probably the most useful form.

```java
class Demo {

    static int add(int a, int b) {

        return a + b;
    }

    public static void main(String[] args) {

        int result = add(10, 20);

        System.out.println(result);
    }
}
```

Here:

```java
int add(int a, int b)
```

means:

```text
int        → returns int
add        → method name
int a,b    → accepts arguments
```

Call:

```java
add(10, 20)
```

Execution:

```text
10 → a
20 → b

a + b
 ↓
30

return 30
 ↓
result
```

Output:

```text
30
```

---

# 11. Remember the four types with one example

| Type | Syntax                          | Example                  |
| ---- | ------------------------------- | ------------------------ |
| 1    | `void method()`                 | `void add()`             |
| 2    | `void method(parameters)`       | `void add(int a, int b)` |
| 3    | `returnType method()`           | `int add()`              |
| 4    | `returnType method(parameters)` | `int add(int a, int b)`  |

### Easy memory trick:

```text
             ARGUMENTS?
              /      \
            NO        YES
            |          |
         NO RETURN   NO RETURN
            |          |
         void m()   void m(int x)


             RETURN?
              /    \
            NO      YES
            |        |
       void method   int method()
```

Even easier:

> **Arguments = Input**
> **Return value = Output**

So:

```text
                METHOD
                  |
           ----------------
           |              |
        INPUT           OUTPUT
      Arguments       Return value
```

---

# 12. Parameters vs Arguments

This is another common confusion.

Look at:

```java
static void add(int a, int b) {
    System.out.println(a + b);
}
```

Here:

```java
int a, int b
```

are called **parameters**.

When we call:

```java
add(10, 20);
```

`10` and `20` are called **arguments**.

### Remember:

> **Parameters are written in method declaration.**

> **Arguments are supplied during method call.**

```java
void add(int a, int b)
         ↑       ↑
      parameters


add(10, 20)
    ↑    ↑
  arguments
```

---

# 13. How is a method executed?

This is very important.

Creating a method does **not** execute it.

Example:

```java
class Demo {

    static void hello() {
        System.out.println("Hello");
    }

    public static void main(String[] args) {

    }
}
```

Nothing is printed.

Why?

Because we only **defined** the method.

We didn't call it.

We need:

```java
hello();
```

So:

```java
static void hello() {
    System.out.println("Hello");
}
```

means:

> "Here is the method."

And:

```java
hello();
```

means:

> "Execute this method."

---

# 14. Method Definition vs Method Call

### Method definition

```java
static void hello() {
    System.out.println("Hello");
}
```

### Method call

```java
hello();
```

Think:

```text
Definition = Create the machine

Call = Start the machine
```

---

# 15. One method can be called multiple times

```java
class Demo {

    static void hello() {
        System.out.println("Hello");
    }

    public static void main(String[] args) {

        hello();
        hello();
        hello();
    }
}
```

Output:

```text
Hello
Hello
Hello
```

This demonstrates **reusability**.

That's one of the major advantages of methods.

---

# 16. Now comes METHOD OVERLOADING 🔥

Suppose we want to perform addition.

For two integers:

```java
add(10, 20);
```

For three integers:

```java
add(10, 20, 30);
```

For two doubles:

```java
add(10.5, 20.5);
```

Can we create methods with the same name?

### Yes!

```java
class Demo {

    static void add(int a, int b) {
        System.out.println(a + b);
    }

    static void add(int a, int b, int c) {
        System.out.println(a + b + c);
    }

    static void add(double a, double b) {
        System.out.println(a + b);
    }

    public static void main(String[] args) {

        add(10, 20);
        add(10, 20, 30);
        add(10.5, 20.5);
    }
}
```

This is called:

# Method Overloading

### Definition:

> **Method overloading is the process of defining multiple methods with the same name but different parameter lists in the same class.**

---

# 17. How must overloaded methods be different?

They must differ in their **parameter list**.

They can differ by:

### 1. Number of parameters

```java
add(int a, int b)
add(int a, int b, int c)
```

Different number.

---

### 2. Type of parameters

```java
add(int a, int b)
add(double a, double b)
```

Different types.

---

### 3. Order of parameter types

```java
display(int a, double b)
display(double a, int b)
```

Different order.

---

# 18. What is NOT method overloading?

Changing only the return type is **not enough**.

This is invalid:

```java
static int add(int a, int b) {
    return a + b;
}

static double add(int a, int b) {
    return a + b;
}
```

Why?

Because both have the same:

```text
method name → add
parameter list → (int, int)
```

Only return type changed.

Java cannot overload methods based only on return type.

### Golden rule:

> **Method overloading depends on the parameter list, not the return type.**

---

# 19. How does Java decide which overloaded method to call?

Suppose:

```java
class Demo {

    static void show(int x) {
        System.out.println("int");
    }

    static void show(double x) {
        System.out.println("double");
    }

    public static void main(String[] args) {

        show(10);
    }
}
```

Which method should Java call?

There are multiple `show()` methods.

Java uses **method overloading resolution**.

The basic idea is:

### Step 1 — Check number of arguments

```java
show(10);
```

One argument.

So Java looks for methods accepting one argument.

---

### Step 2 — Check exact type match

`10` is an `int`.

So:

```java
show(int x)
```

is an exact match.

---

### Step 3 — Choose the best matching method

Therefore:

```java
show(int x)
```

is selected.

Output:

```text
int
```

---

# 20. The 3-step idea for overloaded method selection

For your notes, remember this sequence:

### Step 1

**Number of arguments**

### Step 2

**Type of arguments**

### Step 3

**Best matching method**

In simple cases:

```text
Method Call
    ↓
1. Number of arguments
    ↓
2. Type of arguments
    ↓
3. Best matching method
    ↓
Execute
```

---

# 21. Is `main()` method overloaded?

### Yes. Technically, `main()` can be overloaded.

Example:

```java
class Demo {

    public static void main(String[] args) {
        System.out.println("Original main");
        main(10);
    }

    static void main(int x) {
        System.out.println("Overloaded main");
    }
}
```

Output:

```text
Original main
Overloaded main
```

So:

```java
main(String[] args)
```

and:

```java
main(int x)
```

are overloaded methods.

### BUT...

The JVM specifically looks for:

```java
public static void main(String[] args)
```

as the entry point.

It does **not** start execution from:

```java
main(int x)
```

Therefore:

> **`main()` can be overloaded, but only `public static void main(String[] args)` is recognized as the standard JVM entry point.**

---

# 22. One complete program without method overloading

First, let's see normal methods.

```java
class Calculator {

    static int add(int a, int b) {
        return a + b;
    }

    static int subtract(int a, int b) {
        return a - b;
    }

    static int multiply(int a, int b) {
        return a * b;
    }

    public static void main(String[] args) {

        System.out.println(add(10, 20));
        System.out.println(subtract(20, 10));
        System.out.println(multiply(10, 20));
    }
}
```

Here every method has a different name:

```text
add()
subtract()
multiply()
```

There is **no method overloading**.

---

# 23. Same program using method overloading

Now:

```java
class Calculator {

    static int calculate(int a, int b) {
        return a + b;
    }

    static int calculate(int a, int b, int c) {
        return a + b + c;
    }

    static double calculate(double a, double b) {
        return a + b;
    }

    public static void main(String[] args) {

        System.out.println(calculate(10, 20));

        System.out.println(calculate(10, 20, 30));

        System.out.println(calculate(10.5, 20.5));
    }
}
```

Same method name:

```java
calculate()
```

but different parameter lists.

Therefore:

> **Method overloading.**

---

# 24. The most important mental model 🧠

Whenever you see a method, ask **two questions**:

### Question 1:

> Does the method take input?

Look at:

```java
()
```

or:

```java
(int a, int b)
```

### Question 2:

> Does the method give output?

Look at:

```java
void
```

or:

```java
int
double
String
```

Then you can immediately identify the method type.

---

# 25. Example — identify the type

```java
static void display()
```

Ask:

**Arguments?**

No.

**Return?**

No.

Therefore:

> Type 1 — No Return + No Arguments.

---

```java
static void display(int x)
```

Arguments?

Yes.

Return?

No.

Therefore:

> Type 2 — No Return + Arguments.

---

```java
static int display()
```

Arguments?

No.

Return?

Yes.

Therefore:

> Type 3 — Return + No Arguments.

---

```java
static int display(int x)
```

Arguments?

Yes.

Return?

Yes.

Therefore:

> Type 4 — Return + Arguments.

---

# 26. Final picture to remember forever 🚀

```text
                         JAVA METHODS
                              |
              -------------------------------
              |                             |
        PREDEFINED                     USER-DEFINED
        METHODS                         METHODS
              |
              |
       Depending on
       arguments & return
              |
       -------------------
       |        |        |
       |        |        |
      ...      ...      ...
```

For user-defined methods:

```text
                 METHOD
                   |
        ----------------------
        |                    |
   RETURN VALUE?          ARGUMENTS?
        |                    |
      void / type          () / values
```

The four combinations:

```text
1. void method()
   ↓
   No return + No arguments


2. void method(int x)
   ↓
   No return + Arguments


3. int method()
   ↓
   Return + No arguments


4. int method(int x)
   ↓
   Return + Arguments
```

And finally:

```text
SAME METHOD NAME
       +
DIFFERENT PARAMETERS
       ↓
METHOD OVERLOADING
```

### ⭐ Five things you should be able to answer after this TEACHME

1. **What is a method?**
   → A reusable block of code that performs a specific task.

2. **What are the two broad types?**
   → Predefined and user-defined methods.

3. **What are the four method types based on syntax?**
   → No return/no arguments, no return/arguments, return/no arguments, return/arguments.

4. **What is method overloading?**
   → Same method name with different parameter lists.

5. **Can `main()` be overloaded?**
   → Yes, but the JVM uses `public static void main(String[] args)` as the standard entry point.
