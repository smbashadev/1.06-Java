# Conditional Statements in Java — DEEP DIVE 🔥

A **conditional statement** allows a Java program to make a decision based on a condition.

The fundamental idea is:

```text
              Condition
                  ↓
          ┌───────┴───────┐
          ↓               ↓
        true            false
          ↓               ↓
     execute path    alternative path
```

Java's main conditional constructs are:

```text
1. if
2. if-else
3. else-if ladder
4. nested if
5. switch
```

We'll also understand **conditions, boolean expressions, braces, `break`, fall-through, nested decisions, and when to use each one**.

---

# 1. What Is a Condition?

A condition is an expression whose result is either:

```text
true
```

or

```text
false
```

Example:

```java
int age = 20;

age >= 18
```

The result is:

```text
20 >= 18
     ↓
    true
```

Therefore:

```java
if (age >= 18) {
    System.out.println("Adult");
}
```

Output:

```text
Adult
```

---

# 2. Conditions Usually Use Relational Operators

Java provides comparison operators:

| Operator | Meaning               | Example  |
| -------- | --------------------- | -------- |
| `>`      | greater than          | `a > b`  |
| `<`      | less than             | `a < b`  |
| `>=`     | greater than or equal | `a >= b` |
| `<=`     | less than or equal    | `a <= b` |
| `==`     | equal to              | `a == b` |
| `!=`     | not equal to          | `a != b` |

Example:

```java
int a = 10;
int b = 20;

System.out.println(a < b);
```

Output:

```text
true
```

---

# 3. `==` vs `=`

This is one of the most important beginner doubts.

### `=`

Assignment:

```java
int x = 10;
```

Means:

> Put `10` into `x`.

### `==`

Comparison:

```java
x == 10
```

Means:

> Is `x` equal to `10`?

So:

```java
if (x == 10) {
    System.out.println("Yes");
}
```

Correct.

---

# 🟢 4. `if` Statement

## Definition

The `if` statement executes a block of code **only when its condition is true**.

### Syntax

```java
if (condition) {
    // statements
}
```

### Example

```java
int age = 20;

if (age >= 18) {
    System.out.println("Eligible");
}
```

Output:

```text
Eligible
```

---

# 5. What If the Condition Is False?

```java
int age = 15;

if (age >= 18) {
    System.out.println("Eligible");
}
```

The condition:

```text
15 >= 18
```

is:

```text
false
```

Therefore the body is skipped.

Output:

```text
(no output)
```

This is the defining characteristic of `if`.

---

# 6. Flow of `if`

```text
       Start
         ↓
     Condition
     ↙       ↘
  true       false
   ↓           ↓
 if body      Skip
   ↓           ↓
   └──────→ Continue
```

---

# 🟡 7. `if-else`

Sometimes you don't want to do nothing when the condition is false.

You want an alternative.

That's where `else` comes in.

### Syntax

```java
if (condition) {
    // true block
}
else {
    // false block
}
```

### Example

```java
int number = 10;

if (number % 2 == 0) {
    System.out.println("Even");
}
else {
    System.out.println("Odd");
}
```

Output:

```text
Even
```

---

# 8. How `if-else` Works

```text
             Condition
             ↙       ↘
          true       false
           ↓           ↓
       if block    else block
           ↓           ↓
           └─────┬─────┘
                 ↓
               Exit
```

### Important:

Exactly **one** of the two branches executes.

```text
if block     → executes if true
else block   → executes if false
```

---

# 9. Can `else` Exist Without `if`?

❌ No.

This is invalid:

```java
else {
    System.out.println("Hello");
}
```

`else` must be associated with an `if`.

---

# 10. Can `if` Exist Without `else`?

✅ Yes.

```java
if (age >= 18) {
    System.out.println("Adult");
}
```

`else` is optional.

---

# 🟠 11. `else-if` Ladder

Suppose you have several possible conditions.

Example:

```text
90+ → A
75+ → B
60+ → C
below 60 → Fail
```

You can use an `else-if` ladder.

### Example

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

# 12. How Does an `else-if` Ladder Execute?

Java checks from **top to bottom**.

For:

```java
int marks = 82;
```

Java asks:

```text
marks >= 90 ?
```

No.

Then:

```text
marks >= 75 ?
```

Yes.

So:

```text
Execute B
     ↓
STOP CHECKING REMAINING CONDITIONS
```

