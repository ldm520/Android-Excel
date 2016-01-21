package com.ldm.support.bean;

import java.io.Serializable;

/**
 * 收入实体Bean
 * @description：
 * @author ldm
 * @date 2015-7-16 下午5:05:31
 */
public class IncomeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String year;
	private String month;
	private long time;
	private String salary;
	private String prize;
	private String manager;
	private String invest;
	private String other;
	private String remark;
	private String total;
	private int isFirstDay;//是否是当月帐单的第一条数据0表示否，1表示是
	private int isFirstYear;// 是否是当年帐单的第一条数据0表示否，1表示是

	public int getIsFirstDay() {
		return isFirstDay;
	}

	public void setIsFirstDay(int isFirstDay) {
		this.isFirstDay = isFirstDay;
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

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getInvest() {
		return invest;
	}

	public void setInvest(String invest) {
		this.invest = invest;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "IncomeBean [year=" + year + ", month=" + month + ", time=" + time + ", salary=" + salary + ", prize=" + prize + ", manager=" + manager + ", invest=" + invest + ", other=" + other + ", remark=" + remark + ", total=" + total + ", isFirstDay=" + isFirstDay + "]";
	}

}
