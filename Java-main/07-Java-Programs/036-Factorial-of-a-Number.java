/*
 * ============================================================================
 * Program Name : Factorial of a Number
 * File Name    : 036-Factorial-of-a-Number.java
 * Class Name   : FactorialOfANumber
 *
 * Description:
 * This program accepts a non-negative integer from the user and
 * calculates its factorial using the for loop.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the for loop.
 * - Calculate the factorial of a given number.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class FactorialOfANumber {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a number.
        System.out.print("Enter a Non-Negative Integer: ");

        // Read the number entered by the user.
        int number = scanner.nextInt();

        // Declare and initialize a variable to store the factorial.
        long factorial = 1;

        // Check whether the entered number is valid.
        if (number < 0) {

            // Display an error message for negative numbers.
            System.out.println("Factorial is not defined for negative numbers.");
            // Example Output:
            // Factorial is not defined for negative numbers.

        } else {

            // Iterate from 1 to the given number.
            for (int i = 1; i <= number; i++) {

                // Multiply the current value with the factorial.
                factorial = factorial * i;

            }

            // Display the calculated factorial.
            System.out.println("Factorial of " + number + " = " + factorial);
            // Example Output:
            // Enter a Non-Negative Integer: 5
            // Factorial of 5 = 120

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
