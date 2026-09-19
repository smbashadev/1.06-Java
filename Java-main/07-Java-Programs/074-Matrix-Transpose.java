/*
 * ============================================================================
 * Program Name : Matrix Transpose
 * File Name    : 074-Matrix-Transpose.java
 * Class Name   : MatrixTranspose
 *
 * Description:
 * This program accepts a matrix from the user and displays its transpose.
 * The transpose of a matrix is obtained by interchanging its rows and columns.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to declare and use two-dimensional arrays.
 * - Learn how to find and display the transpose of a matrix.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class MatrixTranspose {

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

            // Declare the original matrix.
            int[][] matrix = new int[rows][columns];

            // Declare the transpose matrix.
            int[][] transpose = new int[columns][rows];

            // Ask the user to enter the matrix elements.
            System.out.println("Enter the Matrix Elements:");

            // Read the matrix elements.
            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < columns; j++) {

                    // Store the current element.
                    matrix[i][j] = scanner.nextInt();

                }

            }

            // Find the transpose of the matrix.
            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < columns; j++) {

                    // Swap rows with columns.
                    transpose[j][i] = matrix[i][j];

                }

            }

            // Display the original matrix.
            System.out.println("Original Matrix:");

            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < columns; j++) {

                    System.out.print(matrix[i][j] + " ");

                }

                System.out.println();

            }

            // Display the transpose matrix.
            System.out.println("Transpose Matrix:");

            for (int i = 0; i < columns; i++) {

                for (int j = 0; j < rows; j++) {

                    System.out.print(transpose[i][j] + " ");

                }

                System.out.println();

            }

            // Example Output:
            // Enter the Number of Rows: 2
            // Enter the Number of Columns: 3
            // Enter the Matrix Elements:
            // 1 2 3
            // 4 5 6
            // Original Matrix:
            // 1 2 3
            // 4 5 6
            // Transpose Matrix:
            // 1 4
            // 2 5
            // 3 6

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
