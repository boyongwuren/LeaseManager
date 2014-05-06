package com.bywr.lease.constant;

/**
 * 水电表
 * @author zmp
 *
 */
public class WaterPowerTable 
{
      public static final String TABLE_NAME = "waterPowerTabel";

      public static final String ROOMID = "roomID";
      
      public static final String DATA_INFO = "dataInfo";

      public static final String WATER_INFO = "waterInfo";

      public static final String POWER_INFO = "powerInfo";
      
      /**
       * 创建表语句
       */
      public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + 
    		                                                                         ROOMID + " INTEGER , " + 
 																				     DATA_INFO + " TEXT, "+ 
 																				     WATER_INFO +" INTEGER ," +
 																				     POWER_INFO +" INTEGER " +
 																			     ");";  
      
      
      /**
       * 删除表
       */
      public static final String DELETE_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
      
      
}
