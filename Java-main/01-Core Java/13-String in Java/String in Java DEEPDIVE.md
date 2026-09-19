Strings in Java — DEEPDIVE

Strings are one of the most important topics in Java because they combine classes, objects, immutability, memory management, String Pool, ==, .equals(), intern(), methods, and mutable alternatives such as StringBuffer and StringBuilder.


---

1. What is a String in Java?

A String is an object of the java.lang.String class that represents a sequence of characters.

String s = "Java";

Here:

String  → class
s       → reference variable
"Java"  → String object/value

String is not a primitive data type.

Java primitive types are:

byte
short
int
long
float
double
char
boolean

String is a reference type.


---

2. Why Is String Special in Java?

Although String is a normal class, Java gives it special treatment.

For example:

String s1 = "Java";
String s2 = "Java";

Java can store the common literal "Java" in the String Pool and allow both references to use that same pooled object.

String Pool

               "Java"
               /    \
             s1      s2

This saves memory when the same String literals are used repeatedly.


---

3. String Is Immutable

Definition

Immutable means an object whose state/content cannot be changed after the object has been created.

String objects are immutable.

Consider:

String s = "Java";

s.concat(" Programming");

System.out.println(s);

Output:

Java

Why didn't concat() change s?

Because:

s.concat(" Programming");

creates another String; it doesn't modify the existing "Java" object.

If you want the reference to point to the newly created String:

String s = "Java";

s = s.concat(" Programming");

System.out.println(s);

Output:

Java Programming


---

4. The Most Important Immutability Example

Look carefully:

String s = "Java";

s = "Python";

Some beginners say:

> "s changed from Java to Python, so String is mutable."



❌ Wrong.

What actually happened?

Before:

s ─────→ "Java"


After:

s ─────→ "Python"

The reference s changed.

The "Java" object itself was never modified.

Therefore:

> Changing a reference is not the same as changing an object.




---

5. Why Is String Immutable?

String immutability provides several benefits:

1. Security

Strings are frequently used for:

file paths
URLs
class names
database connection information
network addresses

If their contents could unexpectedly change, security problems could occur.

2. String Pool

Because String objects cannot change, the JVM can safely share pooled String objects.

String a = "Java";
String b = "Java";

If Strings were mutable, changing a could unexpectedly affect b.

3. Thread safety

Immutable objects can safely be shared between threads without synchronization for their state.

4. Hashing

Strings are commonly used as keys in collections such as HashMap.

Their contents do not change after creation, which makes their hash-code behavior suitable for this purpose.


---

6. Three Common Ways to Create Strings

Way 1 — String Literal

String s1 = "Java";

The literal is stored in the String Pool.


---

Way 2 — new String()

String s2 = new String("Java");

This creates a distinct String object.

Conceptually:

String Pool              Heap

"Java"                   "Java"
   ↑                        ↑
 literal                  s2


---

Way 3 — Character Array

char[] ch = {'J', 'a', 'v', 'a'};

String s3 = new String(ch);

System.out.println(s3);

Output:

Java


---

7. Important: Are These Three Different Kinds of String?

No.

They all produce objects of:

java.lang.String

The difference is mainly how the String object is created and where references are obtained from.

And regardless of creation method:

> String objects are immutable.




---

8. String Pool

The String Pool is a special area associated with the JVM's handling of interned Strings.

Consider:

String s1 = "Java";
String s2 = "Java";

Java can reuse the same pooled object:

String Pool
                 │
              "Java"
              /    \
            s1      s2

Therefore:

System.out.println(s1 == s2);

Output:

true


---

9. Why Does == Return true?

Because == compares reference identity for objects.

In this example:

String s1 = "Java";
String s2 = "Java";

both references refer to the same pooled object.

Therefore:

s1 == s2

is:

true


---

10. Creating Duplicate Strings in the Heap

Now:

String s1 = "Java";
String s2 = new String("Java");

The literal is pooled, while new String() creates a distinct object.

Conceptually:

String Pool                  Heap

"Java"                       "Java"
  ↑                             ↑
 s1                            s2

Therefore:

System.out.println(s1 == s2);

Output:

false

But:

System.out.println(s1.equals(s2));

Output:

true

Because the contents are the same.


---

11. == vs .equals()

This is one of the most important String concepts.

Operator/Method	Checks	Example

==	Reference identity	Are these the same object?
.equals()	String contents	Do these Strings contain the same characters?


