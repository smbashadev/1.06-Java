# Multithreading in Java — ONEPAGE

## 1. Definition

**Multithreading** is the process of executing **multiple threads concurrently within a single process**.

A **thread** is a lightweight unit of execution.

```text
Java Program / Process
        |
   ┌────┼────┐
   ↓    ↓    ↓
Thread1 Thread2 Thread3
```

Example: A Java application can simultaneously perform background work, handle user interaction, and process data using different threads.

---

# 2. Process vs Thread

| Process                                  | Thread                                        |
| ---------------------------------------- | --------------------------------------------- |
| Independent program in execution         | Unit of execution inside a process            |
| Has its own memory space                 | Threads share process resources               |
| Relatively heavyweight                   | Lightweight                                   |
| Communication is comparatively expensive | Communication is easier through shared memory |
| Can contain multiple threads             | Exists within a process                       |

---

# 3. Why Multithreading?

Multithreading is useful for:

* Better responsiveness
* Concurrent execution of tasks
* Better utilization of CPU resources
* Performing background operations
* Handling multiple independent activities

```text
Single Thread:

Task A → Task B → Task C


Multiple Threads:

Thread 1 → Task A
Thread 2 → Task B
Thread 3 → Task C
```

**Important:** Concurrent execution does not always mean that every thread literally executes at the exact same instant. Actual parallel execution depends on available CPU cores and scheduling.

---

# 4. Thread Life Cycle

A Java thread can move through states represented by `Thread.State`:

```text
NEW
 ↓
RUNNABLE
 ↓
RUNNING / executing
 ↓
TERMINATED
```

A thread can also temporarily enter:

```text
BLOCKED
WAITING
TIMED_WAITING
```

Conceptually:

```text
              NEW
               |
             start()
               ↓
           RUNNABLE
          /    |     \
         ↓     ↓      ↓
    BLOCKED  WAITING  TIMED_WAITING
         \     |      /
          \    |     /
            RUNNABLE
               |
          execution ends
               ↓
          TERMINATED
```

**Important:** `RUNNING` is commonly used when explaining execution, but `Thread.State` itself reports `RUNNABLE` for both ready-to-run and actually running states.

---

# 5. Ways to Create a Thread

The two classic approaches are:

### 1. Extending `Thread`

