package callback;

import static gen.Configurator.getInstance;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import gen.Configurator;

public class DLR_Update_Delete {

	private static final String colSep = "#";
	private static Configurator configurator = getInstance();
	Statement stmt = null;
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	Statement stmtUpdate = null;
	Connection conn = null;

	public static void main(String[] args) {
		DLR_Update_Delete DLR_obj = new DLR_Update_Delete();
		DLR_obj.connect_db();
		try {
			while (true) {
				DLR_obj.startSub();
				System.out.println("Waiting for new DLR .... Sleep 10");
				Thread.sleep(1000 * 2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void connect_db() {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// conn =
			// DriverManager.getConnection("jdbc:mysql://5.189.169.12:3306/httmtn?autoReconnect=true",
			// "root","gloadmin123");
			conn = DriverManager.getConnection("jdbc:mysql://91.205.172.123:3306/gameomania?autoReconnect=true", "root",
					"gloadmin123");
			// conn =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/bobble?autoReconnect=true",
			// "root","gloadmin123");
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
			stmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			stmtUpdate = conn.createStatement();
			System.out.println("DB Connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void startSub() {
		try {
			String ani = "", servicename = "", m_act = "WEB", action = "", next_billed_date = "now()",
					last_billed_date = "now()", default_amount = "", sub_date_time = "", network = "", guid = "",
					sub_id = "", operator = "", datetime = "", date = "", ref = "";
			int amount = 0, campaign_id = 0;
			String service_type = "";

			String getDLR = configurator.getProperty("getDLRSub");
			getDLR = getDLR.replace("<type>", "sub").replace("<action>", "Deletion");
			System.out.println(getDLR);
			ResultSet rsd = stmt.executeQuery(getDLR);
			if (rsd.next()) {
				rsd.beforeFirst();
				while (rsd.next()) {
					ani = rsd.getString(1);
					sub_date_time = rsd.getString(2);
					action = rsd.getString(3);
					network = rsd.getString(4);
					guid = rsd.getString(5);
					sub_id = rsd.getString(6);
					amount = rsd.getInt(7);
					servicename = rsd.getString(8);
					datetime = rsd.getString(9);
					date = rsd.getString(10);
					try {
						ref = rsd.getString(11);
						System.out.println("org Ref:::" + ref);
						if (ref == null) {
							ref = "na";
						}
					} catch (Exception e) {
						ref = "na";
					}
					System.out.println("Ref:::" + ref);

					amount = amount * 1;
					default_amount = rsd.getString(7);
					if (ref.matches("[0-9]+")) {
						campaign_id = Integer.parseInt(ref);
					} else {
						campaign_id = 0;
					}
					System.out.println(campaign_id);

					/*
					 * if(servicename.equalsIgnoreCase("Happy Tube Games")) { service_type =
					 * "Games"; } else if(servicename.equalsIgnoreCase("Happy Tube TV")){
					 * service_type = "SVOD"; } else if(servicename.equalsIgnoreCase("Cash Rider")){
					 * service_type = "cashrider"; }
					 */

					String getsvc = configurator.getProperty("getsvc");
					getsvc = getsvc.replace("<guid>", guid);
					System.out.println(getsvc);
					ResultSet rssvc = stmt3.executeQuery(getsvc);
					if (rssvc.next()) {
						service_type = rssvc.getString(1);
					}

					String getoper = configurator.getProperty("getoper");
					getoper = getoper.replace("<network>", network);
					System.out.println(getoper);
					ResultSet rsop = stmt3.executeQuery(getoper);
					if (rsop.next()) {
						operator = rsop.getString(1);
					}

					addLoggingDLR(ani + "#" + sub_date_time + "#" + action + "#" + operator + "#" + amount + "#" + guid
							+ "#" + sub_id);

					String updDLR = configurator.getProperty("Update_DLR");
					updDLR = updDLR.replace("<ani>", ani).replace("<servicename>", servicename).replace("<type>", "sub")
							.replace("<network>", network);
					System.out.println(updDLR);
					stmtUpdate.executeUpdate(updDLR);

					if (action.equalsIgnoreCase("Deletion")) {
						String unsub = configurator.getProperty("Unsub_user");
						unsub = unsub.replace("<ani>", ani).replace("<m_deact>", m_act).replace("<action>", action)
								.replace("<servicename>", service_type).replace("<operator>", operator)
								.replace("<datetime>", datetime);
						System.out.println(unsub);
						stmtUpdate.executeUpdate(unsub);

						String instUnsub = configurator.getProperty("Insert_Unsub");
						instUnsub = instUnsub.replace("<ani>", ani).replace("<servicename>", service_type)
								.replace("<operator>", operator);
						System.out.println(instUnsub);
						stmtUpdate.executeUpdate(instUnsub);

						String delSub = configurator.getProperty("Delete_Sub");
						delSub = delSub.replace("<ani>", ani).replace("<servicename>", service_type)
								.replace("<operator>", operator);
						System.out.println(delSub);
						stmtUpdate.executeUpdate(delSub);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getNextDate(String sub_date_time) {
		String finalDate = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(sub_date_time));
			c.add(Calendar.DATE, 1); // Adding 1 day
			finalDate = sdf.format(c.getTime());
			System.out.println(finalDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalDate;
	}

	private String getProvider(int campaign_id) {
		String provider = "";
		try {
			String camp_id = Integer.toString(campaign_id);
			System.out.println("camp_id::" + camp_id);
			String qry = configurator.getProperty("getProvider");
			qry = qry.replace("<camp_id>", camp_id);
			System.out.println(qry);
			ResultSet rsc = stmt2.executeQuery(qry);
			if (rsc.next()) {
				provider = rsc.getString(1);
			} else {
				provider = "na";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return provider;
	}

	private void addLoggingDLR(String data) {
		try {
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
			Date now = new Date();
			String strDate = sdfDate.format(now);
			String filename = "/home/app/Moneta_GameOmania/REPORT/SubReport" + strDate + ".log";

			FileWriter fw = new FileWriter(filename, true);
			fw.write(data + "\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
