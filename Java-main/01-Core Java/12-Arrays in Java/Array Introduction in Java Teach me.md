Arrays Introduction in Java — TEACH ME 👨‍🏫

Let's learn arrays from zero, as if you're seeing them for the first time. I'll explain the idea first, then the syntax, then the program, and finally the common doubts.


---

LEVEL 1 — Understand the Idea

1. Imagine a Student Marks Problem

Suppose you have 5 students:

Student 1 → 80
Student 2 → 75
Student 3 → 90
Student 4 → 65
Student 5 → 88

One way is to create 5 variables:

int mark1 = 80;
int mark2 = 75;
int mark3 = 90;
int mark4 = 65;
int mark5 = 88;

But this becomes difficult if you have 100 or 1,000 students.

Java gives us arrays.

int[] marks = {80, 75, 90, 65, 88};

Now one array stores all 5 marks.

Think of an array as a row of boxes:

marks
         ↓
┌────┬────┬────┬────┬────┐
│ 80 │ 75 │ 90 │ 65 │ 88 │
└────┴────┴────┴────┴────┘
   0    1    2    3    4
       INDEX

Each box contains one value.


---

2. What Is an Array?

Remember this definition:

> An array is an object that stores a fixed number of elements of the same type and accesses them using indexes.



There are 4 important words:

ARRAY
 ↓
Multiple elements
 ↓
Same type
 ↓
Fixed size
 ↓
Index-based access


---

3. Why Does Index Start at 0?

This is one of the first things you must memorize.

For:

int[] marks = {80, 75, 90, 65, 88};

the indexes are:

Value:   80   75   90   65   88
         ↓    ↓    ↓    ↓    ↓
Index:    0    1    2    3    4

So:

marks[0]   → 80
marks[1]   → 75
marks[2]   → 90
marks[3]   → 65
marks[4]   → 88

Golden rule 🏆

> First index = 0



> Last index = length - 1




---

LEVEL 2 — Learn the Syntax

4. Array Declaration

First, you can declare an array reference:

int[] marks;

Read this as:

> marks can refer to an array of integers.



At this point, you haven't created the array yet.


---

5. Create the Array

Now:

marks = new int[5];

Or combine both:

int[] marks = new int[5];

This creates space for 5 integers.

Think:

marks
  ↓
┌────┬────┬────┬────┬────┐
│  0 │  0 │  0 │  0 │  0 │
└────┴────┴────┴────┴────┘
   0    1    2    3    4

Why are they all 0?

Because Java gives int array elements their default value of 0.


---

6. Give Values to the Array

You can assign values using indexes:

int[] marks = new int[5];

marks[0] = 80;
marks[1] = 75;
marks[2] = 90;
marks[3] = 65;
marks[4] = 88;

Now:

┌────┬────┬────┬────┬────┐
│ 80 │ 75 │ 90 │ 65 │ 88 │
└────┴────┴────┴────┴────┘
   0    1    2    3    4


---

7. Shortcut: Declare and Initialize Together

Instead of writing:

int[] marks = new int[5];

marks[0] = 80;
marks[1] = 75;
marks[2] = 90;
marks[3] = 65;
marks[4] = 88;

you can simply write:

int[] marks = {80, 75, 90, 65, 88};

Much easier!

Java automatically understands:

Number of elements = 5


---

8. How Do We Read an Array Element?

Use:

arrayName[index]

Example:

int[] marks = {80, 75, 90, 65, 88};

System.out.println(marks[0]);

Output:

80

Because:

marks[0]
   ↓
first element
   ↓
80


---

9. How Do We Change an Element?

Suppose:

int[] marks = {80, 75, 90};

We want to change 75 to 85.

Use:

marks[1] = 85;

Now:

Before:
80  75  90

After:
80  85  90

So array elements are modifiable.


---

10. How Do We Know the Array Size?

Use:

marks.length

Example:

int[] marks = {80, 75, 90, 65, 88};

System.out.println(marks.length);

Output:

5

Important ⚠️

For an array:

marks.length

