# 9. ResultSet in Java — TEACHME

Let's learn `ResultSet` as if we are sitting together and running a JDBC program step by step.

The most important idea is this:

> **`ResultSet` is the object that lets Java read the rows returned by a SQL `SELECT` query.**

Imagine your database contains:

```text
student
+-----+--------+-------+
| id  | name   | marks |
+-----+--------+-------+
| 101 | Ravi   | 85.5  |
| 102 | Kumar  | 90.0  |
| 103 | Ali    | 78.5  |
+-----+--------+-------+
```

Java executes:

```sql
SELECT id, name, marks FROM student;
```

The database sends the result back to Java, and Java receives it through a:

```java
ResultSet
```

Think of it as:

```text
Database
   ↓
SELECT query
   ↓
executeQuery()
   ↓
ResultSet
   ↓
Read rows one by one
```

---

# 1. ResultSet Interface

## 1.1 First understand the word "ResultSet"

Break the word into two parts:

```text
Result + Set
```

The **result** is what the database gives you after executing a query.

The **set** is the collection of rows in that result.

For example:

```sql
SELECT * FROM student;
```

might produce:

```text
101 Ravi  85.5
102 Kumar 90.0
103 Ali   78.5
```

That returned data is represented to Java through `ResultSet`.

---

## 1.2 Is ResultSet a class?

No.

`ResultSet` is an **interface**.

It belongs to:

```java
java.sql
```

So:

```java
import java.sql.ResultSet;
```

You normally don't write:

```java
ResultSet rs = new ResultSet(); // ❌
```

Instead, JDBC gives you a ResultSet when you execute a query:

```java
ResultSet rs = ps.executeQuery();
```

---

# 1.3 How do we get a ResultSet?

Suppose:

```java
String sql =
    "SELECT id, name, marks FROM student";

PreparedStatement ps =
    con.prepareStatement(sql);
```

Now:

```java
ResultSet rs =
    ps.executeQuery();
```

That's the important moment.

```text
PreparedStatement
       ↓
executeQuery()
       ↓
ResultSet
```

---

# 1.4 What does ResultSet contain?

Think of a ResultSet like a table:

```text
       id       name      marks
        ↓         ↓         ↓

       101       Ravi      85.5
       102       Kumar     90.0
       103       Ali       78.5
```

But there is one additional important concept:

## The cursor

A ResultSet has a **cursor**.

Think of the cursor as your finger pointing at one row.

Initially:

```text
Cursor
  ↓
BEFORE FIRST

101 Ravi  85.5
102 Kumar 90.0
103 Ali   78.5
```

The cursor is initially **before the first row**.

This brings us to the most important ResultSet method:

```text
next()
```

---

# 2. `next()`

## 2.1 What is next()?

`next()` moves the ResultSet cursor to the next row.

Syntax:

```java
rs.next();
```

It returns a:

```java
boolean
```

Meaning:

```text
true  → a row exists
false → no more rows
```

---

# 2.2 Why do we need next()?

Suppose the ResultSet contains:

```text
101 Ravi
102 Kumar
103 Ali
```

Initially:

```text
Cursor
  ↓
BEFORE FIRST

101 Ravi
102 Kumar
103 Ali
```

Now:

```java
rs.next();
```

The cursor moves:

```text
101 Ravi
↑
Cursor

102 Kumar
103 Ali
```

Now we can read Ravi's data.

---

# 2.3 Second next()

Call:

```java
rs.next();
```

Again.

Now:

```text
101 Ravi

102 Kumar
↑
Cursor

103 Ali
```

The cursor is now on Kumar.

Another:

```java
rs.next();
```

moves to Ali.

---

# 2.4 What happens after the last row?

After Ali:

```java
rs.next();
```

returns:

```java
false
```

because there is no fourth row.

So:

```text
101 → true
102 → true
103 → true
end → false
```

---

# 2.5 Why is `while(rs.next())` so popular?

Because it automatically handles this process.

```java
while (rs.next()) {
    // process current row
}
```

Think:

```text
next()
 ↓
Is there a row?
 ↓
YES → process it
 ↓
next()
 ↓
Is there a row?
 ↓
YES → process it
 ↓
...
 ↓
NO
 ↓
stop
```

This is the standard ResultSet pattern.

---

# 2.6 Example

```java
while (rs.next()) {

    System.out.println(
        rs.getInt("id")
    );
}
```

Output:

