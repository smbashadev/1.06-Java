/*
 * ============================================================================
 * Program Name : Read Integer Using Scanner
 * File Name    : 014-Read-Integer-Using-Scanner.java
 * Class Name   : ReadIntegerUsingScanner
 *
 * Description:
 * This program demonstrates how to read an integer value from the user
 * using the Scanner class in Java. The Scanner class is available in the
 * java.util package and is commonly used to accept user input from the keyboard.
 *
 * Objective:
 * - Understand how to use the Scanner class.
 * - Learn how to read an integer from the user.
 * - Display the entered integer value.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ReadIntegerUsingScanner {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter an integer.
        System.out.print("Enter an Integer: ");

        // Read the integer entered by the user.
        int number = scanner.nextInt();

        // Display the entered integer.
        System.out.println("You Entered: " + number);
        // Example Output: You Entered: 25

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
