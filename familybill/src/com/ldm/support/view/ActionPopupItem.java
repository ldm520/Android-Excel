package com.ldm.support.view;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * @description：弹窗内部子类项（绘制标题和图标）
 * @author samy
 * @date 2014年8月24日 下午11:58:01
 */
public class ActionPopupItem {
	/** 定义图片对象 */
	public Drawable mDrawable;
	/** 定义文本对象 */
	public CharSequence mTitle;
	/** 最上面头部是否做特殊处理显示 */
	public Boolean isTopSpecial;
	/** 当前的选项是否被选中*/
	public Boolean mItemSelected = false;
	/** 当前的选项的值*/
	public int mItemValue;

	public ActionPopupItem(Drawable drawable, CharSequence title, Boolean isTopSpecial) {
		this.mDrawable = drawable;
		this.mTitle = title;
		this.isTopSpecial = isTopSpecial;
	}
	
	public ActionPopupItem( CharSequence title, Boolean isTopSpecial,int mItemValue,boolean mItemSelected) {
		this.mTitle = title;
		this.isTopSpecial = isTopSpecial;
		this.mItemValue = mItemValue;
		this.mItemSelected = mItemSelected;
	}

	public ActionPopupItem(Context context, int titleId, int drawableId, Boolean isTopSpecial) {
		this(context, context.getResources().getText(titleId), drawableId, isTopSpecial);
	}

	public ActionPopupItem(Context context, CharSequence title, int drawableId, Boolean isTopSpecial) {
		this(context.getResources().getDrawable(drawableId), title, isTopSpecial);
	}

	/**
	 * 重构构造函数，可以选择不传图片
	 * 
	 * @param context
	 * @param title
	 * @param isTopSpecial
	 */
	public ActionPopupItem(Context context, CharSequence title, Boolean isTopSpecial) {
		this((Drawable) null, title, isTopSpecial);
		//this.mTitle = title;
		//this.isTopSpecial = isTopSpecial;
	}
	public ActionPopupItem(CharSequence title, Boolean itemSelected) {
		this((Drawable) null, title, false);
		mItemSelected = itemSelected;
		//this.mTitle = title;
		//this.isTopSpecial = isTopSpecial;
	}
}
