/*
 * ============================================================================
 * Program Name : Binary to Decimal
 * File Name    : 049-Binary-to-Decimal.java
 * Class Name   : BinaryToDecimal
 *
 * Description:
 * This program accepts a binary number from the user and converts
 * it into its decimal equivalent.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to convert binary numbers to decimal.
 * - Practice using the while loop and arithmetic operators.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class BinaryToDecimal {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a binary number.
        System.out.print("Enter a Binary Number: ");

        // Read the binary number entered by the user.
        long binaryNumber = scanner.nextLong();

        // Store the original binary number.
        long originalBinary = binaryNumber;

        // Declare and initialize a variable to store the decimal number.
        int decimalNumber = 0;

        // Declare and initialize the power of 2.
        int power = 1;

        // Declare a variable to validate the binary number.
        boolean isValidBinary = true;

        // Process each digit of the binary number.
        while (binaryNumber != 0) {

            // Extract the last digit.
            int digit = (int) (binaryNumber % 10);

            // Check whether the digit is valid.
            if (digit != 0 && digit != 1) {

                // Mark the binary number as invalid.
                isValidBinary = false;

                // Exit the loop.
                break;

            }

            // Calculate the decimal value.
            decimalNumber = decimalNumber + (digit * power);

            // Update the power of 2.
            power = power * 2;

            // Remove the last digit from the binary number.
            binaryNumber = binaryNumber / 10;

        }

        // Check whether the entered binary number is valid.
        if (isValidBinary) {

            // Display the original binary number.
            System.out.println("Binary Number : " + originalBinary);

            // Display the decimal equivalent.
            System.out.println("Decimal Number: " + decimalNumber);

            // Example Output:
            // Enter a Binary Number: 11001
            // Binary Number : 11001
            // Decimal Number: 25

        } else {

            // Display an error message.
            System.out.println("Invalid Binary Number.");
            // Example Output:
            // Invalid Binary Number.

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
