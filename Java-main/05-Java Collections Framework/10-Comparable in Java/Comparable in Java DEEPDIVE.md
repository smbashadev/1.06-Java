# 10. Comparable in Java — DEEPDIVE

> **Training rule:** No Generics.
> All examples use normal/raw Java syntax so that `Comparable` is understood independently before studying Generics.

---

# 1. Comparable in Java

## 1.1 What is Comparable?

`Comparable` is an interface used when we want a class to define its **own natural ordering**.

It belongs to:

```java
java.lang
```

Therefore, no explicit import is normally required.

The important method is:

```java
compareTo()
```

The basic form, without Generics, is:

```java
class Student implements Comparable
{
    public int compareTo(Object obj)
    {
        // comparison logic
    }
}
```

The class itself decides:

> "When two objects of my class are compared, which object should come first?"

---

# 2. Why do we need Comparable?

Consider a class:

```java
class Student
{
    int rollNo;
    String name;
    int marks;
}
```

Suppose we have:

```text
Student 1 → rollNo = 30
Student 2 → rollNo = 10
Student 3 → rollNo = 20
```

If we ask Java to sort these Student objects:

```java
Collections.sort(list);
```

Java needs to know:

```text
Should Student objects be sorted by:

rollNo?
name?
marks?
```

Java cannot automatically decide what your class's natural order should be.

We can tell Java:

```text
Student → natural ordering = rollNo
```

by implementing `Comparable`.

---

# 3. Comparable and Natural Ordering

These two concepts are closely connected.

```text
Comparable
     ↓
compareTo()
     ↓
defines
     ↓
Natural Ordering
```

For example:

```text
Student
   ↓
compare by rollNo
   ↓
10, 20, 30, 40
```

That ordering becomes the **natural ordering** of `Student`.

---

# 4. `compareTo()` — Complete Explanation

## 4.1 What is `compareTo()`?

`compareTo()` is the method used to compare the current object with another object.

Without Generics:

```java
public int compareTo(Object obj)
{
    // comparison logic
}
```

It returns an `int`.

The returned value tells the sorting mechanism the relative order of the two objects.

---

# 5. Meaning of the `compareTo()` Result

There are three important possibilities.

## Negative value

```text
compareTo() < 0
```

means:

> Current object comes before the other object.

Example:

```text
10 compared with 20
```

```text
10 - 20 = -10
```

Therefore:

```text
10 comes before 20
```

---

## Zero

```text
compareTo() == 0
```

means:

> The two objects are equal according to the ordering.

Example:

```text
20 compared with 20
```

```text
20 - 20 = 0
```

---

## Positive value

```text
compareTo() > 0
```

means:

> Current object comes after the other object.

Example:

```text
30 compared with 20
```

```text
30 - 20 = 10
```

Therefore:

```text
30 comes after 20
```

---

# 6. The Most Important `compareTo()` Rule

Do **not** focus on the exact returned number.

Focus on its **sign**.

```text
             compareTo()
                  │
       ┌──────────┼──────────┐
       ↓          ↓          ↓
   Negative       0      Positive
       ↓          ↓          ↓
    Before      Equal      After
```

For example, all of these indicate "before":

```text
-1
-5
-100
```

All indicate "equal":

```text
0
```

And all of these indicate "after":

```text
1
5
100
```

---

# 7. Implementing Comparable

Let's create a complete example.

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

Let's understand every line.

---

## `implements Comparable`

```java
class Student implements Comparable
```

This tells Java:

> Student objects can define a natural ordering.

---

## `compareTo(Object obj)`

```java
public int compareTo(Object obj)
```

The method receives another object.

Because we are deliberately not using Generics, the parameter is:

```java
Object
```

---

## Type casting

```java
Student s = (Student)obj;
```

The received reference is of type `Object`.

We cast it back to `Student` so we can access:

```java
s.rollNo
```

---

## Comparing the values

```java
return this.rollNo - s.rollNo;
```

Here:

```text
this.rollNo
```

means:

> roll number of the current object

and:

```text
s.rollNo
```

means:

> roll number of the other object.

---

# 8. Understanding `this` in `compareTo()`

Suppose Java is comparing:

```text
Student(10)
```

with:

```text
Student(30)
```

Conceptually:

```text
Current object = Student(10)
Other object   = Student(30)
```

Therefore:

```java
this.rollNo
```

is:

```text
10
```

and:

```java
s.rollNo
```

is:

```text
30
```

So:

```text
10 - 30 = -20
```

Negative means:

```text
Student(10) comes before Student(30)
```

---

# 9. Complete Program — Natural Ordering

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
        return "Roll No: " + rollNo;
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

        System.out.println("Before sorting:");
        System.out.println(list);

        Collections.sort(list);

        System.out.println("After sorting:");
        System.out.println(list);
    }
}
```

Output:

```text
Before sorting:
[Roll No: 30, Roll No: 10, Roll No: 20]

After sorting:
[Roll No: 10, Roll No: 20, Roll No: 30]
```

---

# 10. What happens internally during sorting?

You don't need to memorize the internal sorting algorithm.

The important concept is:

```text
Collections.sort(list)
          ↓
