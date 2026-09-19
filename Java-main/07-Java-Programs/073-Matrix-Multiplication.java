/*
 * ============================================================================
 * Program Name : Matrix Multiplication
 * File Name    : 073-Matrix-Multiplication.java
 * Class Name   : MatrixMultiplication
 *
 * Description:
 * This program accepts two matrices from the user and performs
 * matrix multiplication if the matrices are compatible.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to perform matrix multiplication using nested loops.
 * - Display the resultant matrix after multiplication.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class MatrixMultiplication {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the number of rows for the first matrix.
        System.out.print("Enter the Number of Rows for First Matrix: ");

        // Read the number of rows.
        int rows1 = scanner.nextInt();

        // Ask the user to enter the number of columns for the first matrix.
        System.out.print("Enter the Number of Columns for First Matrix: ");

        // Read the number of columns.
        int columns1 = scanner.nextInt();

        // Ask the user to enter the number of rows for the second matrix.
        System.out.print("Enter the Number of Rows for Second Matrix: ");

        // Read the number of rows.
        int rows2 = scanner.nextInt();

        // Ask the user to enter the number of columns for the second matrix.
        System.out.print("Enter the Number of Columns for Second Matrix: ");

        // Read the number of columns.
        int columns2 = scanner.nextInt();

        // Check whether the entered matrix dimensions are valid.
        if (rows1 <= 0 || columns1 <= 0 || rows2 <= 0 || columns2 <= 0) {

            // Display an error message.
            System.out.println("Please enter positive values for rows and columns.");

        }
        // Check whether the matrices are compatible for multiplication.
        else if (columns1 != rows2) {

            // Display an error message.
            System.out.println("Matrix multiplication is not possible.");
            System.out.println("The number of columns of the first matrix must be equal to the number of rows of the second matrix.");

        } else {

            // Declare the first matrix.
            int[][] matrix1 = new int[rows1][columns1];

            // Declare the second matrix.
            int[][] matrix2 = new int[rows2][columns2];

            // Declare the result matrix.
            int[][] result = new int[rows1][columns2];

            // Ask the user to enter the elements of the first matrix.
            System.out.println("Enter the Elements of First Matrix:");

            // Read the first matrix elements.
            for (int i = 0; i < rows1; i++) {

                for (int j = 0; j < columns1; j++) {

                    // Store the current element.
                    matrix1[i][j] = scanner.nextInt();

                }

            }

            // Ask the user to enter the elements of the second matrix.
            System.out.println("Enter the Elements of Second Matrix:");

            // Read the second matrix elements.
            for (int i = 0; i < rows2; i++) {

                for (int j = 0; j < columns2; j++) {

                    // Store the current element.
                    matrix2[i][j] = scanner.nextInt();

                }

            }

            // Perform matrix multiplication.
            for (int i = 0; i < rows1; i++) {

                for (int j = 0; j < columns2; j++) {

                    // Initialize the current result element.
                    result[i][j] = 0;

                    // Multiply corresponding elements and accumulate the sum.
                    for (int k = 0; k < columns1; k++) {

                        result[i][j] = result[i][j] + (matrix1[i][k] * matrix2[k][j]);

                    }

                }

            }

            // Display the first matrix.
            System.out.println("First Matrix:");

            for (int i = 0; i < rows1; i++) {

                for (int j = 0; j < columns1; j++) {

                    System.out.print(matrix1[i][j] + " ");

                }

                System.out.println();

            }

            // Display the second matrix.
            System.out.println("Second Matrix:");

            for (int i = 0; i < rows2; i++) {

                for (int j = 0; j < columns2; j++) {

                    System.out.print(matrix2[i][j] + " ");

                }

                System.out.println();

            }

            // Display the resultant matrix.
            System.out.println("Resultant Matrix After Multiplication:");

            for (int i = 0; i < rows1; i++) {

                for (int j = 0; j < columns2; j++) {

                    System.out.print(result[i][j] + " ");

                }

                System.out.println();

            }

            // Example Output:
            // Enter the Number of Rows for First Matrix: 2
            // Enter the Number of Columns for First Matrix: 2
            // Enter the Number of Rows for Second Matrix: 2
            // Enter the Number of Columns for Second Matrix: 2
            // Enter the Elements of First Matrix:
            // 1 2
            // 3 4
            // Enter the Elements of Second Matrix:
            // 5 6
            // 7 8
            // Resultant Matrix After Multiplication:
            // 19 22
            // 43 50

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
