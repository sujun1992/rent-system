package com.rent.system.common;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Data
public class CommonHttpResponse<T> {

  private static final int OK = 200;
  private int code;
  private String message;
  private T data;


  public static <T> ResponseEntity<CommonHttpResponse<T>> ok(T data) {
    CommonHttpResponse<T> response = new CommonHttpResponse<>();
    response.code = OK;
    response.data = data;
    return ResponseEntity.ok(response);
  }

  public static <T> ResponseEntity<CommonHttpResponse<T>> exception(int code, String message) {
    return exception(code, message, null);
  }

  public static <T> ResponseEntity<CommonHttpResponse<T>> exception(int code, String message,
      T data) {
    CommonHttpResponse<T> response = new CommonHttpResponse<>();
    response.code = code;
    response.message = message;
    response.data = data;
    return ResponseEntity.ok(response);
  }

  public static ResponseEntity status(HttpStatus httpStatus) {
    return ResponseEntity.status(httpStatus).build();
  }

}
