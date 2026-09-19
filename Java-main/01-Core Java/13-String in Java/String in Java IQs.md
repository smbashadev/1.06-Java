🔥 Strings in Java — DOUBTKILLER

This is the confusion-clearing version. The goal is to destroy the common doubts students have about String, String Pool, Heap, immutability, ==, .equals(), new, intern(), split(), StringBuffer, and StringBuilder.


---

1. ❓ Is String a Data Type or a Class?

Answer:

String is a class, not a primitive data type.

String s = "Java";

Here:

String → class
s      → reference variable
"Java" → String object/value

Primitive examples:

int
char
float
double
boolean

String is a reference type.


---

2. ❓ If String Is a Class, Why Don't We Use new?

Both are valid:

String s1 = "Java";

and:

String s2 = new String("Java");

The difference is mainly in how the object is obtained/created and where pooling is involved.

Literal

String s1 = "Java";

The JVM uses the String Pool for the literal.

new

String s2 = new String("Java");

Explicitly creates a distinct String object.


---

3. ❓ What Exactly Is the String Pool?

The String Pool is a JVM-managed pool of canonical String literals.

Example:

String a = "Java";
String b = "Java";

The same pooled "Java" can be reused:

String Pool

          "Java"
          /    \
         a      b

Therefore:

System.out.println(a == b);

Output:

true


---

4. ❓ Does Every String Object Go Into the String Pool?

No.

This is a very important distinction.

String s1 = "Java";

uses a pooled literal.

But:

String s2 = new String("Java");

creates a distinct String object.

Conceptually:

String Pool             Heap

"Java"                  "Java"
  ↑                       ↑
 s1                      s2


---

5. ❓ Does new String("Java") Create One Object or Two?

This is a classic interview question.

String s = new String("Java");

The safest explanation is:

"Java" is a String literal and is associated with the String Pool.

new String(...) creates a new distinct String object.


So if "Java" wasn't already present in the pool, the operation can involve the pooled literal plus the newly created object.

String Pool             Heap

"Java"                  new String("Java")
  ↑                           ↑
literal                    s

If "Java" is already in the pool, no additional pooled "Java" needs to be created.


---

6. ❓ What Is the Difference Between == and .equals()?

This is probably the #1 String doubt.

==

For references, checks whether both references identify the same object.

.equals()

For String, checks whether the contents are equal.

Example:

String a = new String("Java");
String b = new String("Java");

System.out.println(a == b);
System.out.println(a.equals(b));

Output:

false
true

Why?

a ───→ "Java"   Object 1

b ───→ "Java"   Object 2

Different objects:

a == b → false

Same contents:

a.equals(b) → true

🔥 Never forget:

> == → same object?
.equals() → same String content?




---

7. ❓ Why Does This Give true?

String a = "Java";
String b = "Java";

System.out.println(a == b);

Output:

true

Because both literals can refer to the same pooled String.

Pool

      "Java"
      /    \
     a      b


---

8. ❓ Why Does This Give false?

String a = "Java";
String b = new String("Java");

System.out.println(a == b);

Output:

false

Because:

Pool                    Heap

"Java"                  "Java"
  ↑                       ↑
  a                       b

Different objects.

But:

a.equals(b)

is:

true


---

9. ❓ What Does "String Is Immutable" Actually Mean?

Immutable means an existing String object's character contents cannot be changed.

Example:

String s = "Java";

s.concat(" Programming");

System.out.println(s);

Output:

Java

Why?

concat() produces another String.

The original "Java" wasn't modified.


---

10. ❓ Then How Does This Work?

String s = "Java";

s = s.concat(" Programming");

System.out.println(s);

Output:

Java Programming

Because the reference was reassigned.

Conceptually:

Before:

s ───→ "Java"


After:

s ───→ "Java Programming"

The original String wasn't mutated.

🔥 Key distinction

> Reference reassignment ≠ object mutation




---

11. ❓ Does toUpperCase() Modify the Original String?

No.

String s = "java";

s.toUpperCase();

System.out.println(s);

Output:

java

Because:

s.toUpperCase()

returns another String.

Correct:

s = s.toUpperCase();

Now:

JAVA


---

12. ❓ If the Reference Changes, Doesn't That Mean String Is Mutable?

No.

Consider:

String s = "Java";

s = "Python";

The variable s now refers to another object.

The "Java" object wasn't modified.

Before:

s ───→ "Java"


After:

s ───→ "Python"

The reference changed, not the String object.


---

13. ❓ Why Is String Immutable?

Important reasons include:

1. Security

