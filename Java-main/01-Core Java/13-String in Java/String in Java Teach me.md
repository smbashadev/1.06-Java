Strings in Java — TEACHME 🎓

Let's learn Strings in Java from zero to interview level. The main goal is not just memorizing methods, but understanding what actually happens in memory.


---

1. First Question: What is a String?

Suppose you write:

String name = "Java";

A String is an object of the String class used to represent a sequence of characters.

String → Class
name   → Reference variable
"Java" → String object/value

String is not a primitive data type.

Primitive example

int x = 10;

String example

String s = "Java";

So remember:

> String is a class, not a primitive data type.




---

2. Why Don't We Write new?

You might ask:

> "If String is a class, why do we write this?"



String s = "Java";

instead of:

String s = new String("Java");

Both are valid.

The first is called String literal creation.

The second explicitly creates a String object using new.


---

3. Three Important Ways to Create a String

Way 1 — String Literal

String s1 = "Java";

This uses the String Pool.


---

Way 2 — Using new

String s2 = new String("Java");

This creates a distinct String object.


---

Way 3 — From Character Array

char[] ch = {'J', 'a', 'v', 'a'};

String s3 = new String(ch);

System.out.println(s3);

Output:

Java


---

4. What is String Pool?

This is the most important concept.

Consider:

String s1 = "Java";
String s2 = "Java";

Will Java create two "Java" objects?

For String literals, Java can reuse the same pooled String.

Conceptually:

String Pool

               "Java"
               /    \
             s1      s2

So:

System.out.println(s1 == s2);

Output:

true

Why?

Because both references refer to the same pooled object.


---

5. Now Use new

Look at this:

String s1 = "Java";
String s2 = new String("Java");

Conceptually:

String Pool                 Heap

"Java"                      "Java"
  ↑                            ↑
 s1                           s2

Now:

System.out.println(s1 == s2);

Output:

false

But:

System.out.println(s1.equals(s2));

Output:

true

This leads us to the most important difference in Strings.


---

6. == vs .equals()

==

For objects, == checks reference identity.

It asks:

> "Are these references pointing to the same object?"




---

.equals()

For Strings, .equals() checks content equality.

It asks:

> "Do these two String objects contain the same characters?"




---

Example

String a = new String("Java");
String b = new String("Java");

System.out.println(a == b);
System.out.println(a.equals(b));

Output:

false
true

Why?

a ───→ "Java"   ← Object 1

b ───→ "Java"   ← Object 2

Two different objects:

a == b → false

Same content:

a.equals(b) → true

Golden Rule ⭐

> == → same object?
.equals() → same content?




---

7. Duplicate Strings in the String Pool

String s1 = "Java";
String s2 = "Java";
String s3 = "Java";

Conceptually:

String Pool

                 "Java"
              /    |    \
            s1    s2     s3

Therefore:

s1 == s2

is:

true

and:

s1.equals(s2)

is also:

true


---

8. Duplicate Strings in Heap

Now:

String s1 = new String("Java");
String s2 = new String("Java");

Conceptually:

Heap

"Java" ← s1

"Java" ← s2

They are separate objects.

Therefore:

System.out.println(s1 == s2);

Output:

false

But:

System.out.println(s1.equals(s2));

Output:

true


---

9. The Most Important Concept: String Is Immutable

What does immutable mean?

> Once a String object is created, its contents cannot be changed.



Example:

String s = "Java";

s.concat(" Programming");

System.out.println(s);

Output:

Java

You may wonder:

> "But concat() should add Programming!"



It creates a new String.

You didn't store the returned String.


---

10. Correct Use of concat()

String s = "Java";

s = s.concat(" Programming");

System.out.println(s);

Output:

Java Programming

What happened?

Before:

s ───→ "Java"


After:

s ───→ "Java Programming"

The original "Java" object was not modified.


---

11. A Common Student Confusion

Consider:

String s = "Java";

s = "Python";

