# 1. Introduction to Collections in Java — DEEPDIVE

> **Training rule for this entire Collections Framework:** We will learn the framework using **normal/raw collection programs**. No Generics syntax such as `ArrayList<Integer>` or `HashMap<Integer, String>` will be used in these examples. Generics will be handled separately later.

---

# 1. Collection Definition

## 1.1 What is a Collection?

A **collection** is an object that represents a group of objects as a single unit.

Suppose we want to store the marks of five students.

Without a collection:

```java
int mark1 = 80;
int mark2 = 75;
int mark3 = 90;
int mark4 = 65;
int mark5 = 88;
```

Each value requires a separate variable.

Instead, we can use a collection:

```java
ArrayList al = new ArrayList();

al.add(80);
al.add(75);
al.add(90);
al.add(65);
al.add(88);
```

Now all five values are managed through one object.

### Simple definition

> **A Collection is an object used to store and manipulate a group of objects as a single unit.**

---

## 1.2 Why do we call it an object?

Consider:

```java
ArrayList al = new ArrayList();
```

Here:

```text
ArrayList
    ↓
Class
```

and:

```text
al
 ↓
Object/reference
```

The `ArrayList` object internally manages the elements that we add to it.

Therefore, we don't directly manage individual memory locations as we do with a simple array.

---

## 1.3 Collection is both a concept and an interface

This creates an important terminology issue.

### Collection as a general concept

When someone says:

> "Use a collection to store these values."

they may be referring generally to the idea of a data structure that stores multiple objects.

### Collection as a Java interface

Java also has an interface literally named:

```java
Collection
```

It belongs to:

```java
java.util
```

For example:

```java
Collection c = new ArrayList();
```

Here `Collection` is specifically the Java interface.

So:

```text
collection
   ↓
General concept

Collection
   ↓
Specific Java interface
```

Capitalization matters.

---

## 1.4 Collection interface

The `Collection` interface provides common operations that are applicable to many collection types.

For example:

```java
Collection c = new ArrayList();

c.add(10);
c.add(20);
c.add(30);

System.out.println(c);
```

Output:

```text
[10, 20, 30]
```

The same common interface can be used with different implementations.

For example:

```java
Collection c = new ArrayList();
```

or:

```java
Collection c = new HashSet();
```

The underlying behavior differs because the implementation differs.

---

## 1.5 Collection program

```java
import java.util.*;

class CollectionDemo
{
    public static void main(String[] args)
    {
        Collection c = new ArrayList();

        c.add(10);
        c.add(20);
        c.add(30);

        System.out.println(c);
    }
}
```

### Output

```text
[10, 20, 30]
```

### Program explanation

```java
import java.util.*;
```

Imports the collection classes and interfaces from `java.util`.

```java
Collection c = new ArrayList();
```

A `Collection` reference refers to an `ArrayList` object.

```java
c.add(10);
```

Adds the value `10`.

Similarly:

```java
c.add(20);
c.add(30);
```

add the remaining values.

Finally:

```java
System.out.println(c);
```

prints the collection.

---

# 2. Need for Collections

To understand why Collections Framework was introduced, first understand the limitations of arrays.

---

## 2.1 Storing multiple values without an array

Suppose:

```java
int a = 10;
int b = 20;
int c = 30;
int d = 40;
```

This becomes inconvenient when the number of values increases.

We need a mechanism to represent:

```text
10
20
30
40
50
60
...
```

as one logical group.

An array solves this partially.

---

## 2.2 Array solution

```java
int a[] = new int[5];

a[0] = 10;
a[1] = 20;
a[2] = 30;
a[3] = 40;
a[4] = 50;
```

This is better because one variable represents multiple values.

But arrays have limitations.

---

## 2.3 Major problem: fixed size

Suppose:

```java
int a[] = new int[5];
```

The array can store five elements.

What if we suddenly need six?

The array itself cannot simply become:

```text
5 elements → 6 elements
```

We would need to create another array and copy the values.

Conceptually:

```text
Old array
[10][20][30][40][50]
          ↓
      create new array
          ↓
[10][20][30][40][50][60]
```

This becomes inconvenient when the size changes frequently.

---

## 2.4 Collections provide dynamic management

