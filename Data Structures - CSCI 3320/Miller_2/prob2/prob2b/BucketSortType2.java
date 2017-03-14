/*
    Name        : Caleb Miller
    Class       : 3320-002
    Program #   : Assignment 2, Problem 2b
    Due Date    : September 15, 2015

    Honor Pledge:  On my honor as a student of the University
               of Nebraska at Omaha, I have neither given nor received
               unauthorized help on this homework assignment.

NAME: Caleb Miller
NUID: 616
EMAIL: calebmiller@unomaha.edu

Partners: None;
Description: This program practices the use of the bucket sort, using an array
             of Linked Lists. It shows how the sort is actually done by printing
             out what it looks like after each pass. This program differs from 
             Problem 2a by using 100 buckets rather than 10.
*/

import java.util.Scanner;

public class BucketSortType2 {

        //create arrays to be used throughout the class; one to keep track of
        //the size, and the other the actual Array of Linked Lists
        int[] size = new int[100];
        BucketSortType2[] list = new BucketSortType2[100];
    
    public static void main (String args[]) {
        
        //get input from user    
        Scanner sc = new Scanner(System.in);
        BucketSortType2 ins = new BucketSortType2();
        System.out.print("\nEnter the list of integers to be sorted, separated by blank spaces: ");
        
        //convert input to array of numbers
        String sampleString = sc.nextLine();
        String[] stringArray = sampleString.split(" ");
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            String numberAsString = stringArray[i];
            intArray[i] = Integer.parseInt(numberAsString);
        }
        
        //instantiate a new instance of the list at each header from the array
        for (int i = 0; i <= 99; i++ ) {
            ins.list[i] = new BucketSortType2();
            ins.size[i] = 0;
        }

        int passmult = 1;

        //find number of passes
        int passes = ins.findpasses(intArray);
        System.out.println("\nNumber of passes required: " + passes ); 
                
        //perform bucket sort based on number of passes
        for (int count = 1; count <= passes; count++) {

        passmult*=100;
        ins.sort(intArray, passmult);
        System.out.printf("\nPass %d buckets are: ", count );

            //display list
            for (int p = 0; p <= 99; p++ ) {
                System.out.print( "\n" + p + ": " );
                ins.list[p].displayList(ins.size[p]); 
            }

        intArray = ins.changearray(intArray.length);
        System.out.println();    

        }

        //print out final, sorted list
        System.out.print("Sorted List: ");
        for (int a = 0; a < intArray.length; a++ ) {
            System.out.print( intArray[a] + " ");
        }

        System.out.println("\n");

    } //end of main method


//  Function Name   : changearray
//  Parameters      : integer: length of the list of all numbers
//  Return value(s) : array of integers to update the array in the main method
//  Partners        : none
//  Description     : this method is used to update the overall order of numbers
//                    since the last pass the bucket sort made

    public int[] changearray (int length ) {
        int[] newArray = new int[length];
        int count = 0;
        for (int p = 0; p < 100; p++) {
            while (list[p].hdr.next != null) {
                newArray[count]=list[p].remove(1);
                size[p]--;
                count++; 
            }
        }
        return newArray;
    }


//  Function Name   : findpasses
//  Parameters      : integer array: overall list of numbers
//  Return value(s) : integer: # of digits in the largest number
//  Partners        : none
//  Description     : this method determines how many passes is needed, by
//                    finding how many digits the largest number in the list has

    public int findpasses ( int[] intArray ) {
        int length;
        int largest = 0;

        for (int x = 0; x <intArray.length; x++) {
            length = (int)(Math.log10(intArray[x])+1);
            if (length > largest)
                largest = length;
        }
        double large = largest;
        largest = (int)Math.ceil(large/2);
        return largest;
    }
            

//  Function Name   : sort
//  Parameters      : integer: modifier so we put each number in the correct
//                             position based off which pass it is
//                    integer array: the latest list of numbers from the last
//                                   pass
//  Return value(s) : none
//  Partners        : none
//  Description     : this method finds where each number needs to be inserted,
//                    and then inserts it into the linked list, as well as
//                    increases the size at that array

    public void sort ( int[] intArray, int modifier ) {
        for (int x=0; x < intArray.length; x++) {
            int y = ((intArray[x] % modifier)/(modifier/100));
            list[y].insert(intArray[x]);
            size[y]++;
        }
    }


//  Function Name   : displayList
//  Parameters      : integer: size of the list
//  Return value(s) : none
//  Partners        : none
//  Description     : this method simply prints out what the current list is

    public void displayList(int size) {
        int x=0;
        Node currentNode = hdr;
        if (currentNode.next != null) {
        while (x <= size) {
            currentNode.displayNode(); 
            currentNode = currentNode.next;
            x++;
        }
        }
        
    }

    private Node hdr = new Node(0 , null); //create header Node


//  Function Name   : insert
//  Parameters      : integer: data connected with the Node
//                    integer: position of the Node in the list
//  Return value(s) : none
//  Partners        : none; method given to us in class
//  Description     : this method inserts a new element into the list

    public void insert (int data) {
        Node tmpNode = hdr;
        Node newNode = new Node(data, null);
           while (tmpNode.next != null) 
            tmpNode=tmpNode.next;
        newNode.next = tmpNode.next;
        tmpNode.next = newNode;
    }


//  Function Name   : remove
//  Parameters      : integer: position of the Node in the list
//  Return value(s) : returns the data of the element removed
//  Partners        : none; based off the method given in class, but changed
//                    slightly for this program
//  Description     : this method removes an element from the list

    public int remove (int pos) {
        
        Node tmpNode, tmpNode2;
        tmpNode = hdr;
        tmpNode2 = hdr.next;
        
        
        if (tmpNode2.next != null) {
                hdr.next = tmpNode2.next;
                return tmpNode2.data;
        }
        else {
            int a = tmpNode2.data;
            hdr.next = null;
            return a;
        }
    }


//  Function Name   : find
//  Parameters      : integer: position of the Node in the list
//  Return value(s) : returns the data of the element found
//  Partners        : none; method given to us in class
//  Description     : this method finds the data of an element in the list
   
    public int find (int pos) {
        Node tmpNode = hdr;
        for (int i=1; i<pos; i++)
            tmpNode=tmpNode.next;
        return tmpNode.data;
    }

    private class Node {
        public int data;
        public Node next;
        public Node (int d, Node p) {// gets the data and next element from the Node; d is data passed in, and p (type: Node) is the reference to the next Node
            data = d;
            next = p;
        }

        public void displayNode() {//displays data of the current Node from the display List method
            if(data != 0)
            System.out.print(data + " ");
        }
    }
}
