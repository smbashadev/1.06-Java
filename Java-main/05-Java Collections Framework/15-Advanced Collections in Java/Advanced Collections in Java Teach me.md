# 15. Advanced Collections in Java — TEACHME

Let's learn this as if we're building the idea from **zero**, rather than memorizing collection names.

The seven things we need to understand are:

```text
1. Concurrent Collections
2. Fail-Fast
3. Fail-Safe / Weakly Consistent Iteration
4. Synchronization
5. Immutable / Unmodifiable Collections
6. Collection Performance
7. Choosing the Correct Collection
```

---

# 1. Concurrent Collections

## First: What problem are we solving?

Imagine we have a collection:

```java
List<Integer> list = new ArrayList<>();
```

Now suppose two threads are using it:

```text
Thread 1 ───────┐
                ↓
             ArrayList
                ↑
Thread 2 ───────┘
```

Both threads may try to read or modify the collection at the same time.

An ordinary `ArrayList`, `HashMap`, `HashSet`, etc. is **not automatically safe for concurrent modification by multiple threads**.

So Java provides collections specifically designed for concurrent programming.

These are called **concurrent collections**.

---

## 1.1 Important concurrent collections

Some important examples are:

```text
ConcurrentHashMap
CopyOnWriteArrayList
CopyOnWriteArraySet
ConcurrentLinkedQueue
ConcurrentLinkedDeque
BlockingQueue
```

They are primarily found in:

```java
java.util.concurrent
```

---

# 1.2 ConcurrentHashMap

Let's start with the most important one.

```java
ConcurrentHashMap<Integer, String> map =
        new ConcurrentHashMap<>();
```

It stores:

```text
key → value
```

just like `HashMap`.

For example:

```java
map.put(1, "Java");
map.put(2, "Python");
map.put(3, "C++");
```

The important difference is that `ConcurrentHashMap` is designed for **concurrent access by multiple threads**.

---

## 1.3 HashMap vs ConcurrentHashMap

Think of them this way:

```text
HashMap
   ↓
ordinary map
   ↓
not thread-safe
```

Whereas:

```text
ConcurrentHashMap
   ↓
concurrent map
   ↓
designed for multiple threads
```

So if several threads need to access and modify the same map, `ConcurrentHashMap` is often a suitable choice.

---

## 1.4 Does ConcurrentHashMap allow null?

No.

```java
map.put(null, "Java");  // ❌
map.put(1, null);       // ❌
```

Neither null keys nor null values are permitted.

---

## 1.5 A very important feature: atomic operations

Suppose we write:

```java
if (!map.containsKey(1)) {
    map.put(1, "Java");
}
```

There is a problem.

Imagine:

```text
Thread A → checks key 1
Thread B → checks key 1
Thread A → inserts
Thread B → inserts
```

The entire operation isn't atomic.

`ConcurrentHashMap` provides methods such as:

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

> Put this mapping only if the key doesn't already have a mapping.

That's particularly useful in concurrent programs.

---

# 1.6 CopyOnWriteArrayList

Now consider a different situation.

Suppose we have:

```text
10, 20, 30, 40
```

and:

```text
READ → READ → READ → READ → READ → WRITE
```

There are many more reads than writes.

For this type of workload, Java provides:

```java
CopyOnWriteArrayList<Integer> list =
        new CopyOnWriteArrayList<>();
```

---

## Why is it called Copy-On-Write?

Because when you modify the list, the underlying array is copied.

Conceptually:

```text
Before:

[10][20][30]
```

You execute:

```java
list.add(40);
```

Conceptually:

```text
Old array:
[10][20][30]

New array:
[10][20][30][40]
```

The implementation then uses the new array.

---

## When is CopyOnWriteArrayList useful?

When:

```text
READS >>> WRITES
```

For example:

* listener lists
* configuration-like data
* observer lists
* read-heavy shared collections

But if you constantly modify the list, copying can become expensive.

So don't think:

> "CopyOnWriteArrayList is always better because it is thread-safe."

It isn't.

---

# 1.7 BlockingQueue

