// Name    : Caleb Miller
// Class    : 1400-001 
// Program 13
// Due Date: Nov 12 2014
// 
// Honor Pledge:  On my honor as a student of the University 
// of Nebraska at Omaha, I have neither given nor received 
// unauthorized help on this homework assignment. 
// 
// Caleb Miller
// calebmiller@unomaha.edu
// 616
//
// Colleagues : none;
//
// Program Description: This program randomly selects how many birthdays are
//                      on a certain day based on a group of 18,975 people.
// 
// Testing : program was tested, and it works and compiles correctly 

import java.util.Random;
public class calebmiller_Birthdays {
    public static void main ( String args[] ) {
        int[] total = new int[365];
        Random randnum = new Random();
        int randValue;
        int high=1, low=100;
        for ( int count = 1; count <= 18975; count++ ) {
            int x = randnum.nextInt(365)+1;
            total[x-1] += 1;
        }
        for ( int a = 1; a <=365; a++ ) {
            System.out.printf ( "Day %d : %d\n", a, total[a-1] );
            if (total[a-1] > high )
                high = total[a-1];
            }
            System.out.printf ( "\nThe following day(s) have %d people:\n", high );
        for ( int b = 1; b <= 365; b++) {
            if (total[b-1] == high)
                System.out.printf ( "%d ", b );
        }
        System.out.printf ( "\n\n" );
        for (int c = 1; c<= 365; c++) {
            if (total[c-1] < low)
                low = total[c-1];
        }
            System.out.printf ( "The following day(s) have %d people:\n", low );
        for ( int y = 1; y <= 365; y++ ) {
            if (total[y-1] == low)
                System.out.printf ( "%d ", y );
        }
        System.out.printf ( "\n\n" );
            

                
         
    }
}

