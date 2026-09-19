# 9. Iterator in Java — TEACHME

**Important training rule:** We will **not use Generics** in this topic. All examples use normal/raw collection syntax.

I’ll teach this as if you are learning Iterator for the first time, starting from the problem and then building each concept.

---

# 1. First understand the problem

Suppose we have a collection containing:

```text
A
B
C
D
```

We want to access these elements one by one:

```text
A → B → C → D
```

How can Java provide a common way to travel through different collections?

That is where **Iterator** comes in.

Think of an Iterator as a **travelling pointer/cursor** that moves through the collection.

```text
Collection
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

And so on.

---

# 2. Iterator

## What is Iterator?

`Iterator` is an interface from:

```java
java.util
```

It is used to traverse collection elements **one by one in the forward direction**.

The basic syntax is:

```java
Iterator itr = collection.iterator();
```

For example:

```java
ArrayList list = new ArrayList();

list.add("A");
list.add("B");
list.add("C");

Iterator itr = list.iterator();
```

Now `itr` can be used to travel through the list.

---

# 3. Why do we need Iterator?

Imagine you have an `ArrayList`.

You can access elements using indexes:

```java
System.out.println(list.get(0));
System.out.println(list.get(1));
System.out.println(list.get(2));
```

But different collections don't all provide index-based access.

For example:

```text
ArrayList
LinkedList
HashSet
TreeSet
```

They don't all work the same way.

Iterator gives us a **common traversal mechanism**.

```text
Collection
    ↓
iterator()
    ↓
Iterator
    ↓
element
    ↓
element
    ↓
element
```

---

# 4. The three important Iterator methods

The most important methods are:

```text
hasNext()
next()
remove()
```

Let's understand them individually.

---

# 5. `hasNext()`

Suppose the collection contains:

```text
A B C
```

You are currently before `A`.

You need to know:

> "Is there another element available?"

That's exactly what:

```java
itr.hasNext()
```

does.

It returns:

```text
true
```

if another element exists.

Otherwise:

```text
false
```

### Easy meaning

```text
hasNext()
     ↓
"Is there another element?"
```

It **does not return the element**.

---

# 6. `next()`

Now suppose `hasNext()` says:

```text
true
```

You want the actual element.

Use:

```java
itr.next()
```

It:

1. returns the next element
2. moves the iterator forward

So:

```text
hasNext()
   ↓
"Yes, there is an element."
   ↓
next()
   ↓
"Give me that element."
```

---

# 7. The most important combination

You will very frequently see:

```java
while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

Read this in English:

> "While another element exists, get that element and print it."

This is the standard Iterator pattern.

---

# 8. First Iterator program

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

# 9. Let's mentally execute the program

Initially:

```text
[Ravi] [Kiran] [Basha]
   ↑
 Iterator
```

`hasNext()`:

```text
true
```

`next()`:

```text
Ravi
```

Iterator moves forward.

Then:

```text
[Ravi] [Kiran] [Basha]
          ↑
```

`hasNext()`:

```text
true
```

`next()`:

```text
Kiran
```

Then:

```text
[Ravi] [Kiran] [Basha]
                   ↑
```

Finally:

```text
hasNext() → false
```

Loop ends.

---

# 10. Why not simply call `next()`?

Consider:

```java
Iterator itr = list.iterator();

System.out.println(itr.next());
System.out.println(itr.next());
System.out.println(itr.next());
```

This works only if enough elements exist.

If you call `next()` when no element is available, Java can throw:

```text
NoSuchElementException
```

Therefore:

```java
while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

is the safer normal pattern.

---

# 11. Iterator `remove()`

Now suppose:

```text
[A] [B] [C]
```

You are traversing the collection and want to remove `B`.

Iterator provides:

```java
itr.remove();
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

# 12. Why use `itr.remove()`?

Because the Iterator knows which element it has just returned.

The sequence is:

```text
next()
  ↓
B returned
  ↓
remove()
  ↓
B removed
```

This is an important rule:

> `remove()` removes the element most recently returned by `next()`.

---

# 13. A common mistake

Don't think this:

```java
itr.remove();
```

means:

> "Remove any element I want."

No.

It means:

> "Remove the element associated with the most recent successful traversal operation."

So understand:

```text
next()
 ↓
element
 ↓
remove()
```

---

# PART 2 — ListIterator