Objects need to be ordered
          ↓
Student implements Comparable
          ↓
compareTo() is available
          ↓
compareTo() determines relative order
          ↓
List gets sorted
```

So the responsibility is divided:

```text
Collections.sort()
        ↓
performs sorting

compareTo()
        ↓
defines how two Student objects are ordered
```

---

# 11. Comparable Does NOT Perform Sorting

This is a common misunderstanding.

Some students think:

> "Comparable sorts the objects."

More accurately:

**Comparable defines the natural ordering.**

The sorting operation is performed by a sorting mechanism such as:

```java
Collections.sort(list);
```

So:

```text
Comparable
   ↓
defines comparison rule

Collections.sort()
   ↓
uses that rule to sort
```

---

# 12. Natural Ordering — Complete Explanation

## 12.1 What does "natural" mean?

Natural ordering means the **default ordering associated with a type**.

For example, numbers naturally have numerical ordering:

```text
10
20
30
40
```

Strings have lexicographical ordering:

```text
Apple
Banana
Cat
Dog
```

For your own class, you decide what the natural ordering should be.

---

# 13. Example: Student Natural Ordering

Suppose:

```java
class Student
{
    int rollNo;
    String name;
    int marks;
}
```

There are multiple possible ordering rules:

```text
By rollNo
By name
By marks
```

If we choose:

```text
rollNo
```

then:

```text
Student natural ordering = rollNo
```

If we implement `compareTo()` based on `rollNo`:

```java
return this.rollNo - s.rollNo;
```

then roll number becomes the natural ordering.

---

# 14. Can we define more than one natural ordering?

Usually, a class has **one natural ordering** represented by its `Comparable` implementation.

For example:

```text
Student
   ↓
Comparable
   ↓
Natural ordering = rollNo
```

If you later want:

```text
sort by name
sort by marks
sort by age
```

you generally use `Comparator`.

This is the important conceptual distinction:

```text
Comparable
    ↓
Natural/default ordering

Comparator
    ↓
Alternative/custom ordering
```

`Comparator` will be covered separately in your roadmap.

---

# 15. Comparable with Strings

`String` already has a natural ordering.

For example:

```java
System.out.println("Apple".compareTo("Banana"));
```

The result is negative because `"Apple"` comes before `"Banana"` in lexicographical ordering.

Similarly:

```java
System.out.println("Banana".compareTo("Apple"));
```

produces a positive result.

And:

```java
System.out.println("Apple".compareTo("Apple"));
```

produces:

```text
0
```

This demonstrates the same three-result rule.

---

# 16. Comparable with Integer

Wrapper classes such as `Integer` also have natural ordering.

Conceptually:

```text
10 < 20 < 30
```

So their `compareTo()` implementation follows numerical ordering.

This is one reason sorting collections of already comparable standard-library objects is straightforward.

---

# 17. `compareTo()` vs `equals()`

This is an important area of confusion.

`equals()` asks:

> Are these objects equal?

`compareTo()` asks:

> What is the ordering relationship between these objects?

Conceptually:

```text
equals()
   ↓
Equal or not?

compareTo()
   ↓
Before / Equal / After?
```

Example:

```text
10 compared to 20
```

`compareTo()` tells us:

```text
10 comes before 20
```

---

# 18. Is `compareTo() == 0` exactly the same as `equals()`?

Not necessarily.

A well-designed natural ordering often agrees with `equals()`, but Java's ordering contract does not universally require that.

This distinction becomes particularly important with classes such as `BigDecimal`, where two values can compare as zero while `equals()` considers them different because of scale.

For basic training:

```text
compareTo() == 0
```

means:

> The objects are equal **for ordering purposes**.

Do not automatically conclude:

```text
equals() == true
```

---

# 19. Comparable and TreeSet

This is especially important in Collections Framework.

`TreeSet` maintains elements according to their ordering.

If objects implement `Comparable`, their natural ordering can be used.

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

Here:

```text
Student
   ↓
Comparable
   ↓
compareTo()
   ↓
rollNo ordering
   ↓
TreeSet uses ordering
```

---

# 20. Important: Comparable Is Not Required for Every Collection

You should not think:

> "Every collection requires Comparable."

That is incorrect.

For example, a normal `ArrayList` can store objects without them implementing Comparable.

The need for ordering becomes important when you ask a collection or sorting operation to **order objects**.

Examples include:

```text
Collections.sort()
TreeSet
TreeMap
```

depending on how they are being used.

---

# 21. Common Mistake — Wrong Return Type

Incorrect:

```java
public boolean compareTo(Object obj)
```

Correct:

```java
public int compareTo(Object obj)
```

`compareTo()` returns:

```text
int
```

not:

```text
boolean
```

Because it needs to represent three ordering states:

```text
negative
zero
positive
```

A boolean could represent only two states.

---

# 22. Common Mistake — Returning Only `1` and `0`

Students sometimes write:

```java
if(this.rollNo > s.rollNo)
    return 1;
else
    return 0;
