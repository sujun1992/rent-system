package com.rent.system.user.model;

import lombok.Data;

import java.util.List;

@Data
public class UserListResponse {
    private long total;
    private List<UserDetailInfo> list;
}
