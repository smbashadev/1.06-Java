# 1. Introduction to Collections in Java — TEACHME

> **Training rule:** We are learning the Collections Framework with **normal/raw collection programs**. No Generic syntax such as `ArrayList<Integer>` is used. Generics will be learned separately.

---

# First: What Are We Trying to Learn?

Imagine you are developing a student-management application.

You have:

```text
Student 1
Student 2
Student 3
Student 4
Student 5
...
```

You need somewhere to store all these objects.

You have two major choices:

```text
             Store multiple values
                    |
             _______________
            |               |
          Array          Collection
```

Today we are learning **why Java provides Collections and how the Collections Framework is organized.**

We will learn six things:

```text
1. What is a Collection?
2. Why do we need Collections?
3. What are their advantages?
4. Collections vs Arrays
5. What is the Collection Framework?
6. What is the Collection hierarchy?
```

Let's build the understanding step by step.

---

# PART 1 — Collection Definition

## Step 1: Start with normal variables

Suppose I ask you to store three student marks.

You could write:

```java
int mark1 = 80;
int mark2 = 90;
int mark3 = 70;
```

The problem is obvious.

What if there are:

```text
100 students?
10,000 students?
1,000,000 students?
```

Creating individual variables is not practical.

---

## Step 2: Use an array

We can group values:

```java
int marks[] = {80, 90, 70};
```

Now one variable represents multiple values.

But arrays have limitations.

We'll come to those in a moment.

---

## Step 3: Use a Collection

Java provides collection classes for managing groups of objects.

For example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(80);
        al.add(90);
        al.add(70);

        System.out.println(al);
    }
}
```

### Output

```text
[80, 90, 70]
```

Instead of manually managing individual values, the `ArrayList` object manages the group.

---

## What is a Collection?

### Definition

> **A Collection is an object that represents a group of objects as a single unit and provides operations for managing those objects.**

Think:

```text
Many objects
     ↓
One collection object
     ↓
Manage them together
```

---

# PART 2 — Need for Collections

Now let's understand **why Java needs Collections**.

Imagine you are maintaining a list of employee names.

Initially:

```text
Ravi
John
Kiran
```

Tomorrow:

```text
Ravi
John
Kiran
Rahul
```

Next week:

```text
Ravi
John
Kiran
Rahul
Anil
Priya
...
```

The number of elements can change.

This creates a major requirement:

> **We need a data structure capable of managing a changing number of objects conveniently.**

---

## Problem 1 — Fixed size of arrays

Suppose:

```java
int a[] = new int[3];
```

The array has three positions:

```text
+----+----+----+
| 10 | 20 | 30 |
+----+----+----+
  0    1    2
```

Now suppose you want to add `40`.

The array doesn't automatically become:

```text
+----+----+----+----+
| 10 | 20 | 30 | 40 |
+----+----+----+----+
```

You need another array and copying logic.

Collections make dynamic management much easier.

---

## Problem 2 — Adding elements

With a collection:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
al.add(40);
```

The `add()` method is already provided.

---

## Problem 3 — Removing elements

Suppose:

```text
10
20
30
40
```

We want to remove `20`.

With an `ArrayList`:

```java
al.remove(20);
```

The collection provides the operation.

---

## Problem 4 — Searching

Suppose we want to know:

> "Does 30 exist?"

We can use:

```java
al.contains(30);
```

It returns:

```text
true
```

or:

```text
false
```

---

## Problem 5 — Different requirements

This is perhaps the biggest reason for the Collections Framework.

Different applications have different requirements.

### Requirement A

> "I want duplicate elements."

Think:

```text
List
```

### Requirement B

> "I don't want duplicates."

Think:

```text
Set
```

### Requirement C

> "I want elements processed according to queue rules."

Think:

```text
Queue
```

### Requirement D

> "I want key-value relationships."

Think:

```text
Map
```

So Java gives us **different collection types for different requirements**.

---

# PART 3 — Advantages of Collections

