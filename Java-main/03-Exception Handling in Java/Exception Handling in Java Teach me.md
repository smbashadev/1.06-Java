# Exception Handling in Java — TEACHME

Let’s learn **Exception Handling in Java from zero**, as if we are sitting in a classroom. I’ll build the concept step by step, then connect all the keywords, types, methods, programs, outputs, diagrams, and important differences.

---

# 1. First Understand the Problem

Imagine this program:

```java
class Demo
{
    public static void main(String[] args)
    {
        System.out.println("A");

        int a = 10;
        int b = 0;

        int c = a / b;

        System.out.println("B");
    }
}
```

What do you think happens?

We know:

```text
10 / 0
```

is not possible.

So Java produces:

```text
ArithmeticException
```

Output is similar to:

```text
A
Exception in thread "main" java.lang.ArithmeticException: / by zero
```

Notice something important:

```java
System.out.println("B");
```

doesn't execute.

Why?

Because the normal flow of the program was interrupted.

---

# 2. What Is an Exception?

An **exception** is an abnormal condition that occurs during program execution and interrupts the normal flow of the program.

Think:

```text
Normal program
      ↓
Statement
      ↓
Statement
      ↓
Statement
      ↓
Exception ❌
      ↓
Normal flow interrupted
```

Examples:

```text
10 / 0
       → ArithmeticException

array[10] when array length is 3
       → ArrayIndexOutOfBoundsException

nullObject.method()
       → NullPointerException

Integer.parseInt("ABC")
       → NumberFormatException
```

---

# 3. Then What Is Exception Handling?

Suppose your friend falls while walking.

You don't stop living.

You help your friend and continue.

Exception handling works similarly.

```text
Exception occurs
      ↓
Detect it
      ↓
Handle it
      ↓
Perform appropriate action
      ↓
Continue/terminate in a controlled way
```

### Definition

> **Exception handling is the mechanism used to detect and handle exceptional conditions during program execution in a controlled manner.**

---

# 4. Why Do We Need Exception Handling?

Without handling:

```text
Program
   ↓
Exception
   ↓
Abnormal termination
```

With handling:

```text
Program
   ↓
Exception
   ↓
Handler
   ↓
Appropriate action
   ↓
Program continues where applicable
```

### Example

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

        System.out.println("Program continues");
    }
}
```

Output:

```text
Cannot divide by zero
Program continues
```

That's the basic idea of exception handling.

---

# 5. The Five Important Keywords

Java gives us five important keywords:

```text
        Exception Handling
               |
     ┌─────────┼─────────┐
     ↓         ↓         ↓
    try      catch     finally
                |
             ┌──┴──┐
             ↓     ↓
           throw  throws
```

Remember:

| Keyword   | Simple meaning                                         |
| --------- | ------------------------------------------------------ |
| `try`     | Put risky code here                                    |
| `catch`   | Handle the exception                                   |
| `finally` | Code that normally executes when leaving the construct |
| `throw`   | Explicitly throw an exception                          |
| `throws`  | Declare possible exceptions from a method              |

---

# 6. First Learn `try`

Suppose you have dangerous/risky code:

```java
int c = a / b;
```

You can place it inside `try`:

```java
try
{
    int c = a / b;
}
```

But a `try` cannot stand alone.

It needs either:

```text
try + catch
```

or:

```text
try + finally
```

---

# 7. Now Learn `catch`

`catch` handles an exception.

```java
try
{
    int c = 10 / 0;
}
catch(ArithmeticException e)
{
    System.out.println("Cannot divide by zero");
}
```

Think of it like:

```text
try
 ↓
Something went wrong
 ↓
Which exception?
 ↓
ArithmeticException
 ↓
Find matching catch
 ↓
Execute catch
```

Output:

```text
Cannot divide by zero
```

---

# 8. What Is `e`?

Look at:

```java
catch(ArithmeticException e)
```

Two things are present:

```text
ArithmeticException
        ↓
Exception type

e
↓
Reference variable
```

When the exception occurs, Java creates an exception object.

The reference `e` refers to that object.

That's why we can write:

```java
e.getMessage();
```

or:

```java
e.printStackTrace();
```

---

# 9. Let's Understand the Complete Flow

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

Why not `B`?

Because:

```text
A
 ↓
