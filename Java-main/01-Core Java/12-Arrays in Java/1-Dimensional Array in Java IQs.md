1-Dimensional Array in Java — DOUBTKILLER 🔥

This is the doubt-clearing version. We'll focus on the questions that usually cause confusion.


---

1. What exactly is a 1-Dimensional Array?

A 1-D array stores multiple elements of the same component type in a single sequence.

int[] numbers = {10, 20, 30, 40};

Think:

numbers
             ↓
        ┌────┬────┬────┬────┐
        │ 10 │ 20 │ 30 │ 40 │
        └────┴────┴────┴────┘
          0    1    2    3

You use one index to access an element:

numbers[2]

So:

1-D → one index → array[index]


---

2. Why is it called "1-D"?

Because an element is located using one index.

a[2]

That's one dimension.

Compare:

1-D → a[i]

2-D → a[i][j]

3-D → a[i][j][k]

Don't confuse number of elements with dimension.

For example:

int[] a = new int[100];

is still 1-D, even though it has 100 elements.


---

3. Is an Array a Variable?

This is a very common doubt.

Consider:

int[] a = new int[5];

There are actually two concepts here:

a
↓
reference variable

and:

new int[5]
↓
array object

Conceptually:

a
        ↓
   ┌────┬────┬────┬────┬────┐
   │ 0  │ 0  │ 0  │ 0  │ 0  │
   └────┴────┴────┴────┴────┘

So a is a reference to an array object.


---

4. Does Declaration Create the Array?

No.

int[] a;

This only declares a reference variable.

It does not create an array object.

You still need:

a = new int[5];

Then the array is created.

Remember:

Declaration → reference variable
Creation → array object


---

5. What Does new int[5] Mean?

This:

new int[5]

means:

> Create an array containing 5 integer elements.



It does NOT mean:

indexes = 1,2,3,4,5

Instead:

elements = 5

indexes:
0 1 2 3 4

Therefore:

length = 5
last index = 4


---

6. Why Does Index Start at 0?

Java arrays use zero-based indexing.

For:

int[] a = {10, 20, 30};

we have:

Index:  0    1    2
Value: 10   20   30

Therefore:

a[0] → 10
a[1] → 20
a[2] → 30

Golden rule:

First index = 0


---

7. Is Index the Same as Position?

Be careful.

Conceptually:

Position: 1    2    3
Index:    0    1    2
Value:   10   20   30

The first position has index 0.

So when Java says:

a[0]

it means:

> Give me the first element.




---

8. What is length?

For:

int[] a = {10, 20, 30, 40};

a.length

is:

4

because there are 4 elements.

But the last index is:

3

Don't confuse:

length = number of elements
last index = length - 1

Therefore:

length = 4
last index = 3


---

9. Why Is the Last Index length - 1?

Suppose:

length = 5

Starting from zero:

0
1
2
3
4

That's 5 indexes.

Therefore:

last index = 5 - 1 = 4

This is why:

a[a.length - 1]

accesses the last element.


---

10. Why Do We Write i < a.length?

Suppose:

int[] a = new int[5];

Valid indexes:

0 1 2 3 4

So:

for (int i = 0; i < a.length; i++)

produces:

i = 0 → valid
i = 1 → valid
i = 2 → valid
i = 3 → valid
i = 4 → valid
i = 5 → stop

At i = 5:

5 < 5 → false

Perfect.


---

11. What Happens With <=?

If you write:

for (int i = 0; i <= a.length; i++)

eventually:

i = 5

Then:

a[5]

But the last valid index is 4.

So Java throws:

ArrayIndexOutOfBoundsException

Memorize:

i < a.length

✅

i <= a.length

❌


---

12. What is the Difference Between a.length and a.length()?

For arrays:

a.length

is correct.

a.length()

is wrong.

Why?

Because length is an array field.

Example:

int[] a = new int[5];

System.out.println(a.length);

Output:

5

Don't confuse this with String:

String s = "Java";

s.length()

String uses a method.

So:

Array  → length
String → length()


---

13. What Are Default Values?

Suppose:

int[] a = new int[5];

You didn't explicitly put values inside it.

Java initializes them:

[0][0][0][0][0]

For common types:

Type	Default value

byte	0
short	0
int	0
long	0L
float	0.0f
double	0.0d
char	'\u0000'
boolean	false
Reference	null



---

14. What Happens Here?

int[] a = new int[3];

System.out.println(a[0]);

Output:

0

Because int elements have default value 0.


---

15. Can We Change an Array Element?

Yes.

int[] a = {10, 20, 30};

a[1] = 200;

Before:

[10][20][30]

After:

[10][200][30]

Arrays are mutable.


---

16. Can We Change the Array's Size?

No.

Suppose:

int[] a = new int[5];

That particular array always has:

length = 5

You can't turn that object into a 10-element array.

You can instead create a new array:

a = new int[10];

Now a refers to a different array.


---

17. Is {10, 20, 30} an Array?

This is a subtle syntax question.

When used in a declaration:

int[] a = {10, 20, 30};

Java creates and initializes the array.

But this is invalid:

a = {10, 20, 30};  // ❌

Use:

a = new int[]{10, 20, 30};  // ✅

So:

Declaration + initialization
→ {10,20,30} is allowed

Assignment later
→ use new int[]{10,20,30}


---

18. What Happens Here?

int[] a = {10, 20, 30};
int[] b = a;

Does Java create two arrays?

No.

There is only one array.

a ───────┐
                ↓
             [10][20][30]
                ↑
       b ───────┘

Both references point to the same object.


---

19. Prove It

