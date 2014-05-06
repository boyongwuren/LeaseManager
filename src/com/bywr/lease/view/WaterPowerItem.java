package com.bywr.lease.view;


import com.example.leasemanagerc.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class WaterPowerItem extends LinearLayout {

	private EditText monthTxt;
	private EditText waterNumTxt;
	private EditText powerNumTxt;
	
	public WaterPowerItem(Context context)
	{
		super(context);
		
		LayoutInflater.from(context).inflate(R.layout.water_power_item, this,true);
		
		monthTxt = (EditText) findViewById(R.id.month);
		waterNumTxt = (EditText) findViewById(R.id.waterNum);
		powerNumTxt = (EditText) findViewById(R.id.powerNum);
		
		monthTxt.setFocusable(false);
		waterNumTxt.setFocusable(false);
		powerNumTxt.setFocusable(false);
		
	}
	
	/**
	 * 设置日期
	 * @param month
	 */
	public void setMonthTxt(String month)
	{
		monthTxt.setText(month);
	}
	
	/**
	 * 设置水量
	 * @param month
	 */
	public void setWaterTxt(int waterNum)
	{
		waterNumTxt.setText(waterNum+"");
	}
	
	/**
	 * 设置电量
	 * @param powerNum
	 */
	public void setPowerTxt(int powerNum)
	{
		powerNumTxt.setText(powerNum+"");
	}
	
	

	 

}
