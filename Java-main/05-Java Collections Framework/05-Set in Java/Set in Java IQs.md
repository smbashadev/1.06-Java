# 5. Set in Java — DOUBTKILLER

**Training rule:** No Generics. All programs use normal/raw collection syntax. Generics will be taught separately in Topic 14.

The purpose of **DOUBTKILLER** is different from the other formats: here we attack the places where students commonly get confused, especially **duplicates, ordering, indexes, `add()`, `null`, HashSet vs LinkedHashSet vs TreeSet, and TreeSet comparison**.

---

# 1. SET INTERFACE — DOUBTS KILLED

## Doubt 1: What exactly is a Set?

A `Set` is an interface in `java.util` used to represent a collection that **does not allow duplicate elements**.

```java
Set s = new HashSet();
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

Conceptually:

```text
10
20
30
```

The second `10` is not added.

### Remember

```text
Set = uniqueness
```

---

# 2. DOUBT: Is Set a class?

**No. Set is an interface.**

Therefore:

```java
Set s = new Set();
```

❌ Invalid.

But:

```java
Set s = new HashSet();
```

✅ Valid.

```java
Set s = new LinkedHashSet();
```

✅ Valid.

```java
Set s = new TreeSet();
```

✅ Valid.

Hierarchy:

```text
             Collection
                  |
                 Set
          ┌───────┼────────┐
          ↓       ↓        ↓
      HashSet  LinkedHashSet TreeSet
```

---

# 3. DOUBT: If Set doesn't allow duplicates, what happens when I add one?

Look at:

```java
HashSet s = new HashSet();

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

```text
add(10)
→ 10 is new
→ true

add(20)
→ 20 is new
→ true

add(10)
→ 10 already exists
→ false
```

Therefore:

```text
add()
 ↓
true  → actually added
false → duplicate/not added
```

This is a **very important Set behavior**.

---

# 4. DOUBT: Does `add()` always return true?

No.

For a Set:

```java
s.add(10);
```

can return:

```text
true
```

if the element was added.

But:

```java
s.add(10);
```

again can return:

```text
false
```

because the element already exists.

---

# 5. DOUBT: Does Set have index numbers?

**No.**

List:

```text
Index:   0    1    2
         ↓    ↓    ↓
        10   20   30
```

Set:

```text
10
20
30
```

There is no concept that you should use as:

```text
index 0
index 1
index 2
```

Therefore:

```java
s.get(0);
```

❌ Invalid.

---

# 6. DOUBT: Can I remove an element by index?

With a Set, you remove an **element**, not an index.

```java
HashSet s = new HashSet();

s.add(10);
s.add(20);
s.add(30);

s.remove(20);
```

This means:

> Remove the element whose value is `20`.

It does **not** mean:

> Remove element at index 20.

There is no index.

---

# 7. DOUBT: Can I search an element?

Yes.

Use:

```java
contains()
```

Example:

```java
HashSet s = new HashSet();

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

Think:

```text
contains(x)
     ↓
"Does x exist?"
```

---

# 8. DOUBT: Does Set preserve insertion order?

**Set itself does not promise insertion order.**

And this is where the three implementations become important.

```text
HashSet
→ order not guaranteed

LinkedHashSet
→ insertion order

TreeSet
→ sorted order
```

---

# 9. HASHSET — DOUBTS KILLED

## Doubt: Why use HashSet?

Use HashSet when the main requirement is:

> **I need unique elements, and I don't need to depend on their order.**

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        HashSet s = new HashSet();

        s.add(30);
        s.add(10);
        s.add(20);
        s.add(10);

        System.out.println(s);
    }
}
```

The duplicate `10` isn't stored.

---

# 10. DOUBT: Does HashSet store elements randomly?

Be careful with the wording.

Don't say:

> HashSet randomly stores elements.

The safer and correct concept is:

> **HashSet does not guarantee insertion order.**

You must not write your program assuming a particular order.

For example:

```java
s.add(30);
s.add(10);
s.add(20);
```

