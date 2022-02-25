package com.rent.system.user.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity(name = "user_info")
public class UserEntity {

  @Id
  @GeneratedValue(generator = "uuidGenerator")
  @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "user_id")
  private String userId;

  /**
   * 用户名
   */
  @Column(name = "account")
  private String account;

  /**
   * 密码
   */
  @Column(name = "password")
  private String password;

  /**
   * 身份证
   */
  @Column(name = "id_card")
  private String idCard;

  /**
   * 职业
   */
  @Column(name = "profession")
  private String profession;

  /**
   * 收入
   */
  @Column(name = "revenue")
  private int revenue;

  /**
   * 姓名
   */
  @Column(name = "name")
  private String name;

  /**
   * 昵称
   */
  @Column(name = "nickname")
  private String nickname;

  /**
   * 电话号码
   */
  @Column(name = "phone")
  private String phone;

  /**
   * 性别：0：男；1：女
   */
  @Column(name = "sex")
  private int sex;

  /**
   * 类别：0：房客；1：房东；2：管理员
   */
  @Column(name = "type")
  private int type;
}
