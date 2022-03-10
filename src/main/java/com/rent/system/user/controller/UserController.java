package com.rent.system.user.controller;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.user.model.UserDetailInfo;
import com.rent.system.user.model.UserUpdateRequestBody;
import com.rent.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 修改用户信息
     *
     * @return
     */
    @PutMapping
    public ResponseEntity<CommonHttpResponse<String>> updateUser(@RequestBody UserUpdateRequestBody body) {
        return userService.updateUser(body);
    }

    @GetMapping
    public ResponseEntity<CommonHttpResponse<UserDetailInfo>> getUserDetail(HttpSession session) {
        return userService.getUserDetail(session);
    }
}
