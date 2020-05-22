package com.yy.yychat.enums;

public enum FriendRequestOpType {
	
	IGNORE(0, "Ignore"),
	ACCEPT(1, "Accept");
	
	public final Integer type;
	public final String msg;

	FriendRequestOpType(Integer type, String msg){
		this.type = type;
		this.msg = msg;
	}
	
	public Integer getType() {
		return type;
	}  
	
	public static String getMsgByType(Integer type) {
		for (FriendRequestOpType operType : FriendRequestOpType.values()) {
			if (operType.getType() == type) {
				return operType.msg;
			}
		}
		return null;
	}
	
}
