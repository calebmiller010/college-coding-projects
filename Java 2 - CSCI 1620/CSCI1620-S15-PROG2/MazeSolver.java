// Caleb Miller
// Program 2
// Due Date: 2/24/2015
//
// Honor Pledge:  On my honor as a student of the University
//                of Nebraska at Omaha, I have neither given nor received
//                unauthorized help on this homework assignment.
//
// Partners:    no partners; I received help from a tutor on February 24th at about 12:30, who explained to me how you can assign boolean variables even by
//              using "or" statements, as you implement recursion to find when it returns true. 
//
//
// Desc.:   This program uses recursion to find the solution to a maze.
//
/*

   1. Recursion is basically processing successive repitition of a method calling itself until the intended condition is met, and then it uses the information that it has "stored" in the previous repitiions on the way "back down". The Base Case is the condition that when met, does not require the method to call itself again. The General Case is the case that is used when the base case isn't met. It is used to get the recursive process 'closer' to the base case, by continuing the "process", by a method call (itself, or another).

    2.The program as given to us does not have any recursion involved in it. The recursion happens with boolean result = ____. This is where we need to call the method solve_maze (its own method) to implement recursion. To determine which direction it needs to go, we have it call itself 4 times, separated by an "or" to see which of the directions lead to an acceptable path. If the path it tried returns with false, then it tries the next one and the next one until it comes back with true. This makes up the recursive portion of the program.

    3.The Base Case in this program occurs in the 2nd line of the solve_maze method. It is triggered when the program finds the end of the maze. Then it returns true to the previous call stack, and goes back through all the call stacks, replacing the ? marks with periods. All the call stacks below the one where the end was found return true to the stack before it, which solves the maze. And finally, once it backtracks through its path back to row 0 col 0, all the spaces are filled to show the solution to the path.
 
*/

import java.util.Scanner;
public class MazeSolver {
    private static Scanner in = new Scanner(System.in);
    private static char[][] maze = {
        {' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#'},
        {'#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', ' ', ' ', ' ', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', '#'},
        {'#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#'},
        {'#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', '#'},
        {'#', ' ', '#', ' ', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', ' ', '#', '#'},
        {'#', ' ', '#', ' ', '#', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', ' ', ' ', '#', ' ', '#', '#'},
        {'#', ' ', '#', ' ', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#'},
        {'#', ' ', '#', ' ', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#'},
        {'#', ' ', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#'},
        {'#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', ' ', ' '},
        {'#', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#'},
        {'#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
    };
    public static void main (String args[]) {
        System.out.println("\fSimple Maze Solver");
        while (true) {
            System.out.print("\nMake a choice (p=print maze, s=solve maze, q=quit): ");
            char choice = in.next().charAt(0);
            System.out.println();
            if (choice == 'p') {
                System.out.println("Printing Maze (Unsolved)");
                print_maze(maze);
            } else if (choice == 's') {
                System.out.println("Solving Maze");
                char[][] temp_maze = new char[maze.length][maze[0].length];
                for (int row=0; row<maze.length; row++)
                    for (int col=0; col<maze[row].length; col++)
                        temp_maze[row][col] = maze[row][col];
                if (solve_maze(temp_maze, 0, 0)) print_maze(temp_maze);
                else System.out.println("Maze is unsolvable.");
            } else if (choice == 'q') {
                System.out.println("Exiting...\n");
                break;
            } else System.out.println("Invalid Selection");
        }
    }
    private static void print_maze (char[][] m) {
        for (int row=0; row<m.length; row++) {
            for (int col=0; col<m[row].length; col++)
                System.out.print(m[row][col]);
            System.out.println();
        }
    }
    private static boolean solve_maze (char[][] m, int row, int col) {
        if (row < 0 || col < 0 || row >= m.length || col >= m[row].length || m[row][col] != ' ') return false;
        else if (row == m.length - 1 || col == m[row].length - 1) { m[row][col] = '.'; return true; }
        m[row][col] = '?';
        boolean result = solve_maze(m, row-1, col)|solve_maze(m, row+1, col)|solve_maze(m,row, col-1)|solve_maze(m,row,col+1);
        if (!result) m[row][col] = '#';
        else m[row][col] = '.';
        if (row == 0 && col == 0)
            for (int r=0; r<m.length; r++)
                for (int c=0; c<m[r].length; c++)
                    if (m[r][c] == ' ') m[r][c] = '#';
        return result;
    }
}

