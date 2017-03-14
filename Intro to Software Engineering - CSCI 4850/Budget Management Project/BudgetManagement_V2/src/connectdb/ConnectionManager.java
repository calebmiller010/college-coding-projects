package connectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
	static String url = "jdbc:mysql://ec2budgetmanager.ddns.net:3306/myDB";
	static String user = "database";
	static String password = "password";
	private Connection connection;
	
	public ConnectionManager() {
		connection = null;
	}
	
	public Connection getConnectionTest() {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;	
	}
	
	public Connection getConnectionPool() {
		if(connection==null) {
			Context ctx;
			DataSource ds = null;
			try {
				ctx = new InitialContext();
				ds = (DataSource)ctx.lookup("java:comp/env/jdbc/budgetmanager");
			} catch (NamingException e1) {
				e1.printStackTrace();
			}
			try {
				connection = ds.getConnection();
			}
			catch (SQLException e) {
			    System.out.println(e);
			}
		}
		return connection;
	}
}
