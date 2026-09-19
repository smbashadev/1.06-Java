# 2. Iterable in Java — 3LEVEL

> **Training rule:** No Generics are used here. We will learn Generics separately later.

The **3LEVEL method** means we understand the same concept at three depths:

* 🟢 **LEVEL 1 — Beginner:** What is it?
* 🟡 **LEVEL 2 — Developer:** How does it work?
* 🔴 **LEVEL 3 — Interview/Deep Understanding:** Why does it work this way, what are the traps, and how are the concepts connected?

---

# 🟢 LEVEL 1 — BEGINNER LEVEL

## 1. What is Iterable?

`Iterable` is an interface in Java.

It belongs to:

```java
java.lang
```

Its purpose is to provide a way to **iterate/traverse elements one by one**.

Simple meaning:

> **Iterable means an object can provide an Iterator to traverse its elements.**

---

## 2. What is iteration?

Suppose we have:

```text
10  20  30  40
```

Visiting the elements one by one:

```text
10 → 20 → 30 → 40
```

is called **iteration**.

---

## 3. What is traversal?

Traversal means:

> **Visiting the elements of a data structure one by one.**

For example:

```text
Collection
   |
   +-- 10
   +-- 20
   +-- 30
```

Traversal:

```text
10 → 20 → 30
```

---

# 4. Why do we need Iterable?

Different collections can store their elements differently.

For example:

```text
ArrayList
LinkedList
HashSet
TreeSet
```

We don't want a completely different standard mechanism for traversing every collection.

Therefore Java provides a common mechanism:

```text
Collection
    ↓
Iterable
    ↓
Iterator
    ↓
Traverse elements
```

---

# 5. Iterable vs Iterator

This is extremely important.

### Iterable

```text
Can provide an Iterator
```

### Iterator

```text
Actually used to traverse elements
```

Remember:

```text
Iterable → gives Iterator
Iterator → traverses elements
```

---

# 6. `iterator()`

The important method associated with `Iterable` is:

```java
iterator()
```

It gives us an `Iterator` object.

Example:

```java
Iterator itr = al.iterator();
```

Flow:

```text
ArrayList
    ↓
iterator()
    ↓
Iterator object
```

---

# 7. Iterator's important methods

Once we obtain the iterator, two methods are particularly important for basic traversal:

```java
hasNext()
next()
```

### `hasNext()`

Checks whether another element exists.

```text
true
false
```

### `next()`

Returns the next element.

---

# 8. Basic program

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

Output:

```text
10
20
30
```

---

# 9. Understand the program

### Step 1

```java
ArrayList al = new ArrayList();
```

Creates an ArrayList.

### Step 2

```java
al.add(10);
al.add(20);
al.add(30);
```

Elements:

```text
[10, 20, 30]
```

### Step 3

```java
Iterator itr = al.iterator();
```

Gets an Iterator.

### Step 4

```java
itr.hasNext()
```

Checks whether another element exists.

### Step 5

```java
itr.next()
```

Gets the next element.

---

# 10. Simple flow

```text
ArrayList
   ↓
iterator()
   ↓
Iterator
   ↓
hasNext()
   ↓
true
   ↓
next()
   ↓
10
   ↓
hasNext()
   ↓
true
   ↓
next()
   ↓
20
   ↓
hasNext()
   ↓
true
   ↓
next()
   ↓
30
   ↓
hasNext()
   ↓
false
```

---

# 🟡 LEVEL 2 — DEVELOPER LEVEL

Now let's understand how `Iterable` fits into the Collections Framework.

---

# 11. Collection and Iterable relationship

The important relationship is:

```text
Iterable
    ↑
Collection
```

In Java terminology:

> `Collection` extends `Iterable`.

Therefore collection types inherit the ability to provide an iterator.

Conceptually:

```text
Iterable
    ↑
Collection
    ↑
 ┌──┼─────────┐
List Set     Queue
```

And implementations such as:

```text
ArrayList
LinkedList
HashSet
TreeSet
PriorityQueue
```

can therefore be traversed using an iterator.

---

# 12. Why is this useful?

Suppose we have:

```java
ArrayList al = new ArrayList();
```

We can write:

```java
Iterator itr = al.iterator();
```

Suppose instead we have:

```java
HashSet hs = new HashSet();
```

We can also write:

```java
Iterator itr = hs.iterator();
```

The internal structures are completely different, but the traversal mechanism is standardized.

---

# 13. ArrayList traversal

```java
import java.util.*;

class ArrayListTraversal
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

---

# 14. HashSet traversal

```java
import java.util.*;

class HashSetTraversal
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

Important:

**Do not depend on the output order of `HashSet`.**

Its iteration order is not guaranteed to be insertion order.

---

# 15. Why Iterator is important

Consider `ArrayList`.

We can access elements by index:

```java
al.get(0);
al.get(1);
al.get(2);
```

But a `HashSet` doesn't provide index-based access.

For example:

```java
hs.get(0);
```

is invalid.

