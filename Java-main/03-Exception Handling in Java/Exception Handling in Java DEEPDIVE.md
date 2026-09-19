# Exception Handling in Java — DEEPDIVE

Exception Handling is one of the most important topics in Java because it explains **what happens when something goes wrong during program execution and how Java allows us to handle that situation in a controlled way**.

---

# 1. Exception — Definition

> **An exception is an abnormal event or condition that occurs during the execution of a program and disrupts the normal flow of program execution.**

Example:

```java
class Demo
{
    public static void main(String[] args)
    {
        int a = 10;
        int b = 0;

        System.out.println(a / b);

        System.out.println("Hello");
    }
}
```

Output:

```text
Exception in thread "main" java.lang.ArithmeticException: / by zero
```

Notice that:

```java
System.out.println("Hello");
```

is never executed.

Why?

```text
10 / 0
   ↓
Exception occurs
   ↓
Normal flow interrupted
   ↓
Program terminates
```

---

# 2. What Is Exception Handling?

> **Exception handling is the mechanism provided by Java to detect, handle, and manage exceptional situations so that the program can respond to them in a controlled manner.**

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
Risky operation
   ↓
Exception
   ↓
Handler
   ↓
Alternative/recovery action
   ↓
Program continues
```

---

# 3. Why Do We Need Exception Handling?

Consider a banking program.

```java
int balance = 5000;
int amount = 10000;
```

If the program doesn't properly handle an invalid withdrawal, the application may behave incorrectly.

Similarly, applications encounter situations such as:

* dividing by zero
* accessing an invalid array index
* accessing an object through a `null` reference
* entering an invalid number format
* attempting to open a missing file
* database failures
* network failures

Exception handling allows us to deal with such situations deliberately.

### Importance

```text
Exception Handling
        |
        ├── Prevents uncontrolled termination
        ├── Maintains normal program flow where possible
        ├── Separates normal logic from error handling
        ├── Provides meaningful error information
        ├── Improves reliability
        ├── Helps resource cleanup
        └── Supports recovery/alternative processing
```

---

# 4. Exception Hierarchy

The most important hierarchy is:

```text
                         Object
                            |
                         Throwable
                       /           \
                      /             \
               Exception            Error
                   |
             RuntimeException
             /      |       \
            /       |        \
ArithmeticException  NullPointerException
                    ArrayIndexOutOfBoundsException
                    NumberFormatException
```

The hierarchy is important because **catch matching follows the type hierarchy**.

---

# 5. `Throwable`

`Throwable` is the root type for objects that can be thrown and caught by Java's exception mechanism.

It has two major branches:

```text
Throwable
   |
   ├── Exception
   |
   └── Error
```

---

# 6. Exception

`Exception` represents conditions that applications may commonly need to handle.

Examples:

```text
IOException
SQLException
ClassNotFoundException
RuntimeException
```

---

# 7. Error

`Error` represents serious problems generally associated with the JVM or runtime environment.

Examples:

```text
OutOfMemoryError
StackOverflowError
NoClassDefFoundError
```

Errors are generally not treated as ordinary application-level exceptions to recover from.

---

# 8. Exception vs Error

| Exception                                                     | Error                                                   |
| ------------------------------------------------------------- | ------------------------------------------------------- |
| Generally represents application-level exceptional conditions | Generally represents serious JVM/runtime problems       |
| Often possible to handle                                      | Usually not intended for normal recovery                |
| Example: `IOException`                                        | Example: `OutOfMemoryError`                             |
| Application may reasonably anticipate some exceptions         | Usually indicates a serious environment/runtime problem |

---

# 9. Types of Exceptions

At a practical Java level, exceptions are commonly divided into:

```text
                    Exception
                       |
             ┌─────────┴─────────┐
             ↓                   ↓
        Checked             Unchecked
        Exception           Exception
                                 |
                          RuntimeException
