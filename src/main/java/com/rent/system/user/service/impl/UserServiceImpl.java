package com.rent.system.user.service.impl;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.user.dao.UserDao;
import com.rent.system.user.dao.entity.UserEntity;
import com.rent.system.user.model.UserDetailInfo;
import com.rent.system.user.model.UserUpdateRequestBody;
import com.rent.system.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<CommonHttpResponse<String>> updateUser(UserUpdateRequestBody body) {
        Optional<UserEntity> optional = userDao.findById(body.getUserId());
        if (optional.isPresent()) {
            UserEntity userEntity = optional.get();
            userEntity.setPassword(passwordEncoder.encode(body.getPassword()));
            userEntity.setNickname(body.getNickname());
            userEntity.setIdCard(body.getIdCard());
            userEntity.setName(body.getName());
            userEntity.setPhone(body.getPhone());
            userEntity.setProfession(body.getProfession());
            userEntity.setRevenue(body.getRevenue());
            userEntity.setSex(body.getSex());
            userDao.saveAndFlush(userEntity);
        }
        return CommonHttpResponse.ok("success");
    }

    @Override
    public ResponseEntity<CommonHttpResponse<UserDetailInfo>> getUserDetail(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        Optional<UserEntity> optional = userDao.findById(userId);
        UserDetailInfo detailInfo = new UserDetailInfo();
        if (optional.isPresent()) {
            UserEntity userEntity = optional.get();
            BeanUtils.copyProperties(userEntity, detailInfo);
        }
        return CommonHttpResponse.ok(detailInfo);
    }
}
