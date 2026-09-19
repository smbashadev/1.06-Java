# 6. Queue in Java — 3LEVEL

> **Training rule:** No Generics anywhere in this topic. All programs use normal/raw Collection Framework syntax.

The **3LEVEL** approach means we understand every concept in three stages:

* **LEVEL 1 — Basic:** What is it?
* **LEVEL 2 — Practical:** How does it work?
* **LEVEL 3 — Deep Understanding:** Why, differences, traps, and interview points.

---

# 1. Queue Interface

## LEVEL 1 — BASIC

### What is Queue?

`Queue` is an **interface** in Java's Collection Framework.

It is used when elements have to be processed according to an ordering rule.

The most common Queue behavior is:

```text
FIFO
First In → First Out
```

Example:

```text
10 → 20 → 30
```

`10` entered first, so `10` is normally removed first.

### Basic declaration

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Queue q = new LinkedList();

        q.add(10);
        q.add(20);
        q.add(30);

        System.out.println(q);
    }
}
```

Output:

```text
[10, 20, 30]
```

Important:

```text
Queue = Interface
LinkedList = Class
```

Therefore:

```java
Queue q = new Queue();
```

❌ Invalid.

---

# LEVEL 2 — PRACTICAL

## Important Queue methods

Queue methods can be remembered in three pairs.

### 1. Insertion

```java
add()
offer()
```

### 2. Removal

```java
remove()
poll()
```

### 3. Examination

```java
element()
peek()
```

---

## `add()`

Adds an element.

```java
q.add(10);
q.add(20);
q.add(30);
```

Queue:

```text
10 → 20 → 30
```

---

## `offer()`

Also attempts to add an element.

```java
q.offer(40);
```

The important difference between `add()` and `offer()` concerns what happens when insertion cannot be performed.

---

## `remove()`

Removes the head.

```java
q.remove();
```

If:

```text
10 → 20 → 30
```

then:

```text
20 → 30
```

`10` was removed.

If the Queue is empty:

```text
remove()
   ↓
NoSuchElementException
```

---

## `poll()`

Also removes the head.

```java
q.poll();
```

But if the Queue is empty:

```text
poll()
  ↓
null
```

So:

```text
remove() → exception if empty
poll()   → null if empty
```

---

## `element()`

Returns the head without removing it.

```java
q.element();
```

If:

```text
10 → 20 → 30
```

it returns:

```text
10
```

but Queue remains:

```text
10 → 20 → 30
```

If empty, it throws an exception.

---

## `peek()`

Also checks the head without removing it.

```java
q.peek();
```

If empty:

```text
peek()
  ↓
null
```

Therefore:

```text
element() → exception if empty
peek()    → null if empty
```

---

# LEVEL 3 — DEEP UNDERSTANDING

The complete Queue method map:

```text
                  QUEUE
                    |
        ┌───────────┼───────────┐
        ↓           ↓           ↓
     INSERT       REMOVE       CHECK
        |           |           |
    add/offer   remove/poll  element/peek
```

The important pairs are:

| Purpose | Strict version | Safer alternative |
| ------- | -------------- | ----------------- |
| Insert  | `add()`        | `offer()`         |
| Remove  | `remove()`     | `poll()`          |
| Examine | `element()`    | `peek()`          |

### Most important difference

```text
add()       ↔ offer()
remove()    ↔ poll()
element()   ↔ peek()
```

The right-side methods use special return values when the operation cannot be performed:

```text
offer() → false
poll()  → null
peek()  → null
```

---

# 2. PriorityQueue

## LEVEL 1 — BASIC

`PriorityQueue` is a **class** that implements the `Queue` interface.

Hierarchy:

```text
Collection
    ↓
  Queue
    ↓
PriorityQueue
```

Its purpose is to process elements according to **priority**, rather than simply relying on normal FIFO insertion order.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        PriorityQueue q = new PriorityQueue();

        q.add(30);
        q.add(10);
        q.add(20);

        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
    }
}
```

Output:

```text
10
20
30
```

For these numbers, the smallest value has the highest priority under natural ordering.

---

# LEVEL 2 — PRACTICAL

Consider:

```text
30
10
20
```

Insertion order:

```text
30 → 10 → 20
```

But processing with `poll()` gives:

```text
10
20
30
```

So PriorityQueue is **not simply a FIFO Queue**.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        PriorityQueue q = new PriorityQueue();

        q.add(50);
        q.add(20);
        q.add(40);
        q.add(10);
        q.add(30);

        while (!q.isEmpty())
        {
            System.out.println(q.poll());
        }
    }
}
```

Output:

```text
10
20
30
40
50
```

---

## Duplicate elements

PriorityQueue permits duplicates.

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        PriorityQueue q = new PriorityQueue();

        q.add(10);
        q.add(10);
        q.add(20);

        while (!q.isEmpty())
        {
            System.out.println(q.poll());
        }
    }
}
```

The two `10` values are allowed.

---

## `peek()` with PriorityQueue

```java
q.add(30);
q.add(10);
q.add(20);

System.out.println(q.peek());
```

The head is:

```text
10
```

But `peek()` does not remove it.

---

# LEVEL 3 — DEEP UNDERSTANDING

### Important misconception

Don't think:

> "PriorityQueue is always a completely sorted List."

That's incorrect.

The important guarantee is about the **head / removal order**, not that every internal element is presented as a fully sorted sequence.

For example:

```java
System.out.println(q);
```

should not be treated as the correct way to demonstrate that the entire Queue is sorted.

