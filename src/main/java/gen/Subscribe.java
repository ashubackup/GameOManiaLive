package gen;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gen.conv.Conversion;

/**
 * Servlet implementation class Subscribe
 */
@WebServlet("/Subscribe")
public class Subscribe extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Conversion cv = new Conversion();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Subscribe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String Url = "/landing";
		boolean status = false;
		try {
			String clickid = request.getParameter("clickid");
			String number = request.getParameter("number");
			number = new DataCollector().checkAni(number);
			if (clickid != null ) {
				String pubid = request.getParameter("pubid");
				String provider = request.getParameter("V");
				String svc = request.getParameter("svc");
				cv.addToConv(number, svc, clickid, pubid, provider);
				 status = new DataCollector().addConvNewSUb(Loader.contentConn,number);

			}else {
				 status = new DataCollector().addNewSUb(Loader.contentConn,number);

			}
			if(status) {
				request.setAttribute("subApplMessage", "Your Subscription is on progress");
			}else {
				request.setAttribute("subApplMessage", "Something Went Wrong!! Please try again later!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Something Went Wrong");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/"+Url+"");
		dispatcher.forward(request, response);
	}
	


}
