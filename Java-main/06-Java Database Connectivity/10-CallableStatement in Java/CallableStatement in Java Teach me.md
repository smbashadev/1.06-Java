# 10. CallableStatement in Java — TEACHME

Let's learn `CallableStatement` **from zero**, as if this is your first time seeing it.

The goal is not just to memorize methods. By the end, you should understand:

```text
Why CallableStatement?
        ↓
What is a Stored Procedure?
        ↓
How Java calls it
        ↓
How values go IN
        ↓
How values come OUT
        ↓
How INOUT works
        ↓
Why registerOutParameter() is necessary
```

---

# 1. First Understand the Problem

Imagine we have a `student` table:

```text
student
--------------------------------
id       name       marks
--------------------------------
101      Ravi       85
102      Kumar      90
103      John       75
```

Suppose Java wants to ask the database:

> "Give me the name of the student whose ID is 101."

One way is to directly send SQL from Java:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT name FROM student WHERE id = ?"
    );

ps.setInt(1, 101);
```

That's normal JDBC.

But databases can also contain **stored procedures**.

For example, the database might have a procedure called:

```text
getStudentName
```

Java can simply say:

```text
"Database, execute getStudentName."
```

That's where **CallableStatement** comes in.

---

# 2. CallableStatement

## 2.1 What is CallableStatement?

`CallableStatement` is a JDBC interface used to **call stored procedures and stored functions** in a database.

It belongs to:

```java
java.sql
```

So:

```java
java.sql.CallableStatement
```

---

## 2.2 Why "Callable"?

Think about the word:

```text
Callable
   ↓
Something that can be called
```

A stored procedure is something that can be called.

Therefore:

```text
Java
  ↓
CallableStatement
  ↓
CALL
  ↓
Stored Procedure
```

---

# 2.3 How do we create CallableStatement?

We use:

```java
Connection.prepareCall()
```

Example:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudentName(?)}"
    );
```

Notice the important method:

```java
prepareCall()
```

not:

```java
prepareStatement()
```

### Remember

```text
SQL statement
     ↓
prepareStatement()

Stored procedure/function
     ↓
prepareCall()
```

---

# 2.4 Simple example

Suppose the database contains:

```text
getStudentName(101)
```

Java:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudentName(?)}"
    );

cs.setInt(1, 101);

cs.execute();
```

The overall flow is:

```text
Java
 ↓
CallableStatement
 ↓
JDBC Driver
 ↓
Database
 ↓
getStudentName(101)
```

---

# 2.5 What is the `?`

Look at:

```java
"{call getStudentName(?)}"
```

The `?` represents a **parameter**.

So:

```text
getStudentName(?)
              ↑
          parameter
```

Then:

```java
cs.setInt(1, 101);
```

puts `101` into that parameter.

So conceptually:

```text
Before:

getStudentName(?)


After:

getStudentName(101)
```

---

# 2.6 CallableStatement hierarchy

You can remember it like this:

```text
Statement
    ↑
PreparedStatement
    ↑
CallableStatement
```

So `CallableStatement` is related to the functionality provided by `PreparedStatement`, but its special purpose is calling stored procedures/functions.

---

# 3. Stored Procedures

Now let's understand what we're actually calling.

## 3.1 What is a stored procedure?

A **stored procedure** is a named program stored inside the database.

It can contain database operations such as:

```text
SELECT
INSERT
UPDATE
DELETE
conditions
loops
calculations
etc.
```

The exact syntax depends on the database.

---

# 3.2 Think of a stored procedure as a database method

This analogy makes it much easier.

A Java method:

```java
void printStudent(int id) {
    // logic
}
```

has:

```text
method name
parameters
logic
```

A stored procedure similarly has:

```text
procedure name
parameters
database logic
```

For example:

```text
getStudentName
       ↓
    parameter
       ↓
   database logic
```

So you can initially think:

> **Stored procedure = method stored inside the database.**

It isn't literally a Java method, but this analogy is excellent for learning.

---

# 3.3 Example stored procedure

Database-side example:

```sql
CREATE PROCEDURE getStudentName(
    IN p_id INT,
    OUT p_name VARCHAR(100)
)
BEGIN
    SELECT name
    INTO p_name
    FROM student
    WHERE id = p_id;
