package com.bywr.lease.activity;

import java.io.File;
import java.util.ArrayList;

import com.bywr.lease.adapter.ViewPageAdapter;
import com.bywr.lease.constant.BundleConst;
import com.bywr.lease.constant.Contant;
import com.bywr.lease.handler.HandlerUserInfoTable;
import com.bywr.lease.handler.HandlerWaterPowerTable;
import com.bywr.lease.interfaces.OpenWaterPowerInterface;
import com.bywr.lease.utils.FileUtil;
import com.bywr.lease.utils.LogTool;
import com.bywr.lease.view.UserInfoView;
import com.bywr.lease.vo.UserInfoVo;
import com.bywr.lease.vo.WaterPowerVo;
import com.example.leasemanagerc.R;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

/**
 * 显示用户信息的Activity
 * 
 * @author Administrator
 * 
 */
public class ShowUserInfoActivity extends Activity {

	private ViewPager viewPage;

	private ArrayList<UserInfoView> pages = new ArrayList<UserInfoView>();

	private OpenWaterPowerClass openWaterPowerClass = new OpenWaterPowerClass();

	public ShowUserInfoActivity()
	{

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_user_info);
		viewPage = (ViewPager) findViewById(R.id.viewPage);
        viewPage.setOnPageChangeListener(new ViewPagerChangerListener());
		createViewPageInfo();
		
