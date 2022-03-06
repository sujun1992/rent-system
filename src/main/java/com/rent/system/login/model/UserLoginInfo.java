package com.rent.system.login.model;

import com.rent.system.user.dao.entity.UserEntity;
import lombok.Data;

@Data
public class UserLoginInfo {
    private String account;

    private String userId;

    private String nickname;

    private String name;

    private int sex;

    private int type;

    public UserLoginInfo(UserEntity entity) {
        account = entity.getAccount();
        userId = entity.getUserId();
        nickname = entity.getNickname();
        name = entity.getName();
        sex = entity.getSex();
        type = entity.getType();
    }
}