10 / 0
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

This is a **very important rule**.

> Once an exception occurs in a `try` block, normal execution of the remaining statements in that `try` block is interrupted.

---

# 10. Now Learn `finally`

Suppose you want something to execute when leaving the `try`/`catch` structure.

Use:

```java
finally
```

Example:

```java
class Demo
{
    public static void main(String[] args)
    {
        try
        {
            int a = 10 / 2;
            System.out.println(a);
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

# 11. Why Do We Use `finally`?

Historically, `finally` is commonly used for cleanup.

For example:

```text
open resource
     ↓
use resource
     ↓
close resource
```

Modern Java provides **try-with-resources**, which is generally better for resources such as files.

---

# 12. Complete `try-catch-finally`

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
            System.out.println("Division by zero");
        }
        finally
        {
            System.out.println("Process completed");
        }
    }
}
```

Output:

```text
Division by zero
Process completed
```

Flow:

```text
try
 ↓
Exception
 ↓
catch
 ↓
finally
 ↓
Continue/exit
```

---

# 13. Multiple `catch`

What if a program can produce different exceptions?

We can have multiple `catch` blocks.

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

Why?

Because the actual exception is:

```text
ArrayIndexOutOfBoundsException
```

So Java chooses the matching `catch`.

---

# 14. Catch Order — Very Important

Suppose:

```java
catch(Exception e)
{
}
catch(ArithmeticException e)
{
}
```

This is wrong because:

```text
Exception
    ↑
ArithmeticException
```

`Exception` is more general.

If it comes first, the later `ArithmeticException` handler becomes unreachable.

Correct:

```java
catch(ArithmeticException e)
{
}
catch(Exception e)
{
}
```

### Golden rule

> **Specific exception first, general exception later.**

```text
Specific
   ↓
General
```

---

# 15. Exception Hierarchy

Now let's understand where these exceptions come from.

```text
                         Object
                            |
                        Throwable
                       /         \
                      /           \
               Exception          Error
                   |
            RuntimeException
             /      |       \
            ↓       ↓        ↓
     Arithmetic   NullPointer  NumberFormat
     Exception    Exception    Exception
```

`Throwable` has two major branches:

```text
Throwable
   |
   ├── Exception
   |
   └── Error
```

---

# 16. Exception vs Error

Don't confuse these.

### Exception

Usually represents an exceptional condition that application code may handle.

Examples:

```text
IOException
SQLException
ArithmeticException
NullPointerException
```

### Error

Usually represents serious runtime/JVM problems.

Examples:

```text
OutOfMemoryError
StackOverflowError
```

Simple memory trick:

```text
Exception → application-level exceptional condition
Error     → serious JVM/runtime problem
```

---

# 17. Checked and Unchecked Exceptions

This is another very important classification.

```text
Exception
   |
   ├── Checked Exceptions
   |
   └── RuntimeException
          ↓
     Unchecked Exceptions
```

---

# 18. Checked Exception

A checked exception is checked by the compiler.

Example:

```java
import java.io.*;

class Demo
{
    public static void main(String[] args)
    {
        try
        {
            FileReader f =
                new FileReader("abc.txt");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }
}
```

Output if the file does not exist:

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

The important idea:

> The compiler requires checked exceptions to be handled or declared.

---

# 19. Unchecked Exception

Unchecked exceptions belong to the `RuntimeException` hierarchy.

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

The program compiles, but fails when executed.

Common examples:

```text
ArithmeticException
NullPointerException
NumberFormatException
ArrayIndexOutOfBoundsException
ClassCastException
IllegalArgumentException
```

---

# 20. Checked vs Unchecked — Easy Table

| Checked                                                          | Unchecked                                      |
| ---------------------------------------------------------------- | ---------------------------------------------- |
| Compiler checks handling/declaration                             | Compiler doesn't require catch/declare         |
| Generally outside normal program control                         | Often caused by runtime/programming conditions |
| Usually under `Exception` excluding `RuntimeException` hierarchy | Under `RuntimeException`                       |
| Example: `IOException`                                           | Example: `ArithmeticException`                 |

---

# 21. Now Learn `throw`

Suppose Java doesn't automatically know your business rule.

For example:

```text
Age = 15
Minimum age = 18
```

