package com.bywr.lease.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class LogTool {

	/**
	 * 显示提示
	 * @param text
	 */
	public static void showTip(String text)
	{
		Toast toast = Toast.makeText(SingleToolClass.context, text, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	/**
	 * 显示弹窗
	 * @param context
	 * @param message 弹窗内容
	 * @param positiveLabel 确定按钮的文字
	 * @param negativeLabel 取消按钮的文字
	 * @param positiveListener 确定按钮的监听
	 * @param negativeListener 取消按钮的监听
	 */
	public static void showAlertTip(Context context,String message,String positiveLabel,String negativeLabel,OnClickListener positiveListener,OnClickListener negativeListener)
	{
		Builder builder = new Builder(context);
		builder.setMessage(message);
		builder.setPositiveButton(positiveLabel, positiveListener);
		builder.setNegativeButton(negativeLabel, negativeListener);
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

}
