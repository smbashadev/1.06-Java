Data Types in Java — DEEP DIVE

A data type tells Java what kind of data a variable can hold and what operations can be performed on that data.

The first thing to understand is:

Java Data Types
                               │
                  ┌────────────┴────────────┐
                  ▼                         ▼
             Primitive                  Reference
               Types                      Types
                  │                         │
       ┌──────────┼──────────┐       ┌─────┼─────┐
       ▼          ▼          ▼       ▼     ▼     ▼
    Integer     Floating   Character  Class Array Interface
    Types       Types
       │           │
 byte short int long float double
                    │
                  char
                    │
                 boolean


---

1. What exactly is a data type?

Consider:

int age = 20;

There are three important pieces:

int     age     = 20
 │       │       │
Type   Variable Value

int tells Java:

> "age is intended to hold an integer value."



Another example:

double price = 99.50;

Here:

double → type
price  → variable
99.50  → value


---

2. Why do we need data types?

Java is a statically typed language.

That means the type of a variable is known/checked at compile time.

For example:

int age = 20;

You cannot normally assign a String to that int variable:

age = "Twenty";   // compilation error

Why?

Because:

int ≠ String

The compiler catches many such type errors before the program runs.


---

3. Two Major Categories

Java data types are broadly classified as:

Java Data Types
      │
 ┌────┴────┐
 ▼         ▼
Primitive  Reference

Primitive

There are exactly 8 primitive types:

byte
short
int
long
float
double
char
boolean

Reference

Examples include:

String
Arrays
Classes
Objects
Interfaces
Enums


---

4. Primitive Data Types

Primitive types are the basic data types built into the Java language.

They can be grouped as:

Primitive Types
│
├── Integral
│   ├── byte
│   ├── short
│   ├── int
│   ├── long
│   └── char
│
├── Floating-point
│   ├── float
│   └── double
│
└── boolean

⚠️ Important: char is an integral type in Java's type system, even though we usually teach it separately as the character type.


---

5. byte

byte age = 25;

byte is an 8-bit signed integer.

Range:

-128 to 127

Why?

An 8-bit signed two's-complement integer has:

-2⁷ to 2⁷ - 1
= -128 to 127

When useful?

When you deliberately need a small integer range or when working with binary data.


---

6. short

short marks = 300;

short is a 16-bit signed integer.

Range:

-32,768 to 32,767

Formula:

-2¹⁵ to 2¹⁵ - 1

In ordinary application code, int is usually more common.


---

7. int

int population = 100000;

int is a 32-bit signed integer.

Range:

-2,147,483,648
to
2,147,483,647

Formula:

-2³¹ to 2³¹ - 1

Why is int important?

It is Java's standard/default integer type for most integer literals and ordinary integer calculations.

Example:

int x = 10;
int y = 20;

int sum = x + y;


---

8. long

long population = 8000000000L;

long is a 64-bit signed integer.

Range:

-2⁶³ to 2⁶³ - 1

or approximately:

-9.22 × 10¹⁸
to
+9.22 × 10¹⁸

Why L?

An integer literal such as:

8000000000

is not an int because it exceeds the int range.

Write:

long x = 8000000000L;

L tells Java to treat the literal as a long.


---

9. Integer Type Summary

byte    → 8-bit
short   → 16-bit
int     → 32-bit
long    → 64-bit

Think:

> 8 → 16 → 32 → 64




---

10. float

float represents single-precision floating-point values.

Example:

float temperature = 36.5f;

Why f?

A decimal floating-point literal such as:

36.5

is a double by default.

So this:

float x = 36.5;

causes a type error.

Use:

float x = 36.5f;


---

11. double

double represents double-precision floating-point values.

Example:

double price = 99.99;

Decimal literals are double by default.

Therefore:

double x = 10.5;

is valid.

Why is double commonly preferred?

It provides more precision than float and is generally the normal choice for ordinary floating-point calculations.


---

12. Float vs Double

float  → 32-bit
double → 64-bit

But don't think:

> "64 bits means every decimal has exactly twice the accuracy."



Floating-point representation is more complicated.

For most beginner purposes:

> double provides greater precision/range than float.




---

13. Floating-Point Warning ⚠️

