package com.yy.yychat.controller;

import com.yy.yychat.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class Hello {

    @ResponseBody
    @GetMapping("/hello")
    public Result hello() {
        return Result.ok("hello");
    }

}