It does **not** continue checking the `60` condition after finding the matching branch.

---

# 13. Very Important: Order Matters

Look at this:

```java
int marks = 95;

if (marks >= 60) {
    System.out.println("C");
}
else if (marks >= 90) {
    System.out.println("A");
}
```

Output:

```text
C
```

Why?

Because:

```text
95 >= 60
```

is already true.

Java enters the first branch and never reaches the `else-if`.

Therefore:

> In an `else-if` ladder, **the order of conditions matters**.

Usually, when checking ranges like marks, put the most restrictive/highest threshold first.

---

# 14. `if` Statements Are Independent

This is different:

```java
if (marks >= 60) {
    System.out.println("C");
}

if (marks >= 90) {
    System.out.println("A");
}
```

For:

```text
marks = 95
```

both conditions are true.

Output:

```text
C
A
```

Why?

Because these are **two separate `if` statements**.

Compare:

```text
if
else-if
else
```

with:

```text
if
if
```

They are not the same.

---

# 15. Biggest Doubt: Multiple `if` vs `else-if`

### Multiple `if`

```java
if (condition1) {
}
if (condition2) {
}
if (condition3) {
}
```

Potentially **multiple blocks can execute**.

### `else-if` ladder

```java
if (condition1) {
}
else if (condition2) {
}
else if (condition3) {
}
```

Only the **first matching branch** executes.

### Memory:

> **Separate `if`s = independent decisions.**

> **`else-if` = one connected decision chain.**

---

# 🔵 16. Nested `if`

A conditional statement inside another conditional statement is called a **nested if**.

Example:

```java
int age = 25;
boolean citizen = true;

if (age >= 18) {

    if (citizen) {
        System.out.println("Eligible");
    }
}
```

Execution:

```text
age >= 18?
     ↓
   true
     ↓
citizen?
     ↓
   true
     ↓
Eligible
```

---

# 17. Why Use Nested `if`?

Suppose the second condition only makes sense if the first condition is true.

For example:

```text
First:
Is the person an adult?

Then:
Is the person a citizen?

Only if both are relevant do we check the second condition.
```

That's a natural nested decision.

---

# 18. Nested `if` vs Logical Operators

This:

```java
if (age >= 18) {
    if (citizen) {
        System.out.println("Eligible");
    }
}
```

can often be expressed as:

```java
if (age >= 18 && citizen) {
    System.out.println("Eligible");
}
```

Both can represent the same logical requirement in simple cases.

The second is often more concise.

---

# 🟣 19. Logical Operators in Conditions

Java provides:

```text
&&  AND
||  OR
!   NOT
```

---

## `&&` — AND

Both conditions must be true.

```java
if (age >= 18 && citizen) {
    System.out.println("Eligible");
}
```

Truth table:

| A     | B     | A && B   |
| ----- | ----- | -------- |
| false | false | false    |
| false | true  | false    |
| true  | false | false    |
| true  | true  | **true** |

Memory:

> **AND = everyone must agree.**

---

# 20. `||` — OR

At least one condition must be true.

```java
if (day == 6 || day == 7) {
    System.out.println("Weekend");
}
```

Truth table:

| A     | B     | A || B   |
| ----- | ----- | -------- |
| false | false | false    |
| false | true  | **true** |
| true  | false | **true** |
| true  | true  | **true** |

Memory:

> **OR = at least one is enough.**

---

# 21. `!` — NOT

Reverses a boolean result.

```java
boolean raining = false;

if (!raining) {
    System.out.println("Go outside");
}
```

Since:

```text
raining = false
```

then:

```text
!raining = true
```

---

# 22. Short-Circuit Evaluation

This is a deeper but important concept.

With:

```java
A && B
```

if `A` is already false, Java doesn't need to evaluate `B` to know the whole expression is false.

Similarly:

```java
A || B
```

if `A` is already true, Java doesn't need to evaluate `B`.

Example:

```java
if (x != 0 && 10 / x > 2) {
    System.out.println("Valid");
}
```

If `x == 0`:

```text
x != 0 → false
```

Java stops there, so:

```text
10 / x
```

is not evaluated.

This prevents division by zero in this example.

---

# 🟤 23. `switch`

Now suppose you have one expression and several **fixed possible values**.

For example:

```text
1 → Monday
2 → Tuesday
3 → Wednesday
```

A `switch` can make this cleaner.

