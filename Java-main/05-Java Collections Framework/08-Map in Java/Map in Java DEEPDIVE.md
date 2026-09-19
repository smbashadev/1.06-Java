# 8. Map in Java — DEEPDIVE

> **Training rule:** No Generics anywhere in these examples. We are intentionally using the traditional/raw collection syntax because this is for your training notes.
>
> ```java
> HashMap map = new HashMap();
> ```
>
> not:
>
> ```java
> HashMap<Integer, String> map = new HashMap<>();
> ```

---

# PART 1 — Map Interface

## 1. What is a Map?

A **Map** is an interface in Java used to store data in the form of **key-value pairs**.

The basic structure is:

```text
KEY  →  VALUE
```

Example:

```text
101 → "Ravi"
102 → "Kiran"
103 → "Basha"
```

Here:

```text
101, 102, 103
      ↓
     Keys

Ravi, Kiran, Basha
      ↓
    Values
```

A Map is useful when we want to retrieve a value using some unique identifier.

For example:

```text
Roll Number → Student Name
Product ID  → Product Name
Employee ID → Employee Name
Country Code → Country Name
```

---

# 2. Why Do We Need Map?

Suppose we have:

```text
101 Ravi
102 Kiran
103 Basha
104 John
```

If we want to find the student name for roll number `103`, a Map allows us to directly associate:

```text
103 → Basha
```

So the basic idea is:

```text
Key
 ↓
Find associated Value
```

---

# 3. Map vs Collection

This is a very important conceptual point.

A `Collection` generally stores **individual elements**:

```text
10
20
30
40
```

A `Map` stores **key-value pairs**:

```text
101 → Ravi
102 → Kiran
103 → Basha
```

Therefore:

```text
Collection
    ↓
individual elements

Map
    ↓
key + value
```

---

# 4. Is Map a Child Interface of Collection?

**No.**

This is one of the most frequently asked questions.

`Map` does **not** extend `Collection`.

Simplified hierarchy:

```text
                    Iterable
                       |
                   Collection
                 /     |      \
              List    Set     Queue
              
              
                    Map
                     |
       ┌─────────────┼──────────────┐
       ↓             ↓              ↓
   HashMap     LinkedHashMap     TreeMap
       ↓
   ...
```

So:

```text
Map ≠ Collection
```

They are separate parts of the Java Collections Framework.

---

# 5. Important Map Terminology

Suppose:

```java
map.put(101, "Ravi");
```

Then:

```text
101
 ↓
KEY

"Ravi"
 ↓
VALUE
```

The complete mapping is called an **entry**.

```text
101 = Ravi
```

So remember:

```text
Map
 ↓
Entries
 ↓
Key + Value
```

---

# 6. Can a Map Have Duplicate Keys?

**No.**

Consider:

```java
map.put(101, "Ravi");
map.put(101, "Kiran");
```

The second `put()` does not create another key `101`.

Instead:

```text
101 → Kiran
```

The previous value is replaced.

---

# 7. Can a Map Have Duplicate Values?

**Yes.**

```java
map.put(101, "Ravi");
map.put(102, "Ravi");
```

This is valid.

The keys are different:

```text
101 → Ravi
102 → Ravi
```

Therefore:

```text
Duplicate key   ❌
Duplicate value ✅
```

---

# 8. Basic Map Methods

Before studying individual Map implementations, understand these fundamental methods.

---

## `put()`

Adds a key-value mapping.

```java
map.put(101, "Ravi");
```

Meaning:

```text
101 → Ravi
```

If key `101` already exists, its old value is replaced.

---

## `get()`

Retrieves the value associated with a key.

```java
System.out.println(map.get(101));
```

Output:

```text
Ravi
```

---

## `remove()`

Removes the mapping associated with a key.

```java
map.remove(101);
```

---

## `containsKey()`

Checks whether a key exists.

```java
System.out.println(map.containsKey(101));
```

Result:

```text
true
```

or:

```text
false
```

---

## `containsValue()`

Checks whether a value exists.

```java
System.out.println(map.containsValue("Ravi"));
```

---

## `size()`

Returns the number of mappings.

```java
System.out.println(map.size());
```

If:

```text
101 → Ravi
102 → Kiran
103 → Basha
```

then:

```text
size = 3
```

---

## `isEmpty()`

Checks whether the Map contains no entries.

```java
map.isEmpty();
```

---

