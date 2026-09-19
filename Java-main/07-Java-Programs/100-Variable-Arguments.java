/*
 * ============================================================================
 * Program Name : Variable Arguments
 * File Name    : 100-Variable-Arguments.java
 * Class Name   : VariableArguments
 *
 * Description:
 * This program demonstrates the usage of variable arguments (varargs)
 * in Java. A varargs method can accept zero or more arguments of the
 * same data type.
 *
 * Objective:
 * - Understand the concept of variable arguments (varargs).
 * - Learn how to declare a varargs method.
 * - Learn how to pass multiple values to a single method.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class VariableArguments {

    // User-defined method that accepts variable number of integer arguments.
    public void calculateSum(int... numbers) {

        // Declare a variable to store the sum.
        int sum = 0;

        // Traverse all the numbers using an enhanced for loop.
        for (int number : numbers) {

            // Add the current number to the sum.
            sum = sum + number;

        }

        // Display all the numbers.
        System.out.print("Numbers: ");

        // Traverse the numbers to display them.
        for (int number : numbers) {

            // Display the current number.
            System.out.print(number + " ");

        }

        // Move the cursor to the next line.
        System.out.println();

        // Display the calculated sum.
        System.out.println("Sum: " + sum);

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        VariableArguments object = new VariableArguments();

        // Call the varargs method by passing multiple values.
        object.calculateSum(10, 20, 30, 40, 50);

        // Example Output:
        // Numbers: 10 20 30 40 50
        // Sum: 150
    }
}