```

---

# 10. Checked Exception

A checked exception is an exception that the compiler requires you to account for through handling or declaration.

Example:

```java
import java.io.FileReader;
import java.io.FileNotFoundException;

class Demo
{
    public static void main(String[] args)
    {
        try
        {
            FileReader f = new FileReader("abc.txt");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }
}
```

Output if the file doesn't exist:

```text
File not found
```

Examples:

```text
IOException
FileNotFoundException
SQLException
ClassNotFoundException
```

---

# 11. Unchecked Exception

Unchecked exceptions are subclasses of `RuntimeException`.

They are not subject to compile-time catch-or-declare checking.

Example:

```java
class Demo
{
    public static void main(String[] args)
    {
        int a = 10 / 0;
    }
}
```

The program compiles, but fails during execution.

Common examples:

```text
ArithmeticException
NullPointerException
ArrayIndexOutOfBoundsException
NumberFormatException
ClassCastException
```

---

# 12. Checked vs Unchecked

| Checked                                                                    | Unchecked                                   |
| -------------------------------------------------------------------------- | ------------------------------------------- |
| Compiler requires handling or declaration                                  | Compiler does not require catch/declare     |
| Generally subclasses of `Exception` excluding `RuntimeException` hierarchy | Subclasses of `RuntimeException`            |
| Often external/environmental conditions                                    | Often programming/runtime conditions        |
| Example: `IOException`                                                     | Example: `ArithmeticException`              |
| Can use `try-catch` or `throws`                                            | Can use `try-catch`, but it isn't mandatory |

---

# 13. Five Important Keywords

Java provides five important exception-handling keywords:

```text
try
catch
finally
throw
throws
```

Tree:

```text
             Exception Handling
                     |
       ┌─────────────┼─────────────┐
       ↓             ↓             ↓
      try          catch         finally
                     |
              ┌──────┴──────┐
              ↓             ↓
            throw         throws
```

---

# 14. `try` Block

The `try` block contains code where an exception may occur.

Syntax:

```java
try
{
    // risky code
}
```

A `try` statement must be followed by at least one `catch` or a `finally`.

Example:

```java
class Demo
{
    public static void main(String[] args)
    {
        try
        {
            int a = 10 / 0;
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

---

# 15. How `try-catch` Works

```text
                try
                 |
                 ↓
          Risky statement
                 |
        ┌────────┴────────┐
        ↓                 ↓
  No exception       Exception occurs
        ↓                 ↓
 Continue normally   Search matching
                         catch
                           |
                           ↓
                      Handler executes
```

---

# 16. `catch` Block

A `catch` block handles a matching exception.

Syntax:

```java
catch(ExceptionType reference)
{
    // handling code
}
```

Example:

```java
catch(ArithmeticException e)
{
    System.out.println("Arithmetic problem");
}
```

Here:

```text
ArithmeticException
       ↓
Exception type

e
↓
Reference to exception object
```

---

# 17. Exception Object

When an exception occurs, Java creates an object representing that exception.

Conceptually:

```text
Exception occurs
       ↓
Exception object created
       ↓
JVM searches for handler
       ↓
Matching catch
       ↓
catch reference receives exception object
```

For:

```java
catch(ArithmeticException e)
```

`e` refers to the exception object.

---

# 18. `finally` Block

`finally` contains code intended to execute when control leaves the `try`/`catch` construct, subject to exceptional JVM termination scenarios.

Example:

```java
class Demo
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

# 19. Why Is `finally` Important?

It is traditionally used for cleanup operations such as:

```text
closing resources
releasing resources
cleanup operations
```

For modern resource management, **try-with-resources** is generally preferred for `AutoCloseable` resources.

---

# 20. `try-catch-finally` Complete Program

```java
class Demo
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
            System.out.println("Execution completed");
        }
    }
}
```

Output:

```text
Division by zero is not allowed
Execution completed
```

Flow:

```text
try
 ↓
10 / 0
 ↓
ArithmeticException
 ↓
catch
 ↓
Message displayed
 ↓
finally
 ↓
Execution completed
```

---

# 21. Can `try` Exist Without `catch`?

### Yes, if there is `finally`.

```java
class Demo
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println("Inside try");
        }
        finally
        {
            System.out.println("Inside finally");
        }
    }
}
```

Output:

```text
Inside try
Inside finally
```

But:

```java
try
{
}
```

by itself is invalid.

---

# 22. Multiple `catch` Blocks

A single `try` can have multiple `catch` blocks.

```java
class Demo
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

