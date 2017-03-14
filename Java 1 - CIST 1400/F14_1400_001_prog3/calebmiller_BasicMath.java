//Name : Caleb Miller
//Class: 1400-001
//Program 3
//Due Date: 9/17/2014
//
//Honor Pledge: On my honor as a student of the University
//              of Nebraska at Omaha, I have neither given 
//              nor received unauthorized help on this 
//              homework assignment.
//
//  Caleb Miller
//  calebmiller@unomaha.edu
//  616
//
// Colleagues: none
//  This program is made for the user to input numbers,
// and the program will perform multiple math functions
// and output the result

//  Testing: program was compiled and run with no errors

import java.util.Scanner;


public class calebmiller_BasicMath {
    public static void main( String args[] ) {
       Scanner input = new Scanner (System.in);
       
       System.out.print ( "Enter A: ");
        int A = input.nextInt();

        System.out.print ( "Enter B: ");
        int B = input.nextInt();

        System.out.print ( "Enter C: ");
        int C = input.nextInt();

        System.out.printf( "ADD: %d\n", A + B + C );
        System.out.printf( "DIV: %d\n", A / B );
        System.out.printf( "MOD: %d\n", A % C );
        System.out.printf( "PRO: %d\n", A * B * C );
        System.out.printf( "DIF: %d\n", B - C);

    }
}
