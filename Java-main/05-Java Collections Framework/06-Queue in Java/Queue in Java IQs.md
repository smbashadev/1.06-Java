# 6. Queue in Java — DOUBTKILLER

This section is designed specifically to eliminate the **confusions, traps, misconceptions, and interview/exam doubts** around:

1. **Queue Interface**
2. **PriorityQueue**
3. **Deque**

> **Important training rule:** No Generics. All programs use normal/raw Collection Framework syntax.

---

# PART 1 — Queue Interface: Doubts Killer

## Doubt 1: Is Queue a class or interface?

### Answer:

`Queue` is an **interface**.

```java
Queue q;
```

is valid as a reference declaration.

But:

```java
Queue q = new Queue();
```

is invalid.

Why?

Because interfaces cannot be directly instantiated.

You need a class that implements `Queue`.

For example:

```java
Queue q = new LinkedList();
```

---

# Doubt 2: Does Queue always mean FIFO?

**Not necessarily.**

FIFO is the **traditional/common Queue behavior**, but the `Queue` interface itself does not force every implementation to use simple FIFO ordering.

For example:

```text
Queue
├── LinkedList
├── PriorityQueue
└── Deque implementations
```

`LinkedList` can be used as a FIFO Queue.

`PriorityQueue` uses priority ordering.

So remember:

```text
Queue = Interface
```

not:

```text
Queue = automatically FIFO in every implementation
```

---

# Doubt 3: What exactly is FIFO?

FIFO means:

> **First In, First Out**

Suppose:

```java
q.add(10);
q.add(20);
q.add(30);
```

Conceptually:

```text
10 → 20 → 30
```

`10` entered first.

Therefore:

```java
q.remove();
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

# Doubt 4: Why can't I write this?

```java
Queue q = new Queue();
```

Because:

```text
Queue = interface
```

An interface describes behavior; it does not provide a directly instantiable object.

Use:

```java
Queue q = new LinkedList();
```

or an appropriate Queue implementation.

---

# Doubt 5: `add()` and `offer()` look the same. Why two methods?

Both are used to insert an element.

```java
q.add(10);
```

and:

```java
q.offer(10);
```

The important difference appears when insertion **cannot be performed**.

### `add()`

May throw an exception when the Queue refuses insertion.

### `offer()`

Returns:

```text
false
```

when insertion cannot be performed.

So:

```text
add()
→ exception on insertion failure

offer()
→ false on insertion failure
```

For ordinary unbounded implementations, you normally won't see a practical difference during successful insertion.

---

# Doubt 6: `remove()` and `poll()` both remove. What's the difference?

Suppose:

```text
10 → 20 → 30
```

Both:

```java
q.remove();
```

and:

```java
q.poll();
```

remove `10`.

The difference appears when Queue is empty.

### `remove()`

```text
Empty Queue
     ↓
remove()
     ↓
NoSuchElementException
```

### `poll()`

```text
Empty Queue
     ↓
poll()
     ↓
null
```

### Memory trick

```text
remove = strict
poll   = safe
```

---

# Doubt 7: Does `peek()` remove the element?

**No.**

This is a very common mistake.

Suppose:

```text
10 → 20 → 30
```

Execute:

```java
System.out.println(q.peek());
```

Output:

```text
10
```

But Queue remains:

```text
10 → 20 → 30
```

`peek()` only **looks at the head**.

---

# Doubt 8: Does `poll()` only look at the element?

No.

`poll()` **removes** the head.

```text
Before:
10 → 20 → 30

poll()

After:
20 → 30
```

Therefore:

```text
peek() → look
poll() → remove
```

---

# Doubt 9: `element()` vs `peek()`?

Both examine the head without removing it.

Difference:

### `element()`

Empty Queue:

```text
NoSuchElementException
```

### `peek()`

Empty Queue:

```text
null
```

Therefore:

```text
element() → exception if empty
peek()    → null if empty
```

---

# Doubt 10: What are the six important Queue methods?

Remember them in pairs.

```text
INSERT
add()
offer()
```

```text
REMOVE
remove()
poll()
```

```text
EXAMINE
element()
peek()
```

The complete memory table:

| Operation | Exception-oriented | Special-value-oriented |
| --------- | ------------------ | ---------------------- |
| Insert    | `add()`            | `offer()`              |
| Remove    | `remove()`         | `poll()`               |
| Examine   | `element()`        | `peek()`               |

---

# Doubt 11: What is the "head" of Queue?

The **head** is the element that is next to be retrieved/removed according to the Queue's ordering policy.

For a normal FIFO Queue:

```text
HEAD
 ↓
