package com.company.core.Enum;

public enum FileServiceEnum {
	alibaba("alibaba"), os("os");
	private String type;

	FileServiceEnum(String type) {
		this.type = type;
	}
	public String getType(){
		return type;
	}

	public FileServiceEnum compare(String type) {
		for (FileServiceEnum e : FileServiceEnum.values()) {
			if (e.type.equals(type)) {
				return e;
			}
		}
		return null;
	}
}
