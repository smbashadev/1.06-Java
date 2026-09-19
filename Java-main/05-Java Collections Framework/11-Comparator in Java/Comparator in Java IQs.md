# 11. Comparator in Java — DOUBTKILLER

> **Rule:** No Generics anywhere.
> This section is specifically for removing the common doubts and traps around `Comparator`, `compare()`, custom sorting, and `Comparable vs Comparator`.

---

# 1. `compare()` — DOUBTKILLER

## Doubt 1: What exactly is `compare()`?

`compare()` is the method provided by the `Comparator` interface for comparing **two objects**.

Raw form:

```java
public int compare(Object obj1, Object obj2)
{
    // comparison logic
}
```

Think:

```text
obj1          obj2
  ↓             ↓
First         Second
object        object
       \       /
        compare()
            ↓
       int result
```

---

## Doubt 2: Why are there TWO objects?

Because Comparator is answering:

> **"Between these two objects, which one should come first?"**

For example:

```text
Student 1 → rollNo 10
Student 2 → rollNo 20
```

The Comparator compares:

```text
10 vs 20
```

and returns a result.

---

## Doubt 3: What does negative mean?

If:

```java
compare(obj1, obj2)
```

returns a negative value:

```text
obj1 comes BEFORE obj2
```

Example:

```text
10 - 20 = -10
```

Therefore:

```text
10 comes before 20
```

---

## Doubt 4: What does positive mean?

Positive means:

```text
obj1 comes AFTER obj2
```

Example:

```text
30 - 20 = +10
```

Therefore:

```text
30 comes after 20
```

---

## Doubt 5: What does zero mean?

Zero means:

```text
obj1 and obj2 are equal for this ordering rule
```

Example:

```text
20 - 20 = 0
```

It does **not necessarily mean the two objects are completely identical**.

---

## Doubt 6: Does `compare()` have to return -1, 0, 1?

**No.**

This is a very common misconception.

These are all valid based on their sign:

```text
-100 → BEFORE
-10  → BEFORE
-1   → BEFORE

0    → SAME

1    → AFTER
10   → AFTER
100  → AFTER
```

The important thing is:

```text
Negative → Before
Zero     → Same for ordering
Positive → After
```

---

## Doubt 7: Why is the return type `int`?

Because Java needs three ordering states:

```text
First < Second
First = Second
First > Second
```

An `int` naturally provides:

```text
negative
zero
positive
```

Therefore:

```java
int compare(...)
```

is appropriate for ordering.

---

## Doubt 8: Why do we use `Object`?

Because we are following your rule:

> **Do not use Generics.**

Therefore the raw Comparator method uses:

```java
Object
```

Example:

```java
public int compare(Object obj1, Object obj2)
```

But we want to access Student members.

So we cast:

```java
Student s1 = (Student)obj1;
Student s2 = (Student)obj2;
```

Now we can use:

```java
s1.rollNo
s2.rollNo
```

---

## Doubt 9: Why can't I write this?

```java
return obj1.rollNo - obj2.rollNo;
```

Because `obj1` and `obj2` are declared as:

```java
Object
```

The compiler knows only that they are `Object`s.

`Object` does not have:

```text
rollNo
name
marks
```

So we first convert:

```java
Student s1 = (Student)obj1;
Student s2 = (Student)obj2;
```

Then:

```java
return s1.rollNo - s2.rollNo;
```

---

# 2. Custom Sorting — DOUBTKILLER

## Doubt 10: What exactly is custom sorting?

Custom sorting means:

> **Sorting objects according to a particular rule that we choose.**

Suppose:

```text
Student
├── rollNo
├── name
└── marks
```

We may want:

```text
Sort by rollNo
Sort by name
Sort by marks
```

These are different sorting requirements.

---

## Doubt 11: Why can't one sorting rule handle everything?

Because different requirements need different comparisons.

For example:

```text
By rollNo:
10
20
30
```

But by marks:

```text
70
80
90
```

And by name:

```text
Arun
Kumar
Ravi
```

The comparison rule changes.

Comparator lets us define those rules separately.

---

