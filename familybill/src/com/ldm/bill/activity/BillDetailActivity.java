package com.ldm.bill.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.ldm.common.CommonTitleHelper;
import com.ldm.common.Constant;
import com.ldm.expend.AddBillActivity;
import com.ldm.familybill.R;
import com.ldm.support.bean.ExpendsBean;
import com.ldm.support.bean.IncomeBean;
import com.ldm.support.db.DBTool;

/**
 * 每天的帐单详情记录
 * @description：
 * @author ldm
 * @date 2015-7-24 下午5:04:07
 */
public class BillDetailActivity extends Activity implements OnClickListener {
	/**************支出部分*****************/
	private EditText mFoodEdt;
	private EditText mArticlesEdt;
	private EditText mTrafficEdt;
	private EditText mTravelEdt;
	private EditText mClothesEdt;
	private EditText mDoctorEdt;
	private EditText mRenQingEdt;
	private EditText mBabyEdt;
	private EditText mLiveEdt;
	private View expendView;
	/*************收入部分************************************/
	private View incomeView;
	private EditText mSalaryEdt;
	private EditText mPrizeEdt;
	private EditText mManageEdt;
	private EditText mInvestEdt;
	/*******共用部分*****************************/
	private File billFile;
	private EditText mOtherEdt;
	private EditText mRemarkEdt;
	private CommonTitleHelper mtTitleHelper;
	/*************展示数据条件处理*********************/
	private int type;
	private String time;
	private IncomeBean incomeBean;// 收入数据
	private ExpendsBean expendBean;// 支出数据

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_detail);
		type = getIntent().getIntExtra(Constant.BILL_TYPE, Constant.INCOME_TYPE);
		time = getIntent().getStringExtra(Constant.TIME);
		findViewsById();
		initViewDatas();
		initListeners();
	}

	/**
	 * 设置详情数据
	 * @description：
	 * @author ldm
	 * @date 2015-7-31 上午9:59:21
	 */
	private void initViewDatas() {
		if (type == Constant.INCOME_TYPE) {
			incomeBean = getIncome(time);
			incomeView.setVisibility(View.VISIBLE);
			expendView.setVisibility(View.GONE);
			setIncomeData(incomeBean);
		}
		else if (type == Constant.EXPEND_TYPE) {
			incomeView.setVisibility(View.GONE);
			expendView.setVisibility(View.VISIBLE);
			expendBean = getExpend(time);
			setExpendData(expendBean);
		}
	}

	private void setIncomeData(IncomeBean bean) {
		mSalaryEdt.setText("￥" + bean.getSalary());
		mPrizeEdt.setText("￥" + bean.getPrize());
		mManageEdt.setText("￥" + bean.getManager());
		mInvestEdt.setText("￥" + bean.getInvest());
		mOtherEdt.setText("￥" + bean.getOther());
		mRemarkEdt.setText(bean.getRemark());
	}

	private void setExpendData(ExpendsBean bean) {
		mFoodEdt.setText("￥" + bean.getFood());
		mArticlesEdt.setText("￥" + bean.getLive());
		mTrafficEdt.setText("￥" + bean.getTraffic());
		mTravelEdt.setText("￥" + bean.getTravel());
		mClothesEdt.setText("￥" + bean.getClothes());
		mDoctorEdt.setText("￥" + bean.getDoctor());
		mRenQingEdt.setText("￥" + bean.getLaiWang());
		mBabyEdt.setText("￥" + bean.getEducation());
		mLiveEdt.setText("￥" + bean.getTravel());
		mOtherEdt.setText("￥" + bean.getOther());
		mRemarkEdt.setText(bean.getRemark());
	}

	private void findViewsById() {
		/****************共用部分***********************************/
		mtTitleHelper = new CommonTitleHelper(this, -1, R.string.family_bill_edit, R.string.family_bill_detail);
		mOtherEdt = (EditText) findViewById(R.id.bill_other_edt);
		mRemarkEdt = (EditText) findViewById(R.id.bill_remark_edt);
		/*****************支出部分*********************************/
		mFoodEdt = (EditText) findViewById(R.id.family_bill_food_edt);
		mArticlesEdt = (EditText) findViewById(R.id.family_bill_articles_edt);
		mTrafficEdt = (EditText) findViewById(R.id.family_bill_traffic_edt);
		mTravelEdt = (EditText) findViewById(R.id.family_bill_travel_edt);
		mClothesEdt = (EditText) findViewById(R.id.family_bill_clothes_edt);
		mDoctorEdt = (EditText) findViewById(R.id.family_bill_doctor_edt);
		mRenQingEdt = (EditText) findViewById(R.id.family_bill_laiwang_edt);
		mBabyEdt = (EditText) findViewById(R.id.family_bill_baby_edt);
		mLiveEdt = (EditText) findViewById(R.id.family_bill_live_edt);
		expendView = findViewById(R.id.add_expend_layout);
		/*****************收入部分**************************************/
		mSalaryEdt = (EditText) findViewById(R.id.income_salary_edt);
		mPrizeEdt = (EditText) findViewById(R.id.income_prize_edt);
		mManageEdt = (EditText) findViewById(R.id.income_manager_edt);
		mInvestEdt = (EditText) findViewById(R.id.income_invest_edt);
		incomeView = findViewById(R.id.add_income_layout);

	}

	private void initListeners() {
		mtTitleHelper.setOnLeftClickListener(this);
		mtTitleHelper.setOnRightClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.left:
				finish();
				break;
			case R.id.right:// 编辑帐单数据
				Bundle dataBundle = new Bundle();
				Intent intent = new Intent(this, AddBillActivity.class);
				if (type == Constant.INCOME_TYPE) {
					dataBundle.putSerializable(Constant.INCOME_BEAN, incomeBean);
				}
				else {
					dataBundle.putSerializable(Constant.EXPEND_BEAN, expendBean);
				}
				intent.putExtras(dataBundle);
				startActivity(intent);
				finish();
				break;
		}
	}

	private IncomeBean getIncome(String time) {
		return new DBTool(this).findOneDayIncome(time).get(0);
	}

	private ExpendsBean getExpend(String time) {
		return new DBTool(this).findOneDayExpend(time).get(0);
	}
}
