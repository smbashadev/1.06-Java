/*
 * ============================================================================
 * Program Name : Remove Spaces from a String
 * File Name    : 087-Remove-Spaces-from-a-String.java
 * Class Name   : RemoveSpacesFromAString
 *
 * Description:
 * This program accepts a String from the user and removes all spaces
 * present in the String using the replace() method.
 *
 * Objective:
 * - Understand how to read a String from the user.
 * - Learn how to use the replace() method.
 * - Display the String after removing all spaces.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class RemoveSpacesFromAString {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a string.
        System.out.print("Enter a String: ");

        // Read the complete string entered by the user.
        String originalString = scanner.nextLine();

        // Remove all spaces from the string.
        String modifiedString = originalString.replace(" ", "");

        // Display the original string.
        System.out.println("Original String: " + originalString);

        // Display the modified string.
        System.out.println("String After Removing Spaces: " + modifiedString);

        // Example Output:
        // Enter a String: Core Java Programming
        // Original String: Core Java Programming
        // String After Removing Spaces: CoreJavaProgramming

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
