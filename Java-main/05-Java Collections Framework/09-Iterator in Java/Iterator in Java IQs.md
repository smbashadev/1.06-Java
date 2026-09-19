# 9. Iterator in Java — DOUBTKILLER

**Training rule:** No Generics anywhere. All examples use normal/raw Java Collections syntax.

This section is designed specifically to eliminate the common doubts that arise between **Iterator, ListIterator, and Enumeration**.

---

# 1. Iterator — DOUBTKILLER

## Doubt 1: What exactly is an Iterator?

An `Iterator` is an object used to **traverse elements of a collection one by one in the forward direction**.

```text id="w4r0s9"
Collection
[A] [B] [C] [D]
 ↑
Iterator
```

It provides a common traversal mechanism instead of depending on indexes.

```java id="3q8l1f"
Iterator itr = collection.iterator();
```

`Iterator` belongs to:

```java id="kzj1mw"
java.util
```

---

## Doubt 2: Why do we need Iterator if ArrayList already has `get()`?

Because `get(index)` depends on indexes.

For example:

```java id="8e3c5p"
list.get(0);
list.get(1);
list.get(2);
```

But a `HashSet` does not provide index-based access.

Iterator provides a common traversal mechanism:

```text id="9c1h8w"
ArrayList ──┐
LinkedList ─┤
HashSet ────┤──→ Iterator
TreeSet ────┘
```

So:

> Iterator allows us to traverse collection elements without depending on index-based access.

---

## Doubt 3: Does Iterator move forward or backward?

**Only forward.**

```text id="6s4z0j"
A → B → C → D
```

It cannot do:

```text id="8x6n1m"
D → C → B → A
```

For backward traversal of a List, use `ListIterator`.

---

## Doubt 4: What is `hasNext()` actually doing?

It checks whether another element is available.

```java id="n3j5k8"
itr.hasNext()
```

Possible results:

```text id="u8x6w2"
true
false
```

It does **not** return the element.

Think:

```text id="p5r0kd"
hasNext()
   ↓
"Is something available?"
```

---

## Doubt 5: What does `next()` actually do?

`next()`:

1. returns the next element
2. moves the iterator forward

```java id="k2n4pw"
itr.next();
```

Think:

```text id="v1f7dz"
next()
  ↓
"Give me the next element"
  ↓
Move forward
```

---

## Doubt 6: Why are `hasNext()` and `next()` usually together?

Because `next()` should normally be called only when another element exists.

Standard pattern:

```java id="7s4h1q"
while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

Meaning:

> While another element exists, retrieve it.

---

## Doubt 7: What happens if I call `next()` when there is no element?

It can throw:

```text id="f2m8sa"
NoSuchElementException
```

Therefore:

```java id="8k0v3h"
hasNext()
```

should normally be checked before:

```java id="1f9x7c"
next()
```

---

## Doubt 8: Does `hasNext()` move the Iterator?

**No.**

This is extremely important.

```java id="3u7m9x"
itr.hasNext();
itr.hasNext();
itr.hasNext();
```

All three checks do not move the iterator.

Only:

```java id="r1x4pz"
next()
```

moves it forward.

Remember:

```text id="x5q8mv"
hasNext() → CHECK
next()    → GET + MOVE
```

---

## Doubt 9: What does `remove()` remove?

`itr.remove()` removes the element **most recently returned by `next()`**.

Example:

```text id="3j7b0k"
[A] [B] [C]
```

Suppose:

```java id="p7f4ra"
itr.next();
```

returns:

```text id="b9d2xe"
A
```

Then:

```java id="g4z6sc"
itr.remove();
```

removes `A`.

---

## Doubt 10: Can I call `remove()` before `next()`?

Normally, **no**.

For example:

```java id="6d5q8r"
Iterator itr = list.iterator();

itr.remove();
```

This can throw:

```text id="9w2n6k"
IllegalStateException
```

because no element has yet been returned by `next()`.

Correct sequence:

```text id="m7v1cx"
next()
 ↓
element returned
 ↓
remove()
```

---

## Doubt 11: Can I call `remove()` twice for one `next()`?

No.

Example:

```java id="7a3v9m"
itr.next();
itr.remove();
itr.remove();
```

The second `remove()` is invalid because there has not been another successful `next()` operation.

Think:

```text id="6m8x2p"
next()
 ↓
remove() ✓

