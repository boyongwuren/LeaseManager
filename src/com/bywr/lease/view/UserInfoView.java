package com.bywr.lease.view;

import java.util.ArrayList;
import java.util.Calendar;

import com.bywr.lease.handler.HandlerUserInfoTable;
import com.bywr.lease.handler.HandlerWaterPowerTable;
import com.bywr.lease.interfaces.BackWaterPowerInterface;
import com.bywr.lease.interfaces.OpenWaterPowerInterface;
import com.bywr.lease.utils.AlarmTool;
import com.bywr.lease.utils.SingleToolClass;
import com.bywr.lease.vo.UserInfoVo;
import com.bywr.lease.vo.WaterPowerVo;
import com.example.leasemanagerc.R;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;


/**
 * 用户信息
 * 
 * @author Administrator
 * 
 */
public class UserInfoView extends LinearLayout {

	private EditText roomIDTxt;

	private EditText useNameTxt;
	
	private EditText antecedentTxt;

	private EditText phoneNumTxt;

	private EditText rentNumTxt;

	private EditText netMoneyTxt;

	private EditText propertyTxt;

	private EditText enterTimeTxt;
	
	private EditText notesTxt;

	private EditText waterNumTxt;

	private EditText powerNumTxt;

	private Button editorBtn;

	private Button backBtn;

	private Button comitBtn;
	
	private BtnOnClickClass btnOnClickClass = new BtnOnClickClass();
	
	private OpenWaterPowerInterface openWaterPowerInterface;
	
