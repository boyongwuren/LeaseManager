package com.bywr.lease.application;

import com.bywr.lease.constant.Contant;
import com.bywr.lease.utils.SingleToolClass;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class LeaseApplication extends Application {

	public LeaseApplication()
	{
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		SingleToolClass.context = this;
		
		SingleToolClass.sharedPreferences = this.getSharedPreferences(Contant.SHAREDPREFERENCE, Context.MODE_MULTI_PROCESS);
	}

}
