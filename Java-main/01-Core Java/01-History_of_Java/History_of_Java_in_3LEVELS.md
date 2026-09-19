# History of Java — 3 Levels

## 🟢 Level 1 — Beginner

Java is a **high-level, object-oriented programming language** developed by **Sun Microsystems**.

* **1991:** The **Green Project** started at Sun Microsystems.
* **James Gosling** led the development team.
* The original language was called **Oak**.
* **1994:** Oak was renamed **Java**.
* **1995:** Java was publicly introduced.
* **1996:** JDK 1.0 was released.

The main idea behind Java was:

> **Write Once, Run Anywhere**

Java achieves this using **bytecode** and the **Java Virtual Machine (JVM)**.

```text
Java Program
     ↓
Compiler
     ↓
Bytecode
     ↓
JVM
     ↓
Any supported platform
```

---

## 🟡 Level 2 — Intermediate

Java was originally designed for **consumer electronic devices**, not specifically for web development.

The Green Project created Oak to solve problems involving:

* different hardware
* portability
* reliability
* security
* memory management

When the consumer-electronics opportunity did not develop as expected, the team turned toward the **Internet and World Wide Web**.

Java became popular because it could run **applets** inside web browsers.

Later, Java became much more important for **server-side and enterprise applications**.

Important technologies included:

* JDBC
* Servlets
* JSP
* J2EE
* Enterprise JavaBeans

Major language milestones included:

| Version  | Important development        |
| -------- | ---------------------------- |
| Java 1.0 | First major release          |
| Java 5   | Generics, annotations, enums |
| Java 8   | Lambdas, Streams             |
| Java 9   | Module system                |
| Java 11  | LTS release                  |
| Java 17  | LTS release                  |
| Java 21  | LTS + virtual threads        |
| Java 25  | LTS release                  |

---

## 🔴 Level 3 — Deep Understanding

The most important historical contribution of Java is **not simply its syntax**. It is the **JVM-based execution model**.

Traditional compiled languages generally follow:

```text
Source Code
     ↓
Compiler
     ↓
Native Machine Code
     ↓
Specific CPU
```

Java introduced a powerful abstraction:

```text
Source Code
     ↓
Java Compiler
     ↓
Bytecode
     ↓
JVM
     ↓
Native execution
```

The JVM separates the Java program from the underlying hardware.

This created a platform-independent software model:

```text
                 Java Bytecode
                       ↓
          ┌────────────┼────────────┐
          ↓            ↓            ↓
       JVM/Linux   JVM/Windows   JVM/macOS
          ↓            ↓            ↓
       Hardware     Hardware     Hardware
```

The JVM later became highly sophisticated through **JIT compilation**, garbage collection, runtime optimization, and advanced concurrency mechanisms.

Java also evolved from its original verbose style. Java 8 introduced functional programming features such as lambdas and Streams; later versions introduced records, pattern matching, sealed classes, and virtual threads.

The corporate history is also important: **Oracle acquired Sun Microsystems in 2010**, after which Oracle became the primary steward of Java. Java subsequently moved to a faster release cycle, with major **LTS versions** such as Java 11, 17, 21, and 25.

### ⭐ Final takeaway

```text
1991 → Green Project
       ↓
      Oak
       ↓
     Java
       ↓
Bytecode + JVM
       ↓
 Web/Applets
       ↓
Enterprise Java
       ↓
Modern Java
       ↓
Cloud + Backend + Large-scale Systems
```

**In one sentence:** Java began as an attempt to create portable software for consumer devices, found its breakthrough through the Internet, and became a global programming platform because of its **JVM, portability, security, ecosystem, and continuous evolution**.
