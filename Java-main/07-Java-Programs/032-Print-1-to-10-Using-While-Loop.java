/*
 * ============================================================================
 * Program Name : Print 1 to 10 Using While Loop
 * File Name    : 032-Print-1-to-10-Using-While-Loop.java
 * Class Name   : Print1To10UsingWhileLoop
 *
 * Description:
 * This program demonstrates how to print numbers from 1 to 10
 * using the while loop in Java.
 *
 * Objective:
 * - Understand the syntax of the while loop.
 * - Learn how to execute a block of code repeatedly.
 * - Print a sequence of numbers using iteration.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class Print1To10UsingWhileLoop {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize the loop variable.
        int number = 1;

        // Display a heading.
        System.out.println("Numbers from 1 to 10:");

        // Execute the loop until the value becomes greater than 10.
        while (number <= 10) {

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
    }
}
