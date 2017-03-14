package objects;
import java.util.List;

import bizlayer.BizLogicMath;

public class Account {
	User user;
	List<Transaction> transactions;
	Budget budget;
	List<Payment> payments;
	double totalexpenses;
	double totalincome;
	
	public Account(User user, List<Transaction> transactions, Budget budget, List<Payment> payments) {
		this.user = user;
		this.transactions = transactions;
		this.budget = budget;
		this.payments = payments;
		BizLogicMath m = new BizLogicMath();
		totalexpenses = m.totalSpending(this.transactions);
		totalincome = m.totalIncome(this.transactions);
	}
	
	public double getTotalexpenses() {
		return totalexpenses;
	}

	public void setTotalexpenses(double totalexpenses) {
		this.totalexpenses = totalexpenses;
	}

	public double getTotalincome() {
		return totalincome;
	}

	public void setTotalincome(double totalincome) {
		this.totalincome = totalincome;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public Budget getBudget() {
		return budget;
	}
	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	
	
}
