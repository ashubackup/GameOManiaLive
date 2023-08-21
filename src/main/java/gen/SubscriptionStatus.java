package gen;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.Calendar;
import java.io.FileWriter;
import java.io.File;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.BufferedReader;
import java.io.PrintWriter;
import gen.Conversion;
import org.json.JSONObject;
import java.text.SimpleDateFormat;

import gen.Loader;
import java.sql.Connection;
/**
 * Servlet implementation class SubscriptionStatus
 */
@WebServlet("/SubscriptionStatus")
public class SubscriptionStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String CallbackPath;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubscriptionStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }
    
    public String getDateFormat(final String date) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String dateInString = date;
        final String[] arr = dateInString.split("\\+");
        final String[] newarr = arr[0].split("T");
        System.out.println(newarr[0]);
        final String data = String.valueOf(String.valueOf(newarr[0])) + " " + newarr[1];
        System.out.println(data);
        return data;
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final PrintWriter out = response.getWriter();
        try {
            this.doGet(request, response);
            response.setContentType("text/xml");
            final StringBuffer jb = new StringBuffer();
            String line = null;
            try {
                final BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    jb.append(line);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            this.CreateFile(jb);
            final JSONObject jsonObj = new JSONObject(jb.toString());
            String ani = (String)jsonObj.get("user_msisdn");
            final String countyCode = "27";
            final int len = "27".length();
            if (ani.substring(0, len).equals("27")) {
                ani = ani.substring(len);
            }
            String last_billed_at = (String)jsonObj.get("last_billed_at");
            String next_billing_at = (String)jsonObj.get("next_billing_at");
            final String channel_name = (String)jsonObj.get("channel_name");
            final String status_name = (String)jsonObj.get("status_name");
            final String svc_name = (String)jsonObj.get("svc_name");
            int amount = (int)jsonObj.get("billing_rate");
            final int campaign_id = (int)jsonObj.get("campaign_id");
            amount /= 100;
            next_billing_at = this.getDateFormat(next_billing_at);
            last_billed_at = this.getDateFormat(last_billed_at);
            if (status_name.equalsIgnoreCase("CANCELLED")) {
                final String updateSub = "update tbl_subscription set unsub_date_time = now(),m_deact='" + channel_name + "',STATUS ='" + status_name + "' where ani ='" + ani + "' and service_type='Games'";
                final PreparedStatement ps = Loader.contentConn.prepareStatement(updateSub);
                ps.executeUpdate();
                ps.close();
                final String insertUnsub = "insert into tbl_subscription_unsub select * from tbl_subscription where ani='" + ani + "' and service_type='Games'";
                final PreparedStatement ps2 = Loader.contentConn.prepareStatement(insertUnsub);
                ps2.executeUpdate();
                ps2.close();
                final String deleteSUb = "delete from `tbl_subscription` where `ani`='" + ani + "' and service_type='Games' ";
                final PreparedStatement ps3 = Loader.contentConn.prepareStatement(deleteSUb);
                ps3.executeUpdate();
                ps3.close();
            }
            else if (status_name.equalsIgnoreCase("ACTIVE")) {
                if (campaign_id != 0) {
                    //new Conversion().sendSMS(Integer.toString(campaign_id), ani, jsonObj.toString(), svc_name, status_name);
                }
                if (channel_name.equalsIgnoreCase("USSD")) {
                    final String instSub = "insert into tbl_subscription (ani,sub_date_time,unsub_date_time,m_act,lang,service_type,status,charging_date,billing_date,default_amount,RECORDSTATUS,pack_type,`next_billed_date`,`last_billed_date`) values ('" + ani + "',now(),NULL,'USSD','e','Games','Active',NULL,NULL," + amount + ",'1','Daily',?,?)";
                    final PreparedStatement ps = Loader.contentConn.prepareStatement(instSub);
                    ps.setString(1, next_billing_at);
                    ps.setString(2, last_billed_at);
                    ps.executeUpdate();
                    ps.close();
                }
                else if (channel_name.equalsIgnoreCase("SMS")) {
                    final String instSub = "insert into tbl_subscription (ani,sub_date_time,unsub_date_time,m_act,lang,service_type,status,charging_date,billing_date,default_amount,RECORDSTATUS,pack_type,`next_billed_date`,`last_billed_date`) values ('" + ani + "',now(),NULL,'SMS','e','Games','Active',NULL,NULL," + amount + ",'1','Daily',?,?)";
                    final PreparedStatement ps = Loader.contentConn.prepareStatement(instSub);
                    ps.setString(1, next_billing_at);
                    ps.setString(2, last_billed_at);
                    ps.executeUpdate();
                    ps.close();
                }
                else {
                    final String checkUser = "select * from tbl_subscription where ani ='" + ani + "' and service_type='Games'";
                    final PreparedStatement checkU = Loader.contentConn.prepareStatement(checkUser);
                    final ResultSet res = checkU.executeQuery();
                    System.out.println(checkUser);
                    if (res.next()) {
                        final String inset = "update `tbl_subscription` set `sub_date_time`=now(),`m_act`=?,`STATUS`=?,`default_amount`=" + amount + ",`next_billed_date`=?,`last_billed_date`=? where ani = '" + ani + "' and service_type='Games'";
                        final PreparedStatement ps4 = Loader.contentConn.prepareStatement(inset);
                        ps4.setString(1, "WEB");
                        ps4.setString(2, status_name);
                        ps4.setString(3, next_billing_at);
                        ps4.setString(4, last_billed_at);
                        ps4.executeUpdate();
                        ps4.close();
                    }
                    else {
                        System.out.println("THis is channel " + channel_name);
                        final String instSub2 = "insert into tbl_subscription (ani,sub_date_time,unsub_date_time,m_act,lang,service_type,status,charging_date,billing_date,default_amount,RECORDSTATUS,pack_type,`next_billed_date`,`last_billed_date`) values ('" + ani + "',now(),NULL,'WEB','e','Games','Active',NULL,NULL," + amount + ",'1','Daily',?,?)";
                        final PreparedStatement ps4 = Loader.contentConn.prepareStatement(instSub2);
                        System.out.println(instSub2);
                        ps4.setString(1, next_billing_at);
                        ps4.setString(2, last_billed_at);
                        ps4.executeUpdate();
                        ps4.close();
                    }
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        response.setStatus(204);
        out.println("");
    }
    
    private synchronized void CreateFile(final StringBuffer Data) {
        this.CallbackPath = "/home/SDPLOGS/NDOTO_Game/";
        final Long FileName = get_Time();
        final String Rand = get_rand();
        try {
            final File hFile = new File(String.valueOf(String.valueOf(this.CallbackPath)) + FileName + Rand + ".txt");
            System.out.println("IF BLOCK");
            final FileWriter hFileWriter = new FileWriter(hFile, false);
            hFileWriter.write(Data.toString());
            hFileWriter.close();
            System.out.println(Data.toString());
            System.out.println("text File Created in Subscription : " + this.CallbackPath + FileName + Rand + ".txt");
            final File hFilelck = new File(String.valueOf(String.valueOf(this.CallbackPath)) + FileName + Rand + ".lck");
            final FileWriter hFilelck2 = new FileWriter(hFilelck, false);
            hFilelck2.write("0");
            hFilelck2.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static long get_Time() {
        final Calendar lCDateTime = Calendar.getInstance();
        final long Time1 = lCDateTime.getTimeInMillis();
        System.out.println("Time in milliseconds :" + Time1);
        return Time1;
    }
    
    private static String get_rand() {
        final Random r = new Random();
        final int value = r.nextInt(10) + 9;
        final String rand = String.valueOf(value);
        return rand;
    }
}