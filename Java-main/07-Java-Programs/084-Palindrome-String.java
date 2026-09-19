/*
 * ============================================================================
 * Program Name : Palindrome String
 * File Name    : 084-Palindrome-String.java
 * Class Name   : PalindromeString
 *
 * Description:
 * This program accepts a String from the user and checks whether
 * it is a palindrome by reversing the String and comparing it with
 * the original String.
 *
 * Objective:
 * - Understand how to read a String from the user.
 * - Learn how to reverse a String using charAt().
 * - Learn how to compare two Strings using equals().
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class PalindromeString {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a string.
        System.out.print("Enter a String: ");

        // Read the complete string entered by the user.
        String originalString = scanner.nextLine();

        // Declare a variable to store the reversed string.
        String reversedString = "";

        // Traverse the string from the last character to the first.
        for (int i = originalString.length() - 1; i >= 0; i--) {

            // Append the current character to the reversed string.
            reversedString = reversedString + originalString.charAt(i);

        }

        // Display the original string.
        System.out.println("Original String: " + originalString);

        // Display the reversed string.
        System.out.println("Reversed String: " + reversedString);

        // Check whether both strings are equal.
        if (originalString.equals(reversedString)) {

            // Display the palindrome message.
            System.out.println("The Given String is a Palindrome.");

        } else {

            // Display the non-palindrome message.
            System.out.println("The Given String is Not a Palindrome.");

        }

        // Example Output:
        // Enter a String: madam
        // Original String: madam
        // Reversed String: madam
        // The Given String is a Palindrome.

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