Another important concurrent collection is:

```java
BlockingQueue<Integer> queue;
```

This is especially useful for a **producer-consumer problem**.

Imagine:

```text
Producer
   ↓
BlockingQueue
   ↓
Consumer
```

The producer puts work into the queue.

The consumer takes work from the queue.

For example:

```java
BlockingQueue<Integer> queue =
        new ArrayBlockingQueue<>(10);
```

Producer:

```java
queue.put(100);
```

Consumer:

```java
Integer value = queue.take();
```

If the queue is empty, `take()` can wait.

If the queue is full, `put()` can wait.

This is why it's called a **blocking** queue.

---

# 1.8 ConcurrentLinkedQueue

If you want a concurrent FIFO queue:

```java
ConcurrentLinkedQueue<Integer> queue =
        new ConcurrentLinkedQueue<>();
```

Then:

```java
queue.offer(10);
queue.offer(20);
queue.offer(30);
```

and:

```java
queue.poll();
```

removes the head.

---

# 🧠 Remember concurrent collections like this

```text
ConcurrentHashMap
        ↓
Concurrent key/value storage

CopyOnWriteArrayList
        ↓
Many reads + few writes

ConcurrentLinkedQueue
        ↓
Concurrent non-blocking queue

BlockingQueue
        ↓
Producer + Consumer
```

---

# 2. Fail-Fast

Now let's understand a completely different concept.

Suppose:

```java
List<Integer> list =
        new ArrayList<>();

list.add(10);
list.add(20);
list.add(30);
```

We start iterating:

```java
for (Integer x : list) {
    System.out.println(x);
}
```

Internally, the enhanced `for` loop uses an iterator.

---

## 2.1 The problem

What if we modify the collection while iterating?

```java
for (Integer x : list) {

    if (x == 20) {
        list.remove(x);
    }
}
```

This can result in:

```text
ConcurrentModificationException
```

This behavior is called **fail-fast**.

---

# 2.2 What does fail-fast mean?

Very simply:

> If an iterator detects an incompatible structural modification to its collection while iterating, it may fail quickly by throwing an exception.

The common exception is:

```java
ConcurrentModificationException
```

---

# 2.3 Why does Java do this?

Imagine the iterator thinks:

```text
10 → 20 → 30
```

Then suddenly the underlying collection changes.

The iterator's assumptions may no longer be valid.

Instead of silently continuing with potentially confusing behavior, the iterator may say:

```text
"Something changed that I wasn't expecting!"
```

and throw:

```text
ConcurrentModificationException
```

---

# 2.4 Is it only caused by multiple threads?

No!

This is a very common misunderstanding.

You can get it in a **single thread**.

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

Only one thread is involved.

Therefore:

```text
ConcurrentModificationException
            ≠
"another thread definitely modified it"
```

---

# 2.5 How can we safely remove while iterating?

Use the iterator's own `remove()` method.

```java
Iterator<Integer> iterator =
        list.iterator();

while (iterator.hasNext()) {

    Integer x = iterator.next();

    if (x == 20) {
        iterator.remove();
    }
}
```

Here the iterator itself performs the removal.

---

# 2.6 Is fail-fast guaranteed?

No.

This is very important.

Fail-fast behavior is generally **best effort**.

Don't write code that depends upon:

```text
"ConcurrentModificationException must always occur."
```

Its purpose is mainly to detect programming mistakes early.

---

# 2.7 Fail-fast does NOT mean thread-safe

Remember:

```text
Fail-fast
    ≠
Thread-safe
```

For example:

```java
ArrayList<Integer> list =
        new ArrayList<>();
```

An `ArrayList` iterator may be fail-fast, but `ArrayList` itself is not a thread-safe collection.

---

# 3. Fail-Safe / Weakly Consistent Iteration

Now let's look at the opposite idea.

Suppose multiple threads are using:

```java
ConcurrentHashMap<Integer, String>
```

and one thread is iterating while another thread modifies it.

Do we necessarily want:

```text
ConcurrentModificationException
```

every time?

No.

