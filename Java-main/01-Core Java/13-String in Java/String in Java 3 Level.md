Strings in Java — 3LEVEL

Think of this as Level 1 → Level 2 → Level 3.
First understand the idea, then the working, then the tricky/interview level.


---

🟢 LEVEL 1 — BASIC

1. What is String?

A String is an object of the String class that represents a sequence of characters.

String s = "Java";

Here:

String → class
s      → reference variable
"Java" → String value/object

String is not a primitive data type.


---

2. Creating Strings

Method 1: String Literal

String s1 = "Java";

Uses the String Pool.

Method 2: Using new

String s2 = new String("Java");

Creates a distinct String object.

Method 3: Character Array

char[] ch = {'J', 'a', 'v', 'a'};

String s3 = new String(ch);

System.out.println(s3);

Output:

Java


---

3. String Is Immutable

Immutable = cannot change the contents of an existing String object.

Example:

String s = "Java";

s.concat(" Programming");

System.out.println(s);

Output:

Java

Why?

Because concat() creates a new String.

Correct:

s = s.concat(" Programming");

Now:

Java Programming

Remember:

> The reference can change; the String object's contents cannot.




---

4. String Pool

Consider:

String s1 = "Java";
String s2 = "Java";

Java can reuse the same pooled String:

String Pool

          "Java"
          /    \
        s1      s2

Therefore:

System.out.println(s1 == s2);

Output:

true


---

5. == vs .equals()

==

Checks reference identity.

.equals()

Checks String contents.

Example:

String s1 = new String("Java");
String s2 = new String("Java");

System.out.println(s1 == s2);
System.out.println(s1.equals(s2));

Output:

false
true

Golden Rule ⭐

==        → Same object/reference?
.equals() → Same content?


---

6. intern()

intern() returns the canonical pooled representation.

String s1 = new String("Java");
String s2 = s1.intern();
String s3 = "Java";

System.out.println(s2 == s3);

Output:

true

Think:

s1 → Heap "Java"

s2 ─────→ Pool "Java" ←──── s3


---

🟡 LEVEL 2 — INTERMEDIATE

Now let's understand the important String methods.


---

7. length()

String s = "Java";

System.out.println(s.length());

Output:

4


---

8. charAt()

String s = "Java";

System.out.println(s.charAt(2));

Output:

v

Indexes begin at 0.

J a v a
0 1 2 3


---

9. substring()

String s = "Java Programming";

System.out.println(s.substring(5));

Output:

Programming

Two arguments:

System.out.println(s.substring(0, 4));

Output:

Java

Remember:

> The ending index is exclusive.




---

10. concat()

String s1 = "Java";
String s2 = " Programming";

System.out.println(s1.concat(s2));

Output:

Java Programming

It returns a new String.


---

11. equals()

System.out.println(
    "Java".equals("Java")
);

Output:

true


---

12. equalsIgnoreCase()

System.out.println(
    "JAVA".equalsIgnoreCase("java")
);

Output:

true


---

13. compareTo()

Compares Strings lexicographically.

System.out.println("A".compareTo("B"));

Output:

-1

General rule:

negative → first String comes before second
0        → equal
positive  → first String comes after second


---

14. Searching Methods

contains()

"Java Programming".contains("Java");

→ true

startsWith()

"Java Programming".startsWith("Java");

→ true

endsWith()

"Java Programming".endsWith("Programming");

→ true

indexOf()

"Java Java".indexOf("Java");

→ 0

lastIndexOf()

"Java Java".lastIndexOf("Java");

→ 5


---

15. Case Conversion

String s = "Java";

System.out.println(s.toUpperCase());
System.out.println(s.toLowerCase());

Output:

JAVA
java

Remember:

> These operations return Strings; they do not modify the original String.




---

16. replace()

String s = "Java Java";

System.out.println(
    s.replace("Java", "Python")
);

Output:

Python Python


---

17. trim() and strip()

String s = "  Java  ";

System.out.println(s.trim());
System.out.println(s.strip());

Both commonly produce:

Java

strip() uses Unicode whitespace rules, while trim() follows older, narrower whitespace behavior.


---

18. isEmpty() vs isBlank()

"".isEmpty();      // true

"   ".isEmpty();   // false

"".isBlank();      // true

"   ".isBlank();   // true

Remember:

isEmpty → zero characters
isBlank → empty or whitespace only


---

19. toCharArray()

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

20. split() ⭐

split() divides a String into a String[].

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

Important

The delimiter passed to split() is a regular expression.

For example:

"A.B.C".split("\\.");


---

21. StringBuffer

StringBuffer is a mutable character sequence.

StringBuffer sb = new StringBuffer("Java");

sb.append(" Programming");

System.out.println(sb);

Output:

Java Programming

Important methods:

append()
insert()
delete()
deleteCharAt()
replace()
reverse()
setCharAt()
length()
capacity()


---

22. StringBuilder

StringBuilder is also mutable.

StringBuilder sb = new StringBuilder("Java");

sb.append(" Programming");

System.out.println(sb);

Output:

Java Programming


---

23. String vs StringBuffer vs StringBuilder

Feature	String	StringBuffer	StringBuilder

Mutable	❌	✅	✅
Synchronization	Immutable state	Synchronized	Not synchronized
Main purpose	Text that doesn't need repeated mutation	Shared mutable text where synchronization is useful	Frequent modification in ordinary single-threaded code


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

🔴 LEVEL 3 — ADVANCED / INTERVIEW

Now let's solve the confusing parts.


