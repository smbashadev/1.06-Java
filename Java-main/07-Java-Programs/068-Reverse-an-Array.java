/*
 * ============================================================================
 * Program Name : Reverse an Array
 * File Name    : 068-Reverse-an-Array.java
 * Class Name   : ReverseAnArray
 *
 * Description:
 * This program accepts the size and elements of a one-dimensional array
 * from the user and prints the array elements in reverse order.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to traverse an array in reverse order.
 * - Display the array elements from last to first.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ReverseAnArray {

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

            // Read all array elements.
            for (int i = 0; i < size; i++) {

                // Store the current element in the array.
                array[i] = scanner.nextInt();

            }

            // Display the original array.
            System.out.println("Original Array:");

            // Traverse and print the original array.
            for (int i = 0; i < size; i++) {

                // Print the current array element.
                System.out.print(array[i] + " ");

            }

            // Move the cursor to the next line.
            System.out.println();

            // Display the reversed array.
            System.out.println("Reversed Array:");

            // Traverse the array from last index to first index.
            for (int i = size - 1; i >= 0; i--) {

                // Print the current array element.
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
            // Original Array:
            // 10 20 30 40 50
            // Reversed Array:
            // 50 40 30 20 10

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
