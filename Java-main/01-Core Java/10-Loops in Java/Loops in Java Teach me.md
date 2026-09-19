# Loops in Java — TEACH ME 👨‍🏫

Let's learn loops from **zero → understanding → writing programs**.

The easiest way is to imagine a loop as a **repeat machine**.

---

# 🧠 1. What is a Loop?

Suppose I ask Java to print:

```text
Hello
Hello
Hello
Hello
Hello
```

Without a loop:

```java
System.out.println("Hello");
System.out.println("Hello");
System.out.println("Hello");
System.out.println("Hello");
System.out.println("Hello");
```

That's repetitive.

With a loop:

```java
for (int i = 1; i <= 5; i++) {
    System.out.println("Hello");
}
```

Much better.

### So:

> **Loop = repeat a block of code.**

---

# 🪜 2. The 4 Loops You Must Know

Java commonly uses these four loops:

```text
                 LOOPS
                   │
       ┌───────────┼───────────┐
       ↓           ↓           ↓
      for        while      do-while
                              
                   +
            enhanced for
```

We will learn them one by one.

---

# 🟢 3. First: `for` Loop

Imagine you want:

```text
1
2
3
4
5
```

You know exactly how many times you want to repeat.

Use:

```java
for
```

### Program

```java
class Demo {
    public static void main(String[] args) {

        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
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

---

# 🔍 4. Understand This One Line

```java
for (int i = 1; i <= 5; i++)
```

It has three important parts:

```text
for ( initialization ; condition ; update )
```

So:

```java
for (int i = 1; i <= 5; i++)
```

means:

```text
int i = 1       → Start from 1
i <= 5          → Continue while i is ≤ 5
i++             → Increase i by 1
```

---

# 🔄 5. How Does It Actually Run?

Java does this:

```text
int i = 1
   ↓
i <= 5 ? YES
   ↓
print i
   ↓
i++
   ↓
i = 2
   ↓
i <= 5 ? YES
   ↓
print i
   ↓
...
   ↓
i = 6
   ↓
6 <= 5 ? NO
   ↓
STOP
```

That's a loop.

---

# 🎯 6. Easy `for` Example

Print `"Java"` 3 times:

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

Notice:

> The loop variable doesn't have to be printed. It can simply control repetition.

---

# 🟡 7. Second: `while` Loop

Now imagine you **don't know exactly how many times** something will repeat.

You simply have a condition:

> "Keep going while this condition is true."

That's where `while` comes in.

### Example

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
    i++;
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

---

# 🔍 8. How `while` Works

Look at:

```java
while (i <= 5)
```

Java asks:

```text
Is i <= 5?
```

If:

```text
YES → execute body
NO  → stop
```

Then it checks again.

---

# ⚠️ 9. The Most Important `while` Rule

You usually need to change the variable inside the loop.

Bad:

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
}
```

What happens?

```text
i = 1
 ↓
1 <= 5 → true
 ↓
print 1
 ↓
i is STILL 1
 ↓
1 <= 5 → true
 ↓
print 1
 ↓
forever...
```

🔥 **Infinite loop!**

Correct:

```java
int i = 1;

while (i <= 5) {
    System.out.println(i);
    i++;
}
```

---

# 🔵 10. Third: `do-while`

Now imagine:

> "I want the program to execute the body **at least once**, and then decide whether to continue."

Use:

```java
do-while
```

### Example

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

# 💥 11. Why Is `do-while` Special?

Look at this:

```java
int i = 10;

do {
    System.out.println(i);
} while (i < 5);
```

What will happen?

You might think:

```text
10 < 5 → false
```

so nothing prints.

❌ Wrong.

Output:

```text
10
```

Why?

Because `do-while` says:

```text
DO the work first
      ↓
CHECK the condition
```

---

# 🧠 12. `while` vs `do-while`

### `while`

```text
CHECK
  ↓
WORK
```

### `do-while`

```text
WORK
 ↓
CHECK
```

Therefore:

```text
while     → may execute 0 times
do-while  → executes at least 1 time
```

This is one of the **most important loop concepts**.

---

# 🟣 13. Fourth: Enhanced `for`

Suppose we have an array:

```java
int[] numbers = {10, 20, 30, 40};
```

We want to print every number.

We could use:

```java
for (int i = 0; i < numbers.length; i++) {
    System.out.println(numbers[i]);
}
```

But Java gives us an easier form:

```java
for (int n : numbers) {
    System.out.println(n);
}
```

This is the:

> **Enhanced `for` loop / for-each loop**

---

# 🔍 14. Understand `:` in Enhanced `for`

```java
for (int n : numbers)
```

Read it as:

> **For each `n` in `numbers`...**

So:

```text
numbers = {10, 20, 30, 40}

n = 10
n = 20
n = 30
n = 40
```

The body executes once for each element.

---

# 🧪 15. Complete Example

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

---

# 🧩 16. When Should I Use Which Loop?

This is where students usually get confused.

Ask yourself:

### Question 1:

> Do I know/control the number of repetitions with a counter?

Use:

```text
for
```