Concurrent collections are designed with different iteration semantics.

---

# 3.1 "Fail-safe" terminology

You'll frequently hear:

> "Concurrent collections provide fail-safe iterators."

This is common teaching terminology, but **"fail-safe" is not an official universal Java Collections Framework iterator category**.

More precise terminology includes:

```text
weakly consistent
snapshot
fail-fast
```

depending on the collection.

---

# 3.2 Weakly consistent iterator

A **weakly consistent iterator** generally:

* doesn't throw `ConcurrentModificationException` merely because another thread modifies the collection
* can continue while the collection changes
* may reflect some modifications
* may not reflect all modifications
* isn't necessarily a fixed snapshot

`ConcurrentHashMap` is the classic example.

---

# 3.3 Imagine this

Suppose:

```text
Map:

1 → Java
2 → Python
3 → C++
```

Thread A starts iterating.

At the same time:

```text
Thread B adds:

4 → Go
```

The iterator can continue.

But you shouldn't assume:

```text
"Thread A must see 4."
```

Nor should you assume:

```text
"Thread A must not see 4."
```

The iterator's semantics don't promise a fixed snapshot.

---

# 3.4 Weakly consistent ≠ snapshot

This distinction is extremely important.

### Weakly consistent

```text
collection changes
       ↓
iterator continues
       ↓
may see some changes
```

### Snapshot

```text
iterator created
       ↓
snapshot taken
       ↓
later modifications
       ↓
existing iterator still sees its snapshot
```

---

# 3.5 CopyOnWriteArrayList and snapshots

Consider:

```java
CopyOnWriteArrayList<Integer> list =
        new CopyOnWriteArrayList<>();

list.add(10);
list.add(20);
list.add(30);
```

Then:

```java
Iterator<Integer> iterator =
        list.iterator();
```

Conceptually the iterator has:

```text
[10, 20, 30]
```

Now:

```java
list.add(40);
```

The existing iterator works from its snapshot.

So:

```text
Current list:
10 20 30 40

Existing iterator:
10 20 30
```

This is one of the key ideas behind copy-on-write iteration.

---

# 3.6 Comparison

| Feature                           | Fail-Fast                                   | Weakly Consistent   | Snapshot                                    |
| --------------------------------- | ------------------------------------------- | ------------------- | ------------------------------------------- |
| Typical example                   | `ArrayList`                                 | `ConcurrentHashMap` | `CopyOnWriteArrayList`                      |
| Concurrent modification tolerated | Not as a supported iteration pattern        | Yes                 | Yes                                         |
| Fixed snapshot                    | No                                          | No                  | Yes                                         |
| May observe changes               | Not safely supported                        | May                 | Existing iterator doesn't see later changes |
| Typical exception                 | May throw `ConcurrentModificationException` | Generally no        | No                                          |

---

# 4. Synchronization

Now we need to understand **why synchronization exists**.

---

# 4.1 The shared-data problem

Imagine:

```java
int count = 0;
```

Two threads execute:

```java
count++;
```

We might think:

```text
count = count + 1
```

is one operation.

But conceptually it involves:

```text
1. Read count
2. Add 1
3. Write count
```

Suppose:

```text
Initial count = 0

Thread A reads 0
Thread B reads 0

Thread A writes 1
Thread B writes 1
```

Final result:

```text
1
```

when we expected:

```text
2
```

This is a race condition.

---

# 4.2 What does synchronization do?

Synchronization coordinates access to shared mutable state.

Conceptually:

```text
Thread A
   ↓
acquire lock
   ↓
perform operation
   ↓
release lock

Thread B
   ↓
waits
```

Then Thread B can enter.

---

# 4.3 Synchronized collection wrappers

Java provides:

```java
Collections.synchronizedList(...)
Collections.synchronizedSet(...)
Collections.synchronizedMap(...)
```

For example:

```java
List<Integer> list =
    Collections.synchronizedList(
        new ArrayList<>()
    );
```

Now individual access through the wrapper is synchronized.

---

# 4.4 But what about iteration?

This is where many students make a mistake.

