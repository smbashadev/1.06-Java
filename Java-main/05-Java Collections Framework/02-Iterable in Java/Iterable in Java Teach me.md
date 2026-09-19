# 2. Iterable in Java — TEACHME

> **Training rule:** We will use **normal Java programs without Generics** throughout this explanation. Generics such as `Iterable<Integer>` and `Iterator<Integer>` will be taught later in the **Generics** topic.

---

# 1. First understand the problem

Imagine you have a box containing several numbers:

```text
+-----------------------+
| 10   20   30   40    |
+-----------------------+
```

Suppose you want to see every number one by one.

You need some mechanism that says:

```text
Give me the first element
Give me the next element
Give me the next element
...
Stop when no element remains
```

This process is called:

> **Iteration / Traversal**

Java provides a standard mechanism for this through **`Iterable` and `Iterator`**.

---

# 2. What is `Iterable`?

`Iterable` is an **interface** in Java.

It belongs to:

```text
java.lang
```

Its complete name is:

```java
java.lang.Iterable
```

The basic idea is:

> **An object that implements `Iterable` can provide an `Iterator` for traversing its elements.**

Think of `Iterable` as saying:

> **"I can give you a way to go through my elements."**

---

# 3. Why do we need `Iterable`?

Different collections store data differently.

For example:

```text
ArrayList
LinkedList
HashSet
TreeSet
```

Internally, these collections work differently.

But we don't want to learn a completely different traversal mechanism for every collection.

Java provides a common concept:

```text
                 Iterable
                    |
              iteration
                    |
              Iterator
```

So regardless of the collection implementation, we have a standard way to traverse its elements.

---

# 4. What is iteration?

Suppose:

```text
[10, 20, 30, 40]
```

If we visit the elements:

```text
10 → 20 → 30 → 40
```

that is called **iteration**.

In simple words:

> **Iteration = visiting elements one by one.**

---

# 5. What is traversal?

Traversal means moving through all the elements of a data structure.

For example:

```text
Collection
    |
    +-- 10
    +-- 20
    +-- 30
    `-- 40
```

Traversing it means:

```text
10 → 20 → 30 → 40
```

In Collections Framework terminology, **iteration** is commonly used for this process.

---

# 6. The most important relationship

Remember this:

```text
Iterable
    |
    | iterator()
    ↓
Iterator
    |
    +-- hasNext()
    |
    `-- next()
```

This is the heart of the entire topic.

---

# 7. Don't confuse `Iterable` and `Iterator`

This is the **first major doubt**.

They are not the same.

### `Iterable`

Means:

> "I can provide an iterator."

### `Iterator`

Means:

> "I am the object used to traverse the elements."

Therefore:

```text
Iterable
   ↓
provides
   ↓
Iterator
   ↓
traverses elements
```

### Easy memory trick

```text
Iterable = "Can I be iterated?"

Iterator = "I am doing the iteration."
```

---

# 8. What method does `Iterable` provide?

The most important method is:

```java
iterator()
```

Conceptually:

```text
Iterable
    |
iterator()
    |
    ↓
Iterator object
```

So when we call:

```java
al.iterator();
```

we are asking the collection:

> "Give me an object that can traverse your elements."

---

# 9. First simple program

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

# 10. Let's understand this program slowly

## Step 1 — Create ArrayList

```java
ArrayList al = new ArrayList();
```

We created an `ArrayList`.

Initially:

```text
[]
```

---

## Step 2 — Add elements

```java
al.add(10);
al.add(20);
al.add(30);
```

Now:

```text
[10, 20, 30]
```

---

## Step 3 — Get Iterator

```java
Iterator itr = al.iterator();
```

This is extremely important.

We are **not getting an element**.

We are getting an **Iterator object**.

```text
ArrayList
    |
iterator()
    |
    ↓
Iterator
```

---

# 11. What does the Iterator do?

The iterator keeps track of where we are during traversal.

Imagine:

```text
[10] [20] [30]
 ^
 |
Iterator
```

