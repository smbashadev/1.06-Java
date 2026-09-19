# 10. CallableStatement in Java — 3LEVEL

The **3LEVEL method** means we understand every concept at three depths:

* 🟢 **LEVEL 1 — Basic:** What is it?
* 🟡 **LEVEL 2 — Understanding:** How and why does it work?
* 🔴 **LEVEL 3 — Deep:** How do the pieces connect, and what mistakes/confusions should you avoid?

---

# 1. CallableStatement

## 🟢 LEVEL 1 — Basic

### Definition

`CallableStatement` is a JDBC interface used to **call stored procedures and stored functions** in a database.

Package:

```java
java.sql.CallableStatement
```

Created using:

```java
Connection.prepareCall()
```

Example:

```java
CallableStatement cs =
    con.prepareCall("{call getStudent(?)}");
```

Then:

```java
cs.execute();
```

### Simple memory

```text
CallableStatement
       ↓
CALL
       ↓
Stored Procedure
```

---

## 🟡 LEVEL 2 — Understanding

Suppose the database has:

```text
getStudent(101)
```

Java can call it through:

```java
CallableStatement cs =
    con.prepareCall("{call getStudent(?)}");

cs.setInt(1, 101);

cs.execute();
```

The flow is:

```text
Java Application
      ↓
CallableStatement
      ↓
JDBC Driver
      ↓
Database
      ↓
Stored Procedure
```

The `?` represents a parameter:

```text
{call getStudent(?)}
                  ↑
              parameter
```

And:

```java
cs.setInt(1, 101);
```

puts `101` into parameter 1.

---

## 🔴 LEVEL 3 — Deep

`CallableStatement` is specifically designed for database routines.

Compare:

```text
PreparedStatement
      ↓
Parameterized SQL

CallableStatement
      ↓
Stored Procedure / Function
```

The important creation method is:

```java
con.prepareCall(...)
```

not:

```java
con.prepareStatement(...)
```

Typical lifecycle:

```text
Connection
    ↓
prepareCall()
    ↓
CallableStatement
    ↓
Set IN parameters
    ↓
Register OUT parameters
    ↓
execute()
    ↓
Read output
    ↓
close()
```

### Important distinction

`CallableStatement` does **not** mean the procedure itself is stored in Java.

The procedure exists in the:

```text
Database
```

while `CallableStatement` exists on the:

```text
Java/JDBC side
```

---

# 2. Stored Procedures

## 🟢 LEVEL 1 — Basic

A **stored procedure** is a named program stored inside a database.

It can contain database operations such as:

```text
SELECT
INSERT
UPDATE
DELETE
calculations
conditions
etc.
```

Think:

> **Stored procedure = database-side program that can be called.**

---

## 🟡 LEVEL 2 — Understanding

Imagine a Java method:

```java
void getStudent(int id) {
    // logic
}
```

A stored procedure is conceptually similar:

```text
getStudent(id)
      ↓
database logic
```

For example:

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

Java can call it:

```java
CallableStatement cs =
    con.prepareCall("{call getStudent(?)}");

cs.setInt(1, 101);

cs.execute();
```

---

## 🔴 LEVEL 3 — Deep

The important distinction is **where the logic executes**.

### Java method

```text
Java method
    ↓
JVM/application
```

### Stored procedure

```text
Stored procedure
    ↓
Database server
```

Java communicates with the procedure through JDBC:

```text
┌──────────────────────┐
│   Java Application   │
└──────────┬───────────┘
           ↓
┌──────────────────────┐
│  CallableStatement   │
└──────────┬───────────┘
           ↓
┌──────────────────────┐
│     JDBC Driver      │
└──────────┬───────────┘
           ↓
┌──────────────────────┐
│      Database        │
│                      │
│  Stored Procedure    │
└──────────────────────┘
```

A stored procedure can receive parameters and can produce:

* Result sets
* OUT parameters
* INOUT parameters
* status/results depending on the database implementation

---

# 3. IN Parameters

## 🟢 LEVEL 1 — Basic

An `IN` parameter sends a value:

```text
Java → Database
```

Example:

```java
cs.setInt(1, 101);
```

Memory:

> **IN = Input goes in.**

---

## 🟡 LEVEL 2 — Understanding

Suppose:

```sql
CREATE PROCEDURE getStudent(
    IN p_id INT
)
```

Java supplies the ID:

```java
cs.setInt(1, 101);
```

Flow:

```text
101
 ↓
Java
 ↓
IN parameter
 ↓
Stored Procedure
```

The procedure can use that value.

---

## 🔴 LEVEL 3 — Deep

For an `IN` parameter, normally you:

```text
set → execute
```

Example:

```java
CallableStatement cs =
    con.prepareCall("{call getStudent(?)}");

cs.setInt(1, 101);

cs.execute();
```

You do **not** normally register a pure IN parameter with:

```java
registerOutParameter()
```

because it isn't returning a value through that parameter.

### Common setters

```java
cs.setInt(1, 101);

cs.setString(2, "Ravi");

cs.setDouble(3, 85.5);
```

Parameter indexes start at **1**:

```text
?       ?       ?
↓       ↓       ↓
1       2       3
```

---

# 4. OUT Parameters

## 🟢 LEVEL 1 — Basic

An `OUT` parameter sends a value:

```text
Database → Java
```

Memory:

> **OUT = Output comes out.**

---

## 🟡 LEVEL 2 — Understanding

Suppose the procedure has:

```sql
OUT p_name VARCHAR(100)
```

The database procedure produces the student's name.

Java first registers the parameter:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

Then:

```java
cs.execute();
```

Finally:

```java
String name =
    cs.getString(2);
```

Flow:

```text
Stored Procedure
      ↓
OUT parameter
      ↓
Java
```

---