NOT:

marks.length()


---

LEVEL 3 — Use Arrays with Loops

Now arrays become really powerful.

Suppose:

int[] marks = {80, 75, 90, 65, 88};

We want to print every mark.

We could write:

System.out.println(marks[0]);
System.out.println(marks[1]);
System.out.println(marks[2]);
System.out.println(marks[3]);
System.out.println(marks[4]);

But that's repetitive.

Instead use a loop.


---

11. Array + for Loop

int[] marks = {80, 75, 90, 65, 88};

for (int i = 0; i < marks.length; i++) {
    System.out.println(marks[i]);
}

Output:

80
75
90
65
88

Let's understand the loop.

First iteration:

i = 0
marks[0] → 80

Second:

i = 1
marks[1] → 75

Third:

i = 2
marks[2] → 90

Fourth:

i = 3
marks[3] → 65

Fifth:

i = 4
marks[4] → 88

Then:

i = 5

5 < marks.length
5 < 5
false

Loop stops.


---

12. Why i < marks.length?

Suppose:

int[] marks = new int[5];

Then:

length = 5

Valid indexes:

0  1  2  3  4

Therefore:

i < 5

is correct.

If you write:

i <= marks.length

eventually:

i = 5

and Java tries:

marks[5]

❌ Invalid!


---

13. Enhanced for Loop

Java also provides a simpler way to read every element.

int[] marks = {80, 75, 90, 65, 88};

for (int mark : marks) {
    System.out.println(mark);
}

Read it as:

> For each mark in marks, print mark.



Output:

80
75
90
65
88


---

14. Normal for vs Enhanced for

Normal for

for (int i = 0; i < marks.length; i++) {
    System.out.println(marks[i]);
}

Use when you need the index.

Enhanced for

for (int mark : marks) {
    System.out.println(mark);
}

Use when you only need the values.

Easy memory trick:

Need INDEX?
    ↓
normal for

Need VALUE only?
    ↓
enhanced for


---

15. Let's Build a Real Program

class ArrayDemo {

    public static void main(String[] args) {

        int[] marks = {80, 75, 90, 65, 88};

        System.out.println("Number of students: " + marks.length);

        for (int i = 0; i < marks.length; i++) {
            System.out.println("Mark = " + marks[i]);
        }
    }
}

Output:

Number of students: 5
Mark = 80
Mark = 75
Mark = 90
Mark = 65
Mark = 88


---

16. Let's Calculate the Total

Now let's use the array for something useful.

class ArrayDemo {

    public static void main(String[] args) {

        int[] marks = {80, 75, 90, 65, 88};

        int total = 0;

        for (int i = 0; i < marks.length; i++) {
            total = total + marks[i];
        }

        System.out.println("Total = " + total);
    }
}

Output:

Total = 398

The important idea is:

total = 0

+ 80 → 80
+ 75 → 155
+ 90 → 245
+ 65 → 310
+ 88 → 398

This is one of the most common uses of arrays + loops.


---

17. Find the Average

class ArrayDemo {

    public static void main(String[] args) {

        int[] marks = {80, 75, 90, 65, 88};

        int total = 0;

        for (int mark : marks) {
            total = total + mark;
        }

        double average = (double) total / marks.length;

        System.out.println("Average = " + average);
    }
}

Output:

Average = 79.6

Notice:

(double) total

This ensures decimal division.


---

18. Default Values — Very Important

Suppose:

int[] a = new int[3];

You haven't assigned anything.

Java gives:

[0][0][0]

For different types:

Data Type	Default Value

int	0
long	0L
float	0.0f
double	0.0
char	'\u0000'
boolean	false
Reference types	null


Example:

boolean[] values = new boolean[3];

Initially:

false false false


---

19. Very Important: Array Is an Object

You may think:

int[] a;

is a primitive.

It isn't.

int is primitive.

But:

int[]

is an array type, and an array is an object.

Conceptually:

a
 ↓
reference
 ↓
ARRAY OBJECT

That's why you can do:

a.length


---

20. Array Reference Concept

