# 8. Map in Java — ONEPAGE

> **Training rule:** We are learning the Collections Framework **without Generics** at this stage. All examples below use normal/raw syntax such as `HashMap map = new HashMap();`.

---

# 1. Map Interface

## Definition

`Map` is an interface in `java.util` that stores data in the form of:

```text
KEY → VALUE
```

Example:

```text
101 → "Ravi"
102 → "Kiran"
103 → "Basha"
```

Here:

* `101`, `102`, `103` → keys
* `"Ravi"`, `"Kiran"`, `"Basha"` → values

### Important

A `Map` is **not a subtype of `Collection`**.

```text
Collection
   |
   ├── List
   ├── Set
   └── Queue

Map
   |
   ├── HashMap
   ├── LinkedHashMap
   ├── TreeMap
   ├── Hashtable
   ├── ConcurrentHashMap
   └── WeakHashMap
```

---

## Basic Map Program

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

        System.out.println(map);
    }
}
```

Possible output:

```text
{101=Ravi, 102=Kiran, 103=Basha}
```

---

# 2. HashMap

`HashMap` is the most commonly used general-purpose Map implementation.

```java
HashMap map = new HashMap();
```

### Main characteristics

```text
HashMap
├── Key-value pairs
├── Duplicate keys ❌
├── Duplicate values ✅
├── One null key ✅
├── Multiple null values ✅
├── Maintains insertion order ❌
└── Sorted order ❌
```

Example:

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

---

## Duplicate Key in HashMap

```java
map.put(101, "Ravi");
map.put(101, "Kiran");
```

The second value replaces the first value.

Result:

```text
101 → Kiran
```

Therefore:

```text
Same key
   ↓
old value replaced
```

### Important

A Map cannot contain duplicate keys.

But values can be duplicated:

```java
map.put(101, "Ravi");
map.put(102, "Ravi");
```

This is valid.

---

# 3. LinkedHashMap

`LinkedHashMap` is a subclass of `HashMap` that maintains **insertion order**.

```java
LinkedHashMap map = new LinkedHashMap();
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedHashMap map = new LinkedHashMap();

        map.put(101, "Ravi");
        map.put(102, "Kiran");
        map.put(103, "Basha");

        System.out.println(map);
    }
}
```

Output:

```text
{101=Ravi, 102=Kiran, 103=Basha}
```

### Main characteristics

```text
LinkedHashMap
├── Key-value pairs
├── Duplicate keys ❌
├── Duplicate values ✅
├── One null key ✅
├── Multiple null values ✅
├── Insertion order ✅
└── Sorted order ❌
```

### Easy memory trick

```text
HashMap
   ↓
No guaranteed insertion order

LinkedHashMap
   ↓
Linked = remembers insertion order
```

---

# 4. TreeMap

`TreeMap` stores key-value pairs according to the **sorted order of keys**.

```java
TreeMap map = new TreeMap();
```

Example:

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

The insertion order was:

```text
103
101
102
```

but the keys are displayed in ascending order:

```text
101
102
103
```

### Main characteristics

```text
TreeMap
├── Key-value pairs
├── Duplicate keys ❌
├── Duplicate values ✅
├── null key ❌
├── null values ✅
└── Keys sorted ✅
```

### Important

`TreeMap` sorts by **key**, not by value.

---

# 5. Hashtable

`Hashtable` is a legacy Map implementation.

```java
Hashtable table = new Hashtable();
```

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

        System.out.println(table);
    }
}
```

### Main characteristics

```text
Hashtable
├── Key-value pairs
├── Duplicate keys ❌
├── Duplicate values ✅
├── null key ❌
├── null value ❌
├── Synchronized ✅
└── Legacy class
```

### Very important

`Hashtable` does **not** allow:

```text
null key ❌
null value ❌
```

---

# 6. ConcurrentHashMap

`ConcurrentHashMap` belongs to the `java.util.concurrent` package.

It is designed for **concurrent access**, where multiple threads may operate on the Map.

```java
import java.util.concurrent.*;

ConcurrentHashMap map = new ConcurrentHashMap();
```

Example:

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

### Main characteristics

```text
ConcurrentHashMap
├── Key-value pairs
├── Duplicate keys ❌
├── Duplicate values ✅
├── null key ❌
├── null value ❌
└── Designed for concurrent access
```

### Important distinction

Do not simply say:

> "ConcurrentHashMap is synchronized HashMap."

That is an oversimplification.

It is specifically designed to provide efficient concurrent Map operations rather than making every operation equivalent to the old `Hashtable` synchronization model.

---

# 7. WeakHashMap

`WeakHashMap` is a special Map implementation in which keys are held using **weak references**.

This has an important relationship with **Garbage Collection**.

```java
WeakHashMap map = new WeakHashMap();
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        WeakHashMap map = new WeakHashMap();

        String key = new String("Java");

        map.put(key, "Programming");

        System.out.println(map);

        key = null;

        System.gc();

        System.out.println(map);
    }
}
```

