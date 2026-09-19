# Methods in Java — 3LEVEL

Think of **3LEVEL** as learning the same topic in three depths:

* 🟢 **LEVEL 1 → Beginner**
* 🟡 **LEVEL 2 → Intermediate**
* 🔴 **LEVEL 3 → Advanced / Interview**

---

# 🟢 LEVEL 1 — FOUNDATION

## 1. What is a Method?

A **method** is a block of code designed to perform a particular task.

Example:

```java
static void greet() {
    System.out.println("Hello Java");
}
```

Calling it:

```java
greet();
```

Output:

```text
Hello Java
```

### Simple idea

```text
Create method → Call method → Method executes
```

---

## 2. Why do we use methods?

Methods provide:

* **Code reusability**
* **Modularity**
* **Readability**
* **Easy maintenance**
* **Avoiding repeated code**

Instead of writing:

```java
System.out.println("Hello");
System.out.println("Hello");
System.out.println("Hello");
```

we can write:

```java
static void greet() {
    System.out.println("Hello");
}
```

and call:

```java
greet();
greet();
greet();
```

---

# 3. Types of Methods

Broadly:

```text
Methods
  |
  ├── Predefined Methods
  |
  └── User-defined Methods
```

### Predefined method

Already provided by Java.

```java
System.out.println("Hello");
```

`println()` is a predefined method.

### User-defined method

Created by the programmer.

```java
static void greet() {
    System.out.println("Hello");
}
```

`greet()` is user-defined.

---

# 4. Four Types Based on Return + Arguments

This is one of the **most important classifications**.

```text
                    METHODS
                       |
          ---------------------------
          |                         |
       No Return                  Return
          |                         |
       --------                  --------
       |      |                  |      |
     No Arg  Arg               No Arg   Arg
```

### Type 1 — No Return + No Arguments

```java
void add() {
    System.out.println(10 + 20);
}
```

### Type 2 — No Return + Arguments

```java
void add(int a, int b) {
    System.out.println(a + b);
}
```

### Type 3 — Return + No Arguments

```java
int add() {
    return 10 + 20;
}
```

### Type 4 — Return + Arguments

```java
int add(int a, int b) {
    return a + b;
}
```

### Memory trick:

> **Arguments = Input**
> **Return = Output**

---

# 🟡 LEVEL 2 — UNDERSTANDING

## 5. Method Syntax

General syntax:

```java
modifier returnType methodName(parameters) {
    // method body
}
```

Example:

```java
static int add(int a, int b) {
    return a + b;
}
```

Break it down:

```text
static        → modifier
int           → return type
add           → method name
int a, int b  → parameters
return ...    → method body
```

---

# 6. Parameters vs Arguments

This is a very common confusion.

```java
static int add(int a, int b)
```

Here:

```text
a and b → Parameters
```

When calling:

```java
add(10, 20);
```

Here:

```text
10 and 20 → Arguments
```

### Remember:

> **Parameters → method declaration**

> **Arguments → method call**

---

# 7. Method Definition vs Method Call

### Definition

```java
static void hello() {
    System.out.println("Hello");
}
```

This creates the method.

### Call

```java
hello();
```

This executes the method.

Think:

```text
Definition = Create the machine
Call       = Start the machine
```

---

# 8. Return Type

Suppose:

```java
static int add() {
    return 10 + 20;
}
```

The `int` tells Java:

> This method will return an integer value.

Then:

```java
int result = add();
```

The returned value is stored in `result`.

```text
add()
 ↓
30
 ↓
result
```

---

# 9. `void`

`void` means the method doesn't return a value.

```java
static void display() {
    System.out.println("Hello");
}
```

You cannot use it as an `int` result:

```java
int x = display(); // ❌
```

because `display()` returns nothing.

---

# 10. Method Overloading

Now suppose we have:

```java
static void add(int a, int b) {
    System.out.println(a + b);
}

static void add(int a, int b, int c) {
    System.out.println(a + b + c);
}
```

Same name:

```text
add
```

Different parameters:

```text
(int, int)
(int, int, int)
```

This is:

> **Method Overloading**

### Definition

> Method overloading is defining multiple methods with the same name but different parameter lists in the same class.

---

# 11. How can overloaded methods differ?

### Different number of parameters

