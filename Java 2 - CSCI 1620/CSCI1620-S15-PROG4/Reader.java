// Caleb Miller
// Program 4
// Due Date: April 14, 2014
//
// Honor Pledge:  On my honor as a student of the University
//                of Nebraska at Omaha, I have neither given nor received
//                unauthorized help on this homework assignment.
//
// Partners:    none
//
//
// Description: this program uses the Scanner object to read from a text file,
//              and display the information read from it.

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Reader {
    public static void main (String args[]) {
        try {
            Scanner input = new Scanner(new File("decords.txt"));
            while (input.hasNext()) {
            System.out.println("Name: "+input.next()+" " +input.next());
            System.out.println("Age: " +input.next());
            System.out.println("Wage: "+ input.next());
            System.out.println();
            }
            input.close();
        } catch (FileNotFoundException FNFE) {
            System.err.println("Error opening file: "+FNFE.getMessage());
            System.exit(1);
        }
    }
}

