# Multi Threading in Java — DOUBTKILLER

This section is designed to kill the **common doubts, traps, interview questions, and confusing points** in Java Multithreading.

---

# 1. Is Multithreading and Multiprocessing the Same?

**No.**

### Multiprocessing

Multiple processes execute independently.

```text
Process 1
Process 2
Process 3
```

### Multithreading

Multiple threads exist inside a process.

```text
             Process
                |
       ┌────────┼────────┐
       ↓        ↓        ↓
    Thread 1 Thread 2 Thread 3
```

### Remember

> **Process = independent execution environment**
> **Thread = execution unit inside a process**

---

# 2. Is a Thread a Process?

❌ No.

A thread is a **unit of execution within a process**.

```text
Process
   |
   ├── Thread 1
   ├── Thread 2
   └── Thread 3
```

---

# 3. Is `main()` a Thread?

The `main()` method itself is not a thread.

The JVM starts a **main thread**, and that thread invokes:

```java
main(String[] args)
```

So:

```text
JVM
 ↓
Main Thread
 ↓
main()
```

---

# 4. Does Every Java Program Have Only One Thread?

❌ No.

Even a simple Java program starts with the main thread, and the JVM/runtime may have other threads as well.

Your application can additionally create many threads.

---

# 5. What Is the Difference Between `start()` and `run()`?

This is the **#1 multithreading doubt**.

### `start()`

```java
t.start();
```

Requests that the thread be started. The JVM then invokes `run()` on that new thread.

### `run()`

```java
t.run();
```

is simply a normal method call.

### Remember:

```text
start()
  ↓
new thread execution
  ↓
run()
```

but:

```text
run()
  ↓
normal method call
```

---

# 6. What Happens If I Call `run()` Instead of `start()`?

Example:

```java
class Test extends Thread
{
    public void run()
    {
        System.out.println(
            Thread.currentThread().getName()
        );
    }

    public static void main(String[] args)
    {
        Test t = new Test();

        t.run();
    }
}
```

Output:

```text
main
```

Why?

Because `run()` was directly invoked by the main thread.

---

# 7. What Happens If I Use `start()`?

```java
class Test extends Thread
{
    public void run()
    {
        System.out.println(
            Thread.currentThread().getName()
        );
    }

    public static void main(String[] args)
    {
        Test t = new Test();

        t.start();
    }
}
```

The `run()` method executes on the newly started thread.

The exact thread name can vary, but it will not simply be the main thread.

---

# 8. Can We Call `start()` Twice?

❌ No.

```java
t.start();
t.start();
```

The second attempt throws:

```text
java.lang.IllegalThreadStateException
```

### Remember

> A particular `Thread` object can be started only once.

---

# 9. Can We Call `run()` Twice?

Yes.

```java
t.run();
t.run();
```

There is no rule that prevents normal method invocation.

But remember:

> Calling `run()` directly does not create a new thread.

---

# 10. Does `start()` Execute `run()` Immediately?

Not necessarily.

After:

```java
t.start();
```

the thread becomes eligible for scheduling.

The scheduler determines when it actually executes.

Therefore:

```java
t.start();

System.out.println("Main");
```

doesn't guarantee which message appears first.

---

# 11. Why Does Multithreaded Output Change Every Time?

Example:

```java
class Test extends Thread
{
    public void run()
    {
        for(int i = 1; i <= 5; i++)
            System.out.println("Child " + i);
    }

    public static void main(String[] args)
    {
        Test t = new Test();

        t.start();

        for(int i = 1; i <= 5; i++)
            System.out.println("Main " + i);
    }
}
```

One possible output:

```text
Main 1
Child 1
Main 2
Child 2
Child 3
Main 3
Main 4
Child 4
Main 5
Child 5
```

Another run may produce a different order.

### Why?

Because thread scheduling is not something you should use to assume a deterministic ordering unless you explicitly establish that ordering.

---

# 12. Does Multithreading Mean Threads Always Execute Simultaneously?

❌ Not necessarily.

Two related concepts are:

### Concurrency

Multiple tasks make progress during overlapping periods.

### Parallelism

Multiple tasks literally execute at the same time on different CPU cores.

```text
Concurrency:
T1 → T2 → T1 → T2

Parallelism:
CPU 1 → T1
CPU 2 → T2
```

Multithreading can be used for concurrent execution and, where the runtime/OS and hardware allow it, parallel execution.

---

# 13. Is Thread Faster Than a Process?

Generally, threads are **lighter-weight** than processes.

Threads within the same process can share process resources, while processes have stronger isolation.

But don't conclude:

> "More threads always means more speed."

❌ Wrong.

