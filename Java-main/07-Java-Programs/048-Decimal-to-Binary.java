/*
 * ============================================================================
 * Program Name : Decimal to Binary
 * File Name    : 048-Decimal-to-Binary.java
 * Class Name   : DecimalToBinary
 *
 * Description:
 * This program accepts a decimal number from the user and converts
 * it into its binary equivalent.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to convert decimal numbers to binary.
 * - Practice using the while loop and arithmetic operators.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class DecimalToBinary {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a decimal number.
        System.out.print("Enter a Decimal Number: ");

        // Read the decimal number entered by the user.
        int decimalNumber = scanner.nextInt();

        // Check whether the entered number is valid.
        if (decimalNumber < 0) {

            // Display an error message for negative numbers.
            System.out.println("Please enter a non-negative integer.");
            // Example Output:
            // Please enter a non-negative integer.

        } else if (decimalNumber == 0) {

            // Display the binary value of zero.
            System.out.println("Binary Number: 0");
            // Example Output:
            // Binary Number: 0

        } else {

            // Store the original decimal number.
            int originalNumber = decimalNumber;

            // Declare and initialize a variable to store the binary number.
            long binaryNumber = 0;

            // Declare and initialize the place value.
            long placeValue = 1;

            // Convert the decimal number into binary.
            while (decimalNumber != 0) {

                // Find the remainder after dividing by 2.
                int remainder = decimalNumber % 2;

                // Build the binary number.
                binaryNumber = binaryNumber + (remainder * placeValue);

                // Update the place value.
                placeValue = placeValue * 10;

                // Divide the decimal number by 2.
                decimalNumber = decimalNumber / 2;

            }

            // Display the original decimal number.
            System.out.println("Decimal Number: " + originalNumber);

            // Display the binary equivalent.
            System.out.println("Binary Number : " + binaryNumber);

            // Example Output:
            // Enter a Decimal Number: 25
            // Decimal Number: 25
            // Binary Number : 11001

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
