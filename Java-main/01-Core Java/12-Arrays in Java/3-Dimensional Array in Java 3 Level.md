3-Dimensional Array in Java — 3 LEVEL

Think of a 3-D array as multiple 2-D tables stacked together.

1-D → Line
2-D → Table
3-D → Collection of Tables / Layers


---

🟢 LEVEL 1 — BEGINNER

1. What is a 3-Dimensional Array?

A 3-Dimensional Array stores data using three indexes.

int[][][] a;

Access an element using:

a[layer][row][column]

Remember:

a[i][j][k]
  │  │  │
  │  │  └── Column
  │  └───── Row
  └──────── Layer


---

2. Simple Example

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

Visualize:

Layer 0          Layer 1

10  20           50  60
30  40           70  80

There are:

2 Layers
2 Rows per Layer
2 Columns per Row

Total elements:

2 × 2 × 2 = 8


---

3. Accessing Elements

System.out.println(a[0][0][0]);

Output:

10

Because:

0 → Layer 0
0 → Row 0
0 → Column 0

Another example:

System.out.println(a[1][0][1]);

Output:

60


---

4. Declaration and Creation

Declaration:

int[][][] a;

Creation:

a = new int[2][3][4];

Or together:

int[][][] a = new int[2][3][4];

Meaning:

2 → Layers
3 → Rows
4 → Columns


---

🟡 LEVEL 2 — INTERMEDIATE

1. length in 3-D Arrays

For:

int[][][] a = new int[2][3][4];

Number of layers

a.length

→ 2

Number of rows

a[i].length

→ 3

Number of columns

a[i][j].length

→ 4

So memorize:

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

2. Traversing a 3-D Array

Because there are three dimensions, we normally use three nested loops.

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        for (int k = 0; k < a[i][j].length; k++) {

            System.out.print(a[i][j][k] + " ");
        }

        System.out.println();
    }

    System.out.println();
}

Roles:

i → Layer
j → Row
k → Column


---

3. Complete Program

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

4. Sum of All Elements

int sum = 0;

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        for (int k = 0; k < a[i][j].length; k++) {

            sum += a[i][j][k];
        }
    }
}

System.out.println("Sum = " + sum);

For the above array:

10 + 20 + 30 + 40 + 50 + 60 + 70 + 80
= 360


---

🔴 LEVEL 3 — ADVANCED

1. 3-D Array Is an Array of Arrays of Arrays

This is the most important advanced concept.

When you write:

int[][][] a;

think:

int[][][]
    ↓
Array of
    ↓
int[][]
    ↓
Array of
    ↓
int[]
    ↓
Array of
    ↓
int

So:

a
│
├── Layer 0 → int[][]
│      ├── Row 0 → int[]
│      └── Row 1 → int[]
│
└── Layer 1 → int[][]
       ├── Row 0 → int[]
       └── Row 1 → int[]

This is why Java allows jagged 3-D arrays.


---

2. Jagged 3-D Array

Rows don't have to contain the same number of elements.

int[][][] a = {
    {
        {10, 20},
        {30, 40, 50}
    },
    {
        {60},
        {70, 80, 90}
    }
};

Structure:

Layer 0
    Row 0 → 10 20
    Row 1 → 30 40 50

Layer 1
    Row 0 → 60
    Row 1 → 70 80 90

This is valid because each row is a separate int[].


---

3. Why Use a[i][j].length?

Because rows can have different lengths.

Correct:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        for (int k = 0; k < a[i][j].length; k++) {

            System.out.println(a[i][j][k]);
        }
    }
}

The three limits correspond to the three levels:

a.length
       ↓
Layer count

a[i].length
       ↓
Row count

a[i][j].length
       ↓
Element count


---

⭐ 3-LEVEL MEMORY MAP

3-D ARRAY
                     │
                     ↓
                int[][][] a
                     │
          ┌──────────┴──────────┐
          ↓                     ↓
       LEVEL 1               LEVEL 2
       Beginner            Intermediate
          │                     │
          ↓                     ↓
   Layer/Row/Column          length
   a[i][j][k]             nested loops
   declaration              sum/search
          │
          └──────────┬──────────┘
                     ↓
                  LEVEL 3
                  Advanced
                     │
                     ↓
             Array of Arrays
                     │
                     ↓
              Jagged 3-D Array

🔥 Final Formula

int[][][] a = new int[2][3][4];

a.length          → 2 → Layers
a[i].length       → 3 → Rows
a[i][j].length    → 4 → Columns

a[i][j][k]
   ↓   ↓   ↓
 Layer Row Column

One-line definition

> A 3-Dimensional Array in Java is an array of 2-Dimensional arrays that stores data using three indexes: layer, row, and column.
