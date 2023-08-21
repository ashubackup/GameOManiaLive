package gen;

import static gen.Configurator.getInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;

public class Conversion {
	private static Configurator configurator = getInstance();
	
	String resp = "{status:\"0\",newid;\"0\"}";
	private static String get_Time() {
	    SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(sdf.format(timestamp));

		return sdf.format(timestamp);
	}

	private String get_rand() {
		 int check = 0 ;
		 int rand_int1 = 0;
		 String rand = "";
		 while(check == 0){
			 rand_int1 = Math.abs(ThreadLocalRandom.current().nextInt()); 
			 rand = String.valueOf(rand_int1) ;
			 String get = getClikid(rand);
			 System.out.println(rand.length());
			 System.out.println("getValue"+ get);
			 if (rand.length() <= 10 && get.equalsIgnoreCase("0") ) {
				 
				 check ++;
			 }
		 }
		
		
		

		return rand;
	}

	public static void main(String[] args) {
		System.out.println(new Conversion().get_rand());
	}
	
	   public String insertLogs(String clickid,Connection conn, String id) {
		   String newid = "1";
		   JSONObject obj = new JSONObject();
			String message = "";
			
			ResultSet rs = null;
			try {
				
				String mode="",provider="",service="";
				Statement stmtid = conn.createStatement();
				String getdetails = "select vendor,service,type from tbl_promoters where id='"+id+"'";
				System.out.println(getdetails);
				ResultSet rsi = stmtid.executeQuery(getdetails);
				if(rsi.next()) {
					provider = rsi.getString(1);
					service = rsi.getString(2);
					mode = rsi.getString(3);
				}
				
				Statement stmt = conn.createStatement();
				String guid = "";
				String getGuid = "select productid from tbl_sdp_info where servicename='"+service+"' and status=0";
				System.out.println(getGuid);
				ResultSet rsg = stmt.executeQuery(getGuid);
				if(rsg.next()) {
					guid = rsg.getString(1);
				}

				String datetime = getCurrentDateTime();
				String instQry = "insert into tbl_conv_logs(clickid,createddatetime,modifieddatetime,provider,service,mode)"
						+ " values (?,'"+datetime+"','"+datetime+"','"+provider+"','"+service+"','"+mode+"')";
				
				PreparedStatement statement= conn.prepareStatement(instQry, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1,clickid);
				
				statement.executeUpdate();
				
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                System.out.println("Genereated "+generatedKeys.getLong(1));
		                obj.put("status", "1");
						obj.put("newid", generatedKeys.getLong(1));
						obj.put("guid", guid);
		            }
		            else {
		            	obj.put("status", "0");
						obj.put("newid", "0");
						obj.put("guid", guid);
		            }
		        }
				
