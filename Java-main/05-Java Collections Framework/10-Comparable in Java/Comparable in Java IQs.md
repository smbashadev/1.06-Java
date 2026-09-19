# 10. Comparable in Java — DOUBTKILLER

> **Training rule:** No Generics.
> We will use only normal/raw Java syntax and focus specifically on the doubts that commonly occur with `Comparable`, `compareTo()`, and natural ordering.

---

# DOUBT 1 — What exactly is `Comparable`?

`Comparable` is an interface used to define the **natural ordering** of objects.

It belongs to:

```java
java.lang
```

Basic form:

```java
class Student implements Comparable
{
    public int compareTo(Object obj)
    {
        // comparison logic
    }
}
```

Think:

```text
Comparable
     ↓
"My objects have a natural/default order."
```

---

# DOUBT 2 — What is `compareTo()`?

`compareTo()` is the method that defines the comparison logic.

Without Generics:

```java
public int compareTo(Object obj)
{
    // logic
}
```

It compares:

```text
current object
      VS
another object
```

---

# DOUBT 3 — Why does `compareTo()` return `int`?

Because Java needs **three possible ordering results**.

```text
Current object < Other object
        ↓
    Negative
```

```text
Current object = Other object
        ↓
       Zero
```

```text
Current object > Other object
        ↓
    Positive
```

Therefore:

```text
int
```

is appropriate.

### Remember:

```text
Negative → BEFORE
Zero     → SAME
Positive → AFTER
```

---

# DOUBT 4 — Does negative mean exactly `-1`?

**No.**

Any negative integer can represent "before".

For example:

```text
-1
-5
-20
-100
```

All mean:

```text
BEFORE
```

Similarly:

```text
1
5
20
100
```

all mean:

```text
AFTER
```

The **sign** matters, not the exact number.

---

# DOUBT 5 — Does zero mean the two objects are exactly identical?

Not necessarily.

```java
obj1.compareTo(obj2) == 0
```

means:

> The two objects are considered equal **for ordering purposes**.

It does not universally mean:

```java
obj1.equals(obj2)
```

is `true`.

So remember:

```text
compareTo() → ordering
equals()    → equality
```

---

# DOUBT 6 — What is the "current object"?

Look at:

```java
public int compareTo(Object obj)
{
    Student s = (Student)obj;

    return this.rollNo - s.rollNo;
}
```

Here:

```java
this.rollNo
```

means:

> Roll number of the current object.

And:

```java
s.rollNo
```

means:

> Roll number of the other object.

So:

```text
this = CURRENT
s    = OTHER
```

---

# DOUBT 7 — Why do we use `this`?

Suppose we have:

```text
Student A → rollNo = 10
Student B → rollNo = 20
```

When A compares itself with B:

```text
this.rollNo = 10
s.rollNo    = 20
```

Therefore:

```java
return this.rollNo - s.rollNo;
```

becomes:

```text
10 - 20
= -10
```

Negative means:

```text
Student A comes BEFORE Student B
```

---

# DOUBT 8 — Why do we cast `Object` to `Student`?

Because our method is written without Generics:

```java
public int compareTo(Object obj)
```

Therefore:

```text
obj
```

is an `Object` reference.

We need a `Student` reference to access:

```text
rollNo
```

So we write:

```java
Student s = (Student)obj;
```

Then:

```java
s.rollNo
```

becomes possible.

The flow is:

```text
Object
  ↓
Type Casting
  ↓
Student
  ↓
Student members
```

---

# DOUBT 9 — Why can't we simply write `obj.rollNo`?

Because `obj` is declared as:

```java
Object obj
```

The `Object` class does not have:

```java
rollNo
```

Therefore this is invalid:

```java
obj.rollNo
```

We first cast:

```java
Student s = (Student)obj;
```

Then:

```java
s.rollNo
```

works.

---

# DOUBT 10 — What exactly is Natural Ordering?

Natural ordering means:

> **The default ordering defined for objects of a particular class.**

For example:

```text
Numbers:
10 20 30 40
```

Numerical ordering is natural for numbers.

For strings:

```text
Apple
Banana
Cat
Dog
```

lexicographical ordering is their natural ordering.

For our own class, we decide what the natural ordering should be.

---

# DOUBT 11 — How does my class get a natural ordering?

Suppose:

```java
class Student
{
    int rollNo;
    String name;
    int marks;
}
```

We decide:

> Student objects should naturally be ordered by roll number.

Then we implement:

```java
class Student implements Comparable
```

and compare:

```java
return this.rollNo - s.rollNo;
```

Now:

```text
Student
   ↓
Comparable
   ↓
compareTo()
   ↓
rollNo comparison
   ↓
Natural ordering = rollNo
```

---

# DOUBT 12 — Does Java automatically know my natural ordering?

**No.**

For a custom class such as:

