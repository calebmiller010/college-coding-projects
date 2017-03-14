package bizlayer;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import db.DataObject;
import objects.Budget;
import objects.Category;
import objects.Payment;
import objects.Transaction;

public class BizLogicData {
	private DataObject dao = new DataObject();
	
	public List<Transaction> getAllTransactionsByUserId(int id) {
		return dao.getAllTransactionsByUserId(id);
	}
	
	public int addTransaction(int userid, String name, double amount, int type, String comment, String category) {
		return dao.addTransaction(userid, name, amount, type, comment, category);
	}

	public void removetransaction(int removeid) {
		dao.removeTransaction(removeid);
		dao.removePayment(removeid);
	}

	public void addUpcomingPayment(int userid, int transactionid, int pdate) {
		dao.addPayment(userid, transactionid, pdate);
	}

	public List<Payment> getAllPaymentsByUserId(int id) {
		List<Payment> payments = dao.getAllPaymentsByUserId(id);
		List<Transaction> transactions = dao.getAllTransactionsByUserId(id);
		
		for(Payment p:payments) {
			Transaction t = getTransaction(transactions,p.getTransactionid());
			p.setAmount(t.getAmount()*-1);
			p.setCategory(t.getCategory());
			p.setComment(t.getComment());
			p.setId(t.getId());
			p.setName(t.getName());
			p.setRecurring(t.isRecurring());
		}
		return payments;
	}

	private Transaction getTransaction(List<Transaction> transactions, int transactionid) {
		for(Transaction t:transactions) {
			if(t.getTransactionid() == transactionid)
				return t;
		}
		return null;
	}

	public void payBill(int removeid) {
		Calendar cal = Calendar.getInstance();
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		
		dao.payBill(removeid, dayOfMonth);
	}
	
	public void addUpdateBudget(int id, HashMap<Category, Double> budgets) {
		if(dao.getBudget(id) != null)
			dao.removeCustomBudget(id);
		dao.addCustombudget(id, budgets);
	}

	public Budget getBudget(int id) {
		HashMap<Category,Double> budgettable = dao.getBudget(id);
		if(budgettable==null) 
			budgettable = defaultBudget();
		Budget b = new Budget(budgettable);
		return b;
	}
	
	public HashMap<Category,Double> defaultBudget() {
		HashMap<Category,Double> budgettable = new HashMap<Category,Double>();
		budgettable.put(Category.FOOD, .1);
    	budgettable.put(Category.SHELTER, .2);
    	budgettable.put(Category.UTILITIES, .05);
    	budgettable.put(Category.CLOTHING, .05);
    	budgettable.put(Category.TRANSPORTATION, .05);
    	budgettable.put(Category.MEDICAL, .1);
    	budgettable.put(Category.INSURANCE, .1);
    	budgettable.put(Category.PERSONAL, .1);
    	budgettable.put(Category.EDUCATION, .1);
    	budgettable.put(Category.SAVINGS, .05);
    	budgettable.put(Category.EXTRA_SPENDING, .05);
    	budgettable.put(Category.OTHER, .05);
    	return budgettable;
	}

	public void removeCustomBudget(int removeid) {
		dao.removeCustomBudget(removeid);
	}
	

	
	
}
