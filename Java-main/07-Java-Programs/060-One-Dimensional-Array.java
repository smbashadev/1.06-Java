/*
 * ============================================================================
 * Program Name : One-Dimensional Array
 * File Name    : 060-One-Dimensional-Array.java
 * Class Name   : OneDimensionalArray
 *
 * Description:
 * This program accepts the size and elements of a one-dimensional array
 * from the user and displays all the array elements.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to declare and initialize a one-dimensional array.
 * - Learn how to access and display array elements using loops.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class OneDimensionalArray {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the size of the array.
        System.out.print("Enter the Size of the Array: ");

        // Read the size entered by the user.
        int size = scanner.nextInt();

        // Check whether the entered size is valid.
        if (size <= 0) {

            // Display an error message.
            System.out.println("Please enter a positive array size.");

        } else {

            // Declare a one-dimensional integer array.
            int[] array = new int[size];

            // Ask the user to enter the array elements.
            System.out.println("Enter " + size + " Array Elements:");

            // Read the array elements from the user.
            for (int i = 0; i < size; i++) {

                // Store each element in the array.
                array[i] = scanner.nextInt();

            }

            // Display the array elements.
            System.out.println("Array Elements:");

            // Traverse the array using a for loop.
            for (int i = 0; i < size; i++) {

                // Print each array element.
                System.out.print(array[i] + " ");

            }

            // Move the cursor to the next line.
            System.out.println();

            // Example Output:
            // Enter the Size of the Array: 5
            // Enter 5 Array Elements:
            // 10
            // 20
            // 30
            // 40
            // 50
            // Array Elements:
            // 10 20 30 40 50

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
