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
 * Servlet implementation class DeleteCustomBudget
 */
@WebServlet("/DeleteCustomBudget")
public class DeleteCustomBudget extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCustomBudget() {
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
		int i=3;
		session.setAttribute("tab", i);
		User u = (User)session.getAttribute("user");
		
		BizLogicData data = new BizLogicData();
		
		data.removeCustomBudget(u.getId());
		response.sendRedirect ("/BudgetManagement_V2/budget");
	}


}
