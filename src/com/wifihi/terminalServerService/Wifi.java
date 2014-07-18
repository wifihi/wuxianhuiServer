package com.wifihi.terminalServerService;
import java.io.Serializable;

public class Wifi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String SSID;
	private String passwd;
	private int Authorise;
	public int getAuthorise()
	{
		return this.Authorise;
	}
	public void setAuthorise(int Authorise)
	{
		this.Authorise=Authorise;
	}
	public String getSSID()
	{
		return this.SSID;
	}  
	public void setSSID(String SSID)
	{
		this.SSID=SSID;
	}
	public String getPasswd()
	{
		return this.passwd;
	}
	public void setPasswd(String passwd)
	{
		this.passwd=passwd;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Wifi["+this.SSID+","+this.passwd+","+this.Authorise+"]";
		
	}

}
