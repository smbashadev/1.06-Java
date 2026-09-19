# Loops in Java — DEEP DIVE 🔥

A **loop** is a control-flow statement that allows Java to execute the same block of code repeatedly based on a condition or by traversing elements.

Java's four commonly taught loops are:

```text
1. for loop
2. while loop
3. do-while loop
4. enhanced for loop (for-each)
```

The most important distinction is **how repetition is controlled**.

---

# 1. First Understand the General Loop

Every traditional loop has these basic ideas:

```text
Initialization
      ↓
   Condition
      ↓
   Loop Body
      ↓
    Update
      ↓
   Condition
      ↺
```

For example:

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
    i++;
}
```

Here:

```text
Initialization → int i = 1
Condition      → i <= 5
Body           → System.out.println(i)
Update         → i++
```

If the condition becomes false, the loop terminates.

---

# 2. Why Do We Need Loops?

Without a loop:

```java
System.out.println(1);
System.out.println(2);
System.out.println(3);
System.out.println(4);
System.out.println(5);
```

With a loop:

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

Instead of writing the same operation repeatedly, we describe **how repetition should happen**.

---

# 🟢 1. `for` LOOP

## Definition

The `for` loop is generally used when the initialization, condition, and update can be expressed conveniently together, especially for **count-controlled repetition**.

---

## Syntax

```java
for (initialization; condition; update) {
    // body
}
```

Example:

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

---

# 3. How `for` Loop Executes

Consider:

```java
for (int i = 1; i <= 3; i++) {
    System.out.println(i);
}
```

Execution:

```text
Step 1:
int i = 1

Step 2:
i <= 3
1 <= 3 → true

Step 3:
print 1

Step 4:
i++
i becomes 2

Step 5:
2 <= 3 → true

Step 6:
print 2

Step 7:
i++
i becomes 3

Step 8:
3 <= 3 → true

Step 9:
print 3

Step 10:
i++
i becomes 4

Step 11:
4 <= 3 → false

Exit
```

Output:

```text
1
2
3
```

---

# 4. Important: Initialization Runs Only Once

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

This:

```java
int i = 1
```

runs only once.

The repeated part is:

```text
condition → body → update
```

---

# 5. Can We Have Multiple Initializations?

Yes.

```java
for (int i = 1, j = 5; i <= 5; i++, j--) {
    System.out.println(i + " " + j);
}
```

Output:

```text
1 5
2 4
3 3
4 2
5 1
```

---

# 6. Can We Omit Parts of a `for` Loop?

Yes, syntactically.

For example:

```java
int i = 1;

for (; i <= 5; i++) {
    System.out.println(i);
}
```

Initialization is outside the loop.

You can also write:

```java
for (int i = 1; ; i++) {
    System.out.println(i);
}
```

This has no condition, so the condition is effectively always true.

⚠️ This becomes an infinite loop unless something inside terminates it.

---

# 7. Infinite `for` Loop

```java
for (;;) {
    System.out.println("Hello");
}
```

This is an infinite loop.

Equivalent idea:

```java
while (true) {
    System.out.println("Hello");
}
```

---

# 🟡 2. `while` LOOP

## Definition

A `while` loop repeatedly executes its body **as long as its condition is true**.

The condition is checked **before** each iteration.

---

## Syntax

```java
while (condition) {
    // body
}
```

Example:

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
    i++;
}
```

---

# 8. Flow of `while`

```text
        Start
          ↓
    Initialization
          ↓
       Condition
       ↙       ↘
    true       false
      ↓           ↓
    Body         Exit
      ↓
    Update
      ↓
   Condition
      ↺
```

---

# 9. Why Is It Called an Entry-Controlled Loop?

Because the condition is checked **before entering the body**.

Example:

```java
int i = 10;

while (i < 5) {
    System.out.println(i);
}
```

Output:

```text
(no output)
```

Why?

```text
10 < 5
 ↓
false
 ↓
body never executes
```

Therefore:

> `while` is an **entry-controlled loop**.

---

# 10. Infinite `while` Loop

```java
while (true) {
    System.out.println("Hello");
}
```

This keeps executing unless something causes termination.

---

# 🔵 3. `do-while` LOOP

## Definition

A `do-while` loop executes its body first and checks the condition afterward.

---

## Syntax

```java
do {
    // body
} while (condition);
```

⚠️ The semicolon after the condition is required.

---

# 11. Example

```java
int i = 1;

do {
    System.out.println(i);
    i++;
} while (i <= 5);
```

