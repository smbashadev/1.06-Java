# 12. Collections Utility Class in Java — DEEPDIVE

> **Rule followed:** No Generics anywhere.
> This is the **DEEPDIVE** version, so every sub-concept is explained individually, including purpose, syntax, working, examples, important points, and common mistakes.

---

# 1. First: What is `Collections`?

Before learning the individual methods, we must understand what `Collections` actually is.

Java provides:

```java
java.util.Collections
```

`Collections` is a **utility class** containing static methods that perform common operations on collections.

Example:

```java
Collections.sort(list);
Collections.reverse(list);
Collections.shuffle(list);
```

Because these methods are `static`, we call them using the class name:

```text
Collections.method()
```

not by creating a `Collections` object.

---

# 2. `Collection` vs `Collections`

This is one of the most important basic confusions.

### `Collection`

```text
Collection
    ↓
Interface
```

It represents a group of objects and provides common collection operations.

### `Collections`

```text
Collections
    ↓
Utility class
    ↓
Static utility methods
```

For example:

```java
Collections.sort(list);
```

So remember:

```text
Collection  → Interface

Collections → Utility class
```

The names are similar, but their purposes are completely different.

---

# 3. `sort()`

## 3.1 Definition

`Collections.sort()` is used to arrange the elements of a `List` according to their **natural ordering**, or according to a supplied `Comparator`.

Basic form:

```java
Collections.sort(list);
```

With Comparator:

```java
Collections.sort(list, comparator);
```

Because we are not using Generics, we will use raw collections in the examples.

---

## 3.2 Basic Example

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

        System.out.println("Before: " + list);

        Collections.sort(list);

        System.out.println("After: " + list);
    }
}
```

Output:

```text
Before: [30, 10, 20]
After: [10, 20, 30]
```

---

## 3.3 How does `sort()` work conceptually?

Suppose:

```text
[30, 10, 20]
```

`sort()` needs to determine the correct ordering.

Conceptually:

```text
30
10
20
 ↓
Compare elements
 ↓
Determine ordering
 ↓
Rearrange
 ↓
10
20
30
```

The exact internal sorting implementation is an implementation detail, but conceptually the method uses ordering information to arrange the list.

---

## 3.4 Sorting Strings

```java
ArrayList list = new ArrayList();

list.add("Ravi");
list.add("Arun");
list.add("Kumar");

Collections.sort(list);

System.out.println(list);
```

Output:

```text
[Arun, Kumar, Ravi]
```

Strings have a natural ordering defined by `String`.

---

## 3.5 Sorting custom objects

Suppose:

```java
class Student
{
    int rollNo;
    String name;

    Student(int rollNo, String name)
    {
        this.rollNo = rollNo;
        this.name = name;
    }
}
```

A custom Student class doesn't automatically have a natural ordering merely because it has fields.

If we want custom ordering, we can supply a Comparator:

```java
class RollNoComparator implements Comparator
{
    public int compare(Object obj1, Object obj2)
    {
        Student s1 = (Student)obj1;
        Student s2 = (Student)obj2;

        return s1.rollNo - s2.rollNo;
    }
}
```

Then:

```java
Collections.sort(list, new RollNoComparator());
```

---

## 3.6 Important point

`sort()` works on a `List`.

It is not a general-purpose operation that sorts every collection type.

For example:

```text
ArrayList → List → suitable
LinkedList → List → suitable
```

But a `Set` does not have indexed list ordering in the same way.

---

# 4. `reverse()`

## 4.1 Definition

`Collections.reverse()` reverses the **current order** of the elements in a `List`.

Syntax:

```java
Collections.reverse(list);
```

---

## 4.2 Example

```java
ArrayList list = new ArrayList();

list.add(10);
list.add(20);
list.add(30);
list.add(40);

System.out.println("Before: " + list);

Collections.reverse(list);

