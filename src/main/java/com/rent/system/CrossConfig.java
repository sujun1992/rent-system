
package com.rent.system;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class CrossConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedHeaders("*")
        .allowedOriginPatterns("*")
        .allowCredentials(true)
        .exposedHeaders("*")
        .allowedMethods("POST", "GET", "DELETE", "PUT", "PATCH");
  }
}
