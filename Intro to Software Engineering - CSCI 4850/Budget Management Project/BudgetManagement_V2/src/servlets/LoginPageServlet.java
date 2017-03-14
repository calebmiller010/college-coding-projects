package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.BCrypt;
import db.DataObject;
import objects.User;

@SuppressWarnings("serial")
@WebServlet("/budget")
public class LoginPageServlet extends HttpServlet {
	private User currentuser = new User();
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(true);
    	
    	if(session.getAttribute("user") != null) { 
        	// if the user is logged in, forward them to home page
            doPost(request,response);
        }
    	else
    		request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //main.jsp  login.jsp newuser.jsp loginpageservlet.java mainpageservlet.java newuserservlet.java 
    	HttpSession session = request.getSession(true);
    	DataObject db = new DataObject();
    	
    	
    	if(session.getAttribute("user") != null) { 
        	// if the user is logged in, forward them to home page
  			//response.sendRedirect ("/BudgetManagement_V2/budget");
            request.getRequestDispatcher("/home").forward(request, response);
            return;
        }
    	int tab = 1;
   		session.setAttribute("tab",tab);
        		
    	Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);
        
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        
        request.setAttribute("tryusername", name);
        if (name == null || name.trim().isEmpty()) {
            messages.put("username", "Please enter name");
        } else if(!validUserName(name, db)) {
        	messages.put("username", "Username does not exist!!");
        	request.removeAttribute("tryusername");
    	} else if (password == null || password.trim().isEmpty()) {
            messages.put("password", "Please enter password");
        } else if (!validPassword(name,password, db)) {
    		messages.put("password", "Incorrect password!!");
    	} 

        if(messages.isEmpty()) {
        	session.setAttribute("user", currentuser);
            request.getRequestDispatcher("/home").forward(request, response);
        }
        else 
        	request.getRequestDispatcher("/login.jsp").forward(request, response);
    
    }

	private boolean validPassword(String name, String password, DataObject db) {
		User user = null;
		user = db.getUserByUsername(name);
		
		if(BCrypt.checkpw(password, user.getPassword())) {
		//if (user.getPassword().equals(password)) {
			currentuser.setId(user.getId());
			currentuser.setUsername(user.getUsername());
			return true;
		}
		return false;
	}

	private boolean validUserName(String name, DataObject db) {
		User user = db.getUserByUsername(name);

		if (user != null)
			return true;
		return false;
	}
    
}