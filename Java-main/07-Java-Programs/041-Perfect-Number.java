/*
 * ============================================================================
 * Program Name : Perfect Number
 * File Name    : 041-Perfect-Number.java
 * Class Name   : PerfectNumber
 *
 * Description:
 * This program accepts an integer from the user and checks whether
 * the given number is a Perfect Number or not using the for loop.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to find the factors of a number.
 * - Calculate the sum of proper factors.
 * - Compare the sum of factors with the original number.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class PerfectNumber {

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

        // Declare and initialize a variable to store the sum of factors.
        int sum = 0;

        // Check whether the entered number is valid.
        if (number <= 0) {

            // Display an error message.
            System.out.println("Please enter a positive integer.");
            // Example Output:
            // Please enter a positive integer.

        } else {

            // Find all proper factors of the given number.
            for (int i = 1; i < number; i++) {

                // Check whether the current number is a factor.
                if (number % i == 0) {

                    // Add the factor to the sum.
                    sum = sum + i;

                }

            }

            // Check whether the sum of factors is equal to the original number.
            if (sum == originalNumber) {

                // Display that the number is a Perfect Number.
                System.out.println(originalNumber + " is a Perfect Number.");
                // Example Output:
                // 28 is a Perfect Number.

            } else {

                // Display that the number is not a Perfect Number.
                System.out.println(originalNumber + " is Not a Perfect Number.");
                // Example Output:
                // 25 is Not a Perfect Number.

            }

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
