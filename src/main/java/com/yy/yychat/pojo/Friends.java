package com.yy.yychat.pojo;

import javax.persistence.*;

public class Friends {
    @Id
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "friend_id")
    private Integer friendId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return friend_id
     */
    public Integer getFriendId() {
        return friendId;
    }

    /**
     * @param friendId
     */
    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }
}