/*
 * ============================================================================
 * Program Name : Method Overloading Calculator
 * File Name    : 105-Method-Overloading-Calculator.java
 * Class Name   : MethodOverloadingCalculator
 *
 * Description:
 * This program demonstrates the concept of method overloading
 * by implementing a simple calculator. Multiple methods with
 * the same name perform addition using different parameter lists.
 *
 * Objective:
 * - Understand method overloading using a real-time example.
 * - Learn how Java selects the appropriate overloaded method.
 * - Understand compile-time polymorphism.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class MethodOverloadingCalculator {

    // User-defined method to add two integer numbers.
    public int add(int number1, int number2) {

        // Return the sum of two integers.
        return number1 + number2;

    }

    // User-defined overloaded method to add three integer numbers.
    public int add(int number1, int number2, int number3) {

        // Return the sum of three integers.
        return number1 + number2 + number3;

    }

    // User-defined overloaded method to add two double numbers.
    public double add(double number1, double number2) {

        // Return the sum of two double values.
        return number1 + number2;

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        MethodOverloadingCalculator object = new MethodOverloadingCalculator();

        // Call the method that accepts two integer arguments.
        int result1 = object.add(10, 20);

        // Call the overloaded method that accepts three integer arguments.
        int result2 = object.add(10, 20, 30);

        // Call the overloaded method that accepts two double arguments.
        double result3 = object.add(12.5, 7.5);

        // Display the result of adding two integers.
        System.out.println("Addition of Two Integers   : " + result1);

        // Display the result of adding three integers.
        System.out.println("Addition of Three Integers : " + result2);

        // Display the result of adding two double values.
        System.out.println("Addition of Two Doubles    : " + result3);

        // Example Output:
        // Addition of Two Integers   : 30
        // Addition of Three Integers : 60
        // Addition of Two Doubles    : 20.0
    }
}