They think:

```java
Collections.synchronizedList(...)
```

means:

> "Everything involving the list is automatically synchronized."

Not quite.

When iterating, synchronize externally:

```java
synchronized (list) {

    for (Integer x : list) {
        System.out.println(x);
    }
}
```

Why?

Because iteration is a **sequence of operations**, not one simple method call.

---

# 4.5 Synchronized vs concurrent

Think:

```text
Synchronized wrapper
        ↓
take an existing collection
        ↓
synchronize access
```

Whereas:

```text
Concurrent collection
        ↓
designed specifically
for concurrent workloads
```

For example:

```java
Collections.synchronizedMap(
    new HashMap<>()
);
```

versus:

```java
new ConcurrentHashMap<>();
```

They are not the same implementation or concurrency model.

---

# 4.6 Synchronization vs immutability

Don't mix these concepts.

### Synchronization

```text
Collection can change
        ↓
threads coordinate access
```

### Immutability

```text
Collection cannot structurally change
```

Therefore:

```text
Synchronized ≠ Immutable
Immutable ≠ Synchronized
```

---

# 5. Immutable / Unmodifiable Collections

This is another area where terminology causes confusion.

---

# 5.1 What is an immutable collection?

An immutable collection cannot be structurally changed after creation.

Example:

```java
List<String> list =
        List.of("Java", "Python", "C++");
```

Then:

```java
list.add("Go");
```

throws:

```text
UnsupportedOperationException
```

Similarly:

```java
Set.of(...)
Map.of(...)
```

create unmodifiable collection instances.

---

# 5.2 What does unmodifiable mean?

An unmodifiable collection does not allow modifications **through that collection reference**.

For example:

```java
List<String> original =
        new ArrayList<>();

original.add("Java");

List<String> view =
        Collections.unmodifiableList(original);
```

This is prohibited:

```java
view.add("Python");  // ❌
```

But this remains possible:

```java
original.add("Python");  // ✅
```

So the view may now show:

```text
Java
Python
```

---

# 5.3 Think about a window

Imagine:

```text
original collection
       ↓
    [Java]
       ↑
       │
unmodifiable view
```

The view says:

> "You cannot modify the collection through me."

But the owner of the original collection can still change it.

Therefore:

```text
unmodifiable view
        ≠
independent immutable copy
```

---

# 5.4 `List.of()` vs `unmodifiableList()`

### `List.of()`

```java
List<String> list =
    List.of("A", "B", "C");
```

This creates an unmodifiable list.

### `Collections.unmodifiableList()`

```java
List<String> view =
    Collections.unmodifiableList(original);
```

This creates an **unmodifiable view**.

This is one of the most important distinctions in this chapter.

---

# 5.5 Are the objects inside immutable?

Not necessarily.

Consider:

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
    new Student("Mahaboob");

List<Student> list =
    List.of(s);
```

You cannot:

```java
list.add(...);      // ❌
```

But:

```java
s.name = "Basha";
```

may still work.

Therefore:

```text
Immutable collection
        ≠
Every contained object is immutable
```

This is called the difference between **shallow immutability** and **deep immutability**.

---

# 5.6 Why use immutable collections?

They are useful when you want:

* predictable state
* safer API design
* protection from accidental structural changes
* easier sharing between threads
* simpler reasoning about program state

For example:

```java
private static final List<String> DAYS =
    List.of("MON", "TUE", "WED");
```

Nobody can accidentally do:

```java
DAYS.add("THU");
```

---

# 6. Collection Performance

Now let's answer:

> Which collection is faster?

The correct answer is:

> **It depends on what operation you need.**

---

# 6.1 Big-O in simple language

Suppose we have 1,000,000 elements.

### O(1)

Doesn't depend significantly on `n`.

```text
1 operation
```

### O(log n)

Grows slowly.

```text
1,000,000 elements
→ roughly a small number of tree-search steps
```

### O(n)

May need to examine many elements.

```text
1,000,000 elements
→ potentially 1,000,000 checks
```

---

# 6.2 ArrayList

Consider:

```java
ArrayList<Integer> list =
    new ArrayList<>();