So a common traversal mechanism is useful:

```text
ArrayList ──────┐
LinkedList ─────┤
HashSet ────────┤
TreeSet ────────┤
Queue ──────────┘
       ↓
    Iterator
       ↓
   Traversal
```

---

# 16. Iterable does not store data

This is important.

`Iterable` is an interface.

It does not itself act as:

```text
ArrayList
HashSet
TreeSet
```

It represents a **capability/contract** related to iteration.

Think:

```text
Iterable
   ↓
"I can provide an iterator."
```

---

# 17. Iterable does not decide ordering

`Iterable` does not say:

> "Elements must be returned in insertion order."

The actual collection determines its ordering characteristics.

For example:

| Collection    | General iteration behavior |
| ------------- | -------------------------- |
| ArrayList     | List order                 |
| LinkedHashSet | Insertion order            |
| TreeSet       | Sorted order               |
| HashSet       | No guaranteed order        |

So:

> **Iterable provides traversal capability; the implementation determines the iteration behavior/order.**

---

# 18. Enhanced for loop

We can also write:

```java
for(Object x : al)
{
    System.out.println(x);
}
```

This is called the:

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

Output:

```text
10
20
30
```

---

# 19. Relationship between for-each and Iterable

For an object used in an enhanced `for` loop, Java needs an iteration mechanism.

For an Iterable collection, the conceptual flow is:

```text
for-each
   ↓
iterator()
   ↓
Iterator
   ↓
hasNext()
   ↓
next()
```

You don't manually write those iterator calls when using the enhanced `for` loop.

---

# 20. Important exception: Arrays

An array is **not** an `Iterable`.

For example:

```java
int a[] = {10,20,30};
```

This does **not** mean:

```text
array implements Iterable
```

But this is valid:

```java
for(int x : a)
{
    System.out.println(x);
}
```

Why?

Because Java's enhanced `for` statement has special language support for arrays.

So remember:

```text
Array
   ├── not Iterable
   └── supports enhanced for

Collection
   └── Iterable
          ↓
       Iterator
```

---

# 21. Can we call iterator() on an array?

No.

This is invalid:

```java
int a[] = {10,20,30};

a.iterator();
```

Arrays do not have an `iterator()` method.

---

# 22. Can a class implement Iterable without being a Collection?

Yes.

This is an important conceptual point.

A custom class can implement `Iterable` without implementing `Collection`.

Conceptually:

```java
class MyClass implements Iterable
{
    // iterator implementation
}
```

The class is saying:

> "I provide a mechanism for iterating through my data."

It does **not** have to be a Java Collection.

---

# 🔴 LEVEL 3 — INTERVIEW + DEEP UNDERSTANDING

Now let's remove the common confusion surrounding `Iterable`.

---

# 23. Iterable is a capability, not a data structure

This distinction is critical.

Don't think:

```text
Iterable = Collection
```

Instead:

```text
Iterable
    =
"An object can provide an Iterator."
```

A collection can implement this capability.

A custom data structure can also implement it.

---

# 24. `Iterable` vs `Iterator` — exact conceptual difference

| Iterable                                              | Iterator                                       |
| ----------------------------------------------------- | ---------------------------------------------- |
| Interface representing something that can be iterated | Interface representing the traversal mechanism |
| Provides `iterator()`                                 | Provides traversal operations                  |
| Used to obtain an Iterator                            | Used to traverse                               |
| Think "source of iterator"                            | Think "cursor/traversal object"                |

Memory:

```text
Iterable
   ↓
"Give me an iterator."

Iterator
   ↓
"Here is the next element."
```

---

# 25. Why doesn't Iterable itself contain `next()`?

Because their responsibilities are different.

Imagine:

```text
Collection
```

contains the data.

It shouldn't itself have to maintain the current traversal position for every possible traversal.

Instead:

```text
Collection
    ↓
iterator()
    ↓
Iterator
    ↓
maintains traversal state
```

This separates:

```text
Data structure
```

from:

```text
Traversal mechanism
```

---

# 26. Multiple Iterators

This separation allows multiple iterators to exist.

Conceptually:

```text
Collection
   |
   +── Iterator A
   |
   `── Iterator B
```

Each iterator can have its own traversal position.

For example:

```java
Iterator itr1 = al.iterator();
Iterator itr2 = al.iterator();
```

These are two different iterator objects.

Each maintains its own traversal state.

---

# 27. Why this design is powerful

Suppose:

```text
Collection = [10, 20, 30, 40]
```

We can create:

```text
Iterator A → starts traversal
Iterator B → starts another traversal
```

The collection doesn't need to maintain one universal "current position."

The iterator handles the traversal state.

---

# 28. What happens when `next()` goes beyond the end?

Suppose:

```text
[10, 20]
```

We do:

```java
itr.next();  // 10
itr.next();  // 20
itr.next();  // no element
```

The third call can result in:

```text
NoSuchElementException
```

Therefore the normal safe pattern is:

```java
while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

---

