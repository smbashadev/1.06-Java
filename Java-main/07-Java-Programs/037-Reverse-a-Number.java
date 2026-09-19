/*
 * ============================================================================
 * Program Name : Reverse a Number
 * File Name    : 037-Reverse-a-Number.java
 * Class Name   : ReverseANumber
 *
 * Description:
 * This program accepts an integer from the user and reverses its digits
 * using the while loop.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the while loop.
 * - Reverse the digits of a given number.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ReverseANumber {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a number.
        System.out.print("Enter a Number: ");

        // Read the number entered by the user.
        int number = scanner.nextInt();

        // Store the original number for displaying the result later.
        int originalNumber = number;

        // Declare and initialize a variable to store the reversed number.
        int reverse = 0;

        // Reverse the digits using the while loop.
        while (number != 0) {

            // Extract the last digit from the number.
            int digit = number % 10;

            // Append the extracted digit to the reversed number.
            reverse = reverse * 10 + digit;

            // Remove the last digit from the original number.
            number = number / 10;

        }

        // Display the reversed number.
        System.out.println("Reverse of " + originalNumber + " = " + reverse);
        // Example Output:
        // Enter a Number: 12345
        // Reverse of 12345 = 54321

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
