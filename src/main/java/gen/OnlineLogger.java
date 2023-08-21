package gen;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OnlineLogger
 */
@WebServlet("/OnlineLogger")
public class OnlineLogger extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OnlineLogger() {
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
		
		response.setContentType("text/html");
        final PrintWriter out = response.getWriter();
        Statement stmt = null;
        final String ani = request.getParameter("ani");
        String gameurl = request.getParameter("url");
        String gameid = request.getParameter("gameid");
        String cat_id = request.getParameter("cat_id");
        final String type = request.getParameter("type");
        gameurl = URLDecoder.decode(gameurl);
        try {
           
            stmt = Loader.contentConn.createStatement();
           final String qry = "insert into tbl_online_play (ani,url,DATETIME,type,cat_id,gameid) values ('" + ani + "','" + gameurl + "',now(),'" + type + "','"+cat_id+"','"+gameid+"')";
           stmt.executeUpdate(qry);
            
            response.sendRedirect("onlinePlay.jsp?gid="+gameid+"");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        out.flush();
        out.close();
	}

}
