# 9. Iterator in Java — DEEPDIVE

**Training rule:** We will **not use Generics** anywhere in these examples. Generics will be covered separately in Topic 14.

The Iterator topic has three important traversal mechanisms:

```text
9. Iterator
│
├── Iterator
├── ListIterator
└── Enumeration
```

The central idea is simple:

> A collection stores multiple objects. An iterator provides a controlled way to visit those objects one by one.

---

# PART 1 — Iterator

## 1. What is Iterator?

`Iterator` is an interface in the `java.util` package.

It is used to traverse elements of a collection **one by one in the forward direction**.

Basic syntax:

```java
Iterator itr = collection.iterator();
```

Example:

```java
ArrayList list = new ArrayList();

list.add("A");
list.add("B");
list.add("C");

Iterator itr = list.iterator();
```

Now `itr` is connected to the collection and can be used to traverse it.

---

# 2. Why do we need Iterator?

Suppose we have:

```java
ArrayList list = new ArrayList();

list.add("A");
list.add("B");
list.add("C");
```

We want to access every element one by one.

Iterator provides a standard mechanism:

```text
Collection
   ↓
iterator()
   ↓
Iterator object
   ↓
hasNext()
   ↓
next()
   ↓
hasNext()
   ↓
next()
   ↓
...
```

Instead of depending on indexes, Iterator allows traversal through the collection.

---

# 3. Iterator methods

Iterator mainly provides three methods:

```text
hasNext()
next()
remove()
```

Let's understand each separately.

---

## 3.1 `hasNext()`

### Definition

`hasNext()` checks whether another element is available for traversal.

Return type:

```java
boolean
```

Example:

```java
if(itr.hasNext())
{
    System.out.println("Element available");
}
```

It returns:

```text
true
```

if another element exists.

Otherwise:

```text
false
```

### Important

`hasNext()` does **not** return the element.

It only checks.

```text
hasNext()
   ↓
"Is another element available?"
```

---

# 4. `next()`

### Definition

`next()` returns the next element in the iteration and advances the iterator position.

Example:

```java
Object obj = itr.next();
```

Because we are deliberately not using Generics, the returned object is commonly handled as `Object` and may need casting when assigning it to a specific type.

Example:

```java
String name = (String)itr.next();
```

---

# 5. `hasNext()` vs `next()`

This causes many beginner mistakes.

### `hasNext()`

```java
itr.hasNext();
```

means:

> Is another element available?

### `next()`

```java
itr.next();
```

means:

> Give me the next element and move forward.

Therefore:

```text
hasNext() → checks
next()    → retrieves + advances
```

---

# 6. Basic Iterator Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        list.add("Ravi");
        list.add("Kiran");
        list.add("Basha");

        Iterator itr = list.iterator();

        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }
    }
}
```

Output:

```text
Ravi
Kiran
Basha
```

---

# 7. How the Iterator moves

Initially:

```text
[A] [B] [C]
 ^
 iterator position
```

Calling:

```java
itr.next();
```

returns:

```text
A
```

and advances.

Conceptually:

```text
[A] [B] [C]
     ^
```

Next:

```java
itr.next();
```

returns:

```text
B
```

Then:

```text
[A] [B] [C]
         ^
```

Then:

```java
itr.next();
```

returns:

```text
C
```

---

# 8. What happens if `next()` is called when there is no element?

Consider:

```java
ArrayList list = new ArrayList();

Iterator itr = list.iterator();

itr.next();
```

There is no next element.

This results in:

```text
NoSuchElementException
```

Therefore, normally use:

```java
while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

rather than blindly calling `next()`.

---

# 9. Iterator `remove()`

Iterator also provides:

```java
remove()
```

It removes the element **most recently returned by `next()`**.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        list.add("A");
        list.add("B");
        list.add("C");

        Iterator itr = list.iterator();

        while(itr.hasNext())
        {
            String value = (String)itr.next();

            if(value.equals("B"))
            {
                itr.remove();
            }
        }

        System.out.println(list);
    }
}
```

Output:

```text
[A, C]
```

---

# 10. Why use `Iterator.remove()`?

This is an important practical point.

Suppose you are traversing a collection and want to remove the current element.

Using the iterator's `remove()` method is the supported way to remove the element returned by the iterator.

Example:

```java
Iterator itr = list.iterator();

while(itr.hasNext())
{
    String value = (String)itr.next();

    if(value.equals("B"))
    {
        itr.remove();
    }
}
```

---

# 11. Can we call `remove()` before `next()`?

Normally, no.

Example:

```java
Iterator itr = list.iterator();

