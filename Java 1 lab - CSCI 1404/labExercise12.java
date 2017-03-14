/**
 * @author      Joshua Nelson <joshuanelson@unomaha.edu>
 * @version     1.0
 * @since       2013-11-20
 */


import java.util.Scanner;

public class labExercise12
{
    public static void main( String args[] )
    {
        int rows, cols;

        Scanner in = new Scanner( System.in );

        System.out.println();
        System.out.print( "Enter number of columns: " );
        cols = in.nextInt();
        System.out.print( "Enter number of rows: " );
        rows = in.nextInt();

        int[][] multTable = new int[rows+1][cols+1];

        fillTable( multTable );

        System.out.println( "\nHere's your multiplication table\n" );

        printTable( multTable );

    }

    /**
     * Fills table with multiplication values (row+1)*(col+1)
     *
     * @param table     the table to be filled
     */
    public static void fillTable( int[][] table )
    {
        for (int r = 1; r < table.length; r++ ) {
            for ( int c = 1; c < table[r].length; c++ )
                table[r][c] = r*c;
                // Fill in code here to fill the table with the mult values
        }
        }

        /**
         * Prints the table
         * 
         * @param table     the table to be printed
         */
        public static void printTable( int[][] table )
        {   
            System.out.print ( " " );
            for ( int x = 1; x < table.length; x++ )
            System.out.printf ( "      %d", x );
            System.out.print ( "\n--------------------------------------------------------\n");
            for (int r = 1; r < table.length; r++ ) {
                System.out.print ( "\n" + r + "|     " );
                for ( int c = 1; c < table[r].length; c++ )
                    System.out.print ( table[r][c] + "      " );
            }
            System.out.println();// Fill in code here to properly print the table
            // Be sure to get it as close to the sample output as possible

        }
    }