Floating-point numbers are approximations, not arbitrary exact decimal fractions.

For example:

double x = 0.1;
double y = 0.2;

System.out.println(x + y);

You may encounter:

0.30000000000000004

Why?

Because binary floating-point cannot represent many decimal fractions exactly.

So for applications requiring exact decimal arithmetic, such as certain financial calculations, BigDecimal is often more appropriate than float or double.


---

14. char

char stores a single 16-bit Unicode code unit.

Example:

char grade = 'A';

Important:

'A' → char
"A" → String

So:

char c = 'A';       // correct
String s = "A";     // correct

But:

char c = "A";       // wrong


---

15. Why is char 16-bit?

Java's char is based on a UTF-16 code unit.

This leads to an important advanced point:

> A Java char is not necessarily a complete Unicode character/code point.



Many Unicode characters fit in one char, but supplementary characters use a surrogate pair—two char values.

So:

char ≠ always one complete Unicode character

For full Unicode code points, Java provides APIs such as:

String.codePointAt(...)


---

16. boolean

boolean represents a logical value:

true
false

Example:

boolean isJavaEasy = true;

Only these two values are valid:

true
false

Unlike C/C++, Java does not treat 0 and 1 as boolean values.

This is invalid:

boolean x = 1;   // error


---

17. Does boolean have a fixed size?

This is a common exam trap.

Java specifies the type and its behavior, but does not define a simple 8/16/32/64-bit storage size for boolean in the same way it does for numeric primitive types.

So don't blindly write:

> boolean = 1 byte



as a universal Java language rule.


---

18. The 8 Primitive Types — Complete Table

Type	Category	Size	Typical range / values

byte	Integer	8-bit	-128 to 127
short	Integer	16-bit	-32,768 to 32,767
int	Integer	32-bit	-2³¹ to 2³¹−1
long	Integer	64-bit	-2⁶³ to 2⁶³−1
float	Floating point	32-bit	Approx. ±3.4 × 10³⁸
double	Floating point	64-bit	Approx. ±1.8 × 10³⁰⁸
char	Character / integral	16-bit	\u0000 to \uFFFF
boolean	Logical	JVM-dependent	true / false



---

19. Reference Data Types

Now we come to the second major category.

Reference Types

A reference variable holds a reference to an object, rather than directly being one of the eight primitive values.

Examples:

String name = "Rahul";

int[] numbers = {10, 20, 30};

Student student = new Student();

Here:

name     → reference
numbers  → reference
student  → reference


---

20. String

String is a class, so it is a reference type, not a primitive type.

Example:

String name = "Java";

Many beginners make this mistake:

String = primitive ❌
String = reference type ✅

There is no primitive string type in Java.


---

21. Arrays

Arrays are also reference types.

Example:

int[] numbers = {10, 20, 30};

The elements are int, but the array itself is an object/reference type.

This distinction is important:

int      → primitive
int[]    → reference type


---

22. Classes and Objects

Suppose:

class Student {
    int age;
}

Then:

Student s = new Student();

Student is a reference type.

Conceptually:

Stack/reference variable       Heap
─────────────────────          ─────────────
s ───────────────────────────→ Student object

The exact memory layout is JVM-implementation-dependent, but this is a useful conceptual model.


---

23. Interfaces

An interface is also a reference type.

Example:

interface Animal {
    void sound();
}

A variable can have an interface type:

Animal a = new Dog();

The variable a is a reference variable whose declared type is Animal.


---

24. Enum

Enums are also reference types.

Example:

enum Day {
    MONDAY, TUESDAY, WEDNESDAY
}

Then:

Day today = Day.MONDAY;

Day is not a primitive type.


---

25. Primitive vs Reference — The Core Difference

Consider:

int x = 10;
String s = "Java";

Conceptually:

x
↓
10

s
↓
reference ─────→ String object

So:

> Primitive variable → contains a primitive value



> Reference variable → contains a reference to an object




---

26. Can primitive variables contain null?

❌ No.

This is invalid:

int x = null;

But a reference variable can generally be null:

String name = null;

Meaning:

> name currently refers to no object.




---

27. Wrapper Classes

Java provides object wrappers for primitive types.

