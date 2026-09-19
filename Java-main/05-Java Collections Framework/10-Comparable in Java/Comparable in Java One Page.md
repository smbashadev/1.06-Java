# 10. Comparable in Java — ONEPAGE

> **Training rule:** No Generics. We use normal/raw collection syntax only.
> `Comparable` is used when an object has a **natural/default ordering**.

---

# 1. Comparable

`Comparable` is an interface from:

```java
java.lang
```

It is used to define the **natural ordering** of objects.

The important method is:

```java
compareTo()
```

Basic structure:

```java
class Student implements Comparable
{
    public int compareTo(Object obj)
    {
        // comparison logic
    }
}
```

### Simple idea

Suppose we have:

```text
Student A
Student B
Student C
```

Java does not automatically know how your `Student` objects should be ordered.

Should they be ordered by:

```text
Roll number?
Name?
Age?
Marks?
```

You define the natural ordering using `Comparable`.

---

# 2. `compareTo()`

The `compareTo()` method compares the **current object** with another object.

Basic syntax without Generics:

```java
public int compareTo(Object obj)
{
    // comparison logic
}
```

It returns an `int`.

The result has three meanings:

| Result         | Meaning                                      |
| -------------- | -------------------------------------------- |
| Negative value | Current object comes before the other object |
| `0`            | Both are considered equal for ordering       |
| Positive value | Current object comes after the other object  |

The exact number is generally not important. The **sign** is important.

```text
compareTo()
     │
     ├── negative → before
     ├── zero     → equal
     └── positive → after
```

---

# 3. Simple `compareTo()` Example

```java
class Student implements Comparable
{
    int marks;

    Student(int marks)
    {
        this.marks = marks;
    }

    public int compareTo(Object obj)
    {
        Student s = (Student)obj;

        return this.marks - s.marks;
    }
}
```

Here:

```java
this.marks
```

means the current object's marks.

And:

```java
s.marks
```

means the other object's marks.

For example:

```text
Current = 80
Other   = 60

80 - 60 = 20
```

Positive result:

```text
80 comes after 60
```

For:

```text
Current = 40
Other   = 60

40 - 60 = -20
```

Negative result:

```text
40 comes before 60
```

---

# 4. Natural Ordering

**Natural ordering** means the default ordering that is defined for objects of a particular type.

Examples:

```text
Integer → numerical order
String  → lexicographical/alphabetical order
```

For your own class, you can define the natural ordering by implementing:

```java
Comparable
```

Example:

```text
Student → sort by roll number
```

If you decide that roll number is the natural ordering, then your `compareTo()` should compare roll numbers.

---

# 5. Complete Natural Ordering Example

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

        Collections.sort(list);

        System.out.println(list);
    }
}
```

Output:

```text
[Roll No: 10, Roll No: 20, Roll No: 30]
```

Here:

```text
Student
   ↓
implements Comparable
   ↓
compareTo()
   ↓
rollNo comparison
   ↓
Natural ordering
   ↓
Collections.sort()
```

---

# 6. What exactly is being compared?

When Java performs sorting, conceptually it compares objects like:

```text
Student(10)
     vs
Student(30)
```

The `compareTo()` method decides their order.

```java
this.rollNo - s.rollNo
```

So:

```text
10 - 30 = negative
```

Therefore:

```text
10 comes before 30
```

---

# 7. Important Difference: Comparable vs compareTo()

Don't confuse these two.

### Comparable

It is the **interface**:

```java
Comparable
```

### compareTo()

It is the **method inside Comparable** that defines the comparison logic:

```java
compareTo()
```

So:

```text
Comparable
    ↓
provides compareTo()
    ↓
compareTo() defines natural ordering
```

---

# 8. Most Important Rules

### Rule 1

`Comparable` belongs to:

```java
java.lang
```

Therefore, normally no import is required.

### Rule 2

The method is:

```java
compareTo()
```

### Rule 3

`compareTo()` returns:

```java
int
```

### Rule 4

The sign of the result matters:

```text
negative → current object before other
zero     → same ordering
positive → current object after other
```

### Rule 5

`Comparable` defines the object's:

> **Natural ordering**

---

# 9. ONE-MINUTE REVISION

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
Zero     → Equal
Positive → After
     ↓
Defines Natural Ordering
```

### One-line definition

> **Comparable is an interface used to define the natural ordering of objects through the `compareTo()` method.**

### Easy memory trick

```text
Comparable = "How should MY objects naturally be ordered?"
```

For example:

```text
Student → Roll Number
Employee → Employee ID
Book → Price
```

The field you choose becomes the basis of the object's **natural ordering**.
