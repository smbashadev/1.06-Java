/*
 * ============================================================================
 * Program Name : Three-Dimensional Array
 * File Name    : 075-Three-Dimensional-Array.java
 * Class Name   : ThreeDimensionalArray
 *
 * Description:
 * This program accepts the dimensions and elements of a three-dimensional
 * array from the user and displays all the elements layer by layer.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to declare and initialize a three-dimensional array.
 * - Learn how to access and display 3D array elements using nested loops.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ThreeDimensionalArray {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the number of layers.
        System.out.print("Enter the Number of Layers: ");

        // Read the number of layers.
        int layers = scanner.nextInt();

        // Ask the user to enter the number of rows.
        System.out.print("Enter the Number of Rows: ");

        // Read the number of rows.
        int rows = scanner.nextInt();

        // Ask the user to enter the number of columns.
        System.out.print("Enter the Number of Columns: ");

        // Read the number of columns.
        int columns = scanner.nextInt();

        // Check whether the entered dimensions are valid.
        if (layers <= 0 || rows <= 0 || columns <= 0) {

            // Display an error message.
            System.out.println("Please enter positive values for layers, rows, and columns.");

        } else {

            // Declare a three-dimensional integer array.
            int[][][] array = new int[layers][rows][columns];

            // Ask the user to enter the array elements.
            System.out.println("Enter the Array Elements:");

            // Read all the elements using nested loops.
            for (int i = 0; i < layers; i++) {

                System.out.println("Layer " + (i + 1) + ":");

                for (int j = 0; j < rows; j++) {

                    for (int k = 0; k < columns; k++) {

                        // Store the current element.
                        array[i][j][k] = scanner.nextInt();

                    }

                }

            }

            // Display the array elements.
            System.out.println("Three-Dimensional Array:");

            // Traverse the array using nested loops.
            for (int i = 0; i < layers; i++) {

                System.out.println("Layer " + (i + 1) + ":");

                for (int j = 0; j < rows; j++) {

                    for (int k = 0; k < columns; k++) {

                        // Display the current element.
                        System.out.print(array[i][j][k] + " ");

                    }

                    // Move to the next row.
                    System.out.println();

                }

                // Leave a blank line after each layer.
                System.out.println();

            }

            // Example Output:
            // Enter the Number of Layers: 2
            // Enter the Number of Rows: 2
            // Enter the Number of Columns: 2
            // Enter the Array Elements:
            // Layer 1:
            // 1 2
            // 3 4
            // Layer 2:
            // 5 6
            // 7 8
            //
            // Three-Dimensional Array:
            // Layer 1:
            // 1 2
            // 3 4
            //
            // Layer 2:
            // 5 6
            // 7 8

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
