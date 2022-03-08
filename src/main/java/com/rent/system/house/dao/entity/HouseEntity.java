package com.rent.system.house.dao.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity(name = "house_info")
public class HouseEntity {

  @Id
  @GeneratedValue(generator = "uuidGenerator")
  @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "house_id")
  private String houseId;
  /**
   * 地区
   */
  @Column(name = "area")
  private String area;
  /**
   * 房租
   */
  @Column(name = "rent_num")
  private int rentNum;
  /**
   * 租房时间
   */
  @Column(name = "rental_time")
  private long rentalTime;
  /**
   * 是否合租（几人）1表示不合租，只有一个人
   */
  @Column(name = "share_num")
  private int shareNum;

  /**
   * 房型
   */
  @Column(name = "house_type")
  private String houseType;

  /**
   * 房屋照片
   */
  @Column(name = "house_picture")
  private String housePicture;

  /**
   * 介绍
   */
  @Column(name = "house_desc")
  private String houseDesc;

  /**
   * 房屋出租状态:0：待租；1：出租中；2：房屋待续租
   */
  @Column(name = "house_rent_status")
  private int houseRentStatus;

  /**
   * 房屋审核状态:0：未审核；1：审核通过
   */
  @Column(name = "house_audit_status")
  private int houseAuditStatus;
}
