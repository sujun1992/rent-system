package com.rent.system.user.dao;


import com.rent.system.user.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<UserEntity, String>,
    JpaSpecificationExecutor<UserEntity> {

}
