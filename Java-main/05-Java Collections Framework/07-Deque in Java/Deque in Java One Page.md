# 7. Deque in Java — ONEPAGE

> **Training rule:** No Generics are used. All programs use normal/raw Collection Framework syntax.

## 1. What is Deque?

**Deque** stands for **Double Ended Queue**.

It is an interface in Java that allows insertion and removal of elements from **both ends**.

```text
              FRONT                 REAR
                ↓                     ↓
             10 → 20 → 30
                ↑                     ↑
          operations possible at both ends
```

Hierarchy:

```text
Collection
    ↓
  Queue
    ↓
  Deque
   ↙  ↘
ArrayDeque  LinkedList
```

Two important implementations covered here:

1. `ArrayDeque`
2. `LinkedList` as a Deque

---

# 2. ArrayDeque

## Definition

`ArrayDeque` is a class that implements the `Deque` interface.

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

## Important ArrayDeque Methods

### Insert at Front

```java
d.addFirst(10);
```

### Insert at Rear

```java
d.addLast(20);
```

### Remove from Front

```java
d.removeFirst();
```

### Remove from Rear

```java
d.removeLast();
```

### Examine Front

```java
d.peekFirst();
```

### Examine Rear

```java
d.peekLast();
```

---

## Example

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

        System.out.println(d);

        System.out.println(d.removeFirst());
        System.out.println(d.removeLast());

        System.out.println(d);
    }
}
```

Output:

```text
[10, 20, 30]
10
30
[20]
```

---

## ArrayDeque as Queue

Use:

```text
addLast()
removeFirst()
```

Example:

```java
d.addLast(10);
d.addLast(20);
d.addLast(30);

d.removeFirst();
```

Result:

```text
10
```

This gives:

```text
FIFO
First In → First Out
```

---

## ArrayDeque as Stack

Use:

```text
addFirst()
removeFirst()
```

Example:

```java
d.addFirst(10);
d.addFirst(20);
d.addFirst(30);

d.removeFirst();
```

Result:

```text
30
```

This gives:

```text
LIFO
Last In → First Out
```

---

## Important ArrayDeque Points

* Implements `Deque`.
* Allows insertion/removal at both ends.
* Can behave like a Queue.
* Can behave like a Stack.
* **Does not permit `null` elements.**
* Allows duplicate elements.
* Not synchronized.
* Usually preferred over the legacy `Stack` class for stack-style operations.

---

# 3. LinkedList as Deque

`LinkedList` is a class that can implement the `Deque` interface.

Therefore:

```java
Deque d = new LinkedList();
```

is valid.

```text
LinkedList
     ↓
  Deque
```

Example:

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

## LinkedList Deque Methods

The same major Deque methods can be used:

```java
addFirst()
addLast()

removeFirst()
removeLast()

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
        Deque d = new LinkedList();

        d.addFirst(20);
        d.addFirst(10);
        d.addLast(30);

        System.out.println(d);

        System.out.println(d.removeFirst());
        System.out.println(d.removeLast());

        System.out.println(d);
    }
}
```

Output:

```text
[10, 20, 30]
10
30
[20]
```

---

# 4. ArrayDeque vs LinkedList as Deque

| Feature              | ArrayDeque      | LinkedList   |
| -------------------- | --------------- | ------------ |
| Type                 | Class           | Class        |
| Implements Deque     | Yes             | Yes          |
| Insert at both ends  | Yes             | Yes          |
| Remove at both ends  | Yes             | Yes          |
| Allows duplicates    | Yes             | Yes          |
| Allows `null`        | **No**          | **Yes**      |
| Can work as Queue    | Yes             | Yes          |
| Can work as Stack    | Yes             | Yes          |
| Underlying structure | Resizable array | Linked nodes |
| Legacy class?        | No              | No           |

---

# 5. Most Important Difference

The easiest difference to remember:

```text
ArrayDeque
→ null NOT allowed
```

```text
LinkedList
→ null allowed
```

Example:

### ArrayDeque

```java
ArrayDeque d = new ArrayDeque();

d.add(null);
```

❌ `NullPointerException`

### LinkedList

```java
LinkedList l = new LinkedList();

l.add(null);
```

✅ `null` can be stored.

---

# 6. Which One Should You Remember?

For **Deque operations**, think:

```text
                 Deque
                /     \
               /       \
       ArrayDeque    LinkedList
```

### ArrayDeque

Think:

> **Fast general-purpose Deque implementation, no null.**

### LinkedList

Think:

> **Linked-node List + Queue + Deque capabilities, and null is permitted.**

---

# ⭐ ONEPAGE MEMORY MAP

```text
                         DEQUE
                  Double Ended Queue
                         |
                ┌────────┴────────┐
                ↓                 ↓
          ArrayDeque          LinkedList
                |                 |
          null not allowed    null allowed
                |                 |
                └────────┬────────┘
                         ↓
                 BOTH ENDS
                         |
              ┌──────────┴──────────┐
              ↓                     ↓
           FRONT                   REAR
              |                     |
         addFirst()              addLast()
         removeFirst()           removeLast()
         peekFirst()             peekLast()
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
addFirst(10)
addFirst(20)
addFirst(30)

30
↓
20
↓
10

removeFirst() → 30

LIFO
```

## Final takeaway

> **ArrayDeque and LinkedList can both implement Deque. Deque allows operations at both ends. ArrayDeque does not permit `null`, whereas LinkedList does. Both can be used to implement Queue-like FIFO behavior and Stack-like LIFO behavior.**

**No Generics are used anywhere in this lesson.**
