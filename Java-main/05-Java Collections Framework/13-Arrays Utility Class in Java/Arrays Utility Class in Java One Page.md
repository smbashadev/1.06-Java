# 13. Arrays Utility Class in Java — ONEPAGE

> **Important rule:** We will **not use Generics** in this entire Collection Framework study.
> Here we focus only on the `java.util.Arrays` utility class and its specified methods.

---

# 1. `Arrays` Utility Class

`Arrays` is a **utility class** in `java.util` package that provides static methods for performing operations on arrays.

Import:

```java
import java.util.Arrays;
```

Basic idea:

```text
Array
  ↓
Arrays utility class
  ↓
sort()
binarySearch()
equals()
fill()
asList()
```

### `Collection` vs `Collections` vs `Arrays`

| Name          | What is it?   | Purpose                       |
| ------------- | ------------- | ----------------------------- |
| `Collection`  | Interface     | Represents a group of objects |
| `Collections` | Utility class | Operations on collections     |
| `Arrays`      | Utility class | Operations on arrays          |

---

# 2. `Arrays.sort()`

## Definition

`Arrays.sort()` is used to arrange the elements of an array in ascending/natural order.

### Syntax

```java
Arrays.sort(array);
```

### Example

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

### How it works

Before:

```text
[30, 10, 20]
```

After:

```text
[10, 20, 30]
```

### Important

`Arrays.sort()` modifies the **existing array**.

It doesn't create another array just to give you the sorted result.

### Don't confuse

```text
Arrays.sort()
       ↓
Array

Collections.sort()
       ↓
List
```

---

# 3. `Arrays.binarySearch()`

## Definition

`Arrays.binarySearch()` searches for an element in an array and returns its index if found.

### Syntax

```java
Arrays.binarySearch(array, key);
```

### Example

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
Index:  0   1   2   3   4
Value: 10  20  30  40  50
                ↑
               30
```

Therefore:

```text
binarySearch() → 2
```

### ⚠️ Critical rule

For the natural-order version, the array should be sorted before binary searching.

Correct flow:

```text
Unsorted array
      ↓
Arrays.sort()
      ↓
Sorted array
      ↓
Arrays.binarySearch()
```

Example:

```java
int a[] = {30, 10, 20};

Arrays.sort(a);

int result = Arrays.binarySearch(a, 20);

System.out.println(result);
```

Output:

```text
1
```

### If element isn't found

The result is a **negative value**.

Do not assume:

```text
Not found → always -1
```

For `Arrays.binarySearch()`, the negative result encodes the insertion position.

---

# 4. `Arrays.equals()`

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

if the corresponding elements are equal and the arrays have the same length.

Otherwise:

```text
false
```

---

## Example 1 — Equal arrays

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

## Example 2 — Different elements

```java
int a[] = {10, 20, 30};
int b[] = {10, 20, 40};

System.out.println(Arrays.equals(a, b));
```

Output:

```text
false
```

---

## Example 3 — Different order

```java
int a[] = {10, 20, 30};
int b[] = {30, 20, 10};

System.out.println(Arrays.equals(a, b));
```

Output:

```text
false
```

### Important

`Arrays.equals()` checks **order**.

Therefore:

```text
[10,20,30]
```

and:

```text
[30,20,10]
```

are not equal.

---

# 5. `Arrays.fill()`

## Definition

`Arrays.fill()` replaces **all existing elements** of an array with the specified value.

### Syntax

```java
Arrays.fill(array, value);
```

### Example

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

### Before

```text
[10, 20, 30, 40]
```

### After

```text
[100, 100, 100, 100]
```

### Critical point

`fill()` does **not** add elements.

It replaces existing array elements.

```text
fill()
 ↓
Replace values
```

Array size remains unchanged.

```text
Before → 4 elements
After  → 4 elements
```

---

# 6. `Arrays.asList()`

## Definition

`Arrays.asList()` converts an array into a **List view backed by the array**.

### Syntax

```java
Arrays.asList(array);
```

Example:

```java
import java.util.Arrays;

class Test
{
    public static void main(String[] args)
    {
        String a[] = {"A", "B", "C"};

        System.out.println(Arrays.asList(a));
    }
}
```

Output:

```text
[A, B, C]
```

---

## Why is `asList()` important?

It provides a bridge between:

```text
Array
  ↓
Arrays.asList()
  ↓
List view
```

The resulting list is **fixed-size**.

That means operations that change the size, such as adding or removing elements, are not supported.

For example, conceptually:

```java
String a[] = {"A", "B", "C"};

List list = Arrays.asList(a);

list.add("D");       // UnsupportedOperationException
```

Similarly:

```java
list.remove("A");    // UnsupportedOperationException
```

---

## But can we replace an existing element?

**Yes.**

For example:

```java
String a[] = {"A", "B", "C"};

List list = Arrays.asList(a);

list.set(1, "X");

System.out.println(list);
```

Output:

```text
[A, X, C]
```

Because the list is backed by the array, the array also reflects the replacement:

```text
a → [A, X, C]
```

### Very important distinction

```text
add/remove
    ↓
Changes size
    ↓
Not supported

set()
    ↓
Changes existing value
    ↓
Supported
```

---

# 🔥 `Arrays` Utility Methods — ONEPAGE MASTER TABLE

| Method           | Purpose                    | Returns                | Changes original array? |
| ---------------- | -------------------------- | ---------------------- | ----------------------- |
| `sort()`         | Sorts array                | `void`                 | ✅ Yes                   |
| `binarySearch()` | Searches sorted array      | index / negative value | ❌ No                    |
| `equals()`       | Compares arrays            | `boolean`              | ❌ No                    |
| `fill()`         | Replaces all values        | `void`                 | ✅ Yes                   |
| `asList()`       | Creates List view of array | `List`                 | View is backed by array |

---

# 🧠 `Collections` vs `Arrays`

This distinction is extremely important.

```text
                 Utility Classes
                      |
              ┌───────┴───────┐
              │               │
          Collections        Arrays
              │               │
         Collection/List      Array
              │               │
         sort(list)        sort(array)
         reverse(list)     binarySearch(array)
         shuffle(list)     equals(array,array)
         max(list)         fill(array,value)
         min(list)         asList(array)
```

### Easy memory trick

```text
Collections → Collection-related operations

Arrays → Array-related operations
```

---

# 🎯 Final ONEPAGE Memory

```text
Arrays.sort()
    ↓
Arrange array

Arrays.binarySearch()
    ↓
Search array → returns index / negative value

Arrays.equals()
    ↓
Compare two arrays → true / false

Arrays.fill()
    ↓
Replace all existing array values

Arrays.asList()
    ↓
Array → fixed-size List view
```

### ⭐ Most important `asList()` rule

```text
Arrays.asList(array)
        ↓
Fixed-size List
        ↓
add() ❌
remove() ❌
set() ✅
        ↓
Backed by original array
```

And the central distinction:

> **`Collections` works with collections/lists; `Arrays` provides utility operations for arrays.**
