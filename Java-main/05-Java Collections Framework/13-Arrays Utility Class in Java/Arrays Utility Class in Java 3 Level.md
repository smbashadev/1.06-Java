# 13. Arrays Utility Class in Java — 3LEVEL

The **3LEVEL method** means we understand every concept at three depths:

```text
LEVEL 1 → What is it?
LEVEL 2 → How does it work?
LEVEL 3 → What are the important doubts/rules?
```

We will apply this separately to:

1. `sort()`
2. `binarySearch()`
3. `equals()`
4. `fill()`
5. `asList()`

> We will **not use Generics**.

---

# 1. `Arrays.sort()`

## LEVEL 1 — What is `sort()`?

`Arrays.sort()` is used to arrange the elements of an array in sorted order.

Import:

```java
import java.util.Arrays;
```

Syntax:

```java
Arrays.sort(array);
```

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

### Remember

```text
sort()
 ↓
Arrange array
```

---

## LEVEL 2 — How does it work?

Before:

```text
Index:   0   1   2
         ↓   ↓   ↓
Array:  30  10  20
```

After:

```text
Index:   0   1   2
         ↓   ↓   ↓
Array:  10  20  30
```

`sort()` changes the **existing array**.

It does not create a new array for you.

Also, `sort()` returns `void`.

Therefore:

```java
Arrays.sort(a);       // ✅
```

but:

```java
int b[] = Arrays.sort(a);    // ❌
```

is incorrect.

---

## LEVEL 3 — Important rules

### Rule 1: It modifies the original array

```java
int a[] = {30, 10, 20};

Arrays.sort(a);

System.out.println(Arrays.toString(a));
```

Result:

```text
[10, 20, 30]
```

### Rule 2: You can sort a range

```java
Arrays.sort(a, 1, 4);
```

Here:

```text
fromIndex → inclusive
toIndex   → exclusive
```

Example:

```java
int a[] = {50, 40, 30, 20, 10};

Arrays.sort(a, 1, 4);
```

Result:

```text
[50, 20, 30, 40, 10]
```

Only indexes `1`, `2`, and `3` were sorted.

---

# 2. `Arrays.binarySearch()`

## LEVEL 1 — What is `binarySearch()`?

`Arrays.binarySearch()` searches for an element in an array.

Syntax:

```java
Arrays.binarySearch(array, value);
```

Example:

```java
int a[] = {10, 20, 30, 40, 50};

int result = Arrays.binarySearch(a, 30);

System.out.println(result);
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
```

So:

```text
30 → index 2
```

---

## LEVEL 2 — How does it work?

Binary search works by repeatedly reducing the search area.

Conceptually:

```text
Entire sorted array
        ↓
Check middle
        ↓
Target smaller or larger?
        ↓
Discard half
        ↓
Continue
```

For example:

```text
[10, 20, 30, 40, 50, 60, 70]
```

Search:

```text
60
```

Instead of checking every element sequentially, binary search uses the ordering to eliminate large portions of the search space.

---

## LEVEL 3 — Important rules

### Rule 1: Array should be appropriately sorted

Usually:

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

### Rule 2: It returns the index

It does **not** return the searched value.

```text
Searching 20
     ↓
returns index 1
```

### Rule 3: Not found gives a negative result

```java
int a[] = {10, 20, 30};

int result = Arrays.binarySearch(a, 25);
```

`result` is negative.

So use:

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

### Rule 4: It does not sort automatically

This:

```java
Arrays.binarySearch(a, 20);
```

doesn't mean:

```text
sort → search
```

automatically.

---

# 3. `Arrays.equals()`

## LEVEL 1 — What is `equals()`?

`Arrays.equals()` compares two arrays element-by-element.

Syntax:

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

Example:

```java
int a[] = {10, 20, 30};
int b[] = {10, 20, 30};

System.out.println(Arrays.equals(a, b));
```

Output:

```text
true
```

---

## LEVEL 2 — How does it work?

It checks the arrays' contents.

For:

```text
A → [10, 20, 30]

B → [10, 20, 30]
```

it compares:

```text
A[0] == B[0] → 10 == 10 → true
A[1] == B[1] → 20 == 20 → true
A[2] == B[2] → 30 == 30 → true
```

Therefore:

```text
true
```

---

## LEVEL 3 — Important rules

### Rule 1: Length matters

```text
[10,20,30]
[10,20,30,40]
```

are not equal.

Result:

```text
false
```

### Rule 2: Order matters

```text
[10,20,30]
[30,20,10]
```

returns:

```text
false
```

### Rule 3: `==` and `Arrays.equals()` are different

```java
int a[] = {10, 20, 30};
int b[] = {10, 20, 30};

System.out.println(a == b);
```

Result:

```text
false
```

because `==` compares array references.

But:

```java
System.out.println(Arrays.equals(a, b));
```

returns:

```text
true
```

So remember:

```text
== 
 ↓
Same array object?

Arrays.equals()
 ↓
Same array contents?
```

---

# 4. `Arrays.fill()`

## LEVEL 1 — What is `fill()`?

`Arrays.fill()` puts the same value into all elements of an array.

Syntax:

