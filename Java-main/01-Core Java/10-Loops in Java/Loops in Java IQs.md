# Loops in Java — DOUBT KILLER 💥

This is the **confusion-clearing version**.
Instead of memorizing definitions, we'll kill the doubts that usually appear in exams, interviews, and programming.

---

# 1. What exactly is a Loop?

A loop is a mechanism that allows Java to **execute a block of statements repeatedly**.

Example:

```java
for (int i = 1; i <= 3; i++) {
    System.out.println("Java");
}
```

Output:

```text
Java
Java
Java
```

So:

```text
Loop = Repetition
```

---

# 2. How Many Loops Are There in Java?

For your syllabus, remember these **4**:

```text
1. for
2. while
3. do-while
4. enhanced for
```

---

# 3. Biggest Doubt: Which Loop Should I Use?

Use this simple decision rule:

| Situation                                        | Use            |
| ------------------------------------------------ | -------------- |
| I want counter-controlled repetition             | `for`          |
| I want condition-controlled repetition           | `while`        |
| I must execute the body at least once            | `do-while`     |
| I want to process every array/collection element | Enhanced `for` |

### Example

**"Print 1 to 10."**

```java
for (int i = 1; i <= 10; i++)
```

**"Keep going while password is wrong."**

```java
while (!correct)
```

**"Show menu at least once."**

```java
do {
    // menu
} while (choice != 0);
```

**"Print every element of an array."**

```java
for (int x : numbers)
```

---

# 4. Biggest Doubt: `for` vs `while`

They can often accomplish the **same task**.

### `for`

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

### `while`

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
    i++;
}
```

Both produce:

```text
1
2
3
4
5
```

So what's the difference?

### Main difference is organization/style.

`for` conveniently puts:

```text
initialization
condition
update
```

together.

`while` separates them.

### Memory:

> **`for` = counter-oriented**
> **`while` = condition-oriented**

---

# 5. Biggest Doubt: `while` vs `do-while`

This is extremely important.

### `while`

```java
while (condition) {
    // body
}
```

Order:

```text
CHECK → BODY
```

### `do-while`

```java
do {
    // body
} while (condition);
```

Order:

```text
BODY → CHECK
```

Therefore:

```text
while     → can execute 0 times
do-while  → executes at least 1 time
```

---

# 6. Prove That `do-while` Executes Once

Consider:

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

Because:

```text
10 < 5 → false
```

The body never executes.

Now:

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

Why?

```text
do body
   ↓
print 10
   ↓
check 10 < 5
   ↓
false
   ↓
exit
```

### 🔥 Golden rule

> **`do-while` always gets one chance to execute its body.**

---

# 7. Does `for` Execute At Least Once?

❌ No.

Example:

```java
for (int i = 10; i < 5; i++) {
    System.out.println(i);
}
```

Condition is false immediately.

Output:

```text
nothing
```

Therefore:

```text
for      → 0 or more
while    → 0 or more
do-while → 1 or more
```

---

# 8. Is Enhanced `for` a Completely Different Type of Loop?

It is a special form of `for` designed for **iteration over arrays and supported collections**.

Example:

```java
int[] a = {10, 20, 30};

for (int x : a) {
    System.out.println(x);
}
```

You don't manually write:

```java
i = 0
i++
i < a.length
```

Java handles the traversal.

---

# 9. Does Enhanced `for` Give Index?

❌ No.

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

`x` is the **element**.

It isn't:

```text
x = 0
x = 1
x = 2
```

If you need indexes:

```java
for (int i = 0; i < a.length; i++) {
    System.out.println(i + " " + a[i]);
}
```

---

# 10. Can I Use Enhanced `for` With an Array?

✅ Yes.

```java
int[] numbers = {10, 20, 30};

for (int n : numbers) {
    System.out.println(n);
}
```

---

# 11. Can I Use Enhanced `for` With Collections?

✅ Yes.

For example:

```java
ArrayList<Integer> list = new ArrayList<>();

list.add(10);
list.add(20);
list.add(30);

