/*
 * ============================================================================
 * Program Name : Number Pattern
 * File Name    : 057-Number-Pattern.java
 * Class Name   : NumberPattern
 *
 * Description:
 * This program accepts the number of rows from the user and prints
 * a Number Pattern using nested for loops.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use nested for loops.
 * - Print a Number Pattern.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class NumberPattern {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the number of rows.
        System.out.print("Enter the Number of Rows: ");

        // Read the number of rows entered by the user.
        int rows = scanner.nextInt();

        // Check whether the entered number of rows is valid.
        if (rows <= 0) {

            // Display an error message.
            System.out.println("Please enter a positive integer.");
            // Example Output:
            // Please enter a positive integer.

        } else {

            // Display the heading.
            System.out.println("Number Pattern:");

            // Outer loop controls the number of rows.
            for (int i = 1; i <= rows; i++) {

                // Inner loop prints the current row number.
                for (int j = 1; j <= i; j++) {

                    // Print the current row number.
                    System.out.print(i + " ");

                }

                // Move to the next line after printing one row.
                System.out.println();

            }

            // Example Output:
            // Enter the Number of Rows: 5
            // Number Pattern:
            // 1
            // 2 2
            // 3 3 3
            // 4 4 4 4
            // 5 5 5 5 5

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
