Conditional Statements in Java — 3 LEVELS 🧠

We'll learn this in Level 1 → Level 2 → Level 3, so you can move from basic understanding to interview/exam-level confidence.


---

🟢 LEVEL 1 — FOUNDATION

1. What is a Conditional Statement?

A conditional statement allows Java to make a decision.

Example:

int age = 20;

if (age >= 18) {
    System.out.println("Adult");
}

Java asks:

Is age >= 18?
       ↓
     true
       ↓
Print "Adult"

A condition must produce:

true

or

false


---

2. Main Conditional Statements

Java provides:

1. if
2. if-else
3. else-if ladder
4. nested if
5. switch


---

3. if

Meaning

> Execute something only if the condition is true.



Syntax

if (condition) {
    // statements
}

Example

int number = 10;

if (number > 0) {
    System.out.println("Positive");
}

Output:

Positive

If the condition is false, the block is skipped.


---

4. if-else

Used when there are two possibilities.

Syntax

if (condition) {
    // true
}
else {
    // false
}

Example

int number = 7;

if (number % 2 == 0) {
    System.out.println("Even");
}
else {
    System.out.println("Odd");
}

Output:

Odd

Think:

condition
  ↙   ↘
true  false
 ↓      ↓
if     else


---

5. else-if Ladder

Used when there are multiple conditions.

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

Output:

B

Golden rule:

> The first true condition wins.




---

6. Nested if

An if inside another if.

int age = 20;
boolean citizen = true;

if (age >= 18) {

    if (citizen) {
        System.out.println("Eligible");
    }
}

Think:

First decision
      ↓
     true
      ↓
Second decision
      ↓
     true
      ↓
   Execute


---

7. switch

Used when one value has several fixed choices.

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

Output:

Tuesday

Important keywords:

case    → possible value
break   → exit switch
default → no case matched


---

🟡 LEVEL 2 — UNDERSTANDING

Now let's understand how Java actually makes the decision.


---

8. Conditions Use Comparison Operators

Operator	Meaning

>	Greater than
<	Less than
>=	Greater than or equal
<=	Less than or equal
==	Equal
!=	Not equal


Example:

int a = 10;
int b = 20;

if (a < b) {
    System.out.println("a is smaller");
}


---

9. = vs ==

Very important!

=

Assignment:

int x = 10;

Means:

> Store 10 in x.



==

Comparison:

x == 10

Means:

> Is x equal to 10?



Therefore:

if (x == 10) {
    System.out.println("Yes");
}


---

10. Multiple Conditions

Java has logical operators.

AND — &&

Both conditions must be true.

if (age >= 18 && citizen) {
    System.out.println("Eligible");
}

true  && true  → true
true  && false → false
false && true  → false
false && false → false

Memory:

> && = BOTH




---

11. OR — ||

At least one condition must be true.

if (day == 6 || day == 7) {
    System.out.println("Weekend");
}

true  || true  → true
true  || false → true
false || true  → true
false || false → false

Memory:

> || = AT LEAST ONE




---

12. NOT — !

Reverses the boolean value.

boolean raining = false;

if (!raining) {
    System.out.println("Go outside");
}

!true  → false
!false → true


---

13. Multiple if vs else-if

This is a major exam/interview concept.

Separate if

if (x > 10) {
    System.out.println("A");
}

if (x > 20) {
    System.out.println("B");
}

Both conditions are independently checked.

If x = 30:

A
B


---

else-if

if (x > 10) {
    System.out.println("A");
}
else if (x > 20) {
    System.out.println("B");
}

If x = 30:

A

Because the first condition is already true.

Remember:

> Separate ifs → multiple blocks may execute.



> else-if ladder → only the first matching block executes.




---

14. Order of else-if Matters

Wrong ordering:

if (marks >= 60) {
    System.out.println("C");
}
else if (marks >= 90) {
    System.out.println("A");
}

For:

marks = 95

Output:

C

because:

95 >= 60 → true

Java stops there.

Correct ordering:

if (marks >= 90) {
    System.out.println("A");
}
else if (marks >= 60) {
    System.out.println("C");
}

Now:

95 >= 90 → true

Output:

A


---

15. switch Fall-Through

Consider:

int x = 1;

switch (x) {

    case 1:
        System.out.println("One");

    case 2:
        System.out.println("Two");

    case 3:
        System.out.println("Three");
}

Output:

One
Two
Three

Why?

There is no break.

This is called:

> Fall-through



With:

case 1:
    System.out.println("One");
    break;

execution exits the switch after case 1.


---

16. default

default executes when no case matches.

int day = 10;

