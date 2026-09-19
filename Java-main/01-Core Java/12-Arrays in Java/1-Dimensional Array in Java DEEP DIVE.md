1-Dimensional Array in Java — DEEP DIVE 🔥

A 1-Dimensional Array is one of the most fundamental concepts in Java. Once you understand it properly, 2-D arrays, searching, sorting, ArrayList, and many data-structure concepts become much easier.


---

1. What Is a 1-Dimensional Array?

A 1-D array stores multiple elements of the same component type in a single sequence.

int[] marks = {80, 90, 70, 60, 85};

Conceptually:

marks
  ↓
┌────┬────┬────┬────┬────┐
│ 80 │ 90 │ 70 │ 60 │ 85 │
└────┴────┴────┴────┴────┘
   0    1    2    3    4

Each element is identified using one index.

That's why it is called 1-dimensional.

Definition

> A 1-Dimensional array is an array whose elements are arranged in a single sequence and are accessed using one index.




---

2. Why Do We Need a 1-D Array?

Suppose you have marks of 5 students.

Without an array:

int mark1 = 80;
int mark2 = 90;
int mark3 = 70;
int mark4 = 60;
int mark5 = 85;

This becomes inconvenient when there are 1,000 students.

With an array:

int[] marks = {80, 90, 70, 60, 85};

One reference gives access to all the values.

marks
 ↓
[80][90][70][60][85]


---

3. Why Is It Called "1-Dimensional"?

Compare these:

1-D

[10][20][30][40]

You need one index:

a[2]

2-D

[10][20][30]
[40][50][60]

You need two indexes:

a[1][2]

So:

1-D → a[i]
2-D → a[i][j]

The number of indexes required to locate an element corresponds to the array's dimension.


---

4. Declaration

The preferred syntax is:

int[] marks;

You can also write:

int marks[];

Both are legal Java.

But:

int[] marks;

is generally clearer because the [] visibly belongs to the array type.


---

5. What Actually Happens During Declaration?

Consider:

int[] marks;

At this point:

marks is a reference variable.

No array object has been created yet.


Conceptually:

marks
  ↓
reference
  ↓
no array object yet

You haven't allocated the array.


---

6. Creation

Now:

marks = new int[5];

or:

int[] marks = new int[5];

The new operator creates an array object.

Conceptually:

marks
  ↓
┌────┬────┬────┬────┬────┐
│  0 │  0 │  0 │  0 │  0 │
└────┴────┴────┴────┴────┘
   0    1    2    3    4


---

7. What Does new int[5] Actually Mean?

It means:

> Create an array capable of containing 5 int elements.



It does not mean:

last index = 5

Instead:

number of elements = 5
indexes = 0, 1, 2, 3, 4
last index = 4

Formula

last index = length - 1


---

8. Initialization

You can initialize directly:

int[] marks = {80, 90, 70, 60, 85};

Java determines the size automatically.

There are 5 values, so:

marks.length

is:

5

Indexes:

0 1 2 3 4


---

9. Explicit Array Creation + Initialization

You can also write:

int[] marks = new int[]{80, 90, 70, 60, 85};

This is equivalent in effect to:

int[] marks = {80, 90, 70, 60, 85};

But there's an important syntax difference when assigning a new array later.

This is invalid:

marks = {10, 20, 30};   // ❌

Use:

marks = new int[]{10, 20, 30};   // ✅


---

10. Default Values

Suppose:

int[] a = new int[5];

You haven't assigned values.

Java automatically initializes every element to the default value for the component type:

[0][0][0][0][0]

For common types:

Component type	Default

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

11. Accessing Elements

Suppose:

int[] marks = {80, 90, 70, 60, 85};

Access using:

marks[index]

Examples:

System.out.println(marks[0]);
System.out.println(marks[2]);
System.out.println(marks[4]);

Output:

80
70
85


---

12. Understanding the Index

Value:    80    90    70    60    85
          ↓     ↓     ↓     ↓     ↓
Index:     0     1     2     3     4

So:

marks[0] → 80
marks[1] → 90
marks[2] → 70
marks[3] → 60
marks[4] → 85

The index is not the value.

This distinction is extremely important.

index → position
value → data stored at that position


---

13. Why Does Index Start at 0?

Java uses zero-based indexing.

You don't need to memorize a complicated reason to use arrays correctly. Just remember:

First element → index 0
Second element → index 1
Third element → index 2

Therefore:

n elements → indexes 0 through n-1


---

14. Modifying an Element

An array is mutable.

Suppose:

int[] marks = {80, 90, 70};

Change the second element:

marks[1] = 95;

Now:

Before:
[80][90][70]

After:
[80][95][70]


---

15. Array Length

Use:

marks.length

Example:

int[] marks = {80, 90, 70, 60};

System.out.println(marks.length);