Consider:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
al.add(40);
al.add(50);
al.add(60);
```

We didn't specify:

```java
new ArrayList(5)
```

and then become permanently restricted to five elements.

The implementation can manage its internal storage as elements are added.

### Output

```text
[10, 20, 30, 40, 50, 60]
```

---

## 2.5 Need for operations

With arrays, common operations often require us to write additional logic.

Collections provide standard operations such as:

```java
add()
remove()
contains()
size()
clear()
```

For example:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);

System.out.println(al.contains(20));

al.remove(20);

System.out.println(al);
```

Output:

```text
true
[10, 30]
```

---

## 2.6 Need for different data structures

Not every problem requires the same storage behavior.

Suppose we need:

### Ordered group with duplicates

Use:

```text
List
```

### Unique elements

Use:

```text
Set
```

### Processing elements according to queue rules

Use:

```text
Queue
```

### Key-value relationships

Use:

```text
Map
```

Therefore, the Collections Framework provides **different data structures for different requirements**.

---

# 3. Advantages of Collections

The Collections Framework provides many advantages.

---

## 3.1 Dynamic size

Many collection implementations can dynamically manage their storage.

Example:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
al.add(40);
```

The programmer doesn't have to manually create a larger array every time another element is required.

---

## 3.2 Ready-made data structures

Java provides several implementations.

```text
List
 |
 +-- ArrayList
 +-- LinkedList
 +-- Vector
 +-- Stack

Set
 |
 +-- HashSet
 +-- LinkedHashSet
 +-- TreeSet

Queue
 |
 +-- PriorityQueue
 +-- Deque

Map
 |
 +-- HashMap
 +-- LinkedHashMap
 +-- TreeMap
 +-- Hashtable
```

We can select one according to our requirement.

---

## 3.3 Ready-made methods

For example:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

We can perform operations directly:

```java
al.remove(20);
```

```java
al.contains(10);
```

```java
al.size();
```

Instead of repeatedly implementing these operations ourselves.

---

## 3.4 Standardized architecture

Different collection implementations follow common interfaces.

For example:

```text
Collection
     |
     +-------- ArrayList
     |
     +-------- HashSet
     |
     +-------- LinkedList
```

This provides a common programming model.

---

## 3.5 Multiple implementation choices

Suppose two applications both need to store multiple elements.

Application A might need:

```text
Fast indexed access
        ↓
ArrayList
```

Application B might need:

```text
Unique elements
        ↓
HashSet
```

Application C might need:

```text
Sorted unique elements
        ↓
TreeSet
```

The framework gives us choices instead of forcing one data structure for every problem.

---

## 3.6 Ready-made algorithms

Java also provides utility classes containing algorithms.

For example:

```java
Collections.sort();
Collections.reverse();
Collections.shuffle();
Collections.max();
Collections.min();
```

This means common operations do not have to be implemented from scratch every time.

---

## 3.7 Better code reuse

A developer can use a standard collection implementation instead of creating a custom data structure for every common requirement.

This reduces development effort and makes code easier to understand.

---

# 4. Collections vs Arrays

This is one of the most important comparisons.

---

## 4.1 Array

An array is a Java language feature used to store multiple values of the same array component type.

Example:

```java
int a[] = {10, 20, 30};
```

The array has a fixed length.

```java
System.out.println(a.length);
```

Output:

```text
3
```

---

## 4.2 Collection

A collection is an object from the Collections Framework used to manage a group of objects.

Example:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

We can ask for the number of elements using:

```java
al.size();
```

---

## 4.3 Detailed comparison

| Feature              | Array                                                             | Collection                              |
| -------------------- | ----------------------------------------------------------------- | --------------------------------------- |
| Part of              | Java language                                                     | Collections Framework                   |
| Size                 | Fixed after creation                                              | Many implementations dynamically resize |
| Stores               | Primitives and object references                                  | Objects                                 |
| Access               | Index-based for ordinary arrays                                   | Depends on collection                   |
| Add operation        | No `add()` method                                                 | Collection APIs provide addition        |
| Remove operation     | No `remove()` method                                              | Collection APIs provide removal         |
| Size                 | `length`                                                          | `size()`                                |
| Data structures      | Array                                                             | List, Set, Queue etc.                   |
| Duplicate handling   | Depends on stored values; array itself imposes no uniqueness rule | Depends on implementation               |
| Ordering             | Index order                                                       | Depends on implementation               |
| Sorting              | `Arrays.sort()`                                                   | `Collections.sort()` for Lists          |
| Searching            | Usually through code or `Arrays` utility                          | Collection-specific methods/utilities   |
| Framework interfaces | No                                                                | Yes                                     |
| Flexibility          | Comparatively limited                                             | More choices                            |

---

## 4.4 Array program

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

## 4.5 Collection program

```java
import java.util.*;

