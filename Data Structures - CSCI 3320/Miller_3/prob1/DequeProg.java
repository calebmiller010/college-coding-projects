/*
    Name        : Caleb Miller
    Class       : 3320-002
    Program #   : Program 3 Problem 1
    Due Date    : October 6, 2015 at 11:59 PM

    Honor Pledge:  On my honor as a student of the University
               of Nebraska at Omaha, I have neither given nor received
               unauthorized help on this homework assignment.

NAME: Caleb Miller
NUID: 616
EMAIL: calebmiller@unomaha.edu

Partners: none;
Description: This program implements a Double-Ended Queue.
*/

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Scanner;

public class DequeProg {
    
    Deque<Integer> dq = new ArrayDeque<Integer>();
    
    public static void main(String[] args) {
        
        DequeProg instance = new DequeProg();
        Scanner sc = new Scanner(System.in);
        int choice, element;
        System.out.println();
        
        do {
            
            System.out.print("Enter operation for deque (1: Push, 2: Pop, 3: Inject, 4: Eject, 5: Quit): ");
            choice = sc.nextInt();
            
            switch (choice) {
                case 1: System.out.print("Enter element to push: ");
                        element = sc.nextInt();
                        instance.dq.addFirst(element);
                        instance.print();
                        break;

                case 2: if (instance.dq.size()==0)
                            System.out.println("Deque is empty, nothing to pop.");
                        else {
                            instance.dq.removeFirst();
                            instance.print();
                        }
                        break;

                case 3: System.out.print("Enter element to inject: ");
                        element = sc.nextInt();
                        instance.dq.addLast(element);
                        instance.print();
                        break;

                case 4: if (instance.dq.size()==0)
                            System.out.println("Deque is empty, nothing to eject.");
                        else {
                            instance.dq.removeLast();
                            instance.print();
                        }
                        break;

                case 5: System.out.println("Bye!\n");
                        break;

            }

        } while (choice!=5);     

    }


    public void print() {

        String print = "" + dq;
        print = print.replace(",","");
        print = print.replace("[","");
        print = print.replace("]","");
        System.out.println("Current Deque: " + print );

    }
}    