	private UserInfoVo vo;
	
	
	private boolean canEditor = false;
	
	
	public UserInfoView(Context context , OpenWaterPowerInterface openWaterPowerInterface)
	{
		super(context);
		
        this.openWaterPowerInterface = openWaterPowerInterface;
		
		LayoutInflater.from(context).inflate(R.layout.user_info, this, true);

		roomIDTxt = (EditText) findViewById(R.id.roomNum);
		useNameTxt = (EditText) findViewById(R.id.useName);
		antecedentTxt = (EditText) findViewById(R.id.antecedent);
		phoneNumTxt = (EditText) findViewById(R.id.phoneNum);
		rentNumTxt = (EditText) findViewById(R.id.rentNum);
		netMoneyTxt = (EditText) findViewById(R.id.netMoney);
		propertyTxt = (EditText) findViewById(R.id.property);
		enterTimeTxt = (EditText) findViewById(R.id.enterTime);
		notesTxt = (EditText) findViewById(R.id.notes);
		waterNumTxt = (EditText) findViewById(R.id.waterNum);
		powerNumTxt = (EditText) findViewById(R.id.powerNum);

		editorBtn = (Button) findViewById(R.id.editorBtn);
		backBtn = (Button) findViewById(R.id.backBtn);
		comitBtn = (Button) findViewById(R.id.comitBtn);

		backBtn.setVisibility(View.INVISIBLE);
		comitBtn.setVisibility(View.INVISIBLE);
		
		backBtn.setOnClickListener(btnOnClickClass);
		editorBtn.setOnClickListener(btnOnClickClass);
		comitBtn.setOnClickListener(btnOnClickClass);
		powerNumTxt.setOnClickListener(btnOnClickClass);
		waterNumTxt.setOnClickListener(btnOnClickClass);
		enterTimeTxt.setOnClickListener(btnOnClickClass);
		
		setCanEditor(false);
		
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			if(canEditor)
			{
				backEditor();
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 是否能编辑
	 * @param yes
	 */
	private void setCanEditor(boolean yes)
	{
		canEditor = yes;
		roomIDTxt.setFocusable(yes);
		roomIDTxt.setFocusableInTouchMode(yes);
		
		useNameTxt.setFocusable(yes);
		useNameTxt.setFocusableInTouchMode(yes);
		
		antecedentTxt.setFocusable(yes);
		antecedentTxt.setFocusableInTouchMode(yes);
		
		phoneNumTxt.setFocusable(yes);
		phoneNumTxt.setFocusableInTouchMode(yes);
		
		rentNumTxt.setFocusable(yes);
		rentNumTxt.setFocusableInTouchMode(yes);
		
		netMoneyTxt.setFocusable(yes);
		netMoneyTxt.setFocusableInTouchMode(yes);
		
		propertyTxt.setFocusable(yes);
		propertyTxt.setFocusableInTouchMode(yes);
		
		enterTimeTxt.setClickable(yes);
		
		notesTxt.setFocusable(yes);
		notesTxt.setFocusableInTouchMode(yes);
		
		if(yes)
		{
			roomIDTxt.requestFocus();
			this.setFocusable(true);
			this.setFocusableInTouchMode(true);
		}
	}

	
	/**
	 * 设置用户信息
	 * @param vo
	 */
	public void setUserInfoVo(UserInfoVo vo)
	{
		this.vo = vo;
		roomIDTxt.setText(vo.roomId + "");
		useNameTxt.setText(vo.userName + "");
		antecedentTxt.setText(vo.antecedent + "");
		phoneNumTxt.setText(vo.phoneNum + "");
		rentNumTxt.setText(vo.rentNum + "");
		netMoneyTxt.setText(vo.netMoney + "");
		propertyTxt.setText(vo.protery + "");
		enterTimeTxt.setText(vo.enterTime + "");
		notesTxt.setText(vo.notes);
		
		doChangeWaterPower();
	}

	private class BtnOnClickClass implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.comitBtn:
				comitDataInfo();
				break;
			case R.id.editorBtn:
                 editorInfo();
				break;
			case R.id.backBtn:
				backEditor();
				break;
			case R.id.powerNum:
				openWaterPowerInterface.openWaterPower(vo.roomId,vo.userName);
				SingleToolClass.backWaterPowerInterface = backWaterPowerClass;
				break;
			case R.id.waterNum:
				openWaterPowerInterface.openWaterPower(vo.roomId,vo.userName);
				SingleToolClass.backWaterPowerInterface = backWaterPowerClass;
				break;
			case R.id.enterTime:
				openDateCheckPanel();
				break;
			}
		}
	}
	
	/**
	 * 提交信息
	 */
	private void comitDataInfo()
	{
		backBtn.setVisibility(View.INVISIBLE);
		comitBtn.setVisibility(View.INVISIBLE);
		editorBtn.setVisibility(View.VISIBLE);
		
		vo.userName = useNameTxt.getText().toString();
		vo.antecedent = Integer.parseInt(antecedentTxt.getText().toString());
		vo.phoneNum = phoneNumTxt.getText().toString();
		vo.rentNum = Integer.parseInt(rentNumTxt.getText().toString());
		vo.netMoney = Integer.parseInt(netMoneyTxt.getText().toString());
		vo.protery = Integer.parseInt(propertyTxt.getText().toString());
		vo.enterTime = enterTimeTxt.getText().toString();
		vo.notes = notesTxt.getText().toString();
		
		HandlerUserInfoTable.updataTableData(vo);
		
		setCanEditor(false);
	}
	
	/**
	 * 编辑信息
	 */
	private void editorInfo()
	{
		backBtn.setVisibility(View.VISIBLE);
		comitBtn.setVisibility(View.VISIBLE);
		editorBtn.setVisibility(View.INVISIBLE);
		setCanEditor(true);
	}
	
	/**
	 * 退出编辑
	 */
	public void backEditor()
	{
		backBtn.setVisibility(View.INVISIBLE);
		comitBtn.setVisibility(View.INVISIBLE);
		editorBtn.setVisibility(View.VISIBLE);
		setCanEditor(false);
		
		setUserInfoVo(vo);
	}
	
	/**
	 * 选择日期
	 */
	private void openDateCheckPanel()
	{
		if(canEditor == false)
		{
			return;
		}
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext() , new DateSetListener(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	    datePickerDialog.show();
	}
	
	
	private class DateSetListener implements OnDateSetListener
	{
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) 
		{
			String monthString = (monthOfYear+1)+"";
			if(monthString.length()==1)
			{
				monthString = "0"+monthString;
			}
			
			String daysString = dayOfMonth+"";
			if(daysString.length() == 1)
			{
				daysString = "0"+daysString;
			}
			enterTimeTxt.setText(year+"/"+monthString+"/"+daysString);
			
			AlarmTool.setAlarmTime(getContext(), vo.roomId, vo.enterTime);
		}
	}
	

	
	private BackWaterPowerClass backWaterPowerClass = new BackWaterPowerClass();
	
	private class BackWaterPowerClass implements BackWaterPowerInterface
	{
		@Override
		public void backWaterPower() 
		{
		   doChangeWaterPower();	
		}
	}
	
	/**
	 * 每个月水电的使用量
	 * 上一个月减去这个月
	 */
	private void doChangeWaterPower()
	{
		ArrayList<WaterPowerVo> wpvos = HandlerWaterPowerTable.getDataFromTable(vo.roomId);
		if(wpvos.size()>=2)
		{
			WaterPowerVo wpvoBack = wpvos.get(wpvos.size()-2);
			WaterPowerVo wpvoNow = wpvos.get(wpvos.size()-1);
			waterNumTxt.setText(""+(wpvoNow.waterInfo - wpvoBack.waterInfo));
			powerNumTxt.setText(""+(wpvoNow.powerInfo - wpvoBack.powerInfo));
		}else
		{
			waterNumTxt.setText("0");
			powerNumTxt.setText("0");
		}
	}
	
	public boolean getCanEditor()
	{
		return canEditor;
	}
	
}
