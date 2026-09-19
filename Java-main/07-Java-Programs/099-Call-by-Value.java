/*
 * ============================================================================
 * Program Name : Call by Value
 * File Name    : 099-Call-by-Value.java
 * Class Name   : CallByValue
 *
 * Description:
 * This program demonstrates the Call by Value concept in Java.
 * Java always passes arguments by value. Changes made to the
 * method parameters do not affect the original variables.
 *
 * Objective:
 * - Understand the Call by Value concept.
 * - Learn how method arguments are passed in Java.
 * - Observe that original variable values remain unchanged.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class CallByValue {

    // User-defined method to demonstrate Call by Value.
    public void modifyValue(int number) {

        // Display the received value.
        System.out.println("Value Inside Method (Before Modification): " + number);

        // Modify the local copy of the value.
        number = number + 50;

        // Display the modified value.
        System.out.println("Value Inside Method (After Modification) : " + number);

    }

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create an object of the current class.
        CallByValue object = new CallByValue();

        // Declare and initialize a variable.
        int number = 100;

        // Display the value before calling the method.
        System.out.println("Value Before Method Call : " + number);

        // Call the method by passing the variable.
        object.modifyValue(number);

        // Display the value after the method call.
        System.out.println("Value After Method Call  : " + number);

        // Example Output:
        // Value Before Method Call : 100
        // Value Inside Method (Before Modification): 100
        // Value Inside Method (After Modification) : 150
        // Value After Method Call  : 100
    }
}
