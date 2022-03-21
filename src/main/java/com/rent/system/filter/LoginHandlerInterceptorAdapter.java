package com.rent.system.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rent.system.user.dao.UserDao;
import com.rent.system.user.dao.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
@Slf4j
public class LoginHandlerInterceptorAdapter implements HandlerInterceptor {
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String userId = (String) request.getSession().getAttribute("userId");
        if (StringUtils.hasLength(userId)) {
            return true;
        } else {
            userId = request.getHeader("userId");
            if (userId != null) {
                log.info("find user id in header!");
                Optional<UserEntity> optional = userDao.findById(userId);
                if (optional.isPresent()) {
                    request.getSession().setAttribute("userId", userId);
                    request.getSession().setAttribute("account", optional.get().getAccount());
                    request.getSession().setAttribute("type", optional.get().getType());
                    log.info("set user info success!");
                    return true;
                }
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
