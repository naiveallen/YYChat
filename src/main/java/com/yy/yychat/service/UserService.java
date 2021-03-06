package com.yy.yychat.service;

import com.yy.yychat.netty.ChatMsg;
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

	public int saveMsg(ChatMsg chatMsg);

	public void updateMsgSigned(List<Integer> msgIdList);

}
