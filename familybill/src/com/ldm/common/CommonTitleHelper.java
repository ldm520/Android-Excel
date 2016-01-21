package com.ldm.common;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ldm.familybill.R;

/**
 * 页面通用的Title
 * @description：
 * @author ldm
 * @date 2015-7-15 下午7:50:52
 */
public class CommonTitleHelper {
	private final Activity mActivity;
	private TextView left;
	private TextView right;
	private TextView title;

	/**
	 * 只有title
	 * @param activity
	 * @param titleStrId
	 */
	public CommonTitleHelper(Activity activity, int titleStrId) {
		this(activity, -1, -1, titleStrId);
	}

	/**
	 * 设置有左右操作及标题的Title
	 */
	public CommonTitleHelper(Activity activity, int leftStrId, int rightStrId, int titleStrId) {
		mActivity = activity;
		mActivity.setTitle(titleStrId);
		left = (TextView) activity.findViewById(R.id.left);
		setLeftMsg(leftStrId);
		left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.onBackPressed();
			}
		});
		right = (TextView) activity.findViewById(R.id.right);
		setRightMsg(rightStrId);
		title = (TextView) activity.findViewById(R.id.title);
		setTitleMsg(titleStrId);
	}

	/**
	 * 设置标题右侧文字
	 * @description：
	 * @author ldm
	 * @date 2015-7-15 下午7:54:32
	 */
	public void setRightMsg(int rightStrId) {
		if (rightStrId == -1) {
			right.setVisibility(View.GONE);
		}
		else {
			right.setVisibility(View.VISIBLE);
			right.setText(rightStrId);
		}
	}

	/**
	 * 设置标题右侧文字
	 * @description：
	 * @author ldm
	 * @date 2015-7-15 下午7:54:32
	 */
	public void setRightMsg(String msg) {
		if (TextUtils.isEmpty(msg)) {
			right.setVisibility(View.GONE);
		}
		else {
			right.setVisibility(View.VISIBLE);
			right.setText(msg);
		}
	}

	public void setOnRightClickListener(View.OnClickListener listener) {
		right.setOnClickListener(listener);
	}

	/**
	 * 设置标题左侧文字
	 * @description：
	 * @author ldm
	 * @date 2015-7-15 下午7:54:32
	 */
	public void setLeftMsg(int leftStrId) {
		if (leftStrId == -1) {
			left.setVisibility(View.GONE);
		}
		else {
			left.setVisibility(View.VISIBLE);
			left.setText(leftStrId);
		}
	}

	/**
	 * 设置标题左侧文字
	 * @description：
	 * @author ldm
	 * @date 2015-7-15 下午7:54:32
	 */
	public void setLeftMsg(String msg) {
		left.setVisibility(View.VISIBLE);
		left.setText(msg);
	}

	public void setOnLeftClickListener(View.OnClickListener listener) {
		left.setVisibility(View.VISIBLE);
		left.setOnClickListener(listener);
	}

	/**
	 * 设置标题中间文字
	 * @description：
	 * @author ldm
	 * @date 2015-7-15 下午7:54:32
	 */
	public void setTitleMsg(int titleStrId) {
		if (titleStrId == -1) {
			title.setVisibility(View.GONE);
			mActivity.setTitle("");
		}
		else {
			title.setVisibility(View.VISIBLE);
			title.setText(titleStrId);
			mActivity.setTitle(titleStrId);
		}
	}

	/**
	 * 设置标题中间文字
	 * @description：
	 * @author ldm
	 * @date 2015-7-15 下午7:54:32
	 */
	public void setTitleMsg(String msg) {
		title.setVisibility(View.VISIBLE);
		title.setText(msg);
		mActivity.setTitle(msg);
	}
}
