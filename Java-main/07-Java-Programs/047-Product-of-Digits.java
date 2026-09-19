/*
 * ============================================================================
 * Program Name : Product of Digits
 * File Name    : 047-Product-of-Digits.java
 * Class Name   : ProductOfDigits
 *
 * Description:
 * This program accepts an integer from the user and calculates
 * the product of all digits present in the given number.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the while loop.
 * - Extract each digit and calculate the product.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ProductOfDigits {

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

        // Check whether the entered number is zero.
        if (number == 0) {

            // Display the product for zero.
            System.out.println("Product of Digits: 0");
            // Example Output:
            // Product of Digits: 0

        } else {

            // Declare and initialize a variable to store the product of digits.
            int product = 1;

            // Extract and multiply each digit until the number becomes zero.
            while (number != 0) {

                // Extract the last digit.
                int digit = number % 10;

                // Multiply the extracted digit with the product.
                product = product * digit;

                // Remove the last digit from the number.
                number = number / 10;

            }

            // Display the product of digits.
            System.out.println("Product of Digits: " + product);
            // Example Output:
            // Enter a Number: 1234
            // Product of Digits: 24

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
