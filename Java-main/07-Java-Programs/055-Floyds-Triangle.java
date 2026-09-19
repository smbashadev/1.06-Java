/*
 * ============================================================================
 * Program Name : Floyd's Triangle
 * File Name    : 055-Floyds-Triangle.java
 * Class Name   : FloydsTriangle
 *
 * Description:
 * This program accepts the number of rows from the user and prints
 * Floyd's Triangle using nested for loops.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use nested for loops.
 * - Print Floyd's Triangle using consecutive numbers.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class FloydsTriangle {

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
            System.out.println("Floyd's Triangle:");

            // Declare and initialize the first number.
            int number = 1;

            // Outer loop controls the number of rows.
            for (int i = 1; i <= rows; i++) {

                // Inner loop prints the numbers in each row.
                for (int j = 1; j <= i; j++) {

                    // Print the current number.
                    System.out.print(number + " ");

                    // Increment the number.
                    number++;

                }

                // Move to the next line after printing one row.
                System.out.println();

            }

            // Example Output:
            // Enter the Number of Rows: 5
            // Floyd's Triangle:
            // 1
            // 2 3
            // 4 5 6
            // 7 8 9 10
            // 11 12 13 14 15

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
