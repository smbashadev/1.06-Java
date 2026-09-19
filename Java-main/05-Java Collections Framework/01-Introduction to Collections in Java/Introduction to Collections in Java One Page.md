# 1. Introduction to Collections in Java — ONEPAGE

> **Important training rule:** All programs below use **normal/raw collection syntax**. **No Generics concept is used.** Generics will be treated separately later.

---

# 1. Collection Definition

### Definition

A **Collection** in Java is an object that is used to **store and manage a group of objects as a single unit**.

Instead of creating separate variables for every value:

```java
int a = 10;
int b = 20;
int c = 30;
```

we can store multiple objects in a collection:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

### Simple meaning

> **Collection = A container/object that stores a group of objects and provides operations to manage them.**

### Important point

`Collection` can refer to:

1. The **general concept** of storing groups of objects.
2. The **Collection interface** in the Java Collections Framework.

Do not confuse these two meanings.

---

# 2. Need for Collections

Before Collections Framework, arrays were commonly used to store multiple values.

### Problem with arrays

An array has a **fixed size**.

```java
int a[] = new int[3];

a[0] = 10;
a[1] = 20;
a[2] = 30;
```

Suppose later we need to store a fourth value.

The existing array cannot simply grow from size 3 to size 4.

Collections solve this type of problem by providing data structures that can dynamically manage groups of objects.

### Why do we need Collections?

Collections are needed for:

* storing multiple objects;
* dynamically managing the number of elements;
* adding elements;
* removing elements;
* searching elements;
* checking whether an element exists;
* sorting elements;
* traversing elements;
* representing different data-storage requirements.

### Example

```java
import java.util.*;

class NeedCollection
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);
        al.add(40);

        System.out.println(al);
    }
}
```

### Output

```text
[10, 20, 30, 40]
```

The collection can accommodate additional elements without manually creating a new fixed-size array.

---

# 3. Advantages of Collections

The Java Collections Framework provides several advantages.

### 1. Dynamic size

Many collection implementations can grow and shrink according to the number of elements.

### 2. Ready-made data structures

Java provides classes such as:

```text
ArrayList
LinkedList
HashSet
TreeSet
PriorityQueue
HashMap
TreeMap
```

### 3. Ready-made methods

We don't have to write every operation ourselves.

For example:

```java
al.add(10);
al.remove(10);
al.contains(20);
al.size();
```

### 4. Different storage behaviors

Different collections solve different problems.

| Requirement      | Suitable collection |
| ---------------- | ------------------- |
| Ordered elements | List                |
| No duplicates    | Set                 |
| Processing order | Queue               |
| Key-value pairs  | Map                 |
| Stack behavior   | Stack / Deque       |

### 5. Reusable algorithms

Java provides utility operations for:

* sorting
* searching
* reversing
* shuffling
* finding minimum/maximum
* filling
* swapping

through the `Collections` utility class.

### 6. Better data management

Instead of designing our own data structures for common requirements, we can use the classes already provided by Java.

---

# 4. Collections vs Arrays

| Feature               | Array                        | Collection                      |
| --------------------- | ---------------------------- | ------------------------------- |
| Size                  | Fixed                        | Usually dynamically managed     |
| Stores                | Primitive values and objects | Objects                         |
| Data structure        | Basic language feature       | Framework of interfaces/classes |
| Ready-made methods    | Very limited                 | Many methods available          |
| Add/remove operations | Manual                       | Built-in operations             |
| Searching             | Usually programmed manually  | Many APIs available             |
| Sorting               | `Arrays.sort()`              | `Collections.sort()` for Lists  |
| Different structures  | Array only                   | List, Set, Queue, Map etc.      |
| Flexibility           | Comparatively less           | More flexible                   |
| Performance           | Can be very efficient        | Depends on implementation       |

### Simple example

**Array:**

```java
int a[] = {10, 20, 30};
```

**Collection:**

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

### Important difference

An array is **not part of the Collections Framework**.

Also:

> **Map is part of the Java Collections Framework, but Map does not extend the Collection interface.**

