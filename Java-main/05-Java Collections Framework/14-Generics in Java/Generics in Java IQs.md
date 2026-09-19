# 14. Generics in Java — DOUBTKILLER

This section is designed to eliminate the **confusing points, traps, misconceptions, interview questions, and “why?” questions** around Generics.

---

# 1. Generic Classes

## ❓ Doubt 1: What exactly is `T`?

```java
class Box<T>
{
    T value;
}
```

`T` is **not a variable**.

`T` is a **type parameter**.

It represents a type that will be supplied later.

For example:

```java
Box<String>
```

means:

```text
T → String
```

and:

```java
Box<Integer>
```

means:

```text
T → Integer
```

---

## ❓ Doubt 2: Is `T` a keyword?

**No.**

You could write:

```java
class Box<X>
```

or:

```java
class Box<MyType>
```

They are legal.

But Java programmers conventionally use:

```text
T → Type
E → Element
K → Key
V → Value
N → Number
```

---

## ❓ Doubt 3: Why not just use `Object`?

You can use `Object`, but you lose compile-time type safety.

### Using Object

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

Usage:

```java
Box b = new Box();

b.set("Java");

String s = (String)b.get();
```

You need casting.

With Generics:

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

Then:

```java
Box<String> b = new Box<>();

b.set("Java");

String s = b.get();
```

No explicit cast.

### Key difference

```text
Object
   ↓
Can store anything
   ↓
May require casting
   ↓
Wrong cast can fail at runtime


Generics
   ↓
Type specified
   ↓
Compiler checks the type
   ↓
Many errors caught at compile time
```

---

## ❓ Doubt 4: Can I use primitive types?

No.

```java
Box<int> b;       // ❌
```

Use:

```java
Box<Integer> b;   // ✅
```

Likewise:

```text
int      → Integer
double   → Double
char     → Character
boolean  → Boolean
long     → Long
float    → Float
```

Java's autoboxing/unboxing makes wrapper types convenient.

---

## ❓ Doubt 5: Can a generic class have multiple type parameters?

Yes.

```java
class Pair<K, V>
{
    K key;
    V value;
}
```

Usage:

```java
Pair<Integer, String> p = new Pair<>();
```

Therefore:

```text
K → Integer
V → String
```

---

## ❓ Doubt 6: Is `Box<String>` a different class from `Box<Integer>`?

At the Java source/type-system level, they are different **parameterizations** of the generic class.

But don't think of them as two separately declared classes:

```text
BoxString.class
BoxInteger.class
```

Java generics are implemented primarily through **type erasure**.

The compiler uses generic information for type checking, while the JVM does not maintain separate runtime class definitions for every generic argument.

---

## ❓ Doubt 7: Can I write this?

```java
class Box<T>
{
    static T value;
}
```

No.

```text
❌ static members cannot use the class's type parameter T
```

Why?

`T` belongs to an **object/type parameterization**, while a static member belongs to the class itself.

Imagine:

```java
Box<String>
Box<Integer>
```

What would one shared static `T value` mean?

Should it be String or Integer?

Therefore:

```java
class Box<T>
{
    T value;              // ✅

    static T value2;     // ❌
}
```

---

# 2. Generic Methods

## ❓ Doubt 8: What makes a method generic?

This:

```java
static <T> void display(T value)
{
    System.out.println(value);
}
```

The method declares:

```java
<T>
```

before the return type.

That means the method owns its type parameter.

---

## ❓ Doubt 9: Why is `<T>` before `void`?

Because Java syntax requires the method's type parameters to be declared before the return type.

Correct:

```java
<T> void display(T value)
```

Incorrect:

```java
void <T> display(T value)    // ❌
```

Think:

```text
static
   ↓
<T>          ← declare type parameter
   ↓
void         ← return type
   ↓
display()
```

---

## ❓ Doubt 10: Does the class itself have to be generic?

No.

This is perfectly valid:

```java
class Demo
{
    static <T> void display(T value)
    {
        System.out.println(value);
    }
}
```

The class is ordinary.

Only the method is generic.

---

## ❓ Doubt 11: What is the difference between these?

```java
class Box<T>
```

and:

```java
<T> void display(T x)
```

### First

```java
class Box<T>
```

`T` belongs to the **class**.

### Second

```java
<T> void display(T x)
```

`T` belongs to the **method**.

---

## ❓ Doubt 12: Can a generic method return `T`?

Absolutely.

```java
static <T> T get(T value)
{
    return value;
}
```

Then:

```java
String s = get("Java");
```

and:

```java
Integer n = get(100);
```

The compiler infers the appropriate type.

---

## ❓ Doubt 13: Can a generic method have a bound?

Yes.

```java
static <T extends Number> void display(T value)
{
    System.out.println(value);
}
```

Allowed:

```java
display(100);
display(10.5);
```

Not allowed:

```java
display("Java");      // ❌
```

because `String` is not a subtype of `Number`.

---

# 3. Type Safety

## ❓ Doubt 14: What exactly does "type safety" mean?

It means:

> Java uses the type information to prevent incompatible objects from being used where a particular type is expected.

Example:

```java
List<String> list = new ArrayList<>();

list.add("Java");       // ✅
list.add("Python");     // ✅
list.add(100);          // ❌
```

The compiler knows:

```text
list → String only
```

---

## ❓ Doubt 15: Why is this better than a raw collection?

Raw:

```java
List list = new ArrayList();

list.add("Java");
list.add(100);
```

Now the collection can contain unrelated types.

With:

```java
List<String> list = new ArrayList<>();
```

the compiler prevents:

```java
list.add(100);
```

So the error is discovered earlier.

---

## ❓ Doubt 16: Does Generics completely eliminate runtime errors?

**No.**

Generics provide strong **compile-time** checking, but Java still has runtime type-related issues in some situations.

For example, raw types and unchecked casts can bypass compile-time checks.

```java
List raw = new ArrayList();

raw.add(100);

List<String> strings = raw;    // unchecked conversion
```

This is one reason raw types should generally be avoided.

---

# 4. Wildcards

## ❓ Doubt 17: What does `?` actually mean?

```java
List<?> list;
```

means:

> A List whose element type is some unknown type.

It does **not** mean:

```java
List<Object>
```

---

## ❓ Doubt 18: Why is `List<?>` different from `List<Object>`?

This is one of the biggest Generic doubts.

### `List<Object>`

```java
List<Object>
```

means:

> The actual type argument is exactly `Object`.

You can add:

```java
list.add("Java");
list.add(100);
```

because both are Objects.

### `List<?>`

```java
List<?>
```

means:

> The actual type argument is unknown.

It might be:

```text
List<String>
List<Integer>
List<Double>
```

Therefore:

```java
list.add("Java");       // ❌
```

is not safe.

---

# 5. Why Can't We Add to `List<?>`?

Suppose:

```java
List<?> list = new ArrayList<Integer>();
```

If Java allowed:

```java
list.add("Java");
```

we would have:

```text
List<Integer>
       ↓
String inserted
       ↓
Type safety destroyed
```

Therefore the compiler prevents it.

But:

```java
list.add(null);
```

is allowed because `null` can represent the absence of a reference value.

---

# 6. Upper Bound

## ❓ Doubt 19: What does `? extends Number` mean?

```java
List<? extends Number>
```

means:

> A List of some unknown type that is `Number` or a subclass of `Number`.

Possible actual types:

```text
List<Number>
List<Integer>
List<Double>
List<Float>
```

---

## ❓ Doubt 20: Why is it called an upper bound?

Because the unknown type cannot go above the specified boundary in the subtype relationship.

```text
Object
   ↑
Number      ← bound
   ↑
Integer
```

For:

```java
? extends Number
```

the actual type must be:

```text
Number
or
a subtype of Number
```

