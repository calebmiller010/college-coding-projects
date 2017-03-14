// Name     : Caleb Miller
// Class    : 1400-001 
// Program 14
// Due Date: Nov. 21
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
// Program Description: This program finds prime numbers using the Sieve
//                      method, and prints "sexy primes" first from a range
//                      of 1 to 50000, then from a range the user inputs.
// 
// Testing :  Program has been compiled, tested, and works properly.

import java.util.Scanner;
public class calebmiller_Sieve
{
     public static void main ( String args[] ) {
          final int HOWMANY = 50000; // The range of numbers

          boolean[] sieve = new boolean[HOWMANY+1]; // Boolean array of true/false
          int lower = 1, upper = HOWMANY; // Setting initial boundaries
          // the following method call will implement the Sieve algorithm
          processSieve( sieve );
          // the following method call will print all 1419 sexy pairs of primes
          showPrimes( sieve, lower, upper );
          // the following method call gets the lower boundary for printing
          lower = getLower( HOWMANY );
          // the following method call gets the upper boundary for printing
          upper = getUpper( lower, HOWMANY );
          // the following method call prints sexy pairs in the new lower-upper range
          showPrimes( sieve, lower, upper );
          }
      public static void processSieve ( boolean[] sieve ) {
          for ( int x=0; x < sieve.length; x++ ) 
              sieve[x]=true;
          sieve[1]=false;
          sieve[0]=false;
          for ( int y=2; y*y <= sieve.length; y++ ) {
              if (sieve[y]) {
                  for ( int a=y; a*y<sieve.length; a++ )
                      sieve[a*y]=false;
              }
          }
      }
      // implement method processSieve here
      public static int getLower ( int x ) {
          Scanner in = new Scanner (System.in);
          int low = 1;
              do {
                  System.out.print ( "Please enter the lower boundary (between 1 and 50000) : " );
                  low = in.nextInt();
              } while (low<1 || low>x);
          return low;
      }

      public static int getUpper ( int low, int x ) {
         Scanner in = new Scanner (System.in);
         int up = 50000;
            do {
                  System.out.print ( "\nPlease enter the upper boundary (between 1 and 50000) : " );
                  up = in.nextInt();
              } while (up<=low || up>x );
         return up;
      }
                
      public static void showPrimes ( boolean[] number, int low, int up ) {
          int count = 0;
          System.out.printf ( "\nHere are all of the sexy prime pairs in the range %d to %d, one pair per line: \n", low, up);
          for ( int x = low; x < (up-6); x++ ) {
              if (number[x] && number[x+6]) {
                  System.out.printf ( "%d and %d\n", x, x+6 );
                  count++;
              }
          }
          System.out.printf ( "There were %d sexy prime pairs displayed.\n\n", count );
      }

    
    
      // implement method showPrimes here
      // implement method getLower here
      // implement method getUpper here
}