You want to explicitly say:

> This situation is invalid.

Use `throw`.

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

# 22. Understand `throw` in One Sentence

> **`throw` is used to explicitly throw an exception object.**

Syntax:

```java
throw new ExceptionType("message");
```

---

# 23. Now Learn `throws`

Suppose a method may produce an exception.

We can declare it:

```java
void readFile() throws IOException
```

Example:

```java
import java.io.*;

class Demo
{
    static void readFile() throws IOException
    {
        FileReader f =
            new FileReader("abc.txt");
    }

    public static void main(String[] args)
        throws IOException
    {
        readFile();
    }
}
```

Here:

```text
throws
   ↓
declares possible exception
```

It does not itself throw the exception.

---

# 24. `throw` vs `throws`

This is a favorite exam/interview question.

| `throw`                         | `throws`                             |
| ------------------------------- | ------------------------------------ |
| Explicitly throws an exception  | Declares possible exception(s)       |
| Used inside method/block        | Used in method declaration           |
| Followed by an exception object | Followed by exception type(s)        |
| Actually initiates throwing     | Does not itself throw                |
| Usually one object at a time    | Can declare multiple exception types |

Remember:

```text
throw
  ↓
DO

throws
  ↓
DECLARE
```

---

# 25. Exception Propagation

Now imagine methods calling methods.

```text
main()
  ↓
method1()
  ↓
method2()
  ↓
method3()
```

Suppose the exception occurs in `method3()`.

If `method3()` doesn't handle it:

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
class Demo
{
    static void method3()
    {
        int x = 10 / 0;
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
            System.out.println(
                "Exception handled in main"
            );
        }
    }
}
```

Output:

```text
Exception handled in main
```

---

# 26. Visualize Exception Propagation

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
method3 handler?
  |
  └── No
       ↓
method2 handler?
  |
  └── No
       ↓
method1 handler?
  |
  └── No
       ↓
main handler?
  |
  └── Yes
       ↓
Exception handled
```

This movement upward is called **exception propagation**.

---

# 27. Nested `try`

A `try` can be inside another `try`.

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
                int x = 10 / 0;
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

The inner handler handles the exception.

---

# 28. Exception Methods

Now let's learn what we can do with the exception object.

Suppose:

```java
catch(Exception e)
```

We can call methods on `e`.

The important ones are:

```text
getMessage()
toString()
printStackTrace()
getCause()
getLocalizedMessage()
getSuppressed()
addSuppressed()
```

---

# 29. `getMessage()`

Returns the detail message.

Example:

```java
class Demo
{
    public static void main(String[] args)
    {
        try
        {
            int x = 10 / 0;
        }
        catch(Exception e)
        {
            System.out.println(
                e.getMessage()
            );
        }
    }
}
```

Output:

```text
/ by zero
```

---

# 30. `toString()`

Returns a textual representation containing the exception class and message.

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

# 31. `printStackTrace()`

This is extremely useful during debugging.

```java
catch(Exception e)
{
    e.printStackTrace();
}
```

It displays information such as:

```text
Exception type
Message
Class
Method
Line number
Call sequence
```

Typical output:

```text
java.lang.ArithmeticException: / by zero
    at Demo.main(Demo.java:...)
```

Exact formatting varies by runtime/version.

---

# 32. Compare the Three

| Method              | What it gives            |
| ------------------- | ------------------------ |
| `getMessage()`      | Message                  |
| `toString()`        | Exception type + message |
| `printStackTrace()` | Detailed stack trace     |

Think:

```text
getMessage()
     ↓
"What happened?"

toString()
     ↓
"What exception + what message?"

printStackTrace()
     ↓
"Where did it happen?"
```

---

# 33. User-Defined Exception

Sometimes Java's built-in exception types aren't expressive enough.

Suppose our application needs:

```text
AgeException
```

We can create it.

```java
class AgeException extends Exception
{
    AgeException(String message)
    {
        super(message);
    }
}
```

Use it:

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
            System.out.println(
                e.getMessage()
            );
        }
    }
}
```

Output:

```text
Age must be 18 or above
```

---

# 34. Why `super(message)`?

Look at:

```java
AgeException(String message)
{
    super(message);
}
```

The parent is:

```text
AgeException
     ↓
Exception
     ↓
