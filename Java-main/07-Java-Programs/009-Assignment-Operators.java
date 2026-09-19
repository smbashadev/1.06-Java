/*
 * ============================================================================
 * Program Name : Assignment Operators
 * File Name    : 009-Assignment-Operators.java
 * Class Name   : AssignmentOperators
 *
 * Description:
 * This program demonstrates the use of assignment operators in Java.
 * Assignment operators are used to assign values to variables and
 * perform arithmetic operations along with assignment.
 *
 * Assignment Operators:
 * =   Assign
 * +=  Add and Assign
 * -=  Subtract and Assign
 * *=  Multiply and Assign
 * /=  Divide and Assign
 * %=  Modulus and Assign
 *
 * Objective:
 * - Understand assignment operators in Java.
 * - Learn how compound assignment operators work.
 * - Display the updated value after each operation.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class AssignmentOperators {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize an integer variable.
        int number = 20;

        // Print the original value.
        System.out.println("Initial Value : " + number); // Output: Initial Value : 20

        // Add 10 and assign the result to the variable.
        number += 10;
        System.out.println("After += 10   : " + number); // Output: After += 10   : 30

        // Subtract 5 and assign the result to the variable.
        number -= 5;
        System.out.println("After -= 5    : " + number); // Output: After -= 5    : 25

        // Multiply by 2 and assign the result to the variable.
        number *= 2;
        System.out.println("After *= 2    : " + number); // Output: After *= 2    : 50

        // Divide by 5 and assign the result to the variable.
        number /= 5;
        System.out.println("After /= 5    : " + number); // Output: After /= 5    : 10

        // Find the remainder after division by 3 and assign the result.
        number %= 3;
        System.out.println("After %= 3    : " + number); // Output: After %= 3    : 1
    }
}
