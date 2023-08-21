package com.spt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gen.Loader;

/**
 * Servlet implementation class SPTSubApi
 */
@WebServlet("/Urls")
public class Urls extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Urls() {
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
		Parameter objParameter = new Parameter();

		String res = objParameter.noActionResponse;

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
		PrintWriter out = response.getWriter();
		ActionListener list = new ActionListener();

		try {

			String queryString = printPayload(request);
			objParameter.setField(queryString, objParameter);
			System.out.println("----------" + objParameter.getAction());
			if (objParameter.getAction().equalsIgnoreCase("1")) { // Create Ordeer
				res = list.checkAni(objParameter, Loader.contentConn);
			} else if (objParameter.getAction().equalsIgnoreCase("2")) {
				res = list.addAni(objParameter, Loader.contentConn);
			} else if (objParameter.getAction().equalsIgnoreCase("3")) {
				res = list.addToConv(objParameter, Loader.contentConn);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(res);
	}

	public String printPayload(HttpServletRequest req) {
		try {
			BufferedReader br = req.getReader();
			String query = br.readLine();
			System.out.println("QUERY STRING *********" + query);
			br.close();
			return query;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