### Syntax

```java
switch (expression) {

    case value1:
        // statements
        break;

    case value2:
        // statements
        break;

    default:
        // statements
}
```

---

# 24. `switch` Example

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

# 25. How Does `switch` Work?

Given:

```java
int day = 2;
```

Java evaluates:

```text
switch expression
      ↓
     2
      ↓
compare with cases
      ↓
case 1? No
      ↓
case 2? YES
      ↓
execute case 2
      ↓
break
      ↓
exit switch
```

---

# 26. What Is `case`?

A `case` represents one possible matching value.

```java
case 1:
```

means:

> If the switch expression matches `1`, execute this section.

---

# 27. What Is `default`?

`default` is the fallback branch.

Example:

```java
int day = 8;

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

Since no case matches:

```text
default
```

executes.

### Important:

`default` is optional.

---

# 28. Why Is `break` Important in `switch`?

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

Java enters `case 1` and then continues executing subsequent statements.

This behavior is called:

> **fall-through**

---

# 29. `switch` With `break`

```java
switch (x) {

    case 1:
        System.out.println("One");
        break;

    case 2:
        System.out.println("Two");
        break;
}
```

Now:

```text
case 1
 ↓
print One
 ↓
break
 ↓
exit switch
```

---

# 30. Is `break` Required in Every `case`?

❌ Not necessarily.

There are valid situations where intentional fall-through is useful.

But as a beginner, remember:

> If you don't want execution to continue into the next case, use `break`.

---

# 31. Multiple Cases With One Action

You can intentionally group cases.

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

For `6`:

```text
case 6
 ↓
case 7
 ↓
Weekend
 ↓
break
```

This is intentional fall-through to share the same action.

---

# 32. `if-else` vs `switch`

This is a common exam question.

### `if-else`

Better when conditions involve:

```text
ranges
complex expressions
multiple variables
logical operators
```

Example:

```java
if (marks >= 90)
```

or:

```java
if (age >= 18 && citizen)
```

---

### `switch`

Useful when one expression is being matched against **specific alternatives**.

Example:

```java
switch (day) {
    case 1:
    case 2:
}
```

---

# 33. Can `switch` Check Ranges Like `90–100`?

A traditional `case` does not work like:

```java
case marks >= 90:
```

That is not the normal `switch` case syntax.

For ranges such as:

```text
90–100
75–89
60–74
```

an `if-else` ladder is generally the natural choice:

```java
if (marks >= 90) {
    ...
}
else if (marks >= 75) {
    ...
}
```

---

# 34. Can `switch` Use Strings?

Yes.

Example:

```java
String day = "Monday";

switch (day) {

    case "Monday":
        System.out.println("Start");
        break;

    case "Sunday":
        System.out.println("Holiday");
        break;

    default:
        System.out.println("Other");
}
```

---

# 35. Can `switch` Use `char`?

Yes.

```java
char grade = 'A';

switch (grade) {

    case 'A':
        System.out.println("Excellent");
        break;

    case 'B':
        System.out.println("Good");
        break;

    default:
        System.out.println("Other");
}
```

---

# 36. Can `switch` Use `boolean`?

Traditional `switch` does **not** use `boolean` as a switch selector.

For boolean conditions, use:

```java
if
```

For example:

```java
if (isLoggedIn) {
    System.out.println("Welcome");
}
```

---

# 37. Important: `if` Condition Must Be Boolean

In Java:

```java
if (10 > 5) {
    System.out.println("Yes");
}
```

is valid.

But:

```java
if (10) {
    System.out.println("Yes");
}
```

is **not valid Java**.

Unlike some languages, Java does not treat an integer like `1` as `true`.

The condition must evaluate to a boolean.

---

# 38. Example: Java vs C-Style Thinking

This is invalid Java:

```java
int x = 10;

if (x) {
}
```

Correct:

```java
if (x != 0) {
}
```

because:

```text
x != 0
```

produces a boolean.

---

# 39. Can We Use a Boolean Variable Directly?

Absolutely.

```java
boolean eligible = true;

if (eligible) {
    System.out.println("Eligible");
}
```

This is perfectly valid.

---

# 40. Nested `if-else` — The Dangling `else` Doubt

Consider:

```java
if (a > 0)
    if (b > 0)
        System.out.println("Both positive");
    else
        System.out.println("What?");
