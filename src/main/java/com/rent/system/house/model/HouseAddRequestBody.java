package com.rent.system.house.model;

import lombok.Data;

@Data
public class HouseAddRequestBody {

  private String area;
  private int rentNum;
  private long rentalTime;
  private int shareNum;
  private String houseDesc;
  private String address;
  private String houseType;
  private String roomType;
  private String housePicture;
}
