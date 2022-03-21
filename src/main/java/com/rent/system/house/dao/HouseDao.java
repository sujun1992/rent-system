package com.rent.system.house.dao;


import com.rent.system.house.dao.entity.HouseEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HouseDao extends JpaRepository<HouseEntity, String>,
        JpaSpecificationExecutor<HouseEntity> {

    List<HouseEntity> findByHouseTenant(String tenant);

    List<HouseEntity> findByHouseTenantAndHouseRentStatus(String tenant, int status);

    List<HouseEntity> findByHouseOwnerAndHouseRentStatusIn(String id, List<Integer> status);

    Optional<HouseEntity> findByHouseTenantAndHouseId(String tenant, String houseId);

    List<HouseEntity> findByHouseRentStatusAndHouseRentActionIn(int rentStatus, List<Integer> status);

    List<HouseEntity> findByHouseRentStatus(int rentStatus);
}