The iterator starts before the first element.

Then:

```java
itr.next();
```

gives:

```text
10
```

Then it moves forward.

Next:

```java
itr.next();
```

gives:

```text
20
```

Then:

```java
itr.next();
```

gives:

```text
30
```

---

# 12. What is `hasNext()`?

This is another very important concept.

```java
itr.hasNext()
```

means:

> **"Is there another element available?"**

It returns either:

```text
true
```

or:

```text
false
```

For example:

```text
Elements: [10, 20, 30]

hasNext() → true
hasNext() → true
hasNext() → true
hasNext() → false
```

---

# 13. What is `next()`?

```java
itr.next()
```

means:

> **"Give me the next element."**

For:

```text
[10, 20, 30]
```

the calls conceptually produce:

```text
next() → 10
next() → 20
next() → 30
```

---

# 14. `hasNext()` vs `next()`

Remember this table:

| Method      | Meaning                       | Return           |
| ----------- | ----------------------------- | ---------------- |
| `hasNext()` | Is another element available? | `true` / `false` |
| `next()`    | Give the next element         | Element          |

### Easy trick

```text
hasNext()
   ↓
CHECK

next()
   ↓
GET
```

So:

```text
hasNext() = Check
next()    = Get
```

---

# 15. Why do we use `hasNext()` before `next()`?

Suppose the collection contains only:

```text
[10, 20, 30]
```

After reading `30`, there is nothing left.

If we blindly call:

```java
itr.next();
```

again, Java can throw:

```text
NoSuchElementException
```

Therefore we normally write:

```java
while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

This means:

```text
Check
 ↓
Is there another element?
 ↓
Yes
 ↓
Get it
 ↓
Repeat
```

---

# 16. Complete movement diagram

Suppose:

```text
[10] [20] [30]
```

### Initially

```text
Iterator
   ↓
[10] [20] [30]
```

### Check

```java
itr.hasNext()
```

Result:

```text
true
```

### Get

```java
itr.next()
```

Result:

```text
10
```

Now move forward.

### Next

```java
itr.hasNext()
```

Result:

```text
true
```

```java
itr.next()
```

Result:

```text
20
```

Again:

```java
itr.hasNext()
```

```text
true
```

Then:

```java
itr.next()
```

```text
30
```

Finally:

```java
itr.hasNext()
```

returns:

```text
false
```

Loop ends.

---

# 17. The complete flow

Memorize this:

```text
Collection object
       ↓
iterator()
       ↓
Iterator object
       ↓
hasNext()
       ↓
   true?
    /  \
  yes   no
   |
 next()
   |
element
   |
repeat
```

This is the most important diagram for `Iterable`.

---

# 18. Where does `Collection` come into the picture?

Now let's connect this with the Collections Framework.

The relationship is:

```text
Iterable
    ↑
Collection
```

Meaning:

```text
Collection extends Iterable
```

So collection interfaces and implementations ultimately inherit the ability to provide an iterator.

---

# 19. Collection hierarchy connection

```text
                         Iterable
                            |
                       Collection
                            |
             _______________|_______________
            |               |               |
           List            Set            Queue
            |               |               |
       ArrayList         HashSet       PriorityQueue
       LinkedList        LinkedHashSet
       Vector            TreeSet
       Stack
```

Therefore:

```text
List
Set
Queue
```

are ultimately connected to:

```text
Iterable
```

---

# 20. Why is this useful?

Suppose today we use:

```java
ArrayList al = new ArrayList();
```

Tomorrow we use:

```java
HashSet hs = new HashSet();
```

The internal structure is different.

But both can be traversed using an iterator:

```java
Iterator itr = al.iterator();
```

and:

```java
Iterator itr = hs.iterator();
```

This gives us a common traversal mechanism.

---

# 21. ArrayList example

```java
import java.util.*;

class ArrayListIteration
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(100);
        al.add(200);
        al.add(300);

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
100
200
300
```

---

# 22. HashSet example

```java
import java.util.*;

