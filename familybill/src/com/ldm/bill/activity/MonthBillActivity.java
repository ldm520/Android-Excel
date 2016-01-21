package com.ldm.bill.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

import com.ldm.common.Constant;
import com.ldm.support.bean.CommonBean;
import com.ldm.support.db.DBTool;
import com.ldm.support.tool.DataTools;
/**
 * 年度月份数据列表
 * @description：
 * @author ldm
 * @date 2015-7-29 下午3:04:03
 */
public class MonthBillActivity extends BillBaseActivity {
	private int year;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onitemClick(int postion) {
		Bundle bundle = new Bundle();
		bundle.putString(Constant.MONTH, billList.get(postion).getMonth());
		Intent in = new Intent(this, DayActivity.class);
		in.putExtras(bundle);
		startActivity(in);
	}

	@Override
	protected ArrayList<CommonBean> getBillData() {
		year=getIntent().getIntExtra(Constant.YEAR, DataTools.getInstance().getNowYear());
		type = getIntent().getIntExtra(Constant.BILL_TYPE, Constant.INCOME_TYPE);
		billList = new DBTool(this).findMonthData(type,String.valueOf(year));
		return billList;
	}

	@Override
	protected int getDataType() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	protected int getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
