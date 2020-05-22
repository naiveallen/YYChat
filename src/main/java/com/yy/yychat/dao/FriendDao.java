package com.yy.yychat.dao;

import com.yy.yychat.pojo.Friends;
import com.yy.yychat.pojo.FriendsRequest;
import com.yy.yychat.pojo.vo.FriendRequestVO;
import com.yy.yychat.pojo.vo.MyFriendsVO;

import java.util.List;

public interface FriendDao {

    public Friends searchFriendById(int userId, int friendId);

    public FriendsRequest searchFriendRequestById(int userId, int friendId);

    public FriendsRequest insertFriendRequest(FriendsRequest friendsRequest);

    public void deleteFriendRequest(int userId, int friendId);

    public List<FriendRequestVO> queryFriendRequestList(int accepterId);

    public void insertFriends(Friends friends);

    public List<MyFriendsVO> queryMyFriends(int userId);






}
