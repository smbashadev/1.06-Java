# 1. Introduction to Collections in Java — 3LEVEL

> **Training rule:** Normal/raw collection programs only. **No Generics syntax** such as `ArrayList<Integer>` or `HashMap<Integer, String>`. Generics will be studied separately.

The **3LEVEL method** means we understand every concept at three depths:

```text
LEVEL 1 → What is it?
LEVEL 2 → How does it work?
LEVEL 3 → Why/when do we use it?
```

---

# 1. Collection Definition

## LEVEL 1 — What is a Collection?

A **Collection** is an object used to represent a group of objects as a single unit.

Example:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);

System.out.println(al);
```

### Output

```text
[10, 20, 30]
```

Instead of managing three separate values, we manage them through one collection object.

```text
10
20
30
 ↓
Collection object
```

---

## LEVEL 2 — How does it work?

`Collection` is also the name of a Java interface.

```java
Collection c = new ArrayList();
```

Here:

```text
Collection
    ↓
interface/reference type

ArrayList
    ↓
implementation class

c
    ↓
reference variable
```

The `Collection` interface defines common operations that many collection types can perform.

---

## LEVEL 3 — Why do we need it?

Applications frequently need to manage groups of objects.

Examples:

```text
Students
Employees
Products
Marks
Orders
Customers
```

Instead of creating separate variables:

```java
int a = 10;
int b = 20;
int c = 30;
```

we can use:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

### Remember

> **Collection = group of objects managed as one unit.**

---

# 2. Need for Collections

## LEVEL 1 — What is the need?

Java programs frequently need to store multiple objects.

For example:

```text
Student 1
Student 2
Student 3
Student 4
...
```

We need a convenient mechanism to store and manipulate them.

---

## LEVEL 2 — What problems do Collections solve?

### Problem 1 — Managing many values

Instead of:

```java
int a = 10;
int b = 20;
int c = 30;
int d = 40;
```

we can write:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
al.add(40);
```

---

### Problem 2 — Dynamic management

An array has a fixed length after creation:

```java
int a[] = new int[3];
```

It cannot simply become a four-element array.

A collection such as `ArrayList` can dynamically manage its internal storage as elements are added or removed.

---

### Problem 3 — Ready-made operations

Collections provide operations such as:

```text
add()
remove()
contains()
size()
clear()
```

Example:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);

System.out.println(al.contains(20));

al.remove(20);

System.out.println(al);
```

### Output

```text
true
[10, 30]
```

---

## LEVEL 3 — Why are different collections necessary?

Because different problems require different behavior.

```text
Need
 |
 +-- Duplicates allowed
 |       ↓
 |      List
 |
 +-- No duplicate elements
 |       ↓
 |      Set
 |
 +-- Queue-based processing
 |       ↓
 |      Queue
 |
 +-- Key → Value
         ↓
        Map
```

### Remember

> **Collections are needed because applications need flexible, reusable ways to store and manipulate groups of objects.**

---

# 3. Advantages of Collections

## LEVEL 1 — What are the advantages?

The major advantages are:

```text
1. Dynamic size management
2. Ready-made data structures
3. Ready-made methods
4. Different collection types
5. Reusable algorithms
6. Common architecture
```

---

## LEVEL 2 — How do these advantages work?

### 1. Dynamic size management

Example:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
al.add(40);
al.add(50);
```

The programmer does not manually create a new larger array each time an element is added.

---

### 2. Ready-made data structures

Java provides:

```text
ArrayList
LinkedList
Vector
Stack

HashSet
LinkedHashSet
TreeSet

PriorityQueue
ArrayDeque

HashMap
LinkedHashMap
TreeMap
Hashtable
```

---

### 3. Ready-made methods

For example:

```java
al.add(10);
al.remove(10);
al.contains(20);
al.size();
al.clear();
```

---

### 4. Different behaviors

```text
List
 ↓
duplicates + ordering

Set
 ↓
unique elements

Queue
 ↓
queue processing

Map
 ↓
key-value association
```

---

### 5. Algorithms

The `Collections` utility class provides operations such as:

```text
sort()
reverse()
shuffle()
max()
min()
binarySearch()
swap()
fill()
frequency()
```

---

## LEVEL 3 — When are these advantages useful?

Imagine an e-commerce application.

You may need:

```text
Product list
      ↓
ArrayList

Unique product categories
      ↓
HashSet

Products waiting for processing
      ↓
Queue

Product ID → Product
      ↓
HashMap
```

