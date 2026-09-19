# 10. CallableStatement in Java — DEEPDIVE

`CallableStatement` is the JDBC mechanism used by a Java program to **invoke stored procedures and stored functions in a database**.

We will study each concept independently and then connect them together:

```text
CallableStatement
        ↓
Stored Procedure
        ↓
Parameters
   ┌────┼────┐
   ↓    ↓    ↓
  IN   OUT  INOUT
        ↓
registerOutParameter()
```

---

# 1. CallableStatement

## 1.1 What is CallableStatement?

`CallableStatement` is an interface in the `java.sql` package:

```java
java.sql.CallableStatement
```

It is used to execute:

* Stored procedures
* Stored functions

Example:

```java
CallableStatement cs =
    con.prepareCall("{call getStudent(?)}");
```

Then:

```java
cs.execute();
```

---

## 1.2 Why is it called CallableStatement?

The word **Callable** means:

> Something that can be called.

In JDBC:

```text
Java
  ↓
calls
  ↓
database stored procedure/function
```

So `CallableStatement` represents a JDBC statement that can **call database-side executable logic**.

---

# 1.3 CallableStatement hierarchy

Conceptually:

```text
java.lang.Object
       ↓
   Statement
       ↑
PreparedStatement
       ↑
CallableStatement
```

More precisely, the interfaces relate as:

```text
Statement
   ↑
PreparedStatement
   ↑
CallableStatement
```

`CallableStatement` inherits functionality from `PreparedStatement`, which inherits from `Statement`.

Therefore, `CallableStatement` supports many operations associated with prepared statements in addition to procedure-specific functionality.

---

# 1.4 Creating a CallableStatement

A `CallableStatement` is normally created using:

```java
Connection.prepareCall()
```

Example:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

Notice:

```java
prepareCall()
```

not:

```java
prepareStatement()
```

### Difference

```text
prepareStatement()
       ↓
PreparedStatement
       ↓
SQL statement

prepareCall()
       ↓
CallableStatement
       ↓
Stored procedure/function call
```

---

# 1.5 What does `{call ...}` mean?

Consider:

```java
con.prepareCall(
    "{call getStudent(?)}"
);
```

The:

```text
{call ...}
```

syntax is JDBC escape syntax for calling a procedure.

For example:

```java
"{call getStudent(?)}"
```

means conceptually:

```text
Call procedure getStudent
with one parameter
```

The JDBC driver translates the call appropriately for the target database.

---

# 1.6 Procedure with multiple parameters

Suppose:

```text
getStudent(id, department)
```

Java:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?, ?)}"
    );
```

Then:

```java
cs.setInt(1, 101);
cs.setString(2, "CSE");
```

The parameter positions are:

```text
?       ?
↓       ↓
1       2
```

---

# 1.7 CallableStatement does not itself contain the procedure

This is an important conceptual distinction.

The stored procedure exists in the:

```text
Database
```

The `CallableStatement` exists on the:

```text
Java/JDBC side
```

Think:

```text
Java application
      │
      │ CallableStatement
      ↓
JDBC Driver
      │
      ↓
Database
      │
      ↓
Stored Procedure
```

---

# 1.8 Basic lifecycle

Typical lifecycle:

```text
Connection
    ↓
prepareCall()
    ↓
CallableStatement
    ↓
Set parameters
    ↓
Register OUT parameters
    ↓
execute()
    ↓
Read output
    ↓
Close
```

Example:

```java
CallableStatement cs =
    con.prepareCall(
        "{call calculate(?)}"
    );

cs.setInt(1, 100);

cs.execute();

cs.close();
```

---

# 2. Stored Procedures

# 2.1 What is a stored procedure?

A **stored procedure** is a named database-side program stored inside the database.

It can contain database operations such as:

```text
SELECT
INSERT
UPDATE
DELETE
conditional logic
loops
variables
error handling
```

depending on the database's procedural SQL language.

---

# 2.2 Why use stored procedures?

Suppose Java needs to perform:

```text
Validate student
      ↓
Calculate marks
      ↓
Update student
      ↓
Insert audit record
      ↓
Return result
```

Instead of sending every operation separately from Java, database-side logic can be placed into a stored procedure.

Then Java can simply call:

```text
call procedure
```

---

# 2.3 Stored procedure example

The exact syntax differs between database systems.

For example, conceptually:

```sql
CREATE PROCEDURE getStudent(IN p_id INT)
BEGIN
    SELECT id, name, marks
    FROM student
    WHERE id = p_id;
