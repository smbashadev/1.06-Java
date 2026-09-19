/*
 * ============================================================================
 * Program Name : Sum of First N Numbers
 * File Name    : 035-Sum-of-First-N-Numbers.java
 * Class Name   : SumOfFirstNNumbers
 *
 * Description:
 * This program accepts a positive integer from the user and calculates
 * the sum of the first N natural numbers using the for loop.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the for loop.
 * - Calculate the sum of the first N natural numbers.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class SumOfFirstNNumbers {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the value of N.
        System.out.print("Enter the Value of N: ");

        // Read the value entered by the user.
        int n = scanner.nextInt();

        // Declare and initialize a variable to store the sum.
        int sum = 0;

        // Check whether the entered value is valid.
        if (n <= 0) {

            // Display an error message.
            System.out.println("Please enter a positive integer.");
            // Example Output: Please enter a positive integer.

        } else {

            // Iterate from 1 to N using the for loop.
            for (int i = 1; i <= n; i++) {

                // Add the current number to the sum.
                sum = sum + i;

            }

            // Display the calculated sum.
            System.out.println("Sum of First " + n + " Natural Numbers = " + sum);
            // Example Output:
            // Enter the Value of N: 10
            // Sum of First 10 Natural Numbers = 55

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
