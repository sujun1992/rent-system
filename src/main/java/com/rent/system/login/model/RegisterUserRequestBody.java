package com.rent.system.login.model;

import lombok.Data;


@Data
public class RegisterUserRequestBody {

  private String account;

  private String password;

  private String nickname;
}
