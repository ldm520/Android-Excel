package com.ldm.support.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.ldm.common.Constant;
import com.ldm.support.bean.CommonBean;
import com.ldm.support.bean.ExpendsBean;
import com.ldm.support.bean.IncomeBean;

/**
 * 项目数据操作工具类（后续会统一用GreenDao）
 * @description：
 * @author ldm
 * @date 2015-7-16 下午5:39:54
 */
public class DBTool {
	private DBHelper dbHelper;
	private Context context;

	public DBTool(Context context) {
		this.context = context;
		dbHelper = new DBHelper(context);
		dbHelper.open();
	}

	/**
	 * 按照条件查询支出帐单
	 * @description：
	 * @author ldm
	 * @date 2015-7-24 下午3:28:41
	 */
	public ArrayList<CommonBean> findAllExpend(String column, String sql) {
		Cursor mCrusor;
		ArrayList<CommonBean> expendList = new ArrayList<CommonBean>();
		if (TextUtils.isEmpty(column) || TextUtils.isEmpty(sql)) {
			mCrusor = dbHelper.exeSql("select * from expend_bill");
		}
		else {
			mCrusor = dbHelper.exeSql("select * from expend_bill where " + column + "=" + "'" + sql + "'");
		}
		while (mCrusor.moveToNext()) {
			CommonBean bean = new CommonBean();
			bean.setYear(mCrusor.getString(1));
			bean.setMonth(mCrusor.getString(2));
			bean.setTime(mCrusor.getLong(3));
			bean.setTotal(mCrusor.getString(15));
			expendList.add(bean);
		}
		mCrusor.close();
		return expendList;
	}

	/**
	 * 按条件查询收入收单
	 * @description：
	 * @author ldm
	 * @date 2015-7-24 下午3:29:01
	 */
	public ArrayList<CommonBean> findAllIncome(String column, String sql) {
		Cursor mCrusor;
		ArrayList<CommonBean> expendList = new ArrayList<CommonBean>();
		if (TextUtils.isEmpty(column) || TextUtils.isEmpty(sql)) {
			mCrusor = dbHelper.exeSql("select * from income_bill");
		}
		else {
			mCrusor = dbHelper.exeSql("select * from income_bill where " + column + "=" + "'" + sql + "'");
		}
		while (mCrusor.moveToNext()) {
			//id, year, month, time, salary, prize, manager, invest, other, remark, total]
			CommonBean bean = new CommonBean();
			bean.setYear(mCrusor.getString(1));
			bean.setMonth(mCrusor.getString(2));
			bean.setTime(mCrusor.getLong(3));
			bean.setTotal(mCrusor.getString(10));
			expendList.add(bean);
		}
		mCrusor.close();
		return expendList;
	}

	/**
	 * 计算某一段时间的收入总帐单
	 * @description：
	 * @author ldm
	 * @date 2015-7-22 下午3:00:00
	 */
	public double calIncomeBill(String colum, String sql) {
		double result = 0;
		ArrayList<String> expendList = new ArrayList<String>();
		Cursor mCrusor = dbHelper.exeSql("select total from income_bill where " + colum + "=" + "'" + sql + "'");
		while (mCrusor.moveToNext()) {
			expendList.add(mCrusor.getString(0));
		}
		mCrusor.close();

		if (expendList.size() > 0) {
			for (String string : expendList) {
				if (!TextUtils.isEmpty(string)) {
					result += Double.valueOf(string);
				}
			}
		}
		return result;
	}

	/**
	 * 计算某一段时间的支出总帐单
	 * @description：
	 * @author ldm
	 * @date 2015-7-22 下午3:00:00
	 */
	public double calExpendBill(String colum, String sql) {
		double result = 0;
		ArrayList<String> expendList = new ArrayList<String>();
		Cursor mCrusor = dbHelper.exeSql("select total from expend_bill where " + colum + "=" + "'" + sql + "'");
		while (mCrusor.moveToNext()) {
			expendList.add(mCrusor.getString(0));
		}
		mCrusor.close();

		if (expendList.size() > 0) {
			for (String string : expendList) {
				if (!TextUtils.isEmpty(string)) {
					result += Double.valueOf(string);
				}
			}
		}
		return result;
	}

	/**
	 * 查询年份数据
	 * @description：
	 * @author ldm
	 * @date 2015-7-27 下午4:34:18
	 */
	public ArrayList<CommonBean> findYearData(int type) {
		ArrayList<CommonBean> billList = new ArrayList<CommonBean>();
		ArrayList<CommonBean> tempList = new ArrayList<CommonBean>();
		if (type == Constant.INCOME_TYPE) {
			billList = findAllIncome("", "");
		}
		else {
			billList = findAllExpend("", "");
		}
		String yearFirst = "";
		for (CommonBean bean : billList) {
			if (yearFirst.equals(bean.getYear())) {
				bean.setIsFirst(0);
			}
			else {
				bean.setIsFirst(1);
				yearFirst = bean.getYear();
			}
		}
		for (int i = 0; i < billList.size(); i++) {
			if (billList.get(i).getIsFirst() == 1) {
				tempList.add(billList.get(i));
			}
		}
		return tempList;
	}

