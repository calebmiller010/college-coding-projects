/*
    Name        : Caleb Miller
    Class       : 3320-002
    Program #   : Assignment 2, Problem 1a
    Due Date    : September 15, 2015
    
    Honor Pledge:  On my honor as a student of the University
               of Nebraska at Omaha, I have neither given nor received
               unauthorized help on this homework assignment.

NAME: Caleb Miller
NUID: 616
EMAIL: calebmiller@unomaha.edu
    
Partners: None; 
Description: This program is based off the Josephus problem, using linked 
             lists to calculate who the winner is, based off the number of 
             people in the circle, and the number of passes before someone
             is eliminated.
*/

import java.util.Scanner;

public class LinkedCircleList {
    public static void main ( String args[] ) {
        
        Scanner input = new Scanner(System.in);
        
        System.out.print("\nEnter the values of M: ");
        int M = input.nextInt(); // M = passes

        System.out.print("Enter the value of N: ");
        int N = input.nextInt(); // N = size of circle
        
        LinkedCircleList list = new LinkedCircleList();
        
        for (int x=1; x<=N; x++)
            list.insert(x,x);
        
        list.josephus(N,M);

    }
    
//  Function Name   : josephus
//  Parameters      : integer: size of the circle/list
//                    integer: how many passes before a person is eliminated
//  Return value(s) : none
//  Partners        : none
//  Description     : this method runs the josephus problem, which uses the 
//                    linked list to determine who is eliminated from the 
//                    list each time, then prints out which element was 
//                    eliminated, and what the new list looks like, until a 
//                    winner is determined.   

    public void josephus (int size, int step) {
        int count = 0;
        int curpos = 0;
        int print;
        System.out.print( "Current list: " );
        
        // display initial list
        displayList(size);
        
        while (size>1) { // narrow down the circle, based off size and step while the list has more than one elements
            count++;

            for (int i=0; i<step; i++) { // go through the list i number of times based on what M/step is

                if (curpos==size-1) // if the list reaches the end, revert back to the beginning of the list
                    curpos=0;
                else // otherwise, go to next element
                    curpos++;
            }
            if (curpos==size-1) { // special case in removing the last element of the list
                removelast(size);
                print = getlast(size);
                System.out.println("Pass " + count + " eliminated " + print);
                curpos = 0;
            }
            else { // remove element, and print out what the pass eliminated
                print = remove(curpos);
                System.out.println("Pass " + count + " eliminated " + print);
            }

            size--;
            System.out.print( "Current list: " );
            displayList(size);

        } // end of while loop

        System.out.print("WINNER: ");
        displayList(size);
        System.out.println();
        
    }

//  Function Name   : removelast
//  Parameters      : integer: size of the circle/list
//  Return value(s) : none
//  Partners        : none
//  Description     : this program is for the case in that it must eliminate
//                    the last element of the list   
    public void removelast(int size) {
        Node currentNode = hdr;
        Node firstNode = hdr;
        for (int x=0; x<size; x++) {
            currentNode = currentNode.next;
        }
        currentNode.next = firstNode;
    }

//  Function Name   : getlast
//  Parameters      : integer: size of the circle/list
//  Return value(s) : integer: the last element of the list
//  Partners        : none
//  Description     : I tried making the removelast method return the element
//                    it removed, but I was getting different kind of errors
//                    so I just decided to make a new getlast method to return
//                    the data of the last element
    public int getlast(int size) {
        Node currentNode = hdr;
        for (int x=0; x<size; x++) {
            currentNode = currentNode.next;
        }
        int x = currentNode.data;
        return x;
    }

//  Function Name   : displayList
//  Parameters      : integer: size of the circle/list
//  Return value(s) : none
//  Partners        : none
//  Description     : this method simply prints out what the current list is
    public void displayList(int size) {
        int x=0;
        Node currentNode = hdr;

        while (x <= size) {
            currentNode.displayNode(); 
            currentNode = currentNode.next;
            x++;
        }
        System.out.println("");
    }

    private Node hdr = new Node(0 , null); // create header Node
    private int size;

//  Function Name   : insert
//  Parameters      : integer: data connected with the Node
//                    integer: position of the Node in the list
//  Return value(s) : none
//  Partners        : none; method given to us in class
//  Description     : this method inserts a new element into the list
    public void insert (int data, int pos) {
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
//  Partners        : none; method given to us in class
//  Description     : this method removes an element from the list
    public int remove (int pos) {

        Node tmpNode, tmpNode2;
        tmpNode = hdr;
        tmpNode2 = hdr.next;

        for (int i=0; i<pos; i++) {
            tmpNode = tmpNode.next;
            tmpNode2 = tmpNode2.next;
        }

        tmpNode.next = tmpNode2.next;
        return tmpNode2.data;
    }

//  Class Name      : Node
//  Partners        : none; given to us in class
//  Description     : this is the Node class, essential to the use of a 
//                    Linked List. In this case, our nodes have a reference
//                    to the data stored, and the next node.
    private class Node {
        public int data;
        public Node next;

        public Node (int d, Node p) { // gets the data and next element from the Node; d is data passed in, and p (type: Node) is the reference to the next Node
            data = d;
            next = p;
        }

        public void displayNode() { // displays data of the current Node from the displayList method
            if(data != 0)
            System.out.print(data + " ");
        }
    }
}
