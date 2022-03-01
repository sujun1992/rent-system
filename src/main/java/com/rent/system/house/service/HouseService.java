package com.rent.system.house.service;


import com.rent.system.common.CommonHttpResponse;
import com.rent.system.house.model.HouseAddRequestBody;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface HouseService {

  ResponseEntity<CommonHttpResponse<String>> addHouse(HouseAddRequestBody body, MultipartFile houseType, MultipartFile housePicture);

  void getHouseType(String houseId, HttpServletResponse response);

  void getHousePicture(String houseId, HttpServletResponse response);

  ResponseEntity<CommonHttpResponse<String>> delHouse(String houseId);
}
