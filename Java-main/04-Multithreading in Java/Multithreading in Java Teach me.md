# Multithreading in Java — TEACHME

Let’s learn **Multithreading from zero**, as if you are seeing the concept for the first time. I’ll build it step by step and especially make **synchronization** very clear.

---

# 1. First Understand: What Is a Thread?

Imagine you open a Java application.

That application is a **process**.

Inside that process, Java can perform different activities using different **threads**.

```text
                Java Application
                      |
                    Process
                      |
          ┌───────────┼───────────┐
          ↓           ↓           ↓
       Thread 1    Thread 2    Thread 3
       Task A      Task B      Task C
```

### Simple definition

> **A thread is a lightweight unit of execution inside a process.**

So:

```text
Process
   ↓
contains
   ↓
Threads
```

---

# 2. Why Do We Need Multiple Threads?

Suppose you have three tasks:

```java
task1();
task2();
task3();
```

With one thread:

```text
task1
 ↓
task2
 ↓
task3
```

If `task1()` takes 10 seconds, `task2()` must wait.

With multiple threads:

```text
Thread 1 → task1

Thread 2 → task2

Thread 3 → task3
```

The tasks can make progress concurrently.

### Real-world example

Think about a mobile application:

```text
Download file
     +
Play music
     +
Respond to user
```

You don't want the application to freeze while downloading a file.

Multithreading helps separate such activities.

---

# 3. Process vs Thread

Think of a **process as a house** and **threads as people working inside the house**.

| Process                      | Thread                   |
| ---------------------------- | ------------------------ |
| Program in execution         | Unit of execution        |
| Has its own memory space     | Shares process resources |
| Relatively heavyweight       | Lightweight              |
| Can contain multiple threads | Exists inside a process  |

So:

```text
One Process
     |
     ├── Thread 1
     ├── Thread 2
     └── Thread 3
```

---

# 4. The Main Thread

When Java starts your program:

```java
class Demo
{
    public static void main(String[] args)
    {
        System.out.println("Hello");
    }
}
```

the `main()` method executes on the **main thread**.

We can prove it:

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

# 5. How Do We Create a Thread?

For learning purposes, remember these two classic approaches:

### Method 1

Extend `Thread`.

### Method 2

Implement `Runnable`.

---

# 6. Creating Thread by Extending `Thread`

```java
class MyThread extends Thread
{
    public void run()
    {
        System.out.println("Child thread");
    }
}

class Demo
{
    public static void main(String[] args)
    {
        MyThread t = new MyThread();

        t.start();

        System.out.println("Main thread");
    }
}
```

Possible output:

```text
Child thread
Main thread
```

But this is also possible:

```text
Main thread
Child thread
```

### Why?

Because after `start()`, the exact scheduling order is not guaranteed.

---

# 7. The Most Important Question: What Is `start()`?

Suppose:

```java
t.start();
```

What happens?

Conceptually:

```text
t.start()
    ↓
Thread becomes eligible for execution
    ↓
JVM/thread scheduler
    ↓
run()
```

So `start()` is responsible for starting a separate thread of execution.

---

# 8. Then What Is `run()`?

`run()` contains the task that the thread performs.

```java
public void run()
{
    System.out.println("Task");
}
```

But here is the **big confusion**:

```java
t.run();
```

does **not** create a new thread by itself.

It is simply a normal method call.

---

# 9. `start()` vs `run()` — Remember This Forever

```text
start()
   ↓
Starts thread execution
   ↓
run() executes on that thread
```

But:

```text
run()
   ↓
Direct method call
   ↓
No new thread created
```

Example:

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

Because `run()` was directly called by the main thread.

---

# 10. Now Use `start()`

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

        t.start();
    }
}
```

Output:

```text
Thread-0
```

The exact default thread name can vary, but importantly, it is a separate thread rather than `main`.

---

# 11. Second Way — `Runnable`

Instead of making our class a `Thread`, we can define a task using `Runnable`.

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

Think about it like this:

```text
MyTask
   ↓
Runnable
   ↓
defines task

Thread
   ↓
