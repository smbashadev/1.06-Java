# 15. Advanced Collections in Java — DEEPDIVE

This chapter goes beyond simply knowing the collection names. The goal is to understand **what happens when collections are shared between threads, what happens during modification while iterating, how synchronization works, what immutability really means, how performance differs, and how to select the correct collection for a real problem.**

---

# 1. Concurrent Collections

## 1.1 What are concurrent collections?

A **concurrent collection** is a collection implementation designed specifically for situations where **multiple threads access the collection concurrently**.

Important examples are in:

```java
java.util.concurrent
```

Common concurrent collections include:

```text
ConcurrentHashMap
CopyOnWriteArrayList
CopyOnWriteArraySet
ConcurrentLinkedQueue
ConcurrentLinkedDeque
BlockingQueue implementations
```

Examples of `BlockingQueue` implementations:

```text
ArrayBlockingQueue
LinkedBlockingQueue
PriorityBlockingQueue
DelayQueue
SynchronousQueue
```

---

# 1.2 Why do we need concurrent collections?

Suppose two threads access an ordinary `HashMap`:

```text
Thread 1 ───────┐
                ├──> HashMap
Thread 2 ───────┘
```

If both threads modify shared mutable state without appropriate synchronization, you can get race conditions or inconsistent behavior.

Simply doing:

```java
Map<Integer, String> map = new HashMap<>();
```

does **not** make the map thread-safe.

---

# 1.3 `ConcurrentHashMap`

`ConcurrentHashMap` is one of the most important concurrent collections.

```java
import java.util.concurrent.ConcurrentHashMap;

ConcurrentHashMap<Integer, String> map =
        new ConcurrentHashMap<>();

map.put(1, "Java");
map.put(2, "Python");
```

Multiple threads can access it concurrently.

Example:

```java
ConcurrentHashMap<Integer, String> map =
        new ConcurrentHashMap<>();

Thread t1 = new Thread(() ->
    map.put(1, "Java")
);

Thread t2 = new Thread(() ->
    map.put(2, "Python")
);

t1.start();
t2.start();
```

---

# 1.4 Is `ConcurrentHashMap` completely locked?

No.

This is an important misconception.

Modern `ConcurrentHashMap` is designed to permit a high degree of concurrent access rather than placing one global lock around the entire map for every operation.

Its implementation uses sophisticated concurrency mechanisms internally.

The important conceptual distinction is:

```text
Synchronized Map
        ↓
broad locking around operations

ConcurrentHashMap
        ↓
designed for scalable concurrent access
```

---

# 1.5 Does `ConcurrentHashMap` allow null?

No.

```java
map.put(null, "Java");       // ❌
map.put(1, null);            // ❌
```

Both null keys and null values are prohibited.

One reason is that in concurrent access, `null` cannot cleanly distinguish between:

```text
key is absent
```

and:

```text
key exists with null value
```

---

# 1.6 Atomic compound operations

This is another major reason to use concurrent collections.

Suppose you want:

```text
If key does not exist
        ↓
insert value
```

Doing separate operations:

```java
if (!map.containsKey(key))
{
    map.put(key, value);
}
```

is not atomic.

Another thread could modify the map between the two operations.

`ConcurrentHashMap` provides operations such as:

```java
putIfAbsent()
compute()
computeIfAbsent()
computeIfPresent()
merge()
replace()
replaceAll()
```

For example:

```java
map.putIfAbsent(1, "Java");
```

means approximately:

> Add the value only if the key does not already have a mapping.

This is much safer for concurrent compound logic than manually combining separate operations.

---

# 1.7 `CopyOnWriteArrayList`

Another important concurrent collection:

```java
CopyOnWriteArrayList<String> list =
        new CopyOnWriteArrayList<>();
```

The basic idea is:

> When the collection is modified, a new copy of the underlying array is created.

Therefore:

```text
READ
 ↓
cheap / non-blocking style access

WRITE
 ↓
copy underlying array
 ↓
perform modification
```

---

# 1.8 When should we use CopyOnWriteArrayList?

