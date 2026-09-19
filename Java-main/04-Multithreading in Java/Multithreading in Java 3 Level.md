# Multi Threading in Java — 3LEVEL

The **3LEVEL method** means:

* 🟢 **LEVEL 1 — Beginner:** What is it?
* 🟡 **LEVEL 2 — Intermediate:** How does it work?
* 🔴 **LEVEL 3 — Advanced:** What happens internally and what problems must you handle?

---

# 🟢 LEVEL 1 — BEGINNER

## 1. What Is Multithreading?

> **Multithreading is the process of executing multiple threads concurrently within a single process.**

A **thread** is a lightweight unit of execution.

```text
Java Process
     |
     ├── Thread 1
     ├── Thread 2
     └── Thread 3
```

### Example

A Java application may simultaneously:

```text
Download a file
Play music
Handle user input
```

Instead of making one task wait unnecessarily for another, multiple threads can make progress concurrently.

---

# 2. Main Thread

Every normal Java application begins execution with the **main thread**.

```java
class Demo
{
    public static void main(String[] args)
    {
        System.out.println(
            Thread.currentThread().getName()
        );
    }
}
```

Output:

```text
main
```

So:

```text
JVM
 ↓
main thread
 ↓
main()
```

---

# 3. Creating a Thread

There are two classic ways.

### Way 1 — Extend `Thread`

```java
class MyThread extends Thread
{
    public void run()
    {
        System.out.println("Child Thread");
    }
}

class Demo
{
    public static void main(String[] args)
    {
        MyThread t = new MyThread();

        t.start();
    }
}
```

---

### Way 2 — Implement `Runnable`

```java
class MyTask implements Runnable
{
    public void run()
    {
        System.out.println("Child Thread");
    }
}

class Demo
{
    public static void main(String[] args)
    {
        MyTask task = new MyTask();

        Thread t = new Thread(task);

        t.start();
    }
}
```

---

# 4. `start()` vs `run()`

This is one of the most important points.

### `start()`

```java
t.start();
```

Starts a new thread of execution, which then invokes `run()`.

### `run()`

```java
t.run();
```

is simply a normal method invocation.

It does **not** by itself create a new thread.

```text
start()
  ↓
new thread execution
  ↓
run()
```

Whereas:

```text
run()
  ↓
normal method call
```

---

# 5. Thread Life Cycle

A thread can move through these Java `Thread.State` values:

```text
NEW
 ↓
RUNNABLE
 ↓
BLOCKED / WAITING / TIMED_WAITING
 ↓
RUNNABLE
 ↓
TERMINATED
```

### Main states

| State         | Meaning                                 |
| ------------- | --------------------------------------- |
| NEW           | Thread created but not started          |
| RUNNABLE      | Eligible to run / running               |
| BLOCKED       | Waiting to acquire a monitor lock       |
| WAITING       | Waiting indefinitely for another action |
| TIMED_WAITING | Waiting for a specified time            |
| TERMINATED    | Execution has completed                 |

---

# 6. Important Thread Methods

### `sleep()`

```java
Thread.sleep(1000);
```

Pauses the current thread for approximately one second.

**Important:** `sleep()` does not release an intrinsic monitor.

---

### `join()`

```java
t.join();
```

Makes the calling thread wait until thread `t` terminates.

---

### `getName()`

```java
t.getName();
```

Gets the thread name.

---

### `setName()`

```java
t.setName("Worker");
```

Sets the thread name.

---

### `currentThread()`

```java
Thread.currentThread();
```

Returns the currently executing thread.

---

# 🟡 LEVEL 2 — INTERMEDIATE

# 7. Why Do We Need Synchronization?

The biggest problem begins when multiple threads access **shared mutable data**.

Example:

```java
class Counter
{
    int count = 0;

    void increment()
    {
        count++;
    }
}
```

Suppose two threads execute:

```text
Thread 1 → increment()
Thread 2 → increment()
```

We might expect:

```text
0 → 1 → 2
```

But `count++` conceptually involves:

```text
READ
 ↓
ADD 1
 ↓
WRITE
```

Two threads can interleave those operations.

This can cause a:

# Race Condition

---

# 8. `synchronized`

We can protect the critical section:

```java
class Counter
{
    int count = 0;

    synchronized void increment()
    {
        count++;
    }
}
```

Now the relevant monitor permits only one thread at a time to execute the synchronized method for that object.

