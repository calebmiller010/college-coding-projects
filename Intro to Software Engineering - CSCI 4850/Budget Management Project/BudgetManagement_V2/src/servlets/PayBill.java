package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bizlayer.BizLogicData;

/**
 * Servlet implementation class PayBill
 */
@WebServlet("/PayBill")
public class PayBill extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayBill() {
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
		int removeid = Integer.parseInt(request.getParameter("paymentid"));
		HttpSession session = request.getSession(true);
		int i=1;
		session.setAttribute("tab", i);
		
		BizLogicData data = new BizLogicData();
		
		data.payBill(removeid);
		response.sendRedirect ("/BudgetManagement_V2/budget");
	}

}
