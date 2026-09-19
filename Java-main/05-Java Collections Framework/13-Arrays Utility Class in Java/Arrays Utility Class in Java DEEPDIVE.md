# 13. Arrays Utility Class in Java — DEEPDIVE

We will now study **every sub-concept individually and completely**.

> **Framework rule:** No Generics concept is used.
> We will use raw types where a `List` is necessary for `Arrays.asList()`.

---

# 0. `Arrays` Utility Class — Foundation

Before studying the individual methods, understand what `Arrays` itself is.

`Arrays` is a class in the `java.util` package.

```java
import java.util.Arrays;
```

It provides **static utility methods for working with arrays**.

For example:

```java
Arrays.sort(a);
Arrays.binarySearch(a, 20);
Arrays.equals(a, b);
Arrays.fill(a, 100);
Arrays.asList(a);
```

So:

```text
                 java.util.Arrays
                        |
        ┌───────────────┼────────────────┐
        ↓               ↓                ↓
      Sorting        Searching        Comparing
        |               |                |
      sort()      binarySearch()       equals()
        
        ┌───────────────┴────────────────┐
        ↓                                ↓
     Modifying                       Converting
        |                                |
      fill()                         asList()
```

---

# 1. `Arrays.sort()`

## 1.1 Definition

`Arrays.sort()` is used to sort the elements of an array according to their natural ordering.

For an `int` array, natural ordering means **ascending numerical order**.

```java
Arrays.sort(array);
```

---

## 1.2 Basic Example

```java
import java.util.Arrays;

class Test
{
    public static void main(String[] args)
    {
        int a[] = {50, 20, 40, 10, 30};

        Arrays.sort(a);

        System.out.println(Arrays.toString(a));
    }
}
```

Output:

```text
[10, 20, 30, 40, 50]
```

---

## 1.3 What exactly happened?

Original array:

```text
Index:   0   1   2   3   4
         ↓   ↓   ↓   ↓   ↓
Array:  50  20  40  10  30
```

After:

```text
Index:   0   1   2   3   4
         ↓   ↓   ↓   ↓   ↓
Array:  10  20  30  40  50
```

The **same array** has been rearranged.

---

## 1.4 Does `sort()` return a new array?

No.

The method sorts the supplied array itself.

```java
int a[] = {30, 10, 20};

Arrays.sort(a);
```

Now `a` itself contains:

```text
[10, 20, 30]
```

The method's return type is `void`.

Therefore this is incorrect:

```java
int b[] = Arrays.sort(a);    // WRONG
```

because `sort()` doesn't return an array.

---

## 1.5 Sorting part of an array

`Arrays` also provides an overloaded form:

```java
Arrays.sort(array, fromIndex, toIndex);
```

Important:

> `fromIndex` is inclusive, while `toIndex` is exclusive.

Example:

```java
int a[] = {50, 40, 30, 20, 10};

Arrays.sort(a, 1, 4);
```

Indexes:

```text
Index:   0   1   2   3   4
Value:  50  40  30  20  10
             └───────┘
             sorted
```

Indexes `1`, `2`, and `3` are sorted.

Result:

```text
[50, 20, 30, 40, 10]
```

Notice:

```text
fromIndex = 1  → included
toIndex   = 4  → excluded
```

---

## 1.6 `sort()` vs `Collections.sort()`

This is an important distinction.

```text
Arrays.sort()
     ↓
Array
```

while:

```text
Collections.sort()
     ↓
List
```

Conceptually:

```java
int a[] = {30, 10, 20};

Arrays.sort(a);
```

For a list:

```java
ArrayList list = new ArrayList();

list.add(30);
list.add(10);
list.add(20);

Collections.sort(list);
```

---

# 2. `Arrays.binarySearch()`

## 2.1 Definition

`Arrays.binarySearch()` searches for a specified value in an array using binary search.

Basic syntax:

```java
Arrays.binarySearch(array, key);
```

If found, it returns the **index** of the key.

---

## 2.2 Basic Example

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

Because:

```text
Index:   0   1   2   3   4
Value:  10  20  30  40  50
                 ↑
```

`30` is at index `2`.

---

## 2.3 Why is it called binary search?

Binary search repeatedly divides the search area.

Suppose:

```text
[10, 20, 30, 40, 50, 60, 70]
```

Search for `60`.

Instead of checking every element one by one, binary search examines the middle and eliminates half of the remaining search space.

