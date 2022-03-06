package com.rent.system.login.service;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.login.model.LoginRequestBody;
import com.rent.system.login.model.RegisterUserRequestBody;
import com.rent.system.login.model.UserLoginInfo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;


public interface LoginService {

  ResponseEntity<CommonHttpResponse<String>> register(RegisterUserRequestBody body);

  ResponseEntity<CommonHttpResponse<UserLoginInfo>> login(LoginRequestBody body, HttpSession session);
}
