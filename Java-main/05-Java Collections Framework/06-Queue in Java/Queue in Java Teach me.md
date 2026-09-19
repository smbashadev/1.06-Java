# 6. Queue in Java — TEACHME

I’ll teach this as if you are learning **Queue for the first time**, step by step.

> **Your training rule:** No Generics. Every Java program below uses the traditional/raw collection syntax. Generics will be taught separately in Topic 14.

---

# PART 1 — First Understand the Problem

Imagine you go to a bank.

There are three people:

```text
Person A
Person B
Person C
```

They arrive in this order:

```text
A → B → C
```

Who should normally be served first?

```text
A
```

Then:

```text
B
```

Then:

```text
C
```

This is called:

# FIFO

**F**irst **I**n
**F**irst **O**ut

```text
A → B → C

First entered = A
First removed = A
```

This is the basic idea behind a **Queue**.

---

# PART 2 — What is Queue?

A **Queue** is an interface in Java's Collection Framework.

It is present in:

```java
java.util
```

Its purpose is to hold elements that are waiting to be processed.

Simple definition:

> **Queue is a Collection Framework interface used to store elements for processing according to a particular ordering policy.**

For a traditional FIFO Queue:

```text
Insert → Rear

Front → Remove
```

Visualize it like this:

```text
              Queue
                |
                ↓
        10 → 20 → 30
        ↑           ↑
      Front        Rear
```

If we remove an element:

```text
10
```

is removed first.

Remaining:

```text
20 → 30
```

---

# PART 3 — Why Do We Need Queue?

Let's say a printer receives documents:

```text
Document A
Document B
Document C
```

A arrives first.

So the printer should normally process:

```text
A
B
C
```

We can represent this as:

```text
A → B → C
```

This is exactly the kind of situation for which Queue is useful.

### Real-world examples

Queue concepts appear in:

* Printer jobs
* Customer service systems
* Task processing
* Request processing
* CPU scheduling
* Message processing
* Network requests

The general pattern is:

```text
New data
   ↓
Queue
   ↓
Wait
   ↓
Process
```

---

# PART 4 — Is Queue a Class?

No.

This is extremely important.

`Queue` is an **interface**.

Therefore this is invalid:

```java
Queue q = new Queue();
```

❌ You cannot directly create an object of an interface.

Instead, use a class that implements Queue.

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

# PART 5 — Understand the Hierarchy

The important part of the Collection Framework is:

```text
                 Collection
                     |
                   Queue
                ┌────┴────┐
                ↓         ↓
        PriorityQueue    Deque
                           |
                    ┌──────┴──────┐
                    ↓             ↓
                ArrayDeque     LinkedList
```

For **Topic 6**, we learn:

```text
Queue
├── Queue Interface
├── PriorityQueue
└── Deque
```

Your next topic, **Topic 7**, goes deeper into:

```text
Deque
├── ArrayDeque
└── LinkedList as Deque
```

So don't mix Topic 6 and Topic 7.

---

# PART 6 — First Queue Program

Let's write the simplest possible Queue program.

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

Output will conceptually be:

```text
[10, 20, 30]
```

Think of it as:

```text
Front
 ↓
10 → 20 → 30
          ↑
         Rear
```

---

# PART 7 — Queue Methods

There are six important Queue methods you must learn.

```text
add()
offer()

remove()
poll()

element()
peek()
```

Don't memorize them randomly.

They come in **three pairs**.

```text
INSERT
add()     ↔ offer()

REMOVE
remove()  ↔ poll()

CHECK
element() ↔ peek()
```

Let's understand each pair.

---

# PART 8 — `add()`

`add()` inserts an element into the Queue.

```java
q.add(10);
```

Then:

```java
q.add(20);
```

Then:

```java
q.add(30);
```

Queue:

```text
10 → 20 → 30
```

Example:

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

---

# PART 9 — `offer()`

`offer()` also inserts an element.

```java
q.offer(10);
q.offer(20);
q.offer(30);
```

Queue:

```text
10 → 20 → 30
```

So you may ask:

> Why do we have both `add()` and `offer()`?

Because they behave differently when insertion cannot be performed.

The important memory rule is:

```text
add()
→ may throw exception

offer()
→ returns false
```

For normal unbounded Queue implementations, both usually succeed.

---

# PART 10 — `add()` vs `offer()`

| `add()`                                  | `offer()`                            |
| ---------------------------------------- | ------------------------------------ |
| Inserts element                          | Inserts element                      |
| Returns boolean                          | Returns boolean                      |
| May throw exception when insertion fails | Returns `false` when insertion fails |

For your beginner notes:

```text
add   = insert
offer = insert
```

The failure behavior is the important difference.

---

# PART 11 — `remove()`

Now let's remove an element.

