# 15. Advanced Collections in Java — 3LEVEL

The **3LEVEL method** means every concept is understood at three depths:

* 🟢 **LEVEL 1 — Basic:** What is it?
* 🟡 **LEVEL 2 — Working:** How does it work and when do we use it?
* 🔴 **LEVEL 3 — Deep Understanding:** Important rules, examples, differences, and common traps.

---

# 1. Concurrent Collections

## 🟢 LEVEL 1 — Basic

A **concurrent collection** is a collection designed to be safely and efficiently used by **multiple threads**.

Examples:

```text
ConcurrentHashMap
CopyOnWriteArrayList
ConcurrentLinkedQueue
BlockingQueue
```

Most are in:

```java
java.util.concurrent
```

The main purpose is:

> Allow multiple threads to work with shared collection data without requiring you to manually put one giant lock around every operation.

---

## 🟡 LEVEL 2 — Working

Consider:

```java
Map<Integer, String> map =
    new HashMap<>();
```

`HashMap` is not thread-safe.

If multiple threads simultaneously modify the same `HashMap`, the program needs appropriate external synchronization.

Instead:

```java
ConcurrentHashMap<Integer, String> map =
    new ConcurrentHashMap<>();
```

Now the collection is specifically designed for concurrent access.

For example:

```java
map.put(1, "Java");
map.put(2, "Python");
```

Concurrent collections provide concurrency mechanisms appropriate to their specific design.

---

## 🔴 LEVEL 3 — Deep Understanding

### Important examples

| Collection              | Main use                           |
| ----------------------- | ---------------------------------- |
| `ConcurrentHashMap`     | Concurrent key-value data          |
| `CopyOnWriteArrayList`  | Many reads, few writes             |
| `ConcurrentLinkedQueue` | Concurrent non-blocking FIFO queue |
| `BlockingQueue`         | Producer-consumer communication    |

### `ConcurrentHashMap`

```java
ConcurrentHashMap<Integer, String> map =
    new ConcurrentHashMap<>();
```

Useful methods include:

```java
putIfAbsent()
compute()
computeIfAbsent()
computeIfPresent()
merge()
replace()
```

For example:

```java
map.putIfAbsent(1, "Java");
```

means:

> Insert the mapping only if the key doesn't already have a mapping.

### Important rule

`ConcurrentHashMap` does **not** permit:

```java
map.put(null, "Java"); // ❌
map.put(1, null);      // ❌
```

---

### `CopyOnWriteArrayList`

```java
CopyOnWriteArrayList<String> list =
    new CopyOnWriteArrayList<>();
```

When a write occurs, a new underlying array is created.

Therefore it is especially suitable for:

```text
READ READ READ READ READ READ
                    ↓
                   WRITE
```

Not:

```text
WRITE WRITE WRITE WRITE WRITE
```

because frequent writes can be expensive.

---

### `BlockingQueue`

Useful for:

```text
Producer
   ↓
BlockingQueue
   ↓
Consumer
```

Example:

```java
BlockingQueue<String> queue =
    new ArrayBlockingQueue<>(10);
```

Producer:

```java
queue.put("Task");
```

Consumer:

```java
String task = queue.take();
```

`put()` and `take()` can wait when the queue is full or empty, respectively.

### 🔥 Remember

```text
ConcurrentHashMap → concurrent Map
CopyOnWriteArrayList → read-heavy List
BlockingQueue → producer/consumer
```

---

# 2. Fail-Fast

## 🟢 LEVEL 1 — Basic

**Fail-fast** means an iterator may quickly detect an unexpected structural modification of the collection and throw:

```java
ConcurrentModificationException
```

Example:

```java
List<Integer> list =
    new ArrayList<>();

list.add(10);
list.add(20);
list.add(30);

for (Integer x : list) {
    if (x == 20) {
        list.remove(x);
    }
}
```

This can produce:

```text
ConcurrentModificationException
```

---

## 🟡 LEVEL 2 — Working

Why?

The iterator expects the collection to remain structurally consistent while it is iterating.

But this happens:

```text
Iterator
   ↓
10 → 20 → 30

Underlying collection changes
   ↓
10 → 30
```

