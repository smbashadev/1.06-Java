# Exception Handling in Java — ONEPAGE

> **Note:** Your topic is **Exception Handling**, but the last instruction asks for all concepts of **Encapsulation**. Those are different Java topics. I’ll therefore keep this ONEPAGE focused on **Exception Handling in Java** rather than mixing Encapsulation into it.

---

# 1. What Is an Exception?

An **exception** is an abnormal condition that occurs during program execution and interrupts the normal flow of the program.

Example:

```java
class Test
{
    public static void main(String[] args)
    {
        int a = 10;
        int b = 0;

        int c = a / b;

        System.out.println(c);
    }
}
```

Output:

```text
Exception in thread "main" java.lang.ArithmeticException: / by zero
```

Here:

```text
10 / 0
  ↓
Exception occurs
  ↓
Normal flow stops
```

---

# 2. What Is Exception Handling?

> **Exception handling is the mechanism used to detect, handle, and recover from exceptions so that the abnormal situation does not unnecessarily terminate the program.**

Java mainly provides:

```text
try
catch
finally
throw
throws
```

---

# 3. Why Do We Need Exception Handling?

Without exception handling:

```text
Program
   ↓
Exception
   ↓
Abnormal termination
```

With exception handling:

```text
Program
   ↓
Exception
   ↓
Exception Handler
   ↓
Recovery / Alternative action
   ↓
Program continues
```

### Importance

* Prevents abnormal program termination.
* Separates error-handling code from normal code.
* Helps maintain normal program flow.
* Makes programs more reliable.
* Provides meaningful error information.
* Helps handle unexpected runtime conditions.

---

# 4. Exception Hierarchy

```text
                    Object
                      |
                  Throwable
                 /         \
                /           \
         Exception           Error
             |
      RuntimeException
       /      |       \
      /       |        \
Arithmetic  NullPointer  ArrayIndexOutOfBounds
Exception   Exception      Exception
```

### Main division:

```text
Throwable
   |
   ├── Exception
   |
   └── Error
```

---

# 5. Exception vs Error

| Exception                                                 | Error                                                       |
| --------------------------------------------------------- | ----------------------------------------------------------- |
| Generally represents conditions an application may handle | Generally represents serious JVM/system problems            |
| Often recoverable                                         | Usually not intended for normal recovery                    |
| Example: `ArithmeticException`                            | Example: `OutOfMemoryError`                                 |
| Can commonly be handled using `try-catch`                 | Usually should not be handled as ordinary application logic |

---

# 6. Types of Exceptions

The commonly discussed categories are:

```text
Exception
   |
   ├── Checked Exception
   |
   └── Unchecked Exception
```

---

## Checked Exception

Checked exceptions are checked by the compiler.

Example:

```java
import java.io.*;

class Test
{
    public static void main(String[] args) throws IOException
    {
        FileReader f = new FileReader("abc.txt");
    }
}
```

Examples:

```text
IOException
SQLException
ClassNotFoundException
FileNotFoundException
```

They generally must be **caught or declared**.

---

## Unchecked Exception

Unchecked exceptions occur at runtime and are subclasses of `RuntimeException`.

Example:

```java
class Test
{
    public static void main(String[] args)
    {
        int a = 10 / 0;
    }
}
```

Common examples:

```text
ArithmeticException
NullPointerException
ArrayIndexOutOfBoundsException
NumberFormatException
```

---

# 7. `try` Block

The `try` block contains code that may produce an exception.

```java
try
{
    int a = 10 / 0;
}
```

But `try` normally needs an associated `catch` and/or `finally`.

---

# 8. `catch` Block

The `catch` block handles an exception.

```java
class Test
{
    public static void main(String[] args)
    {
        try
        {
            int a = 10 / 0;
            System.out.println(a);
        }
        catch(ArithmeticException e)
        {
            System.out.println("Cannot divide by zero");
        }
    }
}
```

Output:

```text
Cannot divide by zero
```

### Flow

```text
try
 ↓
Exception occurs
 ↓
Matching catch
 ↓
Handler executes
 ↓
Program continues
```

---

# 9. `finally` Block

The `finally` block is used for code that should normally execute whether an exception occurs or not.

```java
class Test
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println(10 / 2);
        }
        catch(ArithmeticException e)
        {
            System.out.println("Exception");
        }
        finally
        {
            System.out.println("Finally executed");
        }
    }
}
```

Output:

```text
5
Finally executed
```

---

# 10. Complete `try-catch-finally`

```java
class Test
{
    public static void main(String[] args)
    {
        try
        {
            int a = 10;
            int b = 0;

            System.out.println(a / b);
        }
        catch(ArithmeticException e)
        {
            System.out.println("Division by zero is not allowed");
        }
        finally
        {
            System.out.println("Program completed");
        }
    }
}
```