This is a very important point.

---

# 5. Collection Framework

### Definition

The **Java Collections Framework (JCF)** is a unified architecture provided by Java for **storing, organizing, accessing and manipulating groups of objects**.

It contains:

```text
Interfaces
    +
Classes
    +
Algorithms
    +
Utility methods
```

### Main components

```text
Java Collections Framework
│
├── Interfaces
│   ├── Iterable
│   ├── Collection
│   ├── List
│   ├── Set
│   ├── Queue
│   ├── Deque
│   └── Map
│
├── Implementations
│   ├── ArrayList
│   ├── LinkedList
│   ├── Vector
│   ├── Stack
│   ├── HashSet
│   ├── LinkedHashSet
│   ├── TreeSet
│   ├── PriorityQueue
│   ├── ArrayDeque
│   ├── HashMap
│   ├── LinkedHashMap
│   └── TreeMap
│
└── Utility classes
    ├── Collections
    └── Arrays
```

### Why is it called a Framework?

Because Java provides a **complete organized structure** containing interfaces, implementations and utility operations that work together.

---

# 6. Collection Hierarchy

The basic hierarchy can be represented as:

```text
                         Iterable
                            |
                       Collection
                            |
             _______________|_______________
            |               |               |
           List             Set            Queue
            |               |               |
     _______|_______    ____|_____       PriorityQueue
    |       |       |  |    |     |           |
ArrayList LinkedList Vector HashSet LinkedHashSet TreeSet
           |       |
           |      Stack
           |
        Deque
          |
     ArrayDeque
```

A more accurate conceptual view is:

```text
Iterable
   |
Collection
   |
   ├── List
   │    ├── ArrayList
   │    ├── LinkedList
   │    ├── Vector
   │    └── Stack
   │
   ├── Set
   │    ├── HashSet
   │    ├── LinkedHashSet
   │    └── TreeSet
   │
   └── Queue
        ├── PriorityQueue
        └── Deque
             ├── ArrayDeque
             └── LinkedList

Map
 |
 ├── HashMap
 ├── LinkedHashMap
 ├── TreeMap
 ├── Hashtable
 ├── ConcurrentHashMap
 └── WeakHashMap
```

### ⚠️ Most important hierarchy doubt

`Map` is **not** a child of `Collection`.

```text
Collection
   |
   +-- List
   +-- Set
   +-- Queue


Map
   |
   +-- HashMap
   +-- TreeMap
   +-- LinkedHashMap
```

Both `Collection` and `Map` are major parts of the **Java Collections Framework**, but they represent different concepts.

---

# Complete Concept at a Glance

```text
             JAVA COLLECTIONS FRAMEWORK
                       |
        _______________|________________
       |                                |
   Collection                          Map
       |                                |
   _____|_____                    Key → Value
  |     |     |
 List  Set   Queue
  |     |      |
  |     |     Deque
  |     |
ArrayList HashSet
LinkedList LinkedHashSet
Vector TreeSet
Stack
```

### Remember this sequence

```text
Iterable
   ↓
Collection
   ↓
List / Set / Queue
   ↓
Implementing Classes
```

And separately:

```text
Map
 ↓
HashMap / LinkedHashMap / TreeMap / ...
```

### ⭐ Final exam/interview points

1. **Collection** stores a group of objects.
2. **Collection Framework** is the complete architecture for handling groups of objects.
3. **Collection is an interface** in `java.util`.
4. **Collections** is also the name of a utility class — don't confuse `Collection` with `Collections`.
5. **List** generally allows duplicates and maintains an order.
6. **Set** does not permit duplicate elements according to its contract.
7. **Queue** represents elements waiting for processing.
8. **Deque** allows operations at both ends.
9. **Map** stores key-value mappings.
10. **Map does not extend Collection.**
11. Arrays have a **fixed size**; many collection implementations provide dynamic sizing.
12. The Collections Framework provides **interfaces, implementations and utility algorithms**.
13. **Generics are intentionally not used here.**