Only the matching handler executes.

---

# 23. Catch Order

This is invalid:

```java
try
{
}
catch(Exception e)
{
}
catch(ArithmeticException e)
{
}
```

Why?

Because:

```text
Exception
   ↑
ArithmeticException
```

The first `catch` already catches `ArithmeticException`.

Correct:

```java
try
{
}
catch(ArithmeticException e)
{
}
catch(Exception e)
{
}
```

### Golden rule:

> **Write more specific exception handlers before more general exception handlers.**

---

# 24. Multi-Catch

Java allows multiple exception types in one catch block.

```java
class Demo
{
    public static void main(String[] args)
    {
        try
        {
            int a = Integer.parseInt("ABC");
        }
        catch(ArithmeticException | NumberFormatException e)
        {
            System.out.println("Invalid operation or number");
        }
    }
}
```

Output:

```text
Invalid operation or number
```

This avoids repeating the same handling code.

---

# 25. `throw` Keyword

`throw` is used when we explicitly want to throw an exception object.

Syntax:

```java
throw new ExceptionType("message");
```

Example:

```java
class Demo
{
    public static void main(String[] args)
    {
        int age = 15;

        if(age < 18)
        {
            throw new IllegalArgumentException(
                "Age must be 18 or above"
            );
        }

        System.out.println("Eligible");
    }
}
```

Output:

```text
Exception in thread "main" java.lang.IllegalArgumentException:
Age must be 18 or above
```

---

# 26. Why Use `throw`?

Suppose the program has a business rule:

```text
Age < 18
    ↓
Not eligible
```

Java won't automatically know your business rule.

You can explicitly create and throw an exception:

```java
throw new IllegalArgumentException("Not eligible");
```

---

# 27. `throws` Keyword

`throws` is used in a method declaration.

Example:

```java
import java.io.*;

class Demo
{
    static void readFile() throws IOException
    {
        FileReader f = new FileReader("abc.txt");
    }
}
```

It means:

> This method may result in an `IOException`; the caller must account for it.

---

# 28. `throw` vs `throws`

| `throw`                               | `throws`                             |
| ------------------------------------- | ------------------------------------ |
| Used to explicitly throw an exception | Used to declare possible exceptions  |
| Used inside method/block              | Used in method declaration           |
| Followed by an exception object       | Followed by exception type(s)        |
| Actually initiates throwing           | Does not itself throw an exception   |
| Usually one object at a time          | Can declare multiple exception types |

Example:

```java
throw new IOException();
```

vs:

```java
void test() throws IOException
```

---

# 29. Exception Propagation

Suppose:

```text
main()
  ↓
method1()
  ↓
method2()
  ↓
method3()
```

If `method3()` generates an exception and doesn't handle it:

```text
method3()
   ↓
method2()
   ↓
method1()
   ↓
main()
```

Java searches for an appropriate handler up the call stack.

Example:

```java
class Demo
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

# 30. Exception Propagation Tree

```text
main()
  |
  ↓
method1()
  |
  ↓
method2()
  |
  ↓
method3()
  |
  ↓
Exception
  |
  ↓
method3 catch?
  | No
  ↓
method2 catch?
  | No
  ↓
method1 catch?
  | No
  ↓
main catch?
  | Yes
  ↓
