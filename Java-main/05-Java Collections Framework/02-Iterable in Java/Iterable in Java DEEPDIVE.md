# 2. Iterable in Java — DEEPDIVE

> **Training rule:** We are learning the Collections Framework from the basics first. Therefore, **no Generic syntax** such as `Iterable<Integer>`, `Iterator<Integer>`, or `ArrayList<Integer>` is used in any program below. Generics will be covered separately.

---

# 1. What is `Iterable`?

`Iterable` is an **interface** in Java that represents an object whose elements can be traversed one by one.

It belongs to:

```java
java.lang.Iterable
```

Its fundamental purpose is to provide a standard mechanism for obtaining an `Iterator`.

The basic relationship is:

```text
Iterable
    |
    ↓
Collection
    |
    +-- List
    +-- Set
    `-- Queue
```

Therefore:

> **`Collection` is a subinterface of `Iterable`.**

---

# 2. Why was `Iterable` introduced?

Suppose a program contains a collection:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
al.add(40);
```

The collection contains:

```text
+----+----+----+----+
| 10 | 20 | 30 | 40 |
+----+----+----+----+
```

At some point, we need to process every element:

```text
10
↓
20
↓
30
↓
40
```

This process is called **iteration** or **traversal**.

Java needed a common mechanism so that different collection implementations could be traversed in a standardized way.

That common mechanism is based on `Iterable`.

---

# 3. What does "iteration" mean?

**Iteration** means repeatedly accessing elements one by one.

For example:

```text
Collection:

10  20  30  40

Iteration:

10
 ↓
20
 ↓
30
 ↓
40
```

We are not changing the collection merely by traversing it.

We are simply visiting its elements.

---

# 4. What does "traversal" mean?

Traversal and iteration are closely related terms.

If we have:

```text
[10, 20, 30, 40]
```

and visit:

```text
10 → 20 → 30 → 40
```

we are **traversing** the collection.

In Collections Framework terminology, **iteration** is the commonly used term.

---

# 5. What does `Iterable` provide?

The most important operation associated with `Iterable` is:

```java
iterator()
```

Conceptually:

```text
Iterable
    |
    +-- iterator()
             |
             ↓
          Iterator
```

The `iterator()` method provides an `Iterator` object that can traverse the elements.

---

# 6. The relationship between `Iterable` and `Iterator`

This is extremely important.

They are **two different interfaces**.

```text
Iterable
   ↓
provides an Iterator

Iterator
   ↓
performs traversal
```

Think of it like this:

```text
Iterable = "I can give you a way to traverse me."

Iterator = "I am the object that traverses me."
```

---

# 7. First program using `Iterable` behavior

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

---

# 8. How does this program work?

Look at:

```java
ArrayList al = new ArrayList();
```

An `ArrayList` is created.

Then:

```java
al.add(10);
al.add(20);
al.add(30);
```

The collection contains:

```text
[10, 20, 30]
```

Then:

```java
for(Object x : al)
```

asks Java to iterate through the elements.

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
iteration supported
```

---

# 9. What is the enhanced `for` loop?

This:

```java
for(Object x : al)
{
    System.out.println(x);
}
```

is called the:

> **Enhanced for loop** or **for-each loop**.

It provides a convenient way of traversing an `Iterable` object.

---

# 10. What happens conceptually behind the for-each loop?

When the expression being traversed is an `Iterable`, Java's iteration mechanism is based on obtaining an iterator.

Conceptually, think of:

```java
for(Object x : al)
{
    System.out.println(x);
}
```

as being equivalent in idea to:

```java
Iterator itr = al.iterator();

while(itr.hasNext())
{
    Object x = itr.next();

    System.out.println(x);
}
```

This conceptual connection is extremely important.

```text
for-each loop
      ↓
iteration mechanism
      ↓
iterator()
      ↓
Iterator
      ↓
hasNext()
      ↓
next()
```

---

# 11. Using `iterator()` directly

Now let's explicitly obtain the iterator.

```java
import java.util.*;

class IteratorDemo
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

# 12. Explain the program line by line

### Step 1

```java
ArrayList al = new ArrayList();
```

Creates an `ArrayList`.

---

### Step 2

```java
al.add(10);
al.add(20);
al.add(30);
```