---

24. Important Memory Question

What is the output?

String a = "Java";
String b = "Java";
String c = new String("Java");

System.out.println(a == b);
System.out.println(a == c);
System.out.println(a.equals(c));

Answer

true
false
true

Why?

String Pool
                 │
              "Java"
              /    \
             a      b


               Heap
                 │
              "Java"
                 ↑
                 c

So:

a == b       → true
a == c       → false
a.equals(c)  → true


---

25. More Difficult Memory Question

String s1 = "Java";
String s2 = new String("Java");
String s3 = s2.intern();
String s4 = "Java";

What happens?

Pool:

"Java"
 / | \
s1 s3 s4


Heap:

"Java"
  ↑
 s2

Therefore:

s1 == s2   // false
s1 == s3   // true
s1 == s4   // true

s2 == s3   // false
s3 == s4   // true

But:

s1.equals(s2)   // true
s2.equals(s3)   // true


---

26. Why Doesn't This Modify the String?

Consider:

String s = "Java";

s.toUpperCase();

System.out.println(s);

Output:

Java

Because:

"Java"
  ↓
toUpperCase()
  ↓
new String "JAVA"

But you didn't assign the returned value.

Correct:

s = s.toUpperCase();

Now:

JAVA


---

27. final String Is Different From String Immutability

Consider:

final String s = "Java";

final means:

> The reference s cannot be reassigned.



It does not create String immutability.

String is already immutable.

Compare:

String s = "Java";
s = "Python";       // allowed

But:

final String s = "Java";
s = "Python";       // compile-time error


---

28. null vs Empty String

These are different:

String a = null;
String b = "";

a:

No String object is referenced.

b:

References an empty String.

Therefore:

a == null       // true
b.isEmpty()     // true

But:

a.length();

causes:

NullPointerException


---

29. String Concatenation

You can use:

String s = "Java" + " Programming";

For compile-time constant literals, the compiler can perform concatenation during compilation.

With variables:

String a = "Java";
String b = " Programming";

String c = a + b;

Java uses appropriate concatenation machinery.

For explicit repeated modifications, use:

StringBuilder

rather than repeatedly creating new Strings yourself.


---

30. Complete String Method Program

class StringMethods {

    public static void main(String[] args) {

        String s = "Java Programming";

        System.out.println("length       = " + s.length());
        System.out.println("charAt       = " + s.charAt(2));
        System.out.println("substring    = " + s.substring(5));
        System.out.println("concat       = " + s.concat(" Language"));

        System.out.println("equals       = " +
                s.equals("Java Programming"));

        System.out.println("ignore case  = " +
                s.equalsIgnoreCase("JAVA PROGRAMMING"));

        System.out.println("contains     = " +
                s.contains("Java"));

        System.out.println("startsWith   = " +
                s.startsWith("Java"));

        System.out.println("endsWith     = " +
                s.endsWith("Programming"));

        System.out.println("indexOf      = " +
                s.indexOf("Java"));

        System.out.println("lastIndexOf  = " +
                s.lastIndexOf("a"));

        System.out.println("uppercase    = " +
                s.toUpperCase());

        System.out.println("lowercase    = " +
                s.toLowerCase());

        System.out.println("replace      = " +
                s.replace("Java", "Python"));

        System.out.println("isEmpty      = " +
                s.isEmpty());

        System.out.println("isBlank      = " +
                s.isBlank());
    }
}


---

31. Complete split() Program

class SplitDemo {

    public static void main(String[] args) {

        String data = "Java,Python,C++,JavaScript";

        String[] languages = data.split(",");

        for (String language : languages) {
            System.out.println(language);
        }
    }
}

Output:

Java
Python
C++
JavaScript


---

32. Complete StringBuffer Program

class BufferDemo {

    public static void main(String[] args) {

        StringBuffer sb = new StringBuffer("Java");

        sb.append(" Programming");
        System.out.println(sb);

        sb.insert(5, "Language ");
        System.out.println(sb);

        sb.delete(5, 14);
        System.out.println(sb);

        sb.replace(0, 4, "Python");
        System.out.println(sb);

        sb.setCharAt(0, 'J');
        System.out.println(sb);

        System.out.println("Length = " + sb.length());
        System.out.println("Capacity = " + sb.capacity());

        sb.reverse();
        System.out.println(sb);
    }
}


---

33. Complete StringBuilder Program

class BuilderDemo {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder("Java");

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

34. 🎯 3-Level Final Revision

🟢 Level 1 — Remember

String = class
String = reference type
String = immutable
String literals → String Pool
== → reference identity
equals() → content


---

🟡 Level 2 — Understand

String
  ↓
Immutable

String Pool
  ↓
Reuse String literals

new String()
  ↓
Distinct String object

intern()
  ↓
Pooled representation

split()
  ↓
String[]

StringBuffer
  ↓
Mutable + synchronized

StringBuilder
  ↓
Mutable + not synchronized


---

🔴 Level 3 — Solve

Whenever you see:

String a = "Java";
String b = "Java";
String c = new String("Java");
String d = c.intern();

Immediately draw:

STRING POOL
                  │
                "Java"
               /  |  \
              a   b   d


                 HEAP
                  │
                "Java"
                  ↑
                  c

Then answer:

a == b       → true
a == c       → false
a == d       → true
c == d       → false

a.equals(c)  → true
c.equals(d)  → true

🏆 Final formula

> String = Immutable + String Pool + == identity + .equals() content + intern() pool + StringBuffer/StringBuilder for mutable text.
