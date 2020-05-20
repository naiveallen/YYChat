package com.yy.yychat.dao;

import com.yy.yychat.mapper.FriendsMapper;
import com.yy.yychat.mapper.FriendsRequestMapper;
import com.yy.yychat.pojo.Friends;
import com.yy.yychat.pojo.FriendsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Repository
public class FriendDaoImpl implements FriendDao {

    @Autowired
    private FriendsMapper friendsMapper;

    @Autowired
    private FriendsRequestMapper friendsRequestMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Friends searchFriendById(int userId, int friendId) {
        Example friendsExample = new Example(Friends.class);
        Example.Criteria criteria = friendsExample.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("friendId", friendId);
        Friends result = friendsMapper.selectOneByExample(friendsExample);
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public FriendsRequest searchFriendRequestById(int userId, int friendId) {
        Example friendsRequestExample = new Example(FriendsRequest.class);
        Example.Criteria criteria = friendsRequestExample.createCriteria();
        criteria.andEqualTo("senderId", userId);
        criteria.andEqualTo("accepterId", friendId);
        FriendsRequest result =
                friendsRequestMapper.selectOneByExample(friendsRequestExample);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Friends insertFriends(Friends friends) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public FriendsRequest insertFriendRequest(FriendsRequest friendsRequest) {
        friendsRequestMapper.insert(friendsRequest);
        FriendsRequest res = searchFriendRequestById(
                friendsRequest.getSenderId(),
                friendsRequest.getAccepterId());

        return res;
    }


}
