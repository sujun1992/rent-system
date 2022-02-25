package com.rent.system.house.dao;


import com.rent.system.house.dao.entity.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HouseDao extends JpaRepository<HouseEntity, String>,
    JpaSpecificationExecutor<HouseEntity> {

}
