package com.ldm.support.tool;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 
 * @description 详细描述：
 * @author samy
 * @date 2014-5-6 下午3:27:26
 */
public class UiHelper {

	/**
	 * 获取屏幕显示的宽度和高度
	 * 
	 * @param context
	 *            上下文
	 * @return 屏幕显示的宽度和高度
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics;
	}

	// int screenWidth = UiHelper.getDisplayMetrics(getBaseContext()).widthPixels;
	// int temp = screenWidth / 2 -36;

	// ViewGroup.LayoutParams params = mTvMessageTotalSize.getLayoutParams();
	// MarginLayoutParams mlps = new MarginLayoutParams(params);
	// MarginLayoutParams mlps = (MarginLayoutParams) mTvMessageTotalSize.getLayoutParams();
	// mlps.setMargins(temp, mlps.topMargin, mlps.rightMargin, mlps.bottomMargin);
	// mTvMessageTotalSize.setLayoutParams(mlps);
	// mTvMessageTotalSize.getParent().requestLayout();

	public static boolean isGifImage(String imageUrl) {
		return (!TextUtils.isEmpty(imageUrl) && (imageUrl.endsWith(".gif") || imageUrl.endsWith(".GIF")));
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @param activity
	 *            Activity
	 * @return 状态栏高度
	 */
	public static int getStatusBarHeight(Activity activity) {
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		try {
			if (statusBarHeight == 0) {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object obj = c.newInstance();
				Field field = c.getField("status_bar_height");
				int id = Integer.parseInt(field.get(obj).toString());
				statusBarHeight = activity.getResources().getDimensionPixelSize(id);
			}
		}
		catch (Exception e) {
			// LogUtil.e("get status bar height fail", e.toString());
		}
		return statusBarHeight;
	}

	/**
	 * 退出Activity时，销毁界面中ImageView 的资源图片
	 * 
	 * @param view
	 */
	public static void unbindImageViewDrawables(ImageView view) {
		if (view != null) {
			if (view.getDrawable() != null) {
				Drawable drawable = view.getDrawable();
				// 别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
				/**
				 * view.setBackgroundResource(0) 优先 drawable.setCallback(null) 的原因在setBackground的代码里包含对背景处理： Regardless of whether we're setting a new background or not, we want to clear the previous drawable. if (mBackground != null) { mBackground.setCallback(null); unscheduleDrawable(mBackground); } 而drawable.setCallback仅将mCallback = new WeakReference<Callback>(cb)设置为弱引用。 因此，view.getDrawable()之后优先调用view .setBackgroundResource(0)，便可放心大胆的对drawable操作而不导致used a recycled bitmap错误
				 */
				view.setBackgroundResource(0);
				drawable.setCallback(null);
				if (drawable instanceof BitmapDrawable) {
					BitmapDrawable bd = (BitmapDrawable) drawable;
					if (bd != null) {
						Bitmap bitmap = bd.getBitmap();
						if (bitmap != null && !bitmap.isRecycled()) {
							// HWLog.d(TAG, "Recycling Bitmap Memory " +
							// ImageUtils.getBitmapSize(bitmap) + "byte");
							bitmap.recycle();
							bitmap = null;
						}
					}
				}
			}
			else {
				view.setBackgroundResource(0);// 别忘了把背景设为null，避免onDraw刷新背景时候出现used
											  // a recycled bitmap错误
			}
			view.setImageDrawable(null);
		}
	}

	/**
	 * 销毁某个界面中的资源图片和背景图片. 慎用。 只有确认此界面图片不会再使用时才可以调用此函数
	 * 
	 * @param view
	 */
	public static void unbindViewsDrawables(View view) {
		if (view != null) {
			if (view instanceof ImageView) {
				unbindImageViewDrawables((ImageView) view);
			}

			if (view.getBackground() != null) {
				Drawable drawableBG = view.getBackground();
				drawableBG.setCallback(null); // 把回调刷新设为null // 使之不再invalidate
				view.setBackgroundResource(0);// 别忘了把背景设为null，避免onDraw刷新背景时候出现used
											  // a recycled bitmap错误

				if (drawableBG instanceof BitmapDrawable) {
					BitmapDrawable bd = (BitmapDrawable) drawableBG;
					if (bd != null) {
						Bitmap bitmap = bd.getBitmap();
						if (bitmap != null) {
							// HWLog.d("tag", "Recycling Bitmap Memory " +
							// ImageUtils.getBitmapSize(bitmap) + "byte");
							bitmap.recycle();
							bitmap = null;
						}
					}
				}
			}

			if (view instanceof ViewGroup) {
				for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
					unbindViewsDrawables(((ViewGroup) view).getChildAt(i));
				}
			}
		}
	}

	/**
	 * 限制标题的长度（广告页面及酒店详情页面标题太长会覆盖两端）
	 * title指标题内容，length批标题限制的字符长度
	 * @description：
	 * @author ldm
	 * @date 2014年8月21日 下午2:43:54
	 */
	public static String limitTitlelength(String title, int length) {
		String content = "";
		int countSize = 0;
		int showLength = 0;
		char[] array = title.toCharArray();
		int i = 0;
		for (i = 0; i < array.length; i++) {
			if ((char) (byte) array[i] != array[i])// 判断是否为中文
			{
				countSize += 2; // 如果为中文或者中文特殊符号则占两个字节
			}
			else {
				countSize += 1; // 英文则占一个字节
			}
			if (countSize <= length) {
				showLength++;
			}
		}
		if (countSize > length) {
			content = title.substring(0, showLength - 1) + "...";
		}
		else {
			content = title;
		}
		return content;
	}

	public static int dpToPx(int dp, Context context) {
		Resources r = context.getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return px;
	}
}
