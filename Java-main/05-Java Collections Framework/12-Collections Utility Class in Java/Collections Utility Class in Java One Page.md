# 12. Collections Utility Class in Java — ONEPAGE

> **Rule:** No Generics are used anywhere.
> `Collections` is a utility class from `java.util` that provides ready-made operations for working with collections, especially `List` objects.

```java
import java.util.Collections;
```

---

# 1. `sort()`

### Definition

`sort()` arranges the elements of a `List` in ascending order according to their natural ordering, or according to a supplied `Comparator`.

### Syntax

```java
Collections.sort(list);
```

### Example

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        list.add(30);
        list.add(10);
        list.add(20);

        Collections.sort(list);

        System.out.println(list);
    }
}
```

Output:

```text
[10, 20, 30]
```

### Remember

```text
sort()
  ↓
Arrange elements
  ↓
Ascending / natural ordering
```

With Comparator:

```java
Collections.sort(list, new MyComparator());
```

---

# 2. `reverse()`

### Definition

`reverse()` reverses the **current order** of the elements in a `List`.

It does not perform a new ascending sort.

### Syntax

```java
Collections.reverse(list);
```

### Example

```java
ArrayList list = new ArrayList();

list.add(10);
list.add(20);
list.add(30);

Collections.reverse(list);

System.out.println(list);
```

Output:

```text
[30, 20, 10]
```

If the original list is:

```text
[30, 10, 20]
```

then `reverse()` produces:

```text
[20, 10, 30]
```

### Remember

```text
reverse()
   ↓
Current first ↔ Current last
```

**Important:** `reverse()` and descending `sort()` are not conceptually the same operation.

---

# 3. `shuffle()`

### Definition

`shuffle()` randomly rearranges the elements of a `List`.

### Syntax

```java
Collections.shuffle(list);
```

### Example

```java
ArrayList list = new ArrayList();

list.add(10);
list.add(20);
list.add(30);
list.add(40);

Collections.shuffle(list);

System.out.println(list);
```

A possible output:

```text
[30, 10, 40, 20]
```

Another execution may produce a different order.

### Remember

```text
shuffle()
   ↓
Randomly rearrange
```

It does **not** sort the list.

---

# 4. `max()`

### Definition

`max()` returns the **largest element** according to the elements' natural ordering, or according to a supplied Comparator.

### Syntax

```java
Collections.max(list);
```

### Example

```java
ArrayList list = new ArrayList();

list.add(40);
list.add(10);
list.add(70);
list.add(20);

Object result = Collections.max(list);

System.out.println(result);
```

Output:

```text
70
```

Because we are not using Generics, the return type is handled as `Object`.

### Remember

```text
max()
  ↓
Largest element
```

---

# 5. `min()`

### Definition

`min()` returns the **smallest element** according to natural ordering or a supplied Comparator.

### Syntax

```java
Collections.min(list);
```

### Example

```java
ArrayList list = new ArrayList();

list.add(40);
list.add(10);
list.add(70);
list.add(20);

Object result = Collections.min(list);

System.out.println(result);
```

Output:

```text
10
```

### Remember

```text
min()
  ↓
Smallest element
```

---

# 6. `frequency()`

### Definition

`frequency()` counts how many times a particular element occurs in a collection.

### Syntax

```java
Collections.frequency(collection, object);
```

### Example

```java
ArrayList list = new ArrayList();

list.add(10);
list.add(20);
list.add(10);
list.add(30);
list.add(10);

int count = Collections.frequency(list, 10);

System.out.println(count);
```

Output:

```text
3
```

Because `10` occurs three times.

### Remember

```text
frequency(list, element)
       ↓
How many times does element occur?
```

---

# 7. `binarySearch()`

### Definition

`binarySearch()` searches for an element in a **sorted List** using binary search.

### Syntax

```java
Collections.binarySearch(list, key);
```

### Example

```java
ArrayList list = new ArrayList();

list.add(10);
list.add(20);
list.add(30);
list.add(40);
list.add(50);

int result = Collections.binarySearch(list, 30);

System.out.println(result);
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
                index 2
```

### VERY IMPORTANT

The list should be sorted according to the ordering used for the search.

For natural ordering:

```java
Collections.sort(list);
int result = Collections.binarySearch(list, 30);
```

### If the element isn't found

The return value is a **negative value** indicating the search did not find the key; the exact negative result also encodes the insertion-point information.

### Remember

```text
binarySearch()
       ↓
