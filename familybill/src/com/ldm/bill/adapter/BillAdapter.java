package com.ldm.bill.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ldm.common.Constant;
import com.ldm.familybill.R;
import com.ldm.support.bean.CommonBean;
import com.ldm.support.tool.DataTools;

/**
 * 帐单列表数据适配器
 * @description：
 * @author ldm
 * @date 2015-7-16 上午9:40:54
 */
public class BillAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<CommonBean> billList;
	private int type;// 1为支出，2为收入，默认是支出
	private int dataType;// 数据类型，1年2月3日

	public BillAdapter(Context context, ArrayList<CommonBean> billList, int type, int dataType) {
		this.context = context;
		this.billList = billList;
		this.type = type;
		this.dataType = dataType;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return billList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return billList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.bill_list_item, null);
			holder.timeTv = (TextView) convertView.findViewById(R.id.bill_item_time);
			holder.typeTv = (TextView) convertView.findViewById(R.id.bill_item_type);
			holder.contentTv = (TextView) convertView.findViewById(R.id.bill_item_number);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		CommonBean bean = billList.get(position);
		if (dataType == 1) {
			holder.timeTv.setText(bean.getYear() + "年");
		}
		else if (dataType == 2) {
			holder.timeTv.setText(bean.getYear() + "年" + bean.getMonth() + "月");
		}
		else {
			holder.timeTv.setText(DataTools.getInstance().getNowDay(bean.getTime()));
		}
		holder.typeTv.setText(type == Constant.EXPEND_TYPE ? "支出" : "收入");
		holder.contentTv.setText("￥" + bean.getTotal());
		return convertView;
	}

	static class ViewHolder {
		TextView timeTv, typeTv, contentTv;// 分别指点时间，类型及对应的金额
	}
}