Output:

```text
1
2
3
4
5
```

---

# 12. Why Is `do-while` Different?

Look at:

```java
int i = 10;

do {
    System.out.println(i);
} while (i < 5);
```

Output:

```text
10
```

Even though:

```text
10 < 5 → false
```

the body already executed.

Execution:

```text
Start
 ↓
Body
 ↓
Condition
 ↓
false
 ↓
Exit
```

Therefore:

> `do-while` is an **exit-controlled loop**.

---

# 13. The Most Important Difference

Compare:

### `while`

```java
while (condition) {
    body
}
```

```text
Condition
   ↓
Body
```

### `do-while`

```java
do {
    body
} while (condition);
```

```text
Body
  ↓
Condition
```

That's the fundamental difference.

---

# 🟣 4. ENHANCED `for` LOOP

The enhanced `for` loop is also called the:

> **for-each loop**

It is primarily used to traverse arrays and many Java collections.

---

## Syntax

```java
for (dataType variable : arrayOrCollection) {
    // body
}
```

---

# 14. Array Example

```java
int[] numbers = {10, 20, 30, 40, 50};

for (int n : numbers) {
    System.out.println(n);
}
```

Output:

```text
10
20
30
40
50
```

Conceptually:

```text
n = 10
n = 20
n = 30
n = 40
n = 50
```

---

# 15. What Does `:` Mean?

In:

```java
for (int n : numbers)
```

read it approximately as:

> "For each element in `numbers`, assign that element to `n`."

So:

```text
numbers = {10,20,30}

iteration 1 → n = 10
iteration 2 → n = 20
iteration 3 → n = 30
```

---

# 16. Traditional `for` vs Enhanced `for`

Suppose:

```java
int[] a = {10, 20, 30};
```

### Traditional `for`

```java
for (int i = 0; i < a.length; i++) {
    System.out.println(a[i]);
}
```

### Enhanced `for`

```java
for (int x : a) {
    System.out.println(x);
}
```

The enhanced form is shorter when you simply want to process each element.

---

# 17. Important Limitation of Enhanced `for`

Suppose you need the index:

```text
0
1
2
3
```

A traditional `for` loop is often more suitable:

```java
for (int i = 0; i < a.length; i++) {
    System.out.println(i + " " + a[i]);
}
```

Enhanced `for` gives you the element directly, not an explicit index variable.

---

# 18. Does Enhanced `for` Modify the Array?

Be careful.

```java
int[] a = {10, 20, 30};

for (int x : a) {
    x = x + 10;
}
```

This does **not** change the array to:

```text
20 30 40
```

The variable `x` receives the element value.

For primitive elements, changing `x` doesn't change the array element.

If you need to modify elements by index:

```java
for (int i = 0; i < a.length; i++) {
    a[i] = a[i] + 10;
}
```

---

# 19. Enhanced `for` with Strings

```java
String[] names = {"Ravi", "Arun", "Priya"};

for (String name : names) {
    System.out.println(name);
}
```

Output:

```text
Ravi
Arun
Priya
```

---

# 20. Enhanced `for` with Collections

Example:

```java
ArrayList<String> names = new ArrayList<>();

names.add("Ravi");
names.add("Arun");
names.add("Priya");

for (String name : names) {
    System.out.println(name);
}
```

It is widely used for traversing collections.

---

# 🔥 COMPARING ALL FOUR

| Feature                   | `for`                  | `while`                    | `do-while`              | Enhanced `for`                                     |
| ------------------------- | ---------------------- | -------------------------- | ----------------------- | -------------------------------------------------- |
| Condition                 | Before body            | Before body                | After body              | Traversal-based                                    |
| Minimum executions        | 0                      | 0                          | 1                       | 0 if no elements                                   |
| Initialization syntax     | Usually in loop        | Usually before loop        | Usually before loop     | Variable declaration in loop                       |
| Update                    | Usually in loop        | Usually in body            | Usually in body         | Automatic traversal                                |
| Index available directly? | ✅                      | ✅                          | ✅                       | ❌                                                  |
| Best for                  | Count-controlled loops | Condition-controlled loops | At-least-once execution | Arrays/collections                                 |
| Can be infinite?          | ✅                      | ✅                          | ✅                       | Not normally used for intentional infinite looping |

---

# 21. Entry-Controlled vs Exit-Controlled

This is an important exam question.

### Entry-controlled

Condition checked **before** body:

```text
for
while
```

```text
Condition
    ↓
  Body
```

