# 10. Comparable in Java — TEACHME

> **Training rule:** We will not use Generics here.
> We first understand `Comparable`, `compareTo()`, and natural ordering using normal Java syntax.

---

# Part 1 — First Understand the Problem

Imagine we have three students:

```text
Student A → Roll No: 30
Student B → Roll No: 10
Student C → Roll No: 20
```

Now I ask:

> **Can Java automatically know which Student should come first?**

No.

Java knows how to compare some built-in values such as numbers and strings, but for our own class:

```java
class Student
{
    int rollNo;
    String name;
}
```

Java doesn't automatically know whether we want:

```text
Roll No
Name
Marks
Age
```

to determine the order.

So we need to **teach Java how Student objects should be compared.**

That's where `Comparable` comes in.

---

# Part 2 — What is Comparable?

`Comparable` is an interface.

It tells Java:

> "My class knows how its objects should be naturally ordered."

We write:

```java
class Student implements Comparable
{
    // ...
}
```

The important method is:

```java
compareTo()
```

So remember:

```text
Comparable
     ↓
compareTo()
     ↓
Defines how objects are naturally ordered
```

---

# Part 3 — What is `compareTo()`?

`compareTo()` is the method that actually performs the comparison.

Without Generics:

```java
public int compareTo(Object obj)
{
    // comparison logic
}
```

Notice something very important:

```text
compareTo()
     ↓
returns int
```

It does **not** return:

```text
true / false
```

Instead, it returns a number whose **sign** tells Java the ordering.

---

# Part 4 — The Three Possible Results

Suppose we compare:

```text
10
```

with:

```text
20
```

There are three possibilities.

### Case 1 — Negative

```text
10 compared with 20
```

Result:

```text
negative
```

Meaning:

> 10 should come before 20.

---

### Case 2 — Zero

```text
20 compared with 20
```

Result:

```text
0
```

Meaning:

> They are equal for ordering purposes.

---

### Case 3 — Positive

```text
30 compared with 20
```

Result:

```text
positive
```

Meaning:

> 30 should come after 20.

---

# Part 5 — Easy Memory Trick

Just remember:

```text
compareTo()
     │
     ├── Negative → BEFORE
     ├── Zero     → SAME
     └── Positive → AFTER
```

That's the most important thing about `compareTo()`.

---

# Part 6 — Let's Build Our Own Example

Suppose we want to order Students according to their roll numbers.

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

Don't worry about the whole program yet.

Let's understand it one line at a time.

---

# Part 7 — `implements Comparable`

```java
class Student implements Comparable
```

This means:

> Student objects can be compared according to a rule defined by the class.

---

# Part 8 — `compareTo(Object obj)`

```java
public int compareTo(Object obj)
```

The method receives another object.

Because we are intentionally **not using Generics**, the parameter is:

```java
Object obj
```

Then:

```java
Student s = (Student)obj;
```

converts that object reference into a `Student` reference.

Now we can access:

```java
s.rollNo
```

---

# Part 9 — Understanding `this`

Look at:

```java
return this.rollNo - s.rollNo;
```

There are two students involved.

```text
this
 ↓
Current Student

s
 ↓
Other Student
```

For example:

```text
Current Student → rollNo = 10
Other Student   → rollNo = 20
```

Then:

```text
this.rollNo = 10
s.rollNo    = 20
```

Therefore:

```text
10 - 20
= -10
```

Negative.

So:

```text
10 comes before 20
```

---

# Part 10 — Let's Compare Another Example

Suppose:

```text
Current Student → 30
Other Student   → 20
```

Then:

```text
30 - 20
= 10
```

Positive.

Therefore:

```text
30 comes after 20
```

---

# Part 11 — What is Natural Ordering?

Now we reach the second major concept.

### Natural ordering means:

> **The default ordering defined for objects of a class.**

For example:

```text
Numbers:
10
20
30
40
```

This is natural numerical ordering.

For strings:

```text
Apple
Banana
Cat
Dog
```

This is lexicographical ordering.

For our own class, **we decide what the natural ordering should be.**

---

# Part 12 — Student Example

Suppose Student has:

```java
class Student
{
    int rollNo;
    String name;
    int marks;
}
```

We have several possible choices.

```text
Student
   │
   ├── rollNo
   ├── name
   └── marks
```

We decide:

> Students should naturally be ordered according to roll number.

Therefore:

