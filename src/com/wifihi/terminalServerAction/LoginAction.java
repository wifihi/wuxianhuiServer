package com.wifihi.terminalServerAction;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionSupport;
import com.wifihi.persistance.User;
import com.wifihi.terminalServerService.LoginResultString;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONObject;


public class LoginAction extends ActionSupport implements ServletRequestAware{
	private static final long serialVersionUID = 1L;
	private String reqContent = null;
	private LoginResultString result;
	
	@Override
	public void setServletRequest(HttpServletRequest arg0) {   //閼惧嘲绶辨潻娆愵偧鐠囬攱鐪伴惃鍓卐quest鐎电钖�
		this.reqContent = GetRequestAction.getJsonContent(arg0);  //static method getJsonContent no new
		System.out.println("reqContent: " + reqContent);
	}
	
	public String userLoginCheck(){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		JSONObject json = new JSONObject(this.reqContent);
		String tel=json.getString("tel");
		String passwd=json.getString("passwd");
		User user = new User(); 
		Session session = sf.openSession();
		result = new LoginResultString();
		try{
				session.beginTransaction();
			
				Query q = session.createQuery("from User where phonenumber=:tel"); 
				q.setParameter("tel",tel);
				user = (User) q.uniqueResult();
				if(user==null){
					result.setResult("err10003");
				}
			//	user = (User)session.createQuery("from user where phonenumber='"+tel+"'");
				
				if(user.getPassword().equals(passwd)){
					result.setResult(user.getUserId().toString());
				}
				else if(!user.getPassword().equals(passwd)){
					result.setResult("err10002");
				}
		}catch(Exception e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		
		System.out.println(result.getResult());
		return SUCCESS;
	}
	public LoginResultString getResult() {
		return result;
	}

	public void setResult(LoginResultString result) {
		this.result = result;
	}
}