remove() ✗
```

---

## Doubt 12: Why use `itr.remove()` instead of `list.remove()` while traversing?

Because directly modifying the collection while an Iterator is active can cause:

```text id="p5d9w1"
ConcurrentModificationException
```

Example of the risky pattern:

```java id="w6c1q3"
while(itr.hasNext())
{
    String value = (String)itr.next();

    if(value.equals("B"))
    {
        list.remove(value);
    }
}
```

The Iterator expects to control its traversal state.

Use:

```java id="8v4m2z"
itr.remove();
```

when removing the element currently being traversed.

---

# 2. ListIterator — DOUBTKILLER

## Doubt 13: Is ListIterator completely different from Iterator?

No.

`ListIterator` is a more powerful iterator designed specifically for Lists.

Conceptually:

```text id="6y1m8s"
Iterator
   ↑
ListIterator
```

`ListIterator` extends `Iterator`.

Therefore it has the basic Iterator functionality and adds more operations.

---

## Doubt 14: Why can't I use ListIterator with HashSet?

Because `ListIterator` is specifically associated with `List`.

Examples:

```text id="h7v3q2"
ArrayList
LinkedList
Vector
```

can provide a ListIterator.

A `HashSet` is not a List, so it does not provide `listIterator()`.

---

## Doubt 15: What is the biggest difference between Iterator and ListIterator?

This is the **most important difference**:

```text id="0f8x2m"
Iterator
    ↓
Forward only

ListIterator
    ↓
Forward + Backward
```

---

## Doubt 16: What is `hasPrevious()`?

It checks whether an element exists behind the current ListIterator position.

```java id="q6k9sw"
itr.hasPrevious()
```

returns:

```text id="c2v7nz"
true / false
```

---

## Doubt 17: What does `previous()` do?

It returns the previous element and moves the ListIterator backward.

```java id="k8p3vf"
itr.previous()
```

Example:

```text id="7w4m1s"
A → B → C
        ↑
       current

previous()
     ↓
     B
```

---

## Doubt 18: Why does `hasPrevious()` initially return false?

Suppose:

```java id="m0v5kc"
ListIterator itr = list.listIterator();
```

The iterator starts at the beginning:

```text id="2q7n4x"
| A | B | C |
^
```

There is nothing before the current position.

Therefore:

```java id="6f3j8v"
itr.hasPrevious()
```

returns:

```text id="5w9m1a"
false
```

---

## Doubt 19: When can I use `previous()`?

After the iterator has moved forward.

For example:

```java id="v7q2kc"
itr.next();
itr.next();
itr.next();
```

Now it is at the end:

```text id="4n8s1d"
| A | B | C |
          ^
```

Now:

```java id="9x3m6p"
itr.hasPrevious()
```

is:

```text id="e2v7ka"
true
```

and:

```java id="1f5q8z"
itr.previous()
```

returns:

```text id="6k4c9m"
C
```

---

## Doubt 20: Can ListIterator move forward and then backward?

**Yes.**

Example:

```java id="3x7m2p"
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

        while(itr.hasPrevious())
        {
            System.out.println(itr.previous());
        }
    }
}
```

Output:

```text id="h9s4vc"
A
B
C
C
B
A
```

---

## Doubt 21: Can ListIterator add elements?

**Yes.**

```java id="p4n7sx"
itr.add("X");
```

This inserts an element at the iterator's current position according to ListIterator's insertion rules.

---

## Doubt 22: Can ListIterator remove elements?

**Yes.**

```java id="q3m8vz"
itr.remove();
```

Like Iterator, removal is governed by the traversal state.

---

## Doubt 23: Can ListIterator replace an element?

**Yes.**

It provides:

```java id="8v1c6m"
itr.set("X");
```

`set()` replaces the last element returned by `next()` or `previous()`.

---

## Doubt 24: What is the difference between `add()` and `set()`?

Very important:

```text id="j2f6qa"
add()
 ↓
INSERT new element
```

Whereas:

```text id="r9k4wx"
set()
 ↓
REPLACE existing element
```

Example:

```text id="5m1z8c"
Before:
[A] [B] [C]
```

`add("X")`:

```text id="0p6d3v"
[A] [B] [X] [C]
```

`set("X")`:

```text id="9k3v7m"
[A] [X] [C]
```

---

## Doubt 25: Can I call `set()` at any time?

No.

`set()` requires an appropriate element to have been returned by `next()` or `previous()` and not invalidated by a subsequent conflicting iterator operation.

The simple training rule is:

```text id="1r6m9x"
next()/previous()
       ↓
