# Constructor in Java — ONE PAGE 🧠

## 1. What is a Constructor?

A **constructor** is a special member of a class used to **initialize an object when the object is created**.

### Main characteristics

* Constructor name **must be the same as the class name**.
* It has **no return type**, not even `void`.
* It is executed automatically when an object is created using `new`.
* Constructors can be **overloaded**.
* Constructors are **not inherited**.
* If you don't write any constructor, the compiler provides a **default constructor** (subject to the usual rules).

### Basic syntax

```java
class Student {

    Student() {
        // initialization
    }
}
```

Object creation:

```java
Student s = new Student();
```

Flow:

```text
new Student()
     ↓
Constructor executes
     ↓
Object gets initialized
```

---

# 2. Normal Program — Without `this`

Suppose we don't use a constructor:

```java
class Student {

    int id;
    String name;

    public static void main(String[] args) {

        Student s = new Student();

        s.id = 101;
        s.name = "Ravi";

        System.out.println(s.id + " " + s.name);
    }
}
```

Output:

```text
101 Ravi
```

Here we create the object first and then initialize its instance variables separately.

---

# 3. Constructor Without `this` — Print Output in One Line

We can initialize the object during object creation:

```java
class Student {

    int id;
    String name;

    Student(int i, String n) {
        id = i;
        name = n;
    }

    public static void main(String[] args) {
        System.out.println(new Student(101, "Ravi").id + " " +
                           new Student(101, "Ravi").name);
    }
}
```

But this creates **two different objects**, which is unnecessary.

A better version is:

```java
class Student {

    int id;
    String name;

    Student(int i, String n) {
        id = i;
        name = n;
    }

    public static void main(String[] args) {

        Student s = new Student(101, "Ravi");

        System.out.println(s.id + " " + s.name);
    }
}
```

Output:

```text
101 Ravi
```

The constructor initializes the object:

```text
new Student(101, "Ravi")
        ↓
constructor
        ↓
id = 101
name = "Ravi"
```

---

# 4. Constructor + Shadowing Problem

Suppose we write:

```java
class Student {

    int id;
    String name;

    Student(int id, String name) {
        id = id;
        name = name;
    }

    public static void main(String[] args) {

        Student s = new Student(101, "Ravi");

        System.out.println(s.id + " " + s.name);
    }
}
```

Output:

```text
0 null
```

😵 Why?

Because the constructor parameters have the **same names** as the instance variables.

```java
Student(int id, String name)
```

Inside:

```java
id = id;
name = name;
```

both sides refer to the **nearest/local parameter variables**.

The instance variables remain unchanged.

This is called **variable shadowing**.

---

# 5. Avoid Shadowing Using `this`

`this` refers to the **current object**.

So:

```java
this.id
```

means:

> The `id` belonging to the current object.

Correct program:

```java
class Student {

    int id;
    String name;

    Student(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public static void main(String[] args) {

        Student s = new Student(101, "Ravi");

        System.out.println(s.id + " " + s.name);
    }
}
```

Output:

```text
101 Ravi
```

### Understand this line:

```java
this.id = id;
```

```text
this.id  → instance variable
id       → constructor parameter
```

Similarly:

```java
this.name = name;
```

```text
this.name → instance variable
name      → parameter
```

### Golden rule

> **When a local/parameter variable shadows an instance variable, `this` can be used to explicitly refer to the current object's instance variable.**

---

# 6. Non-Declaration of Local Variables Using Constructor

Consider:

```java
class Student {

    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void main(String[] args) {

        Student s = new Student(101, "Ravi");

        System.out.println(s.id + " " + s.name);
    }
}
```

Notice:

```java
Student s = ...
```

is a local variable declaration.

But we can avoid declaring a local reference variable:

```java
class Student {

    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void main(String[] args) {

        System.out.println(new Student(101, "Ravi").id);
    }
}
```

Output:

```text
101
```

Here:

```java
new Student(101, "Ravi")
```

creates the object, but we don't store its reference in a local variable.

⚠️ If you need to access multiple fields repeatedly, using a reference variable is usually clearer.

---

# 7. Parameterized Constructor

A constructor that accepts parameters is called a **parameterized constructor**.

Example:

```java
class Student {

    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void main(String[] args) {

        Student s = new Student(101, "Ravi");

        System.out.println(s.id);
        System.out.println(s.name);
    }
}
```

