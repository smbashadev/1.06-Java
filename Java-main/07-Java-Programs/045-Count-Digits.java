/*
 * ============================================================================
 * Program Name : Count Digits
 * File Name    : 045-Count-Digits.java
 * Class Name   : CountDigits
 *
 * Description:
 * This program accepts an integer from the user and counts
 * the total number of digits present in the given number.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the while loop.
 * - Count the number of digits in an integer.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class CountDigits {

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

            // Display the digit count for zero.
            System.out.println("Total Number of Digits: 1");
            // Example Output:
            // Total Number of Digits: 1

        } else {

            // Declare and initialize a variable to count digits.
            int digitCount = 0;

            // Count the digits using the while loop.
            while (number != 0) {

                // Increase the digit count.
                digitCount++;

                // Remove the last digit from the number.
                number = number / 10;

            }

            // Display the total number of digits.
            System.out.println("Total Number of Digits: " + digitCount);
            // Example Output:
            // Enter a Number: 12345
            // Total Number of Digits: 5

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
