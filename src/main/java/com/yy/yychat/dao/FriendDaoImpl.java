package com.yy.yychat.dao;

import com.yy.yychat.mapper.FriendsMapper;
import com.yy.yychat.pojo.Friends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Repository
public class FriendDaoImpl implements FriendDao {

    @Autowired
    private FriendsMapper friendsMapper;

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






}
