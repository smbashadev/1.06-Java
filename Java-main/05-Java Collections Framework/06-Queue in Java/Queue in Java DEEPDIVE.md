# 6. Queue in Java — DEEPDIVE

> **Training rule:** No Generics anywhere in this lesson. All programs use normal/raw collection syntax. Generics will be taught separately in Topic 14.

This DEEPDIVE explains **Queue Interface, PriorityQueue, and Deque individually**, including their purpose, hierarchy, methods, working, programs, differences, and common doubts.

---

# 1. Queue Interface

## 1.1 What is a Queue?

`Queue` is an interface in the `java.util` package.

A Queue represents a collection designed mainly for **holding elements before they are processed**.

The traditional Queue principle is:

> **FIFO — First In, First Out**

Imagine people standing in a line:

```text
First person → Second person → Third person
     ↓
Processed first
```

The person who enters first generally leaves first.

Therefore:

```text
Queue = FIFO
```

---

# 1.2 Why do we need Queue?

Suppose a printer receives three documents:

```text
Document A
Document B
Document C
```

If A arrives first:

```text
A → B → C
```

The printer processes:

```text
A
B
C
```

A Queue is appropriate because the data must be processed in an orderly sequence.

Other examples include:

* Printer jobs
* Customer service lines
* CPU/task processing
* Request processing
* Message processing
* Breadth-first search
* Scheduling systems

The important idea is:

```text
Data arrives
     ↓
Queue stores it
     ↓
Data is processed
```

---

# 1.3 Queue hierarchy

The relevant part of the Collection Framework is:

```text
                 Collection
                     |
                   Queue
                ┌────┴─────┐
                ↓          ↓
        PriorityQueue     Deque
                           |
                     ┌─────┴─────┐
                     ↓           ↓
                 ArrayDeque   LinkedList
```

For this Topic 6 lesson, we concentrate on:

```text
Queue Interface
PriorityQueue
Deque
```

`ArrayDeque` and `LinkedList as Deque` are covered in your **Topic 7 — Deque**.

---

# 1.4 Is Queue a class or interface?

Queue is an **interface**.

Therefore:

```java
Queue q = new Queue();
```

❌ Invalid.

You need an implementation.

For example:

```java
Queue q = new PriorityQueue();
```

or:

```java
Queue q = new LinkedList();
```

or:

```java
Queue q = new ArrayDeque();
```

---

# 1.5 Queue does not mean only LinkedList

This is a common beginner misunderstanding.

Some students learn:

```java
Queue q = new LinkedList();
```

and conclude:

> Queue means LinkedList.

Wrong.

`Queue` is the interface.

`LinkedList`, `PriorityQueue`, and `ArrayDeque` can provide Queue-related behavior.

Conceptually:

```text
Queue
 ↓
Interface
 ↓
Different implementations
```

---

# 1.6 Basic Queue program

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

Conceptually:

```text
10 → 20 → 30
```

The first element is the **head** of the Queue.

```text
HEAD
 ↓
10 → 20 → 30
          ↑
         TAIL
```

Different Queue implementations may have different internal structures, but this head/tail model is useful for understanding Queue operations.

---

# 1.7 Queue terminology

Two words are particularly important:

### Head

The element at the front of the Queue.

```text
HEAD
 ↓
10 → 20 → 30
```

### Tail

The rear/end where new elements are normally inserted in a traditional FIFO Queue.

```text
10 → 20 → 30
          ↑
         TAIL
```

So:

```text
Insertion → Tail
Removal   → Head
```

This produces FIFO behavior.

---

# 2. Queue Methods

The important Queue methods are:

```text
add()
offer()

remove()
poll()

element()
peek()
```

They form three pairs:

```text
Insertion:
add() ↔ offer()

Removal:
remove() ↔ poll()

Examination:
element() ↔ peek()
```

Let's understand each pair carefully.

---

# 3. `add()`

`add()` inserts an element into the Queue.

```java
Queue q = new LinkedList();

q.add(10);
q.add(20);
q.add(30);

System.out.println(q);
```

Conceptually:

```text
10 → 20 → 30
```

The first inserted element becomes the head in a normal FIFO Queue.

---

# 3.1 What does `add()` return?

`add()` returns a boolean indicating successful insertion.

Example:

```java
System.out.println(q.add(10));
```

Usually:

```text
true
```

For a Queue with capacity restrictions, `add()` can throw an exception when insertion cannot be performed.

---

# 4. `offer()`

`offer()` is another Queue insertion method.

```java
Queue q = new LinkedList();

q.offer(10);
q.offer(20);
q.offer(30);

System.out.println(q);
```