element returned
       ↓
set()
```

---

## Doubt 26: What are `nextIndex()` and `previousIndex()`?

ListIterator knows about List indexes.

```java id="7v2q8m"
itr.nextIndex()
```

gives the index associated with the next forward traversal.

```java id="0x5m3k"
itr.previousIndex()
```

gives the index associated with the previous backward traversal.

This is another reason ListIterator is more powerful than Iterator.

---

# 3. Enumeration — DOUBTKILLER

## Doubt 27: What is Enumeration?

`Enumeration` is a **legacy traversal interface** from `java.util`.

It was designed for older collection classes.

Common examples include:

```text id="q8m2zs"
Vector
Hashtable
```

---

## Doubt 28: Why is Enumeration called legacy?

Because it predates the modern Collections Framework traversal style.

The important historical progression is approximately:

```text id="2m7x4c"
Older Java
    ↓
Enumeration

Collections Framework
    ↓
Iterator

List-specific enhanced traversal
    ↓
ListIterator
```

So in modern Java code, Iterator/ListIterator are generally preferred when appropriate.

---

## Doubt 29: What are Enumeration's methods?

Only two main traversal methods:

```java id="h4x8z1"
hasMoreElements()
nextElement()
```

---

## Doubt 30: Is `hasMoreElements()` the same idea as `hasNext()`?

Yes, conceptually.

```text id="m3v7q9"
Iterator:
hasNext()

Enumeration:
hasMoreElements()
```

Both ask:

> "Is another element available?"

---

## Doubt 31: Is `nextElement()` similar to `next()`?

Yes, conceptually.

```text id="n6w2cp"
Iterator:
next()

Enumeration:
nextElement()
```

Both retrieve the next element.

---

## Doubt 32: Can Enumeration move backward?

**No.**

```text id="x8m4q2"
A → B → C
```

It only supports forward traversal.

---

## Doubt 33: Can Enumeration remove elements?

**No.**

Enumeration does not provide:

```java id="p9v3ks"
remove()
```

---

## Doubt 34: Can Enumeration add elements?

**No.**

There is no:

```java id="z1m6qx"
add()
```

---

## Doubt 35: Can Enumeration replace elements?

**No.**

There is no:

```java id="c7n2wv"
set()
```

---

# 4. Iterator vs ListIterator vs Enumeration — DOUBTKILLER

## Doubt 36: Which one is forward only?

```text id="d6q1xm"
Iterator
Enumeration
```

Both move forward.

---

## Doubt 37: Which one supports backward traversal?

```text id="z8m4qp"
ListIterator
```

Only ListIterator.

---

## Doubt 38: Which one supports `add()`?

```text id="x5v7nc"
ListIterator
```

---

## Doubt 39: Which one supports `set()`?

```text id="h2q9md"
ListIterator
```

---

## Doubt 40: Which ones support `remove()`?

```text id="r4m8vz"
Iterator
ListIterator
```

Enumeration does not.

---

## Doubt 41: Which one is legacy?

```text id="p7x3kn"
Enumeration
```

---

## Doubt 42: Which one is specifically for Lists?

```text id="c9m2qw"
ListIterator
```

---

# 5. Master Comparison

| Question            | Iterator | ListIterator | Enumeration      |
| ------------------- | -------- | ------------ | ---------------- |
| Forward traversal?  | ✅        | ✅            | ✅                |
| Backward traversal? | ❌        | ✅            | ❌                |
| `hasNext()`?        | ✅        | ✅            | ❌                |
| `next()`?           | ✅        | ✅            | ❌                |
| `hasPrevious()`?    | ❌        | ✅            | ❌                |
| `previous()`?       | ❌        | ✅            | ❌                |
| `remove()`?         | ✅        | ✅            | ❌                |
| `add()`?            | ❌        | ✅            | ❌                |
| `set()`?            | ❌        | ✅            | ❌                |
| Index methods?      | ❌        | ✅            | ❌                |
| Used with Lists?    | Yes      | Yes          | Not specifically |
| Legacy?             | ❌        | ❌            | ✅                |

---

# 6. The Most Confusing Point: Iterator vs ListIterator

Students often think:

> "If ListIterator can do everything Iterator does, why do we need Iterator?"

Because they serve different levels of traversal.

### Iterator

Useful when you simply need:

```text id="z6r3mw"
Go forward
Read elements
Possibly remove elements
```

### ListIterator

Useful when you need:

```text id="n8q1vc"
Go forward
Go backward
Insert
Remove
Replace
Know indexes
```

So:

```text id="4k7m2x"
Simple traversal
     ↓
