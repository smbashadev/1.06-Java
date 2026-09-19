/*
 * ============================================================================
 * Program Name : Jagged Array
 * File Name    : 076-Jagged-Array.java
 * Class Name   : JaggedArray
 *
 * Description:
 * This program demonstrates the use of a Jagged Array (Array of Arrays)
 * in Java. A jagged array allows each row to have a different number
 * of columns.
 *
 * Objective:
 * - Understand the concept of Jagged Arrays.
 * - Learn how to create rows with different column sizes.
 * - Learn how to read and display Jagged Array elements.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class JaggedArray {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the number of rows.
        System.out.print("Enter the Number of Rows: ");

        // Read the number of rows.
        int rows = scanner.nextInt();

        // Check whether the entered number of rows is valid.
        if (rows <= 0) {

            // Display an error message.
            System.out.println("Please enter a positive number of rows.");

        } else {

            // Declare a jagged array.
            int[][] jaggedArray = new int[rows][];

            // Create each row with different column sizes.
            for (int i = 0; i < rows; i++) {

                // Ask the user to enter the number of columns for the current row.
                System.out.print("Enter the Number of Columns for Row " + (i + 1) + ": ");

                // Read the number of columns.
                int columns = scanner.nextInt();

                // Allocate memory for the current row.
                jaggedArray[i] = new int[columns];

            }

            // Ask the user to enter the array elements.
            System.out.println("Enter the Jagged Array Elements:");

            // Read all elements of the jagged array.
            for (int i = 0; i < jaggedArray.length; i++) {

                System.out.println("Row " + (i + 1) + ":");

                for (int j = 0; j < jaggedArray[i].length; j++) {

                    // Store the current element.
                    jaggedArray[i][j] = scanner.nextInt();

                }

            }

            // Display the jagged array.
            System.out.println("Jagged Array Elements:");

            // Traverse the jagged array.
            for (int i = 0; i < jaggedArray.length; i++) {

                for (int j = 0; j < jaggedArray[i].length; j++) {

                    // Display the current element.
                    System.out.print(jaggedArray[i][j] + " ");

                }

                // Move to the next row.
                System.out.println();

            }

            // Example Output:
            // Enter the Number of Rows: 3
            // Enter the Number of Columns for Row 1: 2
            // Enter the Number of Columns for Row 2: 4
            // Enter the Number of Columns for Row 3: 3
            // Enter the Jagged Array Elements:
            // Row 1:
            // 10 20
            // Row 2:
            // 30 40 50 60
            // Row 3:
            // 70 80 90
            // Jagged Array Elements:
            // 10 20
            // 30 40 50 60
            // 70 80 90

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