When:

```text
READS >>> WRITES
```

Example:

```text
configuration data
listener lists
observer lists
small collections that are read very frequently
```

If the collection is modified thousands of times per second, `CopyOnWriteArrayList` may be a poor choice because every write involves copying.

---

# 1.9 Example of snapshot iteration

```java
CopyOnWriteArrayList<Integer> list =
        new CopyOnWriteArrayList<>();

list.add(10);
list.add(20);
list.add(30);

for (Integer x : list)
{
    list.add(40);
    System.out.println(x);
}
```

The iterator works over the array snapshot it obtained.

The newly added elements are not necessarily seen by that existing iterator.

This is fundamentally different from an ordinary `ArrayList`.

---

# 1.10 `ConcurrentLinkedQueue`

For a non-blocking concurrent FIFO queue:

```java
ConcurrentLinkedQueue<Integer> queue =
        new ConcurrentLinkedQueue<>();

queue.offer(10);
queue.offer(20);

System.out.println(queue.poll());
```

Output:

```text
10
```

It is designed for concurrent producer/consumer-style access without using a traditional blocking lock for the basic queue operations.

---

# 1.11 `BlockingQueue`

A `BlockingQueue` is different from an ordinary concurrent queue.

Example:

```java
BlockingQueue<Integer> queue =
        new ArrayBlockingQueue<>(10);
```

A producer can do:

```java
queue.put(100);
```

and a consumer can do:

```java
Integer x = queue.take();
```

If the queue is full, `put()` can wait.

If the queue is empty, `take()` can wait.

This makes `BlockingQueue` extremely useful for **producer-consumer designs**.

---

# 1.12 Concurrent collection summary

| Collection              | Main purpose                               |
| ----------------------- | ------------------------------------------ |
| `ConcurrentHashMap`     | Concurrent key-value storage               |
| `CopyOnWriteArrayList`  | Read-heavy concurrent list                 |
| `ConcurrentLinkedQueue` | Non-blocking concurrent FIFO               |
| `ConcurrentLinkedDeque` | Non-blocking concurrent double-ended queue |
| `BlockingQueue`         | Producer-consumer coordination             |

---

# 2. Fail-Fast

## 2.1 What does fail-fast mean?

A fail-fast iterator attempts to detect an incompatible structural modification to its backing collection while iteration is in progress.

It may throw:

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

for (Integer x : list)
{
    if (x == 20)
    {
        list.remove(x);
    }
}
```

This can produce:

```text
ConcurrentModificationException
```

---

# 2.2 Why does this happen?

The iterator internally tracks modification state.

Conceptually:

```text
Collection
   │
   ├── modification count
   │
   └── Iterator
          │
          └── expected modification state
```

If the collection is structurally modified outside the iterator, the iterator can detect that its expected state no longer matches the collection's state.

---

# 2.3 What is structural modification?

A structural modification generally means a change that changes the collection's structure, such as:

```java
list.add(...)
list.remove(...)
```

For maps and sets, insertion/removal of entries is structural.

Changing an existing object's internal state is not necessarily a structural modification to the collection.

---

# 2.4 Can an iterator itself remove an element?

Yes.

```java
Iterator<Integer> it = list.iterator();

while (it.hasNext())
{
    Integer x = it.next();

    if (x == 20)
    {
        it.remove();
    }
}
```

`Iterator.remove()` is specifically designed to remove the last element returned by the iterator.

This avoids the particular structural-modification problem caused by directly modifying the collection during iteration.

---

# 2.5 Is fail-fast guaranteed?

**No.**

This is a very important exam/interview point.

Fail-fast behavior is generally **best effort**.

You should not write application logic that depends on:

```text
"ConcurrentModificationException will definitely occur."
```

The purpose is primarily to detect programming errors early rather than provide thread synchronization.

---

# 2.6 Fail-fast does NOT mean thread-safe

This statement is extremely important:

```text
Fail-fast ≠ thread-safe
```

For example:

```java
ArrayList<Integer> list =
        new ArrayList<>();
