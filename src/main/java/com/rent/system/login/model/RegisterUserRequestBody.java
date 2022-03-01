package com.rent.system.login.model;

import lombok.Data;

/**
 * @author jun.su <jun.su@mobvoi.com>
 * @since 2022/2/28 21:53
 */
@Data
public class RegisterUserRequestBody {

  private String account;

  private String password;

  private String nickname;
}
