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
    address = entity.getAddress();
    houseAuditStatus = entity.getHouseAuditStatus();
    houseRentStatus = entity.getHouseRentStatus();
    houseTenant = entity.getHouseTenant();
  }

  private String area;
  private int shareNum;
  private int rentNum;
  private String rentalTime;
  private String housePicture;
  private String houseType;
  private String houseDesc;
  private String houseId;
  private String address;
  private String houseTenant;
  private int houseRentStatus;
  private int houseAuditStatus;
  private LodgerInfo lodger;
}
