# 13. Arrays Utility Class in Java — DOUBTKILLER

This version is focused on **confusions, traps, interview questions, and the exact reason behind each rule**.

We will cover:

1. `sort()`
2. `binarySearch()`
3. `equals()`
4. `fill()`
5. `asList()`

> **No Generics concept is used.**

---

# 1. `Arrays.sort()` — DOUBTKILLER

## Doubt 1: What exactly is `Arrays`?

`Arrays` is a utility class in:

```java
java.util.Arrays
```

So:

```java
import java.util.Arrays;
```

It provides static methods for performing operations on arrays.

For example:

```java
Arrays.sort(a);
Arrays.binarySearch(a, 20);
Arrays.equals(a, b);
Arrays.fill(a, 100);
Arrays.asList(a);
```

---

## Doubt 2: Does `sort()` create a new array?

**No.**

Example:

```java
int a[] = {30, 10, 20};

Arrays.sort(a);

System.out.println(Arrays.toString(a));
```

Output:

```text
[10, 20, 30]
```

The original `a` has been modified.

Think:

```text
Before:
a → [30,10,20]

Arrays.sort(a)

After:
a → [10,20,30]
```

---

## Doubt 3: Does `sort()` return the sorted array?

**No.**

Its return type is:

```text
void
```

Therefore:

```java
Arrays.sort(a);        // ✅
```

but:

```java
int b[] = Arrays.sort(a);   // ❌
```

is invalid.

---

## Doubt 4: Can I sort only part of an array?

**Yes.**

```java
Arrays.sort(a, fromIndex, toIndex);
```

The confusing rule is:

```text
fromIndex → inclusive
toIndex   → exclusive
```

Example:

```java
int a[] = {50, 40, 30, 20, 10};

Arrays.sort(a, 1, 4);
```

Indexes `1`, `2`, `3` are sorted.

Result:

```text
[50, 20, 30, 40, 10]
```

Not:

```text
[50, 20, 30, 40, 10]
             ↑
```

Actually, exactly indexes `1–3` are affected; index `4` remains `10`.

---

## Doubt 5: Does `sort()` always mean ascending order?

For primitive arrays using the normal `sort(array)` form, the elements are sorted in ascending numerical order.

Example:

```java
int a[] = {40, 10, 30, 20};

Arrays.sort(a);
```

Result:

```text
[10, 20, 30, 40]
```

For reference arrays, ordering depends on the applicable natural ordering/comparator.

---

# 2. `Arrays.binarySearch()` — DOUBTKILLER

## Doubt 6: What does `binarySearch()` return?

It returns the **index** of the searched element if it is found.

```java
int a[] = {10, 20, 30, 40};

int result = Arrays.binarySearch(a, 30);

System.out.println(result);
```

Output:

```text
2
```

Not:

```text
30
```

Remember:

```text
search value → 30
returned value → 2
```

because `2` is the index.

---

## Doubt 7: Why does binary search need a sorted array?

Binary search uses the ordering of the array to eliminate portions of the search range.

Suppose:

```text
[10,20,30,40,50,60,70]
```

If you are searching for `60`, knowing that the middle is `40` tells the algorithm that the target must be to the right.

If the array is randomly arranged:

```text
[40,10,70,20,60,30,50]
```

that reasoning no longer works.

Therefore:

```java
Arrays.sort(a);
Arrays.binarySearch(a, key);
```

is the normal pattern for natural-order searching.

---

## Doubt 8: Does `binarySearch()` automatically sort the array?

**No.**

This:

```java
Arrays.binarySearch(a, 20);
```

does not mean:

```text
sort
 ↓
search
```

It only searches.

If you need natural-order binary search, sort the array first.

---

## Doubt 9: What happens when the element isn't found?

A **negative value** is returned.

Example:

```java
int a[] = {10, 20, 30, 40};

int result = Arrays.binarySearch(a, 25);

System.out.println(result);
```

The result is negative.

So:

```java
if(result >= 0)
{
    System.out.println("Found");
}
else
{
    System.out.println("Not Found");
}
```

