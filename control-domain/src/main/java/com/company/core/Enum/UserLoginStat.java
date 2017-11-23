package com.company.core.Enum;

//用户状态 0：正常 5：冻结（不可登入）,9:失效（相当于删除）
public enum UserLoginStat {
	N("0","正常"),F("5","冻结"),D("9","失效");
	
	private String stat;
	private String dis;
	UserLoginStat(String stat,String dis){
		this.stat = stat;
		this.dis = dis;
	}
	public String getStat(){
		return stat;
	}
	public String getDis() {
		return dis;
	}
}
