// Name    : Caleb Miller
// Class    : 1400-001 
// Program 16
// Due Date: Dec. 10 2014
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
// Program Description: This program tests whether  the set of numbers the 
//                      user inputs is a valid sudoku table. This class
//                      uses the methods called from calebmiller_SudokuCheckerTest.java
// 
// Testing : Program has been compiled and tested and works properly

import java.util.Scanner;
public class calebmiller_SudokuChecker {
    private int[][] grid = new int[4][4];

    public void getGrid() {
        Scanner in = new Scanner (System.in);
        for ( int r = 0; r < grid.length; r++ ) {
            System.out.printf ( "Enter Row %d: ", r+1 );
            for ( int c = 0; c < grid[r].length; c++ )
                grid[r][c] = in.nextInt();
        }
    }
    public void displayGrid() {
        System.out.print ( "Here is the grid as I see it now:\n" );
        for ( int r = 0; r < grid.length; r++ ) {
            System.out.print ( "\n\t" );
            for ( int c = 0; c < grid[r].length; c++ )
                System.out.printf ( "%d  ", grid[r][c] );
        }
        System.out.println ( "\n" );
    }
    public void checkGrid() {
        int x =1;
        boolean check = true;
        System.out.print ( "\n" );
        for ( int r = 0; r < grid.length; r+=2 ) {
            for ( int c = 0; c < grid[r].length; c+=2 ) {
                System.out.printf ( "REG-%d:", x);
                if (grid[c][r]+grid[c][r+1]+grid[c+1][r]+grid[c+1][r+1] == 10)
                    System.out.println ( "GOOD" );
                else {
                    System.out.println ( "BAD" );
                    check = false;
                }
                x++;
            }
        }
        System.out.print ( "\n" );
        for ( int y = 0; y < grid.length; y++ ) {
            System.out.printf ( "ROW-%d:", y);
            if (grid[y][1]+grid[y][2]+grid[y][3]+grid[y][0] == 10)
                System.out.println ( "GOOD" );
            else {
                System.out.println ( "BAD" );
                check = false;
            }
        }
            System.out.print ( "\n" );
        for ( int a = 0; a < grid[0].length; a++ ) {
            System.out.printf ( "COL-%d:", a+1);
            if (grid[0][a]+grid[1][a]+grid[2][a]+grid[3][a] == 10)
                System.out.println ( "GOOD" );
            else {
                System.out.println ( "BAD" );
                check = false;
            }
        }
        if (check)
            System.out.println ( "\nSUDO:VALID\n" );
        else
            System.out.println ( "\nSUDO:INVALID\n" );
    }
}