System.out.println("After: " + list);
```

Output:

```text
Before: [10, 20, 30, 40]
After: [40, 30, 20, 10]
```

---

## 4.3 Very Important: `reverse()` does NOT mean "sort descending"

Suppose:

```text
[30, 10, 20]
```

If we use:

```java
Collections.reverse(list);
```

we get:

```text
[20, 10, 30]
```

Why?

Because it reverses the **existing order**.

It doesn't first sort the elements.

---

## 4.4 Compare with sorting

Original:

```text
[30, 10, 20]
```

### `sort()`

```text
[10, 20, 30]
```

### `reverse()`

```text
[20, 10, 30]
```

### `sort()` followed by `reverse()`

```text
[30, 20, 10]
```

Therefore:

```text
sort()
    ↓
Ascending/natural ordering

reverse()
    ↓
Reverse whatever order currently exists
```

---

# 5. `shuffle()`

## 5.1 Definition

`Collections.shuffle()` randomly rearranges the elements of a `List`.

Syntax:

```java
Collections.shuffle(list);
```

---

## 5.2 Example

```java
ArrayList list = new ArrayList();

list.add(10);
list.add(20);
list.add(30);
list.add(40);
list.add(50);

Collections.shuffle(list);

System.out.println(list);
```

Possible output:

```text
[30, 10, 50, 20, 40]
```

Another execution can produce another order.

---

## 5.3 Why is the result different?

Because `shuffle()` uses randomness to rearrange the elements.

Therefore, don't expect:

```text
[30, 10, 50, 20, 40]
```

every time.

---

## 5.4 `shuffle()` vs `reverse()`

### Reverse

```text
[10,20,30,40]
      ↓
[40,30,20,10]
```

Completely predictable.

### Shuffle

```text
[10,20,30,40]
      ↓
Possible:
[30,10,40,20]
```

Randomized.

So:

```text
reverse() → deterministic reversal

shuffle() → randomized rearrangement
```

---

# 6. `max()`

## 6.1 Definition

`Collections.max()` returns the largest element according to natural ordering, or according to a supplied Comparator.

Syntax:

```java
Collections.max(collection);
```

With Comparator:

```java
Collections.max(collection, comparator);
```

---

## 6.2 Example

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

---

## 6.3 Does `max()` sort the collection?

**No.**

Suppose:

```text
[40, 10, 70, 20]
```

After:

```java
Collections.max(list);
```

the list is still:

```text
[40, 10, 70, 20]
```

The method simply finds and returns the maximum element.

Think:

```text
max()
 ↓
Search for largest
 ↓
Return largest
```

It doesn't rearrange the list.

---

## 6.4 `max()` with Comparator

Suppose Student objects have:

```text
rollNo
marks
```

and we want the Student with the highest marks.

We can supply a Comparator that compares marks.

Conceptually:

```text
Students
   ↓
max()
   ↓
Comparator
   ↓
Compare marks
   ↓
Student with greatest marks
```

---

# 7. `min()`

## 7.1 Definition

`Collections.min()` returns the smallest element according to natural ordering or a supplied Comparator.

Syntax:

```java
Collections.min(collection);
```

---

## 7.2 Example

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

---

## 7.3 Does `min()` modify the list?

**No.**

Original:

```text
[40, 10, 70, 20]
```

After:

```java
Collections.min(list);
```

still:

```text
[40, 10, 70, 20]
```

Only the smallest element is returned.

---

# 8. `min()` vs `max()`

Suppose:

```text
[40, 10, 70, 20]
```

Then:

```text
min() → 10

