/*
 * ============================================================================
 * Program Name : Prime Numbers in a Range
 * File Name    : 043-Prime-Numbers-in-a-Range.java
 * Class Name   : PrimeNumbersInARange
 *
 * Description:
 * This program accepts the starting and ending values of a range from
 * the user and displays all Prime Numbers within that range.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use nested for loops.
 * - Check whether each number in a range is prime.
 * - Display all prime numbers within the specified range.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class PrimeNumbersInARange {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the starting value.
        System.out.print("Enter the Starting Number: ");

        // Read the starting value.
        int start = scanner.nextInt();

        // Ask the user to enter the ending value.
        System.out.print("Enter the Ending Number: ");

        // Read the ending value.
        int end = scanner.nextInt();

        // Check whether the entered range is valid.
        if (start > end) {

            // Display an error message.
            System.out.println("Invalid Range! Starting Number should be less than or equal to Ending Number.");

        } else {

            // Display the heading.
            System.out.println("Prime Numbers between " + start + " and " + end + " are:");

            // Traverse through every number in the given range.
            for (int number = start; number <= end; number++) {

                // Skip numbers less than or equal to 1 because they are not prime.
                if (number <= 1) {
                    continue;
                }

                // Declare and initialize the factor counter.
                int factorCount = 0;

                // Find the factors of the current number.
                for (int i = 1; i <= number; i++) {

                    // Check whether the current value is a factor.
                    if (number % i == 0) {

                        // Increase the factor count.
                        factorCount++;

                    }

                }

                // Check whether the current number is prime.
                if (factorCount == 2) {

                    // Display the prime number.
                    System.out.println(number);

                }

            }

            // Example Output:
            // Prime Numbers between 10 and 30 are:
            // 11
            // 13
            // 17
            // 19
            // 23
            // 29

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
