/*
Name        : Caleb Miller
Class       : 4450-001
Program #   : Assignment 3, Problem 2c
Due Date    : October 11, 2016

Honor Pledge:  On my honor as a student of the University
               of Nebraska at Omaha, I have neither given nor received
               unauthorized help on this homework assignment.

NAME: Caleb Miller
NUID: 616
EMAIL: calebmiller@unomaha.edu

Partners: None;
Description: This program implements the likelihood weighting sampling algorithm
*/

import java.util.HashMap;
import java.util.Scanner;

public class LWA {
    
    public double Likelihood_Weighting(int samples) { // Likelihood Weighting algorithm
        HashMap<Boolean,Double> vector = new HashMap<Boolean,Double>(); // We only keep track of the true/false values for the query in the vector before normalizing
        queryindex = getqueryindex(query);
        for(int i=0;i<samples;i++) {
            returnobj r = weighted_sample(); // create bayesian network sample
            Boolean queryvalue = r.returnevents[queryindex].value; // find value of query, will help keep track of the weight
            Double d = r.weight;
            if(vector.containsKey(queryvalue)) 
                d += vector.get(queryvalue); // add returned weight to current value of weight
            vector.put(queryvalue, d);
        }
        return normalizevector(vector.get(Boolean.TRUE),vector.get(Boolean.FALSE)); // return value of desired query back to main method
    }
    
    public returnobj weighted_sample() {
        double weight = 1;
        event[] samplevents = {E.copy(),T.copy(),R.copy(),S.copy(),P.copy()}; // create local copy of events
        for(int count = 0;count<events.length;count++) {
            if(events[count].value != null) { // if it's an evidence variable then...
                weight *= getweight(events[count], samplevents); // get the weight!
            }
            else {
                samplevents[count].value = randomevent(events[count], samplevents); // otherwise, generate a random true/false value for it
            }
        }
        return new returnobj(weight,samplevents);
    }
   


    String evidence;
    String query;
    Boolean truequery;
    int queryindex = -1;
    event E = new event("E");
    event T = new event("T");
    event R = new event("R");
    event S = new event("S");
    event P = new event("P");
    event[] events = new event[5];
    
    public static void main (String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.print("E = Education\nT = Teaching\nR = Research\nS = Service\nP = Performance\n(format - P(query|evidence1,evidence2,...evidenceN)\nTo make variable probability low: '-'\nexample - P(R|E)\n          P(S|-T,P)\n          P(-E|T,-P)\nEnter your query: ");
        String input = in.nextLine();
        LWA a = new LWA();
        if (!a.validInput(input)) {
            System.out.println("Invalid input!!!");
            in.close();
            return;
        }
        double percent = a.Likelihood_Weighting(100);
        System.out.printf("%s: %.2f%%\n", input, 100*percent);
        in.close();
    }
    
    private double normalizevector(Double tweight, Double fweight) {
        Double total = tweight + fweight;
        if (truequery) // if query value wanted is true
            return tweight/total;
        else
            return fweight/total;
    }
    
    public double getweight(event e, event[] sampled) {
// used to get the weight based on the event and the current sampled bayesian network
        if (e.equals(E))
            return getweightE(sampled[0].value); // 
        if (e.equals(T))
            return getweightT(sampled[0].value,sampled[1].value);
        if (e.equals(R))
            return getweightR(sampled[0].value,sampled[2].value);
        if (e.equals(S))
            return getweightS(sampled[3].value);
        if (e.equals(P))
            return getweightP(sampled[1].value,sampled[2].value,sampled[3].value,sampled[4].value);
        return 1;
    }

    public Boolean randomevent(event e, event[] sampled) {
// used to generate the random event based on the event passed in, and the current sampled bayesian network
        if (e.equals(E))
            return probE(); // no parents
        if(e.equals(T))
            return probT(sampled[0].value); // 1 parent - E
        if(e.equals(R))
            return probR(sampled[0].value); // 1 parent - E
        if(e.equals(S))
            return probS(); // no parents
        if(e.equals(P))
            return probP(sampled[1].value,sampled[2].value,sampled[3].value); // 3 parents - T,R,S
        return false;
    }
    
