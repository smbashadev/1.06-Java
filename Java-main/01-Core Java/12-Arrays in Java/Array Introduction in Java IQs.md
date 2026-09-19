Arrays Introduction in Java — DOUBT KILLER 🔥

This is the exam + interview + beginner-confusion clearing version. We'll focus on the questions that usually create confusion.


---

1. What Exactly Is an Array?

Definition

> An array is an object that stores a fixed number of elements of the same component type, where each element is accessed using a zero-based index.



Example:

int[] marks = {80, 90, 70, 60};

Visualize:

marks
  ↓
┌────┬────┬────┬────┐
│ 80 │ 90 │ 70 │ 60 │
└────┴────┴────┴────┘
   0    1    2    3


---

2. Why Do We Need Arrays?

Without an array:

int mark1 = 80;
int mark2 = 90;
int mark3 = 70;
int mark4 = 60;

With an array:

int[] marks = {80, 90, 70, 60};

So instead of managing many variables, we manage one array reference.


---

3. Is an Array a Data Type?

This question needs a careful answer.

Primitive data types:

byte
short
int
long
float
double
char
boolean

An array is not a primitive data type.

For example:

int[] a;

int is primitive, but int[] is an array type/reference type.

Arrays are objects in Java.


---

4. Is an Array an Object?

Yes ✅

int[] a = new int[5];

The array created by new is an object.

Think:

a
↓
reference
↓
ARRAY OBJECT

That's why arrays have properties such as:

a.length


---

5. Is a the Array or the Reference?

Consider:

int[] a = new int[5];

Conceptually:

a
       │
       │ reference
       ↓
┌────┬────┬────┬────┬────┐
│  0 │  0 │  0 │  0 │  0 │
└────┴────┴────┴────┴────┘

a is a reference variable.

The actual array is the object created by:

new int[5]


---

6. Does Declaration Create an Array?

No.

int[] a;

This only declares a reference variable.

It does not create an array object.

You need:

a = new int[5];

or:

int[] a = new int[5];


---

7. What Does new int[5] Mean?

This is a major doubt.

new int[5]

means:

> Create an array containing 5 integer elements.



It does not mean the last index is 5.

The indexes are:

0  1  2  3  4

Therefore:

Number of elements = 5
Last index = 4


---

8. Why Does Index Start at 0?

Java arrays use zero-based indexing.

For:

int[] a = {10, 20, 30};

we have:

Index:  0    1    2
        ↓    ↓    ↓
Value: 10   20   30

Therefore:

a[0] → 10
a[1] → 20
a[2] → 30

Golden formula:

Last index = length - 1


---

9. What Is a.length?

Suppose:

int[] a = {10, 20, 30, 40};

Then:

a.length

returns:

4

It means:

> Number of elements in the array.



It does NOT mean:

> Last index.



Last index:

4 - 1 = 3


---

10. Why Is length Not length()?

For arrays:

a.length

For strings:

str.length()

So remember:

Array  → length
String → length()

length is an array field/property.


---

11. What Is the Difference Between a.length and a[a.length - 1]?

Suppose:

int[] a = {10, 20, 30, 40};

Then:

a.length

gives:

4

But:

a[a.length - 1]

means:

a[3]

which gives:

40

Therefore:

a.length
       ↓
number of elements

a[a.length - 1]
       ↓
last element


---

12. Why Is This Loop Correct?

for (int i = 0; i < a.length; i++) {
    System.out.println(a[i]);
}

Suppose:

a.length = 4

Then i becomes:

0
1
2
3

At:

i = 4

condition:

4 < 4

is false.

Therefore it stops before accessing a[4].


---

13. Why Is <= Wrong Here?

This is wrong:

for (int i = 0; i <= a.length; i++)

Because eventually:

i = a.length

Suppose length = 4:

i = 4

Then Java attempts:

a[4]

But valid indexes are:

0 1 2 3

Result:

ArrayIndexOutOfBoundsException

Remember:

Index condition:

0 ≤ index < length


---

14. Can We Access a Negative Index?

No.

a[-1]

is invalid.

Java throws:

ArrayIndexOutOfBoundsException


---

