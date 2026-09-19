# 4. List in Java — TEACHME

> **Important training rule:** We will use **NO Generics** in this entire explanation.
> Generics belong to your separate **Topic 14 — Generics**.
> So all programs use normal/raw syntax such as:
>
> ```java
> ArrayList al = new ArrayList();
> ```

Let's learn `List` as if we're building the concept from zero.

---

# 1. First understand the problem

Suppose you want to store 5 student marks.

You could use an array:

```java
int marks[] = new int[5];
```

That's fine if you already know:

> "I need exactly 5 elements."

But what if you don't know how many elements you need?

Maybe:

```text
5 students
50 students
500 students
5000 students
```

An array is not convenient because its size is fixed after creation.

That's where the **Collections Framework** becomes useful.

And one of the most important parts of the Collections Framework is:

# `List`

---

# 2. What exactly is List?

Think of a List as a **flexible ordered container**.

Imagine a queue of numbers:

```text
10
20
30
40
```

The List remembers their order.

So:

```java
List l = new ArrayList();

l.add(10);
l.add(20);
l.add(30);
l.add(40);
```

produces:

```text
[10, 20, 30, 40]
```

The order in which you add elements is maintained.

---

# 3. Three things you should immediately remember about List

Whenever you hear **List**, think:

```text
LIST
 ↓
ORDER
DUPLICATES
INDEX
```

### 1. Order

```text
10
20
30
```

stays:

```text
[10, 20, 30]
```

### 2. Duplicates

```java
l.add(10);
l.add(20);
l.add(10);
```

is perfectly valid:

```text
[10, 20, 10]
```

### 3. Index

Every element has a position.

```text
Element:  10   20   30
Index:     0    1    2
```

So:

```java
l.get(1);
```

returns:

```text
20
```

---

# 4. Where does List come from?

`List` is an **interface**.

It belongs to:

```java
java.util
```

So we normally write:

```java
import java.util.*;
```

The basic hierarchy is:

```text
Collection
    ↑
   List
    ↑
    ├── ArrayList
    ├── LinkedList
    └── Vector
          ↑
        Stack
```

Don't worry about memorizing everything yet.

We'll understand each one individually.

---

# 5. Why is List an interface?

This is an important Java concept.

You cannot normally create an object directly from an interface.

So this is wrong:

```java
List l = new List();
```

❌ `List` is an interface.

Instead, we create an object of a class that implements List:

```java
List l = new ArrayList();
```

or:

```java
List l = new LinkedList();
```

or:

```java
List l = new Vector();
```

The idea is:

```text
List
 ↓
Rules / contract

ArrayList
 ↓
Implementation

LinkedList
 ↓
Implementation

Vector
 ↓
Implementation
```

---

# 6. Your first List program

Let's write the simplest possible program.

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        List l = new ArrayList();

        l.add(10);
        l.add(20);
        l.add(30);

        System.out.println(l);
    }
}
```

Output:

```text
[10, 20, 30]
```

Now let's understand every line.

### Line 1

```java
import java.util.*;
```

We import collection classes and interfaces.

### Line 2

```java
List l = new ArrayList();
```

We create an ArrayList object and store its reference in a List reference.

### Then:

```java
l.add(10);
```

Adds `10`.

Then:

```java
l.add(20);
```

Adds `20`.

Then:

```java
l.add(30);
```

Adds `30`.

Finally:

```java
System.out.println(l);
```

prints the List.

---

# 7. Why not simply use ArrayList everywhere?

You might ask:

> "If ArrayList is doing the actual work, why write `List l`?"

Excellent question.

Because `List` represents the **general behavior**, while `ArrayList` represents one implementation.

For example:

```java
List l = new ArrayList();
```

Later, you could change:

```java
List l = new LinkedList();
```

without changing code that only depends on List behavior.

This is an example of **programming to an interface**.

You don't need to master that phrase right now. Just remember:

```text
List = common contract
ArrayList = one implementation
LinkedList = another implementation
Vector = another implementation
```

---

# 8. List allows duplicates

Let's test it.

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        List l = new ArrayList();

        l.add(10);
        l.add(20);
        l.add(10);
        l.add(30);
        l.add(20);

        System.out.println(l);
    }
}
```