is the easy way to test it.

---

## Doubt 10: Is the "not found" result always `-1`?

**No.**

This is a very common mistake.

For the natural-order overload, Java returns a negative value encoding the insertion point.

Conceptually:

```text
found:
    result >= 0

not found:
    result < 0
```

The exact negative result is not simply a universal `-1`.

---

## Doubt 11: Does `binarySearch()` change the array?

**No.**

Searching doesn't rearrange the array.

```text
Before:
[10,20,30,40]

binarySearch(...)

After:
[10,20,30,40]
```

---

# 3. `Arrays.equals()` — DOUBTKILLER

## Doubt 12: What does `Arrays.equals()` compare?

It compares array **contents element-by-element**.

```java
int a[] = {10,20,30};
int b[] = {10,20,30};

System.out.println(Arrays.equals(a,b));
```

Output:

```text
true
```

---

## Doubt 13: Is `Arrays.equals()` the same as `==`?

**No.**

This is one of the biggest array doubts.

```java
int a[] = {10,20,30};
int b[] = {10,20,30};
```

Now:

```java
System.out.println(a == b);
```

returns:

```text
false
```

because `a` and `b` are references to two different array objects.

But:

```java
System.out.println(Arrays.equals(a,b));
```

returns:

```text
true
```

because their contents are equal.

Remember:

```text
a == b
   ↓
Same array object?

Arrays.equals(a,b)
   ↓
Same array contents?
```

---

## Doubt 14: What if both references point to the same array?

Example:

```java
int a[] = {10,20,30};

int b[] = a;

System.out.println(a == b);
```

Result:

```text
true
```

And:

```java
System.out.println(Arrays.equals(a,b));
```

also gives:

```text
true
```

Because both references refer to the same array.

---

## Doubt 15: Does order matter?

**Yes.**

```text
A → [10,20,30]
B → [30,20,10]
```

Then:

```java
Arrays.equals(a,b)
```

returns:

```text
false
```

`Arrays.equals()` is not a "same elements regardless of order" test.

---

## Doubt 16: Does length matter?

**Yes.**

```text
[10,20,30]
[10,20,30,40]
```

are not equal.

Therefore:

```text
Same length
+
Same element at each position
=
true
```

---

## Doubt 17: Can I use `Arrays.equals()` to compare nested arrays?

For nested arrays, don't confuse:

```java
Arrays.equals()
```

with:

```java
Arrays.deepEquals()
```

`Arrays.equals()` is intended for one-dimensional array contents.

For arrays containing arrays, `Arrays.deepEquals()` is the specialized method.

That distinction becomes important when you study multidimensional arrays.

---

# 4. `Arrays.fill()` — DOUBTKILLER

## Doubt 18: What does `fill()` actually do?

It replaces elements with the supplied value.

```java
int a[] = {10,20,30};

Arrays.fill(a, 100);
```

Result:

```text
[100,100,100]
```

---

## Doubt 19: Does `fill()` add elements?

**No.**

Before:

```text
[10,20,30]
```

After:

```text
[100,100,100]
```

The size remains:

```text
3
```

because Java arrays have fixed length.

---

## Doubt 20: Can I fill only a range?

**Yes.**

```java
Arrays.fill(a, fromIndex, toIndex, value);
```

Again:

```text
fromIndex → inclusive
toIndex   → exclusive
```

Example:

```java
int a[] = {10,20,30,40,50};

Arrays.fill(a, 1, 4, 100);
```

Result:

```text
[10,100,100,100,50]
```

because:

```text
index 1 → changed
index 2 → changed
index 3 → changed
index 4 → NOT changed
```

---

## Doubt 21: Does `fill()` return the modified array?

**No.**

It returns `void`.

Correct:

```java
Arrays.fill(a, 100);
```

Incorrect:

```java
int b[] = Arrays.fill(a, 100);
```

---

# 5. `Arrays.asList()` — DOUBTKILLER

This is the section where you should be especially careful.

---

## Doubt 22: What does `Arrays.asList()` actually produce?

For an appropriate reference-type array such as:

```java
String a[] = {"A", "B", "C"};
```

this:

```java
List list = Arrays.asList(a);
```

produces a **fixed-size List backed by the array**.

Think:

```text
          Array
       [A, B, C]
        ↕  ↕  ↕
          List
       [A, B, C]
```

---

## Doubt 23: Is the List a copy of the array?

**No.**

This is extremely important.

Example:

```java
String a[] = {"A", "B", "C"};

List list = Arrays.asList(a);

a[1] = "X";

System.out.println(list);
```

Output:

```text
[A, X, C]
```

The List sees the array modification.

---

## Doubt 24: If I change the List, does the array change?

If you change an existing position using `set()`, **yes**.

```java
String a[] = {"A", "B", "C"};

List list = Arrays.asList(a);

list.set(1, "X");

System.out.println(Arrays.toString(a));
```

Output:

```text
[A, X, C]
```

So:

```text
Array change
    ↕
List's existing-element change
```

---

# ⭐ Doubt 25: Why does `set()` work but `add()` fail?

This is the most important `asList()` question.

Consider:

```text
[A, B, C]
```

### `set()`

```java
list.set(1, "X");
```

becomes:

```text
[A, X, C]
```

Number of elements:

```text
3 → 3
```

So it works.

### `add()`

```java
list.add("D");
```

would require:

```text
[A,B,C]
    ↓
[A,B,C,D]
```

Number of elements:

```text
3 → 4
```

But the underlying array cannot change its length.

Therefore:

```text
set()   → size unchanged → ✅
add()   → size changes   → ❌
remove() → size changes  → ❌
```

---

# Doubt 26: Does `Arrays.asList()` create a resizable ArrayList?

**No.**

This is a very common mistake.

Do not think:

```text
Arrays.asList()
      ↓
ArrayList
      ↓
Resizable
```

Instead:

```text
Arrays.asList()
      ↓
fixed-size List backed by array
```

It is **not** the same thing as creating a normal resizable `ArrayList`.

---

# Doubt 27: What happens with `add()`?

Example:

```java
String a[] = {"A", "B", "C"};

List list = Arrays.asList(a);

list.add("D");
```

This throws:

```text
UnsupportedOperationException
```

The same applies to size-changing operations such as `remove()`.

---

# Doubt 28: What operations are allowed?

The easiest memory table is:

| Operation                            | `Arrays.asList()` result |
| ------------------------------------ | ------------------------ |
| `get()`                              | ✅                        |
| `set()`                              | ✅                        |
| `size()`                             | ✅                        |
| `contains()`                         | ✅                        |
| `add()`                              | ❌                        |
| `remove()`                           | ❌                        |
| Change original array element        | ✅ reflected              |
| Change existing element with `set()` | ✅ reflected in array     |

The important dividing line is:

```text
Changes SIZE → ❌
Changes EXISTING ELEMENT → ✅
```

---

# Doubt 29: Can `Arrays.asList()` be used with `int[]`?

Be careful.

Consider:

```java
int a[] = {10,20,30};

List list = Arrays.asList(a);
```

A primitive array such as `int[]` is **not treated as three separate `Integer` elements** by this method.

The important beginner rule is:

> `Arrays.asList()` works naturally as a list view when given an array of reference elements, such as `String[]`. Primitive arrays have special behavior because `int[]` itself is one array object.

So for teaching and exams, clearly distinguish:

```java
String[]     → array of reference elements
int[]        → primitive array
```

---

# 🔥 Doubt 30: Why does `Arrays.asList()` appear in the `Arrays` class?

Because it provides a bridge:

```text
Array
  ↓
Arrays.asList()
  ↓
List interface
```

This lets an array participate in APIs that expect a List, while retaining the fixed-size nature of the underlying array.

---

# 6. The Biggest Confusions — Side by Side

## `sort()` vs `binarySearch()`

```text
sort()
 ↓
Changes order of elements

binarySearch()
 ↓
Searches for an element
 ↓
Doesn't sort
```

---

## `equals()` vs `==`

