// Caleb Miller
// Program 3
// Due Date: 4/7/2015
//
// Honor Pledge:  On my honor as a student of the University
//                of Nebraska at Omaha, I have neither given nor received
//                unauthorized help on this homework assignment.
//
// Partners:    none
//
//
// Desc.:   This program uses a composition relationship between the Stack
//          class and LinkedList class. This "stack class" has all of the
//          methods used in our lecture notes.
//

import java.util.LinkedList;
import java.util.List;

public class Stack {
    private List<Integer> list = new LinkedList<Integer>();
    public void push (int o) { 
        list.add(o);
    }
    
    public Object pop () {
        Object o = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return o;
    }
    public boolean empty () { return list.size() == 0; }
    public int length () { return list.size(); }
    public boolean has_value (Object o) {
        for (int x=0; x<list.size(); x++)
            if (list.get(x).equals(o)) return true;
        return false;
    }
    public Object peek () { return list.get(list.size() - 1); }
}