---

# 7. Why Can We Read from `? extends Number`?

Consider:

```java
List<? extends Number> list;
```

The exact type is unknown.

Maybe:

```text
List<Integer>
```

Maybe:

```text
List<Double>
```

But both guarantee:

```text
element IS-A Number
```

Therefore:

```java
Number n = list.get(0);
```

is safe.

---

# 8. Why Can't We Add to `? extends Number`?

This is the classic trap.

You might think:

```java
List<? extends Number> list;

list.add(10);       // Why not?
```

Because the actual list could be:

```java
List<Double>
```

Then:

```text
Integer → inserted into List<Double>
```

would be invalid.

The compiler therefore says:

```java
list.add(10);       // ❌
```

---

# 9. Upper Bound Does NOT Mean "Only Parent"

This is wrong thinking:

```text
? extends Number = only Number
```

It actually means:

```text
Number
OR
Integer
OR
Double
OR
another Number subtype
```

The exact type is unknown.

---

# 10. Lower Bound

## ❓ Doubt 21: What does `? super Integer` mean?

```java
List<? super Integer>
```

means:

> A List of some unknown type that is `Integer` or a superclass of Integer.

Possible actual types:

```text
List<Integer>
List<Number>
List<Object>
```

---

# 11. Why Is It Called Lower Bound?

Hierarchy:

```text
Object
   ↑
Number
   ↑
Integer
```

Here:

```text
Integer
```

is the lower boundary for:

```java
? super Integer
```

The actual type can be:

```text
Integer
Number
Object
```

---

# 12. Why Can We Add Integer to `? super Integer`?

Suppose:

```java
List<? super Integer> list;
```

The actual list could be:

```text
List<Integer>
List<Number>
List<Object>
```

All three can store:

```java
Integer
```

Therefore:

```java
list.add(100);       // ✅
```

is safe.

---

# 13. Why Can't We Add Double?

```java
list.add(10.5);      // ❌
```

Because the actual list could be:

```java
List<Integer>
```

and:

```text
Double ≠ Integer
```

So the compiler cannot guarantee safety.

---

# 14. Why Can We Only Read `Object` from `? super Integer`?

Suppose:

```java
List<? super Integer> list;
```

The actual list could be:

```text
List<Object>
```

If you retrieve an element, you don't know whether it is:

```text
Integer
String
Double
...
```

Therefore:

```java
Object x = list.get(0);      // ✅
```

is safe.

But:

```java
Integer x = list.get(0);     // ❌
```

is not guaranteed to be safe.

---

# 15. Upper vs Lower Bound — The Killer Comparison

|                 | Upper Bound        | Lower Bound       |
| --------------- | ------------------ | ----------------- |
| Syntax          | `? extends T`      | `? super T`       |
| Meaning         | T or subtype       | T or supertype    |
| Example         | `? extends Number` | `? super Integer` |
| Main capability | Read               | Write             |
| Safe read       | As `T`             | As `Object`       |
| Safe add of T   | ❌                  | ✅                 |
| PECS            | Producer           | Consumer          |

---

# 16. The PECS Rule

This is the rule you should remember forever:

# **P — E — C — S**

```text
Producer → Extends
Consumer → Super
```

### Producer

If you're mainly **getting values out**:

```java
? extends T
```

### Consumer

If you're mainly **putting values in**:

```java
? super T
```

---

# 17. Killer Question: Is `List<Integer>` a `List<Number>`?

No.

```java
List<Integer> list = new ArrayList<>();

List<Number> x = list;       // ❌
```

This surprises many beginners.

Even though:

```text
Integer extends Number
```

it does **not** mean:

```text
List<Integer> extends List<Number>
```

Generic types in Java are generally **invariant**.

---

# 18. Then How Can We Pass `List<Integer>` Where `List<? extends Number>` Is Expected?

Because wildcard types provide the required flexibility:

```java
List<Integer> list = new ArrayList<>();

List<? extends Number> x = list;     // ✅
```