```text
Student natural ordering = rollNo
```

And we write:

```java
return this.rollNo - s.rollNo;
```

Now Java knows the natural order.

---

# Part 13 — Complete Program

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
        ArrayList list = new ArrayList();

        list.add(new Student(30));
        list.add(new Student(10));
        list.add(new Student(20));

        System.out.println("Before Sorting:");
        System.out.println(list);

        Collections.sort(list);

        System.out.println("After Sorting:");
        System.out.println(list);
    }
}
```

Output:

```text
Before Sorting:
[30, 10, 20]

After Sorting:
[10, 20, 30]
```

---

# Part 14 — What Exactly Happened?

Before sorting:

```text
30  10  20
```

We told Java:

```text
Student natural ordering = rollNo
```

through:

```java
compareTo()
```

Then:

```java
Collections.sort(list);
```

uses that ordering.

The conceptual flow is:

```text
Student objects
      ↓
Comparable
      ↓
compareTo()
      ↓
Compare roll numbers
      ↓
Natural ordering
      ↓
Collections.sort()
      ↓
10 20 30
```

---

# Part 15 — Very Important: Comparable Does NOT Sort

This is a common doubt.

Some students think:

> "Comparable sorts the objects."

Not exactly.

`Comparable` **defines the comparison rule**.

`Collections.sort()` performs the sorting.

Think of it like this:

```text
Comparable
    ↓
Teacher says:
"Students should be ordered by roll number."

Collections.sort()
    ↓
Actually arranges them.
```

So:

```text
Comparable → HOW to order
sort()     → DO the sorting
```

---

# Part 16 — Another Way to Understand It

Imagine a classroom.

You have:

```text
30
10
20
```

You tell the teacher:

> "Arrange students according to roll number."

The teacher now knows the rule:

```text
10 < 20 < 30
```

In Java:

```text
Comparable
    ↓
provides the rule
```

and:

```text
Collections.sort()
    ↓
uses the rule
```

---

# Part 17 — Why is it called "Natural"?

Because it represents the object's **default/preferred ordering**.

For example, suppose we decide:

```text
Student → Roll Number
```

Then whenever Student objects need their natural ordering, roll number is used.

Similarly:

```text
Employee → Employee ID
Book     → Price
Product  → Product ID
```

could be chosen as the natural ordering.

---

# Part 18 — Can We Choose Name Instead?

Yes.

Suppose:

```java
class Student
{
    int rollNo;
    String name;
}
```

If we decide:

```text
Student natural ordering = name
```

then `compareTo()` should compare names instead.

Conceptually:

```java
return this.name.compareTo(s.name);
```

Now the natural ordering is based on name.

So the important point is:

> **The field used inside `compareTo()` determines the natural ordering you define for your class.**

---

# Part 19 — Can We Have Multiple Natural Orderings?

Normally, a class defines **one natural ordering** through `Comparable`.

For example:

```text
Student
   ↓
Comparable
   ↓
Natural ordering = rollNo
```

But maybe sometimes we want:

```text
Sort by rollNo
Sort by name
Sort by marks
```

Those are different ordering requirements.

That's where another concept comes in:

```text
Comparator
```

You will study `Comparator` separately.

For now remember:

```text
Comparable  → Natural/default ordering

Comparator  → Custom/alternative ordering
```

---

# Part 20 — `compareTo()` Is Not `equals()`

Another common doubt.

`equals()` asks:

> "Are these objects equal?"

`compareTo()` asks:

> "Which object comes before the other?"

Think:

```text
equals()
   ↓
Equal or Not Equal?

compareTo()
   ↓
Before / Same ordering / After?
```

For example:

```text
10 compared with 20
```

`compareTo()` gives a negative result.

That tells us:

```text
10 comes before 20
```

---

# Part 21 — What Does `compareTo() == 0` Mean?

If:

```java
obj1.compareTo(obj2) == 0
```

it means:

> `obj1` and `obj2` are considered equal **for ordering purposes**.

For beginner understanding:

```text
compareTo() == 0
       ↓
same position in ordering
```

But technically, don't automatically assume:

```java
obj1.equals(obj2)
```

will also be `true`.

`compareTo()` and `equals()` have different purposes.

---

# Part 22 — Why Does `compareTo()` Return `int`?

Because Java needs three possible ordering states:

```text
BEFORE
SAME
AFTER
```

An `int` can represent all three:

```text
Negative
   ↓
