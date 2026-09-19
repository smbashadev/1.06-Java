# 4. List in Java — DOUBTKILLER

> **Rule for your training notes:** No Generics.
> Every program below uses normal/raw collection syntax.

This section is designed to kill the common doubts and interview traps around **List, ArrayList, LinkedList, Vector, and Stack**.

---

# 1. LIST INTERFACE — DOUBTKILLER

## ❓ Doubt 1: Is List a class or interface?

**List is an interface.**

```java
List l;
```

is a reference declaration.

But:

```java
new List();
```

❌ is invalid because an interface cannot be directly instantiated.

Instead:

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

---

## ❓ Doubt 2: Why can I write `List l = new ArrayList()`?

Because `ArrayList` implements `List`.

Think:

```text
List
 ↑
 │ implements
ArrayList
```

Therefore an ArrayList object can be referred to using a List reference.

This is **upcasting / interface reference usage**.

---

## ❓ Doubt 3: Does List store only unique elements?

**No.**

Duplicates are allowed.

```java
List l = new ArrayList();

l.add(10);
l.add(20);
l.add(10);

System.out.println(l);
```

Output:

```text
[10, 20, 10]
```

So:

```text
List → duplicates allowed
Set  → duplicates not allowed
```

---

## ❓ Doubt 4: Does List maintain insertion order?

**Yes.**

```java
l.add(30);
l.add(10);
l.add(20);
```

Output:

```text
[30, 10, 20]
```

It does not automatically sort the elements.

So:

> **Insertion order ≠ sorted order**

---

## ❓ Doubt 5: Does List support indexing?

**Yes.**

```java
List l = new ArrayList();

l.add(10);
l.add(20);
l.add(30);

System.out.println(l.get(1));
```

Output:

```text
20
```

Indexes:

```text
Value:  10   20   30
Index:   0    1    2
```

---

# 2. `add()` vs `set()` — BIG DOUBT

This is one of the most common List mistakes.

Suppose:

```text
[10, 20, 30]
```

### `add(1, 50)`

```java
l.add(1, 50);
```

Result:

```text
[10, 50, 20, 30]
```

It **inserts** a new element.

### `set(1, 50)`

```java
l.set(1, 50);
```

Result:

```text
[10, 50, 30]
```

It **replaces** the existing element.

### Remember:

```text
add → INSERT
set → REPLACE
```

---

# 3. `remove()` — THE FAMOUS DOUBT

Suppose:

```java
List l = new ArrayList();

l.add(10);
l.add(20);
l.add(30);
```

Now:

```java
l.remove(1);
```

What gets removed?

**Index 1 → 20**

Result:

```text
[10, 30]
```

Because:

```java
remove(int index)
```

---

## ❓ What if I want to remove the value 20?

Use:

```java
l.remove(Integer.valueOf(20));
```

Now Java receives an `Integer` object rather than primitive `int`.

Result:

```text
[10, 30]
```

So remember:

```text
remove(1)
       ↓
index 1

remove(Integer.valueOf(20))
       ↓
value/object 20
```

This is a classic Java method-overloading/autoboxing trap.

---

# 4. ARRAYLIST — DOUBTKILLER

## ❓ Doubt 1: Is ArrayList actually an array?

Not exactly.

`ArrayList` is a **class that uses an internally managed resizable array structure**.

You can think of it as:

```text
Normal array
    ↓
fixed size

ArrayList
    ↓
resizable collection
```

---

## ❓ Doubt 2: Is ArrayList fixed size?

**No.**

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(20);
al.add(30);
al.add(40);
al.add(50);
```

The collection grows as required.

This is one of the major differences from:

```java
int a[] = new int[5];
```

---

## ❓ Doubt 3: Does ArrayList allow duplicates?

Yes.

```java
ArrayList al = new ArrayList();

al.add(10);
al.add(10);
al.add(20);

System.out.println(al);
```

Output:

```text
[10, 10, 20]
```

---

## ❓ Doubt 4: Does ArrayList maintain insertion order?

Yes.

```java
al.add(50);
al.add(10);
al.add(30);
```

Output:

```text
[50, 10, 30]
```

It doesn't automatically sort.

---

## ❓ Doubt 5: Is ArrayList synchronized?

**No.**

Traditional `ArrayList` is not synchronized.

Therefore:

```text
ArrayList
 ↓
