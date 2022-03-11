package com.rent.system.house.model;

import com.rent.system.user.dao.entity.UserEntity;
import lombok.Data;

@Data
public class LodgerInfo {

  private String name;
  private String phone;
  private String idCard;
  private String profession;
  private int sex;

  public LodgerInfo(UserEntity entity) {
    name = entity.getName();
    phone = entity.getPhone();
    idCard = entity.getIdCard();
    profession = entity.getProfession();
    sex = entity.getSex();
  }
}
