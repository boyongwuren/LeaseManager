package com.bywr.lease.vo;

import com.bywr.lease.constant.Contant;

/**
 * 水电数据结构
 * @author Administrator
 *
 */
public class WaterPowerVo 
{
    
	/**
	 * 房号
	 */
	public int roomId = 0;
	
	/**
	 * 日期信息
	 */
	 public String dataInfo;
	 
	 /**
	  * 水信息
	  */
	 public int waterInfo;
	 
	 /**
	  * 电信息
	  */
	 public int powerInfo;
	 
	 @Override
	public String toString() 
	 {
		return roomId+Contant.EACH_CUT_LINE+dataInfo+Contant.EACH_CUT_LINE+waterInfo+Contant.EACH_CUT_LINE+powerInfo;
	}

}
