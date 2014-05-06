package com.bywr.lease.activity;

import java.util.ArrayList;

import com.bywr.lease.constant.BundleConst;
import com.bywr.lease.constant.Contant;
import com.bywr.lease.handler.HandlerWaterPowerTable;
import com.bywr.lease.interfaces.RecognizeSpeechInterface;
import com.bywr.lease.utils.SingleToolClass;
import com.bywr.lease.view.AddWaterPowerView;
import com.bywr.lease.view.WaterPowerItem;
import com.bywr.lease.vo.WaterPowerVo;
import com.example.leasemanagerc.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 水电信息界面
 * 
 * @author Administrator
 * 
 */
public class ShowWaterPowerActivity extends Activity  implements RecognizeSpeechInterface
{

	private TextView roomNumTxt;

	private TextView userNameTxt;

	private LinearLayout monthContent;

	private RelativeLayout addWaterPowerContent;

	private BtnOnClickClass btnOnClickClass = new BtnOnClickClass();

	private ArrayList<WaterPowerVo> wpvos;

	private int roomId;

	private String userName;

	public ShowWaterPowerActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.water_power_info);

		roomNumTxt = (TextView) findViewById(R.id.roomNum);
		userNameTxt = (TextView) findViewById(R.id.userName);
		monthContent = (LinearLayout) findViewById(R.id.monthContent);
		addWaterPowerContent = (RelativeLayout) findViewById(R.id.addWaterPowerContent);

		Bundle bundle = getIntent().getExtras();
		roomId = bundle.getInt(BundleConst.ROOMID);
		userName = bundle.getString(BundleConst.USERNAME);

		roomNumTxt.setText(roomId + "");
		userNameTxt.setText(userName);

		this.wpvos = HandlerWaterPowerTable.getDataFromTable(roomId);
		setWaterPowerVos(this.wpvos);
	}

	/**
	 * 按钮被点击
	 * 
	 * @author Administrator
	 * 
	 */
	private class BtnOnClickClass implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.comitBtn:
				// 添加水电视图的确定按钮被点击
				sureAddOneWaterPowerItem();
				break;
			case R.id.cancelBtn:
				// 添加水电视图的取消按钮被点击
				addWaterPowerContent.removeAllViews();
				addWaterPowerView = null;
				break;
			}
		}
	}

	/**
	 * 添加一个月水电的视图
	 */
	private AddWaterPowerView addWaterPowerView;

	/***
	 * 单击了添加一个月水电的按钮 显示添加水电的视图
	 */
	private void addOneWaterPowerItem() {
		addWaterPowerView = new AddWaterPowerView(this,this);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		addWaterPowerContent.addView(addWaterPowerView, lp);
		addWaterPowerView.comitBtn.setOnClickListener(btnOnClickClass);
		addWaterPowerView.cancelBtn.setOnClickListener(btnOnClickClass);
	}

	/**
	 * 确定添加一个月水电
	 */
	private void sureAddOneWaterPowerItem() {
		addWaterPowerContent.removeAllViews();
		String date = addWaterPowerView.getMonth();
		int waterNum = addWaterPowerView.getWaterNum();
		int powerNum = addWaterPowerView.getPowerNum();
		addWaterPowerView = null;

		doAddOneWaterPowerItem(date, waterNum, powerNum);

		HandlerWaterPowerTable.insertDataToTable(roomId, date, waterNum,powerNum);

		WaterPowerVo vo = new WaterPowerVo();
		vo.roomId = roomId;
		vo.dataInfo = date;
		vo.waterInfo = waterNum;
		vo.powerInfo = powerNum;
		this.wpvos.add(vo);
	}

	/**
	 * 执行添加一个月的水电
	 * 
	 * @param date
	 * @param waterNum
	 * @param powerNum
	 */
	private void doAddOneWaterPowerItem(String date, int waterNum, int powerNum) 
	{
		WaterPowerItem waterPowerItem = new WaterPowerItem(this);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 10);
		monthContent.addView(waterPowerItem, lp);

		waterPowerItem.setMonthTxt(date);
		waterPowerItem.setWaterTxt(waterNum);
		waterPowerItem.setPowerTxt(powerNum);
	}

	/**
	 * 设置水电信息
	 * 
	 * @param wpvos
	 */
	public void setWaterPowerVos(ArrayList<WaterPowerVo> wpvos) {
		this.wpvos = wpvos;
		for (int i = 0; i < wpvos.size(); i++) {
			WaterPowerVo vo = this.wpvos.get(i);
			doAddOneWaterPowerItem(vo.dataInfo, vo.waterInfo, vo.powerInfo);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, Contant.MENU_ADD_WATERPOWER, 1, "添加水电");
		menu.add(1, Contant.MENU_CACULATE, 1, "结算费用");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) 
		{
			case Contant.MENU_ADD_WATERPOWER:
				addOneWaterPowerItem();
				break;
				
			case Contant.MENU_CACULATE:
				Intent intent = new Intent(this,CacuateInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt(BundleConst.ROOMID, roomId);
				intent.putExtras(bundle);
				this.startActivity(intent);
				break;
		}

		return true;
	}
	
	@Override
	public void finish() 
	{
		super.finish();
		
		if(SingleToolClass.backWaterPowerInterface != null)
		{
			SingleToolClass.backWaterPowerInterface.backWaterPower();
		}
	}

	private TextView speechTextView;
	@Override
	public void setInputTxt(TextView textView) 
	{
		 speechTextView = textView;
		 try
		 {
			 Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			 intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");  
			 startActivityForResult(intent, 0);
		} catch (Exception e) 
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,   Uri.parse("https://market.android.com/details?id=com.example.leasemanager"));
			startActivity(browserIntent);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0&&resultCode == RESULT_OK&&data!=null)
		{
			ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);  
			speechTextView.setText(text.get(0));
		}
	}

}