10 → 20 → 30
```

Therefore:

```java
q.peek();
```

returns:

```text
10
```

---

# Doubt 12: Is the first element always the smallest element?

**No.**

This is an extremely important distinction.

For a normal FIFO Queue:

```java
q.add(50);
q.add(10);
q.add(30);
```

the insertion order is:

```text
50 → 10 → 30
```

The head is:

```text
50
```

because it arrived first.

A normal Queue does not automatically sort numbers.

---

# PART 2 — PriorityQueue: Doubts Killer

# Doubt 13: What is PriorityQueue?

`PriorityQueue` is a **class** that implements the `Queue` interface.

Conceptually:

```text
Collection
    ↓
Queue
    ↓
PriorityQueue
```

Its important characteristic is:

> Elements are processed according to priority.

For numbers using natural ordering, the smallest element normally has the highest priority.

---

# Doubt 14: Is PriorityQueue FIFO?

**No.**

This is probably the biggest PriorityQueue misconception.

Suppose:

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

But:

```java
q.poll();
```

returns:

```text
10
```

Then:

```text
20
```

Then:

```text
30
```

So:

```text
Queue
→ commonly FIFO

PriorityQueue
→ priority-based
```

---

# Doubt 15: Why does 10 come before 30?

Because for integers under natural ordering:

```text
10 < 20 < 30
```

The smallest value has the highest priority.

Therefore:

```text
poll()
→ 10
```

---

# Doubt 16: Is PriorityQueue a sorted collection?

**Do not think of it as a sorted List.**

This distinction is important.

PriorityQueue guarantees that its **head** is the element with the highest priority according to its ordering.

Don't rely on:

```java
System.out.println(q);
```

to demonstrate a completely sorted sequence.

Instead, demonstrate priority by repeatedly calling:

```java
q.poll();
```

For example:

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
```

---

# Doubt 17: Does PriorityQueue remove the largest number first?

By default for natural ordering of numbers:

**No.**

It removes the smallest first.

```text
10 → 20 → 30
```

So:

```text
10
```

has the highest priority.

If you want a different ordering, you can later learn **Comparator**, which is specifically covered in your roadmap under Topic 11.

---

# Doubt 18: Does PriorityQueue allow duplicates?

**Yes.**

```java
PriorityQueue q = new PriorityQueue();

q.add(10);
q.add(10);
q.add(20);
```

Duplicates are allowed.

Unlike:

```text
Set → duplicates not allowed
```

Queue implementations generally can contain duplicates.

---

# Doubt 19: Does PriorityQueue allow null?

**No.**

```java
q.add(null);
```

results in a `NullPointerException`.

The Queue needs to be able to determine ordering/priority, and `null` cannot participate in the natural ordering.

---

# Doubt 20: Does PriorityQueue preserve insertion order?

**No.**

Suppose:

```text
Inserted:
40
10
30
20
```

Priority processing is based on priority, not insertion order.

So don't think:

```text
40 → 10 → 30 → 20
```

will necessarily be the `poll()` order.

---

# Doubt 21: Can PriorityQueue contain duplicate priorities?

Yes.

For example:

```text
10
10
20
20
30
```

is valid.

The Queue can contain multiple equal-priority elements.

---

# PART 3 — Deque: Doubts Killer

# Doubt 22: What does Deque mean?

Deque means:

> **Double Ended Queue**

It is pronounced approximately:

> "deck"

It is an interface.

```text
Collection
    ↓
Queue
    ↓
Deque
```

---

# Doubt 23: Why do we need Deque if Queue already exists?

A normal Queue mainly focuses on:

```text
insert → one end
remove → other end
```

Deque gives you both ends.

