# 2. Iterable in Java — ONEPAGE

> **Training rule:** No Generics are used. All examples use normal/raw Java syntax.

---

# 1. What is `Iterable`?

`Iterable` is an **interface** in Java that represents an object whose elements can be traversed/iterated one by one.

It is located in:

```java
java.lang.Iterable
```

The basic relationship is:

```text
Iterable
   |
Collection
   |
   +-- List
   +-- Set
   `-- Queue
```

So:

> **`Iterable` is the parent interface of `Collection`.**

---

# 2. Why do we need `Iterable`?

Suppose we have a collection:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

We need some mechanism to visit each element:

```text
10 → 20 → 30
```

This process is called **iteration** or **traversal**.

`Iterable` provides the standard mechanism that allows an object to be used with the enhanced `for` loop.

---

# 3. `Iterable` and `for-each` Loop

Example:

```java
import java.util.*;

class IterableDemo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);

        for(Object x : al)
        {
            System.out.println(x);
        }
    }
}
```

### Output

```text
10
20
30
```

The enhanced `for` loop works because `ArrayList` ultimately implements `Iterable`.

Conceptually:

```text
ArrayList
   ↓
List
   ↓
Collection
   ↓
Iterable
   ↓
for-each support
```

---

# 4. What does `Iterable` provide?

The important method is:

```java
iterator()
```

Conceptually:

```text
Iterable
    |
    └── iterator()
             |
             ↓
          Iterator
```

`iterator()` returns an `Iterator` object.

Example:

```java
import java.util.*;

class IterableIteratorDemo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);

        Iterator itr = al.iterator();

        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }
    }
}
```

### Output

```text
10
20
30
```

---

# 5. How does `iterator()` work?

The process is:

```text
Collection object
      ↓
iterator()
      ↓
Iterator object
      ↓
hasNext()
      ↓
next()
      ↓
Next element
```

For:

```text
[10, 20, 30]
```

the traversal conceptually becomes:

```text
hasNext() → true
next()    → 10

hasNext() → true
next()    → 20

hasNext() → true
next()    → 30

hasNext() → false
```

---

# 6. Important `Iterable` Hierarchy

```text
              Iterable
                  |
              Collection
                  |
       ___________|___________
      |           |           |
     List        Set         Queue
      |           |           |
 ArrayList     HashSet   PriorityQueue
 LinkedList    TreeSet      Deque
 Vector
 Stack
```

`Map` is **not** under `Iterable` through `Collection`.

```text
Iterable
   |
Collection
```

whereas:

```text
Map
```

is a separate branch of the Collections Framework.

---

# 7. Does `Map` implement `Iterable`?

A `Map` itself is not a `Collection`, and it does not directly provide `Iterable` behavior for its key-value entries as a whole.

For example:

```java
HashMap hm = new HashMap();

hm.put(101, "Ravi");
hm.put(102, "John");
```

You cannot treat the `HashMap` itself as an `Iterable` collection of key-value pairs in the same way as an `ArrayList`.

Instead, you can iterate through views such as:

```text
keySet()
values()
entrySet()
```

This will become important in the **Map** and **Iterator** topics.

---

# 8. `Iterable` vs `Iterator`

This is one of the most important differences.

| `Iterable`                                | `Iterator`                                        |
| ----------------------------------------- | ------------------------------------------------- |
| Interface                                 | Interface                                         |
| Represents something that can be iterated | Represents the object used to perform iteration   |
| Provides `iterator()`                     | Provides methods such as `hasNext()` and `next()` |
| Used as the source of iteration           | Performs the actual traversal                     |

Think:

```text
Iterable
   ↓
"I can provide an iterator."

Iterator
   ↓
"I actually move through the elements."
```

---

# 9. Real-Life Analogy

Imagine a bookshelf containing books.

```text
Bookshelf
 ↓
Iterable
```

The bookshelf says:

> "You can go through my books."

The person moving from book to book is:

```text
Iterator
```

So:

```text
Iterable
   ↓
provides Iterator
   ↓
Iterator traverses elements
```

---

# 10. Can We Implement `Iterable` Ourselves?

**Yes.**

A class can implement `Iterable` and provide its own `iterator()` implementation.

Basic idea:

```java
class MyClass implements Iterable
{
    public Iterator iterator()
    {
        // return Iterator object
    }
}
```

This is useful when creating custom objects that should support standard iteration.

For beginner Collections training, the important relationship is:

```text
Collection
     ↓
Iterable
     ↓
iterator()
     ↓
Iterator
```

---

# 11. Why is `Iterable` important?

It provides a **common iteration contract**.

Without needing to know the exact implementation:

```text
ArrayList
LinkedList
HashSet
TreeSet
Vector
```

we can traverse their elements using the standard iteration mechanism.

For example:

```java
for(Object x : al)
{
    System.out.println(x);
}
```

The same basic traversal style can be used with many different collection implementations.

---

# 12. ONEPAGE SUMMARY

```text
                         Iterable
                            |
                     iterator()
                            |
                            ↓
                         Iterator
                            |
                     hasNext()/next()
                            |
                            ↓
                       Elements
```

### Remember these facts:

* `Iterable` is an **interface**.
* It belongs to `java.lang`.
* `Collection` extends `Iterable`.
* It provides the `iterator()` method.
* `iterator()` returns an `Iterator`.
* `Iterator` performs the actual traversal.
* The enhanced `for` loop works with objects that are `Iterable`.
* `List`, `Set`, and `Queue` are ultimately `Iterable`.
* `Map` is **not** a subtype of `Collection` and is treated separately.
* `Iterable` and `Iterator` are **not the same thing**.

### Golden formula

```text
Iterable
   ↓
iterator()
   ↓
Iterator
   ↓
hasNext()
   ↓
next()
   ↓
Element
```

> **In one sentence:** `Iterable` is the interface that provides a standard way for an object to supply an `Iterator`, enabling its elements to be traversed one by one.
