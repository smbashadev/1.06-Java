# 10. CallableStatement in Java — ONEPAGE

`CallableStatement` is the JDBC interface used when Java needs to **call a stored procedure or stored function in a database**.

The easiest mental model is:

```text
Java Application
       ↓
CallableStatement
       ↓
Stored Procedure
       ↓
Database
       ↓
Result / OUT parameters
       ↓
Java
```

---

# 1. CallableStatement

## What is CallableStatement?

`CallableStatement` is an interface in:

```java
java.sql.CallableStatement
```

It extends:

```text
Statement
   ↑
PreparedStatement
   ↑
CallableStatement
```

It is specifically designed to execute **stored procedures and stored functions**.

Create it using:

```java
CallableStatement cs =
    con.prepareCall("{call getStudent(?)}");
```

Then execute:

```java
cs.execute();
```

### Basic syntax

```java
CallableStatement cs =
    con.prepareCall("{call procedureName(?)}");
```

---

## Why use CallableStatement?

Instead of writing all database logic in Java:

```text
Java
 ↓
many SQL statements
 ↓
Database
```

you can put reusable database logic into a stored procedure:

```text
Java
 ↓
call procedure
 ↓
Database executes procedure
```

This is especially useful when the database already contains stored procedures or when database-side logic is intentionally centralized.

---

# 2. Stored Procedures

## What is a stored procedure?

A **stored procedure** is a named set of SQL/database statements stored inside the database.

For example, conceptually:

```sql
CREATE PROCEDURE getStudent(IN p_id INT)
BEGIN
    SELECT id, name, marks
    FROM student
    WHERE id = p_id;
END;
```

The procedure is stored in the database.

Java can call it using:

```java
CallableStatement cs =
    con.prepareCall("{call getStudent(?)}");

cs.setInt(1, 101);

cs.execute();
```

---

## Procedure vs normal SQL

### Normal SQL

Java sends:

```sql
SELECT * FROM student WHERE id = 101;
```

### Stored procedure

Java sends a request like:

```text
Call getStudent with 101
```

The database executes the stored procedure.

```text
Java
 ↓
CallableStatement
 ↓
CALL getStudent(101)
 ↓
Database procedure
 ↓
Result
```

---

# 3. IN Parameters

An **IN parameter** sends a value **from Java to the stored procedure**.

Think:

```text
Java
  ↓
  IN
  ↓
Procedure
```

Example procedure:

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
    con.prepareCall("{call getStudent(?)}");

cs.setInt(1, 101);

cs.execute();
```

Here:

```java
cs.setInt(1, 101);
```

means:

> Put `101` into the first parameter.

### Common IN setters

```java
cs.setInt(1, 101);
cs.setString(2, "Ravi");
cs.setDouble(3, 85.5);
```

The parameter is supplied **before execution**.

### Memory trick

```text
IN
↓
Java → Database
```

---

# 4. OUT Parameters

An **OUT parameter** sends a value **from the stored procedure back to Java**.

Think:

```text
Java
  ↓
Procedure
  ↓
  OUT
  ↓
Java
```

Example procedure:

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

System.out.println(name);
```

The important sequence is:

```text
1. set IN parameter
2. register OUT parameter
3. execute procedure
4. retrieve OUT value
```

---

## Why do we need registerOutParameter()?

Java needs to tell JDBC:

> "Parameter number 2 is an OUT parameter, and I expect its SQL type to be VARCHAR."

That's what:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

does.

Then after execution:

```java
String name =
    cs.getString(2);
```

retrieves the returned value.

### Memory trick

```text
IN
 ↓
setXXX()

OUT
 ↓
registerOutParameter()
 ↓
execute()
 ↓
getXXX()
```

---

# 5. INOUT Parameters

An **INOUT parameter does both jobs**.

It sends a value:

```text
Java → Database
```

and then receives a modified value:

```text
Database → Java
```

So:

```text
INOUT

Java
 ↓
initial value
 ↓
Procedure
 ↓
modified value
 ↓
Java
```

Example procedure:

```sql
CREATE PROCEDURE increaseMarks(
    INOUT p_marks INT
)
BEGIN
    SET p_marks = p_marks + 5;
END;
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

Here:

```text
80
 ↓