Adds three elements.

Collection becomes:

```text
[10, 20, 30]
```

---

### Step 3

```java
Iterator itr = al.iterator();
```

The `iterator()` method returns an `Iterator`.

Conceptually:

```text
ArrayList
    |
iterator()
    |
    ↓
Iterator
```

The iterator starts before the first element.

Conceptually:

```text
Iterator position
      ↓
[10] [20] [30]
 ^
before first element
```

---

### Step 4

```java
itr.hasNext()
```

Checks whether another element is available.

Initially:

```text
true
```

---

### Step 5

```java
itr.next()
```

Moves to the next element and returns it.

First call:

```text
10
```

Then:

```text
20
```

Then:

```text
30
```

---

### Step 6

After the last element:

```java
itr.hasNext()
```

returns:

```text
false
```

The loop terminates.

---

# 13. `hasNext()` vs `next()`

This is one of the most common beginner doubts.

## `hasNext()`

Asks:

> **"Is another element available?"**

It returns a boolean.

```text
true
false
```

---

## `next()`

Asks for the:

> **"Next element."**

It returns the next element.

Therefore:

```text
hasNext()
   ↓
checks

next()
   ↓
retrieves/moves to next element
```

---

# 14. Iterator movement diagram

For:

```text
[10, 20, 30]
```

initially:

```text
       Iterator
          ↓
       [10] [20] [30]
```

First:

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

returns:

```text
10
```

Conceptually:

```text
          Iterator
             ↓
       [10] [20] [30]
```

Next iteration:

```text
             Iterator
                ↓
       [10] [20] [30]
```

`next()` returns:

```text
20
```

Finally:

```text
                    Iterator
                       ↓
       [10] [20] [30]
```

`next()` returns:

```text
30
```

Then:

```java
hasNext()
```

returns:

```text
false
```

---

# 15. Why don't we directly access elements using indexes?

For an `ArrayList`, we can do:

```java
System.out.println(al.get(0));
System.out.println(al.get(1));
System.out.println(al.get(2));
```

But this is **index-based traversal**.

Not every collection is naturally index-based.

For example:

```text
HashSet
TreeSet
```

do not provide normal List-style index access.

So Java provides a common traversal mechanism:

```text
Iterable
    ↓
Iterator
```

This allows different collection types to be traversed without depending on indexes.

---

# 16. ArrayList vs HashSet iteration

## ArrayList

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

We could use indexes.

But we can also use:

```java
Iterator itr = al.iterator();
```

---

## HashSet

```java
HashSet hs = new HashSet();

hs.add(10);
hs.add(20);
hs.add(30);
```

There is no List-style:

```java
hs.get(0);
```

Instead:

```java
Iterator itr = hs.iterator();
```

This is one of the reasons the `Iterable`/`Iterator` design is important.

---

# 17. One program with List and Set

```java
import java.util.*;

class IterableCollectionDemo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);

        HashSet hs = new HashSet();

        hs.add(40);
        hs.add(50);
        hs.add(60);

        Iterator itr1 = al.iterator();

        System.out.println("ArrayList:");

        while(itr1.hasNext())
        {
            System.out.println(itr1.next());
        }

        Iterator itr2 = hs.iterator();

        System.out.println("HashSet:");

        while(itr2.hasNext())
        {
            System.out.println(itr2.next());
        }
    }
}
```

### Output

One possible output is:

```text
ArrayList:
10
20
30
HashSet:
40
50
60
```

The order of a `HashSet` should **not** be assumed to be guaranteed.

---

# 18. Why is `Iterable` above `Collection`?

The inheritance relationship is:

```text
Iterable
    ↑
Collection
```

Meaning:

```text
Collection extends Iterable
```

Therefore, anything that implements `Collection` must satisfy the `Iterable` contract.

This gives collection types standard iteration support.

---

# 19. Is `Iterable` a Collection?

**No.**

This distinction is important.

```text
Iterable
```

is the broader iteration contract.

```text
Collection
```

is a more specialized interface for groups of objects.

Relationship:

```text
Iterable
   ↑
Collection
```

Therefore:

```text
Every Collection is Iterable.
```

But:

```text
Not every Iterable is necessarily a Collection.
```

A programmer can create another class that implements `Iterable` without implementing `Collection`.