for (int n : list) {
    System.out.println(n);
}
```

---

# 12. What Does `:` Mean?

This:

```java
for (int n : numbers)
```

can be mentally read as:

> **For each `n` in `numbers`**

Example:

```text
numbers = {10,20,30}

First iteration  → n = 10
Second iteration → n = 20
Third iteration  → n = 30
```

---

# 13. Biggest Doubt: What Is an Iteration?

One **iteration** means:

> One complete execution of the loop body.

Example:

```java
for (int i = 1; i <= 3; i++) {
    System.out.println(i);
}
```

There are **3 iterations**.

```text
Iteration 1 → print 1
Iteration 2 → print 2
Iteration 3 → print 3
```

---

# 14. What Is an Infinite Loop?

An infinite loop is a loop that doesn't naturally terminate because its continuation condition never becomes false.

Example:

```java
while (true) {
    System.out.println("Hello");
}
```

The condition:

```text
true
```

never becomes false.

---

# 15. Why Does This Become Infinite?

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
}
```

Because `i` never changes.

```text
i = 1
 ↓
1 <= 5 → true
 ↓
print
 ↓
i = 1
 ↓
1 <= 5 → true
 ↓
print
 ↓
...
```

### Doubt killer:

> A loop does not automatically change your counter.

You must provide the appropriate update.

---

# 16. Does Every Loop Need `i++`?

❌ No.

`i++` is only one possible update.

You can use:

```java
i++;
```

or:

```java
i--;
```

or:

```java
i += 2;
```

or:

```java
i = i * 2;
```

depending on the problem.

Example:

```java
for (int i = 2; i <= 10; i += 2) {
    System.out.println(i);
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

---

# 17. Does `i++` Mean "Run the Loop"?

❌ No.

`i++` means:

> Increase the value of `i` by 1.

It is often used as the **update expression** in a loop.

---

# 18. What Is `i++` Doing in a `for` Loop?

```java
for (int i = 1; i <= 5; i++) {
```

Break it apart:

```text
int i = 1 → initialization
i <= 5    → condition
i++       → update
```

---

# 19. What Happens If the Condition Is Always True?

Example:

```java
for (int i = 1; i <= 5; ) {
    System.out.println(i);
}
```

There is no update.

`i` remains `1`.

Therefore:

```text
1 <= 5 → always true
```

Infinite loop.

---

# 20. `break` — Does It Skip One Iteration?

❌ No.

`break` **terminates the loop**.

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

At 5:

```text
break → EXIT LOOP
```

---

# 21. `continue` — Does It Stop the Loop?

❌ No.

It skips the current iteration.

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

At 3:

```text
continue
   ↓
skip remaining body
   ↓
next iteration
```

### Remember forever:

```text
break     → STOP
continue  → SKIP
```

---

# 22. Does `continue` Skip the Update in a `for` Loop?

This is a **very important doubt**.

Consider:

```java
for (int i = 1; i <= 5; i++) {

    if (i == 3) {
        continue;
    }

    System.out.println(i);
}
```

When `i == 3`, `continue` executes.

Does `i++` happen?

✅ **Yes.**

In a `for` loop, after `continue`, control proceeds to the **update expression**, then the condition is checked.

Conceptually:

```text
continue
   ↓
i++
   ↓
condition
```

This is why the loop doesn't get stuck at `3`.

---

# 23. `continue` in a `while` Loop — Be Careful!

Consider:

```java
int i = 1;

while (i <= 5) {

    if (i == 3) {
        continue;
    }

    i++;
}
```

⚠️ This can become an infinite loop because when `i == 3`, execution keeps returning to the condition without reaching `i++`.

So:

> Be especially careful with `continue` when the update is written inside the body of a `while` loop.

---

# 24. Can Loops Be Nested?

✅ Yes.

Example:

```java
for (int i = 1; i <= 3; i++) {

    for (int j = 1; j <= 2; j++) {
        System.out.println(i + " " + j);
    }
}
```

Output:

```text
1 1
1 2
2 1
2 2
3 1
3 2
```

The inner loop runs completely for each outer-loop iteration.

---

# 25. How Many Times Does a Nested Loop Run?

```java
for (int i = 1; i <= 3; i++) {

    for (int j = 1; j <= 4; j++) {
        System.out.println("*");
    }
}
```

Outer:

```text
3 times
```

Inner:

```text
4 times per outer iteration
```

Total:

```text
3 × 4 = 12
```

So the body executes **12 times**.

---

# 26. Can We Put Any Loop Inside Any Loop?

✅ Yes.

You can have:

```text
for inside for
for inside while
while inside for
do-while inside for
while inside do-while
```

and so on.

The important thing is understanding which loop controls which block.

---

# 27. Is `for(;;)` Valid Java?

✅ Yes.

```java
for (;;) {
    System.out.println("Hello");
}
```

This is an infinite `for` loop.

It is essentially a loop with no explicit initialization, condition, or update.

---

# 28. Is `while(true)` Valid Java?

✅ Yes.

```java
while (true) {
    System.out.println("Hello");
}
```

This is also an infinite loop.

---

# 29. Can an Infinite Loop Be Stopped With `break`?

Yes.

```java
while (true) {

    System.out.println("Hello");

    break;
}
```

Output:

```text
Hello
```

The loop terminates when `break` executes.

---

# 30. Biggest Syntax Doubt — Semicolon

### Correct `while`

```java
while (condition) {
    // body
}
```

### Correct `do-while`

```java
do {
    // body
} while (condition);
```

Notice:

```text
while → no semicolon after condition when using a block
do-while → semicolon required
```

---

# 31. What Happens With an Accidental Semicolon?

Example:

```java
int i = 1;

while (i <= 5);
{
    System.out.println(i);
    i++;
}
```

The semicolon:

```java
while (i <= 5);
```

is treated as the loop's body.

The block afterward is **not** the body of the `while`.

This can produce unexpected behavior and, depending on the code, even an infinite loop.

---

# 32. Can `for` Have an Empty Body?

Yes.

```java
for (int i = 0; i < 10; i++);
```

The loop body is an empty statement.

Legal Java, but often an accidental semicolon.

---

# 33. Can We Use a Loop Without Braces?

Yes, for a single statement.

```java
for (int i = 1; i <= 5; i++)
    System.out.println(i);
```

This is valid.

But if there are multiple statements, braces are needed to group them:

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
    System.out.println("Java");
}
```

### Good habit:

> Use braces even when optional. It makes the code safer and easier to read.

---

# 34. Can `for` and `while` Do the Same Thing?

Often, yes.

### `for`

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

### `while`

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
    i++;
}
```

Same output.

So don't think:

> "`for` can do X but `while` cannot."

Often the difference is **how naturally the repetition is expressed**.

---

# 35. Which Loop Is Entry-Controlled?

```text
for       → Entry-controlled
while     → Entry-controlled
do-while  → Exit-controlled
```

Meaning:

### Entry-controlled

```text
Condition → Body
```

### Exit-controlled

```text
Body → Condition
```

---

# 36. Which Loops Can Execute Zero Times?

### `for`

✅ Yes.

### `while`

✅ Yes.

### `do-while`

❌ No.

### Enhanced `for`

If the array/collection has no elements, the body executes zero times.

---

# 37. Which Loop Executes At Least Once?

Only among these four:

```text
do-while
```

because:

```text
BODY → CONDITION
```

---

# 38. Is Enhanced `for` Only for Arrays?

❌ No.

It can be used with arrays and objects that support the required iteration mechanism, including Java collections.

Example:

```java
for (String name : names) {
    System.out.println(name);
}
```

---

# 39. Can Enhanced `for` Modify Primitive Array Elements?

Consider:

```java
int[] a = {10, 20, 30};

