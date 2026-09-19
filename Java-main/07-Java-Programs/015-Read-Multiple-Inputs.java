/*
 * ============================================================================
 * Program Name : Read Multiple Inputs
 * File Name    : 015-Read-Multiple-Inputs.java
 * Class Name   : ReadMultipleInputs
 *
 * Description:
 * This program demonstrates how to read multiple types of input from
 * the user using the Scanner class in Java. It accepts an integer,
 * a double, a character, a boolean, and a String from the keyboard.
 *
 * Objective:
 * - Understand how to read multiple inputs using Scanner.
 * - Learn different Scanner methods for different data types.
 * - Display all the values entered by the user.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ReadMultipleInputs {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter an integer.
        System.out.print("Enter an Integer: ");

        // Read the integer value.
        int integerValue = scanner.nextInt();

        // Ask the user to enter a decimal number.
        System.out.print("Enter a Double Value: ");

        // Read the double value.
        double doubleValue = scanner.nextDouble();

        // Ask the user to enter a single character.
        System.out.print("Enter a Character: ");

        // Read the first character entered by the user.
        char characterValue = scanner.next().charAt(0);

        // Ask the user to enter a boolean value.
        System.out.print("Enter a Boolean (true/false): ");

        // Read the boolean value.
        boolean booleanValue = scanner.nextBoolean();

        // Consume the leftover newline character.
        scanner.nextLine();

        // Ask the user to enter a string.
        System.out.print("Enter Your Name: ");

        // Read the complete line of text.
        String name = scanner.nextLine();

        // Display all the entered values.
        System.out.println("\n----- User Input Details -----");

        // Print the integer value.
        System.out.println("Integer Value  : " + integerValue);
        // Example Output: Integer Value  : 100

        // Print the double value.
        System.out.println("Double Value   : " + doubleValue);
        // Example Output: Double Value   : 98.75

        // Print the character value.
        System.out.println("Character      : " + characterValue);
        // Example Output: Character      : A

        // Print the boolean value.
        System.out.println("Boolean Value  : " + booleanValue);
        // Example Output: Boolean Value  : true

        // Print the string value.
        System.out.println("Name           : " + name);
        // Example Output: Name           : Shaik Mahaboob Basha

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
