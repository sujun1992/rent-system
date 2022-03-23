package com.rent.system.user.controller;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.user.model.UserDetailInfo;
import com.rent.system.user.model.UserListResponse;
import com.rent.system.user.model.UserUpdateRequestBody;
import com.rent.system.user.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<CommonHttpResponse<String>> updateUser(
      @RequestBody UserUpdateRequestBody body) {
    return userService.updateUser(body);
  }

  @GetMapping
  public ResponseEntity<CommonHttpResponse<UserDetailInfo>> getUserDetail(HttpSession session) {
    return userService.getUserDetail(session);
  }

  @GetMapping("/list")
  public ResponseEntity<CommonHttpResponse<UserListResponse>> getUserList(
      @RequestParam(defaultValue = "1", required = false) int page,
      @RequestParam(defaultValue = "10", required = false) int size,
      @RequestParam(required = false) Integer type,
      HttpSession session) {
    return userService.getUserList(page, size, type, session);
  }
}