Some students say:

> "String is mutable because its value changed."



❌ No.

The reference changed.

The object did not change.

Before:

s ───→ "Java"


After:

s ───→ "Python"

The "Java" object remains unchanged.

Remember:

> Changing a reference is not the same as changing an object.




---

12. Why Is String Immutable?

There are several important reasons.

1. Security

Strings are used for sensitive information such as:

URLs
file paths
class names
connection information

Immutable values cannot unexpectedly change.

2. String Pool

The JVM can safely share String objects.

String a = "Java";
String b = "Java";

If Strings were mutable, changing one could affect the other.

3. Thread safety

Immutable objects can safely be shared between threads without modifying their state.

4. Hashing

Strings are commonly used as keys in hash-based collections. Stable content helps maintain stable hash behavior.


---

13. intern() — Very Important

Suppose:

String s1 = new String("Java");

s1 refers to a distinct object.

Now:

String s2 = s1.intern();

intern() gives you the canonical pooled representation.

If:

String s3 = "Java";

then:

System.out.println(s2 == s3);

Output:

true

Think:

s1 ───→ Heap "Java"

s2 ───────┐
          ↓
       Pool "Java" ←──── s3


---

14. Does intern() Change the Original Object?

No.

String s1 = new String("Java");
String s2 = s1.intern();

s1 still points to its original object.

intern() returns a pooled reference.


---

15. Is "String Doesn't Have Immutable Objects" Correct?

No.

The correct statement is:

> String objects are immutable.



The confusion usually comes from:

String s = "Java";
s = "Python";

Here the reference changed.

The "Java" object did not change.


---

16. String Methods

Now let's learn the most important methods.


---

length()

Returns the number of characters.

String s = "Java";

System.out.println(s.length());

Output:

4

Remember:

Java
0123

Length is 4, last index is 3.


---

17. charAt()

Returns the character at an index.

String s = "Java";

System.out.println(s.charAt(2));

Output:

v

Indexes start at 0.


---

18. substring()

Extracts part of a String.

String s = "Java Programming";

System.out.println(s.substring(5));

Output:

Programming

Two arguments:

System.out.println(s.substring(0, 4));

Output:

Java

Important

The ending index is exclusive.

substring(0, 4)

0 1 2 3
J a v a


---

19. concat()

String a = "Java";
String b = " Programming";

System.out.println(a.concat(b));

Output:

Java Programming

Remember: Strings are immutable, so concat() returns a new String.


---

20. equals()

String a = "Java";
String b = "Java";

System.out.println(a.equals(b));

Output:

true

Checks content.


---

21. equalsIgnoreCase()

System.out.println(
    "JAVA".equalsIgnoreCase("java")
);

Output:

true

Case doesn't matter.


---

22. compareTo()

Used for lexicographical comparison.

System.out.println("A".compareTo("B"));

Output:

-1

General idea:

negative → first comes before second
0        → equal
positive  → first comes after second


---

23. compareToIgnoreCase()

System.out.println(
    "JAVA".compareToIgnoreCase("java")
);

Output:

0


---

24. contains()

Checks whether a sequence exists.

String s = "Java Programming";

System.out.println(s.contains("Java"));

Output:

true


---

25. startsWith()

System.out.println(
    "Java Programming".startsWith("Java")
);

Output:

true


---

26. endsWith()

System.out.println(
    "Java Programming".endsWith("Programming")
);

Output:

true


---

27. indexOf()

Finds the first occurrence.

System.out.println(
    "Java Java".indexOf("Java")
);

Output:

0


---

28. lastIndexOf()

Finds the last occurrence.

System.out.println(
    "Java Java".lastIndexOf("Java")
);

Output:

5


---

29. toUpperCase()

System.out.println("Java".toUpperCase());

Output:

JAVA


---

30. toLowerCase()

System.out.println("JAVA".toLowerCase());

Output:

java


---

31. replace()

String s = "Java Java";