Example:

```java
for (int i = 1; i <= 10; i++)
```

---

### Question 2:

> Do I want to keep repeating while a condition is true?

Use:

```text
while
```

Example:

```java
while (balance > 0)
```

---

### Question 3:

> Must the code execute at least once?

Use:

```text
do-while
```

Example:

```java
do {
    // display menu
} while (choice != 0);
```

---

### Question 4:

> Do I simply want to process every array/collection element?

Use:

```text
enhanced for
```

Example:

```java
for (int mark : marks)
```

---

# 📊 17. The Four Loops Compared

| Loop           | Main Idea                | Condition                       |          Minimum execution |
| -------------- | ------------------------ | ------------------------------- | -------------------------: |
| `for`          | Count/control repetition | Before body                     |                          0 |
| `while`        | Condition-controlled     | Before body                     |                          0 |
| `do-while`     | Execute first            | After body                      |                          1 |
| Enhanced `for` | Traverse elements        | Automatically handles traversal | 0 if there are no elements |

---

# 🎮 18. Real-Life Example

Imagine a game.

### `for`

> "Give the player 5 lives."

```java
for (int i = 1; i <= 5; i++) {
    System.out.println("Life " + i);
}
```

---

### `while`

> "Keep playing while the player is alive."

```java
while (playerAlive) {
    playGame();
}
```

---

### `do-while`

> "Show the menu at least once."

```java
do {
    showMenu();
} while (choice != 0);
```

---

### Enhanced `for`

> "Check every player in the team."

```java
for (Player p : players) {
    p.display();
}
```

---

# 🔥 19. `break`

Sometimes you want to **stop the loop immediately**.

Use:

```java
break;
```

Example:

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

At `i == 5`:

```text
break
 ↓
STOP LOOP
```

### Memory:

> **break = completely stop**

---

# 🔥 20. `continue`

Sometimes you don't want to stop the loop.

You only want to **skip one iteration**.

Use:

```java
continue;
```

Example:

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
skip this iteration
   ↓
go to next iteration
```

### Memory:

> **continue = skip**

---

# 🧠 21. `break` vs `continue`

```text
break
  ↓
EXIT LOOP


continue
  ↓
SKIP CURRENT ITERATION
  ↓
NEXT ITERATION
```

---

# 🪆 22. Nested Loops

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

Think of it like this:

```text
Outer i = 1
    ↓
Inner loop runs completely

Outer i = 2
    ↓
Inner loop runs completely

Outer i = 3
    ↓
Inner loop runs completely
```

---

# 🎯 23. One Important Program

Print even numbers from 1 to 10:

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

Here we combine:

```text
loop + condition
```

This is how loops are commonly used in real programs.

---

# 🧪 24. Find the Sum

```java
int sum = 0;

for (int i = 1; i <= 5; i++) {
    sum = sum + i;
}

System.out.println(sum);
```

Execution:

```text
sum = 0

i=1 → sum = 1
i=2 → sum = 3
i=3 → sum = 6
i=4 → sum = 10
i=5 → sum = 15
```

Output:

```text
15
```

This teaches an important loop pattern:

```text
Initialize accumulator
        ↓
Repeat
        ↓
Update accumulator
```

---

# 🧠 25. The Biggest Mental Model

Whenever you see a loop, immediately identify **four things**:

```text
1. Where does it start?
2. What condition keeps it running?
3. What code repeats?
4. What changes each iteration?
```

Example:

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

Answer:

```text
Start       → i = 1
Condition   → i <= 5
Repeated    → println(i)
Change      → i++
```

If you can identify these four, you understand the loop.

---

# 🚨 26. Most Common Student Mistakes

### Mistake 1 — Forgetting update

```java
while (i <= 5) {
    System.out.println(i);
}
```

➡️ Infinite loop.

---

### Mistake 2 — Using `=` instead of `==`

```java
if (i = 5) // ❌
```

Assignment is not comparison.

Use:

```java
if (i == 5)
```

---

### Mistake 3 — Wrong boundary

```java
for (int i = 0; i < 5; i++)
```

Runs:

```text
0 1 2 3 4
```

not 5 times from 1.

---

### Mistake 4 — Forgetting the `do-while` semicolon

Correct:

```java
do {
    ...
} while (condition);
```

---

### Mistake 5 — Thinking enhanced `for` gives an index

```java
for (int x : numbers)
```

`x` is the **element**, not the index.

---

# 🧠 27. The Ultimate Memory Trick

```text
              LOOPS
                │
       ┌────────┼─────────┐
       │        │         │
       ↓        ↓         ↓
      FOR     WHILE    DO-WHILE
       │        │         │
     Count    Check      Do first
       │        │         │
       └────────┴─────────┘
                +
          ENHANCED FOR
                │
           Every element
```

### Remember these four sentences:

> **FOR → I know/control the repetition.**

> **WHILE → Check first, then work.**

> **DO-WHILE → Work first, then check.**

> **ENHANCED FOR → Give me every element.**

And:

> **`break` = stop the loop; `continue` = skip the current iteration.**
