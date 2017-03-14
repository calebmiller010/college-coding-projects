// Name    : Caleb Miller
// Class    : 1400-001 
// Program 9 
// Due Date: 10/22/2014  
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
// Program Description: This program calculates the price of shipping based
//                      off user inputs
// 
// Testing :  Program was compiled correctly and worked correctly


import java.util.Scanner;

public class calebmiller_Shipping {
    public static void main ( String args[] ) {
        Scanner in = new Scanner (System.in);
        int ship, weight;
        double price=0;
        int count = 0;

        System.out.print ( "Welcome to the You Send It We Rend It Shipping Company\nHow heavy is your package in pounds (1-60)? " );
        weight = in.nextInt();
        while ( weight < 1 || weight > 60 ) {
            System.out.print ( "How heavy is your package in pounds (1-60)? " );
            weight = in.nextInt();
            count++;
        }
        
        System.out.print ( "How far will you be shipping the package in miles? " );
        ship = in.nextInt();
        while ( ship < 0 ) {
            System.out.print ( "How far will you be shipping the package in miles? " );
            ship = in.nextInt();
        }

        if ( weight >= 1 && weight < 11 ) 
        price = 5.01;
        if ( weight >= 11 && weight < 20 ) 
        price = 7.02;
        if ( weight >= 21 && weight < 30 ) 
        price = 9.03;
        if ( weight >= 31 && weight < 40 ) 
        price = 11.04;
        if ( weight >= 41 && weight <= 60 ) 
        price = 15.00;
        
        int variable=0;
       int multiply=0; 
        if ((ship % 100) == 0) {
            variable = ship - 1;
            multiply = variable/100 + 1;
        }
        else
            multiply = ship/100 + 1;
        
        System.out.printf ( "Your total shipping cost for %d miles is $%.2f\n", ship, price*multiply );
    }
}
