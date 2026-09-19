# 5. Set in Java — DEEPDIVE

> **Training rule:** We are learning the Collections Framework **without Generics** at this stage.
> Generics will be studied separately in Topic 14.
>
> **DEEPDIVE goal:** understand not only *what* each Set concept is, but also *why* it behaves that way, how it works, its methods, programs, differences, restrictions, and common mistakes.

---

# PART 1 — SET INTERFACE

## 1. What is Set?

`Set` is an interface in the `java.util` package.

It represents a collection that **does not allow duplicate elements**.

```text
Collection
     ↑
    Set
```

The most important characteristic is:

```text
Set
 ↓
NO DUPLICATES
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Set s = new HashSet();

        s.add(10);
        s.add(20);
        s.add(10);
        s.add(30);

        System.out.println(s);
    }
}
```

Possible output:

```text
[20, 10, 30]
```

The important observation is not the order.

The important observation is:

```text
10
20
10
30
```

becomes:

```text
10
20
30
```

because the second `10` is a duplicate.

---

# 2. Why does Set not allow duplicates?

The purpose of a Set is to represent a collection of **distinct/unique elements**.

For example, suppose we want to store student roll numbers:

```text
101
102
103
101
```

If roll numbers must be unique, storing `101` twice is undesirable.

A Set automatically prevents duplicate entries.

```text
Input:
101
102
103
101

Set:
101
102
103
```

Therefore:

> Use a Set when uniqueness is an important requirement.

---

# 3. Set vs List

This is the first major concept you must understand.

### List

```java
List l = new ArrayList();

l.add(10);
l.add(20);
l.add(10);

System.out.println(l);
```

Result:

```text
[10, 20, 10]
```

Duplicates are allowed.

### Set

```java
Set s = new HashSet();

s.add(10);
s.add(20);
s.add(10);

System.out.println(s);
```

Result contains only:

```text
10
20
```

Therefore:

```text
LIST
 ↓
Duplicates allowed
Index available


SET
 ↓
Duplicates not allowed
Index unavailable
```

---

# 4. Can we create an object of Set?

No.

This is invalid:

```java
Set s = new Set();
```

Why?

Because `Set` is an **interface**.

We need a class implementing Set:

```java
Set s = new HashSet();
```

or:

```java
Set s = new LinkedHashSet();
```

or:

```java
Set s = new TreeSet();
```

---

# 5. Set Implementations

The major implementations in your roadmap are:

```text
                    Set
                     ↑
          ┌──────────┼──────────┐
          │          │          │
          ↓          ↓          ↓
      HashSet   LinkedHashSet  TreeSet
```

They all provide uniqueness, but they differ primarily in **ordering behavior**.

```text
HashSet
    ↓
No guaranteed order

LinkedHashSet
    ↓
Insertion order

TreeSet
    ↓
Sorted order
```

This distinction is the heart of the Set topic.

---

# 6. Important Set Characteristics

| Property     | Set                       |
| ------------ | ------------------------- |
| Duplicates   | ❌ Not allowed             |
| Index        | ❌ No                      |
| `add()`      | ✅                         |
| `remove()`   | ✅                         |
| `contains()` | ✅                         |
| `size()`     | ✅                         |
| `isEmpty()`  | ✅                         |
| `clear()`    | ✅                         |
| Ordering     | Depends on implementation |

---

# 7. `add()` in Set

`add()` is particularly interesting in Set because it returns a boolean.

```java
Set s = new HashSet();

System.out.println(s.add(10));
System.out.println(s.add(20));
System.out.println(s.add(10));
```

Output:

```text
true
true
false
```

Why?

### First operation

```text
add(10)
```

10 wasn't present.

```text
→ added
→ true
```

### Second operation

```text
add(20)
```

20 wasn't present.

```text
→ added
→ true
```

### Third operation

```text
add(10)
```

10 already exists.

```text
→ not added
→ false
```

Therefore:

```text
add()
 ↓
true  → element was added
false → element was not added
```

This is an important difference between the conceptual behavior of List and Set.

---

# 8. `contains()` with Set