```java
class Student
```

Java doesn't know whether you want:

```text
rollNo
name
marks
age
```

to determine ordering.

You have to define the rule.

That's the purpose of `Comparable`.

---

# DOUBT 13 — Does Comparable sort objects?

**No.**

This distinction is extremely important.

`Comparable` defines the comparison rule.

For example:

```text
Student → compare by rollNo
```

A sorting operation uses that rule to arrange the objects.

Conceptually:

```text
Comparable
    ↓
defines HOW objects should be ordered

Collections.sort()
    ↓
actually performs sorting
```

So:

> **Comparable defines the ordering; it does not itself perform the sorting.**

---

# DOUBT 14 — What is the relationship between Comparable and `compareTo()`?

Think of it like this:

```text
Comparable
     ↓
Interface
     ↓
compareTo()
     ↓
Comparison logic
```

`Comparable` is the interface.

`compareTo()` is the method used to define the natural ordering.

---

# DOUBT 15 — What is the relationship between `compareTo()` and natural ordering?

This is the complete relationship:

```text
Comparable
     ↓
compareTo()
     ↓
Comparison rule
     ↓
Natural ordering
```

Example:

```text
Student
   ↓
compareTo() compares rollNo
   ↓
rollNo becomes natural ordering
```

---

# DOUBT 16 — Can I choose `name` instead of `rollNo`?

Yes.

Suppose:

```java
class Student
{
    int rollNo;
    String name;
}
```

If we decide that the natural ordering should be based on `name`, then the comparison logic can be based on the names.

For example:

```java
public int compareTo(Object obj)
{
    Student s = (Student)obj;

    return this.name.compareTo(s.name);
}
```

Now:

```text
Natural ordering = name
```

The important point is:

> **Whatever comparison rule you define in `compareTo()` becomes the natural ordering of the class.**

---

# DOUBT 17 — Can I define both roll-number ordering and name ordering using Comparable?

Not as two simultaneous natural orderings.

A class normally has **one natural ordering** through its `Comparable` implementation.

For example:

```text
Student
   ↓
Comparable
   ↓
Natural ordering = rollNo
```

If you need another ordering:

```text
Student by name
Student by marks
Student by age
```

that is where `Comparator` comes in.

Remember:

```text
Comparable
    ↓
Natural/default ordering

Comparator
    ↓
Alternative/custom ordering
```

---

# DOUBT 18 — Is natural ordering always ascending?

**No.**

"Natural" means **default ordering**, not necessarily ascending.

If your `compareTo()` defines:

```text
10 before 20 before 30
```

then natural ordering is ascending.

But if you deliberately define:

```text
30 before 20 before 10
```

then that can be the class's natural ordering too.

So:

```text
Natural ≠ necessarily ascending
```

Natural means:

> The ordering defined as the class's default ordering.

---

# DOUBT 19 — What happens if I reverse the subtraction?

Suppose you normally write:

```java
return this.rollNo - s.rollNo;
```

This gives ascending ordering.

If you write:

```java
return s.rollNo - this.rollNo;
```

you reverse the result.

Conceptually:

```text
30
20
10
```

can become the natural ordering.

So the comparison logic determines the direction.

---

# DOUBT 20 — Why can't `compareTo()` return boolean?

Imagine:

```java
boolean compareTo(...)
```

A boolean gives only:

```text
true
false
```

But ordering requires:

```text
BEFORE
SAME
AFTER
```

That's three states.

Therefore `compareTo()` returns:

```text
int
```

which allows:

```text
negative
zero
positive
```

---

# DOUBT 21 — What is wrong with this implementation?

```java
public int compareTo(Object obj)
{
    Student s = (Student)obj;

    if(this.rollNo > s.rollNo)
        return 1;
    else
        return 0;
}
```

The problem is that it doesn't distinguish:

```text
Current < Other
```

from:

```text
Current = Other
```

Both would return `0`.

A correct comparison needs:

```text
Current < Other → negative
Current = Other → zero
Current > Other → positive
```

---

# DOUBT 22 — Do I need to memorize the internal sorting algorithm?

**No.**

For this topic, concentrate on:

```text
Comparable
compareTo()
negative
zero
positive
natural ordering
```

You don't need to memorize the internal implementation of `Collections.sort()` to understand `Comparable`.

---

# DOUBT 23 — Why is `Comparable` useful in Collections Framework?

Because many situations require objects to have an ordering.

For example:

```text
Sorting
   ↓
Need ordering
   ↓
Comparable can provide natural ordering
```

Ordered collections can also use ordering rules.

For example, `TreeSet` can use the natural ordering of its elements.

---

# DOUBT 24 — Can you show the complete flow with `TreeSet`?

Yes.

Suppose:

```java
class Student implements Comparable
```

and:

```java
compareTo()
```

compares roll numbers.

Then conceptually:

```text
Student objects
      ↓
Comparable
      ↓
compareTo()
      ↓
rollNo comparison
      ↓
Natural ordering
      ↓
TreeSet can maintain ordered elements
```

Example:

```java
import java.util.*;

class Student implements Comparable
{
    int rollNo;

    Student(int rollNo)
    {
        this.rollNo = rollNo;
    }

    public int compareTo(Object obj)
    {
        Student s = (Student)obj;

        return this.rollNo - s.rollNo;
    }

    public String toString()
    {
        return "" + rollNo;
    }
}

class Demo
{
    public static void main(String[] args)
    {
        TreeSet set = new TreeSet();

        set.add(new Student(30));
        set.add(new Student(10));
        set.add(new Student(20));

        System.out.println(set);
    }
}
```

Output:

```text
[10, 20, 30]
```

The important concept is:

```text
Student
   ↓
compareTo()
   ↓
rollNo natural ordering
   ↓
TreeSet uses ordering
```

---

# DOUBT 25 — Is `Comparable` only for `ArrayList`?

No.

`Comparable` is not tied to one particular collection.

It is a mechanism for defining object ordering.

It can become relevant when an operation or collection needs to compare/order objects.

Examples include:

```text
Collections.sort()
TreeSet
TreeMap
```

depending on their usage.

---

# DOUBT 26 — What if two Student objects have the same roll number?

Suppose:

```text
Student 1 → 10
Student 2 → 10
```

Then:

```java
return this.rollNo - s.rollNo;
```

produces:

```text
10 - 10 = 0
```

Therefore:

```text
compareTo() == 0
```

means they are equal **according to this ordering**.

This can have important consequences in sorted collections such as `TreeSet`, where the ordering comparison can determine whether an element is treated as a duplicate.

---

# DOUBT 27 — Is `compareTo() == 0` always the same as `equals()`?

**No.**

This is an important technical distinction.

```text
compareTo() == 0
```

means:

> Equal according to the ordering.

Whereas:

```text
equals() == true
```

means:

> Equal according to the class's equality definition.

Ideally, natural ordering is often consistent with `equals()`, but Java does not universally require that.

---

# DOUBT 28 — Where is Comparable located?

`Comparable` belongs to:

```java
java.lang
```

Therefore:

```java
import java.lang.Comparable;
```

is normally unnecessary because `java.lang` is automatically available.

---

# DOUBT 29 — What is the simplest possible definition?

### Comparable

> `Comparable` is an interface used by a class to define the natural ordering of its objects.

### `compareTo()`

> `compareTo()` compares the current object with another object and returns a negative, zero, or positive integer indicating their relative ordering.

### Natural ordering

> Natural ordering is the default ordering defined for objects of a class.

---

# DOUBT 30 — What should I remember for an interview?

Remember this exact structure:

```text
Comparable
    ↓
Interface
    ↓
compareTo()
    ↓
Returns int
    ↓
Negative → Before
Zero     → Equal for ordering
Positive → After
    ↓
Defines Natural Ordering
```

---

# 🔥 MASTER DOUBTKILLER — One Example

```java
class Student implements Comparable
{
    int rollNo;

    Student(int rollNo)
    {
        this.rollNo = rollNo;
    }

    public int compareTo(Object obj)
    {
        Student s = (Student)obj;

        return this.rollNo - s.rollNo;
    }
}
```

Suppose:

```text
Current Student = 20
Other Student   = 50
```

Java conceptually gets:

```text
20 - 50
= -30
```

Negative:

```text
20 comes BEFORE 50
```

Now:

```text
Current Student = 50
Other Student   = 20
```

```text
50 - 20
= +30
```

Positive:

```text
50 comes AFTER 20
```

And:

```text
Current Student = 20
Other Student   = 20
```

```text
20 - 20
= 0
```

Therefore:

```text
Same ordering position
```

---

# 🧠 FINAL DOUBTKILLER MAP

```text
                     Comparable
                         │
                         ↓
                    compareTo()
                         │
                Compare two objects
                         │
             ┌───────────┼───────────┐
             ↓           ↓           ↓
         Negative        0        Positive
             ↓           ↓           ↓
          BEFORE       SAME        AFTER
                         │
                         ↓
                Natural Ordering
                         │
                         ↓
          Default ordering of the class
```

### The five answers you must know

**1. What is Comparable?**

> An interface used to define natural ordering.

**2. What is `compareTo()`?**

> The method that defines the comparison logic.

**3. What does negative mean?**

> Current object comes before the other object.

**4. What does zero mean?**

> Objects are equal for ordering purposes.

**5. What does positive mean?**

> Current object comes after the other object.

### And the biggest distinction:

```text
Comparable → Natural / Default ordering
Comparator → Custom / Alternative ordering
```

**No Generics are required anywhere in this explanation.**
