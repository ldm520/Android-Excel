package com.ldm.support.bean;

import java.io.Serializable;

/**
 * 帐单列表实体bean
 * @description：
 * @author ldm
 * @date 2015-7-17 下午1:59:00
 */
public class CommonBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String year;
	private String month;
	private long time;
	private String billType;
	private String total;
	private int isFirst;//是否是当前年月第一条数据，1为是

	public int getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "CommonBean [year=" + year + ", month=" + month + ", time=" + time + ", billType=" + billType + ", total=" + total + ", isFirst=" + isFirst + "]";
	}

}
