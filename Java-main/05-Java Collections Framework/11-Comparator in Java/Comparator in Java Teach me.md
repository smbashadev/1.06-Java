# 11. Comparator in Java — TEACHME

> **Training rule:** We will **not use Generics**.
> I will teach this from the beginning, assuming you are learning `Comparator` for the first time.

---

# 1. First Understand the Problem

Imagine we have three students:

```text
30 → Ravi → 85 marks
10 → Arun → 90 marks
20 → Kumar → 75 marks
```

A teacher may ask:

> "Arrange these students by roll number."

We get:

```text
10 → Arun
20 → Kumar
30 → Ravi
```

Later the teacher may say:

> "Now arrange them by name."

We get:

```text
Arun
Kumar
Ravi
```

Later:

> "Now arrange them by marks."

We get:

```text
75
85
90
```

### Question

How can Java know **which rule we want to use**?

That's where **Comparator** comes in.

---

# 2. What is Comparator?

Think of a `Comparator` as a **separate teacher who knows how to compare two objects**.

```text
Student objects
      ↓
Comparator
      ↓
"Which one should come first?"
```

So:

> **Comparator is an interface used to define custom/alternative ordering of objects.**

It belongs to:

```java
java.util
```

---

# 3. Before Comparator — What Does "Compare" Mean?

Suppose we have:

```text
Student A → rollNo 10
Student B → rollNo 20
```

We ask:

> Which student should come first?

There are only three possibilities:

```text
A before B
A and B same for ordering
A after B
```

Java represents these using an `int`.

```text
Negative → First comes BEFORE second

Zero → Both are SAME for ordering

Positive → First comes AFTER second
```

Remember:

```text
        compare()
           |
    -----------------
    |       |       |
 Negative   0    Positive
    |       |       |
 BEFORE   SAME    AFTER
```

---

# 4. The `compare()` Method

The most important method in `Comparator` is:

```java
public int compare(Object obj1, Object obj2)
{
    // comparison logic
}
```

Let's understand every part.

### `public`

The method is publicly accessible.

### `int`

The method returns an integer.

### `compare`

This is the method name.

### `Object obj1`

First object.

### `Object obj2`

Second object.

So:

```text
compare(obj1, obj2)
       ↓      ↓
    First    Second
    object   object
```

---

# 5. Why Does `compare()` Return `int`?

Because Java needs three results:

```text
First < Second
First = Second
First > Second
```

Therefore:

```text
Negative
Zero
Positive
```

For example:

```text
-10 → first comes before
  0 → same for ordering
+10 → first comes after
```

It does **not** have to return exactly:

```text
-1
0
1
```

Any negative, zero, or positive integer is sufficient.

---

# 6. Let's Build Our First Comparator

Suppose we have:

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

    public String toString()
    {
        return rollNo + " - " + name;
    }
}
```

Now we want to sort Students by roll number.

We create:

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

---

# 7. Let's Understand the Comparator Slowly

Look at:

```java
class RollNoComparator implements Comparator
```

This means:

> `RollNoComparator` is a class that follows the rules of the `Comparator` interface.

Then:

```java
public int compare(Object obj1, Object obj2)
```

means:

> "Give me two objects and I will tell you which one should come first."

Then:

```java
Student s1 = (Student)obj1;
Student s2 = (Student)obj2;
```

Because we are **not using Generics**, the parameters are `Object`.

We therefore cast them to `Student`.

Finally:

```java
return s1.rollNo - s2.rollNo;
```

This determines the ordering.

---

# 8. Understand `s1.rollNo - s2.rollNo`

Suppose:

```text
s1.rollNo = 10
s2.rollNo = 20
```

Then:

```text
10 - 20
= -10
```

Negative means:

```text
s1 comes BEFORE s2
```

Now:

```text
s1.rollNo = 30
s2.rollNo = 20
```

Then:

```text
30 - 20
= +10
```

Positive means:

```text
s1 comes AFTER s2
```

Finally:

```text
20 - 20
= 0
```

Meaning:

```text
Same for ordering
```

---

# 9. Now Let Java Sort the Students

Suppose:

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

We want roll-number ordering.

We write:

```java
Collections.sort(list, new RollNoComparator());
```

The important part is:

```text
new RollNoComparator()
```

We are giving Java our comparison rule.

---

# 10. Complete Program

```java
import java.util.*;

class Student
{
    int rollNo;
    String name;

    Student(int rollNo, String name)
    {
        this.rollNo = rollNo;
        this.name = name;
    }

    public String toString()
    {
        return rollNo + " - " + name;
    }
}

class RollNoComparator implements Comparator
{
    public int compare(Object obj1, Object obj2)
    {
        Student s1 = (Student)obj1;
        Student s2 = (Student)obj2;

        return s1.rollNo - s2.rollNo;
    }
}