Iterator

Advanced List traversal
     ↓
ListIterator
```

---

# 7. The Most Confusing Point: Iterator vs Enumeration

Their basic traversal behavior looks similar:

```text id="v5m9qs"
Iterator:
hasNext()
next()

Enumeration:
hasMoreElements()
nextElement()
```

But their history and capabilities differ.

```text id="k2x7nd"
Iterator
→ Collections Framework
→ modern traversal mechanism

Enumeration
→ older API
→ legacy traversal mechanism
```

---

# 8. The Most Confusing Point: `next()` vs `hasNext()`

Never confuse them.

```text id="q3m8vx"
hasNext()
    ↓
CHECK

next()
    ↓
GET + MOVE
```

Similarly:

```text id="b7n2kp"
hasPrevious()
    ↓
CHECK

previous()
    ↓
GET + MOVE BACKWARD
```

---

# 9. The Most Confusing Point: `add()` vs `set()`

```text id="j8m4vc"
add()
 ↓
NEW element

set()
 ↓
REPLACE element
```

Example:

```text id="2q6z9w"
[A] [B] [C]
```

After `add("X")`:

```text id="7m1p4d"
[A] [B] [X] [C]
```

After `set("X")` on `B`:

```text id="4x8n2s"
[A] [X] [C]
```

---

# 10. The Most Confusing Point: Why does ListIterator go backward only after going forward?

Because the iterator represents a **position between elements**, not simply a selected element.

Imagine:

```text id="w7k2mq"
| A | B | C |
^
```

At the beginning, there is nothing behind it.

After moving forward:

```text id="s4n8vx"
| A | B | C |
      ^
```

Now something exists behind the iterator.

Therefore `previous()` can move backward.

---

# 11. Exam-Trap Questions

### Q1. Which interface supports forward and backward traversal?

**Answer:**

```text id="h6m3qv"
ListIterator
```

---

### Q2. Which interface is legacy?

**Answer:**

```text id="n8x4cz"
Enumeration
```

---

### Q3. Which method checks whether another element exists in Iterator?

**Answer:**

```text id="p2v7ms"
hasNext()
```

---

### Q4. Which method retrieves the next element?

**Answer:**

```text id="c5k9wx"
next()
```

---

### Q5. Which method retrieves the previous element?

**Answer:**

```text id="m1q8vz"
previous()
```

---

### Q6. Which traversal interface supports `set()`?

**Answer:**

```text id="x7n3kp"
ListIterator
```

---

### Q7. Which traversal interface supports `add()`?

**Answer:**

```text id="q4m9cs"
ListIterator
```

---

### Q8. Which traversal interfaces support `remove()`?

**Answer:**

```text id="v2k6mw"
Iterator
ListIterator
```

---

### Q9. Which interface uses `hasMoreElements()`?

**Answer:**

```text id="z8p3qn"
Enumeration
```

---

### Q10. Which interface uses `nextElement()`?

**Answer:**

```text id="r5m7vx"
Enumeration
```

---

# 12. Final DOUBTKILLER Memory Map

```text id="k6w2ps"
                    TRAVERSAL
                       │
       ┌───────────────┼────────────────┐
       │               │                │
    Iterator      ListIterator      Enumeration
       │               │                │
   Forward       Forward +          Forward
      only        Backward           only
       │               │                │
   remove()       add()              Legacy
                  remove()
                  set()
                  indexes
```

### Remember these three lines:

```text id="x3q7mv"
Iterator      = Forward traversal

ListIterator  = Forward + Backward + List modifications

Enumeration   = Legacy forward traversal
```

And the most important method mapping:

```text id="n9m4xc"
Iterator:
hasNext() → next()

ListIterator:
hasNext() → next()
hasPrevious() → previous()

Enumeration:
hasMoreElements() → nextElement()
```

**No Generics are required to understand any of these three concepts.** The Generic Collections syntax belongs to your later **Generics** topic, so it should not be mixed into this training stage.
