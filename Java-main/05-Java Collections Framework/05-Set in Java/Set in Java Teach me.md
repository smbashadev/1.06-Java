# 5. Set in Java — TEACHME

We will learn this **from the beginning**, as if you have never studied Set before.

**Important training rule:** We will **not use Generics** here. All examples use normal/raw collection syntax. Generics will come later as a separate topic.

---

# PART 1 — SET INTERFACE

## 1. First understand the problem

Suppose you want to store marks:

```text
10
20
30
20
10
40
```

If you use a normal array:

```java
int a[] = {10, 20, 30, 20, 10, 40};
```

Java happily stores duplicates.

But sometimes we don't want duplicates.

We want:

```text
10
20
30
40
```

This is where **Set** becomes useful.

---

# 2. What is Set?

`Set` is an interface in Java's `java.util` package.

Its main job is:

> **Store unique elements.**

The simplest way to remember it:

```text
SET
 ↓
NO DUPLICATES
```

For example:

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

The result contains:

```text
10
20
30
```

There is only one `10`.

---

# 3. Why do we need Set?

Imagine a college system storing student roll numbers.

Suppose:

```text
101
102
103
101
104
102
```

Roll numbers should be unique.

If we use an array:

```text
101 102 103 101 104 102
```

we have duplicates.

With a Set:

```text
101 102 103 104
```

The Set automatically prevents duplicate elements.

So remember:

> **When uniqueness is important, think about Set.**

---

# 4. Set is an interface

You cannot directly create a Set object:

```java
Set s = new Set();
```

❌ Wrong.

Why?

Because `Set` is an interface.

We need a class that implements Set.

For example:

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

Think of it like this:

```text
              Set
               ↑
       ┌───────┼────────┐
       │       │        │
       ↓       ↓        ↓
   HashSet  LinkedHashSet TreeSet
```

---

# 5. Set vs List

This is extremely important.

### List

```java
List l = new ArrayList();

l.add(10);
l.add(20);
l.add(10);
```

Result:

```text
10 20 10
```

Duplicates are allowed.

### Set

```java
Set s = new HashSet();

s.add(10);
s.add(20);
s.add(10);
```

Result contains:

```text
10 20
```

Duplicate `10` is not stored again.

So:

```text
LIST
 ↓
Duplicates allowed


SET
 ↓
Duplicates NOT allowed
```

---

# 6. Does Set have index numbers?

No.

This is another major difference.

In a List:

```text
Index:   0    1    2
         ↓    ↓    ↓
        10   20   30
```

You can write:

```java
l.get(0);
```

But Set doesn't work with indexes.

You cannot write:

```java
s.get(0);
```

❌ There is no `get(index)` operation in Set.

Why?

Because Set is concerned with **uniqueness**, not positional/index-based storage.

Remember:

```text
List → Index
Set  → No Index
```

---

# PART 2 — HASHSET

# 7. What is HashSet?

`HashSet` is a class that implements the `Set` interface.

```text
Set
 ↑
HashSet
```

Its job is:

> Store unique elements without guaranteeing insertion order.

---

# 8. First HashSet program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        HashSet hs = new HashSet();

        hs.add(10);
        hs.add(20);
        hs.add(10);
        hs.add(30);

        System.out.println(hs);
    }
}
```

The result contains only:

```text
10
20
30
```

The duplicate `10` is rejected.

---

# 9. What does "Hash" mean?

You don't need to be frightened by the word **hash**.

For now, understand the basic idea:

HashSet uses a **hashing mechanism** internally to organize elements.

Very simplified:

```text
Element
   ↓
Hashing
   ↓
Find suitable internal location
   ↓
Store / Search
```

You don't manually perform these steps.

Java does them internally.

---

# 10. Most important HashSet rule

HashSet does **not guarantee insertion order**.

Suppose we write:

```java
HashSet hs = new HashSet();

hs.add(30);
hs.add(10);
hs.add(20);
```

You might see:

```text
[20, 10, 30]
```

or another order.

Therefore:

> **Never depend on HashSet's displayed/iteration order.**

Don't think:

```text
HashSet = random
```

The better statement is:

```text
HashSet = order is NOT guaranteed
```

---

# 11. Does HashSet allow duplicates?

No.

```java
HashSet hs = new HashSet();

