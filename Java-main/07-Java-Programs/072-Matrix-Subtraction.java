/*
 * ============================================================================
 * Program Name : Matrix Subtraction
 * File Name    : 072-Matrix-Subtraction.java
 * Class Name   : MatrixSubtraction
 *
 * Description:
 * This program accepts two matrices of the same order from the user
 * and performs matrix subtraction. The resulting matrix is then displayed.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to declare and use two-dimensional arrays.
 * - Perform matrix subtraction using nested loops.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class MatrixSubtraction {

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

            // Declare the first matrix.
            int[][] matrix1 = new int[rows][columns];

            // Declare the second matrix.
            int[][] matrix2 = new int[rows][columns];

            // Declare the result matrix.
            int[][] result = new int[rows][columns];

            // Ask the user to enter the elements of the first matrix.
            System.out.println("Enter the Elements of First Matrix:");

            // Read the first matrix elements.
            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < columns; j++) {

                    // Store the current element.
                    matrix1[i][j] = scanner.nextInt();

                }

            }

            // Ask the user to enter the elements of the second matrix.
            System.out.println("Enter the Elements of Second Matrix:");

            // Read the second matrix elements.
            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < columns; j++) {

                    // Store the current element.
                    matrix2[i][j] = scanner.nextInt();

                }

            }

            // Perform matrix subtraction.
            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < columns; j++) {

                    // Subtract the corresponding elements.
                    result[i][j] = matrix1[i][j] - matrix2[i][j];

                }

            }

            // Display the first matrix.
            System.out.println("First Matrix:");

            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < columns; j++) {

                    System.out.print(matrix1[i][j] + " ");

                }

                System.out.println();

            }

            // Display the second matrix.
            System.out.println("Second Matrix:");

            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < columns; j++) {

                    System.out.print(matrix2[i][j] + " ");

                }

                System.out.println();

            }

            // Display the resultant matrix.
            System.out.println("Resultant Matrix After Subtraction:");

            for (int i = 0; i < rows; i++) {

                for (int j = 0; j < columns; j++) {

                    System.out.print(result[i][j] + " ");

                }

                System.out.println();

            }

            // Example Output:
            // Enter the Number of Rows: 2
            // Enter the Number of Columns: 2
            // Enter the Elements of First Matrix:
            // 8 6
            // 5 4
            // Enter the Elements of Second Matrix:
            // 3 2
            // 1 4
            // First Matrix:
            // 8 6
            // 5 4
            // Second Matrix:
            // 3 2
            // 1 4
            // Resultant Matrix After Subtraction:
            // 5 4
            // 4 0

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
