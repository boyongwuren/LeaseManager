package com.bywr.lease.broadcasetReceiver;

import java.util.ArrayList;

import com.bywr.lease.constant.Contant;
import com.bywr.lease.handler.HandlerUserInfoTable;
import com.bywr.lease.utils.AlarmTool;
import com.bywr.lease.utils.FileUtil;
import com.bywr.lease.vo.UserInfoVo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 监听开机启动
 * @author zmp
 *
 */
public class BootBroadcaseReceiver extends BroadcastReceiver 
{

	public BootBroadcaseReceiver()
	{
		 
	}

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		 ArrayList<UserInfoVo> uVos = HandlerUserInfoTable.getTableAllData();
         int len = uVos.size();
         
         for (int i = 0; i < len; i++)
         {
			UserInfoVo vo = uVos.get(i);
			AlarmTool.setAlarmTime(context, vo.roomId, vo.enterTime);
		 }
		 
	}

}
