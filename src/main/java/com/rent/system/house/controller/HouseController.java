package com.rent.system.house.controller;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.house.model.HouseAddRequestBody;
import com.rent.system.house.model.HouseBaseInfo;
import com.rent.system.house.model.HouseListResponse;
import com.rent.system.house.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    /**
     * 获取房型图片
     *
     * @param houseId
     * @param response
     */
    @GetMapping("/{houseId}/houseType")
    public void getHouseType(@PathVariable String houseId, HttpServletResponse response) {
        houseService.getHouseType(houseId, response);
    }

    /**
     * 房屋的照片
     *
     * @param houseId
     * @param response
     */
    @GetMapping("/{houseId}/housePicture")
    public void getHousePicture(@PathVariable String houseId, HttpServletResponse response) {
        houseService.getHousePicture(houseId, response);
    }

    @DeleteMapping("/{houseId}")
    public ResponseEntity<CommonHttpResponse<String>> delHouse(@PathVariable String houseId) {
        return houseService.delHouse(houseId);
    }

    @GetMapping
    public ResponseEntity<CommonHttpResponse<HouseListResponse>> getHouseList(@RequestParam(defaultValue = "1", required = false) int page,
                                                                              @RequestParam(defaultValue = "10", required = false) int size,
                                                                              @RequestParam(required = false) String area,
                                                                              @RequestParam(required = false, defaultValue = "true") boolean share,
                                                                              @RequestParam(required = false) int rentMinNum,
                                                                              @RequestParam(required = false) int rentMaxNum,
                                                                              HttpSession session) {
        return houseService.getHouseList(page, size, area, share, rentMinNum, rentMaxNum, session);
    }

    @GetMapping("/{houseId}")
    public ResponseEntity<CommonHttpResponse<HouseBaseInfo>> getHouse(@PathVariable String houseId) {
       return houseService.getHouse(houseId);
    }
}
