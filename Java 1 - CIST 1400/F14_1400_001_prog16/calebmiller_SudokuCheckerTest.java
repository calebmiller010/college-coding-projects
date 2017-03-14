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
// Colleagues : none
//
// Program Description: This program checks to see if the numbers a user enters
//                      is a valid sudoku table. This is the constructor class
//                      that calls the methods from calebmiller_SudokuChecker
// 
// Testing :  program has been compiled and works properly.


public class calebmiller_SudokuCheckerTest
{
     public static void main ( String args[] )
          {
               calebmiller_SudokuChecker grid = new calebmiller_SudokuChecker();
               System.out.print ( "Welcome to the Sudoku Checker v1.0!\n\nThis program checks simple, small, 4x4\nSudoku grids for correctness. Each column,\nrow and 2x2 region contains the numbers 1\nthrough 4 only once.\n\nTo check your Sudoku, enter your board one\nrow at a time, with each digit separated by\na space. Hit ENTER at the end of a row.\n\n" );
               grid.displayGrid(); 
               grid.getGrid();
               grid.displayGrid();
              System.out.println ( "Thank you.  Now checking ..." ); 
               grid.checkGrid();
          }
}

