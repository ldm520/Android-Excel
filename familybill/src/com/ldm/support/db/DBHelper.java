package com.ldm.support.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "ldm_family"; // DB name
	private Context mcontext;
	private DBHelper mDbHelper;
	private SQLiteDatabase db;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 11);
		this.mcontext = context;
	}

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);

	}

	/**
	 * 用户第一次使用软件时调用的操作，用于获取数据库创建语句（SW）,然后创建数据库
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 支出表
		String sql = "create table if not exists expend_bill(id integer primary key,year text,month text,time LONG,food text,live text,use text,clothes text,traffic text,doctor text,laiwang text,eduation text,travel text,other text,remark text,total text)";
		db.execSQL(sql);
		// 收益表格
		String sql1 = "create table if not exists income_bill(id integer primary key,year text,month text,time LONG,salary text,prize text,manager text,invest text,other text,remark text,total text)";
		db.execSQL(sql1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/* 打开数据库,如果已经打开就使用，否则创建 */
	public DBHelper open() {
		if (null == mDbHelper) {
			mDbHelper = new DBHelper(mcontext);
		}
		db = mDbHelper.getWritableDatabase();
		return this;
	}

	/* 关闭数据库 */
	public void close() {
		db.close();
		mDbHelper.close();
	}

	/**添加数据 */
	public long insert(String tableName, ContentValues values) {
		return db.insert(tableName, null, values);
	}

	/**添加数据 */
	public void delete(String sql) {
		db.execSQL(sql);
	}

	/**查询数据*/
	public Cursor findList(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
		return db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}

	public Cursor exeSql(String sql) {
		return db.rawQuery(sql, null);
	}
}