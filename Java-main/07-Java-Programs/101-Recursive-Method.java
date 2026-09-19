/*
 * ============================================================================
 * Program Name : Recursive Method
 * File Name    : 101-Recursive-Method.java
 * Class Name   : RecursiveMethod
 *
 * Description:
 * This program demonstrates the concept of recursion in Java.
 * A recursive method is a method that calls itself until a
 * terminating condition (base case) is reached.
 *
 * Objective:
 * - Understand the concept of recursion.
 * - Learn how a recursive method works.
 * - Calculate the factorial of a number using recursion.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class RecursiveMethod {

    // User-defined recursive method to calculate the factorial of a number.
    public int findFactorial(int number) {

        // Check whether the number is 0 or 1.
        if (number == 0 || number == 1) {

            // Return 1 because the factorial of 0 and 1 is 1.
            return 1;

        }

        // Return the factorial by calling the same method recursively.
        return number * findFactorial(number - 1);

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        RecursiveMethod object = new RecursiveMethod();

        // Declare and initialize a number.
        int number = 5;

        // Call the recursive method and store the returned value.
        int factorial = object.findFactorial(number);

        // Display the entered number.
        System.out.println("Number    : " + number);

        // Display the factorial of the number.
        System.out.println("Factorial : " + factorial);

        // Example Output:
        // Number    : 5
        // Factorial : 120
    }
}
