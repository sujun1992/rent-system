package com.rent.system.house.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
     * 房屋出租状态:0：待租；1：出租中；2：房屋待续租；3：房客发起房屋续租；4：房客发起租房申请；5：房东同意续租申请；6:退租
     */
    @Column(name = "house_rent_status")
    private int houseRentStatus;

    /**
     * 房屋出租状态:0：房客发起房屋续租；1：房客发起租房申请；2：退租；3：待租；4：出租中
     */
    @Column(name = "house_rent_action")
    private int houseRentAction;

    /**
     * 房屋审核状态:0：未审核；1：审核通过；2：审核不通过
     */
    @Column(name = "house_audit_status")
    private int houseAuditStatus;

    /**
     * 房屋所属人
     */
    @Column(name = "house_owner")
    private String houseOwner;

    /**
     * 房屋承租人
     */
    @Column(name = "house_tenant")
    private String houseTenant;

    @Column(name = "address")
    private String address;

    @Column(name = "rent_duration")
    private int rentDuration;

    @Column(name = "room_type")
    private String roomType;
}