BEFORE

Zero
   ↓
SAME

Positive
   ↓
AFTER
```

A `boolean` would not be enough because it provides only:

```text
true / false
```

---

# Part 23 — Do I Need to Return Exactly `-1`, `0`, `1`?

No.

This is important.

These are all valid in terms of the sign:

```text
-1
-10
-100
```

All mean:

```text
BEFORE
```

Similarly:

```text
1
10
100
```

all mean:

```text
AFTER
```

So remember:

> **The sign matters, not the exact non-zero number.**

---

# Part 24 — One Common Program Mistake

Incorrect idea:

```java
if(this.rollNo > s.rollNo)
    return 1;
else
    return 0;
```

Why is this wrong?

Because it doesn't properly distinguish:

```text
Current < Other
```

from:

```text
Current = Other
```

A proper comparison must provide:

```text
Current < Other → negative

Current = Other → zero

Current > Other → positive
```

---

# Part 25 — The Three Questions Java Is Asking

Whenever `compareTo()` is called, think that Java is asking:

### Question 1

```text
Should current object come BEFORE the other?
```

If yes:

```text
negative
```

### Question 2

```text
Are they equal for ordering?
```

If yes:

```text
zero
```

### Question 3

```text
Should current object come AFTER the other?
```

If yes:

```text
positive
```

That's all `compareTo()` is fundamentally doing.

---

# Part 26 — Natural Ordering Example

Suppose:

```text
Students:

Student A → 30
Student B → 10
Student C → 20
```

Natural ordering based on roll number:

```text
10
20
30
```

Therefore:

```text
Student(10)
     ↓
comes before
     ↓
Student(20)
     ↓
comes before
     ↓
Student(30)
```

---

# Part 27 — The Big Picture

```text
                 Student
                    │
                    ↓
          implements Comparable
                    │
                    ↓
               compareTo()
                    │
                    ↓
          Compare roll numbers
                    │
          ┌─────────┼─────────┐
          ↓         ↓         ↓
      Negative      0      Positive
          ↓         ↓         ↓
       Before     Same      After
                    │
                    ↓
            Natural Ordering
                    │
                    ↓
             Sorting can use it
```

---

# Part 28 — Comparable vs Natural Ordering

Don't treat them as exactly the same thing.

| Comparable                        | Natural Ordering                |
| --------------------------------- | ------------------------------- |
| Interface                         | Ordering concept                |
| Provides `compareTo()`            | The default ordering of objects |
| Mechanism for defining comparison | Result of that comparison rule  |
| Implemented by the class          | Defined by the class            |

Easy way:

```text
Comparable = TOOL
Natural ordering = RESULT/RULE
```

---

# Part 29 — Real-Life Analogy

Imagine students standing in a line.

You say:

> "Stand according to roll number."

The rule is:

```text
Roll number
```

That is your natural ordering rule.

Then:

```text
10 → 20 → 30 → 40
```

Java equivalent:

```text
Comparable
    ↓
compareTo()
    ↓
roll number comparison
    ↓
natural ordering
```

---

# Part 30 — Teach-Back Test

If I ask you:

### "What is Comparable?"

You should answer:

> `Comparable` is an interface used to define the natural ordering of objects.

### "What method does it provide?"

> `compareTo()`.

### "What does compareTo() return?"

> An `int`.

### "What does negative mean?"

> Current object comes before the other object.

### "What does zero mean?"

> Both are equal for ordering purposes.

### "What does positive mean?"

> Current object comes after the other object.

### "What is natural ordering?"

> The default ordering defined for objects of a class.

---

# Final Memory Map

```text
Comparable
     │
     ↓
compareTo()
     │
     ↓
Compare current object
with another object
     │
     ├── Negative → BEFORE
     ├── Zero     → SAME
     └── Positive → AFTER
     │
     ↓
Natural Ordering
     │
     ↓
Can be used by
sorting / ordered collections
```

### ⭐ The one sentence you should never forget

> **Comparable is used when a class itself wants to define how its objects should be naturally ordered, and `compareTo()` contains that comparison logic.**

And remember the distinction for your upcoming topic:

```text
Comparable  → Natural Ordering
Comparator  → Custom/Alternative Ordering
```

**No Generics are needed for this concept.** We are keeping them completely separate, exactly according to your Collections Framework training approach.
