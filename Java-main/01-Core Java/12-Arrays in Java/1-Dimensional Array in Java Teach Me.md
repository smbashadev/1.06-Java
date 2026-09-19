1-Dimensional Array in Java — TEACH ME 👨‍🏫

Let's learn 1-Dimensional Arrays from zero, step by step. The goal is not just to memorize syntax—you should understand what the array is, how indexing works, and why the code works.


---

🟢 STEP 1 — First Understand the Problem

Imagine you have marks of 5 students.

You could create 5 variables:

int mark1 = 80;
int mark2 = 90;
int mark3 = 70;
int mark4 = 60;
int mark5 = 85;

But imagine 1,000 students.

Would you create:

mark1
mark2
mark3
...
mark1000

Obviously not.

Instead:

int[] marks = {80, 90, 70, 60, 85};

Now one array stores all 5 values.


---

🟢 STEP 2 — Visualize the Array

Think of an array as a row of boxes:

marks
  ↓
┌────┬────┬────┬────┬────┐
│ 80 │ 90 │ 70 │ 60 │ 85 │
└────┴────┴────┴────┴────┘
   0    1    2    3    4

The numbers underneath are called indexes.

An index tells Java which position you want.

For example:

marks[0]

means:

> Give me the element at index 0.



So:

marks[0] → 80
marks[1] → 90
marks[2] → 70
marks[3] → 60
marks[4] → 85


---

🟢 STEP 3 — Why Is It Called 1-Dimensional?

Because you need only one index to locate an element.

marks[2]

That's one dimension.

Compare this with a 2-D array:

matrix[1][2]

which requires two indexes.

So:

1-D → a[i]

2-D → a[i][j]

For now, focus on:

a[i]


---

🟢 STEP 4 — Array Definition

Remember this definition:

> A 1-Dimensional array is a fixed-size collection of elements of the same type, arranged in a single sequence and accessed using one index.



There are four important ideas:

Same Type
    +
Fixed Size
    +
Single Sequence
    +
One Index
    ↓
1-D Array


---

🟡 STEP 5 — How Do We Declare an Array?

Write:

int[] marks;

This means:

> marks can refer to an array of integers.



But at this point, the array has not been created yet.

Think:

marks
  ↓
reference

There is no array object yet.


---

🟡 STEP 6 — How Do We Create the Array?

Use new:

marks = new int[5];

Or combine declaration and creation:

int[] marks = new int[5];

Now Java creates an array with 5 integer elements.

┌────┬────┬────┬────┬────┐
│  0 │  0 │  0 │  0 │  0 │
└────┴────┴────┴────┴────┘
   0    1    2    3    4

Why are the values 0?

Because the default value of int is 0.


---

🟡 STEP 7 — What Does new int[5] Mean?

This is a common doubt.

new int[5]

means:

> Create an array capable of holding 5 integers.



It does not mean:

> Indexes go from 1 to 5.



Actually:

Number of elements = 5

Indexes:
0
1
2
3
4

Golden rule:

Last index = length - 1

So:

5 - 1 = 4


---

🟡 STEP 8 — Another Way to Create an Array

Instead of creating an empty array and assigning values individually:

int[] marks = new int[5];

marks[0] = 80;
marks[1] = 90;
marks[2] = 70;
marks[3] = 60;
marks[4] = 85;

you can directly initialize:

int[] marks = {80, 90, 70, 60, 85};

Much easier.

Java automatically determines:

Number of elements = 5


---

🟡 STEP 9 — Accessing Elements

Suppose:

int[] marks = {80, 90, 70, 60, 85};

To print the first value:

System.out.println(marks[0]);

Output:

80

To print the third value:

System.out.println(marks[2]);

Output:

70

Remember:

> Index = position, not value.




---

🟡 STEP 10 — Change an Element

Suppose:

int[] marks = {80, 90, 70};

We want to change 90 to 95.

90 is at index 1.

So:

marks[1] = 95;

Now:

Before:
[80][90][70]

After:
[80][95][70]

Therefore, array elements can be modified.


---

🟡 STEP 11 — How Do We Know the Number of Elements?

Use:

marks.length

Example:

int[] marks = {80, 90, 70, 60, 85};

System.out.println(marks.length);

Output:

5

Very important:

For arrays:

marks.length

✅ Correct

Not:

marks.length()

❌ Wrong


---

🔴 STEP 12 — The Most Important Formula

Suppose:

int[] a = new int[5];

Then:

Number of elements = 5
First index        = 0
Last index         = 4

Formula:

Last index = length - 1

Therefore:

Valid indexes:
0 ≤ index < length

This single rule prevents most array-indexing mistakes.


---

🔴 STEP 13 — Why Do We Use a Loop?

Suppose:

int[] marks = {80, 90, 70, 60, 85};

You could write:

System.out.println(marks[0]);
System.out.println(marks[1]);
System.out.println(marks[2]);
System.out.println(marks[3]);
System.out.println(marks[4]);

But that's repetitive.

Use a for loop:

for (int i = 0; i < marks.length; i++) {
    System.out.println(marks[i]);
}

Output:

80
90
70
60
85


---

🔴 STEP 14 — Understand the Loop Carefully

This is the most important part:

for (int i = 0; i < marks.length; i++)

Suppose:

marks.length = 5

Then:

i = 0 → marks[0] → 80
i = 1 → marks[1] → 90
i = 2 → marks[2] → 70
i = 3 → marks[3] → 60
i = 4 → marks[4] → 85

