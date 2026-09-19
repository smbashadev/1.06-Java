# 14. Generics in Java — TEACHME

Let's learn **Generics from zero**, as if this is your first time seeing the topic.

The goal is not just to memorize syntax. By the end, you should understand **why Generics exist, how they work, and especially why `? extends` and `? super` behave differently**.

---

# 1. First: What Problem Do Generics Solve?

Imagine you create a box.

You want the box to store a value.

Without Generics, you might write:

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
```

To get the value:

```java
String s = (String)b.get();
```

Notice the cast:

```java
(String)
```

That's inconvenient.

And there is a bigger problem.

You could accidentally do:

```java
b.set(100);
```

Now the box contains an `Integer`.

Later:

```java
String s = (String)b.get();
```

💥 Runtime error:

```text
ClassCastException
```

---

# 2. Generics: The Solution

Instead of saying:

> "This box stores `Object`."

we can say:

> "This box stores **a particular type**."

We write:

```java
class Box<T>
```

Here:

```text
T = Type
```

`T` is a **placeholder for a data type**.

Now:

```java
Box<String>
```

means:

```text
T = String
```

And:

```java
Box<Integer>
```

means:

```text
T = Integer
```

So the same class can work with different types.

---

# 3. Generic Classes

## 3.1 What is a Generic Class?

A class that uses a **type parameter** is called a generic class.

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

Look carefully:

```java
class Box<T>
```

`T` is declared here.

Then we use `T` inside the class:

```java
T value;
```

```java
void set(T value)
```

```java
T get()
```

---

# 4. How Do We Use a Generic Class?

Suppose we want a box for `String`.

```java
Box<String> b = new Box<String>();
```

Now Java knows:

```text
T = String
```

Therefore:

```java
b.set("Java");
```

is valid.

And:

```java
String s = b.get();
```

is valid.

No casting is required.

---

## Now use the same class for Integer

```java
Box<Integer> b = new Box<Integer>();

b.set(100);

Integer x = b.get();
```

Again:

```text
T = Integer
```

The same `Box` class is being reused.

---

# 5. The Big Idea Behind Generic Classes

Think of:

```java
Box<T>
```

as a **template for a type**.

When you write:

```java
Box<String>
```

you are saying:

> "Give me a Box where T means String."

When you write:

```java
Box<Integer>
```

you are saying:

> "Give me a Box where T means Integer."

So:

```text
Box<T>
   |
   +---- Box<String>
   |
   +---- Box<Integer>
   |
   +---- Box<Double>
```

One class → many type-safe versions.

---

# 6. Why Is This Better?

Without Generics:

```java
Object value
```

With Generics:

```java
T value
```

Without Generics:

```java
String s = (String)b.get();
```

With Generics:

```java
String s = b.get();
```

Without Generics, wrong types may be discovered at runtime.

With Generics, the compiler can reject them earlier.

---

# 7. One Important Rule

You cannot use primitive types directly as generic arguments.

This is invalid:

```java
Box<int>        // ❌
```

Use the wrapper class:

```java
Box<Integer>    // ✅
```

Similarly:

```text
int      → Integer
double   → Double
char     → Character
boolean  → Boolean
long     → Long
float    → Float
```

Java's **autoboxing** makes using wrapper types convenient.

---

# 8. Generic Classes with Multiple Types

A class can have more than one type parameter.

Example:

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

    void display()
    {
        System.out.println(key);
        System.out.println(value);
    }
}
```

Use:

```java
Pair<Integer, String> p =
    new Pair<Integer, String>(101, "Java");
```

Here:

```text
K → Integer
V → String
```

Common naming conventions:

```text
T → Type
E → Element
K → Key
V → Value
N → Number
```

These names are conventions, not special Java keywords.

---

# 9. Generic Methods

Now let's separate two ideas.

A **generic class** has a type parameter belonging to the class.

A **generic method** has a type parameter belonging to the method.

---

## 9.1 Simple Generic Method

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

It comes **before the return type**.

Complete example:

```java
class Demo
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

The compiler determines the appropriate type.

---

# 10. Why Is `<T>` Before `void`?

Look at:

```java
static <T> void display(T value)
```

Break it down:

```text
static
  ↓
access/modifier information

<T>
  ↓
type parameter declaration

void
  ↓
return type

display
  ↓
method name

(T value)
  ↓