max() → 70
```

Neither operation is intended to sort the list.

---

# 9. `frequency()`

## 9.1 Definition

`Collections.frequency()` counts how many times a particular element occurs in a collection.

Syntax:

```java
Collections.frequency(collection, object);
```

---

## 9.2 Example

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

---

## 9.3 How does it work conceptually?

List:

```text
[10, 20, 10, 30, 10]
```

Search for:

```text
10
```

Occurrences:

```text
10 → found
20 → no
10 → found
30 → no
10 → found
```

Count:

```text
3
```

---

## 9.4 Does `frequency()` modify the collection?

No.

Before:

```text
[10,20,10,30,10]
```

After calling:

```java
Collections.frequency(list, 10);
```

the list remains:

```text
[10,20,10,30,10]
```

---

## 9.5 `frequency()` vs `size()`

Suppose:

```text
[10,20,10,30,10]
```

Then:

```text
size() = 5
```

because there are five total elements.

But:

```text
frequency(10) = 3
```

because `10` occurs three times.

---

# 10. `binarySearch()`

This is one of the most important methods in this chapter.

## 10.1 Definition

`Collections.binarySearch()` searches for a specified element in a `List` using binary search.

Syntax:

```java
Collections.binarySearch(list, key);
```

---

# 11. Why does binary search require sorting?

Binary search works by repeatedly eliminating approximately half of the remaining search space.

For example:

```text
[10, 20, 30, 40, 50, 60, 70]
```

Search:

```text
60
```

Because the list is ordered, we can determine whether to search the left or right portion.

Conceptually:

```text
Entire list
     ↓
Check middle
     ↓
Is target smaller or larger?
     ↓
Discard one half
     ↓
Repeat
```

If the data isn't ordered according to the same ordering used by the search, this reasoning is not valid.

---

# 12. `binarySearch()` Example

```java
ArrayList list = new ArrayList();

list.add(10);
list.add(20);
list.add(30);
list.add(40);
list.add(50);

int index = Collections.binarySearch(list, 30);

System.out.println(index);
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

---

# 13. What if the element isn't found?

The result is negative.

The exact negative value is related to the **insertion point**, so it carries more information than simply "not found."

For example, with:

```text
[10, 20, 30, 40]
```

searching for `25` doesn't find it.

The insertion point would be between `20` and `30`.

The method returns a negative value encoding that position.

For a simple existence check, the important rule is:

```java
if (Collections.binarySearch(list, key) >= 0)
{
    System.out.println("Found");
}
else
{
    System.out.println("Not Found");
}
```

---

# 14. Critical `binarySearch()` Rule

This is a common exam/interview question:

> **Can I use `binarySearch()` on an unsorted list?**

You should not use the natural-order overload on an unsorted list.

Correct:

```java
Collections.sort(list);

int result = Collections.binarySearch(list, 30);
```

If using a Comparator, the list must be sorted according to that same Comparator.

Conceptually:

```text
Sort rule
    ↓
must match
    ↓
Search rule
```

---

# 15. `swap()`

## 15.1 Definition

`Collections.swap()` exchanges the elements at two positions in a `List`.

Syntax:

```java
Collections.swap(list, index1, index2);
```

---

## 15.2 Example

```java
ArrayList list = new ArrayList();

list.add(10);
list.add(20);
list.add(30);
list.add(40);

Collections.swap(list, 0, 3);

System.out.println(list);
```

Before:

```text
Index:   0   1   2   3
Value:  10  20  30  40
```

After:

```text
Index:   0   1   2   3
Value:  40  20  30  10
```

---

# 16. Does `swap()` change the size?

No.

Before:

```text
[10,20,30,40]
size = 4
```

After:

```text
[40,20,30,10]
size = 4
```

Only the positions change.

---

# 17. `swap()` vs `reverse()`

### `swap()`

Changes two specified positions:

```text
[10,20,30,40]

swap(0,3)

[40,20,30,10]
```

### `reverse()`

Reverses the entire list:

```text
[10,20,30,40]

reverse()

[40,30,20,10]
```

Therefore:

```text
swap()    → Two positions
reverse() → Entire list
```

---

# 18. `fill()`

## 18.1 Definition

`Collections.fill()` replaces every existing element in a `List` with the specified value.

Syntax:

```java
Collections.fill(list, value);
```

---

## 18.2 Example

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

---

# 19. Does `fill()` add elements?

**No.**

This is extremely important.

Before:

```text
[10,20,30]
```

Number of elements:

```text
3
```

