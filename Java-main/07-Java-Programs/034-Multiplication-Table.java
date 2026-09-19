/*
 * ============================================================================
 * Program Name : Multiplication Table
 * File Name    : 034-Multiplication-Table.java
 * Class Name   : MultiplicationTable
 *
 * Description:
 * This program accepts a number from the user and prints its
 * multiplication table from 1 to 10 using the for loop.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the for loop.
 * - Generate a multiplication table for a given number.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class MultiplicationTable {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a number.
        System.out.print("Enter a Number: ");

        // Read the number entered by the user.
        int number = scanner.nextInt();

        // Display a heading.
        System.out.println("\nMultiplication Table of " + number + ":");

        // Iterate from 1 to 10 using the for loop.
        for (int i = 1; i <= 10; i++) {

            // Calculate the multiplication result.
            int result = number * i;

            // Display the multiplication table.
            System.out.println(number + " x " + i + " = " + result);
            // Example Output:
            // 5 x 1 = 5
            // 5 x 2 = 10
            // ...
            // 5 x 10 = 50
        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