parameter
```

The method must declare `T` before using it.

---

# 11. Generic Method Returning a Value

A generic method can return `T`.

```java
static <T> T getValue(T value)
{
    return value;
}
```

Now:

```java
Integer x = getValue(100);
```

The compiler understands:

```text
T = Integer
```

And:

```java
String s = getValue("Java");
```

means:

```text
T = String
```

Same method, different types.

---

# 12. Generic Class vs Generic Method

This is a common interview/exam question.

### Generic class

```java
class Box<T>
```

`T` belongs to the **class**.

### Generic method

```java
<T> void display(T value)
```

`T` belongs to the **method**.

Therefore, a normal class can contain a generic method:

```java
class Demo
{
    static <T> void display(T value)
    {
        System.out.println(value);
    }
}
```

The class doesn't need to be generic.

---

# 13. Type Safety

Now let's understand **why Generics are so useful**.

Suppose:

```java
List<String> names = new ArrayList<>();
```

The compiler knows:

```text
names
 ↓
List of String
```

So:

```java
names.add("Java");      // ✅
names.add("Python");    // ✅
```

But:

```java
names.add(100);         // ❌
```

The compiler says:

> You cannot put an Integer into a List<String>.

That's **type safety**.

---

# 14. Type Safety in Simple Words

Type safety means:

> **The program is prevented from using an object as the wrong type.**

Generics move many type-related errors from:

```text
Runtime
```

to:

```text
Compile time
```

That's a huge advantage.

---

# 15. Without Generics vs With Generics

### Without Generics

```java
List list = new ArrayList();

list.add("Java");
list.add(100);
```

Now the list can contain different types.

Later:

```java
String s = (String)list.get(1);
```

But element 1 is actually an `Integer`.

So:

```text
ClassCastException
```

---

### With Generics

```java
List<String> list = new ArrayList<>();

list.add("Java");
list.add(100);      // ❌ compile-time error
```

The compiler catches the mistake before the program runs.

---

# 16. Now Comes the Confusing Part: Wildcards

Don't worry. We'll take it slowly.

A wildcard is:

```java
?
```

Read it as:

> **some unknown type**

Example:

```java
List<?> list;
```

means:

> `list` is a List of some type, but I don't know exactly which type.

It could be:

```text
List<String>
List<Integer>
List<Double>
```

and so on.

---

# 17. Why Do We Need `?`?

Suppose we want a method that can display **any kind of List**.

We don't care whether it is:

```text
List<String>
List<Integer>
List<Double>
```

We can write:

```java
static void display(List<?> list)
{
    for(Object x : list)
    {
        System.out.println(x);
    }
}
```

Now all of these can be passed:

```java
display(stringList);
display(integerList);
display(doubleList);
```

That's the purpose of an unbounded wildcard.

---

# 18. What Does `List<?>` Really Say?

It does **not** mean:

> "This is a List<Object>."

It means:

> "This is a List of some unknown type."

That difference is extremely important.

These are different:

```java
List<Object>
```

and:

```java
List<?>
```

---

# 19. Can We Add to `List<?>`?

Suppose:

```java
List<?> list = new ArrayList<String>();
```

Can we do:

```java
list.add("Java");
```

No. ❌

Why?

Because the compiler doesn't know the actual type.

The actual list could be:

```text
List<String>
```

or:

```text
List<Integer>
```

If Java allowed:

```java
list.add("Java");
```

it could accidentally put a String into a `List<Integer>`.

Therefore:

```java
list.add("Java");    // ❌
```

But:

```java
list.add(null);      // ✅
```

is allowed.

---

# 20. Wildcard Types

There are three important forms:

```text
?
? extends T
? super T
```

We will now understand each one.

---

# 21. Unbounded Wildcard — `?`

Example:

```java
List<?> list
```

Meaning:

```text
List of unknown type
```

Use it when:

> You don't care what the exact type is.

You can safely read elements as `Object`.

```java
Object x = list.get(0);
```

But you generally cannot add arbitrary values.

---

# 22. Upper Bound — `? extends`

Now suppose we don't just want **any type**.

We want:

> `Number` or one of its subclasses.

We write:

```java
List<? extends Number>
```

This is an **upper-bounded wildcard**.

---

# 23. Understanding `? extends Number`

Think about this hierarchy:

```text
        Object
           ↑
        Number
        ↑    ↑
       /      \
 Integer     Double