int[] a = {10, 20, 30};

int[] b = a;

b[0] = 100;

System.out.println(a[0]);

Output:

100

Why?

Because b[0] = 100 changed the same array that a refers to.


---

20. Is null the Same as an Empty Array?

Absolutely not.

Empty array:

int[] a = new int[0];

An array object exists.

length = 0

Null:

int[] a = null;

a does not refer to an array object.

Therefore:

a.length

causes:

NullPointerException

Remember:

new int[0] → array exists
null       → no array object referenced


---

21. Can an Array Store Different Data Types?

Normally, no.

int[] a = {10, 20, 30};

This is invalid:

a[0] = 10.5;     // ❌
a[1] = "Java";   // ❌

The array's component type is int.


---

22. Can a 1-D Array Store Objects?

Yes.

String[] names = {"Ali", "Ravi", "John"};

You can also have:

Student[] students = new Student[3];

Initially:

[null][null][null]

because the elements contain references to Student objects.


---

23. Normal for vs Enhanced for

Normal for

for (int i = 0; i < a.length; i++) {
    System.out.println(a[i]);
}

You get:

index + value

Enhanced for

for (int value : a) {
    System.out.println(value);
}

You get:

value only

Rule:

Need index? → normal for
Need values? → enhanced for


---

24. Can Enhanced for Give the Index?

Not directly.

This:

for (int value : a)

gives you the value, not the index.

If you need:

index + value

use:

for (int i = 0; i < a.length; i++) {
    System.out.println(i + " " + a[i]);
}


---

25. Does Enhanced for Modify the Array?

Consider:

int[] a = {10, 20, 30};

for (int value : a) {
    value = value + 10;
}

Many beginners expect:

[20][30][40]

But the array remains:

[10][20][30]

because value is only the loop variable.

To modify elements, use the index:

for (int i = 0; i < a.length; i++) {
    a[i] = a[i] + 10;
}

Now:

[20][30][40]


---

26. What Happens If We Use final?

Consider:

final int[] a = {10, 20, 30};

Can we do:

a[0] = 100;

Yes.

The array can still be modified.

But:

a = new int[5];

is not allowed.

Why?

final prevents changing the reference, not the contents of the array object.

Conceptually:

final a
  ↓
same array reference
  ↓
[100][20][30]


---

27. Advanced Doubt: Is Array Length Stored?

Yes, the array object knows its length.

That's why Java can provide:

a.length

You don't need to separately maintain a variable such as:

int size = 5;

just to know the array's length.


---

28. Advanced Doubt: What Happens to the Old Array?

Consider:

int[] a = new int[5];

a = new int[10];

Now the variable a refers to the new 10-element array.

If nothing else refers to the old 5-element array, it becomes eligible for garbage collection.

Conceptually:

Before:

a → [ ][ ][ ][ ][ ]


After:

a → [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]

The old object may eventually be reclaimed by the JVM's garbage collector.


---

29. Advanced Doubt: Why Can String[] Become Object[]?

Because String is an Object.

String[] names = new String[3];

Object[] objects = names;

This is allowed.

But the actual array is still a String[].

Therefore:

objects[0] = 100;

causes:

ArrayStoreException

because an Integer cannot be stored inside that actual String[].

This is called array covariance.


---

30. Complete Example — Understand Everything

class OneDArray {

    public static void main(String[] args) {

        int[] numbers = {10, 20, 30, 40, 50};

        // Length
        System.out.println("Length = " + numbers.length);

        // Access
        System.out.println("First = " + numbers[0]);

        // Modify
        numbers[2] = 300;

        // Traverse
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(
                "Index " + i + " = " + numbers[i]
            );
        }
    }
}

Output:

Length = 5
First = 10
Index 0 = 10
Index 1 = 20
Index 2 = 300
Index 3 = 40
Index 4 = 50


---

🧠 DOUBTKILLER CHEAT SHEET

Doubt	Correct Answer

First index?	0
Last index?	length - 1
Number of elements?	array.length
Array size fixed?	Yes
Can elements change?	Yes
Can array size change?	No
Array indexing starts at?	0
Access syntax?	a[index]
Array length syntax?	a.length
a.length()?	❌
Invalid index?	ArrayIndexOutOfBoundsException
a = null; a.length?	NullPointerException
new int[0]?	Valid zero-length array
int[] a; creates array?	❌
new int[5] creates?	5-element array
int[] b = a creates second array?	❌
Same-type elements?	Yes
Can arrays contain objects?	Yes
Need index during traversal?	Normal for
Need values only?	Enhanced for



---

🚨 THE 5 DOUBTS YOU MUST NEVER MIX UP

① Length vs Last Index

length = 5
last index = 4

② Declaration vs Creation

int[] a;        // declaration
a = new int[5]; // creation

③ Empty vs Null

new int[0]  // empty array
null        // no referenced array

④ Reference vs Object

int[] b = a;

doesn't create another array.

⑤ < vs <=

i < a.length     // ✅
i <= a.length    // ❌


---

🏆 FINAL MEMORY TRICK

Whenever you see:

int[] a = new int[5];

immediately think:

a
            ↓
      ┌────┬────┬────┬────┬────┐
      │    │    │    │    │    │
      └────┴────┴────┴────┴────┘
        0    1    2    3    4
        ↑                   ↑
      FIRST               LAST

      length = 5
      last index = 4

And remember the master rule:

> For an array of length n, the only valid indexes are 0 through n - 1.



That one rule explains indexing, loops, ArrayIndexOutOfBoundsException, length, and the i < array.length pattern all at once.
