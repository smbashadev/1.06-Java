# 13. Arrays Utility Class in Java — TEACHME

We’ll learn this as if you are seeing `Arrays` for the first time.

> **Your rule is followed:** We will **not use the Generics concept** in this Collection Framework series.

---

# 1. First understand: What is `Arrays`?

Suppose you have an array:

```java
int a[] = {30, 10, 20};
```

You may want to:

* sort it
* search it
* compare it with another array
* put the same value into all positions
* view it as a `List`

Java provides the `Arrays` utility class for these operations.

It belongs to:

```java
java.util.Arrays
```

So we import it:

```java
import java.util.Arrays;
```

Think:

```text
                 Arrays
                   |
       ┌───────────┼───────────┐
       ↓           ↓           ↓
     Sort        Search      Compare
       |           |           |
    sort()   binarySearch()  equals()
       
       ↓
     Modify
       |
     fill()

       ↓
     View as List
       |
    asList()
```

---

# 2. `sort()`

## What problem does `sort()` solve?

Imagine:

```text
30  10  20
```

You want:

```text
10  20  30
```

That's exactly what `Arrays.sort()` does.

---

## Definition

`Arrays.sort()` sorts the elements of an array into ascending/natural order.

### Syntax

```java
Arrays.sort(array);
```

---

## Example

```java
import java.util.Arrays;

class Test
{
    public static void main(String[] args)
    {
        int a[] = {30, 10, 20};

        Arrays.sort(a);

        System.out.println(Arrays.toString(a));
    }
}
```

Output:

```text
[10, 20, 30]
```

---

## What happened?

Before:

```text
a
↓
[30, 10, 20]
```

After:

```text
a
↓
[10, 20, 30]
```

The important point is:

> `sort()` changes the existing array.

It doesn't give you a separate sorted array.

---

## Does `sort()` return anything?

No.

Its return type is:

```text
void
```

Therefore:

```java
Arrays.sort(a);
```

is correct.

But:

```java
int b[] = Arrays.sort(a);
```

is wrong.

---

## Easy way to remember

```text
sort()
  ↓
"Arrange the array"
```

---

# 3. `binarySearch()`

Now suppose your array is:

```text
10  20  30  40  50
```

You want to find:

```text
30
```

You can use:

```java
Arrays.binarySearch()
```

---

## Definition

`Arrays.binarySearch()` searches an array for a specified value using binary search.

### Syntax

```java
Arrays.binarySearch(array, value);
```

---

## Example

```java
import java.util.Arrays;

class Test
{
    public static void main(String[] args)
    {
        int a[] = {10, 20, 30, 40, 50};

        int result = Arrays.binarySearch(a, 30);

        System.out.println(result);
    }
}
```

Output:

```text
2
```

Why?

```text
Index:   0   1   2   3   4
Value:  10  20  30  40  50
                 ↑
                30
```

`30` is at index `2`.

Therefore:

```text
binarySearch()
      ↓
returns index
```

---

# 🚨 Most Important Rule of `binarySearch()`

For the natural-order form, the array should be sorted first.

For example:

```java
int a[] = {30, 10, 20};
```

Don't directly depend on:

```java
Arrays.binarySearch(a, 20);
```

Instead:

```java
Arrays.sort(a);

int result = Arrays.binarySearch(a, 20);
```

Complete:

```java
import java.util.Arrays;

class Test
{
    public static void main(String[] args)
    {
        int a[] = {30, 10, 20};

        Arrays.sort(a);

        int result = Arrays.binarySearch(a, 20);

        System.out.println(result);
    }
}
```

Output:

```text
1
```

Because after sorting:

```text
[10, 20, 30]
```

and:

```text
20 → index 1
```

---

## What if the element isn't found?

Suppose:

```java
int a[] = {10, 20, 30, 40, 50};

int result = Arrays.binarySearch(a, 35);
```

The result will be **negative**.

Therefore:

```java
if(result >= 0)
{
    System.out.println("Element Found");
}
else
{
    System.out.println("Element Not Found");
}
```

### Important doubt

Does "not found" always mean `-1`?

**No.**

`Arrays.binarySearch()` returns a negative value that encodes the insertion position.

For beginner-level checking:

```text
result >= 0
     ↓
FOUND

result < 0
     ↓
NOT FOUND
```

is the important rule.

---

# 4. `equals()`

Now imagine you have two arrays:

```text
Array A → [10, 20, 30]

Array B → [10, 20, 30]
```

You want to know:

> "Do these arrays contain the same elements in the same order?"

Use:

```java
Arrays.equals()
```

---

## Definition

`Arrays.equals()` compares two arrays element-by-element.

### Syntax

