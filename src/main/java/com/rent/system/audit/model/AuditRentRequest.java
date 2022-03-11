package com.rent.system.audit.model;

import lombok.Data;

@Data
public class AuditRentRequest {

  private String houseId;
  private String userId;
  private boolean pass;
}
