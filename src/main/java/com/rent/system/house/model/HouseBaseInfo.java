package com.rent.system.house.model;

import com.rent.system.house.dao.entity.HouseEntity;
import lombok.Data;

@Data
public class HouseBaseInfo {

  public HouseBaseInfo(HouseEntity entity) {
    area = entity.getArea();
    shareNum = entity.getShareNum();
    rentNum = entity.getRentNum();
    rentalTime = entity.getRentalTime();
    houseDesc = entity.getHouseDesc();
    houseId = entity.getHouseId();
    address = entity.getAddress();
    houseAuditStatus = entity.getHouseAuditStatus();
    houseRentStatus = entity.getHouseRentStatus();
    houseTenant = entity.getHouseTenant();
    houseOwner = entity.getHouseOwner();
    roomType = entity.getRoomType();
    houseRentAction = entity.getHouseRentAction();
  }

  private String area;
  private int shareNum;
  private int rentNum;
  private long rentalTime;
  private String housePicture;
  private String houseType;
  private String houseDesc;
  private String houseOwner;
  private String houseId;
  private String address;
  private String houseTenant;
  private String roomType;
  private int houseRentStatus;
  private int houseRentAction;
  private int houseAuditStatus;
  private LodgerInfo lodger;
  private OwnerInfo owner;
}
