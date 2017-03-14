package servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import bizlayer.*;
import objects.Account;
import objects.ExpenseCategoryInformation;
import objects.Payment;
import objects.User;

@WebServlet("/home")
public class MainPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	BizLogicData data = new BizLogicData();
    	BizLogicMath math = new BizLogicMath();
    	HttpSession session = request.getSession(true);
    	
    	User currentuser = (User)session.getAttribute("user");
    	int i = (int)session.getAttribute("tab");
    	request.setAttribute("tab", i);

    	Account currentaccount = new Account(currentuser, data.getAllTransactionsByUserId(currentuser.getId()), data.getBudget(currentuser.getId()), data.getAllPaymentsByUserId(currentuser.getId()));
    	
    	List<List<Payment>> sortedpayments = math.getoverduepayments(currentaccount.getPayments());        
 
        List<ExpenseCategoryInformation> categoriesoverview = math.getCategoriesOverview(currentaccount); 
        int value[] = new int[12];
        int a=0;
        for(ExpenseCategoryInformation e:categoriesoverview) {
        	value[a]=(int)e.getBudgetpercent();
        	e.setBudgetpercent(Math.round(e.getBudgetpercent()));
        	a++;
        }
        
        request.setAttribute("categories", math.getExpenseCategoriesFromTransactionList(currentaccount.getTransactions())); 
        request.setAttribute("transactions", currentaccount.getTransactions()); // Will be available as ${transactions} in JSP
        request.setAttribute("overduepayments", sortedpayments.get(0)); 
        request.setAttribute("upcomingpayments", sortedpayments.get(1));
        request.setAttribute("paid", sortedpayments.get(2));
        request.setAttribute("categoriesoverview", categoriesoverview);
        request.setAttribute("budgetpercentvalues", value);
        
        Calendar cal = Calendar.getInstance();
		String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		request.setAttribute("month", month);
        
       
        request.setAttribute("totalincome", currentaccount.getTotalincome());
        request.setAttribute("totalspending", currentaccount.getTotalexpenses());
       
        request.getRequestDispatcher("/main.jsp").forward(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(true);
    	User currentuser = (User)session.getAttribute("user");
    	if(currentuser != null)
    		doPost(request,response);
    	else
    		response.sendRedirect ("/BudgetManagement_V2/budget");
    }

}