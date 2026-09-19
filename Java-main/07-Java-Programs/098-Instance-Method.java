/*
 * ============================================================================
 * Program Name : Instance Method
 * File Name    : 098-Instance-Method.java
 * Class Name   : InstanceMethod
 *
 * Description:
 * This program demonstrates the usage of an instance method in Java.
 * An instance method belongs to an object of the class and can be
 * called only after creating an object.
 *
 * Objective:
 * - Understand the concept of instance methods.
 * - Learn how to declare an instance method.
 * - Learn how to call an instance method using an object.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class InstanceMethod {

    // Instance method to display a welcome message.
    public void displayMessage() {

        // Display the welcome message.
        System.out.println("Welcome to Core Java Programming!");

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        InstanceMethod object = new InstanceMethod();

        // Call the instance method using the object.
        object.displayMessage();

        // Example Output:
        // Welcome to Core Java Programming!
    }
}