# 3. Doubt: Does Comparator itself sort?

**No.**

This distinction is extremely important.

Comparator provides:

```text
comparison rule
```

The sorting operation uses that rule.

Conceptually:

```text
Collections.sort()
       ↓
"Which object should come first?"
       ↓
Comparator.compare()
       ↓
Negative / Zero / Positive
       ↓
Sorting algorithm
       ↓
Objects arranged
```

Therefore, the accurate statement is:

> **Comparator defines the ordering rule used by a sorting operation.**

Not:

> "Comparator itself performs the sorting."

---

# 4. Doubt: Show me the complete flow

Suppose:

```java
Collections.sort(list, new RollNoComparator());
```

Flow:

```text
ArrayList
   ↓
Student objects
   ↓
Collections.sort()
   ↓
RollNoComparator
   ↓
compare(obj1, obj2)
   ↓
Compare roll numbers
   ↓
Negative / Zero / Positive
   ↓
Sorting algorithm uses result
   ↓
Final sorted list
```

---

# 5. Doubt: Why create a separate Comparator class?

Suppose:

```text
Student
```

contains:

```text
rollNo
name
marks
```

We might need:

```text
RollNoComparator
NameComparator
MarksComparator
```

This gives us:

```text
Student
   |
   +---- RollNoComparator
   |
   +---- NameComparator
   |
   +---- MarksComparator
```

Each Comparator has one comparison responsibility.

---

# 6. Doubt: Can the same Student list be sorted in different ways?

**Absolutely.**

Suppose:

```java
ArrayList list = new ArrayList();

list.add(new Student(30, "Ravi", 85));
list.add(new Student(10, "Arun", 90));
list.add(new Student(20, "Kumar", 75));
```

By roll number:

```java
Collections.sort(list, new RollNoComparator());
```

By name:

```java
Collections.sort(list, new NameComparator());
```

By marks:

```java
Collections.sort(list, new MarksComparator());
```

Same objects.

Different ordering rules.

---

# 7. Doubt: Does Comparator change the Student object?

**No.**

Suppose:

```text
Student:
30 Ravi
10 Arun
20 Kumar
```

Sorting changes the **order in the collection**.

It doesn't mean:

```text
Ravi's rollNo becomes 10
```

The object's data remains the same.

The collection's arrangement changes.

---

# 8. Doubt: What is ascending sorting?

Suppose:

```text
10
20
30
```

We want smaller values first.

A common comparison is:

```java
return s1.rollNo - s2.rollNo;
```

Conceptually:

```text
small → large
```

---

# 9. Doubt: What is descending sorting?

Suppose we want:

```text
30
20
10
```

We can reverse the comparison direction:

```java
return s2.rollNo - s1.rollNo;
```

Conceptually:

```text
large → small
```

Therefore:

```text
s1 - s2 → ascending
s2 - s1 → descending
```

For simple teaching examples this is easy to understand; in production code, subtraction-based comparisons can have integer-overflow edge cases, so safer comparison techniques are preferable.

---

# 10. Doubt: Can Comparator sort Strings?

Yes.

For example:

```java
class NameComparator implements Comparator
{
    public int compare(Object obj1, Object obj2)
    {
        Student s1 = (Student)obj1;
        Student s2 = (Student)obj2;

        return s1.name.compareTo(s2.name);
    }
}
```

Here:

```text
Student
   ↓
name
   ↓
String.compareTo()
   ↓
Comparison result
```

---

# 11. Doubt: Why are we using `compareTo()` inside `compare()`?

This confuses many students.

We are dealing with **two different concepts**.

Outer method:

```java
Comparator.compare()
```

Inside it, we can use:

```java
String.compareTo()
```

because `name` is a String.

So:

```text
Comparator.compare()
       ↓
Student objects
       ↓
Student.name
       ↓
String.compareTo()
```

There is no contradiction.

---

# 12. Comparable vs Comparator — DOUBTKILLER

## Doubt 12: Are Comparable and Comparator the same?

**No.**

They are both interfaces related to object ordering, but they serve different purposes.

The most important distinction:

```text
Comparable
    ↓
compareTo()
    ↓
Natural ordering
```

```text
Comparator
    ↓
compare()
    ↓
Custom/alternative ordering
```

---

# 13. Doubt: What is Natural Ordering?

Natural ordering means:

> **The default ordering defined for a class.**

For example, if we decide:

```text
Student natural order = rollNo
```

then the Student class can implement:

```java
Comparable
```

and define:

```java
compareTo()
```

So:

```text
Student
   ↓
Comparable
   ↓
compareTo()
   ↓
Natural order
```

---

# 14. Doubt: Is Natural Ordering Always Ascending?

**No.**

Natural ordering simply means:

> The ordering defined as the class's natural/default ordering.

It doesn't inherently mean ascending.

The class's comparison logic determines the order.

---

# 15. Doubt: What is Custom/Alternative Ordering?

Suppose Student's natural ordering is:

```text
rollNo
```

But now we want:

```text
name
```

We can create:

```text
NameComparator
```

That's an alternative/custom ordering.

```text
Student
   ↓
Natural order → rollNo

NameComparator
   ↓
Alternative order → name
```

---

# 16. Doubt: Where does `compareTo()` belong?

`compareTo()` belongs to:

```text
Comparable
```

For example:

```java
class Student implements Comparable
{
    public int compareTo(Object obj)
    {
        Student s = (Student)obj;

        return this.rollNo - s.rollNo;
    }
}
```

---

# 17. Doubt: Where does `compare()` belong?

`compare()` belongs to:

```text
Comparator
```

Example:

```java
class NameComparator implements Comparator
{
    public int compare(Object obj1, Object obj2)
    {
        Student s1 = (Student)obj1;
        Student s2 = (Student)obj2;

        return s1.name.compareTo(s2.name);
    }
}
```

Never mix them up:

```text
Comparable  → compareTo()
Comparator  → compare()
```

---

# 18. Doubt: Why does Comparable use one object parameter?

Consider:

```java
obj1.compareTo(obj2);
```

The first object is the object on which the method is invoked.

Conceptually:

```text
obj1
 ↓
"Compare ME with obj2."
```

So:

```text
Comparable
   ↓
Current object
      VS
Other object
```

---

# 19. Doubt: Why does Comparator use two parameters?

Comparator itself is an external comparison object.

So:

```java
comparator.compare(obj1, obj2);
```

means:

```text
obj1
 VS
obj2
```

The Comparator is essentially saying:

> "Give me two objects and I will tell you their relative order."

---

# 20. Doubt: Why is Comparator better for multiple sorting rules?

Imagine:

```text
Student
```

has:

```text
rollNo
name
marks
age
```

We may need:

```text
Sort by rollNo
Sort by name
Sort by marks
Sort by age
```

With Comparator:

```text
RollNoComparator
NameComparator
MarksComparator
AgeComparator
```

This is clean:

```text
                  Student
                     |
       --------------------------------
       |       |        |             |
      Roll    Name     Marks         Age
       |       |        |             |
       ↓       ↓        ↓             ↓
   Comparator Comparator Comparator Comparator
```

---

# 21. Doubt: Can Comparable and Comparator both be used for the same class?

**Yes.**

This is an important point.

For example:

```text
Student
   ↓
Comparable
   ↓
Natural order = rollNo
```

and:

```text
NameComparator
   ↓
Custom order = name
```

and:

```text
MarksComparator
   ↓
Custom order = marks
```

So:

```text
One natural order
+
Multiple custom orders
```

is completely possible.

---

# 22. Doubt: Does Comparator replace Comparable?

**No.**

They solve related but different requirements.

```text
Comparable
    ↓
"What is my natural order?"

Comparator
    ↓
"What alternative order do you want?"
```

Think:

```text
Comparable → Default rule
Comparator → Special rule
```

---

# 23. Doubt: Can Comparator change the natural order?

**No.**

Suppose:

```text
Student natural ordering = rollNo
```

and we use:

```java
Collections.sort(list, new NameComparator());
```

This does not redefine Student's natural ordering.

It simply says:

> For this sorting operation, use the name-based comparison rule.

Natural order remains:

```text
rollNo
```

---

# 24. Doubt: What if `compare()` returns zero for two different objects?

Suppose our Comparator compares only marks:

```java
return s1.marks - s2.marks;
```

Students:

```text
Student A → rollNo 10, marks 80
Student B → rollNo 20, marks 80
```

Then:

```text
80 - 80 = 0
```

So:

```text
compare(A, B) == 0
```

This means:

> They are equivalent according to the marks-based ordering.

It doesn't necessarily mean:

```text
A == B
```

or that all their fields are identical.

---

# 25. Doubt: What happens if I cast the wrong object?

With raw Comparator:

```java
Student s1 = (Student)obj1;
```

we are assuming:

```text
obj1 actually contains a Student
```

If it doesn't, the cast can fail at runtime with:

```text
ClassCastException
```

This is one consequence of using raw types.

For our current learning rule:

```text
NO GENERICS
```

we explicitly perform the cast.

---

# 26. Doubt: Why not make Student implement Comparator?

This is an important conceptual distinction.

If you write:

```java
class Student implements Comparator
```

you are saying:

> "A Student object is a comparison strategy."

Usually that is not what we want.

We generally want:

```text
Student
   ↓
The object being compared

StudentComparator
   ↓
The rule used to compare Students
```

So a separate Comparator class is usually clearer.

---

# 27. Doubt: What if I need three different sorting orders?

Easy:

```text
Student
   |
   +── RollNoComparator
   |
   +── NameComparator
   |
   +── MarksComparator
```

Then select the appropriate Comparator when sorting.

This is one of the biggest practical advantages of Comparator.

---

# 28. The Biggest Confusion — One-Line Answer

If someone asks:

### "What is the difference between Comparable and Comparator?"

Answer:

> **Comparable defines the natural ordering of objects through `compareTo()`, whereas Comparator defines a custom or alternative ordering through `compare()`.**

That's the interview-quality answer.

---

# 29. The Biggest Confusion — Method Names

Memorize this:

```text
╔══════════════════════════════╗
║ Comparable  → compareTo()    ║
║ Comparator  → compare()      ║
╚══════════════════════════════╝
```

Never reverse them.

---

# 30. The Biggest Confusion — Result

Memorize this:

```text
╔══════════════════════════════╗
║ Negative → BEFORE            ║
║ Zero     → SAME FOR ORDERING ║
║ Positive → AFTER             ║
╚══════════════════════════════╝
```

---

# 31. The Biggest Confusion — Purpose

Memorize this:

```text
╔══════════════════════════════╗
║ Comparable                   ║
║      ↓                       ║
║ Natural/default ordering     ║
╠══════════════════════════════╣
║ Comparator                   ║
║      ↓                       ║
║ Custom/alternative ordering  ║
╚══════════════════════════════╝
```

---

# 32. Final DOUBTKILLER Map

```text
                         COMPARATOR
                             |
                 "How should two objects
                    be compared?"
                             |
                           compare()
                             |
                -------------------------
                |           |           |
             Negative       0        Positive
                |           |           |
              BEFORE     SAME FOR      AFTER
                         ORDERING
```

For custom sorting:

```text
Student
   |
   +── RollNoComparator → rollNo
   |
   +── NameComparator   → name
   |
   +── MarksComparator  → marks
```

And for the big comparison:

```text
             OBJECT ORDERING
                    |
          ---------------------
          |                   |
          ↓                   ↓
     Comparable           Comparator
          |                   |
          ↓                   ↓
     compareTo()           compare()
          |                   |
          ↓                   ↓
 Natural/default        Custom/alternative
    ordering               ordering
```

## ⭐ Final 5-second revision

```text
Comparable
→ compareTo()
→ Natural ordering
→ Usually inside the class

Comparator
→ compare()
→ Custom/alternative ordering
→ Usually separate class

compare() result:
Negative → BEFORE
Zero     → SAME for ordering
Positive → AFTER
```

**No Generics are used anywhere in this DOUBTKILLER section.**
