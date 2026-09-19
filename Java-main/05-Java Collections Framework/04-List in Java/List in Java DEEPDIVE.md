# 4. List in Java — DEEPDIVE

> **Training rule:** No Generics anywhere in this topic. We will use **normal/raw collection syntax** throughout. Generics are a separate topic later.
>
> The goal here is not just to memorize `ArrayList`, `LinkedList`, `Vector`, and `Stack`, but to understand **why they exist, how they work, what makes them different, and when to use each one**.

---

# PART 1 — LIST INTERFACE

## 1. What is a List?

`List` is an interface in the `java.util` package.

It represents an **ordered collection**.

The most important characteristics are:

```text
List
 │
 ├── Maintains insertion order
 ├── Allows duplicate elements
 ├── Allows null values
 ├── Supports index-based access
 └── Allows insertion/removal at positions
```

For example:

```text
[10, 20, 30, 20]
```

This is a valid List because:

* `10`, `20`, `30` are retained in order.
* `20` occurs twice.
* Each element has a position.

Indexes:

```text
Element:  10   20   30   20
Index:     0    1    2    3
```

---

# 2. List Hierarchy

The basic hierarchy is:

```text
                         Collection
                             ↑
                            List
                             │
              ┌──────────────┼──────────────┐
              ↓              ↓              ↓
          ArrayList      LinkedList        Vector
                                              ↑
                                            Stack
```

More accurately:

```text
Collection
    ↑
   List
    ↑
    ├── ArrayList
    ├── LinkedList
    └── Vector
          ↑
        Stack
```

And `LinkedList` also implements `Deque`.

```text
LinkedList
    ├── List
    └── Deque
```

This becomes important when we study Queue and Deque.

---

# 3. Why Do We Need List?

Before Collections, arrays were commonly used:

```java
int a[] = new int[5];
```

But arrays have a fixed size.

Suppose we don't know how many elements will be required.

```text
5?
50?
500?
5000?
```

A List provides a dynamically resizable collection.

For example:

```java
List l = new ArrayList();

l.add(10);
l.add(20);
l.add(30);
l.add(40);
l.add(50);
```

The collection can grow as elements are added.

---

# 4. Array vs List

| Array                         | List                      |
| ----------------------------- | ------------------------- |
| Usually fixed length          | Dynamically resizable     |
| `length`                      | `size()`                  |
| Primitive arrays possible     | Collections store objects |
| Basic language feature        | Collections Framework     |
| Limited ready-made operations | Many collection methods   |
| Index-based                   | List is index-based       |

Example array:

```java
int a[] = new int[3];
```

Once created, its length is fixed.

List:

```java
List l = new ArrayList();
```

can grow as elements are added.

---

# 5. Creating a List

Because `List` is an interface:

```java
List l = new List();
```

❌ Invalid.

Instead:

```java
List l = new ArrayList();
```

✅ Valid.

Or:

```java
List l = new LinkedList();
```

✅ Valid.

Or:

```java
List l = new Vector();
```

✅ Valid.

This is **interface reference + implementation object**.

---

# 6. Basic List Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        List l = new ArrayList();

        l.add(10);
        l.add(20);
        l.add(30);

        System.out.println(l);
    }
}
```

Output:

```text
[10, 20, 30]
```

---

# 7. List Allows Duplicates

Unlike a Set, List allows duplicate values.

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        List l = new ArrayList();

        l.add(10);
        l.add(20);
        l.add(10);
        l.add(30);
        l.add(20);

        System.out.println(l);
    }
}
```

Output:

```text
[10, 20, 10, 30, 20]
```

Nothing prevents the same value from appearing multiple times.

---

# 8. List Maintains Insertion Order

Suppose:

```java
l.add(30);
l.add(10);
l.add(20);
```

The output remains:

```text
[30, 10, 20]
```

It does not automatically sort the elements.

### Important distinction

```text
Insertion order ≠ sorted order
```

For example:

```text
Insertion order:
30 → 10 → 20

Sorted order:
10 → 20 → 30
```

A List maintains the first order unless you explicitly rearrange it.

---

# 9. Index-Based Access

One of the most important features of List is index access.

Suppose:

```text
[10, 20, 30, 40]
```

Then:

```text
index 0 → 10
index 1 → 20
index 2 → 30
index 3 → 40
```

Therefore:

```java
l.get(2);
```

returns:

```text
30
```

---

# 10. `get()`

`get(index)` returns the element at a particular index.

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        List l = new ArrayList();

        l.add(10);
        l.add(20);
        l.add(30);

        System.out.println(l.get(0));
        System.out.println(l.get(1));
        System.out.println(l.get(2));
    }
}
```

Output:

```text
10
20
30
```

---

# 11. `add(index, element)`

List also allows us to insert an element at a particular index.

Suppose:

```text
[10, 20, 30]
```

Execute:

```java
l.add(1, 50);
```

Result:

```text
[10, 50, 20, 30]
```

The existing elements shift to make room.

```text
Before:

10  20  30
    ↑
   index 1

After:

10  50  20  30
    ↑
  inserted
```

---

# 12. `set(index, element)`

`set()` **replaces** an existing element.

Suppose:

```text
[10, 20, 30]
```

Execute:

```java
l.set(1, 50);
```

Result:

```text
[10, 50, 30]
```

### Important

```text
add(index, value)
    ↓
INSERT

set(index, value)
    ↓
REPLACE
```

This distinction is frequently tested.

---

# 13. `add()` vs `set()`

Suppose:

```text
[10,20,30]
```

### `add(1,50)`

```text
[10,50,20,30]
```

Size increases.

### `set(1,50)`

```text
[10,50,30]
```

Size remains the same.

So:

```text
add() → adds a new element
set() → replaces an existing element
```

---

# 14. `remove(index)`

List supports removal by index.

```java
l.remove(1);
```

Suppose:

```text
[10,20,30]
```

Result:

```text
[10,30]
```

because index `1` contained `20`.

---

# 15. The Famous Integer `remove()` Problem

This is one of the most important List concepts.

Suppose:

```java
List l = new ArrayList();

l.add(10);
l.add(20);
l.add(30);
```

Now:

```java
l.remove(1);
```

What happens?

It removes the element at **index 1**:

```text
[10,30]
```

because `1` is an `int`.

---

## What if we want to remove the value `20`?

We can explicitly provide an `Integer` object:

```java
l.remove(Integer.valueOf(20));
```

Now the value `20` is removed.

This distinction comes from overloaded methods:

```text
remove(int index)
remove(Object object)
```

---

# 16. Why This Happens

Java chooses an overloaded method based on the argument type.

```java
l.remove(1);
```

`1` is an `int`.

Therefore:

```text
remove(int)
```

is selected.

But:

```java
l.remove(Integer.valueOf(20));
```

is an `Integer` object.

Therefore:

```text
remove(Object)
```

is selected.

This is one of the reasons List becomes especially interesting when we study wrapper classes and method overloading.

---

# 17. List Allows `null`

For example:

```java
List l = new ArrayList();

l.add(10);
l.add(null);
l.add(20);
l.add(null);

System.out.println(l);
```

Output:

```text
[10, null, 20, null]
```

List implementations such as `ArrayList` allow `null`.

---

# PART 2 — ARRAYLIST

# 18. What is ArrayList?

`ArrayList` is a class in `java.util`.

It implements `List`.

Conceptually:

```text
Collection
    ↑
   List
    ↑
ArrayList
```

ArrayList is backed by a dynamically resizable array.

---

# 19. Why Is It Called ArrayList?

Because its internal data structure is based on an array.

Conceptually:

```text
Array
 ↓
[10][20][30][40]
```

When more room is required, ArrayList internally manages a larger storage area.

Therefore:

```text
ArrayList
=
Array + dynamic resizing + List operations
```

This is a conceptual model; the implementation details are handled internally by Java.

---

# 20. Basic ArrayList Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);

        System.out.println(al);
    }
}
```

Output:

```text
[10, 20, 30]
```

---

# 21. ArrayList and Dynamic Size

Consider:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

No fixed size was specified.

We can continue:

```java
al.add(40);
al.add(50);
al.add(60);
```

The ArrayList dynamically manages its storage.

---

# 22. ArrayList Index Access

