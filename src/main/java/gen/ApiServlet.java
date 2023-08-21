package gen;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ApiServlet
 */
@WebServlet("/ApiServlet")
public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "{\"status\":\"0\",\"error\":\"action not defined\"}";
        try {
            final PrintWriter out = response.getWriter();
            if (request.getParameter("action").equalsIgnoreCase("1")) {
                result = new Conversion().insertLogs(request.getParameter("cid"), Loader.contentConn,
                		request.getParameter("id"));
            }
            System.out.println("This is result :: " + result);
            out.print(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}

}