Example:

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

Different objects:

s1 == s2 → false

Same content:

s1.equals(s2) → true


---

12. Duplicate Strings in the String Pool

String s1 = "Java";
String s2 = "Java";

Output:

System.out.println(s1 == s2);
System.out.println(s1.equals(s2));

true
true

Because both refer to the same pooled String.


---

13. A Very Important Combination

String s1 = "Java";
String s2 = new String("Java");
String s3 = new String("Java");
String s4 = "Java";

Conceptually:

String Pool
                   │
                "Java"
                /    \
              s1      s4


                 Heap
               /      \
          "Java"      "Java"
             ↑           ↑
            s2          s3

Therefore:

s1 == s4        // true
s1 == s2        // false
s2 == s3        // false

s1.equals(s2)   // true
s2.equals(s3)   // true

This single example solves many String-memory questions.


---

14. intern() Method

The intern() method returns the canonical pooled representation of a String.

Example:

String s1 = new String("Java");

String s2 = s1.intern();

String s3 = "Java";

System.out.println(s2 == s3);

Output:

true

Why?

s1 → Heap "Java"

s2 ───────┐
          ↓
       Pool "Java" ←──── s3

intern() allows the reference to use the pooled representation.


---

15. Important intern() Example

String s1 = new String("Java");

System.out.println(s1 == "Java");

Usually:

false

But:

System.out.println(s1.intern() == "Java");

returns:

true


---

16. Does intern() Modify the Original String?

No.

String s1 = new String("Java");

String s2 = s1.intern();

s1 still refers to the original object.

intern() returns a pooled reference.


---

17. "String Doesn't Have Immutable Objects" — Correcting the Doubt

The statement:

> "Java String doesn't have immutable objects."



is incorrect.

The correct statement is:

> String objects are immutable.



The confusion usually comes from this:

String s = "Java";

s = "Python";

The String object didn't change.

The reference changed.


---

18. String Methods

Java's String class provides many methods for:

searching
comparing
extracting
replacing
splitting
converting
formatting
checking

Let's understand the important methods.


---

19. length()

Returns the number of characters.

String s = "Java";

System.out.println(s.length());

Output:

4

Important:

index → 0 1 2 3
value → J a v a
length → 4


---

20. charAt()

Returns the character at a specified index.

String s = "Java";

System.out.println(s.charAt(2));

Output:

v

Index starts from 0.


---

21. substring()

Extracts part of a String.

String s = "Java Programming";

System.out.println(s.substring(5));

Output:

Programming

Two-argument version:

System.out.println(s.substring(0, 4));

Output:

Java

The ending index is exclusive.


---

22. concat()

Combines Strings.

String s1 = "Java";
String s2 = " Programming";

System.out.println(s1.concat(s2));

Output:

Java Programming

Remember:

s1.concat(s2);

does not modify s1.


---

23. equals()

Compares contents.

String a = "Java";
String b = "Java";

System.out.println(a.equals(b));

Output:

true


---

24. equalsIgnoreCase()

Ignores case differences.

System.out.println("JAVA".equalsIgnoreCase("java"));

Output:

true


---

25. compareTo()

Performs lexicographic comparison.

System.out.println("A".compareTo("B"));

Output:

-1

General interpretation:

negative → first String comes before second
0        → equal
positive  → first String comes after second


---

26. compareToIgnoreCase()

System.out.println("JAVA".compareToIgnoreCase("java"));

Output:

0


---

27. contains()

Checks whether a sequence occurs in the String.

String s = "Java Programming";

System.out.println(s.contains("Java"));

Output:

true


---

28. startsWith()

System.out.println("Java Programming".startsWith("Java"));

Output:

true


---

29. endsWith()

System.out.println("Java Programming".endsWith("Programming"));

Output:

true


---

30. indexOf()

Returns the first occurrence.

String s = "Java Java";

System.out.println(s.indexOf("Java"));

Output:

0


---

31. lastIndexOf()

Returns the last occurrence.

System.out.println("Java Java".lastIndexOf("Java"));

Output:

5


---

32. Case Conversion

toUpperCase()

System.out.println("Java".toUpperCase());

Output:

JAVA

toLowerCase()

System.out.println("JAVA".toLowerCase());

Output:

java

These return Strings; they do not modify the original String.


---

33. trim() vs strip()

trim()

