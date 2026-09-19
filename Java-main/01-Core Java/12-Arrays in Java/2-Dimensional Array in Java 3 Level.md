2-Dimensional Array in Java — 3 LEVEL

🟢 LEVEL 1 — BEGINNER

1. What is a 2-Dimensional Array?

A 2-Dimensional Array stores elements in the form of rows and columns.

Example:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60},
    {70, 80, 90}
};

Think of it as:

Column
        0    1    2
     ┌────┬────┬────┐
 0   │ 10 │ 20 │ 30 │
     ├────┼────┼────┤
 1   │ 40 │ 50 │ 60 │
     ├────┼────┼────┤
 2   │ 70 │ 80 │ 90 │
     └────┴────┴────┘
Row

2. Accessing Elements

Syntax:

a[row][column]

Examples:

a[0][0] → 10
a[0][1] → 20
a[1][2] → 60
a[2][0] → 70

Golden Rule ⭐

a[i][j]

i → Row
j → Column


---

3. Declaration

int[][] a;

Other valid forms:

int a[][];
int[] a[];

Preferred:

int[][] a;


---

4. Creation

int[][] a = new int[3][4];

Means:

3 rows
4 columns

Total elements:

3 × 4 = 12


---

5. Traversal

A 2-D array is normally traversed using nested loops:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        System.out.print(a[i][j] + " ");
    }

    System.out.println();
}

Remember:

Outer loop → Rows
Inner loop → Columns


---

🟡 LEVEL 2 — INTERMEDIATE

1. Understanding length

For:

int[][] a = new int[3][4];

a.length

a.length

gives:

3

because there are 3 rows.

a[i].length

a[i].length

gives the number of elements in row i.

For a rectangular array:

a.length     → 3
a[0].length  → 4
a[1].length  → 4
a[2].length  → 4

Important:

a.length
    ↓
Number of rows

a[i].length
    ↓
Number of elements in row i


---

2. Complete Program

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

3. Sum of All Elements

int sum = 0;

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        sum += a[i][j];
    }
}

System.out.println(sum);

For:

10 20 30
40 50 60

Output:

210


---

4. Largest Element

int largest = a[0][0];

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        if (a[i][j] > largest) {
            largest = a[i][j];
        }
    }
}

System.out.println(largest);


---

5. Enhanced for Loop

A 2-D array can also be traversed using enhanced for:

for (int[] row : a) {

    for (int value : row) {

        System.out.print(value + " ");
    }

    System.out.println();
}

Why int[] row?

Because Java's 2-D array is technically an:

> Array of arrays.




---

🔴 LEVEL 3 — ADVANCED

1. Java 2-D Array = Array of Arrays

This is the most important advanced concept.

When we write:

int[][] a;

think:

a
 ↓
Array
 ↓
contains
 ↓
int[] arrays

Conceptually:

a
 ↓
┌────────┬────────┬────────┐
│ Row 0  │ Row 1  │ Row 2  │
└────────┴────────┴────────┘
    ↓        ↓        ↓
 [10,20] [30,40] [50,60]

Therefore, a Java 2-D array doesn't have to be rectangular.


---

2. Jagged Array

A jagged array has rows of different lengths.

int[][] a = {
    {10, 20},
    {30, 40, 50, 60},
    {70, 80, 90}
};

Structure:

Row 0 → 10 20
Row 1 → 30 40 50 60
Row 2 → 70 80 90

Therefore:

a.length       → 3
a[0].length    → 2
a[1].length    → 4
a[2].length    → 3

This is exactly why we generally write:

j < a[i].length

instead of assuming all rows have the same length.


---

3. Creating a Jagged Array Step by Step

int[][] a = new int[3][];

a[0] = new int[2];
a[1] = new int[4];
a[2] = new int[3];

Here:

3 → number of rows

2 → size of row 0
4 → size of row 1
3 → size of row 2


---

4. Matrix vs Java 2-D Array

A mathematical matrix is generally rectangular:

10 20 30
40 50 60
70 80 90

But Java allows:

10 20
30 40 50
60 70 80 90

because Java's 2-D array is an array of arrays.

So:

> A Java 2-D array can represent a matrix, but it doesn't have to be a rectangular matrix.




---

5. Row-Wise Processing

Given:

10 20 30
40 50 60
70 80 90

Find each row's sum:

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


---

6. Column-Wise Processing

For a rectangular array:

for (int j = 0; j < a[0].length; j++) {

    int sum = 0;

    for (int i = 0; i < a.length; i++) {

        sum += a[i][j];
    }

    System.out.println("Column " + j + " = " + sum);
}

The order is reversed:

Row-wise:
i → outer
j → inner

Column-wise:
j → outer
i → inner


---

7. Common Errors

❌ Error 1: Confusing length

a.length

does not mean total number of elements.

For:

new int[3][4]

a.length = 3

Total elements = 12.


---

❌ Error 2: Wrong inner-loop condition

Avoid:

j < a.length

Use:

j < a[i].length

because the number of columns belongs to the current row.


---

❌ Error 3: Mixing row and column

a[1][2]

means:

Row 1
Column 2

not:

Row 2
Column 1


---

❌ Error 4: Forgetting zero-based indexing

For:

int[][] a = new int[3][4];

valid indexes are:

Rows    → 0, 1, 2
Columns → 0, 1, 2, 3

So:

a[2][3]

is the last element.


---

🧠 3-LEVEL MEMORY MAP

2-D ARRAY
                     │
        ┌────────────┴────────────┐
        ↓                         ↓
     LEVEL 1                   LEVEL 2
    Beginner                 Intermediate
        │                         │
        ↓                         ↓
 Rows + Columns              length
 a[i][j]                     nested loops
 declaration                  sum/search
 creation                     enhanced for
        │
        └────────────┬────────────┘
                     ↓
                  LEVEL 3
                  Advanced
                     │
                     ↓
              Array of Arrays
                     │
                     ↓
                Jagged Arrays
                     │
                     ↓
             Row/Column Processing

⭐ Final Exam Formula

int[][] a = new int[ROWS][COLUMNS];

a.length       → ROWS
a[i].length    → columns/elements in row i
a[i][j]        → element at row i, column j

And the most important traversal pattern:

for (int i = 0; i < a.length; i++) {
    for (int j = 0; j < a[i].length; j++) {
        System.out.println(a[i][j]);
    }
}

One-line definition

> A 2-Dimensional Array in Java is an array of arrays used to store and access data using row and column indexes.