## `clear()`

Removes all mappings.

```java
map.clear();
```

---

# 9. `keySet()`

Returns the keys as a Set view.

```java
System.out.println(map.keySet());
```

If the Map contains:

```text
101 → Ravi
102 → Kiran
103 → Basha
```

you may see:

```text
[101, 102, 103]
```

---

# 10. `values()`

Returns a collection view of the values.

```java
System.out.println(map.values());
```

Possible output:

```text
[Ravi, Kiran, Basha]
```

---

# 11. `entrySet()`

Returns the Map's entries.

```java
System.out.println(map.entrySet());
```

Possible output:

```text
[101=Ravi, 102=Kiran, 103=Basha]
```

Think:

```text
keySet()
    ↓
only keys

values()
    ↓
only values

entrySet()
    ↓
key + value
```

---

# 12. Complete Basic Map Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Map map = new HashMap();

        map.put(101, "Ravi");
        map.put(102, "Kiran");
        map.put(103, "Basha");

        System.out.println("Map = " + map);

        System.out.println("Value = " + map.get(102));

        System.out.println("Contains Key = " +
                           map.containsKey(101));

        System.out.println("Contains Value = " +
                           map.containsValue("Basha"));

        System.out.println("Size = " + map.size());

        System.out.println("Keys = " + map.keySet());

        System.out.println("Values = " + map.values());

        System.out.println("Entries = " + map.entrySet());
    }
}
```

---

# PART 2 — HashMap

# 13. What is HashMap?

`HashMap` is a class that implements the `Map` interface.

```java
HashMap map = new HashMap();
```

Package:

```java
java.util
```

Import:

```java
import java.util.*;
```

It is the most commonly used general-purpose Map implementation.

---

# 14. How HashMap Stores Data

Conceptually:

```text
HashMap
   |
   +---- Key → Value
   |
   +---- Key → Value
   |
   +---- Key → Value
```

Example:

```text
101 → Ravi
102 → Kiran
103 → Basha
```

Internally, HashMap uses hashing to determine where entries should be stored.

For beginner understanding:

```text
Key
 ↓
hashing
 ↓
location/bucket
 ↓
entry
```

You normally don't need to manually calculate this location.

---

# 15. HashMap Characteristics

```text
HashMap
├── Key-value pairs
├── Duplicate keys ❌
├── Duplicate values ✅
├── One null key ✅
├── Multiple null values ✅
├── Guaranteed insertion order ❌
├── Sorted order ❌
└── Not synchronized
```

---

# 16. HashMap Example

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        HashMap map = new HashMap();

        map.put(101, "Ravi");
        map.put(102, "Kiran");
        map.put(103, "Basha");

        System.out.println(map);
    }
}
```

The exact display order should **not** be relied upon.

Do not write a program assuming HashMap will always display insertion order.

---

# 17. HashMap Duplicate-Key Example

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        HashMap map = new HashMap();

        map.put(101, "Ravi");
        map.put(101, "Kiran");

        System.out.println(map);
    }
}
```

Result conceptually:

```text
101 → Kiran
```

The old value is replaced.

---

# 18. HashMap Duplicate-Value Example

```java
map.put(101, "Ravi");
map.put(102, "Ravi");
```

Valid:

```text
101 → Ravi
102 → Ravi
```

because the keys are different.

---

# 19. HashMap and `null`

HashMap permits:

```java
map.put(null, "Ravi");
```

and:

```java
map.put(101, null);
```

It can have:

```text
one null key
multiple null values
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        HashMap map = new HashMap();

        map.put(null, "Ravi");
        map.put(101, null);
        map.put(102, null);

        System.out.println(map);
    }
}
```

---

# 20. When Should We Use HashMap?

Use HashMap when you primarily need:

> **Fast general-purpose key-value storage and you do not need a particular iteration order or sorted keys.**

Example:

```text
Student ID → Student Name
```

---

# PART 3 — LinkedHashMap

# 21. What is LinkedHashMap?

`LinkedHashMap` is a Map implementation that maintains a predictable **insertion order** when iterating over its entries.

```java
LinkedHashMap map = new LinkedHashMap();
```

It extends `HashMap`.

Conceptually:

```text
HashMap
   ↑
LinkedHashMap
```

---

# 22. Why Does LinkedHashMap Maintain Order?

It combines hash-table-style lookup with a linked structure that maintains the order of entries.

Suppose insertion is:

```text
101
102
103
104
```

Iteration follows:

```text
101
102
103
104
```

So:

```text
LinkedHashMap
       ↓