```text
Thread 1
   ↓
gets monitor
   ↓
increment()
   ↓
releases monitor

Thread 2
   ↓
gets monitor
   ↓
increment()
```

---

# 9. Synchronized Block

Instead of synchronizing the complete method:

```java
class Counter
{
    int count = 0;

    void increment()
    {
        synchronized(this)
        {
            count++;
        }
    }
}
```

Only the critical section is protected.

You can also synchronize on a dedicated lock:

```java
class Counter
{
    private int count = 0;

    private final Object lock = new Object();

    void increment()
    {
        synchronized(lock)
        {
            count++;
        }
    }
}
```

---

# 10. What Is a Monitor?

Every Java object can be associated with an **intrinsic monitor**.

Think of it as a lock:

```text
Object
  |
  ↓
Monitor
  |
  ↓
One thread owns it at a time
```

For an instance synchronized method:

```java
synchronized void test()
{
}
```

the relevant object's monitor is used.

For:

```java
static synchronized void test()
{
}
```

the synchronization is associated with the `Class` object's monitor.

---

# 11. `sleep()` vs `wait()`

This difference is extremely important.

| `sleep()`                          | `wait()`                                              |
| ---------------------------------- | ----------------------------------------------------- |
| Method of `Thread`                 | Method of `Object`                                    |
| Pauses current thread              | Used for thread coordination                          |
| Does not release intrinsic monitor | Releases the corresponding monitor                    |
| Can be used with a timeout         | Can be indefinite or timed                            |
| Doesn't require owning a monitor   | Must be called while owning the corresponding monitor |

Example:

```java
synchronized(lock)
{
    lock.wait();
}
```

When `wait()` is called:

```text
Thread
  ↓
wait()
  ↓
releases lock
  ↓
WAITING
```

---

# 12. `notify()`

```java
synchronized(lock)
{
    lock.notify();
}
```

Makes one waiting thread eligible to compete to reacquire that monitor.

---

# 13. `notifyAll()`

```java
synchronized(lock)
{
    lock.notifyAll();
}
```

Makes all threads waiting on that monitor eligible to compete for the monitor.

It does **not** mean all those threads execute the synchronized section simultaneously.

---

# 14. Producer–Consumer Concept

A classic use of `wait()` and `notifyAll()` is Producer–Consumer.

```text
Producer
   |
   ↓
  Buffer
   |
   ↓
Consumer
```

If the buffer is full:

```text
Producer → waits
```

If the buffer is empty:

```text
Consumer → waits
```

When the state changes:

```text
notifyAll()
```

can wake waiting threads so they can re-check their conditions.

Correct coordination commonly looks like:

```java
synchronized(lock)
{
    while(!condition)
    {
        lock.wait();
    }

    // perform operation
}
```

Use `while`, not `if`, to re-check the condition after waking.

---

# 15. Thread Priority

Java defines:

```text
MIN_PRIORITY  = 1
NORM_PRIORITY = 5
MAX_PRIORITY  = 10
```

Example:

```java
t.setPriority(Thread.MAX_PRIORITY);
```

But priority does **not guarantee** that a particular thread executes first.

---

# 16. Daemon Thread

A daemon thread is a background thread.

```java
Thread t = new Thread(task);

t.setDaemon(true);

t.start();
```

`setDaemon(true)` must be called **before** starting the thread.

The JVM does not remain alive solely because daemon threads are still running after all non-daemon threads have terminated.

---

# 🔴 LEVEL 3 — ADVANCED

# 17. Synchronization Has Two Major Purposes

Synchronization is not only:

> "Allow one thread at a time."

It also provides important **memory visibility and ordering guarantees**.

So synchronization gives us, among other things:

```text
Mutual Exclusion
       +
Memory Visibility / Ordering
```

---

# 18. Instance vs Static Synchronization

### Instance synchronized method

```java
synchronized void test()
{
}
```

Uses the relevant object's monitor.

```text
Object A → Monitor A
Object B → Monitor B
```

So two different objects have different intrinsic monitors.

---

### Static synchronized method

```java
static synchronized void test()
{
}
```

Uses the monitor associated with the class object.

Conceptually:

```text
Class object
     |
   Monitor
     |
static synchronized method
```

---

# 19. `volatile`

Suppose one thread changes a shared flag:

```java
volatile boolean running = true;
```

`volatile` helps ensure that reads and writes of that variable have the required cross-thread visibility semantics.

But:

```java
volatile int count;

count++;
```

is **not an atomic increment**.

Remember:

```text
volatile
   ↓
visibility

synchronized
   ↓
mutual exclusion
+
visibility/order
```

---

# 20. Atomic Classes

For atomic operations, Java provides classes such as:

```java
AtomicInteger
AtomicLong
AtomicBoolean
```

Example:

```java
import java.util.concurrent.atomic.AtomicInteger;

class Counter
{
    AtomicInteger count = new AtomicInteger();

    void increment()
    {
        count.incrementAndGet();
    }
}
```

Here:

```java
count.incrementAndGet();
```

performs an atomic increment.

---

# 21. Deadlock

Deadlock occurs when threads become permanently stuck waiting for resources held by one another.

Example:

```text
Thread 1
   |
 owns Lock A
   |
 wants Lock B
   ↑
   |
 owns Lock B
   |
Thread 2
   |
 wants Lock A
```

Neither can continue.

### Simple idea

```text
T1 → A → waits for B
T2 → B → waits for A
```

---

# 22. Starvation

A thread suffers starvation when it repeatedly fails to obtain the resources or execution opportunities it needs.

```text
Thread 1 → repeatedly gets resource
Thread 2 → keeps waiting
```

It is different from deadlock because the system may still be making progress elsewhere.

---

# 23. Livelock

In livelock, threads remain active but fail to make useful progress.

```text
Thread 1 → changes action
Thread 2 → reacts
Thread 1 → reacts again
Thread 2 → reacts again
```

They are not blocked, but the actual task does not complete.

---

# 24. ExecutorService

For real-world applications, manually creating large numbers of threads is often inappropriate.

Use an executor:

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Demo
{
    public static void main(String[] args)
    {
        ExecutorService service =
            Executors.newFixedThreadPool(2);

        service.submit(() ->
        {
            System.out.println("Task 1");
        });

        service.submit(() ->
        {
            System.out.println("Task 2");
        });

        service.shutdown();
    }
}
```

Architecture:

```text
Tasks
  ↓
ExecutorService
  ↓
Thread Pool
  ↓
Worker Threads
```

---

# 25. Runnable vs Callable

### Runnable

```java
Runnable r = () ->
{
    System.out.println("Hello");
};
```

Generally represents a task that doesn't return a result.

### Callable

```java
Callable<Integer> c = () ->
{
    return 100;
};
```

Can return a result and throw checked exceptions.

---

# 26. Future

```java
Future<Integer> result =
    service.submit(c);
```

Then:

```java
Integer value = result.get();
```

`get()` waits if necessary until the result is available.

---

# 27. Complete 3LEVEL Tree

```text
                 MULTITHREADING
                       |
       ┌───────────────┼────────────────┐
       ↓               ↓                ↓
   LEVEL 1          LEVEL 2          LEVEL 3
   Beginner       Intermediate       Advanced
       |               |                |
       ↓               ↓                ↓
    Thread          Shared Data       Memory Model
    Process         Race Condition    volatile
    main            Synchronization   Atomic Classes
    start()         synchronized      Deadlock
    run()           Monitor           Starvation
    Runnable        wait()             Livelock
    Lifecycle       notify()           ExecutorService
    sleep()         notifyAll()        Thread Pool
    join()          Producer/Consumer  Callable
    Priority                           Future
    Daemon
```

---

# ⭐ 3LEVEL Quick Revision

## 🟢 LEVEL 1 — "What?"

```text
Thread
 ↓
Lightweight execution unit

Multithreading
 ↓
Multiple threads within a process

Create
 ↓
Thread / Runnable

Start
 ↓
start()

Task
 ↓
run()
```

## 🟡 LEVEL 2 — "How?"

```text
Multiple Threads
       ↓
Shared Data
       ↓
Race Condition
       ↓
Critical Section
       ↓
synchronized
       ↓
Monitor
       ↓
wait()
notify()
notifyAll()
```

## 🔴 LEVEL 3 — "What Can Go Wrong / How Do Professionals Handle It?"

```text
Shared Memory
     |
     ├── Race Condition
     ├── Visibility
     ├── Atomicity
     ├── Deadlock
     ├── Starvation
     └── Livelock
     
Solutions
     |
     ├── synchronized
     ├── volatile
     ├── Atomic classes
     ├── Locks
     ├── Concurrent collections
     └── ExecutorService
```

### 🔥 One-line memory formula

> **Thread → Shared Data → Race Condition → Synchronization → Monitor → wait/notify → Thread Safety → Deadlock/Starvation/Livelock → Modern Concurrency Utilities**.
