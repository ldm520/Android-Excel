package com.ldm.bill.activity;

import com.ldm.familybill.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * 导出帐单到邮箱
 * @description：
 * @author ldm
 * @date 2015-10-13 下午3:38:40
 * 再附一些常用的邮件服务器126邮箱
 * POP3服务器:pop.126.com    SMTP服务器:smtp.126.com 163邮箱
 * POP3服务器:pop.163.com    SMTP服务器:smtp.163.com
 * yahoo邮箱    POP3服务器：pop.mail.yahoo.com.cn    SMTP服务器：smtp.mail.yahoo.com.cn
 * Sohu邮箱     POP3服务器：pop3.sohu.com     SMTP服务器：smtp.sohu.com
 * Gmail邮箱    POP3服务器是pop.gmail.com   SMTP服务器是smtp.gmail.com 
 *  QQ邮箱   POP3服务器：pop.qq.com    SMTP服务器：smtp.qq.com   
 */
public class ExportBill2EmailActivity extends Activity {
	private int billType;
	private int emailType=0;
	private String emaliAddress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_export_email);
	}
}