END;
```

Here:

```text
getStudent
```

is the procedure name.

And:

```text
p_id
```

is an input parameter.

---

# 2.4 Calling it from Java

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );

cs.setInt(1, 101);

cs.execute();
```

Flow:

```text
Java
 │
 │ 101
 ↓
CallableStatement
 │
 ↓
JDBC Driver
 │
 ↓
Database
 │
 ↓
getStudent(101)
```

---

# 2.5 Stored procedure vs Java method

A Java method:

```java
void getStudent(int id) {
    ...
}
```

runs inside the Java application/JVM.

A stored procedure:

```text
getStudent(IN id INT)
```

runs inside the database system.

Therefore:

| Java Method                             | Stored Procedure                       |
| --------------------------------------- | -------------------------------------- |
| Exists in Java program                  | Exists in database                     |
| Executes in JVM/application environment | Executes in database                   |
| Called directly by Java                 | Called through JDBC/database interface |
| Java method parameters                  | Procedure parameters                   |

---

# 2.6 Stored procedure vs SQL statement

Normal SQL:

```java
PreparedStatement ps =
    con.prepareStatement(
        "SELECT * FROM student WHERE id = ?"
    );
```

Stored procedure:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

The first sends an SQL statement.

The second invokes database-side stored logic.

---

# 2.7 Stored procedure can have parameters

The most important parameter modes are:

```text
IN
OUT
INOUT
```

These define the **direction of data flow**.

---

# 3. IN Parameters

## 3.1 What is an IN parameter?

An `IN` parameter receives a value **from the caller**.

In our case:

```text
Java → Database procedure
```

Example:

```text
IN p_id INT
```

Java supplies the value:

```java
cs.setInt(1, 101);
```

---

# 3.2 IN parameter flow

```text
Java
 │
 │ value
 ↓
IN parameter
 │
 ↓
Stored Procedure
```

For example:

```text
101
 ↓
p_id
 ↓
getStudent()
```

---

# 3.3 Example

Procedure:

```sql
CREATE PROCEDURE getStudent(IN p_id INT)
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

cs.setInt(1, 101);

cs.execute();
```

---

# 3.4 Why use `setInt()`?

Because the procedure parameter is:

```sql
INT
```

So Java supplies an integer:

```java
cs.setInt(1, 101);
```

Similarly:

```java
cs.setString(1, "Ravi");
```

for a textual parameter.

```java
cs.setDouble(1, 85.5);
```

for a numeric decimal parameter.

---

# 3.5 Parameter index

Consider:

```java
"{call getStudent(?, ?)}"
```

There are two parameters:

```text
?       ?
↓       ↓
1       2
```

Therefore:

```java
cs.setInt(1, 101);
cs.setString(2, "CSE");
```

---

# 3.6 Important: Parameter numbering starts at 1

JDBC parameter indexes are **1-based**.

Correct:

```java
cs.setInt(1, 101);
```

Incorrect:

```java
cs.setInt(0, 101); // ❌
```

This is similar to JDBC column indexes in `ResultSet`.

```text
JDBC
 ↓
Parameter index → starts at 1
ResultSet column index → starts at 1
```

---

# 3.7 Does an IN parameter require registerOutParameter()?

❌ No.

For a pure `IN` parameter:

```java
cs.setInt(1, 101);
```

is enough.

You don't register it as an OUT parameter.

---

# 4. OUT Parameters

## 4.1 What is an OUT parameter?

An `OUT` parameter is used to return a value from the procedure to the caller.

Direction:

```text
Database
    ↓
OUT parameter
    ↓
Java
```

---

# 4.2 Example

Suppose the procedure:

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

# 4.3 Java code

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

---

# 4.4 Why can't we use setString() for OUT?

Because an OUT parameter is not initially being supplied by Java.

This:

```java
cs.setString(2, "Ravi");
```

means:

> Java is supplying `"Ravi"` as an input value.

But OUT means:

> Database will produce the value.

So instead:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

---

# 4.5 Why register before execute?

Because JDBC needs to know beforehand:

```text
Parameter 2
     ↓
OUT parameter
     ↓
SQL type VARCHAR
```

