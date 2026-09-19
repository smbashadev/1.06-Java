/*
 * ============================================================================
 * Program Name : String Concatenation
 * File Name    : 079-String-Concatenation.java
 * Class Name   : StringConcatenation
 *
 * Description:
 * This program accepts two Strings from the user and concatenates them
 * using the concat() method and the '+' operator.
 *
 * Objective:
 * - Understand how to concatenate Strings in Java.
 * - Learn how to use the concat() method.
 * - Learn how to use the '+' operator for String concatenation.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class StringConcatenation {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the first string.
        System.out.print("Enter the First String: ");

        // Read the first string.
        String firstString = scanner.nextLine();

        // Ask the user to enter the second string.
        System.out.print("Enter the Second String: ");

        // Read the second string.
        String secondString = scanner.nextLine();

        // Concatenate the strings using the concat() method.
        String concatenatedUsingConcat = firstString.concat(secondString);

        // Concatenate the strings using the '+' operator.
        String concatenatedUsingPlus = firstString + secondString;

        // Concatenate the strings with a space using the '+' operator.
        String concatenatedWithSpace = firstString + " " + secondString;

        // Display the result using the concat() method.
        System.out.println("Using concat() Method: " + concatenatedUsingConcat);

        // Display the result using the '+' operator.
        System.out.println("Using '+' Operator: " + concatenatedUsingPlus);

        // Display the result with a space between the strings.
        System.out.println("Using '+' Operator with Space: " + concatenatedWithSpace);

        // Example Output:
        // Enter the First String: Core
        // Enter the Second String: Java
        // Using concat() Method: CoreJava
        // Using '+' Operator: CoreJava
        // Using '+' Operator with Space: Core Java

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
