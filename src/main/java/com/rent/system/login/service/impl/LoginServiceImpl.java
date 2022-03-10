package com.rent.system.login.service.impl;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.login.model.LoginRequestBody;
import com.rent.system.login.model.RegisterUserRequestBody;
import com.rent.system.login.model.UserLoginInfo;
import com.rent.system.login.service.LoginService;
import com.rent.system.user.dao.UserDao;
import com.rent.system.user.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Service
public class LoginServiceImpl implements LoginService {
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
    public ResponseEntity<CommonHttpResponse<String>> register(RegisterUserRequestBody body) {
        UserEntity entity = new UserEntity();
        entity.setAccount(body.getAccount());
        entity.setType(body.getType());
        entity.setPassword(passwordEncoder.encode(body.getPassword()));
        userDao.saveAndFlush(entity);
        return CommonHttpResponse.ok("success");
    }

    @Override
    public ResponseEntity<CommonHttpResponse<UserLoginInfo>> login(LoginRequestBody body, HttpSession session) {
        Optional<UserEntity> optional = userDao.findByAccount(body.getAccount());
        if (optional.isPresent()) {
            UserEntity entity = optional.get();
            boolean matches = passwordEncoder.matches(body.getPassword(), entity.getPassword());
            if (matches) {
                session.setAttribute("userId", entity.getUserId());
                session.setAttribute("account", entity.getAccount());
                session.setAttribute("type", entity.getType());
                UserLoginInfo info = new UserLoginInfo(entity);
                return CommonHttpResponse.ok(info);
            } else {
                return CommonHttpResponse.exception(60001, "用户名或者密码不正确！");
            }
        } else {
            return CommonHttpResponse.exception(60001, "用户名或者密码不正确！");
        }
    }
}
