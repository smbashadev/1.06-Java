# 11. Comparator in Java — 3LEVEL

> **Rule:** No Generics anywhere. All examples use the raw `Comparator` form.

The **3LEVEL** approach means we understand every concept in three stages:

```text
LEVEL 1 → Basic understanding
LEVEL 2 → Working understanding
LEVEL 3 → Deep/interview understanding
```

---

# 1. `compare()`

## LEVEL 1 — Basic Understanding

`compare()` is the main method of the `Comparator` interface.

It is used to compare **two objects**.

Basic form:

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
First object   Second object
       \       /
        compare()
           ↓
      int result
```

The result has three meanings:

| Result   | Meaning                    |
| -------- | -------------------------- |
| Negative | `obj1` comes before `obj2` |
| `0`      | Same for ordering          |
| Positive | `obj1` comes after `obj2`  |

### Simple example

```java
class MyComparator implements Comparator
{
    public int compare(Object obj1, Object obj2)
    {
        return 10 - 20;
    }
}
```

Result:

```text
-10
```

Negative means the first value comes before the second.

---

## LEVEL 2 — Working Understanding

Let's compare Student objects.

```java
class Student
{
    int rollNo;
    String name;

    Student(int rollNo, String name)
    {
        this.rollNo = rollNo;
        this.name = name;
    }
}
```

Now:

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

Why do we cast?

Because:

```java
Object obj1
Object obj2
```

are the parameter types.

We need:

```java
Student s1
Student s2
```

to access:

```java
s1.rollNo
s2.rollNo
```

### Example

Suppose:

```text
s1.rollNo = 10
s2.rollNo = 30
```

Then:

```text
10 - 30 = -20
```

Therefore:

```text
s1 comes before s2
```

If:

```text
30 - 10 = +20
```

then:

```text
s1 comes after s2
```

If:

```text
20 - 20 = 0
```

then:

```text
same for ordering
```

---

## LEVEL 3 — Deep Understanding

The important point is:

> `compare()` does not itself perform the complete sorting operation.

It provides the **comparison rule** that a sorting algorithm can use.

Conceptually:

```text
Collections.sort()
       ↓
Needs to compare two objects
       ↓
Comparator.compare()
       ↓
Negative / Zero / Positive
       ↓
Sorting algorithm uses result
       ↓
Objects arranged
```

Also remember:

### It does NOT have to return exactly `-1`, `0`, or `1`.

These are all valid according to their sign:

```text
-100 → before
-20  → before
-1   → before

0    → same for ordering

1    → after
20   → after
100  → after
```

---

# 2. Custom Sorting

## LEVEL 1 — Basic Understanding

**Custom sorting** means:

> Sorting objects according to a particular rule that we choose.

Suppose:

```text
Student
├── rollNo
├── name
└── marks
```

We might want:

```text
Sort by rollNo
Sort by name
Sort by marks
```

These are different ordering requirements.

Comparator lets us create those rules.

```text
Student objects
      ↓
Comparator
      ↓
Custom ordering
```

---

## LEVEL 2 — Working Understanding

Suppose we have:

```java
ArrayList list = new ArrayList();

list.add(new Student(30, "Ravi"));
list.add(new Student(10, "Arun"));
list.add(new Student(20, "Kumar"));
```

Current order:

```text
30 Ravi
10 Arun
20 Kumar
```

Create:

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
Collections.sort(list, new RollNoComparator());
```

Result:

```text
10 Arun
20 Kumar
30 Ravi
```

### Custom sorting by name

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

Use:

```java
Collections.sort(list, new NameComparator());
```

Result:

```text
Arun
Kumar
Ravi
```

### Custom sorting by marks

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

Use:

```java
Collections.sort(list, new MarksComparator());
```

---

## LEVEL 3 — Deep Understanding

The key advantage is that **one class can have multiple alternative ordering rules**.

For example:

```text
                       Student
                          |
          --------------------------------
          |              |               |
          ↓              ↓               ↓
     RollNoComparator NameComparator MarksComparator
          |              |               |
          ↓              ↓               ↓
       rollNo           name            marks
```

So we can do:

```java
Collections.sort(list, new RollNoComparator());
```

or:

```java
Collections.sort(list, new NameComparator());
```

or:

```java
Collections.sort(list, new MarksComparator());
```

The Student class does not have to be rewritten every time the sorting requirement changes.

### Important distinction

```text
Comparator
    ↓
Defines comparison rule
```

Whereas:

```text
Sorting operation
    ↓
Uses comparison rule
    ↓
Arranges objects
```

Therefore, don't say:

> "Comparator sorts the collection."

