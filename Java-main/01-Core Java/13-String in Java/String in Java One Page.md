Strings in Java — ONE PAGE

> Core idea: String is a class in Java used to represent a sequence of characters.
The most important property of String is that String objects are immutable.




---

1. String Definition

A String is a sequence of characters represented by an object of the java.lang.String class.

String s = "Java";

Here:

String → class
s      → reference variable
"Java" → String object

String belongs to java.lang, so no import is required.

String s = "Hello";
System.out.println(s);

Output:

Hello


---

2. Immutable String

Immutable means:

> Once a String object is created, its contents cannot be changed.



Example:

String s = "Java";

s.concat(" Programming");

System.out.println(s);

Output:

Java

Why?

concat() does not modify "Java". It creates another String object.

Correct:

String s = "Java";

s = s.concat(" Programming");

System.out.println(s);

Output:

Java Programming

The original "Java" object was not changed. The reference s was made to point to another object.


---

3. Mutable vs Immutable Strings

Feature	Immutable String	Mutable StringBuffer / StringBuilder

Can content change?	❌ No	✅ Yes
Modification creates new object?	Usually yes	No, same object can be modified
Thread safety	Naturally safe because immutable	StringBuffer synchronized
Performance for repeated modification	Less suitable	Better
Example	String s="Java";	StringBuilder sb=new StringBuilder("Java");


Immutable example

String s = "Java";

s = s.concat(" Programming");

System.out.println(s);

Mutable example

StringBuilder sb = new StringBuilder("Java");

sb.append(" Programming");

System.out.println(sb);


---

4. Three Common Ways to Create String Objects

① String Literal

String s1 = "Java";

The literal is stored in the String Pool.


---

② Using new

String s2 = new String("Java");

This explicitly creates a String object on the heap. The literal "Java" is also associated with the String Pool if it wasn't already there.


---

③ Using Character Array

char[] ch = {'J', 'a', 'v', 'a'};

String s3 = new String(ch);

System.out.println(s3);

Output:

Java

All three produce a String whose contents cannot be changed.

> Important: A String is immutable regardless of whether it was created using a literal or new String().




---

5. String Pool

Java maintains a special pool for String literals.

String s1 = "Java";
String s2 = "Java";

Usually only one pooled "Java" object is used.

s1 ─────┐
        ↓
     "Java"
   String Pool
        ↑
s2 ─────┘

Therefore:

System.out.println(s1 == s2);

Output:

true


---

6. == vs .equals()

This is one of the biggest String interview questions.

==

Compares references — whether two references point to the same object.

.equals()

Compares String contents.

Example:

String s1 = "Java";
String s2 = "Java";

System.out.println(s1 == s2);
System.out.println(s1.equals(s2));

Output:

true
true

Now:

String s1 = new String("Java");
String s2 = new String("Java");

System.out.println(s1 == s2);
System.out.println(s1.equals(s2));

Output:

false
true

Why?

s1 → Heap object "Java"

s2 → Different Heap object "Java"

Contents are equal, references are different.


---

7. Duplicate String Outside the String Pool — Heap

class Demo {
    public static void main(String[] args) {

        String s1 = "Java";
        String s2 = new String("Java");

        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
    }
}

Output:

false
true

Conceptually:

String Pool             Heap

"Java" ← s1

                          "Java" ← s2

new String("Java") creates a separate String object.


---

8. Duplicate Strings Inside the String Pool

class Demo {
    public static void main(String[] args) {

        String s1 = "Java";
        String s2 = "Java";

        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
    }
}

Output:

true
true

Both references point to the same pooled object.


---

9. intern() Method

intern() returns the canonical pooled representation of a String.

String s1 = new String("Java");
String s2 = s1.intern();

String s3 = "Java";

System.out.println(s2 == s3);

Output:

true

Conceptually:

s1 → Heap "Java"

s2 ─────┐
        ↓
Pool → "Java" ← s3

Remember

new String("Java").intern()

gives the pooled "Java" reference.


---

10. Important Correction: "String Doesn't Have Immutable Objects"

This statement is incorrect.

Java String objects are immutable.

What sometimes causes confusion is this:

String s = "Java";

s = "Python";

It looks as though the String changed from Java to Python.

It did not.

Instead:

Before:

s → "Java"


After:

s → "Python"

The reference changed. The "Java" object was not modified.


---

11. Important String Methods

Assume:

String s = "Hello Java";

length()

Returns number of characters.

System.out.println(s.length());

Output:

10


---

charAt()

Returns character at a particular index.

System.out.println(s.charAt(1));

Output:

e

Indexes start from 0.


---

substring()

System.out.println(s.substring(6));

Output:

Java

Two arguments:

System.out.println(s.substring(0, 5));

Output:

Hello

