package com.rent.system.house.service;


import com.rent.system.common.CommonHttpResponse;
import com.rent.system.house.model.HouseAddRequestBody;
import com.rent.system.house.model.HouseAgreeRequest;
import com.rent.system.house.model.HouseRequest;
import com.rent.system.house.model.HouseTenantInfo;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rent.system.house.model.HouseBaseInfo;
import com.rent.system.house.model.HouseListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface HouseService {

  ResponseEntity<CommonHttpResponse<String>> addHouse(HouseAddRequestBody body,
      HttpSession session);

  void getHouseType(String houseId, HttpServletResponse response);

  void getHousePicture(String houseId, HttpServletResponse response);

  ResponseEntity<CommonHttpResponse<String>> delHouse(String houseId);

  ResponseEntity<CommonHttpResponse<HouseListResponse>> getHouseList(int page, int size, String area, Boolean share, int rentMinNum, int rentMaxNum, HttpSession session);

  ResponseEntity<CommonHttpResponse<HouseBaseInfo>> getHouse(String houseId);

  ResponseEntity<CommonHttpResponse<List<HouseBaseInfo>>> currentRentHouse(HttpSession session);

  ResponseEntity<CommonHttpResponse<String>> leaseRenewal(String houseId, int type, int rentNum,
      int rentTime, HttpSession session);

  ResponseEntity<CommonHttpResponse<String>> updateHouse(String houseId, HouseAddRequestBody body);

  ResponseEntity<CommonHttpResponse<List<HouseTenantInfo>>> rent(HttpSession session);

  ResponseEntity<CommonHttpResponse<String>> picture(MultipartFile picture);

  ResponseEntity<CommonHttpResponse<String>> agree(HouseAgreeRequest request);

  ResponseEntity<CommonHttpResponse<String>> houseRequest(HouseRequest request, HttpSession session);

  ResponseEntity<CommonHttpResponse<Map<Integer, List<HouseBaseInfo>>>> tenantAuditInfo(HttpSession session);

    ResponseEntity<CommonHttpResponse<String>> adminAgree(HouseAgreeRequest request);

  ResponseEntity<CommonHttpResponse<Map<Integer, List<HouseBaseInfo>>>> adminAuditInfo(HttpSession session);

  ResponseEntity<CommonHttpResponse<List<HouseBaseInfo>>> getRentHouse(HttpSession session);

  ResponseEntity<CommonHttpResponse<List<HouseBaseInfo>>> getOwnerRentHouse(HttpSession session);
}
