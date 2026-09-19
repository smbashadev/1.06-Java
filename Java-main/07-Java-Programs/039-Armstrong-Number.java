/*
 * ============================================================================
 * Program Name : Armstrong Number
 * File Name    : 039-Armstrong-Number.java
 * Class Name   : ArmstrongNumber
 *
 * Description:
 * This program accepts an integer from the user and checks whether
 * the given number is an Armstrong number or not using the while loop.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the while loop.
 * - Calculate the sum of the cubes of the digits of a number.
 * - Compare the calculated sum with the original number.
 *
 * Note:
 * This program checks only 3-digit Armstrong numbers.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ArmstrongNumber {

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

        // Declare and initialize a variable to store the sum.
        int sum = 0;

        // Calculate the sum of the cubes of each digit.
        while (number != 0) {

            // Extract the last digit.
            int digit = number % 10;

            // Add the cube of the digit to the sum.
            sum = sum + (digit * digit * digit);

            // Remove the last digit from the number.
            number = number / 10;

        }

        // Check whether the original number is equal to the calculated sum.
        if (originalNumber == sum) {

            // Display that the number is an Armstrong number.
            System.out.println(originalNumber + " is an Armstrong Number.");
            // Example Output:
            // 153 is an Armstrong Number.

        } else {

            // Display that the number is not an Armstrong number.
            System.out.println(originalNumber + " is Not an Armstrong Number.");
            // Example Output:
            // 123 is Not an Armstrong Number.

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
