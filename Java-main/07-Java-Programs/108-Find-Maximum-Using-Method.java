/*
 * ============================================================================
 * Program Name : Find Maximum Using Method
 * File Name    : 108-Find-Maximum-Using-Method.java
 * Class Name   : FindMaximumUsingMethod
 *
 * Description:
 * This program demonstrates how to find the maximum of two numbers
 * using a user-defined method. The method compares the numbers and
 * returns the larger value.
 *
 * Objective:
 * - Understand methods with return types.
 * - Learn how to compare two numbers using a method.
 * - Learn how to return the maximum value.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class FindMaximumUsingMethod {

    // User-defined method to find the maximum of two numbers.
    public int findMaximum(int number1, int number2) {

        // Check whether the first number is greater than the second number.
        if (number1 > number2) {

            // Return the first number.
            return number1;

        } else {

            // Return the second number.
            return number2;

        }

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        FindMaximumUsingMethod object = new FindMaximumUsingMethod();

        // Declare and initialize two numbers.
        int number1 = 45;
        int number2 = 75;

        // Call the user-defined method and store the returned value.
        int maximum = object.findMaximum(number1, number2);

        // Display the first number.
        System.out.println("First Number  : " + number1);

        // Display the second number.
        System.out.println("Second Number : " + number2);

        // Display the maximum number.
        System.out.println("Maximum Number: " + maximum);

        // Example Output:
        // First Number  : 45
        // Second Number : 75
        // Maximum Number: 75
    }
}
