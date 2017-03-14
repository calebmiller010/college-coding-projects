/*
   1. What is the difference between a Stack and a Linked List?

   Linked Lists and Stacks are both Dynamic Data Structures, but the main difference of them is the accessibility of the elements in the structure. A Linked List can have any element removed/retrieved from any position in the List at any time from the use of nodes linking each element together. In a Stack, we are only able to make a limited number of operations. You cannot simply add/remove any element anywhere in the Stack. You have to follow what is called Last In, First Out, meaning you can only access, add, read, etc. elements on the "top" of the stack. Restricted Data Structures like Stacks we use to solve very specific problems. They are very similar, but the main thing to remember is the idea of Last In, First Out with stacks. 


   2. What is a node and what is a link?

    A Node is a self-referring class that stores the element data; It is basically the reference to the actual piece of data. The link is what connects all the elements together...specifically it connects the nodes together; we jump from one element to another thru those nodes to access the element we want.


   3. What are the pros and cons of using Stacks/LinkedLists versus arrays?

    The pros of using dynamic data structures versus an array is that it is much more flexible in the actual adding and removal of data in the list. They expand/shrink automatically when you add/remove an element. They use memory efficiently, because the memory is kept to a minimum of what is needed. Also, empty storage spaces are not necessary like in arrays. Arrays also have a fixed-size, which is difficut to expand or shrink.

    The cons of using dynamic data structures versus an array is that it is much more efficient to actually access your data in arrays. With dynamic structures, you have to use nodes and links to be able to access an element from the front or back end of the list. With an array, each element has a specific reference you can access at any time, so the computer does not make inefficient operations that make it go element by element to find something.

*/


import java.util.Scanner;

public class StackTest {
public static void main (String args[]) {
    Scanner in = new Scanner (System.in);
    Stack st = new Stack();
    st.push("One");
    st.push("Two");
    st.push("Three");
    System.out.print ("Type in a String, and I will tell you if your String is in the Stack or not: ");
    String user = in.next();
    if (st.has_value(user))
        System.out.println ("Yes, \""+user+"\" is in the Stack.");
    else
        System.out.println ("No, \""+user+"\" is not in the Stack.");
    }
}
