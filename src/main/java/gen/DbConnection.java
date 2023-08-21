package gen;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
	
	public static Connection getDatabse() {

		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gameomania?autoReconnect=true&serverTimezone=UTC", "root","gloadmin123");
			conn = DriverManager.getConnection("jdbc:mysql://91.205.172.123:3306/gameomania?autoReconnect=true&serverTimezone=UTC", "root","gloadmin123");

			System.out.println("gameomania DB connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}
	
//	
//	public static void main(String args[]) {
//		getDatabse();
//	}

	
}
