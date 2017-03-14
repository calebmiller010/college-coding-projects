// Name    : Caleb Miller
// Class    : 1400-001 
// Program 7
// Due Date: 10/15/2014
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
// Program Description: This program converts decimal values into octal values
// 
// Testing :  program was tested and works properly

import java.util.Scanner;

public class calebmiller_OctalConversion {
    public static void main ( String args[] ) {
        Scanner in = new Scanner (System.in);
        int dec=0, control=0;
        String oct = "";
        System.out.print ("Enter a number to convert: ");
        dec = in.nextInt();
        if (dec > 2097151 || dec < 0 ) 
            System.out.print ( "UNABLE TO CONVERT" );
        else {
            while ( control<7 ) {
                oct = "" +(dec % 8) + oct;
                dec /= 8;
                control++;
            }
            System.out.println(oct);
        }
    }
}