Because ArrayList is array-based, random access by index is generally efficient.

```java
System.out.println(al.get(3));
```

For:

```text
[10,20,30,40]
```

the result is:

```text
40
```

This makes ArrayList a strong choice when the application frequently asks:

```text
"Give me the element at index X."
```

---

# 23. ArrayList Insertion

Consider:

```text
[10,20,30,40]
```

Now:

```java
al.add(1, 50);
```

Result:

```text
[10,50,20,30,40]
```

Elements after the insertion position have to move.

Therefore, insertion in the middle can be relatively expensive compared with simple index access.

---

# 24. ArrayList Removal

Similarly:

```java
al.remove(1);
```

may require elements after the removed position to shift.

Example:

```text
Before:
[10,20,30,40]

remove index 1

After:
[10,30,40]
```

This shifting is an important reason why ArrayList is not automatically the best choice for frequent middle insertions/removals.

---

# 25. ArrayList Performance Idea

Typical conceptual comparison:

```text
Operation                 ArrayList

get(index)                 Fast
set(index,value)           Fast
add(end)                   Usually fast
add(middle)                More expensive
remove(middle)             More expensive
```

Do not interpret this as every operation having one absolute fixed time in every situation; actual performance depends on the operation and implementation details.

---

# 26. ArrayList Is Not Synchronized

ArrayList itself does not provide built-in synchronization for its normal operations.

Therefore:

```text
ArrayList
    ↓
Not synchronized
```

If multiple threads modify a shared ArrayList, appropriate concurrency control may be necessary.

We'll study synchronization and concurrent collections later.

---

# 27. ArrayList vs Array

| Array               | ArrayList             |
| ------------------- | --------------------- |
| Fixed length        | Dynamically resizable |
| `length`            | `size()`              |
| Supports primitives | Stores objects        |
| `a[index]`          | `get(index)`          |
| `a[index] = value`  | `set(index,value)`    |
| Language feature    | Collections Framework |

---

# PART 3 — LINKEDLIST

# 28. What is LinkedList?

`LinkedList` is a class that implements:

```text
List
Deque
```

Therefore:

```text
LinkedList
   ├── List behavior
   └── Deque behavior
```

It is based on linked nodes.

Conceptually:

```text
[10] ↔ [20] ↔ [30] ↔ [40]
```

Each node contains a value and links to other nodes.

---

# 29. Why Linked Structure?

Consider:

```text
[10] ↔ [20] ↔ [30] ↔ [40]
```

If we insert an element near an appropriate linked position, the structure can rearrange links rather than shifting an entire array of elements.

For example:

```text
Before:

10 ↔ 20 ↔ 30

Insert 50 between 10 and 20:

10 ↔ 50 ↔ 20 ↔ 30
```

The conceptual mechanism is link adjustment.

However, finding a position by index can itself require traversal.

---

# 30. Basic LinkedList Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedList ll = new LinkedList();

        ll.add(10);
        ll.add(20);
        ll.add(30);

        System.out.println(ll);
    }
}
```

Output:

```text
[10, 20, 30]
```

---

# 31. LinkedList Special Methods

Because LinkedList also implements `Deque`, it provides methods such as:

```text
addFirst()
addLast()

getFirst()
getLast()

removeFirst()
removeLast()

peek()
poll()
offer()
```

Example:

```java
LinkedList ll = new LinkedList();

ll.add(20);
ll.addFirst(10);
ll.addLast(30);

System.out.println(ll);
```

Output:

```text
[10, 20, 30]
```

---

# 32. `addFirst()` and `addLast()`

```java
ll.addFirst(10);
```

adds at the beginning.

```java
ll.addLast(30);
```

adds at the end.

Example:

```text
Initially:
[20]

addFirst(10):
[10,20]

addLast(30):
[10,20,30]
```

This is one reason LinkedList is useful for operations at the ends.

---

# 33. LinkedList Index Access

LinkedList supports:

```java
ll.get(2);
```

because it implements List.

But unlike ArrayList, accessing an arbitrary index generally requires traversal through linked nodes.

Therefore:

```text
ArrayList
    ↓
index access generally faster

LinkedList
    ↓
