# 5. Set in Java — ONEPAGE

> **Training rule:** No Generics. All programs use normal/raw collection syntax.
> **Goal:** Complete individual understanding of `Set`, `HashSet`, `LinkedHashSet`, and `TreeSet`.

---

# 1. Set Interface

## Definition

`Set` is an interface in `java.util` that represents a collection of **unique elements**.

The most important property:

> **A Set does not allow duplicate elements.**

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

The second `10` is not added because it is already present.

### Set identity

```text
Set
 ↓
Duplicates → NOT allowed
Index      → NOT available
```

Unlike `List`:

```text
List → duplicates allowed
Set  → duplicates not allowed
```

---

## Important Set properties

| Property           | Set                       |
| ------------------ | ------------------------- |
| Interface/Class    | Interface                 |
| Duplicates         | ❌ No                      |
| Index-based access | ❌ No                      |
| `add()`            | ✅ Yes                     |
| `remove()`         | ✅ Yes                     |
| `contains()`       | ✅ Yes                     |
| Insertion order    | Depends on implementation |
| Sorting            | Depends on implementation |

---

## Set cannot be directly instantiated

```java
Set s = new Set();
```

❌ Invalid.

Because `Set` is an interface.

Instead:

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

# 2. HashSet

## Definition

`HashSet` is a class that implements `Set`.

```text
Set
 ↑
HashSet
```

Its main purpose is:

> **Store unique elements without guaranteeing insertion order.**

Example:

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

Output could be:

```text
[20, 10, 30]
```

The important point is **not the displayed order**.

The important point is:

```text
10 → stored only once
```

---

## HashSet characteristics

```text
HashSet
   ↓
Duplicates → No
Insertion order → Not guaranteed
Index → No
Sorting → No
```

Therefore don't write code that assumes:

```text
HashSet always prints in a particular order
```

The iteration order should not be relied upon.

---

## HashSet and `null`

A `HashSet` permits a single `null` element.

```java
HashSet hs = new HashSet();

hs.add(10);
hs.add(null);
hs.add(20);
hs.add(null);

System.out.println(hs);
```

Only one `null` can remain because Set does not allow duplicates.

---

# 3. LinkedHashSet

## Definition

`LinkedHashSet` is a Set implementation that:

> **Does not allow duplicates and maintains insertion order.**

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

Notice:

```text
Inserted:
30 → 10 → 20 → 10

Result:
30 → 10 → 20
```

The duplicate `10` is ignored, while the original insertion order is preserved.

---

## LinkedHashSet characteristics

```text
LinkedHashSet
       ↓
Duplicates → No
Insertion order → Yes
Index → No
Sorting → No
```

This is the major difference between `HashSet` and `LinkedHashSet`.

---

# 4. TreeSet

## Definition

`TreeSet` is a Set implementation that:

> **Stores unique elements in sorted order.**

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

Notice the insertion order was:

```text
30 → 10 → 20 → 10
```

But TreeSet gives:

```text
10 → 20 → 30
```

because it maintains sorted order.

---

## TreeSet characteristics

```text
TreeSet
   ↓
Duplicates → No
Sorted order → Yes
Index → No
```

For normal comparable values such as numbers, the natural ordering is:

```text
10 < 20 < 30
```

For Strings, natural ordering is lexicographical.

---

# 5. HashSet vs LinkedHashSet vs TreeSet

This is the **most important comparison**.

| Feature            | HashSet          | LinkedHashSet            | TreeSet               |
| ------------------ | ---------------- | ------------------------ | --------------------- |
| Duplicate elements | ❌                | ❌                        | ❌                     |
| Insertion order    | ❌ Not guaranteed | ✅ Yes                    | ❌                     |
| Sorted order       | ❌                | ❌                        | ✅                     |
| Index              | ❌                | ❌                        | ❌                     |
| Main idea          | Unique elements  | Unique + insertion order | Unique + sorted order |

### Memory trick

```text
HashSet
   ↓
Unique
No guaranteed order
```

```text
LinkedHashSet
   ↓
Unique
+
Insertion order
```

```text
TreeSet
   ↓
Unique
+
Sorted order
```

---

# 6. Set Hierarchy

```text
                    Collection
                        ↑
                       Set
                        ↑
             ┌──────────┼──────────┐
             │          │          │
             ↓          ↓          ↓
          HashSet   LinkedHashSet  TreeSet
```

Remember:

```text
Set
 ↓
unique elements
```

Then:

```text
HashSet
 ↓
unique + no guaranteed order
```

```text
LinkedHashSet
 ↓
unique + insertion order
```

```text
TreeSet
 ↓
unique + sorted order
```

---

# 7. The Same Data in Three Sets

This single example makes the difference crystal clear:

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

Conceptually:

```text
Input:
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

### TreeSet

```text
10
20
30
```

---

# 8. `add()` Return Value in Set

A very important point:

`add()` returns a `boolean`.

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
First 10 → newly added → true
20       → newly added → true
Second 10 → duplicate   → false
```

Therefore:

```text
add()
 ↓
true  → element was added
false → element was not added
         because duplicate already exists
```

---

# 9. Can Set use `get(index)`?

**No.**

This is because Set does not provide index-based access.

This is valid for List:

```java
List l = new ArrayList();

l.add(10);
l.add(20);

System.out.println(l.get(0));
```

But this is invalid:

```java
Set s = new HashSet();

s.add(10);
s.add(20);

s.get(0);    // ❌
```

Because:

```text
List → index
Set  → no index
```

---

# 10. Can Set contain `null`?

It depends on the implementation.

### HashSet

Can contain one `null`.

### LinkedHashSet

Can contain one `null`.

### TreeSet

Traditional natural-order TreeSet does **not** generally permit `null` because it needs to compare elements to maintain sorted order; adding `null` can result in `NullPointerException`.

So don't memorize:

```text
"All Sets allow null."
```

Instead remember:

```text
HashSet        → one null allowed
LinkedHashSet  → one null allowed
TreeSet        → null generally not allowed for natural ordering
```

---

# 11. When should I use each Set?

### Need unique elements and don't care about order?

```text
HashSet
```

### Need unique elements and insertion order?

```text
LinkedHashSet
```

### Need unique elements and sorted order?

```text
TreeSet
```

### Decision tree

```text
Need duplicates?
       │
       └── NO
            ↓
          Set
            ↓
       Need ordering?
       ┌────┼──────────────┐
       │    │              │
      No  Insertion      Sorted
       │    order          │
       ↓      │            ↓
   HashSet    ↓         TreeSet
              ↓
       LinkedHashSet
```

---

# 12. ONEPAGE Final Memory Map

```text
                         Collection
                              ↑
                             Set
                              ↑
              ┌───────────────┼────────────────┐
              │               │                │
              ↓               ↓                ↓
           HashSet      LinkedHashSet       TreeSet
```

### Set

```text
Unique elements
No index
```

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

### The one-line formula

```text
HashSet       = UNIQUE
LinkedHashSet = UNIQUE + INSERTION ORDER
TreeSet       = UNIQUE + SORTED ORDER
```

### Most important List → Set distinction

```text
LIST
 ↓
duplicates allowed
index available
```

```text
SET
 ↓
duplicates not allowed
index unavailable
```

> **No Generics were used here.** Generics remain a separate topic in your roadmap and will be introduced only when we reach **14. Generics**.
