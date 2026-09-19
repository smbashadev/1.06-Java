# 5. Set in Java — 3LEVEL

**Training rule:** No Generics. Every program below uses normal/raw collection syntax. Generics will be taught separately in Topic 14.

The **3LEVEL method** means we understand every concept at three depths:

* **LEVEL 1 → Basic:** What is it?
* **LEVEL 2 → Working:** How does it behave?
* **LEVEL 3 → Interview/Concept:** Why, differences, traps, and when to use it?

---

# 1. SET INTERFACE

## LEVEL 1 — Basic

### What is Set?

`Set` is an interface in `java.util`.

Its main characteristic is:

> **A Set does not allow duplicate elements.**

Example:

```java
import java.util.*;

class Test
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

Conceptually, the Set contains:

```text
10
20
30
```

The second `10` is not stored as another element.

---

## LEVEL 2 — Working

Set is an **interface**, so we don't normally create it directly.

❌ Wrong:

```java
Set s = new Set();
```

✅ Correct:

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

Hierarchy:

```text
             Collection
                  |
                 Set
          ┌───────┼────────┐
          ↓       ↓        ↓
      HashSet  LinkedHashSet TreeSet
```

### Important properties

| Property           | Set   |
| ------------------ | ----- |
| Duplicate elements | ❌ No  |
| Index              | ❌ No  |
| `get(index)`       | ❌ No  |
| `add()`            | ✅ Yes |
| `remove()`         | ✅ Yes |
| `contains()`       | ✅ Yes |
| `size()`           | ✅ Yes |
| `clear()`          | ✅ Yes |

---

## LEVEL 3 — Concept / Interview

### Why doesn't Set have `get()`?

Because Set is not index-based.

A List has:

```text
0 → 10
1 → 20
2 → 30
```

So:

```java
list.get(1);
```

makes sense.

But Set is based on membership:

```text
Does 20 exist?
```

not:

```text
What is at index 1?
```

Therefore Set doesn't provide:

```java
s.get(1);
```

---

### Set's central rule

Always remember:

```text
SET
 ↓
UNIQUENESS
```

Different Set implementations add different ordering behaviors.

---

# 2. HASHSET

## LEVEL 1 — Basic

`HashSet` is a class that implements `Set`.

```text
Set
 ↑
HashSet
```

Its main characteristics are:

```text
HashSet
   ↓
No duplicates
+
No guaranteed order
```

Example:

```java
import java.util.*;

class Test
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

The duplicate `10` isn't stored again.

---

## LEVEL 2 — Working

### `add()`

```java
HashSet hs = new HashSet();

System.out.println(hs.add(10));
System.out.println(hs.add(20));
System.out.println(hs.add(10));
```

Output:

```text
true
true
false
```

Why?

```text
First 10 → new → true
20       → new → true
Second 10 → duplicate → false
```

So:

```text
add()
 ↓
true  = element added
false = element not added
```

---

### `contains()`

```java
HashSet hs = new HashSet();

hs.add(10);
hs.add(20);
hs.add(30);

System.out.println(hs.contains(20));
System.out.println(hs.contains(50));
```

Output:

```text
true
false
```

---

### `remove()`

```java
HashSet hs = new HashSet();

hs.add(10);
hs.add(20);
hs.add(30);

hs.remove(20);

System.out.println(hs);
```

`20` is removed.

Remember:

```java
hs.remove(20);
```

means:

> Remove the element whose value is `20`.

It does **not** mean:

> Remove index 20.

There is no index.

---

## LEVEL 3 — Concept / Interview

### Does HashSet maintain insertion order?

**No guarantee.**

If:

```java
hs.add(30);
hs.add(10);
hs.add(20);
```

you must not assume the output will always be:

```text
30 10 20
```

The order is not something your program should depend on.

Therefore:

```text
HashSet
→ Unique
→ Order not guaranteed
```

---

### Why is HashSet generally fast?

HashSet uses a **hashing-based mechanism** internally.

Conceptually:

```text
Element
   ↓
hashing
   ↓
internal location
   ↓
store/search
```

This allows efficient operations for typical use cases.

You don't manually calculate the storage location.

---

### Does HashSet allow `null`?

Yes, a HashSet can contain one `null`.

```java
HashSet hs = new HashSet();

hs.add(10);
hs.add(null);
hs.add(null);

System.out.println(hs);
```

Only one `null` is retained.

Why?

Because:

```text
Set → no duplicates
```

