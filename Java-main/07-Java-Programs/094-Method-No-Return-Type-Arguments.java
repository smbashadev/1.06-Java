/*
 * ============================================================================
 * Program Name : Method with No Return Type and Arguments
 * File Name    : 094-Method-No-Return-Type-Arguments.java
 * Class Name   : MethodNoReturnTypeArguments
 *
 * Description:
 * This program demonstrates a user-defined method that has
 * no return type but accepts arguments. The method receives
 * two numbers and displays their sum.
 *
 * Objective:
 * - Understand methods with no return type.
 * - Understand methods that accept arguments.
 * - Learn how to pass values to a user-defined method.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class MethodNoReturnTypeArguments {

    // User-defined method with no return type and arguments.
    public void calculateSum(int number1, int number2) {

        // Calculate the sum of the two numbers.
        int sum = number1 + number2;

        // Display the first number.
        System.out.println("First Number  : " + number1);

        // Display the second number.
        System.out.println("Second Number : " + number2);

        // Display the sum.
        System.out.println("Sum           : " + sum);

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        MethodNoReturnTypeArguments object = new MethodNoReturnTypeArguments();

        // Call the user-defined method by passing arguments.
        object.calculateSum(25, 35);

        // Example Output:
        // First Number  : 25
        // Second Number : 35
        // Sum           : 60
    }
}
