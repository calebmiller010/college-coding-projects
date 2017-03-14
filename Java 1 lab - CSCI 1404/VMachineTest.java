public class VMachineTest
{
    public static void main(String args[])
    {
        VMachine choose = new VMachine();//Create a vending machine object.
        
        
        choose.Stock();//Next, the machine should be stocked. 
        
        
        choose.Buy();//Three customers approach the machine, each wishing to make a
        choose.Buy();//Three customers approach the machine, each wishing to make a
        choose.Buy();//Three customers approach the machine, each wishing to make a
        //purchase from the machine
        
        
        choose.Inventory();//The owner is now closing the store and wishes to check the
        //inventory on the machine.
        
        
        choose.Stock();
        choose.Inventory();//The owner restocks the machine, collects the money, and checks
        //the inventory again to make sure everything is right.
        
        
        //The end
    }
}
