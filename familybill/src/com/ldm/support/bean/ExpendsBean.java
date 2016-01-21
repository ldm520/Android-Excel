package com.ldm.support.bean;

import java.io.Serializable;

/**
 * 收入实体Bean
 * @description：
 * @author ldm
 * @date 2015-7-16 下午5:05:31
 */
public class ExpendsBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String year;
	private String month;
	private long time;
	private String food;
	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	private String live;
	private String use;
	private String clothes;
	private String traffic;
	private String doctor;
	private String laiWang;
	private String education;
	private String travel;
	private String other;
	private String remark;
	private String total;
	private int isFirstDay;// 是否是当月帐单的第一条数据0表示否，1表示是
	private int isFirstYear;// 是否是当年帐单的第一条数据0表示否，1表示是

	public int getIsFirstYear() {
		return isFirstYear;
	}

	public void setIsFirstYear(int isFirstYear) {
		this.isFirstYear = isFirstYear;
	}

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

	public String getLive() {
		return live;
	}

	public void setLive(String live) {
		this.live = live;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getClothes() {
		return clothes;
	}

	public void setClothes(String clothes) {
		this.clothes = clothes;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getLaiWang() {
		return laiWang;
	}

	public void setLaiWang(String laiWang) {
		this.laiWang = laiWang;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getTravel() {
		return travel;
	}

	public void setTravel(String travel) {
		this.travel = travel;
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
		return "ExpendsBean [year=" + year + ", month=" + month + ", time=" + time + ", food=" + food + ", live=" + live + ", use=" + use + ", clothes=" + clothes + ", traffic=" + traffic + ", doctor=" + doctor + ", laiWang=" + laiWang + ", education=" + education + ", travel=" + travel + ", other=" + other + ", remark=" + remark + ", total=" + total + ", isFirstDay=" + isFirstDay + ", isFirstYear=" + isFirstYear + "]";
	}

}
