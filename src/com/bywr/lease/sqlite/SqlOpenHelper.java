package com.bywr.lease.sqlite;

import com.bywr.lease.constant.Contant;
import com.bywr.lease.constant.SQLConstant;
import com.bywr.lease.constant.SharedPreferceConst;
import com.bywr.lease.constant.UserInfoTable;
import com.bywr.lease.constant.WaterPowerTable;
import com.bywr.lease.handler.HandlerUserInfoTable;
import com.bywr.lease.utils.SharedPreferenceTool;
import com.bywr.lease.utils.SingleToolClass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作类
 * @author Administrator
 *
 */
public class SqlOpenHelper extends SQLiteOpenHelper 
{

	private static SqlOpenHelper sqlOpenHelper = null;
	
	public SqlOpenHelper(Context context, CursorFactory factory) 
	{
		super(context, SQLConstant.SQLDB_NAME, factory, SQLConstant.SQL_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
        db.execSQL(UserInfoTable.CREATE_TABLE);
        db.execSQL(WaterPowerTable.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) 
	{
		db.execSQL(UserInfoTable.DELETE_TABLE);
		db.execSQL(WaterPowerTable.DELETE_TABLE);
		onCreate(db);
	}
	
	public static SqlOpenHelper getSqlOpenHelper()
	{
		if(sqlOpenHelper == null)
		{
			sqlOpenHelper = new SqlOpenHelper(SingleToolClass.context, null);
			
			boolean isFirstOpenApp = SharedPreferenceTool.getValue(SharedPreferceConst.FIRST_OPEN_APP,true);
			if(isFirstOpenApp)
			{
				SharedPreferenceTool.setValue(SharedPreferceConst.FIRST_OPEN_APP, false);
				//数据库建立的时候，插入十个房间的初始数据
		        for (int i = 0; i < Contant.ROOM_IDS.length; i++) 
				{
		        	HandlerUserInfoTable.insertDataToTable(Contant.ROOM_IDS[i], "租客 "+i, i, i, i, i, i, "2013/1/1","备注信息"+i);
				}
			}
			
		}
		
		return sqlOpenHelper;
	}

}
