# Data Types in Java — ONE PAGE 📘

A **data type** tells Java **what kind of value a variable can store** and helps determine how that value is represented and used.

## 1. Main Classification

Java data types are divided into **2 categories**:

```text
                 Data Types
                     │
          ┌──────────┴──────────┐
          ▼                     ▼
     Primitive              Non-Primitive
      Data Types             Data Types
          │                     │
    ┌─────┴─────┐         Examples:
    ▼           ▼         String
 Numeric     boolean      Array
    │                    Class
 ┌──┴──────────────┐     Interface
 ▼                 ▼
Integer         Floating
Types            Types
```

---

# 2. Primitive Data Types

Java has **8 primitive data types**.

| Type      |          Size | Example             | Used for              |
| --------- | ------------: | ------------------- | --------------------- |
| `byte`    |         8-bit | `byte x = 10;`      | Small integers        |
| `short`   |        16-bit | `short x = 100;`    | Small/medium integers |
| `int`     |        32-bit | `int x = 1000;`     | Integer values        |
| `long`    |        64-bit | `long x = 100000L;` | Large integers        |
| `float`   |        32-bit | `float x = 10.5f;`  | Decimal values        |
| `double`  |        64-bit | `double x = 10.5;`  | More precise decimals |
| `char`    |        16-bit | `char c = 'A';`     | Single character      |
| `boolean` | JVM-dependent | `boolean b = true;` | `true` / `false`      |

### Integer types

```text
byte → short → int → long
```

### Floating-point types

```text
float → double
```

---

# 3. Important Examples

```java
byte b = 10;
short s = 100;
int i = 1000;
long l = 100000L;

float f = 10.5f;
double d = 10.5;

char c = 'A';

boolean flag = true;
```

### ⚠️ Remember

```java
long x = 100000L;
float y = 10.5f;
```

`L` is commonly used for a `long` literal, and `f`/`F` is required for a `float` literal when the literal is written as a decimal.

---

# 4. Non-Primitive / Reference Types

These are types that refer to objects rather than being one of Java's eight primitive types.

Examples:

```java
String name = "John";

int[] numbers = {1, 2, 3};

Student s = new Student();
```

Common reference types include:

* **Class**
* **Object**
* **String**
* **Array**
* **Interface**
* **Enum**
* other reference types

Example:

```text
String
  ↓
Reference type
  ↓
Object
```

---

# 5. Primitive vs Reference Types

| Primitive                | Reference                                                  |
| ------------------------ | ---------------------------------------------------------- |
| 8 types                  | Many possible types                                        |
| Stores a primitive value | Stores a reference to an object/value                      |
| Examples: `int`, `char`  | Examples: `String`, arrays, classes                        |
| Built into Java language | Created/provided through classes, interfaces, arrays, etc. |
| Cannot be `null`         | Can generally be `null`                                    |

Example:

```java
int age = 20;
String name = "Alex";
```

Here:

```text
age  → primitive int
name → reference type String
```

---

# 🎯 Quick Memory Trick

### **8 Primitive Types**

> **B S I L F D C B**

```text
B → byte
S → short
I → int
L → long
F → float
D → double
C → char
B → boolean
```

### Most commonly used

```text
int       → whole numbers
double    → decimal numbers
char      → one character
boolean   → true/false
String    → text
```

## ⭐ Exam Answer

> **Java data types are classified into primitive and reference (non-primitive) types. Java has eight primitive types: byte, short, int, long, float, double, char, and boolean. Reference types include classes, objects, arrays, strings, interfaces, and enums.**
