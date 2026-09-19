# 10. CallableStatement in Java — DOUBTKILLER

This version is designed to **kill the doubts that normally appear in exams, interviews, coding, and real JDBC programs**.

We will handle each sub-concept independently:

```text
10. CallableStatement
│
├── 1. CallableStatement
├── 2. Stored Procedures
├── 3. IN Parameters
├── 4. OUT Parameters
├── 5. INOUT Parameters
└── 6. registerOutParameter()
```

---

# 1. CallableStatement

## ❓ Doubt 1: What exactly is CallableStatement?

`CallableStatement` is a JDBC interface used to **execute stored procedures and stored functions in a database**.

```java
java.sql.CallableStatement
```

It is obtained from a `Connection`:

```java
CallableStatement cs =
    con.prepareCall("{call getStudent(?)}");
```

So:

```text
Connection
    ↓
prepareCall()
    ↓
CallableStatement
```

---

## ❓ Doubt 2: Why do we need CallableStatement?

Suppose the database already contains:

```text
getStudent(101)
```

and that logic is stored inside the database.

Instead of writing the complete SQL logic in Java, Java can call that database routine:

```java
CallableStatement cs =
    con.prepareCall("{call getStudent(?)}");

cs.setInt(1, 101);

cs.execute();
```

Therefore:

> **CallableStatement provides the JDBC mechanism for calling database routines.**

---

## ❓ Doubt 3: Why is it called `prepareCall()`?

Because we're preparing a **call** to a database procedure/function.

Compare:

```java
con.prepareStatement(...)
```

with:

```java
con.prepareCall(...)
```

### `prepareStatement()`

Used for parameterized SQL:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

### `prepareCall()`

Used for a stored procedure/function call:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

### Memory trick

```text
SQL statement
     ↓
prepareStatement()

Stored procedure/function
     ↓
prepareCall()
```

---

## ❓ Doubt 4: Is CallableStatement a class?

No.

It is an **interface**.

```java
CallableStatement
```

is an interface from `java.sql`.

The JDBC driver provides the actual implementation.

Conceptually:

```text
Java code
    ↓
CallableStatement interface
    ↓
JDBC Driver implementation
    ↓
Database
```

---

## ❓ Doubt 5: Can CallableStatement execute normal SQL?

Technically, `CallableStatement` inherits statement functionality, but its intended purpose is calling database routines.

For ordinary SQL, use:

```text
Statement
PreparedStatement
```

For procedures/functions:

```text
CallableStatement
```

Don't choose `CallableStatement` just because it can execute something.

---

# 2. Stored Procedures

## ❓ Doubt 6: What is a stored procedure?

A stored procedure is a **named routine stored in the database**.

It contains database-side logic.

For example, conceptually:

```text
getStudentName()
       ↓
database logic
       ↓
returns student name
```

It can accept parameters and can produce results.

---

## ❓ Doubt 7: Is a stored procedure a Java method?

No.

It is useful to **compare** it with a Java method, but they are not the same thing.

### Java method

```java
void getStudent(int id) {
    // Java logic
}
```

runs in:

```text
JVM / Java application
```

### Stored procedure

```text
getStudent(id)
```

runs in:

```text
Database server
```

So:

```text
Java Method
→ Java side

Stored Procedure
→ Database side
```

---

## ❓ Doubt 8: Where is the stored procedure stored?

Inside the database system.

Not inside:

```text
.java file
```

Not inside:

```text
.class file
```

Instead, it is created and stored by the database.

---

## ❓ Doubt 9: Why use stored procedures?

Depending on the application/database design, stored procedures can be useful for:

* centralizing database logic
* reusing database operations
* encapsulating complex database operations
* reducing repeated SQL logic in applications
* performing operations close to the data

But they aren't automatically better than application-side SQL. The choice depends on architecture, database, performance, maintainability, and team practices.

---

## ❓ Doubt 10: Does every database use the same stored-procedure syntax?

**No.**

Stored procedure syntax is database-specific.

For example, MySQL, Oracle, SQL Server, and PostgreSQL have different routine syntax and capabilities.

However, JDBC provides a common Java-side API for calling them.

That's one of JDBC's major benefits.

---

# 3. IN Parameters

## ❓ Doubt 11: What does IN mean?

`IN` means:

> The parameter receives an input value from the caller.

In JDBC:

```text
Java → Database procedure
```

Example:

```java
cs.setInt(1, 101);
```

---

## ❓ Doubt 12: Why do we use `setInt()`?

Suppose the procedure expects:

```text
IN p_id INT
```

Java supplies an integer:

```java
cs.setInt(1, 101);
```

Here:

```text
1
```

