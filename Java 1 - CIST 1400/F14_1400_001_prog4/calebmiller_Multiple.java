// Name    : Caleb Miller
// Class    : 1400-001 
// Program 4
// Due Date: 9/17/2014
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
// Program Description: This program is supposed to tell you if a number the
//                      user inputs is a multiple of 11, and if it is even or
//                      odd
// 
// Testing :  Program compiled and was run correctly
//

import java.util.Scanner;

public class calebmiller_Multiple {
    public static void main( String args[] ) {
        Scanner input= new Scanner (System.in);
        System.out.print( "Please enter a non-negative (>=0) integer (whole) number: ");
         
        int x=input.nextInt();
        int rdr = x % 11;
        int eo = x % 2;
        String evenodd="";
        if (eo == 0){
           evenodd ="EVEN";
        }
        else {
          evenodd = "ODD";
        }
            
        if (x > 0) 
        { if (rdr == 0){
            System.out.printf( "%d:MULTIPLE:%s\n", x, evenodd);
        }
            else {
                
                System.out.printf( "%d:NOT:%s\n", x, evenodd );
            }
        }
        else {
            System.out.println( "ERROR" );
              }

    }
}
