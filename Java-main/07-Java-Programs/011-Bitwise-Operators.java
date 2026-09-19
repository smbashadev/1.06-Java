/*
 * ============================================================================
 * Program Name : Bitwise Operators
 * File Name    : 011-Bitwise-Operators.java
 * Class Name   : BitwiseOperators
 *
 * Description:
 * This program demonstrates the use of bitwise operators in Java.
 * Bitwise operators perform operations directly on the binary
 * representation of integer values.
 *
 * Bitwise Operators:
 * &   Bitwise AND
 * |   Bitwise OR
 * ^   Bitwise XOR
 * ~   Bitwise Complement
 * <<  Left Shift
 * >>  Right Shift
 *
 * Objective:
 * - Understand bitwise operators in Java.
 * - Learn how bitwise operations work on binary values.
 * - Display the result of each bitwise operation.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class BitwiseOperators {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize the first integer variable.
        int number1 = 10; // Binary: 1010

        // Declare and initialize the second integer variable.
        int number2 = 6;  // Binary: 0110

        // Print the values of both numbers.
        System.out.println("First Number  : " + number1); // Output: 10
        System.out.println("Second Number : " + number2); // Output: 6

        // Perform Bitwise AND operation.
        System.out.println("number1 & number2  : " + (number1 & number2)); // Output: 2

        // Perform Bitwise OR operation.
        System.out.println("number1 | number2  : " + (number1 | number2)); // Output: 14

        // Perform Bitwise XOR operation.
        System.out.println("number1 ^ number2  : " + (number1 ^ number2)); // Output: 12

        // Perform Bitwise Complement operation.
        System.out.println("~number1           : " + (~number1)); // Output: -11

        // Perform Left Shift operation.
        System.out.println("number1 << 2       : " + (number1 << 2)); // Output: 40

        // Perform Right Shift operation.
        System.out.println("number1 >> 2       : " + (number1 >> 2)); // Output: 2
    }
}
