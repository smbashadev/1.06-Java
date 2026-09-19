/*
 * ============================================================================
 * Program Name : Simple Calculator Using Switch
 * File Name    : 027-Simple-Calculator-Using-Switch.java
 * Class Name   : SimpleCalculatorUsingSwitch
 *
 * Description:
 * This program demonstrates how to perform basic arithmetic operations
 * using the switch statement in Java.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the switch statement.
 * - Perform addition, subtraction, multiplication, division, and modulus.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class SimpleCalculatorUsingSwitch {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the first number.
        System.out.print("Enter the First Number: ");

        // Read the first number entered by the user.
        double firstNumber = scanner.nextDouble();

        // Ask the user to enter the second number.
        System.out.print("Enter the Second Number: ");

        // Read the second number entered by the user.
        double secondNumber = scanner.nextDouble();

        // Display the list of available operations.
        System.out.println("\nChoose an Operation:");
        System.out.println("+ : Addition");
        System.out.println("- : Subtraction");
        System.out.println("* : Multiplication");
        System.out.println("/ : Division");
        System.out.println("% : Modulus");

        // Ask the user to enter the desired operation.
        System.out.print("Enter Your Choice: ");

        // Read the operator entered by the user.
        char operator = scanner.next().charAt(0);

        // Execute the selected operation.
        switch (operator) {

            // Perform addition.
            case '+':

                // Display the addition result.
                System.out.println("Result: " + (firstNumber + secondNumber));
                // Example Output: Result: 30.0
                break;

            // Perform subtraction.
            case '-':

                // Display the subtraction result.
                System.out.println("Result: " + (firstNumber - secondNumber));
                // Example Output: Result: 10.0
                break;

            // Perform multiplication.
            case '*':

                // Display the multiplication result.
                System.out.println("Result: " + (firstNumber * secondNumber));
                // Example Output: Result: 200.0
                break;

            // Perform division.
            case '/':

                // Check whether the second number is zero.
                if (secondNumber == 0) {

                    // Display an error message.
                    System.out.println("Error! Division by zero is not allowed.");
                    // Example Output: Error! Division by zero is not allowed.

                } else {

                    // Display the division result.
                    System.out.println("Result: " + (firstNumber / secondNumber));
                    // Example Output: Result: 5.0

                }
                break;

            // Perform modulus operation.
            case '%':

                // Check whether the second number is zero.
                if (secondNumber == 0) {

                    // Display an error message.
                    System.out.println("Error! Modulus by zero is not allowed.");
                    // Example Output: Error! Modulus by zero is not allowed.

                } else {

                    // Display the modulus result.
                    System.out.println("Result: " + (firstNumber % secondNumber));
                    // Example Output: Result: 2.0

                }
                break;

            // Execute this block for an invalid operator.
            default:

                // Display an error message.
                System.out.println("Invalid Operator! Please choose +, -, *, /, or %.");
                // Example Output: Invalid Operator! Please choose +, -, *, /, or %.
        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
