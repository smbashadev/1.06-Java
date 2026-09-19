/*
 * ============================================================================
 * Program Name : Alphabet Pattern
 * File Name    : 058-Alphabet-Pattern.java
 * Class Name   : AlphabetPattern
 *
 * Description:
 * This program accepts the number of rows from the user and prints
 * an Alphabet Pattern using nested for loops.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use nested for loops.
 * - Print an Alphabet Pattern.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class AlphabetPattern {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the number of rows.
        System.out.print("Enter the Number of Rows: ");

        // Read the number of rows entered by the user.
        int rows = scanner.nextInt();

        // Check whether the entered number of rows is valid.
        if (rows <= 0 || rows > 26) {

            // Display an error message.
            System.out.println("Please enter a value between 1 and 26.");
            // Example Output:
            // Please enter a value between 1 and 26.

        } else {

            // Display the heading.
            System.out.println("Alphabet Pattern:");

            // Outer loop controls the number of rows.
            for (int i = 1; i <= rows; i++) {

                // Calculate the alphabet character for the current row.
                char alphabet = (char) ('A' + i - 1);

                // Inner loop prints the current alphabet.
                for (int j = 1; j <= i; j++) {

                    // Print the current alphabet.
                    System.out.print(alphabet + " ");

                }

                // Move to the next line after printing one row.
                System.out.println();

            }

            // Example Output:
            // Enter the Number of Rows: 5
            // Alphabet Pattern:
            // A
            // B B
            // C C C
            // D D D D
            // E E E E E

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
