package com.bywr.lease.activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service 
{

	public MyService() 
	{
		
	}

	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		
		new Thread(new Runnable() 
								{
						            @Override
						            public void run() {
						                while (true) {
						                    try 
						                    {
						                        Thread.sleep(1000);
						                    } catch (InterruptedException e)
						                    {
						                    	
						                    }
						                    
						                    Intent intent = new Intent();
						                    intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
						                    MyService.this.sendBroadcast(intent);
						                }
						            }
						        }
					).start();
		
		

	}

}