endIndex is exclusive.


---

concat()

System.out.println(s.concat(" Programming"));

Output:

Hello Java Programming


---

equals()

System.out.println("Java".equals("Java"));

Output:

true


---

equalsIgnoreCase()

System.out.println("JAVA".equalsIgnoreCase("java"));

Output:

true


---

compareTo()

Lexicographically compares two Strings.

System.out.println("A".compareTo("B"));

Output:

-1


---

compareToIgnoreCase()

System.out.println("JAVA".compareToIgnoreCase("java"));

Output:

0


---

contains()

System.out.println(s.contains("Java"));

Output:

true


---

startsWith()

System.out.println(s.startsWith("Hello"));

Output:

true


---

endsWith()

System.out.println(s.endsWith("Java"));

Output:

true


---

indexOf()

System.out.println(s.indexOf("Java"));

Output:

6


---

lastIndexOf()

System.out.println("Java Java".lastIndexOf("Java"));

Output:

5


---

toUpperCase()

System.out.println(s.toUpperCase());

Output:

HELLO JAVA


---

toLowerCase()

System.out.println(s.toLowerCase());

Output:

hello java


---

trim()

Removes leading and trailing characters traditionally defined as ASCII spaces.

String x = "  Java  ";

System.out.println(x.trim());

Output:

Java


---

strip()

Removes leading and trailing Unicode whitespace.

String x = "  Java  ";

System.out.println(x.strip());

Output:

Java


---

isEmpty()

Checks whether length is 0.

System.out.println("".isEmpty());

Output:

true


---

isBlank()

Checks whether the String is empty or contains only Unicode whitespace.

System.out.println("   ".isBlank());

Output:

true


---

replace()

Replaces characters or literal character sequences.

System.out.println("Java".replace('a', 'o'));

Output:

Jovo


---

replaceFirst()

Uses a regular expression and replaces the first matching occurrence.

System.out.println("Java Java".replaceFirst("Java", "Python"));

Output:

Python Java


---

replaceAll()

Uses a regular expression and replaces all matches.

System.out.println("Java123".replaceAll("\\d", ""));

Output:

Java


---

12. split() — Very Important

split() divides a String into an array based on a regular-expression delimiter.

String s = "Java,Python,C++";

String[] a = s.split(",");

for (String x : a) {
    System.out.println(x);
}

Output:

Java
Python
C++

Conceptually:

"Java,Python,C++"

       split(",")
           ↓

["Java", "Python", "C++"]

With whitespace

String s = "Java Python C++";

String[] a = s.split(" ");

for (String x : a) {
    System.out.println(x);
}


---

13. split() Uses Regular Expressions

This is important.

For example, to split on a dot:

String s = "A.B.C";

String[] a = s.split("\\.");

Why \\.?

Because . has a special meaning in regular expressions.


---

14. toCharArray()

Converts String into a character array.

String s = "Java";

char[] ch = s.toCharArray();

for (char c : ch) {
    System.out.println(c);
}


---

15. getBytes()

Converts String into bytes using the specified/default charset.

String s = "Java";

byte[] b = s.getBytes();

for (byte x : b) {
    System.out.println(x);
}

For predictable encoding, prefer an explicit charset, e.g. StandardCharsets.UTF_8.


---

16. valueOf()

Converts values into String representations.

int n = 100;

String s = String.valueOf(n);

System.out.println(s);

Output:

100


---

17. format()

Creates a formatted String.

String s = String.format("Name: %s, Age: %d", "Ravi", 20);

System.out.println(s);

Output:

Name: Ravi, Age: 20


---

18. join()

Joins multiple Strings using a delimiter.

String s = String.join("-", "2026", "08", "19");

System.out.println(s);

Output:

2026-08-19


---

19. repeat()

Repeats a String a specified number of times.

System.out.println("Java ".repeat(3));

Output:

Java Java Java


---

20. matches()

Checks whether the entire String matches a regular expression.

String s = "12345";

System.out.println(s.matches("\\d+"));

Output:

true


---

21. regionMatches()

Compares a specific region of two Strings.

String s1 = "HelloJava";
String s2 = "Java";

System.out.println(s1.regionMatches(5, s2, 0, 4));

Output:

true


---

22. contentEquals()

Compares String content with another character sequence.

String s = "Java";

StringBuilder sb = new StringBuilder("Java");

System.out.println(s.contentEquals(sb));

Output:

true


---

23. codePointAt()

Returns the Unicode code point at an index.

String s = "Java";

System.out.println(s.codePointAt(0));

Output:

74


---

24. codePointCount()

Counts Unicode code points in a range.

String s = "Java";

System.out.println(s.codePointCount(0, s.length()));

Output:

4


---

25. offsetByCodePoints()