This says:

> I don't need an exact `List<Number>`. I accept a List of some subtype of Number.

---

# 19. Killer Question: Why Is This Allowed?

```java
List<? super Integer> x = new ArrayList<Number>();
```

Because:

```text
Integer
   ↓
Number
```

and Number is a supertype of Integer.

Therefore:

```text
? super Integer
```

can represent:

```text
Integer
Number
Object
```

---

# 20. Killer Question: Can We Instantiate a Wildcard?

No.

This is invalid:

```java
new ArrayList<?>();
```

❌

A wildcard is used to describe an **unknown type argument**, not to create an object with an unknown type directly.

Correct:

```java
List<?> list = new ArrayList<String>();
```

---

# 21. Killer Question: Can We Create `new T()`?

Generally no:

```java
class Box<T>
{
    T value = new T();       // ❌
}
```

Because due to type erasure, Java cannot generally construct an arbitrary `T` this way.

A common approach is to pass a factory, constructor reference, or `Class<T>` where appropriate.

---

# 22. Killer Question: Can We Create an Array of T?

Generally:

```java
T[] arr = new T[10];         // ❌
```

is not allowed directly.

Generic arrays interact badly with type erasure.

---

# 23. Killer Question: Can a Generic Class Have a Static T?

No:

```java
class Box<T>
{
    static T value;          // ❌
}
```

Because the static field belongs to the class rather than one particular parameterization.

---

# 24. Killer Question: Can a Generic Method Exist in a Non-Generic Class?

Yes.

```java
class Demo
{
    static <T> void display(T value)
    {
        System.out.println(value);
    }
}
```

This is completely valid.

---

# 25. Killer Question: Can a Generic Class Have a Generic Method?

Yes.

```java
class Box<T>
{
    T value;

    <U> void display(U data)
    {
        System.out.println(data);
    }
}
```

Here:

```text
T → class-level type parameter
U → method-level type parameter
```

They are independent.

---

# 26. Killer Question: Can `T` Represent a Primitive?

No.

```java
Box<int>       // ❌
```

Use:

```java
Box<Integer>   // ✅
```

Generics work with reference types, not primitive type arguments.

---

# 27. Killer Question: Is `? extends Number` Read-Only?

Not literally.

A better statement is:

> You cannot safely add a specific non-null value to it because the exact subtype is unknown.

You can still perform operations that don't require changing the element type, and you can remove elements through appropriate collection operations.

For teaching purposes:

```text
? extends T → mainly READ
```

is the useful mental model.

---

# 28. Killer Question: Is `? super Integer` Write-Only?

Not literally.

You **can read**, but the only universally safe static type for a retrieved element is `Object`.

Therefore:

```text
? super T → mainly WRITE
```

is the useful mental model.

---

# 29. Killer Question: Why Is `Object` the Safe Read Type?

For:

```java
List<? super Integer>
```

the actual list could be:

```text
List<Integer>
List<Number>
List<Object>
```

The common guaranteed supertype is:

```text
Object
```

Therefore:

```java
Object x = list.get(0);
```

is safe.

---

# 30. Generic Class vs Wildcard — Another Common Confusion

These:

```java
class Box<T>
```

and:

```java
Box<?>
```

are not the same concept.

### `T`

is a **type parameter**.

It introduces a type variable.

### `?`

is a **wildcard**.

It represents an unknown type argument.

For example:

```java
class Box<T>
{
}
```

and:

```java
Box<String> a = new Box<>();
Box<?> b = a;
```

`T` and `?` have different roles.

---

# 31. Generic Type Parameter vs Wildcard

|                                       | Type Parameter | Wildcard |
| ------------------------------------- | -------------- | -------- |
| Example                               | `<T>`          | `<?>`    |
| Introduces a type variable?           | Yes            | No       |
| Can be used to relate multiple types? | Yes            | Limited  |
| Used in class declaration?            | Yes            | No       |
| Used in parameterized type?           | Can be         | Yes      |
| Example                               | `class Box<T>` | `Box<?>` |

