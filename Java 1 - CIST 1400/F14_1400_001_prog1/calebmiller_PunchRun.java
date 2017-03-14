//Name      :Caleb Miller
//Class     :1400-001
//Program#  :1
//Due Date  :September 10 2014 6:00 PM
//
//Honor Pledge: On my honor as a student of the University
//              of Nebraska at Omaha, I have neither given nor received
//              unatuthorized help on this homework assignment.
//
//NAME: Caleb Miller
//EMAIL: calebmiller@unomaha.edu
//NUID: 616

//Colleagues: none

//It looks like the program compares 2 integers and decides whether
//the first integer is greater than, less than, or equal to the 
//second integer

//Testing   :Properly compiled and ran the java program


import java.util.Scanner;

public class calebmiller_PunchRun
{
    public static void main ( String args[] )
    {
        Scanner input = new Scanner ( System.in );
        int number1, number2;

        System.out.print ( "Enter first integer: ");
        number1 = input.nextInt();

        System.out.print ( "Enter second integer: ");
        number2=input.nextInt();

        if (number1 == number2)
            System.out.printf( "%d == %d\n", number1, number2 );

        if (number1 != number2)
            System.out.printf( "%d != %d\n", number1, number2 );

        if (number1 < number2)
            System.out.printf( "%d < %d\n", number1, number2 );

        if (number1 > number2)
            System.out.printf( "%d > %d\n", number1, number2 );

        if (number1 <= number2)
            System.out.printf( "%d <= %d\n", number1, number2 );

        if (number1 >= number2)
            System.out.printf( "%d >= %d\n", number1, number2 );
    }
}
