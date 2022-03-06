package com.rent.system.login.controller;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.login.model.LoginRequestBody;
import com.rent.system.login.model.RegisterUserRequestBody;
import com.rent.system.login.model.UserLoginInfo;
import com.rent.system.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
public class LoginController {

  private LoginService loginService;

  @Autowired
  public void setLoginService(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/register")
  public ResponseEntity<CommonHttpResponse<String>> register(
      @RequestBody RegisterUserRequestBody body) {
    return loginService.register(body);
  }

  @PostMapping("/login")
  public ResponseEntity<CommonHttpResponse<UserLoginInfo>> login(
          @RequestBody LoginRequestBody body, HttpSession session) {
    return loginService.login(body,session);
  }
}
