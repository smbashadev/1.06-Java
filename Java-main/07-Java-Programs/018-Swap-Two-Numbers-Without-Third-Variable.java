/*
 * ============================================================================
 * Program Name : Swap Two Numbers Without Third Variable
 * File Name    : 018-Swap-Two-Numbers-Without-Third-Variable.java
 * Class Name   : SwapTwoNumbersWithoutThirdVariable
 *
 * Description:
 * This program demonstrates how to swap the values of two variables
 * without using a third (temporary) variable. The swapping is performed
 * using arithmetic operations.
 *
 * Algorithm:
 * Step 1: firstNumber = firstNumber + secondNumber
 * Step 2: secondNumber = firstNumber - secondNumber
 * Step 3: firstNumber = firstNumber - secondNumber
 *
 * Note:
 * This approach works for most integer values but may cause overflow
 * if the numbers are extremely large. For practical applications,
 * using a temporary variable is generally recommended for better readability.
 *
 * Objective:
 * - Understand swapping without a temporary variable.
 * - Learn how arithmetic operations can exchange values.
 * - Display the values before and after swapping.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class SwapTwoNumbersWithoutThirdVariable {

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

        // Add both numbers and store the result in the first variable.
        firstNumber = firstNumber + secondNumber;

        // Subtract the original second number to obtain the original first number.
        secondNumber = firstNumber - secondNumber;

        // Subtract the updated second number to obtain the original second number.
        firstNumber = firstNumber - secondNumber;

        // Display the values after swapping.
        System.out.println("\nAfter Swapping:");
        System.out.println("First Number  : " + firstNumber);   // Output: 50
        System.out.println("Second Number : " + secondNumber);  // Output: 25
    }
}