Output:

4

Remember:

marks.length       // ✅
marks.length()     // ❌

For an array, length is a field, not a method.


---

16. length vs Last Index

Suppose:

int[] a = {10, 20, 30, 40};

Then:

a.length = 4

but:

last index = 3

because:

last index = length - 1
            = 4 - 1
            = 3

Therefore:

a[a.length - 1]

means:

a[3]

which gives:

40


---

17. Valid Index Rule

For an array of length n:

0 <= index < n

For example:

int[] a = new int[5];

Valid:

0
1
2
3
4

Invalid:

-1
5
6
100


---

18. ArrayIndexOutOfBoundsException

Example:

int[] a = {10, 20, 30};

System.out.println(a[3]);

The length is 3.

Valid indexes:

0, 1, 2

3 is invalid.

Java throws:

ArrayIndexOutOfBoundsException

Similarly:

a[-1]

is invalid.


---

19. Traversing the Array

Traversal means visiting each element one by one.

Normal for loop

int[] a = {10, 20, 30, 40};

for (int i = 0; i < a.length; i++) {
    System.out.println(a[i]);
}

Output:

10
20
30
40


---

20. Why i < a.length?

Suppose:

a.length = 4

Then:

i = 0 → a[0]
i = 1 → a[1]
i = 2 → a[2]
i = 3 → a[3]
i = 4 → stop

At i = 4:

4 < 4 → false

This prevents an invalid access.


---

21. Why Not i <= a.length?

If:

for (int i = 0; i <= a.length; i++)

then eventually:

i = 4

for a length-4 array.

Java attempts:

a[4]

But the last valid index is:

3

Result:

ArrayIndexOutOfBoundsException


---

22. Enhanced for Loop

Java provides a simpler syntax:

int[] a = {10, 20, 30, 40};

for (int value : a) {
    System.out.println(value);
}

Read it as:

> For each value in a.



Here value represents the current element.


---

23. Normal for vs Enhanced for

Normal

for (int i = 0; i < a.length; i++) {
    System.out.println(a[i]);
}

You have:

i   → index
a[i] → value

Enhanced

for (int value : a) {
    System.out.println(value);
}

You have:

value → current element

Rule

Need index?
   ↓
normal for

Need values only?
   ↓
enhanced for


---

24. Complete 1-D Array Program

class OneDArrayDemo {

    public static void main(String[] args) {

        int[] marks = {80, 90, 70, 60, 85};

        System.out.println("Array length = " + marks.length);

        for (int i = 0; i < marks.length; i++) {
            System.out.println(
                "Index " + i + " = " + marks[i]
            );
        }
    }
}

Output:

Array length = 5
Index 0 = 80
Index 1 = 90
Index 2 = 70
Index 3 = 60
Index 4 = 85


---

25. Calculating Sum

Arrays become useful when combined with loops.

class SumArray {

    public static void main(String[] args) {

        int[] numbers = {10, 20, 30, 40};

        int sum = 0;

        for (int i = 0; i < numbers.length; i++) {
            sum = sum + numbers[i];
        }

        System.out.println("Sum = " + sum);
    }
}

Output:

Sum = 100

Flow:

sum = 0
 ↓
0 + 10 = 10
 ↓
10 + 20 = 30
 ↓
30 + 30 = 60
 ↓
60 + 40 = 100


---

26. Finding the Largest Element

class LargestElement {

    public static void main(String[] args) {

        int[] numbers = {40, 10, 90, 30, 70};

        int largest = numbers[0];

        for (int i = 1; i < numbers.length; i++) {

            if (numbers[i] > largest) {
                largest = numbers[i];
            }
        }

        System.out.println("Largest = " + largest);
    }
}

Output:

Largest = 90

Notice the important idea:

int largest = numbers[0];

We start with an actual array element rather than assuming a value such as 0.


---

27. Searching an Element

A simple linear search:

class SearchArray {

    public static void main(String[] args) {

        int[] numbers = {10, 20, 30, 40, 50};

        int search = 30;

        for (int i = 0; i < numbers.length; i++) {

            if (numbers[i] == search) {
                System.out.println("Found at index " + i);
                break;
            }
        }
    }
}

Output:

Found at index 2

The important concept:

array
 ↓
check each element
 ↓
compare with target
 ↓
found → use its index


---

28. Array Is an Object

This is an important Java concept.

int[] a = new int[5];

a is a reference variable.

The array itself is an object.

Conceptually:

a
       ↓
   reference
       ↓
┌────┬────┬────┬────┬────┐
│  0 │  0 │  0 │  0 │  0 │
└────┴────┴────┴────┴────┘


---

29. Reference Assignment

Consider:

int[] a = {10, 20, 30};

int[] b = a;

A common misconception is:

> "Java created a second array."



❌ No.

