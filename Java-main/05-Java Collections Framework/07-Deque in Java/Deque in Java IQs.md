# 7. Deque in Java — DOUBTKILLER

This section is designed specifically to **remove the common confusions and traps** around `Deque`, `ArrayDeque`, and `LinkedList as Deque`.

> **Training rule:** No Generics. All programs use normal/raw collection syntax.

---

# 1. BIGGEST DOUBT: What exactly is a Deque?

**Deque** stands for:

> **Double Ended Queue**

It means a collection where we can perform insertion and removal from **both ends**.

```text
             FRONT                         REAR
               ↓                            ↓
             10 → 20 → 30 → 40
               ↑                            ↑
          operations                    operations
```

Therefore:

```text
Deque
 ↓
Two ends available
 ↓
FRONT + REAR
```

### Don't make this mistake

Deque does **not** mean:

> "A queue that only removes from both ends."

It supports operations such as:

* insertion at both ends
* removal at both ends
* examination of both ends

---

# 2. BIGGEST DOUBT: Is Deque a Class or Interface?

`Deque` is an **interface**.

Therefore this is wrong:

```java
Deque d = new Deque();
```

You cannot directly create an object of an interface.

Instead, use an implementation class.

For example:

```java
Deque d = new ArrayDeque();
```

or:

```java
Deque d = new LinkedList();
```

And, following your training rule, there are **no generic type parameters** here.

---

# 3. BIGGEST DOUBT: What Are the Important Implementations?

For this topic, concentrate on:

```text
Deque
  ↑
  ├── ArrayDeque
  │
  └── LinkedList
```

So:

```java
ArrayDeque d = new ArrayDeque();
```

and:

```java
LinkedList l = new LinkedList();
```

can both be used for Deque operations.

---

# 4. BIGGEST DOUBT: Why Does LinkedList Appear Under Deque?

This confuses many students.

You may already know:

```text
List
 ↓
LinkedList
```

Then suddenly you see:

```text
Deque
 ↓
LinkedList
```

What's happening?

`LinkedList` implements **both `List` and `Deque`**.

Conceptually:

```text
                 LinkedList
                /          \
               ↓            ↓
             List          Deque
                            ↓
                          Queue
```

Therefore the same `LinkedList` object can be used for List operations **and** Deque operations.

---

# 5. BIGGEST DOUBT: Is ArrayDeque a Subclass of LinkedList?

**No.**

They are two different classes.

```text
Deque
 ↑   ↑
 |   |
 |   └── LinkedList
 |
 └────── ArrayDeque
```

Do not think:

```text
ArrayDeque → LinkedList
```

That is wrong.

---

# 6. BIGGEST DOUBT: What Does "Both Ends" Actually Mean?

Suppose:

```text
10 → 20 → 30 → 40
```

There are two ends:

```text
FRONT                         REAR
  ↓                             ↓
10 → 20 → 30 → 40
```

You can add:

```text
FRONT
  ↓
5 → 10 → 20 → 30 → 40
```

or:

```text
10 → 20 → 30 → 40 → 50
                              ↑
                             REAR
```

You can also remove:

```text
remove FRONT
```

or:

```text
remove REAR
```

That is the meaning of **double-ended**.

---

# 7. BIGGEST DOUBT: `addFirst()` vs `addLast()`

This is extremely simple once you connect the names.

### `addFirst()`

```java
d.addFirst(10);
```

Means:

> Add at the first/front end.

### `addLast()`

```java
d.addLast(10);
```

Means:

> Add at the last/rear end.

Remember:

```text
First → FRONT
Last  → REAR
```

---

# 8. BIGGEST DOUBT: `removeFirst()` vs `removeLast()`

Same naming rule.

```java
d.removeFirst();
```

means:

> Remove from front.

```java
d.removeLast();
```

means:

> Remove from rear.

Visual:

```text
removeFirst()                 removeLast()
      ↓                             ↓
    FRONT                          REAR
      ↓                             ↓
    10 → 20 → 30 → 40
```

---

# 9. BIGGEST DOUBT: `peekFirst()` Does It Remove?

**No.**

`peekFirst()` only looks at the first element.

Suppose:

```text
10 → 20 → 30
```

Then:

```java
System.out.println(d.peekFirst());
```

gives:

```text
10
```

but the Deque remains:

```text
10 → 20 → 30
```

Therefore:

```text
peek = look
```

not:

```text
peek = remove
```

---

# 10. BIGGEST DOUBT: `peekLast()` Does It Remove?

Again:

**No.**

