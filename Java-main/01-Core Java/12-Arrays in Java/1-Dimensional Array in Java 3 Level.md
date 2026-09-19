1-Dimensional Array in Java — 3 LEVELS

Think of this as Level 1 → Level 2 → Level 3. Move to the next level only when the previous one is clear.


---

🟢 LEVEL 1 — BEGINNER

1. What is a 1-D Array?

A 1-Dimensional array stores multiple values of the same type in a single sequence.

int[] marks = {80, 90, 70, 60};

Visualize it:

80   90   70   60
        ↓    ↓    ↓    ↓
Index   0    1    2    3

Key idea

1-D Array
   ↓
One index
   ↓
array[index]

For example:

System.out.println(marks[2]);

Output:

70


---

2. Declaration

int[] marks;

This declares a reference variable.

The array has not been created yet.


---

3. Creation

marks = new int[4];

Or:

int[] marks = new int[4];

This creates an array containing 4 integers.

Initially:

[0][0][0][0]

because the default value of int is 0.


---

4. Initialization

You can directly provide values:

int[] marks = {80, 90, 70, 60};

Now:

Index:  0   1   2   3
Value: 80  90  70  60


---

5. Index

Java arrays use zero-based indexing.

First index = 0
Last index  = length - 1

For:

int[] a = {10, 20, 30, 40, 50};

length = 5
last index = 4


---

6. Access and Modify

Access:

System.out.println(a[2]);

Output:

30

Modify:

a[2] = 100;

Now:

[10][20][100][40][50]


---

7. Length

System.out.println(a.length);

Output:

5

Remember:

a.length      // ✅
a.length()    // ❌


---

8. Basic Program

class ArrayDemo {

    public static void main(String[] args) {

        int[] numbers = {10, 20, 30, 40, 50};

        for (int i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i]);
        }
    }
}

Output:

10
20
30
40
50

LEVEL 1 MASTER FORMULA

Array
 ↓
Same Type
 ↓
Fixed Size
 ↓
Zero-Based Index
 ↓
array[index]
 ↓
array.length


---

🟡 LEVEL 2 — INTERMEDIATE

Now let's understand how arrays actually behave.


---

1. Why Use a Loop?

Without a loop:

System.out.println(a[0]);
System.out.println(a[1]);
System.out.println(a[2]);
System.out.println(a[3]);

With a loop:

for (int i = 0; i < a.length; i++) {
    System.out.println(a[i]);
}

The loop automatically visits every index.

i = 0 → a[0]
i = 1 → a[1]
i = 2 → a[2]
i = 3 → a[3]
...


---

2. Why < and Not <=?

Suppose:

int[] a = new int[5];

Valid indexes:

0 1 2 3 4

Correct:

i < a.length

Incorrect:

i <= a.length

Because i eventually becomes 5.

Then:

a[5]

causes:

ArrayIndexOutOfBoundsException

Golden Rule

0 ≤ index < array.length


---

3. Enhanced for Loop

If you don't need the index:

for (int value : a) {
    System.out.println(value);
}

Read it as:

> For every value inside a.



Comparison

Requirement	Loop

Need index	Normal for
Need only values	Enhanced for



---

4. Sum of Array Elements

int[] numbers = {10, 20, 30, 40};

int sum = 0;

for (int value : numbers) {
    sum = sum + value;
}

System.out.println(sum);

Output:

100

Flow:

0
 ↓ +10
10
 ↓ +20
30
 ↓ +30
60
 ↓ +40
100


---

5. Find Largest Element

int[] numbers = {40, 10, 90, 30, 70};

int largest = numbers[0];

for (int i = 1; i < numbers.length; i++) {

    if (numbers[i] > largest) {
        largest = numbers[i];
    }
}

System.out.println(largest);

Output:

90


---

6. Searching

int[] numbers = {10, 20, 30, 40, 50};

int search = 30;

for (int i = 0; i < numbers.length; i++) {

    if (numbers[i] == search) {
        System.out.println("Found at index " + i);
        break;
    }
}

Output:

Found at index 2


---

7. Array Is an Object

This:

int[] a = new int[5];

creates an array object.

