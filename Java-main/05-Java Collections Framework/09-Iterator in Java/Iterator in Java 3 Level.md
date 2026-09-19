# 9. Iterator in Java — 3LEVEL

**Training rule: No Generics.**
All programs below use normal/raw collection syntax.

The purpose of **3LEVEL** is to understand each concept at three depths:

```text
LEVEL 1 → Basic understanding
LEVEL 2 → Working knowledge
LEVEL 3 → Deep/interview understanding
```

---

# 1. Iterator

## LEVEL 1 — Basic Understanding

### What is Iterator?

`Iterator` is an interface from the `java.util` package.

It is used to **traverse collection elements one by one in the forward direction**.

Think of it like a pointer moving through a collection:

```text
[A] [B] [C] [D]
 ↑
Iterator
```

Then:

```text
[A] [B] [C] [D]
     ↑
```

Then:

```text
[A] [B] [C] [D]
          ↑
```

### Basic syntax

```java
Iterator itr = collection.iterator();
```

### Important methods

```text
hasNext()
next()
remove()
```

---

## Basic Program

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
            System.out.println(itr.next());
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

# LEVEL 2 — Working Knowledge

## 1. `hasNext()`

Checks whether another element is available.

```java
itr.hasNext();
```

Returns:

```text
true  → another element exists
false → no more elements
```

It **does not retrieve** the element.

---

## 2. `next()`

Returns the next element and advances the iterator.

```java
itr.next();
```

Remember:

```text
hasNext() → checks
next()    → retrieves + moves forward
```

---

## 3. `remove()`

Removes the element most recently returned by `next()`.

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

### Important sequence

```text
next()
  ↓
element returned
  ↓
remove()
  ↓
that element removed
```

---

# LEVEL 3 — Deep Understanding

## Iterator and collection modification

Suppose an Iterator is being used:

```java
Iterator itr = list.iterator();
```

and the collection is directly modified during traversal:

```java
list.remove("B");
```

This can result in:

```text
ConcurrentModificationException
```

Instead, use:

```java
itr.remove();
```

when removing the element returned by the iterator.

### Important distinction

```text
list.remove()
     ↓
direct collection modification

itr.remove()
     ↓
iterator-controlled removal
```

---

## Iterator limitations

Iterator can:

```text
✓ Traverse forward
✓ Retrieve elements
✓ Remove elements
```

Iterator cannot:

```text
✗ Traverse backward
✗ Add elements
✗ Replace elements using set()
✗ Provide list indexes
```

That leads us to **ListIterator**.

---

# 2. ListIterator

## LEVEL 1 — Basic Understanding

### What is ListIterator?

`ListIterator` is an interface used to traverse **List implementations**.

It is more powerful than Iterator.

The biggest difference:

```text
Iterator
   ↓
Forward only

ListIterator
   ↓
Forward + Backward
```

Basic syntax:

```java
ListIterator itr = list.listIterator();
```

---

## Basic Program

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
            System.out.println(itr.next());
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

# LEVEL 2 — Working Knowledge

ListIterator provides:

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

The most important new methods are:

```text
hasPrevious()
previous()
```

---

## `hasPrevious()`

Checks whether an element exists behind the current iterator position.

```java
itr.hasPrevious();
```

---

## `previous()`

Returns the previous element and moves backward.

```java
itr.previous();
```

---

## Forward + Backward Program

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

# LEVEL 3 — Deep Understanding

## ListIterator `add()`

ListIterator can insert an element during traversal.

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

## ListIterator `set()`

`set()` replaces the last element returned by `next()` or `previous()`.

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

### Remember

```text
add() → insert new element

set() → replace existing element
```

---

## ListIterator index methods

### `nextIndex()`

Returns the index of the element that would be returned by the next `next()` call.

### `previousIndex()`

Returns the index of the element that would be returned by the next `previous()` call.

Therefore ListIterator provides considerably more control over List traversal than Iterator.

---

# 3. Enumeration

## LEVEL 1 — Basic Understanding

### What is Enumeration?

`Enumeration` is a **legacy interface** used for traversing elements.

It belongs to:

```java
java.util
```