```

Its iterator may be fail-fast, but `ArrayList` itself is not a thread-safe collection.

---

# 2.7 Why is `ConcurrentModificationException` misleading?

Despite the name, it does not necessarily mean:

```text
"another thread modified the collection."
```

It can happen within a **single thread**:

```java
for (Integer x : list)
{
    list.remove(x);
}
```

So the exception means, roughly:

> The collection was modified in a way that interfered with the iterator's expected state.

---

# 3. Fail-Safe / Weakly Consistent Iteration

## 3.1 Is "fail-safe" an official Java term?

Not really.

This is an important terminology correction.

Java's official API documentation more commonly describes particular iterator behaviors as:

* **weakly consistent**
* **snapshot**
* fail-fast

The term **fail-safe** is widely used in Java teaching material, but it is not a formal universal classification in the Collections Framework.

---

# 3.2 Weakly consistent iterator

Concurrent collections can provide iterators that are **weakly consistent**.

For example:

```java
ConcurrentHashMap<Integer, String> map =
        new ConcurrentHashMap<>();
```

Its iterators:

* do not generally throw `ConcurrentModificationException` because of concurrent modification
* can proceed while the collection changes
* may reflect some modifications
* may not reflect all modifications
* do not necessarily represent one immutable snapshot

---

# 3.3 Example

```java
ConcurrentHashMap<Integer, String> map =
        new ConcurrentHashMap<>();

map.put(1, "Java");
map.put(2, "Python");
map.put(3, "C++");

for (Integer key : map.keySet())
{
    map.put(4, "Go");

    System.out.println(key);
}
```

The iterator does not behave like an ordinary `HashMap` iterator that detects every structural change through fail-fast behavior.

The exact elements observed should not be assumed to represent a frozen snapshot.

---

# 3.4 Snapshot iteration

`CopyOnWriteArrayList` is different.

Its iterator uses a snapshot of the array at iterator creation.

Conceptually:

```text
Original array
[10, 20, 30]
       │
       └── Iterator created
                │
                └── snapshot = [10,20,30]

Later:
list.add(40)

Current list:
[10,20,30,40]

Existing iterator:
[10,20,30]
```

Therefore the iterator can safely proceed without observing the later modification.

---

# 3.5 Weakly consistent vs snapshot

|                                                               | Weakly consistent   | Snapshot                   |
| ------------------------------------------------------------- | ------------------- | -------------------------- |
| Example                                                       | `ConcurrentHashMap` | `CopyOnWriteArrayList`     |
| Concurrent modifications tolerated                            | Yes                 | Yes                        |
| Fixed snapshot                                                | No                  | Yes                        |
| May observe later modifications                               | Potentially         | Existing iterator does not |
| `ConcurrentModificationException` for concurrent modification | Generally no        | No                         |

---

# 4. Synchronization

## 4.1 What is synchronization?

Synchronization is a mechanism for coordinating access to shared mutable state between threads.

Suppose:

```java
int count = 0;
```

Two threads perform:

```java
count++;
```

This looks like one operation but conceptually involves:

```text
read count
   ↓
add 1
   ↓
write count
```

Two threads can interfere with each other.

---

# 4.2 Synchronizing a collection

Java provides wrappers such as:

```java
List<Integer> list =
    Collections.synchronizedList(new ArrayList<>());
```

Similarly:

```java
Set<Integer> set =
    Collections.synchronizedSet(new HashSet<>());
```

and:

```java
Map<Integer, String> map =
    Collections.synchronizedMap(new HashMap<>());
```

---

# 4.3 What does the synchronized wrapper do?

It synchronizes access to the wrapped collection's methods.

Conceptually:

```text
Thread A
   │
   ├── lock
   │
   ├── operation
   │
   └── unlock

Thread B
   │
   └── waits for lock
```

This provides synchronized access to individual operations.

---

# 4.4 Is a synchronized collection automatically safe for iteration?

This is a classic trap.

No.

You should synchronize explicitly while traversing:

```java
List<Integer> list =
    Collections.synchronizedList(new ArrayList<>());