Do not depend on:

```text
30 10 20
```

or any other particular order.

---

# 11. DOUBT: Why doesn't HashSet maintain insertion order?

Because HashSet is designed around **hashing**, rather than maintaining a linked insertion sequence.

Conceptually:

```text
Element
   ↓
Hashing
   ↓
Internal organization
   ↓
Store/search
```

Its primary purpose is efficient Set operations, not preserving insertion order.

If insertion order matters, use:

```text
LinkedHashSet
```

---

# 12. DOUBT: Does HashSet allow `null`?

Yes.

A HashSet can contain one `null`.

```java
HashSet s = new HashSet();

s.add(10);
s.add(null);
s.add(20);
s.add(null);

System.out.println(s);
```

There is only one `null`.

Why?

Because:

```text
Set
→ duplicates not allowed
```

So:

```text
null
null
```

would be a duplicate.

---

# 13. LINKEDHASHSET — DOUBTS KILLED

## Doubt: Why do we need LinkedHashSet if HashSet already removes duplicates?

Because sometimes you need **both**:

```text
No duplicates
+
Insertion order
```

That's exactly where LinkedHashSet is useful.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedHashSet s = new LinkedHashSet();

        s.add(30);
        s.add(10);
        s.add(20);
        s.add(10);

        System.out.println(s);
    }
}
```

Conceptually:

```text
30
10
20
```

The duplicate `10` is rejected.

The original insertion order is retained.

---

# 14. DOUBT: Does "Linked" mean duplicates are allowed?

**Absolutely not.**

This is a common misunderstanding.

`LinkedHashSet` is still a:

```text
Set
```

Therefore:

```text
duplicates → not allowed
```

The word `Linked` relates to maintaining ordering information.

So:

```text
LinkedHashSet
=
Set
+
insertion-order maintenance
```

Not:

```text
Set
+
duplicates
```

---

# 15. DOUBT: Does LinkedHashSet sort elements?

**No.**

Suppose:

```java
LinkedHashSet s = new LinkedHashSet();

s.add(50);
s.add(10);
s.add(30);
```

The order is:

```text
50
10
30
```

It doesn't automatically become:

```text
10
30
50
```

Because:

```text
LinkedHashSet → insertion order
```

not:

```text
LinkedHashSet → sorted order
```

---

# 16. TREESET — DOUBTS KILLED

## Doubt: Why do we need TreeSet?

When you need:

```text
Unique elements
+
Sorted order
```

use TreeSet.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        TreeSet s = new TreeSet();

        s.add(30);
        s.add(10);
        s.add(20);
        s.add(10);

        System.out.println(s);
    }
}
```

Result:

```text
[10, 20, 30]
```

Two rules are operating:

```text
Duplicate 10
→ rejected

30, 10, 20
→ sorted
→ 10, 20, 30
```

---

# 17. DOUBT: Does TreeSet preserve insertion order?

**No.**

Suppose:

```text
Inserted:
50
10
30
20
```

TreeSet maintains sorted order:

```text
10
20
30
50
```

So:

```text
TreeSet
≠ insertion order
```

Instead:

```text
TreeSet
= sorted order
```

---

# 18. DOUBT: Why does TreeSet sort?

TreeSet is based on a tree-oriented sorted structure.

To maintain sorted order, it needs to determine the ordering relationship between elements.

For numbers:

```text
10 < 20 < 30
```

For Strings, Java provides natural ordering.

Later, `Comparable` and `Comparator` explain how custom objects can be ordered.

For now:

```text
TreeSet
→ needs elements that can be ordered
```

---

# 19. DOUBT: Does TreeSet allow duplicates?

No.

```java
TreeSet s = new TreeSet();

s.add(10);
s.add(20);
s.add(10);
```

Result:

```text
10
20
```

Remember:

```text
TreeSet
   ↓
Set
   ↓
No duplicates
```

---

# 20. DOUBT: Does TreeSet allow null?

With its normal natural ordering, **no**.

Example:

