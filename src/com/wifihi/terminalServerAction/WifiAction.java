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
	private Wifi wifi;  //����passwd��
	private List<Wifi> wifiList = new ArrayList<Wifi>(); //���ص�List
	//��Ȩ
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

    //�����û�������
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
		//��������
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session session = sf.openSession();
		
		String hql = "from Wifimanage  where macaddress like :mac"; //���ִ�Сд
		String hqlwsp = "from Wspuser  where wspuser = :wspid";
		Wifimanage maci = new Wifimanage();   //��ѯʱʹ��
		
		Wspuser wspuser = new Wspuser();             //��Ӷ���
		
		for(int i = 0; i < macs.length; i++){
			try{
				Wifi wifii = new Wifi(); 
				session.beginTransaction();
				Query q = session.createQuery(hql); //����
				q.setParameter("mac", macs[i]);
				maci = (Wifimanage) q.uniqueResult(); //��ȡ��ǰ����
				if(maci==null)continue;               //��Ϊ�������
				else
					{
					wifii.setMac(macs[i]); 
				//���ø�mac
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
					}//���wifii����
			}catch(Exception e){
				session.getTransaction().rollback();
			}
			finally{
				//session.close();//�����c3p0�����ùر�..
			}
		}
		System.out.println("wifiList:"+wifiList.toString());
		return SUCCESS;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.reqContent = GetRequestAction.getJsonContent(arg0);;
		System.out.println("reqContent: " + reqContent);//���json����
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
