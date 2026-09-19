/*
 * ============================================================================
 * Program Name : Check Even or Odd
 * File Name    : 022-Check-Even-or-Odd.java
 * Class Name   : CheckEvenOrOdd
 *
 * Description:
 * This program demonstrates how to determine whether a given number is
 * even or odd by accepting input from the user using the Scanner class.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the if-else statement.
 * - Identify whether a number is even or odd.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class CheckEvenOrOdd {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter an integer.
        System.out.print("Enter an Integer: ");

        // Read the integer entered by the user.
        int number = scanner.nextInt();

        // Check whether the number is even.
        if (number % 2 == 0) {

            // Display the result if the number is even.
            System.out.println(number + " is an Even Number.");
            // Example Output: 24 is an Even Number.

        }

        // Execute this block if the number is odd.
        else {

            // Display the result if the number is odd.
            System.out.println(number + " is an Odd Number.");
            // Example Output: 15 is an Odd Number.

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