class Demo
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        list.add(new Student(30, "Ravi"));
        list.add(new Student(10, "Arun"));
        list.add(new Student(20, "Kumar"));

        System.out.println("Before Sorting:");
        System.out.println(list);

        Collections.sort(list, new RollNoComparator());

        System.out.println("After Sorting:");
        System.out.println(list);
    }
}
```

Output:

```text
Before Sorting:
[30 - Ravi, 10 - Arun, 20 - Kumar]

After Sorting:
[10 - Arun, 20 - Kumar, 30 - Ravi]
```

---

# 11. What Did `Comparator` Actually Do?

This is the most important question.

Did Comparator itself rearrange the ArrayList?

**No.**

The Comparator provided the **comparison rule**.

Think of it this way:

```text
Collections.sort()
       ↓
"I need to know which object comes first."
       ↓
asks Comparator
       ↓
compare(obj1, obj2)
       ↓
Negative / Zero / Positive
       ↓
Sorting process uses that information
```

Therefore:

> **Comparator provides the ordering logic; the sorting operation uses that logic to arrange the objects.**

---

# 12. Now the Real Power — Custom Sorting

Suppose the same Student class has:

```text
rollNo
name
marks
```

We can create:

```text
RollNoComparator
NameComparator
MarksComparator
```

Each one has a different job.

```text
                 Student
                    |
       ---------------------------
       |            |            |
       ↓            ↓            ↓
    RollNo         Name         Marks
       |            |            |
       ↓            ↓            ↓
  Comparator    Comparator    Comparator
```

Now we can choose the ordering we want.

---

# 13. Custom Sorting by Name

Create:

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

The students are sorted alphabetically by name.

For example:

```text
Ravi
Arun
Kumar
```

becomes:

```text
Arun
Kumar
Ravi
```

---

# 14. Why Are We Using `String.compareTo()` Here?

Look at:

```java
s1.name.compareTo(s2.name)
```

Here:

```text
s1.name
```

is a String.

Java's `String` class already knows how to compare Strings.

So our Comparator says:

> "For Students, compare their names using String's comparison rule."

Flow:

```text
Student
   ↓
name
   ↓
String
   ↓
String.compareTo()
   ↓
Name ordering
```

---

# 15. Custom Sorting by Marks

Suppose:

```text
30 Ravi 85
10 Arun 90
20 Kumar 75
```

We want:

```text
75
85
90
```

Create:

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

Now the ordering is based on marks.

---

# 16. So What Does "Custom" Mean?

Custom means:

> **We choose the rule instead of relying on the object's natural/default ordering.**

For example:

```text
"Sort Students by name."
```

That's custom.

```text
"Sort Students by marks."
```

That's custom.

```text
"Sort Students by roll number."
```

That can also be custom if roll number isn't the class's natural ordering.

The word **custom** simply means:

> A particular ordering rule supplied for the requirement.

---

# 17. Now Let's Compare Comparable and Comparator

This is where many students become confused.

First remember:

```text
Comparable → compareTo()

Comparator → compare()
```

That single line is extremely important.

---

# 18. Comparable

With `Comparable`, the class itself defines its natural ordering.

Example:

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

Student is saying:

> "My natural ordering is based on roll number."

So:

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

# 19. Comparator

With Comparator, we create a separate comparison class.

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

The Student class doesn't need to say:

> "My natural ordering is name."

Instead, we say:

> "For this particular requirement, compare Students by name."

So:

```text
NameComparator
       ↓
compare()
       ↓
Custom ordering
```

---

# 20. Think of Comparable as "My Default Rule"

Imagine Student says:

> "If nobody tells you anything else, arrange me by roll number."

That's Comparable.

```text
Student
   ↓
"My default order = rollNo"
```

---

# 21. Think of Comparator as "Your Special Rule"

Now the teacher says:

> "Today, don't use roll number. Arrange the Students by name."

We create:

```text
NameComparator
```

It says:

> "For this sorting operation, use names."

That's Comparator.

---

# 22. The Best Real-Life Analogy

Imagine a school.

### Student's default identity

Every student has a roll number.

The school may naturally identify/order students by roll number.

That is like:

```text
Comparable
```

Now the teacher says:

> "Today I want students alphabetically."

That's:

```text
Comparator
```

Another teacher says:

> "I want students according to marks."

Another:

```text
Comparator
```

So:

```text
Default rule
     ↓
Comparable

Special requirement
     ↓
Comparator
```

---

# 23. Why Do We Need Both?

Because an object can have:

```text
one natural/default ordering
```

but may need:

```text
many alternative orderings
```

For Student:

```text
Natural ordering → rollNo

Alternative 1 → name
Alternative 2 → marks
Alternative 3 → age
```

Therefore:

```text
Comparable
    ↓