Then:

i = 5

Condition:

5 < 5

is false.

Loop stops.


---

🔴 STEP 15 — Why Not <=?

Don't write:

for (int i = 0; i <= marks.length; i++)

Suppose:

length = 5

Eventually:

i = 5

Then Java tries:

marks[5]

But valid indexes are:

0 1 2 3 4

So you get:

ArrayIndexOutOfBoundsException

Remember:

i < marks.length

not:

i <= marks.length


---

🔴 STEP 16 — Enhanced for Loop

Java gives us an easier way to read all elements:

for (int mark : marks) {
    System.out.println(mark);
}

Read this as:

> For each mark in marks, print the mark.



Output:

80
90
70
60
85


---

🔴 STEP 17 — Normal for vs Enhanced for

Normal for

for (int i = 0; i < marks.length; i++) {
    System.out.println(marks[i]);
}

Here:

i       → index
marks[i] → value

Enhanced for

for (int mark : marks) {
    System.out.println(mark);
}

Here:

mark → current value

Easy rule:

> Need the index? Use normal for.



> Need only the values? Enhanced for is convenient.




---

🔴 STEP 18 — Let's Build a Complete Program

class OneDArray {

    public static void main(String[] args) {

        int[] marks = {80, 90, 70, 60, 85};

        System.out.println("Number of elements: " + marks.length);

        for (int i = 0; i < marks.length; i++) {
            System.out.println("Index " + i + " = " + marks[i]);
        }
    }
}

Output:

Number of elements: 5
Index 0 = 80
Index 1 = 90
Index 2 = 70
Index 3 = 60
Index 4 = 85


---

🔴 STEP 19 — Let's Do Something Useful

Find the Total

int[] marks = {80, 90, 70, 60, 85};

int total = 0;

for (int mark : marks) {
    total = total + mark;
}

System.out.println("Total = " + total);

Output:

Total = 385

The flow:

total = 0
   ↓
+80 = 80
   ↓
+90 = 170
   ↓
+70 = 240
   ↓
+60 = 300
   ↓
+85 = 385


---

🔴 STEP 20 — Find the Largest Value

int[] numbers = {40, 90, 20, 70, 50};

int largest = numbers[0];

for (int i = 1; i < numbers.length; i++) {

    if (numbers[i] > largest) {
        largest = numbers[i];
    }
}

System.out.println("Largest = " + largest);

Output:

Largest = 90

Why start with:

int largest = numbers[0];

Because we use an actual element from the array as our initial comparison value.


---

🔴 STEP 21 — What If We Access an Invalid Index?

Example:

int[] a = {10, 20, 30};

Valid:

a[0]
a[1]
a[2]

But:

a[3]

is invalid.

Java throws:

ArrayIndexOutOfBoundsException

Remember:

length = 3
last index = 2


---

🔴 STEP 22 — Is an Array an Object?

Yes.

This is an important Java concept.

int[] a = new int[5];

Conceptually:

a
↓
reference
↓
ARRAY OBJECT

The variable a holds a reference to the array object.


---

🔴 STEP 23 — What Happens Here?

int[] a = {10, 20, 30};

int[] b = a;

Does Java create another array?

❌ No.

Both references point to the same array:

a ─────┐
              ↓
           [10][20][30]
              ↑
       b ─────┘

Now:

b[0] = 100;

Then:

System.out.println(a[0]);

prints:

100

Because a and b refer to the same array.


---

🔴 STEP 24 — Empty Array vs null

These are different.

Empty array:

int[] a = new int[0];

An array exists.

length = 0

Null:

int[] a = null;

a doesn't refer to an array object.

Therefore:

a.length

causes:

NullPointerException

Remember:

Empty array ≠ null


---

🔴 STEP 25 — Can We Resize a 1-D Array?

No.

If:

int[] a = new int[5];

that array remains length 5.

You cannot add a sixth element to that same array.

You can create another array:

a = new int[10];

but that is a new array object.


---

🧠 NOW TEST YOURSELF

Don't look at the answers immediately.

Q1

int[] a = {10, 20, 30, 40};

What is:

a.length

Q2

What is the last valid index?

Q3

What is:

a[2]

Q4

Is this valid?

a[4]

Q5

What does this print?

int[] a = new int[3];

System.out.println(a[0]);

Q6

What's wrong here?

for (int i = 0; i <= a.length; i++)


---

✅ ANSWERS

Q1

4

Q2

3

because:

length - 1 = 4 - 1 = 3

Q3

30

Q4

❌ No.

Valid indexes:

0, 1, 2, 3

Q5

0

because int array elements default to 0.

Q6

<= eventually allows:

i = a.length

which is not a valid index.

Correct:

i < a.length


---

🏆 MASTER THE CONCEPT

If you remember only this diagram, remember this:

1-D ARRAY
                     │
                     ↓
          int[] marks = new int[5]
                     │
                     ↓
        ┌────┬────┬────┬────┬────┐
        │  0 │  0 │  0 │  0 │  0 │
        └────┴────┴────┴────┴────┘
          0    1    2    3    4
          ↑              ↑
       first            last
       index            index

Remember these 6 rules:

1. One array → multiple elements


2. All elements have the same component type


3. Size is fixed after creation


4. Index starts at 0


5. Last index = length - 1


6. Access = array[index]



The most important formula:

0 ≤ index < array.length

If you truly understand that formula, most beginner 1-D array doubts disappear.
