# Constructor in Java — 3 LEVEL 

Think of this as **Level 1 = remember**, **Level 2 = understand**, **Level 3 = master**.

---

# 🟢 LEVEL 1 — BASIC

## 1. What is a Constructor?

A **constructor** is a special member of a class used mainly to **initialize an object when it is created**.

### Three rules

```text
1. Constructor name = Class name
2. No return type
3. Invoked during object creation
```

Example:

```java
class Student {

    Student() {
        System.out.println("Constructor executed");
    }

    public static void main(String[] args) {
        Student s = new Student();
    }
}
```

Output:

```text
Constructor executed
```

---

## 2. Constructor vs Method

| Constructor                        | Method                    |
| ---------------------------------- | ------------------------- |
| Same name as class                 | Can have any valid name   |
| No return type                     | Has return type or `void` |
| Used mainly for initialization     | Performs an operation     |
| Invoked as part of object creation | Usually called explicitly |
| Can be overloaded                  | Can be overloaded         |

### Remember:

```java
Student()       // Constructor
void Student()  // Method
```

---

# 🟡 LEVEL 2 — UNDERSTAND

## 3. Normal Program Without Constructor

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

Flow:

```text
Create object
     ↓
Assign id
     ↓
Assign name
     ↓
Print
```

---

## 4. Using Constructor

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

Flow:

```text
new Student(101, "Ravi")
          ↓
Constructor receives values
          ↓
id = 101
name = Ravi
```

---

# 5. Parameterized Constructor

A constructor that receives parameters:

```java
Student(int id, String name)
```

Example:

```java
class Student {

    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

Call:

```java
Student s = new Student(101, "Ravi");
```

---

# 6. No-Argument Constructor

A constructor with no parameters:

```java
class Student {

    int id;
    String name;

    Student() {
        id = 101;
        name = "Ravi";
    }
}
```

Call:

```java
Student s = new Student();
```

Output values:

```text
101 Ravi
```

---

# 7. `this` Keyword

`this` refers to the **current object**.

Consider:

```java
class Student {

    int id;

    Student(int id) {
        this.id = id;
    }
}
```

Here there are two `id`s:

```text
this.id → instance variable
id      → constructor parameter
```

Therefore:

```java
this.id = id;
```

means:

```text
object's id = parameter id
```

---

# 8. Shadowing Problem

Without `this`:

```java
class Student {

    int id;

    Student(int id) {
        id = id;
    }

    public static void main(String[] args) {

        Student s = new Student(101);

        System.out.println(s.id);
    }
}
```

Output:

```text
0
```

Why?

The constructor parameter `id` shadows the instance variable `id`.

### Solution

```java
this.id = id;
```

---

# 9. Local vs Instance Variable

| Instance Variable                                   | Local Variable                                      |
| --------------------------------------------------- | --------------------------------------------------- |
| Declared inside class, outside methods/constructors | Declared inside method/constructor/block            |
| Belongs to object                                   | Belongs to local execution scope                    |
| Gets default value                                  | Does not get an automatic usable default value      |
| Example: `int id;`                                  | Example: `int x = 10;`                              |
| Can be accessed using `this`                        | Cannot be accessed using `this` as a local variable |

Example:

```java
class Student {

    int id;             // Instance variable

    Student(int value) {

        int x = 10;     // Local variable

        this.id = value;
    }
}
```

---

# 10. Default Values

Instance variables automatically get default values:

```text
int       → 0
double    → 0.0
boolean   → false
reference → null
```

But:

```java
void display() {

    int x;

    System.out.println(x); // ❌
}
```

A local variable must be assigned before it is read.

---

# 🔴 LEVEL 3 — MASTER

## 11. Constructor Overloading

A class can have multiple constructors with **different parameter lists**.

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
}
```

Now:

```java
Student s1 = new Student();
Student s2 = new Student(101, "Ravi");
```

Java selects the appropriate constructor based on the arguments.

---

# 12. Constructor Overloading Rules

### Valid

```java
Student()
Student(int)
Student(int, String)
Student(String, int)
```

### Invalid

Changing only return type doesn't apply because constructors don't have return types.

Also, these cannot be distinguished merely by modifiers:

```java
public Student(int x)
private Student(int x)
```

They have the same parameter list and cannot coexist as separate constructors.

---

# 13. Default Constructor — Important Trap

If you write **no constructor at all**:

```java
class Student {
}
```

Java provides a compiler-generated default constructor.

But if you write:

```java
class Student {

    Student(int id) {
    }
}
```

then:

```java
Student s = new Student();
```

❌ Error.

Why?

