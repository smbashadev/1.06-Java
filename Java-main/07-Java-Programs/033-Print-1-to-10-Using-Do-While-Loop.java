/*
 * ============================================================================
 * Program Name : Print 1 to 10 Using Do-While Loop
 * File Name    : 033-Print-1-to-10-Using-Do-While-Loop.java
 * Class Name   : Print1To10UsingDoWhileLoop
 *
 * Description:
 * This program demonstrates how to print numbers from 1 to 10
 * using the do-while loop in Java.
 *
 * Objective:
 * - Understand the syntax of the do-while loop.
 * - Learn how the do-while loop executes at least once.
 * - Print a sequence of numbers using iteration.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class Print1To10UsingDoWhileLoop {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize the loop variable.
        int number = 1;

        // Display a heading.
        System.out.println("Numbers from 1 to 10:");

        // Execute the loop at least once.
        do {

            // Display the current value of the loop variable.
            System.out.println(number);
            // Output:
            // 1
            // 2
            // 3
            // ...
            // 10

            // Increment the loop variable.
            number++;

        }

        // Continue the loop until the value becomes greater than 10.
        while (number <= 10);
    }
}