class CollectionSizeDemo
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

Notice that we added four elements without declaring:

```java
new ArrayList(4)
```

as a fixed limit.

---

## 4.6 `length` vs `size()`

This is a common beginner doubt.

### Array

```java
a.length
```

`length` is a field.

### Collection

```java
al.size()
```

`size()` is a method.

Therefore:

```text
Array       → length
Collection  → size()
```

---

## 4.7 Can Collections store primitive values?

This question is important under our current **normal/raw syntax**.

A Java Collection stores **objects**, not primitive values.

For example:

```java
ArrayList al = new ArrayList();

al.add(10);
```

Here `10` is written as an `int`, but Java automatically converts the primitive value to the corresponding wrapper object.

Conceptually:

```text
int 10
   ↓
Integer object
   ↓
stored in collection
```

This automatic conversion is called **autoboxing**.

Similarly, when retrieving the value, Java can automatically convert the wrapper object back to a primitive when required. This is called **unboxing**.

We are mentioning this only to explain normal collection behavior; **Generics are not being introduced here.**

---

# 5. Collection Framework

## 5.1 Definition

The **Java Collections Framework (JCF)** is a unified architecture for representing and manipulating collections of objects.

It provides:

```text
Interfaces
     +
Implementations
     +
Algorithms
     +
Utility classes
```

---

# 5.2 Why was a framework needed?

Imagine Java had only individual unrelated classes:

```text
SomeListClass
SomeSetClass
SomeQueueClass
SomeMapClass
```

Each could have completely different method names.

Programming would become unnecessarily difficult.

Instead, Java provides common interfaces.

For example:

```text
Collection
    |
    +-- List
    |
    +-- Set
    |
    +-- Queue
```

This gives different classes a common conceptual architecture.

---

# 5.3 Main parts of the Collections Framework

```text
Java Collections Framework
│
├── Interfaces
│
├── Implementing Classes
│
├── Algorithms
│
└── Utility Classes
```

Let's understand each.

---

## 5.4 Interfaces

Interfaces define contracts.

Important interfaces include:

```text
Iterable
Collection
List
Set
Queue
Deque
Map
```

For example:

```java
List list;
```

The reference type represents the List contract.

---

## 5.5 Implementation classes

Classes provide concrete implementations.

Examples:

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
ConcurrentHashMap
WeakHashMap
```

Example:

```java
List list = new ArrayList();
```

Conceptually:

```text
List
 ↓
contract

ArrayList
 ↓
implementation
```

---

# 5.6 Algorithms

The framework also provides algorithms.

For example:

```java
Collections.sort(list);
```

Other operations include:

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

These will be studied separately later.

---

# 5.7 Utility classes

Two important utility classes in our roadmap are:

```text
Collections
Arrays
```

### `Collections`

Used primarily for operations on collections, especially Lists.

Example:

```java
Collections.sort(al);
```

### `Arrays`

Used for operations on arrays.

Example:

```java
Arrays.sort(a);
```

This gives us an important distinction:

```text
Array
  ↓
Arrays utility class

Collection/List
  ↓
Collections utility class
```

---

# 5.8 `Collection` vs `Collections`

This causes enormous confusion among beginners.

### `Collection`

```java
Collection
```

is an **interface**.

### `Collections`

```java
Collections
```

is a **utility class**.

They are different.

```text
Collection
    ↓
Interface

Collections
    ↓
Utility class
```

Example:

```java
Collection c = new ArrayList();
```

versus:

```java
Collections.sort(al);
```

---

# 6. Collection Hierarchy

Now we can understand how all these components are connected.

---

## 6.1 Highest-level relationship

The simplified hierarchy begins with:

```text
Iterable
   |
