package com.bywr.lease.constant;

import java.io.File;

import android.os.Environment;

public class Contant
{

	public final static int MENU_ADD_WATERPOWER = 1;//添加一个月水电
	public final static int MENU_EXPORT_DATA_ID = 2;//导出
	public final static int MENU_IMPORT_DATA_ID = 3; //导入
	public final static int MENU_CACULATE = 4;//结算菜单
	public final static int MENU_SCREENCAPTURE= 5;//截屏
	public final static int MENU_SENDMSG= 6;//发送短信

	
	public final static String APP_DICTROY_DB= Environment.getExternalStorageDirectory().getPath()+File.separator+"leaseManager"+File.separator+"db"; 

	public final static String APP_DICTROY_PIC= Environment.getExternalStorageDirectory().getPath()+File.separator+"leaseManager"+File.separator+"pic"; 
	
	
	
	
	/**
	 * 房间个数
	 */
	public final static int ROOM_NUM = 10;
	
	public final static String WATER_UNIT = " (吨)";

	public final static String POWER_UNIT = " (度)";
	
	/**
	 * 十个房间的房号
	 */
	public final static int[] ROOM_IDS = {101 ,102 ,103, 104 ,105,201 ,202 ,203 ,204 ,205};
	
	/**
	 * 项目的sharedpreference的key
	 */
	public final static String SHAREDPREFERENCE = "sharedPreference";
	
	/*
	 * 导出数据时候每一行的分割符号
	 */
	public final static String EACH_CUT_LINE = "#";
	
	/*
	 * 导出数据时候每一条数据的分割符号
	 */
	public final static String EACH_CUR_VO = "@";
	
	/**
	 * 导出去的时候，用户信息表格文件名称
	 */
	public final static String EXPORT_USERINFO_FILENAME = "userInfo.db";

	/**
	 * 导出去的时候，用户水电表格文件名称
	 */
	public final static String EXPORT_WATERPOWER_FILENAME = "waterPower.db";
}
