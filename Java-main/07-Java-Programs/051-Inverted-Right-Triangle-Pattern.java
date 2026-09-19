/*
 * ============================================================================
 * Program Name : Inverted Right Triangle Pattern
 * File Name    : 051-Inverted-Right-Triangle-Pattern.java
 * Class Name   : InvertedRightTrianglePattern
 *
 * Description:
 * This program accepts the number of rows from the user and prints
 * an Inverted Right Triangle Star Pattern using nested for loops.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use nested for loops.
 * - Print an Inverted Right Triangle Star Pattern.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class InvertedRightTrianglePattern {

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
            System.out.println("Inverted Right Triangle Pattern:");

            // Outer loop controls the number of rows.
            for (int i = rows; i >= 1; i--) {

                // Inner loop prints stars in each row.
                for (int j = 1; j <= i; j++) {

                    // Print a star followed by a space.
                    System.out.print("* ");

                }

                // Move to the next line after printing one row.
                System.out.println();

            }

            // Example Output:
            // Enter the Number of Rows: 5
            // Inverted Right Triangle Pattern:
            // * * * * *
            // * * * *
            // * * *
            // * *
            // *

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