index access generally slower
```

---

# 34. LinkedList Is Not Synchronized

Like ArrayList:

```text
LinkedList
    ↓
Not synchronized
```

If shared across multiple threads, appropriate synchronization/concurrency mechanisms may be needed.

---

# 35. ArrayList vs LinkedList — Internal Picture

### ArrayList

```text
Index:

0    1    2    3
↓    ↓    ↓    ↓
[10][20][30][40]
```

### LinkedList

```text
[10] ↔ [20] ↔ [30] ↔ [40]
```

Therefore:

```text
ArrayList
→ array-based

LinkedList
→ linked-node based
```

---

# 36. When Should We Choose LinkedList?

It can be useful when your workload involves frequent insertion/removal at the beginning or end and you need List/Deque behavior.

But don't use the simplistic rule:

> "Insertion = always LinkedList."

That's not universally correct.

Actual performance depends on **where** the insertion occurs, how the position is found, and the overall workload.

For many ordinary List workloads, ArrayList is the better default.

---

# PART 4 — VECTOR

# 37. What is Vector?

`Vector` is a legacy collection class that implements `List`.

Hierarchy:

```text
Collection
    ↑
   List
    ↑
  Vector
```

Vector is similar to ArrayList in its general List behavior, but its traditional methods are synchronized.

---

# 38. Basic Vector Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Vector v = new Vector();

        v.add(10);
        v.add(20);
        v.add(30);

        System.out.println(v);
    }
}
```

Output:

```text
[10, 20, 30]
```

---

# 39. Why Is Vector Called Legacy?

Vector has existed since the early versions of Java.

The Collections Framework was introduced later, and newer classes such as ArrayList became the usual choice for general-purpose List usage.

Therefore, you'll often see:

```text
Old / legacy
    ↓
Vector
Stack

Modern general List
    ↓
ArrayList
```

This does **not** mean Vector is broken or unusable.

It means that for new general-purpose code, ArrayList is commonly preferred unless Vector's particular synchronization/legacy behavior is required.

---

# 40. Vector and Synchronization

Traditional Vector methods are synchronized.

Conceptually:

```text
ArrayList
    ↓
Not synchronized

Vector
    ↓
Synchronized
```

This matters when multiple threads access shared mutable data.

However:

> Synchronization does not automatically make every multi-operation sequence logically thread-safe.

For example, a sequence of several operations may still require external coordination.

We'll study synchronization in the Advanced Collections section.

---

# 41. Vector Capacity

Vector provides methods related to capacity, such as:

```java
capacity()
```

Example:

```java
Vector v = new Vector();

System.out.println(v.capacity());
```

Vector has an internal capacity separate from its logical element count.

Therefore:

```text
size ≠ capacity
```

This distinction is also important with ArrayList.

---

# 42. Vector — Size vs Capacity

Suppose:

```text
Vector contains:
[10,20,30]
```

Then:

```java
v.size();
```

returns:

```text
3
```

But:

```java
v.capacity();
```

can be larger than `3`.

Therefore:

```text
size
↓
Number of actual elements

capacity
↓
Available internal storage capacity
```

---

# PART 5 — STACK

# 43. What is Stack?

`Stack` is a legacy class that extends `Vector`.

Hierarchy:

```text
Collection
    ↑
   List
    ↑
  Vector
    ↑
  Stack
```

Its primary purpose is to represent a **LIFO** data structure.

```text
LIFO

Last In
First Out
```

---

# 44. Real-Life Example

Imagine plates:

```text
       Plate 30  ← top
       Plate 20
       Plate 10
```

You normally remove the top plate first.

So:

```text
Last plate added
       ↓
First plate removed
```

That is LIFO.

---

# 45. Stack `push()`

`push()` adds an element to the top.

```java
Stack s = new Stack();

s.push(10);
s.push(20);
s.push(30);

System.out.println(s);
```

Output:

```text
[10, 20, 30]
```

`30` is the top element.

---

# 46. Stack `pop()`

`pop()`:

```text
1. returns the top element
2. removes the top element
```

Example:

```java
System.out.println(s.pop());
```

Output:

```text
30
```

Now:

```text
[10,20]
```

---

# 47. Stack `peek()`

`peek()`:

```text
1. returns the top element
2. does NOT remove it
```

Example:

```java
System.out.println(s.peek());
```

If:

```text
[10,20,30]
```

output:

```text
30
```

and the stack remains:

```text
[10,20,30]
```

---

# 48. `pop()` vs `peek()`

This should be memorized:

```text
pop()
 ↓
SEE + REMOVE

peek()
 ↓
SEE ONLY
```

Example:

```text
Stack:
[10,20,30]
       ↑
      top
```

`peek()`:

```text
[10,20,30]
```

`pop()`:

```text
[10,20]
```

---

# 49. Stack `empty()`

Checks whether the stack contains no elements.

```java
s.empty();
```

Returns:

```text
true
```

or:

```text
false
```

---

# 50. Stack `search()`

Stack provides:

```java
search(object)
```

which searches for an element and returns its position measured from the top.

Example:

```text
Stack:
[10,20,30]
       ↑ top
```

Then:

```java
s.search(30);
```

returns:

```text
1
```

because the top element is position `1`.

This is **not the same as a List index**.

That's an important distinction.

---

# 51. Complete Stack Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Stack s = new Stack();

        s.push(10);
        s.push(20);
        s.push(30);

        System.out.println("Stack = " + s);

        System.out.println("Top = " + s.peek());

        System.out.println("Removed = " + s.pop());

        System.out.println("Stack after pop = " + s);

        System.out.println("Is empty = " + s.empty());
    }
}
```

Output:

```text
Stack = [10, 20, 30]
Top = 30
Removed = 30
Stack after pop = [10, 20]
Is empty = false
```

---

# PART 6 — COMPARING ALL FOUR

## 52. ArrayList vs LinkedList vs Vector vs Stack

| Feature               | ArrayList      | LinkedList              | Vector                   | Stack          |
| --------------------- | -------------- | ----------------------- | ------------------------ | -------------- |
| Implements List       | Yes            | Yes                     | Yes                      | Yes            |
| Linked with Deque     | No             | Yes                     | No                       | No             |
| Maintains order       | Yes            | Yes                     | Yes                      | Yes            |
| Duplicates            | Yes            | Yes                     | Yes                      | Yes            |
| `null`                | Allowed        | Allowed                 | Allowed                  | Allowed        |
| Index access          | Generally fast | Generally slower        | Generally fast           | Generally fast |
| Synchronized          | No             | No                      | Yes                      | Yes            |
| Structure             | Dynamic array  | Linked nodes            | Dynamic array            | Vector-based   |
| Main purpose          | General List   | List + Deque operations | Legacy synchronized List | LIFO           |
| Modern general choice | Yes            | Sometimes               | Usually not              | Usually not    |

---

# 53. Which One Should I Use?

### Requirement 1 — General-purpose List

```text
ArrayList
```

Why?

```text
Fast index access
Simple
Commonly used
Good general-purpose default
```

---

### Requirement 2 — List + frequent end operations

```text
LinkedList
```

Especially when you need its Deque operations.

---

### Requirement 3 — Legacy synchronized List

```text
Vector
```

Usually encountered in older code.

---

### Requirement 4 — Traditional LIFO Stack

```text
Stack
```

But for new code, Java's `Deque` implementations such as `ArrayDeque` are generally preferred for stack behavior.

We will study `Deque` and `ArrayDeque` separately.

---

# PART 7 — IMPORTANT LIST CONFUSIONS

## 54. `add()` vs `add(index, element)`

```java
l.add(50);
```

adds at the end.

```java
l.add(1, 50);
```

adds at index `1`.

---

## 55. `add(index, element)` vs `set(index, element)`

Before:

```text
[10,20,30]
```

### Add

```java
l.add(1,50);
```

Result:

```text
[10,50,20,30]
```

### Set

```java
l.set(1,50);
```

Result:

```text
[10,50,30]
```

Therefore:

```text
add → INSERT
set → REPLACE
```

---

# 56. `remove(index)` vs `remove(object)`

```java
l.remove(1);
```

→ remove index `1`.

```java
l.remove(Integer.valueOf(20));
```

→ remove object `20`.

This is a classic List/overloading issue.

---

# 57. `ArrayList` vs `LinkedList`

Don't memorize:

```text
ArrayList = good
LinkedList = bad
```

Instead understand the data structures.

```text
ArrayList
    ↓