Collection
```

Then:

```text
Collection
   |
   +-- List
   +-- Set
   +-- Queue
```

And `Deque` is a subinterface of `Queue`.

---

# 6.2 Complete conceptual tree

```text
                         Iterable
                            |
                       Collection
                            |
          _________________|_________________
         |                 |                 |
        List               Set              Queue
         |                 |                 |
     ____|____          ___|___          ____|____
    |    |    |        |   |   |        |         |
ArrayList LinkedList Vector HashSet LinkedHashSet TreeSet PriorityQueue Deque
    |             |                             
    |            Stack
    |
    |
   Deque
      |
  ArrayDeque
```

However, there is an important inheritance detail:

```text
LinkedList
```

implements both:

```text
List
Deque
```

So the conceptual hierarchy is better shown as:

```text
Iterable
   |
Collection
   |
   +----------------+----------------+
   |                |                |
  List              Set             Queue
   |                |                |
   |                |          PriorityQueue
   |                |
   |                +-- HashSet
   |                +-- LinkedHashSet
   |                `-- TreeSet
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
  |
  `-- LinkedList
```

---

# 6.3 Where does Map belong?

This is **one of the most important points in the entire Collections Framework**.

Many beginners draw:

```text
Collection
   |
   +-- Map
```

That is incorrect.

`Map` is **not a subtype of `Collection`**.

Instead:

```text
Java Collections Framework
│
├── Collection hierarchy
│
└── Map hierarchy
```

So:

```text
              Collections Framework
                       |
             __________|__________
            |                     |
       Collection                Map
            |                     |
       List/Set/Queue       HashMap/TreeMap/...
```

---

# 6.4 Map hierarchy

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

Map stores data in the form:

```text
Key → Value
```

Example:

```text
101 → "Ravi"
102 → "John"
103 → "Kiran"
```

We will study Map separately in Topic 8.

---

# 6.5 Why is Map separate?

A `Collection` generally represents a group of individual elements:

```text
10
20
30
40
```

A `Map` represents relationships between two things:

```text
101 → Ravi
102 → John
103 → Kiran
```

Therefore, their APIs and contracts are different.

A Map doesn't use the Collection interface as its parent.

---

# 6.6 Full framework tree

For our complete training roadmap:

```text
JAVA COLLECTIONS FRAMEWORK
│
├── Iterable
│
├── Collection
│   │
│   ├── List
│   │   ├── ArrayList
│   │   ├── LinkedList
│   │   ├── Vector
│   │   └── Stack
│   │
│   ├── Set
│   │   ├── HashSet
│   │   ├── LinkedHashSet
│   │   └── TreeSet
│   │
│   └── Queue
│       ├── PriorityQueue
│       └── Deque
│           ├── ArrayDeque
│           └── LinkedList
│
├── Map
│   ├── HashMap
│   ├── LinkedHashMap
│   ├── TreeMap
│   ├── Hashtable
│   ├── ConcurrentHashMap
│   └── WeakHashMap
│
├── Traversal
│   ├── Iterator
│   ├── ListIterator
│   └── Enumeration
│
├── Ordering
│   ├── Comparable
│   └── Comparator
│
├── Utility Classes
│   ├── Collections
│   └── Arrays
│
└── Advanced Concepts
    ├── Concurrent Collections
    ├── Fail-fast
    ├── Weakly consistent iteration
    ├── Synchronization
    ├── Unmodifiable Collections
    └── Performance
```

---

# 7. One Program Connecting the Framework Concepts

Let's write one normal/raw program that demonstrates the idea of choosing a collection.

```java
import java.util.*;