Then the driver can properly handle the returned value.

Therefore the normal sequence is:

```text
register
   ↓
execute
   ↓
get
```

not:

```text
execute
   ↓
register
```

---

# 4.6 Reading an OUT parameter

After:

```java
cs.execute();
```

you retrieve the value using an appropriate getter:

```java
String name =
    cs.getString(2);
```

or:

```java
int count =
    cs.getInt(2);
```

or:

```java
double value =
    cs.getDouble(2);
```

---

# 4.7 OUT parameter is not a ResultSet

This is a major distinction.

A stored procedure can return data through different mechanisms.

### ResultSet

```text
Rows and columns
```

Example:

```text
101 Ravi 85
102 Kumar 90
```

### OUT parameter

```text
Individual output value
```

Example:

```text
"Ravi"
```

Therefore:

```text
ResultSet
→ tabular result

OUT parameter
→ parameter value
```

A procedure can potentially produce both.

---

# 5. INOUT Parameters

## 5.1 What is INOUT?

An `INOUT` parameter performs both operations:

```text
Java → Database
Database → Java
```

It starts with a value supplied by Java and may return a modified value.

---

# 5.2 Mental model

```text
          INOUT

Java
 │
 │ initial value
 ↓
Procedure
 │
 │ modified value
 ↓
Java
```

Example:

```text
80
 ↓
procedure
 ↓
85
```

---

# 5.3 Stored procedure example

Conceptually:

```sql
CREATE PROCEDURE increaseMarks(
    INOUT p_marks INT
)
BEGIN
    SET p_marks = p_marks + 5;
END;
```

Suppose Java supplies:

```text
80
```

Procedure changes it:

```text
80 + 5 = 85
```

Java receives:

```text
85
```

---

# 5.4 Java code

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

Output:

```text
85
```

---

# 5.5 Why do we need both set and register?

Because INOUT has two directions.

### Input side

```java
cs.setInt(1, 80);
```

means:

```text
Java → procedure
```

### Output side

```java
cs.registerOutParameter(
    1,
    Types.INTEGER
);
```

means:

```text
procedure → Java
```

So:

```text
INOUT
 ↓
set + register
 ↓
execute
 ↓
get
```

---

# 5.6 IN vs OUT vs INOUT

This table is worth memorizing:

| Parameter | Data direction   | `setXXX()` | `registerOutParameter()` | `getXXX()` |
| --------- | ---------------- | ---------: | -----------------------: | ---------: |
| IN        | Java → DB        |          ✅ |                        ❌ |          ❌ |
| OUT       | DB → Java        |          ❌ |                        ✅ |          ✅ |
| INOUT     | Java → DB → Java |          ✅ |                        ✅ |          ✅ |

---

# 6. `registerOutParameter()`

## 6.1 What is registerOutParameter()?

It is a method of `CallableStatement` used to tell JDBC:

> "This parameter will return a value from the database, and this is its SQL type."

Example:

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);
```

---

# 6.2 Method signature

A commonly used form is:

```java
void registerOutParameter(
    int parameterIndex,
    int sqlType
)
```

For example:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

---

# 6.3 First argument — parameterIndex

```java
2
```

means:

> The second parameter is an OUT parameter.

For:

```java
"{call test(?, ?, ?)}"
```

we have:

```text
1 → ?
2 → ?
3 → ?
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

# 6.4 Second argument — sqlType

Example:

```java
Types.INTEGER
```

means the database output is expected to have SQL type `INTEGER`.

Common types:

```java
Types.INTEGER
Types.VARCHAR
Types.DOUBLE
Types.DECIMAL
Types.DATE
Types.TIMESTAMP
Types.BOOLEAN
```

---

# 6.5 Example with VARCHAR

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

Then:

```java
String name =
    cs.getString(2);
```

---

# 6.6 Example with INTEGER

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);
```

Then:

```java
int count =
    cs.getInt(2);
```

---

# 6.7 Does registerOutParameter() retrieve the value?

❌ No.

This is a critical distinction.

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);
```

does **not** retrieve the output.

It only registers the parameter.

Retrieval happens after execution:

```java
cs.execute();

int value =
    cs.getInt(2);
```

Therefore:

```text
registerOutParameter()
       ↓
DECLARE/REGISTER OUTPUT

execute()
       ↓
RUN PROCEDURE

getXXX()
       ↓
READ OUTPUT
```

