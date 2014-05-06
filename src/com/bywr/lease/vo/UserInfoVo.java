package com.bywr.lease.vo;

import com.bywr.lease.constant.Contant;

public class UserInfoVo 
{

	/**
	 * 房号
	 */
	public int roomId = 0;
	
	/**
	 * 用户名
	 */
	public String userName = "";
	
	/**
	 * 押金
	 */
	public int antecedent = 0;
	
	/**
	 * 电话号码
	 */
	public String phoneNum = "";
	
	/**
	 * 租金
	 */
	public int rentNum = 0;
	
	/**
	 * 网费
	 */
	public int netMoney = 0;
	
	/**
	 * 物业
	 */
	public int protery = 0;
	
	/**
	 * 入住时间
	 */
	public String enterTime = "";

	/**
	 * 备注
	 */
	public String notes = "";
	
	@Override
	public String toString() 
	{
		return  roomId+Contant.EACH_CUT_LINE+userName+Contant.EACH_CUT_LINE+antecedent+Contant.EACH_CUT_LINE+phoneNum+Contant.EACH_CUT_LINE+rentNum+Contant.EACH_CUT_LINE+netMoney+Contant.EACH_CUT_LINE+protery+Contant.EACH_CUT_LINE+enterTime+Contant.EACH_CUT_LINE+notes;
	}

}
