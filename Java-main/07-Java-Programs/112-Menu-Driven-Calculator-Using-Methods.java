/*
 * ============================================================================
 * Program Name : Menu-Driven Calculator Using Methods
 * File Name    : 112-Menu-Driven-Calculator-Using-Methods.java
 * Class Name   : MenuDrivenCalculatorUsingMethods
 *
 * Description:
 * This program demonstrates a menu-driven calculator using
 * user-defined methods. The user can perform basic arithmetic
 * operations such as addition, subtraction, multiplication,
 * and division by selecting an option from the menu.
 *
 * Objective:
 * - Understand menu-driven programming.
 * - Learn how to use multiple user-defined methods.
 * - Understand method calling and return values.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class MenuDrivenCalculatorUsingMethods {

    // User-defined method to perform addition.
    public int add(int number1, int number2) {

        // Return the sum.
        return number1 + number2;

    }

    // User-defined method to perform subtraction.
    public int subtract(int number1, int number2) {

        // Return the difference.
        return number1 - number2;

    }

    // User-defined method to perform multiplication.
    public int multiply(int number1, int number2) {

        // Return the product.
        return number1 * number2;

    }

    // User-defined method to perform division.
    public double divide(int number1, int number2) {

        // Return the division result.
        return (double) number1 / number2;

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read user input.
        Scanner scanner = new Scanner(System.in);

        // Create an object of the current class.
        MenuDrivenCalculatorUsingMethods object =
                new MenuDrivenCalculatorUsingMethods();

        // Display the calculator menu.
        System.out.println("===== Calculator Menu =====");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");

        // Ask the user to choose an operation.
        System.out.print("Enter Your Choice: ");
        int choice = scanner.nextInt();

        // Read the first number.
        System.out.print("Enter First Number: ");
        int number1 = scanner.nextInt();

        // Read the second number.
        System.out.print("Enter Second Number: ");
        int number2 = scanner.nextInt();

        // Perform the selected operation.
        switch (choice) {

            case 1:

                // Display the addition result.
                System.out.println("Result: "
                        + object.add(number1, number2));
                break;

            case 2:

                // Display the subtraction result.
                System.out.println("Result: "
                        + object.subtract(number1, number2));
                break;

            case 3:

                // Display the multiplication result.
                System.out.println("Result: "
                        + object.multiply(number1, number2));
                break;

            case 4:

                // Check whether the divisor is zero.
                if (number2 != 0) {

                    // Display the division result.
                    System.out.println("Result: "
                            + object.divide(number1, number2));

                } else {

                    // Display an error message.
                    System.out.println("Division by zero is not allowed.");

                }

                break;

            default:

                // Display an invalid choice message.
                System.out.println("Invalid Choice.");

        }

        // Close the Scanner object.
        scanner.close();

        // Example Output:
        // ===== Calculator Menu =====
        // 1. Addition
        // 2. Subtraction
        // 3. Multiplication
        // 4. Division
        // Enter Your Choice: 1
        // Enter First Number: 20
        // Enter Second Number: 30
        // Result: 50

    }
}