HashMap behavior
       +
insertion-order maintenance
```

---

# 23. LinkedHashMap Example

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedHashMap map = new LinkedHashMap();

        map.put(103, "Basha");
        map.put(101, "Ravi");
        map.put(102, "Kiran");

        System.out.println(map);
    }
}
```

Output:

```text
{103=Basha, 101=Ravi, 102=Kiran}
```

The important point is that the insertion order is preserved.

---

# 24. LinkedHashMap Characteristics

```text
LinkedHashMap
├── Duplicate keys ❌
├── Duplicate values ✅
├── Null key ✅
├── Null values ✅
├── Insertion order ✅
├── Sorted order ❌
└── Not synchronized
```

---

# 25. Is LinkedHashMap Sorted?

**No.**

This is a common mistake.

If you insert:

```text
103
101
102
```

LinkedHashMap maintains:

```text
103
101
102
```

It does **not** automatically produce:

```text
101
102
103
```

Therefore:

```text
LinkedHashMap → insertion order
TreeMap       → sorted key order
```

---

# PART 4 — TreeMap

# 26. What is TreeMap?

`TreeMap` is a Map implementation that maintains its entries according to the **sorted order of keys**.

```java
TreeMap map = new TreeMap();
```

It implements the `NavigableMap` interface through the sorted-map hierarchy.

For basic understanding:

```text
Map
 ↓
SortedMap
 ↓
NavigableMap
 ↓
TreeMap
```

---

# 27. TreeMap Example

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        TreeMap map = new TreeMap();

        map.put(103, "Basha");
        map.put(101, "Ravi");
        map.put(102, "Kiran");

        System.out.println(map);
    }
}
```

Output:

```text
{101=Ravi, 102=Kiran, 103=Basha}
```

Although insertion was:

```text
103
101
102
```

the keys are arranged in ascending order.

---

# 28. Does TreeMap Sort Values?

**No.**

TreeMap sorts according to its **keys**.

Example:

```text
103 → Apple
101 → Zebra
102 → Mango
```

TreeMap produces key order:

```text
101 → Zebra
102 → Mango
103 → Apple
```

The values are not alphabetically sorted.

---

# 29. TreeMap and `null`

The standard natural-ordering use of `TreeMap` does not permit a `null` key because it needs to compare keys.

This:

```java
map.put(null, "Ravi");
```

results in a `NullPointerException` in the normal natural-ordering case.

Null values are allowed:

```java
map.put(101, null);
```

---

# 30. TreeMap Characteristics

```text
TreeMap
├── Duplicate keys ❌
├── Duplicate values ✅
├── Null key ❌
├── Null values ✅
├── Insertion order ❌
├── Sorted keys ✅
└── Not synchronized
```

---

# 31. When Should We Use TreeMap?

Use TreeMap when:

> **You need Map data arranged according to key order.**

For example:

```text
101 → Ravi
102 → Kiran
103 → Basha
```

and you need keys to remain sorted.

---

# PART 5 — Hashtable

# 32. What is Hashtable?

`Hashtable` is a legacy Map implementation.

```java
Hashtable table = new Hashtable();
```

Package:

```text
java.util
```

It predates many of the modern collection classes.

---

# 33. Hashtable Characteristics

```text
Hashtable
├── Key-value pairs
├── Duplicate keys ❌
├── Duplicate values ✅
├── Null key ❌
├── Null value ❌
├── Synchronized ✅
└── Legacy
```

---

# 34. Hashtable Example

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

        System.out.println(table);
    }
}
```

---

# 35. Why Doesn't Hashtable Allow null?

Both are prohibited:

```java
table.put(null, "Ravi");
```

and:

```java
table.put(101, null);
```

They result in `NullPointerException`.

Remember:

```text
Hashtable
   ↓
null key ❌
null value ❌
```

---

# 36. Why Is Hashtable Called Legacy?

It belongs to the older Java collection APIs and was designed before the modern Collections Framework was introduced.

It is still part of Java and can be encountered in older applications.

But for new applications, other Map implementations are generally preferred depending on the requirement.

---

# 37. Hashtable vs HashMap

This is a very important comparison.

