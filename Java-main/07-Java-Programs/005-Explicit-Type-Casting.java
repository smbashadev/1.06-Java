/*
 * ============================================================================
 * Program Name : Narrowing Type Casting
 * File Name    : 005-Narrowing-Type-Casting.java
 * Class Name   : NarrowingTypeCasting
 *
 * Description:
 * This program demonstrates narrowing (explicit) type casting in Java.
 * Narrowing type casting converts a larger data type into a smaller
 * data type using the casting operator. Since the destination data type
 * is smaller, there may be a loss of data.
 *
 * Narrowing Conversion Order:
 * double → float → long → int → short → byte
 *
 * Objective:
 * - Understand narrowing (explicit) type casting.
 * - Learn how to convert larger data types into smaller data types.
 * - Observe the possible loss of data after conversion.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class NarrowingTypeCasting {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize a double variable.
        double doubleValue = 125.75;

        // Perform explicit type casting from double to float.
        float floatValue = (float) doubleValue;

        // Perform explicit type casting from float to long.
        long longValue = (long) floatValue;

        // Perform explicit type casting from long to int.
        int intValue = (int) longValue;

        // Perform explicit type casting from int to short.
        short shortValue = (short) intValue;

        // Perform explicit type casting from short to byte.
        byte byteValue = (byte) shortValue;

        // Print the original double value.
        System.out.println("Double Value : " + doubleValue); // Output: Double Value : 125.75

        // Print the float value after narrowing.
        System.out.println("Float Value  : " + floatValue); // Output: Float Value  : 125.75

        // Print the long value after narrowing.
        System.out.println("Long Value   : " + longValue); // Output: Long Value   : 125

        // Print the int value after narrowing.
        System.out.println("Int Value    : " + intValue); // Output: Int Value    : 125

        // Print the short value after narrowing.
        System.out.println("Short Value  : " + shortValue); // Output: Short Value  : 125

        // Print the byte value after narrowing.
        System.out.println("Byte Value   : " + byteValue); // Output: Byte Value   : 125
    }
}