Natural ordering

Comparator
    ↓
Multiple custom orderings
```

---

# 24. Important Difference in Location of Code

### Comparable

Comparison logic is generally placed inside the class being ordered:

```text
Student
   ↓
compareTo()
```

### Comparator

Comparison logic can be placed in a separate class:

```text
Student

NameComparator
MarksComparator
RollNoComparator
```

This separates:

```text
Data
```

from:

```text
Ordering strategy
```

---

# 25. Important Difference in Methods

Don't mix these up:

```text
Comparable → compareTo()
Comparator → compare()
```

### Comparable

```java
obj1.compareTo(obj2)
```

Conceptually:

```text
Current object
      VS
Other object
```

### Comparator

```java
comparator.compare(obj1, obj2)
```

Conceptually:

```text
First object
      VS
Second object
```

---

# 26. Comparison Table

| Comparable                                     | Comparator                                               |
| ---------------------------------------------- | -------------------------------------------------------- |
| Interface                                      | Interface                                                |
| `java.lang`                                    | `java.util`                                              |
| `compareTo()`                                  | `compare()`                                              |
| Natural ordering                               | Custom/alternative ordering                              |
| Usually implemented by the class being ordered | Usually implemented by a separate comparison class       |
| Normally defines one natural order             | Multiple Comparator classes can define different orders  |
| Example: Student naturally by rollNo           | Example: Student by name                                 |
| Example: Student naturally by marks            | Example: Student by marks when marks isn't natural order |

---

# 27. A Very Important Question

### Can a class use both Comparable and Comparator?

**Yes.**

For example:

```text
Student
   ↓
Comparable
   ↓
Natural ordering = rollNo
```

And separately:

```text
NameComparator
   ↓
Custom ordering = name
```

So the same Student objects can have:

```text
Natural ordering → rollNo
Custom ordering → name
Custom ordering → marks
```

---

# 28. Another Important Question

### Does Comparator change the natural ordering?

**No.**

Suppose:

```text
Student natural ordering = rollNo
```

Then:

```java
Collections.sort(list);
```

uses the natural ordering.

If you write:

```java
Collections.sort(list, new NameComparator());
```

you are simply asking that particular sorting operation to use the name comparison rule.

The Student's natural ordering remains unchanged.

---

# 29. Another Important Question

### Is natural ordering always ascending?

**No.**

Natural ordering means:

> The default ordering defined by the class.

It doesn't inherently mean ascending.

The comparison logic determines the direction.

---

# 30. Another Important Question

### Does Comparator have to return `-1`, `0`, `1`?

No.

For example:

```text
-100 → BEFORE
-5   → BEFORE
-1   → BEFORE

0    → SAME

1    → AFTER
50   → AFTER
100  → AFTER
```

The **sign** matters.

---

# 31. Another Important Question

### Does `compare() == 0` mean the objects are completely identical?

No.

It means:

> They are equal according to the comparison rule.

For example, if our Comparator compares only marks:

```text
Student A → marks 80
Student B → marks 80
```

then:

```text
compare(A, B) == 0
```

even if:

```text
A.rollNo != B.rollNo
A.name != B.name
```

They are equal **for that ordering rule**.

---

# 32. One Complete Picture

```text
                     JAVA OBJECTS
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
        Default rule          Special requirement
```

---

# 33. Remember These Three Definitions

### `compare()`

> `compare()` is the method of `Comparator` used to compare two objects and determine their relative ordering.

### Custom sorting

> Custom sorting means arranging objects according to a specific ordering rule chosen for a particular requirement.

### Comparable vs Comparator

> `Comparable` defines the natural/default ordering through `compareTo()`, while `Comparator` defines custom/alternative ordering through `compare()`.

---

# 34. Final TEACHME Memory Trick 🧠

Imagine you have a box of Student objects.

You ask:

### "What is the student's normal/default order?"

```text
Comparable
    ↓
compareTo()
```

You then ask:

### "Can I sort these Students differently today?"

```text
Comparator
    ↓
compare()
```

So the complete formula is:

```text
                    SORTING OBJECTS
                           |
                 "How should they be ordered?"
                           |
              -----------------------------
              |                           |
              ↓                           ↓
       Default/Natural              Custom/Alternative
              |                           |
              ↓                           ↓
         Comparable                   Comparator
              |                           |
              ↓                           ↓
         compareTo()                   compare()
```

## ⭐ The one rule you should never forget

```text
Comparable  → compareTo() → Natural Ordering

Comparator  → compare()   → Custom Ordering
```

And for the result:

```text
compare() / compareTo()

Negative → BEFORE
Zero     → SAME for ordering
Positive → AFTER
```

**No Generics are used in this lesson.**
