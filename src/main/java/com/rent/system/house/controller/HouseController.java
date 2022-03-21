package com.rent.system.house.controller;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.house.model.HouseAddRequestBody;
import com.rent.system.house.model.HouseAgreeRequest;
import com.rent.system.house.model.HouseBaseInfo;
import com.rent.system.house.model.HouseListResponse;
import com.rent.system.house.model.HouseRequest;
import com.rent.system.house.model.HouseTenantInfo;
import com.rent.system.house.service.HouseService;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @RequestBody HouseAddRequestBody body,
            HttpSession session) {
        return houseService.addHouse(body, session);
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
    public ResponseEntity<CommonHttpResponse<HouseListResponse>> getHouseList(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) Boolean share,
            @RequestParam(required = false, defaultValue = "0") int rentMinNum,
            @RequestParam(required = false, defaultValue = "0") int rentMaxNum,
            HttpSession session) {
        return houseService.getHouseList(page, size, area, share, rentMinNum, rentMaxNum, session);
    }

    @GetMapping("/{houseId}")
    public ResponseEntity<CommonHttpResponse<HouseBaseInfo>> getHouse(@PathVariable String houseId) {
        return houseService.getHouse(houseId);
    }

    @GetMapping("/currentRentHouse")
    public ResponseEntity<CommonHttpResponse<List<HouseBaseInfo>>> currentRentHouse(HttpSession session) {
        return houseService.currentRentHouse(session);
    }

    @PostMapping("/leaseRenewal")
    public ResponseEntity<CommonHttpResponse<String>> leaseRenewal(
            @RequestParam(required = false) String houseId,
            @RequestParam(required = false) int type,
            @RequestParam(required = false) int rentNum,
            HttpSession session) {
        return houseService.leaseRenewal(houseId, type, rentNum, session);
    }

    @PostMapping("/request")
    public ResponseEntity<CommonHttpResponse<String>> houseRequest(@RequestBody HouseRequest request,
                                                                   HttpSession session) {
        return houseService.houseRequest(request, session);
    }

    @PutMapping("/{houseId}")
    public ResponseEntity<CommonHttpResponse<String>> updateHouse(
            @RequestBody HouseAddRequestBody body,
            @PathVariable String houseId) {
        return houseService.updateHouse(houseId, body);
    }

    @GetMapping("/rent")
    public ResponseEntity<CommonHttpResponse<List<HouseTenantInfo>>> rent(HttpSession session) {
        return houseService.rent(session);
    }

    @PostMapping("/agree")
    public ResponseEntity<CommonHttpResponse<String>> agree(@RequestBody HouseAgreeRequest request) {
        return houseService.agree(request);
    }

    @PostMapping("/admin/agree")
    public ResponseEntity<CommonHttpResponse<String>> adminAgree(@RequestBody HouseAgreeRequest request) {
        return houseService.adminAgree(request);
    }

    @PostMapping("/picture")
    public ResponseEntity<CommonHttpResponse<String>> picture(
            @RequestPart(value = "file", required = false) MultipartFile picture) {
        return houseService.picture(picture);
    }

    @GetMapping("/tenant/auditInfo")
    public ResponseEntity<CommonHttpResponse<Map<Integer, List<HouseBaseInfo>>>> tenantAuditInfo(HttpSession session) {
        return houseService.tenantAuditInfo(session);
    }

    @GetMapping("/admin/auditInfo")
    public ResponseEntity<CommonHttpResponse<Map<Integer, List<HouseBaseInfo>>>> adminAuditInfo(HttpSession session) {
        return houseService.adminAuditInfo(session);
    }

    @GetMapping("/admin/getRentHouse")
    public ResponseEntity<CommonHttpResponse<List<HouseBaseInfo>>> getRentHouse(HttpSession session) {
        return houseService.getRentHouse(session);
    }

    @GetMapping("/owner/getRentHouse")
    public ResponseEntity<CommonHttpResponse<List<HouseBaseInfo>>> getOwnerRentHouse(HttpSession session) {
        return houseService.getOwnerRentHouse(session);
    }
}
