/*
 * ============================================================================
 * Program Name : Prime Number
 * File Name    : 042-Prime-Number.java
 * Class Name   : PrimeNumber
 *
 * Description:
 * This program accepts an integer from the user and checks whether
 * the given number is a Prime Number or not using the for loop.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to find the factors of a number.
 * - Determine whether a number is prime.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class PrimeNumber {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a number.
        System.out.print("Enter a Number: ");

        // Read the number entered by the user.
        int number = scanner.nextInt();

        // Declare and initialize a variable to count the factors.
        int factorCount = 0;

        // Check whether the entered number is valid.
        if (number <= 1) {

            // Display that the number is not prime.
            System.out.println(number + " is Not a Prime Number.");
            // Example Output:
            // 1 is Not a Prime Number.

        } else {

            // Find the factors of the given number.
            for (int i = 1; i <= number; i++) {

                // Check whether the current number is a factor.
                if (number % i == 0) {

                    // Increase the factor count.
                    factorCount++;

                }

            }

            // Check whether the number has exactly two factors.
            if (factorCount == 2) {

                // Display that the number is a Prime Number.
                System.out.println(number + " is a Prime Number.");
                // Example Output:
                // 13 is a Prime Number.

            } else {

                // Display that the number is not a Prime Number.
                System.out.println(number + " is Not a Prime Number.");
                // Example Output:
                // 12 is Not a Prime Number.

            }

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