Search quickly
       ↓
List must be appropriately sorted
```

---

# 8. `swap()`

### Definition

`swap()` exchanges the elements at two specified positions in a `List`.

### Syntax

```java
Collections.swap(list, index1, index2);
```

### Example

```java
ArrayList list = new ArrayList();

list.add(10);
list.add(20);
list.add(30);

Collections.swap(list, 0, 2);

System.out.println(list);
```

Before:

```text
Index:   0   1   2
Value:  10  20  30
```

After:

```text
Index:   0   1   2
Value:  30  20  10
```

### Remember

```text
swap(list, 0, 2)
       ↓
Exchange index 0 and index 2
```

---

# 9. `fill()`

### Definition

`fill()` replaces **every element** in a `List` with the specified value.

### Syntax

```java
Collections.fill(list, value);
```

### Example

```java
ArrayList list = new ArrayList();

list.add(10);
list.add(20);
list.add(30);

Collections.fill(list, 100);

System.out.println(list);
```

Output:

```text
[100, 100, 100]
```

### VERY IMPORTANT

`fill()` does **not** add new elements.

Before:

```text
[10, 20, 30]
```

After:

```text
[100, 100, 100]
```

The size remains:

```text
3
```

### Remember

```text
fill()
  ↓
Replace every existing element
```

---

# 10. All Methods at a Glance

| Method           | Purpose                       | Example                      |
| ---------------- | ----------------------------- | ---------------------------- |
| `sort()`         | Sort list                     | `[30,10,20] → [10,20,30]`    |
| `reverse()`      | Reverse current order         | `[10,20,30] → [30,20,10]`    |
| `shuffle()`      | Randomly rearrange            | Random order                 |
| `max()`          | Find largest element          | `10,50,20 → 50`              |
| `min()`          | Find smallest element         | `10,50,20 → 10`              |
| `frequency()`    | Count occurrences             | `10,20,10 → frequency(10)=2` |
| `binarySearch()` | Search sorted list            | Find `30` → index            |
| `swap()`         | Exchange two positions        | index `0 ↔ 2`                |
| `fill()`         | Replace all existing elements | `[10,20,30] → [5,5,5]`       |

---

# 11. Most Important Differences

### `sort()` vs `reverse()`

```text
sort()
↓
Changes order according to sorting rule

reverse()
↓
Simply reverses the current order
```

Example:

```text
Original:
[30, 10, 20]

sort():
[10, 20, 30]

reverse():
[20, 10, 30]
```

---

### `sort()` vs `shuffle()`

```text
sort()
↓
Ordered

shuffle()
↓
Randomly rearranged
```

---

### `min()` vs `max()`

```text
min() → Smallest

max() → Largest
```

---

### `frequency()` vs `size()`

```text
size()
↓
Total number of elements

frequency()
↓
Number of occurrences of one particular element
```

Example:

```text
[10, 20, 10, 30, 10]
```

```text
size() = 5

frequency(10) = 3
```

---

### `swap()` vs `reverse()`

```text
swap()
↓
Exchange TWO specified positions

reverse()
↓
Reverse the ENTIRE list
```

---

### `fill()` vs `add()`

```text
add()
↓
Adds an element

fill()
↓
Replaces all existing elements
```

---

# ⭐ ONEPAGE Memory Map

```text
                 Collections
                Utility Class
                     |
   ------------------------------------------------
   |       |       |       |       |       |      |
   ↓       ↓       ↓       ↓       ↓       ↓      ↓
 sort   reverse shuffle  max     min frequency search
                                     
              --------------------------
              |            |           |
              ↓            ↓           ↓
            swap         fill      binarySearch
```

### Final memory formula:

```text
sort()          → Arrange
reverse()       → Reverse
shuffle()       → Randomize
max()           → Largest
min()           → Smallest
frequency()     → Count occurrences
binarySearch()  → Search sorted list
swap()          → Exchange two positions
fill()          → Replace all existing elements
```

**Important:** `Collections` is the **utility class** (`java.util.Collections`), while `Collection` is the **interface**. Do not confuse:

```text
Collection  → Interface
Collections → Utility class
```
