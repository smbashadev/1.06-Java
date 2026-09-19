2-Dimensional Array in Java — DEEPDIVE

A 2-Dimensional Array is one of the most important array concepts in Java because it introduces the idea of rows, columns, nested loops, and jagged arrays.


---

1. What Is a 2-Dimensional Array?

A 2-D array stores data in a structure that we commonly visualize as a table:

Columns
           0    1    2
        ┌────┬────┬────┐
Row 0   │ 10 │ 20 │ 30 │
        ├────┼────┼────┤
Row 1   │ 40 │ 50 │ 60 │
        ├────┼────┼────┤
Row 2   │ 70 │ 80 │ 90 │
        └────┴────┴────┘

To identify one element, we need two indexes:

a[row][column]

For example:

a[1][2]

means:

> Row 1, Column 2



and gives:

60


---

2. Why Is It Called 2-Dimensional?

Because two indexes are required to locate an element.

1-D array

a[2]

One index.

2-D array

a[2][3]

Two indexes.

Conceptually:

1-D
   ↓
a[i]

2-D
   ↓
a[i][j]


---

3. Declaration of a 2-D Array

The most common syntax is:

int[][] a;

You may also see:

int a[][];

and:

int[] a[];

All three are valid Java syntax.

However, for clarity and readability, prefer:

int[][] a;


---

4. Declaration Does Not Create the Array

This is important.

int[][] a;

At this point, we have only declared a reference variable.

We haven't created the array object yet.

Think:

a
↓
reference variable

To create the array:

a = new int[3][4];

Now the array exists.


---

5. What Does new int[3][4] Mean?

Consider:

int[][] a = new int[3][4];

This means:

3 rows
×
4 columns

So there are:

3 × 4 = 12

elements.

Visual representation:

0    1    2    3
    ┌────┬────┬────┬────┐
 0  │  0 │  0 │  0 │  0 │
    ├────┼────┼────┼────┤
 1  │  0 │  0 │  0 │  0 │
    ├────┼────┼────┼────┤
 2  │  0 │  0 │  0 │  0 │
    └────┴────┴────┴────┘

Because the array is an int array, the default value is 0.


---

6. Understanding the Two Indexes

Suppose:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60},
    {70, 80, 90}
};

Visualize:

Column
          0    1    2
       ┌────┬────┬────┐
Row 0  │ 10 │ 20 │ 30 │
       ├────┼────┼────┤
Row 1  │ 40 │ 50 │ 60 │
       ├────┼────┼────┤
Row 2  │ 70 │ 80 │ 90 │
       └────┴────┴────┘

Now:

a[0][0] → 10
a[0][1] → 20
a[0][2] → 30

a[1][0] → 40
a[1][1] → 50
a[1][2] → 60

a[2][0] → 70
a[2][1] → 80
a[2][2] → 90

Golden rule:

a[i][j]

i → row
j → column


---

7. Accessing an Element

Example:

System.out.println(a[1][2]);

Output:

60

Break it down:

a[1][2]

1 → Row 1
2 → Column 2

Therefore:

Row 1 → 40 50 60
                 ↑
              Column 2

Answer = 60


---

8. Modifying an Element

Array elements can be changed.

a[1][2] = 600;

Before:

40  50  60

After:

40  50  600

So:

System.out.println(a[1][2]);

prints:

600


---

9. Initialization During Declaration

The easiest way to initialize a 2-D array is:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60},
    {70, 80, 90}
};

Each inner {...} represents a row.

{10,20,30} → Row 0
{40,50,60} → Row 1
{70,80,90} → Row 2


---

10. Creation First, Initialization Later

You can also create the array first:

int[][] a = new int[3][3];

Then assign values:

a[0][0] = 10;
a[0][1] = 20;
a[0][2] = 30;

a[1][0] = 40;
a[1][1] = 50;
a[1][2] = 60;

This is equivalent in concept to initializing the array directly.


---

11. Understanding length

This is one of the most important areas.

Suppose:

int[][] a = new int[3][4];

Then:

a.length

gives:

3

because there are 3 rows.

But:

a[0].length

gives:

4

because row 0 contains 4 elements.

So:

a.length
    ↓
number of rows

a[i].length
    ↓
number of elements in row i


---

12. Why Do We Need a[i].length?

You might see:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        System.out.println(a[i][j]);
    }
}

Why not:

j < a.length

Because the number of rows and number of columns are different concepts.

For:

int[][] a = new int[3][5];

we have:

a.length     = 3
a[0].length  = 5

Therefore:

i → uses a.length
j → uses a[i].length


---

13. Why Are Nested Loops Used?

A 2-D array has:

Rows
  +
Columns

So we need:

Outer loop → rows
Inner loop → columns

Example:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        System.out.print(a[i][j] + " ");
    }

    System.out.println();
}

Think:

Outer loop
   ↓
Row

Inner loop
   ↓
Columns within that row


---

14. Trace the Nested Loop

Suppose:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60}
};

The loop executes like this:

i = 0
    j = 0 → a[0][0] → 10
    j = 1 → a[0][1] → 20
    j = 2 → a[0][2] → 30

i = 1
    j = 0 → a[1][0] → 40
    j = 1 → a[1][1] → 50
    j = 2 → a[1][2] → 60

Output:

10 20 30
40 50 60


---

15. Complete Traversal Program

class TwoDArray {

    public static void main(String[] args) {

        int[][] a = {
            {10, 20, 30},
            {40, 50, 60},
            {70, 80, 90}
        };

        for (int i = 0; i < a.length; i++) {

            for (int j = 0; j < a[i].length; j++) {

                System.out.print(a[i][j] + " ");
            }

            System.out.println();
        }
    }
}

Output:

10 20 30
40 50 60
70 80 90


---

16. Enhanced for Loop

You can also traverse a 2-D array using enhanced for loops:

for (int[] row : a) {

    for (int value : row) {

        System.out.print(value + " ");
    }

    System.out.println();
}

Notice something important:

for (int[] row : a)

The type is:

int[]

because each element of the outer array is itself an int[].

This leads to an important Java concept.


---

17. Is a 2-D Array Really an Array of Arrays?

Yes.

This is one of the most important concepts in Java.

When you write:

int[][] a;

you can understand it as:

array
  ↓
contains references to
  ↓
int[] arrays

Conceptually:

a
 ↓
┌────────┬────────┬────────┐
│ row 0  │ row 1  │ row 2  │
└────────┴────────┴────────┘
    ↓        ↓        ↓
 [10,20]  [30,40]  [50,60]

So Java's 2-D arrays are technically arrays whose elements are arrays.


---

18. This Explains Jagged Arrays

Because a 2-D array is an array of arrays, different rows can have different lengths.

Example:

int[][] a = new int[3][];

a[0] = new int[2];
a[1] = new int[4];
a[2] = new int[3];

Now:

Row 0 → [ ][ ]
Row 1 → [ ][ ][ ][ ]
Row 2 → [ ][ ][ ]

This is called a jagged array or ragged array.


---

19. Jagged Array Example

int[][] a = {
    {10, 20},
    {30, 40, 50, 60},
    {70, 80, 90}
};

Visual:

Row 0 → 10 20
Row 1 → 30 40 50 60
Row 2 → 70 80 90

Here:

a.length       → 3
a[0].length    → 2
a[1].length    → 4
a[2].length    → 3

This is why the safest nested-loop condition is:

j < a[i].length

rather than assuming every row has the same number of columns.


---

20. Can We Create a Jagged Array Directly?

Yes:

int[][] a = new int[3][];

a[0] = new int[2];
a[1] = new int[4];
a[2] = new int[3];

Notice:

new int[3][]

We specified the number of rows but did not specify the row lengths.

Then each row is created separately.


---

21. Can We Leave a Row null?

Yes.

Example:

int[][] a = new int[3][];

a[0] = new int[2];
a[1] = null;
a[2] = new int[3];

Conceptually:

a
 ↓
[row 0] → [ ][ ]
[row 1] → null
[row 2] → [ ][ ][ ]

If you try:

a[1].length

you'll get:

NullPointerException

because row 1 doesn't refer to an array.


---

22. Default Values in 2-D Arrays

For:

int[][] a = new int[2][3];

all elements initially contain 0.

0 0 0
0 0 0

For:

String[][] names = new String[2][3];

the initial values are:

null null null
null null null

because String is a reference type.


---

23. Finding the Sum of All Elements

Example:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60}
};

int sum = 0;

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        sum = sum + a[i][j];
    }
}

System.out.println("Sum = " + sum);

Output:

Sum = 210

Calculation:

10 + 20 + 30 + 40 + 50 + 60 = 210


---

24. Find the Largest Element

int[][] a = {
    {10, 80, 30},
    {40, 20, 60},
    {70, 50, 90}
};

int largest = a[0][0];

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        if (a[i][j] > largest) {
            largest = a[i][j];
        }
    }
}

System.out.println("Largest = " + largest);

Output:

Largest = 90


---

25. Row-Wise Sum

Suppose:

10 20 30
40 50 60
70 80 90

To calculate the sum of each row:

for (int i = 0; i < a.length; i++) {

    int sum = 0;

    for (int j = 0; j < a[i].length; j++) {
        sum += a[i][j];
    }

    System.out.println("Row " + i + " = " + sum);
}

