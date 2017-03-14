package servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bizlayer.BizLogicData;
import objects.Category;
import objects.User;

/**
 * Servlet implementation class AddUpdateCustomBudget
 */
@WebServlet("/AddUpdateCustomBudget")
public class AddUpdateCustomBudget extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUpdateCustomBudget() {
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
		int i=3;
		session.setAttribute("tab", i);

		HashMap<Category,Double> budgettable = new HashMap<Category,Double>();
		budgettable.put(Category.FOOD, getDouble(request.getParameter("food")));
    	budgettable.put(Category.SHELTER, getDouble(request.getParameter("shelter")));
    	budgettable.put(Category.UTILITIES, getDouble(request.getParameter("utilities")));
    	budgettable.put(Category.CLOTHING, getDouble(request.getParameter("clothing")));
    	budgettable.put(Category.TRANSPORTATION, getDouble(request.getParameter("transportation")));
    	budgettable.put(Category.MEDICAL, getDouble(request.getParameter("medical")));
    	budgettable.put(Category.INSURANCE, getDouble(request.getParameter("insurance")));
    	budgettable.put(Category.PERSONAL, getDouble(request.getParameter("personal")));
    	budgettable.put(Category.EDUCATION, getDouble(request.getParameter("education")));
    	budgettable.put(Category.SAVINGS, getDouble(request.getParameter("savings")));
    	budgettable.put(Category.EXTRA_SPENDING, getDouble(request.getParameter("extraspending")));
    	budgettable.put(Category.OTHER, getDouble(request.getParameter("other")));
		
    	data.addUpdateBudget(currentuser.getId(), budgettable);
    	
		response.sendRedirect ("/BudgetManagement_V2/budget");
	}

	private Double getDouble(String s) {
		Double d = Double.parseDouble(s);
		return d/100;
	}


}
