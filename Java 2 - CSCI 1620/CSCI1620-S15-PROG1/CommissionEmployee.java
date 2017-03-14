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

public class CommissionEmployee extends Employee {
    private double sales, rate;
    public CommissionEmployee (String f, String l, double sal, double r) {
        super(f, l);
        sales = sal;
        rate = r;
    }
    @Override
    public double calculate_weekly_pay () {
        return (double)Math.round(sales*rate * 100) / 100;
    }
}