More accurately:

> **Comparator defines how objects should be compared, and a sorting operation can use that rule to arrange them.**

---

# 3. Comparable vs Comparator

## LEVEL 1 — Basic Understanding

The easiest way to remember them:

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
Custom ordering
```

### Comparable

The class itself defines its natural ordering.

### Comparator

A separate comparison object can define an alternative/custom ordering.

---

## LEVEL 2 — Working Understanding

### Comparable example

```java
class Student implements Comparable
{
    int rollNo;

    public int compareTo(Object obj)
    {
        Student s = (Student)obj;

        return this.rollNo - s.rollNo;
    }
}
```

Here Student says:

> "My natural ordering is based on roll number."

Conceptually:

```text
Student
   ↓
Comparable
   ↓
compareTo()
   ↓
Natural ordering
```

---

### Comparator example

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
NameComparator
   ↓
compare()
   ↓
Name-based custom ordering
```

The Student class itself doesn't have to change its natural ordering.

---

## LEVEL 3 — Deep Understanding

Imagine Student has:

```text
rollNo
name
marks
```

We could decide:

```text
Natural ordering = rollNo
```

Then:

```java
class Student implements Comparable
```

provides:

```java
compareTo()
```

But we may also need:

```text
Sort by name
Sort by marks
Sort by some other property
```

Those can be separate Comparators:

```text
Student
   |
   +── Comparable
   |      ↓
   |   compareTo()
   |      ↓
   |   Natural order
   |
   +── NameComparator
   |      ↓
   |   compare()
   |      ↓
   |   Name order
   |
   +── MarksComparator
          ↓
       compare()
          ↓
       Marks order
```

This is the fundamental reason Comparator is useful.

---

# 4. Comparable vs Comparator — Complete Table

| Point                   | Comparable                       | Comparator                       |
| ----------------------- | -------------------------------- | -------------------------------- |
| Interface               | `Comparable`                     | `Comparator`                     |
| Package                 | `java.lang`                      | `java.util`                      |
| Main method             | `compareTo()`                    | `compare()`                      |
| Purpose                 | Natural ordering                 | Custom/alternative ordering      |
| Comparison              | Current object vs another object | Two supplied objects             |
| Usually implemented by  | Class being ordered              | Separate comparison class        |
| Natural order           | Yes                              | No                               |
| Multiple ordering rules | Not its primary purpose          | Easily possible                  |
| Example                 | Student by rollNo                | Student by name                  |
| Example method          | `obj1.compareTo(obj2)`           | `comparator.compare(obj1, obj2)` |

---

# 5. The Three Most Important Differences

### Difference 1 — Method

```text
Comparable  → compareTo()

Comparator  → compare()
```

---

### Difference 2 — Purpose

```text
Comparable  → Natural ordering

Comparator  → Custom ordering
```

---

### Difference 3 — Location

```text
Comparable
    ↓
Usually inside the class being ordered

Comparator
    ↓
Usually separate from the class being ordered
```

---

# 6. One Real-Life Example

Imagine a school.

The school normally arranges students by:

```text
Roll Number
```

That is like:

```text
Comparable
```

Then the teacher says:

> "Today arrange students alphabetically."

That's:

```text
NameComparator
```

Another teacher says:

> "Arrange them according to marks."

That's:

```text
MarksComparator
```

So:

```text
Default arrangement
       ↓
Comparable

Special arrangement
       ↓
Comparator
```

---

# 7. Final 3LEVEL Master Map

```text
                         COMPARATOR
                             |
              -------------------------------
              |                             |
              ↓                             ↓
          compare()                   Custom Sorting
              |                             |
              ↓                             ↓
       Compare two objects          Choose your own rule
              |                             |
      ------------------          -----------------------
      |        |       |          |          |          |
      ↓        ↓       ↓          ↓          ↓          ↓
   Negative    0    Positive    Name       Marks     RollNo
      |        |       |
    BEFORE   SAME     AFTER
```

And alongside it:

```text
                    OBJECT ORDERING
                          |
             -------------------------
             |                       |
             ↓                       ↓
        Comparable               Comparator
             |                       |
             ↓                       ↓
        compareTo()               compare()
             |                       |
             ↓                       ↓
      Natural ordering       Custom ordering
```

## ⭐ Final memory formula

```text
Comparable
    ↓
compareTo()
    ↓
Natural / default ordering
```

```text
Comparator
    ↓
compare()
    ↓
Custom / alternative ordering
```

And:

```text
compare() result

Negative → First before second
Zero     → Same for ordering
Positive → First after second
```

**No Generics are used anywhere in this 3LEVEL explanation.**
