package com.bywr.lease.adapter;

import java.util.ArrayList;

import com.bywr.lease.view.UserInfoView;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPageAdapter extends PagerAdapter {

	private ArrayList<UserInfoView> pages;

	public ViewPageAdapter(ArrayList<UserInfoView> pages) {
		this.pages = pages;
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// 这个方法用来实例化页卡
		container.addView(pages.get(position), 0);// 添加页卡
		return pages.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		// 删除页卡
		container.removeView(pages.get(position));
	}

}