---

# 20. Can our own class implement `Iterable`?

**Yes.**

This is an important concept.

Suppose we create:

```java
class MyClass implements Iterable
{
    // iterator() implementation
}
```

Then our class can participate in Java's standard iteration mechanism.

This means `Iterable` isn't exclusively for the built-in Collections Framework.

---

# 21. Simple custom `Iterable` concept

At the conceptual level:

```java
class MyClass implements Iterable
{
    public Iterator iterator()
    {
        // return an Iterator
    }
}
```

The important idea is:

```text
Our class
   ↓
implements Iterable
   ↓
provides iterator()
   ↓
returns Iterator
   ↓
elements can be traversed
```

The actual custom `Iterator` implementation becomes easier to understand after learning the `Iterator` interface separately.

---

# 22. `Iterable` and `for-each`: Important distinction

A common statement is:

> "for-each works only with Collections."

That is **not correct**.

The enhanced `for` loop can work with:

1. Arrays
2. Objects implementing `Iterable`

For example:

```java
int a[] = {10, 20, 30};

for(int x : a)
{
    System.out.println(x);
}
```

### Output

```text
10
20
30
```

An array itself isn't a Collection and doesn't implement `Iterable`.

Yet Java's enhanced `for` statement has special support for arrays.

So remember:

```text
for-each
   |
   +-- Array
   |
   `-- Iterable
```

---

# 23. Very important: Array is NOT Iterable

This is a common interview trap.

Wrong:

```text
Array
 ↓
Iterable
```

An array does not implement the `Iterable` interface.

But:

```java
for(int x : array)
```

still works because the Java language specifically supports arrays in the enhanced `for` statement.

---

# 24. `Iterable` vs Array

| Feature                             | `Iterable` object            | Array        |
| ----------------------------------- | ---------------------------- | ------------ |
| Is `Iterable` interface involved?   | Yes                          | No           |
| `iterator()`                        | Available through `Iterable` | No           |
| Enhanced `for`                      | Yes                          | Yes          |
| `Iterator` can be obtained directly | Yes                          | Not directly |
| Collection Framework object         | Usually                      | No           |

---

# 25. Important `Iterable` method

For the basic Collections Framework understanding, remember:

```java
iterator()
```

Its purpose:

> Returns an iterator over the elements.

Conceptually:

```text
Iterable
    |
 iterator()
    |
    ↓
Iterator
```

---

# 26. What exactly is returned by `iterator()`?

Not an element.

It returns an **Iterator object**.

Wrong understanding:

```text
iterator()
    ↓
10
```

Correct:

```text
iterator()
    ↓
Iterator object
    ↓
next()
    ↓
10
```

This distinction is extremely important.

---

# 27. Does `iterator()` return the first element?

**No.**

Example:

```java
Iterator itr = al.iterator();
```

This does not mean:

```text
itr = first element
```

It means:

```text
itr = Iterator object
```

Then:

```java
itr.next();
```

is used to obtain the next element.

---

# 28. Does `hasNext()` return the next element?

**No.**

It returns:

```text
true
```

or:

```text
false
```

Example:

```java
if(itr.hasNext())
{
    System.out.println(itr.next());
}
```

The first method checks.

The second method retrieves.

---

# 29. What happens if `next()` is called when no element exists?

If the iterator has reached the end and `next()` is called again, Java throws:

```text
NoSuchElementException
```

Example conceptually:

```java
while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

is safe because `hasNext()` is checked first.

But:

```java
itr.next();
itr.next();
itr.next();
itr.next();
```

can cause an exception if the collection contains only three elements.

---

# 30. Why use `hasNext()` before `next()`?

Because we don't know how many elements remain.

The standard pattern is:

```java
while(itr.hasNext())
{
    Object x = itr.next();

    System.out.println(x);
}
```

Think:

```text
CHECK
 ↓
hasNext()

If TRUE
 ↓
next()

Repeat
```

---

# 31. Can an Iterator traverse only List?

**No.**

It can be used with many collection implementations.

For example:

```text
ArrayList
LinkedList
Vector
HashSet
LinkedHashSet
TreeSet
PriorityQueue
```

and others that provide the relevant iteration mechanism.

---

# 32. Does iteration mean sorting?

