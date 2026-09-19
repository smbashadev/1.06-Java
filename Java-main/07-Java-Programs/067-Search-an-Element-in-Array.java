/*
 * ============================================================================
 * Program Name : Search an Element in Array
 * File Name    : 067-Search-an-Element-in-Array.java
 * Class Name   : SearchAnElementInArray
 *
 * Description:
 * This program accepts the size and elements of a one-dimensional array
 * from the user and searches for a specified element using Linear Search.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to traverse a one-dimensional array.
 * - Search for an element using Linear Search.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class SearchAnElementInArray {

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

            // Ask the user to enter the element to search.
            System.out.print("Enter the Element to Search: ");

            // Read the search element.
            int searchElement = scanner.nextInt();

            // Declare a flag variable to track whether the element is found.
            boolean found = false;

            // Traverse the array to search for the element.
            for (int i = 0; i < size; i++) {

                // Compare the current element with the search element.
                if (array[i] == searchElement) {

                    // Display the position where the element is found.
                    System.out.println("Element Found at Index: " + i);

                    // Update the flag variable.
                    found = true;

                    // Exit the loop since the element is found.
                    break;

                }

            }

            // Check whether the element was found.
            if (!found) {

                // Display a message if the element is not found.
                System.out.println("Element Not Found.");

            }

            // Example Output:
            // Enter the Size of the Array: 5
            // Enter 5 Array Elements:
            // 10
            // 20
            // 30
            // 40
            // 50
            // Enter the Element to Search: 30
            // Element Found at Index: 2

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
