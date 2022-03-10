package com.rent.system.house.model;

import com.rent.system.common.TimeUtil;
import com.rent.system.house.dao.entity.HouseEntity;
import lombok.Data;

@Data
public class HouseBaseInfo {

    public HouseBaseInfo(HouseEntity entity) {
        area = entity.getArea();
        shareNum = entity.getShareNum();
        rentNum = entity.getRentNum();
        rentalTime = TimeUtil.formatterTime(entity.getRentalTime());
        houseDesc = entity.getHouseDesc();
        houseId = entity.getHouseId();
        houseAuditStatus = entity.getHouseAuditStatus();
        houseRentStatus = entity.getHouseRentStatus();
    }

    private String area;
    private int shareNum;
    private int rentNum;
    private String rentalTime;
    private String houseDesc;
    private String houseId;
    private int houseRentStatus;
    private int houseAuditStatus;
}
