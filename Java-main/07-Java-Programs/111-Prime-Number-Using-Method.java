/*
 * ============================================================================
 * Program Name : Prime Number Using Method
 * File Name    : 111-Prime-Number-Using-Method.java
 * Class Name   : PrimeNumberUsingMethod
 *
 * Description:
 * This program demonstrates how to check whether a number is
 * prime or not using a user-defined method. The method returns
 * true if the number is prime; otherwise, it returns false.
 *
 * Objective:
 * - Understand methods with boolean return types.
 * - Learn how to check for prime numbers using a method.
 * - Understand conditional statements and loops.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class PrimeNumberUsingMethod {

    // User-defined method to check whether a number is prime.
    public boolean isPrime(int number) {

        // Check whether the number is less than or equal to 1.
        if (number <= 1) {

            // Return false because numbers less than or equal to 1 are not prime.
            return false;

        }

        // Check divisibility from 2 to number / 2.
        for (int i = 2; i <= number / 2; i++) {

            // Check whether the number is divisible by the current value.
            if (number % i == 0) {

                // Return false because the number has a factor.
                return false;

            }

        }

        // Return true because no factors were found.
        return true;

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        PrimeNumberUsingMethod object = new PrimeNumberUsingMethod();

        // Declare and initialize a number.
        int number = 29;

        // Call the user-defined method and store the returned result.
        boolean result = object.isPrime(number);

        // Display the entered number.
        System.out.println("Number : " + number);

        // Check whether the number is prime.
        if (result) {

            // Display the prime message.
            System.out.println(number + " is a Prime Number.");

        } else {

            // Display the non-prime message.
            System.out.println(number + " is Not a Prime Number.");

        }

        // Example Output:
        // Number : 29
        // 29 is a Prime Number.
    }
}
