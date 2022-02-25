package com.rent.system.house.controller;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.house.model.HouseAddRequestBody;
import com.rent.system.house.service.HouseService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/house")
public class HouseController {

  private HouseService houseService;

  @Autowired
  public void setHouseService(HouseService houseService) {
    this.houseService = houseService;
  }

  @PostMapping
  public ResponseEntity<CommonHttpResponse<String>> addHouse(
      @RequestPart("request") HouseAddRequestBody body,
      @RequestPart(value = "houseType", required = false) MultipartFile houseType,
      @RequestPart(value = "housePicture", required = false) MultipartFile housePicture) {
    return houseService.addHouse(body, houseType, housePicture);
  }

  @GetMapping("/{houseId}/houseType")
  public void getHouseType(@PathVariable String houseId, HttpServletResponse response) {
    houseService.getHouseType(houseId, response);
  }
}