```text
== 
 ↓
Reference identity

Arrays.equals()
 ↓
Array contents
```

---

## `fill()` vs `add()`

```text
fill()
 ↓
Changes existing values
 ↓
Size unchanged

add()
 ↓
Adds an element
 ↓
Size changes
```

---

## `asList()` vs normal resizable List

```text
Arrays.asList()
 ↓
Fixed-size view
 ↓
add/remove ❌
set ✅
```

A normal resizable `ArrayList` has different behavior.

---

# 7. The Most Important Range Rule

Both `sort()` and `fill()` can operate on ranges.

For example:

```java
Arrays.sort(a, 1, 4);
Arrays.fill(a, 1, 4, 100);
```

In both cases:

```text
1 → included
4 → excluded
```

So:

```text
[1,4)
```

means:

```text
1, 2, 3
```

not:

```text
1, 2, 3, 4
```

---

# 8. One Program That Exposes the Main Doubts

```java
import java.util.Arrays;
import java.util.List;

class Test
{
    public static void main(String[] args)
    {
        // sort()
        int a[] = {30, 10, 20};

        Arrays.sort(a);

        System.out.println(Arrays.toString(a));


        // binarySearch()
        int index = Arrays.binarySearch(a, 20);

        System.out.println(index);


        // equals()
        int b[] = {10, 20, 30};

        System.out.println(Arrays.equals(a, b));


        // fill()
        Arrays.fill(b, 100);

        System.out.println(Arrays.toString(b));


        // asList()
        String names[] = {"Java", "Python", "C"};

        List list = Arrays.asList(names);

        list.set(1, "HTML");

        System.out.println(list);
        System.out.println(Arrays.toString(names));
    }
}
```

Output:

```text
[10, 20, 30]
1
true
[100, 100, 100]
[Java, HTML, C]
[Java, HTML, C]
```

The last two outputs are the proof that:

```text
list.set(...)
      ↓
changes the backing array
```

---

# 🧠 FINAL DOUBTKILLER CHEAT SHEET

## `sort()`

```text
Q: What does it do?
A: Sorts the array.

Q: Modifies original?
A: YES.

Q: Returns array?
A: NO. void.

Q: Range?
A: YES.

Range:
from → inclusive
to   → exclusive
```

---

## `binarySearch()`

```text
Q: What does it do?
A: Searches an array.

Q: What does it return?
A: Index if found.

Q: What if not found?
A: Negative result.

Q: Automatically sorts?
A: NO.

Q: Should array be sorted?
A: YES, for the natural-order form.
```

---

## `equals()`

```text
Q: What does it compare?
A: Array contents.

Q: Does order matter?
A: YES.

Q: Does length matter?
A: YES.

Q: Same as ==?
A: NO.

==             → reference identity
Arrays.equals  → contents
```

---

## `fill()`

```text
Q: What does it do?
A: Replaces elements with a value.

Q: Adds elements?
A: NO.

Q: Changes array length?
A: NO.

Q: Can it fill a range?
A: YES.

Range:
from → inclusive
to   → exclusive
```

---

## `asList()`

```text
Q: What does it return?
A: Fixed-size List backed by array.

Q: Is it a copy?
A: NO.

Q: add()?
A: ❌ Unsupported.

Q: remove()?
A: ❌ Unsupported.

Q: set()?
A: ✅ Supported.

Q: Change array element?
A: Reflected in List.

Q: set() through List?
A: Reflected in array.
```

---

# 🔥 ULTIMATE MEMORY FORMULA

```text
Arrays
  |
  ├── sort()
  │      ↓
  │   ARRANGE
  │
  ├── binarySearch()
  │      ↓
  │     FIND
  │
  ├── equals()
  │      ↓
  │   COMPARE
  │
  ├── fill()
  │      ↓
  │   REPLACE
  │
  └── asList()
         ↓
       VIEW
```

> **`sort` = Arrange | `binarySearch` = Find | `equals` = Compare | `fill` = Replace | `asList` = Fixed-size View**

If you remember that sentence, you have the **core purpose** of all five methods.