class FrameworkIntroduction
{
    public static void main(String[] args)
    {
        List list = new ArrayList();

        list.add("Java");
        list.add("Python");
        list.add("Java");

        Set set = new HashSet();

        set.add("Java");
        set.add("Python");
        set.add("Java");

        System.out.println("List : " + list);
        System.out.println("Set  : " + set);
    }
}
```

### Output

```text
List : [Java, Python, Java]
Set  : [Java, Python]
```

> The exact order displayed by a `HashSet` is not guaranteed. The important observation is that the duplicate `"Java"` is not retained as a second distinct Set element.

---

## Program explanation

### Step 1

```java
List list = new ArrayList();
```

We choose `ArrayList` because we want List behavior.

### Step 2

```java
list.add("Java");
list.add("Python");
list.add("Java");
```

A List normally permits duplicates.

Therefore:

```text
Java
Python
Java
```

are retained.

### Step 3

```java
Set set = new HashSet();
```

We choose a Set because we don't want duplicate elements.

### Step 4

```java
set.add("Java");
set.add("Python");
set.add("Java");
```

The second `"Java"` does not become another distinct Set element.

---

# 8. The Core Idea Behind the Entire Framework

The most important thing to understand from this chapter is:

> **Don't choose a collection because its name sounds familiar. Choose it according to the requirement.**

Think like this:

```text
What do I need?
       |
       +-- Ordered + duplicates
       |       ↓
       |      List
       |
       +-- Unique elements
       |       ↓
       |      Set
       |
       +-- Processing/priority
       |       ↓
       |      Queue
       |
       +-- Both ends
       |       ↓
       |      Deque
       |
       `-- Key → Value
               ↓
              Map
```

Then select the appropriate implementation.

---

# 9. Complete Summary of the Six Sub-Concepts

## 1. Collection Definition

```text
Collection
    ↓
Group of objects
    ↓
Managed as one unit
```

---

## 2. Need for Collections

Collections are needed because applications frequently need to:

* store groups of objects;
* dynamically add/remove elements;
* search elements;
* process elements;
* maintain different ordering rules;
* enforce uniqueness;
* associate keys with values.

---

## 3. Advantages

```text
Collections
    |
    +-- Dynamic management
    +-- Ready-made structures
    +-- Standard methods
    +-- Reusable algorithms
    +-- Multiple implementations
    +-- Common interfaces
    `-- Better code reuse
```

---

## 4. Collections vs Arrays

```text
Array
  ↓
Fixed-size structure

Collection
  ↓
Framework of data structures
  ↓
Flexible operations
```

---

## 5. Collection Framework

```text
Framework
    |
    +-- Interfaces
    +-- Implementations
    +-- Algorithms
    `-- Utility classes
```

---

## 6. Collection Hierarchy

```text
Iterable
   |
Collection
   |
   +-- List
   +-- Set
   `-- Queue
        |
       Deque

Map
   |
   +-- HashMap
   +-- LinkedHashMap
   +-- TreeMap
   +-- Hashtable
   +-- ConcurrentHashMap
   `-- WeakHashMap
```

---

# ⭐ DEEPDIVE FINAL DOUBT KILLER

### Is Collection a class?

**No.** `Collection` is an interface.

### Is Collections a class?

**Yes.** `Collections` is a utility class.

### Is Collection Framework the same as Collection interface?

**No.**

```text
Collection Framework
       ↓
Complete architecture

Collection
       ↓
One interface inside that architecture
```

### Is Map a Collection?

**No.** Map is a separate branch within the Collections Framework.

### Are arrays collections?

**No.** Arrays are Java language constructs, not implementations of the Collections Framework.

### Can a Collection store primitive values directly?

**No.** Collection implementations store objects. Primitive values such as `int` are automatically boxed when passed to collection methods such as `add()`.

### Is ArrayList the Collection Framework?

**No.**

```text
Collection Framework
       ↓
contains many interfaces/classes
       ↓
ArrayList is only one implementation
```

### Is List a class?

**No.** `List` is an interface.

### Is ArrayList an interface?

**No.** `ArrayList` is a class implementing `List`.

### Does every Collection maintain insertion order?

**No.** Ordering depends on the particular collection implementation.

### Does every Set sort elements?

**No.**

```text
HashSet       → no guaranteed order
LinkedHashSet → insertion order
TreeSet       → sorted order
```

### Does every collection allow duplicates?

**No.** Duplicate behavior depends on the collection type.

### What should I remember above everything else?

```text
                    COLLECTIONS FRAMEWORK
                            |
             _______________|_______________
            |                               |
       Collection                         Map
            |                               |
       _____|_____                    Key → Value
      |     |     |
     List  Set   Queue
             |
       Different
       implementations
```

**Understand the contract first → understand the implementation second → then choose the collection according to the requirement.**

And throughout this chapter, **no Generics concept has been used.**
