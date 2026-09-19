/*
 * ============================================================================
 * Program Name : Strong Number
 * File Name    : 040-Strong-Number.java
 * Class Name   : StrongNumber
 *
 * Description:
 * This program accepts an integer from the user and checks whether
 * the given number is a Strong Number or not using the while loop.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use nested loops.
 * - Calculate the factorial of each digit.
 * - Compare the sum of factorials with the original number.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class StrongNumber {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a number.
        System.out.print("Enter a Number: ");

        // Read the number entered by the user.
        int number = scanner.nextInt();

        // Store the original number for comparison.
        int originalNumber = number;

        // Declare and initialize a variable to store the sum of factorials.
        int sum = 0;

        // Process each digit of the given number.
        while (number != 0) {

            // Extract the last digit.
            int digit = number % 10;

            // Declare and initialize the factorial variable.
            int factorial = 1;

            // Calculate the factorial of the extracted digit.
            for (int i = 1; i <= digit; i++) {

                // Multiply the current value to calculate factorial.
                factorial = factorial * i;

            }

            // Add the factorial of the digit to the sum.
            sum = sum + factorial;

            // Remove the last digit from the number.
            number = number / 10;

        }

        // Check whether the original number is equal to the sum of factorials.
        if (originalNumber == sum) {

            // Display that the number is a Strong Number.
            System.out.println(originalNumber + " is a Strong Number.");
            // Example Output:
            // 145 is a Strong Number.

        } else {

            // Display that the number is not a Strong Number.
            System.out.println(originalNumber + " is Not a Strong Number.");
            // Example Output:
            // 123 is Not a Strong Number.

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