```

Therefore:

```java
List<? extends Number>
```

can represent:

```text
List<Number>
List<Integer>
List<Double>
```

and other subclasses of `Number`.

---

# 24. Why Is It Called "Upper Bound"?

Because `Number` is the upper boundary.

```java
? extends Number
          ↑
       boundary
```

The unknown type must be:

```text
Number
OR
a subclass of Number
```

It cannot be:

```text
String
```

because String isn't a subtype of Number.

---

# 25. The Most Important Thing About `extends`

Remember:

```text
? extends T
```

means:

> **I can safely get/read values as T.**

Example:

```java
static void display(List<? extends Number> list)
{
    for(Number n : list)
    {
        System.out.println(n);
    }
}
```

Why is this safe?

Because the list is guaranteed to contain:

```text
Number
or subclass of Number
```

So every element can safely be treated as `Number`.

---

# 26. Why Can't We Add to `? extends Number`?

Suppose:

```java
List<? extends Number> list;
```

You might think:

```java
list.add(100);
```

should work.

But imagine the actual list is:

```text
List<Double>
```

Then inserting:

```text
Integer
```

would be wrong.

The compiler doesn't know the exact subtype.

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

# 27. Think of `extends` as a Producer

This makes it easy.

A:

```java
List<? extends Number>
```

can **produce** Number values for us to read.

```text
? extends Number
       ↓
    PRODUCER
       ↓
      READ
```

This gives us the first half of the famous rule:

> **Producer Extends**

---

# 28. Lower Bound — `? super`

Now let's reverse the situation.

Suppose we want a list that can accept `Integer` values.

We write:

```java
List<? super Integer>
```

This is a **lower-bounded wildcard**.

---

# 29. Understanding `? super Integer`

Hierarchy:

```text
Object
   ↑
Number
   ↑
Integer
```

`? super Integer` can represent:

```text
List<Integer>
List<Number>
List<Object>
```

because all three are `Integer` or a superclass of `Integer`.

---

# 30. Why Is It Called "Lower Bound"?

Because:

```java
? super Integer
```

uses `Integer` as the lower boundary.

The actual type can be:

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

because those aren't supertypes of Integer.

---

# 31. The Most Important Thing About `super`

Suppose:

```java
List<? super Integer> list;
```

We can safely do:

```java
list.add(100);
```

Why?

Because the actual list can only be:

```text
List<Integer>
List<Number>
List<Object>
```

and all of them can store an Integer.

Therefore:

```java
list.add(100);       // ✅
```

is safe.

---

# 32. Can We Add a Double?

No.

```java
list.add(10.5);      // ❌
```

Why?

Because the actual list could be:

```text
List<Integer>
```

and `Double` isn't allowed there.

So the guaranteed safe thing to add is an `Integer` (and values compatible with it).

---

# 33. Can We Read from `? super Integer`?

Yes, but the safe type is:

```java
Object
```

Example:

```java
Object x = list.get(0);
```

Why not:

```java
Integer x = list.get(0);
```

?

Because the actual list could be:

```text
List<Object>
```

The compiler doesn't know whether the object retrieved is actually an Integer.

So:

```text
? super Integer
      ↓
Safe to WRITE Integer
      ↓
Safe to READ as Object
```

---

# 34. Think of `super` as a Consumer

A:

```java
List<? super Integer>
```

can **consume Integer values**.

```text
? super Integer
       ↓
    CONSUMER
       ↓
      WRITE
```

This gives us the second half:

> **Consumer Super**

---

# 35. PECS

Now you know the famous rule:

# **PECS**

```text
P = Producer
E = Extends

