package com.rent.system.login.service;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.login.model.LoginRequestBody;
import com.rent.system.login.model.RegisterUserRequestBody;
import org.springframework.http.ResponseEntity;

/**
 * @author jun.su <jun.su@mobvoi.com>
 * @since 2022/2/28 21:55
 */
public interface LoginService {

  ResponseEntity<CommonHttpResponse<String>> register(RegisterUserRequestBody body);

  ResponseEntity<CommonHttpResponse<String>> login(LoginRequestBody body);
}