IN
 ↓
Procedure adds 5
 ↓
85
 ↓
OUT
 ↓
Java
```

### Important

For `INOUT`, you generally need **both**:

```java
cs.setInt(...)
```

and:

```java
cs.registerOutParameter(...)
```

---

# 6. `registerOutParameter()`

This is one of the most important `CallableStatement` methods.

## Purpose

It registers a parameter as an **OUT parameter** and tells JDBC its SQL type.

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

Meaning:

```text
Parameter 2
     ↓
is OUT
     ↓
SQL type = INTEGER
```

---

## Common SQL types

```java
Types.INTEGER
Types.VARCHAR
Types.DOUBLE
Types.DECIMAL
Types.DATE
Types.BOOLEAN
```

Example:

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

Then retrieve:

```java
String value =
    cs.getString(2);
```

For integer:

```java
cs.registerOutParameter(
    2,
    Types.INTEGER
);

int value =
    cs.getInt(2);
```

---

# 7. Complete IN + OUT Example

Suppose the database has:

```text
student
--------------------
id    name
101   Ravi
102   Kumar
```

Stored procedure:

```sql
CREATE PROCEDURE getName(
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
CallableStatement cs =
    con.prepareCall(
        "{call getName(?, ?)}"
    );

cs.setInt(1, 101);

cs.registerOutParameter(
    2,
    Types.VARCHAR
);

cs.execute();

String name =
    cs.getString(2);

System.out.println(name);
```

Output:

```text
Ravi
```

Flow:

```text
Java
 │
 │ 101
 ↓
IN parameter
 │
 ↓
Stored Procedure
 │
 │ "Ravi"
 ↓
OUT parameter
 │
 ↓
Java
```

---

# 8. IN vs OUT vs INOUT

| Parameter | Direction | Java before execution | Register? | Java after execution       |
| --------- | --------- | --------------------- | --------- | -------------------------- |
| `IN`      | Java → DB | `setXXX()`            | ❌ No      | Usually not used as output |
| `OUT`     | DB → Java | No input value        | ✅ Yes     | `getXXX()`                 |
| `INOUT`   | Java ↔ DB | `setXXX()`            | ✅ Yes     | `getXXX()`                 |

### Memorize:

```text
IN
→ set

OUT
→ register → get

INOUT
→ set → register → get
```

---

# 9. Complete CallableStatement Flow

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
Retrieve OUT parameters
    ↓
Close CallableStatement
```

Example:

```java
CallableStatement cs =
    con.prepareCall(
        "{call procedureName(?, ?, ?)}"
    );

cs.setInt(1, 101);

cs.setString(2, "Ravi");

cs.registerOutParameter(
    3,
    Types.INTEGER
);

cs.execute();

int result =
    cs.getInt(3);

cs.close();
```

---

# 🔥 ONEPAGE Doubt-Killer

### `CallableStatement`

> Used to call stored procedures/functions.

```java
con.prepareCall(...)
```

---

### Stored Procedure

> Named database-side program containing SQL/database logic.

---

### `IN`

> Input goes **Java → Database**.

```java
cs.setInt(1, 101);
```

---

### `OUT`

> Output goes **Database → Java**.

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

### `INOUT`

> Value goes **Java → Database → Java**.

```java
cs.setInt(1, 80);

cs.registerOutParameter(
    1,
    Types.INTEGER
);

cs.execute();

int value =
    cs.getInt(1);
```

---

### `registerOutParameter()`

> Tells JDBC which parameter is an OUT parameter and what SQL type it has.

```java
cs.registerOutParameter(
    2,
    Types.VARCHAR
);
```

---

## 🧠 Ultimate Memory Formula

```text
                 CallableStatement
                         │
                         ↓
                  Stored Procedure
                         │
          ┌──────────────┼──────────────┐
          ↓              ↓              ↓
         IN             OUT           INOUT
          │              │              │
       setXXX()      register()      setXXX()
                         │          + register()
                         ↓              │
                       getXXX()         ↓
                                      getXXX()
```

### The most important sequence:

```text
IN:
set → execute

OUT:
register → execute → get

INOUT:
set → register → execute → get
```

If you remember just that sequence, the basic `CallableStatement` API becomes much easier to use.