```text
        FRONT             REAR
          ↓                 ↓
       10 → 20 → 30
          ↑                 ↑
       operate           operate
       here              here
```

You can:

```text
insert at front
insert at rear

remove from front
remove from rear

check front
check rear
```

---

# Doubt 24: What does `addFirst()` do?

It inserts at the front.

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

# Doubt 25: What does `addLast()` do?

It inserts at the rear.

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

# Doubt 26: `removeFirst()` vs `removeLast()`?

Suppose:

```text
10 → 20 → 30
```

### `removeFirst()`

Removes:

```text
10
```

Remaining:

```text
20 → 30
```

### `removeLast()`

Removes:

```text
30
```

Remaining:

```text
10 → 20
```

---

# Doubt 27: `peekFirst()` vs `peekLast()`?

Suppose:

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

Neither removes anything.

---

# Doubt 28: What are the major Deque methods?

### Insert

```text
addFirst()
addLast()

offerFirst()
offerLast()
```

### Remove

```text
removeFirst()
removeLast()

pollFirst()
pollLast()
```

### Examine

```text
getFirst()
getLast()

peekFirst()
peekLast()
```

---

# Doubt 29: Why do we have both `addFirst()` and `offerFirst()`?

Same basic pattern as Queue.

```text
addFirst()
↔
offerFirst()
```

Both attempt insertion at the front.

The important distinction is how insertion failure is reported.

Likewise:

```text
addLast()
↔
offerLast()
```

---

# Doubt 30: Why do we have `removeFirst()` and `pollFirst()`?

Because they differ when the Deque is empty.

```text
removeFirst()
→ exception if empty
```

while:

```text
pollFirst()
→ null if empty
```

Similarly:

```text
removeLast()
→ exception if empty

pollLast()
→ null if empty
```

---

# Doubt 31: Can Deque behave like a normal Queue?

**Yes.**

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

That's:

```text
FIFO
```

---

# Doubt 32: Can Deque behave like Stack?

**Yes.**

Use the same end for insertion and removal.

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

That's:

```text
LIFO
Last In, First Out
```

So:

```text
Deque
├── Queue behavior → FIFO
└── Stack behavior → LIFO
```

---

# Doubt 33: Is Deque the same as Stack?

**No.**

Deque is an interface.

`Stack` is a class.

They are different concepts.

However, a Deque can provide Stack-like LIFO behavior.

Modern Java code often prefers a `Deque` implementation such as `ArrayDeque` for stack operations.

For your roadmap, remember:

```text
Deque ≠ Stack
```

but:

```text
Deque can behave like Stack
```

---

# Doubt 34: Is Deque the same as Queue?

**No.**

Deque is a specialized Queue interface that supports operations at **both ends**.

Think:

```text
Queue
→ restricted end operations

Deque
→ both ends available
```

---

# Doubt 35: Can ArrayDeque contain null?

**No.**

This is important.

```java
Deque d = new ArrayDeque();

d.add(null);
```

is not allowed.

`ArrayDeque` does not permit `null`.

---

# PART 4 — BIGGEST CONFUSIONS

## Queue vs PriorityQueue

### Queue

```text
10 → 20 → 30
```

Normal FIFO idea:

```text
10 comes out first
```

### PriorityQueue

```text
30
10
20
```

Priority determines processing:

```text
10
20
30
```

Therefore:

```text
Queue
→ arrival/order policy

PriorityQueue
→ priority policy
```

---

# Queue vs Deque

### Queue

```text
       Insert
          ↓
10 → 20 → 30
↑
Remove
```

### Deque

```text
      FRONT       REAR
        ↓           ↓
     10 → 20 → 30
        ↑           ↑
     operations at both ends
```

---

# PriorityQueue vs TreeSet

This is another common confusion.

Both can appear to process numbers in ascending order.

But they have different purposes.

### PriorityQueue

Allows duplicates:

```text
10
10
20
```

### TreeSet

Does not allow duplicates:

```text
10
20
```

So:

```text
PriorityQueue
→ priority processing

TreeSet
→ unique + sorted set
```

Do not confuse them.

---

# Queue vs Set

### Queue

```text
Duplicates allowed
```

Example:

```text
10
10
20
```