Too many threads can cause:

* context-switch overhead
* memory overhead
* contention
* synchronization overhead

---

# 14. What Is a Race Condition?

Suppose:

```java
int count = 0;
```

Two threads execute:

```java
count++;
```

You might expect:

```text
Thread 1 → +1
Thread 2 → +1
Final = 2
```

But:

```text
count++
```

is conceptually:

```text
READ
 ↓
ADD
 ↓
WRITE
```

Possible interleaving:

```text
T1 → reads 0
T2 → reads 0
T1 → writes 1
T2 → writes 1
```

Final:

```text
1
```

instead of:

```text
2
```

This is a **race condition**.

---

# 15. What Is a Critical Section?

A **critical section** is a section of code that accesses shared state and therefore needs appropriate coordination when multiple threads can execute it concurrently.

Example:

```java
synchronized
{
    count++;
}
```

Conceptually:

```text
Thread 1 ──→ Critical Section
Thread 2 ──→ waits/competes
```

---

# 16. Does `synchronized` Make the Whole Program Single-Threaded?

❌ No.

Only the relevant synchronized region protected by the same monitor is mutually exclusive.

Example:

```text
Thread 1 → normal code ──→ synchronized section ──→ normal code
Thread 2 → normal code ──→ synchronized section ──→ normal code
```

Threads can still execute other independent work concurrently.

---

# 17. What Does `synchronized` Actually Do?

It provides mutual exclusion around the synchronized region for a particular monitor.

Example:

```java
class Counter
{
    int count;

    synchronized void increment()
    {
        count++;
    }
}
```

For the same object:

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

# 18. What Is the Monitor?

A monitor is the synchronization mechanism associated with an object/class that controls ownership of an intrinsic lock and supports `wait()`/`notify()` coordination.

Think:

```text
Object
   |
   ↓
Monitor / intrinsic lock
   |
   ↓
one owner at a time
```

---

# 19. Does Every Object Have a Lock?

Every Java object can be used as the object associated with an intrinsic monitor for synchronization.

For example:

```java
Object lock = new Object();

synchronized(lock)
{
    // protected code
}
```

The lock used here is associated with `lock`.

---

# 20. Does `synchronized` Lock the Method?

This wording causes confusion.

For an instance synchronized method:

```java
synchronized void test()
{
}
```

Java doesn't mean that the method itself is locked globally.

The **object's monitor** is acquired for the invocation.

---

# 21. What Is the Lock for a Static Synchronized Method?

Example:

```java
static synchronized void test()
{
}
```

The lock is associated with the **Class object**.

Conceptually:

```text
Instance synchronized
       ↓
Object monitor

Static synchronized
       ↓
Class object's monitor
```

---

# 22. Two Objects = Two Locks?

Suppose:

```java
Counter c1 = new Counter();
Counter c2 = new Counter();
```

Then:

```text
c1 → monitor A
c2 → monitor B
```

If:

```text
Thread 1 → c1 synchronized method
Thread 2 → c2 synchronized method
```

they don't automatically block one another merely because the methods have the same declaration.

---

# 23. Does `sleep()` Release the Lock?

❌ **No.**

This is a very important doubt.

```java
synchronized(lock)
{
    Thread.sleep(5000);
}
```

During the sleep:

```text
Thread → sleeping
        ↓
still owns monitor
```

So another thread requiring that same monitor can remain blocked.

---

# 24. Does `wait()` Release the Lock?

✅ Yes.

```java
synchronized(lock)
{
    lock.wait();
}
```

Conceptually:

```text
Thread
 ↓
wait()
 ↓
releases lock
 ↓
WAITING
```

When later awakened, the thread must reacquire the monitor before continuing beyond the `wait()` call.

---

# 25. `sleep()` vs `wait()` — Final Answer

| Point                       | `sleep()`                              | `wait()`            |
| --------------------------- | -------------------------------------- | ------------------- |
| Class                       | `Thread`                               | `Object`            |
| Main purpose                | Timed pause                            | Thread coordination |
| Releases monitor?           | ❌ No                                   | ✅ Yes               |
| Monitor ownership required? | ❌ No                                   | ✅ Yes               |
| Can wait indefinitely?      | No, ordinary `sleep` requires duration | Yes                 |
| Can use timeout?            | Yes                                    | Yes                 |

---

# 26. Why Does `wait()` Belong to `Object`?

Because the wait/notification mechanism is associated with an object's **monitor**.

Therefore:

```java
obj.wait();
obj.notify();
obj.notifyAll();
```

operate with respect to `obj`'s monitor.

---

# 27. Can I Call `wait()` Anywhere?

❌ Not arbitrarily.