Output:

Row 0 = 60
Row 1 = 150
Row 2 = 240

The important point is that:

int sum = 0;

is placed inside the outer loop, so it resets for every row.


---

26. Column-Wise Processing

For a rectangular array:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60},
    {70, 80, 90}
};

We can process columns:

for (int j = 0; j < a[0].length; j++) {

    int sum = 0;

    for (int i = 0; i < a.length; i++) {
        sum += a[i][j];
    }

    System.out.println("Column " + j + " = " + sum);
}

Output:

Column 0 = 120
Column 1 = 150
Column 2 = 180


---

27. Matrix Addition

Two matrices of the same dimensions can be added.

int[][] a = {
    {1, 2},
    {3, 4}
};

int[][] b = {
    {5, 6},
    {7, 8}
};

int[][] c = new int[2][2];

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        c[i][j] = a[i][j] + b[i][j];
    }
}

Result:

6  8
10 12

The rule is:

c[i][j] = a[i][j] + b[i][j]


---

28. Transpose of a Matrix

Given:

1 2 3
4 5 6

Transpose:

1 4
2 5
3 6

Rows become columns.

For a rectangular matrix:

int[][] a = {
    {1, 2, 3},
    {4, 5, 6}
};

int[][] transpose = new int[3][2];

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        transpose[j][i] = a[i][j];
    }
}

The key statement:

transpose[j][i] = a[i][j];


---

29. Important Difference: 2-D Array vs Matrix

In mathematics, a matrix is generally rectangular.

Java's int[][] is technically an array of arrays, so Java allows:

10 20
30 40 50
60

This is valid Java.

Therefore:

> Every rectangular matrix can be represented using a Java 2-D array, but a Java 2-D array does not have to be rectangular.




---

30. Common Mistake — Wrong Column Length

Consider:

int[][] a = new int[3][4];

Some beginners write:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a.length; j++) {
        System.out.println(a[i][j]);
    }
}

This happens to work only because both values are 3 and 4? Actually, it does not correctly visit all columns: a.length is 3, so column 3 is skipped.

Correct:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {
        System.out.println(a[i][j]);
    }
}


---

31. Common Mistake — Using the Wrong Index

Suppose:

int[][] a = {
    {10, 20},
    {30, 40}
};

This:

a[0][1]

means:

Row 0
Column 1
→ 20

Not:

Row 1
Column 0

which would be:

a[1][0]

and gives:

30


---

32. Common Mistake — a.length Is Not Total Elements

For:

int[][] a = new int[3][4];

many beginners think:

a.length == 12

❌ Wrong.

a.length == 3

It represents the number of rows.

Total elements in a rectangular array:

3 × 4 = 12

But for a jagged array, there isn't necessarily one simple rows × columns formula.


---

33. 1-D vs 2-D

Feature	1-D	2-D

Declaration	int[] a	int[][] a
Indexes	1	2
Access	a[i]	a[i][j]
Structure	Sequence	Rows + columns
Traversal	Usually one loop	Usually nested loops
Length	a.length	a.length, a[i].length
Example	[10,20,30]	[[10,20],[30,40]]



---

34. The Most Important Mental Model

Don't think of:

int[][] a

as one giant rectangular box.

Think:

a
                 ↓
       ┌─────────┼─────────┐
       ↓         ↓         ↓
    row 0      row 1      row 2
       ↓         ↓         ↓
   [10 20]   [30 40]   [50 60]

This explains:

a.length

as:

> How many row arrays are there?



And:

a[i].length

as:

> How many elements are in row i?




---

35. Final Deep-Dive Summary

2-D ARRAY
                        │
                        ↓
               Array of Arrays
                        │
             ┌──────────┴──────────┐
             ↓                     ↓
           ROWS                 COLUMNS
             │                     │
       a.length              a[i].length
             │                     │
             └──────────┬──────────┘
                        ↓
                   a[i][j]
                        │
                  ┌─────┴─────┐
                  ↓           ↓
                i = row     j = column

Memorize these rules:

1. int[][] a
       → 2-D array reference

2. new int[3][4]
       → 3 rows, 4 columns

3. a.length
       → number of rows

4. a[i].length
       → number of elements in row i

5. a[i][j]
       → row i, column j

6. Index starts at 0

7. Nested loops
       → outer loop = rows
       → inner loop = columns

8. Java 2-D arrays are technically
   arrays of arrays.

9. Because rows are separate arrays,
   Java supports jagged arrays.

10. Invalid index
       → ArrayIndexOutOfBoundsException

⭐ One sentence to remember

> A Java 2-D array is an array whose elements are themselves arrays, and an element is accessed using a[row][column].
