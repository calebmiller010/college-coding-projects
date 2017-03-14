// Name    : Caleb Miller
// Class    : 1400-001 
// Program 10
// Due Date: October 24th
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
// Program Description: This program creates a numerology report based on a date
//                      the user enters.
// Testing :  

import java.util.Scanner;

public class calebmiller_Numerology {
    public static void main ( String args [] ) {
        Scanner in = new Scanner (System.in);
        int finalresult=0;
        int x = 0;
        int month= 0;
        int day=0;
        int year=0;
        char char1;
        char char2;
        int total=0;
        int nextnum = 0;
        int latestnum = 0;

        while ( x == 0 ) {
            System.out.print ( "Enter the birth date ( mm / dd / yyyy ): " );
            month = in.nextInt();
            char1 = in.next().charAt(0);
            day = in.nextInt();
            char2 = in.next().charAt(0);
            year = in.nextInt();
            x = 1;
                if (char1 != '/') {
                    System.out.println ( "Use forward slashes between mm / dd / yyyy!" );
                    x = 0;
                    continue;
                }
                if (char2 != '/') {
                    System.out.println ( "Use forward slashes between mm / dd / yyyy!" );
                    x = 0;
                    continue;
                }
                if ( year < 1880 || year > 2280)  {
                    System.out.println ( "Invalid year: must be between 1880 and 2280" );
                    x=0;
                    continue;
                }

            switch ( month ) {
                        case 1: if ( day < 1 || day > 31 ) {
                                    System.out.println ( "Bad day: " + day ); 
                        
                         x = 0;}
                         break;

                         case 2:
                         int feb = 28;
                         if ( year % 4 == 0 )
                             feb = 29;
                         if ( year % 400 == 0)
                             feb = 28;
                         if ( day < 1 || day > feb ) {
                             System.out.println ( "Bad day: " + day );
                        
                        x=0;}
                        break;

                        case 3: if ( day < 1 || day > 31 ) {
                                    System.out.println ( "Bad day: " + day );
                        
                        x=0;}
                        break;

                        case 4: if ( day < 1 || day > 30 ) {
                                    System.out.println ( "Bad day: " + day );
                        
                        x=0;}
                        break;

                        case 5: if ( day < 1 || day > 31 ) {
                                    System.out.println ( "Bad day: " + day );
                        
                        x=0;}
                        break;

                        case 6: if ( day < 1 || day > 30 ) {
                                    System.out.println ( "Bad day: " + day );
                        
                        x=0;}
                        break;

                        case 7: if ( day < 1 || day > 31 ) {
                                    System.out.println ( "Bad day: " + day );
                        
                        x=0;}
                        break;

                        case 8: if ( day < 1 || day > 31 ) {
                                    System.out.println ( "Bad day: " + day );
                        
                        x=0;}
                        break;

                        case 9: if ( day < 1 || day > 30 ) {
                                    System.out.println ( "Bad day: " + day );
                        
                        x=0;}
                        break;

                        case 10: if ( day < 1 || day > 31 ) {
                                     System.out.println ( "Bad day: " + day );
                        
                        x=0;}
                        break;

                        case 11: if ( day < 1 || day > 30 ) {
                                     System.out.println ( "Bad day: " + day );
                        
                        x=0;}
                        break;

                        case 12:if ( day < 1 || day > 31 ) {                                           
                                    System.out.println ( "Bad day: " + day );
                        
                        x=0;}
                        break;

                        default: {System.out.println ( "Bad month: " + month );
                                     x = 0;
                                    }
            }
        
        }

            total = month + day + year;

            do { 

                latestnum = 0;
                while ( total > 9 ) {
                    latestnum = latestnum + ( total % 10 );
                    total /= 10;
                }
                latestnum += total;
                total = latestnum;
            } while ( latestnum > 9 );

            System.out.println ( "Welcome to the numerology report for " + month + " / " + day + " / " + year + ":" );

            switch ( latestnum ) {
                case 1: System.out.println ( "=1= First is the worst." );
                        break;
                case 2: System.out.println ( "=2= Second is the best." );
                        break;
                case 3: System.out.println ( "=3= Third is the nerd with the polka dot dress." );
                        break;
                case 4: System.out.println ( "=4= Fourth is the one with a hairy chest." );
                        break;
                case 5: System.out.println ( "=5= Fifth is the one who needs to impress." );
                        break;
                case 6: System.out.println ( "=6= Sixth is the one who's an ugly princess." );
                        break;
                case 7: System.out.println ( "=7= Seventh is the one in the stupid vest." );
                        break;
                case 8: System.out.println ( "=8= Eighth is the ape who makes a mess." );
                        break;
                case 9: System.out.println ( "=9= Ninth is the one that is worth much less." );
                        break;

        }} 
    }
