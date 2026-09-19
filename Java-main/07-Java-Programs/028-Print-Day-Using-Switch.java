/*
 * ============================================================================
 * Program Name : Print Day Using Switch
 * File Name    : 028-Print-Day-Using-Switch.java
 * Class Name   : PrintDayUsingSwitch
 *
 * Description:
 * This program accepts a day number from the user and displays the
 * corresponding day of the week using the switch statement.
 *
 * Objective:
 * - Understand user input using the Scanner class.
 * - Learn how to use the switch statement.
 * - Map numeric values to the corresponding day names.
 *
 * Day Mapping:
 * 1 : Monday
 * 2 : Tuesday
 * 3 : Wednesday
 * 4 : Thursday
 * 5 : Friday
 * 6 : Saturday
 * 7 : Sunday
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

import java.util.Scanner;

public class PrintDayUsingSwitch {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard.
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter a day number.
        System.out.print("Enter Day Number (1-7): ");

        // Read the day number entered by the user.
        int dayNumber = scanner.nextInt();

        // Display the corresponding day using the switch statement.
        switch (dayNumber) {

            // Display Monday.
            case 1:
                System.out.println("Day: Monday");
                // Example Output: Day: Monday
                break;

            // Display Tuesday.
            case 2:
                System.out.println("Day: Tuesday");
                // Example Output: Day: Tuesday
                break;

            // Display Wednesday.
            case 3:
                System.out.println("Day: Wednesday");
                // Example Output: Day: Wednesday
                break;

            // Display Thursday.
            case 4:
                System.out.println("Day: Thursday");
                // Example Output: Day: Thursday
                break;

            // Display Friday.
            case 5:
                System.out.println("Day: Friday");
                // Example Output: Day: Friday
                break;

            // Display Saturday.
            case 6:
                System.out.println("Day: Saturday");
                // Example Output: Day: Saturday
                break;

            // Display Sunday.
            case 7:
                System.out.println("Day: Sunday");
                // Example Output: Day: Sunday
                break;

            // Execute this block for an invalid day number.
            default:
                System.out.println("Invalid Day Number! Please enter a value between 1 and 7.");
                // Example Output: Invalid Day Number! Please enter a value between 1 and 7.
        }

        // Close the Scanner object to release system resources.
        scanner.close();
    }
}
