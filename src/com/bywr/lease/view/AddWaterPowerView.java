package com.bywr.lease.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.bywr.lease.constant.Contant;
import com.bywr.lease.interfaces.RecognizeSpeechInterface;
import com.example.leasemanagerc.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 添加一个月水电的视图
 * @author Administrator
 *
 */
public class AddWaterPowerView extends LinearLayout 
{

	private EditText monthTxt;
	private EditText waterNumTxt;
	private EditText powerNumTxt;
	
	public Button comitBtn;
	
	public Button cancelBtn;
	
	private RecognizeSpeechInterface recognizeSpeechInterface;
	
	public AddWaterPowerView(Context context,RecognizeSpeechInterface recognizeSpeechInterface) 
	{
		super(context);
		LayoutInflater.from(context).inflate(R.layout.add_water_power_view, this,true);
		this.recognizeSpeechInterface = recognizeSpeechInterface;
		monthTxt = (EditText) findViewById(R.id.month);
		waterNumTxt = (EditText) findViewById(R.id.waterNum);
		powerNumTxt = (EditText) findViewById(R.id.powerNum);

		comitBtn = (Button) findViewById(R.id.comitBtn);
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		
		
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINA);//可以方便地修改日期格式 
		String hehe = dateFormat.format( now ); 
		monthTxt.setText(hehe);
		
		waterNumTxt.setText("");
		powerNumTxt.setText("");
		
		monthTxt.setOnClickListener(new MonthClickClass());
		monthTxt.setFocusable(false);
		
		FocusChangeClass focusChangeClass = new FocusChangeClass();
		waterNumTxt.setOnFocusChangeListener(focusChangeClass);
		powerNumTxt.setOnFocusChangeListener(focusChangeClass);
	}
	
	private class FocusChangeClass implements OnFocusChangeListener
	{
		@Override
		public void onFocusChange(View arg0, boolean arg1) 
		{
			 if(arg1 == true)
			 {
				 recognizeSpeechInterface.setInputTxt((TextView) arg0);
				 ((TextView)arg0).setText("");
			 }
		}
	}
	
	
	
	
	
	private DatePickerDialog datePickerDialog;
	
	/**
	 * 月份被点击，跳出选择日期控件
	 * @author zmp
	 *
	 */
	private class MonthClickClass implements OnClickListener
	{
		@Override
		public void onClick(View v) 
		{
			if(datePickerDialog == null)
			{
				Calendar calendar = Calendar.getInstance();
				datePickerDialog = new DatePickerDialog(AddWaterPowerView.this.getContext(), new DatePickerSelectClass(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH));
			}
		    datePickerDialog.show();
		}
	}
	
	private class DatePickerSelectClass implements OnDateSetListener
	{
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) 
		{
			String monthString = (monthOfYear+1)+"";
			if(monthString.length() == 1)
			{
				monthString = "0"+monthString;
			}
			
			String dayString = dayOfMonth+"";
			if(dayString.length()==1)
			{
				dayString = "0"+dayString;
			}
			monthTxt.setText(year+"/"+monthString+"/"+dayString);
		}
	}
	
	
	/**
	 * 获取日期
	 * @return
	 */
	public String getMonth()
	{
		return monthTxt.getText().toString();
	}
	
	/**
	 *  获取水量
	 * @return
	 */
	public int getWaterNum()
	{
		return Integer.parseInt(waterNumTxt.getText().toString());
	}

	/**
	 * 获取电量
	 * @return
	 */
	public int getPowerNum()
	{
		return Integer.parseInt(powerNumTxt.getText().toString());
	}
	

	 

}
