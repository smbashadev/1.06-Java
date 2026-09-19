2-Dimensional Array in Java — TEACH ME 👨‍🏫

Let's learn 2-Dimensional Arrays from zero, step by step. The most important idea is to understand rows, columns, indexes, and nested loops.


---

🟢 STEP 1 — First Understand the Problem

Suppose you want to store marks of students in different subjects:

Maths  Java  SQL
Student 1     80    90    70
Student 2     75    85    95
Student 3     90    88    92

Using separate variables would be inconvenient.

A 2-D array lets us represent this as a table:

int[][] marks = {
    {80, 90, 70},
    {75, 85, 95},
    {90, 88, 92}
};

Visualize it:

Column
              0    1    2
           ┌────┬────┬────┐
Row 0      │ 80 │ 90 │ 70 │
           ├────┼────┼────┤
Row 1      │ 75 │ 85 │ 95 │
           ├────┼────┼────┤
Row 2      │ 90 │ 88 │ 92 │
           └────┴────┴────┘


---

🟢 STEP 2 — Why Is It Called 2-Dimensional?

Because we need two indexes to locate an element.

For a 1-D array:

a[2]

One index.

For a 2-D array:

a[1][2]

Two indexes.

Think:

1-D → a[i]

2-D → a[i][j]


---

🟢 STEP 3 — Understand a[row][column]

This is the most important rule:

a[row][column]

The:

first index  → row
second index → column

For:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60},
    {70, 80, 90}
};

we have:

Column
             0    1    2
          ┌────┬────┬────┐
Row 0     │ 10 │ 20 │ 30 │
          ├────┼────┼────┤
Row 1     │ 40 │ 50 │ 60 │
          ├────┼────┼────┤
Row 2     │ 70 │ 80 │ 90 │
          └────┴────┴────┘

Therefore:

a[0][0] → 10
a[0][1] → 20
a[0][2] → 30

a[1][0] → 40
a[1][1] → 50
a[1][2] → 60

a[2][0] → 70
a[2][1] → 80
a[2][2] → 90


---

🟢 STEP 4 — Declaration

The most common declaration is:

int[][] a;

This means:

> a can refer to a 2-D integer array.



But remember:

int[][] a;

does not create the array yet.


---

🟡 STEP 5 — Creation

Create the array using new:

int[][] a = new int[3][3];

This creates:

3 rows × 3 columns

Visualize:

┌────┬────┬────┐
│  0 │  0 │  0 │
├────┼────┼────┤
│  0 │  0 │  0 │
├────┼────┼────┤
│  0 │  0 │  0 │
└────┴────┴────┘

There are:

3 × 3 = 9

elements.

The values are 0 because the default value of int is 0.


---

🟡 STEP 6 — What Does new int[3][3] Mean?

Don't confuse the numbers.

new int[3][3]

means:

3 → number of rows
3 → number of elements in each row

For:

new int[2][4]

we get:

2 rows
4 columns

┌────┬────┬────┬────┐
│    │    │    │    │
├────┼────┼────┼────┤
│    │    │    │    │
└────┴────┴────┴────┘

Total:

2 × 4 = 8 elements


---

🟡 STEP 7 — Direct Initialization

Instead of creating the array and filling it separately, we can directly initialize it:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60},
    {70, 80, 90}
};

Each inner {} represents one row.

{10, 20, 30} → Row 0
{40, 50, 60} → Row 1
{70, 80, 90} → Row 2


---

🟡 STEP 8 — Accessing an Element

Suppose:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60},
    {70, 80, 90}
};

What is:

a[1][2]

Follow the two indexes:

First index = 1 → Row 1

Row 1:
40 50 60

Second index = 2 → 60

Therefore:

System.out.println(a[1][2]);

Output:

60


---

🟡 STEP 9 — Modify an Element

You can change an element:

a[1][2] = 600;

Before:

40 50 60

After:

40 50 600

So:

System.out.println(a[1][2]);

prints:

600


---

🟡 STEP 10 — Understanding length

This is extremely important.

Suppose:

int[][] a = new int[3][4];

Then:

a.length

means:

> How many rows are there?



Answer:

3

But:

a[0].length

means:

> How many elements are in row 0?



Answer:

4

Therefore:

a.length
     ↓
number of rows

a[i].length
     ↓
number of elements in row i


---

🟡 STEP 11 — Why Do We Need Two Loops?

Suppose:

10 20 30
40 50 60
70 80 90

We need to visit:

Row 0 → 10 20 30
Row 1 → 40 50 60
Row 2 → 70 80 90

So:

Outer loop → controls rows
Inner loop → controls columns

That's why we use nested loops.


---

🔴 STEP 12 — The Most Important 2-D Array Program

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

🔴 STEP 13 — Understand the Nested Loop

Don't memorize it blindly.

Look at:

for (int i = 0; i < a.length; i++)

i represents:

ROW

Then:

for (int j = 0; j < a[i].length; j++)

j represents:

COLUMN

Therefore:

a[i][j]

means:

a[row][column]


---

🔴 STEP 14 — Trace the Program

Suppose:

10 20 30
40 50 60

First outer-loop iteration:

i = 0

