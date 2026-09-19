# 3. Collection Interface in Java — ONEPAGE

> **Training rule:** No Generics are used. All examples use **normal/raw collection syntax** so that the Collection Framework is learned first. Generics will be covered separately.

---

# 1. What is `Collection`?

`Collection` is an **interface** in the Java Collections Framework.

It represents a group of objects/elements.

Package:

```java
java.util
```

Basic hierarchy:

```text
Iterable
   ↑
Collection
   ↑
 ┌─┼───────────┐
List          Set         Queue
```

Examples of classes implementing `Collection`:

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
```

> **Important:** `Map` is NOT a child of `Collection`. `Map` has a separate hierarchy.

---

# 2. Why do we need the Collection Interface?

Different collection classes have different internal implementations.

For example:

```text
ArrayList
LinkedList
HashSet
TreeSet
```

But many basic operations are common:

```text
add
remove
search
count
check empty
remove everything
traverse
```

Instead of defining these operations separately for every collection, Java provides a common interface:

```text
Collection
   |
   ├── add()
   ├── remove()
   ├── contains()
   ├── size()
   ├── clear()
   └── ...
```

This is the main purpose of the `Collection` interface.

---

# 3. Collection Interface Methods

The important methods in your training list are:

| Method          | Purpose                                          |
| --------------- | ------------------------------------------------ |
| `add()`         | Add one element                                  |
| `addAll()`      | Add all elements from another collection         |
| `remove()`      | Remove an element                                |
| `removeAll()`   | Remove matching elements from another collection |
| `contains()`    | Check whether an element exists                  |
| `containsAll()` | Check whether all specified elements exist       |
| `size()`        | Number of elements                               |
| `isEmpty()`     | Check whether collection has no elements         |
| `clear()`       | Remove all elements                              |
| `iterator()`    | Obtain Iterator                                  |
| `toArray()`     | Convert collection elements to an array          |

---

# 4. `add()`

## Purpose

Adds one element to the collection.

Syntax:

```java
collection.add(element);
```

Example:

```java
import java.util.*;

class AddDemo
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

### Return value

`add()` returns:

```java
boolean
```

Example:

```java
boolean result = al.add(40);

System.out.println(result);
```

Usually:

```text
true
```

For collections where adding is successful, it returns `true`.

### Important

Different collections may have different behavior regarding duplicates.

```text
List → duplicates allowed
Set  → duplicates generally not allowed
```

---

# 5. `addAll()`

## Purpose

Adds all elements from one collection into another collection.

Syntax:

```java
collection1.addAll(collection2);
```

Example:

```java
import java.util.*;

class AddAllDemo
{
    public static void main(String[] args)
    {
        ArrayList al1 = new ArrayList();
        ArrayList al2 = new ArrayList();

        al1.add(10);
        al1.add(20);

        al2.add(30);
        al2.add(40);

        al1.addAll(al2);

        System.out.println(al1);
    }
}
```

Output:

```text
[10, 20, 30, 40]
```

Conceptually:

```text
al1 = [10, 20]

al2 = [30, 40]

       addAll()
          ↓

al1 = [10, 20, 30, 40]
```

---

# 6. `remove()`

## Purpose

Removes an element from the collection.

Syntax:

```java
collection.remove(element);
```

Example:

```java
import java.util.*;

class RemoveDemo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);

        al.remove(20);

        System.out.println(al);
    }
}
```

Output:

```text
[10, 30]
```

### Return value

`remove()` returns:

```java
boolean
```

It generally returns:

```text
true  → element was removed
false → element was not found
```

---

# 7. `removeAll()`

## Purpose

Removes from one collection all elements that are also present in another collection.

Example:

```text
Collection 1 = [10, 20, 30, 40]
Collection 2 = [20, 40]
```

After:

```java
collection1.removeAll(collection2);
```

we get:

```text
[10, 30]
```

Program:

```java
import java.util.*;

class RemoveAllDemo
{
    public static void main(String[] args)
    {
        ArrayList al1 = new ArrayList();
        ArrayList al2 = new ArrayList();

        al1.add(10);
        al1.add(20);
        al1.add(30);
        al1.add(40);

        al2.add(20);
        al2.add(40);

        al1.removeAll(al2);

        System.out.println(al1);
    }
}
```

Output:

```text
[10, 30]
```

### Memory

```text
remove()
    ↓
remove one matching element

removeAll()
    ↓
remove all matching elements
```

---

# 8. `contains()`

## Purpose

Checks whether an element exists in the collection.

Syntax:

```java
collection.contains(element);
```

Example:

```java
import java.util.*;

class ContainsDemo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);

        System.out.println(al.contains(20));
        System.out.println(al.contains(50));
    }
}
```

Output:

```text
true
false
```

### Return type

```java
boolean
```

Memory:

```text
contains()
     ↓
"Does this element exist?"
     ↓
true / false
```

---

# 9. `containsAll()`

## Purpose

Checks whether **all elements** of one collection are present in another collection.

Example:

```text
al1 = [10, 20, 30, 40]

al2 = [20, 40]
```

```java
al1.containsAll(al2);
```

returns:

```text
true
```

Because both `20` and `40` exist in `al1`.

Example:

```java
import java.util.*;

class ContainsAllDemo
{
    public static void main(String[] args)
    {
        ArrayList al1 = new ArrayList();
        ArrayList al2 = new ArrayList();

        al1.add(10);
        al1.add(20);
        al1.add(30);

        al2.add(10);
        al2.add(30);

        System.out.println(al1.containsAll(al2));
    }
}
```