Output:

```text
[10, 20, 10, 30, 20]
```

Notice:

```text
10 → appears twice
20 → appears twice
```

That's allowed.

This is one major difference between:

```text
List → duplicates allowed
Set  → duplicates not allowed
```

We'll study Set later.

---

# 9. List maintains insertion order

Suppose I write:

```java
l.add(50);
l.add(10);
l.add(30);
l.add(20);
```

The List is:

```text
[50, 10, 30, 20]
```

It doesn't automatically become:

```text
[10, 20, 30, 50]
```

because:

> **List maintains insertion order; it does not automatically sort.**

This distinction is extremely important.

---

# 10. Understanding index

Let's say:

```text
[100, 200, 300, 400]
```

The positions are:

```text
Value:    100   200   300   400
Index:      0     1     2     3
```

Remember:

> Java indexes normally start from `0`.

So:

```java
l.get(0);
```

returns:

```text
100
```

and:

```java
l.get(2);
```

returns:

```text
300
```

---

# 11. `get()` — "Give me the element"

Suppose:

```java
List l = new ArrayList();

l.add(10);
l.add(20);
l.add(30);
```

Now:

```java
System.out.println(l.get(1));
```

Output:

```text
20
```

Think:

```text
get(index)
    ↓
"Give me what's present at this position."
```

---

# 12. `add(index, element)` — "Insert here"

Suppose we have:

```text
[10, 20, 30]
```

Now:

```java
l.add(1, 50);
```

What does it mean?

It means:

> "Insert `50` at index 1."

Result:

```text
[10, 50, 20, 30]
```

Notice that `20` and `30` move.

---

# 13. `set(index, element)` — "Replace this"

Now suppose:

```text
[10, 20, 30]
```

and:

```java
l.set(1, 50);
```

Result:

```text
[10, 50, 30]
```

It **replaces** `20`.

This is extremely important:

```text
add(index, value)
       ↓
INSERT

set(index, value)
       ↓
REPLACE
```

---

# 14. Let's compare them

Start:

```text
[10, 20, 30]
```

### `add(1, 50)`

```text
[10, 50, 20, 30]
```

Number of elements increased.

### `set(1, 50)`

```text
[10, 50, 30]
```

Number of elements stayed the same.

So:

> **add = put a new element**

> **set = replace an existing element**

---

# 15. Removing an element

Suppose:

```text
[10, 20, 30]
```

Now:

```java
l.remove(1);
```

The element at index `1` is removed.

Result:

```text
[10, 30]
```

---

# 16. A famous List confusion

Look carefully:

```java
l.remove(1);
```

Does it mean:

```text
remove value 1
```

or:

```text
remove index 1
```

It means:

> **remove index 1**

because List has:

```java
remove(int index)
```

---

# 17. What if I want to remove the value 20?

Suppose:

```java
List l = new ArrayList();

l.add(10);
l.add(20);
l.add(30);
```

You want to remove the **value** `20`.

Use:

```java
l.remove(Integer.valueOf(20));
```

Now Java understands that you are supplying an object.

This is related to **method overloading**.

There are two relevant forms:

```text
remove(int)
remove(Object)
```

If you give:

```java
1
```

Java sees an `int`.

If you give:

```java
Integer.valueOf(20)
```

Java sees an object.

This is a very important interview/programming concept.

---

# 18. Now let's meet ArrayList

The first implementation of List we'll learn is:

# `ArrayList`

Think:

```text
ArrayList
     ↓
Dynamic array
```

An ordinary array:

```java
int a[] = new int[5];
```

has a fixed length.

ArrayList provides dynamically managed storage.

---