		this.startService(new Intent(this,MyService.class));
	}
	
	@Override
    protected void onDestroy() 
	{
        super.onDestroy();
        this.stopService(new Intent(this, MyService.class));
    }


	/**
	 * 创建10个页面 101 102 103 104 105 201 202 203 204 205
	 */
	private void createViewPageInfo()
	{
		for (int i = 0; i < Contant.ROOM_IDS.length; i++) 
		{
			UserInfoVo vo = HandlerUserInfoTable.getDataFromTable(Contant.ROOM_IDS[i]);
			UserInfoView userInfoView = new UserInfoView(this,openWaterPowerClass);
			userInfoView.setUserInfoVo(vo);
			pages.add(userInfoView);
		}
		viewPage.setAdapter(new ViewPageAdapter(pages));
	}

	/**
	 * 点击 打开水电界面
	 * 
	 * @author Administrator
	 * 
	 */
	private class OpenWaterPowerClass implements OpenWaterPowerInterface {

		@Override
		public void openWaterPower(int roomId, String userName) {
			Bundle bundle = new Bundle();
			bundle.putInt(BundleConst.ROOMID, roomId);
			bundle.putString(BundleConst.USERNAME, userName);

			Intent intent = new Intent(ShowUserInfoActivity.this,ShowWaterPowerActivity.class);
			intent.putExtras(bundle);
			ShowUserInfoActivity.this.startActivity(intent);
		}

	}

	private boolean isSureBack = false;
	
	@Override
	public void onBackPressed() 
	{
		if(isSureBack == false)
		{
			isSureBack = true;
			handler.postDelayed(runnable, 2000);//每两秒执行一次runnable.
			LogTool.showTip("再按一次退出");
		}else
		{
			handler.removeCallbacks(runnable);
			super.onBackPressed();
		}
	}
	
	private final Handler handler = new Handler() ;
	 
	Runnable runnable = new Runnable() 
	{
		@Override
		public void run() 
		{
			isSureBack = false;
		}
	};

	private boolean showAlert = true;
	private int oldIndex = 0;
	private class ViewPagerChangerListener implements OnPageChangeListener
	{
		@Override
		public void onPageScrollStateChanged(int arg0) 
		{
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) 
		{
		}

		@Override
		public void onPageSelected(int arg0) 
		{
			UserInfoView userInfoView = pages.get(oldIndex);
			if(userInfoView!=null&&userInfoView.getCanEditor()&&showAlert)
			{
				//可编辑状态下
				LogTool.showAlertTip(ShowUserInfoActivity.this, "是否放弃编辑", "是", "否", new PositiveListenerClass(arg0,userInfoView), new NegativeListenerClass());
				showAlert = false;
				viewPage.setCurrentItem(oldIndex);
			}else 
			{
				showAlert = true;
				oldIndex = arg0;
			}
		}
		
	}
	
	/**
	 * 弹窗确定按钮
	 * @author zmp
	 *
	 */
	private class PositiveListenerClass implements OnClickListener
	{
		private int curIndex;
		private UserInfoView oldUserInfoView;
		public PositiveListenerClass(int oldIndex,UserInfoView oldUserInfoView)
		{
			this.curIndex = oldIndex;
			this.oldUserInfoView = oldUserInfoView;
		}
		
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			oldIndex = curIndex;
			viewPage.setCurrentItem(curIndex);
			if(oldUserInfoView != null)
			{
				oldUserInfoView.backEditor();
			}
		}
		
	}

	private class NegativeListenerClass implements OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(0, Contant.MENU_EXPORT_DATA_ID, Menu.NONE, "导出数据");
		menu.add(0, Contant.MENU_IMPORT_DATA_ID, Menu.NONE, "导入数据");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		 if(item.getItemId() == Contant.MENU_EXPORT_DATA_ID)
		 {
			 //导出数据
			 exportData();
			 LogTool.showTip("导出数据成功");
		 }
		 
		 if(item.getItemId() == Contant.MENU_IMPORT_DATA_ID)
		 {
			 //导入数据
			 importData();
			 LogTool.showTip("导入成功");
		 }
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 导出数据
	 */
	private void exportData()
	{
		String exportString = HandlerUserInfoTable.getTabeleDatString();
		 FileUtil.writeFile(Contant.APP_DICTROY_DB, Contant.EXPORT_USERINFO_FILENAME, exportString);
		
		 String exportWaterPowerString = HandlerWaterPowerTable.getAllDataString();
		 FileUtil.writeFile(Contant.APP_DICTROY_DB, Contant.EXPORT_WATERPOWER_FILENAME, exportWaterPowerString);
		 
	}
	
	/**
	 * 导入数据
	 */
	private void importData()
	{
		importUserInfoData();
		importWaterPowerData();
	}
	
	/**
	 * 导入用户数据
	 */
	
	private void importUserInfoData()
	{
		try 
		{
			String filePathString = FileUtil.searchFilePath(Contant.EXPORT_USERINFO_FILENAME);
			 if(!TextUtils.isEmpty(filePathString))
			 {
				 String contentString = FileUtil.readFile(filePathString);
				 String[] vosStrings = contentString.split(Contant.EACH_CUR_VO);
				 ArrayList<UserInfoVo> uVos = new ArrayList<UserInfoVo>();
				 for (int i = 0; i < vosStrings.length; i++) 
				 {
					String vosString = vosStrings[i];
					String[] proprtyStrings = vosString.split(Contant.EACH_CUT_LINE);
					
					UserInfoVo vo = new UserInfoVo();
					vo.roomId = Integer.parseInt(proprtyStrings[0]);
					vo.userName = proprtyStrings[1];
					vo.antecedent = Integer.parseInt(proprtyStrings[2]);
					vo.phoneNum = proprtyStrings[3];
					vo.rentNum = Integer.parseInt(proprtyStrings[4]);
					vo.netMoney = Integer.parseInt(proprtyStrings[5]);
					vo.protery = Integer.parseInt(proprtyStrings[6]);
					vo.enterTime = proprtyStrings[7];
					vo.notes = proprtyStrings[8];
					
					uVos.add(vo);
					
					UserInfoView userInfoView = pages.get(i);
					userInfoView.setUserInfoVo(vo);
				 }
				 
				 if(uVos.size()>0)
				 {
					 HandlerUserInfoTable.clearTable();
					 HandlerUserInfoTable.insertDataToTable(uVos);
				 }
			 }
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	

	/**
	 * 导入水电数据
	 */
	private void importWaterPowerData()
	{
		try 
		{
			String filePathString = FileUtil.searchFilePath(Contant.EXPORT_WATERPOWER_FILENAME);
			if(!TextUtils.isEmpty(filePathString))
			{
				String contentString = FileUtil.readFile(filePathString);
				String[] vosStrings = contentString.split(Contant.EACH_CUR_VO);
				ArrayList<WaterPowerVo> wpvos = new ArrayList<WaterPowerVo>();
				for (int i = 0; i < vosStrings.length; i++) 
				{
					String vosString = vosStrings[i];
					String[] proprtyStrings = vosString.split(Contant.EACH_CUT_LINE);
					
					WaterPowerVo vo = new WaterPowerVo();
					vo.roomId = Integer.parseInt(proprtyStrings[0]);
					vo.dataInfo = proprtyStrings[1];
//					vo.waterInfo = Integer.parseInt(proprtyStrings[2]);
//					vo.powerInfo = Integer.parseInt(proprtyStrings[3]);
					vo.waterInfo =  (int)(Float.parseFloat(proprtyStrings[2])+0.5);
					vo.powerInfo = (int)(Float.parseFloat(proprtyStrings[3])+0.5);
					wpvos.add(vo);
				}
				
				if(wpvos.size()>0)
				{
					HandlerWaterPowerTable.clearTable();
					HandlerWaterPowerTable.insertDataToTable(wpvos);
				}
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
 
}
