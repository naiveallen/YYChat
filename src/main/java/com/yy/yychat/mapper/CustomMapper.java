package com.yy.yychat.mapper;

import java.util.List;

import com.yy.yychat.pojo.User;
import com.yy.yychat.pojo.vo.FriendRequestVO;
import com.yy.yychat.pojo.vo.MyFriendsVO;
import com.yy.yychat.utils.MyMapper;


public interface CustomMapper extends MyMapper<User> {
	
	public List<FriendRequestVO> queryFriendRequestList(int accepterId);
	
	public List<MyFriendsVO> queryMyFriends(int userId);
	
	public void batchUpdateMsgSigned(List<String> msgIdList);
	
}
