# 6. Queue in Java — ONEPAGE

**Training rule:** No Generics. All examples use normal/raw collection syntax. Generics will be covered separately in Topic 14.

---

# 1. Queue Interface

## Definition

`Queue` is an interface in `java.util` used to represent a collection designed primarily for **holding elements before processing**.

The traditional Queue principle is:

> **FIFO — First In, First Out**

Example:

```text
Person 1 → Person 2 → Person 3
   ↓          ↓          ↓
First       Second      Third
```

Person 1 is processed first.

Basic hierarchy:

```text
             Collection
                  |
                Queue
                  |
        ┌─────────┴─────────┐
        ↓                   ↓
 PriorityQueue           Deque
                            |
                  ┌─────────┴─────────┐
                  ↓                   ↓
             ArrayDeque          LinkedList
```

---

# 2. Queue is an Interface

You cannot normally create a Queue directly:

```java
Queue q = new Queue();
```

❌ Invalid.

You need a class that implements Queue, for example:

```java
Queue q = new PriorityQueue();
```

or:

```java
Queue q = new ArrayDeque();
```

---

# 3. Important Queue Methods

Queue has several important methods:

| Method      | Meaning                       |
| ----------- | ----------------------------- |
| `add()`     | Inserts element               |
| `offer()`   | Inserts element               |
| `remove()`  | Removes and returns head      |
| `poll()`    | Removes and returns head      |
| `element()` | Returns head without removing |
| `peek()`    | Returns head without removing |

The most important pairs are:

```text
add()     ↔ offer()
remove()  ↔ poll()
element() ↔ peek()
```

---

# 4. `add()` vs `offer()`

Both are used to insert elements.

```java
q.add(10);
```

and:

```java
q.offer(20);
```

For an ordinary queue with available capacity, both can successfully insert.

The difference becomes important when insertion cannot be performed:

```text
add()
→ may throw an exception

offer()
→ returns false
```

For beginner-level Queue programs, remember:

```text
add()   → insert
offer() → insert
```

---

# 5. `remove()` vs `poll()`

Both remove the head element.

Suppose:

```text
10 → 20 → 30
```

Then:

```java
q.remove();
```

removes:

```text
10
```

Likewise:

```java
q.poll();
```

also removes:

```text
10
```

Important difference when the Queue is empty:

```text
remove()
→ throws exception

poll()
→ returns null
```

Memory:

```text
remove → strict
poll   → safer empty-queue behavior
```

---

# 6. `element()` vs `peek()`

Both examine the head **without removing it**.

Suppose:

```text
10 → 20 → 30
```

```java
q.element();
```

returns:

```text
10
```

but keeps the Queue:

```text
10 → 20 → 30
```

Likewise:

```java
q.peek();
```

returns `10` without removing it.

When the Queue is empty:

```text
element()
→ throws exception

peek()
→ returns null
```

---

# 7. Queue Basic Program

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

        System.out.println(q.peek());

        System.out.println(q.poll());

        System.out.println(q);
    }
}
```

Conceptually:

```text
Initially:
10 → 20 → 30

peek()
→ 10
Queue remains:
10 → 20 → 30

poll()
→ removes 10

Remaining:
20 → 30
```

---

# 8. PriorityQueue

`PriorityQueue` is a class that implements `Queue`.

```text
Queue
  |
PriorityQueue
```

Its important characteristic is:

> Elements are processed according to **priority**, rather than simply following insertion order.

For numbers, the natural ordering normally means the smallest element has the highest priority.

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

        System.out.println(q);

        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
    }
}
```

The elements are removed in priority order:

```text
10
20
30
```

---

# 9. Very Important PriorityQueue Doubt

Do **not** think:

> PriorityQueue always displays its entire contents in sorted order.

That is not the correct concept.

The important guarantee is about the **head / removal according to priority**.

For example:

```java
System.out.println(q.peek());
```

gives the highest-priority element.

And:

```java
q.poll();
```

removes the highest-priority element.

So remember:

```text
PriorityQueue
     ↓
Priority determines head/removal
```

---

# 10. Queue vs PriorityQueue

### Normal FIFO Queue

```text
Insert:
10 → 20 → 30

Remove:
10 → 20 → 30
```

### PriorityQueue

```text
Insert:
30 → 10 → 20

Priority:
10 → 20 → 30
```

So:

```text
Queue
→ generally FIFO

PriorityQueue
→ priority-based processing
```

---

# 11. Deque

`Deque` means:

> **Double Ended Queue**

It allows insertion and removal from **both ends**.

```text
       FRONT
         ↓
    10  20  30
         ↑
        REAR
```

You can work from:

```text
front
  ↕
rear
```

Unlike a traditional FIFO Queue, Deque gives you much more flexibility.

---

# 12. Deque Important Methods

| Operation | Front           | Rear           |
| --------- | --------------- | -------------- |
| Insert    | `addFirst()`    | `addLast()`    |
| Insert    | `offerFirst()`  | `offerLast()`  |
| Remove    | `removeFirst()` | `removeLast()` |
| Remove    | `pollFirst()`   | `pollLast()`   |
| Examine   | `getFirst()`    | `getLast()`    |
| Examine   | `peekFirst()`   | `peekLast()`   |

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

        System.out.println(d.removeFirst());
        System.out.println(d.removeLast());

        System.out.println(d);
    }
}
```

Conceptually:

```text
addFirst(20)
20

addLast(30)
20 → 30

addFirst(10)
10 → 20 → 30
```

Then:

```text
removeFirst()
→ 10

removeLast()
→ 30
```

Remaining:

```text
20
```

---

# 13. Queue vs Deque

### Queue

Primarily works from one logical direction:

```text
Insert → REAR
          ↓
       10 20 30
          ↓
       Remove FRONT
```

### Deque

Works from both ends:

```text
       FRONT              REAR
         ↓                  ↓
        10  → 20 → 30
         ↑                  ↑
    add/remove         add/remove
```

Therefore:

```text
Queue
→ normal queue operations

Deque
→ operations at both ends
```

---

# 14. Important Hierarchy

For your Collections Framework notes, remember:

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

And your roadmap separates:

```text
6. Queue
   ├── Queue Interface
   ├── PriorityQueue
   └── Deque

7. Deque
   ├── ArrayDeque
   └── LinkedList as Deque
```

So **don't go deeply into ArrayDeque and LinkedList-as-Deque here**. They belong to Topic 7.

---

# 15. Quick Comparison

| Feature                | Queue                      | PriorityQueue                   | Deque        |
| ---------------------- | -------------------------- | ------------------------------- | ------------ |
| Type                   | Interface                  | Class                           | Interface    |
| Main concept           | FIFO                       | Priority                        | Double-ended |
| Insert                 | Queue methods              | Queue methods                   | Both ends    |
| Remove                 | Head                       | Highest-priority head           | Both ends    |
| Front operations       | Yes                        | Yes                             | Yes          |
| Rear operations        | Limited normal Queue model | No normal rear-processing model | Yes          |
| Example implementation | LinkedList                 | PriorityQueue                   | ArrayDeque   |

---

# ⭐ ONEPAGE MEMORY MAP

```text
QUEUE
  |
  ├── Queue Interface
  │      |
  │      ├── add()
  │      ├── offer()
  │      ├── remove()
  │      ├── poll()
  │      ├── element()
  │      └── peek()
  │
  ├── PriorityQueue
  │      |
  │      └── Priority-based processing
  │
  └── Deque
         |
         └── Double Ended Queue
                |
                ├── Front
                └── Rear
```

### The three most important formulas

```text
Queue      = FIFO
```

```text
PriorityQueue = Priority-based processing
```

```text
Deque      = Insert/Remove from both ends
```

### And remember the method pairs:

```text
add()      ↔ offer()
remove()   ↔ poll()
element()  ↔ peek()
```

**No Generics used anywhere in this lesson.**
