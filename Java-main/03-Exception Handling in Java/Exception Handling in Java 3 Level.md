# Exception Handling in Java — 3LEVEL

Think of **3LEVEL** as learning the same topic at three depths:

```text
LEVEL 1 → Beginner
LEVEL 2 → Intermediate
LEVEL 3 → Advanced / Interview
```

---

# 🟢 LEVEL 1 — BEGINNER

## 1. What is an Exception?

An **exception** is an abnormal condition that occurs during program execution and interrupts the normal flow of the program.

Example:

```java
class Demo
{
    public static void main(String[] args)
    {
        System.out.println("A");

        int a = 10;
        int b = 0;

        System.out.println(a / b);

        System.out.println("B");
    }
}
```

Output:

```text
A
Exception in thread "main" java.lang.ArithmeticException: / by zero
```

`B` is not printed because the exception interrupts normal execution.

---

# 2. What Is Exception Handling?

**Exception handling** is the mechanism used to handle exceptional conditions so that the program can respond in a controlled manner.

```text
Program
   ↓
Exception occurs
   ↓
Handle exception
   ↓
Controlled execution
```

---

# 3. Five Keywords

Java provides five important exception-handling keywords:

```text
try
catch
finally
throw
throws
```

### `try`

Contains risky code.

```java
try
{
    int x = 10 / 0;
}
```

### `catch`

Handles the exception.

```java
catch(ArithmeticException e)
{
    System.out.println("Cannot divide by zero");
}
```

### `finally`

Contains code that normally executes when leaving the `try`/`catch` construct.

```java
finally
{
    System.out.println("Finally");
}
```

### `throw`

Explicitly throws an exception.

```java
throw new ArithmeticException("Invalid operation");
```

### `throws`

Declares that a method may propagate an exception.

```java
void test() throws IOException
{
}
```

---

# 4. Basic `try-catch` Program

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

### Flow

```text
try
 ↓
Exception
 ↓
catch
 ↓
Handle exception
 ↓
Continue
```

---

# 5. Exception Hierarchy

```text
Object
  |
Throwable
  |
  ├── Exception
  |      |
  |      └── RuntimeException
  |
  └── Error
```

Examples:

```text
ArithmeticException
NullPointerException
NumberFormatException
ArrayIndexOutOfBoundsException
```

---

# 6. Checked and Unchecked Exceptions

### Checked

Compiler checks whether the exception is handled or declared.

Examples:

```text
IOException
FileNotFoundException
SQLException
ClassNotFoundException
```

### Unchecked

Runtime exceptions under `RuntimeException`.

Examples:

```text
ArithmeticException
NullPointerException
NumberFormatException
```

---

# 7. LEVEL 1 Memory Map

```text
Exception Handling
       |
       ├── try → risky code
       ├── catch → handling
       ├── finally → cleanup/final action
       ├── throw → explicitly throw
       └── throws → declare
```

---

# 🟡 LEVEL 2 — INTERMEDIATE

Now let's understand **how Java actually handles exceptions**.

---

# 8. Multiple `catch`

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

Java executes the matching handler.

### Important rule

Specific exceptions should come before general exceptions.

Correct:

```java
catch(ArithmeticException e)
{
}
catch(Exception e)
{
}
```

Incorrect:

```java
catch(Exception e)
{
}
catch(ArithmeticException e)
{
}
```

Because `Exception` can already catch the `ArithmeticException`.

---

# 9. `try-catch-finally`

```java
class Demo
{
    public static void main(String[] args)
    {
        try
        {
            int x = 10 / 0;
        }
        catch(ArithmeticException e)
        {
            System.out.println("Exception handled");
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
Exception handled
Finally executed
```

Flow:

```text
        try
         |
     Exception
         |
       catch
         |
      finally
         |
      Continue
```

---

# 10. Exception Object

Consider:

```java
catch(ArithmeticException e)
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

Therefore we can write:

```java
e.getMessage();
e.toString();
e.printStackTrace();
```

---

# 11. Important Exception Methods

| Method                  | Purpose                          |
| ----------------------- | -------------------------------- |
| `getMessage()`          | Returns detail message           |
| `toString()`            | Returns exception type + message |
| `printStackTrace()`     | Prints stack-trace information   |
| `getCause()`            | Returns the cause                |
| `getLocalizedMessage()` | Returns localized detail message |
| `addSuppressed()`       | Adds a suppressed exception      |
| `getSuppressed()`       | Gets suppressed exceptions       |

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
            System.out.println(e.getMessage());
            System.out.println(e.toString());
        }
    }
}
```

Typical output:

```text
/ by zero
java.lang.ArithmeticException: / by zero
```

---

# 12. `throw`

Use `throw` when **you want to explicitly create and throw an exception**.

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

Remember:

```text
throw
   ↓
Explicitly throw exception
```

---

# 13. `throws`

`throws` is written in the method declaration.

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

Remember:

```text
throw
  ↓
actually throws

throws
  ↓
declares possible propagation
```

---

# 14. `throw` vs `throws`

| `throw`                          | `throws`                                |
| -------------------------------- | --------------------------------------- |
| Keyword used to explicitly throw | Keyword used to declare                 |
| Used with an exception object    | Used with exception type(s)             |
| Used inside method/block         | Used in method declaration              |
| Causes a throw operation         | Does not itself throw                   |
| Example: `throw new Exception()` | Example: `void test() throws Exception` |

---

# 15. Exception Propagation

Suppose:

```text
main()
  ↓
method1()
  ↓
method2()
  ↓
method3()
  ↓
Exception
```

If `method3()` doesn't handle it, Java looks to its caller.

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
                "Exception handled"
            );
        }
    }
}
```

Output:

```text
Exception handled
```

This is called **exception propagation**.

---

# 16. Nested `try`

A `try` can occur inside another `try`.

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

---

# 17. User-Defined Exception

We can create our own exception.

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

# 18. Checked User-Defined Exception

Because:

```java
class AgeException extends Exception
```

the exception is checked.

If we instead use:

```java
class AgeException extends RuntimeException
```

it becomes unchecked.

---

# 19. Try-With-Resources

Used when working with resources such as files and streams.

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
            System.out.println("File error");
        }
    }
}
```

Java automatically closes the resource when the try-with-resources statement completes.

---

# 20. LEVEL 2 Tree

```text
                 Exception Handling
                         |
        ┌────────────────┼────────────────┐
        ↓                ↓                ↓
       Basic          Handling         Advanced
        |                |                |
     try/catch       multiple catch    propagation
     finally         nested try        user-defined
                                      try-resource
        |
   ┌────┴────┐
   ↓         ↓
 throw     throws
```

---

# 🔴 LEVEL 3 — ADVANCED / INTERVIEW

Now let's remove the remaining confusion.

---

# 21. Exception vs Error

Both belong to `Throwable`.

```text
Throwable
   |
   ├── Exception
   |
   └── Error
```

### Exception

Generally represents conditions that application code can reasonably handle.

### Error

Generally represents serious problems involving the JVM/runtime environment.

Examples:

```text
Exception:
IOException
ArithmeticException
NullPointerException

Error:
OutOfMemoryError
StackOverflowError
```

You normally should not treat `Error` as an ordinary application exception.

---

# 22. Checked vs Unchecked — Precise Understanding

```text
Throwable
   |
   ├── Exception
   |      |
   |      ├── checked exceptions
   |      |
   |      └── RuntimeException
   |             ↓
   |         unchecked
   |
   └── Error
```

More precisely:

> Checked exceptions are exception types other than `RuntimeException` and its subclasses, and they are subject to compile-time catch-or-declare checking.

Unchecked exceptions include:

```text
RuntimeException
and its subclasses
```

Errors are also unchecked by the compiler.

---

# 23. Why Does Java Have Checked Exceptions?

Suppose a method accesses a file.

```java
FileReader f =
    new FileReader("abc.txt");
```

The file may not exist.

Java forces the programmer to consciously deal with that possibility:

```text
Possible failure
      ↓
Compiler says:
"Handle it or declare it."
```

This is the purpose of checked-exception checking.

---

# 24. Why Doesn't Java Force `ArithmeticException` to Be Caught?

For example:

```java
int x = 10 / 0;
```

Java doesn't require:

```java
try
{
}
catch
{
}
```

because `ArithmeticException` is unchecked.

The compiler doesn't enforce catch-or-declare for it.

---

# 25. Exception Propagation — Advanced View

Suppose:

```java
static void A()
{
    B();
}

static void B()
{
    C();
}

static void C()
{
    throw new ArithmeticException("Error");
}
```

Flow:

```text
A()
 ↓
B()
 ↓
C()
 ↓
throw
```

If `C()` doesn't handle it:

```text
C()
 ↓
B()
 ↓
A()
 ↓
caller
```

Java searches each stack frame for a matching handler.

---

# 26. `finally` and Control Flow

Consider:

```java
class Demo
{
    static int test()
    {
        try
        {
            return 10;
        }
        finally
        {
            System.out.println("Finally");
        }
    }

    public static void main(String[] args)
    {
        System.out.println(test());
    }
}
```

Output:

```text
Finally
10
```

Why?

The `finally` block executes before the method actually completes its return.

---

# 27. Never Normally Use `return` in `finally`

Consider:

```java
static int test()
{
    try
    {
        return 10;
    }
    finally
    {
        return 20;
    }
}
```

The `finally` return overrides the earlier return.

