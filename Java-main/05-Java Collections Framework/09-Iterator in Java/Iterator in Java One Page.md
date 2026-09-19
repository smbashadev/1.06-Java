# 9. Iterator in Java — ONEPAGE

**Training rule:** No Generics. All programs use normal/raw collection syntax.

The Iterator topic contains three important traversal mechanisms:

```text
Iterator
ListIterator
Enumeration
```

Their common purpose is to **retrieve/traverse elements from a collection one by one**.

---

# 1. Iterator

## Definition

`Iterator` is an interface used to traverse elements of a collection **one by one in the forward direction**.

It belongs to:

```java
java.util
```

Basic syntax:

```java
Iterator itr = collection.iterator();
```

### Important methods

| Method      | Purpose                                       |
| ----------- | --------------------------------------------- |
| `hasNext()` | Checks whether another element exists         |
| `next()`    | Returns the next element                      |
| `remove()`  | Removes the last element returned by `next()` |

---

## Basic Program

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

### How it works

```text
Iterator
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

### Important point

`hasNext()` **checks**.

`next()` **moves/returns**.

Do not confuse them.

---

## Iterator `remove()`

`remove()` removes the element that was most recently returned by `next()`.

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

### Remember

```text
Iterator
   |
   ├── Forward traversal
   ├── hasNext()
   ├── next()
   └── remove()
```

---

# 2. ListIterator

## Definition

`ListIterator` is an iterator specifically designed for **List implementations**.

It can move:

```text
Forward  →
Backward ←
```

It belongs to:

```java
java.util
```

Syntax:

```java
ListIterator itr = list.listIterator();
```

### Important methods

| Method          | Purpose                            |
| --------------- | ---------------------------------- |
| `hasNext()`     | Checks next element                |
| `next()`        | Returns next element               |
| `hasPrevious()` | Checks previous element            |
| `previous()`    | Returns previous element           |
| `add()`         | Adds an element                    |
| `remove()`      | Removes an element                 |
| `set()`         | Replaces the last returned element |

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

## Backward Traversal

After moving to the end:

```java
while(itr.hasNext())
{
    itr.next();
}
```

we can move backward:

```java
while(itr.hasPrevious())
{
    System.out.println(itr.previous());
}
```

Output:

```text
C
B
A
```

### Remember

```text
ListIterator
      |
      ├── Forward  →
      ├── Backward ←
      ├── add()
      ├── remove()
      └── set()
```

---

# 3. Enumeration

## Definition

`Enumeration` is an older, **legacy** interface used to traverse elements.

It is mainly associated with legacy classes such as:

```text
Vector
Hashtable
```

It belongs to:

```java
java.util
```

### Important methods

| Method              | Purpose                               |
| ------------------- | ------------------------------------- |
| `hasMoreElements()` | Checks whether another element exists |
| `nextElement()`     | Returns the next element              |

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

### Remember

```text
Enumeration
      |
      ├── hasMoreElements()
      └── nextElement()
```

---

# Iterator vs ListIterator vs Enumeration

| Feature                        | Iterator  | ListIterator       | Enumeration        |
| ------------------------------ | --------- | ------------------ | ------------------ |
| Type                           | Interface | Interface          | Interface          |
| Traversal                      | Forward   | Forward + backward | Forward            |
| `hasNext()`                    | ✅         | ✅                  | ❌                  |
| `next()`                       | ✅         | ✅                  | ❌                  |
| `hasPrevious()`                | ❌         | ✅                  | ❌                  |
| `previous()`                   | ❌         | ✅                  | ❌                  |
| `remove()`                     | ✅         | ✅                  | ❌                  |
| `add()`                        | ❌         | ✅                  | ❌                  |
| `set()`                        | ❌         | ✅                  | ❌                  |
| Works with general Collections | ✅         | ❌ List-specific    | Legacy collections |
| Legacy                         | No        | No                 | Yes                |

---

# The Most Important Differences

### Iterator

```text
General traversal
       ↓
Forward only
```

### ListIterator

```text
List traversal
       ↓
Forward + backward
       ↓
More modification operations
```

### Enumeration

```text
Legacy traversal
       ↓
Forward only
       ↓
Very limited operations
```

---

# Easy Memory Trick

```text
Iterator
   ↓
I = "I go forward"

ListIterator
   ↓
List + forward + backward

Enumeration
   ↓
Old/legacy traversal
```

### Final exam memory

> **Iterator → forward traversal + remove**
> **ListIterator → forward + backward + add/remove/set**
> **Enumeration → legacy forward traversal only**