Removes leading/trailing characters traditionally treated as ASCII spaces.

String s = "  Java  ";

System.out.println(s.trim());

strip()

Uses Unicode whitespace rules.

System.out.println(s.strip());

Both commonly produce:

Java


---

34. isEmpty() vs isBlank()

This is a common interview question.

isEmpty()

True only when length is zero.

"".isEmpty()

→ true

But:

"   ".isEmpty()

→ false

isBlank()

True when empty or containing only whitespace.

"   ".isBlank()

→ true

Remember:

isEmpty → no characters
isBlank → no meaningful non-whitespace characters


---

35. replace()

String s = "Java Java";

System.out.println(s.replace("Java", "Python"));

Output:

Python Python

replace() works with literal character/sequence replacement rather than regex.


---

36. replaceFirst()

Uses a regular expression and replaces the first match.

String s = "Java Java";

System.out.println(s.replaceFirst("Java", "Python"));

Output:

Python Java


---

37. replaceAll()

Uses regular expressions and replaces all matches.

String s = "Java123";

System.out.println(s.replaceAll("\\d", ""));

Output:

Java


---

38. split() — Deep Understanding

split() is extremely important.

It divides a String into a String[].

String s = "Java,Python,C++";

String[] arr = s.split(",");

for (String x : arr) {
    System.out.println(x);
}

Output:

Java
Python
C++

Conceptually:

Java,Python,C++
     ↓
    split(",")
     ↓
┌────────┬────────┬─────┐
│ Java   │ Python │ C++ │
└────────┴────────┴─────┘


---

39. Why Does split() Use a Regular Expression?

The delimiter supplied to split() is a regular expression.

For simple delimiters:

"Java-Python-C++".split("-")

is straightforward.

But . has special regex meaning.

So:

"A.B.C".split("\\.")

is appropriate.


---

40. split() with Limit

You can specify a limit:

String s = "A-B-C-D";

String[] a = s.split("-", 2);

Result:

A
B-C-D

The limit controls how many pieces are produced.


---

41. toCharArray()

Converts String to a character array.

String s = "Java";

char[] chars = s.toCharArray();

for (char c : chars) {
    System.out.println(c);
}


---

42. getChars()

Copies a range of characters into an existing array.

String s = "Java";

char[] ch = new char[4];

s.getChars(0, 4, ch, 0);

System.out.println(ch);

Output:

Java


---

43. getBytes()

Converts a String to bytes.

For predictable encoding:

import java.nio.charset.StandardCharsets;

String s = "Java";

byte[] bytes = s.getBytes(StandardCharsets.UTF_8);


---

44. valueOf()

Converts primitive/reference values to String representations.

int n = 100;

String s = String.valueOf(n);

System.out.println(s);

Output:

100


---

45. format()

String s = String.format(
    "Name: %s, Age: %d",
    "Ravi",
    20
);

System.out.println(s);

Output:

Name: Ravi, Age: 20


---

46. formatted()

The instance-oriented form:

String s = "Name: %s, Age: %d".formatted("Ravi", 20);

System.out.println(s);


---

47. join()

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

48. repeat()

System.out.println("Java ".repeat(3));

Conceptually:

Java Java Java


---

49. matches()

Checks whether the entire String matches a regular expression.

String s = "12345";

System.out.println(s.matches("\\d+"));

Output:

true


---

50. regionMatches()

Compares portions of Strings.

String s1 = "HelloJava";
String s2 = "Java";

System.out.println(
    s1.regionMatches(5, s2, 0, 4)
);

Output:

true

Meaning:

Hello[Java]
     ↑
     Compare with
     [Java]


---

51. contentEquals()

Can compare a String with another character sequence.

String s = "Java";

StringBuilder sb = new StringBuilder("Java");

System.out.println(s.contentEquals(sb));

Output:

true


---

52. Unicode Methods

Important methods include:

codePointAt()
codePointBefore()
codePointCount()
offsetByCodePoints()
chars()
codePoints()

For example:

String s = "Java";

System.out.println(s.codePointAt(0));

J has Unicode code point:

74


---

53. lines()

Creates a stream of lines.

String s = """
Java
Python
C++
""";

s.lines().forEach(System.out::println);

Output:

Java
Python
C++


---

54. transform()

Allows a function to transform a String.

String result =
    "java".transform(x -> x.toUpperCase());

System.out.println(result);

Output:

JAVA