	/**
	 * 查询年度月份数据
	 * @description：
	 * @author ldm
	 * @date 2015-7-27 下午4:34:18
	 */
	public ArrayList<CommonBean> findMonthData(int type,String year) {
		ArrayList<CommonBean> billList = new ArrayList<CommonBean>();
		ArrayList<CommonBean> tempList = new ArrayList<CommonBean>();
		
		
		if (type == Constant.INCOME_TYPE) {
			billList = findAllIncome(Constant.YEAR, year);
		}
		else {
			billList = findAllExpend(Constant.YEAR, year);
		}
		String yearFirst = "";
		for (CommonBean bean : billList) {
			if (yearFirst.equals(bean.getMonth())) {
				bean.setIsFirst(0);
			}
			else {
				bean.setIsFirst(1);
				yearFirst = bean.getMonth();
			}
		}
		for (int i = 0; i < billList.size(); i++) {
			if (billList.get(i).getIsFirst() == 1) {
				if (type == Constant.INCOME_TYPE) {
					billList.get(i).setTotal(String.valueOf(calIncomeBill("month",String.valueOf(billList.get(i).getMonth()))));

				}
				else {
					billList.get(i).setTotal(String.valueOf(calExpendBill("month",String.valueOf(billList.get(i).getMonth()))));
				}
				tempList.add(billList.get(i));
			}
		}
		return tempList;
	}

	/**
	 * 根据年月查询当月的收入
	 * @description：
	 * @author ldm
	 * @date 2015-7-27 下午4:43:54
	 */
	public ArrayList<CommonBean> findIncomeMonth(String year, String month) {
		ArrayList<CommonBean> monthList = new ArrayList<CommonBean>();
		String sql = "select * from income_bill where year=" + year + "and month=" + month;
		Cursor mCrusor = dbHelper.exeSql(sql);
		while (mCrusor.moveToNext()) {
			CommonBean bean = new CommonBean();
			bean.setYear(mCrusor.getString(1));
			bean.setMonth(mCrusor.getString(2));
			bean.setTime(mCrusor.getLong(3));
			bean.setTotal(mCrusor.getString(10));
			monthList.add(bean);
		}
		mCrusor.close();
		return monthList;
	}

	/**
	 * 根据年月查询当月的支出
	 * @description：
	 * @author ldm
	 * @date 2015-7-27 下午4:43:54
	 */
	public ArrayList<CommonBean> findExpendMonth(String year, String month) {
		ArrayList<CommonBean> monthList = new ArrayList<CommonBean>();
		String sql = "select * from expend_bill where year=" + year + "and month=" + month;
		Cursor mCrusor = dbHelper.exeSql(sql);
		while (mCrusor.moveToNext()) {
			CommonBean bean = new CommonBean();
			bean.setYear(mCrusor.getString(1));
			bean.setMonth(mCrusor.getString(2));
			bean.setTime(mCrusor.getLong(3));
			bean.setTotal(mCrusor.getString(15));
			monthList.add(bean);
		}
		mCrusor.close();
		return monthList;
	}

	/**
	 * 获取某一天的收入帐单数据
	 * @description：
	 * @author ldm
	 * @date 2015-7-27 下午5:32:28
	 */
	public ArrayList<IncomeBean> findOneDayIncome(String time) {
		ArrayList<IncomeBean> list = new ArrayList<IncomeBean>();
		String sql = "select * from income_bill where time=" + time;
		Cursor cursor = dbHelper.exeSql(sql);
		while (cursor.moveToNext()) {
			IncomeBean bean = new IncomeBean();
			bean.setYear(cursor.getString(1));
			bean.setMonth(cursor.getString(2));
			bean.setTime(cursor.getLong(3));
			bean.setSalary(cursor.getString(4));
			bean.setPrize(cursor.getString(5));
			bean.setManager(cursor.getString(6));
			bean.setInvest(cursor.getString(7));
			bean.setOther(cursor.getString(8));
			bean.setRemark(cursor.getString(9));
			bean.setTotal(cursor.getString(10));
			list.add(bean);
		}
		return list;
	}

	/**
	 * 获取某一天的收入帐单数据
	 * @description：
	 * @author ldm
	 * @date 2015-7-27 下午5:32:28
	 */
	public ArrayList<ExpendsBean> findOneDayExpend(String time) {
		ArrayList<ExpendsBean> list = new ArrayList<ExpendsBean>();
		String sql = "select * from expend_bill where time=" + time;
		Cursor cursor = dbHelper.exeSql(sql);
		while (cursor.moveToNext()) {
			//year, month, time, salary, prize, manager, invest, other, remark, total]
			ExpendsBean bean = new ExpendsBean();
			bean.setYear(cursor.getString(1));
			bean.setMonth(cursor.getString(2));
			bean.setTime(cursor.getLong(3));
			bean.setFood(cursor.getString(4));
			bean.setLive(cursor.getString(5));
			bean.setUse(cursor.getString(6));
			bean.setClothes(cursor.getString(7));
			bean.setTraffic(cursor.getString(8));
			bean.setDoctor(cursor.getString(9));
			bean.setLaiWang(cursor.getString(10));
			bean.setEducation(cursor.getString(11));
			bean.setTravel(cursor.getString(12));
			bean.setOther(cursor.getString(13));
			bean.setRemark(cursor.getString(14));
			bean.setTotal(cursor.getString(5));
			list.add(bean);
		}
		return list;
	}
}
