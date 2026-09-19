# 4. List in Java — 3LEVEL

> **Training rule:** No Generics anywhere.
> All examples use normal/raw collection syntax.

The **3LEVEL** approach means we understand each concept at three depths:

* 🟢 **LEVEL 1 — Basic:** What is it?
* 🟡 **LEVEL 2 — Practical:** How does it work and how do we program it?
* 🔴 **LEVEL 3 — Interview/Deep understanding:** Important differences, traps, and when to use it.

---

# 1. List Interface

## 🟢 LEVEL 1 — Basic

`List` is an interface in `java.util` used to store a group of objects where:

* **Insertion order is maintained**
* **Duplicates are allowed**
* **Index-based access is available**

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

        System.out.println(l);
    }
}
```

Output:

```text
[10, 20, 10]
```

Notice:

```text
10
20
10
```

The duplicate `10` is allowed.

---

## 🟡 LEVEL 2 — Practical

List provides operations such as:

```java
add()
get()
set()
remove()
contains()
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

System.out.println(l.get(1));  // 20

l.set(1, 50);

System.out.println(l);        // [10, 50, 30]
```

Remember:

```text
add() → insert
get() → retrieve
set() → replace
remove() → delete
```

---

## 🔴 LEVEL 3 — Important Understanding

`List` is an **interface**, so:

```java
List l = new List();
```

❌ Invalid.

Instead:

```java
List l = new ArrayList();
```

or:

```java
List l = new LinkedList();
```

or:

```java
List l = new Vector();
```

The important hierarchy is:

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

### List identity

```text
ORDER       → Yes
DUPLICATES  → Yes
INDEX       → Yes
```

---

# 2. ArrayList

## 🟢 LEVEL 1 — Basic

`ArrayList` is a class that implements `List`.

It is essentially a **resizable/dynamic array**.

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

## 🟡 LEVEL 2 — Practical

ArrayList allows:

### Adding

```java
al.add(10);
```

### Adding at a particular position

```java
al.add(1, 50);
```

### Reading

```java
al.get(1);
```

### Replacing

```java
al.set(1, 100);
```

### Removing

```java
al.remove(1);
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

        System.out.println(al);

        al.add(1, 50);
        System.out.println(al);

        al.set(1, 100);
        System.out.println(al);

        al.remove(1);
        System.out.println(al);
    }
}
```

Output:

```text
[10, 20, 30]
[10, 50, 20, 30]
[10, 100, 20, 30]
[10, 20, 30]
```

---

## 🔴 LEVEL 3 — Important Understanding

ArrayList internally uses an array-like structure whose capacity can grow as needed.

This gives it a major advantage:

```text
Index-based access
       ↓
Very efficient
```

For example:

```java
al.get(500);
```

does not require walking through every previous element as a linked structure would.

But insertion/removal in the middle can require shifting elements.

Example:

```text
Before:

[10][20][30][40]

Insert 50 at index 1:

[10][50][20][30][40]
      ↑
   elements shifted
```

### ArrayList is generally a good default List choice.

---

# 3. LinkedList

## 🟢 LEVEL 1 — Basic

`LinkedList` is another implementation of `List`.

Conceptually, elements are maintained as linked nodes:

```text
[10] ↔ [20] ↔ [30] ↔ [40]
```

Basic program:

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

## 🟡 LEVEL 2 — Practical

LinkedList provides convenient operations at both ends:

```java
addFirst()
addLast()

removeFirst()
removeLast()
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedList ll = new LinkedList();

        ll.add(20);

        ll.addFirst(10);
        ll.addLast(30);

        System.out.println(ll);

        ll.removeFirst();
        ll.removeLast();

        System.out.println(ll);
    }
}
```

Output:

```text
[10, 20, 30]
[20]
```

---

## 🔴 LEVEL 3 — Important Understanding

LinkedList is special because it implements both:

```text
List
  +
Deque
```

Conceptually:

```text
LinkedList
    │
    ├── List behavior
    │
    └── Deque behavior
```

Therefore, it can be used for operations involving both ends.

However, don't memorize:

```text
LinkedList = always faster
ArrayList = always slower
```

That is incorrect.

The appropriate choice depends on the workload.

### General rule

```text
Frequent index-based access
        ↓
ArrayList is usually preferable

Frequent operations at the ends
        ↓
LinkedList can be useful
```

---

# 4. Vector

## 🟢 LEVEL 1 — Basic

`Vector` is another implementation of `List`.

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

## 🟡 LEVEL 2 — Practical

Vector supports many familiar List operations:

```java
v.add(10);
v.add(20);

System.out.println(v.get(0));