END;
```

Don't worry about every SQL detail yet.

Focus on:

```text
getStudentName
      ↓
IN p_id
      ↓
OUT p_name
```

The procedure receives an ID and returns a name.

---

# 3.4 Calling the procedure from Java

Java:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudentName(?, ?)}"
    );
```

There are two `?` parameters:

```text
              ?       ?
              ↓       ↓
            param1  param2
```

Then:

```java
cs.setInt(1, 101);
```

means:

```text
parameter 1 = 101
```

And parameter 2 will be an output.

---

# 3.5 Complete flow

```text
                  DATABASE
             ┌────────────────┐
             │ getStudentName │
             │                │
             │ IN  p_id       │
             │ OUT p_name     │
             └───────▲────────┘
                     │
                     │
               JDBC Driver
                     ▲
                     │
              CallableStatement
                     ▲
                     │
              Java Application
```

---

# 4. IN Parameters

Now let's understand parameters one at a time.

## 4.1 What is an IN parameter?

An `IN` parameter is used to **send a value from Java to the stored procedure**.

Direction:

```text
Java
  ↓
Database
```

That's the entire idea.

---

# 4.2 Real-world analogy

Imagine calling a restaurant:

```text
You → Restaurant
"Give me 2 pizzas."
```

You are sending information **into** the restaurant.

That's like an:

```text
IN parameter
```

---

# 4.3 Example

Procedure:

```sql
CREATE PROCEDURE getStudent(
    IN p_id INT
)
BEGIN
    SELECT *
    FROM student
    WHERE id = p_id;
END;
```

Java:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

There is one parameter:

```text
?
↓
parameter 1
```

Set it:

```java
cs.setInt(1, 101);
```

Now:

```text
Java
 ↓
101
 ↓
IN parameter
 ↓
getStudent()
```

---

# 4.4 Why `setInt()`?

Because the parameter is an integer:

```sql
p_id INT
```

Therefore:

```java
cs.setInt(1, 101);
```

If it were a string:

```sql
p_name VARCHAR(100)
```

you could use:

```java
cs.setString(1, "Ravi");
```

If it were a double:

```java
cs.setDouble(1, 85.5);
```

---

# 4.5 Common setters

```java
cs.setInt(1, 101);

cs.setString(2, "Ravi");

cs.setDouble(3, 85.5);
```

The first argument is the parameter number.

The second argument is the value.

```text
setInt(1, 101)
       ↑   ↑
       │   └── value
       └────── parameter number
```

---

# 4.6 Parameter numbering

JDBC parameters start from **1**, not 0.

For:

```java
"{call test(?, ?, ?)}"
```

we have:

```text
?       ?       ?
↓       ↓       ↓
1       2       3
```

Therefore:

```java
cs.setInt(1, 101);
cs.setString(2, "Ravi");
cs.setDouble(3, 85.5);
```

---

# 4.7 Does IN require `registerOutParameter()`?

No.

For a pure `IN` parameter:

```java
cs.setInt(1, 101);
```

is what you need.

Remember:

```text
IN
↓
set
```

---

# 5. OUT Parameters

Now let's reverse the direction.

## 5.1 What is an OUT parameter?

An `OUT` parameter sends a value **from the database procedure back to Java**.

Direction:

```text
Database
   ↓
Java
```

---

# 5.2 Real-world analogy

Imagine you ask:

```text
You → Restaurant:
"What is my bill?"
```

Restaurant:

```text
Restaurant → You:
"₹500"
```

You didn't give the restaurant the bill amount.

The restaurant **produced** it.

That's similar to an:

```text
OUT parameter
```

---

# 5.3 Example

Suppose our procedure is:

```sql
CREATE PROCEDURE getStudentName(
    IN p_id INT,
    OUT p_name VARCHAR(100)
)
BEGIN
    SELECT name
    INTO p_name
    FROM student
    WHERE id = p_id;
END;
```

There are two parameters:

```text
1 → IN
2 → OUT
```

---

# 5.4 Java code

First create the `CallableStatement`:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudentName(?, ?)}"
    );
