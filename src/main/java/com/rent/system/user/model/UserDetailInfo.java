package com.rent.system.user.model;

import lombok.Data;

@Data
public class UserDetailInfo {
    private String userId;

    /**
     * 密码
     */
    private String password;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 职业
     */
    private String profession;

    /**
     * 收入
     */
    private int revenue;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 性别：0：男；1：女
     */
    private int sex;

    private String account;

    private int type;
}