Conceptually:

```text
10 → 20 → 30
```

The important distinction is how failure is reported.

```text
add()
→ may throw exception if insertion cannot be performed

offer()
→ returns false if insertion cannot be performed
```

For ordinary unbounded Queue implementations, both commonly succeed.

---

# 5. `add()` vs `offer()`

| `add()`                                | `offer()`                          |
| -------------------------------------- | ---------------------------------- |
| Inserts element                        | Inserts element                    |
| Returns boolean                        | Returns boolean                    |
| Can throw exception if insertion fails | Returns `false` if insertion fails |

Memory:

```text
add   → insert, exception possible
offer → insert, false possible
```

---

# 6. `remove()`

`remove()` removes and returns the head element.

Suppose:

```text
10 → 20 → 30
```

Execute:

```java
q.remove();
```

Result:

```text
10
```

Remaining Queue:

```text
20 → 30
```

So:

```text
remove()
→ remove HEAD
```

---

# 6.1 What happens if Queue is empty?

This is important.

```java
Queue q = new LinkedList();

q.remove();
```

There is nothing to remove.

`remove()` throws:

```text
NoSuchElementException
```

Therefore:

```text
remove()
→ empty Queue
→ exception
```

---

# 7. `poll()`

`poll()` also removes and returns the head.

Suppose:

```text
10 → 20 → 30
```

Then:

```java
q.poll();
```

returns:

```text
10
```

and leaves:

```text
20 → 30
```

---

# 7.1 What happens when Queue is empty?

Unlike `remove()`, `poll()` returns:

```text
null
```

So:

```text
remove()
→ empty Queue → exception

poll()
→ empty Queue → null
```

---

# 8. `remove()` vs `poll()`

| `remove()`              | `poll()`                |
| ----------------------- | ----------------------- |
| Removes head            | Removes head            |
| Returns removed element | Returns removed element |
| Empty Queue → exception | Empty Queue → `null`    |

Memory:

```text
remove → strict
poll   → returns null when empty
```

---

# 9. `element()`

`element()` returns the head element **without removing it**.

Suppose:

```text
10 → 20 → 30
```

Execute:

```java
q.element();
```

Result:

```text
10
```

Queue remains:

```text
10 → 20 → 30
```

Therefore:

```text
element()
→ see HEAD
→ don't remove HEAD
```

---

# 9.1 Empty Queue with `element()`

If Queue is empty:

```java
q.element();
```

throws:

```text
NoSuchElementException
```

---

# 10. `peek()`

`peek()` also examines the head without removing it.

```java
q.peek();
```

If:

```text
10 → 20 → 30
```

the result is:

```text
10
```

The Queue remains:

```text
10 → 20 → 30
```

If the Queue is empty:

```text
peek()
→ null
```

---

# 11. `element()` vs `peek()`

| `element()`             | `peek()`             |
| ----------------------- | -------------------- |
| Returns head            | Returns head         |
| Does not remove         | Does not remove      |
| Empty Queue → exception | Empty Queue → `null` |

Memory:

```text
element → strict
peek    → null when empty
```

---

# 12. Complete Queue Method Map

This is worth memorizing:

```text
                    QUEUE
                      |
        ┌─────────────┼─────────────┐
        ↓             ↓             ↓
     INSERT         REMOVE       EXAMINE
        |             |             |
   add / offer   remove / poll  element / peek
```

And the behavior:

```text
add()
→ insert

offer()
→ insert

remove()
→ remove head / exception if empty

poll()
→ remove head / null if empty

element()
→ inspect head / exception if empty

peek()
→ inspect head / null if empty
```

---

# 13. Complete Queue Program

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

        System.out.println("Queue = " + q);

        System.out.println("Head = " + q.peek());

        System.out.println("Removed = " + q.poll());

        System.out.println("Queue after poll = " + q);

        System.out.println("Head = " + q.element());

        System.out.println("Removed = " + q.remove());

        System.out.println("Final Queue = " + q);
    }
}
```

Conceptual flow:

```text
After add:
10 → 20 → 30

peek():
10

poll():
remove 10

20 → 30

element():
20

remove():
remove 20

30
```

---

# 14. FIFO — Deep Understanding

FIFO means:

> **First In, First Out**

Suppose:

```text
q.add(100);
q.add(200);
q.add(300);
```

Queue:

```text
100 → 200 → 300
```

First:

```text
100
```

was inserted.

Therefore first:

```text
100
```

is removed.

Then:

```text
200
```

Then:

```text
300
```

So:

```text
Insertion order:
100 → 200 → 300

