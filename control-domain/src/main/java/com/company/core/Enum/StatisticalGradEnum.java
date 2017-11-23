package com.company.core.Enum;

import java.util.Calendar;

//0：日  ， 1：月  ，2：all（所有）
public enum StatisticalGradEnum {
	
	DAY(0,"当天"),MONTH(1,"当月"),ALL(2,"全部");
	
	private int grad;
	private Calendar time;
	private String legendName;
	StatisticalGradEnum(int grad,String legendName){
		this.grad = grad;
		this.legendName = legendName;
	}
	public int getGrad() {
		return grad;
	}
	public Calendar getTime() {
		return time;
	}
	public void setTime(Calendar time) {
		this.time = time;
	}
	public String getLegendName() {
		return legendName;
	}
}
