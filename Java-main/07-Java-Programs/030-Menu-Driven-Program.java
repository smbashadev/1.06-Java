/*
 * ============================================================================
 * Program Name : Menu Driven Program
 * File Name    : 030-Menu-Driven-Program.java
 * Class Name   : MenuDrivenProgram
 *
 * Description:
 * This program demonstrates a simple menu-driven application using the
 * switch statement. The user can perform basic arithmetic operations by
 * selecting an option from the menu.
 *
 * Objective:
 * - Understand menu-driven programming in Java.
 * - Learn how to use the switch statement with user input.
 * - Perform arithmetic operations based on the selected menu option.
 *
 * Menu:
 * 1. Addition
 * 2. Subtraction
 * 3. Multiplication
 * 4. Division
 * 5. Exit
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class MenuDrivenProgram {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Display the application title.
        System.out.println("========== MENU DRIVEN PROGRAM ==========");

        // Display the available menu options.
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        System.out.println("5. Exit");

        // Ask the user to choose an option.
        System.out.print("Enter Your Choice: ");

        // Read the user's menu choice.
        int choice = scanner.nextInt();

        // Check whether the selected option is Exit.
        if (choice == 5) {

            // Display the exit message.
            System.out.println("Thank You! Program Exited Successfully.");
            // Example Output: Thank You! Program Exited Successfully.

            // Close the Scanner object.
            scanner.close();

            // Terminate the program.
            return;
        }

        // Ask the user to enter the first number.
        System.out.print("Enter the First Number: ");

        // Read the first number.
        double firstNumber = scanner.nextDouble();

        // Ask the user to enter the second number.
        System.out.print("Enter the Second Number: ");

        // Read the second number.
        double secondNumber = scanner.nextDouble();

        // Execute the selected operation.
        switch (choice) {

            // Perform addition.
            case 1:

                // Display the addition result.
                System.out.println("Result: " + (firstNumber + secondNumber));
                // Example Output: Result: 30.0
                break;

            // Perform subtraction.
            case 2:

                // Display the subtraction result.
                System.out.println("Result: " + (firstNumber - secondNumber));
                // Example Output: Result: 10.0
                break;

            // Perform multiplication.
            case 3:

                // Display the multiplication result.
                System.out.println("Result: " + (firstNumber * secondNumber));
                // Example Output: Result: 200.0
                break;

            // Perform division.
            case 4:

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

            // Execute this block for an invalid menu option.
            default:

                // Display an invalid choice message.
                System.out.println("Invalid Choice! Please select a valid menu option.");
                // Example Output: Invalid Choice! Please select a valid menu option.
        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