switch (day) {

    case 1:
        System.out.println("Monday");
        break;

    case 2:
        System.out.println("Tuesday");
        break;

    default:
        System.out.println("Invalid");
}

Output:

Invalid


---

🔴 LEVEL 3 — MASTER LEVEL

Now let's handle the doubts that usually cause mistakes in exams and interviews.


---

17. Does Java Allow if (10)?

❌ No.

This is invalid:

int x = 10;

if (x) {
    System.out.println("Hello");
}

Java requires the condition to be a boolean expression.

Correct:

if (x != 0) {
    System.out.println("Hello");
}


---

18. Can if Exist Without else?

✅ Yes.

if (x > 0) {
    System.out.println("Positive");
}

else is optional.


---

19. Can else Exist Without if?

❌ No.

Invalid:

else {
    System.out.println("Hello");
}

else must belong to an if.


---

20. Can else-if Exist Without Final else?

✅ Yes.

if (x > 100) {
    System.out.println("Large");
}
else if (x > 50) {
    System.out.println("Medium");
}

If neither condition is true, nothing happens.


---

21. Is default Mandatory in switch?

❌ No.

This is valid:

switch (day) {

    case 1:
        System.out.println("Monday");
        break;

    case 2:
        System.out.println("Tuesday");
        break;
}

If there is no match, nothing happens.


---

22. Is break Mandatory in switch?

❌ No.

But if you omit it, you may get fall-through.

case 1:
    System.out.println("One");
    break;

Use break when you want to exit the switch after that case.


---

23. Nested if and the else Problem

Consider:

if (a > 0)
    if (b > 0)
        System.out.println("A");
    else
        System.out.println("B");

Which if does else belong to?

It belongs to the nearest unmatched if:

if (a > 0)
    └── if (b > 0)
            └── else

To avoid confusion, use braces:

if (a > 0) {

    if (b > 0) {
        System.out.println("A");
    }
    else {
        System.out.println("B");
    }
}


---

24. Short-Circuit Evaluation

This is a higher-level concept.

Consider:

if (x != 0 && 10 / x > 2) {
    System.out.println("Valid");
}

Suppose:

x = 0

Java evaluates:

x != 0
   ↓
false

Because the left side of && is false, Java doesn't need to evaluate:

10 / x

This is called:

> Short-circuit evaluation



Similarly:

if (x == 10 || expensiveOperation()) {
}

If:

x == 10

is already true, Java doesn't need to evaluate the second side.


---

25. if-else vs switch — Master Decision

Ask yourself:

Question 1

Are you checking a range?

marks >= 90

Use:

if / else-if


---

Question 2

Are you checking complex conditions?

age >= 18 && citizen

Use:

if


---

Question 3

Are you matching one value against fixed choices?

day == 1
day == 2
day == 3

Consider:

switch


---

26. Complete Example — Exam Marks

class Result {

    public static void main(String[] args) {

        int marks = 85;

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid Marks");
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

Trace:

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
   ↓
Grade B


---

27. Complete Example — Menu

This is a good switch situation.

class Menu {

    public static void main(String[] args) {

        int choice = 2;

        switch (choice) {

            case 1:
                System.out.println("Add");
                break;

            case 2:
                System.out.println("Delete");
                break;

            case 3:
                System.out.println("Update");
                break;

            default:
                System.out.println("Invalid Choice");
        }
    }
}

Output:

Delete


---

28. 3-Level Summary

🟢 LEVEL 1 — Know

if
if-else
else-if
nested if
switch

Know their basic syntax.


---

🟡 LEVEL 2 — Understand

if       → one condition
if-else  → two paths
else-if  → multiple conditions
nested   → decision inside decision
switch   → fixed choices

Also understand:

&& → AND
|| → OR
!  → NOT


---

🔴 LEVEL 3 — Master

Remember:

First true else-if branch wins.
Separate if statements are independent.
switch can fall through without break.
default is optional.
break is generally used to exit a switch case.
Java conditions must produce boolean values.
else belongs to the nearest unmatched if.
Short-circuiting occurs with && and ||.


---

🏆 FINAL 10-SECOND REVISION

CONDITIONAL STATEMENTS
                       │
       ┌───────────────┼────────────────┐
       ↓               ↓                ↓
      if           if-else          else-if
   one choice     two paths       many conditions
                       │
                       ↓
                  nested if
               decision inside
                  decision

                       +
                    switch
                       │
                  fixed values
                       │
              case → break → exit

The easiest memory trick:

> ONE → if



> TWO → if-else



> MANY CONDITIONS → else-if



> DECISION INSIDE DECISION → nested if



> MANY FIXED VALUES → switch
