Type Casting in Java — TEACH ME 

Let's learn Type Casting from zero, as if we're in a classroom. I'll build the idea first, then the rules, then the tricky cases.


---

🧠 STEP 1 — First Understand the Problem

Suppose you have:

int age = 20;

age is an int.

Now suppose you want to put that value into a double:

double x = age;

Java allows this:

int 20
  ↓
double 20.0

Why?

Because a double can represent the value 20 without a problem.

This is called widening conversion.


---

🟢 STEP 2 — Widening

Think:

> Small container → Bigger container



int x = 10;
double y = x;

No special syntax is required.

int
 ↓
double

Java automatically performs the conversion.

Another example

byte a = 10;
int b = a;

byte → int

Again, automatic.


---

🧠 Easy Memory Trick

> Widening = automatic



Think:

SMALL → BIG
   ↓
SAFE
   ↓
AUTOMATIC


---

🟡 STEP 3 — What About Big → Small?

Now reverse the situation:

double x = 10.5;
int y = x;

Java says:

❌ incompatible types

Why?

Because double can contain a fractional value, while int cannot.

Java doesn't want you to accidentally lose information.

So you must explicitly tell Java:

> "I know what I'm doing."



Write:

int y = (int) x;

Now:

double 10.5
     ↓
   (int)
     ↓
int 10

This is narrowing casting.


---

🔴 STEP 4 — Narrowing

Think:

> Big container → Smaller container



double x = 10.5;
int y = (int) x;

The syntax:

(targetType)

is called the cast operator.

So:

(int) x

means:

> Convert the value of x to int.




---

🎯 The Two Main Types

TYPE CASTING
                      │
              ┌───────┴───────┐
              ▼               ▼
          WIDENING         NARROWING
          Automatic        Explicit
          Small → Big      Big → Small
          Usually safer    May lose data


---

🟢 STEP 5 — Let's Practice

Example 1

int x = 10;
double y = x;

Ask:

int → double?

Yes.

Therefore:

✅ Widening
✅ Automatic


---

Example 2

double x = 10.5;
int y = (int) x;

Ask:

double → int?

Yes, but it's narrowing.

Therefore:

✅ Narrowing
✅ Explicit cast

Output:

10


---

🧠 STEP 6 — Why Did .5 Disappear?

This is very important.

double x = 10.9;
int y = (int) x;

You might think:

10.9 → 11

❌ Wrong.

Casting doesn't round.

It truncates toward zero:

10.9  → 10
10.1  → 10

-10.9 → -10
-10.1 → -10

So:

> Casting ≠ rounding




---

🟡 STEP 7 — What About byte?

Consider:

int x = 100;
byte y = (byte) x;

This works because 100 fits inside a byte.

A Java byte range is:

-128 to 127

But now:

int x = 130;
byte y = (byte) x;

What happens?

130 doesn't fit in byte.

The result is:

-126

So narrowing can cause information loss/wrapping.


---

🟠 STEP 8 — char and Casting

Remember:

char c = 'A';

Java's char is a 16-bit UTF-16 code unit.

You can convert it to an integer:

char c = 'A';
int x = c;

Result:

65

So:

char → int

is widening.


---

Now reverse it:

int x = 65;
char c = (char) x;

Result:

'A'

So:

int → char

requires explicit casting.


---

🔥 STEP 9 — One Very Important Trap

Look at this:

byte a = 10;
byte b = 20;

byte c = a + b;

Will it work?

❌ No.

Why?

Java performs numeric promotion.

byte + byte
     ↓
    int

So this works:

int c = a + b;

If you specifically want a byte:

byte c = (byte)(a + b);


---

🧠 STEP 10 — char Has a Similar Rule

char c = 'A';

int x = c + 1;

The result is an int.

So arithmetic can promote smaller integral types.

Think:

byte
short
char
   ↓
 arithmetic
   ↓
 int

This is why beginner programs sometimes produce surprising compilation errors.


---

