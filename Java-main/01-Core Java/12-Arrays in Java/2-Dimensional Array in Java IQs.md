2-Dimensional Array in Java — DOUBT KILLER 🔥

This is designed to remove the most common confusion points in 2-Dimensional Arrays.


---

1. What Exactly Is a 2-Dimensional Array?

A 2-D array stores data using rows and columns.

int[][] a = {
    {10, 20, 30},
    {40, 50, 60},
    {70, 80, 90}
};

Visualize it:

COLUMN
            0    1    2
         ┌────┬────┬────┐
ROW 0    │ 10 │ 20 │ 30 │
         ├────┼────┼────┤
ROW 1    │ 40 │ 50 │ 60 │
         ├────┼────┼────┤
ROW 2    │ 70 │ 80 │ 90 │
         └────┴────┴────┘

⭐ Golden Rule

a[row][column]

Therefore:

a[1][2]

means:

> Row 1, Column 2 → 60




---

2. Why Are There Two Indexes?

Because one index identifies the row, and the second identifies the element within that row.

a[i][j]
  │  │
  │  └── Column
  └───── Row

Compare:

1-D → a[i]

2-D → a[i][j]


---

3. Is int[][] Really a Matrix?

Not technically.

This:

int[][] a;

means:

> An array whose elements are themselves int[] arrays.



Think:

a
│
├── Row 0 → int[]
├── Row 1 → int[]
└── Row 2 → int[]

That's why Java can have different-sized rows.


---

4. What Does new int[3][4] Mean?

This:

int[][] a = new int[3][4];

means:

3 → number of rows
4 → number of elements in each row

So:

┌────┬────┬────┬────┐
│  0 │  0 │  0 │  0 │ Row 0
├────┼────┼────┼────┤
│  0 │  0 │  0 │  0 │ Row 1
├────┼────┼────┼────┤
│  0 │  0 │  0 │  0 │ Row 2
└────┴────┴────┴────┘

Total elements:

3 × 4 = 12


---

5. Does a.length Give Total Elements?

❌ No!

For:

int[][] a = new int[3][4];

a.length

gives:

3

because there are 3 rows.

It does NOT give 12.

Remember:

a.length
    ↓
Number of rows

a[i].length
    ↓
Number of elements in row i


---

6. Then How Do I Get the Number of Columns?

For a particular row:

a[i].length

Example:

a[0].length

means:

> How many elements are in row 0?



For:

int[][] a = new int[3][4];

the answer is:

4


---

7. Why Do We Use a[i].length in the Inner Loop?

This is one of the most important doubts.

Correct:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        System.out.println(a[i][j]);
    }
}

Why?

Because:

i → tells us which row we're currently processing

So:

a[i].length

means:

> Give me the length of this particular row.



This also works for jagged arrays.


---

8. What Happens If I Use a.length for Both Loops?

Suppose:

int[][] a = new int[3][5];

If you write:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a.length; j++) {
        System.out.println(a[i][j]);
    }
}

Then:

a.length = 3

So j only goes:

0, 1, 2

Column 3 and column 4 are never visited.

It doesn't necessarily throw an exception—it simply doesn't traverse the complete array.

Correct:

j < a[i].length


---

9. Why Does Index Start at 0?

Java arrays are zero-indexed.

For:

int[][] a = new int[3][4];

rows are:

0
1
2

columns are:

0
1
2
3

Therefore the last element is:

a[2][3]

General rule:

Last row    = a.length - 1
Last column = a[i].length - 1


---

10. What Is a[1][2]?

Take:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60},
    {70, 80, 90}
};

Find:

a[1][2]

Step 1:

1 → Row 1

Row 1 is:

40 50 60

Step 2:

2 → Column 2

Therefore:

60


---

11. What Is the Difference Between a[1] and a[1][2]?

This is a very important Java concept.

Given:

int[][] a = {
    {10, 20, 30},
    {40, 50, 60}
};

