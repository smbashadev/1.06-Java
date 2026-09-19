# Architecture of Java — 3 LEVELS 🎯

I'll use **your textbook architecture**:

```text
JDK
├── Java Compiler
└── JRE
    ├── JVM
    │   ├── Java Interpreter
    │   ├── JIT Compiler
    │   └── Runtime System
    ├── Class Loader
    ├── Bytecode Verifier
    └── Library Files
```

---

# 🟢 LEVEL 1 — Beginner

## 1. JDK — Java Development Kit

**JDK is used to develop Java programs.**

It contains:

```text
JDK
├── Java Compiler
└── JRE
```

### Java Compiler

The compiler is `javac`.

```text
Hello.java
    ↓
  javac
    ↓
Hello.class
```

So:

> **Compiler converts Java source code into bytecode.**

---

## 2. JRE — Java Runtime Environment

JRE provides the environment needed to run Java programs.

```text
JRE
├── JVM
├── Class Loader
├── Bytecode Verifier
└── Library Files
```

Remember:

> **JDK = Develop**
> **JRE = Run**

---

## 3. JVM — Java Virtual Machine

JVM executes Java bytecode.

```text
JVM
├── Java Interpreter
├── JIT Compiler
└── Runtime System
```

Remember:

> **JVM = Execute**

---

# 🟡 LEVEL 2 — Intermediate

Now understand what each component actually does.

### Java Compiler

Converts:

```text
Source Code → Bytecode
```

Example:

```text
Program.java
     ↓
    javac
     ↓
Program.class
```

---

### Class Loader

Loads required `.class` files into the JVM.

```text
.class file
    ↓
Class Loader
    ↓
JVM
```

**Think:**

> Class Loader = **Load**

---

### Bytecode Verifier

Checks loaded bytecode against JVM verification rules before execution proceeds.

```text
Bytecode
   ↓
Verifier
   ↓
Execution
```

**Think:**

> Verifier = **Check**

---

### Library Files

Provide predefined Java functionality.

Examples:

```text
String
System
Math
Scanner
ArrayList
```

**Think:**

> Library = **Ready-made functionality**

---

### Java Interpreter

Executes JVM bytecode.

```text
Bytecode
   ↓
Interpreter
   ↓
Execution
```

**Think:**

> Interpreter = **Execute**

---

### JIT Compiler

**JIT = Just-In-Time Compiler**

It can compile frequently executed bytecode into optimized native machine instructions.

```text
Bytecode
   ↓
JIT
   ↓
Native Code
   ↓
CPU
```

**Think:**

> JIT = **Optimize for speed**

---

### Runtime System

Supports program execution, including areas such as:

* memory management
* threads
* method execution
* exception handling
* garbage collection

**Think:**

> Runtime System = **Supports execution**

---

# 🔴 LEVEL 3 — Advanced

Now understand the **complete execution architecture**.

Suppose you write:

```java
class Hello {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

### Phase 1 — Development

```text
Hello.java
    ↓
JDK
    ↓
javac
    ↓
Hello.class
```

The `.class` file contains **JVM bytecode**.

---

### Phase 2 — Class Loading

The runtime loads the required class:

```text
Hello.class
    ↓
Class Loader
    ↓
JVM
```

---

### Phase 3 — Verification

The bytecode is checked:

```text
Loaded Bytecode
      ↓
Bytecode Verification
      ↓
Continue if valid
```

---

### Phase 4 — Execution

The JVM executes the bytecode.

Traditional architecture emphasizes:

```text
             JVM
              │
       ┌──────┼───────┐
       ▼      ▼       ▼
   Interpreter JIT  Runtime
                   System
```

The interpreter can execute bytecode directly.

The JIT can identify frequently executed code and compile/optimize it into native instructions.

---

### Complete flow

```text
                   Java Source
                    Hello.java
                        │
                        ▼
                       JDK
                        │
                        ▼
                 Java Compiler
                    (javac)
                        │
                        ▼
                     Bytecode
                    Hello.class
                        │
                        ▼
                       JRE
                        │
       ┌────────────────┼─────────────────┐
       ▼                ▼                 ▼
 Class Loader    Bytecode Verifier   Library Files
       │
       ▼
      JVM
       │
   ┌───┼────────────┐
   ▼   ▼            ▼
 JIT Interpreter  Runtime
                 System
   │
   ▼
Native Execution
   │
   ▼
   CPU
   │
   ▼
 Output
```

---

# 🧠 The Most Important Distinction

This is where most students get confused:

| Component              | What it does                       |
| ---------------------- | ---------------------------------- |
| **JDK**                | Develops Java applications         |
| **Compiler (`javac`)** | `.java` → bytecode                 |
| **JRE**                | Traditional runtime environment    |
| **Class Loader**       | Loads classes                      |
| **Bytecode Verifier**  | Verifies bytecode                  |
| **Libraries**          | Provides Java APIs                 |
| **JVM**                | Executes bytecode                  |
| **Interpreter**        | Executes bytecode                  |
| **JIT**                | Optimizes frequently executed code |
| **Runtime System**     | Supports execution                 |

---

# 🔥 3-Level Memory Formula

### 🟢 Level 1

```text
JDK → JRE → JVM
```

### 🟡 Level 2

```text
JDK
├── Compiler
└── JRE
    ├── JVM
    ├── Class Loader
    ├── Verifier
    └── Libraries
```

### 🔴 Level 3

```text
.java
 ↓
javac
 ↓
.class / Bytecode
 ↓
Class Loading
 ↓
Verification
 ↓
JVM
 ↓
Interpreter + JIT + Runtime System
 ↓
Native execution
 ↓
CPU
```

## ⭐ Exam-ready answer

> **Java architecture consists of the JDK, JRE, and JVM. The JDK is used for development and includes the Java compiler and, in the traditional textbook model, the JRE. The JRE provides the runtime environment, including the JVM, class loader, bytecode verifier, and library files. The JVM executes bytecode using the interpreter, JIT compiler, and runtime system.**

### 🔥 Golden rule

**JDK → Develop**
**JRE → Run**
**JVM → Execute**
**Compiler → Convert**
**Loader → Load**
**Verifier → Check**
**Interpreter → Execute**
**JIT → Optimize**
