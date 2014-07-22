package com.wifihi.terminalServerAction;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.wifihi.persistance.Wifimanage;
import com.wifihi.persistance.Wspuser;
import com.wifihi.terminalServerService.Wifi;

public class WifiAction extends ActionSupport implements ServletRequestAware{
	private static final long serialVersionUID = 1L;
	private String reqContent = null;
	private Wifi wifi;  //返回passwd的
	private List<Wifi> wifiList = new ArrayList<Wifi>(); //返回的List
	//鉴权
	public String rtnAuthorise(){
		JSONObject json = new JSONObject(this.reqContent);
		String mac = json.getString("mac");
		
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session session = sf.openSession();
		wifi = new Wifi();
		Wifimanage macAdd = new Wifimanage();
		try{
				session.beginTransaction();
				Query q = session.createQuery("from Wifimanage  where MacAddress like :mac");
				q.setParameter("mac", mac);
				macAdd = (Wifimanage)q.uniqueResult();
				if(macAdd!=null){
					if(macAdd.getAuthorise()==false)
					{
						wifi.setWifiname(macAdd.getWifiName());
						wifi.setPasswd(macAdd.getPassword());
						wifi.setAuthorise(macAdd.getAuthorise());
					}
					else
					{
						wifi.setWifiname(macAdd.getWifiName());
						wifi.setPasswd("");
						wifi.setAuthorise(macAdd.getAuthorise());
					}
					
				}
				
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("wifii"+wifi.toString());
		return SUCCESS;
	}

    //返回用户名密码
	public String rtnWifi(){
		JSONObject json = new JSONObject(this.reqContent);
		String mac = json.getString("mac");
		String hql = "from Wifimanage w where w.MacAddress=:mac";
		String hqlwsp = "from Wspuser  where wspuser = :wspid";

		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session session = sf.openSession();
		wifi = new Wifi();
		Wifimanage macAdd = new Wifimanage();
		Wspuser wspuser = new Wspuser();   
		try{
				session.beginTransaction();
				Query q = session.createQuery(hql);
				q.setParameter("mac", mac);
				macAdd = (Wifimanage)q.uniqueResult();
				if(macAdd!=null)
					{
				wifi.setMac(mac); 
				//wifi.setPasswd(macAdd.getPassword());
				wifi.setDisplayname(macAdd.getDisplayName().toString());
				q = session.createQuery(hqlwsp);
				q.setParameter("wspid", macAdd.getWspuser().getWspuser());
				System.out.println("wspid:"+macAdd.getWspuser().getWspuser());
				wspuser = (Wspuser)q.uniqueResult();
				wifi.setWspusername(wspuser.getUserName());
					}
				else
					wifi.setPasswd("err10004");
			}catch(Exception e){
				System.out.println("Something Wrong in select wifiPassword.");
		}
		return SUCCESS;
	}
	
	//return wifiList
	public String rtnWifiList(){
	
		JSONArray jsonArray = new JSONArray(this.reqContent);
		String[] macs = new String[jsonArray.length()];
		for(int i = 0; i < jsonArray.length(); i++){
			macs[i] = jsonArray.getJSONObject(i).getString("mac"); 
		}
		//建立连接
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session session = sf.openSession();
		
		String hql = "from Wifimanage  where macaddress like :mac"; //区分大小写
		String hqlwsp = "from Wspuser  where wspuser = :wspid";
		Wifimanage maci = new Wifimanage();   //查询时使用
		
		Wspuser wspuser = new Wspuser();             //添加对象
		
		for(int i = 0; i < macs.length; i++){
			try{
				Wifi wifii = new Wifi(); 
				session.beginTransaction();
				Query q = session.createQuery(hql); //查找
				q.setParameter("mac", macs[i]);
				maci = (Wifimanage) q.uniqueResult(); //获取当前对象
				if(maci==null)continue;               //若为空则继续
				else
					{
					wifii.setMac(macs[i]); 
				//设置该mac
				wifii.setWifiname(maci.getWifiName().toString());
				wifii.setDisplayname(maci.getDisplayName().toString());
				q = session.createQuery(hqlwsp);
				q.setParameter("wspid", maci.getWspuser().getWspuser());
				System.out.println("wspid:"+maci.getWspuser().getWspuser());
				wspuser = (Wspuser)q.uniqueResult();
				wifii.setWspusername(wspuser.getUserName());
					wifiList.add(wifii);
					System.out.println("wifiList:"+wifiList.toString());
					System.out.println("wifii"+wifii.toString());
					}//添加wifii对象
			}catch(Exception e){
				session.getTransaction().rollback();
			}
			finally{
				//session.close();//如果用c3p0好像不用关闭..
			}
		}
		System.out.println("wifiList:"+wifiList.toString());
		return SUCCESS;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.reqContent = GetRequestAction.getJsonContent(arg0);;
		System.out.println("reqContent: " + reqContent);//多个json对象
	}
	public Wifi getWifi() {
		return wifi;
	}

	public void setWifi(Wifi wifi) {
		this.wifi = wifi;
	}

	
	
	public List<Wifi> getWifiList() {
		return wifiList;
	}

	public void setWifi(List<Wifi> wifiList) {
		this.wifiList = wifiList;
	}
	
}