Conceptually:

```text
Entire array
     ↓
Middle element
     ↓
Is target smaller or larger?
     ↓
Discard half
     ↓
Repeat
```

That is why binary search can be much faster than a simple sequential search for large, appropriately sorted arrays.

---

## 2.4 Most important rule: sorted array

For the natural-order form:

```java
Arrays.binarySearch(a, key);
```

the array should be sorted according to the same ordering.

Correct:

```java
int a[] = {30, 10, 20};

Arrays.sort(a);

int result = Arrays.binarySearch(a, 20);
```

After sorting:

```text
[10, 20, 30]
```

Result:

```text
1
```

---

## 2.5 What happens if the array is not sorted?

Do **not** assume that `binarySearch()` will sort the array automatically.

It does not.

This:

```java
Arrays.binarySearch(a, 20);
```

doesn't perform:

```text
sort → search
```

automatically.

The programmer must ensure that the array is appropriately ordered.

---

## 2.6 What if the element isn't found?

The method returns a negative value.

Example:

```java
int a[] = {10, 20, 30, 40, 50};

int result = Arrays.binarySearch(a, 35);

System.out.println(result);
```

The result is negative.

A safe presence check is:

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

---

## 2.7 Is "not found" always `-1`?

**No.**

This is a common mistake.

`Arrays.binarySearch()` returns a negative value encoding the insertion point when the key isn't found.

So don't write the rule:

```text
not found → -1
```

Instead remember:

```text
found
 ↓
index >= 0

not found
 ↓
negative result
```

---

## 2.8 `binarySearch()` does not modify the array

Suppose:

```text
[10,20,30,40]
```

After:

```java
Arrays.binarySearch(a, 30);
```

the array remains:

```text
[10,20,30,40]
```

Searching does not rearrange it.

---

# 3. `Arrays.equals()`

## 3.1 Definition

`Arrays.equals()` compares two arrays **element by element**.

Syntax:

```java
Arrays.equals(array1, array2);
```

Return type:

```text
boolean
```

Possible results:

```text
true
false
```

---

# 3.2 Example — Equal arrays

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

## 3.3 Example — Different element

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
a → [10,20,30]
b → [10,20,40]
             ↑
          different
```

---

# 3.4 Does order matter?

**Yes.**

Consider:

```java
int a[] = {10, 20, 30};
int b[] = {30, 20, 10};

System.out.println(Arrays.equals(a, b));
```

Output:

```text
false
```

Although both arrays contain the same three values, they are in different positions.

`Arrays.equals()` performs an **ordered element-by-element comparison**.

---

# 3.5 Does array length matter?

Yes.

```java
int a[] = {10, 20, 30};
int b[] = {10, 20, 30, 40};
```

They are not equal.

Result:

```text
false
```

So conceptually:

```text
Arrays.equals()
       ↓
Same length?
       ↓
Same element at index 0?
       ↓
Same element at index 1?
       ↓
Same element at index 2?
       ↓
...
       ↓
true / false
```

---

# 3.6 `==` vs `Arrays.equals()`

This is one of the most important array doubts.

Consider:

```java
int a[] = {10, 20, 30};
int b[] = {10, 20, 30};
```

Now:

```java
System.out.println(a == b);
```

produces:

```text
false
```

because `a == b` compares whether the two array references refer to the **same array object**.

But:

```java
System.out.println(Arrays.equals(a, b));
```

produces:

```text
true
```

because `Arrays.equals()` compares the array contents element-by-element.

Therefore:

```text
== 
 ↓
Reference identity

Arrays.equals()
 ↓
Element-by-element equality
```

---

# 4. `Arrays.fill()`

## 4.1 Definition

`Arrays.fill()` assigns the specified value to **every element** of an array.

Syntax:

```java
Arrays.fill(array, value);
```

---

## 4.2 Basic Example

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

## 4.3 Does `fill()` add elements?

**No.**

Before:

```text
[10,20,30,40]
```

After:

```text
[100,100,100,100]
```

The array still contains **four elements**.

So:

```text
fill()
 ↓
Replace values

NOT

fill()
 ↓
Add elements
```

---

# 4.4 Filling only a portion

There is an overloaded form:

```java
Arrays.fill(array, fromIndex, toIndex, value);
```

Again:

```text
fromIndex → inclusive
toIndex   → exclusive
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

Indexes `1`, `2`, and `3` are replaced.

