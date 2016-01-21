package com.ldm.bill.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

import com.ldm.common.Constant;
import com.ldm.support.bean.CommonBean;
import com.ldm.support.db.DBTool;

/**
 * 年度帐单信息
 * @description：
 * @author ldm
 * @date 2015-7-22 下午4:41:28
 */
public class YearBillActivity extends BillBaseActivity {
	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onitemClick(int postion) {
		Bundle bundle = new Bundle();
		bundle.putString(Constant.YEAR, billList.get(postion).getYear());
		Intent in = new Intent(this, MonthBillActivity.class);
		in.putExtras(bundle);
		startActivity(in);
	}

	@Override
	protected ArrayList<CommonBean> getBillData() {
		type = getIntent().getIntExtra(Constant.BILL_TYPE, Constant.INCOME_TYPE);
		billList = new DBTool(this).findYearData(type);
		return billList;
	}

	@Override
	protected int getDataType() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	protected int getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