hs.add(10);
hs.add(20);
hs.add(10);
hs.add(20);
hs.add(30);
```

Only unique values remain.

```text
10
20
30
```

---

# 12. Understanding `add()` return value

This is a very useful Set concept.

Look:

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

### First:

```text
add(10)
```

10 doesn't exist.

So:

```text
Added → true
```

### Second:

```text
add(20)
```

20 doesn't exist.

So:

```text
Added → true
```

### Third:

```text
add(10)
```

10 already exists.

So:

```text
Not added → false
```

Therefore remember:

```text
add()
 ↓
true  → element was added
false → element was not added
```

---

# 13. HashSet with `contains()`

Suppose:

```java
HashSet hs = new HashSet();

hs.add(10);
hs.add(20);
hs.add(30);
```

Now:

```java
System.out.println(hs.contains(20));
```

Output:

```text
true
```

Because 20 exists.

But:

```java
System.out.println(hs.contains(50));
```

gives:

```text
false
```

So:

```text
contains()
 ↓
"Is this element present?"
```

---

# 14. HashSet with `remove()`

```java
HashSet hs = new HashSet();

hs.add(10);
hs.add(20);
hs.add(30);

hs.remove(20);

System.out.println(hs);
```

Now `20` is removed.

Remember:

```text
Set has no index
```

So:

```java
hs.remove(20);
```

means:

> Remove the element `20`.

It doesn't mean "remove index 20."

---

# 15. HashSet and null

HashSet allows one `null`.

```java
HashSet hs = new HashSet();

hs.add(10);
hs.add(null);
hs.add(20);
hs.add(null);

System.out.println(hs);
```

Only one `null` is retained.

Why?

Because Set doesn't allow duplicates.

So:

```text
null
null
```

is treated as duplicate occurrence.

---

# PART 3 — LINKEDHASHSET

# 16. What is LinkedHashSet?

`LinkedHashSet` is another implementation of Set.

Its special feature is:

> **It maintains insertion order.**

So remember:

```text
LinkedHashSet
      ↓
Unique elements
      +
Insertion order
```

---

# 17. First LinkedHashSet program

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

Look carefully.

We inserted:

```text
30
10
20
10
```

The second `10` is rejected.

The remaining elements appear in insertion order:

```text
30
10
20
```

---

# 18. Why is it called LinkedHashSet?

Conceptually, it combines:

```text
Hashing
   +
Linked ordering
```

The hashing mechanism helps manage Set elements.

The linked ordering mechanism helps remember insertion order.

You don't manually create or manage the links.

Java handles that internally.

---

# 19. LinkedHashSet vs HashSet

Suppose we insert:

```text
30
10
20
```

### HashSet

```text
30
10
20
```

is **not guaranteed** to be the iteration order.

### LinkedHashSet

```text
30
10
20
```

is maintained as insertion order.

Therefore:

```text
HashSet
→ Unique
→ Order not guaranteed


LinkedHashSet
→ Unique
→ Insertion order
```

---

# 20. Does LinkedHashSet allow duplicates?

No.

```java
LinkedHashSet lhs = new LinkedHashSet();

lhs.add(10);
lhs.add(20);
lhs.add(10);
lhs.add(30);
```

Result:

```text
10
20
30
```

So don't confuse:

```text
Linked
```

with:

```text
Duplicates allowed
```

It is still a Set.

Therefore duplicates are still prohibited.

---

# 21. Does LinkedHashSet sort?

No.

Suppose:

```java
LinkedHashSet lhs = new LinkedHashSet();

lhs.add(50);
lhs.add(10);
lhs.add(30);
```

It maintains:

```text
50
10
30
```

It does **not** automatically change it to:

```text
10
30
50
```

Why?

Because LinkedHashSet maintains:

> **Insertion order**

not sorted order.

---

# PART 4 — TREESET

# 22. What is TreeSet?

`TreeSet` is another implementation of Set.

Its special feature is:

> **It stores elements in sorted order.**

Remember:

```text
TreeSet
   ↓
Unique elements
   +
Sorted order
```

---

# 23. First TreeSet program

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
Duplicate 10
     ↓
Rejected
```

and:

```text
30, 10, 20
     ↓
10, 20, 30
```

because TreeSet maintains sorted order.

---

# 24. Why is it called TreeSet?

Because TreeSet is based on a tree-based sorted structure.

Think of it conceptually as:

```text
             20
            /  \
          10    30
```

The actual implementation has more details, but for learning the Collections Framework, remember:

```text
TreeSet
   ↓
Tree-based
   ↓
Sorted
```

---

# 25. TreeSet and numbers

Numbers have a natural ordering.

For example:

```text
10 < 20 < 30 < 40
```

So:

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

TreeSet arranges them according to sorted order.

---

# 26. TreeSet and Strings

TreeSet can also sort Strings according to their natural ordering.

```java
TreeSet ts = new TreeSet();

ts.add("Java");
ts.add("Python");
ts.add("C");

System.out.println(ts);
```

The Strings are arranged according to their natural lexicographical ordering.

The key idea is:

```text
TreeSet
   ↓
Needs a way to compare elements
   ↓
Maintains sorted order
```

---

# 27. Does TreeSet allow duplicates?

No.

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
TreeSet = Unique + Sorted
```

---

# 28. TreeSet and null

This is an important rule.

A normal natural-order TreeSet generally does **not** accept `null`.

For example:

```java
TreeSet ts = new TreeSet();

ts.add(10);
ts.add(null);
```

can produce:

```text
NullPointerException
```

Why?

Because TreeSet needs to compare elements to maintain sorted order.

How should you remember this?

```text
HashSet
→ one null allowed


LinkedHashSet
→ one null allowed


TreeSet
→ null generally not allowed with natural ordering
```

---

# 29. TreeSet's special methods

TreeSet has useful methods for navigating sorted elements.

Some important ones are:

```text
first()
last()
higher()
lower()
ceiling()
floor()
```

Let's learn them one by one.

---

# 30. `first()`

Suppose:

```java
TreeSet ts = new TreeSet();

ts.add(10);
ts.add(20);
ts.add(30);
ts.add(40);
```

Then:

```java
System.out.println(ts.first());
```

Output:

```text
10
```

Because `10` is the smallest element.

Remember:

```text
first() → smallest element
```

---

# 31. `last()`

```java
System.out.println(ts.last());
```

Output:

```text
40
```

Because `40` is the largest element.

Remember:

```text
last() → largest element
```

---

# 32. `higher()`

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

Because it means:

> Give me the smallest element **strictly greater than 20**.

So:

```text
higher(20)
     ↓
30
```

---

# 33. `lower()`

```java
ts.lower(20);
```

returns:

```text
10
```

Because:

> Give me the largest element **strictly smaller than 20**.

So:

```text
lower(20)
     ↓
10
```

---

# 34. `ceiling()`

Suppose:

```text
10 20 30 40
```

Now:

```java
ts.ceiling(25);
```

returns:

```text
30
```

Why?

We need:

> Smallest element greater than or equal to 25.

30 qualifies.

Remember:

```text
ceiling(x)
→ smallest element >= x
```

---

# 35. `floor()`

```java
ts.floor(25);
```

returns:

```text
20
```

Why?

We need:

> Largest element less than or equal to 25.

20 qualifies.

Remember:

```text
floor(x)
→ largest element <= x
```

---

# 36. Very easy way to remember TreeSet navigation

Suppose:

```text
10 20 30 40
```

For `20`:

```text
lower(20)   → 10
higher(20)  → 30
```

For `25`:

```text
floor(25)   → 20
ceiling(25) → 30
```

Memory:

```text
lower   → strictly below
higher  → strictly above

floor   → below or equal
ceiling → above or equal
```

---

# PART 5 — PUT EVERYTHING TOGETHER

# 37. One program comparing all three

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        HashSet h = new HashSet();
        LinkedHashSet l = new LinkedHashSet();
        TreeSet t = new TreeSet();

        h.add(30);
        h.add(10);
        h.add(20);

        l.add(30);
        l.add(10);
        l.add(20);

        t.add(30);
        t.add(10);
        t.add(20);

        System.out.println("HashSet       = " + h);
        System.out.println("LinkedHashSet = " + l);
        System.out.println("TreeSet       = " + t);
    }
}
```

Now think about each collection.

### HashSet