Output:

```text
Division by zero is not allowed
Program completed
```

---

# 11. Multiple `catch` Blocks

A single `try` can have multiple `catch` blocks.

```java
class Test
{
    public static void main(String[] args)
    {
        try
        {
            int[] a = {10, 20, 30};

            System.out.println(a[5]);
        }
        catch(ArithmeticException e)
        {
            System.out.println("Arithmetic problem");
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Array index problem");
        }
    }
}
```

Output:

```text
Array index problem
```

### Flow

```text
                  try
                   |
          Exception occurs
                   |
       ┌───────────┼───────────┐
       ↓           ↓           ↓
 Arithmetic     Array       Other
  catch         catch       catch
       |
       ↓
 Matching catch executes
```

Only the **matching handler** executes.

---

# 12. Catch Order Is Important

This is wrong:

```java
try
{
    // code
}
catch(Exception e)
{
}
catch(ArithmeticException e)
{
}
```

Why?

`ArithmeticException` is already covered by `Exception`.

Correct:

```java
try
{
    // code
}
catch(ArithmeticException e)
{
}
catch(Exception e)
{
}
```

### Rule

```text
Specific exception
       ↓
General exception
```

---

# 13. `throw` Keyword

`throw` is used to explicitly throw an exception.

```java
class Test
{
    public static void main(String[] args)
    {
        int age = 15;

        if(age < 18)
        {
            throw new ArithmeticException("Not eligible");
        }

        System.out.println("Eligible");
    }
}
```

Output:

```text
Exception in thread "main" java.lang.ArithmeticException: Not eligible
```

Basic structure:

```java
throw new ExceptionType("message");
```

---

# 14. `throws` Keyword

`throws` is used in a method declaration to indicate that a method may pass an exception to its caller.

```java
import java.io.*;

class Test
{
    static void readFile() throws IOException
    {
        FileReader f = new FileReader("abc.txt");
    }

    public static void main(String[] args) throws IOException
    {
        readFile();
    }
}
```

Remember:

```text
throw
 ↓
Actually throws one exception

throws
 ↓
Declares possible exception(s)
```

---

# 15. `throw` vs `throws`

| `throw`                               | `throws`                        |
| ------------------------------------- | ------------------------------- |
| Used to explicitly throw an exception | Used to declare exceptions      |
| Used inside method/block              | Used in method declaration      |
| Throws an exception object            | Indicates possible exceptions   |
| Generally one exception at a time     | Can declare multiple exceptions |

Example:

```java
throw new ArithmeticException();
```

vs

```java
void test() throws IOException
```

---

# 16. Exception Object

When an exception occurs, Java creates an exception object.

```text
Exception occurs
      ↓
Exception object created
      ↓
JVM searches for handler
      ↓
Matching catch found
      ↓
Handler executes
```

The reference:

```java
catch(Exception e)
```

refers to that exception object.

---

# 17. Important Exception Methods

The `Throwable` class provides useful methods.

### `getMessage()`

Returns the exception message.

```java
catch(Exception e)
{
    System.out.println(e.getMessage());
}
```

---

### `toString()`

Returns exception class name and message.

```java
catch(Exception e)
{
    System.out.println(e.toString());
}
```

---

### `printStackTrace()`

Prints detailed stack-trace information.

```java
catch(Exception e)
{
    e.printStackTrace();
}
```

---

# 18. Example Using All Three

```java
class Test
{
    public static void main(String[] args)
    {
        try
        {
            int a = 10 / 0;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
```

Typical output includes:

```text
/ by zero
java.lang.ArithmeticException: / by zero
```

followed by stack-trace information.

---

# 19. Nested `try`

A `try` block can occur inside another `try`.

```java
class Test
{
    public static void main(String[] args)
    {
        try
        {
            try
            {
                int a = 10 / 0;
            }
            catch(ArithmeticException e)
            {
                System.out.println("Inner catch");
            }
        }
        catch(Exception e)
        {
            System.out.println("Outer catch");
        }
    }
}
```

Output:

```text
Inner catch
```

The inner `catch` handles the exception.

---

# 20. User-Defined Exception

We can create our own exception class.

```java
class AgeException extends Exception
{
    AgeException(String message)
    {
        super(message);
    }
}

class Test
{
    public static void main(String[] args)
    {
        int age = 15;

        try
        {
            if(age < 18)
            {
                throw new AgeException("Age must be 18 or above");
            }

            System.out.println("Eligible");
        }
        catch(AgeException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
```

Output:

```text
Age must be 18 or above
```

---

# 21. Exception Propagation

Suppose:

```text
method3()
   ↓
method2()
   ↓
method1()
   ↓
main()
```