Resizable array
    ↓
Excellent random/index access
```

```text
LinkedList
    ↓
Linked nodes
    ↓
Useful for certain insertion/removal-at-ends workloads
    ↓
Also provides Deque operations
```

---

# 58. `Vector` vs `ArrayList`

The key historical distinction:

```text
ArrayList
    ↓
Not synchronized

Vector
    ↓
Synchronized
```

Vector is older and generally not the first choice for new general-purpose List code.

---

# 59. `Stack` vs `Vector`

`Stack` is:

```text
Stack extends Vector
```

Therefore Stack inherits Vector's characteristics, but adds stack-oriented operations:

```text
push()
pop()
peek()
empty()
search()
```

The purpose is:

```text
Vector → general List
Stack  → LIFO behavior
```

---

# 60. One Program Showing List Operations

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        List l = new ArrayList();

        // add()
        l.add(10);
        l.add(20);
        l.add(30);

        System.out.println(l);

        // add(index, element)
        l.add(1, 50);

        System.out.println(l);

        // get()
        System.out.println("Element = " + l.get(2));

        // set()
        l.set(2, 100);

        System.out.println(l);

        // contains()
        System.out.println("Contains 20 = " + l.contains(20));

        // indexOf()
        System.out.println("Index = " + l.indexOf(20));

        // remove(index)
        l.remove(1);

        System.out.println(l);
    }
}
```

Possible output:

```text
[10, 20, 30]
[10, 50, 20, 30]
Element = 20
[10, 50, 100, 30]
Contains 20 = false
Index = -1
[10, 100, 30]
```

Notice something important:

After:

```java
l.set(2, 100);
```

the original `20` is replaced.

Therefore:

```java
l.contains(20)
```

becomes:

```text
false
```

---

# 61. List Methods — Organized

```text
LIST
│
├── Adding
│   ├── add(element)
│   └── add(index, element)
│
├── Accessing
│   └── get(index)
│
├── Updating
│   └── set(index, element)
│
├── Removing
│   ├── remove(index)
│   └── remove(object)
│
├── Searching
│   ├── contains()
│   ├── indexOf()
│   └── lastIndexOf()
│
└── Common Collection methods
    ├── size()
    ├── isEmpty()
    ├── clear()
    ├── iterator()
    └── toArray()
```

---

# 62. Complete Mental Model

Think about the List family like this:

```text
                         LIST
                           │
              "Ordered collection"
                           │
       ┌───────────────────┼───────────────────┐
       │                   │                   │
       ↓                   ↓                   ↓
  ARRAYLIST            LINKEDLIST             VECTOR
       │                   │                   │
 Dynamic array        Linked nodes        Synchronized
       │                   │                   │
 Fast index            List + Deque        Legacy List
 access
                                               │
                                               ↓
                                             STACK
                                               │
                                               ↓
                                             LIFO
```

---

# 🔥 DEEPDIVE FINAL REVISION

### `List`

> Ordered collection that permits duplicates and supports index-based operations.

### `ArrayList`

> List backed by a dynamically resizable array; excellent general-purpose choice and generally strong for index-based access.

### `LinkedList`

> List + Deque implementation based on linked nodes; useful for particular insertion/removal-at-ends workloads.

### `Vector`

> Legacy synchronized List implementation.

### `Stack`

> Legacy LIFO stack class that extends Vector.

---

## The four in one line

```text
ArrayList  → Dynamic Array
LinkedList → Linked Nodes + Deque
Vector     → Synchronized Legacy List
Stack      → LIFO Legacy Vector
```

## The most important distinctions

```text
List
 ↓
ORDER + DUPLICATES + INDEX

ArrayList
 ↓
FAST INDEX ACCESS

LinkedList
 ↓
LINKED STRUCTURE + DEQUE

Vector
 ↓
SYNCHRONIZED + LEGACY

Stack
 ↓
LIFO
```

And one rule worth remembering for the rest of the Collections Framework:

> **Don't choose a collection merely because you memorized its name. Choose it based on the operations your program performs most frequently.**
