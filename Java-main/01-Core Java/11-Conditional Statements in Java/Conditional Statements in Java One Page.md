# Conditional Statements in Java — ONE PAGE 🎯

A **conditional statement** allows Java to make a decision based on whether a condition is `true` or `false`.

### Basic idea

```text
Condition
   ↓
 true / false
   ↓
Choose which statement to execute
```

Java's main conditional statements are:

```text
1. if
2. if-else
3. else-if ladder
4. nested if
5. switch
```

---

# 1. `if` Statement

Used when you want to execute code **only when a condition is true**.

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

If the condition is false, the body is skipped.

---

# 2. `if-else` Statement

Used when there are **two possible paths**.

### Syntax

```java
if (condition) {
    // true block
} else {
    // false block
}
```

### Example

```java
int number = 7;

if (number % 2 == 0) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
```

Output:

```text
Odd
```

### Remember

```text
true  → if block
false → else block
```

Only **one** of the two blocks executes.

---

# 3. `else-if` Ladder

Used when there are **multiple conditions**.

### Syntax

```java
if (condition1) {
    // block 1
}
else if (condition2) {
    // block 2
}
else if (condition3) {
    // block 3
}
else {
    // default block
}
```

### Example

```java
int marks = 75;

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

### Important

Java checks from **top to bottom**.

Once a condition is true, its block executes and the remaining `else-if` conditions are skipped.

---

# 4. Nested `if`

An `if` statement inside another `if` statement is called a **nested if**.

### Example

```java
int age = 20;
boolean citizen = true;

if (age >= 18) {

    if (citizen) {
        System.out.println("Eligible");
    }
}
```

Think:

```text
Outer condition
      ↓
    true
      ↓
Inner condition
      ↓
    true
      ↓
   Execute
```

---

# 5. `switch`

Used when one expression needs to be compared against multiple possible values.

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

### Example

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
        System.out.println("Invalid day");
}
```

Output:

```text
Tuesday
```

---

# ⚠️ Why `break` in `switch`?

Without `break`, execution can continue into the following cases.

Example:

```java
int x = 1;

switch (x) {

    case 1:
        System.out.println("One");

    case 2:
        System.out.println("Two");
}
```

Output:

```text
One
Two
```

With:

```java
break;
```

after `case 1`, execution leaves the switch.

---

# 🔥 Conditional Statements Comparison

| Statement        | Best Used For                      |
| ---------------- | ---------------------------------- |
| `if`             | One condition                      |
| `if-else`        | Two alternatives                   |
| `else-if` ladder | Multiple conditions/ranges         |
| Nested `if`      | Condition inside another condition |
| `switch`         | Multiple fixed choices             |

---

# 🧠 Quick Decision

```text
             Need a decision?
                    ↓
          ┌─────────┴─────────┐
          ↓                   ↓
     Conditions            Fixed choices
     /ranges               /known values
          ↓                   ↓
    if / else-if           switch
```

### Easy memory

> **`if` → "Is this condition true?"**

> **`if-else` → "Which of two paths?"**

> **`else-if` → "Which condition is true?"**

> **nested `if` → "If this is true, check another condition."**

> **`switch` → "Which fixed case matches?"**
