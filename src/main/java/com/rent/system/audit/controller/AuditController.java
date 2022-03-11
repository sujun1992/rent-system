package com.rent.system.audit.controller;

import com.rent.system.audit.model.AuditHouseRequest;
import com.rent.system.audit.model.AuditRentRequest;
import com.rent.system.audit.service.AuditService;
import com.rent.system.common.CommonHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit")
public class AuditController {

  private AuditService auditService;

  @Autowired
  public void setAuditService(AuditService auditService) {
    this.auditService = auditService;
  }

  @PostMapping("/house")
  public ResponseEntity<CommonHttpResponse<String>> houseAudit(@RequestBody AuditHouseRequest request) {
    return auditService.houseAudit(request);
  }

  @PostMapping("/rent")
  public ResponseEntity<CommonHttpResponse<String>> rentAudit(@RequestBody AuditRentRequest request) {
    return auditService.rentAudit(request);
  }
}
