package com.wifihi.persistance;

import java.sql.Time;

public class TempId extends AbstractTempId java.io.Serializable{
	public TempId(Wspuser wspUser,Time pushTime,String userRegistID,
			String wspRegistID,Time responseTime,User user){
		super(wspUser,pushTime,userRegistID,wspRegistID,responseTime,user);
	}
}
