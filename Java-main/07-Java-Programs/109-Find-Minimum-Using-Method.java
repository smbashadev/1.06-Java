/*
 * ============================================================================
 * Program Name : Find Minimum Using Method
 * File Name    : 109-Find-Minimum-Using-Method.java
 * Class Name   : FindMinimumUsingMethod
 *
 * Description:
 * This program demonstrates how to find the minimum of two numbers
 * using a user-defined method. The method compares the numbers and
 * returns the smaller value.
 *
 * Objective:
 * - Understand methods with return types.
 * - Learn how to compare two numbers using a method.
 * - Learn how to return the minimum value.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class FindMinimumUsingMethod {

    // User-defined method to find the minimum of two numbers.
    public int findMinimum(int number1, int number2) {

        // Check whether the first number is smaller than the second number.
        if (number1 < number2) {

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
        FindMinimumUsingMethod object = new FindMinimumUsingMethod();

        // Declare and initialize two numbers.
        int number1 = 45;
        int number2 = 75;

        // Call the user-defined method and store the returned value.
        int minimum = object.findMinimum(number1, number2);

        // Display the first number.
        System.out.println("First Number  : " + number1);

        // Display the second number.
        System.out.println("Second Number : " + number2);

        // Display the minimum number.
        System.out.println("Minimum Number: " + minimum);

        // Example Output:
        // First Number  : 45
        // Second Number : 75
        // Minimum Number: 45
    }
}
