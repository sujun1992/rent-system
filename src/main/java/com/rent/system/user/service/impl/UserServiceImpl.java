package com.rent.system.user.service.impl;

import com.rent.system.common.CommonHttpResponse;
import com.rent.system.user.dao.UserDao;
import com.rent.system.user.dao.entity.UserEntity;
import com.rent.system.user.dao.entity.UserEntity_;
import com.rent.system.user.model.UserDetailInfo;
import com.rent.system.user.model.UserListResponse;
import com.rent.system.user.model.UserUpdateRequestBody;
import com.rent.system.user.service.UserService;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserDao userDao;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public ResponseEntity<CommonHttpResponse<String>> updateUser(UserUpdateRequestBody body) {
    Optional<UserEntity> optional = userDao.findById(body.getUserId());
    if (optional.isPresent()) {
      UserEntity userEntity = optional.get();
      if (!userEntity.getPassword().equalsIgnoreCase(body.getPassword())) {
        userEntity.setPassword(passwordEncoder.encode(body.getPassword()));
      }
      userEntity.setNickname(body.getNickname());
      userEntity.setIdCard(body.getIdCard());
      userEntity.setName(body.getName());
      userEntity.setPhone(body.getPhone());
      userEntity.setProfession(body.getProfession());
      userEntity.setRevenue(body.getRevenue());
      userEntity.setSex(body.getSex());
      userDao.saveAndFlush(userEntity);
    }
    return CommonHttpResponse.ok("success");
  }

  @Override
  public ResponseEntity<CommonHttpResponse<UserDetailInfo>> getUserDetail(HttpSession session) {
    String userId = (String) session.getAttribute("userId");
    Optional<UserEntity> optional = userDao.findById(userId);
    UserDetailInfo detailInfo = new UserDetailInfo();
    if (optional.isPresent()) {
      UserEntity userEntity = optional.get();
      BeanUtils.copyProperties(userEntity, detailInfo);
    }
    return CommonHttpResponse.ok(detailInfo);
  }

  @Override
  public ResponseEntity<CommonHttpResponse<UserListResponse>> getUserList(int page, int size,
      Integer type, HttpSession session) {
    int userType = Integer.parseInt(String.valueOf(session.getAttribute("type")));
    if (userType != 2) {
      return CommonHttpResponse.exception(60003, "没有权限！");
    }
    Pageable pageable = PageRequest
        .of(page - 1, size, Sort.Direction.DESC, UserEntity_.ACCOUNT);
    Page<UserEntity> entities = userDao.findAll((root, query, criteriaBuilder) -> {
      CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get(UserEntity_.TYPE));
      if (type == null) {
        in.value(0);
        in.value(1);
      } else {
        in.value(type);
      }
      return criteriaBuilder.and(in);
    }, pageable);
    UserListResponse response = new UserListResponse();
    response.setTotal(entities.getTotalElements());
    response.setList(entities.getContent().stream().map(entity -> {
      UserDetailInfo detailInfo = new UserDetailInfo();
      BeanUtils.copyProperties(entity, detailInfo);
      return detailInfo;
    }).collect(Collectors.toList()));
    return CommonHttpResponse.ok(response);
  }
}
