# 2. Iterable in Java — DOUBTKILLER

> **Training rule:** No Generics are used in this topic. Generics will be covered separately.

This section is specifically designed to eliminate the **confusions, traps, interview questions, and common mistakes** surrounding `Iterable`.

---

# 1. First Doubt: What exactly is `Iterable`?

`Iterable` is an interface in Java.

It belongs to:

```java
java.lang
```

Its purpose is to provide a standard mechanism for obtaining an `Iterator`.

In simple language:

> **`Iterable` means "I can provide an Iterator that can traverse my elements."**

The basic relationship is:

```text
Iterable
   |
   | iterator()
   ↓
Iterator
   |
   | hasNext(), next()
   ↓
Elements
```

---

# 2. Is Iterable a Collection?

### ❌ No.

This is one of the most common mistakes.

The relationship is:

```text
Iterable
    ↑
Collection
```

In other words:

```text
Collection extends Iterable
```

Therefore:

```text
Collection IS-A Iterable
```

But:

```text
Iterable IS-NOT-A Collection
```

### Remember

```text
Iterable
   ↑
Collection
```

The arrow points toward the parent.

---

# 3. Is Iterable the same as Iterator?

### ❌ Absolutely not.

They have different responsibilities.

### `Iterable`

Provides an iterator.

### `Iterator`

Performs traversal.

Think:

```text
Iterable
   ↓
"Give me a way to traverse."

Iterator
   ↓
"Here is the traversal mechanism."
```

### Memory trick

> **Iterable = provider**

> **Iterator = traveler**

---

# 4. What does `iterator()` actually return?

This is a very common exam/interview trap.

Suppose:

```java
Iterator itr = al.iterator();
```

What does `al.iterator()` return?

### ❌ Not the first element

### ❌ Not all elements

### ❌ Not an array

### ✅ An `Iterator` object

So:

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

# 5. What does `hasNext()` actually do?

Suppose:

```text
[10, 20, 30]
```

When we write:

```java
itr.hasNext();
```

it asks:

> **"Is another element available?"**

The answer is:

```text
true
```

or:

```text
false
```

It does **not** return the next element.

---

# 6. What does `next()` actually do?

```java
itr.next();
```

means:

> **Return the next element and advance the iterator.**

For:

```text
[10, 20, 30]
```

we conceptually get:

```text
next() → 10
next() → 20
next() → 30
```

---

# 7. Why do we use `hasNext()` before `next()`?

Suppose:

```text
[10, 20]
```

After retrieving `20`, there is no element left.

If we call:

```java
itr.next();
```

again, Java can throw:

```text
NoSuchElementException
```

Therefore the standard pattern is:

```java
while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

Meaning:

```text
CHECK
 ↓
Is an element available?
 ↓
YES
 ↓
GET IT
 ↓
Repeat
```

---

# 8. Does `hasNext()` move the Iterator?

### ❌ No.

This is important.

```java
itr.hasNext();
```

only checks.

It does not consume the element.

The traversal happens with:

```java
itr.next();
```

Therefore:

```text
hasNext()
   ↓
Check

next()
   ↓
Move + return element
```

---

# 9. Does calling `hasNext()` multiple times move the Iterator?

No.

For example:

```java
itr.hasNext();
itr.hasNext();
itr.hasNext();
```

These checks don't normally advance the iterator.

You can think:

```text
hasNext()
hasNext()
hasNext()
```

as asking the same question repeatedly:

> "Is there another element?"

The position changes when traversal occurs through `next()`.

---

# 10. Does `next()` only return the element?

Conceptually, `next()` does two things:

```text
1. Returns the next element
2. Advances the iterator
```

For:

```text
[10, 20, 30]
```

the iterator progresses:

```text
next() → 10
          ↓
next() → 20
          ↓
next() → 30
```

---

# 11. What happens if I call `next()` without `hasNext()`?

It can still work **if an element exists**.

For example:

```java
System.out.println(itr.next());
```

may successfully return the first element.

But if there are no elements remaining:

```text
NoSuchElementException
```

can occur.

Therefore:

```java
while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

is the standard safe traversal pattern.

---

# 12. Does `Iterable` itself traverse the collection?

### ❌ No.

This is another subtle distinction.

The flow is:

```text
Iterable
   ↓
iterator()
   ↓
Iterator
   ↓
Traversal
```

`Iterable` provides the ability to obtain an iterator.

The Iterator performs the traversal.

---

# 13. Does `Iterable` store elements?

### ❌ No.

`Iterable` is an interface.

It is a contract describing iteration capability.

The actual collection implementation stores the elements.

For example:

```text
ArrayList
   ↓
stores elements

HashSet
   ↓
stores elements

TreeSet
   ↓
stores elements

Iterable
   ↓
provides iteration capability
```

