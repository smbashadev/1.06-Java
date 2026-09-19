/*
 * ============================================================================
 * Program Name : Pascal's Triangle
 * File Name    : 056-Pascals-Triangle.java
 * Class Name   : PascalsTriangle
 *
 * Description:
 * This program accepts the number of rows from the user and prints
 * Pascal's Triangle using nested for loops.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use nested for loops.
 * - Print Pascal's Triangle using combinations.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class PascalsTriangle {

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
            System.out.println("Pascal's Triangle:");

            // Outer loop controls the number of rows.
            for (int i = 0; i < rows; i++) {

                // Print the required leading spaces.
                for (int j = 1; j <= rows - i; j++) {

                    // Print two spaces for proper alignment.
                    System.out.print("  ");

                }

                // Initialize the first value of every row.
                int number = 1;

                // Print the values in the current row.
                for (int j = 0; j <= i; j++) {

                    // Print the current value.
                    System.out.print(number + "   ");

                    // Calculate the next value using Pascal's Triangle formula.
                    number = number * (i - j) / (j + 1);

                }

                // Move to the next line after printing one row.
                System.out.println();

            }

            // Example Output:
            // Enter the Number of Rows: 5
            // Pascal's Triangle:
            //           1
            //         1   1
            //       1   2   1
            //     1   3   3   1
            //   1   4   6   4   1

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