```java
d.peekLast();
```

only examines the last element.

For:

```text
10 → 20 → 30
```

it returns:

```text
30
```

but the Deque remains unchanged.

---

# 11. THE MOST IMPORTANT METHOD TABLE

| Method          | Meaning           |
| --------------- | ----------------- |
| `addFirst()`    | Insert at front   |
| `addLast()`     | Insert at rear    |
| `removeFirst()` | Remove from front |
| `removeLast()`  | Remove from rear  |
| `peekFirst()`   | View front        |
| `peekLast()`    | View rear         |

Memorize this:

```text
             DEQUE
               |
       ┌───────┴───────┐
       ↓               ↓
     FRONT            REAR
       |               |
 addFirst()        addLast()
 removeFirst()     removeLast()
 peekFirst()       peekLast()
```

---

# 12. BIGGEST DOUBT: Does Deque Follow FIFO?

**Not necessarily.**

This is a very important distinction.

A normal Queue is generally used with:

```text
FIFO
```

But a Deque supports operations at **both ends**.

Therefore the behavior depends on **which methods you use**.

For example:

```java
d.addLast(10);
d.addLast(20);
d.addLast(30);
```

and:

```java
d.removeFirst();
```

produces:

```text
10
20
30
```

This gives FIFO behavior.

But Deque itself is not restricted to FIFO.

---

# 13. BIGGEST DOUBT: Can Deque Behave Like a Stack?

**Yes.**

Use the same end for insertion and removal.

For example:

```java
d.addFirst(10);
d.addFirst(20);
d.addFirst(30);
```

Then:

```java
d.removeFirst();
```

returns:

```text
30
```

then:

```text
20
10
```

That's:

```text
LIFO
```

So:

```text
Deque
 ├── can provide FIFO behavior
 └── can provide LIFO behavior
```

---

# 14. Queue Behavior vs Stack Behavior

### Queue-style Deque

```text
addLast()
removeFirst()
```

Result:

```text
FIFO
```

### Stack-style Deque

```text
addFirst()
removeFirst()
```

Result:

```text
LIFO
```

Don't say:

> "Deque means LIFO."

Wrong.

Don't say:

> "Deque means FIFO."

Also wrong.

The important point is:

> **Deque supports operations at both ends, so it can be used to implement either queue-like or stack-like behavior.**

---

# 15. BIGGEST DOUBT: Does ArrayDeque Allow Duplicates?

**Yes.**

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addLast(10);
        d.addLast(10);
        d.addLast(20);

        System.out.println(d);
    }
}
```

Output:

```text
[10, 10, 20]
```

Therefore:

```text
ArrayDeque
   ↓
duplicates allowed
```

---

# 16. BIGGEST DOUBT: Does ArrayDeque Allow `null`?

**No.**

This is a very important fact.

```java
ArrayDeque d = new ArrayDeque();

d.add(null);
```

This results in a `NullPointerException`.

Remember:

```text
ArrayDeque
 ├── duplicates ✅
 └── null ❌
```

---

# 17. BIGGEST DOUBT: Does LinkedList Allow `null`?

**Yes.**

```java
LinkedList l = new LinkedList();

l.add(null);

System.out.println(l);
```

Output:

```text
[null]
```

Therefore:

```text
LinkedList
 ├── duplicates ✅
 └── null ✅
```

---

# 18. BIGGEST DOUBT: Why Does ArrayDeque Reject `null`?

This is mainly important for understanding the API behavior.

Consider:

```java
d.peekFirst();
```

If the Deque is empty, a peek operation can indicate no element by returning:

```text
null
```

If `null` were also a valid stored element, distinguishing these situations would be problematic.

So `ArrayDeque` prohibits `null`.

### Exam answer

> `ArrayDeque` does not permit `null` elements.

That's enough unless the interviewer asks for the reasoning.

---

# 19. BIGGEST DOUBT: Does LinkedList Automatically Become a Deque?

No.

The class supports the Deque operations because `LinkedList` **implements the `Deque` interface**.

You can therefore use:

```java
LinkedList l = new LinkedList();
```

and call:

```java
l.addFirst(10);
l.addLast(20);
```

The methods are available because LinkedList provides Deque functionality.

---

# 20. BIGGEST DOUBT: ArrayDeque vs LinkedList

This is one of the most important comparisons.

| Point               | ArrayDeque | LinkedList |
| ------------------- | ---------- | ---------- |
| Class               | Yes        | Yes        |
| Can act as Deque    | Yes        | Yes        |
| Front insertion     | Yes        | Yes        |
| Rear insertion      | Yes        | Yes        |
| Front removal       | Yes        | Yes        |
| Rear removal        | Yes        | Yes        |
| Duplicates          | Yes        | Yes        |
| `null`              | No         | Yes        |
| List functionality  | No         | Yes        |
| Deque functionality | Yes        | Yes        |

---

# 21. BIGGEST DOUBT: If Both Can Be Deques, Why Have Two Classes?

Because they have different underlying designs and capabilities.

### ArrayDeque

Designed specifically around Deque operations using a resizable array internally.

### LinkedList

Uses a linked-node structure and additionally provides List functionality.

So if you primarily need:

> **Deque operations**

`ArrayDeque` is generally the natural implementation.

If you need:

> **List + Deque functionality**

`LinkedList` may be appropriate.

---

# 22. BIGGEST DOUBT: Which One Is Faster?

Don't memorize a simplistic statement such as:

> "ArrayDeque is always faster."

That's too broad.

For ordinary Deque use, `ArrayDeque` is generally preferred because it is purpose-built for Deque operations and has good memory/cache characteristics.

`LinkedList` has node objects and links between nodes, which introduces additional memory overhead.

For your training notes, remember:

```text
Deque requirement
      ↓