**No.**

Iteration only means visiting elements.

For example:

```text
[50, 10, 30]
```

Iteration may produce:

```text
50 → 10 → 30
```

It doesn't automatically become:

```text
10 → 30 → 50
```

Sorting is a separate operation.

---

# 33. Does iteration mean insertion order?

**No.**

Iteration means traversal according to the collection's iteration behavior.

For example, `HashSet` does not guarantee insertion order.

So:

```java
hs.add(30);
hs.add(10);
hs.add(20);
```

doesn't mean iteration must produce:

```text
30
10
20
```

Nor should you assume any particular order.

---

# 34. `Iterable` and Collection hierarchy

The important hierarchy is:

```text
                         Iterable
                            |
                       Collection
                            |
              ______________|______________
             |              |              |
            List           Set           Queue
             |              |              |
       ArrayList         HashSet      PriorityQueue
       LinkedList        LinkedHashSet     |
       Vector            TreeSet         Deque
          |
        Stack
```

And:

```text
Deque
 |
 +-- ArrayDeque
 `-- LinkedList
```

---

# 35. Where does Map fit?

Map is separate:

```text
               Collections Framework
                        |
             ___________|___________
            |                       |
       Collection                  Map
            |                       |
       List/Set/Queue        HashMap/TreeMap/...
```

Therefore:

```text
Map
 ↓
not Collection
 ↓
not a subtype of Collection
```

And you should not think of `Map` as inheriting `Iterable` through `Collection`.

---

# 36. Why doesn't Map use `Iterable` directly?

A normal Collection represents individual elements:

```text
10
20
30
```

A Map represents associations:

```text
101 → Ravi
102 → John
103 → Kiran
```

There are multiple things that can be traversed:

```text
keys
values
entries
```

Therefore Map provides views such as:

```java
keySet()
values()
entrySet()
```

Those views can then be traversed.

We will study this properly in the Map topic.

---

# 37. `Iterable` vs `Collection`

| `Iterable`                       | `Collection`                    |
| -------------------------------- | ------------------------------- |
| Interface                        | Interface                       |
| Focuses on iteration             | Focuses on groups of elements   |
| Provides `iterator()`            | Provides collection operations  |
| Parent of `Collection`           | Extends `Iterable`              |
| Can be implemented independently | Specialized collection contract |

Relationship:

```text
Iterable
   ↑
Collection
```

---

# 38. `Iterable` vs `Iterator`

| `Iterable`                             | `Iterator`                           |
| -------------------------------------- | ------------------------------------ |
| Says an object can provide an iterator | Performs traversal                   |
| Provides `iterator()`                  | Provides traversal methods           |
| Used by the source object              | Used to move through source elements |
| `iterator()` returns Iterator          | `hasNext()`, `next()` etc.           |

### Memory trick

```text
Iterable
"I can be iterated."

Iterator
"I am doing the iteration."
```

---

# 39. Complete working flow

Suppose:

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
```

The complete conceptual flow is:

```text
                ArrayList
                   |
                   ↓
               Collection
                   |
                   ↓
                Iterable
                   |
             iterator()
                   |
                   ↓
                Iterator
                   |
            ______________
           |              |
       hasNext()        next()
           |              |
       checks          returns
      availability     element
```

---

# 40. Complete program

```java
import java.util.*;

class IterableCompleteDemo
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

            System.out.println("Element = " + value);
        }
    }
}
```

### Output

```text
Element = 100
Element = 200
Element = 300
Element = 400
```

### Program flow

```text
ArrayList created
      ↓
Elements added
      ↓
iterator() called
      ↓
Iterator created
      ↓
hasNext()
      ↓
next()
      ↓
Element printed
      ↓
Repeat
      ↓
hasNext() = false
      ↓
Loop ends
```

---

# 41. Why `Object` is used in our programs

We are intentionally using:

```java
Object
```

rather than:

```java
Integer
String
```

or Generic syntax such as:

```java
Iterator<Integer>
```

because this Collections training is currently being done **without Generics**.

Example:

```java
Iterator itr = al.iterator();

while(itr.hasNext())
{
    Object value = itr.next();
}
```

This is the traditional/raw style we are using for the current roadmap.

---

# 42. One more important distinction: `Iterable` does not store elements