```text
101
102
103
```

---

# 2.7 `next()` doesn't retrieve data

This is important.

`next()` does **not** give you the row data.

It only moves the cursor.

```text
next()
 ↓
Move cursor
```

Then:

```text
getInt()
getString()
getDouble()
getObject()
 ↓
Read values
```

So:

```text
next() = MOVE
getXXX() = READ
```

🔥 Remember this forever.

---

# 3. `getInt()`

Now the cursor is on a row.

Suppose:

```text
101 Ravi 85.5
 ↑
current row
```

We want:

```text
101
```

Use:

```java
int id = rs.getInt("id");
```

---

## 3.1 What does getInt() do?

It retrieves a column value as a Java `int`.

Example:

```java
int id =
    rs.getInt("id");
```

---

# 3.2 Using column index

You can also write:

```java
int id =
    rs.getInt(1);
```

Why `1`?

Because JDBC column indexes start from **1**.

```text
1 → id
2 → name
3 → marks
```

Not:

```text
0 → id
1 → name
2 → marks
```

That is a common beginner mistake.

---

# 3.3 Complete example

```java
while (rs.next()) {

    int id =
        rs.getInt("id");

    System.out.println(id);
}
```

Output:

```text
101
102
103
```

---

# 3.4 What if id is NULL?

Here's a slightly advanced but important point.

`int` is a primitive.

It cannot contain `null`.

If the database value is SQL `NULL`, `getInt()` returns:

```text
0
```

So how do we know whether it was actually `0` or SQL `NULL`?

Use:

```java
rs.wasNull();
```

Example:

```java
int id =
    rs.getInt("id");

if (rs.wasNull()) {
    System.out.println("ID is NULL");
}
```

So remember:

```text
getInt()
   ↓
primitive int
   ↓
SQL NULL → default primitive value
   ↓
use wasNull() to check
```

---

# 4. `getString()`

Suppose we want:

```text
Ravi
```

The database column is:

```text
name
```

Use:

```java
String name =
    rs.getString("name");
```

---

## 4.1 What does getString() do?

It retrieves the column value as a Java `String`.

Example:

```java
while (rs.next()) {

    String name =
        rs.getString("name");

    System.out.println(name);
}
```

Output:

```text
Ravi
Kumar
Ali
```

---

# 4.2 Using column index

Because:

```text
1 → id
2 → name
3 → marks
```

you can write:

```java
String name =
    rs.getString(2);
```

But:

```java
rs.getString("name");
```

is usually easier to understand.

---

# 4.3 What if name is NULL?

For a String:

```java
String name =
    rs.getString("name");
```

SQL `NULL` becomes Java:

```java
null
```

So:

```java
if (name == null) {
    System.out.println("Name is NULL");
}
```

---

# 5. `getDouble()`

Suppose the current row is:

```text
101 Ravi 85.5
```

We want:

```text
85.5
```

Use:

```java
double marks =
    rs.getDouble("marks");
```

---

# 5.1 What does getDouble() do?

It retrieves a database value as a Java `double`.

Example:

```java
while (rs.next()) {

    double marks =
        rs.getDouble("marks");

    System.out.println(marks);
}
```

Output:

```text
85.5
90.0
78.5
```

---

# 5.2 Using column index

Because `marks` is column 3:

```java
double marks =
    rs.getDouble(3);
```

---

# 5.3 NULL problem

Like `getInt()`:

```java
double marks =
    rs.getDouble("marks");
```

If SQL value is `NULL`, the primitive result is:

```text
0.0
```

Use:

```java
rs.wasNull();
```

to determine whether the SQL value was actually `NULL`.

---

# 5.4 Important real-world point

Don't automatically use `double` for money.

For example:

```text
salary = 12345.67
```

For exact decimal/financial values, Java's:

```java
BigDecimal
```

is generally more appropriate.

JDBC provides:

```java
rs.getBigDecimal("salary");
```

So:

```text
Marks/approximate numeric calculation
        ↓
double may be suitable

Money/exact decimal
        ↓
BigDecimal
```

---

# 6. `getObject()`

Now let's say:

> "I don't want to specify the exact getter. Give me the value as an Object."

Use:

```java
Object value =
    rs.getObject("name");
```

---

# 6.1 What does getObject() do?

It retrieves a database column as a Java `Object`.

The JDBC driver determines the appropriate Java representation.

Conceptually:

```text
Database SQL type
       ↓
JDBC Driver
       ↓
Java Object
```

For example, depending on the driver/database:

```text
INTEGER
   ↓
Integer

VARCHAR
   ↓
String

DECIMAL
   ↓
BigDecimal
```

---

# 6.2 Why would we use getObject()?

Imagine you're building a generic database viewer.

You don't know beforehand whether the column is:

```text
int
String
double
Date
BigDecimal
...
```

Instead of writing a different getter for every possible column:

```java
getInt()
getString()
getDouble()
...
```

you can use:

```java
getObject()
```

---

# 6.3 Example

```java
while (rs.next()) {

    Object value =
        rs.getObject(1);

    System.out.println(value);
}
```

---

# 6.4 Typed getObject()

You can also request a specific Java type:

```java
String name =
    rs.getObject(
        "name",
        String.class
    );
```

Another example:

```java
Integer id =
    rs.getObject(
        "id",
        Integer.class
    );
```

---

# 6.5 getObject() and NULL

This is convenient:

```java
Object value =
    rs.getObject("name");
```

If the database contains SQL `NULL`, the result can simply be:

```java
null
```

No `wasNull()` is required merely to distinguish `null` from an ordinary object reference.

---

# 7. ResultSet Types

Now we're going to learn a slightly confusing topic.

There are different **types of ResultSet**.

Ask yourself:

> "How should the cursor be allowed to move?"

The answer is controlled by the ResultSet type.

There are three major types:

```java
ResultSet.TYPE_FORWARD_ONLY
ResultSet.TYPE_SCROLL_INSENSITIVE
ResultSet.TYPE_SCROLL_SENSITIVE
```

---

# 7.1 TYPE_FORWARD_ONLY

This is the simplest.

```java
ResultSet.TYPE_FORWARD_ONLY
```

Think:

```text
ONLY FORWARD
```

Cursor:

```text
BEFORE FIRST
     ↓
ROW 1
     ↓
ROW 2
     ↓
ROW 3
     ↓
AFTER LAST
```

You normally use:

```java
rs.next();
```

---

## When should I use it?

When you simply want:

```text
Read row
 ↓
Read next row
 ↓
Read next row
 ↓
Finish
```

This is very common.

---

# 7.2 TYPE_SCROLL_INSENSITIVE

Now suppose you want the cursor to move around.

You may use:

```java
ResultSet.TYPE_SCROLL_INSENSITIVE
```

Now methods such as these can be available:

```java
rs.next();
rs.previous();
rs.first();
rs.last();
rs.absolute(3);
```

Think:

```text
       ↑
       ↓
← ROW → ROW → ROW →
       ↑
       ↓
```

The cursor can scroll.

---

## What does "insensitive" mean?

It means the ResultSet generally does not reflect certain changes made to the underlying database after the result was produced.

Don't interpret it as:

> "The database cannot change."

The database can change.

The point is that the ResultSet is not generally expected to reflect those later changes.

---

# 7.3 TYPE_SCROLL_SENSITIVE

The third type is:

```java
ResultSet.TYPE_SCROLL_SENSITIVE
```

It is also scrollable.

But it is intended to be sensitive to certain changes in the underlying database data.

So:

```text
FORWARD_ONLY
    ↓
Forward movement

SCROLL_INSENSITIVE
    ↓
Scroll + generally not sensitive to later changes

SCROLL_SENSITIVE
    ↓
Scroll + intended sensitivity to certain changes
```

---

# 7.4 Very important: Driver support

You might request:

```java
TYPE_SCROLL_SENSITIVE
```

but your particular database/JDBC driver might not fully support it.

Therefore, JDBC may provide different capabilities than what you requested.

You can inspect what you actually received:

```java
rs.getType();
```

This is a very important real-world concept.

---

# 7.5 Creating a scrollable ResultSet

Example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY
    );
```

Then:

```java
ResultSet rs =
    st.executeQuery(
        "SELECT * FROM student"
    );
```

Now you can potentially do:

```java
rs.first();
```

or:

```java
rs.last();
```

or:

```java
rs.absolute(2);
```

---

# 8. ResultSet Concurrency

Now we come to another concept that students often mix up with ResultSet type.

Let's separate them.

## ResultSet Type asks:

> **How can I move the cursor?**

## ResultSet Concurrency asks:

> **Can I update the database through the ResultSet?**

There are two major modes:

```java
ResultSet.CONCUR_READ_ONLY
ResultSet.CONCUR_UPDATABLE
```

---

# 8.1 CONCUR_READ_ONLY

This means:

> "I only want to read the ResultSet."

Example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.CONCUR_READ_ONLY
    );
```