---

# 14. Does `Iterable` determine the order of elements?

### ❌ No.

This is extremely important.

The actual collection determines its iteration behavior.

For example:

### ArrayList

```text
10 → 20 → 30
```

normally follows list order.

### LinkedHashSet

maintains insertion order during iteration.

### TreeSet

iterates according to its sorted-order rules.

### HashSet

does not guarantee a particular iteration order.

Therefore:

> **Iterable gives traversal capability; it does not define the collection's ordering policy.**

---

# 15. If two collections implement Iterable, will they have the same order?

### ❌ No.

For example:

```text
ArrayList
HashSet
TreeSet
```

can all be traversed.

But their ordering behavior differs.

```text
Iterable
   |
   +── ArrayList → List order
   |
   +── HashSet → no guaranteed order
   |
   `── TreeSet → sorted order
```

---

# 16. Is ArrayList directly an Iterable?

Yes.

Through the inheritance chain:

```text
ArrayList
   ↑
List
   ↑
Collection
   ↑
Iterable
```

Therefore:

```text
ArrayList IS-A Iterable
```

---

# 17. Is HashSet an Iterable?

Yes.

Conceptually:

```text
HashSet
   ↑
Set
   ↑
Collection
   ↑
Iterable
```

Therefore:

```java
Iterator itr = hs.iterator();
```

is valid.

---

# 18. Is LinkedList an Iterable?

Yes.

It belongs to the List hierarchy and therefore ultimately implements `Iterable`.

```text
LinkedList
   ↓
List
   ↓
Collection
   ↓
Iterable
```

---

# 19. Is TreeSet an Iterable?

Yes.

```text
TreeSet
   ↓
Set
   ↓
Collection
   ↓
Iterable
```

Therefore it can provide an iterator.

---

# 20. Is Map an Iterable?

### ❌ The Map interface itself is not a Collection and does not extend `Iterable`.

This is a very important distinction.

The hierarchy is roughly:

```text
                Iterable
                   ↑
               Collection
              /    |    \
            List   Set   Queue


                 Map
              /   |   \
         HashMap TreeMap ...
```

So:

```text
Map ≠ Collection
```

and:

```text
Map ≠ Iterable
```

in the same direct sense as Collection.

---

# 21. Then how do we iterate a Map?

A Map provides views such as:

```java
keySet()
values()
entrySet()
```

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

Here we are iterating the **key set**, not treating the Map itself as a Collection.

---

# 22. Is an array Iterable?

### ❌ No.

This is a famous question.

For example:

```java
int a[] = {10, 20, 30};
```

The array does not implement `Iterable`.

Therefore:

```java
a.iterator();
```

is invalid.

---

# 23. Then why does for-each work with arrays?

Because Java's enhanced `for` statement has special language support for arrays.

This works:

```java
int a[] = {10, 20, 30};

for(int x : a)
{
    System.out.println(x);
}
```

So:

```text
Array
   |
   └── supports enhanced for
```

does **not** mean:

```text
Array implements Iterable
```

---

# 24. Is for-each only for Collections?

### ❌ No.

The enhanced `for` loop works with:

1. Arrays
2. Objects that provide the required iteration mechanism, including `Iterable` objects.

So:

```text
for-each
   |
   ├── Array
   |
   └── Iterable object
```

---

# 25. Does every object support for-each?

### ❌ No.

For example:

```java
int x = 10;

for(Object a : x)
{
}
```

is invalid.

The expression must be an array or suitable iterable structure.

---

# 26. Why was Iterable introduced?

Imagine there were no common iteration contract.

Every collection could require a different traversal mechanism:

```text
ArrayList → special method
HashSet → another method
TreeSet → another method
LinkedList → another method
```

That would make collection traversal unnecessarily complicated.

Instead Java provides:

```text
Iterable
   ↓
iterator()
   ↓
common traversal mechanism
```

This is a major benefit of abstraction.

---

# 27. Does Iterable know whether the collection is an ArrayList or HashSet?

### ❌ No.

The `Iterable` concept doesn't require the caller to know the internal data structure.

For example:

```java
Iterator itr = collection.iterator();
```

The caller simply asks:

> "Give me an iterator."

The actual collection implementation determines how that iterator works internally.

---

# 28. Why is Iterator separate from the collection?

This is a design question.

Suppose a collection contains:

```text
10 20 30 40
```

If the collection itself maintained the current position:

```text
Collection
   ↓
current position
```

then managing multiple independent traversals would become awkward.

Instead:

```text
Collection
   |
   +── Iterator A
   |
   +── Iterator B
   |
   `── Iterator C
```

Each iterator can maintain its own traversal state.

This is one reason the separation is useful.

---