### Set

```text
Duplicates not allowed
```

Example:

```text
10
20
```

---

# The Empty-Queue Trap

This is worth memorizing.

```text
              EMPTY
                |
       ┌────────┼────────┐
       ↓        ↓        ↓
     remove   element    add
       ↓        ↓
   exception  exception
```

Safer methods:

```text
poll()
→ null

peek()
→ null
```

And insertion:

```text
offer()
→ false if insertion fails
```

---

# 🔥 The Golden Six

If someone asks you:

> "What are the six important Queue methods?"

Immediately answer:

```text
add()
offer()

remove()
poll()

element()
peek()
```

And explain:

```text
add / offer
→ insertion

remove / poll
→ removal

element / peek
→ examination
```

---

# 🔥 The Golden Deque Methods

Remember the three categories.

### Front

```text
addFirst()
removeFirst()
peekFirst()
```

### Rear

```text
addLast()
removeLast()
peekLast()
```

And safer insertion/removal versions:

```text
offerFirst()
offerLast()

pollFirst()
pollLast()
```

---

# 🚨 Interview/Exam Traps

### Trap 1

**"Queue is a class."**

❌ Wrong.

```text
Queue = interface
```

---

### Trap 2

**"PriorityQueue follows FIFO."**

❌ Wrong.

```text
PriorityQueue = priority-based
```

---

### Trap 3

**"`peek()` removes the head."**

❌ Wrong.

```text
peek() = examine only
```

---

### Trap 4

**"`poll()` only examines the head."**

❌ Wrong.

```text
poll() = remove + return
```

---

### Trap 5

**"`remove()` returns null if Queue is empty."**

❌ Wrong.

```text
remove() → NoSuchElementException
```

---

### Trap 6

**"`poll()` throws an exception when Queue is empty."**

❌ Wrong.

```text
poll() → null
```

---

### Trap 7

**"`element()` returns null if Queue is empty."**

❌ Wrong.

```text
element() → NoSuchElementException
```

---

### Trap 8

**"`peek()` throws an exception when Queue is empty."**

❌ Wrong.

```text
peek() → null
```

---

### Trap 9

**"Deque only removes from the front."**

❌ Wrong.

Deque supports:

```text
removeFirst()
removeLast()
```

---

### Trap 10

**"Deque cannot act like Stack."**

❌ Wrong.

It can provide LIFO behavior.

---

### Trap 11

**"PriorityQueue cannot contain duplicates."**

❌ Wrong.

Duplicates are allowed.

---

### Trap 12

**"PriorityQueue allows null."**

❌ Wrong.

`PriorityQueue` does not permit `null`.

---

# 🧠 FINAL DOUBTKILLER MAP

```text
                     COLLECTION
                         |
                       QUEUE
                  _______|_______
                 |               |
                 ↓               ↓
          PriorityQueue         Deque
                 |               |
             PRIORITY        BOTH ENDS
                                 |
                       ┌─────────┴─────────┐
                       ↓                   ↓
                    Queue              Stack-like
                   behavior             behavior
                     FIFO                 LIFO
```

### Queue

```text
10 → 20 → 30

remove()
   ↓
10
```

### PriorityQueue

```text
Inserted:
30 → 10 → 20

poll():
10
20
30
```

### Deque

```text
          FRONT       REAR
            ↓           ↓
         10 → 20 → 30
            ↑           ↑
         both ends available
```

---

# ⭐ One-Minute DOUBTKILLER Revision

```text
Queue
→ Interface
→ commonly FIFO
```

```text
PriorityQueue
→ Class
→ priority-based
→ duplicates allowed
→ null not allowed
```

```text
Deque
→ Interface
→ Double Ended Queue
→ operations at front and rear
→ can behave like Queue
→ can behave like Stack
```

```text
add()       → insert
offer()     → insert

remove()    → remove / exception if empty
poll()      → remove / null if empty

element()   → examine / exception if empty
peek()      → examine / null if empty
```

And the **most important three words**:

> **Queue = FIFO**
> **PriorityQueue = PRIORITY**
> **Deque = BOTH ENDS**

**No Generics are used anywhere in this DOUBTKILLER topic.**
