package com.yy.yychat.controller;

import com.yy.yychat.pojo.User;
import com.yy.yychat.pojo.vo.UserVO;
import com.yy.yychat.service.UserService;
import com.yy.yychat.utils.MD5Utils;
import com.yy.yychat.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerOrLogin")
    public Result registerOrLogin(@RequestBody User user) {
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
}