Removal order:
100 → 200 → 300
```

---

# 15. Real-Life FIFO Example

Imagine a ticket counter:

```text
Person A
Person B
Person C
```

A comes first.

So:

```text
A → B → C
```

The service order is:

```text
A
B
C
```

This is FIFO.

---

# 16. But Queue Interface Alone Does Not Mean Every Implementation Has Identical Ordering

This is an important conceptual point.

`Queue` defines Queue operations.

But implementations can provide different ordering policies.

For example:

```text
LinkedList used as Queue
→ FIFO behavior

PriorityQueue
→ priority-based ordering

Deque
→ can operate at both ends
```

Therefore don't oversimplify:

```text
Queue = always FIFO in every possible implementation
```

A better understanding is:

> A traditional Queue is FIFO, but Queue implementations can define different ordering behavior.

---

# 17. PriorityQueue

Now we move to the second major part of Topic 6.

`PriorityQueue` is a class in `java.util`.

Hierarchy:

```text
Collection
    |
   Queue
    |
PriorityQueue
```

Its purpose is:

> Process elements according to priority.

---

# 18. Why do we need PriorityQueue?

Suppose these tasks arrive:

```text
Task A → priority 30
Task B → priority 10
Task C → priority 20
```

A normal FIFO Queue might process according to arrival:

```text
A → B → C
```

But a priority-based Queue asks:

> Which element has the highest priority?

For numbers under natural ordering, the smallest value has priority.

Therefore:

```text
10 → 20 → 30
```

---

# 19. Basic PriorityQueue Program

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

        System.out.println(q);

        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
    }
}
```

The removal sequence is:

```text
10
20
30
```

because the smallest number has the highest priority under natural ordering.

---

# 20. Important PriorityQueue Doubt

A common mistake is:

> "PriorityQueue stores everything in completely sorted order."

Don't use that mental model.

The important behavior is:

```text
peek()
→ highest-priority element

poll()
→ removes highest-priority element
```

The internal representation is not simply an ordinary sorted List.

Therefore, if you print the whole PriorityQueue, don't use that output as proof that every element is globally sorted.

The **priority of the head** is what matters.

---

# 21. PriorityQueue with Strings

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        PriorityQueue q = new PriorityQueue();

        q.add("Java");
        q.add("C");
        q.add("Python");

        while (!q.isEmpty())
        {
            System.out.println(q.poll());
        }
    }
}
```

The elements are processed according to their natural ordering.

For Strings, that means lexicographical ordering.

Conceptually:

```text
C
Java
Python
```

---

# 22. PriorityQueue and FIFO — Important Difference

Consider:

```java
PriorityQueue q = new PriorityQueue();

q.add(30);
q.add(10);
q.add(20);
```

Insertion order:

```text
30 → 10 → 20
```

But removal order:

```text
10 → 20 → 30
```

Therefore:

```text
PriorityQueue
≠ ordinary insertion-order FIFO behavior
```

Instead:

```text
PriorityQueue
→ priority decides processing order
```

---

# 23. What is the default priority?

For elements with natural ordering, `PriorityQueue` uses their natural ordering.

For numbers:

```text
10 < 20 < 30
```

So:

```text
10
```

comes to the head first.

Thus, in the normal numeric case:

```text
smallest number = highest priority
```

Later, `Comparator` allows custom priority rules.

That belongs to your separate Comparator topic.

---

# 24. Does PriorityQueue allow duplicate elements?

Yes.

This is a very important difference from Set.

```java
PriorityQueue q = new PriorityQueue();

q.add(10);
q.add(10);
q.add(20);

System.out.println(q);
```

Both `10` values can exist.

Why?

Because:

```text
PriorityQueue → Queue → Collection
```

It is **not a Set**.

So:

```text
Set
→ duplicates not allowed

Queue
→ duplicates can be allowed
```

---

# 25. Does PriorityQueue allow null?

No.

Adding `null` to a PriorityQueue results in:

```text
NullPointerException
```

because the queue needs to maintain priority ordering.

---

# 26. PriorityQueue Summary

```text
PriorityQueue
      ↓
Queue implementation
      ↓
Priority-based processing
      ↓
Natural ordering by default
      ↓
Smallest number normally comes first
```

Remember:

```text
peek()
→ shows highest-priority element

poll()
→ removes highest-priority element
```

---

# 27. Deque

Now the third major part.

`Deque` means:

> **Double Ended Queue**

Pronounced approximately:

> "deck"

Deque is an interface in `java.util`.

Hierarchy:

```text
Collection
    |
  Queue
    |
  Deque