```

It uses a resizable array.

Therefore:

```java
list.get(500);
```

is typically:

```text
O(1)
```

Why?

Because the index directly determines where to access the array.

---

# 6.3 ArrayList insertion

At the end:

```java
list.add(100);
```

is typically **amortized O(1)**.

Occasionally the internal array has to grow.

So:

```text
Most additions → cheap
Occasional resize → expensive
Overall → amortized O(1)
```

---

# 6.4 ArrayList middle insertion

Suppose:

```text
[A][B][C][D]
```

Insert `X` at index 1:

```text
[A][X][B][C][D]
```

`B`, `C`, and `D` have to move.

Therefore:

```text
middle insertion → O(n)
```

---

# 6.5 ArrayList removal

Remove `B`:

```text
[A][B][C][D]
```

becomes:

```text
[A][C][D]
```

Elements shift.

Therefore:

```text
middle removal → O(n)
```

---

# 6.6 LinkedList

Now imagine:

```text
A ↔ B ↔ C ↔ D
```

Each node stores links to neighboring nodes.

So if you already have a reference to the correct node, insertion/removal can be O(1).

But finding the node by index takes traversal.

For:

```java
list.get(1000);
```

you cannot directly jump to element 1000.

Therefore:

```text
get(index) → O(n)
```

in typical use.

---

# 6.7 The big LinkedList misunderstanding

Students often memorize:

```text
LinkedList insertion = O(1)
```

and stop there.

That's incomplete.

Suppose:

```java
list.add(1000, value);
```

The program must first locate index 1000.

So:

```text
find position → O(n)
insert node   → O(1)
--------------------
total         → O(n)
```

Therefore don't automatically choose `LinkedList` whenever you see insertions/deletions.

---

# 6.8 HashSet

`HashSet` uses hashing.

Typical/expected performance:

```text
add()      → average O(1)
contains() → average O(1)
remove()   → average O(1)
```

This makes it excellent for questions like:

> "Have I already seen this value?"

---

# 6.9 TreeSet

`TreeSet` maintains sorted order.

Typical operations:

```text
add()      → O(log n)
contains() → O(log n)
remove()   → O(log n)
```

Why accept O(log n) instead of average O(1)?

Because we get:

```text
sorted data
```

So:

```text
HashSet
→ speed-focused uniqueness

TreeSet
→ sorted uniqueness
```

---

# 6.10 HashMap

Typical/expected:

```text
put()        → average O(1)
get()        → average O(1)
remove()     → average O(1)
containsKey()→ average O(1)
```

Excellent for:

```text
key → value
```

lookup.

---

# 6.11 TreeMap

`TreeMap` maintains keys in sorted order.

Typical:

```text
put() → O(log n)
get() → O(log n)
remove() → O(log n)
```

So:

```text
HashMap
→ fast average lookup

TreeMap
→ sorted keys
```

---

# 6.12 Performance table

| Collection   | Typical operation        | Typical complexity |
| ------------ | ------------------------ | -----------------: |
| `ArrayList`  | `get(index)`             |               O(1) |
| `ArrayList`  | add at end               |     Amortized O(1) |
| `ArrayList`  | middle insertion/removal |               O(n) |
| `LinkedList` | `get(index)`             |               O(n) |
| `LinkedList` | add/remove at ends       |               O(1) |
| `HashSet`    | add/search/remove        |       Average O(1) |
| `TreeSet`    | add/search/remove        |           O(log n) |
| `HashMap`    | put/get/remove           |       Average O(1) |
| `TreeMap`    | put/get/remove           |           O(log n) |

These are typical complexity characteristics, not a promise that every implementation or workload behaves identically.

---

# 7. Choosing the Correct Collection

Now let's pretend we're developing a real application.

Instead of asking:

> "Which collection should I memorize?"

Ask:

> **"What does my data need to do?"**

---

# 7.1 Need duplicates?

Example:

```text
Java
Java
Python
Java
```

If duplicates are meaningful:

```text
List
```

Use:

```java
ArrayList
```

as the usual general-purpose starting point.

---

# 7.2 Need uniqueness?

Suppose:

```text
Java
Java
Python
```

but we want:

```text
Java
Python
```

Use:

```text
Set
```

Usually:

```java
HashSet
```

---

# 7.3 Need uniqueness + insertion order?

Use:

```java
LinkedHashSet
```

Example:

```text
Input:

Java
Python
C++
Java
```

Encounter order:

```text
Java
Python
C++
```

and duplicate `Java` isn't added again.

---

# 7.4 Need uniqueness + sorting?

Use:

```java
TreeSet
```

Example:

```java
TreeSet<Integer> set =
    new TreeSet<>();

set.add(50);
set.add(10);
set.add(30);
```

Iteration gives sorted order:

```text
10
30
50
```

---

# 7.5 Need key-value data?

Use:

```text
Map
```

Example:

```text
101 → Java
102 → Python
103 → C++
```

The general-purpose choice:

```java
HashMap
```

---

# 7.6 Need key-value + insertion order?

Use:

```java
LinkedHashMap
```

---

# 7.7 Need key-value + sorted keys?

Use:

```java
TreeMap
```

---

# 7.8 Need concurrent key-value storage?

Use:

```java
ConcurrentHashMap
```

especially when multiple threads need to access/update the map.

---

# 7.9 Need a normal queue?

Use:

```text
Queue
```

Often:

```java
ArrayDeque
```

is an excellent general-purpose choice for queue/deque operations.

---

# 7.10 Need both ends?

Use:

```text
Deque
```

Example:

```java
Deque<Integer> deque =
    new ArrayDeque<>();
```

Then:

```java
deque.addFirst(10);
deque.addLast(20);
```

and:

```java
deque.removeFirst();
deque.removeLast();
```

---

# 7.11 Need priority?

Use:

```java
PriorityQueue
```

For example:

```text
Task A → priority 1
Task B → priority 5
Task C → priority 2
```

The queue determines which element is at the head according to its ordering.

Remember:

> `PriorityQueue` is not the same thing as a fully sorted list.

---

# 7.12 Need producer-consumer communication?

Use:

```text
BlockingQueue
```

Think:

```text
Producer
    ↓
┌───────────────┐
│ BlockingQueue │
└───────────────┘
    ↓
Consumer
```

This is one of the most practical uses of concurrent collections.

---

# 7.13 Many reads and very few writes?

Consider:

```java
CopyOnWriteArrayList
```

Think:

```text
READ READ READ READ READ
              ↓
            WRITE
```

Good fit.

But:

```text
WRITE WRITE WRITE WRITE WRITE
```

is generally a poor fit.

---

# 8. Let's Build a Decision Tree

Start here:

```text
              WHAT DO I NEED?
                    │
        ┌───────────┼───────────┐
        │           │           │
    Key → Value   Sequence    Unique data
        │           │           │
       Map         List         Set
```

Then:

```text
MAP
 │
 ├── Fast general lookup → HashMap
 ├── Insertion/access order → LinkedHashMap
 ├── Sorted keys → TreeMap
 └── Concurrent → ConcurrentHashMap
```

For List:

```text
LIST
 │
 ├── General-purpose/index access → ArrayList
 └── Specialized linked/deque use → LinkedList
```

For Set:

```text
SET
 │
 ├── Fast general uniqueness → HashSet
 ├── Insertion order → LinkedHashSet
 └── Sorted → TreeSet
```

For processing:

```text
PROCESSING
 │
 ├── FIFO → Queue
 ├── Both ends → Deque
 ├── Priority → PriorityQueue
 └── Producer/consumer → BlockingQueue
```

---

# 9. The Most Important Comparisons

## ArrayList vs LinkedList

```text
ArrayList
   ↓
array
   ↓
fast get(index)
   ↓
usually the default List

LinkedList
   ↓
nodes
   ↓
slow get(index)
   ↓
useful for specific linked/deque workloads
```

---

## HashSet vs LinkedHashSet vs TreeSet

Think:

```text
HashSet
   ↓
