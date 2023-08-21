package callback;

import static gen.Configurator.getInstance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gen.Configurator;
import gen.DataCollector;
import gen.Loader;
import gen.Parameter;

/**
 * Servlet implementation class Callback
 */
@WebServlet("/callback")
public class callback extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String CallbackPath;
	private static Configurator configurator = getInstance();
	private DataCollector dc = new DataCollector();
	Parameter objparam = new Parameter();
	String url = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public callback() {
		super();
		this.CallbackPath = "/home/SDPLOGS/gameomania/";
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml");
		final PrintWriter out = response.getWriter();
		try {
			final StringBuffer jb = new StringBuffer();
			String line = null;

			/*
			 * final BufferedReader reader = request.getReader(); while ((line =
			 * reader.readLine()) != null) { jb.append(line); }
			 */

			String data = request.getQueryString();
			System.out.println("Callback data::" + data);

			String billingtype = "", stsbillingref = "", charge = "";
			String type = request.getParameter("type");
			String sub_date_time = request.getParameter("date");
			String action = request.getParameter("action");
			String ani = request.getParameter("msisdn");
			String network = request.getParameter("network");
			String guid = request.getParameter("guid");
			String sub_id = request.getParameter("subscriberid");
			String ref = request.getParameter("ref");
			String tag = request.getParameter("tag");
			if (!type.equalsIgnoreCase("Navigation")) {
				if (!action.equalsIgnoreCase("AlreadySubscribed")) {
					charge = request.getParameter("charge");
					stsbillingref = request.getParameter("StsBillingReference");
				}
			}
			if (type.equalsIgnoreCase("Billing")) {
				billingtype = request.getParameter("billingtype");
			}

			if (action.equalsIgnoreCase("Deletion")) {
				ref = "na";
			}

			if (ref == null || ref.equalsIgnoreCase("null") || ref.equalsIgnoreCase("")) {
				ref = "na";
			}

			this.CreateFile(data);

			String countyCode = "27";
			if (ani.startsWith("0"))
				ani = ani.substring(1);
			if (ani.startsWith("+"))
				ani = ani.substring(1);
			int len = countyCode.length();
			if (ani.substring(0, len).equals(countyCode))
				ani = ani.substring(len);
			if (ani.contains(" "))
				ani = ani.replace(" ", "");

			String servicename = "";
			Statement stmt3 = Loader.contentConn.createStatement();
			String getsvc = configurator.getProperty("getsvc");
			getsvc = getsvc.replace("<guid>", guid);
			System.out.println(getsvc);
			ResultSet rssvc = stmt3.executeQuery(getsvc);
			if (rssvc.next()) {
				servicename = rssvc.getString(1);
			}

			HttpSession session = request.getSession(true);
			if (type.equalsIgnoreCase("Navigation")) {

//				if (servicename.equalsIgnoreCase("GameOMania")) {
//					response.sendRedirect("http://gameomania.thehappytubes.com/home?ani=" + ani);
//				} else if (servicename.equalsIgnoreCase("KidsZone")) {
//					response.sendRedirect("https://kidszone.thehappytubes.com/login?type=onnet&msisdn=" + ani);
//				} else if (servicename.equalsIgnoreCase("CashBattle")) {
//					response.sendRedirect("https://cashbattle.thehappytubes.com/login?type=onnet&msisdn=" + ani);
//				}

				objparam.setSvc_name(servicename);
				objparam = dc.getServiceData(Loader.contentConn, objparam);
				url = objparam.getSvc_url() + "?svc_id=" + objparam.getId() + "&type=onnet&ani=" + ani
						+ "&type=onnet&msisdn=" + ani;
				response.sendRedirect("" + url + "");
				dc.print("", "Successfull Navigation");

			} else if (type.equalsIgnoreCase("Sync")) {
				System.out.println("Data insertion for sub");
				Statement update = Loader.contentConn.createStatement();
				sub_date_time = this.getDateFormat(sub_date_time);
				String insert = "insert into tbl_dlr (type,sub_date_time,action,ani,network,guid,subscriberid,charge,ref,"
						+ "tag,StsBillingReference,datetime,billing_type,servicename) " + "values ('sub','"
						+ sub_date_time + "','" + action + "','" + ani + "','" + network + "','" + guid + "'," + "'"
						+ sub_id + "','0','" + ref + "','" + tag + "','0','" + getCurrentTime() + "','0','"
						+ servicename + "')";
				System.out.println(insert);
				update.executeUpdate(insert);

				if (action.equalsIgnoreCase("Addition") || action.equalsIgnoreCase("AlreadySubscribed")) {
					/*
					 * if (servicename.equalsIgnoreCase("GameOMania")) {
					 * response.sendRedirect("http://gameomania.thehappytubes.com/home?ani=" + ani);
					 * } else if (servicename.equalsIgnoreCase("KidsZone")) { response.sendRedirect(
					 * "https://kidszone.thehappytubes.com/login?type=onnet&msisdn=" + ani); } else
					 * if (servicename.equalsIgnoreCase("CashBattle")) { response.sendRedirect(
					 * "https://cashbattle.thehappytubes.com/login?type=onnet&msisdn=" + ani); }
					 */

					objparam.setSvc_name(servicename);
					objparam = dc.getServiceData(Loader.contentConn, objparam);
					url = objparam.getSvc_url() + "?svc_id=" + objparam.getId() + "&type=onnet&ani=" + ani
							+ "&type=onnet&msisdn=" + ani;
					response.sendRedirect("" + url + "");
					dc.print("", "Successfull Navigation");
				}

			} else if (type.equalsIgnoreCase("Billing")) {
				System.out.println("Data insertion for billing");
				Statement update = Loader.contentConn.createStatement();
				sub_date_time = this.getDateFormat(sub_date_time);
				String insert = "insert into tbl_dlr (type,sub_date_time,action,ani,network,guid,subscriberid,charge,ref,"
						+ "tag,StsBillingReference,datetime,billing_type,servicename) " + "values ('billing','"
						+ sub_date_time + "','" + action + "','" + ani + "','" + network + "','" + guid + "'," + "'"
						+ sub_id + "','" + charge + "','" + ref + "','" + tag + "','" + stsbillingref + "'," + "'"
						+ getCurrentTime() + "','" + billingtype + "','" + servicename + "')";
				System.out.println(insert);
				update.executeUpdate(insert);
			}

			out.println("Callback received successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void CreateFile(String Data) {
		final Long FileName = get_Time();
		final String Rand = get_rand();
		try {
			final File hFile = new File(String.valueOf(this.CallbackPath) + FileName + Rand + ".txt");
			final FileWriter hFileWriter = new FileWriter(hFile, false);
			hFileWriter.write(Data.toString());
			hFileWriter.close();
			System.out.println(Data.toString());
			System.out.println("Text File Created :: " + this.CallbackPath + FileName + Rand + ".txt");
			final File hFilelck = new File(String.valueOf(this.CallbackPath) + FileName + Rand + ".lck");
			final FileWriter hFilelck2 = new FileWriter(hFilelck, false);
			hFilelck2.write("0");
			hFilelck2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static long get_Time() {
		final Calendar lCDateTime = Calendar.getInstance();
		final long Time1 = lCDateTime.getTimeInMillis();
		return Time1;
	}

	private static String get_rand() {
		final Random r = new Random();
		final int value = r.nextInt(10) + 9;
		final String rand = String.valueOf(value);
		return rand;
	}

	private String getDateFormat(String date) {
		String format_date = "";
		date = date.replace("%3a", ":");
		date = date.replace("+", " ");
		date = date.replace(" PM", "");
		date = date.replace(" AM", "");
		format_date = date;
		System.out.println(format_date);
		return format_date;

	}

	public String getCurrentTime() {
		String dt = "";
		try {
			Date localTime = new Date();

			DateFormat converter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			converter.setTimeZone(TimeZone.getTimeZone("GMT+2"));

			System.out.println("local time : " + localTime);
			;
			System.out.println("time in Tanzania : " + converter.format(localTime));

			dt = converter.format(localTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt;
	}

}