`contains()` checks whether an element exists.

```java
Set s = new HashSet();

s.add(10);
s.add(20);
s.add(30);

System.out.println(s.contains(20));
System.out.println(s.contains(50));
```

Output:

```text
true
false
```

Conceptually:

```text
contains(20)
     ↓
Is 20 present?
     ↓
Yes
     ↓
true
```

---

# 9. `remove()` with Set

Set doesn't use indexes.

Therefore:

```java
s.remove(20);
```

means:

> Remove the element `20`.

It does **not** mean:

> Remove element at index 20.

This is different from List.

### List

```java
l.remove(2);
```

Normally means:

```text
remove index 2
```

### Set

```java
s.remove(2);
```

means:

```text
remove the element whose value/object is 2
```

because Set has no index.

---

# PART 2 — HASHSET

# 10. What is HashSet?

`HashSet` is a class implementing `Set`.

```text
Set
 ↑
HashSet
```

Its primary purpose is:

> Store unique elements using hashing, without guaranteeing insertion order.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        HashSet hs = new HashSet();

        hs.add(30);
        hs.add(10);
        hs.add(20);
        hs.add(10);

        System.out.println(hs);
    }
}
```

Possible output:

```text
[20, 10, 30]
```

The exact displayed order should **not** be relied upon.

---

# 11. Why is it called HashSet?

Because HashSet uses a **hashing-based mechanism** internally to organize its elements.

The basic idea is:

```text
Object
  ↓
hashing
  ↓
location/bucket selection
  ↓
stored/found
```

This allows HashSet to efficiently perform operations such as checking whether an element is present.

You don't manually calculate the internal bucket arrangement when using HashSet.

Java handles that internally.

---

# 12. HashSet and order

A very important rule:

> **HashSet does not guarantee insertion order.**

Suppose:

```java
hs.add(50);
hs.add(10);
hs.add(30);
hs.add(20);
```

Do not assume the output must be:

```text
[50, 10, 30, 20]
```

It might display in another order.

Therefore never write logic depending on HashSet iteration order.

---

# 13. HashSet and sorting

HashSet does **not** maintain sorted order.

If you insert:

```text
50
10
30
20
```

HashSet does not promise:

```text
10
20
30
50
```

If sorted ordering is the requirement, TreeSet is the appropriate Set implementation.

---

# 14. HashSet and duplicates

Example:

```java
HashSet hs = new HashSet();

hs.add(10);
hs.add(20);
hs.add(10);
hs.add(30);
hs.add(20);

System.out.println(hs);
```

Only unique elements remain.

Conceptually:

```text
Input:
10 20 10 30 20

HashSet:
10 20 30
```

---

# 15. How does HashSet identify duplicates?

This is an important deeper concept.

HashSet relies on the object's **hashing information and equality comparison** when determining whether an equivalent element is already present.

For ordinary wrapper/String values, Java already provides appropriate implementations.

For example:

```java
hs.add("Java");
hs.add("Java");
```

The second logically equal String is treated as a duplicate.

This is why `hashCode()` and `equals()` become extremely important when custom objects are stored in hash-based collections.

You will encounter this again when studying advanced collections and object equality.

---

# 16. HashSet and `null`

HashSet permits a single `null` element.

```java
HashSet hs = new HashSet();

hs.add(10);
hs.add(null);
hs.add(20);
hs.add(null);

System.out.println(hs);
```

The duplicate `null` is not retained.

Conceptually:

```text
10
null
20
null
```

becomes:

```text
10
null
20
```

---

# 17. HashSet advantages

### Advantage 1 — Uniqueness

Duplicates are automatically rejected.

### Advantage 2 — Hash-based lookup

Hashing makes membership operations efficient on average.

### Advantage 3 — No need to manually check duplicates

Instead of:

```java
if(!list.contains(10))
{
    list.add(10);
}
```

a Set can enforce uniqueness as part of its normal contract.

---

# PART 3 — LINKEDHASHSET

# 18. What is LinkedHashSet?

`LinkedHashSet` is a Set implementation that combines:

```text
Set uniqueness
        +
