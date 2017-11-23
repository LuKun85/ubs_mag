package com.company.core.Enum;

public enum UserTypeIdEnum {
	// 0：学生 1：工薪族 2：自由职业 3：企业主
	ENTREPRENEUR("3"), NO_JO("2"), OFFICE_WORKER("1"), STUDENT("0");
	private String value;

	UserTypeIdEnum(String value) {
		this.value = value;
	}
	
	public boolean Equals(Object value){
		value = value.toString();
		if(value == null || "".equals(((String)value).trim())){
			return false;
		}
		return this.value.equals(value);
	}
}
