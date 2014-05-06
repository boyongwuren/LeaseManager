package com.bywr.lease.handler;

import java.util.ArrayList;

import com.bywr.lease.constant.Contant;
import com.bywr.lease.constant.UserInfoTable;
import com.bywr.lease.sqlite.SqlOpenHelper;
import com.bywr.lease.vo.UserInfoVo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *  处理用户表
 * @author Administrator
 *
 */
public class HandlerUserInfoTable 
{

      /**
       * 插入数据
     * @param roomId  房号
     * @param userName  名字
     * @param antecedent  押金
     * @param phoneNum 电话
     * @param rentNum  租金
     * @param netMoney 网费
     * @param property 物业
     * @param enterTime 入住时间
     * @param notes  备注
     */
    public static void insertDataToTable(int roomId,String userName,int antecedent,long phoneNum, int rentNum,int netMoney,int property, String enterTime,String notes)
      {
    	  SQLiteDatabase db = SqlOpenHelper.getSqlOpenHelper().getWritableDatabase();
    	  ContentValues cv = new ContentValues();
    	  cv.put(UserInfoTable.ROOM_ID, roomId);
    	  cv.put(UserInfoTable.USER_NAME, userName);
    	  cv.put(UserInfoTable.ANTECEDENT, antecedent);
    	  cv.put(UserInfoTable.PHONENUM, phoneNum);
    	  cv.put(UserInfoTable.RENTNUM, rentNum);
    	  cv.put(UserInfoTable.NET_MONTY, netMoney);
    	  cv.put(UserInfoTable.PROPERTY, property);
    	  cv.put(UserInfoTable.ENTER_TIME, enterTime);
    	  cv.put(UserInfoTable.NOTES, notes);
    	  
    	  db.insert(UserInfoTable.TABLE_NAME, null, cv);
    	  db.close();
      }
      
      /**
       * 插入数据
     * @param vo
     */
    public static void insertDataToTable(UserInfoVo vo)
      {
    	  insertDataToTable(vo.roomId, vo.userName, vo.antecedent, Long.parseLong(vo.phoneNum), vo.rentNum, vo.netMoney, vo.protery, vo.enterTime, vo.notes);
      }
      
      /**
       * 插入数据
     * @param uVos
     */
    public static void insertDataToTable(ArrayList<UserInfoVo> uVos)
      {
    	  int len = uVos.size();
    	  for (int i = 0; i < len; i++) 
    	  {
			 insertDataToTable(uVos.get(i));
		  }
      }
      
      /**
       * 通过房间号 获取用户信息
       * @param roomId
       * @return
       */
      public static UserInfoVo getDataFromTable(int roomId)
      {
    	  SQLiteDatabase db = SqlOpenHelper.getSqlOpenHelper().getReadableDatabase();
    	  Cursor cursor = db.query(UserInfoTable.TABLE_NAME, null, UserInfoTable.ROOM_ID+"=?", new String[]{roomId+""}, null, null, null);
    	  
    	  UserInfoVo vo = new UserInfoVo();
    	  
    	  while(cursor.moveToNext())
    	  {
    		  getDataFromCursor(cursor, vo);
    	  }
    	  
    	  cursor.close();
    	  db.close();
    	  return vo;
      }
      
      /**
       * 更新数据
       * @param vo
       */
      public static void updataTableData(UserInfoVo vo)
      {
    	  SQLiteDatabase db = SqlOpenHelper.getSqlOpenHelper().getReadableDatabase();
    	  
    	  ContentValues cv = new ContentValues();
    	  cv.put(UserInfoTable.ROOM_ID, vo.roomId);
    	  cv.put(UserInfoTable.USER_NAME, vo.userName);
    	  cv.put(UserInfoTable.ANTECEDENT, vo.antecedent);
    	  cv.put(UserInfoTable.PHONENUM, vo.phoneNum);
    	  cv.put(UserInfoTable.RENTNUM, vo.rentNum);
    	  cv.put(UserInfoTable.NET_MONTY, vo.netMoney);
    	  cv.put(UserInfoTable.PROPERTY, vo.protery);
    	  cv.put(UserInfoTable.ENTER_TIME, vo.enterTime);
    	  cv.put(UserInfoTable.NOTES, vo.notes);
    	  
    	  db.update(UserInfoTable.TABLE_NAME, cv, UserInfoTable.ROOM_ID+"=?", new String[]{vo.roomId+""});
    	  db.close();
      }
      
      
      /**
       * 获取表里头的全部数据
     * @return
     */
    public static ArrayList<UserInfoVo> getTableAllData()
      {
    	  SQLiteDatabase db = SqlOpenHelper.getSqlOpenHelper().getReadableDatabase();
    	  Cursor cursor = db.query(UserInfoTable.TABLE_NAME, null, null, null, null, null, null);
    	  ArrayList<UserInfoVo> uVos = new ArrayList<UserInfoVo>();
    	  while (cursor.moveToNext())
    	  {
    		  UserInfoVo vo = new UserInfoVo();
			  getDataFromCursor(cursor, vo);
			  uVos.add(vo);
		  }
    	  return uVos;
      }
    
    /**
     * 返回数据表的字符串表示形式
     * @return
     */
    public static String getTabeleDatString()
    {
    	String result = "";
    	ArrayList<UserInfoVo> uVos = getTableAllData();
    	int len = uVos.size();
    	for (int i = 0; i < len; i++) 
    	{
			UserInfoVo vo = uVos.get(i);
			String tempString = vo.toString();
			result+=tempString+Contant.EACH_CUR_VO;
		}
    	
    	if(len>0)
    	{
    		result = result.substring(0, result.length()-1);
    	}
    	
    	return result;
    }
      
      /**
       * 从游标获取数据
     * @param cursor
     * @param vo
     */
    private static void getDataFromCursor(Cursor cursor,UserInfoVo vo)
      {
    	  int roomId = cursor.getInt(cursor.getColumnIndex(UserInfoTable.ROOM_ID));
    	  String userName = cursor.getString(cursor.getColumnIndex(UserInfoTable.USER_NAME));
		  int antecedent = cursor.getInt(cursor.getColumnIndex(UserInfoTable.ANTECEDENT));
		  String phoneNum = cursor.getString(cursor.getColumnIndex(UserInfoTable.PHONENUM));
		  int rentNum = cursor.getInt(cursor.getColumnIndex(UserInfoTable.RENTNUM));
		  int netMoney = cursor.getInt(cursor.getColumnIndex(UserInfoTable.NET_MONTY));
		  int property = cursor.getInt(cursor.getColumnIndex(UserInfoTable.PROPERTY));
		  String enterTime = cursor.getString(cursor.getColumnIndex(UserInfoTable.ENTER_TIME));
		  String notes = cursor.getString(cursor.getColumnIndex(UserInfoTable.NOTES));
		  
		  vo.roomId = roomId;
		  vo.userName = userName;
		  vo.antecedent = antecedent;
		  vo.phoneNum = phoneNum;
		  vo.rentNum = rentNum;
		  vo.netMoney = netMoney;
		  vo.protery = property;
		  vo.enterTime = enterTime;
		  vo.notes = notes;
      }
      
    
    /**
     * 删除表中全部数据 
     */
    public static void clearTable()
    {
    	SQLiteDatabase sqLiteDatabase = SqlOpenHelper.getSqlOpenHelper().getReadableDatabase();
    	sqLiteDatabase.delete(UserInfoTable.TABLE_NAME, null, null);
    	sqLiteDatabase.close();
    }
      
}