Consider:

int[] a = {10, 20, 30};

Think:

a
       │
       │ reference
       ↓
 ┌────┬────┬────┐
 │ 10 │ 20 │ 30 │
 └────┴────┴────┘
   0    1    2

Now:

int[] b = a;

Does Java create another array?

No ❌

Now both references point to the same array:

a ─────┐
              ↓
          [10][20][30]
              ↑
       b ─────┘

Therefore:

b[0] = 100;

changes the same array.

So:

System.out.println(a[0]);

prints:

100


---

21. Common Mistake: Invalid Index

Suppose:

int[] a = {10, 20, 30};

Valid:

a[0]
a[1]
a[2]

Invalid:

a[3]

Java throws:

ArrayIndexOutOfBoundsException

Remember:

length = 3
last index = 3 - 1 = 2


---

22. Common Mistake: Forgetting new

This is wrong:

int[] a;

a[0] = 10;

Why?

You declared the reference but didn't create an array.

Correct:

int[] a = new int[3];

a[0] = 10;


---

23. Common Mistake: length()

Wrong:

a.length()

Correct:

a.length

Remember:

ARRAY  → length
STRING → length()


---

24. Can We Change the Array Size?

No.

Suppose:

int[] a = new int[5];

It always has 5 elements.

You cannot do something like:

"Add one more element to this same array"

Instead, you create another array.

If you need a collection whose size changes dynamically, Java provides ArrayList.

For now:

Array     → fixed size
ArrayList → dynamic size


---

25. Can an Array Store Objects?

Absolutely.

String[] names = {"Ali", "Ravi", "John"};

The array contains references to String objects.

You can also have:

Student[] students = new Student[5];

The initial values are:

null null null null null

because the array contains object references.


---

26. Can an Array Have Zero Elements?

Yes.

int[] a = new int[0];

This is a valid array.

length = 0

But there is no:

a[0]

because there are no elements.

Don't confuse this with:

int[] a = null;

These are different:

new int[0]
    ↓
array exists
    ↓
length = 0

null
    ↓
array does not exist


---

27. Multidimensional Arrays

Java also supports arrays of arrays.

Example:

int[][] matrix = new int[2][3];

Think of it as:

Column
       0  1  2

Row 0 [ 0  0  0 ]

Row 1 [ 0  0  0 ]

Access:

matrix[0][1]

means:

> Row 0, column 1.




---

28. The Big Picture

You now have this chain:

ARRAY
                  ↓
       Stores multiple values
                  ↓
           Same component type
                  ↓
             Fixed length
                  ↓
          Zero-based indexes
                  ↓
        Access using array[index]
                  ↓
           Use array.length
                  ↓
          Process with loops


---

🎯 QUICK TEST — Check Your Understanding

Question 1

int[] a = {10, 20, 30, 40};

What is:

a.length

Answer: 4


---

Question 2

What is the last valid index?

Answer:

4 - 1 = 3


---

Question 3

What is:

a[2]

Answer: 30


---

Question 4

Is this valid?

a[4]

Answer: ❌ No.

Valid indexes:

0, 1, 2, 3


---

Question 5

What happens here?

int[] a = new int[3];

System.out.println(a[0]);

Answer:

0

because the default value of int is 0.


---

Question 6

What is the difference?

a.length

and

a.length()

Answer:

a.length   → correct
a.length() → wrong for arrays


---

🧠 FINAL MEMORY FORMULA

If you remember only this, you can handle most beginner array questions:

int[] a = new int[5];

means:

a
                 ↓
       ┌───┬───┬───┬───┬───┐
       │ 0 │ 0 │ 0 │ 0 │ 0 │
       └───┴───┴───┴───┴───┘
         0   1   2   3   4

Therefore:

Number of elements = 5
First index         = 0
Last index          = 4
Last index formula  = length - 1

And:

a[0]

means get the first element,

while:

a.length

means get the number of elements.

⭐ One sentence to remember

> Array = fixed-size, same-type collection of elements accessed using zero-based indexes.
