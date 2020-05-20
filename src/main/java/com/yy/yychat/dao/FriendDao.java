package com.yy.yychat.dao;

import com.yy.yychat.pojo.Friends;
import com.yy.yychat.pojo.FriendsRequest;

public interface FriendDao {

    public Friends searchFriendById(int userId, int friendId);

    public FriendsRequest searchFriendRequestById(int userId, int friendId);

    public Friends insertFriends(Friends friends);

    public FriendsRequest insertFriendRequest(FriendsRequest friendsRequest);



}
