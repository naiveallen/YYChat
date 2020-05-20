package com.yy.yychat.enums;

public enum SearchFriendsStatus {
	
	SUCCESS(0, "OK"),
	USER_NOT_EXIST(1, "User does not exist..."),
	NOT_YOURSELF(2, "You cannot add yourself..."),
	ALREADY_FRIENDS(3, "This user is already your friend...");
	
	public final int status;
	public final String msg;
	
	SearchFriendsStatus(int status, String msg){
		this.status = status;
		this.msg = msg;
	}
	
	public int getStatus() {
		return status;
	}  
	
	public static String getMsgByKey(int status) {
		for (SearchFriendsStatus type : SearchFriendsStatus.values()) {
			if (type.getStatus() == status) {
				return type.msg;
			}
		}
		return null;
	}
	
}