itr.remove();
```

There is no element that has been returned by `next()` yet.

This can result in:

```text
IllegalStateException
```

The basic rule is:

```text
next()
  ↓
element returned
  ↓
remove()
```

---

# 12. Can we call `remove()` twice for one `next()`?

No.

For a normal Iterator usage pattern:

```java
itr.next();
itr.remove();
itr.remove();
```

the second `remove()` is illegal because there is no new element returned by `next()` since the previous removal.

Again, this can result in:

```text
IllegalStateException
```

Remember:

```text
next()
remove()
next()
remove()
```

is the safe conceptual pattern.

---

# 13. Iterator and `for-each`

The enhanced `for` loop:

```java
for(Object obj : list)
{
    System.out.println(obj);
}
```

is internally based on the iterable/iterator mechanism.

But when you need explicit control over traversal, especially removal through the iterator, using an explicit Iterator is useful:

```java
Iterator itr = list.iterator();

while(itr.hasNext())
{
    Object obj = itr.next();

    // operations
}
```

---

# PART 2 — ListIterator

# 14. What is ListIterator?

`ListIterator` is a sub-interface of `Iterator` designed specifically for **List implementations**.

It provides more functionality than Iterator.

Most importantly:

```text
Iterator
   ↓
Forward only

ListIterator
   ↓
Forward + backward
```

It belongs to:

```java
java.util
```

---

# 15. Where can ListIterator be used?

ListIterator is designed for Lists.

For example:

```java
ArrayList
LinkedList
Vector
```

You can obtain one using:

```java
ListIterator itr = list.listIterator();
```

It is not a general iterator for Set implementations such as:

```text
HashSet
TreeSet
```

because ListIterator is specifically associated with List traversal.

---

# 16. ListIterator methods

Important methods:

```text
hasNext()
next()

hasPrevious()
previous()

remove()
add()
set()

nextIndex()
previousIndex()
```

The additional functionality is what makes ListIterator more powerful.

---

# 17. `hasNext()` in ListIterator

Same basic meaning as Iterator:

```java
itr.hasNext();
```

It checks whether an element exists in the forward direction.

---

# 18. `next()` in ListIterator

```java
itr.next();
```

returns the next element and moves forward.

---

# 19. `hasPrevious()`

This is one of the major differences.

```java
itr.hasPrevious();
```

checks whether an element exists in the backward direction.

It returns:

```text
true
```

or:

```text
false
```

---

# 20. `previous()`

```java
itr.previous();
```

returns the previous element and moves backward.

Therefore:

```text
Iterator:

→ → → →


ListIterator:

← ← ←
  ↕
→ → →
```

---

# 21. Forward and Backward Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        list.add("A");
        list.add("B");
        list.add("C");

        ListIterator itr = list.listIterator();

        System.out.println("Forward:");

        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }

        System.out.println("Backward:");

        while(itr.hasPrevious())
        {
            System.out.println(itr.previous());
        }
    }
}
```

Output:

```text
Forward:
A
B
C

Backward:
C
B
A
```

---

# 22. Why does backward traversal work only after moving forward?

This is a very common doubt.

When we initially create:

```java
ListIterator itr = list.listIterator();
```

the iterator starts at the beginning of the list.

Conceptually:

```text
[A] [B] [C]
 ^
```

At this position:

```java
itr.hasPrevious()
```

is false.

Now we move forward:

```java
itr.next();
itr.next();
itr.next();
```

The iterator reaches the end:

```text
[A] [B] [C]
             ^
```

Now:

```java
itr.hasPrevious()
```

is true.

Therefore:

```java
itr.previous();
```

can move backward.

---

# 23. ListIterator `add()`

ListIterator can add an element during traversal.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        list.add("A");
        list.add("C");

        ListIterator itr = list.listIterator();

        while(itr.hasNext())
        {
            String value = (String)itr.next();

            if(value.equals("A"))
            {
                itr.add("B");
            }
        }

        System.out.println(list);
    }
}
```

Output:

```text
[A, B, C]
```

---

# 24. ListIterator `set()`

`set()` replaces the last element returned by `next()` or `previous()`.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        list.add("A");
        list.add("B");
        list.add("C");

        ListIterator itr = list.listIterator();

        while(itr.hasNext())
        {
            String value = (String)itr.next();

            if(value.equals("B"))
            {
                itr.set("X");
            }
        }

        System.out.println(list);
    }
}
```

Output:

```text
[A, X, C]
```

---

# 25. `add()` vs `set()`

This is a very important difference.

### `add()`

Adds a **new element**.