After:

```java
Collections.fill(list, 100);
```

we get:

```text
[100,100,100]
```

Number of elements:

```text
3
```

So:

```text
fill()
 ↓
Replace existing elements
```

not:

```text
fill()
 ↓
Add new elements
```

---

# 20. `fill()` vs `add()`

### `add()`

```java
list.add(100);
```

Adds an element.

Example:

```text
[10,20,30]
       ↓
[10,20,30,100]
```

### `fill()`

```java
Collections.fill(list, 100);
```

Replaces existing elements:

```text
[10,20,30]
       ↓
[100,100,100]
```

---

# 21. Important Empty-List Point

Consider:

```java
ArrayList list = new ArrayList();

Collections.fill(list, 100);
```

Result:

```text
[]
```

Why?

Because there are **no existing elements to replace**.

`fill()` doesn't create elements.

---

# 22. All Nine Methods — Deep Conceptual Classification

These methods can be grouped according to what they do.

### Ordering / arrangement

```text
sort()
reverse()
shuffle()
swap()
```

### Finding values

```text
min()
max()
```

### Counting

```text
frequency()
```

### Searching

```text
binarySearch()
```

### Replacing

```text
fill()
```

This gives us a useful mental map:

```text
                Collections
                     |
       ---------------------------------
       |        |       |       |      |
       ↓        ↓       ↓       ↓      ↓
   Arrange    Find    Count   Search Replace
       |        |       |       |      |
       ↓        ↓       ↓       ↓      ↓
 sort       min/max frequency binary  fill
 reverse                        Search
 shuffle
 swap
```

---

# 23. Detailed Comparison of All Methods

| Method           | Main job                  | Changes list? |                  Needs ordering? |
| ---------------- | ------------------------- | ------------: | -------------------------------: |
| `sort()`         | Arrange elements          |           Yes | Uses natural order or Comparator |
| `reverse()`      | Reverse current order     |           Yes |                               No |
| `shuffle()`      | Randomize order           |           Yes |                               No |
| `max()`          | Find largest              |            No |      Natural order or Comparator |
| `min()`          | Find smallest             |            No |      Natural order or Comparator |
| `frequency()`    | Count occurrences         |            No |                               No |
| `binarySearch()` | Search for element        |            No |    **Yes, appropriately sorted** |
| `swap()`         | Exchange two positions    |           Yes |                               No |
| `fill()`         | Replace existing elements |           Yes |                               No |

---

# 24. `sort()` vs `binarySearch()`

These two are often used together.

```java
Collections.sort(list);

int index = Collections.binarySearch(list, key);
```

Flow:

```text
Unsorted list
     ↓
sort()
     ↓
Sorted list
     ↓
binarySearch()
     ↓
Search key
```

Important:

> `binarySearch()` is a search operation; it doesn't sort the list first for you.

---

# 25. `max()` vs `sort()`

Suppose:

```text
[40, 10, 70, 20]
```

If we need only the largest value:

```java
Collections.max(list);
```

is conceptually the appropriate operation.

We don't need to sort the entire list just to find the maximum.

Similarly:

```java
Collections.min(list);
```

finds the smallest.

So:

```text
Need largest → max()

Need smallest → min()

Need complete ordering → sort()
```

---

# 26. `shuffle()` vs `sort()`

```text
sort()
 ↓
Predictable ordered result
```

while:

```text
shuffle()
 ↓
Randomized order
```

Example:

```text
Original:
[40,10,30,20]

sort():
[10,20,30,40]

shuffle():
Possible [30,40,10,20]
```

---

# 27. `reverse()` vs Descending Sort

Suppose:

```text
[30,10,20]
```

### Reverse

```text
[20,10,30]
```

### Sort ascending, then reverse

```text
sort():
[10,20,30]

reverse():
[30,20,10]
```

Therefore, if your goal is descending order, **simply calling `reverse()` on an unsorted list is not equivalent to descending sort**.

---

# 28. Important Empty Collection Behavior

