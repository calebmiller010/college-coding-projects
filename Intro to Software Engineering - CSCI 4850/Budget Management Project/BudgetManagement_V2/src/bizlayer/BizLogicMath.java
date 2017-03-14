package bizlayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import objects.Account;
import objects.Category;
import objects.ExpenseCategoryInformation;
import objects.Payment;
import objects.Transaction;

public class BizLogicMath {
	
	

	public List<List<Payment>> getoverduepayments(List<Payment> payments) {
		List<List<Payment>> bothlists = new ArrayList<List<Payment>>(3);
		// overdue payments = index 0
		// upcoming payments = index 1
		List<Payment> overdue = new ArrayList<Payment>();
		List<Payment> upcoming = new ArrayList<Payment>();
		List<Payment> paid = new ArrayList<Payment>();
		
		Calendar cal = Calendar.getInstance();
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		
		for(Payment payment:payments) {
			if(payment.isPaid()) {
				paid.add(payment);
			}
			else {
				if(payment.getDaydue() < dayOfMonth)
					overdue.add(payment);
				else
					upcoming.add(payment);
			}
		}
		overdue = sortunpaidlist(overdue);
		upcoming = sortunpaidlist(upcoming);
		paid = sortpaidlist(paid);
		bothlists.add(overdue);
		bothlists.add(upcoming);
		bothlists.add(paid);
		return bothlists;
	}
	
	

	public List<String> getExpenseCategoriesFromTransactionList(List<Transaction> transactions) {
		List<String> categories = new ArrayList<String>(10);
		categories.add(Category.FOOD.toString());
		categories.add(Category.SHELTER.toString());
		categories.add(Category.UTILITIES.toString());
		categories.add(Category.CLOTHING.toString());
		categories.add(Category.TRANSPORTATION.toString());
		categories.add(Category.MEDICAL.toString());
		categories.add(Category.INSURANCE.toString());
		categories.add(Category.PERSONAL.toString());
		categories.add(Category.EDUCATION.toString());
		categories.add(Category.SAVINGS.toString());
		categories.add(Category.EXTRA_SPENDING.toString());

		for(Transaction t:transactions) {
			if (t.getAmount() < 0) {
				if( !categories.contains(t.getCategory()) ) { 
					categories.add(t.getCategory());
				}
			}
		}
		return categories;
	}
	
	
	/*
	public List<Payment> sortunpaidlist(List<Payment> list) {
		 Payment temp;
	        if (list.size()>1)
	        {
	            for (int x=0; x<list.size(); x++) 
	            {
	                for (int i=1; i < list.size()-x; i++) {
	                    if (list.get(i-1).getDaydue() > list.get(i).getDaydue())
	                    {
	                        temp = list.get(i-1);
                        	list.set(i-1,list.get(i) );
                        	list.set(i, temp);
	                    }
	                }
	            }
	        }
	        return list;
	}
	
	public List<Payment> sortpaidlist(List<Payment> list) {
		 Payment temp;
	        if (list.size()>1)
	        {
	            for (int x=0; x<list.size(); x++) 
	            {
	                for (int i=1; i < list.size()-x; i++) {
	                    if (list.get(i-1).getDatepaid() < list.get(i).getDatepaid())
	                    {
	                        temp = list.get(i-1);
                        	list.set(i-1,list.get(i) );
                        	list.set(i, temp);
	                    }
	                }
	            }
	        }
	        return list;
	}
	*/
	
	public List<Payment> sortunpaidlist(List<Payment> list) {
		return sortcommon(list,false);
	}
	public List<Payment> sortpaidlist(List<Payment> list) {
		return sortcommon(list,true);
	}
	
	public List<Payment> sortcommon(List<Payment> list, Boolean paid) {
		 Payment temp;
	        if (list.size()>1)
	        {
	            for (int x=0; x<list.size(); x++) 
	            {
	                for (int i=1; i < list.size()-x; i++) {
	                    if ( ( list.get(i-1).getDaydue() > list.get(i).getDaydue() || paid ) && ! ( list.get(i-1).getDaydue() > list.get(i).getDaydue() && paid  ))
	                    {
	                    temp = list.get(i-1);
                       	list.set(i-1,list.get(i) );
                       	list.set(i, temp);
	                    }
	                }
	            }
	        }
	        return list;
	}
	
	
	/*
	public double totalIncome(List<Transaction> incomes) {
		double sum = 0.0;
		for(Transaction t:incomes) {
			if (t.getAmount() > 0)
				sum += Math.round(t.getAmount() * 100.0) / 100.0;
		}
		return sum;
	}
	
	public double totalSpending(List<Transaction> spendings) {
		double sum = 0.0;
		for(Transaction t:spendings) {
			if (t.getAmount() < 0)
				sum += Math.round(t.getAmount() * 100.0) / 100.0;
		}
		return sum;
	}*/
	
	public double totalSpending(List<Transaction> transactions) {
		return totalCommon(transactions,false);
	}
	
	public double totalIncome(List<Transaction> transactions) {
		return totalCommon(transactions,true);
	}
	
	public double totalCommon(List<Transaction> list, Boolean income) {
		double sum = 0.0;
		for(Transaction t:list) {
			if (( income || t.getAmount() < 0 ) && ! ( income && t.getAmount() < 0 ) )
				sum += Math.round(t.getAmount() * 100.0) / 100.0;
		}
		return sum;		
	}



	public List<ExpenseCategoryInformation> getCategoriesOverview(Account currentaccount) {
		List<ExpenseCategoryInformation> list = new ArrayList<ExpenseCategoryInformation>();
		
		for(Category c:Category.values()) {
			ExpenseCategoryInformation temp = new ExpenseCategoryInformation();
			temp.setCategory(c.toString());
			double actualspending = getactualspending(currentaccount.getTransactions(), c);
			temp.setActualspending(actualspending);
			double income = currentaccount.getTotalincome();
			double percent = currentaccount.getBudget().getPercentFromCategory(c);
			double br = Math.round(income * percent * 100.0) / 100.0;
			temp.setBudgetrecommendation(br);
			temp.setBudgetpercent((int)(percent*100));
			list.add(temp);
		}
		
		//The goal is to return a list of things with a:
		// category, budgetrecommendation, actualspending, difference, and budgetpercent
		return list;
	}

	private double getactualspending(List<Transaction> transactions, Category c) {
		double sum=0.0;
		for(Transaction t:transactions) {
			if(t.getAmount() < 0) {
				Category a = Category.getValue(t.getCategory());
				if(a.equals(c)) {
					sum += t.getAmount();
				}
			}
		}
		if(sum == 0)
			return 0;
		return sum * -1;
	}
	
}
