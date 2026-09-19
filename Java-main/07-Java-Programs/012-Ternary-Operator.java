/*
 * ============================================================================
 * Program Name : Ternary Operator
 * File Name    : 012-Ternary-Operator.java
 * Class Name   : TernaryOperator
 *
 * Description:
 * This program demonstrates the use of the ternary operator in Java.
 * The ternary operator is a shorthand alternative to the if-else statement.
 * It evaluates a condition and returns one of two values depending on
 * whether the condition is true or false.
 *
 * Syntax:
 * condition ? expression1 : expression2;
 *
 * Objective:
 * - Understand the ternary operator in Java.
 * - Learn how to replace simple if-else statements using the ternary operator.
 * - Display the result based on a condition.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class TernaryOperator {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize an integer variable.
        int number = 25;

        // Use the ternary operator to determine whether the number is even or odd.
        String result = (number % 2 == 0) ? "Even Number" : "Odd Number";

        // Print the given number.
        System.out.println("Number : " + number); // Output: Number : 25

        // Print the result returned by the ternary operator.
        System.out.println("Result : " + result); // Output: Result : Odd Number

        // Declare and initialize two integer variables.
        int firstNumber = 40;
        int secondNumber = 60;

        // Use the ternary operator to find the greater number.
        int greaterNumber = (firstNumber > secondNumber) ? firstNumber : secondNumber;

        // Print both numbers.
        System.out.println("First Number  : " + firstNumber);  // Output: First Number  : 40
        System.out.println("Second Number : " + secondNumber); // Output: Second Number : 60

        // Print the greater number.
        System.out.println("Greater Number: " + greaterNumber); // Output: Greater Number: 60
    }
}