```text
Unique
No guaranteed order
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

---

# 38. Real-life example

Imagine you have these names:

```text
Ravi
Kiran
Ravi
Arun
Kiran
```

You want different things in different situations.

### Requirement 1

> I only want unique names.

Use:

```text
HashSet
```

### Requirement 2

> I want unique names in the order they were entered.

Use:

```text
LinkedHashSet
```

### Requirement 3

> I want unique names alphabetically sorted.

Use:

```text
TreeSet
```

That's the real reason Java provides multiple Set implementations.

---

# 39. A simple decision method

Whenever you see a Set question, ask:

### Question 1

**Do I need duplicates?**

If yes:

```text
Don't choose Set.
Think about List.
```

If no:

```text
Use Set.
```

Then ask:

### Question 2

**Do I care about order?**

If no:

```text
HashSet
```

If insertion order:

```text
LinkedHashSet
```

If sorted order:

```text
TreeSet
```

---

# 40. The Complete Decision Tree

```text
                 Need unique elements?
                         |
                        YES
                         |
                    Choose Set
                         |
                   Need ordering?
                  /       |       \
                NO        |        YES
                |         |         |
                ↓         |     What order?
            HashSet       |      /       \
                          |     /         \
                          | insertion     sorted
                          |    ↓            ↓
                          | LinkedHashSet  TreeSet
```

---

# 41. Common Beginner Mistakes

## Mistake 1

Thinking Set allows duplicates.

❌ Wrong.

```text
Set → duplicates not allowed
```

---

## Mistake 2

Thinking HashSet preserves insertion order.

❌ Wrong.

```text
HashSet → order not guaranteed
```

---

## Mistake 3

Thinking LinkedHashSet sorts.

❌ Wrong.

```text
LinkedHashSet → insertion order
```

---

## Mistake 4

Thinking every Set is sorted.

❌ Wrong.

```text
Only TreeSet provides sorted ordering.
```

---

## Mistake 5

Trying to use `get(index)`.

```java
s.get(0);
```

❌ Set has no index-based `get()`.

---

## Mistake 6

Thinking `remove(0)` means "remove first element."

For a Set, there is no index 0.

```java
s.remove(0);
```

means:

> Remove the element whose value/object is `0`.

---

# 42. Most Important Comparison

| Requirement                      | Collection      |
| -------------------------------- | --------------- |
| Duplicates allowed + index       | `ArrayList`     |
| Unique + no ordering requirement | `HashSet`       |
| Unique + insertion order         | `LinkedHashSet` |
| Unique + sorted order            | `TreeSet`       |

This table will become very useful when we later compare **List vs Set vs Queue vs Map**.

---

# 43. Your Set Memory Formula

Don't memorize four pages.

Remember these four lines:

```text
Set
 ↓
NO DUPLICATES
```

```text
HashSet
 ↓
UNIQUE + NO GUARANTEED ORDER
```

```text
LinkedHashSet
 ↓
UNIQUE + INSERTION ORDER
```

```text
TreeSet
 ↓
UNIQUE + SORTED ORDER
```

And:

```text
HashSet       → "I don't care about order."
LinkedHashSet → "Keep the order I inserted."
TreeSet       → "Sort it for me."
```

---

# 44. Final Teacher Test

If I ask you:

### "Which Set should I use when duplicates are not allowed and order doesn't matter?"

Answer:

**HashSet**

### "Which Set should I use when duplicates are not allowed and insertion order must be preserved?"

Answer:

**LinkedHashSet**

### "Which Set should I use when duplicates are not allowed and elements must remain sorted?"

Answer:

**TreeSet**

### "Does Set have index?"

Answer:

**No.**

### "Does Set allow duplicates?"

Answer:

**No.**

### "Does HashSet guarantee insertion order?"

Answer:

**No.**

### "Does LinkedHashSet sort?"

Answer:

**No, it maintains insertion order.**

### "Does TreeSet maintain insertion order?"

Answer:

**No, it maintains sorted order.**

---

# 🔥 One Final Picture

```text
                         SET
                          |
                   "NO DUPLICATES"
                          |
             ┌────────────┼────────────┐
             |            |            |
             ↓            ↓            ↓
         HashSet    LinkedHashSet   TreeSet
             |            |            |
             ↓            ↓            ↓
        No guaranteed   Insertion     Sorted
           order          order        order
             |            |            |
             ↓            ↓            ↓
        "I need only   "Keep the     "Sort the
         uniqueness"    input order"   elements"
```

**No Generics anywhere in this lesson.** Generics remain completely separate and will be taught in **Topic 14 — Generics**.
