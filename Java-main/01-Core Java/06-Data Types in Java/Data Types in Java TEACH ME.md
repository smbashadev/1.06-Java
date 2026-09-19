Data Types in Java — TEACH ME 🎓

Let's learn Data Types in Java from zero, step by step. By the end, you should be able to look at a variable and immediately tell what type it is, what it stores, and why that type is used.


---

1. First: What is a Data Type?

Imagine you have boxes 📦.

One box is for whole numbers:

┌─────────────┐
│     25      │
└─────────────┘

Another box is for characters:

┌─────────────┐
│      A      │
└─────────────┘

Another is for true/false:

┌─────────────┐
│    true     │
└─────────────┘

In Java, the data type tells Java what kind of data a variable is meant to hold.

Example:

int age = 25;

Here:

int  → data type
age  → variable
25   → value

So:

> Data type = tells Java what kind of data a variable represents.




---

2. Why Do We Need Data Types?

Suppose we write:

age = 25;

Java needs to know:

Is 25 an integer?

A decimal?

A character?

Text?

True/false?


So we specify:

int age = 25;

Now Java knows:

> age is an integer variable.



If you try:

int age = "twenty-five";

Java gives a compile-time error, because "twenty-five" is text, not an int.


---

3. Two Main Categories

Java data types are divided into two major categories:

DATA TYPES
                       │
             ┌─────────┴─────────┐
             ▼                   ▼
        PRIMITIVE            REFERENCE
          TYPES                 TYPES

Let's understand them separately.


---

🟢 PART 1: PRIMITIVE DATA TYPES

Java has exactly 8 primitive data types.

byte
short
int
long
float
double
char
boolean

Let's learn them one by one.


---

4. byte

byte is used for small whole numbers.

byte age = 20;

A byte is 8-bit signed and can store:

-128 to 127

Think:

> byte → very small integer



Example:

byte temperature = 30;


---

5. short

short is also an integer type, but larger than byte.

short marks = 500;

It is 16-bit signed.

Range:

-32,768 to 32,767

Think:

byte → smaller
short → bigger


---

6. int ⭐

This is one of the most important types in Java.

int is used for normal whole numbers.

int age = 21;
int marks = 95;
int salary = 50000;

It is a 32-bit signed integer.

Range:

-2,147,483,648
to
 2,147,483,647

For ordinary integer calculations, int is usually the type you'll use most often.


---

7. long

What if the number is too large for int?

Use long.

long population = 8000000000L;

long is a 64-bit signed integer.

The L is important when the literal itself needs to be treated as a long.

long x = 100L;

Think:

byte → small
short → bigger
int → bigger
long → very large


---

8. Integer Types Together

Now put them together:

INTEGER TYPES

byte   → 8-bit
short  → 16-bit
int    → 32-bit
long   → 64-bit

Easy memory:

> 8 → 16 → 32 → 64




---

9. float

Now let's talk about decimal numbers.

float price = 25.5f;

float stores single-precision floating-point values.

Notice the f:

25.5f

Why?

Because a decimal floating-point literal such as:

25.5

is normally a double literal.

Therefore:

float x = 25.5;   // ❌

Use:

float x = 25.5f;  // ✅


---

10. double ⭐

double is another floating-point type.

double price = 99.99;

It provides greater precision than float and is generally the normal choice for floating-point calculations.

Notice:

double x = 10.5;

No d is required because decimal floating-point literals are double by default.


---

11. Float vs Double

Remember:

float  → 32-bit
double → 64-bit

Example:

float f = 10.5f;
double d = 10.5;

Simple rule

> Use double when you need an ordinary decimal floating-point value unless you have a reason to use float.




---

12. char

Now we need a type for a single character.

Use char.

char grade = 'A';

Notice the single quotes:

'A'

Compare:

'A'       → char
"Java"    → String

So:

char c = 'A';      // ✅
String s = "A";    // ✅

But:

char c = "A";      // ❌


---

13. Important char Fact

Java's char is 16-bit and represents a UTF-16 code unit.

For beginner-level understanding:

> char is used for individual characters.



Example:

char letter = 'J';
char digit = '5';
char symbol = '$';

Advanced point: some Unicode characters require two UTF-16 code units, so one complete Unicode code point isn't always represented by one Java char.


---

14. boolean

Now we need a type for questions that have two answers:

YES / NO
TRUE / FALSE

Java uses:

boolean

Example:

boolean isStudent = true;
boolean isPassed = false;

A boolean has only:

true
false

This is invalid:

boolean x = 1;    // ❌

Java does not treat 1 as true or 0 as false.


---

15. The 8 Primitive Types

Let's stop and memorize them.

PRIMITIVE TYPES
                    │
     ┌──────────────┼──────────────┐
     │              │              │
     ▼              ▼              ▼
  Integer       Floating        Others
     │             │              │
     ├─ byte       ├─ float       ├─ char
     ├─ short      └─ double      └─ boolean
     ├─ int
     └─ long

Memory trick:

> B S I L F D C B



B → byte
S → short
I → int
L → long
F → float
D → double
C → char
B → boolean


---

🔵 PART 2: REFERENCE TYPES

Now comes the second major category.

REFERENCE TYPES

Examples:

String
Array
Class
Object
Interface
Enum


---

16. What is a Reference Type?

Consider:

String name = "Java";

String is not a primitive type.

It is a class, so name is a reference variable.

Conceptually:

name
  │
  ▼
String object
"Java"

So:

> A reference variable refers to an object.




---

17. String

This is probably the most important reference type for beginners.

String name = "Rahul";

Remember:

String → reference type

NOT:

String → primitive ❌

Java has no primitive string type.


---

18. Arrays

Arrays are also reference types.

Example:

int[] numbers = {10, 20, 30};

Here:

