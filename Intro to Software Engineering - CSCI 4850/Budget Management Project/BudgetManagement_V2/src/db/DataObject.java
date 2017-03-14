package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import connectdb.ConnectionManager;
import objects.Category;
import objects.Payment;
import objects.Transaction;
import objects.User;

public class DataObject {
	Connection connection = null;
	
	public DataObject () {
		if (connection == null) {
			ConnectionManager c = new ConnectionManager();
			connection = c.getConnectionTest();
		}
	}
    
	public List<Transaction> getAllTransactionsByUserId(int id) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        String mysql = "SELECT * FROM transactions WHERE userid = ?";
       
        try {
            PreparedStatement stmt = connection.prepareStatement(mysql);
			stmt.setInt(1, id);
	        ResultSet resultSet = stmt.executeQuery(); 
            while (resultSet.next()) {
                Transaction trans = new Transaction();
                trans.setId(resultSet.getInt("userid"));
                trans.setName(resultSet.getString("name"));
                trans.setAmount(resultSet.getDouble("amount"));
                trans.setComment(resultSet.getString("comment"));
                if(resultSet.getInt("type") == 0)
                	trans.setRecurring(false);
                else
                	trans.setRecurring(true);
                trans.setCategory(resultSet.getString("category"));
                trans.setTransactionid(resultSet.getInt("transactionid"));
                transactions.add(trans);
	        }
        } catch(SQLException e) {
        	e.printStackTrace();
        }
        return transactions;
    }
	    
    public User getUserByUsername (String username) {
    	String mysql = "SELECT * FROM logIn WHERE userID=?";
    	
    	try {
    		PreparedStatement stmt = connection.prepareStatement(mysql);
 			stmt.setString(1, username);
 	        ResultSet resultSet = stmt.executeQuery();
	    	if(resultSet.next()) {
	    		User user = new User();
	    		user.setId(resultSet.getInt("id"));
	    		user.setPassword(resultSet.getString("password"));
	    		user.setUsername(resultSet.getString("userID"));
	    		return user;
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
		return null;
    }
    
    public void addNewUser (User newuser) {
    	String mysql = "INSERT INTO logIn (userID, password) VALUES (?,?)";
    	
    	try {
    		PreparedStatement stmt = connection.prepareStatement(mysql);
 			stmt.setString(1, newuser.getUsername());
 			stmt.setString(2, BCrypt.hashpw(newuser.getPassword(), BCrypt.gensalt()));//newuser.getPassword());
 	        stmt.executeUpdate();
    	} catch (SQLException e) { 
    		e.printStackTrace();
    	}
    }
    
    public int addTransaction (int userid, String name, double amount, int type, String comment, String category) {
    	String mysql = "INSERT INTO transactions (userid, name, amount, type, comment, category) VALUES (?, ?, ?, ?, ?, ?)";
    	
    	try {
    		PreparedStatement stmt = connection.prepareStatement(mysql);
    		stmt.setInt(1, userid);
    		stmt.setString(2, name);
    		stmt.setDouble(3, amount);
    		stmt.setInt(4, type);
    		stmt.setString(5, comment);
    		stmt.setString(6,category);
    		stmt.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}

    	return returnlastinsertid();

    }
    
    public int returnlastinsertid() {
    	String mysql = "SELECT LAST_INSERT_ID()";
    	
    	try {
    		PreparedStatement stmt = connection.prepareStatement(mysql);
    		ResultSet resultSet = stmt.executeQuery();
	    	if(resultSet.next()) {
	    		return resultSet.getInt(1);
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return 0;
    }

	public void removeTransaction(int removeid) {
		String mysql = "DELETE FROM transactions WHERE transactionid=?";
		
		try{
			PreparedStatement stmt = connection.prepareStatement(mysql);
			stmt.setInt(1, removeid);
			stmt.executeUpdate();
		} catch (SQLException e) { 
    		e.printStackTrace();
    	}
	}

	public void addPayment(int userid, int transactionid, int pdate) {
		String mysql = "INSERT INTO paymentsdue (userid, transactionid, daydue, paid) VALUES (?, ?, ?, ?)";
    	
    	try {
    		PreparedStatement stmt = connection.prepareStatement(mysql);
    		stmt.setInt(1, userid);
    		stmt.setInt(2, transactionid);
    		stmt.setInt(3, pdate);
    		stmt.setInt(4, 0); // payment always starts out unpaid
    		stmt.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	}

	public List<Payment> getAllPaymentsByUserId(int id) {
		List<Payment> payments = new ArrayList<Payment>();
        String mysql = "SELECT * FROM paymentsdue WHERE userid = ?";
       
        try {
            PreparedStatement stmt = connection.prepareStatement(mysql);
			stmt.setInt(1, id);
	        ResultSet resultSet = stmt.executeQuery(); 
            while (resultSet.next()) {
                Payment payment = new Payment(resultSet.getInt("paymentid"), resultSet.getInt("daydue"), resultSet.getInt("paid"));
                payment.setTransactionid(resultSet.getInt("transactionid"));
                payment.setDatepaid(resultSet.getInt("datepaid"));
                payments.add(payment);
	        }
        } catch(SQLException e) {
        	e.printStackTrace();
        }
        return payments;
	}

	public void payBill(int paymentid, int dayOfMonth) {
		String mysql = "UPDATE paymentsdue SET paid=?, datepaid=? WHERE paymentid=?";
		
		try{
			PreparedStatement stmt = connection.prepareStatement(mysql);
			stmt.setInt(1, 1);
			stmt.setInt(2, dayOfMonth);
			stmt.setInt(3, paymentid);
			stmt.executeUpdate();
		} catch (SQLException e) { 
    		e.printStackTrace();
    	}
	}

	public void removePayment(int removeid) {
		String mysql = "DELETE FROM paymentsdue WHERE transactionid=?";
		
		try{
			PreparedStatement stmt = connection.prepareStatement(mysql);
			stmt.setInt(1, removeid);
			stmt.executeUpdate();
		} catch (SQLException e) { 
    		e.printStackTrace();
    	}
	}

	public HashMap<Category, Double> getBudget(int id) {
		HashMap<Category, Double> budgettable = new HashMap<Category,Double>();
        String mysql = "SELECT * FROM custombudget WHERE userid = ?";
       
        try {
            PreparedStatement stmt = connection.prepareStatement(mysql);
			stmt.setInt(1, id);
	        ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()) {
            	budgettable.put(Category.FOOD, resultSet.getDouble("food"));
            	budgettable.put(Category.SHELTER, resultSet.getDouble("shelter"));
            	budgettable.put(Category.UTILITIES, resultSet.getDouble("utilities"));
            	budgettable.put(Category.CLOTHING, resultSet.getDouble("clothing"));
            	budgettable.put(Category.TRANSPORTATION, resultSet.getDouble("transportation"));
            	budgettable.put(Category.MEDICAL, resultSet.getDouble("medical"));
            	budgettable.put(Category.INSURANCE, resultSet.getDouble("insurance"));
            	budgettable.put(Category.PERSONAL, resultSet.getDouble("personal"));
            	budgettable.put(Category.EDUCATION, resultSet.getDouble("education"));
            	budgettable.put(Category.SAVINGS, resultSet.getDouble("savings"));
            	budgettable.put(Category.EXTRA_SPENDING, resultSet.getDouble("extraspending"));
            	budgettable.put(Category.OTHER, resultSet.getDouble("other"));
                return budgettable;
	        }
        } catch(SQLException e) {
        	e.printStackTrace();
        }
		
		return null;
	}
	
	public void addCustombudget(int id, HashMap<Category, Double> budgets) {
		String mysql = "INSERT INTO custombudget (userid, food, shelter, utilities, clothing, transportation, medical, insurance, personal, education, savings, extraspending, other) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	
		try {
    		PreparedStatement stmt = connection.prepareStatement(mysql);
    		stmt.setInt(1, id);
    		stmt.setDouble(2, budgets.get(Category.FOOD));
    		stmt.setDouble(3, budgets.get(Category.SHELTER));
    		stmt.setDouble(4, budgets.get(Category.UTILITIES));
    		stmt.setDouble(5, budgets.get(Category.CLOTHING));
    		stmt.setDouble(6, budgets.get(Category.TRANSPORTATION));
    		stmt.setDouble(7, budgets.get(Category.MEDICAL));
    		stmt.setDouble(8, budgets.get(Category.INSURANCE));
    		stmt.setDouble(9, budgets.get(Category.PERSONAL));
    		stmt.setDouble(10, budgets.get(Category.EDUCATION));
    		stmt.setDouble(11, budgets.get(Category.SAVINGS));
    		stmt.setDouble(12, budgets.get(Category.EXTRA_SPENDING));
    		stmt.setDouble(13, budgets.get(Category.OTHER));
    		stmt.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	}
	
	public void removeCustomBudget(int id) {
		String mysql = "DELETE FROM custombudget WHERE userid=?";
		
		try{
			PreparedStatement stmt = connection.prepareStatement(mysql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) { 
    		e.printStackTrace();
    	}
	}
	
	/*
	public void updateCustombudget(int id, HashMap<Category, Double> budgets) {
		String mysql = "UPDATE custombudget SET shelter=?, food=?, utilities=?, clothing=?, transportation=?, medical=?, insurance=?, personal=?, education=?, savings=?, extraspending=?, other=? WHERE userid=?";
		
		try {
    		PreparedStatement stmt = connection.prepareStatement(mysql);
    		stmt.setDouble(1, budgets.get(Category.FOOD));
    		stmt.setDouble(2, budgets.get(Category.SHELTER));
    		stmt.setDouble(3, budgets.get(Category.UTILITIES));
    		stmt.setDouble(4, budgets.get(Category.CLOTHING));
    		stmt.setDouble(5, budgets.get(Category.TRANSPORTATION));
    		stmt.setDouble(6, budgets.get(Category.MEDICAL));
    		stmt.setDouble(7, budgets.get(Category.INSURANCE));
    		stmt.setDouble(8, budgets.get(Category.PERSONAL));
    		stmt.setDouble(9, budgets.get(Category.EDUCATION));
    		stmt.setDouble(10, budgets.get(Category.SAVINGS));
    		stmt.setDouble(11, budgets.get(Category.EXTRA_SPENDING));
    		stmt.setDouble(12, budgets.get(Category.OTHER));
    		stmt.setInt(13, id);
    		stmt.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	}*/

}
