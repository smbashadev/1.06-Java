/*
 * ============================================================================
 * Program Name : Sum of Digits
 * File Name    : 046-Sum-of-Digits.java
 * Class Name   : SumOfDigits
 *
 * Description:
 * This program accepts an integer from the user and calculates
 * the sum of all digits present in the given number.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the while loop.
 * - Extract each digit and calculate the sum.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class SumOfDigits {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a number.
        System.out.print("Enter a Number: ");

        // Read the number entered by the user.
        int number = scanner.nextInt();

        // Convert the number into a positive value.
        number = Math.abs(number);

        // Declare and initialize a variable to store the sum of digits.
        int sum = 0;

        // Extract and add each digit until the number becomes zero.
        while (number != 0) {

            // Extract the last digit.
            int digit = number % 10;

            // Add the extracted digit to the sum.
            sum = sum + digit;

            // Remove the last digit from the number.
            number = number / 10;

        }

        // Display the sum of digits.
        System.out.println("Sum of Digits: " + sum);
        // Example Output:
        // Enter a Number: 12345
        // Sum of Digits: 15

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
