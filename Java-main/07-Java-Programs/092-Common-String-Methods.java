/*
 * ============================================================================
 * Program Name : Common String Methods
 * File Name    : 092-Common-String-Methods.java
 * Class Name   : CommonStringMethods
 *
 * Description:
 * This program demonstrates the usage of commonly used methods
 * available in the String class.
 *
 * Objective:
 * - Understand the frequently used String methods.
 * - Learn how different String methods work.
 * - Display the output of each method with examples.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class CommonStringMethods {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a String object.
        String text = "  Java Programming  ";

        // Display the original String.
        System.out.println("Original String        : \"" + text + "\"");

        // Display the length of the String.
        System.out.println("length()               : " + text.length());

        // Convert the String to uppercase.
        System.out.println("toUpperCase()          : " + text.toUpperCase());

        // Convert the String to lowercase.
        System.out.println("toLowerCase()          : " + text.toLowerCase());

        // Remove leading and trailing spaces.
        System.out.println("trim()                 : \"" + text.trim() + "\"");

        // Check whether the String contains a specific word.
        System.out.println("contains(\"Java\")      : " + text.contains("Java"));

        // Check whether the String starts with a specific word.
        System.out.println("startsWith(\"  Ja\")    : " + text.startsWith("  Ja"));

        // Check whether the String ends with a specific word.
        System.out.println("endsWith(\"ing  \")     : " + text.endsWith("ing  "));

        // Get the character at a specified index.
        System.out.println("charAt(3)              : " + text.charAt(3));

        // Find the first occurrence of a character.
        System.out.println("indexOf('a')           : " + text.indexOf('a'));

        // Find the last occurrence of a character.
        System.out.println("lastIndexOf('a')       : " + text.lastIndexOf('a'));

        // Extract a portion of the String.
        System.out.println("substring(2, 6)        : " + text.substring(2, 6));

        // Replace a word in the String.
        System.out.println("replace()              : "
                + text.replace("Programming", "Language"));

        // Compare two Strings.
        System.out.println("equals()               : "
                + text.equals("Java Programming"));

        // Compare two Strings ignoring case.
        System.out.println("equalsIgnoreCase()     : "
                + text.trim().equalsIgnoreCase("java programming"));

        // Check whether the String is empty.
        System.out.println("isEmpty()              : " + text.isEmpty());

        // Example Output:
        // Original String        : "  Java Programming  "
        // length()               : 20
        // toUpperCase()          :   JAVA PROGRAMMING
        // toLowerCase()          :   java programming
        // trim()                 : "Java Programming"
        // contains("Java")       : true
        // startsWith("  Ja")     : true
        // endsWith("ing  ")      : true
        // charAt(3)              : a
        // indexOf('a')           : 3
        // lastIndexOf('a')       : 13
        // substring(2, 6)        : Java
        // replace()              :   Java Language
        // equals()               : false
        // equalsIgnoreCase()     : true
        // isEmpty()              : false
    }
}
