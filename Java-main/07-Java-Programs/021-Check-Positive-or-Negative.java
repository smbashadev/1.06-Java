/*
 * ============================================================================
 * Program Name : Check Positive or Negative
 * File Name    : 021-Check-Positive-or-Negative.java
 * Class Name   : CheckPositiveOrNegative
 *
 * Description:
 * This program demonstrates how to determine whether a given number is
 * positive, negative, or zero using the if-else-if ladder in Java.
 *
 * Objective:
 * - Understand conditional statements in Java.
 * - Learn how to compare numeric values.
 * - Identify whether a number is positive, negative, or zero.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class CheckPositiveOrNegative {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize an integer variable.
        int number = -25;

        // Display the given number.
        System.out.println("Given Number : " + number);
        // Output: Given Number : -25

        // Check whether the number is positive.
        if (number > 0) {

            // Display the result if the number is positive.
            System.out.println(number + " is a Positive Number.");
            // Example Output: 25 is a Positive Number.

        }

        // Check whether the number is negative.
        else if (number < 0) {

            // Display the result if the number is negative.
            System.out.println(number + " is a Negative Number.");
            // Output: -25 is a Negative Number.

        }

        // Execute this block if the number is neither positive nor negative.
        else {

            // Display the result if the number is zero.
            System.out.println("The number is Zero.");
            // Example Output: The number is Zero.

        }
    }
}
