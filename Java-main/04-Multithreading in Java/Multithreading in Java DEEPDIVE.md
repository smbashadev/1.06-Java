# Multithreading in Java — DEEPDIVE

This is the **complete DEEPDIVE version**, following the same approach as Exception Handling: definitions, concepts, terminology, diagrams, programs, outputs, confusing points, synchronization, inter-thread communication, problems, and modern Java concurrency.

---

# 1. What Is Multithreading?

**Multithreading** is the process of executing multiple threads concurrently within a single Java process.

A **thread** is the smallest unit of execution that can be scheduled independently.

For example, a browser-like application may need to:

```text
Download data
     +
Process data
     +
Respond to user actions
     +
Perform background work
```

Instead of making one activity wait for another unnecessarily, different tasks can be performed by different threads.

```text
                    Java Process
                         |
          ┌──────────────┼──────────────┐
          ↓              ↓              ↓
       Thread 1       Thread 2       Thread 3
       Download       Calculation    User Input
```

---

# 2. What Is a Process?

A **process** is a program in execution.

For example:

```text
Java Application
       ↓
     Process
       |
 ┌─────┼─────┐
 ↓     ↓     ↓
 T1    T2    T3
```

A single process can contain multiple threads.

---

# 3. Process vs Thread

| Process                                               | Thread                                        |
| ----------------------------------------------------- | --------------------------------------------- |
| Program in execution                                  | Unit of execution inside a process            |
| Has its own address space                             | Shares process resources                      |
| Relatively heavyweight                                | Relatively lightweight                        |
| Process creation is more expensive                    | Thread creation is generally cheaper          |
| Processes normally communicate through IPC mechanisms | Threads can communicate through shared memory |
| One process can contain multiple threads              | A thread belongs to a process                 |

---

# 4. Why Do We Need Multithreading?

Suppose we have:

```java
task1();
task2();
task3();
```

With a single thread:

```text
task1
  ↓
task2
  ↓
task3
```

If `task1()` takes a long time, `task2()` must wait.

With multiple threads:

```text
Thread 1 → task1

Thread 2 → task2

Thread 3 → task3
```

This can improve:

* Responsiveness
* Throughput
* Resource utilization
* Background processing
* Concurrent task execution

### Important

Multithreading does **not automatically mean faster execution**.

Creating and coordinating threads also has overhead.

---

# 5. Concurrency vs Parallelism

These terms are often confused.

## Concurrency

Multiple tasks make progress during overlapping periods.

```text
Time →
T1: ███     ███
T2:    ███     ███
```

## Parallelism

Multiple tasks literally execute simultaneously on different CPU cores.

```text
Core 1: █████████
Core 2: █████████
```

Therefore:

```text
Concurrency ≠ necessarily Parallelism
```

Java supports both depending on the environment and execution model.

---

# 6. What Is a Thread?

A thread is an independent path of execution within a process.

Every Java application starts with at least one thread: the **main thread**.

Example:

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

Typical output:

```text
main
```

---

# 7. Main Thread

When the JVM starts the application's `main()` method, execution occurs on the main thread.

```text
JVM
 |
 ↓
main thread
 |
 ↓
main()
```

From the main thread, we can create additional threads.

---

# 8. Creating a Thread — Two Classic Approaches

There are two traditional approaches commonly taught first:

1. Extend `Thread`
2. Implement `Runnable`

---

# 9. Creating Thread by Extending `Thread`

```java
class MyThread extends Thread
{
    public void run()
    {
        System.out.println("Child thread running");
    }
}

class Demo
{
    public static void main(String[] args)
    {
        MyThread t = new MyThread();

        t.start();

        System.out.println("Main thread running");
    }
}
```

Possible output:

```text
Child thread running
Main thread running
```

or:

```text
Main thread running
Child thread running
```

### Why can the order change?

Because thread scheduling is not guaranteed to follow source-code order after `start()`.

---

# 10. What Does `start()` Actually Do?

This is one of the **most important multithreading concepts**.

```java
t.start();
```

does not simply execute `run()` like an ordinary method call.

It starts the thread's execution and the JVM schedules that thread to execute its `run()` method.

Conceptually:

```text
t.start()
    ↓
Thread becomes eligible to run
    ↓
Scheduler/JVM
    ↓
run()
```

---

# 11. `run()` vs `start()`

