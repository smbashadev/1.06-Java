/*
 * ============================================================================
 * Program Name : Swap Two Numbers Using Third Variable
 * File Name    : 017-Swap-Two-Numbers-Using-Third-Variable.java
 * Class Name   : SwapTwoNumbersUsingThirdVariable
 *
 * Description:
 * This program demonstrates how to swap the values of two variables
 * using a third (temporary) variable. The temporary variable stores
 * one value while the other value is exchanged.
 *
 * Algorithm:
 * Step 1: Store the value of the first variable in a temporary variable.
 * Step 2: Assign the value of the second variable to the first variable.
 * Step 3: Assign the value stored in the temporary variable to the second variable.
 *
 * Objective:
 * - Understand the concept of swapping two variables.
 * - Learn how to use a temporary variable for swapping.
 * - Display the values before and after swapping.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class SwapTwoNumbersUsingThirdVariable {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Declare and initialize the first number.
        int firstNumber = 25;

        // Declare and initialize the second number.
        int secondNumber = 50;

        // Display the values before swapping.
        System.out.println("Before Swapping:");
        System.out.println("First Number  : " + firstNumber);   // Output: 25
        System.out.println("Second Number : " + secondNumber);  // Output: 50

        // Declare a temporary variable to store the first number.
        int temporary;

        // Store the value of the first number in the temporary variable.
        temporary = firstNumber;

        // Assign the value of the second number to the first number.
        firstNumber = secondNumber;

        // Assign the value stored in the temporary variable to the second number.
        secondNumber = temporary;

        // Display the values after swapping.
        System.out.println("\nAfter Swapping:");
        System.out.println("First Number  : " + firstNumber);   // Output: 50
        System.out.println("Second Number : " + secondNumber);  // Output: 25
    }
}
