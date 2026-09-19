# 14. JDBC Exceptions in Java — ONEPAGE

JDBC programs communicate with a database, so many things can go wrong:

```text
Java Program
     ↓
JDBC
     ↓
JDBC Driver
     ↓
Database
```

For example:

* Database server is down
* Wrong username/password
* Wrong database URL
* SQL syntax error
* Table doesn't exist
* Duplicate key
* Constraint violation
* Connection failure

JDBC primarily represents these database-related problems using **`SQLException` and its subclasses**.

---

# 1. `SQLException`

## What is `SQLException`?

`SQLException` is a Java exception class used to represent problems that occur while interacting with a database through JDBC.

It belongs to:

```java
java.sql.SQLException
```

Example:

```java
try {
    Connection con =
        DriverManager.getConnection(url, username, password);
} catch (SQLException e) {
    e.printStackTrace();
}
```

The basic hierarchy is:

```text
Throwable
   │
   └── Exception
         │
         └── SQLException
               │
               ├── SQLTimeoutException
               ├── SQLIntegrityConstraintViolationException
               ├── SQLSyntaxErrorException
               └── ...
```

---

## Why can `SQLException` occur?

For example:

```java
Connection con =
    DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/college",
        "root",
        "wrongPassword"
    );
```

The database may reject the connection.

Another example:

```java
Statement st = con.createStatement();

st.executeQuery(
    "SELECT * FROM studentt"
);
```

If `studentt` doesn't exist, the database/driver can report an SQL error.

---

## Important information inside `SQLException`

A `SQLException` can provide several useful pieces of information:

```java
e.getMessage();
e.getSQLState();
e.getErrorCode();
```

So:

```text
SQLException
    │
    ├── Message
    ├── SQLState
    ├── Error Code
    └── Cause / chained exceptions
```

---

# 2. SQLState

## What is SQLState?

**SQLState** is a standardized five-character code that describes the general condition associated with an SQL operation.

You obtain it using:

```java
e.getSQLState();
```

Example:

```java
catch (SQLException e) {

    System.out.println(
        "SQLState: " + e.getSQLState()
    );
}
```

It generally looks like:

```text
XXXXX
```

For example:

```text
23000
```

---

## Why is SQLState useful?

Suppose the same Java program can work with different database systems.

Database-specific error numbers may differ.

SQLState provides a more standardized classification of SQL/database conditions.

Conceptually:

```text
SQLState
   ↓
General / standardized condition
```

whereas:

```text
Error Code
   ↓
Database/driver-specific numeric information
```

---

# 3. Error Codes

## What is an error code?

An error code is a numeric code associated with a database error.

Retrieve it using:

```java
e.getErrorCode();
```

Example:

```java
catch (SQLException e) {

    System.out.println(
        "Error Code: " + e.getErrorCode()
    );
}
```

The exact meaning of a numeric error code is usually **database/vendor-specific**.

For example:

```text
Database
   ↓
Vendor-specific error
   ↓
Numeric error code
```

Therefore, don't memorize a particular number as a universal JDBC error code.

---

# SQLState vs Error Code

This is a very important distinction.

| Feature               | SQLState                     | Error Code                         |
| --------------------- | ---------------------------- | ---------------------------------- |
| Format                | Usually 5 characters         | Integer                            |
| Purpose               | Standardized classification  | Vendor/driver-specific information |
| Obtained using        | `getSQLState()`              | `getErrorCode()`                   |
| Database independent? | More portable                | Usually less portable              |
| Useful for            | General error classification | Database-specific diagnosis        |

Remember:

```text
SQLState  → standardized classification
ErrorCode → database/vendor-specific detail
```

---

# 4. Exception Handling

## Why do we handle JDBC exceptions?

Because database operations can fail at runtime.

For example:

```java
try {

    Connection con =
        DriverManager.getConnection(
            url,
            username,
            password
        );

} catch (SQLException e) {

    System.out.println(
        "Database error: " + e.getMessage()
    );
}
```

The basic pattern is:

```text
try
 ↓
JDBC operation
 ↓
Exception?
 ↓
catch(SQLException)
 ↓
Handle error
```

---

# `printStackTrace()`

During development, you can use:

```java
catch (SQLException e) {
    e.printStackTrace();
}
```

It prints diagnostic information including the exception stack trace.