---

# 32. One Very Important Difference

Suppose:

```java
static <T> void copy(T a, T b)
```

The two arguments participate in the **same type parameter** `T`.

Whereas:

```java
static void copy(List<?> a, List<?> b)
```

each `?` represents an unknown type; they do **not** mean both lists necessarily have the same element type.

This is a subtle but important distinction.

---

# 33. `? extends` Does Not Mean You Can Add a Subclass

Suppose:

```java
List<? extends Number> list;
```

You might think:

```java
list.add(Integer.valueOf(10));
```

should work because:

```text
Integer extends Number
```

But the actual list could be:

```text
List<Double>
```

Therefore the compiler rejects the addition.

This is one of the most important wildcard rules.

---

# 34. `? super` Does Not Mean You Can Add Every Object

Suppose:

```java
List<? super Integer> list;
```

You can add:

```java
Integer
```

but not arbitrary objects:

```java
list.add("Java");       // ❌
list.add(10.5);        // ❌
```

The guaranteed safe value is an `Integer`.

---

# 35. A Complete PECS Example

```java
import java.util.*;

class Demo
{
    static double sum(List<? extends Number> list)
    {
        double total = 0;

        for (Number n : list)
        {
            total += n.doubleValue();
        }

        return total;
    }

    static void addIntegers(List<? super Integer> list)
    {
        list.add(10);
        list.add(20);
        list.add(30);
    }
}
```

First method:

```java
List<? extends Number>
```

is a **producer**.

It produces Numbers to us.

Second method:

```java
List<? super Integer>
```

is a **consumer**.

It consumes Integers from us.

---

# 36. Final Doubt Killer Table

| Concept                         | What it really means                  |
| ------------------------------- | ------------------------------------- |
| `T`                             | Named type parameter                  |
| `<T>` in class                  | Class-level type parameter            |
| `<T>` before method return type | Method-level type parameter           |
| `?`                             | Unknown type argument                 |
| `? extends T`                   | Unknown subtype of T                  |
| `? super T`                     | Unknown supertype of T                |
| `List<T>`                       | Exactly parameterized by T            |
| `List<?>`                       | List of some unknown type             |
| `List<Object>`                  | Specifically a List of Object         |
| Generic class                   | Reusable class parameterized by type  |
| Generic method                  | Reusable method parameterized by type |
| Type safety                     | Compiler checks compatible types      |
| Upper bound                     | `extends`                             |
| Lower bound                     | `super`                               |
| Producer                        | `extends`                             |
| Consumer                        | `super`                               |

---

# 🧠 The Ultimate Generics Doubt Killer

If you remember only this diagram, remember this one:

```text
                         GENERICS
                            │
              ┌─────────────┴─────────────┐
              │                           │
          Type Parameters              Wildcards
              │                           │
        ┌─────┴─────┐              ┌──────┼──────┐
        │           │              │      │      │
   Generic Class  Generic       ?     extends   super
                  Method               │          │
                                       │          │
                                  Upper Bound  Lower Bound
                                       │          │
                                   Producer     Consumer
                                       │          │
                                     READ        WRITE
                                       │          │
                                  PECS: Producer Extends,
                                       Consumer Super
```

## 🔥 Six statements that eliminate most Generics confusion

1. **`T` is a type parameter; `?` is a wildcard.**
2. **Generic classes make one class reusable for different types.**
3. **Generic methods make one method reusable for different types.**
4. **Generics provide compile-time type safety and reduce casting.**
5. **`? extends T` → unknown subtype → safely read as `T` → Producer.**
6. **`? super T` → unknown supertype → safely add `T` → Consumer.**

### And the ultimate memory sentence:

> **`extends` asks: "What can I safely take out?" — `super` asks: "What can I safely put in?"**

That is the core of Java Generics.
