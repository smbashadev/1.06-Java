/*
 * ============================================================================
 * Program Name : Method Overloading with Two Methods
 * File Name    : 102-Method-Overloading-Two-Methods.java
 * Class Name   : MethodOverloadingTwoMethods
 *
 * Description:
 * This program demonstrates the concept of method overloading in Java.
 * Two methods with the same name but different parameter lists are
 * defined. The appropriate method is called based on the arguments
 * passed during method invocation.
 *
 * Objective:
 * - Understand the concept of method overloading.
 * - Learn how methods can have the same name.
 * - Learn how Java selects the appropriate overloaded method.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class MethodOverloadingTwoMethods {

    // User-defined method to calculate the sum of two numbers.
    public int calculateSum(int number1, int number2) {

        // Return the sum of two numbers.
        return number1 + number2;

    }

    // User-defined overloaded method to calculate the sum of three numbers.
    public int calculateSum(int number1, int number2, int number3) {

        // Return the sum of three numbers.
        return number1 + number2 + number3;

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        MethodOverloadingTwoMethods object = new MethodOverloadingTwoMethods();

        // Call the method that accepts two arguments.
        int result1 = object.calculateSum(10, 20);

        // Call the overloaded method that accepts three arguments.
        int result2 = object.calculateSum(10, 20, 30);

        // Display the result of the first method.
        System.out.println("Sum of Two Numbers   : " + result1);

        // Display the result of the second method.
        System.out.println("Sum of Three Numbers : " + result2);

        // Example Output:
        // Sum of Two Numbers   : 30
        // Sum of Three Numbers : 60
    }
}