synchronized (list)
{
    for (Integer x : list)
    {
        System.out.println(x);
    }
}
```

Why?

Because:

```text
iteration
```

is a multi-step operation.

The individual collection methods being synchronized does not automatically make the entire sequence:

```text
hasNext()
next()
hasNext()
next()
...
```

atomic with respect to other modifications.

---

# 4.5 Synchronized collection vs concurrent collection

### Synchronized wrapper

```java
Collections.synchronizedList(...)
```

takes an existing collection and adds synchronized method access around it.

### Concurrent collection

```java
ConcurrentHashMap
```

is designed specifically for concurrent workloads.

Therefore:

```text
Synchronized
    ↓
general locking approach

Concurrent
    ↓
designed for concurrent scalability/semantics
```

Neither is automatically "better" in every situation.

---

# 4.6 Synchronization does not make objects immutable

This:

```java
Collections.synchronizedList(...)
```

means concurrent access is coordinated.

It does **not** mean:

```text
collection can never change
```

In fact, its purpose is to allow controlled mutation.

Compare:

```text
Synchronized → can change safely
Immutable    → cannot change
```

---

# 5. Immutable / Unmodifiable Collections

This area has an extremely important distinction.

---

## 5.1 Immutable collection

An immutable collection cannot be structurally modified after creation.

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

produce unmodifiable collection instances.

---

# 5.2 Unmodifiable view

Consider:

```java
List<String> original =
        new ArrayList<>();

original.add("Java");

List<String> view =
        Collections.unmodifiableList(original);
```

This prevents modification through `view`:

```java
view.add("Python");       // ❌
```

But:

```java
original.add("Python");
```

is still possible.

Therefore the view can change because its underlying collection changed.

---

# 5.3 Visual difference

### Unmodifiable view

```text
original
   │
   ├── can modify
   │
   ↓
unmodifiable view
   │
   └── cannot modify through view
```

But:

```text
original changes
       ↓
view may reflect change
```

---

### Immutable collection

```text
Immutable collection
        │
        ├── cannot add
        ├── cannot remove
        └── cannot replace
```

The collection's structure cannot be modified.

---

# 5.4 `List.of()` vs `Collections.unmodifiableList()`

### `List.of()`

```java
List<String> list =
    List.of("A", "B", "C");
```

Creates an unmodifiable list.

### `Collections.unmodifiableList()`

```java
List<String> list =
    Collections.unmodifiableList(original);
```

Creates an **unmodifiable view** of `original`.

This distinction matters.

---

# 5.5 Are immutable collections deeply immutable?

Not necessarily.

Consider:

```java
class Student
{
    String name;

    Student(String name)
    {
        this.name = name;
    }
}
```

Then:

```java
Student s = new Student("A");

List<Student> list =
    List.of(s);
```

The list structure cannot change:

```java
list.add(...);       // ❌
```

But:

```java
s.name = "B";
```

can still change the object contained in the list.

Therefore:

```text
Immutable collection
        ≠
deeply immutable object graph
```

---

# 5.6 Why is immutability useful?

Immutable collections are useful because they:

* reduce accidental modification
* are easier to reason about
* can simplify sharing between threads
* make APIs safer
* support defensive design
* reduce synchronization requirements when the referenced state truly never changes

---

# 6. Collection Performance

Performance is not simply:

```text
Which collection is fastest?
```

The correct question is:

> **Fastest for which operation and workload?**

---

# 6.1 Big-O basics

Common complexity classes:

```text
O(1)       constant
O(log n)   logarithmic
O(n)       linear
O(n log n)
O(n²)
```

As `n` grows:

```text
O(1) < O(log n) < O(n) < O(n log n) < O(n²)
```

---

# 6.2 ArrayList

```java
List<Integer> list =
    new ArrayList<>();