Now we move to the second concept.

# 14. What is ListIterator?

`ListIterator` is an enhanced form of Iterator specifically designed for **List implementations**.

It can move in two directions.

Normal Iterator:

```text
→ → → →
```

ListIterator:

```text
← ← ←
→ → →
```

Therefore:

> **Iterator = forward**

> **ListIterator = forward + backward**

---

# 15. Where can we use ListIterator?

ListIterator is designed for List objects such as:

```text
ArrayList
LinkedList
Vector
```

Example:

```java
ArrayList list = new ArrayList();

list.add("A");
list.add("B");
list.add("C");

ListIterator itr = list.listIterator();
```

---

# 16. ListIterator methods

The important methods are:

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

Don't worry about memorizing everything immediately.

First understand the most important difference:

```text
Iterator:
forward

ListIterator:
forward + backward
```

---

# 17. Forward traversal with ListIterator

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

So far it looks exactly like Iterator.

The real difference comes when we go backward.

---

# 18. `hasPrevious()`

This asks:

> "Is there an element behind the current iterator position?"

Syntax:

```java
itr.hasPrevious()
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

# 19. `previous()`

This retrieves the previous element and moves backward.

Syntax:

```java
itr.previous()
```

So:

```text
hasPrevious()
      ↓
"Is there an element behind me?"
      ↓
previous()
      ↓
"Give me that element."
```

---

# 20. Forward + backward program

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

# 21. Why does backward traversal work here?

This is a common doubt.

Initially, the iterator is at the beginning.

```text
| A | B | C |
^
```

There is nothing before it.

Therefore:

```java
itr.hasPrevious()
```

returns:

```text
false
```

After moving forward:

```text
itr.next();
itr.next();
itr.next();
```

the iterator reaches the end:

```text
| A | B | C |
          ^
```

Now there are elements behind it.

Therefore:

```java
itr.hasPrevious()
```

returns:

```text
true
```

and:

```java
itr.previous()
```

moves backward.

---

# 22. ListIterator `add()`

ListIterator can insert an element while traversing.

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

# 23. `add()` vs `remove()`

Think:

```text
add()    → puts something in
remove() → takes something out
```

ListIterator can perform both while traversing.

---

# 24. ListIterator `set()`

`set()` is used to replace the last element returned by `next()` or `previous()`.

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

# 25. Understand `add()` vs `set()`

This is very important.

Suppose:

```text
[A] [B] [C]
```

### `add("X")`

Adds a new element:

```text
[A] [B] [X] [C]
```

### `set("X")`

Replaces an existing element:

```text
[A] [X] [C]
```

So remember:

```text
add() → INSERT
set() → REPLACE
```

---

# 26. `nextIndex()`

ListIterator also knows indexes.

```java
itr.nextIndex()
```

returns the index of the element that would be returned by the next `next()` call.

Initially:

```text
nextIndex() → 0
```

After one `next()`:

```text
nextIndex() → 1
```

---

# 27. `previousIndex()`

This gives the index of the element that would be returned by the next `previous()` call.

Initially:

```text
previousIndex() → -1
```

because there is nothing before the first element.

---

# PART 3 — Enumeration

Now let's learn the third concept.

# 28. What is Enumeration?

`Enumeration` is an **old/legacy traversal interface** in Java.

It was used with older collection classes.

Common examples:

```text
Vector
Hashtable
```

It is also in:

```java
java.util
```

---

# 29. Why is Enumeration called legacy?

Java has evolved.

Older Java collection classes used Enumeration.

Later, Java introduced the Collections Framework and Iterator-based traversal.

Therefore:

```text
Enumeration → older/legacy
Iterator     → modern
ListIterator → modern and more powerful for Lists
```

You may still encounter Enumeration in older Java code.

---

# 30. Enumeration methods

Enumeration mainly has two methods:

```text
hasMoreElements()
nextElement()
```

Compare this with Iterator:

```text
Iterator:
hasNext()
next()

Enumeration:
hasMoreElements()
nextElement()
```

---

# 31. `hasMoreElements()`

It checks whether another element exists.

```java
e.hasMoreElements()
```

returns:

```text
true
```

or:

```text
false
```

Think:

```text
hasMoreElements()
        ↓
"Is another element available?"
```

---

# 32. `nextElement()`

It returns the next element.

```java
e.nextElement()
```

Think:

```text
nextElement()
      ↓
