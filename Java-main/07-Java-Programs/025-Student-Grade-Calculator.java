/*
 * ============================================================================
 * Program Name : Student Grade Calculator
 * File Name    : 025-Student-Grade-Calculator.java
 * Class Name   : StudentGradeCalculator
 *
 * Description:
 * This program accepts the marks obtained by a student and calculates
 * the corresponding grade using the if-else-if ladder.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the if-else-if ladder.
 * - Calculate the student's grade based on marks.
 *
 * Grade Criteria:
 * Marks >= 90  : Grade A
 * Marks >= 80  : Grade B
 * Marks >= 70  : Grade C
 * Marks >= 60  : Grade D
 * Marks >= 35  : Grade E
 * Marks < 35   : Fail
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class StudentGradeCalculator {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the student's marks.
        System.out.print("Enter Student Marks (0 - 100): ");

        // Read the marks entered by the user.
        int marks = scanner.nextInt();

        // Check whether the entered marks are valid.
        if (marks < 0 || marks > 100) {

            // Display an error message for invalid marks.
            System.out.println("Invalid Marks! Please enter marks between 0 and 100.");
            // Example Output: Invalid Marks! Please enter marks between 0 and 100.

        }

        // Check whether the student secured Grade A.
        else if (marks >= 90) {

            // Display Grade A.
            System.out.println("Grade: A");
            // Example Output: Grade: A

        }

        // Check whether the student secured Grade B.
        else if (marks >= 80) {

            // Display Grade B.
            System.out.println("Grade: B");
            // Example Output: Grade: B

        }

        // Check whether the student secured Grade C.
        else if (marks >= 70) {

            // Display Grade C.
            System.out.println("Grade: C");
            // Example Output: Grade: C

        }

        // Check whether the student secured Grade D.
        else if (marks >= 60) {

            // Display Grade D.
            System.out.println("Grade: D");
            // Example Output: Grade: D

        }

        // Check whether the student secured Grade E.
        else if (marks >= 35) {

            // Display Grade E.
            System.out.println("Grade: E");
            // Example Output: Grade: E

        }

        // Execute this block if the student has failed.
        else {

            // Display the fail result.
            System.out.println("Result: Fail");
            // Example Output: Result: Fail

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