The iterator detects that its expected state no longer matches the collection.

It may then fail.

---

## 🔴 LEVEL 3 — Deep Understanding

### Fail-fast does NOT mean multithreading is required.

This can happen with only one thread:

```java
for (Integer x : list) {
    list.remove(x);
}
```

Therefore:

```text
ConcurrentModificationException
        ≠
"Another thread definitely modified the collection."
```

It can occur in a single-threaded program.

---

### Correct removal using Iterator

Instead of:

```java
list.remove(x);
```

use:

```java
Iterator<Integer> it =
    list.iterator();

while (it.hasNext()) {

    Integer x = it.next();

    if (x == 20) {
        it.remove();
    }
}
```

The iterator knows about its own removal operation.

---

### Important trap

Fail-fast behavior is generally **best effort**.

Don't design a program around:

> "The iterator is guaranteed to throw `ConcurrentModificationException`."

The purpose is mainly to expose programming errors early.

### 🔥 Remember

```text
Fail-fast
   ↓
Unexpected structural modification
   ↓
Iterator may detect it
   ↓
ConcurrentModificationException
```

---

# 3. Fail-Safe / Weakly Consistent Iteration

## 🟢 LEVEL 1 — Basic

You will often hear:

> "Concurrent collections have fail-safe iterators."

This is common classroom terminology.

More precise Java terminology depends on the collection. Some iterators are **weakly consistent**, while others use a **snapshot**.

They generally allow iteration to continue while concurrent modifications happen.

---

## 🟡 LEVEL 2 — Working

Consider:

```java
ConcurrentHashMap<Integer, String> map =
    new ConcurrentHashMap<>();
```

Suppose one thread iterates:

```text
Thread A
   ↓
iterator
```

while another thread performs:

```text
Thread B
   ↓
map.put(...)
```

The iterator does not normally throw `ConcurrentModificationException` merely because of that concurrent modification.

It can continue.

---

## 🔴 LEVEL 3 — Deep Understanding

### Weakly consistent iterator

A weakly consistent iterator generally:

* doesn't fail merely because another thread modifies the collection
* can continue while modification occurs
* may reflect some modifications
* may not reflect all modifications
* does not represent a fixed snapshot

Example:

```text
Initial:

1 → Java
2 → Python
3 → C++
```

Iterator starts.

Another thread adds:

```text
4 → Go
```

The iterator may or may not reflect that newly added entry according to the collection's iteration semantics.

Don't assume:

```text
"must see 4"
```

or:

```text
"must not see 4"
```

---

## Snapshot iteration

`CopyOnWriteArrayList` provides snapshot-style iteration.

Conceptually:

```text
Iterator created:

[10][20][30]
```

Then:

```java
list.add(40);
```

Current list:

```text
[10][20][30][40]
```

Existing iterator conceptually continues over:

```text
[10][20][30]
```

---

## 🔥 Three iteration models

```text
FAIL-FAST
   ↓
Unexpected modification
   ↓
may throw exception


WEAKLY CONSISTENT
   ↓
Concurrent modification allowed
   ↓
may observe some changes


SNAPSHOT
   ↓
Iterator sees a fixed version
   ↓
later modifications don't affect that iterator
```

### Very important

"Fail-safe" is useful as a teaching term, but when discussing Java precisely, prefer:

```text
Fail-fast
Weakly consistent
Snapshot
```

depending on the actual collection.

---

# 4. Synchronization

## 🟢 LEVEL 1 — Basic

**Synchronization** is a mechanism used to coordinate multiple threads accessing shared mutable data.

Its goal is to prevent problems such as:

```text
Race conditions
```

Example:

```java
int count = 0;
```

Two threads execute:

```java
count++;
```

at the same time.

The result may not be what we expect.

---

## 🟡 LEVEL 2 — Working

Java provides synchronized collection wrappers.

For example:

```java
List<Integer> list =
    Collections.synchronizedList(
        new ArrayList<>()
    );
```

Similarly:

```java
Collections.synchronizedSet(...)
Collections.synchronizedMap(...)
```

These wrap an ordinary collection with synchronization.

---

## 🔴 LEVEL 3 — Deep Understanding

