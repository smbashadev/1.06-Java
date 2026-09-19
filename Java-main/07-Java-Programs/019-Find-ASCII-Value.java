/*
 * ============================================================================
 * Program Name : Find ASCII Value
 * File Name    : 019-Find-ASCII-Value.java
 * Class Name   : FindASCIIValue
 *
 * Description:
 * This program demonstrates how to find the ASCII value of a character
 * in Java. Every character is internally represented by a numeric value
 * based on the ASCII (American Standard Code for Information Interchange)
 * character set for standard characters.
 *
 * Objective:
 * - Understand the relationship between characters and their ASCII values.
 * - Learn how implicit type conversion converts a char into an int.
 * - Display the ASCII value of a given character.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class FindASCIIValue {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize a character variable.
        char character = 'A';

        // Convert the character to its ASCII value using implicit type casting.
        int asciiValue = character;

        // Display the character.
        System.out.println("Character   : " + character);
        // Output: Character   : A

        // Display the ASCII value.
        System.out.println("ASCII Value : " + asciiValue);
        // Output: ASCII Value : 65

        // Display another example using a lowercase character.
        char lowerCaseCharacter = 'a';

        // Convert the lowercase character to its ASCII value.
        int lowerCaseASCII = lowerCaseCharacter;

        // Display the lowercase character.
        System.out.println("\nCharacter   : " + lowerCaseCharacter);
        // Output: Character   : a

        // Display its ASCII value.
        System.out.println("ASCII Value : " + lowerCaseASCII);
        // Output: ASCII Value : 97

        // Display another example using a digit.
        char digit = '5';

        // Convert the digit to its ASCII value.
        int digitASCII = digit;

        // Display the digit.
        System.out.println("\nCharacter   : " + digit);
        // Output: Character   : 5

        // Display its ASCII value.
        System.out.println("ASCII Value : " + digitASCII);
        // Output: ASCII Value : 53
    }
}