System.out.println(
    s.replace("Java", "Python")
);

Output:

Python Python


---

32. replaceFirst()

Replaces the first regex match.

String s = "Java Java";

System.out.println(
    s.replaceFirst("Java", "Python")
);

Output:

Python Java


---

33. replaceAll()

Replaces all regex matches.

String s = "Java123";

System.out.println(
    s.replaceAll("\\d", "")
);

Output:

Java


---

34. trim()

Removes leading and trailing characters traditionally treated as ASCII spaces.

String s = "  Java  ";

System.out.println(s.trim());

Output:

Java


---

35. strip()

Uses Unicode whitespace rules.

String s = "  Java  ";

System.out.println(s.strip());

Output:

Java


---

36. isEmpty()

Checks whether the String has zero characters.

System.out.println("".isEmpty());

Output:

true

But:

System.out.println("   ".isEmpty());

Output:

false

Because spaces are characters.


---

37. isBlank()

Checks whether the String is empty or contains only whitespace.

System.out.println("   ".isBlank());

Output:

true

Remember:

isEmpty() → zero characters

isBlank() → empty OR whitespace only


---

38. toCharArray()

Converts String into a character array.

String s = "Java";

char[] ch = s.toCharArray();

for (char c : ch) {
    System.out.println(c);
}

Output:

J
a
v
a


---

39. valueOf()

Converts a value into a String.

int n = 100;

String s = String.valueOf(n);

System.out.println(s);

Output:

100


---

40. join()

String result = String.join(
    "-",
    "Java",
    "Python",
    "C++"
);

System.out.println(result);

Output:

Java-Python-C++


---

41. repeat()

System.out.println("Java ".repeat(3));

Output:

Java Java Java


---

42. matches()

Checks whether the whole String matches a regular expression.

String s = "12345";

System.out.println(s.matches("\\d+"));

Output:

true


---

43. split() — VERY IMPORTANT ⭐

split() divides a String into a String[].

Example:

String s = "Java,Python,C++";

String[] arr = s.split(",");

for (String x : arr) {
    System.out.println(x);
}

Output:

Java
Python
C++

Think:

Java,Python,C++
       ↓
    split(",")
       ↓
Java | Python | C++


---

44. Why Does split() Return an Array?

Because one String is being divided into multiple Strings.

String s = "A-B-C";

String[] arr = s.split("-");

Result:

arr[0] = "A"
arr[1] = "B"
arr[2] = "C"


---

45. split() Uses Regular Expressions

The delimiter is a regular expression.

For example:

"A.B.C".split("\\.")

The \\. is used because . has special meaning in regular expressions.


---

46. split() with Limit

String s = "A-B-C-D";

String[] arr = s.split("-", 2);

Result:

A
B-C-D

The limit controls the number of resulting pieces.


---

47. StringBuffer

Now suppose you need to modify text many times.

String is immutable.

So Java provides:

StringBuffer

StringBuffer is mutable.

Example:

StringBuffer sb = new StringBuffer("Java");

sb.append(" Programming");

System.out.println(sb);

Output:

Java Programming

The same mutable buffer is modified.


---

48. Important StringBuffer Methods

append()

sb.append(" Java");

insert()

sb.insert(0, "Hello ");

delete()

sb.delete(0, 5);

deleteCharAt()

sb.deleteCharAt(0);

replace()

sb.replace(0, 4, "Python");

reverse()

sb.reverse();

setCharAt()

sb.setCharAt(0, 'J');

length()

sb.length();

capacity()

sb.capacity();


---

49. StringBuffer Program

class Demo {

    public static void main(String[] args) {

        StringBuffer sb = new StringBuffer("Java");

        System.out.println(sb);

        sb.append(" Programming");
        System.out.println(sb);

        sb.insert(5, "Language ");
        System.out.println(sb);

        sb.delete(5, 14);
        System.out.println(sb);

        sb.replace(0, 4, "Python");
        System.out.println(sb);

        sb.reverse();
        System.out.println(sb);
    }
}


