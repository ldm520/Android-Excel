package com.ldm.support.tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

import com.ldm.support.bean.IncomeBean;

/**
 * 项目的一些通用处理方法
 * @description：
 * @author ldm
 * @date 2015-7-22 下午2:49:49
 */
public class DataTools {
	private Calendar mCalendar;
	private SimpleDateFormat mDateFormat;
	private static DataTools mTools=null;
	@SuppressLint("SimpleDateFormat")
	private  DataTools() {
		mCalendar = Calendar.getInstance();
		mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	public static DataTools getInstance(){
		if(mTools==null){
			mTools=new DataTools();
		}
		return mTools;
	}
	/* 获取当前年份* */
	public int getNowYear() {
		return mCalendar.get(Calendar.YEAR);
	}

	/* 获取当前月份* */
	public int getNowMonth() {
		return mCalendar.get(Calendar.MONTH)+1;
	}

	/* 获取当前日期* */
	public String getNowDay(long time) {
		return mDateFormat.format(new Date(time));
	}

	/* 获取当前日期Long* */
	public Long getNowSeconds() {
		return new Date().getTime();
	}
/**
 * 对数据进行月份整理
 * @description：
 * @author ldm
 * @date 2015-7-22 下午5:01:44
 */
	private ArrayList<IncomeBean> iteratorData(ArrayList<IncomeBean> list) {
		String firstBill = "";
		ArrayList<IncomeBean> newList = new ArrayList<IncomeBean>();
		for (IncomeBean bean : list) {
			if (firstBill.equals(bean.getMonth())) {
				bean.setIsFirstDay(0);
			}
			else {
				bean.setIsFirstDay(1);
			}
			newList.add(bean);
		}
		return newList;
	}
}
