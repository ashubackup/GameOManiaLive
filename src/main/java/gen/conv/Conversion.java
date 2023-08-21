package gen.conv;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import gen.Loader;

public class Conversion {
	public boolean checkAni(String ani,String svc) {
		try {
			String query = "select * from tbl_subscription where service_type='"+svc+"' and ani='"+ani+"'";
			ResultSet res = getResultSet(query, Loader.contentConn);
			if(res.next()) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	public boolean addToConv(String ani,String svc,String clickid,String pubid,String provider) {
		try {
			String query = "insert into tbl_conv_logs(ani,service,clickid,pubid,provider)values('"+ani+"','"+svc+"','"+clickid+"','"+pubid+"','"+provider+"')";
			updateRecord(query, Loader.contentConn);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
	
	public ResultSet getResultSet(String query,Connection conn) {
        ResultSet res = null;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			res = ps.executeQuery();
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
