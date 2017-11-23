package com.company.core.Enum;
//发送类型  0：每天发送    1：定时发送  2：不发送（测试使用）
public enum SystemMessageSendType {
	EVERY_DAY("0"), TIMER("1"), TEST("2");
	private String type;

	SystemMessageSendType(String stat) {
		this.type = stat;
	}

	public String getType() {
		return type;
	}
}