15. What Happens If We Access an Invalid Index?

Example:

int[] a = {10, 20, 30};

System.out.println(a[3]);

Valid indexes:

0 1 2

3 doesn't exist.

Therefore:

ArrayIndexOutOfBoundsException


---

16. Can We Change Array Elements?

Yes ✅

int[] a = {10, 20, 30};

a[1] = 200;

Now:

10 200 30

Arrays are mutable.


---

17. Can We Change the Array Size?

No ❌

If:

int[] a = new int[5];

then that array has exactly 5 elements.

You cannot resize that same array.

You can create another array:

a = new int[10];

But that's a new array object, not a resized version of the old one.


---

18. What Happens to the Old Array?

Consider:

int[] a = new int[5];

a = new int[10];

Initially:

a
↓
[ ][ ][ ][ ][ ]

After reassignment:

a
↓
[ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]

The old 5-element array is no longer referenced by a.

If nothing else refers to it, it can eventually be reclaimed by Java's garbage collector.


---

19. Can an Array Store Different Types?

Normally, an array has a declared component type.

For example:

int[] a = {10, 20, 30};

You cannot put a String into it:

a[0] = "Java";   // ❌

because the array is an int[].


---

20. Can an Array Store Objects?

Yes.

String[] names = {
    "Ali",
    "Ravi",
    "John"
};

You can also create:

Student[] students = new Student[3];

Initially:

null null null

because the elements are references.


---

21. Why Are Object Array Elements null?

Consider:

String[] names = new String[3];

Java creates:

[null][null][null]

Why?

Because String is a reference type.

The default value of a reference is:

null

Compare:

int[] a = new int[3];

gives:

[0][0][0]

while:

String[] b = new String[3];

gives:

[null][null][null]


---

22. What Are Default Values?

Array element type	Default value

byte	0
short	0
int	0
long	0L
float	0.0f
double	0.0d
char	'\u0000'
boolean	false
Reference type	null



---

23. What Is the Difference Between These Two?

Case 1

int[] a = new int[0];

An actual array exists.

length = 0

Case 2

int[] a = null;

No array object is referenced.

So:

a.length

in Case 2 causes:

NullPointerException

Easy memory:

empty array ≠ null


---

24. What Does This Do?

int[] a = {10, 20, 30};

int[] b = a;

Does b get a new array?

No ❌

Both point to the same array.

a ─────┐
              ↓
           [10][20][30]
              ↑
       b ─────┘

Therefore:

b[0] = 100;

also changes what a[0] sees.

System.out.println(a[0]);

Output:

100


---

25. Does int[] b = a Copy the Array?

No.

It copies the reference.

b = a
 ↓
reference copied

It does not mean:

elements copied

If you need a separate copy, you need an array-copy operation such as clone() or Arrays.copyOf().


---

26. Is an Array Mutable?

Yes.

This:

a[0] = 100;

changes an element.

But:

array length

cannot be changed.

So:

Elements → mutable
Length   → fixed


---

27. What Is the Difference Between Array and ArrayList?

Array	ArrayList

Fixed size	Dynamic size
a.length	list.size()
a[i]	list.get(i)
Can store primitives directly	Uses wrapper/reference types
Simple fixed-size data	Useful for changing-size collections


Example:

int[] a = new int[5];

versus:

ArrayList<Integer> list = new ArrayList<>();


---

28. Can We Write int a[]?

Yes.

int[] a;

and:

int a[];

are both valid.

But generally prefer:

int[] a;

because it makes the array type more obvious.


---

29. Can We Initialize Like This?

int[] a = {10, 20, 30};

Yes ✅

But later this is invalid:

a = {40, 50, 60};   // ❌

Instead:

a = new int[]{40, 50, 60};   // ✅


---

30. Three Ways to Create an Array

Way 1

int[] a = new int[3];

Creates:

[0][0][0]

Way 2

int[] a = {10, 20, 30};

Creates and initializes.

Way 3

int[] a = new int[]{10, 20, 30};

Also creates and initializes.


---

31. What Is a Multidimensional Array?

Example:

int[][] matrix = new int[2][3];

Think:

0    1    2
    ┌────┬────┬────┐
 0  │  0 │  0 │  0 │
    ├────┼────┼────┤
 1  │  0 │  0 │  0 │
    └────┴────┴────┘

Access:

matrix[0][1]

means:

row 0
column 1


---

32. Is int[][] Really a Matrix Internally?

Conceptually, Java treats it as:

> An array whose elements are themselves arrays.



That's why Java can have different row lengths:

int[][] a = new int[2][];

a[0] = new int[3];
a[1] = new int[5];

So:

row 0 → [ ][ ][ ]

row 1 → [ ][ ][ ][ ][ ]

This is called a jagged/ragged array.


---

33. Array + Enhanced for

int[] a = {10, 20, 30};

for (int x : a) {
    System.out.println(x);
}

Read:

> For each element x in array a.



But remember:

for (int x : a) {
    x = 100;
}

doesn't change the array elements.

If you want to modify them:

for (int i = 0; i < a.length; i++) {
    a[i] = 100;
}


---

34. Most Important Difference: Index vs Value

Normal loop:

for (int i = 0; i < a.length; i++) {
    System.out.println(a[i]);
}

Here:

i       → index
a[i]    → value

Enhanced loop:

for (int x : a) {
    System.out.println(x);
}

Here:

x → current value

This distinction is extremely important.


---

35. Can Array Length Be Negative?

No.

This:

new int[-5]

is invalid at runtime and results in:

NegativeArraySizeException


---

36. Can Array Length Be Zero?

Yes:

new int[0]

is completely valid.


---

37. Can Array Length Be a Variable?

Yes.

int n = 5;

int[] a = new int[n];

Now:

a.length = 5

The size is determined when the array is created.


---

38. Can Array Length Be Changed Later?

No.

int n = 5;
int[] a = new int[n];

n = 10;

Does a.length become 10?

No ❌

It remains:

5

Because changing n doesn't resize the already-created array.


---

39. The Ultimate Array Formula

For any array:

length
                       ↓
          ┌────┬────┬────┬────┐
          │    │    │    │    │
          └────┴────┴────┴────┘
             0    1    2    3
             ↑             ↑
          first          last
          index          index

Therefore:

First index = 0
Last index  = length - 1

And valid index condition:

0 <= index < length

🔥 If you understand this formula, most array indexing doubts disappear.


---

💥 DOUBT-KILLER RAPID FIRE

Doubt	Answer

What is an array?	Fixed-size collection of same-type elements
Is array a primitive?	❌ No
Is array an object?	✅ Yes
Is array variable a reference?	✅ Yes
First index?	0
Last index?	length - 1
Number of elements?	array.length
length() for array?	❌ No
Can elements be changed?	✅ Yes
Can length be changed?	❌ No
Can array length be 0?	✅ Yes
Can array length be negative?	❌ No
Invalid positive index?	ArrayIndexOutOfBoundsException
Invalid negative index?	ArrayIndexOutOfBoundsException
Accessing null array?	NullPointerException
Default int element?	0
Default boolean element?	false
Default object-reference element?	null
Does b = a copy the array?	❌ No, reference is copied
Can array contain objects?	✅ Yes
Can Java have multidimensional arrays?	✅ Yes
Are multidimensional arrays arrays of arrays?	✅ Yes
Dynamic-size alternative?	ArrayList



---

🧠 FINAL CONCEPT MAP

JAVA ARRAY
                              │
             ┌────────────────┼────────────────┐
             ↓                ↓                ↓
         Same Type        Fixed Size       Index Based
             │                │                │
             │                │                ↓
             │                │           starts at 0
             │                │                │
             │                │                ↓
             │                │          last = length-1
             │                │
             │                ↓
             │          cannot resize
             │
             ↓
     int[] / double[] /
     String[] / Student[]
             │
             ↓
       Array is an object
             │
             ↓
     Variable holds reference
             │
             ↓
        a[index]
             │
             ↓
       access element
             │
             ↓
        a.length
             │
             ↓
      number of elements

🏆 The one sentence to memorize

> An array in Java is an object containing a fixed number of elements of the same component type, accessed using zero-based indexes, with the number of elements obtained through the length field.