### Exit-controlled

Condition checked **after** body:

```text
do-while
```

```text
Body
 ↓
Condition
```

### Enhanced `for`

It's a traversal construct rather than simply being classified by the same condition pattern.

---

# 22. Nested Loops

A loop inside another loop is called a **nested loop**.

Example:

```java
for (int i = 1; i <= 3; i++) {

    for (int j = 1; j <= 3; j++) {
        System.out.println(i + " " + j);
    }
}
```

Output:

```text
1 1
1 2
1 3
2 1
2 2
2 3
3 1
3 2
3 3
```

Think:

```text
Outer loop
   ↓
Inner loop runs completely
   ↓
Outer loop updates
   ↓
Inner loop runs completely again
```

---

# 23. Nested Loop — Execution Count

```java
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 4; j++) {
        System.out.println("*");
    }
}
```

Outer loop:

```text
3 times
```

Inner loop:

```text
4 times for each outer iteration
```

Total:

```text
3 × 4 = 12
```

body executions.

---

# 24. `break`

`break` terminates the nearest enclosing loop.

```java
for (int i = 1; i <= 10; i++) {

    if (i == 5) {
        break;
    }

    System.out.println(i);
}
```

Output:

```text
1
2
3
4
```

Flow:

```text
i = 5
 ↓
break
 ↓
loop terminates
```

---

# 25. `continue`

`continue` skips the remaining body of the **current iteration** and proceeds to the next iteration.

```java
for (int i = 1; i <= 5; i++) {

    if (i == 3) {
        continue;
    }

    System.out.println(i);
}
```

Output:

```text
1
2
4
5
```

At `i == 3`:

```text
continue
   ↓
skip remaining body
   ↓
next iteration
```

---

# 26. `break` vs `continue`

| `break`                       | `continue`                             |
| ----------------------------- | -------------------------------------- |
| Terminates the loop           | Skips current iteration                |
| Control goes outside the loop | Control proceeds toward next iteration |
| Loop ends                     | Loop continues                         |

Memory trick:

```text
break    → STOP
continue → SKIP
```

---

# 27. Labeled `break`

Java also supports labels.

```java
outer:
for (int i = 1; i <= 3; i++) {

    for (int j = 1; j <= 3; j++) {

        if (i == 2 && j == 2) {
            break outer;
        }

        System.out.println(i + " " + j);
    }
}
```

`break outer;` terminates the loop associated with the `outer` label.

This is particularly useful when working with nested loops.

---

# 28. Labeled `continue`

Java also allows:

```java
continue outer;
```

This skips to the next iteration of the labeled outer loop.

Example:

```java
outer:
for (int i = 1; i <= 3; i++) {

    for (int j = 1; j <= 3; j++) {

        if (j == 2) {
            continue outer;
        }

        System.out.println(i + " " + j);
    }
}
```

---

# 29. Common Mistake — Forgetting Update

This is dangerous:

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
}
```

`i` never changes.

Therefore:

```text
i = 1
condition true
print
condition true
print
condition true
...
```

Infinite loop.

Correct:

```java
while (i <= 5) {
    System.out.println(i);
    i++;
}
```

---

# 30. Common Mistake — Semicolon

Look:

```java
while (i <= 5);
{
    System.out.println(i);
}
```

That semicolon terminates the `while` statement.

It can produce surprising behavior.

Similarly, don't accidentally write:

```java
for (int i = 0; i < 5; i++);
```

unless an empty loop is genuinely intended.

---

# 31. Common Mistake — `do-while` Semicolon

Correct:

```java
do {
    System.out.println("Hello");
} while (condition);
```

The semicolon is required.

This is different from:

```java
while (condition) {
}
```

where the semicolon is not placed after the condition.

---

# 32. Scope of `for` Variable

Consider:

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

The `i` declared there has scope associated with the `for` statement/body.

So this is invalid after the loop:

```java
System.out.println(i); // ❌
```

If you need `i` afterward:

```java
int i;

for (i = 1; i <= 5; i++) {
    System.out.println(i);
}