Then:

```java
while (rs.next()) {

    System.out.println(
        rs.getString("name")
    );
}
```

This is the most common style.

---

# 8.2 CONCUR_UPDATABLE

Now imagine:

> "I want to modify a database row through the ResultSet."

You can request:

```java
ResultSet.CONCUR_UPDATABLE
```

For example:

```java
Statement st =
    con.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_UPDATABLE
    );
```

Then, if the driver/database/query support an updatable ResultSet:

```java
rs.next();

rs.updateString(
    "name",
    "Ravi Kumar"
);

rs.updateRow();
```

Conceptually:

```text
ResultSet
   ↓
Current database row
   ↓
updateString()
   ↓
updateRow()
   ↓
Database updated
```

---

# 8.3 Does CONCUR_UPDATABLE always work?

**No.**

This is extremely important.

Writing:

```java
CONCUR_UPDATABLE
```

doesn't magically make every SQL query updatable.

The database and driver must support it, and the query must be eligible.

For example, complicated queries involving:

```text
JOIN
GROUP BY
aggregate functions
calculated columns
```

may not produce an updatable ResultSet.

---

# 9. Type vs Concurrency — The Easiest Way to Understand

Imagine you're controlling a car.

### ResultSet TYPE

asks:

> "How can the car move?"

```text
Forward only
or
Move around
```

### ResultSet CONCURRENCY

asks:

> "Can I modify the thing I'm looking at?"

```text
Read only
or
Update
```

Therefore:

```text
ResultSet
    │
    ├── TYPE
    │    ├── FORWARD_ONLY
    │    ├── SCROLL_INSENSITIVE
    │    └── SCROLL_SENSITIVE
    │
    └── CONCURRENCY
         ├── READ_ONLY
         └── UPDATABLE
```

🔥 **Never mix these two categories.**

---

# 10. Let's Build One Complete Program

Suppose our database contains:

```text
student

id     name      marks
101    Ravi      85.5
102    Kumar     90.0
103    Ali       78.5
```

Java:

```java
import java.sql.*;

public class ResultSetDemo {

    public static void main(String[] args)
            throws Exception {

        Connection con =
            DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testdb",
                "root",
                "password"
            );

        String sql =
            "SELECT id, name, marks " +
            "FROM student";

        PreparedStatement ps =
            con.prepareStatement(sql);

        ResultSet rs =
            ps.executeQuery();

        while (rs.next()) {

            int id =
                rs.getInt("id");

            String name =
                rs.getString("name");

            double marks =
                rs.getDouble("marks");

            System.out.println(
                id + " " +
                name + " " +
                marks
            );
        }

        rs.close();
        ps.close();
        con.close();
    }
}
```

---

# 11. Understand the Program Like a Story

Don't memorize the code blindly.

Understand the story.

### Step 1

Create SQL:

```java
String sql =
    "SELECT id, name, marks FROM student";
```

Meaning:

> Database, give me these columns.

---

### Step 2

Create PreparedStatement:

```java
PreparedStatement ps =
    con.prepareStatement(sql);
```

Meaning:

> JDBC, prepare this SQL statement.

---

### Step 3

Execute:

```java
ResultSet rs =
    ps.executeQuery();
```

Meaning:

> Database, execute the SELECT and give me the result.

The result becomes:

```text
ResultSet
```

---

### Step 4

Move to the first row:

```java
rs.next();
```

---

### Step 5

Read columns:

```java
rs.getInt("id");
rs.getString("name");
rs.getDouble("marks");
```

---

### Step 6

Move to next row:

```java
rs.next();
```

---

### Step 7

Continue until:

```java
rs.next() == false
```

---

# 12. The Most Important Difference

Students frequently confuse:

```java
next()
```

with:

```java
getInt()
```

Remember:

### `next()`

**Moves**

```text
Row 1
 ↓
Row 2
 ↓
Row 3
```

### `getXXX()`

**Reads**

```text
Current Row
    ↓
id
name
marks
```

So:

```text
next() → MOVE

getInt() → READ integer

getString() → READ String

getDouble() → READ double

getObject() → READ Object
```

---

# 13. Column Index vs Column Name