# 19. First ArrayList program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);

        System.out.println(al);
    }
}
```

Output:

```text
[10, 20, 30]
```

---

# 20. Why ArrayList is popular

Imagine you're constantly doing:

```text
"Give me element at index 100."
"Give me element at index 50."
"Give me element at index 200."
```

ArrayList is generally very good at index-based access.

Conceptually:

```text
ArrayList

Index:
 0    1    2    3
 ↓    ↓    ↓    ↓
[10] [20] [30] [40]
```

So:

```java
al.get(2);
```

can efficiently access the element at that position.

---

# 21. ArrayList insertion in the middle

Suppose:

```text
[10, 20, 30, 40]
```

Now:

```java
al.add(1, 50);
```

Result:

```text
[10, 50, 20, 30, 40]
```

The elements after the insertion position have to shift.

Therefore:

```text
ArrayList
 ↓
Great for reading by index
 ↓
Middle insertion/removal can require shifting
```

---

# 22. Now meet LinkedList

The second List implementation is:

# `LinkedList`

Think:

```text
LinkedList
     ↓
Linked nodes
```

Conceptually:

```text
[10] ↔ [20] ↔ [30] ↔ [40]
```

Instead of thinking about one continuous array, think about separate connected nodes.

---

# 23. First LinkedList program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        LinkedList ll = new LinkedList();

        ll.add(10);
        ll.add(20);
        ll.add(30);

        System.out.println(ll);
    }
}
```

Output:

```text
[10, 20, 30]
```

---

# 24. Why is LinkedList interesting?

LinkedList can perform operations at the beginning and end conveniently.

For example:

```java
ll.addFirst(10);
ll.addLast(30);
```

Suppose we start with:

```text
[20]
```

After:

```java
ll.addFirst(10);
```

we get:

```text
[10, 20]
```

After:

```java
ll.addLast(30);
```

we get:

```text
[10, 20, 30]
```

---

# 25. LinkedList has another identity

This is important.

LinkedList is not just a List.

It also implements:

```text
Deque
```

Therefore:

```text
LinkedList
   ├── List
   └── Deque
```

That's why LinkedList has methods such as:

```text
addFirst()
addLast()
removeFirst()
removeLast()
peek()
poll()
offer()
```

We'll understand the Queue/Deque side later.

For now, remember:

> **LinkedList can behave both as a List and as a Deque.**

---

# 26. ArrayList vs LinkedList — simple story

Imagine two storage systems.

### ArrayList

```text
[10][20][30][40][50]
```

Everything is arranged in array-style storage.

Excellent for:

```text
"Give me index 3."
```

### LinkedList

```text
[10] ↔ [20] ↔ [30] ↔ [40] ↔ [50]
```

Elements are linked.

Useful for certain insertion/removal patterns, especially at the ends.

---

# 27. Don't make this mistake

Don't memorize:

```text
ArrayList = fast
LinkedList = slow
```

That's too simplistic.

Instead:

```text
ArrayList
 ↓
Excellent general-purpose List
 ↓
Strong index/random access

LinkedList
 ↓
Linked structure
 ↓
Useful for certain insertion/removal-at-ends workloads
 ↓
Also supports Deque
```

For many normal applications, ArrayList is the first List you should consider.

---

# 28. Now meet Vector

Next:

# `Vector`

Vector is another List implementation.

Hierarchy:

```text
Collection
    ↑
   List
    ↑
  Vector
```

Vector is an older/legacy class.

---

# 29. Basic Vector program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Vector v = new Vector();

        v.add(10);
        v.add(20);
        v.add(30);

        System.out.println(v);
    }
}
```

Output:

```text
[10, 20, 30]
```

At first glance, this looks just like ArrayList.

So what is the important difference?

---

# 30. Vector's important characteristic

Traditional Vector methods are:

> **synchronized**

Think:

```text
ArrayList
 ↓
Not synchronized

Vector
 ↓
Synchronized
```

This was important in older Java applications involving multiple threads.

Vector is therefore historically important, but for most new general-purpose List code, ArrayList is usually preferred.

---

# 31. Vector is a legacy class

Think about Java history:

```text
Older Java
     ↓