Consider:

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

        t.run();
    }
}
```

Output:

```text
main
```

Why?

Because:

```java
t.run();
```

is simply a normal method invocation.

It does **not** create a new thread.

Now:

```java
t.start();
```

causes the thread to be started.

---

# 12. Golden Rule

```text
start()
   ↓
new thread execution

run()
   ↓
normal method execution when called directly
```

Never confuse these two.

---

# 13. Creating Thread Using `Runnable`

`Runnable` represents a task whose `run()` method can be executed by a thread.

```java
class MyTask implements Runnable
{
    public void run()
    {
        System.out.println("Task running");
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
Task running
```

---

# 14. Why Is `Runnable` Often Preferred?

Java allows a class to extend only one class.

If we write:

```java
class MyTask extends Thread
{
}
```

our class is already using its class inheritance relationship with `Thread`.

With:

```java
class MyTask implements Runnable
{
}
```

the task is separated from the thread object.

Conceptually:

```text
Task
 ↓
Runnable

Execution mechanism
 ↓
Thread
```

This separation is often cleaner.

---

# 15. Thread Life Cycle

Java represents thread states using `Thread.State`.

The states are:

```text
NEW
RUNNABLE
BLOCKED
WAITING
TIMED_WAITING
TERMINATED
```

---

# 16. NEW State

When a thread object is created:

```java
Thread t = new Thread(task);
```

it is in the:

```text
NEW
```

state.

```text
Thread object created
        ↓
       NEW
```

The thread has not yet been started.

---

# 17. RUNNABLE State

After:

```java
t.start();
```

the thread becomes eligible for execution.

```text
NEW
 ↓
start()
 ↓
RUNNABLE
```

`RUNNABLE` covers a thread that is ready to run as well as one actually running according to the JVM's state model.

---

# 18. BLOCKED State

A thread enters `BLOCKED` when it is waiting to acquire an intrinsic monitor lock.

Example:

```text
Thread 1
   |
 owns lock
   |
Thread 2
   |
 waiting for same lock
   ↓
BLOCKED
```

---

# 19. WAITING State

A thread enters `WAITING` when it waits indefinitely for another thread/action.

Examples include:

```java
wait();
```

or certain forms of:

```java
join();
```

---

# 20. TIMED_WAITING State

A thread enters `TIMED_WAITING` when it waits for a specified period.

Examples:

```java
Thread.sleep(1000);
```

and timed forms of:

```java
wait(1000);
join(1000);
```

---

# 21. TERMINATED State

After the thread's `run()` method finishes:

```text
Thread execution completed
          ↓
      TERMINATED
```

A terminated thread cannot be restarted.

---

# 22. Thread Life Cycle Diagram

```text
                         NEW
                          |
                        start()
                          ↓
                      RUNNABLE
                     /    |    \
                    ↓     ↓     ↓
                BLOCKED WAITING TIMED_WAITING
                    \     |     /
                     \    |    /
                      RUNNABLE
                          |
                    run() completes
                          ↓
                     TERMINATED
```

---

# 23. Can a Thread Be Started Twice?

**No.**

This is invalid:

```java
Thread t = new Thread(task);

t.start();
t.start();
```

A thread instance can be started only once.

The second attempt results in:

```text
IllegalThreadStateException
```

---

# 24. Important Thread Methods

Some important methods include:

```text
start()
run()
sleep()
join()
interrupt()
isAlive()
getName()
setName()
getPriority()
setPriority()
currentThread()
isInterrupted()
```

---

# 25. `currentThread()`

Returns a reference to the currently executing thread.

```java
System.out.println(
    Thread.currentThread().getName()
);
```

---

# 26. `getName()` and `setName()`

```java
class Demo extends Thread
{
    public void run()
    {
        System.out.println(getName());
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

---

# 27. `sleep()`

`sleep()` pauses the currently executing thread for approximately the specified duration.

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
                return;
            }
        }
    }

    public static void main(String[] args)
    {
        new Demo().start();
    }
}
```

Output:

```text
1
2
3
```

The values appear approximately one second apart.

---

# 28. Does `sleep()` Release the Lock?

**No.**

Suppose a thread is executing inside a synchronized block and calls:

```java
Thread.sleep(5000);
```

it continues to hold the intrinsic monitor during the sleep.

This is an extremely important distinction:

```text
sleep()
 ↓
pauses thread
 ↓