```

Internally, an `ArrayList` is backed by a resizable array.

Therefore:

```java
list.get(500);
```

is typically:

```text
O(1)
```

because the index can be directly calculated.

---

# 6.3 ArrayList insertion

At the end:

```java
list.add(value);
```

is generally **amortized O(1)**.

Why amortized?

Because occasionally the backing array becomes full and must be replaced with a larger array.

Conceptually:

```text
[10][20][30][40]
              ↑
            full

        ↓ resize

[10][20][30][40][ ][ ][ ][ ]
```

That particular resize is expensive, but spread across many additions, the average cost is amortized O(1).

---

# 6.4 ArrayList insertion in the middle

Suppose:

```text
[A][B][C][D]
```

Insert `X` at index 1:

```text
[A][X][B][C][D]
```

Elements must shift.

Therefore:

```text
middle insertion → O(n)
```

---

# 6.5 ArrayList removal

Removing from the middle also requires shifting elements.

```text
[A][B][C][D]
    ↓ remove B

[A][C][D]
```

Therefore:

```text
middle removal → O(n)
```

---

# 6.6 LinkedList

`LinkedList` is node-based.

Conceptually:

```text
Node
 ├── previous
 ├── data
 └── next
```

Example:

```text
A ↔ B ↔ C ↔ D
```

Index-based access is slow:

```java
list.get(1000);
```

because the list must traverse nodes.

Therefore:

```text
get(index) → O(n)
```

in typical use.

---

# 6.7 Why doesn't LinkedList automatically make insertion O(1)?

This is an extremely common misconception.

If you **already have the node/position**, insertion can be O(1).

But if you say:

```java
list.add(1000, value);
```

the implementation first has to locate the position.

That traversal costs O(n).

So:

```text
find position + insert
        ↓
O(n) + O(1)
        ↓
O(n)
```

---

# 6.8 HashSet

`HashSet` is hash-table based.

Typical operations:

```text
add()       → average O(1)
contains()  → average O(1)
remove()    → average O(1)
```

But these are **average/expected** complexities, not universal worst-case guarantees.

Performance depends heavily on good hashing and implementation details.

---

# 6.9 TreeSet

`TreeSet` is based on a balanced tree structure.

Typical operations:

```text
add()       → O(log n)
contains()  → O(log n)
remove()    → O(log n)
```

The advantage is ordering:

```java
TreeSet<Integer> set =
    new TreeSet<>();
```

will maintain sorted order according to its ordering rules.

---

# 6.10 HashMap

Typical:

```text
put()       → average O(1)
get()       → average O(1)
remove()    → average O(1)
containsKey → average O(1)
```

The actual performance depends on hashing and implementation details.

---

# 6.11 TreeMap

`TreeMap` maintains its keys according to ordering.

Typical operations:

```text
put()        → O(log n)
get()        → O(log n)
remove()     → O(log n)
```

Use it when you need:

```text
key/value + sorted keys
```

rather than simply maximum hash-table lookup performance.

---

# 6.12 Performance comparison

| Collection      |     Access |   Search |                   Add |       Remove | Main strength         |
| --------------- | ---------: | -------: | --------------------: | -----------: | --------------------- |
| `ArrayList`     | O(1) index |     O(n) | Amortized O(1) at end |         O(n) | Random access         |
| `LinkedList`    | O(n) index |     O(n) |          O(1) at ends | O(1) at ends | Node/deque operations |
| `HashSet`       |          — | Avg O(1) |              Avg O(1) |     Avg O(1) | Uniqueness            |
| `LinkedHashSet` |          — | Avg O(1) |              Avg O(1) |     Avg O(1) | Uniqueness + order    |
| `TreeSet`       |          — | O(log n) |              O(log n) |     O(log n) | Sorted uniqueness     |
| `HashMap`       |          — | Avg O(1) |              Avg O(1) |     Avg O(1) | Key/value lookup      |
| `LinkedHashMap` |          — | Avg O(1) |              Avg O(1) |     Avg O(1) | Map + encounter order |
| `TreeMap`       |          — | O(log n) |              O(log n) |     O(log n) | Sorted keys           |

---

# 7. Choosing the Correct Collection

This is where all the previous concepts come together.

Don't memorize:

```text
ArrayList = this
HashSet = that
```

Instead, ask a sequence of questions.

---

# 7.1 Question 1: Do I need key-value relationships?

If yes:

```text
Map
```

Examples:

```java
Map<Integer, String>
```

Use:

```text
HashMap
LinkedHashMap
TreeMap
ConcurrentHashMap
```

depending on the requirements.

---

# 7.2 Question 2: Do I need duplicates?

If yes:

```text
List
```

Example:

```java
List<String> names =
    new ArrayList<>();
