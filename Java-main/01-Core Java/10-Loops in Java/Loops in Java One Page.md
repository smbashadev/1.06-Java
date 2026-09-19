# Loops in Java — ONE PAGE 🔄

A **loop** is used to execute a block of statements repeatedly as long as a specified condition is satisfied.

### Java has 4 commonly used loops:

```text
1. for loop
2. while loop
3. do-while loop
4. enhanced for loop (for-each)
```

---

## 1. `for` Loop

Used when the number of iterations is **known or can be controlled with a counter**.

### Syntax

```java
for (initialization; condition; update) {
    // statements
}
```

### Example

```java
class Demo {
    public static void main(String[] args) {

        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
        }
    }
}
```

### Output

```text
1
2
3
4
5
```

### Flow

```text
Initialization
      ↓
   Condition
   ↙       ↘
true       false
 ↓           ↓
Body        Exit
 ↓
Update
 ↓
Condition
```

### Remember

> **`for` = initialization + condition + update in one place.**

---

# 2. `while` Loop

Used when the number of iterations is **not necessarily known in advance** and the condition should be checked **before** executing the body.

### Syntax

```java
while (condition) {
    // statements
}
```

### Example

```java
class Demo {
    public static void main(String[] args) {

        int i = 1;

        while (i <= 5) {
            System.out.println(i);
            i++;
        }
    }
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

### Flow

```text
Initialization
      ↓
   Condition
   ↙       ↘
true       false
 ↓           ↓
Body        Exit
 ↓
Update
 ↓
Condition
```

### Remember

> **`while` = check first, execute later.**

---

# 3. `do-while` Loop

A `do-while` loop executes the body **at least once**, because the condition is checked **after** the body.

### Syntax

```java
do {
    // statements
} while (condition);
```

⚠️ Notice the **semicolon** after `while(condition);`

### Example

```java
class Demo {
    public static void main(String[] args) {

        int i = 1;

        do {
            System.out.println(i);
            i++;
        } while (i <= 5);
    }
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

### Important Example

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
do body first
     ↓
check condition
     ↓
false
     ↓
exit
```

### Remember

> **`do-while` = execute first, check later.**

---

# 4. Enhanced `for` Loop

Also called:

* **for-each loop**
* **enhanced for loop**

It is mainly used to traverse **arrays and collections**.

### Syntax

```java
for (dataType variable : arrayOrCollection) {
    // statements
}
```

### Example with Array

```java
class Demo {
    public static void main(String[] args) {

        int[] numbers = {10, 20, 30, 40};

        for (int n : numbers) {
            System.out.println(n);
        }
    }
}
```

Output:

```text
10
20
30
40
```

### How it works

```text
numbers
   ↓
10 → n
20 → n
30 → n
40 → n
```

### Remember

> **Enhanced `for` = easily visit each element.**

---

# 🔥 4 Loops — Side-by-Side

| Loop           | Condition Checked            | Best Used For                     | Executes At Least Once? |
| -------------- | ---------------------------- | --------------------------------- | ----------------------- |
| `for`          | Before body                  | Known/count-controlled iterations | ❌                       |
| `while`        | Before body                  | Condition-controlled repetition   | ❌                       |
| `do-while`     | After body                   | Body must execute once            | ✅                       |
| Enhanced `for` | Internally handles traversal | Arrays/collections                | Depends on elements     |

---

# 🧠 The Biggest Difference

### `for`

```java
for (int i = 1; i <= 5; i++)
```

Everything is together:

```text
initialization → condition → update
```

### `while`

```java
int i = 1;

while (i <= 5) {
    ...
    i++;
}
```

Initialization and update are written separately.

### `do-while`

```java
do {
    ...
} while (condition);
```

Body executes **before** condition checking.

### Enhanced `for`

```java
for (int x : array)
```

You don't manually manage an index.

---

# 🎯 Easy Memory Trick

```text
FOR       → Count / controlled repetition
WHILE     → Check → Execute
DO-WHILE  → Execute → Check
ENHANCED  → Element by element
```

### One-line summary

> **`for` and `while` check the condition before execution, `do-while` checks after execution, and enhanced `for` is designed for convenient traversal of arrays and collections.**