Inner loop:

j = 0 → a[0][0] → 10
j = 1 → a[0][1] → 20
j = 2 → a[0][2] → 30

Then:

i = 1

Inner loop:

j = 0 → a[1][0] → 40
j = 1 → a[1][1] → 50
j = 2 → a[1][2] → 60

So output:

10 20 30
40 50 60


---

🔴 STEP 15 — Why a[i].length?

This is a very common doubt.

Suppose:

int[][] a = new int[3][5];

Then:

a.length = 3

because there are 3 rows.

But:

a[0].length = 5

because row 0 has 5 elements.

So:

for (int i = 0; i < a.length; i++)

controls the rows.

And:

for (int j = 0; j < a[i].length; j++)

controls the elements inside that row.


---

🔴 STEP 16 — Very Important: Java 2-D Arrays Are Arrays of Arrays

This is the deeper concept.

When you write:

int[][] a;

think:

a
↓
Array
 ↓
contains
 ↓
multiple int[] arrays

Conceptually:

a
 ↓
┌─────────┬─────────┬─────────┐
│ Row 0   │ Row 1   │ Row 2   │
└─────────┴─────────┴─────────┘
     ↓         ↓         ↓
  [10,20]  [30,40]  [50,60]

So technically:

> A Java 2-D array is an array of arrays.



This is why Java can support jagged arrays.


---

🔴 STEP 17 — What Is a Jagged Array?

A jagged array is a 2-D array where different rows have different lengths.

Example:

int[][] a = {
    {10, 20},
    {30, 40, 50, 60},
    {70, 80, 90}
};

Visual:

Row 0 → 10 20
Row 1 → 30 40 50 60
Row 2 → 70 80 90

Notice:

Row 0 → 2 elements
Row 1 → 4 elements
Row 2 → 3 elements

That's perfectly valid Java.


---

🔴 STEP 18 — Why Does the Normal Nested Loop Still Work?

Because we use:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        System.out.print(a[i][j] + " ");
    }

    System.out.println();
}

For each row, Java asks:

How long is THIS row?

using:

a[i].length

That's much safer than assuming every row has the same number of elements.


---

🔴 STEP 19 — Enhanced for Loop

We can also use enhanced for loops:

for (int[] row : a) {

    for (int value : row) {

        System.out.print(value + " ");
    }

    System.out.println();
}

Understand the types:

int[] row

because each element of a is an int[].

Then:

int value

because each element of row is an int.

So:

2-D array
   ↓
int[] row
   ↓
int value


---

🔴 STEP 20 — Find the Sum of All Elements

Let's do a real problem.

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

Because:

10 + 20 + 30 + 40 + 50 + 60
= 210


---

🔴 STEP 21 — Find the Largest Number

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

🔴 STEP 22 — Find Row-Wise Sum

Suppose:

10 20 30
40 50 60
70 80 90

We want:

Row 0 → 60
Row 1 → 150
Row 2 → 240

Program:

for (int i = 0; i < a.length; i++) {

    int sum = 0;

    for (int j = 0; j < a[i].length; j++) {
        sum += a[i][j];
    }

    System.out.println("Row " + i + " Sum = " + sum);
}

Output:

Row 0 Sum = 60
Row 1 Sum = 150
Row 2 Sum = 240


---

🔴 STEP 23 — Common Mistake

Suppose:

int[][] a = new int[3][5];

A beginner may write:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a.length; j++) {
        System.out.println(a[i][j]);
    }
}

The problem:

a.length = 3

But each row has:

5 elements

So columns 3 and 4 won't be processed.

Correct:

for (int j = 0; j < a[i].length; j++)


---

🔴 STEP 24 — Common Mistake: Confusing Row and Column

Suppose:

10 20 30
40 50 60

What is:

a[0][1]

First:

0 → Row 0

Row 0:

10 20 30

Then:

1 → Column 1

Answer:

20

So:

a[row][column]

Always.


---

🟣 STEP 25 — Quick Comparison

Concept	1-D Array	2-D Array

Declaration	int[] a	int[][] a
Indexes	1	2
Access	a[i]	a[i][j]
Structure	Sequence	Rows + columns
Traversal	One loop	Nested loops
Length	a.length	a.length, a[i].length



---

🧠 FINAL MEMORY MAP

2-D ARRAY
                     ↓
              Rows + Columns
                     ↓
               a[row][column]
                     ↓
              ┌──────┴──────┐
              ↓             ↓
           row index     column index
              ↓             ↓
              i             j
              ↓             ↓
           a[i][j]

And for traversal:

Outer loop
    ↓
Rows
    ↓
Inner loop
    ↓
Columns
    ↓
a[i][j]

⭐ Remember these 7 rules

1. int[][] a → declares a 2-D array reference.


2. new int[3][4] → 3 rows, 4 elements per row.


3. Index starts at 0.


4. a[i][j] → row i, column j.


5. a.length → number of rows.


6. a[i].length → number of elements in row i.


7. Java 2-D arrays are technically arrays of arrays, which is why jagged arrays are possible.



> Master a[i][j] and a.length vs a[i].length; once those are clear, the rest of 2-D arrays becomes much easier.