executes task
```

This separation is often useful because Java classes can extend only one class.

---

# 12. Thread Life Cycle

A thread doesn't stay in one state.

The main states reported by Java are:

```text
NEW
RUNNABLE
BLOCKED
WAITING
TIMED_WAITING
TERMINATED
```

Let's understand them like a story.

---

# 13. NEW

You create the thread:

```java
Thread t = new Thread(task);
```

At this point:

```text
NEW
```

The thread has not started.

---

# 14. RUNNABLE

You call:

```java
t.start();
```

Now the thread becomes eligible to execute:

```text
NEW
 ↓
start()
 ↓
RUNNABLE
```

Java's `RUNNABLE` state covers both being ready to run and actually running.

---

# 15. BLOCKED

Suppose Thread 1 has a synchronized lock.

Thread 2 wants the same lock.

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

Thread 2 waits until it can acquire the monitor.

---

# 16. WAITING

A thread can deliberately wait for another thread/action.

For example:

```java
wait();
```

or an appropriate `join()` call.

The thread enters:

```text
WAITING
```

---

# 17. TIMED_WAITING

Suppose:

```java
Thread.sleep(5000);
```

The thread temporarily waits for a specified period.

It can be in:

```text
TIMED_WAITING
```

---

# 18. TERMINATED

When `run()` finishes:

```text
RUNNABLE
   ↓
run() completes
   ↓
TERMINATED
```

A terminated thread cannot be started again.

---

# 19. Life Cycle in One Diagram

```text
                  NEW
                   |
                 start()
                   ↓
               RUNNABLE
              /    |     \
             ↓     ↓      ↓
         BLOCKED WAITING TIMED_WAITING
             \     |      /
              \    |     /
               RUNNABLE
                   |
              run() ends
                   ↓
              TERMINATED
```

---

# 20. `sleep()` — Take a Break

Suppose a thread is working:

```text
1
2
3
```

and you want it to pause.

```java
Thread.sleep(1000);
```

means approximately:

> "Pause the currently executing thread for 1000 milliseconds."

Example:

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
                Thread.currentThread().interrupt();
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

The output appears approximately one second apart.

---

# 21. `sleep()` Does NOT Release a Lock

This becomes extremely important when we study synchronization.

If a thread has an intrinsic monitor and does:

```java
Thread.sleep(5000);
```

it **continues holding that monitor**.

Remember:

```text
sleep()
   ↓
pause thread
   ↓
does NOT release monitor
```

---

# 22. `join()` — "Wait for That Thread"

Suppose:

```text
Main
 |
 +---- starts Child
 |
 +---- wants to wait for Child
```

Use:

```java
t.join();
```

Example:

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

### Why?

Because:

```text
main
 ↓
join()
 ↓
waits for child
 ↓
child finishes
 ↓
main continues
```

---

# 23. Thread Naming

We can give threads meaningful names.

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

Useful methods:

```text
getName()
setName()
currentThread()
```

---

# 24. Thread Priority

Java defines:

```text
MIN_PRIORITY   = 1
NORM_PRIORITY  = 5
MAX_PRIORITY   = 10
```

Example:

```java
t.setPriority(Thread.MAX_PRIORITY);
```

But don't make this mistake:

> Higher priority does NOT guarantee that a thread executes first.

It is a scheduling hint, not a strict ordering mechanism.

---

# 25. Daemon Thread

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
before start()
```

The JVM does not stay alive solely because daemon threads remain after all non-daemon threads have terminated.

---

# 26. Now the Most Important Part — Shared Data

Suppose two threads share one bank account.

```text
              Bank Account
                   |
             balance = 1000
              /          \
             /            \
       Thread 1         Thread 2
       withdraw         withdraw
```

Both threads are accessing the same data.

This is where problems can happen.

---

# 27. Race Condition

Consider:

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

Two threads execute:

```java
counter.increment();
```

We may expect:

```text
Thread 1 → +1
Thread 2 → +1

Final = 2
```

But `count++` is not one indivisible operation.

Conceptually:

```text
READ count
    ↓
ADD 1
    ↓
WRITE count
```

Imagine:

```text
Initial count = 0

Thread 1 → reads 0
Thread 2 → reads 0

Thread 1 → writes 1
Thread 2 → writes 1
```

Final:

```text
1
```

instead of:

```text
2
```

This is called a **race condition**.

---

# 28. How Do We Solve the Race Condition?

We need to protect the critical section.

This is where:

# `synchronized`

comes in.

---

# 29. What Is Synchronization?

Synchronization controls concurrent access to shared mutable data so that operations requiring mutual exclusion are performed safely.