System.out.println(i);
```

---

# 33. `for` Loop Can Have No Body

Java permits:

```java
for (int i = 0; i < 10; i++);
```

The loop body is an empty statement.

This is legal Java, although often accidental.

---

# 34. Infinite Loop vs Finite Loop

### Finite

```java
for (int i = 1; i <= 5; i++) {
}
```

Eventually:

```text
condition → false
```

### Infinite

```java
while (true) {
}
```

There is no natural false condition.

It requires something such as `break` or another termination mechanism to leave the loop.

---

# 35. Which Loop Should You Choose?

### Choose `for` when:

You know or can naturally express the iteration using a counter.

```java
for (int i = 0; i < 10; i++)
```

### Choose `while` when:

The continuation condition is the main focus.

```java
while (userInput != 0)
```

### Choose `do-while` when:

The operation must happen **at least once**.

```java
do {
    // show menu
} while (choice != 0);
```

### Choose enhanced `for` when:

You simply need to process each element.

```java
for (int x : numbers)
```

---

# 36. Classic Menu Example — Why `do-while`?

Imagine a menu:

```text
1. Add
2. Delete
3. Search
0. Exit
```

You want the menu to appear at least once.

```java
int choice;

do {
    System.out.println("1. Add");
    System.out.println("2. Delete");
    System.out.println("3. Search");
    System.out.println("0. Exit");

    // read choice

} while (choice != 0);
```

This is a natural use of `do-while`.

---

# 37. Classic Array Example — Enhanced `for`

```java
int[] marks = {80, 75, 90, 85};

int total = 0;

for (int mark : marks) {
    total += mark;
}

System.out.println(total);
```

Output:

```text
330
```

Here we don't need an index, so enhanced `for` is convenient.

---

# 38. Classic Search Example — Traditional `for`

```java
int[] numbers = {10, 20, 30, 40, 50};

int target = 30;

for (int i = 0; i < numbers.length; i++) {

    if (numbers[i] == target) {
        System.out.println("Found at index " + i);
        break;
    }
}
```

Output:

```text
Found at index 2
```

A traditional `for` loop is useful because we need the **index**.

---

# 39. The Four Loops as Questions

When deciding which loop to use, ask:

### Question 1

> Do I have a counter-controlled repetition?

```text
YES → for
```

### Question 2

> Is the condition the main thing controlling repetition?

```text
YES → while
```

### Question 3

> Must the body execute at least once?

```text
YES → do-while
```

### Question 4

> Am I simply processing every element of an array/collection?

```text
YES → enhanced for
```

---

# 🧠 MASTER FLOWCHART

```text
                    Need repetition?
                          │
                          ▼
                ┌────────────────────┐
                │ What are you doing?│
                └─────────┬──────────┘
                          │
          ┌───────────────┼─────────────────┐
          │               │                 │
          ▼               ▼                 ▼
   Count/control     Condition-driven   Traverse elements
          │               │                 │
          ▼               ▼                 ▼
         for            while        enhanced for
                          │
                          │
                  Must execute once?
                          │
                         YES
                          ↓
                      do-while
```

---

# 🔥 FINAL COMPARISON

```text
for
 ↓
Initialization → Condition → Body → Update
                         ↖__________↙

while
 ↓
Initialization → Condition → Body → Update
                         ↖________↙

do-while
 ↓
Initialization → Body → Update → Condition
                                  ↖______↙

enhanced for
 ↓
Get next element → Body → Get next element → ...
```

---

# 🎯 EXAM-READY DEFINITIONS

### `for`

> The `for` loop repeatedly executes a block of statements while its condition is true, with initialization, condition, and update typically specified in one statement.

### `while`

> The `while` loop repeatedly executes its body as long as its condition is true, with the condition checked before each iteration.

### `do-while`

> The `do-while` loop executes its body at least once and then repeatedly executes it while its condition remains true.

### Enhanced `for`

> The enhanced `for` loop, or for-each loop, provides a convenient way to traverse elements of arrays and supported collections without explicitly managing an index.

---

# 🚨 12 DOUBT KILLERS

| Doubt                                       | Answer |
| ------------------------------------------- | ------ |
| `for` can execute zero times?               | ✅      |
| `while` can execute zero times?             | ✅      |
| `do-while` can execute zero times?          | ❌      |
| `do-while` executes at least once?          | ✅      |
| `for` condition is checked before body?     | ✅      |
| `while` condition is checked before body?   | ✅      |
| `do-while` condition is checked after body? | ✅      |
| Enhanced `for` works with arrays?           | ✅      |
| Enhanced `for` is also called for-each?     | ✅      |
| `break` terminates the loop?                | ✅      |
| `continue` skips the current iteration?     | ✅      |
| Loops can be nested?                        | ✅      |

## 🔑 Ultimate memory line

> **`for` = count, `while` = check then execute, `do-while` = execute then check, enhanced `for` = visit every element.**
