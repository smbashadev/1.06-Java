/*
 * ============================================================================
 * Program Name : Method with Return Type and Arguments
 * File Name    : 096-Method-Return-Type-Arguments.java
 * Class Name   : MethodReturnTypeArguments
 *
 * Description:
 * This program demonstrates a user-defined method that has
 * a return type and accepts arguments. The method receives
 * two numbers, calculates their sum, and returns the result.
 *
 * Objective:
 * - Understand methods with a return type.
 * - Understand methods that accept arguments.
 * - Learn how to return a value from a user-defined method.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class MethodReturnTypeArguments {

    // User-defined method with a return type and arguments.
    public int calculateSum(int number1, int number2) {

        // Calculate the sum of the two numbers.
        int sum = number1 + number2;

        // Return the calculated sum.
        return sum;

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        MethodReturnTypeArguments object = new MethodReturnTypeArguments();

        // Call the user-defined method by passing arguments and store the returned value.
        int result = object.calculateSum(45, 55);

        // Display the first number.
        System.out.println("First Number  : 45");

        // Display the second number.
        System.out.println("Second Number : 55");

        // Display the returned result.
        System.out.println("Sum           : " + result);

        // Example Output:
        // First Number  : 45
        // Second Number : 55
        // Sum           : 100
    }
}