Instead of building every data structure yourself, Java provides them.

### Remember

> **The biggest advantage is that Java provides reusable, tested collection implementations instead of forcing programmers to build common data structures themselves.**

---

# 4. Collections vs Arrays

## LEVEL 1 — What is an Array?

An array stores multiple values under one variable.

```java
int a[] = {10, 20, 30};
```

It has a fixed length.

```java
System.out.println(a.length);
```

Output:

```text
3
```

---

## What is a Collection?

Example:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

The collection provides methods for managing the elements.

```java
al.size();
```

---

# LEVEL 2 — Major differences

| Feature          | Array                 | Collection                                   |
| ---------------- | --------------------- | -------------------------------------------- |
| Nature           | Java language feature | Collections Framework                        |
| Size             | Fixed                 | Many implementations dynamically manage size |
| Add operation    | No `add()`            | Collection APIs provide addition             |
| Remove operation | No general `remove()` | Collection APIs provide removal              |
| Size             | `length`              | `size()`                                     |
| Structures       | Array                 | List, Set, Queue, Map                        |
| Flexibility      | Comparatively limited | More flexible                                |
| Algorithms       | `Arrays`              | `Collections`                                |

---

## Example

### Array

```java
class ArrayDemo
{
    public static void main(String[] args)
    {
        int a[] = new int[3];

        a[0] = 10;
        a[1] = 20;
        a[2] = 30;

        System.out.println(a.length);
    }
}
```

### Output

```text
3
```

---

### Collection

```java
import java.util.*;

class CollectionDemo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);
        al.add(40);

        System.out.println(al.size());
    }
}
```

### Output

```text
4
```

---

# LEVEL 3 — When should we choose Array or Collection?

Think about the requirement.

### Use an Array when:

```text
Known/fixed number of elements
        +
Simple indexed storage
        +
Array behavior is sufficient
```

Example:

```java
int months[] = new int[12];
```

The number of months is fixed.

---

### Use a Collection when:

```text
Number of elements may change
        +
Need add/remove/search operations
        +
Need specialized structures
        +
Need framework algorithms
```

### Golden comparison

```text
ARRAY
 ↓
Fixed-size structure

COLLECTION
 ↓
Flexible framework of data structures
```

---

# 5. Collection Framework

## LEVEL 1 — What is it?

The **Java Collections Framework (JCF)** is a unified architecture for storing and manipulating groups of objects.

It contains:

```text
Interfaces
+
Implementation classes
+
Algorithms
+
Utility classes
```

---

# LEVEL 2 — What does the framework contain?

### Interfaces

```text
Iterable
Collection
List
Set
Queue
Deque
Map
```

### Implementation classes

```text
ArrayList
LinkedList
Vector
Stack

HashSet
LinkedHashSet
TreeSet

PriorityQueue
ArrayDeque

HashMap
LinkedHashMap
TreeMap
Hashtable
```

### Utility classes

```text
Collections
Arrays
```

### Algorithms

```text
sort()
reverse()
shuffle()
max()
min()
binarySearch()
```

---

# LEVEL 3 — Why is it called a Framework?

Because these aren't random, unrelated classes.

They are organized into a common architecture.

For example:

```text
List
 |
 +-- ArrayList
 |
 +-- LinkedList
 |
 +-- Vector
```

The interface defines common behavior, while implementation classes provide concrete behavior.

This gives us:

```text
Common design
      +
Reusable classes
      +
Standard methods
      +
Ready-made algorithms
```

---

# 6. Collection Hierarchy

This is the most important structural concept.

## LEVEL 1 — Basic hierarchy

Start with:

```text
Iterable
   |
Collection
   |
   +-- List
   +-- Set
   +-- Queue
```

And:

```text
Queue
   |
  Deque
```

---

# LEVEL 2 — Implementation hierarchy

## List

```text
List
 |
 +-- ArrayList
 |
 +-- LinkedList
 |
 +-- Vector
      |
     Stack
```

### List characteristics

Generally:

```text
Ordered
Duplicates allowed
Index-based operations
```

Example:

```text
[10, 20, 10, 30]
```

---

## Set

```text
Set
 |
 +-- HashSet
 |
 +-- LinkedHashSet
 |
 +-- TreeSet
```

### Set characteristic

```text
Duplicate elements are not allowed
```

Different implementations have different ordering behavior.

```text
HashSet
    ↓
No guaranteed iteration order

LinkedHashSet
    ↓
Insertion order

TreeSet
    ↓
Sorted order
```

