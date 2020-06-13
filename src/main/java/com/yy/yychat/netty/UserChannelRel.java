package com.yy.yychat.netty;

import java.util.HashMap;

import io.netty.channel.Channel;

public class UserChannelRel {

	private static HashMap<Integer, Channel> manager = new HashMap<>();

	public static void put(int senderId, Channel channel) {
		manager.put(senderId, channel);
	}
	
	public static Channel get(int senderId) {
		return manager.get(senderId);
	}
	
	public static void output() {
		for (HashMap.Entry<Integer, Channel> entry : manager.entrySet()) {
			System.out.println("UserId: " + entry.getKey() 
							+ ", ChannelId: " + entry.getValue().id().asLongText());
		}
	}
}