Suppose:

```text
10 → 20 → 30
```

Execute:

```java
q.remove();
```

The first element is removed:

```text
10
```

Remaining:

```text
20 → 30
```

Example:

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

        q.remove();

        System.out.println(q);
    }
}
```

Conceptually:

```text
Before:
10 → 20 → 30

remove()

After:
20 → 30
```

---

# PART 12 — What if Queue is Empty?

Suppose:

```java
Queue q = new LinkedList();
```

There is nothing inside.

Now:

```java
q.remove();
```

There is nothing to remove.

`remove()` throws:

```text
NoSuchElementException
```

Remember:

```text
remove()
→ empty Queue
→ exception
```

---

# PART 13 — `poll()`

`poll()` also removes the head element.

```java
q.poll();
```

Suppose:

```text
10 → 20 → 30
```

Then:

```java
q.poll();
```

removes:

```text
10
```

Remaining:

```text
20 → 30
```

---

# PART 14 — `remove()` vs `poll()`

Here's the important difference.

If Queue is empty:

```text
remove()
→ exception

poll()
→ null
```

Therefore:

```text
remove = strict
poll   = returns null when empty
```

This pair is very important for exams and interviews.

---

# PART 15 — `element()`

Now suppose we don't want to remove the first element.

We only want to **look at it**.

Use:

```java
q.element();
```

Suppose:

```text
10 → 20 → 30
```

Then:

```java
q.element();
```

returns:

```text
10
```

But the Queue remains:

```text
10 → 20 → 30
```

Nothing was removed.

---

# PART 16 — `peek()`

`peek()` also looks at the first element.

```java
q.peek();
```

Suppose:

```text
10 → 20 → 30
```

Then:

```java
q.peek();
```

returns:

```text
10
```

Queue remains:

```text
10 → 20 → 30
```

---

# PART 17 — `element()` vs `peek()`

Again, the difference is what happens when the Queue is empty.

```text
element()
→ empty Queue → exception

peek()
→ empty Queue → null
```

So remember:

```text
element = strict
peek    = null if empty
```

---

# PART 18 — The Complete Queue Method Story

Now connect everything.

Imagine:

```text
10 → 20 → 30
```

### Want to insert?

```java
add()
offer()
```

### Want to remove?

```java
remove()
poll()
```

### Want to just look?

```java
element()
peek()
```

Complete picture:

```text
                    QUEUE
                      |
          ┌───────────┼───────────┐
          ↓           ↓           ↓
       INSERT       REMOVE      CHECK
          |           |           |
     add/offer   remove/poll  element/peek
```

---

# PART 19 — First Important Program

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

        System.out.println("Queue = " + q);
    }
}
```

Think through it.

Initially:

```text
10 → 20 → 30
```

`peek()`:

```text
10
```

Queue still:

```text
10 → 20 → 30
```

`poll()`:

```text
10 removed
```

Queue becomes:

```text
20 → 30
```

---

# PART 20 — Now Understand PriorityQueue

So far we learned a normal Queue.

Now suppose we don't want to process elements simply according to arrival.

Suppose we have:

```text
30
10
20
```

But we want the smallest number to be processed first.

We need:

# PriorityQueue

`PriorityQueue` is a class that implements `Queue`.

```text
Collection
    ↓
Queue
    ↓
PriorityQueue
```

---

# PART 21 — Why PriorityQueue?

Imagine three tasks:

```text
Task A → priority 30
Task B → priority 10
Task C → priority 20
```

A normal FIFO system might think:

```text
A → B → C
```

But a PriorityQueue thinks:

```text
10 → 20 → 30
```

because the smallest number has the highest priority under natural ordering.

---

# PART 22 — Basic PriorityQueue Program

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

Why?

Because the PriorityQueue processes the elements according to their priority.

For numbers:

```text
10 < 20 < 30
```

So:

```text
10
```

gets priority first.

---

# PART 23 — Very Important: Don't Think PriorityQueue Is a Sorted List

This causes lots of confusion.

Don't think:

> PriorityQueue means every element is always displayed in sorted order.

That's not the correct idea.

The important behavior is:

```java
q.peek();
```

gives the highest-priority element.

And:

```java
q.poll();
```

removes the highest-priority element.

So concentrate on:

```text
HEAD
 ↓
highest priority
```

---

# PART 24 — PriorityQueue Example

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        PriorityQueue q = new PriorityQueue();

        q.add(50);
        q.add(10);
        q.add(30);
        q.add(20);

        while (!q.isEmpty())
        {
            System.out.println(q.poll());
        }
    }
}
```

Processing order:

```text
10
20
30
50
```

---

# PART 25 — Does PriorityQueue Allow Duplicates?

Yes.

Example:

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

Remember:

```text
Set
→ no duplicates