for (int x : a) {
    x = x + 10;
}
```

The array does **not** become:

```text
20 30 40
```

Why?

`x` is a local loop variable receiving the element value.

To modify the primitive array elements:

```java
for (int i = 0; i < a.length; i++) {
    a[i] = a[i] + 10;
}
```

---

# 40. What Is the Difference Between `i` and `a[i]`?

Suppose:

```java
int[] a = {10, 20, 30};
```

In:

```java
for (int i = 0; i < a.length; i++) {
    System.out.println(a[i]);
}
```

```text
i     → index
a[i]  → element at that index
```

So:

```text
i = 0 → a[i] = 10
i = 1 → a[i] = 20
i = 2 → a[i] = 30
```

But in:

```java
for (int x : a)
```

```text
x → element
```

---

# 41. Can We Use `break` in Enhanced `for`?

✅ Yes.

```java
for (int x : numbers) {

    if (x == 30) {
        break;
    }

    System.out.println(x);
}
```

---

# 42. Can We Use `continue` in Enhanced `for`?

✅ Yes.

```java
for (int x : numbers) {

    if (x == 30) {
        continue;
    }

    System.out.println(x);
}
```

It skips that element's current iteration.

---

# 43. Does `break` Stop Only One Loop?

Normally, `break` terminates the **nearest enclosing loop**.

Example:

```java
for (...) {

    for (...) {

        break;
    }
}
```

The `break` stops the **inner loop**, not automatically the outer loop.

---

# 44. How Do We Break an Outer Loop?

Java provides labeled statements.

```java
outer:
for (int i = 1; i <= 3; i++) {

    for (int j = 1; j <= 3; j++) {

        if (i == 2 && j == 2) {
            break outer;
        }
    }
}
```

`break outer;` terminates the labeled outer loop.

---

# 45. Is a Loop a Method?

❌ No.

A loop is a **control-flow construct**.

For example:

```java
for (...)
```

is not a method.

Methods are things like:

```java
main()
println()
```

A method can **contain a loop**, though.

---

# 46. Is `for-each` a Method?

❌ No.

Enhanced `for` is a **language construct**.

```java
for (int x : numbers)
```

It is not a method named `forEach`.

There is also a separate Java API method named `forEach`, but don't confuse the two.

---

# 47. Does Every Loop Need a Condition?

Traditional loops have a condition or equivalent continuation mechanism.

But syntactically, a `for` loop can omit its condition:

```java
for (;;) {
}
```

This is an infinite loop.

---

# 48. Can a Loop Have Multiple Variables?

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

# 49. Can the Update Be More Than `i++`?

Absolutely.

```java
for (int i = 0; i <= 10; i += 2) {
    System.out.println(i);
}
```

Output:

```text
0
2
4
6
8
10
```

The update determines how the loop progresses.

---

# 50. Final Doubt Killer Table 🏆

| Doubt                                     | Correct Answer                 |
| ----------------------------------------- | ------------------------------ |
| What is a loop?                           | Repeated execution of a block  |
| Number of loops in your syllabus?         | 4                              |
| `for`                                     | Counter/control-oriented       |
| `while`                                   | Condition checked before body  |
| `do-while`                                | Body executed before condition |
| Enhanced `for`                            | Traverses elements             |
| Can `for` execute 0 times?                | ✅                              |
| Can `while` execute 0 times?              | ✅                              |
| Can `do-while` execute 0 times?           | ❌                              |
| `do-while` minimum executions?            | 1                              |
| `break`                                   | Terminates nearest loop        |
| `continue`                                | Skips current iteration        |
| Enhanced `for` gives index?               | ❌                              |
| `x` in `for (int x : a)`                  | Element                        |
| `i` in `a[i]`                             | Index                          |
| Infinite loop possible?                   | ✅                              |
| Nested loops possible?                    | ✅                              |
| `for(;;)` valid?                          | ✅                              |
| `while(true)` valid?                      | ✅                              |
| `do-while` needs final `;`?               | ✅                              |
| `for` and `while` can perform same tasks? | Often yes                      |

---

# 🎯 THE 10-SECOND REVISION

When you see a loop, think:

```text
FOR
↓
"How many / counter?"
```

```text
WHILE
↓
"Is the condition true?"
↓
Then execute
```

```text
DO-WHILE
↓
"Execute once first"
↓
Then check
```

```text
ENHANCED FOR
↓
"Give me every element"
```

And finally:

```text
break     = STOP 🛑
continue  = SKIP ⏭️
```

### 🔥 One ultimate rule

> **`for` and `while` ask permission before entering the loop body; `do-while` enters first and asks permission afterward. Enhanced `for` is the convenient way to process each element of an array or iterable collection.**