Now let's understand why Collections are useful.

---

## Advantage 1 — Dynamic size

Many collection implementations can grow and shrink as elements are added or removed.

Example:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
al.add(40);
al.add(50);
```

We don't have to manually create a larger array whenever another element is needed.

---

# Advantage 2 — Ready-made classes

Java has already provided many data structures.

For example:

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

We don't have to implement all these data structures ourselves.

---

# Advantage 3 — Ready-made methods

Collections provide methods for common operations.

For example:

```java
al.add(10);
al.remove(10);
al.contains(20);
al.size();
al.clear();
```

Instead of writing our own implementation for each operation.

---

# Advantage 4 — Different data structures

Suppose I say:

> "I need to store unique elements."

You can choose:

```text
Set
```

Then:

> "I need them sorted."

You can choose:

```text
TreeSet
```

Then:

> "I need insertion order."

You might choose:

```text
LinkedHashSet
```

So the framework gives us choices.

---

# Advantage 5 — Reusable algorithms

Java provides the `Collections` utility class.

It contains operations such as:

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

Example:

```java
Collections.sort(al);
```

The sorting algorithm doesn't have to be written from scratch for every program.

---

# Advantage 6 — Common architecture

Many collection classes implement common interfaces.

For example:

```text
             List
              |
       +------+------+
       |             |
   ArrayList      LinkedList
```

So we can learn the `List` concept and then apply that knowledge to multiple implementations.

---

# PART 4 — Collections vs Arrays

This is a very important topic.

Let's compare them carefully.

---

## First understand Array

An array is a Java language feature.

Example:

```java
int a[] = {10, 20, 30};
```

It stores multiple values under one variable.

---

## Now understand Collection

A collection is part of the Java Collections Framework.

Example:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

---

## Difference 1 — Size

### Array

```java
int a[] = new int[3];
```

Size:

```text
3
```

The length is fixed after creation.

### Collection

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
al.add(40);
```

The implementation manages its storage as elements are added.

---

## Difference 2 — Size checking

Array:

```java
a.length
```

Collection:

```java
al.size()
```

Remember:

```text
Array       → length
Collection  → size()
```

`length` is a field, while `size()` is a method.

---

## Difference 3 — Adding elements

Array:

```java
a[0] = 10;
a[1] = 20;
```

Collection:

```java
al.add(10);
al.add(20);
```

---

## Difference 4 — Removing elements

Arrays don't provide a general `remove()` method.

Collections provide removal operations.

```java
al.remove(10);
```

---

## Difference 5 — Different data structures

An array gives you an array.

The Collections Framework gives you:

```text
List
Set
Queue
Deque
Map
```

with many implementations.

---

## Complete comparison

| Feature                          | Array                                  | Collection                                   |
| -------------------------------- | -------------------------------------- | -------------------------------------------- |
| What is it?                      | Java language feature                  | Collections Framework                        |
| Size                             | Fixed                                  | Many implementations dynamically manage size |
| Add method                       | No                                     | Yes, depending on collection                 |
| Remove method                    | No general `remove()`                  | Yes, depending on collection                 |
| Size operation                   | `length`                               | `size()`                                     |
| Data structures                  | Array                                  | List, Set, Queue, Map etc.                   |
| Flexibility                      | Lower                                  | Higher                                       |
| Ready-made collection operations | Limited                                | Extensive                                    |
| Algorithms                       | `Arrays` utility class                 | `Collections` utility class                  |
| Stores                           | Primitive values and object references | Objects                                      |

---

# Important Question

## Can Collections store `int`?

You may see:

```java
ArrayList al = new ArrayList();

al.add(10);
```

You might ask:

> "But `10` is an `int`. Doesn't a Collection store objects?"

Yes, collections store objects.

Java automatically boxes the primitive:

```text
int
 ↓
Integer object
 ↓
Collection
```

This automatic conversion is called **autoboxing**.

We are **not introducing Generics here**.

---

# PART 5 — Collection Framework

