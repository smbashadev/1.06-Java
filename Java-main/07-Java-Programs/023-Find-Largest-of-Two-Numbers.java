/*
 * ============================================================================
 * Program Name : Find Largest of Two Numbers
 * File Name    : 023-Find-Largest-of-Two-Numbers.java
 * Class Name   : FindLargestOfTwoNumbers
 *
 * Description:
 * This program demonstrates how to find the largest of two numbers
 * entered by the user using the if-else statement.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to compare two numbers.
 * - Identify the largest number using conditional statements.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class FindLargestOfTwoNumbers {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the first number.
        System.out.print("Enter the First Number: ");

        // Read the first number entered by the user.
        int firstNumber = scanner.nextInt();

        // Ask the user to enter the second number.
        System.out.print("Enter the Second Number: ");

        // Read the second number entered by the user.
        int secondNumber = scanner.nextInt();

        // Check whether the first number is greater than the second number.
        if (firstNumber > secondNumber) {

            // Display the first number as the largest number.
            System.out.println("Largest Number: " + firstNumber);
            // Example Output: Largest Number: 50

        }

        // Check whether the second number is greater than the first number.
        else if (secondNumber > firstNumber) {

            // Display the second number as the largest number.
            System.out.println("Largest Number: " + secondNumber);
            // Example Output: Largest Number: 75

        }

        // Execute this block if both numbers are equal.
        else {

            // Display that both numbers are equal.
            System.out.println("Both numbers are Equal.");
            // Example Output: Both numbers are Equal.

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
