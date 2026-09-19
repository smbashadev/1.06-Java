# 14. Generics in Java — 3LEVEL

We will learn every sub-concept in **3 levels**:

* 🟢 **Level 1 — Basic:** What is it?
* 🟡 **Level 2 — Intermediate:** How and why does it work?
* 🔴 **Level 3 — Advanced:** Rules, internal reasoning, pitfalls, and interview-level understanding.

---

# 1. Generic Classes

## 🟢 Level 1 — Basic

A **generic class** is a class that can work with different data types using a **type parameter**.

Syntax:

```java
class Box<T>
{
    T value;

    void set(T value)
    {
        this.value = value;
    }

    T get()
    {
        return value;
    }
}
```

Here:

```java
T
```

is a **type parameter**.

We can create:

```java
Box<String> b1 = new Box<>();
Box<Integer> b2 = new Box<>();
```

So:

```text
Box<String>  → T becomes String
Box<Integer> → T becomes Integer
```

Example:

```java
Box<String> b = new Box<>();

b.set("Java");

String s = b.get();

System.out.println(s);
```

Output:

```text
Java
```

---

## 🟡 Level 2 — Intermediate

The main purpose of a generic class is **reusability + type safety**.

Instead of creating:

```java
class StringBox
```

and:

```java
class IntegerBox
```

we create one:

```java
class Box<T>
```

and specialize it when creating an object:

```java
Box<String>
Box<Integer>
Box<Double>
```

### Multiple type parameters

A generic class can have multiple type parameters:

```java
class Pair<K, V>
{
    K key;
    V value;

    Pair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}
```

Usage:

```java
Pair<Integer, String> p =
    new Pair<>(101, "Java");
```

Here:

```text
K → Integer
V → String
```

Common conventions:

| Letter | Typical meaning |
| ------ | --------------- |
| `T`    | Type            |
| `E`    | Element         |
| `K`    | Key             |
| `V`    | Value           |
| `N`    | Number          |

These are **conventions**, not keywords.

---

## 🔴 Level 3 — Advanced

A generic type parameter is **not a normal runtime variable**.

For example:

```java
class Box<T>
{
    T value;
}
```

Java implements generics primarily through **type erasure**.

Conceptually, generic type information is used heavily by the compiler for type checking, while the JVM does not maintain separate runtime versions such as:

```text
Box<String>
Box<Integer>
```

as completely different classes.

This is one reason some operations are illegal:

```java
new T();          // ❌
T.class;          // ❌
new T[10];        // ❌
```

Also, primitive types cannot directly be type arguments:

```java
Box<int>          // ❌
Box<Integer>      // ✅
```

---

# 2. Generic Methods

## 🟢 Level 1 — Basic

A **generic method** is a method that has its own type parameter.

Example:

```java
static <T> void display(T value)
{
    System.out.println(value);
}
```

Notice:

```java
<T>
```

appears **before the return type**.

Use:

```java
display("Java");
display(100);
display(10.5);
```

The same method works with different types.

---

## 🟡 Level 2 — Intermediate

A generic method can return a generic type too.

```java
static <T> T getValue(T value)
{
    return value;
}
```

Usage:

```java
String s = getValue("Java");

Integer n = getValue(100);
```

The compiler can infer `T`.

You can also explicitly provide the type in appropriate contexts:

```java
String s = Demo.<String>getValue("Java");
```

---

### Generic class vs generic method

Generic class:

```java
class Box<T>
{
}
```

Here `T` belongs to the **class**.

Generic method:

```java
<T> void display(T value)
{
}
```

Here `T` belongs to the **method**.

A non-generic class can contain a generic method:

```java
class Demo
{
    static <T> void display(T value)
    {
        System.out.println(value);
    }
}
```

---

## 🔴 Level 3 — Advanced

A generic method's type parameter can have bounds.

Example:

```java
static <T extends Number> void display(T value)
{
    System.out.println(value);
}
```

Now `T` must be `Number` or a subtype of `Number`.

Therefore:

```java
display(10);       // ✅
display(10.5);     // ✅
```

But:

```java
display("Java");   // ❌
```

because `String` does not extend `Number`.

Multiple bounds are also possible, subject to Java's rules:

```java
<T extends SomeClass & Interface1 & Interface2>
```

A class bound, if present, must come first.

---

# 3. Type Safety

## 🟢 Level 1 — Basic

**Type safety** means Java prevents you from using the wrong data type where another type is expected.

Example:

```java
List<String> names = new ArrayList<>();

names.add("Java");
names.add("Python");
```

But:

```java
names.add(100);      // ❌
```

The compiler rejects it.

Why?

Because:

```java
List<String>
```

means:

> This list is intended to contain Strings.

---

## 🟡 Level 2 — Intermediate

Without Generics:

```java
List list = new ArrayList();

list.add("Java");
list.add(100);
```

Different types can be inserted.

Later:

```java
String s = (String) list.get(1);
```

But element `1` is actually an `Integer`.

This causes:

```text
ClassCastException
```

With Generics:

```java
List<String> list = new ArrayList<>();

list.add("Java");
list.add(100);      // ❌ compile-time error
```

The problem is detected **before execution**.

Therefore Generics provide stronger compile-time type checking.

---

## 🔴 Level 3 — Advanced

Generics primarily provide **compile-time type safety**.

Consider:

```java
List<String> list = new ArrayList<>();
```

The compiler tracks that the list is intended to contain `String`.

This eliminates many explicit casts:

Without Generics:

```java
String s = (String) list.get(0);
```

With Generics:

```java
String s = list.get(0);
```

However, Generics do **not** make every possible runtime type problem disappear.

For example, raw types and unchecked operations can weaken compile-time guarantees:

```java
List list = new ArrayList();
```

This is a **raw type** and should generally be avoided in new code.

---

# 4. Wildcards

## 🟢 Level 1 — Basic

A wildcard is:

```java
?
```

It means:

> **An unknown type.**

Example:

```java
List<?> list;
```

This can refer to:

```java
List<String>
List<Integer>
List<Double>
```

and other parameterized `List` types.

---

## 🟡 Level 2 — Intermediate

Suppose we want one method to accept a list regardless of its element type:

```java
static void display(List<?> list)
{
    for (Object x : list)
    {
        System.out.println(x);
    }
}
```

Now:

```java
List<String> a = new ArrayList<>();
List<Integer> b = new ArrayList<>();

display(a);
display(b);
```

Both work.

### Important distinction

These are **not the same**:

```java
List<Object>
```

and:

```java
List<?>
```

`List<Object>` means:

> specifically a List whose element type is `Object`.

`List<?>` means:

> a List whose element type is some unknown type.

---

## 🔴 Level 3 — Advanced

With:

```java
List<?> list;
```

you cannot generally add an arbitrary value:

```java
list.add("Java");       // ❌
list.add(100);          // ❌
```

Why?

Because the actual list could be:

```java
List<Integer>
```

or:

```java
List<String>
```

The compiler cannot guarantee that the value is appropriate.

But:

```java
list.add(null);         // ✅
```

is safe because `null` can be assigned to reference types.

You can safely retrieve elements as:

```java
Object value = list.get(0);
```

because every reference-type object is an `Object`.

---

# 5. Upper Bound — `? extends`

## 🟢 Level 1 — Basic

An upper-bounded wildcard looks like:

```java
? extends Number
```

It means:

> Some unknown type that is `Number` or a subclass of `Number`.

For example:

```java
List<? extends Number>
```

can refer to:

```java
List<Integer>
List<Double>
List<Float>
List<Number>
```

---

## 🟡 Level 2 — Intermediate

Consider:

```text
Object
   ↑
Number
   ↑
 ┌─┴──────┐
Integer  Double
```

Therefore:

```java
List<? extends Number>
```

can represent lists whose element type is:

```text
Number
Integer
Double
Float
...
```

You can safely read an element as `Number`:

```java
Number n = list.get(0);
```

because regardless of the exact subtype, it is guaranteed to be a `Number`.

---

### But why can't we add?

This is invalid:

```java
list.add(100);       // ❌
```

The actual list could be:

```java
List<Double>
```

and an Integer cannot be inserted into it.

So:

```text
? extends Number
       ↓
      READ
       ↓
   Number
```

---

## 🔴 Level 3 — Advanced

This is the **Producer Extends** part of PECS.

```text
? extends T
      ↓
  Producer
      ↓
   mainly READ
```

For example:

```java
static double sum(List<? extends Number> list)
{
    double total = 0;

    for (Number n : list)
    {
        total += n.doubleValue();
    }

    return total;
}
```

Now:

```java
List<Integer> a =
    Arrays.asList(10, 20, 30);

List<Double> b =
    Arrays.asList(1.5, 2.5, 3.5);

sum(a);     // ✅
sum(b);     // ✅
```

