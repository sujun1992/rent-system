package com.rent.system.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoginHandlerInterceptorAdapter implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) throws Exception {
    String userId = String.valueOf(request.getSession().getAttribute("userId"));
    if (StringUtils.hasLength(userId)) {
      return true;
    } else {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return false;
    }
  }
}
