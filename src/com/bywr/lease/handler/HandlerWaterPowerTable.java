package com.bywr.lease.handler;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bywr.lease.constant.Contant;
import com.bywr.lease.constant.UserInfoTable;
import com.bywr.lease.constant.WaterPowerTable;
import com.bywr.lease.sqlite.SqlOpenHelper;
import com.bywr.lease.vo.WaterPowerVo;

public class HandlerWaterPowerTable 
{

	/**
	 * 插入数据
	 * @param dataInfo
	 * @param waterInfo
	 * @param powerInfo
	 */
      public static void insertDataToTable(int roomID,String dataInfo,int waterInfo,int powerInfo)
      {
    	  SQLiteDatabase db = SqlOpenHelper.getSqlOpenHelper().getReadableDatabase();
    	  ContentValues cv = new ContentValues();
    	  cv.put(WaterPowerTable.ROOMID, roomID);
    	  cv.put(WaterPowerTable.DATA_INFO, dataInfo);
    	  cv.put(WaterPowerTable.WATER_INFO, waterInfo);
    	  cv.put(WaterPowerTable.POWER_INFO, powerInfo);
    	  
    	  db.insert(WaterPowerTable.TABLE_NAME, null, cv);
    	  db.close();
      }
      
      /**
       * 插入数据
       * @param vo
       */
      public static void insertDataToTable(WaterPowerVo vo)
      {
    	  insertDataToTable(vo.roomId, vo.dataInfo, vo.waterInfo, vo.powerInfo);
      }
      
      /**
       * 插入数据
       * @param wpvos
       */
    public static void insertDataToTable(ArrayList<WaterPowerVo> wpvos)
      {
    	  int len = wpvos.size();
    	  for (int i = 0; i < len; i++) 
    	  {
			  WaterPowerVo vo = wpvos.get(i);
			  insertDataToTable(vo);
		  }
      }
      
      /**
       * 获取房号对应的数据信息
       * @param roomId
       * @return
       */
      public static ArrayList<WaterPowerVo> getDataFromTable(int roomId)
      {
    	  SQLiteDatabase db = SqlOpenHelper.getSqlOpenHelper().getReadableDatabase();
    	  Cursor cursor = db.query(WaterPowerTable.TABLE_NAME, null, WaterPowerTable.ROOMID+"=?", new String[]{roomId+""}, null, null, null);
    	  
    	  ArrayList<WaterPowerVo> vos = new ArrayList<WaterPowerVo>();
    	  
    	  while(cursor.moveToNext())
    	  {
    		  WaterPowerVo vo = new WaterPowerVo();
    		  String dataInfo = cursor.getString(cursor.getColumnIndex(WaterPowerTable.DATA_INFO));
    		  int waterInfo = cursor.getInt(cursor.getColumnIndex(WaterPowerTable.WATER_INFO));
    		  int powerInfo = cursor.getInt(cursor.getColumnIndex(WaterPowerTable.POWER_INFO));
    		  
    		  vo.roomId = roomId;
    		  vo.dataInfo = dataInfo;
    		  vo.waterInfo = waterInfo;
    		  vo.powerInfo = powerInfo;
    		  
    		  vos.add(vo);
    	  }
    	  
    	  cursor.close();
    	  db.close();
    	  return vos;
      }
      
      /**
       * 通过房号和日期更新水电数量信息
       * @param roomId
       * @param dataInfo
       * @param waterInfo
       * @param powerInfo
       */
      public static void updataTableData(int roomId,String dataInfo,String waterInfo,String powerInfo)
      {
          SQLiteDatabase db = SqlOpenHelper.getSqlOpenHelper().getReadableDatabase();
    	  
    	  ContentValues cv = new ContentValues();
    	  cv.put(WaterPowerTable.ROOMID, roomId);
    	  cv.put(WaterPowerTable.DATA_INFO, dataInfo);
    	  cv.put(WaterPowerTable.WATER_INFO, waterInfo);
    	  cv.put(WaterPowerTable.POWER_INFO, powerInfo);
    	  
    	  db.update(WaterPowerTable.TABLE_NAME, cv, WaterPowerTable.ROOMID+"=? AND "+WaterPowerTable.DATA_INFO+"=?", new String[]{roomId+"",dataInfo});
    	  db.close();
      }
      
      /**
       * 获取所有的水电数据
     * @return
     */
    public static ArrayList<WaterPowerVo> getAllWaterPowerData()
      {
    	 SQLiteDatabase db = SqlOpenHelper.getSqlOpenHelper().getReadableDatabase();
  	     Cursor cursor = db.query(WaterPowerTable.TABLE_NAME, null, null, null, null, null, null);
  	  
  	     ArrayList<WaterPowerVo> vos = new ArrayList<WaterPowerVo>();
  	  
	  	  while(cursor.moveToNext())
	  	  {
	  		  WaterPowerVo vo = new WaterPowerVo();
	  		  int roomId = cursor.getInt(cursor.getColumnIndex(WaterPowerTable.ROOMID));
	  		  String dataInfo = cursor.getString(cursor.getColumnIndex(WaterPowerTable.DATA_INFO));
	  		  int waterInfo = cursor.getInt(cursor.getColumnIndex(WaterPowerTable.WATER_INFO));
	  		  int powerInfo = cursor.getInt(cursor.getColumnIndex(WaterPowerTable.POWER_INFO));
	  		  
	  		  vo.roomId = roomId;
	  		  vo.dataInfo = dataInfo;
	  		  vo.waterInfo = waterInfo;
	  		  vo.powerInfo = powerInfo;
	  		  
	  		  vos.add(vo);
	  	  }
  	  
	  	  cursor.close();
	  	  db.close();
	  	  return vos;
      }
    
    /**
     * 获取数据表的字符串保存形式
     * @return
     */
    public static String getAllDataString()
    {
    	String result = "";
    	ArrayList<WaterPowerVo> wpvos = getAllWaterPowerData();
    	int len = wpvos.size();
    	for (int i = 0; i < len; i++) 
    	{
			WaterPowerVo vo = wpvos.get(i);
			result += vo.toString()+Contant.EACH_CUR_VO;
		}
    	
    	if(len>0)
    	{
    		result = result.substring(0, result.length()-1);
    	}
    	
    	return result;
    }


    /**
     * 删除表中全部数据 
     */
    public static void clearTable()
    {
    	SQLiteDatabase sqLiteDatabase = SqlOpenHelper.getSqlOpenHelper().getReadableDatabase();
    	sqLiteDatabase.delete(WaterPowerTable.TABLE_NAME, null, null);
    	sqLiteDatabase.close();
    }
    
}
