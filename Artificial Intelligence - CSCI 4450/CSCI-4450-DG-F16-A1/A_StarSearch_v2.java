/*
Name        : Caleb Miller
Class       : 4450-001
Program #   : Assignment 1, Problem 2b
Due Date    : September 13, 2015

Honor Pledge:  On my honor as a student of the University
               of Nebraska at Omaha, I have neither given nor received
               unauthorized help on this homework assignment.

NAME: Caleb Miller
NUID: 616
EMAIL: calebmiller@unomaha.edu

Partners: None;
Description: This program implements an A* search to solve and 8-puzzle 
             with the heuristic being the "Manhattan Distance" which is the sum 
             of positions away that each tile is from its correct position.
*/

import java.util.*;

public class A_StarSearch_v2 {
    
    public static class PQSort implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return heuristic(a.state) - heuristic(b.state); 
        }
    }
    
    public final class Direction {
        String zerocantbeonindex;
        int tileswapmodifier;
        String print;
        public Direction (String z, int t, String p) {
            zerocantbeonindex = z;
            tileswapmodifier = t;
            print = p;
        }
    }
    
    public Direction Up = new Direction("678",3,"U");
    public Direction Down = new Direction("012",-3,"D");
    public Direction Right = new Direction("036",-1,"R");
    public Direction Left = new Direction("258",1,"L");
    private static final String goalstate = "012345678";
    
    public static void main(String[] args) {    
        Scanner in = new Scanner(System.in);
        System.out.print("Enter string representing the order of tiles: ");
        String input = in.nextLine();
        input = input.trim();
        if (!validInput(input)) {
            in.close();
            return;
        }
        System.out.println("The Goal State is: " + goalstate);
        A_StarSearch_v2 solve = new A_StarSearch_v2();
        String path = solve.Search(input);
        System.out.println("Action Sequence: " + path);
        in.close();
    }
    
    public String Search(String search) {
        PQSort pqs = new PQSort();
        PriorityQueue<Node> Fringe = new PriorityQueue<Node>(1000,pqs);
        Set<String> Explored = new HashSet<String>();
        ArrayList<Node> children = new ArrayList<Node>();
        Fringe.add(new Node(search,""));
        
        while(!Fringe.isEmpty()) {
            Node current = Fringe.poll();
            if ( current.state.compareTo(goalstate) == 0) {
                return getpath(current);
            }
            Explored.add(current.state);
            children = populateChildren(current);
            for(Node child:children){
                if (!Explored.contains(child.state) && !Fringe.contains(child.state)) {
                    Fringe.add(child);
                }
            }
        
        }
        return "Not Found";
    }
    
    public static boolean validInput (String s) {
        if (!s.matches("[0-8]{9}"))
            return false;
        s = s.replaceFirst("0", "");
        s = s.replaceFirst("1", "");
        s = s.replaceFirst("2", "");
        s = s.replaceFirst("3", "");
        s = s.replaceFirst("4", "");
        s = s.replaceFirst("5", "");
        s = s.replaceFirst("6", "");
        s = s.replaceFirst("7", "");
        s = s.replaceFirst("8", "");
        if (s.isEmpty())
            return true;
        return false;
    }
    
    public static int heuristic(String state) {
        int count = 0;
        for(int i=0;i<9;i++) {
            count+= getDistance(i,goalstate.indexOf(state.substring(i,i+1)));
        }
        return count;
    }
    
    public static int getDistance(int currentpos, int rightpos) {
        int currentrow = getrow(currentpos);
        int currentcol = getcol(currentpos);
        int correctrow = getrow(rightpos);
        int correctcol = getcol(rightpos);
        
        return Math.abs(currentrow-correctrow) + Math.abs(currentcol-correctcol);
    }
    
    public static int getrow(int pos) {
        switch (pos) {
        case 0:
        case 1:
        case 2: return 0;
        case 3: 
        case 4:
        case 5: return 1;
        case 6:
        case 7:
        case 8: return 2;
        }
        return -1;
    }

    public static int getcol(int pos) {
        switch (pos) {
        case 0:
        case 3:
        case 6: return 0;
        case 1: 
        case 4:
        case 7: return 1;
        case 2:
        case 5:
        case 8: return 2;
        }
        return -1;
    }
    
    public String getpath(Node end) {
        if (end.parent==null) 
            return "No changes made!!";
        Node tmp=end.parent;
        String ret=end.direction;
        while (tmp.parent!=null) {
            ret = tmp.direction + "-" + ret;
            tmp = tmp.parent;
        }
        return ret;
    }
    
    public ArrayList<Node> populateChildren(Node current) {
        ArrayList<Node> list = new ArrayList<Node>();
        Node tmp;
        tmp = GetChild(current,Down);
        if(tmp!=null) list.add(tmp);
        tmp = GetChild(current,Right);
        if(tmp!=null) list.add(tmp);
        tmp =GetChild(current,Up);
        if(tmp!=null) list.add(tmp);
        tmp =GetChild(current,Left);
        if(tmp!=null) list.add(tmp);
        return list;
    }

    public Node GetChild (Node current, Direction dir) {
        for (int i=0; i<dir.zerocantbeonindex.length(); i++)
            if(current.state.charAt(Integer.parseInt(dir.zerocantbeonindex.substring(i,i+1))) == '0' )
                return null;
        String newstate = moveTile(current.state, dir.tileswapmodifier);
        return new Node(current,newstate,dir.print);
    }
    
    public String moveTile (String s, int pm) {
        int zeroindex = s.indexOf('0');
        char[] arr = s.toCharArray();
        arr[zeroindex] = arr[zeroindex + pm];
        arr[zeroindex + pm] = '0';
        return new String(arr);
    }
    
    public class Node {
        String state;
        Node parent;
        String direction;
        
        public Node(String state, String direction) {
            this.state = state;
            parent = null;
            this.direction = direction;
        }
        
        public Node(Node parent, String state, String direction) {
            this.parent = parent;
            this.state = state;
            this.direction = direction;
        }
    }
    
}

