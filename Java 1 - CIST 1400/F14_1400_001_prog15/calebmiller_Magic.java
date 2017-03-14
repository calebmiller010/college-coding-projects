// Name    : Caleb Miller
// Class    : 1400-001 
// Program 15
// Due Date: Nov 24 2014
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
// Program Description: This program determines whether a square is a magic square.
// 
// Testing : program tested and compiles correctly.
import java.util.Scanner;

public class calebmiller_Magic {
    public static void main ( String[] args ) {
        Scanner in = new Scanner (System.in);
        int[][] square = new int[4][4];
        System.out.print ( "\nThe magic value for your square is 34, which means that every row,\ncolumn and diagonal of your square must add up to that number.\n" );
        System.out.println ();
        for ( int r = 0; r < square.length; r++ ) {
            System.out.printf ( "Please enter the 4 values for row %d, separated by spaces: ", r );
            for ( int c = 0; c < square[r].length; c++ )
                square[r][c] = in.nextInt();
        }
        System.out.println ( "\nChecking square for problems: " );
        boolean check = true;
        check = checkDiagonals( square, check );
        check = checkRows( square, check );
        check = checkColumns( square, check );
        boolean [][] used = new boolean[4][4];// This boolean array is to prevent printing numbers under "RANG" more than once
        for ( int r = 0; r < square.length; r++ ) {
            for ( int c = 0; c < square[r].length; c++ ) 
                used[r][c]=false;
        }

        check = checkRang ( square, used, check );
        if (check)
            System.out.println ( "MAGIC: YES" );
        else
            System.out.println ( "MAGIC: NO" );


    }   // End of main class
    public static boolean checkRang ( int square[][], boolean used[][], boolean check ) {
        System.out.print ( "RANG: " );
        boolean localcheck = true;
        for ( int r = 0; r < square.length; r++ ) {
            for ( int c = 0; c < square[r].length; c++ ) {
                for ( int x = r; x < square.length; x++ ) {
                    for ( int y = c+1; y < square[x].length; y++ ) {
                        if ( (square[r][c]==square[x][y]) || (square[r][c]<1||square[r][c]>16) ) {
                            used[r][c]=true;
                            check = false;
                            localcheck= false;
                        }
                    }

                }
            }
        }
        for ( int i = 0; i < square.length; i++ ) {
            for ( int j = 0; j < square[i].length; j++ ) {
                if (used[i][j])
                    System.out.print ( square[i][j] + " " );
            }
        }
        if (localcheck)
            System.out.print ( "VALID" );
        System.out.println ();

        return check;

    }
    public static boolean checkRows ( int square[][], boolean check ) {
        int total =  0;
        System.out.print ( "ROWS: " );
        for ( int r = 0; r < square.length; r++ ) {
            total = 0;
            for ( int c = 0; c < square[r].length; c++ ) {
                total+=square[r][c];
            }
            if (total!=34) {
                System.out.print ( r + " " );
                check = false;
            } 
        }
        if (check)
            System.out.print ( "VALID" );
        System.out.println ();
        return check;
    }
    public static boolean checkColumns ( int square[][], boolean check ) {
        int total =  0;
        System.out.print ( "COLS: " );
        for ( int c = 0; c < square.length; c++ ) {
            total = 0;
            for ( int r = 0; r < square[c].length; r++ ) {
                total+=square[r][c];
            }
            if (total!=34) {
                System.out.print ( c + " " );
                check = false;
            } 
        }
        if (check)
            System.out.print ( "VALID" );
        System.out.println ();
        return check;
    }
    public static boolean checkDiagonals ( int square[][], boolean check ) {
        int total1=0, total2 =  0;
        System.out.print ( "DIAG: " );
        for ( int x = 0; x < square.length; x++ ) 
            total1+=square[x][x];
        if (total1!=34) {
            System.out.print ( "0 " );
            check = false;
        }
        int col = 0;
        for ( int row = 0; row < square.length; row++ ) {
            col = Math.abs( row-3 );
            total2+=square[row][col];
        }
        if (total2!=34) {
            System.out.print ( "1 " );
            check = false;

        }
        if (check)
            System.out.print ( "VALID" );
        System.out.println ();
        return check;

    }




}
