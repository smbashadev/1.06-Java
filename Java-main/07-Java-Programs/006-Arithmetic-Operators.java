/*
 * ============================================================================
 * Program Name : Arithmetic Operators
 * File Name    : 006-Arithmetic-Operators.java
 * Class Name   : ArithmeticOperators
 *
 * Description:
 * This program demonstrates the use of arithmetic operators in Java.
 * Arithmetic operators are used to perform mathematical operations
 * such as addition, subtraction, multiplication, division, and modulus.
 *
 * Arithmetic Operators:
 * +  Addition
 * -  Subtraction
 * *  Multiplication
 * /  Division
 * %  Modulus (Remainder)
 *
 * Objective:
 * - Understand arithmetic operators in Java.
 * - Perform basic mathematical calculations.
 * - Display the result of each arithmetic operation.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class ArithmeticOperators {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize the first integer variable.
        int number1 = 20;

        // Declare and initialize the second integer variable.
        int number2 = 6;

        // Perform addition.
        int addition = number1 + number2;

        // Perform subtraction.
        int subtraction = number1 - number2;

        // Perform multiplication.
        int multiplication = number1 * number2;

        // Perform integer division.
        int division = number1 / number2;

        // Find the remainder using the modulus operator.
        int modulus = number1 % number2;

        // Print the values of both numbers.
        System.out.println("First Number  : " + number1); // Output: First Number  : 20
        System.out.println("Second Number : " + number2); // Output: Second Number : 6

        // Print the addition result.
        System.out.println("Addition       : " + addition); // Output: Addition       : 26

        // Print the subtraction result.
        System.out.println("Subtraction    : " + subtraction); // Output: Subtraction    : 14

        // Print the multiplication result.
        System.out.println("Multiplication : " + multiplication); // Output: Multiplication : 120

        // Print the division result.
        System.out.println("Division       : " + division); // Output: Division       : 3

        // Print the modulus result.
        System.out.println("Modulus        : " + modulus); // Output: Modulus        : 2
    }
}