v.set(0, 100);

v.remove(1);
```

It also has methods associated with its legacy design, such as:

```java
capacity()
```

Example:

```java
System.out.println(v.size());
System.out.println(v.capacity());
```

Remember:

```text
size
 ↓
number of actual elements

capacity
 ↓
currently allocated internal storage capacity
```

---

## 🔴 LEVEL 3 — Important Understanding

The major characteristic of traditional `Vector` is:

> Its methods are synchronized.

So historically:

```text
ArrayList
    ↓
not synchronized

Vector
    ↓
synchronized
```

Vector is also considered a **legacy collection class**.

It is still part of Java and you should understand it, especially for existing/older code and interviews.

For most new general-purpose List usage, `ArrayList` is normally preferred when synchronization is not required.

---

# 5. Stack

## 🟢 LEVEL 1 — Basic

`Stack` is a class that extends `Vector`.

Its purpose is to provide:

# LIFO

**Last In, First Out**

Think about plates:

```text
       30 ← last added
       20
       10
```

You remove `30` first.

---

## 🟡 LEVEL 2 — Practical

The important Stack methods are:

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

Conceptually:

```text
30 ← TOP
20
10
```

---

### `peek()`

Looks at the top without removing it.

```java
System.out.println(s.peek());
```

Output:

```text
30
```

Stack remains:

```text
30
20
10
```

---

### `pop()`

Returns and removes the top element.

```java
System.out.println(s.pop());
```

Output:

```text
30
```

Now:

```text
20 ← TOP
10
```

---

## 🔴 LEVEL 3 — Important Understanding

Complete example:

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

        System.out.println("Stack = " + s);
    }
}
```

Output:

```text
Stack = [10, 20, 30]
Top = 30
Removed = 30
Stack = [10, 20]
```

### `peek()` vs `pop()`

```text
peek()
 ↓
see top
 ↓
don't remove


pop()
 ↓
see top
 ↓
remove top
```

---

# 6. ArrayList vs LinkedList vs Vector vs Stack

| Feature               | ArrayList     | LinkedList       | Vector                   | Stack                                   |
| --------------------- | ------------- | ---------------- | ------------------------ | --------------------------------------- |
| Type                  | Class         | Class            | Class                    | Class                                   |
| List                  | Yes           | Yes              | Yes                      | Yes                                     |
| Duplicates            | Yes           | Yes              | Yes                      | Yes                                     |
| Insertion order       | Yes           | Yes              | Yes                      | Yes                                     |
| Index access          | Yes           | Yes              | Yes                      | Yes                                     |
| Main idea             | Dynamic array | Linked structure | Synchronized legacy List | LIFO                                    |
| Synchronization       | No            | No               | Yes                      | Inherited from Vector                   |
| Special behavior      | General List  | List + Deque     | Legacy                   | Stack operations                        |
| Typical modern choice | **Yes**       | Depends          | Usually legacy           | Usually prefer Deque for new stack code |

---

# 7. The entire List concept in one picture

```text
                         Collection
                              ↑
                            List
                              ↑
             ┌────────────────┼────────────────┐
             │                │                │
             ↓                ↓                ↓
         ArrayList        LinkedList         Vector
                                                ↑
                                                │
                                              Stack
```

Now attach the identity to each:

```text
List
 ↓
ORDER + DUPLICATES + INDEX


ArrayList
 ↓
DYNAMIC ARRAY


LinkedList
 ↓
LINKED STRUCTURE
+
LIST + DEQUE


Vector
 ↓
SYNCHRONIZED
+
LEGACY


Stack
 ↓
LIFO
+
push / pop / peek
```

---

# 🧠 3LEVEL Final Revision

### 🟢 LEVEL 1 — Know the names

```text
List
 ├── ArrayList
 ├── LinkedList
 └── Vector
      └── Stack
```

### 🟡 LEVEL 2 — Know the purpose

```text
ArrayList  → general-purpose dynamic List
LinkedList → linked List + Deque behavior
Vector     → synchronized legacy List
Stack      → LIFO
```

### 🔴 LEVEL 3 — Know the traps

```text
List is an interface
        ↓
can't directly create List object

List allows duplicates
        ↓
Set does not

add(index, value)
        ↓
insert

set(index, value)
        ↓
replace

peek()
        ↓
see top, don't remove

pop()
        ↓
see + remove top

Vector
        ↓
synchronized legacy class

Stack
        ↓
extends Vector
        ↓
LIFO
```

And **throughout this 3LEVEL lesson, no Generics were used**—we're keeping Generics completely separate for Topic 14, exactly according to your training approach.