---

# 6.8 Can we call getXXX() before execute()?

Normally, you should not.

Correct:

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);

cs.execute();

int result =
    cs.getInt(2);
```

The procedure must execute first so the database can produce the output.

---

# 6.9 Why do we specify SQL type?

Because JDBC needs type information for the output parameter.

For example:

```text
OUT parameter
    ↓
INTEGER
```

or:

```text
OUT parameter
    ↓
VARCHAR
```

The JDBC driver uses this information to correctly handle the returned value.

---

# 7. Complete Example — IN + OUT

Let's put everything together.

Suppose database procedure:

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

public class CallableDemo {

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

        // Execute procedure
        cs.execute();

        // Retrieve OUT value
        String name =
            cs.getString(2);

        System.out.println(name);

        cs.close();
        con.close();
    }
}
```

---

# 8. Complete Example — INOUT

Database procedure:

```sql
CREATE PROCEDURE increaseSalary(
    INOUT p_salary DECIMAL(10,2)
)
BEGIN
    SET p_salary = p_salary * 1.10;
END;
```

Java:

```java
CallableStatement cs =
    con.prepareCall(
        "{call increaseSalary(?)}"
    );

cs.setDouble(1, 50000.00);

cs.registerOutParameter(
    1,
    Types.DECIMAL
);

cs.execute();

double salary =
    cs.getDouble(1);

System.out.println(salary);
```

Conceptually:

```text
50000
   ↓
IN
   ↓
increaseSalary()
   ↓
55000
   ↓
OUT
   ↓
Java
```

For exact monetary calculations, `BigDecimal` is generally preferable to `double`:

```java
cs.setBigDecimal(
    1,
    new BigDecimal("50000.00")
);

cs.registerOutParameter(
    1,
    Types.DECIMAL
);

cs.execute();

BigDecimal salary =
    cs.getBigDecimal(1);
```

---

# 9. CallableStatement with a ResultSet

A stored procedure can also produce a result set.

For example:

```sql
CREATE PROCEDURE getStudents()
BEGIN
    SELECT id, name, marks
    FROM student;
END;
```

Java:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudents()}"
    );

boolean hasResult =
    cs.execute();

if (hasResult) {

    ResultSet rs =
        cs.getResultSet();

    while (rs.next()) {

        int id =
            rs.getInt("id");

        String name =
            rs.getString("name");

        System.out.println(
            id + " " + name
        );
    }

    rs.close();
}
```

This illustrates an important point:

```text
CallableStatement
        ↓
Stored Procedure
        ↓
ResultSet
```

is possible.

And:

```text
CallableStatement
        ↓
Stored Procedure
        ↓
OUT parameter
```

is also possible.

They are different mechanisms.

---

# 10. Stored Procedure vs Stored Function

Although the main focus here is stored procedures, you should know the distinction.

### Stored procedure

Called using procedure-call syntax:

```java
"{call procedureName(?)}"
```

### Stored function

Returns a value as part of the function call.

JDBC can use syntax such as:

```java
"{? = call functionName(?)}"
```

For example:

```java
CallableStatement cs =
    con.prepareCall(
        "{? = call calculateTotal(?)}"
    );

cs.registerOutParameter(
    1,
    Types.INTEGER
);

cs.setInt(2, 10);

cs.execute();

int result =
    cs.getInt(1);
```

Here the first parameter represents the function's return value.

Database-specific syntax and behavior can vary, so the database documentation and JDBC driver documentation matter.

---

# 11. Parameter Direction — Deep Mental Model

Imagine a procedure:

```text
calculateSomething(
    IN a,
    OUT b,
    INOUT c
)
```

Data movement is:

```text
                  Stored Procedure
                  ┌───────────────┐
Java ──a─────────→│ IN            │
                  │               │
Java ←────────b───│ OUT           │
                  │               │
Java ──c─────────→│ INOUT         │
Java ←────────c───│               │
                  └───────────────┘
```

This explains everything.

---

# 12. Common Mistakes

## Mistake 1 — Using `prepareStatement()`

Wrong:

```java
PreparedStatement ps =
    con.prepareStatement(
        "{call getStudent(?)}"
    );
```

For a stored procedure, normally use:

```java
CallableStatement cs =
    con.prepareCall(
        "{call getStudent(?)}"
    );
