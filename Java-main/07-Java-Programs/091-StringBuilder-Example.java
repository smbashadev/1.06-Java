/*
 * ============================================================================
 * Program Name : StringBuilder Example
 * File Name    : 091-StringBuilder-Example.java
 * Class Name   : StringBuilderExample
 *
 * Description:
 * This program demonstrates the usage of the StringBuilder class.
 * It performs various operations such as append, insert, replace,
 * delete, reverse, and displays the modified StringBuilder.
 *
 * Objective:
 * - Understand the concept of StringBuilder.
 * - Learn how to modify strings using StringBuilder methods.
 * - Understand that StringBuilder objects are mutable.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class StringBuilderExample {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a StringBuilder object.
        StringBuilder stringBuilder = new StringBuilder("Java");

        // Display the original StringBuilder.
        System.out.println("Original StringBuilder : " + stringBuilder);

        // Append text to the StringBuilder.
        stringBuilder.append(" Programming");
        System.out.println("After append()         : " + stringBuilder);

        // Insert text at the specified index.
        stringBuilder.insert(5, "Core ");
        System.out.println("After insert()         : " + stringBuilder);

        // Replace characters from index 5 to 9.
        stringBuilder.replace(5, 9, "Advanced ");
        System.out.println("After replace()        : " + stringBuilder);

        // Delete characters from index 5 to 14.
        stringBuilder.delete(5, 14);
        System.out.println("After delete()         : " + stringBuilder);

        // Reverse the StringBuilder.
        stringBuilder.reverse();
        System.out.println("After reverse()        : " + stringBuilder);

        // Example Output:
        // Original StringBuilder : Java
        // After append()         : Java Programming
        // After insert()         : Java Core Programming
        // After replace()        : Java Advanced Programming
        // After delete()         : Java Programming
        // After reverse()        : gnimmargorP avaJ
    }
}