insertion-order maintenance
```

Therefore:

> **LinkedHashSet does not allow duplicates and maintains insertion order.**

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedHashSet lhs = new LinkedHashSet();

        lhs.add(30);
        lhs.add(10);
        lhs.add(20);
        lhs.add(10);

        System.out.println(lhs);
    }
}
```

Output:

```text
[30, 10, 20]
```

---

# 19. Why is LinkedHashSet different from HashSet?

Both prevent duplicates.

The major difference is ordering.

### HashSet

```text
Unique
+
No guaranteed iteration order
```

### LinkedHashSet

```text
Unique
+
Insertion order
```

Example:

```text
Insertion:
30 → 10 → 20
```

LinkedHashSet gives:

```text
30 → 10 → 20
```

The duplicate `10` does not create a second occurrence.

---

# 20. How does LinkedHashSet maintain insertion order?

Internally, it combines hashing with linked ordering information.

Conceptually:

```text
Hashing
   +
Linked ordering
```

The hashing part helps with Set operations.

The linked structure preserves the order in which elements were inserted.

You don't manually manage those links.

---

# 21. LinkedHashSet example with duplicate insertion

```java
LinkedHashSet lhs = new LinkedHashSet();

lhs.add(10);
lhs.add(20);
lhs.add(30);
lhs.add(20);
lhs.add(40);

System.out.println(lhs);
```

Output:

```text
[10, 20, 30, 40]
```

Notice what happened to the second `20`.

It didn't create:

```text
[10, 20, 30, 20, 40]
```

because Set uniqueness still applies.

---

# 22. Does LinkedHashSet sort?

**No.**

This is very important.

Suppose:

```java
lhs.add(50);
lhs.add(10);
lhs.add(30);
```

Result:

```text
[50, 10, 30]
```

not:

```text
[10, 30, 50]
```

LinkedHashSet maintains **insertion order**, not sorted order.

---

# PART 4 — TREESET

# 23. What is TreeSet?

`TreeSet` is a Set implementation that maintains its elements in **sorted order**.

```text
Set
 ↑
TreeSet
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        TreeSet ts = new TreeSet();

        ts.add(30);
        ts.add(10);
        ts.add(20);
        ts.add(10);

        System.out.println(ts);
    }
}
```

Output:

```text
[10, 20, 30]
```

Two things happened:

```text
Duplicate 10 → rejected
Elements      → sorted
```

---

# 24. How does TreeSet maintain sorting?

TreeSet is based on a **sorted tree structure**.

Conceptually:

```text
             20
            /  \
          10    30
```

The actual implementation details are more specific, but for training purposes the key idea is:

```text
TreeSet
   ↓
tree-based sorted Set
```

Because it must maintain ordering, it needs a way to compare elements.

---

# 25. TreeSet and natural ordering

For objects that have a natural ordering, TreeSet can use that ordering.

Numbers:

```text
10 < 20 < 30
```

Strings are ordered lexicographically.

Example:

```java
TreeSet ts = new TreeSet();

ts.add("Java");
ts.add("Python");
ts.add("C");

System.out.println(ts);
```

The elements are arranged according to their natural ordering.

---

# 26. TreeSet does not use insertion order

Suppose:

```java
TreeSet ts = new TreeSet();

ts.add(50);
ts.add(10);
ts.add(30);
```

Output:

```text
[10, 30, 50]
```

Even though insertion was:

```text
50 → 10 → 30
```

TreeSet cares about **sorted order**, not insertion order.

---

# 27. TreeSet and duplicates

Like every Set:

```java
TreeSet ts = new TreeSet();

ts.add(10);
ts.add(20);
ts.add(10);
```

Only:

```text
10
20
```

remain.

So:

```text
TreeSet
 =
unique
+
sorted
```

---

# 28. TreeSet and `null`

This is an important difference.

A normal natural-order `TreeSet` generally cannot accept `null`, because maintaining sorted order requires comparison.

For example:

```java
TreeSet ts = new TreeSet();

ts.add(10);
ts.add(null);
```

can result in:

```text
NullPointerException
```

So remember:

```text
HashSet       → one null allowed
LinkedHashSet → one null allowed
TreeSet       → null generally not allowed
```

There are specialized comparator-based cases worth studying later, but don't let those obscure the basic rule.

---

# 29. TreeSet provides sorted-set operations

TreeSet offers additional useful operations such as:

```text
first()
last()
higher()
lower()
ceiling()
floor()
```

Example:

```java
TreeSet ts = new TreeSet();

ts.add(10);
ts.add(20);
ts.add(30);
ts.add(40);

System.out.println(ts.first());
System.out.println(ts.last());
```

Output:

```text
10
40
```

### `first()`

Returns the smallest element.

### `last()`

Returns the largest element.

---

# 30. `higher()` vs `lower()`

Suppose:

```text
10 20 30 40
```

Then:

```java
ts.higher(20);
```

returns:

```text
30
```

because it means:

> Strictly greater than 20.

And:

```java
ts.lower(20);
```

returns:

```text
10
```

because it means:

> Strictly less than 20.

---

# 31. `ceiling()` vs `floor()`

This distinction causes many doubts.

Suppose:

```text
10 20 30 40
```

### `ceiling(25)`

Find the smallest element **greater than or equal to** 25.

Answer:

```text
30
```

### `floor(25)`

Find the largest element **less than or equal to** 25.

Answer:

```text
20
```

Memory:

```text
ceiling → up
floor   → down
```

More precisely:

```text
ceiling(x) → smallest element >= x
floor(x)   → largest element <= x
```

---

# 32. HashSet vs LinkedHashSet vs TreeSet — Deep Comparison

| Feature                             | HashSet                          | LinkedHashSet                    | TreeSet                          |
| ----------------------------------- | -------------------------------- | -------------------------------- | -------------------------------- |
| Duplicates                          | ❌                                | ❌                                | ❌                                |
| Hashing-based                       | Yes                              | Yes                              | No, tree-based                   |
| Insertion order                     | ❌ Not guaranteed                 | ✅                                | ❌                                |
| Sorted order                        | ❌                                | ❌                                | ✅                                |
| Index                               | ❌                                | ❌                                | ❌                                |
| Allows one `null`                   | ✅                                | ✅                                | Generally ❌                      |
| Main purpose                        | Unique elements                  | Unique + insertion order         | Unique + sorted order            |
| Relative general operation behavior | Usually very good average lookup | Usually very good average lookup | Typically logarithmic operations |
| Extra navigation methods            | No                               | No                               | Yes                              |

---

# 33. The Three Implementations Using One Example

Insert:

```text
40
10
30
20
10
```

### HashSet

```text
Unique
+
No guaranteed order
```

Possible display:

```text
[20, 40, 10, 30]
```

The exact order is not the point.

### LinkedHashSet

```text
[40, 10, 30, 20]
```

Why?

Because insertion order is preserved.

### TreeSet

```text
[10, 20, 30, 40]
```

Why?

Because sorted order is maintained.

---

# 34. Choosing the correct Set

Ask one question:

## "What ordering behavior do I need?"

### Case 1

> I only need unique elements.

Use:

```text
HashSet
```

### Case 2

> I need unique elements and want to preserve insertion order.

Use:

```text
LinkedHashSet
```

### Case 3

> I need unique elements and want them sorted.

Use:

```text
TreeSet
```

Decision:

```text
                 Need Set?
                    ↓
              Unique elements
                    ↓
             Need ordering?
             /       |       \
           No     Insertion   Sorted
            ↓        ↓          ↓
        HashSet  LinkedHashSet TreeSet
```

---

# 35. Set and `equals()` / `hashCode()`

This is an important deeper concept, especially for HashSet.

Suppose we create our own class:

```java
class Student
{
    int id;

    Student(int id)
    {
        this.id = id;
    }
}
```

Now:

```java
HashSet hs = new HashSet();

hs.add(new Student(101));
hs.add(new Student(101));

System.out.println(hs.size());
```

A beginner may expect:

```text
1
```

but without appropriately defining equality/hash behavior, two distinct Student objects can be treated as different objects.

This introduces the important rule:

> For hash-based collections, custom classes should correctly implement `equals()` and `hashCode()` when logical equality is required.

This topic becomes especially important when storing user-defined objects.

---

# 36. Why `equals()` and `hashCode()` both matter

Very simplified conceptual flow:

```text
HashSet.add(object)
       ↓
hashCode()
       ↓
identify relevant area
       ↓
equals()
       ↓
determine logical equality
```

The exact internal implementation is more nuanced, but this model is useful for understanding why both methods matter.

A fundamental contract is:

> If two objects are equal according to `equals()`, they must return the same `hashCode()`.

The reverse is **not** necessarily true:

```text
same hashCode
    ≠
objects must be equal
```

Hash collisions are possible.

---

# 37. Set and Objects

A Set can contain objects of user-defined classes.

For example:

```java
class Student
{
    int id;

    Student(int id)
    {
        this.id = id;
    }
}
```

Then:

```java
HashSet hs = new HashSet();

hs.add(new Student(101));
hs.add(new Student(102));
```

The collection stores object references.

When logical duplicate detection is required, `equals()` and `hashCode()` become essential.

This is why Set isn't merely:

> "A collection that removes duplicate numbers."

It is a framework contract based on **object equality**.

---

# 38. Set and Mutable Objects — Advanced Warning

Suppose an object is inserted into a HashSet and later you change fields that participate in its `equals()`/`hashCode()` calculations.

That can cause problems because the object's effective hash position may no longer correspond to its current state.

Therefore:

> Avoid changing equality-relevant state of objects while they are being used as elements of hash-based collections.

This is a deeper reason why object design matters when using Collections.

---

# 39. Set and Iteration

Set does not provide:

```java
get(0)
get(1)
get(2)
```

because there is no index.

To traverse a Set, you can use an enhanced `for` loop or an Iterator.

Without Generics:

```java
Set s = new HashSet();

s.add(10);
s.add(20);
s.add(30);

for(Object x : s)
{
    System.out.println(x);
}
```

Here `Object` is used because we're deliberately learning Collections without Generics.

---

# 40. Set Does Not Mean "Random"

A very common misunderstanding is:

> "Set means random order."

That isn't the correct definition.

The correct statement is:

> A Set does not permit duplicates; **ordering depends on the implementation**.

For example:

```text
HashSet
→ no guaranteed order

LinkedHashSet
→ insertion order

TreeSet
→ sorted order
```

So:

```text
Set ≠ random
```

---

# 41. Set Does Not Mean "Sorted"

Another common mistake:

> "Set automatically sorts."

False.

Only particular Set implementations provide ordering.

```text
HashSet        → no sorting guarantee
LinkedHashSet  → insertion order
TreeSet        → sorted order
```

---

# 42. Set vs ArrayList — Practical Example

Suppose you receive:

```text
10
20
10
30
20
40
```

### If you need all values including duplicates:

```text
ArrayList
```

Result:

```text
10 20 10 30 20 40
```

### If you need only unique values:

```text
HashSet
```

Result conceptually:

```text
10 20 30 40
```

### If you need unique values in original insertion order:

```text
LinkedHashSet
```

Result:

```text
10 20 30 40
```

### If you need unique values sorted:

```text
TreeSet
```

Result:

```text
10 20 30 40
```

The last three happen to look the same for this input, but their **ordering contracts are different**.

---

# 43. Critical Difference: LinkedHashSet vs TreeSet

Suppose input is:

```text
50
10
30
20
```

### LinkedHashSet

```text
50
10
30
20
```

because:

> Preserve insertion order.

### TreeSet

```text
10
20
30
50
```

because:

> Maintain sorted order.

So:

```text
LinkedHashSet → "When was it inserted?"
TreeSet       → "Where does it belong in sorted order?"
```

---

# 44. Critical Difference: HashSet vs LinkedHashSet

Suppose:

```text
30
10
20
```

### HashSet

Does not guarantee:

```text
30 10 20
```

### LinkedHashSet

Guarantees:

```text
30 10 20
```

Therefore LinkedHashSet provides additional ordering behavior while still retaining Set uniqueness.

