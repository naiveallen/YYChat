package com.yy.yychat.pojo.vo;

public class MyFriendsVO {
    private int friendUserId;
    private String friendUsername;
    private String friendAvatar;
    private String friendNickname;
    
	public int getFriendUserId() {
		return friendUserId;
	}
	public void setFriendUserId(int friendUserId) {
		this.friendUserId = friendUserId;
	}
	public String getFriendUsername() {
		return friendUsername;
	}
	public void setFriendUsername(String friendUsername) {
		this.friendUsername = friendUsername;
	}
	public String getFriendNickname() {
		return friendNickname;
	}
	public void setFriendNickname(String friendNickname) {
		this.friendNickname = friendNickname;
	}

	public String getFriendAvatar() {
		return friendAvatar;
	}

	public void setFriendAvatar(String friendAvatar) {
		this.friendAvatar = friendAvatar;
	}
}