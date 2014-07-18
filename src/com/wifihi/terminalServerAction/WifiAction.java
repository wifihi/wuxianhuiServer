package com.wifihi.terminalServerAction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.wifihi.persistance.HibernateSessionFactory;
import com.wifihi.persistance.Wifimanage;

public class WifiAction extends ActionSupport implements ServletRequestAware{
	private static final long serialVersionUID = 1L;
	 
	private String reqContent = null;
	
	private Wifimanage wifi;
	private List<Wifimanage> wifiList;

	public List<Wifimanage> getWifiList()
	{
		return this.wifiList;
	}
	public void setWifiList(List<Wifimanage> wifiList)
	{
		this.wifiList=wifiList;
	}
	
	public String rtnAuthorise(){
		JSONObject json = new JSONObject(this.reqContent);
		String ssid = json.getString("SSID");
		System.out.println("ssid :"+ssid);
	//	String sql = "select WifiName,Password,Authorise from wifimanage where WifiName='" + ssid+"'";
	//	Statement stmt = DBOperate.getDBOperate().getStatement();
		Session session = HibernateSessionFactory.getSession();
		wifi = new Wifimanage();
		try{
				session.beginTransaction();
				wifi =  session.createQuery(from Wifimanage w where w.wifiid=ssid);
				wifi.setSSID(rs.getString("WifiName"));
				wifi.setAuthorise(rs.getInt("Authorise"));//rs.getInt("Authorise")
				System.out.println("wifi.getAuthorise()"+wifi.getAuthorise());
					wifi.setPasswd(rs.getString("Password"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String rtnWifi(){
		if(this.reqContent.trim().equals("") || this.reqContent.charAt(0) != '{'){//what is reqContent
			wifi = new Wifi();
			wifi.setSSID("no");
			wifi.setSSID("no");
			 
			return SUCCESS;
		}
		
		JSONObject json = new JSONObject(this.reqContent);
		String ssid = json.getString("SSID");
		String sql = "select * from wifiinfo where ssid=" + ssid;
		Statement stmt = DBOperate.getDBOperate().getStatement();
		
		wifi = new Wifi();
		ResultSet rs;
		try{
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				/*wifi.setName(rs.getString("first_name"));
				person.setBirthday(rs.getString("birth_date"));
				person.setGender(rs.getString("gender"));
				System.out.println(person);*/
				wifi.setSSID(rs.getString("ssid"));
				 wifi.setPasswd(rs.getString("passwd"));
				 System.out.println(wifi);
			}
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String rtnWifiList(){
		if(this.reqContent.trim().equals("") || this.reqContent.charAt(0) != '['){
			wifiList = new ArrayList<>();
			wifi = new Wifi();
			wifi.setSSID("no"); 
			wifi.setPasswd("no");
			/*person.setBirthday("no");
			person.setGender("no");
			personList.add(person);*/
			return SUCCESS;
		}
		
		JSONArray jsonArray = new JSONArray(this.reqContent);
		String[] ssids = new String[jsonArray.length()];
		for(int i = 0; i < jsonArray.length(); i++){
			ssids[i] = jsonArray.getJSONObject(i).getString("SSID");
		}
		
		String sql = "select WifiName from wifimanage where WifiName =" +"'"+ssids[0]+"'";
		for(int i = 1; i < ssids.length; i++){
			sql += " or WifiName=" +"'"+ ssids[i]+"'";
		}
		
		wifiList = new ArrayList<>();
		Statement stmt = DBOperate.getDBOperate().getStatement();
		ResultSet rs;
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Wifi w=new Wifi();
				w.setSSID(rs.getString("WifiName"));//ssid为数据库中的列名
				//w.setPasswd(rs.getString("Password"));
				System.out.println(w.toString());
				wifiList.add(w);
			}
			System.out.println("wifiList");
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.reqContent = RequestJsonContent.getJsonContent(arg0);
		System.out.println("reqContent: " + reqContent);//多个jsion对象
	}
	
}
