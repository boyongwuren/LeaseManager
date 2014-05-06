package com.bywr.lease.broadcasetReceiver;

import com.bywr.lease.activity.CacuateInfoActivity;
import com.bywr.lease.constant.BundleConst;
import com.bywr.lease.handler.HandlerUserInfoTable;
import com.bywr.lease.utils.AlarmTool;
import com.bywr.lease.vo.UserInfoVo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 闹钟唤醒了
 * @author zmp
 *
 */
public class AlarmBroadcaseReceiver extends BroadcastReceiver 
{

	public AlarmBroadcaseReceiver() 
	{
		 
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) 
	{
         int roomId = arg1.getExtras().getInt(BundleConst.ROOMID);
         UserInfoVo vo = HandlerUserInfoTable.getDataFromTable(roomId);
         
         Intent intent = new Intent(arg0,CacuateInfoActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         intent.putExtras(arg1.getExtras());
         arg0.startActivity(intent);
         
         AlarmTool.setAlarmTime(arg0, roomId, vo.enterTime);
	}

}
