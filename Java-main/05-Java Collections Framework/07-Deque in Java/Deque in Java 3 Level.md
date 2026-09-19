# 7. Deque in Java — 3LEVEL

> **Training rule:** No Generics. All programs use normal/raw collection syntax.

The **3LEVEL** approach means we understand every concept at three depths:

1. **LEVEL 1 — Basic:** What is it?
2. **LEVEL 2 — Understanding:** How and why does it work?
3. **LEVEL 3 — Practical:** Program, output, and important questions.

---

# PART A — ArrayDeque

## LEVEL 1 — BASIC

### 1. What is ArrayDeque?

`ArrayDeque` is a **class** in Java used to implement a **Deque**.

Deque means:

> **Double Ended Queue**

It allows insertion and removal from **both ends**.

```text
             FRONT                    REAR
               ↓                       ↓
             10 → 20 → 30 → 40
               ↑                       ↑
            operations              operations
```

Important class:

```java
ArrayDeque
```

Package:

```java
java.util
```

Import:

```java
import java.util.*;
```

Create object:

```java
ArrayDeque d = new ArrayDeque();
```

---

## 2. Why is it called ArrayDeque?

The name has two parts:

```text
Array + Deque
```

It is a Deque implementation based on a resizable array structure internally.

You don't directly manage the array.

Java manages it for you.

---

## 3. Basic ArrayDeque Methods

The most important methods are:

| Method          | Purpose           |
| --------------- | ----------------- |
| `addFirst()`    | Add at front      |
| `addLast()`     | Add at rear       |
| `removeFirst()` | Remove from front |
| `removeLast()`  | Remove from rear  |
| `peekFirst()`   | View front        |
| `peekLast()`    | View rear         |

Remember:

```text
             DEQUE
               |
       ┌───────┴───────┐
       ↓               ↓
     FRONT            REAR
       |               |
 addFirst()        addLast()
 removeFirst()     removeLast()
 peekFirst()       peekLast()
```

---

# LEVEL 2 — UNDERSTANDING

## 4. `addFirst()`

Adds an element at the **front**.

```java
d.addFirst(10);
```

If:

```text
20 → 30
```

after:

```java
d.addFirst(10);
```

we get:

```text
10 → 20 → 30
```

---

## 5. `addLast()`

Adds an element at the **rear**.

```java
d.addLast(40);
```

If:

```text
10 → 20 → 30
```

then:

```text
10 → 20 → 30 → 40
```

---

## 6. `removeFirst()`

Removes the element at the **front**.

```java
d.removeFirst();
```

If:

```text
10 → 20 → 30
```

then `10` is removed:

```text
20 → 30
```

---

## 7. `removeLast()`

Removes the element at the **rear**.

```java
d.removeLast();
```

If:

```text
10 → 20 → 30
```

then `30` is removed:

```text
10 → 20
```

---

## 8. `peekFirst()`

Returns the front element **without removing it**.

```java
d.peekFirst();
```

For:

```text
10 → 20 → 30
```

result:

```text
10
```

Deque remains:

```text
10 → 20 → 30
```

---

## 9. `peekLast()`

Returns the rear element without removing it.

```java
d.peekLast();
```

For:

```text
10 → 20 → 30
```

result:

```text
30
```

Deque remains unchanged.

---

## 10. Does ArrayDeque allow duplicates?

Yes.

```java
ArrayDeque d = new ArrayDeque();

d.addLast(10);
d.addLast(10);
d.addLast(20);

System.out.println(d);
```

Output:

```text
[10, 10, 20]
```

So:

```text
ArrayDeque → duplicates allowed
```

---

## 11. Does ArrayDeque allow null?

**No.**

```java
ArrayDeque d = new ArrayDeque();

d.add(null);
```

This causes a `NullPointerException`.

Remember:

```text
ArrayDeque
   ↓
null ❌
duplicates ✅
```

---

# LEVEL 3 — PRACTICAL

## 12. Complete ArrayDeque Program

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

        System.out.println("Deque = " + d);

        System.out.println("First = " + d.peekFirst());
        System.out.println("Last = " + d.peekLast());

        System.out.println("Removed First = " + d.removeFirst());
        System.out.println("Removed Last = " + d.removeLast());

        System.out.println("Deque = " + d);
    }
}
```

Output:

```text
Deque = [10, 20, 30, 40]
First = 10
Last = 40
Removed First = 10
Removed Last = 40
Deque = [20, 30]
```

---

# 13. How ArrayDeque Can Behave Like a Queue

Queue follows:

```text
FIFO
```

**First In First Out**

Use:

```java
d.addLast();
d.removeFirst();
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

Therefore:

```text
addLast() + removeFirst()
        ↓
       FIFO
        ↓
      Queue
```

---

# 14. How ArrayDeque Can Behave Like a Stack

Stack follows:

```text
LIFO
```

**Last In First Out**

Use:

```java
d.addFirst();
d.removeFirst();
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

Therefore:

```text
addFirst() + removeFirst()
        ↓
       LIFO
        ↓
       Stack
```

---

# PART B — LinkedList as Deque

## LEVEL 1 — BASIC

## 15. Can LinkedList be used as a Deque?

**Yes.**

This is an important point.

`LinkedList` implements the `Deque` interface.

Therefore a `LinkedList` object can perform Deque operations.

```java
LinkedList l = new LinkedList();
```

You can use:

```java
l.addFirst();
l.addLast();

