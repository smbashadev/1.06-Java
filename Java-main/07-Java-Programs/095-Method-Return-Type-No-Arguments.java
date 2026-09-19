/*
 * ============================================================================
 * Program Name : Method with Return Type and No Arguments
 * File Name    : 095-Method-Return-Type-No-Arguments.java
 * Class Name   : MethodReturnTypeNoArguments
 *
 * Description:
 * This program demonstrates a user-defined method that has
 * a return type but does not accept any arguments. The method
 * calculates the sum of two numbers and returns the result.
 *
 * Objective:
 * - Understand methods with a return type.
 * - Understand methods with no arguments.
 * - Learn how to return a value from a user-defined method.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class MethodReturnTypeNoArguments {

    // User-defined method with a return type and no arguments.
    public int calculateSum() {

        // Declare the first number.
        int number1 = 40;

        // Declare the second number.
        int number2 = 60;

        // Calculate the sum of the two numbers.
        int sum = number1 + number2;

        // Return the calculated sum.
        return sum;

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        MethodReturnTypeNoArguments object = new MethodReturnTypeNoArguments();

        // Call the user-defined method and store the returned value.
        int result = object.calculateSum();

        // Display the returned result.
        System.out.println("Sum: " + result);

        // Example Output:
        // Sum: 100
    }
}
