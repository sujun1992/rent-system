package com.rent.system.login.model;

import lombok.Data;

/**
 * @author jun.su <jun.su@mobvoi.com>
 * @since 2022/2/28 21:56
 */
@Data
public class LoginRequestBody {

  private String account;
  private String password;
}