| Feature                    | HashMap | Hashtable |
| -------------------------- | ------- | --------- |
| Modern general-purpose Map | Yes     | Legacy    |
| Synchronization            | No      | Yes       |
| Null key                   | Yes     | No        |
| Null value                 | Yes     | No        |
| Duplicate keys             | No      | No        |
| Duplicate values           | Yes     | Yes       |

Don't summarize this as:

> Hashtable = better HashMap.

They have different design histories and concurrency characteristics.

---

# PART 6 — ConcurrentHashMap

# 38. What is ConcurrentHashMap?

`ConcurrentHashMap` is a Map implementation designed for **concurrent access by multiple threads**.

Package:

```java
java.util.concurrent
```

Import:

```java
import java.util.concurrent.*;
```

Create:

```java
ConcurrentHashMap map = new ConcurrentHashMap();
```

---

# 39. Why Do We Need ConcurrentHashMap?

Imagine multiple threads accessing the same Map.

```text
Thread 1 ──┐
Thread 2 ──┼──→ Shared Map
Thread 3 ──┘
```

Ordinary `HashMap` is not designed for unrestricted concurrent modification by multiple threads.

`ConcurrentHashMap` provides a collection designed for concurrent use.

---

# 40. ConcurrentHashMap Example

```java
import java.util.concurrent.*;

class Demo
{
    public static void main(String[] args)
    {
        ConcurrentHashMap map = new ConcurrentHashMap();

        map.put(101, "Ravi");
        map.put(102, "Kiran");

        System.out.println(map);
    }
}
```

---

# 41. ConcurrentHashMap and null

This is extremely important.

`ConcurrentHashMap` does **not** permit:

```text
null key ❌
null value ❌
```

Therefore:

```java
map.put(null, "Ravi");
```

is invalid.

And:

```java
map.put(101, null);
```

is also invalid.

---

# 42. Is ConcurrentHashMap the Same as Hashtable?

**No.**

Both support concurrent access, but they are different implementations with different designs.

Simplified training distinction:

```text
Hashtable
    ↓
legacy synchronized Map

ConcurrentHashMap
    ↓
modern concurrent Map designed for scalable concurrent access
```

Do not write:

> "ConcurrentHashMap is just a newer Hashtable."

That is incorrect.

---

# 43. ConcurrentHashMap Characteristics

```text
ConcurrentHashMap
├── Duplicate keys ❌
├── Duplicate values ✅
├── Null key ❌
├── Null value ❌
├── Concurrent access ✅
└── Not a legacy replacement by simple renaming
```

---

# PART 7 — WeakHashMap

# 44. What is WeakHashMap?

`WeakHashMap` is a special Map implementation in which its keys are held using **weak references**.

This connects the Map with Java's **Garbage Collection** mechanism.

```java
WeakHashMap map = new WeakHashMap();
```

---

# 45. Why Is WeakHashMap Different?

Normal Map implementations such as HashMap maintain strong references to their keys.

Conceptually:

```text
HashMap
   ↓
Map holds key
   ↓
key remains strongly reachable through Map
```

WeakHashMap uses weak references for keys.

Conceptually:

```text
WeakHashMap
      ↓
 weak reference
      ↓
     Key
```

If there are no strong references to the key elsewhere, the key may become eligible for garbage collection.

When that happens, the corresponding mapping can disappear from the WeakHashMap.

---

# 46. Basic WeakHashMap Example

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        WeakHashMap map = new WeakHashMap();

        String key = new String("Java");

        map.put(key, "Programming");

        System.out.println("Before = " + map);

        key = null;

        System.gc();

        System.out.println("After = " + map);
    }
}
```

You may observe:

```text
Before = {Java=Programming}
After = {}
```

But **this output is not guaranteed**.

---

# 47. Why Is the Output Not Guaranteed?

Because:

```java
System.gc();
```

does not guarantee that garbage collection will happen immediately.

Therefore this:

```text
key = null;
System.gc();
```

means:

> The key may become eligible for garbage collection.

It does **not** mean:

> The JVM must immediately collect the key.

So don't create an exam answer that promises a particular `After` output.

---

# 48. WeakHashMap's Real Purpose

WeakHashMap can be useful when:

> You want mappings to disappear automatically when their keys are no longer strongly referenced elsewhere.

A conceptual use case is an auxiliary mapping associated with objects whose lifetime should determine the lifetime of the mapping.

---

# 49. Important WeakHashMap Trap

The following does **not** necessarily make the entry disappear:

```java
key = null;
```

if another strong reference to the same key still exists.

Example:

```java
String key1 = new String("Java");
String key2 = key1;

