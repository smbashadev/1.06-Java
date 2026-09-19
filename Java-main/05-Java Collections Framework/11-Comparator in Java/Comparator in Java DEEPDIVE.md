# 11. Comparator in Java — DEEPDIVE

> **Training rule:** No Generics anywhere.
> All examples use the traditional/raw form of `Comparator`, because your Collections Framework roadmap is being studied without introducing the Generics concept.

---

# 1. What is `Comparator`?

`Comparator` is an interface in Java used to define **custom ordering** or **alternative ordering** for objects.

It belongs to:

```java
java.util
```

Basic declaration:

```java
class MyComparator implements Comparator
{
    public int compare(Object obj1, Object obj2)
    {
        // comparison logic
    }
}
```

The central method is:

```java
compare()
```

The overall idea is:

```text
Comparator
     ↓
compare()
     ↓
Comparison rule
     ↓
Custom / Alternative ordering
```

---

# 2. Why was Comparator introduced?

Consider a `Student` class:

```java
class Student
{
    int rollNo;
    String name;
    int marks;
}
```

A Student can logically be ordered in many ways:

```text
1. Roll number
2. Name
3. Marks
```

For example:

```text
Student 30 Ravi 85
Student 10 Arun 90
Student 20 Kumar 75
```

We could want:

### By roll number

```text
10 Arun
20 Kumar
30 Ravi
```

### By name

```text
10 Arun
20 Kumar
30 Ravi
```

### By marks

```text
20 Kumar 75
30 Ravi 85
10 Arun 90
```

The important problem is:

> **One class can have many possible sorting requirements.**

`Comparator` allows us to define those different sorting rules separately.

---

# 3. Where does Comparator belong?

`Comparator` belongs to:

```java
java.util.Comparator
```

Therefore we commonly write:

```java
import java.util.*;
```

Unlike `Comparable`, which belongs to `java.lang`, `Comparator` belongs to `java.util`.

---

# 4. Basic Syntax

Without Generics:

```java
class MyComparator implements Comparator
{
    public int compare(Object obj1, Object obj2)
    {
        // comparison logic
    }
}
```

There are three important parts:

```text
implements Comparator
        ↓
implement the Comparator interface

compare(Object obj1, Object obj2)
        ↓
receive two objects

return int
        ↓
tell Java their relative order
```

---

# 5. What is `compare()`?

`compare()` is the method used by a `Comparator` to compare **two objects**.

Syntax:

```java
public int compare(Object obj1, Object obj2)
{
    // comparison logic
}
```

Here:

```text
obj1 → first object
obj2 → second object
```

The method returns an `int`.

---

# 6. Meaning of the `compare()` Result

There are three possibilities.

## Negative value

```text
obj1 comes BEFORE obj2
```

## Zero

```text
obj1 and obj2 are equal for ordering
```

## Positive value

```text
obj1 comes AFTER obj2
```

Diagram:

```text
                  compare()
                     |
             -------------------
             |        |        |
          Negative    0     Positive
             |        |        |
           BEFORE   SAME     AFTER
```

---

# 7. Does `compare()` have to return `-1`, `0`, `1`?

**No.**

It only needs to communicate the sign.

For example:

```text
-100 → BEFORE
-20  → BEFORE
-1   → BEFORE

0    → SAME

1    → AFTER
20   → AFTER
100  → AFTER
```

So this is perfectly valid:

```java
return 50;
```

It means:

```text
First object comes AFTER second object.
```

---

# 8. Why does `compare()` return `int`?

Because ordering requires three states:

```text
First < Second
First = Second
First > Second
```

A boolean can only represent:

```text
true
false
```

but `int` can represent:

```text
negative
zero
positive
```

Therefore:

```java
int compare(...)
```

is suitable for ordering.

---

# 9. Understanding the Two Objects

This is one of the most important differences between `compare()` and `compareTo()`.

With `Comparator`:

```java
compare(obj1, obj2)
```

there are **two explicitly supplied objects**.

```text
obj1
 ↓
First object

obj2
 ↓
Second object
```

With `Comparable`:

```java
compareTo(obj)
```

the comparison is conceptually:

```text
this object
    VS
other object
```

Therefore:

```text
Comparable
    ↓
compareTo()
    ↓
this vs other

Comparator
    ↓
compare()
    ↓
obj1 vs obj2
```

