/*
    Name        : Caleb Miller
    Class       : 3320-002
    Program #   : Assignment 2, Problem 3
    Due Date    : September 15, 2015

    Honor Pledge:  On my honor as a student of the University
               of Nebraska at Omaha, I have neither given nor received
               unauthorized help on this homework assignment.

NAME: Caleb Miller
NUID: 616
EMAIL: calebmiller@unomaha.edu

Partners: None;
Description: This program test whether what the user inputs to the program is
             a palindrome or not. 
*/

import java.util.Scanner;
import java.util.Arrays;//used to compare Arrays
import java.util.Stack;

public class Palindrome {
            
    public static void main (String args[]) {

        System.out.println();    
        
        //get user input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string to test for palindrome: ");
        String input = sc.nextLine();
        input = input.replace( " ", "");
        
        //replace any characters that's not a letter from the string
        for (int x = 0; x<=255; x++) {
            if ((x < 65) || ( x > 90 && x < 97 ) || (x > 122)) {
               String p = "";
               p = p+(char)x; 
               input = input.replace(p,"");
            }
        }
            
        char[] stringArray = input.toCharArray();
        char[] simpArray = new char[stringArray.length];//simpArray is the array used for user input with all lowercase

        //copy over to simpArray, making any uppercase letters lowercase
        for (int i = 0; i < stringArray.length; i++) {

            if ((int)stringArray[i] >= 65 && (int)stringArray[i] <= 90) {
                stringArray[i] = (char)((int)stringArray[i]+32);
                simpArray[i] = stringArray[i];
            }

            if ((int)stringArray[i] >= 97 && (int)stringArray[i] <= 122) 
                simpArray[i] = stringArray[i];
        
        }

        Stack<Character> st = new Stack<Character>();

        //push all the letter in the stack
        for (int c = 0; c < simpArray.length; c++)
            st.push(simpArray[c]);

        char[] newArray = new char[simpArray.length];//newArray is the array that will be the reverse of simpArray. This is where the return of each Stack pop goes into

        for (int d = 0; d < simpArray.length; d++) 
            newArray[d]=(char)st.pop();

        //print out reverse string
        System.out.print( "Reverse string is: ");
        for (int e = 0; e < newArray.length; e++) 
            System.out.print( newArray[e] );

        System.out.println();

        if (Arrays.equals(simpArray, newArray))//from java.util.Arrays; compares the two arrays for equality
            System.out.println( "Input string is a palindrome\n" );
        else
            System.out.println( "Input string is not a palindrome\n" );
    }
}