    public boolean validInput(String in) {
        String input = in.replaceAll(" ", "").toUpperCase();
        
        try{
            if (input.substring(2, 4).contains("-")) {
                truequery = false;
                query = input.substring(2, 4);
                evidence = input.substring(5,input.length()-1);
            }
            else {
                truequery = true;
                query = input.substring(2, 3);
                evidence = input.substring(4,input.length()-1);
            }
        }
        catch (IndexOutOfBoundsException e) {
            if(input.matches("P[(]-?[ETRSP][)]")) { // catches case of no evidence variables i.e. P(P)
                events[0] = E;
                events[1] = T;
                events[2] = R;
                events[3] = S;
                events[4] = P;
                return true;
            }
            return false;
        }
        
        if (!input.substring(0, 2).equals("P("))
            return false;
        if (!(query.matches("-?E")|query.matches("-?T")|query.matches("-?R")|query.matches("-?S")|query.matches("-?P")))
            return false;
        
        String[] evidences = new String[5];
        evidences = evidence.split(",");
        if (input.substring(3,5).contains("|")) {
            for(String a:evidences) { 
                switch(a) {
                case "E": E.value = true;
                break;
                case "-E": E.value = false;
                break;
                case "T": T.value = true;
                break;
                case "-T": T.value = false;
                break;
                case "R": R.value = true;
                break;
                case "-R": R.value = false;
                break;
                case "S": S.value = true;
                break;
                case "-S": S.value = false;
                break;
                case "P": P.value = true;
                break;
                case "-P": P.value = false;
                break;
                }
            }
            events[0] = E;
            events[1] = T;
            events[2] = R;
            events[3] = S;
            events[4] = P;
            
            if (input.substring(input.length()-1).equals(")"))
                return true;
            else
                return false;
            
        }
        else {
            return false;
        }
    }

    public double getweightP(Boolean t, Boolean r, Boolean s, Boolean p) {
        if(t) {
            if(r) {
                if(s) {
                    if(p) // P(P|T,R,S)
                        return .95;
                    else
                        return .05;
                }
                else {
                    if(p) // P(P|T,R,-S)
                        return .8;
                    else
                        return .2;
                }
            }
            else {
                if(p) // P(P|T,-R,-S) and P(P|T,-R,S)
                    return .3;
                else
                    return .7;
            }
        }
        else {
            if(r) { // P(P|-T,R,S) and P(P|-T,R,-S)
                return .5;
            }
            else {
                if(p) // P(P|-T,-R,S) and P(P|-T,-R,-S)
                    return .05;
                else
                    return .95;
            }
        }
    }
    public double getweightS(Boolean s) {
        if(s) return .4; // P(S)
        else return .6;
    }
    public double getweightE(Boolean b) {
        if (b) return .7; // P(E)
        else return .3;
    }
    
    public double getweightT(Boolean e, Boolean t) {
        if(e) {
            if(t) // P(T|E)
                return .45;
            else
                return .55;
        }
        else
            if(t) // P(T|-E)
                return .15;
            else
                return .85;
    }
    
    public double getweightR(Boolean e, Boolean r) {
        if(e) {
            if(r) // P(R|E)
                return .85;
            else
                return .15;
        }
        else
            if(r) // P(R|-E)
                return .35;
            else
                return .65;
    }


    public boolean probE() {
        if (Math.random()<=0.7) // P(e)
            return true;
        else
            return false;
    }
    
    public boolean probS() {
        if (Math.random()<=0.4) // P(s)
            return true;
        else
            return false;
    }
    
    public boolean probT(Boolean e) {
        double rand = Math.random();
        if (e) {
            if (rand<=.45) // P(t|e)
                return true;
            else
                return false;
        }
        else {
            if (rand<=.15) // P(t|-e)
                return true;
            else
                return false;
        }
    }
    
    public boolean probR(Boolean e) {
        double rand = Math.random();
        if (e) {
            if (rand<=.85) // P(r|e)
                return true;
            else
                return false;
        }
        else {
            if (rand<=.35) // P(r|-e)
                return true;
            else
                return false;
        }
    }
    
    public boolean probP(Boolean t, Boolean r,Boolean s) {
        double rand = Math.random();
        if(t) {
            if (r) {
                if(s) {
                    if (rand<=.95) // P(p|t,r,s)
                        return true;
                    else
                        return false;
                }
                else {
                    if (rand<=.8) // P(p|t,r,-s)
                        return true;
                    else
                        return false;
                }
            }
            else {
                if (rand<=.3) // P(p|t,-r,s) and P(p|t,-r,-s)
                    return true;
                else
                    return false;
            }
        }
        else {
            if (r) {
                if (rand<=.5) // P(p|-t,r,s) and P(p|-t,r,-s)
                    return true;
                else
                    return false;
            }
            else {
                if (rand<=.05) // P(p|-t,-r,s) and P(p|-t,-r,-s)
                    return true;
                else
                    return false;
            }
        }
    }
    
    private int getqueryindex(String qs) {
        if (qs.contains("E")) return 0;
        if (qs.contains("T")) return 1;
        if (qs.contains("R")) return 2;
        if (qs.contains("S")) return 3;
        if (qs.contains("P")) return 4;
        return 5;
    }

    public class returnobj {
        double weight;
        event[] returnevents;
        public returnobj(double weight, event[] returnevents) {
            super();
            this.weight = weight;
            this.returnevents = returnevents;
        }
        
    }
    
    public class event {
        String name;
        Boolean value;
        
        public event(String name, Boolean value) {
            super();
            this.name = name;
            this.value = value;
        }

        public event(String n) {
            this.name = n;
            value = null;
        }
        
        public event copy() {
            return new event(this.name,this.value);
        }
        
    }
    
}