Exception handled
```

---

# 31. Nested `try`

A `try` can be placed inside another `try`.

```java
class Demo
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println("Outer try");

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
Outer try
Inner catch
```

The inner `catch` handles the exception.

---

# 32. Important Exception Methods

Most important methods come from `Throwable`.

---

## `getMessage()`

Returns the detail message.

```java
catch(Exception e)
{
    System.out.println(e.getMessage());
}
```

Example output:

```text
/ by zero
```

---

## `toString()`

Returns a string containing the exception class name and message.

```java
catch(Exception e)
{
    System.out.println(e.toString());
}
```

Typical output:

```text
java.lang.ArithmeticException: / by zero
```

---

## `printStackTrace()`

Prints detailed stack-trace information.

```java
catch(Exception e)
{
    e.printStackTrace();
}
```

It helps identify:

```text
Exception type
Exception message
Class
Method
Line number
Call sequence
```

---

# 33. Complete Program Using Exception Methods

```java
class Demo
{
    public static void main(String[] args)
    {
        try
        {
            int a = 10 / 0;
        }
        catch(Exception e)
        {
            System.out.println("Message:");
            System.out.println(e.getMessage());

            System.out.println("toString:");
            System.out.println(e.toString());

            System.out.println("Stack trace:");
            e.printStackTrace();
        }
    }
}
```

Typical output contains:

```text
Message:
/ by zero

toString:
java.lang.ArithmeticException: / by zero

Stack trace:
java.lang.ArithmeticException: / by zero
    at Demo.main(...)
```

The exact stack-trace formatting depends on the Java version/runtime.

---

# 34. Other Useful `Throwable` Methods

Important methods include:

```text
getMessage()
getLocalizedMessage()
toString()
printStackTrace()
getCause()
initCause()
getSuppressed()
addSuppressed()
```

These support inspecting an exception and understanding its cause/related suppressed exceptions.

---

# 35. Cause of an Exception

One exception can have another exception as its cause.

Conceptually:

```text
High-level exception
       |
       ↓
Caused by
       |
       ↓
