package com.yy.yychat.netty;

import java.io.Serializable;

public class ChatMsg implements Serializable {

	private static final long serialVersionUID = 3611169682695799175L;
	
	private int senderId;
	private int receiverId;
	private String msg;
	private int msgId;
	
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	
}
