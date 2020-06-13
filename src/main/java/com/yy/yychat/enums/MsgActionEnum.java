package com.yy.yychat.enums;

public enum MsgActionEnum {
	
	CONNECT(1, "Connect"),
	CHAT(2, "Chat Message"),
	SIGNED(3, "Received Message"),
	KEEPALIVE(4, "heartbeat"),
	PULL_FRIEND(5, "Fetch Friends");
	
	public final Integer type;
	public final String content;
	
	MsgActionEnum(Integer type, String content){
		this.type = type;
		this.content = content;
	}
	
	public Integer getType() {
		return type;
	}  
}
