# 14. Generics in Java — ONEPAGE

Generics allow us to write classes, interfaces, and methods that work with **different data types while maintaining compile-time type safety**.

The central idea is:

```text
Without Generics
Object → cast → possible ClassCastException

With Generics
specific type → compiler checks → safer code
```

---

# 1. Generic Classes

## Definition

A **generic class** is a class that uses a type parameter.

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

```text
T = type parameter
```

Use it:

```java
Box<Integer> b1 = new Box<Integer>();
b1.set(100);

Integer x = b1.get();
```

Another type:

```java
Box<String> b2 = new Box<String>();
b2.set("Java");

String s = b2.get();
```

The same class works with different types:

```text
Box<Integer>
Box<String>
Box<Double>
```

### Important

`T` is not a specific type such as `Integer`.

It is a **placeholder for a type**.

Common naming conventions:

```text
T → Type
E → Element
K → Key
V → Value
N → Number
```

---

# 2. Generic Methods

## Definition

A **generic method** is a method that declares its own type parameter.

Syntax:

```java
static <T> void display(T value)
{
    System.out.println(value);
}
```

Example:

```java
class Test
{
    static <T> void display(T value)
    {
        System.out.println(value);
    }

    public static void main(String[] args)
    {
        display(100);
        display("Java");
        display(10.5);
    }
}
```

Output:

```text
100
Java
10.5
```

### Important distinction

Generic class:

```java
class Box<T>
```

The type parameter belongs to the **class**.

Generic method:

```java
<T> void display(T value)
```

The type parameter belongs to the **method**.

A non-generic class can contain a generic method.

---

# 3. Type Safety

## Definition

**Type safety** means the compiler prevents us from putting an inappropriate type into a generic structure.

Example:

```java
List<String> list = new ArrayList<String>();

list.add("Java");
list.add("Python");
```

This is valid.

But:

```java
list.add(100);
```

causes a **compile-time error**.

Why?

Because:

```text
List<String>
     ↓
Only String objects are allowed
```

Without generics:

```java
List list = new ArrayList();

list.add("Java");
list.add(100);
```

Different types can enter the same collection, potentially requiring casts later.

With generics:

```text
List<String>
     ↓
compiler knows the expected type
     ↓
wrong type rejected
```

### Major benefit

```text
Generics
   ↓
Compile-time type checking
   ↓
Fewer explicit casts
   ↓
Better type safety
```

---

# 4. Wildcards

A wildcard is represented by:

```java
?
```

It means:

> **Some unknown type.**

Example:

```java
List<?> list;
```

This means:

```text
List of some type
```

The exact type is unknown.

It could be:

```text
List<String>
List<Integer>
List<Double>
```

etc.

---

## Why use `?`?

Suppose:

```java
static void display(List<?> list)
{
    for(Object x : list)
    {
        System.out.println(x);
    }
}
```

This method can accept:

```java
List<String>
List<Integer>
List<Double>
```

because the method doesn't need to know the exact element type.

### Important wildcard idea

```text
? = unknown type
```

It does **not** mean:

```text
any type can be freely inserted
```

For example:

```java
List<?> list = new ArrayList<String>();

list.add("Java");    // ❌
```

Generally, you cannot add an arbitrary non-`null` value to `List<?>`, because the actual type is unknown.

---

# 5. Upper Bound

Upper bound uses:

```java
? extends Type
```

Example:

```java
List<? extends Number>
```

Meaning:

```text
List of Number
       OR
List of Integer
       OR
List of Double
       OR
another subtype of Number
```

Conceptually:

```text
Number
 ├── Integer
 ├── Double
 ├── Float
 └── ...
```

So:

```java
List<Integer>
```

can be assigned to:

```java
List<? extends Number>
```

and:

```java
List<Double>
```

can also be assigned to it.

---

## Key rule: `extends` → mainly READ

Example:

```java
static double sum(List<? extends Number> list)
{
    double total = 0;

    for(Number n : list)
    {
        total += n.doubleValue();
    }

    return total;
}
```

You can safely read elements as `Number`.

But you generally cannot add an arbitrary `Number`:

```java
list.add(10);       // ❌
list.add(10.5);     // ❌
```

because the actual list might be:

```text
List<Integer>
```

and a `Double` would be invalid.

### Memory rule

```text
? extends T
     ↓
Producer
     ↓
READ
```

---

# 6. Lower Bound

Lower bound uses:

```java
? super Type
```

Example:

```java
List<? super Integer>
```

This can refer to:

```text
List<Integer>
List<Number>
List<Object>
```

because:

```text
Object
  ↑
Number
  ↑
Integer
```

---

## Key rule: `super` → mainly WRITE

Example:

```java
static void addNumbers(List<? super Integer> list)
{
    list.add(10);
    list.add(20);
}
```

Adding `Integer` values is safe.

Why?

Because the actual list must be capable of holding an `Integer`:

```text
List<Integer> → can hold Integer
List<Number>  → can hold Integer
List<Object>  → can hold Integer
```

But when reading:

```java
Object x = list.get(0);
```

the safe type is generally `Object`, because the exact type is unknown.

### Memory rule

```text
? super T
     ↓
Consumer
     ↓
WRITE
```

---

# 🔥 `extends` vs `super`

| Feature            | `? extends T`                     | `? super T`                         |
| ------------------ | --------------------------------- | ----------------------------------- |
| Meaning            | Unknown type that is T or subtype | Unknown type that is T or supertype |
| Example            | `List<? extends Number>`          | `List<? super Integer>`             |
| Main use           | Read                              | Write                               |
| Safe value to read | `T`/upper bound                   | `Object`                            |
| Add a `T`?         | Generally ❌                       | ✅                                   |
| Memory             | Producer                          | Consumer                            |

### Famous memory rule

> **PECS = Producer Extends, Consumer Super**

```text
Producer → extends
Consumer → super
```

---

# 🧠 Complete Generics Map

```text
Generics
│
├── Generic Class
│      ↓
│   class Box<T>
│
├── Generic Method
│      ↓
│   <T> void method(T x)
│
├── Type Safety
│      ↓
│   Compile-time checking
│
└── Wildcards
       │
       ├── ?
       │    ↓
       │  Unknown type
       │
       ├── ? extends T
       │    ↓
       │  Upper bound
       │    ↓
       │  Mainly READ
       │
       └── ? super T
            ↓
          Lower bound
            ↓
          Mainly WRITE
```

# ⭐ Final One-Page Memory

```text
<T>
 ↓
Type parameter

?
 ↓
Unknown type

? extends T
 ↓
Upper bound
 ↓
Producer
 ↓
READ

? super T
 ↓
Lower bound
 ↓
Consumer
 ↓
WRITE
```

And the most important distinction:

```text
Generic Class
class Box<T>
→ type parameter belongs to class

Generic Method
<T> void show(T x)
→ type parameter belongs to method

Type Safety
→ compiler catches incompatible types

Wildcard
?
→ unknown type

Upper Bound
? extends T
→ T or subclass

Lower Bound
? super T
→ T or superclass
```

**One sentence to remember everything:**

> **Generics give compile-time type safety; `?` represents an unknown type; `extends` places an upper bound and is mainly used for reading, while `super` places a lower bound and is mainly used for writing.**