# 29. Can we create multiple Iterators from one collection?

Yes.

Example:

```java
Iterator itr1 = al.iterator();
Iterator itr2 = al.iterator();
```

Now we have two iterator objects.

Conceptually:

```text
ArrayList
   |
   +── Iterator 1
   |
   `── Iterator 2
```

Each has its own traversal position.

---

# 30. Does `iterator()` return the same Iterator every time?

You should **not assume** that.

Calling:

```java
Iterator itr1 = al.iterator();
Iterator itr2 = al.iterator();
```

normally produces separate iterator instances representing separate traversals.

Don't write your program assuming:

```text
itr1 == itr2
```

---

# 31. Can an Iterator be reused after reaching the end?

You should treat an iterator as a **single traversal object**.

Once it reaches the end:

```text
hasNext() → false
```

you normally obtain a new iterator if you want to start traversal again:

```java
Iterator itr = al.iterator();
```

Again:

```java
Iterator itr = al.iterator();
```

---

# 32. Can I reset an Iterator?

There is no standard:

```java
itr.reset();
```

method in the basic `Iterator` interface.

If you want to traverse from the beginning again, obtain a new iterator.

```java
Iterator itr = al.iterator();
```

---

# 33. What if the collection changes while Iterator is being used?

This is a major topic that will become important under:

> **Fail-fast / fail-safe / weakly consistent iteration**

For example:

```java
Iterator itr = al.iterator();

while(itr.hasNext())
{
    Object x = itr.next();

    al.add(100);
}
```

For many standard collection implementations, structurally modifying the collection directly while iterating can cause:

```text
ConcurrentModificationException
```

The exact behavior depends on the collection and iterator implementation.

---

# 34. Why does ConcurrentModificationException happen?

Because the iterator can detect that the underlying collection was structurally modified outside the iterator's expected mechanism.

Conceptually:

```text
Collection
   ↓
Iterator created
   ↓
Iterator expects collection state A
   ↓
Collection directly modified
   ↓
Collection state changes
   ↓
Iterator detects unexpected modification
   ↓
ConcurrentModificationException
```

We'll study this in detail under **Advanced Collections**.

---

# 35. Can Iterator itself remove elements?

Yes.

The standard `Iterator` interface provides:

```java
remove()
```

in addition to:

```java
hasNext()
next()
```

For example:

```java
Iterator itr = al.iterator();

while(itr.hasNext())
{
    Object x = itr.next();

    if(x.equals(20))
    {
        itr.remove();
    }
}
```

This is different from directly modifying the collection while iterating.

The complete Iterator topic will cover this properly.

---

# 36. Important: Iterable vs Iterator vs ListIterator

Don't mix them.

```text
Iterable
   ↓
iterator()
   ↓
Iterator
```

And for Lists:

```text
List
   ↓
listIterator()
   ↓
ListIterator
```

`ListIterator` provides additional capabilities such as bidirectional traversal.

We'll study:

```text
Iterator
ListIterator
Enumeration
```

separately in your roadmap.

---

# 37. Does Iterable mean "iterable only once"?

No.

A collection implementing `Iterable` can generally be traversed multiple times by obtaining new iterators.

For example:

```java
Iterator itr1 = al.iterator();

while(itr1.hasNext())
{
    System.out.println(itr1.next());
}
```

Then:

```java
Iterator itr2 = al.iterator();

while(itr2.hasNext())
{
    System.out.println(itr2.next());
}
```

The second iterator starts another traversal.

---

# 38. Does Iterator know the size of the collection?

Not necessarily.

The basic `Iterator` interface does not provide:

```java
size()
```

The Iterator's primary responsibility is traversal.

If you need collection size:

```java
al.size();
```

That's a responsibility of the collection.

---

# 39. Does Iterable provide `size()`?

### ❌ No.

Don't expect:

```java
iterable.size();
```

`Iterable` is about iteration.

Collection-related operations such as:

```text
size()
add()
remove()
contains()
```

belong to the Collection hierarchy, not `Iterable` itself.

---

# 40. Does Iterable provide `add()`?

### ❌ No.

This is another important distinction.

`Iterable` does not mean:

> "I can add elements."

It means:

> "I can provide an iterator."

---

# 41. Does Iterable provide `remove()`?

### ❌ Not as a collection operation.

`Iterator` has its own `remove()` method for removing the last element returned by the iterator, subject to its contract.

But:

```text
Iterable
```

itself is not a general collection-manipulation interface.

---

# 42. Does Iterable provide `contains()`?

### ❌ No.

`contains()` belongs to `Collection`, not `Iterable`.

So:

```text
Iterable
   |
   └── iteration capability

Collection
   |
   ├── add()
   ├── remove()
   ├── contains()
   ├── size()
   └── ...