```java
class MyThread extends Thread
{
    public void run()
    {
        System.out.println("Thread is running");
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

Output:

```text
Thread is running
```

---

### 2. Implementing `Runnable`

```java
class MyTask implements Runnable
{
    public void run()
    {
        System.out.println("Thread is running");
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

Output:

```text
Thread is running
```

### Which is generally preferred?

`Runnable` is often preferred because your class can still inherit from another class.

---

# 6. `start()` vs `run()`

This is one of the most important multithreading doubts.

### `start()`

```java
t.start();
```

Requests that a new thread be started. The JVM then schedules its `run()` method.

### `run()`

```java
t.run();
```

is an ordinary method call when invoked directly; it does **not** by itself create a new thread.

```java
class Demo extends Thread
{
    public void run()
    {
        System.out.println("Running");
    }

    public static void main(String[] args)
    {
        Demo t = new Demo();

        t.run();       // normal method call
        t.start();     // starts a new thread
    }
}
```

---

# 7. Thread Naming

```java
class Demo extends Thread
{
    public void run()
    {
        System.out.println(
            Thread.currentThread().getName()
        );
    }

    public static void main(String[] args)
    {
        Demo t = new Demo();

        t.setName("Worker");

        t.start();
    }
}
```

Output:

```text
Worker
```

Useful methods:

```text
getName()
setName()
currentThread()
```

---

# 8. `sleep()`

`sleep()` pauses the currently executing thread for a specified time.

```java
class Demo extends Thread
{
    public void run()
    {
        for(int i = 1; i <= 3; i++)
        {
            System.out.println(i);

            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                System.out.println("Interrupted");
            }
        }
    }

    public static void main(String[] args)
    {
        new Demo().start();
    }
}
```

Output appears approximately one second apart:

```text
1
2
3
```

### Important

`sleep()` does **not** release an intrinsic monitor lock that the thread already holds.

---

# 9. `join()`

`join()` allows one thread to wait for another thread to terminate.

```java
class Demo extends Thread
{
    public void run()
    {
        for(int i = 1; i <= 3; i++)
        {
            System.out.println(i);
        }
    }

    public static void main(String[] args)
        throws InterruptedException
    {
        Demo t = new Demo();

        t.start();

        t.join();

        System.out.println("Main completed");
    }
}
```

Conceptually:

```text
Main
 |
 | start()
 ↓
Thread
 |
 | completes
 ↓
Main continues
```

---

# 10. `isAlive()`

Checks whether a thread has been started and has not yet terminated.

```java
System.out.println(t.isAlive());
```

---

# 11. Thread Priority

Java provides priorities from:

```text
Thread.MIN_PRIORITY = 1
Thread.NORM_PRIORITY = 5
Thread.MAX_PRIORITY = 10
```

Example:

```java
t.setPriority(Thread.MAX_PRIORITY);
```

**Important:** Priority is only a scheduling hint; it does not guarantee which thread executes first.

---

# 12. Daemon Thread

A daemon thread is a background thread.

```java
Thread t = new Thread(task);

t.setDaemon(true);

t.start();
```

The JVM does not keep running merely because daemon threads remain when all started non-daemon threads have terminated.

`setDaemon(true)` must be called **before** the thread is started.

---

# 13. Thread Interruption

A thread can be requested to stop what it is waiting/sleeping for by interruption.

```java
t.interrupt();
```

The interrupted thread can respond appropriately.

For example, if it is sleeping, `InterruptedException` may be thrown.

**Important:** `interrupt()` is a request/interruption mechanism; it does not forcibly kill the thread.

---

# 14. Multiple Threads Example

```java
class MyThread extends Thread
{
    public void run()
    {
        for(int i = 1; i <= 5; i++)
        {
            System.out.println(
                getName() + " : " + i
            );
        }
    }
}

class Demo
{
    public static void main(String[] args)
    {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();
    }
}
```

The exact output order is **not guaranteed**.

Possible output:

```text
Thread-1 : 1
Thread-2 : 1
Thread-1 : 2
Thread-2 : 2
...
```

Another execution may produce a different order.

---

# 15. What Is Synchronization?

When multiple threads access a **shared mutable resource**, their operations can interfere with each other.

**Synchronization** is a mechanism used to control concurrent access to shared resources and help maintain consistency.

```text
             Shared Resource
             /             \
        Thread 1          Thread 2
             \             /
              \           /
              Synchronization
                    |
              Controlled access
```

---

# 16. Race Condition

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

If multiple threads execute `increment()` concurrently, `count++` is not one indivisible operation.

Conceptually:

```text
Read count
   ↓
Add 1
   ↓
Write count
```

Two threads can interfere with these steps, producing an unexpected result.

This is a **race condition**.

---

# 17. Synchronized Method

```java
class Counter
{
    private int count = 0;

    synchronized void increment()
    {
        count++;
    }

    int getCount()
    {
        return count;
    }
}
```

` synchronized` provides mutual exclusion around the method's execution for the relevant object monitor.

---

# 18. Synchronized Block

Instead of synchronizing the entire method:

```java
synchronized(this)
{
    count++;
}
```

Example:

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

This can reduce the synchronized portion to only the critical section.

---

# 19. Static Synchronization

For a `static synchronized` method:

```java
static synchronized void test()
{
}
```

the lock is associated with the **Class object**, rather than an individual instance.

Conceptually:

```text
synchronized instance method
        ↓
object monitor

static synchronized method
        ↓
Class object's monitor
```

---

# 20. Synchronization and Object Lock

For:

```java
synchronized void test()
{
}
```

the thread acquires the monitor associated with the object on which the method is invoked.

```text
Object
  |
  └── Monitor lock
          |
      Thread enters
          ↓
       executes
          ↓
      releases lock
```

Only one thread at a time can hold that particular monitor.

---

# 21. `wait()`, `notify()`, `notifyAll()`

These methods are associated with an object's monitor.

### `wait()`

A thread waits and releases the monitor.

### `notify()`

Wakes one waiting thread.

### `notifyAll()`

Wakes all threads waiting on that monitor.

Example:

```java
class Demo
{
    synchronized void test()
        throws InterruptedException
    {
        System.out.println("Waiting");

        wait();

        System.out.println("Resumed");
    }

    synchronized void wake()
    {
        notify();
    }
}
```

### Critical point

`wait()`, `notify()`, and `notifyAll()` must be invoked while the current thread owns the corresponding object's monitor, otherwise `IllegalMonitorStateException` occurs.

---

# 22. `sleep()` vs `wait()`

| `sleep()`                                              | `wait()`                                              |
| ------------------------------------------------------ | ----------------------------------------------------- |
| Method of `Thread`                                     | Method of `Object`                                    |
| Used for timed suspension                              | Used for inter-thread coordination                    |
| Does not release an intrinsic monitor lock             | Releases the object's monitor                         |
| Can be called without owning a monitor                 | Must be called while owning the corresponding monitor |
| Usually resumes after time expires, unless interrupted | Waits until notified/interrupted or timeout occurs    |

---

# 23. Deadlock

A **deadlock** occurs when threads become permanently blocked because each is waiting for a resource held by another.

```text
Thread 1
   |
 holds Lock A
   ↓
waiting for Lock B
   ↑
   |
Thread 2
   |
 holds Lock B
   ↓
waiting for Lock A
```

Neither can proceed.

---

# 24. Inter-Thread Communication

Threads can coordinate using:

```text
wait()
notify()
notifyAll()
```

Typical pattern:

```text
Producer
    |
    ↓
shared resource
    ↑
    |
Consumer
```

The producer can notify consumers when data becomes available, while consumers can wait when the resource is unavailable.

---

# 25. Volatile

`volatile` is used when a variable is shared between threads and visibility of updates matters.

```java
class Demo
{
    volatile boolean running = true;
}
```

A write to a volatile variable becomes visible to other threads according to Java's memory model.

### Important

`volatile` **does not make compound operations such as `count++` atomic**.

```java
volatile int count;

count++;    // still not atomic
```

For such operations, synchronization or an appropriate atomic class may be required.

---

# 26. Atomic Classes

Java provides classes such as:

```text
AtomicInteger
AtomicLong
AtomicBoolean
```

Example:

```java
import java.util.concurrent.atomic.AtomicInteger;

class Counter
{
    AtomicInteger count =
        new AtomicInteger(0);

    void increment()
    {
        count.incrementAndGet();
    }
}
```

These provide useful atomic operations without manually synchronizing every operation.

---

# 27. Thread Safety

A class is considered **thread-safe** when its behavior remains correct when accessed concurrently according to its contract.

Ways to achieve thread safety include:

```text
Synchronization
Immutable objects
Atomic classes
Concurrent collections
Proper confinement
```

---

# 28. Modern Java Concurrency

Multithreading is broader than manually creating `Thread` objects.

Java also provides:

```text
ExecutorService
Future
Callable
CompletableFuture
ConcurrentHashMap
BlockingQueue
Atomic classes
Locks
Semaphores
CountDownLatch
CyclicBarrier
```

For larger applications, **executors and concurrency utilities are generally preferred over manually creating many threads**.

---

# 29. Thread vs Runnable vs Callable

| `Thread`                     | `Runnable`                 | `Callable`                                 |
| ---------------------------- | -------------------------- | ------------------------------------------ |
| Represents a thread          | Represents a task          | Represents a task                          |
| `run()`                      | `run()`                    | `call()`                                   |
| No return value from `run()` | No return value            | Can return a value                         |
| `Thread` itself is a class   | Functional interface       | Functional interface                       |
| Direct thread management     | Separates task from thread | Supports result/exception through `Future` |

---

# 30. Complete Concept Tree

```text
                         MULTITHREADING
                              |
        ┌─────────────────────┼─────────────────────┐
        ↓                     ↓                     ↓
      Thread               Creation             Life Cycle
        |                     |                     |
   currentThread()      Thread class          NEW
   getName()             Runnable             RUNNABLE
   setName()             Callable             BLOCKED
   sleep()                                    WAITING
   join()                              TIMED_WAITING
   interrupt()                               TERMINATED
   priority
   daemon
        |
        ↓
   Synchronization
        |
   ┌────┼───────────────┐
   ↓    ↓               ↓
 synchronized        Locks       Atomic
   |                    |          |
method/block         Lock API    AtomicInteger
   |
   ↓
Shared Resource
   |
   ↓
Race Condition
   |
   ↓
Thread Safety
   |
   ├── wait()
   ├── notify()
   └── notifyAll()
```

---

# 31. ONE-PAGE Final Revision

```text
MULTITHREADING
      |
      ├── Thread = lightweight unit of execution
      |
      ├── Creation
      |     ├── Thread
      |     └── Runnable
      |
      ├── Important methods
      |     ├── start()
      |     ├── run()
      |     ├── sleep()
      |     ├── join()
      |     ├── interrupt()
      |     ├── getName()
      |     └── setName()
      |
      ├── States
      |     ├── NEW
      |     ├── RUNNABLE
      |     ├── BLOCKED
      |     ├── WAITING
      |     ├── TIMED_WAITING
      |     └── TERMINATED
      |
      ├── Problems
      |     ├── Race condition
      |     └── Deadlock
      |
      ├── Synchronization
      |     ├── synchronized method
      |     ├── synchronized block
      |     └── static synchronization
      |
      ├── Communication
      |     ├── wait()
      |     ├── notify()
      |     └── notifyAll()
      |
      ├── Visibility
      |     └── volatile
      |
      ├── Atomicity
      |     └── Atomic classes
      |
      └── Modern concurrency
            ├── ExecutorService
            ├── Future
            ├── Callable
            ├── CompletableFuture
            ├── Concurrent collections
            └── Locks
```

### ⭐ Most important doubts to remember

```text
start()  → starts a new thread
run()    → ordinary method call when called directly

sleep()  → pauses current thread; does NOT release monitor
wait()   → waits AND releases corresponding monitor

notify() → wakes one waiting thread
notifyAll() → wakes all waiting threads

synchronized → mutual exclusion around protected code

volatile → visibility, NOT general atomicity

Race condition → unsafe concurrent access
Deadlock → threads permanently wait for each other

Thread → execution mechanism
Runnable/Callable → task abstraction

join() → wait for another thread to terminate
interrupt() → request interruption; does not forcibly kill
```
