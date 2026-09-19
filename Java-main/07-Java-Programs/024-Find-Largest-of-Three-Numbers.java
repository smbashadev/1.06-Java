/*
 * ============================================================================
 * Program Name : Find Largest of Three Numbers
 * File Name    : 024-Find-Largest-of-Three-Numbers.java
 * Class Name   : FindLargestOfThreeNumbers
 *
 * Description:
 * This program demonstrates how to find the largest of three numbers
 * entered by the user using the if-else-if ladder.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to compare three numbers.
 * - Identify the largest number using conditional statements.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class FindLargestOfThreeNumbers {

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

        // Ask the user to enter the third number.
        System.out.print("Enter the Third Number: ");

        // Read the third number entered by the user.
        int thirdNumber = scanner.nextInt();

        // Check whether the first number is greater than or equal to the other two numbers.
        if (firstNumber >= secondNumber && firstNumber >= thirdNumber) {

            // Display the first number as the largest number.
            System.out.println("Largest Number: " + firstNumber);
            // Example Output: Largest Number: 95

        }

        // Check whether the second number is greater than or equal to the other two numbers.
        else if (secondNumber >= firstNumber && secondNumber >= thirdNumber) {

            // Display the second number as the largest number.
            System.out.println("Largest Number: " + secondNumber);
            // Example Output: Largest Number: 120

        }

        // Execute this block if the third number is the largest.
        else {

            // Display the third number as the largest number.
            System.out.println("Largest Number: " + thirdNumber);
            // Example Output: Largest Number: 150

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