The key may become eligible for garbage collection after:

```java
key = null;
```

If the key is garbage-collected, its corresponding entry can be removed from the `WeakHashMap`.

### Important

The exact timing of garbage collection is **not guaranteed**.

Therefore never write a program that assumes:

```text
System.gc()
   ↓
immediately removes the key
```

It is only a request/hint to the JVM.

---

# 8. Map Implementations — Master Comparison

| Feature          | HashMap     | LinkedHashMap            | TreeMap    | Hashtable               | ConcurrentHashMap | WeakHashMap               |
| ---------------- | ----------- | ------------------------ | ---------- | ----------------------- | ----------------- | ------------------------- |
| Key-value pairs  | ✅           | ✅                        | ✅          | ✅                       | ✅                 | ✅                         |
| Duplicate keys   | ❌           | ❌                        | ❌          | ❌                       | ❌                 | ❌                         |
| Duplicate values | ✅           | ✅                        | ✅          | ✅                       | ✅                 | ✅                         |
| Null key         | ✅ One       | ✅ One                    | ❌          | ❌                       | ❌                 | Special weak-key behavior |
| Null values      | ✅           | ✅                        | ✅          | ❌                       | ❌                 | ✅                         |
| Insertion order  | ❌           | ✅                        | ❌          | ❌                       | ❌                 | ❌                         |
| Sorted keys      | ❌           | ❌                        | ✅          | ❌                       | ❌                 | ❌                         |
| Synchronized     | ❌           | ❌                        | ❌          | ✅                       | Concurrent        | ❌                         |
| Main purpose     | General Map | Preserve insertion order | Sorted Map | Legacy synchronized Map | Concurrent access | Weak-key mappings         |

---

# 9. The Most Important Difference

Remember these six in one picture:

```text
                    MAP
                     |
     ┌───────────────┼────────────────┐
     ↓               ↓                ↓
  HashMap       LinkedHashMap       TreeMap
     |               |                |
 general         insertion          sorted
 purpose          order              keys


     Hashtable       ConcurrentHashMap       WeakHashMap
          |                  |                    |
       legacy           concurrent           weak keys
     synchronized          access
```

---

# 10. Null Rule — Memorize This

```text
HashMap
   null key ✅
   null value ✅

LinkedHashMap
   null key ✅
   null value ✅

TreeMap
   null key ❌
   null value ✅

Hashtable
   null key ❌
   null value ❌

ConcurrentHashMap
   null key ❌
   null value ❌

WeakHashMap
   null key is a special case; weak-key semantics do not make it equivalent to HashMap
   null values ✅
```

For beginner-level training, the most important contrast is:

```text
HashMap       → null allowed
Hashtable     → null not allowed
ConcurrentHashMap → null not allowed
TreeMap       → null key not allowed
```

---

# 11. Important Map Methods

Although your current roadmap lists the Map implementations rather than individual methods, these methods are essential:

| Method            | Purpose                          |
| ----------------- | -------------------------------- |
| `put()`           | Add/update key-value pair        |
| `putAll()`        | Add all entries from another Map |
| `get()`           | Get value using key              |
| `remove()`        | Remove entry using key           |
| `containsKey()`   | Check whether key exists         |
| `containsValue()` | Check whether value exists       |
| `size()`          | Number of entries                |
| `isEmpty()`       | Check whether Map is empty       |
| `clear()`         | Remove all entries               |
| `keySet()`        | Obtain keys                      |
| `values()`        | Obtain values                    |
| `entrySet()`      | Obtain key-value entries         |

---

# 12. One Golden Rule About Keys

In a Map:

```text
KEY → VALUE
```

The **key identifies the entry**.

For example:

```java
map.put(101, "Ravi");
map.put(102, "Kiran");
```

Here:

```text
101 → Ravi
102 → Kiran
```

If you do:

```java
map.put(101, "Basha");
```

you don't get another `101`.

Instead:

```text
101 → Basha
```

The old value `"Ravi"` is replaced.

---

# 13. Final Memory Formula

```text
HashMap
→ General-purpose Map
→ No guaranteed order
→ null key/value allowed

LinkedHashMap
→ HashMap + insertion order

TreeMap
→ Sorted keys

Hashtable
→ Legacy + synchronized
→ no null key/value

ConcurrentHashMap
→ Concurrent access
→ no null key/value

WeakHashMap
→ Weakly held keys
→ useful when entries should disappear
   after keys become garbage-collectible
```

### The easiest way to remember the entire topic

```text
HashMap       → General
LinkedHashMap → Insertion Order
TreeMap       → Sorted Keys
Hashtable     → Legacy + Synchronized
ConcurrentHashMap → Concurrent Access
WeakHashMap   → Weak Keys
```

**And one fundamental fact:** `Map` is separate from the `Collection` hierarchy; a Map stores **key-value mappings**, whereas a `Collection` represents a group of individual elements.