Think about a **single bathroom**:

```text
Person 1 ──→ Bathroom
Person 2 ──→ waits
Person 3 ──→ waits
```

Only one person can use it at a time.

Similarly:

```text
Thread 1 ──→ synchronized section
Thread 2 ──→ waits
Thread 3 ──→ waits
```

---

# 30. What Is the Lock?

Java objects can have an **intrinsic monitor**.

Think of it as a key.

```text
             Object
               |
            Monitor
               |
              Key
```

Only one thread can own that monitor at a time.

---

# 31. Synchronized Method

Let's fix our counter.

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

Now:

```text
Thread 1
   ↓
gets lock
   ↓
increment()
   ↓
releases lock

Thread 2
   ↓
gets lock
   ↓
increment()
   ↓
releases lock
```

So the critical operation is protected.

---

# 32. Complete Synchronization Program

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
        Counter c = new Counter();

        Thread t1 = new Thread(() ->
        {
            for(int i = 0; i < 1000; i++)
            {
                c.increment();
            }
        });

        Thread t2 = new Thread(() ->
        {
            for(int i = 0; i < 1000; i++)
            {
                c.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(c.getCount());
    }
}
```

Output:

```text
2000
```

### Why?

```text
Thread 1 + Thread 2
       ↓
same Counter object
       ↓
same count
       ↓
synchronized increment()
       ↓
one thread at a time
       ↓
2000
```

---

# 33. Important Question: Does `synchronized` Stop All Threads?

**No.**

This is a common misconception.

Suppose:

```text
Thread 1 → synchronized method
Thread 2 → synchronized method
```

Thread 2 isn't destroyed.

It simply waits for the relevant monitor.

```text
Thread 1 → owns monitor
Thread 2 → waits
```

After Thread 1 leaves:

```text
Thread 1 → releases monitor
Thread 2 → may acquire monitor
```

---

# 34. Synchronized Block

We don't always need to synchronize the whole method.

Instead:

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

Here only the critical section is synchronized.

---

# 35. Why Use a Synchronized Block?

Suppose:

```java
void method()
{
    operation1();

    synchronized(this)
    {
        count++;
    }

    operation2();
}
```

Only the important shared-data operation is protected.

This can reduce the amount of code that needs to execute while holding the lock.

---

# 36. Dedicated Lock Object

You can also use a separate lock:

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

Think:

```text
Counter
   |
   ├── count
   |
   └── lock
```

The `lock` object is used specifically for synchronization.

---

# 37. Instance Synchronized Method

When you write:

```java
synchronized void test()
{
}
```

the relevant lock is the object's monitor.

If:

```java
Counter c = new Counter();
```

then:

```text
c
 |
 └── monitor
```

The synchronized method uses that monitor.

---

# 38. Same Object vs Different Objects

Suppose:

```java
Counter c1 = new Counter();
Counter c2 = new Counter();
```

Now:

```text
Thread 1 → c1.increment()
Thread 2 → c2.increment()
```

These are different objects.

Therefore they have different intrinsic monitors.

```text
c1 → Lock A

c2 → Lock B
```

Synchronization on `c1` does not automatically block synchronization on `c2`.

---

# 39. Static Synchronized Method

Now:

```java
static synchronized void test()
{
}
```

This is different.

The lock is associated with the **Class object**, not an individual instance.

```text
instance synchronized
        ↓
object monitor

static synchronized
        ↓
Class object's monitor
```

---

# 40. Synchronization Has Two Important Ideas

When learning `synchronized`, remember:

### 1. Mutual exclusion

Only one thread at a time can execute the protected region under the same monitor.

### 2. Memory visibility/ordering

Synchronization also establishes important happens-before relationships, so changes made before releasing the monitor can become visible to a thread that subsequently acquires that same monitor.

So synchronization is not merely "one thread at a time."

---

# 41. Now Learn `wait()`

Suppose a thread says:

> "The data I need isn't available. I should wait."

It can use:

```java
wait();
```

But `wait()` is special.

When a thread calls `wait()` while holding an object's monitor:

```text
Thread
  ↓
wait()
  ↓
releases monitor
  ↓
WAITING
```

This is different from `sleep()`.

---

# 42. `sleep()` vs `wait()`

Remember this simple sentence:

> **sleep waits; wait waits and releases the monitor.**

| `sleep()`                              | `wait()`                       |
| -------------------------------------- | ------------------------------ |
| `Thread` method                        | `Object` method                |
| Pauses thread                          | Waits for coordination         |
| Does not release monitor               | Releases corresponding monitor |
| Can be called without owning a monitor | Must own corresponding monitor |
| Can use a timeout                      | Can be indefinite or timed     |

---

# 43. `notify()`

Suppose another thread is waiting.

We can use:

```java
notify();
```

It makes one waiting thread eligible to compete to reacquire the monitor.

Think:

```text
Thread 1
   ↓
WAITING

Thread 2
   ↓
notify()
   ↓
Thread 1 can compete for monitor
```

---

# 44. `notifyAll()`

```java
notifyAll();
```

makes all threads waiting on that monitor eligible to compete for the monitor.

It does **not** mean all of them execute the synchronized block simultaneously.

Only one can own that monitor at a time.

---

# 45. Important `wait()` Rule

You cannot safely write:

```java
obj.wait();
```

without owning `obj`'s monitor.

Correct:

```java
synchronized(obj)
{
    obj.wait();
}
```

Otherwise:

```text
IllegalMonitorStateException
```

---

# 46. Same Rule for `notify()`

Correct:

```java
synchronized(obj)
{
    obj.notify();
}
```

The current thread must own that monitor.

---

# 47. Producer and Consumer

Let's imagine a water tank.

```text
Producer
   ↓
adds water
   ↓
[ Tank ]
   ↓
Consumer
removes water
```

If tank is full:

```text
Producer → wait
```

If tank is empty:

```text
Consumer → wait
```

When the state changes:

```text
notify()
```

or:

```text
notifyAll()
```

can be used.

This is the classic **Producer-Consumer problem**.

---

# 48. Why Use `while` With `wait()`?

Correct pattern:

```java
synchronized(lock)
{
    while(!condition)
    {
        lock.wait();
    }

    // continue
}
```

Why not simply:

```java
if(!condition)
{
    lock.wait();
}
```

Because after waking up, the condition should be checked again.

The thread must not assume that the desired condition is still true.

---

# 49. Deadlock — The Scary Problem

Imagine:

```text
Thread 1 has Key A
Thread 2 has Key B
```

Thread 1 says:

> "I need Key B."

Thread 2 says:

> "I need Key A."

Now:

```text
Thread 1 → holds A → waits for B

Thread 2 → holds B → waits for A
```

Nobody can continue.

This is **deadlock**.

---

# 50. Deadlock Diagram

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

Both remain stuck.

---

# 51. Starvation

Starvation is different.

One thread continuously fails to obtain the resources or scheduling opportunities it needs.

```text
Thread 1 → keeps getting resource
Thread 2 → keeps waiting
```

Thread 2 isn't necessarily deadlocked, but it is not making sufficient progress.

---

# 52. Livelock

In livelock, threads are active but don't accomplish useful work.

Think about two people trying to pass each other in a narrow hallway:

```text
Person A → moves left
Person B → moves left

Person A → moves right
Person B → moves right
```

Both keep reacting but don't progress.

That's similar to **livelock**.

---

# 53. `volatile`

Now suppose one thread changes:

```java
running = false;
```

and another thread continuously reads it.

`volatile` can be used when we need the variable's updates to be visible across threads:

```java
volatile boolean running = true;
```

But:

```java
volatile int count;

count++;
```

is **not automatically atomic**.

Remember:

```text
volatile → visibility
synchronized → mutual exclusion + memory synchronization
```

---

# 54. AtomicInteger

If we need atomic increment:

```java
import java.util.concurrent.atomic.AtomicInteger;

class Counter
{
    AtomicInteger count =
        new AtomicInteger();

    void increment()
    {
        count.incrementAndGet();
    }
}
```

Now:

```text
incrementAndGet()
```

is an atomic increment operation.

---

# 55. Thread Safety

A class is thread-safe when its behavior remains correct when accessed concurrently according to its contract.

Common approaches:

```text
Synchronization
     +
Atomic classes
     +
Immutable objects
     +
Thread confinement
     +
Concurrent collections
     +
Locks
```

---

# 56. Modern Way — ExecutorService

In real applications, you usually don't want to manually create a huge number of threads.

Java provides:

```java
ExecutorService
```

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

Think:

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

# 57. Thread Pool

Suppose you have 100 tasks.

Instead of:

```text
100 tasks
 ↓
100 threads
```

you might use:

```text
100 tasks
     ↓
Thread Pool
     ↓
5 Worker Threads
```

The workers process tasks from the pool.

This controls concurrency and avoids unnecessary thread creation.

---

# 58. Runnable vs Callable

### Runnable

```java
Runnable task = () ->
{
    System.out.println("Hello");
};
```

No result.

### Callable

```java
Callable<Integer> task = () ->
{
    return 100;
};
```

Can return a result and throw checked exceptions.

---

# 59. Future

When submitting a `Callable`:

```java
Future<Integer> result =
    service.submit(task);
```

You can later obtain:

```java
Integer value = result.get();
```

`get()` waits if necessary.

---

# 60. Complete Learning Map

```text
                     MULTITHREADING
                           |
             ┌─────────────┴─────────────┐
             ↓                           ↓
          THREAD                      PROCESS
             |
     ┌───────┼────────┐
     ↓       ↓        ↓
 Thread   Runnable  Callable
     |
     ↓
 start()
     |
     ↓
  Lifecycle
     |
 ┌───┼─────────────────────┐
 ↓   ↓      ↓       ↓      ↓
NEW RUNNABLE BLOCKED WAITING TIMED_WAITING
                              |
                              ↓
                         TERMINATED


             SHARED MUTABLE DATA
                     |
                     ↓
              RACE CONDITION
                     |
                     ↓
              CRITICAL SECTION
                     |
                     ↓
               SYNCHRONIZATION
                     |
          ┌──────────┼──────────┐
          ↓          ↓          ↓
      synchronized   Lock      Atomic
          |
     ┌────┴────┐
     ↓         ↓
   Method     Block
     |
     ↓
   Monitor
     |
     ↓
 wait / notify / notifyAll
```

---

# 61. TEACHME Final Story

If you remember only **one story**, remember this:

### Step 1

Java starts:

```text
main thread
```

### Step 2

You create more threads:

```text
main
 ├── Thread 1
 ├── Thread 2
 └── Thread 3
```

### Step 3

You start them:

```java
t.start();
```

### Step 4

Each thread executes its task through:

```java
run()
```

### Step 5

Threads may need to share data:

```text
Thread 1 ──┐
           ↓
       Shared Data
           ↑
Thread 2 ──┘
```

### Step 6

Unsafe sharing can cause:

```text
Race Condition
```

### Step 7

Protect the critical section:

```java
synchronized
```

### Step 8

If threads need to coordinate:

```java
wait()
notify()
notifyAll()
```

### Step 9

If threads are waiting for each other incorrectly:

```text
Deadlock
```

### Step 10

For larger applications, use:

```text
ExecutorService
Thread Pools
Concurrent Collections
Atomic Classes
Locks
CompletableFuture
```

---

# ⭐ The 15 Rules You Must Never Forget

```text
1. Thread = unit of execution.

2. Process can contain multiple threads.

3. main() executes on the main thread.

4. start() starts a new thread of execution.

5. Direct run() call does not create a new thread.

6. A Thread object cannot be started more than once.

7. sleep() pauses the current thread.

8. sleep() does NOT release an intrinsic monitor.

9. join() makes the calling thread wait for another thread.

10. Race condition happens because of unsafe concurrent access.

11. synchronized provides mutual exclusion for the same monitor.

12. synchronized instance method uses the object's monitor.

13. static synchronized method uses the Class object's monitor.

14. wait() releases the corresponding monitor; sleep() does not.

15. wait(), notify(), and notifyAll() require ownership of the corresponding monitor.
```

### And the most important mental model:

```text
                 MULTITHREADING
                       ↓
             Multiple Threads
                       ↓
              Shared Resources
                       ↓
              Race Conditions
                       ↓
               Synchronization
                       ↓
              ┌────────┴────────┐
              ↓                 ↓
        synchronized          Locks
              ↓
            Monitor
              ↓
       Mutual Exclusion
              ↓
      Thread-safe access
              ↓
      wait / notify / notifyAll
              ↓
       Inter-thread communication
```

That is the complete **TEACHME picture**: first understand threads, then their life cycle and methods, then understand why shared data creates problems, and only then learn **synchronization, monitors, `wait()`/`notify()`, deadlock, and modern concurrency utilities**.