---

# 45. Performance — Conceptual View

Don't memorize performance numbers without understanding what they represent.

### HashSet

Hash-based operations are generally **O(1) average** for basic operations under good hashing.

### LinkedHashSet

Also generally **O(1) average** for basic hash-based operations, with additional overhead for maintaining insertion order.

### TreeSet

Basic operations are generally **O(log n)** because the collection maintains a sorted tree structure.

Conceptually:

```text
HashSet
→ fast average lookup
→ no ordering guarantee

LinkedHashSet
→ fast average lookup
→ insertion order

TreeSet
→ sorted
→ logarithmic operations
```

---

# 46. Complete Set Example

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        HashSet h = new HashSet();
        LinkedHashSet l = new LinkedHashSet();
        TreeSet t = new TreeSet();

        h.add(40);
        h.add(10);
        h.add(30);
        h.add(20);
        h.add(10);

        l.add(40);
        l.add(10);
        l.add(30);
        l.add(20);
        l.add(10);

        t.add(40);
        t.add(10);
        t.add(30);
        t.add(20);
        t.add(10);

        System.out.println("HashSet       : " + h);
        System.out.println("LinkedHashSet : " + l);
        System.out.println("TreeSet       : " + t);
    }
}
```

Conceptually:

```text
HashSet
→ unique
→ order not guaranteed

LinkedHashSet
→ unique
→ 40, 10, 30, 20

TreeSet
→ unique
→ 10, 20, 30, 40
```

---

# 47. Final DeepDive Architecture

```text
                         Collection
                              ↑
                             Set
                              ↑
              ┌───────────────┼────────────────┐
              │               │                │
              ↓               ↓                ↓
          HashSet       LinkedHashSet       TreeSet
              │               │                │
              │               │                │
              ↓               ↓                ↓
         Hash-based       Hash-based        Tree-based
         uniqueness       uniqueness        uniqueness
              │               │                │
              ↓               ↓                ↓
        No guaranteed     Insertion          Sorted
           order           order             order
```

---

# 48. The Complete Mental Model

## Set

```text
UNIQUE
```

## HashSet

```text
UNIQUE
+
HASHING
+
NO GUARANTEED ORDER
```

## LinkedHashSet

```text
UNIQUE
+
HASHING
+
INSERTION ORDER
```

## TreeSet

```text
UNIQUE
+
SORTED ORDER
+
TREE STRUCTURE
```

---

# 49. Final Revision Table

| Concept                 | What it means                                                                  |
| ----------------------- | ------------------------------------------------------------------------------ |
| `Set`                   | Interface for unique elements                                                  |
| `HashSet`               | Unique elements, no guaranteed iteration order                                 |
| `LinkedHashSet`         | Unique elements + insertion order                                              |
| `TreeSet`               | Unique elements + sorted order                                                 |
| `add()` returns `true`  | Element was added                                                              |
| `add()` returns `false` | Duplicate/equivalent element already exists                                    |
| `Set.get()`             | ❌ No such List-style operation                                                 |
| `HashSet` null          | One `null` allowed                                                             |
| `LinkedHashSet` null    | One `null` allowed                                                             |
| `TreeSet` null          | Generally not allowed with natural ordering                                    |
| HashSet custom objects  | `equals()` + `hashCode()` are important                                        |
| TreeSet custom objects  | Elements need a valid ordering, typically via natural ordering or a Comparator |

---

# 🔥 The 3 Sentences You Must Remember

> **HashSet:** "I need uniqueness, and I don't care about iteration order."

> **LinkedHashSet:** "I need uniqueness, and I want insertion order."

> **TreeSet:** "I need uniqueness, and I want sorted order."

And the fundamental rule for the entire Set topic:

```text
                    SET
                     ↓
              DUPLICATES = NO
                     ↓
       ┌─────────────┼─────────────┐
       ↓             ↓             ↓
   HashSet      LinkedHashSet    TreeSet
       ↓             ↓             ↓
   No order       Insertion      Sorted
   guarantee        order         order
```

**Generics are intentionally excluded from this entire lesson.**
