# 8. Map in Java — DOUBTKILLER

This is the **confusion-removal version**. I will focus on the questions that usually cause mistakes in training, exams, interviews, and programs.

**Your rule is followed:** **NO Generics** in any program.

---

# 1. Map Interface — DOUBTKILLER

## Doubt 1: What exactly is a Map?

A Map stores data as:

```text
KEY → VALUE
```

Example:

```text
101 → Ravi
102 → Kiran
103 → Basha
```

Here:

```text
101 → Key
Ravi → Value
```

Think of it like a dictionary:

```text
WORD → MEANING
```

---

## Doubt 2: Is Map a Collection?

**No.**

This is extremely important.

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
    └── ...
```

So:

```text
Map ≠ Collection
```

Map is a separate interface in the Collections Framework.

---

## Doubt 3: Why doesn't Map extend Collection?

Because the data models are different.

Collection stores individual elements:

```text
Ravi
Kiran
Basha
```

Map stores mappings:

```text
101 → Ravi
102 → Kiran
103 → Basha
```

Therefore Map requires its own operations such as:

```text
put()
get()
containsKey()
containsValue()
```

rather than behaving exactly like a Collection.

---

## Doubt 4: Can Map contain duplicate keys?

**No.**

```java
map.put(101, "Ravi");
map.put(101, "Kiran");
```

The second `put()` replaces the previous value.

Final mapping:

```text
101 → Kiran
```

It does **not** become:

```text
101 → Ravi
101 → Kiran
```

---

## Doubt 5: Can Map contain duplicate values?

**Yes.**

```java
map.put(101, "Ravi");
map.put(102, "Ravi");
```

This is perfectly valid:

```text
101 → Ravi
102 → Ravi
```

So remember:

```text
Keys   → unique
Values → can repeat
```

---

## Doubt 6: What happens when I call `put()` with an existing key?

The old value is replaced.

```java
map.put(101, "Ravi");

map.put(101, "Basha");
```

Final:

```text
101 → Basha
```

This is one of the most important Map behaviors.

---

## Doubt 7: What is `get()`?

```java
map.get(101);
```

means:

> Find key `101` and return its associated value.

If:

```text
101 → Ravi
```

then:

```java
map.get(101)
```

returns:

```text
Ravi
```

---

## Doubt 8: What happens if the key doesn't exist?

For ordinary Map implementations such as HashMap:

```java
map.get(999);
```

returns:

```text
null
```

assuming there is no mapping for key `999`.

---

## Doubt 9: What is `containsKey()`?

```java
map.containsKey(101);
```

asks:

> Does key `101` exist?

It returns:

```text
true
```

or:

```text
false
```

---

## Doubt 10: What is `containsValue()`?

```java
map.containsValue("Ravi");
```

asks:

> Does the value `"Ravi"` exist?

Again:

```text
true / false
```

---

## Doubt 11: `keySet()` vs `values()` vs `entrySet()`

Suppose:

```text
101 → Ravi
102 → Kiran
103 → Basha
```

### `keySet()`

```java
map.keySet();
```

gives:

```text
101, 102, 103
```

### `values()`

```java
map.values();
```

gives:

```text
Ravi, Kiran, Basha
```

### `entrySet()`

```java
map.entrySet();
```

gives mappings:

```text
101=Ravi
102=Kiran
103=Basha
```

Remember:

```text
keySet()   → keys
values()   → values
entrySet() → key + value
```

---

# 2. HashMap — DOUBTKILLER

## Doubt 1: Is HashMap a class or interface?

**Class.**

```java
HashMap map = new HashMap();
```

It implements `Map`.

---

## Doubt 2: Does HashMap maintain insertion order?

**No guaranteed insertion order.**

If you insert:

```text
103
101
102
```

do not write your program assuming the output will be:

```text
103
101
102
```

The iteration order is not guaranteed by the HashMap contract.

---

## Doubt 3: Does HashMap sort its keys?

**No.**

HashMap is not a sorted Map.

If you need sorted keys:

```text
TreeMap
```

---

## Doubt 4: Does HashMap allow duplicate keys?

**No.**

```java
map.put(101, "Ravi");
map.put(101, "Kiran");
```

Result:

```text
101 → Kiran
```

---

## Doubt 5: Does HashMap allow duplicate values?

**Yes.**

```java
map.put(101, "Ravi");
map.put(102, "Ravi");
```

Valid.

---

## Doubt 6: Does HashMap allow null?

Yes.

A HashMap can have:

```text
one null key
multiple null values
```

Example:

```java
map.put(null, "Ravi");

