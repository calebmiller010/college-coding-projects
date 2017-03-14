import java.util.Scanner;

public class labExercise13 {
    public static void main ( String args [] ) {
        Stopwatch time = new Stopwatch();
        time.start();
        Scanner in = new Scanner (System.in);
        System.out.println ( "Stopwatch Started" );
        int t = 0;
        char input = 'a';
        do {
            System.out.print ( "Enter q to quit, any other to check: \n" );
            input = in.next().charAt(0);
            time.printTime();
            


        } while ( input != 'q' );
    }
}
