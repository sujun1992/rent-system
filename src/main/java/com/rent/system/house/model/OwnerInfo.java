package com.rent.system.house.model;

import com.rent.system.user.dao.entity.UserEntity;
import lombok.Data;

@Data
public class OwnerInfo {
    private String name;
    private String phone;
    private int sex;
    private String idCard;

    public OwnerInfo(UserEntity entity) {
        name = entity.getName();
        phone = entity.getPhone();
        sex = entity.getSex();
        idCard = entity.getIdCard();
    }
}
