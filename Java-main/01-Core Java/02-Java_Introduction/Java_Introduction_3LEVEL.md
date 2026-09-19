# Java Introduction 

## 🟢 Level 1 — Beginner

### What is Java?

**Java is a high-level, object-oriented, class-based programming language and software platform.**

It was developed at **Sun Microsystems** and publicly introduced in **1995**.

Java is used to build:

* Web applications
* Backend/API applications
* Enterprise software
* Banking systems
* Cloud applications
* Distributed systems

### Basic Java program

```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Output:

```text
Hello, World!
```

### Basic execution

```text
Java Source
    ↓
Compiler
    ↓
Bytecode
    ↓
JVM
    ↓
Output
```

The famous idea behind Java is:

> **Write Once, Run Anywhere**

---

# 🟡 Level 2 — Intermediate

## Java, JDK, JVM — don't confuse them

### Java

The **programming language**.

### JDK

**Java Development Kit**

Used to **develop** Java programs.

It includes tools such as the Java compiler.

```text
JDK
 ├── javac
 ├── Java tools
 ├── Libraries
 └── JVM/runtime components
```

### JVM

**Java Virtual Machine**

Used to **execute Java bytecode**.

### The complete process

```text
Hello.java
    ↓
javac
    ↓
Hello.class
    ↓
Bytecode
    ↓
JVM
    ↓
Program runs
```

---

## Why is Java platform-independent?

Suppose you have the same Java program on Windows and Linux.

You don't normally compile it directly into Windows-specific or Linux-specific machine code.

Instead:

```text
             Java Bytecode
                    ↓
          ┌─────────┴─────────┐
          ↓                   ↓
     Windows JVM          Linux JVM
          ↓                   ↓
       Windows             Linux
```

The **JVM provides the platform-specific layer**.

That's the core reason Java became portable.

---

## Java's major characteristics

Java is commonly described as:

* **Simple**
* **Object-oriented**
* **Platform-independent**
* **Secure**
* **Robust**
* **Portable**
* **Multithreaded**
* **High-performance through JVM optimization**
* **Distributed/network-friendly**
* **Dynamically extensible**

---

# 🔴 Level 3 — Deep Understanding

Now let's understand what actually happens inside the JVM.

### Step 1 — Write source code

```java
int x = 10;
int y = 20;

System.out.println(x + y);
```

### Step 2 — Compile

The Java compiler:

```text
javac
```

converts the source code into **bytecode**.

```text
.java
 ↓
javac
 ↓
.class
```

### Step 3 — JVM loads the bytecode

The JVM loads and verifies classes before executing them.

### Step 4 — JVM executes

The JVM can interpret bytecode and, in modern implementations, use **JIT compilation** to compile frequently executed code into optimized native machine code.

```text
Bytecode
   ↓
JVM
   ↓
Interpretation + JIT optimization
   ↓
Native execution
```

### Step 5 — Memory is managed

Java uses **garbage collection**.

When objects are no longer reachable by the application, the JVM can reclaim their memory.

```text
Create object
     ↓
Use object
     ↓
No longer reachable
     ↓
Garbage Collector
     ↓
Memory reclaimed
```

---

# Java and Object-Oriented Programming

Java organizes programs around **classes and objects**.

Example:

```java
class Student {
    String name;

    void study() {
        System.out.println(name + " is studying");
    }
}
```

Create an object:

```java
Student s = new Student();
s.name = "Rahul";
s.study();
```

The four major OOP concepts are:

```text
             OOP
              │
    ┌─────────┼─────────┐
    ↓         ↓         ↓
Encapsulation Inheritance Polymorphism
              │
          Abstraction
```

---

# Java's Architecture

The easiest deep mental model is:

```text
┌─────────────────────────────┐
│       Java Application      │
├─────────────────────────────┤
│       Java Libraries        │
├─────────────────────────────┤
│             JVM             │
│  ┌──────┬───────┬─────────┐ │
│  │ GC   │  JIT  │ Runtime │ │
│  └──────┴───────┴─────────┘ │
├─────────────────────────────┤
│       Operating System      │
├─────────────────────────────┤
│          Hardware           │
└─────────────────────────────┘
```

This architecture is what makes Java more than just a language.

---

# 🎯 3-Level Revision

### 🟢 Level 1

**Java = programming language**

**JDK = development**

**JVM = execution**

**Bytecode = intermediate code**

---

### 🟡 Level 2

```text
.java
 ↓
javac
 ↓
.class
 ↓
JVM
 ↓
Execution
```

Java is popular because of:

**Portability + OOP + Security + Garbage Collection + Huge Ecosystem**

---

### 🔴 Level 3

The real power of Java comes from the **JVM**.

```text
Source Code
    ↓
Compiler
    ↓
Bytecode
    ↓
Class Loading + Verification
    ↓
JVM
    ↓
Interpreter / JIT
    ↓
Native execution
```

Meanwhile, the JVM manages memory, garbage collection, threads, exceptions, and runtime optimization.

### ⭐ One-line definition

> **Java is a high-level, object-oriented programming language whose programs are compiled into bytecode and executed by the JVM, providing portability across different platforms.**