Because the compiler no longer automatically supplies a no-argument default constructor.

If you want both:

```java
class Student {

    Student() {
    }

    Student(int id) {
    }
}
```

---

# 14. `this()` — Constructor Chaining

Don't confuse:

```text
this
```

with:

```text
this()
```

### `this`

Current object:

```java
this.id
```

### `this()`

Calls another constructor in the same class:

```java
this(101, "Ravi");
```

Example:

```java
class Student {

    int id;
    String name;

    Student() {
        this(101, "Ravi");
    }

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

Flow:

```text
Student()
   ↓
this(101, "Ravi")
   ↓
Student(int, String)
   ↓
Initialize fields
```

### Rule

`this(...)` must be the **first statement** in the constructor.

---

# 15. `super()`

`super()` invokes a superclass constructor.

```java
class Animal {

    Animal() {
        System.out.println("Animal");
    }
}

class Dog extends Animal {

    Dog() {
        super();
        System.out.println("Dog");
    }
}
```

Output:

```text
Animal
Dog
```

Remember:

```text
this()  → same class constructor
super() → superclass constructor
```

---

# 16. Constructor + Inheritance

Constructors:

```text
❌ Cannot be inherited
❌ Cannot be overridden
✅ Can be overloaded
```

But when a subclass object is created, a superclass constructor is invoked as part of object construction.

---

# 17. Can Constructor Be Private?

✅ Yes.

```java
class Demo {

    private Demo() {
        System.out.println("Constructor");
    }
}
```

Useful when you want to control object creation.

---

# 18. Can Constructor Be Static?

❌ No.

```java
static Demo() { }  // ❌
```

---

# 19. Can Constructor Be Final?

❌ No.

```java
final Demo() { }  // ❌
```

---

# 20. Can Constructor Be Abstract?

❌ No.

```java
abstract Demo() { }  // ❌
```

---

# 21. Can Constructor Have `void`?

❌ No.

This:

```java
void Student() {
}
```

is a **method**, not a constructor.

---

# 22. Can We Call a Constructor Like a Method?

❌ No.

Invalid:

```java
s.Student();
```

Correct:

```java
new Student();
```

or constructor chaining:

```java
this();
super();
```

---

# 23. Can We Create an Object Without a Local Reference Variable?

Yes.

```java
class Student {

    int id;

    Student(int id) {
        this.id = id;
    }

    public static void main(String[] args) {

        System.out.println(new Student(101).id);
    }
}
```

Output:

```text
101
```

There is no:

```java
Student s
```

local reference variable.

---

# 24. Complete Master Program

```java
class Student {

    int id;
    String name;
    int age;

    // No-argument constructor
    Student() {
        this(0, "Unknown", 0);
    }

    // Parameterized constructor
    Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    void display() {
        System.out.println(id + " " + name + " " + age);
    }

    public static void main(String[] args) {

        Student s1 = new Student();
        Student s2 = new Student(101, "Ravi", 20);

        s1.display();
        s2.display();
    }
}
```

Output:

```text
0 Unknown 0
101 Ravi 20
```

---

# 🧠 3-LEVEL REVISION MAP

```text
                 CONSTRUCTOR
                      │
       ┌──────────────┼──────────────┐
       ↓              ↓              ↓
     BASIC         UNDERSTAND      MASTER
       │              │              │
 Same class       Parameterized    Overloading
 No return type   No-argument      this()
 Object creation  Shadowing        super()
 Initialization   this             Inheritance
                                 Default constructor
```

---

# 🚨 10 DOUBT KILLERS

| Question                               | Answer |
| -------------------------------------- | ------ |
| Same name as class?                    | ✅ Yes  |
| Return type?                           | ❌ None |
| Can it have `void`?                    | ❌ No   |
| Can it be overloaded?                  | ✅ Yes  |
| Can it be overridden?                  | ❌ No   |
| Can it be inherited?                   | ❌ No   |
| Can it be private?                     | ✅ Yes  |
| Can it be static?                      | ❌ No   |
| Can `this` be used?                    | ✅ Yes  |
| Can `this()` call another constructor? | ✅ Yes  |

---

# 🎯 EXAM MEMORY

### Level 1 — Definition

> **Constructor = special class member + same class name + no return type + object initialization.**

### Level 2 — Main types

```text
No-argument
Parameterized
```

### Level 3 — Advanced

```text
this
this()
super()
constructor overloading
default constructor
shadowing
inheritance
```

### 🔥 One sentence to remember

> **When `new` creates an object, the appropriate constructor is invoked to initialize that object; `this` identifies the current object, while `this()` and `super()` are used for constructor chaining.**
