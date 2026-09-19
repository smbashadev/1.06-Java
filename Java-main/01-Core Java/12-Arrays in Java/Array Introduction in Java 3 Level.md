Arrays Introduction in Java — 3 LEVEL 🚀

We'll learn this in 3 levels:

🟢 Level 1 — Beginner: What and why?

🟡 Level 2 — Intermediate: How does it work?

🔴 Level 3 — Advanced: What is really happening?



---

🟢 LEVEL 1 — BEGINNER

1. What is an Array?

Suppose you want to store 5 marks.

Without an array:

int mark1 = 80;
int mark2 = 75;
int mark3 = 90;
int mark4 = 65;
int mark5 = 88;

Instead, use:

int[] marks = {80, 75, 90, 65, 88};

An array allows us to store multiple values of the same type using one array variable.

Definition

> An array is a fixed-size collection of elements of the same data type, accessed using indexes.




---

2. Visualize It

marks
  ↓
┌────┬────┬────┬────┬────┐
│ 80 │ 75 │ 90 │ 65 │ 88 │
└────┴────┴────┴────┴────┘
   0    1    2    3    4
       Index

The first index is always:

0

The last index is:

length - 1

For 5 elements:

last index = 5 - 1 = 4


---

3. Array Declaration

int[] marks;

This says:

> marks is a reference that can refer to an integer array.



It does not create the array yet.


---

4. Array Creation

marks = new int[5];

Or combine:

int[] marks = new int[5];

Now Java creates an array containing 5 integers.

Initially:

[0][0][0][0][0]

because the default value of int is 0.


---

5. Array Initialization

You can directly provide values:

int[] marks = {80, 75, 90, 65, 88};

Java automatically determines:

length = 5


---

6. Access Elements

int[] marks = {80, 75, 90};

System.out.println(marks[0]);

Output:

80

Other examples:

marks[1] → 75
marks[2] → 90


---

7. Change an Element

int[] marks = {80, 75, 90};

marks[1] = 85;

Array becomes:

80  85  90

So arrays are mutable.


---

8. Find Array Length

System.out.println(marks.length);

For:

int[] marks = {80, 75, 90};

output:

3

⚠️ Array uses:

marks.length

not:

marks.length()


---

🟡 LEVEL 2 — INTERMEDIATE

9. Array + for Loop

Instead of:

System.out.println(marks[0]);
System.out.println(marks[1]);
System.out.println(marks[2]);

use:

int[] marks = {80, 75, 90};

for (int i = 0; i < marks.length; i++) {
    System.out.println(marks[i]);
}

Output:

80
75
90

How does it work?

i = 0 → marks[0]
i = 1 → marks[1]
i = 2 → marks[2]
i = 3 → stop

Because:

3 < 3 → false


---

10. Enhanced for Loop

If you don't need the index:

int[] marks = {80, 75, 90};

for (int mark : marks) {
    System.out.println(mark);
}

Read it as:

> For every mark in marks.



Difference

Normal for	Enhanced for

Gives index	Doesn't directly give index
marks[i]	mark
Good when modifying by position	Good for reading elements



---

11. Finding Total

int[] marks = {80, 75, 90};

int total = 0;

for (int i = 0; i < marks.length; i++) {
    total = total + marks[i];
}

System.out.println(total);

Output:

245

Flow:

total = 0
       ↓
+80 = 80
       ↓
+75 = 155
       ↓
+90 = 245


---

12. Finding Average

int[] marks = {80, 75, 90};

int total = 0;

for (int mark : marks) {
    total += mark;
}

double average = (double) total / marks.length;

System.out.println(average);

Output:

81.66666666666667

The array's length is useful here because it tells us the number of elements.


---

13. Important Array Errors

Error 1 — Invalid index

int[] a = {10, 20, 30};

System.out.println(a[3]);

Valid indexes:

0 1 2

a[3] causes:

ArrayIndexOutOfBoundsException


---

Error 2 — Wrong loop condition

Wrong:

for (int i = 0; i <= a.length; i++)

Correct:

for (int i = 0; i < a.length; i++)

Remember:

> Use <, not <=, when iterating from 0 to the last index.




---

Error 3 — No array creation

Wrong:

int[] a;
a[0] = 10;

Correct:

int[] a = new int[5];
a[0] = 10;


---

14. Array Size Is Fixed

If:

int[] a = new int[5];

you cannot increase this same array to 10 elements.

The array remains:

length = 5

If you need a dynamically sized collection, use a collection such as ArrayList.