Strings are widely used for values such as:

file paths
URLs
class names
configuration values

2. String Pool

Pooling relies on Strings being safely shareable.

3. Thread safety

Immutable objects can safely be shared without modifying their state.

4. Hash-based collections

Stable String content supports predictable hashing when Strings are used as keys.


---

14. ❓ Is final Responsible for String Immutability?

No.

This is a common misunderstanding.

String is immutable because of the design of the String class.

final has a different meaning.

final String s = "Java";

means:

> The reference s cannot be reassigned.



It doesn't mean String immutability comes from final.


---

15. ❓ What Is the Difference?

String s = "Java";

and:

final String s = "Java";

First

s = "Python";

Allowed.

Second

s = "Python";

Compile-time error.

But in both cases, the actual String objects remain immutable.


---

16. ❓ What Does intern() Do?

intern() returns the canonical representation of the String from the String Pool.

Example:

String s1 = new String("Java");
String s2 = s1.intern();
String s3 = "Java";

System.out.println(s2 == s3);

Output:

true

Conceptually:

Heap:

"Java" ← s1


Pool:

"Java" ← s2
          ↑
          s3


---

17. ❓ Does intern() Move the Object Into the Pool?

Not exactly.

This is an important correction.

String s1 = new String("Java");
String s2 = s1.intern();

intern() returns the canonical pooled String reference.

It does not turn the original s1 object into the pooled object.

Therefore:

s1 == s2

is normally:

false

while:

String s3 = "Java";

s2 == s3

is:

true


---

18. ❓ What Is the Difference Between intern() and new?

new String("Java")

→ creates a distinct String object.

s.intern()

→ returns the canonical pooled representation.

Think:

new
 ↓
Distinct object

intern()
 ↓
Pooled representation


---

19. ❓ What Happens Here?

String a = "Java";
String b = new String("Java");
String c = b.intern();
String d = "Java";

Memory model:

String Pool
                 │
               "Java"
              /  |  \
             a   c    d


                Heap
                 │
               "Java"
                 ↑
                 b

Therefore:

a == b   // false
a == c   // true
a == d   // true
b == c   // false
b == d   // false
c == d   // true

But:

a.equals(b) // true


---

20. ❓ What Does split() Return?

It returns a String array:

String[]

Example:

String s = "Java,Python,C++";

String[] arr = s.split(",");

System.out.println(arr[0]);
System.out.println(arr[1]);
System.out.println(arr[2]);

Output:

Java
Python
C++

Think:

"Java,Python,C++"
        ↓
     split(",")
        ↓
String[]
   ↓
["Java", "Python", "C++"]


---

21. ❓ Why Does split() Sometimes Behave Surprisingly?

Because the argument is a regular expression.

For example, . has a special meaning in regex.

Therefore:

"A.B.C".split(".")

doesn't mean "split at a literal dot."

Use:

"A.B.C".split("\\.")

for a literal dot.


---

22. ❓ What Is the Difference Between trim() and strip()?

Both remove leading/trailing whitespace, but they use different definitions.

trim()

Older method with narrower whitespace behavior.

strip()

Uses Unicode-aware whitespace rules.

String s = "  Java  ";

System.out.println(s.trim());
System.out.println(s.strip());

Both normally print:

Java


---

23. ❓ Difference Between isEmpty() and isBlank()?

String a = "";
String b = "   ";

isEmpty()

a.isEmpty(); // true
b.isEmpty(); // false

isBlank()

a.isBlank(); // true
b.isBlank(); // true

Remember:

isEmpty()
→ length == 0

isBlank()
→ empty or whitespace only


---

24. ❓ What Happens With null?

String s = null;

System.out.println(s.length());

Result:

NullPointerException

Because s doesn't refer to a String object.

Compare:

String s = "";

This is a real empty String.

null
↓
no object referenced

""
↓
empty String object


---

25. ❓ null vs ""?

null	""

No String object referenced	Empty String object
length() causes NPE	length() returns 0
isEmpty() cannot be called	isEmpty() returns true


Example:

String a = null;
String b = "";

System.out.println(b.length()); // 0

But:

a.length(); // NullPointerException


---

26. ❓ What Is StringBuffer?

StringBuffer is a mutable sequence of characters.

StringBuffer sb = new StringBuffer("Java");

sb.append(" Programming");

System.out.println(sb);

Output:

Java Programming

Unlike String:

String
 ↓
Immutable

StringBuffer:

StringBuffer
 ↓
Mutable


---

27. ❓ What Is StringBuilder?

StringBuilder is also mutable.

