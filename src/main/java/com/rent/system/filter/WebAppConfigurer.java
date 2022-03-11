package com.rent.system.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

  @Autowired
  private LoginHandlerInterceptorAdapter loginHandlerInterceptorAdapter;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginHandlerInterceptorAdapter)
        .addPathPatterns("/**")
        .excludePathPatterns("/actuator/health")
        .excludePathPatterns("/login")
        .excludePathPatterns("/register");
    WebMvcConfigurer.super.addInterceptors(registry);
  }
}