```java
add(int a, int b)
add(int a, int b, int c)
```

### Different parameter types

```java
add(int a, int b)
add(double a, double b)
```

### Different order of parameter types

```java
show(int a, double b)
show(double a, int b)
```

---

# 12. What doesn't create overloading?

Changing only the return type is **not enough**.

❌ Invalid:

```java
int add(int a, int b) {
    return a + b;
}

double add(int a, int b) {
    return a + b;
}
```

The parameter lists are identical.

Therefore:

> **Return type alone cannot overload a method.**

---

# 🔴 LEVEL 3 — ADVANCED / INTERVIEW

## 13. How does Java select an overloaded method?

Consider:

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

Java needs to decide which `show()` to execute.

A useful three-step mental model is:

```text
Method call
    ↓
1. Number of arguments
    ↓
2. Argument types
    ↓
3. Best matching method
```

For:

```java
show(10);
```

`10` is an `int`.

Therefore:

```java
show(int x)
```

is the exact match.

---

# 14. Can main() be overloaded?

### Yes.

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

So these are two different methods:

```java
main(String[] args)
main(int x)
```

### But important:

The JVM looks for the standard entry point:

```java
public static void main(String[] args)
```

It does not automatically start from:

```java
main(int x)
```

Therefore:

> **`main()` can be overloaded, but only the standard `main(String[] args)` is the JVM entry point.**

---

# 15. Static vs Instance Methods

Methods can also be categorized based on whether they belong to the class or object.

### Static method

```java
static void display() {
    System.out.println("Hello");
}
```

Can be called directly from a static context:

```java
display();
```

### Instance method

```java
void display() {
    System.out.println("Hello");
}
```

Normally requires an object:

```java
Demo d = new Demo();
d.display();
```

So:

```text
Static method
     ↓
Class-level

Instance method
     ↓
Object-level
```

---

# 16. Complete Level-3 Example

```java
class Calculator {

    static int add(int a, int b) {
        return a + b;
    }

    static int add(int a, int b, int c) {
        return a + b + c;
    }

    static double add(double a, double b) {
        return a + b;
    }

    public static void main(String[] args) {

        int result1 = add(10, 20);
        int result2 = add(10, 20, 30);
        double result3 = add(10.5, 20.5);

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }
}
```

Here we have:

```text
add(int, int)
       ↓
2 int arguments

add(int, int, int)
       ↓
3 int arguments

add(double, double)
       ↓
2 double arguments
```

Java selects the appropriate overloaded method based on the arguments.

---

# 🧠 3LEVEL MASTER MAP

```text
                    METHODS IN JAVA
                          │
             ┌────────────┴────────────┐
             │                         │
       PREDEFINED                 USER-DEFINED
             │                         │
       Java-provided              Programmer-created
                                       │
                              ┌────────┴────────┐
                              │                 │
                         PARAMETERS        RETURN VALUE
                              │                 │
                       ┌──────┴──────┐     ┌────┴────┐
                       │             │     │         │
                      NO            YES   NO        YES
                       │             │     │         │
                       └──────┬──────┘     └────┬────┘
                              │                 │
                       FOUR COMBINATIONS
                              │
              ┌───────────────┼────────────────┐
              │               │                │
          void m()       void m(int)       int m()
                                               │
                                            int m(int)
```

And:

```text
Same method name
       +
Different parameter list
       ↓
METHOD OVERLOADING
```

### ⭐ Exam/Interview Core

| Question                                 | Answer                                            |
| ---------------------------------------- | ------------------------------------------------- |
| What is a method?                        | Reusable block of code performing a specific task |
| Why methods?                             | Reusability, modularity, readability, maintenance |
| Types?                                   | Predefined and user-defined                       |
| Four syntax types?                       | Based on return type and arguments                |
| What is a parameter?                     | Variable in method declaration                    |
| What is an argument?                     | Actual value passed during method call            |
| What does `void` mean?                   | No return value                                   |
| Can return type alone overload a method? | ❌ No                                              |
| What is method overloading?              | Same name + different parameter list              |
| Can `main()` be overloaded?              | ✅ Yes                                             |
| Which `main()` is JVM entry point?       | `public static void main(String[] args)`          |
| Arguments represent what?                | Input                                             |
| Return value represents what?            | Output                                            |