"Give me the next element."
```

---

# 33. Enumeration with Vector

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

# 34. Understand the Vector example

First:

```java
Vector v = new Vector();
```

We add:

```text
A
B
C
```

Then:

```java
Enumeration e = v.elements();
```

creates an Enumeration for traversing the Vector.

Then:

```java
while(e.hasMoreElements())
```

checks whether another element exists.

And:

```java
e.nextElement()
```

gets the element.

---

# 35. Enumeration with Hashtable

Enumeration can also be used with the legacy `Hashtable` class.

Example:

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

This example traverses the values.

---

# PART 4 — Compare All Three

Now let's put everything together.

## Iterator

Imagine a person walking down a road:

```text
A → B → C → D
```

He can go forward.

He can also remove the element returned through the iterator.

```text
Iterator
   ↓
Forward
   ↓
hasNext()
next()
remove()
```

---

## ListIterator

Imagine a person who can walk in both directions:

```text
A ← B ← C
→ → →
```

It has more control:

```text
ListIterator
     ↓
Forward
Backward
Add
Remove
Replace
Index information
```

---

## Enumeration

Imagine an old traversal mechanism:

```text
A → B → C → D
```

It can move forward, but has fewer operations.

```text
Enumeration
     ↓
Forward
     ↓
hasMoreElements()
nextElement()
```

---

# 36. Complete comparison table

| Feature            | Iterator | ListIterator | Enumeration |
| ------------------ | -------- | ------------ | ----------- |
| Forward traversal  | ✅        | ✅            | ✅           |
| Backward traversal | ❌        | ✅            | ❌           |
| `hasNext()`        | ✅        | ✅            | ❌           |
| `next()`           | ✅        | ✅            | ❌           |
| `hasPrevious()`    | ❌        | ✅            | ❌           |
| `previous()`       | ❌        | ✅            | ❌           |
| `remove()`         | ✅        | ✅            | ❌           |
| `add()`            | ❌        | ✅            | ❌           |
| `set()`            | ❌        | ✅            | ❌           |
| Index methods      | ❌        | ✅            | ❌           |
| Modern             | ✅        | ✅            | ❌           |
| Legacy             | ❌        | ❌            | ✅           |

---

# 37. The three most important memory rules

### Rule 1

```text
Iterator
= Forward
```

### Rule 2

```text
ListIterator
= Forward + Backward
```

### Rule 3

```text
Enumeration
= Legacy + Forward
```

---

# 38. Method memory trick

Don't memorize the methods randomly.

Understand the naming.

### Iterator

```text
hasNext() → Is there a next?
next()    → Give me next
```

### ListIterator

```text
hasNext()       → Is there a next?
next()          → Give me next

hasPrevious()   → Is there a previous?
previous()      → Give me previous
```

### Enumeration

```text
hasMoreElements() → Is there another element?
nextElement()     → Give me next element
```

---

# 39. One final example to understand all three

Suppose:

```text
[A] [B] [C]
```

### Iterator

```text
A → B → C
```

Only forward.

### ListIterator

```text
A → B → C
      ←
      B
      ←
      A
```

Forward and backward.

### Enumeration

```text
A → B → C
```

Forward, using the old traversal mechanism.

---

# 40. Final mental picture

Keep this picture in your mind:

```text
                    TRAVERSAL
                       │
          ┌────────────┼────────────┐
          │            │            │
       Iterator   ListIterator  Enumeration
          │            │            │
       Forward    Forward +      Forward
         only      Backward        only
          │            │            │
       remove()    add/remove/    Legacy
                    set()
```

### If I ask you in an interview:

**Which traverses forward?**

```text
Iterator
ListIterator
Enumeration
```

All three.

**Which traverses backward?**

```text
ListIterator
```

**Which is legacy?**

```text
Enumeration
```

**Which can add during traversal?**

```text
ListIterator
```

**Which can replace an element using `set()`?**

```text
ListIterator
```

**Which can remove through its traversal object?**

```text
Iterator
ListIterator
```

**What is the easiest way to remember everything?**

```text
Iterator      → Forward
ListIterator  → Forward + Backward + More control
Enumeration   → Old Forward traversal
```

And throughout this lesson, **no Generics were used**, exactly according to your Collections Framework training approach.
