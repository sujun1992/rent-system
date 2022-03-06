package com.rent.system.user.controller;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.user.model.UserUpdateRequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 修改用户信息
     *
     * @return
     */
    @PutMapping
    public ResponseEntity<CommonHttpResponse<String>> updateUser(@RequestBody UserUpdateRequestBody body) {

    }
}