Several methods have special behavior with empty collections.

For example:

```java
Collections.max(emptyList);
```

and:

```java
Collections.min(emptyList);
```

cannot produce a maximum or minimum because there is no element.

They throw:

```text
NoSuchElementException
```

Similarly, a binary search on an empty list simply doesn't find the requested element and returns the appropriate negative result.

---

# 29. A Complete Demonstration Program

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
        list.add(10);

        System.out.println("Original: " + list);

        Collections.sort(list);
        System.out.println("sort(): " + list);

        Collections.reverse(list);
        System.out.println("reverse(): " + list);

        Collections.shuffle(list);
        System.out.println("shuffle(): " + list);

        Object maximum = Collections.max(list);
        System.out.println("max(): " + maximum);

        Object minimum = Collections.min(list);
        System.out.println("min(): " + minimum);

        int count = Collections.frequency(list, 10);
        System.out.println("frequency(10): " + count);

        Collections.sort(list);

        int index = Collections.binarySearch(list, 20);
        System.out.println("binarySearch(20): " + index);

        Collections.swap(list, 0, 1);
        System.out.println("swap(0,1): " + list);

        Collections.fill(list, 100);
        System.out.println("fill(100): " + list);
    }
}
```

The exact `shuffle()` output can vary because it is randomized.

---

# 30. Important Interview/Exam Questions

### Q1. What is `Collections`?

> `Collections` is a utility class in `java.util` that provides static methods for performing common operations on collections.

### Q2. Is `Collections` an interface?

**No.**

```text
Collection  → interface
Collections → utility class
```

### Q3. Does `max()` sort the collection?

**No.** It finds and returns the maximum element.

### Q4. Does `min()` sort the collection?

**No.** It finds and returns the minimum element.

### Q5. Does `fill()` add elements?

**No.** It replaces existing elements.

### Q6. Does `reverse()` sort descending?

**Not by itself.** It reverses the current order.

### Q7. Can `shuffle()` produce a different order on different executions?

**Yes.**

### Q8. Can binary search be performed on an unsorted list?

You should not use the natural-order overload on an unsorted list. The list must be appropriately sorted for the ordering used by the search.

### Q9. Does `swap()` change the size?

**No.**

### Q10. Which method counts occurrences?

```text
frequency()
```

---

# 31. Final Deep-Dive Mental Model

```text
                 java.util.Collections
                         |
     ------------------------------------------------
     |          |          |          |             |
     ↓          ↓          ↓          ↓             ↓
   sort()    reverse()  shuffle()   swap()        fill()
     |          |          |          |             |
     ↓          ↓          ↓          ↓             ↓
  Arrange    Reverse     Random     Exchange     Replace
```

And:

```text
                 Collections
                      |
             -------------------
             |                 |
             ↓                 ↓
          min()              max()
             |                 |
             ↓                 ↓
        Smallest           Largest
```

And:

```text
                 Collections
                      |
             -------------------
             |                 |
             ↓                 ↓
       frequency()       binarySearch()
             |                 |
             ↓                 ↓
       Count occurrences    Search
                               |
                               ↓
                    Requires appropriate
                       sorted ordering
```

---

# ⭐ DEEPDIVE Final Revision

```text
sort()
→ Arranges a List according to natural ordering or Comparator.

reverse()
→ Reverses the current List order.

shuffle()
→ Randomly rearranges a List.

max()
→ Returns the largest element.

min()
→ Returns the smallest element.

frequency()
→ Returns how many times an element occurs.

binarySearch()
→ Searches for an element in an appropriately sorted List.

swap()
→ Exchanges two elements at specified indexes.

fill()
→ Replaces every existing List element with a specified value.
```

### The most important distinction:

```text
Collection
    ↓
Interface

Collections
    ↓
Utility class
    ↓
sort()
reverse()
shuffle()
max()
min()
frequency()
binarySearch()
swap()
fill()
```

**No Generics are used anywhere in this DEEPDIVE explanation.**
