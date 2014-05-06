package com.bywr.lease.activity;

import java.util.ArrayList;
import java.util.Calendar;

import com.bywr.lease.constant.BundleConst;
import com.bywr.lease.constant.Contant;
import com.bywr.lease.handler.HandlerUserInfoTable;
import com.bywr.lease.handler.HandlerWaterPowerTable;
import com.bywr.lease.utils.FileUtil;
import com.bywr.lease.vo.UserInfoVo;
import com.bywr.lease.vo.WaterPowerVo;
import com.example.leasemanagerc.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 结算界面
 * @author zmp
 *
 */
public class CacuateInfoActivity extends Activity 
{
	private TextView mouthTxt;
	private TextView roomIdTxt;
	private TextView userNameTxt;
	private TextView baseFormula;
	private TextView frontWater;
	private TextView frontPower;
	private TextView changeFormula;
	
	private int roomId;
	
	private UserInfoVo uVo;
	
	private ArrayList<WaterPowerVo> wpvos;
	
	private final String PLUSSIGN = "+";
	private final String EQUALSIGN = "=";
	private final String DELECTSIGN = "-";
	private final String MULTIPLYSIGN = "*";
	
	private int month;

	private String msgContent = "";
	public CacuateInfoActivity() 
	{
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caculate_info);
		mouthTxt = (TextView) findViewById(R.id.mouthId);
		roomIdTxt = (TextView) findViewById(R.id.roomIdTxt);
		userNameTxt = (TextView) findViewById(R.id.userNameTxt);
		baseFormula = (TextView) findViewById(R.id.baseFormula);
		frontWater = (TextView) findViewById(R.id.frontWater);
		frontPower = (TextView) findViewById(R.id.frontPower);
		changeFormula = (TextView) findViewById(R.id.changeFormula);
		
		Calendar calendar = Calendar.getInstance();
		month = calendar.get(Calendar.MONTH)+1;
		
		mouthTxt.setText(month+"月份");
		
		roomId = getIntent().getExtras().getInt(BundleConst.ROOMID);
		
		uVo = HandlerUserInfoTable.getDataFromTable(roomId);
		
		wpvos = HandlerWaterPowerTable.getDataFromTable(roomId);
		
		roomIdTxt.setText(roomId+"");
		userNameTxt.setText(uVo.userName);
		
		int baseMoney = uVo.rentNum+uVo.netMoney+uVo.protery;//基础费用总和
		baseFormula.setText(uVo.rentNum+PLUSSIGN+uVo.netMoney+PLUSSIGN+uVo.protery+EQUALSIGN+baseMoney);
		
		int frontWaterNum = 0;
		int frontPowerNum= 0;
		
		int curWaterNum = 0;
		int curPowerNum = 0;
		
		int incWaterNum = 0;
		int incPowerNum = 0;
		
		if(wpvos.size() == 0)
		{
			
		}else if(wpvos.size() == 1)
		{
			WaterPowerVo wpvoVo = wpvos.get(0);
			curWaterNum = wpvoVo.waterInfo;
			curPowerNum = wpvoVo.powerInfo;
		}else 
		{
			WaterPowerVo frontWpvo = wpvos.get(wpvos.size()-2);
			WaterPowerVo curWpvo = wpvos.get(wpvos.size()-1);
			
			frontWaterNum = frontWpvo.waterInfo;
			curWaterNum = curWpvo.waterInfo;
			
			frontPowerNum = frontWpvo.powerInfo;
			curPowerNum = curWpvo.powerInfo;
		}
		
		incWaterNum = curWaterNum - frontWaterNum;
		incPowerNum = curPowerNum - frontPowerNum;
		
		frontWater.setText(curWaterNum+DELECTSIGN+frontWaterNum+EQUALSIGN+incWaterNum);
		frontPower.setText(curPowerNum+DELECTSIGN+frontPowerNum+EQUALSIGN+incPowerNum);
		
		int waterMoney = incWaterNum*4;
		int powerMoney = (int) (incPowerNum*0.8+0.5);
		changeFormula.setText(incWaterNum+MULTIPLYSIGN+4+PLUSSIGN+incPowerNum+MULTIPLYSIGN+0.8+EQUALSIGN+waterMoney+PLUSSIGN+powerMoney+EQUALSIGN+(waterMoney+powerMoney));
		msgContent = "水:"+curWaterNum+"电:"+curPowerNum;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(0, Contant.MENU_SCREENCAPTURE, Menu.NONE, "截屏");
		menu.add(0, Contant.MENU_SENDMSG, Menu.NONE, "短信通知");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if(item.getItemId() == Contant.MENU_SCREENCAPTURE)
		{
			//截屏
			Bitmap bitmap = FileUtil.screenCapture(this);
			FileUtil.saveBitmapToSdc(bitmap, Contant.APP_DICTROY_PIC,roomId+"_"+uVo.userName+"_"+month+".png");
		}else if(item.getItemId() == Contant.MENU_SENDMSG)
		{
			if(PhoneNumberUtils.isGlobalPhoneNumber(uVo.phoneNum))
			{
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+uVo.phoneNum));
			    intent.putExtra("sms_body", msgContent);
			    startActivity(intent);
			}
		}
		return super.onOptionsItemSelected(item);
	}

}
