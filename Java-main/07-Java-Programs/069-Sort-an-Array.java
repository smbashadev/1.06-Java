/*
 * ============================================================================
 * Program Name : Sort an Array
 * File Name    : 069-Sort-an-Array.java
 * Class Name   : SortAnArray
 *
 * Description:
 * This program accepts the size and elements of a one-dimensional array
 * from the user and sorts the array elements in ascending order.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to traverse a one-dimensional array.
 * - Sort array elements in ascending order using Bubble Sort.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class SortAnArray {

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

            // Sort the array using Bubble Sort.
            for (int i = 0; i < size - 1; i++) {

                // Compare adjacent elements.
                for (int j = 0; j < size - 1 - i; j++) {

                    // Check whether the current element is greater than the next element.
                    if (array[j] > array[j + 1]) {

                        // Swap the two elements.
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;

                    }

                }

            }

            // Display the sorted array.
            System.out.println("Sorted Array (Ascending Order):");

            // Traverse and print the sorted array.
            for (int i = 0; i < size; i++) {

                // Print the current array element.
                System.out.print(array[i] + " ");

            }

            // Move the cursor to the next line.
            System.out.println();

            // Example Output:
            // Enter the Size of the Array: 5
            // Enter 5 Array Elements:
            // 50
            // 20
            // 40
            // 10
            // 30
            // Original Array:
            // 50 20 40 10 30
            // Sorted Array (Ascending Order):
            // 10 20 30 40 50

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
