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

public abstract class Employee {
    private int employee_id;
    private static int next_id = 1;
    private String first_name, last_name;
    public Employee (String first_name, String last_name) {
        employee_id = next_id++;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    public int get_employee_id () {
        return employee_id;
    }
    public abstract double calculate_weekly_pay ();
    @Override
    public String toString () {
        return String.format("%s, %s (%s) (EID: %d)", last_name,
        first_name, this.getClass().getName(), employee_id);
    }
    @Override
    public boolean equals (Object o) {
        return (employee_id == ((Employee)o).get_employee_id() ? true :false);
    }
}

