3-Dimensional Jagged Array in Java — ONE PAGE

> Note: The correct term is Jagged Array (not “Zagged Array”).



1. Definition

A 3-Dimensional Jagged Array is a 3-D array in which the inner arrays can have different lengths.

Unlike a regular 3-D array:

int[][][] a = new int[2][3][4];

where every layer has 3 rows and every row has 4 elements, a jagged array can have different numbers of rows and different numbers of elements in each row.


---

2. Basic Structure

3-D Array
    ↓
Layers
    ↓
Rows
    ↓
Elements

Access an element using:

a[i][j][k]

where:

i → Layer
j → Row
k → Element/Column


---

3. Example of 3-D Jagged Array

int[][][] a = {
    {
        {10, 20},
        {30, 40, 50}
    },
    {
        {60},
        {70, 80, 90, 100}
    }
};

Visual representation:

Layer 0
   Row 0 → 10 20
   Row 1 → 30 40 50

Layer 1
   Row 0 → 60
   Row 1 → 70 80 90 100

Notice:

Layer 0:
Row 0 → 2 elements
Row 1 → 3 elements

Layer 1:
Row 0 → 1 element
Row 1 → 4 elements

Therefore, it is jagged.


---

4. Why Is It Possible?

In Java, a multidimensional array is actually an array of arrays.

int[][][]
   ↓
Array of int[][]
   ↓
Array of int[]
   ↓
int values

Therefore, each inner int[] can have a different length.


---

5. Traversing a 3-D Jagged Array

Use three nested loops:

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

a.length
      ↓
Number of Layers

a[i].length
      ↓
Number of Rows in Layer i

a[i][j].length
      ↓
Number of Elements in Row j


---

6. Complete Program

class ThreeDJaggedArray {

    public static void main(String[] args) {

        int[][][] a = {
            {
                {10, 20},
                {30, 40, 50}
            },
            {
                {60},
                {70, 80, 90, 100}
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
30 40 50

60
70 80 90 100


---

7. Regular vs Jagged 3-D Array

Regular 3-D Array	Jagged 3-D Array

Same size at each level	Inner arrays can have different sizes
new int[2][3][4]	Different inner lengths possible
Rectangular structure	Irregular structure
Simple fixed dimensions	Flexible dimensions


Regular

int[][][] a = new int[2][3][4];

2 Layers
  ↓
3 Rows each
  ↓
4 Elements each

Jagged

int[][][] a = {
    {{10, 20}, {30, 40, 50}},
    {{60}, {70, 80, 90, 100}}
};

Layer 0 → 2, 3 elements per row
Layer 1 → 1, 4 elements per row


---

⭐ Remember This

3-D Jagged Array
       ↓
Array of 2-D arrays
       ↓
Rows can have different lengths
       ↓
a[i][j][k]
       ↓
Layer → Row → Element

> A 3-D jagged array in Java is a 3-dimensional array whose inner arrays are allowed to have different lengths, giving it a flexible, irregular structure.