Array      → fixed size
ArrayList  → dynamically resizable


---

🔴 LEVEL 3 — ADVANCED

Now let's understand what Java is actually doing.


---

15. Array Is an Object

This is a very important concept.

int[] a = {10, 20, 30};

a is a reference variable.

The array itself is an object.

Conceptually:

a
│
│ reference
↓
┌────┬────┬────┐
│ 10 │ 20 │ 30 │
└────┴────┴────┘

So:

int
 ↓
primitive

int[]
 ↓
array reference type / array object


---

16. Assignment Copies the Reference

Consider:

int[] a = {10, 20, 30};

int[] b = a;

Many beginners think a new array was created.

❌ No.

Both references refer to the same array:

a ─────┐
              ↓
           [10][20][30]
              ↑
       b ─────┘

Now:

b[0] = 100;

Then:

System.out.println(a[0]);

Output:

100

Why?

Because a and b refer to the same array object.


---

17. null vs Empty Array

These are very different:

Empty array

int[] a = new int[0];

There is an array object.

length = 0

Null reference

int[] a = null;

There is no array object being referenced.

So:

a.length

with a == null causes:

NullPointerException


---

18. Default Values Depend on Type

Example:

int[] a = new int[3];

becomes:

0 0 0

But:

boolean[] b = new boolean[3];

becomes:

false false false

And:

String[] names = new String[3];

becomes:

null null null

So Java initializes array elements with their type's default value.


---

19. Arrays of Objects

Consider:

String[] names = {"Ali", "Ravi", "John"};

The array stores references to String objects.

Similarly:

Student[] students = new Student[3];

Initially:

students
   ↓
[null][null][null]

You then assign objects:

students[0] = new Student();


---

20. Multidimensional Arrays

Java supports:

int[][] matrix = new int[2][3];

Think:

columns
           0    1    2
        ┌────┬────┬────┐
row 0   │  0 │  0 │  0 │
        ├────┼────┼────┤
row 1   │  0 │  0 │  0 │
        └────┴────┴────┘

Access:

matrix[0][1]

means:

> Row 0, column 1.




---

21. Java Supports Jagged Arrays

Java's multidimensional arrays are actually arrays of arrays.

Therefore:

int[][] a = new int[2][];

a[0] = new int[3];
a[1] = new int[5];

creates:

Row 0 → [ ][ ][ ]

Row 1 → [ ][ ][ ][ ][ ]

So rows don't necessarily have to be the same length.


---

22. Array Declaration Styles

Both are valid:

int[] a;

and:

int a[];

But the first is generally preferred:

int[] a;

because it clearly communicates:

> a is an array of int.




---

23. Three Ways to Initialize

1. Array initializer

int[] a = {10, 20, 30};

2. new with size

int[] a = new int[3];

3. new with values

int[] a = new int[]{10, 20, 30};

All are valid.


---

24. Important Syntax Trap

This is valid:

int[] a = {10, 20, 30};

But later:

a = {40, 50, 60};   // ❌

is invalid.

Use:

a = new int[]{40, 50, 60};   // ✅


---

🧠 3-LEVEL SUMMARY

🟢 LEVEL 1 — Know

Array
 ↓
Multiple values
 ↓
Same type
 ↓
Fixed size
 ↓
Index starts at 0

Main syntax:

int[] a = {10, 20, 30};


---

🟡 LEVEL 2 — Use

Access:

a[0]

Modify:

a[0] = 100;

Length:

a.length

Loop:

for (int i = 0; i < a.length; i++) {
    System.out.println(a[i]);
}

Enhanced loop:

for (int x : a) {
    System.out.println(x);
}


---

🔴 LEVEL 3 — Understand

Array is an object
       ↓
Array variable holds a reference
       ↓
Array length is fixed
       ↓
Assignment copies the reference
       ↓
Arrays can contain primitives or references
       ↓
Multidimensional arrays are arrays of arrays


---

🏆 10 Rules to Memorize

1. Array stores multiple elements.


2. Elements have a component type.


3. Array size is fixed after creation.


4. Index starts at 0.


5. Last index = length - 1.


6. Number of elements = array.length.


7. Use array[index] to access an element.


8. array.length is a field, not a method.


9. Invalid indexes cause ArrayIndexOutOfBoundsException.


10. An array is an object, and an array variable holds a reference to it.



⭐ One-line memory trick

> ARRAY = Same Type + Fixed Size + Zero-Based Index + One Reference Name.