Output:

```text
true
```

### Memory

```text
contains()
     ↓
one element?

containsAll()
     ↓
all elements?
```

---

# 10. `size()`

## Purpose

Returns the number of elements currently present.

Syntax:

```java
collection.size();
```

Example:

```java
import java.util.*;

class SizeDemo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);

        System.out.println(al.size());
    }
}
```

Output:

```text
3
```

Important:

> `size()` gives the **number of elements**, not the internal capacity.

---

# 11. `isEmpty()`

## Purpose

Checks whether the collection contains zero elements.

Syntax:

```java
collection.isEmpty();
```

Example:

```java
import java.util.*;

class IsEmptyDemo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        System.out.println(al.isEmpty());

        al.add(10);

        System.out.println(al.isEmpty());
    }
}
```

Output:

```text
true
false
```

Memory:

```text
isEmpty()
    ↓
No elements?
    ↓
true / false
```

---

# 12. `clear()`

## Purpose

Removes **all elements** from the collection.

Example:

```java
import java.util.*;

class ClearDemo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);

        System.out.println(al);

        al.clear();

        System.out.println(al);
    }
}
```

Output:

```text
[10, 20, 30]
[]
```

Memory:

```text
remove()
   ↓
one matching element

removeAll()
   ↓
matching elements

clear()
   ↓
EVERY element
```

---

# 13. `iterator()`

## Purpose

Returns an `Iterator` for traversing the collection.

Syntax:

```java
Iterator itr = collection.iterator();
```

Example:

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

Output:

```text
10
20
30
```

Flow:

```text
Collection
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

### Important

`iterator()` does **not** return an element.

It returns an:

```text
Iterator object
```

---

# 14. `toArray()`

## Purpose

Converts the collection's elements into an array.

Syntax:

```java
Object arr[] = collection.toArray();
```

Example:

```java
import java.util.*;

class ToArrayDemo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);

        Object arr[] = al.toArray();

        for(int i = 0; i < arr.length; i++)
        {
            System.out.println(arr[i]);
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

### Important

With the no-argument `toArray()`:

```java
Object arr[] = al.toArray();
```

the returned array is an `Object[]`.

---

# 15. `Collection` Methods — Complete Memory Table

| Method          | Meaning                           | Return     |
| --------------- | --------------------------------- | ---------- |
| `add()`         | Add one element                   | `boolean`  |
| `addAll()`      | Add another collection's elements | `boolean`  |
| `remove()`      | Remove an element                 | `boolean`  |
| `removeAll()`   | Remove matching elements          | `boolean`  |
| `contains()`    | Check one element                 | `boolean`  |
| `containsAll()` | Check all specified elements      | `boolean`  |
| `size()`        | Number of elements                | `int`      |
| `isEmpty()`     | Check zero elements               | `boolean`  |
| `clear()`       | Remove everything                 | `void`     |
| `iterator()`    | Obtain Iterator                   | `Iterator` |
| `toArray()`     | Convert to array                  | `Object[]` |

---

# 16. The Most Important Differences

### `add()` vs `addAll()`

```text
add()
 ↓
one element
```

```text
addAll()
 ↓
another collection
```

---

### `remove()` vs `removeAll()`

```text
remove()
 ↓
one matching element
```

```text
removeAll()
 ↓
all matching elements
```

---

### `contains()` vs `containsAll()`

```text
contains()
 ↓
one element?
```

```text
containsAll()
 ↓
all elements from another collection?
```

---

### `size()` vs `isEmpty()`

```text
size()
 ↓
How many?
```

```text
isEmpty()
 ↓
Are there zero?
```

---

### `clear()` vs `removeAll()`

```text
clear()
 ↓
remove everything
```

```text
removeAll(other)
 ↓
remove elements matching another collection
```

---

# 17. Complete Relationship

```text
                    Iterable
                       ↑
                       |
                  Collection
                       |
        ┌──────────────┼──────────────┐
        ↓              ↓              ↓
       List            Set           Queue
        |
    ArrayList
    LinkedList
    Vector
    Stack
```

The common `Collection` operations include:

```text
                 Collection
                     |
     ┌───────┬───────┼────────┬─────────┐
     ↓       ↓       ↓        ↓         ↓
    add    remove  contains  size     clear
     ↓       ↓       ↓        ↓         ↓
   addAll removeAll containsAll isEmpty iterator
                                      ↓
                                  toArray()
```

---

# 🧠 ONEPAGE FINAL REVISION

```text
add()           → Add ONE element
addAll()        → Add ALL elements from another collection

remove()        → Remove an element
removeAll()     → Remove matching elements

contains()      → Check ONE element
containsAll()   → Check ALL specified elements

size()          → Number of elements
isEmpty()       → Whether zero elements exist

clear()         → Remove EVERYTHING

iterator()      → Get Iterator for traversal

toArray()       → Convert collection to Object[]
```

### 🔥 One-line memory formula

> **ADD → REMOVE → SEARCH → COUNT → EMPTY → CLEAR → TRAVERSE → CONVERT**

```text
ADD       → add(), addAll()
REMOVE    → remove(), removeAll()
SEARCH    → contains(), containsAll()
COUNT     → size()
EMPTY     → isEmpty()
CLEAR     → clear()
TRAVERSE  → iterator()
CONVERT   → toArray()
```

**Core idea:** `Collection` defines the **common operations for groups of objects**, while individual implementations such as `ArrayList`, `HashSet`, and `PriorityQueue` decide **how those operations are internally implemented and what special behavior they provide**.