```

This is not a correct general implementation of the comparison contract because it fails to return a negative result when the current object should come before the other object.

The proper conceptual structure is:

```text
Current < Other → negative
Current = Other → zero
Current > Other → positive
```

---

# 23. Common Mistake — Confusing Current and Other Object

Suppose:

```java
public int compareTo(Object obj)
{
    Student s = (Student)obj;

    return this.rollNo - s.rollNo;
}
```

Remember:

```text
this
 ↓
current object

s
 ↓
other object
```

Therefore:

```text
this.rollNo - s.rollNo
```

means:

```text
current - other
```

---

# 24. Natural Ordering Does Not Mean Ascending Only

Natural ordering is whatever ordering the class defines.

Most simple examples define ascending order:

```text
10, 20, 30
```

But you could define the natural ordering differently.

For example, if you deliberately write comparison logic for descending order:

```java
return s.rollNo - this.rollNo;
```

then the natural ordering would become:

```text
30, 20, 10
```

So:

> Natural ordering means the class's defined default ordering, not necessarily ascending order.

---

# 25. A Better Way to Think About Comparable

Imagine every class has a question:

> "If two objects of my type meet, which one should come first?"

For a `Student`:

```text
Student
   ↓
Which one comes first?
   ↓
Compare roll numbers
```

For an `Employee`:

```text
Employee
   ↓
Which one comes first?
   ↓
Compare employee IDs
```

For a `Book`:

```text
Book
   ↓
Which one comes first?
   ↓
Compare price
```

The answer is implemented in:

```java
compareTo()
```

---

# 26. Complete Concept Flow

```text
                 Comparable
                     │
                     ↓
                 compareTo()
                     │
                     ↓
          Compare two objects
                     │
          ┌──────────┼──────────┐
          ↓          ↓          ↓
      Negative       0      Positive
          ↓          ↓          ↓
       Before      Equal      After
          │
          ↓
    Natural Ordering
          │
          ↓
 Sorting / Ordered Collections
```

---

# 27. Comparable vs Natural Ordering

| Comparable                               | Natural Ordering               |
| ---------------------------------------- | ------------------------------ |
| Interface                                | Ordering concept               |
| Provides `compareTo()`                   | Resulting default ordering     |
| Implemented by a class                   | Defined by that implementation |
| Used to establish comparison logic       | Represents the default order   |
| Example: `Student implements Comparable` | Student ordered by roll number |

So don't say:

> Comparable and natural ordering are two completely unrelated things.

Instead:

```text
Comparable → mechanism
Natural ordering → ordering defined using that mechanism
```

---

# 28. Important Rules to Remember

### Rule 1

`Comparable` is an interface.

### Rule 2

It belongs to:

```text
java.lang
```

### Rule 3

Its important method is:

```java
compareTo()
```

### Rule 4

`compareTo()` returns:

```text
int
```

### Rule 5

The sign of the result matters:

```text
< 0 → before
= 0 → equal for ordering
> 0 → after
```

### Rule 6

Comparable is generally used to define **natural ordering**.

### Rule 7

A class generally has one natural ordering.

### Rule 8

For alternative/custom ordering, Java provides `Comparator`.

---

# 29. Final Deep-Dive Example

```java
import java.util.*;

class Employee implements Comparable
{
    int id;
    String name;

    Employee(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int compareTo(Object obj)
    {
        Employee e = (Employee)obj;

        return this.id - e.id;
    }

    public String toString()
    {
        return id + " - " + name;
    }
}

class Demo
{
    public static void main(String[] args)
    {
        ArrayList list = new ArrayList();

        list.add(new Employee(103, "C"));
        list.add(new Employee(101, "A"));
        list.add(new Employee(102, "B"));

        System.out.println("Before sorting:");
        System.out.println(list);

        Collections.sort(list);

        System.out.println("After sorting:");
        System.out.println(list);
    }
}
```

Output:

```text
Before sorting:
[103 - C, 101 - A, 102 - B]

After sorting:
[101 - A, 102 - B, 103 - C]
```

### What happened?

```text
Employee
   ↓
implements Comparable
   ↓
compareTo()
   ↓
compares id
   ↓
id becomes natural ordering
   ↓
Collections.sort()
   ↓
Employees sorted by id
```

---

# 30. DEEPDIVE Final Summary

```text
Comparable
   │
   ├── Interface
   │
   ├── Package: java.lang
   │
   └── Main method:
          compareTo(Object)
                │
                ↓
             int result
                │
       ┌────────┼────────┐
       ↓        ↓        ↓
   negative     0     positive
       ↓        ↓        ↓
    before    equal    after
                │
                ↓
        Natural Ordering
```

### The most important sentence

> **Comparable allows a class to define its natural ordering by implementing the `compareTo()` method.**

### And remember the division of responsibility:

```text
Comparable
    ↓
defines HOW objects should be ordered

Collections.sort()
    ↓
performs the sorting
```

**No Generics are involved here.** Generics can be learned later as a separate topic without changing the core understanding of `Comparable`, `compareTo()`, and natural ordering.
