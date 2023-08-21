package gen;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Logger_PW
 */
@WebServlet("/Logger_PW")
public class Logger_PW extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logger_PW() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		DbConnection db1 = null;
		Statement stmt = null;
		String ani = request.getParameter("ani");
		String gameurl = request.getParameter("url");
		String type = request.getParameter("type");
		String nick_name = request.getParameter("nick_name");
		String game_id = request.getParameter("id");
		String cli_type = request.getParameter("cli_type");
		System.out.println("type:" + type);
		gameurl = URLDecoder.decode(gameurl);
		String mainUrl = gameurl + "?phone_no=" + ani + "&nick_name=" + nick_name + "&game_id=" + game_id + "&type="
				+ cli_type;
		try {

			db1 = new DbConnection();

			stmt = Loader.contentConn.createStatement();
			String qry = "insert into tbl_online_play (ani,url,DATETIME,type,gameid) values ('" + ani + "','" + gameurl
					+ "',now(),'" + cli_type + "','" + game_id + "')";
			System.out.println(qry);
			stmt.executeUpdate(qry);
			response.sendRedirect(mainUrl);

		} catch (Exception e) {
			System.out.println(e);
		}
		out.flush();
		out.close();
	}
}