Result:

```text
20
```

This is why returning from `finally` is strongly discouraged.

It can also interfere with exceptions.

---

# 28. Exception Chaining

Sometimes one exception occurs because another exception caused it.

Conceptually:

```text
High-level exception
        |
        ↓
caused by
        |
        ↓
Original exception
```

Example:

```java
try
{
    // operation
}
catch(Exception e)
{
    throw new RuntimeException(
        "Operation failed", e
    );
}
```

The second argument supplies the original cause.

Then:

```java
e.getCause()
```

can retrieve that cause.

---

# 29. Suppressed Exceptions

Try-with-resources can involve suppressed exceptions.

Conceptually:

```text
Main exception
     |
     ├── Primary exception
     |
     └── Suppressed exception
```

You can inspect suppressed exceptions with:

```java
e.getSuppressed()
```

and add one with:

```java
e.addSuppressed(otherException);
```

---

# 30. Complete Bank Example

Now combine:

* Encapsulation
* Constructor
* Validation
* `throw`
* `try`
* `catch`
* `finally`
* Exception object
* `getMessage()`

```java
class BankAccount
{
    private double balance;

    BankAccount(double balance)
    {
        if(balance < 0)
        {
            throw new IllegalArgumentException(
                "Initial balance cannot be negative"
            );
        }

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

### Complete flow

```text
BankAccount object
       ↓
balance = 5000
       ↓
withdraw(6000)
       ↓
6000 > 5000
       ↓
throw IllegalArgumentException
       ↓
catch
       ↓
getMessage()
       ↓
"Insufficient balance"
       ↓
finally
       ↓
balance remains 5000
```

---

# 31. Most Important Differences

## Exception vs Exception Handling

| Exception                      | Exception Handling            |
| ------------------------------ | ----------------------------- |
| Abnormal condition/object      | Mechanism for dealing with it |
| Interrupts normal flow         | Provides controlled response  |
| Example: `ArithmeticException` | `try-catch`                   |

---

## `final` vs `finally` vs `finalize()`

| `final`              | `finally`                  | `finalize()`             |
| -------------------- | -------------------------- | ------------------------ |
| Keyword              | Keyword                    | Method                   |
| Used for restriction | Used in exception handling | Old GC-related mechanism |
| `final int x`        | `finally {}`               | Deprecated               |

---

## `throw` vs `throws`

```text
throw  → explicitly throw
throws → declare possible propagation
```

---

# 32. Exception Handling Master Tree

```text
                         EXCEPTION HANDLING
                                  |
          ┌───────────────────────┼────────────────────────┐
          ↓                       ↓                        ↓
       Keywords                Hierarchy                Handling
          |                       |                        |
   ┌──────┼──────┐          Throwable                 try-catch
   ↓      ↓      ↓              /    \                 finally
  try   catch  finally      Exception  Error            |
   |              |             |                       |
 throw           cleanup    RuntimeException       nested try
 throws                       |                     multiple catch
                              |
                       Unchecked exceptions
                             
        Checked exceptions
              ↓
      Compiler checking
```

---

# 33. 3LEVEL Final Revision

### 🟢 Level 1 — Remember

```text
Exception
   ↓
Abnormal condition

try
   ↓
Risky code

catch
   ↓
Handle

finally
   ↓
Final/cleanup code

throw
   ↓
Explicitly throw

throws
   ↓
Declare
```

---

### 🟡 Level 2 — Understand

```text
Exception
   |
   ├── Checked
   |
   └── Unchecked
          |
      RuntimeException
```

And:

```text
try
 ↓
exception
 ↓
matching catch
 ↓
finally
 ↓
continue/exit
```

---

### 🔴 Level 3 — Master

You should now understand:

* Exception hierarchy
* `Throwable`
* `Exception`
* `RuntimeException`
* `Error`
* Checked exceptions
* Unchecked exceptions
* `try`
* `catch`
* Multiple `catch`
* Nested `try`
* `finally`
* `throw`
* `throws`
* Exception propagation
* Exception object
* `getMessage()`
* `toString()`
* `printStackTrace()`
* `getCause()`
* Suppressed exceptions
* User-defined checked exceptions
* User-defined unchecked exceptions
* Try-with-resources
* Exception chaining
* `final` vs `finally` vs `finalize()`
* Exception handling with encapsulated classes
* Control flow involving `finally`

### One final memory formula:

```text
             EXCEPTION HANDLING
                     |
       ┌─────────────┼─────────────┐
       ↓             ↓             ↓
      TRY           CATCH        FINALLY
   risky code      handle       cleanup
                     |
                 ┌───┴───┐
                 ↓       ↓
              THROW    THROWS
              throw    declare
```

**If you understand this tree, you understand the foundation of Exception Handling in Java.**