Instead, demonstrate priority using:

```java
q.poll();
```

repeatedly.

### PriorityQueue and `null`

`PriorityQueue` does not permit `null` elements.

```java
q.add(null);
```

causes a `NullPointerException`.

### PriorityQueue and duplicates

Duplicates are allowed.

```text
10
10
20
20
```

is valid.

---

# 3. Deque

## LEVEL 1 — BASIC

`Deque` means:

# Double Ended Queue

It is an interface.

Hierarchy:

```text
Collection
    ↓
  Queue
    ↓
  Deque
```

The special feature of Deque is:

> Elements can be inserted and removed from both ends.

Visualize:

```text
        FRONT                 REAR
          ↓                    ↓
       10 → 20 → 30
```

You can operate on:

```text
FRONT
```

and:

```text
REAR
```

---

# LEVEL 2 — PRACTICAL

## Insertion

Important methods:

```java
addFirst()
addLast()

offerFirst()
offerLast()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Deque d = new ArrayDeque();

        d.addFirst(20);
        d.addLast(30);
        d.addFirst(10);

        System.out.println(d);
    }
}
```

Conceptually:

```text
20
 ↓
20 → 30

addFirst(10)

10 → 20 → 30
```

---

## Removal

Important methods:

```java
removeFirst()
removeLast()

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
        Deque d = new ArrayDeque();

        d.addLast(10);
        d.addLast(20);
        d.addLast(30);

        System.out.println(d.removeFirst());
        System.out.println(d.removeLast());
    }
}
```

Output:

```text
10
30
```

Remaining element:

```text
20
```

---

## Examination

To look at the ends without removing:

```java
getFirst()
getLast()

peekFirst()
peekLast()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Deque d = new ArrayDeque();

        d.addLast(10);
        d.addLast(20);
        d.addLast(30);

        System.out.println(d.peekFirst());
        System.out.println(d.peekLast());
    }
}
```

Output:

```text
10
30
```

The Deque remains:

```text
10 → 20 → 30
```

---

# LEVEL 3 — DEEP UNDERSTANDING

Deque is more powerful than an ordinary FIFO Queue because it gives access to **both ends**.

```text
                  DEQUE
                    |
             ┌──────┴──────┐
             ↓             ↓
          FRONT           REAR
             |             |
        addFirst()      addLast()
        removeFirst()   removeLast()
        peekFirst()     peekLast()
```

---

# Deque Can Behave Like Queue

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
        Deque d = new ArrayDeque();

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

That's FIFO.

```text
Deque
 ↓
Queue behavior
```

---

# Deque Can Behave Like Stack

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
        Deque d = new ArrayDeque();

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

This is:

```text
LIFO
Last In → First Out
```

Therefore:

```text
Deque
 ├── Can behave like Queue
 └── Can behave like Stack
```

---

# 🔥 3LEVEL MASTER COMPARISON

| Feature                      | Queue                     | PriorityQueue             | Deque                           |
| ---------------------------- | ------------------------- | ------------------------- | ------------------------------- |
| Type                         | Interface                 | Class                     | Interface                       |
| Main idea                    | FIFO-style processing     | Priority-based processing | Both ends                       |
| Basic implementation example | `LinkedList`              | `PriorityQueue`           | `ArrayDeque`                    |
| Insert                       | `add()`, `offer()`        | `add()`, `offer()`        | `addFirst()`, `addLast()`       |
| Remove                       | `remove()`, `poll()`      | `remove()`, `poll()`      | `removeFirst()`, `removeLast()` |
| Examine                      | `element()`, `peek()`     | `element()`, `peek()`     | `peekFirst()`, `peekLast()`     |
| Both ends accessible?        | No                        | No                        | Yes                             |
| Duplicate elements           | Yes                       | Yes                       | Yes                             |
| `null`                       | Depends on implementation | Not allowed               | Not allowed in `ArrayDeque`     |

---

# 🧠 3LEVEL MEMORY MAP

```text
                 COLLECTION
                     |
                   QUEUE
              _______|_______
             |               |
             ↓               ↓
      PriorityQueue         Deque
             |               |
        PRIORITY         BOTH ENDS
                             |
                    ┌────────┴────────┐
                    ↓                 ↓
               Queue behavior    Stack behavior
                    |                 |
               FIFO              LIFO
```

## Queue

```text
FIRST IN
   ↓
10 → 20 → 30
   ↓
FIRST OUT
```

## PriorityQueue

```text
30
10  → priority → 10 first
20
```

## Deque

```text
        FRONT             REAR
          ↓                 ↓
       10 → 20 → 30
          ↑                 ↑
       operate           operate
       here              here
```

---

# ⭐ Final 3LEVEL Revision

### Queue

> An interface used for holding elements that are processed according to a queue ordering policy, commonly FIFO.

### PriorityQueue

> A Queue implementation that processes its head according to priority rather than ordinary insertion order.

### Deque

> A Double Ended Queue that allows insertion, removal, and examination at both ends.

### Three critical pairs

```text
add()       ↔ offer()
remove()    ↔ poll()
element()   ↔ peek()
```

### Deque's three important groups

```text
INSERT:
addFirst()
addLast()

REMOVE:
removeFirst()
removeLast()

CHECK:
peekFirst()
peekLast()
```

### Most important conceptual difference

```text
Queue
→ FIFO

PriorityQueue
→ Priority

Deque
→ Both Ends
```

**No Generics are used in any program in this 3LEVEL lesson.**