```text
A B C
  ↓
add(X)

A B X C
```

### `set()`

Replaces the **last element returned**.

```text
A B C
  ↓
set(X)

A X C
```

So:

```text
add() → insert
set() → replace
```

---

# 26. ListIterator `remove()`

Like Iterator, ListIterator can remove the last element returned by `next()` or `previous()`.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        list.add("A");
        list.add("B");
        list.add("C");

        ListIterator itr = list.listIterator();

        while(itr.hasNext())
        {
            String value = (String)itr.next();

            if(value.equals("B"))
            {
                itr.remove();
            }
        }

        System.out.println(list);
    }
}
```

Output:

```text
[A, C]
```

---

# 27. `nextIndex()`

`nextIndex()` returns the index of the element that would be returned by the next call to `next()`.

Example:

```java
ListIterator itr = list.listIterator();

System.out.println(itr.nextIndex());
```

Initially, the result is:

```text
0
```

After calling:

```java
itr.next();
```

the next index becomes:

```text
1
```

---

# 28. `previousIndex()`

`previousIndex()` returns the index of the element that would be returned by the next call to `previous()`.

At the beginning:

```text
previousIndex() → -1
```

This makes sense because there is no element before index `0`.

---

# 29. Iterator vs ListIterator

This difference should be completely clear:

```text
Iterator
   ↓
Forward only
   ↓
hasNext()
next()
remove()
```

```text
ListIterator
   ↓
Forward + backward
   ↓
hasNext()
next()
hasPrevious()
previous()
remove()
add()
set()
nextIndex()
previousIndex()
```

---

# PART 3 — Enumeration

# 30. What is Enumeration?

`Enumeration` is a **legacy interface** used to traverse elements.

It predates the modern Collections Framework iterator APIs.

It is mainly associated with legacy classes such as:

```text
Vector
Hashtable
```

It belongs to:

```java
java.util
```

---

# 31. Why does Enumeration still exist?

Because Java maintains backward compatibility.

Older Java programs used classes such as:

```text
Vector
Hashtable
```

and their traversal mechanisms.

Modern code generally uses:

```text
Iterator
ListIterator
```

or other modern traversal mechanisms depending on the collection.

---

# 32. Enumeration methods

Enumeration has two main methods:

```text
hasMoreElements()
nextElement()
```

---

# 33. `hasMoreElements()`

Checks whether another element is available.

```java
e.hasMoreElements();
```

returns:

```text
true / false
```

It is conceptually similar to:

```java
itr.hasNext();
```

---

# 34. `nextElement()`

Returns the next element.

```java
e.nextElement();
```

It is conceptually similar to:

```java
itr.next();
```

---

# 35. Basic Enumeration Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Vector v = new Vector();

        v.add("A");
        v.add("B");
        v.add("C");

        Enumeration e = v.elements();

        while(e.hasMoreElements())
        {
            System.out.println(e.nextElement());
        }
    }
}
```

Output:

```text
A
B
C
```

---

# 36. Enumeration with Hashtable

Enumeration can also be obtained from a Hashtable's legacy traversal methods.

For example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Hashtable table = new Hashtable();

        table.put(101, "Ravi");
        table.put(102, "Kiran");
        table.put(103, "Basha");

        Enumeration e = table.elements();

        while(e.hasMoreElements())
        {
            System.out.println(e.nextElement());
        }
    }
}
```

This enumerates the **values** returned by `elements()`.

---

# 37. Does Enumeration support `remove()`?

**No.**

Enumeration provides:

```text
hasMoreElements()
nextElement()
```

It does not provide the Iterator-style:

```text
remove()
```

This is one reason Iterator is more flexible.

---

# 38. Can Enumeration move backward?

**No.**

Enumeration provides forward traversal only.

```text
Enumeration

→ → → →
```

It does not have:

```text
previous()
hasPrevious()
```

---

# 39. Enumeration vs Iterator

| Feature              | Iterator    | Enumeration         |
| -------------------- | ----------- | ------------------- |
| Modern traversal API | Yes         | Legacy              |
| Forward traversal    | Yes         | Yes                 |
| Backward traversal   | No          | No                  |
| Check next           | `hasNext()` | `hasMoreElements()` |
| Get next             | `next()`    | `nextElement()`     |
| Remove               | Yes         | No                  |
| Common association   | Collections | Vector/Hashtable    |

---

# PART 4 — The Most Important Concept: Cursor Position

Understanding cursor position removes many Iterator doubts.

Suppose:

```text
[A] [B] [C]
```

A ListIterator conceptually sits **between elements**, not directly "on" an element.

Initially:

```text
 | A | B | C |
 ^
