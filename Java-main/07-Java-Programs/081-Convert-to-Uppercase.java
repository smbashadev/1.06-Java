/*
 * ============================================================================
 * Program Name : Convert to Uppercase
 * File Name    : 081-Convert-to-Uppercase.java
 * Class Name   : ConvertToUppercase
 *
 * Description:
 * This program accepts a String from the user and converts it into
 * uppercase using the toUpperCase() method of the String class.
 *
 * Objective:
 * - Understand how to read a String from the user.
 * - Learn how to use the toUpperCase() method.
 * - Display the original and uppercase Strings.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ConvertToUppercase {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a string.
        System.out.print("Enter a String: ");

        // Read the complete string entered by the user.
        String originalString = scanner.nextLine();

        // Convert the string to uppercase.
        String upperCaseString = originalString.toUpperCase();

        // Display the original string.
        System.out.println("Original String: " + originalString);

        // Display the uppercase string.
        System.out.println("Uppercase String: " + upperCaseString);

        // Example Output:
        // Enter a String: Core Java Programming
        // Original String: Core Java Programming
        // Uppercase String: CORE JAVA PROGRAMMING

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
