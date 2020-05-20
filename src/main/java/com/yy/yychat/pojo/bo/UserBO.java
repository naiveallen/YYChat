package com.yy.yychat.pojo.bo;

public class UserBO {
    private Integer id;
    private String avatarData;
    private String nickname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatarData() {
        return avatarData;
    }

    public void setAvatarData(String avatarData) {
        this.avatarData = avatarData;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