```

Calling:

```java
itr.next();
```

returns `A` and moves:

```text
 | A | B | C |
     ^
```

Calling `next()` again:

```text
 | A | B | C |
         ^
```

returns `B`.

Calling:

```java
itr.previous();
```

moves backward and returns the element immediately before the cursor.

This cursor model is particularly useful for understanding:

```text
next()
previous()
nextIndex()
previousIndex()
add()
remove()
set()
```

---

# PART 5 — Fail-Fast Modification Problem

This is an important deep-dive concept.

Suppose an Iterator is traversing an `ArrayList`:

```java
Iterator itr = list.iterator();
```

and you structurally modify the list directly while using the iterator:

```java
list.add("X");
```

The iterator may detect this modification and throw:

```text
ConcurrentModificationException
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        list.add("A");
        list.add("B");
        list.add("C");

        Iterator itr = list.iterator();

        while(itr.hasNext())
        {
            String value = (String)itr.next();

            if(value.equals("B"))
            {
                list.remove("B");
            }
        }
    }
}
```

This can result in:

```text
ConcurrentModificationException
```

---

# 40. Then how should we remove while iterating?

Use the iterator's own `remove()`:

```java
Iterator itr = list.iterator();

while(itr.hasNext())
{
    String value = (String)itr.next();

    if(value.equals("B"))
    {
        itr.remove();
    }
}
```

This is the proper Iterator-based removal mechanism.

---

# PART 6 — Complete Comparison

| Feature                        | Iterator    | ListIterator | Enumeration      |
| ------------------------------ | ----------- | ------------ | ---------------- |
| Interface                      | Yes         | Yes          | Yes              |
| Package                        | `java.util` | `java.util`  | `java.util`      |
| Modern API                     | Yes         | Yes          | Legacy           |
| Forward traversal              | ✅           | ✅            | ✅                |
| Backward traversal             | ❌           | ✅            | ❌                |
| `hasNext()`                    | ✅           | ✅            | ❌                |
| `next()`                       | ✅           | ✅            | ❌                |
| `hasPrevious()`                | ❌           | ✅            | ❌                |
| `previous()`                   | ❌           | ✅            | ❌                |
| `remove()`                     | ✅           | ✅            | ❌                |
| `add()`                        | ❌           | ✅            | ❌                |
| `set()`                        | ❌           | ✅            | ❌                |
| Index methods                  | ❌           | ✅            | ❌                |
| Designed for List specifically | ❌           | ✅            | ❌                |
| Common legacy use              | ❌           | ❌            | Vector/Hashtable |

---

# PART 7 — Most Common Interview Doubts

## Q1. Which is the parent interface?

`ListIterator` extends `Iterator`.

Conceptually:

```text
Iterator
   ↑
ListIterator
```

So ListIterator gets the Iterator functionality and adds more capabilities.

---

## Q2. Which can traverse backward?

Only:

```text
ListIterator
```

among these three.

---

## Q3. Which is legacy?

```text
Enumeration
```

---

## Q4. Which supports removal?

```text
Iterator      → Yes
ListIterator  → Yes
Enumeration   → No
```

---

## Q5. Which supports adding while traversing?

```text
ListIterator → Yes
Iterator     → No
Enumeration  → No
```

---

## Q6. Which supports replacing an element?

```text
ListIterator → set()
```

Iterator and Enumeration do not provide `set()`.

---

## Q7. Can ListIterator be used with HashSet?

**No.**

ListIterator is for List implementations.

---

## Q8. Can Iterator be used with Set?

**Yes.**

For example:

```java
HashSet set = new HashSet();

Iterator itr = set.iterator();
```

This is one of the advantages of Iterator: it provides a common traversal mechanism for many collection types.

---

# PART 8 — Final Mental Model

Think of the three as three different levels of traversal capability:

```text
                 TRAVERSAL
                     │
        ┌────────────┼────────────┐
        │            │            │
    Iterator    ListIterator   Enumeration
        │            │            │
    Forward      Forward +      Forward
      only        Backward       only
        │            │            │
    remove()    add/remove/       Legacy
                  set()
```

### One-line memory

> **Iterator = forward traversal.**
> **ListIterator = forward + backward + modification operations.**
> **Enumeration = old/legacy forward traversal.**

And the three method pairs should be permanently clear:

```text
Iterator:
hasNext()       → next()

ListIterator:
hasNext()       → next()
hasPrevious()   → previous()

Enumeration:
hasMoreElements() → nextElement()
```

**No Generics are used anywhere in this DEEPDIVE.**
