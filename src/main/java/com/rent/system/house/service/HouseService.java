package com.rent.system.house.service;


import com.rent.system.common.CommonHttpResponse;
import com.rent.system.house.model.HouseAddRequestBody;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rent.system.house.model.HouseBaseInfo;
import com.rent.system.house.model.HouseListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface HouseService {

  ResponseEntity<CommonHttpResponse<String>> addHouse(HouseAddRequestBody body, MultipartFile houseType, MultipartFile housePicture);

  void getHouseType(String houseId, HttpServletResponse response);

  void getHousePicture(String houseId, HttpServletResponse response);

  ResponseEntity<CommonHttpResponse<String>> delHouse(String houseId);

  ResponseEntity<CommonHttpResponse<HouseListResponse>> getHouseList(int page, int size, String area, boolean share, int rentMinNum, int rentMaxNum, HttpSession session);

  ResponseEntity<CommonHttpResponse<HouseBaseInfo>> getHouse(String houseId);
}