not synchronized
```

If synchronized behavior is needed, other approaches/classes can be considered depending on the requirement.

---

## ❓ Doubt 6: Is ArrayList faster than LinkedList?

Don't memorize:

> "ArrayList is always faster."

That's wrong.

Instead:

### ArrayList is generally strong for:

```text
index-based access
get(index)
set(index)
```

### LinkedList can be useful for:

```text
operations at the beginning/end
Deque-style operations
```

The actual performance depends on the operation and workload.

---

## ❓ Doubt 7: Why is ArrayList generally preferred?

For many ordinary List use cases, ArrayList gives a good combination of:

```text
simple API
+
good index access
+
dynamic size
+
good general-purpose performance
```

So if someone says:

> "I simply need a List."

A common first choice is:

```java
List l = new ArrayList();
```

---

# 5. LINKEDLIST — DOUBTKILLER

## ❓ Doubt 1: Is LinkedList a List?

Yes.

It implements `List`.

But there's an important extra point:

> LinkedList also implements `Deque`.

Conceptually:

```text
LinkedList
   ├── List behavior
   └── Deque behavior
```

---

## ❓ Doubt 2: Why is it called LinkedList?

Because conceptually its elements are maintained as linked nodes.

Think:

```text
[10] ↔ [20] ↔ [30] ↔ [40]
```

Each node is connected to other nodes.

This is different from thinking of ArrayList as an array-backed structure.

---

## ❓ Doubt 3: Does LinkedList allow duplicates?

Yes.

```java
LinkedList ll = new LinkedList();

ll.add(10);
ll.add(10);
ll.add(20);

System.out.println(ll);
```

Output:

```text
[10, 10, 20]
```

---

## ❓ Doubt 4: Does LinkedList maintain insertion order?

Yes.

```java
ll.add(30);
ll.add(10);
ll.add(20);
```

Result:

```text
[30, 10, 20]
```

---

## ❓ Doubt 5: Is LinkedList always faster for insertion?

**No.**

This is a very common misconception.

People often memorize:

```text
ArrayList → insertion slow
LinkedList → insertion fast
```

That's too simplistic.

With a LinkedList, you still need to locate the appropriate position unless you already have the relevant node/iterator position.

So don't blindly say:

> "LinkedList is faster."

Instead understand the operation being performed.

---

## ❓ Doubt 6: Why does LinkedList have `addFirst()`?

Because LinkedList also supports Deque operations.

```java
ll.addFirst(10);
ll.addLast(30);
```

Example:

```java
LinkedList ll = new LinkedList();

ll.add(20);

ll.addFirst(10);
ll.addLast(30);

System.out.println(ll);
```

Output:

```text
[10, 20, 30]
```

---

## ❓ Doubt 7: Can LinkedList be used as a Queue?

Yes.

It implements Queue/Deque-related interfaces, so it can support queue-style operations.

For example:

```java
LinkedList ll = new LinkedList();

ll.offer(10);
ll.offer(20);
ll.offer(30);

System.out.println(ll);
```

Output:

```text
[10, 20, 30]
```

And:

```java
System.out.println(ll.poll());
```

removes the head.

This is why LinkedList has a broader role than simply being a List.

---

# 6. VECTOR — DOUBTKILLER

## ❓ Doubt 1: Is Vector a List?

Yes.

```text
List
 ↑
Vector
```

---

## ❓ Doubt 2: Is Vector a modern replacement for ArrayList?

**No.**

Vector is an older/legacy collection class.

For normal modern List programming, ArrayList is generally preferred unless you have a specific reason to use Vector.

---

## ❓ Doubt 3: What is the major difference between Vector and ArrayList?

Traditional Vector methods are **synchronized**.

```text
ArrayList
 ↓
not synchronized

Vector
 ↓
synchronized
```

This distinction is important when studying older Java APIs and multithreading.

---

## ❓ Doubt 4: Does Vector allow duplicates?

Yes.

```java
Vector v = new Vector();

v.add(10);
v.add(10);
v.add(20);

System.out.println(v);
```

Output:

```text
[10, 10, 20]
```

---

## ❓ Doubt 5: Does Vector maintain insertion order?

Yes.

```java
v.add(30);
v.add(10);
v.add(20);
```

Output:

```text
[30, 10, 20]
```

---

## ❓ Doubt 6: What is `capacity()`?

Suppose:

```java
Vector v = new Vector();

v.add(10);
v.add(20);
v.add(30);
```

Then:

```java
v.size();
```

means:

> Number of elements currently stored.

Whereas:

```java
v.capacity();
```

means:

> Current internal storage capacity.

So:

```text
size     → actual elements
capacity → available internal capacity
```

They are not the same concept.

---

# 7. STACK — DOUBTKILLER

## ❓ Doubt 1: Is Stack a separate List family?

Stack is a class that **extends Vector**.

Hierarchy:

```text
List
 ↑