So two `null` values would be duplicates.

---

# 3. LINKEDHASHSET

## LEVEL 1 — Basic

`LinkedHashSet` is another Set implementation.

Its main characteristics are:

```text
LinkedHashSet
      ↓
No duplicates
+
Insertion order maintained
```

Example:

```java
import java.util.*;

class Test
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

The elements are maintained according to insertion order:

```text
30
10
20
```

The duplicate `10` is ignored.

---

## LEVEL 2 — Working

Suppose we insert:

```text
30
10
20
30
40
```

LinkedHashSet remembers the first insertion order:

```text
30
10
20
40
```

The duplicate `30` isn't added again.

Conceptually:

```text
Insert:
30 → 10 → 20 → 30 → 40
                 ↑
              duplicate

Result:
30 → 10 → 20 → 40
```

---

### LinkedHashSet does NOT sort

This is important.

```java
LinkedHashSet lhs = new LinkedHashSet();

lhs.add(50);
lhs.add(10);
lhs.add(30);
```

The order remains:

```text
50
10
30
```

It does **not** become:

```text
10
30
50
```

because LinkedHashSet maintains **insertion order**, not sorted order.

---

## LEVEL 3 — Concept / Interview

Why "Linked"?

Conceptually, LinkedHashSet maintains additional linkage information that preserves insertion order.

Think:

```text
Hashing
   +
Linked ordering
```

That's why it can provide:

```text
Unique elements
+
Insertion order
```

### LinkedHashSet vs HashSet

| Feature         | HashSet        | LinkedHashSet |
| --------------- | -------------- | ------------- |
| Duplicates      | No             | No            |
| Insertion order | Not guaranteed | Yes           |
| Sorting         | No             | No            |
| Index           | No             | No            |
| One `null`      | Yes            | Yes           |

Memory trick:

```text
HashSet
→ "I only care about uniqueness."

LinkedHashSet
→ "I care about uniqueness AND insertion order."
```

---

# 4. TREESET

## LEVEL 1 — Basic

`TreeSet` is another implementation of Set.

Its main characteristics are:

```text
TreeSet
   ↓
No duplicates
+
Sorted order
```

Example:

```java
import java.util.*;

class Test
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

Conceptually:

```text
[10, 20, 30]
```

Two things happened:

```text
Duplicate 10
    ↓
Rejected
```

and:

```text
30, 10, 20
    ↓
Sorted
10, 20, 30
```

---

# LEVEL 2 — Working

## TreeSet sorts numbers

```java
TreeSet ts = new TreeSet();

ts.add(40);
ts.add(10);
ts.add(30);
ts.add(20);

System.out.println(ts);
```

Output:

```text
[10, 20, 30, 40]
```

Insertion order doesn't matter.

---

## TreeSet sorts Strings

```java
TreeSet ts = new TreeSet();

ts.add("Java");
ts.add("C");
ts.add("Python");

System.out.println(ts);
```

The elements are arranged according to their natural ordering.

This becomes especially important when we study **Comparable** and **Comparator** later.

---

## TreeSet navigation methods

TreeSet provides methods such as:

```text
first()
last()
lower()
higher()
floor()
ceiling()
```

Suppose:

```text
10 20 30 40
```

### `first()`

```java
ts.first();
```

Result:

```text
10
```

Meaning:

```text
first() → smallest element
```

---

### `last()`

```java
ts.last();
```

Result:

```text
40
```

Meaning:

```text
last() → largest element
```

---

### `lower()`

```java
ts.lower(20);
```

Result:

```text
10
```

Meaning:

> Largest element strictly smaller than `20`.

---

### `higher()`

```java
ts.higher(20);
```

Result:

```text
30
```

Meaning:

> Smallest element strictly greater than `20`.

---

### `floor()`

Suppose:

```text
10 20 30 40
```

```java
ts.floor(25);
```

Result:

```text
20
```

Meaning:

> Largest element less than or equal to `25`.

---

### `ceiling()`

```java
ts.ceiling(25);
```

Result:

```text
30
```

Meaning:

> Smallest element greater than or equal to `25`.

---

## LEVEL 3 — Concept / Interview

### TreeSet needs comparison

TreeSet must determine how elements should be ordered.

For simple types such as numbers and Strings, Java already has natural ordering.

Later, when we study:

```text
Comparable
Comparator
```

you will learn how custom objects can be sorted.

So don't mix that topic into Set yet.

Just remember:

