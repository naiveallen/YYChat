package com.yy.yychat.service;

import com.yy.yychat.pojo.User;
import com.yy.yychat.pojo.vo.FriendRequestVO;
import com.yy.yychat.pojo.vo.MyFriendsVO;

import java.util.List;

public interface UserService {

	public boolean queryUsernameIsExist(String username);

	public User queryUserByUsername(String username);

	public User login(String username, String pwd);

	public User register(User user);

	public User updateUserInfo(User user);

	public int preSearchFriend(int myUserId, String friendUsername);

	public void sendFriendRequest(int myUserId, String friendUsername);

	public List<FriendRequestVO> queryFreiendRequest(int accepterId);

	public void handleFriendRequest(int accepterId, int senderId, int opType);

	public List<MyFriendsVO> queryMyFriends(int userId);





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
