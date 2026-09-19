/*
 * Program: Static Variable in Java
 *
 * Description:
 * ...
 */

public class StaticVariable {

    // Static variable
    static double PI = 3.14159;

    public static void main(String[] args) {

        // Radius of first circle
        double radius1 = 5;

        // Calculate area
        double area1 = PI * radius1 * radius1;

        // Display first circle
        System.out.println("===== Circle 1 =====");
        System.out.println("Radius : " + radius1);
        System.out.println("PI     : " + PI);
        System.out.println("Area   : " + area1);

        System.out.println();

        // Radius of second circle
        double radius2 = 10;

        // Calculate area
        double area2 = PI * radius2 * radius2;

        // Display second circle
        System.out.println("===== Circle 2 =====");
        System.out.println("Radius : " + radius2);
        System.out.println("PI     : " + PI);
        System.out.println("Area   : " + area2);

        System.out.println();

        // Radius of third circle
        double radius3 = 15;

        // Calculate area
        double area3 = PI * radius3 * radius3;

        // Display third circle
        System.out.println("===== Circle 3 =====");
        System.out.println("Radius : " + radius3);
        System.out.println("PI     : " + PI);
        System.out.println("Area   : " + area3);
    }
}

/*
==========================================
Sample Output
==========================================

===== Circle 1 =====
Radius : 5.0
PI     : 3.14159
Area   : 78.53975

===== Circle 2 =====
Radius : 10.0
PI     : 3.14159
Area   : 314.159

===== Circle 3 =====
Radius : 15.0
PI     : 3.14159
Area   : 706.85775

*/