---

55. indent()

String s = "Java";

System.out.print(s.indent(4));

It adds indentation according to the specified amount.


---

56. stripIndent()

Useful for removing incidental indentation from multi-line Strings.

String s = """
        Java
        Python
        """;

System.out.println(s.stripIndent());


---

57. translateEscapes()

Processes escape sequences contained in the String.

String s = "Java\\nProgramming";

System.out.println(s.translateEscapes());

Output:

Java
Programming


---

58. StringBuffer

StringBuffer is a mutable sequence of characters.

StringBuffer sb = new StringBuffer("Java");

sb.append(" Programming");

System.out.println(sb);

Output:

Java Programming

Unlike String, the same StringBuffer object can be modified.


---

59. Important StringBuffer Methods

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

capacity()

System.out.println(sb.capacity());

length()

System.out.println(sb.length());


---

60. StringBuffer Complete Program

class StringBufferDemo {

    public static void main(String[] args) {

        StringBuffer sb = new StringBuffer("Java");

        System.out.println("Original : " + sb);

        sb.append(" Programming");
        System.out.println("Append   : " + sb);

        sb.insert(5, "Language ");
        System.out.println("Insert   : " + sb);

        sb.delete(5, 14);
        System.out.println("Delete   : " + sb);

        sb.replace(0, 4, "Python");
        System.out.println("Replace  : " + sb);

        sb.setCharAt(0, 'J');
        System.out.println("SetChar  : " + sb);

        System.out.println("Length   : " + sb.length());
        System.out.println("Capacity : " + sb.capacity());

        sb.reverse();
        System.out.println("Reverse  : " + sb);
    }
}


---

61. Why Does StringBuffer Exist?

Suppose you repeatedly modify text.

Using String:

String s = "";

s = s + "Java";
s = s + " Python";
s = s + " C++";

Many intermediate String objects may be created.

For frequent modifications, a mutable class is more appropriate:

StringBuffer sb = new StringBuffer();

sb.append("Java");
sb.append(" Python");
sb.append(" C++");


---

62. StringBuilder

StringBuilder is another mutable character sequence.

StringBuilder sb = new StringBuilder("Java");

sb.append(" Programming");

System.out.println(sb);

Output:

Java Programming


---

63. StringBuilder vs StringBuffer

The biggest difference:

StringBuffer
    ↓
Mutable
    ↓
Synchronized

StringBuilder
    ↓
Mutable
    ↓
Not synchronized

Therefore:

StringBuilder

Generally preferred when:

working in ordinary single-threaded code

performing many String modifications

synchronization isn't required


StringBuffer

Useful when:

you specifically need its synchronized API for shared mutable text



---

64. StringBuilder Complete Program

class StringBuilderDemo {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder("Java");

        System.out.println("Original : " + sb);

        sb.append(" Programming");
        System.out.println("Append   : " + sb);

        sb.insert(5, "Language ");
        System.out.println("Insert   : " + sb);

        sb.delete(5, 14);
        System.out.println("Delete   : " + sb);

        sb.replace(0, 4, "Python");
        System.out.println("Replace  : " + sb);

        sb.setCharAt(0, 'J');
        System.out.println("SetChar  : " + sb);

        System.out.println("Length   : " + sb.length());
        System.out.println("Capacity : " + sb.capacity());

        sb.reverse();
        System.out.println("Reverse  : " + sb);
    }
}


---

65. String vs StringBuffer vs StringBuilder

Property	String	StringBuffer	StringBuilder

Mutable	❌	✅	✅
Modification	Creates new String when content needs to change	Modifies existing buffer	Modifies existing builder
Thread synchronization	Immutable state is inherently safe to share	Synchronized	Not synchronized
Typical performance for repeated edits	Lowest suitability	Generally slower than Builder	Generally fastest of these three
Best use	Fixed/mostly-fixed text	Shared mutable text where synchronization is useful	Frequent modification in ordinary single-threaded code



---

66. String Concatenation

You can concatenate using +:

String s = "Java" + " Programming";

For compile-time constant literals, Java can optimize the concatenation.

With variables:

String a = "Java";
String b = " Programming";

String c = a + b;

The compiler/runtime uses appropriate concatenation machinery; conceptually, don't assume every + operation literally means repeated creation of a manually visible StringBuilder.

For explicit repeated modifications, use StringBuilder.


---

67. String and final