Vector
     ↓
Later Collections Framework
     ↓
ArrayList
```

So if you see Vector in an old Java application, don't be surprised.

You should know it because:

* It's part of Java Collections.
* It appears in legacy applications.
* It can appear in interviews.
* It demonstrates the difference between synchronized and non-synchronized collection classes.

---

# 32. Vector — size vs capacity

Vector has:

```java
v.size();
```

and:

```java
v.capacity();
```

These are different concepts.

Suppose:

```text
Actual elements:
[10, 20, 30]
```

Then:

```java
v.size()
```

means:

```text
3
```

while capacity may be greater than 3.

Think:

```text
size
 ↓
How many elements are actually present?

capacity
 ↓
How much internal storage is currently available?
```

---

# 33. Now meet Stack

The fourth List-related class is:

# `Stack`

Stack extends Vector.

Hierarchy:

```text
Collection
    ↑
   List
    ↑
  Vector
    ↑
  Stack
```

Stack is designed around:

# LIFO

```text
Last In
First Out
```

---

# 34. Real-life example of Stack

Imagine a stack of plates.

You put plates on top:

```text
       30 ← last plate added
       20
       10
```

Which plate will you normally take first?

```text
30
```

Because it was the last one added.

That's:

```text
Last In
First Out
```

---

# 35. Stack `push()`

To add an element:

```java
push()
```

Example:

```java
Stack s = new Stack();

s.push(10);
s.push(20);
s.push(30);
```

Stack:

```text
30 ← top
20
10
```

---

# 36. Stack `peek()`

Suppose:

```text
30 ← top
20
10
```

Now:

```java
s.peek();
```

returns:

```text
30
```

but does not remove it.

After `peek()`:

```text
30 ← top
20
10
```

still exists.

Think:

```text
peek()
 ↓
LOOK
```

---

# 37. Stack `pop()`

Now:

```java
s.pop();
```

returns:

```text
30
```

and removes it.

Stack becomes:

```text
20 ← top
10
```

Think:

```text
pop()
 ↓
LOOK + REMOVE
```

---

# 38. `peek()` vs `pop()`

Memorize this:

```text
peek()
   ↓
returns top
doesn't remove

pop()
   ↓
returns top
removes top
```

This is one of the most important Stack concepts.

---

# 39. Complete Stack program

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        Stack s = new Stack();

        s.push(10);
        s.push(20);
        s.push(30);

        System.out.println("Stack = " + s);

        System.out.println("Top = " + s.peek());

        System.out.println("Removed = " + s.pop());

        System.out.println("Stack = " + s);
    }
}
```

Output:

```text
Stack = [10, 20, 30]
Top = 30
Removed = 30
Stack = [10, 20]
```

---

# 40. Stack `empty()`

To check whether Stack has no elements:

```java
s.empty();
```

Example:

```java
if(s.empty())
{
    System.out.println("Stack is empty");
}
else
{
    System.out.println("Stack is not empty");
}
```

---

# 41. Stack `search()`

Stack also provides:

```java
s.search(20);
```

It searches from the top.

Suppose:

```text
30 ← top
20
10
```

Then:

```text
search(30) → 1
search(20) → 2
search(10) → 3
```

Important:

> `search()` positions are measured from the top and are **not normal List indexes**.

---

# 42. Now put the four together

Let's make the entire List family easy.

```text
                         LIST
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ↓                ↓                ↓
      ArrayList        LinkedList          Vector
                                              │
                                              ↓
                                            Stack
```

### ArrayList

```text
Dynamic array
```

### LinkedList

```text
Linked nodes
+
Deque operations
```

### Vector

```text
Synchronized legacy List
```

### Stack

```text
LIFO
+
extends Vector
```

---

# 43. Imagine you're choosing one

I'll give you situations.

### Situation 1

> "I need a normal List for my application."

Think:

```text
ArrayList
```

---

### Situation 2

> "I need List behavior and also operations at both ends."

Think:

```text
LinkedList
```