### Synchronized collection

Conceptually:

```text
ArrayList
   +
synchronization wrapper
   =
synchronizedList
```

Example:

```java
List<Integer> list =
    Collections.synchronizedList(
        new ArrayList<>()
    );
```

But there is an important trap.

### Iteration requires external synchronization

Use:

```java
synchronized (list) {

    for (Integer x : list) {
        System.out.println(x);
    }
}
```

Why?

Because iteration involves multiple operations:

```text
hasNext()
next()
hasNext()
next()
...
```

The whole iteration needs appropriate coordination.

---

## Synchronized vs concurrent

These are not identical.

### Synchronized wrapper

```text
Existing collection
      +
synchronization
```

Example:

```java
Collections.synchronizedList(...)
```

### Concurrent collection

```text
Collection designed
specifically for concurrent access
```

Examples:

```java
ConcurrentHashMap
CopyOnWriteArrayList
ConcurrentLinkedQueue
```

### 🔥 Remember

```text
Synchronized ≠ Concurrent collection

Synchronized → synchronization wrapper/mechanism

Concurrent → collection designed around concurrency
```

---

# 5. Immutable / Unmodifiable Collections

## 🟢 LEVEL 1 — Basic

An **unmodifiable collection** does not allow structural modification through that collection reference.

Example:

```java
List<String> list =
    List.of("Java", "Python", "C++");
```

This fails:

```java
list.add("Go"); // ❌
```

with:

```text
UnsupportedOperationException
```

---

## 🟡 LEVEL 2 — Working

Java provides:

```java
List.of(...)
Set.of(...)
Map.of(...)
```

These create unmodifiable collections.

There are also wrapper methods:

```java
Collections.unmodifiableList(...)
Collections.unmodifiableSet(...)
Collections.unmodifiableMap(...)
```

---

## 🔴 LEVEL 3 — Deep Understanding

The biggest distinction is:

```text
Immutable collection
        vs
Unmodifiable view
```

Consider:

```java
List<String> original =
    new ArrayList<>();

original.add("Java");

List<String> view =
    Collections.unmodifiableList(original);
```

This is illegal:

```java
view.add("Python"); // ❌
```

But this is allowed:

```java
original.add("Python"); // ✅
```

The view can therefore change because the underlying collection changed.

So:

```text
unmodifiable view
        ≠
independent immutable copy
```

---

## `List.of()`

```java
List<String> list =
    List.of("Java", "Python");
```

The list itself cannot be structurally modified.

---

## Mutable elements

An unmodifiable collection doesn't automatically make its elements immutable.

For example:

```java
class Student {
    String name;

    Student(String name) {
        this.name = name;
    }
}
```

Then:

```java
Student s =
    new Student("Java");

List<Student> list =
    List.of(s);
```

This is prohibited:

```java
list.add(new Student("Python")); // ❌
```

But:

```java
s.name = "Changed"; // potentially ✅
```

So:

```text
Immutable collection
        ≠
Immutable objects inside it
```

---

# 6. Collection Performance

## 🟢 LEVEL 1 — Basic

Different collections are optimized for different operations.

For example:

```text
ArrayList → fast index access
HashSet   → fast average membership lookup
HashMap   → fast average key lookup
TreeSet   → sorted unique elements
TreeMap   → sorted keys
```

Therefore:

> There is no single "fastest collection."

---

## 🟡 LEVEL 2 — Working

Important typical complexities:

| Collection   | Operation             | Typical complexity |
| ------------ | --------------------- | -----------------: |
| `ArrayList`  | `get(index)`          |               O(1) |
| `ArrayList`  | add at end            |     Amortized O(1) |
| `ArrayList`  | middle insertion      |               O(n) |
| `ArrayList`  | middle removal        |               O(n) |
| `LinkedList` | `get(index)`          |               O(n) |
| `LinkedList` | end insertion/removal |               O(1) |
| `HashSet`    | add/contains/remove   |       Average O(1) |
| `TreeSet`    | add/contains/remove   |           O(log n) |
| `HashMap`    | put/get/remove        |       Average O(1) |
| `TreeMap`    | put/get/remove        |           O(log n) |

---

