# 7. Deque in Java — DEEPDIVE

> **Training rule:** No Generics are used anywhere in this lesson.
> All programs use normal/raw Collection Framework syntax such as `ArrayDeque d = new ArrayDeque();`.

---

# 1. Deque — Foundation

Before studying `ArrayDeque` and `LinkedList`, understand exactly what a **Deque** is.

## What is Deque?

**Deque** stands for:

> **Double Ended Queue**

A Deque allows us to insert, remove, and examine elements from **both the front and rear**.

```text
                 FRONT                  REAR
                   ↓                      ↓
                10 → 20 → 30 → 40
                   ↑                      ↑
              operations             operations
              possible               possible
              here                   here
```

Therefore, unlike an ordinary Queue where we normally think in terms of one insertion end and one removal end, a Deque provides operations at **both ends**.

---

# 2. Deque is an Interface

`Deque` is not a class.

It is an interface from `java.util`.

```java
import java.util.*;
```

You cannot write:

```java
Deque d = new Deque();
```

❌ Invalid.

Why?

Because an interface cannot be directly instantiated.

Instead, use a class that implements `Deque`.

For example:

```java
Deque d = new ArrayDeque();
```

or:

```java
Deque d = new LinkedList();
```

Conceptually:

```text
                  Collection
                      ↓
                    Queue
                      ↓
                    Deque
                   /     \
                  ↓       ↓
           ArrayDeque   LinkedList
```

---

# 3. Why Do We Need Deque?

Suppose we have:

```text
10 → 20 → 30
```

With a normal Queue, we generally work like:

```text
insert → REAR
remove → FRONT
```

But sometimes an application needs:

```text
insert at FRONT
insert at REAR

remove from FRONT
remove from REAR
```

That is exactly what Deque provides.

---

# 4. Two Important Deque Implementations

Your roadmap contains:

```text
7. Deque
   ├── ArrayDeque
   └── LinkedList as Deque
```

So we need to understand:

### 1. ArrayDeque

A dedicated Deque implementation based on a resizable array structure.

### 2. LinkedList

A linked-list class that also implements `Deque`, so it can be used as a double-ended queue.

---

# PART A — ARRAYDEQUE

# 5. What is ArrayDeque?

`ArrayDeque` is a class in `java.util`.

It implements the `Deque` interface.

```text
Deque
   ↑
ArrayDeque
```

A basic program:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addFirst(10);
        d.addLast(20);
        d.addLast(30);

        System.out.println(d);
    }
}
```

Output:

```text
[10, 20, 30]
```

---

# 6. Why is it called ArrayDeque?

The name can be understood as:

```text
Array + Deque
```

It is designed around a resizable-array representation while providing Deque operations.

You do **not** need to manually manage the array.

For example, you don't write:

```java
int[] arr = new int[10];
```

Instead:

```java
ArrayDeque d = new ArrayDeque();
```

The class manages its internal storage.

---

# 7. ArrayDeque: Insertion at Front

Use:

```java
addFirst()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addFirst(10);
        d.addFirst(20);
        d.addFirst(30);

        System.out.println(d);
    }
}
```

Conceptually:

```text
addFirst(10)

10
```

Then:

```text
addFirst(20)

20 → 10
```

Then:

```text
addFirst(30)

30 → 20 → 10
```

So output is:

```text
[30, 20, 10]
```

---

# 8. ArrayDeque: Insertion at Rear

Use:

```java
addLast()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addLast(10);
        d.addLast(20);
        d.addLast(30);

        System.out.println(d);
    }
}
```

Output:

```text
[10, 20, 30]
```

Conceptually:

```text
10 → 20 → 30
```

---

# 9. `addFirst()` vs `addLast()`

Suppose:

```text
[20]
```

Execute:

```java
d.addFirst(10);
```

Result:

```text
[10, 20]
```

But if:

```java
d.addLast(30);
```

Result:

```text
[20, 30]
```

So:

```text
addFirst()
→ FRONT

addLast()
→ REAR
```

---

# 10. ArrayDeque: Removal from Front

Use:

```java
removeFirst()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addLast(10);
        d.addLast(20);
        d.addLast(30);

        System.out.println(d.removeFirst());

        System.out.println(d);
    }
}
```

Output:

```text
10
[20, 30]
```

The first element was removed.

---

# 11. ArrayDeque: Removal from Rear

Use:

```java
removeLast()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addLast(10);
        d.addLast(20);
        d.addLast(30);

        System.out.println(d.removeLast());

        System.out.println(d);
    }
}
```

Output:

```text
30
[10, 20]
```

The last element was removed.

---

# 12. `removeFirst()` vs `removeLast()`

Suppose:

```text
10 → 20 → 30
```

### `removeFirst()`

```text
10 → removed