This is incorrect:

```java
lock.wait();
```

if the current thread doesn't own `lock`'s monitor.

Correct:

```java
synchronized(lock)
{
    lock.wait();
}
```

Otherwise:

```text
IllegalMonitorStateException
```

---

# 28. Same for `notify()`?

Yes.

Correct:

```java
synchronized(lock)
{
    lock.notify();
}
```

The current thread must own the corresponding monitor.

---

# 29. Does `notify()` Immediately Run the Waiting Thread?

❌ No.

`notify()` does not mean:

> "Run this thread immediately."

It makes one waiting thread eligible to compete for the monitor.

The notifying thread still has to leave the synchronized region before another thread can acquire that monitor.

---

# 30. Does `notifyAll()` Run All Threads Simultaneously?

❌ No.

It wakes all threads waiting on that monitor in the sense that they become eligible to compete for the monitor.

Only one can own that monitor at a time.

```text
notifyAll()
   ↓
T1 ─┐
T2 ─┼→ compete for monitor
T3 ─┘
   ↓
one acquires it
```

---

# 31. Why Should `wait()` Usually Be Inside `while`?

Correct:

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

Why?

Because after waking up, the thread should **re-check the condition** before proceeding.

---

# 32. What Is `join()`?

Suppose:

```text
Main
 |
 +── starts Thread T
 |
 +── join()
```

When main executes:

```java
t.join();
```

the **main thread waits for `t` to terminate**.

It is not the same thing as `wait()`.

---

# 33. `join()` vs `wait()`

| `join()`                                   | `wait()`                                        |
| ------------------------------------------ | ----------------------------------------------- |
| Method of `Thread`                         | Method of `Object`                              |
| Used to wait for a thread's termination    | Used for coordination around a monitor          |
| Calling thread waits for target thread     | Current thread waits for notification/condition |
| Associated with target thread's completion | Associated with an object's monitor             |

---

# 34. What Happens to a Thread After `run()` Finishes?

It enters:

```text
TERMINATED
```

It cannot be restarted.

```text
NEW
 ↓
start()
 ↓
RUNNABLE
 ↓
run() completes
 ↓
TERMINATED
```

---

# 35. Can a Terminated Thread Be Started Again?

❌ No.

```java
t.start();   // first time

// after termination
t.start();   // IllegalThreadStateException
```

Create another `Thread` object if you need another execution.

---

# 36. What Is Deadlock?

Deadlock occurs when threads are permanently waiting for locks/resources held by one another.

Example:

```text
Thread 1
  |
holds Lock A
  |
waits for Lock B
       ↑
       |
holds Lock B
  |
Thread 2
  |
waits for Lock A
```

Neither progresses.

---

# 37. How Can We Reduce Deadlock Risk?

A common strategy is to acquire multiple locks in a **consistent global order**.

For example:

```text
Always acquire:
Lock A
   ↓
Lock B
```

Never:

```text
Thread 1: A → B
Thread 2: B → A
```

This can eliminate one common circular-wait pattern.

Other approaches include minimizing lock scope and using higher-level concurrency utilities.

---

# 38. What Is Starvation?

One thread keeps getting denied the resources/opportunities it needs.

```text
T1 → repeatedly gets resource
T2 → repeatedly waits
```

Unlike deadlock, other threads may continue making progress.

---

# 39. What Is Livelock?

Threads are not blocked, but continuously react to each other without completing useful work.

```text
T1 → changes behavior
T2 → reacts
T1 → reacts
T2 → reacts
```

They are active but don't progress.

---

# 40. Is `volatile` the Same as `synchronized`?

❌ No.

### `volatile`

Primarily provides visibility and ordering guarantees for accesses to that variable.

### `synchronized`

Provides mutual exclusion and memory synchronization.

```text
volatile
   ↓
visibility/order

synchronized
   ↓
mutual exclusion
+
visibility/order
```

---

# 41. Is `volatile count++` Thread-Safe?

❌ No.

```java
volatile int count;

count++;
```

`count++` is a read-modify-write operation.

`volatile` doesn't turn it into one indivisible atomic operation.

Use an appropriate atomic class or synchronization when atomic increment is required.

---

# 42. What Is `AtomicInteger`?

```java
AtomicInteger count = new AtomicInteger();

count.incrementAndGet();
```

It provides atomic operations without requiring you to synchronize that increment yourself.

Common atomic classes include:

```text
AtomicInteger
AtomicLong
AtomicBoolean
```

---

# 43. Is `String` Thread-Safe?

`String` is immutable.

Once a `String` object is created, its state cannot be changed.

This makes sharing `String` objects between threads much safer than sharing mutable objects.

