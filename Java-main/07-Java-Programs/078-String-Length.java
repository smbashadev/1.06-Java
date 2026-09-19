/*
 * ============================================================================
 * Program Name : String Length
 * File Name    : 078-String-Length.java
 * Class Name   : StringLength
 *
 * Description:
 * This program accepts a String from the user and finds its length
 * using the length() method of the String class.
 *
 * Objective:
 * - Understand how to read a String from the user.
 * - Learn how to use the length() method.
 * - Display the total number of characters in a String.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class StringLength {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a string.
        System.out.print("Enter a String: ");

        // Read the complete string entered by the user.
        String text = scanner.nextLine();

        // Find the length of the string.
        int length = text.length();

        // Display the entered string.
        System.out.println("Entered String: " + text);

        // Display the length of the string.
        System.out.println("Length of the String: " + length);

        // Example Output:
        // Enter a String: Core Java
        // Entered String: Core Java
        // Length of the String: 9

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