class HashSetIteration
{
    public static void main(String[] args)
    {
        HashSet hs = new HashSet();

        hs.add(100);
        hs.add(200);
        hs.add(300);

        Iterator itr = hs.iterator();

        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }
    }
}
```

A possible output is:

```text
100
200
300
```

But **do not assume that order** for `HashSet`.

The important thing is that we can traverse it.

---

# 23. Why is Iterator better than indexes?

Consider:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

With a List, we can use:

```java
al.get(0);
al.get(1);
al.get(2);
```

But consider:

```java
HashSet hs = new HashSet();
```

We cannot normally say:

```java
hs.get(0);
```

because Set does not provide List-style index access.

Iterator solves this problem:

```java
Iterator itr = hs.iterator();
```

Now we can traverse the elements without depending on indexes.

---

# 24. This is the real reason for the Iterator mechanism

Different collection types have different internal structures.

```text
ArrayList
    ↓
array-based structure

LinkedList
    ↓
linked structure

HashSet
    ↓
hash-based structure

TreeSet
    ↓
tree-based structure
```

But Java gives them a common traversal mechanism:

```text
              Different Collections
                       |
                       ↓
                    Iterable
                       |
                       ↓
                    Iterator
                       |
                       ↓
                 Common traversal
```

---

# 25. What is the enhanced `for` loop?

You have probably seen:

```java
for(Object x : al)
{
    System.out.println(x);
}
```

This is called:

> **Enhanced for loop / for-each loop**

Example:

```java
import java.util.*;

class ForEachDemo
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

---

# 26. How is for-each related to Iterable?

Conceptually:

```text
for-each
   ↓
iteration mechanism
   ↓
iterator()
   ↓
Iterator
   ↓
hasNext()
next()
```

You don't manually write the iterator code when using the enhanced `for` loop.

Java handles the iteration mechanism for you.

---

# 27. Important correction: for-each is NOT only for Collections

Many beginners think:

> "For-each works only with Collections."

That is wrong.

It works with:

### 1. Arrays

```java
int a[] = {10, 20, 30};

for(int x : a)
{
    System.out.println(x);
}
```

Output:

```text
10
20
30
```

### 2. Iterable objects

Such as collection objects.

```text
ArrayList
HashSet
TreeSet
LinkedList
...
```

---

# 28. Is an array an `Iterable`?

**No.**

This is a common interview question.

An array does not implement the `Iterable` interface.

But Java's enhanced `for` statement has special language support for arrays.

So:

```text
Array
   ✗
   Iterable
```

But:

```text
Array
   ✓
for-each
```

---

# 29. Can we call `iterator()` on an array?

No.

This won't work:

```java
int a[] = {10, 20, 30};

a.iterator();
```

because arrays don't provide the `iterator()` method.

But this works:

```java
for(int x : a)
{
    System.out.println(x);
}
```

---

# 30. Does `Iterable` store elements?

No.

This is very important.

`Iterable` is an **interface**, not a data-storage implementation.

Think:

```text
Iterable
   ↓
Contract / capability
```

Whereas:

```text
ArrayList
HashSet
TreeSet
```

are actual collection implementations that manage elements.

---

# 31. Does `Iterable` determine how elements are stored?

No.

For example:

```text
ArrayList
 → array-based internal storage

LinkedList
 → linked structure

HashSet
 → hash-based storage

TreeSet
 → tree-based storage
```

`Iterable` doesn't care about those implementation details.

It only provides the iteration contract.

---

# 32. Does `Iterable` determine the order?

No.

This is another important point.

Different collections have different ordering behavior.

### ArrayList

Maintains list order.

### LinkedHashSet

Maintains insertion-order iteration.

### TreeSet

Provides sorted-order behavior.

### HashSet

Does not guarantee a particular iteration order.

Therefore:

> **`Iterable` provides iteration capability; the collection implementation determines its iteration order.**

---

# 33. Is `Iterable` itself a Collection?

No.

The relationship is:

```text
Iterable
   ↑
Collection
```

So:

```text
Collection is an Iterable
```

But `Iterable` is not itself the `Collection` interface.

---

# 34. Can a class implement `Iterable` without implementing `Collection`?

**Yes.**

For example:

```java
class MyClass implements Iterable
{
    // iterator() implementation
}
```

This is possible because `Iterable` is a general iteration contract.

It is not restricted to the Collections Framework.

---

# 35. Why would we create our own Iterable class?

Suppose we create our own data structure:

```text
MyData
   |
   +-- 10
   +-- 20
   +-- 30
```

We may want:

```java
for(Object x : myData)
{
    System.out.println(x);
}
```

To make that possible, our class can implement `Iterable` and provide an iterator.

Conceptually:

```text
MyClass
   |
implements Iterable
   |
iterator()
   |
Iterator
   |
for-each traversal
```

This is useful when building custom data structures.

---

# 36. `Iterable` does not perform traversal itself

This is a subtle but important point.

Don't think:

```text
Iterable
 ↓
automatically walks through elements
```

Instead:

```text
Iterable
   ↓
iterator()
   ↓
Iterator
   ↓
traversal
```

So:

> **`Iterable` provides the ability to obtain an iterator; the `Iterator` performs the traversal.**

---

# 37. What exactly does `iterator()` return?

It returns:

```text
Iterator object
```

Not:

```text
first element
```

Not:

```text
all elements
```

Not:

```text
array
```

Correct:

```text
iterator()
     ↓
Iterator object
```

Then:

```text
Iterator
     ↓
next()
     ↓
element
```

---

# 38. Common mistake #1

### Wrong:

```text
iterator() returns the first element.
```

### Correct:

```text
iterator() returns an Iterator object.
```

Then:

```java
itr.next();
```

returns the next element.

---

# 39. Common mistake #2

### Wrong:

```text
hasNext() returns the next element.
```

### Correct:

```text
hasNext() checks whether another element exists.
```

It returns:

```text
true / false
```

---

# 40. Common mistake #3

### Wrong:

```text
Iterable and Iterator are the same.
```

### Correct:

```text
Iterable
   ↓
provides Iterator

Iterator
   ↓
traverses elements
```

---

# 41. Common mistake #4

### Wrong:

> Every object used with for-each must implement `Iterable`.

Not exactly.

Arrays are the important exception.

```text
for-each
   |
   +-- arrays
   |
   `-- Iterable objects
```

---

# 42. Common mistake #5

### Wrong:

> `Map` is a Collection.

Correct:

```text
Collection
   |
   +-- List
   +-- Set
   `-- Queue

Map
   |
   +-- HashMap
   +-- TreeMap
   +-- LinkedHashMap
   ...
```

`Map` is a separate branch.

---

# 43. How Map is eventually iterated

A `Map` contains:

```text
101 → "Ravi"
102 → "John"
103 → "Kiran"
```

We don't treat the Map itself like a normal `Collection` of individual elements.

Instead, we can obtain views:

```java
hm.keySet();
hm.values();
hm.entrySet();
```

Those views can be traversed.

We'll study this in detail under **Map** and **Iterator**.

---

# 44. Real-life analogy

Imagine a library.

```text
Library
   |
   +-- Book 1
   +-- Book 2
   +-- Book 3
```

The library says:

> "You can walk through my books."

That's like:

```text
Iterable
```

The person walking through the books is:

```text
Iterator
```

The person asks:

```text
"Is another book available?"
```

Equivalent to:

```java
hasNext()
```

Then:

```text
"Give me the next book."
```

Equivalent to:

```java
next()
```

So:

```text
Library
  ↓
Iterable

Walking mechanism
  ↓
Iterator

"Is another book there?"
  ↓
hasNext()

"Give me the next book."
  ↓
next()
```

---

# 45. The entire concept in one story

Suppose:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

You say:

> "I want to visit every element."

The `ArrayList` ultimately satisfies the `Iterable` contract.

You ask:

```java
al.iterator();
```

The collection responds:

> "Here is an Iterator."

You store it:

```java
Iterator itr = al.iterator();
```

Then you ask:

```java
itr.hasNext()
```

It says:

```text
true
```

You ask:

```java
itr.next()
```

It gives:

```text
10
```

Again:

```text
hasNext() → true
next()    → 20
```

Again:

```text
hasNext() → true
next()    → 30
```

Finally:

```text
hasNext() → false
```

You stop.

That is the complete concept of **iteration through `Iterable` and `Iterator`**.

---

# 46. Complete program with explanation

```java
import java.util.*;

class IterableTeachingDemo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(100);
        al.add(200);
        al.add(300);
        al.add(400);

        Iterator itr = al.iterator();

        while(itr.hasNext())
        {
            Object value = itr.next();

            System.out.println("Value = " + value);
        }
    }
}
```

### Output

```text
Value = 100
Value = 200
Value = 300
Value = 400
```

### Execution

```text
ArrayList created
       ↓
100 added
       ↓
200 added
       ↓
300 added
       ↓
400 added
       ↓
iterator()
       ↓
Iterator created
       ↓
hasNext() → true
       ↓
next() → 100
       ↓
hasNext() → true
       ↓
next() → 200
       ↓
hasNext() → true
       ↓
next() → 300
       ↓
hasNext() → true
       ↓
next() → 400
       ↓
hasNext() → false
       ↓
Program continues
```

---

# 47. Why is this concept important before learning Collection?

Because almost every major collection topic will eventually involve traversal.

For example:

```text
ArrayList
LinkedList
Vector
Stack
HashSet
LinkedHashSet
TreeSet
Queue
Deque
```

You will repeatedly encounter:

```java
Iterator itr = collection.iterator();
```

So understanding `Iterable` now makes the later topics much easier.

---

# 48. `Iterable` and the Collections Framework — Big Picture

```text
                  JAVA COLLECTIONS FRAMEWORK
                           |
              __________________________
             |                          |
        Collection                     Map
             |
          Iterable
             |
       iterator()
             |
          Iterator
             |
       traversal of
          elements
```

More precisely, inheritance goes upward:

```text
Iterable
    ↑
Collection
    ↑
List / Set / Queue
```

---

# 49. Important interview questions

### Q1. What is `Iterable`?

`Iterable` is an interface that provides a standard mechanism for obtaining an iterator to traverse elements.

---

### Q2. Which package contains `Iterable`?

```text
java.lang
```

---

### Q3. What is the important method associated with `Iterable`?

```java
iterator()
```

---

### Q4. What does `iterator()` return?

An:

```text
Iterator
```

object.

---

### Q5. What is the purpose of `Iterator`?

To traverse elements one by one.

---

### Q6. What does `hasNext()` do?

Checks whether another element is available.

---

### Q7. What does `next()` do?

Returns the next element.

---

### Q8. Is `Iterable` the same as `Iterator`?

No.

```text
Iterable → provides Iterator
Iterator → performs traversal
```

---

### Q9. Is an array an `Iterable`?

No.

But arrays are supported by the enhanced `for` loop.

---

### Q10. Is `Map` a `Collection`?

No.

Map is a separate branch of the Collections Framework.

---

# 50. Final memory formula

If you remember only one thing from this entire topic, remember this:

```text
                Iterable
                   |
                   |
              iterator()
                   |
                   ↓
                Iterator
                   |
          __________|__________
         |                     |
     hasNext()              next()
         |                     |
       CHECK                  GET
         |                     |
         └──────────┬──────────┘
                    ↓
                 Element
```

And remember:

```text
Iterable = "Can provide an iterator."

Iterator = "Traverses the elements."

hasNext() = "Is anything left?"

next() = "Give me the next element."
```

### One final example to remember

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);

Iterator itr = al.iterator();

while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

Output:

```text
10
20
30
```

**That four-line pattern is the foundation for iterator-based traversal throughout the Java Collections Framework.**