map.put(101, null);
map.put(102, null);
```

---

## Doubt 7: Why only one null key?

Because a Map cannot have duplicate keys.

`null` is itself being used as a key.

So:

```java
map.put(null, "Ravi");
map.put(null, "Kiran");
```

doesn't create two null keys.

The second value replaces the first:

```text
null → Kiran
```

---

## Doubt 8: Is HashMap synchronized?

**No.**

HashMap is not synchronized.

If multiple threads need coordinated concurrent access, you need an appropriate concurrency mechanism such as `ConcurrentHashMap`, depending on the requirement.

---

## HashMap Memory

```text
HashMap
│
├── Class
├── Key-value pairs
├── Duplicate keys ❌
├── Duplicate values ✅
├── One null key ✅
├── Multiple null values ✅
├── Guaranteed insertion order ❌
├── Sorted keys ❌
└── Synchronized ❌
```

---

# 3. LinkedHashMap — DOUBTKILLER

## Doubt 1: Why do we need LinkedHashMap if HashMap already exists?

Because LinkedHashMap maintains **insertion order** during iteration.

Suppose:

```java
map.put(103, "Basha");
map.put(101, "Ravi");
map.put(102, "Kiran");
```

LinkedHashMap preserves:

```text
103
101
102
```

when iterating.

---

## Doubt 2: Does LinkedHashMap sort the keys?

**No.**

This is a common mistake.

Insertion order:

```text
103
101
102
```

remains:

```text
103
101
102
```

It does not automatically change that into:

```text
101
102
103
```

For sorted keys, use:

```text
TreeMap
```

---

## Doubt 3: Does LinkedHashMap allow duplicate keys?

**No.**

Same Map rule:

```java
map.put(101, "Ravi");
map.put(101, "Kiran");
```

Final:

```text
101 → Kiran
```

---

## Doubt 4: Does LinkedHashMap allow null?

Yes.

It permits a null key and null values.

---

## Doubt 5: Why is it called "Linked"?

Internally, LinkedHashMap maintains additional linked relationships between entries so that it can preserve a defined iteration order.

For your beginner understanding:

```text
HashMap
   ↓
hash-based storage

LinkedHashMap
   ↓
hash-based storage
+
linked ordering information
```

---

## LinkedHashMap Memory

```text
LinkedHashMap
│
├── Class
├── Key-value pairs
├── Duplicate keys ❌
├── Duplicate values ✅
├── Null key ✅
├── Null values ✅
├── Insertion order ✅
└── Sorted keys ❌
```

---

# 4. TreeMap — DOUBTKILLER

## Doubt 1: Why do we use TreeMap?

When we want **sorted keys**.

Example:

```text
Insert:

103 → Basha
101 → Ravi
102 → Kiran
```

TreeMap gives keys in sorted order:

```text
101 → Ravi
102 → Kiran
103 → Basha
```

---

## Doubt 2: Does TreeMap sort values?

**No.**

TreeMap sorts the **keys**.

This is extremely important.

```text
TreeMap
   ↓
Sorts KEYS
```

not:

```text
TreeMap
   ↓