Throwable
```

We pass the message to the parent so that:

```java
e.getMessage()
```

can return it.

---

# 35. Checked User-Defined Exception

When we write:

```java
class AgeException extends Exception
```

we create a checked exception.

Therefore it must be handled or declared when required.

---

# 36. Unchecked User-Defined Exception

If we write:

```java
class AgeException extends RuntimeException
{
    AgeException(String message)
    {
        super(message);
    }
}
```

it becomes an unchecked exception.

Remember:

```text
extends Exception
      ↓
Checked

extends RuntimeException
      ↓
Unchecked
```

---

# 37. Try-With-Resources

Now imagine a file.

Traditional concept:

```text
Open file
   ↓
Use file
   ↓
Close file
```

Java provides:

```java
try(resource)
{
}
```

Example:

```java
import java.io.*;

class Demo
{
    public static void main(String[] args)
    {
        try(FileReader f =
                new FileReader("abc.txt"))
        {
            System.out.println("File opened");
        }
        catch(IOException e)
        {
            System.out.println(
                "File operation failed"
            );
        }
    }
}
```

Java automatically closes the resource when leaving the try-with-resources statement.

---

# 38. Why Try-With-Resources?

Instead of manually worrying about:

```text
open
 ↓
use
 ↓
finally
 ↓
close
```

we can use:

```text
try(resource)
      ↓
    use
      ↓
automatic closing
```

This is particularly useful for files, streams, sockets, database resources, and other `AutoCloseable` resources.

---

# 39. `final`, `finally`, `finalize()`

Students often confuse these.

### `final`

Keyword:

```java
final int x = 10;
```

### `finally`

Exception-handling block:

```java
finally
{
    System.out.println("Cleanup");
}
```

### `finalize()`

An old garbage-collection-related method that is deprecated and should not be relied upon.

So:

```text
final     → keyword
finally   → exception block
finalize  → deprecated old mechanism
```

---

# 40. Common Exceptions — Learn With Examples

## 1. ArithmeticException

```java
int x = 10 / 0;
```

---

## 2. NullPointerException

```java
String s = null;

System.out.println(s.length());
```

---

## 3. ArrayIndexOutOfBoundsException

```java
int[] a = {10, 20};

System.out.println(a[5]);
```

---

## 4. NumberFormatException

```java
int x = Integer.parseInt("ABC");
```

---

## 5. ClassCastException

Occurs when an object is cast to an incompatible type.

---

## 6. IllegalArgumentException

Used when an argument supplied to a method is inappropriate.

---

# 41. Let's Combine Everything

Now we'll create a realistic **Bank Account** example.

This is useful because it connects exception handling with the Encapsulation concept you already studied.

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
        catch(IllegalArgumentException e)
        {
            System.out.println(
                e.getMessage()
            );
        }
        finally
        {
            System.out.println(
                "Balance = " +
                account.getBalance()
            );
        }
    }
}
```

Output:

```text
Insufficient balance
Balance = 5000.0
```

---

# 42. Understand This Program Like a Story

### Step 1

Object created:

```java
BankAccount account =
    new BankAccount(5000);
```

Balance:

```text
5000
```

### Step 2

We request:

```java
account.withdraw(6000);
```

### Step 3

Java checks:

```text
6000 > 5000
```

True.

### Step 4

Program explicitly throws:

```java
throw new IllegalArgumentException(
    "Insufficient balance"
);
```

### Step 5

Exception reaches `main()`.

### Step 6

Matching catch executes:

```java
catch(IllegalArgumentException e)
```

### Step 7

Message is printed.

### Step 8

`finally` executes.

Final result:

```text
Insufficient balance
Balance = 5000.0
```

---

# 43. Exception Handling + Encapsulation

Notice something beautiful here.

### Encapsulation:

```text
private balance
      ↓
Outside code cannot directly modify it
```

### Exception handling:

```text
Invalid operation
      ↓
Exception
      ↓
Controlled handling
```

Together:

```text
             BankAccount
                  |
        ┌─────────┴─────────┐
        ↓                   ↓
   Encapsulation       Exception Handling
        ↓                   ↓
 private balance       invalid withdrawal
        ↓                   ↓
 controlled access    controlled response
```

They are different concepts, but they work together very well.

---

