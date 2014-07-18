package com.wifihi.terminalServerAction;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionSupport;
import com.wifihi.persistance.HibernateSessionFactory;
import com.wifihi.persistance.User;
import com.wifihi.terminalServerService.LoginResultString;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;
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
		JSONObject json = new JSONObject(this.reqContent);
		String tel=json.getString("tel");
		String passwd=json.getString("passwd");
		User user = new User(); 
		Session session = HibernateSessionFactory.getSession();
		result = new LoginResultString();
		try{
				session.beginTransaction();
				//濮濄倕顦垫担璺ㄦ暏HQL 娴ｅ棜绻曢崣顖欎簰閺堝鐨濈憗鍛畱閺囨潙銈介惃鍕煙濞夛拷
				user = (User)session.createQuery("from user u where u.phonenumber ='"+tel+"'");
				if(user.getPassword().equals(passwd)){
					result.setResult(user.getUserId().toString());
				}
				else if(!user.getPassword().equals(passwd)){
					result.setResult("err10002");
				}
				else{
					result.setResult("err10003");
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
