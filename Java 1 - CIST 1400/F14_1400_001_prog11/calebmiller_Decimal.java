// Name    : Caleb Miller
// Class    : 1400-001 
// Program 11
// Due Date: 29 Oct 2014
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
// Program Description: This program converts Octal numbers to Decimal numbers
//                      using multiple methods
// 
// Testing : I tested the program using all the examples in the program and 
//           all my answers were correct.



import java.util.Scanner;

public class calebmiller_Decimal {
    public static void main ( String args[] ) {
        Scanner sc = new Scanner ( System.in );
        int foo = -1;
        while ( foo < 0 || foo > 77777777 ) {
        System.out.print ("Enter up to an 8-digit octal number and I'll convert it: ");
        foo = sc.nextInt();
        }
        System.out.printf ( "%d:%d\n", foo, convert(foo));
    }
    public static int convert ( int octalNumber ) {
    int temp = octalNumber;
    int digits= 0;
    while (temp != 0 ) {
        temp /= 10;
        digits++; 
    }
    int nextDigit, multiply, total = 0;
    for ( int x = 0; x <= digits; x++ ) {
        nextDigit = octalNumber % 10;
        multiply = nextDigit * ((int)Math.pow ( 8, x ));
        total += multiply;
        octalNumber /= 10;
    }
    return total;
    }
    
}
