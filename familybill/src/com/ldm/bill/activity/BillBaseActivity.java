package com.ldm.bill.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ldm.bill.adapter.BillAdapter;
import com.ldm.common.CommonTitleHelper;
import com.ldm.familybill.R;
import com.ldm.support.bean.CommonBean;

/**
 * 帐单列表基类
 * @description：
 * @author ldm
 * @date 2015-7-27 下午5:16:42
 */
public abstract class BillBaseActivity extends Activity {
	private CommonTitleHelper mTitleHelper;
	protected ListView bill_lv;
	protected BillAdapter mAdapter;
	protected ArrayList<CommonBean> billList;
	private View emptyView;
	protected int type;
	protected int dataType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_list);
		mTitleHelper = new CommonTitleHelper(this,-1,R.string.family_bill_email, R.string.family_bill_record);
		bill_lv = (ListView) findViewById(R.id.bill_listview);
		emptyView = findViewById(R.id.no_data_view);
		initListeners();
		billList = getBillData();
		dataType = getDataType();
		type = getType();
		if (billList.size() > 0) {
			bill_lv.setVisibility(View.VISIBLE);
			emptyView.setVisibility(View.GONE);
			mAdapter = new BillAdapter(this, billList, type, dataType);
			bill_lv.setAdapter(mAdapter);
		}
		else {
			bill_lv.setVisibility(View.GONE);
			emptyView.setVisibility(View.VISIBLE);
		}
	}

	protected abstract int getDataType();

	private void initListeners() {
		mTitleHelper.setOnLeftClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mTitleHelper.setOnRightClickListener(new OnClickListener() {//通过发邮件导出帐单
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(BillBaseActivity.this, ExportBill2EmailActivity.class);
				startActivity(intent);
			}
		});
		bill_lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onitemClick(position);
			}
		});
	}

	protected abstract ArrayList<CommonBean> getBillData();

	protected abstract void onitemClick(int postion);

	protected abstract int getType();
}