unique
   ↓
no guaranteed order
```

```text
LinkedHashSet
   ↓
unique
   ↓
insertion/encounter order
```

```text
TreeSet
   ↓
unique
   ↓
sorted
```

---

## HashMap vs LinkedHashMap vs TreeMap

Think:

```text
HashMap
   ↓
key/value
   ↓
no guaranteed order
```

```text
LinkedHashMap
   ↓
key/value
   ↓
predictable encounter order
```

```text
TreeMap
   ↓
key/value
   ↓
sorted keys
```

---

# 10. Synchronized vs Concurrent

This is a very important distinction.

Imagine:

```java
Collections.synchronizedList(
    new ArrayList<>()
);
```

This is essentially:

```text
ordinary collection
        +
synchronized wrapper
```

Whereas:

```java
new CopyOnWriteArrayList<>();
```

or:

```java
new ConcurrentHashMap<>();
```

are specifically designed with concurrent access patterns in mind.

So:

```text
Synchronized collection
       ↓
locking-based wrapper

Concurrent collection
       ↓
purpose-designed concurrency
```

---

# 11. Fail-Fast vs Weakly Consistent vs Snapshot

Let's put everything together.

### Ordinary collection

```text
ArrayList
HashMap
HashSet
```

Iterator is commonly:

```text
FAIL-FAST
```

---

### ConcurrentHashMap

Iterator is:

```text
WEAKLY CONSISTENT
```

It can continue while concurrent changes occur.

---

### CopyOnWriteArrayList

Iterator is:

```text
SNAPSHOT-BASED
```

It sees the state represented by the array when the iterator was created.

---

# 12. Immutable vs Unmodifiable

Remember this sentence:

> **An unmodifiable view prevents modification through the view; immutability means the collection itself cannot be structurally modified.**

Example:

```java
List<String> original =
    new ArrayList<>();

List<String> view =
    Collections.unmodifiableList(original);
```

The `view` cannot modify the list.

But:

```java
original.add("Java");
```

can still change what the view sees.

Whereas:

```java
List<String> immutable =
    List.of("Java", "Python");
```

cannot be structurally modified.

---

# 13. Real-World Examples

## Example 1 — Student names

Requirement:

> Store names and allow duplicates.

Use:

```java
List<String> names =
    new ArrayList<>();
```

---

## Example 2 — Unique usernames

Requirement:

> Every username must be unique.

Use:

```java
Set<String> usernames =
    new HashSet<>();
```

---

## Example 3 — Unique usernames in registration order

```java
Set<String> usernames =
    new LinkedHashSet<>();
```

---

## Example 4 — Sorted employee IDs

```java
Set<Integer> ids =
    new TreeSet<>();
```

---

## Example 5 — Employee ID → employee name

```java
Map<Integer, String> employees =
    new HashMap<>();
```

---

## Example 6 — Sorted employee IDs with employee information

```java
Map<Integer, String> employees =
    new TreeMap<>();
```

---

## Example 7 — Shared map between threads

```java
ConcurrentHashMap<Integer, String> map =
    new ConcurrentHashMap<>();
```

---

## Example 8 — Listener list

If listeners are read very frequently and rarely changed:

```java
CopyOnWriteArrayList<Listener> listeners =
    new CopyOnWriteArrayList<>();
```

---

## Example 9 — Producer/consumer

```java
BlockingQueue<Task> queue =
    new LinkedBlockingQueue<>();
