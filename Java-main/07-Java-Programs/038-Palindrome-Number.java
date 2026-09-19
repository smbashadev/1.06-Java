/*
 * ============================================================================
 * Program Name : Palindrome Number
 * File Name    : 038-Palindrome-Number.java
 * Class Name   : PalindromeNumber
 *
 * Description:
 * This program accepts an integer from the user and checks whether
 * the given number is a palindrome or not using the while loop.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the while loop.
 * - Reverse a number and compare it with the original number.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class PalindromeNumber {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a number.
        System.out.print("Enter a Number: ");

        // Read the number entered by the user.
        int number = scanner.nextInt();

        // Store the original number for comparison.
        int originalNumber = number;

        // Declare and initialize a variable to store the reversed number.
        int reverse = 0;

        // Reverse the given number using the while loop.
        while (number != 0) {

            // Extract the last digit.
            int digit = number % 10;

            // Append the extracted digit to the reversed number.
            reverse = reverse * 10 + digit;

            // Remove the last digit from the number.
            number = number / 10;

        }

        // Check whether the original number and reversed number are equal.
        if (originalNumber == reverse) {

            // Display that the number is a palindrome.
            System.out.println(originalNumber + " is a Palindrome Number.");
            // Example Output:
            // 121 is a Palindrome Number.

        } else {

            // Display that the number is not a palindrome.
            System.out.println(originalNumber + " is Not a Palindrome Number.");
            // Example Output:
            // 123 is Not a Palindrome Number.

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
