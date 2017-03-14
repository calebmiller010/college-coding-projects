package objects;

public class Transaction {
	int id;
	String name;
	double amount;
	String comment;
	String category;
	boolean recurring;
	int transactionid;
	
	public int getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}
	public Transaction(int id, String name, double amount, String comment, String category, Boolean recurring, int transactionid) {
		super();
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.comment = comment;
		this.category = category;
		this.recurring = recurring;
		this.transactionid = transactionid;
	}
	public Transaction() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAmount() {
		return amount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isRecurring() {
		return recurring;
	}
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
