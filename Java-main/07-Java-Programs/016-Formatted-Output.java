/*
 * ============================================================================
 * Program Name : Formatted Output
 * File Name    : 016-Formatted-Output.java
 * Class Name   : FormattedOutput
 *
 * Description:
 * This program demonstrates how to display formatted output in Java
 * using the printf() method. The printf() method allows developers to
 * print values in a structured and readable format using format specifiers.
 *
 * Common Format Specifiers:
 * %d  - Integer
 * %f  - Floating-point Number
 * %.2f - Floating-point Number (2 Decimal Places)
 * %c  - Character
 * %s  - String
 * %b  - Boolean
 * %n  - New Line
 *
 * Objective:
 * - Understand formatted output using printf().
 * - Learn commonly used format specifiers.
 * - Display different data types in a formatted manner.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class FormattedOutput {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize an integer variable.
        int studentId = 101;

        // Declare and initialize a String variable.
        String studentName = "Shaik Mahaboob Basha";

        // Declare and initialize a double variable.
        double percentage = 92.75;

        // Declare and initialize a character variable.
        char grade = 'A';

        // Declare and initialize a boolean variable.
        boolean isPassed = true;

        // Display a heading.
        System.out.println("========== Student Details ==========");

        // Print the integer using %d.
        System.out.printf("Student ID    : %d%n", studentId);

        // Print the String using %s.
        System.out.printf("Student Name  : %s%n", studentName);

        // Print the double value with two decimal places using %.2f.
        System.out.printf("Percentage    : %.2f%n", percentage);

        // Print the character using %c.
        System.out.printf("Grade         : %c%n", grade);

        // Print the boolean using %b.
        System.out.printf("Passed        : %b%n", isPassed);

        // Display another heading.
        System.out.println();

        System.out.println("========== Employee Details ==========");

        // Print multiple values using a single printf() statement.
        System.out.printf(
                "ID: %d | Name: %s | Salary: %.2f | Grade: %c%n",
                1001,
                "Rahul",
                55000.75,
                'A'
        );

        /*
         * Expected Output:
         *
         * ========== Student Details ==========
         * Student ID    : 101
         * Student Name  : Shaik Mahaboob Basha
         * Percentage    : 92.75
         * Grade         : A
         * Passed        : true
         *
         * ========== Employee Details ==========
         * ID: 1001 | Name: Rahul | Salary: 55000.75 | Grade: A
         */
    }
}