int   → primitive
int[] → reference type

That's an important distinction.

The elements are int, but the array itself is an object/reference type.


---

19. Classes

Suppose we create:

class Student {
    int age;
}

Then:

Student s = new Student();

Student is a reference type.

Conceptually:

s
│
└──────────────→ Student object


---

20. Primitive vs Reference

Let's compare:

int age = 20;
String name = "Java";

age

int
 ↓
primitive

name

String
 ↓
reference
 ↓
object

So:

int     → primitive
String  → reference


---

21. The null Concept

Here's a very important difference.

A reference can generally be:

String name = null;

This means:

> name currently refers to no object.



But:

int age = null;

is invalid.

Why?

Because int is a primitive type.

So:

Primitive → cannot be null
Reference → can generally be null


---

22. Wrapper Classes

Java provides object versions of primitive types.

Primitive     Wrapper
────────────────────────
byte       →  Byte
short      →  Short
int        →  Integer
long       →  Long
float      →  Float
double     →  Double
char       →  Character
boolean    →  Boolean

For example:

int x = 10;
Integer y = 10;

Here:

int      → primitive
Integer  → reference type


---

23. Why Do We Need Wrapper Classes?

Some Java APIs work with objects, not primitive values.

For example, collections such as:

ArrayList<Integer>

use Integer, not int, as the type argument.

Java can automatically convert:

int → Integer

This is called autoboxing.

And:

Integer → int

is called unboxing.


---

24. One Big Example

Look at this program:

class Student {
    int age;
    double percentage;
    char grade;
    boolean passed;
    String name;

    public static void main(String[] args) {

        Student s = new Student();

        s.age = 20;
        s.percentage = 85.5;
        s.grade = 'A';
        s.passed = true;
        s.name = "Rahul";
    }
}

Let's identify the data types:

age
 ↓
int
 ↓
primitive

percentage
 ↓
double
 ↓
primitive

grade
 ↓
char
 ↓
primitive

passed
 ↓
boolean
 ↓
primitive

name
 ↓
String
 ↓
reference


---

25. Quick Identification Game 🧠

I'll give you some variables.

Example 1

int age = 20;

What is the type?

Answer: int → primitive.


---

Example 2

double salary = 50000.50;

Answer: double → primitive.


---

Example 3

char gender = 'M';

Answer: char → primitive.


---

Example 4

boolean passed = true;

Answer: boolean → primitive.


---

Example 5

String city = "Hyderabad";

Answer: String → reference type.


---

Example 6

int[] marks = {90, 80, 70};

Answer: int[] → reference type.


---

26. A Very Important Exam Trap

What is the difference between:

char c = 'A';

and:

String s = "A";

Answer:

'A'
 ↓
char
 ↓
primitive

"A"
 ↓
String
 ↓
reference

Easy rule:

> Single quotes → char
Double quotes → String




---

27. Another Important Trap

What's the difference between:

int x = 10;

and:

Integer x = 10;

int
 ↓
primitive

Integer
 ↓
wrapper class
 ↓
reference type

They are not the same type.


---

28. What About void?

You may see:

void main()

or:

public void display()

Is void a data type?

void is a special keyword used to indicate that a method does not return a value.

It is not one of Java's eight primitive types.

So don't memorize:

byte
short
int
long
float
double
char
boolean
void ❌

There are exactly 8 primitive data types.


---

29. Default Values

Suppose you have instance variables:

class Test {
    int x;
    double d;
    char c;
    boolean b;
    String s;
}

Java gives default values to instance fields:

int      → 0
double   → 0.0
char     → '\u0000'
boolean  → false
String   → null

But be careful!

Local variables are different.

This is not allowed:

void test() {
    int x;
    System.out.println(x);
}

Java requires a local variable to be initialized before use.


---

30. Data Type Conversion

Sometimes we want to convert one type into another.

Example:

int x = 10;
long y = x;

This is widening conversion.

int → long

Java can perform this automatically.


---

31. Narrowing Conversion

Now:

double x = 10.5;
int y = (int)x;

We explicitly use:

(int)

This is called casting.

Result:

10

The .5 is lost.

So:

> Narrowing conversions can lose information.




---

32. The Big Picture

Now you should see the entire concept:

JAVA DATA TYPES
                           │
             ┌─────────────┴─────────────┐
             ▼                           ▼
        PRIMITIVE                    REFERENCE
             │                           │
      ┌──────┼───────┐            ┌─────┼──────┐
      ▼      ▼       ▼            ▼     ▼      ▼
   Integer Floating Other       String Array  Class
      │      Point
      │        │
 byte short  float
 int   long  double
           │
          char
           │
        boolean


---

🎯 Now Test Yourself

Try answering these without looking above:

1.

int x = 100;

Primitive or reference?

2.

String name = "Java";

Primitive or reference?

3.

char c = 'A';

Primitive or reference?

4.

int[] numbers = {1, 2, 3};

Primitive or reference?

5.

Integer number = 100;

Primitive or reference?

Answers

1. int       → Primitive
2. String    → Reference
3. char      → Primitive
4. int[]     → Reference
5. Integer   → Reference


---

⭐ FINAL MEMORY MAP

8 PRIMITIVE TYPES
│
├── byte
├── short
├── int
├── long
├── float
├── double
├── char
└── boolean

REFERENCE TYPES
│
├── String
├── Array
├── Class
├── Interface
├── Enum
└── other object/reference types

Remember these 5 golden rules:

> 1. Java has 8 primitive data types.



> 2. String is a reference type, NOT a primitive.



> 3. int is the common type for whole numbers.



> 4. double is the common floating-point type.



> 5. int and Integer are different: primitive vs wrapper/reference.



🧠 One-line formula

Data Types → Primitive (8) + Reference (objects such as String, arrays, classes).
