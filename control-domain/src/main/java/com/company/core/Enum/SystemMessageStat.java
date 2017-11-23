package com.company.core.Enum;

//状态  0：已下发  1：未下发 2：失败
public enum SystemMessageStat {
	S("0"), F("2"), I("1");
	private String stat;

	SystemMessageStat(String stat) {
		this.stat = stat;
	}

	public String getStat() {
		return stat;
	}
}
