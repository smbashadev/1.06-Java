# Conditional Statements in Java — TEACH ME 👨‍🏫

Let's learn this from **zero → understanding → writing programs**.

The main goal is not to memorize syntax. You should be able to look at a problem and decide **which conditional statement to use**.

---

# 1. First Understand: What Is a Decision?

Imagine your Java program is asking:

> "Is the student eligible?"

The program needs to check a condition:

```java
age >= 18
```

If the answer is `true`:

```text
Eligible
```

If the answer is `false`:

```text
Not Eligible
```

That's **conditional programming**.

### Think like this:

```text
             CONDITION
                 ↓
        ┌────────┴────────┐
        ↓                 ↓
      TRUE              FALSE
        ↓                 ↓
    Do this           Do that
```

---

# 2. What Does a Condition Produce?

A Java condition produces a **boolean value**:

```text
true
```

or

```text
false
```

For example:

```java
10 > 5
```

produces:

```text
true
```

And:

```java
10 < 5
```

produces:

```text
false
```

---

# 3. Your First `if`

Let's start with the simplest conditional statement.

```java
int age = 20;

if (age >= 18) {
    System.out.println("Adult");
}
```

Read this as English:

> **IF age is greater than or equal to 18, print "Adult".**

Since:

```text
20 >= 18
```

is true, output is:

```text
Adult
```

---

# 4. What If the Condition Is False?

Change:

```java
int age = 15;
```

Now:

```java
if (age >= 18) {
    System.out.println("Adult");
}
```

Java checks:

```text
15 >= 18
     ↓
   false
```

So Java simply **skips the block**.

```text
No output
```

### Remember:

> `if` has no alternative instruction.

If false → do nothing and continue.

---

# 5. Now We Need `else`

Suppose you don't want Java to do nothing.

You want:

```text
18 or above → Adult
below 18    → Minor
```

Use `if-else`.

```java
int age = 15;

if (age >= 18) {
    System.out.println("Adult");
}
else {
    System.out.println("Minor");
}
```

Output:

```text
Minor
```

---

# 6. Understand `if-else` Like a Door 🚪

Imagine one door with two paths:

```text
                 age >= 18?
                 /       \
               YES       NO
                ↓         ↓
             Adult      Minor
```

Only **one path** can be taken.

That's `if-else`.

---

# 7. Now a Real Problem: Grades

Suppose:

```text
90+ → A
75+ → B
60+ → C
below 60 → Fail
```

Can we use just `if-else`?

Not conveniently, because there are more than two possibilities.

We use an **else-if ladder**.

```java
int marks = 82;

if (marks >= 90) {
    System.out.println("A");
}
else if (marks >= 75) {
    System.out.println("B");
}
else if (marks >= 60) {
    System.out.println("C");
}
else {
    System.out.println("Fail");
}
```

Output:

```text
B
```

---

# 8. How Does `else-if` Work?

This is extremely important.

Java checks **from top to bottom**.

For:

```java
marks = 82
```

Java asks:

```text
82 >= 90?
```

❌ No.

Then:

```text
82 >= 75?
```

✅ Yes.

So Java prints:

```text
B
```

Then it stops checking the remaining branches.

---

# 9. The Golden Rule of `else-if`

> **The first true condition wins.**

Suppose:

```java
int x = 100;

if (x >= 50) {
    System.out.println("A");
}
else if (x >= 80) {
    System.out.println("B");
}
```

What prints?

```text
A
```

Why?

Because:

```text
100 >= 50
```

is already true.

Java never reaches:

```java
else if (x >= 80)
```

### Therefore:

**Order matters.**

---

# 10. Separate `if` vs `else-if`

This causes huge confusion.

### Separate `if`s:

```java
if (x >= 50) {
    System.out.println("A");
}

if (x >= 80) {
    System.out.println("B");
}
```

If:

```text
x = 100
```

Output:

```text
A
B
```

Both conditions are checked independently.

---

### `else-if`:

```java
if (x >= 50) {
    System.out.println("A");
}
else if (x >= 80) {
    System.out.println("B");
}
```

Output:

```text
A
```

Only the first matching branch executes.

### Remember:

```text
if + if + if
    ↓
Independent decisions

if + else-if + else
    ↓
One decision chain
```

---

# 11. What Is Nested `if`?

Now imagine:

> First check whether the person is 18+.
> If yes, check whether they are a citizen.

That's a decision inside another decision.

```java
int age = 20;
boolean citizen = true;

if (age >= 18) {

    if (citizen) {
        System.out.println("Eligible");
    }
}
```

This is called **nested `if`**.

