import java.util.Scanner;

public class VMachine {
    private int candy;
    private int soda;
    private int diet;
    private double money;

    public VMachine()
    { candy = 0;
        soda = 0;
        diet = 0;
        money = 0;
        System.out.println ( "Welcome to the Java Vending Machine\n" );
    }

    
    
    public void Stock () {
    Scanner in = new Scanner (System.in);
        System.out.print ( "How many bars of candy are being added? " );
        int c = in.nextInt();
        candy+=c;
        System.out.print ( "How many bottles of soda are being added? " );
        int s = in.nextInt();
        soda+=s;
        System.out.print ( "How many bottles of diet soda are being added? " );
        int d = in.nextInt();
        diet+=d;
        System.out.printf ( "$%.2f was collected from the machine\n\n", money );
        money = 0.00;


    }

    public void Inventory () {
        System.out.print ("--------Inventory--------\n" );
        System.out.print ( "|\tItem\t|\tNumber\n" );
        System.out.printf ( "|\tCandy\t|\t%d\n", candy );
        System.out.printf ( "|\tSoda\t|\t%d\n", soda );
        System.out.printf ( "|\tDiet Soda|\t%d\n", diet );
        System.out.printf ( "|\tMoney|\t%.2f\n", money );

    }

    public void Buy () {
        
    Scanner in = new Scanner (System.in);
       
        System.out.println ( "What would you like to buy?" );
        System.out.println ( "Choices:\n\tCandy\n\tSoda\n\tDiet Soda\n" );
        System.out.print ( "Choice: " );
        String input = in.nextLine();
        switch (input) {
            case "candy": candy=candy-1;
                          money+=.5;
                          break;
            case "soda": soda=soda-1;
                         money+=1;
                         break;
            case "diet": diet=diet-1;
                         money+=1;
                         break;
        }
        System.out.println ( "\n" );
        

    
        
    
    }



}
