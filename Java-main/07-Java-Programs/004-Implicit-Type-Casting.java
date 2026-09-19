/*
 * ============================================================================
 * Program Name : Widening Type Casting
 * File Name    : 004-Widening-Type-Casting.java
 * Class Name   : WideningTypeCasting
 *
 * Description:
 * This program demonstrates widening (implicit) type casting in Java.
 * Widening type casting automatically converts a smaller data type into
 * a larger data type without any explicit casting by the programmer.
 *
 * Widening Conversion Order:
 * byte → short → int → long → float → double
 *         char → int → long → float → double
 *
 * Objective:
 * - Understand widening (implicit) type casting.
 * - Learn automatic conversion from smaller to larger data types.
 * - Display the converted values.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class WideningTypeCasting {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize a byte variable.
        byte byteValue = 25;

        // Perform widening type casting from byte to int.
        int intValue = byteValue;

        // Perform widening type casting from int to long.
        long longValue = intValue;

        // Perform widening type casting from long to float.
        float floatValue = longValue;

        // Perform widening type casting from float to double.
        double doubleValue = floatValue;

        // Print the original byte value.
        System.out.println("Byte Value   : " + byteValue); // Output: Byte Value   : 25

        // Print the int value after widening.
        System.out.println("Int Value    : " + intValue); // Output: Int Value    : 25

        // Print the long value after widening.
        System.out.println("Long Value   : " + longValue); // Output: Long Value   : 25

        // Print the float value after widening.
        System.out.println("Float Value  : " + floatValue); // Output: Float Value  : 25.0

        // Print the double value after widening.
        System.out.println("Double Value : " + doubleValue); // Output: Double Value : 25.0
    }
}