---

# 10. Why do we cast `Object`?

Because we are deliberately not using Generics.

Suppose:

```java
public int compare(Object obj1, Object obj2)
```

The declared type is:

```java
Object
```

But we want to access:

```java
Student
```

members such as:

```java
rollNo
name
marks
```

Therefore:

```java
Student s1 = (Student)obj1;
Student s2 = (Student)obj2;
```

Now we can write:

```java
s1.rollNo
s2.rollNo
```

Flow:

```text
Object
   ↓
Type casting
   ↓
Student
   ↓
Student-specific members
```

---

# 11. Why can't we directly use `obj1.rollNo`?

Because:

```java
Object obj1
```

means the compiler knows only that `obj1` is an `Object`.

The `Object` class does not contain:

```java
rollNo
```

Therefore:

```java
obj1.rollNo
```

is invalid.

We first convert:

```java
Student s1 = (Student)obj1;
```

Then:

```java
s1.rollNo
```

is valid.

---

# 12. Complete Custom Sorting Example

Let's create:

```java
class Student
```

with:

```text
rollNo
name
marks
```

```java
import java.util.*;

class Student
{
    int rollNo;
    String name;
    int marks;

    Student(int rollNo, String name, int marks)
    {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    public String toString()
    {
        return rollNo + " " + name + " " + marks;
    }
}
```

Now create a Comparator for roll number:

```java
class RollNoComparator implements Comparator
{
    public int compare(Object obj1, Object obj2)
    {
        Student s1 = (Student)obj1;
        Student s2 = (Student)obj2;

        return s1.rollNo - s2.rollNo;
    }
}
```

Then:

```java
class Demo
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        list.add(new Student(30, "Ravi", 85));
        list.add(new Student(10, "Arun", 90));
        list.add(new Student(20, "Kumar", 75));

        Collections.sort(list, new RollNoComparator());

        System.out.println(list);
    }
}
```

Output:

```text
[10 Arun 90, 20 Kumar 75, 30 Ravi 85]
```

---

# 13. What exactly happened?

The flow is:

```text
ArrayList
    ↓
Student objects
    ↓
Collections.sort()
    ↓
RollNoComparator
    ↓
compare()
    ↓
Compare rollNo
    ↓
Objects arranged by rollNo
```

Notice something important:

The `Student` class itself does **not** contain the roll-number comparison logic.

The comparison logic is in:

```java
RollNoComparator
```

That is one of the major ideas behind `Comparator`.

---

# 14. Custom Sorting by Name

We can create another Comparator:

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

Then:

```java
Collections.sort(list, new NameComparator());
```

Now the same Student objects are sorted by name.

---

# 15. Custom Sorting by Marks

Another Comparator:

```java
class MarksComparator implements Comparator
{
    public int compare(Object obj1, Object obj2)
    {
        Student s1 = (Student)obj1;
        Student s2 = (Student)obj2;

        return s1.marks - s2.marks;
    }
}
```

Then:

```java
Collections.sort(list, new MarksComparator());
```

Now sorting is based on marks.

---

# 16. One Class, Multiple Sorting Rules

This is where Comparator becomes very powerful.

```text
                    Student
                       |
          ---------------------------
          |            |            |
          ↓            ↓            ↓
      RollNo        Name          Marks
    Comparator    Comparator    Comparator
          |            |            |
          ↓            ↓            ↓
      Sort by       Sort by       Sort by
       rollNo         name         marks
```

We don't need to modify `Student` every time we want a different ordering.

---

# 17. Why is this called Custom Sorting?

Because **we decide the rule**.

For example:

```text
Sort students by rollNo
```

or:

```text
Sort students by name
```

or:

```text
Sort students by marks
```

The sorting rule is supplied externally.

Therefore:

> **Comparator provides custom/alternative ordering.**

---

# 18. Natural Ordering vs Custom Ordering

Suppose Student has:

```text
rollNo
name
marks
```

and the class defines:

```text
rollNo
```

as its natural ordering.

Then:

```text
Natural ordering
     ↓
rollNo
```

But today we want:

```text
name
```

Then:

```text
Comparator
     ↓
name
```

Tomorrow:

```text
marks
```

Then:

```text
Comparator
     ↓
marks
```

So:

```text
Comparable → default/natural ordering
Comparator → custom/alternative ordering
```

---

# 19. `Collections.sort()` Without Comparator

Suppose the objects implement `Comparable`.

We can conceptually write:

```java
Collections.sort(list);
```

The sorting operation uses the elements' natural ordering.

Flow:

```text
list
 ↓
Comparable
 ↓
compareTo()
 ↓
Natural ordering
```

---

# 20. `Collections.sort()` With Comparator

When we supply a Comparator:

```java
Collections.sort(list, new NameComparator());
```

the sorting operation uses the supplied comparison rule.

Flow:

```text
list
 ↓
Comparator
 ↓
compare()
 ↓
Custom ordering
```

Therefore:

```text
Collections.sort(list)
        ↓
Natural ordering

Collections.sort(list, comparator)
        ↓
Specified custom ordering
```

---

# 21. Can a class implement both Comparable and use Comparator?

**Yes.**

This is a very important point.

For example:

```text
Student
   ↓
Comparable
   ↓
Natural ordering = rollNo
```

At another time:

```text
Student
   ↓
NameComparator
   ↓
Custom ordering = name
```

So the same class can have:

```text
One natural ordering
+
Many possible custom orderings
```

---

# 22. Why not simply put every sorting rule inside Student?

Imagine:

```java
class Student
{
    // rollNo sorting
    // name sorting
    // marks sorting
    // age sorting
    // salary sorting
}
```

This can make the class unnecessarily responsible for many unrelated ordering rules.

Comparator allows us to separate the comparison logic:

```text
Student
   ↓
Student data

Comparator
   ↓
Ordering logic
```

This separation makes the design cleaner.

---

# 23. Comparable vs Comparator — Core Difference

## Comparable

The class itself defines its natural ordering.

```java
class Student implements Comparable
```

The comparison method is:

```java
compareTo()
```

Conceptually:

```text
Student
   ↓
"My natural order is this."
```

---

## Comparator

An external object defines an ordering rule.

```java
class NameComparator implements Comparator
```

The comparison method is:

```java
compare()
```

Conceptually:

```text
NameComparator
   ↓
"For this sorting operation, use this order."
```

---

# 24. Detailed Comparison Table

| Feature                    | Comparable                                       | Comparator                                        |
| -------------------------- | ------------------------------------------------ | ------------------------------------------------- |
| Type                       | Interface                                        | Interface                                         |
| Package                    | `java.lang`                                      | `java.util`                                       |
| Main method                | `compareTo()`                                    | `compare()`                                       |
| Number of objects compared | Current object + other object                    | Two supplied objects                              |
| Purpose                    | Natural ordering                                 | Custom/alternative ordering                       |
| Comparison location        | Usually inside the class being ordered           | Separate Comparator class                         |
| Natural ordering           | Yes                                              | No; provides an alternative/custom rule           |
| Multiple ordering rules    | Not conveniently through multiple natural orders | Easily possible using multiple Comparator classes |
| Modifies class             | Class implements Comparable                      | Original class need not implement Comparator      |
| Typical example            | Student by rollNo                                | Student by name/marks                             |

---

# 25. The Most Important Memory Trick

```text
Comparable
    ↓
compareTo()
    ↓
Natural ordering
```

Whereas:

```text
Comparator
    ↓
compare()
    ↓
Custom ordering
```

Remember:

> **Comparable = Compare me according to my natural order.**

> **Comparator = Compare these objects according to this particular rule.**

---

# 26. `compareTo()` vs `compare()` in Detail

### `compareTo()`

Syntax:

```java
obj1.compareTo(obj2);
```

Conceptually:

```text
obj1
 ↓
current object

obj2
 ↓
other object
```

---

### `compare()`

Syntax:

```java
comparator.compare(obj1, obj2);
```

Conceptually:

```text
obj1 → first object
obj2 → second object
```

Therefore:

```text
compareTo()
    ↓
belongs to the object/class's Comparable mechanism

compare()
    ↓
belongs to the Comparator object
```

---

# 27. Can Comparator change the natural ordering?

It doesn't **change** the natural ordering.

Instead, it provides another ordering rule for the operation that uses it.

Suppose:

```text
Natural ordering = rollNo
```

Then:

```java
Collections.sort(list);
```

uses that natural order.

If we use:

```java
Collections.sort(list, new NameComparator());
```

we are asking that particular sorting operation to use name ordering instead.

The natural ordering itself remains:

```text
rollNo
```

---

# 28. Is Comparator itself responsible for sorting?

**No.**

This is another common misunderstanding.

Comparator provides the comparison rule.

For example:

```java
NameComparator
```

defines:

```text
How should two Students be ordered by name?
```

A sorting operation uses that rule.

Therefore:

```text
Comparator
    ↓
Defines comparison

Sorting algorithm
    ↓
Uses comparison
    ↓
Arranges elements
```

---

# 29. What if `compare()` returns zero?

Suppose:

```text
Student A → marks = 80
Student B → marks = 80
```

and the Comparator compares only marks:

```java
return s1.marks - s2.marks;
```

Then:

```text
80 - 80 = 0
```

Therefore:

```text
compare() == 0
```

means:

> A and B are equal according to the marks-based ordering.

It does not necessarily mean their:

```text
rollNo
name
```

are also equal.

---

# 30. Important Consequence with Sorted Collections

When ordering-based collections such as `TreeSet` use comparison, a result of:

```text
compare() == 0
```

can cause two distinct objects to be treated as equivalent **for the collection's ordering purposes**.

Therefore, when designing a Comparator, you should carefully decide what fields determine equality in the ordering.

For example, if you compare only:

```text
marks
```

then two different Students with the same marks can compare as zero.

---

# 31. Ascending vs Descending Custom Sorting

Suppose:

```java
return s1.marks - s2.marks;
```

This establishes ascending ordering by marks.

Conceptually:

```text
50
60
70
80
```

If the comparison direction is reversed:

```java
return s2.marks - s1.marks;
```

the ordering becomes:

```text
80
70
60
50
```

So Comparator can also define:

```text
Ascending
```

or:

```text
Descending
```

ordering.

---

# 32. String Comparison

For Strings, we can use:

```java
s1.name.compareTo(s2.name)
```

For example:

```text
"Arun"
"Kumar"
"Ravi"
```

The String class already has its own comparison mechanism.

Our Comparator can delegate to it:

```java
return s1.name.compareTo(s2.name);
```

So:

```text
Student Comparator
       ↓
Student.name
       ↓
String.compareTo()
       ↓
Name ordering
```

---

# 33. Comparator Does Not Require the Original Class to Change

Suppose the original class is:

```java
class Student
{
    int rollNo;
    String name;
}
```

We don't have to change it to:

```java
class Student implements Comparator
```

That would actually be conceptually wrong for this purpose.

Instead:

```java
class NameComparator implements Comparator
```

The relationship is:

```text
Student
   ↑
objects being compared

NameComparator
   ↑
comparison rule
```

---

# 34. One Student Class, Three Comparators

```java
class RollNoComparator implements Comparator
{
    public int compare(Object obj1, Object obj2)
    {
        Student s1 = (Student)obj1;
        Student s2 = (Student)obj2;

        return s1.rollNo - s2.rollNo;
    }
}
```

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

```java
class MarksComparator implements Comparator
{
    public int compare(Object obj1, Object obj2)
    {
        Student s1 = (Student)obj1;
        Student s2 = (Student)obj2;

        return s1.marks - s2.marks;
    }
}
```

Now:

```text
RollNoComparator → rollNo
NameComparator   → name
MarksComparator  → marks
```

This is the real power of Comparator.

---

# 35. Complete Mental Model

```text
                         Student
                            |
             -------------------------------
             |              |              |
             ↓              ↓              ↓
          rollNo          name           marks
             |              |              |
             ↓              ↓              ↓
       RollNoComparator NameComparator MarksComparator
             |              |              |
             ↓              ↓              ↓
         Custom         Custom          Custom
         ordering       ordering        ordering
```

The Student class contains the **data**.

The Comparator contains the **ordering rule**.

---

# 36. When Should You Think of Comparator?

Whenever you hear:

```text
"Sort by something else."
```

Think:

```text
Comparator
```

Examples:

```text
Sort employees by salary
Sort students by marks
Sort students by name
Sort products by price
Sort books by title
```

These are all typical custom-ordering situations.

---

# 37. When Should You Think of Comparable?

Whenever you hear:

```text
"What is the object's default/natural order?"
```

Think:

```text
Comparable
```

