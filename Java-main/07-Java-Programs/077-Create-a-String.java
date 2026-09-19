/*
 * ============================================================================
 * Program Name : Create a String
 * File Name    : 077-Create-a-String.java
 * Class Name   : CreateAString
 *
 * Description:
 * This program demonstrates different ways of creating a String in Java.
 * It creates one String using a string literal and another using the
 * String class constructor, then displays both strings.
 *
 * Objective:
 * - Understand how to create String objects in Java.
 * - Learn the difference between String literals and String objects.
 * - Display String values.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class CreateAString {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a String using a string literal.
        String stringLiteral = "Hello, Java!";

        // Create a String using the String class constructor.
        String stringObject = new String("Welcome to Core Java");

        // Display the String created using a string literal.
        System.out.println("String Created Using Literal: " + stringLiteral);

        // Display the String created using the String constructor.
        System.out.println("String Created Using new Keyword: " + stringObject);

        // Example Output:
        // String Created Using Literal: Hello, Java!
        // String Created Using new Keyword: Welcome to Core Java

    }
}
