/*
 * ============================================================================
 * Program Name : Two-Dimensional Array
 * File Name    : 070-Two-Dimensional-Array.java
 * Class Name   : TwoDimensionalArray
 *
 * Description:
 * This program accepts the number of rows, columns, and elements of a
 * two-dimensional array from the user and displays the array in matrix form.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to declare and initialize a two-dimensional array.
 * - Learn how to access and display matrix elements using nested loops.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class TwoDimensionalArray {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the number of rows.
        System.out.print("Enter the Number of Rows: ");

        // Read the number of rows.
        int rows = scanner.nextInt();

        // Ask the user to enter the number of columns.
        System.out.print("Enter the Number of Columns: ");

        // Read the number of columns.
        int columns = scanner.nextInt();

        // Check whether the entered dimensions are valid.
        if (rows <= 0 || columns <= 0) {

            // Display an error message.
            System.out.println("Please enter positive values for rows and columns.");

        } else {

            // Declare a two-dimensional integer array.
            int[][] array = new int[rows][columns];

            // Ask the user to enter the array elements.
            System.out.println("Enter the Array Elements:");

            // Read all array elements using nested loops.
            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < columns; j++) {

                    // Store the current element in the array.
                    array[i][j] = scanner.nextInt();

                }

            }

            // Display the array elements.
            System.out.println("Two-Dimensional Array:");

            // Traverse the array using nested loops.
            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < columns; j++) {

                    // Print the current array element.
                    System.out.print(array[i][j] + " ");

                }

                // Move to the next row.
                System.out.println();

            }

            // Example Output:
            // Enter the Number of Rows: 2
            // Enter the Number of Columns: 3
            // Enter the Array Elements:
            // 10 20 30
            // 40 50 60
            // Two-Dimensional Array:
            // 10 20 30
            // 40 50 60

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