means parameter number 1.

And:

```text
101
```

is the value.

---

## ❓ Doubt 13: Why does parameter numbering start from 1?

JDBC parameter indexes are **1-based**.

For:

```java
"{call test(?, ?, ?)}"
```

the parameters are:

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

Not:

```java
cs.setInt(0, 101); // Wrong
```

---

## ❓ Doubt 14: Does an IN parameter need `registerOutParameter()`?

**No.**

For a pure `IN` parameter:

```java
cs.setInt(1, 101);
```

is sufficient for supplying its value.

Think:

```text
IN
↓
Input
↓
setXXX()
```

---

## ❓ Doubt 15: What happens to the IN value?

Suppose:

```java
cs.setInt(1, 101);
```

The conceptual flow is:

```text
101
 ↓
Java
 ↓
JDBC
 ↓
Driver
 ↓
Database
 ↓
IN parameter
```

The procedure can then use that value.

---

## ❓ Doubt 16: What if the IN parameter is String?

Use an appropriate setter:

```java
cs.setString(1, "Ravi");
```

For a double:

```java
cs.setDouble(1, 85.5);
```

For a date/time or other type, use the appropriate JDBC setter.

---

# 4. OUT Parameters

This is where most beginners become confused.

## ❓ Doubt 17: What does OUT mean?

`OUT` means the procedure produces a value that the caller can retrieve.

Direction:

```text
Database
   ↓
Java
```

Example:

```text
OUT p_name VARCHAR
```

The procedure generates `p_name`, and Java retrieves it.

---

# ❓ Doubt 18: Why can't I just use `getString()`?

Suppose:

```java
String name = cs.getString(2);
```

How does JDBC know that parameter 2 is an output parameter?

You first tell JDBC:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

Then execute:

```java
cs.execute();
```

Then retrieve:

```java
String name =
    cs.getString(2);
```

So the sequence is:

```text
register
   ↓
execute
   ↓
get
```

---

# ❓ Doubt 19: Does `registerOutParameter()` actually retrieve the value?

**No.**

This is extremely important.

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

means:

> "Parameter 2 is an output parameter of SQL type VARCHAR."

It does **not** mean:

> "Give me the output now."

Retrieval happens later:

```java
cs.getString(2);
```

---

# ❓ Doubt 20: Why do we specify `Types.VARCHAR`?

Because JDBC needs the SQL type of the output parameter.

Example:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

means:

```text
Parameter = 2
Direction = OUT
SQL type = VARCHAR
```

Another example:

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);
```

means:

```text
Parameter = 2
Direction = OUT
SQL type = INTEGER
```

---

# ❓ Doubt 21: What is the complete OUT sequence?

Exactly this:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudentName(?, ?)}"
    );

cs.setInt(1, 101);

cs.registerOutParameter(
    2,
    Types.VARCHAR
);

cs.execute();

String name =
    cs.getString(2);
```

Understand every line:

```text
setInt()
    ↓
give IN value

registerOutParameter()
    ↓
declare/register OUT parameter

execute()
    ↓
run procedure

getString()
    ↓
retrieve OUT value
```

---

# 5. INOUT Parameters

## ❓ Doubt 22: What is INOUT?

`INOUT` combines:

```text
IN + OUT
```

The parameter:

1. receives an initial value
2. participates in the procedure
3. returns a value

Direction:

```text
Java
 ↓
Database
 ↓
Java
```

---

## ❓ Doubt 23: Why do INOUT parameters need both `setXXX()` and `registerOutParameter()`?

Because they have **both directions**.

Suppose:

```text
INOUT marks = 80
```

Java must send:

```java
cs.setInt(1, 80);
```

That's the input side.

Then Java must tell JDBC that the parameter will also return a value:

```java
cs.registerOutParameter(
    1,
    Types.INTEGER
);
```

That's the output side.

Then:

```java
cs.execute();
```

And finally:

```java
int marks =
    cs.getInt(1);
```

---

# ❓ Doubt 24: Complete INOUT example

Suppose the database procedure conceptually does:

```text
INOUT marks

marks = marks + 5
```

Java:

```java
CallableStatement cs =
    con.prepareCall(
        "{call increaseMarks(?)}"
    );

cs.setInt(1, 80);

cs.registerOutParameter(
    1,
    Types.INTEGER
);

cs.execute();

int result =
    cs.getInt(1);

System.out.println(result);
```

Output:

```text
85
```

Flow:

```text
80
 ↓
Java
 ↓
INOUT parameter
 ↓
Procedure
 ↓
80 + 5
 ↓
85
 ↓
Java
```

---

# ❓ Doubt 25: Is INOUT the same as using separate IN and OUT parameters?

