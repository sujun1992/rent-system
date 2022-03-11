package com.rent.system.audit.service.impl;

import com.rent.system.audit.model.AuditHouseRequest;
import com.rent.system.audit.model.AuditRentRequest;
import com.rent.system.audit.service.AuditService;
import com.rent.system.common.CommonHttpResponse;
import com.rent.system.house.dao.HouseDao;
import com.rent.system.house.dao.entity.HouseEntity;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

  private HouseDao houseDao;

  @Autowired
  public void setHouseDao(HouseDao houseDao) {
    this.houseDao = houseDao;
  }

  @Override
  public ResponseEntity<CommonHttpResponse<String>> houseAudit(AuditHouseRequest request) {
    Optional<HouseEntity> optional = houseDao.findById(request.getHouseId());
    if (optional.isPresent()) {
      HouseEntity entity = optional.get();
      entity.setHouseAuditStatus(request.isPass() ? 1 : 2);
      houseDao.save(entity);
      return CommonHttpResponse.ok("success");
    }
    return CommonHttpResponse.exception(60002, "未找到对应房屋信息！");
  }

  @Override
  public ResponseEntity<CommonHttpResponse<String>> rentAudit(AuditRentRequest request) {
    Optional<HouseEntity> optional = houseDao.findById(request.getHouseId());
    if (optional.isPresent()) {
      HouseEntity entity = optional.get();
      entity.setHouseRentStatus(request.isPass() ? 1 : 0);
      houseDao.save(entity);
      return CommonHttpResponse.ok("success");
    }
    return CommonHttpResponse.exception(60002, "未找到对应房屋信息！");
  }
}
