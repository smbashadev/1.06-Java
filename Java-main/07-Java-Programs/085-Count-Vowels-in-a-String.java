/*
 * ============================================================================
 * Program Name : Count Vowels in a String
 * File Name    : 085-Count-Vowels-in-a-String.java
 * Class Name   : CountVowelsInAString
 *
 * Description:
 * This program accepts a String from the user and counts the number
 * of vowels present in the String.
 *
 * Objective:
 * - Understand how to read a String from the user.
 * - Learn how to traverse a String using a for loop.
 * - Learn how to identify and count vowels.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class CountVowelsInAString {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a string.
        System.out.print("Enter a String: ");

        // Read the complete string entered by the user.
        String text = scanner.nextLine();

        // Convert the string to lowercase for easy comparison.
        String lowerCaseText = text.toLowerCase();

        // Declare a variable to store the vowel count.
        int vowelCount = 0;

        // Traverse each character of the string.
        for (int i = 0; i < lowerCaseText.length(); i++) {

            // Read the current character.
            char character = lowerCaseText.charAt(i);

            // Check whether the current character is a vowel.
            if (character == 'a' || character == 'e' || character == 'i'
                    || character == 'o' || character == 'u') {

                // Increment the vowel count.
                vowelCount++;

            }

        }

        // Display the entered string.
        System.out.println("Entered String: " + text);

        // Display the total number of vowels.
        System.out.println("Number of Vowels: " + vowelCount);

        // Example Output:
        // Enter a String: Core Java Programming
        // Entered String: Core Java Programming
        // Number of Vowels: 7

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