No.

These:

```text
IN a
OUT b
```

are two separate parameters.

Whereas:

```text
INOUT a
```

is one parameter that has both input and output behavior.

Example:

```text
Two parameters:

IN    → parameter 1
OUT   → parameter 2
```

versus:

```text
One parameter:

INOUT → parameter 1
```

---

# 6. `registerOutParameter()`

Now let's completely destroy the confusion around this method.

## ❓ Doubt 26: What exactly does `registerOutParameter()` do?

It registers a parameter as an output parameter and specifies its SQL type.

Syntax:

```java
cs.registerOutParameter(
    parameterIndex,
    sqlType
);
```

Example:

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);
```

---

# ❓ Doubt 27: What does parameterIndex mean?

It identifies **which `?`** is being registered.

Suppose:

```java
"{call calculate(?, ?, ?)}"
```

Then:

```text
?       ?       ?
↓       ↓       ↓
1       2       3
```

If you write:

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);
```

you are saying:

```text
The SECOND parameter is an OUT parameter.
```

---

# ❓ Doubt 28: What does `sqlType` mean?

It tells JDBC the SQL type of the output.

Examples:

```java
Types.INTEGER
Types.VARCHAR
Types.DOUBLE
Types.DECIMAL
Types.DATE
Types.TIMESTAMP
```

For example:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

means:

```text
Parameter 2
    ↓
OUT
    ↓
VARCHAR
```

---

# ❓ Doubt 29: Can I call `registerOutParameter()` on an IN parameter?

For a pure `IN` parameter, **no need**.

The conceptual distinction is:

```text
IN
→ setXXX()

OUT
→ registerOutParameter()
→ getXXX()

INOUT
→ setXXX()
→ registerOutParameter()
→ getXXX()
```

---

# ❓ Doubt 30: Does the order matter?

Yes, the normal sequence matters.

For OUT:

```text
register
   ↓
execute
   ↓
get
```

Don't try to retrieve the output before the procedure has executed.

Correct:

```java
cs.registerOutParameter(2, Types.INTEGER);

cs.execute();

int value = cs.getInt(2);
```

---

# 7. The Ultimate Parameter Table

| Feature                   | IN        | OUT       | INOUT            |
| ------------------------- | --------- | --------- | ---------------- |
| Java sends value          | ✅         | ❌         | ✅                |
| Database sends value back | ❌         | ✅         | ✅                |
| Direction                 | Java → DB | DB → Java | Java → DB → Java |
| `setXXX()`                | ✅         | ❌         | ✅                |
| `registerOutParameter()`  | ❌         | ✅         | ✅                |
| `getXXX()`                | ❌         | ✅         | ✅                |

### Memorize this:

```text
IN     = SET

OUT    = REGISTER + GET

INOUT  = SET + REGISTER + GET
```

And all three require:

```text
EXECUTE
```

---

# 8. Biggest Confusion: `setXXX()` vs `getXXX()` vs `registerOutParameter()`

This is worth memorizing separately.

### `setXXX()`

**Puts a value into a parameter.**

```java
cs.setInt(1, 101);
```

Meaning:

```text
Java → parameter
```

---

### `registerOutParameter()`

**Tells JDBC that a parameter will provide output.**

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);
```

Meaning:

```text
parameter → output
```

---

### `getXXX()`

**Reads the output value after execution.**

```java
int result =
    cs.getInt(2);
```

Meaning:

```text
output parameter → Java
```

---

# 9. Complete Example: IN + OUT

Suppose:

```text
Procedure:

getStudentName(
    IN  id,
    OUT name
)
```

Java:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudentName(?, ?)}"
    );
```

Parameter map:

```text
?       ?
↓       ↓
1       2
IN      OUT
```

Set IN:

```java
cs.setInt(1, 101);
```

Register OUT:

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

Get OUT:

```java
String name =
    cs.getString(2);
```

Everything becomes:

```text
Parameter 1
     ↓
setInt()
     ↓
101
     ↓
Procedure

Parameter 2
     ↓
registerOutParameter()
     ↓
Procedure
     ↓
getString()
     ↓
Java
```

---

# 10. Complete Example: INOUT

Suppose:

```text
Procedure:

increaseMarks(
    INOUT marks
)
```

Java:

```java
CallableStatement cs =
    con.prepareCall(
        "{call increaseMarks(?)}"
    );

cs.setInt(1, 80);

cs.registerOutParameter(
    1,
    Types.INTEGER
);

cs.execute();

int marks =
    cs.getInt(1);

System.out.println(marks);
```

Conceptually:

```text
setInt(1, 80)
       ↓
      80
       ↓
   procedure
       ↓
      85
       ↓
getInt(1)
```