```

Set the IN value:

```java
cs.setInt(1, 101);
```

Now parameter 2 is OUT.

So register it:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

Execute:

```java
cs.execute();
```

Finally retrieve:

```java
String name =
    cs.getString(2);
```

---

# 5.5 Understand the four lines

These four lines are extremely important:

```java
cs.setInt(1, 101);
```

Means:

```text
Give parameter 1 the value 101.
```

Then:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

Means:

```text
Parameter 2 will return a VARCHAR value.
```

Then:

```java
cs.execute();
```

Means:

```text
Run the stored procedure.
```

Then:

```java
String name =
    cs.getString(2);
```

Means:

```text
Give me the value returned through parameter 2.
```

---

# 5.6 The complete flow

```text
Java
 │
 │ 101
 ↓
IN parameter #1
 │
 ↓
Stored Procedure
 │
 │ "Ravi"
 ↓
OUT parameter #2
 │
 ↓
Java
```

---

# 5.7 Why can't we simply use `getString()`?

Suppose you write:

```java
String name =
    cs.getString(2);
```

How does JDBC know that parameter 2 is an output parameter?

That's why we first do:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

This tells JDBC:

```text
Parameter 2
     ↓
is an OUT parameter
     ↓
its SQL type is VARCHAR
```

---

# 6. INOUT Parameters

Now we combine both.

## 6.1 What is INOUT?

`INOUT` means:

```text
Input + Output
```

The value travels:

```text
Java
 ↓
Database
 ↓
Java
```

---

# 6.2 Real-world analogy

Imagine you give a mechanic:

```text
Car with 50,000 km
```

The mechanic changes something and returns:

```text
Car with updated information
```

The same piece of information travels in and comes back modified.

That's the idea of `INOUT`.

---

# 6.3 Example

Suppose we have:

```sql
CREATE PROCEDURE increaseMarks(
    INOUT p_marks INT
)
BEGIN
    SET p_marks = p_marks + 5;
END;
```

Java sends:

```text
80
```

Procedure changes it:

```text
80 + 5 = 85
```

Java gets:

```text
85
```

---

# 6.4 Java code

```java
CallableStatement cs =
    con.prepareCall(
        "{call increaseMarks(?)}"
    );
```

Set the initial value:

```java
cs.setInt(1, 80);
```

Register it as output:

```java
cs.registerOutParameter(
    1,
    Types.INTEGER
);
```

Execute:

```java
cs.execute();
```

Retrieve:

```java
int marks =
    cs.getInt(1);
```

Print:

```java
System.out.println(marks);
```

Output:

```text
85
```

---

# 6.5 Why do we need both methods?

Because `INOUT` has two directions.

### Input

```java
cs.setInt(1, 80);
```

means:

```text
Java → Database
```

### Output

```java
cs.registerOutParameter(
    1,
    Types.INTEGER
);
```

means:

```text
Database → Java
```

Then:

```java
cs.execute();
```

runs the procedure.

And:

```java
cs.getInt(1);
```

gets the changed value.

---

# 6.6 INOUT flow

```text
             80
              │
              ↓
            Java
              │
              ↓
        INOUT parameter
              │
              ↓
       Stored Procedure
              │
           + 5
              │
              ↓
             85
              │
              ↓
            Java
```

---

# 7. `registerOutParameter()`

Now let's focus completely on this method.

## 7.1 What is it?

`registerOutParameter()` tells JDBC:

> "This parameter will produce an output value, and this is the SQL type of that output."

Example:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

---

# 7.2 Why is it necessary?

Suppose we have:

```text
Parameter 1 → IN
Parameter 2 → OUT
```

Java needs to communicate that information to JDBC.

So:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

is effectively telling JDBC:

```text
Parameter #2
      ↓
OUTPUT
      ↓
SQL type = VARCHAR
```

---

# 7.3 First argument

```java
2
```

is the parameter index.

Example:

```java
"{call test(?, ?, ?)}"
```

means:

```text
Parameter 1 → ?
Parameter 2 → ?
Parameter 3 → ?
```

So:

```java
cs.registerOutParameter(
    3,
    Types.INTEGER
);
```

registers parameter 3.

---

# 7.4 Second argument

```java
Types.INTEGER
```

is the SQL type.

For example:

```java
Types.INTEGER
Types.VARCHAR
Types.DOUBLE
Types.DECIMAL
Types.DATE
Types.TIMESTAMP
```

---

# 7.5 Example

Suppose:

```text
parameter 2 → OUT → VARCHAR
```

Write:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

Then retrieve:

```java
String result =
    cs.getString(2);
