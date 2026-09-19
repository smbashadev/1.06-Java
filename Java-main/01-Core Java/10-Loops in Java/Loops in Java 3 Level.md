# Loops in Java — 3 LEVEL 🪜

We'll learn loops in **three stages**:

```text
LEVEL 1 → Know
LEVEL 2 → Understand
LEVEL 3 → Master
```

Java's four loops:

```text
for
while
do-while
enhanced for
```

---

# 🟢 LEVEL 1 — KNOW

## 1. What is a Loop?

A **loop** repeatedly executes a block of statements while its repetition rule allows it to continue.

Example:

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

Output:

```text
1
2
3
4
5
```

Instead of writing `println()` five times, we use one loop.

---

# 2. `for` Loop

### Syntax

```java
for (initialization; condition; update) {
    // body
}
```

### Example

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

Think:

```text
Start → Check → Execute → Update → Check → ...
```

---

# 3. `while` Loop

### Syntax

```java
while (condition) {
    // body
}
```

### Example

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
    i++;
}
```

Think:

```text
Check → Execute → Update → Check → ...
```

---

# 4. `do-while` Loop

### Syntax

```java
do {
    // body
} while (condition);
```

### Example

```java
int i = 1;

do {
    System.out.println(i);
    i++;
} while (i <= 5);
```

Think:

```text
Execute → Update → Check → Execute → ...
```

### Key point

The body executes **at least once**.

---

# 5. Enhanced `for` Loop

Also called **for-each loop**.

### Syntax

```java
for (dataType variable : arrayOrCollection) {
    // body
}
```

Example:

```java
int[] numbers = {10, 20, 30};

for (int n : numbers) {
    System.out.println(n);
}
```

Output:

```text
10
20
30
```

Think:

```text
First element → Second element → Third element → ...
```

---

# 🟡 LEVEL 2 — UNDERSTAND

## 6. The Most Important Difference

### `for`

Use it when the repetition is naturally **counter-controlled**.

```java
for (int i = 1; i <= 10; i++) {
    System.out.println(i);
}
```

---

### `while`

Use it when the **condition** is the main thing controlling repetition.

```java
while (balance > 0) {
    // continue
}
```

---

### `do-while`

Use it when the body must execute **at least once**.

```java
do {
    // show menu
} while (choice != 0);
```

---

### Enhanced `for`

Use it when you want to process **each element** of an array or collection.

```java
for (int mark : marks) {
    System.out.println(mark);
}
```

---

# 7. Entry-Controlled vs Exit-Controlled

### Entry-controlled

Condition is checked **before** the body.

```text
for
while
```

```text
Condition
   ↓
Body
```

These can execute **zero times**.

Example:

```java
int i = 10;

while (i < 5) {
    System.out.println(i);
}
```

Output:

```text
nothing
```

---

### Exit-controlled

Condition is checked **after** the body.

```text
do-while
```

```text
Body
 ↓
Condition
```

Therefore it executes **at least once**.

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

---

# 8. `break`

`break` means:

> **Stop the loop completely.**

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

---

# 9. `continue`

`continue` means:

> **Skip the current iteration and continue with the next one.**

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

### Memory

```text
break     → STOP
continue  → SKIP
```

---

# 🔴 LEVEL 3 — MASTER

Now let's understand what actually happens during execution.

---

## 10. `for` Loop — Complete Execution

```java
for (int i = 1; i <= 3; i++) {
    System.out.println(i);
}
```

### Step 1

Initialization:

```text
i = 1
```

### Step 2

Condition:

```text
1 <= 3 → true
```

### Step 3

Body:

```text
print 1
```

### Step 4

Update:

```text
i++
i = 2
```

Then:

```text
2 <= 3 → true
print 2
i++
```

Then:

```text
3 <= 3 → true
print 3
i++
```

Now:

```text
i = 4
4 <= 3 → false
```

Loop ends.

---

# 11. The Golden Loop Formula

For traditional loops, identify:

```text
1. Initialization
2. Condition
3. Body
4. Update
```

Example:

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
    i++;
}
```

Identify:

```text
Initialization → int i = 1
Condition      → i <= 5
Body           → println(i)
Update         → i++
```

If you understand these four, you can trace most basic loops.

---

# 12. Why Does an Infinite Loop Happen?

Example:

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
}
```

Ask:

> What changes `i`?

Nothing.

Therefore:

```text
i = 1
 ↓
1 <= 5 → true
 ↓
print
 ↓
i still 1
 ↓
1 <= 5 → true
 ↓
print
 ↓
forever...
```

Correct:

```java
while (i <= 5) {
    System.out.println(i);
    i++;
}
```

---

# 13. Nested Loops

A loop inside another loop:

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

### Important idea

For every one iteration of the outer loop, the inner loop runs completely.

So:

```text
Outer = 3 times
Inner = 3 times each

Total = 3 × 3 = 9
```

---

# 14. Enhanced `for` — Important Difference

Given:

```java
int[] a = {10, 20, 30};
```

Traditional:

```java
for (int i = 0; i < a.length; i++) {
    System.out.println(a[i]);
}
```

Enhanced:

```java
for (int x : a) {
    System.out.println(x);
}
```

The second is simpler when you only need the elements.

But if you need the **index**, traditional `for` is usually more appropriate:

```java
for (int i = 0; i < a.length; i++) {
    System.out.println(i + " " + a[i]);
}
```

---

# 15. Enhanced `for` Does Not Give You the Index

```java
int[] a = {10, 20, 30};

for (int x : a) {
    System.out.println(x);
}
```

Here:

```text
x = 10
x = 20
x = 30
```

`x` is an **element**, not:

```text
0
1
2
```

---

# 16. Classic Pattern — Sum

```java
int sum = 0;

for (int i = 1; i <= 5; i++) {
    sum = sum + i;
}

System.out.println(sum);
```

Output:

```text
15
```

Execution:

```text
sum = 0
 ↓
+1 = 1
 ↓
+2 = 3
 ↓
+3 = 6
 ↓
+4 = 10
 ↓
+5 = 15
```

This pattern is called an **accumulator pattern**.

---

# 17. Classic Pattern — Even Numbers

```java
for (int i = 1; i <= 10; i++) {

    if (i % 2 == 0) {
        System.out.println(i);
    }
}
```

Output:

```text
2
4
6
8
10
```

Here:

```text
Loop → generates numbers
if   → filters numbers
```

---

# 18. Classic Pattern — Search

```java
int[] numbers = {10, 20, 30, 40};

for (int i = 0; i < numbers.length; i++) {

    if (numbers[i] == 30) {
        System.out.println("Found");
        break;
    }
}
```

Once `30` is found:

```text
if true
 ↓
break
 ↓
loop ends
```

---

# 19. Which Loop Should You Choose?

Use this decision system:

```text
                Need a loop?
                     ↓
        ┌────────────┼────────────┐
        ↓            ↓            ↓
   Count/control   Condition    Elements
        ↓            ↓            ↓
       for         while    enhanced for
                     │
                     ↓
              Must execute once?
                     │
                    YES
                     ↓
                 do-while
```

---

# 🧠 FINAL 3-LEVEL REVISION

## 🟢 LEVEL 1 — KNOW

```text
for
while
do-while
enhanced for
```

Basic meanings:

```text
for          → counter/control
while        → condition first
do-while     → body first
enhanced for → every element
```

---

## 🟡 LEVEL 2 — UNDERSTAND

```text
for / while
    ↓
condition checked first
    ↓
may execute 0 times


do-while
    ↓
body executes first
    ↓
executes at least once


enhanced for
    ↓
gets each element automatically
```

---

## 🔴 LEVEL 3 — MASTER

You should be able to identify:

```text
Initialization
Condition
Body
Update
```

and understand:

```text
break     → terminate loop
continue  → skip iteration
nested    → loop inside loop
infinite  → condition never becomes false
```

### 🔥 Ultimate memory formula

> **`for` = I control the count.**
> **`while` = Check first.**
> **`do-while` = Do first.**
> **Enhanced `for` = Give me every element.**
> **`break` = Stop.**
> **`continue` = Skip.**
