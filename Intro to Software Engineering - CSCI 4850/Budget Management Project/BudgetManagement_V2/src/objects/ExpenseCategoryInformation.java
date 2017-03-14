package objects;

//This class is for displaying under the budget tab easier
public class ExpenseCategoryInformation {
	String category;
	double budgetrecommendation;
	double actualspending;
	double difference;
	int budgetpercent;
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getBudgetrecommendation() {
		return budgetrecommendation;
	}
	public void setBudgetrecommendation(double budgetrecommendation) {
		this.budgetrecommendation = budgetrecommendation;
	}
	public double getActualspending() {
		return actualspending;
	}
	public void setActualspending(double actualspending) {
		this.actualspending = actualspending;
	}
	public double getDifference() {
		return this.budgetrecommendation - this.actualspending;
	}
	public int getBudgetpercent() {
		return budgetpercent;
	}
	public void setBudgetpercent(int budgetpercent) {
		this.budgetpercent = budgetpercent;
	}
}