```

---

# 7.6 `registerOutParameter()` does NOT retrieve the value

This is one of the biggest beginner doubts.

This:

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);
```

does **not** mean:

```text
"Give me the value."
```

It means:

```text
"Register parameter 2 as an output parameter."
```

Retrieval happens using:

```java
cs.getInt(2);
```

after execution.

So:

```text
registerOutParameter()
        ↓
Tell JDBC about output

execute()
        ↓
Run procedure

getXXX()
        ↓
Retrieve output
```

---

# 8. The Three Parameter Types Together

Let's put everything together.

Suppose:

```text
procedure calculate(
    IN a,
    OUT b,
    INOUT c
)
```

The flow is:

```text
                   PROCEDURE
              ┌─────────────────┐
              │                 │
Java ──a─────→│ IN              │
              │                 │
Java ←────b───│ OUT             │
              │                 │
Java ──c─────→│ INOUT           │
Java ←────c───│                 │
              │                 │
              └─────────────────┘
```

Java operations:

```text
IN
 ↓
setXXX()

OUT
 ↓
registerOutParameter()
 ↓
getXXX()

INOUT
 ↓
setXXX()
 ↓
registerOutParameter()
 ↓
getXXX()
```

---

# 9. Complete Example From Start to Finish

Let's build a complete conceptual example.

Suppose the database has:

```text
student
--------------------------------
id       name       marks
--------------------------------
101      Ravi       85
102      Kumar      90
```

And a stored procedure:

```sql
CREATE PROCEDURE getStudentName(
    IN p_id INT,
    OUT p_name VARCHAR(100)
)
BEGIN
    SELECT name
    INTO p_name
    FROM student
    WHERE id = p_id;
END;
```

Java:

```java
import java.sql.*;

public class Demo {

    public static void main(String[] args)
            throws Exception {

        Connection con =
            DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testdb",
                "root",
                "password"
            );

        CallableStatement cs =
            con.prepareCall(
                "{call getStudentName(?, ?)}"
            );

        // IN parameter
        cs.setInt(1, 101);

        // OUT parameter
        cs.registerOutParameter(
            2,
            Types.VARCHAR
        );

        // Execute
        cs.execute();

        // Get OUT value
        String name =
            cs.getString(2);

        System.out.println(
            "Student Name: " + name
        );

        cs.close();
        con.close();
    }
}
```

Output:

```text
Student Name: Ravi
```

---

# 10. Let's Read This Program Like a Teacher

### Step 1

```java
Connection con = ...
```

We establish a connection with the database.

```text
Java → Database
```

---

### Step 2

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudentName(?, ?)}"
    );
```

We tell JDBC:

> "I want to call the stored procedure `getStudentName`, and it has two parameters."

---

### Step 3

```java
cs.setInt(1, 101);
```

Parameter 1 is an IN parameter.

So:

```text
101
 ↓
Java → Database
```

---

### Step 4

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

Parameter 2 is an OUT parameter.

So:

```text
Database → Java
```

And its type is:

```text
VARCHAR
```

---

### Step 5

```java
cs.execute();
```

Now the database actually executes the procedure.

---

### Step 6

```java
String name =
    cs.getString(2);
```

Now Java asks:

> "Give me the value returned through parameter 2."

---

# 11. The Most Important Pattern

Memorize this:

## IN

```java
cs.setXXX(index, value);
```

Then:

```java
cs.execute();
```

---

## OUT

```java
cs.registerOutParameter(
    index,
    Types.TYPE
);

cs.execute();

cs.getXXX(index);
```

---

## INOUT

```java
cs.setXXX(index, value);

cs.registerOutParameter(
    index,
    Types.TYPE
);

cs.execute();