Queue
→ duplicates can exist
```

---

# PART 26 — Does PriorityQueue Allow `null`?

No.

A PriorityQueue does not allow `null` elements.

For example:

```java
q.add(null);
```

causes:

```text
NullPointerException
```

The reason is that the PriorityQueue needs to determine ordering/priority.

---

# PART 27 — Now Understand Deque

The word **Deque** comes from:

> Double Ended Queue

It is pronounced:

> "deck"

Deque is an interface.

```text
Collection
    ↓
Queue
    ↓
Deque
```

Its special feature is:

> **Both ends can be used.**

---

# PART 28 — Normal Queue vs Deque

Normal Queue:

```text
        Insert
          ↓
10 → 20 → 30
↑
Remove
```

Deque:

```text
        FRONT
          ↓
       10 → 20 → 30
          ↑        ↑
       FRONT      REAR
```

With Deque, you can operate from:

```text
Front
```

and:

```text
Rear
```

---

# PART 29 — Why Do We Need Deque?

Suppose you have:

```text
10 → 20 → 30
```

A normal Queue mainly gives you:

```text
remove from front
```

But a Deque lets you say:

```text
Remove from front
```

or:

```text
Remove from rear
```

Similarly, you can insert at either end.

Therefore:

```text
Deque
=
Queue with operations at both ends
```

---

# PART 30 — Deque Insertion Methods

There are four important insertion methods:

```text
addFirst()
addLast()

offerFirst()
offerLast()
```

### `addFirst()`

Adds at front.

```text
20 → 30
```

After:

```java
d.addFirst(10);
```

we get:

```text
10 → 20 → 30
```

### `addLast()`

Adds at rear.

```text
10 → 20
```

After:

```java
d.addLast(30);
```

we get:

```text
10 → 20 → 30
```

---

# PART 31 — Deque Removal Methods

There are four important removal methods:

```text
removeFirst()
removeLast()

pollFirst()
pollLast()
```

### `removeFirst()`

```text
10 → 20 → 30
```

removes:

```text
10
```

### `removeLast()`

```text
10 → 20 → 30
```

removes:

```text
30
```

---

# PART 32 — Deque Examination Methods

To look without removing:

```text
getFirst()
getLast()

peekFirst()
peekLast()
```

Example:

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

while:

```java
d.peekLast();
```

returns:

```text
30
```

Nothing is removed.

---

# PART 33 — Complete Deque Program

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

Let's trace it.

### Step 1

```java
d.addFirst(20);
```

```text
20
```

### Step 2

```java
d.addLast(30);
```

```text
20 → 30
```

### Step 3

```java
d.addFirst(10);
```

```text
10 → 20 → 30
```

### Step 4

```java
d.peekFirst();
```

returns:

```text
10
```

### Step 5

```java
d.peekLast();
```

returns:

```text
30
```

### Step 6

```java
d.removeFirst();
```

removes:

```text
10
```

### Step 7

```java
d.removeLast();
```

removes:

```text
30
```

Remaining:

```text
20
```

---

# PART 34 — Deque Can Behave Like Queue

This is an excellent concept.

Suppose:

```java
Deque d = new ArrayDeque();
```

Use:

```text
addLast()
```

for insertion.

Use:

```text
removeFirst()
```

for removal.

Example:

```java
d.addLast(10);
d.addLast(20);
d.addLast(30);
```

Gives:

```text
10 → 20 → 30
```

Then:

```java
d.removeFirst();
```

returns:

```text
10
```

This is FIFO.

Therefore:

```text
Deque can behave like Queue.
```

---

# PART 35 — Deque Can Behave Like Stack

Now use the same end for both operations.

```text
addFirst()
removeFirst()
```

Example:

```java
d.addFirst(10);
d.addFirst(20);
d.addFirst(30);
```

Conceptually:

```text
30 → 20 → 10
```

Now:

```java
d.removeFirst();
```

returns:

```text
30
```

The last inserted element came out first.

That's:

# LIFO

**Last In, First Out**

Therefore:

```text
Deque can behave like Queue
        AND
Deque can behave like Stack
```

This is one of the most important concepts to remember.

---

# PART 36 — Queue vs PriorityQueue vs Deque

Let's put everything together.

| Concept       | Main Idea                 |
| ------------- | ------------------------- |
| Queue         | FIFO-style processing     |
| PriorityQueue | Priority-based processing |
| Deque         | Both ends                 |

Think:

```text
Queue
→ Who came first?

PriorityQueue
→ Who has priority?

