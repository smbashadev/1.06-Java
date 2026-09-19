/*
 * ============================================================================
 * Program Name : Instanceof Operator
 * File Name    : 013-Instanceof-Operator.java
 * Class Name   : InstanceofOperator
 *
 * Description:
 * This program demonstrates the use of the instanceof operator in Java.
 * The instanceof operator is used to check whether an object is an
 * instance of a particular class or implements a specific interface.
 * It returns a boolean value (true or false).
 *
 * Syntax:
 * object instanceof ClassName
 *
 * Objective:
 * - Understand the instanceof operator in Java.
 * - Learn how to verify the type of an object.
 * - Display the result of instanceof comparisons.
 *
 * Author      : Shaik Mahaboob Basha
 * Repository  : 01-Core-Java
 * Folder      : 08-Java-Programs
 * ============================================================================
 */

public class InstanceofOperator {

    // The main() method is the entry point of every Java application.
    public static void main(String[] args) {

        // Create a String object.
        String message = "Welcome to Core Java";

        // Check whether the object is an instance of the String class.
        boolean isString = message instanceof String;

        // Check whether the object is an instance of the Object class.
        boolean isObject = message instanceof Object;

        // Print the object value.
        System.out.println("Message : " + message);
        // Output: Message : Welcome to Core Java

        // Print the result of the String check.
        System.out.println("message instanceof String : " + isString);
        // Output: message instanceof String : true

        // Print the result of the Object check.
        System.out.println("message instanceof Object : " + isObject);
        // Output: message instanceof Object : true

        // Create a generic Object reference that stores a String object.
        Object data = "Java Programming";

        // Check whether the Object reference contains a String object.
        System.out.println("data instanceof String : " + (data instanceof String));
        // Output: data instanceof String : true

        // Check whether the Object reference contains an Integer object.
        System.out.println("data instanceof Integer : " + (data instanceof Integer));
        // Output: data instanceof Integer : false
    }
}
