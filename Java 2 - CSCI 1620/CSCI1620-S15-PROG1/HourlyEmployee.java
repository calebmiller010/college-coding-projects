// Name: Caleb Miller
// Program 1
// Due Date: February 18th, 2015.
//
// Honor Pledge:  On my honor as a student of the University
//                of Nebraska at Omaha, I have neither given nor received
//                unauthorized help on this homework assignment.
//
// Partners:    none.
//
// Desc.:   This program receives information from the user about employees, 
//          then prints out the information about each employee, including
//          their weekly salary, and total pay for the week.

public class HourlyEmployee extends Employee {
    private double wage, hours;
    public HourlyEmployee (String f, String l, double w, double h) {
        super(f, l);
        wage = w;
        hours = h;
    }
    @Override
    public double calculate_weekly_pay () {
        return (double)Math.round(wage * hours * 100) / 100;
    }
}