```java
TreeSet s = new TreeSet();

s.add(10);
s.add(null);
```

This can result in:

```text
NullPointerException
```

Why?

TreeSet needs to compare elements to maintain sorted order.

`null` cannot participate in normal natural ordering with an integer.

So remember:

```text
HashSet       → one null allowed
LinkedHashSet → one null allowed
TreeSet       → null not allowed with natural ordering
```

---

# 21. TREESET SPECIAL METHODS

These methods are frequently confusing.

Suppose:

```java
TreeSet s = new TreeSet();

s.add(10);
s.add(20);
s.add(30);
s.add(40);
```

Visualize:

```text
10 ---- 20 ---- 30 ---- 40
```

---

## `first()`

```java
s.first();
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

## `last()`

```java
s.last();
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

# 22. `lower()` vs `higher()`

Suppose:

```text
10 20 30 40
```

### `lower(20)`

```java
s.lower(20);
```

Result:

```text
10
```

Meaning:

> Largest element **strictly smaller** than 20.

### `higher(20)`

```java
s.higher(20);
```

Result:

```text
30
```

Meaning:

> Smallest element **strictly greater** than 20.

Memory:

```text
lower  → below
higher → above
```

---

# 23. `floor()` vs `ceiling()`

This pair causes many doubts.

Suppose:

```text
10 20 30 40
```

Ask for `25`.

### `floor(25)`

```java
s.floor(25);
```

Result:

```text
20
```

Because floor means:

> Largest element **≤ 25**

### `ceiling(25)`

```java
s.ceiling(25);
```

Result:

```text
30
```

Because ceiling means:

> Smallest element **≥ 25**

Memory:

```text
floor
→ below OR equal

ceiling
→ above OR equal
```

---

# 24. The Four TreeSet Methods Together

Suppose:

```text
10 20 30 40
```

Ask about `25`.

```text
lower(25)   → 20
floor(25)   → 20

higher(25)  → 30
ceiling(25) → 30
```

Now ask about `20`.

```text
lower(20)   → 10
floor(20)   → 20

higher(20)  → 30
ceiling(20) → 20
```

The difference is the word **equal**.

```text
lower   → <
higher  → >

floor   → ≤
ceiling → ≥
```

🔥 This is one of the most useful TreeSet memory tricks.

---

# 25. BIGGEST DOUBT — HASHSET vs LINKEDHASHSET vs TREESET

Suppose the input is:

```text
30
10
20
10
```

All three reject the second `10`.

The difference is **ordering**.

### HashSet

```text
Unique
Order not guaranteed
```

### LinkedHashSet

```text
Unique
Insertion order
```

### TreeSet

```text
Unique
Sorted order
```

Therefore:

```text
             SET
              |
        No duplicates
              |
      ┌───────┼────────┐
      ↓       ↓        ↓
 HashSet  LinkedHashSet TreeSet
    ↓         ↓          ↓
No order   Insertion    Sorted
           order
```

---

# 26. BIGGEST INTERVIEW TRAP

### Question:

> Which collection should I use if duplicates must be removed but the original insertion order must remain?

Don't answer:

```text
HashSet
```

Correct:

```text
LinkedHashSet
```

Because:

```text
HashSet
→ uniqueness
→ no guaranteed insertion order

LinkedHashSet
→ uniqueness
→ insertion order
```

---

# 27. Another Trap

### Question:

> Which Set automatically sorts elements?

Answer:

**TreeSet**

Not:

```text
HashSet
```

and not:

```text
LinkedHashSet
```

---

# 28. Another Trap

### Question:

> Does LinkedHashSet mean sorted Set?

No.

It means:

```text
LinkedHashSet
→ insertion order
```

TreeSet means:

```text
TreeSet
→ sorted order
```

---

# 29. Another Trap

### Question:

> Can I use `get(0)` on HashSet?

No.

Same answer for:

```text
HashSet
LinkedHashSet
TreeSet
```

All are Sets.

Therefore:

```text
No index
No get(index)
```

