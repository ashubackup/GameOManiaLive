package com.spt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONObject;

import gen.DbConnection;
import gen.Loader;

public class ActionListener {
	JSONObject mjson = new JSONObject();
	Parameter objpram = new Parameter();
	String response = objpram.errorRepsone;

	public String checkAni(Parameter objparam, Connection conn) {
		try {
			return verfiyAni(checkAni(objparam.getAni()), conn);
		} catch (Exception e) {
			return response;
		}
	}
	

	public String verfiyAni(String ani, Connection conn) {
		JSONObject json = new JSONObject();
		try {
			String getQry = "select * from tbl_subscription where ani='" + ani + "' and service_type='SpotTheBall'";
			PreparedStatement ps1 = conn.prepareStatement(getQry);
			ResultSet res1 = ps1.executeQuery();
			if (res1.next()) {
				String Qry = "select * from tbl_subscription where ani='" + ani + "' and service_type='SpotTheBall'"
						+ " and date(next_billed_date)>=Date(subdate(now(),10))";
				PreparedStatement ps2 = conn.prepareStatement(Qry);
				ResultSet res2 = ps2.executeQuery();
				if (res2.next()) {
					json.put("message", "Active");
					json.put("status", "100");
				} else {
					json.put("message", "Pending");
					json.put("status", "99");
				}
			} else {
				boolean status = checkUSSDEntry(conn, ani);
				if(status) {
					json.put("message", "consent_pending");
					json.put("status", "96");
				}else {
					json.put("message", "Inactive");
					json.put("status", "98");
				}
				
			}
			json.put("msisdn", ani);
			mjson.put("data", json);
			mjson.put("responseMessage", "Success");
			mjson.put("responseStatus", "1");
			response = mjson.toString();
		} catch (Exception e) {
			return response;
		}
		return response;
	}

	public String addAni(Parameter objparam, Connection conn) {
		JSONObject json = new JSONObject();
		try {
			String ani = checkAni(objparam.getAni());
			String verfiyani = verfiyAni(ani, conn);
			JSONObject obj = new JSONObject(verfiyani);
			String responseStatus = obj.getString("responseStatus");
			if (responseStatus.equalsIgnoreCase("0")) {
				return verfiyani;
			}
			if (obj.getJSONObject("data").getString("status").equalsIgnoreCase("98")) {
				boolean status = checkUSSDEntry(conn, ani);
				if(status) {
					json.put("message", "consent_pending");
					json.put("status", "96");
					json.put("msisdn", ani);
				}else {
					String addQueryBilling = "INSERT INTO tbl_ussd_billing (ani,TOTAL_AMOUNT,DEDUCTED_AMOUNT,ISPREPAID,DATETIME,RECORDSTATUS,"
							+ "ERRORDESC,CIRCLEID,PROCESS_DATE,TYPE_EVENT,IS_EMM,PROCESS_DATETIME,SRC,NOOFATTEMPT,MODE,servicename,subservicename,"
							+ "product_id,pack_type) " + "VALUES ('" + ani
							+ "','20','20','0',NOW(),'5','null','all','null','SUB','0',NULL,'WEB','0',"
							+ "'WEB','SpotTheBall','SpotTheBall',NULL,'Daily')";
					
						boolean addBilling = UpdateQuery(conn, addQueryBilling);
						if (addBilling) {
							json.put("message", "Added");
							json.put("status", "97");
							json.put("msisdn", ani);
							
						}
				}
				mjson.put("data", json);
				mjson.put("responseMessage", "Success");
				mjson.put("responseStatus", "1");
				return mjson.toString();
				
			}
			return verfiyani;
		} catch (Exception e) {
			e.printStackTrace();
			return response;
		}
	}
	public boolean updateRecord(String query, Connection conn) {
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String addToConv(Parameter objparam,Connection conn) {
		try {
			objparam.setAni(checkAni(objparam.getAni()));
			String query = "insert into tbl_conv_logs(ani,service,clickid,pubid,provider)values('"+objparam.getAni()+"','"+objparam.getSvc()+"','"+objparam.getClickid()+"','"+objparam.getPubid()+"','"+objparam.getProvider()+"')";
			updateRecord(query, conn);
			addNewSUb(conn, objparam.getAni());
			return objparam.success;
		} catch (Exception e) {
			e.printStackTrace();
			return objparam.errorRepsone;
		}
	}
	
	public boolean addNewSUb(Connection conn,String ani) {
		try {
			String billingQuery = "INSERT INTO tbl_ussd_billing (ani,TOTAL_AMOUNT,DEDUCTED_AMOUNT,ISPREPAID,DATETIME,RECORDSTATUS,ERRORDESC,CIRCLEID,PROCESS_DATE,TYPE_EVENT,IS_EMM,PROCESS_DATETIME,SRC,NOOFATTEMPT,MODE,servicename,subservicename,product_id,pack_type) VALUES ('"+ani+"','20','20','0',NOW(),'5','null','all','null','SUB','0',NULL,'WEB1','0','WEB1','SpotTheBall','SpotTheBall',NULL,'Daily')";
			boolean billingStatus = UpdateQuery(conn, billingQuery);
			if(billingStatus) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	
	public boolean checkUSSDEntry(Connection conn, String ani) {
		try {
			String query ="select * from tbl_ussd_billing where ani='"+ani+"'";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet res = ps.executeQuery();
			System.out.println(query);
			if(res.next()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static void main(String[] args) {
		Parameter obj = new Parameter();
		obj.setAni("0708091524");
		DbConnection db = new DbConnection();
		System.out.println(new ActionListener().addAni(obj, db.getDatabse()));
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
		String countyCode = "254";
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
}
