package gen;

import static gen.Configurator.getInstance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataCollector {

	private static Configurator configurator = getInstance();

	public Parameter getServiceData(Connection conn, Parameter objparam) {
		try {
			String query = configurator.getProperty("getServiceDetails");
			query = query.replace("<svc_name>", objparam.getSvc_name());
			ResultSet res = getResultSet(conn, query);
			if (res.next()) {
				objparam.setId(res.getInt(1));
				objparam.setSvc_url(res.getString(2));
				return objparam;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objparam;
	}

	public void print(String info, String data) {
		System.out.println("JAVA -- " + info + " -- " + data);
	}

	public ResultSet getResultSet(Connection conn, String query) {
		ResultSet res = null;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			print("query", query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public ResultSet getCategorie(String id) {
		ResultSet res = null;

		try {
			String query = "select * from tbl_game_cat where status = '1' and sr_no='" + id + "'";
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public ResultSet getCategories() {
		ResultSet res = null;

		try {
			String query = "select * from tbl_game_cat where status = '1' ";
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public static ResultSet getGameByGameId(String id) {
		ResultSet rs = null;
		try {

			String query = "select * from tbl_game_html where gameid='" + id + "'";
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			System.out.println(query);
			rs = ps.executeQuery();

		} catch (Exception e) {

			e.printStackTrace();
		}
		return rs;

	}

	public static ResultSet getGamesbyCatid(String id) {
		ResultSet rs = null;
		try {

			String query = "select * from tbl_game_html where status='1' and cat_id='" + id + "' and vendor='Gentech' ";
			System.out.println(query);
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet getGamesByCatID(String _id) {
		ResultSet res = null;

		try {
			String query = "select * from tbl_portal_game where status in( '1','99','98') and cat_id='" + _id
					+ "' order by year(datetime) desc";
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public ResultSet getTrendingGames() {
		ResultSet res = null;

		try {
			String query = "select * from tbl_portal_game where status = '99'  order by year(datetime) desc limit 6";
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public ResultSet getHighlightGames() {
		ResultSet res = null;

		try {
			String query = "select * from tbl_game_html where banner_url='1' and vendor='Gentech' limit 6";
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			// System.out.println(query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public ResultSet getGamesByCatName(String category) {
		ResultSet res = null;

		try {
			String query = "select * from tbl_portal_game where status not in ('0') and category='" + category + "'";
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			res = ps.executeQuery();
			System.out.println(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public String getStatus(final String ani) {
		String State = "";
		try {

			final Statement stmt = Loader.contentConn.createStatement();
			final String chkqry = "select * from tbl_subscription where ani='" + ani
					+ "' and service_type='GameOMania'  ";
			System.out.println(chkqry);
			final ResultSet rs = stmt.executeQuery(chkqry);
			if (rs.next()) {
				State = this.getUserState(ani, "GameOMania");
			} else {

//                    State = "2";
				State = "0"; // change for demo

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return State;
	}

	public boolean checkUSSDEntry(Connection conn, String ani) {
		try {
			String query = "select * from tbl_ussd_billing where ani='" + ani + "'";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet res = ps.executeQuery();
			System.out.println(query);
			if (res.next()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public boolean addConvNewSUb(Connection conn, String ani) {
		try {
			// String subQuery = "insert into tbl_subscription
			// (ani,sub_date_time,unsub_date_time,m_act,lang,service_type,status,charging_date,billing_date,default_amount,RECORDSTATUS,pack_type,`next_billed_date`,last_billed_date)
			// values
			// ('"+ani+"',now(),NULL,'WEB','e','GameOMania','Active',NULL,NULL,'10','1','Daily',NULL,NULL)";
			String billingQuery = "INSERT INTO tbl_ussd_billing (ani,TOTAL_AMOUNT,DEDUCTED_AMOUNT,ISPREPAID,DATETIME,RECORDSTATUS,ERRORDESC,CIRCLEID,PROCESS_DATE,TYPE_EVENT,IS_EMM,PROCESS_DATETIME,SRC,NOOFATTEMPT,MODE,servicename,subservicename,product_id,pack_type) VALUES ('"
					+ ani
					+ "','20','20','0',NOW(),'5','null','all','null','SUB','0',NULL,'WEB1','0','WEB','games','games',NULL,'Daily')";
			// boolean subStatus = UpdateQuery(conn,subQuery);
			boolean billingStatus = UpdateQuery(conn, billingQuery);
			if (billingStatus) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public boolean addNewSUb(Connection conn, String ani) {
		try {
			// String subQuery = "insert into tbl_subscription
			// (ani,sub_date_time,unsub_date_time,m_act,lang,service_type,status,charging_date,billing_date,default_amount,RECORDSTATUS,pack_type,`next_billed_date`,last_billed_date)
			// values
			// ('"+ani+"',now(),NULL,'WEB','e','games','Active',NULL,NULL,'10','1','Daily',NULL,NULL)";
			String billingQuery = "INSERT INTO tbl_ussd_billing (ani,TOTAL_AMOUNT,DEDUCTED_AMOUNT,ISPREPAID,DATETIME,RECORDSTATUS,ERRORDESC,CIRCLEID,PROCESS_DATE,TYPE_EVENT,IS_EMM,PROCESS_DATETIME,SRC,NOOFATTEMPT,MODE,servicename,subservicename,product_id,pack_type) VALUES ('"
					+ ani
					+ "','20','20','0',NOW(),'5','null','all','null','SUB','0',NULL,'WEB','0','WEB','games','games',NULL,'Daily')";
			// boolean subStatus = UpdateQuery(conn,subQuery);
			boolean billingStatus = UpdateQuery(conn, billingQuery);
			if (billingStatus) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public boolean UpdateQuery(Connection conn, String query) {
		try {
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			int count = ps.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public String checkAni(String ani) {
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
		return ani;
	}

	public String getUserState(final String ani, final String service) {
		String State = "0";
		try {
			Statement stmt = null;
			stmt = Loader.contentConn.createStatement();
			int cnt = 0;
			final String subQry = "select count(1) as cnt from tbl_subscription where ani='" + ani
					+ "' and service_type='" + service + "' " + "and date(next_billed_date)>=  Date(subdate(now(),10))";
			System.out.println("subQry::::" + subQry);
			final ResultSet rssub = stmt.executeQuery(subQry);
			if (rssub.next()) {
				cnt = rssub.getInt(1);
				System.out.println("cnt~~" + cnt);
			}
			if (cnt > 0) {
				State = "1";
			} else {
				State = "2";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return State;
	}

	public void MobileLogs(String ani, String os, Connection conn) {

		try {
			Statement stmt = conn.createStatement();

			if (os.contains("iPhone")) {
				os = "iPhone";
			} else if (os.contains("Android")) {
				os = "Android";
			} else {
				os = "Windows";
			}

			String datetime = getCurrentDateTime();
			String insertQry = "insert into tbl_handsetlogs(ani,os,datetime)" + " values ('" + ani + "','" + os + "','"
					+ datetime + "')";
			System.out.println(insertQry);
			stmt.executeUpdate(insertQry);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getCurrentDateTime() {
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

	public void UserJourney(String ani, String page, Connection conn) {

		try {
			Statement stmt = conn.createStatement();

			String datetime = getCurrentDateTime();
			String insertQry = "insert into tbl_user_Journey(ani,page,datetime)" + " values ('" + ani + "','" + page
					+ "','" + datetime + "')";
			System.out.println(insertQry);
			stmt.executeUpdate(insertQry);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void GamePlay(String ani, String gameurl, String cat_id, String category, String imgurl, String gid,
			Connection conn) {

		try {
			Statement stmt = conn.createStatement();

			String datetime = getCurrentDateTime();
			String insertQry = "insert into tbl_online_play(ani,url,gameid,cat_id,type,datetime,imgurl)" + " values ('"
					+ ani + "','" + gameurl + "','" + gid + "','" + cat_id + "','" + category + "','" + datetime + "','"
					+ imgurl + "')";
			System.out.println(insertQry);
			stmt.executeUpdate(insertQry);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