byte    → Byte
short   → Short
int     → Integer
long    → Long
float   → Float
double  → Double
char    → Character
boolean → Boolean

Example:

Integer x = 10;

Here Integer is a reference type.

This is useful when APIs require objects rather than primitives.


---

28. Autoboxing

Java can automatically convert between a primitive and its wrapper in many contexts.

int x = 10;

Integer y = x;

This is called autoboxing.

Reverse:

Integer y = 10;

int x = y;

This is unboxing.

Conceptually:

int → Integer     Autoboxing
Integer → int     Unboxing


---

29. Default Values

Instance variables and array elements receive default values when created.

For example:

class Test {
    int x;
    boolean flag;
    String name;
}

Their default values are conceptually:

int     → 0
boolean → false
reference → null

For primitive types generally:

byte/short/int/long → 0
float/double        → 0.0
char                → '\u0000'
boolean             → false
reference           → null

⚠️ Important

Local variables do not automatically receive these defaults.

This is invalid:

void test() {
    int x;
    System.out.println(x); // compilation error
}

You must initialize x before using it.


---

30. Type Conversion

Java supports conversion between compatible numeric types.

Example:

int x = 10;
long y = x;

This is safe widening conversion:

int → long

But:

long x = 10;
int y = x;

requires explicit narrowing:

int y = (int) x;


---

31. Widening Conversion

Generally:

byte
  ↓
short
  ↓
int
  ↓
long
  ↓
float
  ↓
double

But don't interpret this as "every conversion is perfectly accurate." For example, converting long to float can lose integer precision because floating-point representation is approximate.

Example:

int x = 100;
double y = x;

No explicit cast is required.


---

32. Narrowing Conversion

Going in the opposite direction generally requires an explicit cast.

double d = 10.5;
int x = (int) d;

Result:

10

The fractional part is discarded.

So:

double → int

can lose information.


---

33. final Does NOT Mean Data Type

Another common doubt:

final int MAX = 100;

Here:

final → modifier
int   → data type
MAX   → variable
100   → value

So final is not a data type.


---

34. String Is NOT a Primitive

Remember this forever:

byte       primitive
short      primitive
int        primitive
long       primitive
float      primitive
double     primitive
char       primitive
boolean    primitive

String     reference type


---

35. Integer vs int

Very important:

int a = 10;
Integer b = 10;

int     → primitive
Integer → reference type / wrapper class

They are not the same type.


---

🧠 Final Mental Map

JAVA DATA TYPES
                           │
              ┌────────────┴────────────┐
              ▼                         ▼
         PRIMITIVE                  REFERENCE
              │                         │
      ┌───────┼────────┐         ┌──────┼───────┐
      ▼       ▼        ▼         ▼      ▼       ▼
   Integer  Floating  Other    String  Array   Class
      │       point
      │         │
 byte short   float
 int   long   double
              │
             char
              │
           boolean


---

🔥 DEEP-DIVE EXAM TRAPS

❌ String is primitive

✅ String is a reference type.


---

❌ Integer and int are the same

✅ int is primitive; Integer is a wrapper/reference type.


---

❌ JVM defines every primitive as exactly 1/2/4/8 bytes

✅ Java precisely specifies widths for the numeric primitive types, but boolean has no fixed language-level storage size.


---

❌ char is 8-bit

✅ Java char is 16-bit.


---

❌ char can hold every Unicode character in one variable

✅ A char is a UTF-16 code unit; some Unicode code points require two chars.


---

❌ Decimal literals are float by default

✅ Decimal floating-point literals are double by default.

float x = 10.5f;


---

❌ long always needs L

✅ Only literals that need to be treated as long require the suffix; a variable declared long can receive an int literal if it fits.

long x = 10;     // valid
long y = 10L;    // valid


---

🎯 Final Summary

Primitive — 8

byte
short
int
long
float
double
char
boolean

Reference

String
Array
Class
Object
Interface
Enum
...

Most important facts

> Java has 8 primitive data types.



> String is a reference type, not a primitive.



> int is the usual integer type; double is the usual floating-point type.



> char is a 16-bit UTF-16 code unit.



> Reference variables can generally be null; primitive variables cannot.



> Java is statically typed, so many type errors are detected at compile time.
