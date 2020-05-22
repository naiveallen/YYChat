package com.yy.yychat.service;

import com.yy.yychat.dao.FriendDao;
import com.yy.yychat.dao.UserDao;
import com.yy.yychat.enums.FriendRequestOpType;
import com.yy.yychat.enums.SearchFriendsStatus;
import com.yy.yychat.pojo.Friends;
import com.yy.yychat.pojo.FriendsRequest;
import com.yy.yychat.pojo.User;
import com.yy.yychat.pojo.vo.FriendRequestVO;
import com.yy.yychat.pojo.vo.MyFriendsVO;
import com.yy.yychat.utils.MD5Utils;
import com.yy.yychat.utils.QRCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Value("${qrcode-upload-path}")
    String qrCodePath;

    @Override
    public boolean queryUsernameIsExist(String username) {
        User user = userDao.findByUsername(username);
        return user != null;
    }

    @Override
    public User queryUserByUsername(String username) {
        User user = userDao.findByUsername(username);
        return user;
    }


    @Override
    public User login(String username, String password) {
        User user = userDao.queryUserByUsernameAndPassword(username, password);
        return user;
    }


    @Override
    public User register(User user) {

        user.setNickname(user.getUsername());
        user.setAvatar("");
        user.setAvatarThumbnail("");

        String qrCodeFileName = qrCodePath + user.getUsername() + "_qrcode.png";
        qrCodeUtils.createQRCode(qrCodeFileName, "username:" + user.getUsername());
        user.setQrcode("qrcodes/" + user.getUsername() + "_qrcode.png");

        try {
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        User res = userDao.insertUser(user);
        return res;
    }


    @Override
    public User updateUserInfo(User user) {
        return userDao.updateUser(user);
    }


    @Override
    public int preSearchFriend(int myUserId, String friendUsername) {
        User friend = userDao.findByUsername(friendUsername);
        if (friend == null) {
            return SearchFriendsStatus.USER_NOT_EXIST.status;
        }

        if (friend.getId() == myUserId) {
            return SearchFriendsStatus.NOT_YOURSELF.status;
        }

        Friends friends = friendDao.searchFriendById(myUserId, friend.getId());
        if (friends != null) {
            return SearchFriendsStatus.ALREADY_FRIENDS.status;
        }

        return SearchFriendsStatus.SUCCESS.status;
    }


    @Override
    public void sendFriendRequest(int myUserId, String friendUsername) {
        User friend = userDao.findByUsername(friendUsername);

        FriendsRequest res = friendDao.searchFriendRequestById(myUserId, friend.getId());
        if (res != null) {
            return;
        }

        FriendsRequest friendsRequest = new FriendsRequest();
        friendsRequest.setSenderId(myUserId);
        friendsRequest.setAccepterId(friend.getId());
        friendsRequest.setRequestTime(new Date());
        friendDao.insertFriendRequest(friendsRequest);

    }

    @Override
    public List<FriendRequestVO> queryFreiendRequest(int accepterId) {
        List<FriendRequestVO> res = friendDao.queryFriendRequestList(accepterId);
        return res;
    }

    @Override
    public void handleFriendRequest(int accepterId, int senderId, int opType) {
        if (opType == FriendRequestOpType.ACCEPT.type) {
            // add two records in friends table
            Friends friend1 = new Friends();
            Friends friend2 = new Friends();
            friend1.setUserId(accepterId);
            friend1.setFriendId(senderId);
            friend2.setUserId(senderId);
            friend2.setFriendId(accepterId);

            friendDao.insertFriends(friend1);
            friendDao.insertFriends(friend2);
        }

        // delete record in friend_request table
        friendDao.deleteFriendRequest(accepterId, senderId);
        friendDao.deleteFriendRequest(senderId, accepterId);

    }

    @Override
    public List<MyFriendsVO> queryMyFriends(int userId) {
        List<MyFriendsVO> res = friendDao.queryMyFriends(userId);
        return res;
    }





}
