/*
 * ============================================================================
 * Program Name : Method with No Return Type and No Arguments
 * File Name    : 093-Method-No-Return-Type-No-Arguments.java
 * Class Name   : MethodNoReturnTypeNoArguments
 *
 * Description:
 * This program demonstrates a user-defined method that has
 * no return type and no arguments. The method simply displays
 * a welcome message.
 *
 * Objective:
 * - Understand methods with no return type.
 * - Understand methods with no arguments.
 * - Learn how to call a user-defined method.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class MethodNoReturnTypeNoArguments {

    // User-defined method with no return type and no arguments.
    public void displayMessage() {

        // Display a welcome message.
        System.out.println("Welcome to Core Java Programming!");

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        MethodNoReturnTypeNoArguments object = new MethodNoReturnTypeNoArguments();

        // Call the user-defined method.
        object.displayMessage();

        // Example Output:
        // Welcome to Core Java Programming!
    }
}