---

# 30. Another Trap — `remove()`

Consider:

```java
HashSet s = new HashSet();

s.add(10);
s.add(20);
s.add(30);

s.remove(20);
```

Some beginners think:

> "Remove the second element."

❌ No.

There is no index.

It means:

> Remove the element `20`.

---

# 31. Another Trap — Duplicate Object vs Same Value

At beginner level, understand the central rule:

```text
Set
→ checks whether an equivalent element is already present
```

For simple values:

```java
s.add(10);
s.add(10);
```

the duplicate is obvious.

When you later start putting custom objects into Sets, `equals()` and `hashCode()` become extremely important, especially for HashSet.

That is a **separate advanced topic** and should not be mixed into your first Set lesson.

---

# 32. One Program to Kill Multiple Doubts

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        HashSet h = new HashSet();

        System.out.println(h.add(30));
        System.out.println(h.add(10));
        System.out.println(h.add(20));
        System.out.println(h.add(10));

        System.out.println("HashSet = " + h);

        LinkedHashSet l = new LinkedHashSet();

        l.add(30);
        l.add(10);
        l.add(20);
        l.add(10);

        System.out.println("LinkedHashSet = " + l);

        TreeSet t = new TreeSet();

        t.add(30);
        t.add(10);
        t.add(20);
        t.add(10);

        System.out.println("TreeSet = " + t);

        System.out.println("First = " + t.first());
        System.out.println("Last = " + t.last());
        System.out.println("Lower than 20 = " + t.lower(20));
        System.out.println("Higher than 20 = " + t.higher(20));
        System.out.println("Floor of 25 = " + t.floor(25));
        System.out.println("Ceiling of 25 = " + t.ceiling(25));
    }
}
```

This single program demonstrates:

```text
✓ Set uniqueness
✓ add() return value
✓ HashSet
✓ LinkedHashSet
✓ TreeSet
✓ Duplicate rejection
✓ Insertion order
✓ Sorted order
✓ first()
✓ last()
✓ lower()
✓ higher()
✓ floor()
✓ ceiling()
```

---

# 33. FINAL DOUBTKILLER TABLE

| Doubt                                        | Correct answer         |
| -------------------------------------------- | ---------------------- |
| Is Set a class?                              | ❌ No, interface        |
| Does Set allow duplicates?                   | ❌ No                   |
| Does Set have index?                         | ❌ No                   |
| Can Set use `get(0)`?                        | ❌ No                   |
| Does HashSet guarantee insertion order?      | ❌ No                   |
| Does HashSet allow one `null`?               | ✅ Yes                  |
| Does LinkedHashSet allow duplicates?         | ❌ No                   |
| Does LinkedHashSet preserve insertion order? | ✅ Yes                  |
| Does LinkedHashSet sort?                     | ❌ No                   |
| Does TreeSet allow duplicates?               | ❌ No                   |
| Does TreeSet sort?                           | ✅ Yes                  |
| Does TreeSet preserve insertion order?       | ❌ No                   |
| Does normal TreeSet allow `null`?            | ❌ No                   |
| `lower(x)` means?                            | Largest element `< x`  |
| `higher(x)` means?                           | Smallest element `> x` |
| `floor(x)` means?                            | Largest element `≤ x`  |
| `ceiling(x)` means?                          | Smallest element `≥ x` |
| Unique + no ordering requirement?            | HashSet                |
| Unique + insertion order?                    | LinkedHashSet          |
| Unique + sorted order?                       | TreeSet                |

---

# 🔥 THE 3 LINES YOU MUST NEVER FORGET

```text
HashSet
→ UNIQUE + NO GUARANTEED ORDER
```

```text
LinkedHashSet
→ UNIQUE + INSERTION ORDER
```

```text
TreeSet
→ UNIQUE + SORTED ORDER
```

And the ultimate Set rule:

```text
SET = NO DUPLICATES + NO INDEX
```

**Generics are deliberately not used anywhere in this DOUBTKILLER lesson.**