## 🔴 LEVEL 3 — Deep Understanding

### ArrayList

Because it is backed by a resizable array:

```java
list.get(500);
```

can directly access the index.

Therefore:

```text
get(index) → O(1)
```

Adding at the end is typically:

```text
amortized O(1)
```

But inserting in the middle requires shifting elements:

```text
[A][B][C][D]

Insert X at index 1:

[A][X][B][C][D]
```

Therefore:

```text
middle insertion → O(n)
```

---

### LinkedList

Conceptually:

```text
A ↔ B ↔ C ↔ D
```

Finding index 1000 requires traversal:

```text
A → B → C → ... → element 1000
```

Therefore:

```text
get(index) → O(n)
```

If the correct node/position is already known, linking/unlinking nodes can be O(1).

This is why the simplistic statement:

```text
"LinkedList insertion is O(1)"
```

is incomplete.

---

### HashSet

Typical average:

```text
add()      → O(1)
contains() → O(1)
remove()   → O(1)
```

Excellent for:

> "Does this value already exist?"

---

### TreeSet

Maintains sorted order:

```text
10
20
30
40
```

Typical:

```text
add()      → O(log n)
contains() → O(log n)
remove()   → O(log n)
```

You pay extra compared with a hash-based set because you also get ordering.

---

### HashMap

Typical average:

```text
put()         → O(1)
get()         → O(1)
remove()      → O(1)
containsKey() → O(1)
```

---

### TreeMap

Keys remain sorted.

Typical:

```text
put()    → O(log n)
get()    → O(log n)
remove() → O(log n)
```

---

# 7. Choosing the Correct Collection

## 🟢 LEVEL 1 — Basic

Don't start with:

> "Which collection is best?"

Start with:

> **"What requirement do I have?"**

Ask:

```text
1. Do I need duplicates?
2. Do I need ordering?
3. Do I need sorting?
4. Do I need key-value storage?
5. Do I need fast lookup?
6. Do I need queue behavior?
7. Do multiple threads access it?
8. Can the data change?
```

---

# 🟡 LEVEL 2 — Working

## Need duplicates?

Use:

```text
List
```

Usually:

```java
ArrayList
```

---

## Need unique elements?

Use:

```text
Set
```

Then:

```text
HashSet
```

for general-purpose uniqueness.

---

## Need unique + insertion order?

```java
LinkedHashSet
```

---

## Need unique + sorted?

```java
TreeSet
```

---

## Need key-value?

```java
HashMap
```

---

## Need key-value + insertion/encounter order?

```java
LinkedHashMap
```

---

## Need key-value + sorted keys?

```java
TreeMap
```

---

## Need concurrent key-value storage?

```java
ConcurrentHashMap
```

---

## Need FIFO?

```java
Queue
```

A common implementation choice:

```java
ArrayDeque
```

---

## Need both ends?

```java
Deque
```

Often:

```java
ArrayDeque
```

---

## Need priority?

```java
PriorityQueue
```

---

## Need producer-consumer coordination?

```java
BlockingQueue
```

---

# 🔴 LEVEL 3 — Deep Understanding

Here's the complete decision process.

## Step 1 — What is the data model?

### Sequence

```text
List
```

### Unique values

```text
Set
```

### Key → value

```text
Map
```

### Processing order

```text
Queue / Deque
```

---

## Step 2 — Do you need ordering?

### No guaranteed ordering requirement

```text
HashSet
HashMap
```

### Need insertion/encounter order

```text
LinkedHashSet
LinkedHashMap
```

### Need sorted order

```text
TreeSet
TreeMap
```

---

## Step 3 — Do you need concurrency?

If multiple threads access shared mutable collection state, consider:

```text
ConcurrentHashMap
CopyOnWriteArrayList
ConcurrentLinkedQueue
BlockingQueue
```

depending on the workload.

---

## Step 4 — What operation dominates?

If you frequently need:

```java
list.get(index);
```

prefer:

```text
ArrayList
```

If you frequently need:

```java
set.contains(value);
```

consider:

```text
HashSet
```

If you frequently need:

```java
map.get(key);
```

consider:

```text
HashMap
```

If you need sorted data:

```text
TreeSet / TreeMap
```

---

# 8. Complete Decision Chart

```text
                    WHAT DO I NEED?
                           │
          ┌────────────────┼─────────────────┐
          │                │                 │
        List              Set               Map
          │                │                 │
     ┌────┴────┐      ┌────┼────┐       ┌────┼────┐
     │         │      │    │    │       │    │    │
  ArrayList LinkedList Hash Linked Tree Hash Linked Tree
                       Set  HashSet? Set Map  HashMap Map
                            │
                       LinkedHashSet
```

A cleaner practical version:

```text
LIST
 ├── General → ArrayList
 └── Specialized linked/deque use → LinkedList

SET
 ├── Fast general uniqueness → HashSet
 ├── Insertion order → LinkedHashSet
 └── Sorted → TreeSet

MAP
 ├── Fast general lookup → HashMap
 ├── Encounter order → LinkedHashMap
 ├── Sorted keys → TreeMap
 └── Concurrent access → ConcurrentHashMap

QUEUE
 ├── General FIFO → ArrayDeque
 ├── Priority → PriorityQueue
 └── Producer/consumer → BlockingQueue
```

---

# 9. 🔥 Most Important Confusions

## Confusion 1

### Fail-fast = multithreading?

❌ No.

It can happen in one thread.

```text
Fail-fast
→ unexpected structural modification during iteration
```

---

## Confusion 2

### Fail-safe = official Java interface?

❌ Not exactly.

Use precise terms such as:

```text
weakly consistent
snapshot
```

when describing the actual iterator behavior.

---

## Confusion 3

### ConcurrentHashMap = synchronized HashMap?

❌ No.

They use different designs and concurrency characteristics.

---

## Confusion 4

### `LinkedList` is always faster for insertion?

❌ No.

Finding the insertion position can cost O(n).

---

## Confusion 5

### TreeSet is faster than HashSet?

❌ Generally not for basic operations.

```text
HashSet → average O(1)
TreeSet → O(log n)
```

TreeSet gives sorted order.

---

## Confusion 6

### Unmodifiable = immutable copy?

❌ No.

```java
Collections.unmodifiableList(original)
```

is a view.

The original collection can still change.

---

## Confusion 7

### Immutable collection means contained objects are immutable?

❌ No.

The collection structure and the objects stored inside it are separate concepts.

---

# 10. 🧠 Final 3-Level Memory Map

```text
ADVANCED COLLECTIONS
│
├── Concurrent Collections
│   ├── ConcurrentHashMap
│   ├── CopyOnWriteArrayList
│   ├── ConcurrentLinkedQueue
│   └── BlockingQueue
│
├── Fail-Fast
│   ├── Unexpected modification
│   ├── Iterator detects it
│   └── May throw ConcurrentModificationException
│
├── Weakly Consistent / Snapshot
│   ├── Concurrent iteration
│   ├── Weakly consistent → may see changes
│   └── Snapshot → fixed iterator view
│
├── Synchronization
│   ├── Coordinates threads
│   ├── synchronizedList/Set/Map
│   └── Iteration needs appropriate external synchronization
│
├── Immutable / Unmodifiable
│   ├── List.of / Set.of / Map.of
│   ├── unmodifiable wrappers
│   └── Unmodifiable view ≠ immutable copy
│
├── Performance
│   ├── ArrayList → O(1) index
│   ├── HashSet → average O(1)
│   ├── HashMap → average O(1)
│   ├── TreeSet → O(log n)
│   └── TreeMap → O(log n)
│
└── Choosing
    ├── List → duplicates/sequence
    ├── Set → uniqueness
    ├── Map → key/value
    ├── Queue → processing order
    ├── Deque → both ends
    └── Concurrent collection → shared concurrent access
```

## 🎯 The ultimate rule

```text
REQUIREMENT
    ↓
Duplicates?
    ↓
Ordering?
    ↓
Sorting?
    ↓
Lookup pattern?
    ↓
Concurrency?
    ↓
Mutability?
    ↓
CHOOSE COLLECTION
```

**Don't memorize "best collection." Learn the requirement → characteristics → collection relationship.** That is the real purpose of Advanced Collections.
