package com.rent.system.user.service;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.user.model.UserDetailInfo;
import com.rent.system.user.model.UserListResponse;
import com.rent.system.user.model.UserUpdateRequestBody;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface UserService {

  ResponseEntity<CommonHttpResponse<String>> updateUser(UserUpdateRequestBody body);

  ResponseEntity<CommonHttpResponse<UserDetailInfo>> getUserDetail(HttpSession session);

  ResponseEntity<CommonHttpResponse<UserListResponse>> getUserList(int page, int size, Integer type,
      HttpSession session);
}
