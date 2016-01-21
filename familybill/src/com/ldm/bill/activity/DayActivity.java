package com.ldm.bill.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ldm.common.Constant;
import com.ldm.support.bean.CommonBean;
import com.ldm.support.db.DBTool;
import com.ldm.support.tool.DataTools;

/**
 *月度对应的每一天帐单数据列表
 * @description：
 * @author ldm
 * @date 2015-7-17 下午1:36:47
 */
public class DayActivity extends BillBaseActivity {
	private int type;
	private int month;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onitemClick(int postion) {
		Bundle bundle = new Bundle();
		bundle.putString(Constant.TIME, String.valueOf(billList.get(postion).getTime()));
		bundle.putInt(Constant.BILL_TYPE, type);
		Intent in = new Intent(this, BillDetailActivity.class);
		in.putExtras(bundle);
		startActivity(in);
	}

	@Override
	
	protected ArrayList<CommonBean> getBillData() {
		type = getIntent().getIntExtra(Constant.BILL_TYPE, Constant.INCOME_TYPE);
		month = getIntent().getIntExtra(Constant.MONTH, DataTools.getInstance().getNowMonth());
		Log.i("ldm", type+"");
		if (type == 1) {// 收入
			billList = new DBTool(this).findAllIncome("month", String.valueOf(month));
		}
		else if (type == 2) {//支出 
			billList = new DBTool(this).findAllExpend("month", String.valueOf(month));
		}
	
		return billList;
	}

	@Override
	protected int getDataType() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	protected int getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
