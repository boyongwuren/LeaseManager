package com.bywr.lease.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

public class FileUtil
{

 
	/**
	 * 写文件
	 * @param filePath 文件的目录
	 * @param fileName 文件名称
	 * @param content 文件内容
	 */
	public static void writeFile(String filePath,String fileName,String content)
	 {
		 try 
		 {
			File file = new File(filePath);
			if(!file.exists())
			{
				file.mkdirs();
			}
			
			FileWriter fileWriter = new FileWriter(filePath+File.separator+fileName);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	 }
	

	/**
	 * 搜寻文件的路径
	 * @param fileName 文件名称
	 * @return
	 */
	public static String searchFilePath(String fileName)
	{
		String result = "";
		
		result = searchFile(Environment.getExternalStorageDirectory(), fileName);
		
		return result;
	}
	
	private static String searchFile(File file,String fileName)
	{
		String result = "";
		File[] files = file.listFiles();
		if(files != null)
		{
			int len = files.length;
			for (int i = 0; i < len; i++) 
			{
				File tempFile = files[i];
				if(tempFile.isDirectory())
				{
					result = searchFile(tempFile,fileName);
					if(!TextUtils.isEmpty(result))
					{
						break;
					}
				}else 
				{
					String tempFileName = tempFile.getName();
					if(tempFileName.equals(fileName))
					{
						//文件名称一样
						result = tempFile.getAbsolutePath();
						break;
					}
				}
			}
		}
		
		return result;
		
	}
	
	/**
	 * 读取文件
	 * @param fileName 文件所在位置的完整路径
	 * @return
	 */
	public static String readFile(String fileName)
	{
		String result = "";
		File file = new File(fileName);
		BufferedReader bufferedReader = null;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(file));
			String tempString = "";
			while((tempString = bufferedReader.readLine()) != null)
			{
				result += tempString;
			}
			
			bufferedReader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 截屏
	 */
	public static Bitmap screenCapture(Activity context)
	{
		Bitmap bitmap = null;
		View view = context.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);//设置是否进行绘图缓存
		view.buildDrawingCache();
		bitmap = view.getDrawingCache();
		
		//获取状态栏高度
		Rect frame = new Rect();
		view.getWindowVisibleDisplayFrame(frame);
		int stautsHeight = frame.top;
		
		int width = context.getWindowManager().getDefaultDisplay().getWidth();
		int height = context.getWindowManager().getDefaultDisplay().getHeight();
		
		bitmap = Bitmap.createBitmap(bitmap, 0, stautsHeight, width, height-stautsHeight);
		return bitmap;
	}
	
	/**
	 * 保存图片到sdcard中
	 * @param bitmap
	 * @param path 完整路径
	 */
	public static void saveBitmapToSdc(Bitmap bitmap,String path,String picName)
	{
		FileOutputStream fos;
		try 
		{
			File file = new File(path);
			if(!file.exists())
			{
				file.mkdirs();
			}
			fos = new FileOutputStream(path+File.separator+picName);
			if(fos != null)
			{
				bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
