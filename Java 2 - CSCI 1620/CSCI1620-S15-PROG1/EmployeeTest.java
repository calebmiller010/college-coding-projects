// Name: Caleb Miller
// Program 1
// Due Date: February 18th, 2015.
//
// Honor Pledge:  On my honor as a student of the University
//                of Nebraska at Omaha, I have neither given nor received
//                unauthorized help on this homework assignment.
//
// Partners:    none
//
// Desc.:   This program receives information from the user about employees, 
//          then prints out the information about each employee, including
//          their weekly salary, and total pay for the week.

import java.util.Scanner;

public class EmployeeTest {
    public static void main ( String args[] ) {
        Scanner in = new Scanner (System.in);
        System.out.print ( "How many Employees should we have: " );
        int num = in.nextInt();
        String fname, lname;
        System.out.printf ( "Great. We'll create %d employees now.", num );
        Employee[] staff = new Employee[num];
        for (int x=0; x<num; x++ ) {
            System.out.println ( "\n\n(h=hourly, s=salary, c=commission)" );
            System.out.printf ( "Employee #%d type: ", x+1 );
            char type =in.next().charAt(0);
        if (type=='h'||type=='s'||type=='c'){
                System.out.print ( "First name: " );
                fname=in.next();
                System.out.print ( "Last name: " );
                lname=in.next();
            switch (type) {
            case 'h':
                     System.out.print ( "Hours worked: ");
                     double hours = in.nextDouble();
                     System.out.print ( "Wage: $" );
                     double wage = in.nextDouble(); 
                     staff[x]= new HourlyEmployee( fname, lname, hours, wage);
                     break;
            case 's':
                     System.out.print ( "Salary: $ ");
                     double salary = in.nextDouble();
                     staff[x]= new SalaryEmployee( fname, lname, salary);
                     break;
            case 'c':
                     System.out.print ( "Sales: $");
                     double sales = in.nextDouble();
                     System.out.print ( "Enter commission rate between 0.0 and 1.0 (i.e.\".1\" = 10% commission)\n" );
                     System.out.print ( "Rate: ");
                     double rate = in.nextDouble();
                     staff[x]= new CommissionEmployee( fname, lname, sales, rate);

        }
        }
        else {
                x=x-1;
                System.out.print ( "Invalid  Employee type" );
                continue;
        }
        }
        System.out.println ( "\nAll Employees created! \n" );

        double total_pay = 0;
        for (int i=0; i<staff.length; i++) {
                double week=staff[i].calculate_weekly_pay();
                System.out.println(staff[i]);
                System.out.printf( "Weekly Pay: $%.2f \n\n", week );
                total_pay+=week;
        }
        System.out.printf ( "The total pay for the week: $%.2f\n\n", total_pay );

    }
}
