package com.wifihi.terminalServerService;
import java.io.Serializable;

public class Wifi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mac;
	private String passwd;
	private boolean Authorise;
	
	public boolean isAuthorise() {
		return Authorise;
	}

	public void setAuthorise(boolean authorise) {
		Authorise = authorise;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Wifi["+this.mac+","+this.passwd+","+this.Authorise+"]";
		
	}

}
