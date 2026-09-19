/*
 * ============================================================================
 * Program Name : Split a String
 * File Name    : 089-Split-a-String.java
 * Class Name   : SplitAString
 *
 * Description:
 * This program accepts a String from the user and splits it into
 * individual words using the split() method. Each word is then
 * displayed separately.
 *
 * Objective:
 * - Understand how to read a String from the user.
 * - Learn how to use the split() method.
 * - Learn how to store split values in a String array.
 * - Display each word using a for loop.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class SplitAString {

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

            // Display a message when no input is provided.
            System.out.println("The entered String is empty.");

        } else {

            // Split the string into individual words.
            String[] words = text.split("\\s+");

            // Display the heading.
            System.out.println("Words in the String:");

            // Traverse the array and display each word.
            for (int i = 0; i < words.length; i++) {

                // Display the current word.
                System.out.println("Word " + (i + 1) + ": " + words[i]);

            }

        }

        // Example Output:
        // Enter a String: Java is a Programming Language
        // Words in the String:
        // Word 1: Java
        // Word 2: is
        // Word 3: a
        // Word 4: Programming
        // Word 5: Language

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
