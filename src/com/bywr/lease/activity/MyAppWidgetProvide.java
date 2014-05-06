package com.bywr.lease.activity;

import java.util.Calendar;

import com.example.leasemanagerc.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyAppWidgetProvide extends AppWidgetProvider 
{

	public MyAppWidgetProvide()
	{
		
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) 
	{
		System.out.println("onUpdate");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		int len = appWidgetIds.length;
		for (int i = 0; i < len; i++) 
		{
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgetprovidelayout);
			Calendar calendar = Calendar.getInstance();
			remoteViews.setTextViewText(R.id.txt, "time = "+calendar.getTimeInMillis());
			
			Intent intent = new Intent();
			intent.setAction("com.bywr.lease.manager");
			
//			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			
			remoteViews.setOnClickPendingIntent(R.id.button, pendingIntent);
			int index = appWidgetIds[i];
			appWidgetManager.updateAppWidget(index, remoteViews);
		}
	}
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		super.onReceive(context, intent);
		 
		 
		
		
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widgetprovidelayout);
		Calendar calendar = Calendar.getInstance();
		remoteViews.setTextViewText(R.id.txt, "time = "+calendar.getTimeInMillis());
		
		
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, MyAppWidgetProvide.class));
		appWidgetManager.updateAppWidget(ids, remoteViews);
		
	}
	
	

}
