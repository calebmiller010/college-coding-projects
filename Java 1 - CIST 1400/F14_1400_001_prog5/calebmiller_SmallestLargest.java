// Name    : Caleb Miller
// Class    : 1400-001 
// Program 5
// Due Date: 10/1/2014
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
// Program Description: This program compares multiple integers the user inputs
//                      and print what the smallest and largest numbers are.
// Testing : 


import java.util.Scanner;

public class calebmiller_SmallestLargest {
    public static void main ( String args [] ) {
        Scanner in = new Scanner (System.in);
        int counter = 1;
        int input;
        int max = -2147483647;
        int min = 2147483647;
        while ( counter <= 5)  {
            switch ( counter ) {
            case 1: System.out.print ( "Please enter first number: ");
                    break;
            case 2: System.out.print ( "Please enter second number: ");
                    break;
            case 3: System.out.print ( "Please enter third number: ");
                    break;
            case 4: System.out.print ( "Please enter fourth number: ");
                    break;
            case 5: System.out.print ( "Please enter fifth number: ");
                    break; }
        input = in.nextInt();
        if (input >= max)
            max = input;
        if (input <= min)
            min = input;
        counter++; //counter=counter+1
    }
        System.out.printf ( "%d:%d \n", min, max );
    }
} 
