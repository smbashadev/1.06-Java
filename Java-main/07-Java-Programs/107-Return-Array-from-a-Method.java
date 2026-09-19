/*
 * ============================================================================
 * Program Name : Return Array from a Method
 * File Name    : 107-Return-Array-from-a-Method.java
 * Class Name   : ReturnArrayFromAMethod
 *
 * Description:
 * This program demonstrates how to return an array from a
 * user-defined method. The returned array is received in the
 * main() method and its elements are displayed.
 *
 * Objective:
 * - Understand how to return an array from a method.
 * - Learn how to receive the returned array.
 * - Learn how to traverse and display array elements.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class ReturnArrayFromAMethod {

    // User-defined method to create and return an integer array.
    public int[] getArray() {

        // Declare and initialize an integer array.
        int[] numbers = {10, 20, 30, 40, 50};

        // Return the array.
        return numbers;

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        ReturnArrayFromAMethod object = new ReturnArrayFromAMethod();

        // Receive the array returned by the method.
        int[] numbers = object.getArray();

        // Display the heading.
        System.out.println("Array Elements:");

        // Traverse the returned array using a for loop.
        for (int i = 0; i < numbers.length; i++) {

            // Display the current array element.
            System.out.println("Element " + i + " : " + numbers[i]);

        }

        // Example Output:
        // Array Elements:
        // Element 0 : 10
        // Element 1 : 20
        // Element 2 : 30
        // Element 3 : 40
        // Element 4 : 50
    }
}