because it also implements Deque.

---

### Situation 3

> "I'm maintaining an old application and I find Vector."

Understand:

```text
Vector
```

It is a legacy synchronized List.

---

### Situation 4

> "I need Last-In-First-Out behavior."

Think:

```text
Stack
```

For modern Java code, a `Deque` such as `ArrayDeque` is generally preferred for new stack implementations, but Stack is still important to learn.

---

# 44. One important practical distinction

Don't think:

```text
List = ArrayList
```

That's incorrect.

Think:

```text
List = interface

ArrayList = class
LinkedList = class
Vector = class
Stack = class
```

Relationship:

```text
List
 ↑
 ├── ArrayList
 ├── LinkedList
 └── Vector
      ↑
    Stack
```

---

# 45. Complete List program

Let's combine the important List operations.

```java
import java.util.*;

class Demo
{
    public static void main(String[] args)
    {
        List l = new ArrayList();

        // Adding
        l.add(10);
        l.add(20);
        l.add(30);

        System.out.println(l);

        // Inserting
        l.add(1, 50);

        System.out.println(l);

        // Accessing
        System.out.println("Element = " + l.get(2));

        // Replacing
        l.set(2, 100);

        System.out.println(l);

        // Searching
        System.out.println("Contains 20 = " + l.contains(20));

        // Size
        System.out.println("Size = " + l.size());

        // Removing by index
        l.remove(1);

        System.out.println(l);
    }
}
```

Walk through it:

```text
Initially:
[]

add 10:
[10]

add 20:
[10,20]

add 30:
[10,20,30]

add(1,50):
[10,50,20,30]

get(2):
20

set(2,100):
[10,50,100,30]

remove(1):
[10,100,30]
```

Once you can mentally execute this program, you've understood the core behavior of List.

---

# 46. The most important List methods

You don't need to memorize everything at once.

Divide them into groups.

### Add

```java
add()
add(index, element)
```

### Read

```java
get()
```

### Replace

```java
set()
```

### Remove

```java
remove()
```

### Search

```java
contains()
indexOf()
lastIndexOf()
```

### Size/state

```java
size()
isEmpty()
```

### Remove everything

```java
clear()
```

---

# 47. The three most important differences

If you're learning this for the first time, concentrate on these three.

### Difference 1

```text
add(index,value)
        vs
set(index,value)
```

```text
add → INSERT
set → REPLACE
```

---

### Difference 2

```text
remove(index)
        vs
remove(object)
```

```text
remove(1)
        ↓
index

remove(Integer.valueOf(20))
        ↓
object/value
```

---

### Difference 3

```text
ArrayList
        vs
LinkedList
```

```text
ArrayList
→ dynamic array
→ strong index access

LinkedList
→ linked structure
→ List + Deque
```

---

# 48. Final Teaching Picture

Whenever you see:

```java
List l
```

your brain should immediately think:

```text
LIST
 │
 ├── ORDERED
 │
 ├── DUPLICATES ALLOWED
 │
 ├── INDEX BASED
 │
 └── IMPLEMENTATIONS
       │
       ├── ArrayList
       │      ↓
       │   Dynamic array
       │
       ├── LinkedList
       │      ↓
       │   Linked nodes + Deque
       │
       └── Vector
              ↓
           Synchronized
           Legacy
              │
              ↓
            Stack
              ↓
             LIFO
```

---

# 🧠 Final Memory Trick

Remember these four words:

```text
ARRAY
LINK
SYNC
STACK
```

They map directly to:

```text
ARRAY  → ArrayList
LINK   → LinkedList
SYNC   → Vector
STACK  → Stack
```

And remember the List identity:

```text
LIST = ORDER + DUPLICATES + INDEX
```

Once these ideas are clear, the next collection type—**Set**—becomes much easier because you can directly compare it with List:

```text
LIST
 ↓
duplicates allowed
order maintained
index available

SET
 ↓
duplicates not allowed
(no index-based List operations)
```

That contrast is the key to understanding why Java provides different collection types.