Suppose:

```text
1 → id
2 → name
3 → marks
```

You can write:

```java
rs.getInt(1);
rs.getString(2);
rs.getDouble(3);
```

Or:

```java
rs.getInt("id");
rs.getString("name");
rs.getDouble("marks");
```

For teaching and maintainability, I recommend understanding and commonly using column labels:

```java
rs.getInt("id");
```

because the code tells you what you're retrieving.

---

# 14. A Small Example to Test Your Understanding

Suppose:

```text
id    name
101   Ravi
102   Kumar
```

And:

```java
while (rs.next()) {

    int id =
        rs.getInt(1);

    String name =
        rs.getString(2);

    System.out.println(
        id + " " + name
    );
}
```

What happens?

### First `next()`

Cursor → Ravi

```text
101 Ravi
```

Then:

```java
getInt(1)
```

gives:

```text
101
```

and:

```java
getString(2)
```

gives:

```text
Ravi
```

### Second `next()`

Cursor → Kumar

Then:

```text
102 Kumar
```

### Third `next()`

Returns:

```text
false
```

Loop ends.

Output:

```text
101 Ravi
102 Kumar
```

---

# 15. ResultSet and SQL NULL

This is worth remembering separately.

## Primitive getter

```java
int id = rs.getInt("id");
```

If SQL value is `NULL`:

```text
int → 0
```

Check:

```java
rs.wasNull();
```

---

## Object getter

```java
String name =
    rs.getString("name");
```

If SQL value is `NULL`:

```text
String → null
```

---

## getObject()

```java
Object value =
    rs.getObject("name");
```

SQL `NULL` can become:

```text
Object → null
```

---

# 16. ResultSet Metadata

One more useful concept connected to ResultSet is metadata.

You can ask:

> "What columns does this ResultSet contain?"

Use:

```java
ResultSetMetaData meta =
    rs.getMetaData();
```

Then:

```java
int count =
    meta.getColumnCount();
```

You can inspect:

```java
meta.getColumnName(1);
meta.getColumnLabel(1);
meta.getColumnType(1);
meta.getColumnTypeName(1);
```

This is useful when writing generic database applications where you don't know the columns in advance.

---

# 17. Closing ResultSet

When you're finished:

```java
rs.close();
```

Because ResultSet is a JDBC resource.

Better:

```java
try (
    PreparedStatement ps =
        con.prepareStatement(sql);

    ResultSet rs =
        ps.executeQuery()
) {

    while (rs.next()) {
        System.out.println(
            rs.getString("name")
        );
    }
}
```

Try-with-resources automatically closes these resources.

---

# 18. Final Visual Map

```text
                    ResultSet
                        │
                        │
             result of executeQuery()
                        │
                        ↓
                ┌─────────────┐
                │   Cursor    │
                └─────────────┘
                        │
                ┌───────┴────────┐
                ↓                ↓
              next()          navigation
                │             (scrollable)
                ↓
           Current Row
                │
       ┌────────┼─────────┐
       ↓        ↓         ↓
    getInt() getString() getDouble()
       │        │         │
       └────────┼─────────┘
                ↓
           Java values

                +
                
          getObject()
                ↓
        Java Object value
```

And separately:

```text
ResultSet
│
├── TYPE
│   ├── TYPE_FORWARD_ONLY
│   ├── TYPE_SCROLL_INSENSITIVE
│   └── TYPE_SCROLL_SENSITIVE
│
└── CONCURRENCY
    ├── CONCUR_READ_ONLY
    └── CONCUR_UPDATABLE
```

---

# 🧠 TEACHME Memory Trick

Remember this sentence:

> **"ResultSet gives me the result, next() takes me to a row, getXXX() gives me the column value, TYPE controls how I move, and CONCUR controls whether I can update."**

That's the entire concept in one sentence.

```text
ResultSet
   ↓
"Give me query result"

next()
   ↓
"Take me to the next row"

getInt()
   ↓
"Give me integer"

getString()
   ↓
"Give me String"

getDouble()
   ↓
"Give me double"

getObject()
   ↓
"Give me Object"

TYPE
   ↓
"How can I move?"

CONCURRENCY
   ↓
"Can I update?"
```

### ⭐ Most important interview distinction

```text
TYPE
→ cursor movement

CONCURRENCY
→ update capability
```

If you understand that distinction, the entire `ResultSet` topic becomes much easier.
