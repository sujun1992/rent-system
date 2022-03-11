package com.rent.system.house.dao;


import com.rent.system.house.dao.entity.HouseEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HouseDao extends JpaRepository<HouseEntity, String>,
    JpaSpecificationExecutor<HouseEntity> {

  Optional<HouseEntity> findByHouseTenant(String tenant);

  List<HouseEntity> findByHouseOwnerAndHouseRentStatusIn(String id, List<Integer> status);

  Optional<HouseEntity> findByHouseTenantAndHouseId(String tenant, String houseId);
}
