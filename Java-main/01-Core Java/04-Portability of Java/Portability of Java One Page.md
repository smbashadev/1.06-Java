# Portability of Java — ONE PAGE

## 🔹 What is Portability?

**Portability** means the ability of a program to run on different computer systems or operating systems with little or no modification.

Java is famous for:

> **“Write Once, Run Anywhere” (WORA)**

---

# 🔹 Why is Java Portable?

The main reason is **bytecode + JVM**.

When you write Java:

```text
Hello.java
     ↓
   javac
     ↓
Hello.class
     ↓
   Bytecode
```

The `.class` file contains **platform-independent bytecode**.

That bytecode can run on different platforms through their appropriate JVM implementations.

```text
                 Java Source
                     ↓
                  javac
                     ↓
                  Bytecode
                     ↓
          ┌──────────┼──────────┐
          ↓          ↓          ↓
     Windows JVM  Linux JVM  macOS JVM
          ↓          ↓          ↓
       Windows     Linux      macOS
```

So normally, you don't need to rewrite the Java source code for each operating system.

---

# 🔹 Important: JVM Is Platform-Specific

This is a common exam question.

❌ **JVM is not platform-independent.**

Each operating system needs a suitable JVM implementation.

```text
Same Java Bytecode
       │
 ┌─────┼─────┐
 ↓     ↓     ↓
JVM   JVM   JVM
Win  Linux  macOS
```

Therefore:

> **Java bytecode is platform-independent, while the JVM is platform-dependent.**

---

# 🔹 How Does Portability Work?

### Step 1 — Write

```java
System.out.println("Hello");
```

### Step 2 — Compile

```text
.java
  ↓
javac
  ↓
.class
```

### Step 3 — Run

The same bytecode can be used with a suitable JVM on different platforms.

```text
                 Bytecode
                    ↓
       ┌────────────┼────────────┐
       ↓            ↓            ↓
 Windows JVM    Linux JVM    macOS JVM
       ↓            ↓            ↓
 Windows        Linux        macOS
```

---

# 🔹 Why Not Direct Machine Code?

If Java directly generated platform-specific machine code:

```text
Java
 ↓
Windows machine code
```

that code would generally be tied to Windows/CPU details.

Java instead uses:

```text
Java Source
    ↓
Bytecode
    ↓
JVM
    ↓
Platform-specific execution
```

The JVM acts as an abstraction layer between the bytecode and the underlying platform.

---

# 🔹 Example

Suppose you compile:

```java
public class Test {
    public static void main(String[] args) {
        System.out.println("Java");
    }
}
```

You get:

```text
Test.class
```

The bytecode can be run using compatible JVM implementations on:

* Windows
* Linux
* macOS

The Java source generally does not need to be rewritten just because the operating system changes.

---

# 🔥 Important Distinction

| Term            | Meaning                                            |
| --------------- | -------------------------------------------------- |
| **Source code** | Java program written by developer                  |
| **Bytecode**    | Intermediate instructions stored in `.class` files |
| **JVM**         | Executes bytecode                                  |
| **Portability** | Ability to run across platforms                    |
| **WORA**        | Write Once, Run Anywhere                           |

---

## ⭐ One-Line Exam Answer

> **Java is portable because Java source code is compiled into platform-independent bytecode, which can be executed on different operating systems using their respective JVM implementations.**

### 🧠 Remember:

**Java Source → Bytecode → JVM → Different Platforms**

**Bytecode = Portable**
**JVM = Platform-specific**
