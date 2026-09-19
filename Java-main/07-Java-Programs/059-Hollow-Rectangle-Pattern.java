/*
 * ============================================================================
 * Program Name : Hollow Rectangle Pattern
 * File Name    : 059-Hollow-Rectangle-Pattern.java
 * Class Name   : HollowRectanglePattern
 *
 * Description:
 * This program accepts the number of rows and columns from the user
 * and prints a Hollow Rectangle Star Pattern using nested for loops.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use nested for loops.
 * - Print a Hollow Rectangle Star Pattern.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class HollowRectanglePattern {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the number of rows.
        System.out.print("Enter the Number of Rows: ");

        // Read the number of rows entered by the user.
        int rows = scanner.nextInt();

        // Ask the user to enter the number of columns.
        System.out.print("Enter the Number of Columns: ");

        // Read the number of columns entered by the user.
        int columns = scanner.nextInt();

        // Check whether the entered values are valid.
        if (rows <= 0 || columns <= 0) {

            // Display an error message.
            System.out.println("Please enter positive integers for rows and columns.");
            // Example Output:
            // Please enter positive integers for rows and columns.

        } else {

            // Display the heading.
            System.out.println("Hollow Rectangle Pattern:");

            // Outer loop controls the number of rows.
            for (int i = 1; i <= rows; i++) {

                // Inner loop controls the number of columns.
                for (int j = 1; j <= columns; j++) {

                    // Check whether the current position is on the border.
                    if (i == 1 || i == rows || j == 1 || j == columns) {

                        // Print a star for the border.
                        System.out.print("* ");

                    } else {

                        // Print spaces inside the rectangle.
                        System.out.print("  ");

                    }

                }

                // Move to the next line after printing one row.
                System.out.println();

            }

            // Example Output:
            // Enter the Number of Rows: 5
            // Enter the Number of Columns: 6
            // Hollow Rectangle Pattern:
            // * * * * * *
            // *         *
            // *         *
            // *         *
            // * * * * * *

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