```

Which `if` does the `else` belong to?

By Java's rule:

> An `else` is associated with the **nearest unmatched `if`**.

So it belongs to:

```java
if (b > 0)
```

not the outer `if`.

---

# 41. Avoid Dangling `else` Confusion With Braces

Instead of:

```java
if (a > 0)
    if (b > 0)
        ...
    else
        ...
```

write:

```java
if (a > 0) {

    if (b > 0) {
        System.out.println("Both positive");
    }
    else {
        System.out.println("B is not positive");
    }
}
```

Braces make the structure obvious.

---

# 42. Can We Put an `if` Inside `switch`?

Yes.

```java
switch (day) {

    case 1:
        if (holiday) {
            System.out.println("Holiday");
        }
        break;
}
```

Likewise, a `switch` can appear inside an `if`.

Conditional constructs can be nested.

---

# 43. Conditional Statement vs Conditional Operator

Don't confuse:

```text
if
```

with the:

```text
?: 
```

operator.

Example:

```java
int max = (a > b) ? a : b;
```

This is the **ternary conditional operator**, not an `if` statement.

It is useful for compact expressions.

---

# 44. A Complete Decision Program

Let's combine several concepts.

```java
class Student {

    public static void main(String[] args) {

        int marks = 82;

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks");
        }
        else if (marks >= 90) {
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
    }
}
```

For:

```text
marks = 82
```

execution is:

```text
Invalid? → No
90+?     → No
75+?     → Yes
          ↓
          B
```

---

# 🧠 45. Master Decision Tree

When solving a conditional problem, ask:

```text
                 What decision?
                       ↓
             ┌─────────┴─────────┐
             ↓                   ↓
       One condition        Multiple choices
             ↓                   ↓
            if              What type?
                             ↙       ↘
                       Conditions   Fixed values
                           ↓            ↓
                       if/else       switch
                           │
                    Multiple ranges?
                           ↓
                      else-if
```

---

# 🔥 46. The Five Constructs — One Table

| Construct   | Meaning                  | Example Situation  |
| ----------- | ------------------------ | ------------------ |
| `if`        | Execute if true          | `age >= 18`        |
| `if-else`   | Choose one of two paths  | Even/odd           |
| `else-if`   | Choose among conditions  | Grade calculation  |
| Nested `if` | Decision inside decision | Eligibility checks |
| `switch`    | Match fixed alternatives | Menu/day/command   |

---

# 🚨 47. Top Doubts — Killed

### Doubt 1: Can `if` exist without `else`?

✅ Yes.

---

### Doubt 2: Can `else` exist without `if`?

❌ No.

---

### Doubt 3: Can multiple separate `if`s execute?

✅ Yes.

---

### Doubt 4: Can multiple branches of one `if-else-if` ladder execute?

❌ No. Only the first matching branch executes.

---

### Doubt 5: Does `else-if` check all conditions?

It checks sequentially until it finds the **first true condition**.

---

### Doubt 6: Is `else` compulsory in an `else-if` ladder?

❌ No.

```java
if (...)
else if (...)
```

is valid.

---

### Doubt 7: Is `default` compulsory in `switch`?

❌ No.

---

### Doubt 8: Is `break` compulsory in `switch`?

❌ No, but without it, execution can fall through into later cases.

---

### Doubt 9: Can `if` check a number directly?

❌ No.

```java
if (10) // invalid
```

Use a boolean expression:

```java
if (10 > 5)
```

---

### Doubt 10: Can `switch` check ranges naturally?

❌ Traditional `case` matching is for specific case values, not ordinary range conditions. Use `if-else` for ranges.

---

# 🏆 FINAL MEMORY MAP

```text
                 CONDITIONALS
                      │
       ┌──────────────┼──────────────┐
       ↓              ↓              ↓
      if           if-else        else-if
       │              │              │
   One decision    Two paths     Many conditions
                                      │
                                      ↓
                                  Nested if
                                      │
                              Decision inside
                                decision

                      +
                   switch
                      │
                Fixed choices
                      │
             case → break
```

## 🔑 The five golden rules

> **1. `if` → execute when condition is true.**

> **2. `if-else` → choose between two paths.**

> **3. `else-if` → check multiple conditions from top to bottom; first true branch wins.**

> **4. Nested `if` → put one decision inside another.**

> **5. `switch` → match one expression against fixed alternatives; `break` prevents unwanted fall-through.**
