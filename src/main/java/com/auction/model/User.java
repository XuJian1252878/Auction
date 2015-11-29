package com.auction.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "USER")
@Proxy(lazy = true)
public class User {

  public User() {

  }

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "increment")
  @Column(name = "id", length = 32)
  private Integer id;

  @Column(name = "userName", length = 32)
  private String userName;

  @Column(name = "age", length = 32)
  private Integer age;

  @Column(name = "addr", length = 50)
  private String address;

  @Column(name = "password", length = 16)
  private String password;

  @Transient
  private String confirmPassword;

  @Column(name = "email", length = 50)
  private String email;

  // 用户上传的头像文件在服务器中的路径信息
  @Column(name = "avatarPath")
  private String avatarPath;

  @Transient
  private MultipartFile avatarFile;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  private Set<Comment> comment = new HashSet<Comment>();

  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "user")
  private Set<Bid> bids = new HashSet<Bid>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAvatarPath() {
    return avatarPath;
  }

  public void setAvatarPath(String avatarPath) {
    this.avatarPath = avatarPath;
  }

  public MultipartFile getAvatarFile() {
    return avatarFile;
  }

  public void setAvatarFile(MultipartFile avatarFile) {
    this.avatarFile = avatarFile;
  }

  public Set<Comment> getComment() {
    return comment;
  }

  public void setComment(Set<Comment> comment) {
    this.comment = comment;
  }

  public Set<Bid> getBids() {
    return bids;
  }

  public void setBids(Set<Bid> bids) {
    this.bids = bids;
  }

}