Now we understand individual collections.

But Java doesn't simply provide random collection classes.

They are organized into a complete architecture.

That architecture is called:

# Java Collections Framework

### Definition

> **The Java Collections Framework is a unified architecture consisting of interfaces, implementations, algorithms and utility classes for storing and manipulating groups of objects.**

Think of it as a large family:

```text
        JAVA COLLECTIONS FRAMEWORK
                    |
       ______________|______________
      |              |              |
 Interfaces    Implementations   Utilities
```

---

# Why call it a Framework?

Because Java gives us a complete structure.

Instead of writing:

```text
My own List
My own Set
My own Queue
My own Map
My own sorting algorithm
My own searching algorithm
```

Java gives us standardized solutions.

---

# Main Parts

## 1. Interfaces

Important interfaces:

```text
Iterable
Collection
List
Set
Queue
Deque
Map
```

Interfaces define the contracts.

---

## 2. Implementation classes

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
```

Classes provide concrete implementations.

---

## 3. Algorithms

Java provides utility algorithms such as:

```text
sort()
reverse()
shuffle()
max()
min()
binarySearch()
```

---

## 4. Utility classes

Two important utility classes in our roadmap are:

```text
Collections
Arrays
```

Remember the difference:

```text
Collection
    ↓
Interface

Collections
    ↓
Utility class
```

---

# PART 6 — Collection Hierarchy

Now comes the part students frequently get confused about.

Let's build the hierarchy slowly.

---

# Level 1 — Iterable

At a high level:

```text
Iterable
   |
Collection
```

`Iterable` provides the ability to obtain an iterator and supports the enhanced `for` loop mechanism.

We will study `Iterable` separately.

---

# Level 2 — Collection

Under `Collection`, we have major branches:

```text
             Collection
            /    |     \
          List  Set   Queue
```

These interfaces represent different collection behaviors.

---

# Level 3 — List

Under List:

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

### List generally means:

* ordered collection;
* duplicates are permitted;
* index-based operations are available.

Example:

```text
[10, 20, 10, 30]
```

The duplicate `10` can exist.

---

# Level 4 — Set

Under Set:

```text
Set
 |
 +-- HashSet
 |
 +-- LinkedHashSet
 |
 `-- TreeSet
```

Set represents collections that do not allow duplicate elements according to the Set contract.

---

# Level 5 — Queue

Under Queue:

```text
Queue
 |
 +-- PriorityQueue
 |
 `-- Deque
```

Queue is designed around elements waiting for processing.

---

# Level 6 — Deque

Deque means:

> **Double Ended Queue**

It supports insertion/removal operations at both ends.

```text
          Deque
         /     \
        /       \
ArrayDeque    LinkedList
```

`LinkedList` can participate in both List and Deque roles.

---

# What About Map?

STOP HERE — this is a very important point.

Many beginners draw:

```text
Collection
    |
   Map
```

❌ **Wrong.**

The correct idea is:

```text
Java Collections Framework
          |
     _____|_____
    |           |
Collection     Map
```

`Map` is a separate branch.

---

# Map Hierarchy

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

Map stores:

```text
Key → Value
```

Example:

```text
101 → Ravi
102 → John
103 → Kiran
```

We'll study Map in detail later.

---

# Let's Build the Whole Picture

Here is the mental picture I want you to remember:

```text
                 JAVA COLLECTIONS FRAMEWORK
                            |
              ______________|______________
             |                             |
       Collection                          Map
             |                             |
      _______|_______                 Key → Value
     |       |       |
    List    Set     Queue
     |       |       |
     |       |      Deque
     |       |
     |       +-- HashSet
     |       +-- LinkedHashSet
     |       `-- TreeSet
     |
     +-- ArrayList
     +-- LinkedList
     +-- Vector
     `-- Stack
