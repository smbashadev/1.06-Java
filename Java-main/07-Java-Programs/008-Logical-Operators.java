/*
 * ============================================================================
 * Program Name : Logical Operators
 * File Name    : 008-Logical-Operators.java
 * Class Name   : LogicalOperators
 *
 * Description:
 * This program demonstrates the use of logical operators in Java.
 * Logical operators are used to combine two or more boolean expressions
 * and return a boolean result (true or false).
 *
 * Logical Operators:
 * &&  Logical AND
 * ||  Logical OR
 * !   Logical NOT
 *
 * Objective:
 * - Understand logical operators in Java.
 * - Learn how to combine boolean expressions.
 * - Display the result of logical operations.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class LogicalOperators {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize the first integer variable.
        int number1 = 20;

        // Declare and initialize the second integer variable.
        int number2 = 10;

        // Evaluate the first boolean expression.
        boolean condition1 = number1 > number2;

        // Evaluate the second boolean expression.
        boolean condition2 = number1 < 50;

        // Print the values of both numbers.
        System.out.println("First Number  : " + number1); // Output: First Number  : 20
        System.out.println("Second Number : " + number2); // Output: Second Number : 10

        // Print the value of the first condition.
        System.out.println("Condition 1 (number1 > number2) : " + condition1); // Output: true

        // Print the value of the second condition.
        System.out.println("Condition 2 (number1 < 50)      : " + condition2); // Output: true

        // Demonstrate the Logical AND (&&) operator.
        System.out.println("condition1 && condition2 : " + (condition1 && condition2)); // Output: true

        // Demonstrate the Logical OR (||) operator.
        System.out.println("condition1 || condition2 : " + (condition1 || condition2)); // Output: true

        // Demonstrate the Logical NOT (!) operator.
        System.out.println("!condition1              : " + (!condition1)); // Output: false

        // Demonstrate another Logical AND example.
        System.out.println("(number1 > 30) && (number2 < 20) : "
                + ((number1 > 30) && (number2 < 20))); // Output: false

        // Demonstrate another Logical OR example.
        System.out.println("(number1 > 30) || (number2 < 20) : "
                + ((number1 > 30) || (number2 < 20))); // Output: true
    }
}
