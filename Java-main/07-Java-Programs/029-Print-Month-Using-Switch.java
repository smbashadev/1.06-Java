/*
 * ============================================================================
 * Program Name : Print Month Using Switch
 * File Name    : 029-Print-Month-Using-Switch.java
 * Class Name   : PrintMonthUsingSwitch
 *
 * Description:
 * This program accepts a month number from the user and displays the
 * corresponding month name using the switch statement.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the switch statement.
 * - Map numeric values to the corresponding month names.
 *
 * Month Mapping:
 * 1  : January
 * 2  : February
 * 3  : March
 * 4  : April
 * 5  : May
 * 6  : June
 * 7  : July
 * 8  : August
 * 9  : September
 * 10 : October
 * 11 : November
 * 12 : December
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class PrintMonthUsingSwitch {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a month number.
        System.out.print("Enter Month Number (1-12): ");

        // Read the month number entered by the user.
        int monthNumber = scanner.nextInt();

        // Display the corresponding month using the switch statement.
        switch (monthNumber) {

            // Display January.
            case 1:
                System.out.println("Month: January");
                // Example Output: Month: January
                break;

            // Display February.
            case 2:
                System.out.println("Month: February");
                // Example Output: Month: February
                break;

            // Display March.
            case 3:
                System.out.println("Month: March");
                // Example Output: Month: March
                break;

            // Display April.
            case 4:
                System.out.println("Month: April");
                // Example Output: Month: April
                break;

            // Display May.
            case 5:
                System.out.println("Month: May");
                // Example Output: Month: May
                break;

            // Display June.
            case 6:
                System.out.println("Month: June");
                // Example Output: Month: June
                break;

            // Display July.
            case 7:
                System.out.println("Month: July");
                // Example Output: Month: July
                break;

            // Display August.
            case 8:
                System.out.println("Month: August");
                // Example Output: Month: August
                break;

            // Display September.
            case 9:
                System.out.println("Month: September");
                // Example Output: Month: September
                break;

            // Display October.
            case 10:
                System.out.println("Month: October");
                // Example Output: Month: October
                break;

            // Display November.
            case 11:
                System.out.println("Month: November");
                // Example Output: Month: November
                break;

            // Display December.
            case 12:
                System.out.println("Month: December");
                // Example Output: Month: December
                break;

            // Execute this block for an invalid month number.
            default:
                System.out.println("Invalid Month Number! Please enter a value between 1 and 12.");
                // Example Output: Invalid Month Number! Please enter a value between 1 and 12.
        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
