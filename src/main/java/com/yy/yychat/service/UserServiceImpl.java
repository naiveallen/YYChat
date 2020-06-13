package com.yy.yychat.service;

import com.yy.yychat.dao.FriendDao;
import com.yy.yychat.dao.MsgDao;
import com.yy.yychat.dao.UserDao;
import com.yy.yychat.enums.FriendRequestOpType;
import com.yy.yychat.enums.MsgActionEnum;
import com.yy.yychat.enums.MsgSignFlagEnum;
import com.yy.yychat.enums.SearchFriendsStatus;
import com.yy.yychat.netty.ChatMsg;
import com.yy.yychat.netty.DataContent;
import com.yy.yychat.netty.UserChannelRel;
import com.yy.yychat.pojo.Friends;
import com.yy.yychat.pojo.FriendsRequest;
import com.yy.yychat.pojo.Message;
import com.yy.yychat.pojo.User;
import com.yy.yychat.pojo.vo.FriendRequestVO;
import com.yy.yychat.pojo.vo.MyFriendsVO;
import com.yy.yychat.utils.JsonUtils;
import com.yy.yychat.utils.MD5Utils;
import com.yy.yychat.utils.QRCodeUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
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
    private MsgDao msgDao;

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
        System.out.println("QRCode generated.");

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

        // Send latest friends list to sender
        Channel sendChannel = UserChannelRel.get(senderId);
        if (sendChannel != null) {
            DataContent dataContent = new DataContent();
            dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);

            sendChannel.writeAndFlush(new TextWebSocketFrame(
                            JsonUtils.objectToJson(dataContent)));
        }

    }

    @Override
    public List<MyFriendsVO> queryMyFriends(int userId) {
        List<MyFriendsVO> res = friendDao.queryMyFriends(userId);
        return res;
    }

    @Override
    public int saveMsg(ChatMsg chatMsg) {
        Message msg = new Message();
        msg.setAccpterId(chatMsg.getReceiverId());
        msg.setSenderId(chatMsg.getSenderId());
        msg.setCreateTime(new Date());
        msg.setIsRead(MsgSignFlagEnum.unsign.type);
        msg.setMsg(chatMsg.getMsg());
        int id = msgDao.insertMsg(msg);

        return id;
    }

    @Override
    public void updateMsgSigned(List<Integer> msgIdList) {
        msgDao.batchUpdateMsgRead(msgIdList);
    }



}