Result:

```text
[10, 100, 100, 100, 50]
```

---

## 4.5 `fill()` vs initialization

You can initialize:

```java
int a[] = {10, 20, 30};
```

Then:

```java
Arrays.fill(a, 5);
```

produces:

```text
[5,5,5]
```

`fill()` is particularly useful when an array already exists and you want to assign the same value to multiple positions.

---

# 5. `Arrays.asList()`

This method requires the most careful explanation.

---

## 5.1 Definition

`Arrays.asList()` creates a **fixed-size List backed by the supplied array**.

Basic syntax:

```java
Arrays.asList(array);
```

Example:

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

# 5.2 Is the returned object an actual independent copy?

**No.**

This is extremely important.

The List returned by `Arrays.asList()` is backed by the original array.

Conceptually:

```text
        Original Array
        [A, B, C]
          ↕  ↕  ↕
        List View
        [A, B, C]
```

The list and array are connected.

---

# 5.3 Change the array — what happens to the List?

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

Because the List is backed by the same array.

```text
Array:
[A, X, C]
   ↓
List view:
[A, X, C]
```

---

# 5.4 Change the List using `set()` — what happens to the array?

Example:

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

Again, both reflect the same underlying data.

---

# 5.5 Can we use `add()`?

**No.**

The List returned by `Arrays.asList()` is fixed-size.

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

Why?

Because adding would increase the List's size, but the List is backed by an array whose size is fixed.

---

# 5.6 Can we use `remove()`?

Also **no**.

```java
list.remove("A");
```

throws:

```text
UnsupportedOperationException
```

because removal would change the size.

---

# 5.7 Can we use `set()`?

**Yes.**

```java
list.set(1, "X");
```

is supported.

Why?

Because `set()` replaces an existing element.

It doesn't change the number of elements.

```text
add()
 ↓
size changes
 ↓
❌

remove()
 ↓
size changes
 ↓
❌

set()
 ↓
size stays same
 ↓
✅
```

---

# 5.8 The most important `asList()` rule

Memorize this:

```text
Arrays.asList(array)
          ↓
      Fixed-size
          ↓
 ┌────────┼─────────┐
 ↓        ↓         ↓
add()   remove()   set()
 ❌        ❌        ✅
```

---

# 5.9 Why is `asList()` useful?

It provides a bridge between arrays and list-based APIs.

Conceptually:

```text
Array
  ↓
Arrays.asList()
  ↓
List view
```

For example:

```java
String a[] = {"Java", "Python", "C"};

List list = Arrays.asList(a);

System.out.println(list);
```

This lets you work with the array through the `List` interface for operations that don't require changing the size.

---

# 5.10 `asList()` with primitive arrays — Important Trap

This is a particularly important point.

Consider:

```java
int a[] = {10, 20, 30};

List list = Arrays.asList(a);
```

Do **not** expect:

```text
[10, 20, 30]
```

because `int[]` is itself one object, and `Arrays.asList()` is designed around an array of reference elements.

Without using the Generics concept, the safe lesson is:

> `Arrays.asList()` behaves as expected for an array such as `String[]`, while primitive arrays such as `int[]` have special behavior and should not be treated as an ordinary list of individual `int` values.

This is one reason `Arrays.asList()` is commonly demonstrated with:

```java
String a[] = {"A", "B", "C"};
```

rather than:

```java
int a[] = {10,20,30};
```

---

# 6. Deep Comparison of the Five Methods

## `sort()`

```text
Array
 ↓
Rearrange
 ↓
Ascending/natural order
```

Changes array:

```text
YES
```

Returns:

```text
void
```

---

## `binarySearch()`

```text
Array
 ↓
Search
 ↓
Index / negative result
```

Changes array:

```text
NO
```

Important:

```text
Array should be appropriately sorted
```

---

## `equals()`

```text
Array 1
   +
Array 2
   ↓
Element-by-element comparison
   ↓
true / false
```

Changes arrays:

```text
NO
```

---

## `fill()`

```text
Array
 ↓
Replace values
 ↓
Specified value
```

Changes array:

```text
YES
```

Size:

```text
UNCHANGED
```

---

## `asList()`

```text
Array
 ↓
Arrays.asList()
 ↓
Fixed-size List view
 ↓
Backed by array
```

Size-changing operations:

```text
add()    ❌
remove() ❌
```

Existing-value replacement:

```text
set()    ✅
```

---

# 🔥 `Arrays` vs `Collections` — DEEP Comparison

| Operation     | Arrays                  | Collections                  |
| ------------- | ----------------------- | ---------------------------- |
| Sort          | `Arrays.sort()`         | `Collections.sort()`         |
| Binary search | `Arrays.binarySearch()` | `Collections.binarySearch()` |
| Fill          | `Arrays.fill()`         | `Collections.fill()`         |
| Reverse       | —                       | `Collections.reverse()`      |
| Shuffle       | —                       | `Collections.shuffle()`      |
| Max           | —                       | `Collections.max()`          |
| Min           | —                       | `Collections.min()`          |
| Frequency     | —                       | `Collections.frequency()`    |
| Swap          | —                       | `Collections.swap()`         |
| Array → List  | `Arrays.asList()`       | —                            |

The conceptual distinction is:

```text
Arrays
  ↓
Array-oriented utility operations

Collections
  ↓
Collection/List-oriented utility operations
```

---

# 🚨 Deep Doubt Killer

## Doubt 1

Does:

```java
Arrays.sort(a);
```

return the sorted array?

**No.**

It sorts `a` itself and returns `void`.

---

## Doubt 2

Does:

```java
Arrays.binarySearch(a, 30);
```

return `30`?

**No.**

It returns the **index** when found.

---

## Doubt 3

Does binary search sort the array first?

**No.**

The programmer must provide an appropriately sorted array for the natural-order form.

---

## Doubt 4

Does:

```java
Arrays.equals(a, b)
```

compare references?

**No.**

It compares array contents.

`a == b` is the reference comparison.

---

## Doubt 5

Does `equals()` ignore order?

**No.**

Order matters.

```text
[10,20,30] ≠ [30,20,10]
```

---

## Doubt 6

Does `fill()` increase the array size?

**No.**

Arrays have fixed length.

---

## Doubt 7

Does `asList()` create an independent copy?

**No.**

The returned List is backed by the original array.

---

## Doubt 8

Can the `asList()` result use `add()`?

**No.**

It is fixed-size.

---

## Doubt 9

Can the `asList()` result use `set()`?

**Yes.**

It replaces an existing element without changing size.

---

## Doubt 10

Does changing the array affect the `asList()` result?

**Yes.**

Because the List is backed by the array.

---

# 🧠 One Complete Program

This program puts the five concepts together without using Generics:

```java
import java.util.Arrays;
import java.util.List;

class Test
{
    public static void main(String[] args)
    {
        int a[] = {30, 10, 20};

        // 1. sort()
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));

        // 2. binarySearch()
        int result = Arrays.binarySearch(a, 20);
        System.out.println(result);

        // 3. equals()
        int b[] = {10, 20, 30};
        System.out.println(Arrays.equals(a, b));

        // 4. fill()
        Arrays.fill(b, 100);
        System.out.println(Arrays.toString(b));

        // 5. asList()
        String names[] = {"A", "B", "C"};
        List list = Arrays.asList(names);

        System.out.println(list);

        list.set(1, "X");

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
[A, B, C]
[A, X, C]
[A, X, C]
```

The last two lines prove the important `asList()` relationship:

```text
List
 ↕
Original array
```

---

# 🎯 DEEPDIVE MASTER MAP

```text
                 java.util.Arrays
                        |
       ┌────────────────┼─────────────────┐
       |                |                 |
    ARRANGE           SEARCH           COMPARE
       |                |                 |
     sort()       binarySearch()       equals()
       |
       |
    MODIFY
       |
    fill()
       |
       |
   CONVERT / VIEW
       |
   asList()
```

### Final rules to permanently remember

```text
sort()
→ Sorts the existing array.

binarySearch()
→ Searches an appropriately sorted array.
→ Returns index if found.
→ Returns negative value if not found.

equals()
→ Compares contents element-by-element.
→ Order matters.
→ Returns boolean.

fill()
→ Replaces existing values.
→ Does not change array length.

asList()
→ Creates a fixed-size List view backed by the array.
→ add() ❌
→ remove() ❌
→ set() ✅
→ Array and List view reflect changes to existing elements.
```

### ⭐ The five-word memory trick

```text
sort       → ARRANGE
binary     → SEARCH
equals     → COMPARE
fill       → REPLACE
asList     → VIEW
```

**No Generics concept has been used in this DEEPDIVE.**
