package com.rent.system.login.model;

import lombok.Data;


@Data
public class RegisterUserRequestBody {

  private String account;

  private String password;

  /**
   * 类别：0：房客；1：房东；
   */
  private int type;
}