map.put(key1, "Programming");

key1 = null;
```

The key is still strongly reachable through:

```text
key2
```

Therefore it is not eligible for garbage collection merely because `key1` became `null`.

---

# 50. WeakHashMap and Values

The special weak-reference behavior concerns the **keys**, not the values.

Conceptually:

```text
WeakHashMap

KEY   → weakly referenced
VALUE → normally referenced
```

This distinction is extremely important.

---

# PART 8 — Deep Comparison

# 51. All Seven Map Topics Together

```text
                         MAP
                          |
      ┌───────────┬───────┼────────┬────────────┐
      ↓           ↓       ↓        ↓            ↓
   HashMap   LinkedHashMap TreeMap Hashtable ConcurrentHashMap
      |
      |
 WeakHashMap
```

More accurately, these are separate implementations of Map-related interfaces; the diagram is only for memory organization.

---

# 52. HashMap vs LinkedHashMap vs TreeMap

This is probably the **most important comparison**.

| Feature          | HashMap | LinkedHashMap | TreeMap         |
| ---------------- | ------- | ------------- | --------------- |
| General purpose  | ✅       | ✅             | For sorted maps |
| Insertion order  | ❌       | ✅             | ❌               |
| Key sorting      | ❌       | ❌             | ✅               |
| Null key         | ✅       | ✅             | ❌               |
| Null values      | ✅       | ✅             | ✅               |
| Duplicate keys   | ❌       | ❌             | ❌               |
| Duplicate values | ✅       | ✅             | ✅               |

Remember:

```text
HashMap
   ↓
No order requirement

LinkedHashMap
   ↓
Insertion order

TreeMap
   ↓
Sorted keys
```

---

# 53. HashMap vs Hashtable vs ConcurrentHashMap

| Feature            | HashMap        | Hashtable    | ConcurrentHashMap        |
| ------------------ | -------------- | ------------ | ------------------------ |
| Modern general Map | ✅              | ❌            | ✅                        |
| Concurrent use     | Not inherently | Synchronized | Designed for concurrency |
| Null key           | ✅              | ❌            | ❌                        |
| Null value         | ✅              | ❌            | ❌                        |
| Legacy             | ❌              | ✅            | ❌                        |

The key memory rule:

```text
HashMap
→ ordinary/general use

Hashtable
→ old synchronized Map

ConcurrentHashMap
→ concurrent Map
```

---

# 54. WeakHashMap vs HashMap

The major difference is **key reference behavior**.

```text
HashMap
   ↓
Strong key references

WeakHashMap
   ↓
Weak key references
```

Therefore a WeakHashMap entry may disappear when its key is no longer strongly reachable elsewhere and is garbage-collected.

---

# 55. Ordering Comparison

```text
HashMap
   ↓
No guaranteed insertion order

LinkedHashMap
   ↓
Insertion order

TreeMap
   ↓
Sorted key order

Hashtable
   ↓
No guaranteed insertion order

ConcurrentHashMap
   ↓
Do not depend on insertion/sorted order

WeakHashMap
   ↓
Do not use it when a particular iteration order is required
```

---

# 56. Null Comparison

Memorize this table:

| Map               |     Null Key | Null Value |
| ----------------- | -----------: | ---------: |
| HashMap           |            ✅ |          ✅ |
| LinkedHashMap     |            ✅ |          ✅ |
| TreeMap           |            ❌ |          ✅ |
| Hashtable         |            ❌ |          ❌ |
| ConcurrentHashMap |            ❌ |          ❌ |
| WeakHashMap       | Special case |          ✅ |

For `TreeMap`, the no-null-key rule applies to the normal natural-ordering use.

---

# 57. Important Deep Concept — Key Uniqueness

Why can't a Map have duplicate keys?

Because a Map conceptually represents:

```text
KEY → VALUE
```

A key identifies one mapping.

If you write:

```java
map.put(101, "Ravi");
map.put(101, "Kiran");
```

the Map changes the mapping:

```text
Before:
101 → Ravi

