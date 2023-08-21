package gen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		String Url = "/landing";
		try {

			String type = request.getParameter("type");
			String ref = request.getParameter("ref");
			String ani=new DataCollector().checkAni(request.getParameter("number"));
			System.out.println("Ani :" + ani);
			System.out.println("type :" + type);

			String status = new DataCollector().getStatus(ani);
			System.out.println(status);
			if (status.equalsIgnoreCase("1")) {
				session.setAttribute("ani", ani);
				response.sendRedirect("home?ani="+ani);
				Url = "home";
			} 
			else {
				if(type.equalsIgnoreCase("onnet")) {
					response.sendRedirect("http://sdp.smartcalltech.co.za/Traffic/df3846a6-8048-4a1c-a9f2-99cb2f8f99f2/");
					Url = "home";
				}
				else if (type.equalsIgnoreCase("offnet")) {
					/*
					 * response.sendRedirect(
					 * "http://sdp.smartcalltech.co.za/sms/df3846a6-8048-4a1c-a9f2-99cb2f8f99f2/27"+
					 * ani+"/?ref="+ref); Url = "home";
					 */
					String output = "";
					String flag = "";
					String URL = "http://sdp.smartcalltech.co.za/sms/df3846a6-8048-4a1c-a9f2-99cb2f8f99f2/27"+ani+"/?ref="+ref;
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost postRequest = new HttpPost(URL);
					System.out.println(postRequest);
					HttpResponse resp = httpClient.execute(postRequest);
					BufferedReader br = new BufferedReader(new InputStreamReader((resp.getEntity().getContent())));
					
					System.out.println("Output from Server ....");
					while ((output = br.readLine()) != null) {
						System.out.println("output::" + output);
						flag = output;
					}
					if(flag.equalsIgnoreCase("TRUE")) {
						response.sendRedirect("ThanksPage?flag=TRUE&ref="+ref+"&ani="+ani);
					}
					else {
						response.sendRedirect("ThanksPage?flag=FALSE&ref="+ref+"&ani="+ani);
					}
				}
				
			}
//			else if (status.equalsIgnoreCase("0")) {
//				request.setAttribute("errorMessage", "Please recharge your account to gain entry to GameOMania");
//			}else if (status.equalsIgnoreCase("2")) {
//				response.sendRedirect("http://optin.telkomsdp.co.za/service/68");
//				Url = "index";
//			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Something Went Wrong");
		}
		/*
		 * if(Url != "home") { RequestDispatcher dispatcher =
		 * request.getRequestDispatcher("/"+Url+""); dispatcher.forward(request,
		 * response); }
		 */
		
	}

}