	    		resp = obj.toString();
			}
			
			catch (Exception e) {
				e.printStackTrace();
				
			}
			
			return resp;
		}
	   
	   
	   public void updateLogs(String id,String ani,String json,String service ,String service_status,String url,String url_hit_status) {
	
			try {
				String instQry = "update tbl_conv_logs set svc_name='"+service+"',service_status='"+service_status+"',url_hit_status = '"+url_hit_status+"',modifieddatetime=now(),url='"+url+"',json = '"+json+"',status ='1',ani='"+ani+"' where newid='"+id+"' and status = '0'";
				
				PreparedStatement statement= Loader.contentConn.prepareStatement(instQry);
				
			
				statement.executeUpdate();
			
	    		System.out.println(instQry);
			}
			catch (Exception e) {
				e.printStackTrace();
				
			}
			
		}
	   
	   public String getClikid(String id) {
		  
			String new_id = "0";
			try {
				String instQry = "select * from  tbl_conv_logs where newid = '"+id+"'";
				
				PreparedStatement statement= Loader.contentConn.prepareStatement(instQry);
				
			
				ResultSet res = statement.executeQuery();
				if(res.next()) {
					new_id = res.getString("clickid");
				}else {
					new_id = "0";
				}
				
	    		System.out.println(instQry);
			}
			catch (Exception e) {
				e.printStackTrace();
				
			}
			
			return new_id;
		}
	
	   public String getClikid(String id, Connection conn) {

			String new_id = null;
			try {
				String Qry = "select clickid from  tbl_conv_logs where newid = '" + id + "'";
				System.out.println(Qry);

				PreparedStatement statement = conn.prepareStatement(Qry);
	            ResultSet res = statement.executeQuery();
				if (res.next()) {
					new_id = res.getString(1);
				} else {
					new_id = "0";
				}

			} catch (Exception e) {
				e.printStackTrace();

			}

			return new_id;
		}
		
		public String getProvider(String id, Connection conn) {

			String provider = null ;
			try {
				String Qry = "select provider from  tbl_conv_logs where newid = '" + id + "'";
				System.out.println(Qry);
				PreparedStatement statement = conn.prepareStatement(Qry);
	            ResultSet res = statement.executeQuery();
				if (res.next()) {
					provider = res.getString(1);
				} else {
					provider = "NA";
				}

				
			} catch (Exception e) {
				e.printStackTrace();

			}

			return provider;
		}

		private String getPostback(String provider, Connection conn,String service) {
			String url = "",type="";
			try {
				String Qry = "select postback_url,rep_type from tbl_promoters where name='"+provider+"' and status=0 and service='"+service+"'";
				System.out.println(Qry);
				PreparedStatement statement = conn.prepareStatement(Qry);
	            ResultSet res = statement.executeQuery();
	            if(res.next()) {
	            	url = res.getString(1);
	            	type = res.getString(2);
	            }
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return url+"#"+type;
		}

		private String getPubid(String id, Connection conn) {

			String pubid = null;
			try {
				String Qry = "select pubid from  tbl_conv_logs where newid = '" + id + "'";
				System.out.println(Qry);

				PreparedStatement statement = conn.prepareStatement(Qry);
	            ResultSet res = statement.executeQuery();
				if (res.next()) {
					pubid = res.getString(1);
				} else {
					pubid = "0";
				}

			} catch (Exception e) {
				e.printStackTrace();

			}

			return pubid;
		}
	   
	  public void updateOptickConv(String id, String ani,  String service, String service_status, Connection conn,
			  String svc_name, String operator){
		  String click_id = getClikid(id,conn);
			String provider = getProvider(id,conn);
			System.out.println("click_id:"+click_id);
			System.out.println("provider:"+provider);
			
			String pUrl = getPostback(provider,conn,service);
			String a[] = pUrl.split("#");
			String postback_url = a[0];
			String type = a[1];
			System.out.println("postback_url:"+postback_url+" type:"+type);
			if(type.equalsIgnoreCase("1")) {
				postback_url = postback_url.replace("external_id", click_id);
				System.out.println("URL after replace::"+postback_url);
			}
			else if (type.equalsIgnoreCase("2")) {
				String pubid = getPubid(id,conn);
				System.out.println("pubid:"+pubid);
				postback_url = postback_url.replace("external_id", click_id).replace("pub_id", pubid);
				System.out.println("URL after replace::"+postback_url);
			}
			
			String https_url = postback_url;
			System.out.println(https_url);
			URL url;
			try {
				url = new URL(https_url);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				System.out.println("****** Content of the URL ********");
				con.setRequestMethod("GET");
				BufferedReader br = null;
				String input;
				String Fininput = "";
				final int result = con.getResponseCode();
				System.out.println("THis is result" + result);
				if (result == 200) {
					br = new BufferedReader(new InputStreamReader(con.getInputStream()));

				} else {
					br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				}
				updateLogs(id, ani, svc_name, service_status, https_url, Integer.toString(result),conn,operator);
				updateSubscription(ani,provider,id,service,conn,operator);
				//updateBilling(ani,provider,service,conn,operator,id);   // updateing in billing dlr
				//updateOptickTbl(click_id,ani,conn);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	  }
	  
	  public void updateBilling(String ani, String provider, String service, Connection conn, String operator, String id) {
			try {
				String updtBilling = configurator.getProperty("update_billing_conv");
				updtBilling = updtBilling.replace("<provider>", provider).replace("<ani>", ani)
						.replace("<servicename>", service).replace("<operator>", operator).replace("<camp_id>", id);
				System.out.println(updtBilling);
				PreparedStatement statement = conn.prepareStatement(updtBilling);
	            statement.executeUpdate();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	  
	  private void updateSubscription(String ani, String provider, String id, String service, Connection conn, String operator) {
			try {
				String updtSub = configurator.getProperty("update_sub_conv");
				updtSub = updtSub.replace("<camp_id>", id).replace("<provider>", provider).replace("<ani>", ani)
						.replace("<servicename>", service).replace("<operator>", operator);
				System.out.println(updtSub);
				PreparedStatement statement = conn.prepareStatement(updtSub);
	            statement.executeUpdate();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	  
	  public void updateLogs(String id, String ani, String service, String service_status, String url,
				String url_hit_status, Connection conn, String operator) {

			try {
				String instQry = "update tbl_conv_logs set svc_name='" + service + "',service_status='" + service_status
						+ "',url_hit_status = '" + url_hit_status + "',modifieddatetime=now(),url='" + url + "',"
						+ "status ='1',ani='" + ani + "',operator='"+operator+"' where newid='" + id + "' and status = '0'";
				System.out.println(instQry);
	            PreparedStatement statement = conn.prepareStatement(instQry);
	            statement.executeUpdate();
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
			   
			     System.out.println("local time : " + localTime);;
			     System.out.println("time in Tanzania : " + converter.format(localTime));
			     
			     dt = converter.format(localTime);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return dt;
		}

	public void updateSocial( String ani, String service_type, String provider, String operator,Connection conn) {
		

				try {
//					 System.out.println("Oprator ::: "+operator);
//					String updtSub = configurator.getProperty("update_direct_sub");
//					updtSub = updtSub.replace("<m_act>", "AVM").replace("<ani>", ani)
//							.replace("<servicename>", service_type);
             
			String updtSub="update tbl_subscription set m_act='AVM' where ani='"+ani+"' and service_type='"+service_type+"'";
					System.out.println(updtSub);
					PreparedStatement statement = conn.prepareStatement(updtSub);
		            statement.executeUpdate();
		            
				}
				catch (Exception e) {
					e.printStackTrace();
					
					System.out.println("Erorr message"+e.getMessage());
	               }
		
		
		
	}
	
}
