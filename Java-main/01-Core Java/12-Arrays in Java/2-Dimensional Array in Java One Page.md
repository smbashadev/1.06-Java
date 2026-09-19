2-Dimensional Array in Java — ONE PAGE

1. Definition

A 2-Dimensional Array (2-D array) is an array arranged in rows and columns, where each element is accessed using two indexes:

array[row][column]

Think of it like a table:

Column
          0    1    2
       ┌────┬────┬────┐
Row 0  │ 10 │ 20 │ 30 │
       ├────┼────┼────┤
Row 1  │ 40 │ 50 │ 60 │
       ├────┼────┼────┤
Row 2  │ 70 │ 80 │ 90 │
       └────┴────┴────┘

So:

a[0][1] → 20
a[1][2] → 60
a[2][0] → 70


---

2. Declaration

int[][] a;

or:

int a[][];

or:

int[] a[];

All three are valid.

The most commonly used form is:

int[][] a;


---

3. Creation

int[][] a = new int[3][3];

This creates:

3 rows × 3 columns = 9 elements

Initially:

┌────┬────┬────┐
│  0 │  0 │  0 │
├────┼────┼────┤
│  0 │  0 │  0 │
├────┼────┼────┤
│  0 │  0 │  0 │
└────┴────┴────┘


---

4. Initialization

You can directly initialize:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60},
    {70, 80, 90}
};

Structure:

Row 0 → 10 20 30
Row 1 → 40 50 60
Row 2 → 70 80 90


---

5. Accessing Elements

Syntax:

array[row][column]

Example:

System.out.println(a[1][2]);

Output:

60

Because:

0    1    2
    ┌────┬────┬────┐
 0  │ 10 │ 20 │ 30 │
    ├────┼────┼────┤
 1  │ 40 │ 50 │ 60 │ ← [1][2]
    ├────┼────┼────┤
 2  │ 70 │ 80 │ 90 │
    └────┴────┴────┘


---

6. Modifying an Element

a[1][2] = 600;

Now:

40  50  600

The original value 60 is replaced by 600.


---

7. Number of Rows

For:

int[][] a = new int[3][4];

Number of rows:

a.length

Output:

3


---

8. Number of Columns

For a rectangular array:

a[0].length

gives the number of columns in row 0.

For:

int[][] a = new int[3][4];

a.length       → 3
a[0].length    → 4

Important:

a.length
     ↓
number of rows

a[i].length
     ↓
number of columns in row i


---

9. Traversing a 2-D Array

A 2-D array generally requires nested loops.

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        System.out.print(a[i][j] + " ");
    }

    System.out.println();
}

Here:

i → row
j → column

Think:

i = 0 → first row
       j = 0,1,2

i = 1 → second row
       j = 0,1,2

i = 2 → third row
       j = 0,1,2


---

10. Complete Program

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

11. 2-D Array vs 1-D Array

1-D Array	2-D Array

int[] a	int[][] a
One index	Two indexes
a[i]	a[i][j]
Single sequence	Rows and columns
Usually one loop	Usually nested loops



---

12. Most Important Points

2-D Array
   ↓
Rows + Columns
   ↓
Two indexes
   ↓
a[row][column]

Remember these:

Index starts from 0.

First index represents the row.

Second index represents the column.

a.length → number of rows.

a[i].length → number of columns in row i.

Use nested loops to traverse a 2-D array.

Last row index = a.length - 1.

Last column index of row i = a[i].length - 1.


Golden Rule ⭐

a[i][j]

i → Row
j → Column

So whenever you see:

a[2][3]

read it as:

> Row 2, Column 3.