20 → 30
```

### `removeLast()`

```text
30 → removed

10 → 20
```

Therefore:

```text
removeFirst()
→ removes FRONT

removeLast()
→ removes REAR
```

---

# 13. ArrayDeque: Examining the Front

Use:

```java
peekFirst()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addLast(10);
        d.addLast(20);
        d.addLast(30);

        System.out.println(d.peekFirst());

        System.out.println(d);
    }
}
```

Output:

```text
10
[10, 20, 30]
```

Notice:

```text
peekFirst()
```

did **not** remove `10`.

---

# 14. ArrayDeque: Examining the Rear

Use:

```java
peekLast()
```

Example:

```java
System.out.println(d.peekLast());
```

For:

```text
10 → 20 → 30
```

the result is:

```text
30
```

The Deque remains unchanged.

---

# 15. ArrayDeque — Complete Basic Method Map

```text
                   ArrayDeque
                       |
          ┌────────────┼────────────┐
          ↓            ↓            ↓
       INSERT        REMOVE       EXAMINE
          |            |            |
     addFirst()   removeFirst()  peekFirst()
     addLast()    removeLast()   peekLast()
```

This is the most important map.

---

# 16. `offerFirst()` and `offerLast()`

Deque also provides:

```java
offerFirst()
offerLast()
```

They are insertion methods.

For example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.offerFirst(10);
        d.offerLast(20);

        System.out.println(d);
    }
}
```

Output:

```text
[10, 20]
```

Conceptually:

```text
addFirst()  ↔ offerFirst()
addLast()   ↔ offerLast()
```

The `offer` versions are designed to report unsuccessful insertion through a return value rather than the exception-oriented behavior associated with `add`.

---

# 17. `pollFirst()` and `pollLast()`

Deque also provides:

```java
pollFirst()
pollLast()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addLast(10);
        d.addLast(20);
        d.addLast(30);

        System.out.println(d.pollFirst());
        System.out.println(d.pollLast());

        System.out.println(d);
    }
}
```

Output:

```text
10
30
[20]
```

Important:

```text
removeFirst() → exception-oriented when empty
pollFirst()   → null when empty

removeLast()  → exception-oriented when empty
pollLast()    → null when empty
```

---

# 18. `getFirst()` and `getLast()`

These examine the ends without removing.

```java
d.getFirst();
d.getLast();
```

Difference from `peekFirst()` / `peekLast()` is what happens when the Deque is empty.

```text
getFirst()  → exception if empty
peekFirst() → null if empty

getLast()   → exception if empty
peekLast()  → null if empty
```

So the same general pattern seen with Queue continues in Deque.

---

# 19. Complete ArrayDeque Method Families

## Insertion

```text
addFirst()
addLast()

offerFirst()
offerLast()
```

## Removal

```text
removeFirst()
removeLast()

pollFirst()
pollLast()
```

## Examination

```text
getFirst()
getLast()

peekFirst()
peekLast()
```

---

# 20. ArrayDeque Does Not Allow `null`

This is one of the most important properties.

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.add(null);
    }
}
```

This results in:

```text
NullPointerException
```

Therefore:

```text
ArrayDeque
→ null NOT allowed
```

---

# 21. Why Does ArrayDeque Not Allow Null?

A useful practical reason is that the Deque uses `null` as a meaningful return value for operations such as:

```java
pollFirst()
pollLast()
peekFirst()
peekLast()
```

For example, an empty Deque can produce:

```text
peekFirst()
→ null
```

If `null` were also a legitimate stored element, it would become difficult to distinguish:

```text
"the Deque contains null"
```

from:

```text
"the Deque is empty"
```

Therefore `ArrayDeque` prohibits `null`.

---

# 22. ArrayDeque Allows Duplicates

Unlike a Set, an ArrayDeque can contain duplicate values.

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.add(10);
        d.add(10);
        d.add(20);

        System.out.println(d);
    }
}
```

Output:

```text
[10, 10, 20]
```

Therefore:

```text
Deque
→ duplicates allowed
```

---

# 23. ArrayDeque Is Not a Set

This is another important distinction.

ArrayDeque:

```text
10
20
10
```

can contain duplicates.

Set:

```text
10
20
```

does not retain duplicate occurrences.

So:

```text
ArrayDeque → duplicates allowed
Set        → duplicates not allowed
```

---

# 24. ArrayDeque as a Queue

A Deque can behave exactly like a FIFO Queue if you consistently use:

```text
addLast()
removeFirst()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addLast(10);
        d.addLast(20);
        d.addLast(30);

        System.out.println(d.removeFirst());
        System.out.println(d.removeFirst());
        System.out.println(d.removeFirst());
    }
}
```

Output:

```text
10
20
30
```

That's:

```text
FIFO
First In → First Out
```

---

# 25. ArrayDeque as a Stack

A Deque can also behave like a Stack.

Use the **same end** for insertion and removal.

For example:

```text
addFirst()
removeFirst()
```

Program:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addFirst(10);
        d.addFirst(20);
        d.addFirst(30);

        System.out.println(d.removeFirst());
        System.out.println(d.removeFirst());
        System.out.println(d.removeFirst());
    }
}
```

Output:

```text
30
20
10
```

That's:

```text
LIFO
Last In → First Out
```

---

# 26. ArrayDeque — Internal Concept

You don't need to manually implement it, but understand the idea.

It uses an array-like circular structure internally rather than creating a separate node for every element.

Conceptually:

```text
      ┌─────┬─────┬─────┬─────┬─────┐
      │     │ 10  │ 20  │ 30  │     │
      └─────┴─────┴─────┴─────┴─────┘
        ↑                       ↑
      FRONT                   REAR
```

When elements are inserted or removed from either end, the implementation manages its internal positions efficiently.

The actual internal implementation details are more sophisticated than this simplified picture, but this is enough for understanding the concept.

---

# PART B — LINKEDLIST AS DEQUE

# 27. What is LinkedList as Deque?

`LinkedList` is a class that implements several collection interfaces, including `Deque`.

Therefore:

```java
Deque d = new LinkedList();
```

is valid.

This is important because the same `LinkedList` object can be used in different roles.

Conceptually:

```text
                    LinkedList
                 /      |       \
                ↓       ↓        ↓
              List    Queue    Deque
```

So `LinkedList` is not only a List implementation.

It can also act as a Queue and a Deque.

---

# 28. Basic LinkedList as Deque Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Deque d = new LinkedList();

        d.addFirst(10);
        d.addLast(20);
        d.addLast(30);

        System.out.println(d);
    }
}
```

Output:

```text
[10, 20, 30]
```

---

# 29. LinkedList — Insert at Front

```java
d.addFirst(10);
```

Suppose:

```text
20 → 30
```

After:

```text
addFirst(10)
```

we get:

```text
10 → 20 → 30
```

---

# 30. LinkedList — Insert at Rear

```java
d.addLast(30);
```

Suppose:

```text
10 → 20
```

After:

```text
addLast(30)
```

we get:

```text
10 → 20 → 30
```

---

# 31. LinkedList — Remove from Front

```java
d.removeFirst();
```

For:

```text
10 → 20 → 30
```

result:

```text
10 removed
```

Remaining:

```text
20 → 30
```

---

# 32. LinkedList — Remove from Rear

```java
d.removeLast();
```

For:

```text
10 → 20 → 30
```

result:

```text
30 removed
```

Remaining:

```text
10 → 20
```

---

# 33. LinkedList — Examine Front and Rear

Use:

```java
d.peekFirst();
d.peekLast();
```

For:

```text
10 → 20 → 30
```

we get:

```text
peekFirst() → 10
peekLast()  → 30
```

Nothing is removed.

---

# 34. LinkedList Allows `null`

Unlike `ArrayDeque`, `LinkedList` permits `null`.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedList l = new LinkedList();

        l.add(10);
        l.add(null);
        l.add(20);

        System.out.println(l);
    }
}
```

Output:

```text
[10, null, 20]
```

Therefore:

```text
ArrayDeque → null not allowed
LinkedList → null allowed
```

---

# 35. LinkedList Allows Duplicates

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedList l = new LinkedList();

        l.addLast(10);
        l.addLast(10);
        l.addLast(20);

        System.out.println(l);
    }
}
```

Output:

```text
[10, 10, 20]
```

So both:

```text
ArrayDeque
LinkedList
```

allow duplicate elements.

---

# 36. LinkedList as Queue

Use:

```text
addLast()
removeFirst()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedList l = new LinkedList();

        l.addLast(10);
        l.addLast(20);
        l.addLast(30);

        System.out.println(l.removeFirst());
        System.out.println(l.removeFirst());
        System.out.println(l.removeFirst());
    }
}
```

Output:

```text
10
20
30
```

FIFO behavior.

---

# 37. LinkedList as Stack

Use:

```text
addFirst()
removeFirst()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedList l = new LinkedList();

        l.addFirst(10);
        l.addFirst(20);
        l.addFirst(30);

        System.out.println(l.removeFirst());
        System.out.println(l.removeFirst());
        System.out.println(l.removeFirst());
    }
}
```

Output:

```text
30
20
10
```

LIFO behavior.

---

# 38. Why Can LinkedList Be Used as Deque?

Because `LinkedList` implements the `Deque` interface.

Therefore it provides Deque operations such as:

```text
addFirst()
addLast()

removeFirst()
removeLast()

peekFirst()
peekLast()
```

This is not because every List automatically becomes a Deque.

It is specifically because **LinkedList implements Deque**.

---

# 39. LinkedList Internal Concept

A linked list stores elements in linked nodes.

Simplified concept:

```text
      ┌─────┐       ┌─────┐       ┌─────┐
      │ 10  │──────→│ 20  │──────→│ 30  │
      └─────┘       └─────┘       └─────┘
```

In Java's `LinkedList`, the actual implementation is a **doubly linked list**, meaning links exist in both directions.

Simplified:

```text
NULL ← 10 ↔ 20 ↔ 30 → NULL
```

This makes operations at the ends particularly natural for a linked-list implementation.

---

# 40. ArrayDeque vs LinkedList — Internal Difference

This is one of the most important parts of the topic.

## ArrayDeque

Conceptually uses:

```text
Resizable array
```

```text
[10][20][30][40]
```

## LinkedList

Uses:

```text
Linked nodes
```

```text
10 ↔ 20 ↔ 30 ↔ 40
```

Therefore:

```text
ArrayDeque
→ array-based Deque

LinkedList
→ node-based Deque
```

---

# 41. ArrayDeque vs LinkedList as Deque

| Feature                | ArrayDeque        | LinkedList                                   |
| ---------------------- | ----------------- | -------------------------------------------- |
| Class                  | Yes               | Yes                                          |
| Implements Deque       | Yes               | Yes                                          |
| Underlying concept     | Resizable array   | Doubly linked nodes                          |
| Add at front           | Yes               | Yes                                          |
| Add at rear            | Yes               | Yes                                          |
| Remove at front        | Yes               | Yes                                          |
| Remove at rear         | Yes               | Yes                                          |
| Duplicate elements     | Allowed           | Allowed                                      |
| `null`                 | **Not allowed**   | **Allowed**                                  |
| Queue behavior         | Yes               | Yes                                          |
| Stack behavior         | Yes               | Yes                                          |
| Random List operations | No List interface | Yes, because LinkedList also implements List |

That last row is particularly important.

---

# 42. Why Would We Choose LinkedList if ArrayDeque Exists?

Because `LinkedList` provides more than Deque functionality.

It can act as:

```text
List
Queue
Deque
```

For example:

```java
LinkedList l = new LinkedList();
```

can use List operations as well as Deque operations.

`ArrayDeque`, however, is specifically designed around the Deque/Queue role.

Therefore, if you specifically need a **Deque**, `ArrayDeque` is commonly the more natural choice.

If you need the additional List behavior of `LinkedList`, then `LinkedList` may be appropriate.

---

# 43. Important Performance Concept

At a high level:

### ArrayDeque

Designed for efficient operations at both ends using its internal array structure.

### LinkedList

Designed around linked nodes, with efficient operations at the ends.

But don't conclude:

> "LinkedList is always faster."

or:

> "ArrayDeque is always faster."

Performance depends on the operation, workload, memory behavior, and implementation details.

For normal Deque usage, `ArrayDeque` is generally the preferred starting point when you don't specifically need `LinkedList`'s List functionality or its ability to store `null`.

---

# 44. Null Difference — Extremely Important

Memorize this table:

| Collection | `null` allowed? |
| ---------- | --------------- |
| ArrayDeque | ❌ No            |
| LinkedList | ✅ Yes           |

Example:

```java
ArrayDeque d = new ArrayDeque();
d.add(null);
```

❌ Not allowed.

But:

```java
LinkedList l = new LinkedList();
l.add(null);
```

✅ Allowed.

---

# 45. Both Allow Duplicate Elements

```text
ArrayDeque
→ duplicates allowed

LinkedList
→ duplicates allowed
```

Example:

```text
10
10
20
20
```

is valid in both.

---

# 46. ArrayDeque and LinkedList Do Not Sort Automatically

This is another common misconception.

Suppose:

```java
d.addLast(30);
d.addLast(10);
d.addLast(20);
```

The Deque does not automatically become:

```text
10 → 20 → 30
```

It retains the order created by your operations.

You should not confuse:

```text
Deque
```

with:

```text
TreeSet
PriorityQueue
```

which have ordering-related behavior.

---

# 47. Deque Does Not Mean "Sorted"

This is worth emphasizing.

Deque describes **where operations can occur**:

```text
FRONT ←→ REAR
```

It does not describe sorting.

Therefore:

```text
Deque
→ double-ended access

PriorityQueue
→ priority ordering

TreeSet
→ sorted set
```

These are different concepts.

---

# 48. Complete Practical Program — ArrayDeque

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addFirst(20);
        d.addFirst(10);
        d.addLast(30);
        d.addLast(40);

        System.out.println("Deque: " + d);

        System.out.println("First: " + d.peekFirst());
        System.out.println("Last: " + d.peekLast());

        System.out.println("Removed First: " + d.removeFirst());
        System.out.println("Removed Last: " + d.removeLast());

        System.out.println("Deque: " + d);
    }
}
```

Output:

```text
Deque: [10, 20, 30, 40]
First: 10
Last: 40
Removed First: 10
Removed Last: 40
Deque: [20, 30]
```

---

# 49. Complete Practical Program — LinkedList as Deque

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedList l = new LinkedList();

        l.addFirst(20);
        l.addFirst(10);
        l.addLast(30);
        l.addLast(40);

        System.out.println("Deque: " + l);

        System.out.println("First: " + l.peekFirst());
        System.out.println("Last: " + l.peekLast());

        System.out.println("Removed First: " + l.removeFirst());
        System.out.println("Removed Last: " + l.removeLast());

        System.out.println("Deque: " + l);
    }
}
```

Output:

```text
Deque: [10, 20, 30, 40]
First: 10
Last: 40
Removed First: 10
Removed Last: 40
Deque: [20, 30]
```

---

# 50. Final Conceptual Comparison

```text
                         DEQUE
                           |
              Double Ended Queue
                           |
                  ┌────────┴────────┐
                  ↓                 ↓
             ArrayDeque         LinkedList
                  |                 |
             array-based       node-based
                  |                 |
          null not allowed      null allowed
                  |                 |
                  └────────┬────────┘
                           ↓
                     BOTH ENDS
                           |
             ┌─────────────┴─────────────┐
             ↓                           ↓
          FRONT                         REAR
             |                           |
        addFirst()                   addLast()
        removeFirst()                removeLast()
        peekFirst()                  peekLast()
```

# ⭐ Deep-Dive Final Summary

### `ArrayDeque`

> A dedicated `Deque` implementation using a resizable-array-based structure. It supports efficient operations at both ends, permits duplicates, and does **not** permit `null`.

### `LinkedList as Deque`

> `LinkedList` implements `Deque`, so a LinkedList object can be used for double-ended queue operations. It uses a doubly linked structure, permits duplicates and `null`, and additionally provides List functionality.

### The four fundamental operations

```text
addFirst()     → insert at FRONT
addLast()      → insert at REAR

removeFirst()  → remove from FRONT
removeLast()   → remove from REAR
```

### The two examination operations

```text
peekFirst()    → see FRONT
peekLast()     → see REAR
```

### Queue behavior

```text
addLast()
   ↓
10 → 20 → 30
↑
removeFirst()

FIFO
```

### Stack behavior

```text
addFirst()
   ↓
10
20
30
↑
removeFirst()

LIFO
```

### The one comparison you must never forget

```text
ArrayDeque
→ null ❌

LinkedList
→ null ✅
```

**No Generics are used anywhere in this DEEPDIVE lesson.**
