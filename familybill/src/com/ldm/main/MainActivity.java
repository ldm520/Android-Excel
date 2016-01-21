package com.ldm.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ldm.bill.activity.DayActivity;
import com.ldm.bill.activity.MonthBillActivity;
import com.ldm.bill.activity.YearBillActivity;
import com.ldm.common.CommonTitleHelper;
import com.ldm.common.Constant;
import com.ldm.expend.AddBillActivity;
import com.ldm.familybill.R;
import com.ldm.support.bean.CommonBean;
import com.ldm.support.db.DBTool;
import com.ldm.support.tool.DataTools;
import com.ldm.support.view.ActionPopupItem;
import com.ldm.support.view.TitlePopup;

/**
 * 主界面
 * @description：
 * @author ldm
 * @date 2015-7-27 下午2:08:57
 */
public class MainActivity extends Activity implements OnClickListener, TitlePopup.OnItemOnClickListener {
	private CommonTitleHelper mTitleHelper;
	private TextView timeTv;// 时间展示
	private TextView incomeTv;// 收入
	private TextView expendTv;// 支出
	private RelativeLayout incomeDetailTv;// 查看收入详情
	private RelativeLayout expendDetailTv;// 查看支出详情
	private TextView addBillTv;// 记一笔
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String month;
	private TitlePopup titlePopup;
	private String[] topSpiners = { "收入收单", "支出帐单" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		month = String.valueOf(DataTools.getInstance().getNowMonth());
		initViews();
		initListeners();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUiDatas();

	}

	private void initListeners() {
		this.incomeDetailTv.setOnClickListener(this);
		this.expendDetailTv.setOnClickListener(this);
		this.addBillTv.setOnClickListener(this);
	}

	private void initViews() {
		mTitleHelper = new CommonTitleHelper(this, -1, R.string.family_all_bill, R.string.hello_world);
		mTitleHelper.setOnRightClickListener(this);
		this.timeTv = (TextView) findViewById(R.id.main_time_tv);
		this.incomeTv = (TextView) findViewById(R.id.main_income_content_tv);
		this.incomeDetailTv = (RelativeLayout) findViewById(R.id.main_income_detail_tv);
		this.expendTv = (TextView) findViewById(R.id.main_expend_content_tv);
		this.expendDetailTv = (RelativeLayout) findViewById(R.id.main_expend_detail_tv);
		this.addBillTv = (TextView) findViewById(R.id.main_add_tv);
		titlePopup = new TitlePopup(MainActivity.this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, this);
		titlePopup.addAction(new ActionPopupItem(topSpiners[0], false));
		titlePopup.addAction(new ActionPopupItem(topSpiners[1], false));
		titlePopup.setUIStyle(2);
	}

	private void setUiDatas() {
		timeTv.setText(sdf.format(new Date()));
		this.incomeTv.setText("￥" + String.valueOf(new DBTool(this).calIncomeBill("month", month)));
		this.expendTv.setText("￥" + String.valueOf(new DBTool(this).calExpendBill("month", month)));
	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		switch (v.getId()) {
			case R.id.main_income_detail_tv:// 本月收入帐单
				bundle.clear();
				bundle.putInt(Constant.BILL_TYPE, Constant.INCOME_TYPE);
				bundle.putInt(Constant.MONTH, DataTools.getInstance().getNowMonth());
				Intent mIntent = new Intent(this, DayActivity.class);
				mIntent.putExtras(bundle);
				startActivity(mIntent);
				break;
			case R.id.main_expend_detail_tv:// 本月支出帐单
				bundle.clear();
				bundle.putInt(Constant.BILL_TYPE, Constant.EXPEND_TYPE);
				bundle.putInt(Constant.MONTH, DataTools.getInstance().getNowMonth());
				Intent tet = new Intent(this, DayActivity.class);
				tet.putExtras(bundle);
				startActivity(tet);
				break;
			case R.id.main_add_tv:// 记一笔
				startActivity(new Intent(this, AddBillActivity.class));
				break;
			case R.id.right:// 跳转到总帐单中
				titlePopup.show(v);
				break;
		}
	}

	/**
	 * 根据数据库情况判断是要跳转到哪个页面（如果记录大于1年则进入年份帐单页面，否则直接进入月份帐单页面）
	 * @description：
	 * @author ldm
	 * @date 2015-7-27 下午2:14:36
	 */
	private boolean jumpToWhere(int type) {
		int temp = 0;
		ArrayList<CommonBean> billList = null;
		if (type == Constant.INCOME_TYPE) {
			billList = new DBTool(this).findAllIncome("", "");
		}
		else {
			billList = new DBTool(this).findAllExpend("", "");
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
				temp++;
			}
		}
		if (temp > 1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void onItemClick(com.ldm.support.view.ActionPopupItem item, int position) {
		Bundle bundle = new Bundle();
		Intent incomeIntent = null;
		for (int i = 0; i < titlePopup.getActionSize(); i++)
			titlePopup.getAction(i).mItemSelected = false;
		if (item.mTitle.equals(topSpiners[0])) {
			bundle.putInt(Constant.BILL_TYPE, Constant.INCOME_TYPE);
			if (jumpToWhere(Constant.INCOME_TYPE)) {
				incomeIntent = new Intent(this, YearBillActivity.class);
			}
			else {
				incomeIntent = new Intent(this, MonthBillActivity.class);
			}
			incomeIntent.putExtras(bundle);
			startActivity(incomeIntent);
		}
		else if (item.mTitle.equals(topSpiners[1])) {
			bundle.clear();
			bundle.putInt(Constant.BILL_TYPE, Constant.EXPEND_TYPE);
			if (jumpToWhere(Constant.EXPEND_TYPE)) {
				incomeIntent = new Intent(this, YearBillActivity.class);
			}
			else {
				incomeIntent = new Intent(this, MonthBillActivity.class);
			}
			incomeIntent.putExtras(bundle);
			startActivity(incomeIntent);
		}
	}
}
