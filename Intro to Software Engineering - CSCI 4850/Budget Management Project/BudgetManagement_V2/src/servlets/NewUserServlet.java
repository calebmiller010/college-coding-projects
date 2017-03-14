package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DataObject;
import objects.User;

/**
 * Servlet implementation class NewUserServlet
 */
@WebServlet("/newuser")
public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DataObject db = new DataObject();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/newuser.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);
        
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmpassword = request.getParameter("confirmpassword");
        
        
        User add = new User();
        add.setUsername(name);
        add.setPassword(password);;
    	request.setAttribute("username",name);

    	if(name == null || name.trim().isEmpty())
        	messages.put("username", "Please enter username for new user!");
    	else if (!validateusername(name)) {
        	messages.put("username", "Username already exists!!");
        	request.removeAttribute("username"); }
        else if(password == null || password.trim().isEmpty())
        	messages.put("password", "Must enter password!");
        else if(name == null || confirmpassword.trim().isEmpty())
        	messages.put("confirmpassword", "Must enter password!");
        else if(!password.equals(confirmpassword)) {
        	messages.put("password", "Passwords do not match!!");
        }
        
        if(messages.isEmpty()) {
        	messages.put("success", "<b>" + name + "</b> added successfully!!");
        	request.removeAttribute("username");
        	db.addNewUser(add);
        }
        	request.getRequestDispatcher("/newuser.jsp").forward(request, response);

	}
	
	private boolean validateusername(String name) {
		User user = db.getUserByUsername(name);
		
		if(user == null)
			return true;
		return false;
	}

}
