package com.auction.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

  @Column(name = "sex")
  private Integer sex;

  @Column(name = "birthday")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private Date birthday;

  @Column(name = "email", length = 50)
  private String email;

  // 用户上传的头像文件在服务器中的路径信息
  @Column(name = "avatarPath")
  private String avatarPath;

  @Transient
  private MultipartFile avatarFile;

  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "user")
  @JsonBackReference
  private Set<Comment> comment = new HashSet<Comment>();

  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "user")
  @JsonBackReference
  private Set<Bid> bids = new HashSet<Bid>();

  @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "user")
  @JsonBackReference
  private Set<Product> products = new HashSet<Product>();

  /**
   * 这里的mappedby="sender"，表示这个一对多的关系是由Message方（多的一方）管理，
   * 具体来说就是有Message方（多的一方）中的sender成员变量来管理，
   * mappedby 后面跟的名字必须和对应的 多的一方的 对应成员变量名称一致。
   */
  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "sender")
  @JsonBackReference
  private Set<Message> sendMsgs = new HashSet<Message>();

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "receiver")
  @JsonBackReference
  private Set<Message> receiveMsgs = new HashSet<Message>();

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

  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
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

  public Set<Product> getProducts() {
    return products;
  }

  public void setProducts(Set<Product> products) {
    this.products = products;
  }

  public Set<Message> getSendMsgs() {
    return sendMsgs;
  }

  public void setSendMsgs(Set<Message> sendMsgs) {
    this.sendMsgs = sendMsgs;
  }

  public Set<Message> getReceiveMsgs() {
    return receiveMsgs;
  }

  public void setReceiveMsgs(Set<Message> receiveMsgs) {
    this.receiveMsgs = receiveMsgs;
  }

}
