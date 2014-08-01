package com.wifihi.persistance;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
public enum AbstractTempId implements java.io.Serializable{
	
	private Long id;
	private Wspuser wspUser;
	private Time pushTime;
	private String userRegistID;
	private String wspRegistID;
	private Time responseTime;
	private User user;
	
	public AbstractTempId(Wspuser wspUser,Time pushTime,String userRegistID,
			String wspRegistID,Time responseTime,User user){
		this.wspUser = wspUser;
		this.pushTime = pushTime;
		this.userRegistID = userRegistID;
		this.wspRegistID = wspRegistID;
		this.responseTime = responseTime;
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Wspuser getWspUser() {
		return wspUser;
	}
	public void setWspUser(Wspuser wspUser) {
		this.wspUser = wspUser;
	}
	public Time getPushTime() {
		return pushTime;
	}
	public void setPushTime(Time pushTime) {
		this.pushTime = pushTime;
	}
	public String getUserRegistID() {
		return userRegistID;
	}
	public void setUserRegistID(String userRegistID) {
		this.userRegistID = userRegistID;
	}
	public String getWspRegistID() {
		return wspRegistID;
	}
	public void setWspRegistID(String wspRegistID) {
		this.wspRegistID = wspRegistID;
	}
	public Time getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Time responseTime) {
		this.responseTime = responseTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
