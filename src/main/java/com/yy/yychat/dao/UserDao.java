package com.yy.yychat.dao;

import com.yy.yychat.pojo.User;

public interface UserDao {

    public User queryUserByUsernameAndPassword(String username, String password);

    public User findById(Integer id);

    public User findByUsername(String username);

    public User insertUser(User user);

    public User updateUser(User user);




}