Examples:

```text
Student → natural order by rollNo
Employee → natural order by ID
Book → natural order by ISBN
```

The actual natural-order field is determined by the class design.

---

# 38. Common Mistake — Confusing the Names

Don't write:

```text
Comparable → compare()
Comparator → compareTo()
```

That is **wrong**.

Correct:

```text
Comparable  → compareTo()

Comparator  → compare()
```

---

# 39. Common Mistake — Thinking Comparator Means Sorting

Wrong:

> "Comparator sorts the collection."

Better:

> "Comparator defines how two objects should be compared; a sorting operation can use that comparison rule to arrange the collection."

---

# 40. Common Mistake — Thinking Natural Means Ascending

Wrong:

> "Natural ordering always means ascending."

Correct:

> "Natural ordering means the default ordering defined by the class."

Ascending or descending depends on the comparison rule.

---

# 41. Common Mistake — Thinking Comparator and Comparable Are Alternatives That Cannot Coexist

They can coexist.

For example:

```text
Student
   ↓
Comparable
   ↓
Natural ordering = rollNo
```

and separately:

```text
NameComparator
   ↓
Custom ordering = name
```

Both can be used with the same Student class.

---

# 42. Interview Question: Why is Comparator preferred when multiple sort orders are needed?

Because one class normally has one natural ordering.

But the same objects may require:

```text
Name ordering
Marks ordering
Roll number ordering
Age ordering
```

Comparator lets us create separate comparison strategies.

```text
Student
   |
   +-- NameComparator
   +-- MarksComparator
   +-- RollNoComparator
   +-- AgeComparator
```

Therefore Comparator is well suited for **multiple custom ordering requirements**.

---

# 43. Interview Question: Can Comparator compare objects of different classes?

Technically, the raw `Comparator` method receives `Object` references, so the implementation can perform whatever checks/casts it is designed to support.

But a Comparator should normally be designed around a particular kind of object and used consistently.

For example:

```text
Student Comparator
        ↓
Student objects
```

It should not casually expect unrelated object types.

---

# 44. Interview Question: What happens if the wrong object is passed?

With the raw form:

```java
Student s1 = (Student)obj1;
```

if `obj1` is not actually a `Student`, the cast can fail at runtime with:

```text
ClassCastException
```

This is one of the disadvantages of using raw types.

For your current learning stage, the important flow is:

```text
Object
 ↓
explicit cast
 ↓
expected class
```

---

# 45. Comparator and Type Safety

Because we are intentionally **not studying Generics yet**, our Comparator examples use:

```java
Object
```

and explicit casts:

```java
Student s = (Student)obj;
```

This is less type-safe than the modern generic form.

But that is intentional for your roadmap:

```text
Current Collections Framework study
        ↓
NO Generics
```

We will keep the concepts separate so Generics do not interfere with understanding Collections.

---

# 46. Master Comparison

```text
                    OBJECT ORDERING
                          |
              -------------------------
              |                       |
              ↓                       ↓
         Comparable               Comparator
              |                       |
              ↓                       ↓
        compareTo()                compare()
              |                       |
              ↓                       ↓
       Natural ordering        Custom ordering
              |                       |
              ↓                       ↓
        Default order          Alternative orders
```

---

# 47. Final Deep-Dive Summary

## `compare()`

```text
compare(Object obj1, Object obj2)
```

* Receives two objects.
* Compares them.
* Returns an `int`.
* Negative → first before second.
* Zero → equal for ordering.
* Positive → first after second.

---

## Custom Sorting

Custom sorting means:

> Defining a sorting rule other than the object's natural/default ordering.

Examples:

```text
Student by name
Student by marks
Student by rollNo
Employee by salary
Book by title
```

Comparator provides these rules.

---

## Comparable vs Comparator

### Comparable

```text
Interface
   ↓
compareTo()
   ↓
Natural ordering
```

### Comparator

```text
Interface
   ↓
compare()
   ↓
Custom/alternative ordering
```

### Ultimate memory rule

> **Comparable defines how an object naturally compares itself. Comparator defines how two objects should be compared for a particular/custom requirement.**

```text
Comparable
    → compareTo()
    → Natural ordering

Comparator
    → compare()
    → Custom ordering
```

And under your roadmap's rule: **no Generics are used in this entire explanation.**