cs.getXXX(index);
```

---

# 12. IN vs OUT vs INOUT — Easy Comparison

|                          | IN        | OUT       | INOUT            |
| ------------------------ | --------- | --------- | ---------------- |
| Java sends value?        | ✅         | ❌         | ✅                |
| Database returns value?  | ❌         | ✅         | ✅                |
| Direction                | Java → DB | DB → Java | Java → DB → Java |
| `setXXX()`               | ✅         | ❌         | ✅                |
| `registerOutParameter()` | ❌         | ✅         | ✅                |
| `getXXX()`               | ❌         | ✅         | ✅                |

### Super-simple memory trick

```text
IN
= Give

OUT
= Get

INOUT
= Give + Get
```

---

# 13. CallableStatement vs PreparedStatement

This confusion is very common.

### PreparedStatement

Used mainly for parameterized SQL:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

### CallableStatement

Used for stored procedures/functions:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

Think:

```text
SQL
 ↓
PreparedStatement

Procedure / Function
 ↓
CallableStatement
```

---

# 14. CallableStatement vs ResultSet

Another important distinction.

`CallableStatement`:

```text
Used to CALL
```

`ResultSet`:

```text
Used to READ tabular results
```

For example:

```text
CallableStatement
       ↓
calls procedure
       ↓
ResultSet
       ↓
reads rows
```

A procedure can also return output through an OUT parameter:

```text
CallableStatement
       ↓
calls procedure
       ↓
OUT parameter
       ↓
getXXX()
```

Therefore:

```text
ResultSet ≠ OUT parameter
```

---

# 15. Common Beginner Mistakes

## Mistake 1

Using:

```java
prepareStatement()
```

instead of:

```java
prepareCall()
```

For a stored procedure, normally use:

```java
con.prepareCall(...)
```

---

## Mistake 2

Forgetting:

```java
registerOutParameter()
```

For an OUT parameter, register it before execution.

---

## Mistake 3

Trying to retrieve before executing:

```java
cs.getString(2);
```

before:

```java
cs.execute();
```

The procedure needs to execute first.

Correct:

```text
register
 ↓
execute
 ↓
get
```

---

## Mistake 4

Thinking `registerOutParameter()` gives the value.

It doesn't.

```text
registerOutParameter()
→ register output

getXXX()
→ retrieve output
```

---

## Mistake 5

Forgetting that parameter indexes start at 1.

Correct:

```java
cs.setInt(1, 101);
```

Not:

```java
cs.setInt(0, 101);
```

---

# 16. Final Mental Picture

Imagine a door between Java and the database.

```text
                  JAVA
                   │
          ┌────────┼────────┐
          │        │        │
          ↓        ↓        ↓
         IN       OUT      INOUT
          │        ↑        ↕
          │        │        │
          └────────┼────────┘
                   ↓
             JDBC Driver
                   ↓
           STORED PROCEDURE
                   ↓
               DATABASE
```

### `IN`

```text
Java ─────────→ Database
```

### `OUT`

```text
Java ←───────── Database
```

### `INOUT`

```text
Java ─────────→ Database
Java ←───────── Database
```

---

# 🧠 Final Revision

### CallableStatement

> JDBC interface used to call stored procedures and stored functions.

```java
con.prepareCall(...)
```

### Stored Procedure

> A named program stored and executed inside the database.

### IN parameter

> Sends data **from Java to the procedure**.

```java
cs.setInt(1, 101);
```

### OUT parameter

> Sends data **from the procedure back to Java**.

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);

cs.execute();

String x = cs.getString(2);
```

### INOUT parameter

> Sends data to the procedure and receives a potentially modified value back.

```java
cs.setInt(1, 80);

cs.registerOutParameter(
    1,
    Types.INTEGER
);

cs.execute();

int x = cs.getInt(1);
```

### `registerOutParameter()`

> Tells JDBC that a parameter is an OUT/INOUT parameter and specifies its SQL type.

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);
```

## 🔥 The one formula to remember

```text
IN
    set
     ↓
  execute

OUT
  register
     ↓
  execute
     ↓
    get

INOUT
    set
     ↓
  register
     ↓
  execute
     ↓
    get
```

If this four-step pattern is clear, you have the core of `CallableStatement` firmly understood.