---

# 12. Visualize Nested `if`

```text
             age >= 18?
                  ↓
                YES
                  ↓
             citizen?
             /      \
           YES       NO
            ↓         ↓
        Eligible     ...
```

So:

> **Nested = one conditional statement inside another conditional statement.**

---

# 13. Can We Simplify Nested `if`?

Sometimes yes.

Instead of:

```java
if (age >= 18) {
    if (citizen) {
        System.out.println("Eligible");
    }
}
```

we can write:

```java
if (age >= 18 && citizen) {
    System.out.println("Eligible");
}
```

Here `&&` means **AND**.

Both conditions must be true.

---

# 14. Learn `&&`

Suppose:

```java
boolean student = true;
boolean paidFees = true;
```

Then:

```java
if (student && paidFees) {
    System.out.println("Allowed");
}
```

Read it as:

> If the person is a student **AND** has paid the fees.

Both must be true.

```text
true  && true  → true
true  && false → false
false && true  → false
false && false → false
```

### Memory:

> `&&` = **AND = both required**

---

# 15. Learn `||`

Suppose:

```text
Saturday OR Sunday
```

means either one is enough.

```java
if (day == 6 || day == 7) {
    System.out.println("Weekend");
}
```

`||` means **OR**.

```text
true  || true  → true
true  || false → true
false || true  → true
false || false → false
```

### Memory:

> `||` = **OR = at least one**

---

# 16. Learn `!`

`!` means **NOT**.

Suppose:

```java
boolean raining = false;
```

Then:

```java
!raining
```

means:

```text
NOT false
   ↓
true
```

Example:

```java
if (!raining) {
    System.out.println("Go outside");
}
```

### Memory:

```text
!true  → false
!false → true
```

---

# 17. Now Let's Learn `switch`

Suppose you have:

```text
1 → Monday
2 → Tuesday
3 → Wednesday
4 → Thursday
```

You could write:

```java
if (day == 1) {
    ...
}
else if (day == 2) {
    ...
}
else if (day == 3) {
    ...
}
```

But this is a good situation for `switch`.

```java
int day = 2;

switch (day) {

    case 1:
        System.out.println("Monday");
        break;

    case 2:
        System.out.println("Tuesday");
        break;

    case 3:
        System.out.println("Wednesday");
        break;

    default:
        System.out.println("Invalid");
}
```

Output:

```text
Tuesday
```

---

# 18. Understand `switch` Like a Selection Board

Think:

```text
day = 2

        switch
           ↓
      ┌────┼────┐
      ↓    ↓    ↓
     1     2    3
     ↓     ↓    ↓
  Monday Tuesday Wednesday
           ↑
         MATCH
```

Java finds:

```text
case 2
```

and executes it.

---

# 19. What Is `break`?

This is extremely important in `switch`.

```java
case 2:
    System.out.println("Tuesday");
    break;
```

`break` tells Java:

> "I found my case. Leave the switch now."

Without `break`, Java can continue into the next cases.

---

# 20. What Is Fall-Through?

Consider:

```java
int x = 1;

switch (x) {

    case 1:
        System.out.println("One");

    case 2:
        System.out.println("Two");

    case 3:
        System.out.println("Three");
}
```

Output:

```text
One
Two
Three
```

Why?

Because there is no `break`.

This is called:

> **Fall-through**

Java entered `case 1` and continued executing subsequent statements.

---

# 21. Can Fall-Through Be Useful?

Yes.

Suppose:

```text
Saturday → Weekend
Sunday   → Weekend
```

You can intentionally group them:

```java
int day = 6;

switch (day) {

    case 6:
    case 7:
        System.out.println("Weekend");
        break;

    default:
        System.out.println("Weekday");
}
```

Output:

```text
Weekend
```

Here `case 6` deliberately falls through to the shared code.

---

# 22. What Is `default`?

`default` means:

> "None of the cases matched."

Example:

```java
int day = 10;

switch (day) {

    case 1:
        System.out.println("Monday");
        break;

    case 2:
        System.out.println("Tuesday");
        break;

    default:
        System.out.println("Invalid day");
}
```

Output:

```text
Invalid day
```

`default` is similar to the `else` concept.

---

# 23. `else` vs `default`

Conceptually:

```text
if-else
```

has:

```text
if       → matching condition
else     → nothing matched
```

`switch` has:

```text
case     → matching value
default  → nothing matched
```

They're not technically identical, but this comparison is useful for understanding.

---

# 24. When Should I Use `if`?

Use `if` when you have **one condition**.

Example:

```java
if (temperature > 40) {
    System.out.println("Very hot");
}
```

