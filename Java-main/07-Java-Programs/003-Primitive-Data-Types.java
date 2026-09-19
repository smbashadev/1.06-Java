/*
 * ============================================================================
 * Program Name : Primitive Data Types
 * File Name    : 003-Primitive-Data-Types.java
 * Class Name   : PrimitiveDataTypes
 *
 * Description:
 * This program demonstrates all eight primitive data types available in Java.
 * It declares, initializes, and prints the value of each primitive data type.
 *
 * Objective:
 * - Learn the eight primitive data types in Java.
 * - Understand how to declare and initialize variables.
 * - Display variable values using System.out.println().
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class PrimitiveDataTypes {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize a byte variable.
        byte byteValue = 100;

        // Declare and initialize a short variable.
        short shortValue = 20000;

        // Declare and initialize an int variable.
        int intValue = 500000;

        // Declare and initialize a long variable.
        long longValue = 9876543210L;

        // Declare and initialize a float variable.
        float floatValue = 45.75f;

        // Declare and initialize a double variable.
        double doubleValue = 12345.6789;

        // Declare and initialize a char variable.
        char charValue = 'J';

        // Declare and initialize a boolean variable.
        boolean booleanValue = true;

        // Print the value of the byte variable.
        System.out.println("Byte Value    : " + byteValue); // Output: Byte Value    : 100

        // Print the value of the short variable.
        System.out.println("Short Value   : " + shortValue); // Output: Short Value   : 20000

        // Print the value of the int variable.
        System.out.println("Int Value     : " + intValue); // Output: Int Value     : 500000

        // Print the value of the long variable.
        System.out.println("Long Value    : " + longValue); // Output: Long Value    : 9876543210

        // Print the value of the float variable.
        System.out.println("Float Value   : " + floatValue); // Output: Float Value   : 45.75

        // Print the value of the double variable.
        System.out.println("Double Value  : " + doubleValue); // Output: Double Value  : 12345.6789

        // Print the value of the char variable.
        System.out.println("Char Value    : " + charValue); // Output: Char Value    : J

        // Print the value of the boolean variable.
        System.out.println("Boolean Value : " + booleanValue); // Output: Boolean Value : true
    }
}
