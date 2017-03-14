/*
Name        : Caleb Miller
Class       : 4450-001
Program #   : Assignment 1, Problem 1
Due Date    : September 13, 2015

Honor Pledge:  On my honor as a student of the University
               of Nebraska at Omaha, I have neither given nor received
               unauthorized help on this homework assignment.

NAME: Caleb Miller
NUID: 616
EMAIL: calebmiller@unomaha.edu

Partners: None;
Description: This program implements a Depth limited search to solve a 5-puzzle
*/

import java.util.*;

public class DepthLimitedSearch {
    
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
    
    public Direction Up = new Direction("345",3,"U");
    public Direction Down = new Direction("012",-3,"D");
    public Direction Right = new Direction("03",-1,"R");
    public Direction Left = new Direction("25",1,"L");
    private static final String goalstate = "123450";
    
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
        DepthLimitedSearch solve = new DepthLimitedSearch();
        String path = solve.IDS(input,goalstate).msg;
        System.out.println("Action Sequence: " + path);
        in.close();
    }
    
    public static boolean validInput (String s) {
        if (!s.matches("[0-5]{6}"))
            return false;
        s = s.replaceFirst("0", "");
        s = s.replaceFirst("1", "");
        s = s.replaceFirst("2", "");
        s = s.replaceFirst("3", "");
        s = s.replaceFirst("4", "");
        s = s.replaceFirst("5", "");
        if (s.isEmpty())
            return true;
        return false;
    }
    
    public returnobj IDS(String search, String goal) {
        returnobj ret = new returnobj(false,"");
        for (int d=0; d<18;d++) {
            ret = DLS(search,goal,d);
            if (ret.success)
                return ret;
        }
        ret.success = false;
        ret.msg = "Not found";
        return ret;
    }
    
    public returnobj DLS(String search, String goal, int depth) {
        ArrayList<Node> children = new ArrayList<Node>();
        Stack<Node> Fringe = new Stack<Node>();
        Fringe.push(new Node(search,""));
        while (!Fringe.isEmpty())
        {
            Node current = Fringe.pop();
            if (current.state.compareTo(goal)==0)
            {
                return new returnobj(true,getpath(current));
            }
            if (current.depth == depth)
            {
                continue;
            }
            else
            {
                children = populateChildren(current.state);
                for (int i = 0; i < children.size(); i++)
                {
                    Fringe.push(new Node(current,children.get(i).state, children.get(i).direction));
                }
            }
           
        }
        
        return new returnobj(false,"");
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
    
    public ArrayList<Node> populateChildren(String state) {
        ArrayList<Node> list = new ArrayList<Node>();
        Node tmp;
        tmp = GetChild(state,Down);
        if(tmp!=null) list.add(tmp);
        tmp = GetChild(state,Right);
        if(tmp!=null) list.add(tmp);
        tmp =GetChild(state,Up);
        if(tmp!=null) list.add(tmp);
        tmp =GetChild(state,Left);
        if(tmp!=null) list.add(tmp);
        return list;
    }

    public Node GetChild (String state, Direction dir) {
        for (int i=0; i<dir.zerocantbeonindex.length(); i++)
            if(state.charAt(Integer.parseInt(dir.zerocantbeonindex.substring(i,i+1))) == '0' )
                return null;
        String newstate = moveTile(state, dir.tileswapmodifier);
        return new Node(newstate,dir.print);
    }
    
    public String moveTile (String s, int pm) {
        int zeroindex = s.indexOf('0');
        char[] arr = s.toCharArray();
        arr[zeroindex] = arr[zeroindex + pm];
        arr[zeroindex + pm] = '0';
        return new String(arr);
    }
    
    public class returnobj {
        boolean success;
        String msg;
        public returnobj(boolean s, String m) {
            success = s;
            msg = m;
        }
    }
    
    public class Node {
        int depth;
        String state;
        Node parent;
        String direction;
        
        public Node(String s, String d) {
            state = s;
            depth = 0;
            parent = null;
            direction = d;
        }
        
        public Node(Node p, String s, String d) {
            parent = p;
            state = s;
            depth = p.depth + 1;
            direction = d;
        }
    }
    
}