But thread safety of an application still depends on the **overall shared state**, not simply whether one field happens to be a `String`.

---

# 44. Is `StringBuilder` Thread-Safe?

❌ No.

`StringBuilder` is designed for efficient single-threaded use.

For shared mutable text state across threads, you need appropriate external synchronization or a suitable concurrent design.

---

# 45. Is `StringBuffer` Thread-Safe?

`StringBuffer` has synchronized methods and is designed for thread-safe operations on the buffer itself.

But remember:

> Thread-safe individual operations don't automatically make an entire multi-step algorithm thread-safe.

---

# 46. Can We Create Multiple Threads From One Class?

Yes.

```java
class Demo extends Thread
{
    public void run()
    {
        System.out.println("Running");
    }

    public static void main(String[] args)
    {
        Demo t1 = new Demo();
        Demo t2 = new Demo();
        Demo t3 = new Demo();

        t1.start();
        t2.start();
        t3.start();
    }
}
```

```text
Demo
 |
 ├── t1
 ├── t2
 └── t3
```

Each is a separate `Thread` object.

---

# 47. Can Multiple Threads Execute the Same Runnable Object?

Yes.

```java
class Task implements Runnable
{
    public void run()
    {
        System.out.println("Task");
    }
}

class Demo
{
    public static void main(String[] args)
    {
        Task task = new Task();

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
    }
}
```

Here both threads execute the same `Runnable` object's `run()` method.

If that object contains mutable shared state, synchronization may be necessary.

---

# 48. `Thread` vs `Runnable`

| `Thread`                         | `Runnable`                           |
| -------------------------------- | ------------------------------------ |
| Represents a thread              | Represents a task                    |
| Class is extended                | Interface is implemented             |
| Uses `start()` on Thread object  | Create a Thread with Runnable        |
| Prevents extending another class | Class can still extend another class |

This is one reason `Runnable` is often preferable when modeling a task separately from the thread executing it.

---

# 49. What Is a Daemon Thread?

A daemon thread is a background thread.

```java
Thread t = new Thread(task);

t.setDaemon(true);

t.start();
```

Important:

```text
setDaemon(true)
       ↓
BEFORE start()
```

The JVM does not remain alive solely because daemon threads remain after all non-daemon threads have terminated.

---

# 50. What Is Thread Priority?

Java provides:

```text
MIN_PRIORITY  = 1
NORM_PRIORITY = 5
MAX_PRIORITY  = 10
```

Example:

```java
t.setPriority(Thread.MAX_PRIORITY);
```

### Doubt:

> Does maximum priority guarantee first execution?

❌ No.

Never use priority as a substitute for proper synchronization or coordination.

---

# 51. What Is ExecutorService?

Instead of manually creating a thread for every task:

```text
1000 tasks
 ↓
1000 threads
```

you can use a thread pool:

```text
1000 tasks
      ↓
ExecutorService
      ↓
Thread Pool
      ↓
Fixed number of workers
```

Example:

```java
ExecutorService service =
    Executors.newFixedThreadPool(3);
```

Then:

```java
service.submit(task);
```

---

# 52. Why Is Thread Pool Better?

It can:

* reuse worker threads
* control the number of concurrent tasks
* reduce thread-creation overhead
* provide task management

And after submitting work:

```java
service.shutdown();
```

should be used when you no longer need the executor to accept new tasks.

---

# 53. Runnable vs Callable

### Runnable

```java
Runnable r = () ->
{
    System.out.println("Hello");
};
```

Primarily represents work with no returned result.

### Callable

```java
Callable<Integer> c = () ->
{
    return 100;
};
```

Can return a result and throw checked exceptions.

---

# 54. What Is Future?

```java
Future<Integer> f =
    service.submit(c);
```

Later:

```java
Integer result = f.get();
```

`get()` waits if the computation isn't finished yet.

So:

```text
Callable
   ↓
ExecutorService
   ↓
Future
   ↓
get()
   ↓
Result
```

---

# 55. Biggest Synchronization Doubt

### Does this:

```java
synchronized void method()
{
}
```

lock every object of that class?

❌ No.

It synchronizes on the **particular object** on which the instance method is invoked.

For:

```java
A a1 = new A();
A a2 = new A();
```

conceptually:

```text
a1 → Monitor 1
a2 → Monitor 2
```

---

# 56. Biggest `wait()` Doubt

### Does `wait()` mean "sleep"?

Not exactly.

`wait()` is a **coordination mechanism**.

The thread:

```text
wait()
 ↓
releases corresponding monitor
 ↓
waits
 ↓
gets notified / otherwise becomes eligible
 ↓
reacquires monitor
 ↓
continues
```

