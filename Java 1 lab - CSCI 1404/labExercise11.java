//=================================================================================
// For this lab exercise, you are expected to fill in the bubbleSort() and
// linearSearch() methods. Feel free to use the slides as reference.
// It is recommended to not change anything outside of those methods. There is a
// lot of code here, and it is possible to damage it beyond repair.
//=================================================================================

import java.util.Scanner;
import java.util.Random;

/**
 * @author      Joshua Nelson <joshuanelson@unomaha.edu>
 * @version     1.0
 * @since       2013-06-18
 */
public class labExercise11
{
    public static void main( String[] args )
    {
        Scanner in = new Scanner( System.in );

        int num;
        int index;
        char choice;

        int[] a = new int[10];
        initArray( a );

        System.out.println();

        System.out.printf(
                "Initialized array:\n" +
                "%s\n\n", 
                arrayToString( a ) );

        bubbleSort( a );

        System.out.printf(
                "Sorted array:\n" +
                "%s\n\n",
                arrayToString( a ) );

        System.out.print( "Please enter a number to search: " );
        num = in.nextInt();

        index = linearSearch( a, num );

        if ( index == -1 )
            System.out.printf( "%d not found.\n\n", num );
        else
            System.out.printf( "%d found at index %d.\n\n", num, index );

        System.out.printf( "Start Linear VS Binary Brawl (Y/N)? " );
        choice = in.next().charAt(0);
        System.out.println();
        if ( choice == 'Y' || choice == 'y' )
        {
            LinearVSBinaryBrawl();
        }
    }

    /**
     * Uses the bubble sort technique to sort the array.
     *
     * @param array     array to be sorted
     */
    public static void bubbleSort( int[] array )
    {
        for ( int out = 0; out < array.length-1; out++ ) {
            for ( int in = 0; in < array.length-1; in++ ) {
                if (array[in] > array[in+1] ) {
                    int temp = array[in];
                    array[in] = array[in+1];
                    array[in+1] = temp;
                }
            }
        }// Fill in bubbleSort algorithim here.

    }

    /**
     * Uses the linear searching technique to find 'key' in 'a'.
     *
     * @param array     array to be searched
     * @param key       key to be found
     *
     * @return          index of the key, -1 if not found
     */
    public static int linearSearch( int[] array, int key )
    {
        int num = -1;
        for (int a = 0; a < array.length; a++ ) {
            if ( array[a]==key )
                num = a;
        }
        return num;
    }
                // Fill in linearSearch algorithm here. Be sure to return the position
        // of the element 'key', and return -1 if it's not found.

    





    //=============================================================================
    // The rest of the code is already filled out for you.
    // Feel free to look through code, but I suggest paying particular attention
    // to the Binary Algorithm. It is not expected of you to fully understand it,
    // as it implements recursive methods, but you should be able to get the
    // general idea behind it.
    //============================================================================





    /**
     * Initializes the elements of array 'a' to random values.
     *
     * @param a         array to be initialized
     */ 
    public static void initArray( int[] a )
    {
        Random r = new Random();

        for ( int i = 0; i < a.length; i++ )
            // let any element be any number 
            // that could also serve as an index
            a[i] = r.nextInt( a.length - 1 );
    }

    /**
     * Converts array 'a' to a string.
     * 
     * @param a         array to be converted
     *
     * @return          converted String
     */
    public static String arrayToString( int[] a )
    {
        String buffer = "[ ";
        for ( int i = 0; i < a.length - 1; i++ )
            buffer += a[i] + ", ";
        buffer += a[a.length-1] + " ]";
        return buffer;
    }

    /**
     * Initiate test between Linear and Binary Searching.
     *
     * Performs an extensive test involving 10000-element sized array with ten
     * tests, each performing the search with identical parameters. The result is
     * the average of the ten tests.
     */
    public static void LinearVSBinaryBrawl()
    {
        final int SIZE = 10000;
        final int TESTS = 10;
        long linearTotalTime = 0;
        long binaryTotalTime = 0;

        Random r = new Random();

        System.out.printf( "[+] Initializing array of size %d...\n", SIZE );

        int[] a = new int[SIZE];
        initArray( a );

        System.out.printf( "[+] Sorting array using Bubble Sort Code...\n" );

        bubbleSort( a );

        System.out.printf( "[+] Array sorted. Starting extensive test of %d trials each...\n", TESTS );

        for ( int i = 0; i < TESTS; i++ )
        {
            int key = r.nextInt( a.length - 1 );
            long start, end;

            // Linear
            start = System.nanoTime();
            linearSearch( a, key );
            end = System.nanoTime();
            linearTotalTime += end - start;

            // Binary
            start = System.nanoTime();
            binarySearch( a, key );
            end = System.nanoTime();
            binaryTotalTime += end - start;
        }

        System.out.printf( 
                "[-] Done. Results: (avg. in nanoseconds)\n" +
                "   [+] Linear Search: %d\n" +
                "   [+] Binary Search: %d\n\n",
                linearTotalTime / TESTS, binaryTotalTime / TESTS );
    }

    /**
     * Driver method.
     *
     * @param a         array to be searched
     * @param key       key to find
     *
     * @return          index of key found, -1 if not found
     */
    public static int binarySearch( int[] a, int key )
    {
        return binarySearch( a, key, 0, a.length - 1 );
    }

    /**
     * Uses the binary search technique to find key in array 'a'.
     *
     * @param a         array to be searched
     * @param key       key to find
     * @param min       min index of search area
     * @param max       max index of search area
     *
     * @return          index of key found, -1 if not found
     */
    public static int binarySearch( int[] a, int key, int min, int max )
    {
        // test if search area is empty
        if ( max < min )
            // search area is empty, return index -1
            return -1;
        
        // calculate midpoint
        int mid = min + ( ( max - min ) / 2 );

        // test if in lower area
        if ( a[mid] > key )
            // start a new search between min & mid - 1
            return binarySearch( a, key, min, mid - 1 );

        // test if in upper area
        else if ( a[mid] < key )
            // start a new search between mid + 1 & max
            return binarySearch( a, key, mid + 1, max );

        else
            // key has been found, return index
            return mid;
    }
}
