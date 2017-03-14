package objects;

import java.util.HashMap;

public class Budget {
	HashMap<Category, Double> budgettable;
	
	public Budget(HashMap<Category, Double> budget) {
		super();
		this.budgettable = budget;
	}

	public Double getPercentFromCategory (Category c) {
		return budgettable.get(c);
	}
	
	public HashMap<Category, Double> getBudget() {
		return budgettable;
	}
	public void setBudget(HashMap<Category, Double> budget) {
		this.budgettable = budget;
	}
	
}
