package com.rent.system.audit.model;

import lombok.Data;

@Data
public class AuditHouseRequest {

  private String houseId;
  private boolean pass;
}