---

50. StringBuilder

StringBuilder is also mutable.

StringBuilder sb = new StringBuilder("Java");

sb.append(" Programming");

System.out.println(sb);

Output:

Java Programming


---

51. StringBuffer vs StringBuilder

This is a common interview question.

Feature	String	StringBuffer	StringBuilder

Mutable	❌	✅	✅
Thread-safe via synchronization	Immutable state	Yes	No
Modification	New String when needed	Same buffer	Same builder
Typical use	Fixed/mostly-fixed text	Shared mutable text where synchronization is useful	Frequent modification in ordinary single-threaded code


Easy memory trick:

String
   ↓
Immutable

StringBuffer
   ↓
Mutable + synchronized

StringBuilder
   ↓
Mutable + not synchronized


---

52. One Complete Example

Let's combine everything.

class StringDemo {

    public static void main(String[] args) {

        String s = "Java Programming";

        System.out.println("Original       : " + s);
        System.out.println("Length         : " + s.length());
        System.out.println("charAt(2)      : " + s.charAt(2));
        System.out.println("Substring      : " + s.substring(5));
        System.out.println("Contains Java  : " + s.contains("Java"));
        System.out.println("Starts Java    : " + s.startsWith("Java"));
        System.out.println("Ends Program   : " + s.endsWith("Programming"));
        System.out.println("Index of Java  : " + s.indexOf("Java"));
        System.out.println("Uppercase      : " + s.toUpperCase());
        System.out.println("Lowercase      : " + s.toLowerCase());
        System.out.println("Replace        : " +
                s.replace("Java", "Python"));

        String[] arr = s.split(" ");

        System.out.println("\nAfter split:");

        for (String x : arr) {
            System.out.println(x);
        }
    }
}


---

53. The Big Picture 🧠

When you see:

String s = "Java";

think:

String
           ↓
         Class
           ↓
         Object
           ↓
       Immutable
           ↓
     String Pool may
       be involved

When you see:

String s = new String("Java");

think:

"Java" → Pool

new String(...)
      ↓
distinct String object

When you see:

s1 == s2

think:

> Same reference/object?



When you see:

s1.equals(s2)

think:

> Same content?



When you see:

s.intern()

think:

> Get the pooled/canonical String representation.



When you see:

StringBuffer

think:

> Mutable + synchronized



When you see:

StringBuilder

think:

> Mutable + not synchronized




---

54. Final Exam Revision ⭐

STRING
│
├── Class
│
├── Reference type
│
├── Immutable
│
├── String Pool
│
├── Creation
│   ├── Literal
│   ├── new String()
│   └── char[]
│
├── Comparison
│   ├── ==       → reference identity
│   └── equals() → content
│
├── Pool
│   └── intern()
│
├── Important methods
│   ├── length()
│   ├── charAt()
│   ├── substring()
│   ├── concat()
│   ├── equals()
│   ├── compareTo()
│   ├── contains()
│   ├── indexOf()
│   ├── lastIndexOf()
│   ├── startsWith()
│   ├── endsWith()
│   ├── replace()
│   ├── replaceFirst()
│   ├── replaceAll()
│   ├── split()
│   ├── trim()
│   ├── strip()
│   ├── isEmpty()
│   ├── isBlank()
│   ├── toUpperCase()
│   ├── toLowerCase()
│   ├── toCharArray()
│   ├── valueOf()
│   ├── join()
│   └── repeat()
│
└── Mutable alternatives
    ├── StringBuffer
    └── StringBuilder

🏆 The 5 facts you absolutely must remember

1. String is a class, not a primitive.


2. String objects are immutable.


3. String literals can be stored/reused in the String Pool.


4. == checks reference identity; .equals() checks String content.


5. StringBuffer and StringBuilder are mutable; StringBuffer is synchronized, StringBuilder is not.