## 🔴 LEVEL 3 — Deep

For an OUT parameter, remember:

```text
register
   ↓
execute
   ↓
get
```

Example:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);

cs.execute();

String name =
    cs.getString(2);
```

### Why register?

JDBC needs to know:

```text
Parameter 2
    ↓
is an OUT parameter
    ↓
SQL type = VARCHAR
```

### Important

This:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

**does not retrieve the value.**

It only registers the output.

Retrieval happens with:

```java
cs.getString(2);
```

---

# 5. INOUT Parameters

## 🟢 LEVEL 1 — Basic

`INOUT` means:

> The parameter is both input and output.

Direction:

```text
Java → Database → Java
```

---

## 🟡 LEVEL 2 — Understanding

Suppose Java sends:

```text
80
```

The procedure adds 5:

```text
80 + 5 = 85
```

Java receives:

```text
85
```

So:

```text
80
 ↓
Java
 ↓
INOUT
 ↓
Procedure
 ↓
85
 ↓
Java
```

---

## 🔴 LEVEL 3 — Deep

Because INOUT has both directions, you need both:

```java
setXXX()
```

and:

```java
registerOutParameter()
```

Example:

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

### Remember

```text
INOUT
 ↓
set
 ↓
register
 ↓
execute
 ↓
get
```

---

# 6. `registerOutParameter()`

## 🟢 LEVEL 1 — Basic

`registerOutParameter()` tells JDBC:

> "This parameter will produce an output value."

Example:

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);
```

---

## 🟡 LEVEL 2 — Understanding

It has two important pieces:

```java
registerOutParameter(
    parameterIndex,
    sqlType
);
```

Example:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

### `2`

Means:

```text
Parameter number 2
```

### `Types.VARCHAR`

Means:

```text
The output SQL type is VARCHAR.
```

---

## 🔴 LEVEL 3 — Deep

`registerOutParameter()` is a **registration step**, not a retrieval step.

The three stages are:

```text
1. REGISTER
   ↓
cs.registerOutParameter(...)

2. EXECUTE
   ↓
cs.execute()

3. RETRIEVE
   ↓
cs.getXXX(...)
```

For example:

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);

cs.execute();

int result =
    cs.getInt(2);
```

### Common SQL types

```java
Types.INTEGER
Types.VARCHAR
Types.DOUBLE
Types.DECIMAL
Types.DATE
Types.TIMESTAMP
Types.BOOLEAN
```

The getter should correspond appropriately:

```java
Types.INTEGER
      ↓
getInt()

Types.VARCHAR
      ↓
getString()

Types.DOUBLE
      ↓
getDouble()
```

---

# 7. All Three Parameters Together

This is the most important comparison.

| Parameter | Direction        | `setXXX()` | `registerOutParameter()` | `getXXX()` |
| --------- | ---------------- | ---------: | -----------------------: | ---------: |
| **IN**    | Java → DB        |          ✅ |                        ❌ |          ❌ |
| **OUT**   | DB → Java        |          ❌ |                        ✅ |          ✅ |
| **INOUT** | Java → DB → Java |          ✅ |                        ✅ |          ✅ |

### Easy memory

```text
IN
→ GIVE

OUT
→ GET

INOUT
→ GIVE + GET
```

---

# 8. Complete Example

Suppose we have this procedure:

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

// IN
cs.setInt(1, 101);

// OUT
cs.registerOutParameter(
    2,
    Types.VARCHAR
);

// Execute
cs.execute();

// Get OUT value
String name =
    cs.getString(2);

System.out.println(name);

cs.close();
con.close();
```

Flow:

```text
                 Java
                  │
                  │ 101
                  ↓
              IN #1
                  │
                  ↓
          Stored Procedure
                  │
                  │ "Ravi"
                  ↓
              OUT #2
                  │
                  ↓
                 Java
```

---

# 9. The Complete 3LEVEL Summary

## 🟢 LEVEL 1 — Remember

```text
CallableStatement
→ calls stored procedures/functions

Stored Procedure
→ database-side program

IN
→ Java → DB

OUT
→ DB → Java

INOUT
→ Java → DB → Java

registerOutParameter()
→ registers output parameter
```

---

## 🟡 LEVEL 2 — Understand

```text
IN:
setXXX()
    ↓
execute()

OUT:
registerOutParameter()
    ↓
execute()
    ↓
getXXX()

INOUT:
setXXX()
    ↓
registerOutParameter()
    ↓
execute()
    ↓
getXXX()
```

---

## 🔴 LEVEL 3 — Master

The complete architecture is:

```text
                         Java
                          │
                          ↓
                 CallableStatement
                          │
                    prepareCall()
                          │
                          ↓
                     JDBC Driver
                          │
                          ↓
                      Database
                          │
                          ↓
                  Stored Procedure
                          │
              ┌───────────┼───────────┐
              ↓           ↓           ↓
             IN          OUT        INOUT
              │           │           │
          setXXX()    register()   setXXX()
                          │        register()
              │           │           │
              └───────────┼───────────┘
                          ↓
                       execute()
                          │
                    ┌─────┴─────┐
                    ↓             ↓
                ResultSet      OUT value
                                  ↓
                               getXXX()
```

# 🔥 Final Formula

```text
┌───────────────────────────────────────────┐
│              CallableStatement            │
├───────────────────────────────────────────┤
│                                           │
│ IN     = set → execute                    │
│                                           │
│ OUT    = register → execute → get         │
│                                           │
│ INOUT  = set → register → execute → get   │
│                                           │
└───────────────────────────────────────────┘
```

If you can look at a procedure and correctly identify **which parameter is IN, which is OUT, which is INOUT, and therefore whether to use `setXXX()`, `registerOutParameter()`, and `getXXX()`**, you have mastered the fundamental working of `CallableStatement`.
