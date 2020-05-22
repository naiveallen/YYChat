package com.yy.yychat.dao;

import com.yy.yychat.mapper.CustomMapper;
import com.yy.yychat.mapper.FriendsMapper;
import com.yy.yychat.mapper.FriendsRequestMapper;
import com.yy.yychat.pojo.Friends;
import com.yy.yychat.pojo.FriendsRequest;
import com.yy.yychat.pojo.vo.FriendRequestVO;
import com.yy.yychat.pojo.vo.MyFriendsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository
public class FriendDaoImpl implements FriendDao {

    @Autowired
    private FriendsMapper friendsMapper;

    @Autowired
    private FriendsRequestMapper friendsRequestMapper;

    @Autowired
    private CustomMapper customMapper;

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
    public FriendsRequest insertFriendRequest(FriendsRequest friendsRequest) {
        friendsRequestMapper.insert(friendsRequest);
        FriendsRequest res = searchFriendRequestById(
                friendsRequest.getSenderId(),
                friendsRequest.getAccepterId());

        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteFriendRequest(int userId, int friendId) {
        FriendsRequest friendsRequest = searchFriendRequestById(userId, friendId);
        if (friendsRequest != null) {
            friendsRequestMapper.delete(friendsRequest);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FriendRequestVO> queryFriendRequestList(int accepterId) {
        return customMapper.queryFriendRequestList(accepterId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void insertFriends(Friends friends) {
        friendsMapper.insert(friends);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<MyFriendsVO> queryMyFriends(int userId) {
        return customMapper.queryMyFriends(userId);
    }





}
