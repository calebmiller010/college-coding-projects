// Name    : Caleb Miller
// Class    : 1400-001 
// Program 12
// Due Date: Nov 5
// 
// Honor Pledge:  On my honor as a student of the University 
// of Nebraska at Omaha, I have neither given nor received 
// unauthorized help on this homework assignment. 
// 
// Caleb Miller
// calebmiller@unomaha.edu
// 616
//
// Colleagues : none
//
// Program Description: This program decides whether a number is "perfect" and 
//                      if so, it outputs its factors.
// 
// Testing : program compiles and works correctly 

import java.util.Scanner;

public class calebmiller_Perfect {
    public static void main ( String args[] ) {
        Scanner in = new Scanner (System.in);
        int nums = 1;
        do {
        System.out.print ( "How many numbers would you like to test? " );
        nums = in.nextInt();
        } while ( nums <= 0 );

        for ( int x = 1; x <= nums; x++ ) {
            System.out.print ( "Please enter a possible perfect number: " );
            int test = in.nextInt();
            
            if ( testPerfect(test) == true )
                printFactors(test);
            else
                System.out.print ( test + ":NOT PERFECT" );
            System.out.println ( "" );
        }
    }
    public static boolean testPerfect ( int x ) {
        int total = 0;
        boolean tf=false;
        int nextnum = 0;
        for ( int count = ( x / 2 ); count >= 1; count-- ) {
            if ((x % count ) == 0 ) 
                total += count;
        }
        if (total == x )
            tf = true;
        return tf;
    }
    public static void printFactors ( int x ) {
        int nextnum = 0;
        System.out.print ( x + ":" );
        for ( int count = ( x / 2 ); count >= 1; count -- ) {
            if (( x % count ) == 0 )
                System.out.print ( count + " " );
        }
    }
}
