package com.ldm.support.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ldm.familybill.R;
import com.ldm.support.tool.UiHelper;

/**
 * @description：标题按钮上的弹窗（继承自PopupWindow）
 * @author samy
 * @date 2014年8月24日 下午11:57:35
 */
public class TitlePopup extends PopupWindow {
	private Context mContext;
	// 列表弹窗的间隔
	protected final int LIST_PADDING = 10;
	// 实例化一个矩形
	private Rect mRect = new Rect();
	// 坐标的位置（x、y）
	private final int[] mLocation = new int[2];
	// 屏幕的宽度和高度
	private int mScreenWidth, mScreenHeight;
	// 判断是否需要添加或更新列表子类项
	private boolean mIsDirty;
	// 位置不在中心
	private int popupGravity = Gravity.NO_GRAVITY;
	// 弹窗子类项选中时的监听
	private OnItemOnClickListener mItemOnClickListener;
	// 定义列表对象
	private ListView mListView;
	// 定义弹窗子类项列表
	private ArrayList<ActionPopupItem> mActionItems = new ArrayList<ActionPopupItem>();

	private LinearLayout mTitleLinearLayout;
	private int mImagePosition = 1; // 1:left; 2:top; 3:right; 4:bottom

	private int mUIStyle = 1; // 1:绿色背景色，文字为白色，居中对齐；2：白色背景色，文字为黑色，居中对齐

	public TitlePopup(Context context, int width, int height, OnItemOnClickListener listener) {
		this(context, width, height, listener, 1);
	}

	public TitlePopup(Context context, int width, int height, OnItemOnClickListener listener, int ImagePos) {
		this(context, R.layout.title_popup, width, height, listener, ImagePos);

	}

	public TitlePopup(Context context, int layoutResID, int width, int height, OnItemOnClickListener listener, int ImagePos) {
		super(context);
		this.mContext = context;
		mImagePosition = ImagePos;
		// 设置可以获得焦点
		setFocusable(true);
		// 设置弹窗内可点击
		setTouchable(true);
		// 设置弹窗外可点击
		setOutsideTouchable(true);
		// 获得屏幕的宽度和高度
		mScreenWidth = UiHelper.getDisplayMetrics(context).widthPixels;
		mScreenHeight = UiHelper.getDisplayMetrics(context).heightPixels;
		mItemOnClickListener = listener;
		setBackgroundDrawable(new BitmapDrawable());
		// 设置弹窗的宽度和高度
		setWidth(width);
		setHeight(height);
		// 设置弹窗的布局界面
		setContentView(LayoutInflater.from(mContext).inflate(layoutResID, null));
		initUI();
	}

	/**
	 * 
	 * @description：动态设置title的风格
	 * @author lzt
	 * @date 2014年9月12日 下午3:30:24
	 */
	public void setListViewSelector(int resID) {
		if (resID != 0) {
			mListView.setSelector(resID);
		}
		else {
			mListView.setSelector(null);
		}
	}

	public void setTitleLinearBg(int resid) {
		mTitleLinearLayout.setBackgroundResource(resid);
	}

