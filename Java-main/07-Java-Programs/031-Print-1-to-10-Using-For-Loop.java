/*
 * ============================================================================
 * Program Name : Print 1 to 10 Using For Loop
 * File Name    : 031-Print-1-to-10-Using-For-Loop.java
 * Class Name   : Print1To10UsingForLoop
 *
 * Description:
 * This program demonstrates how to print numbers from 1 to 10
 * using the for loop in Java.
 *
 * Objective:
 * - Understand the syntax of the for loop.
 * - Learn how to execute a block of code repeatedly.
 * - Print a sequence of numbers using iteration.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class Print1To10UsingForLoop {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Display a heading.
        System.out.println("Numbers from 1 to 10:");

        // Iterate from 1 to 10 using the for loop.
        for (int number = 1; number <= 10; number++) {

            // Display the current value of the loop variable.
            System.out.println(number);
            // Output:
            // 1
            // 2
            // 3
            // ...
            // 10

        }
    }
}
