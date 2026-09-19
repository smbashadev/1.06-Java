# Architecture of Java — ONE PAGE

> **For your syllabus, use this architecture exactly:**
> **JDK → JDK has Java Compiler + JRE → JRE has JVM + Class Loader + Bytecode Verifier + Library Files → JVM has JIT + Runtime System + Java Interpreter.**

```text
                         JAVA ARCHITECTURE
                                │
                                ▼
                         ┌─────────────┐
                         │     JDK     │
                         └──────┬──────┘
                                │
                  ┌─────────────┴─────────────┐
                  ▼                           ▼
          Java Compiler (javac)              JRE
                                              │
                   ┌──────────────────────────┼──────────────────────┐
                   ▼                          ▼                      ▼
                  JVM                  Class Loader          Bytecode Verifier
                   │
             ┌─────┼─────────────┐
             ▼     ▼             ▼
            JIT  Runtime      Java Interpreter
                 System
                                                                   
                                              │
                                              ▼
                                        Library Files
```

## 1. JDK — Java Development Kit

**JDK = Java Development Kit**

It is the complete kit used to **develop Java applications**.

In your syllabus architecture:

```text
JDK
├── Java Compiler
└── JRE
```

### Java Compiler

The compiler is `javac`.

It converts:

```text
.java  ──javac──>  .class
```

That means:

**Source Code → Bytecode**

---

# 2. JRE — Java Runtime Environment

**JRE** provides the environment required to **run Java programs**.

According to the architecture you're studying:

```text
JRE
├── JVM
├── Class Loader
├── Bytecode Verifier
└── Library Files
```

### Class Loader

Loads required `.class` files into the JVM.

```text
.class file
     ↓
Class Loader
     ↓
JVM
```

### Bytecode Verifier

Checks the bytecode before execution to ensure it satisfies JVM constraints.

```text
Bytecode
   ↓
Verifier
   ↓
JVM Execution
```

### Library Files

Java provides a large collection of predefined classes and APIs that programs can use.

Examples:

```java
String
System
Scanner
ArrayList
```

---

# 3. JVM — Java Virtual Machine

The **JVM executes Java bytecode**.

In your syllabus:

```text
JVM
├── Java Interpreter
├── JIT Compiler
└── Runtime System
```

### Java Interpreter

Executes JVM bytecode.

Conceptually:

```text
Bytecode
   ↓
Interpreter
   ↓
Execution
```

### JIT — Just-In-Time Compiler

JIT improves performance by compiling frequently executed bytecode into native machine instructions at runtime.

```text
Bytecode
   ↓
JIT
   ↓
Native Machine Code
   ↓
CPU
```

### Runtime System

Provides the runtime mechanisms needed while the Java program is executing, including things such as memory management and execution support.

---

# 🔥 Complete Flow

Put everything together:

```text
                   JAVA SOURCE CODE
                         │
                         ▼
                    ┌─────────┐
                    │   JDK   │
                    └────┬────┘
                         │
                    Java Compiler
                       (javac)
                         │
                         ▼
                     BYTECODE
                     (.class)
                         │
                         ▼
                    ┌─────────┐
                    │   JRE   │
                    └────┬────┘
                         │
          ┌──────────────┼──────────────┐
          ▼              ▼              ▼
    Class Loader   Bytecode Verifier  Libraries
          │
          ▼
        ┌─────┐
        │ JVM │
        └──┬──┘
           │
     ┌─────┼──────────────┐
     ▼     ▼              ▼
 Interpreter JIT      Runtime System
     │     Compiler
     │       │
     └───┬───┘
         ▼
      Execution
         │
         ▼
        CPU
         │
         ▼
       OUTPUT
```

## 🧠 Easy Memory Trick

**JDK → JRE → JVM**

Then:

**JRE → Class Loader + Verifier + Libraries**

**JVM → Interpreter + JIT + Runtime System**

### One-line exam answer

> **Java architecture consists of the JDK for development, JRE for the runtime environment, and JVM for executing bytecode; the JDK contains the compiler and JRE, while the JRE contains the JVM, class loader, bytecode verifier, and libraries, with the JVM using the interpreter, JIT compiler, and runtime system for execution.**

**Note:** This is the **traditional/textbook architecture** commonly taught in introductory Java courses. Modern JDK distributions don't always package a separately installable JRE in exactly this old hierarchy, but the above model is appropriate for the syllabus definition you've specified.
