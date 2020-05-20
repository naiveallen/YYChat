package com.yy.yychat.dao;

import com.yy.yychat.mapper.UserMapper;
import com.yy.yychat.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        Example userExample = new Example(User.class);
        Example.Criteria criteria = userExample.createCriteria();

        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);

        User result = userMapper.selectOneByExample(userExample);

        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User findByUsername(String username) {
        Example userExample = new Example(User.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        User result = userMapper.selectOneByExample(userExample);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User insertUser(User user) {
        userMapper.insert(user);
        User res = findByUsername(user.getUsername());
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return findById(user.getId());
    }










}
