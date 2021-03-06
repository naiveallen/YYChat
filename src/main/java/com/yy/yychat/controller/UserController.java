package com.yy.yychat.controller;

import com.yy.yychat.enums.FriendRequestOpType;
import com.yy.yychat.enums.SearchFriendsStatus;
import com.yy.yychat.pojo.User;
import com.yy.yychat.pojo.bo.UserBO;
import com.yy.yychat.pojo.vo.FriendRequestVO;
import com.yy.yychat.pojo.vo.MyFriendsVO;
import com.yy.yychat.pojo.vo.UserVO;
import com.yy.yychat.service.UserService;
import com.yy.yychat.utils.FileUtils;
import com.yy.yychat.utils.MD5Utils;
import com.yy.yychat.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${avatar-upload-path}")
    String filePath;

    @PostMapping("/registerOrLogin")
    public Result registerOrLogin(@RequestBody User user) {
        System.out.println("Post /registerOrLogin");
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())) {
            return Result.errorMsg("Username or password cannot be empty...");
        }

        boolean userIsExist = userService.queryUsernameIsExist(user.getUsername());
        User res = null;
        if (userIsExist) {
            // login
            try {
                res = userService.login(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("MD5 error...");
                return Result.errorMsg("Unknown error...");
            }
            if (res == null) {
                return Result.errorMsg("Username or password is wrong...");
            }
        } else {
            // register
            res = userService.register(user);
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(res, userVO);

        return Result.ok(userVO);
    }

    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestBody UserBO userBO) {
        System.out.println("Post /uploadAvatar");

        Random random = new Random();

        // get the avatar base64 from front end
        String base64 = userBO.getAvatarData();
        String fileName = userBO.getId() + "_" + random.nextInt(1000000);

        try {
            FileUtils.base64ToFile(filePath + fileName, base64);
            System.out.println("Saved avatar.");
            FileUtils.getThumbnail(filePath, fileName);
            System.out.println("Saved avatar thumbnail.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String avatarUrl = "avatars/" + fileName + ".png";
        String avatarThumbnailUrl = "avatars/" + fileName + "_80x80.png";

        User user = new User();
        user.setId(userBO.getId());
        user.setAvatar(avatarUrl);
        user.setAvatarThumbnail(avatarThumbnailUrl);

        User res = userService.updateUserInfo(user);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(res, userVO);

        return Result.ok(userVO);
    }

    @PostMapping("/nickname")
    public Result setNickname(@RequestBody UserBO userBO) {
        System.out.println("Post /nickname");
        User user = new User();
        user.setId(userBO.getId());
        user.setNickname(userBO.getNickname());

        User res = userService.updateUserInfo(user);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(res, userVO);

        return Result.ok(userVO);
    }


    @GetMapping("/search")
    public Result searchFriend(@RequestParam("myUserId") Integer myUserId,
                               @RequestParam("friendUsername") String friendUsername) {
        System.out.println("Get /search");
        if (StringUtils.isBlank(friendUsername)) {
            return Result.errorMsg("Cannot be empty...");
        }

        int myId = myUserId;
        int status = userService.preSearchFriend(myId, friendUsername);
        if (status == SearchFriendsStatus.SUCCESS.status) {
            User friend = userService.queryUserByUsername(friendUsername);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(friend, userVO);
            return Result.ok(friend);
        } else {
            return Result.errorMsg(SearchFriendsStatus.getMsgByKey(status));
        }

    }


    @PostMapping("/addFriendRequest")
    public Result requestFriend(Integer myUserId, String friendUsername) {
        System.out.println("Post /addFriendRequest");
        if (StringUtils.isBlank(friendUsername)) {
            return Result.errorMsg("Username cannot be empty...");
        }
        int myId = myUserId;
        int status = userService.preSearchFriend(myId, friendUsername);
        if (status == SearchFriendsStatus.SUCCESS.status) {
            User friend = userService.queryUserByUsername(friendUsername);
            userService.sendFriendRequest(myId, friendUsername);
        } else {
            return Result.errorMsg(SearchFriendsStatus.getMsgByKey(status));
        }

        return Result.ok();

    }


    @GetMapping("/queryFriendRequest")
    public Result requestFriend(@RequestParam("userId") int accepterId) {
        System.out.println("Get /queryFriendRequest");
        List<FriendRequestVO> res = userService.queryFreiendRequest(accepterId);
        return Result.ok(res);
    }


    @PostMapping("/handleFriendRequest")
    public Result handleFriendRequest(int accepterId, int senderId, int opType) {
        System.out.println("Post /handleFriendRequest");

        if (StringUtils.isBlank(FriendRequestOpType.getMsgByType(opType))) {
            return Result.errorMsg("");
        }

        userService.handleFriendRequest(accepterId, senderId, opType);

        return Result.ok();
    }


    @GetMapping("/myFriends")
    public Result myFriends(@RequestParam("userId") int userId) {
        System.out.println("Get /myFriends");
        List<MyFriendsVO> friends = userService.queryMyFriends(userId);
        return Result.ok(friends);
    }
























}