Vector
 ↑
Stack
```

---

## ❓ Doubt 2: What is the purpose of Stack?

Stack follows:

# LIFO

**Last In, First Out**

Example:

```text
push 10
push 20
push 30
```

Conceptually:

```text
30 ← TOP
20
10
```

`30` comes out first.

---

# 8. `push()` vs `pop()` vs `peek()`

This is the most important Stack distinction.

### `push()`

Adds to the top.

```java
s.push(10);
```

---

### `peek()`

Looks at the top but does **not** remove it.

```java
s.peek();
```

Think:

```text
peek → LOOK
```

---

### `pop()`

Returns the top and **removes it**.

```java
s.pop();
```

Think:

```text
pop → LOOK + REMOVE
```

---

# 9. Stack example

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

        System.out.println(s);

        System.out.println("Peek = " + s.peek());

        System.out.println("Pop = " + s.pop());

        System.out.println(s);
    }
}
```

Output:

```text
[10, 20, 30]
Peek = 30
Pop = 30
[10, 20]
```

---

# 10. ❓ `peek()` removed 30?

**No.**

Before:

```text
[10, 20, 30]
```

After:

```java
s.peek();
```

still:

```text
[10, 20, 30]
```

After:

```java
s.pop();
```

becomes:

```text
[10, 20]
```

Therefore:

```text
peek() → doesn't remove
pop()  → removes
```

---

# 11. ❓ What happens if Stack is empty and I call `pop()`?

A Stack cannot provide an element if there isn't one.

For `Stack.pop()` an empty stack results in:

```text
EmptyStackException
```

Therefore, you can check:

```java
if(s.empty())
{
    System.out.println("Stack is empty");
}
else
{
    System.out.println(s.pop());
}
```

---

# 12. ❓ What does `search()` in Stack return?

Suppose:

```text
30 ← top
20
10
```

Then:

```java
s.search(30);
```

returns:

```text
1
```

and:

```java
s.search(20);
```

returns:

```text
2
```

and:

```java
s.search(10);
```

returns:

```text
3
```

Important:

> Stack `search()` counts positions from the **top**, starting at `1`.

This is different from List indexes, which start at `0`.

---

# 13. BIG COMPARISON — ArrayList vs LinkedList

| Point               | ArrayList             | LinkedList          |
| ------------------- | --------------------- | ------------------- |
| Structure           | Array-backed          | Linked nodes        |
| List                | Yes                   | Yes                 |
| Duplicates          | Yes                   | Yes                 |
| Insertion order     | Yes                   | Yes                 |
| Index access        | Efficient             | Generally slower    |
| `addFirst()`        | Not its main strength | Convenient          |
| `addLast()`         | Yes                   | Convenient          |
| Deque behavior      | No                    | Yes                 |
| Synchronization     | No                    | No                  |
| Common default List | **Yes**               | Depends on workload |

### Memory trick:

```text
ArrayList
    ↓
"Give me element by index."

LinkedList
    ↓
"I need linked/deque-style operations."
```

---

# 14. BIG COMPARISON — ArrayList vs Vector

| Point                         | ArrayList | Vector      |
| ----------------------------- | --------- | ----------- |
| List                          | Yes       | Yes         |
| Dynamic                       | Yes       | Yes         |
| Duplicates                    | Yes       | Yes         |
| Insertion order               | Yes       | Yes         |
| Synchronized                  | No        | Yes         |
| Modern general-purpose choice | Usually   | Usually not |
| Historical/legacy importance  | Lower     | High        |

The key difference to remember:

```text
ArrayList → not synchronized
Vector    → synchronized
```

---

# 15. BIG COMPARISON — Vector vs Stack

This is another common confusion.

```text
Vector
  ↑
Stack
```

Stack **extends Vector**.

So Stack inherits Vector's List-like capabilities but adds stack-specific operations:

```text
push()
pop()
peek()
empty()
search()
```

Therefore:

```text
Vector → general List behavior
Stack  → LIFO behavior
```

---

# 16. BIG COMPARISON — List vs Set

This will become very important when you study Set.

### List

```text
[10, 20, 10, 30]
```

Allowed.

### Set

```text
[10, 20, 30]
```

Duplicate `10` is not retained.

Therefore:

```text
LIST
 ↓
duplicates allowed
index available

SET
 ↓
duplicates not allowed
no List-style index
```

---