After:
101 → Kiran
```

The second operation replaces the previous value.

---

# 58. Important Deep Concept — Values Don't Need to Be Unique

Consider:

```java
map.put(101, "Ravi");
map.put(102, "Ravi");
map.put(103, "Ravi");
```

This is perfectly valid:

```text
101 → Ravi
102 → Ravi
103 → Ravi
```

because the keys are unique.

Therefore:

```text
Map
├── Keys → unique
└── Values → duplicates allowed
```

---

# 59. Important Deep Concept — Updating vs Adding

This:

```java
map.put(101, "Ravi");
```

may be described as adding a mapping.

But:

```java
map.put(101, "Kiran");
```

when `101` already exists is actually **updating/replacing** the value associated with that key.

So `put()` has two common outcomes:

```text
new key
   ↓
new mapping

existing key
   ↓
replace value
```

---

# 60. Important Deep Concept — Map Does Not Store Duplicate Entries for the Same Key

Suppose:

```java
map.put(101, "Ravi");
map.put(101, "Kiran");
map.put(101, "Basha");
```

Final mapping:

```text
101 → Basha
```

Not:

```text
101 → Ravi
101 → Kiran
101 → Basha
```

---

# 61. Choosing the Correct Map

This is the practical decision tree.

### Need general key-value storage?

```text
HashMap
```

### Need insertion order?

```text
LinkedHashMap
```

### Need sorted keys?

```text
TreeMap
```

### Need old synchronized Map behavior?

```text
Hashtable
```

### Need concurrent access?

```text
ConcurrentHashMap
```

### Need mappings whose keys can disappear when no longer strongly referenced?

```text
WeakHashMap
```

---

# 62. Interview-Style Questions

### Q1. Is Map a Collection?

**No.**

Map is a separate interface hierarchy.

---

### Q2. Can Map contain duplicate keys?

**No.**

---

### Q3. Can Map contain duplicate values?

**Yes.**

---

### Q4. Which Map maintains insertion order?

**LinkedHashMap.**

---

### Q5. Which Map sorts keys?

**TreeMap.**

---

### Q6. Which traditional Map is synchronized?

**Hashtable.**

---

### Q7. Which Map is designed for concurrent access?

**ConcurrentHashMap.**

---

### Q8. Which Map uses weak references for keys?

**WeakHashMap.**

---

### Q9. Does HashMap allow null?

**Yes — one null key and multiple null values.**

---

### Q10. Does Hashtable allow null?

**No — neither null keys nor null values.**

---

### Q11. Does ConcurrentHashMap allow null?

**No — neither null keys nor null values.**

---

### Q12. Does TreeMap allow a null key?

In normal natural-ordering use, **no**.

---

### Q13. Does LinkedHashMap maintain sorted order?

**No. It maintains insertion order.**

---

### Q14. Does TreeMap maintain insertion order?

**No. It maintains sorted key order.**

---

### Q15. Does HashMap guarantee insertion order?

**No.**

---

# 63. Final Deep-Dive Mental Model

```text
                         MAP
                          |
              KEY  →  VALUE
                          |
          ┌───────────────┼─────────────────┐
          |               |                 |
      HashMap       LinkedHashMap        TreeMap
          |               |                 |
       General       Insertion order     Sorted keys
          |
          |
     ┌────┴─────────────┐
     ↓                  ↓
 Hashtable       ConcurrentHashMap
     |                  |
 Legacy             Concurrent
 synchronized         access
     |
     └─────────────────────────────┐
                                   ↓
                              WeakHashMap
                                   |
                               Weak keys
```

## The six one-line definitions

**HashMap**

> General-purpose Map; no guaranteed iteration order; permits a null key and null values.

**LinkedHashMap**

> Hash-based Map that maintains insertion order.

**TreeMap**

> Sorted Map that orders mappings according to their keys.

**Hashtable**

> Legacy synchronized Map that does not permit null keys or null values.

**ConcurrentHashMap**

> Concurrent Map implementation designed for efficient access by multiple threads and does not permit null keys or values.

**WeakHashMap**

> Map whose keys are weakly referenced, allowing mappings to disappear after keys become eligible for garbage collection.

---

## Final rule for your Collections training

For **this stage**, keep the hierarchy mentally separated:

```text
COLLECTION SIDE                         MAP SIDE

Iterable
   ↓
Collection                              Map
   ↓                                     ↓
 ┌─┼─────────┐                    ┌──────┼─────────┐
List Set Queue                   HashMap TreeMap ...
```

And **Generics are deliberately postponed to Topic 14**, exactly as in your roadmap.
