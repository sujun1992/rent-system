package com.rent.system.house.model;

import lombok.Data;

@Data
public class HouseAgreeRequest {

  private String userId;
  private String houseId;
  private boolean pass = true;
  private int type;
}