```text
TreeSet
 ↓
Sorted collection
 ↓
Elements must be comparable in some way
```

---

### Does TreeSet allow duplicates?

No.

```java
TreeSet ts = new TreeSet();

ts.add(10);
ts.add(10);
ts.add(20);
```

Result:

```text
10
20
```

Again:

```text
TreeSet → Set → no duplicates
```

---

### Does TreeSet allow null?

With its normal natural ordering, `TreeSet` does not accept `null` as an element because it needs to compare elements to maintain ordering.

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

---

# 5. THREE SET IMPLEMENTATIONS — COMPLETE COMPARISON

| Feature         | HashSet        | LinkedHashSet                | TreeSet                                    |
| --------------- | -------------- | ---------------------------- | ------------------------------------------ |
| Type            | Class          | Class                        | Class                                      |
| Implements Set  | Yes            | Yes                          | Yes                                        |
| Duplicates      | ❌              | ❌                            | ❌                                          |
| Insertion order | Not guaranteed | ✅ Maintained                 | ❌                                          |
| Sorted order    | ❌              | ❌                            | ✅                                          |
| Index           | ❌              | ❌                            | ❌                                          |
| `get(index)`    | ❌              | ❌                            | ❌                                          |
| `null`          | One allowed    | One allowed                  | Normally not allowed with natural ordering |
| Main purpose    | Uniqueness     | Uniqueness + insertion order | Uniqueness + sorting                       |

---

# 6. THE MOST IMPORTANT DIFFERENCE

Suppose we insert:

```text
30
10
20
```

### HashSet

```text
Unique
Order not guaranteed
```

### LinkedHashSet

```text
30
10
20
```

Insertion order maintained.

### TreeSet

```text
10
20
30
```

Sorted order.

Therefore:

```text
                 SET
                  |
          No duplicates
                  |
       ┌──────────┼──────────┐
       ↓          ↓          ↓
   HashSet   LinkedHashSet  TreeSet
       ↓          ↓          ↓
   No order    Insertion    Sorted
               order        order
```

---

# 7. REAL-LIFE DECISION

Suppose you have:

```text
Ravi
Kiran
Ravi
Arun
Kiran
```

### Requirement 1

"I only want unique names."

```text
HashSet
```

### Requirement 2

"I want unique names and I want to preserve the order in which they arrived."

```text
LinkedHashSet
```

### Requirement 3

"I want unique names in sorted order."

```text
TreeSet
```

---

# 8. 3-LEVEL MEMORY MAP

## LEVEL 1 — Remember the definitions

```text
Set
→ No duplicates

HashSet
→ Unique + no guaranteed order

LinkedHashSet
→ Unique + insertion order

TreeSet
→ Unique + sorted order
```

---

## LEVEL 2 — Remember the behavior

```text
HashSet
→ Fast general-purpose uniqueness

LinkedHashSet
→ Hashing + insertion-order maintenance

TreeSet
→ Tree-based sorted Set
```

---

## LEVEL 3 — Remember the traps

```text
Set has no index.

Set doesn't provide get(index).

HashSet doesn't guarantee insertion order.

LinkedHashSet doesn't sort.

TreeSet doesn't preserve insertion order.

All three reject duplicates.

TreeSet's normal natural-order form doesn't accept null.
```

---

# 🔥 FINAL 3-LEVEL TEST

### Q1. Which interface represents a collection of unique elements?

**Set**

### Q2. Which Set should you choose when order doesn't matter?

**HashSet**

### Q3. Which Set preserves insertion order?

**LinkedHashSet**

### Q4. Which Set maintains sorted order?

**TreeSet**

### Q5. Can Set contain duplicate elements?

**No.**

### Q6. Can you use `get(0)` on a Set?

**No.**

### Q7. Does LinkedHashSet sort elements?

**No. It maintains insertion order.**

### Q8. Does HashSet guarantee insertion order?

**No.**

### Q9. Why does TreeSet need comparison?

**Because it must maintain elements in sorted order.**

### Q10. Which one should you immediately think of when the requirement says:

> "Unique + sorted"

**TreeSet.**

### Q11. "Unique + insertion order"

**LinkedHashSet.**

### Q12. "Unique + no ordering requirement"

**HashSet.**

---

## ⭐ One-line formula

```text
HashSet       = UNIQUE
LinkedHashSet = UNIQUE + INSERTION ORDER
TreeSet       = UNIQUE + SORTED ORDER
```

That is the **core of Set**.