```

It allows operations at **both ends**.

---

# 28. Why do we need Deque?

A traditional Queue primarily thinks in terms of:

```text
Insert → Rear
Remove → Front
```

A Deque gives you:

```text
Front
 ↓
10 → 20 → 30
 ↑         ↑
operations at both ends
```

You can:

```text
add at front
add at rear

remove from front
remove from rear

inspect front
inspect rear
```

---

# 29. Deque Methods

Deque provides three major groups.

## Insert

```text
addFirst()
addLast()

offerFirst()
offerLast()
```

## Remove

```text
removeFirst()
removeLast()

pollFirst()
pollLast()
```

## Examine

```text
getFirst()
getLast()

peekFirst()
peekLast()
```

---

# 30. `addFirst()`

Adds an element at the front.

Suppose:

```text
20 → 30
```

Execute:

```java
d.addFirst(10);
```

Result:

```text
10 → 20 → 30
```

---

# 31. `addLast()`

Adds an element at the rear.

Suppose:

```text
10 → 20
```

Execute:

```java
d.addLast(30);
```

Result:

```text
10 → 20 → 30
```

---

# 32. `removeFirst()`

Removes the front element.

```text
10 → 20 → 30
```

```java
d.removeFirst();
```

returns:

```text
10
```

Remaining:

```text
20 → 30
```

---

# 33. `removeLast()`

Removes the rear element.

```text
10 → 20 → 30
```

```java
d.removeLast();
```

returns:

```text
30
```

Remaining:

```text
10 → 20
```

---

# 34. `peekFirst()`

Examines the first element without removing it.

```text
10 → 20 → 30
```

```java
d.peekFirst();
```

returns:

```text
10
```

Queue remains unchanged.

---

# 35. `peekLast()`

Examines the last element without removing it.

```text
10 → 20 → 30
```

```java
d.peekLast();
```

returns:

```text
30
```

Queue remains:

```text
10 → 20 → 30
```

---

# 36. Complete Deque Program

Since Deque is an interface, we need an implementation.

For this basic example:

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

        System.out.println("First = " + d.peekFirst());
        System.out.println("Last = " + d.peekLast());

        System.out.println("Removed First = " + d.removeFirst());
        System.out.println("Removed Last = " + d.removeLast());

        System.out.println(d);
    }
}
```

Flow:

```text
addFirst(20)
20

addLast(30)
20 → 30

addFirst(10)
10 → 20 → 30

peekFirst()
10

peekLast()
30

removeFirst()
10

removeLast()
30

Remaining:
20
```

---

# 37. Deque Can Behave Like a Queue

This is very important.

Suppose:

```text
Deque d = new ArrayDeque();
```

If you only do:

```text
addLast()
removeFirst()
```

you get traditional FIFO behavior.

Example:

```text
addLast(10)
addLast(20)
addLast(30)

Queue:
10 → 20 → 30

removeFirst()
→ 10
```

So:

```text
Deque can behave like a Queue.
```

---

# 38. Deque Can Also Behave Like a Stack

If you use one end consistently:

```text
addFirst()
removeFirst()
```

you get:

```text
LIFO
```

Example:

```text
addFirst(10)
addFirst(20)
addFirst(30)
```

Result:

```text
30 → 20 → 10
```

Then:

```text
removeFirst()
```

returns:

```text
30
```

That's:

```text
Last In, First Out
```

Therefore Deque can support both:

```text
FIFO
```

and:

```text
LIFO
```

This is one of the most powerful ideas behind Deque.

---

# 39. Queue vs PriorityQueue vs Deque

| Feature           | Queue                 | PriorityQueue               | Deque      |
| ----------------- | --------------------- | --------------------------- | ---------- |
| Main idea         | FIFO-style processing | Priority processing         | Both ends  |
| Type              | Interface             | Class                       | Interface  |
| Insert            | Queue end             | According to implementation | Front/rear |
| Remove            | Head                  | Highest-priority head       | Front/rear |
| Duplicates        | Usually allowed       | Allowed                     | Allowed    |
| Index             | No                    | No                          | No         |
| Can act as Stack? | Not normally          | No                          | Yes        |

---

# 40. Queue Method Pairs — Deep Revision

Remember these six methods as three pairs:

### Pair 1 — Insert

```text
add()
offer()
```

### Pair 2 — Remove

```text
remove()
poll()
```

### Pair 3 — Examine

```text
element()
peek()
```

The strict vs safe pattern is:

```text
add       → exception if insertion cannot happen
offer     → false if insertion cannot happen

remove    → exception if empty
poll      → null if empty

element   → exception if empty
peek      → null if empty
```

---

# 41. Deque Method Pairs

The same concept appears in Deque:

```text
Front insertion:
addFirst()
offerFirst()

Rear insertion:
addLast()
offerLast()
```

```text
Front removal:
removeFirst()
pollFirst()

Rear removal:
removeLast()
pollLast()
```

```text
Front examination:
getFirst()
peekFirst()

Rear examination:
getLast()
peekLast()
```

A useful pattern:

```text
add/remove/get
→ strict versions

offer/poll/peek
→ safer versions for failure/empty cases
```

---

# 42. Queue vs List — Important Concept

A List is primarily concerned with:

```text
position/index
```

For example:

```java
list.get(2);
```

A Queue is primarily concerned with:

```text
processing order
```

For example:

```java
q.poll();
```

Therefore:

```text
List
→ "Which element is at this position?"

Queue
→ "Which element should be processed next?"
```

This distinction is extremely useful.

---

# 43. Queue vs Set

Set:

```text
Unique elements
```

Queue:

```text
Processing order
```

Example:

```text
Set
10, 20, 30
```

focuses on uniqueness.

Queue:

```text
10 → 20 → 30
```

focuses on processing sequence.

---

# 44. Queue vs PriorityQueue

### Normal FIFO Queue

```text
Added:
30 → 10 → 20

Removed:
30 → 10 → 20
```

assuming a standard FIFO implementation.

### PriorityQueue

```text
Added:
30 → 10 → 20

Removed:
10 → 20 → 30
```

under natural numeric ordering.

Therefore:

```text
Queue
→ arrival/processing order

PriorityQueue
→ priority order
```

---

# 45. Queue vs Deque

Traditional Queue:

```text
        INSERT
           ↓
        REAR
          |
10 → 20 → 30
 ↑
FRONT
 |
REMOVE
```

Deque:

```text
        FRONT              REAR
          ↓                  ↓
       10 → 20 → 30
          ↑                  ↑
      operations         operations
```

So Deque provides more flexibility.

---

# 46. One Complete Mental Model

Think about three different real-world situations.

### Situation 1 — Normal line

```text
A → B → C
```

A must be served first.

Use:

```text
Queue
```

### Situation 2 — Emergency priority

```text
Normal patient
Critical patient
Less critical patient
```

Processing depends on priority.

Use:

```text
PriorityQueue
```

### Situation 3 — People can enter/leave from either side

```text
FRONT ← A B C → REAR
```

Operations can happen on both ends.

Use:

```text
Deque
```

---

# 47. Final Deep-Dive Comparison

| Concept         | Meaning              | Main behavior                       |
| --------------- | -------------------- | ----------------------------------- |
| Queue           | Processing queue     | FIFO-style                          |
| PriorityQueue   | Priority-based Queue | Highest priority first              |
| Deque           | Double-ended Queue   | Both ends                           |
| `add()`         | Insert               | Strict insertion                    |
| `offer()`       | Insert               | Returns failure instead of throwing |
| `remove()`      | Remove head          | Throws if empty                     |
| `poll()`        | Remove head          | Returns `null` if empty             |
| `element()`     | Examine head         | Throws if empty                     |
| `peek()`        | Examine head         | Returns `null` if empty             |
| `addFirst()`    | Deque insertion      | Front                               |
| `addLast()`     | Deque insertion      | Rear                                |
| `removeFirst()` | Deque removal        | Front                               |
| `removeLast()`  | Deque removal        | Rear                                |
| `peekFirst()`   | Examine              | Front                               |
| `peekLast()`    | Examine              | Rear                                |

---

# 🔥 Final Memory Map

```text
                         Collection
                              |
                            Queue
                 ┌────────────┴────────────┐
                 ↓                         ↓
          PriorityQueue                  Deque
                 ↓                         ↓
       Priority-based order        Double-ended operations
                                           |
                                  ┌────────┴────────┐
                                  ↓                 ↓
                              Front              Rear
```

### Remember these three sentences:

```text
Queue
→ Process elements in queue order.
```

```text
PriorityQueue
→ Process according to priority.
```

```text
Deque
→ Insert, remove, and examine from both ends.
```

### And the most important method map:

```text
QUEUE

Insert:
add() / offer()

Remove:
remove() / poll()

Examine:
element() / peek()
```

```text
DEQUE

Front:
addFirst() / removeFirst() / peekFirst()

Rear:
addLast() / removeLast() / peekLast()
```

**No Generics are used in any example in this lesson.**