If `method3()` does not handle an exception:

```text
method3
  ↓
method2
  ↓
method1
  ↓
main
```

Java searches upward for a matching handler.

Example:

```java
class Test
{
    static void method3()
    {
        int a = 10 / 0;
    }

    static void method2()
    {
        method3();
    }

    static void method1()
    {
        method2();
    }

    public static void main(String[] args)
    {
        try
        {
            method1();
        }
        catch(ArithmeticException e)
        {
            System.out.println("Exception handled in main");
        }
    }
}
```

Output:

```text
Exception handled in main
```

---

# 22. `final`, `finally`, `finalize()`

These are **three different things**.

| Term         | Meaning                                                                |
| ------------ | ---------------------------------------------------------------------- |
| `final`      | Keyword used with variables, methods, classes                          |
| `finally`    | Exception-handling block                                               |
| `finalize()` | Historical GC-related method; deprecated and should not be relied upon |

Example:

```java
final int x = 10;
```

```java
finally
{
    System.out.println("Cleanup");
}
```

Do not confuse them.

---

# 23. Try-With-Resources

For resources that implement `AutoCloseable`, Java provides try-with-resources.

```java
import java.io.*;

class Test
{
    public static void main(String[] args)
    {
        try(FileReader f = new FileReader("abc.txt"))
        {
            System.out.println("File opened");
        }
        catch(IOException e)
        {
            System.out.println("File problem");
        }
    }
}
```

The resource is automatically closed when leaving the try-with-resources statement.

---

# 24. Complete Exception-Handling Tree

```text
                         Throwable
                            |
              ┌─────────────┴─────────────┐
              ↓                           ↓
         Exception                       Error
              |
      ┌───────┴────────┐
      ↓                ↓
   Checked         RuntimeException
   Exceptions          |
                       ├── ArithmeticException
                       ├── NullPointerException
                       ├── NumberFormatException
                       └── ArrayIndexOutOfBoundsException
```

---

# 25. Complete Exception-Handling Flow

```text
                Java Program
                     |
                     ↓
              Risky code
                     |
                     ↓
                   try
                     |
             Exception occurs?
                /          \
              No            Yes
              ↓              ↓
        Continue       Search handler
                             |
                             ↓
                       Matching catch?
                         /        \
                       Yes         No
                        ↓           ↓
                    Handle      Propagate
                    exception       ↓
                        |       Caller method
                        ↓           ↓
                    finally     Continue search
                        |
                        ↓
                  Continue / exit
```

---

# 26. One Complete Program

```java
class Bank
{
    static void withdraw(int balance, int amount)
    {
        try
        {
            if(amount > balance)
            {
                throw new ArithmeticException("Insufficient balance");
            }

            System.out.println("Withdrawal successful");
            System.out.println("Remaining balance = " +
                               (balance - amount));
        }
        catch(ArithmeticException e)
        {
            System.out.println("Transaction failed");
            System.out.println(e.getMessage());
        }
        finally
        {
            System.out.println("Transaction process completed");
        }
    }

    public static void main(String[] args)
    {
        withdraw(5000, 6000);
    }
}
```

Output:

```text
Transaction failed
Insufficient balance
Transaction process completed
```

### Program explanation

```text
main()
  ↓
withdraw()
  ↓
amount > balance
  ↓
throw ArithmeticException
  ↓
catch()
  ↓
Error message displayed
  ↓
finally()
  ↓
Transaction completed
```

---

# 🔥 ONEPAGE FINAL REVISION

```text
                 EXCEPTION HANDLING
                         |
       ┌─────────────────┼─────────────────┐
       ↓                 ↓                 ↓
    Detect             Handle            Recover
       |                 |                 |
      try              catch            finally
                         |
                ┌────────┴────────┐
                ↓                 ↓
             Checked          Unchecked
                                  |
                           RuntimeException
```

### Five keywords

```text
try
 ↓
Risky code

catch
 ↓
Handles exception

finally
 ↓
Cleanup / code that normally executes

throw
 ↓
Explicitly throws exception

throws
 ↓
Declares possible exception
```

### Most important differences

```text
throw  → throws an exception
throws → declares exception

Exception → generally application-level exceptional condition
Error     → serious JVM/system-level problem

Checked   → compiler checks handling/propagation
Unchecked → RuntimeException and its subclasses

final     → keyword
finally   → exception-handling block
finalize  → deprecated historical cleanup mechanism
```

## ⭐ Final Definition

> **Exception handling in Java is a mechanism for handling exceptional conditions during program execution using constructs such as `try`, `catch`, `finally`, `throw`, and `throws`, thereby allowing a program to respond to errors in a controlled manner rather than simply terminating because of an uncaught exception.**
