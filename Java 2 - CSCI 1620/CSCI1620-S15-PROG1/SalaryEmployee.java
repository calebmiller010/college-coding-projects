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

public class SalaryEmployee extends Employee {
    private double salary;
    public SalaryEmployee (String f, String l, double s ) {
        super(f, l);
        salary=s;
        
    }
    @Override
    public double calculate_weekly_pay () {
        return (double)Math.round((salary/52)*100) / 100;
    }
}

