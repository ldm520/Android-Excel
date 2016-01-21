package com.ldm.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ldm.familybill.R;

/**
 * 欢迎页面
 * @description：
 * @author ldm
 * @date 2015-7-15 下午7:37:01
 */
public class WelcomeActivity extends Activity {
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.welcome_activity);
		delayToInitData(2000);
	}

	private void delayToInitData(int delay) {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
				WelcomeActivity.this.finish();
			}
		}, delay);
	}
}
