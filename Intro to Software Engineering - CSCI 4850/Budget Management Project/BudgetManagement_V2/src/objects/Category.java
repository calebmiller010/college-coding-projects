package objects;

public enum Category {
	FOOD("Food"), 
	SHELTER("Shelter"), 
	UTILITIES("Utilities"), 
	CLOTHING("Clothing"), 
	TRANSPORTATION("Transportation"), 
	MEDICAL("Medical"), 
	INSURANCE("Insurance"), 
	PERSONAL("Personal"), 
	EDUCATION("Education"), 
	SAVINGS("Savings"), 
	EXTRA_SPENDING("Extra Spending"), 
	OTHER("Other");
	
	private String value;

    Category(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
   
    public static Category getValue(String s) {
    	for(Category c:Category.values()) {
    		if(c.toString().compareTo(s) == 0)
    			return c;
    	}
    	return Category.OTHER;
    }
   
}