Returns an index offset by a specified number of Unicode code points.

String s = "Java";

System.out.println(s.offsetByCodePoints(0, 2));

Output:

2


---

26. getChars()

Copies characters into a character array.

String s = "Java";

char[] ch = new char[4];

s.getChars(0, 4, ch, 0);

System.out.println(ch);

Output:

Java


---

27. subSequence()

Returns a character sequence from the String.

String s = "Java";

System.out.println(s.subSequence(1, 3));

Output:

av


---

28. toString()

Returns the String itself.

String s = "Java";

System.out.println(s.toString());

Output:

Java


---

29. hashCode()

Returns the hash code of the String.

String s = "Java";

System.out.println(s.hashCode());


---

30. transform()

Applies a function to a String.

String s = "java";

String result = s.transform(x -> x.toUpperCase());

System.out.println(result);

Output:

JAVA


---

31. indent()

Adds/removes indentation according to the specified number.

String s = "Java";

System.out.print(s.indent(4));


---

32. stripIndent()

Removes incidental indentation from a multi-line String.

String s = """
        Java
        Python
        """;

System.out.println(s.stripIndent());


---

33. translateEscapes()

Processes escape sequences contained in a String.

String s = "Java\\nProgramming";

System.out.println(s.translateEscapes());

Output:

Java
Programming


---

34. lines()

Returns a stream of lines.

String s = "Java\nPython\nC++";

s.lines().forEach(System.out::println);

Output:

Java
Python
C++


---

35. formatted()

Formats a String using its format specifiers.

String s = "Name: %s, Age: %d".formatted("Ravi", 20);

System.out.println(s);

Output:

Name: Ravi, Age: 20


---

36. Null/Whitespace Helpers

isEmpty()

"".isEmpty()

→ true

isBlank()

"   ".isBlank()

→ true

These are different:

isEmpty → no characters
isBlank → empty OR only whitespace


---

37. Static String Methods You Should Know

Common static methods include:

String.valueOf()
String.copyValueOf()
String.format()
String.join()
String.compare()
String.CASE_INSENSITIVE_ORDER

Example:

char[] ch = {'J', 'a', 'v', 'a'};

String s = String.copyValueOf(ch);

System.out.println(s);

Output:

Java


---

38. One Program Using Many String Methods

class StringMethodsDemo {

    public static void main(String[] args) {

        String s = "Hello Java";

        System.out.println("Original       : " + s);
        System.out.println("Length         : " + s.length());
        System.out.println("charAt(1)      : " + s.charAt(1));
        System.out.println("substring      : " + s.substring(6));
        System.out.println("concat         : " + s.concat(" World"));
        System.out.println("contains       : " + s.contains("Java"));
        System.out.println("startsWith     : " + s.startsWith("Hello"));
        System.out.println("endsWith       : " + s.endsWith("Java"));
        System.out.println("indexOf        : " + s.indexOf("Java"));
        System.out.println("toUpperCase    : " + s.toUpperCase());
        System.out.println("toLowerCase    : " + s.toLowerCase());
        System.out.println("replace        : " + s.replace("Java", "Python"));
        System.out.println("isEmpty        : " + s.isEmpty());
        System.out.println("isBlank        : " + s.isBlank());

        String[] parts = s.split(" ");

        System.out.println("After split:");

        for (String part : parts) {
            System.out.println(part);
        }
    }
}


---

39. StringBuffer — Complete Concept

StringBuffer is a mutable sequence of characters.

Unlike String:

String s = "Java";

modifying the content creates/returns another String.

But:

StringBuffer sb = new StringBuffer("Java");

can be modified directly.

Example

StringBuffer sb = new StringBuffer("Java");

sb.append(" Programming");

System.out.println(sb);

Output:

Java Programming


---

Important StringBuffer Methods

append()

StringBuffer sb = new StringBuffer("Java");

sb.append(" Programming");

System.out.println(sb);

insert()

sb.insert(5, "Language ");

System.out.println(sb);

delete()

sb.delete(5, 14);

deleteCharAt()

sb.deleteCharAt(0);

replace()

sb.replace(0, 4, "Python");

reverse()

sb.reverse();

capacity()

System.out.println(sb.capacity());

length()

System.out.println(sb.length());

setCharAt()

sb.setCharAt(0, 'X');

charAt()

System.out.println(sb.charAt(0));

substring()

System.out.println(sb.substring(0, 4));


---

40. StringBuffer Program

class StringBufferDemo {