Original exception
```

Example:

```java
class Demo
{
    public static void main(String[] args)
    {
        Exception original =
            new ArithmeticException("/ by zero");

        Exception outer =
            new Exception("Operation failed", original);

        System.out.println(outer.getMessage());
        System.out.println(outer.getCause());
    }
}
```

Output:

```text
Operation failed
java.lang.ArithmeticException: / by zero
```

---

# 36. User-Defined Exception

Java allows us to create custom exception classes.

Example:

```java
class AgeException extends Exception
{
    AgeException(String message)
    {
        super(message);
    }
}
```

Now use it:

```java
class Demo
{
    public static void main(String[] args)
    {
        int age = 15;

        try
        {
            if(age < 18)
            {
                throw new AgeException(
                    "Age must be 18 or above"
                );
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

# 37. Why `super(message)`?

Our constructor:

```java
AgeException(String message)
{
    super(message);
}
```

passes the message to the superclass constructor.

The superclass is:

```text
AgeException
    ↓
Exception
    ↓
Throwable
```

Therefore the message becomes available through:

```java
e.getMessage();
```

---

# 38. Custom Checked Exception

Because:

```java
class AgeException extends Exception
```

extends `Exception` directly rather than `RuntimeException`, it is a checked exception.

Therefore code that throws it must be handled or declared.

---

# 39. Custom Unchecked Exception

We can instead write:

```java
class AgeException extends RuntimeException
{
    AgeException(String message)
    {
        super(message);
    }
}
```

Now it is an unchecked exception.

```text
AgeException
      |
      ├── extends Exception
      │       ↓
      │   Checked
      │
      └── extends RuntimeException
              ↓
          Unchecked
```

---

# 40. Try-With-Resources

Java provides try-with-resources for resources implementing `AutoCloseable`.

Example:

```java
import java.io.*;

class Demo
{
    public static void main(String[] args)
    {
        try(FileReader f = new FileReader("abc.txt"))
        {
            System.out.println("File opened");
        }
        catch(IOException e)
        {
            System.out.println("File operation failed");
        }
    }
}
```

The resource is automatically closed when the try-with-resources statement finishes.

---

# 41. Why Try-With-Resources Is Better for Resources

Traditional style:

```text
open resource
    ↓
use resource
    ↓
finally
    ↓
close resource
```

Try-with-resources:

```text
try(resource)
      ↓
use resource
      ↓
automatic close
```

It reduces resource-leak risks and makes cleanup code simpler.

---

# 42. `final`, `finally`, `finalize()`

These three are frequently confused.

| Term         | Meaning                                  |
| ------------ | ---------------------------------------- |
| `final`      | Keyword                                  |
| `finally`    | Exception-handling block                 |
| `finalize()` | Old GC-related method that is deprecated |

Example of `final`:

```java
final int x = 10;
```

Example of `finally`:

```java
finally
{
    System.out.println("Cleanup");
}
```

`finalize()` should not be used as a modern resource-management mechanism.

---

# 43. Can `finally` Fail to Execute?

Under normal Java control flow, `finally` executes when control leaves the associated `try`/`catch`.

But there are exceptional situations such as:

```text
JVM termination
System.exit()
fatal VM/process failure
```

where normal cleanup cannot be guaranteed.

Therefore don't use `finally` as an absolute guarantee against every possible process termination.

---

# 44. Can We Have `catch` Without `try`?

### ❌ No.

Invalid:

```java
catch(Exception e)
{
}
```

A `catch` must belong to a `try` statement.

---

# 45. Can We Have Multiple `finally` Blocks?

### ❌ No

One `try` statement can have at most one `finally` block.

---

# 46. Can We Have Multiple `catch` Blocks?

### ✅ Yes

```java
try
{
}
catch(ArithmeticException e)
{
}
catch(NullPointerException e)
{
}
catch(Exception e)
{
}
```

---

# 47. Can We Have `try` Without `catch`?

### ✅ Yes, with `finally`.

```java
try
{
    System.out.println("Hello");
}
finally
{
    System.out.println("Cleanup");
}
```

---

# 48. Can We Have `try` With Only `catch`?

### ✅ Yes.

```java
try
{
    int x = 10 / 0;
}
catch(ArithmeticException e)
{
    System.out.println("Handled");
}
```

---

# 49. Can We Have `try`, `catch`, and `finally` Together?

### ✅ Yes.

```java
try
{
}
catch(Exception e)
{
}
finally
{
}
```

This is the most familiar form.

---

# 50. Can We Put `try` Inside `catch`?

### ✅ Yes.

Example:

```java
try
{
    int a = 10 / 0;
}
catch(ArithmeticException e)
{
    try
    {
        System.out.println("Handling exception");
    }
    catch(Exception x)
    {
        System.out.println("Nested handling");
    }
}
```

---

# 51. Can We Put `try` Inside `finally`?

### ✅ Yes.

```java
try
{
    System.out.println("Main try");
}
finally
{
    try
    {
        System.out.println("Try inside finally");
    }
    catch(Exception e)
    {
        System.out.println("Handled");
    }
}
```

Java allows nesting of these constructs.

---

# 52. Common Built-In Exceptions

## `ArithmeticException`

```java
int x = 10 / 0;
```

---

## `NullPointerException`

```java
String s = null;

System.out.println(s.length());
```

---

## `ArrayIndexOutOfBoundsException`

```java
int[] a = {10, 20};

System.out.println(a[5]);
```

---

## `NumberFormatException`

```java
int x = Integer.parseInt("ABC");
```

---

## `ClassCastException`

Occurs when an object is incorrectly cast to an incompatible type.

---

## `IllegalArgumentException`

Used when a method receives an inappropriate argument.

---

# 53. Complete Program Demonstrating Multiple Exceptions

```java
class Demo
{
    public static void main(String[] args)
    {
        try
        {
            String value = "ABC";

            int number = Integer.parseInt(value);

            System.out.println(number);
        }
        catch(NumberFormatException e)
        {
            System.out.println("Invalid number format");
        }
        catch(Exception e)
        {
            System.out.println("Some other exception occurred");
        }
    }
}
```

Output:

```text
Invalid number format
```

---

# 54. Exception Handling and Program Flow

Consider:

```java
class Demo
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println("A");

            int x = 10 / 0;

            System.out.println("B");
        }
        catch(ArithmeticException e)
        {
            System.out.println("C");
        }

        System.out.println("D");
    }
}
```

Output:

```text
A
C
D
```

Why isn't `B` printed?

Because:

```text
A
 ↓
Exception
 ↓
B is skipped
 ↓
catch
 ↓
C
 ↓
D
```

---

# 55. Very Important Rule: After Exception in `try`

Once an exception occurs inside a `try` block:

```text
Statements before exception
       ↓
execute

Exception statement
       ↓
exception

Statements after exception
       ↓
normally skipped
```

Control transfers to the appropriate handler.

---

# 56. `catch(Exception e)` vs Specific Catch

This:

```java
catch(Exception e)
```

can catch many exception types because `Exception` is a superclass.

But this:

```java
catch(ArithmeticException e)
```

is more specific.

### Best practice:

Prefer specific exceptions when you know what you are handling.

```text
Specific
   ↓
General
```

---

# 57. Don't Catch Everything Blindly

Avoid unnecessary code like:

```java
try
{
    // everything
}
catch(Exception e)
{
    System.out.println("Something happened");
}
```

A broad catch can hide programming problems and make debugging harder.

Use a specific exception when appropriate.

---

# 58. Exception Handling vs Error Handling

They aren't exactly identical concepts.

```text
Error handling
   ↓
Broad concept of responding to failures

Exception handling
   ↓
Java's specific mechanism around Throwable/Exception
```

In Java programming discussions, "error handling" is often used informally to include exception handling, but `Error` itself is also a specific branch of `Throwable`.

---

# 59. Exception Handling Tree — Complete

```text
                         Object
                            |
                         Throwable
                            |
             ┌──────────────┴──────────────┐
             |                             |
             ↓                             ↓
        Exception                        Error
             |
      ┌──────┴────────┐
      |               |
      ↓               ↓
 Checked         RuntimeException
 Exceptions           |
                      ├── ArithmeticException
                      ├── NullPointerException
                      ├── NumberFormatException
                      ├── ArrayIndexOutOfBoundsException
                      ├── ClassCastException
                      └── IllegalArgumentException
```

---

# 60. Complete Exception Handling Block Diagram

```text
                         PROGRAM
                            |
                            ↓
                      Risky operation
                            |
                            ↓
                          try
                            |
                  ┌─────────┴─────────┐
                  |                   |
            No exception         Exception
                  |                   |
                  ↓                   ↓
           Continue flow        Exception object
                                      |
                                      ↓
                              Search matching catch
                                      |
                         ┌────────────┴────────────┐
                         |                         |
                       Found                    Not found
                         |                         |
                         ↓                         ↓
                     catch()                 Propagation
                         |                         |
                         └────────────┬────────────┘
                                      ↓
                                   finally
                                      |
                                      ↓
                              Continue / terminate
```

---

# 61. Exception Handling vs `if-else`

Suppose:

```java
if(b == 0)
{
    System.out.println("Cannot divide");
}
else
{
    System.out.println(a / b);
}
```

This is appropriate when zero is an expected condition that you can easily check beforehand.

Exception handling is useful when an exceptional condition occurs during an operation or when an API communicates failure through exceptions.

### Don't use exceptions for every ordinary decision.

---

# 62. Exception Handling and Encapsulation

Since you previously studied Encapsulation, here's the connection:

```text
Encapsulation
     ↓
Protects data and controls access

Exception Handling
     ↓
Protects program flow from exceptional conditions
```

Example:

```text
Bank Account
    |
    ├── Encapsulation
    │      ↓
    │   private balance
    │   controlled methods
    │
    └── Exception Handling
           ↓
       invalid withdrawal
       insufficient balance
       invalid input
```

They solve different problems but can work together.

---

# 63. Complete Bank Program Using Encapsulation + Exception Handling

```java
class BankAccount
{
    private double balance;

    BankAccount(double balance)
    {
        this.balance = balance;
    }

    public void withdraw(double amount)
    {
        if(amount <= 0)
        {
            throw new IllegalArgumentException(
                "Amount must be greater than zero"
            );
        }

        if(amount > balance)
        {
            throw new IllegalArgumentException(
                "Insufficient balance"
            );
        }

        balance = balance - amount;

        System.out.println(
            "Withdrawal successful"
        );
    }

    public double getBalance()
    {
        return balance;
    }
}

class Test
{
    public static void main(String[] args)
    {
        BankAccount account =
            new BankAccount(5000);

        try
        {
            account.withdraw(6000);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println(
            "Balance = " + account.getBalance()
        );
    }
}
```

Output:

```text
Insufficient balance
Balance = 5000.0
```

### What happened?

```text
private balance
      ↓
Encapsulation protects data
      ↓
withdraw()
      ↓
Invalid withdrawal detected
      ↓
Exception thrown
      ↓
main() catches exception
      ↓
Balance remains protected
```

This is an excellent example of combining two concepts without confusing their purposes.

---

# 64. Exception Handling Best Practices

### 1. Catch specific exceptions

Prefer:

```java
catch(NumberFormatException e)
```

when you know that is the problem.

---

### 2. Don't silently swallow exceptions

Avoid:

```java
catch(Exception e)
{
}
```

An empty catch can hide serious problems.

---

### 3. Provide useful messages

Instead of:

```text
Error
```

prefer:

```text
Invalid account number
```

when appropriate.

---

### 4. Use `finally` or try-with-resources appropriately

For modern resource management:

```java
try(resource)
{
}
```

is generally preferred.

---

### 5. Don't use exceptions for ordinary control flow

Use normal conditions for predictable decisions.

---

# 65. Important Differences — Complete Table

| Concept                | Purpose                                            |
| ---------------------- | -------------------------------------------------- |
| `try`                  | Contains risky code                                |
| `catch`                | Handles a matching exception                       |
| `finally`              | Performs code that should normally execute on exit |
| `throw`                | Explicitly throws an exception                     |
| `throws`               | Declares exceptions from a method                  |
| `getMessage()`         | Gets exception detail message                      |
| `toString()`           | Gets exception class + message representation      |
| `printStackTrace()`    | Prints stack-trace details                         |
| Checked exception      | Compiler requires handling/declaration             |
| Unchecked exception    | RuntimeException hierarchy                         |
| Exception              | Application-level exceptional condition            |
| Error                  | Serious JVM/runtime problem                        |
| User-defined exception | Programmer-created exception type                  |

---

# 66. `throw` + `throws` Together

They can appear in the same program.

```java
class Demo
{
    static void checkAge(int age)
        throws IllegalArgumentException
    {
        if(age < 18)
        {
            throw new IllegalArgumentException(
                "Not eligible"
            );
        }

        System.out.println("Eligible");
    }

    public static void main(String[] args)
    {
        try
        {
            checkAge(15);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
```

Output:

```text
Not eligible
```

Here:

```text
throws
   ↓
declares possible exception

throw
   ↓
actually throws exception
```

---

# 67. The Most Important Mental Model

Whenever an exception occurs, think:

```text
                 EXCEPTION
                     |
                     ↓
             Exception object
                     |
                     ↓
             Where did it occur?
                     |
                     ↓
               Current method
                     |
              ┌──────┴──────┐
              ↓             ↓
          Handler?        No handler
              |               |
             Yes              ↓
              |          Caller method
              |               |
              |          Search again
              |               |
              └───────┬───────┘
                      ↓
                  Handled?
                 /       \
               Yes        No
                ↓          ↓
          Continue      Uncaught
                       termination
```

---

# 68. Final Master Program

This program brings together:

* `try`
* `catch`
* `finally`
* `throw`
* `throws`
* custom exception
* method call
* exception propagation
* `getMessage()`

```java
class InsufficientBalanceException
        extends Exception
{
    InsufficientBalanceException(String message)
    {
        super(message);
    }
}

class BankAccount
{
    private double balance;

    BankAccount(double balance)
    {
        this.balance = balance;
    }

    public void withdraw(double amount)
        throws InsufficientBalanceException
    {
        if(amount <= 0)
        {
            throw new IllegalArgumentException(
                "Amount must be greater than zero"
            );
        }

        if(amount > balance)
        {
            throw new InsufficientBalanceException(
                "Insufficient balance"
            );
        }

        balance -= amount;

        System.out.println(
            "Withdrawal successful"
        );
    }

    public double getBalance()
    {
        return balance;
    }
}

class Test
{
    public static void main(String[] args)
    {
        BankAccount account =
            new BankAccount(5000);

        try
        {
            account.withdraw(6000);
        }
        catch(InsufficientBalanceException e)
        {
            System.out.println(
                e.getMessage()
            );
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(
                e.getMessage()
            );
        }
        finally
        {
            System.out.println(
                "Final balance = "
                + account.getBalance()
            );
        }
    }
}
```

Output:

```text
Insufficient balance
Final balance = 5000.0
```

### Complete flow

```text
main()
  ↓
BankAccount object
  ↓
withdraw(6000)
  ↓
6000 > 5000
  ↓
throw InsufficientBalanceException
  ↓
method declared with throws
  ↓
Exception propagates to main()
  ↓
Matching catch found
  ↓
getMessage()
  ↓
"Insufficient balance"
  ↓
finally
  ↓
Final balance = 5000.0
```

---

# 🔥 DEEPDIVE MASTER REVISION

```text
                         EXCEPTION HANDLING
                                  |
             ┌────────────────────┼────────────────────┐
             ↓                    ↓                    ↓
          Throwable           Keywords             Handling
             |                    |                    |
       ┌─────┴─────┐        ┌─────┼─────┐        ┌─────┴─────┐
       ↓           ↓        ↓     ↓     ↓        ↓           ↓
 Exception       Error     try  catch finally   Checked    Unchecked
       |                           |
       ↓                           ↓
 RuntimeException              Multiple catch
       |                           |
       ↓                           ↓
 ArithmeticException          Nested try
 NullPointerException             |
 NumberFormatException            ↓
 ArrayIndexOutOfBoundsException  throw
                                  |
                                  ↓
                                throws
                                  |
                                  ↓
                         Exception propagation
                                  |
                                  ↓
                         User-defined exceptions
                                  |
                                  ↓
                         try-with-resources
```

## ⭐ The five rules to permanently remember

### Rule 1

```text
try → risky code
catch → handling code
finally → cleanup/finalization of the try statement
```

### Rule 2

```text
throw  → actually throws
throws → declares
```

### Rule 3

```text
Specific catch
      ↓
General catch
```

### Rule 4

```text
Checked exception
      ↓
Handle or declare
```

### Rule 5

```text
Interface/Encapsulation/Inheritance/etc.
      ≠
Exception Handling
```

Each is a separate Java concept, although they can be combined in real programs.

> **Final definition:** Exception handling in Java is the structured mechanism built around `Throwable` and constructs such as `try`, `catch`, `finally`, `throw`, and `throws` that allows programs to detect exceptional conditions, transfer control to appropriate handlers, perform necessary cleanup, propagate exceptions when necessary, and continue or terminate in a controlled manner.