StringBuilder sb = new StringBuilder("Java");

sb.append(" Programming");

System.out.println(sb);

Output:

Java Programming


---

28. ❓ StringBuffer vs StringBuilder?

Feature	String	StringBuffer	StringBuilder

Mutable	❌	✅	✅
Synchronized	—	✅	❌
Best general use	Fixed text	Shared mutable text where synchronization is useful	Frequent modification in ordinary single-threaded code


Easy formula:

String
→ Immutable

StringBuffer
→ Mutable + synchronized

StringBuilder
→ Mutable + not synchronized


---

29. ❓ Why Use StringBuilder Instead of String?

Suppose you repeatedly build a String:

String s = "";

for (int i = 0; i < 1000; i++) {
    s = s + i;
}

Because String is immutable, repeated concatenation can create many intermediate Strings.

For repeated modifications, prefer:

StringBuilder sb = new StringBuilder();

for (int i = 0; i < 1000; i++) {
    sb.append(i);
}

This is generally more efficient for this kind of task.


---

30. ❓ Is StringBuffer Faster Than StringBuilder?

Generally, StringBuilder is faster in ordinary single-threaded use, because StringBuffer's methods are synchronized.

But don't reduce it to:

> "StringBuilder is always better."



Use based on the requirements.

Need mutable text
      ↓
Is synchronization required?
      ↓
Yes → StringBuffer
No  → StringBuilder


---

31. ❓ Can String Have Duplicate Values?

Yes.

Example:

String a = new String("Java");
String b = new String("Java");

Two distinct objects can have identical content.

a → "Java"

b → "Java"

Therefore:

a.equals(b) → true
a == b      → false


---

32. ❓ Can Duplicate String Literals Exist in the String Pool?

You can write the same literal many times:

String a = "Java";
String b = "Java";
String c = "Java";

But the pool can reuse the canonical pooled representation.

Conceptually:

"Java"
        /   |   \
       a    b    c

So don't think:

> "Three literals means three pooled objects."




---

33. ❓ Can String Objects Be Changed by StringBuffer?

No.

For example:

String s = "Java";

StringBuffer sb = new StringBuffer(s);

sb.append(" Programming");

sb changes.

s does not.

s
↓
"Java"

sb
↓
"Java Programming"

The String remains immutable.


---

34. ❓ What Is the Difference Between length() and capacity()?

For String:

String s = "Java";

s.length();

returns:

4

StringBuilder/StringBuffer also have length().

But mutable buffers additionally have:

capacity()

which refers to the currently allocated character-storage capacity, not the number of characters currently stored.

Example:

StringBuilder sb = new StringBuilder("Java");

System.out.println(sb.length());
System.out.println(sb.capacity());

The default capacity behavior is different from length().


---

35. ❓ What Is the Difference Between charAt() and substring()?

charAt()

Returns one character.

"Java".charAt(2)

→ 'v'

substring()

Returns a String portion.

"Java".substring(1, 3)

→ "av"

charAt()
→ char

substring()
→ String


---

36. ❓ Why Does substring(0, 4) Give Four Characters?

Because the ending index is exclusive.

String s = "Java";

Indexes:

J  a  v  a
 0  1  2  3

s.substring(0, 4)

takes:

0, 1, 2, 3

Result:

Java

Formula:

> start inclusive, end exclusive




---

37. ❓ What Does compareTo() Return?

It doesn't simply return true or false.

It returns an integer.

"A".compareTo("B")

→ negative

"A".compareTo("A")

→ 0

"B".compareTo("A")

→ positive

So:

negative → first < second
0        → equal
positive → first > second

Don't depend on the exact nonzero value; depend on its sign.


---

38. ❓ What Does replaceAll() Mean?

replaceAll() uses regular expressions.

String s = "Java123";

System.out.println(
    s.replaceAll("\\d", "")
);

Output:

Java

Here:

\d → digit


---

39. ❓ replace() vs replaceAll()?

replace()

Literal/character or CharSequence replacement.

"Java Java".replace("Java", "Python")

→

Python Python

replaceAll()

Regular-expression based replacement.

"Java123".replaceAll("\\d", "")

→

Java


---

40. ❓ Can We Modify a String Character Directly?

No.

This is invalid:

String s = "Java";

s.charAt(0) = 'P';

You cannot assign to the result of charAt().

Because String is immutable.

If you need mutable character manipulation, consider:

StringBuilder

or a character array.


---

41. ❓ What Happens Here?

String s = "Java";

s = s + " Programming";

Conceptually:

"Java"
   +
" Programming"
   ↓
new String
   ↓
s references result

The original "Java" was not modified.


---

42. ❓ Is StringBuilder Immutable?

No.

StringBuilder sb = new StringBuilder("Java");

sb.append(" World");

The builder itself is modified.

Before:
sb → "Java"

After:
sb → "Java World"


---

43. ❓ Is StringBuffer Immutable?

No.

StringBuffer is mutable.

StringBuffer sb = new StringBuffer("Java");

sb.append(" World");

The same buffer can be modified.


---

44. ❓ Is StringBuilder Thread-Safe?

No, not by synchronization.

StringBuffer provides synchronized methods.

Therefore:

StringBuffer → synchronized
StringBuilder → not synchronized

For modern code, choose based on actual concurrency requirements rather than simply assuming one is always superior.


---

45. 🔥 One Big Interview Program

Predict the output:

class Test {

    public static void main(String[] args) {

        String a = "Java";
        String b = "Java";
        String c = new String("Java");
        String d = c.intern();

        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(a == d);

        System.out.println(a.equals(b));
        System.out.println(a.equals(c));
        System.out.println(a.equals(d));
    }
}

Output

true
false
true

true
true
true

Why?

POOL

        "Java"
       /  |  \
      a   b   d


HEAP

        "Java"
          ↑
          c

Therefore:

a == b → true
a == c → false
a == d → true

But all contain "Java":

equals() → true


---

46. 🔥 Another Interview Trap

String s1 = "Ja" + "va";
String s2 = "Java";

System.out.println(s1 == s2);

For compile-time constant expressions, the compiler can fold the concatenation, so the result can be the same pooled literal.

Output:

true

But don't generalize this to every + expression involving variables.


---

47. 🔥 Another Trap

String x = "Java";
String y = new String("Java");

System.out.println(x == y);
System.out.println(x.equals(y));

Answer:

false
true

Never answer true for both just because the visible text is the same.


---

48. 🔥 Another Trap: null

String s = null;

System.out.println(s == null);

Output:

true

But:

System.out.println(s.equals("Java"));

causes:

NullPointerException

A safer pattern when appropriate:

"Java".equals(s)

This returns false if s is null.


---

49. 🔥 String Concept in One Diagram

STRING
                            │
                            ↓
                          Class
                            │
              ┌─────────────┴─────────────┐
              ↓                           ↓
         String Literal               new String()
              │                           │
              ↓                           ↓
        String Pool                 Distinct object
              │
              ↓
          Reuse possible
              │
              ↓
         IMMUTABLE
              │
       ┌──────┴──────┐
       ↓             ↓
      ==          equals()
       ↓             ↓
  Identity        Content
       
       intern()
           ↓
 Canonical pooled String


      Mutable alternatives
             │
       ┌─────┴─────┐
       ↓           ↓
StringBuffer   StringBuilder
       ↓           ↓
Mutable       Mutable
Sync          Not synchronized


---

50. 🏆 Ultimate Doubt Killer Table

Doubt	Correct Answer

String primitive?	❌ No, class/reference type
String mutable?	❌ No
String immutable?	✅ Yes
String literal uses pool?	✅ Yes
new String() creates distinct object?	✅ Yes
== checks String content?	❌ No
== checks reference identity?	✅ Yes
.equals() checks String content?	✅ Yes
intern() returns pooled representation?	✅ Yes
intern() modifies original String?	❌ No
split() returns?	String[]
charAt() returns?	char
substring() returns?	String
isEmpty() means whitespace only?	❌ No
isBlank() handles whitespace-only String?	✅ Yes
StringBuffer mutable?	✅ Yes
StringBuilder mutable?	✅ Yes
StringBuffer synchronized?	✅ Yes
StringBuilder synchronized?	❌ No
null same as ""?	❌ No
final String causes String immutability?	❌ No
Can String object be modified directly?	❌ No



---

🧠 FINAL MEMORY FORMULA

If you remember only this, remember:

STRING
  ↓
Class
  ↓
Immutable
  ↓
String Pool for literals
  ↓
==       → reference identity
equals() → content equality
  ↓
intern() → canonical pooled representation
  ↓
split() → String[]
  ↓
Need mutable text?
  ↓
StringBuffer → mutable + synchronized
StringBuilder → mutable + not synchronized

⭐ The most important exam sentence:

> A String is an immutable object of the String class. String literals can be stored and reused from the String Pool. == compares reference identity, whereas equals() compares String contents. For mutable character sequences, Java provides StringBuffer and StringBuilder.