```

A List allows duplicates:

```text
Java
Java
Python
Java
```

---

# 7.3 Question 3: Do I need uniqueness?

Use:

```text
Set
```

For example:

```java
Set<Integer> numbers =
    new HashSet<>();
```

Adding:

```text
10
10
20
```

results in one `10` and one `20`.

---

# 7.4 Question 4: Do I need sorted values?

Use:

```text
TreeSet
```

for unique sorted values.

```java
TreeSet<Integer> set =
    new TreeSet<>();
```

or:

```text
TreeMap
```

for sorted keys.

---

# 7.5 Question 5: Do I need insertion order?

Use:

```text
LinkedHashSet
LinkedHashMap
```

For example:

```java
Set<String> set =
    new LinkedHashSet<>();
```

It preserves encounter/insertion order.

---

# 7.6 Question 6: Do I need fast index access?

Usually:

```text
ArrayList
```

Example:

```java
list.get(5000);
```

This is one of the biggest reasons `ArrayList` is commonly preferred over `LinkedList`.

---

# 7.7 Question 7: Do I need a queue?

Use:

```text
Queue
```

For example:

```text
ArrayDeque
PriorityQueue
LinkedList
```

depending on the required behavior.

---

# 7.8 Question 8: Do I need both ends?

Use:

```text
Deque
```

For general-purpose deque operations, `ArrayDeque` is often the preferred starting point.

```java
Deque<Integer> deque =
    new ArrayDeque<>();
```

You can operate at both ends:

```java
deque.addFirst(10);
deque.addLast(20);

deque.removeFirst();
deque.removeLast();
```

---

# 7.9 Question 9: Do I need priority ordering?

Use:

```text
PriorityQueue
```

Example:

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>();

pq.offer(30);
pq.offer(10);
pq.offer(20);
```

The head is the least element according to its ordering by default.

Important:

> `PriorityQueue` does **not** mean iterating through it gives a fully sorted sequence.

Its guarantee is primarily about the **head**, not arbitrary iteration order.

---

# 7.10 Question 10: Do multiple threads access the collection?

If yes, consider concurrent collections rather than automatically using ordinary collections.

Examples:

```text
ConcurrentHashMap
CopyOnWriteArrayList
ConcurrentLinkedQueue
BlockingQueue
```

The correct choice depends on the access pattern.

---

# 7.11 Question 11: Are writes rare and reads extremely frequent?

Consider:

```text
CopyOnWriteArrayList
```

especially when:

```text
READS >>> WRITES
```

But don't use it simply because "it's thread-safe."

Every write can involve copying the backing array.

---

# 7.12 Question 12: Do producers and consumers need to wait?

Consider:

```text
BlockingQueue
```

Example architecture:

```text
Producer
   │
   ↓
BlockingQueue
   │
   ↓
Consumer
```

This is ideal for many producer-consumer designs.

---

# 8. The Most Important Selection Table

| Requirement                        | Good starting choice             |
| ---------------------------------- | -------------------------------- |
| General-purpose list               | `ArrayList`                      |
| Frequent indexed access            | `ArrayList`                      |
| Unique elements                    | `HashSet`                        |
| Unique + insertion order           | `LinkedHashSet`                  |
| Unique + sorted                    | `TreeSet`                        |
| General key-value lookup           | `HashMap`                        |
| Key-value + insertion/access order | `LinkedHashMap`                  |
| Key-value + sorted keys            | `TreeMap`                        |
| Concurrent key-value storage       | `ConcurrentHashMap`              |
| Read-heavy concurrent list         | `CopyOnWriteArrayList`           |
| Non-blocking concurrent queue      | `ConcurrentLinkedQueue`          |
| Producer-consumer queue            | `BlockingQueue`                  |
| Double-ended operations            | `ArrayDeque`                     |
| Priority-based processing          | `PriorityQueue`                  |
| Fixed unmodifiable list            | `List.of()`                      |
| Unmodifiable view                  | `Collections.unmodifiableList()` |

