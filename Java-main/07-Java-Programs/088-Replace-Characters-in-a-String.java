/*
 * ============================================================================
 * Program Name : Replace Characters in a String
 * File Name    : 088-Replace-Characters-in-a-String.java
 * Class Name   : ReplaceCharactersInAString
 *
 * Description:
 * This program accepts a String, a character to be replaced,
 * and a new character from the user. It replaces all occurrences
 * of the specified character using the replace() method.
 *
 * Objective:
 * - Understand how to read a String from the user.
 * - Learn how to use the replace() method.
 * - Display the modified String after character replacement.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ReplaceCharactersInAString {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a string.
        System.out.print("Enter a String: ");

        // Read the complete string entered by the user.
        String originalString = scanner.nextLine();

        // Ask the user to enter the character to be replaced.
        System.out.print("Enter the Character to Replace: ");

        // Read the old character.
        char oldCharacter = scanner.next().charAt(0);

        // Ask the user to enter the new character.
        System.out.print("Enter the New Character: ");

        // Read the new character.
        char newCharacter = scanner.next().charAt(0);

        // Replace all occurrences of the specified character.
        String modifiedString = originalString.replace(oldCharacter, newCharacter);

        // Display the original string.
        System.out.println("Original String: " + originalString);

        // Display the modified string.
        System.out.println("Modified String: " + modifiedString);

        // Example Output:
        // Enter a String: Java Programming
        // Enter the Character to Replace: a
        // Enter the New Character: o
        // Original String: Java Programming
        // Modified String: Jovo Progrimming

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