---

# 25. When Should I Use `if-else`?

Use it when there are **two alternatives**.

Example:

```java
if (number % 2 == 0) {
    System.out.println("Even");
}
else {
    System.out.println("Odd");
}
```

Think:

```text
YES / NO
TRUE / FALSE
```

---

# 26. When Should I Use `else-if`?

Use it when you have **multiple conditions or ranges**.

Example:

```java
if (marks >= 90) {
    System.out.println("A");
}
else if (marks >= 75) {
    System.out.println("B");
}
else if (marks >= 60) {
    System.out.println("C");
}
else {
    System.out.println("Fail");
}
```

---

# 27. When Should I Use Nested `if`?

Use nested `if` when:

> One decision depends on another decision.

Example:

```text
Is account active?
       ↓ YES
Is password correct?
       ↓ YES
Login successful
```

---

# 28. When Should I Use `switch`?

Use `switch` when you have **one value and several fixed choices**.

Examples:

```text
menu choice
day number
month number
command
grade character
option selection
```

Example:

```java
switch (choice) {
    case 1:
        ...
        break;
    case 2:
        ...
        break;
}
```

---

# 29. `if-else` or `switch`?

Use this simple rule:

```text
Is it a range/complex condition?
        ↓
       YES
        ↓
      if-else
```

Example:

```java
if (marks >= 75)
```

But:

```text
Is it one value matching fixed choices?
        ↓
       YES
        ↓
      switch
```

Example:

```java
switch (day)
```

---

# 30. One Complete Example

Let's build a small student grading program.

```java
class Student {

    public static void main(String[] args) {

        int marks = 85;

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks");
        }
        else if (marks >= 90) {
            System.out.println("Grade A");
        }
        else if (marks >= 75) {
            System.out.println("Grade B");
        }
        else if (marks >= 60) {
            System.out.println("Grade C");
        }
        else {
            System.out.println("Fail");
        }
    }
}
```

Trace it:

```text
marks = 85

85 < 0 || 85 > 100
        ↓
       false

85 >= 90
   ↓
 false

85 >= 75
   ↓
 true

Grade B
```

Output:

```text
Grade B
```

---

# 🧪 31. Let's Test Your Understanding

### Question 1

```java
int x = 10;

if (x > 5) {
    System.out.println("A");
}
```

What is printed?

<details>
<summary>Answer</summary>

```text
A
```

Because:

```text
10 > 5 → true
```

</details>

---

### Question 2

```java
int x = 10;

if (x > 20) {
    System.out.println("A");
}
else {
    System.out.println("B");
}
```

Answer:

```text
B
```

Because:

```text
10 > 20 → false
```

---

### Question 3

```java
int x = 80;

if (x >= 50) {
    System.out.println("A");
}
else if (x >= 75) {
    System.out.println("B");
}
```

Answer:

```text
A
```

Why?

Because the first condition is already true.

---

### Question 4

```java
int x = 80;

if (x >= 50) {
    System.out.println("A");
}

if (x >= 75) {
    System.out.println("B");
}
```

Answer:

```text
A
B
```

Because these are independent `if` statements.

---

# 🧠 32. The Biggest Mental Model

Don't memorize five unrelated syntax structures.

Think of them as **different ways of asking questions**.

### `if`

> "Is this true?"

```java
if (condition)
```

### `if-else`

> "Is this true? If not, do the other thing."

```java
if (condition)
else
```

### `else-if`

> "Which condition is true?"

```java
if (...)
else if (...)
else if (...)
else
```

### Nested `if`

> "If this is true, ask another question."

```java
if (...) {
    if (...) {
    }
}
```

### `switch`

> "Which fixed value did I receive?"

```java
switch (value)
```

---

# 🎯 33. Final Cheat Sheet

```text
CONDITIONAL STATEMENTS
        │
        ├── if
        │    └── one condition
        │
        ├── if-else
        │    └── two paths
        │
        ├── else-if ladder
        │    └── multiple conditions
        │
        ├── nested if
        │    └── decision inside decision
        │
        └── switch
             └── fixed choices
```

And remember:

```text
&&  → AND  → both
||  → OR   → at least one
!   → NOT  → reverse
```

```text
if false
   ↓
skip

if-else
   ↓
one of two

else-if
   ↓
first true wins

switch
   ↓
matching case

break
   ↓
leave switch
```

## 🏆 One sentence to remember everything

> **Use `if` for one decision, `if-else` for two paths, `else-if` for multiple conditions, nested `if` for dependent decisions, and `switch` for multiple fixed choices.**
