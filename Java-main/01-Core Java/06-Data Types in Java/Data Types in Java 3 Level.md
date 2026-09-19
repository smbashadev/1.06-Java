Data Types in Java — 3 LEVELS 🎯

Learn it in 3 stages: Beginner → Intermediate → Advanced.


---

🟢 LEVEL 1 — BEGINNER

1. What is a Data Type?

A data type tells Java what kind of value a variable can store.

int age = 20;

Here:

int → Data Type
age → Variable
20  → Value


---

2. Two Main Categories

DATA TYPES
                     │
          ┌──────────┴──────────┐
          ▼                     ▼
     PRIMITIVE              REFERENCE
       TYPES                   TYPES

Primitive

Java has exactly 8:

byte
short
int
long
float
double
char
boolean

Reference

Examples:

String
Array
Class
Interface
Enum


---

3. Easy Examples

int age = 20;
double salary = 50000.5;
char grade = 'A';
boolean passed = true;

String name = "Rahul";

Think:

int      → whole number
double   → decimal
char     → single character
boolean  → true/false
String   → text


---

🟡 LEVEL 2 — INTERMEDIATE

Now understand the 8 primitive types properly.

1. Integer Types

byte → short → int → long

Type	Size	Example

byte	8-bit	byte x = 10;
short	16-bit	short x = 100;
int	32-bit	int x = 1000;
long	64-bit	long x = 1000L;


Most commonly used:

int age = 25;


---

2. Floating-Point Types

float → double

Type	Size	Example

float	32-bit	float x = 10.5f;
double	64-bit	double x = 10.5;


Remember:

float x = 10.5f;
double y = 10.5;

A decimal floating-point literal is double by default.


---

3. Character

char grade = 'A';

Java char is 16-bit and represents a UTF-16 code unit.

Remember:

'A' → char
"A" → String


---

4. Boolean

boolean passed = true;

Only:

true
false

are valid boolean values.


---

5. Reference Types

Consider:

String name = "Java";

String is not primitive.

It is a reference type.

Similarly:

int[] marks = {90, 80, 70};

int[] is a reference type, even though its elements are int.


---

🔴 LEVEL 3 — ADVANCED

Now let's understand the distinctions that cause exam and interview doubts.


---

1. Primitive vs Reference

int x = 10;
String s = "Java";

Conceptually:

x
↓
10

s
↓
reference ─────→ String object

Therefore:

int     → primitive
String  → reference

A primitive variable represents a primitive value.

A reference variable refers to an object.


---

2. int vs Integer

int x = 10;
Integer y = 10;

They are different.

int
 ↓
Primitive

Integer
 ↓
Wrapper class
 ↓
Reference type

Wrapper mapping:

byte    → Byte
short   → Short
int     → Integer
long    → Long
float   → Float
double  → Double
char    → Character
boolean → Boolean


---

3. null

Reference types can generally contain null:

String name = null;

Primitive types cannot:

int age = null;       // ❌
boolean x = null;     // ❌

So remember:

> Primitive → no null
Reference → can generally be null




---

4. Default Values

Instance variables/fields receive default values.

byte/short/int/long → 0
float/double        → 0.0
char                → '\u0000'
boolean             → false
reference           → null

But local variables don't get automatic default values.

void test() {
    int x;
    System.out.println(x); // ❌
}

You must initialize x first.


---

5. Type Conversion

Widening

Smaller compatible numeric types can generally be converted to larger ones automatically:

int x = 100;
long y = x;

int → long

Narrowing

A conversion in the opposite direction generally requires casting:

double x = 10.5;
int y = (int) x;

Result:

10

The fractional part is lost.


---

⭐ Complete 3-Level Map

🟢 Level 1 — Remember

Data Types
├── Primitive
└── Reference


---

🟡 Level 2 — Understand

Primitive
├── byte
├── short
├── int
├── long
├── float
├── double
├── char
└── boolean

Reference
├── String
├── Array
├── Class
├── Interface
└── Enum


---

🔴 Level 3 — Master

Primitive
   ↓
8 built-in types
   ↓
stores primitive values
   ↓
cannot be null

Reference
   ↓
String / Array / Class / Interface / Enum...
   ↓
refers to objects
   ↓
can generally be null


---

🧠 Final Exam Memory

8 Primitive Types

> B S I L F D C B



B → byte
S → short
I → int
L → long
F → float
D → double
C → char
B → boolean

Most important traps

String  → Reference ❗
Integer → Reference ❗
int     → Primitive
char    → 16-bit
boolean → true/false
double  → default decimal floating-point type

One-line answer

> Java data types are classified into primitive and reference types; Java has eight primitive types—byte, short, int, long, float, double, char, and boolean—while reference types include classes, arrays, strings, interfaces, and enums.
