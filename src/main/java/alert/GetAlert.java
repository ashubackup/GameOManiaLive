package alert;



import javax.mail.PasswordAuthentication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class GetAlert {

	Statement stmt = null;
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	Statement stmtUpdate = null;
	static Connection conn = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			GetAlert obj = new GetAlert();
			obj.connect_db();
			int value = obj.CheckTraffic();
			System.out.println("value:::"+value);
			
			if(value == 0) {
				obj.SendEmailAlert();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SendEmailAlert() {
		try {
			final String username = "itsupport@genrosys.com";
	        final String password = "Tech_IT^1239G";
	        
	        String SMTP_HOST_NAME = "mail.eoutlooks.com";
	        String SMTP_PORT = "587";

	        
	        Properties prop = new Properties();
			/*
			 * props.put("mail.smtp.host", SMTP_HOST_NAME); props.put("mail.smtp.auth",
			 * "true"); props.put("mail.debug", "false"); props.put("mail.smtp.port",
			 * SMTP_PORT); props.put("mail.smtp.socketFactory.port", SMTP_PORT);
			 * props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
			 * props.put("mail.smtp.socketFactory.fallback", "true");
			 * props.put("mail.smtp.auth", "true");
			 * props.put("mail.smtp.transport.protocol", "smtp");
			 * props.put("mail.smtp.starttls.enable", "true");
			 * props.put("mail.smtp.localhost", "127.0.0.1");
			 */
	        prop.put("mail.smtp.host", SMTP_HOST_NAME);
	        prop.put("mail.smtp.port", SMTP_PORT);
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.starttls.enable", "true"); //TLS
	        prop.put("mail.smtp.transport.protocol", "smtp");
	        prop.put("mail.debug", "false");
	        
	        String mesg = "Please check promotion postback's for MTN South Africa";
			
			String message = "<html><table><tr><td>Dear Team,<br><br></td></tr><tr><td>Greetings! below are the details for current hour : <br><br > </td></tr>" +
					"<tr><td>" +
					"</td></tr><tr>"+mesg+" </tr><tr><td> <br>Regards,<br>Java Team </td></tr><table>";
	        
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });

	        try {

	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("itsupport@genrosys.com"));
	            msg.setRecipients(
	                    Message.RecipientType.TO,
	                    InternetAddress.parse("hitesh.kumar@genrosys.com")
	            );
	            msg.setSubject("Alert Alert Alert!!!!! for "+getCurrentDateTimeIndia());
	            msg.setContent(message, "text/html");

	            Transport.send(msg);

	            System.out.println("Done");

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getCurrentDateTimeIndia() {
		String dt = "";
		try {
			Date localTime = new Date();
			   
		     DateFormat converter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   
		     converter.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		   
		     System.out.println("local time : " + localTime);;
		     System.out.println("time in Tanzania : " + converter.format(localTime));
		     
		     dt = converter.format(localTime);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return dt;
	}

	private int CheckTraffic() {
		int value = 0;
		try {
			int last_count = 0,count=0;
			String datetime = getCurrentDateTime();
			String time = "";
			String nowTime = getCurrentTime();
			
			String getTime = "select hour(datetime) from tbl_dlr where date(datetime) = '"+datetime+"'"
					+ " and type='sub' and action='Addition' order by datetime desc limit 1";
			System.out.println(getTime);
			ResultSet rs = stmt.executeQuery(getTime);
			if(rs.next()) {
				time = rs.getString(1);
				
				String getCount = "select count(1) from tbl_conv_logs where hour(modifieddatetime)='"+time+"'"
						+ " and status=1 and ani is not null";
				System.out.println(getCount);
				ResultSet rsC = stmt.executeQuery(getCount);
				if(rsC.next()) {
					count = rsC.getInt(1);
				}
				
				if(count == 0) {
					value = 0;
				}
				else {
					value = 0;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return value;
		
	}
	
	private String getCurrentTime() {
		String dt = "";
		try {
			Date localTime = new Date();
			   
		     DateFormat converter = new SimpleDateFormat("HH");
		   
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

	private String getbeforeTime() {
		String time = "";
		try {
			DateFormat converter = new SimpleDateFormat("HH");
			Calendar cal = Calendar.getInstance();
			// remove next line if you're always using the current time.
			
			cal.add(Calendar.HOUR, -1);
			Date oneHourBack = cal.getTime();
			System.out.println("oneHourBack:::"+oneHourBack);
			
			time = converter.format(oneHourBack);
			System.out.println("beforeTime::"+time);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	public String getCurrentDateTime() {
		String dt = "";
		try {
			Date localTime = new Date();
			   
		     DateFormat converter = new SimpleDateFormat("yyyy-MM-dd");
		   
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

	private void connect_db() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//conn = DriverManager.getConnection("jdbc:mysql://5.189.169.12:3306/httmtn?autoReconnect=true", "root","gloadmin123");
			conn = DriverManager.getConnection("jdbc:mysql://91.205.172.123:3306/gameomania?autoReconnect=true", "root","gloadmin123");
			//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bobble?autoReconnect=true", "root","gloadmin123");
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
			stmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			stmtUpdate = conn.createStatement();
			System.out.println("DB Connected");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