🟣 STEP 11 — Reference Casting

So far we've discussed primitive types.

Now let's talk about objects.

Suppose:

class Animal {
}

class Dog extends Animal {
}

And:

Dog d = new Dog();
Animal a = d;

This is upcasting.

Dog
 ↓
Animal

Every Dog is an Animal.

So Java allows it automatically.


---

🟢 Upcasting

Dog d = new Dog();
Animal a = d;

Think:

> Child → Parent



Dog → Animal

Usually:

Automatic


---

🔴 Downcasting

Now:

Animal a = new Dog();
Dog d = (Dog) a;

This is:

Parent → Child

or:

> Downcasting



An explicit cast is required.


---

⚠️ STEP 12 — Downcasting Can Fail

Consider:

Animal a = new Cat();

Dog d = (Dog) a;

The compiler may allow the cast because Dog and Cat are related through Animal.

But the actual object is:

Cat

not:

Dog

Therefore at runtime:

ClassCastException

can occur.


---

🧠 The Most Important Reference-Type Idea

Look at:

Animal a = new Dog();

There are two things to distinguish:

Animal a
   ↑
reference type

new Dog()
   ↑
actual object type

The reference type and actual object type aren't necessarily the same.


---

🟠 STEP 13 — Casting Doesn't Change the Object

Suppose:

Animal a = new Dog();
Dog d = (Dog) a;

The cast does NOT transform the object.

It was already:

Dog object

The cast simply lets you treat the reference as a Dog when that is actually valid.


---

❌ STEP 14 — You Can't Cast Anything to Anything

This is invalid:

int x = 10;

String s = (String) x;

Why?

Because int and String aren't related by a valid cast.

If you want:

int → String

use conversion:

String s = String.valueOf(x);

That's conversion, not casting.


---

❌ STEP 15 — String to int

You also cannot do:

String s = "100";

int x = (int) s;  // ❌

Instead:

int x = Integer.parseInt(s);

This is parsing.

So remember:

String → int
     ↓
parseInt()

not casting.


---

🧩 STEP 16 — The Big Picture

Now combine everything:

TYPE CONVERSION
                               │
              ┌────────────────┴────────────────┐
              ▼                                 ▼
         PRIMITIVES                         REFERENCES
              │                                 │
       ┌──────┴──────┐                    ┌─────┴─────┐
       ▼             ▼                    ▼           ▼
   Widening      Narrowing             Upcast      Downcast
   Automatic     Explicit             Child→Parent Parent→Child
       │             │                    │           │
   int→long     double→int            Dog→Animal Animal→Dog


---

🏆 STEP 17 — Let's Solve 5 Questions

Q1

int x = 10;
double y = x;

What type of conversion?

Answer:

Widening


---

Q2

double x = 10.5;
int y = (int)x;

What happens?

Answer:

y = 10


---

Q3

byte a = 10;
byte b = 20;
byte c = a + b;

Correct or wrong?

Answer:

❌ Wrong

Because:

byte + byte → int


---

Q4

Animal a = new Dog();
Dog d = (Dog)a;

What is this?

Answer:

Downcasting

And here it succeeds because the actual object is a Dog.


---

Q5

Animal a = new Cat();
Dog d = (Dog)a;

What happens?

Answer:

Runtime → ClassCastException

because the actual object is a Cat.


---

🎯 Final Memory Trick

Remember these 4 arrows:

1️⃣ Primitive:
   SMALL → BIG
   WIDENING
   AUTOMATIC

2️⃣ Primitive:
   BIG → SMALL
   NARROWING
   EXPLICIT

3️⃣ Object:
   CHILD → PARENT
   UPCASTING
   AUTOMATIC

4️⃣ Object:
   PARENT → CHILD
   DOWNCASTING
   EXPLICIT

🔥 One-line master rule

> Widening is usually automatic, narrowing requires an explicit cast and may lose information; upcasting moves from child to parent, while downcasting moves from parent reference to child and can fail at runtime.