l.removeFirst();
l.removeLast();

l.peekFirst();
l.peekLast();
```

---

## 16. Simplified Relationship

```text
              Collection
                  |
                Queue
                  |
                Deque
                  ↑
                  |
             LinkedList
```

`LinkedList` also implements `List`, which is why it can perform List operations as well.

Conceptually:

```text
                 LinkedList
                /          \
               /            \
             List           Deque
                            |
                           Queue
```

---

# LEVEL 2 — UNDERSTANDING

## 17. LinkedList `addFirst()`

```java
l.addFirst(10);
```

Adds at the front.

Example:

```text
20 → 30
```

After:

```java
l.addFirst(10);
```

becomes:

```text
10 → 20 → 30
```

---

## 18. LinkedList `addLast()`

```java
l.addLast(40);
```

adds at the rear.

```text
10 → 20 → 30
```

becomes:

```text
10 → 20 → 30 → 40
```

---

## 19. LinkedList `removeFirst()`

```java
l.removeFirst();
```

For:

```text
10 → 20 → 30
```

removes `10`.

Remaining:

```text
20 → 30
```

---

## 20. LinkedList `removeLast()`

```java
l.removeLast();
```

For:

```text
10 → 20 → 30
```

removes `30`.

Remaining:

```text
10 → 20
```

---

## 21. LinkedList `peekFirst()`

```java
l.peekFirst();
```

returns the first element without removing it.

```text
10 → 20 → 30
```

Result:

```text
10
```

---

## 22. LinkedList `peekLast()`

```java
l.peekLast();
```

returns the last element without removing it.

For:

```text
10 → 20 → 30
```

result:

```text
30
```

---

## 23. Does LinkedList allow null?

Yes.

```java
LinkedList l = new LinkedList();

l.add(null);

System.out.println(l);
```

Output:

```text
[null]
```

Therefore:

```text
LinkedList
   ↓
null ✅
duplicates ✅
```

---

# LEVEL 3 — PRACTICAL

## 24. Complete LinkedList-as-Deque Program

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

        System.out.println("Deque = " + l);

        System.out.println("First = " + l.peekFirst());
        System.out.println("Last = " + l.peekLast());

        System.out.println("Removed First = " + l.removeFirst());
        System.out.println("Removed Last = " + l.removeLast());

        System.out.println("Deque = " + l);
    }
}
```

Output:

```text
Deque = [10, 20, 30, 40]
First = 10
Last = 40
Removed First = 10
Removed Last = 40
Deque = [20, 30]
```

---

# 25. ArrayDeque vs LinkedList as Deque

| Feature          | ArrayDeque | LinkedList |
| ---------------- | ---------- | ---------- |
| Class            | Yes        | Yes        |
| Deque operations | Yes        | Yes        |
| Add front        | Yes        | Yes        |
| Add rear         | Yes        | Yes        |
| Remove front     | Yes        | Yes        |
| Remove rear      | Yes        | Yes        |
| Duplicates       | Yes        | Yes        |
| `null`           | ❌ No       | ✅ Yes      |
| List operations  | ❌ No       | ✅ Yes      |
| Queue behavior   | Yes        | Yes        |
| Stack behavior   | Yes        | Yes        |

---

# 26. Most Important Comparison

Suppose the requirement is:

> "I only need a double-ended queue."

Then:

```text
ArrayDeque
```

is generally the natural choice.

Suppose the requirement is:

> "I need List functionality and also want to use it as a Deque."

Then:

```text
LinkedList
```

can provide both.

---

# 27. Three-Level Final Revision

## LEVEL 1 — Remember

```text
Deque
 ↓
Double Ended Queue
 ↓
Operations at FRONT and REAR
```

Important implementations:

```text
ArrayDeque
LinkedList
```

---

## LEVEL 2 — Understand

```text
              FRONT              REAR
                ↓                  ↓
              10 → 20 → 30 → 40
                ↑                  ↑
            add/remove          add/remove
```

Methods:

```text
addFirst()       → FRONT
addLast()        → REAR

removeFirst()    → FRONT
removeLast()     → REAR

peekFirst()      → FRONT
peekLast()       → REAR
```

---

## LEVEL 3 — Apply

### Queue behavior

```text
addLast()
removeFirst()

       ↓
      FIFO
```

### Stack behavior

```text
addFirst()
removeFirst()

       ↓
      LIFO
```

### ArrayDeque

```text
null ❌
duplicates ✅
```

### LinkedList

```text
null ✅
duplicates ✅
List + Deque functionality
```

---

# 28. Final Memory Map

```text
7. DEQUE
│
├── Deque
│   └── Double Ended Queue
│
├── ArrayDeque
│   ├── addFirst()
│   ├── addLast()
│   ├── removeFirst()
│   ├── removeLast()
│   ├── peekFirst()
│   ├── peekLast()
│   ├── duplicates ✅
│   └── null ❌
│
└── LinkedList as Deque
    ├── addFirst()
    ├── addLast()
    ├── removeFirst()
    ├── removeLast()
    ├── peekFirst()
    ├── peekLast()
    ├── duplicates ✅
    ├── null ✅
    └── List + Deque functionality
```

> **Core idea:** `ArrayDeque` and `LinkedList` can both work as a **Deque**, allowing elements to be inserted, removed, or examined from **both the front and rear**.