    public static void main(String[] args) {

        StringBuffer sb = new StringBuffer("Java");

        System.out.println("Original : " + sb);

        sb.append(" Programming");
        System.out.println("append   : " + sb);

        sb.insert(5, "Language ");
        System.out.println("insert   : " + sb);

        sb.delete(5, 14);
        System.out.println("delete   : " + sb);

        sb.replace(0, 4, "Python");
        System.out.println("replace  : " + sb);

        sb.setCharAt(0, 'J');
        System.out.println("setChar  : " + sb);

        sb.reverse();
        System.out.println("reverse  : " + sb);
    }
}

Key point:

> StringBuffer is mutable and synchronized, so it is generally safer for shared mutable text across threads, though synchronization can add overhead.




---

41. StringBuilder — Complete Concept

StringBuilder is also a mutable sequence of characters.

It is very similar to StringBuffer, but it is not synchronized.

Therefore, for ordinary single-threaded string modification, StringBuilder is generally preferred over StringBuffer because it usually has less synchronization overhead.

StringBuilder sb = new StringBuilder("Java");

sb.append(" Programming");

System.out.println(sb);

Output:

Java Programming


---

42. StringBuilder Program

class StringBuilderDemo {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder("Java");

        System.out.println("Original : " + sb);

        sb.append(" Programming");
        System.out.println("append   : " + sb);

        sb.insert(5, "Language ");
        System.out.println("insert   : " + sb);

        sb.delete(5, 14);
        System.out.println("delete   : " + sb);

        sb.replace(0, 4, "Python");
        System.out.println("replace  : " + sb);

        sb.setCharAt(0, 'J');
        System.out.println("setChar  : " + sb);

        sb.reverse();
        System.out.println("reverse  : " + sb);
    }
}


---

43. String vs StringBuffer vs StringBuilder

Feature	String	StringBuffer	StringBuilder

Mutable?	❌ No	✅ Yes	✅ Yes
Thread-safe through synchronization?	Immutable, hence inherently safe to share	✅ Yes	❌ No
Performance for repeated modification	Usually lower	Usually slower than Builder	Usually faster
Suitable for frequent modifications	❌	✅	✅
Synchronization	Not applicable	Synchronized methods	Not synchronized
Introduced	Java 1.0	Java 1.0	Java 5
Main use	Fixed text	Shared mutable text	General mutable text



---

44. 🔥 String Pool vs Heap — Final Picture

JVM
                   │
        ┌──────────┴──────────┐
        ↓                     ↓
   String Pool              Heap
        │                     │
     "Java"                "Java"
        ↑                     ↑
        │                     │
       s1                    s2

String s1 = "Java";
String s2 = new String("Java");

Therefore:

s1 == s2

→ false

But:

s1.equals(s2)

→ true


---

45. The Most Important String Doubts

❓ Does concat() modify the original String?

No.

String s = "Java";
s.concat(" World");

System.out.println(s);

Output:

Java


---

❓ Does new String("Java") create a String in the pool?

The literal "Java" is a pooled String if it is not already present, while new String("Java") creates a separate String object on the heap.


---

❓ Why is == sometimes true for Strings?

Because String literals are interned and can refer to the same pooled object.


---

❓ Why is .equals() usually preferred?

Because it checks contents, while == checks reference identity.


---

❓ Can a String object ever change its contents?

No.

That's the definition of String immutability.


---

❓ Is String a primitive data type?

No.

String s = "Java";

String is a class/reference type.


---

❓ Is String a keyword?

No.

String is a class name.


---

❓ Which is mutable?

String        → Immutable
StringBuffer  → Mutable
StringBuilder → Mutable


---

🏆 FINAL STRING CHEAT SHEET

STRING
│
├── String = class
│
├── Immutable
│
├── String Pool
│
├── Heap
│
├── ==       → reference identity
│
├── equals() → content equality
│
├── intern() → pooled/canonical String
│
├── split()  → String → String[]
│
├── StringBuffer
│      └── Mutable + synchronized
│
└── StringBuilder
       └── Mutable + not synchronized

Three common creation forms:

String s1 = "Java";
String s2 = new String("Java");
String s3 = new String(new char[]{'J','a','v','a'});

Most important methods:

length()
charAt()
substring()
concat()
equals()
equalsIgnoreCase()
compareTo()
compareToIgnoreCase()
contains()
startsWith()
endsWith()
indexOf()
lastIndexOf()
toUpperCase()
toLowerCase()
trim()
strip()
isEmpty()
isBlank()
replace()
replaceFirst()
replaceAll()
split()
toCharArray()
getBytes()
getChars()
valueOf()
format()
join()
repeat()
matches()
regionMatches()
contentEquals()
codePointAt()
codePointCount()
offsetByCodePoints()
subSequence()
toString()
hashCode()
transform()
indent()
stripIndent()
translateEscapes()
lines()
formatted()
intern()

> Golden rule: String objects are immutable; StringBuffer and StringBuilder are mutable. Use == when you intentionally care about object identity, and .equals() when you care about String contents.