```java
Arrays.equals(array1, array2);
```

It returns:

```text
true
```

or:

```text
false
```

---

## Example

```java
import java.util.Arrays;

class Test
{
    public static void main(String[] args)
    {
        int a[] = {10, 20, 30};
        int b[] = {10, 20, 30};

        System.out.println(Arrays.equals(a, b));
    }
}
```

Output:

```text
true
```

---

# What if one element is different?

```java
int a[] = {10, 20, 30};
int b[] = {10, 20, 40};

System.out.println(Arrays.equals(a, b));
```

Output:

```text
false
```

Because:

```text
A → 10 20 30
B → 10 20 40
             ↑
          different
```

---

# Does order matter?

**Yes.**

```java
int a[] = {10, 20, 30};
int b[] = {30, 20, 10};

System.out.println(Arrays.equals(a, b));
```

Output:

```text
false
```

Even though both contain:

```text
10, 20, 30
```

their order is different.

Therefore:

```text
[10,20,30]
     ≠
[30,20,10]
```

for `Arrays.equals()`.

---

# `==` vs `Arrays.equals()`

This is a **very important Java doubt**.

Suppose:

```java
int a[] = {10, 20, 30};
int b[] = {10, 20, 30};
```

If you write:

```java
System.out.println(a == b);
```

you get:

```text
false
```

Why?

Because `==` checks whether both references point to the **same array object**.

But:

```java
System.out.println(Arrays.equals(a, b));
```

gives:

```text
true
```

because it compares the contents.

Remember:

```text
== 
 ↓
Reference identity

Arrays.equals()
 ↓
Array contents
```

---

# 5. `fill()`

Now imagine:

```text
[10, 20, 30, 40]
```

You want every element to become:

```text
100
```

You can use:

```java
Arrays.fill()
```

---

## Definition

`Arrays.fill()` replaces all elements of an array with the specified value.

### Syntax

```java
Arrays.fill(array, value);
```

---

## Example

```java
import java.util.Arrays;

class Test
{
    public static void main(String[] args)
    {
        int a[] = {10, 20, 30, 40};

        Arrays.fill(a, 100);

        System.out.println(Arrays.toString(a));
    }
}
```

Output:

```text
[100, 100, 100, 100]
```

---

# Does `fill()` add elements?

**No.**

Before:

```text
[10, 20, 30, 40]
```

After:

```text
[100, 100, 100, 100]
```

Still:

```text
4 elements
```

So:

```text
fill()
 ↓
Replace existing values
```

not:

```text
fill()
 ↓
Add values
```

---

# Fill only some elements

There is another form:

```java
Arrays.fill(array, fromIndex, toIndex, value);
```

Remember:

```text
fromIndex → included
toIndex   → excluded
```

Example:

```java
int a[] = {10, 20, 30, 40, 50};

Arrays.fill(a, 1, 4, 100);
```

Indexes:

```text
Index:   0   1   2   3   4
Value:  10  20  30  40  50
             └───────┘
```

Indexes `1`, `2`, and `3` become `100`.

Result:

```text
[10, 100, 100, 100, 50]
```

---

# 6. `asList()`

This is the method that usually causes the most confusion.

Suppose:

```java
String a[] = {"A", "B", "C"};
```

You want to view this array through a `List`.

You can use:

```java
Arrays.asList(a);
```

---

## Definition

`Arrays.asList()` returns a **fixed-size List backed by the supplied array**.

### Syntax

```java
Arrays.asList(array);
```

---

## Example

```java
import java.util.Arrays;
import java.util.List;

class Test
{
    public static void main(String[] args)
    {
        String a[] = {"A", "B", "C"};

        List list = Arrays.asList(a);

        System.out.println(list);
    }
}
```

Output:

```text
[A, B, C]
```

---

# What does "backed by the array" mean?

This is the key idea.

Think of it like this:

```text
             Original Array
             [A, B, C]
                ↕
             List View
             [A, B, C]
```

The List is not an independent copy.

It is connected to the original array.

---

# Change the array

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

Why?

Because:

```text
Original array
[A, X, C]
     ↓
List view
[A, X, C]
```

---

# Change the List using `set()`

Now reverse the situation.

```java
String a[] = {"A", "B", "C"};

List list = Arrays.asList(a);

list.set(1, "X");

System.out.println(list);
System.out.println(Arrays.toString(a));
```

Output:

```text
[A, X, C]
[A, X, C]
```

Again, both reflect the change.

---

# Can we `add()`?

**No.**

```java
list.add("D");
```

throws:

```text
UnsupportedOperationException
```

Why?

Because the array has a fixed size.

Adding would mean:

```text
3 elements
   ↓
4 elements
```

But the underlying array cannot change its length.

---

# Can we `remove()`?

**No.**

```java
list.remove("A");
```

also throws:

```text
UnsupportedOperationException
```

because removing would reduce the size.

---

# Can we `set()`?

**Yes.**

```java
list.set(1, "X");
```

works.

Why?

Because:

```text
[A, B, C]
```

becomes:

```text
[A, X, C]
```

The number of elements is still `3`.

---

# ⭐ Remember `asList()` Like This

```text
Arrays.asList(array)
          ↓
      List View
          ↓
     Fixed Size
          ↓
 ┌────────┼────────┐
 ↓        ↓        ↓
add()  remove()  set()
 ❌       ❌       ✅
```

---

# 7. Let's Connect All Five Methods

Suppose we start with:

```java
int a[] = {30, 10, 20};
```

### Step 1 — Sort

```java
Arrays.sort(a);
```

Now:

```text
[10, 20, 30]
```

---

### Step 2 — Search

```java
int result = Arrays.binarySearch(a, 20);
```

Result:

```text
1
```

---

### Step 3 — Compare

```java
int b[] = {10, 20, 30};

Arrays.equals(a, b);
```

Result:

```text
true
```

---

### Step 4 — Fill

```java
Arrays.fill(b, 100);
```

Now:

```text
[100, 100, 100]
```

---

### Step 5 — Convert/view as List

For a reference-type array:

```java
String names[] = {"Java", "Python", "C"};

List list = Arrays.asList(names);
```

Now:

```text
Array
 ↓
[A, B, C]
 ↓
Arrays.asList()
 ↓
List view
```

---

# 8. One Program — All Five

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
        int result = Arrays.binarySearch(a, 20);

        System.out.println(result);


        // equals()
        int b[] = {10, 20, 30};

        System.out.println(Arrays.equals(a, b));


        // fill()
        Arrays.fill(b, 100);

        System.out.println(Arrays.toString(b));


        // asList()
        String names[] = {"Java", "Python", "C"};

        List list = Arrays.asList(names);

        System.out.println(list);

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
[Java, Python, C]
[Java, HTML, C]
[Java, HTML, C]
```

The last two lines demonstrate the important connection:

```text
List
 ↕
Original array
```

---

# 9. `Arrays` vs `Collections`

You are studying both, so don't mix them.

### `Arrays`

Works primarily with arrays:

```text
Arrays.sort()
Arrays.binarySearch()
Arrays.equals()
Arrays.fill()
Arrays.asList()
```

### `Collections`

Provides utility operations for collection/list objects:

```text
Collections.sort()
Collections.reverse()
Collections.shuffle()
Collections.max()
Collections.min()
Collections.frequency()
Collections.binarySearch()
Collections.swap()
Collections.fill()
```

Think:

```text
             Utility Classes
                   |
             ┌─────┴─────┐
             ↓           ↓
          Arrays     Collections
             ↓           ↓
           Array     Collections
```

---

# 10. The Five Methods in Simple Language

## `sort()`

Question:

> "Can you arrange my array?"

Answer:

```java
Arrays.sort(a);
```

---

## `binarySearch()`

Question:

> "Where is this value in my sorted array?"

Answer:

```java
Arrays.binarySearch(a, value);
```

---

## `equals()`

Question:

> "Do these two arrays have the same contents in the same order?"

Answer:

```java
Arrays.equals(a, b);
```

---

## `fill()`

Question:

> "Can you replace every element with this value?"

Answer:

```java
Arrays.fill(a, value);
```

---

## `asList()`

Question:

> "Can I get a fixed-size List view of this array?"

Answer:

```java
Arrays.asList(a);
```

---

# 🧠 Final Mental Picture

```text
                    ARRAYS
                      |
        ┌─────────────┼──────────────┐
        ↓             ↓              ↓
      SORT          SEARCH         COMPARE
        |             |              |
     sort()     binarySearch()     equals()
        |
        ↓
      MODIFY
        |
      fill()
        |
        ↓
      LIST VIEW
        |
     asList()
```

### 🔥 Five rules

```text
sort()
→ Sorts the existing array.

binarySearch()
→ Searches and returns an index when found.
→ Use an appropriately sorted array.

equals()
→ Compares array contents.
→ Order matters.

fill()
→ Replaces existing values.
→ Does not change array length.

asList()
→ Creates a fixed-size List view backed by the array.
→ add() ❌
→ remove() ❌
→ set() ✅
```

### One-line memory trick

> **`sort` ARRANGES → `binarySearch` FINDS → `equals` COMPARES → `fill` REPLACES → `asList` VIEWS.**
