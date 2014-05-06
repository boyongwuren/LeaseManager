package com.bywr.lease.constant;

/**
 * 用户信息表
 * @author Administrator
 *
 */
public class UserInfoTable 
{
	/**
	 * 用户信息表
	 */
     public static final String TABLE_NAME = "userInfoTable";
     
     
     /**
      * 房号
      */
     public static final String ROOM_ID = "roomId";
     
     /**
      * 用户名
      */
     public static final String USER_NAME = "userName";
     
     /**
      * 押金
      * antecedent
      */
     public static final String ANTECEDENT = "antecedent";

     /**
      * 电话
      */
     public static final String PHONENUM = "phoneNum";

     /**
      * 租金
      */
     public static final String RENTNUM = "rentNum";

     /**
      * 网费
      */
     public static final String NET_MONTY = "netMoney";

     /**
      * 物业
      */
     public static final String PROPERTY = "property";

     /**
      * 入住时间
      */
     public static final String ENTER_TIME = "enterTime";
     
     /**
      * 备注
      */
     public static final String NOTES = "notes";
     
     /**
      * 创建表语句
      */
     public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + 
																				     ROOM_ID + " INTEGER primary key autoincrement, " + 
																				     USER_NAME + " TEXT, "+ 
																				     ANTECEDENT +" INTEGER ," +
																				     PHONENUM +" TEXT ," +
																				     RENTNUM +" INTEGER ," +
																				     NET_MONTY +" INTEGER ," +
																				     PROPERTY +" INTEGER ," +
																				     ENTER_TIME +" TEXT ," +
																				     NOTES +" TEXT "+ 
																			    ");";  
     /**
      * 删除表
      */
     public static final String DELETE_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
     
}
