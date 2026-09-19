/*
 * ============================================================================
 * Program Name : Convert to Lowercase
 * File Name    : 082-Convert-to-Lowercase.java
 * Class Name   : ConvertToLowercase
 *
 * Description:
 * This program accepts a String from the user and converts it into
 * lowercase using the toLowerCase() method of the String class.
 *
 * Objective:
 * - Understand how to read a String from the user.
 * - Learn how to use the toLowerCase() method.
 * - Display the original and lowercase Strings.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ConvertToLowercase {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a string.
        System.out.print("Enter a String: ");

        // Read the complete string entered by the user.
        String originalString = scanner.nextLine();

        // Convert the string to lowercase.
        String lowerCaseString = originalString.toLowerCase();

        // Display the original string.
        System.out.println("Original String: " + originalString);

        // Display the lowercase string.
        System.out.println("Lowercase String: " + lowerCaseString);

        // Example Output:
        // Enter a String: CORE JAVA PROGRAMMING
        // Original String: CORE JAVA PROGRAMMING
        // Lowercase String: core java programming

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