ArrayDeque is generally preferred
```

But if you specifically need LinkedList's additional List behavior, use LinkedList.

---

# 23. BIGGEST DOUBT: Can ArrayDeque Store Objects?

Yes.

Collections store objects.

With your **no-generics training approach**, you can write:

```java
ArrayDeque d = new ArrayDeque();

d.add(10);
d.add("Hello");
d.add(20.5);

System.out.println(d);
```

The raw collection can accept different reference types because elements are handled as `Object`.

However, mixing unrelated types is generally poor practice in real-world code.

For this training series, we're intentionally avoiding Generics as requested.

---

# 24. BIGGEST DOUBT: Does Deque Sort Elements?

**No.**

Suppose:

```java
d.addLast(30);
d.addLast(10);
d.addLast(20);
```

The order remains based on insertion/Deque operations:

```text
30 → 10 → 20
```

It does not automatically become:

```text
10 → 20 → 30
```

So:

```text
Deque ≠ Sorted collection
```

---

# 25. BIGGEST DOUBT: Is Deque a Set?

No.

Deque and Set have completely different purposes.

### Deque

```text
sequence + two-ended operations
```

### Set

```text
unique elements
```

A Deque can contain:

```text
10 → 10 → 20
```

A Set does not allow duplicate elements.

---

# 26. BIGGEST DOUBT: What Happens When Deque Is Empty?

This is important.

Consider:

```java
ArrayDeque d = new ArrayDeque();
```

There are no elements.

### `peekFirst()`

```java
d.peekFirst();
```

returns:

```text
null
```

### `peekLast()`

```java
d.peekLast();
```

returns:

```text
null
```

But:

### `removeFirst()`

```java
d.removeFirst();
```

throws an exception because there is no element to remove.

Similarly:

```java
d.removeLast();
```

throws an exception when the Deque is empty.

---

# 27. `remove` vs `poll` — A Very Common Doubt

There are two families of removal methods.

```text
removeFirst()
pollFirst()

removeLast()
pollLast()
```

The major difference is their empty-Deque behavior.

| Method          | Empty Deque      |
| --------------- | ---------------- |
| `removeFirst()` | throws exception |
| `pollFirst()`   | returns `null`   |
| `removeLast()`  | throws exception |
| `pollLast()`    | returns `null`   |

Example:

```java
ArrayDeque d = new ArrayDeque();

System.out.println(d.pollFirst());
```

Output:

```text
null
```

But:

```java
d.removeFirst();
```

throws an exception.

---

# 28. `add` vs `offer` — Another Doubt

You will also see:

```text
addFirst()
offerFirst()

addLast()
offerLast()
```

Both are insertion methods.

For an `ArrayDeque`, both are normally successful when there is sufficient memory, because it is resizable.

The important conceptual distinction comes from the Queue API convention:

```text
add...
→ insertion method whose failure convention is exception-based

