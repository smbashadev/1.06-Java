/*
 * ============================================================================
 * Program Name : Static Method
 * File Name    : 097-Static-Method.java
 * Class Name   : StaticMethod
 *
 * Description:
 * This program demonstrates the usage of a static method in Java.
 * A static method belongs to the class rather than an object and
 * can be called directly using the class name.
 *
 * Objective:
 * - Understand the concept of static methods.
 * - Learn how to declare a static method.
 * - Learn how to call a static method without creating an object.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class StaticMethod {

    // Static method to display a welcome message.
    public static void displayMessage() {

        // Display the welcome message.
        System.out.println("Welcome to Core Java Programming!");

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Call the static method using the class name.
        StaticMethod.displayMessage();

        // Example Output:
        // Welcome to Core Java Programming!
    }
}