a[1]

means:

> The entire row 1.



Conceptually:

a[1] → {40, 50, 60}

Its type is:

int[]

a[1][2]

means:

> Element 2 inside row 1.



Its type is:

int

Therefore:

a[1]      → int[]
a[1][2]   → int


---

12. Can I Store a[1] in an int[] Variable?

Yes!

int[] row = a[1];

Now:

row → {40, 50, 60}

And:

System.out.println(row[0]);

prints:

40

This proves that each row is itself an array.


---

13. Why Is Java's 2-D Array Called an Array of Arrays?

Consider:

int[][] a = new int[3][4];

Conceptually:

a
↓
┌─────────┬─────────┬─────────┐
│   a[0]  │   a[1]  │   a[2]  │
└─────────┴─────────┴─────────┘
     ↓         ↓         ↓
   int[]     int[]     int[]

So:

int[][]
  ↓
array of
  ↓
int[]

That's the fundamental reason behind 2-D arrays in Java.


---

14. What Is a Jagged Array?

A jagged array has rows of different lengths.

int[][] a = {
    {10, 20},
    {30, 40, 50, 60},
    {70, 80, 90}
};

Visual:

Row 0 → 10 20
Row 1 → 30 40 50 60
Row 2 → 70 80 90

Now:

a.length       → 3
a[0].length    → 2
a[1].length    → 4
a[2].length    → 3

This is completely valid.


---

15. Why Is a[i].length So Important for Jagged Arrays?

Suppose:

Row 0 → 2 elements
Row 1 → 4 elements
Row 2 → 3 elements

If you use:

j < a[i].length

Java automatically adjusts:

i = 0 → j < 2
i = 1 → j < 4
i = 2 → j < 3

That's perfect.


---

16. Can I Create a Jagged Array Using new?

Yes.

int[][] a = new int[3][];

a[0] = new int[2];
a[1] = new int[4];
a[2] = new int[3];

This means:

3 rows
↓
Row 0 → size 2
Row 1 → size 4
Row 2 → size 3


---

17. Can Rows Have null?

Yes.

int[][] a = new int[3][];

a[0] = new int[2];
a[1] = null;
a[2] = new int[3];

Now:

a[0] → array
a[1] → null
a[2] → array

If you do:

System.out.println(a[1].length);

you get:

NullPointerException

because a[1] doesn't refer to an array.


---

18. How Do I Print a 2-D Array?

Traditional nested loop:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        System.out.print(a[i][j] + " ");
    }

    System.out.println();
}

Enhanced for loop:

for (int[] row : a) {

    for (int value : row) {

        System.out.print(value + " ");
    }

    System.out.println();
}

The second form works because:

a → int[]
row → int

More precisely:

a
↓
int[] rows
↓
int values


---

19. Why Is println() Outside the Inner Loop?

Consider:

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {
        System.out.print(a[i][j] + " ");
    }

    System.out.println();
}

print() keeps values on the same line:

10 20 30

Then:

System.out.println();

moves to the next line after completing one row.

If you put println() inside the inner loop:

for (...) {
    for (...) {
        System.out.println(a[i][j]);
    }
}

you'll get:

10
20
30
40
50
60

instead of a table.


---

20. Can I Change an Element?

Yes.

a[0][1] = 999;

Before:

10 20 30

After:

10 999 30


---

21. Can I Store Strings?

Absolutely.

String[][] names = {
    {"A", "B"},
    {"C", "D"}
};

Access:

System.out.println(names[1][0]);

Output:

C

The same row-column concept applies.


---

22. Can I Store double, char, etc.?

Yes.

double[][] prices = new double[3][4];

char[][] letters = new char[2][3];

boolean[][] flags = new boolean[4][5];

The 2-D array concept is the same.


---

23. What Is the Default Value?

For:

int[][] a = new int[2][3];

default:

0 0 0
0 0 0

For:

double[][] a = new double[2][3];

default:

0.0

For:

boolean[][] a = new boolean[2][3];

default:

false

For:

String[][] a = new String[2][3];

default:

null


---

24. What Happens With an Invalid Index?

Suppose:

int[][] a = new int[3][4];

Valid:

Rows    → 0, 1, 2
Columns → 0, 1, 2, 3

This is invalid:

a[3][0]

because row 3 doesn't exist.

This is also invalid:

a[0][4]

because column 4 doesn't exist in row 0.

Result:

ArrayIndexOutOfBoundsException


---

25. a.length vs a[0].length — THE BIGGEST DOUBT

Memorize this table:

Expression	Meaning

a.length	Number of rows
a[0].length	Number of elements in row 0
a[1].length	Number of elements in row 1
a[i].length	Number of elements in row i
a[i][j]	Actual element


Example:

a =

10 20 30
40 50 60
70 80 90

Then:

a.length      = 3
a[0].length   = 3
a[1].length   = 3
a[2].length   = 3


---

26. What If It's Jagged?

Then:

10 20
30 40 50
60 70 80 90

Now:

a.length      = 3
a[0].length   = 2
a[1].length   = 3
a[2].length   = 4

This is the best way to understand why:

a[i].length

is safer than assuming one fixed column count.


---

27. Find the Sum — Exam Favorite

int[][] a = {
    {10, 20, 30},
    {40, 50, 60}
};

int sum = 0;

for (int i = 0; i < a.length; i++) {

    for (int j = 0; j < a[i].length; j++) {

        sum += a[i][j];
    }
}

System.out.println(sum);

Output:

210


---

28. Find the Largest — Exam Favorite

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

System.out.println(largest);

Output:

90


---

29. Can I Use a 2-D Array as a Method Argument?

Yes.

static void display(int[][] a) {

    for (int i = 0; i < a.length; i++) {

        for (int j = 0; j < a[i].length; j++) {

            System.out.print(a[i][j] + " ");
        }

        System.out.println();
    }
}

Call:

int[][] x = {
    {10, 20},
    {30, 40}
};

display(x);


---

30. Can a 2-D Array Be Returned From a Method?

Yes.

static int[][] getArray() {

    return new int[][] {
        {10, 20},
        {30, 40}
    };
}

Then:

int[][] a = getArray();

So arrays can be:

passed to methods
        +
returned from methods


---

🔥 FINAL DOUBT-KILLER TABLE

Doubt	Correct Answer

What is 2-D array?	Array of arrays
How many indexes?	Two
Syntax to access?	a[i][j]
i means?	Row
j means?	Column/element within row
a.length?	Number of rows
a[i].length?	Length of row i
How to traverse?	Nested loops
Outer loop?	Rows
Inner loop?	Columns/elements
Can rows have different lengths?	Yes
Different row lengths called?	Jagged/Ragged array
Index starts at?	0
Invalid index?	ArrayIndexOutOfBoundsException
a[1] type?	int[]
a[1][2] type?	int
Can 2-D array be passed to method?	Yes
Can method return 2-D array?	Yes



---

🧠 THE 5 THINGS YOU MUST NEVER FORGET

2-D ARRAY
                     ↓
              int[][] a
                     ↓
             Array of Arrays
                     ↓
              a[i][j]
              ↙     ↘
            row     column

⭐ Rule 1

a[i][j]

i = row, j = column.

⭐ Rule 2

a.length

Number of rows.

⭐ Rule 3

a[i].length

Number of elements in row i.

⭐ Rule 4

for (int i = 0; i < a.length; i++)
    for (int j = 0; j < a[i].length; j++)

Outer = row, Inner = elements of that row.

⭐ Rule 5

> Java's 2-D array is an array of arrays, so jagged arrays are possible.



If these five rules are clear, 90% of 2-D array questions become straightforward.
