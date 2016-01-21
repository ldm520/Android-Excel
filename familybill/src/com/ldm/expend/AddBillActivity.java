package com.ldm.expend;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.ldm.common.CommonTitleHelper;
import com.ldm.common.Constant;
import com.ldm.excel.ExcelUtils;
import com.ldm.familybill.R;
import com.ldm.main.MainActivity;
import com.ldm.support.bean.ExpendsBean;
import com.ldm.support.bean.IncomeBean;
import com.ldm.support.db.DBHelper;
import com.ldm.support.tool.DataTools;

/**
 * 增加一笔支出记录
 * @description：
 * @author ldm
 * @date 2015-7-15 下午7:41:57
 */
public class AddBillActivity extends Activity implements OnClickListener {
	/* 添加支出或收入切换* */
	private RadioGroup billRg;
	private RadioButton expendRb;
	private RadioButton incomeRb;
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
	private String[] expendTitle;
	/*************收入部分************************************/
	private View incomeView;
	private EditText mSalaryEdt;
	private EditText mPrizeEdt;
	private EditText mManageEdt;
	private EditText mInvestEdt;
	private String[] incomeTitle;
	/*******共用部分*****************************/
	private File billFile;
	private DBHelper mDbHelper;
	private EditText mOtherEdt;
	private EditText mRemarkEdt;
	private Button mSaveBtn;
	private String[] saveData;
	private ArrayList<ArrayList<String>> bill2List;
	private int billType = Constant.EXPEND_TYPE;// 2为支出，1为收入，默认是支出
	private CommonTitleHelper mtTitleHelper;
	/**用于计算当天的输入的数据合计*/
	private int year = DataTools.getInstance().getNowYear();
	private int month = DataTools.getInstance().getNowMonth();
	private long time = DataTools.getInstance().getNowSeconds();
	private ArrayList<String> calArray = new ArrayList<String>();
	private IncomeBean incomeBean;
	private ExpendsBean expendBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_one_bill);
		expendTitle=getResources().getStringArray(R.array.excel_title);
		findViewsById();
		initDetailData();
		initFiles();
		initListeners();
		mDbHelper = new DBHelper(this);
		mDbHelper.open();
		bill2List = new ArrayList<ArrayList<String>>();
	}

	/**
	 * 编辑帐单时初始化数据
	 * @description：
	 * @author ldm
	 * @date 2015-8-11 下午5:18:18
	 */
	private void initDetailData() {
		if (null != getIntent().getSerializableExtra(Constant.EXPEND_BEAN)) {
			expendBean = (ExpendsBean) getIntent().getSerializableExtra(Constant.EXPEND_BEAN);
			billType = Constant.EXPEND_TYPE;
			expendView.setVisibility(View.VISIBLE);
			incomeView.setVisibility(View.GONE);
			mFoodEdt.setText(expendBean.getFood());
			mArticlesEdt.setText(expendBean.getLive());
			mTrafficEdt.setText(expendBean.getUse());
			mTravelEdt.setText(expendBean.getClothes());
			mClothesEdt.setText(expendBean.getTraffic());
			mDoctorEdt.setText(expendBean.getDoctor());
			mRenQingEdt.setText(expendBean.getLaiWang());
			mBabyEdt.setText(expendBean.getEducation());
			mLiveEdt.setText(expendBean.getTravel());
			mOtherEdt.setText(expendBean.getOther());
			mRemarkEdt.setText(expendBean.getRemark());
		}
		if (null != getIntent().getSerializableExtra(Constant.INCOME_BEAN)) {
			billType = Constant.INCOME_TYPE;
			expendView.setVisibility(View.GONE);
			incomeView.setVisibility(View.VISIBLE);
			incomeBean = (IncomeBean) getIntent().getSerializableExtra(Constant.INCOME_BEAN);
			mSalaryEdt.setText(incomeBean.getSalary());
			mPrizeEdt.setText(incomeBean.getPrize());
			mManageEdt.setText(incomeBean.getManager());
			mInvestEdt.setText(incomeBean.getInvest());
			mOtherEdt.setText(incomeBean.getOther());
			mRemarkEdt.setText(incomeBean.getRemark());
		}
	}

	private void initFiles() {
		billFile = new File(getSDPath() + "/Family");
		makeDir(billFile);
	}

	private void findViewsById() {
		/****************共用部分***********************************/
		mtTitleHelper = new CommonTitleHelper(this, R.string.family_bill_add);
		billRg = (RadioGroup) findViewById(R.id.bill_rg);
		expendRb = (RadioButton) findViewById(R.id.add_one_expend);
		incomeRb = (RadioButton) findViewById(R.id.add_one_income);
		mOtherEdt = (EditText) findViewById(R.id.bill_other_edt);
		mRemarkEdt = (EditText) findViewById(R.id.bill_remark_edt);
		mSaveBtn = (Button) findViewById(R.id.family_bill_save);
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
		mSaveBtn.setOnClickListener(this);
		mtTitleHelper.setOnLeftClickListener(this);
		billRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.add_one_expend:
						billType = Constant.EXPEND_TYPE;
						expendView.setVisibility(View.VISIBLE);
						incomeView.setVisibility(View.GONE);
						break;
					case R.id.add_one_income:
						billType = Constant.INCOME_TYPE;
						expendView.setVisibility(View.GONE);
						incomeView.setVisibility(View.VISIBLE);
						break;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.left:
				finish();
				break;
			case R.id.family_bill_save:
				calArray.clear();
				if (billType == Constant.EXPEND_TYPE) {
					calArray.add(dealSaveData(mFoodEdt));
					calArray.add(dealSaveData(mArticlesEdt));
					calArray.add(dealSaveData(mTrafficEdt));
					calArray.add(dealSaveData(mTravelEdt));
					calArray.add(dealSaveData(mClothesEdt));
					calArray.add(dealSaveData(mDoctorEdt));
					calArray.add(dealSaveData(mRenQingEdt));
					calArray.add(dealSaveData(mBabyEdt));
					calArray.add(dealSaveData(mLiveEdt));
					calArray.add(dealSaveData(mOtherEdt));
					calTotal(calArray);
					saveExpendsData();
					startActivity(new Intent(AddBillActivity.this, MainActivity.class));
					finish();
				}
				else if (billType == Constant.INCOME_TYPE) {
					calArray.add(dealSaveData(mSalaryEdt));
					calArray.add(dealSaveData(mPrizeEdt));
					calArray.add(dealSaveData(mManageEdt));
					calArray.add(dealSaveData(mInvestEdt));
					calArray.add(dealSaveData(mOtherEdt));
					calTotal(calArray);
					saveIncomeData();
					startActivity(new Intent(AddBillActivity.this, MainActivity.class));
					finish();
				}

				break;
		}
	}

	private ArrayList<ArrayList<String>> getBillData() {
		bill2List.clear();
		Cursor mCrusor = mDbHelper.exeSql("select * from expend_bill");
		while (mCrusor.moveToNext()) {// { "日期", "食物水果", "住房租房", "日用百货", "衣着服饰", "交通话费", "医疗保健", "人情客往", "教育培训", "旅游踏青", "其它支出", "备注说明", "合计" };
			ArrayList<String> beanList = new ArrayList<String>();
			beanList.add(mCrusor.getString(3));
			beanList.add(mCrusor.getString(4));
			beanList.add(mCrusor.getString(5));
			beanList.add(mCrusor.getString(6));
			beanList.add(mCrusor.getString(7));
			beanList.add(mCrusor.getString(8));
			beanList.add(mCrusor.getString(9));
			beanList.add(mCrusor.getString(10));
			beanList.add(mCrusor.getString(11));
			beanList.add(mCrusor.getString(12));
			beanList.add(mCrusor.getString(13));
			beanList.add(mCrusor.getString(14));
			beanList.add(mCrusor.getString(15));
			bill2List.add(beanList);
		}
		mCrusor.close();
		return bill2List;
	}

	public static void makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}

	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
		}
		String dir = sdDir.toString();
		return dir;

	}

	private boolean canSave(String[] data) {
		boolean isOk = false;
		for (int i = 0; i < data.length; i++) {
			if (i > 0 && i < data.length) {
				if (!TextUtils.isEmpty(data[i])) {
					isOk = true;
				}
			}
		}
		return isOk;
	}

	/**
	 * 保存支出数据
	 * @description：
	 * @author ldm
	 * @date 2015-7-17 上午9:54:20
	 */
	@SuppressLint("SimpleDateFormat")
	private void saveExpendsData() {
		saveData = new String[] { new SimpleDateFormat("yyyy-MM-dd").format(new Date()), mFoodEdt.getText().toString().trim(), mArticlesEdt.getText().toString().trim(), mTrafficEdt.getText().toString().trim(), mTravelEdt.getText().toString().trim(), mClothesEdt.getText().toString().trim(), mDoctorEdt.getText().toString().trim(), mRenQingEdt.getText().toString().trim(), mBabyEdt.getText().toString().trim(), mLiveEdt.getText().toString().trim(), mOtherEdt.getText().toString().trim(), mRemarkEdt.getText().toString().trim() };
		if (canSave(saveData)) {
			ContentValues values = new ContentValues();
			// year month time food live use clothes traffic doctor laiwang eduation travel other remark total
			values.put("year", year);
			values.put("month", month);
			values.put("time", time);
			values.put("food", dealSaveData(mFoodEdt));
			values.put("live", dealSaveData(mLiveEdt));
			values.put("use", dealSaveData(mArticlesEdt));
			values.put("clothes", dealSaveData(mClothesEdt));
			values.put("traffic", dealSaveData(mTrafficEdt));
			values.put("doctor", dealSaveData(mDoctorEdt));
			values.put("laiwang", dealSaveData(mRenQingEdt));
			values.put("eduation", dealSaveData(mBabyEdt));
			values.put("travel", dealSaveData(mTravelEdt));
			values.put("other", dealSaveData(mOtherEdt));
			values.put("remark", dealSaveData(mRemarkEdt));
			values.put("total", calTotal(calArray));
			if(expendBean!=null){
				String sql="delete from expend_bill where time= "+"'"+String.valueOf(expendBean.getTime())+"'";
				mDbHelper.delete(sql);
			}
			long insert = mDbHelper.insert("expend_bill", values);
			if (insert > 0) {
				ExcelUtils.initExcel(billFile.toString() + "/expend.xls", expendTitle);
				ExcelUtils.writeObjListToExcel(getBillData(), getSDPath() + "/Family/expend.xls", this);
			}
		}
		else {
			Toast.makeText(this, "请至少输入一项数据！", Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 保存收入数据
	 * @description：
	 * @author ldm
	 * @date 2015-7-17 上午9:54:47
	 */
	private void saveIncomeData() {
		saveData = new String[] { new SimpleDateFormat("yyyy-MM-dd").format(new Date()), mSalaryEdt.getText().toString().trim(), mPrizeEdt.getText().toString().trim(), mManageEdt.getText().toString().trim(), mInvestEdt.getText().toString().trim(), mOtherEdt.getText().toString().trim(), mRemarkEdt.getText().toString().trim() };
		if (canSave(saveData)) {
			ContentValues values = new ContentValues();
			// year monty time salary prize manager invest other remark total
			values.put("year", year);
			values.put("month", month);
			values.put("time", time);
			values.put("salary", dealSaveData(mSalaryEdt));
			values.put("prize", dealSaveData(mPrizeEdt));
			values.put("manager", dealSaveData(mManageEdt));
			values.put("invest", dealSaveData(mInvestEdt));
			values.put("other", dealSaveData(mOtherEdt));
			values.put("remark", dealSaveData(mRemarkEdt));
			values.put("total", calTotal(calArray));
			if(incomeBean!=null){
				String sql="delete from income_bill where time= "+"'"+String.valueOf(incomeBean.getTime())+"'";
				mDbHelper.delete(sql);
			}
			long insert = mDbHelper.insert("income_bill", values);
			if (insert > 0) {
				ExcelUtils.initExcel(billFile.toString() + "/income_bill.xls", incomeTitle);
				ExcelUtils.writeObjListToExcel(getIncomeBillData(), getSDPath() + "/Family/income_bill.xls", this);
			}
		}
		else {
			Toast.makeText(this, "请填写任意一项内容", Toast.LENGTH_SHORT).show();
		}

	}

	private ArrayList<ArrayList<String>> getIncomeBillData() {
		bill2List.clear();
		Cursor mCrusor = mDbHelper.exeSql("select * from income_bill");
		while (mCrusor.moveToNext()) {
			ArrayList<String> beanList = new ArrayList<String>();
			beanList.add(mCrusor.getString(3));
			beanList.add(mCrusor.getString(4));
			beanList.add(mCrusor.getString(5));
			beanList.add(mCrusor.getString(6));
			beanList.add(mCrusor.getString(7));
			beanList.add(mCrusor.getString(8));
			beanList.add(mCrusor.getString(9));
			beanList.add(mCrusor.getString(10));
			bill2List.add(beanList);
		}
		mCrusor.close();
		return bill2List;
	}

	/**
	 * 存数据处理，如果没有填写该项，则默认存到数据库为0,方便计算
	 * @description：
	 * @author ldm
	 * @date 2015-7-22 下午3:16:57
	 */
	private String dealSaveData(EditText edt) {
		String data = "0";
		if (!TextUtils.isEmpty(edt.getText().toString().trim())) {
			data = edt.getText().toString().trim();
		}
		return data;
	}

	/**
	 * 计算当天的数据和
	 * @description：
	 * @author ldm
	 * @date 2015-7-22 下午3:23:42
	 */
	private double calTotal(ArrayList<String> dataList) {
		double total = 0;
		for (String string : dataList) {
			total += Double.valueOf(string);
		}
		return total;
	}
}