offer...
→ insertion method whose failure convention is return-value based
```

For `ArrayDeque`, capacity is not fixed, so this distinction usually doesn't matter in ordinary use.

---

# 29. `getFirst()` vs `peekFirst()`

Another common trap.

Both examine the first element.

```text
getFirst()
peekFirst()
```

But their behavior when the Deque is empty differs.

| Method        | Empty Deque |
| ------------- | ----------- |
| `getFirst()`  | exception   |
| `peekFirst()` | `null`      |

Similarly:

```text
getLast()
peekLast()
```

---

# 30. The Three Families — DOUBTKILLER

Memorize this table:

| Operation     | Exception-style | Special-value-style |
| ------------- | --------------- | ------------------- |
| Insert front  | `addFirst()`    | `offerFirst()`      |
| Insert rear   | `addLast()`     | `offerLast()`       |
| Remove front  | `removeFirst()` | `pollFirst()`       |
| Remove rear   | `removeLast()`  | `pollLast()`        |
| Examine front | `getFirst()`    | `peekFirst()`       |
| Examine rear  | `getLast()`     | `peekLast()`        |

This table eliminates a lot of confusion.

---

# 31. BIGGEST DOUBT: Is `ArrayDeque` a Queue?

`ArrayDeque` is a **class** that implements `Deque`.

Since `Deque` extends `Queue`, it can also be used wherever Queue behavior is required.

Conceptually:

```text
Queue
  ↑
Deque
  ↑
ArrayDeque
```

So it can provide Queue behavior.

But its more powerful capability is:

> **operations at both ends.**

---

# 32. BIGGEST DOUBT: Is LinkedList Only a List?

No.

This is one of the most important facts about `LinkedList`.

It can be used as:

```text
List
Queue
Deque
```

Conceptually:

```text
                 LinkedList
                /     |      \
               /      |       \
             List    Queue    Deque
```

The exact interface inheritance means its Deque implementation also provides Queue behavior.

---

# 33. Practical Trap — This Is Valid

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedList l = new LinkedList();

        l.addFirst(10);
        l.addLast(20);

        System.out.println(l);
    }
}
```

Output:

```text
[10, 20]
```

Many beginners think:

> "Why am I able to call `addFirst()` on LinkedList? I thought that was a Deque method."

Because:

> `LinkedList` implements `Deque`.

---

# 34. Practical Trap — This Is Also Valid

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addFirst(10);
        d.addLast(20);

        System.out.println(d);
    }
}
```

Output:

```text
[10, 20]
```

---

# 35. A Very Important Type-Reference Doubt

These are both valid:

```java
ArrayDeque d = new ArrayDeque();
```

and:

```java
Deque d = new ArrayDeque();
```

What's the difference?

### First

```java
ArrayDeque d
```

The reference type is the concrete class.

### Second

```java
Deque d
```

The reference type is the interface.

The second approach emphasizes programming to the interface.

For beginner training, understand both.

---

# 36. Can We Do This?

```java
Deque d = new Deque();
```

**No.**

Because:

```text
Deque = interface
```

But:

```java
Deque d = new ArrayDeque();
```

is valid.

And:

```java
Deque d = new LinkedList();
```

is valid.

---

# 37. Practical Program — Both Implementations

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Deque d1 = new ArrayDeque();
        Deque d2 = new LinkedList();

        d1.addFirst(10);
        d1.addLast(20);

        d2.addFirst(30);
        d2.addLast(40);

        System.out.println(d1);
        System.out.println(d2);
    }
}
```

Output:

```text
[10, 20]
[30, 40]
```

The same interface can refer to different implementation objects.

---

# 38. BIGGEST DOUBT: Which One Allows `null`?

Remember this one-line answer:

```text
ArrayDeque → NO null
LinkedList → YES null
```

This is a favorite conceptual question.

---

# 39. BIGGEST DOUBT: Which One Allows Duplicates?

Both.

```text
ArrayDeque → duplicates YES
LinkedList → duplicates YES
```

Don't confuse Deque with Set.

---

# 40. BIGGEST DOUBT: Which One Maintains Insertion Order?

Both preserve the sequence represented by their Deque operations.

For example:

```java
d.addLast(10);
d.addLast(20);
d.addLast(30);
```

you see:

```text
[10, 20, 30]
```

But don't interpret this as:

> "Deque is a sorting mechanism."

It isn't.

---

# 41. BIGGEST DOUBT: Is `ArrayDeque` Thread-Safe?

No.

`ArrayDeque` is not synchronized.

If multiple threads need shared concurrent access, separate concurrency considerations are required.

For your basic Collections Framework notes:

```text
ArrayDeque
→ not synchronized
```

---

# 42. BIGGEST DOUBT: Is LinkedList Thread-Safe?

No.

`LinkedList` is also not synchronized.

So:

```text
ArrayDeque → not synchronized
LinkedList  → not synchronized
```

Don't confuse this with `Vector` or `Hashtable`, which are legacy synchronized collections.

