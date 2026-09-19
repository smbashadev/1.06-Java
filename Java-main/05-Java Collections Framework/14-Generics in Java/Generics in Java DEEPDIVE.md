# 14. Generics in Java — DEEPDIVE

Generics are one of the most important parts of modern Java because they allow us to write **reusable code while preserving compile-time type safety**.

We will study each concept independently:

1. Generic Classes
2. Generic Methods
3. Type Safety
4. Wildcards
5. Upper Bound
6. Lower Bound

---

# 1. Generic Classes

## 1.1 What is a Generic Class?

A **generic class** is a class that is designed to work with different data types using a **type parameter**.

Example:

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
T
↓
Type parameter
```

`T` is not an actual data type at the point where the class is declared.

It is a placeholder.

---

## 1.2 Why do we need Generic Classes?

Suppose we want a class that stores one value.

Without generics:

```java
class Box
{
    Object value;

    void set(Object value)
    {
        this.value = value;
    }

    Object get()
    {
        return value;
    }
}
```

Now:

```java
Box b = new Box();

b.set("Java");

String s = (String)b.get();
```

We need a cast.

Worse, we could accidentally do:

```java
b.set(100);
```

and later:

```java
String s = (String)b.get();
```

which can cause:

```text
ClassCastException
```

Generics solve this problem.

---

## 1.3 Generic Class Example

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

Use it with `Integer`:

```java
Box<Integer> b = new Box<Integer>();

b.set(100);

Integer x = b.get();

System.out.println(x);
```

Use the same class with `String`:

```java
Box<String> b = new Box<String>();

b.set("Java");

String x = b.get();

System.out.println(x);
```

The same class works with different types.

```text
Box<Integer>
Box<String>
Box<Double>
Box<Float>
```

---

# 1.4 What does `Box<Integer>` mean?

This:

```java
Box<Integer>
```

means:

> Create/use a `Box` whose type parameter `T` is `Integer`.

Therefore:

```java
Box<Integer> b;
```

effectively means:

```text
T = Integer
```

So:

```java
void set(T value)
```

behaves conceptually like:

```java
void set(Integer value)
```

and:

```java
T get()
```

behaves conceptually like:

```java
Integer get()
```

---

# 1.5 Can we use primitive types?

No.

You cannot write:

```java
Box<int>
```

Generic type arguments must be reference types.

Use:

```java
Box<Integer>
```

instead.

Similarly:

```text
int     → Integer
double  → Double
char    → Character
boolean → Boolean
long    → Long
float   → Float
short   → Short
byte    → Byte
```

This is possible because of **autoboxing**.

---

# 1.6 Multiple Type Parameters

A generic class can have multiple type parameters.

Example:

```java
class Pair<K, V>
{
    K key;
    V value;

    void set(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}
```

Use:

```java
Pair<Integer, String> p = new Pair<Integer, String>();

p.set(101, "Java");
```

Here:

```text
K → Integer
V → String
```

Common conventions:

```text
T → Type
E → Element
K → Key
V → Value
N → Number
```

These are conventions, not mandatory keywords.

You could technically write:

```java
class Box<ABC>
```

but conventional names make code easier to understand.

---

# 1.7 Generic Class vs Normal Class

| Normal class                       | Generic class                     |
| ---------------------------------- | --------------------------------- |
| Works with fixed types or `Object` | Works with type parameters        |
| May require casts                  | Usually avoids explicit casts     |
| Less compile-time type information | Strong compile-time type checking |
| Less reusable in a type-safe way   | Highly reusable                   |

---

# 2. Generic Methods

A generic class and generic method are **not the same thing**.

---

## 2.1 What is a Generic Method?

A **generic method** is a method that declares its own type parameter.

Example:

```java
static <T> void display(T value)
{
    System.out.println(value);
}
```

The important part is:

```java
<T>
```

It appears **before the return type**.

---

## 2.2 Complete Example

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

The compiler determines the appropriate type argument from the method invocation.

---

# 2.3 Generic Method with Return Type

A generic method can also return a value of type `T`.

```java
static <T> T getValue(T value)
{
    return value;
}
```

Usage:

```java
Integer x = getValue(100);

String s = getValue("Java");
```

The same method can work with both.

---

# 2.4 Generic Method with Multiple Type Parameters

```java
static <T, U> void display(T a, U b)
{
    System.out.println(a);
    System.out.println(b);
}
```

Usage:

```java
display(100, "Java");
```

Here:

```text
T → Integer
U → String
```

---

# 2.5 Generic Method Inside a Non-Generic Class

This is perfectly valid.

```java
class Test
{
    static <T> void display(T value)
    {
        System.out.println(value);
    }
}
```

The class itself is not generic.

Only the method is generic.

Therefore:

```text
Non-generic class
       ↓
Generic method
```

is allowed.

---

# 2.6 Generic Method Inside a Generic Class

Also possible.

```java
class Box<T>
{
    T value;

