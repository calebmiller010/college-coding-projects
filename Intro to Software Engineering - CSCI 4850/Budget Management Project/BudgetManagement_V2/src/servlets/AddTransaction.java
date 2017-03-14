package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bizlayer.BizLogicData;
import objects.User;

/**
 * Servlet implementation class AddTransaction
 */
@WebServlet("/AddTransaction")
public class AddTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTransaction() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect ("/BudgetManagement_V2/budget");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User currentuser = (User)session.getAttribute("user");
    	BizLogicData data = new BizLogicData();
		int i=2;
		session.setAttribute("tab", i);

        
		String description = request.getParameter("description");
		String amt = request.getParameter("income");
		String recurring = request.getParameter("recurring");
        String category = request.getParameter("category");
        String comment = request.getParameter("comment");
        String paydue = null;
        Boolean addupcomingpayment = false;
        if(category.compareTo("addnewcategoryexpense") == 0)
        	category = request.getParameter("newcategoryexpense");
        
		int type = Integer.parseInt(recurring);
		double amount = 0;
		//multiplier is either 1 (one-time income), 1/12 (yearly income) or -1 (any expense)
        double multiplier = 1; 
		
        if (amt == null) {
        	amt = request.getParameter("expense");
        	multiplier = -1;
        	paydue = request.getParameter("paydue");
        	if(paydue!=null && !paydue.isEmpty()){
        		addupcomingpayment = true;
        	}
        }else {
        	if (type == 1) {
        		multiplier = (double)1/12;
        	}
        }
        
        amount = Double.parseDouble(amt);
        amount = amount * multiplier;
            	
    	int transactionid = data.addTransaction(currentuser.getId(), description, amount, type, comment, category);
    	if(addupcomingpayment) {
    		int pdate = Integer.parseInt(paydue);
    		data.addUpcomingPayment(currentuser.getId(), transactionid, pdate); 
    	}
		response.sendRedirect ("/BudgetManagement_V2/budget");

	}

}
