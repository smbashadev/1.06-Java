/*
 * ============================================================================
 * Program Name : Increment and Decrement Operators
 * File Name    : 010-Increment-and-Decrement-Operators.java
 * Class Name   : IncrementAndDecrementOperators
 *
 * Description:
 * This program demonstrates the use of increment (++) and decrement (--)
 * operators in Java. It also explains the difference between pre-increment,
 * post-increment, pre-decrement, and post-decrement operators.
 *
 * Increment and Decrement Operators:
 * ++  Increment Operator
 * --  Decrement Operator
 *
 * Types:
 * 1. Pre-Increment  (++variable)
 * 2. Post-Increment (variable++)
 * 3. Pre-Decrement  (--variable)
 * 4. Post-Decrement (variable--)
 *
 * Objective:
 * - Understand increment and decrement operators.
 * - Learn the difference between pre and post operators.
 * - Observe how variable values change during execution.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class IncrementAndDecrementOperators {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize an integer variable.
        int number = 10;

        // Print the initial value.
        System.out.println("Initial Value          : " + number); // Output: 10

        // Demonstrate pre-increment.
        System.out.println("Pre-Increment (++number): " + (++number)); // Output: 11

        // Print the value after pre-increment.
        System.out.println("Current Value          : " + number); // Output: 11

        // Demonstrate post-increment.
        System.out.println("Post-Increment (number++): " + (number++)); // Output: 11

        // Print the value after post-increment.
        System.out.println("Current Value          : " + number); // Output: 12

        // Demonstrate pre-decrement.
        System.out.println("Pre-Decrement (--number): " + (--number)); // Output: 11

        // Print the value after pre-decrement.
        System.out.println("Current Value          : " + number); // Output: 11

        // Demonstrate post-decrement.
        System.out.println("Post-Decrement (number--): " + (number--)); // Output: 11

        // Print the final value.
        System.out.println("Final Value            : " + number); // Output: 10
    }
}
