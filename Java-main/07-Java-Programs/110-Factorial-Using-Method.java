/*
 * ============================================================================
 * Program Name : Factorial Using Method
 * File Name    : 110-Factorial-Using-Method.java
 * Class Name   : FactorialUsingMethod
 *
 * Description:
 * This program demonstrates how to calculate the factorial of a
 * number using a user-defined method. The method receives a number,
 * calculates its factorial using a loop, and returns the result.
 *
 * Objective:
 * - Understand methods with return types.
 * - Learn how to calculate factorial using a method.
 * - Learn how to return the calculated result.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class FactorialUsingMethod {

    // User-defined method to calculate the factorial of a number.
    public long findFactorial(int number) {

        // Store the factorial result.
        long factorial = 1;

        // Calculate the factorial using a for loop.
        for (int i = 1; i <= number; i++) {

            // Multiply the current value with the loop variable.
            factorial = factorial * i;

        }

        // Return the factorial value.
        return factorial;

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        FactorialUsingMethod object = new FactorialUsingMethod();

        // Declare and initialize a number.
        int number = 5;

        // Call the user-defined method and store the returned value.
        long result = object.findFactorial(number);

        // Display the entered number.
        System.out.println("Number    : " + number);

        // Display the factorial of the number.
        System.out.println("Factorial : " + result);

        // Example Output:
        // Number    : 5
        // Factorial : 120
    }
}
