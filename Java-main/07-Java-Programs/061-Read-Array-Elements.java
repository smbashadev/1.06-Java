/*
 * ============================================================================
 * Program Name : Read Array Elements
 * File Name    : 061-Read-Array-Elements.java
 * Class Name   : ReadArrayElements
 *
 * Description:
 * This program accepts the size of an array and reads the array
 * elements from the user using the Scanner class.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to read elements into a one-dimensional array.
 * - Store user input in an array using a loop.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ReadArrayElements {

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

            // Read and store each array element.
            for (int i = 0; i < size; i++) {

                // Read the current element from the user.
                array[i] = scanner.nextInt();

            }

            // Display a success message.
            System.out.println("Array elements have been read successfully.");

            // Example Output:
            // Enter the Size of the Array: 5
            // Enter 5 Array Elements:
            // 10
            // 20
            // 30
            // 40
            // 50
            // Array elements have been read successfully.

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
