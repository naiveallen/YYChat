package com.yy.yychat.enums;

public enum MsgSignFlagEnum {
	
	unsign(0, "Un Received"),
	signed(1, "Received");
	
	public final Integer type;
	public final String content;
	
	MsgSignFlagEnum(Integer type, String content){
		this.type = type;
		this.content = content;
	}
	
	public Integer getType() {
		return type;
	}  
}
