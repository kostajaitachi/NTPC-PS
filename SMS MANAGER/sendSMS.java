import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
 
public class sendSMS {
	public String sendSms() {
		try {
			// Construct data
			String user = "username=" + "prakhar.bits059@gmail.com";
			String hash = "&hash=" + "fe8c327ad641df6d64087028b30e6fbcec937be6";
			String message = "&message=" + "heehaawww";
			String sender = "&sender=" + "prakhar";
			String numbers = "&numbers=" + "+919540001956";
			
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
			String data = user + hash + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
	
	public static void main(String args[])
	{
		String x = new sendSMS().sendSms();
		System.out.println(x);
	}
}