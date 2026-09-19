3-Dimensional Array in Java — ONE PAGE

1. What is a 3-Dimensional Array?

A 3-Dimensional Array stores data using three indexes.

Think of it as:

> Layers → Rows → Columns



Syntax:

int[][][] a;

Access an element:

a[layer][row][column]

Example

int[][][] a = {
    {
        {10, 20, 30},
        {40, 50, 60}
    },
    {
        {70, 80, 90},
        {100, 110, 120}
    }
};

Visualize:

Layer 0
┌────┬────┬────┐
│ 10 │ 20 │ 30 │
├────┼────┼────┤
│ 40 │ 50 │ 60 │
└────┴────┴────┘

Layer 1
┌────┬────┬────┐
│ 70 │ 80 │ 90 │
├────┼────┼────┤
│100 │110 │120 │
└────┴────┴────┘


---

2. Three Indexes

The golden rule:

a[layer][row][column]
   ↓      ↓      ↓
   1      2      3

For example:

System.out.println(a[1][0][2]);

Means:

Layer 1
   ↓
Row 0
   ↓
Column 2
   ↓
90

Output:

90


---

3. Declaration

int[][][] a;

Other valid forms:

int a[][][];
int[] a[][];

Preferred:

int[][][] a;


---

4. Creation

int[][][] a = new int[2][3][4];

Meaning:

2 → layers
3 → rows per layer
4 → columns/elements per row

Total elements:

2 × 3 × 4 = 24


---

5. Direct Initialization

int[][][] a = {
    {
        {1, 2},
        {3, 4}
    },
    {
        {5, 6},
        {7, 8}
    }
};

Structure:

Layer 0          Layer 1

1  2              5  6
3  4              7  8


---

6. Traversing a 3-D Array

Because there are three dimensions, we generally need three nested loops:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        for (int k = 0; k < a[i][j].length; k++) {

            System.out.print(a[i][j][k] + " ");
        }

        System.out.println();
    }

    System.out.println();
}

Remember:

i → Layer
j → Row
k → Column

Therefore:

a[i][j][k]

means:

Layer → Row → Column


---

7. Understanding length

For:

int[][][] a = new int[2][3][4];

Number of layers

a.length

→ 2

Number of rows in layer i

a[i].length

→ 3

Number of columns in row j of layer i

a[i][j].length

→ 4

So:

a.length
       ↓
Layers

a[i].length
       ↓
Rows

a[i][j].length
       ↓
Columns


---

8. Complete Example

class ThreeDArray {

    public static void main(String[] args) {

        int[][][] a = {
            {
                {10, 20},
                {30, 40}
            },
            {
                {50, 60},
                {70, 80}
            }
        };

        for (int i = 0; i < a.length; i++) {

            for (int j = 0; j < a[i].length; j++) {

                for (int k = 0; k < a[i][j].length; k++) {

                    System.out.print(a[i][j][k] + " ");
                }

                System.out.println();
            }

            System.out.println();
        }
    }
}

Output:

10 20
30 40

50 60
70 80


---

⭐ 3-D Array Memory Map

3-D ARRAY
                     ↓
               int[][][] a
                     ↓
              ┌──────┴──────┐
              ↓             ↓
           Layer 0        Layer 1
              ↓             ↓
            Rows           Rows
              ↓             ↓
           Columns        Columns

The most important formula:

a[i][j][k]

i → Layer
j → Row
k → Column

One-line definition:

> A 3-Dimensional Array in Java is an array of 2-Dimensional arrays used to store data in layers, rows, and columns.