    <U> void display(U x)
    {
        System.out.println(x);
    }
}
```

Here:

```text
T → class type parameter
U → method type parameter
```

They are independent type parameters.

---

# 2.7 Generic Class vs Generic Method

```java
class Box<T>
```

means:

```text
T belongs to the class
```

while:

```java
<T> void display(T x)
```

means:

```text
T belongs to the method
```

This distinction is extremely important.

---

# 3. Type Safety

## 3.1 What is Type Safety?

**Type safety** means that Java prevents incompatible types from being used where another type is expected.

Generics provide this checking primarily at **compile time**.

Example:

```java
List<String> list = new ArrayList<String>();
```

Now:

```java
list.add("Java");     // ✅
list.add("Python");   // ✅
```

but:

```java
list.add(100);        // ❌
```

The compiler rejects it.

Why?

Because:

```text
List<String>
     ↓
Only String elements are permitted
```

---

# 3.2 Type Safety Without Generics

Consider:

```java
List list = new ArrayList();

list.add("Java");
list.add(100);
```

This is possible with a raw collection.

Later:

```java
String s = (String) list.get(1);
```

The actual object is an `Integer`, not a `String`.

Therefore a runtime:

```text
ClassCastException
```

can occur.

---

# 3.3 Type Safety With Generics

Now:

```java
List<String> list = new ArrayList<String>();

list.add("Java");
list.add(100);
```

The second statement is rejected at compile time.

So the error occurs **earlier**.

```text
Without generics
     ↓
Wrong type may enter
     ↓
Runtime problem

With generics
     ↓
Compiler detects wrong type
     ↓
Compile-time error
```

---

# 3.4 Do Generics Completely Eliminate Runtime Errors?

No.

Generics significantly improve compile-time type safety, but they don't guarantee that a program can never fail at runtime.

For example:

```java
List<String> list = null;

list.add("Java");
```

can still produce:

```text
NullPointerException
```

Generics specifically address **type-related errors**, not every possible runtime error.

---

# 3.5 Type Safety and Casting

Without generics:

```java
Object obj = "Java";

String s = (String)obj;
```

A cast is needed.

With generics:

```java
Box<String> b = new Box<String>();

b.set("Java");

String s = b.get();
```

No explicit cast is needed.

That is one of the major practical benefits of generics.

---

# 4. Wildcards

Now we move to one of the most confusing parts of generics.

## 4.1 What is a Wildcard?

A wildcard is:

```java
?
```

It represents an **unknown type**.

Example:

```java
List<?> list;
```

Read this as:

> A List of some unknown type.

The actual list could be:

```text
List<String>
List<Integer>
List<Double>
List<Object>
```

etc.

---

# 4.2 Why can't we simply use `List<Object>`?

This is a very important doubt.

Suppose:

```java
List<String> strings = new ArrayList<String>();
```

Can we do:

```java
List<Object> objects = strings;
```

No.

Why?

Because generic types are generally **invariant**.

If this were allowed:

```java
List<Object> objects = strings;
objects.add(100);
```

then `100` would enter a `List<String>`.

That would destroy type safety.

Instead, use:

```java
List<?> list = strings;
```

This says:

> I don't care what the exact element type is.

---

# 4.3 What can we do with `List<?>`?

We can safely read elements as `Object`.

```java
static void display(List<?> list)
{
    for(Object x : list)
    {
        System.out.println(x);
    }
}
```

It can accept:

```java
List<String>
List<Integer>
List<Double>
```

---

# 4.4 Can we add to `List<?>`?

Generally, no arbitrary non-null value can be added.

```java
List<?> list = new ArrayList<String>();

list.add("Java");   // ❌
```

Why?

Because the compiler doesn't know whether the actual list is:

```text
List<String>
List<Integer>
List<Double>
```

If it allowed:

```java
list.add("Java");
```

the actual list might turn out to be:

```text
List<Integer>
```

which would be unsafe.

The exception is `null`:

```java
list.add(null);     // ✅
```

because `null` can be assigned to reference types.

---

# 5. Upper Bound

## 5.1 What is an Upper Bound?

An upper-bounded wildcard uses:

```java
? extends Type
```

Example:

```java
List<? extends Number>
```

This means:

> A List whose element type is `Number` or some subclass of `Number`.

For example:

```text
List<Integer>     ✅
List<Double>      ✅
List<Float>       ✅
List<Number>      ✅
```

---

# 5.2 Understanding the Hierarchy

Consider:

```text
        Object
          ↑
        Number
       ↗      ↖
 Integer     Double
```

Then:

```java
List<? extends Number>
```

can refer to:

```text
List<Number>
List<Integer>
List<Double>
```

because each element type is `Number` or a subtype of `Number`.

---

# 5.3 Why is it called "Upper" Bound?

Because `Number` acts as the upper limit.

```text
? extends Number
       ↑
       |
Upper boundary
```

The unknown type cannot be an unrelated type such as:

```text
String
```

because `String` is not a subtype of `Number`.

---

# 5.4 Reading from Upper-Bounded List

This is safe:

```java
static void display(List<? extends Number> list)
{
    for(Number n : list)
    {
        System.out.println(n);
    }
}
```

Why can we use `Number`?

Because whatever the actual type is, it is guaranteed to be `Number` or a subclass.

For example:

```text
Integer → Number
Double  → Number
Float   → Number
```

Therefore:

```text
Unknown subtype
      ↓
Number reference
      ↓
Safe
```

---

# 5.5 Why Can't We Add a Number?

Suppose:

```java
List<? extends Number> list;
```

You might think:

```java
list.add(100);
```

should work because `100` is an `Integer`, and `Integer extends Number`.

But the actual list could be:

```text
List<Double>
```

Then an `Integer` cannot be inserted.

Therefore:

```java
list.add(100);       // ❌
list.add(10.5);      // ❌
```

are not generally allowed.

Again:

```java
list.add(null);      // ✅
```

is allowed.

---

# 5.6 The Producer Concept

An upper-bounded collection is commonly treated as a **producer**.

It produces values for you to read.

```text
? extends Number
       ↓
Producer
       ↓
READ
```

This leads to the famous rule:

> **PECS — Producer Extends, Consumer Super**

---

# 6. Lower Bound

## 6.1 What is a Lower Bound?

A lower-bounded wildcard uses:

```java
? super Type
```

Example:

```java
List<? super Integer>
```

This means:

> A List whose element type is `Integer` or a superclass of `Integer`.

Possible types include:

```text
List<Integer>
List<Number>
List<Object>
```

---

# 6.2 Understanding the Hierarchy

```text
Object
   ↑
Number
   ↑
Integer
```

For:

```java
List<? super Integer>
```

the unknown type can be:

```text
Integer
Number
Object
```

but not:

```text
Double
String
```

because those are not supertypes of `Integer`.

---

# 6.3 Why is it called "Lower" Bound?

`Integer` establishes the lower boundary.

```text
? super Integer
       ↑
       |
Lower boundary
```

The actual type must be `Integer` or higher in the inheritance hierarchy.

---

# 6.4 Why Can We Add Integer?

Consider:

```java
List<? super Integer> list;
```

The actual list could be:

```text
List<Integer>
List<Number>
List<Object>
```

All three can safely hold an `Integer`.

Therefore:

```java
list.add(100);       // ✅
```

is safe.

---

# 6.5 Can We Add Number?

No, not generally.

```java
list.add(10.5);      // ❌
```

Why?

The actual list might be:

```text
List<Integer>
```

and `Double` cannot be stored in `List<Integer>`.

So the only guaranteed safe non-null type to add is `Integer` (or a subtype of it, where applicable).

---

# 6.6 What Can We Read?

This is the interesting part.

```java
List<? super Integer> list;
```

When retrieving:

```java
Object x = list.get(0);
```

is safe.

But:

```java
Integer x = list.get(0);    // ❌
```

is not generally safe.

Why?

Because the actual list might be:

```text
List<Object>
```

and its element could be some other `Object`.

Therefore:

```text
? super Integer
      ↓
Safe to add Integer
      ↓
Safe to read only as Object
```

---

# 6.7 Consumer Concept

A lower-bounded collection is commonly treated as a **consumer**.

It consumes values that you provide.

```text
? super Integer
       ↓
Consumer
       ↓
WRITE
```

Hence:

> **Producer Extends, Consumer Super.**

---

# 7. `?` vs `? extends` vs `? super`

These three are often confused.

| Syntax                   | Meaning                      |
| ------------------------ | ---------------------------- |
| `List<?>`                | List of unknown type         |
| `List<? extends Number>` | List of Number or subtype    |
| `List<? super Integer>`  | List of Integer or supertype |

Conceptually:

```text
? 
↓
I don't know the type.

? extends Number
↓
I don't know the type,
but I know it is Number or a subtype.

? super Integer
↓
I don't know the type,
but I know it is Integer or a supertype.
```

---

# 8. The PECS Rule in Detail

PECS:

```text
P → Producer
E → Extends

C → Consumer
S → Super
```

Therefore:

```text
Producer → ? extends T
Consumer → ? super T
```

### Example: Producer

If you want to read numbers:

```java
static void printNumbers(List<? extends Number> list)
{
    for(Number n : list)
    {
        System.out.println(n);
    }
}
```

The list **produces** `Number` values.

---

### Example: Consumer

If you want to insert integers:

```java
static void addNumbers(List<? super Integer> list)
{
    list.add(10);
    list.add(20);
}
```

The list **consumes** `Integer` values.

---

# 9. Generic Class + Wildcard Together

Consider:

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

Now:

```java
Box<Integer> b1 = new Box<Integer>();
```

The type is known exactly.

But:

```java
Box<?> b2 = b1;
```

means:

```text
Box of some unknown type
```

You can safely read:

```java
Object x = b2.get();
```

But:

```java
b2.set(100);       // ❌
```

because the exact type is unknown.

---

# 10. A Crucial Concept: Invariance

This is one of the foundations needed to understand wildcards.

Suppose:

```java
Integer
   ↓
Number
```

It is true that:

```text
Integer is-a Number
```

But it does **not** mean:

```text
List<Integer> is-a List<Number>
```

So:

```java
List<Integer> integers = new ArrayList<Integer>();

List<Number> numbers = integers;     // ❌
```

Why?

Because then:

```java
numbers.add(10.5);
```

would be allowed, but the original list is supposed to contain only `Integer`.

---

# 11. Then How Do We Accept Both?

Use a wildcard:

```java
List<? extends Number>
```

Now:

```java
List<Integer> integers = new ArrayList<Integer>();

List<? extends Number> numbers = integers;
```

is valid.

Similarly:

```java
List<Double> doubles = new ArrayList<Double>();

List<? extends Number> numbers = doubles;
```

is also valid.

The wildcard says:

> I don't know the exact subtype, but I know it is related to `Number`.

---

# 12. Generic Type Parameter vs Wildcard

These are also different.

### Type parameter

```java
<T>
```

introduces a type variable.

Example:

```java
static <T> T identity(T value)
{
    return value;
}
```

Here `T` represents a specific type for that invocation.

### Wildcard

```java
?
```

represents an unknown type.

Example:

```java
static void display(List<?> list)
```

Here we don't need to name the exact type.

Think:

```text
<T>
 ↓
I want to name/use a type parameter.

?
 ↓
I don't need to know the exact type.
```

---

# 13. Generic Class vs Generic Method vs Wildcard

| Concept            | Example                  | Main idea                          |
| ------------------ | ------------------------ | ---------------------------------- |
| Generic class      | `class Box<T>`           | Class works with a type parameter  |
| Generic method     | `<T> void show(T x)`     | Method works with a type parameter |
| Unbounded wildcard | `List<?>`                | Unknown type                       |
| Upper bound        | `List<? extends Number>` | Unknown subtype/Number             |
| Lower bound        | `List<? super Integer>`  | Unknown supertype/Integer          |

---

# 14. Important Restrictions of Generics

## 14.1 Primitive type arguments

Not allowed:

```java
List<int>       // ❌
```

Use:

```java
List<Integer>   // ✅
```

---

## 14.2 Generic arrays

You cannot generally create an array of a concrete parameterized type like:

```java
List<String>[] arr = new List<String>[10];    // ❌
```

This is due to interactions between arrays and Java generics.

This is a more advanced topic, but remember the rule.

---

# 15. Type Erasure — Important Deep-Dive Concept

Java generics are implemented using **type erasure**.

For example:

```java
class Box<T>
{
    T value;
}
```

At runtime, the JVM does not retain `T` as a normal runtime generic type in the same way the compiler uses it.

The compiler uses generic information to perform type checking and inserts appropriate casts where necessary.

Conceptually:

```text
Source code
   ↓
Generics checked by compiler
   ↓
Type erasure
   ↓
Bytecode
   ↓
JVM
```

This is one reason generic type parameters have restrictions involving runtime type operations.

For example, you cannot normally do:

```java
if(value instanceof T)
```

because `T` is not available as a normal runtime class token.

---

# 16. Complete Conceptual Picture

```text
                    GENERICS
                       |
        ┌──────────────┼──────────────┐
        ↓              ↓              ↓
   Generic Class   Generic Method   Type Safety
        |              |              |
      Box<T>         <T> method     Compile time
        |
        └──────────────┐
                       ↓
                    Wildcards
                       |
              ┌────────┼────────┐
              ↓        ↓        ↓
              ?     ? extends  ? super
              |         |        |
           Unknown    Upper     Lower
             type     bound     bound
                        |         |
                     Producer  Consumer
                        |         |
                       READ      WRITE
```

---

# 17. 🔥 Most Important Doubts — Direct Answers

### Q1. What is `T`?

A **type parameter**.

---

### Q2. What is `?`?

A wildcard representing an **unknown type**.

---

### Q3. Is `?` the same as `Object`?

**No.**

`Object` is an actual type.

`?` represents an unknown type argument.

---

### Q4. Is `List<String>` a subtype of `List<Object>`?

**No.**

Generic types are generally invariant.

---

### Q5. How can a method accept `List<Integer>` and `List<Double>`?

Use:

```java
List<? extends Number>
```

when you need to read them as `Number`.

---

### Q6. Why can't I add to `List<? extends Number>`?

Because the actual list could be `List<Integer>`, `List<Double>`, etc., and the compiler cannot guarantee that your value matches the unknown exact type.

---

### Q7. Why can I add an `Integer` to `List<? super Integer>`?

Because the actual list must be one of:

```text
List<Integer>
List<Number>
List<Object>
```

and all can store an `Integer`.

---

### Q8. Why can I only safely read `Object` from `List<? super Integer>`?

Because the actual list might be `List<Object>`, so the exact element type is unknown.

---

### Q9. What is PECS?

```text
Producer Extends
Consumer Super
```

---

### Q10. Can a generic method exist inside a non-generic class?

**Yes.**

```java
class Test
{
    static <T> void show(T x)
    {
    }
}
```

---

### Q11. Can a generic class have a generic method?

**Yes.**

```java
class Box<T>
{
    <U> void show(U x)
    {
    }
}
```

---

### Q12. Can generics use `int`?

No:

```java
List<int>       // ❌
```

Use:

```java
List<Integer>   // ✅
```

---

# 18. Final Deep-Dive Memory Map

```text
GENERIC CLASS
class Box<T>
    ↓
T belongs to class


GENERIC METHOD
<T> void show(T x)
    ↓
T belongs to method


TYPE SAFETY
List<String>
    ↓
compiler knows expected type
    ↓
wrong type rejected at compile time


WILDCARD
?
    ↓
unknown type


UPPER BOUND
? extends Number
    ↓
Number or subclass
    ↓
Producer
    ↓
READ


LOWER BOUND
? super Integer
    ↓
Integer or superclass
    ↓
Consumer
    ↓
WRITE
```

## ⭐ The ultimate rule

> **`<T>` means "I am defining/naming a type parameter"; `?` means "the exact type is unknown"; `? extends T` means "some unknown subtype of T, so think producer/read"; and `? super T` means "some unknown supertype of T, so think consumer/write."**
