/*
 * ============================================================================
 * Program Name : Electricity Bill Calculator
 * File Name    : 026-Electricity-Bill-Calculator.java
 * Class Name   : ElectricityBillCalculator
 *
 * Description:
 * This program calculates the electricity bill based on the number of
 * units consumed using the if-else-if ladder.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to perform calculations using conditional statements.
 * - Calculate the electricity bill based on slab rates.
 *
 * Tariff:
 * 0 - 100 Units     : ₹1.50 per Unit
 * 101 - 200 Units   : ₹2.50 per Unit
 * 201 - 300 Units   : ₹4.00 per Unit
 * Above 300 Units   : ₹6.00 per Unit
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class ElectricityBillCalculator {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the number of electricity units consumed.
        System.out.print("Enter Electricity Units Consumed: ");

        // Read the number of units entered by the user.
        int units = scanner.nextInt();

        // Declare a variable to store the calculated bill amount.
        double billAmount;

        // Check whether the entered units are valid.
        if (units < 0) {

            // Display an error message for invalid input.
            System.out.println("Invalid Input! Units cannot be negative.");
            // Example Output: Invalid Input! Units cannot be negative.

        }

        // Calculate the bill for units up to 100.
        else if (units <= 100) {

            // Calculate the bill amount.
            billAmount = units * 1.50;

            // Display the total bill amount.
            System.out.println("Electricity Bill: ₹" + billAmount);
            // Example Output: Electricity Bill: ₹120.0

        }

        // Calculate the bill for units between 101 and 200.
        else if (units <= 200) {

            // Calculate the bill amount.
            billAmount = units * 2.50;

            // Display the total bill amount.
            System.out.println("Electricity Bill: ₹" + billAmount);
            // Example Output: Electricity Bill: ₹450.0

        }

        // Calculate the bill for units between 201 and 300.
        else if (units <= 300) {

            // Calculate the bill amount.
            billAmount = units * 4.00;

            // Display the total bill amount.
            System.out.println("Electricity Bill: ₹" + billAmount);
            // Example Output: Electricity Bill: ₹960.0

        }

        // Calculate the bill for units above 300.
        else {

            // Calculate the bill amount.
            billAmount = units * 6.00;

            // Display the total bill amount.
            System.out.println("Electricity Bill: ₹" + billAmount);
            // Example Output: Electricity Bill: ₹2400.0

        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
