Arrays Introduction in Java — ONE PAGE

1. What is an Array?

An array in Java is an object used to store multiple values of the same data type under a single variable name.

Instead of:

int a = 10;
int b = 20;
int c = 30;
int d = 40;

we can use:

int[] numbers = {10, 20, 30, 40};

Simple definition

> An array is a fixed-size collection of elements of the same data type, stored and accessed using an index.




---

2. Why Do We Need Arrays?

Suppose we need to store marks of 5 students.

Without an array:

int m1 = 80;
int m2 = 75;
int m3 = 90;
int m4 = 65;
int m5 = 88;

With an array:

int[] marks = {80, 75, 90, 65, 88};

So arrays make it easier to:

Store multiple values

Process values using loops

Access values using indexes

Reduce repetitive code



---

3. Important Properties of Arrays

Property	Explanation

Same data type	Elements normally have the same component type
Fixed size	Length is fixed after creation
Index-based	Elements are accessed using indexes
Zero-based	First index is 0
Object	An array is an object in Java
length	Gives the number of elements



---

4. Array Index

Consider:

int[] numbers = {10, 20, 30, 40};

Memory representation:

Value:     10    20    30    40
Index:      0     1     2     3

Therefore:

System.out.println(numbers[0]);

Output:

10

And:

System.out.println(numbers[3]);

Output:

40

Golden Rule

> Array index starts from 0 and ends at length - 1.




---

5. Declaration of an Array

Two common forms:

int[] numbers;

or:

int numbers[];

Both are valid Java.

Recommended style

int[] numbers;


---

6. Creating an Array

Use new:

numbers = new int[5];

This creates an array capable of storing 5 integers.

Combined:

int[] numbers = new int[5];

Indexes are:

0  1  2  3  4


---

7. Declaration + Initialization

You can directly provide values:

int[] numbers = {10, 20, 30, 40};

This creates an array of length 4.

You can also write:

int[] numbers = new int[]{10, 20, 30, 40};


---

8. Accessing Elements

int[] numbers = {10, 20, 30};

System.out.println(numbers[0]);
System.out.println(numbers[1]);
System.out.println(numbers[2]);

Output:

10
20
30


---

9. Changing an Element

Arrays are mutable.

int[] numbers = {10, 20, 30};

numbers[1] = 50;

System.out.println(numbers[1]);

Output:

50

Array becomes:

10  50  30


---

10. Finding Array Length

Use:

numbers.length

Example:

int[] numbers = {10, 20, 30, 40};

System.out.println(numbers.length);

Output:

4

⚠️ Notice:

Array       → length
String      → length()

For arrays:

numbers.length

not:

numbers.length()


---

11. Array + for Loop

Arrays are commonly processed using loops.

int[] numbers = {10, 20, 30, 40};

for (int i = 0; i < numbers.length; i++) {
    System.out.println(numbers[i]);
}

Output:

10
20
30
40

Flow:

i = 0 → numbers[0]
i = 1 → numbers[1]
i = 2 → numbers[2]
i = 3 → numbers[3]


---

12. Enhanced for Loop

You can also use:

int[] numbers = {10, 20, 30, 40};

for (int n : numbers) {
    System.out.println(n);
}

Output:

10
20
30
40

Read:

> For each element n in numbers.




---

13. Default Values

When an array is created with new, Java automatically assigns default values.

Example:

int[] a = new int[3];

Initially:

0  0  0

For common types:

Type	Default

int	0
float	0.0f
double	0.0
char	'\u0000'
boolean	false
Reference types	null



---

14. ArrayIndexOutOfBoundsException

Consider:

int[] numbers = {10, 20, 30};

Valid indexes:

0, 1, 2

This is invalid:

System.out.println(numbers[3]);

It causes:

ArrayIndexOutOfBoundsException

Because the last valid index is:

length - 1


---

15. Simple Complete Program

class ArrayDemo {

    public static void main(String[] args) {

        int[] marks = {80, 75, 90, 65, 88};

        System.out.println("Number of students: " + marks.length);

        for (int i = 0; i < marks.length; i++) {
            System.out.println(marks[i]);
        }
    }
}

Output:

Number of students: 5
80
75
90
65
88


---

🧠 ONE-PAGE REVISION

ARRAY
  ↓
Multiple values
  ↓
Same data type
  ↓
Fixed size
  ↓
Index-based
  ↓
Index starts at 0

Main syntax

int[] a;

int[] a = new int[5];

int[] a = {10, 20, 30};

Access

a[0]
a[1]
a[2]

Modify

a[1] = 50;

Length

a.length

Loop

for (int i = 0; i < a.length; i++) {
    System.out.println(a[i]);
}

Remember the formula

> First index = 0



> Last index = length - 1



> Number of elements = length



> Array size is fixed after creation.