```

---

# 43. Can a custom class implement Iterable?

### Yes.

Suppose we have:

```java
class MyData implements Iterable
{
    // provide iterator()
}
```

The class is saying:

> "Objects of this class can be traversed."

It doesn't necessarily need to be a `Collection`.

---

# 44. Why would a custom class implement Iterable?

Suppose we build:

```text
MyDataStructure
   |
   +-- 100
   +-- 200
   +-- 300
```

We want users to write:

```java
for(Object x : data)
{
    System.out.println(x);
}
```

Implementing `Iterable` provides a standard way to expose traversal.

This is particularly useful when creating custom data structures.

---

# 45. Is implementing Iterable enough to make every class a Collection?

### ❌ No.

This is an important inheritance point.

You can have:

```text
MyClass
   |
implements Iterable
```

without:

```text
MyClass
   |
implements Collection
```

So:

```text
Iterable capability
```

does not automatically give you:

```text
Collection operations
```

such as `add`, `remove`, `size`, etc.

---

# 46. What is the simplest mental model?

Think of a **book shelf**.

```text
Shelf
   |
   +-- Book 1
   +-- Book 2
   +-- Book 3
```

The shelf says:

> "I can give you a way to walk through my books."

That's:

```text
Iterable
```

The person walking along the shelf is:

```text
Iterator
```

The person asks:

```text
"Is another book available?"
```

That's:

```java
hasNext()
```

Then asks:

```text
"Give me the next book."
```

That's:

```java
next()
```

---

# 47. The complete confusion-killer diagram

```text
                         Iterable
                            |
                            | iterator()
                            ↓
                         Iterator
                       /          \
                      /            \
               hasNext()          next()
                  |                  |
                  ↓                  ↓
             CHECK ONLY       GET NEXT ELEMENT
                  |                  |
                  └────────┬─────────┘
                           ↓
                       Traversal
```

And:

```text
Collection
    ↑
Iterable
```

**Correction:** The inheritance direction is actually:

```text
Iterable
    ↑
Collection
```

meaning:

```text
Collection extends Iterable
```

---

# 48. Most important interview traps

### Trap 1

**Q:** Is Iterable a class?

**A:** No. It is an interface.

---

### Trap 2

**Q:** Is Iterable a Collection?

**A:** No.

---

### Trap 3

**Q:** Does Collection extend Iterable?

**A:** Yes.

---

### Trap 4

**Q:** Does Iterable provide `iterator()`?

**A:** Yes.

---

### Trap 5

**Q:** Does `iterator()` return an element?

**A:** No. It returns an Iterator.

---

### Trap 6

**Q:** Does `hasNext()` return the next element?

**A:** No. It checks whether another element exists.

---

### Trap 7

**Q:** Does `next()` only check availability?

**A:** No. It returns the next element and advances the iterator.

---

### Trap 8

**Q:** Is an array Iterable?

**A:** No.

---

### Trap 9

**Q:** Can an array be used in enhanced `for`?

**A:** Yes.

---

### Trap 10

**Q:** Is Map a Collection?

**A:** No.

---

### Trap 11

**Q:** Can Map data be traversed?

**A:** Yes, through its collection views such as `keySet()`, `values()`, and `entrySet()`.

---

### Trap 12

**Q:** Does Iterable decide iteration order?

**A:** No. The actual collection implementation determines its ordering behavior.

---

### Trap 13

**Q:** Does Iterable store elements?

**A:** No.

---

### Trap 14

**Q:** Can a custom class implement Iterable without implementing Collection?

**A:** Yes.

---

### Trap 15

**Q:** Can we obtain multiple iterators from one collection?

**A:** Yes.

---

# 49. Final DOUBTKILLER program

```java
import java.util.*;

class IterableDoubtKiller
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
            Object value = itr.next();

            System.out.println(value);
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

Understand the exact responsibility of every line:

```text
ArrayList
   ↓
contains elements

iterator()
   ↓
provides Iterator

Iterator
   ↓
controls traversal

hasNext()
   ↓
checks

next()
   ↓
gets + advances
```

---

# 🧠 FINAL MEMORY MAP

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
               CHECK               GET + MOVE
                 |                     |
                 └──────────┬──────────┘
                            ↓
                         ELEMENTS
```

And the **five rules you should never forget**:

1. **`Iterable` is an interface.**
2. **`Collection` extends `Iterable`.**
3. **`iterator()` gives an `Iterator`, not an element.**
4. **`hasNext()` checks; `next()` gets and advances.**
5. **Array is not `Iterable`, but arrays work with enhanced `for`.**

> **One-line master rule:**
> **Iterable provides the Iterator → Iterator performs traversal → `hasNext()` checks → `next()` retrieves and advances.**