---

# 9. Major Comparisons

## `ArrayList` vs `LinkedList`

|                        | ArrayList         | LinkedList                             |
| ---------------------- | ----------------- | -------------------------------------- |
| Internal structure     | Dynamic array     | Doubly linked nodes                    |
| `get(index)`           | Fast              | Slow                                   |
| Memory overhead        | Lower             | Higher                                 |
| End operations         | Efficient         | Efficient                              |
| Middle insertion       | Shifting required | Node insertion after locating position |
| General default choice | Usually yes       | Specialized use cases                  |

**Important:** Don't automatically choose `LinkedList` just because you see lots of insertions/deletions. If those operations require repeatedly finding positions by index, `ArrayList` may still be faster in real workloads.

---

# 10. `HashSet` vs `TreeSet`

```text
HashSet
   ↓
fast average lookup
   ↓
no sorted order guarantee

TreeSet
   ↓
O(log n)
   ↓
sorted order
```

Choose based on whether you need ordering.

---

# 11. `HashMap` vs `TreeMap`

```text
HashMap
   ↓
fast average lookup
   ↓
no sorted key order

TreeMap
   ↓
O(log n)
   ↓
sorted keys
```

Again:

> Don't pay the ordering cost if you don't need ordering.

---

# 12. `HashMap` vs `ConcurrentHashMap`

### HashMap

```text
not thread-safe
```

### ConcurrentHashMap

```text
designed for concurrent access
```

If multiple threads mutate shared map state, `HashMap` alone is not an appropriate synchronization strategy.

---

# 13. `Collections.synchronizedMap()` vs `ConcurrentHashMap`

```text
Collections.synchronizedMap(...)
       ↓
synchronized wrapper

ConcurrentHashMap
       ↓
purpose-built concurrent map
```

For highly concurrent applications, `ConcurrentHashMap` is generally the more appropriate choice.

But if you simply need to wrap an existing map with synchronized access and the concurrency requirements are modest, a synchronized wrapper can be useful.

---

# 14. Immutability vs Synchronization

This distinction is worth memorizing.

### Synchronization

```text
Multiple threads
       ↓
coordinate access
       ↓
collection may change
```

### Immutability

```text
collection created
       ↓
structure cannot change
```

Therefore:

```text
Thread-safe ≠ immutable
Immutable ≠ synchronized
```

An immutable object can often be safely shared because nobody can mutate its state, but immutability and synchronization are conceptually different mechanisms.

---

# 15. Fail-Fast vs Concurrent Collection

Another major comparison:

```text
ArrayList
   ↓
ordinary collection
   ↓
iterator is commonly fail-fast
```

versus:

```text
ConcurrentHashMap
   ↓
concurrent collection
   ↓
weakly consistent iterator
```

The difference is not simply:

```text
one is good
one is bad
```

They serve different purposes.

---

# 16. The Complete Mental Model

```text
                         COLLECTION
                             │
          ┌──────────────────┼──────────────────┐
          │                  │                  │
       Single-thread       Concurrent       Immutable
          │                  │                  │
          │                  │                  └── List.of()
          │                  │                      Set.of()
          │                  │                      Map.of()
          │                  │
          │            ┌─────┼─────┐
          │            │     │     │
          │         Map    List   Queue
          │            │     │     │
          │         CHM   COWAL   CLQ
          │
          └──────── ordinary collections
                       │
                iteration behavior
                       │
             ┌─────────┴─────────┐
             │                   │
          Fail-fast       Weakly consistent
             │                   │
      ArrayList/HashMap      ConcurrentHashMap
```

---

# 17. Advanced Interview Traps

