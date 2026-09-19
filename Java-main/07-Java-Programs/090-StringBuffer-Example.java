/*
 * ============================================================================
 * Program Name : StringBuffer Example
 * File Name    : 090-StringBuffer-Example.java
 * Class Name   : StringBufferExample
 *
 * Description:
 * This program demonstrates the usage of the StringBuffer class.
 * It performs various operations such as append, insert, replace,
 * delete, reverse, and displays the modified StringBuffer.
 *
 * Objective:
 * - Understand the concept of StringBuffer.
 * - Learn how to modify strings using StringBuffer methods.
 * - Understand that StringBuffer objects are mutable.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class StringBufferExample {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a StringBuffer object.
        StringBuffer stringBuffer = new StringBuffer("Java");

        // Display the original StringBuffer.
        System.out.println("Original StringBuffer : " + stringBuffer);

        // Append text to the StringBuffer.
        stringBuffer.append(" Programming");
        System.out.println("After append()        : " + stringBuffer);

        // Insert text at the specified index.
        stringBuffer.insert(5, "Core ");
        System.out.println("After insert()        : " + stringBuffer);

        // Replace characters from index 5 to 9.
        stringBuffer.replace(5, 9, "Advanced ");
        System.out.println("After replace()       : " + stringBuffer);

        // Delete characters from index 5 to 14.
        stringBuffer.delete(5, 14);
        System.out.println("After delete()        : " + stringBuffer);

        // Reverse the StringBuffer.
        stringBuffer.reverse();
        System.out.println("After reverse()       : " + stringBuffer);

        // Example Output:
        // Original StringBuffer : Java
        // After append()        : Java Programming
        // After insert()        : Java Core Programming
        // After replace()       : Java Advanced Programming
        // After delete()        : Java Programming
        // After reverse()       : gnimmargorP avaJ
    }
}
