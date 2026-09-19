/*
 * ============================================================================
 * Program Name : Pass Array to a Method
 * File Name    : 106-Pass-Array-to-a-Method.java
 * Class Name   : PassArrayToAMethod
 *
 * Description:
 * This program demonstrates how to pass an array as an argument
 * to a user-defined method. The method receives the array and
 * displays all its elements.
 *
 * Objective:
 * - Understand how to pass an array to a method.
 * - Learn how to access array elements inside a method.
 * - Learn how to traverse an array using a loop.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class PassArrayToAMethod {

    // User-defined method to display array elements.
    public void displayArray(int[] numbers) {

        // Display the heading.
        System.out.println("Array Elements:");

        // Traverse the array using a for loop.
        for (int i = 0; i < numbers.length; i++) {

            // Display the current array element.
            System.out.println("Element " + i + " : " + numbers[i]);

        }

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        PassArrayToAMethod object = new PassArrayToAMethod();

        // Declare and initialize an integer array.
        int[] numbers = {10, 20, 30, 40, 50};

        // Pass the array to the user-defined method.
        object.displayArray(numbers);

        // Example Output:
        // Array Elements:
        // Element 0 : 10
        // Element 1 : 20
        // Element 2 : 30
        // Element 3 : 40
        // Element 4 : 50
    }
}