It is mainly associated with older classes such as:

```text
Vector
Hashtable
```

Its two main methods are:

```text
hasMoreElements()
nextElement()
```

---

## Basic Program

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

# LEVEL 2 — Working Knowledge

## `hasMoreElements()`

Checks whether another element is available.

```java
e.hasMoreElements();
```

Returns:

```text
true / false
```

It is conceptually similar to:

```java
itr.hasNext();
```

---

## `nextElement()`

Returns the next element.

```java
e.nextElement();
```

It is conceptually similar to:

```java
itr.next();
```

Therefore:

```text
Iterator:

hasNext()
next()


Enumeration:

hasMoreElements()
nextElement()
```

---

# LEVEL 3 — Deep Understanding

Enumeration has significantly fewer capabilities than ListIterator.

It provides:

```text
✓ Forward traversal
✓ Check for another element
✓ Retrieve next element
```

It does not provide:

```text
✗ Backward traversal
✗ remove()
✗ add()
✗ set()
✗ index methods
```

It is called **legacy** because it belongs to the older Java collection API.

Modern code generally prefers Iterator/ListIterator depending on the requirement, but Enumeration remains important when working with legacy APIs such as Vector and Hashtable.

---

# 4. Iterator vs ListIterator vs Enumeration

## LEVEL 1 — Basic Difference

```text
Iterator
   ↓
Forward

ListIterator
   ↓
Forward + Backward

Enumeration
   ↓
Legacy Forward
```

---

# LEVEL 2 — Method Difference

| Feature    | Iterator    | ListIterator | Enumeration         |
| ---------- | ----------- | ------------ | ------------------- |
| Forward    | Yes         | Yes          | Yes                 |
| Backward   | No          | Yes          | No                  |
| Check next | `hasNext()` | `hasNext()`  | `hasMoreElements()` |
| Get next   | `next()`    | `next()`     | `nextElement()`     |
| Remove     | Yes         | Yes          | No                  |
| Add        | No          | Yes          | No                  |
| Replace    | No          | Yes          | No                  |

---

# LEVEL 3 — Interview-Level Difference

| Feature           | Iterator                     | ListIterator       | Enumeration      |
| ----------------- | ---------------------------- | ------------------ | ---------------- |
| Purpose           | General collection traversal | List traversal     | Legacy traversal |
| Direction         | Forward                      | Forward + backward | Forward          |
| `remove()`        | Yes                          | Yes                | No               |
| `add()`           | No                           | Yes                | No               |
| `set()`           | No                           | Yes                | No               |
| Index information | No                           | Yes                | No               |
| List-specific     | No                           | Yes                | No               |
| Legacy            | No                           | No                 | Yes              |

---

# 5. The Most Important Relationship

Remember:

```text
             Iterator
                ↑
          ListIterator
```

`ListIterator` extends `Iterator`.

Therefore, ListIterator has the basic Iterator functionality and adds additional capabilities.

Conceptually:

```text
Iterator
   │
   ├── hasNext()
   ├── next()
   └── remove()
   
        ↓ extended by

ListIterator
   │
   ├── hasPrevious()
   ├── previous()
   ├── add()
   ├── set()
   ├── nextIndex()
   └── previousIndex()
```

---

# 6. Final 3-Level Memory Map

## Level 1 — Remember the direction

```text
Iterator      → Forward
ListIterator  → Forward + Backward
Enumeration   → Forward + Legacy
```

## Level 2 — Remember the power

```text
Iterator
→ hasNext()
→ next()
→ remove()

ListIterator
→ hasNext()
→ next()
→ hasPrevious()
→ previous()
→ add()
→ remove()
→ set()

Enumeration
→ hasMoreElements()
→ nextElement()
```

## Level 3 — Remember the purpose

```text
Iterator
→ General modern traversal

ListIterator
→ Powerful traversal specifically for Lists

Enumeration
→ Legacy traversal mechanism
```

### One-line exam answer

> **Iterator provides forward traversal, ListIterator provides forward and backward traversal with additional modification/index operations, and Enumeration is a legacy forward-traversal mechanism.**

**And throughout this topic: zero Generics.**
