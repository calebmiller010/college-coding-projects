// Name    : Caleb Miller
// Class    : 1400-001 
// Program 8
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
// Program Description: Program is designed for user to say how many characters to
// convert, then enter in their characters to convert to Leet symbol
// 
// Testing :  program was run and works correctly



import java.util.Scanner;

public class calebmiller_LeetLoop {
    public static void main ( String args [] ) {
        Scanner in = new Scanner (System.in);
        System.out.print ( "How many characters would you like to convert? ");
        int num = in.nextInt();
        while ( num <= 0 ) {
            System.out.print ( "How many characters would you like to convert? ");
            num = in.nextInt();
        }
        
        int counter = 1;

        while ( counter <= num ) {

        System.out.printf ( "Enter character #%d to convert: ", counter );
        char inputchar = in.next().charAt(0);
        String conversion;
        switch (inputchar) {
            case 'A': case 'a':
                conversion = "@";
                break;
            case 'B': case 'b':
                conversion = "I3";
                break;
            case 'C': case 'c':
                conversion = "<";
                break;
            case 'D': case 'd':
                conversion = "[)";
                break;
            case 'E': case 'e':
                conversion = "&";
                break;
            case 'F': case 'f':
                conversion = "]=";
                break;
            case 'G': case 'g':
                conversion = "6";
                break;
            case 'H': case 'h':
                conversion = "]-[";
                break;
            case 'I': case 'i':
                conversion = "1";
                break;
            case 'J': case 'j':
                conversion = "_/";
                break;
            case 'K': case 'k':
                conversion = "X";
                break;
            case 'L': case 'l':
                conversion = "7";
                break;
            case 'M': case 'm':
                conversion = "/\\/\\";
                break;
            case 'N': case 'n':
                conversion = "|\\|";
                break;
            case 'O': case 'o':
                conversion = "()";
                break;
            case 'P': case 'p':
                conversion = "|*";
                break;
            case 'Q': case 'q':
                conversion = "0,";
                break;
            case 'R': case 'r':
                conversion = "I2";
                break;
            case 'S': case 's':
                conversion = "$";
                break;
            case 'T': case 't':
                conversion = "+";
                break;
            case 'U': case 'u':
                conversion = "(_)";
                break;
            case 'V': case 'v':
                conversion = "\\/";
                break;
            case 'W': case 'w':
                conversion = "vv";
                break;
            case 'X': case 'x':
                conversion = "><";
                break;
            case 'Y': case 'y':
                conversion = "j";
                break;
            case 'Z': case 'z':
                conversion = "2";
                break;
            default:
                conversion = "-";
            }
        
    
        counter++;
        System.out.println ( inputchar + " " + conversion );
    }
    }
}