### Trap 1

**"Fail-fast means thread-safe."**

❌ False.

---

### Trap 2

**"`ConcurrentModificationException` means another thread modified the collection."**

❌ False.

It can happen in one thread.

---

### Trap 3

**"`ConcurrentHashMap` is just a synchronized `HashMap`."**

❌ False.

It has different concurrency semantics and implementation strategies.

---

### Trap 4

**"Fail-safe is an official interface in Java Collections."**

❌ False.

It's commonly used teaching terminology.

---

### Trap 5

**"`List.of()` returns an unmodifiable view of another list."**

❌ False.

It creates an unmodifiable list rather than wrapping a mutable list in the same way as `Collections.unmodifiableList()`.

---

### Trap 6

**"`Collections.unmodifiableList()` makes the original list immutable."**

❌ False.

It creates an unmodifiable **view**.

The original collection may still be modified.

---

### Trap 7

**"`LinkedList` is always faster for insertion."**

❌ False.

Finding the insertion location can itself cost O(n).

---

### Trap 8

**"`HashSet` guarantees random order."**

❌ False.

It provides no guaranteed iteration order.

---

### Trap 9

**"`TreeSet` is faster than `HashSet` because it is sorted."**

❌ False.

Sorting comes with a cost:

```text
HashSet → average O(1)
TreeSet → O(log n)
```

---

### Trap 10

**"`PriorityQueue` iteration gives sorted order."**

❌ False.

Only the head is guaranteed to follow the priority ordering.

---

# 18. Final DEEPDIVE Summary

```text
CONCURRENT COLLECTIONS
    ↓
Designed for concurrent access
    ↓
ConcurrentHashMap
CopyOnWriteArrayList
ConcurrentLinkedQueue
BlockingQueue


FAIL-FAST
    ↓
Iterator detects certain structural changes
    ↓
May throw ConcurrentModificationException
    ↓
Best effort
    ↓
Not thread safety


WEAKLY CONSISTENT
    ↓
Concurrent collection iterators
    ↓
Tolerate concurrent modification
    ↓
No fixed snapshot necessarily


SNAPSHOT
    ↓
CopyOnWriteArrayList
    ↓
Iterator sees snapshot from iterator creation


SYNCHRONIZATION
    ↓
Coordinates access to shared mutable state
    ↓
synchronizedList()
synchronizedSet()
synchronizedMap()


IMMUTABLE
    ↓
Cannot structurally modify
    ↓
List.of()
Set.of()
Map.of()


UNMODIFIABLE VIEW
    ↓
Cannot modify through the view
    ↓
Original may still change
    ↓
Collections.unmodifiableX()


PERFORMANCE
    ↓
Choose according to dominant operation
    ↓
ArrayList → indexed access
HashSet → average constant-time uniqueness
TreeSet → sorted uniqueness
HashMap → average constant-time key lookup
TreeMap → sorted keys


CHOOSING
    ↓
Duplicates? → List
Unique? → Set
Key/value? → Map
Sorted? → Tree*
Insertion order? → LinkedHash*
Concurrent? → java.util.concurrent
Read-heavy concurrent list? → CopyOnWrite*
Producer/consumer? → BlockingQueue
Both ends? → Deque
Priority? → PriorityQueue
```

## 🔥 The seven rules to permanently remember

1. **`ArrayList`** → default general-purpose List; excellent indexed access.
2. **`HashSet`** → uniqueness with fast average lookup; no order guarantee.
3. **`TreeSet`** → uniqueness + sorted order.
4. **`HashMap`** → general-purpose key/value lookup.
5. **`TreeMap`** → key/value + sorted keys.
6. **`ConcurrentHashMap`** → concurrent key/value access.
7. **`CopyOnWriteArrayList`** → concurrent, read-heavy workloads where writes are relatively rare.

And the most important conceptual distinction:

> **Fail-fast is about detecting problematic modification during iteration; concurrent collections are about safely supporting concurrent access; synchronization is about coordinating shared mutable access; immutability is about preventing mutation altogether.**
