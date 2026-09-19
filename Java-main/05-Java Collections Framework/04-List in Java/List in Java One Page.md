# 4. List in Java — ONEPAGE

> **Training rule:** We are learning the Collections Framework **without Generics for now**. All programs use normal/raw collection syntax such as `new ArrayList()`. Generics will be studied separately in Topic 14.

---

# 1. What is List?

`List` is an interface in the `java.util` package.

It represents an **ordered collection of elements**.

```text
                    Collection
                        ↑
                       List
                        ↑
        ┌───────────────┼───────────────┐
        ↓               ↓               ↓
    ArrayList       LinkedList        Vector
                                        ↑
                                      Stack
```

### Main characteristics of List

A List:

* Maintains **insertion order**
* Allows **duplicate elements**
* Allows **multiple `null` values** in implementations such as `ArrayList`
* Provides **index-based access**
* Allows elements to be inserted/removed at particular positions

Example:

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

        System.out.println(l);
    }
}
```

Output:

```text
[10, 20, 10, 30]
```

Notice:

```text
10 → duplicate allowed
Order → preserved
```

---

# 2. List Interface — Important Methods

In addition to the common `Collection` methods, `List` provides important index-based operations.

```text
add(element)
add(index, element)

get(index)

set(index, element)

remove(index)
remove(object)

indexOf(object)
lastIndexOf(object)

contains(object)

size()

isEmpty()

clear()
```

Example:

```java
List l = new ArrayList();

l.add(10);
l.add(20);
l.add(30);

System.out.println(l.get(1));
```

Output:

```text
20
```

Indexes start from:

```text
0
1
2
```

So:

```text
index 0 → 10
index 1 → 20
index 2 → 30
```

---

# ⭐ 3. Famous List Doubt — `remove(int)` vs `remove(Object)`

This is one of the most important List concepts.

Consider:

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

Because the argument `1` is an `int`, List's:

```text
remove(int index)
```

is selected.

Therefore:

```text
[10, 30]
```

The element at index `1` was removed.

But:

```java
l.remove(Integer.valueOf(20));
```

means remove the object `20`.

This distinction becomes especially important when working with numeric values.

---

# 4. ArrayList

`ArrayList` is a class that implements `List`.

```text
Collection
    ↑
   List
    ↑
ArrayList
```

It is based on a **resizable array**.

```java
List l = new ArrayList();
```

or:

```java
ArrayList l = new ArrayList();
```

### Characteristics

```text
✓ Maintains insertion order
✓ Allows duplicates
✓ Allows null
✓ Fast random/index access
✓ Dynamic size
✗ Insertion/deletion in the middle can be relatively expensive
✗ Not synchronized
```

Example:

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
        al.add(20);

        System.out.println(al);
        System.out.println(al.get(1));
    }
}
```

Output:

```text
[10, 20, 30, 20]
20
```

---

# 5. ArrayList — Internal Idea

Think of an ArrayList like a resizable array:

```text
Logical elements:

[10] [20] [30] [40]
 0    1    2    3
```

Accessing:

```java
al.get(2);
```

is efficient because the element can be accessed directly by index.

This is why ArrayList is generally preferred when:

> **Frequent reading/access by index is required.**

---

# 6. LinkedList

`LinkedList` is also a class implementing `List`.

It additionally implements `Deque`.

Conceptually:

```text
Collection
    ↑
   List
    ↑
LinkedList
    ↑
also supports Deque operations
```

It is based on a **linked-node structure** rather than a simple array.

Conceptually:

```text
[10] ↔ [20] ↔ [30] ↔ [40]
```

Each node is connected to neighboring nodes.

### Characteristics

```text
✓ Maintains insertion order
✓ Allows duplicates
✓ Allows null
✓ Good for frequent insertion/removal at ends
✓ Can be used as List
✓ Can be used as Deque
✗ Random/index access is generally slower than ArrayList
✗ Not synchronized
```

Example:

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

        ll.addFirst(5);
        ll.addLast(40);

        System.out.println(ll);
    }
}
```

Output:

```text
[5, 10, 20, 30, 40]
```

---

# 7. ArrayList vs LinkedList

| Feature               | ArrayList       | LinkedList       |
| --------------------- | --------------- | ---------------- |
| Internal structure    | Resizable array | Linked nodes     |
| Insertion order       | Yes             | Yes              |
| Duplicates            | Yes             | Yes              |
| `null`                | Allowed         | Allowed          |
| Index access          | Generally fast  | Generally slower |
| Insert/remove at ends | Good            | Very good        |
| Implements            | List            | List + Deque     |
| Synchronized          | No              | No               |

### Simple rule

```text
Frequent index access
        ↓
    ArrayList

Frequent insertion/removal at ends
        ↓
    LinkedList
```

---

# 8. Vector

`Vector` is a legacy collection class that implements `List`.

```text
Collection
    ↑
   List
    ↑
  Vector
