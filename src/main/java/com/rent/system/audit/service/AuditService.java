package com.rent.system.audit.service;


import com.rent.system.audit.model.AuditHouseRequest;
import com.rent.system.audit.model.AuditRentRequest;
import com.rent.system.common.CommonHttpResponse;
import org.springframework.http.ResponseEntity;

public interface AuditService {

  ResponseEntity<CommonHttpResponse<String>> houseAudit(AuditHouseRequest request);

  ResponseEntity<CommonHttpResponse<String>> rentAudit(AuditRentRequest request);
}