---

## Queue

```text
Queue
 |
 +-- PriorityQueue
 |
 `-- Deque
```

---

## Deque

Deque means:

> **Double Ended Queue**

```text
Deque
 |
 +-- ArrayDeque
 |
 `-- LinkedList
```

`LinkedList` can be used as both a List and a Deque.

---

# LEVEL 3 — Where does Map belong?

This is a major exam/interview question.

### Incorrect:

```text
Collection
    |
   Map
```

❌ **Wrong.**

### Correct:

```text
          Java Collections Framework
                    |
          __________|__________
         |                     |
    Collection                Map
         |                     |
    List/Set/Queue        Key → Value
```

`Map` is part of the Collections Framework, but **Map does not extend Collection**.

---

## Map hierarchy

```text
Map
 |
 +-- HashMap
 |
 +-- LinkedHashMap
 |
 +-- TreeMap
 |
 +-- Hashtable
 |
 +-- ConcurrentHashMap
 |
 `-- WeakHashMap
```

Example:

```text
101 → Ravi
102 → John
103 → Kiran
```

The left side is the key.

The right side is the value.

---

# Complete 3-Level Mental Map

```text
LEVEL 1
What?
   ↓
Collection = group of objects
```

```text
LEVEL 2
How?
   ↓
Framework provides interfaces,
classes, methods and algorithms
```

```text
LEVEL 3
Why?
   ↓
Choose the appropriate structure
according to the problem
```

---

# 🔥 COMPLETE HIERARCHY TO MEMORIZE

```text
                    JAVA COLLECTIONS FRAMEWORK
                               |
                _______________|_______________
               |                               |
          Collection                          Map
               |                               |
       ________|________                  Key → Value
      |        |        |
     List     Set      Queue
      |        |        |
      |        |       Deque
      |        |
      |        +-- HashSet
      |        +-- LinkedHashSet
      |        `-- TreeSet
      |
      +-- ArrayList
      +-- LinkedList
      +-- Vector
           |
          Stack

Queue
  |
Deque
  |
  +-- ArrayDeque
  `-- LinkedList
```

---

# 🚨 3LEVEL QUICK REVISION

| Topic               | LEVEL 1                | LEVEL 2                               | LEVEL 3                              |
| ------------------- | ---------------------- | ------------------------------------- | ------------------------------------ |
| Collection          | Group of objects       | Interface providing common operations | Used to manage object groups         |
| Need                | Store multiple objects | Add/remove/search/manage              | Solve real application requirements  |
| Advantages          | Convenience            | Ready-made structures/methods         | Reuse instead of building everything |
| Array vs Collection | Array is fixed-size    | Collection has framework APIs         | Choose based on requirement          |
| Framework           | Complete architecture  | Interfaces + classes + algorithms     | Provides standardized solutions      |
| Hierarchy           | Collection branches    | List/Set/Queue + implementations      | Choose implementation by behavior    |

---

# ⭐ FINAL DOUBT KILLER

### `Collection` vs `Collections`

```text
Collection
   ↓
Interface

Collections
   ↓
Utility class
```

### `Collection Framework` vs `Collection`

```text
Collection Framework
        ↓
Entire architecture

Collection
        ↓
One interface within that architecture
```

### `Array` vs `ArrayList`

```text
Array
 ↓
Fixed length

ArrayList
 ↓
Collection Framework implementation
```

### `List` vs `ArrayList`

```text
List
 ↓
Interface

ArrayList
 ↓
Class / implementation
```

### `Set` vs `HashSet`

```text
Set
 ↓
Interface

HashSet
 ↓
Implementation class
```

### `Map` vs `HashMap`

```text
Map
 ↓
Interface

HashMap
 ↓
Implementation class
```

### Is Map a Collection?

**No.**

### Is Map part of the Collections Framework?

**Yes.**

### Can a List contain duplicates?

**Yes.**

### Can a Set contain duplicates?

**No.**

### Does every Collection maintain insertion order?

**No.**

### Does an Array have `size()`?

**No.**

```text
Array → length
Collection → size()
```

### Does ArrayList have `length`?

**No.**

```text
ArrayList → size()
```

---

## 🧠 One sentence to remember the entire chapter

> **The Java Collections Framework is a unified architecture that provides different interfaces and implementation classes for storing and manipulating groups of objects, allowing us to choose structures such as List, Set, Queue, Deque, and Map according to our requirement.**