a is a reference to that object.

Conceptually:

a
↓
┌────┬────┬────┬────┬────┐
│ 0  │ 0  │ 0  │ 0  │ 0  │
└────┴────┴────┴────┴────┘


---

8. Reference Assignment

Consider:

int[] a = {10, 20, 30};

int[] b = a;

There are not two arrays.

Both references point to the same array:

a ──────┐
        ↓
      [10][20][30]
        ↑
b ──────┘

Therefore:

b[0] = 100;

also makes:

a[0]

equal to 100.


---

🔴 LEVEL 3 — ADVANCED

Now let's understand the deeper Java behavior.


---

1. null vs Empty Array

Empty array

int[] a = new int[0];

Array exists.

length = 0

null

int[] a = null;

No array object is being referenced.

Therefore:

a.length

causes:

NullPointerException

So:

new int[0] ≠ null


---

2. Fixed Size

Once:

int[] a = new int[5];

is created, its length is fixed at 5.

You cannot add another element to that same array.

You can make a refer to another array:

a = new int[10];

but that's a new array object.


---

3. Array Reference vs Array Object

Understand this carefully:

int[] a = new int[3];

There are two concepts:

a
↓
Reference variable

new int[3]
↓
Array object

Conceptually:

a
       ↓
┌──────┬──────┬──────┐
│  0   │  0   │  0   │
└──────┴──────┴──────┘


---

4. Arrays of Objects

A 1-D array doesn't have to contain primitives.

You can have:

String[] names = {
    "Ali",
    "Ravi",
    "John"
};

Or:

Student[] students = new Student[3];

Initially:

[null][null][null]

because the elements are references.


---

5. Array Type Must Match

This is valid:

int[] numbers = {10, 20, 30};

But you can't put a String into it:

numbers[0] = "Java";  // ❌

Why?

Because the array's component type is int.


---

6. Advanced Array Type Behavior

Java arrays carry runtime type information.

For example:

String[] names = new String[3];

Object[] objects = names;

This assignment is allowed because String is an Object.

But:

objects[0] = Integer.valueOf(10);

causes:

ArrayStoreException

Why?

Because the actual array is still:

String[]

not:

Object[]

This is an advanced consequence of Java's array type system.


---

🎯 3-LEVEL MASTER TABLE

Concept	LEVEL 1	LEVEL 2	LEVEL 3

Definition	Same type, fixed size	Single sequence	Runtime array object
Indexing	Starts at 0	0 ≤ i < length	Bounds checked at runtime
Access	a[i]	Loop through a[i]	Invalid access throws exception
Size	a.length	Last = length - 1	Length is fixed
Loop	for	Enhanced for	Understand iteration behavior
Reference	Basic idea	b = a shares array	Reference vs object
Values	Primitive/object	Default values	Runtime component type
Special cases	—	null	ArrayStoreException



---

🧠 FINAL 3-LEVEL MAP

1-D ARRAY
                     │
        ┌────────────┴────────────┐
        ↓                         ↓
      LEVEL 1                   LEVEL 2
   Basic Concepts             Working With It
        │                         │
   Declaration                Traversal
   Creation                   Searching
   Initialization             Sum
   Indexing                   Maximum
   length                     Enhanced for
        │                         │
        └────────────┬────────────┘
                     ↓
                  LEVEL 3
                 Internals
                     │
          ┌──────────┼──────────┐
          ↓          ↓          ↓
       Object     Reference    Runtime
          │          │          │
        null      b = a      Type checks
                              │
                              ↓
                       ArrayStoreException

🏆 If You Can Answer These, You Know 1-D Arrays

1. Why does array indexing start at 0?


2. What is the difference between length and last index?


3. Why is i < a.length correct but i <= a.length wrong?


4. What is the difference between an array reference and an array object?


5. What happens when int[] a = new int[5] is created?


6. What is the difference between null and a zero-length array?


7. Why does int[] a; not create an array?


8. What happens when int[] b = a?


9. Why can a String[] be assigned to an Object[] reference?


10. Why can that lead to ArrayStoreException?



If these ten are clear, you've moved from using arrays to actually understanding arrays.