C = Consumer
S = Super
```

Therefore:

```text
Producer → extends
Consumer → super
```

Or:

> **Producer Extends, Consumer Super.**

---

# 36. Real-Life Analogy

Imagine two boxes.

### Box A — Producer

You ask:

> "Give me something that is a Number."

The box says:

> "I contain some unknown type that extends Number."

```text
? extends Number
```

You can take numbers out.

You don't know exactly whether they're:

```text
Integer
Double
Float
```

but you know they're Numbers.

---

### Box B — Consumer

You say:

> "I want to put Integer values into this box."

The box is:

```text
? super Integer
```

It could be:

```text
Integer box
Number box
Object box
```

All can accept Integer.

---

# 37. Compare Everything

| Concept       | Meaning                | Main operation    |
| ------------- | ---------------------- | ----------------- |
| `T`           | Named type parameter   | Define/use a type |
| `?`           | Unknown type           | Read as Object    |
| `? extends T` | Unknown subtype of T   | Read              |
| `? super T`   | Unknown supertype of T | Write             |

---

# 38. Generic Class Example

```java
class Box<T>
{
    private T value;

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

Usage:

```java
Box<String> b1 = new Box<>();

b1.set("Java");

String s = b1.get();
```

And:

```java
Box<Integer> b2 = new Box<>();

b2.set(100);

Integer x = b2.get();
```

---

# 39. Generic Method Example

```java
class Demo
{
    static <T> void print(T value)
    {
        System.out.println(value);
    }

    public static void main(String[] args)
    {
        print("Java");
        print(100);
        print(10.5);
    }
}
```

One method handles multiple types.

---

# 40. Upper-Bound Example

```java
static double calculate(List<? extends Number> list)
{
    double sum = 0;

    for(Number n : list)
    {
        sum += n.doubleValue();
    }

    return sum;
}
```

Now:

```java
List<Integer> a = Arrays.asList(10, 20, 30);

List<Double> b = Arrays.asList(1.5, 2.5, 3.5);
```

Both can be passed to:

```java
calculate(a);
calculate(b);
```

because both contain types that extend `Number`.

---

# 41. Lower-Bound Example

```java
static void addNumbers(List<? super Integer> list)
{
    list.add(10);
    list.add(20);
    list.add(30);
}
```

It can accept:

```java
List<Integer>
```

or:

```java
List<Number>
```

or:

```java
List<Object>
```

because all can store Integer values.

---

# 42. The Biggest Confusion

Students often think:

```text
extends = inheritance
super = parent
```

That is not enough to understand generics.

Instead remember the **direction of safe operations**.

### `extends`

```text
? extends Number
       ↓
unknown subtype
       ↓
I can READ it as Number
       ↓
Producer
```

### `super`

```text
? super Integer
       ↓
unknown supertype
       ↓
I can WRITE Integer
       ↓
Consumer
```

---

# 43. One More Very Important Difference

These two are **not equivalent**:

```java
List<Object>
```

and:

```java
List<?>
```

### `List<Object>`

Means:

> This is specifically a List whose element type is Object.

You can add:

```java
list.add("Java");
list.add(100);
```

because everything is an Object.

### `List<?>`

Means:

> This is a List of some unknown type.

You cannot arbitrarily add:

```java
list.add("Java");   // ❌
```

because it could actually be a `List<Integer>`.

---

# 44. Quick Mental Test

Suppose:

```java
List<Integer> list = new ArrayList<>();
```

Can this be assigned?

```java
List<? extends Number> x = list;
```

**Yes.** ✅

Because Integer extends Number.

---

Can this be assigned?

```java
List<? super Integer> x = list;
```

**Yes.** ✅

Because the actual type is Integer itself.

---

Can this be assigned?

```java
List<Object> x = list;
```

**No.** ❌

Because generic types are invariant.

---

# 45. Final Learning Map

```text
                         GENERICS
                            │
              ┌─────────────┴─────────────┐
              │                           │
        Generic Class               Generic Method
              │                           │
         class Box<T>                <T> method()
              │
              ↓
        Type Parameter
              │
              ↓
         Type Safety
              │
              ↓
          Wildcards
              │
       ┌──────┼───────┐
       │      │       │
       ?   extends   super
       │      │       │
   unknown   upper   lower
    type     bound   bound
              │       │
           Producer Consumer
              │       │
             READ    WRITE
```

---

# 🧠 Remember It Like This

### Generic Class

> **"My class can work with different types."**

```java
class Box<T>
```

### Generic Method

> **"My method can work with different types."**

```java
<T> void show(T x)
```

### Type Safety

> **"Don't put the wrong type where another type is expected."**

```java
List<String>
```

### Wildcard

> **"I don't know the exact type."**

```java
?
```

### Upper Bound

> **"I know it is this type or below it."**

```java
? extends Number
```

Think:

> **READ / PRODUCER**

### Lower Bound

> **"I know it is this type or above it."**

```java
? super Integer
```

Think:

> **WRITE / CONSUMER**

---

# ⭐ The 6-Line Exam Memory Trick

```text
Generic Class  → class Box<T>
Generic Method → <T> method()
Type Safety    → Compile-time type checking
Wildcard       → ?
Upper Bound    → ? extends T → READ / Producer
Lower Bound    → ? super T   → WRITE / Consumer
```

And the single most important sentence:

> **PECS = Producer Extends, Consumer Super.**