Don't confuse:

final String s = "Java";

with String immutability.

Two separate concepts:

String immutability

The String object's contents cannot change.

final reference

The reference cannot be reassigned.

For example:

final String s = "Java";

You cannot do:

s = "Python";

because s is final.

But even without final:

String s = "Java";
s = "Python";

is legal because you're changing the reference, not the String object.


---

68. null vs Empty String

These are completely different:

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

69. Empty String vs Blank String

String a = "";
String b = "   ";

a.isEmpty() → true
b.isEmpty() → false

a.isBlank() → true
b.isBlank() → true

Remember:

EMPTY → zero characters

BLANK → empty or only whitespace


---

70. Complete String Methods Map

Character/access

length()
charAt()
codePointAt()
codePointBefore()
codePointCount()
offsetByCodePoints()
getChars()
toCharArray()
getBytes()

Comparison

equals()
equalsIgnoreCase()
compareTo()
compareToIgnoreCase()
regionMatches()
contentEquals()

Searching

contains()
indexOf()
lastIndexOf()
startsWith()
endsWith()

Extraction

substring()
subSequence()

Modification-style operations

Remember: String remains immutable.

concat()
replace()
replaceFirst()
replaceAll()
toUpperCase()
toLowerCase()
trim()
strip()
stripLeading()
stripTrailing()
repeat()

Splitting

split()
lines()

Conversion/formatting

valueOf()
format()
formatted()
join()
toString()

Checking

isEmpty()
isBlank()
matches()

Pool

intern()

Modern String operations

transform()
indent()
stripIndent()
translateEscapes()


---

71. Complete Demonstration Program

class StringDeepDive {

    public static void main(String[] args) {

        String s = "Hello Java";

        System.out.println("String       : " + s);
        System.out.println("Length       : " + s.length());
        System.out.println("charAt       : " + s.charAt(1));
        System.out.println("substring    : " + s.substring(6));
        System.out.println("concat       : " + s.concat(" World"));
        System.out.println("contains     : " + s.contains("Java"));
        System.out.println("startsWith   : " + s.startsWith("Hello"));
        System.out.println("endsWith     : " + s.endsWith("Java"));
        System.out.println("indexOf      : " + s.indexOf("Java"));
        System.out.println("lastIndexOf  : " + s.lastIndexOf("Java"));
        System.out.println("upper        : " + s.toUpperCase());
        System.out.println("lower        : " + s.toLowerCase());
        System.out.println("replace      : " + s.replace("Java", "Python"));
        System.out.println("empty        : " + s.isEmpty());
        System.out.println("blank        : " + s.isBlank());

        String[] parts = s.split(" ");

        System.out.println("\nAfter split:");

        for (String part : parts) {
            System.out.println(part);
        }

        char[] chars = s.toCharArray();

        System.out.println("\nCharacters:");

        for (char c : chars) {
            System.out.println(c);
        }
    }
}


---

72. Interview-Level Memory Diagram

Suppose:

String s1 = "Java";
String s2 = "Java";
String s3 = new String("Java");
String s4 = s3.intern();

Think:

JVM
                     │
             ┌───────┴───────┐
             ↓               ↓
       String Pool          Heap
             │               │
          "Java"           "Java"
          /    \              ↑
        s1      s2            s3
          ↑
          │
         s4

Therefore:

s1 == s2       // true
s1 == s3       // false
s1 == s4       // true
s3 == s4       // false

s1.equals(s3)  // true
s3.equals(s4)  // true

This is the core String Pool + new + intern() problem.


---

73. Ultimate String Mental Model

Whenever you see:

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
String Pool may be involved

Whenever you see:

new String("Java")

think:

String literal → Pool
       +
new object     → Heap

Whenever you see:

==

think:

> Same object/reference?



Whenever you see:

.equals()

think:

> Same content?



Whenever you see:

.intern()

think:

> Give me the canonical pooled String.



Whenever you see:

StringBuffer
StringBuilder

think:

> Mutable character sequence.



And finally:

String        → Immutable
StringBuffer  → Mutable + synchronized
StringBuilder → Mutable + not synchronized

🏆 One sentence to remember everything

> String is an immutable class whose objects represent character sequences; Java can share String literals through the String Pool, == checks reference identity, .equals() checks content equality, intern() obtains the pooled representation, and StringBuffer/StringBuilder provide mutable alternatives for repeated modifications.