Deque
→ Which end do I want to use?
```

---

# PART 37 — The Three Concepts as Real-Life Examples

### Queue

People waiting at a normal ticket counter:

```text
A → B → C
```

A is served first.

```text
FIFO
```

---

### PriorityQueue

Hospital emergency processing:

```text
Critical
High
Normal
```

Priority determines who gets processed first.

```text
Priority
```

---

### Deque

A line where you can add/remove from either side:

```text
FRONT ← A B C → REAR
```

Both ends are available.

```text
Double-ended
```

---

# PART 38 — The Most Important Method Pairs

You should be able to recall these without thinking.

## Queue

```text
Insertion
add()
offer()
```

```text
Removal
remove()
poll()
```

```text
Examination
element()
peek()
```

Remember:

```text
add       → insert
offer     → insert

remove    → remove
poll      → remove

element   → look
peek      → look
```

---

# PART 39 — Strict vs Safe Methods

This pattern makes the methods much easier to remember.

### Strict methods

```text
add()
remove()
element()
```

They can throw exceptions when the operation cannot be performed.

### Safer alternatives

```text
offer()
poll()
peek()
```

They use special return values instead:

```text
offer() → false
poll()  → null
peek()  → null
```

So:

```text
add    ↔ offer
remove ↔ poll
element ↔ peek
```

---

# PART 40 — Deque Method Map

Now remember Deque in the same way.

```text
                 DEQUE
                   |
          ┌────────┴────────┐
          ↓                 ↓
        FRONT              REAR
          |                 |
       addFirst()        addLast()
       removeFirst()     removeLast()
       peekFirst()       peekLast()
```

And the alternative insertion/removal methods:

```text
offerFirst()
offerLast()

pollFirst()
pollLast()
```

---

# PART 41 — Common Beginner Mistakes

## Mistake 1

Thinking Queue is a class:

```java
Queue q = new Queue();
```

❌ Wrong.

Queue is an interface.

---

## Mistake 2

Thinking Queue always means `LinkedList`.

Wrong.

`LinkedList` is one possible implementation.

---

## Mistake 3

Thinking PriorityQueue follows insertion order.

Example:

```text
Inserted:
30 → 10 → 20
```

PriorityQueue can process:

```text
10 → 20 → 30
```

---

## Mistake 4

Thinking `peek()` removes the element.

Wrong.

```text
peek()
→ only looks
```

---

## Mistake 5

Thinking `poll()` only looks.

Wrong.

```text
poll()
→ removes
```

---

## Mistake 6

Confusing `remove()` and `poll()`.

Remember:

```text
remove → exception when empty
poll   → null when empty
```

---

## Mistake 7

Thinking Deque is only a Queue.

Deque is more powerful.

It can operate from:

```text
Front
```

and:

```text
Rear
```

It can even be used to implement Stack-like behavior.

---

# PART 42 — Final Mental Picture

If you remember only this, you can rebuild the entire topic:

```text
                     QUEUE
                       |
          ┌────────────┼────────────┐
          ↓            ↓            ↓
       Queue       PriorityQueue    Deque
     Interface       Class        Interface
          |            |             |
        FIFO       Priority       Both Ends
```

Queue:

```text
10 → 20 → 30
↑
remove
```

PriorityQueue:

```text
30, 10, 20

priority processing:
10 → 20 → 30
```

Deque:

```text
10 → 20 → 30
↑           ↑
Front      Rear
```

---

# ⭐ TEACHME FINAL REVISION

### Question 1: What is Queue?

> Queue is an interface used to hold elements for processing according to an ordering policy; traditional queues follow FIFO.

### Question 2: What does FIFO mean?

> First In, First Out.

### Question 3: Is Queue a class?

> No. Queue is an interface.

### Question 4: What is PriorityQueue?

> A Queue implementation that processes elements according to priority.

### Question 5: For numbers, what normally gets priority first?

> The smallest number, under natural ordering.

### Question 6: What is Deque?

> Double Ended Queue — it allows insertion, removal, and examination at both ends.

### Question 7: Can Deque behave like Queue?

> Yes.

### Question 8: Can Deque behave like Stack?

> Yes.

### Question 9: What does `peek()` do?

> Returns the head without removing it; returns `null` if the Queue is empty.

### Question 10: What does `poll()` do?

> Removes and returns the head; returns `null` if the Queue is empty.

### Question 11: Difference between `remove()` and `poll()`?

```text
remove() → exception if empty
poll()   → null if empty
```

### Question 12: Difference between `element()` and `peek()`?

```text
element() → exception if empty
peek()    → null if empty
```

---

# 🔥 One-Line Memory Formula

```text
QUEUE       = FIFO
```

```text
PRIORITYQUEUE = PRIORITY
```

```text
DEQUE       = BOTH ENDS
```

And:

```text
add / offer       → INSERT
remove / poll     → REMOVE
element / peek    → CHECK
```

**No Generics are used anywhere in this TEACHME lesson.**