Sorts VALUES
```

---

## Doubt 3: Does TreeMap preserve insertion order?

**No.**

It follows key ordering instead.

---

## Doubt 4: Can TreeMap have duplicate keys?

**No.**

```java
map.put(101, "Ravi");
map.put(101, "Kiran");
```

Final:

```text
101 → Kiran
```

---

## Doubt 5: Can TreeMap have duplicate values?

**Yes.**

```java
map.put(101, "Ravi");
map.put(102, "Ravi");
```

Valid.

---

## Doubt 6: Does TreeMap allow null key?

A normal natural-order TreeMap does **not** allow a null key because it needs to compare keys to maintain ordering.

```java
map.put(null, "Ravi");
```

results in an exception.

---

## Doubt 7: Does TreeMap allow null values?

Yes.

```java
map.put(101, null);
```

is allowed.

---

## TreeMap Memory

```text
TreeMap
│
├── Class
├── Key-value pairs
├── Duplicate keys ❌
├── Duplicate values ✅
├── Null key ❌
├── Null values ✅
├── Insertion order ❌
└── Sorted keys ✅
```

---

# 5. Hashtable — DOUBTKILLER

## Doubt 1: Is Hashtable modern?

It is a **legacy** Map class.

You should understand it because it is part of Java's Collections Framework and commonly appears in comparisons and older code.

---

## Doubt 2: Is Hashtable synchronized?

**Yes.**

Its traditional methods are synchronized.

But don't conclude:

> Hashtable is always the best choice for multithreaded applications.

For modern concurrent programming, `ConcurrentHashMap` is generally the more appropriate collection when its semantics fit the requirement.

---

## Doubt 3: Does Hashtable allow null key?

**No.**

```java
table.put(null, "Ravi");
```

causes an exception.

---

## Doubt 4: Does Hashtable allow null value?

**No.**

```java
table.put(101, null);
```

also causes an exception.

---

## Doubt 5: Does Hashtable allow duplicate keys?

**No.**

Same basic Map rule.

```java
table.put(101, "Ravi");
table.put(101, "Kiran");
```

Final:

```text
101 → Kiran
```

---

## Doubt 6: Hashtable vs HashMap

| Feature            | HashMap | Hashtable  |
| ------------------ | ------- | ---------- |
| Modern general use | Yes     | Usually no |
| Synchronized       | No      | Yes        |
| Null key           | Yes     | No         |
| Null value         | Yes     | No         |
| Duplicate keys     | No      | No         |
| Duplicate values   | Yes     | Yes        |

---

## Hashtable Memory

```text
Hashtable
│
├── Legacy class
├── Synchronized
├── Duplicate keys ❌
├── Duplicate values ✅
├── Null key ❌
└── Null value ❌
```

---

# 6. ConcurrentHashMap — DOUBTKILLER

## Doubt 1: Why do we need ConcurrentHashMap?

Because multiple threads may need to work with the same Map.

Conceptually:

```text
Thread 1 ──┐
Thread 2 ──┼──→ Same Map
Thread 3 ──┘
```

`ConcurrentHashMap` is designed specifically for concurrent use.

---

## Doubt 2: Is ConcurrentHashMap the same as synchronized Hashtable?

**No.**

This is a major interview doubt.

```text
Hashtable
    ↓
Legacy synchronized Map

ConcurrentHashMap
    ↓
Modern concurrent Map
```

They have different implementations and concurrency characteristics.

---

## Doubt 3: Does ConcurrentHashMap allow null?

**No.**

```text
Null key   ❌
Null value ❌
```

For example:

```java
map.put(null, "Ravi");
```

is invalid.

---

## Doubt 4: Does ConcurrentHashMap allow duplicate keys?

**No.**

It still follows Map semantics.

```java
map.put(101, "Ravi");
map.put(101, "Kiran");
```

Final mapping:

```text
101 → Kiran
```

---

## Doubt 5: Why can't ConcurrentHashMap simply allow null?

One important reason is that concurrent Map operations use `null` to represent absence in APIs such as `get()`. Allowing null would create ambiguity between:

```text
key exists → value is null
```

and:

```text
key does not exist
```

ConcurrentHashMap therefore prohibits null keys and values.

---

## ConcurrentHashMap Memory

```text
ConcurrentHashMap
│
├── Modern concurrent Map
├── Designed for concurrent access
├── Duplicate keys ❌
├── Duplicate values ✅
├── Null key ❌
└── Null value ❌
```

---

# 7. WeakHashMap — DOUBTKILLER

## Doubt 1: Why is WeakHashMap different from HashMap?

Because of the way it treats **keys**.

HashMap:

```text
Map strongly retains its keys.
```

WeakHashMap:

```text
Keys are held weakly.
```

---

## Doubt 2: What does "weak key" actually mean?

Suppose:

```java
String key = new String("Java");
```

Then:

```java
WeakHashMap map = new WeakHashMap();

map.put(key, "Programming");
```

If `key` later becomes unreachable from the rest of the program:

```java
key = null;
```

the key may become eligible for garbage collection.

If garbage collection occurs, the corresponding entry can be removed from the WeakHashMap.

---

## Doubt 3: Does `key = null` immediately remove the entry?

**No.**

This is one of the biggest WeakHashMap misunderstandings.

```java
key = null;
```

means:

> This particular strong reference has been removed.

It does **not** mean:

> Delete the WeakHashMap entry immediately.

The key must become otherwise unreachable and then garbage collection must occur.

---

## Doubt 4: Does `System.gc()` guarantee removal?

**No.**

```java
System.gc();
```

is only a request/hint to the JVM.

The JVM decides when garbage collection actually happens.

Therefore this:

```java
key = null;
System.gc();
```

does not guarantee that the entry disappears immediately.

---

## Doubt 5: What exactly is weak?

The **keys**.

Remember:

```text
WeakHashMap
     ↓
