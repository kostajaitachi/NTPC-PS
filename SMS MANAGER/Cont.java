/*
*
author : prakhar
*
*/
package ntpc_c;

import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class Cont 
{
// database variables
Connection c;
Statement s;
ResultSet r1,r2;

	public Cont()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			c= DriverManager.getConnection("jdbc:odbc:DATA");
			s=c.createStatement();
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		
		}
		
		while(true)
		{
			try
			{
				// where SendDate between sysdate - 0.25 and sysdate
				PreparedStatement p =c.prepareStatement("select GroupName,Message,status,SendDate from mesaages12 where SendDate between Now() - 0.25 and Now()");
				r1 = p.executeQuery();

				while(r1.next())
				{
					
					if(r1.getInt(3) == 0)
					{	
					
						String grpName = r1.getString(1);
						String message = r1.getString(2);
						
							PreparedStatement p1 = c.prepareStatement("select distinct Mobile from group_data,members where GroupName = ?");
							p1.setString(1,grpName);
							r2 = p1.executeQuery();
							while(r2.next())
							{
								String number = "+91" + r2.getString(1);
								//String response = new sendSMS().sendSms(number,message);
								System.out.println(number);
								PreparedStatement p2 = c.prepareStatement("update mesaages12 set status = ? where Message = ?");
								p2.setInt(1,1);
								p2.setString(2,"message");
								p2.execute();
								p2.close();
								Thread.sleep(2000);
							}
							p1.close();
						
					}
					else
						continue;
				}
				p.close();
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			try
			{
				Thread.sleep(5000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[])
	{
		new Cont();
	}
}

class sendSMS 
{
	public String sendSms(String no,String mes) 
	{
		try 
		{
			// Construct data
			String user = "username=" + "prakhar.bits059@gmail.com";
			String hash = "&hash=" + "fe8c327ad641df6d64087028b30e6fbcec937be6";
			String message = "&message=" + mes;
			String sender = "&sender=" + "prakhar";
			String numbers = "&numbers=" + no;
			
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
			while ((line = rd.readLine()) != null) 
			{
				stringBuffer.append(line);
			}
			rd.close();
			
			return stringBuffer.toString();
		} 
		catch (Exception e) 
		{
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
}