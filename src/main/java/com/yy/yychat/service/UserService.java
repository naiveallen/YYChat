package com.yy.yychat.service;

import com.yy.yychat.pojo.User;

import java.util.List;

public interface UserService {

	public boolean queryUsernameIsExist(String username);

	public User login(String username, String pwd);

	public User register(User user);

	public User updateUserInfo(User user);

//	/**
//	 * @Description: 搜索朋友的前置条件
//	 */
//	public Integer preconditionSearchFriends(String myUserId, String friendUsername);
//
//	/**
//	 * @Description: 根据用户名查询用户对象
//	 */
//	public User queryUserInfoByUsername(String username);
//
//	/**
//	 * @Description: 添加好友请求记录，保存到数据库
//	 */
//	public void sendFriendRequest(String myUserId, String friendUsername);
//
//	/**
//	 * @Description: 查询好友请求
//	 */
//	public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);
//
//	/**
//	 * @Description: 删除好友请求记录
//	 */
//	public void deleteFriendRequest(String sendUserId, String acceptUserId);
//
//	/**
//	 * @Description: 通过好友请求
//	 * 				1. 保存好友
//	 * 				2. 逆向保存好友
//	 * 				3. 删除好友请求记录
//	 */
//	public void passFriendRequest(String sendUserId, String acceptUserId);
//
//	/**
//	 * @Description: 查询好友列表
//	 */
//	public List<MyFriendsVO> queryMyFriends(String userId);
//
//	/**
//	 * @Description: 保存聊天消息到数据库
//	 */
//	public String saveMsg(ChatMsg chatMsg);
//
//	/**
//	 * @Description: 批量签收消息
//	 */
//	public void updateMsgSigned(List<String> msgIdList);
//
//	/**
//	 * @Description: 获取未签收消息列表
//	 */
//	public List<com.imooc.pojo.ChatMsg> getUnReadMsgList(String acceptUserId);
	
}