---

# 43. BIGGEST DOUBT: Can ArrayDeque Be Used Instead of Stack?

Yes, for stack behavior.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addFirst(10);
        d.addFirst(20);
        d.addFirst(30);

        System.out.println(d.removeFirst());
    }
}
```

Output:

```text
30
```

This gives LIFO behavior.

---

# 44. BIGGEST DOUBT: Can ArrayDeque Be Used Instead of Queue?

Yes.

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addLast(10);
        d.addLast(20);
        d.addLast(30);

        System.out.println(d.removeFirst());
    }
}
```

Output:

```text
10
```

This gives FIFO behavior.

---

# 45. The Golden Rule

Don't learn:

```text
Deque = Queue
```

Instead learn:

```text
Deque
 ↓
Double-ended structure
 ↓
Can operate at FRONT and REAR
```

Then Queue and Stack behaviors are simply **ways of using those ends**.

---

# 46. ArrayDeque — Complete Mental Test

If I give you:

```java
ArrayDeque d = new ArrayDeque();

d.addLast(10);
d.addLast(20);
d.addFirst(5);
d.addLast(30);
```

What is the Deque?

Start:

```text
[]
```

After:

```text
addLast(10)
```

```text
[10]
```

After:

```text
addLast(20)
```

```text
[10, 20]
```

After:

```text
addFirst(5)
```

```text
[5, 10, 20]
```

After:

```text
addLast(30)
```

```text
[5, 10, 20, 30]
```

Now:

```java
d.removeFirst();
```

removes:

```text
5
```

Remaining:

```text
[10, 20, 30]
```

Then:

```java
d.removeLast();
```

removes:

```text
30
```

Remaining:

```text
[10, 20]
```

If you can trace this correctly, you understand the fundamental Deque operations.

---

# 47. LinkedList-as-Deque — Complete Mental Test

Start:

```java
LinkedList l = new LinkedList();
```

Then:

```java
l.addLast(10);
l.addLast(20);
l.addFirst(5);
l.addLast(30);
```

Result:

```text
[5, 10, 20, 30]
```

Then:

```java
l.removeFirst();
```

Result:

```text
[10, 20, 30]
```

Then:

```java
l.removeLast();
```

Result:

```text
[10, 20]
```

Exactly the same Deque operations are available.

---

# 48. DOUBTKILLER — Rapid Fire

### Is Deque a class?

❌ No.

### Is Deque an interface?

✅ Yes.

### Is ArrayDeque a class?

✅ Yes.

### Is LinkedList a class?

✅ Yes.

### Can ArrayDeque implement Deque functionality?

✅ Yes.

### Can LinkedList implement Deque functionality?

✅ Yes.

### Can ArrayDeque store duplicates?

✅ Yes.

### Can LinkedList store duplicates?

✅ Yes.

### Can ArrayDeque store `null`?

❌ No.

### Can LinkedList store `null`?

✅ Yes.

### Does Deque automatically sort?

❌ No.

### Does Deque mean FIFO only?

❌ No.

### Can Deque provide FIFO behavior?

✅ Yes.

### Can Deque provide LIFO behavior?

✅ Yes.

### Can LinkedList be used as both List and Deque?

✅ Yes.

### Can `new Deque()` be written?

❌ No.

### Can `new ArrayDeque()` be written?

✅ Yes.

### Can `new LinkedList()` be written?

✅ Yes.

---

# 49. FINAL DOUBTKILLER MAP

```text
                         DEQUE
                           |
                    Interface
                           |
            Double Ended Queue
                           |
              ┌────────────┴────────────┐
              ↓                         ↓
         ArrayDeque                 LinkedList
              |                         |
        Deque operations           Deque operations
              |                         |
       null ❌                    null ✅
       duplicate ✅               duplicate ✅
              |                         |
              └────────────┬────────────┘
                           ↓
                 FRONT              REAR
                   |                  |
             addFirst()          addLast()
             removeFirst()       removeLast()
             peekFirst()         peekLast()
```

### And the ultimate distinction:

```text
              DEQUE
                |
       ┌────────┴────────┐
       ↓                 ↓
 Queue-style          Stack-style
       ↓                 ↓
addLast()             addFirst()
removeFirst()         removeFirst()
       ↓                 ↓
     FIFO               LIFO
```

> **The one concept that eliminates almost every Deque doubt:**
> **Deque is not defined by FIFO or LIFO. It is defined by having two accessible ends. FIFO and LIFO are behaviors you can create by choosing which end you insert into and which end you remove from.**
