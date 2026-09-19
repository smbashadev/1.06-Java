1-Dimensional Array in Java — ONE PAGE

1. What is a 1-Dimensional Array?

A 1-Dimensional (1-D) array stores multiple values of the same data type in a single sequence.

int[] marks = {80, 90, 70, 60};

Visual representation:

marks
  ↓
┌────┬────┬────┬────┐
│ 80 │ 90 │ 70 │ 60 │
└────┴────┴────┴────┘
   0    1    2    3
   ↑              ↑
first index    last index

Definition

> A 1-Dimensional array is a fixed-size collection of elements of the same type, arranged in a single line and accessed using one index.




---

2. Declaration

int[] marks;

or:

int marks[];

Preferred:

int[] marks;

Declaration only creates a reference variable; it doesn't create the array object.


---

3. Creation

marks = new int[5];

Or:

int[] marks = new int[5];

This creates an array containing 5 integer elements.

Initially:

[0][0][0][0][0]

because the default value of int is 0.


---

4. Initialization

Direct initialization

int[] marks = {80, 90, 70, 60};

Using new

int[] marks = new int[]{80, 90, 70, 60};


---

5. Indexing

Array indexing starts from 0.

int[] marks = {80, 90, 70, 60};

Index	Value

0	80
1	90
2	70
3	60


Access:

System.out.println(marks[0]); // 80
System.out.println(marks[2]); // 70

Golden Formula

First index = 0
Last index  = length - 1

Here:

length = 4
last index = 4 - 1 = 3


---

6. Modifying Elements

Array elements can be changed.

int[] marks = {80, 90, 70};

marks[1] = 95;

Now:

[80][95][70]


---

7. Finding Length

Use:

marks.length

Example:

int[] marks = {80, 90, 70, 60};

System.out.println(marks.length);

Output:

4

⚠️ For arrays:

marks.length      // ✅
marks.length()    // ❌


---

8. Traversing a 1-D Array

Using for

int[] marks = {80, 90, 70, 60};

for (int i = 0; i < marks.length; i++) {
    System.out.println(marks[i]);
}

Using enhanced for

for (int mark : marks) {
    System.out.println(mark);
}

Use a normal for when you need the index; use enhanced for when you simply need the values.


---

9. Complete Program

class OneDArray {

    public static void main(String[] args) {

        int[] marks = {80, 90, 70, 60};

        System.out.println("Length = " + marks.length);

        for (int i = 0; i < marks.length; i++) {
            System.out.println("Index " + i + " = " + marks[i]);
        }
    }
}

Output

Length = 4
Index 0 = 80
Index 1 = 90
Index 2 = 70
Index 3 = 60


---

10. Common Errors

❌ Invalid index

int[] a = {10, 20, 30};

System.out.println(a[3]);

Valid indexes are 0, 1, 2.

Result:

ArrayIndexOutOfBoundsException

❌ Wrong loop condition

i <= a.length

Correct:

i < a.length

❌ Trying to resize

int[] a = new int[5];

The array cannot be resized after creation.


---

🧠 1-D ARRAY IN ONE VIEW

1-D ARRAY
                     │
        ┌────────────┴────────────┐
        ↓                         ↓
    Same Type                 Fixed Size
        │                         │
        └────────────┬────────────┘
                     ↓
              Single Sequence
                     │
                     ↓
              One Index [i]
                     │
              ┌──────┴──────┐
              ↓             ↓
          First = 0    Last = length-1
                            │
                            ↓
                      array.length

⭐ Remember

> 1-D Array = Same Type + Fixed Size + Single Dimension + One Index + Zero-Based Indexing.
