/*
 * ============================================================================
 * Program Name : Relational Operators
 * File Name    : 007-Relational-Operators.java
 * Class Name   : RelationalOperators
 *
 * Description:
 * This program demonstrates the use of relational operators in Java.
 * Relational operators are used to compare two values and always
 * return a boolean result (true or false).
 *
 * Relational Operators:
 * ==  Equal To
 * !=  Not Equal To
 * >   Greater Than
 * <   Less Than
 * >=  Greater Than or Equal To
 * <=  Less Than or Equal To
 *
 * Objective:
 * - Understand relational operators in Java.
 * - Compare two values using different relational operators.
 * - Display the boolean result of each comparison.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class RelationalOperators {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize the first integer variable.
        int number1 = 20;

        // Declare and initialize the second integer variable.
        int number2 = 15;

        // Print the values of both variables.
        System.out.println("First Number  : " + number1); // Output: First Number  : 20
        System.out.println("Second Number : " + number2); // Output: Second Number : 15

        // Compare whether both numbers are equal.
        System.out.println("number1 == number2 : " + (number1 == number2)); // Output: false

        // Compare whether both numbers are not equal.
        System.out.println("number1 != number2 : " + (number1 != number2)); // Output: true

        // Compare whether the first number is greater than the second.
        System.out.println("number1 > number2  : " + (number1 > number2)); // Output: true

        // Compare whether the first number is less than the second.
        System.out.println("number1 < number2  : " + (number1 < number2)); // Output: false

        // Compare whether the first number is greater than or equal to the second.
        System.out.println("number1 >= number2 : " + (number1 >= number2)); // Output: true

        // Compare whether the first number is less than or equal to the second.
        System.out.println("number1 <= number2 : " + (number1 <= number2)); // Output: false
    }
}
