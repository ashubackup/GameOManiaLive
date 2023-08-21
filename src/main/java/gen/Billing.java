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
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import gen.Loader;
import java.sql.Connection;

/**
 * Servlet implementation class Billing
 */
@WebServlet("/Billing")
public class Billing extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String CallbackPath;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Billing() {
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
        final String data = String.valueOf(String.valueOf(newarr[0])) + " " + newarr[1];
        return data;
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
        response.setContentType("text/xml");
        final PrintWriter out = response.getWriter();
        final StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            final BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            this.CreateFile(jb);
            final JSONObject jsonObj = new JSONObject(jb.toString());
            final String insidObj = jsonObj.get("subscription").toString();
            final JSONObject jsonObjnew = new JSONObject(insidObj);
            String ani = (String)jsonObjnew.get("user_msisdn");
            final int len = "27".length();
            if (ani.substring(0, len).equals("27")) {
                ani = ani.substring(len);
            }
            System.out.println("--------------------" + ani);
            String last_billed_at = (String)jsonObjnew.get("last_billed_at");
            String next_billing_at = (String)jsonObjnew.get("next_billing_at");
            String subscription_started_at = (String)jsonObjnew.get("subscription_started_at");
            final String channel_name = (String)jsonObjnew.get("channel_name");
            final String status_name = (String)jsonObjnew.get("status_name");
            final String result_name = (String)jsonObj.get("result_name");
            int amount = (int)jsonObj.get("amount");
            amount /= 100;
            next_billing_at = this.getDateFormat(next_billing_at);
            last_billed_at = this.getDateFormat(last_billed_at);
            subscription_started_at = this.getDateFormat(subscription_started_at);
            final PreparedStatement ps = Loader.contentConn.prepareStatement("insert into tbl_billing_success(ani,DEDUCTED_AMOUNT,ERRORDESC,TYPE_EVENT,MODE,SUBMODE,PROCESS_DATETIME,servicename) values (?,?,?,'REN',?,?,now(),'Games')");
            ps.setString(1, ani);
            ps.setInt(2, amount);
            ps.setString(3, result_name);
            ps.setString(4, channel_name);
            ps.setString(5, channel_name);
            ps.executeUpdate();
            ps.close();
            if (result_name.equalsIgnoreCase("SUCCESS")) {
                final String checkUser = "select * from tbl_subscription where ani ='" + ani + "' and service_type='Games'";
                final PreparedStatement checkU = Loader.contentConn.prepareStatement(checkUser);
                final ResultSet res = checkU.executeQuery();
                if (res.next()) {
                    final String inset = "update `tbl_subscription` set `sub_date_time`=? ,`STATUS`=?,`default_amount`=" + amount + ",`next_billed_date`=?,`last_billed_date`=? where ani = '" + ani + "' and service_type='Games'";
                    final PreparedStatement ps2 = Loader.contentConn.prepareStatement(inset);
                    ps2.setString(1, subscription_started_at);
                    ps2.setString(2, status_name);
                    ps2.setString(3, next_billing_at);
                    ps2.setString(4, last_billed_at);
                    ps2.executeUpdate();
                    ps2.close();
                }
                else {
                    final String instSub = "insert into tbl_subscription (ani,sub_date_time,unsub_date_time,m_act,lang,service_type,status,charging_date,billing_date,default_amount,RECORDSTATUS,pack_type,`next_billed_date`,`last_billed_date`) values ('" + ani + "',now(),NULL,'" + channel_name + "','e','Games','Active',NULL,NULL," + amount + ",'1','Daily',?,?)";
                    final PreparedStatement ps3 = Loader.contentConn.prepareStatement(instSub);
                    ps3.setString(1, next_billing_at);
                    ps3.setString(2, last_billed_at);
                    ps3.executeUpdate();
                    ps3.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus(200);
        out.println("");
    }
    
    private synchronized void CreateFile(final StringBuffer Data) {
        this.CallbackPath = "/home/SDPLOGS/NDOTO_Game/";
        final Long FileName = get_Time();
        final String Rand = get_rand();
        System.out.println("VALUE OF CALLBACK PATH" + this.CallbackPath);
        try {
            final File hFile = new File(String.valueOf(String.valueOf(this.CallbackPath)) + FileName + Rand + ".txt");
            final FileWriter hFileWriter = new FileWriter(hFile, false);
            hFileWriter.write(Data.toString());
            hFileWriter.close();
            System.out.println(Data.toString());
            System.out.println("text File Created in Billing : " + this.CallbackPath + FileName + Rand + ".txt");
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
        return Time1;
    }
    
    private static String get_rand() {
        final Random r = new Random();
        final int value = r.nextInt(10) + 9;
        final String rand = String.valueOf(value);
        return rand;
    }
}