This is another conceptual trap.

`Iterable` itself is an **interface**.

It doesn't mean:

```text
Iterable = storage container
```

Instead:

```text
Iterable = iteration contract
```

An implementation such as:

```text
ArrayList
HashSet
TreeSet
```

actually manages the collection's elements.

`Iterable` provides the standard iteration contract.

---

# 43. Does `Iterable` decide how elements are stored?

**No.**

For example:

```text
ArrayList
 ↓
array-based internal structure

LinkedList
 ↓
linked-node structure

HashSet
 ↓
hash-based structure

TreeSet
 ↓
tree-based sorted structure
```

Yet they can all provide iteration.

Therefore:

> **`Iterable` specifies the iteration capability, not the internal storage mechanism.**

---

# 44. Does `Iterable` decide the iteration order?

**No.**

The actual collection implementation determines its iteration behavior.

For example:

```text
ArrayList
 → list order

LinkedHashSet
 → insertion-order iteration

TreeSet
 → sorted-order iteration

HashSet
 → no guaranteed iteration order
```

Therefore:

> **Iterable provides the mechanism; the implementation determines what order is observed.**

---

# 45. Does `Iterable` provide `hasNext()`?

**No.**

This is another common confusion.

```text
Iterable
   |
iterator()
   ↓
Iterator
   |
   +-- hasNext()
   +-- next()
```

So:

```text
iterator() → Iterable
```

while:

```text
hasNext()
next()
```

belong to the `Iterator` side of the relationship.

---

# 46. Does `Iterable` provide `next()`?

**No.**

`next()` belongs to `Iterator`.

Correct:

```text
Iterable
    |
iterator()
    ↓
Iterator
    |
    +-- hasNext()
    `-- next()
```

---

# 47. Does `Iterable` remove elements?

Not through `Iterable` itself.

The `Iterable` abstraction is primarily about obtaining an iterator.

Removal, if supported, is associated with the collection/iterator APIs and the specific implementation.

So don't confuse:

```text
Iterable
```

with:

```text
Collection modification methods
```

---

# 48. Does every class implementing `Iterable` have to use `ArrayList`?

**No.**

`Iterable` is an interface.

Any class can implement it and define an appropriate iteration mechanism.

Conceptually:

```text
Class A implements Iterable
Class B implements Iterable
Class C implements Iterable
```

Each can provide its own `iterator()` behavior.

---

# 49. Why is `Iterable` important in Java Collections?

Because it gives Java a **common traversal contract**.

Without needing to know whether the object is:

```text
ArrayList
LinkedList
HashSet
TreeSet
Vector
```

we can use the standard iteration mechanism.

This is an example of **abstraction**.

```text
Different implementations
          ↓
Common interface
          ↓
Common iteration mechanism
```

---

# 50. Final Deep-Dive Summary

## Definition

> `Iterable` is a Java interface that represents an object capable of providing an iterator for traversing its elements.

## Package

```text
java.lang
```

## Important method

```java
iterator()
```

## Return type

```text
Iterator
```

## Relationship

```text
Iterable
   ↑
Collection
```

## Traversal

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
element
```

## Enhanced for loop

Works with:

```text
Arrays
+
Iterable objects
```

## Important distinction

```text
Iterable ≠ Iterator
```

```text
Iterable → provides Iterator
Iterator → performs traversal
```

## Array distinction

```text
Array
   ↓
does NOT implement Iterable
```

but:

```text
for-each
   ↓
supports arrays specially
```

## Map distinction

```text
Map
   ↓
not Collection
   ↓
not under Collection → Iterable hierarchy
```

Map's views such as `keySet()`, `values()`, and `entrySet()` can be traversed.

---

# 🔥 THE COMPLETE MEMORY DIAGRAM

```text
                         Iterable
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
             "Anything             "Give me
              left?"                next element"
                 |                     |
                 └──────────┬──────────┘
                            ↓
                         Element
```

And the Collections relationship:

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
       LinkedList        TreeSet            |
       Vector            LinkedHashSet     Deque
          |
        Stack

                Map  ← separate branch
```

> **The core idea to carry into the next topics is:** `Iterable` does not represent the storage of data; it represents the **ability to provide an `Iterator` so that elements can be traversed in a standard way**.
