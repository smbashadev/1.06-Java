# 7. Deque in Java — TEACHME

> **Important training rule:** We will learn the Java Collections Framework **without Generics** in the programs.
> So you will see normal syntax such as `ArrayDeque d = new ArrayDeque();` and `LinkedList l = new LinkedList();`.

---

# 1. First Understand the Problem

Imagine you have a line of people:

```text
10 → 20 → 30 → 40
```

There are two ends:

```text
FRONT                         REAR
  ↓                             ↓
 10 → 20 → 30 → 40
```

Now suppose I tell you:

* You can add a person at the front.
* You can add a person at the rear.
* You can remove a person from the front.
* You can remove a person from the rear.

What kind of data structure do you need?

**Deque.**

---

# 2. What Does Deque Mean?

Deque is pronounced approximately:

> **"Deck"**

It stands for:

> **Double Ended Queue**

Break the name:

```text
Double
   ↓
Two ends

Ended
   ↓
Operations at the ends

Queue
   ↓
Elements are maintained in a sequence
```

So:

> **A Deque is a data structure that allows insertion and removal of elements from both ends.**

---

# 3. Visualize Deque

Imagine this:

```text
             FRONT                    REAR
               ↓                       ↓
            ┌────┬────┬────┬────┐
            │ 10 │ 20 │ 30 │ 40 │
            └────┴────┴────┴────┘
               ↑                       ↑
             END                     END
```

You can operate here:

```text
FRONT                         REAR
  ↓                             ↓
10 → 20 → 30 → 40
```

You can:

```text
add → FRONT
add → REAR

remove → FRONT
remove → REAR
```

That's the main idea behind Deque.

---

# 4. Where Does Deque Come in the Collection Framework?

Let's build the hierarchy slowly.

```text
Collection
     ↓
   Queue
     ↓
   Deque
```

`Deque` is an **interface**.

It is present in:

```java
java.util
```

So we normally import:

```java
import java.util.*;
```

---

# 5. Can We Create a Deque Object Directly?

No.

This is wrong:

```java
Deque d = new Deque();
```

Why?

Because `Deque` is an interface.

Instead, we need a class that implements `Deque`.

Two important classes for this topic are:

```text
Deque
  ↑
  ├── ArrayDeque
  │
  └── LinkedList
```

Therefore:

```java
ArrayDeque d = new ArrayDeque();
```

is valid.

And:

```java
LinkedList l = new LinkedList();
```

is also valid.

---

# 6. Let's Learn ArrayDeque First

The name itself gives us a clue:

```text
Array + Deque
```

`ArrayDeque` is a Java class designed to provide Deque functionality using an array-based internal structure.

Create an object:

```java
ArrayDeque d = new ArrayDeque();
```

Example:

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

# 7. Don't Memorize Methods Yet — Understand the Ends

Look at:

```text
FRONT                         REAR
  ↓                             ↓
10 → 20 → 30 → 40
```

There are four basic operations:

```text
                 DEQUE
                   |
          ┌────────┴────────┐
          ↓                 ↓
        FRONT              REAR
          |                 |
       INSERT             INSERT
       REMOVE             REMOVE
```

Java gives us methods for each operation.

---

# 8. Add at Front — `addFirst()`

Suppose the Deque is:

```text
20 → 30
```

We want to insert `10` at the front.

Use:

```java
d.addFirst(10);
```

Now:

```text
10 → 20 → 30
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addLast(20);
        d.addLast(30);

        d.addFirst(10);

        System.out.println(d);
    }
}
```

Output:

```text
[10, 20, 30]
```

### Remember

```text
addFirst()
    ↓
FRONT
```

---

# 9. Add at Rear — `addLast()`

Suppose:

```text
10 → 20
```

We want to add `30` at the rear.

Use:

```java
d.addLast(30);
```

Result:

```text
10 → 20 → 30
```

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

        System.out.println(d);
    }
}
```

Output:

```text
[10, 20, 30]
```

### Remember

```text
addLast()
    ↓
REAR
```

---

# 10. Now Learn Removal

Suppose:

```text
10 → 20 → 30
```

We can remove either:

```text
10
```

or:

```text
30
```

---

# 11. Remove from Front — `removeFirst()`

Use:

```java
d.removeFirst();
```

For:

```text
10 → 20 → 30
```

`10` is removed.

Remaining:

```text
20 → 30
```

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

        System.out.println(d);
    }
}
```

Output:

```text
10
[20, 30]
```

### Remember

```text
removeFirst()
      ↓
removes FRONT
```

---

# 12. Remove from Rear — `removeLast()`

Use:

```java
d.removeLast();
```

For:

```text
10 → 20 → 30
```

`30` is removed.

Remaining:

```text
10 → 20
```

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

        System.out.println(d.removeLast());

        System.out.println(d);
    }
}
```

Output:

```text
30
[10, 20]
```

### Remember

```text
removeLast()
      ↓
removes REAR
```

---

# 13. Four Methods You Must Know First

Don't worry about the other methods yet.

First master these four:

```text
addFirst()
addLast()

removeFirst()
removeLast()
```

Think:

```text
                  DEQUE
                    |
          ┌─────────┴─────────┐
          ↓                   ↓
        FRONT                REAR
          |                   |
     addFirst()           addLast()
     removeFirst()        removeLast()
```

If you understand this picture, you understand the fundamental purpose of Deque.

---

# 14. What If We Only Want to Look?

Sometimes we don't want to remove an element.

We just want to see it.

For example:

```text
10 → 20 → 30
```

We want to know:

> What is at the front?

Use:

```java
d.peekFirst();
```

Result:

```text
10
```

The element remains there.

---

# 15. `peekFirst()`

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

        System.out.println(d.peekFirst());

        System.out.println(d);
    }
}
```

Output:

```text
10
[10, 20, 30]
```

Notice:

```text
10
```

was **not removed**.

So:

```text
peekFirst()
     ↓
look at FRONT
     ↓
don't remove
```

---

# 16. `peekLast()`

Similarly:

```java
d.peekLast();
```

looks at the rear.

For:

```text
10 → 20 → 30
```

the result is:

```text
30
```

Example:

```java
System.out.println(d.peekLast());
```

Output:

```text
30
```

The Deque remains unchanged.

---

# 17. Now Learn the Whole Basic Picture

```text
                       DEQUE
                         |
             Double Ended Queue
                         |
              ┌──────────┴──────────┐
              ↓                     ↓
            FRONT                  REAR
              |                     |
       ┌──────┼──────┐       ┌──────┼──────┐
       ↓      ↓      ↓       ↓      ↓      ↓
   addFirst removeFirst peekFirst addLast removeLast peekLast
```

That's the heart of the topic.

---

# 18. Let's Build a Complete ArrayDeque Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        d.addFirst(20);
        d.addFirst(10);
        d.addLast(30);
        d.addLast(40);

        System.out.println("Deque: " + d);

        System.out.println("First: " + d.peekFirst());
        System.out.println("Last: " + d.peekLast());

        System.out.println("Removed First: " + d.removeFirst());
        System.out.println("Removed Last: " + d.removeLast());

        System.out.println("Deque: " + d);
    }
}
```

Output:

```text
Deque: [10, 20, 30, 40]
First: 10
Last: 40
Removed First: 10
Removed Last: 40
Deque: [20, 30]
```

Walk through it:

```text
addFirst(20)
→ [20]

addFirst(10)
→ [10, 20]

addLast(30)
→ [10, 20, 30]

addLast(40)
→ [10, 20, 30, 40]
```

Then:

```text
peekFirst()
→ 10

peekLast()
→ 40
```

Then:

```text
removeFirst()
→ removes 10

removeLast()
→ removes 40
```

Remaining:

```text
[20, 30]
```

---

# 19. ArrayDeque Can Behave Like a Queue

This is an important connection.

A Queue normally follows:

```text
FIFO
```

**First In, First Out**

Suppose:

```text
10 enters
20 enters
30 enters
```

We get:

```text
10 → 20 → 30
```

If we remove from the front:

```text
10
20
30
```

So with `ArrayDeque`, we can create Queue behavior using:

```text
addLast()
removeFirst()
```

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
        System.out.println(d.removeFirst());
        System.out.println(d.removeFirst());
    }
}
```

Output:

```text
10
20
30
```

That's FIFO.

---

# 20. ArrayDeque Can Behave Like a Stack

A Stack follows:

```text
LIFO
```

**Last In, First Out**

Suppose:

```text
10 enters
20 enters
30 enters
```

The last one entered is `30`.

So `30` should come out first.

Use:

```text
addFirst()
removeFirst()
```

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
        System.out.println(d.removeFirst());
        System.out.println(d.removeFirst());
    }
}
```

Output:

```text
30
20
10
```

That's LIFO.

---

# 21. One Deque — Two Behaviors

This is the beautiful part.

The same `ArrayDeque` can behave as:

### Queue

```text
addLast()
removeFirst()
```

```text
FIFO
```

### Stack

```text
addFirst()
removeFirst()
```

```text
LIFO
```

So:

```text
                  ArrayDeque
                      |
             ┌────────┴────────┐
             ↓                 ↓
           Queue              Stack
             |                 |
        FIFO behavior      LIFO behavior
```

---

# PART B — LINKEDLIST AS DEQUE

# 22. Now Let's Learn LinkedList

You already know `LinkedList` belongs to the Java Collections Framework.

But there is an important fact:

> **LinkedList also implements the Deque interface.**

Therefore it can be used as a Deque.

---

# 23. LinkedList as a Deque

You can write:

```java
LinkedList l = new LinkedList();
```

and use:

```java
l.addFirst();
l.addLast();

l.removeFirst();
l.removeLast();

l.peekFirst();
l.peekLast();
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedList l = new LinkedList();

        l.addFirst(10);
        l.addLast(20);
        l.addLast(30);

        System.out.println(l);
    }
}
```

Output:

```text
[10, 20, 30]
```

---

# 24. Why Can LinkedList Do This?

Because its class relationship includes `Deque`.

Simplified:

```text
Collection
     ↓
   Queue
     ↓
   Deque
     ↑
     |
LinkedList
```

More accurately, `LinkedList` implements both `List` and `Deque` (and therefore also `Queue` through `Deque`).

So a LinkedList can be used for different collection purposes.

---

# 25. LinkedList — Add at Front

```java
l.addFirst(10);
```

If:

```text
20 → 30
```

then:

```text
10 → 20 → 30
```

---

# 26. LinkedList — Add at Rear

```java
l.addLast(40);
```

If:

```text
10 → 20 → 30
```

then:

```text
10 → 20 → 30 → 40
```

---

# 27. LinkedList — Remove from Front

```java
l.removeFirst();
```

For:

```text
10 → 20 → 30
```

the result is:

```text
10 removed
```

Remaining:

```text
20 → 30
```

---

# 28. LinkedList — Remove from Rear

```java
l.removeLast();
```

For:

```text
10 → 20 → 30
```

the result is:

```text
30 removed
```

Remaining:

```text
10 → 20
```

---

# 29. LinkedList — Examine Both Ends

```java
l.peekFirst();
l.peekLast();
```

For:

```text
10 → 20 → 30
```

we get:

```text
peekFirst() → 10
peekLast()  → 30
```

Nothing is removed.

---

# 30. Complete LinkedList-as-Deque Program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedList l = new LinkedList();

        l.addFirst(20);
        l.addFirst(10);
        l.addLast(30);
        l.addLast(40);

        System.out.println("Deque: " + l);

        System.out.println("First: " + l.peekFirst());
        System.out.println("Last: " + l.peekLast());

        System.out.println("Removed First: " + l.removeFirst());
        System.out.println("Removed Last: " + l.removeLast());

        System.out.println("Deque: " + l);
    }
}
```

Output:

```text
Deque: [10, 20, 30, 40]
First: 10
Last: 40
Removed First: 10
Removed Last: 40
Deque: [20, 30]
```

---

# 31. The Big Difference: ArrayDeque vs LinkedList

Now compare them.

| Feature              | ArrayDeque | LinkedList |
| -------------------- | ---------- | ---------- |
| Class                | Yes        | Yes        |
| Implements Deque     | Yes        | Yes        |
| Insert at front      | Yes        | Yes        |
| Insert at rear       | Yes        | Yes        |
| Remove at front      | Yes        | Yes        |
| Remove at rear       | Yes        | Yes        |
| Duplicate elements   | Yes        | Yes        |
| `null`               | ❌ No       | ✅ Yes      |
| Can act as Queue     | Yes        | Yes        |
| Can act as Stack     | Yes        | Yes        |
| Also implements List | No         | Yes        |

---

# 32. The Most Important Difference — `null`

This is a very common exam question.

### ArrayDeque

```java
ArrayDeque d = new ArrayDeque();

d.add(null);
```

❌ Not allowed.

### LinkedList

```java
LinkedList l = new LinkedList();

l.add(null);
```

✅ Allowed.

So remember:

```text
ArrayDeque → null ❌

LinkedList → null ✅
```

---