	/**
	 * 初始化弹窗列表
	 */
	private void initUI() {
		// getContentView这种设置不错
		mListView = (ListView) getContentView().findViewById(R.id.title_list);
		mTitleLinearLayout = (LinearLayout) getContentView().findViewById(R.id.title_popu_linear);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
				dismiss();
				if (mItemOnClickListener != null) {
					mItemOnClickListener.onItemClick(mActionItems.get(index), index);
				}
				// Toast.makeText(mContext, "---index----" + index,
				// Toast.LENGTH_LONG).show();
			}
		});
	}

	/**
	 * 显示弹窗列表界面
	 */
	public void show(View view) {
		// 获得点击屏幕的位置坐标
		view.getLocationOnScreen(mLocation);
		// 设置矩形的大小
		mRect.set(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(), mLocation[1] + view.getHeight());
		populateActions();
		// 显示弹窗的位置
		showAtLocation(view, popupGravity, mRect.left, mRect.bottom);
	}

	/**
	 * 添加子类项
	 */
	public void addAction(ActionPopupItem action) {
		if (action != null) {
			mActionItems.add(action);
			mIsDirty = true;
		}
	}

	/**
	 * 清除子类项
	 */
	public void cleanAction() {
		if (!mActionItems.isEmpty()) {
			mActionItems.clear();
			mIsDirty = true;
		}
	}

	/**
	 * 根据位置得到子类项
	 */
	public ActionPopupItem getAction(int position) {
		if (position < 0 || position > mActionItems.size()) return null;
		return mActionItems.get(position);
	}

	/**
	 * 根据得到子类项的个数
	 */
	public int getActionSize() {

		return mActionItems.size();
	}

	/**
	 * 设置监听事件
	 */
	// public void setItemOnClickListener(OnItemOnClickListener
	// onItemOnClickListener) {
	// this.mItemOnClickListener = onItemOnClickListener;
	// }

	/**
	 * @description：弹窗子类项按钮监听事件
	 * @author samy
	 * @date 2014年8月25日 上午12:04:14
	 */
	public static interface OnItemOnClickListener {
		public void onItemClick(ActionPopupItem item, int position);
	}

	/**
	 * 设置弹窗列表子项
	 */
	private void populateActions() {
		mIsDirty = false;
		// 设置列表的适配器
		mListView.setAdapter(new BaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView textView = null;
				if (convertView == null) {
					textView = new TextView(mContext);
					if (mUIStyle == 1 || mUIStyle == 3) {
						textView.setTextColor(mContext.getResources().getColor(android.R.color.white));
					}
					else if (mUIStyle == 2) {
						textView.setTextColor(mContext.getResources().getColor(android.R.color.black));
						textView.setBackgroundResource(android.R.color.white);
					}
					textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
					// 设置文本居中
					textView.setGravity(Gravity.CENTER);
					// 设置文本域的范围
					textView.setPadding(0, 13, 0, 13);
					// 设置文本在一行内显示（不换行）
					textView.setSingleLine(true);
				}
				else {
					textView = (TextView) convertView;
				}
				ActionPopupItem item = mActionItems.get(position);
				// 设置文本文字
				textView.setText(item.mTitle);

				if (null != item.mDrawable) {
					// 设置文字与图标的间隔
					textView.setCompoundDrawablePadding(10);
					// 设置在文字的左边放一个图标
					if (mImagePosition == 3) {
						textView.setCompoundDrawablesWithIntrinsicBounds(null, null, item.mDrawable, null);
					}
					else {
						// textView.setGravity(gravity);
						// textView.setWidth(LayoutParams.MATCH_PARENT);
						// textView.setGravity(Gravity.CENTER);
						textView.setCompoundDrawablesWithIntrinsicBounds(item.mDrawable, null, null, null);
					}
				}
				else {
					if (mUIStyle == 3) {
						textView.setWidth(LayoutParams.MATCH_PARENT);
						textView.setGravity(Gravity.LEFT);
					}
					else if (mUIStyle == 2) {
						textView.setWidth(LayoutParams.MATCH_PARENT);
						textView.setGravity(Gravity.LEFT);
						int topbottom = UiHelper.dpToPx(5, mContext);
						textView.setPadding(UiHelper.dpToPx(18, mContext), topbottom, 0, topbottom);
						if (item.mItemSelected) {
							textView.setTextColor(Color.WHITE);
							textView.setBackgroundColor(Color.RED);
						}
						else {
							textView.setTextColor(Color.BLACK);
							textView.setBackgroundColor(Color.WHITE);
						}
					}
				}
				return textView;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return mActionItems.get(position);
			}

			@Override
			public int getCount() {
				return mActionItems.size();
			}
		});
		ViewGroup.LayoutParams mLayoutParams = mListView.getLayoutParams();
		mLayoutParams.width = mScreenWidth / 3;
		mListView.setLayoutParams(mLayoutParams);
	}

	public int getUIStyle() {
		return mUIStyle;
	}

	public void setUIStyle(int mUIStyle) {
		this.mUIStyle = mUIStyle;
	}

}
