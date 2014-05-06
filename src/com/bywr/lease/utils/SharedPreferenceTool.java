package com.bywr.lease.utils;

public class SharedPreferenceTool 
{

	 /**
	  * 从sharedpreference里获取数据
	 * @param key
	 * @return
	 */
	public static boolean getValue(String key,boolean defValue)
	 {
		 return SingleToolClass.sharedPreferences.getBoolean(key, defValue);
	 }
	
	/**
	 * 设置数值
	 * @param key
	 * @param value
	 */
	public static void setValue(String key,boolean value)
	{
		SingleToolClass.sharedPreferences.edit().putBoolean(key, value).commit();
	}

}