---

# 57. Biggest `notify()` Doubt

### Does `notify()` release the lock?

Not immediately.

The notifying thread still owns the monitor until it exits the synchronized region.

The waiting thread can proceed only after it successfully reacquires that monitor.

---

# 58. Biggest `sleep()` Doubt

### Does `sleep()` make the thread dead?

❌ No.

It temporarily places the thread into:

```text
TIMED_WAITING
```

After the sleep period, it can become eligible to run again.

---

# 59. Biggest `join()` Doubt

### Does `join()` stop the target thread?

❌ No.

It makes the **calling thread wait for the target thread to terminate**.

```text
Main → join(child)
       ↓
Main waits

Child → continues
       ↓
terminates

Main → continues
```

---

# 60. Biggest `synchronized` Doubt

### Does synchronization guarantee fairness?

❌ No.

`synchronized` provides the required monitor-based mutual exclusion and memory synchronization, but you should not assume a particular fairness ordering between competing threads.

---

# 61. Biggest Multithreading Doubt

### "If I create 10 threads, will they execute in the order 1, 2, 3...10?"

❌ Absolutely not guaranteed.

```java
t1.start();
t2.start();
t3.start();
```

does not establish:

```text
t1
 ↓
t2
 ↓
t3
```

execution order.

If order matters, explicitly coordinate the threads using mechanisms such as `join()`, locks/conditions, executors, or other concurrency constructs.

---

# 🔥 FINAL DOUBTKILLER TABLE

| Doubt                                                          | Correct Answer                    |
| -------------------------------------------------------------- | --------------------------------- |
| `Thread` = process?                                            | ❌ No                              |
| `main()` itself = thread?                                      | ❌ Main method runs on main thread |
| `start()` creates/starts thread execution?                     | ✅ Yes                             |
| `run()` directly creates a thread?                             | ❌ No                              |
| Can `start()` be called twice?                                 | ❌ No                              |
| Can `run()` be called directly twice?                          | ✅ Yes, as normal method calls     |
| Does `sleep()` release monitor?                                | ❌ No                              |
| Does `wait()` release corresponding monitor?                   | ✅ Yes                             |
| Is `wait()` a `Thread` method?                                 | ❌ `Object` method                 |
| Is `sleep()` an `Object` method?                               | ❌ `Thread` method                 |
| Does `notify()` immediately execute a waiting thread?          | ❌ No                              |
| Does `notifyAll()` execute all waiting threads simultaneously? | ❌ No                              |
| Does `synchronized` make the whole program single-threaded?    | ❌ No                              |
| Does `volatile` make `count++` atomic?                         | ❌ No                              |
| Does higher priority guarantee first execution?                | ❌ No                              |
| Can different objects have different intrinsic monitors?       | ✅ Yes                             |
| Does instance `synchronized` use object monitor?               | ✅ Yes                             |
| Does static `synchronized` use Class object's monitor?         | ✅ Yes                             |
| Can deadlock occur with multiple locks?                        | ✅ Yes                             |
| Is starvation the same as deadlock?                            | ❌ No                              |
| Is livelock the same as deadlock?                              | ❌ No                              |
| Does `join()` make calling thread wait for target completion?  | ✅ Yes                             |
| Can terminated Thread object be restarted?                     | ❌ No                              |
| Is `Runnable` a thread itself?                                 | ❌ It represents a task            |
| Is `ExecutorService` useful for managing tasks/threads?        | ✅ Yes                             |

---

# 🧠 Ultimate Memory Trick

Whenever you see a multithreading question, mentally walk through this:

```text
                 THREAD
                    ↓
               start()?
                    ↓
              run() executes
                    ↓
          Multiple threads running
                    ↓
             Shared data?
              /          \
            NO            YES
            ↓              ↓
         Usually       Race condition?
                          ↓
                         YES
                          ↓
                   Critical section
                          ↓
                    synchronized?
                          ↓
                       Monitor
                          ↓
              Need communication?
                   /            \
                 YES             NO
                  ↓               ↓
              wait()          continue
                  ↓
              notify()
              notifyAll()

             Possible problems
                    ↓
       ┌────────────┼────────────┐
       ↓            ↓            ↓
    Deadlock    Starvation    Livelock
```

### 🔥 The five lines to permanently remember

> **`start()` starts thread execution; `run()` is the task.**

> **`sleep()` pauses but does not release the monitor.**

> **`wait()` waits and releases the corresponding monitor.**

> **`synchronized` protects shared critical sections using monitors.**

> **Multithreading gives concurrency, but correctness requires proper coordination of shared state.**
