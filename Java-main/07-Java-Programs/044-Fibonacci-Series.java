/*
 * ============================================================================
 * Program Name : Fibonacci Series
 * File Name    : 044-Fibonacci-Series.java
 * Class Name   : FibonacciSeries
 *
 * Description:
 * This program accepts the number of terms from the user and
 * displays the Fibonacci Series using the for loop.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to generate the Fibonacci Series.
 * - Practice using variables and the for loop.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class FibonacciSeries {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the number of terms.
        System.out.print("Enter the Number of Terms: ");

        // Read the number of terms entered by the user.
        int terms = scanner.nextInt();

        // Check whether the entered number of terms is valid.
        if (terms <= 0) {

            // Display an error message.
            System.out.println("Please enter a positive integer.");
            // Example Output:
            // Please enter a positive integer.

        } else {

            // Declare and initialize the first Fibonacci number.
            int firstNumber = 0;

            // Declare and initialize the second Fibonacci number.
            int secondNumber = 1;

            // Display the heading.
            System.out.println("Fibonacci Series:");

            // Generate the Fibonacci Series.
            for (int i = 1; i <= terms; i++) {

                // Display the current Fibonacci number.
                System.out.print(firstNumber + " ");

                // Calculate the next Fibonacci number.
                int nextNumber = firstNumber + secondNumber;

                // Update the first number.
                firstNumber = secondNumber;

                // Update the second number.
                secondNumber = nextNumber;

            }

            // Move the cursor to the next line.
            System.out.println();

            // Example Output:
            // Enter the Number of Terms: 10
            // Fibonacci Series:
            // 0 1 1 2 3 5 8 13 21 34

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
