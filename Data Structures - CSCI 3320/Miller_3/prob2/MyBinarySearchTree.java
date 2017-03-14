/*
    Name        : Caleb Miller
    Class       : 3320-002
    Program #   : Program 3 Problem 2
    Due Date    : October 6, 2015 at 11:59 PM
    
    Honor Pledge:  On my honor as a student of the University
               of Nebraska at Omaha, I have neither given nor received
               unauthorized help on this homework assignment.

NAME: Caleb Miller
NUID: 616
EMAIL: calebmiller@unomaha.edu
    
Partners: none;
Description: This program implements a Binary Search Tree
*/


import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

public class MyBinarySearchTree {
    public static void main (String args[]) {
        MyBinarySearchTree tree = new MyBinarySearchTree();
        Scanner input = new Scanner(System.in);
        int choice=0;
        while (choice != 7) {
            System.out.print ( "\n1)  Insert node\n2)  Print tree\n3)  Print number of leaves in tree\n4)  Print the number of nodes in T that contain only one child\n5)  Print the number of nodes in T that contain only two children\n6)  Print the level order traversal of the tree\n7)  Exit program\n\n>> Enter choice [1-7] from menu above: " );
            choice = input.nextInt();

            switch (choice) {
                case 1: System.out.print ("What value would you like to insert? ");
                        int in = input.nextInt();
                        tree.insert(in);
                        break;
                case 2: System.out.println("\nTree printed 'in-order': ");
                        tree.printTree();
                        System.out.println();
                        break;
                case 3: int leave = tree.leaves();
                        System.out.print("\nLEAVES:" + leave + "\n");
                        break;
                case 4: int onlys = tree.oneNode();
                        System.out.print("\nONLYS: " + onlys + "\n");
                        break;
                case 5: int two = tree.twoNode();
                        System.out.print("\nTWOS: " + two + "\n");
                        break;
                case 6: System.out.println("\nTree printed in 'level order': ");
                        tree.levelOrder();
                        System.out.println();
                        break;
        }
        System.out.println();

        }

    }
    public int twoNode() {
        int x;
        x = twoNode(root);
        return x;
    }
    
    public int leaves() {
        int l;
        l = leaves(root);
        return l;
    }
    public int oneNode() {
        int x;
        x = oneNode(root);
        return x;
    }
    

  public static int numberOfChildren (BinaryNode t){
      int count = 0;
      if(t.left != null ) count++;
      if(t.right != null) count++;       
      return count;   
      }
    private static class BinaryNode {
        public int key;
        public BinaryNode left;
        public BinaryNode right;

        //constructor #1: null constructor
        BinaryNode(int x) {
            key = x;
            left = right = null;
        }

        //constructor #2
        BinaryNode(int x, BinaryNode l, BinaryNode r) {
            key = x;
            left = l;
            right = r;
        }
    }

    private BinaryNode root; //root of our tree
    public BinaryNode getRoot(){return root;}

    public void insert ( int x ) {
        root = insert(x,root);
    }

    //methods for BST operations
    public int findMin() {
        if (isEmpty(root))
            return -1;
        return findMin(root).key;
    }
    public void printTree() {
        if(isEmpty(root))
            System.out.println("Empty tree");
        else
            printTree(root);
    }
    public void levelOrder() {
        Queue<BinaryNode> queue = new LinkedList<BinaryNode>();
        queue.add(root);
        while(!queue.isEmpty())
        {
            BinaryNode tempNode = queue.poll();
            System.out.printf("%d ",tempNode.key);
            if(tempNode.left!=null)
                queue.add(tempNode.left);
            if(tempNode.right!=null)
                queue.add(tempNode.right);
        }
        
    }
    public int leaves(BinaryNode node) {
        if( node == null )
            return 0;
          if( node.left == null && node.right == null ) {
            return 1;
          } else {
            return leaves(node.left) + leaves(node.right);
          }

    }
    public static int oneNode(BinaryNode t)
    {
       if(t == null || numberOfChildren(t)==0){
           return 0;
       }
       if(numberOfChildren(t)==1){
           return 1+ oneNode(t.left) + oneNode(t.right);
       }
           if(numberOfChildren(t)==2 ){
           return oneNode(t.left) + oneNode(t.right);
      }
           return 0;
    }
    public static int twoNode(BinaryNode t)
    {
       if(t == null || numberOfChildren(t)==0){
           return 0;
       }
       if(numberOfChildren(t)==2){
           return 1+ twoNode(t.left) + twoNode(t.right);
       }
           if(numberOfChildren(t)==1 ){
           return twoNode(t.left) + twoNode(t.right);
      }
           return 0;
    }
    private void printTree( BinaryNode t )
    {
        if (t != null)
        {
            printTree(t.left);
            System.out.print(t.key + " ");
            printTree(t.right);
        }
    }
    private BinaryNode findMin(BinaryNode t) {
        if (t == null)
            return null;
        if(t.left==null)
            return t;
        return findMin(t.left);
    }

    public int findMax() {
        if (isEmpty(root))
            return -1;
        return findMax(root).key;
    }

    private BinaryNode findMax(BinaryNode t) {
        if (t == null)
            return null;
        if(t.right==null)
            return t;
        return findMax(t.right);
    }
    public BinaryNode find(int x, BinaryNode t){
        if (t==null) return null;
        else if (x < t.key)
            return find(x, t.left);
        else if (x > t.key)
            return find(x, t.right);
        else
            return t;
    }
    private BinaryNode insert ( int x, BinaryNode t) {
        if (t==null)
            return new BinaryNode(x,null,null);
        if(x<t.key)
            t.left = insert(x,t.left);
        else if (x>t.key)
            t.right = insert(x,t.right);
        else
            ;
        return t;
    }
    private BinaryNode remove(int x, BinaryNode t) {
        if(t==null)
            return t;
        if(x<t.key)
            t.left = remove(x, t.left);
        else if(x>t.key)
            t.right = remove(x, t.right);
        else if(t.left!=null && t.right != null)
        {
            t.key = findMin(t.right).key;
            t.right = remove(t.key,t.right);
        }
        else
            t = (t.left != null) ? t.left : t.right;
        return t;
    }
    boolean isEmpty(BinaryNode t){
        if (t == null) return true;
        else return false;
    }
}