# 17. ❓ Does List automatically sort elements?

**No.**

```java
List l = new ArrayList();

l.add(50);
l.add(10);
l.add(30);

System.out.println(l);
```

Output:

```text
[50, 10, 30]
```

If you want sorting, you can use the `Collections` utility class later:

```java
Collections.sort(l);
```

Then:

```text
[10, 30, 50]
```

Sorting is not an inherent property of List.

---

# 18. ❓ Can List contain `null`?

Yes, implementations such as ArrayList can contain null values.

```java
List l = new ArrayList();

l.add(10);
l.add(null);
l.add(20);

System.out.println(l);
```

Output:

```text
[10, null, 20]
```

Don't confuse:

```text
null
```

with:

```text
"null"
```

The first is the absence of an object reference; the second is a String containing four characters.

---

# 19. ❓ Can List contain different types?

Without Generics, yes.

For example:

```java
List l = new ArrayList();

l.add(10);
l.add("Hello");
l.add(25.5);

System.out.println(l);
```

Output:

```text
[10, Hello, 25.5]
```

This is one reason your training approach of learning the framework **before Generics** is useful.

But there is a major consequence:

```text
No Generics
    ↓
Less compile-time type safety
    ↓
More responsibility on the programmer
```

We'll study the solution to this later in **Generics**.

---

# 20. ❓ Why are wrapper classes involved?

Consider:

```java
List l = new ArrayList();

l.add(10);
```

`10` is an `int`.

Collections store objects, so Java performs **autoboxing**:

```text
int
 ↓
Integer
```

Conceptually:

```java
l.add(Integer.valueOf(10));
```

You don't normally have to write that manually.

This is one of the places where primitive values and wrapper objects meet Collections.

---

# 21. ❓ Does ArrayList store primitive `int` directly?

No.

For:

```java
l.add(10);
```

Java converts the primitive `int` into an `Integer` object through autoboxing.

Conceptually:

```text
10
 ↓
Integer object
 ↓
stored in collection
```

This is **not Generics**; it's Java's boxing/autoboxing mechanism.

---

# 22. ❓ Is Stack the recommended modern stack implementation?

`Stack` is an older class that extends Vector.

For modern Java code, when you specifically need stack behavior, a `Deque` implementation such as `ArrayDeque` is generally preferred.

But don't skip Stack in your Core Java training because:

```text
Stack
 ↓
legacy class
 ↓
important Java concept
 ↓
important interview knowledge
```

---

# 23. FINAL DOUBTKILLER MAP

Memorize this:

```text
                         Collection
                              ↑
                             List
                              ↑
             ┌────────────────┼────────────────┐
             │                │                │
             ↓                ↓                ↓
         ArrayList        LinkedList         Vector
                                                ↑
                                                │
                                              Stack
```

Now attach the key identity:

```text
List
 ↓
ORDER
DUPLICATES
INDEX


ArrayList
 ↓
DYNAMIC ARRAY
GOOD GENERAL-PURPOSE LIST


LinkedList
 ↓
LINKED STRUCTURE
LIST + DEQUE


Vector
 ↓
SYNCHRONIZED
LEGACY


Stack
 ↓
LIFO
push()
pop()
peek()
```

---

# 🔥 10 DOUBTS YOU MUST NEVER CONFUSE AGAIN

| Question                            | Correct answer      |
| ----------------------------------- | ------------------- |
| Is List a class?                    | ❌ No, interface     |
| Can List contain duplicates?        | ✅ Yes               |
| Does List maintain insertion order? | ✅ Yes               |
| Does List support index?            | ✅ Yes               |
| Does `add(index,x)` replace?        | ❌ No, it inserts    |
| Does `set(index,x)` insert?         | ❌ No, it replaces   |
| Does `peek()` remove Stack's top?   | ❌ No                |
| Does `pop()` remove Stack's top?    | ✅ Yes               |
| Is Vector synchronized?             | ✅ Traditionally yes |
| Does LinkedList implement Deque?    | ✅ Yes               |

### The ultimate memory formula

```text
LIST
=
ORDER
+
DUPLICATES
+
INDEX
```

```text
ArrayList
=
Dynamic Array
```

```text
LinkedList
=
List + Deque
```

```text
Vector
=
Synchronized + Legacy
```

```text
Stack
=
LIFO
=
push + pop + peek
```

And the most important distinction:

```text
add()  → INSERT
set()  → REPLACE

peek() → LOOK
pop()  → REMOVE
```

That is the **DOUBTKILLER core** for the entire List topic.