# 44. Important Questions Students Ask

## Can `try` exist without `catch`?

Yes, if it has `finally`.

```java
try
{
}
finally
{
}
```

---

## Can `catch` exist without `try`?

No.

---

## Can we have multiple catches?

Yes.

---

## Can we have multiple `finally` blocks for one try statement?

No.

---

## Can we have nested `try`?

Yes.

---

## Can `try` be inside `catch`?

Yes.

---

## Can `try` be inside `finally`?

Yes.

---

# 45. What Happens If No Catch Matches?

Suppose:

```java
try
{
    int x = 10 / 0;
}
catch(NullPointerException e)
{
    System.out.println("Null");
}
```

The exception is:

```text
ArithmeticException
```

but the catch expects:

```text
NullPointerException
```

No match.

So the exception propagates to the caller.

If nobody handles it, the program terminates with an uncaught exception.

---

# 46. The Complete Mental Picture

Imagine Java as a security system.

```text
                 Program
                    |
                    ↓
               Risky code
                    |
                    ↓
                  try
                    |
             Something happens
                    |
             ┌──────┴──────┐
             ↓             ↓
          Normal       Exception
             ↓             ↓
         Continue      Find handler
                           |
                     ┌─────┴─────┐
                     ↓           ↓
                   Found       Not found
                     ↓           ↓
                  catch      Propagate
                     ↓           ↓
                  finally   Caller method
                     |
                     ↓
                 Continue
```

That's exception handling.

---

# 47. Complete Exception Tree

```text
                         Object
                            |
                         Throwable
                            |
              ┌─────────────┴─────────────┐
              ↓                           ↓
         Exception                       Error
              |
       ┌──────┴──────────┐
       ↓                 ↓
   Checked          RuntimeException
   Exceptions             |
                          ├── ArithmeticException
                          ├── NullPointerException
                          ├── NumberFormatException
                          ├── ArrayIndexOutOfBoundsException
                          ├── ClassCastException
                          └── IllegalArgumentException
```

---

# 48. Complete Keyword Tree

```text
                Exception Handling
                        |
        ┌───────────────┼───────────────┐
        ↓               ↓               ↓
       try            catch           finally
                        |
                   ┌────┴────┐
                   ↓         ↓
                 throw     throws
```

---

# 49. Complete Exception Flow

```text
                     Program
                        |
                        ↓
                   Risky code
                        |
                        ↓
                       try
                        |
                  Exception?
                  /        \
                No          Yes
                |            |
                ↓            ↓
             Continue    Exception object
                             |
                             ↓
                       Find matching catch
                             |
                    ┌────────┴────────┐
                    ↓                 ↓
                  Found            Not found
                    ↓                 ↓
                 catch           Propagation
                    |                 |
                    └────────┬────────┘
                             ↓
                          finally
                             |
                             ↓
                      Continue / exit
```

---

# 50. Final Teacher Summary

If I ask you in an exam:

### What is an exception?

Say:

> An exception is an abnormal condition that occurs during program execution and disrupts the normal flow of the program.

### What is exception handling?

Say:

> Exception handling is the mechanism used to detect and handle exceptional conditions in a controlled manner.

### Five keywords?

```text
try
catch
finally
throw
throws
```

### What is `try`?

> Contains code that may cause an exception.

### What is `catch`?

> Handles a matching exception.

### What is `finally`?

> Contains code intended to execute when leaving the try/catch construct, subject to abnormal JVM termination.

### What is `throw`?

> Explicitly throws an exception object.

### What is `throws`?

> Declares exceptions that a method may propagate to its caller.

### Checked exception?

> An exception subject to compile-time catch-or-declare checking.

### Unchecked exception?

> An exception in the `RuntimeException` hierarchy that is not subject to compile-time catch-or-declare checking.

### Exception hierarchy?

```text
Throwable
   |
   ├── Exception
   |      |
   |   RuntimeException
   |
   └── Error
```

### Most important difference?

```text
throw
  ↓
actually throws

throws
  ↓
declares
```

And the most important flow to remember is:

```text
try
 ↓
Exception occurs
 ↓
Matching catch
 ↓
Handle
 ↓
finally
 ↓
Continue / terminate in a controlled way
```

That is the **core of Exception Handling in Java**.