---

# 11. `CallableStatement` vs `PreparedStatement`

| Feature                  | PreparedStatement         | CallableStatement           |
| ------------------------ | ------------------------- | --------------------------- |
| Main purpose             | Parameterized SQL         | Stored procedures/functions |
| Creation                 | `prepareStatement()`      | `prepareCall()`             |
| Parameters               | `?`                       | `?`                         |
| `setXXX()`               | ✅                         | ✅                           |
| OUT parameters           | Not its primary mechanism | ✅                           |
| `registerOutParameter()` | ❌                         | ✅                           |
| Stored procedure calls   | ❌                         | ✅                           |

### Memory:

```text
PreparedStatement
→ PREPARE SQL

CallableStatement
→ CALL procedure/function
```

---

# 12. `CallableStatement` vs `Statement`

### Statement

```java
Statement st =
    con.createStatement();
```

Used for ordinary SQL.

### CallableStatement

```java
CallableStatement cs =
    con.prepareCall(...);
```

Used for database routines.

```text
Statement
    ↓
SQL

PreparedStatement
    ↓
Parameterized SQL

CallableStatement
    ↓
Stored procedure/function
```

---

# 13. Common Exam Traps

## 🚨 Trap 1

**Question:** Which method creates a `CallableStatement`?

Answer:

```java
Connection.prepareCall()
```

Not:

```java
prepareStatement()
```

---

## 🚨 Trap 2

**Question:** Which parameter sends data from Java to a procedure?

Answer:

```text
IN
```

---

## 🚨 Trap 3

**Question:** Which parameter sends data from a procedure back to Java?

Answer:

```text
OUT
```

---

## 🚨 Trap 4

**Question:** Which parameter does both?

Answer:

```text
INOUT
```

---

## 🚨 Trap 5

**Question:** Which method registers an OUT parameter?

Answer:

```java
registerOutParameter()
```

---

## 🚨 Trap 6

**Question:** Does `registerOutParameter()` retrieve the value?

**No.**

It registers the output parameter.

Retrieval happens through:

```java
getInt()
getString()
getDouble()
...
```

---

## 🚨 Trap 7

**Question:** Does `setInt()` retrieve a value?

**No.**

It supplies an input value.

```text
setXXX()
→ input

getXXX()
→ output retrieval
```

---

## 🚨 Trap 8

**Question:** Are JDBC parameter indexes zero-based?

**No.**

They are **1-based**.

```text
1, 2, 3, ...
```

---

# 14. One Giant Mental Model

Imagine a pipe between Java and the database:

```text
                    JAVA
                     │
                     │
              CallableStatement
                     │
                     ↓
                JDBC Driver
                     │
                     ↓
              STORED PROCEDURE
                     │
          ┌──────────┼──────────┐
          │          │          │
          ↓          ↑          ↕
         IN         OUT       INOUT
          │          │          │
          │          │          │
      setXXX()  register()   setXXX()
                         │     register()
                         │          │
                         └────┬─────┘
                              ↓
                           execute()
                              ↓
                           getXXX()
```

---

# 🔥 DOUBTKILLER: The 6 Things You Must Never Mix Up

### 1. CallableStatement

```text
Java-side JDBC object
```

### 2. Stored Procedure

```text
Database-side routine
```

### 3. IN

```text
Java → Database
```

Use:

```java
setXXX()
```

### 4. OUT

```text
Database → Java
```

Use:

```java
registerOutParameter()
```

then:

```java
getXXX()
```

### 5. INOUT

```text
Java → Database → Java
```

Use:

```java
setXXX()
registerOutParameter()
getXXX()
```

### 6. registerOutParameter()

```text
Registers an OUT/INOUT parameter.
```

It does **not** retrieve the value.

---

# 🧠 Final DOUBTKILLER Formula

```text
                 CallableStatement
                         │
                         ↓
                  prepareCall()
                         │
                         ↓
                Stored Procedure
                         │
              ┌──────────┼──────────┐
              ↓          ↓          ↓
             IN         OUT        INOUT
              │          │           │
           setXXX()   register()  setXXX()
                                  register()
              │          │           │
              └──────────┼───────────┘
                         ↓
                      execute()
                         │
                         ↓
                    getXXX()
```

### The ultimate memory sentence:

> **IN = SET, OUT = REGISTER + GET, INOUT = SET + REGISTER + GET; and execution happens in between.**

```text
IN
SET → EXECUTE

OUT
REGISTER → EXECUTE → GET

INOUT
SET → REGISTER → EXECUTE → GET
```

That single pattern eliminates most `CallableStatement` doubts.