# 33. Why Does ArrayDeque Reject `null`?

Think about this:

```java
d.peekFirst();
```

If the Deque is empty, it can return:

```text
null
```

Now imagine if `null` were also allowed as an actual element.

Then it would become difficult to distinguish:

```text
No element exists
```

from:

```text
The first element is null
```

Therefore, `ArrayDeque` does not allow `null`.

---

# 34. Does Deque Allow Duplicate Elements?

Yes.

For example:

```java
ArrayDeque d = new ArrayDeque();

d.addLast(10);
d.addLast(10);
d.addLast(20);

System.out.println(d);
```

Output:

```text
[10, 10, 20]
```

So:

```text
Deque
→ duplicates allowed
```

The same is true for LinkedList.

---

# 35. Does Deque Automatically Sort Elements?

**No.**

Suppose:

```java
d.addLast(30);
d.addLast(10);
d.addLast(20);
```

The Deque is:

```text
30 → 10 → 20
```

It does not automatically become:

```text
10 → 20 → 30
```

Remember:

> **Deque is about access from two ends, not sorting.**

---

# 36. Three Concepts You Must Not Mix

### Deque

```text
Access from both ends
```

### PriorityQueue

```text
Priority-based retrieval
```

### TreeSet

```text
Sorted Set
```

These are completely different concepts.

---

# 37. ArrayDeque vs LinkedList — Simple Real-Life Analogy

Imagine two different containers.

### ArrayDeque

Think:

> A specially designed container for efficient two-ended operations.

### LinkedList

Think:

> A linked-list container that happens to also provide two-ended Queue/Deque operations.

Therefore:

```text
Need primarily a Deque?
        ↓
   ArrayDeque
```

```text
Need List functionality + Deque functionality?
        ↓
     LinkedList
```

---

# 38. Why Is ArrayDeque Usually Preferred for Deque?

If your requirement is simply:

> "I need a Deque."

then `ArrayDeque` is generally the natural choice.

Why?

Because it is specifically designed as a Deque implementation and avoids the per-element node overhead associated with a linked-list structure.

But don't turn this into:

> "ArrayDeque is always better."

If you need `LinkedList`'s List operations or its ability to store `null`, LinkedList may be useful.

---

# 39. One Important Point About `Stack`

You may wonder:

> "If ArrayDeque can behave like a Stack, why do we have the Stack class?"

Historically Java has the `Stack` class.

But for new code, `Deque`/`ArrayDeque` is generally preferred for stack behavior.

You can think:

```text
Old-style Stack
       ↓
java.util.Stack

Modern stack-style approach
       ↓
Deque
       ↓
ArrayDeque
```

For your Collections Framework learning, understand the behavioral relationship:

```text
Deque can provide LIFO behavior.
```

---

# 40. Empty Deque — Important Difference

Suppose:

```java
ArrayDeque d = new ArrayDeque();
```

There is nothing inside.

Now:

```java
d.peekFirst();
```

returns:

```text
null
```

But:

```java
d.removeFirst();
```

throws an exception because there is nothing to remove.

This gives us a useful pair:

```text
peekFirst()
→ safely examines

removeFirst()
→ removes and requires an element
```

Similarly:

```text
peekLast()
→ safely examines

removeLast()
→ removes and requires an element
```

---

# 41. `pollFirst()` — Another Removal Method

There is another useful method:

```java
pollFirst()
```

Difference:

```text
removeFirst()
→ exception-oriented when empty

pollFirst()
→ returns null when empty
```

Example:

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayDeque d = new ArrayDeque();

        System.out.println(d.pollFirst());
    }
}
```

Output:

```text
null
```

---

# 42. `pollLast()`

Similarly:

```java
d.pollLast();
```

removes the rear element.

For an empty Deque:

```text
pollLast()
→ null
```

So:

```text
removeFirst() ↔ pollFirst()
removeLast()  ↔ pollLast()
```

The important distinction is their empty-Deque behavior.

---

# 43. `offerFirst()` and `offerLast()`

There are also insertion alternatives:

```java
offerFirst()
offerLast()
```

For example:

```java
ArrayDeque d = new ArrayDeque();

d.offerFirst(10);
d.offerLast(20);

System.out.println(d);
```

Output:

```text
[10, 20]
```

For learning purposes, first remember:

```text
addFirst()
addLast()
```

Then learn:

```text
offerFirst()
offerLast()
```

as the Queue/Deque-style insertion alternatives.

---

# 44. The Three Method Families

This is a very useful way to organize the methods in your brain.

## INSERT

```text
addFirst()
addLast()

