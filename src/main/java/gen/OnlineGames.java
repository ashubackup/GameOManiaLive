package gen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class OnlineGames {
	public String getCategories() {
		String https_url = "https://games.gamepix.com/categories?sid=41151";
		//System.out.println(https_url);
		URL url;
		try {
			url = new URL(https_url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			//System.out.println("****** Content of the URL ********");
			con.setRequestMethod("GET");
			BufferedReader br = null;
			String input;
			String Fininput = "";
			final int result = con.getResponseCode();
			//System.out.println("THis is result" + result);
			if (result == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((input = br.readLine()) != null) {
					System.out.println(input);
					Fininput += input;
				}
				//System.out.println("Result" + Fininput);
				return Fininput;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	public String getgameByCatid(String catid) {
		String https_url = "https://games.gamepix.com/games?sid=41151&category="+catid+"&order=d";
		//System.out.println(https_url);
		URL url;
		try {
			url = new URL(https_url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			//System.out.println("****** Content of the URL ********");
			con.setRequestMethod("GET");
			BufferedReader br = null;
			String input;
			String Fininput = "";
			final int result = con.getResponseCode();
			if (result == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((input = br.readLine()) != null) {
				//	System.out.println(input);
					Fininput += input;
				}					
				return Fininput;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
	
	public String getSinglegameByid(String gid) {
		String https_url = "http://games.gamepix.com/game?sid=41151&gid="+gid;
		//System.out.println(https_url);
		URL url;
		try {
			url = new URL(https_url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			//System.out.println("****** Content of the URL ********");
			con.setRequestMethod("POST");
			BufferedReader br = null;
			String input;
			String Fininput = "";
			final int result = con.getResponseCode();
			//System.out.println("-------------"+result);

			if (result == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((input = br.readLine()) != null) {
				//	System.out.println(input);
					Fininput += input;

				}
				
				return Fininput;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
}