# 29. Why `hasNext()` is not "move forward"

Another common misconception:

```text
hasNext()
```

does **not** mean:

> Move to the next element.

It means:

> Check whether another element is available.

The traversal operation is performed by:

```text
next()
```

Therefore:

```text
hasNext() → CHECK
next()    → MOVE + RETURN ELEMENT
```

---

# 30. Does `next()` only return an element?

Conceptually, `next()` does two things:

```text
1. Returns the next element
2. Advances the iterator's position
```

For:

```text
[10,20,30]
```

the conceptual movement is:

```text
Before traversal
      ↓
   [10] [20] [30]

next()
      ↓
returns 10
moves forward

next()
      ↓
returns 20
moves forward

next()
      ↓
returns 30
moves forward
```

---

# 31. Why Iterator instead of `get()`?

`get(index)` is specific to index-based collections such as `List`.

Iterator is more general.

```text
List
   ↓
index access possible

Set
   ↓
index access not provided

Iterator
   ↓
works as a common traversal mechanism
```

That's why Iterator is an important Collections Framework concept.

---

# 32. Iterable and Map — important distinction

Students often draw:

```text
Iterable
   ↓
Collection
   ↓
Map
```

That is incorrect.

The simplified hierarchy is:

```text
                 Iterable
                    ↑
               Collection
              /    |     \
            List   Set   Queue


                 Map
              /   |   \
         HashMap TreeMap ...
```

`Map` is a separate hierarchy.

---

# 33. But can Map data be iterated?

Yes.

A Map provides collection views such as:

```java
keySet()
values()
entrySet()
```

Those views can be traversed.

For example:

```java
HashMap hm = new HashMap();

hm.put(1, "A");
hm.put(2, "B");

Iterator itr = hm.keySet().iterator();

while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

We will study this properly in the **Map** and **Iterator** topics.

---

# 34. `Iterable` and Generics

For **this training**, we intentionally use:

```java
Iterable
Iterator
ArrayList
HashSet
```

without generic type parameters.

For example:

```java
Iterator itr = al.iterator();
```

not:

```java
Iterator<Integer> itr = al.iterator();
```

Why?

Because **Generics is a separate topic in your roadmap**.

We should first understand:

```text
Collection
   ↓
Iterable
   ↓
Iterator
```

conceptually.

Then later:

```text
Generics
   ↓
type safety
   ↓
generic collections
   ↓
wildcards
   ↓
bounds
```

This keeps the training progression clean.

---

# 35. The complete architecture

Put everything together:

```text
                    Iterable
                       ↑
                       |
                   Collection
                       ↑
             __________|__________
            |          |          |
           List        Set       Queue
            |          |          |
       ArrayList     HashSet   PriorityQueue
       LinkedList    TreeSet
       Vector
       Stack
            |
            |
            └──── iterator()
                       |
                       ↓
                    Iterator
                   /        \
             hasNext()      next()
                 |             |
               CHECK          GET
                 |             |
                 └──────┬──────┘
                        ↓
                     Element
```

---

# 36. Three-level summary

## 🟢 Level 1 — Remember

```text
Iterable
   ↓
iterator()
   ↓
Iterator
   ↓
hasNext()
next()
```

Meaning:

> Iterable allows objects to provide a way to traverse elements.

---

## 🟡 Level 2 — Understand

```text
Collection
   ↓
Iterable
   ↓
iterator()
   ↓
Iterator
```

Different collection implementations can therefore use a common traversal mechanism.

---

## 🔴 Level 3 — Master

`Iterable` and `Iterator` deliberately have different responsibilities:

```text
Iterable
    ↓
provides the traversal mechanism

Iterator
    ↓
maintains traversal state
    ↓
hasNext()
    ↓
next()
```

Arrays are not `Iterable`, even though they work with the enhanced `for` statement.

`Map` is not a `Collection`, although its collection views can be iterated.

And `Iterable` itself does not determine the ordering of elements—the particular data structure does.

---

# 🔥 Final DO-NOT-CONFUSE list

| Don't confuse                           | Correct understanding |
| --------------------------------------- | --------------------- |
| `Iterable` = `Iterator`                 | ❌                     |
| `Iterable` provides an `Iterator`       | ✅                     |
| `hasNext()` returns an element          | ❌                     |
| `hasNext()` checks availability         | ✅                     |
| `next()` checks availability            | ❌                     |
| `next()` obtains the next element       | ✅                     |
| Array implements `Iterable`             | ❌                     |
| Array supports enhanced `for`           | ✅                     |
| `Map` extends `Collection`              | ❌                     |
| `Collection` is connected to `Iterable` | ✅                     |
| `iterator()` returns an element         | ❌                     |
| `iterator()` returns an Iterator        | ✅                     |
| `Iterable` stores collection elements   | ❌                     |
| `Iterable` defines iteration capability | ✅                     |

## 🧠 One-line memory

> **`Iterable` provides the Iterator → `Iterator` traverses → `hasNext()` checks → `next()` gets the next element.**
