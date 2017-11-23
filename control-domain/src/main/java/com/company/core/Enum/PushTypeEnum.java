package com.company.core.Enum;

public enum PushTypeEnum {

	JPUSH("Jpush", 0), SYSTEMPUSH("内部发送", 1);

	private String name;
	private int value;

	PushTypeEnum(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public static PushTypeEnum getType(String value) {
		try {
			int i = Integer.parseInt(value);
			PushTypeEnum[] values = PushTypeEnum.values();
			for (PushTypeEnum type : values) {
				if (type.value == i) {
					return type;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

}
