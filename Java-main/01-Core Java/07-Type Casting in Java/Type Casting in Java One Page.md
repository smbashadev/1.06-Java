Type Casting in Java — ONE PAGE 

1. What is Type Casting?

Type casting means converting a value from one data type to another.

Type A
  ↓
Type Casting
  ↓
Type B

Example:

int x = 10;
double y = x;

Here:

int → double


---

2. Two Types of Casting

Type Casting
                  │
          ┌───────┴───────┐
          ▼               ▼
      Widening         Narrowing
     (Implicit)        (Explicit)


---

🟢 3. Widening Casting

Converting a smaller compatible numeric type → larger compatible numeric type.

int x = 10;
double y = x;

int → double

Java performs this automatically.

Common direction

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

Example:

int x = 100;
long y = x;
double z = y;

Remember:

> Widening = automatic = generally safer



⚠️ "Wider" does not mean every conversion is perfectly precise; for example, large integers converted to floating-point can lose precision.


---

🔴 4. Narrowing Casting

Converting a larger type → smaller/incompatible-range numeric type.

It generally requires an explicit cast.

double x = 10.5;
int y = (int) x;

Result:

y = 10

The decimal part is discarded.

Syntax

targetType variable = (targetType) value;

Example:

long x = 100;
int y = (int) x;

Remember:

> Narrowing = explicit cast = possible data loss




---

5. Widening vs Narrowing

Widening	Narrowing

Smaller → broader type	Broader → narrower type
Usually automatic	Usually explicit
Generally safer	May lose information
int → long	long → int
int → double	double → int



---

6. Example

public class Demo {
    public static void main(String[] args) {

        int a = 10;

        // Widening
        double b = a;

        // Narrowing
        double x = 10.75;
        int y = (int) x;

        System.out.println(b); // 10.0
        System.out.println(y); // 10
    }
}


---

7. Important: Type Casting ≠ Type Conversion

Both involve changing types, but the terminology is useful:

Widening conversion:

int x = 10;
double y = x;

Explicit cast:

double x = 10.5;
int y = (int) x;

The second explicitly uses the cast operator:

(int)


---

8. Casting char and Numbers

char participates in numeric conversions.

char c = 'A';
int x = c;

This is widening.

Conceptually:

'A' → Unicode/UTF-16 numeric value → int

For example, 'A' has numeric value 65.

Reverse conversion:

int x = 65;
char c = (char) x;

Result:

'A'


---

9. Casting byte, short, and char

A common Java rule:

> Arithmetic operations on byte, short, and char generally undergo numeric promotion to int.



Example:

byte a = 10;
byte b = 20;

// byte c = a + b;   // ❌
int c = a + b;       // ✅

If you really want a byte:

byte c = (byte)(a + b);


---

10. Casting Reference Types

Casting is not limited to primitives.

Reference types can also be cast when the types are related.

Example:

Animal a = new Dog();

Dog d = (Dog) a;

This is a downcast.

Animal reference
      ↓
   (Dog)
      ↓
Dog reference

⚠️ If the object is not actually a Dog, the cast can fail at runtime with ClassCastException.


---

🎯 Quick Memory Trick

Widening

> Small → Big → Automatic



int → long
int → double

Narrowing

> Big → Small → Cast



long → int
double → int


---

⭐ Exam Formula

TYPE CASTING
                  │
       ┌──────────┴──────────┐
       ▼                     ▼
   WIDENING               NARROWING
   Automatic              Explicit
   Safer                  Data loss possible
   int → long             long → int
   int → double           double → int

One-line definition:

> Type casting in Java is the conversion of a value from one data type to another, either automatically through widening conversion or explicitly using a cast for narrowing conversion.