```java
Arrays.fill(array, value);
```

Example:

```java
int a[] = {10, 20, 30, 40};

Arrays.fill(a, 100);

System.out.println(Arrays.toString(a));
```

Output:

```text
[100, 100, 100, 100]
```

---

## LEVEL 2 — How does it work?

Before:

```text
[10, 20, 30, 40]
```

Call:

```java
Arrays.fill(a, 100);
```

After:

```text
[100, 100, 100, 100]
```

It **replaces values**.

It doesn't add elements.

---

## LEVEL 3 — Important rules

### Rule 1: Array length doesn't change

```text
Before → 4 elements
After  → 4 elements
```

Only the values change.

### Rule 2: You can fill a range

Syntax:

```java
Arrays.fill(array, fromIndex, toIndex, value);
```

Example:

```java
int a[] = {10, 20, 30, 40, 50};

Arrays.fill(a, 1, 4, 100);
```

Result:

```text
[10, 100, 100, 100, 50]
```

because:

```text
1 → included
4 → excluded
```

Therefore indexes:

```text
1, 2, 3
```

are filled.

### Rule 3: It modifies the original array

```text
Original array
     ↓
Arrays.fill()
     ↓
Same array with changed values
```

---

# 5. `Arrays.asList()`

This is the most important one to understand carefully.

---

## LEVEL 1 — What is `asList()`?

`Arrays.asList()` provides a **fixed-size List backed by an array**.

Syntax:

```java
Arrays.asList(array);
```

Example:

```java
String a[] = {"A", "B", "C"};

List list = Arrays.asList(a);

System.out.println(list);
```

Output:

```text
[A, B, C]
```

---

## LEVEL 2 — How does it work?

The List is connected to the original array.

Think:

```text
        Array
     [A, B, C]
       ↕ ↕ ↕
        List
     [A, B, C]
```

It is not an independent copy.

### Change array

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

### Change List using `set()`

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

So both are connected.

---

# LEVEL 3 — Important rules

## Rule 1: `add()` is not supported

```java
list.add("D");
```

throws:

```text
UnsupportedOperationException
```

Why?

Because adding changes the size:

```text
3 elements → 4 elements
```

The underlying array cannot change length.

---

## Rule 2: `remove()` is not supported

```java
list.remove("A");
```

also throws:

```text
UnsupportedOperationException
```

because:

```text
3 elements → 2 elements
```

would change the size.

---

## Rule 3: `set()` is supported

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

The size remains `3`.

---

## ⭐ The `asList()` rule

```text
Arrays.asList()
       ↓
Fixed-size List
       |
   ┌───┼────┐
   ↓   ↓    ↓
 add remove set
  ❌    ❌    ✅
```

---

# 6. All Five Together

Now connect everything.

Suppose:

```java
int a[] = {30, 10, 20};
```

### `sort()`

```java
Arrays.sort(a);
```

Result:

```text
[10, 20, 30]
```

### `binarySearch()`

```java
Arrays.binarySearch(a, 20);
```

Result:

```text
1
```

### `equals()`

```java
int b[] = {10, 20, 30};

Arrays.equals(a, b);
```

Result:

```text
true
```

### `fill()`

```java
Arrays.fill(b, 100);
```

Result:

```text
[100, 100, 100]
```

### `asList()`

For example:

```java
String names[] = {"Java", "Python", "C"};

List list = Arrays.asList(names);
```

Result:

```text
[Java, Python, C]
```

---

# 7. 3LEVEL Master Table

| Method           | LEVEL 1           | LEVEL 2                          | LEVEL 3                                               |
| ---------------- | ----------------- | -------------------------------- | ----------------------------------------------------- |
| `sort()`         | Sort array        | Changes existing array           | Range available; `void` return                        |
| `binarySearch()` | Search array      | Returns index                    | Use appropriately sorted array; negative if not found |
| `equals()`       | Compare arrays    | Compares contents                | Length and order matter; different from `==`          |
| `fill()`         | Replace values    | Same value assigned to positions | Range available; array size unchanged                 |
| `asList()`       | Array → List view | Backed by original array         | Fixed-size; `set()` works, `add/remove` don't         |

---

# 8. Final Memory Map

```text
                 Arrays
                   |
       ┌───────────┼────────────┐
       ↓           ↓            ↓
     sort()   binarySearch()  equals()
       ↓           ↓            ↓
    ARRANGE       FIND        COMPARE
       
       ↓
    fill()
       ↓
    REPLACE

       ↓
   asList()
       ↓
    LIST VIEW
```

## 🔥 One-line memory trick

> **`sort()` ARRANGES → `binarySearch()` FINDS → `equals()` COMPARES → `fill()` REPLACES → `asList()` CREATES A FIXED-SIZE LIST VIEW.**

And the five most important exam/interview facts are:

```text
sort()
→ modifies the array.

binarySearch()
→ returns index when found.
→ array should be appropriately sorted.

equals()
→ compares contents, not references.
→ order matters.

fill()
→ replaces values, not array size.

asList()
→ fixed-size List backed by the original array.
→ add() ❌
→ remove() ❌
→ set() ✅
```
