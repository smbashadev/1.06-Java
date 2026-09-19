/*
 * ============================================================================
 * Program Name : Method Overloading with Different Data Types
 * File Name    : 103-Method-Overloading-Different-Data-Types.java
 * Class Name   : MethodOverloadingDifferentDataTypes
 *
 * Description:
 * This program demonstrates the concept of method overloading
 * using different data types. Multiple methods have the same
 * name but accept different parameter types.
 *
 * Objective:
 * - Understand method overloading using different data types.
 * - Learn how Java selects the appropriate overloaded method.
 * - Understand compile-time polymorphism.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class MethodOverloadingDifferentDataTypes {

    // User-defined method to display an integer value.
    public void displayValue(int number) {

        // Display the integer value.
        System.out.println("Integer Value : " + number);

    }

    // User-defined overloaded method to display a double value.
    public void displayValue(double number) {

        // Display the double value.
        System.out.println("Double Value  : " + number);

    }

    // User-defined overloaded method to display a String value.
    public void displayValue(String text) {

        // Display the String value.
        System.out.println("String Value  : " + text);

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        MethodOverloadingDifferentDataTypes object =
                new MethodOverloadingDifferentDataTypes();

        // Call the method with an integer argument.
        object.displayValue(100);

        // Call the overloaded method with a double argument.
        object.displayValue(99.99);

        // Call the overloaded method with a String argument.
        object.displayValue("Core Java");

        // Example Output:
        // Integer Value : 100
        // Double Value  : 99.99
        // String Value  : Core Java
    }
}
