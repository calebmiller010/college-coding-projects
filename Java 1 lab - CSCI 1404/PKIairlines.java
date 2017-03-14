import java.util.Scanner;
public class PKIairlines {
    public static void main ( String args[] ) {
        String [][] flight = new String[4][5];
        int choice = 1;
        emptySeats( flight );
        while (choice != 5) {
        printMenu();
        choice = getChoice();
        switch (choice) {
            case 1: case 2: case 3: reserveSeats( choice, flight );
                          break;
            case 4: printChart( flight );
                    break;
            }
        }


    }   
    public static void reserveSeats ( int num, String flight[][] ) {
        int x = 1;
        while ( flight[num][x]== "Taken" ) {
            x++;
        if ( x == flight[num].length )
            break;
        }
        if (x > 4) {
            System.out.printf ( "Row %d is full\n\n", num );}
        else {
            flight[num][x] = "Taken";
            System.out.printf ( "You will be flying in row %d, seat %d\nThank you for your reservation\n\n", num, x );
        }
    }
        

            
    public static void printMenu () {
        System.out.print ( "PKI Airlines Menu\n1.   Reserve a seat in row 1\n2.   Reserve a seat in row 2\n3.   Reserve a seat in row 3\n4.   Print seating chart\n5.   Quit\n" );
    }
    public static int getChoice () {
        Scanner in = new Scanner (System.in);
        int a = 0;
        while ( a < 1 || a > 5 )
        a = in.nextInt();
        return a;
    }
    public static void emptySeats ( String array[][] ) {
        for ( int r = 1; r < array.length; r++ )
        {
        for ( int c = 1; c < array[r].length; c++ )
            array[r][c] = "Avail";
        }
    }
    public static void printChart ( String seats[][] ) {
        for ( int i = 1; i < seats.length; i++ ) {
            System.out.println();
        for (int j = 1; j < seats[i].length; j++) 
            System.out.print ( seats[i][j] + "  " );
        System.out.println( "\n" );

        }
    }
}