The method doesn't need to know the exact subtype.

It only needs the guarantee:

> Every element is a `Number`.

---

# 6. Lower Bound — `? super`

## 🟢 Level 1 — Basic

A lower-bounded wildcard looks like:

```java
? super Integer
```

It means:

> Some unknown type that is `Integer` or a superclass of `Integer`.

Possible types include:

```java
Integer
Number
Object
```

So:

```java
List<? super Integer>
```

can represent:

```java
List<Integer>
List<Number>
List<Object>
```

---

## 🟡 Level 2 — Intermediate

Suppose:

```java
List<? super Integer> list;
```

We can safely add an Integer:

```java
list.add(100);
list.add(200);
```

Why?

Because whichever list it actually is:

```text
List<Integer>
List<Number>
List<Object>
```

can store an `Integer`.

But:

```java
list.add(10.5);       // ❌
```

isn't safe because the actual list could be:

```java
List<Integer>
```

---

### What can we read?

We can safely retrieve an element as:

```java
Object x = list.get(0);
```

But not safely as:

```java
Integer x = list.get(0);    // ❌
```

because the actual list could be `List<Object>`.

---

## 🔴 Level 3 — Advanced

This is the **Consumer Super** part of PECS.

```text
? super T
     ↓
 Consumer
     ↓
 mainly WRITE
```

Example:

```java
static void addNumbers(List<? super Integer> list)
{
    list.add(10);
    list.add(20);
    list.add(30);
}
```

All of these are valid arguments:

```java
List<Integer> a = new ArrayList<>();

List<Number> b = new ArrayList<>();

List<Object> c = new ArrayList<>();

addNumbers(a);
addNumbers(b);
addNumbers(c);
```

Why?

Because all three lists can safely consume an `Integer`.

---

# 7. `extends` vs `super`

This is the most important comparison.

| Feature        | `? extends T`            | `? super T`             |
| -------------- | ------------------------ | ----------------------- |
| Meaning        | Unknown subtype of T     | Unknown supertype of T  |
| Bound          | Upper                    | Lower                   |
| Main use       | Read                     | Write                   |
| PECS role      | Producer                 | Consumer                |
| Safe retrieval | As `T`                   | As `Object`             |
| Add `T`        | ❌                        | ✅                       |
| Example        | `List<? extends Number>` | `List<? super Integer>` |

---

# 8. The PECS Rule

Memorize:

# **PECS = Producer Extends, Consumer Super**

If a structure **produces values** for your code:

```java
? extends T
```

If a structure **consumes values** from your code:

```java
? super T
```

---

# 9. Complete Concept Map

```text
                    GENERICS
                       │
       ┌───────────────┼────────────────┐
       │               │                │
 Generic Classes  Generic Methods   Type Safety
       │               │                │
       │               │                ↓
       │               │          Compile-time
       │               │            checking
       │               │
       └───────────────┴────────────────┐
                                        ↓
                                    Wildcards
                                        │
                         ┌──────────────┼──────────────┐
                         │              │              │
                         ?          ? extends T     ? super T
                         │              │              │
                      Unknown        Upper bound     Lower bound
                         │              │              │
                         │           Producer        Consumer
                         │              │              │
                         │             READ           WRITE
                         │
                    unknown type
```

---

# 10. Final 3-Level Revision

## 🟢 Level 1 — Remember

```java
class Box<T>
```

→ Generic class.

```java
<T> void show(T x)
```

→ Generic method.

```java
List<String>
```

→ Type safety.

```java
List<?>
```

→ Unknown type.

```java
List<? extends Number>
```

→ Upper bound.

```java
List<? super Integer>
```

→ Lower bound.

---

## 🟡 Level 2 — Understand

```text
T
↓
A type parameter

?
↓
An unknown type

? extends T
↓
T or a subtype
↓
Read as T

? super T
↓
T or a supertype
↓
Write T
```

---

## 🔴 Level 3 — Master

The mental model to keep permanently:

```text
                 WILDCARDS
                    │
          ┌─────────┴─────────┐
          │                   │
     ? extends T          ? super T
          │                   │
       Producer            Consumer
          │                   │
        READ                WRITE
          │                   │
      Treat as T          Add T safely
          │                   │
          └───────── PECS ─────┘
```

### One final sentence:

> **Generics allow Java to work with types safely and reusablely; wildcards describe unknown type arguments, `extends` gives an upper bound for producers, and `super` gives a lower bound for consumers.**
