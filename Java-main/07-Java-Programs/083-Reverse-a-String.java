/*
 * ============================================================================
 * Program Name : Reverse a String
 * File Name    : 083-Reverse-a-String.java
 * Class Name   : ReverseAString
 *
 * Description:
 * This program accepts a String from the user and reverses it
 * using a for loop and the charAt() method.
 *
 * Objective:
 * - Understand how to read a String from the user.
 * - Learn how to access individual characters using charAt().
 * - Display the reversed String.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ReverseAString {

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

        // Example Output:
        // Enter a String: Java Programming
        // Original String: Java Programming
        // Reversed String: gnimmargorP avaJ

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