```

---

## Mistake 2 — Forgetting to register OUT

Wrong:

```java
cs.setInt(1, 101);

cs.execute();

String name =
    cs.getString(2);
```

If parameter 2 is an OUT parameter, you need:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

---

## Mistake 3 — Registering an IN-only parameter as OUT

If procedure is:

```text
IN p_id
```

you don't normally do:

```java
cs.registerOutParameter(
    1,
    Types.INTEGER
);
```

Only register parameters that are OUT/INOUT as appropriate.

---

## Mistake 4 — Forgetting `setXXX()` for INOUT

Wrong:

```java
cs.registerOutParameter(
    1,
    Types.INTEGER
);
```

For an INOUT parameter, you also need to supply its initial input:

```java
cs.setInt(1, 80);

cs.registerOutParameter(
    1,
    Types.INTEGER
);
```

---

## Mistake 5 — Trying to read the OUT value before execute

Wrong:

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);

int value =
    cs.getInt(2); // ❌
```

Correct:

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);

cs.execute();

int value =
    cs.getInt(2);
```

---

## Mistake 6 — Confusing OUT parameters with ResultSet

They are different.

```text
OUT parameter
→ individual output value

ResultSet
→ rows and columns
```

---

# 13. Complete Parameter Comparison

| Feature                     | IN        | OUT         | INOUT            |
| --------------------------- | --------- | ----------- | ---------------- |
| Java supplies initial value | ✅         | ❌           | ✅                |
| Procedure receives value    | ✅         | ❌ initially | ✅                |
| Procedure returns value     | ❌         | ✅           | ✅                |
| `setXXX()`                  | ✅         | ❌           | ✅                |
| `registerOutParameter()`    | ❌         | ✅           | ✅                |
| `getXXX()`                  | ❌         | ✅           | ✅                |
| Direction                   | Java → DB | DB → Java   | Java → DB → Java |

---

# 14. Complete CallableStatement Lifecycle

```text
             Connection
                 │
                 ↓
           prepareCall()
                 │
                 ↓
       CallableStatement
                 │
       ┌─────────┴──────────┐
       ↓                    ↓
   IN parameters        OUT parameters
       ↓                    ↓
   setXXX()       registerOutParameter()
       │                    │
       └─────────┬──────────┘
                 ↓
              execute()
                 │
                 ↓
       ┌─────────┴──────────┐
       ↓                    ↓
   ResultSet            OUT values
       ↓                    ↓
   next()/getXXX()      getXXX()
                 │
                 ↓
               close()
```

---

# 15. The Most Important Distinctions

### `PreparedStatement`

```text
Execute parameterized SQL
```

```java
con.prepareStatement(...)
```

### `CallableStatement`

```text
Call stored procedure/function
```

```java
con.prepareCall(...)
```

---

### `setXXX()`

Means:

> **Give an input value to a parameter.**

```java
cs.setInt(1, 101);
```

---

### `registerOutParameter()`

Means:

> **Tell JDBC that this parameter will produce an output.**

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

---

### `getXXX()`

Means:

> **Retrieve the output value after execution.**

```java
String name =
    cs.getString(2);
```

---

# 🧠 Final Deep-Dive Memory Formula

```text
CallableStatement
       ↓
Stored Procedure / Function
       ↓
Parameters
       │
       ├── IN
       │    ↓
       │  setXXX()
       │
       ├── OUT
       │    ↓
       │  registerOutParameter()
       │    ↓
       │  execute()
       │    ↓
       │  getXXX()
       │
       └── INOUT
            ↓
          setXXX()
            ↓
          registerOutParameter()
            ↓
          execute()
            ↓
          getXXX()
```

### One-line definitions

> **CallableStatement** = JDBC object used to call stored procedures/functions.

> **Stored Procedure** = database-side named program containing executable database logic.

> **IN** = Java sends a value to the procedure.

> **OUT** = procedure sends a value back to Java.

> **INOUT** = Java sends a value and the procedure can return a modified value.

> **registerOutParameter()** = registers an OUT/INOUT parameter and specifies its SQL type so JDBC can retrieve its result.

### 🔥 The sequence you should never forget

```text
IN      → set → execute

OUT     → register → execute → get

INOUT   → set → register → execute → get
```

That sequence is the foundation of `CallableStatement`.
