/*
 * ============================================================================
 * Program Name : Compare Two Strings
 * File Name    : 080-Compare-Two-Strings.java
 * Class Name   : CompareTwoStrings
 *
 * Description:
 * This program accepts two Strings from the user and compares them
 * using equals(), equalsIgnoreCase(), and compareTo() methods.
 *
 * Objective:
 * - Understand different ways to compare Strings in Java.
 * - Learn the use of equals(), equalsIgnoreCase(), and compareTo().
 * - Display comparison results.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class CompareTwoStrings {

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

        // Compare the strings using equals() method.
        boolean equalsResult = firstString.equals(secondString);

        // Compare the strings using equalsIgnoreCase() method.
        boolean ignoreCaseResult = firstString.equalsIgnoreCase(secondString);

        // Compare the strings using compareTo() method.
        int compareToResult = firstString.compareTo(secondString);

        // Display the result of equals().
        System.out.println("Using equals(): " + equalsResult);

        // Display the result of equalsIgnoreCase().
        System.out.println("Using equalsIgnoreCase(): " + ignoreCaseResult);

        // Display the result of compareTo().
        System.out.println("Using compareTo(): " + compareToResult);

        // Explain the compareTo() result.
        if (compareToResult == 0) {

            // Both strings are equal.
            System.out.println("Both Strings are Equal.");

        } else if (compareToResult > 0) {

            // The first string is greater.
            System.out.println("First String is Greater than Second String.");

        } else {

            // The second string is greater.
            System.out.println("First String is Less than Second String.");

        }

        // Example Output:
        // Enter the First String: Java
        // Enter the Second String: java
        // Using equals(): false
        // Using equalsIgnoreCase(): true
        // Using compareTo(): -32
        // First String is Less than Second String.

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
