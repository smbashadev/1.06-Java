/*
 * ============================================================================
 * Program Name : Count Words in a String
 * File Name    : 086-Count-Words-in-a-String.java
 * Class Name   : CountWordsInAString
 *
 * Description:
 * This program accepts a String from the user and counts the total
 * number of words present in the String.
 *
 * Objective:
 * - Understand how to read a String from the user.
 * - Learn how to remove extra spaces using trim().
 * - Learn how to split a String into words using split().
 * - Count and display the total number of words.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class CountWordsInAString {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a string.
        System.out.print("Enter a String: ");

        // Read the complete string entered by the user.
        String text = scanner.nextLine();

        // Remove leading and trailing spaces.
        text = text.trim();

        // Check whether the string is empty.
        if (text.isEmpty()) {

            // Display the result for an empty string.
            System.out.println("Number of Words: 0");

        } else {

            // Split the string into words using one or more spaces.
            String[] words = text.split("\\s+");

            // Count the total number of words.
            int wordCount = words.length;

            // Display the entered string.
            System.out.println("Entered String: " + text);

            // Display the total number of words.
            System.out.println("Number of Words: " + wordCount);

        }

        // Example Output:
        // Enter a String: Java is a Programming Language
        // Entered String: Java is a Programming Language
        // Number of Words: 5

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