Both references refer to the same array.

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

30. Empty Array vs null

These are different.

Empty array

int[] a = new int[0];

An array object exists.

length = 0

null

int[] a = null;

The reference doesn't currently refer to an array object.

Trying:

a.length

causes:

NullPointerException

Remember:

> Zero-length array exists; null array reference does not refer to an array object.




---

31. Can a 1-D Array Store Objects?

Yes.

String[] names = {"Ali", "Ravi", "John"};

Or:

Student[] students = new Student[3];

Initially:

[null][null][null]

because the elements are references.


---

32. Can We Store Different Primitive Types?

No.

This:

int[] a = {10, 20, 30};

cannot contain a double or String.

For example:

a[0] = 10.5;     // ❌
a[1] = "Java";   // ❌

The array's component type is int.


---

33. Is the Size Really Fixed?

Yes.

int[] a = new int[5];

This particular array has exactly 5 elements.

You cannot make that same object have 10 elements.

You can assign a new array:

a = new int[10];

But now a refers to a different array object.

Old:
a → [ ][ ][ ][ ][ ]

After:
a → [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]


---

34. 1-D Array vs ArrayList

Feature	1-D Array	ArrayList

Size	Fixed	Dynamically resizable
Access	a[i]	list.get(i)
Size	a.length	list.size()
Primitive storage	Direct	Uses wrapper types
Syntax	Simple	More collection-oriented
Best for	Fixed-size data	Changing-size data



---

35. Advanced: Array Type and Component Type

If:

int[] a;

the component type is:

int

If:

String[] names;

the component type is:

String

If:

Student[] students;

the component type is:

Student

So:

int[]       → component type = int
String[]    → component type = String
Student[]   → component type = Student


---

36. Advanced: Arrays Know Their Length

An array object stores its own length information.

That's why:

a.length

can tell you the number of elements without you separately storing the size.

You don't write:

int size = 5;

just to know the array's size.

The array already knows its length.


---

37. Advanced: Runtime Type

Arrays are runtime objects with a specific array type.

For example:

int[] a = new int[5];

is an array object whose component type is int.

Likewise:

String[] s = new String[5];

is a different array type.

This becomes important when you study:

inheritance

polymorphism

Object

exceptions

reflection



---

38. Advanced: Array Covariance

This is a more advanced Java feature.

Suppose:

String[] names = new String[3];

Object[] objects = names;

This is allowed because String is a subtype of Object.

Conceptually:

String[]
   ↓
Object[]

But:

objects[0] = Integer.valueOf(10);

causes:

ArrayStoreException

because the actual array is still a String[].

This is an advanced topic, but it explains why arrays carry runtime type information.


---

39. 1-D Array and Memory — Conceptual View

At a high level:

int[] a = {10, 20, 30};

creates an array object somewhere in Java's managed memory, while a stores a reference to that object.

Conceptually:

Stack/reference context        Heap
────────────────────          ─────────────
a ─────────────────────────→  [10][20][30]

This is a conceptual model rather than a promise about the JVM's exact implementation details.


---

40. Common Interview Traps

Trap 1

int[] a = new int[5];

System.out.println(a.length);

Answer:

5

Not 4.


---

Trap 2

System.out.println(a[5]);

For length 5?

❌ Invalid.

Last index is:

4


---

Trap 3

int[] a = null;

System.out.println(a.length);

Result:

NullPointerException


---

Trap 4

int[] a = new int[0];

System.out.println(a.length);

Result:

0

No exception just for checking its length.


---

Trap 5

int[] a = {10, 20, 30};
int[] b = a;

b[1] = 100;

System.out.println(a[1]);

Output:

100

Because a and b refer to the same array.


---

🧠 THE COMPLETE 1-D ARRAY FLOW

1-D ARRAY
                    │
                    ↓
             Declare reference
                    │
                    ↓
              Create object
                    │
                    ↓
           Initialize elements
                    │
                    ↓
             Access using [i]
                    │
                    ↓
             Modify using [i]
                    │
                    ↓
              Traverse with loop
                    │
                    ↓
             Use array.length
                    │
                    ↓
        Search / Sum / Max / Min


---

🔥 1-D ARRAY MASTER FORMULA

Memorize these five things:

int[] a = new int[5];

means:

a
                 ↓
        ┌────┬────┬────┬────┬────┐
        │  0 │  0 │  0 │  0 │  0 │
        └────┴────┴────┴────┴────┘
          0    1    2    3    4

Therefore:

Number of elements → a.length → 5
First index        → 0
Last index         → a.length - 1 → 4
Access             → a[index]
Valid index        → 0 <= index < a.length

🏆 Final Definition

> A 1-Dimensional array in Java is an array object containing a fixed number of elements of the same component type, arranged in a single sequence and accessed using one zero-based index.