For production applications, you would generally use appropriate logging and error-handling practices rather than simply printing the stack trace.

---

# Handling SQLState and Error Code Together

A useful diagnostic pattern is:

```java
try {

    Statement st = con.createStatement();

    st.executeQuery(
        "SELECT * FROM student"
    );

} catch (SQLException e) {

    System.out.println(
        "Message: " + e.getMessage()
    );

    System.out.println(
        "SQLState: " + e.getSQLState()
    );

    System.out.println(
        "Error Code: " + e.getErrorCode()
    );
}
```

You get:

```text
Message
   ↓
Human-readable description

SQLState
   ↓
General SQL condition

Error Code
   ↓
Vendor-specific diagnostic information
```

---

# Handling Different JDBC Exceptions

You can catch a more specific JDBC exception when appropriate.

For example:

```java
try {
    // JDBC operation
}
catch (SQLIntegrityConstraintViolationException e) {
    // Handle constraint violation
}
catch (SQLException e) {
    // Handle other SQL errors
}
```

The specific exception must come **before** its broader parent:

```text
Specific exception
       ↓
SQLException
```

because:

```java
catch (SQLException e)
```

would otherwise catch the subclass first.

---

# `SQLException` Chaining

A JDBC operation can sometimes produce multiple related SQL exceptions.

`SQLException` supports chaining.

You can iterate through them:

```java
catch (SQLException e) {

    SQLException current = e;

    while (current != null) {

        System.out.println(
            current.getMessage()
        );

        current = current.getNextException();
    }
}
```

Conceptually:

```text
SQLException #1
      ↓
SQLException #2
      ↓
SQLException #3
```

This can be useful when the driver/database provides multiple related diagnostics.

---

# Important Exception-Handling Pattern

For JDBC resources, prefer **try-with-resources**.

Instead of manually closing everything:

```java
Connection con = null;
Statement st = null;
ResultSet rs = null;

try {
    // ...
}
catch (SQLException e) {
    // ...
}
finally {
    // manually close resources
}
```

you can use:

```java
try (
    Connection con =
        DriverManager.getConnection(url, user, password);

    PreparedStatement ps =
        con.prepareStatement(
            "SELECT * FROM student"
        );

    ResultSet rs =
        ps.executeQuery()
) {

    while (rs.next()) {
        System.out.println(
            rs.getInt("id")
        );
    }

} catch (SQLException e) {

    e.printStackTrace();
}
```

The JDBC resources are automatically closed when the try block finishes.

---

# ⭐ ONEPAGE MASTER MAP

```text
                 JDBC EXCEPTIONS
                       │
                       ↓
                 SQLException
                       │
        ┌──────────────┼──────────────┐
        ↓              ↓              ↓
     Message        SQLState       Error Code
        │              │              │
        ↓              ↓              ↓
   Human-readable   Standardized    Vendor/
    information     condition       database-specific
```

Exception handling:

```text
try
 │
 └── JDBC operation
          │
          ↓
     SQLException
          │
     ┌────┴────┐
     ↓         ↓
  handle     diagnose
     │         │
     │    ┌────┼────┐
     │    ↓    ↓    ↓
     │ Message State Code
     │
     ↓
 logging / recovery / appropriate response
```

---

# 🔥 Final Memory Rules

1. **`SQLException`** represents database/JDBC-related errors.

2. Package:

   ```java
   java.sql.SQLException
   ```

3. Get the error message:

   ```java
   e.getMessage()
   ```

4. Get SQLState:

   ```java
   e.getSQLState()
   ```

5. Get vendor error code:

   ```java
   e.getErrorCode()
   ```

6. **SQLState** is intended as a standardized classification.

7. **Error code** is generally database/vendor-specific.

8. Use:

   ```java
   catch (SQLException e)
   ```

   to handle general JDBC SQL errors.

9. More specific JDBC exception subclasses can be caught before `SQLException`.

10. `SQLException` can contain **chained exceptions**, accessible with:

```java
e.getNextException()
```

11. Prefer **try-with-resources** for automatically closing JDBC resources.

### Golden rule:

> **`SQLException` tells you that a JDBC/database operation failed; `getMessage()` tells you what happened, `getSQLState()` gives a standardized classification, and `getErrorCode()` gives database/driver-specific diagnostic information.**