```

---

# 14. Common Student Doubts

## Doubt 1

### "Does fail-fast mean the collection immediately throws an exception whenever modified?"

Not necessarily.

Fail-fast behavior is best effort.

---

## Doubt 2

### "Does ConcurrentModificationException mean multiple threads are involved?"

No.

A single thread can cause it.

---

## Doubt 3

### "Is ConcurrentHashMap just HashMap + synchronized?"

No.

It is specifically designed for concurrent access and has different concurrency characteristics.

---

## Doubt 4

### "Is fail-safe the official opposite of fail-fast?"

Not exactly.

"Fail-safe" is common classroom terminology. The Java API more precisely describes particular iterators as **weakly consistent** or **snapshot-based**.

---

## Doubt 5

### "Does immutable mean objects inside the collection can't change?"

No.

The collection structure can be immutable while its contained objects are mutable.

---

## Doubt 6

### "Is LinkedList faster than ArrayList for every insertion?"

No.

Finding the location can itself require O(n) traversal.

---

## Doubt 7

### "Is TreeSet faster than HashSet?"

Generally no for basic lookup/add/remove.

Typical:

```text
HashSet → average O(1)
TreeSet → O(log n)
```

TreeSet's advantage is sorted order.

---

## Doubt 8

### "Does PriorityQueue keep all elements sorted?"

No.

Its primary guarantee concerns the element at the head.

---

# 15. One Story to Remember Everything

Imagine you're building an online shopping application.

### Product names

Duplicates allowed:

```text
ArrayList
```

### Unique product IDs

```text
HashSet
```

### Unique IDs in registration order

```text
LinkedHashSet
```

### Unique IDs sorted

```text
TreeSet
```

### Product ID → Product

```text
HashMap
```

### Product ID → Product, sorted by ID

```text
TreeMap
```

### Product ID → Product, accessed by many threads

```text
ConcurrentHashMap
```

### Recently viewed products, many reads and few writes

```text
CopyOnWriteArrayList
```

### Jobs waiting for workers

```text
BlockingQueue
```

### Highest-priority job first

```text
PriorityQueue
```

### Data that nobody should modify

```text
List.of(...)
```

Now the collection isn't being selected because of memorization.

It's being selected because of the **requirement**.

---

# 16. 🧠 Final Mental Map

```text
                    ADVANCED COLLECTIONS
                            │
       ┌────────────────────┼────────────────────┐
       │                    │                    │
   Concurrency          Iteration             Safety
       │                    │                    │
       │              ┌─────┴─────┐         ┌────┴────┐
       │              │           │         │         │
       │          Fail-fast   Concurrent   Sync    Immutable
       │                        iteration
       │                           │
       │                    ┌──────┴──────┐
       │                    │             │
       │              Weakly consistent Snapshot
       │
       ├── ConcurrentHashMap
       ├── CopyOnWriteArrayList
       ├── ConcurrentLinkedQueue
       └── BlockingQueue
       
                    PERFORMANCE
                         │
          ┌──────────────┼──────────────┐
          │              │              │
       ArrayList       HashSet       HashMap
       O(1) index      Avg O(1)      Avg O(1)
          │
       TreeSet / TreeMap
          │
        O(log n)


                    CHOICE
                       │
       ┌───────────────┼────────────────┐
       │               │                │
   Duplicates?     Unique?          Key/value?
       │               │                │
      List             Set              Map
       │               │                │
   ArrayList       HashSet          HashMap
                   LinkedHashSet    LinkedHashMap
                   TreeSet          TreeMap
```

# 🔥 The 10 lines you should remember

```text
1. ArrayList       → general-purpose List + fast index access
2. LinkedList      → specialized linked/deque operations
3. HashSet         → unique + fast average lookup
4. LinkedHashSet   → unique + insertion/encounter order
5. TreeSet         → unique + sorted
6. HashMap         → key/value + fast average lookup
7. LinkedHashMap   → key/value + predictable encounter order
8. TreeMap         → key/value + sorted keys
9. ConcurrentHashMap → concurrent key/value access
10. CopyOnWriteArrayList → many reads + few writes
```

And the four concepts that students most often mix up:

```text
FAIL-FAST
→ detects certain unexpected modification during iteration

WEAKLY CONSISTENT
→ concurrent iterator continues and may reflect some changes

SYNCHRONIZED
→ threads coordinate access to mutable state

IMMUTABLE
→ collection structure cannot be modified
```

**The central lesson of Advanced Collections is not "memorize more classes." It is:**

> **First identify the data requirement → then identify ordering/uniqueness → then identify the dominant operation → then consider concurrency → finally choose the collection.**