```

It is similar in many ways to ArrayList, but its methods are synchronized.

### Characteristics

```text
✓ Maintains insertion order
✓ Allows duplicates
✓ Allows null
✓ Dynamic size
✓ Index-based access
✓ Synchronized
✗ Generally slower than ArrayList because of synchronization overhead
```

Example:

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

# 9. ArrayList vs Vector

| Feature         | ArrayList         | Vector                      |
| --------------- | ----------------- | --------------------------- |
| Implements List | Yes               | Yes                         |
| Insertion order | Yes               | Yes                         |
| Duplicates      | Yes               | Yes                         |
| `null`          | Allowed           | Allowed                     |
| Dynamic size    | Yes               | Yes                         |
| Synchronized    | No                | Yes                         |
| Modern choice   | Usually preferred | Mainly legacy/special cases |

### Remember

```text
ArrayList → Not synchronized
Vector    → Synchronized
```

---

# 10. Stack

`Stack` is a legacy class that extends `Vector`.

```text
Collection
    ↑
   List
    ↑
  Vector
    ↑
  Stack
```

Stack follows the **LIFO** principle:

```text
Last In
   ↓
First Out
```

Think about a stack of plates:

```text
       ┌─────┐
       │ 30  │ ← remove first
       ├─────┤
       │ 20  │
       ├─────┤
       │ 10  │
       └─────┘
```

If we add:

```text
10
20
30
```

then `30` is removed first.

---

# 11. Stack Methods

Important Stack methods:

```text
push()
pop()
peek()
empty()
search()
```

### `push()`

Adds an element to the top.

```java
s.push(10);
s.push(20);
s.push(30);
```

Stack:

```text
30 ← top
20
10
```

### `pop()`

Removes and returns the top element.

```java
s.pop();
```

returns:

```text
30
```

### `peek()`

Returns the top element **without removing it**.

```java
s.peek();
```

returns:

```text
30
```

but the stack remains unchanged.

---

# 12. Stack Program

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

        System.out.println(s);

        System.out.println("Top = " + s.peek());

        System.out.println("Removed = " + s.pop());

        System.out.println(s);
    }
}
```

Output:

```text
[10, 20, 30]
Top = 30
Removed = 30
[10, 20]
```

---

# 13. Stack — `pop()` vs `peek()`

This is another important doubt.

### `pop()`

```text
Returns top
+
Removes top
```

### `peek()`

```text
Returns top
+
Does NOT remove top
```

Example:

```text
Stack:
[10,20,30]
```

After:

```java
s.peek();
```

still:

```text
[10,20,30]
```

After:

```java
s.pop();
```

becomes:

```text
[10,20]
```

---

# 14. Complete List Hierarchy

```text
                         Collection
                             ↑
                            List
                             │
          ┌──────────────────┼─────────────────┐
          ↓                  ↓                 ↓
      ArrayList          LinkedList           Vector
                                                ↑
                                              Stack
```

Important relationship:

```text
ArrayList    → implements List
LinkedList   → implements List + Deque
Vector       → implements List
Stack        → extends Vector
```

---

# 🔥 LIST — ONEPAGE MASTER TABLE

| Feature         | ArrayList       | LinkedList       | Vector                   | Stack   |
| --------------- | --------------- | ---------------- | ------------------------ | ------- |
| Type            | Class           | Class            | Class                    | Class   |
| List            | Yes             | Yes              | Yes                      | Yes     |
| Insertion order | Yes             | Yes              | Yes                      | Yes     |
| Duplicates      | Yes             | Yes              | Yes                      | Yes     |
| `null`          | Allowed         | Allowed          | Allowed                  | Allowed |
| Index access    | Fast            | Slower           | Fast                     | Fast    |
| Synchronized    | No              | No               | Yes                      | Yes     |
| Special purpose | General List    | List + Deque     | Legacy synchronized List | LIFO    |
| Key idea        | Resizable array | Linked structure | Synchronized List        | Stack   |

---

# 🧠 When Should I Choose Which?

```text
Need a general-purpose List
            ↓
       ArrayList
```

```text
Need frequent insertion/removal at ends
            ↓
       LinkedList
```

```text
Need legacy synchronized List behavior
            ↓
          Vector
```

```text
Need LIFO stack behavior
            ↓
          Stack
```

For modern Java code, `Deque` implementations such as `ArrayDeque` are generally preferred over the legacy `Stack` class for stack behavior, but **Stack remains important to learn** because it is part of the Java Collections Framework and commonly appears in older code and interviews.

---

# ⭐ FINAL MEMORY MAP

```text
LIST
 │
 ├── Maintains insertion order
 │
 ├── Allows duplicates
 │
 ├── Supports index-based access
 │
 └── Implementations
       │
       ├── ArrayList
       │     └── Resizable array
       │
       ├── LinkedList
       │     └── Linked structure + Deque
       │
       ├── Vector
       │     └── Synchronized legacy List
       │
       └── Stack
             └── LIFO
```

### The four names in one sentence

> **ArrayList = resizable array, LinkedList = linked structure, Vector = synchronized legacy List, Stack = LIFO legacy List.**

And the most important List-specific concept to carry forward is:

```text
List
 ↓
index
 ↓
get(index)
set(index, element)
add(index, element)
remove(index)
```

That **index-based behavior** is what makes `List` fundamentally different from the `Set` and `Queue` topics that come next.
