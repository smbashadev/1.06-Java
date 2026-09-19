/*
 * ============================================================================
 * Program Name : Method Overloading with Different Parameters
 * File Name    : 104-Method-Overloading-Different-Parameters.java
 * Class Name   : MethodOverloadingDifferentParameters
 *
 * Description:
 * This program demonstrates the concept of method overloading
 * using different parameter lists. Multiple methods have the
 * same name but differ in the number and types of parameters.
 *
 * Objective:
 * - Understand method overloading using different parameter lists.
 * - Learn how Java selects the appropriate overloaded method.
 * - Understand compile-time polymorphism.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class MethodOverloadingDifferentParameters {

    // User-defined method that accepts one integer parameter.
    public void displayDetails(int number) {

        // Display the integer value.
        System.out.println("Integer Value          : " + number);

    }

    // User-defined overloaded method that accepts two integer parameters.
    public void displayDetails(int number1, int number2) {

        // Display the first integer value.
        System.out.println("First Integer Value    : " + number1);

        // Display the second integer value.
        System.out.println("Second Integer Value   : " + number2);

    }

    // User-defined overloaded method that accepts an integer and a String.
    public void displayDetails(int number, String text) {

        // Display the integer value.
        System.out.println("Integer Value          : " + number);

        // Display the String value.
        System.out.println("String Value           : " + text);

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        MethodOverloadingDifferentParameters object =
                new MethodOverloadingDifferentParameters();

        // Call the method with one integer parameter.
        object.displayDetails(100);

        // Display a blank line.
        System.out.println();

        // Call the overloaded method with two integer parameters.
        object.displayDetails(100, 200);

        // Display a blank line.
        System.out.println();

        // Call the overloaded method with an integer and a String.
        object.displayDetails(101, "Core Java");

        // Example Output:
        // Integer Value          : 100
        //
        // First Integer Value    : 100
        // Second Integer Value   : 200
        //
        // Integer Value          : 101
        // String Value           : Core Java
    }
}