does NOT release monitor
```

---

# 29. `join()`

`join()` makes the calling thread wait for another thread to terminate.

```java
class Demo extends Thread
{
    public void run()
    {
        System.out.println("Child started");

        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }

        System.out.println("Child completed");
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

Output:

```text
Child started
Child completed
Main completed
```

---

# 30. What Does `join()` Actually Mean?

If:

```text
Main → t.join()
```

then:

```text
Main
 ↓
waits for t
 ↓
t completes
 ↓
Main continues
```

`join()` does not mean "join two threads into one thread."

---

# 31. `isAlive()`

```java
System.out.println(t.isAlive());
```

Returns whether the thread is alive according to the `Thread` API.

---

# 32. Thread Priority

Java defines:

```java
Thread.MIN_PRIORITY
Thread.NORM_PRIORITY
Thread.MAX_PRIORITY
```

with values:

```text
1
5
10
```

Example:

```java
t.setPriority(Thread.MAX_PRIORITY);
```

But:

> Priority does not guarantee execution order.

---

# 33. Daemon Thread

A daemon thread is intended for background work.

```java
Thread t = new Thread(task);

t.setDaemon(true);

t.start();
```

Important:

```text
setDaemon(true)
       ↓
must happen before start()
```

The JVM does not remain alive solely because daemon threads remain after all non-daemon threads have terminated.

---

# 34. Thread Interruption

A thread can request another thread to interrupt its current activity:

```java
t.interrupt();
```

It is important to understand:

> `interrupt()` does not forcibly kill the thread.

It sets the interruption status and may cause interruptible blocking operations such as `sleep()`, `wait()`, or `join()` to throw `InterruptedException`.

---

# 35. What Is a Race Condition?

Suppose:

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

Now two threads execute:

```java
counter.increment();
```

simultaneously.

Many beginners think:

```text
count++
```

is one indivisible operation.

Conceptually, it involves:

```text
READ count
    ↓
ADD 1
    ↓
WRITE count
```

Suppose:

```text
Initial count = 0

Thread 1 reads 0
Thread 2 reads 0

Thread 1 writes 1
Thread 2 writes 1
```

Expected:

```text
2
```

Actual:

```text
1
```

This is a **race condition**.

---

# 36. What Is a Critical Section?

A **critical section** is a section of code that accesses shared mutable state and must be protected against unsafe concurrent execution.

Example:

```java
count++;
```

can be part of a critical section.

```text
Thread 1 ───┐
            ↓
      Critical Section
            ↑
Thread 2 ───┘
```

---

# 37. What Is Synchronization?

**Synchronization** is a mechanism for controlling concurrent access to shared resources so that operations requiring mutual exclusion are not performed by multiple threads simultaneously.

Main goals include:

* Mutual exclusion
* Visibility/ordering guarantees
* Maintaining data consistency

---

# 38. What Is a Monitor/Intrinsic Lock?

Every Java object can be associated with an intrinsic monitor.

When a thread enters:

```java
synchronized(obj)
{
    // protected code
}
```

it must acquire `obj`'s monitor.

```text
             Object
                |
          Intrinsic Monitor
                |
        ┌───────┴───────┐
        ↓               ↓
    Thread 1         Thread 2
      owns              waits
       lock
```

Only one thread at a time can own that particular monitor.

---

# 39. Synchronized Method

Example:

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

If two threads invoke `increment()` on the **same `Counter` object**, only one can execute that synchronized method at a time.

---

# 40. Why Does `synchronized` Work?

For an instance synchronized method:

```java
synchronized void increment()
{
}
```

the lock is associated with:

```text
this object
```

Conceptually:

```text
counter
  |
  └── monitor
       |
       ├── Thread 1 → owns lock
       |
       └── Thread 2 → waits
```

---

# 41. Important: Same Object vs Different Objects

Consider:

```java
Counter c1 = new Counter();
Counter c2 = new Counter();
```

If:

```text
Thread 1 → c1.increment()
Thread 2 → c2.increment()
```

they are using different object monitors.

Therefore, synchronization on `c1` does not automatically block synchronization on `c2`.

This is a major source of confusion.

---

# 42. Synchronized Block

Instead of synchronizing the complete method:

```java
synchronized void test()
{
}
```

we can protect only a critical section:

```java
void test()
{
    // non-critical code

    synchronized(this)
    {
        // critical section
    }

    // other code
}
```

This can reduce unnecessary locking.

---

# 43. Synchronizing on a Dedicated Lock Object

Instead of:

```java
synchronized(this)
```

we can use:

```java
private final Object lock = new Object();

void increment()
{
    synchronized(lock)
    {
        count++;
    }
}
```

This can provide better encapsulation of the lock.

---

# 44. Static Synchronized Method

Consider:

```java
static synchronized void test()
{
}
```

This synchronizes using the monitor associated with the class object.

Conceptually:

```text
Instance synchronized method
        ↓
instance monitor

Static synchronized method
        ↓
Class object's monitor
```

---

# 45. Static Synchronization Example

```java
class Demo
{
    static synchronized void test()
    {
        System.out.println(
            Thread.currentThread().getName()
            + " executing"
        );
    }
}
```

The lock is associated with the class object for `Demo`, not with individual instances.

---

# 46. Synchronized Method vs Synchronized Block

| Synchronized Method               | Synchronized Block             |
| --------------------------------- | ------------------------------ |
| Locks method's associated monitor | Locks specified monitor        |
| Protects the entire method        | Can protect only selected code |
| Simpler                           | More flexible                  |
| May lock more code than necessary | Can minimize lock scope        |

---

# 47. Does Synchronization Mean Only One Thread Exists?

**No.**

Multiple threads still exist.

Synchronization means that particular protected region cannot be entered concurrently by multiple threads holding the same monitor.

```text
Thread 1 ──→ synchronized region
                  ↑
                  │
             one at a time
                  │
Thread 2 ──→ waits
```

After Thread 1 releases the monitor, another eligible thread can acquire it.

---

# 48. What Happens When a Thread Cannot Acquire a Monitor?

If a thread attempts to enter a synchronized region whose monitor is already owned by another thread, it waits to acquire that monitor and is represented by the `BLOCKED` state.

```text
Thread 1
   ↓
owns lock

Thread 2
   ↓
tries same lock
   ↓
BLOCKED
```

---

# 49. `wait()` — Inter-Thread Communication

`wait()` is different from `sleep()`.

When a thread calls:

```java
obj.wait();
```

while owning `obj`'s monitor:

1. It releases that monitor.
2. It enters a waiting state.
3. It waits for notification, interruption, or a timeout if a timed version was used.

---

# 50. `notify()`

```java
obj.notify();
```

wakes one thread waiting on that object's monitor.

But notification does not mean the waiting thread immediately executes.

The awakened thread must first successfully reacquire the monitor.

---

# 51. `notifyAll()`

```java
obj.notifyAll();
```

makes all threads waiting on that object's monitor eligible to compete for the monitor.

Again, they do not all execute simultaneously inside the synchronized region.

Only one can own the monitor at a time.

---

# 52. Complete `wait()` / `notify()` Example

```java
class Shared
{
    synchronized void waiting()
        throws InterruptedException
    {
        System.out.println("Thread waiting");

        wait();

        System.out.println("Thread resumed");
    }

    synchronized void notifying()
    {
        System.out.println("Sending notification");

        notify();
    }
}

class Demo
{
    public static void main(String[] args)
        throws InterruptedException
    {
        Shared s = new Shared();

        Thread t1 = new Thread(() ->
        {
            try
            {
                s.waiting();
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        });

        Thread t2 = new Thread(() ->
        {
            s.notifying();
        });

        t1.start();

        Thread.sleep(500);

        t2.start();

        t1.join();
        t2.join();
    }
}
```

Possible output:

```text
Thread waiting
Sending notification
Thread resumed
```

---

# 53. Why Must `wait()` Be Called Inside Synchronization?

This is important.

This is incorrect:

```java
obj.wait();
```

if the current thread does not own `obj`'s monitor.

It results in:

```text
IllegalMonitorStateException
```

Correct:

```java
synchronized(obj)
{
    obj.wait();
}
```

---

# 54. Why Must `notify()` Be Called Inside Synchronization?

For the same reason.

Correct:

```java
synchronized(obj)
{
    obj.notify();
}
```

The calling thread must own that object's monitor.

---

# 55. `sleep()` vs `wait()` — Ultimate Difference

| `sleep()`                                      | `wait()`                                    |
| ---------------------------------------------- | ------------------------------------------- |
| `Thread` method                                | `Object` method                             |
| Used for timed suspension                      | Used for coordination                       |
| Does not release monitor                       | Releases corresponding monitor              |
| Does not require owning a monitor              | Must own corresponding monitor              |
| Ends after time or interruption                | Waits for notification/interruption/timeout |
| Does not transfer control through notification | Designed for monitor-based communication    |

---

# 56. Producer-Consumer Concept

A classic synchronization problem is **Producer-Consumer**.

```text
Producer
   |
   ↓
 Shared Buffer
   |
   ↓
Consumer
```

Producer:

```text
if buffer is full
      ↓
    wait()
```

Consumer:

```text
if buffer is empty
      ↓
    wait()
```

When the producer adds data:

```text
notify/notifyAll
```

When the consumer removes data:

```text
notify/notifyAll
```

In modern Java, a `BlockingQueue` is often preferable to manually implementing this coordination.

---

# 57. Why Use `while` With `wait()` Instead of `if`?

Correct pattern:

```java
synchronized(lock)
{
    while(!condition)
    {
        lock.wait();
    }

    // use resource
}
```

Not:

```java
if(!condition)
{
    lock.wait();
}
```

The condition should be checked again after waking because the desired condition may no longer hold.

This is a very important concurrency rule.

---

# 58. What Is Deadlock?

Deadlock occurs when threads are permanently waiting for resources held by one another.

Example:

```text
Thread 1 owns Lock A
        ↓
waits for Lock B

Thread 2 owns Lock B
        ↓
waits for Lock A
```

Diagram:

```text
       ┌───────────────┐
       ↓               |
 Thread 1           Lock A
       |               ↑
       ↓               |
    Lock B ←────── Thread 2
```

Neither can proceed.

---

# 59. Deadlock Program

```java
class Demo
{
    static final Object lock1 = new Object();
    static final Object lock2 = new Object();

    public static void main(String[] args)
    {
        Thread t1 = new Thread(() ->
        {
            synchronized(lock1)
            {
                System.out.println("T1 got lock1");

                synchronized(lock2)
                {
                    System.out.println("T1 got lock2");
                }
            }
        });

        Thread t2 = new Thread(() ->
        {
            synchronized(lock2)
            {
                System.out.println("T2 got lock2");

                synchronized(lock1)
                {
                    System.out.println("T2 got lock1");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
```

Possible situation:

```text
T1 → owns lock1 → waits for lock2
T2 → owns lock2 → waits for lock1
```

Program can remain stuck.

---

# 60. How Can Deadlock Be Prevented?

One common technique is to maintain a consistent lock ordering.

For example:

```text
Always acquire:
Lock A
   ↓
Lock B
```

and never:

```text
Thread 1: A → B
Thread 2: B → A
```

Other approaches include:

* Reducing lock scope
* Avoiding unnecessary nested locks
* Using higher-level concurrency utilities
* Using `tryLock()` with timeouts where appropriate

---

# 61. What Is Starvation?

**Starvation** occurs when a thread is repeatedly denied the resources or execution opportunities it needs to make progress.

```text
Thread A → keeps getting access

Thread B → repeatedly waits
```

The program may still be running, but Thread B makes insufficient progress.

---

# 62. What Is Livelock?

In livelock, threads are not blocked; they remain active but repeatedly respond to each other without making useful progress.

```text
Thread 1 → changes action
Thread 2 → reacts
Thread 1 → changes again
Thread 2 → reacts again
```

They are active but accomplish nothing useful.

---

# 63. Deadlock vs Starvation vs Livelock

| Problem    | Main idea                                            |
| ---------- | ---------------------------------------------------- |
| Deadlock   | Threads wait for each other indefinitely             |
| Starvation | A thread repeatedly fails to obtain needed resources |
| Livelock   | Threads keep acting but make no useful progress      |

---

# 64. What Is `volatile`?

`volatile` is primarily about **visibility** of updates between threads.

Example:

```java
class Demo
{
    volatile boolean running = true;
}
```

If one thread changes:

```java
running = false;
```

another thread reading the volatile variable is guaranteed appropriate visibility according to the Java Memory Model.

---

# 65. Does `volatile` Make `count++` Thread-Safe?

**No.**

```java
volatile int count;

count++;
```

is still conceptually:

```text
read
 ↓
add
 ↓
write
```

Two threads can interfere.

For compound atomic operations, consider synchronization or atomic classes.

---

# 66. Atomic Classes

Java provides:

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

Here:

```java
incrementAndGet()
```

provides an atomic increment operation.

---

# 67. Thread Safety

A class is thread-safe when its behavior remains correct under concurrent access according to its documented contract.

Possible techniques include:

```text
Synchronization
Immutable state
Atomic variables
Concurrent collections
Thread confinement
Locks
Message passing
```

---

# 68. What Is Thread Confinement?

Thread confinement means ensuring that mutable data is accessed by only one thread.

If no other thread can access that mutable state concurrently, many synchronization problems disappear.

```text
Thread 1
   |
private mutable data
   |
No other thread accesses it
```

---

# 69. Immutable Objects and Multithreading

Immutable objects are naturally easier to share between threads because their state cannot be changed after construction.

For example:

```text
String
```

is immutable.

If an object cannot change, threads cannot create a race condition by modifying that object's state.

---

# 70. Executor Framework

Instead of manually creating many threads:

```java
new Thread(task1).start();
new Thread(task2).start();
new Thread(task3).start();
```

Java provides executors.

Example:

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

The executor manages a pool of worker threads and schedules submitted tasks.

---

# 71. What Is a Thread Pool?

A thread pool maintains a set of reusable worker threads.

```text
             Executor
                 |
        ┌────────┼────────┐
        ↓        ↓        ↓
      Worker   Worker   Worker
        |
    receives tasks
```

Benefits:

* Avoids repeatedly creating threads
* Controls concurrency
* Reuses worker threads
* Simplifies task management

---

# 72. `Runnable` vs `Callable`

### Runnable

```java
Runnable task = () ->
{
    System.out.println("Task");
};
```

Does not return a result.

### Callable

```java
Callable<Integer> task = () ->
{
    return 100;
};
```

Can return a result and throw checked exceptions.

---

# 73. `Future`

A `Future` represents the result of an asynchronous computation.

```java
ExecutorService service =
    Executors.newSingleThreadExecutor();

Future<Integer> future =
    service.submit(() -> 100);

Integer result = future.get();

service.shutdown();
```

`get()` waits if necessary for the computation to complete.

---

# 74. `CompletableFuture`

For more advanced asynchronous workflows:

```java
CompletableFuture
```

can be used.

Example:

```java
CompletableFuture
    .supplyAsync(() -> 100)
    .thenApply(x -> x * 2)
    .thenAccept(System.out::println);
```

Possible output:

```text
200
```

It supports composition of asynchronous stages.

---

# 75. Concurrent Collections

Ordinary collections are not automatically safe for arbitrary concurrent modification.

Java provides concurrent collections such as:

```text
ConcurrentHashMap
CopyOnWriteArrayList
BlockingQueue
ConcurrentLinkedQueue
```

Example:

```java
ConcurrentHashMap<Integer, String> map =
    new ConcurrentHashMap<>();
```

These are designed for specific concurrent-access patterns.

---

# 76. `synchronized` vs `Lock`

Java provides explicit lock classes such as:

```text
ReentrantLock
```

Example:

```java
import java.util.concurrent.locks.ReentrantLock;

class Counter
{
    private int count = 0;

    private final ReentrantLock lock =
        new ReentrantLock();

    void increment()
    {
        lock.lock();

        try
        {
            count++;
        }
        finally
        {
            lock.unlock();
        }
    }
}
```

### Why `finally`?

To ensure the lock is released even if an exception occurs.

---

# 77. `synchronized` vs `ReentrantLock`

| `synchronized`                                    | `ReentrantLock`                          |
| ------------------------------------------------- | ---------------------------------------- |
| Built into Java language                          | Explicit lock API                        |
| Automatically releases monitor when leaving block | Must explicitly unlock                   |
| Simple                                            | More flexible                            |
| No direct timed lock acquisition                  | Supports `tryLock()`                     |
| No explicit fairness configuration                | Supports optional fairness               |
| Good for many ordinary cases                      | Useful for advanced locking requirements |

---

# 78. Semaphore

A `Semaphore` controls access using a number of permits.

For example:

```text
3 permits
 ↓
At most 3 threads
can access resource
simultaneously
```

This differs from `synchronized`, which provides one-at-a-time ownership of a monitor.

---

# 79. CountDownLatch

A `CountDownLatch` allows one or more threads to wait until a count reaches zero.

Conceptually:

```text
count = 3

Task 1 → countDown()
Task 2 → countDown()
Task 3 → countDown()

count = 0
   ↓
waiting thread proceeds
```

---

# 80. CyclicBarrier

A `CyclicBarrier` allows a group of threads to wait for one another at a common barrier point.

```text
Thread 1 ──┐
Thread 2 ──┼── Barrier
Thread 3 ──┘
             ↓
        all arrive
             ↓
         continue
```

Unlike a `CountDownLatch`, a barrier can be reused.

---

# 81. ThreadLocal

`ThreadLocal` provides each thread with its own independent value.

```text
Thread 1 → value A
Thread 2 → value B
Thread 3 → value C
```

Example:

```java
ThreadLocal<Integer> local =
    ThreadLocal.withInitial(() -> 0);

local.set(100);
```

Each thread interacting with `local` gets its own associated value.

---

# 82. Thread Synchronization Tree

```text
                    SYNCHRONIZATION
                          |
          ┌───────────────┼────────────────┐
          ↓               ↓                ↓
     synchronized       Locks           Atomic
          |               |                |
    ┌─────┴─────┐     ReentrantLock   AtomicInteger
    ↓           ↓
  Method       Block
    |
    ↓
 Monitor
    |
    ↓
 Mutual Exclusion
    |
    ↓
 Shared Resource
    |
    ↓
 Thread Safety
```

---

# 83. Inter-Thread Communication Tree

```text
             INTER-THREAD COMMUNICATION
                       |
              ┌────────┼────────┐
              ↓        ↓        ↓
            wait()   notify() notifyAll()
              |
              ↓
        Object Monitor
              |
              ↓
       Shared Condition
```

---

# 84. Complete Synchronization Program

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

class Demo
{
    public static void main(String[] args)
        throws InterruptedException
    {
        Counter counter = new Counter();

        Thread t1 = new Thread(() ->
        {
            for(int i = 0; i < 1000; i++)
            {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() ->
        {
            for(int i = 0; i < 1000; i++)
            {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter.getCount());
    }
}
```

Output:

```text
2000
```

### Why?

Both threads share:

```text
same Counter object
        ↓
same count variable
        ↓
synchronized increment()
        ↓
one thread at a time
        ↓
correct result
```

---

# 85. Complete Conceptual Program

```java
class SharedCounter
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

class Worker extends Thread
{
    private final SharedCounter counter;

    Worker(
        SharedCounter counter,
        String name)
    {
        super(name);
        this.counter = counter;
    }

    public void run()
    {
        for(int i = 0; i < 1000; i++)
        {
            counter.increment();
        }

        System.out.println(
            getName() + " completed"
        );
    }
}

class Demo
{
    public static void main(String[] args)
        throws InterruptedException
    {
        SharedCounter counter =
            new SharedCounter();

        Worker t1 =
            new Worker(counter, "Worker-1");

        Worker t2 =
            new Worker(counter, "Worker-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(
            "Final count = " +
            counter.getCount()
        );
    }
}
```

Possible output:

```text
Worker-1 completed
Worker-2 completed
Final count = 2000
```

The order of the first two lines can vary.

---

# 86. Most Important `sleep()` / `join()` / `wait()` Comparison

| Method    | Purpose                           |                           Releases monitor? | Associated with |
| --------- | --------------------------------- | ------------------------------------------: | --------------- |
| `sleep()` | Pause current thread              |                                        ❌ No | `Thread`        |
| `join()`  | Wait for another thread to finish | ❌ Not generally a monitor-release mechanism | `Thread`        |
| `wait()`  | Coordinate using a monitor        |                                       ✅ Yes | `Object`        |

---

# 87. Most Important Synchronization Rules

Memorize these:

### Rule 1

```text
synchronized instance method
        ↓
locks that object
```

### Rule 2

```text
static synchronized method
        ↓
locks the Class object's monitor
```

### Rule 3

```text
sleep()
        ↓
does NOT release monitor
```

### Rule 4

```text
wait()
        ↓
releases corresponding monitor
```

### Rule 5

```text
wait()/notify()/notifyAll()
        ↓
must be called while owning corresponding monitor
```

### Rule 6

```text
volatile
        ↓
visibility
        ↓
NOT general atomicity
```

### Rule 7

```text
start()
        ↓
starts thread execution

run()
        ↓
normal method call if invoked directly
```

### Rule 8

```text
Thread object
        ↓
can be started only once
```

---

# 88. The Ultimate Multithreading Flow

```text
                         MULTITHREADING
                               |
                    Multiple execution paths
                               |
                ┌──────────────┼──────────────┐
                ↓              ↓              ↓
             Thread         Runnable        Callable
                |              |              |
                └──────────────┼──────────────┘
                               ↓
                            start()
                               ↓
                           RUNNABLE
                               |
          ┌────────────────────┼─────────────────────┐
          ↓                    ↓                     ↓
       BLOCKED              WAITING            TIMED_WAITING
          |                    |                     |
          └────────────────────┼─────────────────────┘
                               ↓
                           RUNNABLE
                               ↓
                          run() ends
                               ↓
                         TERMINATED


                   SHARED MUTABLE DATA
                           |
                           ↓
                    Race Condition
                           |
                           ↓
                     Synchronization
                           |
              ┌────────────┼────────────┐
              ↓            ↓            ↓
         synchronized     Lock        Atomic
              |
        ┌─────┴─────┐
        ↓           ↓
      Method       Block
        |
        ↓
      Monitor
        |
        ↓
   Mutual Exclusion
        |
        ↓
    Thread Safety
        |
        ↓
 wait() / notify() / notifyAll()


              CONCURRENCY PROBLEMS
                       |
          ┌────────────┼────────────┐
          ↓            ↓            ↓
       Deadlock     Starvation    Livelock
```

---

# 89. Final DEEPDIVE Revision Table

| Concept                | Remember                                                      |
| ---------------------- | ------------------------------------------------------------- |
| Process                | Program in execution                                          |
| Thread                 | Unit of execution within a process                            |
| Multithreading         | Multiple threads executing concurrently                       |
| `Thread`               | Class representing a thread                                   |
| `Runnable`             | Represents a task with `run()`                                |
| `Callable`             | Task that can return a result                                 |
| `start()`              | Starts thread execution                                       |
| `run()`                | Thread task method; direct call is ordinary method invocation |
| `sleep()`              | Temporarily pauses current thread                             |
| `join()`               | Waits for another thread to terminate                         |
| `interrupt()`          | Requests interruption                                         |
| `isAlive()`            | Checks whether thread is alive                                |
| Daemon                 | Background thread                                             |
| Race condition         | Result depends on unsafe timing/interleaving                  |
| Critical section       | Code requiring coordinated access                             |
| Synchronization        | Controls concurrent access to shared state                    |
| Monitor                | Intrinsic lock associated with an object                      |
| `synchronized`         | Mutual exclusion + memory synchronization                     |
| `wait()`               | Wait and release corresponding monitor                        |
| `notify()`             | Notify one waiting thread                                     |
| `notifyAll()`          | Notify all waiting threads                                    |
| `volatile`             | Visibility/ordering guarantee, not general atomicity          |
| Atomic class           | Provides atomic operations                                    |
| Deadlock               | Threads wait indefinitely for each other's resources          |
| Starvation             | Thread repeatedly fails to obtain required progress           |
| Livelock               | Threads remain active but make no progress                    |
| ExecutorService        | Manages task execution using executor/thread pools            |
| Future                 | Represents asynchronous computation result                    |
| CompletableFuture      | Composable asynchronous computation                           |
| Concurrent collections | Collections designed for concurrent access                    |
| `ReentrantLock`        | Explicit, flexible locking mechanism                          |
| `Semaphore`            | Controls access using permits                                 |
| `CountDownLatch`       | Waits until count reaches zero                                |
| `CyclicBarrier`        | Threads wait for one another at a barrier                     |
| `ThreadLocal`          | Per-thread independent values                                 |

## ⭐ Ultimate Doubt Killer

```text
start()  → new thread execution
run()    → ordinary method if called directly

sleep()  → pause; does NOT release monitor
wait()   → wait; DOES release corresponding monitor

notify() → one waiting thread becomes eligible
notifyAll() → all waiting threads become eligible

synchronized → protects shared critical sections

volatile → visibility, NOT count++ atomicity

Race condition → unsafe shared access

Deadlock → threads wait for each other

join() → current thread waits for another thread

interrupt() → request interruption, NOT forced termination

Runnable → task
Thread → execution mechanism

ExecutorService → modern task/thread management
```

This is the core structure you should retain before moving to the next multithreading level.
