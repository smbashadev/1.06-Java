/*
 * ============================================================================
 * Program Name : Largest Element in Array
 * File Name    : 065-Largest-Element-in-Array.java
 * Class Name   : LargestElementInArray
 *
 * Description:
 * This program accepts the size and elements of a one-dimensional array
 * from the user and finds the largest element in the array.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to traverse a one-dimensional array.
 * - Find the largest element using a loop.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class LargestElementInArray {

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

            // Assume the first element is the largest.
            int largest = array[0];

            // Traverse the remaining elements of the array.
            for (int i = 1; i < size; i++) {

                // Compare the current element with the largest element.
                if (array[i] > largest) {

                    // Update the largest element.
                    largest = array[i];

                }

            }

            // Display the array elements.
            System.out.println("Array Elements:");

            // Traverse the array and print each element.
            for (int i = 0; i < size; i++) {

                // Print the current array element.
                System.out.print(array[i] + " ");

            }

            // Move the cursor to the next line.
            System.out.println();

            // Display the largest element.
            System.out.println("Largest Element: " + largest);

            // Example Output:
            // Enter the Size of the Array: 5
            // Enter 5 Array Elements:
            // 15
            // 45
            // 20
            // 80
            // 35
            // Array Elements:
            // 15 45 20 80 35
            // Largest Element: 80

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
