/*
 * ============================================================================
 * Program Name : Display Unicode Value
 * File Name    : 020-Display-Unicode-Value.java
 * Class Name   : DisplayUnicodeValue
 *
 * Description:
 * This program demonstrates how to display the Unicode value of
 * different characters in Java. Java internally uses the Unicode
 * character set, which supports characters from multiple languages
 * around the world.
 *
 * Objective:
 * - Understand Unicode representation in Java.
 * - Learn how characters are stored as Unicode values.
 * - Display the Unicode value of different characters.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class DisplayUnicodeValue {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize an uppercase character.
        char upperCaseCharacter = 'A';

        // Convert the uppercase character to its Unicode value.
        int upperCaseUnicode = upperCaseCharacter;

        // Display the uppercase character.
        System.out.println("Character     : " + upperCaseCharacter);
        // Output: Character     : A

        // Display its Unicode value.
        System.out.println("Unicode Value : " + upperCaseUnicode);
        // Output: Unicode Value : 65

        // Declare and initialize a lowercase character.
        char lowerCaseCharacter = 'a';

        // Convert the lowercase character to its Unicode value.
        int lowerCaseUnicode = lowerCaseCharacter;

        // Display the lowercase character.
        System.out.println("\nCharacter     : " + lowerCaseCharacter);
        // Output: Character     : a

        // Display its Unicode value.
        System.out.println("Unicode Value : " + lowerCaseUnicode);
        // Output: Unicode Value : 97

        // Declare and initialize a Unicode character.
        char unicodeCharacter = '\u20B9';

        // Convert the Unicode character to its integer value.
        int unicodeValue = unicodeCharacter;

        // Display the Unicode character.
        System.out.println("\nCharacter     : " + unicodeCharacter);
        // Output: Character     : ₹

        // Display its Unicode value.
        System.out.println("Unicode Value : " + unicodeValue);
        // Output: Unicode Value : 8377

        // Display the Unicode escape sequence.
        System.out.println("Unicode Escape: \\u20B9");
        // Output: Unicode Escape: \u20B9
    }
}
