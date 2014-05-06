package com.bywr.lease.utils;

import java.util.Calendar;

import com.bywr.lease.constant.BundleConst;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 闹铃工具
 * @author zmp
 *
 */
public class AlarmTool 
{

	public static PendingIntent setAlarmTime(Context context,int roomId,String enterTime) 
	{
		int hourConst = 12;
		int mincConst = 20;
		int secConst = 0;
		
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        
        Intent intent = new Intent("android.com.bywr.lease.alarmDemo.action");
        Bundle bundle = new Bundle();
        bundle.putInt(BundleConst.ROOMID, roomId);
        intent.putExtras(bundle);
        
        PendingIntent sender = PendingIntent.getBroadcast(context, roomId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        
        Calendar curCalendar = Calendar.getInstance();
        curCalendar.setTimeInMillis(System.currentTimeMillis());
        
        String[] times = enterTime.split("/");
        Calendar oldCalendar = Calendar.getInstance();
        oldCalendar.set(curCalendar.get(Calendar.YEAR), curCalendar.get(Calendar.MONTH), Integer.parseInt(times[2]), hourConst, mincConst, secConst);//入住时间的12:20提醒
        
         
        if(curCalendar.before(oldCalendar))
        {
        	//当前时间在入住时间之前
        	//本月提醒
        	curCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(times[2]));
        	curCalendar.set(Calendar.HOUR_OF_DAY, hourConst);
        	curCalendar.set(Calendar.MINUTE, mincConst);
        	curCalendar.set(Calendar.SECOND, secConst);
        	
        }else 
        {
			//下个月提醒了
        	int curMonth = curCalendar.get(Calendar.MONTH)+1;
        	if(curMonth+1>12)
        	{
        		//明年了
        		curCalendar.set(Calendar.YEAR, curCalendar.get(Calendar.YEAR)+1);
        		curCalendar.set(Calendar.MONTH, 0);
        	}else 
        	{
				//今年
        		curCalendar.set(Calendar.MONTH, curCalendar.get(Calendar.MONTH)+1);
			}
        	curCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(times[2]));
        	curCalendar.set(Calendar.HOUR_OF_DAY, hourConst);
        	curCalendar.set(Calendar.MINUTE, mincConst);
        	curCalendar.set(Calendar.SECOND, secConst);
		}
        
        am.set(AlarmManager.RTC_WAKEUP, curCalendar.getTimeInMillis(), sender);
        
        return sender;
    }

}