Here:

```java
Student(int id, String name)
```

is a parameterized constructor.

---

# 8. Non-Parameterized Constructor

A constructor that takes **no parameters** is commonly called a **no-argument constructor**.

Example:

```java
class Student {

    int id;
    String name;

    Student() {
        id = 101;
        name = "Ravi";
    }

    public static void main(String[] args) {

        Student s = new Student();

        System.out.println(s.id + " " + s.name);
    }
}
```

Output:

```text
101 Ravi
```

### Important terminology

Many introductory textbooks call this a **non-parameterized constructor**.

More precisely, Java terminology commonly uses **no-argument constructor** for a constructor with zero parameters.

---

# 9. Can We Have Both?

Yes! This is constructor overloading.

```java
class Student {

    int id;
    String name;

    Student() {
        id = 0;
        name = "Unknown";
    }

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void main(String[] args) {

        Student s1 = new Student();
        Student s2 = new Student(101, "Ravi");

        System.out.println(s1.id + " " + s1.name);
        System.out.println(s2.id + " " + s2.name);
    }
}
```

Output:

```text
0 Unknown
101 Ravi
```

The constructors have the same name because the class name is `Student`, but their parameter lists differ.

---

# 10. Local Variables vs Instance Variables

| Feature                         | Local Variable                              | Instance Variable                          |
| ------------------------------- | ------------------------------------------- | ------------------------------------------ |
| Declared                        | Inside method/constructor/block             | Inside class, outside methods/constructors |
| Belongs to                      | Method/block execution                      | Object                                     |
| Scope                           | Limited to its method/block                 | Throughout applicable instance context     |
| Gets default value?             | ❌ No                                        | ✅ Yes                                      |
| Must be initialized before use? | ✅ Yes                                       | ❌ Not necessarily                          |
| Stored conceptually             | In a method's execution frame/local storage | As part of the object                      |
| Accessed using object?          | ❌ No                                        | ✅ Usually                                  |
| Example                         | `int x = 10;` inside method                 | `int id;` inside class                     |

### Example

```java
class Student {

    int id;              // instance variable

    void display() {

        int marks = 90;  // local variable

        System.out.println(id);
        System.out.println(marks);
    }
}
```

Here:

```text
id
↓
Instance variable
↓
Belongs to Student object

marks
↓
Local variable
↓
Exists within display()
```

---

# 11. Very Important: Default Values

Instance variables receive default values if not explicitly initialized.

For example:

```java
class Demo {

    int x;
    String name;

    public static void main(String[] args) {

        Demo d = new Demo();

        System.out.println(d.x);
        System.out.println(d.name);
    }
}
```

Output:

```text
0
null
```

But this is different:

```java
class Demo {

    public static void main(String[] args) {

        int x;

        System.out.println(x);  // ❌
    }
}
```

A local variable does **not** receive a default value that makes it automatically usable. It must be definitely assigned before reading it.

---

# 🧠 CONSTRUCTOR MASTER FLOW

```text
                CLASS
                  │
                  ▼
             new Student()
                  │
                  ▼
             Object created
                  │
                  ▼
          Constructor executes
                  │
                  ▼
           Object initialized
```

With parameters:

```text
new Student(101, "Ravi")
          │
          ▼
Student(int id, String name)
          │
          ▼
this.id = id
this.name = name
```

---

# ⭐ MOST IMPORTANT POINTS

### Constructor

> Special class member used to initialize objects.

### Constructor name

```text
Same as class name
```

### Return type

```text
No return type
```

### Execution

```text
Automatically executes during object creation
```

### Parameterized constructor

```java
Student(int id, String name)
```

### No-argument constructor

```java
Student()
```

### Shadowing

```java
int id;                 // instance variable

Student(int id) {       // parameter shadows it
    id = id;             // both refer to parameter
}
```

### Solution

```java
this.id = id;
```

```text
this.id → instance variable
id      → parameter
```

### Local vs Instance

```text
Local variable
→ declared inside method/constructor/block
→ no automatic default value

Instance variable
→ declared inside class but outside methods/constructors
→ gets default value
→ belongs to object
```

## 🔥 One-line memory trick

> **Constructor initializes the object; parameters provide the input; `this` identifies the current object's instance variables and resolves shadowing.**