Weak keys
```

Don't memorize:

```text
WeakHashMap → weak values
```

That is wrong.

---

## Doubt 6: Can WeakHashMap have duplicate keys?

**No.**

It is still a Map.

---

## Doubt 7: Can WeakHashMap have duplicate values?

**Yes.**

Like other Maps:

```java
map.put(key1, "Java");
map.put(key2, "Java");
```

can have the same value.

---

## WeakHashMap Memory

```text
WeakHashMap
│
├── Map
├── Duplicate keys ❌
├── Duplicate values ✅
├── Keys are weakly referenced
├── Unreachable keys may be garbage collected
└── Corresponding entries may disappear
```

---

# 8. Biggest Map Confusions — KILL THEM ALL

## Confusion 1

### "Map is a Collection."

❌ Wrong.

```text
Map
```

is separate from:

```text
Collection
```

---

## Confusion 2

### "HashMap maintains insertion order."

❌ Wrong.

HashMap has **no guaranteed insertion order**.

---

## Confusion 3

### "LinkedHashMap sorts the keys."

❌ Wrong.

LinkedHashMap maintains **insertion order**.

---

## Confusion 4

### "TreeMap maintains insertion order."

❌ Wrong.

TreeMap maintains **sorted key order**.

---

## Confusion 5

### "TreeMap sorts values."

❌ Wrong.

TreeMap sorts **keys**.

---

## Confusion 6

### "All Maps allow null."

❌ Wrong.

For the implementations in your roadmap:

```text
HashMap           → null key/value allowed
LinkedHashMap     → null key/value allowed
TreeMap           → null key not allowed in natural ordering
Hashtable         → null key/value not allowed
ConcurrentHashMap → null key/value not allowed
```

---

## Confusion 7

### "Duplicate key means two values are stored."

❌ Wrong.

```java
map.put(101, "Ravi");
map.put(101, "Kiran");
```

means:

```text
101 → Kiran
```

---

## Confusion 8

### "Duplicate values are prohibited."

❌ Wrong.

```text
101 → Ravi
102 → Ravi
```

is valid.

---

## Confusion 9

### "Hashtable and ConcurrentHashMap are the same."

❌ Wrong.

```text
Hashtable
→ legacy synchronized Map

ConcurrentHashMap
→ concurrent Map designed for modern concurrent access
```

---

## Confusion 10

### "WeakHashMap immediately deletes an entry when the key variable becomes null."

❌ Wrong.

The key must become otherwise unreachable and become eligible for garbage collection, and actual garbage collection is not immediate or guaranteed.

---

# 9. Master Doubt Table

| Question                                         | Correct Answer |
| ------------------------------------------------ | -------------- |
| Is Map a Collection?                             | ❌ No           |
| Does Map store key-value pairs?                  | ✅ Yes          |
| Can duplicate keys exist?                        | ❌ No           |
| Can duplicate values exist?                      | ✅ Yes          |
| Does HashMap guarantee insertion order?          | ❌ No           |
| Does LinkedHashMap maintain insertion order?     | ✅ Yes          |
| Does LinkedHashMap sort keys?                    | ❌ No           |
| Does TreeMap sort keys?                          | ✅ Yes          |
| Does TreeMap sort values?                        | ❌ No           |
| Does HashMap allow null key?                     | ✅ Yes          |
| Does LinkedHashMap allow null key?               | ✅ Yes          |
| Does normal TreeMap allow null key?              | ❌ No           |
| Does Hashtable allow null key?                   | ❌ No           |
| Does Hashtable allow null value?                 | ❌ No           |
| Does ConcurrentHashMap allow null key/value?     | ❌ No           |
| Is Hashtable legacy?                             | ✅ Yes          |
| Is Hashtable synchronized?                       | ✅ Yes          |
| Is ConcurrentHashMap designed for concurrency?   | ✅ Yes          |
| Does WeakHashMap use weak keys?                  | ✅ Yes          |
| Does `System.gc()` guarantee garbage collection? | ❌ No           |

---

# 10. Final "Which Map Should I Choose?" Doubt

If you are asked:

### "I don't care about order."

```text
HashMap
```

### "I need insertion order."

```text
LinkedHashMap
```

### "I need sorted keys."

```text
TreeMap
```

### "I'm working with old synchronized Map code."

```text
Hashtable
```

### "Multiple threads need concurrent Map access."

```text
ConcurrentHashMap
```

### "I specifically need keys to be weakly referenced."

```text
WeakHashMap
```

---

# 11. The Six-Word Memory Trick

Remember these six words:

```text
HashMap          → GENERAL
LinkedHashMap    → INSERTION
TreeMap          → SORTED
Hashtable        → LEGACY
ConcurrentHashMap→ CONCURRENT
WeakHashMap      → WEAK-KEY
```

That's the **core of Map**.

And again, **Generics are completely excluded from this Topic 8 material**. They will be introduced only when you reach **Topic 14 — Generics**.
