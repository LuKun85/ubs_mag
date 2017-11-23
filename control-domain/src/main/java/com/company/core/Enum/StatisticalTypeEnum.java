package com.company.core.Enum;

//0：注册 ，1：活跃 ，2：流失  ，3：注册订单  4：申请订单   , 5 下载量 ， 6：产品
public enum StatisticalTypeEnum {

	USER_LOGIN(0,"注册量"), USER_ACTIVE(1,"活跃量"), USER_LOSE(2,"流失量"), ORDER_LOGIN(3,"注册订单量"), ORDER_APPLY(4,"申请订单量"), DOWN(5,"下载量"), PRODUCT(6,"产品量");
	private int type;
	private String chartTitle;

	StatisticalTypeEnum(int type,String chartTitle) {
		this.type = type;
		this.chartTitle = chartTitle;
	}
	public int getType() {
		return type;
	}
	public String getChartTitle() {
		return chartTitle;
	}
}