offerFirst()
offerLast()
```

## REMOVE

```text
removeFirst()
removeLast()

pollFirst()
pollLast()
```

## EXAMINE

```text
getFirst()
getLast()

peekFirst()
peekLast()
```

Visualize:

```text
                   DEQUE METHODS
                         |
          ┌──────────────┼──────────────┐
          ↓              ↓              ↓
       INSERT          REMOVE        EXAMINE
          |              |              |
       addFirst       removeFirst    getFirst
       addLast        removeLast     getLast
       offerFirst     pollFirst      peekFirst
       offerLast      pollLast       peekLast
```

---

# 45. A Complete Teaching Example

Let's imagine a line:

```text
10 → 20 → 30
```

We execute:

```java
d.addFirst(5);
```

Now:

```text
5 → 10 → 20 → 30
```

Then:

```java
d.addLast(40);
```

Now:

```text
5 → 10 → 20 → 30 → 40
```

Then:

```java
d.removeFirst();
```

Result:

```text
10 → 20 → 30 → 40
```

Then:

```java
d.removeLast();
```

Result:

```text
10 → 20 → 30
```

That's exactly what **double-ended** means.

---

# 46. The Name Helps You Remember Everything

Don't memorize randomly.

Break every method name:

```text
add + First
add + Last

remove + First
remove + Last

peek + First
peek + Last
```

The suffix tells you **where** the operation happens.

```text
First → FRONT

Last → REAR
```

Therefore:

```text
addFirst()
→ add at FRONT

addLast()
→ add at REAR

removeFirst()
→ remove FRONT

removeLast()
→ remove REAR
```

---

# 47. ArrayDeque vs LinkedList — Final Mental Model

```text
                    DEQUE
                      |
          ┌───────────┴───────────┐
          ↓                       ↓
     ArrayDeque              LinkedList
          |                       |
    resizable array          linked nodes
          |                       |
    null not allowed          null allowed
          |                       |
          └───────────┬───────────┘
                      ↓
              Double-ended access
                      |
             ┌────────┴────────┐
             ↓                 ↓
           FRONT              REAR
             |                 |
         addFirst()         addLast()
         removeFirst()      removeLast()
         peekFirst()        peekLast()
```

---

# 48. Interview / Exam Questions You Should Be Able to Answer

### Q1. What is Deque?

> Deque stands for Double Ended Queue. It allows insertion, removal, and examination of elements at both ends.

### Q2. Is Deque a class?

> No. `Deque` is an interface.

### Q3. Name two classes that can be used as Deque.

> `ArrayDeque` and `LinkedList`.

### Q4. Does ArrayDeque allow `null`?

> No.

### Q5. Does LinkedList allow `null`?

> Yes.

### Q6. How do you insert at the front?

```java
addFirst()
```

### Q7. How do you insert at the rear?

```java
addLast()
```

### Q8. How do you remove from the front?

```java
removeFirst()
```

### Q9. How do you remove from the rear?

```java
removeLast()
```

### Q10. How can ArrayDeque behave like a Queue?

```text
addLast()
removeFirst()
```

### Q11. How can ArrayDeque behave like a Stack?

```text
addFirst()
removeFirst()
```

### Q12. Does Deque automatically sort elements?

> No.

---

# 49. Final TEACHME Summary

If you remember only one picture from this lesson, remember this:

```text
                         DEQUE
                  Double Ended Queue
                           |
              ┌────────────┴────────────┐
              ↓                         ↓
          ArrayDeque                LinkedList
              |                         |
       array-based idea          linked-node idea
              |                         |
       null ❌                   null ✅
              |                         |
              └────────────┬────────────┘
                           ↓
                 ┌─────────┴─────────┐
                 ↓                   ↓
               FRONT               REAR
                 |                   |
            addFirst()          addLast()
            removeFirst()       removeLast()
            peekFirst()         peekLast()
```

And remember the two behaviors:

```text
QUEUE
addLast() → removeFirst()
     ↓
   FIFO
```

```text
STACK
addFirst() → removeFirst()
     ↓
   LIFO
```

### The one sentence to remember

> **Deque is a double-ended queue where both the front and rear are available for insertion and removal; `ArrayDeque` and `LinkedList` are two important classes that can provide this behavior.**

**No Generics are used in any program in this TEACHME lesson.**