```

---

# Let's Learn It Like a Real-Life Example

Imagine a school.

## List

A teacher maintains an attendance list:

```text
Ravi
John
Ravi
Kiran
```

Duplicates may occur.

Think:

```text
List
```

---

## Set

The school wants unique student IDs:

```text
101
102
103
```

If `102` is entered again, we don't want another distinct `102`.

Think:

```text
Set
```

---

## Queue

Students are waiting to enter the examination hall:

```text
Student A → Student B → Student C
```

Students are processed according to queue rules.

Think:

```text
Queue
```

---

## Map

The school maintains:

```text
Roll Number → Student Name
```

Example:

```text
101 → Ravi
102 → John
103 → Kiran
```

Think:

```text
Map
```

---

# One Program to Connect the Ideas

```java
import java.util.*;

class CollectionTypes
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

The exact order of the `HashSet` output is not guaranteed.

---

# Understand the Program Like a Teacher

## Line 1

```java
List list = new ArrayList();
```

We are saying:

> "I need List behavior, and I am choosing ArrayList as the implementation."

---

## Add elements

```java
list.add("Java");
list.add("Python");
list.add("Java");
```

The List permits duplicate values.

So:

```text
Java
Python
Java
```

remain.

---

## Set

```java
Set set = new HashSet();
```

We are saying:

> "I need Set behavior."

Then:

```java
set.add("Java");
set.add("Python");
set.add("Java");
```

The duplicate `"Java"` isn't retained as a second distinct Set element.

---

# ⭐ Teacher's Memory Trick

Whenever you see a collection requirement, ask **four questions**:

### Question 1

> Do I need duplicates?

```text
YES → List may be suitable
NO  → Set may be suitable
```

### Question 2

> Do I care about ordering?

Then choose an implementation according to the required ordering behavior.

### Question 3

> Am I processing elements through queue/deque rules?

```text
Queue / Deque
```

### Question 4

> Am I storing key-value relationships?

```text
Map
```

---

# ⭐ Six Concepts — Final Teaching Map

```text
1. COLLECTION
   ↓
   Group of objects

2. NEED
   ↓
   Convenient management of groups
   + dynamic structures
   + operations
   + different behaviors

3. ADVANTAGES
   ↓
   Ready-made structures
   + methods
   + algorithms
   + reusable architecture

4. COLLECTION vs ARRAY
   ↓
   Array → fixed size
   Collection → framework with flexible implementations

5. COLLECTION FRAMEWORK
   ↓
   Interfaces
   + Classes
   + Algorithms
   + Utilities

6. COLLECTION HIERARCHY
   ↓
   Collection
   ├── List
   ├── Set
   └── Queue

   Map → separate branch
```

---

# 🚨 TEACHER'S TOP 10 DOUBTS

### 1. Is Collection a class?

**No. `Collection` is an interface.**

### 2. Is `Collections` an interface?

**No. `Collections` is a utility class.**

### 3. Is ArrayList a class?

**Yes.**

### 4. Is List a class?

**No. `List` is an interface.**

### 5. Is Map under Collection?

**No.**

### 6. Is Map part of the Collections Framework?

**Yes.**

### 7. Are arrays part of the Collections Framework?

**No.**

### 8. Can a List contain duplicates?

**Yes.**

### 9. Can a Set contain duplicate elements?

**No, according to the Set contract.**

### 10. Does every collection maintain insertion order?

**No.** Ordering depends on the particular collection implementation.

---

# 🧠 If You Remember Only One Diagram

```text
                  JAVA COLLECTIONS FRAMEWORK
                             |
                  ___________|___________
                 |                       |
            Collection                  Map
                 |                       |
        __________|_________         Key → Value
       |          |         |
      List       Set      Queue
       |          |         |
   ArrayList   HashSet    Deque
   LinkedList  LinkedHashSet |
   Vector      TreeSet    ArrayDeque
   Stack                   LinkedList
```

### The golden rule

> **Collection Framework is the complete family. `Collection` is one major interface branch inside it. `Map` is another major branch, but Map does not extend Collection.**

And throughout this lesson, **no Generics concept has been used**.
